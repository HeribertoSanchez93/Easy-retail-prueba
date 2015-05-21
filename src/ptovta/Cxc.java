//Paquete
package ptovta;

/*Importaciones*/
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.awt.Cursor;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import static ptovta.Princip.bIdle;
import report.RepCXC;
import vis.VisCxc1;
import vis.VisCxc2;


/*Clase para controlar cxc*/
public class Cxc extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Declara variables de instancia*/           
    private int                 iContFi;
            
    /*Contador para modificar tabla*/
    private int                 iContCellEd;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean             bSel;
    
    /*Declara las variables originales de la tabla 1*/
    private String              sFolOri;
    private String              sSerOri;
    private String              sEmpOri;
    private String              sNomOri;
    private String              sImpoOri;
    private String              sImpueOri;
    private String              sTotOri;
    private String              sTotAbonOri;
    private String              sPLiqOri;
    private String              sFOri;
    private String              sFVencOri;
    private String              sSucOri;
    private String              sCajOri;
    private String              sEstacOri;
    private String              sNomEstacOri;
    private String              sEstadOri;    
    
    /*Declara las variables originales de la tabla 2*/
    private String              sVtaOri;    
    private String              sTipDocOri;    
    private String              sProdOri;    
    private String              sCantOri;    
    private String              sUnidOri;            
    private String              sDescOri;    
    private String              sMonOri;        
    private String              sPreOri;          
    private String              sDescripOri;  
    
        
    //Contador para el folio de banco
    private int                 iFolBanc  = 0;
    
    /*Constructor sin argumentos*/
    public Cxc() 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();

        /*Esconde la columna del ID*/
        jTab1.getColumnModel().getColumn(17).setMinWidth(0);
        jTab1.getColumnModel().getColumn(17).setMaxWidth(0);
        
        /*Establece el tamaño de las columnas de la tabla de encabezados*/
        jTab1.getColumnModel().getColumn(3).setPreferredWidth(160);
        jTab1.getColumnModel().getColumn(4).setPreferredWidth(300);
        jTab1.getColumnModel().getColumn(10).setPreferredWidth(150);
        jTab1.getColumnModel().getColumn(10).setPreferredWidth(160);
        jTab1.getColumnModel().getColumn(11).setPreferredWidth(160);
        jTab1.getColumnModel().getColumn(15).setPreferredWidth(300);
        
        /*Establece el tamaño de las columnas de la tabla de partidas*/
        jTab2.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTab2.getColumnModel().getColumn(7).setPreferredWidth(400);
        
        /*Crea el listener para cuando se cambia de selección en la tabla de encabezados*/
        jTab1.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {                           
                /*Si no hay selección entonces regresa*/                
                if(jTab1.getSelectedRow()==-1)
                    return;
                
                /*Obtiene lo pendiente de liquidar*/
                String sAbon    = jTab1.getValueAt(jTab1.getSelectedRow(), 9).toString();
                
                /*Si tiene ( en la cadena entonces coloca 0 pesos, caso contrario la cantidad*/
                if(sAbon.contains("("))
                    jTAbon.setText("$0.00");
                else
                    jTAbon.setText(sAbon);
                
                /*Carga todos todas las partidas de la compra en la tabla de partidas*/
                vCargPart();               
            }
        });
        
        /*Crea el listener para cuando se termina de escribir en el campo del cliente*/
        jTCli.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) 
            {                
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) 
            {            
            }
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) 
            {
                /*Obtén cxc de la base de datos y cargalos en la tabla*/
                vCargCxc();
            }           
        });
        
        /*Crea el listener para cuando se termina de escribir en el campo de la clasificación del cliente*/
        jTClas.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) 
            {                
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) 
            {            
            }
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) 
            {
                /*Obtén cxc de la base de datos y cargalos en la tabla*/
                vCargCxc();
            }           
        });
        
        /*Crea el listener para cuando se termina de escribir en el campo de los días de vencido*/
        jTDia.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) 
            {                
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) 
            {            
            }
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) 
            {
                /*Desmarca los checkbox de pendientes y confirmadas*/
                jCPe.setSelected(false);
                jCCo.setSelected(false);

                /*Marca el checkbox de vencidas*/
                jCVen.setSelected(true);

                /*Obtén cxc de la base de datos y cargalos en la tabla*/
                vCargCxc();
            }           
        });

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBAbon);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Que las comlumnas de la tabla no se muevan*/
        jTab1.getTableHeader().setReorderingAllowed(false);
        jTab2.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("CXC, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en uno*/
        iContFi          = 1;
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab1.setAutoResizeMode     (0);
        jTab2.setAutoResizeMode     (0);
        
        /*Selecciona la fecha del día de hoy inicialmente para las fechas*/
        Date f = new Date();
        jDTDe.setDate   (f);
        jDTA.setDate    (f);

        /*Establece los campos de fecha para que solo se puedan modificar con el botón*/
        jDTDe.getDateEditor().setEnabled    (false);
        jDTA.getDateEditor().setEnabled     (false);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nom de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab1.getModel());
        jTab1.setRowSorter      (trs);
        trs.setSortsOnUpdates   (true);
        
        /*Pon el foco del teclado en el campo de la cliente*/
        jTCli.grabFocus();
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab1.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,   null);
        jTab1.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,  null);
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,   null);
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,  null);
        
        /*Carga todos los cxc en la tabla*/
        vCargCxc();
                
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para cuando se cambia de selección en la tabla 1*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces regresa*/                
                if(jTab1.getSelectedRow()==-1)
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
                        sFolOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString();
                        sSerOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString();
                        sEmpOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 3).toString();
                        sNomOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 4).toString();
                        sImpoOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 5).toString();
                        sImpueOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 6).toString();
                        sTotOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 7).toString();
                        sTotAbonOri    = jTab1.getValueAt(jTab1.getSelectedRow(), 8).toString();
                        sPLiqOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 9).toString();
                        sFOri          = jTab1.getValueAt(jTab1.getSelectedRow(), 10).toString();
                        sFVencOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 11).toString();
                        sSucOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 12).toString();
                        sCajOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 13).toString();
                        sEstacOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 14).toString();
                        sNomEstacOri   = jTab1.getValueAt(jTab1.getSelectedRow(), 15).toString();
                        sEstadOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 16).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab1.setValueAt(sFolOri,       jTab1.getSelectedRow(), 1);                        
                        jTab1.setValueAt(sSerOri,       jTab1.getSelectedRow(), 2);                        
                        jTab1.setValueAt(sEmpOri,       jTab1.getSelectedRow(), 3);                        
                        jTab1.setValueAt(sNomOri,       jTab1.getSelectedRow(), 4);                        
                        jTab1.setValueAt(sImpoOri,      jTab1.getSelectedRow(), 5);                        
                        jTab1.setValueAt(sImpueOri,     jTab1.getSelectedRow(), 6);                        
                        jTab1.setValueAt(sTotOri,       jTab1.getSelectedRow(), 7);                        
                        jTab1.setValueAt(sTotAbonOri,   jTab1.getSelectedRow(), 8);                        
                        jTab1.setValueAt(sPLiqOri,      jTab1.getSelectedRow(), 9);                        
                        jTab1.setValueAt(sFOri,         jTab1.getSelectedRow(), 10);                        
                        jTab1.setValueAt(sFVencOri,     jTab1.getSelectedRow(), 11);                        
                        jTab1.setValueAt(sSucOri,       jTab1.getSelectedRow(), 12);                        
                        jTab1.setValueAt(sCajOri,       jTab1.getSelectedRow(), 13);                        
                        jTab1.setValueAt(sEstacOri,     jTab1.getSelectedRow(), 14);                        
                        jTab1.setValueAt(sNomEstacOri,  jTab1.getSelectedRow(), 15);                        
                        jTab1.setValueAt(sEstadOri,     jTab1.getSelectedRow(), 16);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla 1*/
        jTab1.addPropertyChangeListener(pro);
        
        /*Crea el listener para cuando se cambia de selección en la tabla 2*/
        pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtén la propiedad que a sucedio en el control*/
                String pr = event.getPropertyName();                                
                                
                /*Si no hay selecciòn de fila entonces regresa*/
                if(jTab2.getSelectedRow()==-1)
                    return;
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pr)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sFOri           = jTab2.getValueAt(jTab2.getSelectedRow(), 1).toString();
                        sVtaOri         = jTab2.getValueAt(jTab2.getSelectedRow(), 2).toString();
                        sTipDocOri      = jTab2.getValueAt(jTab2.getSelectedRow(), 3).toString();
                        sProdOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 4).toString();
                        sCantOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 5).toString();
                        sUnidOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 6).toString();
                        sDescripOri     = jTab2.getValueAt(jTab2.getSelectedRow(), 7).toString();
                        sDescOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 8).toString();
                        sMonOri         = jTab2.getValueAt(jTab2.getSelectedRow(), 9).toString();
                        sImpueOri       = jTab2.getValueAt(jTab2.getSelectedRow(), 10).toString();
                        sPreOri         = jTab2.getValueAt(jTab2.getSelectedRow(), 11).toString();
                        sImpoOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 12).toString();                        
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab2.setValueAt(sFOri,         jTab2.getSelectedRow(), 1);                        
                        jTab2.setValueAt(sVtaOri,       jTab2.getSelectedRow(), 2);                        
                        jTab2.setValueAt(sTipDocOri,    jTab2.getSelectedRow(), 3);                        
                        jTab2.setValueAt(sProdOri,      jTab2.getSelectedRow(), 4);                        
                        jTab2.setValueAt(sCantOri,      jTab2.getSelectedRow(), 5);                        
                        jTab2.setValueAt(sUnidOri,      jTab2.getSelectedRow(), 6);                        
                        jTab2.setValueAt(sDescripOri,   jTab2.getSelectedRow(), 7);                        
                        jTab2.setValueAt(sDescOri,      jTab2.getSelectedRow(), 8);                        
                        jTab2.setValueAt(sMonOri,       jTab2.getSelectedRow(), 9);                        
                        jTab2.setValueAt(sImpueOri,     jTab2.getSelectedRow(), 10);                        
                        jTab2.setValueAt(sPreOri,       jTab2.getSelectedRow(), 11);                        
                        jTab2.setValueAt(sImpoOri,      jTab2.getSelectedRow(), 12);                                                
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla 2*/
        jTab2.addPropertyChangeListener(pro);
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene el último folio usado para los pagos de la base de datos*/        
        try
        {
            sQ = "SELECT IFNULL(MAX(fol),0) + 1 AS maxfol FROM cxc";                                    
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colocalo en el control*/
            if(rs.next())
                jTFol.setText(rs.getString("maxfol"));
        }
        catch(SQLException expnSQL)
        {            
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
	    
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de public Cxc() */

    
    /*Carga todos todas las partidas de la venta en la tabla de partidas*/
    private void vCargPart()
    {
        /*Obtiene el documento y la serie de la fila seleccionada*/
        String sNoRefer = jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString();
        String sSer     = jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString();
                
        /*Borra la tabla de partidas de la venta*/
        DefaultTableModel dm = (DefaultTableModel)jTab2.getModel();
        dm.setRowCount(0);
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene el cliente de esa venta*/
        String sCli    = "";
        try
        {
            sQ = "SELECT codemp FROM vtas WHERE noser = '" + sSer + "' AND norefer = '" + sNoRefer + "'";                                    
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCli   = rs.getString("codemp");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
        
        /*Búsca la información del cliente y colocala en los controles*/
        vInfoCli(sCli);                
        
        /*Coloca cadena vacia en el campo del nombre del cliente ya que la función de arriba lo coloca*/
        jTNom.setText("");       
        
        /*Obtiene las partidas de la venta*/
        int iContFiPart = 1;
        try
        {
            sQ = "SELECT partvta.FALT, partvta.VTA, partvta.TIPDOC, partvta.CANT, partvta.PROD, partvta.UNID, prods.DESCRIP, partvta.DESCU, partvta.MON, partvta.IMPUE, partvta.PRE, partvta.IMPO FROM partvta LEFT OUTER JOIN vtas ON vtas.VTA = partvta.VTA LEFT OUTER JOIN prods ON prods.PROD = partvta.PROD WHERE vtas.NOSER = '" + sSer + "' AND vtas.NOREFER = '" + sNoRefer + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene los totales*/
                String sImpue   = rs.getString("impue");                
                String sImpo    = rs.getString("impo");                
                String sPre     = rs.getString("pre");                
                       
                /*Dale formato de moneda a los impos*/                
                double dCant    = Double.parseDouble(sImpue);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sImpue          = n.format(dCant);
                dCant           = Double.parseDouble(sImpo);                
                sImpo           = n.format(dCant);
                dCant           = Double.parseDouble(sPre);                
                sPre            = n.format(dCant);
                
                /*Agrega los registros en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab2.getModel();
                Object nu[]             = {iContFiPart, rs.getString("partvta.FALT"), rs.getString("partvta.VTA"), rs.getString("partvta.TIPDOC"), rs.getString("partvta.PROD"), rs.getString("partvta.CANT"), rs.getString("partvta.UNID"), rs.getString("prods.DESCRIP"), rs.getString("partvta.DESCU"), rs.getString("partvta.MON"), sImpue, sPre, sImpo};
                te.addRow(nu);
                
                /*Aumenta el contador de filas de las partidas*/
                ++iContFiPart;                                               
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
        
    }/*Fin de private void vCargPart()*/
                
                
    /*Obtén cxc de la base de datos y cargalos en la tabla*/
    private void vCargCxc()
    {   
        /*Limpia la tabla de cxc y de partidas*/
        DefaultTableModel dm    = (DefaultTableModel)jTab1.getModel();
        dm.setRowCount(0);
        dm                      = (DefaultTableModel)jTab2.getModel();
        dm.setRowCount(0);
        
        /*Si no hay nada marcado entonces que regrese*/
        if(!jCPe.isSelected() && !jCCo.isSelected() && !jCVen.isSelected() && !jCCa.isSelected())
            return;
                        
        /*Obtiene las fechas de y a*/        
        Date        fD          = jDTDe.getDate();
        Date        fA          = jDTA.getDate();
        
        /*Si alguon es nulo entonces que regrese*/
        if(fD==null || fA ==null)
            return;        
                
        /*Obtiene las fechas con formato*/
        SimpleDateFormat sd     = new SimpleDateFormat("yyy-MM-dd");
        String      sD          = sd.format(fD);        
        String      sA          = sd.format(fA);
                                
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab1.getModel();                    
                
        /*Reinicia el contador de filas*/
        iContFi             = 1;
        
        /*Cre la condición de la fecha*/
        String sConFech     = "AND DATE(a.FALT) >= '" + sD + "' AND DATE(a.FALT) <= '" + sA + "' ";        
        
        /*Crea la condición del cliente*/
        String sCondEm      = "";
        if(jTCli.getText().compareTo("")!=0)
            sCondEm         = " AND empre = '" + jTCli.getText().trim() + "'";        
     
        /*Si el cliente es cadena vacia entonces no habrá condición*/
        if(jTCli.getText().trim().compareTo("")==0)
            sCondEm         = "";
        
        /*Crea la condición de la clasificación del cliente*/
        String sCondClas    = "";
        if(jTClas.getText().compareTo("")!=0)
            sCondClas       = " AND emps.CODCLAS = '" + jTClas.getText().trim() + "'";        
        
        /*Si la clasificación del cliente es cadena vacia entonces no habrá filtro para esto*/
        if(jTClas.getText().compareTo("")==0)
            sCondClas       = "";
        
        /*Obtiene los días de vencido*/        
        String sDia     = jTDia.getText();
        
        /*Crea la consulta para el estado*/
        String sEsta;
        if(jCPe.isSelected() && jCCo.isSelected() && jCVen.isSelected())
            sEsta       = " AND (carg > abon OR carg <= abon OR DATE(a.FVENC) <= DATE_SUB(DATE(now()), INTERVAL " + sDia + " DAY))";        
        else if(jCPe.isSelected() && jCCo.isSelected())
            sEsta       = " AND (carg > abon OR carg <= abon)";
        else if(jCPe.isSelected() && jCVen.isSelected())
            sEsta       = " AND (carg > abon OR DATE(a.FVENC) <= DATE_SUB(DATE(now()), INTERVAL " + sDia + " DAY))";
        else if(jCCo.isSelected() && jCVen.isSelected())
            sEsta       = " AND (carg <= abon OR DATE(a.FVENC) <= DATE_SUB(DATE(now()), INTERVAL " + sDia + " DAY))";
        else if(jCCo.isSelected())
            sEsta       = " AND carg <= abon";                                              
        else if(jCPe.isSelected())
            sEsta       = " AND carg > abon";                                              
        else if(jCVen.isSelected())
            sEsta       = " AND DATE(a.FVENC) <= DATE_SUB(DATE(now()), INTERVAL " + sDia + " DAY)"; 
        else 
            sEsta       = "";        
        
        //Crea la condición para los estados de los documentos
        String sEstadDoc= " vtas.ESTAD IN('CO','PE', 'DEVP')";
        
        //Si va a mostrar las canceladas
        if(jCCa.isSelected())
        {
            //Filtros para cuando tambien se requieren los otros dos tipos
            sEstadDoc = " vtas.ESTAD IN('CA'";
            if(jCCo.isSelected()||jCPe.isSelected())
                sEstadDoc =sEstadDoc + ",'CO','PE', 'DEVP'";
            sEstadDoc =sEstadDoc + ")";
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
        
        /*Trae el cxc de la base de datos y cargalos en la tabla*/
        try
        {   sQ = "SELECT sucuu, nocajj, estacs.NOM, id_Idd, a.NOREFER, a.NOSER, serr, empre, a.SUBTOT, a.IMPUE, a.TOT, vtas.estad, DATE(a.FVENC) AS fven, fdoc, emps.NOM, a.ESTAC FROM (SELECT fdoc, id_id AS id_idd, falt, fvenc, empre, subtot, impue, tot, noser, ser AS serr, sucu AS sucuu, norefer, nocaj AS nocajj, estac, SUM(carg) AS carg, SUM(abon) AS abon FROM cxc GROUP BY norefer, noser, empre)a LEFT OUTER JOIN estacs ON estacs.ESTAC = a.ESTAC LEFT OUTER JOIN emps ON CONCAT_WS('',emps.SER, emps.CODEMP) = empre LEFT OUTER JOIN vtas ON CONCAT_WS('', vtas.NOSER, vtas.NOREFER) = CONCAT_WS('', a.NOSER, a.NOREFER) WHERE " + sEstadDoc + " " + sConFech + " " + sCondClas + " " + sCondEm + "  " + sEsta +  " GROUP BY id_idd";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene los totales*/
                String sSubTot  = rs.getString("subtot");
                String sImpue   = rs.getString("impue");
                String sTot     = rs.getString("tot");               
                String estado   = rs.getString("estad");
                
                /*Obtiene el total de abonos de este CXC*/
                String sAbon    = sTotAbon(con, rs.getString("norefer"), rs.getString("noser"), rs.getString("empre"));
                
                /*Obtiene el saldo pendiente de ese CXC*/
                String sPend    = Double.toString(Double.parseDouble(sTot) - Double.parseDouble(sAbon));
                
                /*Si hubo error entonces regresa*/
                if(sAbon==null)
                    return;
                
                /*Dale formato de moneda a los totales*/                
                double dCant    = Double.parseDouble(sSubTot);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sSubTot         = n.format(dCant);
                dCant           = Double.parseDouble(sImpue);                
                sImpue          = n.format(dCant);
                dCant           = Double.parseDouble(sTot);                
                sTot            = n.format(dCant);
                dCant           = Double.parseDouble(sAbon);                
                sAbon           = n.format(dCant);
                dCant           = Double.parseDouble(sPend);                
                sPend           = n.format(dCant);
                            
                /*Agregalo a la tabla*/
                Object nu[]             = {iContFi, rs.getString("norefer"), rs.getString("noser"), rs.getString("empre"), rs.getString("emps.NOM"), sSubTot, sImpue,sTot, sAbon, sPend, rs.getString("fdoc"), rs.getString("fven"), rs.getString("sucuu"), rs.getString("nocajj"), rs.getString("estac"), rs.getString("estacs.NOM"),estado, rs.getString("id_idd")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas en uno*/
                ++iContFi;        
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

        /*Si hay datos entonces selecciona la primera fila*/
        if(jTab1.getRowCount()>0)
            jTab1.addRowSelectionInterval(0, 0);
        
    }/*Fin de private void vCargCxc()*/
            
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBAbon = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jTAbon = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab2 = new javax.swing.JTable();
        jCPe = new javax.swing.JCheckBox();
        jCCo = new javax.swing.JCheckBox();
        jBVerA = new javax.swing.JButton();
        jDTA = new com.toedter.calendar.JDateChooser();
        jDTDe = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jTCli = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTNom = new javax.swing.JTextField();
        jBCli = new javax.swing.JButton();
        jCVen = new javax.swing.JCheckBox();
        jBActua = new javax.swing.JButton();
        jBVis = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
        jBTab2 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jBRec3 = new javax.swing.JButton();
        jBRec1 = new javax.swing.JButton();
        jBRec2 = new javax.swing.JButton();
        jTDia = new javax.swing.JTextField();
        jTClas = new javax.swing.JTextField();
        jBClas = new javax.swing.JButton();
        jTClasDescrip = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTFol = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTCond = new javax.swing.JTextField();
        jBLib = new javax.swing.JButton();
        jTComen = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTConcep = new javax.swing.JTextField();
        jBConcep = new javax.swing.JButton();
        jTFormPag = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jBFormPag = new javax.swing.JButton();
        jCCa = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jTFolBanc = new javax.swing.JTextField();

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

        jBAbon.setBackground(new java.awt.Color(255, 255, 255));
        jBAbon.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBAbon.setForeground(new java.awt.Color(0, 102, 0));
        jBAbon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/abona.png"))); // NOI18N
        jBAbon.setText("Abonar");
        jBAbon.setToolTipText("Abonar a Venta");
        jBAbon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBAbon.setNextFocusableComponent(jBVerA);
        jBAbon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAbonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAbonMouseExited(evt);
            }
        });
        jBAbon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbonActionPerformed(evt);
            }
        });
        jBAbon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAbonKeyPressed(evt);
            }
        });
        jP1.add(jBAbon, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 110, 120, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setName(""); // NOI18N
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 320, 120, 30));

        jTAbon.setText("$0.00");
        jTAbon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAbon.setNextFocusableComponent(jCPe);
        jTAbon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAbonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAbonFocusLost(evt);
            }
        });
        jTAbon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAbonKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTAbonKeyTyped(evt);
            }
        });
        jP1.add(jTAbon, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 40, 60, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("A:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 40, -1));

        jTab1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Folio", "Serie", "Cliente", "Nombre", "Subtotal", "Impuesto", "Total", "Total Abonos", "Pendiente Liquidar", "Fecha", "Fecha Vencimiento", "Sucursal", "Caja", "Usuario", "Nombre Usuario", "Estado", "id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab1.setGridColor(new java.awt.Color(255, 255, 255));
        jTab1.setNextFocusableComponent(jTab2);
        jTab1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTab1KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab1);
        if (jTab1.getColumnModel().getColumnCount() > 0) {
            jTab1.getColumnModel().getColumn(13).setResizable(false);
            jTab1.getColumnModel().getColumn(15).setResizable(false);
            jTab1.getColumnModel().getColumn(16).setResizable(false);
        }

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 830, 230));

        jTab2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Fecha", "Venta", "Tipo Doc", "Producto", "Qty", "Unidad", "Descripción", "Descuento", "Moneda", "Impuesto", "Precio", "Importe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab2.setColumnSelectionAllowed(true);
        jTab2.setGridColor(new java.awt.Color(255, 255, 255));
        jTab2.setNextFocusableComponent(jBLib);
        jTab2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTab2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTab2KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab2);
        jTab2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 950, 170));

        jCPe.setBackground(new java.awt.Color(255, 255, 255));
        jCPe.setSelected(true);
        jCPe.setText("PE");
        jCPe.setToolTipText("Documentos aun pendientes");
        jCPe.setNextFocusableComponent(jCCo);
        jCPe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCPeActionPerformed(evt);
            }
        });
        jCPe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCPeKeyPressed(evt);
            }
        });
        jP1.add(jCPe, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 20, 80, -1));

        jCCo.setBackground(new java.awt.Color(255, 255, 255));
        jCCo.setText("CO");
        jCCo.setToolTipText("Documentos confirmados");
        jCCo.setNextFocusableComponent(jCCa);
        jCCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCCoActionPerformed(evt);
            }
        });
        jCCo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCoKeyPressed(evt);
            }
        });
        jP1.add(jCCo, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 40, 80, -1));

        jBVerA.setBackground(new java.awt.Color(255, 255, 255));
        jBVerA.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVerA.setForeground(new java.awt.Color(0, 102, 0));
        jBVerA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/abon.png"))); // NOI18N
        jBVerA.setText("Abonos");
        jBVerA.setToolTipText("Ver Todos los Abonos de Compra(s) (F2)");
        jBVerA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVerA.setNextFocusableComponent(jBActua);
        jBVerA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVerAMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVerAMouseExited(evt);
            }
        });
        jBVerA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVerAActionPerformed(evt);
            }
        });
        jBVerA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVerAKeyPressed(evt);
            }
        });
        jP1.add(jBVerA, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 140, 120, 30));

        jDTA.setNextFocusableComponent(jTConcep);
        jDTA.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDTAPropertyChange(evt);
            }
        });
        jDTA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDTAKeyPressed(evt);
            }
        });
        jP1.add(jDTA, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 130, -1));

        jDTDe.setNextFocusableComponent(jDTA);
        jDTDe.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDTDePropertyChange(evt);
            }
        });
        jDTDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDTDeKeyPressed(evt);
            }
        });
        jP1.add(jDTDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, 130, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Clasificación:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 80, -1));

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
        jP1.add(jTCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 120, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("De:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 40, -1));

        jTNom.setEditable(false);
        jTNom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNom.setNextFocusableComponent(jTClas);
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
        jP1.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 230, 20));

        jBCli.setBackground(new java.awt.Color(255, 255, 255));
        jBCli.setText("...");
        jBCli.setToolTipText("Buscar Cliente(s)");
        jBCli.setNextFocusableComponent(jTNom);
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
        jP1.add(jBCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 30, 20));

        jCVen.setBackground(new java.awt.Color(255, 255, 255));
        jCVen.setText("Vencido a:");
        jCVen.setNextFocusableComponent(jTDia);
        jCVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCVenActionPerformed(evt);
            }
        });
        jCVen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVenKeyPressed(evt);
            }
        });
        jP1.add(jCVen, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 20, 90, 20));

        jBActua.setBackground(new java.awt.Color(255, 255, 255));
        jBActua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBActua.setForeground(new java.awt.Color(0, 102, 0));
        jBActua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actualizar.png"))); // NOI18N
        jBActua.setText("Actualizar");
        jBActua.setToolTipText("Actualizar Registros (F5)");
        jBActua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBActua.setNextFocusableComponent(jBVis);
        jBActua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBActuaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBActuaMouseExited(evt);
            }
        });
        jBActua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBActuaActionPerformed(evt);
            }
        });
        jBActua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBActuaKeyPressed(evt);
            }
        });
        jP1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 170, 120, 30));

        jBVis.setBackground(new java.awt.Color(255, 255, 255));
        jBVis.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVis.setForeground(new java.awt.Color(0, 102, 0));
        jBVis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/visor.png"))); // NOI18N
        jBVis.setText("Reporte");
        jBVis.setToolTipText("Reporte (F6)");
        jBVis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVis.setNextFocusableComponent(jBRec1);
        jBVis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVisMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVisMouseExited(evt);
            }
        });
        jBVis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVisActionPerformed(evt);
            }
        });
        jBVis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVisKeyPressed(evt);
            }
        });
        jP1.add(jBVis, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 200, 120, 30));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 10, 20));

        jBTab2.setBackground(new java.awt.Color(0, 153, 153));
        jBTab2.setToolTipText("Mostrar Tabla en Grande");
        jBTab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTab2ActionPerformed(evt);
            }
        });
        jBTab2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTab2KeyPressed(evt);
            }
        });
        jP1.add(jBTab2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(806, 530, 150, -1));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar todo");
        jBTod.setToolTipText("Marcar Todos los Registros de la Tabla (Alt+T)");
        jBTod.setNextFocusableComponent(jTab1);
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 102, 130, 18));

        jBRec3.setBackground(new java.awt.Color(255, 255, 255));
        jBRec3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBRec3.setForeground(new java.awt.Color(0, 102, 0));
        jBRec3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/rec3.png"))); // NOI18N
        jBRec3.setText("Correo");
        jBRec3.setToolTipText("Enviar Correo Electrónico de Recordatorio 3");
        jBRec3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBRec3.setNextFocusableComponent(jBSal);
        jBRec3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBRec3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBRec3MouseExited(evt);
            }
        });
        jBRec3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRec3ActionPerformed(evt);
            }
        });
        jBRec3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBRec3KeyPressed(evt);
            }
        });
        jP1.add(jBRec3, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 290, 120, 30));

        jBRec1.setBackground(new java.awt.Color(255, 255, 255));
        jBRec1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBRec1.setForeground(new java.awt.Color(0, 102, 0));
        jBRec1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/rec1.png"))); // NOI18N
        jBRec1.setText("Correo");
        jBRec1.setToolTipText("Enviar Correo Electrónico de Recordatorio 1");
        jBRec1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBRec1.setNextFocusableComponent(jBRec2);
        jBRec1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBRec1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBRec1MouseExited(evt);
            }
        });
        jBRec1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRec1ActionPerformed(evt);
            }
        });
        jBRec1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBRec1KeyPressed(evt);
            }
        });
        jP1.add(jBRec1, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 230, 120, 30));

        jBRec2.setBackground(new java.awt.Color(255, 255, 255));
        jBRec2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBRec2.setForeground(new java.awt.Color(0, 102, 0));
        jBRec2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/rec2.png"))); // NOI18N
        jBRec2.setText("Correo");
        jBRec2.setToolTipText("Enviar Correo Electrónico de Recordatorio 2");
        jBRec2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBRec2.setNextFocusableComponent(jBRec3);
        jBRec2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBRec2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBRec2MouseExited(evt);
            }
        });
        jBRec2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRec2ActionPerformed(evt);
            }
        });
        jBRec2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBRec2KeyPressed(evt);
            }
        });
        jP1.add(jBRec2, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 260, 120, 30));

        jTDia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTDia.setText("30");
        jTDia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDia.setNextFocusableComponent(jTab1);
        jTDia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDiaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDiaFocusLost(evt);
            }
        });
        jTDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDiaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDiaKeyTyped(evt);
            }
        });
        jP1.add(jTDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 20, 30, 20));

        jTClas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTClas.setNextFocusableComponent(jBClas);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTClasKeyTyped(evt);
            }
        });
        jP1.add(jTClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 120, 20));

        jBClas.setBackground(new java.awt.Color(255, 255, 255));
        jBClas.setText("...");
        jBClas.setToolTipText("Buscar Clasificación(es)");
        jBClas.setNextFocusableComponent(jTClasDescrip);
        jBClas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBClasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBClasMouseExited(evt);
            }
        });
        jBClas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBClasActionPerformed(evt);
            }
        });
        jBClas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBClasKeyPressed(evt);
            }
        });
        jP1.add(jBClas, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 30, 20));

        jTClasDescrip.setEditable(false);
        jTClasDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTClasDescrip.setNextFocusableComponent(jTCond);
        jTClasDescrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTClasDescripFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTClasDescripFocusLost(evt);
            }
        });
        jTClasDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTClasDescripKeyPressed(evt);
            }
        });
        jP1.add(jTClasDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 230, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Comentario:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 120, -1));

        jTFol.setText("0");
        jTFol.setToolTipText("Consecutivo manual");
        jTFol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFolFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFolFocusLost(evt);
            }
        });
        jTFol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFolKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTFolKeyTyped(evt);
            }
        });
        jP1.add(jTFol, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 60, 60, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("$ Abono y folio:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 110, -1));

        jTCond.setEditable(false);
        jTCond.setBackground(new java.awt.Color(255, 255, 204));
        jTCond.setForeground(new java.awt.Color(0, 153, 0));
        jTCond.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTCond.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCond.setFocusable(false);
        jTCond.setNextFocusableComponent(jTComen);
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
        jP1.add(jTCond, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 380, 20));

        jBLib.setBackground(new java.awt.Color(255, 255, 255));
        jBLib.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBLib.setForeground(new java.awt.Color(0, 102, 0));
        jBLib.setText("Liberar");
        jBLib.setToolTipText("Liberar saldo del (los) cliente(s)");
        jBLib.setName(""); // NOI18N
        jBLib.setNextFocusableComponent(jBAbon);
        jBLib.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBLibMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBLibMouseExited(evt);
            }
        });
        jBLib.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLibActionPerformed(evt);
            }
        });
        jBLib.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBLibKeyPressed(evt);
            }
        });
        jP1.add(jBLib, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 60, 80, -1));

        jTComen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTComen.setNextFocusableComponent(jBAbon);
        jTComen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTComenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTComenFocusLost(evt);
            }
        });
        jTComen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTComenKeyPressed(evt);
            }
        });
        jP1.add(jTComen, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 250, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Cliente:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 80, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Concepto:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 120, -1));

        jTConcep.setToolTipText("Concepto del pago del abono");
        jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTConcep.setNextFocusableComponent(jBConcep);
        jTConcep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTConcepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTConcepFocusLost(evt);
            }
        });
        jTConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTConcepKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTConcepKeyTyped(evt);
            }
        });
        jP1.add(jTConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 90, 20));

        jBConcep.setBackground(new java.awt.Color(255, 255, 255));
        jBConcep.setText("jButton1");
        jBConcep.setToolTipText("Buscar concepto");
        jBConcep.setNextFocusableComponent(jTFormPag);
        jBConcep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBConcepMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBConcepMouseExited(evt);
            }
        });
        jBConcep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBConcepActionPerformed(evt);
            }
        });
        jBConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBConcepKeyPressed(evt);
            }
        });
        jP1.add(jBConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, 30, 20));

        jTFormPag.setToolTipText("Forma de pago del abono");
        jTFormPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFormPag.setNextFocusableComponent(jBFormPag);
        jTFormPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFormPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFormPagFocusLost(evt);
            }
        });
        jTFormPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFormPagKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTFormPagKeyTyped(evt);
            }
        });
        jP1.add(jTFormPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 100, 90, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Forma pago:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 120, -1));

        jBFormPag.setBackground(new java.awt.Color(255, 255, 255));
        jBFormPag.setText("jButton1");
        jBFormPag.setToolTipText("Buscar forma de pago");
        jBFormPag.setNextFocusableComponent(jTAbon);
        jBFormPag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBFormPagMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBFormPagMouseExited(evt);
            }
        });
        jBFormPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFormPagActionPerformed(evt);
            }
        });
        jBFormPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBFormPagKeyPressed(evt);
            }
        });
        jP1.add(jBFormPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 100, 30, 20));

        jCCa.setBackground(new java.awt.Color(255, 255, 255));
        jCCa.setText("CA");
        jCCa.setToolTipText("Documentos cancelados");
        jCCa.setNextFocusableComponent(jCVen);
        jCCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCCaActionPerformed(evt);
            }
        });
        jCCa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCaKeyPressed(evt);
            }
        });
        jP1.add(jCCa, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 63, 70, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Folio de banco:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 120, -1));

        jTFolBanc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jP1.add(jTFolBanc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 200, 20));

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
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene el total de abonos de este CXC*/
    private String sTotAbon(Connection con, String sNoRefer, String sNoSer, String sEmpre)
    {                        
        //Declara variables de la base de datos        
        ResultSet       rs;
        Statement       st;
        String sQ;
        
        
        
        
        /*Obtiene el total de abonos de ese CXC*/
        try
        {
            sQ = "SELECT IFNULL(SUM(abon),0) AS abon FROM cxc WHERE concep IN('ABON FAC', 'ABON TIK', 'ABON REM') AND norefer = '" + sNoRefer + "' AND noser = '" + sNoSer + "' AND empre = '" + sEmpre + "' AND carg = 0";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces devuel el resutlado*/
            if(rs.next())                            
                return rs.getString("abon");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return null;
        }  
        
        /*Devuelve nulo*/
        return null;
        
    }/*Fin de private String sTotAbon(Connection con, String sNoRefer, String sNoSer, String sEmpre)*/
                
        
    //Método para abonar a un documento
    private void vProcDoc()
    {
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un registro para abonarle.", "Abonar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();            
            return;            
        }

        //Si no a seleccionado la forma de pago entonces
        if(jTFormPag.getText().trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTFormPag.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una forma de pago.", "Forma pago", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control del abono y regresa*/
            jTFormPag.grabFocus();           
            return;
        }
        
        /*Si lo que quiere abonar es cero pesos entonces*/
        if(Double.parseDouble(jTAbon.getText().replace("$", "").replace(",", "")) <= 0)
        {
            /*Coloca el borde rojo*/                               
            jTAbon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No puedes abonar menos de $0.00.", "Abonar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control del abono y regresa*/
            jTAbon.grabFocus();           
            return;
        }
        
        //Si el abono es mayor a lo que esta pendiente
        if(Double.parseDouble(jTab1.getValueAt(jTab1.getSelectedRow(), 9).toString().replace("$", "").replace(",", "")) < Double.parseDouble(jTAbon.getText().replace("$", "").replace(",", "")))
        {
            /*Coloca el borde rojo*/                               
            jTAbon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No puedes abonar mas del saldo pendiente", "Abonar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control del abono y regresa*/
            jTAbon.grabFocus();           
            return;
        }
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
//Se checa que no sea vacio
        if(jTFolBanc.getText().trim().compareTo("")!=0)
        {
            //Se revisa que no este repetido el folio en un cxc
            String sResptra = Star.sTraUnCamp(con, "folbanc", "cxc", jTFolBanc.getText().trim() + "' AND concep <> 'ACA ABON");

            //Se revisa que no este repetido el folio en un cxp
            String sResptra2 = Star.sTraUnCamp(con, "folbanc", "cxp", jTFolBanc.getText().trim() + "' AND concep <> 'ACA ABON");

            //Si es nulo marca error
            if(sResptra==null||sResptra2==null)
                return;
            else if(sResptra.compareTo("no existe")!=0||sResptra2.compareTo("no existe")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                //Mensajea  
                JOptionPane.showMessageDialog(null, "El folio: " + jTFolBanc.getText() + " ya existe.", "Folio de banco", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                //Coloca el foco del teclado en el control
                jTFolBanc.grabFocus();

                //Coloca el borde rojo y regresa                               
                jTFolBanc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                return;
            }
        }
        
        //Comprueba si el tipo de pago existe
        int iResp   = Star.iExistTipPag(con, jTFormPag.getText().trim());
        
        //Si hubo error entonces regresa
        if(iResp==-1)
            return;
        
        //Si no existe entonces
        if(iResp==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            //Mensajea  
            JOptionPane.showMessageDialog(null, "El tipo de pago: " + jTFormPag.getText() + " no existe.", "Tipo de pago", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Coloca el borde rojo y regresa                               
            jTFormPag.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;
        String      sQ; 
        
        /*Obtiene el id del CXC*/        
        String  sId     = jTab1.getValueAt(jTab1.getSelectedRow(), 17).toString();         
       
        /*Contiene los datos del CXC actual*/
        String sNoRefer = "";
        String sNoSer   = "";
        String sEmpre   = "";
        String sSer     = "";
        String sSubTot  = "";
        String sTot     = "";
        String sImpue   = "";
        String sFVenc   = "";
        String sFDoc    = "";        
        
        /*Obtiene algunos datos de ese CXC*/
        try
        {
            sQ = "SELECT * FROM cxc WHERE id_id = " + sId;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sNoRefer    = rs.getString("norefer");
                sNoSer      = rs.getString("noser");
                sEmpre      = rs.getString("empre");
                sSer        = rs.getString("ser");
                sSubTot     = rs.getString("subtot");
                sTot        = rs.getString("tot");
                sImpue      = rs.getString("impue");
                sFVenc      = rs.getString("fvenc");
                sFDoc       = rs.getString("fdoc");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
        
        /*Obtiene la cantidad a abonar*/        
        String sAbon    = jTAbon.getText().replace("$", "").replace(",", "");
        
        /*Dale formato de moneda a la cantidad a abonar*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        double dCant    = Double.parseDouble(sAbon);                
        sAbon           = n.format(dCant);
        
        /*Preguntar al usuario si esta seguro de querer abonar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere abonar al cliente: " + jTab1.getValueAt(jTab1.getSelectedRow(), 4) + " la cantidad de \"" + sAbon + "\"", "Abonar", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Regresa*/
            return;
        }
        
        //Inserta CXC en la base de datos                
        if(Star.iInsCXCP(con, "cxc", sNoRefer, sNoSer, sEmpre, sSer, sSubTot, sImpue, sTot, "0", sAbon, "'" + sFVenc + "'", "'" + sFVenc + "'", "ABON FAC", jTFormPag.getText().trim(), jTFol.getText().trim(), jTComen.getText().trim(), jTConcep.getText().trim(), jTFolBanc.getText().trim())==-1)
            return;               
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Carga todos los cxc en la tabla*/
        vCargCxc();            
            
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Abono registrado con éxito.", "Abono", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
    }//Fin de private void vProcDoc()
    
    
    //Método para abonar por concepto
    private void vProcConcep()
    {
        //Si el campo del cliente es cadena vacia entonces
        if(jTCli.getText().trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente para el abono por concepto.", "Abonar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control del abono y regresa*/
            jTCli.grabFocus();           
            return;
        }
        
        //Si el campo del concepto es cadena vacia entonces
        if(jTConcep.getText().trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un concepto para el abono.", "Abonar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control del abono y regresa*/
            jTConcep.grabFocus();           
            return;
        }
        
        //Si el campo de la forma de pago es cadena vacia entonces
        if(jTFormPag.getText().trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTFormPag.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una forma de pago para el abono.", "Abonar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control del abono y regresa*/
            jTFormPag.grabFocus();           
            return;
        }
        
        /*Si lo que quiere abonar es cero pesos entonces*/
        if(Double.parseDouble(jTAbon.getText().replace("$", "").replace(",", "")) <= 0)
        {
            /*Coloca el borde rojo*/                               
            jTAbon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No puedes abonar menos de $0.00.", "Abonar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control del abono y regresa*/
            jTAbon.grabFocus();           
            return;
        }
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                        
        /*Obtiene la cantidad a abonar*/        
        String sAbon    = jTAbon.getText().replace("$", "").replace(",", "");
        
        /*Dale formato de moneda a la cantidad a abonar*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        double dCant    = Double.parseDouble(sAbon);                
        sAbon           = n.format(dCant);
        
        /*Preguntar al usuario si esta seguro de querer abonar*/        
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere abonar por concepto al cliente: " + jTCli.getText() + " la cantidad de: " + sAbon + "", "Abonar", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);            
            return;
        }
        
        //Se checa que no sea vacio
        if(jTFolBanc.getText().trim().compareTo("")!=0)
        {
            //Se revisa que no este repetido el folio en un cxc
            String sResptra = Star.sTraUnCamp(con, "folbanc", "cxc", jTFolBanc.getText().trim() + "' AND concep <> 'ACA ABON");

            //Se revisa que no este repetido el folio en un cxp
            String sResptra2 = Star.sTraUnCamp(con, "folbanc", "cxp", jTFolBanc.getText().trim() + "' AND concep <> 'ACA ABON");

            //Si es nulo marca error
            if(sResptra==null||sResptra2==null)
                return;
            else if(sResptra.compareTo("no existe")!=0||sResptra2.compareTo("no existe")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                //Mensajea  
                JOptionPane.showMessageDialog(null, "El folio: " + jTFolBanc.getText() + " ya existe.", "Folio de banco", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                //Coloca el foco del teclado en el control
                jTFolBanc.grabFocus();

                //Coloca el borde rojo y regresa                               
                jTFolBanc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                return;
            }
        }//Fin if(jTFolBanc.getText().trim().compareTo("")!=0)
        
        //Comprueba si el cliente existe
        int iResp   = Star.iExistCliProv(con, jTCli.getText().trim(), true);
        
        //Si hubo error entonces regresa
        if(iResp==-1)
            return;
        
        //Si no existe entonces
        if(iResp==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            //Mensajea  
            JOptionPane.showMessageDialog(null, "El cliente: " + jTCli.getText() + " no existe.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Coloca el foco del teclado en el control
            jTCli.grabFocus();
            
            //Coloca el borde rojo y regresa                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }
        
        //Comprueba si el concepto existe
        iResp       = Star.iExistConPag(con, jTConcep.getText().trim());
        
        //Si hubo error entonces regresa
        if(iResp==-1)
            return;
        
        //Si no existe entonces
        if(iResp==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            //Mensajea  
            JOptionPane.showMessageDialog(null, "El concepto: " + jTConcep.getText() + " no existe.", "Concepto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Coloca el foco del teclado en el control
            jTConcep.grabFocus();
            
            //Coloca el borde rojo y regresa                               
            jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }
        
        //Comprueba si el tipo de pago existe
        iResp       = Star.iExistTipPag(con, jTFormPag.getText().trim());
        
        //Si hubo error entonces regresa
        if(iResp==-1)
            return;
        
        //Si no existe entonces
        if(iResp==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            //Mensajea  
            JOptionPane.showMessageDialog(null, "El tipo de pago: " + jTFormPag.getText() + " no existe.", "Tipo de pago", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Coloca el foco del teclado en el control
            jTFormPag.grabFocus();
            
            //Coloca el borde rojo y regresa                               
            jTFormPag.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }
        
        //Inserta el CXC en la base de datos
        if(Star.iInsCXCP(con, "cxc", "", "", jTCli.getText().trim(), "", "0", "0", "0", "0", sAbon, "now()", "now()", "ABON FAC", jTFormPag.getText().trim(), "0", jTComen.getText().trim(), jTConcep.getText().trim(), jTFolBanc.getText().trim())==-1)
            return;               
                
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Carga todos los cxc en la tabla*/
        vCargCxc();            
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Abono por concepto registrado con éxito.", "Abono", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//Fin de private void vProcConcep()
    
    
    /*Cuando se presiona el botón de abonar*/
    private void jBAbonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbonActionPerformed

        //Pregunta al usuario el abono que va a aplicar de que tipo va a ser
        Object[] op = {"Concepto","Documento"};
        int iRes    = JOptionPane.showOptionDialog(this, "Selecciona el tipo de abono que deseas aplicar:", "Tipo de abono", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.CLOSED_OPTION)
            return; 
        
        //Determina la función dependiendo el tipo de abono que aplicara
        if(iRes==JOptionPane.YES_OPTION)
            vProcConcep();
        else
            vProcDoc();
                
    }//GEN-LAST:event_jBAbonActionPerformed
   
   
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
    /*Cuando se presiona una tecla en el botón de abonar*/
    private void jBAbonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAbonKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBAbonKeyPressed

        
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        this.dispose();        
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed
    
    
    /*Cuando se presiona una tecla en el campo de edición de abono*/
    private void jTAbonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAbonKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAbonKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de abono*/
    private void jTAbonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAbonFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAbon.setSelectionStart(0);jTAbon.setSelectionEnd(jTAbon.getText().length());        
        
    }//GEN-LAST:event_jTAbonFocusGained

        
    /*Cuando se presiona una  tecla en la tabla de cxc*/
    private void jTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTab1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTab1KeyPressed
    
    
    /*Cuando se pierde el foco del teclado en el campo de abono*/
    private void jTAbonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAbonFocusLost

        /*Coloca el cursor al principio del control*/
        jTAbon.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTAbon.getText().compareTo("")!=0)
            jTAbon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si es cadena vacia entonces*/
        if(jTAbon.getText().compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTAbon.setText("$0.00");
            return;
        }
        
        /*Dale formato de moneda a la cantidad*/
        String sAbon    = jTAbon.getText().replace("$", "").replace(",", "");        
        double dCant    = Double.parseDouble(sAbon);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sAbon           = n.format(dCant);
        
        /*Colocalo en el campo*/
        jTAbon.setText(sAbon);
                
    }//GEN-LAST:event_jTAbonFocusLost

                    
    /*Cuando se tipea una tecla en el campo de abono*/
    private void jTAbonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAbonKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTAbonKeyTyped

    
    /*Cuando se presiona una tecla en el botón de ver abonos*/
    private void jBVerAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVerAKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVerAKeyPressed

    
    /*Cuando se presiona una tecla en la tabla de partidas*/
    private void jTab2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTab2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTab2KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de pendientes*/
    private void jCPeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCPeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCPeKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de ya pagados*/
    private void jCCoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCoKeyPressed

    
    /*Cuando sucede una acción en el checkbox de pendientes*/
    private void jCPeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCPeActionPerformed

        /*Obtén cxc de la base de datos y cargalos en la tabla*/
        vCargCxc();
        
    }//GEN-LAST:event_jCPeActionPerformed

    
    /*Cuando sucede una acción en el checkbox de confirmados*/
    private void jCCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCCoActionPerformed

        /*Obtén cxc de la base de datos y cargalos en la tabla*/
        vCargCxc();
       
    }//GEN-LAST:event_jCCoActionPerformed

    
    /*Cuando se presiona una tecla en el control de fecha de*/
    private void jDTDeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDTDeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDTDeKeyPressed

    
    /*Cuando se presiona una tecla en el control de fecha a*/
    private void jDTAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDTAKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDTAKeyPressed
    
    
    /*Cuando cambia el valor en el control de*/
    private void jDTDePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDTDePropertyChange
        
        /*Obtén cxc de la base de datos y cargalos en la tabla*/
        vCargCxc();
        
    }//GEN-LAST:event_jDTDePropertyChange

    
    /*Cuando cambia la fecha en el control a*/
    private void jDTAPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDTAPropertyChange
        
        /*Obtén cxc de la base de datos y cargalos en la tabla*/
        vCargCxc();
        
    }//GEN-LAST:event_jDTAPropertyChange

    
    /*Cuando se gana el foco del teclado en el campo de la cliente*/
    private void jTCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCli.setSelectionStart(0);jTCli.setSelectionEnd(jTCli.getText().length());        
        
    }//GEN-LAST:event_jTCliFocusGained

    
    /*Cuando se presiona una tecla en el campo del cliente*/
    private void jTCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyPressed
        
        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar cliente*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBCli.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCliKeyPressed

    
    /*Cuando se presiona el botón de búscar*/
    private void jBCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCliActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTCli.getText(), 5, jTCli, jTNom, null, "", null);
        b.setVisible(true);
        
        /*Coloca el foco del teclado en el campo del código de la cliente*/
        jTCli.grabFocus();
        
        /*Búsca la información del cliente y colocala en los controles*/
        vInfoCli(jTCli.getText().trim());                
        
    }//GEN-LAST:event_jBCliActionPerformed

    
    /*Cuando se tipea una tecla en el campo de la cliente*/
    private void jTCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCliKeyTyped

    
    /*Búsca la información del cliente y colocala en los controles*/
    private void vInfoCli(String sCli)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Contiene algunos datos del cliente*/
        String sNom     = "";
        String sDiaCred = "";
        String sLimCred = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene información del cliente*/
        try
        {
            sQ = "SELECT nom, diacred, limtcred FROM emps WHERE CONCAT_WS('', emps.SER, emps.CODEMP ) = '" + sCli + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sNom        = rs.getString("nom");                                                                     
                sDiaCred    = rs.getString("diacred");
                sLimCred    = rs.getString("limtcred");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
	        
        /*Si el límite de crédito es vacio entonces que sea 0*/
        if(sLimCred.compareTo("")==0)
            sLimCred    = "0";
        
        /*Si los días de crédito son vacio entonces que sean 0*/
        if(sDiaCred.compareTo("")==0)
            sDiaCred    = "0";
        
        /*Dale formato de moneda al límite de crédito*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        double dCant    = Double.parseDouble(sLimCred);                
        sLimCred        = n.format(dCant);

        /*Coloca las condiciones del crédito*/
        jTCond.setText  ("Días: " + sDiaCred + " Límite: " + sLimCred);                                
        
        /*Obtiene el saldo que tiene pendiente de pagar el cliente*/
        String sPendPag = "0";
        try
        {
            sQ = "SELECT IFNULL((SUM(carg) - SUM(abon)),0) AS pendpag FROM cxc WHERE empre = '" + sCli + "'";  
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
        String sSaldDispo   = Double.toString(Double.parseDouble(sLimCred.replace("$", "").replace(",", "")) - Double.parseDouble(sPendPag));        

        /*Dale formato de moneda al saldo disponible*/        
        dCant               = Double.parseDouble(sSaldDispo);                
        sSaldDispo          = n.format(dCant);

        /*Agrega en el campo el saldo disponible*/
        String sTemp    = jTCond.getText();
        jTCond.setText(sTemp + " Saldo Disponible:" + sSaldDispo);
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Coloca el nombre y el caret al principio del control*/
        jTNom.setText(sNom);
        jTNom.setCaretPosition(0);
        
    }/*Fin de private void vInfoCli(String sProv)*/
        
        
    /*Cuando se pierde el foco del teclado en el control del cliente*/
    private void jTCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusLost

        /*Coloca el cursor al principio del control*/
        jTCli.setCaretPosition(0);
        
        /*Búsca la información del cliente y colocala en los controles*/
        vInfoCli(jTCli.getText().trim());                
        
    }//GEN-LAST:event_jTCliFocusLost

    
    /*Cuando se presiona el botón de abonos*/
    private void jBVerAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerAActionPerformed

        //Pregunta al usuario el abono que va a aplicar de que tipo va a ser
        Object[] op = {"Concepto","Documento"};
        int iRes    = JOptionPane.showOptionDialog(this, "Selecciona los abonos que deseas ver:", "Tipo de abono", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.CLOSED_OPTION)
            return; 
        
        //Determina la función dependiendo el tipo de abono que se desea ver
        if(iRes==JOptionPane.YES_OPTION)
        {
            //Si no a seleccionado un cliente entonces
            if(jTCli.getText().trim().compareTo("")==0)
            {
                //Mensajea
                JOptionPane.showMessageDialog(null, "Selecciona un cliente primero.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Coloca el borde rojo
                jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                //Coloca el foco del teclado en el control y regresa
                jTCli.grabFocus();
                return;
            }

            //Abre la base de datos                             
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;
        
            //Comprueba si el cliente existe
            int iResp   = Star.iExistCliProv(con, jTCli.getText().trim(), true);

            //Si hubo error entonces regresa
            if(iResp==-1)
                return;

            //Si no existe entonces
            if(iResp==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                //Mensajea  
                JOptionPane.showMessageDialog(null, "El cliente: " + jTCli.getText() + " no existe.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                //Coloca el foco del teclado en el control
                jTCli.grabFocus();

                //Coloca el borde rojo y regresa                               
                jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                return;
            }

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            //Muestra la forma para ver los abonos de ese cliente
            VAbons v    = new VAbons(null, "vtas", jTCli.getText().trim());
            v.setVisible(true);
            
        }//Fin de if(iRes==JOptionPane.YES_OPTION)
        else
        {
            /*Obtiene las filas seleccionadas*/
            int iSel[]              = jTab1.getSelectedRows();

            /*Si no se a seleccionado por lo menos un registro entonces*/
            if(iSel.length==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Selecciona por lo menos un registro para ver sus abonos.", "Abonos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en la tabla y regresa*/
                jTab1.grabFocus();                        
                return;
            }

            /*Recorre toda la selección del usuario*/                
            for(int x = iSel.length - 1; x >= 0; x--)
            {
                /*Obtiene el id de cxc*/        
                String sId  = jTab1.getValueAt(iSel[x], 17).toString();

                /*Muestra el formulario para ver los abonos*/
                VAbons v    = new VAbons(sId, "vtas", null);
                v.setVisible(true);
            }                    
            
        }//Fin de else                    
        
    }//GEN-LAST:event_jBVerAActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar cliente*/
    private void jBCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCliKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jBCliKeyPressed

    
    /*Cuando sucede un evento en el checkbox de vencidos*/
    private void jCVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCVenActionPerformed

        /*Obtén cxc de la base de datos y cargalos en la tabla*/
        vCargCxc();

    }//GEN-LAST:event_jCVenActionPerformed

    
    /*Cuando se presiona una tecla en el checbox de vencidos*/
    private void jCVenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVenKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCVenKeyPressed

    
    /*Cuando se presiona el botón de actualizar*/
    private void jBActuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActuaActionPerformed

        /*Obtén cxc de la base de datos y cargalos en la tabla*/
        vCargCxc();

    }//GEN-LAST:event_jBActuaActionPerformed

    
    /*Cuando se pesiona el botón de actualizar*/
    private void jBActuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBActuaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBActuaKeyPressed

    
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

    
    /*Cuando se presiona una tecla en el botón de visor*/
    private void jBVisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVisKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVisKeyPressed

    
    /*Cuando se presiona el botón de visor*/
    private void jBVisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVisActionPerformed
        
        /*Muestra la forma para ver los reportes de CXC*/
        RepCXC r = new RepCXC();
        r.setVisible(true);
        
    }//GEN-LAST:event_jBVisActionPerformed

    
    /*Cuando se presiona el botón de ver tabla 2*/
    private void jBTab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab1ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab1);       

    }//GEN-LAST:event_jBTab1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de mostrar tabla 1*/
    private void jBTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTab1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTab1KeyPressed

    
    /*Cuando se presiona el botón de ver tabla 2*/
    private void jBTab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab2ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab2);       

    }//GEN-LAST:event_jBTab2ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver tabla 2*/
    private void jBTab2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTab2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTab2KeyPressed

    
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
        if(jTab1.getRowCount()==0)
            return;
        
        /*Si están seleccionados los elementos en la tabla entonces*/
        if(bSel)
        {
            /*Coloca la bandera y deseleccionalos*/
            bSel    = false;
            jTab1.clearSelection();
        }
        /*Else deseleccionalos y coloca la bandera*/
        else
        {
            bSel    = true;
            jTab1.setRowSelectionInterval(0, jTab1.getModel().getRowCount()-1);
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
    private void jBCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCli.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCliMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBAbonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbonMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBAbon.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAbonMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVerAMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVerAMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVerA.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVerAMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBActuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBActua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBActuaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVisMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVisMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVis.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVisMouseEntered

    
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
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBAbonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbonMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBAbon.setBackground(colOri);
        
    }//GEN-LAST:event_jBAbonMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVerAMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVerAMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVerA.setBackground(colOri);
        
    }//GEN-LAST:event_jBVerAMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBActuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBActua.setBackground(colOri);
        
    }//GEN-LAST:event_jBActuaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVisMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVisMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVis.setBackground(colOri);
        
    }//GEN-LAST:event_jBVisMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se pierde el foco del teclado en el campo del nombre de la empresa*/
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTNom.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomFocusLost

    
    /*Cuando se presiona una tecla en el botón de recordatorio*/
    private void jBRec3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBRec3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBRec3KeyPressed

    
    /*Cuando el mouse entra en el botón de recordatorio*/
    private void jBRec3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRec3MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBRec3.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBRec3MouseEntered

    
    /*Cuando el mouse sale del botón de recordatorio*/
    private void jBRec3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRec3MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBRec3.setBackground(colOri);
        
    }//GEN-LAST:event_jBRec3MouseExited

    
    /*Cuando se presiona el botón de recordatorio*/
    private void jBRec3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRec3ActionPerformed
        
        /*Si no a seleccionado por lo menos un registro de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un registro para enviarle recordatorio 3.", "Recordatorio 3", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();
            return;
        }

        /*Preguntar al usuario si esta seguro de que querer enviar el correo*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres enviar el(los) correo(s) electrónico(s) de recordatorio 3?", "Recordatorio 3", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Obtiene toda la selección del usuario de la tabla*/
        int iSel[] = jTab1.getSelectedRows();

        /*Crea el arreglo de los clientes entonces emepiza a guardarlos en el arreglo*/
        String sEmps[] = new String[iSel.length];
        for(int x = 0; x < iSel.length; x++)
            sEmps[x] = jTab1.getValueAt(iSel[x], 3).toString();
        
        /*Declara variables final para el thread*/
        final String sEmpsFi[]    = sEmps;
        
        /*Mandale a todos esos clientes el correo de recordatorio 1*/
        (new Thread()
        {
            @Override
            public void run()
            {
                vMandRec(sEmpsFi, "REC3", "asunrec3", "cuerprec3");
            }
            
        }).start();

        //Muestra el loading
        Star.vMostLoading("");
        
    }//GEN-LAST:event_jBRec3ActionPerformed

    
    /*Cuando el mouse entra en el botón de recordatorio 1*/
    private void jBRec1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRec1MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBRec1.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBRec1MouseEntered

    
    /*Cuando el mouse sale del botón de recordatorio 1*/
    private void jBRec1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRec1MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBRec1.setBackground(colOri);
        
    }//GEN-LAST:event_jBRec1MouseExited


    /*Manda correo de cumpleaños automático y manual*/
    private void vMandRec(String sEmps[], String sRut, String sAsu, String sCue)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;        
        
        /*Declara variables del correo*/
        String      sServSMTPSal;
        String      sSMTPPort;
        String      sUsr;
        String      sContra;
        String      sActSSL;
        String      sAsun;
        String      sMens;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;                
        String      sQ;
        String      sQ2;
        
        /*Obtiene los datos del correo electrónico de este usuario y el asunto con cuerpo del correo*/
        try
        {
            sQ = "SELECT srvsmtpsal, portsmtp, actslenlog, usr, pass, " + sAsu + " AS asun, " + sCue + " AS cuerp FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene el asunto y el cuerpo del mensaje*/
                sAsun                   = rs.getString("asun");
                sMens                   = rs.getString("cuerp");
                
                /*Obtiene todos los datos de la consulta*/
                sServSMTPSal            = rs.getString("srvsmtpsal");
                sSMTPPort               = rs.getString("portsmtp");
                sUsr                    = rs.getString("usr");
                sContra                 = rs.getString("pass");
                
                /*Si activar ssl login esta activado entonces guarda true*/
                if(rs.getString("actslenlog").compareTo("1")==0)
                    sActSSL = "true";
                else
                    sActSSL = "false";

                /*Desencripta la contraseña*/
                sContra                = Star.sDecryp(sContra);                                        
            }
            /*Else no hay datos entonces regresa*/
            else
            {
                /*Ingresa en la base de datos el registor de que se fallo por que no hay datos de configuración del correo*/
                try 
                {                    
                    sQ2 = "INSERT INTO logcorrs(    corr,   nodoc,     estad,  motiv,                               estac,                                  falt,  corrde,  tipdoc,                       sucu,                                     nocaj) " + 
                                            "VALUES('',    '',         'FALLO','NO PARÁMETROS DE CONEXIÓN','" +     Login.sUsrG.replace("'", "''") + "',    now(), '',      'RECORDATORIO CXC','" +       Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ2);                           
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }
                
                //Cierra la base de datos y regresa
                Star.iCierrBas(con);
                return;
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
        
        //Si no esta definida la carpeta de la aplicación entonces
        if(sCarp.compareTo("")==0)
        {
            /*Ingresa en la base de datos el registor de que se fallo por que no esta definida la ruta con el servidor*/
            try 
            {                    
                sQ2 = "INSERT INTO logcorrs(    corr,   nodoc,     estad,  motiv,                                           estac,                                  falt,  corrde,  tipdoc,                         sucu,                                     nocaj) " + 
                                        "VALUES('',    '',         'FALLO','NO ESTA DEFINIDA CARPETA COMPARITDA','" +       Login.sUsrG.replace("'", "''") + "',    now(), '',      'RECORDATORIO CXC','" +         Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ2);                           
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }                

            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;

        }/*Fin de else*/
        
        /*Si la carpeta de las imágenes no existe entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();            

        /*Si la carpeta de CXC no existe entonces creala*/
        sCarp                    += "\\CXC";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta del recordatorio no existe entonces creala*/
        sCarp                    += "\\" + sRut;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Bandera para saber si hay archivo para mandar adjunto o no*/
        boolean bSi             = false;
        
        /*Si existe imágen en el directorio entonces*/
        if(new File(sCarp).list().length > 0)
        {
            /*Obtiene la lista del archivo y completa la ruta a la imágen*/
            String sArch [] = new File(sCarp).list();
            sCarp   = sCarp + "\\" + sArch[0];           
            
            /*Coloca la bandera para saber que si hay adjunto*/
            bSi                 = true;            
        }              
        
        /*Declara algunas variables como final*/
        final String sUsrFi     = sUsr;        
        final String sContraFi  = sContra;
        /*Recorre todo el arreglo de clientes*/
        for (String sEmp : sEmps) 
        {
            /*Obtiene el nombre del cliente de la base de datos y su correo*/
            String sNom = "";
            String sCo1 = "";
            try 
            {
                sQ = "SELECT co1, nom FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sEmp + "'";	                                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {
                    sNom    = rs.getString("nom");
                    sCo1    = rs.getString("co1");            
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
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
                final String username = sUsrFi;
                final String password = sContraFi;
                Session session = Session.getDefaultInstance(props,
                        new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
                
                /*Si hay imágen entonces el mensajea va a ser con imágen*/
                Multipart multipart = null;
                if(bSi)
                {
                    /*Carga la imágen*/
                    multipart               = new MimeMultipart("related");
                    BodyPart htmlPart       = new MimeBodyPart();                                       
                    htmlPart.setContent("<html><body>" +
                            "<img src=\"cid:the-img-1\"/><br/>" + sMens + " " + sNom + "</body></html>", "text/html");                    
                    /*Continua creando la imágen*/
                    multipart.addBodyPart(htmlPart);
                    BodyPart imgPart        = new MimeBodyPart();
                    DataSource ds           = new FileDataSource(sCarp);
                    imgPart.setDataHandler(new DataHandler(ds));
                    imgPart.setHeader       ("Content-ID","the-img-1");
                    multipart.addBodyPart(imgPart);
                }
                
                /*Crea el contenido del mensaje*/
                MimeMessage  msj            = new MimeMessage(session);
                msj.setFrom(new InternetAddress(sUsrFi));
                msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sCo1));
                msj.setSubject              (sAsun);

                /*Si es con imágen entonces adjunta el mensaje multipart*/
                if(bSi)
                    msj.setContent(multipart);
                /*Else sin imágen entonces inserta el mensaje en el objeto*/
                else
                    msj.setContent("<html>" + sMens + " " + sNom + "<body></body></html>", "text/html; charset=utf-8");
                
                /*Manda el correo*/
                Transport.send(msj);
            }
            catch(MessagingException expnMessag) 
            {                        
                /*Ingresa en la base de datos el registro de que se fallo*/
                try
                {
                    sQ2 = "INSERT INTO logcorrs(    corr,                  nodoc,     estad,           motiv,                                                   estac,                                  falt,       corrde,                         tipdoc,                          sucu,                                     nocaj) " +
                            "VALUES('" +  sCo1.replace("'", "''") + "',    '',         'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" +     Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',   'RECORDATORIO CXC','" +          Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                    st = con.createStatement();
                    st.executeUpdate(sQ2);                           
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }
                
                /*Mandalo al correo alternativo y continua*/
                Star.vMandAlterCump(sCo1, sEmp, expnMessag.getMessage(), sNom, "man");                                                                
                continue;
                
            } /*Fin de catch(MessagingException expnMessag)*/
            
            /*Ingresa en la base de datos el registro que se mando con éxito*/
            try 
            {                    
                sQ2 = "INSERT INTO logcorrs(    corr,                            nodoc,      estad,      motiv,              estac,                                         falt,       corrde,                         tipdoc,                         sucu,                                     nocaj) " +
                                  "VALUES('" +  sCo1.replace("'", "''") + "',    '',         'ENVIADO',  '',                '" +   Login.sUsrG.replace("'", "''") + "',     now(), '" + sUsr.replace("'", "''") + "',   'RECORDATORIO CXC','" +         Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ2);                           
                
                sQ2 = "INSERT INTO estadiscor(    corr,                            nodoc,      estad,      motiv,              estac,                                         falt,       corrde,                         tipdoc,                         sucu,                                     nocaj) " +
                                  "VALUES('" +  sCo1.replace("'", "''") + "',    '',         'ENVIADO',  '',                '" +   Login.sUsrG.replace("'", "''") + "',     now(), '" + sUsr.replace("'", "''") + "',   'RECORDATORIO CXC','" +         Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ2);                           
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }                                                            
            
        } /*Fin de for(String sCli: sEmps)*/
        
        //Esconde la forma de loading
        Star.vOcultLoadin();
        
        //Cierra la base de datos
        Star.iCierrBas(con);        
                
    }/*Fin de private void vMandRec(String sEmps[], String sRut, String sAsu, String sCue)*/
    
    
    /*Cuando se presioan el botón de recordatorio 1*/
    private void jBRec1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRec1ActionPerformed
        
        /*Si no a seleccionado por lo menos un registro de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un registro para enviarle recordatorio 1.", "Recordatorio 1", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();
            return;
        }
        
        /*Preguntar al usuario si esta seguro de que querer enviar el correo*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres enviar el(los) correo(s) electrónico(s) de recordatorio 1?", "Recordatorio 1", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Obtiene toda la selección del usuario de la tabla*/
        int iSel[] = jTab1.getSelectedRows();

        /*Crea el arreglo de los clientes entonces emepiza a guardarlos en el arreglo*/
        String sEmps[] = new String[iSel.length];
        for(int x = 0; x < iSel.length; x++)
            sEmps[x] = jTab1.getValueAt(iSel[x], 3).toString();
        
        /*Declara variables final para el thread*/
        final String sEmpsFi[]    = sEmps;
        
        /*Mandale a todos esos clientes el correo de recordatorio 1*/
        (new Thread()
        {
            @Override
            public void run()
            {
                vMandRec(sEmpsFi, "REC1", "asunrec1", "cuerprec1");
            }
            
        }).start();

        //Muestra el loading
        Star.vMostLoading("");
        
    }//GEN-LAST:event_jBRec1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de recordatorio 1*/
    private void jBRec1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBRec1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBRec1KeyPressed

    
    /*Cuando el mouse entra en el botón de recordatorio 2*/
    private void jBRec2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRec2MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBRec2.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBRec2MouseEntered

    
    /*Cuando el mouse sal del botón de recordatorio 2*/
    private void jBRec2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRec2MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBRec2.setBackground(colOri);
        
    }//GEN-LAST:event_jBRec2MouseExited

    
    /*Cuando se presiona el botón de recordatorio 2*/
    private void jBRec2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRec2ActionPerformed
        
        /*Si no a seleccionado por lo menos un registro de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un registro para enviarle recordatorio 2.", "Recordatorio 2", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();
            return;
        }
        
        /*Preguntar al usuario si esta seguro de que querer enviar el correo*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres enviar el(los) Correo(s) electrónico(s) de recordatorio 2?", "Recordatorio 2", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Obtiene toda la selección del usuario de la tabla*/
        int iSel[] = jTab1.getSelectedRows();

        /*Crea el arreglo de los clientes entonces emepiza a guardarlos en el arreglo*/
        String sEmps[] = new String[iSel.length];
        for(int x = 0; x < iSel.length; x++)
            sEmps[x] = jTab1.getValueAt(iSel[x], 3).toString();
        
        /*Declara variables final para el thread*/
        final String sEmpsFi[]    = sEmps;
        
        /*Mandale a todos esos clientes el correo de recordatorio 1*/
        (new Thread()
        {
            @Override
            public void run()
            {
                vMandRec(sEmpsFi, "REC2", "asunrec2", "cuerprec2");
            }
            
        }).start();

        //Muestra el loading
        Star.vMostLoading("");
        
    }//GEN-LAST:event_jBRec2ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de recordatorio 2*/
    private void jBRec2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBRec2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBRec2KeyPressed

    
    /*Cuando se tipea una tecla en el campo del día de vencimiento*/
    private void jTDiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDiaKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTDiaKeyTyped

    
    /*Cuando se presiona una tecla en el campo del día de vencimiento*/
    private void jTDiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDiaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDiaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del día de vencimiento*/
    private void jTDiaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDiaFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDia.setSelectionStart(0);jTDia.setSelectionEnd(jTDia.getText().length());        
        
    }//GEN-LAST:event_jTDiaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del día de vencimiento*/
    private void jTDiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDiaFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTDia.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTDia.getText().compareTo("")!=0)
            jTDia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si es cadena vacia entonces coloca 30 y regresa*/
        if(jTDia.getText().compareTo("")==0)        
            jTDia.setText("30");                    
        
    }//GEN-LAST:event_jTDiaFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de la clasificación del cliente*/
    private void jTClasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTClas.setSelectionStart(0);jTClas.setSelectionEnd(jTClas.getText().length());

    }//GEN-LAST:event_jTClasFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la clasificación del cliente*/
    private void jTClasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTClas.setCaretPosition(0);
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
            
        //Obtiene la descripción de la clasificación
        String sDescrip = Star.sDescripClasCli(con, jTClas.getText().trim());
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en su lugar
        jTClasDescrip.setText(sDescrip);
                
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Coloca el foco del teclado del control de la clasificación al principio del control*/
        jTClasDescrip.setCaretPosition(0);

    }//GEN-LAST:event_jTClasFocusLost

    
    /*Cuando se presiona una tecla en el campo de la clasificación del cliente*/
    private void jTClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClasKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTClas.getText(), 12, jTClas, jTClasDescrip, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTClasKeyPressed

    
    /*Cuando se tipea una tecla en el campo de la clasificación del cliente*/
    private void jTClasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClasKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTClasKeyTyped

    
    /*Cuando el mouse entra en el botón de búscar clasificación*/
    private void jBClasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBClasMouseEntered

        /*Cambia el color del fondo del botón*/
        jBClas.setBackground(Star.colBot);

    }//GEN-LAST:event_jBClasMouseEntered

    
    /*Cuando el moue sale del botón de búscar clasificación*/
    private void jBClasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBClasMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBClas.setBackground(colOri);

    }//GEN-LAST:event_jBClasMouseExited

    
    /*Cuando se presiona el botón de búscar clasificación*/
    private void jBClasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBClasActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTClas.getText(), 12, jTClas, jTClasDescrip, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código del producto*/
        jTClas.grabFocus();

    }//GEN-LAST:event_jBClasActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar clasificación*/
    private void jBClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBClasKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBClasKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción de la clasificación*/
    private void jTClasDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasDescripFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTClasDescrip.setSelectionStart(0);jTClasDescrip.setSelectionEnd(jTClasDescrip.getText().length());        

    }//GEN-LAST:event_jTClasDescripFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción de la clasificación*/
    private void jTClasDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasDescripFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTClasDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTClasDescripFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del folio*/
    private void jTFolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFolFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTFol.setSelectionStart(0);jTFol.setSelectionEnd(jTFol.getText().length());

    }//GEN-LAST:event_jTFolFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del folio*/
    private void jTFolFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFolFocusLost

        /*Coloca el cursor al principio del control*/
        jTFol.setCaretPosition(0);

        /*Si es cadena vacia entonces coloca 0 en el campo*/
        if(jTFol.getText().compareTo("")==0)                    
            jTFol.setText("0");                        

    }//GEN-LAST:event_jTFolFocusLost

    
    /*Cuando se presiona una tecla en el campo del folio*/
    private void jTFolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFolKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTFolKeyPressed

    
    /*Cuando se presiona una tecla en el campo del folio*/
    private void jTFolKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFolKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTFolKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de las condiciones*/
    private void jTCondFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCondFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCond.setSelectionStart(0);jTCond.setSelectionEnd(jTCond.getText().length());        
        
    }//GEN-LAST:event_jTCondFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de las condiciones*/
    private void jTCondFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCondFocusLost
        
        /*Coloca el caret al principio del control*/
        jTCond.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCondFocusLost

    
    /*Cuando se presiona una tecla en el campo de las condiciones*/
    private void jTCondKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCondKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCondKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del nombre del cliente*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length());        
        
    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se presiona una tecla en el campo del nombre del cliente*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la descripción de la clase*/
    private void jTClasDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClasDescripKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTClasDescripKeyPressed

    
    /*Cuando se presiona una tecla en el botón de liberar pago*/
    private void jBLibKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLibKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBLibKeyPressed

    
    /*Cuando el mouse entra en el botón de liberar saldo*/
    private void jBLibMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLibMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBLib.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBLibMouseEntered

    
    /*Cuando el mouse sale del botón de liberar pago*/
    private void jBLibMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLibMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBLib.setBackground(colOri);
        
    }//GEN-LAST:event_jBLibMouseExited

    
    /*Cuando se presiona el botón de liberar saldo de cliente*/
    private void jBLibActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLibActionPerformed
        
        //Si no hay cliente seleccionado entonces
        if(jTCli.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente para liberar saldo.", "Liberar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTCli.grabFocus();            
            return;            
        }
        
        /*Preguntar al usuario si esta seguro de que querer limpiar el historial del cliente*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres limpiar el saldo?", "Limpiar", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Pide clave de administrador*/            
        ClavMast cl = new ClavMast(this, 1);
        cl.setVisible(true);

        /*Si la clave que ingreso el usuario fue incorrecta entonces regresa*/
        if(!ClavMast.bSi)        
            return;        
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                        
        //Comprueba si el cliente existe
        iRes    = Star.iExistCliProv(con, jTCli.getText().trim(), true);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el cliente no existe entonces
        if(iRes==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El cliente: " + jTCli.getText().trim() + "no existe.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTCli.grabFocus();            
            return;            
        }       

        //Declara variables de la base de datos
        Statement   st;             
        String      sQ; 
        
        /*Libera todos los CXC de ese cliente*/
        try 
        {            
            sQ = "DELETE FROM cxc WHERE empre = '" + jTCli.getText().trim() + "'";                                
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

        /*Mensajea*/
        JOptionPane.showMessageDialog(null, "Cliente liberado con éxito.", "Liberar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBLibActionPerformed

    
    //Cuando se gana el foco del teclado en el campo del comentario
    private void jTComenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTComenFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTComen.setSelectionStart(0);jTComen.setSelectionEnd(jTComen.getText().length());        
        
    }//GEN-LAST:event_jTComenFocusGained

    
    //Cuando se pierde el foco del teclado en el campo del comentario
    private void jTComenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTComenFocusLost
        
        /*Coloca el caret al principio del control*/
        jTComen.setCaretPosition(0);
        
    }//GEN-LAST:event_jTComenFocusLost

    
    //Cuando se presiona una tecla en el campo del comentario
    private void jTComenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTComenKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTComenKeyPressed

    
    //Cuando se gana el foco del teclado en el campo del concepto
    private void jTConcepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTConcepFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTConcep.setSelectionStart(0);jTConcep.setSelectionEnd(jTConcep.getText().length());
        
    }//GEN-LAST:event_jTConcepFocusGained

    
    //Cuando se pierde el foco del teclado en el campo del concepto
    private void jTConcepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTConcepFocusLost

        /*Coloca el borde negro si tiene datos*/
        if(jTConcep.getText().compareTo("")!=0)
            jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTConcepFocusLost

    
    //Cuando se presiona una tecla en el campo del concepto
    private void jTConcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTConcepKeyPressed

        //Si se presiona la tecla hacia abajo entonces presiona el botón de concepto
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBConcep.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTConcepKeyPressed

    
    //Cuando se tipea una tecla en el campo del concepto
    private void jTConcepKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTConcepKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        
    }//GEN-LAST:event_jTConcepKeyTyped

    
    //Cuando el mouse entra en el botón de búscar concepto
    private void jBConcepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConcepMouseEntered

        /*Cambia el color del fondo del botón*/
        jBConcep.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBConcepMouseEntered

    
    //Cuando el mouse sale del botón de búscar concepto
    private void jBConcepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConcepMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBConcep.setBackground(colOri);
        
    }//GEN-LAST:event_jBConcepMouseExited

    
    //Cuando se presiona el botón de búscar concepto
    private void jBConcepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBConcepActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTConcep.getText(), 44, jTConcep, null, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBConcepActionPerformed

    
    //Cuando se presiona una tecla en el botón de búscar concepto
    private void jBConcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBConcepKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBConcepKeyPressed

    
    //Cuando se gana el foco del teclado en el campo de la forma de pago
    private void jTFormPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFormPagFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTFormPag.setSelectionStart(0);jTFormPag.setSelectionEnd(jTFormPag.getText().length());
        
    }//GEN-LAST:event_jTFormPagFocusGained

    
    //Cuando se pierde el foco del teclado en el campo de la forma de pago
    private void jTFormPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFormPagFocusLost

        /*Coloca el borde negro si tiene datos*/
        if(jTConcep.getText().compareTo("")!=0)
            jTConcep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTFormPagFocusLost

    
    //Cuando se presiona una tecla en el campo de la forma de pago
    private void jTFormPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFormPagKeyPressed

        //Si se presiona la flecha abajo entonces presiona el botón de forma de pago
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBFormPag.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFormPagKeyPressed

    
    //Cuando se tipea una tecla en el campo de la forma de pago
    private void jTFormPagKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFormPagKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
        
    }//GEN-LAST:event_jTFormPagKeyTyped

    
    //Cuando el mouse entra en el botón de búscar forma de pago
    private void jBFormPagMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFormPagMouseEntered

        /*Cambia el color del fondo del botón*/
        jBFormPag.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBFormPagMouseEntered

    
    //Cuando el mouse sale del botón de búscar forma de pago
    private void jBFormPagMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFormPagMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBFormPag.setBackground(colOri);
        
    }//GEN-LAST:event_jBFormPagMouseExited

    
    //Cuando se presiona el botón de búscar forma de pago
    private void jBFormPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFormPagActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTFormPag.getText(), 43, jTFormPag, null, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBFormPagActionPerformed

    
    //Cuando se presiona una tecla en el botón de forma de pago
    private void jBFormPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBFormPagKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
    }//GEN-LAST:event_jBFormPagKeyPressed

    
    //Cuando se presiona una tecla en el check de cancelado
    private void jCCaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCaKeyPressed

    
    //Cuando sucede una acción en el check de documentos cancelados
    private void jCCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCCaActionPerformed
        
        /*Obtén cxc de la base de datos y cargalos en la tabla*/
        vCargCxc();
        
    }//GEN-LAST:event_jCCaActionPerformed

    
    //Cuando el campo de foliobancario gana el foco
    private void jTFolBancFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFolBancFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTFolBanc.setSelectionStart(0);jTFolBanc.setSelectionEnd(jTFolBanc.getText().length());

    }//GEN-LAST:event_jTFolBancFocusGained

    
    //Cuando el campo de foliobancario pierde el foco
    private void jTFolBancFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFolBancFocusLost

        /*Coloca el caret al principio del control*/
        jTFolBanc.setCaretPosition(0);

    }//GEN-LAST:event_jTFolBancFocusLost

    
    
    //Cuando el campo de foliobancario se preciona una tecla
    private void jTFolBancKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFolBancKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTFolBancKeyPressed

    
    //Validar los caracteres del banco
    private void jTFolBancKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFolBancKeyTyped

        iFolBanc=jTFolBanc.getText().trim().length();

        //Limita a 50 el campo de folio
        if(iFolBanc < 50)
        {
            /*Comprueba que el carácter este en los límites permitidos para el teléfono entonces*/
            if(((evt.getKeyChar() < 'A') || (evt.getKeyChar() > 'Z')) && ((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && ((evt.getKeyChar() < 'a') || (evt.getKeyChar() > 'z')) && evt.getKeyChar() != 'Ñ' && evt.getKeyChar() != 'ñ' && evt.getKeyChar() != '.' && evt.getKeyChar() != ',' && evt.getKeyChar() != '-' && evt.getKeyChar() != '_'  )
            evt.consume();
        }
        else
        {
            jTFolBanc.setText(jTFolBanc.getText().substring(0,50));
            evt.consume();
        }

        //Variable para guardar el folio correcto
        String sSinEsp = "";

        //Se determina cuando cuales tuenen error
        for(int i=0;i<jTFolBanc.getText().length();i++)
        if(!(((jTFolBanc.getText().charAt(i) < 'A') || (jTFolBanc.getText().charAt(i) > 'Z')) && ((jTFolBanc.getText().charAt(i) < '0') || (jTFolBanc.getText().charAt(i) > '9')) && ((jTFolBanc.getText().charAt(i) < 'a') || (jTFolBanc.getText().charAt(i) > 'z')) && jTFolBanc.getText().charAt(i) != 'Ñ' && jTFolBanc.getText().charAt(i) != 'ñ' && jTFolBanc.getText().charAt(i) != '.' && jTFolBanc.getText().charAt(i) != ',' && jTFolBanc.getText().charAt(i) != '-' && jTFolBanc.getText().charAt(i) != '_'  ))
        sSinEsp = sSinEsp + jTFolBanc.getText().charAt(i);

        jTFolBanc.setText(sSinEsp);

    }//GEN-LAST:event_jTFolBancKeyTyped
    
    
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
        /*Si se presiona F2 presiona el botón de ver los abonos*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBVerA.doClick();            
        /*Si se presiona F5 presiona el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();
        /*Si se presiona F6 presiona el botón de visor*/
        else if(evt.getKeyCode() == KeyEvent.VK_F6)
            jBVis.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbon;
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBClas;
    private javax.swing.JButton jBCli;
    private javax.swing.JButton jBConcep;
    private javax.swing.JButton jBFormPag;
    private javax.swing.JButton jBLib;
    private javax.swing.JButton jBRec1;
    private javax.swing.JButton jBRec2;
    private javax.swing.JButton jBRec3;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTab2;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBVerA;
    private javax.swing.JButton jBVis;
    private javax.swing.JCheckBox jCCa;
    private javax.swing.JCheckBox jCCo;
    private javax.swing.JCheckBox jCPe;
    private javax.swing.JCheckBox jCVen;
    private com.toedter.calendar.JDateChooser jDTA;
    private com.toedter.calendar.JDateChooser jDTDe;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTAbon;
    private javax.swing.JTextField jTClas;
    private javax.swing.JTextField jTClasDescrip;
    private javax.swing.JTextField jTCli;
    private javax.swing.JTextField jTComen;
    private javax.swing.JTextField jTConcep;
    private javax.swing.JTextField jTCond;
    private javax.swing.JTextField jTDia;
    private javax.swing.JTextField jTFol;
    private javax.swing.JTextField jTFolBanc;
    private javax.swing.JTextField jTFormPag;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTable jTab1;
    private javax.swing.JTable jTab2;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
