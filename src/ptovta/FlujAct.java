//Paquete
package ptovta;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para controlar los seguimientos*/
public class FlujAct extends javax.swing.JFrame 
{
    /*Variable que contiene el id de la tabla que se seleccionó*/    
    private int             iID;      
    
    /*Declara variables originales*/
    private String          sUsrOriG;
    private String          sActOriG;
    private String          sNomOriG;
    
    /*Declara contadores globales*/
    private int             iContCellEd;    
    private int             iContFi;
        
    
        
    /*Constructor sin argumentos*/
    public FlujAct() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();

        /*Crea el listener para cuando se cambia de selección en la tabla de usuarios*/
        jTabUsr.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {           
                /*Si no hay selección en la tabla regresa*/
                if(jTabUsr.getSelectedRow()==-1)
                    return;
                
                /*Borra el texto del control html*/
                jED1.setText("");
                
                /*Carga todas las actividades asigandas y rcibidas en las tablas*/
                vCargAct();               
            }
        });
        
        /*Crea el listener para cuando se cambia de selección en la tabla de activiades recibidas*/
        jTabActRec.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {           
                /*Si no hay selección en la tabla regresa*/
                if(jTabActRec.getSelectedRow()==-1)
                    return;
                
                /*Establece el id que se a seleccionado*/
                iID = Integer.parseInt(jTabActRec.getValueAt(jTabActRec.getSelectedRow(), 3).toString());
                
                /*Carga todos los seguimientos recibidos y rcibidos en las tablas*/
                vCargSeg();               
            }
        });
        
        /*Crea el listener para cuando se cambia de selección en la tabla de activiades asigandas*/
        jTabActAsig.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {           
                /*Si no hay selección en la tabla regresa*/
                if(jTabActAsig.getSelectedRow()==-1)
                    return;
                
                /*Establece el id que se a seleccionado*/
                iID = Integer.parseInt(jTabActAsig.getValueAt(jTabActAsig.getSelectedRow(), 3).toString());
                
                /*Carga todos los seguimientos asigandos y rcibidos en las tablas*/
                vCargSeg();               
            }
        });
        
        /*Esconde la columna del id de la tabla de actividades asignadas y recibidas*/
        jTabActRec.getColumnModel().getColumn(3).setMinWidth(0);
        jTabActRec.getColumnModel().getColumn(3).setMaxWidth(0);
        jTabActAsig.getColumnModel().getColumn(3).setMinWidth(0);
        jTabActAsig.getColumnModel().getColumn(3).setMaxWidth(0);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Para que no se muevan las columnas*/
        jTabActAsig.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Flujo de actividades, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en 1*/
        iContFi      = 1;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTabActAsig.getModel());
        jTabActAsig.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el campo de edición de peso*/
        jTTit.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de actividades recibidas y asignadas*/        
        jTabActRec.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTabActRec.getColumnModel().getColumn(2).setPreferredWidth(300);
        jTabActAsig.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTabActAsig.getColumnModel().getColumn(2).setPreferredWidth(300);
        
        /*Establece el tamaño de las columnas de la tabla usuarios*/        
        jTabUsr.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTabUsr.getColumnModel().getColumn(2).setPreferredWidth(300);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador en todas las tablas y controles de área*/
        jTabActAsig.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTabActAsig.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        jTabActRec.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTabActRec.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        jTabUsr.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTabUsr.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        jTASeg.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTASeg.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Obtiene todos los usuarios de la base de datos y cargalos en la tabla*/
        vCargUsr();
        
        /*Para que las tablas tengan scroll horisontal*/
        jTabActRec.setAutoResizeMode    (0);
        jTabActAsig.setAutoResizeMode   (0);
        jTabUsr.setAutoResizeMode       (0);
        
        /*Crea el listener para la tabla de actividades recibidas, para cuando se modifique una celda guardarlo en la base de datos el cambio*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces que regresa*/
                if(jTabActRec.getSelectedRow()==-1)
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
                        sUsrOriG            = jTabActRec.getValueAt(jTabActRec.getSelectedRow(), 1).toString();                                                                                  
                        sActOriG            = jTabActRec.getValueAt(jTabActRec.getSelectedRow(), 2).toString();                                                                                                          
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd         = 1;
                            
                        /*Coloca los valores origianales*/
                        jTabActRec.setValueAt(sUsrOriG,       jTabActRec.getSelectedRow(), 1);
                        jTabActRec.setValueAt(sActOriG,       jTabActRec.getSelectedRow(), 2);
                                                                    
                    }/*Fin de else*/                                                
                                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla de activiades recibidas*/
        jTabActRec.addPropertyChangeListener(pro);
        
        /*Crea el listener para la tabla de actividades asignadas, para cuando se modifique una celda guardarlo en la base de datos el cambio*/
        pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces que regresa*/
                if(jTabActAsig.getSelectedRow()==-1)
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
                        sUsrOriG            = jTabActAsig.getValueAt(jTabActAsig.getSelectedRow(), 1).toString();                                                                                  
                        sActOriG            = jTabActAsig.getValueAt(jTabActAsig.getSelectedRow(), 2).toString();                                                                                                          
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd         = 1;
                            
                        /*Coloca los valores origianales*/
                        jTabActAsig.setValueAt(sUsrOriG,       jTabActAsig.getSelectedRow(), 1);
                        jTabActAsig.setValueAt(sActOriG,       jTabActAsig.getSelectedRow(), 2);
                                                                    
                    }/*Fin de else*/                                                
                                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla de activiades asignadas*/
        jTabActAsig.addPropertyChangeListener(pro);
        
        /*Crea el listener para la tabla de usuarios, para cuando se modifique una celda guardarlo en la base de datos el cambio*/
        pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces que regresa*/
                if(jTabUsr.getSelectedRow()==-1)
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
                        sUsrOriG            = jTabUsr.getValueAt(jTabUsr.getSelectedRow(), 1).toString();                                                                                  
                        sNomOriG            = jTabUsr.getValueAt(jTabUsr.getSelectedRow(), 2).toString();                                                                                                          
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd         = 1;
                            
                        /*Coloca los valores origianales*/
                        jTabUsr.setValueAt(sUsrOriG,       jTabUsr.getSelectedRow(), 1);
                        jTabUsr.setValueAt(sNomOriG,       jTabUsr.getSelectedRow(), 2);
                                                                    
                    }/*Fin de else*/                                                
                                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla de usuarios*/
        jTabUsr.addPropertyChangeListener(pro);
        
    }/*Fin de public Peso() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBDelRec = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jTTit = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabActAsig = new javax.swing.JTable();
        jBNew = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTabActRec = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTabUsr = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTASeg = new javax.swing.JTextArea();
        jBSeg = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jED1 = new javax.swing.JEditorPane();
        jBDelAsig = new javax.swing.JButton();

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

        jBDelRec.setBackground(new java.awt.Color(255, 255, 255));
        jBDelRec.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDelRec.setForeground(new java.awt.Color(0, 102, 0));
        jBDelRec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDelRec.setToolTipText("Borrar actividad(es) recibidas");
        jBDelRec.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDelRec.setNextFocusableComponent(jTabActAsig);
        jBDelRec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDelRecMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDelRecMouseExited(evt);
            }
        });
        jBDelRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelRecActionPerformed(evt);
            }
        });
        jBDelRec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelRecKeyPressed(evt);
            }
        });
        jP1.add(jBDelRec, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 40, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Usuarios:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, 110, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jTabActRec);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 550, 50, -1));

        jTTit.setToolTipText("Título de seguimiento");
        jTTit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTit.setNextFocusableComponent(jBNew);
        jTTit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTitFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTitFocusLost(evt);
            }
        });
        jTTit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTitKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTitKeyTyped(evt);
            }
        });
        jP1.add(jTTit, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, 150, 20));

        jTabActAsig.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Usuario", "Nombre Actividad", "id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabActAsig.setGridColor(new java.awt.Color(255, 255, 255));
        jTabActAsig.setNextFocusableComponent(jBDelAsig);
        jTabActAsig.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTabActAsig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabActAsigKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTabActAsig);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 320, 140));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setToolTipText("Agregar nueva actividad (Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jTabUsr);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 20, 40, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 550, 120, 20));

        jTabActRec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Usuario", "Nombre Actividad", "id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabActRec.setGridColor(new java.awt.Color(255, 255, 255));
        jTabActRec.setNextFocusableComponent(jBDelRec);
        jTabActRec.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTabActRec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabActRecKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTabActRec);

        jP1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 320, 140));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Actividades recibidas:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 170, -1));

        jTabUsr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Usuario", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTabUsr.setGridColor(new java.awt.Color(255, 255, 255));
        jTabUsr.setNextFocusableComponent(jBSeg);
        jTabUsr.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTabUsr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabUsrKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTabUsr);

        jP1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 40, 300, 140));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Actividades asignadas:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 170, -1));

        jLabel2.setText("Seguimiento:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 180, -1));

        jTASeg.setColumns(20);
        jTASeg.setLineWrap(true);
        jTASeg.setRows(5);
        jTASeg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTASeg.setNextFocusableComponent(jED1);
        jTASeg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTASegFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTASegFocusLost(evt);
            }
        });
        jTASeg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTASegKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTASeg);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 220, 350));

        jBSeg.setBackground(new java.awt.Color(255, 255, 255));
        jBSeg.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSeg.setForeground(new java.awt.Color(0, 102, 0));
        jBSeg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBSeg.setToolTipText("Agregar nuevo seguimiento");
        jBSeg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSeg.setNextFocusableComponent(jED1);
        jBSeg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBSegMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBSegMouseExited(evt);
            }
        });
        jBSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSegActionPerformed(evt);
            }
        });
        jBSeg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBSegKeyPressed(evt);
            }
        });
        jP1.add(jBSeg, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 40, 20));

        jED1.setEditable(false);
        jED1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jED1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jED1.setForeground(new java.awt.Color(0, 102, 153));
        jED1.setNextFocusableComponent(jTabActRec);
        jED1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jED1KeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(jED1);

        jP1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 720, 370));

        jBDelAsig.setBackground(new java.awt.Color(255, 255, 255));
        jBDelAsig.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDelAsig.setForeground(new java.awt.Color(0, 102, 0));
        jBDelAsig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDelAsig.setToolTipText("Borrar actividad(es) asigandas");
        jBDelAsig.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDelAsig.setNextFocusableComponent(jTTit);
        jBDelAsig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDelAsigMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDelAsigMouseExited(evt);
            }
        });
        jBDelAsig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelAsigActionPerformed(evt);
            }
        });
        jBDelAsig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelAsigKeyPressed(evt);
            }
        });
        jP1.add(jBDelAsig, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, 40, 20));

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

    
    /*Carga todas las actividades asigandas y rcibidas en las tablas*/
    private void vCargAct()
    {
        /*Borra el contenido de la tabla de recibidas*/
        DefaultTableModel te = (DefaultTableModel)jTabActRec.getModel();                    
        te.setRowCount(0);
                      
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Resetea el contador de filas*/
        iContFi = 1;

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todas las activiades asignadas del usuario seleccionado al usuario actual*/
        try
        {
            sQ = "SELECT id_id, tit, usrenvi FROM flujact WHERE usrrecib = '" + Login.sUsrG + "' AND usrenvi = '" + jTabUsr.getValueAt(jTabUsr.getSelectedRow(), 1).toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("usrenvi"), rs.getString("tit"), rs.getString("id_id")};
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
        
        /*Crea el modelo para cargar cadenas en la tabla de asigandas*/
        te = (DefaultTableModel)jTabActAsig.getModel();              
        
        /*Borra el contenido de la tabla de actividades asignadas*/
        te.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Trae todas las activiades asignadas a los usuarios por el usuario actual*/
        try
        {
            sQ = "SELECT id_id, tit, usrenvi FROM flujact WHERE usrenvi = '" + Login.sUsrG + "' AND usrrecib = '" + jTabUsr.getValueAt(jTabUsr.getSelectedRow(), 1).toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {                
                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("usrenvi"), rs.getString("tit"), rs.getString("id_id")};
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
        
    }/*Fin de private void vCargAct()*/
                
                                
    /*Carga todoslo seguimientos de las activides recibidas*/
    private void vCargSeg()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Resetea el contador de filas*/
        iContFi = 1;

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;       
        String      sQ;
        
        /*Trae todos los seguimientos de las actividades recibidas de la selección actual*/      
        String sMsj = "";
        try
        {
            sQ = "SELECT segui, partflujact.FALT, partflujact.ESTAC FROM partflujact LEFT OUTER JOIN flujact ON flujact.ID_ID = partflujact.IDFLUJ WHERE flujact.ID_ID  = " + iID;
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Completa el mensajea*/
                sMsj   += "No: " + iContFi + " Fecha: " + rs.getString("falt") + " Usuario: <" + rs.getString("estac") + ">" + System.getProperty( "line.separator" ) + rs.getString("segui") + System.getProperty( "line.separator" ) + "----------------------------------" + System.getProperty( "line.separator" ) + System.getProperty( "line.separator" );                
                                                
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
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Agrega en el control de los mensajes el mensaje concatenado con otros datos*/
        jED1.setText(sMsj);                                                                
        
    }/*Fin de private void vCargSeg()*/
    
    
    /*Obtiene todos los usuarios de la base de datos y cargalos en la tabla*/
    private void vCargUsr()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTabUsr.getModel();                    
        
        /*Resetea el contador de filas*/
        iContFi = 1;

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todas los usuarios de la base de datos y cargalos en la tabla*/
        try
        {
            sQ = "SELECT estac, nom FROM estacs";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("estac"), rs.getString("nom")};
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
        
    }/*Fin de private void vCargUsr()*/
    
    
    /*Cuando se presiona el botón de borrar actividades recibidas*/
    private void jBDelRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelRecActionPerformed
                                                
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTabActRec.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una actividad recibida para borrar.", "Borrar Actividad(es)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTabActRec.grabFocus();                       
            return;            
        }
        
        /*Preguntar al usuario si esta seguro de querer borrar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere borrar la(s) actividad(es) recibidas?", "Borrar Actividad(es)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Para saber si hubo cambios*/
        boolean bMov    = false;

        //Declara variables de la base de datos    
        Statement   st;                
        String      sQ;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTabActRec.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTabActRec.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
       {        
            /*Borra la actividad en la tabla de encabezados*/
            try 
            {                
                sQ = "DELETE FROM flujact WHERE id_id = " + jTabActRec.getValueAt(iSel[x], 3).toString();                                            
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             {    
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                   
            }  

            /*Borra las partidas de la actividad*/
            try 
            {                
                sQ = "DELETE FROM partflujact WHERE idfluj = " + jTabActRec.getValueAt(iSel[x], 3).toString();                                            
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
        }                                                                              
         
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensajea de éxito*/
        if(bMov)
            JOptionPane.showMessageDialog(null, "Actividad(es) borrada(s) con éxito.", "Actividades", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                       
    }//GEN-LAST:event_jBDelRecActionPerformed
   
   
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelRecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelRecKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelRecKeyPressed
    
    
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

       
    /*Cuando se presiona una tecla en el campo de edición de línea*/
    private void jTTitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTitKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTitKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de línea*/
    private void jTTitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTitFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTit.setSelectionStart(0);jTTit.setSelectionEnd(jTTit.getText().length());        
        
    }//GEN-LAST:event_jTTitFocusGained
    
    
    /*Cuando se presiona una  tecla en la tabla de pes*/
    private void jTabActAsigKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabActAsigKeyPressed

        /*Si se presionó enter entonces presiona el botón de nuevo y regresa*/
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            jBNew.doClick();
            return;
        }
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabActAsigKeyPressed

        
    /*Cuando se pierde el foco del teclado en el campo de peso*/
    private void jTTitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTitFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTTit.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTTit.getText().compareTo("")!=0)
            jTTit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                        
    }//GEN-LAST:event_jTTitFocusLost
                  
    
    /*Cuando se tipea una tecla en el campo de código de peso*/
    private void jTTitKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTitKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTTitKeyTyped

            
    /*Cuando se presiona el botón de nueva actividad*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed

        /*Si no a seleccionado un usuario por lo menos en la tabla entonces*/
        if(jTabUsr.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un usuario para asignarle actividades", "Actividades", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTabUsr.grabFocus();
            return;
        }
        
        /*Preguntar al usuario si esta seguro de que querer asignar las actividades*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres generar la actividad?", "Actividades", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;                
        String      sQ;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTabUsr.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Inserta en la base de datos la actividad*/
            try 
            {                
                sQ = "INSERT INTO flujact(  usrenvi,               usrrecib,                                           tit,                                         estac,                 sucu,                 nocaj) "
                            + "VALUES('" +  Login.sUsrG + "', '" + jTabUsr.getValueAt(iSel[x], 1).toString() + "','" + jTTit.getText().replace("'", "''") + "','" + Login.sUsrG + "', '" + Star.sSucu + "', '" + Star.sNoCaj + "')";                                            
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             {    
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                    
            }              
        }      

        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Actividad(es) registrada(s) con éxito.", "Actividades", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se presiona una tecla en el botón de actualizar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBNewKeyPressed

    
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
    private void jBDelRecMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelRecMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDelRec.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelRecMouseEntered

    
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

      
    /*Cuando el mouse sale del botón específico*/
    private void jBDelRecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelRecMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDelRec.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDelRecMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited
   
    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se presiona una tecla en la tabla de activiades recibidas*/
    private void jTabActRecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabActRecKeyPressed

        /*Si se presionó enter entonces presiona el botón de nuevo y regresa*/
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            jBNew.doClick();
            return;
        }
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabActRecKeyPressed

    
    /*Cuando se presiona una tecla en la tabla de usuarios*/
    private void jTabUsrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabUsrKeyPressed

        /*Si se presionó enter entonces presiona el botón de nuevo y regresa*/
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            jBNew.doClick();
            return;
        }
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabUsrKeyPressed

    
    /*Cuando el mouse entra en el botón de seguimiento*/
    private void jBSegMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSegMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSeg.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSegMouseEntered

    
    /*Cuando el mouse sale del botón de seguimiento*/
    private void jBSegMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSegMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBSeg.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSegMouseExited

    
    /*Cuando se presiona el botón agregar nuevo seguimiento*/
    private void jBSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSegActionPerformed
        
        /*Si no a escrito nada en el control del seguimiento entonces*/
        if(jTASeg.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa primero un seguimiento.", "Seguimiento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTASeg.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el contrl y regresa*/
            jTASeg.grabFocus();
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
        
        /*Inserta en la base de datos el nuevo seguimiento*/
        try 
        {                
            sQ = "INSERT INTO partflujact(idfluj,       segui,                                                sucu,                 nocaj,                estac) "
                            + "VALUES(" + iID + ", '" + jTASeg.getText().replace("'", "''").trim() + "', '" + Star.sSucu + "', '" + Star.sNoCaj + "','" + Login.sUsrG + "')";                                            
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

        /*Obtiene la hora del sistema*/
        DateFormat dtFor = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        
        /*Coloca el nuevo seguimiento en el control*/
        jED1.setText(jED1.getText() + "No: " + iContFi + " Fecha: " + dtFor.format(date) + " Usuario: <" + Login.sUsrG + ">" + System.getProperty( "line.separator" ) + jTASeg.getText().trim() + System.getProperty( "line.separator" ) + "----------------------------------" + System.getProperty( "line.separator" ) + System.getProperty( "line.separator" ));                

        /*Deja en cadena vacia el control*/
        jTASeg.setText("");
        
    }//GEN-LAST:event_jBSegActionPerformed

    
    /*Cuando se  presiona una tecla en el botón de seguimiento*/
    private void jBSegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSegKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSegKeyPressed

    
    /*Cuando se presiona una tecla en el cotrol editor*/
    private void jED1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jED1KeyPressed

        /*Función escalable para cuando se presiona una tecla en el módulo*/
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jED1KeyPressed

    
    /*Cuando se presiona una tecla en el control del seguimiento*/
    private void jTASegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTASegKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTASegKeyPressed

    
    /*Cuando el foco del teclado entra en el control del seguimiento*/
    private void jTASegFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTASegFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTASeg.setSelectionStart(0);jTASeg.setSelectionEnd(jTASeg.getText().length());        
        
    }//GEN-LAST:event_jTASegFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del seguimiento*/
    private void jTASegFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTASegFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTASeg.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTASeg.getText().compareTo("")!=0)
            jTASeg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTASegFocusLost

    
    /*Cuando el mouse entra en el botón de borrar seguimiento de asignados*/
    private void jBDelAsigMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelAsigMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDelAsig.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelAsigMouseEntered

    
    /*Cuando el mouse sale del botón de borrar actividades asigandas*/
    private void jBDelAsigMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelAsigMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBDelAsig.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDelAsigMouseExited

    
    /*Cuando se presiona el botón de borrar actividades asignadas*/
    private void jBDelAsigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelAsigActionPerformed
        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTabActAsig.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una actividad asiganda para borrar.", "Borrar Actividad(es)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTabActRec.grabFocus();                       
            return;            
        }
        
        /*Preguntar al usuario si esta seguro de querer borrar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere borrar la(s) actividad(es) asignada(s)?", "Borrar Actividad(es)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Para saber si hubo cambios*/
        boolean bMov    = false;

        //Declara variables de la base de datos    
        Statement   st;                
        String      sQ;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTabActAsig.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTabActAsig.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {        
            /*Borra la actividad en la tabla de encabezados*/
            try 
            {                
                sQ = "DELETE FROM flujact WHERE id_id = " + jTabActAsig.getValueAt(iSel[x], 3).toString();                                            
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             {              
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                    
            }  

            /*Borra las partidas de la actividad*/
            try 
            {                
                sQ = "DELETE FROM partflujact WHERE idfluj = " + jTabActAsig.getValueAt(iSel[x], 3).toString();                                            
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
        }                                                                              
         
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensajea de éxito*/
        if(bMov)
            JOptionPane.showMessageDialog(null, "Actividad(es) borrada(s) con éxito.", "Actividades", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBDelAsigActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar activiades asignadas*/
    private void jBDelAsigKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelAsigKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelAsigKeyPressed
           
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDelRec.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F5 presioan el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBNew.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBDelAsig;
    private javax.swing.JButton jBDelRec;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBSeg;
    private javax.swing.JEditorPane jED1;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea jTASeg;
    private javax.swing.JTextField jTTit;
    private javax.swing.JTable jTabActAsig;
    private javax.swing.JTable jTabActRec;
    private javax.swing.JTable jTabUsr;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
