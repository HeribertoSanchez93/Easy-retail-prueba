//Paquete
package cats;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import ptovta.Star;
import ptovta.Login;




/*Clase que implementa los viats de un proyecto*/
public class Viats extends javax.swing.JFrame 
{
    /*Declara variables de instancia*/  
    private final String    sCodProGlo;    
    private int             iContF;
    
    /*Contador para modificar tabla*/
    private int             iContCellEd;
    
    /*Declara variables originales*/
    private String          sViatOri;
    private String          sGastOri;
    private String          sPersOri;
    private String          sImpoOri;
    private String          sFDeOri;
    private String          sFAOri;    

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;
    
    
    
    /*Consructor sin argumentos*/
    public Viats(String sCodP, Date sIniObr) 
    {
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Recibe del otro formulario parámetros*/
        sCodProGlo      = sCodP;
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);

        /*Inicialmente esta deseleccionada la tabla*/
        bSel            = false;
        
        /*Inicia el contador de las filas*/
        iContF          = 1;
                
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Viáticos de proyecto: " + sCodProGlo + ", Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Pon el foco del teclado en el calendario*/                       
        jCalA.grabFocus();
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Carga las pers en el combobox*/
        vLoadPers();
        
        /*Carga los viáticos disponibles en el combobox*/
        vLoadViats();
        
        /*Carga las diferentes formas de pago en el combobox correspondiente*/
        vLoadPags();
        
        /*Comprueba si existen gastos dados de alta*/
        vGast();
        
        /*Comprueba si existen pers dadas de alta*/
        vPers();                
        
        /*Obtiene todos los viáticos para este proyecto*/
        vGetViat();                
        
        /*Establece la fecha inicial de los calendarios con la del inicio de la obra*/
        jCalDe.setDate      (sIniObr);
        jCalA.setDate       (sIniObr);
        
        /*Carga cadenas vacias en los comboboxes en caso de que no alla datos*/
        jComViat.addItem    ("");
        jComGast.addItem    ("");
        jComPers.addItem    ("");
                
        /*Establece el tamaño de las columnas*/
        jTab.getColumnModel().getColumn(0).setPreferredWidth(8);
    
        /*Crea el listener para cuando se cambia de selección en la tabla de viáticos*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {                
                /*Carga los datos del registro seleccionado en la forma*/
                vLoadDats();               
            }
        });
        
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
                        sViatOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sGastOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sPersOri        = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sImpoOri        = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sFDeOri         = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sFAOri          = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();                        
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sViatOri,       jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sGastOri,       jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sPersOri,       jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sImpoOri,       jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sFDeOri,        jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sFAOri,         jTab.getSelectedRow(), 6);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public Viats() */
      
    
    /*Carga los datos del registro seleccionado en la forma*/
    private void vLoadDats()
    {
        /*Si la fila es -1 regresa*/
        if(jTab.getSelectedRow()==-1)
            return;                        
               
        /*Coloca los valores en los comboboxes*/
        jComViat.setSelectedItem    (jTab.getValueAt(jTab.getSelectedRow(), 1).toString());
        jComGast.setSelectedItem    (jTab.getValueAt(jTab.getSelectedRow(), 2).toString());
        jComPers.setSelectedItem    (jTab.getValueAt(jTab.getSelectedRow(), 3).toString());                        
        
        /*Coloca el importe en el campo*/
        jTImp.setText               (jTab.getValueAt(jTab.getSelectedRow(), 4).toString());
        
        /*Coloca el calendario la fecha de inicio*/
        SimpleDateFormat fT    = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date fecha;
        try
        {            
            fecha                           = fT.parse(jTab.getValueAt(jTab.getSelectedRow(), 5).toString());
            jCalDe.setDate(fecha);        
        }
        catch(ParseException expnPARS)
        {
            //Procesa el error y regresa
            Star.iErrProc(expnPARS.getMessage(), Star.sErrPARS, expnPARS.getStackTrace());
            return;                        
        }        
	       
        /*Coloca el calendario la fecha final*/               
        try
        {            
            fecha                           = fT.parse(jTab.getValueAt(jTab.getSelectedRow(), 6).toString());
            jCalA.setDate(fecha);        
        }
        catch(ParseException expnPARS)
        {
            //Procesa el error
            Star.iErrProc(expnPARS.getMessage(), Star.sErrPARS, expnPARS.getStackTrace());                  
        }      
        
    }/*Fin de private void vLoadDats()*/
    
                
    /*Carga las pers en el combobox*/
    private void vLoadPers()
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

        /*Trae todas las pers de la base de datos*/
        try
        {
            sQ = "SELECT nom FROM pers";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            while(rs.next())
                jComPers.addItem(rs.getString("nom"));                                                                        
        }
        catch(SQLException expnSQL)
        {            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }  
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vLoadPers()*/
    
        
    /*Comprueba si existen gastos dados de alta*/
    private void vGast()
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

        /*Comprueba si existen pags dados de alta*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT pagdescrip FROM pags";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;                                                                        
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

        /*Si no existen pags entonces avisa al usuario*/
        if(!bSi)
            JOptionPane.showMessageDialog(null, "No existen formas de pago dadas de alta.", "Pagos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }/*Fin de private void vGast()*/
    
    
    /*Comprueba si existen pers dadas de alta*/
    private void vPers()
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

        /*Comprueba si existen personas dadas de alta*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT nom FROM pers";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;                                                                        
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

        /*Si no existen pers entonces avisa al usuario*/
        if(!bSi)
            JOptionPane.showMessageDialog(null, "No existen pers dadas de alta.", "Personas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }/*Fin de private void vPers()*/

    
    /*Obtiene todos los viáticos para este proyecto*/
    private void vGetViat()
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

        /*Obtiene todos los viáticos para este proyecto*/        
        try
        {
            sQ = "SELECT * FROM viatspro WHERE codpro = '" + sCodProGlo + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene el importe y dale formato de moneda*/
                String sImp         = rs.getString("imp");
                
                /*Dale formato de moneda*/                
                double dCant        = Double.parseDouble(sImp);                
                NumberFormat n      = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sImp                = n.format(dCant);

                /*Coloca en la tabla todos los registros*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContF, rs.getString("codviat"), rs.getString("pag"), rs.getString("nombper"), sImp, rs.getString("f_de"), rs.getString("f_a"), rs.getString("id_id")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas*/
                ++iContF;                                                
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
        
    }/*Fin de private void vGetViat()*/

    
    /*Carga las pers en el combobox*/
    private void vLoadPags()
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

        /*Trae todas las pers de la base de datos*/
        try
        {
            sQ = "SELECT pag FROM pags";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entones insertalo en el combox*/
            while(rs.next())
                jComGast.addItem(rs.getString("pag"));                                                                       
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }  
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vLoadPags()*/
    
    
    /*Carga los viáticos en el combobox*/
    private void vLoadViats()
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

        /*Trae todos los viáticos de la base de datos*/
        try
        {
            sQ = "SELECT viat FROM viats";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces insertalo en el combobox*/
            while(rs.next())
                jComViat.addItem(rs.getString("viat"));                                                                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }  
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vLoadViats()*/
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jCalA = new com.toedter.calendar.JCalendar();
        jComViat = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jComGast = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jComPers = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jTImp = new javax.swing.JTextField();
        jBNew = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jTDescripPag = new javax.swing.JTextField();
        jCalDe = new com.toedter.calendar.JCalendar();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jBGuar = new javax.swing.JButton();
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

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jBNew);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 420, 110, 30));

        jCalA.setNextFocusableComponent(jComViat);
        jCalA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCalAKeyPressed(evt);
            }
        });
        jP1.add(jCalA, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 380, 230));

        jComViat.setNextFocusableComponent(jComGast);
        jComViat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComViatFocusLost(evt);
            }
        });
        jComViat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComViatKeyPressed(evt);
            }
        });
        jP1.add(jComViat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 250, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Importe:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 270, 80, -1));

        jComGast.setNextFocusableComponent(jTDescripPag);
        jComGast.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComGastFocusLost(evt);
            }
        });
        jComGast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComGastActionPerformed(evt);
            }
        });
        jComGast.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComGastKeyPressed(evt);
            }
        });
        jP1.add(jComGast, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, 110, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Al:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 170, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Viático", "Gasto", "Persona", "Importe", "Fecha Del", "Fecha Al", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBSal);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 910, 220));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Forma de Gasto:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 170, -1));

        jComPers.setNextFocusableComponent(jTImp);
        jComPers.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComPersFocusLost(evt);
            }
        });
        jComPers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComPersKeyPressed(evt);
            }
        });
        jP1.add(jComPers, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 290, 280, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Persona:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 270, 170, -1));

        jTImp.setText("$0.00");
        jTImp.setNextFocusableComponent(jTab);
        jTImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTImpFocusLost(evt);
            }
        });
        jTImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTImpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTImpKeyTyped(evt);
            }
        });
        jP1.add(jTImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 290, 80, -1));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre8.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nuevo Viático (Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jBDel);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 330, 110, 30));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Viático(s) (Ctrl+SUPR)");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jBGuar);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 360, 110, 30));

        jTDescripPag.setEditable(false);
        jTDescripPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripPag.setNextFocusableComponent(jComPers);
        jTDescripPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripPagFocusLost(evt);
            }
        });
        jTDescripPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripPagKeyPressed(evt);
            }
        });
        jP1.add(jTDescripPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 290, 190, 20));

        jCalDe.setNextFocusableComponent(jCalA);
        jCalDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCalDeKeyPressed(evt);
            }
        });
        jP1.add(jCalDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 380, 230));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Viático:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 160, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Del:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, -1));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar");
        jBGuar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBGuar.setNextFocusableComponent(jCalDe);
        jBGuar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGuarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGuarMouseExited(evt);
            }
        });
        jBGuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuarActionPerformed(evt);
            }
        });
        jBGuar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGuarKeyPressed(evt);
            }
        });
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 390, 110, 30));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(926, 580, 120, 20));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 312, 130, 18));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 1049, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de cancelar*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra el formulario*/
        this.dispose();       
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed
    
    
    /*Cuando se presiona una tecla en el botón de nuevo*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed
        
    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en el combobox de viáticos*/
    private void jComViatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComViatKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComViatKeyPressed

    
    /*Cuando se presiona una tecla en el calendario al*/
    private void jCalAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCalAKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCalAKeyPressed

    
    /*Cuando se presiona una tecla en el combobox de gasto*/
    private void jComGastKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComGastKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComGastKeyPressed

    
    /*Cuando se presiona una tecla en el combobx de persona*/
    private void jComPersKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComPersKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComPersKeyPressed

    
    /*Cuando se presiona una tecla en el a tabla de viáticos*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se tipea una tecla en el campo del importe*/
    private void jTImpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTImpKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTImpKeyTyped

    
    /*Cuando se presiona una tecla en el campo del importe del viático*/
    private void jTImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTImpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTImpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del importe*/
    private void jTImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTImp.setSelectionStart(0);jTImp.setSelectionEnd(jTImp.getText().length());   
        
    }//GEN-LAST:event_jTImpFocusGained

    
    /*Cuando se presiona una tecla en el botón de agregar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
        
        //Declara variables locales
        String sViat;
        String sPag;
        String sPer;
        String sImp;
        String sFD;
        String sFA;
        
        
        
        
        /*Obtiene los datos seleccionados del combobox*/
        sViat   = jComViat.getSelectedItem().toString();
        sPag    = jComGast.getSelectedItem().toString();
        sPer    = jComPers.getSelectedItem().toString();
        
        /*Obtiene el importe*/
        sImp    = jTImp.getText();
        
        /*Quitale al importe el formato de moneda*/
        sImp    = sImp.replace("$", "").replace(",", "");        

        /*Si se selecciono la cadena vacia en el viático entonces*/
        if(sViat.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComViat.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Avisa al usuario*/
            JOptionPane.showMessageDialog(null, "Selecciona un viático.", "Viático", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el combobox de los viáticos y regresa*/
            jComViat.grabFocus();                        
            return;
        }
        
        /*Si se selecciono la cadena vacia en la forma de pago entonces*/
        if(sPag.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComGast.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Avisa al usuario*/
            JOptionPane.showMessageDialog(null, "Selecciona una forma de pago.", "Pago", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el combobox de los pagos y regresa*/
            jComGast.grabFocus();                        
            return;
        }
        
        /*Si se selecciono la cadena vacia en la persona*/
        if(sPer.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComPers.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Avisa al usuario*/
            JOptionPane.showMessageDialog(null, "Selecciona una persona.", "Persona", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el combobox de las personas y regresa*/
            jComPers.grabFocus();                        
            return;
        }
        
        /*Obtiene la fecha de*/
        Date fec                = jCalDe.getDate();
        SimpleDateFormat sd     = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        sFD                     = sd.format(fec); 
        
        /*Obtiene la fecha a*/
        fec                     = jCalA.getDate();        
        sFA                     = sd.format(fec); 
               
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Inserta en la base de datos el registro*/
        try 
        {            
            sQ = "INSERT INTO viatspro(codpro,               codviat,         pag,          nombper,        imp,            estac,                     f_de,         f_a,           sucu,                       nocaj) " + 
                           "VALUES('" +sCodProGlo + "', '" + sViat + "', '" + sPag + "','" +   sPer + "', " +  sImp + ", '" +  Login.sUsrG + "', '" + sFD + "','" + sFA + "','" +  Star.sSucu + "', '" + Star.sNoCaj + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
         }
        
        /*Obtiene el último registro insertado en la base de datos*/
        String sID  = "";
        try
        {
            sQ = "SELECT id_id FROM viatspro ORDER BY id_id DESC LIMIT 1";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sID = rs.getString("id_id");                                                                       
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

        /*Dale formato de moneda al importe otra vez*/	
        double dCant    = Double.parseDouble(sImp);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sImp            = n.format(dCant);
        
        /*Agrega el registro en la tabla*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContF, sViat, sPag, sPer, sImp, sFD, sFA, sID};
        te.addRow(nu);
        
        /*Aumenta en uno el contador de filas*/
        ++iContF;
        
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se pierde el foco del teclado en el campo del importe*/
    private void jTImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTImp.setCaretPosition(0);
        
        /*Lee el importe*/
        String sImp    = jTImp.getText();

        /*Si el importe es cadena vacia entonces*/
        if(sImp.compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTImp.setText("$0.00");          
            return;
        }

        /*Si los caracteres introducidos no se puede convertir a double entonces*/
        try
        {
            double d = Double.parseDouble(sImp);
        }
        catch(NumberFormatException expnNumForm)
        {
            jTImp.setText("$0.00");
            return;
        }

        /*Dale formato de moneda al importe*/        
        double dCant    = Double.parseDouble(sImp);
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sImp            = n.format(dCant);

        /*Colocalo nuevamente en el campo ya con formato*/
        jTImp.setText(sImp);

    }//GEN-LAST:event_jTImpFocusLost

    
    /*Cuando se presiona el botòn de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si no a seleccionado un registro de la tabla el usuario entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un registro.", "Viáticos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }

        /*Preguntar al usuario si esta seguro de querer borrar el registro*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar el registro?", "Borrar", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
            return;
        
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
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Obtiene el ID de la fila con relación a la base de datos*/        
            String sID     = jTab.getValueAt(iSel[x], 7).toString();                

            /*Borra el registro de la base de datos*/
            try 
            {            
                sQ = "DELETE FROM viatspro WHERE id_id = " + sID;                    
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
            te.removeRow(iSel[x]);

            /*Resta uno al contador de filas*/
            --iContF;
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                    
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si el contador de filas quedo en 0 entonces colocalo en 1*/
        if(iContF==0)
            iContF  = 1;
                
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Registro(s) borrado(s) con éxito.", "Viáticos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del pago*/
    private void jTDescripPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripPagFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripPag.setSelectionStart(0);jTDescripPag.setSelectionEnd(jTDescripPag.getText().length());           
        
    }//GEN-LAST:event_jTDescripPagFocusGained

    
    /*Cuando se presiona una tecla en el campo de la descripción del pago*/
    private void jTDescripPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripPagKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripPagKeyPressed

    
    /*Cuando sucede una acción en el combobox del gasto*/
    private void jComGastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComGastActionPerformed
                
        /*Si el texto seleccionado es cadena vacia entonces*/
        if(jComGast.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca en la descripción del pago cadena vacia y regresa*/
            jTDescripPag.setText("");                        
            return;
        }
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene la descripción en base al código seleccionado*/
        try
        {
            sQ = "SELECT pagdescrip  FROM pags WHERE pag = '" + jComGast.getSelectedItem().toString() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalo en el control*/
            if(rs.next())
                jTDescripPag.setText(rs.getString("pagdescrip"));                                                                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }  
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComGastActionPerformed

    
    /*Cuando se presiona una tecla en el calendario de*/
    private void jCalDeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCalDeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCalDeKeyPressed

    
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se presiona el botón de guardar cambios*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
        
        /*Si no hay selecciòn regresa*/
        if(jTab.getSelectedRow()==-1)
            return;
        
        /*Si no a seleccionado una fila entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un registro.", "Viáticos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                        
            return;
        }
        
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar Cambios", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.NO_OPTION)
            return;                       
        
        //Declara variables locales
        String sViat;
        String sPag;
        String sPer;
        String sImp;
        String sFD;
        String sFA;
        String sID;
        
        /*Obtiene el ID de la fila seleccionada*/
        sID     =jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
        
        /*Obtiene los datos seleccionados del combobox*/
        sViat   = jComViat.getSelectedItem().toString();
        sPag    = jComGast.getSelectedItem().toString();
        sPer    = jComPers.getSelectedItem().toString();
        
        /*Obtiene el importe*/
        sImp    = jTImp.getText();
        
        /*Quitale al importe el formato de moneda*/
        sImp    = sImp.replace("$", "");
        sImp    = sImp.replace(",", "");

        /*Si se selecciono la cadena vacia en el viático entonces*/
        if(sViat.compareTo("")==0)
        {
            /*Avisa al usuario*/
            JOptionPane.showMessageDialog(null, "Selecciona un viático.", "Viático", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el combobox de los viáticos*/
            jComViat.grabFocus();
            
            /*Regresa por que no puede continuar*/
            return;
        }
        
        /*Si se selecciono la cadena vacia en la forma de pago entonces*/
        if(sPag.compareTo("")==0)
        {
            /*Avisa al usuario*/
            JOptionPane.showMessageDialog(null, "Selecciona una forma de pago.", "Pago", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el combobox de los pags*/
            jComGast.grabFocus();
            
            /*Regresa por que no puede continuar*/
            return;
        }
        
        /*Si se selecciono la cadena vacia en la persona*/
        if(sPer.compareTo("")==0)
        {
            /*Avisa al usuario*/
            JOptionPane.showMessageDialog(null, "Selecciona una persona.", "Persona", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el combobox de las pers*/
            jComPers.grabFocus();
            
            /*Regresa por que no puede continuar*/
            return;
        }
        
        /*Obtiene la fecha de*/
        Date fec                = jCalDe.getDate();
        SimpleDateFormat sd     = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        sFD                     = sd.format(fec); 
        
        /*Obtiene la fecha a*/
        fec                     = jCalA.getDate();        
        sFA                     = sd.format(fec); 
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 

        /*Actualiza en la base de datos el registro con los nuevos valores*/
        try 
        {            
            sQ = "UPDATE viatspro SET codviat = '" + sViat + "', pag = '" + sPag + "', nombper = '" + sPer + "', imp = " + sImp + ", estac = '" + Login.sUsrG + "', f_de = '" + sFD + "', f_a = '" + sFA + "', fmod = now(), sucu = '" + Star.sSucu + "', nocaj = '" + Star.sNoCaj + "' WHERE codpro = '" + sCodProGlo + "' AND id_id = " + sID;
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

        /*Dale formato de moneda al importe otra vez*/	
        double dCant    = Double.parseDouble(sImp);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sImp            = n.format(dCant);
        
        /*Actualiza los campos en la tabla*/
        jTab.setValueAt(iContF - 1,    jTab.getSelectedRow(), 0);
        jTab.setValueAt(sViat,         jTab.getSelectedRow(), 1);
        jTab.setValueAt(sPag,          jTab.getSelectedRow(), 2);
        jTab.setValueAt(sPer,          jTab.getSelectedRow(), 3);
        jTab.setValueAt(sImp,          jTab.getSelectedRow(), 4);
        jTab.setValueAt(sFD,           jTab.getSelectedRow(), 5);
        jTab.setValueAt(sFA,           jTab.getSelectedRow(), 6);
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Exito al guardar cambios.", "Guardar Cambios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
    }//GEN-LAST:event_jBGuarActionPerformed

    
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

    
    /*Cuando se pierde el foco del teclado en el combo de viáticos*/
    private void jComViatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComViatFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComViat.getSelectedItem().toString().compareTo("")!=0)
            jComViat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComViatFocusLost

    
    /*Cuando se pierde el foco del teclado en el combo de gasto*/
    private void jComGastFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComGastFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComGast.getSelectedItem().toString().compareTo("")!=0)
            jComGast.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComGastFocusLost

    
    /*Cuando se pierde el foco del teclado en el combo de personas*/
    private void jComPersFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComPersFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComPers.getSelectedItem().toString().compareTo("")!=0)
            jComPers.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComPersFocusLost

    
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
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
                
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTDescripPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripPagFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescripPag.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripPagFocusLost

    
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
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        
    }/*Fin de void vKeyPressEscalable(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private com.toedter.calendar.JCalendar jCalA;
    private com.toedter.calendar.JCalendar jCalDe;
    private javax.swing.JComboBox jComGast;
    private javax.swing.JComboBox jComPers;
    private javax.swing.JComboBox jComViat;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTDescripPag;
    private javax.swing.JTextField jTImp;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaEmpresa extends javax.swing.JFrame */
