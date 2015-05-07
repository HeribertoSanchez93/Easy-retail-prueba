//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Cursor;
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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import static ptovta.Princip.bIdle;




/*Clase que muestra las vtas para poderlas visualizar en su PDF*/
public class VVtas extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Declara variables de instancia*/        
    private int                 iContFi;        
    private String              sRFCLoc         = "";    
        
    /*Contador para modificar tabla*/
    private int                 iContCellEd;
    
    /*Declara variables originales*/
    private String              sTipDocOri;
    private String              sFolOri;
    private String              sNoSerOri;
    private String              sEstadOri;
    private String              sNomOri;
    private String              sTotOri;
    private String              sTotDescOri;
    private String              sFechOri;
    private String              sSucOri;
    private String              sCajOri;
    private String              sEstacOri;
    private String              sNomEstacOri;
    private String              sVtaOri;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean             bSel;
    
    /*Variable que contiene el borde actual*/
    private Border              bBordOri;
    
    
    
    /*Constructor sin argumentos*/
    public VVtas() 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBVe);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicializa el contador de filas en uno*/
        iContFi      = 1;

        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Ver ventas, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de tickets*/
        jTab.getColumnModel().getColumn(5).setPreferredWidth(250);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Obtiene de la base de datos todas las vtas canceladas y confirmadas y cargalas en la tabla*/
        vCarg();

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
                        sTipDocOri          = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sNoSerOri           = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sEstadOri           = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sNomOri             = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sTotOri             = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sTotDescOri         = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sFechOri            = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sSucOri             = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sCajOri             = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sEstacOri           = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sNomEstacOri        = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        sVtaOri             = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sTipDocOri,         jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sNoSerOri,          jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sEstadOri,          jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sNomOri,            jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sTotOri,            jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sTotDescOri,        jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sFechOri,           jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sSucOri,            jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sCajOri,            jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sEstacOri,          jTab.getSelectedRow(), 10);                        
                        jTab.setValueAt(sNomEstacOri,       jTab.getSelectedRow(), 11);                        
                        jTab.setValueAt(sVtaOri,            jTab.getSelectedRow(), 12);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public VVtas() */

    
    /*Obtiene de la base de datos todas las vtas y cargalas en la tabla*/
    private void vCarg()
    {        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;
        String      sQ;        
        
        /*Comprueba si se tienen que mostrar solamente las ventas el día*/
        String sVtaDi   = "";
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'hoyvtap'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca la cadena si se tiene que mostrar solo el día de hoy*/
                if(rs.getString("val").compareTo("1")==0)                                                   
                    sVtaDi  = " AND DATE(vtas.FEMI) = DATE(now()) ";
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }
        
        /*Comprueba si se tienen que mostrar las remisiones en el bùscador de ventas*/
        String sRem = "";    
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vremptovta'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Completa la cadena si se tienen que mostrar*/
                if(rs.getString("val").compareTo("1")==0)                                                   
                    sRem  = ",'REM'";
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }
        
        /*Comprueba si se tienen que mostrar los tickets en el bùscador de vtas*/
        String sTic = "";
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vticptovta'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Completa la cadena*/
                if(rs.getString("val").compareTo("1")==0)                                                   
                    sTic  = ",'TIK'";
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }
        
        /*Comprueba si se tienen que mostrar las facturas en el bùscador de vtas*/        
        String sFac = "";
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vfacptovta'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Completa la cadena si tiene que bùscar facturas*/
                if(rs.getString("val").compareTo("1")==0)                                                   
                    sFac  = ",'FAC'";
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }
        
        /*Crea la consulta correcta dependiendo de lo que se tenga que bùscar entre tickets, remisiones y facturas*/        
        sQ = "SELECT estacs.NOM, vtas.NOREFER, vtas.ESTAC, vtas.ESTAD, vtas.TIPDOC, vtas.VTA, vtas.NOSER, vtas.SUCU, vtas.NOCAJ, vtas.NOREFER, emps.NOM, vtas.TOT, vtas.FEMI FROM vtas LEFT OUTER JOIN estacs ON estacs.ESTAC = vtas.ESTAC LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP ) = vtas.CODEMP WHERE 1=1 " + sVtaDi + " AND vtas.TIPDOC IN(''" + sFac + sRem + sTic + ") ORDER BY vtas.NOREFER DESC";                                                
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();                    

        //Declara variables locales        
        String      sFol;
        String      sNom;
        String      sTot;
        String      sFech;
        
        /*Trae todas las vtas  de la base de datos y cargalas en la tabla*/
        try
        {            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene los datos de la consulta*/
                sFol                = rs.getString("vtas.NOREFER");                                
                sNom                = rs.getString("emps.NOM");                                 
                sTot                = rs.getString("vtas.TOT");                                 
                sFech               = rs.getString("vtas.FEMI");                                 
                
                /*Dale formato de moneda al total*/                
                double dCant    = Double.parseDouble(sTot);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sTot            = n.format(dCant);
                
                /*Agregalo a la tabla*/
                Object nu[]     = {iContFi, rs.getString("vtas.TIPDOC"), sFol, rs.getString("vtas.NOSER"), rs.getString("vtas.ESTAD"), sNom, sTot, sFech, rs.getString("vtas.SUCU"), rs.getString("vtas.NOCAJ"), rs.getString("vtas.ESTAC"), rs.getString("estacs.NOM"), rs.getString("vtas.VTA")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas el contador de filas en uno*/
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
                
    }/*Fin de private void vCarg()*/
            
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBVe = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMosT = new javax.swing.JButton();
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

        jBVe.setBackground(new java.awt.Color(255, 255, 255));
        jBVe.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBVe.setForeground(new java.awt.Color(0, 102, 0));
        jBVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ver.png"))); // NOI18N
        jBVe.setText("Ver");
        jBVe.setToolTipText("Ver Venta");
        jBVe.setNextFocusableComponent(jBActua);
        jBVe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVeMouseExited(evt);
            }
        });
        jBVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVeActionPerformed(evt);
            }
        });
        jBVe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVeKeyPressed(evt);
            }
        });
        jP1.add(jBVe, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 30, 120, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 90, 120, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Ventas:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 160, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Tipo Documento", "Folio", "Serie", "Estado", "Nombre Cliente", "Total", "Total Descuento", "Fecha", "Sucursal", "No. Caja", "Usuario", "Nombre Usuario", "Venta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 740, 290));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 140, 19));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 460, 20));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setNextFocusableComponent(jBVe);
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 320, 140, 19));

        jBActua.setBackground(new java.awt.Color(255, 255, 255));
        jBActua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBActua.setForeground(new java.awt.Color(0, 102, 0));
        jBActua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actualizar.png"))); // NOI18N
        jBActua.setText("Actualizar");
        jBActua.setToolTipText("Actualizar Registros (F5)");
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
        jP1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 60, 120, 30));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(756, 330, 120, 30));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 12, 130, 18));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de ver ticket*/
    private void jBVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVeActionPerformed
        
        /*Si no hay selección en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una venta de la tabla para ver.", "Ver Venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla*/
            jTab.grabFocus();                        
            return;            
        }
        
        //Declara variables locales
        String      sFol;               
        String      sSer;                                                
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                
        //Obtiene el RFC de la empresa local
        sRFCLoc     = Star.sGetRFCLoc(con);

        //Si hubo error entonces regresa
        if(sRFCLoc==null)
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

        /*Preguntar al usuario si esta seguro de querer ver los tickets*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres ver la(s) venta(s)?", "Ver Venta(s)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
        {
            //Cierra la base de datos
            Star.iCierrBas(con);
            return;                                    
        }
        
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();       
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Obtén algunos datos de la fila*/            
            sFol                    = jTab.getValueAt(iSel[x], 2).toString();          
            sSer                    = jTab.getValueAt(iSel[x], 3).toString();                                      

            /*Crea la ruta correcta dependiendo si es ticket o factura*/
            String sRut = "";
            if(jTab.getValueAt(iSel[x], 1).toString().compareTo("FAC")==0)
                sRut = sCarp + "\\Facturas\\" + Login.sCodEmpBD + "\\CFDI-" + sRFCLoc + "-" +sSer + "-" +sFol + ".pdf";
            else if(jTab.getValueAt(iSel[x], 1).toString().compareTo("TIK")==0)
                sRut = sCarp + "\\Tickets\\" + Login.sCodEmpBD +"\\" + sSer + "-" + sFol + ".pdf";                                        
            
            /*Si no existe el archivo PDF entonces*/
            if(!new File(sRut).exists())
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta \"" + sRut + "\" no existe.", "Venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
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
                                
    }//GEN-LAST:event_jBVeActionPerformed
   
           
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
    /*Cuando se presiona una tecla en el botón de ver ticket*/
    private void jBVeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVeKeyPressed
    
    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la ventana*/
        this.dispose();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

                   
    /*Cuando se presiona una  tecla en la tabla de colores*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed
          
    
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

    
    /*Función para cargar nuevamente los elementos en la tabla*/
    private void vCargT()
    {
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtiene de la base de datos todos los tickets y cargalos en la tabla*/
        vCarg();
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();        
    }
    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed
        
        /*Función para cargar nuevamente los elementos en la tabla*/
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
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Borra todos los item en la tabla de tickets*/
        DefaultTableModel dm    = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi                 = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si se tienen que mostrar solamente las ventas el día*/
        String sVtaDi   = "";
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'hoyvtap'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca la cadena si se tiene que mostrar solo el día de hoy*/
                if(rs.getString("val").compareTo("1")==0)                                                   
                    sVtaDi  = " AND DATE(vtas.FEMI) = DATE(now()) ";
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }
        
        /*Comprueba si se tienen que mostrar las facturas, remisiones y tickets en el bùscador de ventas*/
        String sCond = " AND vtas.TIPDOC IN(''";        
        try
        {
            sQ = "SELECT (SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vremptovta') AS valr, (SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vticptovta') AS valt, val FROM confgral WHERE clasif = 'vtas' AND conf = 'vfacptovta'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Si se tienen que mostrar entonces crea la condición*/
                if(rs.getString("val").compareTo("1")==0)                                                   
                    sCond   += ",'FAC'";
                if(rs.getString("valt").compareTo("1")==0)                                                   
                    sCond   += ",'TIK'";
                if(rs.getString("valr").compareTo("1")==0)                                                   
                    sCond   += ",'REM'";
                
                /*Termina la condición*/
                sCond       += ") ";
                
                /*Si no se tiene que mostrar nada entonces la ocndición será vacia*/
                if(rs.getString("val").compareTo("0")==0 && rs.getString("valt").compareTo("0")==0 && rs.getString("valr").compareTo("0")==0)
                    sCond   = "";
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }
        
        /*Obtiene de la base de datos todoas las vtas tadeo*/        
        try
        {       
            sQ = "SELECT estacs.NOM, vtas.SUCU, vtas.NOCAJ, vtas.ESTAC, vtas.NOSER, vtas.ESTAD, vtas.TIPDOC, vtas.VTA, vtas.NOREFER, emps.NOM, vtas.TOT, vtas.FEMI FROM vtas LEFT OUTER JOIN estacs ON estacs.ESTAC = vtas.ESTAC LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP ) = vtas.CODEMP WHERE 1=1 " + sCond + " " + sVtaDi + " AND (emps.NOM LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR vtas.TIPDOC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR vtas.ESTAD LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR vtas.NOREFER LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR vtas.TOT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR estacs.NOM LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR vtas.FEMI LIKE('%" + jTBusc.getText().replace(" ", "%") + "%'))ORDER BY vtas.NOREFER DESC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene el total*/
                String sTot     = rs.getString      ("vtas.TOT");                                                                   
                                
                /*Dale formato de moneda al total*/                
                double dCant    = Double.parseDouble(sTot);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sTot            = n.format(dCant);
                
                /*Agrega el registro a la tabla en caso de que alla habido una conincidencia*/                
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("vtas.TIPDOC"), rs.getString("vtas.NOREFER"), rs.getString("vtas.NOSER"), rs.getString("vtas.ESTAD"), rs.getString("emps.NOM"), sTot, rs.getString("vtas.FEMI"), rs.getString("vtas.SUCU"), rs.getString("vtas.NOCAJ"), rs.getString("vtas.ESTAC"), rs.getString("estacs.NOM"), rs.getString("vtas.VTA")};
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

        /*Vuelve a poner el foco del teclado en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se presiona el botón de actualizar*/
    private void jBActuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActuaActionPerformed

        /*Función para cargar todos los elementos en la tabla*/
        vCargT();

    }//GEN-LAST:event_jBActuaActionPerformed

    
    /*Cuando se presona una tecla en el botón de actualizar*/
    private void jBActuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBActuaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBActuaKeyPressed

    
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

    
    /*Cuando se mueve el la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
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
    private void jBVeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVe.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVeMouseEntered

    
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

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVe.setBackground(colOri);
        
    }//GEN-LAST:event_jBVeMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBActuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBActua.setBackground(colOri);
        
    }//GEN-LAST:event_jBActuaMouseExited

    
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

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBVe);
        
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
        /*Si se presiona F3 presiona el boton de buscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();        
        /*Si se presiona F4 presiona el boton de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();
        /*Si se presiona F5 presioan el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBVe;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
