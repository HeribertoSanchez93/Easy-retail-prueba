//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para controlar los traspasos*/
public class Traspas extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Declara variables de instancia*/
    private static Traspas      obj = null;              
    private int                 iContFi;
        
    /*Contador para modificar tabla*/
    private int                 iContCellEd;
    
    /*Variable que contiene el borde actual*/
    private Border               bBordOri;    
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean             bSel;
    
    /*Declara variabls originales*/
    private String              sProdOri;
    private String              sAlmaOri;
    private String              sUnidOri;
    private String              sAlma2Ori;
    private String              sConcepOri;
    private String              sCantSalOri;   
    private String              sCantEntOri;
    private String              sFTransOri;
    private String              sSucOri;
    private String              sCajOri;
    private String              sEstacOri;
    private String              sNomEstacOri;
    
    
    
    /*Constructor sin argumentos*/
    public Traspas() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Esconde el control del comentario de la serie*/
        jTComenSer.setVisible(false);
        
        /*Inicia el control de fecha con la fecha del día de hoy*/
        java.util.Date dtDat    = new java.util.Date();
        jDFCadu.setDate(dtDat);
        
        /*Establece el tamaño de las columnas de la tabla*/
        jTab.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(5).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(6).setPreferredWidth(150);
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
                
        /*Listener para el combobox de almacenes A*/
        jComAlma2.addPopupMenuListener(new PopupMenuListener()
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
                if(Star.iCargAlmaCom(con, jComAlma2)==-1)
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
        this.getRootPane().setDefaultButton(jBTransfe);
        
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
        this.setTitle("Traspasos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en 1*/
        iContFi      = 1;
                
        /*Para que las tablas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;

        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Carga todos los almacenes en el combo
        if(Star.iCargAlmaCom(con, jComAlma)==-1)
            return;
        
        //Carga todos los almacenes en el combo
        if(Star.iCargAlmaCom(con, jComAlma2)==-1)
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
        if(Star.iCierrBas(con)==-1)
            return;

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
        
        /*Crea el listener para cuando se cambia de selección en la tabla*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtén la propiedad que a sucedio en el control*/
                String pr = event.getPropertyName();                                
                                
                /*Si no hay selecciòn entonces regresa*/                
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pr)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sProdOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sAlmaOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sUnidOri        = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sConcepOri      = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sCantSalOri     = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sAlma2Ori       = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sCantEntOri     = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sFTransOri      = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sSucOri         = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sCajOri         = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sEstacOri       = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        sNomEstacOri    = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sProdOri,           jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sAlmaOri,           jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sUnidOri,           jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sConcepOri,         jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sCantSalOri,        jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sAlma2Ori,          jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sCantEntOri,        jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sFTransOri,         jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sSucOri,            jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sCajOri,            jTab.getSelectedRow(), 10);                        
                        jTab.setValueAt(sEstacOri,          jTab.getSelectedRow(), 11);                        
                        jTab.setValueAt(sNomEstacOri,       jTab.getSelectedRow(), 12);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
        /*Obtiene todos los traspasos de la base de datos y cargalos en la tabla*/
        CargTrasp();
                    
    }/*Fin de public Traspas() */

            
    /*Metodo para que el formulario no se abra dos veces*/
    public static Traspas getObj()
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new Traspas();
        
        /*Devuelve el resultado*/
        return obj;
        
    }/*Fin de public static Traspasos getObj()*/
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jBTransfe = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMosTod = new javax.swing.JButton();
        jTProd = new javax.swing.JTextField();
        jBProd = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTDescrip = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTCant = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTConcep = new javax.swing.JTextField();
        jBConcep = new javax.swing.JButton();
        jTDescripConcep = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTExist = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jComUnid = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jTUnid = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jBPrec1 = new javax.swing.JButton();
        jLImg = new javax.swing.JLabel();
        jComTall = new javax.swing.JComboBox();
        jComColo = new javax.swing.JComboBox();
        jTTall = new javax.swing.JTextField();
        jTColo = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComAlma = new javax.swing.JComboBox();
        jTDescripAlma = new javax.swing.JTextField();
        jBExisAlma = new javax.swing.JButton();
        jComAlma2 = new javax.swing.JComboBox();
        jTDescripAlma2 = new javax.swing.JTextField();
        jBExisAlma1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTLot = new javax.swing.JTextField();
        jTPedimen = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jDFCadu = new com.toedter.calendar.JDateChooser();
        jTComenSer = new javax.swing.JTextField();
        jTSerProd = new javax.swing.JTextField();
        jBComenSer = new javax.swing.JButton();
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

        jLabel1.setText("Almacén A:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 80, -1));

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 200, 110, 30));

        jBTransfe.setBackground(new java.awt.Color(255, 255, 255));
        jBTransfe.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBTransfe.setForeground(new java.awt.Color(0, 102, 0));
        jBTransfe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/transferencia.png"))); // NOI18N
        jBTransfe.setText("Trans.");
        jBTransfe.setToolTipText("Traspasar entra Almacén");
        jBTransfe.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBTransfe.setNextFocusableComponent(jBSal);
        jBTransfe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBTransfeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBTransfeMouseExited(evt);
            }
        });
        jBTransfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTransfeActionPerformed(evt);
            }
        });
        jBTransfe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTransfeKeyPressed(evt);
            }
        });
        jP1.add(jBTransfe, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 170, 110, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Traspasos:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 160, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Producto", "Cod. Almacén", "Unidad", "Cod. Concepto", "Cant. Saliente", "Cod. Almacén", "Cant. Entrante", "Fecha Traspaso", "Sucursal", "Caja", "Usuario", "Nombre Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 840, 250));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 140, 20));

        jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBusc.setNextFocusableComponent(jBMosTod);
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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 440, 560, 20));

        jBMosTod.setBackground(new java.awt.Color(255, 255, 255));
        jBMosTod.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosTod.setForeground(new java.awt.Color(0, 102, 0));
        jBMosTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosTod.setText("Mostrar F4");
        jBMosTod.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosTod.setNextFocusableComponent(jBTransfe);
        jBMosTod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMosTodMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMosTodMouseExited(evt);
            }
        });
        jBMosTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMosTodActionPerformed(evt);
            }
        });
        jBMosTod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMosTodKeyPressed(evt);
            }
        });
        jP1.add(jBMosTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 440, 140, 20));

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

        jLabel5.setText("*Concepto:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, 100, -1));

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
        jP1.add(jTDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 230, 20));

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
        jP1.add(jTCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 30, 70, 20));

        jLabel7.setText("*Cantidad:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 80, -1));

        jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTConcep.setNextFocusableComponent(jBConcep);
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
        });
        jP1.add(jTConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, 110, 20));

        jBConcep.setBackground(new java.awt.Color(255, 255, 255));
        jBConcep.setText("...");
        jBConcep.setToolTipText("Buscar Concepto(s)");
        jBConcep.setNextFocusableComponent(jTab);
        jBConcep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBConcepMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBConcepMouseExited(evt);
            }
        });
        jBConcep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBConcepActionPerformed(evt);
            }
        });
        jBConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBConcepKeyPressed(evt);
            }
        });
        jP1.add(jBConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 30, 30, 20));

        jTDescripConcep.setEditable(false);
        jTDescripConcep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripConcep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripConcepFocusLost(evt);
            }
        });
        jTDescripConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripConcepKeyPressed(evt);
            }
        });
        jP1.add(jTDescripConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 70, 140, 20));

        jLabel8.setText("Descripción:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 50, 150, -1));

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
        jP1.add(jTExist, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 80, 20));

        jLabel10.setText("*Cod. Producto:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        jLabel11.setText("Descripción:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 150, -1));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(856, 450, 120, 20));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar todo");
        jBTod.setToolTipText("Seleccionar Todos los Elementos de la Tabla (Alt+T)");
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 172, 130, 20));

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
        jP1.add(jComUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 90, 20));

        jLabel13.setText("Existencia:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 90, -1));

        jTUnid.setEditable(false);
        jTUnid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUnid.setNextFocusableComponent(jComTall);
        jTUnid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUnidFocusLost(evt);
            }
        });
        jTUnid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUnidKeyPressed(evt);
            }
        });
        jP1.add(jTUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 80, 20));

        jLabel14.setText("Color:");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 60, -1));

        jBPrec1.setBackground(new java.awt.Color(255, 255, 255));
        jBPrec1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBPrec1.setText("$");
        jBPrec1.setToolTipText("Lista de Precios y Costeos");
        jBPrec1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jBPrec1.setNextFocusableComponent(jComUnid);
        jBPrec1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBPrec1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBPrec1MouseExited(evt);
            }
        });
        jBPrec1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPrec1ActionPerformed(evt);
            }
        });
        jBPrec1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPrec1KeyPressed(evt);
            }
        });
        jP1.add(jBPrec1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 80, 20));

        jLImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/traspas2.png"))); // NOI18N
        jP1.add(jLImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 100, 70));

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
        jP1.add(jComTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 90, 20));

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
        jP1.add(jComColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 90, 20));

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
        jP1.add(jTTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 80, 20));

        jTColo.setEditable(false);
        jTColo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTColo.setNextFocusableComponent(jTLot);
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
        jP1.add(jTColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 80, 20));

        jLabel15.setText("Unidad:");
        jP1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 60, -1));

        jLabel16.setText("Talla:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 60, -1));

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
        jTDescripAlma.setNextFocusableComponent(jBPrec1);
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

        jComAlma2.setNextFocusableComponent(jTDescripAlma2);
        jComAlma2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComAlma2FocusLost(evt);
            }
        });
        jComAlma2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComAlma2ActionPerformed(evt);
            }
        });
        jComAlma2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComAlma2KeyPressed(evt);
            }
        });
        jP1.add(jComAlma2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, 80, 20));

        jTDescripAlma2.setEditable(false);
        jTDescripAlma2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripAlma2.setNextFocusableComponent(jTCant);
        jTDescripAlma2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripAlma2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripAlma2FocusLost(evt);
            }
        });
        jTDescripAlma2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripAlma2KeyPressed(evt);
            }
        });
        jP1.add(jTDescripAlma2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, 90, 20));

        jBExisAlma1.setBackground(new java.awt.Color(0, 153, 153));
        jBExisAlma1.setToolTipText("Existencias por almacén del producto");
        jBExisAlma1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExisAlma1ActionPerformed(evt);
            }
        });
        jBExisAlma1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBExisAlma1KeyPressed(evt);
            }
        });
        jP1.add(jBExisAlma1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 30, 10, 20));

        jLabel12.setText("Lote:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 90, -1));

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

        jLabel17.setText("Pedimento:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, 80, -1));

        jLabel9.setText("Serie:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 190, 20));

        jDFCadu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jDFCadu.setNextFocusableComponent(jTSerProd);
        jDFCadu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFCaduKeyPressed(evt);
            }
        });
        jP1.add(jDFCadu, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 190, 20));

        jTComenSer.setEditable(false);
        jTComenSer.setFocusable(false);
        jP1.add(jTComenSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 90, 10, -1));

        jTSerProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSerProd.setNextFocusableComponent(jComAlma2);
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
        jP1.add(jTSerProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 180, 20));

        jBComenSer.setBackground(new java.awt.Color(0, 153, 153));
        jBComenSer.setToolTipText("Comentario de la Serie");
        jBComenSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBComenSerActionPerformed(evt);
            }
        });
        jBComenSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBComenSerKeyPressed(evt);
            }
        });
        jP1.add(jBComenSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, 10, 20));

        jLabel18.setText("Fecha caducidad:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, 190, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todos los traspasos de la base de datos y cargalos en la tabla*/
    private void CargTrasp()
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
                
        /*Trae todas los trasásps de la base de datos y cargalos en la tabla*/
        try
        {
            sQ = "SELECT traspas.UNID, traspas.ESTAC, estacs.NOM, traspas.PROD, traspas.ALMA, traspas.CONCEP, traspas.CANTSAL, traspas.ALMAA, traspas.CANTENT, traspas.FALT, traspas.SUCU, traspas.NOCAJ  FROM traspas LEFT OUTER JOIN estacs ON estacs.ESTAC = traspas.ESTAC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                //Obtiene la cantidad visual equivalente correcta
                String sCant    = Star.sCantVisuaGKT(rs.getString("traspas.UNID"), rs.getString("traspas.CANTENT"));
                
                /*Agregalo a la tabla*/
                Object nu[]= {iContFi, rs.getString("traspas.PROD"), rs.getString("traspas.ALMA"), rs.getString("traspas.UNID"), rs.getString("traspas.CONCEP"), sCant, rs.getString("traspas.ALMAA"), sCant, rs.getString("traspas.FALT"), rs.getString("traspas.SUCU"), rs.getString("traspas.NOCAJ"), rs.getString("traspas.ESTAC"), rs.getString("nom")};
                te.addRow(nu);
                
                /*Aumentar en uno el contador de pesos*/
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
        
    }/*Fin de private void CargTrasp()*/
    
         
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
            /*Llama al recolector de basura*/
            System.gc();
        
            /*Cierra la forma*/
            this.dispose();
            obj = null;
        }
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el botón de transferencia*/
    private void jBTransfeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTransfeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBTransfeKeyPressed

    
    /*Cuando se presiona una  tecla en la tabla de transpasos*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se presiona el botón de traspasar*/
    private void jBTransfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTransfeActionPerformed
        
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
                    
        //Abre la base de datos
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
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
            /*Si no hay datos entonces no existe*/
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
                sKit    = rs.getString("compue");                        
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
                        
        /*Si el primer código de almacén es cadena vacia entonces*/
        if(jComAlma.getSelectedItem().toString().compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
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
            
        }/*Fin de if(!bSiUnid)*/
        
        /*Si el almacén A esta vacio entonces*/
        if(jComAlma2.getSelectedItem().toString().compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El almacén A esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo*/                               
            jComAlma2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jComAlma2.grabFocus();                        
            return;            
        }                   
        
        /*Si los almacenes para traspaso son iguales entonces*/
        if(jComAlma.getSelectedItem().toString().compareTo(jComAlma2.getSelectedItem().toString())==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No se puede hacer traspaso entre almacenes iguales.", "Traspasos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTProd.grabFocus();                        
            return;             
        }                
        
        /*Si hay cadena vacia en la cantidad no puede continuar*/
        if(jTCant.getText().compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de cantidad esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCant.grabFocus();                        
            return;            
        }                
        
        /*Si hay cadena vacia en el campo del código del concepto no puede continuar*/
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
        int iRes    = Star.iExistConcep(con, jTConcep.getText().trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el concepto no existe entonces
        if(iRes==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código del concepto: " + jTConcep.getText() + " no existe.", "Concepto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTConcep.grabFocus();                        
            return;            

        }/*Fin de if(iRes==0)*/
        
        //Obtiene la existencia del primer producto por almacén
        double dExis1   = Star.dExisProd(null, jComAlma.getSelectedItem().toString().trim(), jTProd.getText().trim());
        
        //Si hubo error entonces regresa
        if(dExis1==-1)
            return;
        
        //Si la existencia es negativa entonces
        if(dExis1<=0)
        {                                
            //Obtiene la configuración para saber si se pueden hacer salidas por sin existencias
            iRes        = Star.iGetConfGral("traspasexis");

            //Si hubo error regresa
            if(iRes==-1)
                return;

            //Si la configuración es que no se permita salidas sin existencias entonces
            if(iRes==1)
            {
                //Mensajea y regresa
                JOptionPane.showMessageDialog(null, "No esta permitido hacer traspasos sin existencias.", "Traspaso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }                    
        }                                            
        
        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres hacer el traspaso?", "Transpaso", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }

        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;        

        /*La cantidad a manejar originalmente será la que ingreso el usuario*/
        String sCant    = jTCant.getText().trim();

        /*Obtiene la cantidad correcta en base a la unidad*/        
        sCant       = Star.sCantUnid(jComUnid.getSelectedItem().toString().trim(), sCant);

        /*Si hay datos en la serie entonces*/
        if(jTSerProd.getText().trim().compareTo("")!=0)        
        {
            /*Registra la salida del primer almacén*/
            Star.vSerPro(con, jTProd.getText().trim(), sCant, jTSerProd.getText().trim(), jComAlma.getSelectedItem().toString().trim(), jTComenSer.getText().trim(), "-");
            
            /*Registra la entrada del segundo almacén*/
            Star.vSerPro(con, jTProd.getText().trim(), sCant, jTSerProd.getText().trim(), jComAlma2.getSelectedItem().toString().trim(), jTComenSer.getText().trim(), "+");
        }
        
        /*Si es un kit entonces*/
        if(sKit.compareTo("1")==0)
        {
            /*Realiza el traspaso de los componentes de los kits entre los almacenes*/                
            if(Star.iInsCompKitInv(con, jTProd.getText().trim(), jComAlma.getSelectedItem().toString(), jTConcep.getText().trim(), "0", "traspas", jComAlma2.getSelectedItem().toString().trim(), sCant)==-1)
                return;   
        }
        /*Else no es un kit entonces*/
        else
        {                        
            /*Si tiene lote o pedimento entonces*/
            if(jTLot.getText().trim().compareTo("")!=0 || jTPedimen.getText().trim().compareTo("")!=0)                        
            {
                /*Obtiene la fecha de caducidad*/
                java.util.Date dtDat    = jDFCadu.getDate();
                SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");
                String sFCadu           = sdf.format(dtDat);

                /*Registra la salida del primer almacén*/
                if(Star.sLotPed(con, jTProd.getText().trim(), sCant, jComAlma.getSelectedItem().toString().trim(), jTLot.getText().trim(), jTPedimen.getText().trim(), sFCadu, "-")==null)                
                    return;
                
                /*Registra la entrada al segundo almacén*/
                if(Star.sLotPed(con, jTProd.getText().trim(), sCant, jComAlma2.getSelectedItem().toString().trim(), jTLot.getText().trim(), jTPedimen.getText().trim(), sFCadu, "+")==null)
                    return;
            }

            /*Si específico talla o color entonces procesa la transferencia entre alamacenes para tallas y colores*/
            if(jComTall.getSelectedItem().toString().compareTo("")!=0 || jComColo.getSelectedItem().toString().compareTo("")!=0)        
                vTallCol(con, sCant, jComAlma2.getSelectedItem().toString(), jComAlma2.getSelectedItem().toString(), jComTall.getSelectedItem().toString(), jComColo.getSelectedItem().toString(), jTProd.getText());

            /*Realiza la afectación correspondiente al almacén para la salida*/
            if(Star.iAfecExisProd(con, jTProd.getText().replace("'", "''").trim(), jComAlma.getSelectedItem().toString().replace("'", "''").trim(), sCant, "-")==-1)
                return;

            /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
            if(!Star.vRegMoniInv(con, jTProd.getText().trim(), sCant, jTDescrip.getText().trim(), jComAlma.getSelectedItem().toString().trim(), Login.sUsrG , "", jTConcep.getText().trim(), jComUnid.getSelectedItem().toString().trim(), "", "", "1"))                                
                return;      

            /*Realiza la afectación correspondiente al almacén para la entrada*/
            if(Star.iAfecExisProd(con, jTProd.getText().replace("'", "''").trim(), jComAlma2.getSelectedItem().toString().replace("'", "''").trim(), sCant, "+")==-1)
                return;
            
            /*Registra el producto que se esta ingresando al inventario en la tabla de monitor de inventarios*/
            if(!Star.vRegMoniInv(con, jTProd.getText().trim(), sCant, jTDescrip.getText().trim(), jComAlma2.getSelectedItem().toString().trim(), Login.sUsrG , "", jTConcep.getText().trim(), jComUnid.getSelectedItem().toString().trim(), "", "", "0"))                                
                return;      

            /*Registra el traspaso entre los almacenes*/
            try 
            {            
                sQ = "INSERT INTO traspas(      prod,                                             alma,                                                                     concep,                                             cantsal,            almaa,                                                                      cantent,           estac,                                  falt,           sucu,                                     nocaj,                                     unid) " + 
                                "VALUES('" +    jTProd.getText().replace("'", "''") + "','" +    jComAlma.getSelectedItem().toString().replace("'", "''").trim() + "','" + jTConcep.getText().replace("'", "''") + "', " +     sCant + ",'" +      jComAlma2.getSelectedItem().toString().replace("'", "''").trim() + "', " +  sCant + ", '" +    Login.sUsrG.replace("'", "''") + "',     now(), '" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "', '" +  jComUnid.getSelectedItem().toString().trim() + "')";
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                         
             }
            
        }/*Fin de else*/
                    
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        /*Obtiene el nombre de el usuario actual*/
        String sNomb    = "";
        try
        {
            sQ = "SELECT nom FROM estacs WHERE estac = '" + Login.sUsrG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sNomb   = rs.getString("nom");                                   
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

        /*Obtiene la fecha de hoy*/
        java.text.DateFormat dtForm = new java.text.SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
        java.util.Date date         = new java.util.Date();
        
        /*Agrega el registro del traspaso en la tabla*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFi, jTProd.getText(), jComAlma.getSelectedItem().toString().trim(), jComUnid.getSelectedItem().toString().trim(), jTConcep.getText(), sCant, jComAlma2.getSelectedItem().toString().replace("'", "''").trim(), sCant, dtForm.format(date), Star.sSucu, Star.sNoCaj, Login.sUsrG, sNomb};
        te.addRow(nu);
        
        /*Aumenta en uno el contador de filas en 1*/
        ++iContFi;
        
        /*Cambia la existencia en el control del producto de donde sale el producto*/
        jTExist.setText(Double.toString(Double.parseDouble(jTExist.getText()) - Double.parseDouble(sCant)));
        
        /*Pon el foco del teclado en el campo de código de producto*/
        jTProd.grabFocus();
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Traspaso del producto: " + jTProd.getText() + " desde almacén: " + jComAlma.getSelectedItem().toString().trim() + " hacia almacén: " + jComAlma2.getSelectedItem().toString().trim() + ", la cantidad: " + sCant + " terminadó con éxito.", "Exito en Traspaso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
         
    }//GEN-LAST:event_jBTransfeActionPerformed
         
  
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscKeyPressed


    /*Procesa la parte de tallas y colores con respecto a las existencias*/
    private void vTallCol(Connection con, String sCant, String sAlma, String sAlma2, String sTall, String sColo, String sProd)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Comprueba si ya existe este producto con la talla, color y almacén en la tabla de tallas y colores para el movimiento de salida*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT prod FROM tallcolo WHERE prod = '" + sProd + "' AND alma = '" + sAlma + "' AND tall = '" + sTall + "' AND colo = '" + sColo + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe y coloca la bandera*/
            if(rs.next())
                bSi = true;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                                                 
        }

        /*Crea la consulta correcta dependiendo si existe o no ya ese registro en la base de datos*/
        if(bSi)
            sQ  = "UPDATE tallcolo SET "
                    + "exist        = exist - " + sCant + " "
                    + "WHERE prod   = '" + sProd + "' AND alma = '" + sAlma + "' AND tall = '" + sTall + "' AND colo = '" + sColo + "'";
        else
            sQ  = "INSERT INTO tallcolo (prod,           tall,            alma,            colo,            exist,          estac,                 sucu,                         nocaj) "
                         + "VALUES('" + sProd + "', '" + sTall + "', '" + sAlma + "', '" + sColo + "', -" + sCant + ", '" + Login.sUsrG + "', '" + Star.sSucu + "', '" +   Star.sNoCaj + "')";
        
        /*Ejecuta la consulta*/
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
        
        /*Comprueba si ya existe este producto con la talla, color y almacén en la tabla de tallas y colores para el movimiento de entrada*/        
        bSi = false;
        try
        {
            sQ = "SELECT prod FROM tallcolo WHERE prod = '" + sProd + "' AND alma = '" + sAlma2 + "' AND tall = '" + sTall + "' AND colo = '" + sColo + "'";	                       
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe y coloca la bandera*/
            if(rs.next())
                bSi = true;
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                                                                                                                                                                    
        }

        /*Crea la consulta correcta dependiendo si existe o no ya ese registro en la base de datos*/
        if(bSi)
            sQ  = "UPDATE tallcolo SET "
                    + "exist        = exist + " + sCant + " "
                    + "WHERE prod   = '" + sProd + "' AND alma = '" + sAlma2 + "' AND tall = '" + sTall + "' AND colo = '" + sColo + "'";
        else
            sQ  = "INSERT INTO tallcolo (prod,           tall,            alma,            colo,            exist,          estac,                 sucu,                         nocaj) "
                         + "VALUES('" + sProd + "', '" + sTall + "', '" + sAlma2 + "', '" + sColo + "', " + sCant + ", '" + Login.sUsrG + "', '" + Star.sSucu + "', '" +   Star.sNoCaj + "')";
        
        /*Ejecuta la consulta*/
        try 
        {                            
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
         }    
        
    }/*Fin de private void vTallCol(Connection con, String sCant, String sAlma, String sAlma2, String sTall, String sColo, String sProd)*/
    
    
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
    private void jBMosTodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMosTodKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMosTodKeyPressed

    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTodActionPerformed
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtiene todos los transpasos de la base de datos y cargalos en la tabla*/
        CargTrasp();
        
        /*Vuelve a poner el foco del teclado en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBMosTodActionPerformed

    
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
        
        /*Borra todos los item en la tabla de transpasos*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi              = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todos los transpasos*/        
        try
        {                  
            sQ = "SELECT traspas.ESTAC, estacs.NOM, traspas.PROD, traspas.ALMA, traspas.CONCEP, traspas.CANTSAL, traspas.ALMAA, traspas.CANTENT, traspas.FALT, traspas.SUCU, traspas.NOCAJ FROM traspas LEFT OUTER JOIN estacs ON estacs.ESTAC = traspas.ESTAC WHERE prod LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR traspas.ALMA LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR estacs.NOM LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR traspas.CONCEP LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR traspas.CANTSAL LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR traspas.ALMA LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR traspas.CANTENT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR traspas.ESTAC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR traspas.FALT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalos a la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("traspas.PROD"), rs.getString("traspas.ALMA"), rs.getString("traspas.CONCEP"), rs.getString("traspas.CANTSAL"), rs.getString("traspas.ALMAA"), rs.getString("traspas.CANTENT"), rs.getString("traspas.FALT"), rs.getString("traspas.SUCU"), rs.getString("traspas.NOCAJ"), rs.getString("traspas.ESTAC"), rs.getString("estacs.NOM")};
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

    
    /*Cuando se gana el foco del teclado en el campo del código del producto*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());        
        
    }//GEN-LAST:event_jTProdFocusGained

    
    /*Carga la información del producto*/
    private void vCargProd()
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
            sQ = "SELECT prod, descrip, unid, exist, compue FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                //Obtiene la existencia visual equivalente correcta
                String sExist   = Star.sCantVisuaGKT(rs.getString("unid"), rs.getString("exist"));
                
                /*Coloca los valores en los controles*/
                jTDescrip.setText           (rs.getString("descrip"));                                           
                jTExist.setText             (sExist);                        
                jComUnid.setSelectedItem    (rs.getString("unid"));
                
                /*Si es un kit entonces deshabilita el control de la unidad*/
                if(rs.getString("compue").compareTo("1")==0)
                {
                    jComUnid.setEnabled     (false);
                    jComUnid.setFocusable   (false);
                }                
            }
            /*Else no existe entonces*/
            else
            {                
                /*Resetea los campos*/
                jTDescrip.setText           ("");            
                jComAlma.setSelectedItem    ("");
                jComAlma2.setSelectedItem   ("");
                jTExist.setText             ("");                        
                jComUnid.setSelectedItem    ("");
                
                /*Habilita nuevamente el combo de la unidad*/
                jComUnid.setEnabled     (true);
                jComUnid.setFocusable   (true);
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
                
    }/*Fin de vCargProd*/
    
    
    /*Cuando se pierde el foco del teclado en el campo del código del producto*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTProd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Carga la información del producto*/
        vCargProd();
        
    }//GEN-LAST:event_jTProdFocusLost

    
    /*Cuando se presiona una tecla en el campo del código del producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar producto*/
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
        /*Else if se presiono F3 entonces presiona el botón de búscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProdKeyPressed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc2 b = new Busc2(this, jTProd.getText(), 1, jTProd, jTDescrip,  jTExist);
        b.setVisible(true);
        
        /*Carga la información del producto*/
        vCargProd();
        
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
        
        /*Obtiene el código del concep seleccionado por el usuario*/
        String sConcep        = jTConcep.getText();

        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Comprueba si el concepto eiste en la base de datos
        int iRes    = Star.iExistConcep(con, sConcep.trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el concepto existe entonces coloca la bandera
        boolean bSi   = false;
        if(iRes==1)
            bSi     = true;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Si el código del concep no existe entonces resetea el campo*/
        if(!bSi)
            jTDescripConcep.setText("");                    
        
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
    private void jBConcepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBConcepActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTConcep.getText(), 7, jTConcep, jTDescripConcep, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBConcepActionPerformed

    
    /*Cuando se presiona una tecla en el campo de buscar coincidencia en concep*/
    private void jBConcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBConcepKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBConcepKeyPressed

    
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

    
    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se arrastra el ratón en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve el mouse se mueve en la forma*/
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

    
    /*Cuando el mouse entra en el botón de búscar*/
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

    
    /*Cuando se pierde el foco del teclado en el combo de las unidades 1*/
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
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/    
    private void jBTransfeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTransfeMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTransfe.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTransfeMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMosTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMosTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMosTodMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(colOri);
        
    }//GEN-LAST:event_jBProdMouseExited

    
    
    /*Cuando el mouse netra en el botón específicado*/
    private void jBConcepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConcepMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBConcep.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBConcepMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBTodMouseExited


    /*Cuando el mouse sale del botón específico*/
    private void jBMosTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMosTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBMosTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTransfeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTransfeMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTransfe.setBackground(colOri);
        
    }//GEN-LAST:event_jBTransfeMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse entra en el botón de listas de precios y costeos del producto 1*/
    private void jBPrec1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPrec1MouseEntered

        /*Cambia el color del fondo del botón*/
        jBPrec1.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBPrec1MouseEntered

    
    /*Cuando el mouse sale en el botón de listas de precios y costeos del producto 1*/
    private void jBPrec1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPrec1MouseExited

        /*Cambia el color del fondo del botón al original*/
        jBPrec1.setBackground(colOri);
        
    }//GEN-LAST:event_jBPrec1MouseExited

    
    /*Cuando se presiona el botón de ver listas de precios y costeos del producto 1*/
    private void jBPrec1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPrec1ActionPerformed

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
        
    }//GEN-LAST:event_jBPrec1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver listas de precios y costeos de producto 1*/
    private void jBPrec1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPrec1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBPrec1KeyPressed

    
    
    
    
    
    /*Cuando el mouse sale del botón de concepto*/
    private void jBConcepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConcepMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBConcep.setBackground(colOri);
        
    }//GEN-LAST:event_jBConcepMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBTransfe);
        
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

    
    
    
    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTDescripConcepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripConcepFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescripConcep.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripConcepFocusLost

    
    /*Cuando sucede una acción en el combobox de unidades*/
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

    
    /*Cuando sucede una acción en el combo de tallas*/
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
            sQ = "SELECT descrip FROM tall WHERE cod  = '" + jComTall.getSelectedItem().toString().trim() + "'";
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

    
    /*Cuando se presiona una tecla en el combo de las tallas*/
    private void jComTallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComTallKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComTallKeyPressed

    
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

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción de la talla*/
    private void jTColoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColoFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTColo.setSelectionStart(0);jTColo.setSelectionEnd(jTColo.getText().length());
        
    }//GEN-LAST:event_jTColoFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del color*/
    private void jTColoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColoFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTColo.setCaretPosition(0);
        
    }//GEN-LAST:event_jTColoFocusLost

    
    /*Cuando se presiona una tecla en el campo de la descrición del color*/
    private void jTColoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColoKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTColoKeyPressed

    
    /*Cuando se pierde el foco del teclado en el combo del almacén*/
    private void jComAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComAlmaFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComAlma.getSelectedItem().toString().compareTo("")!=0)
            jComAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComAlmaFocusLost

    
    /*Cuando sucede una acción en el combo del almacén*/
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

    /*Cuando se presiona una tecla en el combo del almacén*/
    private void jComAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComAlmaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descri´ción del almacén*/
    private void jTDescripAlmaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlmaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripAlma.setSelectionStart(0);jTDescripAlma.setSelectionEnd(jTDescripAlma.getText().length());

    }//GEN-LAST:event_jTDescripAlmaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del almacén*/
    private void jTDescripAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlmaFocusLost

        /*Coloca el caret al principio del control*/
        jTDescripAlma.setCaretPosition(0);

    }//GEN-LAST:event_jTDescripAlmaFocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción del almacén*/
    private void jTDescripAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripAlmaKeyPressed

    
    /*Cuando se presiona una tecla en el botón de ver existencias por almacén*/
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

    
    /*Cuando se pierde el foco del teclado en el combo del almacén 2*/
    private void jComAlma2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComAlma2FocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComAlma.getSelectedItem().toString().compareTo("")!=0)
            jComAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComAlma2FocusLost

    
    /*Cuando sucede una acción en el combo de los almacenes 2*/
    private void jComAlma2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComAlma2ActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComAlma2.getSelectedItem()==null)
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
            sQ = "SELECT almadescrip FROM almas WHERE alma = '" + jComAlma2.getSelectedItem().toString() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTDescripAlma2.setText(rs.getString("almadescrip"));
            else
                jTDescripAlma2.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                                                    
        }

        /*Coloca al principio del control el caret*/
        jTDescripAlma2.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComAlma2ActionPerformed

    
    /*Cuando se presiona una tecla en el combo de los almacenes 2*/
    private void jComAlma2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComAlma2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComAlma2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del almacén*/
    private void jTDescripAlma2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlma2FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripAlma.setSelectionStart(0);jTDescripAlma.setSelectionEnd(jTDescripAlma.getText().length());

    }//GEN-LAST:event_jTDescripAlma2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del almacén 2*/
    private void jTDescripAlma2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlma2FocusLost

        /*Coloca el caret al principio del control*/
        jTDescripAlma.setCaretPosition(0);

    }//GEN-LAST:event_jTDescripAlma2FocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción del almacén 2*/
    private void jTDescripAlma2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripAlma2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripAlma2KeyPressed

    
    /*Cuando se presiona el botón de existencias por almacén de producto 2*/
    private void jBExisAlma1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExisAlma1ActionPerformed

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
        
    }//GEN-LAST:event_jBExisAlma1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de existencias por almacén 2*/
    private void jBExisAlma1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBExisAlma1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBExisAlma1KeyPressed

    
    /*Cuando se gana el foco del teclado en el capo del lote*/
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

    
    /*Cuando se presiona una tecla en el campo del pediemnto*/
    private void jTPedimenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPedimenKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTPedimenKeyPressed

    
    /*Cuando se presiona una tecla en el campo de fecha de caducidad*/
    private void jDFCaduKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFCaduKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jDFCaduKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la unidad*/
    private void jTUnidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUnidKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUnidKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la serie*/
    private void jTSerProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTSerProd.setSelectionStart(0);jTSerProd.setSelectionEnd(jTSerProd.getText().length());
        
    }//GEN-LAST:event_jTSerProdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la serie del producto*/
    private void jTSerProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerProdFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTSerProd.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSerProdFocusLost

    
    /*Cuando se presiona una tecla en el campo de la serie del prodcuto*/
    private void jTSerProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSerProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Si no a seleccionado un producto entonces*/
            if(jTDescrip.getText().compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Selecciona un producto para ver sus series primeramente.", "Series de producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }

            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTProd.getText().trim(), 34, jTSerProd, jTComenSer, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSerProdKeyPressed

    
    /*Cuando se presiona el botón de comentario de la serie*/
    private void jBComenSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBComenSerActionPerformed

        /*Muestar la forma para ver la descripción en grande*/
        DescripGran b = new DescripGran(jTComenSer, null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBComenSerActionPerformed

    
    /*Cuando se presiona una tecla en el botón de comentario de la serie*/
    private void jBComenSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBComenSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBComenSerKeyPressed
      
    
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
        /*Si se presiona F2 presiona el botón de búscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBBusc.doClick();
        /*Si se presiona F2 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBMosTod.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBComenSer;
    private javax.swing.JButton jBConcep;
    private javax.swing.JButton jBExisAlma;
    private javax.swing.JButton jBExisAlma1;
    private javax.swing.JButton jBMosTod;
    private javax.swing.JButton jBPrec1;
    private javax.swing.JButton jBProd;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBTransfe;
    private javax.swing.JComboBox jComAlma;
    private javax.swing.JComboBox jComAlma2;
    private javax.swing.JComboBox jComColo;
    private javax.swing.JComboBox jComTall;
    private javax.swing.JComboBox jComUnid;
    private com.toedter.calendar.JDateChooser jDFCadu;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLImg;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTCant;
    private javax.swing.JTextField jTColo;
    private javax.swing.JTextField jTComenSer;
    private javax.swing.JTextField jTConcep;
    private javax.swing.JTextField jTDescrip;
    private javax.swing.JTextField jTDescripAlma;
    private javax.swing.JTextField jTDescripAlma2;
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

}/*Fin de public class Traspas extends javax.swing.JFrame */
