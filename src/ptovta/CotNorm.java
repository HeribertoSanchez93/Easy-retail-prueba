//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
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
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para hacer una nueva cotización de las normales*/
public class CotNorm extends javax.swing.JFrame
{
    /*Contiene el color original del botón*/
    private java.awt.Color          colOri;
    
    /*Para saber si esta solo en visa*/
    private boolean                 bVist;
    
    /*Bandera para que no se modifique la descripción del producto en la tabla*/
    private boolean                 bModDescrip         = true;
    
    /*Contiene el almacén de cotización*/
    private String                  sAlmGlo     = "";
    
    /*Declara variables de instancia privadas*/    
    private int                     iContFiParts;
    private int                     iContFiGlo;
    private JTable                  jTabCaraGlob;      

    /*Contiene la dirección de la forma para ver imágen en otra vista*/
    private ImgVis                  v;
    
    /*Para almacenar la lista en caso de que algo este mal*/
    private String                  sListOri;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean                 bSel;
    
    /*Variables originales*/
    private String                  sCantOri;
    private String                  sUnidOri;
    private String                  sListPOri;
    private String                  sProdOri;
    private String                  sDescripOri;
    private String                  sPreOri;
    private String                  sImpoOri;            
    private String                  sDescOri;
    private String                  sAlmaOri;    
    private String                  sImpueOri;
    private String                  sImpueImpoOri;
    private String                  sImpoDescOri;
    private String                  sSerProdOri; 
    private String                  sComenSerOri; 
    private String                  sGaranOri; 
    private String                  sKitOri;
    private String                  sTallOri;
    private String                  sColOri;
    private String                  sBackOri;
    private String                  sLotOri;
    private String                  sPedimenOri;
    private String                  sFCaduOri;
    private String                  sCodImpOri;
    
    /*Contador para saber si va de salida o de entrada en la tabla de partidas en la modificación*/    
    private int                     iContCellEd;    
    
    
    
    
    
    /*Consructor sin argumentos*/
    public CotNorm(JTable jTabPar, int iContF, String sCod, boolean bVer) 
    {                
        /*Inicializa los componentes gráfcos*/
        initComponents();

        /*Pon el foco del teclado en el campo del código de la cliente*/
        jTCli.grabFocus();
                
        /*Guarda la bandera para saber si esta en solo visto*/
        bVist   = bVer;
        
        /*Si el código de la cotización no es nulo entonces*/
        if(sCod!=null)
        {
            /*Pon el foco del teclado en el botón de búscar cliente y coloca la etiqueta*/            
            jBCli.grabFocus();
            jLNot.setText("Modificar cotización: " + sCod);
        }
        
        /*Que no sean visibles los controles de lote y pedimento*/
        jTCantLot.setVisible(false);
        jTId.setVisible     (false);
        jTLot.setVisible    (false);
        jTPedimen.setVisible(false);
        jTCadu.setVisible   (false);
        
        /*Esconde la columna del costo*/
        jTab.getColumnModel().getColumn(20).setMinWidth(0);
        jTab.getColumnModel().getColumn(20).setMaxWidth(0);
        
        /*Esconde el control de la garantía*/
        jTGara.setVisible   (false);        
        
        /*Esconde el control del comentario de la serie*/
        jTComenSer.setVisible(false);        
        
        /*Esconde el control del almacén 2*/
        jTAlma2.setVisible(false);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri      = jBSal.getBackground();
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Selecciona la fecha del día de hoy + 10 días para la fecha de vencimiento*/
        Date f      = new Date();
        java.util.Calendar c = java.util.Calendar.getInstance(); 
        c.setTime(f); 
        c.add(java.util.Calendar.DATE, 10);
        f = c.getTime();        
        jDFVenc.setDate(f);  
        
        /*Selecciona la fecha del día de hoy para la fecha de entrega*/
        f = new Date();
        jDFEnt.setDate(f);
        
        /*Selecciona la fecha del día de hoy para el control de la cotización*/
        f = new Date();
        jDFech.setDate(f);
        
        /*Selecciona la fecha del día de hoy para el backorder*/
        jDBack.setDate(f);
        
        /*Listener para el combobox de almacenes*/
        jComAlma.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
        
                //Carga todos los almacenes en el combo
                if(Star.iCargAlmaCom(con, jComAlma)==-1)
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
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Recibe los parámetros del otro formulario*/           
        iContFiGlo      = iContF;
        jTabCaraGlob    = jTabPar;  
                        
        /*Incializa el contador del cell editor*/
        iContCellEd     = 1;
        
        /*Inicializa el contador de filas de las partidas*/
        iContFiParts    = 1;
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
           
        /*Establece el tamaño de las columnas de la tabla de partidas*/
        jTab.getColumnModel().getColumn(0).setPreferredWidth   (45);
        jTab.getColumnModel().getColumn(1).setPreferredWidth   (50);
        jTab.getColumnModel().getColumn(2).setPreferredWidth   (130);
        jTab.getColumnModel().getColumn(3).setPreferredWidth   (160);
        jTab.getColumnModel().getColumn(5).setPreferredWidth   (450);
        jTab.getColumnModel().getColumn(8).setPreferredWidth   (110);        
        jTab.getColumnModel().getColumn(9).setPreferredWidth   (100);
        jTab.getColumnModel().getColumn(10).setPreferredWidth  (110);
        jTab.getColumnModel().getColumn(11).setPreferredWidth  (110);
        jTab.getColumnModel().getColumn(12).setPreferredWidth  (110);
        jTab.getColumnModel().getColumn(13).setPreferredWidth  (130);
        jTab.getColumnModel().getColumn(14).setPreferredWidth  (180);
        jTab.getColumnModel().getColumn(15).setPreferredWidth  (140);
        jTab.getColumnModel().getColumn(16).setPreferredWidth  (140);
        jTab.getColumnModel().getColumn(17).setPreferredWidth  (140);
        jTab.getColumnModel().getColumn(18).setPreferredWidth  (140);
        jTab.getColumnModel().getColumn(19).setPreferredWidth  (140);
        jTab.getColumnModel().getColumn(22).setPreferredWidth  (140);
        jTab.getColumnModel().getColumn(23).setPreferredWidth  (140);
        
        /*Para que la tabla este ordenada al mostrarse y al dar clic en el nom de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Activa el JTextArea de observ para que se usen normamente las teclas de tabulador*/
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Activa el JTextArea de descripción para que se usen normamente las teclas de tabulador*/
        jTADescrip.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTADescrip.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Listener para el combobox de series de cotizaciones*/
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
                    sQ = "SELECT ser FROM consecs WHERE tip = 'COT'";                        
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
                
                //Carga todos los impuestos en el combo
                if(Star.iCargImpueCom(con, jComImp)==-1)
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
                
                //Carga todas las monedas en el combo
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
        
        /*Listener para el combobox de unidades*/
        jComUnid.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {                
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                //Obtiene todas las unidades y cargalas en el combo
                if(Star.iCargUnidCom(con, jComUnid)==-1)
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
                      
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        //Obtiene la configuración de si se puede modificar el tipo de cambio en el sistema o no
        int iRes    = Star.iCambTipCam(con);
        
        //Si hubo error entonces regesa
        if(iRes==-1)
            return;
        
        //Si esta deshabilitado que cambien el tipo de cambio  entonces deshabilita el botón de tipo de cambio
        if(iRes==0)
        {
            jBTipCam.setEnabled     (false);
            jBTipCam.setFocusable   (false);
        }
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        //Carga todos los almacenes en el combo
        if(Star.iCargAlmaCom(con, jComAlma)==-1)
            return;

        /*Obtiene el almacén configurado para venta*/
        try
        {
            sQ = "SELECT extr FROM confgral WHERE clasif = 'cots' AND conf = 'almavtac'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sAlmGlo = rs.getString("extr");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
        
        /*Si el almacén de venta no es cadena vacia entonces*/
        if(sAlmGlo.compareTo("")!=0)
        {
            /*Selecciona el almacén en el combo*/
            jComAlma.setSelectedItem(sAlmGlo);
            
            /*Deshabilita el combo de almacenes*/
            jComAlma.setFocusable   (false);
            jComAlma.setEnabled     (false);
        }
        
        //Carga todas las tallas en el combo
        if(Star.iCargTallCom(con, jComTall)==-1)
            return;
        
        //Trae todos los colores y cargalos en el combo
        if(Star.iCargColoCom(con, jComColo)==-1)
            return;
        
        /*Comprueba si se puede modificar la lista de precio o no*/
        try
        {                  
            sQ = "SELECT val FROM confgral WHERE conf = 'modlistcot' AND clasif = 'cots'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/                
            if(rs.next())
            {
                /*Si no se puede modificar entonces deshabilita los conroles involucrados*/
                if(rs.getString("val").compareTo("0")==0)
                {                                     
                    jTList.setEnabled   (false);                                        
                    jBList.setEnabled   (false);                                        
                }                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Comprueba si se puede o no modificar la descripción del producto*/
        try
        {                  
            sQ = "SELECT val FROM confgral WHERE clasif = 'cots' AND conf = 'moddescrip'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/                
            if(rs.next())
            {
                /*Si se no se puede modificar la desripción entonces deshabilita el campo*/
                if(rs.getString("val").compareTo("0")==0)
                {
                    /*Deshabilita el campo y el botón de descripción en grande*/
                    jTDescrip.setEditable   (false);                             
                    jBGranDescrip.setEnabled(false);
                    
                    /*Coloca la bandera para que no se pueda modificar la descripción de la tabla*/
                    bModDescrip = false;                    
                }
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
                
        /*Comprueba si se puede o no modificar el precio del producto*/
        try
        {                  
            sQ = "SELECT val FROM confgral WHERE clasif = 'cots' AND conf = 'modprec'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/                
            if(rs.next())
            {
                /*Si se no se puede modificar el precio entonces deshabilita el campo*/
                if(rs.getString("val").compareTo("0")==0)
                    jTPre.setEditable(false);                                                                      
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Agrega el elemento vacio en el combobox de sers*/
        jComSer.addItem("");
                
        /*Obtiene todas las sers actualizadas y cargalas en el combobox*/
        try
        {
            sQ = "SELECT ser FROM consecs WHERE tip = 'COT'";                        
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

        /*Selecciona el elemento el elemento vacio en el control de las sers*/
        jComSer.setSelectedItem("");
               
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si no existe la carpeta compartida de la aplicación entonces mensajea*/
        if(sCarp.compareTo("")==0)
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación.", "Carpeta Compartida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                     

        /*Contiene el código de la nueva cotización*/
        String sCodCot;
        
        /*Mientras el código de la nueva cotización este repetido seguirá obteniendo uno que no este repetido*/
        boolean bSi;
        do
        {
            /*Inicialmente no existe el código de la cotización*/
            bSi = false;
            
            /*Obtiene el código de la nueva cotización*/
            sCodCot                 = Star.sGenClavDia();
            
            /*Obtiene si ya existe ese código de cotización en la base de datos*/
            try
            {                  
                sQ = "SELECT codcot FROM cots WHERE codcot = '" + sCodCot + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces colcoa la bandera*/
                while(rs.next())
                    bSi = true;                    
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
        }while(bSi);
        
        //Carga todas las monedas en el combo
        if(Star.iCargMonCom(con, jComMon)==-1)
            return;
        
        /*Selecciona la moneda nacional*/
        try
        {                  
            sQ = "SELECT mon FROM mons WHERE mn = 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces selecciona la moneda nacional*/
            if(rs.next())
                jComMon.setSelectedItem(rs.getString("mon"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        //Carga todos los impuestos en el combo
        if(Star.iCargImpueCom(con, jComImp)==-1)
            return;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Cotizacion, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
                
        //Obtiene todas las unidades y cargalas en el combo
        if(Star.iCargUnidCom(con, jComUnid)==-1)
            return;

        /*Comprueba si existe correo dado de alta para el usuario actual*/
        bSi     = false;
        try
        {
            sQ = "SELECT portsmtp FROM corrselec WHERE estac = '" + Login.sUsrG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi  = true;                                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                   
        
        /*Si no existe corr entonces mensajea*/
        if(!bSi)
            JOptionPane.showMessageDialog(null, "No existe correo electrónico dado de alta para el usuario: " + Login.sUsrG + ".", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
        /*Si la cotización pasada no es nula entonces carga todos los datos machote*/
        if(sCod!=null)
            vCargT(con, sCod);
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Crea el listener para la tabla de partidas, para cuando se modifique una celda recalcular el subtotal*/
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
                    /*Si el contador es 1 entonces va de entrada*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene algunos datos originales*/
                        sCantOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                
                        sProdOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                        
                        sUnidOri        = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                        
                        sListPOri       = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sDescripOri     = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();                        
                        sPreOri         = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();                                                
                        sDescOri        = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sAlmaOri        = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();                        
                        sImpoOri        = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();                        
                        sImpueOri       = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sImpueImpoOri   = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();                                        
                        sImpoDescOri    = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();                                        
                        sSerProdOri     = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();
                        sComenSerOri    = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();
                        sGaranOri       = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();
                        sKitOri         = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();
                        sTallOri        = jTab.getValueAt(jTab.getSelectedRow(), 17).toString();
                        sColOri         = jTab.getValueAt(jTab.getSelectedRow(), 18).toString();
                        sBackOri        = jTab.getValueAt(jTab.getSelectedRow(), 19).toString();
                        sLotOri         = jTab.getValueAt(jTab.getSelectedRow(), 20).toString();
                        sPedimenOri     = jTab.getValueAt(jTab.getSelectedRow(), 21).toString();
                        sFCaduOri       = jTab.getValueAt(jTab.getSelectedRow(), 22).toString();
                        sCodImpOri      = jTab.getValueAt(jTab.getSelectedRow(), 23).toString();
    
                        /*Aumenta el celleditor en 1*/
                        ++iContCellEd;
                    }
                    /*Else, va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd = 1;                                                                                         

                        /*Coloca los valores originales que tenía*/                        
                        jTab.setValueAt     (sProdOri,       jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt     (sUnidOri,       jTab.getSelectedRow(), 3);                                                                       
                        jTab.setValueAt     (sListPOri,      jTab.getSelectedRow(), 4);
                        jTab.setValueAt     (sAlmaOri,       jTab.getSelectedRow(), 8);                                                
                        jTab.setValueAt     (sImpoOri,       jTab.getSelectedRow(), 9);                                                
                        jTab.setValueAt     (sImpueOri,      jTab.getSelectedRow(), 10);                        
                        jTab.setValueAt     (sImpueImpoOri,  jTab.getSelectedRow(), 11);                        
                        jTab.setValueAt     (sImpoDescOri,   jTab.getSelectedRow(), 12);        
                        jTab.setValueAt     (sSerProdOri,    jTab.getSelectedRow(), 13);
                        jTab.setValueAt     (sComenSerOri,   jTab.getSelectedRow(), 14);
                        jTab.setValueAt     (sGaranOri,      jTab.getSelectedRow(), 15);
                        jTab.setValueAt     (sKitOri,        jTab.getSelectedRow(), 16);
                        jTab.setValueAt     (sTallOri,       jTab.getSelectedRow(), 17);
                        jTab.setValueAt     (sColOri,        jTab.getSelectedRow(), 18);
                        jTab.setValueAt     (sBackOri,       jTab.getSelectedRow(), 19);
                        jTab.setValueAt     (sLotOri,        jTab.getSelectedRow(), 20);
                        jTab.setValueAt     (sPedimenOri,    jTab.getSelectedRow(), 21);
                        jTab.setValueAt     (sFCaduOri,      jTab.getSelectedRow(), 22);
                        jTab.setValueAt     (sCodImpOri,     jTab.getSelectedRow(), 23);

                        /*Si no se tiene que modificar la descripción entonces colocala de nuevo*/
                        if(!bModDescrip)
                            jTab.setValueAt(sDescripOri,jTab.getSelectedRow(), 5);
                        
                        /*Obtén la descripción*/
                        String sDescrip     = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        
                        /*Si la descripción es cadena vacia entonces*/
                        if(sDescrip.compareTo("")==0)
                        {
                            /*La descripción sera la original y colocalo en el campo*/
                            sDescrip        = sDescripOri;
                            jTab.setValueAt(sDescrip, jTab.getSelectedRow(), 5);    
                            
                            /*Regresa*/
                            return;
                        }

                        /*Obtiene el descuento*/
                        String sDesc        = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        
                        /*Si el descuento es cadena vacia entonces*/
                        if(sDesc.compareTo("")==0)
                        {
                            /*El descuento será el original y colocalo nuevamente en la celda*/
                            sDesc           = "0";
                            jTab.setValueAt(sDesc, jTab.getSelectedRow(), 7);    
                            
                            /*Regresa*/
                            return;
                        }

                        /*Intenta convertir el descuento en númerico*/
                        try
                        {
                            Double.parseDouble(sDesc);
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Colocar el valor original que tenía el descuento en su lugar*/
                            jTab.setValueAt(sDescOri, jTab.getSelectedRow(), 7);                            

                            /*Recalcula los totales y regresa*/
                            Star.vRecalcTotN(jTab, jTSubTot, jTImpue, jTTot, jTTotDesc);                                                        
                            return;
                        }          

                        /*Convierte a valor absoluto el descuento  introducido, para quitar el negativo en caso de que lo tenga*/
                        sDesc               = Double.toString((Math.abs(Double.parseDouble(sDesc))));                    
                        
                        /*Vuelve a colocar el descuento en la fila con el valor convertido a valor positivo*/
                        jTab.setValueAt     (sDesc, jTab.getSelectedRow(), 7);                        
                        
                        /*Obtiene el valor del impuesto*/
                        String sImpueVal    = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        
                        /*Obtén la cantidad que el usuario ingreso*/
                        String sCant        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        
                        /*Convierte a valor absoluto el número introducido, para quitar el negativo en caso de que lo tenga*/
                        sCant               = Double.toString(Math.abs(Double.parseDouble(sCant)));                    
                        
                        /*Si la cantidad es 0 entonces*/
                        if(Double.parseDouble(sCant)==0)
                        {
                            /*Poner la cantidad original y regresa*/
                            jTab.setValueAt     (sCantOri, jTab.getSelectedRow(), 1);                                                       
                            return;
                        }       
                        
                        /*Vuelve a colocar el valor en la cantidad de la fila con el valor convertido a valor positivo en las dos tablas*/
                        jTab.setValueAt     (sCant, jTab.getSelectedRow(), 1);                        
                        
                        //Abre la base de datos                             
                        Connection  con = Star.conAbrBas(true, false);

                        //Si hubo error entonces regresa
                        if(con==null)
                            return;

                        //Declara variables de la base de datos
                        Statement   st;
                        ResultSet   rs;                        
                        String      sQ; 

                        /*Comprueba si no se debe vender abajo de las existencias*/
                        boolean bSiExistN  = false;
                        try
                        {
                            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vendsinexistpvta'";                        
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos entonces*/
                            if(rs.next())
                            {
                                /*Coloca la banera si esta habilitado*/
                                if(rs.getString("val").compareTo("0")==0)
                                    bSiExistN = true;
                            }
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;
                        }

                        //Obtiene la configuración para saber si mostrar o no el mensaje de existencias negativas
                        int iRes    = Star.iGetConfExistNeg(con);
                        
                        //Si hubo error entonces regresa
                        if(iRes==-1)
                            return;
                        
                        //Si se tiene que mostrar entonces coloca la bandera
                        boolean bSiMsj = false;
                        if(iRes==1)
                            bSiMsj      = true;
                        
                        //Obtiene la existencia general del producto                                                
                        double dExist   = Star.dExistGralProd(con, jTab.getValueAt(jTab.getSelectedRow(), 3).toString());
                        
                        //Conviertelo a string la existencia
                        String sExist   = Double.toString(dExist);
                        
                        /*Obtiene el descuento posible para este usuario*/
                        String sDescI;
                        try
                        {
                            sQ = "SELECT descu, habdesc FROM estacs WHERE estac = '" + Login.sUsrG + "'" ;                        
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos entonces*/
                            if(rs.next())
                            {
                                /*Obtiene el descuento del usuario*/
                                sDescI         = rs.getString("descu");

                                /*Si el descuento esta deshabilitado para este usuario entonces regresa*/
                                if(rs.getString("habdesc").compareTo("0")==0)
                                {                                    
                                    /*Coloca el descuento original que tenía*/
                                    jTab.setValueAt(sDescOri, jTab.getSelectedRow(), 7);

                                    /*El deescuento será igual al que tenía*/
                                    sDesc   = sDescOri;

                                    /*Mensajea*/
                                    JOptionPane.showMessageDialog(null, "El descuento para el usuario: " + Login.sUsrG + " esta deshabilitado.", "Descuento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                                                                                                             
                                }                                   
                                /*Else el descuento esta habilitado para este usuario entonces*/
                                else                                
                                {                                    
                                    /*Si el descuento que se quiere dar es mayor al permitido entonces*/
                                    if(Double.parseDouble(sDesc) > Double.parseDouble(sDescI))
                                    {
                                        /*Coloca el descuento original que tenía*/
                                        jTab.setValueAt(sDescOri, jTab.getSelectedRow(), 7);

                                        /*El deescuento será igual al que tenía*/
                                        sDesc   = sDescOri;
                                        
                                        /*Mensajea*/
                                        JOptionPane.showMessageDialog(null, "El descuento máximo permitido para el usuario: " + Login.sUsrG + " es: " + sDescI + "%.", "Descuento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                                                                         
                                    }
                                }                                    
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

                        /*Si la cantidad es mayor a la existencia y si se tiene que mostrar el mensaje entonces*/
                        if(Double.parseDouble(sCant) > Double.parseDouble(sExist))
                        {      
                            /*Si se tiene que mensajear entonces*/
                            if(bSiMsj)
                                JOptionPane.showMessageDialog(null, "No hay existencias suficientes para este producto.", "Existencia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                            /*Si no se puede vender con exist baja entonces*/
                            if(bSiExistN)
                            {
                                /*Mensajea*/
                                JOptionPane.showMessageDialog(null, "No se puede vender sin existencias.", "Existencias", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                                /*Poner la cantidad original en las dos tablas en la parte de la cantidad y regresa*/
                                jTab.setValueAt     (sCantOri, jTab.getSelectedRow(), 1);                                                              
                                return;                    
                            }                            
                        }

                        /*Obtén el precio*/
                        String sPre     = jTab.getValueAt(jTab.getSelectedRow(), 6).toString().replace("$", "").replace(",", "");                        

                        /*Genera el importe*/
                        String sImp     = Double.toString(Double.parseDouble(sCant) * Double.parseDouble(sPre));                        
                        
                        /*Genera el importe del descuento*/
                        String sImpDesc = Double.toString((Double.parseDouble(sDesc)/100) * Double.parseDouble(sImp));
                        
                        /*Genera el importe menos el descuento*/
                        sImp            = Double.toString(Double.parseDouble(sImp) - (Double.parseDouble(sImpDesc)));
                        
                        /*Cálcula el importe del impuesto*/
                        String sImpImpue= Double.toString((Double.parseDouble(sImpueVal)/100) * Double.parseDouble(sImp));
                        
                        /*Dale formato de moneda al importe*/
                        double dCant    = Double.parseDouble(sImp);                   
                        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                        sImp            = n.format(dCant);

                        /*Dale formato de mon al precio*/
                        dCant           = Double.parseDouble(sPre);                                           
                        sPre            = n.format(dCant);
                        
                        /*Dale formato de moneda al importe del impuesto*/
                        dCant           = Double.parseDouble(sImpImpue);                                           
                        sImpImpue       = n.format(dCant);
                        
                        /*Dale formato de moneda al importe del descuento*/
                        dCant           = Double.parseDouble(sImpDesc);                                           
                        sImpDesc        = n.format(dCant);
                                                
                        /*Actualiza el importe de la fila*/
                        jTab.setValueAt     (sImp, jTab.getSelectedRow(), 9);                        

                        /*Actualiza el precio en la tabla ya con formato de mon*/
                        jTab.setValueAt     (sPre, jTab.getSelectedRow(), 6);
                        
                        /*Actualiza el importe del impuesto en la tabla ya con formato de moneda*/
                        jTab.setValueAt     (sImpImpue, jTab.getSelectedRow(), 11);
                        
                        /*Actualiza el importe del descuento en la tabla ya con formato de moneda*/
                        jTab.setValueAt     (sImpDesc, jTab.getSelectedRow(), 12);

                        /*Recalcula los totes*/
                        Star.vRecalcTotN(jTab, jTSubTot, jTImpue, jTTot, jTTotDesc);                         

                    }/*Fin de else*/
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla de partidas*/
        jTab.addPropertyChangeListener(pr);
                                 
        /*Si la forma es solo de ver entonces*/
        if(bVer)
        {
            /*Coloca el label de solo ver*/            
            jLNot.setText("Ver cotización: " + sCod);        
            
            /*Desactiva los controles*/
            vDeact();
        }
        
    }/*Fin de public NewCot() */       
    
          
    /*Método para desactivar todos los controles*/
    private void vDeact()
    {
        jTCli.setEnabled        (false);
        jBCli.setEnabled        (false);        
        jTCo1.setEnabled        (false);
        jCCo3.setEnabled        (false);
        jBDel.setEnabled        (false);
        jBNew.setEnabled        (false);
        jTCo2.setEnabled        (false);
        jTCo3.setEnabled        (false);
        jCCo1.setEnabled        (false);
        jCCo2.setEnabled        (false);
        jCGuaCo.setEnabled      (false);        
        jDFVenc.setEnabled      (false);
        jDFEnt.setEnabled       (false);
        jTAObserv.setEnabled    (false);
        jTADescrip.setEnabled   (false);
        jCMostA.setEnabled      (false);
        jCImp.setEnabled        (false);
        jCMand.setEnabled       (false);
        jComMon.setEnabled      (false);
        jPanImg.setEnabled      (false);
        jComUnid.setEnabled     (false);
        jTCant.setEnabled       (false);
        jBProd.setEnabled       (false);
        jTProd.setEnabled       (false);
        jTDescrip.setEnabled    (false);
        jTPre.setEnabled        (false);
        jTList.setEnabled       (false);
        jBList.setEnabled       (false);
        jTDesc.setEnabled       (false);
        jBExisAlma.setEnabled   (false);
        jComSer.setEnabled      (false);
        jComAlma.setEnabled     (false);
        jComImp.setEnabled      (false);
        jComTall.setEnabled     (false);
        jComColo.setEnabled     (false);
        jTSerProd.setEnabled    (false);
        jBComenSer.setEnabled   (false);
        jDBack.setEnabled       (false);
        jCBack.setEnabled       (false);
        jTab.setEnabled         (false);
        jBVeGran.setEnabled     (false);
        jBGranDescrip.setEnabled(false);
        jBTipCam.setEnabled        (false);        
        jBGuar.setEnabled       (false);
        jBTod.setEnabled       (false);                
    }
    
    
    /*Carga todos los datos de la cotización machote en los controles*/
    private void vCargT(Connection con, String sCod)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Contiene el código del cliente*/
        String sCli = "";
        
        /*Obtiene el encabezado de la cotización*/
        String sNoSer   = "";
        try
        {
            sQ = "SELECT cots.FENTRE, cots.TOTDESCU, cots.MON, cots.FVENC, cots.DESCRIP, cots.NOSER, cots.OBSERV, cots.CODEMP, cots.SER, cots.CODEMP, tot, subtot, impue FROM cots WHERE codcot = '" + sCod + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene los totales*/
                String sTot     = rs.getString("tot");
                String sSubTot  = rs.getString("subtot");                
                String sImpue   = rs.getString("impue");
                String sTotDesc = rs.getString("totdescu");
                
                /*Coloca la moneda*/
                jComMon.setSelectedItem(rs.getString("mon"));
                
                /*Obtiene el código del cliente*/
                sCli            = rs.getString("codemp");
                
                /*Dale formato de moneda a los totales*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sSubTot);                
                sSubTot         = n.format(dCant);
                dCant           = Double.parseDouble(sImpue);                
                sImpue          = n.format(dCant);
                dCant           = Double.parseDouble(sTot);                
                sTot            = n.format(dCant);
                dCant           = Double.parseDouble(sTotDesc);                
                sTotDesc        = n.format(dCant);
                
                /*Coloca el total del descuento*/
                jTTotDesc.setText(sTotDesc);
                
                /*Coloca la fecha de vencimiento*/
                try
                {
                    SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");                
                    java.util.Date dtDat    = sdf.parse(rs.getString("fvenc"));
                    jDFVenc.setDate(dtDat);
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }
                catch(ParseException expnPARS)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnPARS.getMessage(), Star.sErrPARS, expnPARS.getStackTrace(), con);                    
                    return;
                }
                                    
                /*Coloca la fecha de entrega*/
                try
                {
                    SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");                
                    java.util.Date dtDat    = sdf.parse(rs.getString("fentre"));
                    jDFEnt.setDate(dtDat);
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }
                catch(ParseException expnPARS)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnPARS.getMessage(), Star.sErrPARS, expnPARS.getStackTrace(), con);                                
                    return;
                }
                
                /*Coloca los totales en el control*/
                jTTot.setText           (sTot);
                jTImpue.setText         (sImpue);
               jTSubTot.setText        (sSubTot);
                
                /*Lee todos los campos y colocalos en los controles*/
                jTCli.setText           (rs.getString("cots.CODEMP"));
                jTSer.setText           (rs.getString("cots.SER"));                
                jTAObserv.setText       (rs.getString("cots.OBSERV"));
                jTADescrip.setText      (rs.getString("cots.DESCRIP"));
                
                /*Obtiene la serie de la cotización*/
                sNoSer                  = rs.getString("noser");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Obtiene la descripción de la serie de la cotización*/
        try
        {
            sQ = "SELECT ser FROM consecs WHERE tip = 'COT' AND ser = '" + sNoSer + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces seleccionalo en el combobox por descripción*/
            if(rs.next())                       
                jComSer.setSelectedItem(rs.getString("ser"));                           
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Obtiene los datos del cliente*/
        try
        {
            sQ = "SELECT co1, co2, co3, nom FROM emps WHERE CONCAT_WS('', ser, codemp )= '" + sCli + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca los valores en los controles*/
            if(rs.next())                       
            {
                jTCo1.setText(rs.getString("co1"));
                jTCo2.setText(rs.getString("co2"));
                jTCo3.setText(rs.getString("co3"));
                jTNom.setText(rs.getString("nom"));
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Obtiene todas las partidas de la cotización*/
        try
        {
            sQ = "SELECT * FROM partcot WHERE codcot = '" + sCod + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())          
            {
                /*Obtiene los totales*/
                String sPre         = rs.getString("pre");
                String sImpo        = rs.getString("impo");                
                String sImpueImpo   =rs.getString("impueimpo");
                String sImpoDesc    =rs.getString("impodesc");
                
                /*Dale formato de moneda a los totales*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sPre);                
                sPre            = n.format(dCant);
                dCant           = Double.parseDouble(sImpo);                
                sImpo           = n.format(dCant);
                dCant           = Double.parseDouble(sImpueImpo);                
                sImpueImpo      = n.format(dCant);
                
                /*Determina si es un kit o no*/
                String sKit     = "No";
                if(rs.getString("eskit").compareTo("1")==0)
                    sKit        = "Si";
                
                /*Agrega el registro en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFiParts, rs.getString("cant"), rs.getString("prod"), rs.getString("unid"), rs.getString("list"), rs.getString("descrip"), sPre, rs.getString("desc1"), rs.getString("alma"), sImpo, rs.getString("impueval"), sImpueImpo, sImpoDesc, rs.getString("serprod"), rs.getString("comenser"), rs.getString("garan"), sKit, rs.getString("tall"), rs.getString("colo"), rs.getString("fentre"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("fcadu"), rs.getString("codimpue")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas*/
                ++iContFiParts;
            }                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                            
        }
        
    }/*Fin de private void vCargT(con)*/
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBNew = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBGuar = new javax.swing.JButton();
        jPClien = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTNom = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jBCli = new javax.swing.JButton();
        jTCli = new javax.swing.JTextField();
        jTSer = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComSer = new javax.swing.JComboBox();
        jCCo1 = new javax.swing.JCheckBox();
        jTCo1 = new javax.swing.JTextField();
        jCCo2 = new javax.swing.JCheckBox();
        jTCo2 = new javax.swing.JTextField();
        jCCo3 = new javax.swing.JCheckBox();
        jTCo3 = new javax.swing.JTextField();
        jCGuaCo = new javax.swing.JCheckBox();
        jDFVenc = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jDFEnt = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jDFech = new com.toedter.calendar.JDateChooser();
        jBDel = new javax.swing.JButton();
        jTImpue = new javax.swing.JTextField();
        jTSubTot = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTTot = new javax.swing.JTextField();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jPOtr = new javax.swing.JPanel();
        jCMostA = new javax.swing.JCheckBox();
        jCImp = new javax.swing.JCheckBox();
        jCMand = new javax.swing.JCheckBox();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTAObserv = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTADescrip = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jComMon = new javax.swing.JComboBox();
        jPParts = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTProd = new javax.swing.JTextField();
        jBProd = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jComImp = new javax.swing.JComboBox();
        jTPre = new javax.swing.JTextField();
        jTDescrip = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTList = new javax.swing.JTextField();
        jBList = new javax.swing.JButton();
        jTCant = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComUnid = new javax.swing.JComboBox();
        jTUnid = new javax.swing.JTextField();
        jBTipCam = new javax.swing.JButton();
        jTValImp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTExist = new javax.swing.JTextField();
        jSImg = new javax.swing.JScrollPane();
        jPanImg = new javax.swing.JPanel();
        jLImg = new javax.swing.JLabel();
        jBVeGran = new javax.swing.JButton();
        jBGranDescrip = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTDesc = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jTSerProd = new javax.swing.JTextField();
        jBComenSer = new javax.swing.JButton();
        jLabel50 = new javax.swing.JLabel();
        jTKit = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jComTall = new javax.swing.JComboBox();
        jTDescripTall = new javax.swing.JTextField();
        jTDescripColo = new javax.swing.JTextField();
        jComColo = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jDBack = new com.toedter.calendar.JDateChooser();
        jCBack = new javax.swing.JCheckBox();
        jComAlma = new javax.swing.JComboBox();
        jTDescripAlma = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jBExisAlma = new javax.swing.JButton();
        jTAlma2 = new javax.swing.JTextField();
        jTTotDesc = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTComenSer = new javax.swing.JTextField();
        jTGara = new javax.swing.JTextField();
        jTId = new javax.swing.JTextField();
        jTLot = new javax.swing.JTextField();
        jTPedimen = new javax.swing.JTextField();
        jTCadu = new javax.swing.JTextField();
        jTCantLot = new javax.swing.JTextField();
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
        jBSal.setNextFocusableComponent(jTCli);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 520, 120, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Qty", "Producto", "Unidad", "Lista", "Descripción", "Precio", "Desc.%", "Almacén", "Importe", "%Impuesto", "Total Impuesto", "Importe Descuento", "Serie Producto", "Comentario Serie", "Garantía", "Es Kit", "Talla", "Color", "Back Order", "Lote", "Pedimento", "Caducidad", "Cod.Impuesto"
            }
        ));
        jTab.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBGuar);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 1110, 160));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nueva");
        jBNew.setToolTipText("Nueva Partida (Ctrl+N)");
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 110, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Impuesto:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 540, 160, -1));

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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 120, 30));

        jPClien.setBackground(new java.awt.Color(255, 255, 255));
        jPClien.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del Cliente", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPClien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPClienKeyPressed(evt);
            }
        });
        jPClien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setText("Nombre cliente:");
        jPClien.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 120, -1));

        jLabel17.setText("*Código cliente:");
        jPClien.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 110, -1));

        jTNom.setEditable(false);
        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jPClien.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 180, 20));

        jLabel9.setText("Fecha cotización:");
        jPClien.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 135, -1));

        jBCli.setBackground(new java.awt.Color(255, 255, 255));
        jBCli.setText("...");
        jBCli.setToolTipText("Buscar Cliente(s)");
        jBCli.setNextFocusableComponent(jCCo1);
        jBCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCliMouseExited(evt);
            }
        });
        jBCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCliActionPerformed(evt);
            }
        });
        jBCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCliKeyPressed(evt);
            }
        });
        jPClien.add(jBCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 30, 20));

        jTCli.setBackground(new java.awt.Color(204, 255, 204));
        jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCli.setNextFocusableComponent(jBCli);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCliKeyTyped(evt);
            }
        });
        jPClien.add(jTCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 90, 20));

        jTSer.setEditable(false);
        jTSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSerFocusLost(evt);
            }
        });
        jPClien.add(jTSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 60, 20));

        jLabel2.setText("Fecha entrega:");
        jPClien.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 130, -1));

        jComSer.setNextFocusableComponent(jDFVenc);
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
        jPClien.add(jComSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 140, 20));

        jCCo1.setBackground(new java.awt.Color(255, 255, 255));
        jCCo1.setSelected(true);
        jCCo1.setText("Correo 1");
        jCCo1.setNextFocusableComponent(jTCo1);
        jCCo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo1KeyPressed(evt);
            }
        });
        jPClien.add(jCCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo1.setNextFocusableComponent(jCCo2);
        jTCo1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCo1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCo1FocusLost(evt);
            }
        });
        jTCo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCo1KeyPressed(evt);
            }
        });
        jPClien.add(jTCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 180, 20));

        jCCo2.setBackground(new java.awt.Color(255, 255, 255));
        jCCo2.setText("Correo 2");
        jCCo2.setNextFocusableComponent(jTCo2);
        jCCo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo2KeyPressed(evt);
            }
        });
        jPClien.add(jCCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 20));

        jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo2.setNextFocusableComponent(jCCo3);
        jTCo2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCo2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCo2FocusLost(evt);
            }
        });
        jTCo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCo2KeyPressed(evt);
            }
        });
        jPClien.add(jTCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 180, 20));

        jCCo3.setBackground(new java.awt.Color(255, 255, 255));
        jCCo3.setText("Correo 3");
        jCCo3.setNextFocusableComponent(jTCo3);
        jCCo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo3KeyPressed(evt);
            }
        });
        jPClien.add(jCCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

        jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo3.setNextFocusableComponent(jCGuaCo);
        jTCo3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCo3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCo3FocusLost(evt);
            }
        });
        jTCo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCo3KeyPressed(evt);
            }
        });
        jPClien.add(jTCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 180, 20));

        jCGuaCo.setBackground(new java.awt.Color(255, 255, 255));
        jCGuaCo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCGuaCo.setText("Guardar Correos F8");
        jCGuaCo.setNextFocusableComponent(jDFech);
        jCGuaCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCGuaCoActionPerformed(evt);
            }
        });
        jCGuaCo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGuaCoKeyPressed(evt);
            }
        });
        jPClien.add(jCGuaCo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 180, -1));

        jDFVenc.setNextFocusableComponent(jDFEnt);
        jDFVenc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFVencKeyPressed(evt);
            }
        });
        jPClien.add(jDFVenc, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 140, -1));

        jLabel7.setText("*Serie cotización:");
        jPClien.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 130, -1));

        jDFEnt.setNextFocusableComponent(jTAObserv);
        jDFEnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFEntKeyPressed(evt);
            }
        });
        jPClien.add(jDFEnt, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 140, -1));

        jLabel15.setText("Fecha vencimiento:");
        jPClien.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, 130, -1));

        jDFech.setNextFocusableComponent(jComSer);
        jDFech.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFechKeyPressed(evt);
            }
        });
        jPClien.add(jDFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 140, -1));

        jP1.add(jPClien, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 720, 150));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Partida(s) (Ctrl+SUPR)");
        jBDel.setName(""); // NOI18N
        jBDel.setNextFocusableComponent(jTab);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, 100, 20));

        jTImpue.setEditable(false);
        jTImpue.setBackground(new java.awt.Color(204, 255, 204));
        jTImpue.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTImpue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTImpue.setText("$0.00");
        jTImpue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTImpue.setNextFocusableComponent(jBGuar);
        jTImpue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTImpueFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTImpueFocusLost(evt);
            }
        });
        jTImpue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTImpueKeyPressed(evt);
            }
        });
        jP1.add(jTImpue, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 540, 200, 20));

        jTSubTot.setEditable(false);
        jTSubTot.setBackground(new java.awt.Color(204, 255, 204));
        jTSubTot.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTSubTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTSubTot.setText("$0.00");
        jTSubTot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSubTot.setNextFocusableComponent(jBGuar);
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
        jP1.add(jTSubTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 520, 200, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Descuento:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 560, 190, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Sub Total:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 520, 120, -1));

        jTTot.setEditable(false);
        jTTot.setBackground(new java.awt.Color(204, 255, 204));
        jTTot.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTot.setText("$0.00");
        jTTot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTot.setNextFocusableComponent(jBGuar);
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
        jP1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 580, 200, 20));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, 230, 20));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 342, 130, 18));

        jPOtr.setBackground(new java.awt.Color(255, 255, 255));
        jPOtr.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Encabezado", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPOtr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPOtrKeyPressed(evt);
            }
        });
        jPOtr.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCMostA.setBackground(new java.awt.Color(255, 255, 255));
        jCMostA.setText("Mostrar archivos ");
        jCMostA.setNextFocusableComponent(jCImp);
        jCMostA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMostAKeyPressed(evt);
            }
        });
        jPOtr.add(jCMostA, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 130, 20));

        jCImp.setBackground(new java.awt.Color(255, 255, 255));
        jCImp.setText("Imprimir ");
        jCImp.setNextFocusableComponent(jCMand);
        jCImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCImpKeyPressed(evt);
            }
        });
        jPOtr.add(jCImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 130, -1));

        jCMand.setBackground(new java.awt.Color(255, 255, 255));
        jCMand.setSelected(true);
        jCMand.setText("Mandar correo");
        jCMand.setNextFocusableComponent(jComMon);
        jCMand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMandKeyPressed(evt);
            }
        });
        jPOtr.add(jCMand, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 130, -1));

        jLabel29.setForeground(new java.awt.Color(51, 0, 255));
        jLabel29.setText("F4");
        jPOtr.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 30, -1));

        jLabel30.setForeground(new java.awt.Color(51, 0, 255));
        jLabel30.setText("F5");
        jPOtr.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 30, 20));

        jLabel28.setForeground(new java.awt.Color(51, 0, 255));
        jLabel28.setText("F6");
        jPOtr.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 30, 20));

        jLabel20.setText("Observaciones de la cotización:");
        jPOtr.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 200, -1));

        jScrollPane4.setNextFocusableComponent(jComSer);

        jTAObserv.setColumns(20);
        jTAObserv.setLineWrap(true);
        jTAObserv.setRows(5);
        jTAObserv.setNextFocusableComponent(jTADescrip);
        jTAObserv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAObservFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAObservFocusLost(evt);
            }
        });
        jTAObserv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAObservKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTAObserv);

        jPOtr.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 200, 40));

        jLabel19.setText("Descripción cotización:");
        jPOtr.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 200, -1));

        jTADescrip.setColumns(20);
        jTADescrip.setLineWrap(true);
        jTADescrip.setRows(5);
        jTADescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTADescrip.setNextFocusableComponent(jCMostA);
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
        jScrollPane3.setViewportView(jTADescrip);

        jPOtr.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 200, 40));

        jLabel11.setText("Cod. moneda:");
        jPOtr.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 100, -1));

        jComMon.setName(""); // NOI18N
        jComMon.setNextFocusableComponent(jTProd);
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
        jPOtr.add(jComMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 140, 20));

        jP1.add(jPOtr, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, 370, 150));

        jPParts.setBackground(new java.awt.Color(255, 255, 255));
        jPParts.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del Producto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPParts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPPartsKeyPressed(evt);
            }
        });
        jPParts.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setText("Producto:");
        jPParts.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, -1));

        jTProd.setBackground(new java.awt.Color(255, 255, 153));
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
        jPParts.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 170, 20));

        jBProd.setBackground(new java.awt.Color(255, 255, 255));
        jBProd.setText("...");
        jBProd.setToolTipText("Buscar Producto(s)");
        jBProd.setName(""); // NOI18N
        jBProd.setNextFocusableComponent(jTCant);
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
        jPParts.add(jBProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 30, 20));

        jLabel13.setText("Descripción:");
        jPParts.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, -1));

        jLabel18.setText("Almacén:");
        jPParts.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, -1));

        jLabel21.setText("Cod. impuesto:");
        jPParts.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 100, -1));

        jComImp.setName(""); // NOI18N
        jComImp.setNextFocusableComponent(jTValImp);
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
        jPParts.add(jComImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 70, 20));

        jTPre.setText("$0.00");
        jTPre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre.setNextFocusableComponent(jTList);
        jTPre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPreFocusLost(evt);
            }
        });
        jTPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPreKeyTyped(evt);
            }
        });
        jPParts.add(jTPre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 70, 20));

        jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescrip.setNextFocusableComponent(jTPre);
        jTDescrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripFocusLost(evt);
            }
        });
        jTDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripKeyPressed(evt);
            }
        });
        jPParts.add(jTDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 630, 20));

        jLabel8.setText("L.Precio:");
        jPParts.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 70, -1));

        jTList.setText("1");
        jTList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTList.setNextFocusableComponent(jBList);
        jTList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTListFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTListFocusLost(evt);
            }
        });
        jTList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTListKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTListKeyTyped(evt);
            }
        });
        jPParts.add(jTList, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 60, 20));

        jBList.setBackground(new java.awt.Color(255, 255, 255));
        jBList.setText("jButton1");
        jBList.setToolTipText("Buscar Listas de Precio");
        jBList.setNextFocusableComponent(jTDesc);
        jBList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBListMouseExited(evt);
            }
        });
        jBList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBListActionPerformed(evt);
            }
        });
        jBList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBListKeyPressed(evt);
            }
        });
        jPParts.add(jBList, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 30, 20));

        jTCant.setText("1.0");
        jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCant.setNextFocusableComponent(jComUnid);
        jTCant.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCantFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCantFocusLost(evt);
            }
        });
        jTCant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCantKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCantKeyTyped(evt);
            }
        });
        jPParts.add(jTCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 60, 20));

        jLabel22.setText("Cantidad:");
        jPParts.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 60, -1));

        jLabel6.setText("%Desc:");
        jPParts.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 50, -1));

        jComUnid.setNextFocusableComponent(jTUnid);
        jComUnid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComUnidActionPerformed(evt);
            }
        });
        jComUnid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComUnidKeyPressed(evt);
            }
        });
        jPParts.add(jComUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 70, 20));

        jTUnid.setEditable(false);
        jTUnid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUnid.setNextFocusableComponent(jTExist);
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
        jPParts.add(jTUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, 80, 20));

        jBTipCam.setBackground(new java.awt.Color(255, 255, 255));
        jBTipCam.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBTipCam.setForeground(new java.awt.Color(0, 102, 0));
        jBTipCam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dol.png"))); // NOI18N
        jBTipCam.setToolTipText("Definir el Tipo de Cambio del Día (F7)");
        jBTipCam.setNextFocusableComponent(jComColo);
        jBTipCam.setOpaque(false);
        jBTipCam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBTipCamMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBTipCamMouseExited(evt);
            }
        });
        jBTipCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTipCamActionPerformed(evt);
            }
        });
        jBTipCam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTipCamKeyPressed(evt);
            }
        });
        jPParts.add(jBTipCam, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 130, 40, 40));

        jTValImp.setEditable(false);
        jTValImp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTValImp.setNextFocusableComponent(jTKit);
        jTValImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTValImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTValImpFocusLost(evt);
            }
        });
        jTValImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTValImpKeyPressed(evt);
            }
        });
        jPParts.add(jTValImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 170, 20));

        jLabel3.setText("Gral.Existencia:");
        jPParts.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 90, -1));

        jTExist.setEditable(false);
        jTExist.setForeground(new java.awt.Color(51, 51, 255));
        jTExist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTExist.setNextFocusableComponent(jTDescrip);
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
        jPParts.add(jTExist, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, 90, 20));

        jSImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSImgKeyPressed(evt);
            }
        });

        jPanImg.setBackground(new java.awt.Color(255, 255, 204));
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
                .addGap(0, 258, Short.MAX_VALUE))
        );
        jPanImgLayout.setVerticalGroup(
            jPanImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanImgLayout.createSequentialGroup()
                .addComponent(jLImg)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        jSImg.setViewportView(jPanImg);

        jPParts.add(jSImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, 260, -1));

        jBVeGran.setBackground(new java.awt.Color(255, 255, 255));
        jBVeGran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBVeGran.setToolTipText("Ver Imágen  de Producto Completa");
        jBVeGran.setNextFocusableComponent(jBNew);
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
        jPParts.add(jBVeGran, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 144, 30, 20));

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
        jPParts.add(jBGranDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 50, 10, 20));

        jLabel10.setText("Unidad:");
        jPParts.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, 50, -1));

        jTDesc.setText("0");
        jTDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDesc.setNextFocusableComponent(jComAlma);
        jTDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescFocusLost(evt);
            }
        });
        jTDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDescKeyTyped(evt);
            }
        });
        jPParts.add(jTDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 70, 20));

        jLabel23.setText("Serie:");
        jPParts.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, 100, 20));

        jTSerProd.setToolTipText("Flecha abajo para ver las series existentes");
        jTSerProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSerProd.setNextFocusableComponent(jComImp);
        jTSerProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSerProdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSerProdFocusLost(evt);
            }
        });
        jTSerProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSerProdKeyPressed(evt);
            }
        });
        jPParts.add(jTSerProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, 260, 20));

        jBComenSer.setBackground(new java.awt.Color(0, 153, 153));
        jBComenSer.setToolTipText("Comentario de la Serie");
        jBComenSer.setNextFocusableComponent(jBVeGran);
        jBComenSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBComenSerActionPerformed(evt);
            }
        });
        jBComenSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBComenSerKeyPressed(evt);
            }
        });
        jPParts.add(jBComenSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 90, 10, 20));

        jLabel50.setText("Es Kit:");
        jPParts.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 50, 20));

        jTKit.setEditable(false);
        jTKit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTKit.setNextFocusableComponent(jDBack);
        jTKit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTKitFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTKitFocusLost(evt);
            }
        });
        jPParts.add(jTKit, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, 70, 20));

        jLabel24.setText("Cod. talla:");
        jPParts.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 80, -1));

        jComTall.setNextFocusableComponent(jTDescripTall);
        jComTall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComTallActionPerformed(evt);
            }
        });
        jComTall.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComTallKeyPressed(evt);
            }
        });
        jPParts.add(jComTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 70, 20));

        jTDescripTall.setEditable(false);
        jTDescripTall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripTall.setNextFocusableComponent(jBTipCam);
        jTDescripTall.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripTallFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripTallFocusLost(evt);
            }
        });
        jTDescripTall.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripTallKeyPressed(evt);
            }
        });
        jPParts.add(jTDescripTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, 170, 20));

        jTDescripColo.setEditable(false);
        jTDescripColo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripColo.setNextFocusableComponent(jBVeGran);
        jTDescripColo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripColoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripColoFocusLost(evt);
            }
        });
        jTDescripColo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripColoKeyPressed(evt);
            }
        });
        jPParts.add(jTDescripColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 170, 20));

        jComColo.setNextFocusableComponent(jTDescripColo);
        jComColo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComColoActionPerformed(evt);
            }
        });
        jComColo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComColoKeyPressed(evt);
            }
        });
        jPParts.add(jComColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 70, 20));

        jLabel25.setText("Cod. color:");
        jPParts.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 80, -1));

        jDBack.setNextFocusableComponent(jCBack);
        jDBack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDBackKeyPressed(evt);
            }
        });
        jPParts.add(jDBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, 120, -1));

        jCBack.setBackground(new java.awt.Color(255, 255, 255));
        jCBack.setText("Backorder");
        jCBack.setNextFocusableComponent(jComTall);
        jCBack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCBackKeyPressed(evt);
            }
        });
        jPParts.add(jCBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 130, 120, -1));

        jComAlma.setNextFocusableComponent(jTDescripAlma);
        jComAlma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComAlmaFocusLost(evt);
            }
        });
        jComAlma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComAlmaActionPerformed(evt);
            }
        });
        jComAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComAlmaKeyPressed(evt);
            }
        });
        jPParts.add(jComAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 70, 20));

        jTDescripAlma.setEditable(false);
        jTDescripAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripAlma.setNextFocusableComponent(jTKit);
        jTDescripAlma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripAlmaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripAlmaFocusLost(evt);
            }
        });
        jTDescripAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripAlmaKeyPressed(evt);
            }
        });
        jPParts.add(jTDescripAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 160, 20));

        jLabel27.setText("Precio:");
        jPParts.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 50, -1));

        jBExisAlma.setBackground(new java.awt.Color(0, 153, 153));
        jBExisAlma.setToolTipText("Existencias por almacén del producto");
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
        jPParts.add(jBExisAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 10, 20));

        jP1.add(jPParts, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 1110, 176));

        jTAlma2.setEditable(false);
        jTAlma2.setFocusable(false);
        jP1.add(jTAlma2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 570, 10, -1));

        jTTotDesc.setBackground(new java.awt.Color(204, 255, 204));
        jTTotDesc.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTTotDesc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTotDesc.setText("$0.00");
        jTTotDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTotDesc.setNextFocusableComponent(jBGuar);
        jTTotDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTotDescFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTotDescFocusLost(evt);
            }
        });
        jTTotDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTotDescKeyPressed(evt);
            }
        });
        jP1.add(jTTotDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 560, 200, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Total:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 580, 190, 20));

        jTComenSer.setEditable(false);
        jTComenSer.setFocusable(false);
        jTComenSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTComenSerActionPerformed(evt);
            }
        });
        jP1.add(jTComenSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 0, 20, 10));

        jTGara.setEditable(false);
        jTGara.setFocusable(false);
        jP1.add(jTGara, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 570, 10, -1));

        jTId.setEditable(false);
        jTId.setFocusable(false);
        jP1.add(jTId, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 570, 10, -1));

        jTLot.setEditable(false);
        jTLot.setFocusable(false);
        jP1.add(jTLot, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 570, 10, -1));

        jTPedimen.setEditable(false);
        jTPedimen.setFocusable(false);
        jP1.add(jTPedimen, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 570, 10, -1));

        jTCadu.setEditable(false);
        jTCadu.setFocusable(false);
        jP1.add(jTCadu, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 570, 10, -1));

        jTCantLot.setEditable(false);
        jTCantLot.setText("0");
        jTCantLot.setFocusable(false);
        jP1.add(jTCantLot, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 570, 10, -1));

        jLNot.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLNot.setForeground(new java.awt.Color(204, 0, 0));
        jLNot.setFocusable(false);
        jP1.add(jLNot, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 520, 440, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 1141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
           
    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
        /*Pregunta al usr si esta seguro de abandonar la cotización*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir de la cotización?", "Salir de cotización", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;            
        
        /*Cierra el formulario*/
        this.dispose();        
        Star.gCot   = null;
               
        /*Llama al recolector de basura*/
        System.gc();
        
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

    
    /*Cuando se presiona una tecla en el campo de edición de descuento*/
    private void jTCantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCantKeyPressed

    
    /*Cuando se presiona una tecla en la tabla de cotización*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Función para agregar en la tabla de partidas*/
    private void vAgrParts()
    {                                                       
        /*Si no a seleccionado un cliente entonces*/
        if(jTCli.getText().compareTo("")==0 && jTNom.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente válido.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo y regresa*/
            jTCli.grabFocus();                        
            return;            
        }
        
        /*Si hay cadena vacia en el código del producto no puede continuar*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del código del producto esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo y regresa*/
            jTProd.grabFocus();                        
            return;            
        }
             
        /*Si no ha selecionado un almacén entonces*/
        if(jComAlma.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComAlma.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un almacén.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo regresa*/
            jComAlma.grabFocus();                        
            return;
        }
    
        /*Si el producto es por lote y pedimento entonces*/
        if(Double.parseDouble(jTCantLot.getText().trim())>0)
        {
            /*Si la cantidad que se quiere insertar es mayor a la cantidad del lote permitido entonces*/
            if(Double.parseDouble(jTCant.getText())>Double.parseDouble(jTCantLot.getText().trim()))
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La cantidad de lote a insertar del producto es mayor a la permitida.", "Lote y pedimento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Coloca el borde rojo*/                               
                jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Pon el foco del teclado en el campo de la cantidad y regresa*/
                jTCant.grabFocus();
                return;
            }
            
            /*Recorre toda la tabla de partidas*/
            for(int x = 0; x < jTab.getRowCount(); x++)
            {                
                /*Si en la fila ya esta ese lote entonces*/
                if(jTab.getValueAt(x, 18).toString().compareTo(jTId.getText().trim())==0)
                {
                    /*Obtiene la diferencia de lo que ya esta cargado y lo que se quiere cargar*/
                    String sDif = Double.toString(Double.parseDouble(jTCant.getText().trim()) + Double.parseDouble(jTab.getValueAt(x, 1).toString()));
                    
                    /*Si la diferencia es mayor que lo que se quiere insertar entonces*/
                    if(Double.parseDouble(sDif)>Double.parseDouble(jTCantLot.getText().trim()))
                    {
                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "La cantidad de lote a insertar: " + jTCant.getText().trim() + " mas la cantidad ya cargada: " + jTab.getValueAt(x, 2).toString() + " del producto es mayor a la permitida: " + jTCantLot.getText().trim(), "Lote y pedimento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                        /*Coloca el borde rojo*/                               
                        jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                        /*Pon el foco del teclado en el campo de la cantidad y regresa*/
                        jTCant.grabFocus();
                        return;                                                
                    }
                }
                
            }/*Fin de for(int x = 0; row < jTab.getRowCount(); row++)*/
            
        }/*Fin de if(Double.parseDouble(sCantOriLot)>0)*/
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Si el producto necesita a fuerzas serie entonces
        if(Star.iProdSolSer(con, jTProd.getText().trim())==1)
        {
            //Si la serie es cadena vacia entonces
            if(jTSerProd.getText().trim().compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                //Mensajea
                JOptionPane.showMessageDialog(null, "El producto solicita número de serie.", "Número serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Poner el foco del teclado en el campo de la serie
                jTSerProd.grabFocus();
                
                //Coloca el borde rojo y egresa
                jTSerProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                return;
            }            
        }

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Comprueba si el producto requiere lote y pedimento*/
        String sLot = "";
        try
        {
            sQ = "SELECT lotped FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sLot      = rs.getString("lotped");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }        
        
        /*Si el producto requiere lote y pedimento entonces*/
        if(Integer.parseInt(sLot)==1)
        {
            /*Si no se a ingresado por lo menos un lote o pedimento entonces*/            
            if(jTLot.getText().trim().compareTo("")==0 && jTPedimen.getText().trim().compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El producto requiere de lote o pedimento. Ingresa alguno de estos datos de la forma de lotes y pedimentos.", "Lote y pedimetno", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Coloca el borde rojo*/
                jTLot.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Coloca el foco del teclado en el contorl y regresa*/
                jTLot.grabFocus();
                return;
            }
        }
        
        /*Obtiene si se tiene que agregar la garantía en la descripción del producto*/
        boolean bGara   = false;
	try
        {                  
            sQ = "SELECT val FROM confgral WHERE clasif = 'cots' AND conf = 'garadesccot'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si se tiene que agregar en la descripción entonces coloca la bandera*/
                if(rs.getInt("val")==1)
                    bGara   = true;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Comprueba si no se debe vender abajo de las existencias*/
        boolean bSiExistN  = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vendsinexistpvta'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca la banera si esta habilitado*/
                if(rs.getString("val").compareTo("0")==0)
                    bSiExistN = true;
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        //Obtiene la configuración para saber si mostrar o no el mensaje de existencias negativas
        int iRes    = Star.iGetConfExistNeg(con);

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si se tiene que mostrar entonces coloca la bandera
        boolean bSiMsj = false;
        if(iRes==1)
            bSiMsj      = true;

        /*Si el producto no es válido entonces*/        
        if(jTDescrip.getText().compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa un producto válido.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTProd.grabFocus();               
            return;                                                
        }
                
        /*Lee el costo*/
        String sCost       = jTPre.getText().replace("$", "").replace(",", "");
        
        /*Si el costo es cadena vacia entonces*/
        if(sCost.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Coloca el borde rojo*/                               
            jTPre.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No se a definido costo para este producto.", "Agregar partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTPre.grabFocus();
            return; 
        }
        
        /*Lee el precio*/
        String sPre         = jTPre.getText().replace("$", "").replace(",", "");
        
        /*Si el precio es 0 entonces*/
        if(Double.parseDouble(sPre)==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Coloca el borde rojo*/                               
            jTPre.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No se a definido precio para este producto.", "Agregar partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTPre.grabFocus();            
            return;
        }
        
        /*Si no a seleccionado unidad entonces*/
        if(jComUnid.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComUnid.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una unidad.", "Selección", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el combobox de unids y regresa*/
            jComUnid.grabFocus();                        
            return;
        }
        
        /*Lee la cantidad*/
        String sCant           = jTCant.getText();
        
        /*Si la cantidad es 0 o cadena vacia entonces*/
        if(sCant.compareTo("0")== 0 || sCant.compareTo("")== 0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Coloca el borde rojo*/                               
            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Escribe una cantidad valida.", "Selección", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el campo de cant y regresa*/
            jTCant.grabFocus();                        
            return;
        }
        
        /*Lee la existencia*/
        String sExist          = jTExist.getText();
        
        /*Si la cant es mayor a la exist y si se tiene que mostrar el mensaje entonces*/
        if(Double.parseDouble(sCant) > Double.parseDouble(sExist))
        {    
            /*Mensajea para que el usuario este enterado y si el producto no es de kit por que si es kit no debe de validar esto*/                                
            if(bSiMsj)            
                JOptionPane.showMessageDialog(null, "No hay existencias suficientes para este producto.", "Existencia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
            /*Si no se puede vender con exist baja entonces*/
            if(bSiExistN)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No se puede vender sin existencias.", "Existencias", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                return;                    
            }
        }
        
        /*Si el campo de descripción esta vacio no puede continuar*/
        if(jTDescrip.getText().compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Coloca el borde rojo*/                               
            jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de descripción esta vacio.", "Agregar partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de edición y regresa*/
            jTDescrip.grabFocus();           
            return;            
        }                
                        
        /*Si es un kit entonces*/
        if(jTKit.getText().compareTo("Si")==0)
        {
            //Obtiene si el producto tiene componentes
            double dRes     = Star.dGetCompsProd(con, jTProd.getText().trim());
            
            //Si hubo error entonces regresa
            if(dRes==-1)
                return;

            //Si tiene componentes entonces coloca la bandera
            boolean bSiHay  = false;
            if(dRes>0)
                bSiHay      = true;
            
            /*Si no tiene componentes entonces*/
            if(!bSiHay)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No tiene componentes este kit y no se puede cargar.", "Kits", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
                /*Coloca el foco del teclado en el campo del código del producto y regresa*/
                jTProd.grabFocus();                                
                return;
            }        
            
        }/*Fin de if(sKit.compareTo("Si")==0)*/	                                                            
        
        /*Lee el descuento*/
        String sDesc                = jTDesc.getText();
        
        /*Si el descuento es cadena vacia entonces que sea 0*/
        if(sDesc.compareTo("")==0)                                
            sDesc                   = "0";
                        
        /*Obtiene el descuento posible para este usuario*/
        String sDescI;
        try
        {
            sQ = "SELECT descu, habdesc FROM estacs WHERE estac = '" + Login.sUsrG + "'" ;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene el descuento del usuario*/
                sDescI         = rs.getString("descu");

                /*Si el descuento esta deshabilitado para este usuario entonces*/
                if(rs.getString("habdesc").compareTo("0")==0 && Double.parseDouble(sDesc)>0)
                {       
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;
                    
                    /*Coloca en el campo del descuento 0*/
                    jTDesc.setText("0");

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "El descuento para el usuario: " + Login.sUsrG + " esta deshabilitado.", "Descuento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                                                                                                             
                    return;
                }                                   
                /*Else el descuento esta habilitado para este usuario entonces*/
                else                                
                {                                    
                    /*Si el descuento que se quiere dar es mayor al permitido entonces*/
                    if(Double.parseDouble(sDesc) > Double.parseDouble(sDescI))
                    {
                        //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return;

                        /*Coloca en el campo del descuento el descuento permitido*/
                        jTDesc.setText(sDescI);
                        
                        /*Mensajea y regresa*/
                        JOptionPane.showMessageDialog(null, "El descuento máximo permitido para el usuario: " + Login.sUsrG + " es: " + sDescI, "%.", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                                                                         
                        return;
                    }
                }                                    
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
        
        /*Lee el valor del impuesto*/
        String sImpue          = jTValImp.getText();
        
        /*Si es cadena vacia entonces que sea 0*/
        if(sImpue.compareTo("")==0)
            sImpue              = "0";
                      
        /*Si se tiene que manejar backorder entonces*/
        String sBack    = "";
        if(jCBack.isSelected())
        {
            /*Obtiene la fecha de back order*/
            Date fe                 =  jDBack.getDate();
            SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");
            sBack                   = sdf.format(fe);      
        }                
        
        /*Si se va a insertar una serie entonces*/
        if(jTSerProd.getText().compareTo("")!=0)
        {            
            /*Si la tabla de partidas no tiene partidas entonces*/
            if(jTab.getRowCount()==0)
            {
                /*Obtiene la existencia de esa serie actual*/
            String sExistSer   = "0";
                try
                {
                    sQ  = "SELECT IFNULL(exist,0) AS exist FROM serieprod WHERE ser = '" + jTSerProd.getText().trim() + "'";
                    st  = con.createStatement();
                    rs  = st.executeQuery(sQ);
                    /*Si hay datos entonces obtiene el resultado*/
                    if(rs.next())
                        sExistSer          = rs.getString("exist");
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }

                /*Si la cantidad que se quiere insertar es mayor a la permitida de existencia entonces*/
                if(Double.parseDouble(jTCant.getText())>Double.parseDouble(sExistSer))
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "No se pueden insertar mas cantidades para la serie del producto ya que no alcanzan las existencias.", "Series Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                    /*Coloca el borde rojo*/                               
                    jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                    /*Coloca el foco del teclado en el campo de la cantidad y regresa*/
                    jTCant.grabFocus();                        
                    return;
                }

            }/*Fin de if(jTab.getRowCount()==0)*/
            else
            {
                /*Recorre la tabla de partidas para búscar si esta esta serie ya cargada*/                               
                for(int row = 0; row < jTab.getRowCount(); row++)
                {
                    /*Si ya existe serie en la fila entonces*/            
                    if(jTab.getValueAt(row, 13).toString().compareTo(jTSerProd.getText())==0)
                    {
                        /*Obtiene la existencia de esa serie actual*/
                        String sExistSer   = "";
                        try
                        {
                            sQ  = "SELECT exist FROM serieprod WHERE ser = '" + jTSerProd.getText().trim() + "'";
                            st  = con.createStatement();
                            rs  = st.executeQuery(sQ);
                            /*Si hay datos entonces obtiene el resultado*/
                            if(rs.next())
                                sExistSer          = rs.getString("exist");
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;
                        }

                        /*Crea la cantidad correcta que se puede insertar*/
                        String sCantCo  = Double.toString(Double.parseDouble(jTCant.getText()) + Double.parseDouble(jTab.getValueAt(row, 1).toString()));

                        /*Si la cantidad que se quiere insertar es mayor a la permitida de existencia entonces*/
                        if(Double.parseDouble(sCantCo)>Double.parseDouble(sExistSer))
                        {
                            //Cierra la base de datos
                            if(Star.iCierrBas(con)==-1)
                                return;

                            /*Mensajea*/
                            JOptionPane.showMessageDialog(null, "No se pueden insertar mas cantidades para la serie del producto ya que no alcanzan las existencias.", "Series Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                            /*Coloca el borde rojo*/                               
                            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                            /*Coloca el foco del teclado en el campo de la cantidad y regresa*/
                            jTCant.grabFocus();                        
                            return;
                        }
                        
                    }/*Fin de if(jTab.getValueAt(row, 12).toString().compareTo(jTSerProd.getText())==0)*/

                }/*Fin de for( int row = 0; row < jTab.getRowCount(); row++)*/
                
            }/*Fin de else*/                            
            
        }/*Fin de if(jTSerProd.getText().compareTo("")!=0)*/
        
        //Declara variables locales
        boolean bSi     = false;
        int row         = 0;        
        
        /*Recorre toda la tabla de partidas*/        
        for( ; row < jTab.getRowCount(); row++)
        {
            /*Si el código que va a insertar el usuario ya esta en la tabla entonces*/
            if(jTab.getValueAt(row, 2).toString().trim().compareTo(jTProd.getText().trim())==0 && jTab.getValueAt(row, 3).toString().trim().compareTo(jComUnid.getSelectedItem().toString().trim())==0 && jTab.getValueAt(row, 4).toString().trim().compareTo(jTList.getText().trim())==0 && jTab.getValueAt(row, 5).toString().trim().compareTo(jTDescrip.getText().trim())==0 && Double.parseDouble(jTab.getValueAt(row, 6).toString().trim().replace("$", "").replace(",", ""))==Double.parseDouble(sPre.trim()) && sDesc.trim().compareTo(jTab.getValueAt(row, 7).toString().trim())==0 && jTab.getValueAt(row, 8).toString().trim().compareTo(jComAlma.getSelectedItem().toString().trim())==0 && sImpue.trim().compareTo(jTab.getValueAt(row, 10).toString().trim())==0 && jTab.getValueAt(row, 13).toString().trim().compareTo(jTSerProd.getText().trim())==0 && jTab.getValueAt(row, 15).toString().trim().compareTo(jTGara.getText().trim())==0 && jTab.getValueAt(row, 17).toString().trim().compareTo(jComTall.getSelectedItem().toString().trim())==0 && jTab.getValueAt(row, 18).toString().trim().compareTo(jComColo.getSelectedItem().toString().trim())==0 && jTab.getValueAt(row, 19).toString().trim().compareTo(sBack)==0)
            {
                JOptionPane.showMessageDialog(null, "Entro", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                /*Obtiene la cantidad que tiene originalmente en la fila*/
                double dCant    = Double.parseDouble(jTab.getValueAt(row, 1).toString());
                
                /*Deja la cantidad correcta que es la cantidad anterior mas la nueva*/
                sCant           = Double.toString(Double.parseDouble(sCant) + dCant);
                                
                /*Si la cantidad es mayor a la existencia y si se tiene que mostrar el mensaje entonces*/
                if(Double.parseDouble(sCant) > Double.parseDouble(sExist))
                {    
                    /*Mensajea para que el usr este enterado y si el artículo no es de kit por que si es kit no debe de validar esto*/                                
                    if(bSiMsj)
                        JOptionPane.showMessageDialog(null, "No hay existencias suficientes para este producto.", "Existencia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
                    /*Si no se puede vender con exist baja entonces*/
                    if(bSiExistN)
                    {
                        //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return;

                        /*Mensajea y regresa*/
                        JOptionPane.showMessageDialog(null, "No se puede vender sin existencias.", "Existencias", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        return;                    
                    }
                }
                               
                /*Cálcula el importe nuevo*/
                String sImp         = Double.toString(Double.parseDouble(sPre) * Double.parseDouble(sCant));
                
                /*Cálcula el importe del descuento*/
                String sImpoDesc    = Double.toString(Double.parseDouble(sImp) * (Double.parseDouble(sDesc) / 100));
                
                /*Resta el descuento al importe*/
                sImp                = Double.toString(Double.parseDouble(sImp) - Double.parseDouble(sImpoDesc));
                
                /*Cálcula el impuesto del importe*/
                String sImpuImpInt  =Double.toString(Double.parseDouble(sImp) * (Double.parseDouble(sImpue) / 100));
                
                /*Vuelve a darle formato al importe*/
                NumberFormat n      = NumberFormat.getCurrencyInstance(Locale.getDefault());        
                dCant               = Double.parseDouble(sImp);                                                    
                sImp                = n.format(dCant);
                
                /*Dale formaro de moneda al impuesto del importe*/
                dCant               = Double.parseDouble(sImpuImpInt);                                                    
                sImpuImpInt         = n.format(dCant);
        
                /*Dale formato de moneda al impuesto del descuento*/
                dCant               = Double.parseDouble(sImpoDesc);                                                    
                sImpoDesc           = n.format(dCant);
                
                /*Pon la bandera para saber que si existe ya en la tabla*/
                bSi = true;
                
                /*Modifica algunos datos de la fila*/
                jTab.setValueAt(sCant,       row, 1);
                jTab.setValueAt(sImp,        row, 9);
                jTab.setValueAt(sImpuImpInt, row, 11);
                jTab.setValueAt(sImpoDesc,   row, 12);
            
                /*Sal del bucle*/
                break;
            }
            
        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
        
        /*Crea el importe*/
        String sImp     = Double.toString(Double.parseDouble(sPre) * Double.parseDouble(sCant));
                
        /*Obtiene el importe del descuento*/
        String sImpoDesc= Double.toString(Double.parseDouble(sImp) * (Double.parseDouble(sDesc) / 100));
        
        /*Obtén el importe menos el descuento*/
        sImp            = Double.toString(Double.parseDouble(sImp) - (Double.parseDouble(sImpoDesc)));
                
        /*Obtiene el impuesto del importe*/
        String sImpueImp= Double.toString((Double.parseDouble(sImpue)/100) * Double.parseDouble(sImp));
                        
        /*Dale formato de mneda al precio*/
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());        
        double dCant    = Double.parseDouble(sPre);                                           
        sPre            = n.format(dCant);
                
        /*Dale formato de moneda al importe*/
        dCant           = Double.parseDouble(sImp);                                            
        sImp            = n.format(dCant);
        
        /*Dale formato de moneda al importe del descuento*/
        dCant           = Double.parseDouble(sImpoDesc);                                            
        sImpoDesc       = n.format(dCant);
        
        /*Dale formato de moneda al total del impuesto*/                
        dCant           = Double.parseDouble(sImpueImp);                
        sImpueImp       = n.format(dCant);
        
        /*Si el producto no existe en la tabla entonces*/
        if(!bSi)
        {   
            /*Si esta permitida la garantía entonces obtiene la garantia del control*/
            String  sGara   = "";
            if(bGara)
                sGara       = jTGara.getText().trim();

            /*Determina si debe de poner o no el comentario de la serie*/
            String sComenSer    = jTComenSer.getText().trim();
            if(jTSerProd.getText().trim().compareTo("")==0)
                sComenSer       = "";

            /*Si la serie tiene datos entonces*/
            String sSerProd     = jTSerProd.getText().trim();
            if(sSerProd.compareTo("")!=0)
                sSerProd        = "SER:" + sSerProd;
            
            /*Agrega los datos en la tabla*/
            DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
            Object nu[]             = {Integer.toString(iContFiParts), Double.parseDouble(sCant), jTProd.getText().trim(), jComUnid.getSelectedItem().toString().trim(), jTList.getText().trim(), jTDescrip.getText().trim() + " " + sSerProd + " " + sComenSer + " " + jComTall.getSelectedItem().toString().replace("'", "''").trim() + " " + jComColo.getSelectedItem().toString().replace("'", "''").trim() + " " + sGara + " " + sBack, sPre, sDesc, jComAlma.getSelectedItem().toString().trim(), sImp, sImpue, sImpueImp, sImpoDesc, sSerProd, sComenSer, sGara, jTKit.getText().trim(), jComTall.getSelectedItem().toString().replace("'", "''").trim(), jComColo.getSelectedItem().toString().replace("'", "''").trim(), sBack, jTLot.getText().trim(), jTPedimen.getText().trim(), jTCadu.getText().trim(), jComImp.getSelectedItem().toString().trim()};        
            te.addRow(nu);                                    
        }                          
        
        /*Recalcula los totes leyendo toda la tabla de partidas y los campos de subtot materiales y mano de obra para la tabla de partidas*/
        Star.vRecalcTotN(jTab, jTSubTot, jTImpue, jTTot, jTTotDesc);                
        
        /*Función para limpiar los campos del producto*/
        vLimpP();
            
        /*Colcoa el foco del teclado en el campo del producto*/
        jTProd.grabFocus();
        
    }/*Fin de private void vAgreTabParts()*/
    
    
    /*Cuando se presiona el botón de aceptar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed

        /*Función para agregar en la tabla de partidas*/
        vAgrParts();                                
                        
    }//GEN-LAST:event_jBNewActionPerformed
            
    
    /*Cuando se presiona una tecla en el campo de edición de guardar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de las observ de la cotización*/
    private void jTADescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTADescripKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTADescripKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de descuento*/
    private void jTCantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCant.setSelectionStart(0);jTCant.setSelectionEnd(jTCant.getText().length());        
        
    }//GEN-LAST:event_jTCantFocusGained

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de área de observ*/
    private void jTAObservKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAObservKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAObservKeyPressed

            
    /*Función para guardar la cotización normal JPS1*/
    private void vGuaCot()
    {            
        /*Si aún no hay partidas entonces*/
        if(jTab.getRowCount()==0)
        {
            /*Colcoa el foco del teclado en el control del producto*/
            jTProd.grabFocus();
            
            /*Coloca el borde rojo del control de productos*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No existen partidas en la cotización.", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                         
            return;            
        }
        
        /*Obtiene el código de la cliente*/
        String sCodEmp          = jTCli.getText();
        
        /*Si el código de la cliente es cadena vacia entonces*/
        if(sCodEmp.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente válida.", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTCli.grabFocus();
            return;
        }
        
        /*Si el nombre del cliente es cadena vacia entonces*/
        if(jTNom.getText().trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente válido.", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTCli.grabFocus();
            return;
        }
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Si la moneda seleccionada es la vacia entonces*/
        if(jComMon.getSelectedItem().toString().compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Coloca el borde rojo*/                               
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una moneda.", "Moneda", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control*/
            jComMon.grabFocus();                        
            return;
        }
        
        /*Obtiene cuál es la moneda nacional*/
        String sCodMN   = Star.sGetMonNac(con);
        
        //Si hubo error entonces regresa
        if(sCodMN==null)
            return;

        /*Declara variables locales de bases de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene si la configuración global para que no se pueda cotizar a ningún cliente con moneda distinta a la nacional esta activada o no*/
        boolean bSi = true;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'cots' AND conf = 'otramon'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Marca o desmarca la bandera dependiendo del valor*/
                if(rs.getString("val").compareTo("0")==0)
                    bSi = false;                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
                
        /*Comprueba si el cliente tiene o no habilitado que se le pueda cotizar con otra moneda distinta a la nacional*/        
        try
        {
            sQ = "SELECT otramon FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTCli.getText() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Si no tiene habilitado la cotización con otra mon o globalmente entonces*/
                if(rs.getString("otramon").compareTo("0")==0 || !bSi)              
                {
                    /*Compara el código de mon nacional con el que se quiere agregar*/
                    if(sCodMN.compareTo(jComMon.getSelectedItem().toString())!=0)
                    {
                        if(bSi==false)
                        {
                        //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return;
                        /*Coloca el borde rojo*/                               
                        jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "Por configuración del sistema, solo se permiten cotizaciones en Moneda Nacional: " + sCodMN + ".", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
                        /*Coloca el foco del teclado en el control y regresa*/
                        jComMon.grabFocus();
                        return;
                        }else
                        {
                            //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return;
                        /*Coloca el borde rojo*/                               
                        jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "La configuración del cliente no permite cotizar en una moneda distinta a la Moneda Nacional: " + sCodMN + ".", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
                        /*Coloca el foco del teclado en el control y regresa*/
                        jComMon.grabFocus();
                        return;
                        }
                    }
                }                    
            }
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
        
        /*Si no existe la carpeta compartida de la aplicación entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación.", "Carpeta Compartida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));             
            return;
        }
        
        /*Si no a seleccionado una serie para las cots entonces*/
        if(jComSer.getSelectedItem().toString().compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una serie.", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/
            jComSer.grabFocus();
            return;
        }        
       
        /*Comprueba si existe correo dado de alta para el usuario actual*/
        boolean bMandCo  = false;
        try
        {
            sQ = "SELECT portsmtp FROM corrselec WHERE estac = '" + Login.sUsrG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bMandCo  = true;                
            /*Else, no existe entonces*/
            else
            {
                /*Si esta marcado el checkbox de mandar correo entonces mensajea*/
                if(jCMand.isSelected())
                    JOptionPane.showMessageDialog(null, "No se mandara por correo electrónico la cotización ya que no existe correo elecrónico dado de alta para el usuario: " + Login.sUsrG + ".", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                   
        
        /*Si todavía se puede mandar el correo, comprueba si el usuario lo quiere mandar o no*/
        if(!jCMand.isSelected())
        {
            /*Pon la banera para que no se mande y mensajea*/
            bMandCo = false;                        
            JOptionPane.showMessageDialog(null, "La cotización no se mandara por correo por que se habilito la opción.", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
        }
                
        /*Si no selecciono ningún correo para mandar la cotización entonces*/
        if(bMandCo && (!jCCo1.isSelected() && !jCCo2.isSelected() && !jCCo3.isSelected()))
        {
            /*Coloca la bandera en false y mensajea*/
            bMandCo = false;
            JOptionPane.showMessageDialog(null, "La cotización no se mandara a ningún correo.", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
        }
        
        /*Preguntar al usuario si esta seguro de que estan bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar Nueva Cotización", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);        
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);            
            return;                                            
        }
        
        /*Lee el subtotal*/
        String sSubTot          = jTSubTot.getText().replace("$", "").replace(",", "");             
        
        /*Lee el impuesto*/
        String sImpue           = jTImpue.getText().replace("$", "").replace(",", "");     
        
        /*Lee el total*/
        String sTot             = jTTot.getText().replace("$", "").replace(",", "");             
        
        /*Obtiene el símbolo de la moneda*/
        String sSimb    = "";
        try
        {
            sQ = "SELECT simb FROM mons WHERE mon = '" + jComMon.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sSimb   = rs.getString("simb");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }            

        /*Obtiene el total con letra*/
        String sTotLet          = Star.sObLet(sTot, jComMon.getSelectedItem().toString().trim(), sSimb, true);
        
        /*Lee la fecha de vencimiento*/
        Date fe                 =  jDFVenc.getDate();
        SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        String sFVenc           = sdf.format(fe);      
        
        /*Lee la fecha de entrega*/
        fe                      = jDFEnt.getDate();
        String sFEntre          = sdf.format(fe);
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;
        
        /*Si hay que modificar los correos del cliente entonces*/
        if(jCGuaCo.isSelected())
        {
            /*Actualiza los correos del cliente*/
            try 
            {                
                sQ = "UPDATE emps SET "
                        + "co1                                          = '" + jTCo1.getText().replace("'", "''") + "', "
                        + "co2                                          = '" + jTCo2.getText().replace("'", "''") + "', "
                        + "co3                                          = '" + jTCo3.getText().replace("'", "''") + "', "
                        + "sucu                                         = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj                                        = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE CONCAT_WS('', emps.SER, emps.CODEMP )  = '" + jTCli.getText().replace("'", "''") + "'";                                
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
        
        /*Contiene la serie y el consecutivo*/
        String sConsec  = "";        
        String sSer     = "";
        
        /*Obtiene el consecutivo de la cotización el código de la serie*/        
        try
        {
            sQ = "SELECT consec, ser FROM consecs WHERE tip = 'COT' AND ser = '" + jComSer.getSelectedItem().toString() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                
                sConsec         = rs.getString("consec");                                                   
                sSer            = rs.getString("ser");                                                   
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Actualiza el consecutivo de la cotización en la base de datos*/
        try
        {                       
            sQ = "UPDATE consecs SET "
                    + "consec           = consec + 1 "
                    + "WHERE tip        ='COT' AND ser = '" + jComSer.getSelectedItem().toString().replace("'", "''").trim() + "'";                                                
            st = con.createStatement();
            st.executeUpdate(sQ);            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;            
        }
        
        /*Obtiene el tipo de cambio actual*/
        String sTipCam  = "";
        try
        {
            sQ = "SELECT val FROM mons WHERE mon = '" + jComMon.getSelectedItem().toString() + "'";
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
        
        /*Obtiene la fecha de la cotización en final*/
        java.util.Date dtDat        = jDFech.getDate();
        java.text.SimpleDateFormat sdfDat   = new java.text.SimpleDateFormat("yyyy/MM/dd");
        final String sFCotFi        = sdfDat.format(dtDat);
        
        //Inserta en la base de datos la cotización
        if(Star.iInsCots(con, sSer.replace("'", "''") + sConsec.replace("'", "''"), "", sSer.replace("'", "''"), sCodEmp, jTSer.getText().replace("'", "''"), jTAObserv.getText().replace("'", "''"), jTADescrip.getText().replace("'", "''"), "0", "0", "0", sSubTot, sImpue, sTot, "PE", "0", "0", "'" + sFVenc + "'", jTTotDesc.getText().replace("$", "").replace(",", ""), "0", jComMon.getSelectedItem().toString().trim(), sTipCam, "'" + sFEntre + "'", "'" + sFCotFi + "'")==-1)
            return;
            
        /*Recorre la tabla de partidas*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Si tiene backorder entonces*/
            String sBack                = "now()";            
            if(jTab.getValueAt(x, 19).toString().compareTo("")!=0)
            {
                /*Obtiene la fecha de back order*/
                fe                      =  jDBack.getDate();
                sdf                     = new SimpleDateFormat("yyy-MM-dd");
                sBack                   = "'" +  sdf.format(fe) + "'";                                                                      
            }
                        
            /*Si el producto es un kit entonces coloca la bandera*/                 
            String sKit         = "0";
            if(jTab.getValueAt(x, 16).toString().compareTo("Si")==0)            
                sKit            = "1";                            
            
            /*Inicialmente la fecha de caducidad será de hoy*/
            String sFCadu       = "'" + jTab.getValueAt(x, 22).toString().trim() + "'";
            if(jTab.getValueAt(x, 22).toString().trim().compareTo("")==0)
                sFCadu          = "NOW()";
            
            /*Inserta en la base de datos la partida de la cotización*/
            try 
            {                
                sQ = "INSERT INTO partcot( codcot,                                                              prod,                                                               unid,                                                              cant,                                          descrip,                                                                               pre,                                                                              impo,                                                                             alma,                                                                 desc1,                                      impueval,                                   impueimpo,                                                                pre2,          impo2,      impueimpo2,  desc2,      desc3,  desc4,  desc5, impodesc,                                                                     serprod,                                               comenser,                                                  tipcam,    list,                                                               eskit,         tall,                                                                      colo,                                                                 fentre,              cost,   lot,                                                pedimen,                                           fcadu,      mon,          codimpue) " + 
                        "VALUES('" +       sSer.replace("'", "''") + sConsec.replace("'", "''") + "','" +       jTab.getValueAt(x, 2).toString().replace("'", "''") + "','" +       jTab.getValueAt(x, 3).toString().replace("'", "''") + "'," +       jTab.getValueAt(x, 1).toString() + ",'" +      jTab.getValueAt(x, 5).toString().replace("'", "''").replace("'", "''") + "'," +        jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "") + "," +        jTab.getValueAt(x, 9).toString().replace("$", "").replace(",", "") + ", '" +      jTab.getValueAt(x, 8).toString().replace("'", "''") + "', " +         jTab.getValueAt(x, 7).toString() + ", " +   jTab.getValueAt(x, 10).toString() + ", " +  jTab.getValueAt(x, 11).toString().replace("$", "").replace(",", "") + ",  0,             0,           0,          0,          0,      0,      0, " + jTab.getValueAt(x, 12).toString().replace("$", "").replace(",", "") + ", '" + jTab.getValueAt(x, 13).toString().trim() + "', '" +    jTab.getValueAt(x, 14).toString().replace("'", "''") + "', 0, " +     jTab.getValueAt(x, 4).toString().replace("'", "''").trim() + ", " + sKit + ", '" + jComTall.getSelectedItem().toString().replace("'", "''").trim() + "', '" + jComColo.getSelectedItem().toString().replace("'", "''").trim() + "', DATE(" + sBack + "), 0, '" + jTab.getValueAt(x, 20).toString().trim() + "', '" + jTab.getValueAt(x, 21).toString().trim() + "', " + sFCadu + ", '',      '" + jTab.getValueAt(x, 23).toString().trim() + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            { 
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                               
            }
                        
        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
        
        /*Contiene la fecha de alta y el nombre del usuario*/
        String sFAlt    = "";
        String sNom     = "";
        String sMon     = "";
        
        /*Obtiene de la tabla de cotizaciones algunos datos necesarios*/        
        try
        {                    
            sQ = "SELECT cots.MON, cots.FALT, nom FROM cots LEFT OUTER JOIN estacs ON estacs.ESTAC = cots.ESTAC WHERE codcot = '" + sSer + sConsec + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sFAlt           = rs.getString("falt");                                                                                                                 
                sNom            = rs.getString("nom");                                                                                                                 
                sMon            = rs.getString("mon");
            }                        
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
        String  sNomLoc         = "";
        String  sCallLoc        = "";
        String  sTelLoc         = "";
        String  sColLoc         = "";
        String  sCPLoc          = "";
        String  sCiuLoc         = "";
        String  sEstaLoc        = "";
        String  sPaiLoc         = "";
        String  sRFCLoc         = "";        
        
        /*Obtiene todos los datos de la cliente local*/        
        try
        {                  
            sQ = "SELECT nom, calle, tel, col, cp, ciu, estad, pai, rfc FROM basdats WHERE codemp = '" + Login.sEmpBD + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos obtiene los resultados*/
            if(rs.next())
            {                
                sNomLoc             = rs.getString("nom");                                    
                sCallLoc            = rs.getString("call");                                    
                sTelLoc             = rs.getString("tel");                                    
                sColLoc             = rs.getString("col");                                    
                sCPLoc              = rs.getString("cp");                                    
                sCiuLoc             = rs.getString("ciu");                                    
                sEstaLoc            = rs.getString("estad");                                    
                sPaiLoc             = rs.getString("pai");                                    
                sRFCLoc             = rs.getString("rfc");                                   
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

        /*Dale formato de mon al subtot*/        
        double dCant            = Double.parseDouble(sSubTot);                
        NumberFormat n          = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sSubTot                 = n.format(dCant);

        /*Dale formato de mon al impue*/
        dCant                   = Double.parseDouble(sImpue);                        
        sImpue                  = n.format(dCant);

        /*Dale formato de mon al subtot*/
        dCant                   = Double.parseDouble(sTot);                        
        sTot                    = n.format(dCant);
        
        /*Agrega a la tabla del otro formulario los datos de la nueva cotización*/
        DefaultTableModel te    = (DefaultTableModel)jTabCaraGlob.getModel();
        Object nu[]             = {iContFiGlo, sSer + sConsec, sSer, sCodEmp, jTAObserv.getText(), sSubTot, sImpue, sTot, jTTotDesc.getText(), jTADescrip.getText(), sFAlt, sFAlt, sFVenc, sFEntre, sFCotFi, Star.sSucu, Star.sNoCaj, Login.sUsrG, sNom, "PE", "0", "", "", ""};
        te.addRow(nu);
        
        /*Aumenta en uno el contador de filas de la otra tabla de cots*/
        ++Cots.iContFi;
                
        /*Determina si se tiene que mostrar o no el arcivo*/
        final boolean bMostA;
        if(jCMostA.isSelected())
            bMostA  = true;
        else
            bMostA  = false;
        
        /*Determina si se tiene que imprimir el archivo o no*/
        final boolean bImp;        
        if(jCImp.isSelected())
            bImp    = true;
        else
            bImp    = false;
                
        /*Declara las variable final para el thread*/
        final String sCodEmpFi      = sCodEmp;
        final String sCodCotFi      = sSer + sConsec;
        final String sSubTotFi      = sSubTot;
        final String sImpueFi       = sImpue;
        final String sTotFi         = sTot;
        final String sMonFi         = sMon;
        final String sCo1Fi         = jTCo1.getText().trim();
        final String sCo2Fi         = jTCo2.getText().trim();
        final String sCo3Fi         = jTCo3.getText().trim();
        final String sNomLocFi      = sNomLoc;
        final String sTelLocFi      = sTelLoc;
        final String sColLocFi      = sColLoc;
        final String sCallLocFi     = sCallLoc;
        final String sCPLocFi       = sCPLoc;
        final String sCiuLocFi      = sCiuLoc;
        final String sEstaLocFi     = sEstaLoc;
        final String sPaiLocFi      = sPaiLoc;
        final String sRFCLocFi      = sRFCLoc;
        final String sObservFi      = jTAObserv.getText();
        final String sDescripFi     = jTADescrip.getText();
        final boolean bMandCoFi     = bMandCo;
        final String sImpLetFi      = sTotLet;
                
        /*Thread para quitar carga*/
        (new Thread()
        {
            @Override
            public void run()
            {
                Star.vGenPDFCot(sCodEmpFi, sCodCotFi, sMonFi, sSubTotFi, sImpueFi, sTotFi, sNomLocFi, sTelLocFi, sColLocFi, sCallLocFi, sCPLocFi, sCiuLocFi, sEstaLocFi, sPaiLocFi, sRFCLocFi, sObservFi, sDescripFi, sFCotFi, sImpLetFi, bMostA, bImp, sCo1Fi, sCo2Fi, sCo3Fi, bMandCoFi, "");
            }
            
        }).start();
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Cotización: " + sSer + sConsec + " guardada con éxito.", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
                
        /*Cierra el formulario*/
        this.dispose();        
        Star.gCot   = null;
    
        /*Llama al recolector de basura*/
        System.gc();
        
    }/*Fin de private void vGuaCot()*/
                            
    
    /*Cuando se presiona el botón de aceptar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
        
        /*Función para guardar la cotización normal JPS1*/
        vGuaCot();                                
            
    }//GEN-LAST:event_jBGuarActionPerformed
                
            
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se gana el foco del teclado en el text area de observ*/
    private void jTAObservFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAObserv.setSelectionStart(0);jTAObserv.setSelectionEnd(jTAObserv.getText().length());        
        
    }//GEN-LAST:event_jTAObservFocusGained

    /*Cuando se gana el foco del teclado en el campo de área de descripción*/
    private void jTADescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTADescripFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTADescrip.setSelectionStart(0);jTADescrip.setSelectionEnd(jTADescrip.getText().length());        
        
    }//GEN-LAST:event_jTADescripFocusGained

    
    /*Cuando se presiona una tecla en el panel*/
    private void jPClienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPClienKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPClienKeyPressed

    
    /*Cuando se prsiona una tecla en el botón de borrar partida*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se presiona una tecla en el campo de impues*/
    private void jTImpueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTImpueKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTImpueKeyPressed

    
    /*Cuando se presiona una tecla en el campo de subtot*/
    private void jTSubTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSubTotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSubTotKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de subtot*/
    private void jTSubTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusGained
           
        /*Selecciona todo el texto cuando gana el foco*/
        jTSubTot.setSelectionStart(0);jTSubTot.setSelectionEnd(jTSubTot.getText().length());        
        
    }//GEN-LAST:event_jTSubTotFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de impues*/
    private void jTImpueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpueFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTImpue.setSelectionStart(0);jTImpue.setSelectionEnd(jTImpue.getText().length());        
        
    }//GEN-LAST:event_jTImpueFocusGained

    
    /*Cuando se gana el foco del teclado en el tot*/
    private void jTTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTot.setSelectionStart(0);jTTot.setSelectionEnd(jTTot.getText().length());        
        
    }//GEN-LAST:event_jTTotFocusGained

    
    /*Cuando se presiona una tecla en el campo de tot*/
    private void jTTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTotKeyPressed

    
    
    
    /*Cuando se presiona una tecla en el campo de descripción del artículo*/
    private void jTDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de descripción*/
    private void jTDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescrip.setSelectionStart(0);jTDescrip.setSelectionEnd(jTDescrip.getText().length());        
        
    }//GEN-LAST:event_jTDescripFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de descripción de unidad*/
    private void jTUnidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUnidFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescrip.setSelectionStart(0);jTDescrip.setSelectionEnd(jTDescrip.getText().length());        
        
    }//GEN-LAST:event_jTUnidFocusGained

    
    /*Cuando se presiona una tecle en el campo de unidad*/
    private void jTUnidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUnidKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUnidKeyPressed

    
    /*Cuando se presiona una tecla en el combobox de unids*/
    private void jComUnidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComUnidKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComUnidKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de pre*/
    private void jTPreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPreFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPre.setSelectionStart(0);jTPre.setSelectionEnd(jTPre.getText().length());      
        
        /*Guarda el valor original*/
        sPreOri = jTPre.getText();
        
    }//GEN-LAST:event_jTPreFocusGained

    
    /*Cuando se presiona una tecla en el campo del pre*/
    private void jTPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPreKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPreKeyPressed

    
    /*Cuando ocurre una acción en el combobox de unidad*/
    private void jComUnidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComUnidActionPerformed
                
        /*Si no hay datos entonces regresa*/
        if(jComUnid.getSelectedItem()==null)
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
        
        /*Obtiene la descripción de la unidad*/
        try
        {
            sQ = "SELECT descrip FROM unids WHERE cod = '" + jComUnid.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado en el control*/
            if(rs.next())
                jTUnid.setText(rs.getString("descrip"));                
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
        
    }//GEN-LAST:event_jComUnidActionPerformed

    
    /*Cuando se tipea una tecla en el campo de cant*/
    private void jTCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTCantKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de exist*/
    private void jTExistFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExistFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTExist.setSelectionStart(0);jTExist.setSelectionEnd(jTExist.getText().length());        
        
    }//GEN-LAST:event_jTExistFocusGained

    
    /*Cuando se presiona una tecla en el campo de exist*/
    private void jTExistKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExistKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTExistKeyPressed

            
    /*Cuando se gana el foco del teclado en el campo de nom del cliente*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length());        
        
    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se presiona una tecla en el campo de nom de cliente*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomKeyPressed

    
    /*Función para borrar de la tabla de partidas*/
    private void vDelParts()
    {
        /*Si no se a seleccionado nada en la tabla entonces*/
        if(jTab.getSelectedRow() == -1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una partida para borrar.", "Borrar Partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla de partidas y regresa*/
            jTab.grabFocus();            
            return;
        }   
        
        /*Preguntar al usr si esta seguro de que querer borrar la partida*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la(s) partida(s)?", "Borrar Partida", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Recorre las filas seleccionadas*/
        int iSel[] = jTab.getSelectedRows();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Borralos de la tabla de jps1*/
            DefaultTableModel te  = (DefaultTableModel)jTab.getModel();
            te.removeRow(x);
            
            /*Resta uno al contador de filas*/
            --iContFiParts;
        }
        
        /*Si la tabla quedo en 0 entonces reinicia el contador a 1*/
        if(jTab.getRowCount()==0)
            iContFiParts = 1;
        
        /*Recalcula los totes leyendo toda la tabla de partidas y los campos de subtot materiales y mano de obra*/
        Star.vRecalcTotN(jTab, jTSubTot, jTImpue, jTTot, jTTotDesc);
                            
    }/*Fin de private void vDelParts()*/
           
        
    /*Cuando se presiona el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed

        /*Función para borrar de la tabla de partidas*/
        vDelParts();                                
                
    }//GEN-LAST:event_jBDelActionPerformed
                
    
    /*Cuando se pierde el foco del teclado en el campo del producto*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());
        
    }//GEN-LAST:event_jTProdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de prods*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Función para procesar esta parte*/
        vCargP();               
        
    }//GEN-LAST:event_jTProdFocusLost

    
    
    /*Función paara cuando se pierde el foco del teclado en el campo del producto*/
    private void vCargPPS(String sSerie)
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
        
        /*Trae los datos del producto seleccionado*/        
        try
        {                       
            sQ = "SELECT prod, comen FROM serieprod WHERE ser='" + sSerie  + "'"; 
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {               
                /*Se carga el producto*/
                jTProd.setText          (rs.getString("prod"));
                
                //Se carga e comentario de la serie
                jTComenSer.setText     (rs.getString("comen"));
            }
            /*Else no hay datos entonces*/
            else
            {                   
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Función para limpiar todos los campos del producto*/
                vLimpP();
                
                /*Regresa por que no puede continuar*/
                return;                
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
        
    }//Final private void vCargPPS()
    
    
    /*Función paara cuando se pierde el foco del teclado en el campo del producto*/
    private void vCargP()
    {
        /*Coloca el caret en la posiciòn 0*/
        jTProd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                      
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae los datos del producto seleccionado*/        
        try
        {
            sQ = "SELECT CASE WHEN prods.COMPUE = 1 THEN 'Si' ELSE 'No' END AS esk, garan.DESCRIP AS garadescrip, unid, impue, alma, prods.DESCRIP, costre, prelist1, prelist2, exist FROM prods LEFT OUTER JOIN garan ON garan.GARA = prods.GARAN WHERE prod = '" + jTProd.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca si es kit o no de manera visible*/
                jTKit.setText(rs.getString("esk"));
                
                /*Obtiene el precio que debe tener correctamente por las reglas de negocio*/
                String sPre     = Star.sPreCostVta(jTProd.getText().trim(), jTCli.getText().trim(), "1", "cots", "cot");                

                /*Si hubo error entonces regresa*/
                if(sPre==null)
                    return;                
                
                /*Tokeniza para obtener el precio y la lista*/
                java.util.StringTokenizer stk = new java.util.StringTokenizer(sPre, "|");
                sPre            = stk.nextToken();
                String sList    = stk.nextToken();

                /*Coloca la lista en su lugar*/
                jTList.setText  (sList);
                
                /*Dale formato de moneda al precio*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                double dCant    = Double.parseDouble(sPre);                
                sPre            = n.format(dCant);

                /*Establece en el campo correspondiente el costo*/
                jTPre.setText       (sPre);

                /*Coloca la garantía*/
                jTGara.setText      (rs.getString("garadescrip"));
                                
                /*Agrega la descripción al campo*/
                jTDescrip.setText   (rs.getString("descrip"));

                /*Agregalo al campo de exist*/
                jTExist.setText     (rs.getString("exist"));                                
                
                /*Establece el código del impue en el control*/
                jComImp.setSelectedItem(rs.getString("impue"));
                
                /*Establece el código de la unidad en el control*/
                jComUnid.setSelectedItem(rs.getString("unid"));

            }/*Fin de if(rs.next())*/
            /*Else no hay datos entonces*/
            else
            {                   
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Función para limpiar todos los campos del producto*/
                vLimpP();
                
                /*Regresa por que no puede continuar*/
                return;                
            }
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

        /*Muestra que esta cargando la imágen*/
        jLImg.setText("Cargando...");
        
        /*Obtiene la imágen si es que tiene*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Carga la imágen en el control*/
                Star.vGetImg(jTProd.getText(), jLImg);
                
                /*Muestra que se termino de cargar la imágen*/
                jLImg.setText("");
            }
            
        }).start();
        
        /*Selecciona la mon nacional en el combobox*/
        jComMon.setSelectedItem("MN");
        
    }/*Fin de vCargP()*/
    
    
    /*Función para limpiar los campos del producto*/
    private void vLimpP()
    {
        /*Coloca valores default en todos los campos de la cotización*/
        jTCant.setText          ("1.0");
        jTExist.setText         ("");        
        jTKit.setText           ("");
        jTDescrip.setText       ("");
        jTGara.setText          ("");
        jTCantLot.setText       ("0");
        jTLot.setText           ("");
        jTPedimen.setText       ("");
        jTSerProd.setText       ("");
        jTComenSer.setText      ("");        
        jTPre.setText           ("$0.00");                
        jComAlma.setSelectedItem("");
        jComUnid.setSelectedItem("");            
        jComImp.setSelectedItem ("");                    

        /*Esconde el control de imágen*/
        jLImg.setVisible(false);
        
        /*Si el almacén de venta no es cadena vacia entonces selecciona el almacén global*/
        if(sAlmGlo.compareTo("")!=0)
            jComAlma.setSelectedItem(sAlmGlo);        
    }
    
    
    /*Cuando se presiona una tecla en el campo del producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
            Busc b = new Busc(this, jTProd.getText(), 2, jTProd, jTDescrip, jTAlma2, "", null);
            b.setVisible(true);
            
            /*Función para procesar esta parte*/
            vCargP();               
        }
        /*Else if se presiono CTRL + B entonces*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_B)
        {
            /*Llama a la forma para búscar productos avanzadamente*/
            ptovta.BuscAvan x = new ptovta.BuscAvan(this, jTProd, null, null, null);
            x.setVisible(true);
            
            /*Coloca el foco del teclado en el campo del producto*/
            jTProd.grabFocus();            
        }
        
        /*Else, llama a la función para procesarlo normlemnte*/
        else                    
            vKeyPreEsc(evt);        

    }//GEN-LAST:event_jTProdKeyPressed

   
   /*Cuando se tipea una tecla en el campo del producto*/ 
    private void jTProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyTyped

        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTProd.getText().length()> 255)        
            jTProd.setText(jTProd.getText().substring(0, 255));        

        /*Si el carácter escrito no es un ca imprimible regresar*/
        if(!(evt.getKeyChar() >= 32 && evt.getKeyChar() <= 126))
            return;

        /*Lee los caes introducidos anteriormente por el usr*/
        String sCadena      = jTProd.getText();

        /*Si el carácter introducido es minúscula entonces*/
        char ca             = evt.getKeyChar();
        if(Character.isLowerCase(ca))                              
            ca = Character.toUpperCase(ca);       

        /*No escribas la letra*/
        evt.consume();

        /*Si no es back space*/
        if(ca != '\b')
        {
            /*Forma la cadena*/
            sCadena += Character.toString(ca);

            /*Coloca la cadena en el campo*/
            jTProd.setText(sCadena);
        }
        
    }//GEN-LAST:event_jTProdKeyTyped

    
    /*Cuando se presiona el botón de buscar coincidencia*/
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
        Busc b = new Busc(this, jTProd.getText(), 2, jTProd, jTDescrip, jTAlma2, "", null);
        b.setVisible(true);
        
        /*Pon el foco del teclado en el campo del producto*/
        jTProd.grabFocus();
        
        /*Función para procesar esta parte*/
        vCargP();               
        
    }//GEN-LAST:event_jBProdActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProdKeyPressed
    
    
    /*Cuando se pierde el foco del teclado en el campo del área de observ*/
    private void jTAObservFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusLost

        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTAObserv.getText().length()> 255)
            jTAObserv.setText(jTAObserv.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTAObservFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción*/
    private void jTADescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTADescripFocusLost
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTADescrip.getText().length()> 255)
            jTADescrip.setText(jTADescrip.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTADescripFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la cant*/
    private void jTCantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCant.setCaretPosition(0);
        
        /*Si la cantidad es cadena vacia entonces que sea 1*/
        if(jTCant.getText().compareTo("")==0)
            jTCant.setText("1.0");
        
    }//GEN-LAST:event_jTCantFocusLost
        
    
    /*Cuando se pierde el foco del teclado en el campo de la descripción*/
    private void jTDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDescrip.setCaretPosition(0);
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTDescrip.getText().length()> 255)
            jTDescrip.setText(jTDescrip.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTDescripFocusLost

                
    /*Cuando se gana el foco del teclado en el campo del corr 1*/
    private void jTCo1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo1.setSelectionStart(0);jTCo1.setSelectionEnd(jTCo1.getText().length());  
        
    }//GEN-LAST:event_jTCo1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del corr 2*/
    private void jTCo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo2.setSelectionStart(0);jTCo2.setSelectionEnd(jTCo2.getText().length());  
        
    }//GEN-LAST:event_jTCo2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del corr 3*/
    private void jTCo3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo3.setSelectionStart(0);jTCo3.setSelectionEnd(jTCo3.getText().length());  
        
    }//GEN-LAST:event_jTCo3FocusGained

    
    /*Cuando se presiona una tecla en el campo del corr 1*/
    private void jTCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo1KeyPressed

    
    /*Cuando se presiona una tecla en el campo del corr 2*/
    private void jTCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo2KeyPressed

    
    /*Cuando se presiona una tecla en el campo del corr 3*/
    private void jTCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo3KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox del corr 1*/
    private void jCCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo1KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de corr 2*/
    private void jCCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo2KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de corr 3*/
    private void jCCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo3KeyPressed
    
    
    /*Cuando se presiona una tecla en el checkbox de mostrar archivos*/
    private void jCMostAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMostAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMostAKeyPressed
            
    
    /*Cuando sucede una acción en el combobox de mons*/
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
        String sDescrip = Star.sGetDescripCamp(con, "mondescrip", "mons", "WHERE mon = '" + jComMon.getSelectedItem().toString() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComMon.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);                  

    }//GEN-LAST:event_jComMonActionPerformed

    
    /*Cuando se presiona una tecla en el combobox de mons*/
    private void jComMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComMonKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComMonKeyPressed
    
    
    /*Cuando se pierde el foco del teclado en el campo del pre*/
    private void jTPreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPreFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPre.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTPre.getText().compareTo("")!=0)
            jTPre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el cost es cadena vacia entonces*/
        if(jTPre.getText().compareTo("")==0) 
        {
            /*Pon el valor que tenía anteriormente y regresa*/
            jTPre.setText(sPreOri);              
            return;
        }                                        
        
        /*Si es un producto válido entonces*/
        if(jTDescrip.getText().compareTo("")!=0 && jTProd.getText().compareTo("")!=0)
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

            /*Comprueba si el producto se puede vender a bajo del cost o no*/
            try
            {
                sQ = "SELECT bajcost, cost FROM prods WHERE prod = '" + jTProd.getText() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())
                {
                    /*Si el producto no se puede vender a bajo del cost entonces*/
                    if(rs.getString("bajcost").compareTo("0")==0)
                    {
                        /*Si el pre es menor al cost entonces*/
                        if(Double.parseDouble(jTPre.getText().replace("$", "").replace(",", "")) < Double.parseDouble(rs.getString("cost")))
                        {
                            //Cierra la base de datos
                            if(Star.iCierrBas(con)==-1)
                                return;

                            /*Mensajea*/
                            JOptionPane.showMessageDialog(null, "No se puede vender este producto abajo del cost.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                            /*Coloca el valor anterior y regresa*/
                            jTPre.setText(sPreOri);                  
                            return;
                        }
                    }
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
                        
        }/*Fin de if(jTDescrip.getText().compareTo("")!=0 && jTProd.getText().compareTo("")!=0)*/                        
                    
        /*Dale formato de mon al pre y colocalo en su lugar*/                
        double dCant    = Double.parseDouble(jTPre.getText().replace("$", "").replace(",", ""));                
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        jTPre.setText(n.format(dCant));           
                       
    }//GEN-LAST:event_jTPreFocusLost

    
    /*Cuando se presiona una tecla en el checkbox de imprimir*/
    private void jCImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCImpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCImpKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de guardar corrs*/
    private void jCGuaCoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGuaCoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCGuaCoKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mandar corrs electrónicos*/
    private void jCMandKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMandKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMandKeyPressed

    
    /*Cuando se tipea una tecla en el campo del pre*/
    private void jTPreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPreKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();        
        
    }//GEN-LAST:event_jTPreKeyTyped
                
    
    /*Cuando se presiona el botón de búscar cliente*/
    private void jBCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCliActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
        Busc b = new Busc(this, jTCli.getText(), 1, jTCli, jTNom, jTSer, "", null);
        b.setVisible(true);

        /*Procesa todo esto en una función*/
        vCargCli();                
        
    }//GEN-LAST:event_jBCliActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar cliente*/
    private void jBCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCliKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCliKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del código de la cliente*/
    private void jTCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCli.setSelectionStart(0);jTCli.setSelectionEnd(jTCli.getText().length());

    }//GEN-LAST:event_jTCliFocusGained


    /*Procesa todo esto en una función*/
    private void vCargCli()
    {
        /*Coloca el caret en la posiciòn 0*/
        jTCli.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCli.getText().compareTo("")!=0)
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el código de la cliente esta vacio entonces*/
        if(jTCli.getText().compareTo("")==0)
        {
            /*Coloca cadenas vacias en los campos correspondientes y regresa*/
            jTNom.setText   ("");
            jTSer.setText   ("");
            jTCo1.setText   ("");
            jTCo2.setText   ("");
            jTCo3.setText   ("");
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

        /*Comprueba si existe el código de la cliente*/
        try
        {
            sQ = "SELECT nom, ser, co1, co2, co3 FROM emps WHERE CONCAT_WS('', ser,codemp) = '" + jTCli.getText().trim() + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe*/
            if(rs.next())
            {
                /*Coloca los datos en los campos correspondientes*/
                jTNom.setText(rs.getString("nom"));
                jTSer.setText(rs.getString("ser"));
                jTCo1.setText(rs.getString("co1"));
                jTCo2.setText(rs.getString("co2"));
                jTCo3.setText(rs.getString("co3"));
                
                /*Coloca los carets al principio*/
                jTNom.setCaretPosition(0);
                jTSer.setCaretPosition(0);
                jTCo1.setCaretPosition(0);
                jTCo2.setCaretPosition(0);
                jTCo3.setCaretPosition(0);
            }
            /*Else no existe entonces coloca los campos correspondientes en vacio*/
            else
            {                
                jTNom.setText("");
                jTSer.setText("");
                jTCo1.setText("");
                jTCo2.setText("");
                jTCo3.setText("");
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
        
    }/*Fin de private void vCargCli()*/
        
        
    /*Cuando se pierde el foco del teclado en el campo del código de la cliente*/
    private void jTCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusLost

        /*Procesa todo esto en una función*/
        vCargCli();                
        
        /*Limpia los campos de las partidas*/
        vLimpP();
        
        /*Borra la tabla de partidas*/        
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea los totales*/
        jTSubTot.setText    ("$0.00");
        jTImpue.setText     ("$0.00");
        jTTot.setText       ("$0.00");
        jTTotDesc.setText   ("$0.00");
        
    }//GEN-LAST:event_jTCliFocusLost

    
    /*Cuando se presiona una tecla en el campo del código de la cliente*/
    private void jTCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyPressed

        /*Limpia toda la tabla de partidas*/            
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);

        /*Resetea los totales*/
        jTSubTot.setText    ("$0.00");
        jTImpue.setText     ("$0.00");
        jTTot.setText       ("$0.00");                
        jTTotDesc.setText   ("$0.00");

        /*Resetea el contador de filas*/
        iContFiParts        = 1;
        
        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar cliente*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)                    
            jBCli.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else                   
            vKeyPreEsc(evt);        

    }//GEN-LAST:event_jTCliKeyPressed
    
    
    /*Cuando se tipea una tecla en el campo del código del productor*/
    private void jTCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCliKeyTyped
    
    
    /*Cuando se presiona el botón de tipo de cambio*/
    private void jBTipCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTipCamActionPerformed

        /*Si no a seleccionado ninguna moneda entonces*/
        if(jComMon.getSelectedItem().toString().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una moneda para el tipo de cambio.", "Tipo de Cambio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jComMon.grabFocus();
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

        /*Obtiene el tipo de cambio actual*/
        String sTipCam  = "";
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
        
        //Declara variables locales
        boolean bSi     = false;
        String sResul   = null;
        
        /*Bucle mientras no se inserte una cant válida para el tipo de cambio*/        
        do
        {
            /*Muestra el tipo de cambio para que si lo desean lo actualicen*/
            sResul = JOptionPane.showInputDialog(null, "Tipo de Cambio " + sTipCam + ":", jComMon.getSelectedItem().toString() + " Tipo Cambio", 1);

            /*Si es nulo osea que cancelo entonces que regrese*/
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
                d       = Math.abs(d);
                sResul  = Double.toString(d);
                
                /*Pon la bandera para salir del bucle*/
                bSi = true;
            }
            catch(NumberFormatException expnNumForm)
            {
                JOptionPane.showMessageDialog(null, "Ingresa una cantidad válida.", jComMon.getSelectedItem().toString() + " Tipo Cambio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                 
            }
            
        }while(!bSi || sResul==null);
           
        //Inserta el log del tipo de cambio de la moneda
        if(Star.iRegTipsCambLog(con, jComMon.getSelectedItem().toString().trim(), sResul)==-1)
            return;
        
        //Actualiza el tipo de cambio de la moneda
        if(Star.iActTipCamMon(con, jComMon.getSelectedItem().toString().trim(), sResul)==-1)
            return;
        
        //Cierra la base de datos
        Star.iCierrBas(con);
                    
    }//GEN-LAST:event_jBTipCamActionPerformed

    
    /*Cuando se presiona una tecla en el botón de tipo de cambio*/
    private void jBTipCamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTipCamKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTipCamKeyPressed

    
    /*Cuando sucede una acción en el combobox del código del impuesto*/
    private void jComImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComImpActionPerformed

        /*Si no hay datos entonces regresa*/
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
        String      sQ;

        /*Obtiene el valor del impue en base a su código*/
        try
        {
            sQ = "SELECT impueval FROM impues WHERE codimpue = '" + jComImp.getSelectedItem().toString() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si el valor es -1 solo poner cadena vacia*/
                if(rs.getString("impueval").compareTo("-1")==0)
                    jTValImp.setText("");
                else
                    jTValImp.setText(rs.getString("impueval"));
            }
            else
                jTValImp.setText("");
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

    
    /*Cuando se presiona una tecla en el combobox de impues*/
    private void jComImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComImpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComImpKeyPressed

    
    /*Cuando se gana el foco del teclado en elcampo del valor del impue*/
    private void jTValImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTValImpFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTValImp.setSelectionStart(0);jTValImp.setSelectionEnd(jTValImp.getText().length());

    }//GEN-LAST:event_jTValImpFocusGained
    
    
    /*Cuando se presiona una tecla en el campo del valor del impue*/
    private void jTValImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTValImpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTValImpKeyPressed
        
    
    /*Cuando se presiona una tecla en el combobox de sers de cots*/
    private void jComSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComSerKeyPressed

    
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

    
    /*Cuando se presiona el botón de búscar lista de precios*/
    private void jBListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBListActionPerformed

        /*Si no a seleccionado un producto entonces*/
        if(jTDescrip.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un producto.", "Lista de Precios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo del producto y regresa*/
            jTProd.grabFocus();
            return;
        }

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        BuscPre b = new BuscPre(this, jTList.getText(), 1, jTList, jTProd.getText());
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo de la lita*/
        jTList.grabFocus();
        
    }//GEN-LAST:event_jBListActionPerformed

    
    /*Cuando se presiona una tecla en el campo de la lista de precios*/
    private void jBListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBListKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBListKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la lista de precios*/
    private void jTListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTListFocusGained

        /*Obtiene la lista original*/
        sListOri    = jTList.getText();

        /*Selecciona todo el texto cuando gana el foco*/
        jTList.setSelectionStart(0);jTList.setSelectionEnd(jTList.getText().length());
        
    }//GEN-LAST:event_jTListFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la lista de precios*/
    private void jTListFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTListFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTList.setCaretPosition(0);
        
        /*Si es cadena vacia entonces regresa*/
        if(jTList.getText().compareTo("")==0)
            return;

        /*Si no esta en rango permitido la lista de precios entonces regresa*/
        if(Integer.parseInt(jTList.getText())< 1 || Integer.parseInt(jTList.getText()) > 10)
        {
            /*Coloca la lista original y regresa*/
            jTList.setText(sListOri);
            return;
        }

        /*Si no es un producto válido entonces*/
        if(jTDescrip.getText().compareTo("")==0)
            return;

        /*Obtiene el precio y la lista que debe tener correctamente por las reglas de negocio*/
        String sPre     = Star.sPreCostVta(jTProd.getText().trim(), jTCli.getText().trim(), jTList.getText().trim(), "vtas", "fac");        
        
        /*Si hubo error entonces regresa*/
        if(sPre==null)
            return;                

        /*Tokeniza para obtener el precio y la lista*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sPre, "|");
        sPre            = stk.nextToken();
        String sList    = stk.nextToken();
        
        /*Dale formato de moneda al precio*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
        double dCant    = Double.parseDouble(sPre);                
        jTPre.setText(n.format(dCant));

        /*Coloca la lista en su lugar*/
        jTList.setText(sList);
        
    }//GEN-LAST:event_jTListFocusLost

    
    /*Cuando se presiona una tecla en el campo de la lista de precios*/
    private void jTListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTListKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Si no a seleccionado un producto entonces*/
            if(jTDescrip.getText().compareTo("")==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Selecciona un producto.", "Lista de Precios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo del producto y regresa*/
                jTProd.grabFocus();
                return;
            }
            
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            BuscPre b = new BuscPre(this, jTList.getText(), 1, jTList, jTProd.getText());
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTListKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la lista de precios*/
    private void jTListKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTListKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();
        
    }//GEN-LAST:event_jTListKeyTyped

    
    /*Cuando se presiona una tecla en el panel de partidas*/
    private void jPPartsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPPartsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPPartsKeyPressed

    
    /*Cuando se presiona una tecla en el panel de otros*/
    private void jPOtrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPOtrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPOtrKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la fecha de vencimiento*/
    private void jDFVencKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFVencKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jDFVencKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de la serie*/
    private void jComSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jComSer.getSelectedItem().toString().compareTo("")!=0)
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComSerFocusLost

    
    /*Cuando se pierde el foco del teclado en el combo de la moneda*/
    private void jComMonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComMonFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jComMon.getSelectedItem().toString().compareTo("")!=0)
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComMonFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del impuesto*/
    private void jComImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComImpFocusLost
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jComImp.getSelectedItem().toString().compareTo("")!=0)
            jComImp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComImpFocusLost

    
    /*Cuando sucede una acción en el check de guardar correos del cliente*/
    private void jCGuaCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCGuaCoActionPerformed
        
        /*Función para validar la modificación de los correos del cliente*/
        vValCliCo();
        
    }//GEN-LAST:event_jCGuaCoActionPerformed

    
    /*Cuando se presiona el botón de ver imágen en grande*/
    private void jBVeGranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVeGranActionPerformed

        /*Si a ingresado un producto entonces*/
        if(jTDescrip.getText().compareTo("")==0)
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
                JOptionPane.showMessageDialog(null, "No existe imágen para el producto: " + jTProd.getText() + ".", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
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

    }//GEN-LAST:event_jBVeGranActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver imágen en grande*/
    private void jBVeGranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVeGranKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBVeGranKeyPressed

    
    /*Cuando el mouse entra en el control de la imágen*/
    private void jPanImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImgMouseEntered

        /*Si no a seleccionado un prodcuto válido entonces regresa*/
        if(jTDescrip.getText().compareTo("")==0)
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
            /*Si contiene ficheros entonces*/
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

    
    /*Cuando se presiona una tecla en el scroll de la imágen*/
    private void jSImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSImgKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSImgKeyPressed

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCli.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBCliMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProd.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBProdMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBListMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBList.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBListMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTipCamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTipCamMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTipCam.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBTipCamMouseEntered

    
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
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVeGranMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGranMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVeGran.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBVeGranMouseEntered

    
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
    private void jBCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCli.setBackground(colOri);
        
    }//GEN-LAST:event_jBCliMouseExited


    /*Cuando el mouse sale del botón específico*/
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(colOri);
        
    }//GEN-LAST:event_jBProdMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBListMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBList.setBackground(colOri);
        
    }//GEN-LAST:event_jBListMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/    
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTipCamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTipCamMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTipCam.setBackground(colOri);
        
    }//GEN-LAST:event_jBTipCamMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVeGranMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGranMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVeGran.setBackground(colOri);
        
    }//GEN-LAST:event_jBVeGranMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCo1.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCo1FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCo2.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCo2FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCo3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCo3.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCo3FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTSer.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSerFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTNom.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomFocusLost

    
    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUnidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUnidFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUnid.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUnidFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTExistFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExistFocusLost
 
        /*Coloca el caret en la posiciòn 0*/
        jTExist.setCaretPosition(0);
        
    }//GEN-LAST:event_jTExistFocusLost
        
    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTValImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTValImpFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTValImp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTValImpFocusLost

    
    /*Cuando se presiona el botón de ver descripción en grande*/
    private void jBGranDescripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGranDescripActionPerformed

        /*Muestar la forma para ver la descripción en grande*/
        DescripGran b = new DescripGran(jTDescrip, null);
        b.setVisible(true);

    }//GEN-LAST:event_jBGranDescripActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver descripción en grande*/
    private void jBGranDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGranDescripKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGranDescripKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo del subtotal*/
    private void jTSubTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTSubTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSubTotFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del impuesto*/
    private void jTImpueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpueFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTImpue.setCaretPosition(0);
        
    }//GEN-LAST:event_jTImpueFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del total*/
    private void jTTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTotFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del descuento*/
    private void jTDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDesc.setSelectionStart(0);jTDesc.setSelectionEnd(jTDesc.getText().length());
        
    }//GEN-LAST:event_jTDescFocusGained

    
    /*Cuando se preisona una tecla en el campo del descuento*/
    private void jTDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescKeyPressed

    
    /*Cuando se tipea una tecla en el campo del descunto*/
    private void jTDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTDescKeyTyped

    
    /*Cuando se pierde el foco del teclado en el campo del descuento*/
    private void jTDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusLost
        
        /*Si es cadena vacia el descuento entonces*/        
        if(jTDesc.getText().compareTo("")==0)
        {
            /*Que sea 0 y regresa*/
            jTDesc.setText("0");                        
            return;
        }
        
        /*Si el descuento es 0 que regrese*/
        if(Double.parseDouble(jTDesc.getText())==0)
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

        /*Obtiene el descuento posible para este usuario*/
        String sDesc    = "100";
        try
        {
            sQ = "SELECT descu, habdesc FROM estacs WHERE estac = '" + Login.sUsrG + "'" ;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene el descuento del usuario*/
                sDesc         = rs.getString("descu");
                
                /*Si el descuento esta deshabilitado para este usuario entonces*/
                if(rs.getString("habdesc").compareTo("0")==0)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Coloca 0 en el campo del descuento*/
                    jTDesc.setText("0");
                    
                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "El descuento para el usuario: " + Login.sUsrG + " esta deshabilitado.", "Descuento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    return;                                                
                }
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

        /*Si el descuento que se quiere dar es mayor al permitido entonces*/
        if(Double.parseDouble(jTDesc.getText()) > Double.parseDouble(sDesc))
        {
            /*Coloca el descuento 0*/
            jTDesc.setText("0");
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El descuento máximo permitido para el usuario: " + Login.sUsrG + " es: " + sDesc + "%.", "Descuento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el campo del desc*/
            jTDesc.grabFocus();
        }
        
    }//GEN-LAST:event_jTDescFocusLost

    
    /*Cuando se gana el foco del teclado en el campo deltotal del descuetno*/
    private void jTTotDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotDescFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTotDesc.setSelectionStart(0);jTTotDesc.setSelectionEnd(jTTotDesc.getText().length());        
        
    }//GEN-LAST:event_jTTotDescFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del total*/
    private void jTTotDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotDescFocusLost
 
        /*Coloca el caret en la posiciòn 0*/
        jTTotDesc.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTotDescFocusLost

    
    /*Cuando se presiona una tecla en el campo del total del descuento*/
    private void jTTotDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotDescKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTotDescKeyPressed

    
    /*Cuando sucede una accion en el combobox de series*/
    private void jComSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComSerActionPerformed

        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "consecs", "WHERE tip = 'COT' AND ser = '" + jComSer.getSelectedItem() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComSer.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComSerActionPerformed

    
    //Cuando se gana el foco del teclado en el campo de la serie del producto
    private void jTSerProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTSerProd.setSelectionStart(0);jTSerProd.setSelectionEnd(jTSerProd.getText().length());
        
    }//GEN-LAST:event_jTSerProdFocusGained

    
    //Cuando se pierde el foco del teclado en el campo de la serie del producto
    private void jTSerProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerProdFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTSerProd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTSerProd.getText().compareTo("")!=0)
            jTSerProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        //Si el producto esta vacio
        if(jTProd.getText().trim().compareTo("")==0)
        {
            //carga el producto por su serie
            vCargPPS(jTSerProd.getText().trim());
            vCargP();
        }
            
    }//GEN-LAST:event_jTSerProdFocusLost

    
    /*Cuando se presiona una tecla en el campo de la serie del producto*/
    private void jTSerProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSerProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Si no a seleccionado un producto entonces*/
            if(jTDescrip.getText().compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Selecciona un producto para ver sus series primeramente.", "Series de Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }

            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTProd.getText().trim(), 34, jTSerProd, jTComenSer, null, "", null);
            b.setVisible(true);

            /*Funciòn para procesar esta parte*/
            vCargP();
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSerProdKeyPressed

    
    /*Cuando se presiona el botón de ver cmoentario en grande de la serie*/
    private void jBComenSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBComenSerActionPerformed

        /*Muestar la forma para ver la descripción en grande*/
        DescripGran b = new DescripGran(jTComenSer, null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBComenSerActionPerformed

    
    /*Cuando se presiona una tecla en el botón de comentario de la serie*/
    private void jBComenSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBComenSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBComenSerKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del kit*/
    private void jTKitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTKitFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTKit.setSelectionStart(0);jTKit.setSelectionEnd(jTKit.getText().length());

    }//GEN-LAST:event_jTKitFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del kit*/
    private void jTKitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTKitFocusLost

        /*Coloca el caret al principio del control*/
        jTKit.setCaretPosition(0);

    }//GEN-LAST:event_jTKitFocusLost

    
    /*Cuando sucede una acción en el combo de las tallas*/
    private void jComTallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComTallActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComTall.getSelectedItem()==null)
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

        /*Obtiene la descripción de la talla en base a su código*/
        try
        {
            sQ = "SELECT descrip FROM tall WHERE cod = '" + jComTall.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTDescripTall.setText(rs.getString("descrip"));
            else
                jTDescripTall.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Coloca al principio del control el caret*/
        jTDescripTall.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComTallActionPerformed

    
    /*Cuando se presiona una tecla en el combo de tallas*/
    private void jComTallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComTallKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComTallKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción de la talla*/
    private void jTDescripTallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripTallFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripTall.setSelectionStart(0);jTDescripTall.setSelectionEnd(jTDescripTall.getText().length());
        
    }//GEN-LAST:event_jTDescripTallFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción de la talla*/
    private void jTDescripTallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripTallFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDescripTall.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripTallFocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción de la talla*/
    private void jTDescripTallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripTallKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripTallKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del color*/
    private void jTDescripColoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripColoFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripColo.setSelectionStart(0);jTDescripColo.setSelectionEnd(jTDescripColo.getText().length());
        
    }//GEN-LAST:event_jTDescripColoFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del color*/
    private void jTDescripColoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripColoFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDescripColo.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripColoFocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción del color*/
    private void jTDescripColoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripColoKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripColoKeyPressed

    
    /*Cuando sucede una acción en el combo de colores*/
    private void jComColoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComColoActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComColo.getSelectedItem()==null)
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

        /*Obtiene la descripción de la talla en base a su código*/
        try
        {
            sQ = "SELECT descrip FROM colos WHERE cod = '" + jComColo.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTDescripColo.setText(rs.getString("descrip"));
            else
                jTDescripColo.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Coloca al principio del control el caret*/
        jTDescripColo.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComColoActionPerformed

    
    /*Cuando se presiona una tecla en el combo de los colores*/
    private void jComColoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComColoKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComColoKeyPressed

    
    /*Cuando se presiona una tecla en el campo del backorder*/
    private void jDBackKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDBackKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jDBackKeyPressed

    
    /*Cuando se presiona una tecla en el check de back order*/
    private void jCBackKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCBackKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCBackKeyPressed

    
    
    
    
    /*Cuando se pierde el foco del teclado en el combo de almacén*/
    private void jComAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComAlmaFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComAlma.getSelectedItem().toString().compareTo("")!=0)
            jComAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComAlmaFocusLost

    
    /*Cuando se presiona una tecla en el combo del almacén*/
    private void jComAlmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComAlmaActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComAlma.getSelectedItem()==null)
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

        /*Obtiene la descripción del almacén en base a su código*/
        try
        {
            sQ = "SELECT almadescrip FROM almas WHERE alma = '" + jComAlma.getSelectedItem().toString().trim() + "' AND alma <> '" + Star.strAlmaActFij + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTDescripAlma.setText(rs.getString("almadescrip"));
            else
                jTDescripAlma.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Coloca al principio del control el caret*/
        jTDescripAlma.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComAlmaActionPerformed

    
    /*Cuando se presiona una tecla en el combo del almacén*/
    private void jComAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComAlmaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del almacén*/
    private void jTDescripAlmaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlmaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripAlma.setSelectionStart(0);jTDescripAlma.setSelectionEnd(jTDescripAlma.getText().length());

    }//GEN-LAST:event_jTDescripAlmaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del almacén*/
    private void jTDescripAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlmaFocusLost

        /*Coloca el caret al principio del control*/
        jTDescripAlma.setCaretPosition(0);

    }//GEN-LAST:event_jTDescripAlmaFocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción del almacén*/
    private void jTDescripAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripAlmaKeyPressed

    
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
        ptovta.ProdExisAlm m = new ptovta.ProdExisAlm(jTProd.getText().trim(),jComAlma);
        m.setVisible(true);
        
    }//GEN-LAST:event_jBExisAlmaActionPerformed

    
    /*Cuando se presiona una tecla en el botón del almacén*/
    private void jBExisAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBExisAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBExisAlmaKeyPressed

    
    /*Cuando se presiona una tecla en el control de la fecha de entrega*/
    private void jDFEntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFEntKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jDFEntKeyPressed

    
    /*Cuando se presiona una tecla en el control de la fecha*/
    private void jDFechKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFechKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jDFechKeyPressed

    
    private void jTComenSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTComenSerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTComenSerActionPerformed
    
    
    /*Función para validar la modificación de los correos del cliente*/
    private void vValCliCo()
    {
        /*Si el cliente que quiere modificar es el cliente mostrador entonces*/        
        if(jCGuaCo.isSelected())
        {
            if(jTCli.getText().compareTo(Star.sCliMostG)==0)
            {
                /*Mensajea y desmarca el check*/
                JOptionPane.showMessageDialog(null, "No se puede modificar al cliente mostrador.", "Guardar Correos Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                         
                jCGuaCo.setSelected(false);
            }            
        }            
    }
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G && !bVist)
            jBGuar.doClick();
        /*Si se presiona ALT + L entonces*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_L  && !bVist)
        {
            /*Abre la forma de lotes y pedimentos*/
            LotPed p = new LotPed(this, jTProd.getText().trim(), jTProd, jTAlma2, jTLot, jTPedimen, jTCadu, jTCantLot, jTId);
            p.setVisible(true);
            
            /*Carga todos los datos del producto en los controles*/
            vCargP();                        
        }
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N  && !bVist)
            jBNew.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE  && !bVist)
            jBDel.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T  && !bVist)
            jBTod.doClick();
        /*Si se presiona F4*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4  && !bVist)
        {
            /*Marca el checkbox de mostrar archivos*/
            if(jCMostA.isSelected())
                jCMostA.setSelected(false);
            else
                jCMostA.setSelected(true);            
        }
        /*Si se presiona F5*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5  && !bVist)
        {
            /*Marca o desmarca el checkbox de imprimir*/
            if(jCImp.isSelected())
                jCImp.setSelected(false);
            else
                jCImp.setSelected(true);            
        }
        /*Si se presiona F6*/
        else if(evt.getKeyCode() == KeyEvent.VK_F6  && !bVist)
        {
            /*Marca o desmarca el checkbox de no mandar correo*/
            if(jCMand.isSelected())
                jCMand.setSelected(false);
            else
                jCMand.setSelected(true);            
        }        
        /*Si se presiona F7 presiona el botón de dollar*/        
        else if(evt.getKeyCode() == KeyEvent.VK_F7  && !bVist)
            jBTipCam.doClick();
        /*Si se presiona F8*/
        else if(evt.getKeyCode() == KeyEvent.VK_F8  && !bVist)
        {
            /*Marca o desmarca el checkbox de guardar correos*/
            if(jCGuaCo.isSelected())
                jCGuaCo.setSelected(false);
            else
                jCGuaCo.setSelected(true);            
            
            /*Función para validar la modificación de los correos del cliente*/
            vValCliCo();
        }        
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
           
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCli;
    private javax.swing.JButton jBComenSer;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBExisAlma;
    private javax.swing.JButton jBGranDescrip;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBList;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBProd;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTipCam;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBVeGran;
    private javax.swing.JCheckBox jCBack;
    private javax.swing.JCheckBox jCCo1;
    private javax.swing.JCheckBox jCCo2;
    private javax.swing.JCheckBox jCCo3;
    private javax.swing.JCheckBox jCGuaCo;
    private javax.swing.JCheckBox jCImp;
    private javax.swing.JCheckBox jCMand;
    private javax.swing.JCheckBox jCMostA;
    private javax.swing.JComboBox jComAlma;
    private javax.swing.JComboBox jComColo;
    private javax.swing.JComboBox jComImp;
    private javax.swing.JComboBox jComMon;
    private javax.swing.JComboBox jComSer;
    private javax.swing.JComboBox jComTall;
    private javax.swing.JComboBox jComUnid;
    private com.toedter.calendar.JDateChooser jDBack;
    private com.toedter.calendar.JDateChooser jDFEnt;
    private com.toedter.calendar.JDateChooser jDFVenc;
    private com.toedter.calendar.JDateChooser jDFech;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLImg;
    private javax.swing.JLabel jLNot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPClien;
    private javax.swing.JPanel jPOtr;
    private javax.swing.JPanel jPParts;
    private javax.swing.JPanel jPanImg;
    private javax.swing.JScrollPane jSImg;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTADescrip;
    private javax.swing.JTextArea jTAObserv;
    private javax.swing.JTextField jTAlma2;
    private javax.swing.JTextField jTCadu;
    private javax.swing.JTextField jTCant;
    private javax.swing.JTextField jTCantLot;
    private javax.swing.JTextField jTCli;
    private javax.swing.JTextField jTCo1;
    private javax.swing.JTextField jTCo2;
    private javax.swing.JTextField jTCo3;
    private javax.swing.JTextField jTComenSer;
    private javax.swing.JTextField jTDesc;
    private javax.swing.JTextField jTDescrip;
    private javax.swing.JTextField jTDescripAlma;
    private javax.swing.JTextField jTDescripColo;
    private javax.swing.JTextField jTDescripTall;
    private javax.swing.JTextField jTExist;
    private javax.swing.JTextField jTGara;
    private javax.swing.JTextField jTId;
    private javax.swing.JTextField jTImpue;
    private javax.swing.JTextField jTKit;
    private javax.swing.JTextField jTList;
    private javax.swing.JTextField jTLot;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTPedimen;
    private javax.swing.JTextField jTPre;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTSer;
    private javax.swing.JTextField jTSerProd;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTextField jTTotDesc;
    private javax.swing.JTextField jTUnid;
    private javax.swing.JTextField jTValImp;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevoCliente extends javax.swing.JFrame */
