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




/*Clase para controlar los ingresos y egresos de los depósitos en garantía en contabilidad*/
public class DepGara extends javax.swing.JFrame 
{
    /*Variable que contiene el borde actual*/
    private Border          bBordOri;
    
    /*Declara variables gloabales originales*/
    private String          sCodOriG;
    private String          sNomOriG;
    private String          sDepOriG;
    private String          sCtaOriG;
    
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
    public DepGara(String sUs, String sIns, String sCont, String sB, String sNom, String sUsrCon, String sContr) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
    
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
        this.setTitle("Depósitos en garantía interfaz, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

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
        
        /*Pon el foco del teclado en el campo de edición en el campo de la cuenta de banco*/
        jTBanc.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla*/        
        jTab.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(350);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(180);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(180);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Obtiene todos los clientes de la base de datos y cargalos en la tabla*/
        vCargCli();
        
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
                        sCodOriG            = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                  
                        sNomOriG            = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                                                                                                          
                        sDepOriG            = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sCtaOriG            = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Resetea el cell editor*/
                        iContCellEd         = 1;
                        
                        /*Coloca los valores origianales*/
                        jTab.setValueAt(sCodOriG,       jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sNomOriG,       jTab.getSelectedRow(), 2);                       
                        jTab.setValueAt(sCtaOriG,       jTab.getSelectedRow(), 4);
                                                                   
                        /*Obtiene el importe del deopósito*/
                        String sDep         = jTab.getValueAt(jTab.getSelectedRow(), 3).toString().replace("$", "").replace(",", "");
                        
                        /*Si el depósito no es un número entonces*/
                        try
                        {
                            Double.parseDouble(sDep);
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Pon el valor que tenía antes y regresa*/
                            jTab.setValueAt(sDepOriG,       jTab.getSelectedRow(), 3);
                            return;
                        }
                        
                        /*Si el importe es menor o igual a 0 entonces*/
                        if(Double.parseDouble(sDep)<=0)
                        {
                            /*Coloca el importe anterior que estaba y regresa*/
                            jTab.setValueAt(sDepOriG,       jTab.getSelectedRow(), 3);
                            return;
                        }
                        
                        /*Dale formato de moneda al costo*/                        
                        java.text.NumberFormat n  = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US);
                        double dCant    = Double.parseDouble(sDep);                
                        sDep           = n.format(dCant);
                        
                        /*Colcoa nuevamente el importe en su lugar ya con formato de moneda*/
                        jTab.setValueAt(sDep,       jTab.getSelectedRow(), 3);

                    }/*Fin de else*/                                                
                                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public DepGara() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBIng = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jTGaran = new javax.swing.JTextField();
        jBEgre = new javax.swing.JButton();
        jTBanc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMosT = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jTIvaPend = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTIvaXPag = new javax.swing.JTextField();
        jBPag = new javax.swing.JButton();

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
        jBIng.setNextFocusableComponent(jBEgre);
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
        jP1.add(jBIng, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, 120, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("*IVA PEND.PAG:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 170, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jTBanc);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 200, 120, -1));

        jTGaran.setEditable(false);
        jTGaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTGaran.setNextFocusableComponent(jTIvaPend);
        jTGaran.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTGaranFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTGaranFocusLost(evt);
            }
        });
        jTGaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTGaranKeyPressed(evt);
            }
        });
        jP1.add(jTGaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 270, 20));

        jBEgre.setBackground(new java.awt.Color(255, 255, 255));
        jBEgre.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBEgre.setForeground(new java.awt.Color(0, 102, 0));
        jBEgre.setText("Egreso");
        jBEgre.setToolTipText("Hacer póliza de egreso");
        jBEgre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBEgre.setNextFocusableComponent(jBPag);
        jBEgre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBEgreMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBEgreMouseExited(evt);
            }
        });
        jBEgre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEgreActionPerformed(evt);
            }
        });
        jBEgre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBEgreKeyPressed(evt);
            }
        });
        jP1.add(jBEgre, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 140, 120, 30));

        jTBanc.setEditable(false);
        jTBanc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBanc.setName(""); // NOI18N
        jTBanc.setNextFocusableComponent(jTGaran);
        jTBanc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBancFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTBancFocusLost(evt);
            }
        });
        jTBanc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBancKeyPressed(evt);
            }
        });
        jP1.add(jTBanc, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 270, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Clientes:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 160, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("*Cuenta Banco:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 140, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Código", "Nombre", "Deposito en Garantia", "Cuenta Contable"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBBusc);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 700, 240));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 140, 20));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, 420, 20));

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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 360, 140, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 380, 120, 20));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 102, 130, 18));

        jTIvaPend.setEditable(false);
        jTIvaPend.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTIvaPend.setNextFocusableComponent(jTIvaXPag);
        jTIvaPend.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTIvaPendFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTIvaPendFocusLost(evt);
            }
        });
        jTIvaPend.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTIvaPendKeyPressed(evt);
            }
        });
        jP1.add(jTIvaPend, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 50, 270, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("*Cuenta Garantía:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 170, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("*IVA X PAG:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 170, -1));

        jTIvaXPag.setEditable(false);
        jTIvaXPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTIvaXPag.setNextFocusableComponent(jTab);
        jTIvaXPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTIvaXPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTIvaXPagFocusLost(evt);
            }
        });
        jTIvaXPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTIvaXPagKeyPressed(evt);
            }
        });
        jP1.add(jTIvaXPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 270, 20));

        jBPag.setBackground(new java.awt.Color(255, 255, 255));
        jBPag.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBPag.setForeground(new java.awt.Color(0, 102, 0));
        jBPag.setText("Pago");
        jBPag.setToolTipText("Utilizar el depósito como pago");
        jBPag.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBPag.setNextFocusableComponent(jBSal);
        jBPag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBPagMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBPagMouseExited(evt);
            }
        });
        jBPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPagActionPerformed(evt);
            }
        });
        jBPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPagKeyPressed(evt);
            }
        });
        jP1.add(jBPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 170, 120, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todos los clientes de la base de datos y cargalos en la tabla*/
    private void vCargCli()
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
        
        /*Trae los clientes de la base de datos y cargalos*/
        try
        {
            sQ = "SELECT CONCAT_WS('', ser, codemp) AS codemp, nom, deposit, ctaconta FROM emps";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Obtiene el depósito en garantía*/
                String sDepGar   = rs.getString("deposit");
                
                /*Dale formato de moneda al depósito en garantía*/                
                java.text.NumberFormat n    = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US);
                double dCant                = Double.parseDouble(sDepGar);                
                sDepGar                     = n.format(dCant);

                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("codemp"), rs.getString("nom"), sDepGar, rs.getString("ctaconta")};
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
        
    }/*Fin de private void vCargCli()*/
    
    
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
            sQ = "SELECT ctaivapendpag, ctaivaxpag, ctabanc, ctagaran FROM interbd";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca las cuentas en los controles*/
            if(rs.next())
            {                
                jTBanc.setText      (rs.getString("ctabanc"));
                jTGaran.setText     (rs.getString("ctagaran"));
                jTIvaPend.setText   (rs.getString("ctaivapendpag"));
                jTIvaXPag.setText   (rs.getString("ctagaran"));
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
            JOptionPane.showMessageDialog(null, "Selecciona un cliente para hacer la póliza de ingreso.", "Póliza de Ingreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                       
            return;            
        }

        /*Si no a ingreso la cuenta de banco entonces*/
        if(jTBanc.getText().compareTo("")==0)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa la cuenta de banco.", "Póliza de Ingreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
         
            /*Coloca el borde rojo*/                               
            jTBanc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTBanc.grabFocus();                       
            return;            
        }
        
        /*Si no a ingreso la cuenta de garantía entonces*/
        if(jTGaran.getText().compareTo("")==0)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa la cuenta de garantía.", "Póliza de Ingreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
         
            /*Coloca el borde rojo*/                               
            jTGaran.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTGaran.grabFocus();                       
            return;            
        }
        
        /*Si la cantidad de depósito es menor o igual a 0 entonces*/
        if(Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 3).toString().replace("$", "").replace(",", ""))<=0)
        {            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El total del depótiso es incorrecto.", "Póliza de Ingreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
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
        
        /*Obtiene el depósito en garantía de la base de datos para el cliente*/        
        String sDepGar      = "";
        try
        {
            sQ = "SELECT deposit FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces obtiene el resultado*/
            if(rs.next())
                sDepGar     = rs.getString("deposit");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
                    
        /*Manda a llamar join data para la interfaz de los depósitos en garantía*/
        try
        {
            Process pro = new ProcessBuilder("joindata.exe", sInst, sUsr, sContra, sBD, "depgarai", sUsrConta, sNomConta, sContraConta, jTBanc.getText().replace("-", "").replace(" ", "").trim(), jTGaran.getText().replace("-", "").replace(" ", "").trim(), sDepGar).start();                   
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
            return;
        }            
        
        //Cierra la base de datos
        Star.iCierrBas(con);
                               
    }//GEN-LAST:event_jBIngActionPerformed
   
   
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

    
    /*Cuando se presiona una tecla en el botón de agregar*/
    private void jBEgreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBEgreKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBEgreKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la cuenta de la garantía*/
    private void jTGaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTGaranKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTGaranKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la garantía*/
    private void jTGaranFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTGaranFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTGaran.setSelectionStart(0);jTGaran.setSelectionEnd(jTGaran.getText().length());        
        
    }//GEN-LAST:event_jTGaranFocusGained

    
    /*Cuando se presiona una tecla en el campo de la cuenta del banco*/
    private void jTBancKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBancKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTBancKeyPressed

    
    /*Cuando se presiona una  tecla en la tabla de pes*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de la cuenta del banco*/
    private void jTBancFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBancFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTBanc.setSelectionStart(0);jTBanc.setSelectionEnd(jTBanc.getText().length());        
        
    }//GEN-LAST:event_jTBancFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la garantía*/
    private void jTGaranFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTGaranFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTGaran.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTGaran.getText().compareTo("")!=0)
            jTGaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTGaranFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta del banco*/
    private void jTBancFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBancFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTBanc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBanc.getText().compareTo("")!=0)
            jTBanc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTBancFocusLost
           
    
    /*Cuando se presiona el botón de póliza de egreso*/
    private void jBEgreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEgreActionPerformed
                                                       
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente para hacer la póliza de egreso.", "Póliza de Egreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                       
            return;            
        }
        
        /*Si no a ingreso la primera cuenta entonces*/
        if(jTBanc.getText().compareTo("")==0)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa la primera cuenta.", "Póliza de Egreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
         
            /*Coloca el borde rojo*/                               
            jTBanc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTBanc.grabFocus();                       
            return;            
        }
        
        /*Si no a ingreso la segunda cuenta entonces*/
        if(jTGaran.getText().compareTo("")==0)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa la segunda cuenta.", "Póliza de Egreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
         
            /*Coloca el borde rojo*/                               
            jTGaran.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTGaran.grabFocus();                       
            return;            
        }
        
        /*Si la cantidad de depósito es menor o igual a 0 entonces*/
        if(Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 3).toString().replace("$", "").replace(",", ""))<=0)
        {            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El total del depótiso es incorrecto.", "Póliza de Ingreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
            return;            
        }       
        
        /*Preguntar al usuario si esta seguro de querer hacer la(s) póliza(s)*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres crear la(s) póliza(s)?", "Póliza Egreso", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
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
        
        /*Obtiene el depósito en garantía de la base de datos para el cliente*/        
        String sDepGar      = "";
        try
        {
            sQ = "SELECT deposit FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces obtiene el resultado*/
            if(rs.next())
                sDepGar     = rs.getString("deposit");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Manda a llamar join data para la interfaz de los depósitos en garantía*/
        try
        {
            Process pro = new ProcessBuilder("joindata.exe", sInst, sUsr, sContra, sBD, "depgarae", sUsrConta, sNomConta, sContraConta, jTBanc.getText().replace("-", "").replace(" ", "").trim(), jTGaran.getText().replace("-", "").replace(" ", "").trim(), sDepGar).start();                
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
            return;
        }            

        /*Preguntar al usuario si quiere liquidar el saldo del cliente de garantía*/        
        iRes         = JOptionPane.showOptionDialog(this, "¿Quieres liquidar el depósito en garantía del cliente?", "Depósito en Garantía", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       

        /*Obtiene el código del cliente seleccionado*/
        String sCli = jTab.getValueAt(jTab.getSelectedRow(), 1).toString().trim();

        /*Actualiza en 0 el saldo de deposito de cliente*/
        try 
        {                
            sQ = "UPDATE emps SET "
                    + "deposit                          = 0 "                        
                    + "WHERE CONCAT_WS('', ser, codemp) = '" + sCli + "'";                                    
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
                               
    }//GEN-LAST:event_jBEgreActionPerformed

    
   
    
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
        vCargCli();
        
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

        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
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
    private void jBEgreMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEgreMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBEgre.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBEgreMouseEntered

    
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
    private void jBEgreMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEgreMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBEgre.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBEgreMouseExited

    
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
        this.getRootPane().setDefaultButton(jBEgre);
        
        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del iva pendiente por pagar*/
    private void jTIvaPendFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTIvaPendFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTIvaPend.setSelectionStart(0);jTIvaPend.setSelectionEnd(jTIvaPend.getText().length());        
        
    }//GEN-LAST:event_jTIvaPendFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del iva pendiente por pagar*/
    private void jTIvaPendFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTIvaPendFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTIvaPend.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTIvaPend.getText().compareTo("")!=0)
            jTIvaPend.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTIvaPendFocusLost

    
    /*Cuando se presiona una tecla en el campo del iva peniente por pagar*/
    private void jTIvaPendKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTIvaPendKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTIvaPendKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de iva pendiente por pagar*/
    private void jTIvaXPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTIvaXPagFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTIvaXPag.setSelectionStart(0);jTIvaXPag.setSelectionEnd(jTIvaXPag.getText().length());        
        
    }//GEN-LAST:event_jTIvaXPagFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del iva pendiente por pagar*/
    private void jTIvaXPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTIvaXPagFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTIvaXPag.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTIvaXPag.getText().compareTo("")!=0)
            jTIvaXPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTIvaXPagFocusLost

    
    /*Cuando se presiona una tecla en el campo del iva por pagar*/
    private void jTIvaXPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTIvaXPagKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTIvaXPagKeyPressed

    
    /*Cuando el mouse entra en el botón de pago*/
    private void jBPagMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPagMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBPag.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBPagMouseEntered

    
    /*Cuando el mouse sale del botón de pago*/
    private void jBPagMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPagMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBPag.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBPagMouseExited

    
    /*Cuando se presiona una tecla en el botón de pago*/
    private void jBPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPagKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBPagKeyPressed

    
    /*Cuando se presiona el botón de pagar*/
    private void jBPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPagActionPerformed
        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente para hacer la póliza de ingreso.", "Póliza", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                       
            return;            
        }
        
        /*Si el cliente no tiene cuenta entonces*/
        if(jTab.getValueAt(jTab.getSelectedRow(), 4).toString().compareTo("")==0)
        {            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El cliente: " + jTab.getValueAt(jTab.getSelectedRow(), 1) + " no tiene cuenta asignada.", "Póliza de Egreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                 
            return;            
        }
        
        /*Si no a ingreso la cuenta de garantía entonces*/
        if(jTGaran.getText().compareTo("")==0)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No esta definida la cuenta de garantía.", "Póliza", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
         
            /*Coloca el borde rojo*/                               
            jTGaran.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTGaran.grabFocus();                       
            return;            
        }
        
        /*Si la cuenta del iva pendiente de pagar esta vacia entonces*/
        if(jTIvaPend.getText().compareTo("")==0)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No esta definida la cuenta del iva pendiente de pagar.", "Póliza", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
         
            /*Coloca el borde rojo*/                               
            jTIvaPend.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTIvaPend.grabFocus();                       
            return;            
        }
        
        /*Si la cuenta del iva por pagar de pagar esta vacia entonces*/
        if(jTIvaXPag.getText().compareTo("")==0)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No esta definida la cuenta del iva X pagar.", "Póliza", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
         
            /*Coloca el borde rojo*/                               
            jTIvaXPag.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTIvaXPag.grabFocus();                       
            return;            
        }

        /*Si la cantidad de depósito es menor o igual a 0 entonces*/
        if(Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 3).toString().replace("$", "").replace(",", ""))<=0)
        {            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El total del depótiso es incorrecto.", "Póliza de Ingreso", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
            return;            
        }               
        
        /*Preguntar al usuario si esta seguro de querer hacer la(s) póliza(s)*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres crear la(s) póliza(s)?", "Póliza", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Contiene el despósito en garantía y la cuenta contable del cliente*/
        String sDepGar      = "";
        String sCta         = "";

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene algunos datos del cliente*/                
        try
        {
            sQ = "SELECT ctaconta, deposit FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces obtiene los resutlados*/
            if(rs.next())
            {
                sDepGar     = rs.getString("deposit");
                sCta        = rs.getString("ctaconta");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Manda a llamar join data para la interfaz del pago del depósito en garantía*/
        try
        {
            Process pro = new ProcessBuilder("joindata.exe", sInst, sUsr, sContra, sBD, "paggaran", sUsrConta, sNomConta, sContraConta, sCta.replace("-", "").replace(" ", "").trim(), jTGaran.getText().replace("-", "").replace(" ", "").trim(), sDepGar, jTIvaPend.getText().replace("-", "").replace(" ", "").trim(), jTIvaXPag.getText().replace("-", "").replace(" ", "").trim()).start();                
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
            return;
        }            

        /*Preguntar al usuario si quiere liquidar el saldo del cliente de garantía*/        
        iRes         = JOptionPane.showOptionDialog(this, "¿Quieres liquidar el depósito en garantía del cliente?", "Depósito en Garantía", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            if(Star.iCierrBas(con)==-1)
            return;                       
        }

        /*Obtiene el código del cliente seleccionado*/
        String sCli = jTab.getValueAt(jTab.getSelectedRow(), 1).toString().trim();

        //Inserta en CXC en la base de datos      
        if(Star.iInsCXCP(con, "cxc", "", "", sCli, "", "0", "0", sDepGar, "0", sDepGar, "now()", "now()", "ABON FAC", "", "0", "", "","")==-1)
            return;               
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jBPagActionPerformed
    
    
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
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBEgre.doClick();
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
    private javax.swing.JButton jBEgre;
    private javax.swing.JButton jBIng;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBPag;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTod;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBanc;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTGaran;
    private javax.swing.JTextField jTIvaPend;
    private javax.swing.JTextField jTIvaXPag;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
