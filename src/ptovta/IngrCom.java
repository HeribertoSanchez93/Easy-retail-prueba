//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import static ptovta.Princip.bIdle;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




/*Clase para controlar los pes*/
public class IngrCom extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private java.awt.Color  colOri;

    /*Contador para saber donde guardarel próximo fichero de la compra*/
    private int             iXGlo;
    
    /*Contiene la ruta de donde se leeran los archivos para guardarlos*/
    private String          sRuts[][];
    
    /*Contiene la dirección de la forma para ver la ruta de los ficheros en otra vista*/
    private FilVis          filVi;
    
    /*Contiene el texto del label*/
    private String          sLabTex;
    
    /*Almacena la ruta del logo*/
    private String          sRutLog;
    
    /*Declara variables de instancia*/    
    private JTable          jTableCompras;
    private int             iContFi;

    /*Contiene la dirección de la forma para ver imágen en otra vista*/
    private ImgVis          v;
    
    /*Contador para modificar tabla*/
    private int             iContCellEd;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;

    /*Contiene los datos de la empresa local*/
    private String          sNomLoc             = "";
    private String          sCallLoc            = "";
    private String          sTelLoc             = "";
    private String          sColLoc             = "";
    private String          sCPLoc              = "";
    private String          sCiuLoc             = "";
    private String          sEstLoc             = "";
    private String          sPaiLoc             = "";
    
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
    private String          sImpueValOri;    
    private String          sImpoOri;   
    private String          sTallOri;   
    private String          sColOri;   
    private String          sLotOri;   
    private String          sPedimenOri;   
    private String          sFCaduOri;   
    private String          sSerProdOri;   
    private String          sComenOri;   
    private String          sGaranOri;
    
    /*Bandera para que no se modifique la descripción del producto en la tabla*/
    private boolean         bModDescrip         = true;
    
    //Son para el conteo de productos y series
    private double iCantTot;
    private double iCantAgr;
    private String sCodPrevG;
    private String sCodi;
    
    /*Constructor sin argumentos*/
    public IngrCom(JTable jTableComp, javax.swing.JFrame jFrame,final String sCod,String sCodPrev,String sTipo) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        sCodi=sCod;
        System.out.println(sTipo);
        //Se bloque dependiendo del tipo que sea
        if(sTipo!="")
        {
            if(sTipo.compareTo("orden")==0)
            {
                jCOrd.setSelected(true);
                jLNot.setText("NUEVA ORDEN DE COMPRA");
            }
            jCOrd.setEnabled(false);
                
        }
        
        //Se toma si es un previo de compra
        sCodPrevG=sCodPrev;
        
        /*Inicia el contador de archivos del arreglo en 0 y el arreglo en nulo*/        
        iXGlo   = 0;
        sRuts   = null;
        
        //Se pone no visibles los campos auxiliares
        jTSerProd.setVisible (false);
        jTComenSer.setVisible(false);
        
        //Esconde la columna del importe del impuesto
        jTab.getColumnModel().getColumn(20).setMinWidth(0);
        jTab.getColumnModel().getColumn(20).setMaxWidth(0);
        
        /*Esconde el campo del total de saldo a pagar*/
        jTTotSald.setVisible(false);
        
        /*Que no sea visible el label de tipo de venta*/
        jLTipVta.setVisible(false);
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el tamaño de las columnas de la tabla*/
        jTab.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(5).setPreferredWidth(500);
        jTab.getColumnModel().getColumn(8).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(10).setPreferredWidth(120);
        jTab.getColumnModel().getColumn(16).setPreferredWidth(120);
        jTab.getColumnModel().getColumn(17).setPreferredWidth(190);
        jTab.getColumnModel().getColumn(18).setPreferredWidth(230);
        jTab.getColumnModel().getColumn(19).setPreferredWidth(230);
        
        /*Escoonde el control del almacén 2*/
        jTAlma2.setVisible(false);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Inicialmente es una compra no una órden de compra*/
        vOrdCtrl(false);
        
        /*Selecciona la fecha del día de hoy para la posible de entrega*/
        java.util.Date f = new java.util.Date();
        jDFEnt.setDate(f);  

        /*Selecciona la fecha del día de hoy para la fecha*/        
        jDFech.setDate(f);        
        
        /*Selecciona una fecha inicialmente para la fecha de caducidad*/        
        jDFCadu.setDate(f);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Incializa la tabla del formulario de comprs*/
        jTableCompras               = jTableComp;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Inicia el contador de filas de las partidas*/
        iContFi                    = 1;
             
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
        
        /*Listener para el combobox de series de compras*/
        jComSer.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                /*Carga las series en el combobox*/
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
        
        /*Listener para el combobox de tallas*/
        jComTall.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos        
                Connection  con = Star.conAbrBas(true, false);  

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                //Carga todas las tallas en el combo
                if(Star.iCargTallCom(con, jComTall)==-1)
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
        
        /*Listener para el combobox de colores*/
        jComColo.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos        
                Connection  con = Star.conAbrBas(true, false);  

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                //Trae todos los colores y cargalos en el combo
                if(Star.iCargColoCom(con, jComColo)==-1)
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
                
                /*Trae todos los códigos de las monedas de la base de datos*/
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
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Compra, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);                        
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
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
        
        //Carga las formas de pago en el combo
        if(Star.iCargFormPagCom(con, jComFormPag)==-1)            
            return;
        
        //Carga todos los almacenes en el combo
        if(Star.iCargAlmaCom(con, jComAlma)==-1)
            return;

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Comprueba si se puede o no modificar la descripción del producto*/
        try
        {                  
            sQ = "SELECT val FROM confgral WHERE clasif = 'comps' AND conf = 'moddescrip'";
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
            sQ = "SELECT val FROM confgral WHERE clasif = 'comps' AND conf = 'modprec'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/                
            if(rs.next())
            {
                /*Si se no se puede modificar el precio entonces deshabilita el campo*/
                if(rs.getString("val").compareTo("0")==0)
                    jTCost.setEditable(false);                                                                      
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
        
        /*Obtiene la ruta completa hacia el logo*/
        sRutLog                 = sCarp + "\\imgs\\Logotipo Empresa\\" + Login.sCodEmpBD + "\\Logo.jpg";

        /*Si no existe un logo para la empresa entonces será el logo por default del sistema*/
        if(!new File(sRutLog).exists())
            sRutLog             = getClass().getResource(Star.sIconDef).toString();
                
        /*Obtiene todos los datos de la cliente local*/
        try
        {                  
            sQ = "SELECT nom, calle, tel, col, cp, ciu, estad, pai FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                
                sNomLoc             = rs.getString("nom");                                    
                sCallLoc            = rs.getString("calle");                                    
                sTelLoc             = rs.getString("tel");                                    
                sColLoc             = rs.getString("col");                                    
                sCPLoc              = rs.getString("cp");                                    
                sCiuLoc             = rs.getString("ciu");                                    
                sEstLoc             = rs.getString("estad");                                    
                sPaiLoc             = rs.getString("pai");                                                                                                                    
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }
        
        /*Agrega el elemento vacio en el combobox de series*/
        jComSer.addItem("");
        
        /*Crea la condición para las órdenes de compra*/
        String sConO    = " tip = 'COMP'";
        if(jCOrd.isSelected())
            sConO       = " tip = 'ORDC'";

        /*Obtiene todas las series actualizadas y cargalas en el combobox*/
        try
        {
            sQ = "SELECT descrip FROM consecs WHERE " + sConO;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            while(rs.next())
                jComSer.addItem(rs.getString("descrip"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }  
                
        /*Declara variables*/
        String sCodCom;
        
        /*Mientras el código de la nueva compra este repetido seguirá obteniendo uno que no este repetido*/
        boolean bSi;
        do
        {
            /*Inicialmente no existe el código de la compra*/
            bSi = false;
            
            /*Obtiene el código de la nueva compra*/
            sCodCom                 = Star.sGenClavDia();
            
            /*Obtiene si ya existe ese código de compra en la base de datos*/
            try
            {                  
                sQ = "SELECT codcomp FROM comprs WHERE codcomp = '" + sCodCom + "' AND tip = 'COMP'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces coloca la bandera*/
                if(rs.next())
                    bSi = true;                    
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                       
            }
            
        }while(bSi);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Pon el foco del teclado en el campo del proveedor*/
        jTProv.grabFocus();                
        
        //Obtiene todas las unidades y cargalas en el combo
        if(Star.iCargUnidCom(con, jComUnid)==-1)
            return;

        //Carga todos los impuestos en el combo
        if(Star.iCargImpueCom(con, jComImp)==-1)
            return;
        
        /*Trae todos los códigos de las monedas de la base de datos*/
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
        
        //Carga todas las tallas en el combo
        if(Star.iCargTallCom(con, jComTall)==-1)
            return;
        
        //Trae todos los colores y cargalos en el combo
        if(Star.iCargColoCom(con, jComColo)==-1)
            return;
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para la tabla de partidas cuando se modifique algo*/
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
                    /*Declara variables de bloque*/
                    String sCant;                                        
                    String sDescrip;
                    String sImp;
                    String sDesc;
                    String sDescConv;
                    String sDescAd;
                    String sDescAdConv;
                    String sCost;                    
                                       
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
                        sImpueValOri    = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();                        
                        sImpoOri        = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        sTallOri        = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();
                        sColOri         = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();
                        sLotOri         = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();
                        sPedimenOri     = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();
                        sFCaduOri       = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();
                        sSerProdOri     = jTab.getValueAt(jTab.getSelectedRow(), 17).toString();
                        sComenOri       = jTab.getValueAt(jTab.getSelectedRow(), 18).toString();
                        sGaranOri       = jTab.getValueAt(jTab.getSelectedRow(), 19).toString();

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
                        jTab.setValueAt(sUnidOri,       jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sAlmaOri,       jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sImpueOri,      jTab.getSelectedRow(), 9);                                                
                        jTab.setValueAt(sImpueValOri,   jTab.getSelectedRow(), 10);
                        jTab.setValueAt(sImpoOri,       jTab.getSelectedRow(), 11);                        
                        jTab.setValueAt(sTallOri,       jTab.getSelectedRow(), 12);                        
                        jTab.setValueAt(sColOri,        jTab.getSelectedRow(), 13);                        
                        jTab.setValueAt(sLotOri,        jTab.getSelectedRow(), 14);                        
                        jTab.setValueAt(sPedimenOri,    jTab.getSelectedRow(), 15);                        
                        jTab.setValueAt(sFCaduOri,      jTab.getSelectedRow(), 16);                        
                        jTab.setValueAt(sSerProdOri,    jTab.getSelectedRow(), 17);                        
                        jTab.setValueAt(sComenOri,      jTab.getSelectedRow(), 18);
                        jTab.setValueAt(sGaranOri,      jTab.getSelectedRow(), 19);
                       
                        /*Si no se tiene que modificar la descripción entonces colocala de nuevo*/
                        if(!bModDescrip)
                            jTab.setValueAt(sDescripOri,jTab.getSelectedRow(), 5);
                        
                        /*Obtén la cantidad que el usuario ingreso*/
                        sCant                   = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                       
                        
                        /*Si la descripción es cadena vacia entonces coloca la que tenía originalmente*/
                        if(jTab.getValueAt(jTab.getSelectedRow(), 5).toString().compareTo("")==0)                            
                            jTab.setValueAt(sDescripOri, jTab.getSelectedRow(), 5);

                        /*Si el texto introducido no es númerico para la cantidad entonces*/
                        try
                        {
                            Double.parseDouble(sCant);
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca la cantidad original*/
                            jTab.setValueAt(sCantOri, jTab.getSelectedRow(), 2);

                            /*Recalcula los totes leyendo toda la tabla de partidas y regresa*/
                            vRecalTot();                            
                            return;
                        }          

                        /*Convierte a valor absoluto el número introducido, para quitar el negativo en caso de que lo tenga*/
                        sCant                   = Integer.toString((int)Math.abs(Double.parseDouble(sCant)));                    

                        /*Vuelve a colocar el valor en la cantidad de la fila con el valor convertido a valor positivo*/
                        jTab.setValueAt(sCant, jTab.getSelectedRow(), 2);

                        /*Si la cantidad es 0 entonces*/
                        if(Double.parseDouble(sCant)<=0)                    
                        {
                            /*Coloca uno en la columna*/
                            jTab.setValueAt("1", jTab.getSelectedRow(), 2);                                            
                            
                            /*La cantidad será igual a 1*/
                            sCant               = "1";
                        }
                        
                        /*Si la cantidad original era 0 entonces*/
                        if(Integer.parseInt(sCantOri)==0)
                        {
                            /*Coloca 0 en la cantidad y regresa*/                            
                            jTab.setValueAt("0", jTab.getSelectedRow(), 2);                                                                        
                            return;
                        }
                        
                        /*Obtén el costo de la fila*/
                        sCost                       = jTab.getValueAt(jTab.getSelectedRow(), 6).toString().replace("$", "").replace(",", "");
                                                
                        /*Si el costo no es un número entonces*/
                        try
                        {
                            Double.parseDouble(sCost);
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca el descuento original que tenía*/
                            jTab.setValueAt(sCostOri, jTab.getSelectedRow(), 6);                                            
                            
                            /*Recalcula los totales leyendo toda la tabla de partidas y regresa*/
                            vRecalTot();                            
                            return;
                        }

                        /*Convierte a valor absoluto el costo, para quitar el negativo en caso de que lo tenga*/
                        sCost                       = Double.toString((double)Math.abs(Double.parseDouble(sCost)));                    
                        
                        /*Lee el descuento*/
                        sDesc                       = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        
                        /*Si el descuento no es un número entonces*/
                        try
                        {
                            Double.parseDouble(sDesc);
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca el descuento original que tenía*/
                            jTab.setValueAt(sDescOri, jTab.getSelectedRow(), 7);                                            
                            
                            /*Recalcula los totes leyendo toda la tabla de partidas y regresa*/
                            vRecalTot();                            
                            return;
                        }                        
                        
                        /*Convierte a valor absoluto el descuento, para quitar el negativo en caso de que lo tenga*/
                        sDesc                       = Integer.toString((int)Math.abs(Integer.parseInt(sDesc)));                    
                        
                        /*Si el descuento no es cadena vacia entonces obtiene el descuento*/
                        if(sDesc.compareTo("")!= 0)                        
                            sDescConv    = Double.toString((Double.parseDouble(sDesc) / 100 ) * Double.parseDouble(sCost));                                    
                        /*Else, colocalo en 0 para poder hacer la resta*/
                        else
                            sDescConv    = "0";

                        /*Lee el descuento adicional*/
                        sDescAd              = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();

                        /*Si el descunto adicional no es un número entonces*/
                        try
                        {
                            Double.parseDouble(sDescAd);
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca el descuento original que tenía*/
                            jTab.setValueAt(sDescAdOri, jTab.getSelectedRow(), 8);                                            
                            
                            /*Recalcula los totes leyendo toda la tabla de partidas y regresa*/
                            vRecalTot();                            
                            return;
                        }                        
                        
                        /*Convierte a valor absoluto el descuento adicional, para quitar el negativo en caso de que lo tenga*/
                        sDescAd                       = Integer.toString((int)Math.abs(Integer.parseInt(sDescAd)));                    
                                                
                        /*Si el descuento adicional no es cadena vacia entonces calculalo*/
                        if(sDescAd.compareTo("")!= 0)                        
                            sDescAdConv= Double.toString((Double.parseDouble(sDescAd) / 100 ) * (Double.parseDouble(sCost) - Double.parseDouble(sDescConv)));                        
                        /*Else, colocalo en 0 para poder hacer la resta*/
                        else
                            sDescAdConv= "0";

                        /*Genera el importe*/
                        sImp                        = Double.toString(Double.parseDouble(sCant) * (Double.parseDouble(sCost) - Double.parseDouble(sDescConv) - Double.parseDouble(sDescAdConv)));

                        //Crea el importe del impuesto
                        String sImpoImpue           = Double.toString(Double.parseDouble(sImp) * (Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 10).toString().trim()) / 100));
                        
                        //Coloca el importe del impuesto en la fila                        
                        jTab.setValueAt(sImpoImpue, jTab.getSelectedRow(), 20);                                            
                        
                        /*Dale formato de moneda al importe*/
                        double dCant                = Double.parseDouble(sImp);                   
                        java.text.NumberFormat n    = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
                        sImp                        = n.format(dCant);

                        /*Dale formato de moneda al cost*/
                        dCant                       = Double.parseDouble(sCost);                   
                        n                           = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
                        sCost                       = n.format(dCant);

                        /*Actualiza el importe de la fila en base a la cantidad por el costo*/
                        jTab.setValueAt(sImp, jTab.getSelectedRow(), 11);

                        /*Actualiza el costo de la fila ya con formato de moneda*/
                        jTab.setValueAt(sCost, jTab.getSelectedRow(), 6);

                        /*Recalcula los totes leyendo toda la tabla de partidas*/
                        vRecalTot(); 
                        
                    }/*Fin de else*/                                                                   
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla de partidas*/
        jTab.addPropertyChangeListener(pr);        
        
        /*Si la compra no es nula entonces carga todos los controles con los datos de esa compra*/
        if(sCod!=null)
            vCargT(con, sCod);
        
        /*Si el previo de compra no es nula entonces carga todos los controles con los datos de esa compra*/
        if(sCodPrev!=null)
            vCargPrev(con, sCodPrev);
        
        /*Comprueba si debe iniciar con órden de compra o no*/
        if(sTipo=="")
        {
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'comps' AND conf = 'iniord'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si tiene que ser inicialmente órden de compra entonces*/
                if(rs.getInt("val")==1)
                {
                    /*Marca el check de órden de compra*/
                    jCOrd.setSelected(true);
                    
                    /*Haz invisible todo lo de órden de compra*/
                    vOrdCtrl(true);

                    /*Coloca el label de nueva compra*/
                    jLNot.setText("NUEVA ORDEN DE COMPRA");            

                    /*Esconde el label de tipo de compra*/
                    jLTipVta.setVisible(false);
                }
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }
        }
        //Cierra la base de datos
        Star.iCierrBas(con);
                
    }/*Fin de public IngrCom() */
        
    /*Función para cargar todos los controles de la compra en la forma*/
    private void vCargPrev(Connection con, String sCodPrev)
    {
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ              = ""; 
        
        
        
        
        /*Obtiene todo el encabezado de la compra*/
        String sNoSer   = "";
        try
        {
            sQ = "SELECT prevcomprs.MON, provs.DIACRED, provs.LIMCRED, prevcomprs.NOSER, provs.CO1, provs.CO2, provs.CO3, prevcomprs.NODOC, provs.SER, provs.NOM, prevcomprs.TOT, prevcomprs.SUBTOT, prevcomprs.IMPUE, prevcomprs.PROV FROM prevcomprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = prevcomprs.PROV WHERE codprevcomp='" + sCodPrev + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Función para mostrar las condiciones en las que estamos con el proveedor*/
                vCondP(con, rs.getString("limcred"), rs.getString("diacred"));
                
                /*Obtiene los totales*/
                String sTot         = rs.getString("prevcomprs.TOT");
                String sSubTot      = rs.getString("prevcomprs.SUBTOT");                
                String sImpue       = rs.getString("prevcomprs.IMPUE");                
                
                /*Dale formato de moneda a los totales*/                
                NumberFormat n      = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant        = Double.parseDouble(sSubTot);                
                sSubTot             = n.format(dCant);
                dCant               = Double.parseDouble(sImpue);                
                sImpue              = n.format(dCant);
                dCant               = Double.parseDouble(sTot);                
                sTot                = n.format(dCant);
                
                /*Coloca la moneda en su lugar*/
                jComMon.setSelectedItem(rs.getString("mon"));
                
                /*Coloca los totales en el control*/
                jTTot.setText       (sTot);
                jTImp.setText       (sImpue);
                jTSubTot.setText    (sSubTot);
                
                /*Obtiene los datos y colocalos en los controles*/                
                jTProv.setText      (rs.getString("prevcomprs.PROV"));
                jTNom.setText   (rs.getString("provs.NOM"));
                jTSer.setText       (rs.getString("provs.SER"));
                jTNoDoc.setText     (rs.getString("prevcomprs.NODOC"));
                jTCo1.setText       (rs.getString("provs.CO1"));
                jTCo2.setText       (rs.getString("provs.CO2"));
                jTCo3.setText       (rs.getString("provs.CO3"));
                
                /*Obtiene la serie de la compra*/
                sNoSer              = rs.getString("prevcomprs.NOSER");                
            }
        }
        catch(SQLException e)
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
        
        /*Obtiene todas las partidas de la compra*/
        try
        {
            sQ = "SELECT * FROM partprevcomprs WHERE codcom = '" + sCodPrev + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())          
            {
                /*Obtiene los totales*/
                String sCost     = rs.getString("cost");
                String sImpo     = rs.getString("impo");                
                String sValImp   = Star.sGetValImp(rs.getString("codimpue"));
                if(sValImp.compareTo("")==0)
                    sValImp="0";
                String sImpImpue = Double.toString(Double.parseDouble(sImpo)*Double.parseDouble(sValImp)/100);
                /*Dale formato de moneda a los totales*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sCost);                
                sCost           = n.format(dCant);
                dCant           = Double.parseDouble(sImpo);                
                sImpo           = n.format(dCant);
                
                /*Agrega el registro en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("prod"), rs.getString("cant"), rs.getString("unid"), rs.getString("alma"), rs.getString("descrip"), sCost, rs.getString("descu"), rs.getString("descad"), rs.getString("codimpue"), sValImp , sImpo, rs.getString("tall"), rs.getString("colo"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("flotvenc"), rs.getString("serprod"), rs.getString("comenser"), rs.getString("garan"),sImpImpue};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas*/
                ++iContFi;
            }                
        }
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            //Agrega en el log
            Login.vLog(e.getMessage());            	    
            
            //Mensajea y regresa
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));         
            return;
        }
        
        /*Pon el foco del teclado en el campo del botón de búscar proveedor*/
        jBProv.grabFocus();
        
    }/*Fin de private void vCargT(Connection con)*/
    
    
    /*Función para cargar todos los controles de la compra en la forma*/
    private void vCargT(Connection con, String sCod)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        
        
        
        /*Obtiene todo el encabezado de la compra*/
        String sNoSer   = "";
        try
        {
            sQ = "SELECT comprs.MON, provs.DIACRED, provs.LIMCRED, comprs.NOSER, provs.CO1, provs.CO2, provs.CO3, comprs.NODOC, provs.SER, provs.NOM, comprs.TOT, comprs.SUBTOT, comprs.IMPUE, comprs.PROV FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = comprs.PROV WHERE codcomp = '" + sCod + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Función para mostrar las condiciones en las que estamos con el proveedor*/
                vCondP(con, rs.getString("limcred"), rs.getString("diacred"));
                
                /*Obtiene los totales*/
                String sTot         = rs.getString("comprs.TOT");
                String sSubTot      = rs.getString("comprs.SUBTOT");                
                String sImpue       = rs.getString("comprs.IMPUE");                
                
                /*Dale formato de moneda a los totales*/                
                NumberFormat n      = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant        = Double.parseDouble(sSubTot);                
                sSubTot             = n.format(dCant);
                dCant               = Double.parseDouble(sImpue);                
                sImpue              = n.format(dCant);
                dCant               = Double.parseDouble(sTot);                
                sTot                = n.format(dCant);
                
                /*Coloca la moneda en su lugar*/
                jComMon.setSelectedItem(rs.getString("mon"));
                
                /*Coloca los totales en el control*/
                jTTot.setText       (sTot);
                jTImp.setText       (sImpue);
                jTSubTot.setText    (sSubTot);
                
                /*Obtiene los datos y colocalos en los controles*/                
                jTProv.setText      (rs.getString("comprs.PROV"));
                jTNom.setText   (rs.getString("provs.NOM"));
                jTSer.setText       (rs.getString("provs.SER"));
                jTNoDoc.setText     (rs.getString("comprs.NODOC"));
                jTCo1.setText       (rs.getString("provs.CO1"));
                jTCo2.setText       (rs.getString("provs.CO2"));
                jTCo3.setText       (rs.getString("provs.CO3"));
                
                /*Obtiene la serie de la compra*/
                sNoSer              = rs.getString("comprs.NOSER");                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }
        
        /*Obtiene la descripción de la serie de la compra*/
        try
        {
            sQ = "SELECT descrip FROM consecs WHERE (tip = 'COMP' OR tip = 'ORDC') AND ser = '" + sNoSer + "'";	            
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
        
        /*Obtiene todas las partidas de la compra*/
        try
        {
            sQ = "SELECT * FROM partcomprs WHERE codcom = '" + sCod + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())          
            {
                /*Obtiene los totales*/
                String sCost     = rs.getString("cost");
                String sImpo     = rs.getString("impo");                
                String sValImp   = Star.sGetValImp(rs.getString("codimpue"));
                if(sValImp.compareTo("")==0)
                    sValImp="0";
                String sImpImpue = Double.toString(Double.parseDouble(sImpo)*Double.parseDouble(sValImp)/100);
                /*Dale formato de moneda a los totales*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sCost);                
                sCost           = n.format(dCant);
                dCant           = Double.parseDouble(sImpo);                
                sImpo           = n.format(dCant);
                
                /*Agrega el registro en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("prod"), rs.getString("cant"), rs.getString("unid"), rs.getString("alma"), rs.getString("descrip"), sCost, rs.getString("descu"), rs.getString("descad"), rs.getString("codimpue"), sValImp , sImpo, rs.getString("tall"), rs.getString("colo"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("flotvenc"), rs.getString("serprod"), rs.getString("comenser"), rs.getString("garan"),sImpImpue};
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
        
        /*Pon el foco del teclado en el campo del botón de búscar proveedor*/
        jBProv.grabFocus();
        
    }/*Fin de private void vCargT(Connection con)*/
    
    
    /*Función para recalcular los totales*/
    private void vRecalTot()
    {
        //Declara variables locales
        String                  sSubTot     = "0";
        String                  sImp        = "0";
        String                  sTot; 
        String                  sNot        = "0";
        String                  sDesc       = "0";
        
        
        
        /*Si la tabla no tiene elementos entonces*/
        if(jTab.getRowCount()== 0 )
        {
            /*Colocar en los campos de subtot, impue y tot $0.00*/
            jTSubTot.setText            ("$0.00");
            jTImp.setText               ("$0.00");
            jTTot.setText               ("$0.00");
            jTTotSald.setText           ("$0.00");
            jDesc.setText               ("$0.00");
            
            /*Regresa*/
            return;
        }
        
        /*Recalcula los importes de todas las partidas de la tabla*/
        for( int row = 0;  row < jTab.getRowCount(); row++)
        {                               
            /*Si la partida es una nota de crédito entonces*/
            if(jTab.getValueAt(row, 2).toString().compareTo("0")==0)
            {                                   
                /*Ve sumando el total de nota de crédito y continua*/
                sNot                    = Double.toString(Double.parseDouble(sNot) + Double.parseDouble(jTab.getValueAt(row, 11).toString().replace("$", "").replace(",", "")));                
                continue;
            }

            /*Si es una nota de crédito el impuesto brincalo*/
            if(jTab.getValueAt(row, 2).toString().compareTo("0")==0)
                continue;

            /*Lee el importe de la fila*/
            String sImp2                = Double.toString(Double.parseDouble(jTab.getValueAt(row, 2).toString().replace("$", "").replace(",", ""))*Double.parseDouble(jTab.getValueAt(row, 6).toString().replace("$", "").replace(",", "")));
            
            /*Suma el importe de la fila al subtotal*/
            sSubTot                     = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImp2));
            
            //se saca el descuento
            double dDesc                = Double.parseDouble(sImp2)*Double.parseDouble(jTab.getValueAt(row, 7).toString().replace("$", "").replace(",", ""))/100;
                
            //se saca el impuesto adicional
            dDesc                       = dDesc+((Double.parseDouble(sImp2)-dDesc)*Double.parseDouble(jTab.getValueAt(row, 8).toString().replace("$", "").replace(",", ""))/100);
           
            //Ve sumando el descuento por producto
            sDesc                       = Double.toString(Double.parseDouble(sDesc)+dDesc);
            
            //Ve sumando el impuesto de la fila
            sImp                        = Double.toString(Double.parseDouble(sImp) + Double.parseDouble(jTab.getValueAt(row, 20).toString()));
        }
          
        //Obtiene el total
        sTot                            = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImp)-Double.parseDouble(sDesc));
                        
        /*Si hay saldo de nota de crédito entonces*/
        if(Double.parseDouble(sNot)>0)
        {
            /*Muestra el label de total a pagar y el campo igual*/
            jLTotSald.setVisible(true);
            jTTotSald.setVisible(true);
            
            /*Crea el saldo correcto que es el total menos el saldo de la nota de crédito*/
            String sSaldTot     = Double.toString(Double.parseDouble(sTot) - Double.parseDouble(sNot));

            /*Formatea el saldo a moneda*/            
            NumberFormat n      = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
            double dCant        = Double.parseDouble(sSaldTot);        
            sSaldTot            = n.format(dCant);

            /*Colocalo en su lugar*/
            jTTotSald.setText(sSaldTot);                        
        }
        
        /*Formatea a moneda el subtotal*/
        double dCant                    = Double.parseDouble(sSubTot);
        NumberFormat n                  = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        sSubTot                         = n.format(dCant);
        
        /*Formatear el impuesto a moneda*/
        dCant                           = Double.parseDouble(sImp);
        n                               = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        sImp                            = n.format(dCant);
        
        /*Formatear el total a moneda*/
        dCant                           = Double.parseDouble(sTot);
        n                               = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        sTot                            = n.format(dCant);
        
        /*Formatear el total a descuento*/
        dCant                           = Double.parseDouble(sDesc);
        n                               = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        sDesc                            = n.format(dCant);
        
        /*Colocalos en sus campos correspondientes*/
        jTSubTot.setText    (sSubTot);
        jTImp.setText       (sImp);
        jTTot.setText       (sTot);        
        jTSubTot.setText    (sSubTot); 
        jDesc.setText       (sDesc);
               
    }/*Fin de private void vRecalTot()*/
            
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBGuar = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTDesc = new javax.swing.JTextField();
        jComUnid = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jTDescrip = new javax.swing.JTextField();
        jTCost = new javax.swing.JTextField();
        jTCant = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTDescAd = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jComImp = new javax.swing.JComboBox();
        jTValImp = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTDescripUnid = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTCodOpl2 = new javax.swing.JTextField();
        jTCodOp1 = new javax.swing.JTextField();
        jTExist = new javax.swing.JTextField();
        jBProd = new javax.swing.JButton();
        jTProd = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTCodProv1 = new javax.swing.JTextField();
        jTCodProv2 = new javax.swing.JTextField();
        jSImg = new javax.swing.JScrollPane();
        jPanImg = new javax.swing.JPanel();
        jLImg = new javax.swing.JLabel();
        jBVeGran = new javax.swing.JButton();
        jBUltPre = new javax.swing.JButton();
        jComTall = new javax.swing.JComboBox();
        jLabel34 = new javax.swing.JLabel();
        jComColo = new javax.swing.JComboBox();
        jLabel35 = new javax.swing.JLabel();
        jTDescripColo = new javax.swing.JTextField();
        jTDescripTall = new javax.swing.JTextField();
        jTLot = new javax.swing.JTextField();
        jTPedimen = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jDFCadu = new com.toedter.calendar.JDateChooser();
        jBGranDescrip = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTGaran = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jBUltCostT = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jComAlma = new javax.swing.JComboBox();
        jTDescripAlma = new javax.swing.JTextField();
        jBExisAlma = new javax.swing.JButton();
        jBCarg = new javax.swing.JButton();
        jBLimp = new javax.swing.JButton();
        jBTipCam = new javax.swing.JButton();
        jTCarSer = new javax.swing.JTextField();
        jBCarSer = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTNoDoc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTNom = new javax.swing.JTextField();
        jTProv = new javax.swing.JTextField();
        jBProv = new javax.swing.JButton();
        jTCond = new javax.swing.JTextField();
        jCConta = new javax.swing.JCheckBox();
        jTSer = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jCCo1 = new javax.swing.JCheckBox();
        jTCo1 = new javax.swing.JTextField();
        jCCo2 = new javax.swing.JCheckBox();
        jTCo2 = new javax.swing.JTextField();
        jCCo3 = new javax.swing.JCheckBox();
        jTCo3 = new javax.swing.JTextField();
        jCGua = new javax.swing.JCheckBox();
        jLGua = new javax.swing.JLabel();
        jLGua1 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jTMetPag = new javax.swing.JTextField();
        jTCta = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComFormPag = new javax.swing.JComboBox();
        jTSubTot = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTImp = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTTot = new javax.swing.JTextField();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jPEnca = new javax.swing.JPanel();
        jLEnt = new javax.swing.JLabel();
        jDFEnt = new com.toedter.calendar.JDateChooser();
        jCOrd = new javax.swing.JCheckBox();
        jLabel31 = new javax.swing.JLabel();
        jCImp = new javax.swing.JCheckBox();
        jLabel32 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jComSer = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        jDFech = new com.toedter.calendar.JDateChooser();
        jComMon = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jTAlma2 = new javax.swing.JTextField();
        jTComenSer = new javax.swing.JTextField();
        jLNot = new javax.swing.JLabel();
        jLTipVta = new javax.swing.JLabel();
        jLTotSald = new javax.swing.JLabel();
        jTTotSald = new javax.swing.JTextField();
        jDesc = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jTSerProd = new javax.swing.JTextField();
        label1 = new java.awt.Label();

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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 120, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jTProv);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 550, 120, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Producto", "Qty.", "Unidad", "Almacén", "Descripción", "Costo", "Descuento", "Desc. Adicional", "Impuesto", "Impuesto valor", "Importe", "Talla", "Color", "Lote", "Pedimento", "Fecha Caducidad", "Serie Producto", "Comentario Serie", "Garantía", "Importe impuesto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 1090, 160));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del Producto", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel2KeyPressed(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setText("Costo:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 100, -1));

        jLabel11.setText("Descuento%:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 100, -1));

        jTDesc.setText("0");
        jTDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDesc.setNextFocusableComponent(jBUltCostT);
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
        jPanel2.add(jTDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 140, 20));

        jComUnid.setName(""); // NOI18N
        jComUnid.setNextFocusableComponent(jTDescripUnid);
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
        jPanel2.add(jComUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 120, 20));

        jLabel12.setText("Cod. unidad:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 87, -1));

        jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescrip.setNextFocusableComponent(jTDesc);
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
        jPanel2.add(jTDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 280, 20));

        jTCost.setText("$0.00");
        jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCost.setNextFocusableComponent(jComImp);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCostKeyTyped(evt);
            }
        });
        jPanel2.add(jTCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 120, 20));

        jTCant.setText("1");
        jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCant.setNextFocusableComponent(jTDescAd);
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
        });
        jPanel2.add(jTCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 100, 90, 20));

        jLabel15.setText("Color:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 80, 100, -1));

        jLabel16.setText("Desc. adicional%:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, 110, -1));

        jTDescAd.setText("0");
        jTDescAd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescAd.setNextFocusableComponent(jTLot);
        jTDescAd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescAdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescAdFocusLost(evt);
            }
        });
        jTDescAd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescAdKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDescAdKeyTyped(evt);
            }
        });
        jPanel2.add(jTDescAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 120, 90, 20));

        jLabel17.setText("Cod. impuesto:");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 110, -1));

        jComImp.setNextFocusableComponent(jTValImp);
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
        jPanel2.add(jComImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 120, 20));

        jTValImp.setEditable(false);
        jTValImp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTValImp.setNextFocusableComponent(jBCarg);
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
        jPanel2.add(jTValImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 140, 20));

        jLabel19.setText("Cod.producto:");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 110, -1));

        jTDescripUnid.setEditable(false);
        jTDescripUnid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripUnid.setNextFocusableComponent(jTCost);
        jTDescripUnid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripUnidFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripUnidFocusLost(evt);
            }
        });
        jTDescripUnid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripUnidKeyPressed(evt);
            }
        });
        jPanel2.add(jTDescripUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 140, 20));

        jLabel5.setText("Cod. Opcional 2:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 110, -1));

        jLabel23.setText("Cod.proveedor:");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 110, -1));

        jTCodOpl2.setEditable(false);
        jTCodOpl2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodOpl2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodOpl2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodOpl2FocusLost(evt);
            }
        });
        jTCodOpl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodOpl2KeyPressed(evt);
            }
        });
        jPanel2.add(jTCodOpl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 140, 20));

        jTCodOp1.setEditable(false);
        jTCodOp1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        });
        jPanel2.add(jTCodOp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 120, 20));

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
        jPanel2.add(jTExist, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 90, 20));

        jBProd.setBackground(new java.awt.Color(255, 255, 255));
        jBProd.setText("...");
        jBProd.setToolTipText("Buscar Producto(s)");
        jBProd.setNextFocusableComponent(jComUnid);
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
        jPanel2.add(jBProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 30, 20));

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
        });
        jPanel2.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 90, 20));

        jLabel24.setText("Cod. opcional 1:");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 110, -1));

        jLabel25.setText("Cod.proveedor:");
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 110, -1));

        jTCodProv1.setEditable(false);
        jTCodProv1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodProv1.setNextFocusableComponent(jComAlma);
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
        });
        jPanel2.add(jTCodProv1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 120, 20));

        jTCodProv2.setEditable(false);
        jTCodProv2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCodProv2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodProv2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodProv2FocusLost(evt);
            }
        });
        jTCodProv2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodProv2KeyPressed(evt);
            }
        });
        jPanel2.add(jTCodProv2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 140, 20));

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
                .addGap(0, 171, Short.MAX_VALUE))
        );
        jPanImgLayout.setVerticalGroup(
            jPanImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanImgLayout.createSequentialGroup()
                .addComponent(jLImg)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        jSImg.setViewportView(jPanImg);

        jPanel2.add(jSImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, 190, 110));

        jBVeGran.setBackground(new java.awt.Color(255, 255, 255));
        jBVeGran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBVeGran.setToolTipText("Ver Imágen  de Producto Completa");
        jBVeGran.setNextFocusableComponent(jBCarSer);
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
        jPanel2.add(jBVeGran, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 130, 30, 20));

        jBUltPre.setBackground(new java.awt.Color(255, 255, 255));
        jBUltPre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ultpre.png"))); // NOI18N
        jBUltPre.setToolTipText("Ultimos 100 Precios de Compra de este Proveedor");
        jBUltPre.setNextFocusableComponent(jTGaran);
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
        jPanel2.add(jBUltPre, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 140, 40, 40));

        jComTall.setNextFocusableComponent(jComColo);
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
        jPanel2.add(jComTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, 90, 20));

        jLabel34.setText("Garantía:");
        jPanel2.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, 100, -1));

        jComColo.setNextFocusableComponent(jTCant);
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
        jPanel2.add(jComColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 80, 90, 20));

        jLabel35.setText("Talla:");
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 100, -1));

        jTDescripColo.setEditable(false);
        jTDescripColo.setToolTipText("Descripción de Color");
        jTDescripColo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jPanel2.add(jTDescripColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 80, 100, 20));

        jTDescripTall.setEditable(false);
        jTDescripTall.setToolTipText("Descripción de Talla");
        jTDescripTall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jPanel2.add(jTDescripTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 60, 100, 20));

        jTLot.setToolTipText("Lote");
        jTLot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLot.setNextFocusableComponent(jTPedimen);
        jTLot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLotFocusLost(evt);
            }
        });
        jTLot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLotKeyPressed(evt);
            }
        });
        jPanel2.add(jTLot, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 140, 90, 20));

        jTPedimen.setToolTipText("Pedimento");
        jTPedimen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPedimen.setNextFocusableComponent(jDFCadu);
        jTPedimen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPedimenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPedimenFocusLost(evt);
            }
        });
        jTPedimen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPedimenKeyPressed(evt);
            }
        });
        jPanel2.add(jTPedimen, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 140, 100, 20));

        jLabel37.setText("Fecha caducidad:");
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 110, -1));

        jDFCadu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jDFCadu.setNextFocusableComponent(jBVeGran);
        jDFCadu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFCaduKeyPressed(evt);
            }
        });
        jPanel2.add(jDFCadu, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 160, 190, 20));

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
        jPanel2.add(jBGranDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, 10, 20));

        jLabel38.setText("Lote y pedimento:");
        jPanel2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 110, -1));

        jLabel36.setText("Cantidad:");
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 100, -1));

        jTGaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTGaran.setNextFocusableComponent(jComTall);
        jTGaran.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTGaranFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTGaranFocusLost(evt);
            }
        });
        jTGaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTGaranKeyPressed(evt);
            }
        });
        jPanel2.add(jTGaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, 190, 20));

        jLabel39.setText("Existencia:");
        jPanel2.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 100, -1));

        jBUltCostT.setBackground(new java.awt.Color(255, 255, 255));
        jBUltCostT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ultcostpt.png"))); // NOI18N
        jBUltCostT.setToolTipText("Ultimos 100 Precios de Compra de todos los Proveedores");
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
        jPanel2.add(jBUltCostT, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, 40, 40));

        jLabel40.setText("Almacén:");
        jPanel2.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 87, -1));

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
        jPanel2.add(jComAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 120, 20));

        jTDescripAlma.setEditable(false);
        jTDescripAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripAlma.setNextFocusableComponent(jComUnid);
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
        jPanel2.add(jTDescripAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 140, 20));

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
        jPanel2.add(jBExisAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 10, 20));

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
        jPanel2.add(jBCarg, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 70, -1));

        jBLimp.setBackground(new java.awt.Color(255, 255, 255));
        jBLimp.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBLimp.setText("Limpiar");
        jBLimp.setToolTipText("Limpiar lista de archivos seleccionados");
        jBLimp.setNextFocusableComponent(jBTipCam);
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
        jPanel2.add(jBLimp, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 70, 20));

        jBTipCam.setBackground(new java.awt.Color(255, 255, 255));
        jBTipCam.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBTipCam.setForeground(new java.awt.Color(0, 102, 0));
        jBTipCam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dol.png"))); // NOI18N
        jBTipCam.setToolTipText("Definir el tipo de cambio dollar del dìa");
        jBTipCam.setNextFocusableComponent(jBUltCostT);
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
        jPanel2.add(jBTipCam, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, 40, 40));

        jTCarSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCarSer.setNextFocusableComponent(jBNew);
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
        jPanel2.add(jTCarSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 160, 160, 20));

        jBCarSer.setBackground(new java.awt.Color(255, 255, 255));
        jBCarSer.setText("..");
        jBCarSer.setToolTipText("Buscar archivo EXCEL para series con dos columnas");
        jBCarSer.setNextFocusableComponent(jTCarSer);
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
        jPanel2.add(jBCarSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 160, 30, 20));

        jLabel14.setText("Importar números de serie:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 140, 150, 20));

        jP1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 1090, 200));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos del Proveedor", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Cod. Proveedor:");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 110, -1));

        jLabel6.setText("*Cod. proveedor:");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, -1));

        jTNoDoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoDoc.setNextFocusableComponent(jTCond);
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
        jPanel4.add(jTNoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 90, 20));

        jLabel2.setText("Crédito:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 80, -1));

        jLabel20.setText("*Folio documento:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 110, -1));

        jTNom.setEditable(false);
        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNom.setNextFocusableComponent(jTNoDoc);
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
        jPanel4.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 370, 20));

        jTProv.setBackground(new java.awt.Color(204, 255, 204));
        jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProv.setNextFocusableComponent(jBProv);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTProvKeyTyped(evt);
            }
        });
        jPanel4.add(jTProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 90, 20));

        jBProv.setBackground(new java.awt.Color(255, 255, 255));
        jBProv.setText("...");
        jBProv.setToolTipText("Buscar Proveedor(es)");
        jBProv.setNextFocusableComponent(jTSer);
        jBProv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProvMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProvMouseExited(evt);
            }
        });
        jBProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProvActionPerformed(evt);
            }
        });
        jBProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProvKeyPressed(evt);
            }
        });
        jPanel4.add(jBProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 30, 20));

        jTCond.setEditable(false);
        jTCond.setBackground(new java.awt.Color(255, 255, 204));
        jTCond.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCond.setFocusable(false);
        jTCond.setNextFocusableComponent(jCCo1);
        jTCond.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCondFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCondFocusLost(evt);
            }
        });
        jTCond.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCondKeyPressed(evt);
            }
        });
        jPanel4.add(jTCond, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 370, 20));

        jCConta.setBackground(new java.awt.Color(255, 255, 255));
        jCConta.setText("Contado ");
        jCConta.setNextFocusableComponent(jTMetPag);
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
        jPanel4.add(jCConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 80, -1));

        jTSer.setEditable(false);
        jTSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSer.setNextFocusableComponent(jTNom);
        jTSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSerFocusLost(evt);
            }
        });
        jTSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTSerActionPerformed(evt);
            }
        });
        jTSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSerKeyPressed(evt);
            }
        });
        jPanel4.add(jTSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 60, 20));

        jLabel33.setText("Nombre:");
        jPanel4.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 80, -1));

        jCCo1.setBackground(new java.awt.Color(255, 255, 255));
        jCCo1.setSelected(true);
        jCCo1.setText("Correo 1");
        jCCo1.setNextFocusableComponent(jTCo1);
        jCCo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo1KeyPressed(evt);
            }
        });
        jPanel4.add(jCCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 100, 20));

        jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo1.setNextFocusableComponent(jCConta);
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
        jPanel4.add(jTCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 180, 20));

        jCCo2.setBackground(new java.awt.Color(255, 255, 255));
        jCCo2.setText("Correo 2");
        jCCo2.setNextFocusableComponent(jTCo2);
        jCCo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo2KeyPressed(evt);
            }
        });
        jPanel4.add(jCCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 110, 20));

        jTCo2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo2.setNextFocusableComponent(jCGua);
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
        jPanel4.add(jTCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 180, 20));

        jCCo3.setBackground(new java.awt.Color(255, 255, 255));
        jCCo3.setText("Correo 3");
        jCCo3.setNextFocusableComponent(jTCo3);
        jCCo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo3KeyPressed(evt);
            }
        });
        jPanel4.add(jCCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 110, 20));

        jTCo3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCo3.setNextFocusableComponent(jDFech);
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
        jPanel4.add(jTCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 180, 20));

        jCGua.setBackground(new java.awt.Color(255, 255, 255));
        jCGua.setText("Modificar proveedor");
        jCGua.setNextFocusableComponent(jTCta);
        jCGua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGuaKeyPressed(evt);
            }
        });
        jPanel4.add(jCGua, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 160, 20));

        jLGua.setForeground(new java.awt.Color(0, 0, 255));
        jLGua.setText("F8");
        jPanel4.add(jLGua, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 60, 20));

        jLGua1.setForeground(new java.awt.Color(0, 0, 255));
        jLGua1.setText("Alt+G");
        jPanel4.add(jLGua1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 70, -1));

        jLabel44.setText("Forma pago:");
        jPanel4.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 100, -1));

        jTMetPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMetPag.setNextFocusableComponent(jCCo2);
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
        jPanel4.add(jTMetPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 120, 20));

        jTCta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCta.setNextFocusableComponent(jComFormPag);
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
        jPanel4.add(jTCta, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 80, 120, 20));

        jLabel48.setText("Cuenta:");
        jPanel4.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 100, -1));

        jLabel13.setText("Método pago:");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, 100, -1));

        jComFormPag.setNextFocusableComponent(jCCo3);
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
        jPanel4.add(jComFormPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 100, 120, 20));

        jP1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 790, 130));

        jTSubTot.setEditable(false);
        jTSubTot.setBackground(new java.awt.Color(204, 255, 204));
        jTSubTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
        jP1.add(jTSubTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 550, 160, 20));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Sub Total:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 550, 110, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Impuesto:");
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 590, 110, -1));

        jTImp.setEditable(false);
        jTImp.setBackground(new java.awt.Color(204, 255, 204));
        jTImp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
        jP1.add(jTImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 590, 160, 20));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Total:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 610, 110, -1));

        jTTot.setEditable(false);
        jTTot.setBackground(new java.awt.Color(204, 255, 204));
        jTTot.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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
        jP1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 610, 160, 20));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 650, 160, 20));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 372, 130, 18));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nuevo");
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 110, 20));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Partida(s) (Ctrl+SUPR)");
        jBDel.setNextFocusableComponent(jBTod);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 110, 20));

        jPEnca.setBackground(new java.awt.Color(255, 255, 255));
        jPEnca.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Encabezado", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPEnca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPEncaKeyPressed(evt);
            }
        });
        jPEnca.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLEnt.setText("Fecha entrega:");
        jPEnca.add(jLEnt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, -1));

        jDFEnt.setNextFocusableComponent(jComSer);
        jDFEnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFEntKeyPressed(evt);
            }
        });
        jPEnca.add(jDFEnt, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 130, -1));

        jCOrd.setBackground(new java.awt.Color(255, 255, 255));
        jCOrd.setText("Orden de compra");
        jCOrd.setNextFocusableComponent(jCImp);
        jCOrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCOrdActionPerformed(evt);
            }
        });
        jCOrd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCOrdKeyPressed(evt);
            }
        });
        jPEnca.add(jCOrd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, 20));

        jLabel31.setForeground(new java.awt.Color(0, 0, 255));
        jLabel31.setText("Altl+O");
        jPEnca.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 80, 20));

        jCImp.setBackground(new java.awt.Color(255, 255, 255));
        jCImp.setText("Imprimir");
        jCImp.setNextFocusableComponent(jComMon);
        jCImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCImpKeyPressed(evt);
            }
        });
        jPEnca.add(jCImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 80, 20));

        jLabel32.setForeground(new java.awt.Color(0, 0, 255));
        jLabel32.setText("Ctrl+P");
        jPEnca.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 60, 20));

        jLabel30.setText("*Serie:");
        jPEnca.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 100, -1));

        jComSer.setNextFocusableComponent(jCOrd);
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
        jPEnca.add(jComSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 130, 20));

        jLabel29.setText("Fecha compra:");
        jPEnca.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 100, -1));

        jDFech.setNextFocusableComponent(jDFEnt);
        jDFech.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFechKeyPressed(evt);
            }
        });
        jPEnca.add(jDFech, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 130, -1));

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
        jPEnca.add(jComMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 80, 20));

        jLabel9.setText("Moneda:");
        jPEnca.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 80, 20));

        jP1.add(jPEnca, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 20, 290, 130));
        jP1.add(jTAlma2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 370, 10, -1));

        jTComenSer.setEditable(false);
        jTComenSer.setFocusable(false);
        jP1.add(jTComenSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 370, 10, -1));

        jLNot.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLNot.setForeground(new java.awt.Color(204, 0, 0));
        jLNot.setText("NUEVA COMPRA");
        jLNot.setFocusable(false);
        jP1.add(jLNot, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 550, 420, -1));

        jLTipVta.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLTipVta.setText("COMPRA A CRÉDITO");
        jLTipVta.setFocusable(false);
        jP1.add(jLTipVta, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 590, 500, -1));

        jLTotSald.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLTotSald.setForeground(new java.awt.Color(204, 0, 51));
        jLTotSald.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLTotSald.setText("Total a pagar:");
        jP1.add(jLTotSald, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 630, 160, 20));

        jTTotSald.setEditable(false);
        jTTotSald.setBackground(new java.awt.Color(255, 204, 204));
        jTTotSald.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTTotSald.setForeground(new java.awt.Color(51, 51, 0));
        jTTotSald.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTotSald.setText("$0.00");
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
        jP1.add(jTTotSald, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 630, 160, 20));

        jDesc.setEditable(false);
        jDesc.setBackground(new java.awt.Color(204, 255, 204));
        jDesc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jDesc.setForeground(new java.awt.Color(51, 51, 0));
        jDesc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jDesc.setText("$0.00");
        jDesc.setNextFocusableComponent(jBGuar);
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
        jP1.add(jDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 570, 160, 20));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("Descuento:");
        jP1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 570, 110, -1));

        jTSerProd.setEditable(false);
        jTSerProd.setFocusable(false);
        jP1.add(jTSerProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 370, 10, -1));

        label1.setText("Presionar F2 para aplicar nota de crédito");
        jP1.add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 370, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 1119, Short.MAX_VALUE)
                .addContainerGap())
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
   
    
   /*Carga las series en el combobox*/
   private void vCargSer()
   {
        /*Borra los items en el combobox de series*/
        jComSer.removeAllItems();

        /*Agrega el elemento vacio*/
        jComSer.addItem("");

        /*Crea la condición para las órdenes de compra*/
        String sConO    = " tip = 'COMP'";
        if(jCOrd.isSelected())
            sConO       = " tip = 'ORDC'";
        
        //Abre la base de datos        
        Connection  con = Star.conAbrBas(true, false);  

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
        
    }/*Fin de private void vCargSer(Connection con)*/
     
    
   /*Función para hacer los controles de la órden focusables o no focusables*/    
   private void vOrdCtrl(boolean bVal)
   {
        /*Muestra o no todos los controles que involucran la órden de compra*/
        jDFEnt.setVisible(bVal);
        jCCo1.setVisible(bVal);
        jCCo2.setVisible(bVal);
        jCCo3.setVisible(bVal);
        jTCo1.setVisible(bVal);
        jTCo2.setVisible(bVal);
        jTCo3.setVisible(bVal);                
        jLEnt.setVisible(bVal);

        /*Que todos o ninguno de estos controles sea focusable*/
        jDFEnt.setFocusable(bVal);
        jCCo1.setFocusable(bVal);
        jCCo2.setFocusable(bVal);
        jCCo3.setFocusable(bVal);
        jTCo1.setFocusable(bVal);
        jTCo2.setFocusable(bVal);
        jTCo3.setFocusable(bVal);        
        
    }/*Fin de private void vOrdCtrl(boolean bVal)*/
    
    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed
                                      
    
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
        
    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón de mouse*/
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered

        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);

    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuando se sale el mouse del botón de guadar*/    
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(colOri);

    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        /*Si el código del proveedor es vacio entonces*/
        if(jTProv.getText().replace(jTSer.getText(), "").compareTo("")==0)
        {
            /*Coloca el borde rojo*/
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código del proveedor esta vacio.", "Proveedores", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el control y regresa*/
            jTProv.grabFocus();
            return;
        }

        /*Si el nombre del proveedor es el vacio entonces*/
        if(jTNom.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código del proveedor no existe.", "Proveedores", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el control y regresa*/
            jTProv.grabFocus();
            return;
        }

        /*Si aún no hay partidas entonces*/
        if(jTab.getRowCount()==0)
        {
            /*Coloca el foco del teclado en el campo del producto*/
            jTProd.grabFocus();

            /*Coloca el borde rojo del control de productos*/
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No existen partidas en la compra.", "Guadar compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si no a seleccionado una moneda entonces*/
        if(jComMon.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una moneda.", "Nueva compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
            /*Coloca el foco del teclado en el control y regresa*/
            jComMon.grabFocus();                        
            return;            
        }
        //Abre la base de datos        
        Connection  con = Star.conAbrBas(false, false);  

        //Si hubo error entonces regresa
        if(con==null)
            return;
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene cuál es la moneda nacional*/
        String sCodMN   = Star.sGetMonNac(con);
       
        //Si hubo error entonces regresa
        if(sCodMN==null)
            return ;
        
        /*Obtiene si la configuración global para que no se pueda comprar a ningùn proveedor con moneda distinta a la nacional esta activada o no*/
        boolean bSi = true;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'comps' AND conf = 'otramon'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
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

        /*Comprueba si el proveedor tiene o no habilitado que se le pueda comprar con otra moneda distinta a la nacional*/
        try
        {
            sQ = "SELECT otramon FROM provs WHERE CONCAT_WS('', ser, prov) = '" + jTProv.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Si no tiene habilitado la compra con otra moneda o globalmente entonces*/
                if(rs.getString("otramon").compareTo("0")==0 || !bSi)
                {
                    /*Compara el código de moneda nacional con el que se quiere agregar*/
                    if(sCodMN.compareTo(jComMon.getSelectedItem().toString())!=0)
                    {
                        if(!bSi)
                        {
                        //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return ;

                        /*Coloca el borde rojo*/
                        jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "Por configuración del sistema, solo se permite comprar en Moneda Nacional: \"" + sCodMN + "\".", "Compras", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                        /*Coloca el foco del teclado en el control y regresa*/
                        jComMon.grabFocus();
                        return ;
                        }
                        else
                        {
                            //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return ;

                        /*Coloca el borde rojo*/
                        jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "La configuración del proveedor no permite comprar en una moneda distinta a la Moneda Nacional: \"" + sCodMN + "\".", "Compras", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                        /*Coloca el foco del teclado en el control y regresa*/
                        jComMon.grabFocus();
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
        /*Determina el mensaje*/
        String sMsj = "Orden";
        if(!jCOrd.isSelected())
            sMsj    = "Compra";

        /*Si no se a seleccionado una serie para las compras entonces*/
        if(jComSer.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Coloca el foco del teclado en el combobox de series*/
            jComSer.grabFocus();

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "Selecciona una serie para la " + sMsj + ".", "Guadar compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Lee el número de documento*/
        String sNoDoc            = jTNoDoc.getText().trim();
        if(!jCOrd.isSelected())
        {
            /*Si el número de documento es vacio entonces*/
            if(sNoDoc.compareTo("")==0)
            {
                /*Coloca el borde rojo*/
                jTNoDoc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Ingresa el número de documento.", "Documento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el control y regresa*/
                jTNoDoc.grabFocus();
                return;
            }
        }

        /*Obtiene la fecha del documento*/    
        String sFDoc;
        try
        {
            Date fe                 =  jDFech.getDate();
            SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");
            sFDoc                   = sdf.format(fe);      
        }
        catch(NullPointerException expnNullPoint)
        {
            /*Pon el foco del teclado en el control*/
            jDFech.grabFocus();
                    
            /*Mensajea y regresa*/   
            JOptionPane.showMessageDialog(null, "Fecha de documento incorrecta.", "Fecha", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }                                
        
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
            jDFech.grabFocus();
                    
            /*Mensajea y regresa*/   
            JOptionPane.showMessageDialog(null, "Fecha de entrega incorrecta.", "Fecha", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }                                
        
        /*Lee el subtotal*/
        String sSubTot          = jTSubTot.getText().replace("$", "").replace(",", "");

        /*Lee el impuesto*/
        String sImpue           = jTImp.getText().replace("$", "").replace(",", "");

        /*Lee el total*/
        String sTot             = jTTot.getText().replace("$", "").replace(",", "");
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Búsca el folio que ingreso el usuario para ver si ya existe en en la base de datos*/
        try
        {
            sQ = "SELECT nodoc FROM comprs WHERE prov = '" + jTProv.getText().trim() + "' AND nodoc = '" + jTNoDoc.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe*/
            if(rs.next())
            {
                /*Preguntar al usuario si quiere continuar aunque el folio este repetido*/
                Object[] op = {"Si","No"};
                int iRes    = JOptionPane.showOptionDialog(this, "El folio: " + jTNoDoc.getText().trim() + " ya existe para el proveedor: " + jTProv.getText().trim() + ", ¿Quieres continuar?", "Folio Existente", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                {
                    //Cierra la base de datos y regresa
                    Star.iCierrBas(con);
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
        
        //Declara variables locales
        String sTipCon  = "COMP";
        String sMsjT    = "Compra";
        
        /*Determina el tipo de consecutivo que sera*/        
        if(jCOrd.isSelected())
        {
            sTipCon     = "ORDC";            
            sMsjT       = "Orden de Compra";
        }
        
        /*Obtiene si esta bloqueado el proveedor o no y el último consecutivo de la serie*/
        String sCons    = "";
        try
        {
            sQ = "SELECT (SELECT consec FROM consecs WHERE ser = '" + jComSer.getSelectedItem().toString().trim() + "' AND tip = '" + sTipCon.trim() + "') AS cons, bloq FROM provs WHERE CONCAT_WS('', ser, prov) = '" + jTProv.getText() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene el consecutivo*/
                sCons   = rs.getString("cons");

                /*Si esta bloqueado entonces*/
                if(rs.getString("bloq").compareTo("1")==0)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Coloca el borde rojo*/
                    jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "El proveedor: " + jTProv.getText().trim() + " esta bloqueado, no se le puede comprar.", "Compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
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
        
        //Obtiene si el usuario tiene correo asociado
        boolean bNoMan = false;
        int iRes    = Star.iUsrConfCorr(con, Login.sUsrG);
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Comprueba si la configuración de mostrar el mensaje esta habilitado o no
        int iMosMsj = Star.iMostMsjCorrUsr(con);

        //Si hubo error entonces regresa
        if(iMosMsj==-1)
            return;                        
        
        //Si no tiene correo asociado entonces solamente mensajea
        if(iRes==0 && jCOrd.isSelected() && iMosMsj==1)
        {
            /*Mensajea y coloca la bandera*/
            JOptionPane.showMessageDialog(null, "No se a definido correo electrónico para el usuario: " + Login.sUsrG + " y no se enviará la Orden/Compra.", "Correo electrónico",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            bNoMan = true;
        }
            
        /*Si el mensaje anterior de correo no se ha mostrado entonces si no esta seleccionado ningún correo entonces mensajea y coloca la bandera*/
        if(!bNoMan && jCOrd.isSelected() && !jCCo1.isSelected() && !jCCo2.isSelected() && !jCCo3.isSelected())        
            JOptionPane.showMessageDialog(null, "No se enviará la " + sMsjT + " por correo electrónico por que no se a seleccionado ningún correo del Proveedor.", "Correo Electrónico",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    

        /*Si no se a mostrado el mensaje de correo y si se tiene que mandar a algún correo pero es cadena vacia entonces*/
        if(!bNoMan && (jCCo1.isSelected() && jCCo2.isSelected() && jCCo3.isSelected()))
        {
            JOptionPane.showMessageDialog(null, "No se enviará la " + sMsj + " por correo electrónico  en donde hay cadena vacia de los campos de correo.", "Correo Electrónico",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            bNoMan = true;
        }

        /*Si el control del saldo del cliente esta visible entonces*/
        if(jTTotSald.isVisible())
        {
            /*Si el saldo a pagar es menor a 0 entonces*/
            if(Double.parseDouble(jTTotSald.getText().replace("$", "").replace(",", "").trim())<0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El saldo total a pagar no es correcto.", "Generar nueva venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }            
        }
        
        /*Preguntar al usuario si esta seguro de que están bien los datos*/
        Object[] op = {"Si","No"};
        iRes        = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Generar compra", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }

        /*Si es órden de compra entonces el folio debe ser el consecutivo de la compra*/
        if(jCOrd.isSelected())
            sNoDoc  = sCons;

        /*Determina que tipo de documento sera*/
        String sTip = "COMP";
        if(jCOrd.isSelected())
            sTip    = "ORD";
        
        /*Determina el estado de la órden o compra*/
        String sEst = "CO";
        if(jCOrd.isSelected())
            sEst    = "PE";

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
        
        //Declara variables locales
        String sConsec  = "";
        String sSer     = "";
                
        /*Obtiene el consecutivo y el código de la serie de la compra*/
        try
        {
            sQ = "SELECT consec, ser FROM consecs WHERE tip = '" + sTipCon + "' AND ser = '" + jComSer.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                
                sConsec      = rs.getString("consec");
                sSer         = rs.getString("ser");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }

        /*Actualiza el consecutivo de la compra u órden*/
        try
        {
            sQ = "UPDATE consecs SET "
                    + "consec       = consec + 1, "
                    + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        ='" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE tip    = '" + sTipCon.replace("'", "''").trim() + "' AND ser = '" + jComSer.getSelectedItem().toString().replace("'", "''").trim() + "'";
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                
        }

        /*La fecha de vencimineto inicialmente será la misma del día de hoy*/
        String sFVenc = "now()";
        
        /*Comprueba si tiene crédito el proveedor y los días de crédito*/                
        bSi = false;
        if(!jCOrd.isSelected())
        {
            try
            {
                sQ = "SELECT diacred AS dias, '" + sFDoc + "' + INTERVAL diacred DAY AS vencimien FROM provs WHERE prov = '" + jTProv.getText().replace(jTSer.getText().trim(), "").trim() + "' AND ser = '" + jTSer.getText() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    /*Coloca la bandera*/
                    if(Double.parseDouble(rs.getString("dias")) > 0)
                        bSi = true;

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
            catch(NumberFormatException expnNumForm)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnNumForm.getMessage(), Star.sErrNumForm, expnNumForm.getStackTrace(), con);                                                       
                return;                                        
            }

        }/*Fin de if(!jCOrd.isSelected())*/
        
        /*Si tiene días de crédito y no va a ser de contado entonces*/
        if(bSi && !jCConta.isSelected())
        {
            //Inserta cxp en la base de datos
            if(Star.iInsCXCP(con, "cxp", sSer + sConsec, sSer, jTProv.getText(), jTSer.getText(), sSubTot, sImpue, sTot, sTot, "0",  sFVenc ,"'" + sFDoc + "'", "COMP", jComFormPag.getSelectedItem().toString(), "0", "", "","")==-1)
                return;                                        
        }
        //Else no es de crédito y tampoco una órden de compra entonces registra el CXP
        else if(!jCOrd.isSelected())
        {
            //Inserta en CXP el cargo
            if(Star.iInsCXCP(con, "cxp", sSer + sConsec, sSer, jTProv.getText(), jTSer.getText(), sSubTot, sImpue, sTot, sTot, "0", sFVenc , "'" + sFDoc + "'", "COMP", "", "0", "", "","")==-1)
                return;        
            
            //Inserta en CXP el abono que es el pago
            if(Star.iInsCXCP(con, "cxp", sSer + sConsec, sSer, jTProv.getText(), jTSer.getText(), sSubTot, sImpue, sTot, "0", sTot,  sFVenc , sFVenc, "ABON COMP", jComFormPag.getSelectedItem().toString(), "0", "", "","")==-1)
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
        //Inserta en la base de datos la compra u órden        
        if(Star.iInsComprs(con, sSer.replace("'", "''") + sConsec.replace("'", "''"), sSer.replace("'", "''"), jTSer.getText().replace("'", "''") + jTProv.getText().replace(jTSer.getText(), ""), jTSer.getText().replace("'", "''").trim(), sNoDoc.replace("'", "''"), "'" + sFDoc + "'", sSubTot, sImpue, sTot, "'" + sEst + "'", "now()", "", jTNom.getText().replace("'", "''").trim(), sFVenc, sTip.replace("'", "''"), "'" + sFEnt + "'", jTMetPag.getText().trim(), jTCta.getText().trim(), jComMon.getSelectedItem().toString().trim(), sTipCam, "", "0", "0")==-1)
            return;
            
        /*Recorre la tabla de partidas de compra*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Si la partida es una nota de crédito entonces*/
            if(jTab.getValueAt(x, 2).toString().compareTo("0")==0)
            {
                /*Agrega en la compra la nota de crédito con la que se esta pagando*/
                try 
                {                
                    sQ = "UPDATE comprs SET "
                            + "notcredpag       = CONCAT_WS('', notcredpag, '" + jTab.getValueAt(x, 1).toString().trim() + "|') "
                            + "WHERE codcomp    = '" + sSer.replace("'", "''") + sConsec.replace("'", "''") + "'";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                                           
                }    
                
                /*Actualiza el estado de nota de crédito como que ya esta usada y la compra a la que se asigno*/
                try 
                {                
                    sQ = "UPDATE comprs SET "
                            + "estado                               = 'CO',  "
                            + "notcred                              = '" + sSer.replace("'", "''") + sConsec.replace("'", "''")  + "' " 
                            + "WHERE codcomp                        = '" + jTab.getValueAt(x, 1).toString().trim() + "' AND tip = 'NOT'";                    
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
            
            /*Obtiene el descuento*/
            String sDesc                = jTab.getValueAt(x, 7).toString().trim();
            
            /*Si el descuento es cadena vacia entonces colocar -1*/
            if(sDesc.compareTo("")==0)
                sDesc                   = "-1";
            
            //Obitiene el valor del iva
            String IvaVal               = jTab.getValueAt(x, 10).toString().replace("$", "").replace(",", "").trim();
            
            if(IvaVal.compareTo("")==0)
                IvaVal          = "0";
            
            /*Obtiene el descuento adicional*/
            String sDescAd              = jTab.getValueAt(x, 8).toString().trim();

            /*Si el descuento adicional es cadena vacia entonces colocar -1*/
            if(sDescAd.compareTo("")==0)
                sDescAd                 = "-1";

            /*Obtiene el importe*/
            String sImp                 = jTab.getValueAt(x, 11).toString().replace("$", "").replace(",", "").trim();

            /*Si es órden entonces que se inserte toda la cantidad*/
            String sCant                = jTab.getValueAt(x, 2).toString().replace("$", "").replace(",", "").trim();
            if(jCOrd.isSelected())
                sCant                   = "0";

            /*Obtiene la existencia general del prodcuto*/
            String sExistG              = Star.sGetExisGral(con, jTProd.getText().trim());                       
            
            /*Obtiene el último costo del producto*/
            String sCostU               = Star.sUltCost(con, jTProd.getText().trim());
            
            /*Si el último costo es 0 entonces dejarlo como el costo actual*/
            String sCostP               = Star.sCostProm(con, jTProd.getText().trim());
            if(Double.parseDouble(sCostP)==0)
                sCostP      = jTab.getValueAt(x, 6).toString();
            else
                sCostP      = Double.toString(((Double.parseDouble(sExistG) * Double.parseDouble(sCostU)) + (Double.parseDouble(jTab.getValueAt(x, 2).toString()) * Double.parseDouble(jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "")))) / (Double.parseDouble(sExistG) + Double.parseDouble(jTab.getValueAt(x, 2).toString())));
                                    
            /*Comprueba si el producto es un kit*/
            String sKit = "0";
            try
            {
                sQ = "SELECT compue FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    sKit      = rs.getString("compue");
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                            
            }        
            
            /*Inserta en la base de datos la partida de la compra*/
            try
            {
                sQ = "INSERT INTO partcomprs(codcom,                                                            prod,                                                                       cant,                                            impue,             unid,                                                                descrip,                                                        cost,                                                                                   descu,             descad,              codimpue,                                                  mon,         impo,                                          falt,      recib,          alma,                                                          costpro,                                           lot,                                                            pedimen,                                                        flotvenc,                                                  cantlotpend,  serprod,                                     comenser,                                                        garan,                                          tipcam,             eskit,         tall,                                                colo) " +
                                "VALUES('" + sSer.replace("'", "''") + sConsec.replace("'", "''") + "','"  +    jTab.getValueAt(x, 1).toString().replace("'", "''").trim() + "','" +        jTab.getValueAt(x, 2).toString().trim() + "','"+ IvaVal + "','"   +   jTab.getValueAt(x, 3).toString().replace("'", "''") + "','" +        jTab.getValueAt(x, 5).toString().replace("'", "''") + "','" +   jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "").trim() + "','" +     sDesc + "','" +    sDescAd + "','" +    jTab.getValueAt(x, 9).toString().replace("'", "''") + "',  '','" +      sImp.replace("$", "").replace(",", "") + "',   now(), " + sCant + ", '" + jTab.getValueAt(x, 4).toString().replace("'", "''") + "', " +  sCostP.replace("$", "").replace(",", "") + ", '" + jTab.getValueAt(x, 14).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 15).toString().replace("'", "''") + "', '" + jTab.getValueAt(x, 16).toString().replace("'", "''") + "', 0, '" +       jTab.getValueAt(x, 17).toString() + "', '" + jTab.getValueAt(x, 18).toString().replace("'", "''") + "', '" +  jTab.getValueAt(x, 19).toString() + "', " +     sTipCam + ", " +    sKit + ", '" + jTab.getValueAt(x, 12).toString().trim() + "', '" +  jTab.getValueAt(x, 13).toString().trim() + "')";
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL)
            {    
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                           
            }

            /*Actualiza el último costo del producto*/
            try
            {
                sQ = "UPDATE prods SET "
                        + "cost         = " + jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "") + " "
                        + "WHERE prod   = '" + jTab.getValueAt(x, 1).toString().replace("'", "''") + "'";
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
                    sQ = "SELECT MAX(id_id) AS id FROM partcomprs WHERE codcom = '" + sSer.replace("'", "''") + sConsec.replace("'", "''") + "' ORDER BY id DESC LIMIT 1";	
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
                
                /*Agrega las partidas en la compra con precio 0*/                
                if(Star.iInsCompKit(con, jTab.getValueAt(x, 1).toString().trim(), sSer.replace("'", "''") + sConsec.replace("'", "''"), jTab.getValueAt(x, 4).toString().trim(), "COMP", jTNoDoc.getText().trim(),jTProv.getText().trim(), "0", sSer.replace("'", "''"), sTipCam, sId, jTab.getValueAt(x, 2).toString().trim(), jTab.getValueAt(x, 3).toString().trim())==-1)
                    return;                                
                
            }/*Fin de if(sKit.compareTo("1")==0)*/
                        
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
            
            /*La cantidad a manejar originalmente será la que ingreso el usuario*/
            String sCantO       = jTab.getValueAt(x, 2).toString().trim();

            /*Obtiene la cantidad correcta en base a la unidad*/                
            sCantO              = Star.sCantUnid(jTab.getValueAt(x, 3).toString().trim(), sCantO);

            /*Si tene talla o color entonces procesa las tallas y colores y no es una órden de compra*/
            if(!jCOrd.isSelected() && (jTab.getValueAt(x, 12).toString().compareTo("")!=0 || jTab.getValueAt(x, 13).toString().compareTo("")!=0))                           
                Star.vTallCol(con, sCantO, jTab.getValueAt(x, 4).toString(), jTab.getValueAt(x, 12).toString(), jTab.getValueAt(x, 13).toString(), jTab.getValueAt(x, 1).toString(), "+");            

            /*Si existe serie para este producto y va a ser compra entonces*/
            if(jTab.getValueAt(x, 17).toString().compareTo("")!=0 && !jCOrd.isSelected())
                Star.vSerPro(con, jTab.getValueAt(x, 1).toString(), sCantO, jTab.getValueAt(x, 17).toString(), jTab.getValueAt(x, 4).toString(), jTab.getValueAt(x, 18).toString().replace("'", "''"), "+");            
                    
            /*Ingresa el costeo*/
            if(Star.iInsCost(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim(), jTab.getValueAt(x, 2).toString().trim(), jTab.getValueAt(x, 6).toString().replace("$", "").replace(",", "").trim())==-1)
                return;
                        
            /*Si no es órden, ni kit entonces y tampoco es un servicio entonces*/
            if(!jCOrd.isSelected() && sKit.compareTo("0")==0 && !bServ)
            {                
                /*Si tiene lote o pedimento entonces*/
                if(jTab.getValueAt(x, 14).toString().compareTo("")!=0 || jTab.getValueAt(x, 15).toString().compareTo("")!=0)
                {
                    /*Procesalo en una función*/
                    if(Star.sLotPed(con, jTab.getValueAt(x, 1).toString().trim(), sCantO, jTab.getValueAt(x, 4).toString().trim(), jTab.getValueAt(x, 14).toString().trim(), jTab.getValueAt(x, 15).toString().trim(), jTab.getValueAt(x, 16).toString().trim(), "+")==null)               
                            return;
                }

                /*Realiza la afectación correspondiente al almacén*/
                if(Star.iAfecExisProd(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim(), jTab.getValueAt(x, 4).toString().replace("'", "''").trim(), sCantO, "+")==-1)
                    return;
                
                /*Actualiza la existencia general del producto*/
                if(Star.iCalcGralExis(con, jTab.getValueAt(x, 1).toString().replace("'", "''").trim())==-1)
                    return;
                
                /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
                if(!Star.vRegMoniInv(con, jTab.getValueAt(x, 1).toString().replace("'", "''") , sCantO, jTab.getValueAt(x, 5).toString().replace("'", "''"), jTab.getValueAt(x, 4).toString().replace("'", "''"), Login.sUsrG , sSer.replace("'", "''") + sConsec.replace("'", "''"), "COMP", jTab.getValueAt(x, 3).toString().replace("'", "''"), sSer.replace("'", "''"), jTProv.getText().replace("'", "''"), "0"))                                
                    return;                                                                                                                                                                                                             
                            
            }/*Fin de if(!jCOrd.isSelected())*/

        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
        
        //Declara variables locales
        String sFAlt    = "";
        String sNomEsta = "";
        
        /*Obtiene la fecha de alta con la que ingreso la compra u órden y el nombre del usuario*/        
        try
        {
            sQ = "SELECT (SELECT nom FROM estacs WHERE estac = '" + Login.sUsrG + "') AS est, falt FROM comprs WHERE codcomp = '" + sSer + sConsec + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                
                sFAlt      = rs.getString("falt");
                sNomEsta   = rs.getString("est");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                       
        }

        /*Recorre la tabla de partidas de de la compra para búscar si hay notas de crédito*/        
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Si no es una partida de nota de crédito entonces continua*/
            if(jTab.getValueAt(x, 2).toString().compareTo("0")!=0)
                continue;
            
            /*Si esa nota de crédito no es de ese proveedor entonces*/           
            try
            {
                sQ = "SELECT prov FROM comprs WHERE codcomp = '" + jTab.getValueAt(x, 1) + "' AND tip = 'NOT'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si no hay datos entonces no es esa nota de crédito de ese proveedor*/
                if(!rs.next())
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Coloca el foco del teclado en la tabla de partidas*/
                    jTab.grabFocus();
                    
                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "La nota de crédito: " + jTab.getValueAt(x, 1) + " no es del proveedor: " + jTProv.getText().trim(), "Nota de crédito", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
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
                
        /*Obtiene la cantidad de recibidos de las partidas de la compra*/
        String sCantR   = "";
        try
        {
            sQ = "SELECT SUM(recib) AS recib FROM partcomprs WHERE codcom = '" + sSer + sConsec + "'";
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

        /*Actualiza los recibidos de la compra*/
        try
        {
            sQ = "UPDATE comprs SET "
                    + "recib            = " + sCantR + " "
                    + "WHERE codcomp    = '" + sSer.replace("'", "''") + sConsec.replace("'", "''") +  "'";
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                        
        }

        /*Si tiene que guardar los datos del proveedor entonces*/
        if(jCGua.isSelected())
        {
            /*Actualiza los datos del proveedor*/
            try
            {
                sQ = "UPDATE provs SET "
                        + "co1                              = '" + jTCo1.getText().replace("'", "''").trim() + "', "
                        + "co2                              = '" + jTCo2.getText().replace("'", "''").trim() + "', "
                        + "co3                              = '" + jTCo3.getText().replace("'", "''").trim() + "',  "
                        + "metpag                           = '" + jTMetPag.getText().replace("'", "''").trim() + "',  "
                        + "cta                              = '" + jTCta.getText().replace("'", "''").trim() + "' "
                        + "WHERE CONCAT_WS('', ser, prov)   = '" + jTProv.getText().replace("'", "''").trim() + "'";
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
        if(sCodPrevG != null)
        {
            /*Actualiza la cotización para que sea de cancelación*/
            try
            {            
                sQ = "UPDATE prevcomprs SET "
                        + "estado            = 'CO', "
                        + "codcomp           = '" + sSer.replace("'", "''") + sConsec.replace("'", "''") + "', "
                        + "nosercomp         = '" + sConsec.replace("'", "''") + "', "
                        + "sucu              = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj             = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE codprevcomp = '" + sCodPrevG + "'";                                                
                st = con.createStatement();
                st.executeUpdate(sQ);
                
                
             }
             catch(SQLException ex) 
             { 
                 //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Agrega enel log*/
                Login.vLog(ex.getMessage());

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                return;
            }        

        }

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
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
    }
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

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
            sCarp                    += "\\" + jTProv.getText().trim();
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();
            
            /*Si no existe la carpeta para la compra entonces creala*/
            sCarp                    += "\\" + sSer + sConsec;     
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
        if(sCodi!=null)
        {/*Dale formato de moneda a los totales*/        
        java.text.NumberFormat n  = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        double dCant    = Double.parseDouble(sTot);
        sTot            = n.format(dCant);
        dCant           = Double.parseDouble(sSubTot);
        sSubTot         = n.format(dCant);
        dCant           = Double.parseDouble(sImpue);
        sImpue          = n.format(dCant);

        /*Agrega los datos en la tabla de compras del otro formulario*/
        DefaultTableModel te    = (DefaultTableModel)jTableCompras.getModel();
        Object nu[]             = {Star.iContFiComp, sTip, sSer + sConsec, sSer, sNoDoc, jTProv.getText(), jTNom.getText(), sTot, jComMon.getSelectedItem().toString().trim(), sFAlt, sFDoc, sFEnt, sFAlt, "CO", "", sCantR, "", "", "", Star.sSucu, Star.sNoCaj, Login.sUsrG, sNomEsta};
        te.addRow(nu);
        
        /*Aumenta en uno el contador de filas de las compras del otro formulario*/
        ++Star.iContFiComp;
        }
        System.out.println("exito");
        /*Si se tiene que imprimir la órden o la compra entonces*/
        boolean bImp    = false;
        if(jCImp.isSelected())
            bImp        = true;
        /*Si es órden entonces genera el PDF y mandalo por correo*/
        if(jCOrd.isSelected())
            Star.vMandOr(bImp, false, jTCo1.getText(), jTCo2.getText(), jTCo3.getText(), sSer + sNoDoc, jTNom.getText(), jTProv.getText(), sSubTot, sTot, sImpue, sNomLoc, sTelLoc, sColLoc, sCallLoc, sCPLoc, sCiuLoc, sEstLoc, sPaiLoc, bNoMan, sRutLog, jComMon.getSelectedItem().toString().trim());

        /*Si se tiene que imprimir la compra entonces*/
        if(bImp && !jCOrd.isSelected())
            vImpCom(sSer + sConsec, sFDoc, jTNom.getText(), sNoDoc, jTProv.getText(), jTSubTot.getText(), sTot, jTImp.getText(), true, false, jComMon.getSelectedItem().toString().trim());

        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, sMsj + " :" + sSer + sConsec + " guardada con éxito.", "Éxito al guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

        /*Llama al recolector de basura*/
        System.gc();
        
        /*Sal del formulario*/
        this.dispose();
        Star.gCompr = null;

    }//GEN-LAST:event_jBGuarActionPerformed
  
        
    /*Función para imprimir una compra*/
    private void vImpCom(final String sCod, final String sFDoc, final String sNom, final String sNoDoc, final String sProv, final String sSubTot, final String sTot, final String sImp, final boolean bImp, final boolean bMos, final String sMon)
    {    
        /*Realiza la operación en un thread*/
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
                JasperPrint pr;
                try
                {
                    /*Crea los parámetros que se pasarán*/
                    Map <String,String> para = new HashMap<>();             
                    para.clear();
                    para.put("COMP",       sCod);
                    para.put("FDOC",       sFDoc);
                    para.put("NOM",        sNom);
                    para.put("PROV",       sProv);
                    para.put("MON",        sMon);
                    para.put("NODOC",      sNoDoc);                    
                    para.put("SUBTOT",     sSubTot);
                    para.put("IMPUE",      sImp);
                    para.put("TOT",        sTot);
                    para.put("LOGE",       Star.class.getResource(Star.sIconDef).toString());
                    
                    /*Compila el reporte y muestralo maximizado*/
                    JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptVCom.jrxml"));
                    pr                  = JasperFillManager.fillReport(ja, (Map)para, con);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH); 
                    

                    /*Si se tiene que mostrar entonces*/
                    if(bMos)
                    {
                        /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte Compra");
                    v.setIconImage(newimg);
                        v.setVisible(true);                   
                    }
                }
                catch(JRException expnJASR)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                    return;                                                                                
                }

                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Si se tiene que imprimir entonces hazlo*/
                if(bImp)
                {
                    try
                    {
                        JasperPrintManager.printReport(pr,true);
                    }
                    catch(JRException expnJASR)
                    {
                        //Procesa el error
                        Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                                                                       
                    }                                        
                }
                
            }/*Fin de public void run()*/
            
        }).start();
       
    }/*Fin de private void vImpCom()*/
    
    
    /*Cuando se presiona una tecla en el botón de guaradr*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando el mouse entra del botón de salir*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered

        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);

    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón de salir*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);

    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
        /*Pregunta al usuario si esta seguro de abandonar*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {
            /*Llama al recolector de basura*/
            System.gc();
            
            /*Sal de la forma*/
            this.dispose();
            Star.gCompr = null;
        }

    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en la tabla*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando sucede una acción en el combo de monedas*/
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

    
    /*Cuando se presiona una tecla en el combo de monedas*/
    private void jComMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComMonKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComMonKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del descuento*/
    private void jTDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDesc.setSelectionStart(0);jTDesc.setSelectionEnd(jTDesc.getText().length());

    }//GEN-LAST:event_jTDescFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del descuento*/
    private void jTDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDesc.setCaretPosition(0);
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTDesc.getText().length()> 20)
            jTDesc.setText(jTDesc.getText().substring(0, 20));

        /*Si es cadena vacia entonces que sea 0*/
        if(jTDesc.getText().compareTo("")==0)
            jTDesc.setText("0");

    }//GEN-LAST:event_jTDescFocusLost

    
    /*Cuando se presiona una tecla en el campo del descunto*/
    private void jTDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescKeyPressed

    
    /*Cuando se tipea una tecla en el campo del descuento*/
    private void jTDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyTyped

        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTDesc.getText().length()> 30)
            jTDesc.setText(jTDesc.getText().substring(0, 30));

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTDescKeyTyped

            
    /*Cuando se pierde el foco del teclado en el combo de la unidad*/
    private void jComUnidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComUnidFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComUnid.getSelectedItem().toString().compareTo("")!=0)
            jComUnid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComUnidFocusLost

    
    /*Cuando sucede una acción en el combo de unidades*/
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

        /*Obtiene la descripción de la unidad en base a su código*/
        try
        {
            sQ = "SELECT descrip FROM unids WHERE cod = '" + jComUnid.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTDescripUnid.setText(rs.getString("descrip"));
            else
                jTDescripUnid.setText("");
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

    
    /*Cuando se presiona una tecla en el combo de las unidades*/
    private void jComUnidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComUnidKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComUnidKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripciòn*/
    private void jTDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescrip.setSelectionStart(0);jTDescrip.setSelectionEnd(jTDescrip.getText().length());

    }//GEN-LAST:event_jTDescripFocusGained

    
    /*Cuando se presiona una tecla ene l campo de la descripciòn*/
    private void jTDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del costo*/
    private void jTCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCost.setSelectionStart(0);jTCost.setSelectionEnd(jTCost.getText().length());

    }//GEN-LAST:event_jTCostFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del costo*/
    private void jTCostFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCost.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTCost.getText().compareTo("")!=0)
            jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Lee el texto introducido por el usuario*/
        String sTex = jTCost.getText().replace("$", "").replace(",", "");

        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTCost.getText().length()> 20)        
            jTCost.setText(jTCost.getText().substring(0, 20));        

        /*Si es cadena vacia regresa y no continuar*/
        if(sTex.compareTo("")==0)
            return;

        /*Si los caes introducidos no se puede convertir a double colocar 0 y regresar*/
        try
        {
            double d = Double.parseDouble(sTex);
        }
        catch(NumberFormatException expnNumForm)
        {
            jTCost.setText("$0.00");
            return;
        }

        /*Conviertelo a double*/
        double dCant    = Double.parseDouble(sTex);

        /*Formatealo*/
        java.text.NumberFormat n    = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        sTex                        = n.format(dCant);

        /*Colocalo de nu en el campo de texto*/
        jTCost.setText(sTex);

    }//GEN-LAST:event_jTCostFocusLost

    
    /*Cuando se presiona una tecla en el campo del costo*/
    private void jTCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCostKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCostKeyPressed

    
    /*Cuando se tipea una tecla en el control del costo*/
    private void jTCostKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCostKeyTyped

        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTCost.getText().length()> 30)
            jTCost.setText(jTCost.getText().substring(0, 30));

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTCostKeyTyped

    
    /*Cuando se gana el foco del teclado en el control de la cantidad*/
    private void jTCantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCant.setSelectionStart(0);jTCant.setSelectionEnd(jTCant.getText().length());

    }//GEN-LAST:event_jTCantFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cantidad*/
    private void jTCantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCant.setCaretPosition(0);
                
    }//GEN-LAST:event_jTCantFocusLost

    
    /*Cuando se presiona una tecla en el campo de la cantidad*/
    private void jTCantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCantKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del descuento*/
    private void jTDescAdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescAdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescAd.setSelectionStart(0);jTDescAd.setSelectionEnd(jTDescAd.getText().length());        

    }//GEN-LAST:event_jTDescAdFocusGained

    
    /*Cuando se pierde el foco del teclado en elcampo del descuento adicional*/
    private void jTDescAdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescAdFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDescAd.setCaretPosition(0);
        
        /*Si es cadena vacia entonces que sea 0*/
        if(jTDescAd.getText().compareTo("")==0)
            jTDescAd.setText("0");

    }//GEN-LAST:event_jTDescAdFocusLost

    
    /*Cuando se presiona una tecla en el campo del descuento adicional*/
    private void jTDescAdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescAdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescAdKeyPressed

    
    /*Cuando se tipea una tecla en el campo del descuento adicional*/
    private void jTDescAdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescAdKeyTyped

        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTDescAd.getText().length()> 30)
            jTDescAd.setText(jTDescAd.getText().substring(0, 30));

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTDescAdKeyTyped

    
    /*Cuando sucede una acción en el combo del importe*/
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
        Statement       st;
        ResultSet       rs;        
        String          sQ;
        
        /*Obtiene el valor del impue en base a su código*/
        try
        {
            sQ = "SELECT impueval FROM impues WHERE codimpue = '" + jComImp.getSelectedItem().toString() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
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

    
    /*Cuando se presiona uan tecla en el combo del importe*/
    private void jComImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComImpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComImpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del valor del impuesto*/
    private void jTValImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTValImpFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTValImp.setSelectionStart(0);jTValImp.setSelectionEnd(jTValImp.getText().length());

    }//GEN-LAST:event_jTValImpFocusGained

    
    /*Cuando se presiona una tecla en el campo del valor del impuesto*/
    private void jTValImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTValImpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTValImpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción de la unidad*/
    private void jTDescripUnidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripUnidFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripUnid.setSelectionStart(0);jTDescripUnid.setSelectionEnd(jTDescripUnid.getText().length());

    }//GEN-LAST:event_jTDescripUnidFocusGained

    
    /*Cuando se presiona una tecla en el campo de la descripción*/
    private void jTDescripUnidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripUnidKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripUnidKeyPressed

                   
    /*Cuando se gana el foco del teclado en el campo del código opcional 2*/
    private void jTCodOpl2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodOpl2FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodOpl2.setSelectionStart(0);jTCodOpl2.setSelectionEnd(jTCodOpl2.getText().length());

    }//GEN-LAST:event_jTCodOpl2FocusGained

    
    /*Cuando se presiona una tecla en el campo del código opcional 2*/
    private void jTCodOpl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodOpl2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCodOpl2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del código opcional 1*/
    private void jTCodOp1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodOp1FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodOp1.setSelectionStart(0);jTCodOp1.setSelectionEnd(jTCodOp1.getText().length());

    }//GEN-LAST:event_jTCodOp1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del código opcional 1*/
    private void jTCodOp1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodOp1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCodOp1KeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de existencias*/
    private void jTExistFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExistFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTExist.setSelectionStart(0);jTExist.setSelectionEnd(jTExist.getText().length());

    }//GEN-LAST:event_jTExistFocusGained

    
    /*Cuando se presiona una tecla en el campo de la existencia*/
    private void jTExistKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExistKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTExistKeyPressed

    
    /*Cuando el mouse entra en el campo de bùsqueda global*/
    private void jBProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseEntered

        /*Cambia el color del fondo del botón*/
        jBProd.setBackground(Star.colBot);

    }//GEN-LAST:event_jBProdMouseEntered

    
    /*Cuando el mouse sale del campo de bùsqueda global*/
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(colOri);

    }//GEN-LAST:event_jBProdMouseExited

    
    /*Cuando se presiona el botón de bùsqueda global*/
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProd.getText(), 2, jTProd, jTDescrip, jTAlma2, "", null);
        b.setVisible(true);

        /*Pon el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();
        
        /*Procesa esta función para garantizar que el producto coloco todos sus campos en la forma*/
        vCargP();

    }//GEN-LAST:event_jBProdActionPerformed

    
    /*Cuando se presiona una tecla en el campo de bùsqueda global*/
    private void jBProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBProdKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del producto*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);
        jTProd.setSelectionEnd(jTProd.getText().length());

    }//GEN-LAST:event_jTProdFocusGained

    
    /*Función para limpiar todos los campos del producto*/
    private void vLimpP()
    {
        /*Resetea los campos*/
        jComAlma.setSelectedItem("");
        jTCodOp1.setText        ("");
        jTCost.setText          ("$0.00");
        jTCodOpl2.setText       ("");
        jTExist.setText         ("");
        jTCodProv1.setText      ("");
        jTLot.setText           ("");
        jTPedimen.setText       ("");
        jTCodProv2.setText      ("");
        jTDescripAlma.setText   ("");
        jTDescrip.setText       ("");        
        jComUnid.setSelectedItem("");
        jComImp.setSelectedItem ("");    
        jTCant.setText          ("1");
        jTCarSer.setText        ("");
        //cambio alan
        jTProd.setText          ("");
        
        /*La imágen no será visible*/
        jLImg.setVisible        (false);
        
//        /*Selecciona la fecha para la fecha de caducidad al día de hoy*/
        Date f  = new Date();
        jDFCadu.setDate(f);
    }
    
    
    /*Cuando se pierde el foco del teclado en el campo del producto*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Haz toda esta parte en una funciòn aparte*/
        vCargP();

    }//GEN-LAST:event_jTProdFocusLost

    
    /*Cuando se pirde el foco del teclado en el campo del producto*/
    private void vCargP()
    {
        /*Coloca el caret en la posiciòn 0*/
        jTProd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        /*Coloca el caret al principio del control*/
        jTProd.setCaretPosition(0);
        
        //Abre la base de datos        
        Connection  con = Star.conAbrBas(true, false);  

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Obtiene todos los datos del producto en base a su código y si no existe activa la bandera*/
        try
        {
            sQ = "SELECT prods.UNID, prods.DESCRIP, prods.IMPUE, prods.PRODOP2, prods.PROVOP1, prods.PROVOP2,  prods.COST, prods.COSTRE, prods.PRODOP1, prods.PRODOP2, prods.EXIST, almas.ALMADESCRIP FROM prods LEFT JOIN almas ON almas.ALMA = prods.alma WHERE prods.PROD = '" + jTProd.getText().trim() + "' AND prods.ALMA = '" + jTAlma2.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Dale formato de moneda al último costo*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                double dCant    = Double.parseDouble(Star.sUltCost(con, jTProd.getText().trim()));                
                String sCost    = n.format(dCant);

                /*Colocalos en los campos correspondientes*/                
                jTCodOp1.setText        (rs.getString("prods.PRODOP1"));
                jTCodOpl2.setText       (rs.getString("prods.PRODOP2"));
                jTCodProv1.setText      (rs.getString("prods.PROVOP1"));
                jTCodProv2.setText      (rs.getString("prods.PROVOP2"));
                jTCost.setText          (sCost);
                jTExist.setText         (Star.sCantVisuaGKT(rs.getString("prods.UNID"), rs.getString("prods.EXIST")));
                jTDescripAlma.setText   (rs.getString("almas.ALMADESCRIP"));
                jTDescrip.setText       (rs.getString("prods.DESCRIP"));
                jComImp.setSelectedItem (rs.getString("prods.IMPUE"));
                jComUnid.setSelectedItem (rs.getString("prods.UNID"));

                /*Selecciona la moneda nacional*/
                jComMon.setSelectedItem ("MN");

                /*Coloca algunos campos al principio*/
                jTCodOpl2.setCaretPosition      (0);
                jTCodProv1.setCaretPosition     (0);                
                jTDescrip.setCaretPosition      (0);
                jTCodOp1.setCaretPosition       (0);
                jTCodProv2.setCaretPosition     (0);
                jTDescripAlma.setCaretPosition  (0);
                jTDescripUnid.setCaretPosition  (0);
                jTValImp.setCaretPosition       (0);                
            }
            /*Else, no existe entonces llama a la función para borrar los campos de los productos*/
            else
               vLimpP();
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
                Star.vGetImg(jTProd.getText(), jLImg);

                /*Muestra que se termino de cargar la imágen*/
                jLImg.setText("");
            }
            
        }).start();
        
    }/*Fin de vCargP*/            
    
    
    /*Cuando se presiona una tecla en el campo del producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTProd.getText(), 2, jTProd, jTDescrip, jTAlma2, "", null);
            b.setVisible(true);
            
            /*Procesa esta función para garantizar que el producto coloco todos sus campos en la forma*/
            vCargP();
        }
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

    
    /*Cuando se gana el foco del teclado en el campo del producto*/
    private void jTCodProv1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProv1FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodProv1.setSelectionStart(0);jTCodProv1.setSelectionEnd(jTCodProv1.getText().length());

    }//GEN-LAST:event_jTCodProv1FocusGained

    
    /*Cuando se presiona una tecla en el campo del producto*/
    private void jTCodProv1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodProv1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCodProv1KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del producto opcional 2*/
    private void jTCodProv2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProv2FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodProv2.setSelectionStart(0);jTCodProv2.setSelectionEnd(jTCodProv2.getText().length());

    }//GEN-LAST:event_jTCodProv2FocusGained

    
    /*Cuando se presiona una tecla en el campo del producto opcional 2*/
    private void jTCodProv2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodProv2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCodProv2KeyPressed

    
    /*Cuando se presiona una tecla en el panel 2*/
    private void jPanel2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPanel2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del nùmero de folio*/
    private void jTNoDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoDocFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTNoDoc.setSelectionStart(0);jTNoDoc.setSelectionEnd(jTNoDoc.getText().length());

    }//GEN-LAST:event_jTNoDocFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del nùmero de folio*/
    private void jTNoDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoDocFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTNoDoc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTNoDoc.getText().compareTo("")!=0)
            jTNoDoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTNoDocFocusLost

    
    /*Cuando se presiona una tecla en el campo del folio*/
    private void jTNoDocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoDocKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTNoDocKeyPressed

    
    /*Cuando se libera una tecla en el campo del folio*/
    private void jTNoDocKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoDocKeyReleased

        /*Vuelve a colocarlo*/
        jTNoDoc.setText(jTNoDoc.getText().toUpperCase());

    }//GEN-LAST:event_jTNoDocKeyReleased

    
    /*Cuando se gana el foco del teclado en el campo del nombre del proveedor*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length());

    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se presiona una tecla en el campo del nombre del proveedor*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTNomKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del código del proveedor*/
    private void jTProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusGained
               
        /*Selecciona todo el texto cuando gana el foco*/
        jTProv.setSelectionStart(0);jTProv.setSelectionEnd(jTProv.getText().length());

    }//GEN-LAST:event_jTProvFocusGained

    
    /*Carga todos los datos del proveedor*/
    private void vCargProv()
    {
        /*Esconde el label de total saldo y el control igual*/
        jLTotSald.setVisible(false);
        jTTotSald.setVisible(false);
        
        /*Coloca el caret en la posiciòn 0*/
        jTProv.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTProv.getText().compareTo("")!=0)
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el código del proveedor es vacio entonces*/
        if(jTProv.getText().compareTo("")==0)
        {
            /*Resetea los campos y regresa*/
            jTSer.setText       ("");
            jTNom.setText       ("");
            jTNoDoc.setText     ("");
            jTCond.setText      ("");
            jTCo1.setText       ("");
            jTCo2.setText       ("");
            jTCo3.setText       ("");
            jTMetPag.setText    ("");
            jTCta.setText       ("");
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

        /*Obtiene los datos del proveedor*/
        try
        {
            sQ = "SELECT ser, co1, co2, co3, bloq, nom, diacred, limcred, metpag, cta FROM provs WHERE CONCAT_WS('', ser, prov )  = '" + jTProv.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Función para mostrar las condiciones en las que estamos con el proveedor*/
                vCondP(con, rs.getString("limcred"), rs.getString("diacred"));
                                
                /*Coloca los correos en su lugar*/
                jTNom.setText(rs.getString  ("nom"));
                jTCo1.setText                   (rs.getString("co1"));
                jTCo2.setText                   (rs.getString("co2"));
                jTCo3.setText                   (rs.getString("co3"));
                
                /*Coloca la serie en su lugar*/
                jTSer.setText                   (rs.getString("ser"));

                /*Coloca el método de pago y la cuenta*/
                jTMetPag.setText                (rs.getString("metpag"));
                jTCta.setText                   (rs.getString("cta"));
                
                /*Coloca los cursores al principio de los controles*/
                jTCo1.setCaretPosition          (0);
                jTCo2.setCaretPosition          (0);
                jTCo3.setCaretPosition          (0);

                /*Si esta bloqueado entonces*/
                if(rs.getString("bloq").compareTo("1")==0)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "El proveedor: " + jTProv.getText() + " esta bloqueado, no se le puede comprar.", "Compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                    return;
                }
            }
            /*Else, no hay datos entonces*/
            else
            {
                /*Resetea todos los campos*/
                jTNoDoc.setText     ("");
                jTNom.setText       ("");
                jTCond.setText      ("");
                jTCo1.setText       ("");
                jTCo2.setText       ("");
                jTCo3.setText       ("");
                jTMetPag.setText    ("");
                jTCta.setText       ("");
            }
        }
        catch(SQLException expnNumForm)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnNumForm.getMessage(), Star.sErrNumForm, expnNumForm.getStackTrace(), con);                                                       
            return;                                                                                    
        }

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vCargProv()*/
        
        
    /*Cuando se pierde el foco del teclado en el ampo del código del proveedor*/
    private void jTProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusLost
        
        /*Limpia toda la tabla de partidas de compra*/            
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);

        /*Resetea los totales*/
        jTSubTot.setText    ("$0.00");
        jTImp.setText       ("$0.00");
        jTTot.setText       ("$0.00");                

        /*Resetea el contador de filas*/
        iContFi = 1;

        /*Limpia la parte de los productos*/
        //vLimpP();
        
        //Regresa el comando a la normalidad
        jCOrd.setEnabled(true);
            
        /*Carga todo lo referente al proveedor*/
        vCargProv();
                        
    }//GEN-LAST:event_jTProvFocusLost

    
    /*Función para mostrar las condiciones en las que estamos con el proveedor*/
    private void vCondP(Connection con, String sLimit, String sDiCred)
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
        
        /*Dale formato de moneda al límite de crédito*/
        java.text.NumberFormat n  = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        double dCant    = Double.parseDouble(sLimit);        
        sLimit          = n.format(dCant);
           
        /*Obtiene las condiciones*/
        jTCond.setText("Días: " + sDiCred + " Crédito: " + sLimit);
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene el saldo que tiene pendiente de pagar el proveedor*/
        String sPendPag = "";
        try
        {
            sQ = "SELECT IFNULL((SUM(carg) - SUM(abon)),0) AS pendpag FROM cxp WHERE prov = '" + jTProv.getText().trim() + "' AND concep <> 'NOT'";                        
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
        
        /*Obtiene el saldo que tiene disponible el cliente*/
        String sSaldDispo   = Double.toString(Double.parseDouble(sLimit.replace("$", "").replace(",", "")) - Double.parseDouble(sPendPag));
    
        /*Que sea visible el label de tip ode venta*/
        jLTipVta.setVisible(true);
        
        //Comprueba si el proveedor no esta bloqueado
        int iRes    = Star.iGetBloqCredCliProv(con, "prov", jTProv.getText());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        //correcion carlos
        if(Integer.parseInt(sDiCred)==0)    
        {
            jCConta.setEnabled(false);
            jCConta.setSelected(true);
        }
        else
            jCConta.setEnabled(true);
        /*Si el cliente tiene saldo disponible y crédito habilitado entonces*/
        if(Double.parseDouble(sDiCred)>0 && iRes==0)
        {
            /*El label será a crédito*/
            jLTipVta.setText("COMPRA A CRÉDITO");        
            
            /*Desmarca el check de contado*/
            jCConta.setSelected(false);
        }
        /*Else es de contado entonces*/
        else
        {
            /*Coloca el label que es de contado*/
            jLTipVta.setText("COMPRA DE CONTADO");        
            
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
        
    }/*Fin de private void vCondP(Connection con, String sLimit, String sLimitCred)*/
                
                
    /*Cuando se presiona una tecla en el campo del código del producto*/
    private void jTProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyPressed
                    
        /*Si se presionó tecla de abajo entonces presiona el botón de búscar proveedor*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBProv.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else                    
            vKeyPreEsc(evt);
                    
    }//GEN-LAST:event_jTProvKeyPressed

    
    /*Cuando se tipea una tecla en el campo del código del proveedor*/
    private void jTProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        
    }//GEN-LAST:event_jTProvKeyTyped

    
    /*Cuando el mouse entra en el botón del producto*/
    private void jBProvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProvMouseEntered

        /*Cambia el color del fondo del botón*/
        jBProv.setBackground(Star.colBot);

    }//GEN-LAST:event_jBProvMouseEntered

    
    /*Cuando el mouse sale del botón del producto*/
    private void jBProvMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProvMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBProv.setBackground(colOri);

    }//GEN-LAST:event_jBProvMouseExited

    
    /*Cuando se presiona el botón del producto*/
    private void jBProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProvActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProv.getText(), 3, jTProv, jTNom, jTSer, "", null);
        b.setVisible(true);

        /*Pon el foco del teclado en el campo del proveedor*/
        jTProv.grabFocus();

        /*Carga todo lo referente al proveedor*/
        vCargProv();
        
    }//GEN-LAST:event_jBProvActionPerformed

    
    /*Cuando se presiona una tecla en el botón del producto*/
    private void jBProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProvKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBProvKeyPressed

    
    /*Cuando se presiona un tecla en el checkbox de contado*/
    private void jCContaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCContaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCContaKeyPressed

    
    /*Cuando se presiona una tecla en el check de correo 1*/
    private void jCCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCCo1KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del correo 1*/
    private void jTCo1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCo1.setSelectionStart(0);
        jTCo1.setSelectionEnd(jTCo1.getText().length());

    }//GEN-LAST:event_jTCo1FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del correo 1*/    
    private void jTCo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo1.setCaretPosition(0);
        
        /*Coloca el caret al principio del control*/
        jTCo1.setCaretPosition(0);

    }//GEN-LAST:event_jTCo1FocusLost

    
    /*Cuando se presiona una tecla en el campo del correo 1*/
    private void jTCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCo1KeyPressed

    
    /*Cuando se presiona una tecla en el campo del correo 2*/
    private void jCCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCCo2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del  correo 2*/
    private void jTCo2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCo2.setSelectionStart(0);
        jTCo1.setSelectionEnd(jTCo2.getText().length());

    }//GEN-LAST:event_jTCo2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del correo 2*/
    private void jTCo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCo2.setCaretPosition(0);
        
        /*Coloca el caret al principio del control*/
        jTCo2.setCaretPosition(0);

    }//GEN-LAST:event_jTCo2FocusLost

    
    /*Cuando se presiona uan tecla en el check de correo 2*/
    private void jTCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCo2KeyPressed

    
    /*Cuando se presiona una tecla en el check de correo 3*/
    private void jCCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo3KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo3KeyPressed

    
    /*Cuando se gana el foco del teclado en el check de correo 3*/
    private void jTCo3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCo3.setSelectionStart(0);
        jTCo1.setSelectionEnd(jTCo3.getText().length());

    }//GEN-LAST:event_jTCo3FocusGained

    
    /*Cuando se pirde el foco del teclado en el campo del correo 3*/
    private void jTCo3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo3.setCaretPosition(0);
        
        /*Coloca el caret al principio del control*/
        jTCo3.setCaretPosition(0);

    }//GEN-LAST:event_jTCo3FocusLost

    
    /*Cuando se presiona una tecla en el campo del correo 3*/
    private void jTCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo3KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCo3KeyPressed

    
    /*Cuando se presiona una tecla en el check de guardar datos*/
    private void jCGuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGuaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCGuaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del subtotal*/
    private void jTSubTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTSubTot.setSelectionStart(0);jTSubTot.setSelectionEnd(jTSubTot.getText().length());

    }//GEN-LAST:event_jTSubTotFocusGained

    
    /*Cuando se presiona una tecla en el campo del subtotal*/
    private void jTSubTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSubTotKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTSubTotKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del impuesto*/
    private void jTImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTImp.setSelectionStart(0);jTImp.setSelectionEnd(jTImp.getText().length());

    }//GEN-LAST:event_jTImpFocusGained

    
    /*Cuando se presiona una tecla en el campo del impuesto*/
    private void jTImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTImpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTImpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del total*/
    private void jTTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTTot.setSelectionStart(0);jTTot.setSelectionEnd(jTTot.getText().length());

    }//GEN-LAST:event_jTTotFocusGained

    
    /*Cuando se presiona una tecla en el campo del total*/
    private void jTTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTTotKeyPressed

    
    /*Cuando se presiona el botón de ver tabla en grande*/
    private void jBTab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab1ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab);       
        
    }//GEN-LAST:event_jBTab1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver tabla en grande*/
    private void jBTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTab1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBTab1KeyPressed

    
    /*Cuando el mouse entra en el label de ayuda*/
    private void jLAyuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLAyuMouseEntered

        /*Cambia el cursor del ratón*/
        this.setCursor( new Cursor(Cursor.HAND_CURSOR));

    }//GEN-LAST:event_jLAyuMouseEntered

    
    /*Cuando el mouse sale del label de ayuda*/
    private void jLAyuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLAyuMouseExited

        /*Cambia el cursor del ratón al que tenía*/
        this.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));

    }//GEN-LAST:event_jLAyuMouseExited

    
    /*Cuando el mouse entra en el botón de marcar todo*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered

        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);

    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el mouse sale del botón de marcar todo*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);

    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando se presiona el botón de marcar todo*/
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

    
    /*Cuando se presiona una tecla en el botón de marcar todo*/
    private void jBTodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTodKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBTodKeyPressed

    
    /*Cuando el mouse entra en el botón de nueva partida*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered

        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);

    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse sale del botón de nueva partida*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(colOri);

    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando se presiona el botón de nueva partida*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
        
        /*Si no a seleccionado un proveedor válido entonces*/
        if(jTNom.getText().compareTo("")==0 && jTProv.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un proveedor primeramente.", "Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTProv.grabFocus();
            return;
        }

        /*Si el código del producto es cadena vacia entonces*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un producto primeramente.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
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
        
        /*Si la cantidad es cadena vacia entonces*/
        if(jTCant.getText().trim().compareTo("")==0)
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
        if(Double.parseDouble(jTCant.getText().trim()) < 1)
        {
            /*Coloca el borde rojo*/
            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0.", "Cantidad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCant.grabFocus();
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
        
        /*Si el costo es cadena vacia entonces*/
        if(jTCost.getText().replace("$", "").replace(",", "").compareTo("")==0)
        {
            /*Coloca el borde rojo*/
            jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Indica un costo primero.", "Costo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCost.grabFocus();
            return;
        }

        /*Si el costo es 0 entonces*/
        if(Double.parseDouble(jTCost.getText().replace("$", "").replace(",", ""))== 0)
        {
            /*Coloca el borde rojo*/
            jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El costo es 0.", "Costo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTCost.grabFocus();
            return;
        }        
         
        //Si el control de orden no esta bloqueado entonces
        if(jCOrd.isEnabled())
        {
            //Seleciona el letrero necesario
            String sTipo = "Compra";
            if(jCOrd.isSelected())
                sTipo = "Orden de compra";
            
            //Se le pide confirmacion para bloquear el control del tipo de compra
            Object[] op = {"Si","No"};
            int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres que la partida sea una " + sTipo + "?", "Determina tipo de compra", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
            if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                return;

            //Se bloque el check
            jCOrd.setEnabled(false);
        }
        
        //Abre la base de datos        
        Connection  con = Star.conAbrBas(true, false);  

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables locales
        int iCont           = 1;
        boolean bNesSer     = false;
        
        //Se obtiene la cantidad del producto
        String sCanEnt      = jTCant.getText().trim();

        //Si el producto necesita a fuerzas serie entonces al contador se le da la cantidad para que inserte esas series
        if(Star.iProdSolSer(con, jTProd.getText().trim())==1 && !jCOrd.isSelected())
        {
            //Se obtiene el numero de partidas
            iCont   = Integer.parseInt(jTCant.getText().trim());
            sCanEnt = "1";
            bNesSer = true;
        }
            
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
            for(int i = 0; i < iCont ; i++)
                vAgreProd(sCanEnt,"","");  
        }
        else
            vSerDoc(jTCarSer.getText().trim());
        
        /*Función para limpiar todos los campos del producto*/
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
        catch(FileNotFoundException expnFilNotFoun)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnFilNotFoun.getMessage(), Star.sErrFilNotFoun);                                                       
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
            
            //Abre la base de datos        
            Connection  con = Star.conAbrBas(true, false);  

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Se revisa si se puede repetir series
            if(Star.iConfSer(con)==0)
            {
                //Se comprueba si la serie ya existe
                if(Star.iSerRep(con, sSer)==1 || Star.vSerRepTab(sSer,jTab, 17, iContFi)==1)
                {
                    //Se suma al contador de repetidos
                    iContRep++;
                    continue;
                }                
            }
            
            //Valida la serie
            sSer   = Star.sValSer(sSer.toUpperCase());
            
            //Si no es valida la serie entonces
            if(sSer==null)
            {
                //Se suma al contador de repetidos y continua
                iContRep++;
                continue;
            }
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            if(sSer.trim().compareTo("") != 0)
                vAgreProd("1",sSer,sComen);

        }/*Fin de while(rowIt.hasNext())*/ 
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Se almacenaron: " + (iConta-1-iContRep) + " series y hubo: " + iContRep + " error(es)", "Éxito al leer archivo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
    
    }//Final private int iContSerDoc(String sRut)
    
    
    //Esta funcion es la misma que se tenia para cargar el producto
    private void vAgreProd(String sCanEnt,String sCamp1, String sCamp2) 
    {  
        
        //Abre la base de datos        
        Connection  con = Star.conAbrBas(true, false);  

        //Si hubo error entonces regresa
        if(con==null)
            return;
                
        //Se crean variables
        String sSer     ="";
        String sComen   ="";
        
        /*Lee el costo*/
        String sCost                           = jTCost.getText().replace("$", "").replace(",", "");
                
        //Para evitar un proceso mas primero verifica si es orden 
        if(!jCOrd.isSelected())
        {
            //Si el producto necesita a fuerzas serie 
            if(Star.iProdSolSer(con, jTProd.getText().trim())==1)
            {
                boolean bSer = true;

                while(bSer)
                {
                    //Si no hay archivo entonces
                    if(jTCarSer.getText().trim().compareTo("")==0)
                    {
                        /*Pidele al usuario que ingrese serie y comentario*/   
                        SelSer lo     = new SelSer(jTSerProd,jTComenSer, iCantAgr + "-" + iCantTot, jTProd.getText().trim(),jComAlma.getSelectedItem().toString().trim(),0);                        
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

                        //Se revisa si se puede repetir series
                        if(Star.iConfSer(con)==0)
                        {
                            //Se comprueba si la serie ya existe
                            if(Star.iSerRep(con, sSer)==1)
                            {
                                /*Mensajea*/
                                JOptionPane.showMessageDialog(null, "La serie " + sSer + " ya existe.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                                continue;
                            }

                            //Se comprueba si la serie ya existe
                            if(Star.vSerRepTab(sSer,jTab, 17, iContFi)==1)
                            {
                                /*Mensajea*/
                                JOptionPane.showMessageDialog(null, "La serie " + sSer + " ya existe en esta compra.", "Serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                                continue;
                            }
                        }//Fin if(Star.iConfSer(con)==0)

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
                
            }//Fin if(Star.iProdSolSer(con, jTProd.getText().trim())==1)
            
        }//Fin if(!jCOrd.isSelected())
                
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ;

        /*Comprueba si el producto es un kit*/
        String sKit = "";
        try
        {
            sQ = "SELECT compue FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sKit      = rs.getString("compue");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                    
        }        
        
        /*Si es un kit entonces*/
        if(sKit.compareTo("1")==0)
        {
            //Obtiene si el producto tiene componentes
            double dRes     = Star.dGetCompsProd(con, jTProd.getText().trim());
            
            //Si hubo error entonces regresa
            if(dRes==-1)
                return ;

            //Si tiene componentes entonces coloca la bandera
            boolean bSiHay  = false;
            if(dRes>0)
                bSiHay      = true;
            
            /*Si no tiene componentes entonces*/
            if(!bSiHay)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return ;

                /*Coloca el borde rojo*/                               
                jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "No tiene componentes este kit y no se puede cargar.", "Kits", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
                /*Coloca el foco del teclado en el campo del código del producto y regresa*/
                jTProd.grabFocus();                                
                return ;
            }        
            
        }/*Fin de if(sKit.compareTo("1")==0)*/	                                            
        
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
                    return ;
            
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El producto requiere de lote o pedimento. Ingresa alguno de estos datos primeramente.", "Lote y pedimetno", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Coloca el borde rojo*/
                jTLot.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Coloca el foco del teclado en el contorl y regresa*/
                jTLot.grabFocus();
                return ;
            }
        }
        
//        /*Obtiene cuál es la moneda nacional*/
//        String sCodMN   = Star.sGetMonNac(con);
//        
//        //Si hubo error entonces regresa
//        if(sCodMN==null)
//            return ;
//        
//        /*Obtiene si la configuración global para que no se pueda comprar a ningùn proveedor con moneda distinta a la nacional esta activada o no*/
//        boolean bSi = true;
//        try
//        {
//            sQ = "SELECT val FROM confgral WHERE clasif = 'comps' AND conf = 'otramon'";
//            st = con.createStatement();
//            rs = st.executeQuery(sQ);
//            /*Si hay datos*/
//            if(rs.next())
//            {
//                /*Marca o desmarca la bandera dependiendo del valor*/
//                if(rs.getString("val").compareTo("0")==0)
//                    bSi = false;
//            }
//        }
//        catch(SQLException expnSQL)
//        {
//            //Procesa el error y regresa
//            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
//            return;                                                                
//        }
//
//        /*Comprueba si el proveedor tiene o no habilitado que se le pueda comprar con otra moneda distinta a la nacional*/
//        try
//        {
//            sQ = "SELECT otramon FROM provs WHERE CONCAT_WS('', ser, prov) = '" + jTProv.getText().trim() + "'";
//            st = con.createStatement();
//            rs = st.executeQuery(sQ);
//            /*Si hay datos*/
//            if(rs.next())
//            {
//                /*Si no tiene habilitado la compra con otra moneda o globalmente entonces*/
//                if(rs.getString("otramon").compareTo("0")==0 || !bSi)
//                {
//                    /*Compara el código de moneda nacional con el que se quiere agregar*/
//                    if(sCodMN.compareTo(jComMon.getSelectedItem().toString())!=0)
//                    {
//                        //Cierra la base de datos
//                        if(Star.iCierrBas(con)==-1)
//                            return ;
//
//                        /*Coloca el borde rojo*/
//                        jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
//
//                        /*Mensajea*/
//                        JOptionPane.showMessageDialog(null, "No se tiene permitido comprar con una Moneda distinta a la Nacional \"" + sCodMN + "\".", "Compras", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
//
//                        /*Coloca el foco del teclado en el control y regresa*/
//                        jComMon.grabFocus();
//                        return ;
//                    }
//                }
//            }
//        }
//        catch(SQLException expnSQL)
//        {
//            //Procesa el error y regresa
//            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
//            return;                                                                
//        }

        /*Comprueba si la unidad tomada es la que tiene el producto asignada*/        
        String sSiUnid = Star.sEsUnidProd(con, jTProd.getText().trim(), jComUnid.getSelectedItem().toString().trim());        
        
        /*Si hubo error entonces regresa*/
        if(sSiUnid==null)
            return ;
        
        /*Obtiene la unidad del producto*/        
        String sUnidProd = Star.sGetUnidProd(con, jTProd.getText().trim());        
        
        /*Si hubo error entonces regresa*/
        if(sUnidProd==null)
            return ;
        
        /*Si la unidad no es la misma que tiene el producto entonces*/
        if(sSiUnid.compareTo("0")==0)
        {
            /*Comprueba si la unidad que se quiere usar es alguna unidad base de la unidad del producto*/
            if(!Star.bEsUnidBas(sUnidProd, jComUnid.getSelectedItem().toString().trim()))
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return ;

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La unidad que se quiere manejar: " + jComUnid.getSelectedItem().toString().trim() + " no es base de la unidad: " + sUnidProd + " original del producto y no se puede realizar el movimiento", "Unidades", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return ;                
            }
            
        }/*Fin de if(!bSiUnid)*/
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return ;

        /*Si el costo tiene formato de moneda quitaselo*/
        sCost                           = sCost.replace("$", "").replace(",", "");

        /*Lee el descuento*/
        String sDesc                    = jTDesc.getText().trim();

        /*Si el descuento no es cadena vacia entonces obtiene el descuento del costo*/
        String sDescConvert;
        if(sDesc.compareTo("")!= 0)
            sDescConvert                = Double.toString((Double.parseDouble(sDesc) / 100 ) * Double.parseDouble(sCost));
        /*Else, colocalo en 0 para poder hacer la resta*/
        else
            sDescConvert                = "0";

        /*Lee el descuento adicional*/
        String sDescAd                  = jTDescAd.getText();

        /*Si el descuento adicional no es cadena vacia entonces obtiene el descuento adicional del precio ya con el descuento*/
        String sDescAdConvert;
        if(sDescAd.compareTo("")        != 0)
            sDescAdConvert              = Double.toString((Double.parseDouble(sDescAd) / 100 ) * (Double.parseDouble(sCost) - Double.parseDouble(sDescConvert)));
        /*Else, colocalo en 0 para poder hacer la resta*/
        else
            sDescAdConvert              = "0";

        
        /*Lee la cantidad*/
        String sCant                           = sCanEnt; 
        
        /*Crea el importe*/
        String sImp                     = Double.toString(Double.parseDouble(sCant) * (Double.parseDouble(sCost) - Double.parseDouble(sDescConvert) - Double.parseDouble(sDescAdConvert)));
        
        //Se obtiene valor del impuesto y validalo
        String sValImp=jTValImp.getText().trim();
        if(sValImp.compareTo("")==0)
            sValImp="0";
        
        //Crea el importe del impuesto
        String sImpoImpue               = Double.toString(Double.parseDouble(sImp) * (Double.parseDouble(sValImp) / 100));

        /*Dale formato de moneda al importe*/        
        double dCant                    = Double.parseDouble(sImp);
        java.text.NumberFormat n        = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        sImp                            = n.format(dCant);

        /*Dale formato de moneda al costo*/
        dCant                           = Double.parseDouble(sCost);
        n                               = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        sCost                           = n.format(dCant);

        /*Obtiene la fecha de entrega*/    
        String sFCadu;
        try
        {
            Date fe                 =  jDFCadu.getDate();
            SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");
            sFCadu                   = sdf.format(fe);      
        }
        catch(NullPointerException expnNullPoint)
        {
            /*Pon el foco del teclado en el control*/
            jDFech.grabFocus();
                    
            /*Mensajea y regresa*/   
            JOptionPane.showMessageDialog(null, "Fecha de entrega incorrecta.", "Fecha", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return ;
        }                                
        
        //Declara variables locales
        boolean bSi = false;      
        int iCant2;
        
        /*Recorre toda la tabla de partidas de compra*/        
        for(int row = 0 ; row < jTab.getRowCount(); row++)
        {
            /*Si la partida que va a insertar el usuario ya existe entonces*/            
            if(jTab.getValueAt(row, 4).toString().trim().compareTo(jComAlma.getSelectedItem().toString().trim())==0 && jTab.getValueAt(row, 1).toString().trim().compareTo(jTProd.getText().trim())==0 && Double.parseDouble(jTab.getValueAt(row, 7).toString().trim())==Double.parseDouble(sDesc.trim())&& Double.parseDouble(jTab.getValueAt(row, 8).toString().trim())==Double.parseDouble(sDescAd.trim()) && Double.parseDouble(sCost.replace("$", "").replace(",", ""))==Double.parseDouble(jTab.getValueAt(row, 6).toString().replace("$", "").replace(",", "")) && jComImp.getSelectedItem().toString().trim().compareTo(jTab.getValueAt(row, 9).toString().trim())==0 && jTab.getValueAt(row, 3).toString().trim().compareTo(jComUnid.getSelectedItem().toString().trim())==0 && jTab.getValueAt(row, 12).toString().trim().compareTo(jComTall.getSelectedItem().toString().trim())==0 && jTab.getValueAt(row, 13).toString().trim().compareTo(jComColo.getSelectedItem().toString().trim())==0 && jTab.getValueAt(row, 14).toString().trim().compareTo(jTLot.getText().trim())==0 && jTab.getValueAt(row, 15).toString().trim().compareTo(jTPedimen.getText().trim())==0 && jTab.getValueAt(row, 16).toString().trim().compareTo(sFCadu)==0 && jTab.getValueAt(row, 17).toString().trim().compareTo(sSer)==0)
            {
                /*Obtiene la cantidad que tiene originalmente en la fila*/
                iCant2                  = Integer.parseInt(jTab.getValueAt(row, 2).toString());

                /*Deja la cantidad  correcta*/
                sCant                   = Integer.toString(Integer.parseInt(sCant) + iCant2);

                /*Si el impo tiene el signo de dollar quitaselo*/
                sImp                    = sImp.replace("$", "").replace(",", "");

                /*Crea el impo correcto multiplicandolo por la cantidad*/
                sImp                    = Double.toString(Double.parseDouble(sImp) * Integer.parseInt(sCant));

                //Crea el importe del impuesto
                sImpoImpue              = Double.toString(Double.parseDouble(sImp) * (Double.parseDouble(jTab.getValueAt(row, 10).toString().trim()) / 100));
                        
                //Coloca el importe del impuesto en su lugar
                jTab.setValueAt(sImpoImpue, row, 20);
                
                /*Vuelve a darle formato al importe*/
                n                       = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
                dCant                   = Double.parseDouble(sImp);
                sImp                    = n.format(dCant);

                /*Pon la bandera para saber que si existe ya en la tabla*/
                bSi                     = true;

                /*Modifica la cantidad y el costo solamente en la fila en donde esta el registro*/
                jTab.setValueAt(sCant, row, 2);
                jTab.setValueAt(sImp, row, 11);

                /*Sal del bucle*/
                break;
            }

        }/*Fin de for(int row = 0; x < jTablePartidas.getRowCount(); x++)*/
            
        /*Si el producto no existe en la tabla entonces*/
        if(!bSi)
        {
            /*Determina si debe de poner o no el comentario de la serie*/
            String sComenSer    = sComen.trim();
            if(sSer.trim().compareTo("")==0)
            
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
            DefaultTableModel te = (DefaultTableModel)jTab.getModel();
            Object nu[]= {Integer.toString(iContFi), jTProd.getText(), sCant, jComUnid.getSelectedItem().toString(), jComAlma.getSelectedItem().toString(), jTDescrip.getText().trim() + " " + sSerProd + " " + sComenSer + " " + sLotC + " " + sPedimen, sCost, sDesc, sDescAd, jComImp.getSelectedItem().toString(), sValImp, sImp, jComTall.getSelectedItem().toString(), jComColo.getSelectedItem().toString(), jTLot.getText(), jTPedimen.getText(), sFCadu, sSer.trim(), sComenSer, jTGaran.getText().trim(), sImpoImpue};
            te.addRow(nu);

            /*Aumenta el contador de filas en uno*/
            iContFi ++;
        }

        /*Recalcula los totes leyendo toda la tabla de partidas de compras*/
        vRecalTot();
        
        return ;

    }
    
    /*Cuando se presiona una tecla en el botòn de nueva partida*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando el mouse entra en el botòn de borrar partida*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered

        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);

    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse sale del botòn de borrar partida*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);

    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando se presiona el botòn de borrar partida*/
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

        /*Recalcula los totes leyendo toda la tabla de partidas*/
        vRecalTot();

    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar partida*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la fecha d*/
    private void jDFEntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFEntKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDFEntKeyPressed

    
    /*Cuando sucede una acción en el check de órden de compra*/
    private void jCOrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCOrdActionPerformed

        /*Si esta seleccionado entonces*/
        if(jCOrd.isSelected())
        {
            /*Haz invisible todo lo de órden de compra*/
            vOrdCtrl(true);

            /*Coloca el label de nueva compra*/
            jLNot.setText("NUEVA ORDEN DE COMPRA");            
            
            /*Esconde el label de tipo de compra*/
            jLTipVta.setVisible(false);
            
            //Se vuelve vacio el espacio para el documento de las series
            jTCarSer.setEditable(false);
            jTCarSer.setText    ("");
            jBCarSer.setEnabled (false);
        }
        /*Else no esta marcado entonces*/
        else
        {
            /*Haz visible todo lo de órden de compra*/
            vOrdCtrl(false);

            /*Coloca el label de nueva compra*/
            jLNot.setText("NUEVA COMPRA");
            
            /*Muestra el label de tipo de compra*/
            jLTipVta.setVisible(true);
            
            //Se vuelve a la normalidad para el ingreso de series
            jTCarSer.setEditable(true);
            jBCarSer.setEnabled (true);
         
        }

        /*Vuelve a cargar el cobmbox de las series*/        
        vCargSer();            
        
    }//GEN-LAST:event_jCOrdActionPerformed

    
    /*Cuando se presiona una tecla en el check de la órden*/
    private void jCOrdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCOrdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCOrdKeyPressed

    
    /*Cuando se presiona uan tecla en el check de imprimir*/
    private void jCImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCImpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCImpKeyPressed

    
    /*Cuando se pierde el foco del teclado en el combo de la serie*/
    private void jComSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerFocusLost

        /*Coloca el borde negro si tiene datos*/
        if(jComSer.getSelectedItem().toString().compareTo("")!=0)
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComSerFocusLost

    
    /*Cuando se presiona una tecla en el combo de la serie*/
    private void jComSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComSerKeyPressed

    
    /*Cuando se presiona una tecla en el control de la fecha de*/
    private void jDFechKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFechKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jDFechKeyPressed

    
    /*Cuando se presiona una tecla en el panel de encabezado*/
    private void jPEncaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPEncaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jPEncaKeyPressed

    
    /*Cuando el mouse entra en el botón de ver en grande*/
    private void jBVeGranMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGranMouseEntered

        /*Cambia el color del fondo del botón*/
        jBVeGran.setBackground(Star.colBot);

    }//GEN-LAST:event_jBVeGranMouseEntered

    
    /*Cuadno se presiona el botón de ver en imágen en grande*/
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
                    java.awt.Desktop.getDesktop().open(new File(sCarp + "\\" + sArch[0]));
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

    
    /*Cuando el mouse entra en el panel de la imágen*/
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

    
    /*Cuando el mouse sale del panel de la imágen*/
    private void jPanImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImgMouseExited

        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);

    }//GEN-LAST:event_jPanImgMouseExited

    
    /*Cuando se presiona una tecla en el panel de la imágen*/
    private void jPanImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanImgKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPanImgKeyPressed

    
    /*Cuando se presiona una tecla en el scroll de la imágen*/
    private void jSImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSImgKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jSImgKeyPressed

    
    /*Cuando se presiona una tecla en el panel 1*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando el mouse entra en el botòn de ùltimos precios*/
    private void jBUltPreMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUltPreMouseEntered

        /*Cambia el color del fondo del botón*/
        jBUltPre.setBackground(Star.colBot);

    }//GEN-LAST:event_jBUltPreMouseEntered

    
    /*Cuando se pierde el foco del teclado en el mouse de ùltimos precios*/
    private void jBUltPreMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUltPreMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBUltPre.setBackground(colOri);

    }//GEN-LAST:event_jBUltPreMouseExited

    
    /*Cuando se presiona el botòn de ùltimos precios*/
    private void jBUltPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUltPreActionPerformed

        /*Muestra la forma para ver los últimos precios*/
        UltPrecs u = new UltPrecs(jTProv.getText(), jTProd.getText(), "comp", false);
        u.setVisible(true);

    }//GEN-LAST:event_jBUltPreActionPerformed

    
    /*Cuando se presiona una tecla en el botòn de ùltimos precios*/
    private void jBUltPreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUltPreKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBUltPreKeyPressed

    
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
    private void jTCondFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCondFocusLost
       
        /*Coloca el caret en la posiciòn 0*/
        jTCond.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCondFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCodOp1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodOp1FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCodOp1.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodOp1FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCodProv1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProv1FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCodProv1.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodProv1FocusLost
    
    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/    
    private void jTCodOpl2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodOpl2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCodOpl2.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodOpl2FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCodProv2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProv2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCodProv2.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCodProv2FocusLost

        
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTDescripUnidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripUnidFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescripUnid.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripUnidFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTValImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTValImpFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTValImp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTValImpFocusLost

        
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTExistFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExistFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTExist.setCaretPosition(0);
        
    }//GEN-LAST:event_jTExistFocusLost

    
    /*Cuando sucede una acción en el combo de las series de las compras*/
    private void jComSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComSerActionPerformed
        
        /*Determina que tipo de documento es*/
        String sTip = "COMP";
        if(jCOrd.isSelected())
            sTip    = "ORDC";
                 
        //Abre la base de datos        
        Connection  con = Star.conAbrBas(true, false);  

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "consecs", "WHERE tip = '" + sTip + "' AND ser = '" + jComSer.getSelectedItem() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComSer.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
                        
    }//GEN-LAST:event_jComSerActionPerformed

    
    
    /*Cuando se presiona una tecla en el combo de los colores*/
    private void jComColoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComColoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComColoKeyPressed

    
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

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComTallActionPerformed

    
    /*Cuando se gana el foco del teclaod en el campo de la descripción de la talla*/
    private void jTDescripTallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripTallFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripTall.setSelectionStart(0);jTDescripTall.setSelectionEnd(jTDescripTall.getText().length());
        
    }//GEN-LAST:event_jTDescripTallFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del color*/
    private void jTDescripColoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripColoFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripColo.setSelectionStart(0);jTDescripColo.setSelectionEnd(jTDescripColo.getText().length());
        
    }//GEN-LAST:event_jTDescripColoFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción de la talla*/
    private void jTDescripTallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripTallFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescripTall.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripTallFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del color*/
    private void jTDescripColoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripColoFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescripColo.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripColoFocusLost

    
    /*Cuando se presioan una tecla en el campo de la descripción de la talla*/
    private void jTDescripTallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripTallKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripTallKeyPressed

    
    /*Cuando se presona una tecla en la descripción del color*/
    private void jTDescripColoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripColoKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripColoKeyPressed

    
    /*Cuando sucede una acción en el combo de los colores*/
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

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComColoActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de lote*/
    private void jTLotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLotFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTLot.setSelectionStart(0);jTLot.setSelectionEnd(jTLot.getText().length());

    }//GEN-LAST:event_jTLotFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del lote*/
    private void jTLotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLotFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTLot.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jTLot.getText().compareTo("")!=0)
            jTLot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTLotFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del pedimento*/
    private void jTPedimenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPedimenFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTPedimen.setSelectionStart(0);jTPedimen.setSelectionEnd(jTPedimen.getText().length());

    }//GEN-LAST:event_jTPedimenFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del pedimento*/
    private void jTPedimenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPedimenFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPedimen.setCaretPosition(0);

    }//GEN-LAST:event_jTPedimenFocusLost

    
    /*Cuando se presiona una tecla en el campo del pedimento*/
    private void jTPedimenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPedimenKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTPedimenKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la fecha de caducidad*/
    private void jDFCaduKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFCaduKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jDFCaduKeyPressed

    
    /*Cuando se presiona una tecla en el campo del lote*/
    private void jTLotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTLotKeyPressed

    
    /*Cuando se presiona una tecla en el botón de ver en grande descripción*/
    private void jBGranDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGranDescripKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGranDescripKeyPressed

    
    /*Cuando se presiona el botón de ver descripción en grande*/
    private void jBGranDescripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGranDescripActionPerformed
        
        /*Muestar la forma para ver la descripción en grande*/
        DescripGran b = new DescripGran(jTDescrip, null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBGranDescripActionPerformed

    
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
    
    
    /*Cuando se presiona una tecla en el combobox de tallas*/
    private void jComTallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComTallKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComTallKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la garantía*/
    private void jTGaranFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTGaranFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTGaran.setSelectionStart(0);jTCant.setSelectionEnd(jTCant.getText().length());
        
    }//GEN-LAST:event_jTGaranFocusGained

    
    /*Cuando se pierde el foco del teclaod en le campo de la garantía*/
    private void jTGaranFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTGaranFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTGaran.setCaretPosition(0);
        
    }//GEN-LAST:event_jTGaranFocusLost

    
    /*Cuando se presiona una tecla en el campo de la garantía*/    
    private void jTGaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTGaranKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTGaranKeyPressed

    
    /*Cuando se presiona una tecla en el botón de últimos costos pero de todos los proveedores*/
    private void jBUltCostTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBUltCostTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBUltCostTKeyPressed

    
    /*Cuando el mouse entra en el botón de últimos costos de todos los proveedores*/
    private void jBUltCostTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUltCostTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBUltCostT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBUltCostTMouseEntered

    
    /*Cuando el mouse sale del botón de últimos costos de todos los proveedores*/
    private void jBUltCostTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBUltCostTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBUltCostT.setBackground(colOri);
        
    }//GEN-LAST:event_jBUltCostTMouseExited

    
    /*Cuando se presiona el botón de últimos costos de todos los proveedores*/
    private void jBUltCostTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUltCostTActionPerformed
        
        /*Si no a ingresado un producto entonces*/
        if(jTDescrip.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa un producto primero para poder ver los últimos costos de todos los proveedores.", "Ultimos Costos Proveedores", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el campo del producto y regresa*/
            jTDescrip.grabFocus();
            return;
        }
        
        /*Muestra la forma para ver los últimos precios de todos los proveedores*/
        UltPrecs u = new UltPrecs(jTProv.getText(), jTProd.getText(), "comp", true);
        u.setVisible(true);
        
    }//GEN-LAST:event_jBUltCostTActionPerformed

    
    /*Cuando se pierde el foco del tecldo en el combo del almacén*/
    private void jComAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComAlmaFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComAlma.getSelectedItem().toString().compareTo("")!=0)
            jComAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        //Se carga existencias de almacen
        

    }//GEN-LAST:event_jComAlmaFocusLost

    
    /*Cuando sucede una acción en el combo del almacén*/
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

    
    /*Cuando se presiona una tecla en el combo del almacén*/
    private void jComAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComAlmaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción*/
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

    
    /*Cuando se presiona una tecla en el botón de existencias por almacén*/
    private void jBExisAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBExisAlmaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBExisAlmaKeyPressed


    //Método para procesar la selección o deselección del check de contado
    private void vCheckConta()
    {
        //Si esta seleccionado entonces coloca el label que será de contado
        if(jCConta.isSelected())                                                    
            jLTipVta.setText("COMPRA DE CONTADO");                
        else
        {
            //Comprueba si el proveedor no esta bloqueado
            int iRes    = Star.iGetBloqCredCliProv(null, "prov", jTProv.getText());

            //Si hubo error entonces regresa
            if(iRes==-1)
                return;
            
            //Si el crédito lo tiene bloqueado entonces
            if(iRes==1)
            {
                //Mensajea
                JOptionPane.showMessageDialog(null, "El proveedor: " + jTProv.getText().trim() + " tiene el crédito bloqueado y solo puede ser de contado.", "Crédito", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Selecciona nuevamente el control y regresa
                jCConta.setSelected(true);
                return;
            }
            
            //Coloca el texto de crédito
            jLTipVta.setText(sLabTex);        
        }
        
    }//Fin de private void vCheckConta()
    
    
    /*Cuando sucede una acción en el check de contado*/
    private void jCContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCContaActionPerformed
        
        //Procesa en una función la selección o deselección del check de contado
        vCheckConta();
        
    }//GEN-LAST:event_jCContaActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo del método de pago*/
    private void jTMetPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTMetPag.setSelectionStart(0);jTMetPag.setSelectionEnd(jTMetPag.getText().length());

    }//GEN-LAST:event_jTMetPagFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del método de pago*/
    private void jTMetPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMetPagFocusLost

        /*Coloca el caret al principio del control*/
        jTMetPag.setCaretPosition(0);

    }//GEN-LAST:event_jTMetPagFocusLost

    
    /*Cuando se presiona una tecla en el campo del método de pago*/
    private void jTMetPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMetPagKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTMetPagKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta de pago*/
    private void jTCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCta.setSelectionStart(0);jTCta.setSelectionEnd(jTCta.getText().length());

    }//GEN-LAST:event_jTCtaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta de pago*/
    private void jTCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaFocusLost

        /*Coloca el caret al principio del control*/
        jTCta.setCaretPosition(0);

    }//GEN-LAST:event_jTCtaFocusLost

    
    /*Cuando se presiona una tecla en el campo de la cuenta de pago*/
    private void jTCtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCtaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la serie del proveedor*/
    private void jTSerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSer.setSelectionStart(0);jTSer.setSelectionEnd(jTSer.getText().length());
        
    }//GEN-LAST:event_jTSerFocusGained

    
    /*Cuando se presiona una tecla en el campo de la serie del proveedor*/
    private void jTSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSerKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de las condiciones de pago*/
    private void jTCondFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCondFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCond.setSelectionStart(0);jTCond.setSelectionEnd(jTCond.getText().length());
        
    }//GEN-LAST:event_jTCondFocusGained

    
    /*Cuando se presiona una tecla en el campo de las condiciones*/
    private void jTCondKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCondKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCondKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del total de saldo a pagar*/
    private void jTTotSaldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotSaldFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTTotSald.setSelectionStart(0);jTTotSald.setSelectionEnd(jTTotSald.getText().length());

    }//GEN-LAST:event_jTTotSaldFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del saldo total*/
    private void jTTotSaldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotSaldFocusLost

        /*Coloca el caret al principio del control*/
        jTTotSald.setCaretPosition(0);

    }//GEN-LAST:event_jTTotSaldFocusLost

    
    /*Cuando se presiona una tecla en el campo del total del saldo*/
    private void jTTotSaldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotSaldKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTTotSaldKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de monedas*/
    private void jComMonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComMonFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComMon.getSelectedItem().toString().compareTo("")!=0)
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComMonFocusLost

    
    /*Cuando el mouse sale del botón de ver imágen en grande*/
    private void jBVeGranMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGranMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBVeGran.setBackground(colOri);
        
    }//GEN-LAST:event_jBVeGranMouseExited

    
    //Cuando el mouse entra en el botón de cargar archivos de compra
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

    
    //Cuando el mouse sale del botón de cargar archivos de compra
    private void jBCargMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBCarg.setBackground(colOri);

        /*Si el visor no es nulo entonces escondelo*/
        if(filVi!=null)
            filVi.setVisible(false);
        
    }//GEN-LAST:event_jBCargMouseExited

    
    //Cuando se presiona el botón de cargar archivos de compra
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
        fc.setDialogTitle               ("Buscar archivo(s)");
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

    
    //Cuando se presiona una tecla en el botón de cargar archivos de compra
    private void jBCargKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCargKeyPressed


    //Cuando el mouse entra en el botón de limpiar archivos de compra
    private void jBLimpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimpMouseEntered

        /*Cambia el color del fondo del botón*/
        jBLimp.setBackground(Star.colBot);

    }//GEN-LAST:event_jBLimpMouseEntered

    
    //Cuando el mouse sale del botón de limpiar archivos de compra
    private void jBLimpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimpMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBLimp.setBackground(colOri);

    }//GEN-LAST:event_jBLimpMouseExited

    
    //Cuando se presiona el botón de limpiar archivos de compra
    private void jBLimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimpActionPerformed

        /*Inicia el arreglo en nulo otra vez y el contador*/
        sRuts   = null;
        iXGlo   = 0;

    }//GEN-LAST:event_jBLimpActionPerformed

    
    //Cuando se presiona una tecla en el botón de limpiar archivos de compra
    private void jBLimpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLimpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBLimpKeyPressed

    
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

    
    //Cuando se presiona una tecla en el combo de al forma de pago
    private void jComFormPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComFormPagKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComFormPagKeyPressed

    
    //Cuando el mouse entra en el botón de tipo de cambio
    private void jBTipCamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTipCamMouseEntered

        /*Cambia el color del fondo del botón*/
        jBTipCam.setBackground(Star.colBot);

    }//GEN-LAST:event_jBTipCamMouseEntered

    
    //Cuando el mouse sale del botón de cambiar tipo de cambio
    private void jBTipCamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTipCamMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBTipCam.setBackground(colOri);

    }//GEN-LAST:event_jBTipCamMouseExited

    
    //Cuando se presiona el botón de cambiar el tipo de cambio
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
                //Cierra la base de datos
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

    
    //Cuando se presiona una tecla en el botón de cambiar tipo de cambio
    private void jBTipCamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTipCamKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTipCamKeyPressed


    //Cuando se gana el foco en el campo de descuentos
    private void jDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDescFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jDesc.setSelectionStart(0);jDesc.setSelectionEnd(jDesc.getText().length());

    }//GEN-LAST:event_jDescFocusGained

    
    //Cuando se pierde el foco en el campo de descuentos
    private void jDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDescFocusLost
        
         /*Coloca el caret en la posiciòn 0*/
        jDesc.setCaretPosition(0);
        
    }//GEN-LAST:event_jDescFocusLost

    
    //Cuando se presiona una tecla en el campo de descuentos
    private void jDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDescKeyPressed
        
         //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDescKeyPressed

	
    private void jTSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTSerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTSerActionPerformed
    

    //Cuando el mouse entra en el botòn de cargar serie
    private void jBCarSerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCarSerMouseEntered

        /*Cambia el color del fondo del botón*/
        jBCarSer.setBackground(Star.colBot);

    }//GEN-LAST:event_jBCarSerMouseEntered

    
    //Cuando el mouse sale del botòn de cargar serie
    private void jBCarSerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCarSerMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBCarSer.setBackground(colOri);

    }//GEN-LAST:event_jBCarSerMouseExited

    
    //Cuando se presiona el botòn de cargar series
    private void jBCarSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCarSerActionPerformed

        /*Configura el file chooser para escoger la ruta del directorio donde esta el archivo*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar archivo con series");
        fc.setAcceptAllFileFilterUsed               (false);

        //Si el usuario no presiono aceptar entonces regresa
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

    
    //Cuando se presiona una tecla en el botòn de cargar serie
    private void jBCarSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCarSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCarSerKeyPressed

    
    //Cuando se gana el foco del teclado en el campo de la serie
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

    private void jTValImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTValImpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTValImpActionPerformed
//Cuando el comando de existencias por almacen gana el foco
    private void jTExistAlmaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExistAlmaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/ 
    }//GEN-LAST:event_jTExistAlmaFocusGained

    
    //Cuando el comando de existencias por almacen ierde el foco 
    private void jTExistAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExistAlmaFocusLost
       
        /*Coloca el caret en la posiciòn 0*/
    }//GEN-LAST:event_jTExistAlmaFocusLost


    //Cuandp0 se preciona una tecla en el campo de existencias por almacen
    private void jTExistAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExistAlmaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTExistAlmaKeyPressed

    private void jTCondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTCondActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTCondActionPerformed

    private void jTProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTProvActionPerformed
     private void jCGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCGuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCGuaActionPerformed

    private void jTGaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTGaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTGaranActionPerformed

             
   
    /*Válida si será una órden de compra o compra*/
    private void vOrdCom()
    {
        //Si ya esta bloqueado el control de òrden de compra entonces regresa ya que no puede cambiar
        if(!jCOrd.isEnabled())
            return;
        
        /*Si esta seleccionado entonces*/
        if(jCOrd.isSelected())
        {
            /*Haz invisible todo lo de órden de compra*/
            vOrdCtrl(false);

            /*Desmarca el check de órden*/
            jCOrd.setSelected(false);

            /*Coloca el label de nueva compra*/
            jLNot.setText("NUEVA COMPRA");
        }
        /*Else no esta marcado entonces*/
        else
        {
            /*Haz visible todo lo de órden de compra*/
            vOrdCtrl(true);

            /*Marca el check de órden*/
            jCOrd.setSelected(true);   

            /*Coloca el label de nueva compra*/
            jLNot.setText("NUEVA ORDEN DE COMPRA");
        }

        /*Vuelve a cargar el cobmbox de las series*/        
        vCargSer();            
        
    }/*Fin de private void vOrdCom()*/
    
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar el formulario entonces presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();            
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
            jBTod.doClick();
        /*Si se presiona F8 entonces procesa la selección y deselección del check de contado*/
        else if(evt.getKeyCode() == KeyEvent.VK_F8 )
            vCheckConta();        
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        /*Si se presiona CTRL + P entonces*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_P)
        {
            /*Si esta seleccionado el check de imprimir entonces desmarcalo y viceversa*/
            if(jCImp.isSelected())
                jCImp.setSelected(false);
            else
                jCImp.setSelected(true);        
        }
        /*Si se presiona Alt + G entonces*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_G)
        {
            /*Si esta seleccionado entonces desmarcalo y viceversa*/
            if(jCGua.isSelected())
                jCGua.setSelected(false);
            else
                jCGua.setSelected(true);
        }            
        /*Si se presiona la tecla de F2 entonces*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
        {
            /*Muestra la forma para aplicar nota de crédito al cliente*/            
            BuscComp b = new BuscComp(this, "", null, "notp", jTab, jTProv.getText(), iContFi);
            b.setVisible(true);
            
            /*Recalcula los totales leyendo toda la tabla de partidas*/
            vRecalTot();                            
        }
        /*Si se presiona Alt + O entonces válida si será órden de compra o compra*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_O)
            vOrdCom();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();                
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCarSer;
    private javax.swing.JButton jBCarg;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBExisAlma;
    private javax.swing.JButton jBGranDescrip;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBLimp;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBProd;
    private javax.swing.JButton jBProv;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTipCam;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBUltCostT;
    private javax.swing.JButton jBUltPre;
    private javax.swing.JButton jBVeGran;
    private javax.swing.JCheckBox jCCo1;
    private javax.swing.JCheckBox jCCo2;
    private javax.swing.JCheckBox jCCo3;
    private javax.swing.JCheckBox jCConta;
    private javax.swing.JCheckBox jCGua;
    private javax.swing.JCheckBox jCImp;
    private javax.swing.JCheckBox jCOrd;
    private javax.swing.JComboBox jComAlma;
    private javax.swing.JComboBox jComColo;
    private javax.swing.JComboBox jComFormPag;
    private javax.swing.JComboBox jComImp;
    private javax.swing.JComboBox jComMon;
    private javax.swing.JComboBox jComSer;
    private javax.swing.JComboBox jComTall;
    private javax.swing.JComboBox jComUnid;
    private com.toedter.calendar.JDateChooser jDFCadu;
    private com.toedter.calendar.JDateChooser jDFEnt;
    private com.toedter.calendar.JDateChooser jDFech;
    private javax.swing.JTextField jDesc;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLEnt;
    private javax.swing.JLabel jLGua;
    private javax.swing.JLabel jLGua1;
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
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPEnca;
    private javax.swing.JPanel jPanImg;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jSImg;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTAlma2;
    private javax.swing.JTextField jTCant;
    private javax.swing.JTextField jTCarSer;
    private javax.swing.JTextField jTCo1;
    private javax.swing.JTextField jTCo2;
    private javax.swing.JTextField jTCo3;
    private javax.swing.JTextField jTCodOp1;
    private javax.swing.JTextField jTCodOpl2;
    private javax.swing.JTextField jTCodProv1;
    private javax.swing.JTextField jTCodProv2;
    private javax.swing.JTextField jTComenSer;
    private javax.swing.JTextField jTCond;
    private javax.swing.JTextField jTCost;
    private javax.swing.JTextField jTCta;
    private javax.swing.JTextField jTDesc;
    private javax.swing.JTextField jTDescAd;
    private javax.swing.JTextField jTDescrip;
    private javax.swing.JTextField jTDescripAlma;
    private javax.swing.JTextField jTDescripColo;
    private javax.swing.JTextField jTDescripTall;
    private javax.swing.JTextField jTDescripUnid;
    private javax.swing.JTextField jTExist;
    private javax.swing.JTextField jTGaran;
    private javax.swing.JTextField jTImp;
    private javax.swing.JTextField jTLot;
    private javax.swing.JTextField jTMetPag;
    private javax.swing.JTextField jTNoDoc;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTPedimen;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTProv;
    private javax.swing.JTextField jTSer;
    private javax.swing.JTextField jTSerProd;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTextField jTTotSald;
    private javax.swing.JTextField jTValImp;
    private javax.swing.JTable jTab;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */