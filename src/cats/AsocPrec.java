//Paquete
package cats;

//Importaciones
import java.awt.Cursor;
import javax.swing.border.LineBorder;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.text.NumberFormat;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ptovta.Star;
import ptovta.Login;




/*Clase para asociar precios con productos por cliente*/
public class AsocPrec extends javax.swing.JFrame 
{
    //Contiene los valores orginales para al momento de guardar cambios
    private String          sProdOriG,  sCliOriG, sClasOriG, sClasJeraOriG;
    
    /*Declara variables originales*/
    private String          sClienOri;
    private String          sProdOri;
    private String          sClasOri;
    private String          sClasProdOri;
    private String          sUtilOri;
    private String          sPreOri;
    private String          sListOri;
    
    /*Declara contadors globales*/  
    private int             iContFi;
    
    /*Contador del cell*/
    private int             iContCellEd;
    
    /*Contiene el grupo de los radiobutton*/
    private javax.swing.ButtonGroup bG = new javax.swing.ButtonGroup();
    
    /*Variable que contiene el borde actual*/
    private Border          bBordOri;

    
    
    /*Constructor sin argumentos*/
    public AsocPrec() 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        //Para que la tabla tenga scroll horisontal
        jTab.setAutoResizeMode(0);
        
        //Establece el tamaño de las columnas
        jTab.getColumnModel().getColumn(3).setPreferredWidth(180);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(180);
        
        /*Crea el grupo de los radio buttons*/        
        bG.add(jRUtil);
        bG.add(jRPre);
        bG.add(jRList);
        
        /*Crea el listener para cuando se cambia de selección en la tabla*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {                
                /*Si no hay selección entonces regresa*/                                
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Procesa esto en una función*/
                vSel();
            }
        });        
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Reglas de Negocios, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el campo del cliente*/
        jTCli.grabFocus();
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Inicializa el contador de filas en uno*/
        iContFi      = 1;
        
        /*Carga todas las asociaciones existentes*/
        vCarg();
        
        /*Inicializa el contador del celleditor*/
        iContCellEd = 1;
                
        /*Crea el listener para la tabla de marcs, para cuando se modifique una celda*/
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
                    /*Si el contador de cell editor está en 1 entonces*/
                    if(iContCellEd==1)
                    {                                                
                        /*Obtén algunos datos originales de la fila*/                        
                        sClienOri   = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                  
                        sProdOri    = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sClasOri    = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sClasProdOri= jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sUtilOri    = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sPreOri     = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sListOri    = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd = 1;
                            
                        /*Coloca los valores origianales*/
                        jTab.setValueAt(sClienOri,      jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sProdOri,       jTab.getSelectedRow(), 2);
                        jTab.setValueAt(sClasOri,       jTab.getSelectedRow(), 3);
                        jTab.setValueAt(sClasProdOri,   jTab.getSelectedRow(), 4);
                        jTab.setValueAt(sUtilOri,       jTab.getSelectedRow(), 5);
                        jTab.setValueAt(sPreOri,        jTab.getSelectedRow(), 6);
                        jTab.setValueAt(sListOri,       jTab.getSelectedRow(), 7);
                    }
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */           
        };
        
        /*Establece el listener para la tabla de asociaciones*/
        jTab.addPropertyChangeListener(pr);
    
    }/*Fin de public AsocPrec() */

    
    /*Cuando se cambia de selección en la tabla obtiene los datos del registro*/
    private void vSel()
    {        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Limpia todos los campos de filtro
        jTCli.setText           ("");
        jTNom.setText           ("");
        jTProd.setText          ("");
        jTDescrip.setText       ("");
        jTClas.setText          ("");
        jTDescripClas.setText   ("");
        jTJera.setText          ("");
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;                                
        
        /*Obtiene los datos del registro de la fila seleccionada*/
        try
        {
            sQ = "SELECT asocdesc.CLASJERA, asocdesc.CLAS, clasemp.DESCRIP AS clasdescrip, usar, asocdesc.LIST, asocdesc.UTIL, nom, clien, asocdesc.PROD, prods.DESCRIP, prec FROM asocdesc LEFT OUTER JOIN clasemp ON clasemp.COD = asocdesc.CLAS LEFT OUTER JOIN prods ON prods.PROD = asocdesc.PROD LEFT OUTER JOIN emps ON CONCAT_WS('', ser, codemp) = asocdesc.CLIEN WHERE clien = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString().trim() + "' AND asocdesc.PROD = '" + jTab.getValueAt(jTab.getSelectedRow(), 2).toString().trim() + "' AND asocdesc.CLAS = '" + jTab.getValueAt(jTab.getSelectedRow(), 3).toString().trim() + "' AND asocdesc.CLASJERA = '" + jTab.getValueAt(jTab.getSelectedRow(), 4).toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {                
                /*Coloca el código del cliente y su nombre en los controles*/
                jTCli.setText           (rs.getString("clien"));
                jTCli.setCaretPosition  (0);
                jTNom.setText           (rs.getString("nom"));
                jTNom.setCaretPosition  (0);
                
                /*Coloca el código de la clasificación y su nombre en los controles*/
                jTClas.setText          (rs.getString("clas"));
                jTClas.setCaretPosition (0);
                jTDescripClas.setText   (rs.getString("clasdescrip"));
                jTDescripClas.setCaretPosition(0);
                
                /*Coloca el código del producto y su descripción en los controles*/
                jTProd.setText              (rs.getString("prod"));
                jTProd.setCaretPosition     (0);
                jTDescrip.setText           (rs.getString("descrip"));
                jTDescrip.setCaretPosition  (0);
            
                //Coloca la clasificación jerárquica
                jTJera.setText          (rs.getString("clasjera"));
                
                //Guarda los valores originales
                sProdOriG               = rs.getString("prod");
                sCliOriG                = rs.getString("clien");
                sClasOriG               = rs.getString("clas");
                sClasJeraOriG           = rs.getString("clasjera");
                
                /*Obtiene el precio y dale formato de moeda*/                
                NumberFormat n      = NumberFormat.getCurrencyInstance(java.util.Locale.US);
                double dCant        = Double.parseDouble(rs.getString("prec"));                
                String sPre         = n.format(dCant);
                
                /*Colocalo en su campo*/
                jTPre.setText           (sPre);
                jTPre.setCaretPosition  (0);
                
                /*Coloca el descuento y la lista de precio*/
                jComList.setSelectedItem    (rs.getString("list"));
                jTUtil.setText              (rs.getString("util"));
                jTUtil.setCaretPosition     (0);
                
                /*Marca el radio correspondiente*/
                if(rs.getString("usar").compareTo("util")==0)
                    bG.setSelected(jRUtil.getModel(), true);
                else if(rs.getString("usar").compareTo("prec")==0)
                    bG.setSelected(jRPre.getModel(), true);
                else if(rs.getString("usar").compareTo("list")==0)
                    bG.setSelected(jRList.getModel(), true);                
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
                    
    }/*Fin de private void vSel()*/
                
                
    /*Obtén las asociaciones de la base de datos y cargalas en la tabla*/
    private void vCarg()
    {        
        /*Reinicia el contador de filas*/
        iContFi = 1;
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el objeto para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();                    
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;                                
        
        /*Obtiene todas las asociacioes de la base de datos y cargalas en la tabla*/
        try
        {
            sQ = "SELECT clien, prod, util, prec, list, clas, clasjera FROM asocdesc ORDER BY prod";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Dale formato de moenda al precio*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(java.util.Locale.US);
                double dCant    = Double.parseDouble(rs.getString("prec"));                
                String sPre     = n.format(dCant);
                
                /*Agregalos a la tabla*/
                Object nu[]         = {iContFi, rs.getString("clien"), rs.getString("prod"), rs.getString("clas"), rs.getString("clasjera"), rs.getString("util"), sPre, rs.getString("list")};
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
        
    }/*Fin de private void vCarg()*/
    
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBDel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jTDescrip = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jLAyu = new javax.swing.JLabel();
        jBGuar = new javax.swing.JButton();
        jBProd = new javax.swing.JButton();
        jTProd = new javax.swing.JTextField();
        jBCli = new javax.swing.JButton();
        jTCli = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTNom = new javax.swing.JTextField();
        jRList = new javax.swing.JRadioButton();
        jRPre = new javax.swing.JRadioButton();
        jRUtil = new javax.swing.JRadioButton();
        jTPre = new javax.swing.JTextField();
        jTUtil = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComList = new javax.swing.JComboBox();
        jBBusc = new javax.swing.JButton();
        jBMosT = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBAyu = new javax.swing.JButton();
        jTClas = new javax.swing.JTextField();
        jBClas = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTDescripClas = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTJera = new javax.swing.JTextField();
        jBJera = new javax.swing.JButton();

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
        jBDel.setToolTipText("Borrar Marca(s) (Ctrl+SUPR)");
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 290, 120, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("REGLAS DE NEGOCIOS POR CLIENTE Y PRODUCTO");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 510, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jTCli);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 320, 120, 30));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nueva Marca (Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jTProd);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, 90, 20));

        jTDescrip.setEditable(false);
        jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescrip.setNextFocusableComponent(jTJera);
        jTDescrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripFocusGained(evt);
            }
        });
        jTDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripKeyPressed(evt);
            }
        });
        jP1.add(jTDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 380, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("*Cliente:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 150, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cliente", "Producto", "Clasificación cliente", "Clasificación producto", "Utilidad", "Precio", "Lista"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 530, 230));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 450, 120, 40));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar");
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, 120, 30));

        jBProd.setBackground(new java.awt.Color(255, 255, 255));
        jBProd.setText("...");
        jBProd.setToolTipText("Buscar Producto(s)");
        jBProd.setNextFocusableComponent(jTDescrip);
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
        jP1.add(jBProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 30, 20));

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
        jP1.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 120, 20));

        jBCli.setBackground(new java.awt.Color(255, 255, 255));
        jBCli.setText("...");
        jBCli.setToolTipText("Buscar Cliente");
        jBCli.setNextFocusableComponent(jBNew);
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
        jP1.add(jBCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 30, 20));

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
        jP1.add(jTCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 120, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("*Clasificación producto:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 200, -1));

        jTNom.setEditable(false);
        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jP1.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 380, 20));

        jRList.setBackground(new java.awt.Color(255, 255, 255));
        jRList.setText("Lista");
        jRList.setToolTipText("Manejar esta lista de precios solamente");
        jRList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRListKeyPressed(evt);
            }
        });
        jP1.add(jRList, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, -1, 20));

        jRPre.setBackground(new java.awt.Color(255, 255, 255));
        jRPre.setText("Precio");
        jRPre.setToolTipText("Manejar este precio solamente");
        jRPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRPreKeyPressed(evt);
            }
        });
        jP1.add(jRPre, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 70, 20));

        jRUtil.setBackground(new java.awt.Color(255, 255, 255));
        jRUtil.setSelected(true);
        jRUtil.setText("Utilidad");
        jRUtil.setToolTipText("Manejar esta utilidad solamente");
        jRUtil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRUtilKeyPressed(evt);
            }
        });
        jP1.add(jRUtil, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 230, 70, 20));

        jTPre.setText("$0.00");
        jTPre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre.setNextFocusableComponent(jComList);
        jTPre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPreFocusLost(evt);
            }
        });
        jTPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPreKeyTyped(evt);
            }
        });
        jP1.add(jTPre, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 90, 20));

        jTUtil.setText("0");
        jTUtil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUtil.setNextFocusableComponent(jTPre);
        jTUtil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUtilFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUtilFocusLost(evt);
            }
        });
        jTUtil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUtilKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUtilKeyTyped(evt);
            }
        });
        jP1.add(jTUtil, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 80, 20));

        jLabel2.setText("Lista Precio");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 80, 20));

        jLabel5.setText("Utilidad:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 80, 20));

        jLabel6.setText("Precio:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 80, 20));

        jComList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        jComList.setNextFocusableComponent(jTab);
        jComList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComListKeyPressed(evt);
            }
        });
        jP1.add(jComList, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 90, 20));

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

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setNextFocusableComponent(jBGuar);
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 490, 140, 20));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 490, 240, 20));

        jBAyu.setBackground(new java.awt.Color(0, 153, 153));
        jBAyu.setToolTipText("Ayuda de Búsqueda Avanzada");
        jBAyu.setNextFocusableComponent(jBMosT);
        jBAyu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAyuActionPerformed(evt);
            }
        });
        jBAyu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAyuKeyPressed(evt);
            }
        });
        jP1.add(jBAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 490, 10, 20));

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
        jP1.add(jTClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 120, 20));

        jBClas.setBackground(new java.awt.Color(255, 255, 255));
        jBClas.setText("...");
        jBClas.setToolTipText("Buscar Clasificaciòn(es)");
        jBClas.setNextFocusableComponent(jTDescripClas);
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
        jP1.add(jBClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 30, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("*Producto:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 150, -1));

        jTDescripClas.setEditable(false);
        jTDescripClas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripClas.setNextFocusableComponent(jTUtil);
        jTDescripClas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripClasFocusGained(evt);
            }
        });
        jTDescripClas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripClasKeyPressed(evt);
            }
        });
        jP1.add(jTDescripClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 380, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("*Clasificación cliente:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 200, -1));

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
        jP1.add(jTJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 120, 20));

        jBJera.setBackground(new java.awt.Color(255, 255, 255));
        jBJera.setText("...");
        jBJera.setToolTipText("Buscar jerárquia(s)");
        jBJera.setNextFocusableComponent(jTClas);
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
        jP1.add(jBJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 30, 20));

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
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
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
            JOptionPane.showMessageDialog(null, "No has seleccionado una regla de la lista para borrar.", "Borrar Regla", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();            
            return;            
        }
        
        /*Preguntar al usuario si esta seguro de querer borrar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere borrar la(s) regla(s)?", "Borrar Regla(es)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;                
        String      sQ;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {                   
            /*Borralo de la base de datos*/
            try 
            {                
                sQ = "DELETE FROM asocdesc WHERE clien = '" + jTab.getValueAt(x, 1).toString().trim() + "' AND prod = '" + jTab.getValueAt(x, 2).toString().trim() + "' AND clas = '" + jTab.getValueAt(x, 3).toString().trim() + "'";                    
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
        JOptionPane.showMessageDialog(null, "Regla(s) borrada(s) con éxito.", "Regla", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
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
        
        /*Cierra la forma*/
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

            
    /*Cuando se presiona una tecla en el campo de descripción de modea*/
    private void jTDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripKeyPressed

    
    /*Cuando se presiona una  tecla en la tabla de marcs*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de texto de descripción de la modea*/
    private void jTDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescrip.setSelectionStart(0);jTDescrip.setSelectionEnd(jTDescrip.getText().length());        
        
    }//GEN-LAST:event_jTDescripFocusGained


    //Método para validar que los campos ingresados sean correctos
    private int iValDats(boolean bValCond)
    {
        //Si no a ingresado por lo menos un campo para filtrar entonces
        if(jTNom.getText().compareTo("")==0 && jTDescrip.getText().compareTo("")==0 && jTDescripClas.getText().compareTo("")==0 && jTJera.getText().compareTo("")==0)
        {
            //Mensajea y regresa error
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un filtro.", "Reglas de negocio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
            return -1;            
        }
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa error
        if(con==null)
            return -1;
        
        //Checa si  el cliente existe en la base de datos
        int iRes    = Star.iExistCliProv(con, jTCli.getText().trim(), true);
        
        //Si hubo error entonces regresa error
        if(iRes==-1)
            return -1;
        
        //Si el cliente no existe entonces
        if(iRes==0 && jTNom.getText().trim().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return -1;
                        
            /*Coloca el borde rojo*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Avisa al usuario*/
            JOptionPane.showMessageDialog(null, "El cliente: " + jTCli.getText().trim() + " no existe.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            //Pon el foco del teclado en el campo del código de la marca y regresa error
            jTCli.grabFocus();               
            return -1;            
        }
                                
        //Checa si  el producto existe en la base de datos        
        iRes        = Star.iExistProd(con, jTProd.getText().trim());
        
        //Si hubo error entonces regresa error
        if(iRes==-1)
            return -1;
        
        //Si el producto no existe entonces
        if(iRes==0 && jTProd.getText().trim().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return -1;
                        
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Avisa al usuario*/
            JOptionPane.showMessageDialog(null, "El Producto: " + jTProd.getText().trim() + " no existe.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el campo del código de la marca y regresa error*/
            jTProd.grabFocus();               
            return -1;
            
        }//Fin de if(iRes==0)
        
        //Checa si la clasificación del cliente existe en la base de datos       
        iRes        = Star.iExistClasCli(con, jTClas.getText().trim());
        
        //Si hubo error entonces regresa error
        if(iRes==-1)
            return -1;
        
        //Si la clasificación no existe entonces
        if(iRes==0 && jTClas.getText().trim().compareTo("")!=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return -1;
                        
            /*Coloca el borde rojo*/                               
            jTClas.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Avisa al usuario*/
            JOptionPane.showMessageDialog(null, "La clasificación de cliente: " + jTClas.getText().trim() + " no existe.", "Clasificación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el campo del código de la marca y regresa error*/
            jTClas.grabFocus();               
            return -1;
            
        }//Fin de if(iRes==0 && jTClas.getText().trim().compareTo("")!=0)

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        //Comprueba si la clasificación del producto existe
        try
        {           
            sQ = "SELECT clas FROM clasjeraprod WHERE rut = '" + jTJera.getText().trim() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si no hay datos entonces
            if(!rs.next() && jTJera.getText().trim().compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return -1;
                            
                //Avisa al usuario
                JOptionPane.showMessageDialog(null, "La clasificación de producto no existe.", "regla", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                
                //Coloca el borde rojo y regresa error
                jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                return -1;                    
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;
        }
                
        /*Checa si  el cliente, producto y clasificación ya existen en la base de datos*/        
        try
        {           
            sQ = "SELECT prod, clien, clas, clasjera FROM asocdesc WHERE prod = '" + jTProd.getText().trim() + "' AND clien = '" + jTCli.getText().trim() + "' AND clas = '" + jTClas.getText().trim() + "' AND clasjera = '" + jTJera.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe*/
            if(rs.next())
            {
                //Si viene de guardar cambios entonces
                boolean bSal    = true;
                if(!bValCond)            
                {
                    //Si todos los valores son iguales a los originales entonces coloca la bandera para indicar que todo esta bien
                    if(sProdOriG.compareTo(rs.getString("prod"))==0 && sCliOriG.compareTo(rs.getString("clien"))==0 && sClasOriG.compareTo(rs.getString("clas"))==0 && sClasJeraOriG.compareTo(rs.getString("clasjera"))==0)
                        bSal    = false;                    
                }
        
                //Si tiene que manejar error entonces
                if(bSal)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return -1;
                                
                    /*Avisa al usuario y regresa error*/
                    JOptionPane.showMessageDialog(null, "Esta regla ya existe.", "Regla", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                    return -1;
                    
                }//Fin de if(bSal)                                    
                
            }//Fin de if(rs.next())
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return -1;
        }
        
        //Regresa que todo fue bien
        return 0;
        
    }//Fin de private int iValDats(boolean bValCond)
    
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
                                                
        //Valida los datos ingresados primeramente
        int iRes    = iValDats(true);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        iRes        = JOptionPane.showOptionDialog(this, "¿Seguro que quieres agregar esta regla?", "Agregar Regla", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;        
        
        /*Determina el tipo a usar*/
        String sUsa = "list";
        if(jRUtil.isSelected())
            sUsa    = "desc";
        else if(jRPre.isSelected())
            sUsa    = "prec";
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;        
        String      sQ;                                
        
        /*Inserta en la base de datos el nuevo registro de asociación*/
        try 
        {                
            sQ = "INSERT INTO asocdesc(clien,                                               prod,                                                   util,                       list,                                         prec,                                                        usar,           sucu,                 nocaj,                 estac,                 clas,                              clasjera) "
                        + "VALUES('" + jTCli.getText().replace("'", "''").trim() + "', '" + jTProd.getText().replace("'", "''").trim() + "', " +    jTUtil.getText() + ", " +   jComList.getSelectedItem().toString() + ", " + jTPre.getText().replace("$", "").replace(",", "") + ", '" + sUsa + "', '" + Star.sSucu + "', '" + Star.sNoCaj + "', '" + Login.sUsrG + "', '" + jTClas.getText().trim() + "', '" + jTJera.getText().trim() + "')";                    
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
                    
        /*Agrega el registro en la tabla*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFi, jTCli.getText().trim(), jTProd.getText(), jTClas.getText(), jTJera.getText().trim(), jTUtil.getText(), jTPre.getText(), jComList.getSelectedItem().toString()};
        te.addRow(nu);
        
        /*Aumenta el contador de filas en uno*/
        ++iContFi;
        
        /*Pon el foco del teclado en el campo del cliente*/
        jTCli.grabFocus();                
         
        /*Mensajea*/
        JOptionPane.showMessageDialog(null, "Éxito en la agregación de la regla.", "Regla", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBNewActionPerformed
                
    
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
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelMouseEntered
        
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited
    
    
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

           
   /*Cuando el mouse entra en el botón de guardar*/ 
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered

        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);

    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuando el mouse sale del botón de guardar*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(Star.colOri);

    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        /*Si no a seleccionado un elemento de la lista entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una regla primero para guardar cambios.", "Guardar cambios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }
        
        //Valida los datos ingresados primeramente
        int iRes    = iValDats(false);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        /*Preguntar al usuario si esta seguro de que querer guardar los cambios*/
        Object[] op = {"Si","No"};
        iRes        = JOptionPane.showOptionDialog(this, "¿Seguro que quieres guardar los cambios?", "Guardar cambios", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Determina el tipo a usar*/
        String sUsa = "list";
        if(jRUtil.isSelected())
            sUsa    = "desc";
        else if(jRPre.isSelected())
            sUsa    = "prec";

        //Declara variables de la base de datos    
        Statement   st;                
        String      sQ;                                                                                
        
        /*Actualiza en la base de datos el registro*/
        try 
        {                
            sQ = "UPDATE asocdesc SET "
                    + "clien        = '" + jTCli.getText().trim() + "', "
                    + "prod         = '" + jTProd.getText().trim() + "', "
                    + "clas         = '" + jTClas.getText().trim() + "', "
                    + "clasjera     = '" + jTJera.getText().trim() + "', "
                    + "usar         = '" + sUsa + "', "
                    + "util         = " + jTUtil.getText() + ", "
                    + "list         = " + jComList.getSelectedItem().toString() + ", "
                    + "prec         = " + jTPre.getText().replace("$", "").replace(",", "") + " "
                    + "WHERE clien  = '" + jTab.getValueAt(jTab.getSelectedRow(), 1) + "' AND prod = '" + jTab.getValueAt(jTab.getSelectedRow(), 2) + "'";                                
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
        
        /*Carga nuevamente los registros en la tabla*/
        vCarg();
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Cambios guardados con exito.", "Guardar cambios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuando se presiona una tecla en el botón de guardar cambios*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando el mouse entra en el botón de búscar producto*/
    private void jBProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseEntered

        /*Cambia el color del fondo del botón*/
        jBProd.setBackground(Star.colBot);

    }//GEN-LAST:event_jBProdMouseEntered

    
    /*Cuando el mouse sale del botón de búsca producot*/
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(Star.colOri);

    }//GEN-LAST:event_jBProdMouseExited

    
    /*Cuando se presiona el botón de búscar producto*/
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        ptovta.Busc b = new ptovta.Busc(this, jTProd.getText(), 2, jTProd, jTDescrip, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();

    }//GEN-LAST:event_jBProdActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar producto*/
    private void jBProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBProdKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de búscar producto*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());

    }//GEN-LAST:event_jTProdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del producto*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Coloca el borde negro si tiene datos*/
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Si el campo es cadena vacia entonces*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Coloca en la descripción cadena vacia y regresa*/
            jTDescrip.setText("");            
            return;
        }
        
        /*Comprueba si existe el producto*/
        Star.vExisProd(jTProd.getText(),jTDescrip);
        
    }//GEN-LAST:event_jTProdFocusLost

    
    /*Cuando se presiona una tecla en el campo del producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            ptovta.Busc b = new ptovta.Busc(this, jTProd.getText(), 2, jTProd, null, null, "", null);
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

    
    /*Cuando el mouse entra en el botón de búscar cliente*/
    private void jBCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseEntered

        /*Cambia el color del fondo del botón*/
        jBCli.setBackground(Star.colBot);

    }//GEN-LAST:event_jBCliMouseEntered

    
    /*Cuando el mouse sale del botón de búscar cliente*/
    private void jBCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBCli.setBackground(Star.colOri);

    }//GEN-LAST:event_jBCliMouseExited

    
    /*Cuando se presiona el botón de búscar cliente*/
    private void jBCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCliActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        ptovta.Busc b = new ptovta.Busc(this, jTCli.getText(), 1, jTCli, jTNom, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBCliActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar cliente*/
    private void jBCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCliKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCliKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del cliente*/
    private void jTCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCli.setSelectionStart(0);jTCli.setSelectionEnd(jTCli.getText().length());

    }//GEN-LAST:event_jTCliFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del cliente*/
    private void jTCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCli.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTCli.getText().compareTo("")!=0)
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Si el campo es cadena vacia entonces*/
        if(jTCli.getText().compareTo("")==0)
        {
            /*Coloca en el nombre cadena vacia y regresa*/
            jTNom.setText("");
            return;
        }
        
        /*Comprueba si existe el cliente*/
        Star.vExisCli(jTCli.getText(),jTNom);
        
    }//GEN-LAST:event_jTCliFocusLost

    
    /*Cuando se presiona una tecla en el campo del cliente*/
    private void jTCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            ptovta.Busc b = new ptovta.Busc(this, jTCli.getText(), 1, jTCli, jTNom, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCliKeyPressed

    
    /*Cuando se tipea una tecla en el campo del cliente*/
    private void jTCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTCliKeyTyped

    
    /*Cuando se gana el foco del teclado en el camo del nombre*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length());
        
    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del nombre*/
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost

        /*Coloca el caret al principio del control*/
        jTNom.setCaretPosition(0);

    }//GEN-LAST:event_jTNomFocusLost

    
    /*Cuando se presiona una tecla en el campo del nombre*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomKeyPressed
                
    
    /*Cuando se gana el foco del teclado en el campo del precio*/
    private void jTPreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPreFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTPre.setSelectionStart(0);jTPre.setSelectionEnd(jTPre.getText().length());

        /*Guarda el precio original*/
        sPreOri = jTPre.getText();

    }//GEN-LAST:event_jTPreFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del precio*/
    private void jTPreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPreFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTPre.getText().compareTo("")!=0)
            jTPre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Si es cadena vacia entonces coloca el precio original*/
        if(jTPre.getText().compareTo("")==0)        
        {
            jTPre.setText(sPreOri);            
            jTPre.setCaretPosition(0);
        }

        /*Dale formato de moneda al precio*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(java.util.Locale.US);
        double dCant    = Double.parseDouble(jTPre.getText().replace("$", "").replace(",", ""));                
        jTPre.setText(n.format(dCant));          

    }//GEN-LAST:event_jTPreFocusLost

    
    /*Cuando se presiona una tecla en el campo del precio*/
    private void jTPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPreKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTPreKeyPressed

    
    /*Cuando se tipea una tecla en el campo del precio*/
    private void jTPreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPreKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTPreKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de la utilidad*/
    private void jTUtilFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtilFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTUtil.setSelectionStart(0);jTUtil.setSelectionEnd(jTUtil.getText().length());

        /*Guarda la utilidad original que tenía*/
        sUtilOri = jTUtil.getText();

    }//GEN-LAST:event_jTUtilFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la utilidad*/
    private void jTUtilFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtilFocusLost

        /*Si es cadena vacia la utilidad entonces*/
        if(jTUtil.getText().compareTo("")==0)
        {
            /*Que sea 0 y regresa*/
            jTUtil.setText("0");
            return;
        }

        /*Si la utilidad es mayor a 100 o menor a 0 entonces coloca la utilidad original*/
        if(Double.parseDouble(jTUtil.getText())>100 || Double.parseDouble(jTUtil.getText())<0)
        {
            jTUtil.setText          (sUtilOri);
            jTUtil.setCaretPosition (0);
        }

    }//GEN-LAST:event_jTUtilFocusLost

    
    /*Cuando se presiona uan tecla en el campo de la utilidad*/
    private void jTUtilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUtilKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTUtilKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la utilidad*/
    private void jTUtilKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUtilKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTUtilKeyTyped

    
    //Cuando se presiona una tecla en el check de utilidad
    private void jRUtilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRUtilKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRUtilKeyPressed

    
    /*Cuando se presiona una tecla en el radio de precio*/
    private void jRPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRPreKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRPreKeyPressed

    
    /*Cuando se presiona una tecla en el radio de lista de precio*/
    private void jRListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRListKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRListKeyPressed

    
    /*Cuando se presiona una tecla en el combo de la lista de precios*/
    private void jComListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComListKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComListKeyPressed

    
    /*Cuando el mouse entra en el botón de búscar*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered

        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);

        /*Guardar el borde original que tiene el botón*/
        bBordOri    = jBBusc.getBorder();

        /*Aumenta el grosor del botón*/
        jBBusc.setBorder(new LineBorder(java.awt.Color.GREEN, 3));

    }//GEN-LAST:event_jBBuscMouseEntered

    
    /*Cuando el mouse sale del botón de búscar*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(Star.colOri);

        /*Coloca el borde que tenía*/
        jBBusc.setBorder(bBordOri);

    }//GEN-LAST:event_jBBuscMouseExited

    
    /*Cuando se presiona el botón de búscar*/
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

        /*Crea el token*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(jTBusc.getText().replace(" ", ""),",");
                
        /*Recorre todos los tokens para crear la condinción de búsqueda*/
        String sCond    = "";
        while(stk.hasMoreElements())
        {
            /*Obtiene el token*/
            String sTok = stk.nextToken().toLowerCase();
            
            /*Si tiene cliente entonces completa la consulta*/
            if(sTok.startsWith("cli="))                                                        
                sCond   += " AND REPLACE(clien, ' ', '' ) = '" + sTok.replace("cli=", "") + "'";                        
            /*Else if tiene producto entonces completa la consulta*/
            else if(sTok.startsWith("prod="))
                sCond   += " AND REPLACE(prod, ' ', '' ) = '" + sTok.replace("prod=", "") + "'";            
            /*Else if tiene clasificación entonces completa la consulta*/
            else if(sTok.startsWith("clas="))
                sCond   += " AND REPLACE(clas, ' ', '' ) = '" + sTok.replace("clas=", "") + "'";            
            /*Else if tiene utilidad entonces completa la consulta*/
            else if(sTok.startsWith("util="))
                sCond   += " AND util = " + sTok.replace("util=", "");            
            /*Else if tiene lista entonces*/
            else if(sTok.startsWith("list="))
                sCond   += " AND list = " + sTok.replace("list=", "");            
            /*Else if tiene precio entonces*/
            else if(sTok.startsWith("prec="))
                sCond   += " AND prec = " + sTok.replace("prec=", "");            
            /*Else if tiene nombre de cliente entonces*/
            else if(sTok.startsWith("nom="))
                sCond   += " AND REPLACE(nom, ' ', '' ) LIKE('%" + sTok.replace("nom=", "").replace(" ", "%") + "%')";            
            /*Else if tiene descripción de producto entonces*/
            else if(sTok.startsWith("descrip="))
                sCond   += " AND REPLACE(descrip, ' ', '' ) = '" + sTok.replace("descrip=", "") + "'";            
            else
                sCond   += " AND clien LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')" + " OR asocdesc.PROD LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')" + " OR asocdesc.PREC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')" + " OR asocdesc.LIST LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            
        }/*Fin de while(stk.hasMoreElements())*/
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);

        /*Resetea el contador de filas*/
        iContFi              = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Obtiene de la base de datos los registros*/
        try
        {
            sQ = "SELECT asocdesc.CLASJERA, clien, asocdesc.PROD, asocdesc.UTIL, asocdesc.PREC, asocdesc.LIST, asocdesc.CLAS FROM asocdesc LEFT OUTER JOIN emps ON CONCAT_WS('', ser, codemp) = asocdesc.CLIEN LEFT OUTER JOIN prods ON prods.PROD = asocdesc.PROD WHERE 1=1 " + sCond;            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("clien"), rs.getString("prod"), rs.getString("clas"), rs.getString("clasjera"), rs.getString("util"), rs.getString("prec"), rs.getString("list")};
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

    
    /*Cuando se presiona una tecla en el botón de búscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando el mouse entra en el botón de mostrar todo*/
    private void jBMosTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseEntered

        /*Cambia el color del fondo del botón*/
        jBMosT.setBackground(Star.colBot);

    }//GEN-LAST:event_jBMosTMouseEntered

    
    /*Cuando el mouse sale del botón de mostrar todo*/
    private void jBMosTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBMosT.setBackground(Star.colOri);

    }//GEN-LAST:event_jBMosTMouseExited

    
    /*Cuando se presiona el botón de mostrar todos los registro*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed

        /*Función para mostrar nuevamente los elementos actualizados en la tabla*/
        vCarg();

    }//GEN-LAST:event_jBMosTActionPerformed

    
    /*Cuando se presiona uan tecla en el botón de mostrar todo*/
    private void jBMosTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMosTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBMosTKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de búscar*/
    private void jTBuscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusGained

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBBusc);

        /*Selecciona todo el texto cuando gana el foco*/
        jTBusc.setSelectionStart(0);jTBusc.setSelectionEnd(jTBusc.getText().length());

    }//GEN-LAST:event_jTBuscFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de búscar*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);

        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando se presiona una tecla en el campo de búscar*/
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

    
    /*Cuando se presiona una tecla en el botón de ayuda*/
    private void jBAyuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAyuKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBAyuKeyPressed

    
    /*Cuando se presiona el botón de ayuda de búsqueda*/
    private void jBAyuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAyuActionPerformed
        
        /*Muestra la forma de ayuda de búsqueda avanzada*/
        ptovta.filGral f = new ptovta.filGral(this,"AYUDA DE BÚSQUEDA AVANZADA:" + System.getProperty( "line.separator" ) + System.getProperty( "line.separator" )
                                                 + "Las abreviaciones a utilizar son las siguientes:" + System.getProperty( "line.separator" )
                                                 + "1)cli=(cliente)" + System.getProperty( "line.separator" )
                                                 + "2)prod=(pruducto)" + System.getProperty( "line.separator" )
                                                 + "3)clas=(clasificación)" + System.getProperty( "line.separator" )
                                                 + "4)nom=(nombre del cliente)" + System.getProperty( "line.separator" )
                                                 + "5)descrip=(descripción del producto)" + System.getProperty( "line.separator" )
                                                 + "6)list=(lista de precios)" + System.getProperty( "line.separator" )
                                                 + "7)prec=(precio)" + System.getProperty( "line.separator" )
                                                 + "8)util=(utilidad)" + System.getProperty( "line.separator" )
                                                 + "Estas abreviaciones se utilizan de la siguiente manera separadas por comas:" + System.getProperty( "line.separator" )
                                                 + "cli=cliente, prod=producto, nom=nombre del cliente ...etc." + System.getProperty( "line.separator" )
                                                 + "Ejemplo: cli = 09000, nom= cliente prueba, util= 4, prec = 16.78");        
    f.setVisible(true);
    
    }//GEN-LAST:event_jBAyuActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de la clasificación*/
    private void jTClasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTClas.setSelectionStart(0);jTClas.setSelectionEnd(jTClas.getText().length());

    }//GEN-LAST:event_jTClasFocusGained

    
    /*Cuando se pierde el foco en el campo de la clasificación*/
    private void jTClasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusLost

        /*Coloca el borde negro si tiene datos*/
        if(jTClas.getText().compareTo("")!=0)
            jTClas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Coloca el caret en la posiciòn 0*/
        jTClas.setCaretPosition(0);
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Obtiene la descripción de la clasificación
        String sDescrip = Star.sDescripClasCli(con, jTClas.getText().trim());
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción
        jTDescripClas.setText(sDescrip);
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Coloca el foco del teclado del control de la clasificación al principio del control*/
        jTDescripClas.setCaretPosition(0); 

    }//GEN-LAST:event_jTClasFocusLost

    
    /*Cuando se presiona una tecla en el campo de la clasificación del cliente*/
    private void jTClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClasKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar clasificación*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBClas.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTClasKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la clasificación del cliente*/
    private void jTClasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClasKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTClasKeyTyped

    
    /*Cuando el mouse entra en el botón de clasificación de cliente*/
    private void jBClasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBClasMouseEntered

        /*Cambia el color del fondo del botón*/
        jBClas.setBackground(Star.colBot);

    }//GEN-LAST:event_jBClasMouseEntered

    
    /*Cuando el mouse sale del botón de clasificación de cliente*/
    private void jBClasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBClasMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBClas.setBackground(Star.colOri);

    }//GEN-LAST:event_jBClasMouseExited

    
    /*Cuando se presiona el botón de búscar clasificación de cliente*/
    private void jBClasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBClasActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        ptovta.Busc b = new ptovta.Busc(this, jTClas.getText(), 12, jTClas, jTDescripClas, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código del producto*/
        jTClas.grabFocus();

    }//GEN-LAST:event_jBClasActionPerformed

    
    /*Cuando se presiona una tecla en el campo de clasificación de cliente*/
    private void jBClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBClasKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBClasKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción de la clasificación*/
    private void jTDescripClasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripClasFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripClas.setSelectionStart(0);jTDescripClas.setSelectionEnd(jTDescripClas.getText().length());        
        
    }//GEN-LAST:event_jTDescripClasFocusGained

    
    /*Cuando se presiona una tecla en el campo de la descripción de la clasificación*/
    private void jTDescripClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripClasKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripClasKeyPressed

    
    //Cuando se gana el foco del teclado en el campo de la clasificación jerarquica de producto
    private void jTJeraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTJera.setSelectionStart(0);jTJera.setSelectionEnd(jTJera.getText().length());
        
    }//GEN-LAST:event_jTJeraFocusGained

    
    //Cuando se pierde el foco del teclado en el campo de la clasificación jerarquica del producto
    private void jTJeraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTJera.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTJera.getText().compareTo("")!=0)
            jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTJeraFocusLost

    
    //Cuando se presiona una tecla en el campo del código de la clasificación gerarquica del producto
    private void jTJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTJeraKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar jeraquía*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBJera.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTJeraKeyPressed

    
    //Cuando el mouse entra en el botón del código de la clasificación jerarquica del producto
    private void jBJeraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseEntered

        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBJeraMouseEntered

    
    //Cuando el mouse sale del botón de clasificación jerarquica del producto
    private void jBJeraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseExited

        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBJeraMouseExited

    
    //Cuando se presiona el botón de búscar clasificación jerarquica de producto
    private void jBJeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBJeraActionPerformed

        /*Muestra la forma para ver las jerárquias*/
        cats.ClasJeraVis w = new cats.ClasJeraVis(jTJera.getText().trim(), jTJera, "prod");
        w.setVisible(true);
        
    }//GEN-LAST:event_jBJeraActionPerformed

    
    //Cuando se presiona una tecla en el botón de búscar clasificación jerarquica de producto
    private void jBJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBJeraKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBJeraKeyPressed

    
    
          
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
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F3 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAyu;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBClas;
    private javax.swing.JButton jBCli;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBJera;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBProd;
    private javax.swing.JButton jBSal;
    private javax.swing.JComboBox jComList;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jP1;
    private javax.swing.JRadioButton jRList;
    private javax.swing.JRadioButton jRPre;
    private javax.swing.JRadioButton jRUtil;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTClas;
    private javax.swing.JTextField jTCli;
    private javax.swing.JTextField jTDescrip;
    private javax.swing.JTextField jTDescripClas;
    private javax.swing.JTextField jTJera;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTPre;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTUtil;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
