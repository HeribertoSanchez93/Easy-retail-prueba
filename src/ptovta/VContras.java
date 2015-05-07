//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para ver y reportear los contrarecibos*/
public class VContras extends javax.swing.JFrame 
{
    /*Declara variables de instancia*/    
    public static int       iContFi;

    /*Contador para modificar tabla*/
    private int             iContCellEd;
 
    /*Declara variables origianles*/
    private String          sContraOri;
    private String          sProvOri;
    private String          sNomOri;
    private String          sComprOri;
    private String          sTotOri;
    private String          sResponOri;
    private String          sFechOri;
    private String          sSucOri;
    private String          sCajOri;
    private String          sEstacOri;
    private String          sNomEstacOri;
    
    /*Variable que contiene el borde actual*/
    private Border          bBordOri;

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;
    
    
    
    /*Constructor sin argumentos*/
    public VContras() 
    {                                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Ver contra recibos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Inicia el contador de filas en 1 inicialmente*/
        iContFi      = 1;
                
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla de contrarecibos*/
        jTab.grabFocus();
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Carga todos los contrareicibos en la tabla*/
        vCargContra();                    
        
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
                        sContraOri          = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sProvOri            = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sNomOri             = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sComprOri           = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sTotOri             = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sResponOri          = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sFechOri            = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sSucOri             = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sCajOri             = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sEstacOri           = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sNomEstacOri        = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sContraOri,         jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sProvOri,           jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sNomOri,            jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sComprOri,          jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sTotOri,            jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sResponOri,         jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sFechOri,           jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sSucOri,            jTab.getSelectedRow(), 8);
                        jTab.setValueAt(sCajOri,            jTab.getSelectedRow(), 9);
                        jTab.setValueAt(sEstacOri,          jTab.getSelectedRow(), 10);
                        jTab.setValueAt(sNomEstacOri,       jTab.getSelectedRow(), 11);
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public VContras() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBVis = new javax.swing.JButton();
        jBMosT = new javax.swing.JButton();
        jBPDF = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jRTic = new javax.swing.JRadioButton();
        jRFac = new javax.swing.JRadioButton();
        jBImp = new javax.swing.JButton();
        jBActua = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Contra recibos:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 170, 20));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jTab);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 210, 120, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "No.Contrarecibo", "Proveedor", "Nombre", "Compra", "Total", "Responsable", "Fecha", "Sucursal", "No. Caja", "Usuario", "Nombre Usuario"
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 850, 540));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBBusc.setForeground(new java.awt.Color(0, 102, 0));
        jBBusc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busc5.png"))); // NOI18N
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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 140, 19));

        jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBusc.setNextFocusableComponent(jBMosT);
        jTBusc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBuscFocusGained(evt);
            }
        });
        jTBusc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTBuscActionPerformed(evt);
            }
        });
        jTBusc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscKeyPressed(evt);
            }
        });
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 530, 570, 20));

        jBVis.setBackground(new java.awt.Color(255, 255, 255));
        jBVis.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVis.setForeground(new java.awt.Color(0, 102, 0));
        jBVis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/visor.png"))); // NOI18N
        jBVis.setText("Reporte");
        jBVis.setToolTipText("Reporte de Contra recibos (F2)");
        jBVis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVis.setNextFocusableComponent(jBImp);
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
        jP1.add(jBVis, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 60, 120, 30));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setNextFocusableComponent(jBPDF);
        jBMosT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMosTMouseEntered(evt);
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 530, 140, 19));

        jBPDF.setBackground(new java.awt.Color(255, 255, 255));
        jBPDF.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBPDF.setForeground(new java.awt.Color(0, 102, 0));
        jBPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/pdf.png"))); // NOI18N
        jBPDF.setText("Ver");
        jBPDF.setToolTipText("Ver PDF");
        jBPDF.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBPDF.setNextFocusableComponent(jBVis);
        jBPDF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBPDFMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBPDFMouseExited(evt);
            }
        });
        jBPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPDFActionPerformed(evt);
            }
        });
        jBPDF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPDFKeyPressed(evt);
            }
        });
        jP1.add(jBPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 30, 120, 30));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jRTic.setBackground(new java.awt.Color(255, 255, 255));
        jRTic.setSelected(true);
        jRTic.setText("Imp. Tickets");
        jRTic.setNextFocusableComponent(jRFac);
        jRTic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRTicKeyPressed(evt);
            }
        });

        jRFac.setBackground(new java.awt.Color(255, 255, 255));
        jRFac.setText("Imp. Facturas");
        jRFac.setNextFocusableComponent(jBActua);
        jRFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRFacKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRFac, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(jRTic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRTic)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRFac))
        );

        jP1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 120, 150, 50));

        jBImp.setBackground(new java.awt.Color(255, 255, 255));
        jBImp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBImp.setForeground(new java.awt.Color(0, 102, 0));
        jBImp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impres.png"))); // NOI18N
        jBImp.setText("Imprimir");
        jBImp.setToolTipText("Imprimir Contrarecibo(s)  (Ctrl+P)");
        jBImp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBImp.setNextFocusableComponent(jRTic);
        jBImp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBImpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBImpMouseExited(evt);
            }
        });
        jBImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImpActionPerformed(evt);
            }
        });
        jBImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBImpKeyPressed(evt);
            }
        });
        jP1.add(jBImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 90, 120, 30));

        jBActua.setBackground(new java.awt.Color(255, 255, 255));
        jBActua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBActua.setForeground(new java.awt.Color(0, 102, 0));
        jBActua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actualizar.png"))); // NOI18N
        jBActua.setText("Actualizar");
        jBActua.setToolTipText("Actualizar Registros (F5)");
        jBActua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBActua.setName(""); // NOI18N
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
        jP1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 180, 120, 30));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 550, 110, -1));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 12, 130, 18));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 990, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
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

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra el formulario*/
        this.dispose();        
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en la tabla de contrarecibos*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jTabKeyPressed

            
    /*Cuando se hace un clic en la tabla de contrarecibos*/
    private void jTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabMouseClicked
        
        /*Si se hiso doble clic entonces presiona el botón de ver PDF*/
        if(evt.getClickCount() == 2) 
            jBPDF.doClick();
        
    }//GEN-LAST:event_jTabMouseClicked

        
    /*Cuando se gana el foco del teclado en el campo de buscar*/
    private void jTBuscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusGained
        
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

    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBBuscKeyPressed
    
    
    /*Cuando se presiona una tecla en el botón de mostrar todo*/
    private void jBMosTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMosTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBMosTKeyPressed
    
    
    /*Función para mostrar nuevamente todos los elementos actualizados en la tabla*/
    private void vCargT()
    {
        /*Borra todos los item en la tabla de facturas*/
        DefaultTableModel dm    = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi                 = 1;
        
        /*Carga todos los contrarecibos*/
        vCargContra();  
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();        
    }
    
    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed
        
        /*Carga todos los contrarecibos de la base de datos*/
        vCargContra();
        
    }//GEN-LAST:event_jBMosTActionPerformed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed
        
        /*Lee el campo de buscar*/
        String sBusc                    = jTBusc.getText();
        
        /*Si el campo de buscar esta vacio no puede seguir*/
        if(sBusc.compareTo("")==0)
        {
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
        DefaultTableModel dm            = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi                  = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todos los contrarecibos*/        
        try
        {                  
            sQ = "SELECT contra.SUCU, contra.NOCAJ, estacs.NOM, contra.ID_ID, contra.PROV, provs.NOM, contra.COMP, contra.TOT, contra.RESPON, contra.FECH, contra.ESTAC FROM contra LEFT OUTER JOIN estacs ON estacs.ESTAC = contra.ESTAC LEFT OUTER JOIN provs ON provs.PROV = contra.PROV WHERE contra.ID_ID LIKE('%" + sBusc.replace(" ", "%") + "%') OR contra.PROV LIKE('%" + sBusc.replace(" ", "%") + "%') OR contra.COMP LIKE('%" + sBusc.replace(" ", "%") + "%') OR contra.TOT LIKE('%" + sBusc.replace(" ", "%") + "%') OR contra.RESPON LIKE('%" + sBusc.replace(" ", "%") + "%') OR contra.FECH LIKE('%" + sBusc.replace(" ", "%") + "%') OR contra.ESTAC LIKE('%" + sBusc.replace(" ", "%") + "%') OR estacs.NOM LIKE('%" + sBusc.replace(" ", "%") + "%') ORDER BY id_comp DESC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Declara variables de bloque*/
                String sTot;
                
                /*Obtiene el total*/
                sTot            = rs.getString("contra.TOT");   
                
                /*Dale formato de moneda al total*/                
                double dCant    = Double.parseDouble(sTot);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sTot            = n.format(dCant);
            
                /*Agregalo a la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("contra.ID_ID"), rs.getString("contra.PROV"), rs.getString("provs.NOM"), rs.getString("contra.COMP"), sTot, rs.getString("contra.RESPON"), rs.getString("contra.FECH"), rs.getString("contra.SUCU"), rs.getString("contra.NOCAJ"), rs.getString("contra.ESTAC"), rs.getString("estacs.NOM")};
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
   
    
    /*Cuando se presiona el botón de visor*/
    private void jBVisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVisActionPerformed

        /*Si no hay contrarecibos entonces mensajea*/
        if(jTab.getRowCount()==0)
            JOptionPane.showMessageDialog(null, "No existen contra recibos.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));             
                
    }//GEN-LAST:event_jBVisActionPerformed

    
    /*Cuando se presiona una tecla en el botón de visor*/
    private void jBVisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVisKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVisKeyPressed

    
    /*Cuando se presiona el botón de ver PDF*/
    private void jBPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPDFActionPerformed
                                        
        /*Si el usuario no a seleccionado una registro no puede avanzar*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un contrarecibo.", "Contra recibos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();           
            return;
            
        }/*Fin de if(jTabVents.getSelectedRow()==-1)*/
        
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
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
            return;                        
        }  
        
        //Declara variables locales
        String  sContra;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Obtiene algunos datos de la fila*/        
            sContra         = jTab.getValueAt(iSel[x], 1).toString();  
            
            /*Genera la cadena donde se buscará el pdf*/
            String sRut = sCarp + "\\Contrarecibos\\" + sContra + ".pdf";

            /*Si no existe el archivo PDF entonces*/
            if(!new File(sRut).exists())
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "El contrarecibo  \"" + sRut + "\" no existe.", "Contrarecibo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                continue;
            }

            /*Abre el archivo PDF*/
            try 
            {
                Desktop.getDesktop().open(new File(sRut));
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                       
            }
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                         
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jBPDFActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver PDF*/
    private void jBPDFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPDFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBPDFKeyPressed

    
    /*Cuando se presiona una tecla en el botón de imprimir*/
    private void jBImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBImpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBImpKeyPressed

    
    /*Cuando se presiona el botón de imprimir*/
    private void jBImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImpActionPerformed
        
        /*Si no hay selección en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)            
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un contrarecibo.", "Contrarecibo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Colocal el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                        
            return;
        }
                
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
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
            return;                        
        }  
        
        /*Preguntar al usuario si esta seguro de querer imprimir el contrarecibo*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres imprimir el(los) contrarecibo(s)?", "Contrarecibo(s)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;                       
        }        
         
        /*Obtiene la impresora predeterminada actual*/
        PrintService service        = PrintServiceLookup.lookupDefaultPrintService(); 
        String sImpAnt              = service.getName();

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Declara variables locales
        String sImprFac    = "";
        String sImprTic    = "";        
        
        /*Obtiene el nombre de la impresora que tiene configurada El usuario actual para tickets y facturas*/        
        try
        {
            sQ = "SELECT imptic, impfac FROM estacs WHERE estac = '" + Login.sUsrG + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene el nombre de la impresora y la medida del ticket*/
                sImprTic            = rs.getString("imptic");
                sImprFac            = rs.getString("impfac");                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }   
        
        /*Cambia la impresora predeterminada dependiendo si quiere imprimir en la de facturas o en la de tickets*/
        if(jRTic.isSelected())
            vCambImp(sImprTic);
        else
            vCambImp(sImprFac);
                
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();       
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Obtiene el número de contrarecibo*/        
            String sContra  = jTab.getValueAt(iSel[x], 1).toString();                

            /*Completa la ruta al archivo*/
            String sRut = sCarp + "\\Contrarecibos\\" + sContra + ".pdf";

            /*Si el archivo no existe entonces*/
            if(!new File(sRut).exists())
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "No existe el contrarecibo \"" + sRut + "\".", "Contrarecibo",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));               
                continue;
            }                        
            
            /*Imprime el archivo pdf*/
            try 
            {
                /*Si la impresión esta soportada entonces*/            
                if(Desktop.isDesktopSupported()) 
                {
                    /*Obtiene handler para escritorio*/
                    Desktop desktop = Desktop.getDesktop();

                    /*Imprime el archivo*/
                    desktop.print(new File(sRut)); 
                }

            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                return;                                                                                                                    
            }   

        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                    
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Cambia la impresora predeterminda anterior*/
        vCambImp(sImpAnt);
        
    }//GEN-LAST:event_jBImpActionPerformed

    
    /*Cambia la impresora predeterminada */
    public static void vCambImp(String sImp)
    {
        //Declara variables locales
        String sCmd = "RUNDLL32 PRINTUI.DLL,PrintUIEntry /y /n \"" + sImp + "\"";
        
        
             
        /*Cambia la impresora predeterminada*/
        try
        {
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(sCmd);
            pr.waitFor();
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(VContras.class.getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());            
        }
        catch(InterruptedException expnInterru)
        {
            //Procesa el error
            Star.iErrProc(VContras.class.getName() + " " + expnInterru.getMessage(), Star.sErrInterru, expnInterru.getStackTrace());            
        }
        
    }/*Fin de private void vCambImp(String sImp)*/
        
    
    /*Cuando se presiona una tecla en el radio button del ticket*/
    private void jRTicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRTicKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRTicKeyPressed

    
    /*Cuando se presiona una tecla en el radio button de factura*/
    private void jRFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRFacKeyPressed

    
    /*Cuando se presiona el botón de actualizar*/
    private void jBActuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActuaActionPerformed

        /*Función para cargar todos los elementos en la tabla*/
        vCargT();

    }//GEN-LAST:event_jBActuaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de actualizar*/
    private void jBActuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBActuaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBActuaKeyPressed

    
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

    private void jTBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTBuscActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTBuscActionPerformed

    
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
    private void jBPDFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPDFMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBPDF.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBPDFMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVisMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVisMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVis.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVisMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBImpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBImp.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBImpMouseEntered

    
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
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBPDFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPDFMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBPDF.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBPDFMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVisMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVisMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVis.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBVisMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBImpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBImp.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBImpMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBActuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBActua.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBActuaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited
   
    
    /*Carga todos los contrarecibos de la base de datos*/
    private void vCargContra()
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
        ResultSet   rs ;        
        String      sQ;
      
        /*Trae todos los contrarecibos y cargalos en la tabla*/
        try
        {
            sQ = "SELECT estacs.NOM, contra.SUCU, contra.NOCAJ, contra.ID_ID, contra.PROV, contra.COMP, provs.NOM, contra.TOT, contra.RESPON, contra.FECH, contra.ESTAC FROM contra LEFT OUTER JOIN estacs ON estacs.ESTAC = contra.ESTAC LEFT OUTER JOIN provs ON provs.PROV = contra.PROV ORDER BY contra.ID_ID DESC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                 
                /*Obtiene el total*/
                String sTot     = rs.getString("contra.TOT");
                
                /*Dale formato de moneda al total*/                
                double dCant    = Double.parseDouble(sTot);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sTot            = n.format(dCant);
        
                /*Agregalos a la tabla*/
                Object nu[]     = {iContFi, rs.getString("contra.ID_ID"), rs.getString("contra.PROV"), rs.getString("provs.NOM"), rs.getString("contra.COMP"), sTot, rs.getString("contra.RESPON"), rs.getString("contra.FECH"), rs.getString("contra.SUCU"), rs.getString("contra.NOCAJ"), rs.getString("contra.ESTAC"), rs.getString("estacs.NOM")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas de las compras*/
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
        
    }/*Fin de private void vCargContra()*/
        
        
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el boton de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
            jBTod.doClick();
        /*Si se presiona F2 presiona el botón de visor*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBVis.doClick();        
        /*Si se presiona F3 presiona el botón de búscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();
        /*Si se presiona CTRL + P entonces presiona el botón de imprimir*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_P)
            jBImp.doClick();
        /*Si se presiona F5 presiona el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBImp;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBPDF;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBVis;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRFac;
    private javax.swing.JRadioButton jRTic;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Empresas extends javax.swing.JFrame */
