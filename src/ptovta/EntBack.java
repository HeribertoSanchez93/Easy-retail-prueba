//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JOptionPane;
import java.sql.Connection;
import javax.swing.ImageIcon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;



/*Clase para entregar los backorders*/
public class EntBack extends javax.swing.JFrame 
{            
    /*Declara variables de instancia*/     
    private int             iContFi;
    private String          sVtaGlob;    
    
    /*Contador para modificar tabla*/
    private int             iContCellEd;
    
    /*Variables originales de la tabla*/
    private String          sProdOri;
    private String          sCantOri;
    private String          sUnidOri;
    private String          sAlmaOri;
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
    
    
    /*Consructor con argumentos*/
    public EntBack(String sVta) 
    {                                        
        /*Inicializa los componentes gráfcos*/
        initComponents();

        /*Esconde la columna*/
        jTab.getColumnModel().getColumn(19).setMinWidth(0);
        jTab.getColumnModel().getColumn(19).setMaxWidth(0);
        
        /*Para que la tabla tenga scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Que las comlumnas de la tabla no se muevan*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Obtiene el código de la venta del otro formulario*/
        sVtaGlob       = sVta;
                
        /*Inicia el contador de filas de las partidas*/
        iContFi        = 1;
        
        /*Establece el titulo de la vtana con el usuario, la fecha y hora*/                
        this.setTitle("Entregar Backorders, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el tamaño de las columnas de la tabla de partidas*/
        jTab.getColumnModel().getColumn(1).setPreferredWidth    (130);        
        jTab.getColumnModel().getColumn(5).setPreferredWidth    (400);
        jTab.getColumnModel().getColumn(11).setPreferredWidth   (100);
        jTab.getColumnModel().getColumn(12).setPreferredWidth   (120);        
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();
             
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los datos del encabezado de la venta de la base de datos*/        
        try
        {                  
            sQ = "SELECT vtas.TIPDOC, vtas.NOSER, vtas.ESTAD, vtas.CODEMP,  vtas.NOREFER, vtas.FEMI, vtas.SUBTOT, emps.NOM, vtas.IMPUE, vtas.TOT FROM vtas LEFT JOIN emps ON CONCAT_WS('', emps.SER,emps.CODEMP) = vtas.CODEMP WHERE vtas.VTA = " + sVtaGlob;
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
                jTCli.setText        (rs.getString("vtas.CODEMP"));
                jTNom.setText        (rs.getString("emps.NOM"));
                jTNoDoc.setText         (rs.getString("vtas.NOREFER"));
                jTTipDoc.setText        (rs.getString("vtas.TIPDOC"));
                jTNoSer.setText         (rs.getString("vtas.NOSER"));
                jTFec.setText           (rs.getString("vtas.FEMI"));                
                jTEst.setText           (rs.getString("vtas.ESTAD"));               
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
        
        /*Obtiene todas las partidas de la venta de la base de datos*/
        try
        {                  
            sQ = "SELECT * FROM partvta WHERE vta = " + sVtaGlob;
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
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
                Object nu[]          = {iContFi, rs.getString("prod"), rs.getString("cant"), rs.getString("unid"), rs.getString("alma"), rs.getString("descrip"), sPre, rs.getString("descu"), sImpue, rs.getString("mon"), sImp, rs.getString("cantentre"), "", rs.getString("tall"), rs.getString("colo"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("fcadu"), rs.getString("fentre"), rs.getString("id_id")};        
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
        
        /*Crea el listener para la tabla de partidas, para cuando se modifique la celda de cantiad de entrega*/
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
                        sDescripOri     = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sCostOri        = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sDescOri        = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sImpueOri       = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sMonOri         = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sImpoOri        = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sDevsOri        = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        sTallOri        = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();
                        sColoOri        = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();                        
                        sLotOri         = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();                        
                        sPedimenOri     = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();                        
                        sCaduOri        = jTab.getValueAt(jTab.getSelectedRow(), 17).toString();                        
                        sBackOri        = jTab.getValueAt(jTab.getSelectedRow(), 18).toString();                        
                        
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
                        jTab.setValueAt(sDescripOri,    jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sCostOri,       jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sDescOri,       jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sImpueOri,      jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sMonOri,        jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sImpoOri,       jTab.getSelectedRow(), 10);                        
                        jTab.setValueAt(sDevsOri,       jTab.getSelectedRow(), 11);                        
                        jTab.setValueAt(sTallOri,       jTab.getSelectedRow(), 13);                        
                        jTab.setValueAt(sColoOri,       jTab.getSelectedRow(), 14);                        
                        jTab.setValueAt(sLotOri,        jTab.getSelectedRow(), 15);                        
                        jTab.setValueAt(sPedimenOri,    jTab.getSelectedRow(), 16);                        
                        jTab.setValueAt(sCaduOri,       jTab.getSelectedRow(), 17);                        
                        jTab.setValueAt(sBackOri,       jTab.getSelectedRow(), 18);                        
                                               
                        /*Obtén la cantidad de entrega que el usuario ingreso*/
                        String sCantEnt         = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();                    

                        /*Si la cantidad de entrega introducida no es númerica entonces*/
                        try
                        {
                            Double.parseDouble(sCantEnt);
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Colocar cadena vacia en la cantidad de entrega y regresa*/
                            jTab.getModel().setValueAt("", jTab.getSelectedRow(), 12);                                            
                            return;                                                
                        }          

                        /*Convierte a valor absoluto el número introducido, para quitar el negativo en caso de que lo tenga*/
                        sCantEnt         = Integer.toString((int)Math.abs(Double.parseDouble(sCantEnt)));                    

                        /*Si la cantidad es igual a 0 entonces*/
                        if(Integer.parseInt(sCantEnt)==0)
                        {
                            /*Coloca cadena vacia en la cantidad de entrega y regresa*/
                            jTab.getModel().setValueAt("", jTab.getSelectedRow(), 12);                                            
                            return;
                        }

                        /*Coloca el valor absoluto en la cantidad de entrega*/
                        jTab.getModel().setValueAt(sCantEnt, jTab.getSelectedRow(), 12);                    
                        
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
        jTNom = new javax.swing.JTextField();
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
        jBTabG = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jLNot = new javax.swing.JLabel();
        jCEntT = new javax.swing.JCheckBox();

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
        jBSal.setNextFocusableComponent(jCEntT);
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
                "No.", "Cod. Producto", "Cantidad", "Unidad", "Almacén", "Descripción", "Costo", "Descuento", "Impuesto", "Moneda", "Importe", "Entregados", "Cant. Entregar", "Talla", "Color", "Lote", "Pedimento", "Caducidad", "Backorder", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false
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

        jTNom.setEditable(false);
        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNom.setNextFocusableComponent(jTab);
        jTNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNomFocusLost(evt);
            }
        });
        jTNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNomKeyPressed(evt);
            }
        });
        jPEnca.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 380, 20));

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
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTipDocFocusGained(evt);
            }
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 120, 30));

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
        jLNot.setText("ENTREGAR BACKORDERS");
        jLNot.setFocusable(false);
        jP1.add(jLNot, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 790, -1));

        jCEntT.setBackground(new java.awt.Color(255, 255, 255));
        jCEntT.setSelected(true);
        jCEntT.setText("Entregar todo");
        jCEntT.setNextFocusableComponent(jTab);
        jCEntT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCEntTKeyPressed(evt);
            }
        });
        jP1.add(jCEntT, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 130, -1));

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
        Star.entBackG   = null;
        
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
   
    
    /*Cuando se presiona el botón de aceptar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        /*Si quiere entregar todo entonces*/
        if(jCEntT.isSelected())
        {
            /*Recorre toda la tabla de partidas*/
            for(int x = 0; x < jTab.getRowCount(); x++)
            {
                /*Obtiene la cantidad real de venta*/
                String sCantVta         = jTab.getValueAt(x, 2).toString();
                
                /*Obtiene la cantidad de los ya entregados*/
                String sCantEnt         = jTab.getValueAt(x,11).toString();
                
                /*Crea la cantidad correcta posible de entrega*/
                String sCantReal        = Double.toString(Double.parseDouble(sCantVta) - Double.parseDouble(sCantEnt));
                
                /*Actualiza en la fila la cantidad a entregar*/
                jTab.setValueAt(sCantReal, x, 12);
            }
        }
        /*Else no se va a entregar todo entonces*/
        else
        {
            /*Recorre la tabla de partidas*/
            boolean bSi   = false;
            for(int x = 0; x < jTab.getRowCount(); x++)
            {
                /*Obtiene la cantidad de entrega*/
                String sCantEnt         = jTab.getValueAt(x, 12).toString();

                /*Si la cantidad de entrega es cadena vacia en entonces*/
                if(sCantEnt.compareTo("")==0)
                    continue;

                /*Si la cantidad a entrega es igual o menor a cero entonces*/
                if(Double.parseDouble(sCantEnt) <= 0)
                    continue;

                /*Pon la bandera en true para saber que si hay cambios para entrega*/
                bSi   = true;            
            }

            /*Si no hay cambios para devolución entonces*/
            if(!bSi)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No hay cantidad para entregar.", "Cantidad de Entrega", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                return;
            }
            
        }/*Fin de else*/                    

        /*Recorre toda la tabla de partidas para válidar que las cantidades que se van a entregar*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Obtiene la cantidad original de venta*/
            String sCant           = jTab.getValueAt(x, 2).toString();
            
            /*Obtiene las cantidades anteriormente entregadas*/
            String sCantsDevs      = jTab.getValueAt(x, 11).toString();
            
            /*Obtiene la cantidad a querer entregar*/
            String sCantEnt        = jTab.getValueAt(x, 12).toString();
            
            /*Si la cantidad a querer entregar es cadena vacia entonces que sea 0*/
            if(sCantEnt.compareTo("")==0)
                sCantEnt            = "0";
            
            /*Genera la cant real límite que se puede entregar*/
            String sCantRea         = Double.toString(Double.parseDouble(sCant) - Double.parseDouble(sCantsDevs));
            
            /*Si la cantidad de entrega es mayor a la cantidad real posible de entrega entonces*/
            if(Double.parseDouble(sCantEnt)> Double.parseDouble(sCantRea))
            {                
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cantidad a entregar es mayor a la cantidad posible de entrega.", "Cantidad de Entrega", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));    
                return;
            }            
        }                
                
        //Si esta marcado recibir todo entonces
        if(jCEntT.isSelected())
        {
            /*Preguntar al usuario si esta seguro de querer recibir todo*/
            Object[] op = {"Si","No"};
            int iRes    = JOptionPane.showOptionDialog(this, "Toda la mercancia se va a entregar.¿Estas seguro de que quieres continuar?", "Entrega", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
            if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                return;                       
        }
        
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres hacer la(s) entrega(s)?", "Entrega", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                    
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Declara variables de lajTabdatos*/
        Statement   st;               
        ResultSet   rs;
        String      sQ; 
        
        /*Actualiza en la base de datos para esta venta y saber los que se están entregando*/
        try 
        {                
            sQ = "UPDATE partvta SET "
                    + "entrenow     = 0 "
                    + "WHERE vta    = " + jTVta.getText();                                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                    
        }

        /*Recorre toda la tabla de partidas*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Obtiene la cantidad de entrega*/
            String sCantEnt     = jTab.getValueAt(x, 12).toString();
            
            /*Si la cantidad de entrega es cadena vacia entonces que sea 0*/
            if(sCantEnt.compareTo("")==0)
                sCantEnt = "0";

            /*Resta del inventario la cantidad para el producto de la entrega*/
            try 
            {                
                sQ = "UPDATE prods SET "
                        + "exist        = exist - " + sCantEnt + ", "
                        + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE prod   = '" + jTab.getValueAt(x, 1).toString().replace("'", "''") + "' AND alma = '" + jTab.getValueAt(x, 4).toString().replace("'", "''") + "'";                                                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                       
             }
            
            /*Si tene talla o color entonces procesa las tallas y colores*/
            if(jTab.getValueAt(x, 13).toString().compareTo("")!=0 || jTab.getValueAt(x, 14).toString().compareTo("")!=0)                           
                Star.vTallCol(con, jTab.getValueAt(x, 2).toString(), jTab.getValueAt(x, 4).toString(), jTab.getValueAt(x, 13).toString(), jTab.getValueAt(x, 14).toString(), jTab.getValueAt(x, 1).toString(), "-");            
            
            /*Actualiza la partida en la base de datos para saber los que se entregarón*/
            try 
            {                
                sQ = "UPDATE partvta SET "
                        + "cantentre    = cantentre + " + sCantEnt + ", "
                        + "entrenow     = " + sCantEnt + " "
                        + "WHERE id_id  = " + jTab.getValueAt(x, 19).toString().replace("'", "''") + " AND vta = " + jTVta.getText();                                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                       
            }
            
            /*Obtiene la cantidad correcta*/               
            sCantEnt          = Star.sCantUnid(jTab.getValueAt(x, 2).toString().trim(), sCantEnt);
            
            /*Realiza la afectación correspondiente al almacén*/
            if(Star.iAfecExisProd(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim(), jTab.getValueAt(x, 4).toString().replace("'", "''").trim(), sCantEnt, "-")==-1)
                return;

            /*Actualiza la existencia general del producto*/
            if(Star.iCalcGralExis(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim())==-1)
                return;
            
            /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
            if(!Star.vRegMoniInv(con, jTab.getValueAt(x, 1).toString().replace("'", "''") , sCantEnt, jTab.getModel().getValueAt(x, 5).toString().replace("'", "''"), jTab.getValueAt(x, 4).toString().replace("'", "''"), Login.sUsrG , jTNoSer.getText().replace("'", "''").trim() +   jTNoDoc.getText().replace("'", "''").trim(), "ENT", jTab.getValueAt(x, 3).toString().replace("'", "''"), jTNoSer.getText().trim(), jTCli.getText().replace("'", "''"), "0"))                                
                return;                                                                                                                                                                                                             
                        
        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
        try 
                {                
                    sQ = "UPDATE vtas SET "
                            + "extr1       = 'Ex' "
                            + "WHERE vta        = " + jTVta.getText();                    
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
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si no hay datos entonces solo mensajea*/
        if(sCarp.compareTo("")==0)
            JOptionPane.showMessageDialog(null, "No se guardara el Formato de Etrega en PDF ya que no se a definido la carpeta compartidad de la aplicación.", "Formato Entrega", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
        
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

        /*Obtiene la ruta completa hacia el logo*/
        String sRutLog          = sCarp + "\\imgs\\Logotipo Empresa\\" + Login.sCodEmpBD + "\\Logo.jpg";

        /*Si no existe un logo para la empresa entonces será el logo por default del sistema*/        
        if(!new File(sRutLog).exists())
            sRutLog             = getClass().getResource(Star.sIconDef).toString();
        
        /*Si la ruta compartida de la aplicación esta definida entonces*/
        if(sCarp.compareTo("")!=0)
        {
            /*Si la ruta a las entregas no existe entonces crea la ruta*/
            sCarp = sCarp + "\\Entregas";
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();
            
            /*Si la ruta a la empresa no existe entonces crea la ruta*/
            sCarp      += "\\" + Login.sCodEmpBD;
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();
            
            /*Completa la ruta hacia el PDF*/
            sCarp   += "\\" + sNoSer + "-" + sFol + "-" + System.currentTimeMillis() + ".pdf";
        }
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Exito en la entrega de la venta: " + jTVta.getText() + ".", "Entrega Exitosa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
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
                    JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptNotRecib.jrxml"));
                    JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                    /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte Blackorders");
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
        Star.entBackG   = null;
        
    }//GEN-LAST:event_jBGuarActionPerformed
                   
    
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
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTNom.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomFocusLost

    
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

    
    /*Cuando se presiona una tecla en el check de entregar todo*/
    private void jCEntTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCEntTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCEntTKeyPressed

    
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

    
    /*Cuando se gana el foco del teclado en el campo de la serie*/
    private void jTNoSerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoSerFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoSer.setSelectionStart(0);jTNoSer.setSelectionEnd(jTNoSer.getText().length()); 
        
    }//GEN-LAST:event_jTNoSerFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del nombre*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length()); 
        
    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la fecha*/
    private void jTFecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFecFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFec.setSelectionStart(0);jTFec.setSelectionEnd(jTFec.getText().length()); 
        
    }//GEN-LAST:event_jTFecFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del tipo de documento*/
    private void jTTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTipDocFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTipDoc.setSelectionStart(0);jTTipDoc.setSelectionEnd(jTTipDoc.getText().length()); 
        
    }//GEN-LAST:event_jTTipDocFocusGained

    
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

    
    /*Cuando se presiona una tecla en el campo del número de serie*/
    private void jTNoSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNoSerKeyPressed

    
    /*Cuando se presiona una tecla en el campo del número de nombre*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la fecha*/
    private void jTFecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFecKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFecKeyPressed

    
    /*Cuando se presiona una tecla en el campo del número del tipo de documento*/
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
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTabG;
    private javax.swing.JCheckBox jCEntT;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLNot;
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
    private javax.swing.JTextField jTNoDoc;
    private javax.swing.JTextField jTNoSer;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTTipDoc;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTextField jTVta;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaDevPFac extends javax.swing.JFrame */
