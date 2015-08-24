//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;




/*Clase para ver una compra*/
public class VComp extends javax.swing.JFrame 
{        
    /*Contiene el color original del botón*/
    private java.awt.Color              colOri;
    
    /*Declara variables de instancia*/    
    private int                         iContFi;
    private final String                sCompGlo;
    
    /*Contador para modificar tabla*/
    private int                         iContCellEd;
    
    /*Declara variables originales*/
    private String                      sProdOri;
    private String                      sCantOri;
    private String                      sUnidOri;
    private String                      sDescripOri;
    private String                      sCostOri;
    private String                      sDescOri;
    private String                      sDescAdOri;
    private String                      sImpueOri;
    private String                      sMonOri;
    private String                      sImpoOri;
    
    
    
    
    /*Consructor con argumento*/
    public VComp(String sCodComp) 
    {
        /*Obtiene el código de la compra del otro formulario*/
        sCompGlo                = sCodComp;
        
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Inicia el contador de filas de las partidas*/
        iContFi                 = 1;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Ver compra, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Pon el foco del teclado en el campo del código del proveedor*/
        jTCodProv.grabFocus();
               
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
            sQ = "SELECT comprs.CODORD, comprs.TIP, comprs.ESTADO, comprs.PROV,  comprs.NODOC, comprs.FDOC, comprs.SUBTOT, provs.NOM, comprs.IMPUE, comprs.TOT FROM comprs LEFT JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV ) = comprs.PROV WHERE comprs.CODCOMP = '" + sCompGlo + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Coloca todos los valores obtenidos en sus campos correspondientes*/
                jTCodProv.setText       (rs.getString("comprs.PROV"));
                jTNomProv.setText       (rs.getString("provs.NOM"));
                jTNoDoc.setText         (rs.getString("comprs.NODOC"));
                jTFec.setText           (rs.getString("comprs.FDOC"));
                jTEsta.setText          (rs.getString("comprs.ESTADO"));
                jTOrd.setText           (rs.getString("comprs.CODORD"));
                jTCodCom.setText        (sCompGlo);              
                
                /*Coloca el tipo de documento*/
                jTTipD.setText          (rs.getString("tip"));
                
                /*Si el estad es devolución, devolución parcial o cancelación entonces el color de la letra del campo será roja*/
                if(rs.getString("comprs.ESTADO").compareTo("DEV")==0 || rs.getString("comprs.ESTADO").compareTo("CA")==0 || rs.getString("comprs.ESTADO").compareTo("DEVP")==0)
                    jTEsta.setForeground(Color.RED);
                else
                    jTEsta.setForeground(Color.BLUE);                                                                
            }                        
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
            //Declaracion de variables globales
            String sSubTot  ="0";
            String sDesc    ="0";
            String sImpu    ="0";
            
            //Se ejecuta la consulta
            sQ = "SELECT * FROM partcomprs WHERE codcom = '" + sCompGlo + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene el costo*/
                String sCost        = rs.getString("cost");   
                
                /*Dale formato de moneda al costo*/
                double dCant        = Double.parseDouble(sCost);                
                NumberFormat n      = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sCost               = n.format(dCant);
                
                /*Obtiene el importe*/
                String sImp         = Double.toString(Double.parseDouble(rs.getString("cant"))*Double.parseDouble(rs.getString("cost")));               

                //se saca el descuento
                double dDesc        = Double.parseDouble(sImp)*Double.parseDouble(rs.getString("descu"))/100;
                
                //se saca el impuesto adicional
                dDesc               = dDesc+((Double.parseDouble(sImp)-dDesc)*Double.parseDouble(rs.getString("descad"))/100);
                
                //Obtiene el valor del impuesto
                String sValImp      = Star.sGetValImp(rs.getString("codimpue"));
                    
                //se carga la cantidad de impuesto al importe con el descuento
                String  sImpue      = Double.toString((Double.parseDouble(sImp)-dDesc)*Double.parseDouble(sValImp)/100);
                
                sSubTot             = Double.toString(Double.parseDouble(sSubTot)+Double.parseDouble(sImp));
                sDesc               = Double.toString(Double.parseDouble(sDesc)+dDesc);
                sImpu               = Double.toString(Double.parseDouble(sImpu)+Double.parseDouble(sImpue));
                
                /*Dale formato de moneda al importe*/                
                dCant               = Double.parseDouble(sImp);                
                n                   = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sImp                = n.format(dCant);
                
                /*Agrega todos los datos obtenidos en la tabla de partidas*/
                DefaultTableModel te= (DefaultTableModel)jTab.getModel();
                Object nu[]         = {iContFi, rs.getString("prod"), rs.getString("cant"), rs.getString("unid"), rs.getString("alma"), rs.getString("descrip"), sCost, rs.getString("descu"), rs.getString("descad"), rs.getString("codimpue"), rs.getString("mon"), sImp,sImpue};        
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas*/
                ++iContFi;
            }
            // ya teniendo los datos entonces
            //se saca el total
            String sTot=Double.toString(Double.parseDouble(sSubTot)-Double.parseDouble(sDesc)+Double.parseDouble(sImpu));
            
            /*Dale formato de moneda al subtotal*/                
            double dCant               = Double.parseDouble(sSubTot);                
            NumberFormat n             = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            sSubTot                    = n.format(dCant);
            
            /*Dale formato de moneda al descuento*/                
            dCant                      = Double.parseDouble(sDesc);                
            n                          = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            sDesc                    = n.format(dCant);
            
            /*Dale formato de moneda al impuesto*/                
            dCant                      = Double.parseDouble(sImpu);                
            n                          = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            sImpu                      = n.format(dCant);
            
            /*Dale formato de moneda al impuesto*/                
            dCant                      = Double.parseDouble(sTot);                
            n                          = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            sTot                      = n.format(dCant);
            
            jTSubTot.setText          (sSubTot);
            jDesc.setText             (sDesc);
            jTImp.setText             (sImpu);
            jTTot.setText             (sTot);                        
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
        
        /*Crea el listener para cuando se cambia de selección en la tabla*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtiene la fila seleccionada si no hay seleccion regresa*/                
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Obtén la propiedad que a sucedio en el control*/
                String pr = event.getPropertyName();                                
                                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pr)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sProdOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sCantOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sUnidOri        = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sDescripOri     = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sCostOri        = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sDescOri        = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sDescAdOri      = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sImpueOri       = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sMonOri         = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sImpoOri        = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sProdOri,           jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sCantOri,           jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sUnidOri,           jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sDescripOri,        jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sCostOri,           jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sDescOri,           jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sDescAdOri,         jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sImpueOri,          jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sMonOri,            jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sImpoOri,           jTab.getSelectedRow(), 10);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public VComp() */    
    
    
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
        jLabel1 = new javax.swing.JLabel();
        jTCodCom = new javax.swing.JTextField();
        jTNomProv = new javax.swing.JTextField();
        jTCodProv = new javax.swing.JTextField();
        jTFec = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTEsta = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTTipD = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTOrd = new javax.swing.JTextField();
        jTSubTot = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTImp = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTTot = new javax.swing.JTextField();
        jBTab1 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jDesc = new javax.swing.JTextField();

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
        jBSal.setNextFocusableComponent(jTCodProv);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 110, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Producto", "Cantidad", "Unidad", "Almacén", "Descripción", "Costo", "Descuento", "Desc. Adicional", "Impuesto", "Moneda", "Importe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBSal);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 1030, 240));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Cod. Proveedor:");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 110, -1));

        jLabel6.setText("Cod. Proveedor:");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, -1));

        jTNoDoc.setEditable(false);
        jTNoDoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoDoc.setNextFocusableComponent(jTFec);
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
        jPanel4.add(jTNoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 120, 20));

        jLabel3.setText("Nombre Proveedor:");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 110, -1));

        jLabel2.setText("Fecha:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 80, -1));

        jLabel20.setText("Orden de Compra:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 120, -1));

        jLabel1.setText("Estado:");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 80, -1));

        jTCodCom.setEditable(false);
        jTCodCom.setForeground(new java.awt.Color(51, 51, 255));
        jTCodCom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodCom.setNextFocusableComponent(jTEsta);
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
        jPanel4.add(jTCodCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 160, 20));

        jTNomProv.setEditable(false);
        jTNomProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNomProv.setNextFocusableComponent(jTNoDoc);
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

        jTCodProv.setEditable(false);
        jTCodProv.setBackground(new java.awt.Color(204, 255, 204));
        jTCodProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodProv.setNextFocusableComponent(jTNomProv);
        jTCodProv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodProvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodProvFocusLost(evt);
            }
        });
        jTCodProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodProvKeyPressed(evt);
            }
        });
        jPanel4.add(jTCodProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 120, 20));

        jTFec.setEditable(false);
        jTFec.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFec.setNextFocusableComponent(jTCodCom);
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
        jPanel4.add(jTFec, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 130, 20));

        jLabel5.setText("Cod. Compra:");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 80, -1));

        jTEsta.setEditable(false);
        jTEsta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTEsta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEsta.setNextFocusableComponent(jTab);
        jTEsta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstaFocusLost(evt);
            }
        });
        jTEsta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstaKeyPressed(evt);
            }
        });
        jPanel4.add(jTEsta, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 90, 20));

        jLabel21.setText("No. Documento:");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 110, -1));

        jTTipD.setEditable(false);
        jTTipD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTipD.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTipDFocusLost(evt);
            }
        });
        jPanel4.add(jTTipD, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 120, 20));

        jLabel22.setText("Tipo Documento:");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 110, -1));

        jTOrd.setEditable(false);
        jTOrd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTOrd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTOrdFocusLost(evt);
            }
        });
        jPanel4.add(jTOrd, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 130, 20));

        jP1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 790, 110));

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
        jP1.add(jTSubTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 390, 160, 30));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel26.setText("Sub Total:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 400, 110, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setText("Impuesto:");
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 460, 110, -1));

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
        jP1.add(jTImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 450, 160, 30));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel28.setText("Total:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 490, 110, -1));

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
        jP1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 480, 160, 30));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 10, 20));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel29.setText("Descuento:");
        jP1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 430, 110, -1));

        jDesc.setEditable(false);
        jDesc.setBackground(new java.awt.Color(204, 255, 204));
        jDesc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jDesc.setForeground(new java.awt.Color(51, 51, 0));
        jDesc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jDesc.setText("$0.00");
        jDesc.setNextFocusableComponent(jBSal);
        jDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jDescFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jDescFocusLost(evt);
            }
        });
        jDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDescKeyPressed(evt);
            }
        });
        jP1.add(jDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 420, 160, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                .addContainerGap())
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

        /*Coloca el caret en la posiciòn 0*/
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
    private void jTCodProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProvFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCodProv.setSelectionStart(0);jTCodProv.setSelectionEnd(jTCodProv.getText().length());        
        
    }//GEN-LAST:event_jTCodProvFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de nom del proveedor*/
    private void jTNomProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomProvFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNomProv.setSelectionStart(0);jTNomProv.setSelectionEnd(jTNomProv.getText().length());        
                
    }//GEN-LAST:event_jTNomProvFocusGained

    
    /*Cuando se presiona una tecla en el campo del código del proveedor*/
    private void jTCodProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodProvKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCodProvKeyPressed

    
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
    private void jTEstaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEsta.setSelectionStart(0);jTEsta.setSelectionEnd(jTEsta.getText().length());        
        
    }//GEN-LAST:event_jTEstaFocusGained

    
    /*Cuando se presiona una tecla en el campo de estad*/
    private void jTEstaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstaKeyPressed
        
         //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstaKeyPressed

    
    /*Cuando se presiona una tecla en la tabla de partidas*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
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
    private void jTCodProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProvFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCodProv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodProvFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTipDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTipDFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTipD.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTipDFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTNomProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomProvFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTNomProv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomProvFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTFecFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFecFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTFec.setCaretPosition(0);
        
    }//GEN-LAST:event_jTFecFocusLost


    /*Cuando se pierde el foco del teclado en el control*/
    private void jTOrdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTOrdFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTOrd.setCaretPosition(0);
        
    }//GEN-LAST:event_jTOrdFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCodComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodComFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCodCom.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodComFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTEstaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstaFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTEsta.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEstaFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del subtotal*/
    private void jTSubTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusLost
        
        /*Coloca el caret en la posición 0*/
        jTSubTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSubTotFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del impuesto*/
    private void jTImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusLost
        
        /*Coloca el caret en la posición 0*/
        jTImp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTImpFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del total*/
    private void jTTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusLost
        
        /*Coloca el caret en la posición 0*/
        jTTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTotFocusLost

    private void jDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDescFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jDescFocusGained

    private void jDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDescFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jDescFocusLost

    private void jDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDescKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jDescKeyPressed

       
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
    private javax.swing.JTextField jDesc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTCodCom;
    private javax.swing.JTextField jTCodProv;
    private javax.swing.JTextField jTEsta;
    private javax.swing.JTextField jTFec;
    private javax.swing.JTextField jTImp;
    private javax.swing.JTextField jTNoDoc;
    private javax.swing.JTextField jTNomProv;
    private javax.swing.JTextField jTOrd;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTTipD;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaEmpresa extends javax.swing.JFrame */
