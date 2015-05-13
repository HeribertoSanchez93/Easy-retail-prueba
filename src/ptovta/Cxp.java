//Paquete
package ptovta;

/*Importaciones*/
import report.*;
import vis.VisCxp1;
import vis.VisCxp2;
import static ptovta.Princip.bIdle;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import report.RepCXP;




/*Clase para controlar cxp*/
public class Cxp extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Declara variables de instancia*/           
    private int                 iContFi;
            
    /*Contador para modificar tabla*/
    private int                 iContCellEd;
    
    /*Declara las variables originales de la tabla 1*/
    private String              sComOri;
    private String              sSerOri;
    private String              sProvOri;
    private String              sNomOri;
    private String              sImpoOri;
    private String              sImpueOri;
    private String              sTotOri;
    private String              sTotAbonOri;
    private String              sFOri;
    private String              sFVencOri;
    private String              sSucOri;
    private String              sCajOri;
    private String              sEstacOri;
    private String              sNomEstacOri;
    private String              sEstadOri;
    
    /*Declara las variables originales de la tabla 2*/
    private String              sTipDocOri;
    private String              sProdOri;
    private String              sCantOri;
    private String              sUnidOri;
    private String              sDescripOri;
    private String              sDescOri;
    private String              sMonOri;
    private String              sPreOri;    
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean              bSel;
    
    
    
    /*Constructor sin argumentos*/
    public Cxp() 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Esconde la columna del ID*/
        jTab1.getColumnModel().getColumn(17).setMinWidth(0);
        jTab1.getColumnModel().getColumn(17).setMaxWidth(0);
        
        /*Establece el tamaño de las columnas de la tabla de encabezados*/
        jTab1.getColumnModel().getColumn(3).setPreferredWidth(160);
        jTab1.getColumnModel().getColumn(4).setPreferredWidth(300);
        jTab1.getColumnModel().getColumn(10).setPreferredWidth(160);
        jTab1.getColumnModel().getColumn(11).setPreferredWidth(160);
        jTab1.getColumnModel().getColumn(15).setPreferredWidth(300);
        
        /*Establece el tamaño de las columnas de la tabla de partidas*/
        jTab2.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTab2.getColumnModel().getColumn(7).setPreferredWidth(400);
        
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

                /*Obtén cxp de la base de datos y cargalos en la tabla*/
                vCargCxp();
            }           
        });
        
        /*Crea el listener para cuando se termina de escribir en el campo de la clasificación del proveedor*/
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
                /*Obtén cxp de la base de datos y cargalos en la tabla*/
                vCargCxp();
            }           
        });
        
        /*Crea el listener para cuando se termina de escribir en el campo del proveedor*/
        jTProv.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
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
                /*Obtén cxp de la base de datos y cargalos en la tabla*/
                vCargCxp();
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
        this.setTitle("CXP, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en uno*/
        iContFi          = 1;
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab1.setAutoResizeMode     (0);
        jTab2.setAutoResizeMode     (0);
        
        /*Selecciona el día de hoy inicialmente para las fechas*/
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
        
        /*Pon el foco del teclado en el campo de la empresa*/
        jTProv.grabFocus();
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab1.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,       null);
        jTab1.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,      null);
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,       null);
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,      null);                
                
        /*Crea el listener para cuando se cambia de selección en la tabla de cxp*/
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
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para cuando se cambia de selección en la tabla 1*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtén la propiedad que a sucedio en el control*/
                String pr = event.getPropertyName();                                
                                
                /*Obtiene la fila seleccionada*/              
                if(jTab1.getSelectedRow()==-1)
                    return;
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pr)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sComOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString();
                        sSerOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString();
                        sProvOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 3).toString();
                        sNomOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 4).toString();
                        sImpoOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 5).toString();
                        sImpueOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 6).toString();
                        sTotOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 7).toString();
                        sTotAbonOri    = jTab1.getValueAt(jTab1.getSelectedRow(), 8).toString();
                        sFOri          = jTab1.getValueAt(jTab1.getSelectedRow(), 9).toString();
                        sFVencOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 10).toString();
                        sSucOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 11).toString();
                        sCajOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 12).toString();
                        sEstacOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 13).toString();
                        sNomEstacOri   = jTab1.getValueAt(jTab1.getSelectedRow(), 14).toString();
                        sEstadOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 15).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab1.setValueAt(sComOri,           jTab1.getSelectedRow(), 1);                        
                        jTab1.setValueAt(sSerOri,           jTab1.getSelectedRow(), 2);                        
                        jTab1.setValueAt(sProvOri,          jTab1.getSelectedRow(), 3);                        
                        jTab1.setValueAt(sNomOri,           jTab1.getSelectedRow(), 4);                        
                        jTab1.setValueAt(sImpoOri,          jTab1.getSelectedRow(), 5);                        
                        jTab1.setValueAt(sImpueOri,         jTab1.getSelectedRow(), 6);                        
                        jTab1.setValueAt(sTotOri,           jTab1.getSelectedRow(), 7);                        
                        jTab1.setValueAt(sTotAbonOri,       jTab1.getSelectedRow(), 8);                        
                        jTab1.setValueAt(sFOri,             jTab1.getSelectedRow(), 9);                        
                        jTab1.setValueAt(sFVencOri,         jTab1.getSelectedRow(), 10);                        
                        jTab1.setValueAt(sSucOri,           jTab1.getSelectedRow(), 11);                        
                        jTab1.setValueAt(sCajOri,           jTab1.getSelectedRow(), 12);                        
                        jTab1.setValueAt(sEstacOri,         jTab1.getSelectedRow(), 13);                        
                        jTab1.setValueAt(sNomEstacOri,      jTab1.getSelectedRow(), 14);                        
                        jTab1.setValueAt(sEstadOri,         jTab1.getSelectedRow(), 15);                        
                        
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
                                
                /*Si no hay selecciòn entonces regresa*/
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
                        sComOri         = jTab2.getValueAt(jTab2.getSelectedRow(), 2).toString();
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
                        jTab2.setValueAt(sFOri,             jTab2.getSelectedRow(), 1);                        
                        jTab2.setValueAt(sComOri,           jTab2.getSelectedRow(), 2);                        
                        jTab2.setValueAt(sTipDocOri,        jTab2.getSelectedRow(), 3);                        
                        jTab2.setValueAt(sProdOri,          jTab2.getSelectedRow(), 4);                        
                        jTab2.setValueAt(sCantOri,          jTab2.getSelectedRow(), 5);                        
                        jTab2.setValueAt(sUnidOri,          jTab2.getSelectedRow(), 6);                        
                        jTab2.setValueAt(sDescripOri,       jTab2.getSelectedRow(), 7);                        
                        jTab2.setValueAt(sDescOri,          jTab2.getSelectedRow(), 8);                        
                        jTab2.setValueAt(sMonOri,           jTab2.getSelectedRow(), 9);                        
                        jTab2.setValueAt(sImpueOri,         jTab2.getSelectedRow(), 10);                        
                        jTab2.setValueAt(sPreOri,           jTab2.getSelectedRow(), 11);                        
                        jTab2.setValueAt(sImpoOri,          jTab2.getSelectedRow(), 12);                        
                        
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
            sQ = "SELECT IFNULL(MAX(fol),0) + 1 AS maxfol FROM cxp";                                    
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
        
    }/*Fin de public Cxp() */

    
    /*Carga todos todas las partidas de la venta en la tabla de partidas*/
    private void vCargPart()
    {
        /*Si no hay selección que regrese*/
        if(jTab1.getSelectedRow()==-1)
            return;
                
        /*Obtiene el documento y la serie de la fila seleccionada*/
        String sNoRefer = jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString();                        
                
        /*Borra la tabla de partidas de la compra*/
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
        
        /*Obtiene el proveedor de esa compra*/
        String sProv    = "";
        try
        {
            sQ = "SELECT prov FROM comprs WHERE codcomp = '" + sNoRefer + "'";                                    
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sProv   = rs.getString("prov");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
        
        /*Función para cargar la información del proveedor*/
        vInfoProv(sProv);
        
        /*Coloca cadena vacia en el campo del nombre del proveedor ya que la función de arriba lo coloca*/
        jTNom.setText("");       
        
        /*Obtiene las partidas de la compra*/
        int iContFiPart = 1;
        try
        {
            sQ = "SELECT partcomprs.IMPO, partcomprs.FALT, partcomprs.CODCOM, 'COMPRA', partcomprs.CANT, partcomprs.PROD, partcomprs.UNID, prods.DESCRIP, partcomprs.DESCU, partcomprs.MON, partcomprs.CODIMPUE, partcomprs.COST, partcomprs.CODIMPUE FROM partcomprs LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM LEFT OUTER JOIN prods ON prods.PROD = partcomprs.PROD WHERE comprs.CODCOMP = '" + sNoRefer + "'";                                                
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene los totales*/                
                String sImpo    = rs.getString("impo");                
                String sPre     = rs.getString("cost");                                            
                
                /*Dale formato de moneda a los importes*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());                
                double dCant    = Double.parseDouble(sImpo);                
                sImpo           = n.format(dCant);
                dCant           = Double.parseDouble(sPre);                
                sPre            = n.format(dCant);
                
                /*Agrega los registros en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab2.getModel();
                Object nu[]             = {iContFiPart, rs.getString("partcomprs.FALT"), rs.getString("partcomprs.CODCOM"), "COMPRA", rs.getString("partcomprs.PROD"), rs.getString("partcomprs.CANT"), rs.getString("partcomprs.UNID"), rs.getString("prods.DESCRIP"), rs.getString("partcomprs.DESCU"), rs.getString("partcomprs.MON"), rs.getString("codimpue"), sPre, sImpo};
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
                
                
    /*Obtén cxp de la base de datos y cargalos en la tabla*/
    private void vCargCxp()
    {          
        /*Limpia la tabla de cxp y partidas*/
        DefaultTableModel te = (DefaultTableModel)jTab2.getModel();
        te.setRowCount(0);                
        te                  = (DefaultTableModel)jTab1.getModel();
        te.setRowCount(0);                
        
        /*Obtiene las fechas de y a*/        
        Date        fD          = jDTDe.getDate();
        Date        fA          = jDTA.getDate();
        
        /*Si alguon es nulo entonces que regrese*/
        if(fD == null || fA == null)
            return;
        
        /*Obtiene las fechas con formato*/
        SimpleDateFormat sd     = new SimpleDateFormat("yyy-MM-dd");
        String      sD          = sd.format(fD);        
        String      sA          = sd.format(fA);                    
        
        /*Si no hay nada marcado entonces regresa*/
        if(!jCPe.isSelected() && !jCCo.isSelected() && !jCVen.isSelected())
            return;        
        
        /*Reinicia el contador de filas*/
        iContFi             = 1;
        
        /*Cre la condición de la fecha*/
        String sConFech     = "AND DATE(a.FALT) >= '" + sD + "' AND DATE(a.FALT) <= '" + sA + "' ";        
        
        /*Crea la condición del proveedor*/
        String sCondProv    = "";
        if(jTProv.getText().compareTo("")!=0)
            sCondProv       = " AND a.PROV = '" + jTProv.getText().trim() + "'";        
     
        /*Si el proveedor es cadena vacia entonces no habrá condición*/
        if(jTProv.getText().compareTo("")==0)
            sCondProv       = "";
        
        /*Crea la condición de la clasificación del proveedor*/
        String sCondClas    = "";
        if(jTClas.getText().compareTo("")!=0)
            sCondClas       = " AND provs.CODCLAS = '" + jTClas.getText().trim() + "'";        
        
        /*Si la clasificación del proveedor es cadena vacia entonces no habrá filtro para esto*/
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
        String sEstadDoc= " comprs.ESTADO IN('CO','PE','DEVP')";
        
        //Si va a mostrar las canceladas
        if(jCCa.isSelected())
        {
            //Filtros para cuando tambien se requieren los otros dos tipos
            sEstadDoc = " comprs.ESTADO IN('CA'";
            if(jCCo.isSelected()||jCPe.isSelected())
                sEstadDoc += ",'CO','PE', 'DEVP'";
            sEstadDoc += ")";
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
        
        /*Trae el CXP de la base de datos y cargalos en la tabla*/
        try
        {                        
            /*Ejecuta la consulta*/
            sQ = "SELECT sucuu, nocajj, estacs.NOM, id_Idd, norefer, a.NOSER, serr, a.PROV, a.SUBTOT, a.IMPUE, comprs.estado, a.TOT, DATE(a.FVENC) AS fven, a.FDOC, provs.NOM, a.ESTAC FROM (SELECT fdoc, id_id AS id_idd, falt, fvenc, prov, subtot, impue, tot, noser, ser AS serr, sucu AS sucuu, norefer, nocaj AS nocajj, estac, SUM(carg) AS carg, SUM(abon) AS abon FROM cxp GROUP BY norefer, noser, prov)a LEFT OUTER JOIN comprs ON comprs.CODCOMP = a.NOREFER LEFT OUTER JOIN estacs ON estacs.ESTAC = a.ESTAC LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = a.PROV WHERE " + sEstadDoc + " " + sConFech + " " + sCondClas + " " + sCondProv + "  " + sEsta +  " GROUP BY id_idd";                                                                                                                                                                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene los totales*/
                String sSubTot  = rs.getString("subtot");
                String sImpue   = rs.getString("impue");
                String sTot     = rs.getString("tot");                
            
                /*Obtiene el total de abonos de este CXC*/
                String sAbon    = sTotAbon(con, rs.getString("norefer"), rs.getString("noser"), rs.getString("prov"));
                
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
                Object nu[]             = {iContFi, rs.getString("norefer"), rs.getString("noser"), rs.getString("prov"), rs.getString("provs.NOM"), sSubTot, sImpue,sTot, sAbon, sPend, rs.getString("fdoc"), rs.getString("fven"), rs.getString("sucuu"), rs.getString("nocajj"), rs.getString("estac"), rs.getString("estacs.NOM"), rs.getString("estado"), rs.getString("id_idd")};
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
        
    }/*Fin de private void vCargCxp()*/
            
    
    /*Obtiene el total de abonos de este CXP*/
    private String sTotAbon(Connection con, String sNoRefer, String sNoSer, String sProv)
    {                    
        //Declara variables de la base de datos        
        ResultSet       rs;
        Statement       st;
        String sQ;
        
        
        
        
        /*Obtiene el total de abonos de ese CXP*/
        try
        {
            sQ = "SELECT IFNULL(SUM(abon),0) AS abon FROM cxp WHERE norefer = '" + sNoRefer + "' AND noser = '" + sNoSer + "' AND prov = '" + sProv + "' AND carg = 0 AND concep = 'ABON COMP'";                        
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
        
    }/*Fin de private String sTotAbon(Connection con, String sNoRefer, String sNoSer, String sProv)*/
    
    
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
        jLabel4 = new javax.swing.JLabel();
        jTProv = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTNom = new javax.swing.JTextField();
        jBProv = new javax.swing.JButton();
        jCVen = new javax.swing.JCheckBox();
        jBActua = new javax.swing.JButton();
        jBVis = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
        jBTab2 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jTClas = new javax.swing.JTextField();
        jBClas = new javax.swing.JButton();
        jTClasDescrip = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTDia = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTFol = new javax.swing.JTextField();
        jTCond = new javax.swing.JTextField();
        jBLib = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTComen = new javax.swing.JTextField();
        jTConcep = new javax.swing.JTextField();
        jBConcep = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTFormPag = new javax.swing.JTextField();
        jBFormPag = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jCCa = new javax.swing.JCheckBox();
        jTFolBanc = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

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
        jBAbon.setToolTipText("Abonar");
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
        jP1.add(jBAbon, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 100, 120, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 220, 120, -1));

        jTAbon.setText("$0.00");
        jTAbon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAbon.setNextFocusableComponent(jTFol);
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
                "No.", "Folio", "Serie", "Compra", "Proveedor", "Subtotal", "Impuesto", "Total", "Total Abonos", "Pendiente Liquidar", "Fecha", "Fecha Vencimiento", "Sucursal", "Caja", "Usuario", "Nombre de Usuario", "Estado", "id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 830, 230));

        jTab2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Fecha", "Compra", "Tipo Doc", "Producto", "Qty", "Unidad", "Descripción", "Descuento", "Moneda", "Impuesto", "Precio", "Importe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab2.setGridColor(new java.awt.Color(255, 255, 255));
        jTab2.setNextFocusableComponent(jBLib);
        jTab2.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
        jCCo.setName(""); // NOI18N
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
        jP1.add(jBVerA, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 130, 120, 30));

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

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("$ Abono y folio:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 110, -1));

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
        jP1.add(jTProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 120, 20));

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

        jBProv.setBackground(new java.awt.Color(255, 255, 255));
        jBProv.setText("...");
        jBProv.setToolTipText("Buscar Proveedor(es)");
        jBProv.setNextFocusableComponent(jTNom);
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
        jP1.add(jBProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 30, 20));

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
        jP1.add(jCVen, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 20, 90, -1));

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
        jP1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 160, 120, 30));

        jBVis.setBackground(new java.awt.Color(255, 255, 255));
        jBVis.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVis.setForeground(new java.awt.Color(0, 102, 0));
        jBVis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/visor.png"))); // NOI18N
        jBVis.setText("Reporte");
        jBVis.setToolTipText("Reporte (F6)");
        jBVis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVis.setNextFocusableComponent(jBSal);
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
        jP1.add(jBVis, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 190, 120, 30));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(816, 530, 140, -1));

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
        jLabel7.setText("Proveedor:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 80, -1));

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

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Clasificación:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 80, -1));

        jTFol.setText("0");
        jTFol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFol.setNextFocusableComponent(jCPe);
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
        jBLib.setToolTipText("Liberar saldo del (los) proveedor(s)");
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

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Forma pago:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 120, -1));

        jTComen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTComen.setNextFocusableComponent(jDTDe);
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

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Comentario:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 120, -1));

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

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Concepto:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 120, -1));

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

        jTFolBanc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFolBanc.setNextFocusableComponent(jDTDe);
        jP1.add(jTFolBanc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 200, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Folio de banco:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 120, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
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

    
    //Método para procesar el abono de un documento
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
        
        /*Obtiene la cantidad a abonar*/        
        String sAbon    = jTAbon.getText().replace("$", "").replace(",", "");
        
        /*Dale formato de moneda a la cantidad a abonar*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        double dCant    = Double.parseDouble(sAbon);                
        sAbon           = n.format(dCant);
        
        /*Preguntar al usuario si esta seguro de querer abonar*/        
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere abonar al proveedor: " + jTab1.getValueAt(jTab1.getSelectedRow(), 4) + " la cantidad de: " + sAbon + "", "Abonar", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            if(Star.iCierrBas(con)==-1)
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
        
        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;
        String      sQ; 
          
        /*Contiene los datos del CXC actual*/
        String sNoRefer = "";
        String sNoSer   = "";
        String sProv    = "";
        String sSer     = "";
        String sSubTot  = "";
        String sTot     = "";
        String sImpue   = "";
        String sFVenc   = "";
        String sFDoc    = "";        
        
        /*Obtiene algunos datos de ese CXP*/
        try
        {
            sQ = "SELECT * FROM cxp WHERE id_id = " + jTab1.getValueAt(jTab1.getSelectedRow(), 17).toString().trim();                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sNoRefer    = rs.getString("norefer");
                sNoSer      = rs.getString("noser");
                sProv       = rs.getString("prov");
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
        
        //Inserta CXP en la base de datos        
        if(Star.iInsCXCP(con, "cxp", sNoRefer, sNoSer, sProv, sSer, sSubTot, sImpue, sTot, "0", sAbon, "'" + sFVenc + "'", "'" + sFVenc + "'", "ABON COMP", jTFormPag.getText(), jTFol.getText(), jTComen.getText(), jTConcep.getText(), jTFolBanc.getText().trim())==-1)
            return;        
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Carga todos los cxp en la tabla*/
        vCargCxp();            
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Abono registrado con éxito.", "Abono", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
    }//Fin de private vProcDoc()
    
    
    //Método para procesar un pago por concepto
    private void vProcConcep()
    {
        //Si el campo del proveedor es cadena vacia entonces
        if(jTProv.getText().trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un proveedor para el abono por concepto.", "Abonar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control del abono y regresa*/
            jTProv.grabFocus();           
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
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere abonar por concepto al proveedor: " + jTProv.getText() + " la cantidad de: " + sAbon + "", "Abonar", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos y regresa
            if(Star.iCierrBas(con)==-1)
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
        
        //Comprueba si el proveedor existe
        int iResp   = Star.iExisProv(con, jTProv.getText().trim());
        
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
            JOptionPane.showMessageDialog(null, "El proveedor: " + jTProv.getText() + " no existe.", "Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Coloca el foco del teclado en el control
            jTProv.grabFocus();
            
            //Coloca el borde rojo y regresa                               
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
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
                
        //Inserta el CXP en la base de datos        
        if(Star.iInsCXCP(con, "cxp", "", "", jTProv.getText(), "", "0", "0", "0", "0", sAbon, "now()", "now()", "ABON COMP", jTFormPag.getText(), "0", jTComen.getText(), jTConcep.getText(), jTFolBanc.getText().trim())==-1)
            return;        
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Carga todos los cxp en la tabla*/
        vCargCxp();            
        
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

        
    /*Cuando se presiona una  tecla en la tabla de cxp*/
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

        /*Obtén cxp de la base de datos y cargalos en la tabla*/
        vCargCxp();
        
    }//GEN-LAST:event_jCPeActionPerformed

    
    /*Cuando sucede una acción en el checkbox de confirmados*/
    private void jCCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCCoActionPerformed

        /*Obtén cxp de la base de datos y cargalos en la tabla*/
        vCargCxp();
       
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
        
        /*Obtén cxp de la base de datos y cargalos en la tabla*/
        vCargCxp();
        
    }//GEN-LAST:event_jDTDePropertyChange

    
    /*Cuando cambia la fecha en el control a*/
    private void jDTAPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDTAPropertyChange
        
        /*Obtén cxp de la base de datos y cargalos en la tabla*/
        vCargCxp();
        
    }//GEN-LAST:event_jDTAPropertyChange

    
    /*Cuando se gana el foco del teclado en el campo del proveedor*/
    private void jTProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTProv.setSelectionStart(0);jTProv.setSelectionEnd(jTProv.getText().length());                
        
    }//GEN-LAST:event_jTProvFocusGained

    
    /*Cuando se presiona una tecla en el campo del proveedor*/
    private void jTProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyPressed
        
        /*Si se presiona la tecla de abajo entonces presioan el botón de búscar proveedor*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBProv.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProvKeyPressed

    
    /*Cuando se presiona el botón de búscar*/
    private void jBProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProvActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProv.getText(), 3, jTProv, jTNom, null, "", null);
        b.setVisible(true);
        
        /*Coloca el foco del teclado en el campo del código de la empresa*/
        jTProv.grabFocus();
        
        /*Función para cargar la información del proveedor*/
        vInfoProv(jTProv.getText().trim());
        
    }//GEN-LAST:event_jBProvActionPerformed

    
    /*Cuando se tipea una tecla en el campo del proveedor*/
    private void jTProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTProvKeyTyped

    
    /*Función para cargar la información del proveedor*/
    private void vInfoProv(String sProv)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Contiene algunos datos del proveedor*/
        String sNom     = "";
        String sDiaCred = "";
        String sLimCred = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ; 
    
        /*Obtiene información del proveedor*/
        try
        {
            sQ = "SELECT nom, diacred, limcred FROM provs WHERE CONCAT_WS('', provs.SER, provs.PROV) = '" + sProv + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sNom        = rs.getString("nom");                                                                     
                sDiaCred    = rs.getString("diacred");
                sLimCred    = rs.getString("limcred");
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
            sQ = "SELECT IFNULL((SUM(carg) - SUM(abon)),0) AS pendpag FROM cxp WHERE prov = '" + sProv + "'";
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
        
        /*Obtiene el saldo que tiene disponible el proveeedor*/
        String sSaldDispo   = Double.toString(Double.parseDouble(sLimCred.replace("$", "").replace(",", "")) - Double.parseDouble(sPendPag));        

        /*Dale formato de moneda al saldo disponible*/        
        dCant               = Double.parseDouble(sSaldDispo);                
        sSaldDispo          = n.format(dCant);

        /*Agrega en el campo el saldo disponible*/
        String sTemp    = jTCond.getText();
        jTCond.setText(sTemp + " Saldo disponible:" + sSaldDispo);
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Coloca el nombre y el caret al principio del control*/
        jTNom.setText(sNom);
        jTNom.setCaretPosition(0);
        
    }/*Fin de private void vInfoProv(String sProv)*/
        
        
    /*Cuando se pierde el foco del teclado en el control del proveedor*/
    private void jTProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusLost

        /*Coloca el cursor al principio del control*/
        jTProv.setCaretPosition(0);

        /*Función para cargar la información del proveedor*/
        vInfoProv(jTProv.getText().trim());
                        
    }//GEN-LAST:event_jTProvFocusLost

        
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
            //Si no a seleccionado un proveedor entonces
            if(jTProv.getText().trim().compareTo("")==0)
            {
                //Mensajea
                JOptionPane.showMessageDialog(null, "Selecciona un proveedor primero.", "Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Coloca el borde rojo
                jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                //Coloca el foco del teclado en el control y regresa
                jTProv.grabFocus();
                return;
            }

            //Abre la base de datos                             
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Comprueba si el proveedor existe
            int iResp   = Star.iExisProv(con, jTProv.getText().trim());

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
                JOptionPane.showMessageDialog(null, "El proveedor: " + jTProv.getText() + " no existe.", "Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                //Coloca el foco del teclado en el control
                jTProv.grabFocus();

                //Coloca el borde rojo y regresa                               
                jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                return;
            }

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            //Muestra la forma para ver los abonos de ese proveedor
            VAbons v    = new VAbons(null, "comp", jTProv.getText().trim());
            v.setVisible(true);
            
        }//Fin de if(iResp==0)            
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
                /*Obtiene el id de cxp*/        
                String sId  = jTab1.getValueAt(iSel[x], 17).toString();

                /*Muestra el formulario para ver los abonos*/
                VAbons v    = new VAbons(sId, "comp", null);
                v.setVisible(true);
            }                    
            
        }//Fin de else                    
        
    }//GEN-LAST:event_jBVerAActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar*/
    private void jBProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProvKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jBProvKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de vencido*/
    private void jCVenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVenKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCVenKeyPressed

    
    /*Cuando sucede una acción en el checkbox de vencido*/
    private void jCVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCVenActionPerformed

        /*Obtén cxp de la base de datos y cargalos en la tabla*/
        vCargCxp();
        
    }//GEN-LAST:event_jCVenActionPerformed

    
    /*Cuando se presiona el botón de actualizar*/
    private void jBActuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActuaActionPerformed

        /*Obtén cxp de la base de datos y cargalos en la tabla*/
        vCargCxp();

    }//GEN-LAST:event_jBActuaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de actualizar*/
    private void jBActuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBActuaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBActuaKeyPressed

    
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

    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se presiona el botón de visor*/
    private void jBVisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVisActionPerformed
        
        /*Muestra la forma del visor de CXP*/
        RepCXP r = new RepCXP();
        r.setVisible(true);
        
    }//GEN-LAST:event_jBVisActionPerformed

    
    /*Cuando se presiona una tecla en el botón de visor*/
    private void jBVisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVisKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVisKeyPressed

    
    /*Cuando se presiona el botón de ver tabla en grande 1*/
    private void jBTab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab1ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab1);       

    }//GEN-LAST:event_jBTab1ActionPerformed

    
    /*Cuando se presiona una tecla en la tabla 1*/
    private void jBTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTab1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTab1KeyPressed

    
    /*Cuando se presiona el botón de ver tabla 2*/
    private void jBTab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab2ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab2);       

    }//GEN-LAST:event_jBTab2ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver tabla*/
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
    private void jBProvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProvMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProv.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProvMouseEntered

    
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
    private void jBProvMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProvMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProv.setBackground(colOri);
        
    }//GEN-LAST:event_jBProvMouseExited

    
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

    
    /*Cuando se pierde el foco del teclado en el campo del nombre del proveedor*/
    private void jTNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusLost
        
        /*Coloca el cursor al principio del control*/
        jTNom.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de la clasificación*/
    private void jTClasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTClas.setSelectionStart(0);jTClas.setSelectionEnd(jTClas.getText().length());

    }//GEN-LAST:event_jTClasFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la clasificación*/
    private void jTClasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTClas.setCaretPosition(0);
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Obtiene la descripción de la clasificación*/
        try
        {
            sQ = "SELECT descrip FROM clasprov WHERE cod = '" + jTClas.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces colcoalo en el control*/
            if(rs.next())
                jTClasDescrip.setText(rs.getString("descrip"));
            /*Else, el codigo de la descripción no existe entonces coloca cadena vacia*/
            else
                jTClasDescrip.setText("");
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

        /*Coloca el caret el principio de la descripción de la clasificación*/
        jTClasDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTClasFocusLost

    
    /*Cuando se presiona una tecla en el campo de la clasificación*/
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

    
    /*Cuando se tipea una tecla en el campo de la clasificación del proveedor*/
    private void jTClasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClasKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTClasKeyTyped

    
    /*Cuando el mouse entra en el botón de clasificación del proveedor*/
    private void jBClasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBClasMouseEntered

        /*Cambia el color del fondo del botón*/
        jBClas.setBackground(Star.colBot);

    }//GEN-LAST:event_jBClasMouseEntered

    
    /*Cuando el mouse sale del botón de búscar clasificación de proveedor*/
    private void jBClasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBClasMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBClas.setBackground(colOri);

    }//GEN-LAST:event_jBClasMouseExited

    
    /*Cuando se presiona el botón de búscar clasificación de proveedor*/
    private void jBClasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBClasActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTClas.getText(), 13, jTClas, jTClasDescrip, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código de la clasificación*/
        jTClas.grabFocus();                

    }//GEN-LAST:event_jBClasActionPerformed

    
    /*Cuando se presiona una tecla en el botón de clasificación */
    private void jBClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBClasKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBClasKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción de la clase*/
    private void jTClasDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasDescripFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTClasDescrip.setCaretPosition(0);

    }//GEN-LAST:event_jTClasDescripFocusLost

    
    /*Cuando se gana el foco del teclado en el campo de día*/
    private void jTDiaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDiaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDia.setSelectionStart(0);jTDia.setSelectionEnd(jTDia.getText().length());

    }//GEN-LAST:event_jTDiaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de día*/
    private void jTDiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDiaFocusLost

        /*Coloca el cursor al principio del control*/
        jTDia.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTDia.getText().compareTo("")!=0)
            jTDia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

        //Si es cadena vacia entonces coloca el día y regresa
        if(jTDia.getText().compareTo("")==0)
            jTDia.setText("30");
       
    }//GEN-LAST:event_jTDiaFocusLost

    
    /*Cuando se presiona una tecla en el campo de día*/
    private void jTDiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDiaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDiaKeyPressed

    
    /*Cuando se tipea una tecla en el campo de día*/
    private void jTDiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDiaKeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))
            evt.consume();

    }//GEN-LAST:event_jTDiaKeyTyped

    
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

    
    /*Cuando se tipea una tecla en el campo del folio*/
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

    
    /*Cuando se gana el foco del teclado en el campo del nombre del proveedor*/
    private void jTNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNom.setSelectionStart(0);jTNom.setSelectionEnd(jTNom.getText().length());
        
    }//GEN-LAST:event_jTNomFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la descripción*/
    private void jTClasDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTClasDescripFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTClasDescrip.setSelectionStart(0);jTClasDescrip.setSelectionEnd(jTClasDescrip.getText().length());
        
    }//GEN-LAST:event_jTClasDescripFocusGained

    
    /*Cuando se presiona una tecla en el campo del nombre*/
    private void jTNomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNomKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la descripción*/
    private void jTClasDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTClasDescripKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTClasDescripKeyPressed

    
    /*Cuando el mouse entra en el botón de liberar CXP*/
    private void jBLibMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLibMouseEntered

        /*Cambia el color del fondo del botón*/
        jBLib.setBackground(Star.colBot);

    }//GEN-LAST:event_jBLibMouseEntered

    
    /*Cuando el mouse sale del botón de liberar pagos*/
    private void jBLibMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLibMouseExited

        /*Cambia el color del fondo del botón*/
        jBLib.setBackground(colOri);

    }//GEN-LAST:event_jBLibMouseExited

    
    /*Cuando se presiona el botón de liberar pagos*/
    private void jBLibActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLibActionPerformed

        //Si el proveedor esta vacio entonces
        if(jTProv.getText().trim().compareTo("")==0)
        {            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un proveedor para liberar saldo.", "Liberar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Coloca el borde rojo
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTProv.grabFocus();
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
        
        //Comprueba si el proveedor existe
        iRes    = Star.iExisProv(con, jTProv.getText().trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el proveedor no existe entonces
        if(iRes==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El proveedor: " + jTProv.getText().trim() + " no existe.", "Proveedor", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            //Coloca el borde rojo
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTProv.grabFocus();
            return;
        }

        //Declara variables de la base de datos
        Statement   st;               
        String      sQ;

        /*Libera todos los CXP de ese proveedor*/
        try
        {
            sQ = "DELETE FROM cxp WHERE prov = '" + jTProv.getText().trim() + "'";
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
        JOptionPane.showMessageDialog(null, "Proveedor liberado con éxito.", "Liberar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

    }//GEN-LAST:event_jBLibActionPerformed

    
    /*Cuando se presiona una tecla en el botón de liberar pago*/
    private void jBLibKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLibKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBLibKeyPressed

    
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

    
    //Cuando el mouse entra en el botón de búscar concepto de pago
    private void jBConcepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConcepMouseEntered

        /*Cambia el color del fondo del botón*/
        jBConcep.setBackground(Star.colBot);

    }//GEN-LAST:event_jBConcepMouseEntered

    
    //Cuando el mouse sale del botón de búscar concepto de pago
    private void jBConcepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBConcepMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBConcep.setBackground(colOri);

    }//GEN-LAST:event_jBConcepMouseExited

    
    //Cuando se presiona el botón de búscar concepto de pago
    private void jBConcepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBConcepActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTConcep.getText(), 44, jTConcep, null, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBConcepActionPerformed

    
    //Cuando se presiona una tecla en el botón de búscar concepto de pago
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

    
    //Cuando el mouse entra en el botón de la forma de pago
    private void jBFormPagMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFormPagMouseEntered

        /*Cambia el color del fondo del botón*/
        jBFormPag.setBackground(Star.colBot);

    }//GEN-LAST:event_jBFormPagMouseEntered

    
    //Cuando el mouse sale del botón de la forma de pago
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

    
    //Cuando se presiona una tecla en el botón de la forma de pago
    private void jBFormPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBFormPagKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBFormPagKeyPressed

    private void jCCaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCCaKeyPressed

    
    //Cuando sucede una acción en el check de documentos cancelados
    private void jCCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCCaActionPerformed
                
        /*Obtén cxc de la base de datos y cargalos en la tabla*/
        vCargCxp();
        
    }//GEN-LAST:event_jCCaActionPerformed

    
    //Cuando en folio del banco gana el foco
    private void jTFolBancFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFolBancFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFolBanc.setSelectionStart(0);jTFolBanc.setSelectionEnd(jTFolBanc.getText().length());
        
    }//GEN-LAST:event_jTFolBancFocusGained

    
    //Cuando en folio del banco pierde el foco
    private void jTFolBancFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFolBancFocusLost
        
        /*Coloca el caret al principio del control*/
        jTFolBanc.setCaretPosition(0);
        
    }//GEN-LAST:event_jTFolBancFocusLost

    
    //Cuando se preciona una tecla en el folio de banco
    private void jTFolBancKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFolBancKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFolBancKeyPressed

    
    //Cuando se preciona una tecla en el folio de banco solo se puede letas y numeros
    private void jTFolBancKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFolBancKeyTyped
        
        int iFolBanc=jTFolBanc.getText().trim().length();
        
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
        /*Si se presiona F2 presiona el botón de abonos*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBVerA.doClick();
        /*Si se presiona F5 presiona el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();
        /*Si se presiona F6 presiona el botón de reportes de CXP*/
        else if(evt.getKeyCode() == KeyEvent.VK_F6)
            jBVis.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbon;
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBClas;
    private javax.swing.JButton jBConcep;
    private javax.swing.JButton jBFormPag;
    private javax.swing.JButton jBLib;
    private javax.swing.JButton jBProv;
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JTextField jTComen;
    private javax.swing.JTextField jTConcep;
    private javax.swing.JTextField jTCond;
    private javax.swing.JTextField jTDia;
    private javax.swing.JTextField jTFol;
    private javax.swing.JTextField jTFolBanc;
    private javax.swing.JTextField jTFormPag;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTProv;
    private javax.swing.JTable jTab1;
    private javax.swing.JTable jTab2;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
