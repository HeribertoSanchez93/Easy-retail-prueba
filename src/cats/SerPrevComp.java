/*Paquete*/
package cats;

/*Importaciones*/
import java.awt.Cursor;
import java.awt.HeadlessException;
import static ptovta.Princip.bIdle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ptovta.Star;
import ptovta.Login;
import ptovta.SelSer;



/*Clase para asociar series al producto*/
public class SerPrevComp extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private java.awt.Color  colOri;
    
    
    /*Declara variables originales*/
    private String          sNoG;
    private String          sQtyG;
    private String          sProductoG;
    private String          sUnidadG;
    private String          sListaG;
    private String          sDescripcionG;
    private String          sPrecioG;
    private String          sDescG;
    private String          sAlmaG;
    private String          sImporteG;
    private String          sImpuestoG;
    private String          sTotalimpuestoG;
    private String          sImportedescuentoG;
    private String          sSerOriG;
    private String          sComentarioSerieG;
    private String          sGarantiaG;
    private String          sKitG;
    private String          sTallaG;
    private String          sColorG;
    private String          sBackOrderG;
    private String          sLoteG;
    private String          sPedimentoG;
    private String          sCaducidadG;
    private String          sCodImpueG;
    private String          sCostG;
    private String          sDescAdG;
    private String          sMonedaG;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean         bSel;
    
    /*Contador del cell*/
    private int             iContCellEd;
    
    //Contiene la cotizacion
    private String          sCotG;
    
    
    /*Constructor sin argumentos*/
    public SerPrevComp(String sCot) 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
            
        //Se toma la cotización
        sCotG=sCot;
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);

        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Series para previos de compra, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        /*Cambia el icono de la forma, ya sea el personalizado por el usuario o el de default del sistema*/
        if(new File(new java.io.File("").getAbsolutePath() + "\\Logo.jpg").exists())
        {
            setIconImage(Toolkit.getDefaultToolkit().getImage(new java.io.File("").getAbsolutePath() + "\\Logo.jpg"));
        }
        else
            setIconImage(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de la tabla*/
        jTab.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(50);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(5).setPreferredWidth(500);
        jTab.getColumnModel().getColumn(6).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(7).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(8).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(9).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(10).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(11).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(12).setPreferredWidth(200);
        jTab.getColumnModel().getColumn(13).setPreferredWidth(200);
        jTab.getColumnModel().getColumn(14).setPreferredWidth(500);
        jTab.getColumnModel().getColumn(15).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(16).setPreferredWidth(100);
        
        jTab.getColumnModel().getColumn(17).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(18).setPreferredWidth(100);
        
        jTab.getColumnModel().getColumn(19).setPreferredWidth(200);
        jTab.getColumnModel().getColumn(20).setPreferredWidth(100);
        
        //Se oculta la del id
        jTab.getColumnModel().getColumn(21).setMaxWidth(0);
        jTab.getColumnModel().getColumn(21).setMinWidth(0);
        jTab.getTableHeader().getColumnModel().getColumn(21).setMaxWidth(0);
        jTab.getTableHeader().getColumnModel().getColumn(21).setMinWidth(0);
        
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        //Oculta los campos
        jTComenSer.setVisible(false);
        jTSerProd.setVisible(false);
        
        //Pon el label en su forma
        jLabel1.setText("PREVIO DE COMPRA " + sCot + " " + jLabel1.getText());
        
        /*Carga los números de serie pasados en el arreglo*/
        vCarg(sCot);
        
        /*Inicializa el contador del celleditor*/
        iContCellEd = 1;
                
        /*Crea el listener para la tabla, para cuando se modifique una celda*/
        PropertyChangeListener pr = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces que regresa*/
                if(jTab.getSelectedRow()==-1)
                    return;
                        
                /*Obtén la propiedad que a sucedio en el control*/
                String pro = event.getPropertyName();                                
                                
                /*Si el evento fue por entrar en una celda del tabla*/
                if("tableCellEditor".equals(pro)) 
                {
                    /*Si el contador de cell editor está en 1 entonces*/
                    if(iContCellEd==1)
                    {
                        
                        /*Obtén algunos datos originales de la fila*/        
                        sNoG               = jTab.getValueAt(jTab.getSelectedRow(), 0).toString();
                        sProductoG         = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sQtyG              = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sUnidadG           = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sAlmaG             = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sDescripcionG      = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sCostG             = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sDescG             = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sDescAdG           = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sImpuestoG         = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sTotalimpuestoG    = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sImporteG          = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        sTallaG            = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();
                        sColorG            = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();
                        sLoteG             = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();
                        sPedimentoG        = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();
                        sCaducidadG        = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();
                        sSerOriG           = jTab.getValueAt(jTab.getSelectedRow(), 17).toString();
                        sComentarioSerieG  = jTab.getValueAt(jTab.getSelectedRow(), 18).toString();
                        sGarantiaG         = jTab.getValueAt(jTab.getSelectedRow(), 19).toString();
                        sTotalimpuestoG    = jTab.getValueAt(jTab.getSelectedRow(), 20).toString();

                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd         = 1;
                            
                        /*Coloca los valores origianales*/
                        jTab.setValueAt(sNoG,               jTab.getSelectedRow(), 0); 
                        jTab.setValueAt(sProductoG,         jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sQtyG,              jTab.getSelectedRow(), 2);  
                        jTab.setValueAt(sUnidadG,           jTab.getSelectedRow(), 3);
                        jTab.setValueAt(sAlmaG,             jTab.getSelectedRow(), 4); 
                        jTab.setValueAt(sDescripcionG,      jTab.getSelectedRow(), 5); 
                        jTab.setValueAt(sCostG,             jTab.getSelectedRow(), 6); 
                        jTab.setValueAt(sDescG,             jTab.getSelectedRow(), 7);
                        jTab.setValueAt(sDescAdG,           jTab.getSelectedRow(), 8); 
                        jTab.setValueAt(sImpuestoG,         jTab.getSelectedRow(), 9); 
                        jTab.setValueAt(sTotalimpuestoG,    jTab.getSelectedRow(), 10);
                        jTab.setValueAt(sImporteG,          jTab.getSelectedRow(), 11); 
                        jTab.setValueAt(sTallaG,            jTab.getSelectedRow(), 12); 
                        jTab.setValueAt(sColorG,            jTab.getSelectedRow(), 13); 
                        jTab.setValueAt(sLoteG,             jTab.getSelectedRow(), 14); 
                        jTab.setValueAt(sPedimentoG,        jTab.getSelectedRow(), 15); 
                        jTab.setValueAt(sCaducidadG,        jTab.getSelectedRow(), 16); 
                        
                        jTab.setValueAt(sGarantiaG,         jTab.getSelectedRow(), 19); 
                        jTab.setValueAt(sTotalimpuestoG,    jTab.getSelectedRow(), 20); 
                        
                         
                        
                        //Si la seleccion es la serie
                        if(jTab.getSelectedColumn()==17 && jTab.getValueAt(jTab.getSelectedRow(), 17).toString().trim().compareTo("") != 0)
                        {
                            //Se verifica el valor que se debe de tomar
                            boolean bValNuw = true;
                            /*Abre la base de datos*/        
                            Connection  con;        
                            try 
                            {
                                con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
                            } 
                            catch(SQLException ex) 
                            {    
                                /*Agrega en el log*/
                                Login.vLog(ex.getMessage());

                                /*Mensajea y regresa*/
                                JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                                return;
                            }

                            
                            //Se verifica la configuracion de que la serie solo pueda estar una sola vez en cotizaciobnes
                            String sRespChec=Star.sCheckUnConf(con, "prev", "prevunavezser");

                            //Si es nulo marca error
                            if(sRespChec==null || sRespChec.compareTo("no existe")==0)
                                return;

                            //Se almacena en la variable para checar la serie
                            boolean bSerUnVez = false;
                            if(sRespChec.compareTo("1")==0)
                                bSerUnVez = true;

                            //Revisar que todas las caracteristica de una serie en cotizaciones
                            //Si solo se puede cotizar una sola vez esa pieza 
                            if(bSerUnVez==true)
                            {
                                //Se revisa si se encuentra esa serie en alguna particion de una cotizacion
                                String sResptra = Star.sTraUnCamp(con, "serprod", "partprevcomprs", jTab.getValueAt(jTab.getSelectedRow(), 17).toString());

                                //Si es nulo marca error
                                if(sResptra==null)
                                    return ;
                                else if(sResptra.compareTo("no existe")!=0)
                                {
                                    /*Mensajea*/
                                    JOptionPane.showMessageDialog(null, "La serie " + jTab.getValueAt(jTab.getSelectedRow(), 17).toString() + " ya existe en un previo de compra.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                                    bValNuw = false;
                                }
                                //Se comprueba si la serie ya existe
                                
                                else if(Star.vSerRepTabSalto(jTab.getValueAt(jTab.getSelectedRow(), 17).toString(),jTab, 17, jTab.getSelectedRow())==1)
                                {
                                    /*Mensajea*/
                                    JOptionPane.showMessageDialog(null, "La serie " + jTab.getValueAt(jTab.getSelectedRow(), 17).toString() + " ya existe en este previo de compra.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                                    bValNuw = false;
                                }
                            }

                            //Si hasta el momento cumple con las condiciobnes 
                            if(bValNuw == true)
                            {
                                //Se envia la serie para validar y regresa la nueva serie con mayúsculas
                                String sSer = Star.sValSer(jTab.getValueAt(jTab.getSelectedRow(), 17).toString());

                                //Si no es valioda la serie
                                if(sSer==null)
                                {
                                    JOptionPane.showMessageDialog(null, "Serie no valida solo se aceptan numeros y letras.", "Series Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                                    bValNuw = false;
                                }
                                else
                                {
                                    jTab.setValueAt(sSer,        jTab.getSelectedRow(), 17); 
                                    String sComentVal = Star.sComenSer(sSer,con,jTab.getValueAt(jTab.getSelectedRow(), 1).toString(),jTab.getValueAt(jTab.getSelectedRow(), 4).toString());
                                    if(sComentVal.compareTo("no existe")==0)
                                        sComentVal = "";
                                    jTab.setValueAt(sComentVal,  jTab.getSelectedRow(), 18);
                                }
                                

                            }
                            if(bValNuw == false)
                            {

                                jTab.setValueAt(sSerOriG,           jTab.getSelectedRow(), 17); 
                                jTab.setValueAt(sComentarioSerieG,  jTab.getSelectedRow(), 18);

                            }
                            
                            //Cierra la base de datos
                            if(Star.iCierrBas(con)==-1)
                                return;
                            
                        }//if(jTab.getSelectedColumn()==13)
                        else if(jTab.getSelectedColumn()==17 && jTab.getValueAt(jTab.getSelectedRow(), 17).toString().trim().compareTo("") == 0)
                        {
                            jTab.setValueAt("",                 jTab.getSelectedRow(), 17); 
                            jTab.setValueAt("",                 jTab.getSelectedRow(), 18);
                        }
                        else
                        {
                            jTab.setValueAt(sSerOriG,           jTab.getSelectedRow(), 17); 
                            //
                        }

                        if(jTab.getSelectedColumn()==18 && jTab.getValueAt(jTab.getSelectedRow(), 17).toString().trim().compareTo("") != 0)
                        {
                            
                            jTab.setValueAt(jTab.getValueAt(jTab.getSelectedRow(), 18).toString().trim(),jTab.getSelectedRow(), 18); 
                            
                        }
                        
                        if(jTab.getValueAt(jTab.getSelectedRow(), 17).toString().trim().compareTo("") == 0)
                            jTab.setValueAt(sComentarioSerieG,  jTab.getSelectedRow(), 18);
                    }
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */           
        };
        
        /*Establece el listener para la tabla de asociaciones*/
        jTab.addPropertyChangeListener(pr);
    
    }/*Fin de public Model() */

    
    /*Obtén las series de parte pasados en el arrelgo y cargalos en la tabla*/
    private void vCarg(String sCot)
    {
        /*Abre la base de datos*/        
        Connection  con;  
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
        } 
        catch(SQLException | HeadlessException e) 
        {    
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regreasa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }
        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ  = ""; 

        /*Obtiene las partidas de la cotización*/
        try
        {
            sQ = "SELECT * FROM partprevcomprs WHERE codcom = '" + sCot + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            int iContFiParts    = 1;
            while(rs.next())
            {     
                int iResp = Star.iProdSolSer(con, rs.getString("prod").trim());
                //Se verifica si algun elemento que deba llevar serie tenga
                if((rs.getString("serprod").trim().compareTo("")!=0 && iResp==1)||iResp==0)
                     continue;
                
                /*Obtiene los totales*/
                String sPorImp                  = Star.sGetValImp(rs.getString("codimpue"));
                String sImpueImpo               = Double.toString(Double.parseDouble(sPorImp)/Double.parseDouble(rs.getString("cant")));
                String sCost                    = rs.getString("cost");
                
                /*Lee el descuento*/
                String sDesc                    = rs.getString("descu");

                /*Si el descuento no es cadena vacia entonces obtiene el descuento del costo*/
                String sDescConvert;
                if(sDesc.compareTo("")!= 0)
                    sDescConvert                = Double.toString((Double.parseDouble(sDesc) / 100 ) * Double.parseDouble(sCost));
                /*Else, colocalo en 0 para poder hacer la resta*/
                else
                    sDescConvert                = "0";

                /*Lee el descuento adicional*/
                String sDescAd                  = rs.getString("descad");

                /*Si el descuento adicional no es cadena vacia entonces obtiene el descuento adicional del precio ya con el descuento*/
                String sDescAdConvert;
                if(sDescAd.compareTo("")        != 0)
                    sDescAdConvert              = Double.toString((Double.parseDouble(sDescAd) / 100 ) * (Double.parseDouble(sCost) - Double.parseDouble(sDescConvert)));
                /*Else, colocalo en 0 para poder hacer la resta*/
                else
                    sDescAdConvert              = "0";

        
                String sImpo        = Double.toString(Double.parseDouble(sCost) - Double.parseDouble(sDescConvert) - Double.parseDouble(sDescAdConvert));
                  
                
                //String sImpoDesc    = Double.toString(Double.parseDouble(rs.getString("impodesc"))/Double.parseDouble(rs.getString("cant")));
                
                //Crea el importe del impuesto
                String sImpoImpue               = Double.toString(Double.parseDouble(sImpo) * (Double.parseDouble(sImpueImpo) / 100));
                
                /*Dale formato de moneda a los totales*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sCost);                
                sCost            = n.format(dCant);
                dCant           = Double.parseDouble(sImpo);                
                sImpo           = n.format(dCant);
                
                for(int i = 0 ; i<Double.parseDouble(rs.getString("cant")); i++)
                {
                    /*Agrega el registro en la tabla*/
                    DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                    Object nu[]             = {iContFiParts,
                        rs.getString("prod"),
                        "1",
                        rs.getString("unid"),
                        rs.getString("alma"),
                        rs.getString("descrip"),
                        sCost, 
                        rs.getString("descu"), 
                        rs.getString("descad"), 
                        rs.getString("codimpue"), 
                        sPorImp,
                        sImpo,
                        rs.getString("tall"),
                        rs.getString("colo"),
                        rs.getString("lot"),
                        rs.getString("pedimen"),
                        rs.getString("flotvenc"),
                        rs.getString("serprod"),
                        rs.getString("comenser"),
                        rs.getString("garan"),
                        sImpoImpue,
                        rs.getString("id_id")};
                    te.addRow(nu);
                }//for(int i = 0 ; i<double(rs.getString("cant")) ; i++)
                
                
                /*Aumenta el contador de filas de las partidas*/
                ++iContFiParts;                
            
            }/*Fin de while(rs.next())*/
                        
        }/*Fin de try*/
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }
        
        sMonedaG = "PESOS";
        /*Obtiene las partidas de la cotización*/
        try
        {
            sQ = "SELECT * FROM prevcomprs WHERE codprevcomp = '" + sCot + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            
            /*Si hay datos*/
            if(rs.next())
            {     
                sMonedaG                        = rs.getString("mon");
                
            }/*Fin de while(rs.next())*/
                        
        }/*Fin de try*/
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
    }
    
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jLAyu = new javax.swing.JLabel();
        jBGuar = new javax.swing.JButton();
        jBPonSer = new javax.swing.JButton();
        jTComenSer = new javax.swing.JTextField();
        jTSerProd = new javax.swing.JTextField();
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("SERIES DEL PRODUCTO:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 320, -1));

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 400, 120, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Producto", "Qty", "Unidad", "Almacén", "Descripción", "Costo", "Descuento", "Desc. Adicional", "%Impuesto", "Total impuesto", "Importe", "Talla", "Color", "Lote", "Pedimento", "Caducidad", "Serie del producto", "Comentario de serie", "Garantía", "Importe impuesto", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBGuar);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 750, 370));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 400, 120, 20));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar");
        jBGuar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 400, 120, 30));

        jBPonSer.setBackground(new java.awt.Color(0, 153, 153));
        jBPonSer.setToolTipText("Existencias por almacén del producto");
        jBPonSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPonSerActionPerformed(evt);
            }
        });
        jBPonSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPonSerKeyPressed(evt);
            }
        });
        jP1.add(jBPonSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 30, 10, 20));

        jTComenSer.setEditable(false);
        jTComenSer.setFocusable(false);
        jTComenSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTComenSerActionPerformed(evt);
            }
        });
        jP1.add(jTComenSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 240, 20));

        jTSerProd.setEditable(false);
        jTSerProd.setFocusable(false);
        jP1.add(jTSerProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 400, 150, 20));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar todo");
        jBTod.setToolTipText("Marcar Todos los Registros de la Tabla (Alt+T)");
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, -1, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
      
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
        
        /*Cierra la forma*/
        this.dispose();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
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

    
    /*Cuando se mueve el ratón en la forma*/
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
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

                       
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    //Cuando entra el cursor a el boton de guardar
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered

        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);
    }//GEN-LAST:event_jBGuarMouseEntered

    
    //Cuando sale el cursor a el boton de guardar
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    //Cuando se preciona el boton de guardar
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        //Confirmar echo
        Object[] op = {"Si","No"};
        int iResp    = JOptionPane.showOptionDialog(this, "¿Seguro que decea continuar?", "Confirmar series", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iResp != JOptionPane.YES_OPTION)
            return;
        
        /*Abre la base de datos*/
        Connection  con;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );
            con.setAutoCommit(false);
        }
        catch(SQLException | HeadlessException e)
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));
            return;
        }
        /*Inicia la transacción*/
        try           
        {
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        }
        catch(SQLException ex)
        {                        
            /*Esconde la forma de loading*/
            if(Star.lCargGral!=null)
                Star.lCargGral.setVisible(false);
         
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log*/
            Login.vLog(ex.getMessage());

            /*Mensajea y regresa*/   
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, null);      
            return;
        }
        
        /*Recorre la tabla de partidas*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Declara variables locales de bases de datos*/
            Statement   st;
            ResultSet   rs; 
            String      sQ               = "";

            
             /*Borra el usuario de los logeados*/
            try 
            {                
                sQ = "DELETE FROM partprevcomprs WHERE id_id = " + jTab.getValueAt(x, 21).toString().replace("'", "''");                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException | HeadlessException e) 
             { 
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)                  
                    return;

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, Star.class.getName() + " Error  por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Star.class.getResource(Star.sRutIconEr)));             

                /*Agrega en el log y regresa error*/
                Login.vLog(e.getMessage());
                return;
             }        
            
            /*Determina si debe de poner o no el comentario de la serie*/
            String sComenSer    = " " + jTab.getValueAt(x, 18).toString();
            if(jTab.getValueAt(x, 17).toString().trim().compareTo("")==0)
                sComenSer       = "";
            
            /*Si la serie tiene datos entonces*/
            String sSerProd     = jTab.getValueAt(x, 17).toString().trim();
            if(sSerProd.compareTo("")!=0)
                sSerProd        = " SER:" + sSerProd;
                
            /*Comprueba si el producto es un kit*/
            String sKit = "0";
            try
            {
                sQ = "SELECT compue FROM prods WHERE prod = '" + jTab.getValueAt(x, 1).toString().trim() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    sKit      = rs.getString("compue");
            }
            catch(SQLException e)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Agrega en el log*/
                Login.vLog(e.getMessage());

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));
                return;
            }        

            /*Obtiene el importe*/
            String sImp                 = jTab.getValueAt(x, 11).toString().replace("$", "").replace(",", "").trim();
            
            /*Obtiene la existencia general del prodcuto*/
            String sExistG              = Star.sGetExisGral(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim());                       
            
            /*Obtiene el último costo del producto*/
            String sCostU               = Star.sUltCost(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim());
            
            /*Si el último costo es 0 entonces dejarlo como el costo actual*/
            String sCostP               = Star.sCostProm(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim());
            if(Double.parseDouble(sCostP)==0)
                sCostP      = jTab.getValueAt(x, 6).toString();
            else
                sCostP      = Double.toString(((Double.parseDouble(sExistG) * Double.parseDouble(sCostU)) + (Double.parseDouble(jTab.getValueAt(x, 2).toString()) * Double.parseDouble(jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "")))) / (Double.parseDouble(sExistG) + Double.parseDouble(jTab.getValueAt(x, 2).toString())));
             
            /*Obtiene el tipo de cambio de la moneda*/
            String sTipCam = "";
            try
            {
                sQ = "SELECT val FROM mons WHERE mon = '" + sMonedaG + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    sTipCam      = rs.getString("val");                                   
            }
            catch(SQLException e)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Agrega en el log*/
                Login.vLog(e.getMessage());

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                return;
            }

            /*Inserta en la base de datos la partida de la cotización*/
            try 
            {    
                sQ = "INSERT INTO partprevcomprs(codcom,                  prod,                                                                       cant,                                               unid,                                                                descrip,                                                                                        cost,                                                                                   descu,                                               descad,                                              codimpue,                                                  mon,         impo,                                          falt,    recib,          alma,                                                          costpro,                                           lot,                                                            pedimen,                                                        flotvenc,                                                  cantlotpend,  serprod,                                     comenser,                                                        garan,                                          tipcam,             eskit,         tall,                                                colo) " +
                                         "VALUES('" + sCotG + "','"  +    jTab.getValueAt(x, 1).toString().replace("'", "''").trim() + "','" +        jTab.getValueAt(x, 2).toString().trim() + "','" +   jTab.getValueAt(x, 3).toString().replace("'", "''") + "','" +        jTab.getValueAt(x, 5).toString().replace("'", "''") + "" + sSerProd + "" + sComenSer+ "','" +   jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "").trim() + "','" +     jTab.getValueAt(x, 7).toString().trim() + "','" +    jTab.getValueAt(x, 8).toString().trim() + "','" +    jTab.getValueAt(x, 9).toString().replace("'", "''") + "',  '','" +      sImp.replace("$", "").replace(",", "") + "',   now(),   1    ,     '" + jTab.getValueAt(x, 4).toString().replace("'", "''") + "', " +  sCostP.replace("$", "").replace(",", "") + ", '" + jTab.getValueAt(x, 14).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 15).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 16).toString().replace("'", "''") + "', 0, '" +       jTab.getValueAt(x, 17).toString() + "', '" + jTab.getValueAt(x, 18).toString().replace("'", "''") + "', '" +  jTab.getValueAt(x, 19).toString() + "', '" +     sTipCam + "', " +    sKit + ", '" + jTab.getValueAt(x, 12).toString().trim() + "', '" +  jTab.getValueAt(x, 13).toString().trim() + "')";
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException ex) 
             { 
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
            
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());
            
                /*Mensajea y retorna*/
                JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                return;
             }
                        
        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
        
        /*Termina la transacción*/
        try           
        {
            con.commit();
        }
        catch(SQLException ex)
        {            
            /*Esconde la forma de loading*/
            if(Star.lCargGral!=null)
                Star.lCargGral.setVisible(false);

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log*/
            Login.vLog(ex.getMessage());

            /*Mensajea y regresa*/   
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, null);      
            return;
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        /*Llama al recolector de basura y cierra la forma*/
        System.gc();
        this.dispose();

    }//GEN-LAST:event_jBGuarActionPerformed

    
    //Cuando se preciona una tecla en el botonn de guardar
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    private void jBPonSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPonSerActionPerformed

        /*Obtiene las filas seleccionadas*/
        int iSel[]              = jTab.getSelectedRows();
        
        /*Si no se a seleccionado por lo menos una cotización entonces*/
        if(iSel.length==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un producto para abrir.", "Cotizaciones", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }
        
        /*Abre la base de datos*/        
        Connection  con;        
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
        } 
        catch(SQLException ex) 
        {    
            /*Agrega en el log*/
            Login.vLog(ex.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }

        //Se verifica la configuracion de que la serie solo pueda estar una sola vez en cotizaciobnes
        String sRespChec=Star.sCheckUnConf(con, "prev", "prevunavezser");

        //Si es nulo marca error
        if(sRespChec==null || sRespChec.compareTo("no existe")==0)
            return;

        //Se almacena en la variable para checar la serie
        boolean bSerUnVez = false;
        if(sRespChec.compareTo("1")==0)
            bSerUnVez = true;

        //Poner en cada uno la serie correcta
        for(int i = 0; i < iSel.length; i++)
        {
            if(jTab.getValueAt(iSel[i], 17).toString().trim().compareTo("")!=0)
            {
                JOptionPane.showMessageDialog(null, "Una de sus elecciones ya tiene serie: " + jTab.getValueAt(iSel[i], 17).toString().trim(), "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }
            //Se crean variables
            String sSer="";
            String sComen="";
            
            //Si hay una cancelacion
            boolean bCanPross   = false;
            
            boolean bSer        = true; 
            while(bSer)
            {

                boolean bSerSinex   = false;
                
                /*Pidele al usuario que ingrese serie y comentario*/   
                SelSer lo     = new SelSer(jTSerProd,jTComenSer, (i + 1) + "-" + iSel.length,jTab.getValueAt(iSel[i], 1).toString().trim(),jTab.getValueAt(iSel[i], 4).toString().trim(),0);
                lo.setModal     (true);
                lo.setVisible   (true); 

                //Se obtienen los valores
                sSer=jTSerProd.getText();
                sComen =jTComenSer.getText();

                if(sSer.trim().compareTo("-1")==0)
                    sSer=null;

                /*Si es nula la serie entonces regresa*/
                if(sSer==null)
                {
                    /*Preguntar al usuario si esta seguro de que querer borrar la partida*/
                    Object[] op = {"Si","No"};
                    int iResp    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres cancelar el proceso?", "Borrar Partida", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                    if(iResp==JOptionPane.YES_OPTION)
                    {
                        //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return;
                        
                        //Regresa error
                        jTab.setValueAt("",  iSel[i], 13); 
                        jTab.setValueAt("",  iSel[i], 14);
                        bSer        = false;
                        i           = iSel.length;
                        bCanPross   = true;

                    }
                    //Si no solo regresa al control 
                    continue;
                }

                /*Si es cadena vacia entonces*/
                if(sSer.compareTo("")==0)
                {                    
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Ingresa una serie válida.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    continue;
                }

                //Si solo se puede cotizar una sola vez esa pieza 
                if(bSerUnVez==true)
                {
                    //Se revisa si se encuentra esa serie en alguna particion de una cotizacion
                    String sResptra = Star.sTraUnCamp(con, "serprod", "partprevcomprs", sSer);

                    //Si es nulo marca error
                    if(sResptra==null)
                        return ;
                    
                    else if(sResptra.compareTo("no existe")!=0)
                    {
                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "La serie " + sSer + "  ya existe en un previo de compra", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        continue;
                    }
                    //Se comprueba si la serie ya existe
                    else if(Star.vSerRepTab(sSer,jTab, 17, jTab.getRowCount() + 1)==1)
                    {
                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "La serie " + sSer + " ya existe en este previo de compra", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        continue;
                    }
                }

                //Si el comentario es nulo se pone en blanco
                if(sComen==null)
                    sComen="";

                //Se cambia el flag indicando que esta bien la serie
                bSer=false;

            }//Fin while(bSer)
            
            //Si nunca se cancelo el proceso
            if(bCanPross  == false)
            {
                //Se les da su valor
                jTab.setValueAt(sSer,    iSel[i], 17); 
                jTab.setValueAt(sComen,  iSel[i], 18);
            }
            

        }//for(int i = 0; i < iSel.length; i++)
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
    }//GEN-LAST:event_jBPonSerActionPerformed

    
    //Cuando se preciona una te la en el botom de pon serie
    private void jBPonSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPonSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBPonSerKeyPressed

    private void jTComenSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTComenSerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTComenSerActionPerformed

    /*Cuando se presiona una  tecla en la tabla de marcs*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTabKeyPressed

    
    //cuando se ingresa el cursor al de todos
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered

        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);

    }//GEN-LAST:event_jBTodMouseEntered

    
    //cuando se sale el cursor al de todos
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);

    }//GEN-LAST:event_jBTodMouseExited

    
    //Selecciona todos los elementos
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

    
    //Cuando se preciona alguna tecla en el boton de mostrar todo
    private void jBTodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTodKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
    }//GEN-LAST:event_jBTodKeyPressed

        
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();        
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBPonSer;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTod;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTComenSer;
    private javax.swing.JTextField jTSerProd;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
