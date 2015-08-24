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
import java.io.IOException;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;




/*Clase para recibir una órden de compra*/
public class RecibOrd extends javax.swing.JFrame 
{        
    /*Contiene la dirección de la forma para ver la ruta de los ficheros en otra vista*/
    private FilVis          filVi;
    
    /*Contiene la ruta de donde se leeran los archivos para guardarlos*/
    private String          sRuts[][];
    
    /*Contador para saber donde guardarel próximo fichero de la compra*/
    private int             iXGlo;
    
    /*Declara variables de instancia*/
    private int             iContFi;
    private String          sCodOrdGlo;
    private JTable          jTabComp;
    private int             rowGlo;

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
    private String          sRecibsOri;
    private String          sCantReOri;
    private String          sTallOri;
    private String          sColOri;
    private String          sLotOri;
    private String          sPedimenOri;
    private String          sFCaduOri;
    private String          sSerProdOri;
    private String          sComenOri;
    private String          sGaraOri;
    
    
    
    /*Consructor con argumento*/
    public RecibOrd(String sCod, JTable jTableCom, int ro) 
    {
        /*Inicializa los componentes gráfcos*/
        initComponents();
        

        /*Esconde la columna del total de impuesto*/
        jTab.getColumnModel().getColumn(23).setMinWidth(0);
        jTab.getColumnModel().getColumn(23).setMaxWidth(0);
        
        /*Listener para el combobox de monedas*/
        jComMon.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                //Obtiene todas las monedas y cargalas en el combo
                if(Star.iCargMonCom(con, jComMon)==-1)
                    return;
                        
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
        
        /*Esconde la columna del id*/
        jTab.getColumnModel().getColumn(14).setMinWidth(0);
        jTab.getColumnModel().getColumn(14).setMaxWidth(0);
        
        /*Para que la tabla tenga scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Inicia el contador de archivos del arreglo en 0 y el arreglo en nulo*/        
        iXGlo   = 0;
        sRuts   = null;
        
        /*Selecciona la fecha del día de hoy para la fecha de compra*/
        Date f = new Date();
        jDFech.setDate(f);  
        
        /*Establece el campo de fecha para que solo se pueda modificar con el botón*/
        jDFech.getDateEditor().setEnabled(false);
        
        /*El código de la serie del proveedor no será visible*/
        jTSerProv.setVisible(false);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Obtiene la referencia de la tabla de compras del otro formulario*/
        jTabComp           = jTableCom;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Obtiene la fila que el usuario selecciono para abrir este formulario*/
        rowGlo               = ro;
        
        /*Obtiene el código de la órden del otro formulario*/
        sCodOrdGlo          = sCod;
                
        /*Inicia el contador de filas de las partidas*/
        iContFi             = 1;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Recepción de Orden de Compra, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el tamaño de la última columna de la tabla de partidas*/
        jTab.getColumnModel().getColumn(1).setPreferredWidth(160);        
        jTab.getColumnModel().getColumn(5).setPreferredWidth(400);        
        jTab.getColumnModel().getColumn(8).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(19).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(20).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(21).setPreferredWidth(150);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Pon el foco del en el checkbox de recibir todo*/
        jCRecibT.grabFocus();
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Trae todos los códigos de las monedas de la base de datos*/
        if(Star.iCargMonCom(con, jComMon)==-1)
            return;
        
        //Obtiene todas las formas de pago y cargalas en el combo
        if(Star.iCargFormPagCom(con, jComFormPag)==-1)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los datos de la órden de compra de la base de datos*/        
        try
        {                  
            sQ = "SELECT comprs.MON, comprs.SER, comprs.FENT, comprs.MOTIV, comprs.ESTADO, comprs.PROV,  comprs.NODOC, comprs.FDOC, comprs.SUBTOT, provs.NOM, comprs.IMPUE, comprs.TOT FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('',provs.SER,provs.PROV) = comprs.PROV WHERE comprs.CODCOMP = '" + sCodOrdGlo + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Selecciona la moneda*/
                jComMon.setSelectedItem(rs.getString("mon"));
                
                /*Coloca todos los valores obtenidos en sus campos correspondientes*/
                jTCodProv.setText       (rs.getString("comprs.PROV"));
                jTNomProv.setText       (rs.getString("provs.NOM"));
                jTNoDoc.setText         (rs.getString("comprs.NODOC"));
                jTFec.setText           (rs.getString("comprs.FDOC"));                
                jTEst.setText           (rs.getString("comprs.ESTADO"));
                jTObserv.setText        (rs.getString("comprs.MOTIV"));
                jTFEnt.setText          (rs.getString("comprs.FENT"));
                jTSerProv.setText       (rs.getString("comprs.SER"));
                jTCod.setText           (sCodOrdGlo);   
                
                /*Coloca en color azúl el campo de estado*/
                jTEst.setForeground(Color.BLUE);                                                                                                    
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                     
        }
        
        /*Obtiene todas las partidas de la órden de compra de la base de datos*/
        try
        {      
            //Declaracion de variables locales
            String sSubTot  ="0";
            String sDesc    ="0";
            String sImpu    ="0";
            
            //sQ ejecuta la consulta
            sQ = "SELECT * FROM partcomprs WHERE codcom = '" + sCodOrdGlo + "'";
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
               
                //Se pone la cantidad a resivir que hace falta
                String sFalResiv    = Double.toString(Double.parseDouble(rs.getString("cant"))-Double.parseDouble(rs.getString("recib")));
                
                /*Obtiene el importe*/
                String sImp         = Double.toString(Double.parseDouble(rs.getString("cost"))*Double.parseDouble(rs.getString("cant")));
                
                //se saca el descuento
                double dDesc        = Double.parseDouble(sImp)*Double.parseDouble(rs.getString("descu"))/100;
                
                //se saca el impuesto adicional
                dDesc               = dDesc+((Double.parseDouble(sImp)-dDesc)*Double.parseDouble(rs.getString("descad"))/100);
                
                //Obtiene el valor del impuesto
                String sValImp      = Star.sGetValImp(rs.getString("codimpue"));
                    
                //se carga la cantidad de impuesto al importe con el descuento
                String  sImpue      = Double.toString((Double.parseDouble(sImp)-dDesc)*Double.parseDouble(sValImp)/100);
                
                //Se obtienen los totales
                sSubTot             = Double.toString(Double.parseDouble(sSubTot)+Double.parseDouble(sImp));
                sDesc               = Double.toString(Double.parseDouble(sDesc)+dDesc);
                sImpu               = Double.toString(Double.parseDouble(sImpu)+Double.parseDouble(sImpue));
                
                /*Dale formato de moneda al importe*/                
                dCant               = Double.parseDouble(sImp);                
                n                   = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sImp                = n.format(dCant);
                
                /*Agrega todos los datos obtenidos en la tabla de partidas*/
                DefaultTableModel tm= (DefaultTableModel)jTab.getModel();                                                
                Object nu[]         = {iContFi, rs.getString("prod"), rs.getString("cant"), rs.getString("unid"), rs.getString("alma"), rs.getString("descrip"), sCost, rs.getString("descu"), rs.getString("descad"), rs.getString("codimpue"), rs.getString("mon"), sImp, rs.getString("recib"), sFalResiv.trim(), rs.getString("id_id"), rs.getString("tall"), rs.getString("colo"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("flotvenc"), rs.getString("serprod"), rs.getString("comenser"), rs.getString("garan"),sImpue};        
                tm.addRow(nu);
                
                /*Aumenta en uno el contador de filas*/
                ++iContFi;
                
            }/*Fin de while(rs.next())*/
            
            // ya teniendo los datos entonces
            //se saca el total
            String sTot                =Double.toString(Double.parseDouble(sSubTot)-Double.parseDouble(sDesc)+Double.parseDouble(sImpu));
            
            /*Dale formato de moneda al subtotal*/                
            double dCant               = Double.parseDouble(sSubTot);                
            NumberFormat n             = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            sSubTot                    = n.format(dCant);
            
            /*Dale formato de moneda al descuento*/                
            dCant                      = Double.parseDouble(sDesc);                
            n                          = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            sDesc                      = n.format(dCant);
            
            /*Dale formato de moneda al impuesto*/                
            dCant                      = Double.parseDouble(sImpu);                
            n                          = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            sImpu                      = n.format(dCant);
            
            /*Dale formato de moneda al impuesto*/                
            dCant                      = Double.parseDouble(sTot);                
            n                          = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            sTot                       = n.format(dCant);
            
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
        
        /*Agrega el elemento vacio en el combobox de series*/
        jComSer.addItem("");
        
        /*Obtiene todas las series actualizadas y cargalas en el combobox*/
        try
        {
            sQ = "SELECT ser FROM consecs WHERE tip = 'COMP'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            while(rs.next())
                jComSer.addItem(rs.getString("ser"));
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

        /*Listener para el combobox de series de compras*/
        jComSer.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {               
                //Abre la base de datos
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Borra los items en el combobox de series*/
                jComSer.removeAllItems();
                
                /*Agrega el elemento vacio*/
                jComSer.addItem("");
             
                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 

                /*Obtiene todas las series actualizadas y cargalas en el combobox*/
                try
                {
                    sQ = "SELECT ser FROM consecs WHERE tip = 'COMP'";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces agregalos al combobox*/
                    while(rs.next())
                        jComSer.addItem(rs.getString("ser"));
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;                                                                                                                         
                }  

                //Cierra la base de datos
                Star.iCierrBas(con);

            }/*Fin de public void popupMenuWillBecomeVisible(PopupMenuEvent pme) */

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para la tabla de partidas, para cuando se modifique la celda de cantidad a recibir*/
        PropertyChangeListener pr = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtén la fila seleccionada*/                
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
                        sRecibsOri      = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();
                        sCantReOri      = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();
                        sTallOri        = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();
                        sColOri         = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();
                        sLotOri         = jTab.getValueAt(jTab.getSelectedRow(), 17).toString();
                        sPedimenOri     = jTab.getValueAt(jTab.getSelectedRow(), 18).toString();
                        sFCaduOri       = jTab.getValueAt(jTab.getSelectedRow(), 19).toString();
                        sSerProdOri     = jTab.getValueAt(jTab.getSelectedRow(), 20).toString();
                        sComenOri       = jTab.getValueAt(jTab.getSelectedRow(), 21).toString();
                        sGaraOri        = jTab.getValueAt(jTab.getSelectedRow(), 22).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sProdOri,       jTab.getSelectedRow(), 1);                                                
                        jTab.setValueAt(sUnidOri,       jTab.getSelectedRow(), 3);
                        jTab.setValueAt(sAlmaOri,       jTab.getSelectedRow(), 4);
                        jTab.setValueAt(sDescripOri,    jTab.getSelectedRow(), 5);                                                
                        jTab.setValueAt(sDescOri,       jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sDescAdOri,     jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sImpueOri,      jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sMonOri,        jTab.getSelectedRow(), 10);                        
                        jTab.setValueAt(sImpoOri,       jTab.getSelectedRow(), 11);                        
                        jTab.setValueAt(sRecibsOri,     jTab.getSelectedRow(), 12);                                                
                        jTab.setValueAt(sTallOri,       jTab.getSelectedRow(), 15);
                        jTab.setValueAt(sColOri,        jTab.getSelectedRow(), 16);
                        jTab.setValueAt(sLotOri,        jTab.getSelectedRow(), 17);
                        jTab.setValueAt(sPedimenOri,    jTab.getSelectedRow(), 18);
                        jTab.setValueAt(sFCaduOri,      jTab.getSelectedRow(), 19);
                        jTab.setValueAt(sSerProdOri,    jTab.getSelectedRow(), 20);
                        jTab.setValueAt(sComenOri,      jTab.getSelectedRow(), 21);
                        jTab.setValueAt(sGaraOri,       jTab.getSelectedRow(), 22);
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                                                                    
                    
                    //Obtén la cantidad
                    String sCant                = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                    
                    
                    //Si la cantidad es cadena vacia que sea la original
                    if(sCant.compareTo("")==0)
                        sCant                   = sCantOri;
                    
                    //Si la cantidad no se puede parsear entonces
                    try
                    {
                        Double.parseDouble(sCant);
                    }
                    catch(NumberFormatException expnNumForm)
                    {
                        //La cantidad será la original
                        sCant                   = sCantOri;                        
                    }

                    //Si la cantidad es menor o igual a 0 entonces que sea la original
                    if(Double.parseDouble(sCant)<=0)
                        sCant                   = sCantOri;
                    
                    //Deja el valor absoluto
                    sCant                       = Double.toString(Math.abs(Double.parseDouble(sCant)));
                    
                    //Coloca el nuevo valor de cantidad en su lugar
                    jTab.getModel().setValueAt(sCant, jTab.getSelectedRow(), 2);
                    
                    /*Obtén el nuevo precio*/
                    String sPre                 = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();                    
                    
                    /*Si el precio es cadena vacia entonces será el costo original*/
                    if(sPre.compareTo("")==0)
                        sPre                    = sCostOri;
                    
                    /*Si el nuevo precio no se puede convertir a double entonces*/
                    try
                    {
                        Double.parseDouble(sPre.replace("$", "").replace(",", ""));
                    }
                    catch(NumberFormatException expnNumForm)
                    {
                        /*El precio será el original*/
                        sPre    = sCostOri;
                    }
                    
                    /*Si el precio es menor o igual a 0 entonces que sea el original*/
                    if(Double.parseDouble(sPre.replace("$", "").replace(",", ""))<=0)                    
                        sPre    = sCostOri;                    
                    
                    //Crea el nuevo importe
                    String sImpor       = Double.toString(Double.parseDouble(sPre.replace("$", "").replace(",", "")) * Double.parseDouble(sCant));
                    
                    //Obtiene el valor del impuesto
                    String sValImp      = Star.sGetValImp(jTab.getValueAt(jTab.getSelectedRow(), 9).toString());
                    
                    //se le sacan los descuentos
                    double dDesc        = Double.parseDouble(sImpor)*Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 7).toString())/100;
                    
                    //se saca el impuesto adicional
                    dDesc               = dDesc+((Double.parseDouble(sImpor)-dDesc)*Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 8).toString())/100);
                    
                    //Obtiene el impuesto total
                    sValImp=Double.toString((Double.parseDouble(sImpor)-dDesc)*Double.parseDouble(sValImp)/100);
                
                    /*Dale formato de moneda a los totales*/                    
                    NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                    double dCant    = Double.parseDouble(sImpor);                
                    sImpor          = n.format(dCant);
                    dCant           = Double.parseDouble(sPre.replace("$", "").replace(",", ""));                
                    sPre            = n.format(dCant);                    

                    //Coloca el nuevo precio en su lugar
                    jTab.getModel().setValueAt(sPre, jTab.getSelectedRow(), 6);
                    
                    //Coloca el nuevo importe en su lugar
                    jTab.getModel().setValueAt(sImpor, jTab.getSelectedRow(), 11);
                                        
                    //Coloca el nuevo total de impuesto en su lugar
                    jTab.getModel().setValueAt(sValImp, jTab.getSelectedRow(), 23);
                    
                    //Calcula los totales nuevamente
                    vCalcTot();
                    
                    /*Obtén la cantidad a recibir*/
                    String sCantDev             = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();                    
                                        
                    /*Si la cantidad no es númerica entonces*/
                    try
                    {
                        Double.parseDouble(sCantDev);
                    }
                    catch(NumberFormatException expnNumForm)
                    {                        
                        /*Colocar cadena vacia en la cantidad y regresa*/
                        jTab.getModel().setValueAt("", jTab.getSelectedRow(), 13);                                       
                        return;                                                
                    }          
                                     
                    /*Convierte a valor absoluto el número introducido, para quitar el negativo en caso de que lo tenga*/
                    sCantDev         = Integer.toString((int)Math.abs(Double.parseDouble(sCantDev)));                    
                    
                    /*Si la cantidad es igual a 0 entonces*/
                    if(Integer.parseInt(sCantDev)==0)
                    {
                        /*Coloca cadena vacia en la cantidad y regresa*/
                        jTab.getModel().setValueAt("", jTab.getSelectedRow(), 13);                                                                    
                        return;
                    }
                    
                    /*Coloca el valor absoluto en la cantidad*/
                    jTab.getModel().setValueAt(sCantDev, jTab.getSelectedRow(), 13);                    
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla de partidas*/
        jTab.addPropertyChangeListener(pr);        
        
    }/*Fin de public RecibOrd() */    
    
    
    //Calcula los totales nuevamente
    private void vCalcTot()
    {
        //Declara los totales
        String sSubTot  = "0";
        String sImpue   = "0";
        String sDesc    = "0";
        
        
        
        
        //Recorre toda la tabla de partidas
        for(int iX = 0; iX<jTab.getRowCount();iX++)
        {
            //Se le sacan los descuentos
            double dDesc        = Double.parseDouble(jTab.getValueAt(iX, 11).toString().replace("$", "").replace(",", ""))*Double.parseDouble(jTab.getValueAt(iX, 7).toString())/100;

            //Se saca el impuesto adicional
            dDesc               = dDesc+((Double.parseDouble(jTab.getValueAt(iX, 11).toString().replace("$", "").replace(",", ""))-dDesc)*Double.parseDouble(jTab.getValueAt(iX, 8).toString())/100);

            //Ve sumando el subtotal
            sSubTot             = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(jTab.getValueAt(iX, 11).toString().replace("$", "").replace(",", "")));
            
            //Ve sumando el impuesto
            sImpue              = Double.toString(Double.parseDouble(sImpue) + Double.parseDouble(jTab.getValueAt(iX, 23).toString().replace("$", "").replace(",", "")));
        
            //Se saca el descuento total
            sDesc               = Double.toString(Double.parseDouble(sDesc)+dDesc);
        }
        
        //Obtiene el total
        String sTot = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImpue)-Double.parseDouble(sDesc));
        
        //Dale formato de moneda a los totales                
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
        double dCant    = Double.parseDouble(sTot);                
        sTot            = n.format(dCant);
        dCant           = Double.parseDouble(sSubTot);                
        sSubTot         = n.format(dCant);
        dCant           = Double.parseDouble(sImpue);                
        sImpue          = n.format(dCant);
        dCant           = Double.parseDouble(sDesc);                
        sDesc           = n.format(dCant);
                
        //Coloca los totales en los campos
        jTSubTot.setText(sSubTot);
        jDesc.setText   (sDesc);
        jTImp.setText   (sImpue);
        jTTot.setText   (sTot);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jP2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTNoDoc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTCod = new javax.swing.JTextField();
        jTNomProv = new javax.swing.JTextField();
        jTCodProv = new javax.swing.JTextField();
        jTFec = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTEst = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTFEnt = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jDFech = new com.toedter.calendar.JDateChooser();
        jTSubTot = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTImp = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTTot = new javax.swing.JTextField();
        jBGuar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTObserv = new javax.swing.JTextField();
        jBTabG = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jCRecibT = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        jComSer = new javax.swing.JComboBox();
        jTSerProv = new javax.swing.JTextField();
        jCConta = new javax.swing.JCheckBox();
        jBCarg = new javax.swing.JButton();
        jBLimp = new javax.swing.JButton();
        jComMon = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jDesc = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jComFormPag = new javax.swing.JComboBox();
        jTComenSer = new javax.swing.JTextField();
        jTSerProd = new javax.swing.JTextField();

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
        jBSal.setNextFocusableComponent(jTObserv);
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
                "No.", "Cod. Producto", "Cantidad", "Unidad", "Almacén", "Descripción", "Costo", "Descuento", "Desc. Adicional", "Impuesto", "Moneda", "Importe", "Recibidos", "Cant. Recibir", "ID", "Talla", "Color", "Lote", "Pedimento", "Fecha caducidad", "Serie producto", "Comentario serie", "Garantía", "ImpoImpue"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true, true, true, true, true, true, true, true, false
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

        jP2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Cod. Proveedor:");
        jP2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 110, -1));

        jLabel6.setText("Cod. proveedor:");
        jP2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, -1));

        jTNoDoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoDoc.setNextFocusableComponent(jDFech);
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
        jP2.add(jTNoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 120, 20));

        jLabel3.setText("Nombre proveedor:");
        jP2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 110, -1));

        jLabel2.setText("Fecha entrega:");
        jP2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 110, -1));

        jLabel20.setText("No. documento:");
        jP2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 110, -1));

        jTCod.setEditable(false);
        jTCod.setForeground(new java.awt.Color(51, 51, 255));
        jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCod.setNextFocusableComponent(jTEst);
        jTCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodFocusLost(evt);
            }
        });
        jTCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodKeyPressed(evt);
            }
        });
        jP2.add(jTCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 160, 20));

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
        jP2.add(jTNomProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 380, 20));

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
        jP2.add(jTCodProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 120, 20));

        jTFec.setEditable(false);
        jTFec.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFec.setNextFocusableComponent(jTCod);
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
        jP2.add(jTFec, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 130, 20));

        jLabel5.setText("Cod. orden:");
        jP2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 80, -1));

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
        jP2.add(jTEst, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 90, 20));

        jLabel7.setText("Estado:");
        jP2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 80, -1));

        jLabel8.setText("Fecha:");
        jP2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 80, -1));

        jTFEnt.setEditable(false);
        jTFEnt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFEnt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFEntFocusLost(evt);
            }
        });
        jP2.add(jTFEnt, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 130, 20));

        jLabel29.setText("Fecha compra:");
        jP2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 100, -1));

        jDFech.setNextFocusableComponent(jBCarg);
        jDFech.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFechKeyPressed(evt);
            }
        });
        jP2.add(jDFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 120, -1));

        jP1.add(jP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 810, 110));

        jTSubTot.setEditable(false);
        jTSubTot.setBackground(new java.awt.Color(204, 255, 204));
        jTSubTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTSubTot.setForeground(new java.awt.Color(51, 51, 0));
        jTSubTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTSubTot.setText("$0.00");
        jTSubTot.setFocusable(false);
        jTSubTot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSubTotFocusGained(evt);
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
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 460, 110, -1));

        jTImp.setEditable(false);
        jTImp.setBackground(new java.awt.Color(204, 255, 204));
        jTImp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTImp.setForeground(new java.awt.Color(51, 51, 0));
        jTImp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTImp.setText("$0.00");
        jTImp.setFocusable(false);
        jTImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTImpFocusGained(evt);
            }
        });
        jTImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTImpKeyPressed(evt);
            }
        });
        jP1.add(jTImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 450, 160, 30));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel28.setText("Total:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 490, 110, -1));

        jTTot.setEditable(false);
        jTTot.setBackground(new java.awt.Color(204, 255, 204));
        jTTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTTot.setForeground(new java.awt.Color(51, 51, 0));
        jTTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTot.setText("$0.00");
        jTTot.setFocusable(false);
        jTTot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTotFocusGained(evt);
            }
        });
        jTTot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTotKeyPressed(evt);
            }
        });
        jP1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 480, 160, 30));

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

        jLabel1.setText("Observaciones:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 390, 130, -1));

        jTObserv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTObserv.setNextFocusableComponent(jComMon);
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
        jP1.add(jTObserv, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 390, 410, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 510, 160, -1));

        jCRecibT.setBackground(new java.awt.Color(255, 255, 255));
        jCRecibT.setSelected(true);
        jCRecibT.setText("Recibir todo");
        jCRecibT.setName(""); // NOI18N
        jCRecibT.setNextFocusableComponent(jTNoDoc);
        jCRecibT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCRecibTKeyPressed(evt);
            }
        });
        jP1.add(jCRecibT, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 140, -1));

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("*Serie compra:");
        jP1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, 100, -1));

        jComSer.setName(""); // NOI18N
        jComSer.setNextFocusableComponent(jComFormPag);
        jComSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComSerFocusLost(evt);
            }
        });
        jComSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComSerActionPerformed(evt);
            }
        });
        jComSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComSerKeyPressed(evt);
            }
        });
        jP1.add(jComSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 430, 120, 20));

        jTSerProv.setEditable(false);
        jTSerProv.setFocusable(false);
        jP1.add(jTSerProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 50, 10, -1));

        jCConta.setBackground(new java.awt.Color(255, 255, 255));
        jCConta.setText("Contado ");
        jCConta.setNextFocusableComponent(jCRecibT);
        jCConta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCContaKeyPressed(evt);
            }
        });
        jP1.add(jCConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 470, 110, 20));

        jBCarg.setBackground(new java.awt.Color(255, 255, 255));
        jBCarg.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBCarg.setText("Cargar");
        jBCarg.setToolTipText("Cargar archivos de la compra");
        jBCarg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBCarg.setName(""); // NOI18N
        jBCarg.setNextFocusableComponent(jBLimp);
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
        jP1.add(jBCarg, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 90, 70, -1));

        jBLimp.setBackground(new java.awt.Color(255, 255, 255));
        jBLimp.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBLimp.setText("Limpiar");
        jBLimp.setToolTipText("Limpiar lista de archivos seleccionados");
        jBLimp.setNextFocusableComponent(jTab);
        jBLimp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBLimpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBLimpMouseExited(evt);
            }
        });
        jBLimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimpActionPerformed(evt);
            }
        });
        jBLimp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBLimpKeyPressed(evt);
            }
        });
        jP1.add(jBLimp, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 110, 70, 20));

        jComMon.setNextFocusableComponent(jComSer);
        jComMon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComMonFocusLost(evt);
            }
        });
        jComMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComMonActionPerformed(evt);
            }
        });
        jComMon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComMonKeyPressed(evt);
            }
        });
        jP1.add(jComMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 410, 120, 20));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Moneda:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 410, 100, -1));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel31.setText("Descuento:");
        jP1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 430, 110, -1));

        jDesc.setEditable(false);
        jDesc.setBackground(new java.awt.Color(204, 255, 204));
        jDesc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jDesc.setForeground(new java.awt.Color(51, 51, 0));
        jDesc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jDesc.setText("$0.00");
        jDesc.setFocusable(false);
        jDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jDescFocusGained(evt);
            }
        });
        jDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDescKeyPressed(evt);
            }
        });
        jP1.add(jDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 420, 160, 30));

        jLabel13.setText("Forma pago:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 450, 100, -1));

        jComFormPag.setNextFocusableComponent(jCConta);
        jComFormPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComFormPagFocusLost(evt);
            }
        });
        jComFormPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComFormPagActionPerformed(evt);
            }
        });
        jComFormPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComFormPagKeyPressed(evt);
            }
        });
        jP1.add(jComFormPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 450, 120, 20));

        jTComenSer.setEditable(false);
        jTComenSer.setFocusable(false);
        jP1.add(jTComenSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 50, 10, -1));

        jTSerProd.setEditable(false);
        jTSerProd.setFocusable(false);
        jP1.add(jTSerProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 50, 10, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
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
        Star.recibOrdG  = null;
        
        /*Esconde la forma del visor de archivos*/
        if(filVi!=null)
            filVi.setVisible(false);
        
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

    
    /*Cuando se presiona una tecla en el campo de subtotal*/
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
    private void jTCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCodKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de código de compra*/
    private void jTCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCod.setSelectionStart(0);jTCod.setSelectionEnd(jTCod.getText().length());        
        
    }//GEN-LAST:event_jTCodFocusGained

    
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

    
    /*Cuando se presiona el botón de aceptar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
        
        /*Recorre la tabla de partidas en caso de que no quiera comprar toda la órden*/        
        boolean bSi   = false;
        if(!jCRecibT.isSelected())
        {
            for(int x = 0; x < jTab.getRowCount(); x++)
            {
                /*Obtiene la cantidad de devolución*/
                String sCantDev         = jTab.getValueAt(x, 13).toString();

                /*Si la cantidad es cadena vacia en entonces*/
                if(sCantDev.compareTo("")==0)
                    continue;

                /*Si la cantidad es igual o menor a cero entonces*/
                if(Double.parseDouble(sCantDev) <= 0)
                    continue;

                /*Pon la bandera en true para saber que si hay cambios*/
                bSi   = true;            
            }
            
            /*Si no hay cambios entonces*/
            if(!bSi)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No hay cantidad en las partidas para comprar.", "Orden de compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                     
                return;
            }
            
        }/*Fin de if(jCRecibT.isSelected())*/                                           

        /*Si no se a seleccionado una serie para la compra entonces*/
        if(jComSer.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el combobox de series*/
            jComSer.grabFocus();
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "Selecciona una serie para la compra.", "Generar compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                         
            return;
        }

        /*Contador para saber la cantidad total que se puede comprar*/
        String sCantComp    = "0";        

        /*Contador para saber la cantidad total que se va a comprar y saber si ya se compro todo*/
        String sCantFComp   = "0";
        
        /*Recorre toda la tabla de partidas para válidar que las cantidades que se van a comprar sean correctas*/
        
        if(!jCRecibT.isSelected())
        {
            for(int x = 0; x < jTab.getRowCount(); x++)
            {
                /*Obtiene la cantidad de compra*/
                String sCantDev                = jTab.getValueAt(x, 13).toString();
                
                /*Si la cantidad es cadena vacia que sea 0*/
                if(sCantDev.compareTo("")==0)
                    sCantDev                    = "0";

                /*Genera la cantidad real límite que se puede comprar*/
                String sCantRea                = Double.toString(Double.parseDouble(jTab.getValueAt(x, 2).toString()) - Integer.parseInt(jTab.getValueAt(x, 12).toString()));            
                
                /*Ve sumando la cantidad que se puede comprar en global*/
                sCantComp                       = Double.toString(Double.parseDouble(sCantComp) + Double.parseDouble(sCantRea));
                
                /*Ve sumando la cantidad que se va comprar en global*/
                sCantFComp                      = Double.toString(Double.parseDouble(sCantFComp) + Double.parseDouble(sCantDev));

                /*Si la cantidad de compra es mayor a la cantidad real posible de compra entonces*/
                if(Double.parseDouble(sCantDev)> Double.parseDouble(sCantRea))
                {                
                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "La cantidad a comprar es mayor a la cantidad posible de compra.", "Orden de compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
                    return;
                }            
            }                
            
        }/*Fin de if(!jCRecibT.isSelected())*/                    
        
        //Si esta marcado recibir todo entonces
        if(jCRecibT.isSelected())
        {
            /*Preguntar al usuario si esta seguro de querer recibir todo*/
            Object[] op = {"Si","No"};
            int iRes    = JOptionPane.showOptionDialog(this, "Toda la mercancia se va a recibir.¿Estas seguro de que quieres continuar?", "Orden de compra", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
            if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                return;                       
        }
        
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres hacer la compra?", "Orden de compra", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       

        /*Lee la fecha del documento*/
        Date fe                 =  jDFech.getDate();
        SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        String sFDoc            = sdf.format(fe);      
        
        /*Lee la fecha de entrega*/
        fe                      =  jDFech.getDate();        
        String sFEnt            = sdf.format(fe);      
        
        /*Obtiene el código de la órden*/
        String sCodO            = jTCod.getText();
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(false, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si tiene crédito el proveedor y los días de crédito*/
        boolean bSiC = false;
        String sFVenc = "now()";                
        try
        {                  
            sQ = "SELECT diacred AS dias, '" + sFDoc + "' + INTERVAL diacred DAY AS vencimien FROM provs WHERE CONCAT_WS('', provs.SER, provs.PROV ) = '" + jTCodProv.getText() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Coloca la bandera*/
                if(Integer.parseInt(rs.getString("dias")) > 0)
                    bSiC = true;

                /*Obtiene la fecha de vencimiento*/
                sFVenc  = "'" + rs.getString("vencimien") + "'";                                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }                    

        //Declara variables locales
        String sPDFC    = "0";
        String sXMLC    = "0";
        
        /*Determina si el PDF y el XML esta cargado*/        
        if(sRuts!=null)
        {
            /*Recorre todos los archivos guardados*/
            for(int x = 0; x < sRuts.length - 1; x++)
            {
                /*Si es nulo que continue*/
                if(sRuts[x][1]==null)
                    continue;
                
                /*Si el nombre del archivo termina con .PDF entonces marca la bandera y continua*/
                if(sRuts[x][1].toLowerCase().endsWith(".pdf"))
                {
                    sPDFC   = "1";
                    continue;
                }
                
                /*Si el nombre del archivo termina con .XML entonces marca la bandera*/
                if(sRuts[x][1].toLowerCase().endsWith(".xml"))
                    sXMLC   = "1";        
            }
        }
               
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;        

        /*Actualiza la órden de compra*/
        try 
        {            
            
            sQ = "UPDATE comprs SET "
                    + "subtot           = " + jTSubTot.getText().trim().replace("$", "").replace(",", "") + ", "
                    + "impue            = " + jTImp.getText().trim().replace("$", "").replace(",", "") + ", "
                    + "tot              = " + jTTot.getText().trim().replace("$", "").replace(",", "")
                    + " WHERE codcomp   = '" + jTCod.getText().trim() + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }        

        //Declara variables locales
        String sSer     = "";
        String sSerCons   = "";
        
        /*Obtiene el consecutivo y la serie de la serie de las compras*/        
        try
        {
            sQ = "SELECT ser, consec FROM consecs WHERE ser = '" + jComSer.getSelectedItem().toString().trim() + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces otbiene los resutlados*/
            if(rs.next())
            {
                sSerCons      = rs.getString("ser") + rs.getString("consec");                                   
                sSer        = rs.getString("ser");                                   
            }
        }
        catch(SQLException expnSQL)
        {    
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
        
        /*Actualiza el consecutivo de la compra*/
        try 
        {            
            sQ = "UPDATE consecs SET "
                    + "consec       = consec + 1, "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE tip    = 'COMP' AND ser  = '" + jComSer.getSelectedItem().toString().trim() + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }        
                                
        /*Si se va a comprar todo entonces*/
        if(jCRecibT.isSelected())
        {            
            /*Recorre toda la tabla de partidas*/
            for(int x = 0; x < jTab.getRowCount(); x++)
            {                   
                /*Calcula la cantidad posible de compra*/
                String sCan = Double.toString(Double.parseDouble(jTab.getValueAt(x, 2).toString()) - Double.parseDouble(jTab.getValueAt(x, 12).toString()));

                /*Coloca el valor correcto en el campo a comprar*/
                jTab.setValueAt(sCan, x, 13);                
            }            
            
            /*Actualiza el estado de la órden a confirmada*/
            try 
            {            
                sQ = "UPDATE comprs SET "
                        + "estado = 'CO', "
                        + "observ = '" + jTObserv.getText().replace("'", "''") + "', "
                        + "fmod = now(), "
                        + "sucu = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE codcomp = '" + sCodO.replace("'", "''") + "' AND tip = 'ORD'";                    
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
        /*Else si no se va a comprar todo entonces*/
        else
        {            
            /*Si todo lo que es posible comprar se va a comprar entonces*/            
            if(Double.parseDouble(sCantComp)==Double.parseDouble(sCantFComp))
            {                
                /*Actualiza el estado de la órden para que este ya confirmada*/
                try 
                {                
                    sQ = "UPDATE comprs SET "
                            + "estado = 'CO' "
                            + "WHERE codcomp = '" + jTCod.getText().replace("'", "''") + "'";                    
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
        
        //Se declaran variables globales
        String sSubTot  ="0";
        String sDesc    ="0";
        String sImpu    ="0";
            
        /*Recorre toda la tabla de partidas*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {                    
            /*Si lo que se quiere regresar es cadena vacio o menor a 0 entonces que continue*/
            if(!jCRecibT.isSelected() && (jTab.getValueAt(x, 13).toString().compareTo("")==0 || jTab.getValueAt(x, 13).toString().compareTo("0")==0))
                continue;
            
            /*Obtiene el importe                                                                    costo                                                           cantidad a resivir*/
            String sImpor       = Double.toString(Double.parseDouble(jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", ""))*Double.parseDouble(jTab.getValueAt(x, 13).toString().replace("$", "").replace(",", "")));               

            //se saca el descuento            importe                       descuento/100
            double dDesc        = Double.parseDouble(sImpor)*Double.parseDouble(jTab.getValueAt(x, 7).toString())/100;
                
            //se saca el impuesto adicional  importe menos el primer decuento          descuento adicional / 100
            dDesc               = dDesc+((Double.parseDouble(sImpor)-dDesc)*Double.parseDouble(jTab.getValueAt(x, 8).toString())/100);
                
            //Obtiene el valor del impuesto
            String sValImp      = Star.sGetValImp(jTab.getValueAt(x, 9).toString().trim());
                    
            //se carga la cantidad de impuesto al importe con el descuento
            String  sImpue      = Double.toString((Double.parseDouble(sImpor)-dDesc)*Double.parseDouble(sValImp)/100);
                
            //Se obtienen los totales
            sSubTot             = Double.toString(Double.parseDouble(sSubTot)+Double.parseDouble(sImpor));
            sDesc               = Double.toString(Double.parseDouble(sDesc)+dDesc);
            sImpu               = Double.toString(Double.parseDouble(sImpu)+Double.parseDouble(sImpue));
            
            /*Actualiza algunos datos de la partida*/
            try 
            {            
                sQ = "UPDATE partcomprs SET "
                        + "cant         = " + jTab.getValueAt(x, 2).toString() + ", "                        
                        + "cost         = " + jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "")
                        + " WHERE id_id    = " + jTab.getValueAt(x, 14).toString();                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
            }       
            
            /*Actualiza la partida en la base de datos para saber los que se compraron*/
            try 
            {                
                sQ = "UPDATE partcomprs SET "
                        + "recib        = recib + " + jTab.getValueAt(x, 13).toString() + " "
                        + "WHERE prod   = '" + jTab.getValueAt(x, 1).toString().replace("'", "''") + "' AND codcom = '" + sCodO.replace("'", "''") + "' AND id_id = " + jTab.getValueAt(x, 14).toString();                                                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
            }
            
            /*La cantidad a manejar originalmente será la que ingreso el usuario*/            
            String sCantO       = jTab.getValueAt(x, 13).toString().trim();

            /*Obtiene la cantidad correcta*/              
            sCantO              = Star.sCantUnid(jTab.getValueAt(x, 3).toString().trim(), sCantO);
            
            /*Realiza la afectación correspondiente al almacén*/
            if(Star.iAfecExisProd(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim(), jTab.getValueAt(x, 4).toString().replace("'", "''").trim(), sCantO, "+")==-1)
                return;

            /*Actualiza la existencia general del producto*/
            if(Star.iCalcGralExis(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim())==-1)
                return;

            /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
            if(!Star.vRegMoniInv(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim(), sCantO, jTab.getValueAt(x, 5).toString().replace("'", "''"), jTab.getValueAt(x, 4).toString().replace("'", "''"), Login.sUsrG , jTNoDoc.getText().replace("'", "''"), "COMP ORD", jTab.getValueAt(x, 3).toString().replace("'", "''"), jTSerProv.getText().trim(), jTCodProv.getText().replace("'", "''"), "0"))                                
                return;                                                                                                                                                                                                             
                
            //Declara variables locales
            String sExistG  = "";
            String sCostU   = "";
            
            /*Obtiene algunos datos del producto para el costo promedio*/                           
            try
            {
                sQ = "SELECT (SELECT IFNULL(cost,0) FROM partcomprs WHERE prod = '" + jTab.getValueAt(x, 1) + "' ORDER BY id_id DESC LIMIT 1) AS ultcost, exist FROM prods WHERE prod = '" + jTab.getValueAt(x, 1) + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())
                {
                    /*Obtiene el último costo*/
                    sCostU  = rs.getString("ultcost");
                    
                    /*Si el último costo es cadena vacia entonces*/
                    if(sCostU==null || sCostU.compareTo("")==0)                    
                        sCostU  = "0";
                    
                    /*Obtiene la existencia general*/
                    sExistG = rs.getString("exist");
                }
            }
            catch(SQLException expnSQL)
            {                
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
            }
            
            /*Si el último costo es 0 entonces dejarlo como el costo actual*/
            String sCostP;
            if(Double.parseDouble(sCostU)==0)
                sCostP      = jTab.getValueAt(x, 6).toString();
            else                                   
                sCostP      = Double.toString(((Double.parseDouble(sExistG) * Double.parseDouble(sCostU)) + (Double.parseDouble(jTab.getValueAt(x, 2).toString()) * Double.parseDouble(jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "")))) / (Double.parseDouble(sExistG) + Double.parseDouble(jTab.getValueAt(x, 2).toString())));                                                    
            
            //Si es NaN que sea entonces el costo actual
            if(sCostP.compareTo("NaN")==0)
                sCostP      = jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "");
            
            /*Crea el importe que es la cantidad por el costo*/
            String sImp = Double.toString(Double.parseDouble(jTab.getValueAt(x, 2).toString()) * Double.parseDouble(jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "")));
                        
            /*Si tene talla o color entonces procesa las tallas y colores y no es una órden de compra*/
            if(jTab.getValueAt(x, 15).toString().compareTo("")!=0 || jTab.getValueAt(x, 16).toString().compareTo("")!=0)                           
                Star.vTallCol(con, jTab.getValueAt(x, 2).toString().trim(), jTab.getValueAt(x, 4).toString().trim(), jTab.getValueAt(x, 15).toString().trim(), jTab.getValueAt(x, 15).toString().trim(), jTab.getValueAt(x, 1).toString().trim(), "+");            
            
            //Contiene la serie y el comentario
            String sSerie = "";
            String sComen = "";
        
            //Si el producto necesita a fuerzas serie entonces al contador se le da la cantidad para que inserte esas series
            if(Star.iProdSolSer(con, jTab.getValueAt(x, 1).toString().trim())==1)
            {
                //Contador inicial
                double iCantAgr = 1;
                
                //Obtiene la cantidad a recibir
                double iCont    = Double.parseDouble(jTab.getValueAt(x, 13).toString().trim());
                
                //La cantidad total será gual al límite superior
                double iCantTot = iCont;
                
                //Se hara el numero de veces que se la cantidad a resibir
                for(double i = 0;i < iCont; i++)
                {
                    //Valida que la serie sae valida
                    boolean bSer = true;
                    
                    //Mientras sea una serie valida entonces
                    while(bSer)
                    {                        
                        /*Pidele al usuario que ingrese serie y comentario*/   
                        SelSer lo     = new SelSer(jTSerProd,jTComenSer, iCantAgr + "-" + iCantTot, jTab.getValueAt(x, 1).toString().trim(),jTab.getValueAt(x, 4).toString().trim(),0);                        
                        lo.setVisible   (true); 

                        //Se obtienen los valores
                        sSerie = jTSerProd.getText();
                        sComen = jTComenSer.getText();

                        if(sSerie.trim().compareTo("-1")==0)
                            sSerie = null;

                        /*Si es nula la serie entonces regresa*/
                        if(sSerie==null)
                        {
                            //Se reduce en uno el contador de total
                            iCantTot--;
                            bSer=false;
                            continue;
                        }

                        /*Si es cadena vacia entonces*/
                        if(sSerie.compareTo("")==0)
                        {                    
                            /*Mensajea*/
                            JOptionPane.showMessageDialog(null, "Ingresa una serie válida.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                            continue;
                        }

                        //Se revisa si se puede repetir series
                        if(Star.iConfSer(con)==0)
                        {

                            //Se comprueba si la serie ya existe
                            if(Star.iSerRep(con, sSerie)==1)
                            {
                                /*Mensajea*/
                                JOptionPane.showMessageDialog(null, "La serie " + sSerie + " ya existe.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                                continue;
                            }

                            //Se comprueba si la serie ya existe
                            if(Star.vSerRepTab(sSerie,jTab, 17, iContFi)==1)
                            {
                                /*Mensajea*/
                                JOptionPane.showMessageDialog(null, "La serie " + sSerie + " ya existe en esta compra.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                                continue;
                            }
                            
                        }//Fin if(Star.iConfSer(con)==0)

                        //Si el comentario es nulo se pone en blanco
                        if(sComen==null)
                            sComen = "";

                        //Se aumenta el contador de agregados
                        iCantAgr++;

                        //Se cambia el flag indicando que esta bien la serie
                        bSer = false;

                    }//Fin while(bSer)
                    
                    /*Si la serie es nula entonces regresa*/
                    if(sSerie==null)
                    {
                        //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return;

                        //Mensajea
                        JOptionPane.showMessageDialog(null, "El producto solicita número de serie.", "Número serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                        return;                        
                    }
                    
                    //Se mete la serie a la compra
                    Star.vSerPro(con, jTab.getValueAt(x, 1).toString(), Double.toString(Double.parseDouble(sCantO)/iCont), sSerie, jTab.getValueAt(x, 4).toString(), sComen, "+");            
                    
                    //Crea la descripción de serie para la partida
                    String sSerProd     = sSerie.trim();
                    if(sSerProd.compareTo("")!=0)
                        sSerProd        = "SER:" + sSerProd;
                    String sComenSer    = sComen.trim();
                    
                    //Si la serie es cadena vacia entonces el comentario también
                    if(sSer.trim().compareTo("")==0)
                        sComenSer       = "";

                    //Inserta cada partida
                    try 
                    {                    
                        sQ = "INSERT INTO partcomprs(codcom,                                    prod,                                                      cant,         unid,                                                               descrip,                                                                                                       cost,                                                                          descu,                                            descad,                                         codimpue,                                                              mon,                                                                impo,      falt,recib,      alma,                                                            costpro,                                                                    cantlotpend,      tipcam, serprod,                            comenser,          garan)" + 
                                     "VALUES('" +  sSerCons.replace("'", "''") +"','"  +    jTab.getValueAt(x, 1).toString().replace("'", "''") + "',' 1 ','" +      jTab.getValueAt(x, 3).toString().replace("'", "''") + "','" +       jTab.getValueAt(x, 5).toString().replace("'", "''").trim() + " " + sSerProd + " " + sComenSer + "','" +       jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "") + "','" +    jTab.getValueAt(x, 7).toString() + "','" +        jTab.getValueAt(x, 8).toString() + "','" +      jTab.getValueAt(x, 9).toString().replace("'", "''") + "','" +         jTab.getValueAt(x, 10).toString().replace("'", "''") + "','" +      sImp + "', now(),   1 , '" + jTab.getValueAt(x, 4).toString().replace("'", "''") + "', " +    sCostP.replace("$", "").replace(",", "") + ",                              0,                0, '" + sSerie.replace("'", "''") + "', '" + sComen + "', '" + jTab.getValueAt(x, 22).toString() + "')";                    
                        st = con.createStatement();
                        st.executeUpdate(sQ);
                     }
                     catch(SQLException expnSQL) 
                     { 
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                        return;                                                                                                                             
                    }
                                        
                }//Fin for(int i=0;i<iCont;i++)
                
            }//Fin if(Star.iProdSolSer(con, jTab.getValueAt(x, 1).toString().trim())==1)
            else
            {
                /*Inserta en la base de datos la partida de la compra*/
                try 
                {                    
                    sQ = "INSERT INTO partcomprs(codcom,                                    prod,                                                               cant,                                                               unid,                                                               descrip,                                                            cost,                                                                          descu,                                            descad,                                         codimpue,                                                              mon,                                                                impo,      falt,       recib,                                      alma,                                                            costpro,                                    cantlotpend,      tipcam,           serprod,                                            comenser,                                           garan)" + 
                                     "VALUES('" + sSerCons.replace("'", "''") + "','"  +    jTab.getValueAt(x, 1).toString().replace("'", "''") + "','" +       jTab.getValueAt(x, 13).toString().replace("'", "''") + "','" +      jTab.getValueAt(x, 3).toString().replace("'", "''") + "','" +       jTab.getValueAt(x, 5).toString().replace("'", "''") + "','" +       jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "") + "','" +    jTab.getValueAt(x, 7).toString() + "','" +        jTab.getValueAt(x, 8).toString() + "','" +      jTab.getValueAt(x, 9).toString().replace("'", "''") + "','" +         jTab.getValueAt(x, 10).toString().replace("'", "''") + "','" +      sImp + "', now(), " +  jTab.getValueAt(x, 13).toString() + ", '" + jTab.getValueAt(x, 4).toString().replace("'", "''") + "', " +    sCostP.replace("$", "").replace(",", "") + ",                              0,                0, '" + jTab.getValueAt(x, 20).toString().trim() + "', '" + jTab.getValueAt(x, 21).toString().trim() + "', '" + jTab.getValueAt(x, 22).toString() + "')";                                    
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
            
            /*Actualiza el último costo del producto*/
            try
            {
                sQ = "UPDATE prods SET "
                        + "cost         = " + jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "") + " "
                        + "WHERE prod   = '" + jTab.getValueAt(x, 1).toString().trim() + "'";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                                                                                     
            }
            
        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
        
        /*Obtiene el total que es la suma del subtotal mas el impuesto*/
        String sTot =Double.toString(Double.parseDouble(sSubTot)-Double.parseDouble(sDesc)+Double.parseDouble(sImpu));
        
        //Comprueba si el proveedor no esta bloqueado
        iRes        = Star.iGetBloqCredCliProv(null, "prov", jTCodProv.getText().replace("'", "''").trim());

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        /*Si tiene días de crédito, no va a ser de contado y no esta bloqueado el proveedor entonces*/
        if(bSiC && iRes==0 && !jCConta.isSelected())
        {
            //Inserta cxc en la base de datos
            if(Star.iInsCXCP(con, "cxp", sSerCons, sSer, jTCodProv.getText(), jTSerProv.getText(), sSubTot, sImpu, sTot, sTot, "0",  sFVenc ,"'" + sFDoc+ "'", "COMP", "", "0", "", "","")==-1)
                return;                                
        }
        //Else no es de crédito pero inserta el CXP
        else
        {
            //Inserta el cargo
            if(Star.iInsCXCP(con, "cxp", sSerCons, sSer, jTCodProv.getText(), jTSerProv.getText(), sSubTot, sImpu, sTot, sTot, "0",  sFVenc ,"'" + sFDoc+ "'", "COMP", "", "0", "", "","")==-1)
                return;        
            
            //Inserta el cargo
            if(Star.iInsCXCP(con, "cxp", sSerCons, sSer, jTCodProv.getText(), jTSerProv.getText(), sSubTot, sImpu, sTot, "0", sTot,  sFVenc , sFVenc, "ABON COMP", jComFormPag.getSelectedItem().toString(), "0", "", "","")==-1)
                return;                                
        }
        
        /*Obtiene el tipo de cambio de la moneda*/
        String sTipCam = "";
        try
        {
            sQ = "SELECT val FROM mons WHERE mon = '" + jComMon.getSelectedItem().toString().trim() + "'";
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
            
        //Inserta en la base de datos la nueva compra
        if(Star.iInsComprs(con, sSerCons.replace("'", "''"), sSer.replace("'", "''").trim(), jTCodProv.getText().replace("'", "''").trim(), jTSerProv.getText().replace("'", "''").trim(), jTNoDoc.getText().replace("'", "''").trim(), "'" + sFDoc + "'", sSubTot, sImpu, sTot, "'CO'", "now()", "", jTNomProv.getText().replace("'", "''").trim(), sFVenc, "COMP", "'" + sFEnt + "'", "", "", jComMon.getSelectedItem().toString().trim(), sTipCam, sCodO, sPDFC.replace("'", "''").trim(), sXMLC.replace("'", "''").trim())==-1)
            return;
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        /*Obtiene la cantidad de recibidos de las partidas de la compra*/
        String sCantR   = "";
        try
        {
            sQ = "SELECT SUM(recib) AS recib FROM partcomprs WHERE codcom = '" + sSerCons + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())                            
                sCantR      = rs.getString("recib");                                                               
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
        
        /*Obtiene algunos datos de la compra y el nombre de la estación*/
        String sFMod    = "";
        String sNomEsta = "";
        String sFAlt    = "";
	try
        {                  
            sQ = "SELECT (SELECT nom FROM estacs WHERE estac = '" + Login.sUsrG + "') AS nome, falt, fmod FROM comprs WHERE codcomp = '" + sCodO + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sNomEsta    = rs.getString("nome");                                   
                sFMod       = rs.getString("fmod");                                   
                sFAlt       = rs.getString("falt");                                   
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

        /*Modifica el estado de la órden en la tabla del otro formulario*/
        if(jCRecibT.isSelected())                    
            jTabComp.getModel().setValueAt("CO", rowGlo, 11);
        
        /*Modifica la fecha de última modificación en la tabla compras del otro formulario*/
        jTabComp.getModel().setValueAt(sFMod, rowGlo, 11);
        
        /*Modifica El usuario en la tabla de compras del otro formulario*/
        jTabComp.getModel().setValueAt(Login.sUsrG, rowGlo, 17);
        
        /*Modifica el nombre de la estación del otro formulario*/
        jTabComp.getModel().setValueAt(sNomEsta, rowGlo, 18);
        
        /*Agrega los datos de la nueva compra en la tabla de compras del otro formulario*/
        DefaultTableModel te    = (DefaultTableModel)jTabComp.getModel();
        Object nu[]             = {Star.iContFiComp, "COMP", sSerCons, sSer, jTNoDoc.getText(), jTCodProv.getText(), jTNomProv.getText(), sTot, jComMon.getSelectedItem().toString().trim(), sFAlt, sFDoc, sFEnt, sFAlt, "CO", "", sCantR, jTCod.getText().trim(), "", "", Star.sSucu, Star.sNoCaj, Login.sUsrG, sNomEsta};
        te.addRow(nu);
       
        /*Si la carpeta de la aplicación existe entonces*/
        if(sCarp.compareTo("")!=0)
        {
            /*Si la carpeta de las imágenes no existe entonces creala*/
            sCarp                   += "\\Imagenes";
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();

            /*Si no existe la carpeta de las comprs entonces creala*/
            sCarp                    += "\\Compras";
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();            
            
            /*Si no existe la carpeta de la empresa entonces creala*/
            sCarp                    += "\\" + Login.sCodEmpBD;
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();
            
            /*Si no existe la carpeta para el proveedor entonces creala*/
            sCarp                    += "\\" + jTCodProv.getText();
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();
            
            /*Si no existe la carpeta para la compra entonces creala*/
            sCarp                    += "\\" + sSerCons;     
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();
            
            /*Si el arreglo de archivos no es nulo entonces*/
            if(sRuts!=null)
            {
                /*Recorre todas las rutas de los archivos*/
                for(int x = 0; x < sRuts.length - 1; x++)
                {   
                    /*Si es nulo entonces continua*/
                    if(sRuts[x][0]==null)
                        continue;
                    
                    /*Si el archivo no existe entonces*/
                    if(!new File(sRuts[x][0]).exists())
                    {
                        /*Mensajea y continua*/
                        JOptionPane.showMessageDialog(null, "El archivo: " + sRuts[x][0] + "." + System.getProperty( "line.separator" ) + "no existe y no se guardara para la compra", "Archivos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        continue;
                    }                                                            
                    
                    /*Copia el archivo orgien al destino*/
                    try 
                    {                
                        org.apache.commons.io.FileUtils.copyFile(new File(sRuts[x][0]), new File(sCarp + "\\" + sRuts[x][1]));
                    } 
                    catch(IOException expnIO) 
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                        return;                                                                                                                             
                    }
                }                
            }
            
        }/*Fin de if(sCarp.compareTo("")==0)*/   
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Exito en la compra de la orden: " + sCodO + ".", "Recepción de Orden de Compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                 
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Sal del formulario*/
        this.dispose();
        Star.recibOrdG  = null;
               
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuando se presiona una tecla en la tabla de partidas*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de las observaciones*/
    private void jTObservFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTObservFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTObserv.setSelectionStart(0);jTObserv.setSelectionEnd(jTObserv.getText().length());                
        
    }//GEN-LAST:event_jTObservFocusGained

    
    /*Cuando se presiona una tecla en el campo de las observaciones*/
    private void jTObservKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTObservKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTObservKeyPressed
                
   
    /*Cuando se pierde el foco del teclado en el campo de las observaciones*/
    private void jTObservFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTObservFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTObserv.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTObserv.getText().compareTo("")!=0)
            jTObserv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTObserv.getText().length()> 255)
            jTObserv.setText(jTObserv.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTObservFocusLost

    
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

    
    /*Cuando se presiona una tecla en el checkbox de recibir todo*/
    private void jCRecibTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCRecibTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCRecibTKeyPressed

    private void jComSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComSer.getSelectedItem().toString().compareTo("")!=0)
        jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComSerFocusLost

    
    /*Cuando se presiona una tecla en el combo de las series de las compras*/
    private void jComSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComSerKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la fecha de la compra*/
    private void jDFechKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFechKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jDFechKeyPressed

    
    /*Cuando se presiona una tecla en el chck de contado*/
    private void jCContaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCContaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCContaKeyPressed

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón de cargar archivos a la compra*/
    private void jBCargMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargMouseEntered

        /*Cambia el color del fondo del botón*/
        jBCarg.setBackground(Star.colBot);

        /*Si no hay archivos para mostrar entonces regresa*/
        if(sRuts==null)
            return;
        
        /*Muestra la forma para ver los archivos cargados ya en otra vista*/
        filVi = new FilVis(sRuts);            
        filVi.setVisible(true);

    }//GEN-LAST:event_jBCargMouseEntered

    
    /*Cuando el botón sale del botón de cargar archivos a la compra*/
    private void jBCargMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBCarg.setBackground(Star.colOri);
        
        /*Si el visor no es nulo entonces escondelo*/
        if(filVi!=null)
            filVi.setVisible(false);

    }//GEN-LAST:event_jBCargMouseExited

    
    /*Cuando se presiona el botón de cargar archivos a la compra*/
    private void jBCargActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargActionPerformed

        /*Esconde la forma del visor de archivos*/
        if(filVi!=null)
            filVi.setVisible(false);
        
        /*Si la cantidad de archivos ya es iguala 5 o mayor entonces*/
        if(iXGlo>=5)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No puedes cargar mas de 5 archivos.", "Archivos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
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

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen de la compra*/
        final javax.swing.JFileChooser fc       = new javax.swing.JFileChooser();
        fc.setMultiSelectionEnabled     (true);
        fc.setDialogTitle               ("Buscar Archivo(s)");
        fc.setAcceptAllFileFilterUsed   (false);

        /*Muestra el file choooser*/
        int iVal                    = fc.showSaveDialog(this);

        /*Si el usuario presiono aceptar entonces*/
        if(iVal                    == javax.swing.JFileChooser.APPROVE_OPTION)
        {   
            /*Si no es nulo entonces inicializa el arreglo de los archivos*/
            if(sRuts==null)
            {
                /*Inicaliza el arreglo*/
                sRuts   = new String[5][2];
                
                /*Recorre todo el arreglo e inicializa todo en nulo*/
                for(int x = 0; x < sRuts.length; x++)
                    sRuts[x][1] = null;                                
            }
            
            /*Obtiene todos los archivos seleccionados*/
            File[] fils  = fc.getSelectedFiles();
            
            /*Recorre todos los archivos seleccionados*/
            for(File fil: fils)
            {                
                /*Si el contador ya es mayor a 5 entonces sal del bucle*/
                if(iXGlo>=5)
                    break;
                
                /*Lee la ruta seleccionada de origén*/
                String sRut              = fil.getAbsolutePath();

                /*Agrega en el arreglo la ruta nueva y el nombre del archivo*/
                sRuts[iXGlo][0]         = sRut;
                sRuts[iXGlo][1]         = fil.getName();

                /*Aumenta el contador del arreglo*/
                ++iXGlo;
            }                            
            
        }/*Fin de if(iVal           == JFileChooser.APPROVE_OPTION) */

    }//GEN-LAST:event_jBCargActionPerformed

    
    /*Cuando se presiona una tecla en el botón de cargar archivos a la compra*/
    private void jBCargKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCargKeyPressed

    
    /*Cuando se presiona el botón de limpiar archivos seleccionados*/
    private void jBLimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimpActionPerformed
        
        /*Inicia el arreglo en nulo otra vez y el contador*/
        sRuts   = null;
        iXGlo   = 0;
        
    }//GEN-LAST:event_jBLimpActionPerformed

    
    /*Cuando se presiona una tecla en limpiar archivos seleccionados*/
    private void jBLimpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLimpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBLimpKeyPressed

    
    /*Cuando el mouse entra en el botón de limpiar*/
    private void jBLimpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimpMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBLimp.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBLimpMouseEntered

    
    /*Cuando el mouse sale del botón de limpiar*/
    private void jBLimpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimpMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBLimp.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBLimpMouseExited

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCodProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProvFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCodProv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodProvFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTNoDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoDocFocusLost
    
        /*Coloca el caret en la posiciòn 0*/
        jTNoDoc.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNoDocFocusLost

    
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
    private void jTFEntFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFEntFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTFEnt.setCaretPosition(0);
        
    }//GEN-LAST:event_jTFEntFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCod.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTEstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTEst.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEstFocusLost

    
    /*Cuando se pierde el foco del teclado en el combo de las monedas*/
    private void jComMonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComMonFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComMon.getSelectedItem().toString().compareTo("")!=0)
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComMonFocusLost

    
    /*Cuando sucede una acción en el combo de las monedas*/
    private void jComMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComMonActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComMon.getSelectedItem()==null)
            return;
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "mondescrip", "mons", "WHERE mon = '" + jComMon.getSelectedItem().toString().trim() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComMon.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComMonActionPerformed

    
    /*Cuando se presiona una tecla en el combo de monedas*/
    private void jComMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComMonKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComMonKeyPressed

    
    /*Cuando sucede una acción en el combo de la serie de la compra*/
    private void jComSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComSerActionPerformed

        /*Si no hay selección entonces regresa*/
        if(jComSer.getSelectedItem()==null)
            return;
                    
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "consecs", "WHERE tip = 'COMP' AND ser = '" + jComSer.getSelectedItem().toString().trim() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComSer.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComSerActionPerformed

    
    //Cuando gana el foco se selecciona todo
    private void jDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDescFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jDesc.setSelectionStart(0);jDesc.setSelectionEnd(jDesc.getText().length()); 
        
    }//GEN-LAST:event_jDescFocusGained

    
    //Cuando se preciona algun boton en el campo descuento
    private void jDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDescKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDescKeyPressed

    
    //Cuando se pierde el foco del teclado en el combo de la forma de pago
    private void jComFormPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComFormPagFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComFormPag.getSelectedItem().toString().compareTo("")!=0)
            jComFormPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComFormPagFocusLost

    
    //Cuando sucede una acción en el combo de la forma de pago
    private void jComFormPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComFormPagActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComFormPag.getSelectedItem()==null)
            return;

        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "pags", "WHERE cod = '" + jComFormPag.getSelectedItem().toString().trim() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComFormPag.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
                    
    }//GEN-LAST:event_jComFormPagActionPerformed

    
    //Cuando se presiona una tecla en el combo de la forma de pago
    private void jComFormPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComFormPagKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComFormPagKeyPressed

       
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
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCarg;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBLimp;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTabG;
    private javax.swing.JCheckBox jCConta;
    private javax.swing.JCheckBox jCRecibT;
    private javax.swing.JComboBox jComFormPag;
    private javax.swing.JComboBox jComMon;
    private javax.swing.JComboBox jComSer;
    private com.toedter.calendar.JDateChooser jDFech;
    private javax.swing.JTextField jDesc;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jP2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTCod;
    private javax.swing.JTextField jTCodProv;
    private javax.swing.JTextField jTComenSer;
    private javax.swing.JTextField jTEst;
    private javax.swing.JTextField jTFEnt;
    private javax.swing.JTextField jTFec;
    private javax.swing.JTextField jTImp;
    private javax.swing.JTextField jTNoDoc;
    private javax.swing.JTextField jTNomProv;
    private javax.swing.JTextField jTObserv;
    private javax.swing.JTextField jTSerProd;
    private javax.swing.JTextField jTSerProv;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaEmpresa extends javax.swing.JFrame */
