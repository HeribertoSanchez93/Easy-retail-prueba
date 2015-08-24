/*CR01 Modificado para JPS Ingenieros
96 variable de control de id de cotizaciones
3228 modificacion para abrir cotizacion
3535 copiar pdf
3743 se agregagon campos para poder enviar todos los datos: para partcot
*/
//Paquete
package ptovta;

//Importaciones
import cats.Viats;
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import static ptovta.Proys.vDelPartCot;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Desktop;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.io.BufferedReader;
import java.io.*;




/*Clase para hacer un nu proyecto*/
public class NewProy extends javax.swing.JFrame
{
    /*Contiene el color original del botón*/
    private java.awt.Color          colOri;
    
    /*Declara variables de instancia*/    
    public static int               iContFiCarat;
    private JTable                  jTabProy;
    private int                     rowTabProy;
    private int                     iContFaltMat;
            
    /*Contador para modificar tabla*/
    private int                     iContCellEd;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean                 bSel;
    
    /*Declara las variables originales de la tabla 1*/
    private String                  sCodOri;
    private String                  sCantOri;
    private String                  sUnidOri;
    private String                  sExistOri;
    private String                  sFaltOri;
    private String                  sProdOri;
    private String                  sDescripOri;
    private String                  sAlmaOri;
    private String                  sMonOri;
    private String                  sCostOri;
    private String                  sCostReOri;
    private String                  sCotizadOri;
    private String                  sCotizOri;
    
    /*Declara las variables originales de la tabla 2*/
    private String                  sNoSerOri;
    private String                  sCodEmpOri;
    private String                  sNomEmpOri;
    private String                  sObservOri;    
    private String                  sImpoOri;
    private String                  sFAltOri;
    private String                  sUltModOri;
    private String                  sFVencOri;
    private String                  sSucOri;
    private String                  sCajOri;
    private String                  sEstacOri;
    private String                  sNomEstacOri;
    //CRR01
    private int                     idCot=1;
    
    
    
    /*Consructor sin argumentos*/
    public NewProy(JTable jTablePro, int row) 
    {                        
        /*Inicializa los componentes gráfcos*/
        initComponents();

        /*Para que la tabla tengan scroll horisontal*/
        jTab2.setAutoResizeMode(0);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que no se muevan las columnas*/
        jTab1.getTableHeader().setReorderingAllowed(false);
        jTab2.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Inicializa el contador de materiales faltantes*/
        iContFaltMat = 0;                                
        
        /*Esconde el campo de la ser*/
        jTSer.setVisible(false);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas de la caratula*/
        iContFiCarat     = 1;
              
        /*Recibe la fila que el usuario selecciono de la tabla de proyectos*/
        rowTabProy       = row;
                
        /*Establece el tamaño de las columnas de la tabla de cotizaciones*/
        jTab2.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTab2.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTab2.getColumnModel().getColumn(4).setPreferredWidth(400);
        jTab2.getColumnModel().getColumn(5).setPreferredWidth(180);
        jTab2.getColumnModel().getColumn(6).setPreferredWidth(180);
        jTab2.getColumnModel().getColumn(8).setPreferredWidth(150);
        jTab2.getColumnModel().getColumn(9).setPreferredWidth(150);
        jTab2.getColumnModel().getColumn(10).setPreferredWidth(150);
        jTab2.getColumnModel().getColumn(13).setPreferredWidth(190);
        jTab2.getColumnModel().getColumn(14).setPreferredWidth(100);
        
        /*Selecciona una fecha inicialmente para los dos jdatechooser*/
        Date f = new Date();
        jDIniObr.setDate(f);
        jDTermObr.setDate(f);
        
        /*Establece los campos de fecha para que solo se puedan modificar con el botón*/
        jDIniObr.getDateEditor().setEnabled(false);
        jDTermObr.getDateEditor().setEnabled(false);
                
        /*Recibe el hanlder de la tabla  de proys para poder alterarla*/
        jTabProy         = jTablePro;
                
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Nuevo Proyecto, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);                        
        
        /*Para que la tabla de cotización este ordenada al mostrarce y al dar clic en el nom de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab1.getModel());
        jTab1.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Para que la tabla de caratulas este ordenada al mostrarce y al dar clic en el nom de la columna*/
        trs = new TableRowSorter<>((DefaultTableModel)jTab2.getModel());
        jTab2.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Crea un moodelo para la lista de pers y que quede guardado para estar agregando o quitando items*/
        jLisPers.setModel(new DefaultListModel());
        
        /*Obtiene la fecha y hora del sistema y colocalo en el jlabel correspondiente*/
        DateFormat dateFormat   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date               = new Date();
        jLFec.setText(dateFormat.format(date));
        
        /*Listener para el combobox de personas*/
        jComPers.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {                               
                //Abre la base de datos nuevamente
                Connection con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Borra los items en el combobox de pers*/
                jComPers.removeAllItems();

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 

                /*Obtiene todas las pers actualizadas y cargalas en el combobox*/
                try
                {
                    sQ = "SELECT nom FROM pers";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos etonces cagalos en el combobox*/
                    while(rs.next())
                        jComPers.addItem(rs.getString("nom"));
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
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Declara variables*/
        String sProy;    
        
        /*Mientras el código del nuevo proyecto este repetido seguirá obteniendo uno que no este repetido*/
        boolean bSi;
        do
        {
            /*Inicialmente no existe el código del nu proyecto*/
            bSi = false;
            
            /*Obtiene el código del nuevo proyecto proyecto*/
            sProy                = Star.sGenClavDia();

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;       
            String      sQ;      

            /*Obtiene si ya existe ese código de nu proyecto en la base de datos*/
            try
            {                  
                sQ = "SELECT proy FROM proys WHERE proy = '" + sProy + "'";
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
            
        }while(bSi);
        
        /*Coloca el código del proyecto en el campo correspondiente*/               
        jTProy.setText(sProy);
                                        
        /*Pon el foco del teclado en el campo del código del cliente*/
        jTEmp.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla 1*/
        jTab1.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTab1.getColumnModel().getColumn(7).setPreferredWidth(400);
        jTab1.getColumnModel().getColumn(9).setPreferredWidth(100);
        jTab1.getColumnModel().getColumn(11).setPreferredWidth(100);
        jTab1.getColumnModel().getColumn(14).setPreferredWidth(120);
        jTab1.getColumnModel().getColumn(15).setPreferredWidth(100);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab1.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab1.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Activa en el text area de descripción que se usen normamente las teclas de tabulador*/
        jTADescrip.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTADescrip.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Activa en el text area de otrs que se usen normamente las teclas de tabulador*/
        jTAOtro.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAOtro.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Activa en el text area de observ de cliente que se usen normamente las teclas de tabulador*/
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAObserv.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ;      
        
        /*Trae todas las pers de la base de datos y cargalas en el combobox, si no existen avisa al cliente*/
        boolean bS = false;
        try
        {                    
            sQ = "SELECT nom FROM pers";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Pon la bandera de que si existen en caso de que no sea la cliente vacia*/
                if(rs.getString("nom").compareTo("")!=0)                                                
                    bS = true;                    
                
                /*Agregalos al combobox*/
                jComPers.addItem(rs.getString("nom"));                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                    
        }
        
        /*Si no hay pers entonces solo avisa al usuario y carga la cadena vacia*/
        if(!bS)
        {
            JOptionPane.showMessageDialog(null, "No existen pers aun.", "Faltan Personas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            jComPers.addItem("");             
        }
        
        /*Comprueba si existen emps ya dadas de alta*/        
        try
        {                    
            sQ = "SELECT nom FROM emps WHERE codemp <> '-'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces mensajea*/
            if(!rs.next())
                JOptionPane.showMessageDialog(null, "No existen clientes aun.", "Faltan Clientes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                         
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                    
        }
        
        /*Inserta en la base de datos un proyecto vacio*/
        try 
        {            
            sQ = "INSERT INTO proys(    proy,                           codemp,     ser, obra, tipobr,     descrip,     plant,  otr,   ubic,        condpag,            nompers,       proyvac,        estad,      estac,                                     tot,    tiement,        subtot,    iva,    estatu,     ubigraf,            sucu,                                              nocaj) " + 
                           "VALUES('" + sProy.replace("'", "''") + "',  '',         '',  '',   '',         '',          '',     '',     '',         '',                 '',             1,             'PE','" +   Login.sUsrG.replace("'", "''") + "',   0,      '',              0,         0,      1,          '',     '" +        Star.sSucu.replace("'", "''") + "','" +     Star.sNoCaj.replace("'", "''") + "')";                                
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
            
        /*Crea el table listener para la tabla de carátulas*/
        TableModelListener l = new TableModelListener() 
        {
            /*Cuando sucede un evento en la tabla de carátulas*/
            @Override
            public void tableChanged(TableModelEvent e) 
            {
                /*Si el evento es por insersicón entonces recalcula los totales de la tabla*/
                if(e.getType() == TableModelEvent.INSERT)             
                    Star.vRecalcImports(jTab2, jTTot, jTSubTot, jTImp);
                /*Reclacula los importes*/
                else if(e.getType() == TableModelEvent.DELETE)
                    Star.vRecalcImports(jTab2, jTTot, jTSubTot, jTImp);                
            }
        };
        
        /*Establece el Modelo para la tabla de carátulas*/
        jTab2.getModel().addTableModelListener(l);
                
        /*Recalcula los importes de toda la tabla en caso de que ya alla datos ahí*/
        if(jTab2.getRowCount()>0)
            Star.vRecalcImports(jTab2, jTTot, jTSubTot, jTImp);
                  
        /*Crea el listener para cuando se cambia de selección en la tabla de cotizaciones*/
        jTab2.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {                
                /*Comprueba la cotización seleccionada y muestra los faltantes al momento en la tabla de materiales faltante*/
                vLoadFalt();               
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
                        sCodOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString();
                        sCantOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString();
                        sUnidOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 3).toString();
                        sExistOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 4).toString();
                        sFaltOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 5).toString();
                        sProdOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 6).toString();
                        sDescripOri     = jTab1.getValueAt(jTab1.getSelectedRow(), 7).toString();
                        sMonOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 8).toString();
                        sCostOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 9).toString();
                        sCostReOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 10).toString();
                        sCotizadOri     = jTab1.getValueAt(jTab1.getSelectedRow(), 11).toString();
                        sCotizOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 12).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab1.setValueAt(sCodOri,           jTab1.getSelectedRow(), 1);                        
                        jTab1.setValueAt(sCantOri,          jTab1.getSelectedRow(), 2);                        
                        jTab1.setValueAt(sUnidOri,          jTab1.getSelectedRow(), 3);                        
                        jTab1.setValueAt(sExistOri,         jTab1.getSelectedRow(), 4);                        
                        jTab1.setValueAt(sFAltOri,          jTab1.getSelectedRow(), 5);                        
                        jTab1.setValueAt(sProdOri,          jTab1.getSelectedRow(), 6);                        
                        jTab1.setValueAt(sDescripOri,       jTab1.getSelectedRow(), 7);                        
                        jTab1.setValueAt(sMonOri,           jTab1.getSelectedRow(), 8);                        
                        jTab1.setValueAt(sCostOri,          jTab1.getSelectedRow(), 9);                        
                        jTab1.setValueAt(sCostReOri,        jTab1.getSelectedRow(), 10);                        
                        jTab1.setValueAt(sCotizadOri,       jTab1.getSelectedRow(), 11);                        
                        jTab1.setValueAt(sCotizOri,         jTab1.getSelectedRow(), 12);                        
                        
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
                /*Si no hay selecciòn entonces regresa*/
                if(jTab2.getSelectedRow()==-1)
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
                        sCodOri             = jTab2.getValueAt(jTab2.getSelectedRow(), 1).toString();
                        sNoSerOri           = jTab2.getValueAt(jTab2.getSelectedRow(), 2).toString();
                        sCodEmpOri          = jTab2.getValueAt(jTab2.getSelectedRow(), 3).toString();
                        sNomEmpOri          = jTab2.getValueAt(jTab2.getSelectedRow(), 4).toString();
                        sObservOri          = jTab2.getValueAt(jTab2.getSelectedRow(), 5).toString();
                        sDescripOri         = jTab2.getValueAt(jTab2.getSelectedRow(), 6).toString();
                        sImpoOri            = jTab2.getValueAt(jTab2.getSelectedRow(), 7).toString();
                        sFAltOri            = jTab2.getValueAt(jTab2.getSelectedRow(), 8).toString();
                        sUltModOri          = jTab2.getValueAt(jTab2.getSelectedRow(), 9).toString();
                        sFVencOri           = jTab2.getValueAt(jTab2.getSelectedRow(), 10).toString();
                        sSucOri             = jTab2.getValueAt(jTab2.getSelectedRow(), 11).toString();
                        sCajOri             = jTab2.getValueAt(jTab2.getSelectedRow(), 12).toString();
                        sEstacOri           = jTab2.getValueAt(jTab2.getSelectedRow(), 13).toString();
                        sNomEstacOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 14).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab2.setValueAt(sCodOri,               jTab2.getSelectedRow(), 1);                        
                        jTab2.setValueAt(sNoSerOri,             jTab2.getSelectedRow(), 2);                        
                        jTab2.setValueAt(sCodEmpOri,            jTab2.getSelectedRow(), 3);                        
                        jTab2.setValueAt(sNomEmpOri,            jTab2.getSelectedRow(), 4);                        
                        jTab2.setValueAt(sObservOri,            jTab2.getSelectedRow(), 5);                        
                        jTab2.setValueAt(sDescripOri,           jTab2.getSelectedRow(), 6);                        
                        jTab2.setValueAt(sImpoOri,              jTab2.getSelectedRow(), 7);                        
                        jTab2.setValueAt(sFAltOri,              jTab2.getSelectedRow(), 8);                        
                        jTab2.setValueAt(sUltModOri,            jTab2.getSelectedRow(), 9);                        
                        jTab2.setValueAt(sFVencOri,             jTab2.getSelectedRow(), 10);                        
                        jTab2.setValueAt(sSucOri,               jTab2.getSelectedRow(), 11);                        
                        jTab2.setValueAt(sCajOri,               jTab2.getSelectedRow(), 12);                        
                        jTab2.setValueAt(sEstacOri,             jTab2.getSelectedRow(), 13);                        
                        jTab2.setValueAt(sNomEstacOri,          jTab2.getSelectedRow(), 14);                        
                       
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla 2*/
        jTab2.addPropertyChangeListener(pro);
        
    }/*Fin de public NewProy() */
        
    
    /*Comprueba la cotización seleccionada y muestra los faltantes al momento en la tabla de materiales faltante*/
    private void vLoadFalt()
    {
        /*Si no hay selecciòn entonces regresa*/
        if(jTab2.getSelectedRow()==-1)
            return;
        
        /*Por cualquier error evalua esto*/
        if(jTab2.getSelectedRow()==-1)
            return;
                
        /*Borra todos los item en la tabla de material faltante*/
        DefaultTableModel d = (DefaultTableModel)jTab1.getModel();
        d.setRowCount(0);
        
        /*Inicializa nuevamente el contador de materiales faltantes*/
        iContFaltMat = 0;                                
            
        /*Obtiene el código de la cotización de la fila*/
        String      sCodCot;
        sCodCot                     = jTab2.getValueAt(jTab2.getSelectedRow(), 1).toString();
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Recorre todas las partidas de la cotización*/
        int iContPart   = 1;
        try
        {
            sQ = "SELECT * FROM partcot WHERE codcot = '" + sCodCot + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Función para comprar existencias entre la cotización y lo que hay en el almacén, para que lo coloque en la tabla*/
                vCompExist(iContPart, sCodCot, rs.getString("cant"), rs.getString("prod"), rs.getString("alma"), rs.getString("mon"), rs.getString("unid"), rs.getString("fmod"));
                
                /*Aumenta en uno el contador de partidas*/
                ++iContPart;                                                
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
        
    }/*Fin de private void vLoadFalt()*/
    
    
    /*Muestra todo el faltante de las cots del proyecto actual en la tabla*/
    private void vLoadFaltT()
    {
        /*Borra todos los item en la tabla de material faltante*/
        DefaultTableModel d = (DefaultTableModel)jTab1.getModel();
        d.setRowCount(0);
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Recorre todas las partidas de las cotizaciónes de este proyecto*/
        int iContPart   = 1;
        try
        {
            sQ = "SELECT * FROM partcot LEFT OUTER JOIN cots ON cots.CODCOT = partcot.CODCOT WHERE cots.PROY = '" + jTProy.getText() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Función para comprar existencias entre la cotización y lo que hay en el almacén, para que lo coloque en la tabla*/
                vCompExist(iContPart, rs.getString("codcot"), rs.getString("cant"), rs.getString("prod"), rs.getString("alma"), rs.getString("mon"), rs.getString("unid"), rs.getString("fmod"));
                
                /*Aumenta en uno el contador de partidas*/
                ++iContPart;                                                
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
        
    }/*Fin de private void vLoadFaltT()*/
                
           
    /*Función para comprar existencias entre la cotización y lo que hay en el almacén, para que lo coloque en la tabla*/
    private void vCompExist(int iContPart, String sCodCot, String sCant, String sCodArt, String sAlma, String sMon, String sUnid,  String sUltM)
    {
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
                
        /*Declara variables*/        
        String sExistProd   = "0";
        String sFalt;
        String sDescrip     = "";
        String sCost        = "";
        String sCostRe      = "";        

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene datos del producto de la base de datos*/
        try
        {
            sQ = "SELECT * FROM prods WHERE prod = '" + sCodArt + "' AND alma = '" + sAlma + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene algunos datos necesarios*/                
                sExistProd  = rs.getString("exist");
                sDescrip    = rs.getString("descrip");
                sCost       = rs.getString("cost");
                sCostRe     = rs.getString("costre");                                                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                    
        }         
        
        /*Si la diferencia es mayor entre lo que se necesita y lo que se tiene entonces*/
        if(Integer.parseInt(sCant)>Integer.parseInt(sExistProd))
        {
            /*Declara variables*/
            String sEsta    = "";            
            String sSiPed   = "No";
            String sFEnt    = "";
                        
            /*Comprueba si el producto ya fue pedido*/
            try
            {
                sQ = "SELECT fmod, estac, fent FROM partords WHERE prod = '" + sCodArt + "' AND codcot = '" + sCodCot + "' AND cant = " + sCant + " AND unid = '" + sUnid + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    /*Obtiene algunos datos necesarios*/                
                    sEsta       = rs.getString("estac");
                    sUltM       = rs.getString("fmod");                    
                    sFEnt       = rs.getString("fent");

                    /*Coloca la bandera*/
                    sSiPed      = "Si";                            
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                        
            }  
            
            /*Obtiene el faltante*/
            sFalt                   = Integer.toString(Integer.parseInt(sCant) - Integer.parseInt(sExistProd));
            
            /*Dale formato de mon al cost*/            
            double dCant            = Double.parseDouble(sCost);                
            NumberFormat n          = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            sCost                   = n.format(dCant);

            /*Dale formato de mon al cost recoger*/            
            dCant                   = Double.parseDouble(sCostRe);                            
            sCostRe                 = n.format(dCant);

            /*Agrega el registro en la tabla de material faltante*/
            DefaultTableModel te    = (DefaultTableModel)jTab1.getModel();            
            Object nu[]             = {iContPart, sCodCot, sCant, sUnid, sExistProd, sFalt, sCodArt, sDescrip, sAlma, sMon, sCost, sCostRe, sSiPed, sEsta, sUltM, sFEnt};
            te.addRow(nu);
            
            /*Coloca la cant de material faltante*/
            iContFaltMat            = iContFaltMat + Integer.parseInt(sFalt);             
            
            /*Coloca en el label la cant de material faltante*/
            jLMatFalt.setText(Integer.toString(iContFaltMat));
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vCompExist(int iContPart, String sCodCot, String sCant, String sCodArt, String sAlma, String sMon)*/
                                    
    
    /*Devuelve el código del proyecto basandones en el día, mes, año, hora, minuto y segundo*/
    public static String sGenCodPro()
    {
        //Declara variables locales        
        String sProy = "";
        
        
        
        
        /*Instancia la fecha para obtener la fecha del día de hoy*/
        Date date               = new Date();
        
        /*Construye el código del prouyecto*/
        sProy            = sProy + Integer.toString(date.getDate());
        sProy            = sProy + Integer.toString(date.getMonth());
        sProy            = sProy + Integer.toString(date.getYear());
        sProy            = sProy + Integer.toString(date.getHours());
        sProy            = sProy + Integer.toString(date.getMinutes());
        sProy            = sProy + Integer.toString(date.getSeconds());
        
        /*Devuelve el código del proyecto*/
        return sProy;
        
    }/*Fin de public String sGenCodPro()*/
    
               
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jTTot = new javax.swing.JTextField();
        jBSal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab2 = new javax.swing.JTable();
        jBDel = new javax.swing.JButton();
        jLFec = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTPai = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTCP = new javax.swing.JTextField();
        jTCall = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTPag1 = new javax.swing.JTextField();
        jTPag2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTCiu = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTTel = new javax.swing.JTextField();
        jTRFC = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTEstad = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTAObserv = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jTCol = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTContac = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTNomb = new javax.swing.JTextField();
        jBBusc = new javax.swing.JButton();
        jTEmp = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jCGuaD = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jComPers = new javax.swing.JComboBox();
        jBAgre = new javax.swing.JButton();
        jBDel1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jLisPers = new javax.swing.JList();
        jLabel16 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTImp = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTSubTot = new javax.swing.JTextField();
        jBAbrPart = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTAOtro = new javax.swing.JTextArea();
        jTObr = new javax.swing.JTextField();
        jTTipObr = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jTEntreg = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTUbic = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTCondPag = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTADescrip = new javax.swing.JTextArea();
        jLabel38 = new javax.swing.JLabel();
        jTPlant = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jDTermObr = new com.toedter.calendar.JDateChooser();
        jLabel40 = new javax.swing.JLabel();
        jDIniObr = new com.toedter.calendar.JDateChooser();
        jLabel36 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jBNewCot = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTab1 = new javax.swing.JTable();
        jBNewCotProv = new javax.swing.JButton();
        jBGuarSal = new javax.swing.JButton();
        jBCop = new javax.swing.JButton();
        jBPDF = new javax.swing.JButton();
        jBOrdCom = new javax.swing.JButton();
        jBVerOrds = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jTProy = new javax.swing.JTextField();
        jBGuar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTUbicGraf = new javax.swing.JTextField();
        jBNew = new javax.swing.JButton();
        jBAbr = new javax.swing.JButton();
        jBLog = new javax.swing.JButton();
        jCJPS2 = new javax.swing.JCheckBox();
        jCJPS1 = new javax.swing.JCheckBox();
        jCSelT = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        jLMatFalt = new javax.swing.JLabel();
        jTSer = new javax.swing.JTextField();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();

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
        jP1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 596, 200, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jTEmp);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 610, 120, 30));

        jTab2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Cotización", "Serie", "Cliente", "Nombre Cliente", "Observaciones", "Descripción", "Total", "Fecha de Creación", "Ultima Modificación", "Fecha Vencimiento", "Sucursal", "Caja", "Usuario", "Nombre Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab2.setGridColor(new java.awt.Color(255, 255, 255));
        jTab2.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTab2MouseClicked(evt);
            }
        });
        jTab2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTab2KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab2);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 1030, 120));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Cancelar");
        jBDel.setToolTipText("Borrar Cotizaciòn(es) (Ctrl+SUPR)");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jBCop);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 460, 160, 20));

        jLFec.setFont(new java.awt.Font("Tekton Pro", 0, 18)); // NOI18N
        jLFec.setForeground(new java.awt.Color(0, 0, 153));
        jLFec.setText("--------------");
        jP1.add(jLFec, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 540, 280, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Total:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 600, -1, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Datos de la Obra");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 230, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Calle:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, -1, -1));

        jTPai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPai.setNextFocusableComponent(jTPag1);
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
        });
        jPanel1.add(jTPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 180, 20));

        jLabel9.setText("CP:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, -1, -1));

        jTCP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCP.setNextFocusableComponent(jTEstad);
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
        });
        jPanel1.add(jTCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 190, 20));

        jTCall.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCall.setNextFocusableComponent(jTCP);
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
        });
        jPanel1.add(jTCall, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 190, 20));

        jLabel10.setText("Nombre:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 80, -1));

        jLabel6.setText("Pais:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        jLabel13.setText("Teléfono:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel7.setText("Ciudad:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jTPag1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPag1.setNextFocusableComponent(jTPag2);
        jTPag1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPag1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPag1FocusLost(evt);
            }
        });
        jTPag1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPag1KeyPressed(evt);
            }
        });
        jPanel1.add(jTPag1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 180, 20));

        jTPag2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPag2.setNextFocusableComponent(jTContac);
        jTPag2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPag2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPag2FocusLost(evt);
            }
        });
        jTPag2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPag2KeyPressed(evt);
            }
        });
        jPanel1.add(jTPag2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 180, 20));

        jLabel5.setText("Estado:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, -1, -1));

        jTCiu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCiu.setNextFocusableComponent(jTPai);
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
        });
        jPanel1.add(jTCiu, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 180, 20));

        jLabel11.setText("RFC:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, -1, -1));

        jTTel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel.setName(""); // NOI18N
        jTTel.setNextFocusableComponent(jTCol);
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
        });
        jPanel1.add(jTTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 180, 20));

        jTRFC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRFC.setNextFocusableComponent(jTAObserv);
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
        });
        jPanel1.add(jTRFC, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, 190, 20));

        jLabel4.setText("Colonia:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel8.setText("Cliente:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, -1, -1));

        jLabel20.setText("Observ. del");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 80, -1));

        jTEstad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEstad.setNextFocusableComponent(jTRFC);
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
        });
        jPanel1.add(jTEstad, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 190, 20));

        jTAObserv.setColumns(20);
        jTAObserv.setLineWrap(true);
        jTAObserv.setRows(5);
        jTAObserv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAObserv.setNextFocusableComponent(jCGuaD);
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
        jScrollPane2.setViewportView(jTAObserv);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, 190, 40));

        jLabel15.setText("Página Web1:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCol.setNextFocusableComponent(jTCiu);
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
        jPanel1.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 180, 20));

        jLabel21.setText("Página Web2:");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        jTContac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTContac.setNextFocusableComponent(jTCall);
        jTContac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTContacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTContacFocusLost(evt);
            }
        });
        jTContac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTContacKeyPressed(evt);
            }
        });
        jPanel1.add(jTContac, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 180, 20));

        jLabel12.setText("*Cliente:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 80, -1));

        jTNomb.setEditable(false);
        jTNomb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNomb.setNextFocusableComponent(jTTel);
        jTNomb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNombFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNombFocusLost(evt);
            }
        });
        jTNomb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNombKeyPressed(evt);
            }
        });
        jPanel1.add(jTNomb, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 190, 20));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setText("...");
        jBBusc.setToolTipText("Bùscar Cliente(s)");
        jBBusc.setNextFocusableComponent(jTNomb);
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
        jPanel1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 30, 20));

        jTEmp.setBackground(new java.awt.Color(204, 255, 204));
        jTEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEmp.setNextFocusableComponent(jBBusc);
        jTEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEmpFocusLost(evt);
            }
        });
        jTEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEmpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTEmpKeyTyped(evt);
            }
        });
        jPanel1.add(jTEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 150, 20));

        jLabel35.setText("Contacto:");
        jPanel1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        jCGuaD.setText("Guardar datos de Cliente");
        jCGuaD.setNextFocusableComponent(jTObr);
        jCGuaD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGuaDKeyPressed(evt);
            }
        });
        jPanel1.add(jCGuaD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 230, -1));

        jP1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 570, 230));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel3KeyPressed(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setText("Obra:");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jComPers.setNextFocusableComponent(jBAgre);
        jComPers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComPersKeyPressed(evt);
            }
        });
        jPanel3.add(jComPers, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 400, 20));

        jBAgre.setBackground(new java.awt.Color(255, 255, 255));
        jBAgre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBAgre.setForeground(new java.awt.Color(0, 102, 0));
        jBAgre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBAgre.setToolTipText("Agregar persona a Proyecto");
        jBAgre.setNextFocusableComponent(jLisPers);
        jBAgre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAgreMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAgreMouseExited(evt);
            }
        });
        jBAgre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgreActionPerformed(evt);
            }
        });
        jBAgre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAgreKeyPressed(evt);
            }
        });
        jPanel3.add(jBAgre, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 80, 20));

        jBDel1.setBackground(new java.awt.Color(255, 255, 255));
        jBDel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBDel1.setForeground(new java.awt.Color(0, 102, 0));
        jBDel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel1.setToolTipText("Borrar persona de Proyecto");
        jBDel1.setNextFocusableComponent(jBNewCotProv);
        jBDel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDel1MouseExited(evt);
            }
        });
        jBDel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDel1ActionPerformed(evt);
            }
        });
        jBDel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDel1KeyPressed(evt);
            }
        });
        jPanel3.add(jBDel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 80, 20));

        jLisPers.setNextFocusableComponent(jBDel1);
        jLisPers.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLisPersKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jLisPers);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 400, 50));

        jLabel16.setText("Personas:");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 60, -1));

        jP1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 170, 620, 90));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel25.setText("Impuesto:");
        jP1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 570, 110, -1));

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
        jP1.add(jTImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 568, 200, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel26.setText("Sub Total:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 544, 110, -1));

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
        jP1.add(jTSubTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 540, 200, -1));

        jBAbrPart.setBackground(new java.awt.Color(255, 255, 255));
        jBAbrPart.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBAbrPart.setForeground(new java.awt.Color(0, 102, 0));
        jBAbrPart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/abr5.png"))); // NOI18N
        jBAbrPart.setText("Abrir");
        jBAbrPart.setToolTipText("Abrir Cotizaciòn(es) (Ctrl+A)");
        jBAbrPart.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBAbrPart.setNextFocusableComponent(jBDel);
        jBAbrPart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAbrPartMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAbrPartMouseExited(evt);
            }
        });
        jBAbrPart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbrPartActionPerformed(evt);
            }
        });
        jBAbrPart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAbrPartKeyPressed(evt);
            }
        });
        jP1.add(jBAbrPart, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 440, 160, 20));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel4KeyPressed(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setText("Obra:");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel24.setText("Tipo Obra:");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLabel30.setText("Descripción:");
        jPanel4.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jTAOtro.setColumns(20);
        jTAOtro.setLineWrap(true);
        jTAOtro.setRows(5);
        jTAOtro.setNextFocusableComponent(jTUbic);
        jTAOtro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAOtroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAOtroFocusLost(evt);
            }
        });
        jTAOtro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAOtroKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(jTAOtro);

        jPanel4.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 190, 20));

        jTObr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTObr.setNextFocusableComponent(jTTipObr);
        jTObr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTObrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTObrFocusLost(evt);
            }
        });
        jTObr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTObrKeyPressed(evt);
            }
        });
        jPanel4.add(jTObr, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 190, 20));

        jTTipObr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTipObr.setNextFocusableComponent(jTADescrip);
        jTTipObr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTipObrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTipObrFocusLost(evt);
            }
        });
        jTTipObr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTipObrKeyPressed(evt);
            }
        });
        jPanel4.add(jTTipObr, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 190, 20));

        jLabel31.setText("Obra:");
        jPanel4.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jLabel32.setText("Planta:");
        jPanel4.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 60, -1));

        jTEntreg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEntreg.setNextFocusableComponent(jTCondPag);
        jTEntreg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEntregFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEntregFocusLost(evt);
            }
        });
        jTEntreg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEntregKeyPressed(evt);
            }
        });
        jPanel4.add(jTEntreg, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 200, 20));

        jLabel33.setText("Terminación Obra:");
        jPanel4.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 110, -1));

        jTUbic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUbic.setNextFocusableComponent(jTEntreg);
        jTUbic.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUbicFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUbicFocusLost(evt);
            }
        });
        jTUbic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUbicKeyPressed(evt);
            }
        });
        jPanel4.add(jTUbic, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 200, 20));

        jLabel34.setText("Cond. de Pago:");
        jPanel4.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, 90, -1));

        jTCondPag.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCondPag.setNextFocusableComponent(jComPers);
        jTCondPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCondPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCondPagFocusLost(evt);
            }
        });
        jTCondPag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCondPagKeyPressed(evt);
            }
        });
        jPanel4.add(jTCondPag, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, 200, 20));

        jTADescrip.setColumns(20);
        jTADescrip.setLineWrap(true);
        jTADescrip.setRows(5);
        jTADescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTADescrip.setNextFocusableComponent(jTPlant);
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
        jScrollPane6.setViewportView(jTADescrip);

        jPanel4.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 190, 20));

        jLabel38.setText("Ubicación:");
        jPanel4.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, -1, -1));

        jTPlant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPlant.setNextFocusableComponent(jTAOtro);
        jTPlant.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPlantFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPlantFocusLost(evt);
            }
        });
        jTPlant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPlantKeyPressed(evt);
            }
        });
        jPanel4.add(jTPlant, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 190, 20));

        jLabel39.setText("Otros:");
        jPanel4.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 80, -1));

        jLabel41.setText("Tiempo Entrega:");
        jPanel4.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 100, -1));

        jDTermObr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDTermObrKeyPressed(evt);
            }
        });
        jPanel4.add(jDTermObr, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 200, -1));

        jLabel40.setText("Inicio de Obra:");
        jPanel4.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 90, -1));

        jDIniObr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDIniObrKeyPressed(evt);
            }
        });
        jPanel4.add(jDIniObr, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 200, -1));

        jP1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 620, 120));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel36.setText("Resúmen de Cotizaciones");
        jP1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 390, 360, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("Fecha del Proyecto:");
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 540, 140, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Material Faltante");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 220, -1));

        jBNewCot.setBackground(new java.awt.Color(255, 255, 255));
        jBNewCot.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNewCot.setForeground(new java.awt.Color(0, 102, 0));
        jBNewCot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNewCot.setText("Nueva");
        jBNewCot.setToolTipText("Copiar (Ctrl+N)");
        jBNewCot.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNewCot.setNextFocusableComponent(jBAbrPart);
        jBNewCot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBNewCotMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBNewCotMouseExited(evt);
            }
        });
        jBNewCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNewCotActionPerformed(evt);
            }
        });
        jBNewCot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBNewCotKeyPressed(evt);
            }
        });
        jP1.add(jBNewCot, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 420, 160, 20));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("Personas que Iran al Proyecto");
        jP1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, 230, -1));

        jTab1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Cotización", "Qty.", "Unidad", "Existencia", "Faltante", "Producto", "Descripción", "Almacén", "Moneda", "Costo", "Costo Recoger", "Cotizado", "Cotizo", "Ultima Modificación", "Fecha Entrega"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTab1.setGridColor(new java.awt.Color(255, 255, 255));
        jTab1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTab1KeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTab1);

        jP1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 1030, 110));

        jBNewCotProv.setBackground(new java.awt.Color(255, 255, 255));
        jBNewCotProv.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNewCotProv.setForeground(new java.awt.Color(0, 102, 0));
        jBNewCotProv.setText("Material faltante F3");
        jBNewCotProv.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNewCotProv.setNextFocusableComponent(jBOrdCom);
        jBNewCotProv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBNewCotProvMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBNewCotProvMouseExited(evt);
            }
        });
        jBNewCotProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNewCotProvActionPerformed(evt);
            }
        });
        jBNewCotProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBNewCotProvKeyPressed(evt);
            }
        });
        jP1.add(jBNewCotProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 280, 160, 20));

        jBGuarSal.setBackground(new java.awt.Color(255, 255, 255));
        jBGuarSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuarSal.setForeground(new java.awt.Color(0, 102, 0));
        jBGuarSal.setText("Guardar y Salir ");
        jBGuarSal.setToolTipText("Guardar Proyecto y Salir (F2)");
        jBGuarSal.setNextFocusableComponent(jBSal);
        jBGuarSal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGuarSalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGuarSalMouseExited(evt);
            }
        });
        jBGuarSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuarSalActionPerformed(evt);
            }
        });
        jBGuarSal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGuarSalKeyPressed(evt);
            }
        });
        jP1.add(jBGuarSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 610, 160, 30));

        jBCop.setBackground(new java.awt.Color(255, 255, 255));
        jBCop.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCop.setForeground(new java.awt.Color(0, 102, 0));
        jBCop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/copiar.png"))); // NOI18N
        jBCop.setText("Copiar");
        jBCop.setToolTipText("Copiar Cotización(es) (F10)");
        jBCop.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBCop.setNextFocusableComponent(jBPDF);
        jBCop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCopMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCopMouseExited(evt);
            }
        });
        jBCop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCopActionPerformed(evt);
            }
        });
        jBCop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCopKeyPressed(evt);
            }
        });
        jP1.add(jBCop, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 480, 160, 20));

        jBPDF.setBackground(new java.awt.Color(255, 255, 255));
        jBPDF.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBPDF.setForeground(new java.awt.Color(0, 102, 0));
        jBPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/pdf.png"))); // NOI18N
        jBPDF.setText("Ver");
        jBPDF.setToolTipText("Ver PDF de Cptizacion(es) (F11)");
        jBPDF.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBPDF.setNextFocusableComponent(jCJPS1);
        jBPDF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBPDFMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBPDFMouseExited(evt);
            }
        });
        jBPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPDFActionPerformed(evt);
            }
        });
        jBPDF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBPDFKeyPressed(evt);
            }
        });
        jP1.add(jBPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 500, 160, 20));

        jBOrdCom.setBackground(new java.awt.Color(255, 255, 255));
        jBOrdCom.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBOrdCom.setForeground(new java.awt.Color(0, 102, 0));
        jBOrdCom.setText("Orden de Compra F4");
        jBOrdCom.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBOrdCom.setName(""); // NOI18N
        jBOrdCom.setNextFocusableComponent(jBVerOrds);
        jBOrdCom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBOrdComMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBOrdComMouseExited(evt);
            }
        });
        jBOrdCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOrdComActionPerformed(evt);
            }
        });
        jBOrdCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBOrdComKeyPressed(evt);
            }
        });
        jP1.add(jBOrdCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 300, 160, 20));

        jBVerOrds.setBackground(new java.awt.Color(255, 255, 255));
        jBVerOrds.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVerOrds.setForeground(new java.awt.Color(0, 102, 0));
        jBVerOrds.setText("Ver Ordenes F5");
        jBVerOrds.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVerOrds.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVerOrdsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVerOrdsMouseExited(evt);
            }
        });
        jBVerOrds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVerOrdsActionPerformed(evt);
            }
        });
        jBVerOrds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVerOrdsKeyPressed(evt);
            }
        });
        jP1.add(jBVerOrds, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 320, 160, 20));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setText("Datos del Cliente");
        jP1.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, -1));

        jTProy.setEditable(false);
        jTProy.setForeground(new java.awt.Color(51, 51, 255));
        jTProy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTProy.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 255), 1, true));
        jTProy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProyFocusGained(evt);
            }
        });
        jTProy.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProyKeyPressed(evt);
            }
        });
        jP1.add(jTProy, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 90, 20));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar Proyecto(Ctrl+G)");
        jBGuar.setNextFocusableComponent(jBGuarSal);
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 610, 120, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 153, 0));
        jLabel14.setText("Ubicación Geográfica:");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 190, -1));

        jTUbicGraf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUbicGraf.setNextFocusableComponent(jBAbr);
        jTUbicGraf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUbicGrafFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUbicGrafFocusLost(evt);
            }
        });
        jTUbicGraf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUbicGrafKeyPressed(evt);
            }
        });
        jP1.add(jTUbicGraf, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, 370, 20));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setText("Nueva");
        jBNew.setToolTipText("Nueva Ruta");
        jBNew.setNextFocusableComponent(jBLog);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 570, -1, 40));

        jBAbr.setBackground(new java.awt.Color(255, 255, 255));
        jBAbr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/zonageografica.png"))); // NOI18N
        jBAbr.setToolTipText("Abrir Ruta");
        jBAbr.setNextFocusableComponent(jBNew);
        jBAbr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAbrMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAbrMouseExited(evt);
            }
        });
        jBAbr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbrActionPerformed(evt);
            }
        });
        jBAbr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAbrKeyPressed(evt);
            }
        });
        jP1.add(jBAbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 570, 70, 40));

        jBLog.setBackground(new java.awt.Color(255, 255, 255));
        jBLog.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logcorr.png"))); // NOI18N
        jBLog.setMnemonic('L');
        jBLog.setNextFocusableComponent(jBGuar);
        jBLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBLogMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBLogMouseExited(evt);
            }
        });
        jBLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLogActionPerformed(evt);
            }
        });
        jBLog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBLogKeyPressed(evt);
            }
        });
        jP1.add(jBLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 580, 110, 40));

        jCJPS2.setBackground(new java.awt.Color(255, 255, 255));
        jCJPS2.setText("JPS2");
        jCJPS2.setNextFocusableComponent(jTab2);
        jCJPS2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCJPS2KeyPressed(evt);
            }
        });
        jP1.add(jCJPS2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 520, 80, -1));

        jCJPS1.setBackground(new java.awt.Color(255, 255, 255));
        jCJPS1.setSelected(true);
        jCJPS1.setText("JPS1");
        jCJPS1.setNextFocusableComponent(jCJPS2);
        jCJPS1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCJPS1KeyPressed(evt);
            }
        });
        jP1.add(jCJPS1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 520, 80, -1));

        jCSelT.setBackground(new java.awt.Color(255, 255, 255));
        jCSelT.setText("Seleccionar Todo");
        jCSelT.setNextFocusableComponent(jBNewCot);
        jCSelT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCSelTActionPerformed(evt);
            }
        });
        jCSelT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCSelTKeyPressed(evt);
            }
        });
        jP1.add(jCSelT, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 340, 160, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Total Faltantes:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        jLMatFalt.setForeground(new java.awt.Color(255, 0, 0));
        jLMatFalt.setText("0");
        jP1.add(jLMatFalt, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 100, -1));

        jTSer.setText("Serie Empresa");
        jP1.add(jTSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 70, -1));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 620, 160, 20));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel37.setText("Cod. Proyecto:");
        jP1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 110, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 1225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
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
    
       
    /*Cuando se presiona una tecla en el campo de edición de col*/
    private void jTColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTColKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de col*/
    private void jTColFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCol.setSelectionStart(0);jTCol.setSelectionEnd(jTCol.getText().length());
        
    }//GEN-LAST:event_jTColFocusGained

    
    /*Cuando se presiona una tecla en el text area de observ*/
    private void jTAObservKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAObservKeyPressed

        /*Si se presiona la tecla de tabulador entonces brincar al próximo control que puede tomar
        el foco del teclado*/
        if(evt.getKeyCode() == KeyEvent.VK_TAB)
        {
            /*Mueve el foco del teclado al próximo control focusable y regresa*/
            KeyboardFocusManager m = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            m.focusNextComponent();
            return;
        }

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAObservKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de area de observ de la cliente*/
    private void jTAObservFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTAObserv.setSelectionStart(0);jTAObserv.setSelectionEnd(jTAObserv.getText().length());
        
    }//GEN-LAST:event_jTAObservFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de estad*/
    private void jTEstadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstadKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstadKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de estad*/
    private void jTEstadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTEstad.setSelectionStart(0);jTEstad.setSelectionEnd(jTEstad.getText().length());
        
    }//GEN-LAST:event_jTEstadFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de RFC*/
    private void jTRFCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRFCKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTRFCKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de RFC*/
    private void jTRFCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTRFC.setSelectionStart(0);jTRFC.setSelectionEnd(jTRFC.getText().length());
        
    }//GEN-LAST:event_jTRFCFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición del teléfono*/
    private void jTTelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de teléfono*/
    private void jTTelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTTel.setSelectionStart(0);jTTel.setSelectionEnd(jTTel.getText().length());
        
    }//GEN-LAST:event_jTTelFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de ciu*/
    private void jTCiuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCiuKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCiuKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de ciu*/
    private void jTCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCiu.setSelectionStart(0);jTCiu.setSelectionEnd(jTCiu.getText().length());
        
    }//GEN-LAST:event_jTCiuFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de página web*/
    private void jTPag2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPag2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPag2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de página web 2*/
    private void jTPag2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag2FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTPag2.setSelectionStart(0);jTPag2.setSelectionEnd(jTPag2.getText().length());
        
    }//GEN-LAST:event_jTPag2FocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de página web 1*/
    private void jTPag1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPag1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPag1KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de página web */
    private void jTPag1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag1FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTPag1.setSelectionStart(0);jTPag1.setSelectionEnd(jTPag1.getText().length());
        
    }//GEN-LAST:event_jTPag1FocusGained

    
    /*Cuando se presiona un botón en el campo de edición calle*/
    private void jTCallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCallKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edicón de calle*/
    private void jTCallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCall.setSelectionStart(0);jTCall.setSelectionEnd(jTCall.getText().length());
        
    }//GEN-LAST:event_jTCallFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de cp*/
    private void jTCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCPKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de CP*/
    private void jTCPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCP.setSelectionStart(0);jTCP.setSelectionEnd(jTCP.getText().length());
        
    }//GEN-LAST:event_jTCPFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de pai*/
    private void jTPaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPaiKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPaiKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de pai*/
    private void jTPaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTPai.setSelectionStart(0);jTPai.setSelectionEnd(jTPai.getText().length());
        
    }//GEN-LAST:event_jTPaiFocusGained

    
    /*Cuando se presiona una tecla en el botón de nueva partida*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se presiona el botón de borrar partida*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si el usuario no a seleccionado una partida no puede avanzar*/
        if(jTab2.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una partida de la tabla para borrar.", "Borrar Partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab2.grabFocus();                        
            return;
            
        }/*Fin de if(jTableCaratula.getSelectedRow()==-1)*/
        
        /*Preguntar al usuario si esta seguro de que querer borrar la caratula con la cotización*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres canselar la(s) carátula(s) con todo y la(s) cotización(es)?", "Canselar Carátula y Cotización", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Obtiene el código del proyecto*/
        String sProy           = jTProy.getText();
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Mientras el usuario no escriba un motivo de cancelación o cancele la cancelación entonces*/
        String      sMot;
        do
        {            
            /*Pide al usuario el motivo de la cancelación de la venta*/
            sMot     = JOptionPane.showInputDialog(this,"Motivo de cancelación:", "Motivo", JOptionPane.QUESTION_MESSAGE);
            
            /*Si el usuario cancelo el cuadro entonces regresa por que no puede continuar*/
            if(sMot==null)
                return;

            /*Si el usuario no escribio un motiv de cancelación entonces mensajea*/
            if(sMot.compareTo("")==0)
                JOptionPane.showMessageDialog(null, "Escribe un motivo de cancelación.", "Escribir motivo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                 

        }
        while(sMot.compareTo("")== 0);        
        
        /*Preguntar al usuario si esta seguro querer hacer la cancelación de la factura*/
        Object[] xeta = {"Si","No"};
        iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres hacer la(s) cancelación(es)?", "Cancelación", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes== JOptionPane.NO_OPTION || iRes== JOptionPane.CANCEL_OPTION)
            return;                       
                
        //Abre la base de datos                             
        con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Bandera para saber si hubo alguna cancelación*/
        boolean bCan    = false;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        int iSel[]              = jTab2.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab2.getModel();
        /*Recorre toda la selección del usuario*/        
        for(int x = iSel.length - 1; x >= 0; x--)
        {            
            //Declara variables locales
            String sEstad   = "";            
            
            /*Obtiene algunos datos de la cotización*/            
            try
            {                  
                sQ = "SELECT estad FROM cots WHERE codcot = '" + jTab2.getValueAt(iSel[x], 1).toString().trim() + "'";                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    sEstad  = rs.getString("estad");
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Si la cotización esta confirmada entonces*/
            if(sEstad.compareTo("CO")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La cotización " + jTab1.getValueAt(iSel[x], 1).toString() + " esta confirmada.", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                continue;
            }

            /*Si es una cotización cancelada entonces*/
            if(sEstad.compareTo("CA")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La cotización " + jTab1.getValueAt(iSel[x], 1).toString() + " esta cancelada.", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }

            /*Actualiza la cotización para que sea de cancelación*/
            try 
            {            
                sQ = "UPDATE cots SET "
                        + "estad        = 'CA', "
                        + "motiv        = '" + sMot.replace("'", "''") + "', "
                        + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE codcot = '" + jTab1.getValueAt(iSel[x], 1).toString() + "'";                                                
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }        
            
            /*Coloca la bandera para saber que hubo alguna cancelación*/
            bCan    = true;
            /*Borralo de la tabla*/            
            tm.removeRow(iSel[x]);
            
            /*Resta en uno el contador de filas el contador de filas en uno*/
            --iContFiCarat;
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Mensajea si hubo alguna cancelación*/
        if(bCan)
            JOptionPane.showMessageDialog(null, "Cotización(es) cancelada(s) con éxito.", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Cuando se presiona una tecla en la tabla de cotización*/
    private void jTab2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTab2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTab2KeyPressed

    
    /*Cuando se presiona una tecla en el botón de nu*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona el botón de cancelar*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Pregunta al usuario si esta seguro de salir*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        /*Obtiene el código del proyecto*/
        String sCodPro = jTProy.getText();
        
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ;  
        
        /*Comprueba si el proyecto esta vacio*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT proyvac FROM proys WHERE proy = '" + sCodPro + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = rs.getString("proyvac").compareTo("0")==0;                                                                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                    
        }  
        
        /*Si el proyecto esta vacio entonces*/
        if(!bSi)
        {
            /*Borra todos los viáticos para este proyecto ya que no se guardo*/
            try 
            {                
                sQ = "DELETE FROM viatspro WHERE codpro = '" + sCodPro + "'";                    
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
        
        /*Borra todos los proys que son vacios*/
        try 
        {            
            sQ = "DELETE FROM proys WHERE proy = '" + sCodPro + "' AND proyvac = 1";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                    
         }
        
        /*Libera el proyecto actual en caso de que exista*/
        try 
        {            
            sQ = "UPDATE proys SET "
                    + "estatu       = 0, "
                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE proy   = '" + sCodPro.replace("'", "''") + "' AND proyvac = 0";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                    
         }
        
        /*Comprueba si el proyecto actual es el vacio*/        
        boolean bNoVac    = false;
        try
        {
            sQ = "SELECT proyvac FROM proys WHERE proy = '" + sCodPro + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si el proyecto no es vacio entonces establece la bandera*/
                if(rs.getString("proyvac").compareTo("0")==0)
                    bNoVac    = true;                                                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                    
        }  
                        
        /*Si el proyecto no es proyecto vacio entonces*/
        if(bNoVac)
        {
            /*Declara variables*/
            String sSubTot;
            String sImpue;
            String sTot;
            String sFCrea       = "";
            String sFMod        = "";
            String sEsta        = "";

            /*Obtiene los totes*/
            sSubTot         = jTSubTot.getText();
            sImpue          = jTImp.getText();
            sTot            = jTTot.getText();

            /*Quitales los carácteres de pesos y comas*/
            sSubTot         = sSubTot.replace   ("$", "").replace(",", "");            
            sImpue          = sImpue.replace    ("$", "").replace(",", "");            
            sTot            = sTot.replace      ("$", "").replace(",", "");            
            
            /*Actualiza los totes del proyecto*/
            try 
            {                
                sQ = "UPDATE proys SET "
                        + "subtot       = " + sSubTot + ", "
                        + "tot          = " + sTot + ", "
                        + "iva          = " + sImpue + ", "
                        + "codemp       = '" + jTEmp.getText().replace("'", "''") + "', "
                        + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE proy   = '" + sCodPro.replace("'", "''") + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                        
            }	
            
            /*Obtiene algunos datos del proyecto*/
            try
            {
                sQ = "SELECT falt, fmod, estad FROM proys WHERE proy = '" + sCodPro + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                {                 
                    sFCrea          = rs.getString("falt");
                    sFMod           = rs.getString("fmod");
                    sEsta           = rs.getString("estad");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                        
            }  

            /*Obtiene el nombre del usuario*/
            String sNomb    = "";
            try
            {
                sQ = "SELECT nom FROM estacs WHERE estac = '" + Login.sUsrG + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                    sNomb      = rs.getString("nom");                                   
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                        
            }
        
            /*Dale formato de mon al tot*/            
            double dCant            = Double.parseDouble(sTot);                
            NumberFormat n          = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            sTot                    = n.format(dCant);
            
            /*Comprueba si ya existe el proyecto en la tabla*/
            bSi = false;
            for(int x = 0; x < jTabProy.getRowCount(); x++)
            {
                /*Compara el código del proyecto para saber si esta, si esta entonces coloca la bandera*/
                if(sCodPro.compareTo(jTabProy.getValueAt(x, 1).toString())==0)
                    bSi = true;
            }

            /*Si no existe el proyecto en la tabla de proyectos entonces agrega el nuevo proyecto*/
            if(!bSi)
            {
                DefaultTableModel tm    = (DefaultTableModel)jTabProy.getModel();
                Object nu[]             = {Proys.iContFi, sCodPro, jTNomb.getText(), sTot, sFCrea, sFMod, sEsta, Star.sSucu, Star.sNoCaj, Login.sUsrG, sNomb};
                tm.addRow(nu);
            }                
        
            /*Aumenta en uno el contador de filas*/
            ++Proys.iContFi;

        }/*Fin de if(bNoVac)*/
                  
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return; 

        /*Cierra el formulario*/
        this.dispose();        
        
        /*Llama al recolector de basura*/
        System.gc();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el campo de edición de código de cliente*/
    private void jTTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTotKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición del código del cliente*/
    private void jTTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTTot.setSelectionStart(0);jTTot.setSelectionEnd(jTTot.getText().length());
        
    }//GEN-LAST:event_jTTotFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de IVA*/
    private void jTImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTImp.setSelectionStart(0);jTImp.setSelectionEnd(jTImp.getText().length());
        
    }//GEN-LAST:event_jTImpFocusGained

    
    /*Cuando se presiona una teclaen el campo de edición de IVA*/
    private void jTImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTImpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTImpKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de área de subtot*/
    private void jTSubTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel.setSelectionStart(0);jTTel.setSelectionEnd(jTTel.getText().length());
        
    }//GEN-LAST:event_jTSubTotFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de sub tot*/
    private void jTSubTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSubTotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSubTotKeyPressed

    
    /*Cuando se presiona el botón de abrir partida*/
    private void jBAbrPartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrPartActionPerformed

        /*Lee el nombre de la cliente seleccionada*/
        String sNomEmp     = jTNomb.getText();
        
        /*Lee el código de la cliente seleccionada*/
        String sCodEmp     = jTEmp.getText();
        
        /*Lee el código del proyecto*/
        String sProy       = jTProy.getText();
        
        /*Si el usuario no a seleccionado una partida no puede avanzar*/
        if(jTab2.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una partida de la tabla para modificar.", "Modificar Partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab2.grabFocus();            
            return;            
        }
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab2.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            System.out.println(jTab2.getValueAt(iSel[x], 1).toString().trim());
            /*Muestra el formulario para ver la cotización*/
            NewCotIng n = new NewCotIng( jTab2, iContFiCarat, jTab2.getValueAt(iSel[x], 1).toString().trim(), false, sCodEmp,null, true);
            n.setVisible(true);            
        }
        
    }//GEN-LAST:event_jBAbrPartActionPerformed

    
    /*Cuando se presiona una tecla en el botón de abrir partida*/
    private void jBAbrPartKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAbrPartKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);     
        
    }//GEN-LAST:event_jBAbrPartKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de área de otros*/
    private void jTAOtroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAOtroFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAOtro.setSelectionStart(0);jTAOtro.setSelectionEnd(jTAOtro.getText().length());
        
    }//GEN-LAST:event_jTAOtroFocusGained

    
    /*Cuando se presiona una tecla en el campo de área de otros*/
    private void jTAOtroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAOtroKeyPressed
        
        /*Si se presiona la tecla de tabulador entonces brincar al próximo control que puede tomar
        el foco del teclado*/
        if(evt.getKeyCode() == KeyEvent.VK_TAB)
        {
            /*Mueve el foco del teclado al próximo control focusable y regresa*/
            KeyboardFocusManager m = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            m.focusNextComponent();
            return;
        }

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAOtroKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de obr*/
    private void jTObrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTObrFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTObr.setSelectionStart(0);jTObr.setSelectionEnd(jTObr.getText().length());
        
    }//GEN-LAST:event_jTObrFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de obr*/
    private void jTObrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTObrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTObrKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de tipo de obr 1*/
    private void jTTipObrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTipObrFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTipObr.setSelectionStart(0);jTTipObr.setSelectionEnd(jTTipObr.getText().length());                
        
    }//GEN-LAST:event_jTTipObrFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de tipo obr*/
    private void jTTipObrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTipObrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTipObrKeyPressed

            
    /*Cuando se gana el foco del teclado en el campo de edición de tiempo de entrega*/
    private void jTEntregFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEntregFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEntreg.setSelectionStart(0);jTEntreg.setSelectionEnd(jTEntreg.getText().length());        
        
    }//GEN-LAST:event_jTEntregFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de tiempo de entrega*/
    private void jTEntregKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEntregKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEntregKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de ubicación*/
    private void jTUbicFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUbicFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUbic.setSelectionStart(0);jTUbic.setSelectionEnd(jTUbic.getText().length());        
        
    }//GEN-LAST:event_jTUbicFocusGained

    
    /*Cuando se presiona una tecla en el campo de texto de ubicación*/
    private void jTUbicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUbicKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUbicKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de condiciones de pago*/
    private void jTCondPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCondPagFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCondPag.setSelectionStart(0);jTCondPag.setSelectionEnd(jTCondPag.getText().length());
        
    }//GEN-LAST:event_jTCondPagFocusGained

    
    /*Cuando se presiona una tecla en el campo de edición de condiciones de pago*/
    private void jTCondPagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCondPagKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCondPagKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de área de descripción*/
    private void jTADescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTADescripFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTADescrip.setSelectionStart(0);jTADescrip.setSelectionEnd(jTADescrip.getText().length());
        
    }//GEN-LAST:event_jTADescripFocusGained

    
    /*Cuando se presiona una tecla en el campo de texto de descripción*/
    private void jTADescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTADescripKeyPressed
        
        /*Si se presiona la tecla de tabulador entonces brincar al próximo control que puede tomar
        el foco del teclado*/
        if(evt.getKeyCode() == KeyEvent.VK_TAB)
        {
            /*Mueve el foco del teclado al próximo control focusable y regresa*/
            KeyboardFocusManager m = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            m.focusNextComponent();
            return;
        }

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTADescripKeyPressed
    
    
    /*Cuando se presiona el botón de nueva partida*/
    private void jBNewCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewCotActionPerformed

        /*Lee el nombre de la cliente seleccionada*/
        String sNomEmp     = jTNomb.getText();
        
        /*Lee el código de la cliente seleccionada*/
        String sCodEmp     = jTEmp.getText();
        
        /*Lee el código del proyecto*/
        String sProy       = jTProy.getText();
        
        /*Si si no se a seleccionado una cliente entonces*/
        if(sCodEmp.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado un cliente.", "Nombre Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Coloca el foco del teclado en el campo del código de emps y regresa*/
            jTEmp.grabFocus();            
            return;            
        }
        
        /*Si la cliente no es válida entonces*/
        if(jTNomb.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una cliente válida.", "Nuevo Proyecto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTEmp.grabFocus();               
            return;  
        }
        //CR llamar nueva cotizacion
        /*Llama al formulario para nueva cotización, envia el código de la cliente y del proyecto*/        
        NewCotIng n = new NewCotIng( jTab2, iContFiCarat, sProy, false, sCodEmp, null, true);
        n.setVisible(true);
        ++iContFiCarat;
    }//GEN-LAST:event_jBNewCotActionPerformed

    
    /*Cuando se presiona una tecla en el botón de nueva partida*/
    private void jBNewCotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewCotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);     
        
    }//GEN-LAST:event_jBNewCotKeyPressed

    
    /*Cuando se presiona el botón de agregar persona*/
    private void jBAgreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgreActionPerformed
        
        //Declara variables locales
        String sNom;
        
        
        
        
        /*Lee el item seleccionado en el combobox de pers*/        
        sNom = jComPers.getSelectedItem().toString();
        
        /*Si el nom es la cadena vacia entonces*/
        if(sNom.compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una persona.", "Seleccionar Persona", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control y regresa*/
            jComPers.grabFocus();                        
            return;
        }
        
        /*Recorre toda la lista*/
        for (int i=0; i < jLisPers.getModel().getSize(); i++) 
        {
            /*Lee la cadena de la lista*/
            String str = ((String)jLisPers.getModel().getElementAt(i));
            
            /*Compara para ver si existe el nom*/
            if(str.compareTo(sNom)==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La persona \"" + sNom + " ya esta en el proyecto.", "Persona Existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                
                return;
            }
            
        }/*Fin de for (int i=0; i < jListPersonas.getModel().getSize(); i++) */
                
        /*Agregalo a la lista correspondiente*/            
        DefaultListModel m = (DefaultListModel)jLisPers.getModel();        
        m.addElement(sNom);
        
    }//GEN-LAST:event_jBAgreActionPerformed

    
    /*Cuando se presiona una tecla en el botón de agregar pers*/
    private void jBAgreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAgreKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBAgreKeyPressed

    
    /*Cuando se presiona una tecla en el combobox de pers*/
    private void jComPersKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComPersKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComPersKeyPressed

    
    /*Cuando se presiona una tecla en el panel de agregar pers*/
    private void jPanel3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
                
    }//GEN-LAST:event_jPanel3KeyPressed

    
    /*Cuando se presiona una tecla en el panel de datos de la obr*/
    private void jPanel4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel4KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
                
    }//GEN-LAST:event_jPanel4KeyPressed

    
    /*Cuando se presiona una tecla en el panel de datos de la cliente*/
    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jPanel1KeyPressed

    
    /*Cuando se presiona el botón de borrar persna*/
    private void jBDel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDel1ActionPerformed
        
        /*Si el usuario no a seleccionado un item de la lista de pers entonces*/
        if(jLisPers.getSelectedIndex()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una persona de la lista para borrar.", "Borrar Personas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en la lista de pers y regresa*/
            jLisPers.grabFocus();            
            return;
        }
        
        /*Preguntar al usuario si esta seguro de querer borrar a la persona de la lista*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar a esta persona?", "Borrar Persona", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Remueve el item de la lista*/
        int i               = jLisPers.getSelectedIndex();
        DefaultListModel m  = (DefaultListModel)jLisPers.getModel();
        m.remove(i);                                
        
    }//GEN-LAST:event_jBDel1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar pers*/
    private void jBDel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDel1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);     
        
    }//GEN-LAST:event_jBDel1KeyPressed

    
    /*Cuando se presiona una tecla en la tabla de faltantes*/
    private void jTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTab1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTab1KeyPressed
        
    
    /*Cuando se presiona una tecla en el botón de nueva cotización*/
    private void jBNewCotProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewCotProvKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewCotProvKeyPressed

    
    /*Cuando se presiona el botón de guardar y salir*/
    private void jBGuarSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarSalActionPerformed
        
        /*Función para guardar el proyecto y cierra la forma*/
        vGuaPro(1);                                
            
    }//GEN-LAST:event_jBGuarSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón de guardar y salir*/
    private void jBGuarSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarSalKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de plant*/
    private void jTPlantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPlantKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPlantKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de plant*/
    private void jTPlantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPlantFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPlant.setSelectionStart(0);jTPlant.setSelectionEnd(jTPlant.getText().length());
        
    }//GEN-LAST:event_jTPlantFocusGained

    
    /*Cuando se presiona una tecla en el botón de copia*/
    private void jBCopKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCopKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCopKeyPressed

    
    /*Cuando se presiona el botón de copia*/
    private void jBCopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCopActionPerformed
                    
        String test="",sSubTot="",sImpue="",sTot="",sMon="",sFVenc= "";
        /*Si el usuario no a seleccionado una partida no puede avanzar*/
        if(jTab2.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una carátula de la tabla para copiar.", "Copiar Carátula", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab2.grabFocus();                           
            return;            
        }
               
        /*Preguntar al usuario si esta seguro de querer copiar la carátula*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres copiar la(s) cotización(es)?", "Copiar Cotización(es)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables locales
        String      sCodCot;                                                      
        String      sNomEmp         = "";
        
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab2.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Obtiene el código de la cotización*/        
            sCodCot         = jTab2.getModel().getValueAt(iSel[x], 1).toString();
            
            /*Esta variable contiene el consecutivo*/
            String sConsec  = "";   
            
            /*Esta variable contiene el nombre del usuario*/
            String sNomb    = "";
            
            /*Variables de la cotización*/
            String sNoSer       = "";
            String sCodEmp      = "";
            String sObserv      = "";
            String sDescrip     = "";
            String sSubTotGral  = "";

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;        
            String      sQ; 
            System.out.println("llego");
            /*Obtiene todos los datos de la cotización de la base de datos*/
            try
            {            
                sQ = "SELECT cots.SER, cots.ESTAD, cots.TOT, cots.SUBTOTMAT, cots.IMPUE, cots.SUBTOT, cots.SUBTOTGRAL2, cots.TIPCAM, cots.SER, estacs.NOM, emps.NOM, cots.CODEMP, cots.OBSERV, cots.ESTAC, cots.SUBTOTGRAL, cots.MANOBR, cots.SUBTOTMAT, cots.DESCRIP, cots.NOSER, cots.MON FROM cots LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP ) = cots.CODEMP LEFT OUTER JOIN estacs ON estacs.ESTAC =  cots.ESTAC WHERE codcot = '" + sCodCot + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    /*Obtiene todos los datos de la cotizacion*/                                                                                                                    
                    sNomb               = rs.getString("estacs.NOM");                                 
                    sNomEmp             = rs.getString("emps.NOM");                                 
                    sNoSer              = rs.getString("cots.NOSER");
                    sCodEmp             = rs.getString("cots.CODEMP");
                    sObserv             = rs.getString("cots.OBSERV");
                    sDescrip            = rs.getString("cots.DESCRIP");
                    sSubTotGral         = rs.getString("cots.SUBTOTGRAL");
                    sMon                = rs.getString("cots.mon");
                    System.out.println(sNomEmp);
                    //Declara variables de la base de datos                    
                    ResultSet   rs2;                    
                    String      sQ2; 

                    /*Obtiene el consecutivo de la cotización en base a la serie de la cotización original*/                    
                    try
                    {
                        sQ2= "SELECT consec, ser FROM consecs WHERE tip = 'COT' AND ser = '" + sNoSer + "'";
                        st = con.createStatement();
                        rs2= st.executeQuery(sQ2);
                        /*Si hay datos obtiene el resultado*/
                        if(rs2.next())
                            sConsec         = rs2.getString("consec");                                                                               
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
                        sQ2= "UPDATE consecs SET "
                                + "consec           = consec + 1 "
                                + "WHERE tip        ='COT' AND ser = '" + rs.getString("cots.NOSER").replace("'", "''") + "'";                                                
                        st = con.createStatement();
                        st.executeUpdate(sQ2);            
                     }
                     catch(SQLException expnSQL)
                     {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                        return;                                                
                    }
                    test=rs.getString("cots.NOSER");
                    //sCodEmp=rs.getString("cots.CODEMP");
                    sSubTot=rs.getString("cots.SUBTOT");
                    sImpue=rs.getString("cots.IMPUE");
                    sTot= rs.getString("cots.TOT");
                    System.out.println(sMon);
                    String TipCam=rs.getString("cots.TIPCAM");
                    System.out.println(TipCam);
                    /*Inserta en la base de datos la cotización copia*/
                    vInsCotNewPro(rs.getString("cots.NOSER") + sConsec, jTProy.getText(), rs.getString("cots.CODEMP"), rs.getString("cots.OBSERV"), rs.getString("cots.ESTAC"), rs.getString("cots.SUBTOTGRAL"), rs.getString("cots.MANOBR"), rs.getString("cots.SUBTOTMAT"), rs.getString("cots.DESCRIP"), rs.getString("cots.NOSER"), rs.getString("cots.SER"), TipCam, rs.getString("cots.SUBTOTGRAL2"), rs.getString("cots.SUBTOT"), rs.getString("cots.IMPUE"), rs.getString("cots.TOT"), rs.getString("cots.ESTAD"), con, sMon);

                    /*Inserta en la base de datos las partidas de la cotización para el nuevo proyecto*/
                    vInstParts(rs.getString("cots.NOSER") + sConsec, sCodCot, con);                
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                        
            }
        /*CRR02*/

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
                sCallLoc            = rs.getString("calle");                                    
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
        /*Obtiene el símbolo de la moneda*/
        String sSimb    = "";
        try
        {
            sQ = "SELECT simb FROM mons WHERE mon = '" + sMon + "'";
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
        String sTotLet          = Star.sObLet(sTot, sMon, sSimb, true);
        /*Obtiene algunos datos de la nueva cotización*/
        String      sFCrea          = "";       
        //String      sFVenc          = "";
            try
            {                  
                sQ = "SELECT falt, fvenc FROM cots WHERE codcot = '" +test + sConsec + "'";
                st = con.createStatement();
                /*Si hay datos entonces obtiene los resutlados*/
                rs = st.executeQuery(sQ);
                if(rs.next())
                {
                    sFCrea  = rs.getString("falt");                                                   
                    sFVenc  = rs.getString("fvenc");                                                   
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                       
            }
            System.out.println("llego");
        /*Declara las variable final para el thread*/
        final String sCodEmpFi      = sCodEmp;
        final String sCodCotFi      = test + sConsec;
        final String sSubTotFi      = sSubTot;
        final String sImpueFi       = sImpue;
        final String sTotFi         = sTot;
        final String sMonFi         = sMon;
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
        final boolean bMandCoFi     = false;
        final String sImpLetFi      = sTotLet;
        final String sFCotFi        = sFVenc;       
        /*Thread para quitar carga*/
        (new Thread()
        {
            @Override
            public void run()
            {
                Star.vGenPDFCot(sCodEmpFi, sCodCotFi, sMonFi, sSubTotFi, sImpueFi, sTotFi, sNomLocFi, sTelLocFi, sColLocFi, sCallLocFi, sCPLocFi, sCiuLocFi, sEstaLocFi, sPaiLocFi, sRFCLocFi, sObservFi, sDescripFi, sFCotFi, sImpLetFi, false, false, "", "", "", bMandCoFi, "");
            }
            
        }).start();
        /*funcion para copiar un archivo*/
//        FileInputStream copiarDesde = null; 
//        FileOutputStream copiarA = null; 
//        /*Trae la carpeta compartida del servidor*/
//        String sCarp    = "";                    
//        try
//        {
//            sQ = "SELECT rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
//            st = con.createStatement();
//            rs = st.executeQuery(sQ);
//            /*Si hay datos entonces obtiene el resultado*/
//            if(rs.next())
//                sCarp          = rs.getString("rutap");
//        }
//        catch(SQLException expnSQL)
//        {
//            //Procesa el error y regresa error
//            Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
//            return;                                                    
//        } 
//        
//            String sRutPDF     = sCarp + "\\Cotizaciones";
//
//            /*Si el directorio de cotizaciones no existe entonces creala*/                               
//            if(!new File(sRutPDF).exists())
//                new File(sRutPDF).mkdir();
//
//            /*Si el directorio de la cliente no existe entonces creala*/                               
//            sRutPDF             = sRutPDF + "\\" + Login.sCodEmpBD;
//            if(!new File(sRutPDF).exists())
//                new File(sRutPDF).mkdir();
//            String sCodCot2;
//            sCodCot2=test+Integer.toString(Integer.parseInt(sConsec));
//            String sRutPDF2=sRutPDF+"\\"+sCodCot2+ ".pdf";
//            sRutPDF         += "\\" + sCodCot + ".pdf";
//        try { 
//            copiarDesde = new FileInputStream(sRutPDF); 
//            copiarA = new FileOutputStream(sRutPDF2); 
//            byte[] buffer = new byte[4096]; 
//            int lecturaBytes; 
//
//            while((lecturaBytes = copiarDesde.read(buffer)) != -1) 
//                copiarA.write(buffer, 0, lecturaBytes);        
//            System.out.println("El archivo se ha copiado correctamente."); 
//        }catch (IOException IOE) { 
//
//                }finally { 
//            if(copiarDesde != null) 
//                try { 
//                    copiarDesde.close(); 
//                } catch (IOException IOE) { 
//
//                } 
//            if(copiarA != null) 
//                try{ 
//                    copiarA.close(); 
//                } catch (IOException IOE) { 
//
//                } 
//        } 
        /*CRR02*/    
            /*Obtiene algunos datos de la nueva cotización*/
            
            try
            {                  
                sQ = "SELECT falt, fvenc FROM cots WHERE codcot = '" +test + sConsec + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resutlados*/
                if(rs.next())
                {
                    sFCrea  = rs.getString("falt");                                                   
                    sFVenc  = rs.getString("fvenc");                                                   
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                                       
            }
            
            /*Dale formato de moneda a los totales*/            
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
            double dCant    = Double.parseDouble(sSubTotGral);                
            sSubTotGral     = n.format(dCant);
            System.out.println(sNomEmp);
            sTot="$"+sTot;
            /*Agrega los datos de la nueva carátula de copia en la tabla*/
            DefaultTableModel te    = (DefaultTableModel)jTab2.getModel();
            Object nu[]             = {iContFiCarat, sNoSer + sConsec, sNoSer, sCodEmp, sNomEmp, sObserv, sDescrip, sTot, sFCrea, sFCrea, sFVenc, Star.sSucu, Star.sNoCaj, Login.sUsrG, sNomb};            
            te.addRow(nu);                           
            --iContFiCarat;
            idCot++;
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                                                                    

        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
                
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return; 

        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Exito al realizar la copia de la(s) cotizacione.", "Exito en Copia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));         
        
    }//GEN-LAST:event_jBCopActionPerformed
                                

    /*Inserta en partcotjps1*/
    private void vInsPartCotJPS(String sCotNew, String sCodArt, String sCodUnid, String sCant, String sDescrip, String sPre, String sImpo, String sMon, String sPre2, String sDesc1, String sDesc2, String sDesc3, String sDesc4, String sDesc5, String sImpo2, String sImpueVal, String sImpueImpo, String sImpueImpo2, String sAlma, Connection con, String TipCam,String serprod,String comenser,String tall,String codimpue,String colo,String garan,String lot,String pedimen)
    {
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 

        

        /*Inserta la partida un registro*/
        try 
        {            
            sQ = "INSERT INTO partcot(    codcot,                               prod,                                        tipcam,                             unid,                                      mon,                                   cant,               descrip,                                    pre,               impo,         falt,           pre2,               desc1,              desc2,          desc3,          desc4,              desc5,          impo2,          impueval,          impueimpo,         impueimpo2,            alma,                           serprod,                         comenser,                         tall,                         codimpue,                         colo,                         garan,                         lot,                         pedimen) " + 
                             "VALUES('" + sCotNew.replace("'", "''") + "','" +  sCodArt.replace("'", "''") + "',"+ "'" +  TipCam.replace("'", "''") + "','" + sCodUnid.replace("'", "''") + "', " +"'" + sMon.replace("'", "''")+ "', " +       sCant + ",'" +      sDescrip.replace("'", "''") + "', " +       sPre + ", " +       sImpo + ",      now(),   " +   sPre2 + ", " +      sDesc1 + ", " +     sDesc2 + ", " + sDesc3 + ", " + sDesc4 + ", " +     sDesc5 + ", " + sImpo2 + ", " + sImpueVal + ", " + sImpueImpo + ", " + sImpueImpo2 + ", '" + sAlma.replace("'", "''") + "','"+serprod.replace("'", "''")+"','"+comenser.replace("'", "''")+"','"+tall.replace("'", "''")+"','"+codimpue.replace("'", "''")+"','"+colo.replace("'", "''")+"','"+garan.replace("'", "''")+"','"+lot.replace("'", "''")+"','"+pedimen.replace("'", "''")+"')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL) 
        { 
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                        
        }
                
    }/*Fin de private void vInsertaPartCotizacionesJPS2(String sCotNew, String sCodArt, String sCodUnid, String sCant, String sDescrip, String sPre, String sImpo, Connection con)*/   
    
    
    /*Inserta en la base de datos las partidas de la cotización de jps1 y jps2 para el nu proyecto*/
    private void vInstParts(String sCotNew, String sCodCot, Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 




        /*Obtiene todas las partidas de la cotización en base a su código*/
	try
        {                  
            sQ = "SELECT * FROM partcot WHERE codcot = '" + sCodCot + "'";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                //CRR01
                /*Inserta en partcot*/
                vInsPartCotJPS(sCotNew, rs.getString("prod"), rs.getString("unid"), rs.getString("cant"), rs.getString("descrip"), rs.getString("pre"), rs.getString("impo"), rs.getString("mon"), rs.getString("pre2"), rs.getString("desc1"), rs.getString("desc2"), rs.getString("desc3"), rs.getString("desc4"), rs.getString("desc5"), rs.getString("impo2"), rs.getString("impueval"), rs.getString("impueimpo"), rs.getString("impueimpo2"), rs.getString("alma"), con, rs.getString("tipcam"),rs.getString("serprod"),rs.getString("comenser"),rs.getString("tall"),rs.getString("codimpue"),rs.getString("colo"),rs.getString("garan"),rs.getString("lot"),rs.getString("pedimen") );                                
                //CRR01
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                        
        }        
                
    }/*Fin de private void vInstParts(String sCodCot)*/
    
        
    /*Inserta en la base de datos la cotización para el nu proyecto*/
    private void vInsCotNewPro(String sCotNew, String sCodProN, String sCodEm, String sObserv, String sEsta, String sSubTotGral, String sManObr, String sSubTotMat, String sDescrip, String sNoSer, String sSer, String sTipCam, String sSubTotGral2, String sSubTot, String sImpue, String sTot, String sEstad, Connection con,String sMon)
    {
        //Inserta en la tabla de cotizaciones los datos
        Star.iInsCots(con, sCotNew.replace("'", "''"), sCodProN.replace("'", "''"), sNoSer, sCodEm.replace("'", "''"), sSer, sObserv, sDescrip.replace("'", "''"), sSubTotGral, sSubTotMat, sManObr, sSubTot, sImpue, sTot, sEstad.replace("'", "''"), sSubTotGral2, sSubTotMat, "now()", "0", "0", sMon, sTipCam, "now()", "now()");        
    }
    
    
    /*Cuando se presiona una tecla en el campo de edición de contac*/
    private void jTContacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTContacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTContacKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de atención*/
    private void jTContacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTContacFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPag1.setSelectionStart(0);jTPag1.setSelectionEnd(jTPag1.getText().length());
        
    }//GEN-LAST:event_jTContacFocusGained

               
    /*Cuando se presiona una tecla en el botón de ordén de compra*/
    private void jBOrdComKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBOrdComKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBOrdComKeyPressed
    
    
    /*Cuando se presiona el botón de ver ordenes de compra*/
    private void jBVerOrdsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerOrdsActionPerformed
        
        /*Muestra el formulario de las ordenes de compra*/
        VOrds v = new VOrds(jTProy.getText());
        v.setVisible(true);
        
    }//GEN-LAST:event_jBVerOrdsActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver ordenes de compra*/
    private void jBVerOrdsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVerOrdsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVerOrdsKeyPressed


    
    
    
    
    /*Cuando se presiona una tecla en el date de fecha de entrega*/
    private void jDTermObrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDTermObrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDTermObrKeyPressed

    
    /*Cuando se presiona una tecla en el date de inicio de obr*/
    private void jDIniObrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDIniObrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDIniObrKeyPressed
   
    
    /*Cuando se presiona una tecla en la lista de pers*/
    private void jLisPersKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLisPersKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);     
        
    }//GEN-LAST:event_jLisPersKeyPressed

    
    /*Cuando se presiona una tecla en el campo de código del proyecto*/
    private void jTProyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProyKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProyKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de código de prouyecto*/
    private void jTProyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProyFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTProy.setSelectionStart(0);jTProy.setSelectionEnd(jTProy.getText().length());
        
    }//GEN-LAST:event_jTProyFocusGained

    
    /*Función para guardar el proyecto*/
    private void vGuaPro(int iTip)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;
        Connection  con;  
        String      sQ; 
        
        //Declara variables locales
        String      sProy;
        String      sCodEmp;
        String      sObr;
        String      sTipObr;
        String      sDescrip;
        String      sPlant;
        String      sNomEmp;
        String      sOtrs;
        String      sIniObr;
        String      sTot;
        String      sSubTot;
        String      sUbicGraf;
        String      sIVA;
        String      sUbi;
        String      sTerObr;
        String      sEsta           = "";
        String      sTiemEnt;
        String      sCondPag;
        String      sPers           = "";
        String      sFCrea          = "";
        String      sFMod           = "";
        
        
        
                        
        /*Obtiene el código del proyecto*/
        sProy           = jTProy.getText();        
        
        /*Obtiene el código de la cliente*/
        sCodEmp         = jTEmp.getText();
                
        /*Obtiene el nom de la cliente*/
        sNomEmp         = jTNomb.getText();
                
        /*Si el nombre de la cliente es vacio entonces*/
        if(sNomEmp.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Tienes que seleccionar un cliente.", "Seleccionar Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control y regresa*/
            jTEmp.grabFocus();                        
            return;
        }                
        
        /*Si la cliente no es válida entonces*/
        if(jTNomb.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una cliente válida.", "Proyecto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo y regresa*/
            jTEmp.grabFocus();                
            return;  
        }
        
        /*Si no se a seleccionado una fecha de inicio de obr entonces*/
        if(jDIniObr.getDate()==null)
        {
            /*Coloca el borde rojo*/                               
            jDIniObr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Tienes que seleccionar una fecha de inicio de obra.", "Seleccionar Fecha Inicial", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control y regresa*/
            jDIniObr.grabFocus();                        
            return;
        }
        
        /*Si no se a seleccionado una fecha de terminación de obra entonces*/
        if(jDTermObr.getDate()==null)
        {
            /*Coloca el borde rojo*/                               
            jDTermObr.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Tienes que seleccionar una fecha de terminación de obr.", "Seleccionar Fecha Final", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control y regresa*/
            jDTermObr.grabFocus();            
            return;
        }                
        
        /*Preguntar al usuario si esta seguro de que estan bien los datos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Guardar Cambios Proyecto", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
         
        /*Obtiene la ubicación gráfica*/
        sUbicGraf           = jTUbicGraf.getText();
        
        /*Obtiene la obr*/
        sObr                = jTObr.getText();
        
        /*Obtiene el tipo de obr*/
        sTipObr             = jTTipObr.getText();
        
        /*Obtiene la descripción*/
        sDescrip            = jTADescrip.getText();
        
        /*Obtiene la plant*/
        sPlant              = jTPlant.getText();
        
        /*Obtiene otros*/
        sOtrs               = jTAOtro.getText();
        
        /*Obtiene el inicio de obr*/
        Date f              = jDIniObr.getDate();
        SimpleDateFormat sdf= new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        sIniObr             = sdf.format(f);      
        
        /*Obtiene la terminación de obr*/
        f                   = jDTermObr.getDate();
        sdf                 = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        sTerObr             = sdf.format(f);      
        
        /*Obtiene la ubicación*/
        sUbi                = jTUbic.getText();
        
        /*Obtiene el tiempo de entrega*/
        sTiemEnt            = jTEntreg.getText();
        
        /*Obtiene las condiciones de pago*/
        sCondPag            = jTCondPag.getText();
        
        /*Obtiene el total*/
        sTot                = jTTot.getText();
                        
        /*Si el total tiene el signo de dollar quitaselo*/
        sTot                = sTot.replace("$", "").replace(",", "");                        
        
        /*Obtiene el subtot*/
        sSubTot             = jTSubTot.getText();
                        
        /*Si el subtot tiene el signo de dollar quitaselo*/
        sSubTot             = sSubTot.replace("$", "").replace(",", "");                        
        
        /*Obtiene el IVA*/
        sIVA                = jTImp.getText();
                        
        /*Si el IVA tiene el signo de dollar quitaselo*/
        sIVA                = sIVA.replace("$", "").replace(",", "");                
        
        /*Recorre toda la lista de personas para formar la cadena a pasar*/
        for(int x = 0; x < jLisPers.getModel().getSize(); x++)
            sPers       = sPers + jLisPers.getModel().getElementAt(x) + "<>";
        
        /*Si la lista no esta vacia entonces quita el último separador de los noms*/
        if(jLisPers.getModel().getSize()!=0)        
            sPers       = sPers.substring(0, sPers.length()-2);        

        //Abre la base de datos nuevamente
        con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Cambia el estado del proyecto a que ya no es proyecto vacio y la fecha de última modificación*/
        try 
        {            
            sQ = "UPDATE proys SET "
                    + "proyvac          = 0, "
                    + "codemp           = '" + sCodEmp.replace("'", "''") + "', "
                    + "obra             = '" + sObr.replace("'", "''") + "', "
                    + "tipobr           = '" + sTipObr.replace("'", "''") + "', "
                    + "descrip          = '" + sDescrip.replace("'", "''") + "', "
                    + "plant            = '" + sPlant.replace("'", "''") + "', "
                    + "otr              = '" + sOtrs.replace("'", "''") + "', "
                    + "iniobr           = '" + sIniObr.replace("'", "''") + "', "
                    + "termobr          = '" + sTerObr.replace("'", "''") + "', "
                    + "ubic             = '" + sUbi.replace("'", "''") + "', "
                    + "tiement          = '" + sTiemEnt.replace("'", "''") + "', "
                    + "condpag          = '" + sCondPag.replace("'", "''") + "', "
                    + "tot              = " + sTot + ", "
                    + "fmod             = now(), "
                    + "estac            = '" + Login.sUsrG.replace("'", "''") + "', "
                    + "subtot           = " + sSubTot + ", "
                    + "iva              = " + sIVA + ", "
                    + "nompers          = '" + sPers.replace("'", "''") + "', "
                    + "ubigraf          = '" + sUbicGraf.replace("'", "''") + "', "
                    + "sucu             = '" + Star.sSucu.replace("'", "''") + "', "
                    + "nocaj            = '" + Star.sNoCaj.replace("'", "''") + "' "
                    + "WHERE proy       = '" + sProy.replace("'", "''") + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
         }
        
        /*Obtiene la fecha de creación del proyecto, la de última modificación y el estad*/        
        try
        {                  
            sQ = "SELECT falt, fmod, estad FROM proys WHERE proy = '" + sProy + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene la fecha de creación*/
                sFCrea          = rs.getString("falt");   
                
                /*Obtiene la fecha de última modificación*/
                sFMod           = rs.getString("fmod");   
                
                /*Obtiene el estad*/
                sEsta           = rs.getString("estad");                                   
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }    
                
        /*Si el usuario marco que se guarden los datos de la cliente y si no es la cliente mostrador entonces*/
        if(jCGuaD.isSelected() && jTEmp.getText().compareTo(Star.sCliMostG)!=0)
        {            
            /*Actualiza la información de la cliente*/
            try 
            {            
                sQ = "UPDATE emps SET "
                        + "tel                              = '" + jTTel.getText().replace("'", "''") + "', "
                        + "col                              = '" + jTCol.getText().replace("'", "''") + "', "
                        + "ciu                              = '" + jTCiu.getText().replace("'", "''") + "', "
                        + "pai                              = '" + jTPai.getText().replace("'", "''") + "', "
                        + "estad                            = '" + jTEstad.getText().replace("'", "''") + "', "
                        + "pagweb1                          = '" + jTPag1.getText().replace("'", "''") + "', "
                        + "pagweb2                          = '" + jTPag2.getText().replace("'", "''") + "', "
                        + "calle                            = '" + jTCall.getText().replace("'", "''") + "', "
                        + "contac                           = '" + jTContac.getText().replace("'", "''") + "', "
                        + "CP                               = '" + jTCP.getText().replace("'", "''") + "', "
                        + "RFC                              = '" + jTRFC.getText().replace("'", "''") + "', "
                        + "observ                           = '" + jTAObserv.getText().replace("'", "''") + "' "
                        + "WHERE CONCAT_WS('', ser, codemp )= '" + jTEmp.getText().replace("'", "''") + "'";                                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                                        
            }
            
        }/*Fin de if(jCGuaD.isSelected())*/                                
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        /*Obtiene el nom de El usuario*/
        String sNomb    = "";
        try
        {
            sQ = "SELECT nom FROM estacs WHERE estac = '" + Login.sUsrG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sNomb      = rs.getString("nom");                                                   
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

        /*Formatea el tot a mon*/        
        double dCant    = Double.parseDouble(sTot);                        
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        sTot            = n.format(dCant);
                
        /*Comprueba si el código del proyecto ya esta en la tabla de proys del otro formulario*/
        boolean bSi = false;
        for(int x = 0; x < jTabProy.getRowCount(); x++)
        {
            /*Si el código del proyecto ya esta insertado en la tabla entonces*/
            if(jTabProy.getModel().getValueAt(x, 1).toString().compareTo(sProy)==0)            
            {
                /*Colcoa el índice nuevo*/
                rowTabProy  = x;
                
                /*Activa la bandera para saber si ya esta insertado o no y sal del búcle*/
                bSi = true;
                break;
            }                                                   
        }
               
        /*Si el proyecto ya existe en la tabla de proyecto entonces solo actualizalo*/
        if(bSi)
        {
            jTabProy.setValueAt(sNomEmp,     rowTabProy, 2);
            jTabProy.setValueAt(sTot,        rowTabProy, 3);
            jTabProy.setValueAt(sFCrea,      rowTabProy, 4);
            jTabProy.setValueAt(sFMod,       rowTabProy, 5);
            jTabProy.setValueAt(Login.sUsrG, rowTabProy, 6);
            jTabProy.setValueAt(sEsta,       rowTabProy, 7);        
        }
        /*Else, insertalo*/
        else
        {
            /*Agrega a la tabla de proyectos del otro formulario el nuevo proyecto*/
            DefaultTableModel tm    = (DefaultTableModel)jTabProy.getModel();
            Object nu[]             = {Proys.iContFi, sProy, sNomEmp, sTot, sFCrea, sFMod, sEsta, Star.sSucu, Star.sNoCaj, Login.sUsrG, sNomb};
            tm.addRow(nu);

            /*Aumenta en uno el contador de filas*/
            ++Proys.iContFi;
        }
                        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Proyecto guardado con éxito.", "Éxito al guardar cambios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
        /*Si tiene que cerrar la forma entonces cerrarla*/
        if(iTip==1)
        {
            /*Llama al recolector de basura y cierra la forma*/
            System.gc();        
            dispose();
        }
        
    }/*Fin de private void vGuaPro()*/
        
        
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed
        
        /*Función para guardar el proyecto*/
        vGuaPro(0);                
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuando se presiona una tecla en el campo de guardar cambios*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de ubicación gráfica*/
    private void jTUbicGrafFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUbicGrafFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUbicGraf.setSelectionStart(0);jTUbicGraf.setSelectionEnd(jTUbicGraf.getText().length());        
        
    }//GEN-LAST:event_jTUbicGrafFocusGained

    
    /*Cuando se presiona una tecla en el campo de ubicación gráfica*/
    private void jTUbicGrafKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUbicGrafKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUbicGrafKeyPressed

    
    /*Cuando se presiona una tecla en el botón de abrir*/
    private void jBAbrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAbrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBAbrKeyPressed

    
    /*Cuando se presiona una tecla en el botón de nueva*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona el botón de abrir*/
    private void jBAbrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrActionPerformed
        
       //Declara variables locales        
        String url;
        
        
        
        
        /*Lee la página web que esta en el campo*/
        url             = jTUbicGraf.getText();
        
        /*Si la url es cadena vacia entonces*/
        if(url.compareTo("")==0)
        {
            /*Mensajea al usuario*/
            JOptionPane.showMessageDialog(null, "Selecciona una ruta válida.", "Selecciona Ruta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control y regresa*/
            jTUbicGraf.grabFocus();                        
            return;
        }
        
        /*Abre la página de google maps ya con la ubicación en el navegador por default*/
        try 
        {
            if(Desktop.isDesktopSupported())                                      
                Desktop.getDesktop().browse(new URI(url));
            else 
            {            
                Runtime r = Runtime.getRuntime();
                r.exec("iexplorer " + url);
            }
        } 
        catch(IOException expnIO) 
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                    
        }      
        catch(URISyntaxException expnUriSynta)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnUriSynta.getMessage(), Star.sErrUriSynta, expnUriSynta.getStackTrace());                                            
        }
        
    }//GEN-LAST:event_jBAbrActionPerformed

    
    /*Cuando se presiona el botón de nueva*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
        
        //Declara variables locales        
        String url      = "https://maps.google.com.mx/";
        
        
        /*Abre la página de google maps en el navegador por default*/
        try 
        {
            if(Desktop.isDesktopSupported())                                      
                Desktop.getDesktop().browse(new URI(url));
            else 
            {            
                Runtime r = Runtime.getRuntime();
                r.exec("iexplorer " + url);
            }
        } 
        catch(IOException expnIO) 
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
            return;                                                
        }     
        catch(URISyntaxException expnUriSynta)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnUriSynta.getMessage(), Star.sErrUriSynta, expnUriSynta.getStackTrace());                                
            return;                        
        }
        
        /*Coloca el foco del teclado en el campo de la dirección*/
        jTUbicGraf.grabFocus();
        
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se pierde el foco del teclado en el campo de ubicación gráfica*/
    private void jTUbicGrafFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUbicGrafFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUbicGraf.setCaretPosition(0);
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTUbicGraf.getText().length()> 350)
            jTUbicGraf.setText(jTUbicGraf.getText().substring(0, 350));
        
    }//GEN-LAST:event_jTUbicGrafFocusLost

    
    /*Cuando se hace clic en la tabla de carátulas*/
    private void jTab2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTab2MouseClicked
        
        /*Si se hiso doble clic entonces presiona el botón de abrir cotización*/
        if(evt.getClickCount() == 2) 
            jBAbrPart.doClick();
        
    }//GEN-LAST:event_jTab2MouseClicked

    
    /*Cuando se gana el foco del teclado en el campo de nom*/
    private void jTNombFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNombFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNomb.setSelectionStart(0);jTNomb.setSelectionEnd(jTNomb.getText().length());
        
    }//GEN-LAST:event_jTNombFocusGained

    
    /*Cuando se presiona una tecla en el campo de nom*/
    private void jTNombKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNombKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNombKeyPressed

    
    /*Cuando se presiona el botón de buscar coincidencia*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTEmp.getText(), 5, jTEmp, jTNomb, jTSer, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar coincidencia*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de código de cliente*/
    private void jTEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEmpFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTEmp.setSelectionStart(0);jTEmp.setSelectionEnd(jTEmp.getText().length());
        
    }//GEN-LAST:event_jTEmpFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del código de la cliente*/
    private void jTEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEmpFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTEmp.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTEmp.getText().compareTo("")!=0)
            jTEmp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
        //Abre la base de datos nuevamente
        Connection con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene todos los datos de la cliente en base a su código y si no existe activa la bandera*/
        boolean bSi   = false;
        try
        {
            sQ = "SELECT * FROM emps WHERE CONCAT_WS('', ser, codemp ) = '" + jTEmp.getText() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Pon la bandera para saber que si existe esta cliente*/
                bSi = true;                               

                /*Colocalos en los campos correspondientes*/
                jTNomb.setText      (rs.getString("nom"));
                jTCall.setText      (rs.getString("calle"));
                jTCol.setText       (rs.getString("col"));
                jTTel.setText       (rs.getString("tel"));
                jTPai.setText       (rs.getString("pai"));
                jTCP.setText        (rs.getString("cp"));
                jTRFC.setText       (rs.getString("rfc"));
                jTPag1.setText      (rs.getString("pagweb1"));
                jTPag2.setText      (rs.getString("pagweb2"));
                jTCiu.setText       (rs.getString("ciu"));
                jTNomb.setText      (rs.getString("nom"));
                jTContac.setText    (rs.getString("contac"));
                jTAObserv.setText   (rs.getString("observ"));
                jTEstad.setText     (rs.getString("estad"));
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

        /*Si el código de la cliente no existe entonces*/
        if(!bSi)
        {
            /*Resetea los campos*/            
            jTCall.setText     ("");
            jTCol.setText       ("");
            jTTel.setText       ("");
            jTPai.setText       ("");
            jTCP.setText        ("");
            jTRFC.setText       ("");
            jTNomb.setText      ("");
            jTContac.setText    ("");
            jTAObserv.setText   ("");
            jTCiu.setText       ("");
            jTEstad.setText     ("");            
        }                
        
    }//GEN-LAST:event_jTEmpFocusLost

    
    /*Cuando se presiona una tecla en el campo de código de cliente*/
    private void jTEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEmpKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTEmp.getText(), 5, jTEmp, jTNomb, jTSer, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEmpKeyPressed

    
    /*Cuando se tipea una tecla en el campo del código de la cliente*/
    private void jTEmpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEmpKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTEmpKeyTyped
    
    
    /*Cuando se pierde el foco del teclado en el campo de obr*/
    private void jTObrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTObrFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTObr.setCaretPosition(0);
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTObr.getText().length()> 255)
            jTObr.setText(jTObr.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTObrFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de tipo de obr*/
    private void jTTipObrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTipObrFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTTipObr.setCaretPosition(0);
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTTipObr.getText().length()> 100)
            jTTipObr.setText(jTTipObr.getText().substring(0, 100));
        
    }//GEN-LAST:event_jTTipObrFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de plant*/
    private void jTPlantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPlantFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPlant.setCaretPosition(0);
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTPlant.getText().length()> 255)
            jTPlant.setText(jTPlant.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTPlantFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de ubicación*/
    private void jTUbicFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUbicFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUbic.setCaretPosition(0);
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTUbic.getText().length()> 255)
            jTUbic.setText(jTUbic.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTUbicFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de tiempo de entrega*/
    private void jTEntregFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEntregFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTEntreg.setCaretPosition(0);
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTEntreg.getText().length()> 255)
            jTEntreg.setText(jTEntreg.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTEntregFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de condiciones de pago*/
    private void jTCondPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCondPagFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCondPag.setCaretPosition(0);
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTCondPag.getText().length()> 100)
            jTCondPag.setText(jTCondPag.getText().substring(0, 100));
        
    }//GEN-LAST:event_jTCondPagFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de descripción*/
    private void jTADescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTADescripFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTADescrip.setCaretPosition(0);
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTADescrip.getText().length()> 255)
            jTADescrip.setText(jTADescrip.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTADescripFocusLost
    
    
    /*Cuando se pierde el foco del teclado en el campo de otrs*/
    private void jTAOtroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAOtroFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTAOtro.setCaretPosition(0);
        
        /*Si el campo excede la cant de caes permitidos recortalo*/
        if(jTAOtro.getText().length()> 255)
            jTAOtro.setText(jTAOtro.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTAOtroFocusLost

        
    /*Cuando se presiona una tecla en el botón de ver pdf de cotización*/
    private void jBPDFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPDFKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBPDFKeyPressed

    
    /*Cuando se presiona el botón de PDF*/
    private void jBPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPDFActionPerformed

        /*Si el usuario no a seleccionado una partida no puede avanzar*/
        if(jTab2.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una cotización.", "PDF", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en la tabla y regresa*/
            jTab2.grabFocus();
            return;            
        }
        
        /*Si no esta marcado ningún checkbox para ver entonces*/
        if(!jCJPS1.isSelected() && !jCJPS2.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona JPS1, JPS2.", "PDF", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el primer checkbox*/
            jCJPS1.grabFocus();           
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

        /*Si el directorio de cots no existe entonces crealo*/                               
        String sRutPDF     = sCarp + "\\Cotizaciones";
        if(!new File(sRutPDF).exists())                  
            new File(sRutPDF).mkdir();                    

        /*Si el directorio del cliente no existe entonces crealo*/                               
        sRutPDF             = sRutPDF + "\\" + Login.sCodEmpBD;
        if(!new File(sRutPDF).exists())
            new File(sRutPDF).mkdir();

        /*Declara variables*/
        String  sCodCot;
        String  sRut        = "";
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab2.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Obtiene el código de la cotización*/            
            sCodCot         = jTab2.getValueAt(iSel[x], 1).toString();            
            
            /*Si el checkbox de ver JPS1 cotización esta marcado entonces completa la ruta al PDF*/            
            if(jCJPS1.isSelected())
                sRut         = sRutPDF + "\\" + sCodCot + ".pdf";
            
            /*Si no existe el archivo PDF entonces*/
            if(!new File(sRut).exists())
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La cotización: " + sRut + " no existe.", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                return;
            }

            /*Abre el archivo PDF*/
            try 
            {
                Desktop.getDesktop().open(new File(sRut));
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                return;                                       
            }
            
            /*Si el checkbox de ver JPS2 cotización esta marcado entonces completa la ruta*/            
            if(jCJPS2.isSelected())
                sRut         = sRutPDF + "\\" + sCodCot + "JP.pdf";
            
            /*Si no existe el archivo PDF entonces*/
            if(!new File(sRut).exists())
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La cotización: " + sRut + " no existe.", "Cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                return;
            }

            /*Abre el archivo PDF*/
            try 
            {
                Desktop.getDesktop().open(new File(sRut));
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                            
            }
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                                    
                            
    }//GEN-LAST:event_jBPDFActionPerformed

    
    /*Cuando se presiona el botón de log correos*/
    private void jBLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLogActionPerformed

        /*Muestra la forma para el log de correos*/
        LogCorrs c = new LogCorrs();
        c.setVisible(true);

    }//GEN-LAST:event_jBLogActionPerformed

    
    /*Cuando se presiona una tecla en el botón de log correos*/
    private void jBLogKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLogKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBLogKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de JPS2*/
    private void jCJPS2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCJPS2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCJPS2KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de JPS1*/
    private void jCJPS1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCJPS1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jCJPS1KeyPressed

    
    
    
    /*Cuando se presiona una tecla en el checkbox de seleccionar todo*/
    private void jCSelTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCSelTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCSelTKeyPressed

    
    /*Cuando se presiona el checkbox de seleccionar todo*/
    private void jCSelTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCSelTActionPerformed
        
        /*Si esta seleccionado entonces*/
        if(jCSelT.isSelected())
        {
            /*Selecciona todos los elementos en la tabla de faltantes*/
            jTab1.selectAll();
        }
        else
        {
            /*Deselecciona todo en la tabla volviendolos a cargar*/
            vLoadFaltT();            
        }
        
    }//GEN-LAST:event_jCSelTActionPerformed
    
    
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

    
    /*Cuando se mueve el ratón en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se presiona una tecla en el checkbox de guardar datos de la cliente*/
    private void jCGuaDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGuaDKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCGuaDKeyPressed

    
    /*Cuando se presiona el botón de ver tabla*/
    private void jBTab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab1ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab1);       

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

    
    
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBuscMouseEntered

    
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBAbrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBAbr.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAbrMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGuarSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGuarSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuarSalMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBAgreMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAgreMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBAgre.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAgreMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDel1MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel1.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDel1MouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewCotProvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewCotProvMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNewCotProv.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewCotProvMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBOrdComMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBOrdComMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBOrdCom.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBOrdComMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVerOrdsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVerOrdsMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVerOrds.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVerOrdsMouseEntered

    
    
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewCotMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewCotMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNewCot.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewCotMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBAbrPartMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrPartMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBAbrPart.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAbrPartMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCopMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCopMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCop.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCopMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBPDFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPDFMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBPDF.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBPDFMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBLogMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLogMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBLog.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBLogMouseEntered

    private void jBOrdComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOrdComActionPerformed
        /*Abre la forma de compras una sola vez*/
        if(Star.gCompr==null)
        {            
            Star.gCompr = new IngrCom(null, this, "", "", "orden");
            Star.gCompr.setVisible(true); 
        }
        else
        {            
            /*Si ya esta visible entonces traela al frente*/
            if(Star.gCompr.isVisible())            
                Star.gCompr.toFront();
            else
                Star.gCompr.setVisible(true);
        }
    }//GEN-LAST:event_jBOrdComActionPerformed

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(colOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
    
    /*Cuando el mouse sale del botón específico*/
    private void jBAbrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBAbr.setBackground(colOri);
        
    }//GEN-LAST:event_jBAbrMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuarSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuarSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBAgreMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAgreMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBAgre.setBackground(colOri);
        
    }//GEN-LAST:event_jBAgreMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDel1MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);
        
    }//GEN-LAST:event_jBDel1MouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewCotProvMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewCotProvMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNewCotProv.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewCotProvMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBOrdComMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBOrdComMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBOrdCom.setBackground(colOri);
        
    }//GEN-LAST:event_jBOrdComMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVerOrdsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVerOrdsMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVerOrds.setBackground(colOri);
        
    }//GEN-LAST:event_jBVerOrdsMouseExited

    
    
    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewCotMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewCotMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNewCot.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewCotMouseExited


    /*Cuando el mouse sale del botón específico*/
    private void jBAbrPartMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrPartMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBAbrPart.setBackground(colOri);
        
    }//GEN-LAST:event_jBAbrPartMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCopMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCopMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCop.setBackground(colOri);
        
    }//GEN-LAST:event_jBCopMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBPDFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPDFMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBPDF.setBackground(colOri);
        
    }//GEN-LAST:event_jBPDFMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBLogMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLogMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBLog.setBackground(colOri);
        
    }//GEN-LAST:event_jBLogMouseExited

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTNombFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNombFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTNomb.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNombFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTTelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTel.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTelFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCol.setCaretPosition(0);
        
    }//GEN-LAST:event_jTColFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCiuFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCiu.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCiuFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPaiFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPai.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPaiFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPag1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag1FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPag1.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPag1FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPag2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPag2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPag2.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPag2FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTContacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTContacFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTContac.setCaretPosition(0);
        
    }//GEN-LAST:event_jTContacFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCall.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCallFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCP.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCPFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTEstadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTEstad.setCaretPosition(0);
        
    }//GEN-LAST:event_jTEstadFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTRFCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRFCFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTRFC.setCaretPosition(0);
        
    }//GEN-LAST:event_jTRFCFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTAObservFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAObservFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAObserv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTAObservFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del impuesto*/
    private void jTImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTImpFocusLost
        
        /*Coloca el caret en la posición 0*/
        jTImp.setCaretPosition(0);
        
    }//GEN-LAST:event_jTImpFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del subtotal*/
    private void jTSubTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSubTotFocusLost
        
        /*Coloca el caret en la posición 0*/
        jTSubTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSubTotFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del total*/
    private void jTTotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusLost
        
        /*Coloca el caret en la posición 0*/
        jTTot.setCaretPosition(0);
        
    }//GEN-LAST:event_jTTotFocusLost

    private void jBNewCotProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewCotProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBNewCotProvActionPerformed

            
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();                   
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
//        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
//            jBTod.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de agregar cotización*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNewCot.doClick();
        /*Si se presiona F2 presiona el botón de guardar y salir*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBGuarSal.doClick();                             
        /*Si se presiona F3 presiona el botón de nueva partida*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBNewCotProv.doClick();               
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 presiona el botón de modificar órden*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBOrdCom.doClick(); 
        /*Si se presiona F5 presiona el botón de ver órdenes*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBVerOrds.doClick();             
        /*Si se presiona F6 presiona el botón de generar órden*/
//        else if(evt.getKeyCode() == KeyEvent.VK_F6)
//            jBGenOrd.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona F10 presiona el botón de borrar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F10)
            jBDel.doClick();             
        /*Si se presiona F11 presiona el botón de PDF*/
        else if(evt.getKeyCode() == KeyEvent.VK_F11)
            jBPDF.doClick();                                    
        /*Si se presiona F12 presiona el botón de viáticos*/
//        else if(evt.getKeyCode() == KeyEvent.VK_F12)
//            jBViats.doClick();                                    
        /*Si se presiona CTRL + A entonces presiona el botón de abrir*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_A)
            jBAbrPart.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbr;
    private javax.swing.JButton jBAbrPart;
    private javax.swing.JButton jBAgre;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBCop;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBDel1;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBGuarSal;
    private javax.swing.JButton jBLog;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBNewCot;
    private javax.swing.JButton jBNewCotProv;
    private javax.swing.JButton jBOrdCom;
    private javax.swing.JButton jBPDF;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBVerOrds;
    private javax.swing.JCheckBox jCGuaD;
    private javax.swing.JCheckBox jCJPS1;
    private javax.swing.JCheckBox jCJPS2;
    private javax.swing.JCheckBox jCSelT;
    private javax.swing.JComboBox jComPers;
    private com.toedter.calendar.JDateChooser jDIniObr;
    private com.toedter.calendar.JDateChooser jDTermObr;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLFec;
    private javax.swing.JLabel jLMatFalt;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jLisPers;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea jTADescrip;
    private javax.swing.JTextArea jTAObserv;
    private javax.swing.JTextArea jTAOtro;
    private javax.swing.JTextField jTCP;
    private javax.swing.JTextField jTCall;
    private javax.swing.JTextField jTCiu;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTCondPag;
    private javax.swing.JTextField jTContac;
    private javax.swing.JTextField jTEmp;
    private javax.swing.JTextField jTEntreg;
    private javax.swing.JTextField jTEstad;
    private javax.swing.JTextField jTImp;
    private javax.swing.JTextField jTNomb;
    private javax.swing.JTextField jTObr;
    private javax.swing.JTextField jTPag1;
    private javax.swing.JTextField jTPag2;
    private javax.swing.JTextField jTPai;
    private javax.swing.JTextField jTPlant;
    private javax.swing.JTextField jTProy;
    private javax.swing.JTextField jTRFC;
    private javax.swing.JTextField jTSer;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTTel;
    private javax.swing.JTextField jTTipObr;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTextField jTUbic;
    private javax.swing.JTextField jTUbicGraf;
    private javax.swing.JTable jTab1;
    private javax.swing.JTable jTab2;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevoCliente extends javax.swing.JFrame */
