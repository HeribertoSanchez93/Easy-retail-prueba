//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.awt.Dialog;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para controlar la búsqueda genérica*/
public class Busc extends javax.swing.JDialog
{
    /*Contiene el color original del botón*/
    private final java.awt.Color        colOri;
                
    /*Declara variables de instancia*/
    private static  Busc                obj = null; 
    private final int                   iEn;
    private final String                sBus;
    private final JTextField            jTFCamp;
    private final JTextField            jTFCamp2;
    private final JTextField            jTFCamp3;
    
    /*Contiene la dirección del control de área del otro formulario*/
    private final javax.swing.JTextArea jTAre;
    
    //Se manda el almacen
    private String                       sAlmaG;

    
    
    
    /*Constructor sin argumentos*/
    public Busc(JFrame jFram, String sBusc, int iE, JTextField jCamp ,JTextField jCamp2, JTextField jCamp3, String sTi, javax.swing.JTextArea jTA) 
    {   
        //Que sea modal
        this.setModal(true);
        
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Si El usuario global es nulo entonces, esto por que en la forma de login la primera aún no se define este valor y sale null*/
        String sEsta    = Login.sUsrG;
        if(Login.sUsrG==null)
            sEsta       = "";
        
        /*Si la fecha de login es nulo entonces, esto por que en la forma de login la primera aún no se define este valor y sale null*/
        String sFLog    = Login.sFLog;
        if(Login.sFLog==null)
            sFLog       = "";

        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Busqueda general, Usuario: <" + sEsta + "> " + sFLog);        
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBCarg);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que la tabla de partidas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Que las comlumnas de la tabla no se muevan*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Recibe la variable para saber de que bases de datos cargar la información*/
        iEn             = iE;
        
        /*Recibe lo que se va a búscar*/
        sBus            = sBusc;
        
        /*Recibe el manejador del campo del otro formulario*/
        jTFCamp         = jCamp;
        
        /*Recibe el manejador del campo 2 del otro formulario*/
        jTFCamp2        = jCamp2;
        
        /*Recibe el manejador del campo 3 del otro formulario*/
        jTFCamp3        = jCamp3;
        
        /*Recibe la dirección del control de área del otro formulario*/
        jTAre           = jTA;
        
        //Solo se altera si es por serie
        if(iE==34)
            sAlmaG      = sTi;
        
        /*Va a ser top most la ventana*/
        this.setAlwaysOnTop(true);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de registros*/        
        jTab.getColumnModel().getColumn(1).setPreferredWidth(250);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(250);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Función para saber de que base de datos cargar los datos*/
        vCargReg(sBus);                        
        
    }/*Fin de public Busc() */

    
    /*Función para saber de que base de datos cargar los datos*/
    private void vCargReg(String sBus)
    {
        /*Switch entre los diferentes conceps de busqueda*/
        switch(iEn)
        {
            /*Buscar en cliente*/
            case 1:
                
                /*Obtiene todos los registros coincidentes de los clientes y cargalos en la tabla*/        
                CargClients(sBus);                                    
                break;
                
            /*Buscar en productos*/
            case 2:

                /*Obtiene todos los registros coincidentes de los productos y cargalos en la tabla*/        
                final String sBusFi = sBus;
                (new Thread()
                {
                    @Override
                    public void run()
                    {
                        CargProds(sBusFi, "");                    
                    }
                    
                }).start();
                
                /*Corta aquí*/
                break;
                
            /*Buscar en provs*/
            case 3:

                /*Obtiene todos los registros coincidentes de los provs y cargalos en la tabla*/        
                CargProvs(sBus);                    
                break;
                
            /*Buscar en usuarios*/
            case 4:

                /*Obtiene todos los registros coincidentes de los usuarios y cargalos en la tabla*/        
                CargEstac(sBus);                    
                break;
                
            /*Buscar en empresas*/
            case 5:

                /*Obtiene todos los registros coincidentes de las empsy cargalos en la tabla*/        
                CargEmps(sBus);                    
                break;
                
            /*Buscar en kits*/
            case 6:

                /*Obtiene todos los registros coincidentes de los kits y cargalos en la tabla*/        
                CargKits(sBus);                    
                break;
                
            /*Buscar en conceptos*/
            case 7:

                /*Obtiene todos los registros coincidentes de los conceptos y cargalos en la tabla*/        
                CargConceps(sBus);                    
                break;
                
            /*Buscar en empresas de las bases de datos*/
            case 8:

                /*Obtiene todos los registros coincidentes de las empresas de la base de datos y cargalos en la tabla*/        
                CargEmpsBD(sBus);                    
                break;
                
            /*Buscar en tipos de pagos de las bases de datos*/
            case 9:

                /*Obtiene todos los registros coincidentes de los pagos en la base de datos y cargalos en la tabla*/        
                CargPagsBD(sBus);                    
                break;
                
            /*Buscar en moenedas de las bases de datos*/
            case 10:

                /*Obtiene todos los registros coincidentes de las monedas en la base de datos y cargalos en la tabla*/        
                CargMonBD(sBus);                    
                break;
                
            /*Buscar almacenes de las bases de datos*/
            case 11:

                /*Obtiene todos los registros coincidentes de los almacenes en la base de datos y cargalos en la tabla*/        
                CargAlma(sBus);
                break;
                                
            /*Buscar clasificaciones de las bases de datos*/
            case 12:

                /*Obtiene todos los registros coincidentes de las clasificaciones en la base de datos y cargalos en la tabla*/        
                CargClas(sBus);
                break;                
                
            /*Buscar clasificaciones de los proveedores en la base de datos*/
            case 13:

                /*Obtiene todos los registros coincidentes de las clasificaciones de los proveedores en la base de datos y cargalos en la tabla*/        
                CargClasProv(sBus);
                break;    
                
            /*Buscar clasificaciones del catálogo general en la base de datos*/
            case 14:

                /*Obtiene todos los registros coincidentes de el catálogo general en la base de datos y cargalos en la tabla*/        
                CargCatGral(sBus);
                break;    
                
            /*Buscar en anaqueles de la base de datos*/
            case 15:

                /*Obtiene todos los registros coincidentes de los anaqueles en la base de datos y cargalos en la tabla*/        
                CargAnaGral(sBus);
                break;    
           
            /*Buscar en lugares de la base de datos*/
            case 16:

                /*Obtiene todos los registros coincidentes de los lugares en la base de datos y cargalos en la tabla*/        
                CargLugGral(sBus);
                break;    
                
            /*Buscar en marcas de la base de datos*/
            case 17:

                /*Obtiene todos los registros coincidentes de las marcas en la base de datos y cargalos en la tabla*/        
                CargMarcGral(sBus);
                break;    
                
            /*Buscar en líneas de la base de datos*/
            case 18:

                /*Obtiene todos los registros coincidentes de las líneas en la base de datos y cargalos en la tabla*/        
                CargLinGral(sBus);
                break;    
                
            /*Buscar en clasificaciones extra de la base de datos*/
            case 19:

                /*Obtiene todos los registros coincidentes de las clasificaciones extras en la base de datos y cargalos en la tabla*/        
                CargClasifGral(sBus);
                break;                    
                
            /*Buscar en impuestos extra de la base de datos*/
            case 20:

                /*Obtiene todos los registros coincidentes de los impuestos en la base de datos y cargalos en la tabla*/        
                CargImpGral(sBus);
                break;                    
                
            /*Buscar en unidades de la base de datos*/
            case 21:

                /*Obtiene todos los registros coincidentes de las unidades en la base de datos y cargalos en la tabla*/        
                CargUniGral(sBus);
                break;                    
                
            /*Buscar en pesos de la base de datos*/
            case 22:

                /*Obtiene todos los registros coincidentes de los pesos en la base de datos y cargalos en la tabla*/        
                CargPesGral(sBus);
                break;                    
                
            /*Buscar en colores de la base de datos*/
            case 23:

                /*Obtiene todos los registros coincidentes de los colores en la base de datos y cargalos en la tabla*/        
                CargColoGral(sBus);
                break;                    
                
            /*Buscar en ubicaciones adicionales de la base de datos*/
            case 24:

                /*Obtiene todos los registros coincidentes de las ubicaciones adicionales la base de datos y cargalos en la tabla*/        
                CargUbiAd(sBus);
                break;                    
                  
            /*Buscar en medidas adicionales de la base de datos*/
            case 25:

                /*Obtiene todos los registros coincidentes de las medidas en la base de datos y cargalos en la tabla*/        
                CargMed(sBus);
                break;                    
                
            /*Buscar en kits de la base de datos*/
            case 26:

                /*Obtiene todos los registros coincidentes de los kits en la base de datos y cargalos en la tabla*/        
                CargKit(sBus);
                break;                    
                
            /*Buscar en fabricantes de la base de datos*/
            case 27:

                /*Obtiene todos los registros coincidentes de los fabricantes en la base de datos y cargalos en la tabla*/        
                CargFab(sBus);
                break;                    
                
            /*Buscar en vendedores*/
            case 28:

                /*Obtiene todos los registros coincidentes de los vendedores y cargalos en la tabla*/        
                CargVend(sBus);                    
                break;
          
            /*Buscar en marcas de la base de datos*/
            case 29:

                /*Obtiene todos los registros coincidentes de los Modelos en la base de datos y cargalos en la tabla*/        
                CargModGral(sBus);
                break;    

            /*Buscar en productos pero que no son kits*/
            case 30:

                /*Obtiene todos los registros coincidentes de los productos y cargalos en la tabla*/        
                final String sBusFi2 = sBus;
                (new Thread()
                {
                    @Override
                    public void run()
                    {
                        CargProds(sBusFi2, " AND prods.COMPUE = 0");                    
                    }
                    
                }).start();
                
                /*Corta aquí*/
                break;
                
            /*Buscar en tipos*/
            case 31:

                /*Obtiene todos los registros coincidentes de los tipos en la base de datos y cargalos en la tabla*/        
                CargTips(sBus);
                break;    
                
            /*Buscar en zonas*/
            case 32:

                /*Obtiene todos los registros coincidentes de las zonas en la base de datos y cargalos en la tabla*/        
                CargZon(sBus);
                break;    
                
            /*Buscar en giros*/
            case 33:

                /*Obtiene todos los registros coincidentes de los giros en la base de datos y cargalos en la tabla*/        
                CargGir(sBus);
                break;    
                
            /*Buscar en series*/
            case 34:

                /*Obtiene todos los registros coincidentes de las series en la base de datos y cargalos en la tabla*/        
                CargSerProd(sBus);
                break;    
                
            /*Buscar en series pero sin producto en específico*/
            case 35:

                /*Obtiene todos los registros coincidentes de las series en la base de datos y cargalos en la tabla*/        
                CargSerSinProd(sBus);
                break;    
                
            /*Buscar rubros de las bases de datos*/
            case 36:

                /*Obtiene todos los registros coincidentes de los rubros en la base de datos y cargalos en la tabla*/        
                CargRub(sBus);
                break;                
            
            /*Buscar garantías de las bases de datos*/
            case 37:

                /*Obtiene todos los registros coincidentes de las garantías en la base de datos y cargalos en la tabla*/        
                CargGara(sBus);
                break;                
                
            /*Buscar conceptos de notas de crédito de las bases de datos*/
            case 38:

                /*Obtiene todos los registros coincidentes de los conceptos de las notas de crédito en la base de datos y cargalos en la tabla*/        
                CargNot(sBus);
                break;                
                
            /*Buscar conceptos de notas de crédito de las bases de datos*/
            case 39:

                /*Obtiene todos los registros coincidentes de los conceptos de activo fijo de la base de datos y cargalos en la tabla*/        
                CargConcepAct(sBus);
                break;                
                
            /*Buscar conceptos de notas de crédito de las bases de datos*/
            case 40:

                /*Obtiene todos los registros coincidentes de los tipos de activos fijos de la base de datos y cargalos en la tabla*/        
                CargTipAct(sBus);
                break;                
                
            /*Buscar tallas en la bases de datos*/
            case 41:

                /*Obtiene todos los registros coincidentes de las tallas en la base de datos y cargalos en la tabla*/        
                CargTall(sBus);
                break;                
                
            /*Buscar almacenes sin el de activo fijo*/
            case 42:

                /*Obtiene todos los registros coincidentes de los almacenes pero sin activo fijo*/        
                vCargAlmaActiv(sBus);
                break;                
                
            //Búscar por tipos de pagos
            case 43:

                //Obtiene todos los registros coincidentes de la base de datos para los tipos de pagos
                vCargTipPag(sBus);
                break;                
                
            //Búscar por conceptos de pago
            case 44:

                //Obtiene todos los registros coincidentes de la base de datos para los conceptos de pagos
                vCargConcPag(sBus);
                break;                
                
        }/*Fin de switch(iEn)*/        
        
    }/*Fin de private void vCargReg(String sBus, int iEn)*/
        
        
    /*Metodo para que el formulario no se abra dos veces*/
    public static Busc getObj(JFrame jFram, String sBus, int iEn, JTextField jCamp, JTextField jCamp2, JTextField jCamp3, String sTi,  javax.swing.JTextArea jTA)
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new Busc(jFram, sBus, iEn, jCamp, jCamp2, jCamp3, sTi, jTA);
        
        /*Devuelve el resultado*/
        return obj;
        
    }/*Fin de public static Buscar getObj()*/
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBCarg = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jLAyu = new javax.swing.JLabel();

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

        jBCarg.setBackground(new java.awt.Color(255, 255, 255));
        jBCarg.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCarg.setForeground(new java.awt.Color(0, 102, 0));
        jBCarg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/carg.png"))); // NOI18N
        jBCarg.setText("Aceptar");
        jBCarg.setToolTipText("Aceptar (ENTER)");
        jBCarg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBCarg.setNextFocusableComponent(jBSal);
        jBCarg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCargMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCargMouseExited(evt);
            }
        });
        jBCarg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCargActionPerformed(evt);
            }
        });
        jBCarg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCargKeyPressed(evt);
            }
        });
        jP1.add(jBCarg, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 140, -1));

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 140, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod.", "Descripción/Valor", "Otros"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBCarg);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 240));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 220, 120, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todos los registros y cargalos en la tabla*/
    private void CargClients(String sBus)
    {        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;                       
        
        /*Trae todos los registros coincidentes de la base de datos de la empresa*/
        try
        {
            sQ = "SELECT codemp, nom, ser FROM emps WHERE (CASE WHEN '" + sBus + "' = '' THEN codemp = codemp ELSE CONCAT_WS('', ser, codemp) LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN nom = nom ELSE nom LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("ser") + rs.getString("codemp"), rs.getString("nom"), rs.getString("ser")};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargClients()*/   
    
    
    /*Obtiene todos los registros y cargalos en la tabla*/
    private void CargClas(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de clasificaciones*/
        try
        {
            sQ = "SELECT cod, descrip FROM clasemp WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargClas()*/   
    
    
    /*Obtiene todos los registros de los rubros y cargalos en la tabla*/
    private void CargRub(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de clasificaciones*/
        try
        {
            sQ = "SELECT cod, descrip FROM rubr WHERE (CASE WHEN '" + sBus + "' = '' THEN cod  = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargRub()*/   
    
    
    /*Obtiene todos los registros de las garantías y cargalos en la tabla*/
    private void CargGara(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT gara, descrip FROM garan WHERE (CASE WHEN '" + sBus + "' = '' THEN gara = gara ELSE gara LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("gara"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
              
    }/*Fin de private void CargGara()*/   
    
    
    /*Obtiene todos los registros de los conceptos de las notas de crédito y cargalos en la tabla*/
    private void CargNot(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT concep, descrip FROM concepnot WHERE (CASE WHEN '" + sBus + "' = '' THEN concep = concep ELSE concep LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("concep"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargNot()*/   
    
    
    /*Obtiene todos los registros de los conceptos de activo fijo de la base de datos y cargalos en la tabla*/
    private void CargConcepAct(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;      
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT concep, descrip FROM actfijcat WHERE (CASE WHEN '" + sBus + "' = '' THEN concep = concep ELSE concep LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("concep"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargConcepAct()*/   
    
    
    /*Obtiene todos los registros de los conceptos de tipo de activo fijo de la base de datos y cargalos en la tabla*/
    private void CargTipAct(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT tip, descrip FROM tipactfij WHERE (CASE WHEN '" + sBus + "' = '' THEN tip = tip ELSE tip LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("tip"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargTipAct()*/   
    
    
    /*Obtiene todos los registros de las tallas de la base de datos y cargalas en la tabla*/
    private void CargTall(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");
                
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT cod, descrip FROM tall WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargTall()*/   
    
    
    //Obtiene todos los registros de los tipos de pagos de la base de  datos y cargalos en la tabla
    private void vCargTipPag(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT cod, descrip FROM pags WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargTipPag()*/   
    
    
    //Obtiene todos los registros de los conceptos de pagos de la base de  datos y cargalos en la tabla
    private void vCargConcPag(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT concep, descrip FROM conceppag WHERE (CASE WHEN '" + sBus + "' = '' THEN concep = concep ELSE concep LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("concep"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargConcPag()*/   
    
    
    /*Obtiene todos los registros y cargalos en la tabla*/
    private void CargClasProv(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de clasificaciones de provs*/
        try
        {
            sQ = "SELECT cod, descrip FROM clasprov WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {               
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip")};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargClasProv()*/ 
    
    
    /*Obtiene todos los registros y cargalos en la tabla*/
    private void CargCatGral(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos del catálogo general*/
        try
        {
            sQ = "SELECT cod, descrip FROM grals WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargCatGral()*/ 
    
    
    /*Obtiene todos los registros de los anaqueles y cargalos en la tabla*/
    private void CargAnaGral(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de los anaqueles*/
        try
        {
            sQ = "SELECT cod, descrip FROM anaqs WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargAnaqGral()*/ 
    
        
    /*Obtiene todos los registros de los lugs y cargalos en la tabla*/
    private void CargLugGral(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de los anaqueles*/
        try
        {
            sQ = "SELECT cod, descrip FROM lugs WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargLugGral()*/ 
    
    
    /*Obtiene todos los registros de las marcs y cargalas en la tabla*/
    private void CargMarcGral(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de las marcs*/
        try
        {
            sQ = "SELECT cod, descrip FROM marcs WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargMarcGral()*/ 
    
    
    /*Obtiene todos los registros de los Modelos y cargalas en la tabla*/
    private void CargModGral(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");
                
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de los Modelos*/
        try
        {
            sQ = "SELECT cod, descrip FROM model WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargModGral()*/ 
    
    
    /*Obtiene todos los registros de las líneas y cargalas en la tabla*/
    private void CargLinGral(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de las marcs*/
        try
        {
            sQ = "SELECT cod, descrip FROM lins WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargLinGral()*/ 
    
    
    /*Obtiene todos los registros de las clasificaciones y cargalas en la tabla*/
    private void CargClasifGral(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de las clasificaciones extras*/
        try
        {
            sQ = "SELECT cod, descrip FROM clasprod WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargClasifGral()*/ 
    
    
    /*Obtiene todos los registros de los impues y cargalos en la tabla*/
    private void CargImpGral(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de las clasificaciones extras*/
        try
        {
            sQ = "SELECT codimpue, impueval FROM impues WHERE (CASE WHEN '" + sBus + "' = '' THEN codimpue = codimpue ELSE codimpue LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN impueval = impueval ELSE impueval LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("codimpue"), rs.getString("impueval"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargImpGral()*/ 
    
    
    /*Obtiene todas las unids y cargalas en la tabla*/
    private void CargUniGral(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de las unids*/
        try
        {
            sQ = "SELECT cod, descrip FROM unids WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargUniGral()*/ 
    
    
    /*Obtiene todos los pes y cargalos en la tabla*/
    private void CargPesGral(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de los pes*/
        try
        {
            sQ = "SELECT cod, descrip FROM pes WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargPesGral()*/ 
    
    
    /*Obtiene todos los colos y cargalos en la tabla*/
    private void CargColoGral(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de los colos*/
        try
        {
            sQ = "SELECT cod, descrip FROM colos WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargColoGral()*/ 
    
    
    /*Obtiene todos los tipos y cargalos en la tabla*/
    private void CargTips(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de los colos*/
        try
        {
            sQ = "SELECT cod, descrip FROM tips WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargTips()*/ 
    
    
    /*Obtiene todas las zonas y cargalas en la tabla*/
    private void CargZon(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT cod, descrip FROM zona WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
                          
    }/*Fin de private void CargZon()*/ 
    
    
    /*Obtiene todos los giros y cargalos en la tabla*/
    private void CargGir(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT gir, descrip FROM giro WHERE 1=1 AND (CASE WHEN '" + sBus + "' = '' THEN gir = gir ELSE gir LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("gir"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
                    
    }/*Fin de private void CargGir()*/ 
    
    
    /*Obtiene todas las series por producto y cargalos en la tabla*/
    private void CargSerProd(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            //Crea la consulta
            if(sAlmaG.compareTo("")==0)
                sQ = "SELECT ser, comen, exist, alma FROM serieprod WHERE prod = '" + sBus + "' AND exist > 0 AND alma <> '" + Star.strAlmaActFij + "'"; 
            else
                sQ = "SELECT ser, comen, exist, alma FROM serieprod WHERE prod = '" + sBus + "' AND exist > 0 AND alma <> '" + Star.strAlmaActFij + "' AND alma = '" + sAlmaG.trim() + "'"; 
            
            //Ejecuta la consulta
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("ser"), rs.getString("comen"), "Existencia:" + rs.getString("exist") + "  Almacén = " + rs.getString("alma")};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargSerProd()*/ 
    
    
    /*Obtiene todas las series sin producto y cargalos en la tabla*/
    private void CargSerSinProd(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT ser, comen, exist, alma FROM serieprod WHERE ser LIKE('%" + sBus + "%')";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("ser"), rs.getString("comen"), "Existencia:" + rs.getString("exist") + "  Almacén = " + rs.getString("alma")};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargSerSinProd()*/ 
    
    
    /*Obtiene todas las ubicaciones adicionales y cargalos en la tabla*/
    private void CargUbiAd(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT cod, descrip FROM ubiad WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargUbiAd()*/ 
    
    
    /*Obtiene todas las meds adicionales y cargalas en la tabla*/
    private void CargMed(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT cod, descrip FROM meds WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargMed()*/ 
    
    
    /*Obtiene todas los kits y cargalos en la tabla*/
    private void CargKit(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos*/
        try
        {
            sQ = "SELECT kits.CODKIT, prods.DESCRIP FROM kits LEFT OUTER JOIN prods ON prods.PROD = kits.CODKIT WHERE kits.CODKIT <> '-' AND (CASE WHEN '" + sBus + "' = '' THEN kits.CODKIT = kits.CODKIT ELSE kits.CODKIT LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN prods.DESCRIP = prods.DESCRIP ELSE prods.DESCRIP LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("kits.CODKIT"), rs.getString("prods.DESCRIP"), ""};
                te.addRow(nu);                                
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
                    
    }/*Fin de private void CargKit()*/ 
    
    
    /*Obtiene todos los registros y cargalos en la tabla*/
    private synchronized void CargProds(String sBus, String sCond)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de productos*/
        try
        {
            sQ = "SELECT prod, descrip, exist, unid FROM prods WHERE esvta = 1 " + sCond +  " AND (CASE WHEN '" + sBus + "' = '' THEN prod = prod ELSE prod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END) ORDER BY prod";                                    
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos en la tabla*/
            while(rs.next())
            {   
                //Obtiene la unidad
                String sUnid    = rs.getString("unid");
                
                //Obtiene la cantidad visual equivalente correcta
                String sExist   = Star.sCantVisuaGKT(sUnid, rs.getString("exist"));
                
                //Agrega la fila
                Object nu[]     = {rs.getString("prod"), rs.getString("descrip"), "Existencia:" + sExist + " " + sUnid};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargProds()*/       
    
    
    /*Obtiene todos los registros de los kits y cargalos en la tabla*/
    private void CargKits(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de productos*/
        try
        {
            sQ = "SELECT prod, descrip FROM prods WHERE codimpue <> 1 AND (CASE WHEN '" + sBus + "' = '' THEN prod = prod ELSE prod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END) ORDER BY prod";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]     = {rs.getString("prod"), rs.getString("descrip")};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargKits()*/       
    
    
    /*Obtiene todos los registros de los conceps y cargalos en la tabla*/
    private void CargConceps(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de conceps*/
        try
        {
            sQ = "SELECT concep, descrip FROM conceps WHERE (CASE WHEN '" + sBus + "' = '' THEN concep = concep ELSE concep LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBus + "%') END)";                                                
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]         = {rs.getString("concep"), rs.getString("descrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargConceps()*/       
    
    
    /*Obtiene todos los registros de almas y cargalos en la tabla*/
    private void CargAlma(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos provs*/
        try
        {
            sQ = "SELECT alma, almadescrip FROM almas WHERE (CASE WHEN '" + sBus + "' = '' THEN alma = alma ELSE alma LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN almadescrip = almadescrip ELSE almadescrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                               
                Object nu[]     = {rs.getString("alma"), rs.getString("almadescrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargAlma()*/       
    
    
    /*Obtiene todos los registros de almacenes pero sin activo fijo y cargalos en la tabla*/
    private void vCargAlmaActiv(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos provs*/
        try
        {
            sQ = "SELECT alma, almadescrip FROM almas WHERE alma <> '" + Star.strAlmaActFij + "' AND (CASE WHEN '" + sBus + "' = '' THEN alma = alma ELSE alma LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN almadescrip = almadescrip ELSE almadescrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                               
                Object nu[]     = {rs.getString("alma"), rs.getString("almadescrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void vCargAlmaActiv()*/       
    
    
    /*Obtiene todos los registros y cargalos en la tabla*/
    private void CargProvs(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos provs*/
        try
        {
            sQ = "SELECT ser, prov, nom FROM provs WHERE (CASE WHEN '" + sBus + "' = '' THEN prov = prov ELSE CONCAT_WS('', ser,prov) LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN nom = nom ELSE nom LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[]         = {rs.getString("ser") +rs.getString("prov"), rs.getString("nom"), "Serie:" +rs.getString("ser")};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargProvs()*/       
    
    
    /*Obtiene todos los registros y cargalos en la tabla*/
    private void CargEstac(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de usuarios*/
        try
        {
            sQ = "SELECT id_id, estac, falt FROM estacs WHERE (CASE WHEN '" + sBus + "' = '' THEN estac = estac ELSE estac LIKE('%" + sBus + "%') END )";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[] = {rs.getString("id_id"), rs.getString("estac"), "Fecha Alta:" + rs.getString("falt")};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargEstac()*/       
    
    
    /*Obtiene todos los registros y cargalos en la tabla*/
    private void CargVend(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;       
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de usuarios*/
        try
        {
            sQ = "SELECT nom, estac, falt FROM estacs WHERE vend = 1 AND (CASE WHEN '" + sBus + "' = '' THEN estac = estac ELSE estac LIKE('%" + sBus + "%') END )";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos  agregalos en la tabla*/
            while(rs.next())
            {
                Object nu[] = {rs.getString("estac"), rs.getString("nom"), "Fecha Alta:" + rs.getString("falt")};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargVend()*/       
    
    
    /*Obtiene todos los registros y cargalos en la tabla*/
    private void CargEmps(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de emps*/
        try
        {
            sQ = "SELECT ser, codemp, nom FROM emps WHERE (CASE WHEN '" + sBus + "' = '' THEN codemp = codemp ELSE CONCAT_WS('', ser, codemp) LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN nom = nom ELSE nom LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[] = {rs.getString("ser") + rs.getString("codemp"), rs.getString("nom"), "Serie:" + rs.getString("ser")};
                te.addRow(nu);                               
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
                    
    }/*Fin de private void CargEmps()*/       
    
    
    /*Obtiene todos los registros y cargalos en la tabla*/
    private void CargFab(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de los fabs*/
        try
        {
            sQ = "SELECT cod, descrip FROM fabs WHERE (CASE WHEN '" + sBus + "' = '' THEN cod = cod ELSE cod LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN descrip= descrip ELSE descrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[] = {rs.getString("cod"), rs.getString("descrip"), ""};
                te.addRow(nu);                               
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
        
    }/*Fin de private void CargFab()*/       
    
    
    /*Obtiene todos los registros y cargalos en la tabla*/
    private void CargEmpsBD(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
     
        /*Trae todos los registros coincidentes de la base de datos de emps*/
        try
        {
            sQ = "SELECT codemp, nom, falt FROM basdats WHERE (CASE WHEN '" + sBus + "' = '' THEN codemp = codemp ELSE codemp LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN nom = nom ELSE nom LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while(rs.next())
            {                
                Object nu[]     = {rs.getString("codemp"), rs.getString("nom"), "Fecha Creación:" + rs.getString("falt")};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargEmpsBD()*/       
    
    
    /*Obtiene todos los pags y cargalos en la tabla*/
    private void CargPagsBD(String sBus)
    {        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de pags*/
        try
        {
            sQ = "SELECT pag, pagdescrip FROM pags WHERE (CASE WHEN '" + sBus + "' = '' THEN pag = pag ELSE pag LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN pagdescrip = pagdescrip ELSE pagdescrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while (rs.next())
            {                
                Object nu[]     = {rs.getString("pag"), rs.getString("pagdescrip")};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargPagsBD()*/       
   
   
    /*Obtiene todos las mons y cargalas en la tabla*/
    private void CargMonBD(String sBus)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBus                    = sBus.replace(" ", "%");
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de mons*/
        try
        {
            sQ = "SELECT mon, mondescrip FROM mons WHERE (CASE WHEN '" + sBus + "' = '' THEN mon = mon ELSE mon LIKE('%" + sBus + "%') END OR CASE WHEN '" + sBus + "' = '' THEN mondescrip = mondescrip ELSE mondescrip LIKE('%" + sBus + "%') END)";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos agregalos en la tabla*/
            while (rs.next())
            {                
                Object nu[]     = {rs.getString("mon"), rs.getString("mondescrip"), ""};
                te.addRow(nu);                                
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
        
    }/*Fin de private void CargMonBD()*/       
    
    
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
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Sal del formulario*/
        this.dispose();
        obj = null;      
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una  tecla en la tabla de registros*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed
       
    
    /*Cuando se presiona una tecla en el botón de cargar*/
    private void jBCargKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCargKeyPressed

    
    /*Cuando se presiona el botón de cargar*/
    private void jBCargActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargActionPerformed

        /*Si no hay selección en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Llama al recolector de basura*/
            System.gc();
        
            /*Cierra el formulario*/
            this.dispose();
            obj = null;
            
            /*Si es de poner en un segundo campo entonces pon cadena vacia por que no tiene coincidencias*/
            if(iEn==2 && jTFCamp2 != null)
                jTFCamp2.setText("");
                        
            /*Si es de poner en un tercer campo entonces válida si poner cadena vacia o no*/
            if(iEn==5 && jTFCamp3!=null)
                jTFCamp3.setText("");
            
            /*Regresa*/
            return;                        
        }

        /*Si se tiene que poner la descripción en un segundo campo entonces colocalo*/
        if( iEn == 2 || iEn == 9 || iEn == 30)
        {            
            /*Si el campo no es null entonces cola la descripción ahi, en la parte de los prods es donde se manada a llamar con nulo por que es un poco distinto el tratamiento*/
            if(jTFCamp2!=null)                                     
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());                                                               
        }                    
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Sal del formulario*/
        this.dispose();
        obj                 = null;
        
        /*Si es de emps entonces*/
        if(iEn==1)
        {
            /*Coloca los campos en sus lugs*/
            if(jTFCamp!=null)
                jTFCamp.setText (jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
            if(jTFCamp2!=null)
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());
            if(jTFCamp3!=null)
                jTFCamp3.setText(jTab.getValueAt(jTab.getSelectedRow(), 2).toString().replace("Serie:", ""));
        }                       
        /*Else if es por emps entonces*/
        else if(iEn==2)
            jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        /*Si es de proveedores entonces*/
        else if(iEn==3)
        {
            /*Coloca los campos en sus lugs*/
            if(jTFCamp!=null)
                jTFCamp.setText (jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
            if(jTFCamp2!=null)
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());
            if(jTFCamp3!=null)
                jTFCamp3.setText(jTab.getValueAt(jTab.getSelectedRow(), 2).toString().replace("Serie:", ""));
        }
        /*Else if es 4 entonces pon la descripción, por ejemplo en los usuarios el segundo es el de El usuario*/
        else if(iEn==4)
            jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());        
        /*Si es de emps entonces*/
        else if(iEn==5)
        {
            /*Coloca los campos en sus lugs*/
            if(jTFCamp!=null)
                jTFCamp.setText (jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
            if(jTFCamp2!=null)
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());
            if(jTFCamp3!=null)
                jTFCamp3.setText(jTab.getValueAt(jTab.getSelectedRow(), 2).toString().replace("Serie:", ""));
        }
        /*Else if es por conceps*/
        else if(iEn==7)
        {            
            /*Coloca el código y la descripción del concepto*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
            if(jTFCamp2!=null)
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 01).toString());
        }
        //Si es por empresas del sistema entonces
        else if(iEn==8)
        {
            /*Coloca los campos en sus lugs*/
            if(jTFCamp!=null)
                jTFCamp.setText (jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
            if(jTFCamp2!=null)
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());            
        }
        /*Else if es por tipo de cambio en USD entonces muestrae el tipo de cambio actual para que lo pueda actualizar*/
        else if(iEn==10 && jTFCamp2==null)
        {
            /*Coloca el código de la moneda en su lugar*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
            
            /*Determina si tiene que validar tipo de cambio*/
            vTipCam();
        }
        /*Else if es moneda pero no para establecer el tipo de cambio entonces*/
        else if(iEn==10 && jTFCamp2!=null)
        {
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por almacenes*/
        else if(iEn==11)
        {            
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por almas*/
        else if(iEn==12)
        {            
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
            
            /*Coloca la código en el campo 2*/
            if(jTFCamp2!=null)
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());
        }
        /*Else if es por clasificaciones de proveedores*/
        else if(iEn==13)
        {            
            /*Coloca el código y la descripción del concepto*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
            if(jTFCamp2!=null)
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 01).toString());
        }
        /*Else if es del catálogo general entonces*/
        else if(iEn==14)
        {            
            /*Coloca la descripción en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());
            
            /*Coloca la descripción en el campo de textarea*/
            if(jTAre!=null)
                jTAre.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());
        }
        /*Else if es por anaqueles*/
        else if(iEn==15)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por anaqueles*/
        else if(iEn==16)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por marcas entonces*/
        else if(iEn==17)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
            
            /*Coloca la código en el campo 2*/
            if(jTFCamp2!=null)
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());
        }
        /*Else if es por líneas entonces*/
        else if(iEn==18)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por clasificaciones extras de prods entonces*/
        else if(iEn==19)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por impues entonces*/
        else if(iEn==20)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por unidaes entonces*/
        else if(iEn==21)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por peso entonces*/
        else if(iEn==22)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por color entonces*/
        else if(iEn==23)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por color entonces*/
        else if(iEn==24)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por color entonces*/
        else if(iEn==25)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por kits entonces*/
        else if(iEn==26)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por kits entonces*/
        else if(iEn==27)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por kits entonces*/
        else if(iEn==28)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por Modelos entonces*/
        else if(iEn==29)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
            
            /*Coloca otra columna*/
            if(jTFCamp2!=null)
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());
        }
        /*Else if es por productos con filtro que no sean kits entonces*/
        else if(iEn==30)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());                        
        }
        /*Else if es por tipos entonces*/
        else if(iEn==31)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por zonas entonces*/
        else if(iEn==32)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por giros entonces*/
        else if(iEn==33)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        /*Else if es por serie de producto entonces*/
        else if(iEn==34)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());                        
            
            /*Coloca la código en el campo*/
            if(jTFCamp2!=null)
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());                        
        }
        /*Else if es por serie pero sin producto entonces*/
        else if(iEn==35)
        {            
            /*Coloca la código en el campo*/
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());                                                
        }
        /*Else if es por rubros*/
        else if(iEn==36)
        {            
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
            
            /*Coloca la código en el campo 2*/
            if(jTFCamp2!=null)
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());
        }
        /*Else if es por rubros*/
        else if(iEn==37)
        {            
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
            
            /*Coloca la código en el campo 2*/
            if(jTFCamp2!=null)
                jTFCamp2.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());
        }
        /*Else if es por conceptos de notas de crédito entonces*/
        else if(iEn==38)
        {            
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());                        
        }
        /*Else if es por conceptos de activo fijo entonces*/
        else if(iEn==39)
        {            
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());                        
        }
        /*Else if es por almas*/
        else if(iEn==42)
        {            
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        //Else if es por tipos de pagos
        else if(iEn==43)
        {            
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        //Else if es por conceptos de pagos
        else if(iEn==44)
        {            
            if(jTFCamp!=null)
                jTFCamp.setText(jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        }
        
        /*Si los campos no son nulos entonces coloca el caret al principio del control*/
        if(jTFCamp!=null)
            jTFCamp.setCaretPosition(0);
        if(jTFCamp2!=null)
            jTFCamp2.setCaretPosition(0);
        if(jTFCamp3!=null)
            jTFCamp3.setCaretPosition(0);
        
    }//GEN-LAST:event_jBCargActionPerformed

    
    /*Muestra el tipo de cambio y actualizalo con un dialogo de entrada*/
    void vTipCam()
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

        /*Comprueba si la moneda seleccionada es la nacional*/
        try
        {
            sQ = "SELECT mon FROM mons WHERE mon = '" + jTab.getValueAt(jTab.getSelectedRow(), 0).toString().trim() + "' AND mn = 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces es la moneda nacional*/
            if(rs.next())
            {
                //Cierra la base de datos y regresa
                Star.iCierrBas(con);
                return;                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                        
        }
        
        /*Obtiene el tipo de cambio actual de la moneda*/
        String sTipCam  = "";
        try
        {
            sQ = "SELECT val FROM mons WHERE mon = '" + jTab.getValueAt(jTab.getSelectedRow(), 0).toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sTipCam      = rs.getString("val");                                                   
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                        
        }
        
        //Obtiene la configuración de si se puede modificar el tipo de cambio en el sistema o no
        int iRes    = Star.iCambTipCam(con);
        
        //Si hubo error entonces regesa
        if(iRes==-1)
            return;
        
        //Si esta deshabilitado que cambien el tipo de cambio  entonces deshabilita el botón de tipo de cambio
        if(iRes==1)
        {
            /*Bucle mientras no se inserte una cantidad válida para la moneda*/
            boolean bSi = false;
            String sResul = null;
            do
            {
                /*Muestra el tipo de cambio para que si lo desean lo actualicen*/
                sResul = JOptionPane.showInputDialog(null, "Tipo de Cambio " + sTipCam + ":", jTab.getValueAt(jTab.getSelectedRow(), 0).toString() + " Tipo Cambio", 1);

                /*Si es nulo osea que cancelo entonces*/
                if(sResul==null)
                {
                    //Cierra la base de datos y regresa
                    Star.iCierrBas(con);
                    return;
                }

                /*Si es cadena vacia el resultado entonces regresa*/
                if(sResul.compareTo("")==0)
                {
                    //Cierra la base de datos y regresa
                    Star.iCierrBas(con);                        
                    return;
                }

                /*Comprueba si es un número válido*/
                try
                {
                    /*Intenta convertilo a double*/
                    double d = Double.parseDouble(sResul);

                    /*Conviertelo a su valor absoluto*/
                    sResul = Double.toString(Math.abs(d));

                    /*Pon la bandera para salir del bucle*/
                    bSi = true;
                }
                catch(NumberFormatException expnNumForm)
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Ingresa una cantidad válida.", "USD Tipo cambio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                 
                }

            }while(!bSi || sResul==null);

            //Inserta el log del tipo de cambio de la moneda
            if(Star.iRegTipsCambLog(con, jTab.getValueAt(jTab.getSelectedRow(), 0).toString().trim(), sResul)==-1)
                return;

            //Actualiza el tipo de cambio de la moneda
            if(Star.iActTipCamMon(con, jTab.getValueAt(jTab.getSelectedRow(), 0).toString().trim(), sResul)==-1)
                return;
            
        }//Fin de if(iRes==1)                    
        //Else no se puede cambiar el tipo de cambio por la configuración entonces
        else                    
        {
            /*Dale formato de moneda al tipo de cambio*/            
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
            double dCant    = Double.parseDouble(sTipCam);                
            sTipCam         = n.format(dCant);
            
            //Mensajea que no es posible cambiarlo
            JOptionPane.showMessageDialog(null, "No se puede modificar el tipo de cambio para la moneda: " + jTab.getValueAt(jTab.getSelectedRow(), 0).toString().trim() + " con T.C: " + sTipCam, "Tipo de cambio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de void vTipCam()*/
    
            
    /*Cuando se da clic con el mouse en la tabla de registros*/
    private void jTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabMouseClicked
        
        /*Si se hiso doble clic entonces presiona el botón de cargar*/
        if(evt.getClickCount() == 2) 
            jBCarg.doClick();
        
    }//GEN-LAST:event_jTabMouseClicked

    
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

    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

            
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
    private void jBCargMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCarg.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCargMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCargMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCarg.setBackground(colOri);
        
    }//GEN-LAST:event_jBCargMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited
    
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if el botón de enter se presiona entonces*/
        else if(evt.getKeyCode() == KeyEvent.VK_ENTER) 
        {
            /*Si la tabla tiene el foco del teclado en entonces presona el botón de cargar*/
            if(jTab.hasFocus())
                jBCarg.doClick();
        }
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCarg;
    private javax.swing.JButton jBSal;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
