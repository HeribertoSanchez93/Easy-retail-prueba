//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;




/*Clase para ver una venta en otra vista*/
public class VVta extends javax.swing.JFrame
{        
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Declara variables originales*/    
    private String              sListOri;
    private String              sProdOri;
    private String              sUnidOri;
    private String              sAlmaOri;
    private String              sDescripOri;
    private String              sCantOri;
    private String              sPreOri;
    private String              sDescOri;
    private String              sImpueOri;
    private String              sMonOri;
    private String              sImpoOri;
    private String              sEsKitOri;
    private String              sTallOri;
    private String              sColoOri;
    private String              sLotOri;
    private String              sPedimenOri;
    private String              sCaduOri;
    private String              sBackOri;
    
    /*Contador para modificar tabla*/
    private int                 iContCellEd;
    
    
    
    
    /*Consructor con argumento*/
    public VVta(String sVta) 
    {                                                        
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el tamaño de las columnas de la tabla*/
        jTab.getColumnModel().getColumn(1).setPreferredWidth(160);
        jTab.getColumnModel().getColumn(6).setPreferredWidth(500);
                
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Activa en el JTextArea que se usen normamente las teclas de tabulador*/
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Establece el título de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Ver venta, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Pon el foco del teclado en el código de la empresa*/
        jTCodEmp.grabFocus();                
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene el encabezado de la venta*/        
        try
        {
            sQ = "SELECT vtas.CTA, vtas.SUBTOT, vtas.IMPUE, vtas.TOT, vtas.ESTAD, vtas.OBSERV, vtas.FALT, vtas.METPAG, vtas.NOSER, vtas.TIPDOC, emps.NOEXT, emps.NOINT, emps.RFC, emps.CIU, emps.COL, emps.TEL, emps.PAI, emps.CP, emps.CALLE, vtas.CODEMP, emps.NOM, vtas.CODEMP FROM vtas LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP) = vtas.CODEMP WHERE vta = " + sVta;
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca los resutados en los controles*/
                jTCodEmp.setText        (rs.getString("codemp"));
                jTNomEmp.setText        (rs.getString("nom"));
                jTCall.setText          (rs.getString("calle"));
                jTCol.setText           (rs.getString("col"));
                jTTel.setText           (rs.getString("tel"));
                jTPai.setText           (rs.getString("pai"));
                jTCP.setText            (rs.getString("cp"));
                jTNoInt.setText         (rs.getString("noint"));
                jTNoExt.setText         (rs.getString("noext"));
                jTRFC.setText           (rs.getString("rfc"));
                jTCiu.setText           (rs.getString("ciu"));
                jTEstad.setText         (rs.getString("estad"));
                jTTipDoc.setText        (rs.getString("tipdoc"));
                jTFec.setText           (rs.getString("falt"));
                jTMetPag.setText        (rs.getString("metpag"));
                jTCta.setText           (rs.getString("cta"));
                jTNoSer.setText         (rs.getString("noser"));
                jTAObserv.setText       (rs.getString("observ"));
            
                /*Coloca en el label el tipo de documento que es*/
                if(rs.getString("tipdoc").compareTo("FAC")==0)
                    jLNot.setText("FACTURA");
                else if(rs.getString("tipdoc").compareTo("TIK")==0)
                    jLNot.setText("TICKET");
                else if(rs.getString("tipdoc").compareTo("REM")==0)
                    jLNot.setText("REMISIÓN");
                else if(rs.getString("tipdoc").compareTo("NOTC")==0)
                    jLNot.setText("NOTA DE CRÉDITO");
                
                /*Coloca el cursor de la fecha al principio por que sale algo grande*/
                jTFec.setCaretPosition  (0);
                
                /*Obtiene los totales*/
                String sSubTot          = rs.getString("subtot");
                String sImpue           = rs.getString("impue");
                String sTot             = rs.getString("tot");
                
                /*Dale formato de moneda a los totales*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sImpue);                
                sImpue          = n.format(dCant);
                dCant           = Double.parseDouble(sSubTot);                
                sSubTot         = n.format(dCant);
                dCant           = Double.parseDouble(sTot);                
                sTot            = n.format(dCant);
                
                /*Coloca los totales en los controles*/
                jTSubTot.setText    (sSubTot);
                jTImp.setText       (sImpue);
                jTTot.setText       (sTot);
                
                /*Si el estado es devolución, devolución parcial o cancelación entonces el color de la letra del campo será roja*/
                if(rs.getString("vtas.ESTAD").compareTo("DEV")==0 || rs.getString("vtas.ESTAD").compareTo("CA")==0 || rs.getString("vtas.ESTAD").compareTo("DEVP")==0)
                    jTEstad.setForeground(Color.RED);
                else
                    jTEstad.setForeground(Color.BLUE);
            }                               
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }

        /*Inicia el contador de filas de las partidas*/
        int iContFi                     = 1;               
        
        /*Obtiene todas las partidas de la venta*/        
        try
        {
            sQ = "SELECT partvta.PROD, pre, partvta.IMPO, partvta.DESCRIP, partvta.UNID, partvta.ALMA, partvta.DESCU, partvta.CANT, partvta.VTA, partvta.DEVS, partvta.ESKIT, partvta.MON, partvta.IMPUE, partvta.TALL, partvta.COLO, partvta.LOT, partvta.PEDIMEN, partvta.FCADU, partvta.FENTRE, partvta.CANTENTRE, partvta.TIPDOC, vtas.VTADEVP, partvta.LIST FROM partvta LEFT OUTER JOIN vtas ON vtas.VTA = partvta.VTA WHERE partvta.VTA = " + sVta;
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene los totales*/
                String sPre     = rs.getString("pre");
                String sImp     = rs.getString("impo");                
                
                /*Dale formato de moneda a los totales*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sPre);                
                sPre            = n.format(dCant);
                dCant           = Double.parseDouble(sImp);                
                sImp            = n.format(dCant);
                
                /*Obtiene la descripción*/
                String sDescrip     = rs.getString("descrip");
                
                /*Obtiene la unidad*/
                String sUnid        = rs.getString("unid");
                
                /*Obtiene el almacén*/
                String sAlma        = rs.getString("alma");
                
                /*Obtiene el descuento*/
                String sDesc        = rs.getString("descu");
                                
                /*Obtiene la cantidad*/
                String sCant        = rs.getString("cant");
                
                /*Obtiene si es kit o no*/
                String sKit         = rs.getString("eskit");
                
                /*Obtiene la moneda*/
                String sMon         = rs.getString("mon");
                
                /*Obtiene el impuesto*/
                String sImpue       = rs.getString("impue");
                
                /*Obtiene la talla*/
                String sTall        = rs.getString("tall");
                
                /*Obtiene el color*/
                String sColo        = rs.getString("colo");
                
                /*Obtiene el lote*/
                String sLot        = rs.getString("lot");
                
                /*Obtiene el pedimento*/
                String sPedimen    = rs.getString("pedimen");
                
                /*Obtiene la caducidad*/
                String sCadu        = rs.getString("fcadu");
                
                /*Obtiene el backorder*/
                String sBack        = rs.getString("fentre");
                
                /*Obtiene los entregaos*/
                String sEntre       = rs.getString("cantentre");
                
                /*Si es nota de crédito entonces*/
                if(rs.getString("tipdoc").compareTo("NOTC")==0 && rs.getString("vtadevp").compareTo("0")==0)
                {
                    /*Creal la descripción*/
                    sDescrip        = sDescrip + " del Cliente: " + sUnid;
                    
                    /*Restea los valores que no deben de colocarse*/
                    sDesc           = "";
                    sAlma           = "";
                    sCant           = "";                                        
                    sKit            = "";                                        
                    sMon            = "";                                        
                    sImpue          = "";                                        
                    sUnid           = "";                                        
                    sPre            = "";                                      
                    sTall           = "";               
                    sColo           = "";               
                    sLot            = "";               
                    sPedimen        = "";               
                    sCadu           = "";               
                    sBack           = "";               
                    sEntre          = "";
                }
                
                /*Agrega el registro en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("prod"), sCant, sUnid, sAlma, rs.getString("list"), sDescrip, sPre, sDesc, sImpue, sMon, sImp, sKit, sTall, sColo, sLot, sPedimen, sCadu, sBack, sEntre};
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

        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para la tabla de partidas, para cuando se modifique una celda*/
        PropertyChangeListener propC = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtén la propiedad que a sucedio en el control*/
                String prop = event.getPropertyName();                                
                                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(prop)) 
                {                    
                    /*Obtén la fila seleccionada*/                    
                    if(jTab.getSelectedRow()==-1)
                        return;
                                        
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtén algunos datos originales*/                        
                        sProdOri               = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                                          
                        sCantOri               = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                                                                                                          
                        sUnidOri               = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                                                                          
                        sAlmaOri               = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                                                                          
                        sListOri               = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();                                                                                                          
                        sDescripOri            = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();                                                                                                          
                        sPreOri                = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();                                                                                  
                        sDescOri               = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();                                                                                  
                        sImpueOri              = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();                                                                                  
                        sMonOri                = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();                                                                                  
                        sImpoOri               = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();                                                                                  
                        sEsKitOri              = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();                                                                                  
                        sTallOri               = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();                                                                                  
                        sColoOri               = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();
                        sLotOri                = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();
                        sPedimenOri            = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();
                        sCaduOri               = jTab.getValueAt(jTab.getSelectedRow(), 17).toString();
                        sBackOri               = jTab.getValueAt(jTab.getSelectedRow(), 18).toString();
                        
                        /*Aumenta en uno el contador*/
                        ++iContCellEd;                                                                                                        
                    }
                    else if(iContCellEd >= 2)
                    {        
                        /*Reinicia el conteo*/
                        iContCellEd = 1;
                        
                        /*Coloca los valores originales que tenían*/
                        jTab.getModel().setValueAt(sProdOri,    jTab.getSelectedRow(), 1);
                        jTab.getModel().setValueAt(sCantOri,    jTab.getSelectedRow(), 2);
                        jTab.getModel().setValueAt(sUnidOri,    jTab.getSelectedRow(), 3);
                        jTab.getModel().setValueAt(sAlmaOri,    jTab.getSelectedRow(), 4);
                        jTab.getModel().setValueAt(sListOri,    jTab.getSelectedRow(), 5);
                        jTab.getModel().setValueAt(sDescripOri, jTab.getSelectedRow(), 6);
                        jTab.getModel().setValueAt(sPreOri,     jTab.getSelectedRow(), 7);
                        jTab.getModel().setValueAt(sDescOri,    jTab.getSelectedRow(), 8);
                        jTab.getModel().setValueAt(sImpueOri,   jTab.getSelectedRow(), 9);
                        jTab.getModel().setValueAt(sMonOri,     jTab.getSelectedRow(), 10);
                        jTab.getModel().setValueAt(sImpoOri,    jTab.getSelectedRow(), 11);
                        jTab.getModel().setValueAt(sEsKitOri,   jTab.getSelectedRow(), 12);
                        jTab.getModel().setValueAt(sTallOri,    jTab.getSelectedRow(), 13);
                        jTab.getModel().setValueAt(sColoOri,    jTab.getSelectedRow(), 14);
                        jTab.getModel().setValueAt(sLotOri,     jTab.getSelectedRow(), 15);
                        jTab.getModel().setValueAt(sPedimenOri, jTab.getSelectedRow(), 16);
                        jTab.getModel().setValueAt(sCaduOri,    jTab.getSelectedRow(), 17);
                        jTab.getModel().setValueAt(sBackOri,    jTab.getSelectedRow(), 18);
                                                
                    }/*Fin de else if(iContadorCellEditor >= 2)*/                                                                
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */
            
        };
        
        /*Establece el listener para la tabla de partidas*/
        jTab.addPropertyChangeListener(propC);        
        
    }/*Fin de public NuevaCompra() */    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTCall = new javax.swing.JTextField();
        jTCol = new javax.swing.JTextField();
        jTTel = new javax.swing.JTextField();
        jTPai = new javax.swing.JTextField();
        jTCP = new javax.swing.JTextField();
        jTNoExt = new javax.swing.JTextField();
        jTNoInt = new javax.swing.JTextField();
        jTRFC = new javax.swing.JTextField();
        jTCiu = new javax.swing.JTextField();
        jTEstad = new javax.swing.JTextField();
        jTCodEmp = new javax.swing.JTextField();
        jTNomEmp = new javax.swing.JTextField();
        jTCond = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jTTipDoc = new javax.swing.JTextField();
        jTSubTot = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTImp = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTTot = new javax.swing.JTextField();
        jTFec = new javax.swing.JTextField();
        jTMetPag = new javax.swing.JTextField();
        jTCta = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTAObserv = new javax.swing.JTextArea();
        jLabel48 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jBTab1 = new javax.swing.JButton();
        jTNoSer = new javax.swing.JTextField();
        jLNot = new javax.swing.JLabel();

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
        jBSal.setNextFocusableComponent(jTMetPag);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 110, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Producto", "Qty.", "Unidad", "Almacén", "Lista", "Descripción", "Precio", "Descuento", "Impuesto", "Moneda", "Importe", "Es Kit", "Talla", "Color", "Lote", "Pedimento", "Caducidad", "Backorder", "Entregados"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 1030, 200));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Cod. Proveedor:");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 110, -1));

        jLabel6.setText("*CP:");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 70, -1));

        jLabel20.setText("*Cod. Cliente:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, -1));

        jLabel24.setText("*Calle:");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 110, -1));

        jLabel29.setText("Teléfono:");
        jPanel4.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 110, -1));

        jLabel30.setText("*Colonia:");
        jPanel4.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 70, -1));

        jLabel31.setText("*No. Exterior:");
        jPanel4.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 90, -1));

        jLabel32.setText("No. Interior:");
        jPanel4.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 110, -1));

        jLabel34.setText("*Ciudad:");
        jPanel4.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 70, -1));

        jLabel35.setText("Tipo Documento:");
        jPanel4.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, 100, -1));

        jLabel36.setText("Condiciones:");
        jPanel4.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 230, -1));

        jTCall.setEditable(false);
        jTCall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCall.setNextFocusableComponent(jTCol);
        jTCall.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCallFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCallFocusLost(evt);
            }
        });
        jTCall.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCallKeyPressed(evt);
            }
        });
        jPanel4.add(jTCall, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 160, 20));

        jTCol.setEditable(false);
        jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCol.setNextFocusableComponent(jTTel);
        jTCol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTColFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTColFocusLost(evt);
            }
        });
        jTCol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTColKeyPressed(evt);
            }
        });
        jPanel4.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 160, 20));

        jTTel.setEditable(false);
        jTTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel.setNextFocusableComponent(jTPai);
        jTTel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelFocusLost(evt);
            }
        });
        jTTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelKeyPressed(evt);
            }
        });
        jPanel4.add(jTTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 160, 20));

        jTPai.setEditable(false);
        jTPai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPai.setNextFocusableComponent(jTCP);
        jTPai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPaiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPaiFocusLost(evt);
            }
        });
        jTPai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPaiKeyPressed(evt);
            }
        });
        jPanel4.add(jTPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 160, 20));

        jTCP.setEditable(false);
        jTCP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCP.setNextFocusableComponent(jTNoExt);
        jTCP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCPFocusLost(evt);
            }
        });
        jTCP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCPKeyPressed(evt);
            }
        });
        jPanel4.add(jTCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 160, 20));

        jTNoExt.setEditable(false);
        jTNoExt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoExt.setNextFocusableComponent(jTNoInt);
        jTNoExt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNoExtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNoExtFocusLost(evt);
            }
        });
        jTNoExt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNoExtKeyPressed(evt);
            }
        });
        jPanel4.add(jTNoExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 160, 20));

        jTNoInt.setEditable(false);
        jTNoInt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoInt.setNextFocusableComponent(jTRFC);
        jTNoInt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNoIntFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNoIntFocusLost(evt);
            }
        });
        jTNoInt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNoIntKeyPressed(evt);
            }
        });
        jPanel4.add(jTNoInt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 160, 20));

        jTRFC.setEditable(false);
        jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRFC.setNextFocusableComponent(jTCiu);
        jTRFC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRFCFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRFCFocusLost(evt);
            }
        });
        jTRFC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRFCKeyPressed(evt);
            }
        });
        jPanel4.add(jTRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, 160, 20));

        jTCiu.setEditable(false);
        jTCiu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCiu.setNextFocusableComponent(jTEstad);
        jTCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCiuFocusLost(evt);
            }
        });
        jTCiu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCiuKeyPressed(evt);
            }
        });
        jPanel4.add(jTCiu, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 160, 20));

        jTEstad.setEditable(false);
        jTEstad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEstad.setNextFocusableComponent(jTCond);
        jTEstad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstadFocusLost(evt);
            }
        });
        jTEstad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstadKeyPressed(evt);
            }
        });
        jPanel4.add(jTEstad, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 160, 20));

        jTCodEmp.setEditable(false);
        jTCodEmp.setBackground(new java.awt.Color(204, 255, 204));
        jTCodEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodEmp.setNextFocusableComponent(jTNomEmp);
        jTCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodEmpFocusLost(evt);
            }
        });
        jTCodEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodEmpKeyPressed(evt);
            }
        });
        jPanel4.add(jTCodEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 120, 20));

        jTNomEmp.setEditable(false);
        jTNomEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNomEmp.setNextFocusableComponent(jTCall);
        jTNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNomEmpFocusLost(evt);
            }
        });
        jTNomEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNomEmpKeyPressed(evt);
            }
        });
        jPanel4.add(jTNomEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 410, 20));

        jTCond.setEditable(false);
        jTCond.setForeground(new java.awt.Color(0, 153, 0));
        jTCond.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTCond.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCond.setNextFocusableComponent(jTab);
        jTCond.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCondFocusLost(evt);
            }
        });
        jPanel4.add(jTCond, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 230, 20));

        jLabel45.setText("País:");
        jPanel4.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 70, -1));

        jLabel47.setText("*RFC:");
        jPanel4.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 70, -1));

        jLabel37.setText("*Estado:");
        jPanel4.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 70, -1));

        jTTipDoc.setEditable(false);
        jTTipDoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTipDocFocusLost(evt);
            }
        });
        jPanel4.add(jTTipDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, 130, 20));

        jP1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 790, 180));

        jTSubTot.setEditable(false);
        jTSubTot.setBackground(new java.awt.Color(204, 255, 204));
        jTSubTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTSubTot.setForeground(new java.awt.Color(51, 51, 0));
        jTSubTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTSubTot.setText("$0.00");
        jTSubTot.setNextFocusableComponent(jBSal);
        jTSubTot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSubTotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSubTotFocusLost(evt);
            }
        });
        jTSubTot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSubTotKeyPressed(evt);
            }
        });
        jP1.add(jTSubTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 410, 160, 30));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel26.setText("Sub Total:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 420, 110, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setText("Impuesto:");
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 450, 110, -1));

        jTImp.setEditable(false);
        jTImp.setBackground(new java.awt.Color(204, 255, 204));
        jTImp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTImp.setForeground(new java.awt.Color(51, 51, 0));
        jTImp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTImp.setText("$0.00");
        jTImp.setNextFocusableComponent(jBSal);
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
        });
        jP1.add(jTImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 440, 160, 30));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel28.setText("Total:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 480, 110, -1));

        jTTot.setEditable(false);
        jTTot.setBackground(new java.awt.Color(204, 255, 204));
        jTTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTTot.setForeground(new java.awt.Color(51, 51, 0));
        jTTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTot.setText("$0.00");
        jTTot.setNextFocusableComponent(jBSal);
        jTTot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTotFocusLost(evt);
            }
        });
        jTTot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTotKeyPressed(evt);
            }
        });
        jP1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 470, 160, 30));

        jTFec.setEditable(false);
        jTFec.setForeground(new java.awt.Color(51, 51, 255));
        jTFec.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 255), 1, true));
        jTFec.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFecFocusGained(evt);
            }
        });
        jTFec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFecKeyPressed(evt);
            }
        });
        jP1.add(jTFec, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 40, 120, 20));

        jTMetPag.setEditable(false);
        jTMetPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMetPag.setNextFocusableComponent(jTCta);
        jTMetPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMetPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMetPagFocusLost(evt);
            }
        });
        jTMetPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMetPagKeyPressed(evt);
            }
        });
        jP1.add(jTMetPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 410, 100, 20));

        jTCta.setEditable(false);
        jTCta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCta.setNextFocusableComponent(jTNoSer);
        jTCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaFocusLost(evt);
            }
        });
        jTCta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaKeyPressed(evt);
            }
        });
        jP1.add(jTCta, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 430, 100, 20));

        jLabel22.setText("*Serie Factura:");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, 110, -1));

        jLabel44.setText("Método Pago:");
        jP1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 410, 110, -1));

        jLabel40.setText("Observaciones:");
        jP1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 410, 110, -1));

        jScrollPane2.setNextFocusableComponent(jTCodEmp);

        jTAObserv.setEditable(false);
        jTAObserv.setColumns(20);
        jTAObserv.setRows(5);
        jTAObserv.setNextFocusableComponent(jTCodEmp);
        jTAObserv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAObservFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(jTAObserv);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 430, 230, 70));

        jLabel48.setText("Cuenta:");
        jP1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 430, 110, -1));

        jLabel3.setText("Fecha:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, 80, -1));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 10, 20));

        jTNoSer.setEditable(false);
        jTNoSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoSer.setNextFocusableComponent(jTAObserv);
        jTNoSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNoSerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNoSerFocusLost(evt);
            }
        });
        jTNoSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNoSerKeyPressed(evt);
            }
        });
        jP1.add(jTNoSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 450, 100, 20));

        jLNot.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLNot.setForeground(new java.awt.Color(204, 0, 0));
        jLNot.setText("NOTA DE CRÉDITO");
        jLNot.setFocusable(false);
        jP1.add(jLNot, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 500, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 1058, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
                                  
                
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        dispose();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

        
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/                
        jBSal.doClick();       
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed
                
    
    /*Cuando se gana el foco del teclado en el campo de sub tot*/
    private void jTSubTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSubTot.setSelectionStart(0);jTSubTot.setSelectionEnd(jTSubTot.getText().length()); 
        
    }//GEN-LAST:event_jTSubTotFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de IVA*/
    private void jTImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTImp.setSelectionStart(0);jTImp.setSelectionEnd(jTImp.getText().length()); 
        
    }//GEN-LAST:event_jTImpFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de tot*/
    private void jTTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTot.setSelectionStart(0);jTTot.setSelectionEnd(jTTot.getText().length()); 
        
    }//GEN-LAST:event_jTTotFocusGained

    
    /*Cuando se presiona una tecla en el campo de subtot*/
    private void jTSubTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSubTotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSubTotKeyPressed

    
    /*Cuando se presiona una tecla en el campo de IVA*/
    private void jTImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTImpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTImpKeyPressed

    
    /*Cuando se presiona una tecla en el campo de tot*/
    private void jTTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTotKeyPressed
                       
        
    /*Cuando se presiona una tecla en la tabla de partidas*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de calle*/
    private void jTCallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCall.setSelectionStart(0);jTCall.setSelectionEnd(jTCall.getText().length());        
        
    }//GEN-LAST:event_jTCallFocusGained

    
    /*Cuando se gana el foco del teclado en el campo col*/
    private void jTColFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCol.setSelectionStart(0);jTCol.setSelectionEnd(jTCol.getText().length());        
        
    }//GEN-LAST:event_jTColFocusGained

    
    /*Cuando se gana el foco del teclado en el campo teléfono*/
    private void jTTelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel.setSelectionStart(0);jTTel.setSelectionEnd(jTTel.getText().length());        
        
    }//GEN-LAST:event_jTTelFocusGained

    
    /*Cuando se gana el foco del teclado en el campo pai*/
    private void jTPaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPai.setSelectionStart(0);jTPai.setSelectionEnd(jTPai.getText().length());        
        
    }//GEN-LAST:event_jTPaiFocusGained

    
    /*Cuando se gana el foco del teclado en el campo CP*/
    private void jTCPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCP.setSelectionStart(0);jTCP.setSelectionEnd(jTCP.getText().length());        
        
    }//GEN-LAST:event_jTCPFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de número de exterior*/
    private void jTNoExtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoExt.setSelectionStart(0);jTNoExt.setSelectionEnd(jTNoExt.getText().length());        
        
    }//GEN-LAST:event_jTNoExtFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de número de interior*/
    private void jTNoIntFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoInt.setSelectionStart(0);jTNoInt.setSelectionEnd(jTNoInt.getText().length());        
        
    }//GEN-LAST:event_jTNoIntFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de RFC*/
    private void jTRFCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRFC.setSelectionStart(0);jTRFC.setSelectionEnd(jTRFC.getText().length());        
        
    }//GEN-LAST:event_jTRFCFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de ciu*/
    private void jTCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCiu.setSelectionStart(0);jTCiu.setSelectionEnd(jTCiu.getText().length());        
        
    }//GEN-LAST:event_jTCiuFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de estad*/
    private void jTEstadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEstad.setSelectionStart(0);jTEstad.setSelectionEnd(jTEstad.getText().length());        
        
    }//GEN-LAST:event_jTEstadFocusGained

    
    
    /*Cuando se presiona un tecla en el campo de teléfono*/
    private void jTTelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelKeyPressed

    
    /*Cuando se presiona una tecla en el campo de calle*/
    private void jTCallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCallKeyPressed

    
    /*Cuando se presiona una tecla en el campo de col*/
    private void jTColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTColKeyPressed

    
    /*Cuando se presiona una tecla en el campo de pai*/
    private void jTPaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPaiKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPaiKeyPressed

    
    /*Cuando se presiona una tecla en el campo de CP*/
    private void jTCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCPKeyPressed

    
    /*Cuando se presiona una tecla en el campo de número de exterior*/
    private void jTNoExtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoExtKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoExtKeyPressed

    
    /*Cuando se presiona una tecla en el campo de número de interior*/
    private void jTNoIntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
                
    }//GEN-LAST:event_jTNoIntKeyPressed

    
    /*Cuando se presiona una tecla en el campo de RFC*/
    private void jTRFCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRFCKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTRFCKeyPressed

    
    /*Cuando se presiona una tecla en el campo de ciu*/
    private void jTCiuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCiuKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCiuKeyPressed

    
    /*Cuando se presiona una tecla en el campo de estad*/
    private void jTEstadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstadKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstadKeyPressed

        
    /*Cuando se gana el foco del teclado en el campo de fecha*/
    private void jTFecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFecFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFec.setSelectionStart(0);jTFec.setSelectionEnd(jTFec.getText().length());        
        
    }//GEN-LAST:event_jTFecFocusGained

    
    /*Cuando se presiona una tecla en el campo de fecha*/
    private void jTFecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFecKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFecKeyPressed
    
    
    /*Cuando se gana el foco del teclado en el campo de empresa*/
    private void jTCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodEmpFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodEmp.setSelectionStart(0);jTCodEmp.setSelectionEnd(jTCodEmp.getText().length());

    }//GEN-LAST:event_jTCodEmpFocusGained
   
    
    /*Cuando se presiona una tecla en el campo del código de la empresa*/
    private void jTCodEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        

    }//GEN-LAST:event_jTCodEmpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del nom de la empresa*/
    private void jTNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomEmpFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTNomEmp.setSelectionStart(0);jTNomEmp.setSelectionEnd(jTNomEmp.getText().length());

    }//GEN-LAST:event_jTNomEmpFocusGained

    
    /*Cuando se presiona una tecla en el campo de nom de empresa*/
    private void jTNomEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomEmpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTNomEmpKeyPressed
                                              
    
    /*Cuando se presiona una tecla en el campo del método de pago*/
    private void jTMetPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMetPagKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTMetPagKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la cuenta*/
    private void jTCtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaKeyPressed
    
    
    /*Cuando se gana el foco del teclado en el campo de método de pago*/
    private void jTMetPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMetPag.setSelectionStart(0);jTMetPag.setSelectionEnd(jTMetPag.getText().length()); 
        
    }//GEN-LAST:event_jTMetPagFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta*/
    private void jTCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCta.setSelectionStart(0);jTCta.setSelectionEnd(jTCta.getText().length()); 
        
    }//GEN-LAST:event_jTCtaFocusGained
      
    
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

    
    /*Cuando se mueve el mouse en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved
   
    
    /*Cuando se presiona el botón de ver tabla*/
    private void jBTab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab1ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab);       

    }//GEN-LAST:event_jBTab1ActionPerformed

    
    /*Cuando se presiona una tecla sobre el botón de ver tabla*/
    private void jBTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTab1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTab1KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del número de serie*/
    private void jTNoSerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoSerFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoSer.setSelectionStart(0);jTNoSer.setSelectionEnd(jTNoSer.getText().length());         
        
    }//GEN-LAST:event_jTNoSerFocusGained

    
    /*Cuando se presiona una tecla en el campo del número de serie*/
    private void jTNoSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoSerKeyPressed

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodEmpFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCodEmp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodEmpFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCall.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCallFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCol.setCaretPosition(0);        
        
    }//GEN-LAST:event_jTColFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTel.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPai.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPaiFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCondFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCondFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCond.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCondFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomEmpFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTNomEmp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomEmpFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCP.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCPFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTNoExtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTNoExt.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNoExtFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTNoIntFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTNoInt.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNoIntFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTRFCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTRFC.setCaretPosition(0);
        
    }//GEN-LAST:event_jTRFCFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCiu.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCiuFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTEstadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTEstad.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEstadFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTipDocFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTipDoc.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTipDocFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTMetPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTMetPag.setCaretPosition(0);
        
    }//GEN-LAST:event_jTMetPagFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCta.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCtaFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTNoSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoSerFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTNoSer.setCaretPosition(0);        
        
    }//GEN-LAST:event_jTNoSerFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTAObservFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAObserv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTAObservFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del subtotal*/
    private void jTSubTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTSubTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSubTotFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del impuesto*/
    private void jTImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTImp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTImpFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del total*/
    private void jTTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusLost
       
        /*Coloca el caret en la posiciòn 0*/
        jTTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTotFocusLost
        
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JLabel jLNot;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTAObserv;
    private javax.swing.JTextField jTCP;
    private javax.swing.JTextField jTCall;
    private javax.swing.JTextField jTCiu;
    private javax.swing.JTextField jTCodEmp;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTCond;
    private javax.swing.JTextField jTCta;
    private javax.swing.JTextField jTEstad;
    private javax.swing.JTextField jTFec;
    private javax.swing.JTextField jTImp;
    private javax.swing.JTextField jTMetPag;
    private javax.swing.JTextField jTNoExt;
    private javax.swing.JTextField jTNoInt;
    private javax.swing.JTextField jTNoSer;
    private javax.swing.JTextField jTNomEmp;
    private javax.swing.JTextField jTPai;
    private javax.swing.JTextField jTRFC;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTTel;
    private javax.swing.JTextField jTTipDoc;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaEmpresa extends javax.swing.JFrame */
