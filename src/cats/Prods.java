//Paquete
package cats;

//Importaciones
import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Color;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ptovta.Busc;
import ptovta.Star;
import ptovta.ImgVis;
import ptovta.LPrecs;
import ptovta.Login;
import static ptovta.Princip.bIdle;
import vis.VisProds;


/*Clase para hacer una alta de producto*/
public class Prods extends javax.swing.JFrame
{
    /*Contiene la dirección de la forma para ver imágen en otra vista*/
    private ImgVis              v;
    
    //Thread para mostrar los productos en los controles
    private Thread              thCargProd;         
    
    /*Contiene la unidad original del producto*/
    private String              sUnidOriP;
    
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Variable que contiene el borde actual*/
    private Border              bBordOri;
    
    /*Declara variables de instancia*/
    private static Prods        obj                 = null;
    public static boolean       bMostVe             = true;            
    private int                 iContFi;    

    /*Declara variables originales*/
    private String              sProdOri             = "";
    private String              sDescripOri;
    
    /*Contador para modificar tabla*/
    private int                 iContCellEd;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean             bSel;
    
    /*Declara variables publicas estaticas de las listas de precio*/
    public String        sCostL;
    public String        sPre1;
    public String        sPre2;
    public String        sPre3;
    public String        sPre4;
    public String        sPre5;
    public String        sPre6;
    public String        sPre7;
    public String        sPre8;
    public String        sPre9;
    public String        sPre10;    
    public String        sUeps;
    public String        sPeps;
    public String        sCostP;
    public String        sUltCost;
    
    /*Declara variables pubicoas estáticas de las utilidades de cada lista de presio*/
    public String        sUtil1;
    public String        sUtil2;
    public String        sUtil3;
    public String        sUtil4;
    public String        sUtil5;
    public String        sUtil6;
    public String        sUtil7;
    public String        sUtil8;
    public String        sUtil9;
    public String        sUtil10;
    
    /*Declara variables publicas estáticas de las utilidades sobre venta de cada lista de precio*/
    public String        sUtil1V;
    public String        sUtil2V;
    public String        sUtil3V;
    public String        sUtil4V;
    public String        sUtil5V;
    public String        sUtil6V;
    public String        sUtil7V;
    public String        sUtil8V;
    public String        sUtil9V;
    public String        sUtil10V;

    /*Contiene la referencia a la misma forma*/
    public Prods        prods       = null;
    
    /*Arreglo que contiene las asociaciones de los demas Modelos con el producto*/
    public String       sMods[];
    
    /*Arreglo que contiene las asociaciones de las demás marcas del produto*/
    public String       sMarcs[];
    
    /*Arreglo que contiene las asociaciones números de parte del produto*/
    public String       sParts[];
    
    /*Arreglo que contiene las series del produto*/
    public String       sSers[][];
    
    /*Arreglo que contiene las compatibiidades del produto*/
    public String       sCompa[];
    
    //Arreglo que contiene las compatibiidades con marca y modelo
    public String       sCompaMarcMod[];
    
    /*Contiene el grupo de los radio buttons*/
    private javax.swing.ButtonGroup bG;
    
    
    
    

    /*Consructor sin argumentos*/
    public Prods()
    {               
        /*Inicializa los componentes gráfcos*/
        initComponents();
    
        /*Crea el grupo de los radio buttons de costeos*/
        bG  = new javax.swing.ButtonGroup();
        bG.add(jRUEPS);
        bG.add(jRPEPS);
        bG.add(jRUltCost);
        bG.add(jRProm);
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Esconde el control de almacén temporal*/
        jTAlmaG.setVisible(false);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Inicializa el contador de filas en 1*/
        iContFi                     = 1;
           
        /*Inicialmente esta deseleccionada la tabla*/
        bSel    = false;                
                        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Productos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog); 
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para cuando se modifica algo en la tabla*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección en la tabla entonces regresa*/                
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
                        sProdOri         = jTab.getValueAt  (jTab.getSelectedRow(), 1).toString();
                        sDescripOri     = jTab.getValueAt   (jTab.getSelectedRow(), 2).toString();                        
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sProdOri,      jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sDescripOri,   jTab.getSelectedRow(), 2);                                                       
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Activa en jtextarea de de deswcripción que se usen normamente las teclas de tabulador*/
        jTADescrip.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTADescrip.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Activa en jtextarea de información que se usen normamente las teclas de tabulador*/
        jTAInfor.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAInfor.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Establece el tamaño de las columnas*/
        jTab.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(190);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(300);        

        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);

        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nom de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Inicialmente la ventana sera visibles*/
        bMostVe                     = true;
        
        /*Inicialmente el label de imágen no será visible, se vuelve visible cuando se carga una imágen*/
        jLImg.setVisible(false);

        /*Centra la ventana*/
        this.setLocationRelativeTo(null);                
              
        /*Coloca el botón de guardar cambios a deshabilitado ya que no se pueden guardar cambios para un artículo nuevo*/
        jBGuar.setEnabled(false);
        
        /*Pon los comboboxes su índice vacio*/
        jComLin.setSelectedItem     ("");
        jComTip.setSelectedItem     ("");
        jComMod.setSelectedItem     ("");
        jComMarc.setSelectedItem    ("");
        jComFab.setSelectedItem     ("");
        jComMeds.setSelectedItem    ("");
        jComPeso.setSelectedItem    ("");
        jComCol.setSelectedItem     ("");
        jComUni.setSelectedItem     ("");
        jComUAd.setSelectedItem     ("");
        jComExt.setSelectedItem     ("");
     
        /*Inicializa los precios y costos inicialmente en 0*/                
        sPre1   = "$0.00";
        sPre2   = "$0.00";
        sPre3   = "$0.00";
        sPre4   = "$0.00";
        sPre5   = "$0.00";
        sPre6   = "$0.00";
        sPre7   = "$0.00";
        sPre8   = "$0.00";
        sPre9   = "$0.00";
        sPre10  = "$0.00";
        sCostL  = "$0.00";
        sUeps   = "$0.00";
        sPeps   = "$0.00";
        sCostP  = "$0.00";
        sUltCost= "$0.00";

        /*Inicializa las utilidades inicialmente en 0*/
        sUtil1  = "0.0";
        sUtil2  = "0.0";
        sUtil3  = "0.0";
        sUtil4  = "0.0";
        sUtil5  = "0.0";
        sUtil6  = "0.0";
        sUtil7  = "0.0";
        sUtil8  = "0.0";
        sUtil9  = "0.0";
        sUtil10 = "0.0";
        
        /*Inicializa las utilidades de ventas inicialmente en 0*/
        sUtil1V  = "0.0";
        sUtil2V  = "0.0";
        sUtil3V  = "0.0";
        sUtil4V  = "0.0";
        sUtil5V  = "0.0";
        sUtil6V  = "0.0";
        sUtil7V  = "0.0";
        sUtil8V  = "0.0";
        sUtil9V  = "0.0";
        sUtil10V = "0.0";
                        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Obtiene el método de costeo de la empresa local y seleccionalo en los radio*/
        vSelMet();
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
                
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces mensajea*/
        if(sCarp.compareTo("")==0)
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
        
        /*Carga todos los datos en todos los combos*/
        vCargCombs(con);
                        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
                            
        /*Pon el foco del teclado en el campo del código del artículo*/
        jTProd.grabFocus();
                
        /*Crea el listener para cuando se cambia de selección en la tabla de productos*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {   
                /*Si no hay selección en la fila entonces regresa*/        
                if(jTab.getSelectedRow()==-1)
                    return;
                
                //Detiene el thread para que no cargue los productos
                if(thCargProd!=null)
                    thCargProd.interrupt();
                
                //Crea el thread retrasado para la forma
                thCargProd = new Thread()
                {
                    @Override
                    public void run()
                    {
                        //Duerme el thread 
                        try
                        {
                            Thread.sleep(200);
                        }
                        catch(InterruptedException expnInterr)
                        {
                            return;
                        }                            
                        
                        /*Carga los datos en los controles*/
                        vLoadDat(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());               
                    }
                };      
                thCargProd.start();
            }
        });
        
        /*Listener para el combobox de líneas*/
        jComLin.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Carga todas las líneas*/
                vCargLin(con);

                //Cierra la base de datos
                Star.iCierrBas(con);                                                             
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Listener para el combobox de tipos*/
        jComLin.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Carga todos los tipos*/
                vCargTip(con);

                //Cierra la base de datos
                Star.iCierrBas(con);                                                               
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Listener para el combobox de marcas*/
        jComMarc.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Carga todas las marcas*/
                vCargMarc(con);

                //Cierra la base de datos
                Star.iCierrBas(con);                                                 
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Listener para el combobox de fabricantes*/
        jComFab.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Carga todas los fabricantes*/
                vCargFab(con);

                //Cierra la base de datos
                Star.iCierrBas(con);                                                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Listener para el combobox de medidas*/
        jComMeds.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Carga todas las medidas*/
                vCargMed(con);
                
                //Cierra la base de datos
                Star.iCierrBas(con);                                                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Listener para el combobox de pesos*/
        jComPeso.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Carga todos los pesos*/
                vCargPes(con);
                
                //Cierra la base de datos
                Star.iCierrBas(con);                                                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Listener para el combobox de colores*/
        jComCol.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Carga todos los colores*/
                vCargColo(con);
                
                //Cierra la base de datos
                Star.iCierrBas(con);                                                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Listener para el combobox de unidades*/
        jComUni.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                /*Carga todas las unidades*/
                vCargUnid(con);
                
                //Cierra la base de datos
                Star.iCierrBas(con);                                               
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });

        /*Listener para el combobox de ubicaciones adicionales*/
        jComUni.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                /*Carga todas las ubicaciones*/
                vCargUbiA(con);
                
                //Cierra la base de datos
                Star.iCierrBas(con);                                                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
                        
        /*Listener para el combobox de anaqueles*/
        jComAna.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                /*Carga todos los anaqueles*/
                vCargAnaq(con);
                
                //Cierra la base de datos
                Star.iCierrBas(con);                                                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Listener para el combobox de lugares*/
        jComLug.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                /*Carga todos los lugares*/
                vCargLug(con);
                
                //Cierra la base de datos
                Star.iCierrBas(con);                                                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Listener para el combobox de clasificaciones extra*/
        jComExt.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                /*Carga todas las ubicaciones*/
                vCargClasP(con);
                
                //Cierra la base de datos
                Star.iCierrBas(con);                                                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Listener para el combobox de impuestos*/
        jComImp.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                /*Carga todos los impuestos*/
                vCargImpue(con);
                
                //Cierra la base de datos
                Star.iCierrBas(con);                                                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
    }/*Fin de public AltaArtículo() */

    
    /*Obtiene el método de costeo de la empresa local y seleccionalo en los radio*/
    private void vSelMet()               
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
        
        /*Obtiene el método de costeo de la empresa local*/
        try
        {
            sQ = "SELECT metcost FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Selecciona el método de costeo que sea de la empresa*/
                if(rs.getString("metcost").compareTo("peps")==0)
                    bG.setSelected(jRPEPS.getModel(), true);
                else if(rs.getString("metcost").compareTo("ueps")==0)
                    bG.setSelected(jRUEPS.getModel(), true);
                else if(rs.getString("metcost").compareTo("ultcost")==0)
                    bG.setSelected(jRUltCost.getModel(), true);
                else if(rs.getString("metcost").compareTo("prom")==0)
                    bG.setSelected(jRProm.getModel(), true);
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

    }/*Fin de private void vSelMet(Connection con)*/
        
        
    /*Carga las líneas en el combo*/        
    private void vCargLin(Connection con)
    {
        /*Borra los items en el combobox*/
        jComLin.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComLin.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene todas las líneas de la base de datos*/
        try
        {
            sQ  = "SELECT * FROM lins";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el comb*/
            while(rs.next())                            
                jComLin.addItem(rs.getString("cod"));                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
        }    
        
    }/*Fin de private void vCargLin(Connection con)*/
        
        
    /*Carga todos los datos en todos los combos*/
    private void vCargCombs(Connection con)
    {
        /*Carga las líneas en el combo*/        
        vCargLin(con);                       
        
        /*Selecciona la cadena vacia en el combo*/
        jComLin.setSelectedItem("");
        
        /*Carga las marcas en el combo*/        
        vCargMarc(con);                
        
        /*Selecciona la cadena vacia en el combo de tipos*/
        jComTip.setSelectedItem("");
        
        /*Carga las tipos en el combo*/        
        vCargTip(con);                
        
        /*Selecciona la cadena vacia en el combo*/
        jComMarc.setSelectedItem("");
        
        /*Carga las medidas en el combo*/        
        vCargMed(con);                                
        
        /*Selecciona la cadena vacia en el combo*/
        jComMeds.setSelectedItem("");
        
        /*Carga los fabricantes en el combo*/        
        vCargFab(con);                                
        
        /*Selecciona la cadena vacia en el combo*/
        jComFab.setSelectedItem("");
        
        /*Carga los colores en el combo*/        
        vCargColo(con);                                
        
        /*Selecciona la cadena vacia en el combo*/
        jComCol.setSelectedItem("");
        
        /*Carga los pesos en el combo*/        
        vCargPes(con);                                                                
        
        /*Selecciona la cadena vacia en el combo*/
        jComPeso.setSelectedItem("");
        
        /*Carga las unidades en el combo*/        
        vCargUnid(con);                                                                
        
        /*Selecciona la cadena vacia en el combo*/
        jComUni.setSelectedItem("");
        
        /*Carga las ubicaciones adicionales en el combo*/        
        vCargUbiA(con);                                                                
                 
        /*Selecciona la cadena vacia en el combo*/
        jComUAd.setSelectedItem("");
        
        /*Carga los anaqueles en el combo*/        
        vCargAnaq(con);                                                                                
        
        /*Selecciona la cadena vacia en el combo*/
        jComAna.setSelectedItem("");
        
        /*Carga todas las clasificaciones del producto en el combo*/        
        vCargClasP(con);                                                                                
        
        /*Selecciona la cadena vacia en el combo*/
        jComExt.setSelectedItem("");
        
        /*Carga todos los lugares en el combo*/        
        vCargLug(con);                                                                                
        
        /*Selecciona la cadena vacia en el combo*/
        jComLug.setSelectedItem("");
        
        /*Carga todos los impuestos en el combo*/        
        vCargImpue(con);                                                                                                
        
        /*Selecciona la cadena vacia en el combo*/
        jComImp.setSelectedItem("");
        
        /*Carga todos los Modelos en el combo*/        
        vCargMod(con);                                                                                                
        
        /*Selecciona la cadena vacia en el combo*/
        jComMod.setSelectedItem("");
                
    }/*Fin de private void vCargCombs(Connection con)*/
        
        
    /*Carga las marcas en el combo*/        
    private void vCargMarc(Connection con)
    {
        /*Borra los items en el combobox*/
        jComMarc.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComMarc.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene las marcas y cargalas en el combo*/        
        try
        {
            sQ = "SELECT * FROM marcs";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalas en el conrol*/
            while(rs.next())
                jComMarc.addItem(rs.getString("cod"));            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                
        }    
        
    }/*Fin de private void vCargMarc(Connection con)*/
    
    
    /*Carga los tipos en el combo*/        
    private void vCargTip(Connection con)
    {
        /*Borra los items en el combobox*/
        jComTip.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComTip.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene los tipos y cargalos en el combo*/        
        try
        {
            sQ = "SELECT * FROM tips";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el conrol*/
            while(rs.next())
                jComTip.addItem(rs.getString("cod"));            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
        }    
        
    }/*Fin de private void vCargTip(Connection con)*/
    
    
    /*Carga las medidas en el combo*/        
    private void vCargMed(Connection con)
    {
        /*Borra los items en el combobox*/
        jComMeds.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComMeds.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Carga las medidas en los combo*/        
        try
        {
            sQ  = "SELECT * FROM meds";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el control*/
            while(rs.next())
                jComMeds.addItem(rs.getString("cod"));            

        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
        }    
        
    }/*Fin de private void vCargMeds(Connection con)*/
    
    
    /*Carga los fabricantes en el combo*/        
    private void vCargFab(Connection con)
    {
        /*Borra los items en el combobox*/
        jComFab.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComFab.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Carga los fabricantes en el combo*/        
        try
        {
            sQ  = "SELECT * FROM fabs";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el control*/
            while(rs.next())
                jComFab.addItem(rs.getString("cod"));            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
        }
        
    }/*Fin de private void vCargFabs(Connection con)*/
    
    
    /*Carga los colores en el combo*/        
    private void vCargColo(Connection con)
    {
        /*Borra los items en el combobox*/
        jComCol.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComCol.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Carga los colores en los combo*/        
        try
        {
            sQ  = "SELECT * FROM colos";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combo*/
            while(rs.next())
                jComCol.addItem(rs.getString("cod"));           
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
        }    
        
    }/*Fin de private void vCargColo(Connection con)*/
    
    
    /*Carga los pesos en el combo*/        
    private void vCargPes(Connection con)
    {
        /*Borra los items en el combobox*/
        jComPeso.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComPeso.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene los pesos de la base de datos*/       
        try
        {
            sQ = "SELECT * FROM pes";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combo*/
            while(rs.next())
                jComPeso.addItem(rs.getString("cod"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
        }    
        
    }/*Fin de private void vCargPes(Connection con)*/
    
    
    /*Carga las unidades en el combo*/        
    private void vCargUnid(Connection con)
    {
        /*Borra los items en el combobox*/
        jComUni.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComUni.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene todas las unidades de la base de datos*/        
        try
        {
            sQ  = "SELECT * FROM unids";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces cargalas en el combo*/
            while(rs.next())
                jComUni.addItem(rs.getString("cod"));        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
        }    
        
    }/*Fin de private void vCargUnid(Connection con)*/
    
    
    /*Carga las ubicaciones adicionales en el combo*/        
    private void vCargUbiA(Connection con)
    {
        /*Borra los items en el combobox*/
        jComUAd.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComUAd.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene las ubicacioones adicionales de la base de datos*/        
        try
        {
            sQ  = "SELECT * FROM ubiad";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos al combobox*/
            while(rs.next())
                jComUAd.addItem(rs.getString("cod"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
        }    
        
    }/*Fin de private void vCargUbiA(Connection con)*/
    
    
    /*Carga todos los anaqueles en el combo*/        
    private void vCargAnaq(Connection con)
    {
        /*Borra los items en el combobox*/
        jComAna.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComAna.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene todos los anaqueles de la base de datos*/      
        try
        {
            sQ  = "SELECT * FROM anaqs";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combo*/
            while(rs.next())
                jComAna.addItem(rs.getString("cod"));            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
        }    
        
    }/*Fin de private void vCargAnaq(Connection con)*/
    
    
    /*Carga todas las clasificaciones del producto en el combo*/        
    private void vCargClasP(Connection con)
    {
        /*Borra los items en el combobox*/
        jComExt.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComExt.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene todas las clasificaciones de producto de la base de datos*/        
        try
        {
            sQ  = "SELECT * FROM clasprod";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces agregalas al combobox*/
            while(rs.next())
                jComExt.addItem(rs.getString("cod"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
        }    
        
    }/*Fin de private void vCargClasP(Connection con)*/
    
    
    /*Carga todos los lugares en el combo*/        
    private void vCargLug(Connection con)
    {
        /*Borra los items en el combobox*/
        jComLug.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComLug.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene los lugares de la base de datos*/        
        try
        {
            sQ  = "SELECT * FROM lugs";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el control*/
            while(rs.next())
                jComLug.addItem(rs.getString("cod"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                        
        }    
        
    }/*Fin de private void vCargLug(Connection con)*/
        
    
    /*Carga todos los impuestos en el combo*/        
    private void vCargImpue(Connection con)
    {
        /*Borra los items en el combobox*/
        jComImp.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComImp.addItem("");
        
        //Carga todos los impuestos en el combo
        Star.iCargImpueCom(con, jComImp);                
    }
    
    
    /*Carga todos los Modelos en el combo*/        
    private void vCargMod(Connection con)
    {
        /*Borra los items en el combobox*/
        jComMod.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComMod.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene todos los Modelos de la base de datos*/        
        try
        {
            sQ  = "SELECT cod FROM model";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combo*/
            while(rs.next())
                jComMod.addItem(rs.getString("cod"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                        
        }    
        
    }/*Fin de private void vCargMod(Connection con)*/
    
    
    /*Carga todos los datos de la fila de la tabla de productos en los campos*/
    private synchronized void vLoadDat(String sProd)
    {                           
        /*Almacena el código del producto original para cuando se quiera modificar el código del producto*/        
        sProdOri            = sProd;
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;      
        
        /*Trae el usuario de ùltima modificación y quién se creo*/
        try
        {                  
            sQ = "SELECT estac, estaccrea FROM prods WHERE  prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el resultado en su lugar*/
            if(rs.next())
            {
                jTCread.setText         (rs.getString("estaccrea"));                                
                jTUltEstacMod.setText   (rs.getString("estac"));                                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }
        
        //Declara variable final para el thread        
        final String sProdFi        = sProd;
        
        //Trae los datos de compra y último proveedor del producto
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

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ;

                /*Trae los datos de la última compra del producto*/
                try
                {                  
                    sQ = "SELECT (SELECT COUNT(*) FROM provs) AS count, comprs.ESTAC, comprs.FDOC, comprs.NOMPROV, comprs.NODOC FROM comprs WHERE codcomp = (SELECT codcomp FROM partcomprs WHERE partcomprs.PROD = '" + sProdFi + "' ORDER BY comprs.FALT DESC LIMIT 1 ) ORDER BY fdoc DESC LIMIT 1";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces colocalos en su lugar*/
                    if(rs.next())
                    {                
                        jTUltCom.setText        (rs.getString("fdoc"));
                        jTProv.setText          (rs.getString("nomprov"));
                        jTFac.setText           (rs.getString("nodoc"));
                        jTEstac.setText         (rs.getString("comprs.ESTAC"));                                                               
                    }                        
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
                    return;
                }

                /*Trae la cantidad de proveedores existentes*/
                int iCantProv   =  0;
                try
                {                  
                    sQ = "SELECT COUNT(*) AS count FROM provs";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces obtiene el resultado*/
                    if(rs.next())
                        iCantProv               = Integer.parseInt(rs.getString("count"));                                                        
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
                    return;
                }

                /*Crea un arreglo lo suficientemente grande para guardar los proveedores y otro para guardar las cantidades*/
                String  sProvs[] = new String[iCantProv];
                Long    lCan[]   = new Long[iCantProv];

                /*Obtiene todos los nombres y series de los proveedores*/
                int x = 0;
                try
                {                  
                    sQ = "SELECT prov, ser FROM provs";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces almacenalo en el arreglo*/
                    while(rs.next())
                    {                
                        sProvs[x] = rs.getString("ser") + rs.getString("prov");                                 
                        ++x;                                
                    }                        
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
                    return;
                }

                /*Recorre todos los proveedores*/
                int x1  = 0;
                for (String sProv : sProvs) 
                {
                    /*Obtiene la cantidad de veces que con cada proveedor se a comprado un prodcuto*/
                    try
                    {
                        sQ = "SELECT IFNULL(SUM(cant),0) AS cont FROM partcomprs LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM WHERE comprs.PROV = '" + sProv + "' AND partcomprs.PROD IN('" + sProdFi + "')";                                        
                        st = con.createStatement();
                        rs = st.executeQuery(sQ);
                        /*Si hay datos entonces ve creando el contador*/
                        while(rs.next())
                        {                    
                            lCan[x1]         = Long.parseLong(rs.getString("cont"));
                            ++x1;
                        }
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
                        return;
                    }
                }        

                //Declara variables locales
                long lMay   = 0;
                int  iMay   = 0;
                x1          = 0;

                /*Obtiene el índice del valor mayor*/        
                for (Long lCan1 : lCan) 
                {            
                    /*Si el valor actual es mayor al mayor anterior entonces*/
                    if(lCan1>lMay)
                    {
                        /*Guarda el valor mayor y el índica mayor*/
                        lMay = lCan1;
                        iMay = x1;
                    }

                    /*Aumenta el ciclo*/
                    ++x1;
                }       

                /*Si hay datos para poner entonces*/
                if(lMay>0)
                {
                    if(sProvs[iMay] != null)
                    {
                        /*Obtiene el nombre del proveedor en base a su código del arreglo*/        
                        try
                        {
                            sQ = "SELECT nom FROM provs WHERE CONCAT_WS('', ser, prov ) = '" + sProvs[iMay] + "'";                                         
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos*/
                            if(rs.next())
                            {
                                /*Colocalo en el campo*/
                                jTProvMasCom.setText(rs.getString("nom"));

                                /*Coloca en el campo la cantidad de veces por compra*/
                                jTCant.setText(Integer.toString((int)lMay));
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
                        
                    }/*Fin de if(sProvs[iMay] != null)*/

                }/*Fin de if(iMay>0)*/       

            }//Fin de public void run()
            
        }).start();
        
        /*Obtiene el UEPS*/
        sUeps    = Star.sGetUEPS(con, sProd);            
        
        /*Obtiene el PEPS del producto*/           
        sPeps    = Star.sGetPEPS(con, sProd);
        
        /*Obtiene el costo promedio*/
        sCostP   = Star.sCostProm(con, sProd);            
            
        /*Obtiene el último costo*/
        sUltCost = Star.sUltCost(con, sProd);            
        
        /*Dales formato de moneda a todos los costeos*/                
        NumberFormat nu = NumberFormat.getCurrencyInstance(Locale.US);
        double dCant    = Double.parseDouble(sUeps);                
        sUeps           = nu.format(dCant);
        dCant           = Double.parseDouble(sPeps);                
        sPeps           = nu.format(dCant);
        dCant           = Double.parseDouble(sCostP);                
        sCostP          = nu.format(dCant);
        dCant           = Double.parseDouble(sUltCost);                
        sUltCost        = nu.format(dCant);
        
        /*Trae todos los datos del producto*/
        try
        {
            sQ = "SELECT * FROM prods WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Declara variables de bloque*/
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        
                /*Selecciona el método de costeo correcto para el producto*/
                if(rs.getString("metcost").compareTo("peps")==0)
                    bG.setSelected(jRPEPS.getModel(), true);
                else if(rs.getString("metcost").compareTo("ueps")==0)
                    bG.setSelected(jRUEPS.getModel(), true);
                else if(rs.getString("metcost").compareTo("ultcost")==0)
                    bG.setSelected(jRUltCost.getModel(), true);
                else if(rs.getString("metcost").compareTo("prom")==0)
                    bG.setSelected(jRProm.getModel(), true);
                
                /*Obtiene las listas de precios*/
                sPre1           = rs.getString("prelist1");                           
                sPre2           = rs.getString("prelist2");                           
                sPre3           = rs.getString("prelist3");                           
                sPre4           = rs.getString("prelist4");                           
                sPre5           = rs.getString("prelist5");                           
                sPre6           = rs.getString("prelist6");                           
                sPre7           = rs.getString("prelist7");                           
                sPre8           = rs.getString("prelist8");                           
                sPre9           = rs.getString("prelist9");                           
                sPre10          = rs.getString("prelist10");                           

                /*Obtiene el costo de logística*/                
                sCostL          = rs.getString("costre");                   
                        
                /*Dale formato de moneda a los precios de lista*/                       
                dCant           = Double.parseDouble(sPre1);                                
                sPre1           = n.format(dCant);
                dCant           = Double.parseDouble(sPre2);                                
                sPre2           = n.format(dCant);
                dCant           = Double.parseDouble(sPre3);                                
                sPre3           = n.format(dCant);
                dCant           = Double.parseDouble(sPre4);                                
                sPre4           = n.format(dCant);
                dCant           = Double.parseDouble(sPre5);                                
                sPre5           = n.format(dCant);
                dCant           = Double.parseDouble(sPre6);                                
                sPre6           = n.format(dCant);
                dCant           = Double.parseDouble(sPre7);                                
                sPre7           = n.format(dCant);
                dCant           = Double.parseDouble(sPre8);                                
                sPre8           = n.format(dCant);
                dCant           = Double.parseDouble(sPre9);                                
                sPre9           = n.format(dCant);
                dCant           = Double.parseDouble(sPre10);                                
                sPre10          = n.format(dCant);
                                
                /*Formatea a moneda al costo logística*/                
                dCant           = Double.parseDouble(sCostL);                                   
                sCostL          = n.format(dCant);               
                
                /*Habilita el boton de guardar cambios*/
                jBGuar.setEnabled           (true);
                    
                /*Guarda la unidad original para cuando intente el usuario cambiar de unidad saber y convertir las existencias*/
                sUnidOriP       = rs.getString("unid");
                
                /*Coloca en los controles los valores obtenidos*/
                jTADescrip.setText          (rs.getString("descrip"));
                jTMax.setText               (rs.getString("max"));
                jTMin.setText               (rs.getString("min"));
                jTNom.setText               (rs.getString("descripcort"));
                jTExist.setText             (rs.getString("exist"));
                jTJera.setText              (rs.getString("clasjera"));
                jTAInfor.setText            (rs.getString("infor"));
                jTMedMan.setText            (rs.getString("med"));
                jComPeso.setSelectedItem    (rs.getString("pes"));
                jTPesMan.setText            (rs.getString("pesman"));
                jTCodProv.setText           (rs.getString("provop1"));
                jTCodProv1.setText          (rs.getString("provop2"));                
                jTProd.setText              (rs.getString("prod"));                  
                jLFCreac.setText            (rs.getString("falt"));
                jLFMod.setText              (rs.getString("fmod"));                                
                jTAnot.setText              (rs.getString("descprov"));
                jTGara.setText              (rs.getString("garan"));
                jTCodOp1.setText            (rs.getString("prodop1"));
                jTCodOp2.setText            (rs.getString("prodop2"));
                jComCol.setSelectedItem     (rs.getString("colo"));
                jComUni.setSelectedItem     (rs.getString("unid"));                
                jComLin.setSelectedItem     (rs.getString("lin"));
                jComTip.setSelectedItem     (rs.getString("tip"));
                jComMarc.setSelectedItem    (rs.getString("marc"));
                jComFab.setSelectedItem     (rs.getString("fab"));
                jComMeds.setSelectedItem    (rs.getString("codmed"));
                jComLug.setSelectedItem     (rs.getString("lug"));
                jComImp.setSelectedItem    (rs.getString("impue"));
                jComAna.setSelectedItem     (rs.getString("anaq"));
                jComUAd.setSelectedItem     (rs.getString("codubi"));
                jComExt.setSelectedItem     (rs.getString("codext"));                
                jComMod.setSelectedItem     (rs.getString("mode"));
                                
                /*Coloca el cursor al principio de todos los controles*/
                jTADescrip.setCaretPosition (0);
                jTMax.setCaretPosition      (0);
                jTMin.setCaretPosition      (0);
                jTNom.setCaretPosition      (0);
                jTExist.setCaretPosition    (0);
                jTAInfor.setCaretPosition   (0);
                jTMedMan.setCaretPosition   (0);                
                jTPesMan.setCaretPosition   (0);
                jTCodProv.setCaretPosition  (0);
                jTCodProv1.setCaretPosition (0);                
                jTProd.setCaretPosition     (0);
                jTAnot.setCaretPosition     (0);
                jTCodOp1.setCaretPosition   (0);                
                jTCodOp2.setCaretPosition   (0);
                                
                /*Marca el checkbox si es inventariable o no*/
                if(rs.getString("invent").compareTo("1")==0)
                    jCInvent.setSelected    (true);
                else
                    jCInvent.setSelected    (false);
                
                /*Marca el checkbox si requiere pedimento o no*/
                if(rs.getString("lotped").compareTo("1")==0)
                    jCPed.setSelected       (true);
                else
                    jCPed.setSelected       (false);
                
                /*Marca el checkbox si es servicio o no*/
                if(rs.getString("servi").compareTo("1")==0)
                    jCServ.setSelected  (true);
                else
                    jCServ.setSelected  (false);
                
                /*Marca el checkbox de si solicita máximos o mínimos*/
                if(rs.getString("solmaxmin").compareTo("1")==0)
                    jCNoSolMaxMin.setSelected  (true);
                else
                    jCNoSolMaxMin.setSelected  (false);
                
                //Comprueba si el producto solicita número de serie
                if(rs.getInt("solser")==1)
                    jCNoSer.setSelected  (true);
                else
                    jCNoSer.setSelected  (false);
                
                /*Marca el checkbox de si se puede vender a bajo del costo*/
                if(rs.getString("bajcost").compareTo("1")==0)
                    jCBajCost.setSelected  (true);
                else
                    jCBajCost.setSelected  (false);
                
                /*Marca el checkbox si es para venta o no*/
                if(rs.getString("esvta").compareTo("1")==0)
                    jCEsParaVent.setSelected     (true);
                else
                    jCEsParaVent.setSelected     (false);
                
                /*Marca el checkbox si es compuesto o no*/
                if(rs.getString("compue").compareTo("1")==0)
                {
                    /*Seleccionalo*/
                    jCComp.setSelected      (true);
                    
                    /*Habilita el botón de componentes*/
                    jBComps.setEnabled       (true);
                }
                else
                {
                    /*Deselecionalo*/
                    jCComp.setSelected(false);
                    
                    /*Deshabilitalo el botón de componentes*/
                    jBComps.setEnabled(false);
                }

            }/*Fin de if(rs.next())*/            
            /*Else si no existe entonces limpia todos los campos*/
            else             
               vLim();                        
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
            Star.iErrProc(expnNumForm.getMessage(), Star.sErrNumForm, expnNumForm.getStackTrace(), con);            
            return;
        }
        
        /*Obtiene las utilidades de los precios de lista*/
        try
        {
            sQ = "SELECT IFNULL(CASE WHEN (((IFNULL(prelist1, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist1, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por1, IFNULL(prelist2, 0 ) AS prelist2, IFNULL(CASE WHEN (((IFNULL(prelist2, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist2, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por2, IFNULL(prelist3, 0 ) AS prelist3, IFNULL(CASE WHEN (((IFNULL(prelist3, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist3, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por3, IFNULL(prelist4, 0 ) AS prelist4, IFNULL(CASE WHEN (((IFNULL(prelist4, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist4, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100)  END, 0 ) AS por4, IFNULL(prelist5, 0 ) AS prelist5, IFNULL(CASE WHEN (((IFNULL(prelist5, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist5, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 END, 0 ) AS por5,  IFNULL(prelist6, 0 ) AS prelist6, IFNULL(CASE WHEN (((IFNULL(prelist6, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist6, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por6, IFNULL(prelist7, 0 ) AS prelist7, IFNULL(CASE WHEN (((IFNULL(prelist7, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist7, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por7, IFNULL(prelist8, 0 ) AS prelist8, IFNULL(CASE WHEN (((IFNULL(prelist8, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist8, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100)  END, 0 ) AS por8, IFNULL(prelist9, 0 ) AS prelist9, IFNULL(CASE WHEN (((IFNULL(prelist9, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist9, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por9, IFNULL(prelist10, 0 ) AS prelist10, IFNULL(CASE WHEN  (((IFNULL(prelist10, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist10, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por10 FROM prods WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                                                                
                sUtil1  = rs.getString("por1");
                sUtil2  = rs.getString("por2");
                sUtil3  = rs.getString("por3");
                sUtil4  = rs.getString("por4");
                sUtil5  = rs.getString("por5");
                sUtil6  = rs.getString("por6");
                sUtil7  = rs.getString("por7");
                sUtil8  = rs.getString("por8");
                sUtil9  = rs.getString("por9");
                sUtil10 = rs.getString("por10");
            }            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }            

        /*Obtiene las utilidades de venta del producto*/
        try
        {
            sQ = "SELECT utilvta1, utilvta2, utilvta3, utilvta4, utilvta5, utilvta6, utilvta7, utilvta8, utilvta9, utilvta10 FROM prods WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                                                                
                sUtil1V  = rs.getString("utilvta1");
                sUtil2V  = rs.getString("utilvta2");
                sUtil3V  = rs.getString("utilvta3");
                sUtil4V  = rs.getString("utilvta4");
                sUtil5V  = rs.getString("utilvta5");
                sUtil6V  = rs.getString("utilvta6");
                sUtil7V  = rs.getString("utilvta7");
                sUtil8V  = rs.getString("utilvta8");
                sUtil9V  = rs.getString("utilvta9");
                sUtil10V = rs.getString("utilvta10");
            }            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }            
        
        /*Obtiene la cantidad de asociaciones de Modelo que tiene el producto*/
        try
        {
            sQ = "SELECT COUNT(*) AS cont FROM modelprod WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inicia el arreglo con la cantidad específica de valores*/
            if(rs.next())                            
                sMods   = new String[rs.getInt("cont")];            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }            
        
        /*Obtiene los demás Modelos del producto*/
        int iX   = 0;
        try
        {
            sQ = "SELECT mode FROM modelprod WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())                  
            {
                /*Inicia el valor en el arrelog y aumenta en uno el contador*/
                sMods[iX] = rs.getString("mode");            
                ++iX;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }            
        
        /*Obtiene la cantidad de compatibilidades que tiene el producto*/
        try
        {
            sQ = "SELECT COUNT(*) AS cont FROM compa WHERE prod = '" + sProd.trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inicia el arreglo con la cantidad específica de valores*/
            if(rs.next())                                        
                sCompa   = new String[rs.getInt("cont")];                                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }            
        
        /*Obtiene las compatibilidades del producto*/        
        iX   = 0;
        try
        {
            sQ = "SELECT compa FROM compa WHERE prod = '" + sProd.trim() + "'";                            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())                  
            {                
                /*Inicia el valor en el arreglo y aumenta en uno el contador*/
                sCompa[iX] = rs.getString("compa");            
                ++iX;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }                                
        
        /*Obtiene la cantidad de asociaciones de marcas que tiene el producto*/
        try
        {
            sQ = "SELECT COUNT(*) AS cont FROM marcprod WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inicia el arreglo con la cantidad específica de valores*/
            if(rs.next())                            
                sMarcs   = new String[rs.getInt("cont")];            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }            
        
        /*Obtiene las demás marcas del producto*/        
        iX   = 0;
        try
        {
            sQ = "SELECT marc FROM marcprod WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())                  
            {
                /*Inicia el valor en el arreglo y aumenta en uno el contador*/
                sMarcs[iX] = rs.getString("marc");            
                ++iX;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }            
        
        /*Obtiene la cantidad de asociaciones de marcas que tiene el producto*/
        try
        {
            sQ = "SELECT COUNT(*) AS cont FROM terProdCompa WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inicia el arreglo con la cantidad específica de valores*/
            if(rs.next())                            
                sCompaMarcMod   = new String[rs.getInt("cont")];                                         
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }            
        
        //Si no hay elementos entonces que el arreglo sea nulo
        if(sCompaMarcMod.length==0)
            sCompaMarcMod = null;

        /*Obtiene los demas números de parte del producto*/        
        iX   = 0;
        try
        {
            sQ = "SELECT rut FROM terProdCompa WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())                  
            {
                /*Inicia el valor en el arreglo y aumenta en uno el contador*/
                sCompaMarcMod[iX] = rs.getString("rut");            
                ++iX;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }
        
        /*Obtiene la cantidad de asociaciones de marcas que tiene el producto*/
        try
        {
            sQ = "SELECT COUNT(*) AS cont FROM prodpart WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inicia el arreglo con la cantidad específica de valores*/
            if(rs.next())                            
                sParts   = new String[rs.getInt("cont")];            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }            
        
        /*Obtiene los demas números de parte del producto*/        
        iX   = 0;
        try
        {
            sQ = "SELECT part FROM prodpart WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())                  
            {
                /*Inicia el valor en el arreglo y aumenta en uno el contador*/
                sParts[iX] = rs.getString("part");            
                ++iX;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }            
        
        /*Obtiene la cantidad de series que tiene el producto*/
        try
        {
            sQ = "SELECT COUNT(*) AS cont FROM serieprod WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inicia el arreglo con la cantidad específica de valores*/
            if(rs.next())                            
                sSers   = new String[rs.getInt("cont")][4];            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }            
        
        /*Obtiene las demás series del producto*/        
        iX   = 0;
        try
        {
            sQ = "SELECT ser, comen, exist, alma FROM serieprod WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())                  
            {
                /*Inicia el valor en el arreglo*/
                sSers[iX][0] = rs.getString("ser");            
                sSers[iX][1] = rs.getString("comen");            
                sSers[iX][2] = rs.getString("exist");
                sSers[iX][3] = rs.getString("alma");
                
                /*Aumenta en uno el contador de la fila*/
                ++iX;
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

        /*Muestra que esta cargando la imágen*/
        jLImg.setText("Cargando...");
        
        /*Obtiene la imágen si es que tiene*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Carga la imágen en el control*/
                Star.vGetImg(sProdFi, jLImg);
                
                /*Muestra que se termino de cargar la imágen*/
                jLImg.setText("");
            }
            
        }).start();
                
    }/*Fin de private void vCargaFilaEnControles()*/   
    
    
    /*Metodo para que el formulario no se abra dos veces*/
    public static Prods getObj()
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new Prods();

        /*Devuelve el resultado*/
        return obj;

    }/*Fin de public static AltaRapida getObj()*/

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jTProd = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jBNew = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jComCol = new javax.swing.JComboBox();
        jComUni = new javax.swing.JComboBox();
        jComMeds = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTADescrip = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComLin = new javax.swing.JComboBox();
        jComMarc = new javax.swing.JComboBox();
        jComFab = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jComPeso = new javax.swing.JComboBox();
        jTPeso = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTAInfor = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jTMax = new javax.swing.JTextField();
        jTMin = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jBGuar = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jBLim = new javax.swing.JButton();
        jTLin = new javax.swing.JTextField();
        jTCol = new javax.swing.JTextField();
        jTMeds = new javax.swing.JTextField();
        jTFab = new javax.swing.JTextField();
        jTMarc = new javax.swing.JTextField();
        jTPesMan = new javax.swing.JTextField();
        jTMedMan = new javax.swing.JTextField();
        jTExist = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTUnid = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jComAna = new javax.swing.JComboBox();
        jTAnaq = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jComLug = new javax.swing.JComboBox();
        jTLug = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jComUAd = new javax.swing.JComboBox();
        jTUbi = new javax.swing.JTextField();
        jCInvent = new javax.swing.JCheckBox();
        jLabel24 = new javax.swing.JLabel();
        jTAnot = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTCodOp2 = new javax.swing.JTextField();
        jTCodOp1 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jBCargImg = new javax.swing.JButton();
        jBDelImg = new javax.swing.JButton();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jPInfo = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLFCreac = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTUltCom = new javax.swing.JTextField();
        jLFMod = new javax.swing.JLabel();
        jTProv = new javax.swing.JTextField();
        jTFac = new javax.swing.JTextField();
        jTEstac = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jTProvMasCom = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jTCant = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jTCread = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jTUltEstacMod = new javax.swing.JTextField();
        jBMosT = new javax.swing.JButton();
        jCComp = new javax.swing.JCheckBox();
        jBComps = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        jBProd = new javax.swing.JButton();
        jBGen = new javax.swing.JButton();
        jTCodProv = new javax.swing.JTextField();
        jBBusc2 = new javax.swing.JButton();
        jTCodProv1 = new javax.swing.JTextField();
        jBBusc3 = new javax.swing.JButton();
        jCEsParaVent = new javax.swing.JCheckBox();
        jLabel33 = new javax.swing.JLabel();
        jTNom = new javax.swing.JTextField();
        jComImp = new javax.swing.JComboBox();
        jBCargF = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jBDelF = new javax.swing.JButton();
        jBFTec = new javax.swing.JButton();
        jCNoSolMaxMin = new javax.swing.JCheckBox();
        jCBajCost = new javax.swing.JCheckBox();
        jBPrec = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jComExt = new javax.swing.JComboBox();
        jTClas = new javax.swing.JTextField();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jCServ = new javax.swing.JCheckBox();
        jTImpueVal = new javax.swing.JTextField();
        jBVeGran = new javax.swing.JButton();
        jSImg = new javax.swing.JScrollPane();
        jPanImg = new javax.swing.JPanel();
        jLImg = new javax.swing.JLabel();
        jTAlmaG = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jBMasMarc = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jBMasMod = new javax.swing.JButton();
        jComMod = new javax.swing.JComboBox();
        jTMod = new javax.swing.JTextField();
        jBCompa = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jBPart = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        jBGranDescrip = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jComTip = new javax.swing.JComboBox();
        jTTip = new javax.swing.JTextField();
        jBMasSer = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jTGara = new javax.swing.JTextField();
        jBGara = new javax.swing.JButton();
        jBConsec = new javax.swing.JButton();
        jBConsecU = new javax.swing.JButton();
        jTJera = new javax.swing.JTextField();
        jBJera = new javax.swing.JButton();
        jLabel49 = new javax.swing.JLabel();
        jRPEPS = new javax.swing.JRadioButton();
        jRUEPS = new javax.swing.JRadioButton();
        jRUltCost = new javax.swing.JRadioButton();
        jRProm = new javax.swing.JRadioButton();
        jBExisAlma = new javax.swing.JButton();
        jCPed = new javax.swing.JCheckBox();
        jCNoSer = new javax.swing.JCheckBox();
        jBCompMarcMod = new javax.swing.JButton();

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
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
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

        jTProd.setBackground(new java.awt.Color(255, 255, 153));
        jTProd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTProd.setToolTipText("Ctrl+B búsqueda avanzada");
        jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProd.setNextFocusableComponent(jBProd);
        jTProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProdFocusLost(evt);
            }
        });
        jTProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProdKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTProdKeyTyped(evt);
            }
        });
        jP1.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 140, 20));

        jLabel2.setText("*Código producto:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, -1));

        jLabel3.setText("Fabricante:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 100, -1));

        jLabel4.setText("Peso:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre8.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nuevo producto (Ctrl+N)");
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 60, 150, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jSImg);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 150, 150, 30));

        jLabel13.setText("*Nombre:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 120, -1));

        jComCol.setNextFocusableComponent(jComUni);
        jComCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComColActionPerformed(evt);
            }
        });
        jComCol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComColKeyPressed(evt);
            }
        });
        jP1.add(jComCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 120, 20));

        jComUni.setNextFocusableComponent(jComExt);
        jComUni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComUniFocusLost(evt);
            }
        });
        jComUni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComUniActionPerformed(evt);
            }
        });
        jComUni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComUniKeyPressed(evt);
            }
        });
        jP1.add(jComUni, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 120, 20));

        jComMeds.setNextFocusableComponent(jTMedMan);
        jComMeds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComMedsActionPerformed(evt);
            }
        });
        jComMeds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComMedsKeyPressed(evt);
            }
        });
        jP1.add(jComMeds, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 120, 20));

        jTADescrip.setColumns(20);
        jTADescrip.setLineWrap(true);
        jTADescrip.setRows(5);
        jTADescrip.setToolTipText("Esta descripción se utiliza en el punto de venta");
        jTADescrip.setBorder(null);
        jTADescrip.setNextFocusableComponent(jComLin);
        jTADescrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTADescripFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTADescripFocusLost(evt);
            }
        });
        jTADescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTADescripKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTADescrip);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 280, 40));

        jLabel8.setText("Medida:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jLabel10.setText("Tipo:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 70, -1));

        jLabel11.setText("Marca:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 80, -1));

        jComLin.setNextFocusableComponent(jComTip);
        jComLin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComLinActionPerformed(evt);
            }
        });
        jComLin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComLinKeyPressed(evt);
            }
        });
        jP1.add(jComLin, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 120, 20));

        jComMarc.setNextFocusableComponent(jBMasMarc);
        jComMarc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComMarcActionPerformed(evt);
            }
        });
        jComMarc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComMarcKeyPressed(evt);
            }
        });
        jP1.add(jComMarc, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 120, 20));

        jComFab.setNextFocusableComponent(jComMarc);
        jComFab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComFabActionPerformed(evt);
            }
        });
        jComFab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComFabKeyPressed(evt);
            }
        });
        jP1.add(jComFab, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 120, 20));

        jLabel12.setText("Color:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jComPeso.setNextFocusableComponent(jTPesMan);
        jComPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComPesoActionPerformed(evt);
            }
        });
        jComPeso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComPesoKeyPressed(evt);
            }
        });
        jP1.add(jComPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 120, 20));

        jTPeso.setEditable(false);
        jTPeso.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTPeso.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTPeso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPeso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPesoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPesoFocusLost(evt);
            }
        });
        jTPeso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPesoKeyPressed(evt);
            }
        });
        jP1.add(jTPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, 110, 20));

        jLabel14.setText("Modelo:");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 120, -1));

        jScrollPane2.setNextFocusableComponent(jTAnot);

        jTAInfor.setColumns(20);
        jTAInfor.setLineWrap(true);
        jTAInfor.setRows(5);
        jTAInfor.setBorder(null);
        jTAInfor.setNextFocusableComponent(jTAnot);
        jTAInfor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAInforFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAInforFocusLost(evt);
            }
        });
        jTAInfor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAInforKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTAInfor);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 280, 50));

        jLabel15.setText("Información:");
        jP1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 110, -1));

        jTMax.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTMax.setText("2");
        jTMax.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMax.setNextFocusableComponent(jTCodOp1);
        jTMax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMaxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMaxFocusLost(evt);
            }
        });
        jTMax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMaxKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTMaxKeyTyped(evt);
            }
        });
        jP1.add(jTMax, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 400, 120, 20));

        jTMin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTMin.setText("1");
        jTMin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMin.setNextFocusableComponent(jTMax);
        jTMin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMinFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMinFocusLost(evt);
            }
        });
        jTMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMinKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTMinKeyTyped(evt);
            }
        });
        jP1.add(jTMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 400, 80, 20));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Producto", "Descripción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBBusc);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
        jScrollPane3.setViewportView(jTab);

        jP1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 430, 200));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Productos:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 100, -1));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar cambios (Ctrl+G)");
        jBGuar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBGuar.setNextFocusableComponent(jBNew);
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 30, 150, 30));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/can.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar proucto(s) (Ctrl+SUPR)");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jBLim);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 90, 150, 30));

        jBLim.setBackground(new java.awt.Color(255, 255, 255));
        jBLim.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBLim.setForeground(new java.awt.Color(0, 102, 0));
        jBLim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/limp.png"))); // NOI18N
        jBLim.setText("Limpiar");
        jBLim.setToolTipText("Limpiar todos los campos para un nuevo producto");
        jBLim.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBLim.setNextFocusableComponent(jBSal);
        jBLim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBLimMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBLimMouseExited(evt);
            }
        });
        jBLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimActionPerformed(evt);
            }
        });
        jBLim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBLimKeyPressed(evt);
            }
        });
        jP1.add(jBLim, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 120, 150, 30));

        jTLin.setEditable(false);
        jTLin.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTLin.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTLin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLinFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLinFocusLost(evt);
            }
        });
        jTLin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLinKeyPressed(evt);
            }
        });
        jP1.add(jTLin, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, 110, 20));

        jTCol.setEditable(false);
        jTCol.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTCol.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jP1.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 110, 20));

        jTMeds.setEditable(false);
        jTMeds.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTMeds.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTMeds.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMeds.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMedsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMedsFocusLost(evt);
            }
        });
        jTMeds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMedsKeyPressed(evt);
            }
        });
        jP1.add(jTMeds, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 110, 20));

        jTFab.setEditable(false);
        jTFab.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTFab.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTFab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFab.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFabFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFabFocusLost(evt);
            }
        });
        jTFab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFabKeyPressed(evt);
            }
        });
        jP1.add(jTFab, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 110, 20));

        jTMarc.setEditable(false);
        jTMarc.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTMarc.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTMarc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMarc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMarcFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMarcFocusLost(evt);
            }
        });
        jTMarc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMarcKeyPressed(evt);
            }
        });
        jP1.add(jTMarc, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 110, 20));

        jTPesMan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTPesMan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPesMan.setNextFocusableComponent(jComCol);
        jTPesMan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPesManFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPesManFocusLost(evt);
            }
        });
        jTPesMan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPesManKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPesManKeyTyped(evt);
            }
        });
        jP1.add(jTPesMan, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 50, 20));

        jTMedMan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTMedMan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMedMan.setNextFocusableComponent(jComPeso);
        jTMedMan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMedManFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMedManFocusLost(evt);
            }
        });
        jTMedMan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMedManKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTMedManKeyTyped(evt);
            }
        });
        jP1.add(jTMedMan, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 50, 20));

        jTExist.setEditable(false);
        jTExist.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTExist.setFocusable(false);
        jTExist.setNextFocusableComponent(jBExisAlma);
        jTExist.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTExistFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTExistFocusLost(evt);
            }
        });
        jTExist.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTExistKeyPressed(evt);
            }
        });
        jP1.add(jTExist, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 610, 80, -1));

        jLabel18.setText("Impuesto:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 90, -1));

        jTUnid.setEditable(false);
        jTUnid.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTUnid.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTUnid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUnid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUnidFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUnidFocusLost(evt);
            }
        });
        jTUnid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUnidKeyPressed(evt);
            }
        });
        jP1.add(jTUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, 110, 20));

        jPanel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel2KeyPressed(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComAna.setNextFocusableComponent(jComLug);
        jComAna.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComAnaFocusLost(evt);
            }
        });
        jComAna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComAnaActionPerformed(evt);
            }
        });
        jComAna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComAnaKeyPressed(evt);
            }
        });
        jPanel2.add(jComAna, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 120, 20));

        jTAnaq.setEditable(false);
        jTAnaq.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTAnaq.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAnaq.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAnaqFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAnaqFocusLost(evt);
            }
        });
        jTAnaq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAnaqKeyPressed(evt);
            }
        });
        jPanel2.add(jTAnaq, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 130, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Anaquel:");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 60, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Ubicación Adicional:");
        jPanel2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 110, -1));

        jComLug.setNextFocusableComponent(jComUAd);
        jComLug.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComLugFocusLost(evt);
            }
        });
        jComLug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComLugActionPerformed(evt);
            }
        });
        jComLug.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComLugKeyPressed(evt);
            }
        });
        jPanel2.add(jComLug, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 120, 20));

        jTLug.setEditable(false);
        jTLug.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTLug.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLug.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLugFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLugFocusLost(evt);
            }
        });
        jTLug.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLugKeyPressed(evt);
            }
        });
        jPanel2.add(jTLug, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 130, 20));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel37.setText("Lugar:");
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 60, -1));

        jComUAd.setName(""); // NOI18N
        jComUAd.setNextFocusableComponent(jBComps);
        jComUAd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComUAdFocusLost(evt);
            }
        });
        jComUAd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComUAdActionPerformed(evt);
            }
        });
        jComUAd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComUAdKeyPressed(evt);
            }
        });
        jPanel2.add(jComUAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 120, 20));

        jTUbi.setEditable(false);
        jTUbi.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTUbi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUbi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUbiFocusLost(evt);
            }
        });
        jPanel2.add(jTUbi, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 130, 20));

        jP1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 470, 430, 100));

        jCInvent.setBackground(new java.awt.Color(255, 255, 255));
        jCInvent.setSelected(true);
        jCInvent.setText("Inventariable");
        jCInvent.setName(""); // NOI18N
        jCInvent.setNextFocusableComponent(jCComp);
        jCInvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCInventActionPerformed(evt);
            }
        });
        jCInvent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCInventKeyPressed(evt);
            }
        });
        jP1.add(jCInvent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 110, -1));

        jLabel24.setText("Código Opcional 2:");
        jP1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 120, -1));

        jTAnot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAnot.setNextFocusableComponent(jTMin);
        jTAnot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAnotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAnotFocusLost(evt);
            }
        });
        jTAnot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAnotKeyPressed(evt);
            }
        });
        jP1.add(jTAnot, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, 280, 20));

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("*Máximo:");
        jP1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 400, 60, -1));

        jLabel29.setText("*Mínimo:");
        jP1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 60, -1));

        jTCodOp2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTCodOp2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodOp2.setNextFocusableComponent(jTCodProv1);
        jTCodOp2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodOp2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodOp2FocusLost(evt);
            }
        });
        jTCodOp2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodOp2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTCodOp2KeyReleased(evt);
            }
        });
        jP1.add(jTCodOp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 440, 80, 20));

        jTCodOp1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTCodOp1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodOp1.setNextFocusableComponent(jTCodProv);
        jTCodOp1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodOp1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodOp1FocusLost(evt);
            }
        });
        jTCodOp1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodOp1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTCodOp1KeyReleased(evt);
            }
        });
        jP1.add(jTCodOp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 80, 20));

        jLabel30.setText("Num.Partes, Compatibilidades  y Series:");
        jP1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 240, -1));

        jBCargImg.setBackground(new java.awt.Color(255, 255, 255));
        jBCargImg.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBCargImg.setText("Cargar");
        jBCargImg.setToolTipText("Cargar imágen de producto");
        jBCargImg.setNextFocusableComponent(jBDelImg);
        jBCargImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCargImgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCargImgMouseExited(evt);
            }
        });
        jBCargImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCargImgActionPerformed(evt);
            }
        });
        jBCargImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCargImgKeyPressed(evt);
            }
        });
        jP1.add(jBCargImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 420, -1, -1));

        jBDelImg.setBackground(new java.awt.Color(255, 255, 255));
        jBDelImg.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBDelImg.setText("Borrar");
        jBDelImg.setToolTipText("Borrar imágen de producto");
        jBDelImg.setNextFocusableComponent(jBVeGran);
        jBDelImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDelImgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDelImgMouseExited(evt);
            }
        });
        jBDelImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelImgActionPerformed(evt);
            }
        });
        jBDelImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelImgKeyPressed(evt);
            }
        });
        jP1.add(jBDelImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 420, 59, -1));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 230, 130, 19));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 230, 180, -1));

        jPInfo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Creado:");
        jPInfo.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 50, -1));

        jLFCreac.setForeground(new java.awt.Color(51, 51, 255));
        jLFCreac.setText("-");
        jPInfo.add(jLFCreac, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 160, -1));

        jLabel26.setText("Última modificación por:");
        jPInfo.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 170, -1));

        jTUltCom.setEditable(false);
        jTUltCom.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTUltCom.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTUltCom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUltCom.setNextFocusableComponent(jTProv);
        jTUltCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUltComFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUltComFocusLost(evt);
            }
        });
        jTUltCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUltComKeyPressed(evt);
            }
        });
        jPInfo.add(jTUltCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 220, 20));

        jLFMod.setForeground(new java.awt.Color(51, 51, 255));
        jLFMod.setText("-");
        jPInfo.add(jLFMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 160, -1));

        jTProv.setEditable(false);
        jTProv.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTProv.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProv.setNextFocusableComponent(jTFac);
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
        jPInfo.add(jTProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 220, 20));

        jTFac.setEditable(false);
        jTFac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTFac.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTFac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFac.setNextFocusableComponent(jTEstac);
        jTFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFacFocusLost(evt);
            }
        });
        jTFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFacKeyPressed(evt);
            }
        });
        jPInfo.add(jTFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 220, 20));

        jTEstac.setEditable(false);
        jTEstac.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTEstac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEstac.setNextFocusableComponent(jTProvMasCom);
        jTEstac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstacFocusLost(evt);
            }
        });
        jTEstac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstacKeyPressed(evt);
            }
        });
        jPInfo.add(jTEstac, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 220, 20));

        jLabel34.setText("Todos los comprados:");
        jPInfo.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 170, -1));

        jLabel6.setText("Folio última compra:");
        jPInfo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 170, -1));

        jLabel27.setText("Proveedor última compra:");
        jPInfo.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 170, -1));

        jLabel38.setText("Usuario última compra:");
        jPInfo.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 170, -1));

        jTProvMasCom.setEditable(false);
        jTProvMasCom.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTProvMasCom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProvMasCom.setNextFocusableComponent(jTCant);
        jTProvMasCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProvMasComFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProvMasComFocusLost(evt);
            }
        });
        jPInfo.add(jTProvMasCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 220, 20));

        jLabel39.setText("Proveedor mas comprado:");
        jPInfo.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 170, -1));

        jTCant.setEditable(false);
        jTCant.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCant.setNextFocusableComponent(jTAnaq);
        jTCant.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCantFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCantFocusLost(evt);
            }
        });
        jPInfo.add(jTCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 220, 20));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setText("Modificado:");
        jPInfo.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 80, -1));

        jLabel41.setText("Fecha última compra:");
        jPInfo.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 170, -1));

        jTCread.setEditable(false);
        jTCread.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTCread.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCread.setNextFocusableComponent(jTUltCom);
        jTCread.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCreadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCreadFocusLost(evt);
            }
        });
        jPInfo.add(jTCread, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 220, 20));

        jLabel42.setText("Creado por:");
        jPInfo.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 170, -1));

        jTUltEstacMod.setEditable(false);
        jTUltEstacMod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTUltEstacMod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUltEstacMod.setNextFocusableComponent(jTCread);
        jTUltEstacMod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUltEstacModFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUltEstacModFocusLost(evt);
            }
        });
        jPInfo.add(jTUltEstacMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 220, 20));

        jP1.add(jPInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 250, 430, 210));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setNextFocusableComponent(jTUltEstacMod);
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 230, 120, 19));

        jCComp.setBackground(new java.awt.Color(255, 255, 255));
        jCComp.setText("Kit");
        jCComp.setNextFocusableComponent(jCPed);
        jCComp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCCompActionPerformed(evt);
            }
        });
        jCComp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCompKeyPressed(evt);
            }
        });
        jP1.add(jCComp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 560, 90, -1));

        jBComps.setBackground(new java.awt.Color(255, 255, 255));
        jBComps.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBComps.setText("Componentes");
        jBComps.setToolTipText("Agrega los componentes del kit");
        jBComps.setEnabled(false);
        jBComps.setNextFocusableComponent(jTExist);
        jBComps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCompsActionPerformed(evt);
            }
        });
        jBComps.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCompsKeyPressed(evt);
            }
        });
        jP1.add(jBComps, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 570, 110, 20));

        jLabel35.setText("Costos:");
        jP1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 460, 90, 20));

        jBProd.setBackground(new java.awt.Color(255, 255, 255));
        jBProd.setText("...");
        jBProd.setToolTipText("Buscar Producto(s)");
        jBProd.setNextFocusableComponent(jBConsec);
        jBProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProdMouseExited(evt);
            }
        });
        jBProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProdActionPerformed(evt);
            }
        });
        jBProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProdKeyPressed(evt);
            }
        });
        jP1.add(jBProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 30, 20));

        jBGen.setBackground(new java.awt.Color(255, 255, 255));
        jBGen.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/gen.png"))); // NOI18N
        jBGen.setText(" F10");
        jBGen.setToolTipText("Generar aleatoriamente un código de producto");
        jBGen.setNextFocusableComponent(jTNom);
        jBGen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGenMouseExited(evt);
            }
        });
        jBGen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGenActionPerformed(evt);
            }
        });
        jBGen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGenKeyPressed(evt);
            }
        });
        jP1.add(jBGen, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 90, 20));

        jTCodProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodProv.setName(""); // NOI18N
        jTCodProv.setNextFocusableComponent(jBBusc2);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCodProvKeyTyped(evt);
            }
        });
        jP1.add(jTCodProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 420, 90, 20));

        jBBusc2.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc2.setText("...");
        jBBusc2.setToolTipText("Buscar proveedor(es)");
        jBBusc2.setNextFocusableComponent(jTCodOp2);
        jBBusc2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBusc2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBusc2MouseExited(evt);
            }
        });
        jBBusc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBusc2ActionPerformed(evt);
            }
        });
        jBBusc2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBusc2KeyPressed(evt);
            }
        });
        jP1.add(jBBusc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 420, 30, 20));

        jTCodProv1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodProv1.setNextFocusableComponent(jBBusc3);
        jTCodProv1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodProv1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodProv1FocusLost(evt);
            }
        });
        jTCodProv1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodProv1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCodProv1KeyTyped(evt);
            }
        });
        jP1.add(jTCodProv1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 440, 90, 20));

        jBBusc3.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc3.setText("...");
        jBBusc3.setToolTipText("Buscar proveedor(es)");
        jBBusc3.setNextFocusableComponent(jTGara);
        jBBusc3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBusc3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBusc3MouseExited(evt);
            }
        });
        jBBusc3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBusc3ActionPerformed(evt);
            }
        });
        jBBusc3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBusc3KeyPressed(evt);
            }
        });
        jP1.add(jBBusc3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 440, 30, 20));

        jCEsParaVent.setBackground(new java.awt.Color(255, 255, 255));
        jCEsParaVent.setSelected(true);
        jCEsParaVent.setText("Es para venta");
        jCEsParaVent.setNextFocusableComponent(jCNoSolMaxMin);
        jCEsParaVent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCEsParaVentKeyPressed(evt);
            }
        });
        jP1.add(jCEsParaVent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 120, -1));

        jLabel33.setText("Descripción:");
        jP1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, -1));

        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNom.setNextFocusableComponent(jTADescrip);
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
        jP1.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 280, 20));

        jComImp.setNextFocusableComponent(jTAInfor);
        jComImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComImpFocusLost(evt);
            }
        });
        jComImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComImpActionPerformed(evt);
            }
        });
        jComImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComImpKeyPressed(evt);
            }
        });
        jP1.add(jComImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 309, 120, 20));

        jBCargF.setBackground(new java.awt.Color(255, 255, 255));
        jBCargF.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBCargF.setText("Cargar");
        jBCargF.setToolTipText("Cargar ficha técnica para producto");
        jBCargF.setNextFocusableComponent(jBDelF);
        jBCargF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCargFMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCargFMouseExited(evt);
            }
        });
        jBCargF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCargFActionPerformed(evt);
            }
        });
        jBCargF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCargFKeyPressed(evt);
            }
        });
        jP1.add(jBCargF, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 600, -1, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Ficha Técnica:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 580, 110, -1));

        jBDelF.setBackground(new java.awt.Color(255, 255, 255));
        jBDelF.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBDelF.setText("Borrar");
        jBDelF.setToolTipText("Borrar archivo de ficha técnica");
        jBDelF.setNextFocusableComponent(jBFTec);
        jBDelF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDelFMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDelFMouseExited(evt);
            }
        });
        jBDelF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelFActionPerformed(evt);
            }
        });
        jBDelF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelFKeyPressed(evt);
            }
        });
        jP1.add(jBDelF, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 600, -1, 20));

        jBFTec.setBackground(new java.awt.Color(255, 255, 255));
        jBFTec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBFTec.setToolTipText("Ver Ficha Técnica Completa");
        jBFTec.setNextFocusableComponent(jBGuar);
        jBFTec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBFTecMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBFTecMouseExited(evt);
            }
        });
        jBFTec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFTecActionPerformed(evt);
            }
        });
        jBFTec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBFTecKeyPressed(evt);
            }
        });
        jP1.add(jBFTec, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 600, 30, 20));

        jCNoSolMaxMin.setBackground(new java.awt.Color(255, 255, 255));
        jCNoSolMaxMin.setText("No solicitar MN");
        jCNoSolMaxMin.setToolTipText("No solicitar Máximos y Mínimos");
        jCNoSolMaxMin.setNextFocusableComponent(jCBajCost);
        jCNoSolMaxMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCNoSolMaxMinKeyPressed(evt);
            }
        });
        jP1.add(jCNoSolMaxMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 120, -1));

        jCBajCost.setBackground(new java.awt.Color(255, 255, 255));
        jCBajCost.setText("Permitir vender abajo del costo");
        jCBajCost.setToolTipText("Vender abajo del Costo");
        jCBajCost.setNextFocusableComponent(jCServ);
        jP1.add(jCBajCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 210, 20));

        jBPrec.setBackground(new java.awt.Color(255, 255, 255));
        jBPrec.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBPrec.setText("$");
        jBPrec.setToolTipText("");
        jBPrec.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jBPrec.setNextFocusableComponent(jRPEPS);
        jBPrec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBPrecMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBPrecMouseExited(evt);
            }
        });
        jBPrec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPrecActionPerformed(evt);
            }
        });
        jBPrec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPrecKeyPressed(evt);
            }
        });
        jP1.add(jBPrec, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 480, 60, 20));

        jLabel36.setText("*Unidad:");
        jP1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jComExt.setNextFocusableComponent(jComImp);
        jComExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComExtActionPerformed(evt);
            }
        });
        jComExt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComExtKeyPressed(evt);
            }
        });
        jP1.add(jComExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 120, 20));

        jTClas.setEditable(false);
        jTClas.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTClas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTClas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTClasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTClasFocusLost(evt);
            }
        });
        jTClas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTClasKeyPressed(evt);
            }
        });
        jP1.add(jTClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 290, 110, 20));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 620, 140, 20));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 12, 130, 18));

        jCServ.setBackground(new java.awt.Color(255, 255, 255));
        jCServ.setText("Servicio");
        jCServ.setNextFocusableComponent(jCInvent);
        jCServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCServActionPerformed(evt);
            }
        });
        jCServ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCServKeyPressed(evt);
            }
        });
        jP1.add(jCServ, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 110, -1));

        jTImpueVal.setEditable(false);
        jTImpueVal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTImpueVal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTImpueValFocusLost(evt);
            }
        });
        jP1.add(jTImpueVal, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 309, 110, 20));

        jBVeGran.setBackground(new java.awt.Color(255, 255, 255));
        jBVeGran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBVeGran.setToolTipText("Ver imágen  de producto completa");
        jBVeGran.setName(""); // NOI18N
        jBVeGran.setNextFocusableComponent(jTProd);
        jBVeGran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVeGranMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVeGranMouseExited(evt);
            }
        });
        jBVeGran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVeGranActionPerformed(evt);
            }
        });
        jBVeGran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVeGranKeyPressed(evt);
            }
        });
        jP1.add(jBVeGran, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 420, 30, 20));

        jSImg.setNextFocusableComponent(jBCargImg);

        jPanImg.setBackground(new java.awt.Color(255, 255, 204));
        jPanImg.setNextFocusableComponent(jBCargImg);
        jPanImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanImgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanImgMouseExited(evt);
            }
        });
        jPanImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanImgKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanImgLayout = new javax.swing.GroupLayout(jPanImg);
        jPanImg.setLayout(jPanImgLayout);
        jPanImgLayout.setHorizontalGroup(
            jPanImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanImgLayout.createSequentialGroup()
                .addComponent(jLImg)
                .addGap(0, 150, Short.MAX_VALUE))
        );
        jPanImgLayout.setVerticalGroup(
            jPanImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanImgLayout.createSequentialGroup()
                .addComponent(jLImg)
                .addContainerGap(221, Short.MAX_VALUE))
        );

        jSImg.setViewportView(jPanImg);

        jP1.add(jSImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 180, 150, 240));

        jTAlmaG.setEditable(false);
        jTAlmaG.setFocusable(false);
        jP1.add(jTAlmaG, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 80, -1));

        jLabel43.setText("Código Opcional 1:");
        jP1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 120, -1));

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Proveedor:");
        jP1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 420, 70, -1));

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Proveedor:");
        jP1.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 440, 70, -1));

        jBMasMarc.setBackground(new java.awt.Color(255, 255, 255));
        jBMasMarc.setText("+");
        jBMasMarc.setToolTipText("Asociar mas marcas");
        jBMasMarc.setNextFocusableComponent(jComMod);
        jBMasMarc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMasMarcMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMasMarcMouseExited(evt);
            }
        });
        jBMasMarc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMasMarcActionPerformed(evt);
            }
        });
        jBMasMarc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMasMarcKeyPressed(evt);
            }
        });
        jP1.add(jBMasMarc, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 50, 20));

        jLabel16.setText("Clasificación extra:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 120, -1));

        jBMasMod.setBackground(new java.awt.Color(255, 255, 255));
        jBMasMod.setText("+");
        jBMasMod.setToolTipText("Asociar mas modelos");
        jBMasMod.setNextFocusableComponent(jBPart);
        jBMasMod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMasModMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMasModMouseExited(evt);
            }
        });
        jBMasMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMasModActionPerformed(evt);
            }
        });
        jBMasMod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMasModKeyPressed(evt);
            }
        });
        jP1.add(jBMasMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 50, 20));

        jComMod.setNextFocusableComponent(jBMasMod);
        jComMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComModActionPerformed(evt);
            }
        });
        jComMod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComModKeyPressed(evt);
            }
        });
        jP1.add(jComMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 120, 20));

        jTMod.setEditable(false);
        jTMod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTMod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTModFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTModFocusLost(evt);
            }
        });
        jTMod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTModKeyPressed(evt);
            }
        });
        jP1.add(jTMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 170, 110, 20));

        jBCompa.setBackground(new java.awt.Color(255, 255, 255));
        jBCompa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/compa.png"))); // NOI18N
        jBCompa.setToolTipText("Ver las compatibilidades del producto");
        jBCompa.setName(""); // NOI18N
        jBCompa.setNextFocusableComponent(jBMasSer);
        jBCompa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCompaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCompaMouseExited(evt);
            }
        });
        jBCompa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCompaActionPerformed(evt);
            }
        });
        jBCompa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCompaKeyPressed(evt);
            }
        });
        jP1.add(jBCompa, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 60, 20));

        jLabel46.setText("Anotaciones:");
        jP1.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 120, -1));

        jBPart.setBackground(new java.awt.Color(255, 255, 255));
        jBPart.setText("+");
        jBPart.setToolTipText("Asociar números de partes");
        jBPart.setNextFocusableComponent(jBCompa);
        jBPart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPartActionPerformed(evt);
            }
        });
        jBPart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPartKeyPressed(evt);
            }
        });
        jP1.add(jBPart, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 50, 20));

        jLabel47.setText("Existencia general:");
        jP1.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 590, 140, -1));

        jBGranDescrip.setBackground(new java.awt.Color(0, 153, 153));
        jBGranDescrip.setToolTipText("Ver/Modificar Descripción en Grande");
        jBGranDescrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGranDescripActionPerformed(evt);
            }
        });
        jBGranDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGranDescripKeyPressed(evt);
            }
        });
        jP1.add(jBGranDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 10, 20));

        jLabel19.setText("Línea:");
        jP1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 70, -1));

        jComTip.setNextFocusableComponent(jComFab);
        jComTip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComTipActionPerformed(evt);
            }
        });
        jComTip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComTipKeyPressed(evt);
            }
        });
        jP1.add(jComTip, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 120, 20));

        jTTip.setEditable(false);
        jTTip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jP1.add(jTTip, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 110, 20));

        jBMasSer.setBackground(new java.awt.Color(255, 255, 255));
        jBMasSer.setText("+");
        jBMasSer.setToolTipText("Ver las series del producto");
        jBMasSer.setNextFocusableComponent(jComMeds);
        jBMasSer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMasSerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMasSerMouseExited(evt);
            }
        });
        jBMasSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMasSerActionPerformed(evt);
            }
        });
        jBMasSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMasSerKeyPressed(evt);
            }
        });
        jP1.add(jBMasSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 50, 20));

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("Clasificación:");
        jP1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 480, 80, -1));

        jTGara.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTGara.setNextFocusableComponent(jBGara);
        jTGara.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTGaraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTGaraFocusLost(evt);
            }
        });
        jTGara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTGaraKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTGaraKeyTyped(evt);
            }
        });
        jP1.add(jTGara, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 460, 90, 20));

        jBGara.setBackground(new java.awt.Color(255, 255, 255));
        jBGara.setText("...");
        jBGara.setToolTipText("Buscar garantía");
        jBGara.setNextFocusableComponent(jTJera);
        jBGara.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGaraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGaraMouseExited(evt);
            }
        });
        jBGara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGaraActionPerformed(evt);
            }
        });
        jBGara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGaraKeyPressed(evt);
            }
        });
        jP1.add(jBGara, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 460, 30, 20));

        jBConsec.setBackground(new java.awt.Color(255, 255, 255));
        jBConsec.setToolTipText("Obtiene el primer consecutivo del código");
        jBConsec.setNextFocusableComponent(jBConsecU);
        jBConsec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBConsecMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBConsecMouseExited(evt);
            }
        });
        jBConsec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBConsecActionPerformed(evt);
            }
        });
        jBConsec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBConsecKeyPressed(evt);
            }
        });
        jP1.add(jBConsec, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 10, 20));

        jBConsecU.setBackground(new java.awt.Color(255, 255, 255));
        jBConsecU.setToolTipText("Obtiene el último consecutivo del código");
        jBConsecU.setNextFocusableComponent(jBGen);
        jBConsecU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBConsecUMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBConsecUMouseExited(evt);
            }
        });
        jBConsecU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBConsecUActionPerformed(evt);
            }
        });
        jBConsecU.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBConsecUKeyPressed(evt);
            }
        });
        jP1.add(jBConsecU, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 10, 20));

        jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTJera.setNextFocusableComponent(jBJera);
        jTJera.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTJeraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTJeraFocusLost(evt);
            }
        });
        jTJera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTJeraKeyPressed(evt);
            }
        });
        jP1.add(jTJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 480, 90, 20));

        jBJera.setBackground(new java.awt.Color(255, 255, 255));
        jBJera.setText("...");
        jBJera.setToolTipText("Buscar jerárquia(s)");
        jBJera.setNextFocusableComponent(jCEsParaVent);
        jBJera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBJeraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBJeraMouseExited(evt);
            }
        });
        jBJera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBJeraActionPerformed(evt);
            }
        });
        jBJera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBJeraKeyPressed(evt);
            }
        });
        jP1.add(jBJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 480, 30, 20));

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Garantía:");
        jP1.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 460, 70, -1));

        jRPEPS.setBackground(new java.awt.Color(255, 255, 255));
        jRPEPS.setText("PEPS");
        jRPEPS.setNextFocusableComponent(jRUEPS);
        jRPEPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRPEPSKeyPressed(evt);
            }
        });
        jP1.add(jRPEPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 610, 70, -1));

        jRUEPS.setBackground(new java.awt.Color(255, 255, 255));
        jRUEPS.setText("UEPS");
        jRUEPS.setNextFocusableComponent(jRUltCost);
        jRUEPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRUEPSKeyPressed(evt);
            }
        });
        jP1.add(jRUEPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 610, 70, -1));

        jRUltCost.setBackground(new java.awt.Color(255, 255, 255));
        jRUltCost.setSelected(true);
        jRUltCost.setText("Ult.Costo");
        jRUltCost.setNextFocusableComponent(jRProm);
        jRUltCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRUltCostKeyPressed(evt);
            }
        });
        jP1.add(jRUltCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 610, 90, -1));

        jRProm.setBackground(new java.awt.Color(255, 255, 255));
        jRProm.setText("Promedio");
        jRProm.setNextFocusableComponent(jTab);
        jRProm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRPromKeyPressed(evt);
            }
        });
        jP1.add(jRProm, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 610, 100, -1));

        jBExisAlma.setBackground(new java.awt.Color(255, 255, 255));
        jBExisAlma.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBExisAlma.setForeground(new java.awt.Color(0, 102, 0));
        jBExisAlma.setText("Exist. almacenes");
        jBExisAlma.setToolTipText("Existencias del producto por almacén");
        jBExisAlma.setNextFocusableComponent(jBCargF);
        jBExisAlma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBExisAlmaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBExisAlmaMouseExited(evt);
            }
        });
        jBExisAlma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExisAlmaActionPerformed(evt);
            }
        });
        jBExisAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBExisAlmaKeyPressed(evt);
            }
        });
        jP1.add(jBExisAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 610, 130, 20));

        jCPed.setBackground(new java.awt.Color(255, 255, 255));
        jCPed.setText("Pedimento");
        jCPed.setToolTipText("Manejar lote y pedimento para el producto");
        jCPed.setNextFocusableComponent(jCNoSer);
        jCPed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCPedKeyPressed(evt);
            }
        });
        jP1.add(jCPed, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 583, 100, 20));

        jCNoSer.setBackground(new java.awt.Color(255, 255, 255));
        jCNoSer.setText("No. serie");
        jCNoSer.setNextFocusableComponent(jBPrec);
        jCNoSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCNoSerKeyPressed(evt);
            }
        });
        jP1.add(jCNoSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 600, 80, 30));

        jBCompMarcMod.setBackground(new java.awt.Color(255, 255, 255));
        jBCompMarcMod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/compa.png"))); // NOI18N
        jBCompMarcMod.setToolTipText("Ver las compatibilidades del producto");
        jBCompMarcMod.setName(""); // NOI18N
        jBCompMarcMod.setNextFocusableComponent(jBMasSer);
        jBCompMarcMod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCompMarcModMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCompMarcModMouseExited(evt);
            }
        });
        jBCompMarcMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCompMarcModActionPerformed(evt);
            }
        });
        jBCompMarcMod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCompMarcModKeyPressed(evt);
            }
        });
        jP1.add(jBCompMarcMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 500, 120, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 1021, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
                   
        /*Si el campo del código del producto esta vacio no puede seguir*/
        if(jTProd.getText().replace(" ", "").trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del código del producto esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTProd.grabFocus();           
            return;
        }
        
        
        
        /*Almacena el código del producto original para cuando se quiera modificar el código del producto artículo*/
        sProdOri         = jTProd.getText().replace(" ", "").trim();                        
        
        /*Si el código del producto tiene carácteres no permitidos entonces*/
        if(sProdOri.contains("/") || sProdOri.contains("\\") || sProdOri.contains("\"") || sProdOri.contains("?") || sProdOri.contains(":") || sProdOri.contains("|") || sProdOri.contains("*"))
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El código del producto no puede tener los siguientes carácteres especiales: /\\:?*<>|\"", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));             
            return;
        }            
        
        /*Si no ha seleccionado por lo menos una unidad entonces*/
        if(jComUni.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComUni.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una unidad para el producto.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jComUni.grabFocus();          
            return;
        }
        
        //Si hay asociacion de marca y modelo tiene que tener un tipo
        if(sCompaMarcMod!=null && jComTip.getSelectedItem().toString().trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComTip.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Si realiza relaciones con marca y producto es necesario tener un tipo asignado", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jComTip.grabFocus();           
            return;
        }
        
        /*Si no tiene seleccionado kit, servicio e inventariable entonces*/
        if(!jCServ.isSelected() && !jCInvent.isSelected() && !jCComp.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos kit, servicio o inventariable.", "Guardar cambios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el check de inventariable y regresa*/
            jCInvent.grabFocus();
            return;
        }
        
        /*Lee el mínimo*/
        String sMin                    = jTMin.getText();

        /*Lee el máximo*/
        String sMax                    = jTMax.getText();
        
        /*Lee si se tiene que evaluar máximo y mínimo*/        
        int  iEv                = 0;
        if(jCNoSolMaxMin.isSelected())
        {
            /*Coloca el máximo y el mínimo en 0* en caso de que sean cadenas vacias*/            
            if(sMin.compareTo("")==0)
                sMin = "0";
            if(sMax.compareTo("")==0)
                sMax = "0";
                                    
            /*Inicializa la opción para que no solicite máximos y mínimos*/
            iEv = 1;
        }
        
        /*Si el campo de mínimo esta vacio no puede seguir*/
        if(sMin.compareTo("")==0 && iEv == 0)
        {
            /*Coloca el borde rojo*/                               
            jTMin.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo mínimo esta vacio.", "Agregar producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edción y regresa*/
            jTMin.grabFocus();           
            return;
        }
        
        /*Si el campo de mínimo esta en 0 no puede seguir*/
        if(Float.parseFloat(sMin) == 0 && iEv == 0)
        {
            /*Coloca el borde rojo*/                               
            jTMin.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de mínimo está en 0.", "Agregar producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edición y regresa*/
            jTMin.grabFocus();           
            return;
        }        
                
        /*Si el campo de máximo esta vacio no puede seguir*/
        if(sMax.compareTo("")==0 && iEv == 0)
        {
            /*Coloca el borde rojo*/                               
            jTMax.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo máximo esta vacio.", "Agregar producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edción y regresa*/
            jTMax.grabFocus();           
            return;
        }
        
        /*Si el campo de máximo esta en 0 no puede seguir*/
        if(Float.parseFloat(sMax) == 0 && iEv == 0)
        {
            /*Coloca el borde rojo*/                               
            jTMax.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de máximo está en 0.", "Agregar producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edción y regresa*/
            jTMax.grabFocus();           
            return;
        }        
        
        /*Si el campo de máximo es menor que mínimo no puede seguir*/
        if((Float.parseFloat(sMax) < Float.parseFloat(sMin)) && iEv == 0)
        {
            /*Coloca el borde rojo*/                               
            jTMax.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de máximo es menor que mímnimo.", "Agregar producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edción y regresa*/
            jTMin.grabFocus();           
            return;
        }                              
        
        /*Lee el checkbox de inventariable*/
        String sInven   = "0";
        if(jCInvent.isSelected())
            sInven          = "1";
        
        /*Lee el checkbox de si es para venta o no*/
        String sEsPVent = "0";
        if(jCEsParaVent.isSelected())
            sEsPVent        = "1";
        
        /*Lee el checkbox de compuesto*/
        String sCompue      = "0";
        if(jCComp.isSelected())
            sCompue         = "1";
        
        /*Lee lo que mide el producto*/
        String sMedMan      = jTMedMan.getText();
                
        /*Si la medida manual es cadena vacia ponerlo en 0 para que la base de datos la reciba*/
        if(sMedMan.compareTo("")==0)
            sMedMan         = "0";
        
        /*Lee lo que pesa el producto*/
        String sPesMan      = jTPesMan.getText();
        
        /*Si el peso es cadena vacia ponerlo en 0 para que la base de datos la reciba*/        
        if(sPesMan.compareTo("")==0)
            sPesMan         = "0";
        
        /*Lee la información*/
        String sInfor       = jTAInfor.getText();

        //Si la descripción esta como vacia entonces mensajea solamente
        if(jTADescrip.getText().trim().compareTo("")==0)
            JOptionPane.showMessageDialog(null, "La descripción del producto esta vacia y no se podrá cargar el producto en el punto de venta.", "Agregar producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));        
        
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Guardar Nuevo Producto", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                    
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ;
        
        //Obtén si el código del producto existe en la base de datos        
        iRes        = Star.iExistProd(con, jTProd.getText().replace(" ", "").trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el producto existe entonces
        if(iRes==1)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código del producto: " + jTProd.getText().replace(" ", "").trim() + " ya existe.", "Producto Existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edción del código del artículo y regresa*/
            jTProd.grabFocus();               
            return;                            
        }
        
        /*Obtiene se puede vender abajo del cost o no*/
        String sBajCost             = "0";
        if(jCBajCost.isSelected())
            sBajCost                = "1";        
        
        /*Obtiene si es servicio o no*/        
        String sServ                = "0";
        if(jCServ.isSelected())
            sServ                   = "1";
        
        /*Si las listas de precio son cadenas vacia que sean 0*/
        if(sPre1.compareTo("")==0)
            sPre1 = "0";
        if(sPre2.compareTo("")==0)
            sPre2 = "0";
        if(sPre3.compareTo("")==0)
            sPre3 = "0";
        if(sPre4.compareTo("")==0)
            sPre4 = "0";
        if(sPre5.compareTo("")==0)
            sPre5 = "0";
        if(sPre6.compareTo("")==0)
            sPre6 = "0";
        if(sPre7.compareTo("")==0)
            sPre7 = "0";
        if(sPre8.compareTo("")==0)
            sPre8 = "0";
        if(sPre9.compareTo("")==0)
            sPre9 = "0";
        if(sPre10.compareTo("")==0)
            sPre10 = "0";
              
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;
        
        /*Determina el método de costeo seleccionado*/
        String sMetCost = "ultcost";
        if(jRPEPS.isSelected())
            sMetCost    = "peps";
        else if(jRUEPS.isSelected())
            sMetCost    = "ueps";
        else if(jRProm.isSelected())
            sMetCost    = "prom";
        
        /*Determina si el producto va a manejar lote y pedimento*/
        String sLot     = "0";
        if(jCPed.isSelected())
            sLot        = "1";        
        
        /*Determina si el producto va a manejar número de serie*/
        String sNoSer   = "0";
        if(jCNoSer.isSelected())
            sNoSer      = "1";        
        
        /*Inserta el registro en la base de datos*/
        try
        {                       
            sQ = "INSERT INTO prods(       prod,                                                                   descrip,                                            lin,                                                                    marc,                                                               fab,                                                                colo,                                                              pes,                                                                     costre,                                                 unid,                                                                  codmed,                                                                     infor,                                  min,              max,             pesman,           med,                                   anaq,                                                                   lug,                                                                 invent,             descprov,                        estac,                                     prodop1,                                                prodop2,                                        exist,       compue,             provop1,                                               provop2,                                                esvta,               descripcort,                                   impue,                                           cost,                                                    solmaxmin,    bajcost,          prelist1,                                           prelist2,                                           prelist3,                                           prelist4,                                           prelist5,                                           prelist6,                                           prelist7,                                           prelist8,                                           prelist9,                                           prelist10,                                              sucu,                                    nocaj,                    codubi,                                                            codext,                                                               estaccrea,                                  servi,          mode,                                                                 tip,                                                               garan,                                                clasjera,                                             metcost,           utilvta1,         utilvta2,           utilvta3,           utilvta4,           utilvta5,           utilvta6,           utilvta7,           utilvta8,           utilvta9,           utilvta10,       lotped,        solser)" +
                            " VALUES('" +  jTProd.getText().replace(" ", "").replace("'", "''").trim() + "','" +   jTADescrip.getText().replace("'", "''") + "','"+    jComLin.getSelectedItem().toString().replace("'", "''") + "','" +       jComMarc.getSelectedItem().toString().replace("'", "''") + "','" +  jComFab.getSelectedItem().toString().replace("'", "''") + "','" +   jComCol.getSelectedItem().toString().replace("'", "''") + "','" +  jComPeso.getSelectedItem().toString().replace("'", "''") + "', " +       sCostL.replace("$", "").replace(",", "") + " ,'" +      jComUni.getSelectedItem().toString().replace("'", "''") + "','" +      jComMeds.getSelectedItem().toString().replace("'", "''") + "','" +          sInfor.replace("'", "''") + "','" +     sMin + "','" +    sMax + "', " +   sPesMan + "," +   sMedMan.replace("'", "''") + ",'" +    jComAna.getSelectedItem().toString().replace("'", "''") + "','" +       jComLug.getSelectedItem().toString().replace("'", "''") + "', " +    sInven + " ,'" +    jTAnot.getText() + "', '" +      Login.sUsrG.replace("'", "''") + "','" +   jTCodOp1.getText().replace("'", "''") + "','" +         jTCodOp2.getText().replace("'", "''") + "',     0, " +       sCompue + ",  '" +  jTCodProv.getText().replace("'", "''") + "', '" +      jTCodProv1.getText().replace("'", "''") + "', " +       sEsPVent + ", '" +   jTNom.getText().replace("'", "''") + "', '" +  jComImp.getSelectedItem().toString() + "', " +  sUltCost.replace("$", "").replace(",", "") + ", " +      iEv + ",  " + sBajCost + ", " + sPre1.replace("$", "").replace(",", "") + ", " +    sPre2.replace("$", "").replace(",", "") + ",    " + sPre3.replace("$", "").replace(",", "") + ", " +    sPre4.replace("$", "").replace(",", "") + ", " +    sPre5.replace("$", "").replace(",", "") + ", " +    sPre6.replace("$", "").replace(",", "") + ", " +    sPre7.replace("$", "").replace(",", "") + ", " +    sPre8.replace("$", "").replace(",", "") + ", " +    sPre9.replace("$", "").replace(",", "") + ", " +    sPre10.replace("$", "").replace(",", "") + ", '" +      Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj + "','" +    jComUAd.getSelectedItem().toString().replace("'", "''") + "','" +   jComExt.getSelectedItem().toString().replace("'", "''") + "', '" +    Login.sUsrG.replace("'", "''") + "', " +    sServ + ", '" + jComMod.getSelectedItem().toString().replace("'", "''") + "',  '" +   jComTip.getSelectedItem().toString().replace("'", "''") + "', '" + jTGara.getText().replace("'", "''").trim() + "', '" + jTJera.getText().replace("'", "''").trim() + "', '" + sMetCost + "', " + sUtil1V + ", " +  sUtil2V + ", " +    sUtil3V + ", " +    sUtil4V + ", " +    sUtil5V + ", " +    sUtil6V + ", " +    sUtil7V + ", " +    sUtil8V + ", " +    sUtil9V + ", " +    sUtil10V + ", " + sLot + ", " + sNoSer + ")";                                                
            st = con.createStatement();
            st.executeUpdate(sQ);            
         }
         catch(SQLException expnSQL)
         {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
         }

        /*Inserta en log de productos*/
        try 
        {            
            sQ = "INSERT INTO logprods( cod,                                                                    descrip,                                            accio,             estac,                                           sucu,                                               nocaj,                                      falt) " + 
                          "VALUES('" +  jTProd.getText().replace(" ", "").replace("'", "''").trim() + "','" +   jTADescrip.getText().replace("'", "''") + "',       'AGREGAR', '" +    Login.sUsrG.replace("'", "''") + "','" +     Star.sSucu.replace("'", "''") + "','" +       Star.sNoCaj.replace("'", "''") + "',  now())";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }
        
        /*Borra toda la tabla de modelos*/
        try
        {                      
            sQ = "DELETE FROM modelprod";
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL)
         {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
         }
        
        //Si tiene modelos entonces
        if(sMods!=null)
        {
            /*Recorre todo el arreglo de modelos*/        
            for(String sMod: sMods)
            {                    
                /*Inserta en la base de datos la relacion de los Modelos*/
                try
                {                      
                    sQ = "INSERT INTO modelprod(prod,                                                                  mode,           estac,                   sucu,                      nocaj) "
                                 + "VALUES('" + jTProd.getText().replace("'", "''").replace(" ", "").trim() + "', '" + sMod + "', '" + Login.sUsrG + "', '" +   Star.sSucu + "', '" + Star.sNoCaj + "')";
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL)
                 {                     
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
                    return;
                 }

            }/*Fin de for(String sMod: sMods)*/
            
        }//Fin de if(sMods!=null)                    
        
        /*Borra toda la tabla de compatibilidades*/
        try
        {                      
            sQ = "DELETE FROM compa";
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL)
         {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        
        //Si no tiene compatibilidades
        if(sCompa!=null)
        {
            /*Recorre todo el arreglo de compatibilidades*/        
            for(String sComp: sCompa)
            {                    
                /*Inserta en la base de datos la relacion de las compatibilidades*/
                try
                {                      
                    sQ = "INSERT INTO compa(prod,                                                                       compa,             estac,                   sucu,                      nocaj) "
                                 + "VALUES('" + jTProd.getText().replace("'", "''").replace(" ", "").trim() + "', '" +  sComp + "', '" +   Login.sUsrG + "', '" +   Star.sSucu + "', '" + Star.sNoCaj + "')";
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
        }
        
        /*Borra toda la tabla de asociaciones de marcas*/
        try
        {                      
            sQ = "DELETE FROM marcprod";
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL)
         {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }

        //Si si tiene marcas entonces
        if(sMarcs!=null)
        {
            /*Recorre todo el arreglo de marcas*/        
            for(String sMarc: sMarcs)
            {                    
                /*Inserta en la base de datos la relacion de las marcas*/
                try
                {                      
                    sQ = "INSERT INTO marcprod(prod,                                                                   marc,                estac,                   sucu,                      nocaj) "
                                 + "VALUES('" + jTProd.getText().replace("'", "''").replace(" ", "").trim() + "', '" + sMarc + "', '" +     Login.sUsrG + "', '" +   Star.sSucu + "', '" + Star.sNoCaj + "')";
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
        }
        
        /*Borra toda la tabla de números de parte*/
        try
        {                      
            sQ = "DELETE FROM prodpart";
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL)
         {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        
        //Si no tiene compatibilidades
        if(sCompaMarcMod!=null)
        {
            /*Recorre todo el arreglo de compatibilidades*/        
            for(String sComp: sCompaMarcMod)
            {      
                //Tokeniza para quitar la raíz común
                java.util.StringTokenizer stk = new java.util.StringTokenizer(sComp, "|");

                String sMarca  = stk.nextToken();
                String sModelo = stk.nextToken();
                String sRut    = sComp + "|" + jComTip.getSelectedItem().toString();
                /*Inserta en la base de datos la relacion de las compatibilidades*/
                try
                {                      
                    sQ = "INSERT INTO terProdCompa(    prod,                                                                   marc,             model,             rut,             estac,                   sucu,                      nocaj) "
                                                 + "VALUES('" + jTProd.getText().replace("'", "''").replace(" ", "").trim() + "', '" +  sMarca + "', '" + sModelo + "', '" + sRut + "','" +   Login.sUsrG + "', '" +   Star.sSucu + "', '" + Star.sNoCaj + "')";
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
            
        }//if(sCompaMarcMod!=null)

        //Si tiene números de parte entonces
        if(sParts!=null)
        {
            /*Recorre todo el arreglo de números de parte*/        
            for(String sPart: sParts)
            {                    
                /*Inserta en la base de datos la relacion de los números de series*/
                try
                {                      
                    sQ = "INSERT INTO prodpart( prod,                                                                  part,                estac,                   sucu,                 nocaj) "
                                 + "VALUES('" + jTProd.getText().replace("'", "''").replace(" ", "").trim() + "', '" + sPart + "', '" +     Login.sUsrG + "', '" +   Star.sSucu + "', '" + Star.sNoCaj + "')";
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
        }

        /*Borra toda la tabla de series*/
        try
        {                      
            sQ = "DELETE FROM serieprod";
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

        //Declara variables locales
        String sFCrea   = "";
        String sFMod    = "";
        
        /*Obtiene la fecha de modificación y creación del producto*/        
        try
        {
            sQ = "SELECT falt, fmod FROM prods WHERE prod = '" + jTProd.getText().replace(" ", "").trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {            
                sFCrea      = rs.getString("falt");                                
                sFMod       = rs.getString("fmod");                   
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }            
        
        /*Agrea en los labels la fecha de creación y de modificación*/
        jLFCreac.setText                    (sFCrea);
        jLFMod.setText                      (sFMod);
        
        /*Agrega el usuario de última modificación*/
        jTUltEstacMod.setText               (Login.sUsrG);
        
        /*Agrega en el campo de usuario el usuario actual*/
        jTEstac.setText                     (Login.sUsrG);
        
        /*Agrega en el campo de exist en 0*/
        jTExist.setText                     ("0");
        
        /*Si la med manual es cadena vacia entonces ponlo en 0*/
        if(jTMedMan.getText().compareTo("")==0)
            jTMedMan.setText                ("0");
        
        /*Si el pes manual es cadena vacia entonces ponlo en 0*/
        if(jTPesMan.getText().compareTo("")==0)
            jTPesMan.setText               ("0");
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Aumenta en uno el contador de filas de la tabla*/
        ++iContFi;

        /*Habilita el botón de guardar cambios*/
        jBGuar.setEnabled(true);
        
        /*Resetea el contador de filas*/
        iContFi                 = 1;
        
        /*Obtiene el producto antes del tread para que no de nulo en la impresión del mensajea*/
        String sProd    = jTProd.getText().replace(" ", "").trim();
        
        /*Proceso para cargar nuevamente todos los productos en un thread para aligerar*/
        (new Thread()
        {
            @Override
            public void run()
            {                               
                /*Agrega todos los datos de la base de datos a la tabla de productos*/
                vCargProdsTab();                                
            }
            
        }).start();
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Producto: " + sProd + " agregado con éxito", "Éxito al agregar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
        /*Pregunta al usuario si esta seguro de abandonar la alta del artículo*/
        Object[] options = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), options, options[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Comprueba si el código del producto ya existe en la tabla de productos
        iRes        = Star.iExistProd(con, jTProd.getText().trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el producto existe entonces coloca la bandera        
        boolean bSiExist   = false;        
        if(iRes==1)
            bSiExist       = true;
        
	/*Si el producto no existe entonces*/  
        boolean bSiAsocKit = false;
        if(!bSiExist)
        {
            /*Comprueba si el producto esta asociado a un kit*/
            try
            {                  
                sQ = "SELECT codkit FROM kits WHERE codkit = '" + jTProd.getText().trim() + "'";                    
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces coloca la bandera*/
                if(rs.next())
                    bSiAsocKit   = true;
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
                return;                
            }                        
        }
        
        /*Si el producto no existe y tiene asociado un kit entonces*/
        if(!bSiExist && bSiAsocKit)
        {
            /*Borra el kit y todos los componentes que tiene*/
            try 
            {                
                sQ = "DELETE FROM kits WHERE codkit = '" + jTProd.getText().trim() + "'";                                   
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
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Llama al recolector de basura*/
        System.gc();
        
        /*Inicializa la forma nuevamente*/
        Star.gProds = null;
        
        /*Cierra el formulario*/
        this.dispose();
        obj = null;

    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de nom del cliente*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b  = new Busc(this, jTProd.getText(), 2, jTProd, null, jTAlmaG, "", null);
            b.setVisible(true);                        
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else                    
            vKeyPreEsc(evt);        

    }//GEN-LAST:event_jTProdKeyPressed
        
        
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona una tecla en el botón de nuevo*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        /*Presiona el botón de salir*/
        jBSal.doClick();

    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se gana el foco del teclado en el campo del código del artículo*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());

    }//GEN-LAST:event_jTProdFocusGained

        
        
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en el combobox de meds*/
    private void jComMedsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComMedsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComMedsKeyPressed

    
    /*Cuando se presiona una tecla en el combobox de color*/
    private void jComColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComColKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComColKeyPressed

    
    /*Cuando se presiona una tecla en el combobox de unidad*/
    private void jComUniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComUniKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComUniKeyPressed

    
    /*Cuando se presiona una tecla en el campo de área de descripción*/
    private void jTADescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTADescripKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTADescripKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de área de descripción*/
    private void jTADescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTADescripFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTADescrip.setSelectionStart(0);        
        jTADescrip.setSelectionEnd(jTADescrip.getText().length());        

    }//GEN-LAST:event_jTADescripFocusGained

    
    /*Cuando se presiona una tecla en el combobox de línea*/
    private void jComLinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComLinKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComLinKeyPressed

    
    /*Cuando se presiona una tecla en el combobox de marcs*/
    private void jComMarcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComMarcKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComMarcKeyPressed

    
    /*Cuando se presiona una tecla en el combobox de fabricante*/
    private void jComFabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComFabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComFabKeyPressed

    
    /*Cuando se presiona una tecla en el campo de área de información*/
    private void jTAInforKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAInforKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTAInforKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de área de información*/
    private void jTAInforFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAInforFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTAInfor.setSelectionStart(0);        
        jTAInfor.setSelectionEnd(jTAInfor.getText().length());        

    }//GEN-LAST:event_jTAInforFocusGained

    
    /*Cuando se presiona una tecla en el combobox de pes*/
    private void jComPesoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComPesoKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComPesoKeyPressed

    
    /*Cuando se presiona una tecla en el campo de pes*/
    private void jTPesoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPesoKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTPesoKeyPressed

    
    /*Cuando se presiona una tecla en el campo mínimo*/
    private void jTMinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMinKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTMinKeyPressed

    
    /*Cuando se presiona una tecla en el campo de máximo*/
    private void jTMaxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMaxKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTMaxKeyPressed
    
    
    /*Cuando se gana el foco del teclado en el campo de pes*/
    private void jTPesoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPesoFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTPeso.setSelectionStart(0);jTPeso.setSelectionEnd(jTPeso.getText().length());        

    }//GEN-LAST:event_jTPesoFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de mínimo*/
    private void jTMinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMinFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTMin.setSelectionStart(0);jTMin.setSelectionEnd(jTMin.getText().length());

    }//GEN-LAST:event_jTMinFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de máximo*/
    private void jTMaxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMaxFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTMax.setSelectionStart(0);jTMax.setSelectionEnd(jTMax.getText().length());        

    }//GEN-LAST:event_jTMaxFocusGained

    
    /*Cuando se presiona una tecla en la tabla de prods*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se levanta una tecla en el botón de guardar cambios*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se levanta una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se levanta una tecla en el botón de limpiar*/
    private void jBLimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLimKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBLimKeyPressed

    
    /*Cuando se levanta una tecla en el campo de línea*/
    private void jTLinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLinKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTLinKeyPressed

    
    /*Cuando se levanta una tecla en el campo de marca*/
    private void jTMarcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMarcKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTMarcKeyPressed

    
    /*Cuando se levanta una tecla en el campo de fabricante*/
    private void jTFabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTFabKeyPressed

    
    /*Cuando se levanta una tecla en el campo de meds*/
    private void jTMedsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMedsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTMedsKeyPressed

    
    /*Cuando se levanta una tecla en el campo de color*/
    private void jTColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTColKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de línea*/
    private void jTLinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLinFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTLin.setSelectionStart(0);jTLin.setSelectionEnd(jTLin.getText().length());        

    }//GEN-LAST:event_jTLinFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de marca*/
    private void jTMarcFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMarcFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTMarc.setSelectionStart(0);jTMarc.setSelectionEnd(jTMarc.getText().length());        

    }//GEN-LAST:event_jTMarcFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de fabricante*/
    private void jTFabFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFabFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTFab.setSelectionStart(0);jTFab.setSelectionEnd(jTFab.getText().length());        

    }//GEN-LAST:event_jTFabFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de meds*/
    private void jTMedsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMedsFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTMeds.setSelectionStart(0);jTMeds.setSelectionEnd(jTMeds.getText().length());        

    }//GEN-LAST:event_jTMedsFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de color*/
    private void jTColFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCol.setSelectionStart(0);jTCol.setSelectionEnd(jTCol.getText().length());

    }//GEN-LAST:event_jTColFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de descripción*/
    private void jTADescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTADescripFocusLost

        /*Coloca el cursor al principio del control*/
        jTADescrip.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTADescrip.getText().compareTo("")!=0)
            jTADescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTADescrip.getText().length()> 255)
            jTADescrip.setText(jTADescrip.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTADescripFocusLost

       
    
    
    /*Cuando se pierde el foco del teclado en el campo de información*/
    private void jTAInforFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAInforFocusLost

        /*Coloca el cursor al principio del control*/
        jTAInfor.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTAInfor.getText().length()> 255)
            jTAInfor.setText(jTAInfor.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTAInforFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de mínimo*/
    private void jTMinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMinFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTMin.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTMin.getText().compareTo("")!=0)
            jTMin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTMin.getText().length()> 20)
            jTMin.setText(jTMin.getText().substring(0, 20));
        
        /*Si los caracteres introducidos no se puede convertir a double colocar cadena vacia*/
        try  
        {  
            double d = Double.parseDouble(jTMin.getText());  
        }  
        catch(NumberFormatException expnNumForm)  
        {  
            jTMin.setText("");
        }
        
    }//GEN-LAST:event_jTMinFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de máximo*/
    private void jTMaxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMaxFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTMax.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTMax.getText().compareTo("")!=0)
            jTMax.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTMax.getText().length()> 20)
            jTMax.setText(jTMax.getText().substring(0, 20));
        
        /*Si los caracteres introducidos no se puede convertir a double colocar cadena vacia*/
        try  
        {  
            double d = Double.parseDouble(jTMax.getText());  
        }  
        catch(NumberFormatException expnNumForm)  
        {  
            jTMax.setText("");
        }
        
    }//GEN-LAST:event_jTMaxFocusLost

    
    
        
    /*Cuando se typea una tecla en el campo de min*/
    private void jTMinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMinKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTMinKeyTyped

    
    /*Cuando se typea una tecla en el campo de max*/
    private void jTMaxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMaxKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTMaxKeyTyped

    
    /*Cuando se presiona una tecla en el campo de pes*/
    private void jTPesManKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPesManKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPesManKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de pes manual*/
    private void jTPesManFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPesManFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPesMan.setSelectionStart(0);jTPesMan.setSelectionEnd(jTPesMan.getText().length());        
        
    }//GEN-LAST:event_jTPesManFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de pes maual*/
    private void jTPesManFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPesManFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPesMan.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTPesMan.getText().length()> 30)
            jTPesMan.setText(jTPesMan.getText().substring(0, 30));
        
        /*Si los caracteres introducidos no se pueden convertir a double colocar cadena vacia*/
        try  
        {  
            double d = Double.parseDouble(jTPesMan.getText());  
        }  
        catch(NumberFormatException expnNumForm)  
        {  
            jTPesMan.setText("");
        }
        
    }//GEN-LAST:event_jTPesManFocusLost

    
    /*Cuando se typea una tecla en el campo de pes*/
    private void jTPesManKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPesManKeyTyped
        
        /*Obtiene el carácter escrito*/
        char caracter = evt.getKeyChar();
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((caracter < '0') || (caracter > '9')) && (caracter != '\b') && (caracter != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPesManKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de med manual*/
    private void jTMedManFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMedManFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMedMan.setSelectionStart(0);jTMedMan.setSelectionEnd(jTMedMan.getText().length());        
        
    }//GEN-LAST:event_jTMedManFocusGained

    
    /*Cuando se presiona una tecla en el campo de med manual*/
    private void jTMedManKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMedManKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTMedManKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de med*/
    private void jTMedManFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMedManFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTMedMan.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTMedMan.getText().length()> 20)
            jTMedMan.setText(jTMedMan.getText().substring(0, 20));
        
        /*Si los caracteres introducidos no se pueden convertir a double colocar cadena vacia*/
        try  
        {  
            double d = Double.parseDouble(jTMedMan.getText());  
        }  
        catch(NumberFormatException expnNumForm)  
        {  
            jTMedMan.setText("");
        }
        
    }//GEN-LAST:event_jTMedManFocusLost

    
    /*Cuando se typea una tecla en el campo de med manual*/
    private void jTMedManKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMedManKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTMedManKeyTyped
    
    
    /*Cuando la ventana se gana el foco del teclado*/
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        
        /*Si la ventana no se tiene que mostrar no mostrarla*/
        if(!bMostVe)
        {
            /*Llama al recolector de basura*/
            System.gc();
        
            this.dispose();
            obj             = null;
            bMostVe         = true;
        }
        
    }//GEN-LAST:event_formWindowGainedFocus
        
    
    /*Cuando ocurre algo en el combobox de líneas*/
    private void jComLinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComLinActionPerformed

        /*Si no hay selección regresa*/
        if(jComLin.getSelectedItem()==null)
            return;        
                    
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;        
        
        /*Obtén la descripción de la línea de la base de datos*/        
        try
        {
            sQ  = "SELECT * FROM lins WHERE cod = '" + jComLin.getSelectedItem().toString() + "'";            
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el campo y pon el cursor al principio del control*/
                jTLin.setText(rs.getString("descrip"));                                            
                jTLin.setCaretPosition(0);
            }
            else
                jTLin.setText("");                                            
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

        /*Coloca el cursor hasta el principio en el campo de la descripción*/
        jTLin.setCaretPosition(0);
        
    }//GEN-LAST:event_jComLinActionPerformed

    
    /*Cuando sucede algo en el combobox de marcs*/
    private void jComMarcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComMarcActionPerformed
        
        /*Si no hay selección regresa*/
        if(jComMarc.getSelectedItem()==null)
            return;        
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;        
        
        /*Obtén la descripción de la marca de la base de datos*/        
        try
        {
            sQ = "SELECT * FROM marcs WHERE cod = '" + jComMarc.getSelectedItem().toString() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el control y coloca el cursor al principio*/
                jTMarc.setText(rs.getString("descrip"));                              
                jTMarc.setCaretPosition(0);
            }
            else
                jTMarc.setText("");                                            
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

        /*Coloca el cursor hasta el principio en el campo de la descripción*/
        jTMarc.setCaretPosition(0);
        
    }//GEN-LAST:event_jComMarcActionPerformed

    
    /*Cuando sucede algo en el combobox de fabricante*/
    private void jComFabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComFabActionPerformed
        
        /*Si no hay selección entonces regresa*/
        if(jComFab.getSelectedItem()==null)                    
            return;        
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                        
        
        /*Obtén la descripción del fabricante de la base de datos*/        
        try
        {
            sQ = "SELECT * FROM fabs WHERE cod = '" + jComFab.getSelectedItem().toString() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el control y coloca el caret al principio*/
                jTFab.setText(rs.getString("descrip"));                                               
                jTFab.setCaretPosition(0);
            }
            else
                jTFab.setText("");                                            
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

        /*Coloca el cursor hasta el principio en el campo de la descripción*/
        jTFab.setCaretPosition(0);
        
    }//GEN-LAST:event_jComFabActionPerformed

    
    /*Cuando pasa algo en el combobox de meds*/
    private void jComMedsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComMedsActionPerformed
        
        /*Si no hay selección entonces regresa*/
        if(jComMeds.getSelectedItem()==null)
            return;        
                    
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtén la descripción de la med de la base de datos*/        
        try
        {
            sQ = "SELECT * FROM meds WHERE cod = '" + jComMeds.getSelectedItem().toString() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Colcoa el valor en el control y coloca el caret el principio*/
                jTMeds.setText(rs.getString("descrip"));                                               
                jTMeds.setCaretPosition(0);
            }
            else
                jTMeds.setText("");                                            
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
            
        /*Coloca el cursor hasta el principio en el campo de la descripción*/
        jTMeds.setCaretPosition(0);
        
    }//GEN-LAST:event_jComMedsActionPerformed

    
    /*Cuando pasa algo en el combobox de color*/
    private void jComColActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComColActionPerformed
        
        /*Si no hay selección entonces regresa*/
        if(jComCol.getSelectedItem()==null)        
            return;
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                                                        
        
        /*Obtén la descripción del color de la base de datos*/        
        try
        {
            sQ = "SELECT * FROM colos WHERE cod = '" + jComCol.getSelectedItem().toString() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Colcoa el valor en el control y coloca el caret al principio*/
                jTCol.setText(rs.getString("descrip"));                                                
                jTCol.setCaretPosition(0);
            }
            else
                jTCol.setText("");                                            
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

        /*Coloca el cursor hasta el principio en el campo de la descripción*/
        jTCol.setCaretPosition(0);
        
    }//GEN-LAST:event_jComColActionPerformed

    
    /*Cuando sucede algo en el combobox de pes*/
    private void jComPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComPesoActionPerformed
                                                           
        /*Si no hay selección entonces regresa*/
        if(jComPeso.getSelectedItem()==null)        
            return;
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtén la descripción del peso de la base de datos*/        
        try
        {
            sQ  = "SELECT * FROM pes WHERE cod = '" + jComPeso.getSelectedItem().toString() + "'";            
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el control y coloca el caret al principio*/
                jTPeso.setText(rs.getString("descrip"));                                               
                jTPeso.setCaretPosition(0);
            }
            else
                jTPeso.setText("");                                            
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

        /*Coloca el cursor hasta el principio en el campo de la descripción*/
        jTPeso.setCaretPosition(0);
        
    }//GEN-LAST:event_jComPesoActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de unidad*/
    private void jTUnidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUnidFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUnid.setSelectionStart(0);jTUnid.setSelectionEnd(jTUnid.getText().length());        
        
    }//GEN-LAST:event_jTUnidFocusGained

    
    /*Cuando se presiona una tecla en el campo de unidad*/
    private void jTUnidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUnidKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUnidKeyPressed

    
    /*Cuando sucede algo en el combobox de unidad*/
    private void jComUniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComUniActionPerformed
        
        /*Si no hay selección entonces regresa*/
        if(jComUni.getSelectedItem()==null)        
            return;                
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                                                       
        
        /*Obtén la descripción de la unidad de la base de datos*/        
        try
        {
            sQ = "SELECT * FROM unids WHERE cod = '" + jComUni.getSelectedItem().toString() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el control y colcoa el caret al principio del control*/
                jTUnid.setText(rs.getString("descrip"));                                               
                jTUnid.setCaretPosition(0);
            }
            else
                jTUnid.setText("");                                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);
                
    }//GEN-LAST:event_jComUniActionPerformed

    
    /*Cuando se presioan el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
                
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado producto(s) de la tabla para borrar.", "Borrar Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                        
            return;
        }
             
        /*Pregunta si esta seguro de borrar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar el(los) producto(s)?", "Borrar Producto", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;

        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Bandera para saber si algo se borro*/
        boolean bSiDel = false;
        
        //Declara variables de la base de datos
        Statement   st;               
        ResultSet   rs;
        String      sQ;
    
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Comprueba si el producto esta asociado a alguna venta*/
            boolean bSi = false;
            try
            {                  
                sQ = "SELECT prod FROM partvta WHERE prod = '" + jTab.getValueAt(iSel[x], 1).toString().trim() + "'";
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
            
            /*Si ya esta ete producto asociado a una venta entonces*/
            if(bSi)
            {
                /*Mensajea al usuario y continua*/
                //CAMBIO ALAN
                //JOptionPane.showMessageDialog(null, " Ya existe este producto: " + jTab.getValueAt(x, 1).toString() + " en alguna venta", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                JOptionPane.showMessageDialog(null, " Ya existe este producto: " + jTab.getValueAt(iSel[x], 1).toString() + " en alguna venta", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }
            
            /*Comprueba si el producto esta asociado a alguna cotización*/
            bSi     = false;
            try
            {                  
                //CAMBIO ALAN iSel[x]
                //sQ = "SELECT prod FROM partcot WHERE prod = '" + jTab.getValueAt(x, 1).toString().trim() + "'";
                sQ = "SELECT prod FROM partcot WHERE prod = '" + jTab.getValueAt(iSel[x], 1).toString().trim() + "'";
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
            
            /*Si ya esta ete producto asociado a una cotización entonces*/
            if(bSi)
            {
                /*Mensajea al usuario y continua*/
                JOptionPane.showMessageDialog(null, " Ya existe este producto: " + jTab.getValueAt(x, 1).toString() + " en alguna cotización", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }
            
            /*Comprueba si el producto esta asociado a un kit*/
            boolean bSiAsocKit = false;
            try
            {                  
                //CAMBIO ALAN iSel[x]
                //sQ = "SELECT codkit FROM kits WHERE codkit = '" + jTab.getValueAt(x, 1).toString().trim() + "'";
                sQ = "SELECT codkit FROM kits WHERE codkit = '" + jTab.getValueAt(iSel[x], 1).toString().trim() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces coloca la bandera*/
                if(rs.next())
                    bSiAsocKit = true;                                
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
                return;                
            }

            /*Si el producto tiene asociado un kit entonces avisa al usuario y pregunta si esta seguro de borrarlo*/
            if(bSiAsocKit)
            {
                /*Avisa al usuario y pregunta si esta seguro o no*/
                Object[] op1 = {"Si","No"};
                iRes    = JOptionPane.showOptionDialog(this, "El producto: " + jTab.getValueAt(x, 1).toString() + " esta asociado como kit. ¿Seguro que quieres borrarlo?", "Borrar Producto", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op1, op1[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                    continue;                                 
            }
            
            //Inicia la transacción
            if(Star.iIniTransCon(con)==-1)
                return;

            /*Borra el producto de la base de datos*/
            try 
            {                            
                sQ = "DELETE FROM prods WHERE prod = '" + jTab.getValueAt(iSel[x], 1).toString().trim() + "'";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
                return;               
             }        

            /*Inserta en log de productos*/
            try 
            {            
                sQ = "INSERT INTO logprods( cod,                                                                descrip,                                                    accio,             estac,                                       sucu,                                     nocaj,                                falt) " + 
                              "VALUES('" +  jTab.getValueAt(iSel[x], 1).toString().replace("'", "''") + "','" +       jTab.getValueAt(iSel[x], 2).toString().replace("'", "''") + "',  'BORRAR', '" +     Login.sUsrG.replace("'", "''") + "','" +      Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',  now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
                return;                
            }

            /*Borra toda la tabla de modelos*/
            try
            {                      
                sQ = "DELETE FROM terProdCompa WHERE prod='" + jTab.getValueAt(iSel[x], 1).toString().replace("'", "''") + "'";
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL)
             {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
                return;                 
            }
            
            /*Borra el kit y sus componentes en caso de que existan de la base de datos*/
            try 
            {            
                sQ = "DELETE FROM kits WHERE codkit = '" + jTab.getValueAt(iSel[x], 1).toString().trim() + "'";                                          
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
                return;                                
            }     
                        
            //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
            String sCarp    = Star.sGetRutCarp(con);                    

            //Si hubo error entonces regresa
            if(sCarp==null)
                return;
            
            //Limpia el producto
            jTProd.setText("");

            /*Limpia todos los campos*/
            vLim();

            //Termina la transacción
            if(Star.iTermTransCon(con)==-1)
                return;
            
            String sProd                    = jTab.getValueAt(iSel[x], 1).toString().trim();
            sCarp                           += "\\Imagenes";
            sCarp                           += "\\Productos";
            sCarp                           += "\\" + Login.sCodEmpBD;
            sCarp                           += "\\" + sProd;
            
            if(new File(sCarp).exists())
            {
                /*Si tiene ficheros entonces*/
                if( new File(sCarp).list().length > 0)
                {
                    iRes    = JOptionPane.showOptionDialog(this, "Este producto tiene imagen ¿Seguro quiere eliminarla?", "Borrar Producto", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                    if(iRes==JOptionPane.YES_OPTION)
                    {
                        /*Borra todos los archivos de la carpeta*/
                        try
                        {
                            org.apache.commons.io.FileUtils.cleanDirectory(new File(sCarp)); 
                            JOptionPane.showMessageDialog(null, "Imagen eliminada", "", JOptionPane.ERROR_MESSAGE, null);      
                        }
                        catch(IOException expnIO)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                    
                            return;
                        }                       

                        /*Que no sea visible el control de la imágen*/
                        jLImg.setVisible(false);
                    }
                }
            }

            /*Borralo de la tabla*/            
            tm.removeRow(iSel[x]);
            
            /*Resta en uno el contador de filas el contador de filas en uno*/
            --iContFi;
        
            /*Coloca la bandera para saber si se borro algo*/
            bSiDel  = true;
                    
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                                        
                    
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensajea de éxito*/
        if(bSiDel)
            JOptionPane.showMessageDialog(null, "Producto(s) borrado(s) con éxito.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Función para limpiar todos los campos*/
    private synchronized void vLim()
    {
        /*Reinicia las asociaciones de los Modelos y marcas*/
        sMods                       = null;
        sMarcs                      = null;
        sCompa                      = null;
        sParts                      = null;
        sSers                       = null;
        sCompaMarcMod               = null;
        
        /*Limpia todos los campos*/
        jTCant.setText              ("");
        jTAlmaG.setText             ("");
        jTADescrip.setText          ("");         
        jTMedMan.setText            ("");
        jTPesMan.setText            ("");        
        jTExist.setText             ("");
        jTAInfor.setText            ("");
        jTMin.setText               ("1");
        jTEstac.setText             ("");
        jTCodOp1.setText            ("");
        jTCodOp2.setText            ("");
        jTUltCom.setText            ("");
        jTUltEstacMod.setText       ("");
        jTCread.setText             ("");
        jTProv.setText              ("");
        jTCodProv.setText           ("");
        jTCodProv1.setText          ("");
        jTFac.setText               ("");
        jTEstac.setText             ("");        
        jTMax.setText               ("2");
        jTNom.setText               ("");        
        jTProvMasCom.setText        ("");                
        jTAnot.setText              ("");
        jLFCreac.setText            ("-");
        jLFMod.setText              ("-");     
        jTAnot.setText              ("");
        
        /*Que el label que muestra la imágen no sea visible*/
        jLImg.setVisible            (false);
        
        /*Pon los comboboxes su índice vacio*/
        jComLin.setSelectedItem         ("");
        jComTip.setSelectedItem         ("");
        jComMod.setSelectedItem         ("");
        jComMarc.setSelectedItem        ("");
        jComFab.setSelectedItem         ("");
        jComMeds.setSelectedItem        ("");
        jComPeso.setSelectedItem        ("");
        jComCol.setSelectedItem         ("");
        jComUni.setSelectedItem         ("");
        jComAna.setSelectedItem         ("");
        jComUAd.setSelectedItem         ("");
        jComLug.setSelectedItem         ("");
        jComImp.setSelectedItem        ("");        
        jComExt.setSelectedItem         ("");
        
        /*Pon los checkboxes en su valor por default*/
        jCInvent.setSelected            (true);
        jCPed.setSelected               (false);
        jCComp.setSelected              (false);
        jCEsParaVent.setSelected        (true);
        jCNoSolMaxMin.setSelected       (false);
        jCBajCost.setSelected           (false);
        jCNoSer.setSelected             (false);
        
        /*Pon los botones en el estado original*/
        jBComps.setEnabled              (false);
        
        /*Coloca el botón de guardar cambios a deshabilitado ya que no se pueden guardar cambios para un artículo nuevo*/
        jBGuar.setEnabled               (false);
        
        /*Inicaliza los campos de las listas de precios y utilidades*/
        sPre1       = "0.0";
        sPre2       = "0.0";
        sPre3       = "0.0";
        sPre4       = "0.0";
        sPre5       = "0.0";
        sPre6       = "0.0";
        sPre7       = "0.0";
        sPre8       = "0.0";
        sPre9       = "0.0";
        sPre10      = "0.0";
        sUtil1      = "0.0";
        sUtil2      = "0.0";
        sUtil3      = "0.0";
        sUtil4      = "0.0";
        sUtil5      = "0.0";
        sUtil6      = "0.0";
        sUtil7      = "0.0";
        sUtil8      = "0.0";
        sUtil9      = "0.0";
        sUtil10     = "0.0";
        
        /*Inicializa los costos*/
        sUeps       = "$0.00";
        sPeps       = "$0.00";
        sUltCost    = "$0.00";
        
        /*Obtiene el método de costeo de la empresa local y seleccionalo en los radio*/
        vSelMet();
        
    }/*Fin de private void vLim()*/
    
    
    /*Cuando se presiona el botón de limpiar*/
    private void jBLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimActionPerformed
        
        /*Preguntar al usuario si esta seguro de querer limpias todos los campos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres limpiar todos los campos?", "Limpiar Campos", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        //Limpia el producto
        jTProd.setText("");
        
        /*Limpia todos los campos*/
        vLim();
        
        /*Pon el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();
                
    }//GEN-LAST:event_jBLimActionPerformed
        
    
    /*Cuando se presiona una tecla en el campo de exist*/
    private void jTExistKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExistKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTExistKeyPressed
    
    
    /*Cuando se hace clic en la tabla de productos*/
    private void jTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabMouseClicked
        
        /*Carga todos los datos de la fila de la tabla de productos en los campos*/
        vLoadDat(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());
                
    }//GEN-LAST:event_jTabMouseClicked

    
    /*Cuando se presiona el botón de guardar cambios*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
                    
        /*Si el código del producto original es cadena vacia entonces*/
        if(sProdOri.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un producto.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));             
            return;
        }            
                
        /*Si el código del producto tiene carácteres no permitidos entonces*/
        if(sProdOri.contains("/") || sProdOri.contains("\\") || sProdOri.contains("\"") || sProdOri.contains("?") || sProdOri.contains(":") || sProdOri.contains("|") || sProdOri.contains("*"))
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El código del producto no puede tener los siguientes carácteres especiales: /\\:?*<>|\"", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));             
            return;
        }            
        
        //Si hay asociacion marca y modelo nesecita tipo
        if(sCompaMarcMod!=null && jComTip.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComTip.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Si realiza relaciones con marca y producto es necesario tener un tipo asignado", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jComTip.grabFocus();           
            return;
        }
        
        /*Si no ha seleccionado por lo menos una unidad entonces*/
        if(jComUni.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComUni.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una unidad para el producto.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jComUni.grabFocus();          
            return;
        }
        
        /*Si no tiene seleccionado kit, servicio e inventariable entonces*/
        if(!jCServ.isSelected() && !jCInvent.isSelected() && !jCComp.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos kit, servicio o inventariable.", "Guardar cambios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el check de inventariable y regresa*/
            jCInvent.grabFocus();
            return;
        }
        
        /*Lee el mínimo*/
        String sMin                    = jTMin.getText();

        /*Lee el máximo*/
        String sMax                    = jTMax.getText();
        
        /*Lee si se tiene que evaluar máximo y mínimo*/
        int iEv                 = 0;
        if(jCNoSolMaxMin.isSelected())
        {
            /*Coloca el máximo y el mínimo en 0* en caso de que sean cadenas vacias*/            
            if(sMin.compareTo("")==0)
                sMin = "0";
            if(sMax.compareTo("")==0)
                sMax = "0";
            
            /*Pon la variable para saber que no pedira máximos y mínimos*/
            iEv = 1;
        }
        
        /*Si el campo de mínimo esta vacio no puede seguir*/
        if(sMin.compareTo("")==0 && iEv==0)
        {
            /*Coloca el borde rojo*/                               
            jTMin.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo mínimo esta vacio.", "Agregar producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edción y regresa*/
            jTMin.grabFocus();           
            return;
        }
        
        /*Si el campo de mínimo esta en 0 no puede seguir*/
        if(Float.parseFloat(sMin) == 0 && iEv==0)
        {
            /*Coloca el borde rojo*/                               
            jTMin.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de mínimo está en 0.", "Agregar producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edción y regresa*/
            jTMin.grabFocus();           
            return;
        }                        
        
        /*Si el campo de máximo esta vacio no puede seguir*/
        if(sMax.compareTo("")==0 && iEv==0)
        {
            /*Coloca el borde rojo*/                               
            jTMax.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo máximo esta vacio.", "Agregar Artícutlo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edción y regresa*/
            jTMax.grabFocus();           
            return;
        }
        
        /*Si el campo de máximo esta en 0 no puede seguir*/
        if(Float.parseFloat(sMax) == 0 && iEv==0)
        {
            /*Coloca el borde rojo*/                               
            jTMax.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de máximo está en 0.", "Agregar producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edción y regresa*/
            jTMax.grabFocus();           
            return;
        }        
        
        /*Si el campo de máximo es menor que mínimo no puede seguir*/
        if((Float.parseFloat(sMax) < Float.parseFloat(sMin)) && iEv==0) 
        {
            /*Coloca el borde rojo*/                               
            jTMax.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de máximo es menor que mímnimo.", "Agregar producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edción y regresa*/
            jTMin.grabFocus();           
            return;
        }
        
        /*Lee lo que mide el producto*/
        String sMedMan          = jTMedMan.getText();
                
        /*Si la medida manual es cadena vacia ponerlo en 0 para que la base de datos la reciba*/
        if(sMedMan.compareTo("")==0)
            sMedMan             = "0";

        /*Lee el checkbox de inventariable*/
        String sInvent          = "0";
        if(jCInvent.isSelected())
            sInvent             = "1";        
        
        /*Lee el checkbox de si es para venta o no*/
        String sEsVent          = "0";
        if(jCEsParaVent.isSelected())
            sEsVent             = "1";
        
        /*Lee el checkbox de compuesto*/
        String sCompue          = "0";
        if(jCComp.isSelected())
            sCompue             = "1";
        
        /*Lee lo que pesa el producto*/
        String sPesMan          = jTPesMan.getText();
                
        /*Si el peso es cadena vacia ponerlo en 0 para que la base de datos la reciba*/
        if(sPesMan.compareTo("")==0)
            sPesMan             = "0";

        //Si la descripción esta como vacia entonces mensajea solamente
        if(jTADescrip.getText().trim().compareTo("")==0)
            JOptionPane.showMessageDialog(null, "La descripción del producto esta vacia y no se podrá cargar el producto en el punto de venta.", "Agregar producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));        
        
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op             = {"Si","No"};
        int iRes                = JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Modificar Producto", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                         
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Si la unidad seleccionada es diferente a la que tenía originalmente entonces recuerdale al usario de como se manejan las unidades para las existencias*/
        if(sUnidOriP.compareTo(jComUni.getSelectedItem().toString().trim())!=0)        
            JOptionPane.showMessageDialog(null, "Las existencias de un producto se determina por su unidad de equivalencia, al cambiar la unidad el control de unidades podría ser extremadamente diferente.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Comprueba si el producto tiene kits asociados*/
        boolean bSi  = false;
        try
        {
            sQ = "SELECT codkit FROM kits WHERE codkit = '" + sProdOri.trim() + "'";                        
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
        
        /*Si tiene componentes y si ya no será kit entonces pregunta por que serán borrados*/
        if(bSi && !jCComp.isSelected())
        {            
            iRes    = JOptionPane.showOptionDialog(this, "El producto: " + sProdOri + " tiene componentes asociados. Si continuas serán borrados. ¿Quieres continuar?", "Modificar Producto", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
            if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            {
                //Cierra la base de datos y regresa
                Star.iCierrBas(con);                               
                return;                       
            }
            
            /*Borra todos los componentes de ese kit*/
            try
            {                      
                sQ = "DELETE FROM kits WHERE codkit = '" + sProdOri.trim() + "'";
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL)
             {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
                return;               
            }

        }/*Fin de if(bSi)*/
        
        //Comprueba si el código del producto existe               
        iRes        = Star.iExistProd(con, sProdOri.trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el producto existe entonces colcoa la bandera
        bSi     = false;
        if(iRes==1)
            bSi = true;
        
        /*Si el código del prodcuto no existe entonces*/
        if(!bSi)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea y regresa*/  
            JOptionPane.showMessageDialog(null, "El producto: " + sProdOri.trim() + " no existe.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                         
            return;
        }
        /*Si el el código del producto cambió entonces*/
        if(jTProd.getText().replace(" ", "").trim().compareTo(sProdOri)!=0 )
        {
            //Comprueba si el código del producto ya existe en la base de datos            
            iRes                = Star.iExistProd(con, jTProd.getText().replace(" ", "").trim());

            //Si hubo error entonces regresa
            if(iRes==-1)
                return;

            //Si el producto existe entonces coloca la bandera
            boolean bSiExist   = false;            
            if(iRes==1)
                bSiExist        = true;
                        
            /*Si ya existe un registro con este producto entonces*/
            if(bSiExist)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El código de producto: " + jTProd.getText().replace(" ", "").trim() + " ya existen.", "Producto Existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el campo de código del producto y regresa*/
                jTProd.grabFocus();               
                return;
            }
         
        }/*Fin de if(jTProd.getText().replace(" ", "").trim().compareTo(sProdOriginal)!=0 || jComAlma.getSelectedItem().toString().compareTo(jComAlma.getSelectedItem().toString()Original)!=0)*/
        /*Determina si se puede vender abajo del costo o no*/
        String sBajCost = "0";
        if(jCBajCost.isSelected())
            sBajCost    = "1";
        
        /*Obtiene si es servicio o no*/
        String sServ                = "0";
        if(jCServ.isSelected())
            sServ                   = "1";
        
        /*Si las listas de precio son cadenas vacia que sean 0*/
        if(sPre1.compareTo("")==0)
            sPre1 = "0";
        if(sPre2.compareTo("")==0)
            sPre2 = "0";
        if(sPre3.compareTo("")==0)
            sPre3 = "0";
        if(sPre4.compareTo("")==0)
            sPre4 = "0";
        if(sPre5.compareTo("")==0)
            sPre5 = "0";
        if(sPre6.compareTo("")==0)
            sPre6 = "0";
        if(sPre7.compareTo("")==0)
            sPre7 = "0";
        if(sPre8.compareTo("")==0)
            sPre8 = "0";
        if(sPre9.compareTo("")==0)
            sPre9 = "0";
        if(sPre10.compareTo("")==0)
            sPre10 = "0";
                
        /*Auitale el formato de moneda a las listas de precio*/
        sPre1   = sPre1.replace("$", "").replace(",", "");
        sPre2   = sPre2.replace("$", "").replace(",", "");
        sPre3   = sPre3.replace("$", "").replace(",", "");
        sPre4   = sPre4.replace("$", "").replace(",", "");
        sPre5   = sPre5.replace("$", "").replace(",", "");
        sPre6   = sPre6.replace("$", "").replace(",", "");
        sPre7   = sPre7.replace("$", "").replace(",", "");
        sPre8   = sPre8.replace("$", "").replace(",", "");
        sPre9   = sPre9.replace("$", "").replace(",", "");
        sPre10  = sPre10.replace("$", "").replace(",", "");
        
        /*Quitale el formato de moenda al costo*/
        sUltCost   = sUltCost.replace("$", "").replace(",", "");
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;
        /*Determina el método de costeo seleccionado*/
        String sMetCost = "ultcost";
        if(jRPEPS.isSelected())
            sMetCost    = "peps";
        else if(jRUEPS.isSelected())
            sMetCost    = "ueps";
        else if(jRProm.isSelected())
            sMetCost    = "prom";
        
        /*Determina si el producto va a manejar lote y pedimento*/
        String sLot     = "0";
        if(jCPed.isSelected())
            sLot        = "1";        
        
        /*Determina si el producto va a manejar número de serie*/
        String sNoSer   = "0";
        if(jCNoSer.isSelected())
            sNoSer      = "1";        
        
        /*Actualiza la base de datos*/
        try
        {                      
            sQ = "UPDATE prods SET "+
                     "descrip           = '" + jTADescrip.getText().replace("'", "''").trim() + "', " +
                     "lin               = '" + jComLin.getSelectedItem().toString().replace("'", "''").trim() + "', " + 
                     "tip               = '" + jComTip.getSelectedItem().toString().replace("'", "''").trim() + "', " + 
                     "garan             = '" + jTGara.getText().replace("'", "''").trim() + "', " + 
                     "clasjera          = '" + jTJera.getText().replace("'", "''").trim() + "', " + 
                     "marc              = '" + jComMarc.getSelectedItem().toString().replace("'", "''").trim() + "', " +
                     "fab               = '" + jComFab.getSelectedItem().toString().replace("'", "''").trim() + "', " +
                     "metcost           = '" + sMetCost + "', " +
                     "solser            = " + sNoSer + ", " +
                     "lotped            = " + sLot + ", " +
                     "colo              = '" + jComCol.getSelectedItem().toString().replace("'", "''").trim() + "', " +
                     "pes               = '" + jComPeso.getSelectedItem().toString().replace("'", "''").trim() + "', " +                     
                     "costre            = "  + sCostL.replace("$", "").replace(",", "").trim() + ", " +                     
                     "impue             = '"  + jComImp.getSelectedItem().toString().replace("'", "''").trim() + "', " +
                     "unid              = '" + jComUni.getSelectedItem().toString().replace("'", "''").trim() + "', " +
                     "codmed            = '" + jComMeds.getSelectedItem().toString().replace("'", "''").trim() + "', " +                     
                     "infor             = '" + jTAInfor.getText().replace("'", "''").trim() + "', " +
                     "codubi            = '" + jComUAd.getSelectedItem().toString().replace("'", "''").trim() + "', " +
                     "servi             =  " + sServ + ", " +
                     "codext            = '" + jComExt.getSelectedItem().toString().replace("'", "''").trim() + "', " +
                     "min               = '" + sMin + "', " +
                     "descripcort       = '" + jTNom.getText().replace("'", "''").trim() + "', " +
                     "max               = '" + sMax + "', " +
                     "pesman            = " + sPesMan + ", " +
                     "med               = " + sMedMan + ", " +                     
                     "anaq              = '" + jComAna.getSelectedItem().toString().replace("'", "''").trim() + "', " +
                     "lug               = '" + jComLug.getSelectedItem().toString().replace("'", "''").trim() + "', " +                     
                     "solmaxmin         = " + iEv + ", " +                                          
                     "mode              = '" + jComMod.getSelectedItem().toString().replace("'", "''").trim() + "', " +
                     "invent            = "  + sInvent + ", " +
                     "esvta             = "  + sEsVent + ", " + 
                     "compue            = "  + sCompue + ", " +                     
                     "utilvta1          = " + sUtil1V + ", " + 
                     "utilvta2          = " + sUtil2V + ", " + 
                     "utilvta3          = " + sUtil3V + ", " + 
                     "utilvta4          = " + sUtil4V + ", " + 
                     "utilvta5          = " + sUtil5V + ", " + 
                     "utilvta6          = " + sUtil6V + ", " + 
                     "utilvta7          = " + sUtil7V + ", " + 
                     "utilvta8          = " + sUtil8V + ", " + 
                     "utilvta9          = " + sUtil9V + ", " + 
                     "utilvta10         = " + sUtil10V + ", " + 
                     "prelist1          = "  + sPre1.replace("$", "").replace(",", "").trim() + ", " +
                     "prelist2          = "  + sPre2.replace("$", "").replace(",", "").trim() + ", " +
                     "prelist3          = "  + sPre3.replace("$", "").replace(",", "").trim() + ", " +
                     "prelist4          = "  + sPre4.replace("$", "").replace(",", "").trim() + ", " +
                     "prelist5          = "  + sPre5.replace("$", "").replace(",", "").trim() + ", " +
                     "prelist6          = "  + sPre6.replace("$", "").replace(",", "").trim() + ", " +
                     "prelist7          = "  + sPre7.replace("$", "").replace(",", "").trim() + ", " +
                     "prelist8          = "  + sPre8.replace("$", "").replace(",", "").trim() + ", " +
                     "prelist9          = "  + sPre9.replace("$", "").replace(",", "").trim() + ", " +
                     "prelist10         = "  + sPre10.replace("$", "").replace(",", "").trim() + ", " +
                     "bajcost           = "  + sBajCost + ", " +
                     "descprov          = '" + jTAnot.getText().replace("'", "''").trim() + "', " +
                     "fmod              = now(), " + 
                     "estac             = '" + Login.sUsrG.replace("'", "''").trim() + "', " + 
                     "sucu              = '" + Star.sSucu.replace("'", "''").trim() + "', " +
                     "nocaj             = '" + Star.sNoCaj.replace("'", "''").trim() + "', " +
                     "prodop1           = '" + jTCodOp1.getText().replace("'", "''").trim() + "', " +                     
                     "prodop2           = '" + jTCodOp2.getText().replace("'", "''").trim() + "', " + 
                     "provop1           = '" + jTCodProv.getText().replace("'", "''").trim() + "', " +                                                                
                     "provop2           = '" + jTCodProv1.getText().replace("'", "''").trim() + "' " + 
                     "WHERE prod        = '" + jTProd.getText().replace("'", "''").replace(" ", "").trim() + "'";                        
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL)
         {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }

        /*Inserta en log de productos*/
        try 
        {            
            sQ = "INSERT INTO logprods( cod,                                                descrip,                                        accio,                 estac,                                           sucu,                                     nocaj,            falt) " + 
                          "VALUES('" +  jTProd.getText().replace("'", "''") + "','" +       jTADescrip.getText().replace("'", "''") + "',   'MODIFICAR', '" +      Login.sUsrG.replace("'", "''") + "','" +         Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj + "', now())";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        /*Borra toda la tabla de modelos*/
        try
        {                      
            sQ = "DELETE FROM modelprod";
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL)
         {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        /*Recorre todo el arreglo de modelos*/        
        for(String sMod: sMods)
        {                    
            /*Inserta en la base de datos la relacion de los modelos*/
            try
            {                      
                sQ = "INSERT INTO modelprod(prod,                                                                  mode,           estac,                   sucu,                      nocaj) "
                             + "VALUES('" + jTProd.getText().replace("'", "''").replace(" ", "").trim() + "', '" + sMod + "', '" + Login.sUsrG + "', '" +   Star.sSucu + "', '" + Star.sNoCaj + "')";
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
        /*Borra toda la tabla de modelos*/
        try
        {                      
            sQ = "DELETE FROM terProdCompa WHERE prod='" + jTProd.getText().trim() + "'";
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL)
         {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        
        //Si no tiene compatibilidades
        if(sCompaMarcMod!=null)
        {
            /*Recorre todo el arreglo de compatibilidades*/        
            for(String sComp: sCompaMarcMod)
            {      
                //Tokeniza para quitar la raíz común
                java.util.StringTokenizer stk = new java.util.StringTokenizer(sComp, "|");

                String sMarca  = stk.nextToken();
                String sModelo = stk.nextToken();
                String sRut    = sComp + "|" + jComTip.getSelectedItem().toString();
                /*Inserta en la base de datos la relacion de las compatibilidades*/
                try
                {                      
                    sQ = "INSERT INTO terProdCompa(    prod,                                                                   marc,             model,             rut,             estac,                   sucu,                      nocaj) "
                                                 + "VALUES('" + jTProd.getText().replace("'", "''").replace(" ", "").trim() + "', '" +  sMarca + "', '" + sModelo + "', '" + sRut + "','" +   Login.sUsrG + "', '" +   Star.sSucu + "', '" + Star.sNoCaj + "')";
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
            
        }//if(sCompaMarcMod!=null)
        //Cambio alan
        /*Si hay series*/
        if(prods.sSers!=null)
        {
            /*Recorre todo el arreglo de string para obtener el número de parte*/
            for(int x = 0; x < prods.sSers.length; x++)
            {
                /*Actualiza la base de datos*/
                try
                {                      
                    sQ = "UPDATE serieprod SET "+
                             "comen          = '" + prods.sSers[x][1] + "' " + 
                             "WHERE prod     = '" + jTProd.getText().replace("'", "''").replace(" ", "").trim() + "' AND ser='" + prods.sSers[x][0] + "' AND alma='" + prods.sSers[x][3] + "'";                        
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
        }
        /*Borra toda la tabla de compatibilidades*/
        try
        {                      
            sQ = "DELETE FROM compa";
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL)
         {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        
        /*Recorre todo el arreglo de compatibilidades*/        
        for(String sComp: sCompa)
        {                    
            /*Inserta en la base de datos la relacion de las compatibilidades*/
            try
            {                      
                sQ = "INSERT INTO compa(prod,                                                                       compa,             estac,                   sucu,                      nocaj) "
                             + "VALUES('" + jTProd.getText().replace("'", "''").replace(" ", "").trim() + "', '" +  sComp + "', '" +   Login.sUsrG + "', '" +   Star.sSucu + "', '" + Star.sNoCaj + "')";
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
        /*Borra toda la tabla de asociaciones de marcas*/
        try
        {                      
            sQ = "DELETE FROM marcprod";
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL)
         {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        
        /*Recorre todo el arreglo de marcas*/        
        for(String sMarc: sMarcs)
        {                    
            /*Inserta en la base de datos la relacion de las marcas*/
            try
            {                      
                sQ = "INSERT INTO marcprod(prod,                                                                   marc,                estac,                   sucu,                      nocaj) "
                             + "VALUES('" + jTProd.getText().replace("'", "''").replace(" ", "").trim() + "', '" + sMarc + "', '" +     Login.sUsrG + "', '" +   Star.sSucu + "', '" + Star.sNoCaj + "')";
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
        /*Borra toda la tabla de números de parte*/
        try
        {                      
            sQ = "DELETE FROM prodpart";
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        /*Recorre todo el arreglo de números de parte*/        
        for(String sPart: sParts)
        {            
            /*Inserta en la base de datos la relacion de los números de series*/
            try
            {                      
                sQ = "INSERT INTO prodpart( prod,                                                                  part,                estac,                   sucu,                 nocaj) "
                             + "VALUES('" + jTProd.getText().replace("'", "''").replace(" ", "").trim() + "', '" + sPart + "', '" +     Login.sUsrG + "', '" +   Star.sSucu + "', '" + Star.sNoCaj + "')";
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
//se esta probando si esto afecta el guardado de la base de datos y elimino para solucionar un problema de cerrado de base de datos solucion chalo
        //Termina la transacción
//        if(Star.iTermTransCon(con)==-1)
//        {
//            System.out.println("mal");
//            return;
//        }
        /*Obtiene la fecha de la actualización*/
        try
        {
            sQ = "SELECT falt FROM prods WHERE prod = '" + jTProd.getText() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colocalo en el control*/
            if(rs.next())
                jLFMod.setText(rs.getString("falt"));
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

        /*Coloca el usuario que lo acaba de modificar*/
        jTUltEstacMod.setText(Login.sUsrG);                                                   
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Producto: " + jTProd.getText() + " modificado con éxito.", "Éxito al modificar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
        /*Dale formato de moneda a todos los precios de lista y costos nuevamente*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        double dCant    = Double.parseDouble(sPre1.replace("$", "").replace(",", ""));                
        sPre1           = n.format(dCant);
        dCant           = Double.parseDouble(sPre2.replace("$", "").replace(",", ""));                
        sPre2           = n.format(dCant);
        dCant           = Double.parseDouble(sPre3.replace("$", "").replace(",", ""));                
        sPre3           = n.format(dCant);
        dCant           = Double.parseDouble(sPre4.replace("$", "").replace(",", ""));                
        sPre4           = n.format(dCant);
        dCant           = Double.parseDouble(sPre5.replace("$", "").replace(",", ""));                
        sPre5           = n.format(dCant);
        dCant           = Double.parseDouble(sPre6.replace("$", "").replace(",", ""));                
        sPre6           = n.format(dCant);
        dCant           = Double.parseDouble(sPre7.replace("$", "").replace(",", ""));                
        sPre7           = n.format(dCant);
        dCant           = Double.parseDouble(sPre8.replace("$", "").replace(",", ""));                
        sPre8           = n.format(dCant);
        dCant           = Double.parseDouble(sPre9.replace("$", "").replace(",", ""));                
        sPre9           = n.format(dCant);
        dCant           = Double.parseDouble(sPre10.replace("$", "").replace(",", ""));                
        sPre10          = n.format(dCant);
        dCant           = Double.parseDouble(sCostL.replace("$", "").replace(",", ""));                
        sCostL          = n.format(dCant);
        dCant           = Double.parseDouble(sUltCost.replace("$", "").replace(",", ""));                
        sUltCost        = n.format(dCant);
        
    }//GEN-LAST:event_jBGuarActionPerformed
                                            
            
    /*Cuando se presiona una tecla en el panel de ubicación*/
    private void jPanel2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPanel2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de anaquel*/
    private void jTAnaqFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAnaqFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAnaq.setSelectionStart(0);jTAnaq.setSelectionEnd(jTAnaq.getText().length());        
        
    }//GEN-LAST:event_jTAnaqFocusGained

    
    /*Cuando se presiona una tecla en el campo de anaquel*/
    private void jTAnaqKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAnaqKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAnaqKeyPressed

    
    /*Cuando pasa una acción en el combobox de anaqs*/
    private void jComAnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComAnaActionPerformed
        
        /*Si no hay selección entonces regresa*/
        if(jComAna.getSelectedItem()==null)
            return;       
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                                                                    

        /*Obtén la descripción del anaquel de la base de datos*/        
        try
        {
            sQ = "SELECT * FROM anaqs WHERE cod = '" + jComAna.getSelectedItem().toString() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el control y el caret al principio*/
                jTAnaq.setText(rs.getString("descrip"));                                
                jTAnaq.setCaretPosition(0);
            }
            else
                jTAnaq.setText("");                                                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);        
                
    }//GEN-LAST:event_jComAnaActionPerformed

    
    /*Cuando se presiona una tecla en el combobox de anaqs*/
    private void jComAnaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComAnaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComAnaKeyPressed

    
    /*Cuando se presiona una tecla en el combobox de lugar*/
    private void jComLugKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComLugKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComLugKeyPressed

    
    /*Cuando se presiona una tecla en el campo de lugar*/
    private void jTLugKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLugKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTLugKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de lugar*/
    private void jTLugFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLugFocusGained
        
       /*Selecciona todo el texto cuando gana el foco*/
       jTLug.setSelectionStart(0);jTLug.setSelectionEnd(jTLug.getText().length());       
        
    }//GEN-LAST:event_jTLugFocusGained

    
    /*Cuando se genera una acción en el combobox de lugar*/
    private void jComLugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComLugActionPerformed
        
        /*Si no hay selección entonces regresa*/
        if(jComLug.getSelectedItem()==null)
            return;        
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                                    
        
        /*Obtén la descripción del lugar de la base de datos*/        
        try
        {
            sQ = "SELECT descrip FROM lugs WHERE cod = '" + jComLug.getSelectedItem().toString() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el control y el caret al principio*/
                jTLug.setText(rs.getString("descrip"));                              
                jTLug.setCaretPosition(0);
            }
            else
                jTLug.setText("");                                                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);
                
    }//GEN-LAST:event_jComLugActionPerformed

    
    /*Cuando se presiona una tecla en el checkbox de artículo invent*/
    private void jCInventKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCInventKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jCInventKeyPressed

    
    /*Cuando sucede una acción en el checkbox de invent*/
    private void jCInventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCInventActionPerformed
        
        /*Si esta seleccionado entonces*/
        if(jCInvent.isSelected())
        {
            /*Deshabilita el checkbox de compuesto*/
            jCComp.setSelected(false);        
            
            /*Deshabilita el checkbox de servicio*/
            jCServ.setSelected(false);        
            
            /*Deshabilita el botón de componentes*/
            jBComps.setEnabled(false);
        }
        
    }//GEN-LAST:event_jCInventActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de anotaciones*/
    private void jTAnotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAnotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAnot.setSelectionStart(0);        
        jTAnot.setSelectionEnd(jTAnot.getText().length());        
        
    }//GEN-LAST:event_jTAnotFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de descuentos del proveedor*/
    private void jTAnotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAnotFocusLost

        /*Coloca el cursor al principio del control*/
        jTAnot.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTAnot.getText().length()> 100)
            jTAnot.setText(jTAnot.getText().substring(0, 100));
        
    }//GEN-LAST:event_jTAnotFocusLost

    
    /*Cuando se presiona una tecla en el campo de anotaciones*/
    private void jTAnotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAnotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAnotKeyPressed
                        
    
    
    
    
    
   
    /*Cuando se gana el foco del teclado en el campo de código opcional 2*/
    private void jTCodOp2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodOp2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCodOp2.setSelectionStart(0);jTCodOp2.setSelectionEnd(jTCodOp2.getText().length());
        
    }//GEN-LAST:event_jTCodOp2FocusGained

    
   
    
    /*Cuando se pierde el foco del teclado en el campo del código opcional 2*/
    private void jTCodOp2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodOp2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCodOp2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTCodOp2.getText().length()> 30)
            jTCodOp2.setText(jTCodOp2.getText().substring(0, 30));
                
    }//GEN-LAST:event_jTCodOp2FocusLost

    
    /*Cuando se gana el foco del teclado en el campo de código opcional 2*/
    private void jTCodOp2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodOp2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        

    }//GEN-LAST:event_jTCodOp2KeyPressed

    
    
    /*Cuando se levanta una tecla en el campo de código opcional 2*/
    private void jTCodOp2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodOp2KeyReleased
        
        //Declara variables locales
        String sTe;
        
        
        
        
        /*Lee el texto introducido por el usuario*/
        sTe = jTCodOp2.getText();
        
        /*Conviertelo a mayúsculas*/
        sTe = sTe.toUpperCase();
        
        /*Vuelve a colocarlo*/
        jTCodOp2.setText(sTe);
        
    }//GEN-LAST:event_jTCodOp2KeyReleased

    
    /*Cuando se pierde el foco del teclado en el campo de código de artículos*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTProd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si es cadena vacia entonces*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Limpia todos los campos y regresa*/
            vLim();
            return;
        }
        
        /*Carga todos los datos del producto en los campos*/
        vLoadDat(jTProd.getText().trim());
        
    }//GEN-LAST:event_jTProdFocusLost
           
    
    /*Cuando se tipea una tecla en el campo de código de artículo*/
    private void jTProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTProdKeyTyped

    
    /*Cuando se presiona una tecla en el campo de cargar imágen*/
    private void jBCargImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargImgKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCargImgKeyPressed
    
    
    /*Cuando se presiona el botón de cargar*/
    private void jBCargImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargImgActionPerformed
        
        /*Si no a seleccionado un producto de la tabla entonces*/
        if(jTab.getSelectedRow()==-1 && jTProd.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una producto.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de prods y regresa*/
            jTab.grabFocus();            
            return;            
        }                
        
        /*Si no selecciono nada entonces el código del producto lo leera del control*/
        String sProd;
        if(jTab.getSelectedRow()==-1)
            sProd                    = jTProd.getText();
        else
            sProd                    = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Comprueba si el producto existe en la base de datos
        int iRes        = Star.iExistProd(con, jTProd.getText().trim());

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si no existe el producto entonces
        if(iRes==0)
        {
            //Mensajea y regresa
            JOptionPane.showMessageDialog(null, "El producto: " + jTProd.getText() + " no existe para poder cargarle una imágen.", "Cargar imágen", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                
            return;
        }
                
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }
        
        /*Si la carpeta de las imágenes no existe entonces crea la carpeta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de los prods no existe entonces crea la carpeta*/
        sCarp                    += "\\Productos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta del producto en específico no existe entonces crea la carpeta*/
        sCarp                    += "\\" + sProd;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Ya existe una imágen para el producto: " + sProd + " en \"" + sCarp + "\".", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));          
                return;
            }
        }                    

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar Imágen");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut                = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Completa la ruta final con el nom del archivo a donde se copiara*/
            sCarp               +=  "\\" + fc.getSelectedFile().getName();  

            /*Si el nombre del archivo no termina con una extensión de imágen entonces*/
            if(!fc.getSelectedFile().getName().endsWith(".jpg") && !fc.getSelectedFile().getName().endsWith(".jpeg") && !fc.getSelectedFile().getName().endsWith(".bmp") && !fc.getSelectedFile().getName().endsWith(".gif") && !fc.getSelectedFile().getName().endsWith(".png"))
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El formato de la imágen debe ser un formato de imágen conocido.", "Imágen", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                return;
            }
            
            /*Si el archivo de origén no existe entonces*/
            if( !new File(sRut).exists())
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El archivo de origén \"" + sRut + "\" no existe.", "Archivo no existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Copia el archivo orgien al destino*/
            try
            {
                org.apache.commons.io.FileUtils.copyFile(new File(sRut), new File(sCarp));
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                        
                return;               
            }

            /*Carga la imágen en el panel*/
            jLImg.setIcon(new ImageIcon(sRut));

            /*Que el label sea visible*/
            jLImg.setVisible(true);

        }/*Fin de if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)*/
        
    }//GEN-LAST:event_jBCargImgActionPerformed

    
    /*Cuando se presiona el botón de borrar imágen*/
    private void jBDelImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelImgKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelImgKeyPressed

    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelImgActionPerformed
                
        /*Si no a seleccionado un producto de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un producto.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                      
            return;            
        }

        /*Preguntar al usuario si esta seguro de que quiere borrar la imágen*/
        Object[] op         = {"Si","No"};
        int iRes            = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la imágen?", "Borrar Imágen", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/        
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
            return;
        }
        
        /*Si la carpeta de imágenes no existe entonces creala*/
        sCarp            += "\\Imagenes"; 
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de prods no existe entonces creala*/
        sCarp            += "\\Productos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces creala*/
        sCarp            += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta del producto en específico no existe entonces creala*/
        sCarp             += "\\" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El producto: " + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + " no contiene imágen para borrar en \"" + sCarp + "\".", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
                return;
            }
        }
                   
        /*Borra todos los archivos de la carpeta*/
        try
        {
            org.apache.commons.io.FileUtils.cleanDirectory(new File(sCarp)); 
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                        
            return;
        }                       
        
        /*Que no sea visible el control de la imágen*/
        jLImg.setVisible(false);
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Imágen borrada con éxito para producto: " + jTab.getValueAt(jTab.getSelectedRow(), 1).toString(), "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBDelImgActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de búscar*/
    private void jTBuscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusGained

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBBusc);
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTBusc.setSelectionStart(0);jTBusc.setSelectionEnd(jTBusc.getText().length());
        
    }//GEN-LAST:event_jTBuscFocusGained

    
    /*Cuando se presiona una tecla en el campo de buscar*/
    private void jTBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscKeyPressed

        /*Si se presionó enter entonces presiona el botón de búsqueda*/
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            jBBusc.doClick();
        /*Else if se presiono CTRL + B entonces*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_B)
        {
            /*Llama a la forma para búscar productos avanzadamente*/
            ptovta.BuscAvan r = new ptovta.BuscAvan(this, jTProd, null, null, null);
            r.setVisible(true);
            
            /*Coloca el foco del teclado en el campo del producto*/
            jTProd.grabFocus();
        }   
        /*Else llama a la función escalable*/
        else               
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTBuscKeyPressed

    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed
        
        /*Si el campo de buscar esta vacio no puede seguir*/
        if(jTBusc.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de búsqueda esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de búsqueda y regresa*/
            jTBusc.grabFocus();           
            return;
        }                      
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Borra todos los item en la tabla de prods*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
         /*Resetea el contador de filas*/
        iContFi                 = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todos los productos*/        
        try
        {                  
            sQ = "SELECT * FROM prods WHERE prod LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR prodop1 LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR prodop2 LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR codmed LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR descrip LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR marc LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR lin LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR fab LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR colo LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR pes LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR pes LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR med LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR cost LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR costre LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR exist LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR unid LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR anaq LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR lug LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR alma LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR descprov LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR infor LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR min LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR max LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR estac LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR falt LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR fmod LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') ORDER BY prod";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                
                /*Cargalo en la tabla*/
                DefaultTableModel te  = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("prod"), rs.getString("descrip"), rs.getString("alma")};
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

        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed
        
        /*Borra todos los item en la tabla de productos*/
        DefaultTableModel dm    = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi                 = 1;
        
        /*Agrega todos los datos de la base de datos a la tabla de productos*/
        vCargProdsTab();                                
                
        /*Vuelve a poner el foco del teclado en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBMosTActionPerformed

    
    /*Carga en la tabla de articulos los registros*/
    private void vCargProdsTab()
    {               
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();
        te.setRowCount(0);
        
        /*Inicializa el contador de filas*/
        iContFi         = 1;

        /*Declara variables de base dedatos*/
        Statement   st;
        ResultSet   rs;                
        String      sQ;
        
        /*Obtiene todos los productos de la base de datos*/
        try
        {
            sQ = "SELECT * FROM prods ORDER BY prod";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[]     = {iContFi, rs.getString("prod"), rs.getString("descrip"), rs.getString("alma")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas de la tabla*/
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
        
    }/*Fin de private void vCargProdsTab()*/
    
    
    /*Cuando se presiona una tecla en el campo de última compra*/
    private void jTUltComKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUltComKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTUltComKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de última compra*/
    private void jTUltComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUltComFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTUltCom.setSelectionStart(0);jTUltCom.setSelectionEnd(jTUltCom.getText().length());        

    }//GEN-LAST:event_jTUltComFocusGained

    
    /*Cuando se presiona una tecla en el campo de proveedor*/
    private void jTProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTProvKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de proveedor*/
    private void jTProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProv.setSelectionStart(0);jTProv.setSelectionEnd(jTProv.getText().length());

    }//GEN-LAST:event_jTProvFocusGained

    
    /*Cuando se presiona una tecla en el campo de factura*/
    private void jTFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFacKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTFacKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de factura*/
    private void jTFacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFacFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProv.setSelectionStart(0);jTProv.setSelectionEnd(jTProv.getText().length());

    }//GEN-LAST:event_jTFacFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de usuario*/
    private void jTEstacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstacFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEstac.setSelectionStart(0);jTEstac.setSelectionEnd(jTEstac.getText().length());
        
    }//GEN-LAST:event_jTEstacFocusGained

    
    /*Cuando se presiona una tecla en el campo de usuario*/
    private void jTEstacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstacKeyPressed

    
    /*Cuando se presiona una tecla en el botón de mostrar todo*/
    private void jBMosTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMosTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMosTKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de artículo compue*/
    private void jCCompKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCompKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jCCompKeyPressed

    
    /*Cuando sucede una acción en el checkbox de artículo compue*/
    private void jCCompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCCompActionPerformed
        
        /*Si esta seleccionado entonces*/
        if(jCComp.isSelected())
        {
            /*No puede ser un servicio*/
            jCServ.setSelected(false);
            
            /*Deshabilita el checkbox de inventariable*/
            jCInvent.setSelected(false);
            
            /*Habilita el botón de componentes*/
            jBComps.setEnabled(true);
        }
        /*Else deshablita el botón de componentes*/
        else
            jBComps.setEnabled(false);        
        
    }//GEN-LAST:event_jCCompActionPerformed

    
    /*Cuando se presiona una tecla en el botón componentes*/
    private void jBCompsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCompsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCompsKeyPressed

    
    /*Cuando se presiona el botón de componentes*/
    private void jBCompsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCompsActionPerformed
        
        /*Si el código del kit es cadena vacia entonces*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado un código para el producto.", "Código de producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo del código del producto y regresa*/
            jTProd.grabFocus();                        
            return;            
        }
        
        /*Llama al formulario de kits y pasale el código del artículo*/
        Comps com = new Comps(jTProd.getText(), jTADescrip.getText().trim());
        com.setVisible(true);
        
    }//GEN-LAST:event_jBCompsActionPerformed
                
    
    /*Cuando se presiona el botón de buscar coincidencia*/
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProd.getText(), 2, jTProd, null, jTAlmaG, "", null);
        b.setVisible(true);
        
        //cambio alan
        jTProd.grabFocus();
                        
    }//GEN-LAST:event_jBProdActionPerformed

    
    /*Cuando se presiona una tecla en el campo de buscar coincidencia*/
    private void jBProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProdKeyPressed

    
    /*Cuando se presiona el botón de generar*/
    private void jBGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGenActionPerformed
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables locales
        String      sProd;
        
        /*Mientras el código del nuevo producto este repetido seguirá obteniendo uno que no este repetido*/
        boolean bSiExist;
        do
        {
            /*Inicialmente no existe el código del producto*/
            bSiExist = false;
            
            /*Obtiene el código del nuevo producto*/
            sProd                = Star.sGenClavDia();            
        
            //Obtiene si ya existe ese código del producto en la base de datos            
            int iRes        = Star.iExistProd(con, sProd.trim());

            //Si hubo error entonces regresa
            if(iRes==-1)
                return;

            //Si el producto existe entonces coloca la bandera
            if(iRes==1)
                bSiExist    = true;
                        
        }while(bSiExist);                
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Coloca en el campo el código del producto que se genera del día de hoy*/
        jTProd.setText(sProd);
        
        //cambio alan
        jTProd.grabFocus();
                
    }//GEN-LAST:event_jBGenActionPerformed

    
    /*Cuando se presiona una tecla en el botón de generar*/
    private void jBGenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGenKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jBGenKeyPressed
           
        
    /*Cuando se gana el foco del teclado en el campo del código del proveedor opcional 1*/
    private void jTCodProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProvFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodProv.setSelectionStart(0);jTCodProv.setSelectionEnd(jTCodProv.getText().length());

    }//GEN-LAST:event_jTCodProvFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del código del proveedor opcional 1*/
    private void jTCodProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProvFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCodProv.setCaretPosition(0);
                        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                       
        
        /*Comprueba si el código del proveedor existe*/
        boolean bSiExist   = false;
        try
        {
            sQ = "SELECT nom FROM provs WHERE prov = '" + jTCodProv.getText() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
           if(rs.next())
                bSiExist   = true;                           
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }

        /*Si el código del proveedor no existe entonces resetea el campo*/
        if(!bSiExist)
            jTCodProv.setText("");            

        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTCodProv.getText().length()> 30)
            jTCodProv.setText(jTCodProv.getText().substring(0, 30));
        
    }//GEN-LAST:event_jTCodProvFocusLost

    
    /*Cuando se presiona una tecla en el campo del código del proveedor opcional 1*/
    private void jTCodProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodProvKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTCodProv.getText(), 3, jTCodProv, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCodProvKeyPressed

    
    /*Cuando se tipea una tecla en el campo del código del proveedor opcional 1*/
    private void jTCodProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodProvKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCodProvKeyTyped

    
    /*Cuando se presiona el botón de buscar proveedor 1*/
    private void jBBusc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBusc2ActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTCodProv.getText(), 3, jTCodProv, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBBusc2ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar proveedor 2*/
    private void jBBusc2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBusc2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBusc2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del código del proveedor opcional 2*/
    private void jTCodProv1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProv1FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodProv.setSelectionStart(0);jTCodProv.setSelectionEnd(jTCodProv.getText().length());

    }//GEN-LAST:event_jTCodProv1FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del código del proveedor opcional 2*/
    private void jTCodProv1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProv1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCodProv1.setCaretPosition(0);
                    
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Comprueba si el código del proveedor existe*/
        boolean bSiExist   = false;
        try
        {
            sQ = "SELECT nom FROM provs WHERE prov = '" + jTCodProv1.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSiExist   = true;                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;
        }

        /*Si el código del proveedor no existe entonces resetea el campo*/
        if(!bSiExist)
            jTCodProv1.setText("");            

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jTCodProv1FocusLost

    
    /*Cuando se presiona una tecla en el campo del código del proveedor opcional 2*/
    private void jTCodProv1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodProv1KeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTCodProv1.getText(), 3, jTCodProv1, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCodProv1KeyPressed

    
    /*Cuando se tipea una tecla en el campo del código del proveedor opcional 2*/
    private void jTCodProv1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodProv1KeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCodProv1KeyTyped

    
    /*Cuando se presiona el botón de buscar proveedor 2*/
    private void jBBusc3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBusc3ActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTCodProv1.getText(), 3, jTCodProv1, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBBusc3ActionPerformed

    
    /*Cuando se presiona el botón de buscar proveedor 1*/
    private void jBBusc3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBusc3KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBusc3KeyPressed
                
    
    /*Cuando se presiona una tecla en el checkbox de es para venta*/
    private void jCEsParaVentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCEsParaVentKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCEsParaVentKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del nombre*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo el nombre*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);        
        jTNom.setSelectionEnd(jTNom.getText().length());        
        
    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del nombre*/
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost

        /*Coloca el cursor al principio del control*/
        jTNom.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTNom.getText().compareTo("")!=0)
            jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTNom.getText().length()> 255)
            jTNom.setText(jTNom.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTNomFocusLost

       
    /*Cuando se presiona una tecla en el combobox de impue*/
    private void jComImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComImpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComImpKeyPressed

    
    
    /*Cuando se presiona una tecla en el botón de ficha técnica*/
    private void jBCargFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCargFKeyPressed

    
    /*Cuando se presiona el botón de ficha técnica*/
    private void jBCargFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargFActionPerformed
        
        //Declara variables locales
        String      sRut;                
        String      sCarp;                    
        String      sProd;
        
        
        
        
        /*Si no a seleccionado un producto de la tabla entonces*/
        if(jTab.getSelectedRow()==-1 && jTProd.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una producto.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de prods*/
            jTab.grabFocus();
            
            /*Regresa por que no puede continuar*/
            return;
            
        }
        
        /*Obtiene el nom del producto seleccionado*/
        int row                         = jTab.getSelectedRow();
        
        /*Si no selecciono nada entonces el código del producto lo leera del control*/
        if(row==-1)
            sProd                    = jTProd.getText();
        else
            sProd                    = jTab.getValueAt(row, 1).toString();
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
            return;                        
        }
        
        /*Si la carpeta de la ficha técnica no existe entonces crea la carpeta*/
        sCarp                    += "\\FTecnica";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta del producto en específico no existe entonces crea la carpeta*/
        sCarp                    += "\\" + sProd;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Ya existe una ficha técnica para el producto \"" + sProd + "\" en \"" + sCarp + "\".", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
                return;
            }
        }                   

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar Ficha Técnica");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut               = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut               += "\\" + fc.getSelectedFile().getName();    

            /*Completa la ruta final con el nom del archivo a donde se copiara*/
            sCarp            +=  "\\" + fc.getSelectedFile().getName();  

            /*Si el archivo de origén no existe entonces*/
            if( !new File(sRut).exists())
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El archivo de origén \"" + sRut + "\" no existe.", "Archivo no existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));               
                return;
            }

            /*Copia el archivo orgien al destino*/
            try
            {
                org.apache.commons.io.FileUtils.copyFile(new File(sRut), new File(sCarp));
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                    
            }
            
        }/*Fin de if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)*/
        
    }//GEN-LAST:event_jBCargFActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar ficha técnica*/
    private void jBDelFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelFKeyPressed

    
    /*Cuando se presiona una tecla en el botón de mostrar ficha técnica*/
    private void jBFTecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBFTecKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBFTecKeyPressed

    
    /*Cuando se presiona el botón de borrar ficha técnica*/
    private void jBDelFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelFActionPerformed
        
        //Declara variables locales
        String      sProd;
        String      sCarp;
        String      sRut;                
        
        
        
                
        /*Si no a seleccionado un producto de la tabla entonces*/
        if(jTab.getSelectedRow()==-1 && jTProd.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un producto.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();            
            return;            
        }

        /*Obtiene la fila seleccionada*/
        int row         = jTab.getSelectedRow();
        
        /*Si no selecciono nada entonces el código del producto lo leera del control*/
        if(row==-1)
            sProd                    = jTProd.getText();
        else
            sProd                    = jTab.getValueAt(row, 1).toString();                
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                       
            return;
        }
        
        /*Si la carpeta de ficha técnica no existe entonces crea la carpeta*/
        sCarp            += "\\FTecnica"; 
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta del producto en específico no existe entonces crea la carpeta*/
        sCarp             += "\\" + sProd;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El producto: " + sProd + " no contiene ficha técnica para borrar en \"" + sCarp + "\".", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
                return;
            }
        }                    

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen a borrar*/
        final JFileChooser fc   = new JFileChooser  (sCarp);
        fc.setDialogTitle                           ("Borrar Imágen");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut               = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut               += "\\" + fc.getSelectedFile().getName();  

            /*Preguntar al usuario si esta seguro de que quiere borrar la imágen*/
            Object[] op         = {"Si","No"};
            int iRes            = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la ficha técnica?", "Borrar Ficha", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
            if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                return;

            /*Borra la imágen*/
            new File(sRut).delete();

            /*Que no sea visible el label del logotipo*/
            jLImg.setVisible(false);

            /*Mensaje de éxito*/
            JOptionPane.showMessageDialog(null, "Ficha técnica borrada con éxito para producto \"" + sProd + "\" desde \"" + sRut + "\".", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

        }/*Fin de if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION) */
        
    }//GEN-LAST:event_jBDelFActionPerformed

    
    /*Cuando se presiona el botón de borrar ficha ténica*/
    private void jBFTecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFTecActionPerformed
        
        //Declara variables locales        
        String      sCarp;        
        String      sProd;

        
        
                
        /*Si no a seleccionado un producto de la tabla entonces*/
        if(jTab.getSelectedRow()==-1 && jTProd.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un producto.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();           
            return;
        }

        /*Obtiene la fila seleccionada*/
        int row                         = jTab.getSelectedRow();
        
        /*Si no selecciono nada entonces el código del producto lo leera del control*/
        if(row==-1)
            sProd                       = jTProd.getText();
        else
            sProd                       = jTab.getValueAt(row, 1).toString();                

        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existe entonces crea la carpeta*/
        sCarp                    += "\\FTecnica";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta del prodcuto en específico no existe entonces crea la carpeta*/
        sCarp                    += "\\" + sProd;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene productos entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y mensajea*/
                JOptionPane.showMessageDialog(null, "No existe una ficha técnica para el producto: " + sProd + " en \"" + sCarp + "\".", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
                return;
            }
        }                    
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si contiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista del archivo*/
                String sArch [] = new File(sCarp).list();

                /*Abre el archivo*/
                try
                {
                    Desktop.getDesktop().open(new File(sCarp + "\\" + sArch[0]));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                }
            }
        }                    
        
    }//GEN-LAST:event_jBFTecActionPerformed

    
    /*Cuando se presiona una tecla en el checkbox de no soclitar máximos y mínimos*/
    private void jCNoSolMaxMinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCNoSolMaxMinKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCNoSolMaxMinKeyPressed

    
    /*Cuando se presiona una tecla en el botón de precios*/
    private void jBPrecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPrecKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBPrecKeyPressed

    
    /*Cuando se presiona el botón de precios*/
    private void jBPrecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPrecActionPerformed

        /*Si no a ingresado un producto entonces*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un producto primeramente.", "Listas de precios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control y regresa*/
            jTProd.grabFocus();                        
            return;
        }
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Comprueba si el producto existe        
        int iRes        = Star.iExistProd(con, jTProd.getText().trim());

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si el producto no existe entonces coloca la bandera
        boolean bSi = true;
        if(iRes==0)
            bSi     = false;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Muestra el formulario para definir las listas de precios del producto*/
        LPrecs l = new LPrecs(jTProd.getText().trim(), null, bSi, prods);
        l.setVisible(true);
        
    }//GEN-LAST:event_jBPrecActionPerformed
        
    
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

    
    /*Cuando se presiona una tecla en el combobox de clasificación especial*/
    private void jComExtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComExtKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComExtKeyPressed

    
    
    
    /*Cuando se presiona una tecla en el combobx de ubicación adicional*/
    private void jComUAdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComUAdKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComUAdKeyPressed
        
    
    /*Cuando sucede una acción en el combobox de configuración extra*/
    private void jComExtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComExtActionPerformed
        
        /*Si no hay selección entonces regresa*/
        if(jComExt.getSelectedItem()==null)
            return;        
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ       = "";                                
        
        /*Obtén la descripción de la clasificación de la base de datos*/        
        try
        {
            sQ = "SELECT * FROM clasprod WHERE cod = '" + jComExt.getSelectedItem().toString() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos etonces*/
            if(rs.next())
            {
                /*Coloca el valor en el contrl y el caret al principio*/
                jTClas.setText(rs.getString("descrip"));                                               
                jTClas.setCaretPosition(0);
            }
            else
                jTClas.setText("");                                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);        
                
    }//GEN-LAST:event_jComExtActionPerformed
    
        
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

    
    /*Cuando sucede una acción en el combobox de ubicación adicional*/
    private void jComUAdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComUAdActionPerformed
        
        /*Si no hay selección entonces regresa*/
        if(jComUAd.getSelectedItem()==null)
            return;        
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ       = "";                                            
        
        /*Obtén la descripción del lugar de la base de datos*/        
        try
        {
            sQ = "SELECT * FROM ubiad WHERE cod = '" + jComUAd.getSelectedItem().toString() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el control y el caret al principio*/
                jTUbi.setText(rs.getString("descrip"));                              
                jTUbi.setCaretPosition(0);
            }
            else
                jTUbi.setText("");                                                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);                    
                
    }//GEN-LAST:event_jComUAdActionPerformed

    
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

    
    /*Cuando se presiona una tecla en el checkbox de servicio*/
    private void jCServKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCServKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jCServKeyPressed

    
    /*Cuando sucede una acción en el checkbox de servicio*/
    private void jCServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCServActionPerformed
        
        /*Si esta seleccionado entonces*/
        if(jCServ.isSelected())
        {
            /*No puede ser inventariable*/
            jCInvent.setSelected(false);
            
            /*No puede ser un kit*/
            jCComp.setSelected(false);
        }
        /*Else no esta seleccionado entonces selecciona que será inventariable*/
        else
            jCInvent.setSelected(true);
        
    }//GEN-LAST:event_jCServActionPerformed

    
    /*Cuando se pierde el foco del teclado en el combo de impuesto*/
    private void jComImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComImpFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComImp.getSelectedItem().toString().compareTo("")!=0)
            jComImp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComImpFocusLost

    
    
    /*Cuando se pierde el foco del teclado en el combo del anaquel*/
    private void jComAnaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComAnaFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComAna.getSelectedItem().toString().compareTo("")!=0)
            jComAna.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComAnaFocusLost

    
    /*Cuando se pierde el foco del teclado en el combo de lugar*/
    private void jComLugFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComLugFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComLug.getSelectedItem().toString().compareTo("")!=0)
            jComLug.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComLugFocusLost

    
    /*Cuando se pierde el foco del teclado en el combo de ubicación adicional*/
    private void jComUAdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComUAdFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComAna.getSelectedItem().toString().compareTo("")!=0)
            jComAna.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComUAdFocusLost

    
    /*Cuando sucede una acción en el combobox de impuestos*/
    private void jComImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComImpActionPerformed
        
        /*Si no hay selección regresa*/
        if(jComImp.getSelectedItem()==null)
            return;        
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ       = "";                                                            
        
        /*Obtén el valor del impuesto de la base de datos*/        
        try
        {
            sQ  = "SELECT impueval FROM impues WHERE codimpue = '" + jComImp.getSelectedItem().toString() + "'";            
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTImpueVal.setText(rs.getString("impueval"));                                                
            else
                jTImpueVal.setText("");                                                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComImpActionPerformed

    /*Cuando se levanta una tecla en el campo de código opcional 1*/
    private void jTCodOp1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodOp1KeyReleased

        //Declara variables locales
        String sTe;

        /*Lee el texto introducido por el usuario*/
        sTe = jTCodOp1.getText();

        /*Conviertelo a mayúsculas*/
        sTe = sTe.toUpperCase();

        /*Vuelve a colocarlo*/
        jTCodOp1.setText(sTe);

    }//GEN-LAST:event_jTCodOp1KeyReleased

    /*Cuando se pierde el foco del teclado en el campo del código opcional 1*/
    private void jTCodOp1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodOp1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCodOp1.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTCodOp1.getText().length()> 30)
            jTCodOp1.setText(jTCodOp1.getText().substring(0, 30));

    }//GEN-LAST:event_jTCodOp1FocusLost

    /*Cuando se gana el foco del teclado en el campo de código opcional 1*/
    private void jTCodOp1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodOp1FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodOp1.setSelectionStart(0);jTCodOp1.setSelectionEnd(jTCodOp1.getText().length());

    }//GEN-LAST:event_jTCodOp1FocusGained

    
    /*Cuando se presiona una tecla en el campo del producto opcional 1*/
    private void jTCodOp1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodOp1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jTCodOp1KeyPressed

    
    /*Cuando el mouse entra en el control de la imágen*/
    private void jPanImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImgMouseEntered

        /*Si no a seleccionado un prodcuto válido entonces regresa*/
        if(jTADescrip.getText().compareTo("")==0)
            return;

        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces regresa*/
        if(sCarp.compareTo("")==0)
            return;

        /*Si la carpeta de las imágenes no existe entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de los usuarios no existe entonces creala*/
        sCarp                    += "\\Productos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la aplicación no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de El usuario en específico no existe entonces creala*/
        sCarp                    += "\\" + jTProd.getText();
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if(new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista de directorios*/
                String sArc [] = new File(sCarp).list();

                /*Muestra la forma para ver la imágen en otra vista*/
                v = new ImgVis(sCarp + "\\" + sArc[0]);            
                v.setVisible(true);
            }
        }                    

    }//GEN-LAST:event_jPanImgMouseEntered

    
    /*Cuando el mouse sale del control de la imágen*/
    private void jPanImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImgMouseExited

        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
    }//GEN-LAST:event_jPanImgMouseExited

    
    /*Cuando se presiona una tecla en el control de la imágen*/
    private void jPanImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanImgKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jPanImgKeyPressed

    
    /*Cuando se presiona el botón de ver grande la imágen*/
    private void jBVeGranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVeGranActionPerformed

        /*Si a ingresado un producto entonces*/
        if(jTADescrip.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un producto válido.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el control y regresa*/
            jTProd.grabFocus();
            return;
        }

        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existen entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de los prods no existen entonces creala*/
        sCarp                    += "\\Productos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existen entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta del prodcuto en específico no existe entonces creala*/
        sCarp                    += "\\" + jTProd.getText();
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen para el producto: " + jTProd.getText() + " en \"" + sCarp + "\".", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }
                            
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista del archivo*/
                String sArch [] = new File(sCarp).list();

                /*Abre el archivo*/
                try
                {
                    Desktop.getDesktop().open(new File(sCarp + "\\" + sArch[0]));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                               
                }
            }
        }                    
        
    }//GEN-LAST:event_jBVeGranActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver imágen en grande*/
    private void jBVeGranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVeGranKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBVeGranKeyPressed


    /*Cuando el mouse entra en el botón específico*/
    private void jBProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProd.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProdMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGenMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGen.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGenMouseEntered


    /*Cuando el mouse entra en el botón específico*/
    private void jBPrecMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPrecMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBPrec.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBPrecMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBusc2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusc2MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc2.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBusc2MouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBusc3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusc3MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc3.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBusc3MouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMosTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMosT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMosTMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBLimMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBLim.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBLimMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCargImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargImgMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCargImg.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCargImgMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelImgMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDelImg.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelImgMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVeGranMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGranMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVeGran.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVeGranMouseEntered

            
        
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCargFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargFMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCargF.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCargFMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelFMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDelF.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelFMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBFTecMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFTecMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBFTec.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBFTecMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(colOri);
        
    }//GEN-LAST:event_jBProdMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGenMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGen.setBackground(colOri);
        
    }//GEN-LAST:event_jBGenMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBPrecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPrecMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBPrec.setBackground(colOri);
        
    }//GEN-LAST:event_jBPrecMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBusc2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusc2MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc2.setBackground(colOri);
        
    }//GEN-LAST:event_jBBusc2MouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBusc3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusc3MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc3.setBackground(colOri);
        
    }//GEN-LAST:event_jBBusc3MouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMosTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMosT.setBackground(colOri);
        
    }//GEN-LAST:event_jBMosTMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBLimMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBLim.setBackground(colOri);
        
    }//GEN-LAST:event_jBLimMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCargImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargImgMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCargImg.setBackground(colOri);
        
    }//GEN-LAST:event_jBCargImgMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelImgMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDelImg.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelImgMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVeGranMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGranMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVeGran.setBackground(colOri);
        
    }//GEN-LAST:event_jBVeGranMouseExited

    
    
    
    
    
    

    /*Cuando el mouse sale del botón específico*/
    private void jBCargFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargFMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCargF.setBackground(colOri);
        
    }//GEN-LAST:event_jBCargFMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelFMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDelF.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelFMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBFTecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFTecMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBFTec.setBackground(colOri);
        
    }//GEN-LAST:event_jBFTecMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTExistFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExistFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTExist.setCaretPosition(0);
        
    }//GEN-LAST:event_jTExistFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTLinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLinFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTLin.setCaretPosition(0);
        
    }//GEN-LAST:event_jTLinFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTMarcFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMarcFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTMarc.setCaretPosition(0);
        
    }//GEN-LAST:event_jTMarcFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTFabFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFabFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTFab.setCaretPosition(0);
        
    }//GEN-LAST:event_jTFabFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTMedsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMedsFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTMeds.setCaretPosition(0);
        
    }//GEN-LAST:event_jTMedsFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPesoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPesoFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPeso.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPesoFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCol.setCaretPosition(0);
        
    }//GEN-LAST:event_jTColFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUnidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUnidFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUnid.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUnidFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTClasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTClas.setCaretPosition(0);
        
    }//GEN-LAST:event_jTClasFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTImpueValFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpueValFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTImpueVal.setCaretPosition(0);
        
    }//GEN-LAST:event_jTImpueValFocusLost

    
    
    
    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTAnaqFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAnaqFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAnaq.setCaretPosition(0);
        
    }//GEN-LAST:event_jTAnaqFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTLugFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLugFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTLug.setCaretPosition(0);
        
    }//GEN-LAST:event_jTLugFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUbiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUbiFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUbi.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUbiFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUltEstacModFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUltEstacModFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUltEstacMod.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUltEstacModFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCreadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCreadFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCread.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCreadFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUltComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUltComFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUltCom.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUltComFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTProv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTProvFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTFacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFacFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTFac.setCaretPosition(0);
        
    }//GEN-LAST:event_jTFacFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTEstacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstacFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTEstac.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEstacFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTProvMasComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvMasComFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTProv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTProvMasComFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCant.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCantFocusLost

    
    /*Cuando se presiona una tecla en el botón de asociar mas marcas*/
    private void jBMasMarcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMasMarcKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMasMarcKeyPressed

    
    /*Cuando se presiona una tecla en el botón de asociar mas Modelos*/
    private void jBMasModKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMasModKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMasModKeyPressed

    
    /*Cuando se presiona el botón de mas asociaciones de Modelos*/
    private void jBMasModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMasModActionPerformed
        
        /*Muestra la forma para asociar más Modelos al producto*/
        ProdModel p = new ProdModel(prods);
        p.setVisible(true);
        
    }//GEN-LAST:event_jBMasModActionPerformed

    
    /*Cuando el mouse entra en el botón de asociar mas marcas*/
    private void jBMasMarcMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMasMarcMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMasMarc.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMasMarcMouseEntered

    
    /*Cuando el mouse entra en el botón de mas asociaciones de Modelos*/
    private void jBMasModMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMasModMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMasMod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMasModMouseEntered

    
    /*Cuando el mouse sale del botón de mas asociaciones de marcas*/
    private void jBMasMarcMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMasMarcMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMasMarc.setBackground(colOri);
        
    }//GEN-LAST:event_jBMasMarcMouseExited

    
    /*Cuando el mouse sale del botón de más asociaciones de Modelos*/
    private void jBMasModMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMasModMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMasMod.setBackground(colOri);
        
    }//GEN-LAST:event_jBMasModMouseExited

    
    /*Cuando sucede una acción en el combo de los Modelos*/
    private void jComModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComModActionPerformed
        
        /*Si no hay selección regresa*/
        if(jComMod.getSelectedItem()==null)
            return;        
                    
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ       = "";        
        
        /*Obtén la descripción del Modelo de la base de datos*/        
        try
        {
            sQ  = "SELECT descrip FROM model WHERE cod = '" + jComMod.getSelectedItem().toString() + "'";            
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el campo y pon el cursor al principio del control*/
                jTMod.setText(rs.getString("descrip"));                                            
                jTMod.setCaretPosition(0);
            }
            else
                jTMod.setText("");                                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);            
            return;                        
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);                    
        
    }//GEN-LAST:event_jComModActionPerformed

    
    /*Cuando se presiona una tecla en el combo de los Modelos*/
    private void jComModKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComModKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComModKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo del Modelo*/
    private void jTModFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTModFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTMod.setCaretPosition(0);
        
    }//GEN-LAST:event_jTModFocusLost

    
    /*Cuando se presiona una tecla en campo del Modelo*/
    private void jTModKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTModKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTModKeyPressed

    
    /*Cuando se presiona una tecla en el control de la clasificación*/
    private void jTClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClasKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTClasKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la clasificación extra*/
    private void jTClasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTClas.setSelectionStart(0);jTClas.setSelectionEnd(jTClas.getText().length());        
        
    }//GEN-LAST:event_jTClasFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del Modelo*/
    private void jTModFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTModFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMod.setSelectionStart(0);jTMod.setSelectionEnd(jTMod.getText().length());        
        
    }//GEN-LAST:event_jTModFocusGained

    
    /*Cuando se presiona el botón de asociar más marcas al producto*/
    private void jBMasMarcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMasMarcActionPerformed
        
        /*Muestra la forma para asociar más Modelos al producto*/
        ProdMarc p = new ProdMarc(prods);
        p.setVisible(true);
        
    }//GEN-LAST:event_jBMasMarcActionPerformed

    
    /*Cuando se presiona una tecla en el botón de compatibilidades*/
    private void jBCompaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCompaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCompaKeyPressed

    
    /*Cuando se presiona el botón de las compatibilidades*/
    private void jBCompaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCompaActionPerformed
        
        /*Muestra la forma para asociar las compatbilidades al producto*/
        ProdCompa p = new ProdCompa(prods);
        p.setVisible(true);
        
    }//GEN-LAST:event_jBCompaActionPerformed

    
    /*Cuando el mouse entra en el botón de las compatibilidades*/
    private void jBCompaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCompaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCompa.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCompaMouseEntered

    
    /*Cuando el mouse sale del botón de compatibilidades*/
    private void jBCompaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCompaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCompa.setBackground(colOri);
        
    }//GEN-LAST:event_jBCompaMouseExited

    
    
    
    
    
    /*Cuando se presiona una tecla en el botón de números de partidas*/
    private void jBPartKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPartKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBPartKeyPressed

    
    /*Cuando se presiona el botón de números de parte*/
    private void jBPartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPartActionPerformed
        
        /*Muestra la forma para asociar los números de parte*/
        ProdPart t = new ProdPart(prods);
        t.setVisible(true);
        
    }//GEN-LAST:event_jBPartActionPerformed
    
    
    /*Cuando se presiona el botón de ver descripción en grande*/
    private void jBGranDescripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGranDescripActionPerformed

        /*Muestar la forma para ver la descripción en grande*/
        ptovta.DescripGran b = new ptovta.DescripGran(null, jTADescrip);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBGranDescripActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver descripción en grande*/
    private void jBGranDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGranDescripKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBGranDescripKeyPressed

    
    /*Cuando se presiona una tecla en el combo de los tipos*/
    private void jComTipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComTipKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComTipKeyPressed

    
    /*Cuando sucede una acción en el combo de tipos*/
    private void jComTipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComTipActionPerformed
        
        /*Si no hay selección regresa*/
        if(jComTip.getSelectedItem()==null)
            return;        
                    
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ       = "";        
        
        /*Obtén la descripción de la línea de la base de datos*/        
        try
        {
            sQ  = "SELECT * FROM tips WHERE cod = '" + jComTip.getSelectedItem().toString() + "'";            
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el campo y pon el cursor al principio del control*/
                jTTip.setText(rs.getString("descrip"));                                            
                jTTip.setCaretPosition(0);
            }
            else
                jTTip.setText("");                                            
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

        /*Coloca el cursor hasta el principio en el campo de la descripción*/
        jTTip.setCaretPosition(0);
        
    }//GEN-LAST:event_jComTipActionPerformed

    
    /*Cuando se presiona una tecla en el botón de agregar mas series*/
    private void jBMasSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMasSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMasSerKeyPressed

    
    /*Cuando el mouse entra en el botón de mas series*/
    private void jBMasSerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMasSerMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMasSer.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMasSerMouseEntered

    
    /*Cuando el mouse sale del botón de agregar más series*/
    private void jBMasSerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMasSerMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMasSer.setBackground(colOri);
        
    }//GEN-LAST:event_jBMasSerMouseExited

    
    /*Cuando se presiona el botón de más series*/
    private void jBMasSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMasSerActionPerformed
        
        /*Muestra la forma para asociar las series al producto*/
        ProdSer t = new ProdSer(prods);
        t.setVisible(true);
        
    }//GEN-LAST:event_jBMasSerActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de la garantía*/
    private void jTGaraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTGaraFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTGara.setSelectionStart(0);jTGara.setSelectionEnd(jTGara.getText().length());
        
    }//GEN-LAST:event_jTGaraFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la garantía*/
    private void jTGaraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTGaraFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTGara.setCaretPosition(0);
                    
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ          = "";
        
        /*Comprueba si el código existe*/
        boolean bSiExist   = false;
        try
        {
            sQ = "SELECT gara FROM garan WHERE gara = '" + jTGara.getText() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSiExist   = true;                
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
        
        /*Si el código no existe entonces resetea el campo*/
        if(!bSiExist)
            jTGara.setText("");            

    }//GEN-LAST:event_jTGaraFocusLost

    
    /*Cuando se presiona una tecla en el campo de la garantía*/
    private void jTGaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTGaraKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTGara.getText(), 37, jTGara, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTGaraKeyPressed

    private void jTGaraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTGaraKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTGaraKeyTyped

    
    /*Cuando el mouse entra en el botón de la garantía*/
    private void jBGaraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGaraMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGara.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGaraMouseEntered

    
    /*Cuando el mouse sale del botón de garantía*/
    private void jBGaraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGaraMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGara.setBackground(colOri);
        
    }//GEN-LAST:event_jBGaraMouseExited

    
    /*Cuando se presona el botón de búscar garantía*/
    private void jBGaraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGaraActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTGara.getText(), 37, jTGara, null, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBGaraActionPerformed

    
    /*Cuando se presiona una tecla en el botón de la garantía*/
    private void jBGaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGaraKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGaraKeyPressed

    
    /*Cuando se presiona una tecla en el botón de consecutivos del producto*/
    private void jBConsecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBConsecKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBConsecKeyPressed

    
    /*Cuando el mouse entra en el botón de consecutivos del producto*/
    private void jBConsecMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConsecMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBConsec.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBConsecMouseEntered

    
    /*Cuando el mouse sale del botón de consecutivos del producto*/
    private void jBConsecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConsecMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBConsec.setBackground(colOri);
        
    }//GEN-LAST:event_jBConsecMouseExited

    
    /*Cuando se presiona el botón de consecutivos del código del producto*/
    private void jBConsecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBConsecActionPerformed
        
        /*Si no hay un código en el campo entonces*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa primero un código de producto.", "Consecutivo de Código", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Pon el foco del teclado en el control y regresa*/
            jTProd.grabFocus();
            return;
        }
        
        /*Arreglo de carácteres para insertar dentro solo el prefijo del código del producto*/
        char chCod[]    = new char[jTProd.getText().trim().length() + 1];
        
        /*Contador del arreglo de carácteres*/
        int iCont       = 0;
        
        /*Obtiene el código del producto*/
        String sProd    = jTProd.getText().trim();
        
        /*Recorre toda la cadena del código del producto al reves*/
        for(int x = sProd.length() - 1; x >= 0; x--)
        {
            /*Si el carácter no es un dígito entonces sale del búcle*/
            if(!Character.isDigit(sProd.charAt(x)))            
                break;
            
            /*Guarda el carácter en el arreglo*/
            chCod[iCont]= sProd.charAt(x);

            /*Aumenta en uno el contador del arreglo*/
            ++iCont;                
        }   
        
        /*Crea el string de los números finales que tiene el código*/
        String sNum     = new String(new StringBuilder(new String (chCod)).reverse());
        
        /*Remplaza en el código del producto los números por cadena vacia*/
        sProd           = sProd.replace(sNum.trim(), "");                
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Resetea el contador a 1 para ir contando hacia arriba*/
        iCont       = 1;

        /*Búcle infinito hasta que se encuentre el consecutivo*/
        while(true)
        {
            /*Crea el producto con el consecutivo*/
            String sProdC       = sProd + iCont;        
            
            //Comprueba si existe el producto con ese consecutivo en la base de datos                   
            int iRes        = Star.iExistProd(con, sProdC);

            //Si hubo error entonces regresa
            if(iRes==-1)
                return;

            //Si el producto no existe entonces
            if(iRes==0)
            {
                /*El producto será igual al obtenido*/
                sProd   = sProdC;
                break;
            }
                        
            /*Aumenta en uno el contador*/
            ++iCont;
            
        }/*Fin de while(true)*/
                        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
            
        /*Coloca el producto en el control*/
        jTProd.setText(sProd);
        
        //cambio alan
        jTProd.grabFocus();
        
    }//GEN-LAST:event_jBConsecActionPerformed

    
    /*Cuando el mouse sale del botón de seleccionar todo en la tabla*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse entra en el botón de obtener el último consecutivo a partir del actual del producto*/
    private void jBConsecUMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConsecUMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBConsecU.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBConsecUMouseEntered

    
    /*Cuando el mouse sale del botón de obtener último consecutivo del producto a partir del actual*/
    private void jBConsecUMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConsecUMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBConsecU.setBackground(colOri);
        
    }//GEN-LAST:event_jBConsecUMouseExited

    
    /*Cuando se presiona el botón de generar código de producto a partir del consecutivo actual*/
    private void jBConsecUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBConsecUActionPerformed
        
        /*Si no hay un código en el campo entonces*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa primero un código de producto.", "Consecutivo de Código", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Pon el foco del teclado en el control y regresa*/
            jTProd.grabFocus();
            return;
        }
        
        /*Arreglo de carácteres para insertar dentro solo el prefijo del código del producto*/
        char chCod[]    = new char[jTProd.getText().trim().length() + 1];
        
        /*Contador del arreglo de carácteres*/
        long iCont      = 0;        
        
        /*Obtiene el código del producto*/
        String sProd    = jTProd.getText().trim();
        
        /*Recorre toda la cadena del código del producto al reves*/
        for(int x = sProd.length() - 1; x >= 0; x--)
        {
            /*Si el carácter no es un dígito entonces sale del búcle*/
            if(!Character.isDigit(sProd.charAt(x)))            
                break;
            
            /*Guarda el carácter en el arreglo*/
            chCod[(int)iCont]= sProd.charAt(x);

            /*Aumenta en uno el contador del arreglo*/
            ++iCont;  
            
        }   
        
        //Iguala al contador
        long iCont2     = iCont;
                
        /*Crea el string de los números finales que tiene el código*/
        String sNum     = new String(new StringBuilder(new String (chCod)).reverse());
        
        /*Remplaza en el código del producto los números por cadena vacia*/
        sProd           = sProd.replace(sNum.trim(), "");  
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Quita todos los espacios en el string de los números*/
        sNum        = sNum.trim();
        
        /*Si el número es cadena vacia entonces que sea igual a 1, caso contrario sumale 1*/
        if(sNum.compareTo("")==0)
            sNum    = "1";
        else
            sNum    = Long.toString(Long.parseLong(sNum) + 1);
        
        /*Resetea el contador a al número actual para ir contando hacia arriba*/
        iCont       = Long.parseLong(sNum);
        
        /*Búcle infinito hasta que se encuentre el consecutivo*/
        while(true)
        {            
            //Se toma el primer cosecutivo
            String sCons =  Long.toString(iCont);
            
            //Si en la escritura tenia ceros antes de el primer numero y lo antecedia una letra entonces ponselos
            for(int x = 0;x < iCont2-Long.toString(iCont).length(); x++)
                sCons = "0" + sCons;
            
            /*Crea el producto con el consecutivo*/
            String sProdC=sProd + sCons;        
            
            //Comprueba si existe el producto con ese consecutivo en la base de datos                    
            int iRes        = Star.iExistProd(con, sProdC);

            //Si hubo error entonces regresa
            if(iRes==-1)
                return;
            
            //Si el producto no existe entonces
            if(iRes==0)
            {
                /*El producto será igual al obtenido*/
                sProd   = sProdC;
                break;  
            }
                        
            /*Aumenta en uno el contador*/
            ++iCont;
            
        }/*Fin de while(true)*/
                            
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
                
        /*Coloca el producto en el control*/
        jTProd.setText(sProd);
        
        //Pon el foco en el producto
        jTProd.grabFocus();
        
    }//GEN-LAST:event_jBConsecUActionPerformed

    
    /*Cuando se presiona una tecla en el botón de obtener consecutivo del producto desde el actual*/
    private void jBConsecUKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBConsecUKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBConsecUKeyPressed

    
    /*Cuando la forma se a activado*/
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        /*Inicia la referencia a si mismo*/
        prods   = this;

        /*Carga todos los productos en la tabla*/
        (new Thread()
        {
            @Override
            public void run()
            {
                vCargProdsTab();
            }
            
        }).start();
        
    }//GEN-LAST:event_formWindowActivated

    
    /*Cuando se gana el foco del teclado en el campo de la jerarquía*/
    private void jTJeraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTJera.setSelectionStart(0);jTJera.setSelectionEnd(jTJera.getText().length());

    }//GEN-LAST:event_jTJeraFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la jerarquía*/
    private void jTJeraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTJera.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTJera.getText().compareTo("")!=0)
            jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTJeraFocusLost

    
    /*Cuando se presiona una tecla en el campo de la jerarquía*/
    private void jTJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTJeraKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar jeraquía*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBJera.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTJeraKeyPressed

    
    /*Cuando el mouse entra en el botón de jerarquía*/
    private void jBJeraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseEntered

        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(Star.colBot);

    }//GEN-LAST:event_jBJeraMouseEntered

    
    /*Cuando el mouse sale del botón de jerarquía*/
    private void jBJeraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseExited

        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(colOri);

    }//GEN-LAST:event_jBJeraMouseExited

    
    /*Cuando se presiona el botón de jerarquía*/
    private void jBJeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBJeraActionPerformed

        /*Muestra la forma para ver las jerárquias*/
        cats.ClasJeraVis w = new cats.ClasJeraVis(jTJera.getText().trim(), jTJera, "prod");
        w.setVisible(true);

    }//GEN-LAST:event_jBJeraActionPerformed

    
    /*Cuando se presiona una tecla ne el botón de jerarquía*/
    private void jBJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBJeraKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBJeraKeyPressed

    
    /*Cuando se presiona una tecla en el radio de PEPS*/
    private void jRPEPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRPEPSKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jRPEPSKeyPressed

    
    /*Cuando se presiona una tecla en el radio de UEPS*/
    private void jRUEPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRUEPSKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jRUEPSKeyPressed

    
    /*Cuando se presiona una tecla en el radio de UEPS*/
    private void jRUltCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRUltCostKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jRUltCostKeyPressed

    
    /*Cuando se presiona una tecla en el radio de promedio*/
    private void jRPromKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRPromKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jRPromKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del ultimo usuario de compra*/
    private void jTUltEstacModFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUltEstacModFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUltEstacMod.setSelectionStart(0);jTUltEstacMod.setSelectionEnd(jTUltEstacMod.getText().length());        
        
    }//GEN-LAST:event_jTUltEstacModFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de creado*/
    private void jTCreadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCreadFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCread.setSelectionStart(0);jTCread.setSelectionEnd(jTCread.getText().length());        
        
    }//GEN-LAST:event_jTCreadFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del proveedor mas comprado*/
    private void jTProvMasComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvMasComFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTProvMasCom.setSelectionStart(0);jTProvMasCom.setSelectionEnd(jTProvMasCom.getText().length());        
        
    }//GEN-LAST:event_jTProvMasComFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la cantidad total de compra*/
    private void jTCantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCant.setSelectionStart(0);jTCant.setSelectionEnd(jTCant.getText().length());        
        
    }//GEN-LAST:event_jTCantFocusGained

    
    /*Cuando se presiona una tecla en el botón de existencias por almacén*/
    private void jBExisAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBExisAlmaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBExisAlmaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la existencia*/
    private void jTExistFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExistFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTExist.setSelectionStart(0);jTExist.setSelectionEnd(jTExist.getText().length());        
        
    }//GEN-LAST:event_jTExistFocusGained

    
    /*Cuando el mouse entra en el botón de existencias*/
    private void jBExisAlmaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBExisAlmaMouseEntered
 
        /*Cambia el color del fondo del botón*/
        jBExisAlma.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBExisAlmaMouseEntered

    
    /*Cuando el mouse sale del botón de existencias*/
    private void jBExisAlmaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBExisAlmaMouseExited
 
        /*Cambia el color del fondo del botón*/
        jBExisAlma.setBackground(colOri);
        
    }//GEN-LAST:event_jBExisAlmaMouseExited

    
    /*Cuando se presiona el botón de existencias por almacén*/
    private void jBExisAlmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExisAlmaActionPerformed
        
        /*Si no a ingresado un producto entonces*/              
        if(jTProd.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un producto.", "Existencias por almacén", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el campo del producto y regresa*/
            jTProd.grabFocus();
            return;
        }
        
        /*Muestra la forma para ver las existencias por almacén del producto*/
        ptovta.ProdExisAlm m = new ptovta.ProdExisAlm(jTProd.getText().trim(),null);
        m.setVisible(true);
        
    }//GEN-LAST:event_jBExisAlmaActionPerformed

    
    /*Cuando se presiona una tecla en el campo del pedimento*/
    private void jCPedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCPedKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jCPedKeyPressed

    
    //Cuando se pierde el foco del teclado en el combo de unidades
    private void jComUniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComUniFocusLost
    
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComUni.getSelectedItem().toString().compareTo("")!=0)
            jComUni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComUniFocusLost

    
    //Cuando se presiona una tecla en el check de no serie
    private void jCNoSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCNoSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jCNoSerKeyPressed

    
    //Entra en el boton de compatibilidad de marca y modelo
    private void jBCompMarcModMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCompMarcModMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCompMarcMod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCompMarcModMouseEntered

    
    //Sale en el boton de compatibilidad de marca y modelo
    private void jBCompMarcModMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCompMarcModMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCompMarcMod.setBackground(colOri);
        
    }//GEN-LAST:event_jBCompMarcModMouseExited

    
    //Cuando se preciona el boton de compatibilidad de marca y modelo
    private void jBCompMarcModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCompMarcModActionPerformed
        
        /*Muestra la forma para ver las tallas y colores*/
        //cats.ProdMarcMod t = new cats.ProdMarcMod(prods);
        //t.setVisible(true);
        
    }//GEN-LAST:event_jBCompMarcModActionPerformed

    
    //Se presiona una tecla en el boton de compatibilidad de marca y modelo
    private void jBCompMarcModKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCompMarcModKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCompMarcModKeyPressed
  
    
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
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        /*Si se presiona F3 presiona el boton de buscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();               
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();        
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona F6 presiona el botón de limpiar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F6)
            jBLim.doClick(); 
        /*Si se presiona F10 presiona el botón de generar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F10)
            jBGen.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBBusc2;
    private javax.swing.JButton jBBusc3;
    private javax.swing.JButton jBCargF;
    private javax.swing.JButton jBCargImg;
    private javax.swing.JButton jBCompMarcMod;
    private javax.swing.JButton jBCompa;
    private javax.swing.JButton jBComps;
    private javax.swing.JButton jBConsec;
    private javax.swing.JButton jBConsecU;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBDelF;
    private javax.swing.JButton jBDelImg;
    private javax.swing.JButton jBExisAlma;
    private javax.swing.JButton jBFTec;
    private javax.swing.JButton jBGara;
    private javax.swing.JButton jBGen;
    private javax.swing.JButton jBGranDescrip;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBJera;
    private javax.swing.JButton jBLim;
    private javax.swing.JButton jBMasMarc;
    private javax.swing.JButton jBMasMod;
    private javax.swing.JButton jBMasSer;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBPart;
    private javax.swing.JButton jBPrec;
    private javax.swing.JButton jBProd;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBVeGran;
    private javax.swing.JCheckBox jCBajCost;
    private javax.swing.JCheckBox jCComp;
    private javax.swing.JCheckBox jCEsParaVent;
    private javax.swing.JCheckBox jCInvent;
    private javax.swing.JCheckBox jCNoSer;
    private javax.swing.JCheckBox jCNoSolMaxMin;
    private javax.swing.JCheckBox jCPed;
    private javax.swing.JCheckBox jCServ;
    private javax.swing.JComboBox jComAna;
    private javax.swing.JComboBox jComCol;
    private javax.swing.JComboBox jComExt;
    private javax.swing.JComboBox jComFab;
    private javax.swing.JComboBox jComImp;
    private javax.swing.JComboBox jComLin;
    private javax.swing.JComboBox jComLug;
    private javax.swing.JComboBox jComMarc;
    private javax.swing.JComboBox jComMeds;
    private javax.swing.JComboBox jComMod;
    private javax.swing.JComboBox jComPeso;
    private javax.swing.JComboBox jComTip;
    private javax.swing.JComboBox jComUAd;
    private javax.swing.JComboBox jComUni;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLFCreac;
    private javax.swing.JLabel jLFMod;
    private javax.swing.JLabel jLImg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPInfo;
    private javax.swing.JPanel jPanImg;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRPEPS;
    private javax.swing.JRadioButton jRProm;
    private javax.swing.JRadioButton jRUEPS;
    private javax.swing.JRadioButton jRUltCost;
    private javax.swing.JScrollPane jSImg;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTADescrip;
    private javax.swing.JTextArea jTAInfor;
    private javax.swing.JTextField jTAlmaG;
    private javax.swing.JTextField jTAnaq;
    private javax.swing.JTextField jTAnot;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTCant;
    private javax.swing.JTextField jTClas;
    private javax.swing.JTextField jTCodOp1;
    private javax.swing.JTextField jTCodOp2;
    private javax.swing.JTextField jTCodProv;
    private javax.swing.JTextField jTCodProv1;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTCread;
    private javax.swing.JTextField jTEstac;
    private javax.swing.JTextField jTExist;
    private javax.swing.JTextField jTFab;
    private javax.swing.JTextField jTFac;
    private javax.swing.JTextField jTGara;
    private javax.swing.JTextField jTImpueVal;
    private javax.swing.JTextField jTJera;
    private javax.swing.JTextField jTLin;
    private javax.swing.JTextField jTLug;
    private javax.swing.JTextField jTMarc;
    private javax.swing.JTextField jTMax;
    private javax.swing.JTextField jTMedMan;
    private javax.swing.JTextField jTMeds;
    private javax.swing.JTextField jTMin;
    private javax.swing.JTextField jTMod;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTPesMan;
    private javax.swing.JTextField jTPeso;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTProv;
    private javax.swing.JTextField jTProvMasCom;
    private javax.swing.JTextField jTTip;
    private javax.swing.JTextField jTUbi;
    private javax.swing.JTextField jTUltCom;
    private javax.swing.JTextField jTUltEstacMod;
    private javax.swing.JTextField jTUnid;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevoCliente extends javax.swing.JFrame */
