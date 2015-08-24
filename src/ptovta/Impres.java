//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
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
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import static ptovta.Princip.bIdle;




/*Clase para controlar las impresoras*/
public class Impres extends javax.swing.JFrame 
{
    /*Declara variables de instancia*/    
    private int             iContFi;

    /*Variable que almacena el borde del botón de búscar*/
    private Border          bBordOri;
    
    /*Contador para modificar tabla*/
    private int             iContCellEd;
    
    /*Declara variables originales*/
    private String          sEstacOri;
    private String          sImpreFOri;
    private String          sImpreTOri;
    private String          sMMOri;
    private String          sCortOri;

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;
    
    
    
    /*Constructor sin argumentos*/
    public Impres() 
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
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Impresoras, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en 1*/
        iContFi      = 1;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el campo de El usuario*/
        jTEstacs.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de unidades*/        
        jTab.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(150);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
                
        /*Obtiene todas los usuarios que no tienen cadena vacia en el campo de la impresa*/
        CargEstacs();
        
        /*Carga las impresoras en los comboboxes de las imresoras*/
        vCargaImpre();
                    
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
                        sEstacOri           = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sImpreFOri          = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sImpreTOri          = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sMMOri              = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sCortOri            = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sEstacOri,          jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sImpreFOri,         jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sImpreTOri,         jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sMMOri,             jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sCortOri,           jTab.getSelectedRow(), 5);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public Impres() */

    
    /*Carga las impresoras en el combobox*/
    private void vCargaImpre()
    {
        /*Obtiene las impresoras instaladas en el sistema*/
        PrintService[] ser = PrintServiceLookup.lookupPrintServices(null, null);        
        for (PrintService service : ser) 
        {
            jComImpTick.addItem(service.getName());
            jComImpFact.addItem(service.getName());
        }        
    }
       
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBDel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMosT = new javax.swing.JButton();
        jComImpTick = new javax.swing.JComboBox();
        jTEstacs = new javax.swing.JTextField();
        jBBusEsta = new javax.swing.JButton();
        jBProbTick = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComImpFact = new javax.swing.JComboBox();
        jBProbFac = new javax.swing.JButton();
        jC52m = new javax.swing.JCheckBox();
        jCCort = new javax.swing.JCheckBox();
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

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Asosiaciòn(es) (Ctrl+SUPR)");
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 80, 90, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Impresora Facturas:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 170, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jTEstacs);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 110, 90, 30));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nueva Asociaciòn (Ctrl+N)");
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, 90, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Usuarios:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 140, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("*Usuario:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Usuario", "Impresora Facturas", "Impresora Tickets", "52mm", "Corte"
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
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 630, 280));

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
                jButtonBuscarActionPerformed(evt);
            }
        });
        jBBusc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonBuscarKeyPressed(evt);
            }
        });
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 140, 19));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, 350, 20));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setName(""); // NOI18N
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 360, 140, 19));

        jComImpTick.setNextFocusableComponent(jC52m);
        jComImpTick.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComImpTickFocusLost(evt);
            }
        });
        jComImpTick.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComImpTickKeyPressed(evt);
            }
        });
        jP1.add(jComImpTick, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 170, 20));

        jTEstacs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEstacs.setNextFocusableComponent(jBBusEsta);
        jTEstacs.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstacsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstacsFocusLost(evt);
            }
        });
        jTEstacs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstacsKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTEstacsKeyTyped(evt);
            }
        });
        jP1.add(jTEstacs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 20));

        jBBusEsta.setBackground(new java.awt.Color(255, 255, 255));
        jBBusEsta.setText("...");
        jBBusEsta.setToolTipText("Buscar Usuario(s)");
        jBBusEsta.setNextFocusableComponent(jComImpTick);
        jBBusEsta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBusEstaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBusEstaMouseExited(evt);
            }
        });
        jBBusEsta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBusEstaActionPerformed(evt);
            }
        });
        jBBusEsta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBusEstaKeyPressed(evt);
            }
        });
        jP1.add(jBBusEsta, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 30, 20));

        jBProbTick.setBackground(new java.awt.Color(255, 255, 255));
        jBProbTick.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBProbTick.setText("Probar");
        jBProbTick.setNextFocusableComponent(jComImpFact);
        jBProbTick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProbTickMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProbTickMouseExited(evt);
            }
        });
        jBProbTick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProbTickActionPerformed(evt);
            }
        });
        jBProbTick.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProbTickKeyPressed(evt);
            }
        });
        jP1.add(jBProbTick, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Impresora Tickets:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 170, -1));

        jComImpFact.setNextFocusableComponent(jBProbFac);
        jComImpFact.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComImpFactFocusLost(evt);
            }
        });
        jComImpFact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComImpFactKeyPressed(evt);
            }
        });
        jP1.add(jComImpFact, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 160, 20));

        jBProbFac.setBackground(new java.awt.Color(255, 255, 255));
        jBProbFac.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBProbFac.setText("Probar");
        jBProbFac.setNextFocusableComponent(jBNew);
        jBProbFac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProbFacMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProbFacMouseExited(evt);
            }
        });
        jBProbFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProbFacActionPerformed(evt);
            }
        });
        jBProbFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProbFacKeyPressed(evt);
            }
        });
        jP1.add(jBProbFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, -1, 20));

        jC52m.setBackground(new java.awt.Color(255, 255, 255));
        jC52m.setText("52mm");
        jC52m.setNextFocusableComponent(jCCort);
        jC52m.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jC52mKeyPressed(evt);
            }
        });
        jP1.add(jC52m, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 55, 70, 20));

        jCCort.setBackground(new java.awt.Color(255, 255, 255));
        jCCort.setText("Corte");
        jCCort.setNextFocusableComponent(jBProbTick);
        jCCort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCortKeyPressed(evt);
            }
        });
        jP1.add(jCCort, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 55, 80, 20));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(646, 370, 130, 20));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 130, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todos las estacs que no tengan cadena vacia en la impresora de la base de datos y cargalos en la tabla*/
    private void CargEstacs()
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
        
        /*Trae todas las estacs de la base de datos que no tengan cadena vacia en la impresora y cargalos en la tabla*/
        try
        {
            sQ = "SELECT estac, impfac, imptic, 52m, cort FROM estacs WHERE impfac <> '' AND imptic <> ''";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Declara variables*/
                String s52;
                String sCort;
                
                /*Comprueba si es de cort o no*/
                if(rs.getString("cort").compareTo("1")==0)                
                    sCort = "Si";
                else
                    sCort = "No";
                
                /*Comprueba si es de 52 o no*/
                if(rs.getString("52m").compareTo("1")==0)                
                    s52 = "Si";
                else
                    s52 = "No";
                                                            
                /*Agregalos a la tabla*/
                Object nu[]= {iContFi, rs.getString("estac"), rs.getString("impfac"), rs.getString("imptic"), s52, sCort};
                tm.addRow(nu);
                
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
        
    }/*Fin de private void CargEstacs()*/
    
    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un registro de la tabla.", "Borrar Registro", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();            
            return;            
        }
        
        /*Preguntar al usuario si esta seguro de querer borrar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere borrar el(los) registro(s)?", "Borrar Registro", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
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

        //Declara variables locales       
        String      sEsta;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Obtén el usuario y la impresora selecconada*/        
            sEsta               = jTab.getValueAt(iSel[x], 1).toString();                  
            
            /*Borra el registro*/
            try 
            {                
                sQ = "UPDATE estacs SET "
                        + "impfac       = '', "
                        + "imptic       = '', "
                        + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE estac  = '" + sEsta.replace("'", "''") + "'";                    
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
        JOptionPane.showMessageDialog(null, "Registro(s) borrado(s) con éxito.", "Registros", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
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
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {
            /*Llama al recolector de basura*/
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
                        
    
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
        
        //Declara variables locales
        String      sEst;        
        String      sImpTic;
        String      sImpFac;
        String      s52;
        String      sCort;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        
        
        
        
        /*Lee el El usuario que ingreso el usuario*/
        sEst    = jTEstacs.getText().trim();
        
        /*Si hay cadena vacia en el campo de El usuario no puede continuar*/
        if(sEst.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTEstacs.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El usuario esta vacia.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTEstacs.grabFocus();                        
            return;            
        }
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Checa si el código de el usuario existe en la base de datos*/        
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac = '" + sEst + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTEstacs.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Avisa al usuario*/
                JOptionPane.showMessageDialog(null, "El usuario: " + sEst + " no existe.", "Usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el campo de El usuario y regresa*/
                jTEstacs.grabFocus();               
                return;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }        
        
        /*Lee la impresora de tickets que ingreso el usuario*/
        sImpTic  = jComImpTick.getSelectedItem().toString();
        
        /*Si hay cadena vacia en el campo de la impresora de tickets no puede continuar*/
        if(sImpTic.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jComImpTick.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de la impresora de tickets esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el combobox y regresa*/
            jComImpTick.grabFocus();                        
            return;            
        }
        
        /*Lee la impresora de facturas que ingreso el usuario*/
        sImpFac  = jComImpFact.getSelectedItem().toString();
        
        /*Si hay cadena vacia en el campo de la impresora de facturas no puede continuar*/
        if(sImpFac.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jComImpFact.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de la impresora de facturas esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el combobox y regresa*/
            jComImpFact.grabFocus();                        
            return;            
        }
        
        /*Checa si ya existe El usuario con una impresora*/        
        try
        {
            sQ = "SELECT estac FROM estacs WHERE estac = '" + sEst + "' AND imptic <> ''";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos, entonces ya tiene impresora esta usuario*/
            if(rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
            
                /*Coloca el borde rojo*/                               
                jTEstacs.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Avisa al usuario*/
                JOptionPane.showMessageDialog(null, "Ya existen impresoras asignadas para el usuario: " + sEst + ".", "Usuario", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el camopo de El usuario y regresa*/
                jTEstacs.grabFocus();               
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
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Agregar Impresoras", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }
        
        /*Lee si va a ser de 52mm la impresora o no*/
        if(jC52m.isSelected())        
            s52 = "1";
        else
            s52 = "0";
        
        /*Lee si va a ser de cort la impresora o no*/
        if(jCCort.isSelected())        
            sCort = "1";
        else
            sCort = "0";
        
        /*Guarda los datos en la base de datos*/
        try 
        {            
            sQ = "UPDATE estacs SET "
                    + "imptic       = '" + sImpTic.replace("'", "''") + "', "
                    + "impfac       = '" + sImpFac.replace("'", "''") + "', "
                    + "52m          = " + s52 + ", "
                    + "cort         = '" + sCort + "', "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE estac  = '" + sEst.replace("'", "''") + "'";                    
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

        /*Pon con letra si es de 52mm o no*/
        if(s52.compareTo("1")==0)        
            s52 = "Si";
        else
            s52 = "No";
        
        /*Pon con letra si es de 52mm o no*/
        if(sCort.compareTo("1")==0)        
            sCort = "Si";
        else
            sCort = "No";
        
        /*Agrega el registro en la tabla*/
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFi, sEst, sImpFac, sImpTic, s52, sCort};
        tm.addRow(nu);
        
        /*Aumenta en uno el contador de filas en 1*/
        ++iContFi;
        
        /*Pon el foco del teclado en el campo de El usuario*/
        jTEstacs.grabFocus();
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Impresoras agregadas con éxito para El usuario \"" + sEst + "\".", "Exito al agregar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
         
    }//GEN-LAST:event_jBNewActionPerformed

    
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

        /*Obtiene todos los usuarios de la base de datos que no tengan en impresora cadena vacia y cargalos en la tabla*/
        CargEstacs();

        /*Vuelve a poner el foco del teclado en el campo de buscar*/
        jTBusc.grabFocus();

    }//GEN-LAST:event_jBMosTActionPerformed

    
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

    
    /*Cuando se gana el foco del teclado en el campo de buscar*/
    private void jTBuscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusGained

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBBusc);
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTBusc.setSelectionStart(0);jTBusc.setSelectionEnd(jTBusc.getText().length());

    }//GEN-LAST:event_jTBuscFocusGained

    
    /*Cuando se presiona una  tecla en la tabla de registros*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se presiona una tecla en el combbox de impresora de tickets*/
    private void jComImpTickKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComImpTickKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComImpTickKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de El usuario*/
    private void jTEstacsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstacsFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTEstacs.setSelectionStart(0);jTEstacs.setSelectionEnd(jTEstacs.getText().length());        

    }//GEN-LAST:event_jTEstacsFocusGained

        
    /*Cuando se presiona una tecla en el campo de usuario*/
    private void jTEstacsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstacsKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTEstacs.getText(), 4, jTEstacs, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normalmente*/
        else                    
            vKeyPreEsc(evt);        

    }//GEN-LAST:event_jTEstacsKeyPressed

    
    /*Cuando se tipe una tecla en el campo de El usuario*/
    private void jTEstacsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstacsKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTEstacsKeyTyped

    
    /*Cuando se presiona el botón de buscar usuario*/
    private void jBBusEstaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBusEstaActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTEstacs.getText(), 4, jTEstacs, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBBusEstaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar usuario*/
    private void jBBusEstaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBusEstaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBBusEstaKeyPressed

    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jButtonBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonBuscarKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jButtonBuscarKeyPressed

    
    /*Cuando se presiona el botón de buscar*/
    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed

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
        
        /*Borra todos los item en la tabla de registros*/
        DefaultTableModel dm        = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi              = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todos los usuarios que en la impresora no tengan cadena vacia*/        
        try
        {                  
            sQ = "SELECT * FROM estacs WHERE imptic <> '' AND impfac <> '' AND (estac LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR imptic LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR impfac LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR cort LIKE('%" + jTBusc.getText().replace(" ", "%") + "%'))";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene si es de cort o no*/
                String sCort;
                if(rs.getString("cort").compareTo("1")==0)
                    sCort   = "Si";
                else
                    sCort   = "No";
                
                /*Cargalo en la tabla*/
                DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("estac"), rs.getString("impfac"), rs.getString("imptic"), rs.getString("52m"), sCort};
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
        
    }//GEN-LAST:event_jButtonBuscarActionPerformed

        
    /*Cuando se presiona el botón de probar impresoa de tickets*/
    private void jBProbTickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProbTickActionPerformed
        
        //Declara variables locales
        String sImpre;
        
        
        
        
        /*Lee la impresora seleccionada*/
        sImpre  = jComImpTick.getSelectedItem().toString();
        
        /*Si la impresora seleccionada es la vacia entonces*/
        if(sImpre.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una impresora.", "Impresora", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control*/
            jComImpTick.grabFocus();
            
            /*Regresa por que no puede continuar*/
            return;
            
        }
        
        /*Obtiene las impresoras instaladas en el sistema*/
        PrintService[] ser = PrintServiceLookup.lookupPrintServices(null, null);
        
        /*Busca la impresora en las del sistema*/        
        int iInd     = -1;
        for(int x = 0; x < ser.length; x++)
        {
            /*Si la impresora coincide entonces solo sal del bucle para guardar el índice*/
            if(sImpre.compareTo(ser[x].getName())==0)
            {
                iInd= x;                
                break;
            }
                            
        }
        
        /*Si la impresora coincidio entonces*/
        if(iInd != -1)
        {                    
            /*Declara variables final para el thread*/
            final String sImpreFi = sImpre;
            
            /*Thread para aligerar la carga*/
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

                    /*Compila el reporte*/
                    JasperReport    jas;
                    try
                    {
                        jas = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptPrue.jrxml"));               
                    }
                    catch(JRException expnJASR)
                    {    
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                        return;                                                                
                    }            

                    /*Llena el reporte*/
                    JasperPrint pri;
                    try
                    {
                        pri = JasperFillManager.fillReport(jas, null, con);            
                    }
                    catch(JRException expnJASR)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                        return;                                                                
                    }            

                    /*Obtiene la impresora predeterminada actual*/
                    String sImpAnt;
                    PrintService service        = PrintServiceLookup.lookupDefaultPrintService(); 
                    sImpAnt                     = service.getName();
                    
                    /*Cambia la impresora para la prueba*/
                    Star.vCambImp(sImpreFi);
                    
                    /*Manda una impresión*/
                    try
                    {
                        JasperPrintManager.printReport(pri,false);                    
                    }
                    catch(JRException expnJASR)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                        return;                                                               
                    } 

                    /*Cambia la impresora que estaba antes*/
                    Star.vCambImp(sImpAnt);
                    
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensaje de éxito*/
                    JOptionPane.showMessageDialog(null, "Prueba mandada con éxito a la impresora \"" + sImpreFi + "\".", "Impresora", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                }/*Fin de public void run()*/
                
            }).start();
            
        }/*Fin de if(iInd != -1)*/
        /*Else no coincidio con alguna impresora entonces*/
        else
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No existe la impresora en el sistema.", "Impresora", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el combobox de la impresora de facturas*/
            jComImpTick.grabFocus();
        }
        
    }//GEN-LAST:event_jBProbTickActionPerformed

        
    /*Cuando se presiona una tecla en el botón de probar impresora de tickets*/
    private void jBProbTickKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProbTickKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProbTickKeyPressed

    
    /*Cuando se presiona una tecla en el combobox de la impresora de facturas*/
    private void jComImpFactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComImpFactKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComImpFactKeyPressed

    
    /*Cuando se presona una tecla en el botón de probar impresora de facturas*/
    private void jBProbFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProbFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProbFacKeyPressed

    
    /*Cuando se presiona el botón de probar impresoa de facturas*/
    private void jBProbFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProbFacActionPerformed
        
        //Declara variables locales
        String sImpre;
        
        
        
        
        /*Lee la impresora seleccionada*/
        sImpre  = jComImpFact.getSelectedItem().toString();
        
        /*Si la impresora seleccionada es la vacia entonces*/
        if(sImpre.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una impresora.", "Impresora", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control*/
            jComImpFact.grabFocus();
            
            /*Regresa por que no puede continuar*/
            return;
            
        }
        
        /*Obtiene las impresoras instaladas en el sistema*/
        PrintService[] ser = PrintServiceLookup.lookupPrintServices(null, null);
        
        /*Busca la impresora en las del sistema*/        
        int iInd     = -1;
        for(int x = 0; x < ser.length; x++)
        {
            /*Si la impresora coincide entonces solo sal del bucle para guardar el índice*/
            if(sImpre.compareTo(ser[x].getName())==0)
            {
                iInd= x;                
                break;
            }
                            
        }
        
        /*Si la impresora coincidio entonces*/
        if(iInd != -1)
        {
            /*Declara variables final para el thread*/
            final String sImpreFi = sImpre;
            
            /*Thread para aligerar la carga*/
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

                    /*Compila el reporte*/
                    JasperReport    jas;
                    try
                    {
                        jas = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptPrue.jrxml"));               
                    }
                    catch(JRException expnJASR)
                    {    
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                        return;                                                                           
                    }            

                    /*Llena el reporte*/
                    JasperPrint pri;
                    try
                    {
                        pri = JasperFillManager.fillReport(jas, null, con);            
                    }
                    catch(JRException expnJASR)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                        return;                                                               
                    }            

                    /*Obtiene la impresora predeterminada actual*/
                    String sImpAnt;
                    PrintService service        = PrintServiceLookup.lookupDefaultPrintService(); 
                    sImpAnt                     = service.getName();
                    
                    /*Cambia la impresora para la prueba*/
                    Star.vCambImp(sImpreFi);
                    
                    /*Manda una impresión*/
                    try
                    {
                        JasperPrintManager.printReport(pri,false);                    
                    }
                    catch(JRException expnJASR)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                        return;                                                               
                    } 

                    /*Cambia la impresora que se tenía originalmente*/
                    Star.vCambImp(sImpAnt);
                    
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensaje de éxito*/
                    JOptionPane.showMessageDialog(null, "Prueba mandada con éxito a la impresora \"" + sImpreFi + "\".", "Impresora", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                }/*Fin de public void run()*/
                
            }).start();
            
        }/*Fin de if(iInd != -1)*/
        /*Else no coincidio con alguna impresora entonces*/
        else
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No existe la impresora en el sistema.", "Impresora", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el combobox de la impresora de facturas*/
            jComImpFact.grabFocus();
        }                         
        
    }//GEN-LAST:event_jBProbFacActionPerformed

    
    /*Cuando se presiona una tecla en el checkbox de 52 mm*/
    private void jC52mKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jC52mKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jC52mKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de cort*/
    private void jCCortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCortKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jCCortKeyPressed

    
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

    
    /*Cuando el mouse entra en el botón de bùscar*/
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

    
    /*Cuando se pierde el foco del teclado en el control de El usuario*/
    private void jTEstacsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstacsFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTEstacs.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTEstacs.getText().compareTo("")!=0)
            jTEstacs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTEstacsFocusLost

    
    /*Cuando se pierde el foco del teclado en el combo de impresora de tickets*/
    private void jComImpTickFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComImpTickFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jComImpTick.getSelectedItem().toString().compareTo("")!=0)
            jComImpTick.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComImpTickFocusLost

    
    /*Cuando se pierde el foco del teclado en el combo de la impresora de facturas*/
    private void jComImpFactFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComImpFactFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jComImpFact.getSelectedItem().toString().compareTo("")!=0)
            jComImpFact.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComImpFactFocusLost

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBusEstaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusEstaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusEsta.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBusEstaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProbTickMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbTickMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProbTick.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProbTickMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProbFacMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbFacMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProbFac.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProbFacMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
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
    private void jBBusEstaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusEstaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusEsta.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBBusEstaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProbTickMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbTickMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProbTick.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBProbTickMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProbFacMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProbFacMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProbFac.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBProbFacMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
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
    private void jBMosTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMosT.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBMosTMouseExited

    
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
        /*Si se presiona F3 presiona el botón de bùscar 2*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Si se presiona F3 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBMosT.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusEsta;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBProbFac;
    private javax.swing.JButton jBProbTick;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JCheckBox jC52m;
    private javax.swing.JCheckBox jCCort;
    private javax.swing.JComboBox jComImpFact;
    private javax.swing.JComboBox jComImpTick;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTEstacs;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
