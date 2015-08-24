//Paquete
package ptovta;

/*Importaciones*/
import vis.VisDevPFac;
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JOptionPane;
import java.sql.Connection;
import javax.swing.ImageIcon;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;



/*Clase para devolucion parcial de una factura*/
public class DevPVta extends javax.swing.JFrame 
{            
    /*Declara variables de instancia*/     
    private int             iContFi;
    private String          sVtaGlob;
    private JTable          jTabFac;    
    
    /*Contador para modificar tabla*/
    private int             iContCellEd;
    
    /*Contiene el label del otro formulario*/
    private javax.swing.JLabel jLab;
    
    /*Variables originales de la tabla*/
    private String          sProdOri;
    private String          sCantOri;
    private String          sUnidOri;
    private String          sAlmaOri;
    private String          sListOri;
    private String          sDescripOri;
    private String          sCostOri;
    private String          sDescOri;
    private String          sImpueOri;
    private String          sMonOri;
    private String          sImpoOri;
    private String          sDevsOri;
    private String          sTallOri;
    private String          sColoOri;
    private String          sLotOri;
    private String          sPedimenOri;
    private String          sCaduOri;
    private String          sBackOri;
    private String          sEntreOri;
    
    /*Para saber si esto proviene del punto de venta*/
    private boolean         bPtoVta;
    
    
    /*Consructor con argumento*/
    public DevPVta(String sCodFac, JTable jTabVtas, javax.swing.JLabel jLa, boolean bPtoVt) 
    {                                        
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        /*Esconde la columna del id tabla*/
        jTab.getColumnModel().getColumn(20).setMinWidth(0);
        jTab.getColumnModel().getColumn(20).setMaxWidth(0);
        
        /*Obtiene si viene del punto de venta o no*/
        bPtoVta = bPtoVt;
        
        /*Para que la tabla tenga scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Obtiene el label del otro formulario*/
        jLab    = jLa;
        
        /*Que las comlumnas de la tabla no se muevan*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Obtiene la referencia de la tabla de facturas del otro formulario*/
        jTabFac          = jTabVtas;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Obtiene el código de la venta del otro formulario*/
        sVtaGlob       = sCodFac;
                
        /*Inicia el contador de filas de las partidas*/
        iContFi        = 1;
        
        /*Establece el titulo de la vtana con el usuario, la fecha y hora*/                
        this.setTitle("Devolución parcial de factura, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Centra la vtana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el tamaño de la última columna de la tabla de partidas*/
        jTab.getColumnModel().getColumn(1).setPreferredWidth    (130);        
        jTab.getColumnModel().getColumn(6).setPreferredWidth    (400);
        jTab.getColumnModel().getColumn(12).setPreferredWidth   (100);        
        jTab.getColumnModel().getColumn(13).setPreferredWidth   (130);        
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Pon el foco del teclado en el campo del motivo de devolución*/
        jTMot.grabFocus();
        
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los datos de la factura de la base de datos*/        
        try
        {                  
            sQ = "SELECT vtas.TIPDOC, vtas.NOSER, vtas.MOTIV, vtas.ESTAD, vtas.CODEMP,  vtas.NOREFER, vtas.FEMI, vtas.SUBTOT, emps.NOM, vtas.IMPUE, vtas.TOT FROM vtas LEFT JOIN emps ON CONCAT_WS('', emps.SER,emps.CODEMP) = vtas.CODEMP WHERE vtas.VTA = " + sVtaGlob;
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene el subtotal*/                
                String sSubTot  = rs.getString("vtas.SUBTOT");      
                
                /*Dale formato de moneda al subtotal*/                
                double dCant    = Double.parseDouble(sSubTot);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sSubTot         = n.format(dCant);
        
                /*Obtiene el impuesto*/                
                String sImpue= rs.getString("vtas.IMPUE");      
                
                /*Dale formato de moneda al impue*/                
                dCant       = Double.parseDouble(sImpue);                
                n           = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sImpue      = n.format(dCant);
                
                /*Obtiene el total*/                
                String sTot = rs.getString("vtas.TOT");      
                
                /*Dale formato de moneda al total*/                
                dCant       = Double.parseDouble(sTot);                
                n           = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sTot        = n.format(dCant);
                
                /*Coloca todos los valores obtenidos en sus campos correspondientes*/
                jTCli.setText           (rs.getString("vtas.CODEMP"));
                jTNomEmp.setText        (rs.getString("emps.NOM"));
                jTNoDoc.setText         (rs.getString("vtas.NOREFER"));
                jTTipDoc.setText        (rs.getString("vtas.TIPDOC"));
                jTNoSer.setText         (rs.getString("vtas.NOSER"));
                jTFec.setText           (rs.getString("vtas.FEMI"));                
                jTEst.setText           (rs.getString("vtas.ESTAD"));
                jTMot.setText           (rs.getString("vtas.MOTIV"));
                jTVta.setText           (sVtaGlob);
                jTSubTot.setText        (sSubTot);
                jTImp.setText           (sImpue);
                jTTot.setText           (sTot);   
                
                /*Si el estad es devolución parcial entonces el color de la letra del campo será roja*/
                if(rs.getString("vtas.ESTAD").compareTo("DEVP")==0)
                    jTEst.setForeground(Color.RED);
                else
                    jTEst.setForeground(Color.BLUE);
                                                                                
            }/*Fin de while (rs.next())*/                        
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
        
        /*Obtiene todas las partidas de la venta de la base de datos*/
        try
        {                  
            sQ = "SELECT * FROM partvta WHERE vta = " + sVtaGlob;
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Si es un componente entonces que continue por que no lo debe de cargar*/
                if(rs.getString("kitmae").compareTo("1")==0)
                    continue;
                
                /*Obtiene el precio*/
                String sPre     = rs.getString("pre");   
                
                /*Dale formato de moneda al costo*/                
                double dCant    = Double.parseDouble(sPre);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sPre            = n.format(dCant);
                
                /*Obtiene el código del impuesto*/
                String sImpue   = rs.getString("impue"); 
                
                /*Obtiene el importe*/
                String sImp     = rs.getString("impo");   
                
                /*Dale formato de moneda al importe*/                
                dCant           = Double.parseDouble(sImp);                
                n               = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sImp            = n.format(dCant);
                                
                /*Agrega todos los datos obtenidos en la tabla de partidas*/
                DefaultTableModel te = (DefaultTableModel)jTab.getModel();
                Object nu[]          = {iContFi, rs.getString("prod"), rs.getString("cant"), rs.getString("unid"), rs.getString("alma"), rs.getString("list"), rs.getString("descrip"), sPre, rs.getString("descu"), sImpue, rs.getString("mon"), sImp, rs.getString("devs"), "", rs.getString("tall"), rs.getString("colo"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("fcadu"), rs.getString("fentre"), rs.getString("id_id"), rs.getString("cantentre")};        
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas*/
                ++iContFi;
                
            }/*Fin de while(rs.nex())*/                        
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
        if(Star.iCierrBas(con)==-1)
            return;

        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        //inicializa el valor 
        for(int x=0;x<jTab.getRowCount();x++)
        {
                        jTab.setValueAt(0,x,13);
        }
        /*Crea el listener para la tabla de partidas, para cuando se modifique la celda de cant de devolución*/
        PropertyChangeListener pr = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtén la propiedad que a sucedio en el control*/
                String pro = event.getPropertyName();                                
                                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pro)) 
                {                    
                    /*Obtén la fila seleccionada*/                    
                    if(jTab.getSelectedRow()==-1)
                            return;
                        
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sProdOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sCantOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sUnidOri        = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sAlmaOri        = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sListOri        = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sDescripOri     = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sCostOri        = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sDescOri        = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sImpueOri       = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sMonOri         = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sImpoOri        = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        sDevsOri        = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();
                        sTallOri        = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();
                        sColoOri        = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();                        
                        sLotOri         = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();                        
                        sPedimenOri     = jTab.getValueAt(jTab.getSelectedRow(), 17).toString();                        
                        sCaduOri        = jTab.getValueAt(jTab.getSelectedRow(), 18).toString();                        
                        sBackOri        = jTab.getValueAt(jTab.getSelectedRow(), 19).toString();                        
                        sEntreOri       = jTab.getValueAt(jTab.getSelectedRow(), 21).toString();                        
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                        
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sProdOri,       jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sCantOri,       jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sUnidOri,       jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sAlmaOri,       jTab.getSelectedRow(), 4);
                        jTab.setValueAt(sListOri,       jTab.getSelectedRow(), 5);
                        jTab.setValueAt(sDescripOri,    jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sCostOri,       jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sDescOri,       jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sImpueOri,      jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sMonOri,        jTab.getSelectedRow(), 10);                        
                        jTab.setValueAt(sImpoOri,       jTab.getSelectedRow(), 11);                        
                        jTab.setValueAt(sDevsOri,       jTab.getSelectedRow(), 12);
                        jTab.setValueAt(sTallOri,       jTab.getSelectedRow(), 14);                        
                        jTab.setValueAt(sColoOri,       jTab.getSelectedRow(), 15);                        
                        jTab.setValueAt(sLotOri,        jTab.getSelectedRow(), 16);
                        jTab.setValueAt(sPedimenOri,    jTab.getSelectedRow(), 17);
                        jTab.setValueAt(sCaduOri,       jTab.getSelectedRow(), 18);
                        jTab.setValueAt(sBackOri,       jTab.getSelectedRow(), 19);                        
                        jTab.setValueAt(sEntreOri,      jTab.getSelectedRow(), 21);                        
                        
                        /*Obtén la cantidad de devolución que el usuario ingreso*/
                        String sCantDev         = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();                    
                        /*Si la cantidad de devolución introducida no es númerica entonces*/
                        try
                        {
                            Double.parseDouble(sCantDev);
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Colocar cadena vacia en la cant de devolución y regresa*/
                            jTab.getModel().setValueAt(0, jTab.getSelectedRow(), 13);                                            
                            return;                                                
                        }          

                        /*Convierte a valor absoluto el número introducido, para quitar el negativo en caso de que lo tenga*/
                        sCantDev         = Integer.toString((int)Math.abs(Double.parseDouble(sCantDev)));                    

//                        /*Si la cant es igual a 0 entonces*/
//                        if(Integer.parseInt(sCantDev)==0)
//                        {
//                            /*Coloca cadena vacia en la cantidad de devolución y regresa*/
//                            jTab.getModel().setValueAt("", jTab.getSelectedRow(), 13);                                            
//                            return;
//                        }

                        /*Coloca el valor absoluto en la cantidad de devolución*/
                        jTab.getModel().setValueAt(sCantDev, jTab.getSelectedRow(), 13);
                        
                    }/*Fin de else*/
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
       
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */
            
        };
        
        /*Establece el listener para la tabla de partidas*/
        jTab.addPropertyChangeListener(pr);        
    
    }/*Fin de public IngresarCompra() */    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jPEnca = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTNoDoc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTVta = new javax.swing.JTextField();
        jTNomEmp = new javax.swing.JTextField();
        jTCli = new javax.swing.JTextField();
        jTFec = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTEst = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTNoSer = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTTipDoc = new javax.swing.JTextField();
        jTSubTot = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTImp = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTTot = new javax.swing.JTextField();
        jBGuar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTMot = new javax.swing.JTextField();
        jBTabG = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
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
        jBSal.setNextFocusableComponent(jTMot);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 120, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Producto", "Cantidad", "Unidad", "Almacén", "Lista", "Descripción", "Costo", "Descuento", "Impuesto", "Moneda", "Importe", "Devueltos", "Cant. Devolución", "Talla", "Color", "Lote", "Pedimento", "Caducidad", "Backorder", "ID", "Entregados"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBGuar);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 1090, 240));

        jPEnca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPEncaKeyPressed(evt);
            }
        });
        jPEnca.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Cod. Proveedor:");
        jPEnca.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 110, -1));

        jLabel6.setText("Cod. Cliente:");
        jPEnca.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, -1));

        jTNoDoc.setEditable(false);
        jTNoDoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoDoc.setNextFocusableComponent(jTab);
        jTNoDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNoDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNoDocFocusLost(evt);
            }
        });
        jTNoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTNoDocActionPerformed(evt);
            }
        });
        jTNoDoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNoDocKeyPressed(evt);
            }
        });
        jPEnca.add(jTNoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 120, 20));

        jLabel3.setText("Nombre Cliente:");
        jPEnca.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 110, -1));

        jLabel2.setText("Fecha:");
        jPEnca.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 80, -1));

        jTVta.setEditable(false);
        jTVta.setForeground(new java.awt.Color(51, 51, 255));
        jTVta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTVta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTVta.setNextFocusableComponent(jTab);
        jTVta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTVtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTVtaFocusLost(evt);
            }
        });
        jTVta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTVtaKeyPressed(evt);
            }
        });
        jPEnca.add(jTVta, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 90, 20));

        jTNomEmp.setEditable(false);
        jTNomEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNomEmp.setNextFocusableComponent(jTab);
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
        jPEnca.add(jTNomEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 380, 20));

        jTCli.setEditable(false);
        jTCli.setBackground(new java.awt.Color(204, 255, 204));
        jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCli.setNextFocusableComponent(jTab);
        jTCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCliFocusLost(evt);
            }
        });
        jTCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCliKeyPressed(evt);
            }
        });
        jPEnca.add(jTCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 120, 20));

        jTFec.setEditable(false);
        jTFec.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFec.setNextFocusableComponent(jTab);
        jTFec.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFecFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFecFocusLost(evt);
            }
        });
        jTFec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFecKeyPressed(evt);
            }
        });
        jPEnca.add(jTFec, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 130, 20));

        jLabel5.setText("Venta:");
        jPEnca.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 80, -1));

        jTEst.setEditable(false);
        jTEst.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTEst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEst.setNextFocusableComponent(jTab);
        jTEst.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstFocusLost(evt);
            }
        });
        jTEst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstKeyPressed(evt);
            }
        });
        jPEnca.add(jTEst, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 90, 20));

        jLabel7.setText("Tipo Documento:");
        jPEnca.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 120, -1));

        jLabel21.setText("Serie:");
        jPEnca.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 110, -1));

        jLabel22.setText("Folio:");
        jPEnca.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 110, -1));

        jTNoSer.setEditable(false);
        jTNoSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoSer.setNextFocusableComponent(jTab);
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
        jPEnca.add(jTNoSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 120, 20));

        jLabel8.setText("Estado:");
        jPEnca.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 80, -1));

        jTTipDoc.setEditable(false);
        jTTipDoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTipDoc.setNextFocusableComponent(jTab);
        jTTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTipDocFocusLost(evt);
            }
        });
        jTTipDoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTipDocKeyPressed(evt);
            }
        });
        jPEnca.add(jTTipDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 130, 20));

        jP1.add(jPEnca, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 790, 110));

        jTSubTot.setEditable(false);
        jTSubTot.setBackground(new java.awt.Color(204, 255, 204));
        jTSubTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTSubTot.setForeground(new java.awt.Color(51, 51, 0));
        jTSubTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTSubTot.setText("$0.00");
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
        jP1.add(jTSubTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 390, 160, 30));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel26.setText("Sub Total:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 400, 110, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setText("Impuesto:");
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 430, 110, -1));

        jTImp.setEditable(false);
        jTImp.setBackground(new java.awt.Color(204, 255, 204));
        jTImp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTImp.setForeground(new java.awt.Color(51, 51, 0));
        jTImp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTImp.setText("$0.00");
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
        jP1.add(jTImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 420, 160, 30));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel28.setText("Total:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 460, 110, -1));

        jTTot.setEditable(false);
        jTTot.setBackground(new java.awt.Color(204, 255, 204));
        jTTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTTot.setForeground(new java.awt.Color(51, 51, 0));
        jTTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTot.setText("$0.00");
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
        jP1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 450, 160, 30));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar");
        jBGuar.setNextFocusableComponent(jBSal);
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 120, -1));

        jLabel1.setText("*Motivo de Devolución:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 390, 130, -1));

        jTMot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMot.setNextFocusableComponent(jTab);
        jTMot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMotFocusLost(evt);
            }
        });
        jTMot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMotKeyPressed(evt);
            }
        });
        jP1.add(jTMot, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 390, 410, 20));

        jBTabG.setBackground(new java.awt.Color(0, 153, 153));
        jBTabG.setToolTipText("Mostrar Tabla en Grande");
        jBTabG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTabGActionPerformed(evt);
            }
        });
        jBTabG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTabGKeyPressed(evt);
            }
        });
        jP1.add(jBTabG, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 480, 160, -1));

        jLNot.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLNot.setForeground(new java.awt.Color(204, 0, 0));
        jLNot.setText("DEVOLUCIÓN PARCIAL VENTA");
        jLNot.setFocusable(false);
        jP1.add(jLNot, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 790, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 1128, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de salir*/
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
        
           
    /*Cuando se gana el foco del teclado en el campo de sub total*/
    private void jTSubTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSubTot.setSelectionStart(0);jTSubTot.setSelectionEnd(jTSubTot.getText().length()); 
        
    }//GEN-LAST:event_jTSubTotFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de IVA*/
    private void jTImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTImp.setSelectionStart(0);jTImp.setSelectionEnd(jTImp.getText().length()); 
        
    }//GEN-LAST:event_jTImpFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de total*/
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

    
    /*Cuando se presiona una tecla en el campo de total*/
    private void jTTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTotKeyPressed
   
    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
                        
        /*Recorre la tabla de partidas*/
        boolean bSi   = false;
        /*Contiene el subtotal y el impuesto*/
        String sSubTot  = "0";
        String sImpue   = "0";
        String cSubTotal   = "0";
        String cImpue   = "0";
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Obtiene la cantidad de devolución*/
            String sCantDev         = jTab.getValueAt(x, 13).toString();
            /*Si la cant de devolución es cadena vacia en entonces*/
            if(sCantDev.compareTo("")==0)
                continue;
            
            /*Si la cant a devolución es igual o menor a cero entonces*/
            if(Integer.parseInt(sCantDev) <= 0)
                continue;

            /*Pon la bandera en true para saber que si hay cambios para devolución*/
            bSi   = true;            
        }
        
        /*Si no hay cambios para devolución entonces*/
        if(!bSi)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No hay cantidad para devolución.", "Cantidad de devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }

        /*Si el motivo de la devolución esta en cadena vacia entonces*/
        if(jTMot.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTMot.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has indicado un motivo de devolución.", "Motivo devolucion", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control y regresa*/
            jTMot.grabFocus();                            
            return;
        }
                
        /*Recorre toda la tabla de partidas para válidar que las cantidades que se van a devolver sean correctas*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Obtiene la cantidad original de factura*/
            String sCant           = jTab.getValueAt(x, 2).toString();
            
            /*Obtiene las cantidades anteriormente devueltas*/
            String sCantsDevs      = jTab.getValueAt(x, 12).toString();
            
            /*Obtiene la cantidad de devolución*/
            String sCantDev        = jTab.getValueAt(x, 13).toString();
            /*Si la cantidad de devolución es cadena vacia entonces que sea 0*/
            if(sCantDev.compareTo("")==0)
                sCantDev            = "0";
            
            /*Genera la cantidad real límite que se puede devolver*/
            String sCantRea         = Integer.toString(Integer.parseInt(sCant) - Integer.parseInt(sCantsDevs));
            
            /*Si la cantidad de devolución es mayor a la cantidad real posible de devolución entonces*/
            if(Integer.parseInt(sCantDev)> Integer.parseInt(sCantRea))
            {                
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cantidad a devolver es mayor a la cantidad posible de devolución.", "Cantidad de Devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));    
                return;
            }            
        }                
                
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres hacer la devolución parcial?", "Devolución Parcial", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                    
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Declara variables de lajTabdatos*/
        Statement   st;                
        String      sQ; 
        
        /*Actualiza en la base de datos para esta venta y saber los que se están devolviendo*/
        try 
        {                
            sQ = "UPDATE partvta SET "
                    + "entrenow     = 0 "
                    + "WHERE vta    = " + jTVta.getText().trim(); 
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procet sa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                            
        }
        
        /*Recorre toda la tabla de partidas*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Para saber si es un kit o no*/
            String sKit = "0";
            
            //Declara variables locales            
            String sId      = "";
            String sCant    = "";

            /*Para saber si es un servicio o no*/
            String sServ    = "";
            
            /*Contiene la unidad del producto*/
            String sUnid    = "";
            
            /*Contiene el lote y el pedimento*/
            String sLot     = "";
            String sPedimen = "";
            
            /*Contiene el producto*/
            String sProd    = "";
            
            /*Contiene el almacén*/
            String sAlma    = "";
            
            /*Contiene la fecha de caducidad*/
            String sFCadu   = "";
            
            /*Contiene el costo del producto*/
            String sCost    = "";
            
            /*Obtiene algunos datos de la partida*/
            ResultSet rs;
            try
            {                  
                sQ = "SELECT partvta.COST, partvta.FCADU, partvta.ALMA, partvta.PROD, partvta.LOT, partvta.PEDIMEN, partvta.UNID, servi, cant, eskit, partvta.ID_ID FROM partvta LEFT OUTER JOIN prods ON prods.PROD = partvta.PROD WHERE partvta.ID_ID = " + jTab.getValueAt(x, 20).toString().trim();            
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())            
                {
                    sKit    = rs.getString("eskit");                    
                    sId     = rs.getString("id_id");
                    sCant   = rs.getString("cant");
                    sServ   = rs.getString("servi");
                    sUnid   = rs.getString("unid");
                    sLot    = rs.getString("lot");
                    sPedimen= rs.getString("pedimen");
                    sProd   = rs.getString("prod");
                    sAlma   = rs.getString("alma");
                    sFCadu  = rs.getString("fcadu");
                    sCost   = rs.getString("cost");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                    
            }
            
//            //Obtiene los devueltos
//            String sDevs    = jTab.getValueAt(x, 13).toString().trim();
//            if(sDevs.compareTo("")==0)
//                sDevs       = "0";
//            /*Actualiza en la base de datos para esta venta y saber los que se están devolviendo*/
//            try 
//            {                
//                sQ = "UPDATE partvta SET "
//                        + "entrenow     =  " + sDevs + ", "
//                        + "devs         =  devs + " + sDevs + " "
//                        + "WHERE vta    = " + jTVta.getText().trim() + " AND id_id = " + jTab.getValueAt(x, 20).toString().trim();                                                                    
//                st = con.createStatement();
//                st.executeUpdate(sQ);
//             }
//             catch(SQLException expnSQL) 
//             { 
//                //Procesa el error y regresa
//                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
//                return;                                                                                    
//            }

            /*Si es un servicio entonces continua*/
            if(sServ.compareTo("1")==0)
                continue;
            
            /*Si el producto es un kit entonces*/
            if(sKit.compareTo("1")==0)
            {
                /*Función para devolver los componentes de un kit*/
                if(Star.iDevKit(con, sId, sCant)==-1)
                    return;
                
                /*Continua*/
                continue;
            }

            /*Obtiene la cantidad correcta dependiendo de su unidad*/
            String sCantRea     = Star.sCantUnid(sUnid, sCant);
            /*Ingresa el costeo*/
            if(Star.iInsCost(con, sProd, sCant, sCost)==-1)
                return;                             

            /*Si tiene lote o pedimento entonces*/
            if(sLot.compareTo("")!=0 || sPedimen.compareTo("")!=0)
            {
                /*Procesa esta parte para devolver las cantidad*/
                if(Star.sLotPed(con, sProd, sCantRea, sAlma, sLot, sPedimen, sFCadu, "+")==null)
                    return;
            }                
            
            /*Si la cantidad de devolución es cadena vacia entonces que sea 0*/
            if(sCantRea.compareTo("")==0)
                sCantRea       = "0";
            
            /*Realiza la afectación correspondiente al almacén para la entrada*/
            if(Star.iAfecExisProd(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim(), jTab.getValueAt(x, 4).toString().replace("'", "''").trim(), sCantRea.trim(), "+")==-1)
                return;

            /*Actualiza la existencia general del producto*/
            if(Star.iCalcGralExis(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim())==-1)
                return;

            /*Si tene talla o color entonces procesa las tallas y colores*/
            if(jTab.getValueAt(x, 14).toString().compareTo("")!=0 || jTab.getValueAt(x, 15).toString().compareTo("")!=0)                           
                Star.vTallCol(con, sCant, jTab.getValueAt(x, 4).toString(), jTab.getValueAt(x, 14).toString(), jTab.getValueAt(x, 15).toString(), jTab.getValueAt(x, 1).toString(), "+");            
            
            /*Actualiza la partida en la base de datos para saber los que se devolvierón*/
            if(Star.iActuaDev(con, jTab.getValueAt(x, 20).toString(), jTab.getValueAt(x, 13).toString().trim())==-1)
                return;
            
            /*Devuelve los costeos correspondientes*/
            if(Star.iDevCost(con, sId)==-1)
                return;
                                    
            /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
            if(!Star.vRegMoniInv(con, jTab.getModel().getValueAt(x, 1).toString().replace("'", "''") , sCantRea, jTab.getModel().getValueAt(x, 6).toString().replace("'", "''"), jTab.getValueAt(x, 4).toString().replace("'", "''"), Login.sUsrG , jTNoSer.getText().replace("'", "''").trim() +   jTNoDoc.getText().replace("'", "''").trim(), "DEVP", jTab.getValueAt(x, 3).toString().replace("'", "''"), jTNoSer.getText().trim(), jTCli.getText().replace("'", "''"), "0"))                                
                return;                                                                                                                                                                                                             
                        
        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
        
        /*Contiene el resultado de si ya todo se devolvió o no*/
        String sDev = "";
        /*Comprueba si ya todo se devolvió*/
        ResultSet   rs;
        try
        {                  
            sQ = "SELECT CASE WHEN SUM(cant) = SUM(devs) THEN 1 ELSE 0 END AS esta FROM partvta WHERE vta = " + sVtaGlob;            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())            
                sDev    = rs.getString("esta");
        }
        catch(SQLException | NumberFormatException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }
        
        /*Crea la consulta para el estado de la venta depeniendo si ya se devolvió todo o no*/
        String sEstad   = "DEVP";
        if(sDev.compareTo("1")==0)
            sEstad      = "DEV";
        
        /*Actualiza el estado de la venta*/
        try 
        {            
            sQ = "UPDATE vtas SET "
                    + "estad        = '" + sEstad + "', "
                    + "motiv        = '" + jTMot.getText().replace("'", "''").trim() + "', "
                    + "fmod         = now(), "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE vta    = '" + jTVta.getText() + "'";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                            
        }
        
        /*Recorre la tabla de partidas para sacar el proporcional del abono de CXC*/                
        int x;
        for(x = 0; x < jTab.getRowCount(); x++)
        {
            /*Si no hay algo que se va a devolver entonces continua*/
            if(jTab.getValueAt(x, 13).toString().compareTo("")==0)
                continue;
            
            /*Ve sumando el subtotal*/
            sSubTot = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(jTab.getValueAt(x, 11).toString().replace("$", "").replace(",", "")));
            
            /*Ve sumando el impuesto*/
            sImpue  = Double.toString(Double.parseDouble(sImpue) + (Double.parseDouble(jTab.getValueAt(x, 11).toString().replace("$", "").replace(",", "")) * (Double.parseDouble(jTab.getValueAt(x, 9).toString())) / 100));
        
            cSubTotal = Double.toString(Double.parseDouble(cSubTotal) + (Double.parseDouble(jTab.getValueAt(x, 7).toString().replace("$", "").replace(",", "")) * (Double.parseDouble(jTab.getValueAt(x, 13).toString())))) ;
        
            cImpue =Double.toString(Double.parseDouble(cImpue) + (Double.parseDouble(jTab.getValueAt(x, 7).toString().replace("$", "").replace(",", "")) * (Double.parseDouble(jTab.getValueAt(x, 13).toString())))* (Double.parseDouble(jTab.getValueAt(x, 9).toString())) / 100) ;
        }
        
        /*Crea el total*/
        String sTot     = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImpue));        
        String cTotal   = Double.toString(Double.parseDouble(cSubTotal) + Double.parseDouble(cImpue));
        /*Obtiene la serie el cliente*/
        String sSerCli  = "";
        try
        {
            sQ = "SELECT ser FROM emps WHERE CONCAT_WS('', ser,codemp) = '" + jTCli.getText().trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sSerCli = rs.getString("ser");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                            
        }         
        
        /*Contiene la fecha de vencimiento y la fecha del documento*/
        String sFVenc   = "";
        String sFDoc    = "";        
        
        /*Obtiene algunos datos de la venta*/
        try
        {
            sQ = "SELECT fvenc, femi FROM vtas WHERE vta = '" + jTVta.getText().trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                System.out.println("yep");
                sFVenc  = rs.getString("fvenc");
                sFDoc   = rs.getString("femi");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                            
        }         
        
        /*Si no viene del punto de venta entonces*/
        if(!bPtoVta)
        {
            //Inserta el abono en CXC de la base de datos            
            if(Star.iInsCXCP(con, "cxc", jTNoDoc.getText().trim(), jTNoSer.getText().trim(), jTCli.getText().trim(), sSerCli, sSubTot, cImpue, cTotal, "0", cTotal, "'" + sFVenc + "'", "'" + sFDoc + "'", "DEVP FAC", "", "0", "", "","")==-1)
                return;                                   
        }
                
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si no hay datos entonces solo mensajea*/
        if(sCarp.compareTo("")==0)                
            JOptionPane.showMessageDialog(null, "No se guardara el formato de devolución parcial en PDF ya que no se a definido la carpeta compartidad de la aplicación.", "Formato Devolución Parcial", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
            
        /*Obtiene algunos datos de la venta*/
        String sFol    = "";
        String sNoSer  = "";
        try
        {
            sQ  = "SELECT norefer, noser FROM vtas WHERE vta = " + jTVta.getText();
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sFol          = rs.getString("norefer");            
                sNoSer        = rs.getString("noser");                            
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

        /*Función para cargar todas las ventas en la tabla de enzabezados del otro formulario*/
        if(jLab!=null && jTabFac!=null)
        {
            (new Thread()
            {
                @Override
                public void run()
                {
                    Star.vCargVtas("", jLab, jTabFac, null);
                }
                
            }).start();
        }            
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Exito en la devolución parcial de la venta: " + jTVta.getText() + ".", "Devolución Parcial Exitosa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
        /*Obtiene la ruta completa hacia el logo*/
        String sRutLog          = sCarp + "\\imgs\\Logotipo Empresa\\" + Login.sCodEmpBD + "\\Logo.jpg";
        
        /*Si no existe un logo para la empresa entonces será el logo por default del sistema*/        
        if(!new File(sRutLog).exists())
            sRutLog             = getClass().getResource(Star.sIconDef).toString();
        
        /*Si la ruta compartida de la aplicación esta definida entonces*/
        if(sCarp.compareTo("")!=0)
        {
            /*Si la ruta a las devoluciones parciales no existe entonces crea la ruta*/
            sCarp = sCarp + "\\Devoluciones Parciales";
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();
            
            /*Si la ruta a la empresa no existe entonces crea la ruta*/
            sCarp      += "\\" + Login.sCodEmpBD;
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();
            
            /*Completa la ruta hacia el PDF*/
            sCarp   += "\\" + sNoSer + "-" + sFol + "-" + System.currentTimeMillis() + ".pdf";
        }
        
        /*Declara variables final para el thread*/
        final String sEmpFi     = jTCli.getText();
        final String sVtaFi     = jTVta.getText();
        final String sRutLogFi  = sRutLog;
        final String sCarpFi    = sCarp;
       
        /*Crea el thread para mostrar el formato de entrega*/        
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

                /*Muestra el formulario*/
                try
                {
                    /*Crea los parámetros que se pasarán*/
                    Map <String,String> pa = new HashMap<>();
                    pa.clear();
                    pa.put(Star.sSerMostG,  sEmpFi);
                    pa.put("VTA",           sVtaFi);                    
                    pa.put("LOG",           sRutLogFi);
                    pa.put("LOGE",          Star.class.getResource(Star.sIconDef).toString());

                    /*Compila el reporte y maximizado*/
                    JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptVtaDevp.jrxml"));
                    JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                    /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte Punto de Ventas");
                    v.setIconImage(newimg);
                    v.setVisible(true);

                    /*Exportalo a PDF*/
                    JasperExportManager.exportReportToPdfFile(pr, sCarpFi);                                                                

                    //Esconde la forma de loading
                    Star.vOcultLoadin();
                }
                catch(JRException expnJASR)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                    return;                                                                                        
                }

                //Cierra la base de datos
                Star.iCierrBas(con);
                
            }/*Fin de public void run()*/
            
        }).start();

        //Muestra el loading
        Star.vMostLoading("");

        /*Llama al recolector de basura*/
        System.gc();
        
        /*Sal del formulario*/
        this.dispose();
        
    }//GEN-LAST:event_jBGuarActionPerformed
                   

    /*Cuando se pierde el foco del teclado en el campo del motiv de devolución*/
    private void jTMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMotFocusLost

        /*Coloca el cursor al principio del control*/
        jTMot.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTMot.getText().compareTo("")!=0)
            jTMot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTMotFocusLost

    
    /*Cuando se mueve la rueda del ratón en el diálogo*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se arrasta el ratón en el diálogo*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve el ratón en el diálogo*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se presiona el botón de ver la tabla en grande*/
    private void jBTabGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTabGActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab);       

    }//GEN-LAST:event_jBTabGActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver la tabla en la graden*/
    private void jBTabGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTabGKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTabGKeyPressed

    
    /*Cuando se presiona una tecla en la tabla*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se presiona una tecla en el campo del motiv de devoución*/
    private void jTMotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTMotKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del motiv*/
    private void jTMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMot.setSelectionStart(0);jTMot.setSelectionEnd(jTMot.getText().length());                        
        
    }//GEN-LAST:event_jTMotFocusGained

    
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
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
     
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se presiona una tecla en el panel del encabezado*/
    private void jPEncaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPEncaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPEncaKeyPressed

    
    /*Cuando se pierde el foco del teclado en el capo del código del cliente*/
    private void jTCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTCli.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCliFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del folio*/
    private void jTNoDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoDocFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTNoDoc.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNoDocFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la serie de la venta*/
    private void jTNoSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoSerFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTNoSer.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNoSerFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del nombre del cliente*/
    private void jTNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomEmpFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTNomEmp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomEmpFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la fecha*/
    private void jTFecFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFecFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTFec.setCaretPosition(0);
        
    }//GEN-LAST:event_jTFecFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del tipo de documento*/
    private void jTTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTipDocFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTTipDoc.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTipDocFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la venta*/
    private void jTVtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTVtaFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTVta.setCaretPosition(0);
        
    }//GEN-LAST:event_jTVtaFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del estado*/
    private void jTEstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTEst.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEstFocusLost

    
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo del subtotal*/
    private void jTSubTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTSubTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSubTotFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del impuesto*/
    private void jTImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusLost
 
        /*Coloca el cursor al principio del control*/
        jTImp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTImpFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del total*/
    private void jTTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTotFocusLost

    
    /*Cuando se presiona una tecla en el campo del cliente*/
    private void jTCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCliKeyPressed

    
    /*Cuando se presiona una tecla en el campo del número de documento*/
    private void jTNoDocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoDocKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoDocKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la serie*/
    private void jTNoSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoSerKeyPressed

    
    /*Cuando se presiona una tecla en el campo del nombre*/
    private void jTNomEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomEmpKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la fecha*/
    private void jTFecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFecKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFecKeyPressed

    
    /*Cuando se presiona una tecla en el campo del tipo de documento*/
    private void jTTipDocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTipDocKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTipDocKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la venta*/
    private void jTVtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTVtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTVtaKeyPressed

    
    /*Cuando se presiona una tecla en el campo del estado*/
    private void jTEstKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del número de documento*/
    private void jTNoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTNoDocActionPerformed
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoDoc.setSelectionStart(0);jTNoDoc.setSelectionEnd(jTNoDoc.getText().length());                        
        
    }//GEN-LAST:event_jTNoDocActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo del cliente*/
    private void jTCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCli.setSelectionStart(0);jTCli.setSelectionEnd(jTCli.getText().length());                        
        
    }//GEN-LAST:event_jTCliFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del número de documento*/
    private void jTNoDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoDocFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoDoc.setSelectionStart(0);jTNoDoc.setSelectionEnd(jTNoDoc.getText().length());                        
        
    }//GEN-LAST:event_jTNoDocFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del número de serie*/
    private void jTNoSerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoSerFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoSer.setSelectionStart(0);jTNoSer.setSelectionEnd(jTNoSer.getText().length());                        
        
    }//GEN-LAST:event_jTNoSerFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del nombre*/
    private void jTNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomEmpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNomEmp.setSelectionStart(0);jTNomEmp.setSelectionEnd(jTNomEmp.getText().length());                        
        
    }//GEN-LAST:event_jTNomEmpFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la fecha*/
    private void jTFecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFecFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFec.setSelectionStart(0);jTFec.setSelectionEnd(jTFec.getText().length());                        
        
    }//GEN-LAST:event_jTFecFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la venta*/
    private void jTVtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTVtaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTVta.setSelectionStart(0);jTVta.setSelectionEnd(jTVta.getText().length());                        
        
    }//GEN-LAST:event_jTVtaFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del estado*/
    private void jTEstFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEst.setSelectionStart(0);jTEst.setSelectionEnd(jTEst.getText().length());                        
        
    }//GEN-LAST:event_jTEstFocusGained

    
    
    
       
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de cerrar*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
            
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTabG;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLNot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPEnca;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTCli;
    private javax.swing.JTextField jTEst;
    private javax.swing.JTextField jTFec;
    private javax.swing.JTextField jTImp;
    private javax.swing.JTextField jTMot;
    private javax.swing.JTextField jTNoDoc;
    private javax.swing.JTextField jTNoSer;
    private javax.swing.JTextField jTNomEmp;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTTipDoc;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTextField jTVta;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaDevPFac extends javax.swing.JFrame */
