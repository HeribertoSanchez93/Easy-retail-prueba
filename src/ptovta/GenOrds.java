//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;




/*Clase para hacer ordenes de compra*/
public class GenOrds extends javax.swing.JFrame 
{
    /*Declara variables originales*/
    private String          sCodOri;   
    private String          sCantOri;       
    private String          sProdOri;   
    private String          sAlmaOri;   
    private String          sUnidOri;   
    private String          sDescripOri;  
    private String          sMonOri;  
    private String          sDescOri;   
    private String          sDescAdOri;   
    private String          sImpueOri;   
    private String          sValmpueOri;       
    private String          sUltCostOri;   
    private String          sImpoOri;   
    private String          sImpoImpueOri;   
    
    /*Declara variables de instancia*/        
    private int             iContCelE;
    private int             iContFi;
    private String          sCodProGlo;
    private String          sCostGlo;
    private JTable          jTabFalt;
    
    /*Declara variables privadas referentes a los datos de la empresa local*/
    private String          sNomLoc             = "";
    private String          sCallLoc            = "";
    private String          sTelLoc             = "";
    private String          sColLoc             = "";
    private String          sCPLoc              = "";
    private String          sCiuLoc             = "";
    private String          sEstLoc             = "";
    private String          sPaiLoc             = "";
    private String          sRFCLoc             = "";

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;
    
    
    
    
    
    /*Constructor sin argumentos*/
    public GenOrds(String[] sFal, String sCodP, JTable jTabFal) 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicializa el contador de filas en uno*/
        iContFi      = 1;        
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Establece el campo de fecha para que solo se pueda modificar con el botón*/
        jDFEnt.getDateEditor().setEnabled(false);
        
        /*Crea el grupo para los radio buttons*/
        ButtonGroup  g  = new ButtonGroup();
        g.add(jREje1);
        g.add(jREje2);
        
        /*Selecciona la fecha del día de hoy para la posible de entrega*/
        Date f = new Date();
        jDFEnt.setDate(f);  
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Nueva ordén de compra, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Obtiene los datos del otro formulario*/
        sCodProGlo      = sCodP;
        jTabFalt        = jTabFal;
                
        /*Recorre todo el arreglo de strings que se pasarón del otro formulario*/
        for (String sFal1 : sFal) 
        {            
            /*Declara variables*/
            String sCodCot;
            String sCant;
            String sProd;
            String sAlma;
            String sUni;
            String sMon;
            String sDescrip     = "";
            String sCost        = "";
            String sImpuest     = "";
            String sImp;
                        
            /*Obtiene los datos tokenizando la cadena*/
            StringTokenizer st1 = new StringTokenizer(sFal1, "<>");            
            sCodCot             = st1.nextToken();
            sCant               = st1.nextToken();
            sProd               = st1.nextToken();
            sAlma               = st1.nextToken();
            sMon                = st1.nextToken();
            sUni                = st1.nextToken();
            
            //Abre la base de datos                             
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;            
            String      sQ;
            
            /*Obtiene algunos datos del producto*/
            String sImpVal  = "";            
            try
            {
                sQ = "SELECT prods.DESCRIPCORT, prods.COST, prods.IMPUE, impues.IMPUEVAL FROM prods LEFT OUTER JOIN impues ON impues.CODIMPUE = prods.IMPUE WHERE prods.PROD = '" + sProd + "' AND prods.ALMA = '" + sAlma + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                while(rs.next())
                {
                    /*Obtiene los datos de la consulta*/
                    sDescrip        = rs.getString("prods.DESCRIPCORT");
                    sCost           = rs.getString("prods.COST");
                    sImpuest        = rs.getString("prods.IMPUE");
                    sImpVal         = rs.getString("impues.IMPUEVAL");
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
            
            /*Genera el impo*/
            sImp            = Double.toString(Double.parseDouble(sCant) * Double.parseDouble(sCost));
                        
            /*Genera el impue tot del producto*/
            String sImpTot  = Double.toString((Double.parseDouble(sImp) * (Double.parseDouble(sImpVal) / 100)));
            
            /*Dale formato de mon al cost*/            
            double dCant    = Double.parseDouble(sCost);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sCost           = n.format(dCant);
            
            /*Dale formato de mon al impo*/
            dCant           = Double.parseDouble(sImp);                            
            sImp            = n.format(dCant);
            
            /*Dale formato de mon al impue tot*/
            dCant           = Double.parseDouble(sImpTot);                            
            sImpTot         = n.format(dCant);

            /*Agrega el registro en la tabla*/
            DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
            Object nu[]             = {iContFi, sCodCot, sCant, sProd, sAlma, sUni, sDescrip, sMon, 0, 0, sImpuest,sImpVal, sCost, sImp, sImpTot};
            te.addRow(nu);
            
            /*Aumenta en uno el contador de filas*/
            ++iContFi;
            
        }/*Fin de for (String sFal1 : sFal) */                                        
        
        /*Recalcula los totales leyendo toda la tabla de partidas*/
        vRecalTot();                
            
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nom de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el campo del código del producto*/
        jTProv.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla*/
        //jTabParts.getColumnModel().getColumn(2).setPreferredWidth(250);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Incializa el contador del cell editor*/
        iContCelE = 1;
                    
        /*Listener para el combobox de mons*/
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
        
        /*Listener para el combobox de unids*/
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

                /*Borra los items en el combobox de unids*/
                jComUni.removeAllItems();

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 

                /*Obtiene todas las unids actualizadas y cargalas en el combobox*/
                try
                {
                    sQ = "SELECT * FROM unids";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces cargalos en el combobox*/
                    while(rs.next())
                        jComUni.addItem(rs.getString("unid"));
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
        
        /*Listener para el combobox de impues*/
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
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene todos los datos de la empresa local*/
        try
        {                  
            sQ = "SELECT nom, calle, tel, col, cp, ciu, estad, pai, rfc FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene todos los datos de la consulta*/
                sNomLoc             = rs.getString("nom");                                    
                sCallLoc            = rs.getString("calle");                                    
                sTelLoc             = rs.getString("tel");                                    
                sColLoc             = rs.getString("col");                                    
                sCPLoc              = rs.getString("cp");                                    
                sCiuLoc             = rs.getString("ciu");                                    
                sEstLoc             = rs.getString("estad");                                    
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
        
        /*Trae todos los códigos de las unids de la base de datos*/
        boolean bSi = false;
        try
        {                  
            sQ = "SELECT unid FROM unids";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Si existen unids ya en la base de datos entonces marca la bandera*/
                if(rs.getString("unid").compareTo("")!=0)
                    bSi = true;
                
                /*Agrega el código de la unid al combobox correspondiente*/                
                jComUni.addItem(rs.getString("unid"));                                                                      
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                    
        }
        
        /*Si no existen aún unids entonces mensajea*/
        if(!bSi)
            JOptionPane.showMessageDialog(null, "No existen unids dados de alta.", "Unidades", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                         
                
        /*Trae todos los códigos de las monedas de la base de datos*/
        if(Star.iCargMonCom(con, jComMon)==-1)
            return;
        
        //Carga todos los impuestos en el combo
        if(Star.iCargImpueCom(con, jComImp)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Crea el listener para la tabla de partidas, para cuando se modifique una celda hacer los cálculos*/
        PropertyChangeListener proChanLi; 
        proChanLi = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                //Declara variables locales                
                String  sCant;
                String  sUltCost;
                String  sDescrip;
                String  sTotImp;
                String  sImpVal;
                String  sDesc;
                String  sDescAd;
                
                /*Obtén la propiedad que a sucedio en el control*/
                String pr = event.getPropertyName();
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pr)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCelE==1)
                    {
                        /*Obtén algunos datos originales*/                       
                        if(jTab.getSelectedRow()==-1)
                            return;
                        sCodOri                 = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sCantOri                = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sProdOri                = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sAlmaOri                = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sUnidOri                = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sDescripOri             = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sMonOri                 = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();                        
                        sDescOri                = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sDescAdOri              = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sDescAdOri              = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sUltCostOri             = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();                                                                                  
                        sImpoOri                = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();                                                                                  
                        sImpoImpueOri           = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();                                                                                  
                        
                        /*Quitale al cost original el formato de mon*/
                        sUltCostOri             = sUltCostOri.replace("$", "").replace(",", "");                        
                        
                        /*Aumenta en uno el contador*/
                        ++iContCelE;                                                                                
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {                        
                        /*Obtén los datos que pueden cambiar*/                        
                        sCant                   = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sDescrip                = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sDesc                   = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sDescAd                 = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();                                                          
                        sImpVal                 = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        sUltCost                = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();                                                
                     
                        /*Coloca los valores originales en sus campos*/
                        jTab.setValueAt         (sCodOri,       jTab.getSelectedRow(), 1);
                        jTab.setValueAt         (sProdOri,      jTab.getSelectedRow(), 3);
                        jTab.setValueAt         (sAlmaOri,      jTab.getSelectedRow(), 4);
                        jTab.setValueAt         (sUnidOri,      jTab.getSelectedRow(), 5);
                        jTab.setValueAt         (sDescripOri,   jTab.getSelectedRow(), 6);
                        jTab.setValueAt         (sMonOri,       jTab.getSelectedRow(), 7);
                        jTab.setValueAt         (sUltCostOri,   jTab.getSelectedRow(), 12);
                        jTab.setValueAt         (sImpoOri,      jTab.getSelectedRow(), 13);
                        jTab.setValueAt         (sImpoImpueOri, jTab.getSelectedRow(), 14);                        
                        
                        /*Quitale al cost el formato de mon*/
                        sUltCost                = sUltCost.replace("$", "").replace(",", "");                        

                        /*Convierte la descripcion a mayusculas*/
                        sDescrip                = sDescrip.toUpperCase();                                            
                        
                        /*Si el campo excede la cant de caracteres permitidos recortalo*/
                        if(sDescrip.length()> 255)
                        {
                            sDescrip = sDescrip.substring(0, 255);
                        }
                        
                        /*Si el campo excede la cant de caracteres permitidos recortalo*/
                        if(sCant.length()> 21)
                        {
                            sCant = sCant.substring(0, 21);
                        }
                        
                        /*Si el campo excede la cant de caracteres permitidos recortalo*/
                        if(sDesc.length()> 21)
                        {
                            sDesc = sDesc.substring(0, 21);
                        } 
                        
                        /*Si el campo excede la cant de caracteres permitidos recortalo*/
                        if(sDescAd.length()> 21)
                        {
                            sDescAd = sDescAd.substring(0, 21);
                        } 
                        
                        /*Si el campo excede la cant de caracteres permitidos recortalo*/
                        if(sUltCost.length()> 21)
                        {
                            sUltCost = sCant.substring(0, 21);
                        }
                        
                        /*Coloca en la fila la descripción con mayúsculas*/
                        jTab.setValueAt(sDescrip, jTab.getSelectedRow(), 6);
                        
                        /*Si la cant nueva no es un número entonces*/
                        try
                        {                            
                            /*Intenta convertirlo en int*/
                            int iVal;
                            iVal = Integer.parseInt(sCant);
                            
                            /*Deja solo el valor absoluto*/
                            iVal  = Math.abs(iVal);
                            sCant = Integer.toString(iVal);
                            
                            /*Colocalo en el campo*/
                            jTab.setValueAt(Integer.toString(iVal), jTab.getSelectedRow(), 2);
                            
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca en el campo de cant la cant original*/
                            jTab.setValueAt(sCantOri, jTab.getSelectedRow(), 2);
                            
                            /*La cant será igual a la cant original*/
                            sCant = sCantOri;
                            
                        }
                        
                        /*Si la cant nueva es 0 entonces*/
                        if(Integer.parseInt(sCant)==0)
                        {
                            /*Coloca en el campo de cant la cant original*/
                            jTab.setValueAt(sCantOri, jTab.getSelectedRow(), 2);
                            
                            /*La cant será igual a la cant original*/
                            sCant = sCantOri;
                        }
                        
                        /*Si el descuento nuevo no es un número entonces*/
                        try
                        {                            
                            /*Intenta convertirlo en int*/
                            int iVal;
                            iVal = Integer.parseInt(sDesc);
                            
                            /*Deja solo el valor absoluto*/
                            iVal = Math.abs(iVal);
                            
                            /*Colocalo en el campo*/
                            jTab.setValueAt(Integer.toString(iVal), jTab.getSelectedRow(), 8);
                            
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca en el campo del descuento el original*/
                            jTab.setValueAt(sDescOri, jTab.getSelectedRow(), 8);
                            
                            /*El descuento será igual al original*/
                            sDesc = sDescOri;
                            
                        }
                        
                        /*Si el descuento es mayor a 100 entonces*/
                        if(Integer.parseInt(sDesc)>100)
                        {
                            /*Coloca en el campo del descuento el original*/
                            jTab.setValueAt(sDescOri, jTab.getSelectedRow(), 8);
                            
                            /*El descuento será igual al original*/
                            sDesc = sDescOri; 
                        }
                        
                        /*Si el descuento adicional nuevo no es un número entonces*/
                        try
                        {                            
                            /*Intenta convertirlo en int*/
                            int iVal;
                            iVal = Integer.parseInt(sDescAd);
                            
                            /*Deja solo el valor absoluto*/
                            iVal = Math.abs(iVal);
                            
                            /*Colocalo en el campo*/
                            jTab.setValueAt(Integer.toString(iVal), jTab.getSelectedRow(), 9);
                            
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca en el campo del descuento adiconal original*/
                            jTab.setValueAt(sDescAdOri, jTab.getSelectedRow(), 9);
                            
                            /*El descuento adicional será igual al original*/
                            sDescAd = sDescAdOri;
                            
                        }
                             
                        /*Si el descuento adiconal es mayor a 100 entonces*/
                        if(Integer.parseInt(sDescAd)>100)
                        {
                            /*Coloca en el campo del descuento adiconal original*/
                            jTab.setValueAt(sDescAdOri, jTab.getSelectedRow(), 9);
                            
                            /*El descuento adicional será igual al original*/
                            sDescAd = sDescAdOri;
                            
                        }
                        
                        /*Si el cost último no es un número entonces*/
                        try
                        {                            
                            /*Intenta convertirlo en int*/
                            double dVal;
                            dVal = Double.parseDouble(sUltCost);
                            
                            /*Deja solo el valor absoluto*/
                            dVal = Math.abs(dVal);
                            
                            /*Asigna el valor al último cost*/
                            sUltCost = Double.toString(dVal);                                                                                                                        
                            
                        }
                        catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca en el campo el cost original*/
                            jTab.setValueAt(sUltCostOri, jTab.getSelectedRow(), 12);
                            
                            /*El cost será igual al cost original*/
                            sUltCost = sUltCostOri;
                            
                        }                                                
                        
                        /*Si el cost último es 0 entonces*/
                        if(Double.parseDouble(sUltCost)==0)
                        {
                            /*Coloca en el campo el cost original*/
                            jTab.setValueAt(sUltCostOri, jTab.getSelectedRow(), 12);
                            
                            /*El cost será igual al cost original*/
                            sUltCost = sUltCostOri;
                        }
                        
                        /*Si el descuento no es cadena vacia entonces*/
                        String sDescConvert;
                        if(sDesc.compareTo("")!= 0)
                        {
                            /*Obtiene el descuento del cost*/
                            sDescConvert                = Double.toString((Double.parseDouble(sDesc) / 100 ) * Double.parseDouble(sUltCost));
                            
                        }
                        /*Else, colocalo en 0 para poder hacer la resta*/
                        else
                            sDescConvert                = "0";
                        
                        /*Si el descuento adicional no es cadena vacia entonces*/
                        String sDescAdConvert;
                        if(sDescAd.compareTo("")!= 0)
                        {
                            
                            /*Obtiene el descuento adicional del precio ya con el descuento*/
                            sDescAdConvert          = Double.toString((Double.parseDouble(sDescAd) / 100 ) * (Double.parseDouble(sUltCost) - Double.parseDouble(sDescConvert)));
                            
                        }
                        /*Else, colocalo en 0 para poder hacer la resta*/
                        else
                            sDescAdConvert              = "0";
                        
                        /*Calcula el nuevo impo*/
                        String sImp;
                        sImp            = Double.toString(Double.parseDouble(sCant) * (Double.parseDouble(sUltCost) - Double.parseDouble(sDescConvert) - Double.parseDouble(sDescAdConvert)));
                        
                        /*Calcula el nuevo tot del impue*/
                        sTotImp         = Double.toString((Double.parseDouble(sImp) * (Double.parseDouble(sImpVal) / 100)));
                        
                        /*Dale formato de mon al impo*/                                                
                        double dCant    = Double.parseDouble(sImp);                
                        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                        sImp            = n.format(dCant);
                        
                        /*Dale formato de mon al impo del impue*/
                        dCant           = Double.parseDouble(sTotImp);                                        
                        sTotImp         = n.format(dCant);
                        
                        /*Dale formato de mon al cost último*/
                        dCant           = Double.parseDouble(sUltCost);                                        
                        sUltCost        = n.format(dCant);
                        
                        /*Coloca el último cost en el campo*/
                        jTab.setValueAt(sUltCost, jTab.getSelectedRow(), 12);
                            
                        /*Coloca el nuevo impo en su lugar*/
                        jTab.setValueAt(sImp, jTab.getSelectedRow(), 13);
                        
                        /*Coloca el nuevo impo de impue en su lugar*/
                        jTab.setValueAt(sTotImp, jTab.getSelectedRow(), 14);
                        
                        /*Reinicia el conteo*/
                        iContCelE = 1;
                        
                        /*Recalcula los totales leyendo toda la tabla de partidas*/
                        vRecalTot();     
                        
                    }/*Fin de else*/                                                                                                                                                     
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */
            
        };
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(proChanLi);
        
    }/*Fin de public Colors() */

    
    
    /*Función para recalcular los totales*/
    private void vRecalTot()
    {
        //Declara variables locales
        String          sSubTot     = "0";
        String          sImpuest    = "0";                
        String          sTot; 
        NumberFormat    n;        
        
        //Declara variables de la base de datos
        Connection  con;          


        
        
        
        /*Si la tabla no tiene elementos entonces*/
        if(jTab.getRowCount()== 0 )
        {
            /*Colocar en los campos de subtot, impue y tot $0.00*/
            jTSubTot.setText            ("$0.00");
            jTImp.setText               ("$0.00");
            jTTot.setText               ("$0.00");            
        }
        /*Else, recorre toda la tabla para hacer los calculos*/
        else
        {
            //Abre la base de datos                             
            con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;
            
            /*Recalcula los importes de todas las partidas de la tabla*/
            for( int row = 0; row < jTab.getRowCount(); row++)
            {                                    
                /*Declara variables de bloque*/
                String sImpo;                
                String sValTot;

                /*Lee el tot del impue de la fila*/
                sValTot                     = jTab.getModel().getValueAt(row, 14).toString();                                 
                
                /*Si el tot del impue tiene formato de mon quitarcelo*/
                sValTot                     = sValTot.replace("$", "");                
                sValTot                     = sValTot.replace(",", "");
                
                /*Lee el impo de la fila*/
                sImpo                       = jTab.getModel().getValueAt(row, 13).toString();
                
                /*Si el impo tiene formato de mon quitarcelo*/
                sImpo                       = sImpo.replace("$", "");                
                sImpo                       = sImpo.replace(",", "");
                                
                /*Suma el impo de la fila al subtot acumulado*/
                sSubTot                     = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImpo));
                
                /*Suma el impue de la fila al impue acumulado*/
                sImpuest                    = Double.toString(Double.parseDouble(sImpuest) + Double.parseDouble(sValTot));                                

            }/*Fin de for( int row = 0; row < jTabParts.getRowCount(); row++)*/

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Calcula el total*/
            sTot                            = Double.toString(Double.parseDouble(sSubTot) + Double.parseDouble(sImpuest));
            
            /*Formatea a mon el subtot*/
            double dCant                    = Double.parseDouble(sSubTot);
            n                               = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sSubTot                         = n.format(dCant);
            
            /*Formatea a mon al impue*/
            dCant                           = Double.parseDouble(sImpuest);
            n                               = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sImpuest                        = n.format(dCant);
            
            /*Formatea a mon al tot*/
            dCant                           = Double.parseDouble(sTot);
            n                               = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sTot                            = n.format(dCant);

            /*Colocalos en los campos*/
            jTSubTot.setText    (sSubTot);
            jTImp.setText       (sImpuest);
            jTTot.setText       (sTot);

        }/*Fin de else*/
                                
    }/*Fin de private void vRecalTot(String sImpuest)*/
    
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBDel = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jTProv = new javax.swing.JTextField();
        jBBusc1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTNomProv = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTProd = new javax.swing.JTextField();
        jBBusc2 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTDescrip = new javax.swing.JTextField();
        jBGuar = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jTSubTot = new javax.swing.JTextField();
        jTImp = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTTot = new javax.swing.JTextField();
        jTUltCost = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTCant = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTAlma = new javax.swing.JTextField();
        jTDescripAlma = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTCodOp1 = new javax.swing.JTextField();
        jTCodProv1 = new javax.swing.JTextField();
        jTCodOpl2 = new javax.swing.JTextField();
        jTCodProv2 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTExist = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTDescripUnid = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jComImp = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        jTValImp = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTDesc = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jComMon = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jTDescripMon = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTDescAd = new javax.swing.JTextField();
        jComUni = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jDFEnt = new com.toedter.calendar.JDateChooser();
        jCMostA = new javax.swing.JCheckBox();
        jCCo1 = new javax.swing.JCheckBox();
        jCCo2 = new javax.swing.JCheckBox();
        jTCo3 = new javax.swing.JTextField();
        jTCo1 = new javax.swing.JTextField();
        jTCo2 = new javax.swing.JTextField();
        jCMand = new javax.swing.JCheckBox();
        jCCo3 = new javax.swing.JCheckBox();
        jCImp = new javax.swing.JCheckBox();
        jCGuaCo = new javax.swing.JCheckBox();
        jTEje2 = new javax.swing.JTextField();
        jTEje1 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jREje1 = new javax.swing.JRadioButton();
        jREje2 = new javax.swing.JRadioButton();
        jCGuaEje = new javax.swing.JCheckBox();
        jTDiaCred = new javax.swing.JTextField();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 120, 20));

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 510, 110, 30));

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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 120, 20));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Cotización", "Cantidad", "Cod. Producto", "Almacén", "Unidad", "Descripción ", "Moneda", "Descuento", "Desc. Adicional", "Impuesto", "Valor Impuesto", "Ultimo Costo", "Importe", "Importe Impuesto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 1180, 240));

        jTProv.setBackground(new java.awt.Color(204, 255, 204));
        jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProv.setNextFocusableComponent(jBBusc1);
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
        jP1.add(jTProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 90, 20));

        jBBusc1.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc1.setText("...");
        jBBusc1.setToolTipText("Buscar Proveedor(es)");
        jBBusc1.setNextFocusableComponent(jTProd);
        jBBusc1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBusc1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBusc1MouseExited(evt);
            }
        });
        jBBusc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBusc1ActionPerformed(evt);
            }
        });
        jBBusc1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBusc1KeyPressed(evt);
            }
        });
        jP1.add(jBBusc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 30, 20));

        jLabel3.setText("*Proveedor:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 90, -1));

        jTNomProv.setEditable(false);
        jTNomProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jP1.add(jTNomProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 670, 20));

        jLabel4.setText("Nombre Proveedor:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 110, -1));

        jTProd.setBackground(new java.awt.Color(255, 255, 153));
        jTProd.setToolTipText("Ctrl+B búsqueda avanzada");
        jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProd.setNextFocusableComponent(jBBusc2);
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
        jP1.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 90, 20));

        jBBusc2.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc2.setText("...");
        jBBusc2.setToolTipText("Buscar Producto(s)");
        jBBusc2.setNextFocusableComponent(jREje1);
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
        jP1.add(jBBusc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 30, 20));

        jLabel14.setText("Impuesto:");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 90, -1));

        jTDescrip.setEditable(false);
        jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jP1.add(jTDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 670, 20));

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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 110, 30));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel26.setText("Sub Total:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 510, 110, -1));

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
        jP1.add(jTSubTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 510, 160, 20));

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
        jP1.add(jTImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 530, 160, 20));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel27.setText("Impuesto:");
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 530, 110, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel28.setText("Total:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 550, 110, -1));

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
        jP1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 550, 160, 20));

        jTUltCost.setText("$0.00");
        jTUltCost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUltCost.setNextFocusableComponent(jTCant);
        jTUltCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUltCostFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUltCostFocusLost(evt);
            }
        });
        jTUltCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUltCostKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUltCostKeyTyped(evt);
            }
        });
        jP1.add(jTUltCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, 120, 20));

        jLabel15.setText("Descrip. Producto:");
        jP1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 110, -1));

        jLabel16.setText("Ultimo Costo:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, 100, -1));

        jTCant.setText("1");
        jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCant.setNextFocusableComponent(jTDesc);
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
        jP1.add(jTCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 180, 120, 20));

        jLabel17.setText("Cantidad:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 180, 90, -1));

        jTAlma.setEditable(false);
        jTAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAlma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAlmaFocusLost(evt);
            }
        });
        jP1.add(jTAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 120, 20));

        jTDescripAlma.setEditable(false);
        jTDescripAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripAlma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripAlmaFocusLost(evt);
            }
        });
        jP1.add(jTDescripAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 140, 120, 20));

        jLabel18.setText("Almacén:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 70, -1));

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
        jP1.add(jTCodOp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 120, 20));

        jTCodProv1.setEditable(false);
        jTCodProv1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jP1.add(jTCodProv1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 120, 20));

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
        jP1.add(jTCodOpl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 60, 120, 20));

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
        jP1.add(jTCodProv2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 120, 20));

        jLabel23.setText("Cod.Proveedor:");
        jP1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 80, 110, -1));

        jLabel5.setText("Cod. Opcional 2:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, 110, -1));

        jLabel20.setText("Fecha de Entrega:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 80, 120, -1));

        jTExist.setEditable(false);
        jTExist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(205, 205, 255)));
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
        jP1.add(jTExist, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 60, 160, 20));

        jLabel13.setText("Descripción Almacén:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 140, 120, -1));

        jTDescripUnid.setEditable(false);
        jTDescripUnid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jP1.add(jTDescripUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 120, 120, 20));

        jLabel24.setText("Cod. Opcional 1:");
        jP1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 110, -1));

        jLabel25.setText("Cod.Proveedor:");
        jP1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 110, -1));

        jLabel12.setText("Cod. Unidad:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 87, -1));

        jLabel21.setText("Descripción Unidad:");
        jP1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 120, 120, -1));

        jComImp.setNextFocusableComponent(jTUltCost);
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
        jP1.add(jComImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, 120, 20));

        jLabel29.setText("Valor Impuesto:");
        jP1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 160, 110, -1));

        jTValImp.setEditable(false);
        jTValImp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jP1.add(jTValImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 160, 120, 20));

        jLabel11.setText("Descuento%:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 100, -1));

        jTDesc.setText("0");
        jTDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDesc.setNextFocusableComponent(jTDescAd);
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
        jP1.add(jTDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 200, 120, 20));

        jLabel9.setText("Cod. Moneda:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 110, -1));

        jComMon.setNextFocusableComponent(jDFEnt);
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
        jP1.add(jComMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 220, 120, 20));

        jLabel8.setText("Descripción Moneda:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 220, 120, -1));

        jTDescripMon.setEditable(false);
        jTDescripMon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripMon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripMonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripMonFocusLost(evt);
            }
        });
        jTDescripMon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripMonKeyPressed(evt);
            }
        });
        jP1.add(jTDescripMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 220, 120, 20));

        jLabel30.setText("Desc. Adicional%:");
        jP1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 200, 110, -1));

        jTDescAd.setText("0");
        jTDescAd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescAd.setNextFocusableComponent(jComMon);
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
        jP1.add(jTDescAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, 120, 20));

        jComUni.setNextFocusableComponent(jComImp);
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
        jP1.add(jComUni, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, 120, 20));

        jLabel31.setText("Existencia:");
        jP1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 60, 100, -1));

        jDFEnt.setNextFocusableComponent(jCCo1);
        jDFEnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFEntKeyPressed(evt);
            }
        });
        jP1.add(jDFEnt, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 80, 160, -1));

        jCMostA.setBackground(new java.awt.Color(255, 255, 255));
        jCMostA.setText("Mostrar Archivo F6");
        jCMostA.setNextFocusableComponent(jCGuaCo);
        jCMostA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMostAKeyPressed(evt);
            }
        });
        jP1.add(jCMostA, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 200, 160, -1));

        jCCo1.setBackground(new java.awt.Color(255, 255, 255));
        jCCo1.setSelected(true);
        jCCo1.setText("Correo 1");
        jCCo1.setNextFocusableComponent(jTCo1);
        jCCo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo1KeyPressed(evt);
            }
        });
        jP1.add(jCCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 100, 120, -1));

        jCCo2.setBackground(new java.awt.Color(255, 255, 255));
        jCCo2.setText("Correo 2");
        jCCo2.setNextFocusableComponent(jTCo2);
        jCCo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo2KeyPressed(evt);
            }
        });
        jP1.add(jCCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 120, 120, -1));

        jTCo3.setNextFocusableComponent(jCMand);
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
        jP1.add(jTCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 140, 160, -1));

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
        jP1.add(jTCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 100, 160, -1));

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
        jP1.add(jTCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 120, 160, -1));

        jCMand.setBackground(new java.awt.Color(255, 255, 255));
        jCMand.setSelected(true);
        jCMand.setText("Mandar correo  F4");
        jCMand.setNextFocusableComponent(jCImp);
        jCMand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMandKeyPressed(evt);
            }
        });
        jP1.add(jCMand, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 160, 160, -1));

        jCCo3.setBackground(new java.awt.Color(255, 255, 255));
        jCCo3.setText("Correo 3");
        jCCo3.setNextFocusableComponent(jTCo3);
        jCCo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo3KeyPressed(evt);
            }
        });
        jP1.add(jCCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 140, 120, -1));

        jCImp.setBackground(new java.awt.Color(255, 255, 255));
        jCImp.setText("Imprimir F5");
        jCImp.setNextFocusableComponent(jCMostA);
        jCImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCImpKeyPressed(evt);
            }
        });
        jP1.add(jCImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 180, 160, -1));

        jCGuaCo.setBackground(new java.awt.Color(255, 255, 255));
        jCGuaCo.setText("Guardar correos F7");
        jCGuaCo.setNextFocusableComponent(jBNew);
        jCGuaCo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGuaCoKeyPressed(evt);
            }
        });
        jP1.add(jCGuaCo, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 220, 160, -1));

        jTEje2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEje2.setNextFocusableComponent(jCGuaEje);
        jTEje2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEje2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEje2FocusLost(evt);
            }
        });
        jTEje2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEje2KeyPressed(evt);
            }
        });
        jP1.add(jTEje2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 120, 20));

        jTEje1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEje1.setNextFocusableComponent(jREje2);
        jTEje1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEje1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEje1FocusLost(evt);
            }
        });
        jTEje1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEje1KeyPressed(evt);
            }
        });
        jP1.add(jTEje1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 120, 20));

        jLabel32.setText("Cod.Prod. :");
        jP1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 90, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jREje1.setBackground(new java.awt.Color(255, 255, 255));
        jREje1.setSelected(true);
        jREje1.setText("Ejecutivo 1:");
        jREje1.setNextFocusableComponent(jTEje1);
        jREje1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jREje1KeyPressed(evt);
            }
        });

        jREje2.setBackground(new java.awt.Color(255, 255, 255));
        jREje2.setText("Ejecutivo 2:");
        jREje2.setNextFocusableComponent(jTEje2);
        jREje2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jREje2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jREje2, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
            .addComponent(jREje1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jREje1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, Short.MAX_VALUE)
                .addComponent(jREje2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jP1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 100, 90, 50));

        jCGuaEje.setBackground(new java.awt.Color(255, 255, 255));
        jCGuaEje.setText("Guardar Ejecutivos");
        jCGuaEje.setNextFocusableComponent(jComUni);
        jCGuaEje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGuaEjeKeyPressed(evt);
            }
        });
        jP1.add(jCGuaEje, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 210, -1));

        jTDiaCred.setEditable(false);
        jTDiaCred.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDiaCred.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDiaCredFocusLost(evt);
            }
        });
        jP1.add(jTDiaCred, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 20, 180, 20));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1036, 600, 170, 20));

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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(1075, 252, 125, 18));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 1212, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una partida de la tabla.", "Borrar Partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();            
            return;            
        }
        
        /*Preguntar al usr si esta seguro de querer borrar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere borrar la(s) partida(s)?", "Borrar Partida", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        /*Recorre toda la selección del usr*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Borralo de la tabla*/            
            tm.removeRow(iSel[x]);

            /*Resta en uno el contador de filas el contador de filas en uno*/
            --iContFi;
        }
                
        /*Recalcula los totales leyendo toda la tabla de partidas*/
        vRecalTot();              
        
    }//GEN-LAST:event_jBDelActionPerformed
   
   
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed

        
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Pregunta al usr si esta seguro de salir*/                
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {
            /*Llama al recolector de basura y cierra la forma*/
            System.gc();
            this.dispose();            
        }
       
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el botón de agregar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed
    
    
    /*Cuando se presiona una  tecla en la tabla de colores*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed
                    
    
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
        
        /*Lee el campo del código del producto*/
        String sProd                        = jTProd.getText();
        
        /*Si el código del producto es cadena vacia entonces*/
        if(sProd.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un producto primeramente.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jTProd.grabFocus();            
            return;
        }
        
        /*Lee el campo de la descripción del producto*/
        String sProdDescrip                  = jTDescrip.getText();
        
        /*Si el campo de la descripción del producto es cadena vacia entonces*/
        if(sProdDescrip.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un producto válido.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jTProd.grabFocus();                        
            return;
        }
        
        /*Lee la cantidad*/
        String sCant                        = jTCant.getText();
        
        /*Si la cant es menor a 1 entonces*/
        if(Double.parseDouble(sCant) < 1)
        {
            /*Coloca el borde rojo*/                               
            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a uno.", "Cantidad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jTCant.grabFocus();                        
            return;
        }
        
        /*Lee el código de la unidad*/
        String sUni                         = jComUni.getSelectedItem().toString();
        
        /*Si el código de la unid es cadena vacia entonces*/
        if(sUni.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComUni.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Tienes que indicar una unidad.", "Unidad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jComUni.grabFocus();                        
            return;
        } 
        
        /*Lee el costo*/
        String sCost                        = jTUltCost.getText().replace("$", "").replace(",", "");
        
        /*Si el costo es 0 entonces*/
        if(Double.parseDouble(sCost)== 0)
        {
            /*Coloca el borde rojo*/                               
            jTUltCost.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El costo es $0.00.", "Costo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jTUltCost.grabFocus();                        
            return;
        }
        
        /*Lee el código del impuesto*/
        String sImpuest                     = jComImp.getSelectedItem().toString();
        
        /*Si el código del impue es el vacio entonces*/
        if(sImpuest.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComImp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un impuesto.", "Impuesto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jComImp.grabFocus();                        
            return;
        }
        
        /*Lee el valor del impuesto*/
        String sImpueVal                    = jTValImp.getText();
        
        /*Lee el código de la moneda*/
        String sMon                         = jComMon.getSelectedItem().toString();
        
        /*Si el código de la mon es el vacio entonces*/
        if(sMon.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una moneda.", "Moneda", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo y regresa*/
            jComMon.grabFocus();                        
            return;
        }                                              
                      
        /*Si el cost tiene formato de mon quitaselo*/
        sCost                           = sCost.replace("$", "").replace(",", "");               

        /*Lee el almacén*/
        String sAlma                    = jTAlma.getText();
        
        /*Lee el descuento*/
        String sDesc                    = jTDesc.getText();
        
        /*Si el descuento no es cadena vacia entonces obtiene el descuento*/
        String sDescConvert;
        if(sDesc.compareTo("")!= 0)
            sDescConvert                = Double.toString((Double.parseDouble(sDesc) / 100 ) * (Double.parseDouble(sCost) * Double.parseDouble(sCant)));            
        /*Else, colocalo en 0 para poder hacer la resta*/
        else
            sDescConvert                = "0";
        
        /*Lee el descuento adicional*/
        String sDescAd                  = jTDescAd.getText();
        
        /*Si el descuento adicional no es cadena vacia entonces obtiene el descunto adicional que es el precio ya con el descuento*/
        String sDescAdConvert;
        if(sDescAd.compareTo("")!= 0)
            sDescAdConvert          = Double.toString((Double.parseDouble(sDescAd) / 100 ) * (Double.parseDouble(sDescConvert)));
        /*Else, colocalo en 0 para poder hacer la resta*/
        else
            sDescAdConvert              = "0";
        
        /*Crea el importe*/
        String sImp                     = Double.toString(Double.parseDouble(sCant) * (Double.parseDouble(sCost) - Double.parseDouble(sDescConvert) - Double.parseDouble(sDescAdConvert)));
        
        /*Crea el importe del impuesto*/
        String sTotImpue                = Double.toString(Double.parseDouble(sImp) * (Double.parseDouble(sImpueVal) / 100));               
                
        /*Recorre toda la tabla de partidas*/
        boolean bSi                     = false;
        int     row                     = 0;
        int     iCant;        
        for( ; row < jTab.getRowCount(); row++)
        {
            /*Obtiene el código del producto de la fila*/
            String sCodPro              = jTab.getModel().getValueAt(row, 3).toString();
            
            /*Obtiene la unid de la fila*/
            String sUnid1               = jTab.getModel().getValueAt(row, 5).toString();
            
            /*Obtiene el descuento del producto de la fila*/
            String sDesc1                = jTab.getModel().getValueAt(row, 8).toString();
            
            /*Obtiene el descuento adicional del artículo de la fila*/
            String sDescAd1              = jTab.getModel().getValueAt(row, 9).toString();                       
            
            /*Obtiene el valor del impue de la fila*/
            String sImpueVal1            = jTab.getModel().getValueAt(row, 11).toString();                       
            
            /*Obtiene el último cost de la fila*/
            String sCost1                = jTab.getModel().getValueAt(row, 12).toString();                       
            
            /*Quitale el formato de mon al cost*/
            sCost1                      = sCost1.replace("$", "").replace(",", "");           
            
            /*Si el código que va a insertar el usr ya esta en la tabla, y el descuento y el descuento adicional entonces*/
            if(sCodPro.compareTo(sProd)==0 && sDesc1.compareTo(sDesc)==0 && sDescAd1.compareTo(sDescAd)==0 && sUni.compareTo(sUnid1)==0 && Double.parseDouble(sCost) == Double.parseDouble(sCost1) && Integer.parseInt(sImpueVal)==Integer.parseInt(sImpueVal1))
            {                
                /*Obtiene la cant que tiene originalmente en la fila*/
                iCant                   = Integer.parseInt(jTab.getModel().getValueAt(row, 2).toString());
                
                /*Deja la cant correcta*/
                sCant                   = Integer.toString(Integer.parseInt(sCant) + iCant);
                
                /*Si el cost formato de mon quitaselo*/
                sCost                   = sCost.replace("$", "").replace(",", "");                                
        
                /*Crea el impo*/
                sImp                    = Double.toString(Double.parseDouble(sCant) * (Double.parseDouble(sCost) - Double.parseDouble(sDescConvert) - Double.parseDouble(sDescAdConvert)));

                /*Crea el impo del impue*/
                sTotImpue               = Double.toString((Double.parseDouble(sImp) * (Double.parseDouble(sImpueVal) / 100)));
                
                /*Vuelve a darle formato al impo*/                                
                NumberFormat n          = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant            = Double.parseDouble(sImp);                                                    
                sImp                    = n.format(dCant);
        
                /*Dale formato de mon al tot del impue*/        
                dCant                   = Double.parseDouble(sTotImpue);                
                n                       = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sTotImpue               = n.format(dCant);

                /*Modifica los valores en la tabla*/
                jTab.getModel().setValueAt(sCant, row, 2);
                jTab.getModel().setValueAt(sImp, row, 13);
                jTab.getModel().setValueAt(sTotImpue, row, 14);
                      
                /*Coloca el foco del teclado en el campo del producto*/
                jTProd.grabFocus();
                
                /*Recalcula los totales leyendo toda la tabla de partidas de compras y regresa*/
                vRecalTot();                          
                return;
            }
            
        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
               
        /*Dale formato de mon al impo*/        
        double dCant                    = Double.parseDouble(sImp);                
        NumberFormat n                  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sImp                            = n.format(dCant);
        
        /*Dale formato de mon al cost*/        
        dCant                           = Double.parseDouble(sCost);                
        n                               = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sCost                           = n.format(dCant);
        
        /*Dale formato de mon al tot del impue*/        
        dCant                           = Double.parseDouble(sTotImpue);                
        n                               = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTotImpue                       = n.format(dCant);
        
        /*Si el producto no existe en la tabla entonces*/
        if(!bSi)
        {            
            /*Agrega los datos en la tabla*/
            DefaultTableModel tm  = (DefaultTableModel)jTab.getModel();
            Object nuevo[]          = {Integer.toString(iContFi), "NEW", sCant, sProd, sAlma, sUni, sProdDescrip, sMon, sDesc, sDescAd, sImpuest, sImpueVal, sCost, sImp, sTotImpue};        
            tm.addRow(nuevo);
            
            /*Aumenta el contador de filas en uno*/
            iContFi = iContFi + 1;             
        }        
        
        /*Recalcula los totales leyendo toda la tabla de partidas de compras*/
        vRecalTot();                
         
        /*Coloca el foco del teclado en el campo del producto*/
        jTProd.grabFocus();
                
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo del código del proveedor*/                    
    private void jTProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProv.setSelectionStart(0);jTProv.setSelectionEnd(jTProv.getText().length());

    }//GEN-LAST:event_jTProvFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del código del proveedor*/
    private void jTProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTProv.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTProv.getText().compareTo("")!=0)
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                      
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
        boolean bSi         = false;
        try
        {
            sQ = "SELECT nom, co1, co2, co3, diacred, limcred, eje1, eje2 FROM provs WHERE CONCAT_WS('', ser, prov) = '" + jTProv.getText() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Pon la bandera para saber que si existe*/
                bSi                 = true;

                /*Obtiene el límite de crédito*/
                String sLimitC      = rs.getString("limcred");
                
                /*Dale formato de mon*/                
                double dCant        = Double.parseDouble(sLimitC);                
                NumberFormat n      = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sLimitC             = n.format(dCant);
              
                /*Obtiene los días de crédito*/
                String sDia         = rs.getString("diacred");
                
                /*Si los días de crédito son cadena vacia entonces que sean 0*/
                if(sDia.compareTo("")==0)
                    sDia            = "";
                
                /*Obtiene los resultados y colocalos en los campos correspondientes*/                
                jTNomProv.setText       (rs.getString("nom"));
                jTCo1.setText           (rs.getString("co1"));
                jTCo2.setText           (rs.getString("co2"));
                jTCo3.setText           (rs.getString("co3"));
                jTEje1.setText          (rs.getString("eje1"));
                jTEje2.setText          (rs.getString("eje2"));
                jTDiaCred.setText       ("Días:" + sDia + "Crédito:" + sLimitC);
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

        /*Si el código del proveedor no existe entonces*/
        if(!bSi)
        {
            /*Resetea todos los campos*/
            jTNomProv.setText   ("");            
            jTCo1.setText       ("");            
            jTCo2.setText       ("");            
            jTCo3.setText       ("");            
            jTDiaCred.setText   ("");            
        }   

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jTProvFocusLost

    
    /*Cuando se presiona una tecla en el campo del código del proveedor*/
    private void jTProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
            Busc b = new Busc(this, jTProv.getText(), 3, jTProv, jTNomProv, null, "", null);
            b.setVisible(true);
                        
            //Abre la base de datos                             
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;
            
            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;           
            String      sQ;

            /*Obtiene algunos datos del proveedor*/            
            try
            {
                sQ = "SELECT nom, co1, co2, co3, diacred, limcred FROM provs WHERE prov <> '-' AND prov = '" + jTProv.getText() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    /*Obtiene el límite de crédito*/
                    String sLimitC      = rs.getString("limcred");

                    /*Dale formato de mon*/                    
                    double dCant        = Double.parseDouble(sLimitC);                
                    NumberFormat n      = NumberFormat.getCurrencyInstance(Locale.getDefault());
                    sLimitC             = n.format(dCant);

                    /*Obtiene los días de crédito*/
                    String sDia         = rs.getString("diacred");

                    /*Si los días de crédito son cadena vacia entonces que sean 0*/
                    if(sDia.compareTo("")==0)
                        sDia            = "";

                    /*Obtiene los resultados y colocalos en los campos correspondientes*/                
                    jTNomProv.setText       (rs.getString("nom"));
                    jTCo1.setText           (rs.getString("co1"));
                    jTCo2.setText           (rs.getString("co2"));
                    jTCo3.setText           (rs.getString("co3"));
                    jTDiaCred.setText       ("Días:" + sDia + "Crédito:" + sLimitC);
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
            Star.iCierrBas(con);                                        
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);        

    }//GEN-LAST:event_jTProvKeyPressed

    
    //Cuando se tipea una tecla en el campo del código del proveedor
    private void jTProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTProvKeyTyped

    
    /*Cuando se presiona el botón de buscar*/
    private void jBBusc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBusc1ActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
        Busc b = new Busc(this, jTProv.getText(), 3, jTProv, jTNomProv, null, "", null);
        b.setVisible(true);
        
        /*Coloca el foco del teclado en el campo del proveedor*/
        jTProv.grabFocus();

    }//GEN-LAST:event_jBBusc1ActionPerformed

    
    /*Cuando se pressiona una tecla en el botón de búscar*/
    private void jBBusc1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBusc1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBBusc1KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del nom del proveedor*/
    private void jTNomProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomProvFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTNomProv.setSelectionStart(0);jTNomProv.setSelectionEnd(jTNomProv.getText().length());

    }//GEN-LAST:event_jTNomProvFocusGained

    
    /*Cuando se presiona una tecla ne el campo del nom del proveedor*/
    private void jTNomProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomProvKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTNomProvKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del código del producto*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());

    }//GEN-LAST:event_jTProdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del código del producto*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTProd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
        //Declara variables locales
        String      sProd;
                                        
        /*Obtiene el código del producto seleccionado por el usr*/
        sProd            = jTProd.getText();

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
        boolean bSi             = false;
        try
        {
            sQ = "SELECT prods.IMPUE, prods.PROVOP2, prods.PROVOP2, prods.PROVOP1, prods.COST, prods.COSTRE, prods.ALMA, prods.PRODOP1, prods.PRODOP2, prods.EXIST, almas.ALMADESCRIP FROM prods LEFT JOIN almas ON almas.ALMA = prods.ALMA WHERE prods.PROD = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Pon la bandera para saber que si existe el producto*/
                bSi = true;

                /*Colocalos en los campos correspondientes*/
                jTAlma.setText              (rs.getString("prods.ALMA"));
                jTCodOp1.setText            (rs.getString("prods.PRODOP1"));
                jTCodOpl2.setText           (rs.getString("prods.PRODOP2"));
                jTCodProv1.setText          (rs.getString("prods.PROVOP1"));
                jTCodProv2.setText          (rs.getString("prods.PROVOP2"));
                jTExist.setText             (rs.getString("prods.EXIST"));
                jTDescripAlma.setText       (rs.getString("almas.ALMADESCRIP"));
                jComImp.setSelectedItem     (rs.getString("prods.IMPUE"));
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

        /*Si el código del producto no existe entonces*/
        if(!bSi)
        {
            /*Resetea los campos*/
            jTAlma.setText          ("");
            jTCodOp1.setText        ("");
            jTCodOpl2.setText       ("");
            jTExist.setText         ("");
            jTCodProv1.setText      ("");
            jTCodProv2.setText      ("");
            jTDescripAlma.setText   ("");
            jTDescrip.setText       ("");
        }

    }//GEN-LAST:event_jTProdFocusLost

    
    /*Cuando se presiona una tecla en el campo del código del producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
            Busc b = new Busc(this, jTProd.getText(), 2, jTProd, jTDescrip, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTProdKeyPressed

    
    /*Cuando se presioan el botón de buscar artículo*/
    private void jBBusc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBusc2ActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usr escribió*/
        Busc b = new Busc(this, jTProd.getText(), 2, jTProd, jTDescrip, null, "", null);
        b.setVisible(true);
        
        /*Coloca el foco del teclado en el campo del artículo*/
        jTProd.grabFocus();

    }//GEN-LAST:event_jBBusc2ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de busqueda de producto*/
    private void jBBusc2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBusc2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBBusc2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del producto*/
    private void jTDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescrip.setSelectionStart(0);jTDescrip.setSelectionEnd(jTDescrip.getText().length());

    }//GEN-LAST:event_jTDescripFocusGained
    
    
    /*Cuando se presiona una tecla en el campo de la descripción del código del producto*/
    private void jTDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripKeyPressed

    
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se gana el foco del teclado en el subtot*/
    private void jTSubTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTSubTot.setSelectionStart(0);jTSubTot.setSelectionEnd(jTSubTot.getText().length());

    }//GEN-LAST:event_jTSubTotFocusGained

    
    /*Cuando se presiona una tecla en el campo del tot*/
    private void jTSubTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSubTotKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTSubTotKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del impue*/
    private void jTImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTImp.setSelectionStart(0);jTImp.setSelectionEnd(jTImp.getText().length());

    }//GEN-LAST:event_jTImpFocusGained

    
    /*Cuando se presiona una tecla en el campo del impue*/
    private void jTImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTImpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTImpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del tot*/
    private void jTTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTTot.setSelectionStart(0);jTTot.setSelectionEnd(jTTot.getText().length());

    }//GEN-LAST:event_jTTotFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del tot*/
    private void jTTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTTotKeyPressed

    
    /*Cuando se presiona una tecla en el campo del último cost*/
    private void jTUltCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUltCostKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUltCostKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la cant*/
    private void jTCantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCantKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del último cost*/
    private void jTUltCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUltCostFocusGained

        /*Guara el texto que hay globalmente*/
        sCostGlo    = jTUltCost.getText();
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUltCost.setSelectionStart(0);jTUltCost.setSelectionEnd(jTUltCost.getText().length());        
        
    }//GEN-LAST:event_jTUltCostFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de cant*/
    private void jTCantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCant.setSelectionStart(0);jTCant.setSelectionEnd(jTCant.getText().length());
        
    }//GEN-LAST:event_jTCantFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de cant*/
    private void jTCantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusLost
        
        /*Declara variables*/
        String sCant;
        
        
                
        /*Obtiene la cant escrita por el usr*/
        sCant   = jTCant.getText();
        
        /*Si la cant es igual a cadena vacia entonces*/
        if(sCant.compareTo("")==0)
        {
            /*Coloca 1 en el campo y regresa por que no puede continuar*/
            jTCant.setText("1");
            return;
        }
        
        /*Comprueba si el valor es númerico*/
        try
        {
            /*Intenta convertirlo en int*/
            int i = Integer.parseInt(sCant);
            
            /*Obtiene el valor absoluto*/
            i       = Math.abs(i);
            
            /*Colocalo en el campo nuevamente*/
            jTCant.setText(Integer.toString(i));
        }
        catch(NumberFormatException expnNumForm)
        {
            /*Coloca 1 en el campo y regresa por que no puede continuar*/
            jTCant.setText("1");            
        }
        
    }//GEN-LAST:event_jTCantFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del código opcional 1*/
    private void jTCodOp1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodOp1FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodOp1.setSelectionStart(0);jTCodOp1.setSelectionEnd(jTCodOp1.getText().length());

    }//GEN-LAST:event_jTCodOp1FocusGained
    
    
    /*Cuando se presiona una tecla en el campo del código opcional 1*/
    private void jTCodOp1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodOp1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCodOp1KeyPressed

    
    /*Cuando se gana el foco del teclado ne el campo del proveedor 1*/
    private void jTCodProv1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProv1FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodProv1.setSelectionStart(0);jTCodProv1.setSelectionEnd(jTCodProv1.getText().length());

    }//GEN-LAST:event_jTCodProv1FocusGained

    
    /*Cuando se presiona una tecla en el campo del proveedor 1*/
    private void jTCodProv1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodProv1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCodProv1KeyPressed

    
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

    
    /*Cuando se gana el foco del teclado en el campo del proveedor 2*/
    private void jTCodProv2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodProv2FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCodProv2.setSelectionStart(0);jTCodProv2.setSelectionEnd(jTCodProv2.getText().length());

    }//GEN-LAST:event_jTCodProv2FocusGained

    
    /*Cuando se presiona una tecla en el campo del proveedor 2*/
    private void jTCodProv2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodProv2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTCodProv2KeyPressed

    
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
    
    
    /*Ciamdp se gama el foco del teclado en el campo de la descripción de la unid*/
    private void jTDescripUnidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripUnidFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripUnid.setSelectionStart(0);jTDescripUnid.setSelectionEnd(jTDescripUnid.getText().length());        

    }//GEN-LAST:event_jTDescripUnidFocusGained
    
    
   /*Cuando se presiona una tecla en el campo del código de la unid*/
    private void jTDescripUnidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripUnidKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripUnidKeyPressed

    
    /*Cuando sucede una acción en el combobox de los impues*/
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
            while(rs.next())
            {
                /*Si el valor es -1 solo poner cadena vacia*/
                if(rs.getString("impueval").compareTo("-1")==0)
                    jTValImp.setText("");
                else
                    jTValImp.setText(rs.getString("impueval"));
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
        
    }//GEN-LAST:event_jComImpActionPerformed

    
    /*Cuando se presiona una tecla en el combobox de los impues*/
    private void jComImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComImpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComImpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del valor del impue*/
    private void jTValImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTValImpFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTValImp.setSelectionStart(0);jTValImp.setSelectionEnd(jTValImp.getText().length());

    }//GEN-LAST:event_jTValImpFocusGained
    
    
    /*Cuando se presiona una tecla en el campo del valor del impue*/
    private void jTValImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTValImpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTValImpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del descuento*/
    private void jTDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDesc.setSelectionStart(0);jTDesc.setSelectionEnd(jTDesc.getText().length());

    }//GEN-LAST:event_jTDescFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del descuento*/
    private void jTDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDesc.setCaretPosition(0);
        
        /*Obtiene el descuento escrito por el usr*/
        String sDesc   = jTDesc.getText();
        
        /*Si el descuento es igual a cadena vacia entonces*/
        if(sDesc.compareTo("")==0)
        {
            /*Coloca 1 en el campo y regresa por que no puede continuar*/
            jTDesc.setText("1");
            return;
        }
        
        /*Comprueba si el valor es númerico*/
        try
        {
            /*Intenta convertirlo en int*/
            int i = Integer.parseInt(sDesc);
            
            /*Obtiene el valor absoluto*/
            i       = Math.abs(i);
            
            /*Colocalo en el campo nuevamente*/
            jTDesc.setText(Integer.toString(i));
        }
        catch(NumberFormatException expnNumForm)
        {            
            /*Coloca 1 en el campo y regresa por que no puede continuar*/
            jTDesc.setText("1");            
        }

    }//GEN-LAST:event_jTDescFocusLost

    
    /*Cuando se presiona una tecla en el campo de descuento*/
    private void jTDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescKeyPressed

    
    /*Cuando se tipea una tecla en el campo del descuento*/
    private void jTDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyTyped

        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTDesc.getText().length()> 30)
            jTDesc.setText(jTDesc.getText().substring(0, 30));

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTDescKeyTyped

    
    /*Cuando se pierde el foco del teclado en el último cost*/
    private void jTUltCostFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUltCostFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUltCost.setCaretPosition(0);
        
        /*Obtiene el último cost escrito por el usr*/
        String sCost   = jTUltCost.getText();
        
        /*Si el cost es igual a cadena vacia entonces*/
        if(sCost.compareTo("")==0)
        {
            /*Coloca 0 pesos y regresa por que no puede continuar*/
            jTUltCost.setText("$0.00");
            return;
        }
        
        /*Quitale al cost el formato de mon*/
        sCost       = sCost.replace("$", "");
        sCost       = sCost.replace(",", "");
        
        /*Comprueba si el valor es númerico*/
        try
        {
            /*Intenta convertirlo en int*/
            int i   = Integer.parseInt(sCost);
            
            /*Obtiene el valor absoluto*/
            i       = Math.abs(i);
            sCost   = Integer.toString(i);
            
            /*Dale formato de mon al cost*/            
            double dCant    = Double.parseDouble(sCost);                
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            sCost           = n.format(dCant);
            
            /*Colocalo en el campo nuevamente*/
            jTUltCost.setText(sCost);
        }
        catch(NumberFormatException expnNumForm)
        {
            /*Coloca el cost que tenía antes en el campo y regresa por que no puede continuar*/
            jTUltCost.setText(sCostGlo);            
        }
        
    }//GEN-LAST:event_jTUltCostFocusLost

    
    /*Cuando sucede una acción en el combobox de las mons*/
    private void jComMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComMonActionPerformed
               
        /*Si no hay datos entonces regresa*/
        if(jComMon.getSelectedItem()==null)
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
        
        /*Obtiene la descripción de la mon en base a su código*/
        try
        {
            sQ = "SELECT mondescrip FROM mons WHERE mon = '" + jComMon.getSelectedItem().toString() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            if(rs.next())
                jTDescripMon.setText(rs.getString("mondescrip"));            
            else
                jTDescripMon.setText("");            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                           
        }

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComMonActionPerformed

    
    /*Cuando se presiona una tecla en el combobox de las mons*/
    private void jComMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComMonKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComMonKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción de la mon*/
    private void jTDescripMonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripMonFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripMon.setSelectionStart(0);jTDescripMon.setSelectionEnd(jTDescripMon.getText().length());

    }//GEN-LAST:event_jTDescripMonFocusGained
    
    
    /*Cuando se presiona una tecla en el campo de la descripción de la mon*/
    private void jTDescripMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripMonKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripMonKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del descuento adicional*/
    private void jTDescAdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescAdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescAd.setSelectionStart(0);jTDescAd.setSelectionEnd(jTDescAd.getText().length());        

    }//GEN-LAST:event_jTDescAdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del descuento adicional*/
    private void jTDescAdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescAdFocusLost

        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTDescAd.getText().length()> 20)
            jTDescAd.setText(jTDescAd.getText().substring(0, 20));

    }//GEN-LAST:event_jTDescAdFocusLost

    
    /*Cuando se presiona una tecla en el campo del descuento adicional*/
    private void jTDescAdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescAdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescAdKeyPressed

    
    /*Cuando se tipea una tecla en el campo del descuento adicional*/
    private void jTDescAdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescAdKeyTyped

        /*Si el campo excede la cant de caracteres permitidos recortalo*/
        if(jTDescAd.getText().length()> 30)
            jTDescAd.setText(jTDescAd.getText().substring(0, 30));

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTDescAdKeyTyped

    
    //Cuando sucede una acción en el combo de unidades
    private void jComUniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComUniActionPerformed
        
        /*Si no hay datos regresa*/
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
        
        /*Obtiene la descripción de la unid en base a su código*/
        try
        {
            sQ = "SELECT uniddescrip FROM unids WHERE unid = '" + jComUni.getSelectedItem().toString() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combobox*/
            if(rs.next())
                jTDescripUnid.setText(rs.getString("uniddescrip"));            
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
        
    }//GEN-LAST:event_jComUniActionPerformed

    
    /*Cuando se presiona una tecla en el combobox del código de la unid*/
    private void jComUniKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComUniKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComUniKeyPressed

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
                
        /*Lee el código del proveedor*/        
        String sCodProv    = jTProv.getText();
        
        /*Si el código del proveedor es cadena vacia entonces*/
        if(sCodProv.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un proveedor.", "Guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control y regresa*/
            jTProv.grabFocus();                        
            return;
        }
        
        /*Si el proveedor no existe entonces*/
        if(jTNomProv.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El proveedor no existe.", "Guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control y regresa*/
            jTProv.grabFocus();                        
            return;
        }
                
        /*Si la tabla de partidas no tiene partidas entonces*/
        if(jTab.getRowCount()==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No hay partidas para guardar.", "Guardar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));             
            return;
        }
                  
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Obtiene si el usuario tiene correo asociado
        int iRes    = Star.iUsrCon(con, Login.sUsrG);

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Comprueba si la configuración de mostrar el mensaje esta habilitado o no
        int iMosMsj = Star.iMostMsjCorrUsr(con);

        //Si hubo error entonces regresa
        if(iMosMsj==-1)
            return;                        

        //Si no tiene correo asociado entonces solamente mensajea
        boolean bMandCo = true;
        if(iRes==0 && iMosMsj==1)
        {
            /*Colcoa la bandera para que no se intente mandar corr y mensajea*/                
            bMandCo = false;                                
            JOptionPane.showMessageDialog(null, "No se a definido correo electrónico para el usuario: " + Login.sUsrG + ". No se mandará correo electrónico.", "Correo electrónico",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                        
        }

        /*Si aún se puede mandar corr electrónico comprueba si hay correos a donde mandar*/
        if(bMandCo)
        {
            /*Si no hay ningún corr escrito entonces*/
            if(jTCo1.getText().compareTo("")==0 && jTCo2.getText().compareTo("")==0 && jTCo3.getText().compareTo("")==0)
            {
                /*Colocal a banera para que no se intente mandar correo y mensajea*/                
                bMandCo = false;                                
                JOptionPane.showMessageDialog(null, "No se específico ningún correo del proveedor para enviar la orden de compra, la orden no se enviará.", "Correo Electrónico",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                                        
            }            
        }
        
        /*Si aún se puede mandar corr electrónico comprueba si el checkbox de mandar*/
        if(bMandCo)
        {
            /*Si no hay ningún corr escrito entonces*/
            if(!jCMand.isSelected())
            {
                /*Coloca la banera para que no se intente mandar correo y mensajea*/                
                bMandCo = false;                                
                JOptionPane.showMessageDialog(null, "No se enviará correo electrónico por que así se especificó.", "Correo Electrónico",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                                        
            }            
        }
        
        /*Pregunta al usr si esta seguro de querer guardar la ordén*/
        Object[] op = {"Si","No"};
        iRes        = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar Ordén de Compra", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {                 
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;                                   
        }     

        /*Lee la fecha de entrega*/        
        Date fe                 =  jDFEnt.getDate();
        SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        String sFEnt            = sdf.format(fe);      
        
        /*Lee los totales*/
        String      sSubTot;
        String      sImpue;
        String      sTot;        
        sSubTot     = jTSubTot.getText();
        sImpue      = jTImp.getText();
        sTot        = jTTot.getText();
        
        /*Quitales el formato de mon a los totales*/
        sSubTot     = sSubTot.replace   ("$", "");
        sSubTot     = sSubTot.replace   (",", "");
        sImpue      = sImpue.replace    ("$", "");
        sImpue      = sImpue.replace    (",", "");
        sTot        = sTot.replace      ("$", "");
        sTot        = sTot.replace      (",", "");
            
        /*Determina el ejecutivo*/
        String      sEje;
        if(jREje1.isSelected())
            sEje    = jTEje1.getText();
        else
            sEje    = jTEje2.getText();
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Inserta en la base de datos la ordén de compra, osea el encabezado*/        
        try 
        {            
            sQ      = "INSERT INTO ords (   prov,                                   proy,                                   subtot,                                 impue,                                  total,                              estac,                                          fent,                               eje,                                sucu,                                           nocaj) " + 
                              "VALUES('" +  sCodProv.replace("'", "''") + "', '" +  sCodProGlo.replace("'", "''") + "', " + sSubTot.replace("'", "''") + ", " +     sImpue.replace("'", "''") + ", " +      sTot.replace("'", "''") + ", '" +   Login.sUsrG.replace("'", "''") + "', '" +   sFEnt.replace("'", "''") + "', '" + sEje.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                        
            st      = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                            
         }
	
        /*Si los correos se tienen que actualizar en el proveedor entonces*/
        if(jCGuaCo.isSelected())
        {
            /*Actualiza los correos del proveedor*/            
            try 
            {                
                sQ = "UPDATE provs SET "
                        + "co1          = '" + jTCo1.getText().replace("'", "''") + "', "
                        + "co2          = '" + jTCo2.getText().replace("'", "''") + "', "
                        + "co3          = '" + jTCo3.getText().replace("'", "''") + "', "
                        + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE prov   =  '" + sCodProv.replace("'", "''") + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                   
             }
            
        }/*Fin de if(jCGuaCo.isSelected())*/
        
        /*Si se tienen que actualizar los ejecutivos del proveedor entonces*/
        if(jCGuaEje.isSelected())
        {
            /*Actualiza los ejecutivos del proveedor*/            
            try 
            {                
                sQ = "UPDATE provs SET "
                        + "eje1         = '" + jTEje1.getText().replace("'", "''") + "', "
                        + "eje2         = '" + jTEje2.getText().replace("'", "''") + "', "
                        + "co3          = '" + jTCo3.getText().replace("'", "''") + "', "
                        + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE prov   =  '" + sCodProv.replace("'", "''") + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                    
             }
            
        }/*Fin de if(jCGuaCo.isSelected())*/
        
        /*Obtiene el código de la ordén de compra insertado y la fecha*/
        String sCodOrd  = "";
        String sFAlt    = "";
        try
        {
            sQ = "SELECT codord, falt FROM ords ORDER BY codord DESC LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene los resultados*/
                sCodOrd = rs.getString("codord");
                sFAlt   = rs.getString("falt");                                                
            }                        
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
            /*Insertalos en la base de datos de las partidas de las ordenes de compra*/            
            try 
            {                
                sQ = "INSERT INTO partords ( codord,                                codcot,                                                             prod,                                                               cant,                                       alma,                                                               unid,                                                               mon,                                                                descu,                                            descad,                                           impue,                                                                            impueval,                                   ultcost,                                                                          impo,                                                                             estac,                      fent,            descrip,                                     impoimpue,                                                                        sucu,                                             nocaj) " + 
                              " VALUES('" +  sCodOrd.replace("'", "''") + "', '" +  jTab.getValueAt(x, 1).toString().replace("'", "''") + "', '" +      jTab.getValueAt(x, 3).toString().replace("'", "''") + "', " +       jTab.getValueAt(x, 2).toString() + ", '" +  jTab.getValueAt(x, 4).toString().replace("'", "''") + "', '" +      jTab.getValueAt(x, 5).toString().replace("'", "''") + "', '" +      jTab.getValueAt(x, 7).toString().replace("'", "''") + "', " +       jTab.getValueAt(x, 8).toString() + ", " +         jTab.getValueAt(x, 9).toString() + ", '" +        jTab.getValueAt(x, 10).toString().replace("$", "").replace(",", "") + "', " +     jTab.getValueAt(x, 11).toString() + "," +   jTab.getValueAt(x, 12).toString().replace("$", "").replace(",", "") + ", " +      jTab.getValueAt(x, 13).toString().replace("$", "").replace(",", "") + ", '" +     Login.sUsrG + "', '" +  sFEnt + "', '" + jTab.getValueAt(x, 6).toString() + "', " +   jTab.getValueAt(x, 14).toString().replace("$", "").replace(",", "") + ",'" +      Star.sSucu.replace("'", "''") + "','" +     Star.sNoCaj.replace("'", "''") + "')";                    
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
                
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        /*Recorre toda la tabla de faltantes del otro formulario*/
        for(int x = 0; x < jTabFalt.getRowCount(); x++)
        {
            /*Comprueba si ya esta pedido o no y obtiene algunos datos*/
            String sEsta;
            String sUltMod;
            String sFEnt1;
            try
            {
                sQ = "SELECT estac, fmod, fent FROM partords WHERE prod = '" + jTabFalt.getValueAt(x, 6).toString() + "' AND unid = '" + jTabFalt.getValueAt(x, 3).toString() + "' AND cant = " + jTabFalt.getValueAt(x, 2).toString();
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    /*Obtiene algunos datos*/
                    sEsta   = rs.getString("estac");
                    sUltMod = rs.getString("fmod");
                    sFEnt1  = rs.getString("fent"); 
                    
                    /*Actualiza la tabla del otro formulario para saber que ya esta pedido*/
                    jTabFalt.setValueAt("Si", x, 12 );
                    jTabFalt.setValueAt(sEsta, x, 13 );
                    jTabFalt.setValueAt(sUltMod, x, 14 );
                    jTabFalt.setValueAt(sFEnt1, x, 15 );
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
        if(Star.iCierrBas(con)==-1)
            return;

        /*Determina si se va a imprimir*/
        boolean bImpri          = false;
        if(jCImp.isSelected())
            bImpri              = true;                
        
        /*Determina si se va a mostrar el archivo*/
        boolean bMost           = false;
        if(jCMostA.isSelected())
            bMost               = true;                
        
        /*Declara variables final para pasar al thread*/
        final String sOrdFi     = sCodOrd;
        final String sFAltFi    = sFAlt;
        final String sNomP      = jTNomProv.getText();
        final String sProvFi    = sCodProv;
        final String sEjeFi     = sEje;
        final String sFEntFi    = sFEnt;
        final boolean bMandCoFi = bMandCo;
        final boolean bImpriFi  = bImpri;
        final boolean bMostFi   = bMost;
        final String sC1        = jTCo1.getText();
        final String sC2        = jTCo2.getText();
        final String sC3        = jTCo3.getText();
        final String sSubTotFi  = sSubTot;
        final String sImpueFi   = sImpue;
        final String sTotFi     = sTot;
        
        /*Thread para quitar carga y todo se haga mas rápido*/
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

                /*Genera el reporte de la órden*/
                try
                {
                    /*Crea los parámetros que se pasarán*/
                    Map <String,String> para = new HashMap<>();             
                    para.clear();
                    para.put("ORD",               sOrdFi);                    
                    para.put("FECH",              sFAltFi);
                    para.put("NOMPROV",           sNomP);
                    para.put("CODPROV",           sProvFi);
                    para.put("EJE",               sEjeFi);
                    para.put("FENT",              sFEntFi);                    
                    para.put("SUBTOTAL",          sSubTotFi);                    
                    para.put("IMPUE",             sImpueFi);                    
                    para.put("TOTAL",             sTotFi);                                        
                    para.put("EMPLOC",            sNomLoc);
                    para.put("TELLOC",            sTelLoc);
                    para.put("COLLOC",            sColLoc);
                    para.put("CALLLOC",           sCallLoc);
                    para.put("CPLOC",             sCPLoc);
                    para.put("CIULOC",            sCiuLoc);
                    para.put("ESTLOC",            sEstLoc);
                    para.put("PAILOC",            sPaiLoc);                    
                    para.put("LOGE",              Star.class.getResource(Star.sIconDef).toString());

                    /*Establece la ruta del reporte xml*/                        
                    JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptOrd.jrxml"));            
                    JasperPrint  pr     = JasperFillManager.fillReport(ja, (Map)para, con);            

                    /*Si se tiene que mostrar el archivo entonces*/
                    if(bMostFi)
                    {
                        /*Muestralo maximizado*/
                        JasperViewer v  = new JasperViewer(pr, false);
                        v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);                    
                        v.setVisible(true);
                    }

                    /*Si se tiene que imprimir entonces hazlo*/
                    if(bImpriFi)
                        JasperPrintManager.printReport(pr,true);

                    //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
                    String sCarp    = Star.sGetRutCarp(con);                    

                    //Si hubo error entonces regresa
                    if(sCarp==null)
                        return;
                    
                    /*Si el directorio de ordenes no existe entonces crea la ruta*/
                    sCarp = sCarp + "\\Ordenes";
                    if(!new File(sCarp).exists())
                        new File(sCarp).mkdir();
                        
                    /*Si el directorio de la empresa ordenes no existe entonces crea la ruta*/                    
                    sCarp += "\\" + Login.sCodEmpBD; 
                    if(!new File(sCarp).exists())
                        new File(sCarp).mkdir();
                    
                    /*Completa la ruta completa*/
                    String sRutPDF     = sCarp + "\\" + sOrdFi + ".pdf";

                    /*Exportalo a pdf en el directorio completo con el nom del código de la órden*/
                    JasperExportManager.exportReportToPdfFile(pr, sRutPDF);

                    /*Manda el PDF a los correos si lo tiene que mandar*/
                    if(bMandCoFi)
                        vMandPDFEmp(sOrdFi + ".pdf", sRutPDF, sC1, sC2, sC3, sOrdFi);                                                
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
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Exito al guardar la ordén de compra: " + sCodOrd + ".", "Guardada", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        this.dispose();
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Manda la ordén de compra al corr alternativo*/
    private void vMandAlter(final String sNombPDF, final String sRutPDF, final String sCo1, final String sNoDoc)            
    {
        //Declara variables locales
        String      sServSMTPSal            = "";
        String      sSMTPPort               = "";
        String      sUsr                    = "";
        String      sCont                   = "";
        String      sActSSL                 = "";                               

        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        //Declara variables locales
        String sAsunOrd = "";
        String sCuerOrd = "";
        String sCoAlter = "";
        
        /*Trae todos los datos del corr electrónico de la base de datos en base a El usuario*/        
        try
        {
            sQ = "SELECT servsmtpsal, portsmtp, actslenlog, usr, pass, asunord, cuerpord, coalter FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene todos los datos de la consulta*/
                sServSMTPSal                = rs.getString("servsmtpsal");
                sSMTPPort                   = rs.getString("portsmtp");
                sUsr                        = rs.getString("usr");
                sCont                       = rs.getString("pass");
                sAsunOrd                    = rs.getString("asunord");
                sCuerOrd                    = rs.getString("cuerpord");
                sCoAlter                    = rs.getString("coalter");

                /*Si activar ssl login esta activado entonces guarda true*/
                if(rs.getString("actslenlog").compareTo("1")==0)
                    sActSSL = "true";
                else
                    sActSSL = "false";                       

                /*Desencripta la contraseña*/
                sCont                       = Star.sDecryp(sCont);
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                            
        }

        /*Crea el usr y la contraseña como final para que el th valide si son válidos o no*/
        final String sUser      = sUsr;
        final String sContra    = sCont;

        /*Manda el correo*/
        try
        {
            //Define las propiedades de conexión
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", sServSMTPSal);
            props.put("mail.smtp.starttls.enable", sActSSL);
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.port", sSMTPPort);
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            final String username = sUser;
            final String password = sContra;
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
                    
            MimeMessage  msj    = new MimeMessage(session);
            msj.setFrom         (new InternetAddress(sUser));
            msj.setRecipients   (Message.RecipientType.TO,InternetAddress.parse(sCoAlter));
            msj.setSubject      (sAsunOrd + "\"" + sNoDoc + "\" Fallo envío a " + sCo1);
            String msg           = sCuerOrd + " *Fallo envío a " + sCo1 + "*";
            msj.setContent      (msg, "text/html; charset=utf-8");

            /*Genera el adjunto*/
            BodyPart msgBod     = new MimeBodyPart();
            msgBod.setText      (sCuerOrd + " *Fallo envío a " + sCo1 + "*");
            Multipart mult      = new MimeMultipart();
            mult.addBodyPart(msgBod);

            /*Adjunta el PDF*/
            msgBod              = new MimeBodyPart();                                               
            DataSource src1     = new FileDataSource(sRutPDF);                        
            msgBod.setDataHandler(new DataHandler(src1));
            msgBod.setFileName  (sNombPDF);
            mult.addBodyPart    (msgBod);                                                

            /*Junta todo y manda el corr*/
            msj.setContent      (mult);           
            Transport.send      (msj);
        }
        catch(MessagingException expnMessag)
        {
            /*Ingresa en la base de datos el registor de que se fallo*/
            try 
            {                
                sQ = "INSERT INTO logcorrs(   corr,                                 nodoc,                              estad,              motiv,                                                  estac,                                      falt,       corrde,                     tip_doc,                              sucu,                                       nocaj) " + 
                                 "VALUES('" + sCo1.replace("'", "''") + "','" +     sNoDoc.replace("'", "''") + "',     'FALLO','" +        expnMessag.getMessage().replace("'", "''") + "','" +    Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'ORDÉN COMPRA ENVIO ALTERNATIVO','" + Star.sSucu.replace("'", "''") + "','" +     Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                    
            }

            /*Regresa no puede continuar*/
            return;
        }

        /*Ingresa en la base de datos el registor de que se mando con éxito*/
        try 
        {                
            sQ = "INSERT INTO logcorrs (corr,                               nodoc,                          estad,      motiv,    estac,                                    falt,         corrde,                           tip_doc,                              sucu,                                         nocaj) " + 
                           "VALUES('" + sCo1.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" +   Login.sUsrG.replace("'", "''") + "',  now(), '" +   sUsr.replace("'", "''") + "',     'ORDÉN COMPRA ENVIO ALTERNATIVO','" + Star.sSucu.replace("'", "''") + "','" + Star.sNoCaj.replace("'", "''") + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
            
            sQ = "INSERT INTO estadiscor(corr,                               nodoc,                          estad,      motiv,    estac,                                    falt,         corrde,                           tip_doc,                              sucu,                                         nocaj) " + 
                           "VALUES('" + sCo1.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" +   Login.sUsrG.replace("'", "''") + "',  now(), '" +   sUsr.replace("'", "''") + "',     'ORDÉN COMPRA ENVIO ALTERNATIVO','" + Star.sSucu.replace("'", "''") + "','" + Star.sNoCaj.replace("'", "''") + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                           
        }        
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vMandAlter(final String sNombPDF, final String sRutPDF, final String sCo1, final String sNoDoc)*/
    
    
    /*Manda el corr con el pdf al proveedor*/            
    private void vMandPDFEmp(final String sNomPDF, final String sRutPDF, final String sCo1, final String sCo2, final String sCo3, final String sNoDoc)
    {
        /*Manda el PDF a la empresa en un thread*/        
        (new Thread()
        {
            @Override
            public void run()
            {
                //Declara variables locales
                String      sSrvSMTPSal             = "";
                String      sSMTPPort               = "";
                String      sUsr                    = "";
                String      sContrasenia            = "";
                String      sActSSL                 = "";                               
                              
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 
                
                //Declara variables locales
                String sAsunOrd = "";
                String sCuerOrd = "";                
                
                /*Trae todos los datos del corr electrónico de la base de datos en base a El usuario*/                
                try
                {
                    sQ = "SELECT asunord, cuerpord, servsmtpsal, portsmtp, actslenlog, usr, pass FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    if(rs.next())
                    {
                        /*Obtiene todos los datos de la consulta*/
                        sSrvSMTPSal                 = rs.getString("servsmtpsal");
                        sSMTPPort                   = rs.getString("portsmtp");
                        sUsr                        = rs.getString("usr");
                        sContrasenia                = rs.getString("pass");
                        sAsunOrd                    = rs.getString("asunord");
                        sCuerOrd                    = rs.getString("cuerpord");
                        
                        /*Si activar ssl login esta activado entonces guarda true*/
                        if(rs.getString("actslenlog").compareTo("1")==0)
                            sActSSL = "true";
                        else
                            sActSSL = "false";                       
                        
                        /*Desencripta la contraseña*/
                        sContrasenia                = Star.sDecryp(sContrasenia);                        
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                                                        
                }
                
                /*Crea el usr y la contraseña como final para que el thead valide si son válidos o no*/                
                final String sUser      = sUsr;
                final String sContra    = sContrasenia;
                
                /*Si el primer correo no es null entonces*/
                if(sCo1!=null)
                {
                    /*Manda el correo*/
                    try
                    {
                        //Define las propiedades de conexión
                        Properties props = System.getProperties();
                        props.setProperty("mail.smtp.host", sSrvSMTPSal);
                        props.put("mail.smtp.starttls.enable", sActSSL);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.debug", "true");
                        props.put("mail.smtp.port", sSMTPPort);
                        props.put("mail.store.protocol", "pop3");
                        props.put("mail.transport.protocol", "smtp");
                        final String username = sUser;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });

                        MimeMessage  msj        = new MimeMessage(session);
                        msj.setFrom             (new InternetAddress(sUser));
                        msj.setRecipients       (Message.RecipientType.TO,InternetAddress.parse(sCo1));
                        msj.setSubject          (sAsunOrd + "\"" + sNoDoc + "\"");
                        String msg              = sCuerOrd;
                        msj.setContent          (msg, "text/html; charset=utf-8");
                        
                        /*Genera el adjunto*/
                        BodyPart msjbod         = new MimeBodyPart();
                        msjbod.setText          (sCuerOrd);
                        Multipart mult          = new MimeMultipart();
                        mult.addBodyPart        (msjbod);
                        msjbod                  = new MimeBodyPart();
                        DataSource sou          = new FileDataSource(sRutPDF);
                        msjbod.setDataHandler   (new DataHandler(sou));
                        msjbod.setFileName      (sNomPDF);
                        mult.addBodyPart        (msjbod);
                        msj.setContent          (mult);
                        
                        /*Manda el correo*/
                        Transport.send          (msj);
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (  corr,                             nodoc,                          estad,    motiv,  estac,                                    falt,       corrde,                         tip_doc,     sucu,                                          nocaj) " + 
                                             "VALUES('" + sCo1.replace("'", "''") + "','" + sNoDoc.replace("'", "''") + "', 'ENVIADO','','" + Login.sUsrG.replace("'", "''") + "',  now(), '" + sUsr.replace("'", "''") + "',   'ORDEN','" + Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                            
                            sQ = "INSERT INTO estadiscor(  corr,                             nodoc,                          estad,    motiv,  estac,                                    falt,       corrde,                         tip_doc,     sucu,                                          nocaj) " + 
                                             "VALUES('" + sCo1.replace("'", "''") + "','" + sNoDoc.replace("'", "''") + "', 'ENVIADO','','" + Login.sUsrG.replace("'", "''") + "',  now(), '" + sUsr.replace("'", "''") + "',   'ORDEN','" + Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
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
                    catch(MessagingException expnMessag)
                    {
                        /*Ingresa en la base de datos el registor de que se fallo*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (  corr,                             nodoc,                          estad,       motiv,                                                 estac,                                  falt,       corrde,                             tip_doc,        sucu,                                    nocaj) " + 
                                             "VALUES('" + sCo1.replace("'", "''") + "','" + sNoDoc.replace("'", "''") + "', 'FALLO','" + expnMessag.getMessage().replace("'", "''") + "','" +   Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',       'ORDEN','" +    Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                           
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                            return;                                                                                
                        }
                        
                        /*Mandalo al correo alternativo*/
                        vMandAlter(sNomPDF, sRutPDF, sCo1, sNoDoc);
                    }                       
                    
                }/*Fin de if(sCo1!=null)*/
                
                /*Si el segundo corr no es null entonces*/
                if(sCo2!=null)
                {
                    /*Manda un correo*/
                    try
                    {
                        Properties props = System.getProperties();
                        props.setProperty("mail.smtp.host", sSrvSMTPSal);
                        props.put("mail.smtp.starttls.enable", sActSSL);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.debug", "true");
                        props.put("mail.smtp.port", sSMTPPort);
                        props.put("mail.store.protocol", "pop3");
                        props.put("mail.transport.protocol", "smtp");
                        final String username = sUser;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });

                        MimeMessage  msj    = new MimeMessage(session);
                        msj.setFrom         (new InternetAddress(sUser));
                        msj.setRecipients   (Message.RecipientType.TO,InternetAddress.parse(sCo2));
                        msj.setSubject      (sAsunOrd + "\"" + sNoDoc + "\"");
                        String msg           = sCuerOrd;
                        msj.setContent      (msg, "text/html; charset=utf-8");
                        
                        /*Genera el adjunto*/
                        BodyPart msjbod     = new MimeBodyPart();
                        msjbod.setText      (sCuerOrd);
                        Multipart mult      = new MimeMultipart();
                        mult.addBodyPart    (msjbod);
                        msjbod              = new MimeBodyPart();
                        DataSource sou      = new FileDataSource(sRutPDF);
                        msjbod.setDataHandler(new DataHandler(sou));
                        msjbod.setFileName  (sNomPDF);
                        mult.addBodyPart    (msjbod);
                        msj.setContent      (mult);
                        
                        /*Manda el correo*/
                        Transport.send      (msj);
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (corr,                               nodoc,                          estad,      motiv,  estac,                                      falt,         corrde,                           tip_doc,         sucu,                                          nocaj) " + 
                                           "VALUES('" + sCo2.replace("'", "''") + "','" +  sNoDoc.replace("'", "''") + "',  'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" +   sUsr.replace("'", "''") + "',     'ORDEN',    '" +  Star.sSucu.replace("'", "''") + "','" + Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                            
                            sQ = "INSERT INTO estadiscor(corr,                               nodoc,                          estad,      motiv,  estac,                                      falt,         corrde,                           tip_doc,         sucu,                                          nocaj) " + 
                                           "VALUES('" + sCo2.replace("'", "''") + "','" +  sNoDoc.replace("'", "''") + "',  'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" +   sUsr.replace("'", "''") + "',     'ORDEN',    '" +  Star.sSucu.replace("'", "''") + "','" + Star.sNoCaj.replace("'", "''") + "')";                    
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
                    catch(MessagingException expnMessag)
                    {
                        /*Ingresa en la base de datos el registor de que se fallo*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (corr,                               nodoc,                          estad,       motiv,                                                 estac,                                  falt,         corrde,                       tip_doc,     sucu,                                    nocaj) " + 
                                           "VALUES('" + sCo2.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'FALLO','" + expnMessag.getMessage().replace("'", "''") + "','" +   Login.sUsrG.replace("'", "''") + "',    now(), '" +   sUsr.replace("'", "''") + "', 'ORDEN','" + Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                           
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                            return;                                                                                
                        }
                        
                        /*Mandalo al correo alternativo*/
                        vMandAlter(sNomPDF, sRutPDF, sCo2, sNoDoc);
                    }                   
                    
                }/*Fin de if(sCo2!=null)*/
                
                /*Si el primer correo no es null entonces*/
                if(sCo3!=null)
                {                                            
                    /*Manda un correo*/
                    try
                    {
                        Properties props = System.getProperties();
                        props.setProperty("mail.smtp.host", sSrvSMTPSal);
                        props.put("mail.smtp.starttls.enable", sActSSL);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.debug", "true");
                        props.put("mail.smtp.port", sSMTPPort);
                        props.put("mail.store.protocol", "pop3");
                        props.put("mail.transport.protocol", "smtp");
                        final String username = sUser;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });
                        
                        MimeMessage  msj    = new MimeMessage(session);
                        msj.setFrom         (new InternetAddress(sUser));
                        msj.setRecipients   (Message.RecipientType.TO,InternetAddress.parse(sCo3));
                        msj.setSubject      (sAsunOrd + "\"" + sNoDoc + "\"");
                        String msg           = sCuerOrd;
                        msj.setContent      (msg, "text/html; charset=utf-8");
                        
                        /*Genera el adjunto*/
                        BodyPart msjbod     = new MimeBodyPart();
                        msjbod.setText      (sCuerOrd);
                        Multipart mult      = new MimeMultipart();
                        mult.addBodyPart    (msjbod);
                        msjbod              = new MimeBodyPart();
                        DataSource sou      = new FileDataSource(sRutPDF);
                        msjbod.setDataHandler(new DataHandler(sou));
                        msjbod.setFileName  (sNomPDF);
                        mult.addBodyPart    (msjbod);
                        msj.setContent      (mult);
                        
                        /*Manda el correo*/
                        Transport.send(msj);
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (corr,                               nodoc,                          estad,      motiv,  estac,                                      falt,       corrde,                         tip_doc,     sucu,                                          nocaj) " + 
                                           "VALUES('" + sCo3.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'ORDEN','" + Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                            
                            sQ = "INSERT INTO estadiscor(corr,                               nodoc,                          estad,      motiv,  estac,                                      falt,       corrde,                         tip_doc,     sucu,                                          nocaj) " + 
                                           "VALUES('" + sCo3.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'ORDEN','" + Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
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
                    catch(MessagingException expnMessag)
                    {
                        /*Ingresa en la base de datos el registor de que se fallo*/
                        try 
                        {                            
                            sQ = "INSERT INTO logcorrs (corr,                               nodoc,                          estad,      motiv,                                                  estac,                                  falt,        corrde,     tip_doc,     sucu,                                       nocaj) " + 
                                           "VALUES('" + sCo3.replace("'", "''") + "','" +   sNoDoc.replace("'", "''") + "', 'FALLO','" + expnMessag.getMessage().replace("'", "''") + "','" +   Login.sUsrG.replace("'", "''") + "' ,   now(), '" +  sUsr + "', 'ORDEN', '" + Star.sSucu.replace("'", "''") + "','" +     Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                           
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
                        
                        /*Mandalo al correo alternativo*/
                        vMandAlter(sNomPDF, sRutPDF, sCo3, sNoDoc);
                               
                        /*Regresa no puede continuar*/
                        return;
                    }                                        
                    
                }/*Fin de if(sCo3!=null)*/
                
                //Cierra la base de datos
                Star.iCierrBas(con);
                
            }/*Fin de public void run()*/
            
        }).start();
        
    }/*Fin de private void vMandPDFEmp(String sRutPDF, String sCo1, String sCo2, String sCo3, String sIdCorreoSelec)*/
    
    
    /*Cuando se tipea una tecla en el campo de la cant*/
    private void jTCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();        
        
    }//GEN-LAST:event_jTCantKeyTyped

    
    /*Cuando se tipea una tecla en el campo del cost*/
    private void jTUltCostKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUltCostKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTUltCostKeyTyped

    
    /*Cuando se presiona una tecla en el campo de posible fecha de entrega*/
    private void jDFEntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFEntKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDFEntKeyPressed

    
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

    
    /*Cuando se presiona una tecla en el checkbox de corr 1*/
    private void jCCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo1KeyPressed

    
    /*Cuando se presiona una tecla en el chexkbox de corr 2*/
    private void jCCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo2KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de corr 3*/
    private void jCCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo3KeyPressed

    
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

    
    /*Cuando se presiona una tecla en el campo de no mandar corr*/
    private void jCMandKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMandKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMandKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de imprimir*/
    private void jCImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCImpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCImpKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar archivos*/
    private void jCMostAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMostAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMostAKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de guardar correos*/
    private void jCGuaCoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGuaCoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCGuaCoKeyPressed

            
    /*Cuando se presiona una tecla en el campo de ejecutivo 1*/
    private void jTEje1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEje1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEje1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de ejecutivo 2*/
    private void jTEje2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEje2KeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEje2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del ejecutivo 1*/
    private void jTEje1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEje1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEje1.setSelectionStart(0);jTEje1.setSelectionEnd(jTEje1.getText().length());        
        
    }//GEN-LAST:event_jTEje1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del ejecutivo 2*/
    private void jTEje2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEje2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEje2.setSelectionStart(0);jTEje2.setSelectionEnd(jTEje2.getText().length());
        
    }//GEN-LAST:event_jTEje2FocusGained

    
    /*Cuando se presiona una tecla en el radio button de ejecutivo 1*/
    private void jREje1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jREje1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jREje1KeyPressed

    
    /*Cuando se presiona una tecla en el radio button de ejecutivo 2*/
    private void jREje2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jREje2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jREje2KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de guardar ejecutivo*/
    private void jCGuaEjeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGuaEjeKeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCGuaEjeKeyPressed

    
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

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBusc1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusc1MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc1.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBusc1MouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBusc2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusc2MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc2.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBusc2MouseEntered

    
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
    private void jBBusc1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusc1MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc1.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBBusc1MouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBusc2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusc2MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc2.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBBusc2MouseExited

    
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

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTEje1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEje1FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTEje1.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEje1FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTEje2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEje2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTEje2.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEje2FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTNomProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomProvFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTNomProv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomProvFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripFocusLost

    
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
    private void jTAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAlmaFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAlma.setCaretPosition(0);
        
    }//GEN-LAST:event_jTAlmaFocusLost

    
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
    private void jTDescripAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlmaFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescripAlma.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripAlmaFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTValImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTValImpFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTValImp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTValImpFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTDescripMonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripMonFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescripMon.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripMonFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTExistFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTExistFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTExist.setCaretPosition(0);
        
    }//GEN-LAST:event_jTExistFocusLost

    
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
    private void jTDiaCredFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDiaCredFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDiaCred.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDiaCredFocusLost

    
    /*Cuando s pierde el foco del teclado en e lcampo del subtotal*/
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
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4 )
        {
            /*Marca o desmarca el checkbox de no mandar corr electrónico*/
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
            /*Marca o desmarca el checkbox de monstrar archivo*/
            if(jCMostA.isSelected())
                jCMostA.setSelected(false);
            else
                jCMostA.setSelected(true);            
        } 
        
    }/*Fin de void vKeyPressEscalable(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc1;
    private javax.swing.JButton jBBusc2;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JCheckBox jCCo1;
    private javax.swing.JCheckBox jCCo2;
    private javax.swing.JCheckBox jCCo3;
    private javax.swing.JCheckBox jCGuaCo;
    private javax.swing.JCheckBox jCGuaEje;
    private javax.swing.JCheckBox jCImp;
    private javax.swing.JCheckBox jCMand;
    private javax.swing.JCheckBox jCMostA;
    private javax.swing.JComboBox jComImp;
    private javax.swing.JComboBox jComMon;
    private javax.swing.JComboBox jComUni;
    private com.toedter.calendar.JDateChooser jDFEnt;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jREje1;
    private javax.swing.JRadioButton jREje2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTAlma;
    private javax.swing.JTextField jTCant;
    private javax.swing.JTextField jTCo1;
    private javax.swing.JTextField jTCo2;
    private javax.swing.JTextField jTCo3;
    private javax.swing.JTextField jTCodOp1;
    private javax.swing.JTextField jTCodOpl2;
    private javax.swing.JTextField jTCodProv1;
    private javax.swing.JTextField jTCodProv2;
    private javax.swing.JTextField jTDesc;
    private javax.swing.JTextField jTDescAd;
    private javax.swing.JTextField jTDescrip;
    private javax.swing.JTextField jTDescripAlma;
    private javax.swing.JTextField jTDescripMon;
    private javax.swing.JTextField jTDescripUnid;
    private javax.swing.JTextField jTDiaCred;
    private javax.swing.JTextField jTEje1;
    private javax.swing.JTextField jTEje2;
    private javax.swing.JTextField jTExist;
    private javax.swing.JTextField jTImp;
    private javax.swing.JTextField jTNomProv;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTProv;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTextField jTUltCost;
    private javax.swing.JTextField jTValImp;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
