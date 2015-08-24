//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;




/*Clase para devolucion parcial de una compra*/
public class DevPCom extends javax.swing.JFrame
{        
    
    /*Declara variables de instancia*/
    private int             iContFi;
    private String          sCodComGlo;
    private JTable          jTabComp;    

    /*Contador para modificar tabla*/
    private int             iContCellEd;
    
    /*Declara variables originales*/
    private String          sProdOri;
    private String          sCantOri;
    private String          sUnidOri;
    private String          sAlmaOri;
    private String          sDescripOri;
    private String          sCostOri;
    private String          sDescOri;
    private String          sDescAdOri;
    private String          sImpueOri;
    private String          sMonOri;
    private String          sImpoOri;
    private String          sDevsOri;
    private String          sLotOri;
    private String          sPedimenOri;
    private String          sCaduOri;
    private String          sKitOri;
    
    
    
    /*Consructor con argumento*/
    public DevPCom(String sCodComp, JTable jTableCom) 
    {        
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        /*Esconde la columna del ID*/
        jTab.getColumnModel().getColumn(17).setMinWidth(0);
        jTab.getColumnModel().getColumn(17).setMaxWidth(0);
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Que las comlumnas de la tabla no se muevan*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Obtiene la referencia de la tabla de comprs del otro formulario*/
        jTabComp           = jTableCom;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Obtiene el código de la compra del otro formulario*/
        sCodComGlo          = sCodComp;
                
        /*Inicia el contador de filas de las partidas*/
        iContFi             = 1;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Devolución parcial de compra, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el tamaño de la última columna de la tabla de partidas*/
        jTab.getColumnModel().getColumn(1).setPreferredWidth(160);               
        jTab.getColumnModel().getColumn(5).setPreferredWidth(500);        
        jTab.getColumnModel().getColumn(8).setPreferredWidth(160);        
        jTab.getColumnModel().getColumn(12).setPreferredWidth(100);        
        jTab.getColumnModel().getColumn(13).setPreferredWidth(160);        
        jTab.getColumnModel().getColumn(15).setPreferredWidth(170);        
        jTab.getColumnModel().getColumn(16).setPreferredWidth(170);        
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Pon el foco del teclado en el campo del código del proveedor*/
        jTProv.grabFocus();
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los datos de la compra de la base de datos*/        
        try
        {                  
            sQ = "SELECT comprs.NOSER, comprs.OBSERV, comprs.MOTIV, comprs.ESTADO, comprs.PROV,  comprs.NODOC, comprs.FDOC, comprs.SUBTOT, provs.NOM, comprs.IMPUE, comprs.TOT FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV ) = comprs.PROV WHERE comprs.CODCOMP = '" + sCodComGlo + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene el subtotal*/                
                String sSubTot  = rs.getString("comprs.subtot");      
                
                /*Dale formato de moneda al subtot*/                
                double dCant    = Double.parseDouble(sSubTot);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sSubTot         = n.format(dCant);
        
                /*Obtiene el impuesto*/                
                String sImpue   = rs.getString("comprs.impue");      
                
                /*Dale formato de moneda al impue*/                
                dCant           = Double.parseDouble(sImpue);                
                n               = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sImpue          = n.format(dCant);
                
                /*Obtiene el total*/                
                String sTot     = rs.getString("comprs.tot");      
                
                /*Dale formato de moneda al total*/                
                dCant           = Double.parseDouble(sTot);                
                n               = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sTot            = n.format(dCant);
                
                /*Coloca todos los valores obtenidos en sus campos correspondientes*/
                jTProv.setText          (rs.getString("comprs.PROV"));
                jTNomProv.setText       (rs.getString("provs.NOM"));
                jTNoDoc.setText         (rs.getString("comprs.NODOC"));
                jTFec.setText           (rs.getString("comprs.FDOC"));                
                jTEst.setText           (rs.getString("comprs.ESTADO"));
                jTMot.setText           (rs.getString("comprs.MOTIV"));
                jTObserv.setText        (rs.getString("comprs.OBSERV"));
                jTCodCom.setText        (sCodComGlo);
                jTSubTot.setText        (sSubTot);
                jTImp.setText           (sImpue);
                jTTot.setText           (sTot);   
                jTNoSer.setText         (rs.getString("comprs.NOSER"));
                
                /*Si el estado es devolución parcial entonces el color de la letra del campo será roja*/
                if(rs.getString("comprs.ESTADO").compareTo("DEVP")==0)
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
        
        /*Obtiene todas las partidas de la compra de la base de datos*/
        try
        {                  
            sQ = "SELECT * FROM partcomprs WHERE codcom = '" + sCodComGlo + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Si es un componente entonces continua para que no lo cargue*/
                if(rs.getString("kitmae").compareTo("1")==0)
                    continue;
                
                /*Obtiene el costo*/
                String sCost        = rs.getString("cost");   
                
                /*Dale formato de moneda al costo*/                
                double dCant        = Double.parseDouble(sCost);                
                NumberFormat n      = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sCost               = n.format(dCant);
                
                String Impue        =rs.getString("impue");
                /*Obtiene el importe*/
                String sImp         = rs.getString("impo");   
                
                /*Dale formato de moneda al importe*/                
                dCant               = Double.parseDouble(sImp);                
                n                   = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sImp                = n.format(dCant);
                
                /*Determina si es un kit o no*/
                String sKit         = "No";
                if(rs.getString("eskit").compareTo("1")==0)
                    sKit            = "Si";
                
                /*Agrega todos los datos obtenidos en la tabla de partidas*/
                DefaultTableModel tm= (DefaultTableModel)jTab.getModel();
                Object nu[]         = {iContFi, rs.getString("prod"), rs.getString("cant"), rs.getString("unid"), rs.getString("alma"), rs.getString("descrip"), sCost, rs.getString("descu"), rs.getString("descad"), rs.getString("codimpue"), rs.getString("mon"), Impue, rs.getString("devs"), "", rs.getString("lot"), rs.getString("pedimen"), rs.getString("flotvenc"), rs.getString("id_id"), sKit};        
                tm.addRow(nu);
                
                /*Aumenta en uno el contador de filas*/
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
        if(Star.iCierrBas(con)==-1)
            return;

        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        //inicializa el valor 
        for(int x=0;x<jTab.getRowCount();x++)
        {
                        jTab.setValueAt(0,x,13);
        }
        
        /*Crea el listener para la tabla de partidas, para cuando se modifique la celda de cantidad de devolución*/
        PropertyChangeListener pr = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces regresa*/                
                if(jTab.getSelectedRow()==-1)
                    return;
                    
                /*Obtén la propiedad que a sucedio en el control*/
                String pro = event.getPropertyName();                                
                                                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pro)) 
                {           
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sProdOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sCantOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sUnidOri        = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sAlmaOri        = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sDescripOri     = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sCostOri        = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sDescOri        = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sDescAdOri      = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sImpueOri       = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sMonOri         = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sImpoOri        = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        sDevsOri        = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();
                        sLotOri         = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();
                        sPedimenOri     = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();
                        sCaduOri        = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();
                        sKitOri         = jTab.getValueAt(jTab.getSelectedRow(), 18).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sProdOri,       jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sCantOri,       jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sUnidOri,       jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sAlmaOri,       jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sDescripOri,    jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sCostOri,       jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sDescOri,       jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sDescAdOri,     jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sImpueOri,      jTab.getSelectedRow(), 9);                                                
                        jTab.setValueAt(sMonOri,        jTab.getSelectedRow(), 10);                       
                        jTab.setValueAt(sImpoOri,       jTab.getSelectedRow(), 11);                        
                        jTab.setValueAt(sDevsOri,       jTab.getSelectedRow(), 12);                        
                        jTab.setValueAt(sLotOri,        jTab.getSelectedRow(), 14);                        
                        jTab.setValueAt(sPedimenOri,    jTab.getSelectedRow(), 15);                        
                        jTab.setValueAt(sCaduOri,       jTab.getSelectedRow(), 16);
                        jTab.setValueAt(sKitOri,        jTab.getSelectedRow(), 18);
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                                                                    
                    
                    /*Obtén la cantidad de devolución que el usuario ingreso*/
                    String sCantDev             = jTab.getModel().getValueAt(jTab.getSelectedRow(), 13).toString();                    
                                        
                    /*Si la cantidad de devolución introducida no es númerica entonces*/
                    try
                    {
                        Double.parseDouble(sCantDev);
                    }
                    catch(NumberFormatException expnNumForm)
                    {                        
                        /*Colocar cadena vacia en la cantidad de devolución y regresa*/
                        jTab.getModel().setValueAt(0, jTab.getSelectedRow(), 13);                                       
                        return;                                                
                    }          
                                     
                    /*Convierte a valor absoluto el número introducido, para quitar el negativo en caso de que lo tenga*/
                    sCantDev         = Double.toString((double)Math.abs(Double.parseDouble(sCantDev)));                    
                    
//                    /*Si la cantidad es igual a 0 entonces*/
//                    if(Double.parseDouble(sCantDev)==0)
//                    {
//                        /*Coloca cadena vacia en la cantidad de devolución y regresa*/
//                        jTab.getModel().setValueAt("", jTab.getSelectedRow(), 13);                                                                    
//                        return;
//                    }
                    
                    /*Coloca el valor absoluto en la cantidad de devolución*/
                    jTab.getModel().setValueAt(sCantDev, jTab.getSelectedRow(), 13);                    
                    
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
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTNoDoc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTCodCom = new javax.swing.JTextField();
        jTNomProv = new javax.swing.JTextField();
        jTProv = new javax.swing.JTextField();
        jTFec = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTEst = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTObserv = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTNoSer = new javax.swing.JTextField();
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 120, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Producto", "Cantidad", "Unidad", "Almacén", "Descripción", "Costo", "Descuento", "Desc. Adicional", "Impuesto", "Moneda", "Importe", "Devueltos", "Cant. Devolución", "Lote", "Pedimento", "Caducidad", "ID", "Es Kit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true
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

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Cod. Proveedor:");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 110, -1));

        jLabel6.setText("Cod. Proveedor:");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, -1));

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
        jTNoDoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNoDocKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTNoDocKeyReleased(evt);
            }
        });
        jPanel4.add(jTNoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 120, 20));

        jLabel3.setText("Nombre Proveedor:");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 110, -1));

        jLabel2.setText("Fecha:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 80, -1));

        jLabel20.setText("Observaciones:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 110, -1));

        jTCodCom.setEditable(false);
        jTCodCom.setForeground(new java.awt.Color(51, 51, 255));
        jTCodCom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodCom.setNextFocusableComponent(jTab);
        jTCodCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodComFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodComFocusLost(evt);
            }
        });
        jTCodCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodComKeyPressed(evt);
            }
        });
        jPanel4.add(jTCodCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 40, 160, 20));

        jTNomProv.setEditable(false);
        jTNomProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNomProv.setNextFocusableComponent(jTab);
        jTNomProv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNomProvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNomProvFocusLost(evt);
            }
        });
        jTNomProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNomProvKeyPressed(evt);
            }
        });
        jPanel4.add(jTNomProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 380, 20));

        jTProv.setEditable(false);
        jTProv.setBackground(new java.awt.Color(204, 255, 204));
        jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProv.setNextFocusableComponent(jTab);
        jTProv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProvFocusLost(evt);
            }
        });
        jTProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProvKeyPressed(evt);
            }
        });
        jPanel4.add(jTProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 120, 20));

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
        jPanel4.add(jTFec, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 130, 20));

        jLabel5.setText("Cod. Compra:");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 80, -1));

        jTEst.setEditable(false);
        jTEst.setHorizontalAlignment(javax.swing.JTextField.LEFT);
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
        jPanel4.add(jTEst, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 90, 20));

        jLabel7.setText("Serie:");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 80, -1));

        jLabel21.setText("No. Documento:");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 110, -1));

        jTObserv.setEditable(false);
        jTObserv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTObserv.setNextFocusableComponent(jTab);
        jTObserv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTObservFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTObservFocusLost(evt);
            }
        });
        jTObserv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTObservKeyPressed(evt);
            }
        });
        jPanel4.add(jTObserv, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 380, 20));

        jLabel8.setText("Estado:");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 80, -1));

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
        jPanel4.add(jTNoSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 160, 20));

        jP1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 790, 110));

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
        jTImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTImpActionPerformed(evt);
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 120, 30));

        jLabel1.setText("*Motivo de Devolución:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 390, 130, -1));

        jTMot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMot.setNextFocusableComponent(jTProv);
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
        jLNot.setText("DEVOLUCIÓN PARCIAL COMPRA");
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

    
    /*Cuando se presiona una tecla en el campo de edición de nom del cliente*/
    private void jTNoDocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoDocKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoDocKeyPressed

    
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

    
    /*Cuando se gana el foco del teclado en el campo de número de documento*/
    private void jTNoDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoDocFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoDoc.setSelectionStart(0);jTNoDoc.setSelectionEnd(jTNoDoc.getText().length());                        
        
    }//GEN-LAST:event_jTNoDocFocusGained

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de edición de nom*/
    private void jTNoDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoDocFocusLost

        /*Coloca el cursor al principio del control*/
        jTNoDoc.setCaretPosition(0);
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTNoDoc.getText().length()> 50)
            jTNoDoc.setText(jTNoDoc.getText().substring(0, 50));
        
    }//GEN-LAST:event_jTNoDocFocusLost

    
    /*Cuando se levanta una tecla en el campo de nom de empresa*/
    private void jTNoDocKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoDocKeyReleased
        
        //Declara variables locales
        String sTex;
        
        
        /*Lee el texto introducido por el usuario*/
        sTex = jTNoDoc.getText();
        
        /*Conviertelo a mayúsculas*/
        sTex = sTex.toUpperCase();
        
        /*Vuelve a colocarlo*/
        jTNoDoc.setText(sTex);
        
    }//GEN-LAST:event_jTNoDocKeyReleased

    
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

    
    /*Cuando se gana el foco del teclado en el campo del código del proveedor*/
    private void jTProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTProv.setSelectionStart(0);jTProv.setSelectionEnd(jTProv.getText().length());                        
        
    }//GEN-LAST:event_jTProvFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de nom del proveedor*/
    private void jTNomProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomProvFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNomProv.setSelectionStart(0);jTNomProv.setSelectionEnd(jTNomProv.getText().length());                        
                
    }//GEN-LAST:event_jTNomProvFocusGained

    
    /*Cuando se presiona una tecla en el campo del código del proveedor*/
    private void jTProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProvKeyPressed

    
    /*Cuando se presiona una tecla en el campo de nom del proveedor*/
    private void jTNomProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomProvKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomProvKeyPressed

    
    /*Cuando se presiona una tecla en el campo de código de compra*/
    private void jTCodComKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodComKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCodComKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de código de compra*/
    private void jTCodComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodComFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCodCom.setSelectionStart(0);jTCodCom.setSelectionEnd(jTCodCom.getText().length());                        
        
    }//GEN-LAST:event_jTCodComFocusGained

    
    /*Cuando se presiona una tecla en el campo de fechaS*/
    private void jTFecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFecKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFecKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de fecha*/
    private void jTFecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFecFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFec.setSelectionStart(0);jTFec.setSelectionEnd(jTFec.getText().length());                      
        
    }//GEN-LAST:event_jTFecFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de estad*/
    private void jTEstFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEst.setSelectionStart(0);jTEst.setSelectionEnd(jTEst.getText().length());        
        
    }//GEN-LAST:event_jTEstFocusGained

    
    /*Cuando se presiona una tecla en el campo de estad*/
    private void jTEstKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstKeyPressed
        
         //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstKeyPressed

    
    /*Cuando se presiona una tecla en el botón de aceptar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
     /*Recorre la tabla de partidas*/
        boolean bSi   = false;
        /*Contiene el subtotal y el impuesto*/
        String sSubTot  = "0";
        String sImpue   = "0";
        String cSubTotal   = "0";
        String cImpue   = "0";
        String sProv    = "";
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Decflara variables locales*/
            String sCantDev;            
            
            /*Obtiene la cantidad de devolución*/
            sCantDev         = jTab.getModel().getValueAt(x, 13).toString();
            
            /*Si la cantidad de devolución es cadena vacia en entonces*/
            if(sCantDev.compareTo("")==0)
                continue;
            
            /*Si la cantidad a devolución es igual o menor a cero entonces*/
            if(Double.parseDouble(sCantDev) <= 0)
                continue;
            
            /*Pon la bandera en true para saber que si hay cambios para devolución*/
            bSi   = true;            
        }
        
        /*Si no hay cambios para devolución entonces*/
        if(!bSi)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No hay cantidad en las partidas para devolución.", "Cantidad de devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                     
            return;
        }
        
        /*Recorre toda la tabla de partidas para válidar que las cantidades que se van a devolver son correctas*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Genera la cantidad real límite que se puede devolver*/
            String sCantRea                = Double.toString(Double.parseDouble(jTab.getModel().getValueAt(x, 2).toString()) - Double.parseDouble(jTab.getModel().getValueAt(x, 12).toString()));
            
            /*Obtiene la cantidad que se quiere devovler*/
            String sCantDev                 = jTab.getModel().getValueAt(x, 13).toString();
            
            /*Si es cadena vacia entonces que sea 0*/
            if(sCantDev.compareTo("")==0)
                sCantDev                    = "0";
            
            /*Si la cantidad de devolución es mayor a la cantidad real posible de devolución entonces*/
            if(Double.parseDouble(sCantDev)> Double.parseDouble(sCantRea))
            {                
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cantidad a devolver es mayor a la cantidad posible de devolución.", "Cantidad de devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
                return;
            }            
        }                
        
        /*Si el motivo de la devolución esta en cadena vacia entonces*/
        if(jTMot.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTMot.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has indicado un motivo de devolución.", "Motivo devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control y regresa*/
            jTMot.grabFocus();                            
            return;
        }
        
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres hacer la devolución parcial?", "Devolución parcial", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                                   
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Recorre toda la tabla de partidas*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Para saber si es un kit o no*/
            String sKit     = "0";
            
            /*Para saber si es un servicio o no*/
            String sServ    = "0";
            
            //Declara variables locales            
            String sId      = "";
            String sCant    = "";
            
            /*Contiene la unidad*/
            String sUnid    = "";
            
            /*Contiene el lote, pedimento y fecha cadicidad*/
            String sLot     = "";
            String sPedimen = "";
            String sFCadu   = "";
            String sAlma    = "";
            String sProd    = "";
            
            //Declara variables de la base de datos
            Statement   st;               
            String sQ; 

            /*Obtiene algunos datos de la partida*/
            ResultSet rs;
            try
            {                  
                sQ = "SELECT partcomprs.ALMA, partcomprs.PROD, partcomprs.LOT, partcomprs.PEDIMEN, partcomprs.FLOTVENC, partcomprs.UNID, servi, cant, eskit, partcomprs.ID_ID FROM partcomprs LEFT OUTER JOIN prods ON prods.PROD = partcomprs.PROD WHERE partcomprs.ID_ID = " + jTab.getValueAt(x, 17);            
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
                    sFCadu  = rs.getString("flotvenc");
                    sProd   = rs.getString("prod");
                    sAlma   = rs.getString("alma");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                    
            }
            
            /*Si es un servicio entonces continua*/
            if(sServ.compareTo("1")==0)
                continue;
            
            /*Si el producto es un kit entonces*/
            if(sKit.compareTo("1")==0)
            {
                /*Función para devolver los componentes de un kit*/
                if(Star.iDevKitCom(con, sId, sCant)==-1)
                    return;
                
                /*Continua*/
                continue;
            }
            
            /*Obtiene la cantidad de devolución*/
            String sCantDev = jTab.getModel().getValueAt(x, 13).toString().trim();
            
            /*Si la cantidad de devolución es cadena vacia entonces que sea 0*/
            if(sCantDev.compareTo("")==0)
                sCantDev    = "0";
            
            /*Obtiene la cantidad correcta dependiendo de su unidad*/
            sCantDev        = Star.sCantUnid(sUnid, sCantDev);

            /*Dale seguimiento al costeo*/
            if(Star.sGetCost(con, sProd, sCant)==null)
                return;

            /*Si tiene lote o pedimento entonces*/
            if(sLot.compareTo("")!=0 || sPedimen.compareTo("")!=0)
            {
                /*Procesa esta parte para devolver las cantidad*/
                if(Star.sLotPed(con, sProd, sCantDev, sAlma, sLot, sPedimen, sFCadu, "-")==null)
                    return;
            }                
                        
            /*Si es 0 la cantidad de devolución entonces continua*/
            if(Double.parseDouble(sCantDev)==0)
                continue;
            
            /*Realiza la afectación correspondiente al almacén para la salida*/
            if(Star.iAfecExisProd(con, jTab.getModel().getValueAt(x, 1).toString().replace("'", "''").trim(), jTab.getModel().getValueAt(x, 4).toString().replace("'", "''"), sCantDev, "-")==-1)
                return;

            /*Actualiza la existencia general del producto*/
            if(Star.iCalcGralExis(con, jTab.getModel().getValueAt(x, 1).toString().replace("'", "''").trim())==-1)
                return;

            /*Actualiza la partida en la base de datos para saber los que se devolvierón*/
            try 
            {                
                sQ = "UPDATE partcomprs SET " 
                        + "aplic        = 1, "                        
                        + "devs         = devs + " + jTab.getModel().getValueAt(x, 13).toString() + " "
                        + "WHERE id_id  = " + jTab.getModel().getValueAt(x, 17).toString() + " AND codcom = '" + jTCodCom.getText().replace("'", "''") + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                    
             }
            /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
            if(!Star.vRegMoniInv(con, jTab.getModel().getValueAt(x, 1).toString().replace("'", "''") , sCantDev, jTab.getModel().getValueAt(x, 5).toString().replace("'", "''"), jTab.getModel().getValueAt(x, 4).toString().replace("'", "''"), Login.sUsrG , jTCodCom.getText().replace("'", "''").trim(), "DEVP COMP", jTab.getValueAt(x, 3).toString().replace("'", "''"), jTNoSer.getText().trim(), jTProv.getText().trim(), "1"))                                
                return;                                                                                                                                                                                                             
                                    
        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
        
        /*Contiene el concepto del documento*/
        String sDev = "";
        
        //Declara variables de la base de datos
        Statement   st;               
        String      sQ; 
        ResultSet   rs;
        
        /*Comprueba si ya todo se devolvió*/        
        try
        {                  
            sQ = "SELECT CASE WHEN SUM(cant) = SUM(devs) THEN 1 ELSE 0 END AS esta FROM partcomprs WHERE codcom = '" + jTCodCom.getText().trim() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())            
                sDev    = rs.getString("esta");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                            
        }
        
        /*Crea la consulta para el estado de la compra depeniendo si ya se devolvió todo o no*/
        String sEstad   = "DEVP";
        if(sDev.compareTo("1")==0)
            sEstad      = "DEV";
        
        /*Actualiza el estado de la compra para saber que es de devolución parcial*/
        try 
        {            
            sQ = "UPDATE comprs SET "
                    + "estado           = '" + sEstad + "', "
                    + "motiv            = '" + jTMot.getText().replace("'", "''") + "', "
                    + "fmod             = now(), "
                    + "sucu             = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj            = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE codcomp    = '" + jTCodCom.getText().replace("'", "''") + "'";                    
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
        

        for( int x = 0; x < jTab.getRowCount(); x++)
        {  
            /*Si no hay algo que se va a devolver entonces continua*/
            if(jTab.getValueAt(x, 13).toString().compareTo("")==0)
                continue;
            
            /*Ve sumando el subtotal*/
            //sSubTot = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(jTab.getValueAt(x, 12).toString().replace("$", "").replace(",", "")));
            
            /*Ve sumando el impuesto*/
            //sImpue  = Double.toString(Double.parseDouble(sImpue) + (Double.parseDouble(jTab.getValueAt(x, 11).toString().replace("$", "").replace(",", "")) * (Double.parseDouble(jTab.getValueAt(x, 10).toString())) / 100));
        
            cSubTotal = Double.toString(Double.parseDouble(cSubTotal) + (Double.parseDouble(jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "")) * (Double.parseDouble(jTab.getValueAt(x, 13).toString())))) ;
            System.out.println(jTab.getValueAt(x, 10).toString());
            System.out.println(jTab.getValueAt(x, 11).toString());
            System.out.println(jTab.getValueAt(x, 12).toString());
            cImpue =Double.toString(Double.parseDouble(cImpue) + (Double.parseDouble(jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "")) * (Double.parseDouble(jTab.getValueAt(x, 13).toString())))* (Double.parseDouble(jTab.getValueAt(x, 11).toString())) / 100) ;
        }
        
        /*Crea el total*/
        //String sTot     = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImpue));        
        String cTotal   = Double.toString(Double.parseDouble(cSubTotal) + Double.parseDouble(cImpue));
        
        /*Contiene la fecha de vencimiento y la fecha del documento*/
        String sFVenc   = "";
        String sFDoc    = "";        
        
        /*Obtiene algunos datos de la compras*/
        try
        {
            sQ = "SELECT prov, falt, fdoc FROM comprs WHERE codcomp = '" + jTCodCom.getText().trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sProv   = rs.getString("prov");
                sFVenc  = rs.getString("falt");
                sFDoc   = rs.getString("fdoc");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                            
        }
        
        /*Obtiene la serie el cliente*/
        String sSerCli  = "";
        try
        {
            sQ = "SELECT ser FROM provs WHERE CONCAT_WS('', ser,prov) = '" + sProv + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
            {
                sSerCli = rs.getString("ser");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                            
        }         
        
        //Inserta el abono en CXP de la base de datos     chalo       
        if(Star.iInsCXCP(con, "cxp", jTNoDoc.getText().trim(), jTNoSer.getText().trim(), sProv, sSerCli, cSubTotal, cImpue, cTotal, "0", cTotal, "'" + sFVenc + "'", "'" + sFDoc + "'", "DEV PARC", "", "0", "", "","")==-1)
            return;                                   
                
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
                
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Agrega todos los datos de la base de datos a la tabla de comprs*/
        (new Thread()
        {
            @Override
            public void run()
            {
                Star.vCargComp(jTabComp);                  
            }
            
        }).start();
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Exito en la devolución parcial de la compra: " + jTNoDoc.getText() + ".", "Devolución Parcial Exitosa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Sal del formulario*/
        this.dispose();
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuando se presiona una tecla en la tabla de partidas*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de motiv de devolución*/
    private void jTMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMot.setSelectionStart(0);jTMot.setSelectionEnd(jTMot.getText().length());        
        
    }//GEN-LAST:event_jTMotFocusGained

    
    /*Cuando se presiona una tecla en el campo de motiv de devolución*/
    private void jTMotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTMotKeyPressed
                
   
    /*Cuando se pierde el foco del teclado en el campo del motiv de la devolución*/
    private void jTMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMotFocusLost

        /*Coloca el cursor al principio del control*/
        jTMot.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTMot.getText().compareTo("")!=0)
            jTMot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTMot.getText().length()> 255)
            jTMot.setText(jTMot.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTMotFocusLost

    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando arrastra el mouse en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve el ratón en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se presiona el botón de ver tabla en grande*/
    private void jBTabGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTabGActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab);       

    }//GEN-LAST:event_jBTabGActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver tabla en grande*/
    private void jBTabGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTabGKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTabGKeyPressed

    
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

    
    /*Cuando se pierde el foco del teclado en el campo del còdigo del proveedor*/
    private void jTProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTProv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTProvFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de las observaciones*/
    private void jTObservFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTObservFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTObserv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTObservFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del nombre del proveedor*/
    private void jTNomProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomProvFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTNomProv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomProvFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la feha*/
    private void jTFecFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFecFocusLost
       
        /*Coloca el cursor al principio del control*/
        jTFec.setCaretPosition(0);
        
    }//GEN-LAST:event_jTFecFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del código de la compra*/
    private void jTCodComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodComFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTCodCom.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodComFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del estado*/
    private void jTEstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTEst.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEstFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de la serie de la compra*/
    private void jTNoSerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoSerFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoSer.setSelectionStart(0);jTNoSer.setSelectionEnd(jTNoSer.getText().length());                        
        
    }//GEN-LAST:event_jTNoSerFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del a serie de la compra*/
    private void jTNoSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoSerFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTNoSer.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNoSerFocusLost

    
    /*Cuando se presiona una tecla en el campo de la serie*/
    private void jTNoSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoSerKeyPressed

    
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

    
    /*Cuando se presiona una tecla en el campo de observaciones*/
    private void jTObservKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTObservKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTObservKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de las observaciones*/
    private void jTObservFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTObservFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTObserv.setSelectionStart(0);jTObserv.setSelectionEnd(jTObserv.getText().length());                        
        
    }//GEN-LAST:event_jTObservFocusGained

    private void jTImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTImpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTImpActionPerformed

       
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTCodCom;
    private javax.swing.JTextField jTEst;
    private javax.swing.JTextField jTFec;
    private javax.swing.JTextField jTImp;
    private javax.swing.JTextField jTMot;
    private javax.swing.JTextField jTNoDoc;
    private javax.swing.JTextField jTNoSer;
    private javax.swing.JTextField jTNomProv;
    private javax.swing.JTextField jTObserv;
    private javax.swing.JTextField jTProv;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaEmpresa extends javax.swing.JFrame */
