/*CR01 Modificado para JPS Ingenieros 
974 cuando se cambia a pendiente no dejar modificar si es completado
1043 modificado nombre de boton de confirmado
1643 agregado el abrir proyecto
2079 agregado boton para pediente

*/
//Paquete
package ptovta;

//Importaciones
import cats.DefViats;
import cats.AltPers;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
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




/*Clase que muestra todos los proys para darles algún trato*/
public class Proys extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color colOri;
    
    /*Variable que contiene el borde actual*/
    private Border              bBordOri;
    
    /*Declara variables de instancia*/    
    public static int           iContFi;
    
    /*Contador para modificar tabla*/
    private int                 iContCellEd;
    
    /*Declara variables origianles*/
    private String              sProyOri;
    private String              sNomEmpOri;
    private String              sImpoOri;
    private String              sFCreaOri;
    private String              sUltModOri;
    private String              sEstadOri;
    private String              sSucOri;
    private String              sCajOri;
    private String              sEstacOri;
    private String              sNomEstacOri;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean             bSel;
    
    
    
    
    /*Constructor sin argumentos*/
    public Proys() 
    {        
         
        /*Inicaliza los componentes gráficos*/
        initComponents();

        /*Para que la tabla tengatama scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
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
        this.setTitle("Proyectos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
                
        /*Inicializa el contador de filas*/
        iContFi  = 1;
                
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nom de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla de proys*/
        jTab.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de proyectos*/
        jTab.getColumnModel().getColumn(0).setPreferredWidth(50);        
        jTab.getColumnModel().getColumn(1).setPreferredWidth(150);        
        jTab.getColumnModel().getColumn(2).setPreferredWidth(600);        
        jTab.getColumnModel().getColumn(4).setPreferredWidth(160);        
        jTab.getColumnModel().getColumn(5).setPreferredWidth(160);        
        jTab.getColumnModel().getColumn(10).setPreferredWidth(160);        
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
         /*Agrega todos los datos de la base de datos a la tabla de proys*/
         vCargProy();                
        
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
                        sProyOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sNomEmpOri      = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sImpoOri        = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sFCreaOri       = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sUltModOri      = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sEstadOri       = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sSucOri         = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sCajOri         = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sEstacOri       = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sNomEstacOri    = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sProyOri,           jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sNomEmpOri,         jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sImpoOri,           jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sFCreaOri,          jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sUltModOri,         jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sEstadOri,          jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sSucOri,            jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sCajOri,            jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sEstacOri,          jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sNomEstacOri,       jTab.getSelectedRow(), 10);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public Proys() */

        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBNew = new javax.swing.JButton();
        jBAbr = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBCop = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jBProPend = new javax.swing.JButton();
        jBConfirProy = new javax.swing.JButton();
        jBMosT = new javax.swing.JButton();
        jBPers = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jBAutoProy = new javax.swing.JButton();

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

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre8.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nuevo Proyecto (Ctrl+N)");
        jBNew.setNextFocusableComponent(jBCop);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 530, 110, 30));

        jBAbr.setBackground(new java.awt.Color(255, 255, 255));
        jBAbr.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBAbr.setForeground(new java.awt.Color(0, 102, 0));
        jBAbr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/abr.png"))); // NOI18N
        jBAbr.setToolTipText("Abrir Proyecto(s) (Ctrl+A)");
        jBAbr.setNextFocusableComponent(jBNew);
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
        jP1.add(jBAbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 530, 100, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Proyectos:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Proyecto", "Nombre Cliente", "Importe", "Fecha de Creación", "Ultima Modificación", "Estado", "Sucursal", "Caja", "Usuario", "Nombre Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 1150, 500));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 130, 19));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Proyecto(s) (Ctrl+SUPR)");
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 530, 110, 30));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 530, 250, 20));

        jBCop.setBackground(new java.awt.Color(255, 255, 255));
        jBCop.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCop.setForeground(new java.awt.Color(0, 102, 0));
        jBCop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/copiar.jpg"))); // NOI18N
        jBCop.setText("F5");
        jBCop.setToolTipText("Copiar");
        jBCop.setNextFocusableComponent(jBPers);
        jBCop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCopMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCopMouseExited(evt);
            }
        });
        jBCop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCopActionPerformed(evt);
            }
        });
        jBCop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCopKeyPressed(evt);
            }
        });
        jP1.add(jBCop, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 530, 100, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 530, 90, 30));

        jBProPend.setBackground(new java.awt.Color(255, 255, 255));
        jBProPend.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBProPend.setText("Proyecto Pendiente");
        jBProPend.setToolTipText("Cambiar el Estado del Proyecto a Pendiente");
        jBProPend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProPendMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProPendMouseExited(evt);
            }
        });
        jBProPend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProPendActionPerformed(evt);
            }
        });
        jBProPend.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProPendKeyPressed(evt);
            }
        });
        jP1.add(jBProPend, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, 20));

        jBConfirProy.setBackground(new java.awt.Color(255, 255, 255));
        jBConfirProy.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBConfirProy.setText("Proyecto Confirmado");
        jBConfirProy.setToolTipText("Cambiar el Estado del Proyecto a Autorizado");
        jBConfirProy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBConfirProyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBConfirProyMouseExited(evt);
            }
        });
        jBConfirProy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBConfirProyActionPerformed(evt);
            }
        });
        jBConfirProy.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBConfirProyKeyPressed(evt);
            }
        });
        jP1.add(jBConfirProy, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, 20));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 530, 130, 19));

        jBPers.setBackground(new java.awt.Color(255, 255, 255));
        jBPers.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBPers.setForeground(new java.awt.Color(0, 102, 0));
        jBPers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/altpers.png"))); // NOI18N
        jBPers.setText("F8");
        jBPers.setNextFocusableComponent(jBSal);
        jBPers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBPersMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBPersMouseExited(evt);
            }
        });
        jBPers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPersActionPerformed(evt);
            }
        });
        jBPers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPersKeyPressed(evt);
            }
        });
        jP1.add(jBPers, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 530, 90, 30));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 560, 120, 20));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 12, 130, 18));

        jBAutoProy.setBackground(new java.awt.Color(255, 255, 255));
        jBAutoProy.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBAutoProy.setText("Proyecto Autorizado");
        jBAutoProy.setToolTipText("Cambiar el Estado del Proyecto a Autorizado");
        jBAutoProy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAutoProyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAutoProyMouseExited(evt);
            }
        });
        jBAutoProy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAutoProyActionPerformed(evt);
            }
        });
        jBAutoProy.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAutoProyKeyPressed(evt);
            }
        });
        jP1.add(jBAutoProy, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, 20));

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
                .addGap(10, 10, 10)
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Agrega todos los datos de la base de datos a la tabla de proys*/
    private void vCargProy()
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables locales
        String      sTot;
        double      dCant;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene los proys de la base de datos*/
        try
        {                  
            sQ = "SELECT estacs.NOM, proys.SUCU, proys.NOCAJ, proys.PROY, emps.NOM, proys.TOT, proys.FALT, proys.FMOD, proys.ESTAC, proys.ESTAD FROM proys LEFT OUTER JOIN estacs ON estacs.ESTAC = proys.ESTAC LEFT OUTER JOIN emps ON proys.CODEMP = CONCAT_WS('', emps.SER, emps.CODEMP ) WHERE proys.PROYVAC = 0";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                                                
                /*Obtiene el total*/
                sTot            = rs.getString("proys.TOT"); 
                
                /*Formatea el total a moneda*/
                dCant           = Double.parseDouble(sTot);                        
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sTot            = n.format(dCant);
                
                /*Agregalos a la tabla*/                
                DefaultTableModel tm = (DefaultTableModel)jTab.getModel();
                Object nu[]          = {iContFi, rs.getString("proys.PROY"), rs.getString("emps.NOM"), sTot, rs.getString("proys.FALT"), rs.getString("proys.FMOD"), rs.getString("proys.ESTAD"), rs.getString("proys.SUCU"), rs.getString("proys.NOCAJ"), rs.getString("proys.ESTAC"), rs.getString("estacs.NOM")};
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
        catch(NumberFormatException expnNumForm)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnNumForm.getMessage(), Star.sErrNumForm, expnNumForm.getStackTrace(), con);                                
            return;                                                                                                     
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vCargProy()*/
        
        
    /*Al presionar el botón de nu*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
        
        /*Obtiene el índice de la fila seleccionada*/
        int row = jTab.getSelectedRow();
        
        /*Mostrar el formulario de nu proyecto*/
        NewProy n = new NewProy(jTab, row);
        n.setVisible(true);
        
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el botón de nu*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed

   
    /*Cuando se presiona una tecla en la tabla*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscKeyPressed

    
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
        
        /*Borra todos los item en la tabla de proys*/
        DefaultTableModel dm        = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi              = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todos los proys*/        
        try
        {                  
            sQ = "SELECT proys.PROY, proys.TOT, proys.FALT, proys.FMOD, proys.ESTAC, proys.ESTAD, emps.NOM FROM proys LEFT JOIN emps ON emps.CODEMP = proys.CODEMP WHERE proys.PROY LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR emps.NOM LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR proys.TOT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR proys.FALT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR proys.FMOD LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR proys.ESTAC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR proys.ESTAD LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene el tot*/
                String sTot     = rs.getString      ("proys.TOT");   
                                
                /*Dale formato de mon al costo*/                
                double dCant    = Double.parseDouble(sTot);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sTot            = n.format(dCant);
            
                DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("proy"), rs.getString("emps.NOM"), sTot, rs.getString("proys.FALT"), rs.getString("proys.FMOD"), rs.getString("proys.ESTAC"), rs.getString("proys.ESTAD")};
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
        catch(NumberFormatException expnNumForm)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnNumForm.getMessage(), Star.sErrNumForm, expnNumForm.getStackTrace(), con);                                
            return;                                                                                                     
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);            
                    
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se presiona una tecla en el campo de edición de busqueda*/
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

    
    /*Cuando se gana el foco del teclado en el campode edición de buscar*/
    private void jTBuscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTBusc.setSelectionStart(0);jTBusc.setSelectionEnd(jTBusc.getText().length());        
        
    }//GEN-LAST:event_jTBuscFocusGained
                    
    
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se presiona una tecla en el botón de copiar*/
    private void jBCopKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCopKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCopKeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Sal del formulario*/
        this.dispose();        
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Función para actualizar el estad del proyecto*/
    private void vActEsta(String sEsta, String sExt)
    {
        /*Si no a seleccionado algún proyecto de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un proyecto de la tabla", "Seleccionar Proyecto(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla de proys y regresa*/
            jTab.grabFocus();            
            return;            
        }

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
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*CR no hacer nada si es completado*/
            String tes=jTab.getValueAt(iSel[x], 6).toString();
            if(tes.compareTo("CO")==0)
            { 
                JOptionPane.showMessageDialog(null, "El estado del Proyecto "+jTab.getValueAt(iSel[x], 1).toString()+" es completado, no puede ser cambiado a pendiente.", "Estado Proyecto(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            /**/
            /*Actualiza el estad del proyecto a pendiente*/
            try 
            {            
                sQ = "UPDATE proys SET "
                        + "estad        = '" + sEsta.replace("'", "''") + "', "
                        + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE proy   = '" + jTab.getValueAt(iSel[x], 1).toString().replace("'", "''") + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
             }
         
            /*Actualiza el estad del proyecto en la tabla*/
            jTab.setValueAt(sEsta, iSel[x], 6);
        
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                                        
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensaje de cambio de estad con éxito*/
        JOptionPane.showMessageDialog(null, "Estado de proyecto(s) cambiado a " + sExt + " con éxito.", "Estado Proyecto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }/*Fin de private void vActEsta(String sEsta, String sExt)*/
        
        
    /*Cuando se presiona el botón de proyecto pendiente*/
    private void jBProPendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProPendActionPerformed

        /*Función para actualizar el estad del proyecto*/
        vActEsta("PE", "Pendiente");                
        
    }//GEN-LAST:event_jBProPendActionPerformed

    
    /*Cuando se presiona una tecla en el botón de proyecto pendiente*/
    private void jBProPendKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProPendKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProPendKeyPressed

    
    /*Cuando se presiona una tecla en el botón de confirmar proyecto*/
    private void jBConfirProyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBConfirProyKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBConfirProyKeyPressed

    
    /*Cuando se presiona el botón de confirmar proyecto*/
    private void jBConfirProyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBConfirProyActionPerformed
        
        /*Si no a seleccionado algún proyecto de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un proyecto de la tabla", "Seleccionar Proyecto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla de proys y regresa*/
            jTab.grabFocus();            
            return;            
        }
        
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
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Actualiza el estad del proyecto a confirmado*/
            try 
            {            
                sQ = "UPDATE proys SET "
                        + "estad        = 'CO', "
                        + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE proy   = '" + jTab.getValueAt(iSel[x], 1).toString().replace("'", "''") + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
             }

            /*Actualiza el estad del proyecto en la tabla*/
            jTab.setValueAt("CO", iSel[x], 6);
        
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                   
                        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensaje de cambio de estad con éxito*/
        JOptionPane.showMessageDialog(null, "Estado de Proyecto(s) cambiado(s) a confirmado con éxito.", "Estado Proyecto(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBConfirProyActionPerformed

    
    /*Cuando se presiona el botón de borrar proyecto*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si no a seleccionado por lo menos un proyecto en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)        
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un proyecto para borrar.", "Borrar Proyecto(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                        
            return;            
        }
                
        /*Preguntar al usuario si esta seguro de querer borrar el proyecto*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar el(los) proyecto(s)?", "Borrar Proyecto", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(false, true);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Recorre toda la selección del usuario*/
        int iSel[] = jTab.getSelectedRows();
        DefaultTableModel tm  = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            String test=jTab.getValueAt(iSel[x], 6).toString();
            if(test.compareTo("CO")==0)
            {
                JOptionPane.showMessageDialog(null, "El estado del Proyecto "+jTab.getValueAt(iSel[x], 1).toString()+" es completado, no puede ser borrado.", "Estado Proyecto(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            /*Recorre todas las cots que tiene el proyecto*/
            try
            {                  
                sQ = "SELECT * FROM cots WHERE proy = '" + jTab.getModel().getValueAt(iSel[x], 1).toString() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces elimina las partidas de esa cotización*/
                while(rs.next())
                    vDelPartCot(rs.getString("codcot"), con);                    
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
            }

            /*Borra todas las cots del proyecto*/
            try 
            {                
                sQ  = "DELETE FROM cots WHERE proy = '" + jTab.getModel().getValueAt(iSel[x], 1).toString() + "'";                    
                st  = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
             }

            /*Borra todos los viáticos del proyecto*/
            try 
            {               
                sQ  = "DELETE FROM viatspro WHERE codpro = '" + jTab.getModel().getValueAt(iSel[x], 1).toString().replace("'", "''") + "'";                    
                st  = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
             }

            /*Borra el proyecto de la base de datos*/
            try 
            {                
                sQ = "DELETE FROM proys WHERE proy = '" + jTab.getModel().getValueAt(iSel[x], 1).toString() + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
             }

            /*Recorre todas las órdenes de compra que tiene el proyecto*/
            try
            {                  
                sQ = "SELECT codord FROM ords WHERE proy = '" + jTab.getModel().getValueAt(iSel[x], 1).toString() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces borra las partidas de la órden*/
                while(rs.next())
                    vDelPartOrd(rs.getString("codord"), con);                               
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
            }

            /*Borra todas las órdenes del proyecto*/
            try 
            {                
                sQ  = "DELETE FROM ords WHERE proy = '" + jTab.getModel().getValueAt(iSel[x], 1).toString() + "'";             
                st  = con.createStatement();
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

        }/*Fin de for(int x = 0; x < iSel.length; x++)*/                                                                
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Proyecto(s) borrado(s) con éxito.", "Proyectos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBDelActionPerformed


    /*Elimina todas las partidas de esa órden*/
    public static void vDelPartOrd(String sOrd, Connection con)
    {        
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 

        
        
        
        
        /*Borra todas las partidas de la órden específica por el código*/
        try 
        {            
            sQ = "DELETE FROM partords WHERE codord = '" + sOrd + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error
            Star.iErrProc(Proys.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                        
         }                
        
    }/*Fin de public static void vDelPartOrd(String sCodCot)*/
    
    
    /*Elimina todas las partidas de esa cotización*/
    public static void vDelPartCot(String sCodCot, Connection con)
    {        
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 

        
        
        
        
        /*Borra todas las partidas de la cotización específica por el código*/
        try 
        {            
            sQ = "DELETE FROM partcot WHERE codcot = '" + sCodCot + "'";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error
            Star.iErrProc(Proys.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
         }
                
    }/*Fin de public static void vDelPartCot(String sCodCot)*/
                        
                
    /*Cuando se presiona el botón de copiar*/
    private void jBCopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCopActionPerformed
        
        /*Si no se a seleccionado nada en la tabla de proys entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Slecciona por lo menos un proyecto de la tabla para copiar.", "Copiar Proyecto(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();            
            return;            
        }
        
        /*Preguntar al usuario si esta seguro de querer realizar la copia del o los proys*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres realizar la copia del(los) proyecto(s)?", "Copiar Proyecto(s)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       

        //Abre la base de datos
        Connection  con = Star.conAbrBas(false, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables locales        
        String      sCodProyNew;
        String      sCodEmp                     = "";
        String      sObr                        = "";
        String      sTipObr                     = "";
        String      sDescrip                    = "";
        String      sPlant                      = "";
        String      sTot                        = "";
        String      sTiemEnt                    = "";
        String      sSubTot                     = "";
        String      sUbicGraf                   = "";
        String      sIVA                        = "";
        String      sOtr                        = "";
        String      sUbic                       = "";
        String      sEsta                       = "";
        String      sIniObr                     = "";
        String      sTermObr                    = "";
        String      sCondPag                    = "";
        String      sNomPers                    = "";                
        String      sNomb                       = "";        
        String      sSer                        = "";
        String      sFCrea                      = "";        
        String      sNombEmp                    = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Lee de la base de datos todos los datos del proyecto para crear un proyecto igual pero con otro código*/
            try
            {                  
                sQ = "SELECT proys.SER, proys.TIPOBR, proys.CODEMP, proys.OBR, proys.DESCRIP, proys.PLANT, proys.TOT, proys.SUBTOT, proys.IVA, proys.UBI, proys.OTR, estacs.NOM, proys.UBIGRAF, proys.ESTAD, proys.INIOBR, proys.TERMOBR, proys.TIEMENT, proys.CONDPAG, proys.NOMPERS, proys.NOCAJ, proys.SUCU FROM proys LEFT OUTER JOIN estacs ON estacs.ESTAC = proys.ESTAC WHERE proy = '" + jTab.getModel().getValueAt(iSel[x], 1).toString() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    /*Obtiene todos los datos de la consulta*/
                    sCodEmp                 = rs.getString("proys.CODEMP");                                   
                    sObr                    = rs.getString("proys.OBR");                                   
                    sTipObr                 = rs.getString("proys.TIPOBR");                                   
                    sDescrip                = rs.getString("proys.DESCRIP");                                   
                    sPlant                  = rs.getString("proys.PLANT");                                   
                    sTot                    = rs.getString("proys.TOT");                                   
                    sSubTot                 = rs.getString("proys.SUBTOT");                                   
                    sIVA                    = rs.getString("proys.IVA");                                   
                    sUbicGraf               = rs.getString("proys.UBIGRAF");                                   
                    sOtr                    = rs.getString("proys.OTR");                                   
                    sUbic                   = rs.getString("proys.UBI");                                   
                    sEsta                   = rs.getString("proys.ESTAD");                                   
                    sIniObr                 = rs.getString("proys.INIOBR");                                   
                    sTermObr                = rs.getString("proys.TERMOBR");                                   
                    sTiemEnt                = rs.getString("proys.TIEMENT");    
                    sCondPag                = rs.getString("proys.CONDPAG");                                   
                    sNomPers                = rs.getString("proys.NOMPERS");                                                                                                                       
                    sNomb                   = rs.getString("estacs.NOM");                                                                                                   
                    sSer                    = rs.getString("proys.SER");                                                                                                   
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
            }

            /*Obtiene el nom de la empresa en base al código del cliente*/
            try
            {                  
                sQ = "SELECT nom FROM emps WHERE CONCAT_WS('', ser, codemp ) = '" + sCodEmp + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene la consulta*/
                if(rs.next())
                    sNombEmp             = rs.getString("nom");                                                                                   
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
            }

            /*Obtiene el código nuevo para el proyecto de copia*/
            sCodProyNew                   = NewProy.sGenCodPro();
            
            //Inicia la transacción
            if(Star.iIniTransCon(con)==-1)
                return;            
            
            /*Inserta en la base de datos el nuevo proyecto copia*/
            try 
            {           
                sQ = "INSERT INTO proys(proy,                                       codemp,                                 obra,                               tipobr,                                     descrip,                                plant,                                  tot,            subtot,             iva,           otr,                                     ubi,                                    estad,                                  iniobr,                                     termobr,                                    tiement,                                    condpag,                                    nompers,                                estac,                                      falt,           ubigraf,                                sucu,                                           nocaj,                                          ser) " + 
                           "VALUES('" + sCodProyNew.replace("'", "''") + "','" +    sCodEmp.replace("'", "''") + "','" +    sObr.replace("'", "''") + "','" +   sTipObr.replace("'", "''") + "','" +        sDescrip.replace("'", "''") + "','" +   sPlant.replace("'", "''") + "'," +      sTot + "," +    sSubTot + "," +     sIVA + ", '" + sOtr.replace("'", "''") + "','" +        sUbic.replace("'", "''") + "','" +      sEsta.replace("'", "''") + "','" +      sIniObr.replace("'", "''") + "','" +        sTermObr.replace("'", "''") + "','" +       sTiemEnt.replace("'", "''") + "','" +       sCondPag.replace("'", "''") + "','" +       sNomPers.replace("'", "''") + "','" +   Login.sUsrG.replace("'", "''") + "',    now(), '" +     sUbicGraf.replace("'", "''") + "','" +  Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "', '" + sSer.replace("'", "''") + "')";
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
            }

            /*Recorre todas las cots que tiene este proyecto*/
            try
            {                  
                sQ = "SELECT * FROM cots WHERE proy = '" + jTab.getModel().getValueAt(iSel[x], 1).toString() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                while(rs.next())
                {            
                    //Declara variables de la base de datos                    
                    ResultSet   rs2;                    
                    String      sQ2; 

                    /*Obtiene el consecutivo de la cotización en base a la serie de la cotización original*/
                    String sConsec  = "";                            
                    try
                    {
                        sQ2= "SELECT consec, serie FROM consecs WHERE tip = 'COT' AND serie = '" + rs.getString("no_ser") + "'";
                        st = con.createStatement();
                        rs2= st.executeQuery(sQ2);
                        /*Si hay datos obtiene el resultado*/
                        if(rs2.next())
                            sConsec         = rs2.getString("consec");                                                                               
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                        return;                                                                                                                             
                    }

                    /*Actualiza el consecutivo de la cotización en la base de datos*/
                    try
                    {                       
                        sQ2= "UPDATE consecs SET "
                                + "consec = consec + 1 "
                                + "WHERE tip ='COT' AND serie = '" + rs.getString("no_ser").replace("'", "''") + "'";                                                
                        st = con.createStatement();
                        st.executeUpdate(sQ2);            
                     }
                     catch(SQLException expnSQL)
                     {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                        return;                                                                                                                             
                    }
                    
                    /*Inserta en la base de datos la cotización para el nuevo proyecto*/
                    vInsCotNewPro(rs.getString("no_ser") + sConsec, sCodProyNew, rs.getString("codemp"), rs.getString("observ"), rs.getString("estac"), rs.getString("subtotgral"), rs.getString("manobr"), rs.getString("subtotmat"), rs.getString("descrip"), rs.getString("no_ser"), rs.getString("ser"), rs.getString("tipcam"), rs.getString("subtotgral2"), rs.getString("subtot"), rs.getString("impue"), rs.getString("subtotmat"), rs.getString("tot"), rs.getString("estad"), con);

                    /*Inserta en la base de datos las partidas de la cotización para el nuevo proyecto*/
                    vInsParts(rs.getString("no_ser") + sConsec, rs.getString("codcot"), con);                                                
                }
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

            /*Obtiene la fecha de creación de este proyecto copia nuevo insertado*/
            try
            {                  
                sQ = "SELECT falt FROM proys WHERE proy = '" + sCodProyNew + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene la consulta*/
                if(rs.next())
                    sFCrea             = rs.getString("falt");                                                                                                          
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
            }
            
            /*Dale formato al tot*/            
            double dCant            = Double.parseDouble(sTot);                
            NumberFormat n          = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            sTot                    = n.format(dCant);

            /*Agrega a la tabla de proys el nuevo proyecto de copia*/
            DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
            Object nu[]             = {Proys.iContFi, sCodProyNew, sNombEmp, sTot, sFCrea, sFCrea, sEsta, Star.sSucu, Star.sNoCaj, Login.sUsrG, sNomb};        
            tm.addRow(nu);

            /*Aumenta en uno el contador de filas*/
            ++Proys.iContFi;
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                                                                                                
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Proyecto(s) copia agregado(s) con éxito.", "Éxito al copiar Prroyecto(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBCopActionPerformed

    
    /*Inserta en la base de datos las partidas de la cotización de jps1 y jps2 para el nu proyecto*/
    private void vInsParts(String sCodCotNew, String sCodCot, Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;
        String      sQ; 




        /*Obtiene todas las partidas de la cotización en base a su código*/
	try
        {                  
            sQ = "SELECT * FROM partcot WHERE codcot = '" + sCodCot + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta las partidas*/
            while(rs.next())
                vInsPartCot(sCodCotNew, rs.getString("prod"), rs.getString("unid"), rs.getString("cant"), rs.getString("descrip"), rs.getString("pre"), rs.getString("impo"), rs.getString("mon"), rs.getString("pre2"), rs.getString("desc1"), rs.getString("desc2"), rs.getString("desc3"), rs.getString("desc4"), rs.getString("desc5"), rs.getString("impo2"), rs.getString("impueval"), rs.getString("impueimpo"), rs.getString("impueimpo2"), rs.getString("alma"), con);                                                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
        }        
                
    }/*Fin de private void vInsParts(String sCodCot)*/
                
             
    /*Inserta en partcotizacionesjps1*/
    private void vInsPartCot(String sCodCotNew, String sArt, String sUnid, String sCant, String sDescrip, String sPre, String sImp, String sMon, String sPre2, String sDesc1, String sDesc2, String sDesc3, String sDesc4, String sDesc5, String sImpo2, String sImpueVal, String sImpueImpo, String sImpueImpo2, String sAlma, Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 

        

        /*Inserta las partidas en un registro*/
        try 
        {            
            sQ = "INSERT INTO partcot (    codcot,                                     prod,                                       unid,                                   cant,               descrip,                                    pre,                impo,        falt,          mon,                                pre2,            desc1,          desc2,          desc3,              desc4,          desc5,          impo2,          impueval,          impueimpo,          impueimpo2,           alma) " + 
                                "VALUES('" + sCodCotNew.replace("'", "''") + "','" +    sArt.replace("'", "''") + "','" +           sUnid.replace("'", "''") + "', " +      sCant + ",'" +      sDescrip.replace("'", "''") + "', " +       sPre + ", " +       sImp + ",    now(), '" +    sMon.replace("'", "''") + "', " +   sPre2 + ", " +   sDesc1 + ", " + sDesc2 + ", " + sDesc3 + ", " +     sDesc4 + ", " + sDesc5 + ", " + sImpo2 + ", " + sImpueVal + ", " + sImpueImpo + ", " + sImpueImpo2 + ", '" + sAlma.replace("'", "''") + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
         }
                
    }/*Fin de private void vInsertaPartCotizacionesJPS2(String sCodCotNew, String sArt, String sUnid, String sCant, String sDescrip, String sPre, String sImp)*/   
    
                
    /*Inserta en la base de datos la cotización para el nuevo proyecto*/
    private void vInsCotNewPro(String sCodCotNew, String sCodProyN, String sCodEm, String sObserv, String sEsta, String sSubTotGral, String sManoDeO, String sSubTotMat, String sDescrip, String sNoSer, String sSer, String sTipCam, String sSubTotGral2, String sSubTot, String sImpue, String sSubTotMat2, String sTot, String sEstad, Connection con)
    {
        //Inserta en la tabla de cots los datos
        Star.iInsCots(con, sCodCotNew.replace("'", "''"), sCodProyN.replace("'", "''"), sNoSer.replace("'", "''"), sCodEm.replace("'", "''"), sSer.replace("'", "''"), sObserv.replace("'", "''"), sDescrip.replace("'", "''"), sSubTotGral, sSubTotMat, sManoDeO, sSubTot, sImpue, sTot, sEstad.replace("'", "''"), sSubTotGral2, sSubTotMat2, "now()", "0", "0", "", "0", "now()", "now()");                        	        
    }
    
    
    /*Cuando se presiona una tecla en el botón de mostrar todo*/
    private void jBMosTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMosTKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMosTKeyPressed

    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed
        
        /*Borra todos los item en la tabla de proys*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Agrega todos los datos de la base de datos a la tabla de proys*/
        vCargProy();                                
        
    }//GEN-LAST:event_jBMosTActionPerformed
               
    
    /*Cuando se hace clic en la tabla de proys*/
    private void jTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabMouseClicked
        
        /*Si se hiso doble clic entonces presiona el botón de abrir*/
        if(evt.getClickCount() == 2) 
            jBAbr.doClick();
        
    }//GEN-LAST:event_jTabMouseClicked

    
    /*Cuando se presiona una tecla en el botón de abrir*/
    private void jBAbrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAbrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBAbrKeyPressed

    
    /*Cuando se presiona el botón de abrir*/
    private void jBAbrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrActionPerformed
                               
        /*Si no se a seleccionado nada en la tabla de proys entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un proyecto de la tabla para abrir.", "Abrir Proyecto(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en la lista y regresa*/
            jTab.grabFocus();           
            return;
        }

        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            //CR01 agregado abrir
            NewProy n = new NewProy(jTab, x);
            n.setVisible(true);
        }                    

    }//GEN-LAST:event_jBAbrActionPerformed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    
    
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

    
    /*Cuando se presiona el botón de personas*/
    private void jBPersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPersActionPerformed
        
        /*Muestra el gráfico de alta de personas*/
        AltPers a = new AltPers();
        a.setVisible(true);
        
    }//GEN-LAST:event_jBPersActionPerformed

    
    /*Cuando se presiona una tecla en el botón de personas*/
    private void jBPersKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPersKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBPersKeyPressed

    
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

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProPendMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProPendMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProPend.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProPendMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBConfirProyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConfirProyMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBConfirProy.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBConfirProyMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMosTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMosT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMosTMouseEntered

    
    
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
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCopMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCopMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCop.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCopMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBPersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPersMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBPers.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBPersMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProPendMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProPendMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProPend.setBackground(colOri);
        
    }//GEN-LAST:event_jBProPendMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBConfirProyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConfirProyMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBConfirProy.setBackground(colOri);
        
    }//GEN-LAST:event_jBConfirProyMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMosTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMosT.setBackground(colOri);
        
    }//GEN-LAST:event_jBMosTMouseExited

    
    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBAbrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBAbr.setBackground(colOri);
        
    }//GEN-LAST:event_jBAbrMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCopMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCopMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCop.setBackground(colOri);
        
    }//GEN-LAST:event_jBCopMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBPersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPersMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBPers.setBackground(colOri);
        
    }//GEN-LAST:event_jBPersMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    private void jBAutoProyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAutoProyMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jBAutoProyMouseEntered

    private void jBAutoProyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAutoProyMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jBAutoProyMouseExited
/*CR boton de autorizado*/
    private void jBAutoProyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAutoProyActionPerformed
       
        /*Si no a seleccionado algún proyecto de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un proyecto de la tabla", "Seleccionar Proyecto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla de proys y regresa*/
            jTab.grabFocus();            
            return;            
        }
        
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
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            String test=jTab.getValueAt(iSel[x], 6).toString();
            if(test.compareTo("CO")==0)
            {
                JOptionPane.showMessageDialog(null, "El estado de Proyecto "+jTab.getValueAt(iSel[x], 1).toString()+" es completado, no puede ser cambiado a autorizado.", "Estado Proyecto(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            /*Actualiza el estad del proyecto a confirmado*/
            try 
            {            
                sQ = "UPDATE proys SET "
                        + "estad        = 'AU', "
                        + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE proy   = '" + jTab.getValueAt(iSel[x], 1).toString().replace("'", "''") + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
             }

            /*Actualiza el estad del proyecto en la tabla*/
            jTab.setValueAt("AU", iSel[x], 6);
        
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                   
                        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensaje de cambio de estad con éxito*/
        JOptionPane.showMessageDialog(null, "Estado de Proyecto(s) cambiado(s) a autorizado.", "Estado Proyecto(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBAutoProyActionPerformed
/*CR boton de autorizado*/
    private void jBAutoProyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAutoProyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBAutoProyKeyPressed

   
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
            jBMosT.doClick();
        /*Si se presiona F5 presiona el boton de copiar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBCop.doClick(); 
        /*Si se presiona CTRL + A entonces presiona el botón de abrir*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_A)
            jBAbr.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona F8 presiona el botón de alta de personas*/
        else if(evt.getKeyCode() == KeyEvent.VK_F8)
            jBPers.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbr;
    private javax.swing.JButton jBAutoProy;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBConfirProy;
    private javax.swing.JButton jBCop;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBPers;
    private javax.swing.JButton jBProPend;
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

}/*Fin de public class Clientes extends javax.swing.JFrame */
