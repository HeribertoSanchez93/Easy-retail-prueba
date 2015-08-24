//Paquete
package ptovta;

//Importaciones
import cats.Comps;
import org.apache.poi.ss.usermodel.Row;
import java.awt.Color;
import org.apache.poi.ss.usermodel.Cell;
import java.awt.Cursor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import static ptovta.Princip.bIdle;




/*Clase que controla los kits*/
public class Kits extends javax.swing.JFrame 
{
    /*Declara variables de instancia*/    
    public static int       iContFi;

    /*Almacena el borde del botón de bùscar original*/
    private Border          bBordOri ;
    
    /*Contador para modificar tabla*/
    private int             iContCellEd;

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;
    
    /*Declara variables originales*/
    private String          sKitOri;
    private String          sDescripOri;
    private String          sProdOri;
    private String          sCantOri;
    private String          sAlmaOri;
    private String          sFModOri;
    private String          sPre1Ori;
    private String          sPre2Ori;
    private String          sPre3Ori;
    private String          sPre4Ori;
    private String          sPre5Ori;
    private String          sPre6Ori;
    private String          sPre7Ori;
    private String          sPre8Ori;
    private String          sPre9Ori;
    private String          sPre10Ori;
    private String          sSucOri;    
    private String          sCajOri;
    private String          sEstacOri;
    private String          sNomEstacOri;
    
    
    
    
    /*Constructor sin argumentos*/
    public Kits() 
    {                                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el tamaño de las columnas de la tabla*/
        jTab.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(500);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(120);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(70);
        jTab.getColumnModel().getColumn(5).setPreferredWidth(120);
        jTab.getColumnModel().getColumn(6).setPreferredWidth(140);
        jTab.getColumnModel().getColumn(20).setPreferredWidth(150);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Kits, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Inicia el contador de filas en 1 inicialmente*/
        iContFi      = 1;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el botón abrir kit*/
        jBAbr.grabFocus();
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Agrega todos los datos de la base de datos a la tabla de kits*/
        vCargKit();                    
        
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
                        sKitOri             = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sDescripOri         = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sProdOri            = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sCantOri            = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sAlmaOri            = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sFModOri            = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sPre1Ori            = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();                        
                        sPre2Ori            = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();                        
                        sPre3Ori            = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();                        
                        sPre4Ori            = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();                        
                        sPre5Ori            = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();                        
                        sPre6Ori            = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();                        
                        sPre7Ori            = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();                        
                        sPre8Ori            = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();                        
                        sPre9Ori            = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();                        
                        sPre10Ori           = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();                        
                        sSucOri             = jTab.getValueAt(jTab.getSelectedRow(), 17).toString();
                        sCajOri             = jTab.getValueAt(jTab.getSelectedRow(), 18).toString();
                        sEstacOri           = jTab.getValueAt(jTab.getSelectedRow(), 19).toString();
                        sNomEstacOri        = jTab.getValueAt(jTab.getSelectedRow(), 20).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sKitOri,        jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sDescripOri,    jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sProdOri,       jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sCantOri,       jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sAlmaOri,       jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sFModOri,       jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sPre1Ori,       jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sPre2Ori,       jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sPre3Ori,       jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sPre4Ori,       jTab.getSelectedRow(), 10);                        
                        jTab.setValueAt(sPre5Ori,       jTab.getSelectedRow(), 11);                        
                        jTab.setValueAt(sPre6Ori,       jTab.getSelectedRow(), 12);                        
                        jTab.setValueAt(sPre7Ori,       jTab.getSelectedRow(), 13);                        
                        jTab.setValueAt(sPre8Ori,       jTab.getSelectedRow(), 14);                        
                        jTab.setValueAt(sPre9Ori,       jTab.getSelectedRow(), 15);                        
                        jTab.setValueAt(sPre10Ori,      jTab.getSelectedRow(), 16);                        
                        jTab.setValueAt(sSucOri,        jTab.getSelectedRow(), 17);                        
                        jTab.setValueAt(sCajOri,        jTab.getSelectedRow(), 18);                        
                        jTab.setValueAt(sEstacOri,      jTab.getSelectedRow(), 19);                        
                        jTab.setValueAt(sNomEstacOri,   jTab.getSelectedRow(), 20);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public Kits() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMostT = new javax.swing.JButton();
        jBAbr = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jBImp = new javax.swing.JButton();
        jBExpor = new javax.swing.JButton();

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
        jLabel1.setText("Kits:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 170, -1));

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 150, 120, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Kit", "Descripción", "Producto", "Qty.", "Almacén", "última Modificación", "Precio1", "Precio2", "Precio3", "Precio4", "Precio5", "Precio6", "Precio7", "Precio8", "Precio9", "Precio10", "Sucursal", "Caja", "Usuario", "Nombre Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 850, 520));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 150, 20));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 550, 560, 20));

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
        jP1.add(jBMostT, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 550, 140, 20));

        jBAbr.setBackground(new java.awt.Color(255, 255, 255));
        jBAbr.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBAbr.setForeground(new java.awt.Color(0, 102, 0));
        jBAbr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/abr.png"))); // NOI18N
        jBAbr.setText("Abrir");
        jBAbr.setToolTipText("Abrir Kit(s) (Ctrl+A)");
        jBAbr.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBAbr.setNextFocusableComponent(jBImp);
        jBAbr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAbrMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAbrMouseExited(evt);
            }
        });
        jBAbr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbrActionPerformed(evt);
            }
        });
        jBAbr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAbrKeyPressed(evt);
            }
        });
        jP1.add(jBAbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 60, 120, 30));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Kit(s) (Ctrl+SUPR)");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jBAbr);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 30, 120, 30));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(866, 560, 120, 20));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 12, 130, 18));

        jBImp.setBackground(new java.awt.Color(255, 255, 255));
        jBImp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBImp.setForeground(new java.awt.Color(0, 102, 0));
        jBImp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impexe.png"))); // NOI18N
        jBImp.setText("Importar ");
        jBImp.setToolTipText("Importar desde Excel el Catálogo de Kits (F7)");
        jBImp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBImp.setNextFocusableComponent(jBExpor);
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

        jBExpor.setBackground(new java.awt.Color(255, 255, 255));
        jBExpor.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBExpor.setForeground(new java.awt.Color(0, 102, 0));
        jBExpor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/expexe.png"))); // NOI18N
        jBExpor.setText("Exportar");
        jBExpor.setToolTipText("Exportar a Excel (F8)");
        jBExpor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBExpor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBExporMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBExporMouseExited(evt);
            }
        });
        jBExpor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExporActionPerformed(evt);
            }
        });
        jBExpor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBExporKeyPressed(evt);
            }
        });
        jP1.add(jBExpor, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 120, 120, 30));

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
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                .addContainerGap())
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

    
    /*Cuando se presiona una tecla en la tabla de kits*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jTabKeyPressed

            
    /*Cuando se hace un clic en la tabla de kits*/
    private void jTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabMouseClicked
        
        /*Si se hiso doble clic entonces presiona el botón de abrir*/
        if(evt.getClickCount() == 2) 
            jBAbr.doClick();
        
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
    private void jBMostTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMostTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBMostTKeyPressed

       
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMostTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMostTActionPerformed
        
        /*Borra todos los item en la tabla de kits*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Agrega todos los datos de la base de datos a la tabla de kits*/
        vCargKit();  
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBMostTActionPerformed

    
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
        
        /*Borra todos los item en la tabla de kits*/
        DefaultTableModel dm            = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi                  = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todos los kits*/        
        try
        {                  
            sQ = "SELECT kits.CODKIT, kits.PROD, prods.DESCRIP, kits.CANT, kits.ALMA, kits.FMOD, IFNULL(prelist1, 0 ) prelist1, IFNULL(prelist2, 0 ) prelist2, IFNULL(prelist3, 0 ) prelist3, IFNULL(prelist4, 0 ) prelist4, IFNULL(prelist5, 0 ) prelist5, IFNULL(prelist6, 0 ) prelist6, IFNULL(prelist7, 0 ) prelist7, IFNULL(prelist8, 0 ) prelist8, IFNULL(prelist9, 0 ) prelist9, IFNULL(prelist10, 0 ) prelist10, kits.SUCU, kits.NOCAJ, kits.ESTAC, estacs.NOM FROM kits LEFT OUTER JOIN estacs ON estacs.ESTAC = kits.ESTAC LEFT OUTER JOIN prods ON prods.PROD = kits.CODKIT WHERE kits.CODKIT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR kits.PROD LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR estacs.NOM LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR prods.DESCRIP LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR kits.CANT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR kits.ESTAC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR kits.FMOD LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene todos los precios de lista*/
                String sPre1    = rs.getString("prelist1");
                String sPre2    = rs.getString("prelist2");
                String sPre3    = rs.getString("prelist3");
                String sPre4    = rs.getString("prelist4");
                String sPre5    = rs.getString("prelist5");
                String sPre6    = rs.getString("prelist6");
                String sPre7    = rs.getString("prelist7");
                String sPre8    = rs.getString("prelist8");
                String sPre9    = rs.getString("prelist9");
                String sPre10   = rs.getString("prelist10");

                /*Dale formato de moneda a los precios de lista*/           
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                double dCant    = Double.parseDouble(sPre1);                                
                sPre1           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre2);                                
                sPre2           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre3);                                
                sPre3           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre4);                                
                sPre4           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre5);                                
                sPre5           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre6);                                
                sPre6           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre7);                                
                sPre7           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre8);                                
                sPre8           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre9);                                
                sPre9           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre10);                                
                sPre10          = n.format(dCant);                

                /*Agregalo a la tabla*/
                DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("codkit"), rs.getString("descrip"), rs.getString("prod"), rs.getString("cant"), rs.getString("alma"), sPre1, sPre2, sPre3, sPre4, sPre5, sPre6, sPre7, sPre8, sPre9, sPre10, rs.getString("sucu"), rs.getString("nocaj"), rs.getString("estac"), rs.getString("nom")};
                tm.addRow(nu);

                /*Aumenta en uno el contador de filas*/
                ++iContFi;                      
                
            }/*Fin de while (rs.next())*/
                       
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

    
    /*Cuando se presiona el botón de abrir*/
    private void jBAbrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrActionPerformed
                                       
        /*Si el usuario no a seleccionado un kit no puede avanzar*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un kit de la lista.", "Kit", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en la tabla de kits y regresa*/
            jTab.grabFocus();
            return;
        }

        /*Obtiene las filas seleccionadas*/
        int iSel[]              = jTab.getSelectedRows();
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Recorre toda la selección del usuario*/                
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Obtiene la descripción del kit*/
            String sKitDescrip  = "";
            try
            {
                sQ = "SELECT descrip FROM prods WHERE prod = '" + jTab.getValueAt(iSel[x], 1).toString().trim() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    sKitDescrip         = rs.getString("descrip");                                                
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                       
            }                            

            /*Muestra el formulario de kits*/
            Comps c  = new Comps(jTab.getValueAt(iSel[x], 1).toString().trim(), sKitDescrip);
            c.setVisible(true);
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                    

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jBAbrActionPerformed

    
    /*Cuando se presiona una tecla en el botón de abrir*/
    private void jBAbrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAbrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBAbrKeyPressed

    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed

        /*Si no hay selección en la tabla de kits no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado un kit de la lista para borrar.", "Borrar Kit", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en la lista de kits y regresa*/
            jTab.grabFocus();
            return;
        }

        /*Pregunta si esta seguro de borrar el kit*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar el(los) kit(s)?", "Borrar Kit(s)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
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

        /*Variables locales*/
        String      sCodKit;        
        String      sDescrip;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Obtén el código del kit seleccionado y la descripción*/        
            sCodKit         = jTab.getValueAt(iSel[x], 1).toString();        
            sDescrip        = jTab.getValueAt(iSel[x], 2).toString();        
            
            /*Borra el kit de prods*/
            try
            {                
                sQ = "DELETE FROM prods WHERE prod = '" + sCodKit + "'";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                        
            }

            /*Borra el kit de la tabla de kits*/
            try
            {                
                sQ = "DELETE FROM kits WHERE codkit = '" + sCodKit + "'";                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                       
            }

            /*Inserta en log de kits*/
            try 
            {            
                sQ = "INSERT INTO logkit(  kit,                                     prod,                                   descrip,                                accio,             estac,                                           sucu,                                           nocaj,                                      falt) " + 
                              "VALUES('" + sCodKit.replace("'", "''") + "','" +     sCodKit.replace("'", "''") + "',  '" +  sDescrip.replace("'", "''") + "',       'BORRAR', '" +     Login.sUsrG.replace("'", "''") + "','" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',  now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                        
            }
                        
            //Termina la transacción
            if(Star.iTermTransCon(con)==-1)
                return;
            
            /*Borralo de la tabla*/            
            tm.removeRow(iSel[x]);
            
            /*Resta en uno el contador de filas el contador de filas en uno*/
            --iContFi;
        }                                      

        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Kit(s) borrado(s) con éxito.", "Kits", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBDelKeyPressed

    
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

            
    /*Cuando se presiona el botón de kits*/
    private void jBTab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab1ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab);       

    }//GEN-LAST:event_jBTab1ActionPerformed

    
    /*Cuando se presiona una tecla en la tabla*/
    private void jBTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTab1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTab1KeyPressed

    
    /*Cuando el moue entra en el botón de bùscar*/
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
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBAbrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBAbr.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAbrMouseEntered

    
    
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
    private void jBAbrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBAbr.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBAbrMouseExited

    
    
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

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    private void jBImpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpMouseEntered

        /*Cambia el color del fondo del botón*/
        jBImp.setBackground(Star.colBot);

    }//GEN-LAST:event_jBImpMouseEntered

    private void jBImpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBImp.setBackground(Star.colOri);

    }//GEN-LAST:event_jBImpMouseExited

    
    /*Cuando se presiona el botón de importar desde excel*/
    private void jBImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImpActionPerformed

        /*Configura el file chooser para escoger la ruta donde esta el archivo de excel*/
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        fc.setDialogTitle("Archivo de excel");

        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(fc.showSaveDialog(this)== javax.swing.JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut    = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut    += "\\" + fc.getSelectedFile().getName();   

            /*Si no es un archivo de excel entonces*/
            if(!sRut.endsWith("xlsx") && !sRut.endsWith("xls"))
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No es un archivo de excel. Ingresa por favor un archivo .xlsx o .xls", "Archivo no Válido", JOptionPane.INFORMATION_MESSAGE, null);
                return;
            }

            /*Llama a la forma que hará la importación*/
            LoadinKits lo = new LoadinKits(sRut, jTab, iContFi);
            lo.setVisible(true);            
        }

    }//GEN-LAST:event_jBImpActionPerformed

    
    /*Cuando se presiona una tecla en el botón de importar catálogo de kits desde excel*/
    private void jBImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBImpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBImpKeyPressed

    
    /*Cuando el mouse entra en el botón de importar catálogo de kits*/
    private void jBExporMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBExporMouseEntered

        /*Cambia el color del fondo del botón*/
        jBExpor.setBackground(Star.colBot);

    }//GEN-LAST:event_jBExporMouseEntered

    
    /*Cuando el mouse sale del botón de exportar catálogo a excel*/
    private void jBExporMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBExporMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBExpor.setBackground(Star.colOri);

    }//GEN-LAST:event_jBExporMouseExited

    
    /*Cuando se presiona el botón de exportar catálogo de kits a excel*/
    private void jBExporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExporActionPerformed
        
        /*Configura el file chooser para escoger la ruta donde se guardara el archivo de excel*/
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        fc.setDialogTitle("Exportar Kits");

        /*Declara el contador*/
        int         iConta                   = 1;
        
        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(fc.showSaveDialog(this)== javax.swing.JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut = fc.getCurrentDirectory().getAbsolutePath();

            /*Obtiene el nombre del archivo y concatenalo a la ruta*/
            sRut += "\\" + fc.getSelectedFile().getName(); 

            /*Agregale la extensión de excel*/
            sRut += ".xlsx";

            /*Crea documento en blanco de excel*/
            XSSFWorkbook workbook = new XSSFWorkbook();

            /*Crea una hoja en blanco de excel*/
            XSSFSheet sheet = workbook.createSheet("Kits");
            
            //Abre la base de datos
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            /*Pon los encabezados en el archivo de excel*/
            java.util.Map<String, Object[]> data = new java.util.TreeMap<>();
            data.put(Integer.toString(iConta), new Object[] {"COD KIT(1)", "COD PRODUCTO (2)", "ALMACEN (3)", "CANTIDAD(4)"});
            
            /*Aumenta en uno el contador de filas del libro*/
            ++iConta;

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;            
            String      sQ;

            /*Trae todos los registros de las kits*/
            try
            {
                sQ = "SELECT codkit, prod, alma, cant FROM kits";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                while(rs.next())
                {
                    /*Agrega el registro en la fila de excel*/
                    data.put(Integer.toString(iConta), new Object[] {rs.getString("codkit"), rs.getString("prod"), rs.getString("alma"), Integer.parseInt(rs.getString("cant"))});
                    
                    /*Aumenta en uno el contador de filas del libro*/
                    ++iConta;
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

            /*Coloca el fin de archivo*/
            data.put(Integer.toString(iConta), new Object[] {"FINARCHIVO"});            
            
            /*Itera sobre la información para crear la hoja*/
            java.util.Set<String> keyset = data.keySet();
            int rownum = 0;
            for (String key : keyset)
            {
                Row row = sheet.createRow(rownum++);
                Object [] objArr = data.get(key);
                int cellnum = 0;
                for (Object ob : objArr)
                {
                    Cell cell = row.createCell(cellnum++);
                    if(ob instanceof String)
                        cell.setCellValue((String)ob);
                    else if(ob instanceof Integer)
                        cell.setCellValue((Integer)ob);
                }
            }

            /*Escribe los datos en el archivo del disco*/
            try(java.io.FileOutputStream out = new java.io.FileOutputStream(new File(sRut)))
            {
                workbook.write(out);                
            }
            catch(java.io.IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                   
                return;                                                        
            }

            /*Mensaje de éxito*/
            JOptionPane.showMessageDialog(null, "Archivo exportado en \"" + sRut + "\" con éxito.", "Exportar Kits", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Preguntar al usuario si quiere abrir el archivo*/
            Object[] op     = {"Si","No"};
            int iRes        = JOptionPane.showOptionDialog(this, "¿Quieres abrir el archivo?", "Abrir archivo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
            if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                return;

            /*Abre el archivo*/
            try
            {
                java.awt.Desktop.getDesktop().open(new File(sRut));
            }
            catch(java.io.IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                                                   
            }

        }/*Fin de if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)*/

    }//GEN-LAST:event_jBExporActionPerformed

    
    /*Cuando se presiona una tecla en el botón de exportar catálogo de kits a excel*/
    private void jBExporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBExporKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBExporKeyPressed
            
    
    /*Agrega todos los datos de la base de datos a la tabla de kits*/
    private void vCargKit()
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs ;        
        String      sQ;
      
        /*Trae todos los kits de la base de datos*/
        try
        {
            sQ = "SELECT kits.CODKIT, kits.PROD, prods.DESCRIP, kits.CANT, kits.ALMA, kits.FMOD, IFNULL(prelist1, 0 ) prelist1, IFNULL(prelist2, 0 ) prelist2, IFNULL(prelist3, 0 ) prelist3, IFNULL(prelist4, 0 ) prelist4, IFNULL(prelist5, 0 ) prelist5, IFNULL(prelist6, 0 ) prelist6, IFNULL(prelist7, 0 ) prelist7, IFNULL(prelist8, 0 ) prelist8, IFNULL(prelist9, 0 ) prelist9, IFNULL(prelist10, 0 ) prelist10, kits.SUCU, kits.NOCAJ, kits.ESTAC, estacs.NOM FROM kits LEFT OUTER JOIN estacs ON estacs.ESTAC = kits.ESTAC LEFT OUTER JOIN prods ON prods.PROD = kits.CODKIT";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                 
                /*Obtiene todos los precios de lista*/
                String sPre1    = rs.getString("prelist1");
                String sPre2    = rs.getString("prelist2");
                String sPre3    = rs.getString("prelist3");
                String sPre4    = rs.getString("prelist4");
                String sPre5    = rs.getString("prelist5");
                String sPre6    = rs.getString("prelist6");
                String sPre7    = rs.getString("prelist7");
                String sPre8    = rs.getString("prelist8");
                String sPre9    = rs.getString("prelist9");
                String sPre10   = rs.getString("prelist10");

                /*Dale formato de moneda a los precios de lista*/           
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                double dCant    = Double.parseDouble(sPre1);                                
                sPre1           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre2);                                
                sPre2           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre3);                                
                sPre3           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre4);                                
                sPre4           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre5);                                
                sPre5           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre6);                                
                sPre6           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre7);                                
                sPre7           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre8);                                
                sPre8           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre9);                                
                sPre9           = n.format(dCant);                
                dCant           = Double.parseDouble(sPre10);                                
                sPre10          = n.format(dCant);                

                /*Agregalo a la tabla*/
                DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("codkit"), rs.getString("descrip"), rs.getString("prod"), rs.getString("cant"), rs.getString("alma"), rs.getString("fmod"), sPre1, sPre2, sPre3, sPre4, sPre5, sPre6, sPre7, sPre8, sPre9, sPre10, rs.getString("sucu"), rs.getString("nocaj"), rs.getString("estac"), rs.getString("nom")};
                tm.addRow(nu);
                
                /*Aumenta en uno el contador de filas de las compras*/
                ++iContFi;           
                
            }/*Fin de while(rs.next())*/                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }            
        
        //Cierra la base de datos
        Star.iCierrBas(con);
                
    }/*Fin de private void vCargKit()*/
        
        
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
        /*Si se presiona F3 presiona el botón de búscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMostT.doClick();
        /*Si se presiona CTRL + A entonces presiona el botón de abrir*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_A)
            jBAbr.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbr;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBExpor;
    private javax.swing.JButton jBImp;
    private javax.swing.JButton jBMostT;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Empresas extends javax.swing.JFrame */
