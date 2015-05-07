//Paquete
package cats;

//Importaciones
import java.awt.Cursor;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ptovta.Login;
import ptovta.Star;




/*Catálogo de tallas y colores*/
public class TallColo extends javax.swing.JFrame 
{
    /*Declara variables originales*/
    private String          sProdOri;
    private String          sDescripOri;
    private String          sTallOri;
    private String          sDescripTallOri;
    private String          sColOri;
    private String          sDescripColOri;
    private String          sExistOri;
    private String          sAlmaOri;
    
    /*Declara contadors globales*/
    private int             iContCellEd;
    private int             iContFi;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;

    /*Variable que contiene el borde actual*/
    private javax.swing.border.Border bBordOri;
    
    
    
    /*Constructor sin argumentos*/
    public TallColo() 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Tallas y colores, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el campo de edición de código*/
        jTProd.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla*/
        jTab.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(500);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(6).setPreferredWidth(150);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Inicializa el contador de filas en uno*/
        iContFi      = 1;
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;                
        
        /*Crea el listener para la tabla, para cuando se modifique una celda*/
        PropertyChangeListener pr = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtén la propiedad que a sucedio en el control*/
                String pro = event.getPropertyName();                                
                                
                /*Si el evento fue por entrar en una celda del tabla*/
                if("tableCellEditor".equals(pro)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Si no hay selección entonces que regresa*/
                        if(jTab.getSelectedRow()==-1)
                            return;
                        
                        /*Obtén algunos datos originales de la fila*/                        
                        sProdOri            = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                  
                        sDescripOri         = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                                                                                                          
                        sTallOri            = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                                                                          
                        sDescripTallOri     = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                                                                          
                        sColOri             = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();                                                                                                          
                        sDescripColOri      = jTab.getValueAt(jTab.getSelectedRow(), 6).toString(); 
                        sExistOri           = jTab.getValueAt(jTab.getSelectedRow(), 7).toString(); 
                        sAlmaOri            = jTab.getValueAt(jTab.getSelectedRow(), 8).toString(); 
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd         = 1;
                            
                        /*Coloca los valores origianales*/
                        jTab.setValueAt(sProdOri,       jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sDescripOri,    jTab.getSelectedRow(), 2);
                        jTab.setValueAt(sTallOri,       jTab.getSelectedRow(), 3);
                        jTab.setValueAt(sDescripTallOri,jTab.getSelectedRow(), 4);
                        jTab.setValueAt(sColOri,        jTab.getSelectedRow(), 5);
                        jTab.setValueAt(sDescripColOri, jTab.getSelectedRow(), 6);
                        jTab.setValueAt(sExistOri,      jTab.getSelectedRow(), 7);
                        jTab.setValueAt(sAlmaOri,       jTab.getSelectedRow(), 8);
                    }
                                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla de marcs*/
        jTab.addPropertyChangeListener(pr);
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Carga todas las tallas en el combo
        if(Star.iCargTallCom(con, jComTall)==-1)
            return;
        
        //Trae todos los colores y cargalos en el combo
        if(Star.iCargColoCom(con, jComColo)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Obtén todas las tallas y colores de la base de datos*/
        vCarg();
            
    }/*Fin de public Marcs() */

    
    /*Obtén todas las tallas y colores de la base de datos*/
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
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todas las marcas de la base de datos y cargalas en la tabla*/
        try
        {
            sQ = "SELECT IFNULL(colos.DESCRIP, '' ) colodescrip, tall.DESCRIP, prods.DESCRIP, tallcolo.PROD, tallcolo.TALL, tallcolo.ALMA, tallcolo.COLO, tallcolo.EXIST FROM tallcolo LEFT OUTER JOIN colos ON colos.COD = tallcolo.COLO LEFT OUTER JOIN tall ON tall.COD = tallcolo.TALL LEFT OUTER JOIN prods ON prods.PROD = tallcolo.PROD GROUP BY colos.DESCRIP, tall.DESCRIP, prod, tall, alma, colo, exist";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("prod"), rs.getString("prods.DESCRIP"), rs.getString("tall"), rs.getString("tall.DESCRIP"), rs.getString("colo"), rs.getString("colodescrip"), rs.getString("exist"), rs.getString("alma")};
                dm.addRow(nu);
                
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
        jBSal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jTProd = new javax.swing.JTextField();
        jBProd = new javax.swing.JButton();
        jTDescrip = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jComColo = new javax.swing.JComboBox();
        jComTall = new javax.swing.JComboBox();
        jTDescripTall = new javax.swing.JTextField();
        jTDescripColo = new javax.swing.JTextField();
        jTAlma = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jBNew = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jBBusc = new javax.swing.JButton();
        jBMosT = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();

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
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jTProd);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 430, 90, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TALLAS Y COLORES");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 370, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("*Código Producto:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 150, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Producto", "Descripción Producto", "Talla", "Descripción Talla", "Color", "Descripción Color", "Existencia", "Almacén"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 780, 290));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 430, 120, 20));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 122, 130, 18));

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
        jP1.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 120, 20));

        jBProd.setBackground(new java.awt.Color(255, 255, 255));
        jBProd.setText("jButton1");
        jBProd.setToolTipText("Buscar Anaquel(es)");
        jBProd.setNextFocusableComponent(jComTall);
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
        jP1.add(jBProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 30, 20));

        jTDescrip.setEditable(false);
        jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jP1.add(jTDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 140, 20));

        jLabel16.setText("Almacén:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, 60, -1));

        jLabel14.setText("Color:");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 60, -1));

        jComColo.setNextFocusableComponent(jBNew);
        jComColo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComColoActionPerformed(evt);
            }
        });
        jComColo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComColoKeyPressed(evt);
            }
        });
        jP1.add(jComColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 90, 20));

        jComTall.setNextFocusableComponent(jComColo);
        jComTall.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComTallFocusLost(evt);
            }
        });
        jComTall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComTallActionPerformed(evt);
            }
        });
        jComTall.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComTallKeyPressed(evt);
            }
        });
        jP1.add(jComTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 90, 20));

        jTDescripTall.setEditable(false);
        jTDescripTall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripTall.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripTallFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripTallFocusLost(evt);
            }
        });
        jTDescripTall.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripTallKeyPressed(evt);
            }
        });
        jP1.add(jTDescripTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 80, 20));

        jTDescripColo.setEditable(false);
        jTDescripColo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripColo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripColoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripColoFocusLost(evt);
            }
        });
        jTDescripColo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripColoKeyPressed(evt);
            }
        });
        jP1.add(jTDescripColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 80, 20));

        jTAlma.setEditable(false);
        jTAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        });
        jP1.add(jTAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 80, 20));

        jLabel17.setText("Talla:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 60, -1));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nuevo Almacén (Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jBDel);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 90, 20));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Almacen(es) (Ctrl+SUPR)");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jTab);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 90, 20));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 140, 20));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setNextFocusableComponent(jBSal);
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 430, 140, 20));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 430, 250, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        
        /*Llama al recolector de basura y cierra la forma*/
        System.gc();                    
        this.dispose();                    
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

                   
    /*Cuando se presiona una  tecla en la tabla de marcs*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed
    
            
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
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

                
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

        
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

                
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

        
    /*Cuandos se gana el foco del teclado en el campo del código*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());
        
    }//GEN-LAST:event_jTProdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del código del producto*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Coloca el borde negro si tiene datos*/
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Coloca el caret en la posiciòn 0*/
        jTProd.setCaretPosition(0);

        /*Si es cadena vacia lo que ingreso el usuario entonces*/
        if(jTProd.getText().compareTo("")==0)
        {            
            /*Coloca cadena vacia en el control del producto y almacén*/
            jTDescrip.setText   ("");
            jTAlma.setText      ("");        
        }
                
    }//GEN-LAST:event_jTProdFocusLost

    
    /*Cuando se presiona una tecla en el campo del código*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            ptovta.Busc b = new ptovta.Busc(this, jTProd.getText(), 2, jTProd, jTDescrip, jTAlma, "", null);
            b.setVisible(true);
            
            /*Coloca los controles relacionados al producto al principio del control*/
            jTProd.setCaretPosition      (0);
            jTDescrip.setCaretPosition  (0);
            jTAlma.setCaretPosition     (0);           
        }
        /*Else if se presiono CTRL + B entonces*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_B)
        {
            /*Llama a la forma para búscar productos avanzadamente*/
            ptovta.BuscAvan v = new ptovta.BuscAvan(this, jTProd, jTDescrip, null, null);
            v.setVisible(true);
            
            /*Coloca el foco del teclado en el campo del producto*/
            jTProd.grabFocus();            
        }   
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTProdKeyPressed

    
    /*Cuando se tipea una tecla en el campo del código*/
    private void jTProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        
    }//GEN-LAST:event_jTProdKeyTyped

    
    /*Cuando el mouse entra en el campo del código*/
    private void jBProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseEntered

        /*Cambia el color del fondo del botón*/
        jBProd.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProdMouseEntered

    
    /*Cuando el mouse sale del botón del código*/
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBProdMouseExited

    
    /*Cuando se presiona el botón de búscar producto*/
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        ptovta.Busc b = new ptovta.Busc(this, jTProd.getText(), 2, jTProd, jTDescrip, jTAlma, "", null);
        b.setVisible(true);

        /*Coloca los controles relacionados al producto al principio del control*/
        jTProd.setCaretPosition      (0);
        jTDescrip.setCaretPosition  (0);
        jTAlma.setCaretPosition     (0);
                
    }//GEN-LAST:event_jBProdActionPerformed

    
    /*Cuando se presiona una tecla en el botón del búscar producto*/
    private void jBProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProdKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción*/
    private void jTDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescrip.setSelectionStart(0);jTDescrip.setSelectionEnd(jTDescrip.getText().length());

    }//GEN-LAST:event_jTDescripFocusGained

    
    /*Cuando se presiona una tecla en el campo de la descripción*/
    private void jTDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripKeyPressed

    
    /*Cuando sucede una acción en el combo de los colores*/
    private void jComColoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComColoActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComColo.getSelectedItem()==null)
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

        /*Obtiene la descripción de la talla en base a su código*/
        try
        {
            sQ = "SELECT descrip FROM colos WHERE cod = '" + jComColo.getSelectedItem().toString() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTDescripColo.setText(rs.getString("descrip"));
            else
                jTDescripColo.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }

        /*Coloca al principio del control el caret*/
        jTDescripColo.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComColoActionPerformed

    
    /*Cuando se presiona una tecla en el combo de los colores*/
    private void jComColoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComColoKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComColoKeyPressed

    
    /*Cuando sucede una acción en el combobox de tallas*/
    private void jComTallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComTallActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComTall.getSelectedItem()==null)
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

        /*Obtiene la descripción de la talla en base a su código*/
        try
        {
            sQ = "SELECT descrip FROM tall WHERE cod = '" + jComTall.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTDescripTall.setText(rs.getString("descrip"));
            else
                jTDescripTall.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }

        /*Coloca al principio del control el caret*/
        jTDescripTall.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComTallActionPerformed

    
    /*Cuando se presiona una tecla en el combo de tallas*/
    private void jComTallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComTallKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComTallKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción de la talla*/
    private void jTDescripTallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripTallFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripTall.setSelectionStart(0);jTDescripTall.setSelectionEnd(jTDescripTall.getText().length());

    }//GEN-LAST:event_jTDescripTallFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción de la talla*/
    private void jTDescripTallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripTallFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDescripTall.setCaretPosition(0);

    }//GEN-LAST:event_jTDescripTallFocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción de la talla*/
    private void jTDescripTallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripTallKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripTallKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del color*/
    private void jTDescripColoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripColoFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripColo.setSelectionStart(0);jTDescripColo.setSelectionEnd(jTDescripColo.getText().length());

    }//GEN-LAST:event_jTDescripColoFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del color*/
    private void jTDescripColoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripColoFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDescripColo.setCaretPosition(0);

    }//GEN-LAST:event_jTDescripColoFocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción del color*/
    private void jTDescripColoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripColoKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripColoKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del almacén*/
    private void jTAlmaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTAlma.setSelectionStart(0);jTAlma.setSelectionEnd(jTAlma.getText().length());

    }//GEN-LAST:event_jTAlmaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del almacén*/
    private void jTAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTAlma.setCaretPosition(0);

    }//GEN-LAST:event_jTAlmaFocusLost

    
    /*Cuando se presiona una tecla en el campo del almacén*/
    private void jTAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTAlmaKeyPressed

    
    /*Cuando el mouse entra en el botón de nuevo*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered

        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);

    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mosue sale del botón de nuevo*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);

    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando se presiona el botón de nueva partida*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed

        /*Si hay cadena vacia en el campo del código del producto no puede continuar*/
        if(jTProd.getText().replace(" ", "").trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código del producto esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTProd.grabFocus();
            return;
        }

        /*Si hay cadena vacia en la descripción del producto no puede continuar*/
        if(jTDescrip.getText().replace(" ", "").trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un producto válido.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTProd.grabFocus();
            return;
        }
        
        /*Si por lo menos no se selecciono una talla o color entonces*/
        if(jTDescripTall.getText().compareTo("")==0 && jTDescripColo.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/
            jComTall.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos talla o color.", "Tallas y Colores", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo de edición y regresa*/
            jComTall.grabFocus();
            return;
        }
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Declara variables del base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ;        

        /*Checa si ese producto con ese almacén, talla y color ya existe en la base de datos*/
        try
        {
            sQ = "SELECT prod FROM tallcolo WHERE prod = '" + jTProd.getText().trim() + "' AND alma = '" + jTAlma.getText().trim() + "' AND colo = '" + jComColo.getSelectedItem().toString().trim() + "' AND tall = '" + jComTall.getSelectedItem().toString().trim() + "'";                     
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/
                jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Avisa al usuario*/
                JOptionPane.showMessageDialog(null, "Este producto: " + jTProd.getText().replace(" ", "").trim() + " ya existe para esa talla y color.", "Registro Existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Pon el foco del teclado en el campo del código del almacén y regresa*/
                jTProd.grabFocus();
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
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres agregar esta talla y color para el producto correspondiente?", "Tallas y Colores", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);          
            return;
        }       

        /*Guarda los datos en la base de datos*/
        try
        {
            sQ = "INSERT INTO tallcolo(    prod,                                                                 tall,                                                              alma,                                         colo,                                                          exist,  sucu,                 nocaj,                 estac) " +
                          "VALUES('" +    jTProd.getText().replace(" ", "").trim().replace("'", "''") + "','" +   jComTall.getSelectedItem().toString().replace("'", "''") + "','" + jTAlma.getText().replace("'", "''") + "','" + jComColo.getSelectedItem().toString().replace("'", "''") + "', 0, '" + Star.sSucu + "', '" + Star.sNoCaj + "', '" + Login.sUsrG + "')";
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
        Object nu[]             = {iContFi, jTProd.getText().replace(" ", "").trim(), jTDescrip.getText(), jComTall.getSelectedItem().toString(), jTDescripTall.getText(), jComColo.getSelectedItem().toString(), jTDescripColo.getText(), "0", jTAlma.getText()};
        te.addRow(nu);

        /*Aumenta en uno el contador de las filas*/
        ++iContFi;

        /*Pon el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();

        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Producto: " + jTProd.getText() + " agregado con éxito para talla y color.", "Exito al agregar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se presiona una tecla en el botón de nueva partida*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando el mouse entra en el botón de borrar partida*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered

        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);

    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse sale del botón de borrar partida*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(Star.colOri);

    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando se presiona el botón de borrar partida*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed

        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una registro de la lista para borrar.", "Borrar Talla y Color", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }

        /*Preguntar al usuario si esta seguro de querer borrar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere borrar el(los) registro(s)?", "Borrar Talla y Color", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
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
            /*Si tiene existencias entonces*/
            if(Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 7).toString())>0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "El producto: " + jTab.getValueAt(iSel[x], 1).toString() + " para la talla: " + jTab.getValueAt(iSel[x], 3).toString() + " y color: " + jTab.getValueAt(iSel[x], 5).toString() + " tiene existencias y no se puede eliminar.", "Tallas y Colores", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }

            /*Declara variables del base de datos*/
            Statement   st;        
            String      sQ;        

            /*Borra el registro*/
            try
            {
                sQ = "DELETE FROM tallcolo WHERE prod = '" + jTab.getValueAt(iSel[x], 1).toString().replace("'", "''") + "' AND tall = '" + jTab.getValueAt(iSel[x], 3).toString().replace("'", "''") + "' AND colo = '" + jTab.getValueAt(iSel[x], 5).toString().replace("'", "''") + "'";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                        
            }

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
            JOptionPane.showMessageDialog(null, "Registro(s) borrado(s) con éxito.", "Tallas y Colores", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar partida*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se pierde el foco del teclado en el combo de las tallas*/
    private void jComTallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComTallFocusLost
        
        /*Coloca el borde negro si tiene datos*/
        if(jComTall.getSelectedItem().toString().compareTo("")!=0)
            jComTall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComTallFocusLost

    
    /*Cuando el mouse entra en el botón de búscar*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered

        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);

        /*Guardar el borde original que tiene el botón*/
        bBordOri    = jBBusc.getBorder();

        /*Aumenta el grosor del botón*/
        jBBusc.setBorder(new javax.swing.border.LineBorder(java.awt.Color.GREEN, 3));

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
        
        /*Borra todos los item en la tabla de almacenes*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);

        /*Resetea el contador de filas*/
        iContFi              = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Obtiene de la base de datos todos los almacenes*/
        try
        {
            sQ = "SELECT colos.DESCRIP, tall.DESCRIP, prods.DESCRIP, tallcolo.PROD, tallcolo.TALL, tallcolo.COLO, tallcolo.EXIST, tallcolo.ALMA FROM tallcolo LEFT OUTER JOIN colos ON colos.COD = tallcolo.COLO LEFT OUTER JOIN tall ON tall.COD = tallcolo.TALL LEFT OUTER JOIN prods ON prods.PROD = tallcolo.PROD WHERE tallcolo.PROD LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR tallcolo.TALL LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR tallcolo.ALMA LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR tallcolo.COLO LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR tallcolo.EXIST LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') GROUP BY colos.DESCRIP, tall.DESCRIP, prod, tall, alma, colo, exist";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalos en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("prod"), rs.getString("prods.DESCRIP"), rs.getString("tall"), rs.getString("tall.DESCRIP"), rs.getString("colo"), rs.getString("colos.DESCRIP"), rs.getString("exist"), rs.getString("alma")};
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

    
    /*Cuando se presiona una tecal en el botón de búscar*/
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

    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed

        /*Función para cargar nuevamente todos los registros en la tabla*/
        vCarg();

    }//GEN-LAST:event_jBMosTActionPerformed

    
    /*Cuando se presiona una tecal en el botón de mostrar todo*/
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

    
    /*Cundo se pierde el foco del teclado en el campo de búscar*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Coloca el cursor al principio del control*/
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
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();        
        /*Else if se F4 entonces presiona el botón de mostrar todos los registrod*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();        
        /*Else if se F3 entonces presiona el botón de búscar todos los registrod*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBProd;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JComboBox jComColo;
    private javax.swing.JComboBox jComTall;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTAlma;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTDescrip;
    private javax.swing.JTextField jTDescripColo;
    private javax.swing.JTextField jTDescripTall;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
