//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import java.lang.Object;
import static ptovta.Princip.bIdle;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




/*Clase para ingresar una factura nueva*/
public class NewVta extends javax.swing.JFrame
{        
    /*Contiene el almacén de venta*/
    private String              sAlmGlo     = "";
    
    //Grupo de los radio buttons a elegir en si se paga o no el documento
    private ButtonGroup         bGPag       = new ButtonGroup();
    
    /*Contiene la dirección de la forma para ver imágen en otra vista*/
    private ImgVis              v;
    
    /*Contiene el texto del label*/
    private String              sLabTex;
    
    /*Declara variables originales*/
    private String              sCostOri;
    private String              sProdOri;
    private String              sCantOri;
    private String              sUnidOri;
    private String              sAlmaOri;
    private String              sListOri;
    private String              sDescripOri;
    private String              sPreOri;
    private String              sDescOri;
    private String              sImpueOri;    
    private String              sImpoOri;
    private String              sEsKitOri;
    private String              sTotImpueOri;
    private String              sTallOri;
    private String              sColoOri;
    private String              sLotOri;
    private String              sPedimenOri;
    private String              sCaduOri;
    private String              sBackOri;
    private String              sImpoDescOri;
    private String              sSerProdOri;
    private String              sComenSerOri;
    private String              sGaranOri;
    private String              sCodImpOri;
    
    //variables de control agragadas por carlos gonzalo ramirez
    private int                 sBackOrder          =    0;
    private boolean             sPrimVent            = false;
            
    /*Bandera para que no se modifique la descripción del producto en la tabla*/
    private boolean             bModDescrip         = true;
                 
    /*Declara variables privadas de clase*/    
    private JTable              jTabVe;
    private int                 iContCelEd;        
    private int                 iContFi;
    private boolean             bSiCred             = false;            
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean             bSel;
    
    /*Referencia a si mismo para que la forma de domicilio de entrega pueda decir que índice esta seleccionado*/
    private NewVta              jFram               = null;
    
    /*Variable para saber que índice es el que se tiene que usar para la factura*/
    public int                  iInd                = -1;
    
    /*Contiene el código de la cotización*/
    private String              sCodCot;
    
    //Son para el conteo de productos y series
    private double iCantTot;
    private double iCantAgr;
    
    
    /*Consructor con argumento*/
    public NewVta(JTable jTabVent, String sVta, String sCodCo) 
    {                                                               
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        //Crea el grupo de los radio buttons de pagado o no pagado
        bGPag.add       (jRPagad);
        bGPag.add       (jRNoPag);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        //Esconde los campos que no deben de estar visibles
        jTSerProd.setVisible    (false);                
        jLTipVta.setVisible     (false);                
        jTCantLot.setVisible    (false);
        
        /*Obtiene el código de la cotización del otro formulario*/
        sCodCot     = sCodCo;
        
        /*Esconde la tabla de direcciones de envio*/
        jScrTab.setVisible(false);
        
        /*Esconde el label y el campo del total menos el saldo*/
        jLTotSald.setVisible(false);
        jTTotSald.setVisible(false);
        
        /*Esconde la columna del costo*/
        jTab.getColumnModel().getColumn(24).setMinWidth(0);
        jTab.getColumnModel().getColumn(24).setMaxWidth(0);
        
        /*Esconde la columna de la cantidad original del pedimento*/
        jTab.getColumnModel().getColumn(25).setMinWidth(0);
        jTab.getColumnModel().getColumn(25).setMaxWidth(0);
        
        /*Esconde la columna del id del lote y pedimento*/
        jTab.getColumnModel().getColumn(18).setMinWidth(0);
        jTab.getColumnModel().getColumn(18).setMaxWidth(0);
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el tamaño de las columnas de la tabla*/
        jTab.getColumnModel().getColumn(1).setPreferredWidth(160);
        jTab.getColumnModel().getColumn(6).setPreferredWidth(500);
        jTab.getColumnModel().getColumn(12).setPreferredWidth(110);
        jTab.getColumnModel().getColumn(16).setPreferredWidth(170);
        jTab.getColumnModel().getColumn(17).setPreferredWidth(170);
        jTab.getColumnModel().getColumn(20).setPreferredWidth(170);
        jTab.getColumnModel().getColumn(21).setPreferredWidth(170);
        jTab.getColumnModel().getColumn(22).setPreferredWidth(170);
        jTab.getColumnModel().getColumn(23).setPreferredWidth(170);
        jTab.getColumnModel().getColumn(26).setPreferredWidth(170);
        
        /*Esconde el control de almacén*/
        jTAlma2.setVisible  (false);        
        
        /*Esconde el control del costo total de la venta*/
        jTTotCost.setVisible(false);        
        
        /*Esconde el control de la garantía*/
        jTGara.setVisible   (false);        
        
        /*Esconde el control del comentario de la serie*/
        jTComenSer.setVisible(false);        
        
        /*Esconde los contoles de pedimento, lote, caducidad e id de la partida*/
        jTLot.setVisible    (false);        
        jTPedimen.setVisible(false);        
        jTCadu.setVisible   (false);        
        jTId.setVisible     (false);                
                
        /*Incializa la tabla del formulario de facturas*/
        jTabVe      = jTabVent;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Esconde los campos que no serán visibles*/
        jTEsKit.setVisible          (false);
        jTSaldDispo.setVisible      (false);
        jTLimCred.setVisible        (false);
        jTDiaCre.setVisible         (false);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Activa en el JTextArea que se usen normamente las teclas de tabulador*/
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Activa en el control del catálog general que se usen normamente las teclas de tabulador*/
        jTACatGral.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTACatGral.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        
        /*Inicia el contador de filas de las partidas*/
        iContFi                     = 1;
        
        /*Inicialmente estará deshabilitado el control de la fecha*/
        jDFExt.setEnabled(false);
        
        /*Selecciona la fecha del día de hoy para la fecha extra y para la fecha de entrega de la mercancia*/
        Date f = new Date();
        jDFExt.setDate(f);        
        jDBack.setDate(f);        
        jDFEnt.setDate(f);
        jDFech.setDate(f);
        
        /*Establece el título de la ventana con el usuario, la fecha y hora*/                
        this.setTitle("Nueva venta, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Listener para el combobox de monedas*/
        jComMon.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

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
        
        /*Listener para el combobox de almacenes*/
        jComAlma.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

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
        
        /*Listener para el combobox de series*/
        jComSer.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                /*Carga nuevamente las series en el control*/
                vCargSer();                                               
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
                /*Carga los impuestos*/
                vCargImp();
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
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

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
             
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        //Obtiene las formas de pagos y cargalos en el combo
        if(Star.iCargFormPagCom(con, jComFormPag)==-1)
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
        
        /*Comprueba si se tiene que mostrar o no el catálogo general*/
        try
        {                  
            sQ = "SELECT val FROM confgral WHERE conf = 'catgralfac' AND clasif = 'vtas'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces si existe ya un corte Z*/                
            if(rs.next())
            {
                /*Si se tiene que mostrar entonces que sea visible y que tenga parada de tabulador*/
                if(rs.getString("val").compareTo("1")==0)
                {
                    /*El campo*/
                    jTACatGral.setVisible   (true);
                    jTACatGral.setFocusable (true);
                    
                    /*El botón*/
                    jBCatGral.setVisible   (true);
                    jBCatGral.setFocusable (true);
                    
                    /*El label*/
                    jLCatGral.setVisible    (true);
                }
                /*Else deshabilitalo*/
                else
                {
                    /*El campo*/
                    jTACatGral.setVisible   (false);
                    jTACatGral.setFocusable (false);
                    
                    /*El botón*/
                    jBCatGral.setVisible   (false);
                    jBCatGral.setFocusable (false);
                    
                    /*El label*/
                    jLCatGral.setVisible    (false);
                }

            }/*Fin de while (rs.next())*/
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }
        
        /*Comprueba si se puede modificar la lista de precio o no*/
        try
        {                  
            sQ = "SELECT val FROM confgral WHERE conf = 'modlistfac' AND clasif = 'vtas'";
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
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'moddescrip'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/                
            if(rs.next())
            {                
                /*Si se no se puede modificar la desripción entonces*/
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
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'modprec'";
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
        
        /*Agrega la serie vacia*/
        jComSer.addItem("");
        
        /*Obtiene todas las series actualizadas y cargalas en el combobox*/
        try
        {
            sQ = "SELECT ser FROM consecs WHERE tip = 'FAC'";                        
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

        /*Selecciona el elemento el elemento vacio en el control de las series*/
        jComSer.setSelectedItem("");
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        //Obtiene todas las unidades y cargalas en el combo
        if(Star.iCargUnidCom(con, jComUnid)==-1)
            return;

        //Obtiene si el usuario tiene correo asociado
        iRes        = Star.iUsrConfCorr(con, Login.sUsrG);

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Comprueba si la configuración de mostrar el mensaje esta habilitado o no
        int iMosMsj = Star.iMostMsjCorrUsr(con);

        //Si hubo error entonces regresa
        if(iMosMsj==-1)
            return;                        
        
        //Si no tiene correo asociado entonces solamente mensajea
        if(iRes==0 && iMosMsj==1)
            JOptionPane.showMessageDialog(null, "No se a definido correo electrónico para el usuario: " + Login.sUsrG + ".", "Correo electrónico",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                                    

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
        
        /*Si la venta no es nula entonces*/
        if(sVta!=null)
        {            
            /*Carga la venta machote en los campos*/
            vCargT(con, sVta);
            
            /*Coloca el foco del teclado en el botón de búscar cliente*/
            jBCli.grabFocus();
        }
        /*Else if la cotización no es cadena vacia entonces*/
        else if(sCodCot.compareTo("")!=0)
        {
            /*Carga todos los datos de la cotización en la nueva venta*/
            vCargCot(con);
            
            /*Coloca el foco del teclado en el botón de búscar cliente*/
            jBCli.grabFocus();
        }
        /*Else colcoa el foco del teclado en el campo del cliente*/
        else                    
            jTCli.grabFocus();
        
        //Carga todas las tallas en el combo
        if(Star.iCargTallCom(con, jComTall)==-1)
            return;
        
        //Carga todos los almacenes en el combo
        if(Star.iCargAlmaCom(con, jComAlma)==-1)
            return;
        
        //Obtiene el almacén configurado para venta
        sAlmGlo = Star.sGetAlmaVta(con);
        
        //Si hubo error regresa
        if(sAlmGlo==null)
            return;
        
        /*Si el almacén de venta no es cadena vacia entonces*/
        if(sAlmGlo.compareTo("")!=0)
        {
            /*Selecciona el almacén en el combo*/
            jComAlma.setSelectedItem(sAlmGlo);
            
            /*Deshabilita el combo de almacenes*/
            jComAlma.setFocusable   (false);
            jComAlma.setEnabled     (false);
        }
        
        //Trae todos los colores y cargalos en el combo
        if(Star.iCargColoCom(con, jComColo)==-1)
            return;
        
        /*Comprueba la serie fija de la factura*/        
        try
        {                  
            sQ = "SELECT extr FROM confgral WHERE clasif = 'vtas' AND conf = 'serfacfij'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si no es cadena vacia entonces*/
                if(rs.getString("extr").compareTo("")!=0)                                                                                                          
                {
                    /*Selecciona la serie y deshabilita el control*/
                    jComSer.setSelectedItem(rs.getString("extr"));
                    jComSer.setFocusable                (false);
                    jComSer.setEnabled                  (false);
                }
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }
        
        /*Comprueba la moneda fija de la factura*/        
        try
        {                  
            sQ = "SELECT extr FROM confgral WHERE clasif = 'vtas' AND conf = 'monfacfij'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si no es cadena vacia entonces*/
                if(rs.getString("extr").compareTo("")!=0)                                                                                                          
                {                    
                    /*Selecciona la serie y deshabilita el control*/
                    jComMon.setSelectedItem(rs.getString("extr"));
                    jComMon.setFocusable(false);
                    jComMon.setEnabled(false);
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

        /*Incializa el contador del cell editor*/
        iContCelEd  = 1;
        
        /*Crea el listener para la tabla de partidas, para cuando se modifique una celda recalcular los totales*/
        PropertyChangeListener propC = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección regresa*/                    
                if(jTab.getSelectedRow()==-1)
                    return;

                /*Obtén la propiedad que a sucedio en el control*/
                String prop = event.getPropertyName();                                
                                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(prop)) 
                {                                                                                     
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCelEd==1)
                    {
                        /*Obtén algunos datos originales*/                        
                        sProdOri                = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                                          
                        sCantOri                = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                                                                                                          
                        sUnidOri                = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                                                                          
                        sAlmaOri                = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                                                                          
                        sListOri                = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();                                                                                                          
                        sDescripOri             = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();                                                                                                          
                        sPreOri                 = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();                                                                                  
                        sDescOri                = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();                                                                                  
                        sImpueOri               = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();                                                                                                          
                        sImpoOri                = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();                                                                                  
                        sEsKitOri               = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        sTotImpueOri            = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();
                        sTallOri                = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();
                        sColoOri                = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();
                        sLotOri                 = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();
                        sPedimenOri             = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();
                        sCaduOri                = jTab.getValueAt(jTab.getSelectedRow(), 17).toString();
                        sBackOri                = jTab.getValueAt(jTab.getSelectedRow(), 19).toString();
                        sImpoDescOri            = jTab.getValueAt(jTab.getSelectedRow(), 20).toString();
                        sSerProdOri             = jTab.getValueAt(jTab.getSelectedRow(), 21).toString();
                        sComenSerOri            = jTab.getValueAt(jTab.getSelectedRow(), 22).toString();
                        sGaranOri               = jTab.getValueAt(jTab.getSelectedRow(), 23).toString();
                        sCodImpOri              = jTab.getValueAt(jTab.getSelectedRow(), 26).toString();
                        
                        /*Aumenta en uno el contador*/
                        ++iContCelEd;                                                                                                        
                    }
                    else if(iContCelEd >= 2)
                    {        
                        /*Reinicia el conteo*/
                        iContCelEd = 1;
                        
                        /*Coloca los valores originales que tenían*/
                        jTab.setValueAt(sProdOri,    jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sUnidOri,    jTab.getSelectedRow(), 3);
                        jTab.setValueAt(sAlmaOri,    jTab.getSelectedRow(), 4);
                        jTab.setValueAt(sListOri,    jTab.getSelectedRow(), 5);                                                
                        jTab.setValueAt(sImpueOri,   jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sImpoOri,    jTab.getSelectedRow(), 10);
                        jTab.setValueAt(sEsKitOri,   jTab.getSelectedRow(), 11);
                        jTab.setValueAt(sTotImpueOri,jTab.getSelectedRow(), 12);                                                
                        jTab.setValueAt(sTallOri,    jTab.getSelectedRow(), 13);                                                
                        jTab.setValueAt(sColoOri,    jTab.getSelectedRow(), 14);
                        jTab.setValueAt(sLotOri,     jTab.getSelectedRow(), 15);
                        jTab.setValueAt(sPedimenOri, jTab.getSelectedRow(), 16);
                        jTab.setValueAt(sCaduOri,    jTab.getSelectedRow(), 17);
                        jTab.setValueAt(sBackOri,    jTab.getSelectedRow(), 19);
                        jTab.setValueAt(sImpoDescOri,jTab.getSelectedRow(), 20);
                        jTab.setValueAt(sSerProdOri, jTab.getSelectedRow(), 21);
                        jTab.setValueAt(sComenSerOri,jTab.getSelectedRow(), 22);
                        jTab.setValueAt(sGaranOri,   jTab.getSelectedRow(), 23);
                        jTab.setValueAt(sCodImpOri, jTab.getSelectedRow(),  26);
                        
                        /*Si no se tiene que modificar la descripción entonces colocala de nuevo*/
                        if(!bModDescrip)
                            jTab.setValueAt(sDescripOri,jTab.getSelectedRow(), 6);
                        
                        /*Obtén la cantidad que el usuario ingreso*/
                        String sCant                = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();

                        /*Si el texto introducido no es númerico para la cantidad entonces*/
                        try
                        {
                            Double.parseDouble(sCant);
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca la cantidad original*/
                            jTab.setValueAt(sCantOri, jTab.getSelectedRow(), 2);

                            /*Recalcula los totales leyendo toda la tabla de partidas*/
                            vRecTots();                            
                            return;
                        }          
                        
                        /*Convierte a valor absoluto el número introducido, para quitar el negativo en caso de que lo tenga*/
                        sCant                       = Double.toString((int)Math.abs(Double.parseDouble(sCant)));                    

                        /*Vuelve a colocar el valor en la cantidad de la fila con el valor convertido a valor positivo*/
                        jTab.setValueAt(sCant, jTab.getSelectedRow(), 2);

                        /*Si la cantidad es 0 entonces*/
                        if(Double.parseDouble(sCant)==0)
                        {
                            /*La cantidad sera la original y ponerlo en la celda*/
                            sCant                   = sCantOri;
                            jTab.setValueAt(sCant, jTab.getSelectedRow(), 2);
                        }
                                                         
                        /*Si la cantidad original es 0 entonces*/
                        if(Double.parseDouble(sCantOri)==0)
                        {
                            /*Coloca la cantidad 0 y regresa*/
                            jTab.setValueAt("0", jTab.getSelectedRow(), 2);
                            return;
                        }
                        
                        /*Si el producto es por lote y pedimento entonces*/
                        if(Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 25).toString())>0)
                        {
                            /*Si la cantidad que se quiere insertar es mayor a la cantidad del lote permitido entonces*/
                            if(Double.parseDouble(sCant)>Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 25).toString()))
                            {
                                /*Coloca la cantidad original que tenía*/
                                jTab.setValueAt(sCantOri, jTab.getSelectedRow(), 2);
                                
                                /*Mensajea y regresa*/
                                JOptionPane.showMessageDialog(null, "La cantidad de lote a insertar del producto es mayor a la permitida.", "Lote y pedimento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                
                                return;
                            }

                            /*Obtiene la diferencia de lo que ya esta cargado y lo que se quiere cargar*/
                            String sDif = Double.toString(Double.parseDouble(sCant) + Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 2).toString()));

                            /*Si la diferencia es mayor que lo que se quiere insertar entonces*/
                            if(Double.parseDouble(sDif)>Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 25).toString()))
                            {
                                /*Coloca la cantidad original que tenía*/
                                jTab.setValueAt(sCantOri, jTab.getSelectedRow(), 2);
                                
                                /*Mensajea y regresa*/
                                JOptionPane.showMessageDialog(null, "La cantidad de lote a insertar: " + sCant + " mas la cantidad ya cargada: " + jTab.getValueAt(jTab.getSelectedRow(), 2).toString() + " del producto es mayor a la permitida: " + jTab.getValueAt(jTab.getSelectedRow(), 25).toString(), "Lote y pedimento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                                return;                                                
                            }                                

                        }/*Fin de if(Double.parseDouble(sCantOriLot)>0)*/

                        /*Si el precio no es númerico entonces*/
                        try
                        {
                            Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 7).toString().replace("$", "").replace(",", ""));
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca el precio original que tenía*/
                            jTab.setValueAt(sPreOri, jTab.getSelectedRow(), 7);

                            /*Recalcula los totales leyendo toda la tabla de partidas*/
                            vRecTots();                            
                            return;
                        }          
                        
                        /*Si el precio es menor o igual a 0 entonces*/
                        if(Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 7).toString().replace("$", "").replace(",", ""))<=0)
                        {
                            /*Coloca el precio que estaba anteriormente y regresa*/
                            jTab.setValueAt(sPreOri, jTab.getSelectedRow(), 7);                           
                            return;
                        }
                        
                        /*Lee el descuento*/
                        String sDesc                = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        
                        /*Si el descuento es cadena vacia entonces*/
                        if(sDesc.compareTo("")==0)
                        {
                            /*El descuento será el original y coloca el valor en la celda*/
                            sDesc                   = sDescOri;
                            jTab.setValueAt(sDesc, jTab.getSelectedRow(), 8);                           
                        }
                        
                        //Abre la base de datos nuevamente
                        Connection con = Star.conAbrBas(true, false);

                        //Si hubo error entonces regresa
                        if(con==null)
                            return;

                        //Declara variables de la base de datos
                        Statement   st;
                        ResultSet   rs;                        
                        String      sQ; 
                        
                        /*Obtiene si el producto se puede vender a bajo del costo o no*/        
                        try
                        {                  
                            sQ = "SELECT bajcost FROM prods WHERE prod = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString().trim() + "' AND alma = '" + jTab.getValueAt(jTab.getSelectedRow(), 4).toString().trim() + "'";                
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos entonces*/
                            if(rs.next())
                            {                                        
                                /*Si el producto no se puede vender abajo del costo entonces*/
                                if(rs.getString("bajcost").compareTo("0")==0)                                    
                                {
                                    //Cierra la base de datos
                                    if(Star.iCierrBas(con)==-1)
                                        return;

                                    /*Si el precio es menor al costo + cost recoger entonces*/
                                    if(Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 7).toString().replace("$", "").replace(",", "")) < Double.parseDouble(jTCost.getText().replace("$", "").replace(",", "")) )
                                    {
                                        /*Mensajea*/
                                        JOptionPane.showMessageDialog(null, "El precio es menor al costo.", "Precio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                                        /*Coloca el precio original en la celda y regresa*/
                                        jTab.setValueAt(sPreOri, jTab.getSelectedRow(), 7);                           
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
                                    /*Coloca le desc original que tenía*/
                                    jTab.setValueAt(sDescOri, jTab.getSelectedRow(), 8);

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
                                        jTab.setValueAt(sDescOri, jTab.getSelectedRow(), 8);

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
                       
                        /*Obtiene el descuento del precio*/
                        String sDescC               = Double.toString((Double.parseDouble(sDesc) / 100 ) * Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 7).toString().replace("$", "").replace(",", "")));            

                        /*Obtiene el total de descuento*/
                        String sImpoDesc            = Double.toString(Double.parseDouble(sDescC) * Double.parseDouble(sCant));
                        
                        /*Genera el importe*/
                        String sImp                 = Double.toString(Double.parseDouble(sCant) * (Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 7).toString().replace("$", "").replace(",", "")) - Double.parseDouble(sDescC)));

                        /*Crea el impuesto total de la fila*/
                        String sImpTot              = Double.toString(Double.parseDouble(sImp) * (Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 9).toString()) / 100));
                        
                        /*Dale formato de moneda al importe*/
                        double dCant                = Double.parseDouble(sImp);                   
                        NumberFormat n              = NumberFormat.getCurrencyInstance(Locale.getDefault());
                        sImp                        = n.format(dCant);

                        /*Dale formato de moneda al total del importe*/
                        dCant                       = Double.parseDouble(sImpTot);                                           
                        sImpTot                     = n.format(dCant);
                        
                        /*Dale formato de moneda al precio*/                                                
                        dCant                       = Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 7).toString().replace("$", "").replace(",", ""));                                           
                        String sPre                 = n.format(dCant);

                        /*Dale formato de moneda al importe del descuento*/
                        dCant                       = Double.parseDouble(sImpoDesc);                                           
                        sImpoDesc                   = n.format(dCant);
                        
                        /*Actualiza el importe de la fila en base a la cantidad por el precio*/
                        jTab.setValueAt(sImp, jTab.getSelectedRow(), 10);

                        /*Actualiza el precio de la fila ya con formato de moneda*/
                        jTab.setValueAt(sPre, jTab.getSelectedRow(), 7);
                        
                        /*Actualiza el importe total de la fila ya con formato de moneda*/
                        jTab.setValueAt(sImpTot, jTab.getSelectedRow(), 12);
                        
                        /*Actualiza el importe del descuento ya con formato de moneda*/
                        jTab.setValueAt(sImpoDesc, jTab.getSelectedRow(), 20);

                        /*Recalcula los totales leyendo toda la tabla de partidas*/
                        vRecTots();                                                 
                        
                    }/*Fin de else if(iContadorCellEditor >= 2)*/                                                                
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */
            
        };
        
        /*Establece el listener para la tabla de partidas*/
        jTab.addPropertyChangeListener(propC);        
        
        /*Carga los impuestos*/
        vCargImp();
        
    }/*Fin de public NewVta() */    
            

    /*Carga los impuestos en el control*/
    private void vCargImp()
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        //Carga todos los impuestos en el combo
        if(Star.iCargImpueCom(con, jComImp)==-1)
            return;
        
        //Cierra la base de datos
        Star.iCierrBas(con);        
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTDescrip = new javax.swing.JTextField();
        jP1 = new javax.swing.JPanel();
        jBGuar = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jPParts = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTDesc = new javax.swing.JTextField();
        jComUnid = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jTPre = new javax.swing.JTextField();
        jTCant = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jComImp = new javax.swing.JComboBox();
        jTValImp = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTDescripUni = new javax.swing.JTextField();
        jTDescripAlma = new javax.swing.JTextField();
        jTExist = new javax.swing.JTextField();
        jTCost = new javax.swing.JTextField();
        jTProd = new javax.swing.JTextField();
        jBProd = new javax.swing.JButton();
        jTList = new javax.swing.JTextField();
        jBUltPre = new javax.swing.JButton();
        jBList = new javax.swing.JButton();
        jBVeGran = new javax.swing.JButton();
        jBTipCam = new javax.swing.JButton();
        jSImg = new javax.swing.JScrollPane();
        jPanImg = new javax.swing.JPanel();
        jLImg = new javax.swing.JLabel();
        jComTall = new javax.swing.JComboBox();
        jComColo = new javax.swing.JComboBox();
        jTDescripColo = new javax.swing.JTextField();
        jTDescripTall = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jTKit = new javax.swing.JTextField();
        jDBack = new com.toedter.calendar.JDateChooser();
        jLabel50 = new javax.swing.JLabel();
        jCBack = new javax.swing.JCheckBox();
        jBGranDescrip = new javax.swing.JButton();
        jBUltCostT = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jComAlma = new javax.swing.JComboBox();
        jBExisAlma = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jBCarSer = new javax.swing.JButton();
        jTCarSer = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jPClien = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jTCo1 = new javax.swing.JTextField();
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
        jCGDats = new javax.swing.JCheckBox();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTCo3 = new javax.swing.JTextField();
        jCCo3 = new javax.swing.JCheckBox();
        jTCo2 = new javax.swing.JTextField();
        jCCo1 = new javax.swing.JCheckBox();
        jCCo2 = new javax.swing.JCheckBox();
        jTCli = new javax.swing.JTextField();
        jBCli = new javax.swing.JButton();
        jTNom = new javax.swing.JTextField();
        jTCond = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jCConta = new javax.swing.JCheckBox();
        jTListEmp = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTSer = new javax.swing.JTextField();
        jBDom = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jDFEnt = new com.toedter.calendar.JDateChooser();
        jLabel35 = new javax.swing.JLabel();
        jTSubTot = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTImp = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTTot = new javax.swing.JTextField();
        jTSaldDispo = new javax.swing.JTextField();
        jTLimCred = new javax.swing.JTextField();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jTDiaCre = new javax.swing.JTextField();
        jBTod = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jComSer = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jTMetPag = new javax.swing.JTextField();
        jTCta = new javax.swing.JTextField();
        jCGuaPag = new javax.swing.JCheckBox();
        jCMand = new javax.swing.JCheckBox();
        jCImp = new javax.swing.JCheckBox();
        jCMostA = new javax.swing.JCheckBox();
        jCTim = new javax.swing.JCheckBox();
        jLCatGral = new javax.swing.JLabel();
        jBCatGral = new javax.swing.JButton();
        jDFExt = new com.toedter.calendar.JDateChooser();
        jCFExt = new javax.swing.JCheckBox();
        jLabel40 = new javax.swing.JLabel();
        jSObserv = new javax.swing.JScrollPane();
        jTAObserv = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTACatGral = new javax.swing.JTextArea();
        jCRem = new javax.swing.JCheckBox();
        jDFech = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jComMon = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jComFormPag = new javax.swing.JComboBox();
        jTAlma2 = new javax.swing.JTextField();
        jTCadu = new javax.swing.JTextField();
        jTId = new javax.swing.JTextField();
        jTLot = new javax.swing.JTextField();
        jTPedimen = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTTotDesc = new javax.swing.JTextField();
        jTComenSer = new javax.swing.JTextField();
        jTGara = new javax.swing.JTextField();
        jTTotCost = new javax.swing.JTextField();
        jScrTab = new javax.swing.JScrollPane();
        jTabDir = new javax.swing.JTable();
        jTEsKit = new javax.swing.JTextField();
        jLTipVta = new javax.swing.JLabel();
        jTTotSald = new javax.swing.JTextField();
        jLTotSald = new javax.swing.JLabel();
        jLNot = new javax.swing.JLabel();
        jTCantLot = new javax.swing.JTextField();
        jTSerProd = new javax.swing.JTextField();
        jRPagad = new javax.swing.JRadioButton();
        jRNoPag = new javax.swing.JRadioButton();
        label1 = new java.awt.Label();

        jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescrip.setNextFocusableComponent(jComAlma);
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
        jP1.setEnabled(false);
        jP1.setFocusable(false);
        jP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jP1KeyPressed(evt);
            }
        });
        jP1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 120, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jRPagad);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 540, 110, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Producto", "Qty.", "Unidad", "Almacèn", "Lista", "Descripción", "Precio", "Descuento", "Impuesto", "Importe", "Es Kit", "Total Impuesto", "Talla", "Color", "Lote", "Pedimento", "Caducidad", "ID", "Backorder", "Importe Descuento ", "Serie Producto", "Comentario Serie", "Garantía", "Costo", "cantlotori", "Cod.Impuesto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true, true, true, true, true, false, false, true
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
        jScrollPane1.setViewportView(jTab);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 980, 140));

        jPParts.setBackground(new java.awt.Color(255, 255, 255));
        jPParts.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del Producto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPParts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPPartsKeyPressed(evt);
            }
        });
        jPParts.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setText("Precio:");
        jPParts.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 60, -1));

        jLabel11.setText("Desc.%:");
        jPParts.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 60, -1));

        jTDesc.setText("0");
        jTDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDesc.setNextFocusableComponent(jTCant);
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
        jPParts.add(jTDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 80, 20));

        jComUnid.setNextFocusableComponent(jTDescripUni);
        jComUnid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComUnidFocusLost(evt);
            }
        });
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
        jPParts.add(jComUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 90, 20));

        jLabel12.setText("Cod. Color:");
        jPParts.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 87, -1));

        jTPre.setText("$0.00");
        jTPre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre.setNextFocusableComponent(jTDesc);
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
        jPParts.add(jTPre, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 80, 20));

        jTCant.setText("1");
        jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCant.setNextFocusableComponent(jTList);
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
        jPParts.add(jTCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 80, 20));

        jLabel15.setText("U.Costo:");
        jPParts.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 60, -1));

        jLabel17.setText("Cod. Impuesto:");
        jPParts.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 110, -1));

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
        jPParts.add(jComImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 90, 20));

        jTValImp.setEditable(false);
        jTValImp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTValImp.setNextFocusableComponent(jTPre);
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
        jPParts.add(jTValImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 80, 20));

        jLabel19.setText("Cod. Producto:");
        jPParts.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 110, -1));

        jTDescripUni.setEditable(false);
        jTDescripUni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripUni.setNextFocusableComponent(jComImp);
        jTDescripUni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripUniFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripUniFocusLost(evt);
            }
        });
        jTDescripUni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripUniKeyPressed(evt);
            }
        });
        jPParts.add(jTDescripUni, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 80, 20));

        jTDescripAlma.setEditable(false);
        jTDescripAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripAlma.setNextFocusableComponent(jComColo);
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
        jPParts.add(jTDescripAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 70, 20));

        jTExist.setEditable(false);
        jTExist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jPParts.add(jTExist, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 40, 100, 20));

        jTCost.setEditable(false);
        jTCost.setText("$0.00");
        jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCostFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCostFocusLost(evt);
            }
        });
        jTCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCostKeyPressed(evt);
            }
        });
        jPParts.add(jTCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 100, 20));

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
        jPParts.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 60, 20));

        jBProd.setBackground(new java.awt.Color(255, 255, 255));
        jBProd.setText("...");
        jBProd.setToolTipText("Buscar Producto(s)");
        jBProd.setNextFocusableComponent(jTDescrip);
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
        jPParts.add(jBProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 30, 20));

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
        jPParts.add(jTList, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 50, 20));

        jBUltPre.setBackground(new java.awt.Color(255, 255, 255));
        jBUltPre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ultpre.png"))); // NOI18N
        jBUltPre.setToolTipText("Ultimos 100 Precios de Venta por este Cliente");
        jBUltPre.setNextFocusableComponent(jBTipCam);
        jBUltPre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBUltPreMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBUltPreMouseExited(evt);
            }
        });
        jBUltPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUltPreActionPerformed(evt);
            }
        });
        jBUltPre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBUltPreKeyPressed(evt);
            }
        });
        jPParts.add(jBUltPre, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, 40, 40));

        jBList.setBackground(new java.awt.Color(255, 255, 255));
        jBList.setText("jButton1");
        jBList.setToolTipText("Buscar Listas de Precio");
        jBList.setName(""); // NOI18N
        jBList.setNextFocusableComponent(jBUltCostT);
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
        jPParts.add(jBList, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 30, 20));

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
        jPParts.add(jBVeGran, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, 30, 20));

        jBTipCam.setBackground(new java.awt.Color(255, 255, 255));
        jBTipCam.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBTipCam.setForeground(new java.awt.Color(0, 102, 0));
        jBTipCam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dol.png"))); // NOI18N
        jBTipCam.setToolTipText("Definir el tipo de cambio dollar del dìa");
        jBTipCam.setNextFocusableComponent(jDBack);
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
        jPParts.add(jBTipCam, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 40, 40));

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
                .addGap(0, 80, Short.MAX_VALUE))
        );
        jPanImgLayout.setVerticalGroup(
            jPanImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanImgLayout.createSequentialGroup()
                .addComponent(jLImg)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        jSImg.setViewportView(jPanImg);

        jPParts.add(jSImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, 60, 80));

        jComTall.setNextFocusableComponent(jTDescripTall);
        jComTall.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComTallFocusLost(evt);
            }
        });
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
        jPParts.add(jComTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 90, 20));

        jComColo.setNextFocusableComponent(jTDescripColo);
        jComColo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComColoFocusLost(evt);
            }
        });
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
        jPParts.add(jComColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 90, 20));

        jTDescripColo.setEditable(false);
        jTDescripColo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripColo.setNextFocusableComponent(jComUnid);
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
        jPParts.add(jTDescripColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 80, 20));

        jTDescripTall.setEditable(false);
        jTDescripTall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripTall.setNextFocusableComponent(jComColo);
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
        jPParts.add(jTDescripTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 80, 20));

        jLabel13.setText("Cod. Unidad:");
        jPParts.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 87, -1));

        jLabel14.setText("Cod. Talla:");
        jPParts.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 87, -1));

        jLabel46.setText("Existencia:");
        jPParts.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, 70, -1));

        jLabel49.setText("L.Precio:");
        jPParts.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 60, 20));

        jTKit.setEditable(false);
        jTKit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTKit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTKitFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTKitFocusLost(evt);
            }
        });
        jPParts.add(jTKit, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, 100, 20));

        jDBack.setNextFocusableComponent(jCBack);
        jDBack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDBackKeyPressed(evt);
            }
        });
        jPParts.add(jDBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 100, -1));

        jLabel50.setText("Es Kit:");
        jPParts.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 60, 20));

        jCBack.setBackground(new java.awt.Color(255, 255, 255));
        jCBack.setText("Backorder");
        jCBack.setNextFocusableComponent(jTSerProd);
        jCBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBackActionPerformed(evt);
            }
        });
        jCBack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCBackKeyPressed(evt);
            }
        });
        jPParts.add(jCBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 100, 120, -1));

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
        jPParts.add(jBGranDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 10, 20));

        jBUltCostT.setBackground(new java.awt.Color(255, 255, 255));
        jBUltCostT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ultprect.png"))); // NOI18N
        jBUltCostT.setToolTipText("Ultimos 100 Precios de Venta de todos los Clientes");
        jBUltCostT.setNextFocusableComponent(jBUltPre);
        jBUltCostT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBUltCostTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBUltCostTMouseExited(evt);
            }
        });
        jBUltCostT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUltCostTActionPerformed(evt);
            }
        });
        jBUltCostT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBUltCostTKeyPressed(evt);
            }
        });
        jPParts.add(jBUltCostT, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 40, 40));

        jLabel16.setText("Almacén:");
        jPParts.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 87, -1));

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
        jPParts.add(jComAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 90, 20));

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
        jPParts.add(jBExisAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 10, 20));

        jLabel42.setText("Qty:");
        jPParts.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 60, -1));

        jBCarSer.setBackground(new java.awt.Color(255, 255, 255));
        jBCarSer.setText("..");
        jBCarSer.setToolTipText("Buscar archivo EXCEL para series con dos columnas");
        jBCarSer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCarSerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCarSerMouseExited(evt);
            }
        });
        jBCarSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCarSerActionPerformed(evt);
            }
        });
        jBCarSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCarSerKeyPressed(evt);
            }
        });
        jPParts.add(jBCarSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, 30, 20));

        jTCarSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCarSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCarSerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCarSerFocusLost(evt);
            }
        });
        jTCarSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCarSerKeyPressed(evt);
            }
        });
        jPParts.add(jTCarSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 160, 20));

        jLabel23.setText("Importar números de serie:");
        jPParts.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, 190, 20));

        jP1.add(jPParts, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 710, 175));

        jPClien.setBackground(new java.awt.Color(255, 255, 255));
        jPClien.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del Cliente", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPClien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Cod. Proveedor:");
        jPClien.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 110, -1));

        jLabel6.setText("*CP:");
        jPClien.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 70, -1));

        jLabel20.setText("*Cliente:");
        jPClien.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 70, -1));

        jLabel24.setText("*Calle:");
        jPClien.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 70, -1));

        jLabel25.setText("Correo 3:");
        jPClien.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, 70, -1));

        jLabel29.setText("Teléfono:");
        jPClien.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 70, -1));

        jLabel30.setText("*Colonia:");
        jPClien.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 70, -1));

        jLabel31.setText("*No. Exterior:");
        jPClien.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 90, -1));

        jLabel32.setText("No. Interior:");
        jPClien.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 90, -1));

        jLabel34.setText("*Ciudad:");
        jPClien.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, 70, -1));

        jLabel36.setText("Crédito:");
        jPClien.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 230, -1));

        jLabel37.setText("Lista:");
        jPClien.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, 70, -1));

        jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo1.setNextFocusableComponent(jCCo1);
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
        jPClien.add(jTCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 130, 20));

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCallKeyTyped(evt);
            }
        });
        jPClien.add(jTCall, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 160, 20));

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTColKeyTyped(evt);
            }
        });
        jPClien.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 160, 20));

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelKeyTyped(evt);
            }
        });
        jPClien.add(jTTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 160, 20));

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPaiKeyTyped(evt);
            }
        });
        jPClien.add(jTPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 160, 20));

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCPKeyTyped(evt);
            }
        });
        jPClien.add(jTCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 100, 20));

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
        jPClien.add(jTNoExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 100, 20));

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTNoIntKeyTyped(evt);
            }
        });
        jPClien.add(jTNoInt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 100, 20));

        jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRFC.setNextFocusableComponent(jCConta);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTRFCKeyTyped(evt);
            }
        });
        jPClien.add(jTRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, 100, 20));

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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCiuKeyTyped(evt);
            }
        });
        jPClien.add(jTCiu, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 130, 20));

        jTEstad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEstad.setNextFocusableComponent(jTCo1);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTEstadKeyTyped(evt);
            }
        });
        jPClien.add(jTEstad, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 130, 20));

        jCGDats.setBackground(new java.awt.Color(255, 255, 255));
        jCGDats.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCGDats.setText("Mod. datos del cliente F11");
        jCGDats.setNextFocusableComponent(jDFech);
        jCGDats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCGDatsActionPerformed(evt);
            }
        });
        jCGDats.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGDatsKeyPressed(evt);
            }
        });
        jPClien.add(jCGDats, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, 170, -1));

        jLabel38.setText("Correo 1:");
        jPClien.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, 70, -1));

        jLabel39.setText("Correo 2:");
        jPClien.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 110, 70, -1));

        jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo3.setNextFocusableComponent(jCCo3);
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
        jPClien.add(jTCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 130, 20));

        jCCo3.setBackground(new java.awt.Color(255, 255, 255));
        jCCo3.setNextFocusableComponent(jCGDats);
        jCCo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo3KeyPressed(evt);
            }
        });
        jPClien.add(jCCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 130, -1, -1));

        jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo2.setNextFocusableComponent(jCCo2);
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
        jPClien.add(jTCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 110, 130, 20));

        jCCo1.setBackground(new java.awt.Color(255, 255, 255));
        jCCo1.setSelected(true);
        jCCo1.setNextFocusableComponent(jTCo2);
        jCCo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo1KeyPressed(evt);
            }
        });
        jPClien.add(jCCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, -1, -1));

        jCCo2.setBackground(new java.awt.Color(255, 255, 255));
        jCCo2.setNextFocusableComponent(jTCo3);
        jCCo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo2KeyPressed(evt);
            }
        });
        jPClien.add(jCCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 110, -1, -1));

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
        jPClien.add(jTCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 130, 20));

        jBCli.setBackground(new java.awt.Color(255, 255, 255));
        jBCli.setText("...");
        jBCli.setToolTipText("Buscar Cliente(s)");
        jBCli.setNextFocusableComponent(jTCall);
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
        jPClien.add(jBCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 30, 20));

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
        jPClien.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 320, 20));

        jTCond.setEditable(false);
        jTCond.setBackground(new java.awt.Color(255, 255, 204));
        jTCond.setForeground(new java.awt.Color(0, 153, 0));
        jTCond.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTCond.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCond.setFocusable(false);
        jTCond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTCondActionPerformed(evt);
            }
        });
        jPClien.add(jTCond, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 320, 20));

        jLabel45.setText("Pais:");
        jPClien.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 70, -1));

        jCConta.setBackground(new java.awt.Color(255, 255, 255));
        jCConta.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCConta.setText("Contado F8");
        jCConta.setNextFocusableComponent(jBDom);
        jCConta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCContaActionPerformed(evt);
            }
        });
        jCConta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCContaKeyPressed(evt);
            }
        });
        jPClien.add(jCConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 90, -1));

        jTListEmp.setEditable(false);
        jTListEmp.setBackground(new java.awt.Color(255, 255, 255));
        jTListEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTListEmp.setFocusable(false);
        jTListEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTListEmpFocusLost(evt);
            }
        });
        jPClien.add(jTListEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 100, 20));

        jLabel47.setText("*RFC:");
        jPClien.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 70, -1));

        jTSer.setEditable(false);
        jTSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSerFocusLost(evt);
            }
        });
        jPClien.add(jTSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 70, 20));

        jBDom.setBackground(new java.awt.Color(255, 255, 255));
        jBDom.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBDom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/doment.png"))); // NOI18N
        jBDom.setToolTipText("Definir Domicilio de Entrega");
        jBDom.setNextFocusableComponent(jDFEnt);
        jBDom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDomMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDomMouseExited(evt);
            }
        });
        jBDom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDomActionPerformed(evt);
            }
        });
        jBDom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDomKeyPressed(evt);
            }
        });
        jPClien.add(jBDom, new org.netbeans.lib.awtextra.AbsoluteConstraints(481, 150, 30, 30));

        jLabel41.setText("*Estado:");
        jPClien.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 70, -1));

        jDFEnt.setNextFocusableComponent(jTCiu);
        jDFEnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFEntKeyPressed(evt);
            }
        });
        jPClien.add(jDFEnt, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 130, -1));

        jLabel35.setText("Fecha entrega:");
        jPClien.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 90, -1));

        jP1.add(jPClien, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 710, 183));

        jTSubTot.setEditable(false);
        jTSubTot.setBackground(new java.awt.Color(204, 255, 204));
        jTSubTot.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTSubTot.setForeground(new java.awt.Color(51, 51, 0));
        jTSubTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTSubTot.setText("$0.00");
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
        jP1.add(jTSubTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 540, 120, 20));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Sub Total:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 540, 110, 20));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Impuesto:");
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 580, 110, 20));

        jTImp.setEditable(false);
        jTImp.setBackground(new java.awt.Color(204, 255, 204));
        jTImp.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTImp.setForeground(new java.awt.Color(51, 51, 0));
        jTImp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTImp.setText("$0.00");
        jTImp.setNextFocusableComponent(jBGuar);
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
        jP1.add(jTImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 580, 120, 20));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Descuento:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 560, 110, 20));

        jTTot.setEditable(false);
        jTTot.setBackground(new java.awt.Color(204, 255, 204));
        jTTot.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTTot.setForeground(new java.awt.Color(51, 51, 0));
        jTTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTot.setText("$0.00");
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
        jP1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 600, 120, 20));

        jTSaldDispo.setEditable(false);
        jTSaldDispo.setFocusable(false);
        jP1.add(jTSaldDispo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 620, 10, -1));

        jTLimCred.setEditable(false);
        jTLimCred.setFocusable(false);
        jP1.add(jTLimCred, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 620, 10, -1));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 220, 20));

        jTDiaCre.setEditable(false);
        jTDiaCre.setFocusable(false);
        jP1.add(jTDiaCre, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 620, 10, -1));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar todo");
        jBTod.setToolTipText("Marcar Todos los Registros de la Tabla (Alt+T)");
        jBTod.setName(""); // NOI18N
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 382, 130, 18));

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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 110, 20));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Partida(s) (Ctrl+SUPR)");
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, 110, 20));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Encabezado", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Fecha:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 80, -1));

        jLabel22.setText("*Serie:");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, -1));

        jComSer.setNextFocusableComponent(jTMetPag);
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
        jPanel1.add(jComSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 120, 20));

        jLabel44.setText("Forma pago:");
        jPanel1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, -1));

        jLabel48.setText("Cuenta:");
        jPanel1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, -1));

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
        jPanel1.add(jTMetPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 120, 20));

        jTCta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCta.setNextFocusableComponent(jCGuaPag);
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
        jPanel1.add(jTCta, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 120, 20));

        jCGuaPag.setBackground(new java.awt.Color(255, 255, 255));
        jCGuaPag.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCGuaPag.setText("Guardar pago F7");
        jCGuaPag.setName(""); // NOI18N
        jCGuaPag.setNextFocusableComponent(jCRem);
        jCGuaPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGuaPagKeyPressed(evt);
            }
        });
        jPanel1.add(jCGuaPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 140, 20));

        jCMand.setBackground(new java.awt.Color(255, 255, 255));
        jCMand.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMand.setSelected(true);
        jCMand.setText("Mandar correo F4");
        jCMand.setNextFocusableComponent(jCImp);
        jCMand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMandKeyPressed(evt);
            }
        });
        jPanel1.add(jCMand, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 130, -1));

        jCImp.setBackground(new java.awt.Color(255, 255, 255));
        jCImp.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCImp.setText("Imprimir F5");
        jCImp.setNextFocusableComponent(jComMon);
        jCImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCImpKeyPressed(evt);
            }
        });
        jPanel1.add(jCImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 130, -1));

        jCMostA.setBackground(new java.awt.Color(255, 255, 255));
        jCMostA.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMostA.setText("Mostrar archivo F6");
        jCMostA.setNextFocusableComponent(jCTim);
        jCMostA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMostAKeyPressed(evt);
            }
        });
        jPanel1.add(jCMostA, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 130, -1));

        jCTim.setBackground(new java.awt.Color(255, 255, 255));
        jCTim.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCTim.setSelected(true);
        jCTim.setText("Timbrar");
        jCTim.setNextFocusableComponent(jTACatGral);
        jCTim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCTimKeyPressed(evt);
            }
        });
        jPanel1.add(jCTim, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 100, 20));

        jLCatGral.setText("Catálogo general");
        jPanel1.add(jLCatGral, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 130, -1));

        jBCatGral.setBackground(new java.awt.Color(255, 255, 255));
        jBCatGral.setText("...");
        jBCatGral.setToolTipText("Buscar Catálogo General");
        jBCatGral.setNextFocusableComponent(jCFExt);
        jBCatGral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCatGralActionPerformed(evt);
            }
        });
        jBCatGral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCatGralKeyPressed(evt);
            }
        });
        jPanel1.add(jBCatGral, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 30, 20));

        jDFExt.setNextFocusableComponent(jTAObserv);
        jDFExt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFExtKeyPressed(evt);
            }
        });
        jPanel1.add(jDFExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 140, -1));

        jCFExt.setBackground(new java.awt.Color(255, 255, 255));
        jCFExt.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCFExt.setText("Fecha extra F12");
        jCFExt.setNextFocusableComponent(jDFExt);
        jCFExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCFExtActionPerformed(evt);
            }
        });
        jCFExt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCFExtKeyPressed(evt);
            }
        });
        jPanel1.add(jCFExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 140, 20));

        jLabel40.setText("Observaciones:");
        jPanel1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 110, -1));

        jSObserv.setNextFocusableComponent(jTCli);
        jSObserv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSObservKeyPressed(evt);
            }
        });

        jTAObserv.setColumns(20);
        jTAObserv.setRows(5);
        jTAObserv.setNextFocusableComponent(jTProd);
        jTAObserv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAObservFocusLost(evt);
            }
        });
        jTAObserv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAObservKeyPressed(evt);
            }
        });
        jSObserv.setViewportView(jTAObserv);

        jPanel1.add(jSObserv, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 230, 30));

        jTACatGral.setColumns(20);
        jTACatGral.setLineWrap(true);
        jTACatGral.setRows(5);
        jTACatGral.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTACatGralFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTACatGralFocusLost(evt);
            }
        });
        jTACatGral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTACatGralKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTACatGral);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 226, 230, 43));

        jCRem.setBackground(new java.awt.Color(255, 255, 255));
        jCRem.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCRem.setText("Remisión Alt+R");
        jCRem.setToolTipText("La venta es una Remisión");
        jCRem.setNextFocusableComponent(jCMand);
        jCRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCRemActionPerformed(evt);
            }
        });
        jCRem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCRemKeyPressed(evt);
            }
        });
        jPanel1.add(jCRem, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 110, -1));

        jDFech.setNextFocusableComponent(jComSer);
        jDFech.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFechKeyPressed(evt);
            }
        });
        jPanel1.add(jDFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 120, -1));

        jLabel9.setText("Método pago:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 110, -1));

        jComMon.setNextFocusableComponent(jComFormPag);
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
        jPanel1.add(jComMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 90, 20));

        jLabel18.setText("Moneda:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 110, -1));

        jComFormPag.setNextFocusableComponent(jCMostA);
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
        jPanel1.add(jComFormPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 90, -1));

        jP1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, 270, 365));

        jTAlma2.setEditable(false);
        jTAlma2.setFocusable(false);
        jP1.add(jTAlma2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 620, 10, -1));

        jTCadu.setEditable(false);
        jTCadu.setFocusable(false);
        jP1.add(jTCadu, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 620, 10, -1));

        jTId.setEditable(false);
        jTId.setFocusable(false);
        jP1.add(jTId, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 620, 10, -1));

        jTLot.setEditable(false);
        jTLot.setFocusable(false);
        jP1.add(jTLot, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 620, 10, -1));

        jTPedimen.setEditable(false);
        jTPedimen.setFocusable(false);
        jP1.add(jTPedimen, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 620, 10, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("Total:");
        jP1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 600, 110, 20));

        jTTotDesc.setEditable(false);
        jTTotDesc.setBackground(new java.awt.Color(204, 255, 204));
        jTTotDesc.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTTotDesc.setForeground(new java.awt.Color(51, 51, 0));
        jTTotDesc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTotDesc.setText("$0.00");
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
        jP1.add(jTTotDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 560, 120, 20));

        jTComenSer.setEditable(false);
        jTComenSer.setFocusable(false);
        jP1.add(jTComenSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 620, 10, 20));

        jTGara.setEditable(false);
        jTGara.setFocusable(false);
        jP1.add(jTGara, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 620, 10, -1));

        jTTotCost.setEditable(false);
        jTTotCost.setFocusable(false);
        jP1.add(jTTotCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 620, 10, -1));

        jScrTab.setEnabled(false);
        jScrTab.setFocusable(false);

        jTabDir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "tel", "lada", "exten", "cel", "telper1", "telper2", "calle", "col", "noext", "noint", "cp", "ciu", "estad", "pais", "co1", "co2", "co3"
            }
        ));
        jTabDir.setEnabled(false);
        jTabDir.setFocusable(false);
        jTabDir.setShowHorizontalLines(false);
        jTabDir.setShowVerticalLines(false);
        jScrTab.setViewportView(jTabDir);

        jP1.add(jScrTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 620, 20, 20));

        jTEsKit.setEditable(false);
        jTEsKit.setFocusable(false);
        jP1.add(jTEsKit, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 620, 10, 20));

        jLTipVta.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLTipVta.setText("VENTA A CRÉDITO");
        jLTipVta.setFocusable(false);
        jP1.add(jLTipVta, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 580, 500, -1));

        jTTotSald.setEditable(false);
        jTTotSald.setBackground(new java.awt.Color(255, 204, 204));
        jTTotSald.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTTotSald.setForeground(new java.awt.Color(51, 51, 0));
        jTTotSald.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTotSald.setText("$0.00");
        jTTotSald.setNextFocusableComponent(jBGuar);
        jTTotSald.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTotSaldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTotSaldFocusLost(evt);
            }
        });
        jTTotSald.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTotSaldKeyPressed(evt);
            }
        });
        jP1.add(jTTotSald, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 620, 120, 20));

        jLTotSald.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLTotSald.setForeground(new java.awt.Color(204, 0, 51));
        jLTotSald.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLTotSald.setText("Total a pagar:");
        jP1.add(jLTotSald, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 620, 160, 20));

        jLNot.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLNot.setForeground(new java.awt.Color(204, 0, 0));
        jLNot.setText("NUEVA FACTURA");
        jLNot.setFocusable(false);
        jP1.add(jLNot, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 540, 500, -1));

        jTCantLot.setEditable(false);
        jTCantLot.setText("0");
        jP1.add(jTCantLot, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 620, 10, -1));

        jTSerProd.setEditable(false);
        jTSerProd.setFocusable(false);
        jP1.add(jTSerProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 620, 10, 20));

        jRPagad.setBackground(new java.awt.Color(255, 255, 255));
        jRPagad.setSelected(true);
        jRPagad.setText("Pagada");
        jRPagad.setNextFocusableComponent(jRNoPag);
        jRPagad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRPagadActionPerformed(evt);
            }
        });
        jRPagad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRPagadKeyPressed(evt);
            }
        });
        jP1.add(jRPagad, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 620, 70, -1));

        jRNoPag.setBackground(new java.awt.Color(255, 255, 255));
        jRNoPag.setText("No pagada");
        jRNoPag.setNextFocusableComponent(jTCli);
        jRNoPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRNoPagActionPerformed(evt);
            }
        });
        jRNoPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRNoPagKeyPressed(evt);
            }
        });
        jP1.add(jRNoPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 620, -1, -1));

        label1.setText("Presionar F2 para aplicar nota de crédito");
        jP1.add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 380, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 1012, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
        
            
    /*Función para cargar todos los datos de la venta en los controles*/
    private void vCargT(Connection con, String sVta)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Declara la serie y el tipo
        String sNoSer   = "";
        String sTip     = "";
        
        
        
        
        /*Obtiene el encabezado de la venta*/        
        try
        {
            sQ = "SELECT emps.SER, vtas.IMPUE, vtas.TOT, vtas.SUBTOT, vtas.OBSERV, emps.METPAG, emps.CTA, vtas.NOSER, vtas.TIPDOC, CASE WHEN emps.DIACRED = '' THEN 0 ELSE emps.DIACRED END AS diacred, emps.LIMTCRED, emps.CO1, emps.CO2, emps.CO3, emps.ESTAD, emps.CIU, emps.RFC, emps.LIST, emps.NOEXT, emps.NOINT, emps.CP, emps.PAI, emps.TEL, emps.COL, emps.CALLE, emps.NOM, vtas.SER, vtas.CODEMP FROM vtas LEFT OUTER JOIN emps ON CONCAT_WS('',emps.SER, emps.CODEMP ) = vtas.CODEMP WHERE vtas.VTA = " + sVta;	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene los totales*/
                String sTot     = rs.getString("vtas.TOT");
                String sSubTot  = rs.getString("vtas.SUBTOT");                
                String sImpue   = rs.getString("vtas.IMPUE");                
                
                /*Dale formato de moneda a los totales*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sSubTot);                
                sSubTot         = n.format(dCant);
                dCant           = Double.parseDouble(sImpue);                
                sImpue          = n.format(dCant);
                dCant           = Double.parseDouble(sTot);                
                sTot            = n.format(dCant);
                
                /*Coloca los totales en el control*/
                jTTot.setText           (sTot);
                jTImp.setText           (sImpue);
                jTSubTot.setText        (sSubTot);
                
                /*Coloca todos los datos en los controles de encabezado*/
                jTCli.setText           (rs.getString("vtas.CODEMP"));
                jTSer.setText           (rs.getString("emps.SER"));
                jTNom.setText           (rs.getString("emps.NOM"));
                jTCall.setText          (rs.getString("emps.CALLE"));
                jTCol.setText           (rs.getString("emps.COL"));
                jTTel.setText           (rs.getString("emps.TEL"));                
                jTPai.setText           (rs.getString("emps.PAI"));                
                jTCP.setText            (rs.getString("emps.CP"));                
                jTNoExt.setText         (rs.getString("emps.NOEXT"));                
                jTNoInt.setText         (rs.getString("emps.NOINT"));                
                jTRFC.setText           (rs.getString("emps.RFC"));                
                jTListEmp.setText       (rs.getString("emps.LIST"));                
                jTCiu.setText           (rs.getString("emps.CIU"));                
                jTEstad.setText         (rs.getString("emps.ESTAD"));                
                jTCo3.setText           (rs.getString("emps.CO1"));                
                jTCo2.setText           (rs.getString("emps.CO2"));                
                jTCo3.setText           (rs.getString("emps.CO3"));                                
                jTDiaCre.setText        (rs.getString("diacred"));                
                jTMetPag.setText        (rs.getString("emps.METPAG"));
                jTCta.setText           (rs.getString("emps.CTA"));
                jTAObserv.setText       (rs.getString("vtas.OBSERV"));
                
                /*Función para que coloca las condiciones*/
                vCond(con, jTCond, rs.getString("emps.LIMTCRED"), rs.getString("diacred"));                                
                
                /*Obtiene el código de la serie de la venta*/
                sNoSer                  = rs.getString("vtas.NOSER");
                
                /*Obtiene el tipo de venta que es*/
                sTip                    = rs.getString("vtas.TIPDOC");
            }
        }   
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }                            
        
        /*Obtiene la descripción de la serie de la venta*/
        try
        {
            sQ = "SELECT descrip FROM consecs WHERE tip = '" + sTip + "' AND ser = '" + sNoSer + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces seleccionalo en el combobox por descripción*/
            if(rs.next())                       
                jComSer.setSelectedItem(rs.getString("descrip"));                           
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }
        
        /*Obtiene todas las partidas de la venta*/
        try
        {
            sQ = "SELECT * FROM partvta WHERE vta = " + sVta;	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())          
            {
                /*Obtiene los totales*/
                String sPre     = rs.getString("pre");
                String sImpo    = rs.getString("impo");                
                
                /*Dale formato de moneda a los totales*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sPre);                
                sPre            = n.format(dCant);
                dCant           = Double.parseDouble(sImpo);                
                sImpo           = n.format(dCant);
                
                /*Agrega el registro en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("prod"), rs.getString("cant"), rs.getString("unid"), rs.getString("alma"), rs.getString("list"), rs.getString("descrip"), sPre, rs.getString("descu"), rs.getString("impue"), sImpo, rs.getString("eskit"), "0", rs.getString("tall"), rs.getString("colo"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("fcadu"), "", rs.getString("codimpue")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas*/
                ++iContFi;
            }                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                    
        }
        
    }/*Fin de private void vCargT(con)*/
    
    
    /*Función para cargar los datos de la cotización en los controles*/
    private void vCargCot(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Obtiene el encabezado de la cotización*/
        try
        {
            sQ = "SELECT cots.TOTDESCU, cots.FENTRE, cots.MON, cots.TOTCOST, emps.LIMTCRED, cots.OBSERV, emps.METPAG, emps.CTA, CASE WHEN emps.DIACRED = '' THEN 0 ELSE emps.DIACRED END AS diacred, emps.CIU, emps.ESTAD, emps.CO1, emps.CO2, emps.CO3, emps.RFC, emps.LIST, emps.PAI, emps.CP, emps.NOEXT, emps.NOINT, emps.CALLE, emps.COL, emps.TEL, emps.NOM, cots.SER, cots.CODEMP, cots.SUBTOT, cots.IMPUE, cots.TOT FROM cots LEFT OUTER JOIN emps ON CONCAT_WS('',emps.SER, emps.CODEMP) = cots.CODEMP WHERE cots.CODCOT = '" + sCodCot + "'";	
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
                
                /*Coloca el costo total en su control*/
                jTTotCost.setText       (rs.getString("totcost"));
                
                /*Coloca los totales en el control*/
                jTTot.setText           (sTot);
                jTImp.setText           (sImpue);
                jTSubTot.setText        (sSubTot);
                jTTotDesc.setText       (sTotDesc);
                                
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
                
                /*Coloca todos los datos en los controles de encabezado*/
                jTCli.setText           (rs.getString("codemp"));
                jTSer.setText           (rs.getString("ser"));
                jTNom.setText           (rs.getString("nom"));
                jComMon.setSelectedItem (rs.getString("mon"));                
                jTCall.setText          (rs.getString("calle"));
                jTCol.setText           (rs.getString("col"));
                jTTel.setText           (rs.getString("tel"));                
                jTPai.setText           (rs.getString("pai"));                
                jTCP.setText            (rs.getString("cp"));                
                jTNoExt.setText         (rs.getString("noext"));                
                jTNoInt.setText         (rs.getString("noint"));                
                jTRFC.setText           (rs.getString("rfc"));                
                jTListEmp.setText       (rs.getString("list"));                
                jTCiu.setText           (rs.getString("ciu"));                
                jTEstad.setText         (rs.getString("estad"));                
                jTCo3.setText           (rs.getString("co1"));                
                jTCo2.setText           (rs.getString("co2"));                
                jTCo3.setText           (rs.getString("co3"));                                
                jTDiaCre.setText        (rs.getString("diacred"));                
                jTMetPag.setText        (rs.getString("metpag"));
                jTCta.setText           (rs.getString("cta"));
                jTAObserv.setText       (rs.getString("observ"));
            
                /*Coloca todos los controles al principio para que sean visibles*/                
                jTNom.setCaretPosition      (0);
                jTSer.setCaretPosition      (0);
                jTCall.setCaretPosition     (0);
                jTCol.setCaretPosition      (0);
                jTTel.setCaretPosition      (0);
                jTPai.setCaretPosition      (0);
                jTCP.setCaretPosition       (0);
                jTNoExt.setCaretPosition    (0);
                jTNoInt.setCaretPosition    (0);
                jTRFC.setCaretPosition      (0);
                jTCiu.setCaretPosition      (0);
                jTEstad.setCaretPosition    (0);
                jTCo1.setCaretPosition      (0);
                jTCo2.setCaretPosition      (0);
                jTCo3.setCaretPosition      (0);
                jTNom.setCaretPosition      (0);                                
                
                /*Función para que coloque las condiciones*/
                vCond(con, jTCond, rs.getString("limtcred"), rs.getString("diacred"));                                                                
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
            sQ = "SELECT * FROM partcot WHERE codcot = '" + sCodCot + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())          
            {
                /*Obtiene los totales*/
                String sPre         = rs.getString("pre");
                String sImpo        = rs.getString("impo");                
                String sImpueImpo   = rs.getString("impueimpo");
                String sCost        = rs.getString("cost");
                String sImpoDesc    = rs.getString("impodesc");
                
                /*Dale formato de moneda a los totales*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sPre);                
                sPre            = n.format(dCant);
                dCant           = Double.parseDouble(sImpo);                
                sImpo           = n.format(dCant);
                dCant           = Double.parseDouble(sImpueImpo);                
                sImpueImpo      = n.format(dCant);
                dCant           = Double.parseDouble(sCost);                
                sCost           = n.format(dCant);
                dCant           = Double.parseDouble(sImpoDesc);                
                sImpoDesc       = n.format(dCant);
                
                /*Determina si es un kit o no*/
                String sKit     = "No";
                if(rs.getString("eskit").compareTo("1")==0)
                    sKit        = "Si";
                
                /*Agrega el registro en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("prod"), rs.getString("cant"), rs.getString("unid"), rs.getString("alma"), rs.getString("list"), rs.getString("descrip"), sPre, rs.getString("desc1"), rs.getString("impueval"), sImpo, sKit, sImpueImpo, rs.getString("tall"), rs.getString("colo"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("fcadu"), "", rs.getString("fentre"), sImpoDesc, rs.getString("serprod"), rs.getString("comenser"), rs.getString("garan"), sCost, "", rs.getString("codimpue")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas*/
                ++iContFi;
            }                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                    
        }
        
    }/*Fin de private void vCargCot(con)*/
    
    
    /*Función para que coloca las condiciones del cliente en un control*/
    private void vCond(Connection con, javax.swing.JTextField jTCon, String sLimit, String sDiCred)
    {
        /*Si los días de crédito es cadena vacia entonces que sea 0*/
        if(sDiCred.compareTo("")==0)
            sDiCred = "0";
        
        /*Intenta convertir en número los días de crédito*/
        try
        {
            Integer.parseInt(sDiCred);
        }
        catch(NumberFormatException expnNumForm)
        {
            sDiCred = "0";
        }                        

        /*Si el límite de crédito es cadena vacia entonces que sea 0*/
        if(sLimit.compareTo("")==0)
            sLimit  = "0";                               

        /*Intenta convertir el límite de crédito*/
        try
        {
            Double.parseDouble(sLimit);
        }
        catch(NumberFormatException expnNumForm)
        {
            sLimit = "0";
        }
        //correcion carlos
        if(Integer.parseInt(sDiCred)==0)    
        {
            jCConta.setEnabled(false);
            jCConta.setSelected(true);
        } 
        else
            jCConta.setEnabled(true);
        /*Si los días de crédito y el límite de crédito son mayores a 0 entonces el cliente tiene crédito*/
        if(Integer.parseInt(sDiCred)>0 && Double.parseDouble(sLimit)>0)
            bSiCred = true;
        
        /*Dale formato de moneda al límite de crédito*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        double dCant    = Double.parseDouble(sLimit);                
        sLimit          = n.format(dCant);

        /*Coloca las condiciones del crédito*/
        jTCon.setText      ("Días: " + sDiCred + " Límite: " + sLimit);                                
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene el saldo que tiene pendiente de pagar el cliente*/
        String sPendPag = "0";
        try
        {
            sQ = "SELECT IFNULL((SUM(carg) - SUM(abon)),0) AS pendpag FROM cxc WHERE empre = '" + jTCli.getText().trim() + "' AND concep <> 'NOT'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())                            
                sPendPag      = rs.getString("pendpag");                                             
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }
        
        /*Obtiene el saldo que tiene disponible la cliente*/
        String sSaldDispo   = Double.toString(Double.parseDouble(sLimit.replace("$", "").replace(",", "")) - Double.parseDouble(sPendPag));        

        /*Coloca en el campo el saldo disponible*/
        jTSaldDispo.setText (sSaldDispo);
                
        /*Coloca en el campo el límite de crédito*/
        jTLimCred.setText   (sLimit.replace("$", "").replace(",", ""));
        
        /*Que sea visible el label de tip ode venta*/
        jLTipVta.setVisible(true);
        
        //Comprueba si el cliente no esta bloqueado
        int iRes    = Star.iGetBloqCredCliProv(null, "cli", jTCli.getText());

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        /*Si el cliente tiene crédito disponible y no lo tiene bloqueado entonces*/
        if(Double.parseDouble(sDiCred)>0 && iRes==0)
        {
            /*El label será a crédito*/
            jLTipVta.setText("VENTA A CRÉDITO");        
            
            /*Desmarca el check de contado*/
            jCConta.setSelected(false);
        }
        /*Else es contado entonces*/
        else
        {
            /*El label será de contado*/
            jLTipVta.setText("VENTA DE CONTADO");        
            
            /*Marca el check de contado*/
            jCConta.setSelected(true);
        }
        
        /*Guarda el texto globalmente*/
        sLabTex             = jLTipVta.getText().trim();
        
        /*Dale formato de moneda al saldo disponible*/        
        dCant               = Double.parseDouble(sSaldDispo);                
        sSaldDispo          = n.format(dCant);

        /*Agrega en el campo el saldo disponible*/
        String sTemp    = jTCond.getText();
        jTCond.setText(sTemp + " Saldo Disponible:" + sSaldDispo);

    }/*Fin de private void vCond(javax.swing.JTextField jTCond, String sLimCred, String sDiaCred)*/
                
                
                
    /*Cuando se presiona el botón de generar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
                                                                              
        /*Si el código del cliente esta vacio entonces*/
        if(jTNom.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un cliente.", "Clientes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
            /*Coloca el foco del teclado en el control y regresa*/
            jTCli.grabFocus();                        
            return;            
        }

        /*Si no a seleccionado una moneda entonces*/
        if(jComMon.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una moneda.", "Nueva venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
            /*Coloca el foco del teclado en el control y regresa*/
            jComMon.grabFocus();                        
            return;            
        }
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Obtiene cuál es la moneda nacional*/
        String sCodMN   = Star.sGetMonNac(con);
        
        //Si hubo error entonces regresa
        if(sCodMN==null)
            return;

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene si la configuración global para que no se pueda vender a ningùn cliente con moneda distinta a la nacional esta activada o no*/
        boolean bSi     = true;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'otramon'";
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
        
        /*Comprueba si el cliente tiene o no habilitado que se le pueda vender con otra moneda distinta a la nacional*/        
        try
        {
            sQ = "SELECT otramon FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTCli.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si no tiene habilitada la vta con otra mon o globalmente entonces*/
                if(rs.getString("otramon").compareTo("0")==0 || !bSi)              
                {
                    /*Compara el código de moneda nacional con el que se quiere agregar*/
                    if(sCodMN.compareTo(jComMon.getSelectedItem().toString())!=0)
                    {
                        //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return;
                        if(bSi==false)
                        {/*Coloca el borde rojo*/                               
                        jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "Por configuración del sistema, solo se permite vender en Moneda Nacional: " + sCodMN + ".", "Nueva venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
                        /*Coloca el foco del teclado en el control y regresa*/
                        jComMon.grabFocus();
                        return;
                        }
                        else
                        {
                            /*Coloca el borde rojo*/                               
                        jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "La configuración del cliente no permite vender en una moneda distinta a la Moneda Nacional: " + sCodMN + ".", "Nueva venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
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
        
        //Declara variables locales
        String sMinFac  = "";
        boolean bSiMin  = false;
        
        /*Comprueba si esta habilitado usar el mínimo a facturar o no*/                
        try
        {
            sQ = "SELECT nume, val FROM confgral WHERE conf = 'minfac' AND clasif = 'vtas'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {                
                /*Obtiene el mínimo a facturar*/
                sMinFac = rs.getString("nume");
                
                /*Si tiene habilitado usar el mínimo para facturar entonces coloca la bandera*/                                
                if(rs.getString("val").compareTo("1")==0)
                    bSiMin  = true;
            }                                                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }            
        
        /*Si tiene habilitado usar el mínimo para facturar entonces*/
        if(bSiMin)
        {            
            /*Si la cantidad a facturar es menor que el mínimo a facturar entonces*/
            if(Double.parseDouble(jTTot.getText().trim().replace("$", "").replace(",", ""))<Double.parseDouble(sMinFac))
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Dale formato de moneda a la cantidad mínima a facturar*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                double dCant    = Double.parseDouble(sMinFac);                
                sMinFac           = n.format(dCant);

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La cantidad a facturar: " + jTTot.getText().trim() + " es menor a la cantidad mínima a facturar: " + sMinFac, "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                
                return;
            }
            
        }/*Fin de if(bSiMin)*/
        
        /*Comprueba si el cliente existe*/        
        try
        {
            sQ = "SELECT codemp, bloq FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTCli.getText().trim() + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces no existe*/
            if(!rs.next())
            {                
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El cliente: " + jTCli.getText().trim() + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTCli.grabFocus();
                return;                                 
            }
            /*Else, si existe entonces*/
            else
            {                                
                /*Si esta bloqueado el cliente entonces*/
                if(rs.getString("bloq").compareTo("1")==0)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Coloca el borde rojo*/                               
                    jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El cliente: " + jTCli.getText().trim() + " esta bloqueado.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Pon el foco del teclado en el campo de la cliente y regresa*/
                    jTCli.grabFocus();                                        
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
        
        /*Recorre la tabla de partidas de de la venta para checar que todos los kits tengan componentes*/        
        for(int x = 0; x < jTab.getRowCount(); x++)
        {            
            /*Si no es una partida de nota de crédito entonces continua*/
            if(jTab.getValueAt(x, 2).toString().compareTo("0")==0)
                continue;
            
            /*Comprueba si ese producto es un kit*/           
            boolean bSiK    = false;
            try
            {
                sQ = "SELECT compue FROM prods WHERE prod = '" + jTab.getValueAt(x, 1).toString().trim() + "'";	                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())
                {
                    /*Coloca la bandera correcta*/
                    if(rs.getString("compue").compareTo("1")==0)
                        bSiK    = true;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                       
            }            
            
            /*Si es un kit entonces*/
            if(bSiK)
            {
                //Obtiene si el producto tiene componentes
                double dRes     = Star.dGetCompsProd(con, jTab.getValueAt(x, 1).toString().trim());

                //Si hubo error entonces regresa
                if(dRes==-1)
                    return;

                //Si no tiene componentes entonces                 
                if(dRes<=0)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Coloca el foco del teclado en la tabla de partidas*/
                    jTab.grabFocus();

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "El kit: " + jTab.getValueAt(x, 1) + " no tiene componentes.", "Kit", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                    return;
                }
               
            }/*Fin de if(bSiK)*/                            
            
        }/*Fin de for(int x = 0; x < jTab.getRowCount(); x++)*/
                
        /*Si va a ser factura entonces haz algunas validaciones*/
        if(!jCRem.isSelected())
        {        
            /*Valida los datos necesarios para la factura*/
            if(Star.iValFac(con)==-1)
                return;
            
            /*Si no tiene calle el cliente entonces*/
            if(jTCall.getText().trim().compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTCall.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No tiene calle el cliente.", "Nueva factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Colcoa el foco del teclado en el control y regresa*/
                jTCall.grabFocus();
                return;
            }

            /*Si no tiene colonia el cliente entonces*/
            if(jTCol.getText().trim().compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No tiene colonia el cliente.", "Nueva factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Colcoa el foco del teclado en el control y regresa*/
                jTCol.grabFocus();
                return;
            }                

            /*Si no tiene CP el cliente entonces*/
            if(jTCP.getText().trim().compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTCP.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No tiene CP el cliente.", "Nueva factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Colcoa el foco del teclado en el control y regresa*/
                jTCP.grabFocus();
                return;
            }                

            /*Si no tiene número de exterior el cliente entonces*/
            if(jTNoExt.getText().trim().compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTNoExt.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No tiene número de exterior el cliente.", "Nueva factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Colcoa el foco del teclado en el control y regresa*/
                jTNoExt.grabFocus();
                return;
            }

            /*Si no tiene RFC el cliente entonces*/
            if(jTRFC.getText().trim().compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No tiene RFC el cliente.", "Nueva factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Colcoa el foco del teclado en el control y regresa*/
                jTRFC.grabFocus();
                return;
            }

            /*Si no tiene ciudad el cliente entonces*/
            if(jTCiu.getText().trim().compareTo("")==0)
            {
                /*Coloca el borde rojo*/                               
                jTCiu.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No tiene ciudad el cliente.", "Nueva factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Coloca el foco del teclado en el control y regresa*/
                jTCiu.grabFocus();
                return;
            }

            /*Si no tiene estado la cliente entonces*/        
            if(jTEstad.getText().trim().compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca el borde rojo*/                               
                jTEstad.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No tiene estado el cliente.", "Nueva factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Colcoa el foco del teclado en el control y regresa*/
                jTEstad.grabFocus();
                return;
            }
                       
        }/*Fin de if(!jCRem.isSelected())*/                    
        
        /*Si aún no hay partidas entonces*/
        if(jTab.getRowCount()==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Coloca el foco del teclado en el campo del código del producto*/
            jTProd.grabFocus();
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No existen partidas en la venta.", "Guardar venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                         
            return;
        }
            
        /*Si la serie de la venta es cadena vacia entonces*/
        if(jComSer.getSelectedItem().toString().compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
    
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una serie para la venta.", "Guardar venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                         
            
            /*Coloca el foco del teclado en el control y regresa*/
            jComSer.grabFocus();
            return;
        }
        
        //Si contado no esta seleccionado entonces
        if(!jCConta.isSelected())
        {
            /*Si el total es mayor al saldo disponible entonces*/
            if(Double.parseDouble(jTTot.getText().replace("$", "").replace(",", "")) > Double.parseDouble(jTSaldDispo.getText().replace("$", "").replace(",", "")))
            {            
                /*Comprueba la configuración para vender sobre límite de crédito en las facturas*/
                bSi = false;
                try
                {
                    sQ = "SELECT val FROM confgral WHERE conf = 'slimtcredfac' AND clasif = 'vtas'";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    if(rs.next())
                    {
                        /*Si no esta habilitado para que se pueda vender sobre límite de crédito de la cliente entonces coloca la bandera*/
                        if(rs.getString("val").compareTo("1")==0)                                   
                            bSi = true;                        
                    }            
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                    return;                                            
                }

                /*Si no esta permitido vender sobre el límite de crédito y si el cliente tiene crédito entonces*/
                if(!bSi && Double.parseDouble(jTDiaCre.getText())>0)
                {
                    /*Obtiene el saldo disponible*/
                    String sSald    = jTSaldDispo.getText();

                    /*Dale formato de mon al saldo disponible*/                            
                    NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                    double dCant    = Double.parseDouble(sSald);                
                    sSald           = n.format(dCant);
                       
                    if(ClavMast.bSi==false)
                    {
                        /*Mensajea*/
                         JOptionPane.showMessageDialog(null, "El total de la venta: " + jTTot.getText() + " es mayor que el saldo: " + sSald + " del cliente. y la clave de admnistrador que habia dado para completar la venta fue incorrecta favor de volverla a ingresar.", "Venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
                        /*Pide clave de administrador*/            
                         Star.gClavMast= new ClavMast(this, 1);
                         Star.gClavMast.setVisible(true);
                        //Cierra la base de datos y regresa
                        Star.iCierrBas(con);
                        return;
                    }


                }/*Fin de if(!bSi)*/
                
            }/*Fin de if(Double.parseDouble(jTTot.getText().replace("$", "").replace(",", "")) > Double.parseDouble(jTSaldDispo.getText().replace("$", "").replace(",", "")))*/                                
            
        }//Fin de if(!jCConta.isSelected())        
                    
        
        /*Si ninguno de los checkbox de correos electrónicos esta seleccionado entonces mensajea*/
        if((!jCCo1.isSelected() && !jCCo2.isSelected() && !jCCo3.isSelected()) || !jCMand.isSelected())
            JOptionPane.showMessageDialog(null, "No se enviará correo electrónico con la venta ya que no has habilitado las opciones.", "Enviar venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
        /*Comprueba si se tiene que pedir clave para poder hacer la venta venta*/
        try
        {
            sQ = "SELECT val, extr FROM confgral WHERE clasif = 'vtas' AND conf = 'clavsegfac'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si tiene que pedir clave entonces*/
                if(rs.getString("val").compareTo("1")==0)                                   
                {
                    /*Manda llamar la forma para pedir la clave de facturacion*/
                    ClavFac f = new ClavFac(this, Star.sDecryp(rs.getString("extr")));
                    f.setVisible(true);
                    
                    /*Si la clave esta mal o no sigio entonces regresa*/
                    if(!ClavFac.bSi)
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
        
        /*Recorre la tabla de partidas de de la venta para búscar si hay notas de crédito*/        
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Si no es una partida de nota de crédito entonces continua*/
            if(jTab.getValueAt(x, 2).toString().compareTo("0")!=0)
                continue;
            
            /*Si esa nota de crédito no es de ese cliente entonces*/           
            try
            {
                sQ = "SELECT codemp FROM vtas WHERE CONCAT_WS('', noser, norefer) = '" + jTab.getValueAt(x, 1).toString().trim() + "' AND tipdoc = 'NOT'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si n hay datos entonces no es esa nota de crédito de ese cliente*/
                if(!rs.next())
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Coloca el foco del teclado en la tabla de partidas*/
                    jTab.grabFocus();
                    
                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "La nota de crédito: " + jTab.getValueAt(x, 1) + " no es del cliente: " + jTCli.getText(), "Nota de Crédito", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                    return;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                       
            }            
        }

        /*Si el total es menor o igual a 0 entonces*/
        if(Double.parseDouble(jTTot.getText().replace("$", "").replace(",", ""))<=0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El total a pagar no es correcto.", "Generar nueva venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
                
        /*Si el control del saldo del cliente esta visible entonces*/
        if(jTTotSald.isVisible())
        {
            /*Si el saldo a pagar es menor a 0 entonces*/
            if(Double.parseDouble(jTTotSald.getText().replace("$", "").replace(",", ""))<0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El saldo total a pagar no es correcto.", "Generar nueva venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
        }/*Fin de if(jTTotSald.isVisible())*/                    
                
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar venta", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;                       
        }

        /*Recorre la tabla de partidas para saber si todos los productos de lote y caducidad no se han vendido en otro equipo*/        
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Si la partida es de pedimento y lote entonces*/
            if(jTab.getValueAt(x, 15).toString().compareTo("")!=0 || jTab.getValueAt(x, 16).toString().compareTo("")!=0)
            {
                /*Comprueba si aun estan disponibles las cantidades de lote y pedimento de la fila*/
                try
                {
                    sQ = "SELECT exist FROM lotped WHERE id_id = " + jTab.getValueAt(x, 18).toString();	
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces*/
                    if(rs.next())
                    {                        
                        /*Si la cantidad que se quiere vender es mayor que la disponible entonces*/
                        if(Double.parseDouble(jTab.getValueAt(x, 2).toString()) > Double.parseDouble(rs.getString("exist")))                                   
                        {
                            //Cierra la base de datos
                            if(Star.iCierrBas(con)==-1)
                                return;

                            /*Mensajea y regresa*/
                            JOptionPane.showMessageDialog(null, "Ya se vendierón algunos productos que son de lote y pedimento. Revisa nuevamente la venta.", "Lotes y Pedimentos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
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
                
            }/*Fin de if(jTab.getValueAt(x, 16).toString().compareTo("")!=0 || jTab.getValueAt(x, 17).toString().compareTo("")!=0 || jTab.getValueAt(x, 18).toString().compareTo("")!=0)*/                                
            
        }/*Fin de for(int x = 0; x < jTab.getRowCount(); x++)*/
        
        /*Obtiene la fecha de entrega*/    
        String sFEnt;
        try
        {
            Date fe                 =  jDFEnt.getDate();
            SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");
            sFEnt                   = sdf.format(fe);      
        }
        catch(NullPointerException expnNullPoint)
        {                        
            /*Pon el foco del teclado en el control*/
            jDFEnt.grabFocus();
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnNullPoint.getMessage(), Star.sErrNullPoint, expnNullPoint.getStackTrace(), con);
            return;                        
        }                                

        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;        

        /*Si el checkbox para modificar el cliente esta activado y no es cliente mostrador entonces*/
        if(jCGDats.isSelected() && jTCli.getText().compareTo(Star.sCliMostG)!=0)
        {                        
            /*Actualiza en la base de datos los datos de la cliente*/
            try 
            {                
                sQ = "UPDATE emps SET "
                        + "calle        = '" + jTCall.getText().replace("'", "''") + "', "
                        + "col          = '" + jTCol.getText().replace("'", "''") + "', "
                        + "tel          = '" + jTTel.getText().replace("'", "''") + "', "
                        + "pai          = '" + jTPai.getText().replace("'", "''") + "', "
                        + "cp           = '" + jTCP.getText().replace("'", "''") + "', "
                        + "noint        = '" + jTNoInt.getText().replace("'", "''") + "', "
                        + "noext        = '" + jTNoExt.getText().replace("'", "''") + "', "
                        + "rfc          = '" + jTRFC.getText().replace("'", "''") + "', "
                        + "ciu          = '" + jTCiu.getText().replace("'", "''") + "', "
                        + "estad        = '" + jTEstad.getText().replace("'", "''") + "', "
                        + "co1          = '" + jTCo1.getText().replace("'", "''") + "', "
                        + "co2          = '" + jTCo2.getText().replace("'", "''") + "', "
                        + "co3          = '" + jTCo3.getText().replace("'", "''") + "', "
                        + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE codemp = '" + jTCli.getText().replace("'", "''").replace(jTSer.getText().replace("'", "''"), "") + "' AND ser = '" + jTSer.getText().replace("'", "''") + "'";                                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                       
             }
            
        }/*Fin de if(jCGuarDatsClient.isSelected() && jTEmp.getText().compareTo("SYS")!=0)*/               
        
        /*Determina el tipo de documento que será*/
        String sTip = "FAC";
        if(jCRem.isSelected())
            sTip    = "REM";
        
        /*Obtiene el consecutivo de la serie*/       
        String sConFac  = "";        
        try
        {
            sQ = "SELECT ser, consec FROM consecs WHERE tip = '" + sTip + "' AND ser = '" + jComSer.getSelectedItem().toString().trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())            
                sConFac         = rs.getString("consec");                                                    
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }         
        
        /*Actualiza el consecutivo de la serie de la venta*/
        try 
        {            
            sQ = "UPDATE consecs SET "
                    + "consec       = consec + 1, "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE tip    = '" + sTip + "' AND ser = '" + jComSer.getSelectedItem().toString().replace("'", "''").trim() + "'";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }        
        
        /*Obtiene el método de pago*/
        String sMetPag      = jTMetPag.getText();
        
        /*Si el método de pago es cadena vacia entonces el método de pago será no identificable*/
        if(sMetPag.compareTo("")==0)
            sMetPag         = "No Identificado";
        
        /*Obtiene la cuenta de pago*/
        String sCta         = jTCta.getText();
        
        /*Si la cuenta de pago es cadena vacia entonces que la cunta sea 0000*/
        if(sCta.compareTo("")==0)
            sCta            = "0000";
        
        /*Si se tiene que guardar la forma de pago entonces*/
        if(jCGuaPag.isSelected())
        {
            /*Actualiza la forma de pago del cliente*/
            try 
            {         
                sQ = "UPDATE emps SET "
                        + "metpag       = '" + sMetPag.replace("'", "''") + "', "
                        + "cta          = '" + sCta.replace("'", "''") + "', "
                        + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE codemp = '" + jTCli.getText().replace("'", "''").replace(jTSer.getText().replace("'", "''"), "") + "' AND ser = '" + jTSer.getText().replace("'", "''") + "'";                    
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
        
        /*Determina las condiciones de pago*/
        String      sConds;
        if(bSiCred) 
            sConds  = "Crédito";
        else
            sConds  = "Contado";
        
        /*Si el usuario específicamente quiere que sea de contado entonces*/
        if(jCConta.isSelected())
            sConds  = "Contado";
            
        /*Si el cliente tiene crédito entonces*/
        String sFVenc = "";
        if(bSiCred)
        {
            /*Obtiene la fecha de vencimiento del cliente*/               
            try
            {
                sQ = "SELECT CASE WHEN CONVERT(IFNULL(diacred,0), SIGNED INTEGER) = 0 THEN 0 ELSE  IFNULL(diacred,0) END AS dias, CASE WHEN CONVERT(IFNULL(diacred,0), SIGNED INTEGER ) = 0 THEN now() ELSE now() + INTERVAL IFNULL(diacred,0) DAY END AS vencimien FROM emps WHERE codemp = '" + jTCli.getText().replace(jTSer.getText(), "")+ "' AND ser = '" + jTSer.getText() + "'";                                    
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())                
                    sFVenc  = rs.getString("vencimien");                                                                
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                        
            }              
        }
        
        /*Obtiene la fecha del documento*/    
        String sFDoc;
        try
        {
            Date fe                 =  jDFech.getDate();
            SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd|hh:mm:ss");
            sFDoc                   = sdf.format(fe);      
        }
        catch(NullPointerException expnNullPoint)
        {                       
            /*Pon el foco del teclado en el control*/
            jDFech.grabFocus();
            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnNullPoint.getMessage(), Star.sErrNullPoint, expnNullPoint.getStackTrace(), con);
            return;                        
        }                                
        
        /*Cambiale a la fecha la tubería por la T*/
        sFDoc   = sFDoc.replace("|", "T");
        
        /*Inicialmente no será de crédito*/
        String  sCred                   = "now()";
                
        /*Si el cliente es de contado y tiene saldo a favor entonces*/
        if(jTTotSald.isVisible())
        {
            //Inserta cxc el abono en la base de datos            
            if(Star.iInsCXCP(con, "cxc", sConFac, jComSer.getSelectedItem().toString(), jTCli.getText(), jTSer.getText(), jTSubTot.getText(), jTImp.getText(), jTTot.getText(), jTSaldDispo.getText(), "0" , "now()", "'" +sFDoc+ "'", "FAC", "", "0", "", "","")==-1)
                 return;                                       
        }
        
        /*Si tiene días de crédito y no va a ser de contado entonces*/        
        if(bSiCred && !jCConta.isSelected())
        {
            /*Coloca la variable que sea igual a la fecha de vencimiento*/
            sCred   = "'" + sFVenc + "'";
            
            //Inserta cxc en la base de datos            
            if(Star.iInsCXCP(con, "cxc", sConFac, jComSer.getSelectedItem().toString(), jTCli.getText(), jTSer.getText(), jTSubTot.getText(), jTImp.getText(), jTTot.getText(), jTTot.getText(), "0", "'" + sFVenc + "'","'" + sFDoc+ "'", "FAC", "", "", "", "","")==-1)
                return;                                                   
        }
        //Else va a ser de contado entonces
        else
        {
            //Inserta el CXC de la deuda           
            if(Star.iInsCXCP(con, "cxc", sConFac, jComSer.getSelectedItem().toString(), jTCli.getText(), jTSer.getText(), jTSubTot.getText(), jTImp.getText(), jTTot.getText(), jTTot.getText(), "0", "now()","'" + sFDoc+ "'", "FAC", "", "0", "", "","")==-1)
                return;               
            
            //Si va a estar pagada entonces
            if(jRPagad.isSelected())
            {
                //Inserta el CXC del abono   
                if(Star.iInsCXCP(con, "cxc", sConFac, jComSer.getSelectedItem().toString(), jTCli.getText(), jTSer.getText(), jTSubTot.getText(), jTImp.getText(), jTTot.getText(), "0", jTTot.getText(), "now()", "now()", "ABON FAC", jComFormPag.getSelectedItem().toString().trim(), "0", "", "","")==-1)
                return;                                       
            }            
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
                
        /*Determina si será de contado o crédito para la forma de pago*/
        String sFPag;
        if(jLTipVta.getText().trim().compareTo("VENTA DE CONTADO")==0)        
            sFPag  = "C";        
        else
            sFPag  = "CR";
        String Bo="";
        if(sBackOrder==1)
                Bo="BO";
        //Inserta en la base de datos la nueva venta
        if(Star.iInsVtas(con, jComSer.getSelectedItem().toString().replace("'", "''").trim(), sConFac.replace("'", "''").trim(), jTCli.getText().replace("'", "''").trim(),  "",jTSubTot.getText().replace("$", "").replace(",", ""), jTImp.getText().replace("$", "").replace(",", ""), jTTot.getText().replace("$", "").replace(",", ""), "now()", "'" + sFDoc + "'", sCred, "'CO'", "0", "", sTip, "0", sMetPag.replace("'", "''"), sCta.replace("'", "''"), jTAObserv.getText().replace("'", "''"), "0", jTTotDesc.getText().replace("$", "").replace(",", ""), "0", "1", jTTotCost.getText(), Login.sUsrG, jComMon.getSelectedItem().toString().replace("'", "''"), sTipCam, sFPag, "", "", "", "", "", "", "", "", "", "", "", "", "N", sCodCot, "0", "0", "0", "0",Bo)==-1)
            return;
            
        //Declara variables locales
        String sNomEstac    = "";
        String sFAl         = "";
        String sVta         = "";
        String sEsta        = "";
        
        /*Obtiene algunos datos de la venta*/        
        try
        {                  
            sQ = "SELECT nom, vtas.FALT, vta, estad FROM vtas LEFT OUTER JOIN estacs ON vtas.ESTAC = estacs.ESTAC ORDER BY vta DESC LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
            {            
                sFAl            = rs.getString("vtas.FALT");                                    
                sVta            = rs.getString("vta");                                    
                sEsta           = rs.getString("estad");                                 
                sNomEstac       = rs.getString("nom");                                 
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }
                
        /*Obtiene algunos datos del cliente*/        
        String sCtaPred         = "";
        try
        {                  
            sQ = "SELECT ctapred FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTCli.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCtaPred    = rs.getString("ctapred");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }
        
        /*Si viene de una cotización entonces*/
        if(sCodCot.compareTo("")!=0)
        {
            /*Actualiza la cotización*/            
            try 
            {            
                sQ = "UPDATE cots SET "
                        + "estad        = 'CO', "
                        + "vta          = " + sVta + ", "
                        + "norefer      = '" + sConFac.replace("'", "''") + "', "
                        + "noservta     = '" + jComSer.getSelectedItem().toString().replace("'", "''").trim() + "' "
                        + "WHERE codcot = '" + sCodCot + "'";                    
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
                
        /*Contiene el pago de las notas de crédito*/
        String sPagNot  = "";
        
        /*Recorre la tabla de partidas de venta*/        
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Si la partida es una nota de crédito entonces*/
            if(jTab.getValueAt(x, 2).toString().compareTo("0")==0)
            {
                /*Ve creando la cadena de la órden de compra*/
                sPagNot += jTab.getValueAt(x, 1) + "|";
                
                /*Agrega en la venta la nota de crédito con la que se esta pagando*/
                try 
                {                
                    sQ = "UPDATE vtas SET "
                            + "notcredpag       = CONCAT_WS('', notcredpag, '" + jTab.getValueAt(x, 1).toString().trim() + "|') "
                            + "WHERE vta        = " + sVta;                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                    return;                                            
                }    
                
                /*Actualiza el estado de nota de crédito de la nota como que ya esta usada y la venta a la que se asigno*/
                try 
                {                
                    sQ = "UPDATE vtas SET "
                            + "estad                                = 'CO',  "
                            + "notcred                              = '" + jComSer.getSelectedItem().toString().replace("'", "''").trim() + sConFac.replace("'", "''").trim()  + "' " 
                            + "WHERE CONCAT_WS('', noser, norefer)  = '" + jTab.getValueAt(x, 1).toString().trim() + "' AND tipdoc = 'NOT'";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                    return;                                            
                 }    
                
                /*Continua*/
                continue;
                
            }/*Fin de if(jTab.getValueAt(x, 2).toString().compareTo("0")==0)*/
            
            /*Si tene talla o color entonces procesa las tallas y colores*/
            if(jTab.getValueAt(x, 13).toString().compareTo("")!=0 || jTab.getValueAt(x, 14).toString().compareTo("")!=0)                           
                Star.vTallCol(con, jTab.getValueAt(x, 2).toString(), jTab.getValueAt(x, 4).toString(), jTab.getValueAt(x, 13).toString(), jTab.getValueAt(x, 14).toString(), jTab.getValueAt(x, 1).toString(), "-");            
            
            /*Comprueba si el producto es un servicio*/       
            boolean bServ   = false;
            try
            {
                sQ = "SELECT servi FROM prods WHERE prod = '" + jTab.getValueAt(x, 1).toString().trim() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())          
                {
                    /*Si es un servicio entonces coloa la bandera*/
                    if(rs.getString("servi").compareTo("1")==0)                                                    
                        bServ   = true;
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                                    
            }         

            /*Obtiene el descuento*/
            String sDesc    = jTab.getValueAt(x, 8).toString();
            
            /*Si el descuento es cadena vacia entonces colocar 0*/
            if(sDesc.compareTo("")==0)
                sDesc       = "0";
            
            /*Obtiene el valor del impuesto*/
            String sImpue   = jTab.getValueAt(x, 9).toString();
                        
            /*Obtiene el impuesto*/
            String sImp2    = jTab.getValueAt(x, 10).toString().replace("$", "").replace(",", "");
                                    
            /*Obtiene el costo dependiendo el método de costeo de la empresa*/
            String sCostMe  = Star.sGetCost(con, jTab.getValueAt(x, 1).toString().trim(), jTab.getValueAt(x, 2).toString().trim());
                        
            /*Si hubo error entonces regresa*/
            if(sCostMe==null)
                return;
            
            /*Obtiene el tipo de cambio de la moneda*/            
            try
            {
                sQ = "SELECT val FROM mons WHERE mon = '" + jTab.getValueAt(x, 10).toString().trim() + "'";
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
                        
            /*Los entregados son igual a la cantidad de la fila*/
            String sEntre       = jTab.getValueAt(x, 2).toString().trim();
                        
            //Obtiene el campo del backorder
            String sBack        = "'" + jTab.getValueAt(x, 19).toString().trim() + "'";  
            
            //Si el campo del back order tiene datos entonces
            if(sBack.compareTo("''")==0)
                sBack           = "now()";
            
            /*Si tiene backorder entonces la cantidad de entregados son 0*/                      
            if(jTab.getValueAt(x, 19).toString().compareTo("")!=0)            
                sEntre                  = "0";            
            
            /*Obtiene si tiene fecha de caducidad correctamente*/
            String sFCadu               = "now()";
            if(jTab.getValueAt(x, 17).toString().compareTo("")!=0)                           
                sFCadu                  = "'" + jTab.getValueAt(x, 17).toString() + "'";            
            
            /*Si tiene serie entonces procesa esta parte en la función*/
            if(jTab.getValueAt(x, 21).toString().compareTo("")!=0)
                //cAMBIO ALAN
                Star.vSerPro(con, jTab.getValueAt(x, 1).toString(), Star.sCantUnid(jComUnid.getSelectedItem().toString().trim(), jTab.getValueAt(x, 2).toString()), jTab.getValueAt(x, 21).toString(), jTab.getValueAt(x, 4).toString(), jTab.getValueAt(x, 22).toString(), "-");            
                                    
            /*Si el producto es un kit entonces*/                 
            String sKit         = "0";
            if(jTab.getValueAt(x, 11).toString().compareTo("Si")==0)                                            
                sKit            = "1";                            
            
            //Si el impuesto es cadena vacia que sea 0
            if(sImpue.compareTo("")==0)
                sImpue          = "0";
            
            /*Inserta en la base de datos la partida de la venta*/
            try 
            {                
                sQ = "INSERT INTO partvta(    vta,                  prod,                                                          cant,                                          unid,                                                               descrip,                                                           pre,                                                                              descu,            impue,     mon,         impo,          falt,        eskit,          kitmae,     tipdoc,        list,                                        alma,                                                     peps,    ueps,   tall,                                                           colo,                                                           fentre,        cantentre,       serprod,                                     comenser,                                                  tipcam,     garan,                                                         cost,        costprom,  lot,                                                   pedimen,                                            fcadu,               codimpue) " + 
                                 "VALUES(" + sVta + ",'"  +        jTab.getValueAt(x, 1).toString().replace("'", "''") + "'," +    jTab.getValueAt(x, 2).toString() + ",'" +      jTab.getValueAt(x, 3).toString().replace("'", "''") + "','" +       jTab.getValueAt(x, 6).toString().replace("'", "''") + "'," +       jTab.getValueAt(x, 7).toString().replace("$", "").replace(",", "") + "," +        sDesc + "," +     sImpue + ",''," +       sImp2 + ",     now(), " +   sKit + ",       0,          'FAC', " +     jTab.getValueAt(x, 5).toString() + ", '" +   jTab.getValueAt(x, 4).toString().replace("'", "''") + "', 0,       0, '" + jTab.getValueAt(x, 13).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 14).toString().replace("'", "''") + "', " +  sBack + ", " + sEntre + ", '" + jTab.getValueAt(x, 21).toString() + "', '" + jTab.getValueAt(x, 22).toString().replace("'", "''") + "', 0, '" +     jTab.getValueAt(x, 23).toString().replace("'", "''") + "', " + sCostMe + ", 0, '" +    jTab.getValueAt(x, 15).toString().trim() + "', '" +    jTab.getValueAt(x, 16).toString().trim() + "', " +  sFCadu + ", '" +     jTab.getValueAt(x, 26).toString().trim()+"')";                                                                    
                st = con.createStatement();
                st.executeUpdate(sQ);                                                
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                       
            }
                        
            /*Si el producto es un kit entonces*/                             
            if(sKit.compareTo("1")==0)
            {
                /*Obtiene el último ID insertado para referenciar los kits*/
                String sId  = "";
                try
                {
                    sQ = "SELECT MAX(id_id) AS id FROM partvta WHERE vta = " + sVta + " ORDER BY id DESC LIMIT 1";	
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces obtiene el resultado*/
                    if(rs.next())
                        sId = rs.getString("id");
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                    return;                                            
                }
                
                /*Coloca que si es kit y agrega las partidas con precio 0*/
                sKit            = "1";
                if(Star.iInsFacComp(con, jTab.getValueAt(x, 1).toString().trim(), sVta, jTab.getValueAt(x, 4).toString().trim(), "FAC", jComSer.getSelectedItem().toString().replace("'", "''") + sConFac.replace("'", "''"), jTCli.getText().replace("'", "''"), "1", jComSer.getSelectedItem().toString().replace("'", "''"), sTipCam, sId, jTab.getValueAt(x, 2).toString().trim(), jTab.getValueAt(x, 3).toString().trim(), jTab.getValueAt(x, 26).toString().trim())==-1)
                    return;
            }                                   
                        
            /*La cantidad a manejar originalmente será la que ingreso el usuario*/
            String sCantO       = jTab.getValueAt(x, 2).toString().trim();

            /*Obtiene la cantidad correcta*/               
            sCantO              = Star.sCantUnid(jTab.getValueAt(x, 3).toString().trim(), sCantO);
            
            /*Si tiene lote o pedimento la fila entonces procesa esto en una función*/
            if(jTab.getValueAt(x, 15).toString().trim().compareTo("")!=0 || jTab.getValueAt(x, 16).toString().trim().compareTo("")!=0)            
            {                
                if(Star.sLotPed(con, jTab.getValueAt(x, 1).toString().trim(), sCantO, jTab.getValueAt(x, 4).toString().trim(), jTab.getValueAt(x, 15).toString().trim(), jTab.getValueAt(x, 16).toString().trim(), jTab.getValueAt(x, 17).toString().trim(), "-")==null)
                    return;
            }
            
            /*Si no es kit, si se entrega el día de hoy y si no es un servicio entonces*/
            if(sKit.compareTo("0")==0 && sBack.compareTo("now()")==0 && !bServ)
            {                                                                   
                /*Realiza la afectación correspondiente al almacén*/
                if(Star.iAfecExisProd(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim(), jTab.getValueAt(x, 4).toString().replace("'", "''").trim(), sCantO, "-")==-1)
                    return;
                
                /*Actualiza la existencia general del producto*/
                if(Star.iCalcGralExis(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim())==-1)
                    return;
                
                /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
                if(!Star.vRegMoniInv(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim(), sCantO, jTab.getValueAt(x, 6).toString().replace("'", "''"), jTab.getValueAt(x, 4).toString().replace("'", "''"), Login.sUsrG , jComSer.getSelectedItem().toString().replace("'", "''").trim() + sConFac.replace("'", "''"), sTip, jTab.getValueAt(x, 3).toString().replace("'", "''"), jComSer.getSelectedItem().toString().replace("'", "''").trim(), jTCli.getText().replace("'", "''"), "1"))                                
                    return;                                                                                                                                                                                                             
                                
            }/*Fin de if(sKit.compareTo("0")==0)*/                
                        
        }/*Fin de for(int x = 0; x < jTab.getRowCount(); x++)*/
        
        /*Contiene el total de costo*/
        String sTotCost    = "";
        
        /*Obtiene la sumatoria del total del costo*/
        try
        {
            sQ = "SELECT IFNULL(SUM(cost * cant),0) AS cost FROM partvta WHERE vta = " + sVta;	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sTotCost    = rs.getString("cost");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }
        
        /*Actualiza el encabezado de la venta con el UEPS, PEPS y costo promedio total*/
        try 
        {                
            sQ = "UPDATE vtas SET "
                    + "totcost      = " + sTotCost + " "                    
                    + "WHERE vta    = " + sVta;                                                    
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

        /*Comprueba si el sistema esta en modo prueba*/
        boolean bSiT    = true;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'modp'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca la bandera correcta*/
                if(rs.getString("val").compareTo("1")==0)                                   
                    bSiT  = false;                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                
        }
        
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
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si es una remisión entonces no se debe timbrar*/
        if(jCRem.isSelected())
            bSiT                = false;
        
        /*Dale formato de moneda al total*/
        String sTot             = jTTot.getText().replace("$", "").replace(",", "");        
        double dCant            = Double.parseDouble                (sTot);                
        NumberFormat n          = NumberFormat.getCurrencyInstance  (Locale.getDefault());
        sTot                    = n.format(dCant);

        /*Dale formato de moneda al costo total*/
        sTotCost                = jTTotCost.getText().replace("$", "").replace(",", "");        
        dCant                   = Double.parseDouble                (sTotCost);                        
        sTotCost                = n.format(dCant);
        
        /*Determina el texto que se va a poner en timbrado*/
        String sTim = "Timbrando...";
        if(!jCTim.isSelected() || !bSiT)
            sTim    = "No";            
            
        /*Agrega los datos en la tabla de ventas del otro formulario*/
        if(sCodCot.compareTo("")==0)
        {
            DefaultTableModel te    = (DefaultTableModel)jTabVe.getModel();
            Object nu[]             = {Star.iContFiVent, sVta, sConFac, jComSer.getSelectedItem().toString(), jTCli.getText().trim(), sTot, jTTotDesc.getText(), sTotCost, sFAl, sFDoc, sFVenc, sFAl, "", sPagNot.replace("|", " "), sEsta, Login.sUsrG, "", "FAC", jTAObserv.getText(), Star.sSucu, Star.sNoCaj, sNomEstac, sTim, sCodCot, "", "","", "", "", "", "", "", "", "", "", "", "", "", "", ""};
            te.addRow(nu);
        }            

        /*Guarda el contador de fila para saber donde colocar si se timbro o no*/
        int iFil                = Star.iContFiVent - 1;
        
        /*Aumenta en uno el contador de filas de las facturas*/
        ++Star.iContFiVent;
                
        /*Obtiene el campo extra a mostrar*/
        String sCatGral         = jTACatGral.getText();
        
        /*Si se tiene que agregar la fecha en el campo extra entonces*/        
        if(jCFExt.isSelected())
        {
            /*Obtiene la fecha extra seleccionada por el usuario*/                
            try
            {
                Date fe                 =  jDFExt.getDate();
                SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");                
                sCatGral                = sdf.format(fe) + " " + sCatGral;
            }
            catch(NullPointerException expnNullPoint)
            {    
                /*Pon el foco del teclado en el control*/
                jDFExt.grabFocus();
            
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnNullPoint.getMessage(), Star.sErrNullPoint, expnNullPoint.getStackTrace(), con);
                return;                        
            }                                                       
        }
        
        /*Si hay dirección de envio seleccioanda entonces*/
        String sDirEn           = "";
        if(iInd!=-1)
        {
            /*Genera el texto de la dirección de envio*/
            sDirEn           = "Calle: " + jTabDir.getValueAt(iInd,6) + " No.Ext: " + jTabDir.getValueAt(iInd,8) + " No.Int: " + jTabDir.getValueAt(iInd,9) + "Col: " + jTabDir.getValueAt(iInd,7) + System.getProperty( "line.separator" ) +
                                  "CP: " + jTabDir.getValueAt(iInd,10) + " Ciu: " + jTabDir.getValueAt(iInd,11) + " Estad: " + jTabDir.getValueAt(iInd,12) + " Tel: (" + jTabDir.getValueAt(iInd,1) + ")" + jTabDir.getValueAt(iInd,0) + " Exten:" + jTabDir.getValueAt(iInd,2) + " Cel: " + jTabDir.getValueAt(iInd,3);        
        }        
              
        /*Determina si será de contado o crédito para la forma de pago*/
        final String sFormPagFi;
        if(jLTipVta.getText().trim().compareTo("VENTA DE CONTADO")==0)        
            sFormPagFi  = "Pago en una sola exhibición";        
        else
            sFormPagFi  = "Pago en parcialidades";
        
        /*Determina si debe de imrpimir o no*/
        final boolean bImpr     = jCImp.isSelected();
        
        /*Determina si debe mostrar archivos o no*/
        final boolean bMostA    = jCMostA.isSelected();
        
        /*Declara variables final para el thead*/
        final String sConFacFi  = sConFac;    
        final String sTotDescuFi= jTTotDesc.getText().trim().replace("$", "").replace(",", "");    
        final String sVtaFi     = sVta;
        final String sMonFi     = jComMon.getSelectedItem().toString().trim();
        final String sCatGralFi = sCatGral;
        final String sFDocFi    = sFDoc;
        final String sNomEmpFi  = jTNom.getText();
        final String sTelFi     = jTTel.getText();
        final String sCallFi    = jTCall.getText();
        final String sDirEnFi   = sDirEn;
        final String sPaiFi     = jTPai.getText();
        final String sColFi     = jTCol.getText();
        final String sCPFi      = jTCP.getText(); 
        final String sNoExtFi   = jTNoExt.getText();
        final String sNoIntFi   = jTNoInt.getText();        
        final String sCiuFi     = jTCiu.getText();
        final String sEstaFi    = jTEstad.getText();
        final String sRFCFi     = jTRFC.getText();
        final String sCo1Fi     = jTCo1.getText();
        final String sTotLetFi  = Star.sObLet(jTTot.getText().replace("$", "").replace(",", ""), jComMon.getSelectedItem().toString().trim(), sSimb, true);
        final String sSubTotFi  = jTSubTot.getText();
        final String sImpFi     = jTImp.getText();
        final String sTotFi     = sTot;
        final String sSerFacFi  = jComSer.getSelectedItem().toString();
        final String sMetPagFi  = sMetPag;
        final String sCtaPredFi = sCtaPred;
        final String sCtaFi     = sCta;
        final String sTipCamFi  = sTipCam;
        final String sCondsFi   = sConds;        
        final int    iFilFi     = iFil;        
     
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Venta con folio: " + sConFac + " generada con éxito.", "Éxito al generar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));               
        
        /*Si se tiene que timbrar entonces comienza el thread*/
        if(jCTim.isSelected() && bSiT)
        {
            /*Thread para quitar carga y todo se haga mas rápido*/
            (new Thread()
            {
                @Override
                public void run()
                {                
                    /*Función para hacer el timbrado y generar PDF y XML*/
                    Star.vGenTim("fac", sDirEnFi, sConFacFi, sVtaFi, sCatGralFi, sFDocFi, sNomEmpFi, sPaiFi, sTelFi, sCallFi, sColFi, sCPFi, sNoExtFi, sNoIntFi, sCiuFi, sEstaFi, sRFCFi, sCo1Fi, sTotLetFi, sSubTotFi, sImpFi, sTotFi, sSerFacFi, sMetPagFi, sCtaFi, sCondsFi, getClass().getResource(Star.sIconDef).toString(), getClass().getResourceAsStream("/jasreport/rptFac.jrxml"), bMostA, bImpr, jTCo1.getText(), jTCo2.getText(), jTCo3.getText(), jCCo1.isSelected(), jCCo2.isSelected(), jCCo3.isSelected(), jCMand.isSelected(), iFilFi, jTabVe, false, sMonFi, sTotDescuFi, sFormPagFi, "ingreso", sTipCamFi, sCtaPredFi);                                            
                }

            }).start();

            //Muestra el loading
            Star.vMostLoading("");            
        }
        
        /*Si va a ser remisión entonces*/
        if(jCRem.isSelected())
        {   
            /*Thread para quitar carga y todo se haga mas rápido*/
            (new Thread()
            {
                @Override
                public void run()
                {                
                    /*Función para hacer el timbrado y generar PDF y XML*/
                    Star.vGenRem(sDirEnFi, sMonFi, sConFacFi, sVtaFi, sCatGralFi, sFDocFi, sNomEmpFi, sPaiFi, sTelFi, sCallFi, sColFi, sCPFi, sNoExtFi, sNoIntFi, sCiuFi, sEstaFi, sRFCFi, sCo1Fi, sTotLetFi, sSubTotFi, sImpFi, sTotFi, sSerFacFi, sMetPagFi, sCtaFi, sCondsFi, getClass().getResource(Star.sIconDef).toString(), getClass().getResourceAsStream("/jasreport/rptRem.jrxml"), bMostA, bImpr, jTCo1.getText(), jTCo2.getText(), jTCo3.getText(), jCCo1.isSelected(), jCCo2.isSelected(), jCCo3.isSelected(), jCMand.isSelected());                                            
                }
                
            }).start();
        }           
        /*La forma ya no apunta a nada*/
        Star.newVta = null;
            
        /*Sal del formulario*/
        this.dispose();
        Star.newVta = null;
        /*Llama al recolector de basura*/
        System.gc();
        
    }//GEN-LAST:event_jBGuarActionPerformed
                                                                                       
                                                                
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
        /*Pregunta al usuario si esta seguro de abandonar*/                
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {
            /*La forma ya no apunta a nada*/
            Star.newVta = null;
            
            /*Llama al recolector de basura y cierra la forma*/
            System.gc();        
            this.dispose();
        }
        
    }//GEN-LAST:event_jBSalActionPerformed
            
    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el botón de generar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
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

        
    /*Cuando se gana el foco del telcado en el campo de descripción de almacén*/
    private void jTDescripAlmaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlmaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripAlma.setSelectionStart(0);jTDescripAlma.setSelectionEnd(jTDescripAlma.getText().length());        
        
    }//GEN-LAST:event_jTDescripAlmaFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de descripción de artículo*/
    private void jTDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescrip.setSelectionStart(0);jTDescrip.setSelectionEnd(jTDescrip.getText().length());                
        
    }//GEN-LAST:event_jTDescripFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de descripción*/
    private void jTDescripUniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripUniFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripUni.setSelectionStart(0);jTDescripUni.setSelectionEnd(jTDescripUni.getText().length());                
        
    }//GEN-LAST:event_jTDescripUniFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de código de cant*/
    private void jTCantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCant.setSelectionStart(0);jTCant.setSelectionEnd(jTCant.getText().length());        
        
    }//GEN-LAST:event_jTCantFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de cost*/   
    private void jTPreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPreFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPre.setSelectionStart(0);jTPre.setSelectionEnd(jTPre.getText().length());        
        
        /*Guarda el valor original*/
        sCostOri = jTPre.getText();
                
    }//GEN-LAST:event_jTPreFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de desc*/
    private void jTDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDesc.setSelectionStart(0);jTDesc.setSelectionEnd(jTDesc.getText().length()); 
        
        /*Guarda el descuento original que tenía*/
        sDescOri = jTDesc.getText();
        
    }//GEN-LAST:event_jTDescFocusGained
    
    
    /*Cuando se gana el foco del teclado en el campo de valor impue*/
    private void jTValImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTValImpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTValImp.setSelectionStart(0);jTValImp.setSelectionEnd(jTValImp.getText().length());         
        
    }//GEN-LAST:event_jTValImpFocusGained

    
                
    /*Cuando se presiona una tecla en el campo de descripción de almacén*/
    private void jTDescripAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripAlmaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripAlmaKeyPressed

    
    /*Cuando se presiona una tecla en el campo de descripción de artículo*/
    private void jTDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripKeyPressed
    
    
    /*Cuando se presiona una tecla en el combobox de código de unidad*/
    private void jComUnidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComUnidKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComUnidKeyPressed

    
    /*Cuando se presiona una tecla en el campo de descripción de unidad*/
    private void jTDescripUniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripUniKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripUniKeyPressed

    
    /*Cuando se presiona una tecla en el combobox de código de impue*/
    private void jComImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComImpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComImpKeyPressed

    
    /*Cuando se presiona una tecla en el campo de valor de impue*/
    private void jTValImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTValImpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTValImpKeyPressed

    
    /*Cuando se presiona una tecla en el combobox de código de moneda*/
    private void jComMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComMonKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComMonKeyPressed

    
    
    /*Cuando se presiona una tecla en el botón de agregar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar partida*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed

    
    private void jPPartsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPPartsKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jPPartsKeyPressed
       
    
    /*Cuando se presiona una tecla en el campo de cost*/
    private void jTPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPreKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPreKeyPressed

    
    /*Cuando se presiona una tecla en el campo de desc*/
    private void jTDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescKeyPressed

    
    /*Cuando se presiona una tecla en el campo de cant*/
    private void jTCantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCantKeyPressed
    
    
    /*Cuando se pierde el foco del teclado en el campo de cantidad*/
    private void jTCantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCant.getText().compareTo("")!=0)
            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                              
    }//GEN-LAST:event_jTCantFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de costo*/
    private void jTPreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPreFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTPre.getText().compareTo("")!=0)
            jTPre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Lee el texto introducido por el usuario*/
        String sTex = jTPre.getText();
        
        /*Si es cadena vacia entonces*/
        if(sTex.compareTo("")==0)
        {
            /*Coloca 0 y regresa*/
            jTPre.setText("$0.00");
            return;
        }
        
        /*Si tiene formato de moneda quitaselo*/
        sTex = sTex.replace("$", "");      
        sTex = sTex.replace(",", "");
        
        /*Si es cadena vacia regresa y no continuar*/
        if(sTex.compareTo("")==0)
            return;
        
        /*Si los caracteres introducidos no se puede convertir a double colocar cadena vacia y regresar*/
        try  
        {  
            double d = Double.parseDouble(sTex);  
        }  
        catch(NumberFormatException expnNumForm)  
        {              
            jTPre.setText("");
            return;
        }                  
                  
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Si en el campo de costo hay un valor entonces*/
        String sCost = jTCost.getText();
        if(sCost.compareTo("")!=0)
        {            
            /*Obtiene si el producto se puede vender a bajo del costo o no*/        
            try
            {                  
                sQ = "SELECT bajcost FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())
                {                                        
                    /*Si el producto no se puede vender abajo del costo entonces*/
                    if(rs.getString("bajcost").compareTo("0")==0)                                    
                    {
                        /*Quitale el formato de moneda al costo*/
                        sCost   = sCost.replace("$", "").replace(",", "");

                        /*Si el precio es menor al costo entonces*/
                        if(Double.parseDouble(sTex)< Double.parseDouble(sCost))
                        {
                            //Cierra la base de datos
                            if(Star.iCierrBas(con)==-1)
                                return;

                            /*Mensajea*/
                            JOptionPane.showMessageDialog(null, "No se puede vender a bajo del costo este producto.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                            
                            /*Coloca el valor anterior que tenía y regresa*/
                            jTPre.setText(sCostOri);                                                        
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
            
        }/*Fin de if(sCost.compareTo("")!=0)*/        
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Conviertelo a double*/
        double dCant = Double.parseDouble(sTex);
        
        /*Formatealo*/
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTex = n.format(dCant);
        
        /*Colocalo de nu en el campo de texto*/
        jTPre.setText(sTex);                                               
        
    }//GEN-LAST:event_jTPreFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de desc*/
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
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene el desc posible para esta usuario*/
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
                
                /*Si el descuento esta deshabilitado para esta usuario entonces*/
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
            /*Coloca le desc original que tenía*/
            jTDesc.setText(sDescOri);
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El descuento máximo permitido para el usuario: " + Login.sUsrG + " es: " + sDesc + "%.", "Descuento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el campo del desc*/
            jTDesc.grabFocus();
        }
        
    }//GEN-LAST:event_jTDescFocusLost
    
    
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

                    
    /*Cuando pasa una acción en el combobox de unids*/
    private void jComUnidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComUnidActionPerformed
                        
        /*Si no hay datos entonces regresa*/
        if(jComUnid.getSelectedItem()==null)
            return;                
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene la descripción de la unidad en base a su código*/        
        try
        {                  
            sQ = "SELECT descrip FROM unids WHERE cod = '" + jComUnid.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado en el control*/
            if(rs.next())
                jTDescripUni.setText(rs.getString("descrip"));                                
            else
                jTDescripUni.setText("");                                                        
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

    
    /*Cuando sucede una acción en el combobox de impues*/
    private void jComImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComImpActionPerformed
        
        /*Si noy datos entonces regresa*/
        if(jComImp.getSelectedItem()==null)
            return;                        
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

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

    
    /*Cuando sucede una acción en el combobox de mons*/
    private void jComMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComMonActionPerformed
                        
        /*Si no hay datos entonces regresa*/
        if(jComMon.getSelectedItem()==null)
            return;                
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

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
                   
    
    /*Cuando se tipea una tecla en el campo de cost*/
    private void jTPreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPreKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPreKeyTyped

    
    /*Cuando se tipea una tecla en el campo de desc*/
    private void jTDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTDescKeyTyped

        
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed

        /*Si no a seleccionado un cliente válido entonces*/
        if(jTNom.getText().compareTo("")==0 && jTCli.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente válido.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo regresa*/
            jTCli.grabFocus();                        
            return;
        }
        
        /*Si el código del producto es cadena vacia entonces*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un producto primeramente.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo regresa*/
            jTProd.grabFocus();                        
            return;
        }
        
        /*Si la descripción del producto es cadena vacia entonces*/
        if(jTDescrip.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un producto válido.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo regresa*/
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
                    String sDif = Double.toString(Double.parseDouble(jTCant.getText().trim()) + Double.parseDouble(jTab.getValueAt(x, 2).toString()));
                    
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
        
        /*Si la cantidad es cadena vacia entonces*/
        if(jTCant.getText().compareTo("")==0)
        {   
            /*Coloca el borde rojo*/                               
            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Tienes que indicar una cantidad.", "Cantidad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jTCant.grabFocus();                        
            return;
        }
        
        /*Si la cantidad es menor a 1 entonces*/
        if(Double.parseDouble(jTCant.getText()) < 1)
        {
            /*Coloca el borde rojo*/                               
            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0.", "Cantidad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jTCant.grabFocus();                        
            return;
        }
         
        /*Si el precio es cadena vacia entonces*/
        if(jTPre.getText().replace("$", "").replace(",", "").compareTo("")==0)
        {
            
            /*Coloca el borde rojo*/                               
            jTPre.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Indica un precio primero.", "Precio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo regresa*/
            jTPre.grabFocus();                        
            return;
        }
        
        /*Si el precio es 0 entonces*/
        if(Double.parseDouble(jTPre.getText().replace("$", "").replace(",", ""))== 0)
        {
            /*Coloca el borde rojo*/                               
            jTPre.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El precio es $0.00.", "Precio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jTPre.grabFocus();                        
            return;
        }
        
        
        /*Si el código de la unidad es cadena vacia entonces*/
        if(jComUnid.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComUnid.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Tienes que indicar una unidad.", "Unidad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jComUnid.grabFocus();                        
            return;
        }     
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ; 
        
        if(sPrimVent==false)
        {
            if(!jCConta.isSelected())
            {
                /*Si el total es mayor al saldo disponible entonces*/
                if(Double.parseDouble(jTTot.getText().replace("$", "").replace(",", "")) > Double.parseDouble(jTSaldDispo.getText().replace("$", "").replace(",", "")))
                {            
                    /*Comprueba la configuración para vender sobre límite de crédito en las facturas*/
                    boolean bSi = false;
                    try
                    {
                        sQ = "SELECT val FROM confgral WHERE conf = 'slimtcredfac' AND clasif = 'vtas'";
                        st = con.createStatement();
                        rs = st.executeQuery(sQ);
                        /*Si hay datos*/
                        if(rs.next())
                        {
                            /*Si no esta habilitado para que se pueda vender sobre límite de crédito de la cliente entonces coloca la bandera*/
                            if(rs.getString("val").compareTo("1")==0)                                   
                                bSi = true;                        
                        }            
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                        return;                                            
                    }   

                    /*Si no esta permitido vender sobre el límite de crédito y si el cliente tiene crédito entonces*/
                    if(!bSi && Double.parseDouble(jTDiaCre.getText())>0)
                    {
                        /*Obtiene el saldo disponible*/
                        String sSald    = jTSaldDispo.getText();

                        /*Dale formato de mon al saldo disponible*/                            
                        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                        double dCant    = Double.parseDouble(sSald);                
                        sSald           = n.format(dCant);

                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "El total de la venta: " + jTTot.getText() + " es mayor que el saldo: " + sSald + " del cliente. Se necesita permiso de administrador para completar la venta.", "Venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        /*Obtiene la imágen si es que tiene*/
        
                        /*Pide clave de administrador*/            
                        Star.gClavMast = new ClavMast(this, 1);
                        Star.gClavMast.setVisible(true);
                        sPrimVent=true;
                    }/*Fin de if(!bSi)*/
                
                }/*Fin de if(Double.parseDouble(jTTot.getText().replace("$", "").replace(",", "")) > Double.parseDouble(jTSaldDispo.getText().replace("$", "").replace(",", "")))*/                                
            
            }
        }
        /*Si es un kit entonces*/
        if(jTEsKit.getText().compareTo("1")==0)
        {
                
            /*Comprueba si tiene componentes o no*/
            boolean bSiHay  = false;
            try
            {                  
                sQ = "SELECT prod FROM kits WHERE codkit = '" + jTProd.getText().trim() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces coloca la bandera*/
                if(rs.next())
                    bSiHay  = true;                                        
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                       
            }
            
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
            
        }/*Fin de if(jTextFieldEsKit.getText().compareTo("1")==0)*/	                                            

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

        
        /*Comprueba si la unidad tomada es la que tiene el producto asignada*/        
        String sSiUnid = Star.sEsUnidProd(con, jTProd.getText().trim(), jComUnid.getSelectedItem().toString().trim());        
        
        /*Si hubo error entonces regresa*/
        if(sSiUnid==null)
            return;
        
        /*Obtiene la unidad del producto*/        
        String sUnidProd = Star.sGetUnidProd(con, jTProd.getText().trim());        
        
        /*Si hubo error entonces regresa*/
        if(sUnidProd==null)
            return;
        
        /*Si la unidad no es la misma que tiene el producto entonces*/
        if(sSiUnid.compareTo("0")==0)
        {
            /*Comprueba si la unidad que se quiere usar es alguna unidad base de la unidad del producto*/
            if(!Star.bEsUnidBas(sUnidProd, jComUnid.getSelectedItem().toString().trim()))
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La unidad que se quiere manejar: " + jComUnid.getSelectedItem().toString().trim() + " no es base de la unidad: " + sUnidProd + " original del producto y no se puede realizar el movimiento", "Unidades", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;                
            }
        }
        
        //Declara variables locales
        double iCont      = 1;
        boolean bNesSer= false;
        
        //Se obtiene la cantidad del producto
        String sCanEnt = jTCant.getText().trim();

        //Si el producto necesita a fuerzas serie entonces al contador se le da la cantidad para que inserte esas series
        if(Star.iProdSolSer(con, jTProd.getText().trim())==1 && !jCBack.isSelected())
        {
            
            //Se obtiene el numero de partidas
            iCont   = Double.parseDouble(jTCant.getText().trim());
            sCanEnt = "1";
            bNesSer = true;
        }
        
        //Cambio alan
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        //Contadores para ver cuantos elementos faltan de agregar
        iCantAgr = 1;
        iCantTot = Double.parseDouble(jTCant.getText().trim());

        
        //Si no hay archivo entonces o es sin serie
        if((jTCarSer.getText().trim().compareTo("")==0 || bNesSer==false))
        {
            //Se utiliza la funcion de agregar productos si tiene serie se hara el numero de veces dependiendo de la cantidad
            for(double i = 0; i < iCont ; i++)
               vAgreProd(sCanEnt,"","");
        }
        else
            vSerDoc(jTCarSer.getText().trim());
        
        //Se limpia el campo de cargar programa
        jTCarSer.setText("");
        
        /*Función para limpiar todos los campos de los productos*/
        vLimpP();
            
        /*Coloca el foco del teclado en el campo del producto*/
        jTProd.grabFocus();
        
    }//GEN-LAST:event_jBNewActionPerformed


    //Cuando se va a analizar un documento con series
    private void vSerDoc(String sRut)
    {    
        /*Crea la instancia hacia el archivo a importar*/
        FileInputStream file;
        try
        {
            file    =  new FileInputStream(new File(sRut));
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());
            return;                                                
        }                    

        /*Instancia el objeto de excel*/
        XSSFWorkbook wkbok;
        try
        {
            wkbok   = new XSSFWorkbook(file);
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());
            return;                                                
        }   
        
        //Se cierra el archivo
        try
        {
            file.close();
        }
        catch(IOException expnIO)
        {            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());
            return;                                                
        }
        
        /*Obtiene la primera hoja del libro*/
        XSSFSheet sheet         = wkbok.getSheetAt(0);

        /*Contador de fila*/
        int iConta                  = 1;  
        int iContRep                = 0;

        /*Inicializa el contador de la celda por cada fila*/
        int iContCell               = 1;

        /*Recorre todas las hojas de excel*/
        Iterator<Row> rowIt     = sheet.iterator();
        while(rowIt.hasNext())
        {                    
            /*Recorre todas columnas del archivo*/
            Row row             = rowIt.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            
            /*Variable para leer las celdas*/
            String sIn;      

            /*Contiene el código del producto y la serie*/
            String sSer  = "", sComen="";

            /*Recorre todas las celdas de la fila*/
            while(cellIterator.hasNext())
            {
                /*Obtiene el objeto de la celda*/
                Cell cell       = cellIterator.next();                 

                /*Determina el tipo de celda que es*/
                switch(cell.getCellType())
                {
                    /*En caso de que sea de tipo string entonces*/
                    case Cell.CELL_TYPE_STRING:

                        /*Obtiene el valor de la celda*/
                        sIn         = cell.getStringCellValue();                                                            

                        /*Si es la celda 1 entonces*/
                        if(iContCell==1)
                        {
                            /*Si es el fin del archivo entonces*/
                            if(sIn.compareTo("FINARCHIVO")==0)
                            {

                                /*Mensajea y regresa*/
                                JOptionPane.showMessageDialog(null, "Exito en la importación de " + (iConta - 1) + " series.", "Series", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                     
                                return;

                            }
                            /*Else no es el final del archivo entonces*/
                            else
                                /*Guarda la serie*/
                                sSer       = cell.getStringCellValue();

                        }/*Fin de if(iContCell==1)*/
                        
                        /*Si es la celda 1 entonces*/
                        if(iContCell==2)
                                /*Guarda la serie*/
                                sComen       = cell.getStringCellValue();

                        break;

                    /*En caso de que sea de tipo número entonces*/
                    case Cell.CELL_TYPE_NUMERIC:        
                        
                        //Se le da formato por si es numerico los campos puedan ser leidos
                        java.text.DecimalFormat df = new java.text.DecimalFormat("##################.##################");
                        
                        //Si es la celda 1 entonces obtiene la serie
                        if(iContCell==1)                            
                            sSer       = df.format(cell.getNumericCellValue());
                            
                        //Si es la celda 2 entonces guarda la serie
                        if(iContCell==2)                            
                            sComen       = df.format(cell.getNumericCellValue());
                        
                        break;

                }/*Fin de switch(cell.getCellType())*/

                /*Aumenta en uno el contador de las celdas*/
                ++iContCell;

            }/*Fin de while(cellIterator.hasNext())*/

            /*Aumenta en uno el contador de las filas*/
            ++iConta;

            /*Resetea el contador de celdas*/
            iContCell   = 1;
        
            //Abre la base de datos nuevamente
            Connection con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Se envia la serie para validar y regresa la nueva serie con mayúsculas
            sSer = Star.sValSer(sSer);
            
            //Si no es valida la serie
            if(sSer==null)
            {
                //Se suma al contador de repetidos
                iContRep++;
                continue;
            }
            
            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;       
            String      sQ;
            
            /*Comprueba si no se debe vender abajo de las existencias*/
            boolean bSiExistN  = false;
            try
            {
                sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vendsinexistfac'";                        
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
            
            boolean bSerSinex   = false;
            
            //Se toma el valor que tiene las existencias de ese producto con esa serie por almacen
            String sExist=Star.iCantSer(sSer,con,jTProd.getText().trim(),jComAlma.getSelectedItem().toString());
            if(sExist==null)
                return;
            else if(sExist.compareTo("no existe")==0)
            {
                //Se suma al contador de repetidos
                iContRep++;
                continue;
            }
                
            //Si la configuración es que no se permita salidas sin existencias entonces
            if(bSiExistN==true)
            {         

                /*Si la tabla de partidas no tiene partidas entonces*/
                if(jTab.getRowCount()==0)
                {
                    /*Si la cantidad que se quiere insertar es mayor a la permitida de existencia entonces*/
                    if(Double.parseDouble(Star.sCantUnid(jComUnid.getSelectedItem().toString().trim(), "1"))>Double.parseDouble(sExist))
                    {
                        //Se suma al contador de repetidos
                        iContRep++;
                        continue;
                    }


                }/*Fin de if(jTab.getRowCount()==0)*/
                /*Else si tiene datos*/
                else
                {
                    /*Crea la cantidad correcta que se puede insertar*/
                    String sCantCo  = "1";

                    /*Recorre la tabla de partidas para búscar si esta esta serie ya cargada*/                               
                    for(int x = 0; x < jTab.getRowCount(); x++)
                    {
                        /*Si ya existe serie en la fila entonces*/            
                        if(jTab.getValueAt(x, 21).toString().compareTo(sSer)==0)
                        {
                            /*Crea la cantidad correcta que se puede insertar*/
                            sCantCo  = Double.toString(Double.parseDouble(sCantCo) + Double.parseDouble(jTab.getValueAt(x, 2).toString()));

                            /*Si la cantidad que se quiere insertar es mayor a la permitida de existencia entonces*/
                            if(Double.parseDouble(Star.sCantUnid(jComUnid.getSelectedItem().toString().trim(), sCantCo))>Double.parseDouble(sExist))
                                bSerSinex   = true;

                        }/*Fin de if(jTab.getValueAt(x, 21).toString().compareTo(jTSerProd.getText())==0)*/

                    }

                    //Si marco un error el for
                    if(bSerSinex==true)
                    {
                        //Se suma al contador de repetidos
                        iContRep++;
                        continue;
                    }

                }/*Fin de else*/

            }//Fin else if(bSiExistN==true)

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            if(sSer.trim().compareTo("") != 0)
                vAgreProd("1",sSer,sComen);

        }/*Fin de while(rowIt.hasNext())*/ 
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Se almacenaron " + (iConta-1-iContRep) + " series y hubo " + iContRep + " error(s)", "Éxito al leer archivo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
    
    }//Final private int iContSerDoc(String sRut)
    
    
    //Cambio alan
    //Esta funcion es la misma que se tenia para cargar el producto
    private void vAgreProd(String sCanEnt,String sCamp1, String sCamp2) 
    { 
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ; 
        
        /*Obtiene si se tiene que agregar la garantía en la descripción del producto*/
        boolean bGara   = false;
	try
        {                  
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'garandescfac'";
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

        //Obtiene la existencia general del producto
        double dExist   = Star.dExistGralProd(con, jTProd.getText().trim());
        
        //Coloca la existencia del producto en el control
        jTExist.setText(Double.toString(dExist));
        
	/*Lee el costo*/
        String sCost                           = jTCost.getText().replace("$", "").replace(",", "");
        
        /*Si el costo es cadena vacia entonces que sea 0*/
        if(sCost.compareTo("")==0)
            sCost                               = "0";
        
        /*Lee el precio*/
        String sPre                             = jTPre.getText().replace("$", "").replace(",", "");                
        
        //Obtiene la configuración para saber si mostrar o no el mensaje de existencias negativas
        int iRes    = Star.iGetConfExistNeg(con);

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si se tiene que mostrar entonces coloca la bandera
        boolean bSi     = false;
        if(iRes==1 && !jCBack.isSelected())
            bSi         = true;
        
        /*Si la cantidad es mayor a la existencia y si se tiene que mostrar el mensaje entonces*/
        if(bSi)
        {
            if(Double.parseDouble(sCanEnt) > Double.parseDouble(jTExist.getText()))
            {                
                /*Mensajea para que el usuario este enterado*/                
                if(jTEsKit.getText().compareTo("0")==0)
                    JOptionPane.showMessageDialog(null, "No hay existencias suficientes para este producto.", "Existencia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            }
        }                                                       

        /*Comprueba si no se debe vender abajo de las existencias*/
        boolean bSiExistN  = false;
        
        //Se crean variables
        String sSer="";
        String sComen="";
        
        //Para evitar un proceso mas primero verifica si es backorder 
        if(!jCBack.isSelected())
        {
            try
            {
                sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vendsinexistfac'";                        
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
            
            iRes        = Star.iProdSolSer(con, jTProd.getText().trim());

            //Si hubo error entonces regresa
            if(iRes==-1)
                return;
            //Si el producto solicita número de serie entonces
            if(iRes==1)
            {
                boolean bSer        = true;
                

                while(bSer)
                {
                    
                    boolean bSerSinex   = false;
                    
                    //Si no hay archivo entonces
                    if(jTCarSer.getText().trim().compareTo("")==0)
                    {
                        /*Pidele al usuario que ingrese serie y comentario*/   
                        SelSer lo     = new SelSer(jTSerProd,jTComenSer, iCantAgr + "-" + iCantTot,jTProd.getText().trim(),jComAlma.getSelectedItem().toString().trim(),0);                        
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
                            //Se reduce en uno el contador de total
                            iCantTot--;
                            bSer=false;
                            continue;
                        }

                        /*Si es cadena vacia entonces*/
                        if(sSer.compareTo("")==0)
                        {                    
                            /*Mensajea*/
                            JOptionPane.showMessageDialog(null, "Ingresa una serie válida.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                            continue;
                        }

                        //Se toma el valor que tiene las existencias de ese producto con esa serie por almacen
                        String sExist=Star.iCantSer(sSer,con,jTProd.getText().trim(),jComAlma.getSelectedItem().toString());
                        if(sExist==null)
                            return;
                        else if(sExist.compareTo("no existe")==0)
                        {
                            /*Mensajea*/
                            JOptionPane.showMessageDialog(null, "La serie " + sSer + " no existe en este almacen.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                            continue;
                        }
                        //Si la configuración es que no se permita salidas sin existencias entonces
                        if(bSiExistN==true)
                        {         
                            
                            /*Si la tabla de partidas no tiene partidas entonces*/
                            if(jTab.getRowCount()==0)
                            {
                                /*Si la cantidad que se quiere insertar es mayor a la permitida de existencia entonces*/
                                if(Double.parseDouble(Star.sCantUnid(jComUnid.getSelectedItem().toString().trim(), sCanEnt))>Double.parseDouble(sExist))
                                {
                                    /*Mensajea*/
                                    JOptionPane.showMessageDialog(null, "No se pueden insertar mas cantidades para la serie del producto ya que no alcanzan las existencias.", "Series Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                                    continue;
                                }
                                

                            }/*Fin de if(jTab.getRowCount()==0)*/
                            /*Else si tiene datos*/
                            else
                            {
                                /*Crea la cantidad correcta que se puede insertar*/
                                String sCantCo  = sCanEnt;

                                /*Recorre la tabla de partidas para búscar si esta esta serie ya cargada*/                               
                                for(int row = 0; row < jTab.getRowCount(); row++)
                                {
                                    /*Si ya existe serie en la fila entonces*/            
                                    if(jTab.getValueAt(row, 21).toString().compareTo(sSer)==0)
                                    {
                                        /*Crea la cantidad correcta que se puede insertar*/
                                        sCantCo  = Double.toString(Double.parseDouble(sCantCo) + Double.parseDouble(jTab.getValueAt(row, 2).toString()));
                                        
                                        /*Si la cantidad que se quiere insertar es mayor a la permitida de existencia entonces*/
                                        if(Double.parseDouble(Star.sCantUnid(jComUnid.getSelectedItem().toString().trim(), sCantCo))>Double.parseDouble(sExist))
                                        {
                                            /*Mensajea*/
                                            JOptionPane.showMessageDialog(null, "No se pueden insertar mas cantidades para la serie del producto ya que no alcanzan las existencias.", "Series Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                                            bSerSinex   = true;
                                        }

                                    }/*Fin de if(jTab.getValueAt(row, 21).toString().compareTo(jTSerProd.getText())==0)*/

                                }/*Fin de for( int row = 0; row < jTab.getRowCount(); row++)*/
                            
                                //Si marco un error el for
                                if(bSerSinex==true)
                                    continue;
                                
                            }/*Fin de else*/

                        }//Fin else if(bSiExistN==true)

                        //Si el comentario es nulo se pone en blanco
                        if(sComen==null)
                            sComen="";
                        
                    }
                    else
                    {

                        //Se obtienen los valores
                        sSer    = sCamp1;
                        sComen  = sCamp2;

                    }

                    //Se aumenta el contador de agregados
                    iCantAgr++;

                    //Se cambia el flag indicando que esta bien la serie
                    bSer=false;

                }//Fin while(bSer)
                
                //Cambio alan
                /*Si la serie es nula entonces regresa*/
                if(sSer==null)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    //Mensajea
                    JOptionPane.showMessageDialog(null, "El producto solicita número de serie.", "Número serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                    return;
                }

            }//Fin if(iRes==1)
            
        }//Fin if(!jCBack.isSelected())

        /*Lee el campo de la existencia ya actualizada*/
        String sExist                          = jTExist.getText().trim();
        
        /*Si no se puede vender con existencia baja entonces*/
        if(bSiExistN && (Double.parseDouble(Star.sCantUnid(jComUnid.getSelectedItem().toString().trim(), sCanEnt)) > Double.parseDouble(sExist)))
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se puede vender sin existencias.", "Existencias", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
            return;                    
        }
        
        /*Si en el campo de costo hay un valor entonces*/        
        if(sCost.replace("$", "").replace(",", "").compareTo("")!=0)
        {                        
            /*Obtiene si el producto se puede vender a bajo del costo o no*/        
            try
            {                  
                sQ = "SELECT bajcost FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                if(rs.next())
                {                                        
                    /*Si el producto no se puede vender abajo del costo entonces*/
                    if(rs.getString("bajcost").compareTo("0")==0)                                    
                    {
                        /*Quitale el formato de moneda al costo*/
                        sCost   = sCost.replace("$", "").replace(",", "");

                        /*Si el precio es menor al costo entonces*/
                        if(Double.parseDouble(jTPre.getText().replace("$", "").replace(",", ""))< Double.parseDouble(sCost))
                        {
                            //Cierra la base de datos
                            if(Star.iCierrBas(con)==-1)
                                return;

                            /*Mensajea*/
                            JOptionPane.showMessageDialog(null, "No se puede vender a bajo del costo este producto.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                            
                            /*Coloca el valor anterior que tenía y regresa*/
                            jTPre.setText(sCostOri);                                                        
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
            
        }/*Fin de if(sCost.compareTo("")!=0)*/
        
        /*Lee el descuento*/
        String sDesc                = jTDesc.getText();
        
        /*Si el descuento es cadena vacia entonces que sea 0*/
        if(sDesc.compareTo("")==0)                                
            sDesc                   = "0";
                        
        /*Lee la lista*/
        String sList                = jTList.getText();
              
        
        /*Declara variables de bloque*/
        String sCant =sCanEnt;
        
        
        /*Si se tiene que manejar backorder entonces*/
        String sBack    = "";
        if(jCBack.isSelected())
        {
            sBackOrder=1;
            /*Obtiene la fecha de back order*/
            Date fe                 =  jDBack.getDate();
            SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");
            sBack                   = sdf.format(fe);      
        }                
        
        /*Recorre toda la tabla de partidas de factura para saber si el producto ya esta en la tabla y si se puede facturar la cantidad en existente*/
        int row                         = 0;           
        for( ; row < jTab.getRowCount(); row++)
        {
            
            
            /*Si en la fila ya existe el producto entonces*/
            if(jTab.getValueAt(row, 1).toString().compareTo(jTProd.getText())==0 && jTab.getValueAt(row, 4).toString().compareTo(jComAlma.getSelectedItem().toString())==0 && jTab.getValueAt(row, 21).toString().compareTo(sSer)==0 && jTab.getValueAt(row, 23).toString().compareTo(jTGara.getText().trim())==0 && jTab.getValueAt(row, 19).toString().compareTo(sBack)==0)                
            {
                /*Lee la cantidad que ya se va a facturar*/
                sCant   = Double.toString(Double.parseDouble(sCant) + Double.parseDouble(jTab.getValueAt(row, 2).toString()));
                
                /*Si la suma de la cantidad en la fila + la cantidad deseada a insertar es mayor a la existencia entonces*/
                if((Double.parseDouble(Star.sCantUnid(jComUnid.getSelectedItem().toString().trim(), sCant)) > Double.parseDouble(sExist)))
                {                    
                    /*Mensajea para que el usuario este enterado si no es un kit*/                                 
                    if(jTEsKit.getText().compareTo("0")==0 && bSi)
                    {
                        /*Mensajea de las existencias*/
                        JOptionPane.showMessageDialog(null, "No hay existencias suficientes para este producto.", "Existencia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
                        /*Pon la bandera para que ya no se muestre el mensajea*/
                        bSi = false;
                        
                    }
                    /*Si no se puede vender con existencias baja entonces*/
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
                
            }/*Fin de if(jTabParts.getValueAt(row, 1).toString().compareTo(jTProd.getText())==0)                */
            
        }/*Fin de for( ; row < jTablePartidas.getRowCount(); row++)*/
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si el precio tiene el signo de dollar quitaselo*/
        sPre                        = sPre.replace("$", "").replace(",", "");
        
        /*Obtiene la cantidad*/
        String sCan                 = sCanEnt;

        /*Si el descuento no es cadena vacia entonces obtiene el descuento del costo*/
        String sDescConve;
        if(sDesc.compareTo("")!= 0)      
            sDescConve              = Double.toString((Double.parseDouble(sDesc) / 100 ) * Double.parseDouble(sPre)*Double.parseDouble(sCan)); 
        /*Else, colocalo en 0 para poder hacer la resta*/
        else
            sDescConve              = "0";
        
        /*Crea el importe*/
        String sImpo                = Double.toString(Double.parseDouble(sCanEnt) * Double.parseDouble(sPre) - Double.parseDouble(sDescConve));
        
        /*Dale formato de moneda al impuesto*/        
        double dCant                = Double.parseDouble(sImpo);                
        NumberFormat n              = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sImpo                       = n.format(dCant);
        
        /*Dale formato de moneda al precio*/        
        dCant                       = Double.parseDouble(sPre);                
        n                           = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sPre                        = n.format(dCant);
        
        /*Recorre toda la tabla de partidas de factura*/
        boolean bSRegistro   = false;
        row                  = 0;
        double dCantidad;        
        for( ; row < jTab.getRowCount(); row++)
        {            
            /*Si el código que va a insertar el usuario ya esta en la tabla, el descuento, la lista, la moneda, talla, color y el precio entonces*/            
            if(jTab.getValueAt(row, 1).toString().compareTo(jTProd.getText())==0 && jTab.getValueAt(row, 4).toString().compareTo(jComAlma.getSelectedItem().toString())==0 && jTab.getValueAt(row, 3).toString().compareTo(jComUnid.getSelectedItem().toString())==0 &&  jTab.getValueAt(row, 5).toString().compareTo(sList)==0 && jTab.getValueAt(row, 7).toString().replace("$", "").replace(",", "").compareTo(sPre.replace("$", "").replace(",", ""))==0 && jTab.getValueAt(row, 8).toString().compareTo(sDesc)==0 && jTab.getValueAt(row, 9).toString().compareTo(jTValImp.getText())==0 && jTab.getValueAt(row, 13).toString().compareTo(jComTall.getSelectedItem().toString())==0 && jTab.getValueAt(row, 14).toString().compareTo(jComColo.getSelectedItem().toString())==0 && jTab.getValueAt(row, 15).toString().compareTo(jTLot.getText())==0 && jTab.getValueAt(row, 16).toString().compareTo(jTPedimen.getText())==0 && jTab.getValueAt(row, 17).toString().compareTo(jTCadu.getText())==0 && jTab.getValueAt(row, 18).toString().compareTo(jTId.getText())==0 && jTab.getValueAt(row, 19).toString().compareTo(sBack)==0 && jTab.getValueAt(row, 21).toString().compareTo(sSer)==0)
            {
                /*Obtiene la cantidad que tiene originalmente en la fila*/
                dCantidad           = Double.parseDouble(jTab.getValueAt(row, 2).toString());
                
                /*Deja la cantidad correcta que es la cantidad enterior más la cantidad nueva*/
                sCan                = Double.toString(Double.parseDouble(sCan) + dCantidad);
                
                /*Obtiene el precio de la fila*/
                String sPre1        = jTab.getValueAt(row, 7).toString().replace("$", "").replace(",", "");                        
                
                /*Crea el importe correcto multiplicanto el precio por la cantidad*/
                sImpo               = Double.toString(Double.parseDouble(sPre1) * Double.parseDouble(sCan));                
                
                /*Crea el importe del descuento*/
                String sImpoDesc    = Double.toString(Double.parseDouble(sImpo) * (Double.parseDouble(sDesc) / 100));                
                
                //Quita el descuento al impuesto
                sImpo               = Double.toString(Double.parseDouble(sImpo) - Double.parseDouble(sImpoDesc));                
                                
                //Si el impuesto esta vacio entonces que sea 0
                String sImpAux      =jTab.getValueAt(row, 9).toString();
                if(sImpAux.compareTo("")==0)
                    sImpAux="0";
                    
                /*Crea el nuevo impuesto total de la fila*/
                String  sTotImpue   = Double.toString(Double.parseDouble(sImpo) * Double.parseDouble(sImpAux) / 100);

                /*Dale formato de moneda al total del impuesto*/                
                dCant               = Double.parseDouble(sTotImpue);                                                    
                sTotImpue           = n.format(dCant);
                
                /*Dale formato de moneda al importe*/                
                dCant               = Double.parseDouble(sImpo);                                                    
                sImpo               = n.format(dCant);
                
                /*Dale formato de moneda al importe del descuento*/                
                dCant               = Double.parseDouble(sImpoDesc);                                                    
                sImpoDesc           = n.format(dCant);
        
                /*Pon la bandera para saber que si existe ya en la tabla*/
                bSRegistro   = true;
                
                /*Modifica la cant y el cost solamente en la fila en donde esta el registro*/
                jTab.setValueAt(sCan,        row, 2);
                jTab.setValueAt(sImpo,       row, 10);
                jTab.setValueAt(sTotImpue,   row, 12);
                jTab.setValueAt(sImpoDesc,   row, 20);
            
                /*Sal del bucle*/
                break;
            }
            
        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
                       
        /*Si el producto no existe en la tabla entonces*/
        String sKit;
        if(!bSRegistro)
        {   
            /*Si el valor del impuesto es cadena entonces que sea 0*/
            String sValImpue    = jTValImp.getText();
            if(sValImpue.compareTo("")==0)
                sValImpue       = "0";
                
            /*Crea el impuesto total de la fila*/
            String sTotImpue= Double.toString(Double.parseDouble(sImpo.replace("$", "").replace(",", "")) * (Double.parseDouble(sValImpue) / 100));
           
            /*Dale formato de moneda al impuesto total*/                        
            dCant           = Double.parseDouble(sTotImpue);                
            sTotImpue       = n.format(dCant);
            
            /*Dale formato de moneda al importe del descuento total*/                        
            dCant           = Double.parseDouble(sDescConve);                
            sDescConve      = n.format(dCant);
            
            /*Comprueba si es kit o no*/
            if(jTEsKit.getText().compareTo("1")==0)
                sKit    = "Si";
            else
                sKit    = "No";
                        
            /*Convierte la cantidad a double para ponerla en la tabla*/
            double dCan = Double.parseDouble(sCan);
            
            /*Si esta permitida la garantía entonces obtiene la garantia del control*/
            String  sGara   = "";
            if(bGara)
                sGara       = jTGara.getText().trim();
            
            /*Determina si debe de poner o no el comentario de la serie*/
            String sComenSer    = sComen.trim();
            if(sSer.compareTo("")==0)
                sComenSer       = "";

            /*Si la serie tiene datos entonces*/
            String sSerProd     = sSer.trim();
            if(sSerProd.compareTo("")!=0)
                sSerProd        = "SER:" + sSerProd;

            /*Obtiene el lote correcto*/
            String sLotC        = jTLot.getText().trim();
            if(sLotC.compareTo("")!=0)
                sLotC           = "LOT:" + sLotC;
            
            /*Obtiene el pediemento correcto*/
            String sPedimen     = jTPedimen.getText().trim();
            if(sPedimen.compareTo("")!=0)
                sPedimen        = "PED:" + sPedimen;
            
            /*Agrega los datos en la tabla*/
            DefaultTableModel temp = (DefaultTableModel)jTab.getModel();
            Object nu[]     = {iContFi, jTProd.getText(), dCan, jComUnid.getSelectedItem().toString(), jComAlma.getSelectedItem().toString(), sList, jTDescrip.getText().trim() + " " + sSerProd + " " + sComenSer + " " + sLotC + " " + sPedimen + " " + sGara + " " + jComTall.getSelectedItem().toString() + " " + jComColo.getSelectedItem().toString() + " " + sBack, sPre, sDesc, jTValImp.getText(), sImpo, sKit, sTotImpue, jComTall.getSelectedItem().toString(), jComColo.getSelectedItem().toString(), jTLot.getText(), jTPedimen.getText(), jTCadu.getText(), jTId.getText(), sBack, sDescConve, sSer.trim(), sComenSer, sGara, jTCost.getText().replace("$", "").replace(",", ""), jTCantLot.getText().trim(), jComImp.getSelectedItem().toString().trim()};        
            temp.addRow(nu);
            
            /*Aumenta el contador de filas en uno*/
            iContFi = iContFi + 1;             
        }
        
        /*Recalcula los totales leyendo toda la tabla de partidas*/
        vRecTots(); 
        
    }//Fin private void vAgreProd(String sCanEnt,String sCamp1, String sCamp2) 
    
    
    /*Cuando se presiona el botón de borrar partida*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si no se a seleccionado nada en la tabla entonces*/
        if(jTab.getSelectedRow() == -1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una partida para borrar.", "Borrar Partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla de partidas y regresa*/
            jTab.grabFocus();                        
            return;
        }   
        
        /*Preguntar al usuario si esta seguro de que querer borrar la partida*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la(s) partida(s)?", "Borrar Partida", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Borralo de la tabla*/            
            tm.removeRow(iSel[x]);

            /*Resta en uno el contador de filas el contador de filas en uno*/
            --iContFi;
        }                    
        sBackOrder=0;
        /*Recalcula los totales leyendo toda la tabla de partidas*/
        vRecTots();
                
    }//GEN-LAST:event_jBDelActionPerformed

            
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

    
    /*Cuando se gana el foco del teclado en el campo de correo 1*/
    private void jTCo1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo1.setSelectionStart(0);jTCo1.setSelectionEnd(jTCo1.getText().length());        
        
    }//GEN-LAST:event_jTCo1FocusGained

    
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

    
    /*Cuando se presiona una tecla en el campo de correo 1*/
    private void jTCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo1KeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo1KeyPressed

    
    
    
    /*Cuando se gana el foco del teclado en el campo de costo*/
    private void jTCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCost.setSelectionStart(0);jTCost.setSelectionEnd(jTCost.getText().length());                
        
    }//GEN-LAST:event_jTCostFocusGained

    
    /*Cuando se presiona una tecla en el campo de costo*/
    private void jTCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCostKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCostKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de guardar datos del cliente*/
    private void jCGDatsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGDatsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCGDatsKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de correo 2*/
    private void jTCo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo2.setSelectionStart(0);jTCo2.setSelectionEnd(jTCo2.getText().length());        
        
    }//GEN-LAST:event_jTCo2FocusGained

    
    /*Cuando se gana el foco del teclado en el correo 3*/
    private void jTCo3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCo3.setSelectionStart(0);jTCo3.setSelectionEnd(jTCo3.getText().length());        
        
    }//GEN-LAST:event_jTCo3FocusGained

    
    /*Cuando se presiona una tecla en el campo de correo 2*/
    private void jTCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo2KeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de correo 3*/
    private void jTCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo3KeyPressed
                        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo3KeyPressed

    
    /*Cuando se presiona una tecla en el campo de correo 1*/
    private void jCCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de correo 2*/
    private void jCCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de correo 3*/
    private void jCCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo3KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de cliente*/
    private void jTCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCli.setSelectionStart(0);jTCli.setSelectionEnd(jTCli.getText().length());

    }//GEN-LAST:event_jTCliFocusGained

    
    /*Resetea los campos de la cliente*/
    private void vResEmpCam()
    {
        /*Resetea los campos que involucran a la cliente*/
        jTNom.setText       ("");
        jTCall.setText      ("");
        jTCol.setText       ("");
        jTTel.setText       ("");
        jTPai.setText       ("");
        jTCP.setText        ("");
        jTNoExt.setText     ("");
        jTNoInt.setText     ("");
        jTRFC.setText       ("");
        jTCiu.setText       ("");
        jTEstad.setText     ("");
        jTCo1.setText       ("");
        jTCo2.setText       ("");
        jTCo3.setText       ("");
        jTMetPag.setText    ("");
        jTCta.setText       ("");
        jTListEmp.setText   ("");
        jTSer.setText       ("");
        jTSaldDispo.setText ("0");
        jTLimCred.setText   ("0");
        jTDiaCre.setText    ("0");
        sPrimVent=false;
        if(Star.gClavMast!=null)
            Star.gClavMast=null;
    }
                
                
    /*Obtiene todos los datos del cliente*/
    private void vCargCli()
    {
        String rfc="";
        /*Esconde el label de total saldo y el control igual*/
        jLTotSald.setVisible(false);
        jTTotSald.setVisible(false);
    
        /*Coloca el caret en la posiciòn 0*/
        jTCli.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCli.getText().compareTo("")!=0)
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si la cliente es cadena vacia entonces*/
        if(jTCli.getText().compareTo("")==0)
        {
            /*Limpia todos los campos de la cliente y regresa*/            
            vResEmpCam();
            return;
        }            
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        try
        {
                sQ = "SELECT * FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTCli.getText().trim() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                if(rs.next())
                    {
                        rfc=    (rs.getString("rfc"));
                    }
                    else
                    {
                        //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                        return;
                    }
                }
                 catch(SQLException expnSQL)
                {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                                                           
                }
        /*Obtiene todas las direcciones del cliente existentes*/
        try
        {
            sQ = "SELECT * FROM domentcli WHERE codemp = '" + jTCli.getText().trim() + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agrega los datos en la tabla*/
            while(rs.next())
            {                
                DefaultTableModel te    = (DefaultTableModel)jTabDir.getModel();
                Object nu[]             = {rs.getString("tel"), rs.getString("lada"), rs.getString("exten"), rs.getString("cel"), rs.getString("telper1"), rs.getString("telper2"), rs.getString("calle"), rs.getString("col"), rs.getString("noext"), rs.getString("noint"), rs.getString("cp"), rs.getString("ciu"), rs.getString("estad"), rs.getString("pai"), rs.getString("co1"), rs.getString("co2"), rs.getString("co3")};
                te.addRow(nu);
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                                                           
        }
        
        //Determiina la consulta correcta dependiendo de si es o no públic general        
        if(jTCli.getText().trim().compareTo(Star.sCliMostG)==0)            
            sQ = "SELECT * FROM basdats WHERE codemp  = '" + Login.sCodEmpBD + "'";
        else
            sQ = "SELECT * FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTCli.getText().trim() + "'";
        
        //Obtiene todos los datos del cliente
        try
        {                              
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                //Si es cliente mostrador entonces el nombre va a ser PUBLICO GENERAL
                if(jTCli.getText().trim().compareTo(Star.sCliMostG)==0)                                
                    jTNom.setText       ("PUBLICO GENERAL");                                
                else
                    jTNom.setText       (rs.getString("nom"));                                                
                                
                //Si no es público general entonces si usa la lada, caso contrario solo el teléfono
                if(jTCli.getText().trim().compareTo(Star.sCliMostG)!=0)                                
                    jTTel.setText       (rs.getString("lada") + rs.getString("tel"));
                else
                    jTTel.setText       (rs.getString("tel"));
                
                //Sigue agregando los datos del cliente
                jTCall.setText      (rs.getString("calle"));
                jTCol.setText       (rs.getString("col"));                
                jTPai.setText       (rs.getString("pai"));
                jTCP.setText        (rs.getString("cp"));
                jTNoInt.setText     (rs.getString("noint"));
                jTNoExt.setText     (rs.getString("noext"));
                jTRFC.setText       (rfc);
                jTCiu.setText       (rs.getString("ciu"));
                
                //Coloca el correo dependiendo si es público general o no
                if(jTCli.getText().trim().compareTo(Star.sCliMostG)!=0)                                
                {
                    jTCo1.setText       (rs.getString("co1"));
                    jTCo2.setText       (rs.getString("co2"));
                    jTCo3.setText       (rs.getString("co3"));
                }
                else
                    jTCo1.setText       (rs.getString("corr"));
                
                //Coloca algunos datos solo si no es público general
                if(jTCli.getText().trim().compareTo(Star.sCliMostG)!=0)   
                {
                    jTSer.setText       (rs.getString("ser"));               
                    jTListEmp.setText   (rs.getString("list"));                
                    jTMetPag.setText    (rs.getString("metpag"));
                    jTCta.setText       (rs.getString("cta"));                
                    
                    /*Si los días de crédito es cadena vacia entonces que sea 0*/
                    if(rs.getString("diacred").compareTo("")==0)
                        jTDiaCre.setText("0");
                    else
                        jTDiaCre.setText(rs.getString("diacred"));
                    
                    /*Función para que coloca las condiciones*/
                    vCond(con, jTCond, rs.getString("emps.LIMTCRED"), rs.getString("emps.DIACRED"));                                
                }
                
                
                //Sigue agregando los datos del cliente                                    
                jTEstad.setText     (rs.getString("estad"));                    
                                                    
                /*Coloca todos los controles al principio para que sean visibles*/                
                jTNom.setCaretPosition      (0);
                jTSer.setCaretPosition      (0);
                jTCall.setCaretPosition     (0);
                jTCol.setCaretPosition      (0);
                jTTel.setCaretPosition      (0);
                jTPai.setCaretPosition      (0);
                jTCP.setCaretPosition       (0);
                jTNoExt.setCaretPosition    (0);
                jTNoInt.setCaretPosition    (0);
                jTRFC.setCaretPosition      (0);
                jTCiu.setCaretPosition      (0);
                jTEstad.setCaretPosition    (0);
                jTCo1.setCaretPosition      (0);
                jTCo2.setCaretPosition      (0);
                jTCo3.setCaretPosition      (0);
                jTNom.setCaretPosition      (0);                                
                
                /*Si es el cliente mostrador entonces desmarca el checkbox de modificarlo*/
                if(jTCli.getText().compareTo(Star.sCliMostG)==0)               
                    jCGDats.setSelected(false);                                    
                
            }    
            /*Else, la cliente no existe*/
            else
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Resetea los campos de la cliente y regresa*/
                vResEmpCam();
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
        
    }/*Fin de private void vCargCli()*/
        
        
    /*Cuando se pierde el foco del teclado en el campo de código de cliente*/
    private void jTCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusLost

        /*Limpia toda la tabla de partidas*/            
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);

        /*Selecciona moneda vacia*/
        if(jComMon.isEnabled())
            jComMon.setSelectedItem     ("");
            
        /*Limpia toda la tabla de direcciones del cliente*/            
        dm                  = (DefaultTableModel)jTabDir.getModel();
        dm.setRowCount(0);

        /*Resetea los totales*/
        jTSubTot.setText    ("$0.00");
        jTImp.setText       ("$0.00");
        jTTot.setText       ("$0.00");                
        jTTotDesc.setText   ("$0.00");

        /*Resetea el contador de filas*/
        iContFi = 1;

        /*Coloca uno en la cantidad del producto*/
        jTCant.setText              ("1");
    
        /*Limpia toda la parte de los productos*/
        vLimpP();
        
        /*Procesa todo esto en una función*/
        vCargCli();                       
                       
    }//GEN-LAST:event_jTCliFocusLost

    
    /*Cuando se presiona una tecla en el campo del código de la cliente*/
    private void jTCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTCli.getText(), 5, jTCli, jTNom, jTSer, "", null);
            b.setVisible(true);
            
            /*Procesa todo esto en una función*/
            vCargCli();                       
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else        
            vKeyPreEsc(evt);        

    }//GEN-LAST:event_jTCliKeyPressed

    
    /*Cuando se presiona el botón de buscar conincidencias*/
    private void jBCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCliActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTCli.getText(), 5, jTCli, jTNom, jTSer, "", null);
        b.setVisible(true);

        /*Coloca el nombre el cliente al principio*/
        jTNom.setCaretPosition(0);
        
        /*Coloca el foco del teclado en el campo del código de la cliente*/
        jTCli.grabFocus();

        /*Procesa todo esto en una función*/
        vCargCli();               
        
    }//GEN-LAST:event_jBCliActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCliKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCliKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del nom de la cliente*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length());

    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se presiona una tecla en el campo de nom de cliente*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTNomKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del código del artículo*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);                
        jTProd.setSelectionEnd(jTProd.getText().length());

    }//GEN-LAST:event_jTProdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del código del producto*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Funciòn para procesar esta parte*/
        vCargP();
        
    }//GEN-LAST:event_jTProdFocusLost

    
    /*Funciòn para procesar toda la informaciòn del producto cuando se pierde el foco del teclado en el control*/
    private void vCargP()
    {
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Coloca el caret al principio del control*/
        jTProd.setCaretPosition(0);                
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Coloca 1 en la cantidad*/
        jTCant.setText("1");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene todos los datos del producto en base a su código y si no existe activa la bandera*/        
        try
        {
            sQ = "SELECT utilvta1, garan.DESCRIP AS garadescrip, prods.DESCRIP, CASE WHEN prods.COMPUE = 1 THEN 'Si' ELSE 'No' END AS esk, prods.UNID, prods.IMPUE, prods.COST, prods.COMPUE, prods.COSTRE, prods.ALMA, prods.EXIST, almas.ALMADESCRIP, prods.BAJCOST FROM prods LEFT OUTER JOIN garan ON garan.GARA = prods.GARAN LEFT JOIN almas ON almas.ALMA = prods.ALMA WHERE prods.PROD = '" + jTProd.getText().trim() + "' AND esvta = 1";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {                
                /*Obtiene el impuesto*/
                String sImpue   = rs.getString("prods.IMPUE");

                /*Obtiene el último costo*/
                String sCost    = Star.sUltCost(con, jTProd.getText().trim());
                
                /*Dale formato de moneda al último costo*/                                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());                                
                double dCant    = Double.parseDouble(sCost);                                
                sCost           = n.format(dCant);
                
                /*Obtiene el precio que debe tener correctamente por las reglas de negocio*/
                String sPre     = Star.sPreCostVta(jTProd.getText().trim(), jTCli.getText().trim(), "1", "vtas", "fac");                

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
                dCant           = Double.parseDouble(sPre);                
                n               = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sPre            = n.format(dCant);

                /*Coloca si es kit o no de manera visible*/
                jTKit.setText(rs.getString("esk"));
                                
                /*Coloca la garantía*/
                jTGara.setText(rs.getString("garadescrip"));
                
                /*Coloca si es kit o no*/
                if(rs.getString("prods.COMPUE").compareTo("1")==0)
                    jTEsKit.setText("1");
                else
                    jTEsKit.setText("0");
                
                /*Colocalos en los campos correspondientes*/                
                jTExist.setText             (rs.getString("prods.EXIST"));
                jTDescripAlma.setText       (rs.getString("almas.ALMADESCRIP"));                                                                
                jTDescrip.setText           (rs.getString("prods.DESCRIP"));
                jTCost.setText              (sCost);                
                jTPre.setText               (sPre);
                jComUnid.setSelectedItem    (rs.getString("prods.UNID"));
                jComImp.setSelectedItem     (sImpue);
                
                /*Selecciona mon nacional*/
                jComMon.setSelectedItem     ("MN");
                
                /*Coloca todos los controles al principio de su control*/                
                jTExist.setCaretPosition        (0);
                jTDescripAlma.setCaretPosition  (0);
                jTDescrip.setCaretPosition      (0);
                jTCost.setCaretPosition         (0);
                jTPre.setCaretPosition          (0);
            }
            /*Else, el codigo del producto no existe entonces limpia los controles de los productos*/
            else                            
                vLimpP();
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
                
    }/*Fin de private void vCargP()*/            
    
    /*Cuando se presiona una tecla en el campo de código del producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar producto*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBProd.doClick();
        /*Else if se presiono CTRL + B entonces*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_B)
        {
            /*Llama a la forma para búscar productos avanzadamente*/
            ptovta.BuscAvan w = new ptovta.BuscAvan(this, jTProd, null, null, null);
            w.setVisible(true);
            
            /*Coloca el foco del teclado en el campo del producto*/
            jTProd.grabFocus();            
        }   
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTProdKeyPressed

    
    /*Función para limpiar todos los campos de los productos*/
    private void vLimpP()
    {
        /*Deshabilita el check para el backorder*/
        jCBack.setSelected          (false);
        
        //Regresa el comando a la normalidad
        jCBack.setEnabled           (true);
        
        //Se vuelve a la normalidad para el ingreso de series
        jTCarSer.setEditable        (true);
        jBCarSer.setEnabled         (true);
                
        /*Resetea los campos*/
        jTProd.setText              ("");
        jComAlma.setSelectedItem    ("");
        jTExist.setText             ("");
        jTDescripAlma.setText       ("");
        jTDescrip.setText           ("");
        jTSerProd.setText           ("");
        jTGara.setText              ("");
        jTComenSer.setText          ("");
        jTCost.setText              ("$0.00");            
        jComImp.setSelectedItem     ("");
        jComUnid.setSelectedItem    ("");
        jTLot.setText               ("");
        jTPedimen.setText           ("");
        jTCadu.setText              ("");
        jTDesc.setText              ("0");
        jTId.setText                ("");
        jTList.setText              ("1");        
        jTPre.setText               ("$0.00");
        jTCantLot.setText           ("0");
        //CAMBIO ALAN
        jTCarSer.setText            ("");
        
        /*Esconde el control de imágen*/
        jLImg.setVisible            (false);
            
        /*Selecciona unidad vacia*/
        jComUnid.setSelectedItem    ("");
        
        /*Si el almacén de venta no es cadena vacia entonces selecciona el almacén global*/
        if(sAlmGlo.compareTo("")!=0)
            jComAlma.setSelectedItem(sAlmGlo);        
    }
    
    
    /*Cuando se presiona el botón de buscar*/
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProd.getText(), 2, jTProd, jTDescrip, jTAlma2, "", null);
        b.setVisible(true);
        
        /*Coloca la descripción del producto al principio del control*/
        jTDescrip.setCaretPosition(0);
        
        /*Coloca el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();
        
        /*Funciòn para procesar esta parte*/
        vCargP();

    }//GEN-LAST:event_jBProdActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar coincidencia*/
    private void jBProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBProdKeyPressed

    
    /*Cuando se tipea una tecla en el campo del código de la cliente*/
    private void jTCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en mayùsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))        
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCliKeyTyped

    
    /*Cuando se tipea una tecla en el campo de calle*/
    private void jTCallKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCallKeyTyped

    
    /*Cuando se tipea una tecla en el campo de col*/
    private void jTColKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTColKeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono*/
    private void jTTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para el teléfono entonces*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '-') && (evt.getKeyChar() != ' ') && (evt.getKeyChar() != '(') && (evt.getKeyChar() != ')'))         
            evt.consume();
        
    }//GEN-LAST:event_jTTelKeyTyped

    
    /*Cuando se tipea una tecla en el campo de pai*/
    private void jTPaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPaiKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                                      
        
    }//GEN-LAST:event_jTPaiKeyTyped

    
    /*Cuando se tipea una tecla en el campo de cp*/
    private void jTCPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCPKeyTyped

    
    
    /*Cuando se tipea una tecla en el campo del número de interior*/
    private void jTNoIntKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTNoIntKeyTyped

    
    /*Cuando se tipea una tecla en el campo de RFC*/
    private void jTRFCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRFCKeyTyped
                
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTRFCKeyTyped

    
    /*Cuando se tipea una tecla en el campo de ciu*/
    private void jTCiuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCiuKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCiuKeyTyped

    
    /*Cuando se tipea una tecla en el campo de estad*/
    private void jTEstadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstadKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTEstadKeyTyped
        
    
    /*Cuando se pierde el foco del teclado en el campo de calle*/
    private void jTCallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusLost

        /*Coloca el caret al principio del control*/
        jTCall.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCall.getText().compareTo("")!=0)
            jTCall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTCallFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de col*/
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost

        /*Coloca el caret al principio del control*/
        jTCol.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCol.getText().compareTo("")!=0)
            jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTCol.getText().length()> 255)
            jTCol.setText(jTCol.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTColFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de teléfono*/
    private void jTTelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusLost

        /*Coloca el caret al principio del control*/
        jTTel.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTTel.getText().compareTo("")!=0)
            jTTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTTel.getText().length()> 255)
            jTTel.setText(jTTel.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTTelFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de pai*/
    private void jTPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusLost

        /*Coloca el caret al principio del control*/
        jTPai.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTPai.getText().compareTo("")!=0)
            jTPai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
               
    }//GEN-LAST:event_jTPaiFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de CP*/
    private void jTCPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusLost

        /*Coloca el caret al principio del control*/
        jTCP.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCP.getText().compareTo("")!=0)
            jTCP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTCPFocusLost

    
    /*Cuando se pierde el foco del teclado en e lcampo de número de exterior*/
    private void jTNoExtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusLost

        /*Coloca el caret al principio del control*/
        jTNoExt.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTNoExt.getText().compareTo("")!=0)
            jTNoExt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTNoExtFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del número de interior*/
    private void jTNoIntFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusLost
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTNoInt.getText().length()> 21)
            jTNoInt.setText(jTNoInt.getText().substring(0, 21));
        
    }//GEN-LAST:event_jTNoIntFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del RFC*/
    private void jTRFCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusLost

        /*Coloca el caret al principio del control*/
        jTNoInt.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTRFC.getText().compareTo("")!=0)
            jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTRFCFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la ciu*/
    private void jTCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusLost

        /*Coloca el caret al principio del control*/
        jTCiu.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCiu.getText().compareTo("")!=0)
            jTCiu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTCiuFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de estad*/
    private void jTEstadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusLost

        /*Coloca el caret al principio del control*/
        jTEstad.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTEstad.getText().compareTo("")!=0)
            jTEstad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
    }//GEN-LAST:event_jTEstadFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de co1*/
    private void jTCo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusLost

        /*Coloca el caret al principio del control*/
        jTCo1.setCaretPosition(0);
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTCo1.getText().length()> 100)
            jTCo1.setText(jTCo1.getText().substring(0, 100));
        
    }//GEN-LAST:event_jTCo1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de correo 2*/
    private void jTCo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusLost

        /*Coloca el caret al principio del control*/
        jTCo2.setCaretPosition(0);
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTCo2.getText().length()> 100)
            jTCo2.setText(jTCo2.getText().substring(0, 100));
        
    }//GEN-LAST:event_jTCo2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de correo 3*/
    private void jTCo3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusLost

        /*Coloca el caret al principio del control*/
        jTCo3.setCaretPosition(0);
        
        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTCo3.getText().length()> 100)
            jTCo3.setText(jTCo3.getText().substring(0, 100));
       
    }//GEN-LAST:event_jTCo3FocusLost
    
            
    /*Cuando se tipea una tecla en el campo del código del producto*/
    private void jTProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTProdKeyTyped

           
    /*Cuando se presiona una tecla en el checkbox de mostrar archivo*/
    private void jCMostAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMostAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMostAKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de imprimir la factura entonces*/
    private void jCImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCImpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCImpKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mandar correo*/
    private void jCMandKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMandKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMandKeyPressed

    
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

    
    /*Cuando se presiona una tecla en el checkbox de guardar pagos*/
    private void jCGuaPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGuaPagKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCGuaPagKeyPressed

    
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

    
    /*Cuando se presiona una tecla en el checkbox de contado*/
    private void jCContaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCContaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCContaKeyPressed

    
    /*Cuando se presiona una tecla en el campo de lista de pre*/
    private void jTListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTListKeyPressed

        /*Si no a seleccionado un producto entonces*/
        if(jTDescrip.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un producto.", "Lista de Precios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo del producto y regresa*/
            jTProd.grabFocus();
            return;
        }                    
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            BuscPre b = new BuscPre(this, jTList.getText(), 1, jTList, jTProd.getText());
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTListKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de lista de precios*/
    private void jTListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTListFocusGained

        /*Obtiene la lista original*/
        sListOri    = jTList.getText();                
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTList.setSelectionStart(0);jTList.setSelectionEnd(jTList.getText().length()); 
        
    }//GEN-LAST:event_jTListFocusGained

    
    /*Cuando se tipea una tecla en el campo de lista de pre*/
    private void jTListKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTListKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTListKeyTyped

    
    /*Cuando se pierde el foco del teclado en el campo de lista de pre*/
    private void jTListFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTListFocusLost
        
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

    
    /*Cuando se presiona una tecla en el botón del tipo de cambio*/
    private void jBTipCamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTipCamKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBTipCamKeyPressed

    
    /*Cuando se presiona el botón de cambiar tipo de cambio*/
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
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

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
        
        /*Bucle mientras no se inserte una cant válida para el tipo de cambio*/
        boolean bSi = false;
        String sResul = null;
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
    
    
    /*Cuando se presiona una tecla en el combobox de series*/
    private void jComSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComSerKeyPressed
   
    
    /*Cuando se presiona el botón de catálogo general*/
    private void jBCatGralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCatGralActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTACatGral.getText(), 14, null, null, null, "", jTACatGral);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();

    }//GEN-LAST:event_jBCatGralActionPerformed

    
    /*Cuando se presiona una tecla en el botón de catálogo general*/
    private void jBCatGralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCatGralKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCatGralKeyPressed

    
    
    
    
    
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
   
    
    /*Cuando se tipea una tecla en el campo de cant*/
    private void jTCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTCantKeyTyped

    
    /*Cuando se presiona una tecla en el checkbox de fecha extra*/
    private void jCFExtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCFExtKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jCFExtKeyPressed

    
    /*Cuando se presiona una tecla en el control de fecha extra*/
    private void jDFExtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFExtKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDFExtKeyPressed

    
    /*Cuando sucede una acción en el checkbox de fecha extra*/
    private void jCFExtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCFExtActionPerformed
        
        /*Si esta marcado entonces*/
        if(jCFExt.isSelected())
            jDFExt.setEnabled(true);
        else
            jDFExt.setEnabled(false);
        
    }//GEN-LAST:event_jCFExtActionPerformed

    
    /*Cuando se presiona una tecla en el botón de últimos precios*/
    private void jBUltPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUltPreKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBUltPreKeyPressed

    
    /*Cuando se presiona el botón de últimos precios*/
    private void jBUltPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUltPreActionPerformed
        
        /*Muestra la forma para ver los últimos precios*/
        UltPrecs u = new UltPrecs(jTCli.getText(), jTProd.getText(), "fac", false);
        u.setVisible(true);
        
    }//GEN-LAST:event_jBUltPreActionPerformed
       
    
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

    
    /*Cuando se presiona una tecla en el checkbox de timbrar*/
    private void jCTimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCTimKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCTimKeyPressed

    
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

    
    /*Cuando se pierde el foco del teclado en el control de la serie*/
    private void jComSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComSer.getSelectedItem().toString().compareTo("")!=0)
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComSerFocusLost

    
    /*Función para validar si puede o no modificar el cliente mostrador*/
    private void vClien()
    {
        /*Si esta seleccionado entonces*/
        if(jCGDats.isSelected())
        {
            /*Si es cliente mostrador entonces*/
            if(jTCli.getText().compareTo(Star.sCliMostG)==0)
            {
                /*Mensajea y desmarca el control*/
                JOptionPane.showMessageDialog(null, "No se puede modificar el cliente mostrador.", "Modificar cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                jCGDats.setSelected(false);                                                       
            }
        }            
    }
        
        
    
    /*Cuando se presiona una tecla en el botón de búscar lista de precios*/
    private void jBListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBListKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBListKeyPressed

    
    /*Cuando se presiona el botón de búscar lista de precio*/
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

    
    /*Cuando se pierde el foco del teclado en el combo de las unidades*/
    private void jComUnidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComUnidFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComUnid.getSelectedItem().toString().compareTo("")!=0)
            jComUnid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComUnidFocusLost

    
    /*Cuando se pierde el foco del teclado en el combo de los impuestos*/
    private void jComImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComImpFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComImp.getSelectedItem().toString().compareTo("")!=0)
            jComImp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComImpFocusLost

    
    /*Cuando se pierde el foco del teclado en el combo de las monedas*/
    private void jComMonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComMonFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComMon.getSelectedItem().toString().compareTo("")!=0)
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComMonFocusLost

    
    /*Cuando el mouse entra en el control de la imágen*/
    private void jPanImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImgMouseEntered

        /*Si no a seleccionado un prodcuto válido entonces regresa*/
        if(jTDescrip.getText().compareTo("")==0)
            return;

        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

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
            /*Si contiene archivos*/
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

    
    /*Cuando el mouse sale del control de imágen*/
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

        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

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
            /*Si contiene archivos*/
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
            /*Si contiene archivos entonces*/
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
    private void jBUltPreMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUltPreMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBUltPre.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBUltPreMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTipCamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTipCamMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTipCam.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBTipCamMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBListMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBList.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBListMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVeGranMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGranMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVeGran.setBackground(Star.colBot);                
        
    }//GEN-LAST:event_jBVeGranMouseEntered

    
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
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBProdMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCli.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBCliMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBUltPreMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUltPreMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBUltPre.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBUltPreMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTipCamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTipCamMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTipCam.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTipCamMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/    
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVeGranMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGranMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVeGran.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBVeGranMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBListMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBList.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBListMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
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

    
    /*Cuando se pierde el foco del teclaod en el campo de la serie*/
    private void jTSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerFocusLost
        
        /*Coloca el caret al principio del control*/
        jTSer.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSerFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del nombre del cliente*/
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost
        
        /*Coloca el caret al principio del control*/
        jTNom.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del a lista de precios*/
    private void jTListEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTListEmpFocusLost
        
        /*Coloca el caret al principio del control*/
        jTListEmp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTListEmpFocusLost

    
    
    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del producto*/
    private void jTDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusLost
        
        /*Coloca el caret al principio del control*/
        jTDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripFocusLost
   
    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del almacén*/
    private void jTDescripAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlmaFocusLost
        
        /*Coloca el caret al principio del control*/
        jTDescripAlma.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripAlmaFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción de la unidad*/
    private void jTDescripUniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripUniFocusLost
        
        /*Coloca el caret al principio del control*/
        jTDescripUni.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripUniFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del valor del impuesto*/
    private void jTValImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTValImpFocusLost
        
        /*Coloca el caret al principio del control*/
        jTValImp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTValImpFocusLost

    
    
    /*Cuando se pierde el foco del teclado en el campo del costo*/
    private void jTCostFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostFocusLost
        
        /*Coloca el caret al principio del control*/
        jTCost.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCostFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la existencia*/
    private void jTExistFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExistFocusLost
        
        /*Coloca el caret al principio del control*/
        jTExist.setCaretPosition(0);
        
    }//GEN-LAST:event_jTExistFocusLost

    
    
    /*Cuando se pierde el foco del teclado en el campo del método de pago*/
    private void jTMetPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFocusLost
        
        /*Coloca el caret al principio del control*/
        jTMetPag.setCaretPosition(0);
        
    }//GEN-LAST:event_jTMetPagFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta*/
    private void jTCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusLost
        
        /*Coloca el caret al principio del control*/
        jTCta.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCtaFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de las observaciones*/
    private void jTAObservFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusLost
        
        /*Coloca el caret al principio del control*/
        jTAObserv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTAObservFocusLost

    
    /*Cuando se presiona una tecla en el botón de domicilio de entrega*/
    private void jBDomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDomKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDomKeyPressed

    
    /*Cuando se presiona el botón de domicilio de entrega*/
    private void jBDomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDomActionPerformed

        /*Si no a seleccionado un cliente válido entonces*/
        if(jTNom.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente válido.", "Domicilio Entrega", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTCli.grabFocus();
            return;
        }
        
        /*Muestra la forma del domicilio de entrega*/
        DomEntCliFac d = new DomEntCliFac(jTabDir, jFram);
        d.setVisible(true);
        
    }//GEN-LAST:event_jBDomActionPerformed

    
    /*Cuando el mouse entra en el botón del domiclio de entrega*/
    private void jBDomMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDomMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDom.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDomMouseEntered

    
    /*Cuando el mouse sale del botón de domicilio de entrega*/
    private void jBDomMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDomMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDom.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDomMouseExited

    
    /*Cuando se presiona una tecla en el campo de las observaciones*/
    private void jTAObservKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAObservKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAObservKeyPressed

    
    
    /*Cuandos sucede una acción en el combo de las tallas*/
    private void jComTallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComTallActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComTall.getSelectedItem()==null)
            return;
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

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
            sQ = "SELECT descrip FROM tall WHERE cod = '" + jComTall.getSelectedItem().toString() + "'";
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

    
    /*Cuando se presiona una tecla en el combo de las tallas*/
    private void jComTallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComTallKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComTallKeyPressed

    
    /*Cuando sucede una acción en el combo de los colores*/
    private void jComColoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComColoActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComColo.getSelectedItem()==null)
            return;
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

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
            sQ = "SELECT descrip FROM colos WHERE cod = '" + jComColo.getSelectedItem().toString() + "'";
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

    
    /*Cuando se gana el foco del teclado en el cmapo de la descripción del color*/
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

    
    /*Cuando se gana el foco del teclado en el campo kit*/
    private void jTKitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTKitFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTKit.setSelectionStart(0);jTKit.setSelectionEnd(jTKit.getText().length());        
        
    }//GEN-LAST:event_jTKitFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del kit*/
    private void jTKitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTKitFocusLost
        
        /*Coloca el caret al principio del control*/
        jTKit.setCaretPosition(0);
        
    }//GEN-LAST:event_jTKitFocusLost

    
    /*Cuando se presiona una tecla en el campo de la fecha de back order*/
    private void jDBackKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDBackKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDBackKeyPressed

    
    /*Cuando se presiona una tecla en el check de backorder*/
    private void jCBackKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCBackKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCBackKeyPressed

    
    /*Cuando se presiona el botón de ver descripción mas grande*/
    private void jBGranDescripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGranDescripActionPerformed

        /*Muestar la forma para ver la descripción en grande*/
        DescripGran b = new DescripGran(jTDescrip, null);
        b.setVisible(true);

    }//GEN-LAST:event_jBGranDescripActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver descripción en mas grande*/
    private void jBGranDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGranDescripKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGranDescripKeyPressed

    
    /*Cuando se presiona una tecla en el control de la fecha de entrega*/
    private void jDFEntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFEntKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDFEntKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del total del descuento*/
    private void jTTotDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotDescFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTotDesc.setSelectionStart(0);jTTotDesc.setSelectionEnd(jTTotDesc.getText().length()); 
        
    }//GEN-LAST:event_jTTotDescFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del sub total*/
    private void jTSubTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusLost
        
        /*Coloca el caret al principio del control*/
        jTSubTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSubTotFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del impuesto*/
    private void jTImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusLost
 
        /*Coloca el caret al principio del control*/
        jTImp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTImpFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del total del descuento*/
    private void jTTotDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotDescFocusLost
        
        /*Coloca el caret al principio del control*/
        jTTotDesc.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTotDescFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del total*/
    private void jTTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusLost
        
        /*Coloca el caret al principio del control*/
        jTTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTotFocusLost

    
    /*Cuando se presiona una tecla en el campo del descuento*/
    private void jTTotDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotDescKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTotDescKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo del catálogo general*/
    private void jTACatGralFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTACatGralFocusLost
        
        /*Coloca el caret al principio del control*/
        jTACatGral.setCaretPosition(0);
        
    }//GEN-LAST:event_jTACatGralFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del catálogo general*/
    private void jTACatGralFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTACatGralFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTACatGral.setSelectionStart(0);jTACatGral.setSelectionEnd(jTACatGral.getText().length());        
        
    }//GEN-LAST:event_jTACatGralFocusGained

    
    /*Cuando se presiona una tecla en el campo del catálogo general*/
    private void jTACatGralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTACatGralKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTACatGralKeyPressed

    
    /*Cuando se presiona una tecla en el control del scroll de las observaciones*/
    private void jSObservKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSObservKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSObservKeyPressed

    
    /*Cuando la forma se activa*/
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        /*Obtiene la dirección de si mismo*/
        jFram   = this;
                
    }//GEN-LAST:event_formWindowActivated

    
    /*Cuando se presiona una tecla en el check de remisión*/
    private void jCRemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCRemKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCRemKeyPressed

    
    /*Cuando sucede una acción en el check de remisión*/
    private void jCRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCRemActionPerformed
        
        /*Si esta seleccionado el check de remisión entonces*/
        if(jCRem.isSelected())                              
        {
            /*Cambia el texto del label de tipo de venta*/
            jLNot.setText       ("NUEVA REMISIÓN");        
            jLTipVta.setText    ("VENTA DE CONTADO");
            
            /*Marca el checkbox de contado*/
            jCConta.setSelected(true);
        }
        else
        {
            /*Cambiar el texto del label del tipo de venta*/
            jLNot.setText       ("NUEVA FACTURA");
            
            /*Coloca el valor previo que tenía el label*/
            jLTipVta.setText    (sLabTex);
            
            /*Desmarca el checkbox de contado*/
            jCConta.setSelected(false);
        }
        
        /*Carga nuevamente las series en el control*/
        vCargSer();
        
    }//GEN-LAST:event_jCRemActionPerformed

    
    
    /*Cuando el mouse entra en el botín de ultimos costos todos*/
    private void jBUltCostTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUltCostTMouseEntered

        /*Cambia el color del fondo del botón*/
        jBUltCostT.setBackground(Star.colBot);

    }//GEN-LAST:event_jBUltCostTMouseEntered

    
    /*Cuando el mouse sale del botón de últimos costos todos*/
    private void jBUltCostTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUltCostTMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBUltCostT.setBackground(Star.colOri);

    }//GEN-LAST:event_jBUltCostTMouseExited

    
    /*Cuando se presiona el botón de ver los últimos 100 precios de venta a los clientes*/
    private void jBUltCostTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUltCostTActionPerformed

        /*Si no a ingresado un producto entonces*/
        if(jTDescrip.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa un producto primero para poder ver los últimos precios de todos los clientes.", "Ultimos Precios Clientes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo*/
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Coloca el foco del teclado en el campo del producto y regresa*/
            jTDescrip.grabFocus();
            return;
        }

        /*Muestra la forma para ver los últimos precios de todos los clientes*/
        UltPrecs u = new UltPrecs(jTCli.getText(), jTProd.getText(), "fac", true);
        u.setVisible(true);

    }//GEN-LAST:event_jBUltCostTActionPerformed

    
    /*Cuando se presiona una tecla en el botón de últimos 100 precos de todos los clientes*/
    private void jBUltCostTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUltCostTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBUltCostTKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del total del saldo*/
    private void jTTotSaldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotSaldFocusGained
               
        /*Selecciona todo el texto cuando gana el foco*/
        jTTotSald.setSelectionStart(0);jTTotSald.setSelectionEnd(jTTotSald.getText().length()); 
        
    }//GEN-LAST:event_jTTotSaldFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del total del saldo*/
    private void jTTotSaldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotSaldFocusLost
        
        /*Coloca el caret al principio del control*/
        jTTotSald.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTotSaldFocusLost

    
    /*Cuando se presiona una tecla en el campo del total del saldo*/
    private void jTTotSaldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotSaldKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTotSaldKeyPressed

    
    /*Cuando sucede una acción en el combo de los almacenes*/
    private void jComAlmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComAlmaActionPerformed
        
        /*Si no hay datos entonces regresa*/
        if(jComAlma.getSelectedItem()==null)
            return;
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

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
            sQ = "SELECT almadescrip FROM almas WHERE alma = '" + jComAlma.getSelectedItem().toString() + "'";
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

    
    /*Cuando se presiona una tecla en el combo de los almacenes*/
    private void jComAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComAlmaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComAlmaKeyPressed

    private void jComColoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComColoFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComColo.getSelectedItem().toString().compareTo("")!=0)
            jComColo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComColoFocusLost

    
    /*Cuando se pierde el foco del teclado en el combo de la talla*/
    private void jComTallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComTallFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComTall.getSelectedItem().toString().compareTo("")!=0)
            jComTall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComTallFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del almacén*/
    private void jComAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComAlmaFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComAlma.getSelectedItem().toString().compareTo("")!=0)
            jComAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComAlmaFocusLost

    
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

    
    /*Cuando se presiona una tecla en el botón de existencias por almacén*/
    private void jBExisAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBExisAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBExisAlmaKeyPressed


    //Método para hacer escalable cuando se selecciona el check de contado
    private void vCheckConta()
    {
        //Si esta seleccionado entonces cambia el label para que sea de contado
        if(jCConta.isSelected())                    
            jLTipVta.setText("VENTA DE CONTADO");        
        //Else será de crédito entonces
        else
        {
            //Comprueba si el cliente no esta bloqueado
            int iRes    = Star.iGetBloqCredCliProv(null, "cli", jTCli.getText());

            //Si hubo error entonces regresa
            if(iRes==-1)
                return;
            
            //Si el crédito lo tiene bloqueado entonces
            if(iRes==1)
            {
                //Mensajea
                JOptionPane.showMessageDialog(null, "El cliente: " + jTCli.getText().trim() + " tiene el crédito bloqueado y solo puede ser de contado.", "Crédito", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Selecciona nuevamente el control y regresa
                jCConta.setSelected(true);
                return;
            }
            
            //Coloca el label que será de crédito
            jLTipVta.setText(sLabTex);        
        }
        
    }//Fin de private void vCheckConta()
    
    
    /*Cuando sucede una acción en el check de contado*/
    private void jCContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCContaActionPerformed
        
        //Procesa la selección o deselección en esta función
        vCheckConta();
        
    }//GEN-LAST:event_jCContaActionPerformed

    
    /*Cuando se presiona una tecla en el campo de fecha*/
    private void jDFechKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFechKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDFechKeyPressed

    
    /*Cuando sucede una acción en el combo de series*/
    private void jComSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComSerActionPerformed
        
        /*Si es nulo entonces regresa*/
        if(jComSer.getSelectedItem()==null)
            return;
                
        /*Determina que tipo de documento es*/
        String sTip = "FAC";
        if(jCRem.isSelected())
            sTip    = "REM";
                    
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "consecs", "WHERE tip = '" + sTip + "' AND ser = '" + jComSer.getSelectedItem().toString().trim() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComSer.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComSerActionPerformed

    
    //Cuando sucede una acción en el check de modificar datos del cliente
    private void jCGDatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCGDatsActionPerformed
        
        /*Comprueba si puede o no modificar cliente mostrador en caso de que este seleccionado*/
        if(jCGDats.isSelected())                                                
            vClien();                                
            
    }//GEN-LAST:event_jCGDatsActionPerformed

    
    //Cuando se pierde el foco del teclado en el combo de la forma de pago
    private void jComFormPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComFormPagFocusLost
 
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jComFormPag.getSelectedItem().toString().compareTo("")!=0)
            jComFormPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComFormPagFocusLost

    
    //Cuando se presiona una tecla en el combo de la forma de pago
    private void jComFormPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComFormPagKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComFormPagKeyPressed

    
    //Cuando sucede una acción en el combo de las fromas de pago
    private void jComFormPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComFormPagActionPerformed
        
        /*Si no hay datos entonces regresa*/
        if(jComFormPag.getSelectedItem()==null)
            return;                
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

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

    
    //Cuando el mouse entra en el botón de cargar serie
    private void jBCarSerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCarSerMouseEntered

        /*Cambia el color del fondo del botón*/
        jBCarSer.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCarSerMouseEntered

    
    //Cuando el mouse sale del botón de cargar serie
    private void jBCarSerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCarSerMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBCarSer.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBCarSerMouseExited

    
    //Cuando se presiona el botón de cargar serie
    private void jBCarSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCarSerActionPerformed

        /*Configura el file chooser para escoger la ruta del directorio donde esta el archivo*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar archivo con series");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION)
            return;

        /*Si el archivo no es EXCEL entonces*/
        if(!fc.getSelectedFile().getName().toLowerCase().endsWith(".xls")&&!fc.getSelectedFile().getName().toLowerCase().endsWith(".xlsx"))
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El archivo seleccionado no es un archivo de EXCEL.", "Carga series", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Coloca la ruta completa en el control*/
        jTCarSer.setText(fc.getCurrentDirectory().getAbsolutePath() + "\\" + fc.getSelectedFile().getName());
        jTCarSer.setCaretPosition(0);
            
    }//GEN-LAST:event_jBCarSerActionPerformed

    
    //Cuando se presiona una tecla en el botón de cargar serie
    private void jBCarSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCarSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCarSerKeyPressed

    
    //Cuando se gana el foco del teclado en el campo de cargar serie
    private void jTCarSerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCarSerFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCarSer.setSelectionStart(0);jTCarSer.setSelectionEnd(jTCarSer.getText().length());
        
    }//GEN-LAST:event_jTCarSerFocusGained

    
    //Cuando se pierde el foco del teclado en el campo de cargar serie
    private void jTCarSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCarSerFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCarSer.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTCarSer.getText().compareTo("")!=0)
            jTCarSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTCarSerFocusLost

    
    //Cuando se presiona una tecla en el campo de cargar serie
    private void jTCarSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCarSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCarSerKeyPressed

        
    //Cuando se hace alguna accion en checkbox de backorder
    private void jCBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBackActionPerformed
        
        /*Si esta seleccionado entonces*/
        if(jCBack.isSelected())
        {   
            sBackOrder=1;
            //Se vuelve vacio el espacio para el documento de las series
            jTCarSer.setEditable(false);
            jTCarSer.setText    ("");
            jBCarSer.setEnabled (false);
        }
        /*Else no esta marcado entonces*/
        else
        {
            //Se vuelve a la normalidad para el ingreso de series
            jTCarSer.setEditable(true);
            jBCarSer.setEnabled (true);
         
        }
        
    }//GEN-LAST:event_jCBackActionPerformed

    
    //Cuando sucede una acción en el radio de pagado
    private void jRPagadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRPagadActionPerformed

        //Manda el foco del botón de guardar
        jBGuar.grabFocus();

    }//GEN-LAST:event_jRPagadActionPerformed

    
    //Cuando se presion una tecla en el radio de pagado
    private void jRPagadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRPagadKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jRPagadKeyPressed

    
    //Cuando sucede una acción en el radio de no pagado
    private void jRNoPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRNoPagActionPerformed

        //Manda el foco del botón de guardar
        jBGuar.grabFocus();

    }//GEN-LAST:event_jRNoPagActionPerformed

    
    //Cuando se presiona una tecla en el radio de no pagado
    private void jRNoPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRNoPagKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jRNoPagKeyPressed

    private void jTCondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTCondActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTCondActionPerformed

   
   /*Carga las series en el combobox*/
   private void vCargSer()
   {
        /*Borra los items en el combobox de series*/
        jComSer.removeAllItems();

        /*Agrega el elemento vacio*/
        jComSer.addItem("");

        /*Crea la condición de búsqueda*/
        String sConO    = " tip = 'FAC'";
        if(jCRem.isSelected())
            sConO       = " tip = 'REM'";
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;                
        String      sQ; 
        
        /*Obtiene todas las series actualizadas y cargalas en el combobox*/
        try
        {
            sQ = "SELECT ser FROM consecs WHERE " + sConO;                                   
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
        
    }/*Fin de private void vCargSer()*/
   
   
    /*Función para validar cuando es una remisión o factura*/
    private void vRemFac()
    {
        /*Si esta seleccionado el check de remisión entonces*/
        if(jCRem.isSelected())
        {
            /*Deseleccionalo y coloca nueva factura*/            
            jCRem.setSelected(false);
            jLTipVta.setText("NUEVA FACTURA");
        }
        else
        {
            /*Seleccionalo y coloca nueva remisión*/
            jCRem.setSelected(true);
            jLTipVta.setText("NUEVA REMISIÓN");        
        }
        
        /*Carga nuevamente las series en el control*/
        vCargSer();
    }
        
        
    /*Función para recalcular los totales*/
    private void vRecTots()
    {
        //Declara variables locales
        String          sSubTot     = "0";
        String          sImpue      = "0";                        
        String          sTotDesc    = "0";                        
        String          sTotCost    = "0";
        String          sNot        = "0";
        
        
        
        
        /*Si la tabla no tiene elementos entonces*/
        if(jTab.getRowCount()== 0 )
        {
            /*Colocar en los campos de subtotal, impuesto y total $0.00*/
            jTSubTot.setText            ("$0.00");
            jTImp.setText               ("$0.00");
            jTTot.setText               ("$0.00");
            jTTotDesc.setText           ("$0.00");
            jTTotSald.setText           ("$0.00");
            jTTotCost.setText           ("0");
            
            /*Regresa*/
            return;            
        }
                
        /*Recorre toda la tabla de partidas*/
        for( int row = 0; row < jTab.getRowCount(); row++)
        {                      
            /*Si la partida es una nota de crédito entonces*/
            if(jTab.getValueAt(row, 2).toString().compareTo("0")==0)
            {                      
                /*Ve sumando el total de nota de crédito y continua*/
                sNot                    = Double.toString(Double.parseDouble(sNot) + Double.parseDouble(jTab.getValueAt(row, 10).toString().replace("$", "").replace(",", "")));                
                continue;
            }
            
            /*Ve sumando el importe de la fila al subtotal global*/
            sSubTot                     = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(jTab.getValueAt(row, 2).toString().replace("$", "").replace(",", ""))*Double.parseDouble(jTab.getValueAt(row, 7).toString().replace("$", "").replace(",", "")));
            
            /*Ve sumando el importe del descuento al global*/
            sTotDesc                    = Double.toString(Double.parseDouble(sTotDesc) + Double.parseDouble(jTab.getValueAt(row, 20).toString().replace("$", "").replace(",", "")));

            /*Ve sumando el importe del costo al global*/
            sTotCost                    = Double.toString(Double.parseDouble(sTotCost) + (Double.parseDouble(jTab.getValueAt(row, 24).toString()) * Double.parseDouble(jTab.getValueAt(row, 2).toString())));
            
            /*Si es una nota de crédito el impuesto brincalo*/
            if(jTab.getValueAt(row, 12).toString().compareTo("0")==0)
                continue;

            /*Ve sumando el impuesto de la fila*/
            sImpue                      = Double.toString(Double.parseDouble(sImpue) + Double.parseDouble(jTab.getValueAt(row, 12).toString().replace("$", "").replace(",", "")));
        }
        
        //Comprueba si debe de redondear o no el total
        String sResp    = Star.sGetConfRedon(null);
        
        //Si hubo error entonces regresa
        if(sResp==null)
            return;
        
        //Obtiene si se tiene que redondear y las posiciones a redondear
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sResp, "|");
        String sRedo    = stk.nextToken();
        String sNumRedo = stk.nextToken();                
        
        //Si no se tiene que redondear entonces deja la cantidad en 2
        if(sRedo.compareTo("0")==0)
            sNumRedo    = "2";
        
        /*Crea el total*/
        String sTot                     = Double.toString(Star.dRound((Double.parseDouble(sSubTot) + Double.parseDouble(sImpue)-Double.parseDouble(sTotDesc)),Integer.parseInt(sNumRedo)));
        
        /*Si hay saldo de nota de crédito entonces*/
        if(Double.parseDouble(sNot)>0)
        {            
            /*Muestra el label de total a pagar y el campo igual*/
            jLTotSald.setVisible(true);
            jTTotSald.setVisible(true);
            
            /*Crea el saldo correcto que es el total menos el saldo de la nota de crédito*/
            String sSaldTot     = Double.toString(Double.parseDouble(sTot) - Double.parseDouble(sNot));
            
            /*Formatea el saldo a moneda*/
            NumberFormat    n               = NumberFormat.getCurrencyInstance(Locale.getDefault());
            double dCant                    = Double.parseDouble(sSaldTot);        
            sSaldTot                        = n.format(dCant);
            
            /*Colocalo en su lugar*/
            jTTotSald.setText(sSaldTot);                        
        }
        
        /*Si el usuario no tiene días de crédito y si tiene saldo disponible entonces y si no tiene saldo nota de crédito*/        
        if(Double.parseDouble(jTDiaCre.getText())<=0 && Double.parseDouble(jTSaldDispo.getText())>0 && Double.parseDouble(sNot)==0)
        {            
            /*Muestra el label de total a pagar y el campo igual*/
            jLTotSald.setVisible(true);
            jTTotSald.setVisible(true);
            
            /*Obtiene el total menos el saldo*/
            String sTotSald                 = Double.toString(Double.parseDouble(sTot) - Double.parseDouble(jTSaldDispo.getText().replace("$", "").replace(",", "")));
            
            /*Formatea el total menos el saldo a moneda*/
            NumberFormat    n               = NumberFormat.getCurrencyInstance(Locale.getDefault());
            double dCant                    = Double.parseDouble(sTotSald);        
            sTotSald                        = n.format(dCant);
            
            /*Colocalo en su lugar*/
            jTTotSald.setText(sTotSald);                        
        }
        
        /*Formatea la moneda el subtotal*/
        NumberFormat    n               = NumberFormat.getCurrencyInstance(Locale.getDefault());
        double dCant                    = Double.parseDouble(sSubTot);        
        sSubTot                         = n.format(dCant);

        /*Colocalo en el campo de subtotal*/
        jTSubTot.setText                (sSubTot);       
                
        /*Dale formato de moneda al impuesto*/
        dCant                           = Double.parseDouble(sImpue);        
        sImpue                          = n.format(dCant);
        
        /*Colocalo el impuesto en el control*/
        jTImp.setText                   (sImpue);                       
        
        /*Formatear el total a moneda*/
        dCant                           = Double.parseDouble(sTot);        
        sTot                            = n.format(dCant);
        
        /*Coloca el total en su control*/                
        jTTot.setText                   (sTot);
        
        /*Formatear el total de descuento a moneda*/
        dCant                           = Double.parseDouble(sTotDesc);        
        sTotDesc                        = n.format(dCant);
        
        /*Coloca el total en su control*/                
        jTTotDesc.setText               (sTotDesc);
        
        /*Coloca el total del costo en su lugar*/
        jTTotCost.setText               (sTotCost);
        
    }/*Fin de private void vRecTots()*/                                                                   
                
    
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
        /*Else if se presiona Alt + R entonces válida cuando sea una remisión o una factura*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_R)        
            vRemFac();        
        /*Si se presiona ALT + L entonces*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_L)
        {
            /*Abre la forma de lotes y pedimentos*/
            LotPed p = new LotPed(this, jTProd.getText().trim(), jTProd, jTAlma2, jTLot, jTPedimen, jTCadu, jTCantLot, jTId);
            p.setVisible(true);
            
            /*Carga todos los datos del producto en los controles*/
            vCargP();                        
        }
        /*Si se presiona la tecla de F2 entonces*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
        {
            /*Muestra la forma para aplicar nota de crédito al cliente*/            
            BuscVta b = new BuscVta(this, "", null, "notc", jTab, jTCli.getText(), iContFi);
            b.setVisible(true);
            
            /*Recalcula los totales leyendo toda la tabla de partidas*/
            vRecTots();                            
        }
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)                    
            jBNew.doClick();        
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona F4*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4 )
        {
            /*Marca o desmarca el checkbox de no mandar correo electrónico*/
            if(jCMand.isSelected())
                jCMand.setSelected(false);
            else
                jCMand.setSelected(true);
        } 
        /*Si se presiona F5*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5 )
        {
            /*Marca o desmarca el checkbox de imprimir*/
            if(jCImp.isSelected())
                jCImp.setSelected(false);
            else
                jCImp.setSelected(true);            
        } 
        /*Si se presiona F6*/
        else if(evt.getKeyCode() == KeyEvent.VK_F6 )
        {
            /*Marca o desmarca el checkbox de monstrar archivo*/
            if(jCMostA.isSelected())
                jCMostA.setSelected(false);
            else
                jCMostA.setSelected(true);            
        } 
        /*Si se presiona F7*/
        else if(evt.getKeyCode() == KeyEvent.VK_F7 )
        {
            /*Marca o desmarca el checkbox de guardar pagos*/
            if(jCGuaPag.isSelected())
                jCGuaPag.setSelected(false);
            else
                jCGuaPag.setSelected(true);            
        } 
        /*Si se presiona F8 entoncs procesa en un método la selección o deselección*/
        else if(evt.getKeyCode() == KeyEvent.VK_F8 )
            vCheckConta();       
        /*Si se presiona F11*/
        else if(evt.getKeyCode() == KeyEvent.VK_F11)
        {
            /*Marca o desmarca el checkbox de guardar datos del cliente*/
            if(jCGDats.isSelected())                            
                jCGDats.setSelected(false);            
            else            
            {
                /*Seleccionalo primeramente*/
                jCGDats.setSelected(true);                        
                
                /*Función para validar si puede o no modificar el cliente mostrador*/
                vClien();                        
            }
        } 
        /*Si se presiona F12 entonces*/
        else if(evt.getKeyCode() == KeyEvent.VK_F12)
        {
            /*Si esta marcado entonces*/
            if(jCFExt.isSelected())
            {
                /*Desmarca el checkbox y deshabilita el control de fecha*/
                jCFExt.setSelected(false);
                jDFExt.setEnabled(false);
            }
            else
            {
                /*Marca el checkbox y habilita el control de fecha*/
                jCFExt.setSelected(true);
                jDFExt.setEnabled(true);           
            }
        }
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCarSer;
    private javax.swing.JButton jBCatGral;
    private javax.swing.JButton jBCli;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBDom;
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
    private javax.swing.JButton jBUltCostT;
    private javax.swing.JButton jBUltPre;
    private javax.swing.JButton jBVeGran;
    private javax.swing.JCheckBox jCBack;
    private javax.swing.JCheckBox jCCo1;
    private javax.swing.JCheckBox jCCo2;
    private javax.swing.JCheckBox jCCo3;
    private javax.swing.JCheckBox jCConta;
    private javax.swing.JCheckBox jCFExt;
    private javax.swing.JCheckBox jCGDats;
    private javax.swing.JCheckBox jCGuaPag;
    private javax.swing.JCheckBox jCImp;
    private javax.swing.JCheckBox jCMand;
    private javax.swing.JCheckBox jCMostA;
    private javax.swing.JCheckBox jCRem;
    private javax.swing.JCheckBox jCTim;
    private javax.swing.JComboBox jComAlma;
    private javax.swing.JComboBox jComColo;
    private javax.swing.JComboBox jComFormPag;
    private javax.swing.JComboBox jComImp;
    private javax.swing.JComboBox jComMon;
    private javax.swing.JComboBox jComSer;
    private javax.swing.JComboBox jComTall;
    private javax.swing.JComboBox jComUnid;
    private com.toedter.calendar.JDateChooser jDBack;
    private com.toedter.calendar.JDateChooser jDFEnt;
    private com.toedter.calendar.JDateChooser jDFExt;
    private com.toedter.calendar.JDateChooser jDFech;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLCatGral;
    private javax.swing.JLabel jLImg;
    private javax.swing.JLabel jLNot;
    private javax.swing.JLabel jLTipVta;
    private javax.swing.JLabel jLTotSald;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
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
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPClien;
    private javax.swing.JPanel jPParts;
    private javax.swing.JPanel jPanImg;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRNoPag;
    private javax.swing.JRadioButton jRPagad;
    private javax.swing.JScrollPane jSImg;
    private javax.swing.JScrollPane jSObserv;
    private javax.swing.JScrollPane jScrTab;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTACatGral;
    private javax.swing.JTextArea jTAObserv;
    private javax.swing.JTextField jTAlma2;
    private javax.swing.JTextField jTCP;
    private javax.swing.JTextField jTCadu;
    private javax.swing.JTextField jTCall;
    private javax.swing.JTextField jTCant;
    private javax.swing.JTextField jTCantLot;
    private javax.swing.JTextField jTCarSer;
    private javax.swing.JTextField jTCiu;
    private javax.swing.JTextField jTCli;
    private javax.swing.JTextField jTCo1;
    private javax.swing.JTextField jTCo2;
    private javax.swing.JTextField jTCo3;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTComenSer;
    private javax.swing.JTextField jTCond;
    private javax.swing.JTextField jTCost;
    private javax.swing.JTextField jTCta;
    private javax.swing.JTextField jTDesc;
    private javax.swing.JTextField jTDescrip;
    private javax.swing.JTextField jTDescripAlma;
    private javax.swing.JTextField jTDescripColo;
    private javax.swing.JTextField jTDescripTall;
    private javax.swing.JTextField jTDescripUni;
    private javax.swing.JTextField jTDiaCre;
    private javax.swing.JTextField jTEsKit;
    private javax.swing.JTextField jTEstad;
    private javax.swing.JTextField jTExist;
    private javax.swing.JTextField jTGara;
    private javax.swing.JTextField jTId;
    private javax.swing.JTextField jTImp;
    private javax.swing.JTextField jTKit;
    private javax.swing.JTextField jTLimCred;
    private javax.swing.JTextField jTList;
    private javax.swing.JTextField jTListEmp;
    private javax.swing.JTextField jTLot;
    private javax.swing.JTextField jTMetPag;
    private javax.swing.JTextField jTNoExt;
    private javax.swing.JTextField jTNoInt;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTPai;
    private javax.swing.JTextField jTPedimen;
    private javax.swing.JTextField jTPre;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTRFC;
    private javax.swing.JTextField jTSaldDispo;
    private javax.swing.JTextField jTSer;
    private javax.swing.JTextField jTSerProd;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTTel;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTextField jTTotCost;
    private javax.swing.JTextField jTTotDesc;
    private javax.swing.JTextField jTTotSald;
    private javax.swing.JTextField jTValImp;
    private javax.swing.JTable jTab;
    private javax.swing.JTable jTabDir;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaEmpresa extends javax.swing.JFrame */
