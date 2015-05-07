//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import java.awt.Color;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para mandar las ventas a contabilidad*/
public class VtasCont extends javax.swing.JFrame 
{
    /*Variable que contiene el borde actual*/
    private Border          bBordOri;

    /*Bandera de errores de la forma*/
    private boolean         bErr        = false;
    
    /*Declara variables gloabales originales*/
    private String          sVtaOriG;
    private String          sFolOriG;
    private String          sNoSerOriG;
    private String          sCliOriG;
    private String          sNomOriG;
    private String          sSubtotOriG;
    private String          sImpueOriG;
    private String          sTotOriG;
    private String          sFechOriG;
    private String          sCtaCliOriG;
    
    /*Declara contadores globales*/
    private int             iContCellEd;
    private int             iContFi;
        
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;

    /*Contiene los datos de conexión a contabilidad SKD*/
    private String          sNomConta;
    private String          sUsrConta;
    private String          sContraConta;
    
    /*Contiene los datos de conexión a contabilidad SQL*/
    private String          sInst;
    private String          sUsr;
    private String          sContra;
    private String          sBD;
    
    
    
    
    /*Constructor*/
    public VtasCont(String sUs, String sIns, String sCont, String sB, String sNom, String sUsrCon, String sContr) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
    
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Obtiene los datos de conexión a contabilidad SDK*/
        sNomConta   = sNom;
        sUsrConta   = sUsrCon;
        sContraConta= sContr;
        
        /*Obtiene los datos de conexión a contabilidad SQL*/
        sInst       = sIns;
        sUsr        = sUs;
        sContra     = sCont;
        sBD         = sB;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBIng);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Ventas a contabilidad, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en 1*/
        iContFi      = 1;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla*/        
        jTab.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(5).setPreferredWidth(300);
        jTab.getColumnModel().getColumn(9).setPreferredWidth(160);
        jTab.getColumnModel().getColumn(10).setPreferredWidth(170);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Obtiene todas las ventas y cargalas en la tabla*/
        vCargVta();
        
        /*Obtiene las cuentas necesarias y cargalas en los controles*/
        vCargCtas();
            
        /*Crea el listener para la tabla, para cuando se modifique una celda guardarlo en la base de datos el cambio*/
        PropertyChangeListener pro = new PropertyChangeListener() 
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
                        sVtaOriG        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sFolOriG        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                                                                                  
                        sNoSerOriG      = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                                                                          
                        sCliOriG        = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sNomOriG        = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sSubtotOriG     = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sImpueOriG      = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sTotOriG        = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sFechOriG       = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sCtaCliOriG     = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Resetea el cell editor*/
                        iContCellEd         = 1;
                        
                        /*Coloca los valores origianales*/
                        jTab.setValueAt(sVtaOriG,           jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sFolOriG,           jTab.getSelectedRow(), 2);
                        jTab.setValueAt(sNoSerOriG,         jTab.getSelectedRow(), 3);                       
                        jTab.setValueAt(sCliOriG,           jTab.getSelectedRow(), 4);
                        jTab.setValueAt(sNomOriG,           jTab.getSelectedRow(), 5);
                        jTab.setValueAt(sSubtotOriG,        jTab.getSelectedRow(), 6);
                        jTab.setValueAt(sImpueOriG,         jTab.getSelectedRow(), 7);
                        jTab.setValueAt(sTotOriG,           jTab.getSelectedRow(), 8);
                        jTab.setValueAt(sFechOriG,          jTab.getSelectedRow(), 9);
                        jTab.setValueAt(sCtaCliOriG,        jTab.getSelectedRow(), 10);
                    }                 
                                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public VtasCont() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBIng = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jTVtas = new javax.swing.JTextField();
        jTIvaPendPag = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMosT = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jTConsu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTAlma = new javax.swing.JTextField();
        jTDevVta = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComRem = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();

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

        jBIng.setBackground(new java.awt.Color(255, 255, 255));
        jBIng.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBIng.setForeground(new java.awt.Color(0, 102, 0));
        jBIng.setText("Ingreso");
        jBIng.setToolTipText("Hacer póliza de ingreso");
        jBIng.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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
        jP1.add(jBIng, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 130, 120, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("CONSUMIBLES COSTO:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 200, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jTIvaPendPag);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 160, 120, -1));

        jTVtas.setEditable(false);
        jTVtas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTVtas.setNextFocusableComponent(jTConsu);
        jTVtas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTVtasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTVtasFocusLost(evt);
            }
        });
        jTVtas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTVtasKeyPressed(evt);
            }
        });
        jP1.add(jTVtas, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 270, 20));

        jTIvaPendPag.setEditable(false);
        jTIvaPendPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTIvaPendPag.setName(""); // NOI18N
        jTIvaPendPag.setNextFocusableComponent(jTVtas);
        jTIvaPendPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTIvaPendPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTIvaPendPagFocusLost(evt);
            }
        });
        jTIvaPendPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTIvaPendPagKeyPressed(evt);
            }
        });
        jP1.add(jTIvaPendPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 270, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("IVA PENDIENTE PAGAR:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Venta", "Folio", "No.Serie", "Cliente", "Nombre", "Subtotal", "Impuesto", "Total", "Fecha", "Cuenta Cliente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 700, 240));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 140, 20));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 390, 420, 20));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setNextFocusableComponent(jBIng);
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 390, 140, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 410, 120, 20));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 132, 130, 18));

        jTConsu.setEditable(false);
        jTConsu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTConsu.setNextFocusableComponent(jTAlma);
        jTConsu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTConsuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTConsuFocusLost(evt);
            }
        });
        jTConsu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTConsuKeyPressed(evt);
            }
        });
        jP1.add(jTConsu, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 50, 270, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("VENTAS:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 170, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("ALMACEN:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 170, -1));

        jTAlma.setEditable(false);
        jTAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAlma.setNextFocusableComponent(jTDevVta);
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
        jP1.add(jTAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 270, 20));

        jTDevVta.setEditable(false);
        jTDevVta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDevVta.setNextFocusableComponent(jComRem);
        jTDevVta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDevVtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDevVtaFocusLost(evt);
            }
        });
        jTDevVta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDevVtaKeyPressed(evt);
            }
        });
        jP1.add(jTDevVta, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 270, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("REMISIONES:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 200, 20));

        jComRem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "REMANUFACTURA PARA VENTA", "REMANUFACTURA PARA RENTA", "REMISIÓN PARA USO INTERNO" }));
        jComRem.setNextFocusableComponent(jTab);
        jComRem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComRemKeyPressed(evt);
            }
        });
        jP1.add(jComRem, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 270, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("DEV. SOBRE VENTAS:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 200, 20));

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
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todas las ventas de la base de datos y cargalos en la tabla*/
    private void vCargVta()
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
        
        /*Trae todas las facturas y notas de crédito de la base de datos*/
        try
        {
            sQ = "SELECT vta, norefer, noser, vtas.CODEMP, nom, subtot, impue, tot, femi, IFNULL(emps.CTACONTA,'') AS cta FROM vtas LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP) = vtas.CODEMP WHERE tipdoc IN('FAC','NOT', 'REM') ORDER BY vta DESC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Obtiene los totales*/
                String sSubTot              = rs.getString("subtot");
                String sImpue               = rs.getString("impue");
                String sTot                 = rs.getString("tot");
                
                /*Dale formato de moneda a los totales*/                
                java.text.NumberFormat n    = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US);
                double dCant                = Double.parseDouble(sSubTot);                
                sSubTot                     = n.format(dCant);
                dCant                       = Double.parseDouble(sImpue);                
                sImpue                      = n.format(dCant);
                dCant                       = Double.parseDouble(sTot);                
                sTot                        = n.format(dCant);

                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("vta"), rs.getString("norefer"), rs.getString("noser"), rs.getString("codemp"), rs.getString("nom"), sSubTot, sImpue, sTot, rs.getString("femi"), rs.getString("cta")};
                te.addRow(nu);
                
                /*Aumentar en uno el contador de pes*/
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
        
    }/*Fin de private void vCargVta()*/
    
    
    /*Obtiene las cuentas necesarias y cargalas en los campos*/
    private void vCargCtas()
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
        
        /*Obtiene las cuentas necesarias*/
        try
        {
            sQ = "SELECT cta1vta, cta2vta, cta3vta, cta4vta, ctadevsobvta FROM interbd";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca las cuentas en los controles*/
            if(rs.next())
            {                
                jTIvaPendPag.setText    (rs.getString("cta1vta"));
                jTVtas.setText          (rs.getString("cta2vta"));
                jTConsu.setText         (rs.getString("cta3vta"));
                jTAlma.setText          (rs.getString("cta4vta"));
                jTDevVta.setText        (rs.getString("ctadevsobvta"));
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
        
    }/*Fin de private void vCargCtas()*/
    
    
    /*Cuando se presiona el botón de hacer póliza de ingreso*/
    private void jBIngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBIngActionPerformed
                                                
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una venta para hacer la póliza.", "Póliza de Ingreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                       
            return;            
        }

        /*Si no a ingresado la cuenta de iva pendiente de pagar entonces*/
        if(jTIvaPendPag.getText().compareTo("")==0)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No se a definido la cuenta de iva pendiente por pagar.", "Póliza de Ingreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
         
            /*Coloca el borde rojo*/                               
            jTIvaPendPag.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTIvaPendPag.grabFocus();                       
            return;            
        }
        
        /*Si no a ingreso la cuenta de ventas entonces*/
        if(jTVtas.getText().compareTo("")==0)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No se a definido la cuenta de ventas.", "Póliza de Ingreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
         
            /*Coloca el borde rojo*/                               
            jTVtas.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTVtas.grabFocus();                       
            return;            
        }
        
        /*Si no a ingreso la cuenta de cosnumibles costo entonces*/
        if(jTConsu.getText().compareTo("")==0)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No se a definido la cuenta de consumibles costo.", "Póliza de Ingreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
         
            /*Coloca el borde rojo*/                               
            jTConsu.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTConsu.grabFocus();                       
            return;            
        }
        
        /*Si no a ingreso la cuenta de almacén entonces*/
        if(jTAlma.getText().compareTo("")==0)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No se a definido la cuenta de almacén.", "Póliza de Ingreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
         
            /*Coloca el borde rojo*/                               
            jTAlma.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTAlma.grabFocus();                       
            return;            
        }
        
        /*Preguntar al usuario si esta seguro de querer hacer la(s) póliza(s)*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres crear la(s) póliza(s)?", "Póliza Ingreso", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
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
        
        /*Obtiene el método de costeo de la empresa*/                
        String sMetCost     = "";
        try
        {
            sQ = "SELECT metcost FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces obtiene el resutlado*/
            if(rs.next())
                sMetCost    = rs.getString("metcost");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }

        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {            
            /*Declara variables para obtener algunos datos de la venta*/
            String sCtaCli  = "";            
            String sSubTot  = "";
            String sImpue   = "";
            String sTot     = "";            
            String sNoRefer = "";
            String sFDoc    = "";
            String sCli     = "";
            String sNom     = "";
            String sTip     = "";
            
            /*Obtiene algunos datos de la venta*/                
            try
            {
                sQ = "SELECT vtas.TIPDOC, vtas.CODEMP, emps.NOM, femi, norefer, ctaconta, tot, subtot, impue FROM vtas LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP) = vtas.CODEMP WHERE vta = " + jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces obtiene los resultados*/
                if(rs.next())
                {
                    sCtaCli = rs.getString("ctaconta");
                    sSubTot = rs.getString("subtot");
                    sImpue  = rs.getString("impue");
                    sTot    = rs.getString("tot");                                                            
                    sNoRefer= rs.getString("norefer");                                        
                    sFDoc   = rs.getString("femi");                                        
                    sCli    = rs.getString("codemp");                                        
                    sNom    = rs.getString("nom");
                    sTip    = rs.getString("tipdoc");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                    
            }                        

            /*Obtiene si la cuenta del cliente existe*/                
            try
            {
                sQ = "SELECT ctaconta FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCli + "'";                                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si no hay datos entonces*/
                if(rs.next())
                {
                    /*Si no tiene cuenta contable definida entonces*/
                    if(rs.getString("ctaconta").compareTo("")==0)
                    {
                        /*Mensajea y continua*/
                        JOptionPane.showMessageDialog(null, "La cuenta contable del cliente: " + sCli + " no esta defindia.", "Cuenta Contable", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                        continue;
                    }                                            
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                    
            }
            
            /*Recorre todas las partidas de la venta para obtener el costo total por método de costeo*/                
            String sTotCost = "0";
            try
            {
                sQ = "SELECT cost * cant AS cost, costprom * cant AS costprom, peps * cant AS peps, ueps * cant AS ueps, prod FROM partvta WHERE vta = " + jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces recorre los resultados*/
                while(rs.next())
                {
                    /*Obtiene el método de costeo que utiliza el producto*/
                    String sMet = sMetProd(con, rs.getString("prod"));
                    
                    /*Si hubo error entonces*/
                    if(bErr)
                    {
                        /*Resetea la bandera de error*/
                        bErr    = false;
            
                        //Cierra la base de datos y regresa
                        Star.iCierrBas(con);
                        return;
                    }
                    
                    /*Si hay nulo entonces el método de costeo será igual al de la empresa*/
                    if(sMet==null)
                        sMet    = sMetCost;
                    
                    /*Determina que costeo debe de ir sumando*/
                    if(sMet.compareTo("peps")==0)
                        sTotCost    = Double.toString(Double.parseDouble(sTotCost) + Double.parseDouble(rs.getString("peps")));
                    else if(sMet.compareTo("ueps")==0)
                        sTotCost    = Double.toString(Double.parseDouble(sTotCost) + Double.parseDouble(rs.getString("ueps")));
                    if(sMet.compareTo("ultcost")==0)
                        sTotCost    = Double.toString(Double.parseDouble(sTotCost) + Double.parseDouble(rs.getString("cost")));
                    if(sMet.compareTo("prom")==0)
                        sTotCost    = Double.toString(Double.parseDouble(sTotCost) + Double.parseDouble(rs.getString("costprom")));
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                    
            }                        
            
            /*Si va a ser remisión entonces*/
            if(sTip.compareTo("REM")==0)
            {
                /*Contiene las cuentas para los asientos de remisiones*/
                String sCta1;
                String sCta2;

                /*Contiene el conepto*/
                String sConcep  = "REMANUFACTURA VENTA ";
                
                /*Determina las cuentas que tomara para la lectura*/
                sCta1           = "ctaremvta1";
                sCta2           = "ctaremvta2";
                if(jComRem.getSelectedItem().toString().compareTo("REMANUFACTURA PARA RENTA")==0)
                {
                    /*Las cuentas serán estas*/
                    sCta1           = "ctaremrta1";
                    sCta2           = "ctaremrta2";
                    
                    /*El concepto será este*/
                    sConcep         = "REMANUFACTURA RENTA ";
                }
                else if(jComRem.getSelectedItem().toString().compareTo("REMISIÓN PARA USO INTERNO")==0)
                {
                    /*Las cuentas serán estas*/
                    sCta1           = "ctaremint1";
                    sCta2           = "ctaremint2";
                    
                    /*El concepto será este*/
                    sConcep         = "REMISIÓN USO INTERNO ";
                }
                    
                /*Obtiene las cuentas a utilizar*/                
                try
                {
                    sQ = "SELECT " + sCta1 + ", " + sCta2 + " FROM interbd";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos, entonces obtiene los resultados*/
                    if(rs.next())
                    {
                        sCta1   = rs.getString(sCta1);
                        sCta2   = rs.getString(sCta2);                        
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                                                                                                                       
                }
                
                /*Completa el concepto*/
                sConcep  += "<" + sNoRefer + "> " + sFDoc + " " + sCli + " " + sNom;
                
                /*Manda a llamar join data para la interfaz de las remisiones*/
                try
                {
                    Process pro = new ProcessBuilder("joindata.exe", sInst, sUsr, sContra, sBD, "vtarem", sUsrConta, sNomConta, sContraConta, sCta1.replace("-", "").replace(" ", "").trim(), sCta2.replace("-", "").replace(" ", "").trim(), sSubTot, sConcep, sNoRefer).start();                
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                   
                    return;                                                                                                                    
                }            
                
            }/*Fin de if(sTip.compareTo("REM")==0)*/
            /*No será remisión entonces*/
            else
            {
                /*Inicialmente la cuenta será la de ventas*/
                String sCtaVar  = jTVtas.getText().replace("-", "").replace(" ", "").trim();

                /*Inicialmente el concepto será de factura*/
                String sConcep              = "VENTA: <" + sNoRefer + "> " + sFDoc + " " + sCli + ": " + sNom;

                /*Determina que proceso se llamara en joindata*/            
                if(sTip.compareTo("FAC")==0)                                        
                    sTip    = "vtadiar";                                        
                /*Else es nota de crédito entonces*/
                else if(sTip.compareTo("NOT")==0)            
                {
                    /*Será de tipo de nota de crédito*/
                    sTip    = "vtadiarn";
                    sConcep = "NOTA CRÉDITO: <" + sNoRefer + "> " + sFDoc + " " + sCli + ": " + sNom;

                    /*La cuenta será la de devoluciones sobre ventas*/
                    sCtaVar = jTDevVta.getText().replace("-", "").replace(" ", "").trim();
                }
                /*Else es remisión entonces*/
                else if(sTip.compareTo("REM")==0)            
                {
                    /*Será de tipo de remisión*/
                    sTip    = "vtarem";
                    sConcep = "NOTA CRÉDITO: <" + sNoRefer + "> " + sFDoc + " " + sCli + ": " + sNom;

                    /*La cuenta será la de devoluciones sobre ventas*/
                    sCtaVar = jTDevVta.getText().replace("-", "").replace(" ", "").trim();
                }
                
                /*Manda a llamar join data para la interfaz de las ventas*/
                try
                {
                    Process pro = new ProcessBuilder("joindata.exe", sInst, sUsr, sContra, sBD, sTip, sUsrConta, sNomConta, sContraConta, sCtaCli.replace("-", "").replace(" ", "").trim(), jTIvaPendPag.getText().replace("-", "").replace(" ", "").trim(), sCtaVar, jTConsu.getText().replace("-", "").replace(" ", "").trim(), jTAlma.getText().replace("-", "").replace(" ", "").trim(), sTot, sImpue, sSubTot, sTotCost, sConcep, sNoRefer).start();                
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                   
                    return;                                                                                                                    
                }            
                
            }/*Fin de else*/                            
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                    
                    
        //Cierra la base de datos
        Star.iCierrBas(con);
                               
    }//GEN-LAST:event_jBIngActionPerformed
   
   
    /*Obtiene el método de costeo que utiliza el producto*/
    private String sMetProd(Connection con, String sProd)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        
        /*Obtiene el método de costeo del producto*/                
        try
        {                  
            sQ = "SELECT metcost FROM prods WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces devuelve el resultado*/
            if(rs.next())
                return rs.getString("metcost");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return null;                                                                                                                    
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
        {
            //Coloca la bandera de error y regresa
            bErr    = true;
            return null;
        }            

        /*Devuelve que no encontro nada*/
        return null;
        
    }/*Fin de private String sMetProd(Connection con, String sProd)*/
                    
                    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBIngKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBIngKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBIngKeyPressed
    
    
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

    
    
    /*Cuando se presiona una tecla en el campo de la cuenta de la cuenta de las ventas*/
    private void jTVtasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTVtasKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTVtasKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta de las ventas*/
    private void jTVtasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTVtasFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTVtas.setSelectionStart(0);jTVtas.setSelectionEnd(jTVtas.getText().length());        
        
    }//GEN-LAST:event_jTVtasFocusGained

    
    /*Cuando se presiona una tecla en el campo de la cuenta del iva pendiente de pagar*/
    private void jTIvaPendPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTIvaPendPagKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTIvaPendPagKeyPressed

    
    /*Cuando se presiona una  tecla en la tabla de pes*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de la cuenta del iva pendiente de pagar*/
    private void jTIvaPendPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTIvaPendPagFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTIvaPendPag.setSelectionStart(0);jTIvaPendPag.setSelectionEnd(jTIvaPendPag.getText().length());        
        
    }//GEN-LAST:event_jTIvaPendPagFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta de las ventas*/
    private void jTVtasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTVtasFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTVtas.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTVtas.getText().compareTo("")!=0)
            jTVtas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTVtasFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta del iva pendiente de pagar*/
    private void jTIvaPendPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTIvaPendPagFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTIvaPendPag.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTIvaPendPag.getText().compareTo("")!=0)
            jTIvaPendPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTIvaPendPagFocusLost
           
    
    
   
    
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

    
    /*Función para mostrar nuevamente los elementos actualizados en la tabla*/
    private void vCargT()
    {
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtiene todos los clientes de la base de datos y cargalos en la tabla*/
        vCargVta();
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();        
    }
    
    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed
        
        /*Función para mostrar nuevamente los elementos actualizados en la tabla*/
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
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi              = 1;
                
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todos los clientes*/        
        try
        {                  
            sQ = "SELECT CONCAT_WS('', ser, codemp) AS codemp, nom, deposit  FROM emps WHERE CONCAT_WS('', ser, codemp) LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR nom LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR deposit LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene el depósito en garantía*/
                String sDepGar   = rs.getString("deposit");
                
                /*Dale formato de moneda al depósito en garantía*/                
                java.text.NumberFormat n    = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US);
                double dCant                = Double.parseDouble(sDepGar);                
                sDepGar                     = n.format(dCant);

                /*Agregalo a la tabla*/
                Object nu[]     = {iContFi, rs.getString("codemp"), rs.getString("nom"), sDepGar};
                dm.addRow(nu);

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

        /*Vuelve a poner el foco del teclado en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBBuscActionPerformed

            
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

                    
    /*Cuando el mouse entra en el botón de búscar*/
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
        jBBusc.setBackground(Star.colOri);
        
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
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
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

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMosTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMosT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMosTMouseEntered


    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited


    /*Cuando el mouse sale del botón específico*/
    private void jBIngMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBIngMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBIng.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBIngMouseExited
        
    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMosTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMosT.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBMosTMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBIng);
        
        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta de los consumibles*/
    private void jTConsuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTConsuFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTConsu.setSelectionStart(0);jTConsu.setSelectionEnd(jTConsu.getText().length());        
        
    }//GEN-LAST:event_jTConsuFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta de los consumibles*/
    private void jTConsuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTConsuFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTConsu.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTConsu.getText().compareTo("")!=0)
            jTConsu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTConsuFocusLost

    
    /*Cuando se presiona una tecla en el campo de la cuenta de los consumibles*/
    private void jTConsuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTConsuKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTConsuKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta del almacén*/
    private void jTAlmaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAlma.setSelectionStart(0);jTAlma.setSelectionEnd(jTAlma.getText().length());        
        
    }//GEN-LAST:event_jTAlmaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta del almacén*/
    private void jTAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAlma.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAlma.getText().compareTo("")!=0)
            jTAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTAlmaFocusLost

    
    /*Cuando se presiona una tecla en el campo de la cuenta del almacén*/
    private void jTAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAlmaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAlmaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta de devoluciones sobre ventas*/
    private void jTDevVtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDevVtaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDevVta.setSelectionStart(0);jTDevVta.setSelectionEnd(jTDevVta.getText().length());        
        
    }//GEN-LAST:event_jTDevVtaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de las devoluciones sobre venta*/
    private void jTDevVtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDevVtaFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDevVta.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDevVta.getText().compareTo("")!=0)
            jTDevVta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTDevVtaFocusLost

    
    /*Cuando se presiona uan tecla en el campo de devoluciones sobre venta*/
    private void jTDevVtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDevVtaKeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDevVtaKeyPressed

    
    /*Cuando se presiona una tecla en el combo de remisiones*/
    private void jComRemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComRemKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComRemKeyPressed

                      
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
            jBTod.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBIng.doClick();
        /*Si se presiona F3 presiona el botón de bùscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBIng;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTod;
    private javax.swing.JComboBox jComRem;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTAlma;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTConsu;
    private javax.swing.JTextField jTDevVta;
    private javax.swing.JTextField jTIvaPendPag;
    private javax.swing.JTextField jTVtas;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
