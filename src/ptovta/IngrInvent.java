//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static ptovta.Princip.bIdle;
import vis.VisIngrInvent;



/*Clase para controlar los ingres al almacén*/
public class IngrInvent extends javax.swing.JFrame 
{
    /*Declara variables de instancia*/    
    private int             iContFi;

    /*Variable que almacena el borde original del botón de búscar*/
    private Border          bBordOri;
    
    /*Contador para modificar tabla*/
    private int             iContCellEd;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;
    
    /*Contiene el color original del botón*/
    private java.awt.Color   colOri;
    
    /*Declara variables originales*/
    private String          sTipOri;
    private String          sProdOri;
    private String          sUnidOri;
    private String          sAlmaOri;
    private String          sCantOri;
    private String          sConcepOri;
    private String          sFIngOri;
    private String          sSucOri;
    private String          sCajOri;
    private String          sEstacOri;
    private String          sNomEstacOri;
    
    //CAMBIO ALAN
    private double iCantTot;
    private double iCantAgr;
    
    
    
    
    /*Constructor sin argumentos*/
    public IngrInvent() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();

        /*Esconde el control del comentario de la serie*/
        jTComenSer.setVisible(false);
        //CAMBIO ALAN
        jTSerProd.setVisible(false);
        
        /*Inicia el control de fecha con la fecha del día de hoy*/
        java.util.Date dtDat    = new java.util.Date();
        jDFCadu.setDate(dtDat);
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el tamaño de las columnas de la tabla*/
        jTab.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(7).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(11).setPreferredWidth(200);
        
        /*Listener para el combobox de almacenes*/
        jComAlma.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {                
                //Abre la base de datos
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                //Carga todos los almacenes en el combo
                if(Star.iCargAlmaCom(con, jComAlma)==-1)
                    return;

                //Cierra la base de datos
                Star.iCierrBas(con);                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Crea el grupo de los radio buttons*/
        javax.swing.ButtonGroup bG = new javax.swing.ButtonGroup();
        bG.add(jREnt);
        bG.add(jRSal);       
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Entradas y salidas de inventario, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en 1*/
        iContFi      = 1;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nomre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de ingres*/        
        jTab.getColumnModel().getColumn(6).setPreferredWidth(110);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Obtiene todos los ingres de la base de datos y cargalos en la tabla*/
        CargIngres();

        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para cuando se cambia de selección en la tabla*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtén la propiedad que a sucedio en el control*/
                String pr = event.getPropertyName();                                
                                
                /*Obtiene la fila seleccionada*/                
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pr)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sTipOri         = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sProdOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sUnidOri        = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sAlmaOri        = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sCantOri        = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sConcepOri      = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sFIngOri        = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sSucOri         = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sCajOri         = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sEstacOri       = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sNomEstacOri    = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();                        
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sTipOri,        jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sProdOri,       jTab.getSelectedRow(), 2);
                        jTab.setValueAt(sUnidOri,       jTab.getSelectedRow(), 3);
                        jTab.setValueAt(sAlmaOri,       jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sCantOri,       jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sConcepOri,     jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sFIngOri,       jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sSucOri,        jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sCajOri,        jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sEstacOri,      jTab.getSelectedRow(), 10);                        
                        jTab.setValueAt(sNomEstacOri,   jTab.getSelectedRow(), 11);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);

        /*Listener para el combobox de unidades*/
        jComUnid.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                //Obtiene todas las unidades y cargalas en el combo
                if(Star.iCargUnidCom(con, jComUnid)==-1)
                    return;
                
                //Cierra la base de datos
                Star.iCierrBas(con);                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Carga todos los almacenes en el combo
        if(Star.iCargAlmaCom(con, jComAlma)==-1)
            return;
        
        //Carga todas las tallas en el combo
        if(Star.iCargTallCom(con, jComTall)==-1)
            return;
               
        //Trae todos los colores y cargalos en el combo
        if(Star.iCargColoCom(con, jComColo)==-1)
            return;
        
        //Obtiene todas las unidades y cargalas en el combo
        if(Star.iCargUnidCom(con, jComUnid)==-1)
            return;

        //Cierra la base de datos
        Star.iCierrBas(con);
               
    }/*Fin de public Traspasos() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMosT = new javax.swing.JButton();
        jTProd = new javax.swing.JTextField();
        jBProd = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTDescrip = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTCant = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTConcep = new javax.swing.JTextField();
        jBBuscConcep = new javax.swing.JButton();
        jTDescripConcep = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTExist = new javax.swing.JTextField();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jComUnid = new javax.swing.JComboBox();
        jTUnid = new javax.swing.JTextField();
        jRSal = new javax.swing.JRadioButton();
        jREnt = new javax.swing.JRadioButton();
        jBPrec = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jComTall = new javax.swing.JComboBox();
        jTTall = new javax.swing.JTextField();
        jComColo = new javax.swing.JComboBox();
        jTColo = new javax.swing.JTextField();
        jComAlma = new javax.swing.JComboBox();
        jTDescripAlma = new javax.swing.JTextField();
        jBExisAlma = new javax.swing.JButton();
        jTCost = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTLot = new javax.swing.JTextField();
        jDFCadu = new com.toedter.calendar.JDateChooser();
        jTPedimen = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTComenSer = new javax.swing.JTextField();
        jTSerProd = new javax.swing.JTextField();
        jBCarSer = new javax.swing.JButton();
        jTCarSer = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 200, 110, -1));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/carg.png"))); // NOI18N
        jBNew.setText("Aceptar");
        jBNew.setToolTipText("Nuevo Ingreso/Salida (Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jBSal);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 170, 110, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Ingresos:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 160, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "E/S", "Cod. Producto", "Unidad", "Cod. Almacén", "Cantidad", "Cod. Concepto", "Fecha", "Sucursal", "Caja", "Usuario", "Nombre Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 820, 250));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 540, 20));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setNextFocusableComponent(jBNew);
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 420, 140, 20));

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
        });
        jP1.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 120, 20));

        jBProd.setBackground(new java.awt.Color(255, 255, 255));
        jBProd.setText("...");
        jBProd.setToolTipText("Buscar Producto(s)");
        jBProd.setName(""); // NOI18N
        jBProd.setNextFocusableComponent(jComAlma);
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
        jP1.add(jBProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 30, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Costo entrada:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 70, 140, -1));

        jTDescrip.setEditable(false);
        jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        });
        jP1.add(jTDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 230, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Almacén:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 80, -1));

        jTCant.setText("1");
        jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCant.setNextFocusableComponent(jTConcep);
        jTCant.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCantFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCantFocusLost(evt);
            }
        });
        jTCant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCantKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCantKeyTyped(evt);
            }
        });
        jP1.add(jTCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, 70, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("*Cantidad:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 80, -1));

        jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTConcep.setNextFocusableComponent(jBBuscConcep);
        jTConcep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTConcepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTConcepFocusLost(evt);
            }
        });
        jTConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTConcepKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTConcepKeyTyped(evt);
            }
        });
        jP1.add(jTConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, 110, 20));

        jBBuscConcep.setBackground(new java.awt.Color(255, 255, 255));
        jBBuscConcep.setText("...");
        jBBuscConcep.setToolTipText("Buscar Producto(s)");
        jBBuscConcep.setNextFocusableComponent(jTCost);
        jBBuscConcep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBuscConcepMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBuscConcepMouseExited(evt);
            }
        });
        jBBuscConcep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscConcepActionPerformed(evt);
            }
        });
        jBBuscConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBuscConcepKeyPressed(evt);
            }
        });
        jP1.add(jBBuscConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 30, 30, 20));

        jTDescripConcep.setEditable(false);
        jTDescripConcep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripConcep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripConcepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripConcepFocusLost(evt);
            }
        });
        jTDescripConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripConcepKeyPressed(evt);
            }
        });
        jP1.add(jTDescripConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 50, 140, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("*Cod. Producto:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        jTExist.setEditable(false);
        jTExist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTExist.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTExistFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTExistFocusLost(evt);
            }
        });
        jTExist.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTExistKeyPressed(evt);
            }
        });
        jP1.add(jTExist, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 80, 20));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 430, 110, 30));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 152, 130, 18));

        jComUnid.setNextFocusableComponent(jTUnid);
        jComUnid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComUnidFocusLost(evt);
            }
        });
        jComUnid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComUnidActionPerformed(evt);
            }
        });
        jComUnid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComUnidKeyPressed(evt);
            }
        });
        jP1.add(jComUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 90, 20));

        jTUnid.setEditable(false);
        jTUnid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUnid.setNextFocusableComponent(jComTall);
        jTUnid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUnidFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUnidFocusLost(evt);
            }
        });
        jP1.add(jTUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 80, 20));

        jRSal.setBackground(new java.awt.Color(255, 255, 255));
        jRSal.setText("Salida");
        jRSal.setNextFocusableComponent(jBTod);
        jRSal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRSalKeyPressed(evt);
            }
        });
        jP1.add(jRSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 50, 90, -1));

        jREnt.setBackground(new java.awt.Color(255, 255, 255));
        jREnt.setSelected(true);
        jREnt.setText("Entrada");
        jREnt.setNextFocusableComponent(jRSal);
        jREnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jREntKeyPressed(evt);
            }
        });
        jP1.add(jREnt, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 30, 90, -1));

        jBPrec.setBackground(new java.awt.Color(255, 255, 255));
        jBPrec.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBPrec.setText("$");
        jBPrec.setToolTipText("Lista de Precios y Costeos");
        jBPrec.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jBPrec.setNextFocusableComponent(jTLot);
        jBPrec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBPrecMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBPrecMouseExited(evt);
            }
        });
        jBPrec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPrecActionPerformed(evt);
            }
        });
        jBPrec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPrecKeyPressed(evt);
            }
        });
        jP1.add(jBPrec, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 70, 20));

        jLabel16.setText("Talla:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 60, -1));

        jLabel15.setText("Color:");
        jP1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 60, -1));

        jComTall.setNextFocusableComponent(jTTall);
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
        jP1.add(jComTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 90, 20));

        jTTall.setEditable(false);
        jTTall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTall.setNextFocusableComponent(jComColo);
        jTTall.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTallFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTallFocusLost(evt);
            }
        });
        jTTall.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTallKeyPressed(evt);
            }
        });
        jP1.add(jTTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 80, 20));

        jComColo.setNextFocusableComponent(jTColo);
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
        jP1.add(jComColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 90, 20));

        jTColo.setEditable(false);
        jTColo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTColo.setNextFocusableComponent(jBPrec);
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
        });
        jP1.add(jTColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 80, 20));

        jComAlma.setNextFocusableComponent(jTDescripAlma);
        jComAlma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComAlmaFocusLost(evt);
            }
        });
        jComAlma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComAlmaActionPerformed(evt);
            }
        });
        jComAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComAlmaKeyPressed(evt);
            }
        });
        jP1.add(jComAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 80, 20));

        jTDescripAlma.setEditable(false);
        jTDescripAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripAlma.setNextFocusableComponent(jComUnid);
        jTDescripAlma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripAlmaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripAlmaFocusLost(evt);
            }
        });
        jTDescripAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripAlmaKeyPressed(evt);
            }
        });
        jP1.add(jTDescripAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 100, 20));

        jBExisAlma.setBackground(new java.awt.Color(0, 153, 153));
        jBExisAlma.setToolTipText("Existencias por almacén del producto");
        jBExisAlma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExisAlmaActionPerformed(evt);
            }
        });
        jBExisAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBExisAlmaKeyPressed(evt);
            }
        });
        jP1.add(jBExisAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 10, 20));

        jTCost.setText("$0.00");
        jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCost.setNextFocusableComponent(jREnt);
        jTCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCostFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCostFocusLost(evt);
            }
        });
        jTCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCostKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCostKeyTyped(evt);
            }
        });
        jP1.add(jTCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 140, 20));

        jLabel17.setText("Unidad:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 60, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("*Concepto:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 100, -1));

        jTLot.setToolTipText("Lote");
        jTLot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLot.setNextFocusableComponent(jTPedimen);
        jTLot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLotFocusLost(evt);
            }
        });
        jTLot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLotKeyPressed(evt);
            }
        });
        jP1.add(jTLot, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 90, 20));

        jDFCadu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jDFCadu.setNextFocusableComponent(jTCant);
        jDFCadu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFCaduKeyPressed(evt);
            }
        });
        jP1.add(jDFCadu, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 190, 20));

        jTPedimen.setToolTipText("Pedimento");
        jTPedimen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPedimen.setNextFocusableComponent(jDFCadu);
        jTPedimen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPedimenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPedimenFocusLost(evt);
            }
        });
        jTPedimen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPedimenKeyPressed(evt);
            }
        });
        jP1.add(jTPedimen, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 100, 20));

        jLabel11.setText("Existencia general:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 150, -1));

        jLabel12.setText("Lote:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 90, -1));

        jLabel13.setText("Pedimento:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, 90, -1));

        jLabel14.setText("Importar números de serie:");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 190, 20));

        jTComenSer.setEditable(false);
        jTComenSer.setFocusable(false);
        jP1.add(jTComenSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 120, 10, -1));

        jTSerProd.setEditable(false);
        jTSerProd.setFocusable(false);
        jP1.add(jTSerProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 120, 10, -1));

        jBCarSer.setBackground(new java.awt.Color(255, 255, 255));
        jBCarSer.setText("..");
        jBCarSer.setToolTipText("Buscar archivo EXCEL para series con dos columnas");
        jBCarSer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCarSerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCarSerMouseExited(evt);
            }
        });
        jBCarSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCarSerActionPerformed(evt);
            }
        });
        jBCarSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCarSerKeyPressed(evt);
            }
        });
        jP1.add(jBCarSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 30, 20));

        jTCarSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCarSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCarSerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCarSerFocusLost(evt);
            }
        });
        jTCarSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCarSerKeyPressed(evt);
            }
        });
        jP1.add(jTCarSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 160, 20));

        jLabel18.setText("Fecha caducidad:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, 190, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todos los ingresos de la base de datos y cargalos en la tabla*/
    private void CargIngres()
    {                 
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel tm = (DefaultTableModel)jTab.getModel();                    
     
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todas los ingresos de la base de datos y cargalos en la tabla*/
        try
        {
            sQ = "SELECT CASE WHEN ingres.ENTSAL = 0 THEN 'E' ELSE 'S' END AS entsal, estacs.NOM, ingres.PROD, ingres.UNID, ingres.ALMA, ingres.CONCEP, ingres.CANT, ingres.FALT, ingres.SUCU, ingres.NOCAJ, ingres.ESTAC  FROM ingres LEFT OUTER JOIN estacs ON estacs.ESTAC = ingres.ESTAC ";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                    
                //Obtiene la cantidad visual equivalente correcta
                String sCant    = Star.sCantVisuaGKT(rs.getString("ingres.UNID"), rs.getString("ingres.CANT"));
                                
                /*Agregalo a la tabla*/
                Object nu[]= {iContFi, rs.getString("entsal"), rs.getString("ingres.PROD"), rs.getString("ingres.UNID"), rs.getString("ingres.ALMA"), sCant, rs.getString("ingres.CONCEP"), rs.getString("ingres.FALT"), rs.getString("ingres.SUCU"), rs.getString("ingres.NOCAJ"), rs.getString("ingres.ESTAC"), rs.getString("estacs.NOM")};
                tm.addRow(nu);
                
                /*Aumentar en uno el contador de fila*/
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
        
    }/*Fin de private void CargIngres()*/
    
         
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

    
    /*Cuando se presiona una tecla en el botón de ingresar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona una  tecla en la tabla de ingres*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    //CAMBIO ALAN    
    /*Cuando se presiona el botón de ingresar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
        
        /*Si hay cadena vacia en el campo del código del producto no puede continuar*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código del producto esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTProd.grabFocus();            
            return;            
        }                
        
        /*Si el código del almacén es cadena vacia entonces*/
        if(jComAlma.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComAlma.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de almacén esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jComAlma.grabFocus();                        
            return;
        }

        /*Si el código de la unidad es cadena vacia entonces*/
        if(jComUnid.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComUnid.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Tienes que indicar una unidad.", "Unidad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jComUnid.grabFocus();                        
            return;
        }                

        /*Si hay cadena vacia en la cantidad no puede continuar*/
        if(jTCant.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de cantidad esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCant.grabFocus();                        
            return;            
        }                       

        //Obtiene la existencia del producto por almacén
        double dExis1   = Star.dExisProd(null, jComAlma.getSelectedItem().toString().trim(), jTProd.getText().trim());
        
        //Si hubo error entonces regresa
        if(dExis1==-1)
            return;
        
        //Si el movimiento es de salida entonces
        if(jRSal.isSelected())
        {            
            //Si la existencia es negativa entonces
            if(dExis1<=0)
            {                                
                //Obtiene la configuración para saber si se pueden hacer salidas por sin existencias
                int iRes    = Star.iGetConfGral("esexitmov");
                
                //Si hubo error regresa
                if(iRes==-1)
                    return;
                
                //Si la configuración es que no se permita salidas sin existencias entonces
                if(iRes==1)
                {
                    //Mensajea y regresa
                    JOptionPane.showMessageDialog(null, "No esta permitido hacer salidas sin existencias.", "Salida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                    return;
                }                    
            }
        }                                    
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Comprueba si la unidad tomada es la que tiene el producto asignada*/        
        String sSiUnid = Star.sEsUnidProd(con, jTProd.getText().trim(), jComUnid.getSelectedItem().toString().trim());        
        
        /*Si hubo error entonces regresa*/
        if(sSiUnid==null)
            return;
        
        /*Obtiene la unidad del producto*/        
        String sUnidProd = Star.sGetUnidProd(con, jTProd.getText().trim());        
        
        /*Si hubo error entonces regresa*/
        if(sUnidProd==null)
            return;
        
        /*Si la unidad no es la misma que tiene el producto entonces*/
        if(sSiUnid.compareTo("0")==0)
        {
            /*Comprueba si la unidad que se quiere usar es alguna unidad base de la unidad del producto*/
            if(!Star.bEsUnidBas(sUnidProd, jComUnid.getSelectedItem().toString().trim()))
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La unidad que se quiere manejar: " + jComUnid.getSelectedItem().toString().trim() + " no es base de la unidad: " + sUnidProd + " original del producto y no se puede realizar el movimiento", "Unidades", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;                
            }            
        }

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Comprueba si el código del producto existe en la base de datos*/        
        String sKit;
        try
        {
            sQ = "SELECT prod, compue FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces si existe el producto*/
            if(!rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El código del producto: " + jTProd.getText() + " no existe.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Pon el foco del teclado en el campo de edición y regresa*/
                jTProd.grabFocus();               
                return;                                
            }
            /*Else si hay datos entonces obtiene el resultado*/
            else
                sKit = rs.getString("compue");                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }          
        
        //Se crean variables locales
        double iCont=1;
        String sCanEnt= jTCant.getText().trim();
        boolean bNesSer= false;
        
        //cambio alan
        //Si el producto necesita a fuerzas serie entonces
        if(Star.iProdSolSer(con, jTProd.getText().trim())==1)
        {
            iCont=Double.parseDouble(jTCant.getText().trim());
            sCanEnt="1"; 
            bNesSer = true;
        }
        
        /*Si el producto es un kit entonces*/
        if(sKit.compareTo("1")==0)
        {
            //Obtiene si el producto tiene componentes
            double dRes     = Star.dGetCompsProd(con, jTProd.getText().trim());
            
            //Si hubo error entonces regresa
            if(dRes==-1)
                return;

            //Si tiene componentes entonces coloca la bandera
            boolean bSiHay  = false;
            if(dRes>0)
                bSiHay      = true;
                        
            /*Si no tiene componentes entonces*/
            if(!bSiHay)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No tiene componentes este kit y no se puede realizar el movimiento.", "Ingresar/Sacar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
                /*Coloca el foco del teclado en el campo del código del producto y regresa*/
                jTProd.grabFocus();                                
                return;
            }        
            
        }/*Fin de if(sKit.compareTo("1")==0)*/	                                            
        
        /*Si hay cadena vacia en el campo del código del concepto entonces*/
        if(jTConcep.getText().compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código del concepto esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTConcep.grabFocus();                        
            return;            
        }   
        
        //Comprueba si el concepto eiste en la base de datos
        int iRes    = Star.iExiste(con, jTConcep.getText().trim(), "conceps", "concep");
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el concepto existe entonces
        if(iRes==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código del concepto: " + jTConcep.getText().trim() + " no existe.", "Concepto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTConcep.grabFocus();                
            return;                                
        }
                
        /*Si va a ser entrada entonces*/
        if(jREnt.isSelected())
        {                        
            /*Si el costo es 0 entonces*/
            if(Double.parseDouble(jTCost.getText().trim().replace("$", "").replace(",", ""))==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El campo del costo es $0.00 y para entradas se necesita específicar un costo.", "Ingreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Pon el foco del teclado en el campo de edición y regresa*/
                jTCost.grabFocus();                        
                return;
            }        
            
        }/*Fin de if(jREnt.isSelected())*/
                
        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        iRes        = JOptionPane.showOptionDialog(this, "¿Seguro que quieres hacer la Entrada/Salida?", "Entrada/Salida inventario", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }
        
        //Cambio alan
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        //Contadores para ver cuantos elementos faltan de agregar
        iCantAgr = 1;
        iCantTot = Double.parseDouble(jTCant.getText().trim());

        
        //Si no hay archivo entonces o es sin serie
        if((jTCarSer.getText().trim().compareTo("")==0 || bNesSer==false))
        {
            //Se utiliza la funcion de agregar productos si tiene serie se hara el numero de veces dependiendo de la cantidad
            for(double i = 0; i < iCont ; i++)
                vAgreProd(sCanEnt,sKit,"","","0");
        }
        else
            vSerDoc(jTCarSer.getText().trim());
        
        //Se limpia el campo de cargar programa
        jTCarSer.setText("");

    }//GEN-LAST:event_jBNewActionPerformed
        
    
    //cambio alan
    //Cuando se va a analizar un documento con series
    private void vSerDoc(String sRut)
    {    
        /*Crea la instancia hacia el archivo a importar*/
        FileInputStream file;
        try
        {
            file    =  new FileInputStream(new File(sRut));
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
            return;                                                    
        }                    

        /*Instancia el objeto de excel*/
        XSSFWorkbook wkbok;
        try
        {
            wkbok   = new XSSFWorkbook(file);
        }
        catch(Exception expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
            return;                                                    
        }   
                
        //Se cierra el archivo
        try
        {
            file.close();
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
            return;                                                    
        }                

        /*Obtiene la primera hoja del libro*/
        XSSFSheet sheet         = wkbok.getSheetAt(0);

        /*Contador de fila*/
        int iConta                  = 1;  
        int iContRep                = 0;

        /*Inicializa el contador de la celda por cada fila*/
        int iContCell               = 1;

        /*Recorre todas las hojas de excel*/
        Iterator<Row> rowIt     = sheet.iterator();
        while(rowIt.hasNext())
        {                    
            /*Recorre todas columnas del archivo*/
            Row row             = rowIt.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            
            /*Variable para leer las celdas*/
            String sIn;      

            /*Contiene el código del producto y la serie*/
            String sSer  = "", sComen="";

            /*Recorre todas las celdas de la fila*/
            while(cellIterator.hasNext())
            {
                /*Obtiene el objeto de la celda*/
                Cell cell       = cellIterator.next();                 

                /*Determina el tipo de celda que es*/
                switch(cell.getCellType())
                {
                    /*En caso de que sea de tipo string entonces*/
                    case Cell.CELL_TYPE_STRING:

                        /*Obtiene el valor de la celda*/
                        sIn         = cell.getStringCellValue();                                                            

                        /*Si es la celda 1 entonces*/
                        if(iContCell==1)
                        {
                            /*Si es el fin del archivo entonces*/
                            if(sIn.compareTo("FINARCHIVO")==0)
                            {

                                /*Mensajea y regresa*/
                                JOptionPane.showMessageDialog(null, "Exito en la importación de " + (iConta - 1) + " series.", "Series", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                     
                                return;

                            }
                            /*Else no es el final del archivo entonces*/
                            else
                                /*Guarda la serie*/
                                sSer       = cell.getStringCellValue();

                        }/*Fin de if(iContCell==1)*/
                        
                        /*Si es la celda 1 entonces*/
                        if(iContCell==2)
                                /*Guarda la serie*/
                                sComen       = cell.getStringCellValue();

                        break;

                    /*En caso de que sea de tipo número entonces*/
                    case Cell.CELL_TYPE_NUMERIC:        
                        
                        //Se le da formato por si es numerico los campos puedan ser leidos
                        java.text.DecimalFormat df = new java.text.DecimalFormat("##################.##################");
                        
                        /*Si es la celda 1 entonces*/
                        if(iContCell==1)
                            /*Guarda la serie*/
                            sSer       = df.format(cell.getNumericCellValue());
                            
                        /*Si es la celda 2 entonces*/
                        if(iContCell==2)
                            //CAMBIO ALAN
                            /*Guarda la serie*/
                            sComen       = df.format(cell.getNumericCellValue());
                        
                        break;

                }/*Fin de switch(cell.getCellType())*/

                /*Aumenta en uno el contador de las celdas*/
                ++iContCell;

            }/*Fin de while(cellIterator.hasNext())*/

            /*Aumenta en uno el contador de las filas*/
            ++iConta;

            /*Resetea el contador de celdas*/
            iContCell   = 1;
            
            //Abre la base de datos
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Se envia la serie para validar y regresa la nueva serie con mayúsculas
            sSer=Star.sValSer(sSer);
            
            //Si no es valioda la serie
            if(sSer == null)
            {
                //Se suma al contador de repetidos
                iContRep++;
                continue;
            }
            
            //Si es una salida
            if(jRSal.isSelected())
            {
                //Se toma el valor que tiene las existencias de ese producto con esa serie por almacen
                String sExist;

                sExist = Star.iCantSer(sSer,con,jTProd.getText().trim(),jComAlma.getSelectedItem().toString());
                
                if(sExist==null)
                    return;
                else if(sExist.compareTo("no existe")==0)
                {
                    //Se suma al contador de repetidos
                    iContRep++;
                    continue;
                }
                else if(Double.parseDouble(sExist)<=0)
                {         
                    //cambio alan
                    //Obtiene la configuración para saber si se pueden hacer salidas por sin existencias
                    int iRes    = Star.iGetConfGral("esexitmov");

                    //Si hubo error regresa
                    if(iRes==-1)
                        return;

                    //Si la configuración es que no se permita salidas sin existencias entonces
                    if(iRes==1)
                    {
                        //Se suma al contador de repetidos
                        iContRep++;
                        continue;

                    }

                }

            }//Fin if(jRSal.isSelected())
            
            //Declara variables de la base de datos    
            Statement   st;
            ResultSet   rs;        
            String      sQ;

            /*Comprueba si el código del producto existe en la base de datos*/        
            String sKit;
            try
            {
                sQ = "SELECT prod, compue FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces si existe el producto*/
                if(!rs.next())
                {
                    //Se suma al contador de repetidos
                    iContRep++;
                    continue;                               
                }
                /*Else si hay datos entonces obtiene el resultado*/
                else
                    sKit = rs.getString("compue");
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                        
            }          

            /*Si el producto es un kit entonces*/
            if(sKit.compareTo("1")==0)
            {
                //Obtiene si el producto tiene componentes
                double dRes     = Star.dGetCompsProd(con, jTProd.getText().trim());

                //Si hubo error entonces regresa
                if(dRes==-1)
                    return;

                //Si tiene componentes entonces coloca la bandera
                boolean bSiHay  = false;
                if(dRes>0)
                    bSiHay      = true;

                /*Si no tiene componentes entonces*/
                if(!bSiHay)
                {
                    //Se suma al contador de repetidos
                    iContRep++;
                    continue;
                }        

            }/*Fin de if(sKit.compareTo("1")==0)*/	
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            if(sSer.trim().compareTo("") != 0)
                vAgreProd("1",sKit,sSer,sComen,"1");

        }/*Fin de while(rowIt.hasNext())*/ 
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Se almacenaron " + (iConta-1-iContRep) + " series y hubo "+iContRep+" error(s)", "Éxito al leer archivo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
    
    }//Final private int iContSerDoc(String sRut)
    
    
    //cambio alan
    //Se agrega el producto
    private void vAgreProd(String sCanEnt,String sKit,String sCamp1, String sCamp2, String sMens) 
    {
          
        //Declara variables locales
        String sOper    = "+";
        String sTipE    = "0";        
                
        /*Determina si sera entrada o salida y la operación*/
        if(jRSal.isSelected())
        {
            sTipE       = "1";    
            sOper       = "-";
        }
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                
        //Se crean variables
        String sSer   = "";
        String sComen = "";
        String sExist;
        
        //Si el producto necesita a fuerzas serie entonces
        if(Star.iProdSolSer(con, jTProd.getText().trim())==1)
        {
            boolean bSer = true;
            
            while(bSer)
            {
                
                //Si no hay archivo entonces
                if(jTCarSer.getText().trim().compareTo("")==0)
                {
                    //Seleccion para el selser si sera una entrada o una salida
                    int iEdit=0;
                    if(jRSal.isSelected())
                        iEdit=1;

                    /*Pidele al usuario que ingrese serie y comentario*/   
                    SelSer lo     = new SelSer(jTSerProd,jTComenSer, iCantAgr + "-" + iCantTot,jTProd.getText().trim(),jComAlma.getSelectedItem().toString().trim(),iEdit);                    
                    lo.setModal     (true);
                    lo.setVisible   (true); 

                    //Se obtienen los valores
                    sSer=jTSerProd.getText();
                    sComen =jTComenSer.getText();

                    if(sSer.trim().compareTo("-1")==0)
                        sSer=null;

                    /*Si es nula la serie entonces regresa*/
                    if(sSer==null)
                    {
                        //Se reduce en uno el contador de total
                        iCantTot--;
                        bSer=false;
                        continue;
                    }

                    /*Si es cadena vacia entonces*/
                    if(sSer.compareTo("")==0)
                    {                    
                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "Ingresa una serie válida.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        continue;
                    }

                    //Si es una salida
                    if(!jRSal.isSelected())
                    {
                        //Se revisa si se puede repetir series
                        if(Star.iConfSer(con)==0)
                        {

                            //Se comprueba si la serie ya existe
                            if(Star.iSerRep(con, sSer)==1)
                            {
                                /*Mensajea*/
                                JOptionPane.showMessageDialog(null, "La serie " + sSer + " ya existe.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                                continue;
                            }

                        }//Fin if(Star.iConfSer(con)==0)

                    }//Fin if(!jRSal.isSelected())
                    else
                    {

                        //Se toma el valor que tiene las existencias de ese producto con esa serie por almacen
                        sExist=Star.iCantSer(sSer,con,jTProd.getText().trim(),jComAlma.getSelectedItem().toString());
                        if(sExist==null)
                            return;
                        else if(sExist.compareTo("no existe")==0)
                        {
                            /*Mensajea*/
                            JOptionPane.showMessageDialog(null, "La serie " + sSer + " no existe en este almacen.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                            continue;
                        }
                        else if(Double.parseDouble(sExist)<=0)
                        {         
                            //camabio alan
                            //Obtiene la configuración para saber si se pueden hacer salidas por sin existencias
                            int iRes    = Star.iGetConfGral("esexitmov");

                            //Si hubo error regresa
                            if(iRes==-1)
                                return;

                            //Si la configuración es que no se permita salidas sin existencias entonces
                            if(iRes==1)
                            {

                                //Mensajea
                                JOptionPane.showMessageDialog(null, "No esta permitido hacer salidas sin existencias.", "Salida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                                continue;

                            }

                        }

                    }

                    //Si el comentario es nulo se pone en blanco
                    if(sComen==null)
                        sComen="";
                }
                else
                {

                    //Se obtienen los valores
                    sSer    = sCamp1;
                    sComen  = sCamp2;

                }
                
                //Se aumenta el contador de agregados
                iCantAgr++;
                
                //Se cambia el flag indicando que esta bien la serie
                bSer=false;
            
            }//Fin while(bSer)
            //Cambio alan
            /*Si la serie es nula entonces regresa*/
            if(sSer==null)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
            
                //Mensajea
                JOptionPane.showMessageDialog(null, "El producto solicita número de serie.", "Número serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }    
        }
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;
        
        /*La cantidad a manejar originalmente será la que ingreso el usuario*/
        String sCant    = sCanEnt.trim();

        /*Obtiene la cantidad correcta en base a la unidad*/        
        sCant       = Star.sCantUnid(jComUnid.getSelectedItem().toString().trim(), sCant);

        /*Si hay datos en la serie entonces procesa la serie para ls existencias*/
        if(sSer.trim().compareTo("")!=0)        
            Star.vSerPro(con, jTProd.getText().trim(), sCant, sSer.trim(), jComAlma.getSelectedItem().toString().trim(), sComen.trim(), sOper);
        
        /*Si tiene talla o color entonces procesa esto en una función*/
        if(jComTall.getSelectedItem().toString().compareTo("")!=0 || jComColo.getSelectedItem().toString().compareTo("")!=0)                    
            Star.vTallCol(con, sCant, jComAlma.getSelectedItem().toString(), jComTall.getSelectedItem().toString(), jComColo.getSelectedItem().toString(), jTProd.getText(), sOper);                    
        
        /*Si tiene lote o pedimento entonces*/
        if(jTLot.getText().trim().compareTo("")!=0 || jTPedimen.getText().trim().compareTo("")!=0)                        
        {
            /*Obtiene la fecha de caducidad*/
            java.util.Date dtDat    = jDFCadu.getDate();
            SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");
            String sFCadu           = sdf.format(dtDat);
            
            /*Registra la salida o entrada*/
            if(Star.sLotPed(con, jTProd.getText().trim(), sCant, jComAlma.getSelectedItem().toString().trim(), jTLot.getText().trim(), jTPedimen.getText().trim(), sFCadu, sOper)==null)
                return;
        }
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Si es un kit entonces*/
        if(sKit.compareTo("1")==0)
        {                   
            /*Si el movimiento es de entrada entonces*/
            if(sTipE.compareTo("0")==0)
            {                
                /*Agrega en moninven todos los componentes del kit, ademas de actualizar sus existencias generales y existencias por almacén*/                
                if(Star.iInsCompKitInv(con, jTProd.getText().trim(), jComAlma.getSelectedItem().toString(), jTConcep.getText().trim(), jTCost.getText().trim().replace("$", "").replace(",", ""), "0", "", sCant)==-1)
                    return;   
            }   
            /*Else es de salida entonces*/            
            else
            {
                /*Registra todos los componentes del kit en moninv, existencias por almacén y registra la salida de los costos*/                
                if(Star.iInsCompKitInv(con, jTProd.getText().trim(), jComAlma.getSelectedItem().toString(), jTConcep.getText().trim(), jTCost.getText().trim().replace("$", "").replace(",", ""), "1", "", sCant)==-1)
                    return;   
            }                        
            
        }/*Fin de if(sKit.compareTo("1")==0)*/
        /*Else no es un kit entonces*/
        else
        {            
            /*Registra el ingreso/salida al almacén*/
            try 
            {            
                sQ = "INSERT INTO ingres(             prod,                                                 alma,                                                                       concep,                                             cant,                estac,                                      falt,            sucu,                                    nocaj,                                        entsal,          unid) " + 
                                      "VALUES('" +    jTProd.getText().replace("'", "''") + "','" +         jComAlma.getSelectedItem().toString().replace("'", "''").trim() + "','" +   jTConcep.getText().replace("'", "''") + "', " +     sCant + ",'" +       Login.sUsrG.replace("'", "''") + "',        now(), '" +      Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "',  " +     sTipE + ", '" +  jComUnid.getSelectedItem().toString().replace("'", "''") + "')";            
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                        
             }
            
            /*Realiza la modificación de existencia en el almacén*/        
            if(Star.iAfecExisProd(con, jTProd.getText().replace("'", "''").trim(), jComAlma.getSelectedItem().toString().replace("'", "''").trim(), sCant, sOper)==-1)
                return;

            /*Actualiza la existencia general del producto*/
            if(Star.iCalcGralExis(con, jTProd.getText().replace("'", "''").trim())==-1)
                return;

            /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
            if(!Star.vRegMoniInv(con, jTProd.getText().replace("'", "''").trim(), sCant, jTDescrip.getText().replace("'", "''").trim(), jComAlma.getSelectedItem().toString().replace("'", "''").trim(), Login.sUsrG , "", jTConcep.getText().replace("'", "''").trim(), jComUnid.getSelectedItem().toString().replace("'", "''"), "", "", sTipE))                                
                return;                                                                                                                                                                                                                                         

            /*Si es de salida el movimiento entonces*/
            if(sOper.compareTo("-")==0)
            {
                /*Dale seguimiento dependiendo el método de costeo*/
                if(Star.sGetCost(con, jTProd.getText().trim(), sCant)==null)
                    return;
            }

            /*Si el movimiento no es de entreda entonces*/
            if(sOper.compareTo("+")==0)
            {
                /*Obtiene el último ID de moniven*/
                String sId  = "0";
                try
                {
                    sQ = "SELECT id_id FROM moninven ORDER BY id_id DESC LIMIT 1";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces obtiene el resultado*/
                    if(rs.next())
                        sId      = rs.getString("id_id");                                               
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                                                                
                }

                /*Ingresa el costeo*/
                if(Star.iInsCost(con, jTProd.getText().trim(), sCant, jTCost.getText().trim().replace("$", "").replace(",", ""))==-1)
                    return;                             
                                
            }/*Fin de if(sOper.compareTo("+")==0)*/                    
            
        }/*Fin de else*/   
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;

        /*Obtiene el nombre del usuario actual*/
        String sNomb    = "";
        try
        {
            sQ = "SELECT nom FROM estacs WHERE estac = '" + Login.sUsrG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sNomb      = rs.getString("nom");                                               
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
        
        /*Cambia a letra el tipo de movimiento*/        
        if(sTipE.compareTo("1")==0)        
            sTipE   = "S";
        else 
            sTipE   = "E";
        
        /*Obtiene la fecha del día de hoy*/
        DateFormat dtForm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        /*Agrega el registro del ingreso en la tabla*/
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        //CAMBIO ALAN
        Object nu[]             = {iContFi, sTipE, jTProd.getText(), jComUnid.getSelectedItem().toString(), jComAlma.getSelectedItem().toString(), sCanEnt, jTConcep.getText(), dtForm.format(date), Star.sSucu, Star.sNoCaj, Login.sUsrG, sNomb};
        tm.addRow(nu);
        
        /*Aumenta en uno el contador de filas en 1*/
        ++iContFi;
        
        //CAMBIO ALAN
        /*Cambia la exist en el control del producto de donde sale el producto*/
         if(jRSal.isSelected())
             jTExist.setText(Double.toString(Double.parseDouble(jTExist.getText()) - Double.parseDouble(sCanEnt)));
         else
            jTExist.setText(Double.toString(Double.parseDouble(jTExist.getText()) + Double.parseDouble(sCanEnt)));
        
        /*Pon el foco del teclado en el campo de código de producto*/
        jTProd.grabFocus();
        
        /*Mensajea de éxito*/
        //CAMBIO ALAN
        if(sMens.compareTo("0")==0)
            JOptionPane.showMessageDialog(null, "Entrada/Salida del producto: " + jTProd.getText() + " al/del almacén: " + jComAlma.getSelectedItem().toString() + ", la cantidad: " + sCanEnt + " terminada con éxito.", "Exito en Entrada/Salida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
        
    }
    
    
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
        
        /*Obtiene todos los ingres de la base de datos y cargalos en la tabla*/
        CargIngres();
        
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
        
        /*Borra todos los item en la tabla de ingres*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi              = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todos los ingres*/        
        try
        {                  
            sQ = "SELECT estacs.NOM, ingres.PROD, ingres.ALMA, ingres.CONCEP, ingres.CANT, ingres.ALMA, ingres.CANT, ingres.FALT, ingres.SUCU, ingres.NOCAJ, ingres.ESTAC FROM ingres LEFT OUTER JOIN estacs ON estacs.ESTAC = ingres.ESTAC WHERE prod LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR ingres.ALMA LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR estacs.NOM LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR ingres.CONCEP LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR ingres.CANT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR ingres.ALMA LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR ingres.CANT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR ingres.ESTAC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR ingres.FALT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalos a la tabla*/
                DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("ingres.PROD"), rs.getString("ingres.ALMA"), rs.getString("ingres.CONCEP"), rs.getString("ingres.CANT"), rs.getString("ingres.ALMA"), rs.getString("ingres.CANT"), rs.getString("ingres.FALT"), rs.getString("ingres.SUCU"), rs.getString("ingres.NOCAJ"), rs.getString("ingres.ESTAC"), rs.getString("estacs.NOM")};
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

    
    /*Cuando se gana el foco del teclado en el campo del código del producto*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());
        
    }//GEN-LAST:event_jTProdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del código del producto*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTProd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Búsca la información del producto*/
        vBuscProd();
        
    }//GEN-LAST:event_jTProdFocusLost

    
    /*Búsca la información valida del producto*/
    private void vBuscProd()
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
        
        /*Comprueba si el código del producto existe*/        
        try
        {
            sQ = "SELECT prod, descrip, exist, unid, compue FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces*/
            if(!rs.next())
            {                
                /*Resetea los campos*/
                jTDescrip.setText       ("");                            
                jTExist.setText         (""); 
                jComUnid.setSelectedItem("");
                
                /*Habilita el combo de unidades*/
                jComUnid.setEnabled     (true);
                jComUnid.setFocusable   (true);
            }
            /*Else si existe entonces*/
            else
            {                
                //Obtiene la existencia visual equivalente correcta
                String sExist   = Star.sCantVisuaGKT(rs.getString("unid"), rs.getString("exist"));
                
                /*Coloca los valores en los campos*/
                jTDescrip.setText       (rs.getString("descrip"));                            
                jTExist.setText         (sExist); 
                jComUnid.setSelectedItem(rs.getString("unid"));
                
                /*Si el producto es un kit entonces*/
                if(rs.getString("compue").compareTo("1")==0)
                {
                    /*Deshabilita el combo de unidades*/
                    jComUnid.setEnabled     (false);
                    jComUnid.setFocusable   (false);
                }                                    
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }        

        /*Coloca los cursores al principio del control*/
        jTDescrip.setCaretPosition  (0);
        jTExist.setCaretPosition    (0);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vBuscProd()*/
    
    
    /*Cuando se presiona una tecla en el campo del código del producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBProd.doClick();
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

    
    /*Cuando se presiona el botón de buscar*/
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc2 b = new Busc2(this, jTProd.getText(), 1, jTProd, jTDescrip, jTExist);
        b.setVisible(true);
        
        /*Búsca la información del producto*/
        vBuscProd();
        
    }//GEN-LAST:event_jBProdActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProdKeyPressed

    
   
    
   
    /*Cuando se gana el foco del teclado en el campo de descripción 1*/
    private void jTDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescrip.setSelectionStart(0);jTDescrip.setSelectionEnd(jTDescrip.getText().length());
        
    }//GEN-LAST:event_jTDescripFocusGained
    
    
    /*Cuando se presiona una tecla en el campo de descripción 1*/
    private void jTDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jTDescripKeyPressed
    
    
    /*Cuando se gana el foco del teclado en el campo de cant*/
    private void jTCantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCant.setSelectionStart(0);jTCant.setSelectionEnd(jTCant.getText().length());
        
    }//GEN-LAST:event_jTCantFocusGained

    
    /*Cuando se presiona una tecla en el campo de cant*/
    private void jTCantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCantKeyPressed

    
    /*Cuando se tipea una tecla en el campo de cant*/
    private void jTCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyTyped
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTCant.getText().length()> 20)
            jTCant.setText(jTCant.getText().substring(0, 20));        
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();        
        
    }//GEN-LAST:event_jTCantKeyTyped

    
    /*Cuando se pierde el foco del teclado en el campo de cant*/
    private void jTCantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCant.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCant.getText().compareTo("")!=0)
            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTCant.getText().length()> 20)
            jTCant.setText(jTCant.getText().substring(0, 20));
        
        /*Si los caes introducidos no se puede convertir a double colocar cadena vacia*/
        try  
        {  
            double d = Double.parseDouble(jTCant.getText());  
        }  
        catch(NumberFormatException expnNumForm)  
        {  
            jTCant.setText("1");
        }                  
               
    }//GEN-LAST:event_jTCantFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del código del concep*/
    private void jTConcepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTConcepFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTConcep.setSelectionStart(0);jTConcep.setSelectionEnd(jTConcep.getText().length());
        
    }//GEN-LAST:event_jTConcepFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del código del concep*/
    private void jTConcepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTConcepFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTConcep.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTConcep.getText().compareTo("")!=0)
            jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                

        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Comprueba si el concepto eiste en la base de datos
        int iRes    = Star.iExiste(con, jTConcep.getText().trim(), "conceps", "concep");
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si no existe el concepto entonces
        if(iRes==0)
            jTDescripConcep.setText("");
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jTConcepFocusLost

    
    /*Cuando se presiona una tecla en el campo del código del concep*/
    private void jTConcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTConcepKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTConcep.getText(), 7, jTConcep, jTDescripConcep, null, "", null);
            b.setVisible(true);
            
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTConcepKeyPressed

    
    /*Cuando se presiona el botón de buscar coincidencia de concep*/
    private void jBBuscConcepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscConcepActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTConcep.getText(), 7, jTConcep, jTDescripConcep, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBBuscConcepActionPerformed

    
    /*Cuando se presiona una tecla en el campo de buscar coincidencia en concep*/
    private void jBBuscConcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscConcepKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscConcepKeyPressed

    
    /*Cuando se presiona una tecla en el campo de descripción de concep*/
    private void jTDescripConcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripConcepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripConcepKeyPressed

    
    /*Cuando se presiona una tecla en el campo de exist*/
    private void jTExistKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExistKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTExistKeyPressed

        
    /*Cuando se gana el foco del teclado en el campo de exist*/
    private void jTExistFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExistFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTExist.setSelectionStart(0);jTExist.setSelectionEnd(jTExist.getText().length());
        
    }//GEN-LAST:event_jTExistFocusGained

    
    /*Cuando se tipea una tecla en el campo de concep*/
    private void jTConcepKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTConcepKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTConcepKeyTyped
    
    
    /*Cuando se gana el foco del teclado en el campo de la descripción del concep*/
    private void jTDescripConcepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripConcepFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripConcep.setSelectionStart(0);jTDescripConcep.setSelectionEnd(jTDescripConcep.getText().length());        
        
    }//GEN-LAST:event_jTDescripConcepFocusGained

    
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

    
    /*Cuando el mouse entra en el botón de bùscar*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);
        
        /*Guardar el borde original que tiene el botón*/
        bBordOri    = jBBusc.getBorder();
                
        /*Aumenta el grosor del botón*/
        jBBusc.setBorder(new LineBorder(Color.GREEN, 3));
        
    }//GEN-LAST:event_jBBuscMouseEntered

    
    /*Cuando el mouse sale del botón de bùscar*/
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

    private void jComUnidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComUnidFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComUnid.getSelectedItem().toString().compareTo("")!=0)
        jComUnid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComUnidFocusLost

    
    /*Cuando se presiona una tecla en el combo de unidades*/
    private void jComUnidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComUnidKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComUnidKeyPressed

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProd.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProdMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBuscConcepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscConcepMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBuscConcep.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBuscConcepMouseEntered

    
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
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(colOri);
        
    }//GEN-LAST:event_jBProdMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBuscConcepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscConcepMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBuscConcep.setBackground(colOri);
        
    }//GEN-LAST:event_jBBuscConcepMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
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

    
    /*Cuando se presiona una tecla en el radio de entrada*/
    private void jREntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jREntKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jREntKeyPressed

    
    /*Cuando se presiona una tecla en el radio de salida*/
    private void jRSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRSalKeyPressed

    
    /*Cuando el mosue entra en el botón de listas de precios y costeos*/
    private void jBPrecMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPrecMouseEntered

        /*Cambia el color del fondo del botón*/
        jBPrec.setBackground(Star.colBot);

    }//GEN-LAST:event_jBPrecMouseEntered

    
    /*Cuando el mouse sale del botón de listas de precios y costeos*/
    private void jBPrecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPrecMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBPrec.setBackground(colOri);

    }//GEN-LAST:event_jBPrecMouseExited

    
    /*Cuando se presiona el botón de ver listas de precios y costeos*/
    private void jBPrecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPrecActionPerformed

        /*Si no a ingresado un producto entonces*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un producto primeramente.", "Listas de Precios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el control y regresa*/
            jTProd.grabFocus();
            return;
        }

        /*Muestra el formulario para definir las listas de precios del producto*/
        LPrecsVis l = new LPrecsVis(jTProd.getText());
        l.setVisible(true);

    }//GEN-LAST:event_jBPrecActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver listas de precios y costeos*/
    private void jBPrecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPrecKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBPrecKeyPressed

    
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
    private void jTDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTExistFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExistFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTExist.setCaretPosition(0);
        
    }//GEN-LAST:event_jTExistFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUnidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUnidFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUnid.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUnidFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la descripciòn del concepto*/
    private void jTDescripConcepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripConcepFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDescripConcep.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripConcepFocusLost

    
    /*Cuando sucede una accion en el combobox de unidades*/
    private void jComUnidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComUnidActionPerformed
        
        /*Si no hay selección entonces regresa*/
        if(jComUnid.getSelectedItem()==null)        
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
        
        /*Obtén la descripción de la unidad de la base de datos*/        
        try
        {
            sQ = "SELECT * FROM unids WHERE cod = '" + jComUnid.getSelectedItem().toString().trim() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el control y colcoa el caret al principio del control*/
                jTUnid.setText(rs.getString("descrip"));                                               
                jTUnid.setCaretPosition(0);
            }
            else
                jTUnid.setText("");                                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComUnidActionPerformed

    
    /*Cuando sucede una acción en el combo de las tallas*/
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
                jTTall.setText(rs.getString("descrip"));
            else
                jTTall.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }

        /*Coloca al principio del control el caret*/
        jTTall.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComTallActionPerformed

    
    /*Cuando se presiona una tecla en el combo de la talla*/
    private void jComTallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComTallKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComTallKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción de la talla*/
    private void jTTallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTallFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTTall.setSelectionStart(0);jTTall.setSelectionEnd(jTTall.getText().length());

    }//GEN-LAST:event_jTTallFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción de la talla*/
    private void jTTallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTallFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTTall.setCaretPosition(0);

    }//GEN-LAST:event_jTTallFocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción de la talla*/
    private void jTTallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTallKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTTallKeyPressed

    
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
            sQ = "SELECT descrip FROM colos WHERE cod = '" + jComColo.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTColo.setText(rs.getString("descrip"));
            else
                jTColo.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }

        /*Coloca al principio del control el caret*/
        jTColo.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComColoActionPerformed

    
    /*Cuando se presiona una tecla en el combo de los colores*/
    private void jComColoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComColoKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComColoKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del color*/
    private void jTColoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColoFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTColo.setSelectionStart(0);jTColo.setSelectionEnd(jTColo.getText().length());

    }//GEN-LAST:event_jTColoFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del color*/
    private void jTColoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColoFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTColo.setCaretPosition(0);

    }//GEN-LAST:event_jTColoFocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción del color*/
    private void jTColoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColoKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTColoKeyPressed

    
    /*Cuando se pierde el foco del teclado en el combo de los almacenes*/
    private void jComAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComAlmaFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComAlma.getSelectedItem().toString().compareTo("")!=0)
            jComAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComAlmaFocusLost

    
    /*Cuado sucede una acción en el combo de almacenes*/
    private void jComAlmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComAlmaActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComAlma.getSelectedItem()==null)
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

        /*Obtiene la descripción del almacén en base a su código*/
        try
        {
            sQ = "SELECT almadescrip FROM almas WHERE alma = '" + jComAlma.getSelectedItem().toString() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTDescripAlma.setText(rs.getString("almadescrip"));
            else
                jTDescripAlma.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }

        /*Coloca al principio del control el caret*/
        jTDescripAlma.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComAlmaActionPerformed

    
    /*Cuando se presiona una tecla en el combo de almacenes*/
    private void jComAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComAlmaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción en el almacén*/
    private void jTDescripAlmaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlmaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripAlma.setSelectionStart(0);jTDescripAlma.setSelectionEnd(jTDescripAlma.getText().length());
        
    }//GEN-LAST:event_jTDescripAlmaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del almacén*/
    private void jTDescripAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlmaFocusLost

        /*Coloca el caret al principio del control*/
        jTDescripAlma.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripAlmaFocusLost

    
    /*Cuando se presiona una tecal en el campo de la descripción del almacén*/
    private void jTDescripAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripAlmaKeyPressed

    
    /*Cuando se presiona el botón de existencias por almacén*/
    private void jBExisAlmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExisAlmaActionPerformed

        /*Si no a ingresado un producto entonces*/
        if(jTProd.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un producto.", "Existencias por almacén", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo*/
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Coloca el foco del teclado en el campo del producto y regresa*/
            jTProd.grabFocus();
            return;
        }

        /*Muestra la forma para ver las existencias por almacén del producto*/
        ptovta.ProdExisAlm m = new ptovta.ProdExisAlm(jTProd.getText().trim(),jComAlma);
        m.setVisible(true);

    }//GEN-LAST:event_jBExisAlmaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de existencias por almacén*/
    private void jBExisAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBExisAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBExisAlmaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del costo*/
    private void jTCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCost.setSelectionStart(0);jTCost.setSelectionEnd(jTCost.getText().length());
        
    }//GEN-LAST:event_jTCostFocusGained

    
    /*Cuando se pierde el foco del teclado en el campod el costo*/
    private void jTCostFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCost.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTCost.getText().compareTo("")!=0)
            jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Lee el texto introducido por el usuario*/
        String sTex = jTCost.getText().replace("$", "").replace(",", "");

        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTCost.getText().length()> 20)
            jTCost.setText(jTCost.getText().substring(0, 20));

        /*Si es cadena vacia regresa y no continuar*/
        if(sTex.compareTo("")==0)
            return;

        /*Si los caes introducidos no se puede convertir a double colocar 0 y regresar*/
        try
        {
            double d = Double.parseDouble(sTex);
        }
        catch(NumberFormatException expnNumForm)
        {
            jTCost.setText("$0.00");
            return;
        }

        /*Conviertelo a double*/
        double dCant    = Double.parseDouble(sTex);

        /*Formatealo*/
        java.text.NumberFormat n    = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("es","MX"));
        sTex                        = n.format(dCant);

        /*Colocalo de nu en el campo de texto*/
        jTCost.setText(sTex);
        
    }//GEN-LAST:event_jTCostFocusLost

    
    /*Cuando se presiona una tecla en el campo del costo*/
    private void jTCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCostKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCostKeyPressed

    
    /*Cuando se tipea una tecla en el campo del costo*/
    private void jTCostKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCostKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();
        
    }//GEN-LAST:event_jTCostKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del lote*/
    private void jTLotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLotFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTLot.setSelectionStart(0);jTLot.setSelectionEnd(jTLot.getText().length());
        
    }//GEN-LAST:event_jTLotFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del lote*/
    private void jTLotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLotFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTLot.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTLot.getText().compareTo("")!=0)
            jTLot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTLotFocusLost

    
    /*Cuando se presiona una tecla en el campo del lote*/
    private void jTLotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLotKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTLotKeyPressed

    
    /*Cuando se presiona una tecla en el control de fecha de lote*/
    private void jDFCaduKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFCaduKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDFCaduKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del pedimento*/
    private void jTPedimenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPedimenFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTPedimen.setSelectionStart(0);jTPedimen.setSelectionEnd(jTPedimen.getText().length());
        
    }//GEN-LAST:event_jTPedimenFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del pedimento*/
    private void jTPedimenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPedimenFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPedimen.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPedimenFocusLost

    
    /*Cuando se presiona una tecla en el campo del pedimento*/
    private void jTPedimenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPedimenKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPedimenKeyPressed

    
    /*Cuando se gana el foco del teclado en el cmapo de la descripción de la unidad*/
    private void jTUnidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUnidFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUnid.setSelectionStart(0);jTUnid.setSelectionEnd(jTUnid.getText().length());
        
    }//GEN-LAST:event_jTUnidFocusGained

    
    //Cuando el mouse entra en el botón de cargar series
    private void jBCarSerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCarSerMouseEntered

        /*Cambia el color del fondo del botón*/
        jBCarSer.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCarSerMouseEntered

    
    //Cuando el mouse sale del botón de cargar series
    private void jBCarSerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCarSerMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBCarSer.setBackground(colOri);
        
    }//GEN-LAST:event_jBCarSerMouseExited

    
    //Cuando se presiona el botón de cargar series
    private void jBCarSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCarSerActionPerformed

        /*Configura el file chooser para escoger la ruta del directorio donde esta el archivo*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar archivo con series");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION)
            return;

        /*Si el archivo no es EXCEL entonces*/
        if(!fc.getSelectedFile().getName().toLowerCase().endsWith(".xls")&&!fc.getSelectedFile().getName().toLowerCase().endsWith(".xlsx"))
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El archivo seleccionado no es un archivo de EXCEL.", "Carga series", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Coloca la ruta completa en el control*/
        jTCarSer.setText(fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName());
            jTCarSer.setCaretPosition(0);
            
    }//GEN-LAST:event_jBCarSerActionPerformed

    
    //Cuando se presiona una tecla en el botón de cargar series
    private void jBCarSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCarSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCarSerKeyPressed

    
    //Cuando se gana el foco del teclado en el botón de cargar series
    private void jTCarSerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCarSerFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCarSer.setSelectionStart(0);jTCarSer.setSelectionEnd(jTCarSer.getText().length());
        
    }//GEN-LAST:event_jTCarSerFocusGained

    
    //Cuando se pierde el foco del teclado en el control de cargar series
    private void jTCarSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCarSerFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCarSer.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTCarSer.getText().compareTo("")!=0)
            jTCarSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCarSerFocusLost

    
    //Cuando se presiona una tecla en el control de cargar series
    private void jTCarSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCarSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCarSerKeyPressed

    
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
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();        
        /*Si se presiona F3 presiona el botón de búscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBBuscConcep;
    private javax.swing.JButton jBCarSer;
    private javax.swing.JButton jBExisAlma;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBPrec;
    private javax.swing.JButton jBProd;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JComboBox jComAlma;
    private javax.swing.JComboBox jComColo;
    private javax.swing.JComboBox jComTall;
    private javax.swing.JComboBox jComUnid;
    private com.toedter.calendar.JDateChooser jDFCadu;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jP1;
    private javax.swing.JRadioButton jREnt;
    private javax.swing.JRadioButton jRSal;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTCant;
    private javax.swing.JTextField jTCarSer;
    private javax.swing.JTextField jTColo;
    private javax.swing.JTextField jTComenSer;
    private javax.swing.JTextField jTConcep;
    private javax.swing.JTextField jTCost;
    private javax.swing.JTextField jTDescrip;
    private javax.swing.JTextField jTDescripAlma;
    private javax.swing.JTextField jTDescripConcep;
    private javax.swing.JTextField jTExist;
    private javax.swing.JTextField jTLot;
    private javax.swing.JTextField jTPedimen;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTSerProd;
    private javax.swing.JTextField jTTall;
    private javax.swing.JTextField jTUnid;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
