//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import wscance.CancelacionesRecuperarAcusesFallaServicioFaultFaultMessage;
import wscance.CancelacionesRecuperarAcusesFallaSesionFaultFaultMessage;
import wscance.CancelacionesRecuperarAcusesFallaValidacionFaultFaultMessage;
import wstimb.TimbradoEstatusTimbradoFallaServicioFaultFaultMessage;
import wstimb.TimbradoEstatusTimbradoFallaSesionFaultFaultMessage;
import wstimb.TimbradoEstatusTimbradoFallaValidacionFaultFaultMessage;
import wstimb.TimbradoObtenerTimbradoFallaServicioFaultFaultMessage;
import wstimb.TimbradoObtenerTimbradoFallaSesionFaultFaultMessage;
import wstimb.TimbradoObtenerTimbradoFallaValidacionFaultFaultMessage;




/*Clase que controla las vtas*/
public class Vtas extends javax.swing.JFrame 
{
    /*Bandera para saber si la tecla de control esta presionada o no*/
    private boolean         bAltP              = false;
        
    /*Contador para modificar tabla*/
    private int             iContCellEd;
    
    /*Variable que contiene el borde actual*/
    private Border          bBordOri;

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;
    
    /*Declara variables originales de la tabla 1*/
    private String          sVtaOri;
    private String          sFolOri;
    private String          sNoSerOri;
    private String          sEmpOri;
    private String          sImpoOri;        
    private String          sTotDescOri;        
    private String          sTotCostOri;
    private String          sFDocOri;
    private String          sUltModOri;
    private String          sVenciOri;
    private String          sNotCredOri;
    private String          sNotCredPOri;
    private String          sEstadOri;
    private String          sEstacOri;    
    private String          sMotivOri;
    private String          sTipOri;
    private String          sObservOri;
    private String          sSucOri;
    private String          sCajOri;    
    private String          sNomEstacOri;
    private String          sTimOri;
    private String          sCodCotOri;
    private String          sVtaDevOri;
    private String          sFolFiscOri;
    
    /*Declara variables originales del automóvil*/
    private String          sAutRecibOri;
    private String          sAutMarcOri;
    private String          sAutModOri;
    private String          sAutColoOri;
    private String          sAutPlacOri;
    private String          sAutNomOri;
    private String          sAutNoCircOri;
    private String          sAutNoLicOri;
    private String          sAutTelOri;
    private String          sAutDirPartOri;
    private String          sAutDirOficOri;
    private String          sAutTelOfiOri;    
    
    /*Declara variables originales de la tabla 2*/
    private String          sCantOri;
    private String          sCantEntreOri;
    private String          sProdOri;    
    private String          sDescripOri;
    private String          sDevsOri;
    private String          sKitOri;
    private String          sUnidOri;
    private String          sAlmaOri;
    private String          sMonOri;
    private String          sPreOri;
    private String          sDescOri;    
    private String          sImpueOri;
    private String          sFechOri;
    private String          sTallOri;
    private String          sColoOri;
    private String          sLotOri;
    private String          sPedimenOri;
    private String          sCaduOri;
    private String          sBackOri;
    private String          sEntreOri;
    
    //Thread para mostrar las partidas de la venta con retardo
    private Thread          thCargPart;         
    
    
    
    
    /*Constructor sin argumentos*/
        public Vtas() 
    {                                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        //se saca acomoda los componentes dependiendo de la resolucion
        vMyLayout();
        
        /*Esconde la columna del id*/
        jTab1.getColumnModel().getColumn(25).setMinWidth(0);
        jTab1.getColumnModel().getColumn(25).setMaxWidth(0);
        
        /*Para que no se muevan las columnas*/
        jTab1.getTableHeader().setReorderingAllowed(false);
        jTab2.getTableHeader().setReorderingAllowed(false);
        
        /*Que no se redimensione la tabla 1*/
        jTab1.getTableHeader().setReorderingAllowed(false);
        
        /*Que no se redimensione la tabla 2*/
        jTab2.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la vtana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Ventas, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Inicia el contador de filas en 1 inicialmente*/
        Star.iContFiVent      = 1;
        
        /*Para que la tabla de encabezados tengan scroll horisontal*/
        jTab1.setAutoResizeMode(0);
        
        /*Para que la tabla de partidas tengan scroll horisontal*/
        jTab2.setAutoResizeMode(0);
        
        /*Carga todas la series de las facturas en el control*/
        Star.vCargSer(jComSer, "FAC");                               
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab1.getModel());
        jTab1.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el botón de ingresar nueva factura*/
        jBNew.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de ventas*/
        jTab1.getColumnModel().getColumn(0).setPreferredWidth(37);
        jTab1.getColumnModel().getColumn(1).setPreferredWidth(48);
        jTab1.getColumnModel().getColumn(2).setPreferredWidth(48);
        jTab1.getColumnModel().getColumn(4).setPreferredWidth(130);
        jTab1.getColumnModel().getColumn(6).setPreferredWidth(130);
        jTab1.getColumnModel().getColumn(7).setPreferredWidth(130);
        jTab1.getColumnModel().getColumn(8).setPreferredWidth(130);
        jTab1.getColumnModel().getColumn(9).setPreferredWidth(130);
        jTab1.getColumnModel().getColumn(10).setPreferredWidth(130);
        jTab1.getColumnModel().getColumn(11).setPreferredWidth(130);
        jTab1.getColumnModel().getColumn(12).setPreferredWidth(130);
        jTab1.getColumnModel().getColumn(13).setPreferredWidth(150);
        jTab1.getColumnModel().getColumn(16).setPreferredWidth(130);
        jTab1.getColumnModel().getColumn(17).setPreferredWidth(220);
        jTab1.getColumnModel().getColumn(18).setPreferredWidth(200);
        jTab1.getColumnModel().getColumn(20).setPreferredWidth(160);        
        jTab1.getColumnModel().getColumn(21).setPreferredWidth(160);
        jTab1.getColumnModel().getColumn(23).setPreferredWidth(160);        
        jTab1.getColumnModel().getColumn(24).setPreferredWidth(145);
        jTab1.getColumnModel().getColumn(25).setPreferredWidth(145);        
        jTab1.getColumnModel().getColumn(26).setPreferredWidth(280);
                
        /*Establece el tamaño de las columnas de la tabla de partidas de la venta*/
        jTab2.getColumnModel().getColumn(5).setPreferredWidth(400);
        jTab2.getColumnModel().getColumn(15).setPreferredWidth(150);
        jTab2.getColumnModel().getColumn(20).setPreferredWidth(150);
        jTab2.getColumnModel().getColumn(21).setPreferredWidth(150);
        jTab2.getColumnModel().getColumn(22).setPreferredWidth(150);
        jTab2.getColumnModel().getColumn(23).setPreferredWidth(150);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab1.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab1.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                                                                    /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Crea el listener para cuando se cambia de selección en la tabla de ventas*/
        jTab1.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {             
                //Si no hay selección en la tabla entonces regresa
                if(jTab1.getSelectedRow()==-1)
                    return;
                
                //Detiene el thread para que no cargue las partidas
                if(thCargPart!=null)
                    thCargPart.interrupt();
                
                //Crea el thread retrasado para la forma
                thCargPart = new Thread()
                {
                    @Override
                    public void run()
                    {
                        //Duerme el thread 
                        try
                        {
                            Thread.sleep(200);
                        }
                        catch(InterruptedException expnInterru)
                        {
                            return;
                        }                            
                        
                        /*Carga todas las partidas de la venta*/
                        vLoadParts();               
                    }
                };      
                thCargPart.start();                                                                                                                    
            }
        });

        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para cuando se modifica algo en la tabla 1*/
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
                        sVtaOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString();
                        sFolOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString();
                        sNoSerOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 3).toString();
                        sEmpOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 4).toString();
                        sImpoOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 5).toString();
                        sTotDescOri    = jTab1.getValueAt(jTab1.getSelectedRow(), 6).toString();
                        sTotCostOri    = jTab1.getValueAt(jTab1.getSelectedRow(), 7).toString();
                        sFechOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 8).toString();
                        sFDocOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 9).toString();
                        sUltModOri     = jTab1.getValueAt(jTab1.getSelectedRow(), 10).toString();
                        sVenciOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 11).toString();
                        sNotCredOri    = jTab1.getValueAt(jTab1.getSelectedRow(), 12).toString();
                        sNotCredPOri   = jTab1.getValueAt(jTab1.getSelectedRow(), 13).toString();
                        sEstadOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 14).toString();
                        sEstacOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 15).toString();
                        sMotivOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 16).toString();
                        sTipOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 17).toString();
                        sObservOri     = jTab1.getValueAt(jTab1.getSelectedRow(), 18).toString();
                        sSucOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 19).toString();
                        sCajOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 20).toString();
                        sNomEstacOri   = jTab1.getValueAt(jTab1.getSelectedRow(), 21).toString();
                        sTimOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 22).toString();
                        sCodCotOri     = jTab1.getValueAt(jTab1.getSelectedRow(), 23).toString();
                        sVtaDevOri     = jTab1.getValueAt(jTab1.getSelectedRow(), 25).toString();
                        sFolFiscOri    = jTab1.getValueAt(jTab1.getSelectedRow(), 26).toString();                       
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab1.setValueAt(sVtaOri,           jTab1.getSelectedRow(), 1);                        
                        jTab1.setValueAt(sFolOri,           jTab1.getSelectedRow(), 2);                        
                        jTab1.setValueAt(sNoSerOri,         jTab1.getSelectedRow(), 3);                        
                        jTab1.setValueAt(sEmpOri,           jTab1.getSelectedRow(), 4);                        
                        jTab1.setValueAt(sImpoOri,          jTab1.getSelectedRow(), 5);                        
                        jTab1.setValueAt(sTotDescOri,       jTab1.getSelectedRow(), 6);
                        jTab1.setValueAt(sTotCostOri,       jTab1.getSelectedRow(), 7);
                        jTab1.setValueAt(sFechOri,          jTab1.getSelectedRow(), 8);                        
                        jTab1.setValueAt(sFDocOri,          jTab1.getSelectedRow(), 9);                        
                        jTab1.setValueAt(sUltModOri,        jTab1.getSelectedRow(), 10);                        
                        jTab1.setValueAt(sVenciOri,         jTab1.getSelectedRow(), 11);
                        jTab1.setValueAt(sNotCredOri,       jTab1.getSelectedRow(), 12);
                        jTab1.setValueAt(sNotCredPOri,      jTab1.getSelectedRow(), 13);
                        jTab1.setValueAt(sEstadOri,         jTab1.getSelectedRow(), 14);                        
                        jTab1.setValueAt(sEstacOri,         jTab1.getSelectedRow(), 15);                        
                        jTab1.setValueAt(sMotivOri,         jTab1.getSelectedRow(), 16);                        
                        jTab1.setValueAt(sTipOri,           jTab1.getSelectedRow(), 17);                        
                        jTab1.setValueAt(sObservOri,        jTab1.getSelectedRow(), 18);                        
                        jTab1.setValueAt(sSucOri,           jTab1.getSelectedRow(), 19);                        
                        jTab1.setValueAt(sCajOri,           jTab1.getSelectedRow(), 20);                        
                        jTab1.setValueAt(sNomEstacOri,      jTab1.getSelectedRow(), 21);
                        jTab1.setValueAt(sTimOri,           jTab1.getSelectedRow(), 22);
                        jTab1.setValueAt(sCodCotOri,        jTab1.getSelectedRow(), 23);
                        jTab1.setValueAt(sVtaDevOri,        jTab1.getSelectedRow(), 25);
                        jTab1.setValueAt(sFolFiscOri,       jTab1.getSelectedRow(), 26);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla 2*/
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
                        sVtaOri         = jTab2.getValueAt(jTab2.getSelectedRow(), 1).toString();
                        sCantOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 2).toString();
                        sCantEntreOri   = jTab2.getValueAt(jTab2.getSelectedRow(), 3).toString();
                        sProdOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 4).toString();
                        sDescripOri     = jTab2.getValueAt(jTab2.getSelectedRow(), 5).toString();
                        sDevsOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 6).toString();
                        sKitOri         = jTab2.getValueAt(jTab2.getSelectedRow(), 7).toString();
                        sUnidOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 8).toString();
                        sAlmaOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 9).toString();
                        sMonOri         = jTab2.getValueAt(jTab2.getSelectedRow(), 10).toString();
                        sPreOri         = jTab2.getValueAt(jTab2.getSelectedRow(), 11).toString();
                        sDescOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 12).toString();
                        sImpoOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 13).toString();
                        sImpueOri       = jTab2.getValueAt(jTab2.getSelectedRow(), 14).toString();
                        sFechOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 15).toString();
                        sTallOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 16).toString();
                        sColoOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 17).toString();
                        sLotOri         = jTab2.getValueAt(jTab2.getSelectedRow(), 18).toString();
                        sPedimenOri     = jTab2.getValueAt(jTab2.getSelectedRow(), 19).toString();
                        sCaduOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 20).toString();
                        sBackOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 21).toString();
                        sEntreOri       = jTab2.getValueAt(jTab2.getSelectedRow(), 22).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab2.setValueAt(sVtaOri,           jTab2.getSelectedRow(), 1);                        
                        jTab2.setValueAt(sCantOri,          jTab2.getSelectedRow(), 2);
                        jTab2.setValueAt(sCantEntreOri,     jTab2.getSelectedRow(), 3);
                        jTab2.setValueAt(sProdOri,          jTab2.getSelectedRow(), 4);                        
                        jTab2.setValueAt(sDescripOri,       jTab2.getSelectedRow(), 5);                        
                        jTab2.setValueAt(sDevsOri,          jTab2.getSelectedRow(), 6);                        
                        jTab2.setValueAt(sKitOri,           jTab2.getSelectedRow(), 7);                        
                        jTab2.setValueAt(sUnidOri,          jTab2.getSelectedRow(), 8);                        
                        jTab2.setValueAt(sAlmaOri,          jTab2.getSelectedRow(), 9);                        
                        jTab2.setValueAt(sMonOri,           jTab2.getSelectedRow(), 10);                        
                        jTab2.setValueAt(sPreOri,           jTab2.getSelectedRow(), 11);                        
                        jTab2.setValueAt(sDescOri,          jTab2.getSelectedRow(), 12);                        
                        jTab2.setValueAt(sImpoOri,          jTab2.getSelectedRow(), 13);                        
                        jTab2.setValueAt(sImpueOri,         jTab2.getSelectedRow(), 14);                        
                        jTab2.setValueAt(sFechOri,          jTab2.getSelectedRow(), 15);                        
                        jTab2.setValueAt(sTallOri,          jTab2.getSelectedRow(), 16);                        
                        jTab2.setValueAt(sColoOri,          jTab2.getSelectedRow(), 17);                        
                        jTab2.setValueAt(sLotOri,           jTab2.getSelectedRow(), 18);                        
                        jTab2.setValueAt(sPedimenOri,       jTab2.getSelectedRow(), 19);                        
                        jTab2.setValueAt(sCaduOri,          jTab2.getSelectedRow(), 20);                        
                        jTab2.setValueAt(sBackOri,          jTab2.getSelectedRow(), 21);                        
                        jTab2.setValueAt(sEntreOri,         jTab2.getSelectedRow(), 22);                        
                       
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla 2*/
        jTab2.addPropertyChangeListener(pro);
        
        /*Función para cargar todas las ventas en la tabla de enzabezados*/
        (new Thread()
        {
            @Override
            public void run()
            {
                Star.vCargVtas("", jLNot, jTab1, jLVtasTot);
            }
        }).start();
        
    }/*Fin de public Vents() */
    
    private void vMyLayout()
    {
        //Se calcula la resolucion
         int iW= java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
         //se determina el valor maximo de resolucion
         if(iW>1450)
             iW=1450;
         
         //Se calcula el tamaño que tendra la ventana con un rango mas pequeño
         iW=iW-(int)(iW*.1);
         this.setSize(iW, 540);
         
         //tamaño del panel para los margenes
         jP1.setSize(iW-27, 490);
        
               
        //se coloca el panel de los botones
        jPanel1.setLocation(iW-jPanel1.getSize().width-30, 10);
        
        //se le da el tamaño adecuado a los otros dos paneles que contiene las tablas
        jPanel2.setSize(iW-jPanel1.getSize().width-50,201);
        jPanel3.setSize(iW-jPanel1.getSize().width-50,180);
        
        //localizacion del enlace
        jLAyu.setLocation(jPanel3.getLocation().x+jPanel3.getSize().width-jLAyu.getSize().width,jPanel3.getLocation().y+jPanel3.getSize().height+1);

        //se localiza el boton de mostrar todo y el de seleccionar todo
        jBTod.setLocation(iW-jPanel1.getSize().width-31-jBTod.getSize().width, 10);
        jBMosT.setLocation(iW-jPanel1.getSize().width-31-jBMosT.getSize().width,jBMosT.getLocation().y);
        
        //se le da el tamaño a la barra de busqueda
        jTBusc.setSize(jPanel2.getSize().width-jBMosT.getSize().width-jBBusc.getSize().width-1-jBAyu.getSize().width,jTBusc.getSize().height);
        jBAyu.setLocation(jTBusc.getLocation().x+jTBusc.getSize().width,jTBusc.getLocation().y);
        
        //Acomodar los mensajes de error
        jLNotCli.setLocation(jPanel2.getLocation().x,jLNotCli.getLocation().y);
        jLNotCli.setSize(jPanel2.getSize().width,jLNot.getSize().height);
        
        
    }//fin de private void vMyLayout()
        
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jSTab1 = new javax.swing.JScrollPane();
        jTab1 = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMosT = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jBTab1 = new javax.swing.JButton();
        jBTab2 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jLNot = new javax.swing.JLabel();
        jLNotCli = new javax.swing.JLabel();
        jLTimb = new javax.swing.JLabel();
        jBAyu = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jBGenPDF = new javax.swing.JButton();
        jBCa = new javax.swing.JButton();
        jBRem = new javax.swing.JButton();
        jBDirX = new javax.swing.JButton();
        jBDev = new javax.swing.JButton();
        jBDirZ = new javax.swing.JButton();
        jBDevP = new javax.swing.JButton();
        jBDirNot = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jBDirCFDI = new javax.swing.JButton();
        jBNotC = new javax.swing.JButton();
        jBDirTick = new javax.swing.JButton();
        jBVer = new javax.swing.JButton();
        jBDirCan = new javax.swing.JButton();
        jBMail = new javax.swing.JButton();
        jBDirBack = new javax.swing.JButton();
        jBPDF = new javax.swing.JButton();
        jBDirDev = new javax.swing.JButton();
        jBActua = new javax.swing.JButton();
        jBDirDevP = new javax.swing.JButton();
        jBTim = new javax.swing.JButton();
        jBDirAcus = new javax.swing.JButton();
        jBEntre = new javax.swing.JButton();
        jComSer = new javax.swing.JComboBox();
        jBFac = new javax.swing.JButton();
        jBCompro = new javax.swing.JButton();
        jBAcus = new javax.swing.JButton();
        jBXML = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jBVerArch = new javax.swing.JButton();
        jBCarg = new javax.swing.JButton();
        jBInfo = new javax.swing.JButton();
        jBCli = new javax.swing.JButton();
        jTCli = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab2 = new javax.swing.JTable();
        jRadBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLVtasTot = new javax.swing.JLabel();

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
        getContentPane().setLayout(null);

        jP1.setBackground(new java.awt.Color(255, 255, 255));
        jP1.setMinimumSize(new java.awt.Dimension(100, 100));
        jP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jP1KeyPressed(evt);
            }
        });
        jP1.setLayout(null);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jTab1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Venta", "Folio", "Serie", "Cliente", "Total", "Total Descuento", "Total Costo", "Fecha Creación", "Fecha Documento", "Última Modificación", "Vencimiento", "Asignada N.C.", "Pago N.C.", "Estado", "Usuario", "Motivo", "Tipo Documento", "Observaciones", "Sucursal", "No. Caja", "Nombre Usuario", "Timbrada", "Cod.Cotización", "Venta Devolución", "id", "Fol.Fiscal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab1.setGridColor(new java.awt.Color(255, 255, 255));
        jTab1.setNextFocusableComponent(jBBusc);
        jTab1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTab1MouseClicked(evt);
            }
        });
        jTab1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTab1KeyPressed(evt);
            }
        });
        jSTab1.setViewportView(jTab1);

        jPanel2.add(jSTab1);

        jP1.add(jPanel2);
        jPanel2.setBounds(20, 30, 780, 200);

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
        jP1.add(jBBusc);
        jBBusc.setBounds(20, 230, 140, 19);

        jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jP1.add(jTBusc);
        jTBusc.setBounds(160, 230, 490, 20);

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar nuevamente todos los Registros");
        jBMosT.setNextFocusableComponent(jTab2);
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
        jP1.add(jBMosT);
        jBMosT.setBounds(660, 230, 140, 19);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Ventas:");
        jP1.add(jLabel2);
        jLabel2.setBounds(20, 10, 70, 17);

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
        jP1.add(jBTab1);
        jBTab1.setBounds(10, 30, 10, 20);

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
        jP1.add(jBTab2);
        jBTab2.setBounds(10, 290, 10, 20);

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
        jP1.add(jLAyu);
        jLAyu.setBounds(650, 500, 150, 20);

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
        jP1.add(jBTod);
        jBTod.setBounds(670, 12, 130, 18);

        jLNot.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLNot.setForeground(new java.awt.Color(204, 0, 0));
        jLNot.setFocusable(false);
        jP1.add(jLNot);
        jLNot.setBounds(180, 10, 260, 20);

        jLNotCli.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLNotCli.setForeground(new java.awt.Color(204, 0, 0));
        jLNotCli.setFocusable(false);
        jP1.add(jLNotCli);
        jLNotCli.setBounds(40, 250, 760, 20);
        jP1.add(jLTimb);
        jLTimb.setBounds(90, 600, 80, 20);

        jBAyu.setBackground(new java.awt.Color(0, 153, 153));
        jBAyu.setToolTipText("Ayuda de búsqueda avanzada");
        jBAyu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAyuActionPerformed(evt);
            }
        });
        jBAyu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAyuKeyPressed(evt);
            }
        });
        jP1.add(jBAyu);
        jBAyu.setBounds(650, 230, 10, 20);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBGenPDF.setBackground(new java.awt.Color(255, 255, 255));
        jBGenPDF.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGenPDF.setForeground(new java.awt.Color(0, 102, 0));
        jBGenPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/genpdf.png"))); // NOI18N
        jBGenPDF.setText("Generar PDF");
        jBGenPDF.setToolTipText("Generar PDF de venta");
        jBGenPDF.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBGenPDF.setNextFocusableComponent(jBCa);
        jBGenPDF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGenPDFMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGenPDFMouseExited(evt);
            }
        });
        jBGenPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGenPDFActionPerformed(evt);
            }
        });
        jBGenPDF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGenPDFKeyPressed(evt);
            }
        });
        jPanel1.add(jBGenPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 30));

        jBCa.setBackground(new java.awt.Color(255, 255, 255));
        jBCa.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCa.setForeground(new java.awt.Color(0, 102, 0));
        jBCa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/can.png"))); // NOI18N
        jBCa.setText("Cancelar");
        jBCa.setToolTipText("Cancelar venta(s) (Ctrl+SUPR)");
        jBCa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBCa.setNextFocusableComponent(jBDev);
        jBCa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCaMouseExited(evt);
            }
        });
        jBCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCaActionPerformed(evt);
            }
        });
        jBCa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCaKeyPressed(evt);
            }
        });
        jPanel1.add(jBCa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 140, 30));

        jBRem.setBackground(new java.awt.Color(255, 255, 255));
        jBRem.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBRem.setForeground(new java.awt.Color(0, 102, 0));
        jBRem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dirrem.png"))); // NOI18N
        jBRem.setText("Remisión");
        jBRem.setToolTipText("Abrir directorio de remisiones");
        jBRem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBRem.setNextFocusableComponent(jBDirX);
        jBRem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBRemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBRemMouseExited(evt);
            }
        });
        jBRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemActionPerformed(evt);
            }
        });
        jBRem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBRemKeyPressed(evt);
            }
        });
        jPanel1.add(jBRem, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 130, -1));

        jBDirX.setBackground(new java.awt.Color(255, 255, 255));
        jBDirX.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDirX.setForeground(new java.awt.Color(0, 102, 0));
        jBDirX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dircortx.png"))); // NOI18N
        jBDirX.setText("Corte X");
        jBDirX.setToolTipText("Abrir directorio de cortes X");
        jBDirX.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDirX.setNextFocusableComponent(jBDirZ);
        jBDirX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDirXMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDirXMouseExited(evt);
            }
        });
        jBDirX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDirXActionPerformed(evt);
            }
        });
        jBDirX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDirXKeyPressed(evt);
            }
        });
        jPanel1.add(jBDirX, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 130, 30));

        jBDev.setBackground(new java.awt.Color(255, 255, 255));
        jBDev.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDev.setForeground(new java.awt.Color(0, 102, 0));
        jBDev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/devs.png"))); // NOI18N
        jBDev.setText("Devolución");
        jBDev.setToolTipText("Devolución completa de venta(s) (F2)");
        jBDev.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDev.setNextFocusableComponent(jBDevP);
        jBDev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDevMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDevMouseExited(evt);
            }
        });
        jBDev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDevActionPerformed(evt);
            }
        });
        jBDev.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDevKeyPressed(evt);
            }
        });
        jPanel1.add(jBDev, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 140, -1));

        jBDirZ.setBackground(new java.awt.Color(255, 255, 255));
        jBDirZ.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDirZ.setForeground(new java.awt.Color(0, 102, 0));
        jBDirZ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dircortz.png"))); // NOI18N
        jBDirZ.setText("Corte Z");
        jBDirZ.setToolTipText("Abrir directorio de cortes Z");
        jBDirZ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDirZ.setNextFocusableComponent(jBDirNot);
        jBDirZ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDirZMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDirZMouseExited(evt);
            }
        });
        jBDirZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDirZActionPerformed(evt);
            }
        });
        jBDirZ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDirZKeyPressed(evt);
            }
        });
        jPanel1.add(jBDirZ, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 130, 30));

        jBDevP.setBackground(new java.awt.Color(255, 255, 255));
        jBDevP.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDevP.setForeground(new java.awt.Color(0, 102, 0));
        jBDevP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/devpar.png"))); // NOI18N
        jBDevP.setText("Parcial");
        jBDevP.setToolTipText("Devolución paracial de venta(s) (F3)");
        jBDevP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDevP.setNextFocusableComponent(jBNew);
        jBDevP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDevPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDevPMouseExited(evt);
            }
        });
        jBDevP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDevPActionPerformed(evt);
            }
        });
        jBDevP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDevPKeyPressed(evt);
            }
        });
        jPanel1.add(jBDevP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 140, 30));

        jBDirNot.setBackground(new java.awt.Color(255, 255, 255));
        jBDirNot.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDirNot.setForeground(new java.awt.Color(0, 102, 0));
        jBDirNot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dir.png"))); // NOI18N
        jBDirNot.setText("N.Crédito");
        jBDirNot.setToolTipText("Directorio de las Notas de crédito");
        jBDirNot.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDirNot.setNextFocusableComponent(jBDirCFDI);
        jBDirNot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDirNotMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDirNotMouseExited(evt);
            }
        });
        jBDirNot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDirNotActionPerformed(evt);
            }
        });
        jBDirNot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDirNotKeyPressed(evt);
            }
        });
        jPanel1.add(jBDirNot, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 130, 30));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre8.png"))); // NOI18N
        jBNew.setText("Nueva");
        jBNew.setToolTipText("Nueva factura o Remisión (Ctrl+N). Presionando (Alt y este Botón) Puedes Tomar la Venta Seleccionada como Machote para una Nueva Factura o remisión");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jBNotC);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jBNewKeyReleased(evt);
            }
        });
        jPanel1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 140, 30));

        jBDirCFDI.setBackground(new java.awt.Color(255, 255, 255));
        jBDirCFDI.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDirCFDI.setForeground(new java.awt.Color(0, 102, 0));
        jBDirCFDI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dircfd.png"))); // NOI18N
        jBDirCFDI.setText(" CFDI");
        jBDirCFDI.setToolTipText("Directorio de PDF CFDI");
        jBDirCFDI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDirCFDI.setName(""); // NOI18N
        jBDirCFDI.setNextFocusableComponent(jBDirTick);
        jBDirCFDI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDirCFDIMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDirCFDIMouseExited(evt);
            }
        });
        jBDirCFDI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDirCFDIActionPerformed(evt);
            }
        });
        jBDirCFDI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDirCFDIKeyPressed(evt);
            }
        });
        jPanel1.add(jBDirCFDI, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 130, 30));

        jBNotC.setBackground(new java.awt.Color(255, 255, 255));
        jBNotC.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNotC.setForeground(new java.awt.Color(0, 102, 0));
        jBNotC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/notcred.png"))); // NOI18N
        jBNotC.setText("Not.crédito");
        jBNotC.setToolTipText("Generar una nueva nota de crédito. Presionando (Alt y este botón) puedes tomar la nota de crédito seleccionada como machote para una nueva nota de crédito");
        jBNotC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNotC.setNextFocusableComponent(jBVer);
        jBNotC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBNotCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBNotCMouseExited(evt);
            }
        });
        jBNotC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNotCActionPerformed(evt);
            }
        });
        jBNotC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBNotCKeyPressed(evt);
            }
        });
        jPanel1.add(jBNotC, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 140, 30));

        jBDirTick.setBackground(new java.awt.Color(255, 255, 255));
        jBDirTick.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDirTick.setForeground(new java.awt.Color(0, 102, 0));
        jBDirTick.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dirpdftic.png"))); // NOI18N
        jBDirTick.setText("Tickets");
        jBDirTick.setToolTipText("Directorio de PDF tickets");
        jBDirTick.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDirTick.setNextFocusableComponent(jBDirCan);
        jBDirTick.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDirTickMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDirTickMouseExited(evt);
            }
        });
        jBDirTick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDirTickActionPerformed(evt);
            }
        });
        jBDirTick.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDirTickKeyPressed(evt);
            }
        });
        jPanel1.add(jBDirTick, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 130, 30));

        jBVer.setBackground(new java.awt.Color(255, 255, 255));
        jBVer.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVer.setForeground(new java.awt.Color(0, 102, 0));
        jBVer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ver.png"))); // NOI18N
        jBVer.setText("Ver");
        jBVer.setToolTipText("Ver venta(s) en otra vista");
        jBVer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVer.setNextFocusableComponent(jBMail);
        jBVer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVerMouseExited(evt);
            }
        });
        jBVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVerActionPerformed(evt);
            }
        });
        jBVer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVerKeyPressed(evt);
            }
        });
        jPanel1.add(jBVer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 140, 30));

        jBDirCan.setBackground(new java.awt.Color(255, 255, 255));
        jBDirCan.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDirCan.setForeground(new java.awt.Color(0, 102, 0));
        jBDirCan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dirvtacan.png"))); // NOI18N
        jBDirCan.setText("Cancelados");
        jBDirCan.setToolTipText("Directorio de ventas canceladas");
        jBDirCan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDirCan.setNextFocusableComponent(jBDirBack);
        jBDirCan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDirCanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDirCanMouseExited(evt);
            }
        });
        jBDirCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDirCanActionPerformed(evt);
            }
        });
        jBDirCan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDirCanKeyPressed(evt);
            }
        });
        jPanel1.add(jBDirCan, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 130, 30));

        jBMail.setBackground(new java.awt.Color(255, 255, 255));
        jBMail.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMail.setForeground(new java.awt.Color(0, 102, 0));
        jBMail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/corrs.png"))); // NOI18N
        jBMail.setText("Enviar F8");
        jBMail.setToolTipText("Reenviar factura(s)");
        jBMail.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBMail.setNextFocusableComponent(jBPDF);
        jBMail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMailMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMailMouseExited(evt);
            }
        });
        jBMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMailActionPerformed(evt);
            }
        });
        jBMail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMailKeyPressed(evt);
            }
        });
        jPanel1.add(jBMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 140, 30));

        jBDirBack.setBackground(new java.awt.Color(255, 255, 255));
        jBDirBack.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDirBack.setForeground(new java.awt.Color(0, 102, 0));
        jBDirBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/direntback.png"))); // NOI18N
        jBDirBack.setText("Backorders");
        jBDirBack.setToolTipText("Direcotio de entregas de backorders");
        jBDirBack.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDirBack.setNextFocusableComponent(jBDirDev);
        jBDirBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDirBackMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDirBackMouseExited(evt);
            }
        });
        jBDirBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDirBackActionPerformed(evt);
            }
        });
        jBDirBack.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDirBackKeyPressed(evt);
            }
        });
        jPanel1.add(jBDirBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 130, 30));

        jBPDF.setBackground(new java.awt.Color(255, 255, 255));
        jBPDF.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBPDF.setForeground(new java.awt.Color(0, 102, 0));
        jBPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/pdf.png"))); // NOI18N
        jBPDF.setText("Ver PDF");
        jBPDF.setToolTipText("Ver PDF (Alt+F)");
        jBPDF.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBPDF.setNextFocusableComponent(jBActua);
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
        jPanel1.add(jBPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 140, 30));

        jBDirDev.setBackground(new java.awt.Color(255, 255, 255));
        jBDirDev.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDirDev.setForeground(new java.awt.Color(0, 102, 0));
        jBDirDev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dirdevp.png"))); // NOI18N
        jBDirDev.setText("Devoluciones");
        jBDirDev.setToolTipText("Direcotio de devoluciones");
        jBDirDev.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDirDev.setNextFocusableComponent(jBDirDevP);
        jBDirDev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDirDevMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDirDevMouseExited(evt);
            }
        });
        jBDirDev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDirDevActionPerformed(evt);
            }
        });
        jBDirDev.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDirDevKeyPressed(evt);
            }
        });
        jPanel1.add(jBDirDev, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 130, 30));

        jBActua.setBackground(new java.awt.Color(255, 255, 255));
        jBActua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBActua.setForeground(new java.awt.Color(0, 102, 0));
        jBActua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actualizar.png"))); // NOI18N
        jBActua.setText("Actualizar");
        jBActua.setToolTipText("Actualizar registros (F5)");
        jBActua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBActua.setNextFocusableComponent(jBTim);
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
        jPanel1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 140, 30));

        jBDirDevP.setBackground(new java.awt.Color(255, 255, 255));
        jBDirDevP.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDirDevP.setForeground(new java.awt.Color(0, 102, 0));
        jBDirDevP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dirdevp.png"))); // NOI18N
        jBDirDevP.setText("Dev.Parcial");
        jBDirDevP.setToolTipText("Direcotio de devoluciones parciales");
        jBDirDevP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDirDevP.setNextFocusableComponent(jBDirAcus);
        jBDirDevP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDirDevPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDirDevPMouseExited(evt);
            }
        });
        jBDirDevP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDirDevPActionPerformed(evt);
            }
        });
        jBDirDevP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDirDevPKeyPressed(evt);
            }
        });
        jPanel1.add(jBDirDevP, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 130, 30));

        jBTim.setBackground(new java.awt.Color(255, 255, 255));
        jBTim.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBTim.setForeground(new java.awt.Color(0, 102, 0));
        jBTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/cfdtim.png"))); // NOI18N
        jBTim.setText("Timbrar");
        jBTim.setToolTipText("Timbrar factura(s)");
        jBTim.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBTim.setName(""); // NOI18N
        jBTim.setNextFocusableComponent(jBEntre);
        jBTim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBTimMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBTimMouseExited(evt);
            }
        });
        jBTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTimActionPerformed(evt);
            }
        });
        jBTim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTimKeyPressed(evt);
            }
        });
        jPanel1.add(jBTim, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 140, 30));

        jBDirAcus.setBackground(new java.awt.Color(255, 255, 255));
        jBDirAcus.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDirAcus.setForeground(new java.awt.Color(0, 102, 0));
        jBDirAcus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dir.png"))); // NOI18N
        jBDirAcus.setText("Acuses");
        jBDirAcus.setToolTipText("Directorio de acuses");
        jBDirAcus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDirAcus.setNextFocusableComponent(jComSer);
        jBDirAcus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDirAcusMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDirAcusMouseExited(evt);
            }
        });
        jBDirAcus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDirAcusActionPerformed(evt);
            }
        });
        jBDirAcus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDirAcusKeyPressed(evt);
            }
        });
        jPanel1.add(jBDirAcus, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 130, 30));

        jBEntre.setBackground(new java.awt.Color(255, 255, 255));
        jBEntre.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBEntre.setForeground(new java.awt.Color(0, 102, 0));
        jBEntre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/entmerc.png"))); // NOI18N
        jBEntre.setText("Entregar");
        jBEntre.setToolTipText("Entregar backorders de la venta");
        jBEntre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBEntre.setNextFocusableComponent(jBCompro);
        jBEntre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBEntreMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBEntreMouseExited(evt);
            }
        });
        jBEntre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEntreActionPerformed(evt);
            }
        });
        jBEntre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBEntreKeyPressed(evt);
            }
        });
        jPanel1.add(jBEntre, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 140, 30));

        jComSer.setToolTipText("Serie de la Factura de Cierre");
        jComSer.setNextFocusableComponent(jTCli);
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
        jPanel1.add(jComSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 130, 30));

        jBFac.setBackground(new java.awt.Color(255, 255, 255));
        jBFac.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBFac.setForeground(new java.awt.Color(0, 102, 0));
        jBFac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/fac.png"))); // NOI18N
        jBFac.setText("Facturar");
        jBFac.setToolTipText("Facturar ventas ya sean remisiones o tickets");
        jBFac.setName(""); // NOI18N
        jBFac.setNextFocusableComponent(jBVerArch);
        jBFac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBFacMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBFacMouseExited(evt);
            }
        });
        jBFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFacActionPerformed(evt);
            }
        });
        jBFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBFacKeyPressed(evt);
            }
        });
        jPanel1.add(jBFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 130, 30));

        jBCompro.setBackground(new java.awt.Color(255, 255, 255));
        jBCompro.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCompro.setForeground(new java.awt.Color(0, 102, 0));
        jBCompro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/prob.png"))); // NOI18N
        jBCompro.setText("Comprobar");
        jBCompro.setToolTipText("Comprobar si esta timbrada la venta o no");
        jBCompro.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBCompro.setNextFocusableComponent(jBAcus);
        jBCompro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBComproMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBComproMouseExited(evt);
            }
        });
        jBCompro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBComproActionPerformed(evt);
            }
        });
        jBCompro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBComproKeyPressed(evt);
            }
        });
        jPanel1.add(jBCompro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 140, 30));

        jBAcus.setBackground(new java.awt.Color(255, 255, 255));
        jBAcus.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBAcus.setForeground(new java.awt.Color(0, 102, 0));
        jBAcus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/acerd.png"))); // NOI18N
        jBAcus.setText("Acuse");
        jBAcus.setToolTipText("Obtener acuse de venta(s)");
        jBAcus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBAcus.setNextFocusableComponent(jBXML);
        jBAcus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAcusMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAcusMouseExited(evt);
            }
        });
        jBAcus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAcusActionPerformed(evt);
            }
        });
        jBAcus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAcusKeyPressed(evt);
            }
        });
        jPanel1.add(jBAcus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 140, 30));

        jBXML.setBackground(new java.awt.Color(255, 255, 255));
        jBXML.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBXML.setForeground(new java.awt.Color(0, 102, 0));
        jBXML.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/adver.png"))); // NOI18N
        jBXML.setText("Obtener XML");
        jBXML.setToolTipText("Obtener el XML de una venta timbrada");
        jBXML.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBXML.setNextFocusableComponent(jBSal);
        jBXML.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBXMLMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBXMLMouseExited(evt);
            }
        });
        jBXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBXMLActionPerformed(evt);
            }
        });
        jBXML.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBXMLKeyPressed(evt);
            }
        });
        jPanel1.add(jBXML, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 140, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jBRem);
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
        jPanel1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 140, 30));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setText("Borrar");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jBInfo);
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
        jPanel1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 444, -1, -1));

        jBVerArch.setBackground(new java.awt.Color(255, 255, 255));
        jBVerArch.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVerArch.setForeground(new java.awt.Color(0, 102, 0));
        jBVerArch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/verarch.png"))); // NOI18N
        jBVerArch.setText("Archivo(s)");
        jBVerArch.setToolTipText("Ver archivos asociados a la venta");
        jBVerArch.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVerArch.setNextFocusableComponent(jBCarg);
        jBVerArch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVerArchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVerArchMouseExited(evt);
            }
        });
        jBVerArch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVerArchActionPerformed(evt);
            }
        });
        jBVerArch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVerArchKeyPressed(evt);
            }
        });
        jPanel1.add(jBVerArch, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 420, 130, -1));

        jBCarg.setBackground(new java.awt.Color(255, 255, 255));
        jBCarg.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBCarg.setForeground(new java.awt.Color(0, 102, 0));
        jBCarg.setText("Cargar");
        jBCarg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBCarg.setNextFocusableComponent(jBDel);
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
        jPanel1.add(jBCarg, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 444, -1, 20));

        jBInfo.setBackground(new java.awt.Color(255, 255, 255));
        jBInfo.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBInfo.setForeground(new java.awt.Color(0, 102, 0));
        jBInfo.setText("Información");
        jBInfo.setToolTipText("Actualizar información de boleto perdido");
        jBInfo.setNextFocusableComponent(jTab1);
        jBInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBInfoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBInfoMouseExited(evt);
            }
        });
        jBInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBInfoActionPerformed(evt);
            }
        });
        jBInfo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBInfoKeyPressed(evt);
            }
        });
        jPanel1.add(jBInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 480, 130, 30));

        jBCli.setBackground(new java.awt.Color(255, 255, 255));
        jBCli.setText("...");
        jBCli.setToolTipText("Buscar Cliente(s)");
        jBCli.setNextFocusableComponent(jBFac);
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
        jPanel1.add(jBCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, 30, 30));

        jTCli.setBackground(new java.awt.Color(204, 255, 204));
        jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCli.setNextFocusableComponent(jBCli);
        jTCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCliFocusGained(evt);
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
        jPanel1.add(jTCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 100, 30));

        jP1.add(jPanel1);
        jPanel1.setBounds(800, 0, 270, 520);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jTab2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Venta", "Qty", "Entregados", "Producto", "Desripción", "Devueltos", "Kit", "Unidad", "Almacén", "Moneda", "Precio", "Descuento", "Importe", "Impuesto", "Fecha", "Talla", "Color", "Lote", "Pedimento", "Caducidad", "Backorder", "Serie Producto", "Comentario Serie"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab2.setGridColor(new java.awt.Color(255, 255, 255));
        jTab2.setNextFocusableComponent(jBGenPDF);
        jTab2.setRequestFocusEnabled(false);
        jTab2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTab2KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab2);

        jPanel3.add(jScrollPane1);

        jP1.add(jPanel3);
        jPanel3.setBounds(20, 290, 780, 210);

        jRadBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Facturas", "Notas crédito", "Remisiones", "Todo", "Tickets" }));
        jRadBox.setSelectedIndex(3);
        jRadBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadBoxItemStateChanged(evt);
            }
        });
        jRadBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRadBoxKeyPressed(evt);
            }
        });
        jP1.add(jRadBox);
        jRadBox.setBounds(80, 10, 100, 20);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Partidas:");
        jP1.add(jLabel3);
        jLabel3.setBounds(20, 270, 170, 17);

        jLVtasTot.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLVtasTot.setText("# Ventas y total:");
        jP1.add(jLVtasTot);
        jLVtasTot.setBounds(200, 270, 310, 17);

        getContentPane().add(jP1);
        jP1.setBounds(10, 10, 1090, 650);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Carga todas las aprtidas de la compra*/
    private void vLoadParts()
    {                            
        /*Borra la tabla de partidas*/
        DefaultTableModel dm = (DefaultTableModel)jTab2.getModel();
        dm.setRowCount(0);
        
        /*Obtiene la venta*/
        String sVta    = jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString();
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene las partidas de la venta*/
        try
        {
            sQ = "SELECT vtas.VTADEVP, partvta.CANTENTRE, partvta.COMENSER, partvta.SERPROD, partvta.LOT, partvta.PEDIMEN, partvta.FCADU, partvta.TALL, partvta.COLO, partvta.FENTRE, partvta.TALL, partvta.COLO, vtas.ESTAD, partvta.ALMA, vtas.TIPDOC, partvta.VTA, partvta.PROD, partvta.CANT, partvta.DEVS, partvta.ESKIT, partvta.UNID, partvta.DESCRIP, partvta.MON, partvta.PRE, partvta.DESCU, partvta.IMPO, partvta.IMPUE, partvta.FALT, vtas.EXTR1 FROM partvta LEFT OUTER JOIN prods ON prods.PROD = partvta.PROD LEFT OUTER JOIN vtas ON vtas.VTA = partvta.VTA WHERE partvta.KITMAE = 0 AND partvta.VTA = " + sVta;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            int iContFiParts    = 1;
            while(rs.next())
            {                
                /*Obtiene los totales*/
                String sPre             = rs.getString("pre");
                String sImp             = rs.getString("impo");
                String test             = rs.getString("extr1");
                /*Dales formato de moneda a los totales*/                                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sPre);                                
                sPre            = n.format(dCant);
                dCant           = Double.parseDouble(sImp);                                
                sImp            = n.format(dCant);
                if(test==null)
                    test="";
                /*Determina el mensaje para el estado*/
                String sMsj     = "";
                if(test.compareTo("BO")==0)
                    sMsj        = "BACKORDER";
                else if(rs.getString("estad").compareTo("CO")==0)
                    sMsj        = "CONFIRMADA";
                else if(rs.getString("estad").compareTo("CA")==0)
                    sMsj        = "CANCELADA";
                else if(rs.getString("estad").compareTo("DEV")==0)
                    sMsj        = "DEVOLUCIÓN";
                else if(rs.getString("estad").compareTo("DEVP")==0)
                    sMsj        = "DEVOLUCIÓN PARCIAL";
                else if(rs.getString("estad").compareTo("PE")==0)
                    sMsj        = "PENDIENTE";
                
                /*Coloca en el label el tipo de documento que es*/
                if(rs.getString("tipdoc").compareTo("FAC")==0)
                    jLNot.setText("FACTURA: " + sMsj);
                else if(rs.getString("tipdoc").compareTo("TIK")==0)
                    jLNot.setText("TICKET: " + sMsj);
                else if(rs.getString("tipdoc").compareTo("REM")==0)
                    jLNot.setText("REMISIÓN: " + sMsj);
                else if(rs.getString("tipdoc").compareTo("NOTC")==0)
                    jLNot.setText("NOTA DE CRÉDITO: " + sMsj);
                
                /*Obtiene la descripción*/
                String sDescrip     = rs.getString("descrip");
                
                /*Obtiene la unidad*/
                String sUnid        = rs.getString("unid");
                
                /*Obtiene el almacén*/
                String sAlma        = rs.getString("alma");
                
                /*Obtiene el descuento*/
                String sDesc        = rs.getString("descu");
                                
                /*Obtiene la cantidad*/
                String sCant        = rs.getString("cant");
                
                /*Obtiene la venta*/
                String sVtaI        = rs.getString("vta");
                
                /*Obtiene los devueltos*/
                String sDevs        = rs.getString("devs");
                
                /*Obtiene si es kit o no*/
                String sKit         = rs.getString("eskit");
                
                /*Obtiene la moneda*/
                String sMon         = rs.getString("mon");
                
                /*Obtiene el impuesto*/
                String sImpue       = rs.getString("impue");
                
                /*Obtiene la talla*/
                String sTall        = rs.getString("tall");
                
                /*Obtiene el color*/
                String sColo        = rs.getString("colo");
                
                /*Obtiene el lote*/
                String sLot         = rs.getString("lot");
                
                /*Obtiene el pedimento*/
                String sPedimen     = rs.getString("pedimen");
                
                /*Obtiene la caducidad*/
                String sCadu        = rs.getString("fcadu");
                
                /*Obtiene el backorder*/
                String sBack        = rs.getString("fentre");
                
                /*Obtiene los entregados*/
                String sEntre       = rs.getString("cantentre");
                
                /*Obtiene la serie y el comentario de la serie*/
                String sSerProd     = rs.getString("comenser");
                String sComenSer    = rs.getString("serprod");
                
                /*Si es nota de crédito entonces*/
                if(rs.getString("tipdoc").compareTo("NOTC")==0 && rs.getString("vtadevp").compareTo("0")==0)
                {
                    /*Creal la descripción*/
                    sDescrip        = sDescrip + " del Cliente: " + sUnid;
                    
                    /*Restea los valores que no deben de colocarse*/
                    sDesc           = "";
                    sAlma           = "";
                    sCant           = "";                                        
                    sVtaI           = "";                                        
                    sDevs           = "";                                        
                    sKit            = "";                                        
                    sMon            = "";                                        
                    sImpue          = "";                                        
                    sUnid           = "";                                        
                    sPre            = "";                                      
                    sTall           = "";                                      
                    sColo           = "";                                      
                    sBack           = "";
                    sLot            = "";
                    sPedimen        = "";
                    sCadu           = "";
                    sEntre          = "";
                    sComenSer       = "";
                    sSerProd        = "";
                }
                
                /*Carga los datos en la tabla*/                
                DefaultTableModel te    = (DefaultTableModel)jTab2.getModel();
                Object nu[]             = {iContFiParts, sVtaI, sCant, sEntre, rs.getString("prod"), sDescrip, sDevs, sKit, sUnid, sAlma, sMon, sPre, sDesc, sImp, sImpue, rs.getString("falt"), sTall, sColo, sLot, sPedimen, sCadu, sBack, sSerProd, sComenSer};
                te.addRow(nu);
                
                /*Aumenta el contador de filas de las partidas*/
                ++iContFiParts;                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }  
	        
        /*Obtiene el nombre del cliente*/
        try
        {
            sQ = "SELECT emps.NOM FROM vtas LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP) = vtas.CODEMP WHERE vta = " + sVta;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el nombre del cliente en el label*/            
            if(rs.next())            
                jLNotCli.setText(rs.getString("nom"));
            else
                jLNotCli.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }  
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vLoadParts()*/
    
    
    /*Abre la forma de nueva venta*/
    private void vAbrNewV()
    {
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
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea y mensajea*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                            
            return;                        
        }

        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Función para abrir la forma de ventas una sola vez*/
        Star.AbrVta(jTab1, null, "");                
                    
    }/*Fin de private void vAbrNewV()*/
    
    
    /*Abre la forma de nueva nota de crédito*/
    private void vAbrNewN()
    {
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
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea y mensajea*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                            
            return;                        
        }

        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Mostrar el formulario de nueva nota de crédito*/
        NewNot in = new NewNot(jTab1);
        in.setVisible(true);
            
    }/*Fin de private void vAbrNewN()*/
    
    
    /*Abre la forma de nueva venta pero con todos los datos ya cargados en copia de otra*/
    private void vCopVta()
    {
        /*Si no a seleccionado nada de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una venta primeramente.", "Nueva venta machote", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el tabla y regresa*/
            jTab1.grabFocus();
            return;                        
        }
        
        /*Función para abrir la forma de ventas una sola vez*/
        Star.AbrVta(jTab1, jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString(), "");                
    }
    
        
    /*Al presionar el botón de ingresar nueva facturas*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed

        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Obtiene el almacén configurado para venta
        String sAlma = Star.sGetAlmaVta(con);
        
        //Si hubo error regresa
        if(sAlma==null)
            return;
        
        /*Si el almacén no es cadena vacia entonces*/
        if(sAlma.compareTo("")!=0)
        {
            /*Checa si el código del almacén ya existe en la base de datos*/        
            int iRes    = Star.iExiste(con, sAlma.trim(), "almas", "alma");

            //Si hubo error entonces regresa
            if(iRes==-1)
                return;

            //Si no hay datos entonces coloca la bandera en false
            boolean bSi = true;
            if(iRes==0)
                bSi = false;
            
            /*Si el almacén no exsite entonces*/
            if(!bSi)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El almacén para venta no existe y no se puede continuar.", "Almacén", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
        }/*Fin de if(sAlma.compareTo("")!=0)*/                    

        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la tecla de alt esta presionada entonces*/
        if(bAltP)
        {
            /*Resetea la bandera y procesa todo esto en una función*/
            bAltP  = false;            
            vCopVta();
        }
        /*Else no esta entonces realiza esta operación por medio de esta función*/
        else        
            vAbrNewV();                         
        
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se presiona el botón de devolución*/
    private void jBDevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDevActionPerformed
           
        /*Si el usuario no a seleccionado una venta para devolución no puede avanzar*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado por lo menos una venta para devolución.", "Devolcuión de Venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de facturas y regresa*/
            jTab1.grabFocus();                        
            return;            
        }

        /*Obtiene toda la selección del usuario*/
        int iSel[]              = jTab1.getSelectedRows();        
        
        /*Preguntar al usuario si esta seguro querer hacer la devolución de la factura*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres hacer la devolución?", "Devolución de Venta", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes== JOptionPane.NO_OPTION || iRes== JOptionPane.CANCEL_OPTION)
            return;                       
        
        /*Mientras el usuario no escriba un motivo de devolución o cancele la devolución entonces*/
        String sMot;
        do
        {
            /*Pide al usuario el motivo de la devolución de la venta*/
            sMot = JOptionPane.showInputDialog(this ,"Motivo de devolución:", "Motivo", JOptionPane.QUESTION_MESSAGE);
            
            /*Si el usuario cancelo el cuadro entonces regresa por que no puede continuar*/
            if(sMot == null)
                return;

            /*Si el usuario no escribio un motivo de devolución entonces mensajea*/
            if(sMot.compareTo("")==0)
                JOptionPane.showMessageDialog(null, "Escribe un motivo de devolución.", "Escribir motivo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
        }
        while(sMot.compareTo("")== 0);                
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene si se tiene que mostrar o no el ticket de cancelacion
        iRes        = Star.iGetVePDFCan(con);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si tiene que mostrar el ticket de cancelación entonces coloca la bandera
        boolean bSiVerCan   = false;
        if(iRes==1)
            bSiVerCan   = true;
                
        //Obtiene si se tiene que guardar el PDF de cancelación o no
        iRes        = Star.iGetGuaPDFCan(con);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si se tiene que guardar el PDF en disco entonces coloca la bandera
        boolean bSiPDF   = false;
        if(iRes==1)
            bSiPDF   = true;        
        
        /*Bandera para saber si hubo modificación o no*/
        boolean bSi = false;
        
        /*Recorre toda la selección del usuario*/        
        for(int x = iSel.length - 1; x >= 0; x--)
        {            
            //Declara variables locales
            String sTipDoc  = "";
            String sEstad   = "";
            String sNoSer   = "";
            String sFol     = "";
            String sEmp     = "";
            String sSer     = "";
            String sFDoc    = "";
            String sFVenc   = "";
            String sSubTot  = "";
            String sImpue   = "";
            String sTot     = "";
            
            /*Obtiene algunos datos de la venta*/            
            try
            {
                sQ = "SELECT tot, impue, fvenc, ser, falt, subtot, codemp, noser, norefer, estad, tipdoc FROM vtas WHERE vta = " + jTab1.getValueAt(iSel[x], 1).toString();	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())
                {                    
                    sTipDoc     = rs.getString("tipdoc");                                   
                    sEstad      = rs.getString("estad");                                   
                    sNoSer      = rs.getString("noser");                                   
                    sFol        = rs.getString("norefer");                                   
                    sEmp        = rs.getString("codemp");                                   
                    sSer        = rs.getString("ser");                                   
                    sFDoc       = rs.getString("falt");                                   
                    sFVenc      = rs.getString("fvenc");                                   
                    sSubTot     = rs.getString("subtot");                                   
                    sImpue      = rs.getString("impue");                                   
                    sTot        = rs.getString("tot");                                   
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                   
            }

            /*Si es cliente mostrador entonces*/
            if(sEmp.compareTo(Star.sCliMostG)==0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "No se puede hacer devolcuión al cliente mostrador.", "Devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            
            /*Contiene el abono de esa venta*/
            String sAbon    = "";            
            
            /*Obtiene los cargos y los abonos de esa venta*/            
            try
            {                  
                sQ = "SELECT IFNULL(SUM(abon),0) AS abon FROM cxc WHERE empre = '" + sEmp + "' AND norefer = " + sFol + " AND noser = '" + sNoSer + "' AND concep = 'ABON FAC'";                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())                                                 
                    sAbon   = rs.getString("abon");                
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                    
            }
            
            /*Si es una nota de crédito entonces*/
            if(sTipDoc.compareTo("NOTC")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta " + jTab1.getValueAt(iSel[x], 1).toString() + " es una Nota de Crédito y no se puede devolver.", "Nota de Crédito", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                continue;
            }                        
            
            /*Si es una venta que ya fue devuelta entonces*/        
            if(sEstad.compareTo("DEV")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Venta: " + jTab1.getValueAt(iSel[x], 1).toString() + " ya fue devuelta.", "Venta Devuelta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }

            /*Si es una venta que ya fue cancelada entonces*/        
            if(sEstad.compareTo("CA")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Venta: " + jTab1.getValueAt(iSel[x], 1).toString() + " esta cancelada.", "Venta Cancelada", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }

            /*Si es una venta que es por devulución parcial entonces*/
            if(sEstad.compareTo("DEVP")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Venta: " + jTab1.getValueAt(iSel[x], 1).toString() + " fue por devolución parcial.", "Factura de Devolución Parcial", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }
 
            /*Si el abono es mayor a 0 entonces*/            
            if(Double.parseDouble(sAbon)>0)
            {
                /*Dale formato de moneda al abono*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                double dCant    = Double.parseDouble(sAbon);                
                sAbon           = n.format(dCant);

                /*Preguntar al usuario si esta seguro de querer continuar*/
                iRes    = JOptionPane.showOptionDialog(this, "La venta: " + jTab1.getValueAt(iSel[x], 1).toString() + " con folio: " + sNoSer + sFol + " del cliente: " + sEmp + " tiene de abonos: " + sAbon + ". ¿Estas seguro que quieres continuar?", "Devolución de Venta", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                    continue;                                                       
            }
            
            //Inicia la transacción
            if(Star.iIniTransCon(con)==-1)
                return;            

            /*Actualiza la venta para que sea de devolución*/
            try 
            {            
                sQ = "UPDATE vtas SET "
                        + "estad        = 'DEV', "
                        + "motiv        = '" + sMot.replace("'", "''") + "',  "
                        + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE vta    = '" + jTab1.getValueAt(iSel[x], 1).toString() + "'";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                    
            }

            /*Recorre todas las partidas de la venta*/
            try
            {                  
                sQ = "SELECT partvta.COST, partvta.FCADU, partvta.LOT, partvta.PEDIMEN, prods.SERVI, partvta.ID_ID, partvta.ESKIT, partvta.TALL, partvta.COLO, vtas.TIPDOC, vtas.CODEMP, vtas.NOSER, partvta.UNID, partvta.PROD, partvta.CANT, partvta.DESCRIP, partvta.ALMA FROM partvta LEFT JOIN prods ON prods.PROD = partvta.PROD LEFT OUTER JOIN vtas ON vtas.VTA = partvta.VTA WHERE partvta.VTA = '" + jTab1.getValueAt(iSel[x], 1).toString() + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                while(rs.next())
                {
                    /*Si el producto es un kit que continue por que no se debe devolver*/
                    if(rs.getString("eskit").compareTo("1")==0  || rs.getString("servi").compareTo("1")==0)
                        continue;

                    /*Obtiene la cantidad correcta dependiendo de su unidad*/
                    String sCant    = Star.sCantUnid(rs.getString("unid"), rs.getString("cant"));
                        
                    /*Ingresa el costeo*/
                    if(Star.iInsCost(con, rs.getString("prod"), sCant, rs.getString("cost"))==-1)
                        return;                             
                        
                    /*Si la partida fue por lote o pedimento entonces*/
                    if(rs.getString("lot").compareTo("")!=0 || rs.getString("pedimen").compareTo("")!=0)
                    {
                        /*Realiza la afectación a lotes y pedimentos*/
                        if(Star.sLotPed(con, rs.getString("prod"), sCant, rs.getString("alma"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("fcadu"), "+")==null)
                            return;
                    }                            

                    /*Si tiene talla o color entonces procesa esta parte en la siguiente función*/
                    if(rs.getString("tall").compareTo("")!=0 || rs.getString("colo").compareTo("")!=0)
                        Star.vTallCol(con, sCant, rs.getString("alma"), rs.getString("tall"), rs.getString("colo"), rs.getString("prod"), "+");
                    
                    /*Realiza la afectación correspondiente al almacén para la entrada*/
                    if(Star.iAfecExisProd(con, rs.getString("prod").replace("'", "''").trim(), rs.getString("alma").replace("'", "''").trim(), sCant, "+")==-1)
                        return;

                    /*Actualiza la existencia general del producto*/
                    if(Star.iCalcGralExis(con, rs.getString("prod").replace("'", "''").trim())==-1)
                        return;

                    /*Devuelve los costeos correspondientes*/
                    if(Star.iDevCost(con, rs.getString("id_id"))==-1)
                        return;

                    /*Registra el producto que se esta devolviendo de la empresa en la tabla de monitor de invtarios*/
                    if(!Star.vRegMoniInv(con, rs.getString("partvta.PROD"), sCant, rs.getString("partvta.DESCRIP"), rs.getString("alma"), Login.sUsrG, sNoSer + sFol, "DEV " + rs.getString("vtas.TIPDOC"), rs.getString("partvta.UNID"), rs.getString("vtas.NOSER"), rs.getString("vtas.CODEMP"), "0"))                                
                        return;                    
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                    
            }

            //Agrega una partida en cxc para agregarle saldo al cliente de la devolución            
            if(Star.iInsCXCP(con, "cxc", sFol, sNoSer, sEmp, sSer, sSubTot, sImpue, sTot, "0", sTot, "'" + sFVenc + "'", "'" + sFDoc + "'", "DEV FAC", "", "0", "", "","")==-1)
                return;               
            
            //Termina la transacción
            if(Star.iTermTransCon(con)==-1)
                return;
            
            /*Declara variables final para el thread*/
            final String sVtaFi         = jTab1.getValueAt(iSel[x], 1).toString();
            final boolean bSiVerCanFi   = bSiVerCan;
            final boolean bSiPDFFi      = bSiPDF;

            /*Crea el thread para que mande a imprimir y guardar el ticket de devolución*/
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

                    //Obtiene el RFC de la empresa local
                    String sRFCLoc  = Star.sGetRFCLoc(con);

                    //Si hubo error entonces regresa
                    if(sRFCLoc==null)
                        return;

                    /*Declara variables*/
                    String  sFol        = "";
                    String  sNoSer      = "";
                    String  sTipDoc     = "";
                    String  sEmp        = "";
                    String  sSer        = "";
                    String  sNom        = "";
                    String  sFEmi       = "";
                    String  sSubTot     = "";
                    String  sImpue      = "";
                    String  sTot        = "";

                    //Declara variables de la base de datos
                    Statement   st;
                    ResultSet   rs;                    
                    String      sQ; 

                    /*Obtiene algunos datos de la venta que se esta devolviendo*/
                    try
                    {
                        sQ = "SELECT emps.NOM, norefer, noser, tipdoc, vtas.CODEMP, vtas.SER, femi, subtot, impue, tot FROM vtas LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP) = vtas.CODEMP WHERE vta = " + sVtaFi;
                        st = con.createStatement();
                        rs = st.executeQuery(sQ);
                        /*Si hay datos entonces guarda la consulta en las variables*/
                        if(rs.next())
                        {                    
                            sFol      = rs.getString("norefer");                                   
                            sNoSer    = rs.getString("noser");                                   
                            sTipDoc   = rs.getString("tipdoc");                                   
                            sEmp      = rs.getString("vtas.CODEMP");                                   
                            sSer      = rs.getString("ser");                                   
                            sNom      = rs.getString("nom");                                   
                            sFEmi     = rs.getString("femi");                                   
                            sSubTot   = rs.getString("subtot");                                   
                            sImpue    = rs.getString("impue");                                   
                            sTot      = rs.getString("tot");                                   
                        }
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                        return;                                                                                                                                            
                    }

                    /*Dale formato de moneda a los totales*/                    
                    NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                    double dCant    = Double.parseDouble(sSubTot);                
                    sSubTot         = n.format(dCant);
                    dCant           = Double.parseDouble(sImpue);                
                    sImpue          = n.format(dCant);
                    dCant           = Double.parseDouble(sTot);                
                    sTot            = n.format(dCant);

                    /*Crea los parámetros que se pasarán al ticket de devolución*/
                    Map <String,String> para = new HashMap<>();             
                    para.clear();
                    para.put("VTA",         sVtaFi);
                    para.put("FOL",         sFol);        
                    para.put("NOSER",       sNoSer);        
                    para.put("TIPDOC",      sTipDoc);        
                    para.put(Star.sSerMostG,sEmp);        
                    para.put("SER",         sSer);        
                    para.put("NOM",         sNom);        
                    para.put("FEMI",        sFEmi);        
                    para.put("SUBTOT",      sSubTot);        
                    para.put("IMPUE",       sImpue);        
                    para.put("TOT",         sTot);        
                    para.put("TIT",         "DEVOLUCIÓN VENTA");        
                    para.put("LOGE",        Star.class.getResource(Star.sIconDef).toString());

                    /*Compila el reporte*/                        
                    JasperReport ja;
                    JasperPrint  pr;
                    try
                    {
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptDevVta.jrxml"));            
                        pr     = JasperFillManager.fillReport(ja, (Map)para, con);            
                    }
                    catch(JRException expnJASR)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                        return;                                                                                                                                            
                    }                        

                    /*Si esta seleccionado en ver el ticket entonces verlo*/
                    if(bSiVerCanFi)
                    {
                        /*Muestralo maximizado*/
                        JasperViewer v  = new JasperViewer(pr, false);
                        v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);                    
                        v.setVisible(true);
                    }

                    //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
                    String sCarp    = Star.sGetRutCarp(con);                    

                    //Si hubo error entonces regresa
                    if(sCarp==null)
                        return;

                    /*Si el directorio de devoluciones no existe entonces crea la carpeta*/                    
                    String sRutPDF          = sCarp + "\\Devoluciones";                          
                    if(!new File(sRutPDF).exists())
                        new File(sRutPDF).mkdir();

                    /*Si no existe la carpeta de la empresa entonces crea la carpeta*/
                    sRutPDF                 += "\\" + Login.sCodEmpBD;
                    if(!new File(sRutPDF).exists())
                        new File(sRutPDF).mkdir();

                    /*Completa la ruta al PDF*/
                    sRutPDF                 += "\\" + sSer + "-" + sFol + "-" + sRFCLoc + ".pdf";

                    /*Si se tiene que guardar el PDF en disco entonces exportalo al disco*/
                    if(bSiPDFFi)
                    {
                        try
                        {
                            JasperExportManager.exportReportToPdfFile(pr, sRutPDF);
                        }
                        catch(JRException expnJASR)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                            return;                                                                                                                    
                        }
                    }                        

                    //Cierra la base de datos
                    Star.iCierrBas(con);
                    
                }
            }).start();
            
            /*Coloca la bandera para saber que si hubo modificación*/
            bSi = true;
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                                                                                                                          
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Carga nuevamente todas las ventas*/
        (new Thread()
        {
            @Override
            public void run()
            {
                vCargT();
            }
        }).start();
        
        /*Mensaje de éxito en caso de que alla habido una modificación*/
        if(bSi)
            JOptionPane.showMessageDialog(null, "Exito en la devolución de la(s) venta(s).", "Devolución Exitosa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBDevActionPerformed
                    
                                
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el botón de ingresar nueva*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed

        /*Si se presionò la tecla de control entonces coloca la bandera para saber que esta presionada la tecla*/
        if(evt.getKeyCode() == KeyEvent.VK_ALT)
            bAltP  = true;
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed

   
    /*Cuando se presiona una tecla en el botón de devolución*/
    private void jBDevKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDevKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDevKeyPressed

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra el formulario*/
        this.dispose();        
        Star.gVtas = null;
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en la tabla de vtas*/
    private void jTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTab1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jTab1KeyPressed

    
    /*Cuando se presiona un botón en el botón de ver*/
    private void jBVerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBVerKeyPressed

    
    /*Cuando se presiona el botón de ver*/
    private void jBVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerActionPerformed
                                                        
        /*Si el usuario no a seleccionado nada de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una factura para ver.", "Ver Factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();                     
            return;
        }
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab1.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Manda llamar el formulario para ver la venta y pasale el código de la factura*/
            VVta v = new VVta(jTab1.getModel().getValueAt(iSel[x], 1).toString());
            v.setVisible(true);            
        }
                            
    }//GEN-LAST:event_jBVerActionPerformed

    
    /*Cuando se hace un clic en la tabla de compras*/
    private void jTab1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTab1MouseClicked
        
        /*Si se hiso doble clic entonces presiona el botón de ver*/
        if(evt.getClickCount() == 2) 
            jBVer.doClick();
        
    }//GEN-LAST:event_jTab1MouseClicked

    
    /*Cuando se presiona el botón de cancelar*/
    private void jBCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCaActionPerformed
                        
        /*Si el usuario no a seleccionado por lo menos una venta para cancelar no puede avanzar*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una venta para cancelar.", "Cancelación de venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de facturas y regresa*/
            jTab1.grabFocus();                        
            return;            
        }
        
        /*Obtiene toda la selección del usuario*/
        int iSel[]              = jTab1.getSelectedRows();        
        
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
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres hacer la(s) cancelación(es)?", "Cancelación de venta", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes== JOptionPane.NO_OPTION || iRes== JOptionPane.CANCEL_OPTION)
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
        
        //Obtiene si se tiene que mostrar o no el ticket de cancelacion
        iRes        = Star.iGetVePDFCan(con);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si tiene que mostrar el ticket de cancelación entonces coloca la bandera
        boolean bSiVerCan   = false;
        if(iRes==1)
            bSiVerCan   = true;
                
        //Obtiene si se tiene que guardar el PDF de cancelación o no
        iRes        = Star.iGetGuaPDFCan(con);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si se tiene que guardar el PDF en disco entonces coloca la bandera
        boolean bSiPDF   = false;
        if(iRes==1)
            bSiPDF   = true;        
                
        /*Bandera para saber si hubo alguna modificación*/
        boolean bSi = false;

        /*Si la selección es mayor a 500*/
        if(iSel.length>500)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se pueden cancelar mas de 500 ventas al mismo tiempo.", "Cancelación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
            
        }/*Fin de if(iSel.length>500)*/
        
        //Obtiene el RFC de la empresa local
        String sRFCLoc  = Star.sGetRFCLoc(con);

        //Si hubo error entonces regresa
        if(sRFCLoc==null)
            return;

        /*Recorre toda la selección del usuario*/        
        for(int x = iSel.length - 1; x >= 0; x--)
        {            
            //Declara variables locales
            String sEstad   = "";
            String sSubTot  = "";
            String sFol     = "";
            String sNoSer   = "";
            String sFVenc   = "";
            String sImpue   = "";
            String sEmp     = "";
            String sSer     = "";
            String sFDoc    = "";
            String sTipDoc  = "";
            String sTot     = "";            
            String sFolFisc = "";
            
            /*Obtiene algunos datos de la venta*/            
            try
            {                  
                sQ = "SELECT folfisc, tot, tipdoc, falt, fvenc, impue, codemp, ser, norefer, noser, estad, subtot FROM vtas WHERE vta = " + jTab1.getValueAt(iSel[x], 1).toString();                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {                    
                    sEstad  = rs.getString("estad");
                    sSubTot = rs.getString("subtot");
                    sFol    = rs.getString("norefer");
                    sNoSer  = rs.getString("noser");
                    sFVenc  = rs.getString("fvenc");
                    sImpue  = rs.getString("impue");
                    sEmp    = rs.getString("codemp");
                    sSer    = rs.getString("ser");
                    sFDoc   = rs.getString("falt");
                    sTipDoc = rs.getString("tipdoc");
                    sTot    = rs.getString("tot");                    
                    sFolFisc= rs.getString("folfisc");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                    
            }
            
            /*Contiene el abono de esa compra*/
            String sAbon    = "";            
            
            /*Obtiene el abono de esa venta*/            
            try
            {                  
                sQ = "SELECT IFNULL(SUM(abon),0) AS abon FROM cxc WHERE empre = '" + sEmp + "' AND norefer = " + sFol + " AND noser = '" + sNoSer + "' AND concep = 'ABON FAC'";                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene el resultado*/
                if(rs.next())                                                 
                    sAbon   = rs.getString("abon");                                
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                    
            }
            
            /*Si es una venta que ya fue devuelta en su totalidad entonces*/
            if(sEstad.compareTo("DEV")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta " + jTab1.getValueAt(iSel[x], 1).toString().trim() + " no se puede cancelar por que fue por devolución.", "Venta de Devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }

            /*Si es una venta que es por devolución parcial entonces*/
            if(sEstad.compareTo("DEVP")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta " + jTab1.getValueAt(iSel[x], 1).toString().trim() + " no se puede cancelar por que fue por devolución parcial.", "Venta de Devolución Parcial", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }

            if(sEstad.compareTo("CA")==0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta " + jTab1.getValueAt(iSel[x], 1).toString().trim() + " no se puede cancelar por que fue por devolución parcial.", "Venta de Devolución Parcial", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }
            /*Si el abono es mayor a 0 entonces*/            
            if(Double.parseDouble(sAbon)>0)
            {
                /*Dale formato de moneda al abono*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                double dCant    = Double.parseDouble(sAbon);                
                sAbon           = n.format(dCant);

                /*Preguntar al usuario si esta seguro de querer continuar*/
                iRes    = JOptionPane.showOptionDialog(this, "La venta: " + jTab1.getValueAt(iSel[x], 1).toString().trim() + " con folio: " + sNoSer + sFol + " del cliente: " + sEmp + " tiene de abonos: " + sAbon + ". ¿Estas seguro que quieres continuar?", "Cancelar Venta", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                    continue;                                                       
            }                                                
            
            //Si la venta no esta cancelada entonces
            if(sEstad.compareTo("CA")!=0)
            {
                //Inicia la transacción
                if(Star.iIniTransCon(con)==-1)
                    return;            

                /*Actualiza la venta para que sea de cancelación*/
                try 
                {            
                    sQ = "UPDATE vtas SET "
                            + "estad        = 'CA', "
                            + "motiv        = '" + sMot.replace("'", "''") + "', "
                            + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                            + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "', "
                            + "estac        = '" + Login.sUsrG + "', "
                            + "fmod         = now() "
                            + "WHERE vta    = " + jTab1.getValueAt(iSel[x], 1).toString().trim();                                
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                                                                                                                                    
                }        

                /*Si no es una nota de crédito entonces*/
                if(sTipDoc.compareTo("NOTC")!=0)
                {
                    /*Recorre todas las partidas de la venta*/
                    try
                    {                  
                        sQ = "SELECT partvta.COST, partvta.FCADU, partvta.LOT, partvta.PEDIMEN, partvta.FCADU, prods.SERVI, partvta.ID_ID, partvta.ESKIT, partvta.TALL, partvta.COLO, vtas.TIPDOC, vtas.CODEMP, vtas.NOSER, partvta.UNID, partvta.PROD, partvta.CANT, partvta.DESCRIP, partvta.ALMA FROM partvta LEFT OUTER JOIN vtas ON vtas.VTA = partvta.VTA LEFT JOIN prods ON prods.PROD = partvta.PROD WHERE partvta.VTA = '" + jTab1.getValueAt(iSel[x], 1).toString() + "'";
                        st = con.createStatement();
                        rs = st.executeQuery(sQ);
                        /*Si hay datos*/
                        while(rs.next())
                        {   
                            /*Si el producto es un kit o un servicio entonces que continue por que no se debe devolver*/
                            if(rs.getString("eskit").compareTo("1")==0 || rs.getString("servi").compareTo("1")==0)
                                continue;                                               

                            /*Obtiene la cantidad correcta dependiendo de su unidad*/
                            String sCant    = Star.sCantUnid(rs.getString("unid"), rs.getString("cant"));

                            /*Ingresa el costeo*/
                            if(Star.iInsCost(con, rs.getString("prod"), sCant, rs.getString("cost"))==-1)
                                return;                             

                            /*Si la partida fue por lote o pedimento entonces*/
                            if(rs.getString("lot").compareTo("")!=0 || rs.getString("pedimen").compareTo("")!=0)
                            {
                                /*Realiza la afectación a lotes y pedimentos*/
                                if(Star.sLotPed(con, rs.getString("prod"), sCant, rs.getString("alma"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("fcadu"), "+")==null)
                                    return;
                            }                            

                            /*Si tiene talla o color entonces procesa esta parte en la siguiente función*/
                            if(rs.getString("tall").compareTo("")!=0 || rs.getString("colo").compareTo("")!=0)
                               Star.vTallCol(con, sCant, rs.getString("alma"), rs.getString("tall"), rs.getString("colo"), rs.getString("prod"), "+");

                            /*Realiza la afectación correspondiente al almacén para la entrada*/
                            if(Star.iAfecExisProd(con, rs.getString("prod").replace("'", "''").trim(), rs.getString("alma").replace("'", "''").trim(), sCant, "+")==-1)
                                return;

                            /*Actualiza la existencia general del producto*/
                            if(Star.iCalcGralExis(con, rs.getString("prod").replace("'", "''").trim())==-1)
                                return;

                            /*Devuelve los costeos correspondientes*/
                            if(Star.iDevCost(con, rs.getString("id_id"))==-1)
                                return;

                            /*Registra el producto que se esta aumentando al inventario en la tabla de monitor de inventarios*/
                            if(!Star.vRegMoniInv(con, rs.getString("partvta.PROD"), sCant, rs.getString("partvta.DESCRIP"), rs.getString("partvta.ALMA"), Login.sUsrG, sNoSer + sFol, "CAN " + rs.getString("vtas.TIPDOC"), rs.getString("partvta.UNID"), rs.getString("vtas.NOSER"), rs.getString("vtas.CODEMP"), "0"))                                
                                return;                                                                                       
                        }
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                        return;                                                                                                                                        
                    }

                }/*Fin de if(sTipDoc.compareTo("NOT")==0)*/                                    
                /*Else si es una nota de crédito entonces*/
                else
                {
                    /*Recorre todas las partidas de la venta*/
                    try
                    {                  
                        sQ = "SELECT prod FROM partvta WHERE partvta.VTA = '" + jTab1.getValueAt(iSel[x], 1).toString() + "'";
                        st = con.createStatement();
                        rs = st.executeQuery(sQ);
                        /*Si hay datos entonces actualiza cada partida de factura para que ya no esten asociados a una nota de crédito*/
                        while(rs.next())
                            vActNot(con, rs.getString("prod"));
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                        return;                                                                                                                                                        
                    }                                    
                }

                //Agrega una partida en cxc para agregarle saldo al cliente de la cancelación            
                if(Star.iInsCXCP(con, "cxc", sFol, sNoSer, sEmp, sSer, sSubTot, sImpue, sTot, "0", sTot, "'" + sFVenc + "'", "'" + sFDoc + "'", "CA "+ sTipDoc, "", "0", "", "","")==-1)
                    return;               

                //Termina la transacción
                if(Star.iTermTransCon(con)==-1)
                    return;
                
            }//Fin de if(sEstad.compareTo("CA")==0)
            String sRFCLocal="";
            try
            {                  
            sQ = "SELECT noint, noext, IFNULL(nom, '') AS nom, IFNULL(calle,'') AS calle, IFNULL(tel,'') AS tel, IFNULL(col,'') AS col, IFNULL(cp,'') AS cp, IFNULL(ciu,'') AS ciu, IFNULL(estad,'') AS estad, IFNULL(pai,'') AS pai, IFNULL(rfc, '' ) AS rfc FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {                
                    sRFCLocal             = rs.getString("rfc");                                   
                                                   
                }                        
            }
            catch(SQLException expnSQL)
            {
                Star.iErrProc(Star.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
                return;                        
            }
            //Si el folio fiscal existe entonces si timbro con el pack y trata de cancelar con el pack
            if(sFolFisc.compareTo("")!=0)
            {
                //Cancela el folio fiscal con el PAC
                if(sRFCLocal.compareTo("AAA010101AAA")==0)
                {
                    System.out.println("prueba");
                    if(Star.iCanFacP(sFolFisc, sRFCLoc)==-1)
                    {
                    //Cierra la base de datos y regresa
                    Star.iCierrBas(con);
                    return;
                    }    
                }
                else if(Star.iCanFac(sFolFisc, sRFCLoc)==-1)
                {
                    //Cierra la base de datos y regresa
                    Star.iCierrBas(con);
                    return;
                }                
            }                           
            
            /*Coloca la bandera para saber que si hubo una modificación*/
            bSi = true;
            System.out.println(bSi);
            /*Declara variables final para el thread*/
            final String sVtaFi         = jTab1.getValueAt(iSel[x], 1).toString();
            final boolean bSiVerCanFi   = bSiVerCan;
            final boolean bSiPDFFi      = bSiPDF;
            
            /*Crea el thread para que mande a imprimir y guardar el ticket de cancelación*/
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

                    //Obtiene el RFC de la empresa local
                    String sRFCLoc  = Star.sGetRFCLoc(con);

                    //Si hubo error entonces regresa
                    if(sRFCLoc==null)
                        return;

                    /*Declara variables*/
                    String  sFol        = "";
                    String  sNoSer      = "";
                    String  sTipDoc     = "";
                    String  sEmp        = "";
                    String  sSer        = "";
                    String  sNom        = "";
                    String  sFEmi       = "";
                    String  sSubTot     = "";
                    String  sImpue      = "";
                    String  sTot        = "";

                    //Declara variables de la base de datos
                    Statement   st;
                    ResultSet   rs;                    
                    String      sQ; 
                    
                    /*Obtiene algunos datos de la venta que se esta cancelando*/
                    try
                    {
                        sQ = "SELECT emps.NOM, norefer, noser, tipdoc, vtas.CODEMP, vtas.SER, femi, subtot, impue, tot FROM vtas LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP) = vtas.CODEMP WHERE vta = " + sVtaFi;
                        st = con.createStatement();
                        rs = st.executeQuery(sQ);
                        /*Si hay datos entonces guarda la consulta en las variables*/
                        if(rs.next())
                        {                    
                            sFol      = rs.getString("norefer");                                   
                            sNoSer    = rs.getString("noser");                                   
                            sTipDoc   = rs.getString("tipdoc");                                   
                            sEmp      = rs.getString("vtas.CODEMP");                                   
                            sSer      = rs.getString("ser");                                   
                            sNom      = rs.getString("nom");                                   
                            sFEmi     = rs.getString("femi");                                   
                            sSubTot   = rs.getString("subtot");                                   
                            sImpue    = rs.getString("impue");                                   
                            sTot      = rs.getString("tot");                                   
                        }
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                        return;                                                                                                                                           
                    }

                    /*Dale formato de moneda a los totales*/                    
                    NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                    double dCant    = Double.parseDouble(sSubTot);                
                    sSubTot         = n.format(dCant);
                    dCant           = Double.parseDouble(sImpue);                
                    sImpue          = n.format(dCant);
                    dCant           = Double.parseDouble(sTot);                
                    sTot            = n.format(dCant);

                    /*Crea los parámetros que se pasarán al ticket de cancelación*/
                    Map <String,String> para = new HashMap<>();             
                    para.clear();
                    para.put("VTA",         sVtaFi);
                    para.put("FOL",         sFol);        
                    para.put("NOSER",       sNoSer);        
                    para.put("TIPDOC",      sTipDoc);        
                    para.put(Star.sSerMostG,sEmp);        
                    para.put("SER",         sSer);        
                    para.put("NOM",         sNom);        
                    para.put("FEMI",        sFEmi);        
                    para.put("SUBTOT",      sSubTot);        
                    para.put("IMPUE",       sImpue);        
                    para.put("TOT",         sTot);        
                    para.put("TIT",        "CANCELACIÓN VENTA");        
                    para.put("LOGE",       Star.class.getResource(Star.sIconDef).toString());

                    /*Compila el reporte*/                        
                    JasperReport ja;
                    JasperPrint  pr;
                    try
                    {
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptCanVta.jrxml"));            
                        pr     = JasperFillManager.fillReport(ja, (Map)para, con);            
                    }
                    catch(JRException expnJASR)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                        return;                                                                                                                    
                    }                        

                    /*Si esta seleccionado en ver el ticket entonces verlo*/
                    if(bSiVerCanFi)
                    {
                        /*Muestralo maximizado*/
                        JasperViewer v  = new JasperViewer(pr, false);
                        v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);                    
                        v.setVisible(true);
                    }
                    
                    //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
                    String sCarp    = Star.sGetRutCarp(con);                    

                    //Si hubo error entonces regresa
                    if(sCarp==null)
                        return;

                    /*Si el directorio de facturas no existe entonces crea la carpeta*/                    
                    String sRutPDF          = sCarp + "\\Cancelados";                          
                    if(!new File(sRutPDF).exists())
                        new File(sRutPDF).mkdir();

                    /*Si no existe la carpeta de la empresa entonces crea la carpeta*/
                    sRutPDF                 += "\\" + Login.sCodEmpBD;
                    if(!new File(sRutPDF).exists())
                        new File(sRutPDF).mkdir();
                    
                    /*Completa la ruta al PDF*/
                    sRutPDF                 += "\\CA-" + sNoSer + "-" + sFol + "-" + sRFCLoc + ".pdf";
                    
                    /*Si se tiene que guardar el PDF en disco entonces exportalo al disco*/
                    if(bSiPDFFi)
                    {
                        try
                        {
                            JasperExportManager.exportReportToPdfFile(pr, sRutPDF);
                        }
                        catch(JRException expnJASR)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                            return;                                                                                                                    
                        }
                    }                        
                    
                    //Cierra la base de datos
                    Star.iCierrBas(con);                                            
                }
            }).start();
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                            
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Función para cargar todos los elementos en la tabla*/
        (new Thread()
        {
            @Override
            public void run()
            {
                vCargT();
            }
        }).start();
                
        /*Mensaje de éxito en caso de que alla habido una modifcación*/
        if(bSi)
            JOptionPane.showMessageDialog(null, "Exito en la cancelación(es) de la(s) venta(s).", "Cancelación Exitosa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBCaActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de buscar*/
    private void jTBuscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTBusc.setSelectionStart(0);jTBusc.setSelectionEnd(jTBusc.getText().length());        
        
    }//GEN-LAST:event_jTBuscFocusGained
       
    
    /*Cuando se presiona una tecla en el campo de buscar*/
    private void jTBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscKeyPressed

        /*Si se presionó enter entonces presiona el botón de búsqueda y regresa*/
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            jBBusc.doClick();
            return;
        }
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jTBuscKeyPressed

    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBBuscKeyPressed
    
    
    /*Cuando se presiona una tecla en el botón de mostrar todo*/
    private void jBMosTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMosTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBMosTKeyPressed
    
    
    /*Función para cargar nuevamente los elementos en la tabla*/
    private synchronized void vCargT()
    {        
        /*Vuelve a poner el foco del teclado en el campo de buscar*/
        jTBusc.grabFocus();        
        
        /*Agrega todos los datos de la base de datos a la tabla de vtas*/
        (new Thread()
        {
            @Override
            public void run()
            {
                Star.vCargVtas("", jLNot, jTab1, jLVtasTot);                  
            }
        }).start();
    }
    
    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed
        
        /*Función para cargar todos los elementos en la tabla*/
        vCargT();
        
    }//GEN-LAST:event_jBMosTActionPerformed

    
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

        /*Crea el token*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(jTBusc.getText().replace(" ", ""),",");
                
        /*Recorre todos los tokens para crear la condinción de búsqueda*/
        String sCond    = "";
        while(stk.hasMoreElements())
        {
            /*Obtiene el token*/
            String sTok = stk.nextToken().toLowerCase();
            
            /*Si tiene cliente entonces completa la consulta*/
            if(sTok.startsWith("cli="))                                                        
                sCond   += " AND REPLACE(vtas.CODEMP, ' ', '' ) = '" + sTok.toLowerCase().replace("cli=", "") + "'";                        
            /*Else if tiene nombre entonces completa la consulta*/
            else if(sTok.startsWith("nom="))
                sCond   += " AND REPLACE(emps.NOM, ' ', '' ) LIKE('%" + sTok.toLowerCase().replace("nom=", "").replace(" ", "%") + "%')";            
            /*Else if tiene folio entonces completa la consulta*/
            else if(sTok.startsWith("fol="))
                sCond   += " AND norefer = " + sTok.toLowerCase().replace("fol=", "");
            /*Else if tiene fecha entonces completa la consulta*/
            else if(sTok.startsWith("fecha1="))
                sCond   += " AND femi BETWEEN '" + sTok.toLowerCase().replace("fecha1=", "").replace("fecha2=", " ' AND '") + "' ";
            /*Else if tiene serie documento entonces completa la consulta*/
            else if(sTok.startsWith("serdoc="))
                sCond   += " AND noser = '" + sTok.toLowerCase().replace("serdoc=", "") + "'";
            /*Else if tiene estado del documento entonces completa la consulta*/
            else if(sTok.startsWith("estado="))
                sCond   += " AND vtas.ESTAD = '" + sTok.toLowerCase().replace("estado=", "") + "'";
            /*Else if el documento esta facturado o no entonces completa la consulta*/
            else if(sTok.startsWith("facturado="))
                sCond   += " AND vtas.FACTU = " + sTok.toLowerCase().replace("facturado=", "").replace("si", "1").replace("no", "0");
        }
        
        /*Si no se usaron filtros avanzados entonces la consulta sera libre*/
        if(sCond.compareTo("")==0)
            sCond       = " AND vtas.CODEMP LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR emps.NOM LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR vtas.NOREFER LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR vtas.FEMI LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') ";
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Borra todos los item en la tabla de ventas*/
        DefaultTableModel dm            = (DefaultTableModel)jTab1.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        Star.iContFiVent                  = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;        
        
        /*Comprueba si se tiene habilitado que solo se puedan mostrar las vtas del usuario o todas*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vtasxusr'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Coloca la bandera en caso de que este activado*/
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
        
        //Contador del total de las ventas
        double  dCont    = 0;
        
        /*Si se tiene habilitado que solo se muestren las ventas de el usuario entonces la consulta será otra*/
        if(bSi)
            sQ = "SELECT vtas.NOSER, vtas.CODEMP, vtas.TOTCOST, CASE WHEN vtas.TIMBR = 1 THEN 'Si' ELSE 'No' END timbr, vtas.TOTDESCU, estacs.NOM, vtas.NOTCRED, vtas.FVENC, vtas.TIPDOC, vtas.TIC, vtas.OBSERV, vtas.NOCAJ, vtas.SUCU, vtas.NOSER, vtas.VTA, vtas.NOREFER, vtas.TOT, vtas.FALT, vtas.FEMI, vtas.FMOD, vtas.ESTAD, vtas.ESTAC, vtas.MOTIV FROM vtas LEFT OUTER JOIN estacs ON estacs.ESTAC = vtas.ESTAC LEFT OUTER JOIN  emps ON CONCAT('',emps,SER, emps.CODEMP) = vtas.CODEMP WHERE vtas.ESTAC = '" + Login.sUsrG + "' " + sCond + " ORDER BY vtas.VTA DESC";
        else
            sQ = "SELECT vtas.NOSER, vtas.CODEMP, vtas.TOTCOST, CASE WHEN vtas.TIMBR = 1 THEN 'Si' ELSE 'No' END timbr, vtas.TOTDESCU, estacs.NOM, vtas.FVENC, vtas.NOTCRED, vtas.TIPDOC, vtas.TIC, vtas.OBSERV, vtas.NOCAJ, vtas.SUCU, vtas.NOSER, vtas.VTA, vtas.NOREFER, vtas.TOT, vtas.FALT, vtas.FEMI, vtas.FMOD, vtas.ESTAD, vtas.ESTAC, vtas.MOTIV FROM vtas LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP) = vtas.CODEMP LEFT OUTER JOIN estacs ON estacs.ESTAC = vtas.ESTAC WHERE  1=1 " + sCond + "  ORDER BY vtas.VTA DESC";
        
        /*Obtiene de la base de datos todos las ventas*/        
        try
        {                              
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene  los totales*/
                String sTot     = rs.getString("vtas.TOT");
                String sTotDesc = rs.getString("totdescu");
                String sTotCost = rs.getString("totcost");
                
                //Ve sumando el total de las ventas
                dCont           = dCont + rs.getDouble("vtas.TOT");
                
                /*Dale formato de moneda a los totales*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sTot);                                
                sTot            = n.format(dCant);
                dCant           = Double.parseDouble(sTotDesc);                                
                sTotDesc        = n.format(dCant);
                dCant           = Double.parseDouble(sTotCost);                                
                sTotCost        = n.format(dCant);
                
                /*Agregalo a la tabla*/
                DefaultTableModel te = (DefaultTableModel)jTab1.getModel();
                Object nu[]= {Star.iContFiVent, rs.getString("vtas.VTA"), rs.getString("vtas.NOREFER"), rs.getString("vtas.NOSER"), rs.getString("codemp"), sTot, sTotDesc, sTotCost, rs.getString("vtas.FALT"), rs.getString("vtas.FEMI"), rs.getString("vtas.FMOD"), rs.getString("vtas.FVENC"), rs.getString("vtas.NOTCRED"), rs.getString("vtas.ESTAD"), rs.getString("vtas.ESTAC"), rs.getString("vtas.MOTIV"), rs.getString("vtas.TIPDOC"), rs.getString("vtas.OBSERV"), rs.getString("vtas.SUCU"), rs.getString("vtas.NOCAJ"), rs.getString("estacs.NOM"), rs.getString("timbr")};
                te.addRow(nu);

                /*Aumenta en uno el contador de filas*/
                ++Star.iContFiVent;                                       
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

        //Dale formato de moneda al total de ventas
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String sTotVtas = n.format(dCont);
        
        //Coloca el total de ventas y el contador igual
        jLVtasTot.setText("# Ventas y total: " + Star.iContFiVent + " - " + sTotVtas);
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se presiona el botón de devlución parcial*/
    private void jBDevPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDevPActionPerformed
        
        /*Si el usuario no a seleccionado una venta para devolución parcial no puede avanzar*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado por lo menos una venta para devolución parcial.", "Devolución Parcial de Venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de facturas y regresa*/
            jTab1.grabFocus();                        
            return;            
        }
                    
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab1.getSelectedRows();      
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Declara las variables para obtener los datos de la venta*/
            String sTipDoc  = "";
            String sEstad   = "";
            String sCli     = "";

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;        
            String      sQ; 

            /*Obtiene algunos datos de la venta*/            
            try
            {
                sQ = "SELECT codemp, tipdoc, estad FROM vtas WHERE vta = " + jTab1.getValueAt(iSel[x], 1).toString();	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {                   
                    sTipDoc     = rs.getString("tipdoc");                                   
                    sEstad      = rs.getString("estad");                                   
                    sCli        = rs.getString("codemp");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                   
            }

            /*Si es cliente mostrador entonces*/
            if(sCli.compareTo(Star.sCliMostG)==0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "No se puede hacer devolcuión parcial al cliente mostrador.", "Devolución Parcial", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            
            /*Si es una nota de crédito entonces*/
            if(sTipDoc.compareTo("NOT")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta " + jTab1.getValueAt(iSel[x], 1).toString() + " es una Nota de Crédito y no se puede devolver parcialmente.", "Nota de Crédito", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                continue;
            }                        
            
            /*Si es una venta que ya fue devuelta entonces*/
            if(sEstad.compareTo("DEV")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Venta: " + jTab1.getValueAt(iSel[x], 1).toString() + " fue por devolución.", "Venta de Devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }

            /*Si la venta ya esta cancelada entonces*/
            if(sEstad.compareTo("CA")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Venta: " + jTab1.getValueAt(iSel[x], 1).toString() + " esta cancelada.", "Venta Cancelada", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }
            
            /*Abre el formulario para devolución parcial*/
            DevPVta dev = new DevPVta(jTab1.getValueAt(iSel[x], 1).toString(), jTab1, jLNot, false);
            dev.setVisible(true);
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                        

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jBDevPActionPerformed

    
    /*Cuamdo se presiona una tecla en el botón de devolución parcial*/    
    private void jBDevPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDevPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDevPKeyPressed

    
    /*Cuando se presiona una tecla en el botón de cancelar*/
    private void jBCaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBCaKeyPressed

            
    
    
    /*Cuando se presiona una tecla en el botón de enviar mail otra vez*/
    private void jBMailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMailKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);  
        
    }//GEN-LAST:event_jBMailKeyPressed

    
    /*Cuando se presiona el botón de enviar mail otra vez*/
    private void jBMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMailActionPerformed
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Obtiene si el usuario tiene correo asociado
        int iRes    = Star.iUsrConfCorr(con, Login.sUsrG);

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;

        //Si no tiene correo asociado entonces solamente mensajea
        if(iRes==0)
        {            
            //Comprueba si la configuración de mostrar el mensaje esta habilitado o no
            int iMosMsj = Star.iMostMsjCorrUsr(con);

            //Si hubo error entonces regresa
            if(iMosMsj==-1)
                return;                        

            //Si tiene que mensajer entonces mensajea
            if(iMosMsj==1)
                JOptionPane.showMessageDialog(null, "No se a definido correo electrónico para el usuario: " + Login.sUsrG + ".", "Correo electrónico",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                                                        
            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            //Regresa
            return;
        }

        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab1.getSelectedRow()==-1)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una factura o nota de crédito para reenviar.", "Reenviar factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la lista de facturas y regresa*/
            jTab1.grabFocus();                        
            return;           
        }
          
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    
        
        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Mensajea y retorna*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
            return;                        
        }  
        
        //Obtiene el RFC de la empresa local
        String sRFCLoc  = Star.sGetRFCLoc(con);

        //Si hubo error entonces regresa
        if(sRFCLoc==null)
            return;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab1.getSelectedRows();
        for(int x = iSel.length - 1; x >= 0; x--)
        {   
            //Declara variables locales
            String sTipDoc  = "";
            String sNoSer   = "";
            String sFol     = "";

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;        
            String      sQ; 

            /*Obtiene algunos datos de la venta*/            
            try
            {                  
                sQ = "SELECT norefer, noser, tipdoc FROM vtas WHERE vta = " + jTab1.getValueAt(iSel[x], 1).toString();
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {
                    sTipDoc     = rs.getString("tipdoc");                   
                    sNoSer      = rs.getString("noser");                   
                    sFol        = rs.getString("norefer");                   
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                    
            }

            /*Determina la ruta de donde se tomara el archivo*/
            String sRu  = "\\Facturas";
            if(sTipDoc.compareTo("NOT")==0)
                sRu     = "\\Notas credito";
            
            /*Si el directorio de facturas no existe entonces crea el directorio*/                    
            String sRutPDF = sCarp + sRu;                          
            if(!new File(sRutPDF).exists())
                new File(sRutPDF).mkdir();

            /*Si no existe la carpeta de la empresa entonces crea el directorio*/
            sRutPDF       += "\\" + Login.sCodEmpBD;
            if(!new File(sRutPDF).exists())
                new File(sRutPDF).mkdir();

            //Si el tipo de documento no es factura o nota de crédito entonces
            if(sTipDoc.compareTo("FAC") != 0 && sTipDoc.compareTo("NOTC")!=0)           
            {
                //Mensajea y continua
                JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(iSel[x], 1).toString() + " no es una factura o nota de crédito.", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;            
            }
            
            /*Almacena el nombe del archivo PDF*/
            String sNombPDF     = "CFDI-" + sRFCLoc + "-" + sNoSer + "-" + sFol + ".pdf";

            /*Almacena el nombe del archivo XML*/
            String sNombXML     = "CFDI-" + sRFCLoc + "-" +sNoSer + "-" + sFol + ".xml";

            /*Crea la ruta al XML*/
            String sRutXML      = sRutPDF + "\\CFDI-" + sRFCLoc + "-" +sNoSer + "-" + sFol + ".xml";                                        

            /*Completa la ruta*/
            String sRut          = sRutPDF + "\\CFDI-" + sRFCLoc + "-" +sNoSer + "-" + sFol + ".pdf";                                                        

            /*Si no existe el archivo PDF entonces*/
            if(!new File(sRut).exists())
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Factura/Nota de crédito:" + sRut + " no existe en " + sRutPDF + ".", "Reenvio Documento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
                continue;
            }

            //Contiene el nombre y el código del cliente
            String sNomb    = "";
            String sCodEmp  = "";
            
            /*Obtiene el código de la empresa y su nombre en base al código de la factura*/            
            try
            {
               sQ = "SELECT vtas.CODEMP, emps.NOM FROM vtas LEFT OUTER JOIN emps ON CONCAT_WS('',emps.SER,emps.CODEMP) = vtas.CODEMP WHERE vta = '" + jTab1.getValueAt(iSel[x], 1).toString() + "' AND norefer = " + jTab1.getValueAt(iSel[x], 2).toString();                   
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    /*Obtiene los resultados*/
                    sCodEmp         = rs.getString("vtas.CODEMP");                                                
                    sNomb           = rs.getString("emps.NOM");                                                
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                    
            }            
         
            /*Determina que tipode documento es*/
            int iTip        = 1;
            if(sTipDoc.compareTo("ORD")==0)
                iTip        = 2;
            
            /*Muestra el formulario para saber de que correo se reenviara la factura*/
            SelCorr c       = new SelCorr(this, sRut, sRutXML, sCodEmp, sFol, sNombPDF, sNombXML, iTip, sNomb);
            c.setVisible(true);
        
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                        
        
        //Cierra la base de datos
        Star.iCierrBas(con);                    
        
    }//GEN-LAST:event_jBMailActionPerformed
    
    
    /*Cuando se presiona el botón de ver PDF*/
    private void jBPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPDFActionPerformed
        
        /*Si el usuario no a seleccionado una venta no puede avanzar*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una venta para ver su PDF.", "Facturas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();           
            return;            
        }

        /*Preguntar al usuario si esta seguro de querer abrir los PDF*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres abrir el(los) PDF?", "PDF", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes== JOptionPane.NO_OPTION || iRes== JOptionPane.CANCEL_OPTION)
            return;                                    
                    
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Obtiene el RFC de la empresa local
        String sRFCLoc  = Star.sGetRFCLoc(con);

        //Si hubo error entonces regresa
        if(sRFCLoc==null)
            return;

        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
            return;                        
        }  
        
        /*Recorre toda la selección del usuario*/
        int iSel[] = jTab1.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {   
            //Declara variables locales
            String sSer     = "";
            String sBuscEn  = "";
            String sFol     = "";            

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;        
            String      sQ; 

            /*Obtiene algunos datos necesarios*/            
            try
            {
                sQ = "SELECT tipdoc, noser, norefer FROM vtas WHERE vta = " + jTab1.getValueAt(iSel[x], 1).toString();                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos obtiene los resultados*/
                if(rs.next())
                {
                    sBuscEn         = rs.getString("tipdoc");
                    sSer            = rs.getString("noser");
                    sFol            = rs.getString("norefer");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                    
            } 
            
            /*Determina donde buscar, si es por ticket entonces búsca en tickets*/
            String sRutF            = "";
            if(sBuscEn.compareTo("TIK")==0)
                sRutF               = sCarp + "\\Tickets\\" + Login.sCodEmpBD +"\\" + sSer + "-" + sFol + ".pdf";
            /*Else, buscara en facturas*/
            else if(sBuscEn.compareTo("FAC")==0)
                sRutF               = sCarp + "\\Facturas\\" + Login.sCodEmpBD + "\\CFDI-" + sRFCLoc + "-" + sSer + "-" + sFol + ".pdf";
            /*Else, buscara en remisiones*/
            else if(sBuscEn.compareTo("REM")==0)
                sRutF               = sCarp + "\\Remisiones\\" + Login.sCodEmpBD + "\\" + sSer + "-" + sFol + ".pdf";
            /*Else, buscara en notas de crédito*/
            else if(sBuscEn.compareTo("NOTC")==0)
                sRutF               = sCarp + "\\Notas credito\\" + Login.sCodEmpBD + "\\CFDI-" + sRFCLoc + "-" + sSer + "-" + sFol + ".pdf";
            
            /*Si no existe el archivo PDF entonces*/
            if(!new File(sRutF).exists())
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta: " + sRutF + " no existe.", "Venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
                continue;
            }

            /*Abre el archivo PDF*/
            try 
            {
                Desktop.getDesktop().open(new File(sRutF));
            } 
            catch(IOException expnIO) 
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                                                                   
            }
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                                                        
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jBPDFActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver factura PDF*/
    private void jBPDFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBPDFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBPDFKeyPressed

    
    /*Cuando se presiona una tecla en la tabla de partidas*/
    private void jTab2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTab2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);      
        
    }//GEN-LAST:event_jTab2KeyPressed

    
    /*Cuando se presiona una tecla en el botón de comprobantes*/
    private void jBDirCFDIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDirCFDIKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
                
    }//GEN-LAST:event_jBDirCFDIKeyPressed

    
    /*Cuando se presiona el botón de comprobantes*/
    private void jBDirCFDIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDirCFDIActionPerformed
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae la carpeta compartida de la aplicación en el servidor de la base de datos*/        
        String sCarp    = "";
        try
        {
            sQ = "SELECT IFNULL(rutap, '') AS rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");                                        
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
        
        /*Si la ruta a las factura no existe entonces crea la ruta*/
        String sRutTikCompro = sCarp + "\\Facturas";
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Si la ruta de la empresa no existe entonces crea la ruta*/
        sRutTikCompro          += "\\" + Login.sCodEmpBD;
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Abre el directorio de comprobantes*/
        try
        {
            Desktop.getDesktop().open(new File(sRutTikCompro));
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                                                                                                                                  
        }        
        
    }//GEN-LAST:event_jBDirCFDIActionPerformed

    
    /*Cuando se presiona una tecla en el botón de directorios de ticket*/
    private void jBDirTickKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDirTickKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDirTickKeyPressed

    
    /*Cuando se presiona el botón de ver el diectorio de los PDF ticket*/
    private void jBDirTickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDirTickActionPerformed
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae la carpeta compartida de la aplicación en el servidor de la base de datos*/        
        String sCarp    = "";
        try
        {
            sQ = "SELECT IFNULL(rutap, '') AS rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");                                        
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
        
        /*Si la ruta a los tickets no existe entonces crea la ruta*/
        String sRutTikCompro = sCarp + "\\Tickets";
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Si la ruta a la empresa no existe entonces crea la ruta*/
        sRutTikCompro      += "\\" + Login.sCodEmpBD;
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
                
        /*Abre el directorio de comprobantes*/
        try
        {
            Desktop.getDesktop().open(new File(sRutTikCompro));
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                                                                          
        }        
       
    }//GEN-LAST:event_jBDirTickActionPerformed

    
    /*Cuando se presiona el botón de actualizar*/
    private void jBActuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActuaActionPerformed

        /*Función para cargar todos los elementos en la tabla*/
        vCargT();

    }//GEN-LAST:event_jBActuaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de actualizar*/
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

    
    /*Cuando se presiona el botón de ver tabla 1*/
    private void jBTab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab1ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab1);       

    }//GEN-LAST:event_jBTab1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver tabla 1*/
    private void jBTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTab1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTab1KeyPressed

    
    /*Cuando se presiona el botón de ver la tabla 2*/
    private void jBTab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab2ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab2);       

    }//GEN-LAST:event_jBTab2ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver tabla 2*/
    private void jBTab2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTab2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTab2KeyPressed

    
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
        jBBusc.setBackground(Star.colOri);
        
        /*Coloca el borde que tenía*/
        jBBusc.setBorder(bBordOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
    /*Cuando se presiona una tecla en el botón de ver directorio de ventas canceladas*/
    private void jBDirCanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDirCanKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDirCanKeyPressed

    
    /*Cuando se presiona el botón de ver directorio de comprobantes cancelados*/
    private void jBDirCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDirCanActionPerformed

        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Trae la carpeta compartida de la aplicación en el servidor de la base de datos*/        
        String sCarp    = "";
        try
        {
            sQ = "SELECT IFNULL(rutap, '') AS rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");                                        
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

        /*Si la ruta a las cancelados no existe entonces crea la ruta*/
        String sRutTikCompro = sCarp + "\\Cancelados";
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Si la ruta de la empresa no existe entonces crea la ruta*/
        sRutTikCompro          += "\\" + Login.sCodEmpBD;
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Abre el directorio de las ventas canceladas*/
        try
        {
            Desktop.getDesktop().open(new File(sRutTikCompro));
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());
        }        
                
    }//GEN-LAST:event_jBDirCanActionPerformed

    
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

    
    /*Cuando se presiona una tecla en el botón de timbrar*/
    private void jBTimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTimKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBTimKeyPressed

    
    /*Cuando se presiona el botón de timbrar*/
    private void jBTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTimActionPerformed
        
        /*Si no ha seleccionado por lo menos un registro de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una factura/nota de crédito para timbrar.", "Timbrar venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();                     
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
        
        /*Si esta en modo prueba entonces*/
        if(!bSiT)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El sistema esta en modo prueba y no puede timbrar.", "Timbrar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
            return;                        
        }             

        /*Preguntar al usuario si esta seguro de querer timbrar la o las facturas*/
        Object[] op = {"Si","No"};
        int iRes    =  JOptionPane.showOptionDialog(this, "¿Seguro que quieres timbrar la(s) factura(s)/nota(s) de credito?", "Timbrar venta(s)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes== JOptionPane.NO_OPTION || iRes== JOptionPane.CANCEL_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;                                    
        }

        /*Recorre toda la selección del usuario*/
        int iSel[] = jTab1.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {   
            /*Declara variables para obtener los datos de la venta*/
            String sTipDoc  = "";
            String sFol     = "";
            String sFDoc    = "";
            String sCli     = "";
            String sNoSer   = "";
            String sTot     = "";
            String sImpue   = "";
            String sSubTot  = "";
            String sMon     = "";
            String sTotDescu= "";
            String sTipCam  = "";
            String sForPag  = "";
            String sTimb    = "";
            String sEstad   = "";
            
            /*Obtiene algunos datos de la venta*/            
            try
            {
                sQ = "SELECT vtas.SUBTOT, vtas.IMPUE, vtas.ESTAD, vtas.NOSER, mons.VAL, codemp, timbr, formpag, totdescu, vtas.MON, tot, noser, tipdoc, norefer, vtas.FALT FROM vtas LEFT OUTER JOIN mons ON mons.MON = vtas.MON WHERE vta = " + jTab1.getValueAt(iSel[x], 1);                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos obtiene los resultados*/
                if(rs.next())
                {
                    sFol            = rs.getString("norefer");
                    sFDoc           = rs.getString("falt");
                    sTipDoc         = rs.getString("tipdoc");                    
                    sTot            = rs.getString("tot");
                    sSubTot         = rs.getString("subtot");
                    sImpue          = rs.getString("impue");
                    sMon            = rs.getString("mon");
                    sTotDescu       = rs.getString("totdescu");
                    sTipCam         = rs.getString("val");
                    sForPag         = rs.getString("formpag");
                    sTimb           = rs.getString("timbr");
                    sCli            = rs.getString("codemp");
                    sNoSer          = rs.getString("noser");
                    sEstad          = rs.getString("estad");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                    
            } 
            
            /*Deja la fecha correcta*/
            sFDoc           = sFDoc.replace(" ", "T");
                                    
            //Declara variables locales
            String sNom     = "";
            String sCtaPred = "";
            
            /*Obtiene el nombre del cliente*/              
            try
            {
                sQ = "SELECT ctapred, nom FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCli + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos obtiene el resultado*/
                if(rs.next())
                {
                    sNom        = rs.getString("nom");
                    sCtaPred    = rs.getString("ctapred");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                    
            } 
            
            /*Si la venta es una remsión entonces*/            
            if(sTipDoc.compareTo("REM")==0)        
            {                
                JOptionPane.showMessageDialog(null, "La Venta con folio: " + jTab1.getValueAt(iSel[x], 2).toString() + " es una remisión.", "Timbrar venta(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }
            
            /*Si la venta es un ticket entonces*/            
            if(sTipDoc.compareTo("TIK")==0)        
            {                
                JOptionPane.showMessageDialog(null, "La venta con folio: " + jTab1.getValueAt(iSel[x], 2).toString() + " es un ticket.", "Timbrar venta(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }
            
            /*Si ya esta timbrada entonces mensajea y continua*/        
            if(sTimb.compareTo("1")==0)        
            {                
                JOptionPane.showMessageDialog(null, "La venta con folio: " + sFol + " ya esta timbrada.", "Timbrar factura(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }                        
            
            //Si el documento esta cancelado entonces
            if(sEstad.compareTo("CA")==0)        
            {                
                JOptionPane.showMessageDialog(null, "La venta con folio: " + sFol + " esta cancelada.", "Timbrar factura(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }                        
            
            //Si el documento esta en devolución parcial entonces mensajea y continua
            if(sEstad.compareTo("DEV")==0)        
            {                
                JOptionPane.showMessageDialog(null, "La venta con folio: " + sFol + " esta devuelta parcialmente.", "Timbrar factura(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }                        
            
            //Declara variables locales
            String sNomLoc      = "";
            String sCallLoc     = "";
            String sTelLoc      = "";
            String sColLoc      = "";
            String sCPLoc       = "";
            String sCiuLoc      = "";
            String sEstLoc      = "";
            String sPaiLoc      = "";
            String sRFCLoc      = "";
            String sNoExtLoc    = "";        
            String sNoIntLoc    = "";
            String sCoLoc       = "";

            /*Obtiene todos los datos de la empresa local*/       
            try
            {                  
                sQ = "SELECT corr, noint, noext, IFNULL(nom, '') AS nom, IFNULL(calle,'') AS calle, IFNULL(tel,'') AS tel, IFNULL(col,'') AS col, IFNULL(cp,'') AS cp, IFNULL(ciu,'') AS ciu, IFNULL(estad,'') AS estad, IFNULL(pai,'') AS pai, IFNULL(rfc, '' ) AS rfc FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
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
                    sRFCLoc             = rs.getString("rfc");                                   
                    sNoExtLoc           = rs.getString("noext");                                   
                    sNoIntLoc           = rs.getString("noint");
                    sCoLoc              = rs.getString("corr");
                }                        
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                    
            }

            /*Determina la forma de pago correcta*/
            if(sForPag.compareTo("C")==0)        
                sForPag     = "Pago en una sola exhibición";        
            else
                sForPag     = "Pago en parcialidades";

            /*Inicialmente no hay error*/
            Star.bErr  = false;
            
            //Inicialmente serán los datos del cliente
            boolean bBusc   = true;
            
            //Si el cliente es cliente público en general entonces
            if(sCli.compareTo(Star.sCliMostG)==0)
            {
                //Coloca la bandera para usar los datos locales y el nombre del cliente será el de publico general
                bBusc   = false;
                sNom    = sNomLoc;
            }
            
            /*Variable final para el thread*/                  
            final String sCallLocFi     = sCallLoc;
            final String sTelLocFi      = sTelLoc;
            final String sColLocFi      = sColLoc;
            final String sCPLocFi       = sCPLoc;
            final String sCiuLocFi      = sCiuLoc;
            final String sEstLocFi      = sEstLoc;
            final String sPaiLocFi      = sPaiLoc;
            final String sRFCLocFi      = sRFCLoc;
            final String sNoExtLocFi    = sNoExtLoc;        
            final String sNoIntLocFi    = sNoIntLoc;
            final String sCoLocFi       = sCoLoc;
            final String sFolFi         = sFol;
            final String sVta           = jTab1.getValueAt(iSel[x], 1).toString();
            final String sFDocFi        = sFDoc;
            final String sTipCamFi      = sTipCam;
            final String sNomEmpFi      = sNom;
            final String sTotFi         = sTot;
            final String sSubTotFi      = sSubTot;
            final String sImpueFi       = sImpue;
            final String sNoSerFi       = sNoSer;
            final String sCtaPredFi     = sCtaPred;
            final String sTotDescuFi    = sTotDescu;
            final String sMonFi         = sMon;
            final String sForPagFi      = sForPag;
            final String sTipDocFi      = sTipDoc;
            final boolean bBuscFi       = bBusc;
            final int    iFil           = iSel[x];
            
            //Muestra el loading
            Star.vMostLoading("");
            
            /*Thread para que haga todo el proceso */
            (new Thread()
            {
                @Override
                public void run() 
                {                                       
                    /*Función para hacer el timbrado y generar PDF y XML*/
                    Star.vGenTim(sTipDocFi.toLowerCase(),"", sFolFi, sVta, "", sFDocFi, sNomEmpFi, sPaiLocFi, sTelLocFi, sCallLocFi, sColLocFi, sCPLocFi, sNoExtLocFi, sNoIntLocFi, sCiuLocFi, sEstLocFi, sRFCLocFi, sCoLocFi, Star.sObLet(sTotFi, sMonFi, "$", true), sSubTotFi, sImpueFi, sTotFi, sNoSerFi, null, null, null, getClass().getResource(Star.sIconDef).toString(), getClass().getResourceAsStream("/jasreport/rptFac.jrxml"), false, false, null, null, null, true, false, false, true, iFil, jTab1, bBuscFi, sMonFi, sTotDescuFi, sForPagFi, "ingreso", sTipCamFi, sCtaPredFi);                                            
                    
                    /*Mensajea de éxito si no hubo error*/
                    if(Star.bErr)
                        JOptionPane.showMessageDialog(null, "No se pudo timbrar venta con folio: " + sFolFi + " Error al timbrar.", "Timbrar factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                }
            }).start();
                                        
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                
        
    }//GEN-LAST:event_jBTimActionPerformed

    
    /*Actualiza la venta para que ya no este asociada a una nota de crédito*/
    private void vActNot(Connection con, String sSerFol)
    {
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 
        
        
        
        /*Actualiza esa venta el campo de notcred para que ya no este asociado*/
        try 
        {                
            sQ = "UPDATE vtas SET notcred = '' WHERE CONCAT_WS('', noser, norefer) = '" + sSerFol + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                           
         }  
                        
    }/*Fin de private void vActNot(Connection con, String sSerFol)*/
                        
                        
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

    
    /*Cuando se presiona una tecla en el botón de generar PDF*/
    private void jBGenPDFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGenPDFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBGenPDFKeyPressed

    
    /*Cuando se presiona el botón de generar PDF de venta*/
    private void jBGenPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGenPDFActionPerformed
        
        /*Si el usuario no a seleccionado por lo menos una venta entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una venta.", "Generar PDF de venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de ventasy regresa*/
            jTab1.grabFocus();                        
            return;            
        }
        
        /*Configura el file chooser para escoger la ruta del directorio donde esta la calculadora*/
        final JFileChooser fc                       = new JFileChooser  ();
        fc.setDialogTitle                           ("Guardar en...");
        fc.setAcceptAllFileFilterUsed               (false);
        fc.setFileSelectionMode                     (JFileChooser.DIRECTORIES_ONLY);

        /*Si el usuario presiono aceptar entonces obtiene la ruta, caso contrario regresa*/
        String sRut;
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
                sRut            = fc.getSelectedFile().getAbsolutePath();   
        else
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
        
        /*Obtiene la ruta completa hacia el logo*/
        String sRutLog      = sCarp + "\\Imagenes\\Logotipo Empresa\\" + Login.sCodEmpBD + "\\Logo.jpg";
        
        /*Si no existe un logo para la empresa entonces será el logo por default del sistema*/
        if(!new File(sRutLog).exists())
            sRutLog             = getClass().getResource(Star.sIconDef).toString();

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
                
        /*Declara variables*/
        String sNomLoc      = "";
        String sCallLoc     = "";
        String sTelLoc      = "";
        String sColLoc      = "";
        String sCPLoc       = "";
        String sCiuLoc      = "";
        String sNoIntLoc    = "";
        String sNoExtLoc    = "";
        String sEstLoc      = "";
        String sPaiLoc      = "";
        String sRFCLoc      = "";
                
        /*Obtiene todos los datos de la empresa local*/
        try
        {                  
            sQ = "SELECT noext, noint, nom, calle, tel, col, cp, ciu, estad, pai, rfc FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
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
                sRFCLoc             = rs.getString("rfc");
                sNoExtLoc           = rs.getString("noext");
                sNoIntLoc           = rs.getString("noint");
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }
        
        /*Declara variables*/
        String sCall    = "";    
        String sTel     = "";    
        String sPai     = "";    
        String sCol     = "";    
        String sCP      = "";    
        String sNoExt   = "";    
        String sNoInt   = "";    
        String sCiu     = "";    
        String sEstad   = "";    
        String sRFC     = "";    
        String sCo1     = "";             
        String sCta     = "";
        String sMetPag  = "";
        String sTot     = "";
        String sMon     = "";
        String sConds   = "";
        String sSubTot  = "";
        String sImpue   = "";
        String sFol     = "";
        String sRegFisc = "";
        String sLugExp  = "";
        String sNoCertSAT= "";
        String sSell    = "";
        String sSellSAT = "";
        String sCadOri  = "";
        String sFolFisc = "";
        String sTipDoc  = "";
        String sCtaPred = "";
        String sNoSer   = "";
        String sNom     = "";
        String sFAlt    = "";
        String sCatGral = "";
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab1.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Obtiene algunos datos del cliente de la venta*/
            try
            {
                sQ = "SELECT catgral, emps.CTAPRED, regfisc, lugexp, certsat, sell, sellsat, cadori, folfisc, vtas.MON, subtot, impue, CASE WHEN vtas.FORMPAG = 'CR' THEN 'Crédito' ELSE 'Contado' END conds, emps.NOM, vtas.FALT, vtas.TIPDOC, vtas.NOREFER, vtas.NOSER, vtas.CTA, vtas.METPAG, tot, calle, tel, col, pai, cp, noext, noint, ciu, emps.ESTAD, rfc, co1 FROM vtas LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP) = vtas.CODEMP WHERE vta = " + jTab1.getValueAt(iSel[x], 1).toString();	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {            
                    sSell       = rs.getString("sell");
                    sRegFisc    = rs.getString("regfisc");
                    sLugExp     = rs.getString("lugexp");
                    sCtaPred    = rs.getString("ctapred");
                    sNoCertSAT  = rs.getString("certsat");
                    sSellSAT    = rs.getString("sellsat");
                    sCadOri     = rs.getString("cadori");
                    sFol        = rs.getString("norefer");                                   
                    sFAlt       = rs.getString("falt");
                    sMon        = rs.getString("mon");
                    sFolFisc    = rs.getString("folfisc");
                    sNom        = rs.getString("nom");                                   
                    sTipDoc     = rs.getString("tipdoc");                                   
                    sNoSer      = rs.getString("noser");                                   
                    sCall       = rs.getString("calle");                                   
                    sCol        = rs.getString("col");                                   
                    sPai        = rs.getString("pai");                                   
                    sCP         = rs.getString("cp");                                   
                    sNoExt      = rs.getString("noext");                                   
                    sNoInt      = rs.getString("noint");                                   
                    sCiu        = rs.getString("ciu");                                   
                    sEstad      = rs.getString("emps.ESTAD");                                   
                    sRFC        = rs.getString("rfc");                                   
                    sCo1        = rs.getString("co1");                                   
                    sTot        = rs.getString("tot");                                   
                    sCta        = rs.getString("vtas.CTA");                                   
                    sMetPag     = rs.getString("vtas.METPAG");                                   
                    sConds      = rs.getString("conds");                                   
                    sSubTot     = rs.getString("subtot");                                   
                    sImpue      = rs.getString("impue");                                   
                    sCatGral    = rs.getString("catgral");
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

            /*Crea la ruta completa para el PDF*/                       
            String sRutTmp              = "";
            if(sTipDoc.compareTo("FAC")==0)                        
                sRutTmp                 =  sRut + "\\CFDI-" + sRFCLoc + "-" + sNoSer + "-" + sFol + ".pdf";
            else if(sTipDoc.compareTo("TIK")==0 || sTipDoc.compareTo("REM")==0)                        
                sRutTmp                 =  sRut + "\\" + sNoSer + "-" + sFol + ".pdf";            
            else if(sTipDoc.compareTo("NOTC")==0)                        
                sRutTmp                 =  sRut + "\\CFDI-" + sRFCLoc + "-" + sNoSer + "-" + sFol + ".pdf";
            
            /*Determina el nombre del reporte*/
            String sTipRep    = "/jasreport/rptFac.jrxml";    
            if(sTipDoc.compareTo("REM")==0)            
                sTipRep       = "/jasreport/rptRem.jrxml";
            else if(sTipDoc.compareTo("NOTC")==0)            
                sTipRep       = "/jasreport/rptNot.jrxml"; 
                
            //Dale formato de moneda a los totales            
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
            double dCant    = Double.parseDouble(sImpue);                
            sImpue          = n.format(dCant);
            dCant           = Double.parseDouble(sTot);                
            sTot            = n.format(dCant);
            dCant           = Double.parseDouble(sSubTot);                
            sSubTot         = n.format(dCant);
            
            /*Declara variables final para el thread*/
            final String sFolFi     = sFol;
            final String sVtaFi     = jTab1.getValueAt(iSel[x], 1).toString();
            final String sSerFi     = sNoSer;
            final String sNomFi     = sNom;
            final String sFechFi    = sFAlt;
            final String sTelFi     = sTel;
            final String sPaiFi     = sPai;
            final String sCallFi    = sCall;
            final String sColFi     = sCol;
            final String sCPFi      = sCP;
            final String sNoExtFi   = sNoExt;
            final String sNoIntFi   = sNoInt;
            final String sCiuFi     = sCiu;
            final String sEstadFi   = sEstad;
            final String sRFCFi     = sRFC;
            final String sCo1Fi     = sCo1;
            final String sSubTotFi  = sSubTot;                
            final String sImpueFi   = sImpue;
            final String sTotFi     = sTot.replace("$", "").replace(",", "");
            final String sMetPagFi  = sMetPag;
            final String sCtaFi     = sCta;
            final String sCondsFi   = sConds;                
            final String sNomLocFi  = sNomLoc;
            final String sCallLocFi = sCallLoc;
            final String sTelLocFi  = sTelLoc;
            final String sColLocFi  = sColLoc;
            final String sCPLocFi   = sCPLoc;
            final String sCiuLocFi  = sCiuLoc;
            final String sNoIntLocFi= sNoIntLoc;
            final String sNoExtLocFi= sNoExtLoc;
            final String sEstLocFi  = sEstLoc;
            final String sMonFi     = sMon;
            final String sSimbFi    = sSimb;
            final String sPaiLocFi  = sPaiLoc;
            final String sRFCLocFi  = sRFCLoc;
            final String sRutLogFi  = sRutLog;
            final String sFolFiscFi = sFolFisc;
            final String sCtaPredFi = sCtaPred;
            final String sSellFi    = sSell;
            final String sSellSATFi = sSellSAT;
            final String sCadOriFi  = sCadOri;
            final String sRutFi     = sRutTmp;
            final String sNoCertSATFi= sNoCertSAT;
            final String sLugExpFi  = sLugExp;
            final String sRegFiscFi = sRegFisc;
            final String sTipRepFi  = sTipRep;
            final String sCatGralFi = sCatGral;
            
            /*Genera el PDF si es factura, remisión o nota de crédito*/
            if(sTipDoc.compareTo("FAC")==0 || sTipDoc.compareTo("REM")==0 || sTipDoc.compareTo("NOTC")==0)
            {            
                /*Genera el o los PDF en un trhead*/
                (new Thread()
                {
                    @Override
                    public void run()
                    {                                                                                          
                        Star.vPDF("", sMonFi, sFolFi, sCatGralFi, sVtaFi, sSerFi, sFechFi, sNomFi, sPaiFi, sTelFi, sCallFi, sColFi, sCPFi, sNoExtFi, sNoIntFi, sCiuFi, sEstadFi, sRFCFi, sCo1Fi, Star.sObLet(sTotFi, sMonFi, sSimbFi, true), sSubTotFi, sImpueFi, sTotFi, sMetPagFi, sCtaFi, sCondsFi, sNomLocFi, sTelLocFi, sColLocFi, sCallLocFi, sCPLocFi, sCiuLocFi, sEstLocFi, sPaiLocFi, sRFCLocFi, sRutLogFi, true, false, false, getClass().getResourceAsStream(sTipRepFi), null, "", sRutFi, 1, true, false, 0, true, "", sFolFiscFi, sSellFi, sSellSATFi, sCadOriFi, sNoIntLocFi, sNoExtLocFi, sNoCertSATFi, sLugExpFi, sRegFiscFi, sCtaPredFi);                                        
                    }
                }).start();
                
            }/*Fin de if(jTab1.getValueAt(iSel[x], 12).toString().compareTo("FAC")==0)*/
            /*Else if es ticket entonces*/
            else if(sTipDoc.compareTo("TIK")==0)   
            {
                /*Genera el o los PDF en un trhead*/
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

                        /*Inicialmente no es de 52mm*/
                        int i52;

                        /*Contiene el reporte que sera*/
                        java.io.InputStream in;

                        //Declara variables de la base de datos
                        Statement   st;
                        ResultSet   rs;                        
                        String      sQ; 

                        /*Obtiene el nombre de la impresora que tiene configurada El usuario actual y si es de corte o no*/
                        String s52  = "";
                        try
                        {
                            sQ = "SELECT imptic, 52m, cort, impfac FROM estacs WHERE estac = '" + Login.sUsrG + "'";                        
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos entonces obtiene el resultado*/
                            if(rs.next())
                                s52             = rs.getString("52m");                
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                            return;                                                                                                                                                
                        }   

                        /*Si es de 52mm entonces*/
                        if(s52.compareTo("1")==0)                        
                        {
                            /*Coloca que será de 52mm*/
                            i52 = 1;

                            /*Si es cliente mostrador entonces llama el tic de mostrador para evitar datos inncesarios, caso contrario el normal*/                    
                            if(sNomFi.trim().compareTo(Star.sNomCliMostG)!=0)                        
                                in    = getClass().getResourceAsStream("/jasreport/rptTickVta52.jrxml");                        
                            /*El reporte será este*/
                            else
                                in    = getClass().getResourceAsStream("/jasreport/rptTickVtaMost52.jrxml");
                        }                    
                        else
                        {
                            /*Coloca la bandera para saber que no es de 52mm*/
                            i52 = 0;

                            /*Si es cliente mostrador entonces llama el tic de mostrador para evitar datos inncesarios, caso contrario el normal*/
                            if(sNomFi.compareTo(Star.sNomCliMostG)!=0)
                                in    = getClass().getResourceAsStream("/jasreport/rptTickVta.jrxml");
                            /*Else será este reporte*/
                            else                                                    
                                in    = getClass().getResourceAsStream("/jasreport/rptTickVtaMost.jrxml");
                        }                    
                        
                        /*Obtiene el símbolo de la moneda*/
                        String sSimb    = "";
                        try
                        {
                            sQ = "SELECT simb FROM mons WHERE mon = '" + sMonFi + "'";
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

                        /*Genera el PDF*/
                        Star.vPDF("", sMonFi, sFolFi, "", sVtaFi, sSerFi, sFechFi, sNomFi, sPaiFi, sTelFi, sCallFi, sColFi, sCPFi, sNoExtFi, sNoIntFi, sCiuFi, sEstadFi, sRFCFi, sCo1Fi, Star.sObLet(sTotFi, sMonFi, sSimb, true), sSubTotFi, sImpueFi, sTotFi, "", "", "", sNomLocFi, sTelLocFi, sColLocFi, sCallLocFi, sCPLocFi, sCiuLocFi, sEstLocFi, sPaiLocFi, sRFCLocFi, sRutLogFi, true, false, false, in, null, "", sRutFi, 2, true, false, i52, true, "", "", "", "", "", "", "", "", "", "", "");                                                                                                                                                                           
                    }
                }).start();
                
            }/*Fin de else if(jTab1.getValueAt(iSel[x], 12).toString().compareTo("TIK")==0)*/
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jBGenPDFActionPerformed

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMosTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMosT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMosTMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGenPDFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGenPDFMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGenPDF.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGenPDFMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCa.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDevMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDevMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDev.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDevMouseEntered


    /*Cuando el mouse entra en el botón específico*/
    private void jBDevPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDevPMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDevP.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDevPMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVerMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVer.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVerMouseEntered

        
    /*Cuando el mouse entra en el botón específico*/
    private void jBMailMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMailMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMail.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMailMouseEntered

        
    /*Cuando el mouse entra en el botón específico*/
    private void jBPDFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPDFMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBPDF.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBPDFMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDirCFDIMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirCFDIMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDirCFDI.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDirCFDIMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDirTickMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirTickMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDirTick.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDirTickMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDirCanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirCanMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDirCan.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDirCanMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBActuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBActua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBActuaMouseEntered

                    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTimMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTimMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTim.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTimMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMosTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMosT.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBMosTMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGenPDFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGenPDFMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGenPDF.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGenPDFMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCa.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBCaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDevMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDevMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDev.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDevMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDevPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDevPMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDevP.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDevPMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVerMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVer.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBVerMouseExited
    
    
    /*Cuando el mouse sale del botón específico*/
    private void jBMailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMailMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMail.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBMailMouseExited

    
    
    /*Cuando el mouse sale del botón específico*/
    private void jBPDFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBPDFMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBPDF.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBPDFMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDirCFDIMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirCFDIMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDirCFDI.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDirCFDIMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDirTickMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirTickMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDirTick.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDirTickMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDirCanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirCanMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDirCan.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDirCanMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBActuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBActua.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBActuaMouseExited
               
    
    /*Cuando el mouse sale del botón específico*/
    private void jBTimMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTimMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTim.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTimMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se presiona una tecla en el botón de ver directorio de remisiones*/
    private void jBRemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBRemKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBRemKeyPressed

    
    /*Cuando el mouse entra en el botón de ver directorio de remisiones*/
    private void jBRemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRemMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBRem.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBRemMouseEntered

    
    /*Cuando el mouse sale del botón de ver directorio de remisiones*/
    private void jBRemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRemMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBRem.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBRemMouseExited

    
    /*Cuando se presiona el botón de ver directorio de remisiones*/
    private void jBRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemActionPerformed
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae la carpeta compartida de la aplicación en el servidor de la base de datos*/        
        String sCarp    = "";
        try
        {
            sQ = "SELECT IFNULL(rutap, '') AS rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");                                        
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

        /*Si la ruta a las factura no existe entonces crea la ruta*/
        String sRutTikCompro = sCarp + "\\Remisiones";
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Si la ruta de la empresa no existe entonces crea la ruta*/
        sRutTikCompro          += "\\" + Login.sCodEmpBD;
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Abre el directorio de comprobantes*/
        try
        {
            Desktop.getDesktop().open(new File(sRutTikCompro));
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                   
        }        
        
    }//GEN-LAST:event_jBRemActionPerformed

    
    /*Cuando se presiona el botón de ver directorio de cortes X*/
    private void jBDirXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDirXActionPerformed
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae la carpeta compartida de la aplicación en el servidor de la base de datos*/        
        String sCarp    = "";
        try
        {
            sQ = "SELECT IFNULL(rutap, '') AS rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");                                        
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

        /*Si la ruta a los cortes no existe entonces crea la ruta*/
        String sRutTikCompro = sCarp + "\\Cortes X";
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Si la ruta de la empresa no existe entonces crea la ruta*/
        sRutTikCompro          += "\\" + Login.sCodEmpBD;
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Abre el directorio de comprobantes*/
        try
        {
            Desktop.getDesktop().open(new File(sRutTikCompro));
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                   
        }        
        
    }//GEN-LAST:event_jBDirXActionPerformed

    
    /*Cuando se presiona el botón de ver el directorio de cortes Z*/
    private void jBDirZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDirZActionPerformed
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae la carpeta compartida de la aplicación en el servidor de la base de datos*/        
        String sCarp    = "";
        try
        {
            sQ = "SELECT IFNULL(rutap, '') AS rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");                                        
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

        /*Si la ruta a los cortes no existe entonces crea la ruta*/
        String sRutTikCompro = sCarp + "\\Cortes Z";
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Si la ruta de la empresa no existe entonces crea la ruta*/
        sRutTikCompro          += "\\" + Login.sCodEmpBD;
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Abre el directorio de comprobantes*/
        try
        {
            Desktop.getDesktop().open(new File(sRutTikCompro));
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                               
        }        
        
    }//GEN-LAST:event_jBDirZActionPerformed

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando se levanta una tecla en el botòn de nuevo*/
    private void jBNewKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyReleased
        
        /*Si se levanto la tecla de control entonces coloca la bandera en false*/
        if(evt.getKeyCode()==KeyEvent.VK_ALT)
            bAltP  = false;
        
    }//GEN-LAST:event_jBNewKeyReleased

    
    /*Cuando se presiona una tecla en el botón de directorio de cortes X*/
    private void jBDirXKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDirXKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDirXKeyPressed

    
    /*Cuando se presiona una tecla en el botón del directorio Z*/
    private void jBDirZKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDirZKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDirZKeyPressed

    
    /*Cuando se presiona una tecla en el botón de generar nueva nota de crédito*/
    private void jBNotCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNotCKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNotCKeyPressed

    
    /*Cuando se presiona el botón de generar nueva nota de crédito*/
    private void jBNotCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNotCActionPerformed
        
        /*Muestra la forma para una nueva nota de crédito*/
        vAbrNewN();                         
                
    }//GEN-LAST:event_jBNotCActionPerformed

    
    /*Cuando el mouse entra en el botón de ver directorio de corte X*/
    private void jBDirXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirXMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDirX.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDirXMouseEntered

    
    /*Cuando el mouse entra en el botón de ver directorio de cortes Z*/
    private void jBDirZMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirZMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDirZ.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDirZMouseEntered

    
    /*Cuando el mouse entra en el botón de generar nueva nota de crédito*/
    private void jBNotCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNotCMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNotC.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNotCMouseEntered

    
    /*Cuando el mouse sale del directorio de cortes X*/
    private void jBDirXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirXMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDirX.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDirXMouseExited

    
    /*Cuando el mouse sale del botón de directorio de cortes Z*/
    private void jBDirZMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirZMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDirZ.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDirZMouseExited

    
    /*Cuando el mouse sale del botón de generar nueva nota de crédito*/
    private void jBNotCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNotCMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNotC.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNotCMouseExited

    
    /*Cuando se presiona una tecla en el botón de directorio de órdenes de compra*/
    private void jBDirNotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDirNotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDirNotKeyPressed

    
    /*Cuando el mouse entra en el botón de directorio de notas de crédito*/
    private void jBDirNotMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirNotMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDirNot.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDirNotMouseEntered

    
    /*Cuando el mouse entra en el botón del directorio de notas de crédito*/
    private void jBDirNotMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirNotMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDirNot.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDirNotMouseExited

    
    /*Cuando se presiona el botón del directorio de las notas de crédito*/
    private void jBDirNotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDirNotActionPerformed
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae la carpeta compartida de la aplicación en el servidor de la base de datos*/        
        String sCarp    = "";
        try
        {
            sQ = "SELECT IFNULL(rutap, '') AS rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");                                        
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
        
        /*Si la ruta a los tickets no existe entonces crea la ruta*/
        String sRutTikCompro = sCarp + "\\Notas credito";
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Si la ruta a la empresa no existe entonces crea la ruta*/
        sRutTikCompro      += "\\" + Login.sCodEmpBD;
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
                
        /*Abre el directorio de comprobantes*/
        try
        {
            Desktop.getDesktop().open(new File(sRutTikCompro));
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                                           
        }        
        
    }//GEN-LAST:event_jBDirNotActionPerformed

    
    /*Cuando se presiona una tecla en el botón de entregar backorder*/
    private void jBEntreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBEntreKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBEntreKeyPressed

    
    /*Cuando el mouse entra en el botón de entregar backorder*/
    private void jBEntreMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEntreMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBEntre.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBEntreMouseEntered

    
    /*Cuando el mouse entra en el botón de entregar backorder*/
    private void jBEntreMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEntreMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBEntre.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBEntreMouseExited

    
    /*Cuando se presiona el botón de entregar backorder*/
    private void jBEntreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEntreActionPerformed
        
        /*Si el usuario no a seleccionado una venta para entregar entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una venta para entregar backorders.", "Backorder", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de facturas y regresa*/
            jTab1.grabFocus();                        
            return;            
        }
                                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables locales
        String sTipDoc  = "";
        String sEstad   = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene algunos datos de la venta*/            
        try
        {
            sQ = "SELECT tipdoc, estad FROM vtas WHERE vta = " + jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString();	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                   
                sTipDoc     = rs.getString("tipdoc");                                   
                sEstad      = rs.getString("estad");                                   
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }

        /*Comprueba si ya se entregarón todas las partidas*/            
        boolean bYa = false;
        try
        {
            sQ = "SELECT SUM(cant) - SUM(cantentre) AS cant FROM partvta WHERE vta = " + jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString();	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {                   
                //Si la cantidad es menor o igual a 0 entonces pon la bandera
                if(rs.getDouble("cant")>0)
                    bYa = true;
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

        /*Si ya se entrego todo entonces*/
        if(!bYa)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString() + " ya esta completamente entregada.", "Backorder", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
            return;
        }                        

        /*Si es una nota de crédito entonces*/
        if(sTipDoc.compareTo("NOTC")== 0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString() + " es una nota de crédito y no se puede entregar backorder.", "Backorder", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
            return;
        }                        

        /*Si es una venta que ya fue devuelta entonces*/
        if(sEstad.compareTo("DEV")== 0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "La Venta: " + jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString() + " fue por devolución.", "Venta de devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }

        /*Si la venta ya esta cancelada entonces*/
        if(sEstad.compareTo("CA")== 0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "La Venta: " + jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString() + " esta cancelada.", "Venta cancelada", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }

        /*Abre la forma una sola vez*/
        if(Star.entBackG==null)
        {            
            Star.entBackG = new EntBack(jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString());
            Star.entBackG.setVisible(true);
        }
        else
        {            
            /*Si ya esta visible entonces traela al frente*/
            if(Star.entBackG.isVisible())            
                Star.entBackG.toFront();
            else
                Star.entBackG.setVisible(true);            
        }                    
                
    }//GEN-LAST:event_jBEntreActionPerformed

    
    /*Cuando se presiona una tecla en el botón de directorio de backorders*/
    private void jBDirBackKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDirBackKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDirBackKeyPressed

    
    /*Cuando el mouse entra en el botón del directorio de backorders*/
    private void jBDirBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirBackMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDirBack.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDirBackMouseEntered

    
    /*Cuando el mouse sale del botón del directorio de backorders*/
    private void jBDirBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirBackMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDirBack.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDirBackMouseExited

    
    /*Cuando se presiona el botón de directorio de backorders*/
    private void jBDirBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDirBackActionPerformed
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae la carpeta compartida de la aplicación en el servidor de la base de datos*/        
        String sCarp    = "";
        try
        {
            sQ = "SELECT IFNULL(rutap, '') AS rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");                                        
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

        /*Si la ruta a las entregas de los backorders no existe entonces crea la ruta*/
        String sRutTikCompro = sCarp + "\\Entregas";
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Si la ruta a la empresa no existe entonces crea la ruta*/
        sRutTikCompro      += "\\" + Login.sCodEmpBD;
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
                
        /*Abre el directorio de comprobantes*/
        try
        {
            Desktop.getDesktop().open(new File(sRutTikCompro));
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                               
        }        
        
    }//GEN-LAST:event_jBDirBackActionPerformed

    
    /*Cuando se presiona una tecla en el botón de facturar*/
    private void jBFacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBFacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBFacKeyPressed

    
    /*Cuando el mouse entra en el botón de facturar*/
    private void jBFacMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFacMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBFac.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBFacMouseEntered

    
    /*Cuando el mouse sale del botón de facturar*/
    private void jBFacMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBFacMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBFac.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBFacMouseExited

    
    /*Cuando se presiona el botón de factura de facturar*/
    private void jBFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFacActionPerformed
        
        /*Si no a seleccionado una venta de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona alguna(s) venta(s) para facturar.", "Facturar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();
            return;
        }
                
        /*Si no a seleccionado una serie entonces*/
        if(jComSer.getSelectedItem().toString().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una serie para facturar.", "Facturar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jComSer.grabFocus();
            return;
        }

        //Si no a seleccionado un cliente entonces
        if(jTCli.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente para facturar.", "Facturar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTCli.grabFocus();
            return;
        }
        
        //Si el cliente no existe entonces
        if(Star.iExistCli(null, jTCli.getText().trim()) == 0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El cliente: " + jTCli.getText().trim() + " no existe.", "Facturar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTCli.grabFocus();
            return;
        }
        
        /*Preguntar al usuario si esta seguro de que querer facturar las ventas*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres facturas la(s) venta(s)?", "Facturar", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Contiene el contador de las ventas a facturar para saber el tamaño del arreglo*/
        int iVtas   = 0;
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Obtiene toda la selección del usuario
        int iSel[]              = jTab1.getSelectedRows();        
        
        //Obtiene el cliente y la serie de la primera venta
        String sCliOri      = sGetCliVta(con, jTab1.getValueAt(iSel[iSel.length - 1], 1).toString());        
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
                
        /*Recorre toda la selección del usuario*/                
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            //Declara variables de la venta
            String sTipDoc  = "";
            String sFactu   = "";
            
            //Obtiene algunas propiedades de la venta                                    
            try
            {
                sQ = "SELECT tipdoc, factu FROM vtas WHERE vta = " + jTab1.getValueAt(iSel[x], 1);                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                //Si hay datos entonces obtiene los resultados es una factura
                if(rs.next())
                {
                    sTipDoc = rs.getString("tipdoc");
                    sFactu  = rs.getString("factu");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                    
            }

            //Si es una factura entonces
            if(sTipDoc.compareTo("FAC")==0)
            {
                //Mensajea y continua
                JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(iSel[x], 1) + " es una factura y no se puede vovler a facturar.", "Facturar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            
            //Si es una nota de crédito entonces
            if(sTipDoc.compareTo("NOT")==0)
            {
                //Mensajea y continua
                JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(iSel[x], 1) + " es una nota de crédito y no se puede facturar.", "Facturar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            
            //Si ya esta facturada entonces
            if(sFactu.compareTo("1")==0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(iSel[x], 1) + " ya esta facturada y no se puede volver a facturar.", "Facturar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            
            /*Ve aumentando el contador para saber el tamaño del arreglo de las ventas*/
            ++iVtas;
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Si no hay nada que facturar entonces regresa*/
        if(iVtas==0)
            return;
        
        /*Crea el arreglo con las ventas para facturarlas*/
        String saVtas[] = new String[iVtas];
                
        //Recorre nuevamente toda la selección del usuario para completar el arreglo de ventas               
        for(int x = iSel.length - 1, v = 0; x >= 0; x--)
        {                                
            /*Completa el arreglo de las ventas y aumenta el contador de las ventas*/
            saVtas[v] = jTab1.getValueAt(iSel[x], 1).toString();
            ++v;                        
        }                

        //Muestra el loading
        Star.vMostLoading("");
        
        //Obtiene la serie del cliente        
        String sSer = Star.strGetSerCli(null, jTCli.getText().trim());
        
        //Si hubo error entonces regresa
        if(sSer == null)
            return;
        
        /*Declara variables final para el thread*/
        final String saVtasFi[]     = saVtas;
        final String sNoSerFi       = jComSer.getSelectedItem().toString();
        final String sCliFi         = jTCli.getText().trim();
        final String sSerFi         = sSer;
                
        /*Comienza a procesar la factura de cierre en un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {
                vFacCier(saVtasFi, sNoSerFi, sCliFi, sSerFi);
            }
            
        }).start();
        
    }//GEN-LAST:event_jBFacActionPerformed

    
    //Función para obtener el cliente de una venta
    private String sGetCliVta(Connection con, String sVta)
    {
        //Declara variables de base de datos
        ResultSet   rs;
        Statement   st;
        String      sQ;
        
        
        
        
        //Obtiene el cliente de esa venta
        String sCli = "";
        try
        {
            sQ = "SELECT codemp FROM vtas WHERE vta = " + sVta;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sCli          = rs.getString("codemp");                                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return null;                                                                                                                                           
        }
        
        //Regresa el cliente
        return sCli;
        
    }//Fin de private String sGetCliVta(Connection con, String sVta)
    
    
    //Función para obtener la serie de un cliente
    private String sGetSerCli(Connection con, String sCli)
    {
        //Declara variables de base de datos
        ResultSet   rs;
        Statement   st;
        String      sQ;
        
        
        
        
        //Obtiene el cliente de esa venta
        String sSer = "";
        try
        {
            sQ = "SELECT ser FROM emps WHERE CONCAT_WS('', codemp, ser) = '" + sCli + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sSer          = rs.getString("codemp");                                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return null;                                                                                                                                            
        }
        
        //Regresa el cliente
        return sSer;
        
    }//Fin de private String sGetSerCli(Connection con, String sCli)
    
    
    /*Cuando se pierde el foco del teclado en el control de la serie de la factura de cierre*/
    private void jComSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComSerFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComSer.getSelectedItem().toString().compareTo("")!=0)
            jComSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComSerFocusLost

    
    /*Cuando sucede una acción en el combo de las series de las facturas*/
    private void jComSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComSerActionPerformed
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "consecs", "WHERE tip = 'FAC' AND ser = '" + jComSer.getSelectedItem() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComSer.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);                   

    }//GEN-LAST:event_jComSerActionPerformed

    
    /*Cuando se presiona una tecla en el combo de las series de las facturas*/
    private void jComSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComSerKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComSerKeyPressed

    
    /*Cuando el mouse entra en el botón de devoluciones parciales*/
    private void jBDirDevPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirDevPMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDirDevP.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDirDevPMouseEntered

    
    /*Cuando el mouse sale del botón de devoluciones parciales*/
    private void jBDirDevPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirDevPMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBDirDevP.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDirDevPMouseExited

    
    /*Cuando se presiona el botón de ver directorio de devoluciones parciales*/
    private void jBDirDevPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDirDevPActionPerformed
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae la carpeta compartida de la aplicación en el servidor de la base de datos*/        
        String sCarp    = "";
        try
        {
            sQ = "SELECT IFNULL(rutap, '') AS rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");                                        
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

        /*Si la ruta a las devoluciones parciales  no existe entonces crea la ruta*/
        String sRutTikCompro = sCarp + "\\Devoluciones Parciales";
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Si la ruta de la empresa no existe entonces crea la ruta*/
        sRutTikCompro          += "\\" + Login.sCodEmpBD;
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Abre el directorio de las ventas canceladas*/
        try
        {
            Desktop.getDesktop().open(new File(sRutTikCompro));
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                                                       
        }        
        
    }//GEN-LAST:event_jBDirDevPActionPerformed

    /*Cuando se presiona una tecla en el botón de directorio de devoluciones parciales*/
    private void jBDirDevPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDirDevPKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDirDevPKeyPressed

    
    /*Cuando el mouse entra en el botón de devoluciones*/
    private void jBDirDevMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirDevMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDirDev.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDirDevMouseEntered

    
    /*Cuando el mouse sale del botón de devoluciones*/
    private void jBDirDevMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirDevMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBDirDev.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDirDevMouseExited

    
    /*Cuando se presiona el botón de directorio de devoluciones*/   
    private void jBDirDevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDirDevActionPerformed
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae la carpeta compartida de la aplicación en el servidor de la base de datos*/        
        String sCarp    = "";
        try
        {
            sQ = "SELECT IFNULL(rutap, '') AS rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");                                        
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

        /*Si la ruta a las devoluciones no existe entonces crea la ruta*/
        String sRutTikCompro = sCarp + "\\Devoluciones";
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Si la ruta de la empresa no existe entonces crea la ruta*/
        sRutTikCompro          += "\\" + Login.sCodEmpBD;
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Abre el directorio de las ventas canceladas*/
        try
        {
            Desktop.getDesktop().open(new File(sRutTikCompro));
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                                                      
        }        
        
    }//GEN-LAST:event_jBDirDevActionPerformed

    
    /*Cuando se presiona una tecla en el botón de dirctorio de devoluciones*/
    private void jBDirDevKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDirDevKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDirDevKeyPressed

    
    /*Cuando se presiona una tecla en el botón de comprobar timbrado*/
    private void jBComproKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBComproKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBComproKeyPressed

    
    /*Cuando el mouse entra en el botón de comprobar timbrado*/
    private void jBComproMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBComproMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCompro.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBComproMouseEntered

    
    /*Cuando el mouse sale del botón de probar timbrado*/
    private void jBComproMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBComproMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBCompro.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBComproMouseExited

    
    /*Cuando se presiona el botón de probar comprobante en timbrado*/
    private void jBComproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBComproActionPerformed
        
        /*Si no ha seleccionado por lo menos un registro de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una factura/nota de crédito para consultar.", "Consultar timbrado", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();                     
            return;
        }
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Preguntar al usuario si esta seguro de querer consultar las facturas*/
        Object[] op = {"Si","No"};
        int iRes    =  JOptionPane.showOptionDialog(this, "¿Seguro que quieres consultar la(s) factura(s)/nota(s) de credito?", "Consultar timbrado", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes== JOptionPane.NO_OPTION || iRes== JOptionPane.CANCEL_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;                                    
        }
        
        //Obtiene el RFC de la empresa local
        String sRFCLoc  = Star.sGetRFCLoc(con);

        //Si hubo error entonces regresa
        if(sRFCLoc==null)
            return;

        /*Recorre toda la selección del usuario*/
        int iSel[] = jTab1.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {   
            /*Declara variables para obtener los datos de la venta*/        
            String sTipDoc  = "";            
            String sTID     = "";
            String sFolFisc = "";

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;        
            String      sQ;                        

            /*Obtiene algunos datos de la venta*/            
            try
            {
                sQ = "SELECT folfisc, tipdoc, transid FROM vtas WHERE vta = " + jTab1.getValueAt(iSel[x], 1).toString().trim();                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos obtiene los resultados*/
                if(rs.next())
                {                   
                    sTipDoc         = rs.getString("tipdoc");
                    sTID            = rs.getString("transid");
                    sFolFisc        = rs.getString("folfisc");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                    
            } 
            
            /*Si el documento no es una factura o nota de crédito entonces*/
            if(sTipDoc.compareTo("FAC")!=0 && sTipDoc.compareTo("NOT")!=0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(iSel[x], 1).toString().trim() + " no es una factura o nota de crédito.", "Consultar timbrado", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            
            //Si el documento no tiene folio fiscal entonces
            if(sFolFisc.compareTo("")==0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(iSel[x], 1).toString().trim() + " no esta timbrada.", "Consultar timbrado", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            
            /*Obtiene el token de sguridad*/
            String sNewTok  = Star.sCreTokEsta(sRFCLoc);

            /*Si hubo error regresa*/
            if(sNewTok==null)
                return;

            /*Tokeniza para obtener el token y el id de transacción*/
            java.util.StringTokenizer stk2 = new java.util.StringTokenizer(sNewTok, "|");
            sNewTok         = stk2.nextToken();
            String sTransId = stk2.nextToken();

            /*Crea el object factory para consultar los timbres*/
            wscance.ObjectFactory facCli = new wscance.ObjectFactory();

            /*Crea la solicitud para cancelación multiple*/
            wstimb.SolicitudEstatusTimbrado solTim = new wstimb.SolicitudEstatusTimbrado();
            solTim.setRFC(facCli.createSolicitudCancelaMultipleRFC(sRFCLoc));
            solTim.setToken(facCli.createSolicitudCancelaMultipleToken(sNewTok));
            solTim.setTransaccionID(Long.parseLong(sTransId));
            solTim.setTransaccionOriginal(Long.parseLong(sTID));

            /*Haz la consulta del timbrado de esta venta*/
            wstimb.RespuestaEstatusTimbrado wsResp;
            try
            {
                /*Consulta el estado con el WS*/
                wsResp  = Star.estatusTimbrado(solTim);
                
                /*Muestra el resultado*/
                JOptionPane.showMessageDialog(null, "Estatus: " + wsResp.getEstatus().getValue().getDescripcion().getValue(), "Estatus", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
            }
            catch(TimbradoEstatusTimbradoFallaServicioFaultFaultMessage | TimbradoEstatusTimbradoFallaSesionFaultFaultMessage | TimbradoEstatusTimbradoFallaValidacionFaultFaultMessage expnWSPAC)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnWSPAC.getMessage(), Star.sErrWSPAC, expnWSPAC.getStackTrace());                
            }
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/
        
    }//GEN-LAST:event_jBComproActionPerformed

    
    /*Cuando se presiona una tecla en el botón de directorio acuses*/
    private void jBDirAcusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDirAcusKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDirAcusKeyPressed

    
    /*Cuando el mouse entra en el botón de directorio acuse*/
    private void jBDirAcusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirAcusMouseEntered
 
        /*Cambia el color del fondo del botón*/
        jBDirAcus.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDirAcusMouseEntered

    
    /*Cuando el mouse sale del botón de directorio de acuse*/
    private void jBDirAcusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirAcusMouseExited
 
        /*Cambia el color del fondo del botón*/
        jBDirAcus.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDirAcusMouseExited

    
    /*Cuando se presiona el botón de directorio de acuses*/
    private void jBDirAcusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDirAcusActionPerformed
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae la carpeta compartida de la aplicación en el servidor de la base de datos*/        
        String sCarp    = "";
        try
        {
            sQ = "SELECT IFNULL(rutap, '') AS rutap FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sCarp          = rs.getString("rutap");                                        
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

        /*Si la ruta a las factura no existe entonces crea la ruta*/
        String sRutTikCompro = sCarp + "\\Acuses";
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Si la ruta de la empresa no existe entonces crea la ruta*/
        sRutTikCompro          += "\\" + Login.sCodEmpBD;
        if(!new File(sRutTikCompro).exists())
            new File(sRutTikCompro).mkdir();
        
        /*Abre el directorio de comprobantes*/
        try
        {
            Desktop.getDesktop().open(new File(sRutTikCompro));
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                                           
        }        
        
    }//GEN-LAST:event_jBDirAcusActionPerformed

    
    /*Cuando se presiona una tecla en el botón de obtener acuse de cancelación*/
    private void jBAcusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAcusKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBAcusKeyPressed

    
    /*Cuando el mouse entra en el botón de obtener acuse de venta*/
    private void jBAcusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAcusMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBAcus.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAcusMouseEntered

    
    /*Cuando el mouse sale del botón de obtener acuse de venta*/
    private void jBAcusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAcusMouseExited
 
        /*Cambia el color del fondo del botón*/
        jBAcus.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBAcusMouseExited

    
    /*Cuando se presiona el botón de obtener acuse de venta*/
    private void jBAcusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAcusActionPerformed
        
        /*Si no ha seleccionado por lo menos un registro de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una factura/nota de crédito para obtener el acuse.", "Obtener acuse(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();                     
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
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }
        
        /*Preguntar al usuario si esta seguro de querer consultar las facturas*/
        Object[] op = {"Si","No"};
        int iRes    =  JOptionPane.showOptionDialog(this, "¿Seguro que quieres obtener el(los) acuse(s)?", "Obtener acuse(s)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes== JOptionPane.NO_OPTION || iRes== JOptionPane.CANCEL_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;                                    
        }
        
        //Obtiene el RFC de la empresa local
        String sRFCLoc  = Star.sGetRFCLoc(con);

        //Si hubo error entonces regresa
        if(sRFCLoc==null)
            return;

        /*Si la carpeta de los acuses no existe entonces crea la carpeta*/
        sCarp                    += "\\Acuses";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Recorre toda la selección del usuario*/
        int iSel[] = jTab1.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {   
            /*Declara variables para obtener los datos de la venta*/        
            String sTipDoc  = "";            
            String sTID     = "";
            String sEstad   = "";
            String sFol     = "";
            String sSerFac  = "";

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;        
            String      sQ;                        

            /*Obtiene algunos datos de la venta*/            
            try
            {
                sQ = "SELECT norefer, noser, tipdoc, folfisc, estad FROM vtas WHERE vta = " + jTab1.getValueAt(iSel[x], 1).toString().trim();                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos obtiene los resultados*/
                if(rs.next())
                {                   
                    sTipDoc         = rs.getString("tipdoc");
                    sTID            = rs.getString("folfisc");
                    sEstad          = rs.getString("estad");
                    sFol            = rs.getString("norefer");
                    sSerFac         = rs.getString("noser");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                    
            } 
            
            /*Si el documento no es una factura o nota de crédito entonces*/
            if(sTipDoc.compareTo("FAC")!=0 && sTipDoc.compareTo("NOT")!=0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(iSel[x], 1).toString().trim() + " no es una factura o nota de crédito.", "Consultar timbrado", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            
            /*Si el documento no esta cancelado entonces*/
            if(sEstad.compareTo("CA")!=0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(iSel[x], 1).toString().trim() + " no esta cancelada.", "Obtener acuse(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            //Si el documento no esta timbrado entonces
            if(sFol.compareTo("")==0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(iSel[x], 1).toString().trim() + " no esta timbrada.", "Obtener acuse(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            
            /*Obtiene el token de sguridad*/
            String sNewTok  = Star.sCreTokEsta(sRFCLoc);
            
            /*Si hubo error regresa*/
            if(sNewTok==null)
                return;

            /*Tokeniza para obtener el token y el id de transacción*/
            java.util.StringTokenizer stk2 = new java.util.StringTokenizer(sNewTok, "|");
            sNewTok         = stk2.nextToken();
            String sTransId = stk2.nextToken();

            /*Crea el object factory para obtener el acuse*/
            wscance.ObjectFactory facCli = new wscance.ObjectFactory();
            
            /*Crea la solicitud para pedi acuse*/
            wscance.SolicitudAcuse solAcu = new wscance.SolicitudAcuse();
            solAcu.setRFC(facCli.createSolicitudAcuseRFC(sRFCLoc));
            solAcu.setToken(facCli.createSolicitudAcuseToken(sNewTok));
            solAcu.setTransaccionID(Long.parseLong(sTransId));
            solAcu.setUUID(sTID);
            
            System.out.println("sip");
            /*Pide al WS el acuse*/
            wscance.RespuestaRecuperarAcuse wsResp;
            try
            {                                
                wsResp  = Star.recuperarAcuses(solAcu);                                                                                
            }
            catch(CancelacionesRecuperarAcusesFallaSesionFaultFaultMessage | CancelacionesRecuperarAcusesFallaServicioFaultFaultMessage | CancelacionesRecuperarAcusesFallaValidacionFaultFaultMessage expnWSPAC)
            {
                //Procesa el error y continua
                Star.iErrProc(this.getClass().getName() + " " + expnWSPAC.getMessage(), Star.sErrWSPAC, expnWSPAC.getStackTrace());                                                       
                continue;                
            }
            
            System.out.println("sip2");
            /*Completa la ruta donde se guardara el acuse*/
            String sRut = sCarp + "\\ACU-" + sRFCLoc + "-" + sSerFac + "-" + sFol + ".xml";
            
            //Crea el archivo XML
            File flFil;
            try
            {
                flFil = new File(sRut);                   
                flFil.createNewFile();                    
            }
            catch(IOException expnIO)
            {                
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                   
                return;                                                                                                                    
            }
            
            /*Escribe en XML en la ruta*/            
            try(FileWriter fw = new FileWriter(flFil.getAbsoluteFile()); BufferedWriter bw = new BufferedWriter(fw))
            {                                                                 
                bw.write(wsResp.getAcuseXML().getValue());                              
            } 
            catch(IOException expnIO) 
            {			
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                return;                                                                                                                    
            }                

        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/
 
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Acuse(s) obtenido(s) con éxito. Puedes verlo(s) presionando el botón de 'Directorio de acuse'", "Acuse", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBAcusActionPerformed

    
    /*Cuando se presiona una tecla en el botón de obtener XML*/
    private void jBXMLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBXMLKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBXMLKeyPressed

    
    /*Cuando el mouse entra en el botón de obtener XML*/
    private void jBXMLMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBXMLMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBXML.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBXMLMouseEntered

    
    /*Cuando el mouse sale del botón de obtener XML*/
    private void jBXMLMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBXMLMouseExited

        /*Cambia el color del fondo del botón*/
        jBXML.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBXMLMouseExited

    
    /*Cuando se presiona el botón de obtener XML*/
    private void jBXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBXMLActionPerformed
        
        /*Si no ha seleccionado por lo menos un registro de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una factura/nota de crédito para obtener el XML.", "XML", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();                     
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
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }
                
        /*Preguntar al usuario si esta seguro de querer consultar las facturas*/
        Object[] op = {"Si","No"};
        int iRes    =  JOptionPane.showOptionDialog(this, "¿Seguro que quieres obtener el(los) XML(s)?", "Obtener XML(s)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes== JOptionPane.NO_OPTION || iRes== JOptionPane.CANCEL_OPTION)
        {
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;                                    
        }
        
        //Obtiene el RFC de la empresa local
        String sRFCLoc  = Star.sGetRFCLoc(con);

        //Si hubo error entonces regresa
        if(sRFCLoc==null)
            return;

        /*Si la carpeta de las facturas no existe entonces crea la carpeta*/
        sCarp                    += "\\Facturas";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Recorre toda la selección del usuario*/
        int iSel[] = jTab1.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {   
            /*Declara variables para obtener los datos de la venta*/        
            String sTipDoc  = "";            
            String sTID     = "";            
            String sFol     = "";            
            String sSerFac  = "";

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;        
            String      sQ;                        

            /*Obtiene algunos datos de la venta*/            
            try
            {
                sQ = "SELECT noser, norefer, tipdoc, folfisc FROM vtas WHERE vta = " + jTab1.getValueAt(iSel[x], 1).toString().trim();                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos obtiene los resultados*/
                if(rs.next())
                {                   
                    sTipDoc         = rs.getString("tipdoc");
                    sTID            = rs.getString("folfisc");                    
                    sFol            = rs.getString("norefer");                    
                    sSerFac         = rs.getString("noser");                    
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                    
            } 
            
            /*Si el documento no es una factura o nota de crédito entonces*/
            if(sTipDoc.compareTo("FAC")!=0 && sTipDoc.compareTo("NOT")!=0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(iSel[x], 1).toString().trim() + " no es una factura o nota de crédito.", "XML", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                continue;
            }
            
            /*Obtiene el token de sguridad*/
            String sNewTok  = Star.sCreTokEsta(sRFCLoc);
            
            /*Si hubo error regresa*/
            if(sNewTok==null)
                return;

            /*Tokeniza para obtener el token y el id de transacción*/
            java.util.StringTokenizer stk2 = new java.util.StringTokenizer(sNewTok, "|");
            sNewTok         = stk2.nextToken();
            String sTransId = stk2.nextToken();

            /*Crea el object factory para obtener el XML*/
            wstimb.ObjectFactory facCli = new wstimb.ObjectFactory();
            
            /*Crea la solicitud para obtener el XML*/
            wstimb.SolicitudObtenerTimbrado solAcu = new wstimb.SolicitudObtenerTimbrado();
            solAcu.setRFC(facCli.createSolicitudObtenerTimbradoRFC(sRFCLoc));
            solAcu.setToken(facCli.createSolicitudObtenerTimbradoToken(sNewTok));
            solAcu.setTransaccionID(Long.parseLong(sTransId));
            solAcu.setUUID(facCli.createSolicitudObtenerTimbradoUUID(sTID));

            /*Pide al WS el XML*/
            wstimb.RespuestaObtenerTimbrado wsResp;
            try
            {                                
                wsResp  = Star.obtenerTimbrado(solAcu);                                                                                
            }
            catch(TimbradoObtenerTimbradoFallaValidacionFaultFaultMessage | TimbradoObtenerTimbradoFallaSesionFaultFaultMessage | TimbradoObtenerTimbradoFallaServicioFaultFaultMessage expnWSPAC)
            {
                //Procesa el error y continua
                Star.iErrProc(this.getClass().getName() + " " + expnWSPAC.getMessage(), Star.sErrWSPAC, expnWSPAC.getStackTrace());                                                       
                continue;                
            }
                        
            /*Completa la ruta donde se guardara el XML*/
            String sRut = sCarp + "\\CFDI-" + sRFCLoc + "-" + sSerFac + "-" + sFol + ".xml";
            
            //Crea el archivo XML
            File flFil;
            try
            {
                flFil = new File(sRut);                   
                flFil.createNewFile();                    
            }
            catch(IOException expnIO)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                return;                                                                                                                    
            }
            
            //Escribe en XML en la ruta
            try(FileWriter fw = new FileWriter(flFil.getAbsoluteFile()); BufferedWriter bw = new BufferedWriter(fw);)
            {                                                 
                bw.write(wsResp.getComprobanteXML().getValue().getDatosXML().getValue());                
            } 
            catch(IOException expnIO) 
            {			
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                       
            }                

        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/
 
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "XML(s) obtenido(s) con éxito. Puedes verlo(s) presionando el botón de 'Directorio de PDF CFDI'", "XML", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBXMLActionPerformed

    
    /*Cuando se presiona el botón de ayuda en la búsqueda avanzada*/
    private void jBAyuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAyuActionPerformed

        /*Muestra la forma de ayuda de búsqueda avanzada*/
        ptovta.filGral f = new ptovta.filGral(this,"AYUDA DE BÚSQUEDA AVANZADA:" + System.getProperty( "line.separator" ) + System.getProperty( "line.separator" )
            + "Las abreviaciones a utilizar son las siguientes:" + System.getProperty( "line.separator" )
            + "1)cli=(cliente)" + System.getProperty( "line.separator" )
            + "2)nom=(nombre del cliente)" + System.getProperty( "line.separator" )
            + "3)fol=(folio)" + System.getProperty( "line.separator" )            
            + "4)fecha1=(fecha inicial de la venta) fecha2=(fecha final de la venta)" + System.getProperty( "line.separator" )            
            + "5)serdoc=(serie del documento)" + System.getProperty( "line.separator" )
            + "6)estado=(estado del documento)" + System.getProperty( "line.separator" )
            + "7)facturado=(documento facturado (si o no))" + System.getProperty( "line.separator" )
            + "Estas abreviaciones se utilizan de la siguiente manera separadas por comas:" + System.getProperty( "line.separator" )
            + "cli=cliente, prod=producto, fecha1= 2015-01-01 fecha2= 2015-01-31 nom=nombre del cliente ...etc." + System.getProperty( "line.separator" )
            + "Ejemplo: cli = 09000, nom= cliente prueba, fol= 4");
        f.setVisible(true);

    }//GEN-LAST:event_jBAyuActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ayuda a busqueda avanzada*/
    private void jBAyuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAyuKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBAyuKeyPressed

    
    /*Cuando cambia algo en el combobox*/
    private void jRadBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadBoxItemStateChanged
        
        /*Si el estado cambio entonces*/
        if(evt.getStateChange()==1)
        {
            /*Tickets*/
            if(evt.getItem().toString().compareTo("Tickets")==0)
            {        
                /*Borra toda la tabla de encebzados y de partidas*/
                DefaultTableModel dm = (DefaultTableModel)jTab1.getModel();
                dm.setRowCount(0);
                dm = (DefaultTableModel)jTab2.getModel();
                dm.setRowCount(0);

                /*Función para cargar solamente tickets en la tabla de enzabezados*/
                (new Thread()
                {
                    @Override
                    public void run()
                    {
                        Star.vCargVtas(" AND tipdoc = 'TIK' ", jLNot, jTab1, jLVtasTot);
                    }
                }).start();
            }
            /*Facturas*/
            else if(evt.getItem().toString().compareTo("Facturas")==0)
            {
                /*Borra toda la tabla de encebzados y de partidas*/
                DefaultTableModel dm = (DefaultTableModel)jTab1.getModel();
                dm.setRowCount(0);
                dm = (DefaultTableModel)jTab2.getModel();
                dm.setRowCount(0);

                /*Función para cargar solamente facturas en la tabla de enzabezados*/
                (new Thread()
                {
                    @Override
                    public void run()
                    {
                        Star.vCargVtas(" AND tipdoc = 'FAC' ", jLNot, jTab1, jLVtasTot);
                    }
                }).start();
            }
            /*Notas de crédito*/
            else if(evt.getItem().toString().compareTo("Notas crédito")==0)
            {
                /*Borra toda la tabla de encebzados y de partidas*/
                DefaultTableModel dm = (DefaultTableModel)jTab1.getModel();
                dm.setRowCount(0);
                dm = (DefaultTableModel)jTab2.getModel();
                dm.setRowCount(0);

                /*Función para cargar solamente notas de crédito en la tabla de enzabezados*/
                (new Thread()
                {
                    @Override
                    public void run()
                    {
                        Star.vCargVtas(" AND tipdoc = 'NOT' ", jLNot, jTab1, jLVtasTot);
                    }
                }).start();
            }
            /*Remisiones*/
            else if(evt.getItem().toString().compareTo("Remisiones")==0)
            {
                /*Borra toda la tabla de encebzados y de partidas*/
                DefaultTableModel dm = (DefaultTableModel)jTab1.getModel();
                dm.setRowCount(0);
                dm = (DefaultTableModel)jTab2.getModel();
                dm.setRowCount(0);

                /*Función para cargar solamente remisiones en la tabla de enzabezados*/
                (new Thread()
                {
                    @Override
                    public void run()
                    {
                        Star.vCargVtas(" AND tipdoc = 'REM' ", jLNot, jTab1, jLVtasTot);
                    }
                }).start();
            }  
            /*Todo*/
            else if(evt.getItem().toString().compareTo("Todo")==0)
            {
                /*Borra toda la tabla de encebzados y de partidas*/
                DefaultTableModel dm = (DefaultTableModel)jTab1.getModel();
                dm.setRowCount(0);
                dm = (DefaultTableModel)jTab2.getModel();
                dm.setRowCount(0);

                /*Función para cargar todo en la tabla de enzabezados*/
                (new Thread()
                {
                    @Override
                    public void run()
                    {
                        Star.vCargVtas("", jLNot, jTab1, jLVtasTot);
                    }
                }).start();
            }
                
        }/*Fin de if(evt.getStateChange()==1)*/
        
    }//GEN-LAST:event_jRadBoxItemStateChanged
    
    
    /*Cuando se presiona una tecla en el COMBOBOX*/
    private void jRadBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRadBoxKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRadBoxKeyPressed

    
    //Cuando el mouse entra en el botón de ver archivos de venta
    private void jBVerArchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVerArchMouseEntered

        /*Cambia el color del fondo del botón*/
        jBVerArch.setBackground(Star.colBot);

    }//GEN-LAST:event_jBVerArchMouseEntered

    
    //Cuando el mouse sale del botón de ver archivos de venta
    private void jBVerArchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVerArchMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBVerArch.setBackground(Star.colOri);

    }//GEN-LAST:event_jBVerArchMouseExited

    
    //Cuando se presiona el botón de ver archivos de venta
    private void jBVerArchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerArchActionPerformed

        /*Si no a seleccionado una venta de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una venta.", "No selecciono venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();
            return;
        }

        /*Obtiene la fila seleccionada*/
        int row         = jTab1.getSelectedRow();
        
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

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Obtiene algunos datos de la venta*/
        String sFol     = "";
        try
        {
            sQ = "SELECT CONCAT_WS('', noser, norefer) AS fol FROM vtas WHERE vta = " + jTab1.getValueAt(row, 1).toString();
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sFol    = rs.getString("fol");
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

        /*Si la carpeta de ventas no existe entonces crea la ruta*/
        sCarp                    += "\\Ventas";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si no existe la carpeta de la empresa entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si no existe la carpeta para la venta entonces creala*/
        sCarp                    += "\\" + sFol;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene archivos entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(row, 1).toString() + " no contiene archivos cargados.", "Ventas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Pon el foco del teclado en la tabla y regresa*/
                jTab1.grabFocus();
                return;
            }
        }

        /*Configura el file chooser para escoger la ruta del archivo de compra que quiere abrir*/
        final JFileChooser fc   = new JFileChooser  (sCarp);
        fc.setDialogTitle                           ("Abrir imágen");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Muestra el file choooser*/
        int iVal                = fc.showOpenDialog(this);

        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(iVal                == JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut               = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut               += "\\" + fc.getSelectedFile().getName();    

            /*Abre el archivo*/
            try
            {
                Desktop.getDesktop().open(new File(sRut));
            }
            catch(IOException expnIO)
            {
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                       
            }

        }/*Fin de if(iVal           == JFileChooser.APPROVE_OPTION) */

    }//GEN-LAST:event_jBVerArchActionPerformed

    
    //Cuando se presiona una tecla en el botón de ver archivos de venta
    private void jBVerArchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVerArchKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBVerArchKeyPressed

    
    //Cuando el mouse entra en el botón de cargar archivos
    private void jBCargMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargMouseEntered

        /*Cambia el color del fondo del botón*/
        jBCarg.setBackground(Star.colBot);

    }//GEN-LAST:event_jBCargMouseEntered

    
    //Cuando el mouse sale del botón de cargar archivos
    private void jBCargMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBCarg.setBackground(Star.colOri);

    }//GEN-LAST:event_jBCargMouseExited

    
    //Cuando se presiona el botón de cargar archivos
    private void jBCargActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargActionPerformed

        /*Si no a seleccionado una venta en la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una venta.", "Cargar venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();
            return;
        }
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Obtén la fila seleccionada*/
        int row         = jTab1.getSelectedRow();

        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Trae el folio de la venta*/
        String sFol     = "";
        try
        {
            sQ = "SELECT CONCAT_WS('', noser, norefer) AS fol FROM vtas WHERE vta = " + jTab1.getValueAt(row, 1).toString();
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sFol    = rs.getString("fol");
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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen de la compra*/
        final JFileChooser fc           = new JFileChooser();
        fc.setDialogTitle               ("Buscar venta");
        fc.setMultiSelectionEnabled     (true);
        fc.setAcceptAllFileFilterUsed   (false);

        /*Muestra el file choooser*/
        int iVal                    = fc.showSaveDialog(this);

        /*Si el usuario presiono aceptar entonces*/
        if(iVal                    == JFileChooser.APPROVE_OPTION)
        {
            /*Preguntar al usuario si esta seguro de que quiere desasociar la ruta*/
            Object[] op = {"Si","No"};
            int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres guardar el(los) archivo(s) de venta?", "Borrar archivo(s) venta", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
            if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                return;

            /*Si la carpeta de las imágenes no existe entonces creala*/
            sCarp                   += "\\Imagenes";
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();

            /*Si no existe la carpeta de las ventas entonces creala*/
            sCarp                    += "\\Ventas";
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();

            /*Si no existe la carpeta de la empresa entonces creala*/
            sCarp                    += "\\" + Login.sCodEmpBD;
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();

            /*Si no existe la carpeta para el folio entonces creala*/
            sCarp                    += "\\" + sFol;
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();

            /*Obtiene toda la selección del usuario*/
            File[] fils             = fc.getSelectedFiles();

            /*Recorre todos los archivos seleccionados*/
            for(File fil: fils)
            {
                /*Completa la ruta final con el nombre del archivo a donde se copiara*/
                String sTmp         = sCarp +  "\\" + fil.getName();  

                /*Si el archivo de compra ya existe entonces*/
                if( new File(sTmp).exists())
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El archivo: " + fil.getAbsolutePath() + " ya existe en: " + System.getProperty( "line.separator" ) + sTmp + ".", "Archivo existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                    /*Pon el foco del teclado en la tabla de compras y regresa*/
                    jTab1.grabFocus();
                    return;
                }

                /*Si el archivo de origén no existe entonces*/
                if( !new File(fil.getAbsolutePath()).exists())
                {
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El archivo no existe: " + System.getProperty( "line.separator" ) + fil.getAbsolutePath() + ".", "Archivo no existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                    /*Pon el foco del teclado en la tabla de compras y regresa*/
                    jTab1.grabFocus();
                    return;
                }

                /*Copia el archivo orgien al destino*/
                try
                {
                    org.apache.commons.io.FileUtils.copyFile(new File(fil.getAbsolutePath()), new File(sTmp));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                                                                                                    
                }

            }/*Fin de for(File fil: fils)*/

            /*Mensaje de éxito*/
            JOptionPane.showMessageDialog(null, "Archivo(s) guardado(s) con éxito para la venta:" + jTab1.getValueAt(row, 1).toString()  + System.getProperty( "line.separator" ) + sCarp + "\".", "Compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

        }/*Fin de if(iVal           == JFileChooser.APPROVE_OPTION) */       

    }//GEN-LAST:event_jBCargActionPerformed

    
    //Cuando se presiona una tecla en el botón de cargar archivos de venta
    private void jBCargKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCargKeyPressed

    
    //Cuando el mouse entra en el botón de borrar archivos de venta
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered

        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);

    }//GEN-LAST:event_jBDelMouseEntered

    
    //Cuando el mouse sale del botón de borrar archivos de venta
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(Star.colOri);

    }//GEN-LAST:event_jBDelMouseExited

    
    //Cuando se presiona el botón de borrar archivos de venta
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed

        /*Si no a seleccionado una venta de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una venta.", "No selecciono venta", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();
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

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Trae el folio de la venta*/
        String sFol     = "";
        try
        {
            sQ = "SELECT CONCAT_WS('', noser, norefer) AS fol FROM vtas WHERE vta = " + jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString();
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces obtiene el resultado
            if(rs.next())
                sFol    = rs.getString("fol");
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

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existe entonces crea la ruta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de ventas no existe entonces crea la carpeta*/
        sCarp                    += "\\Ventas";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si no existe la carpeta de la empresa entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si no existe la carpeta para el folio entonces crea la carpeta*/
        sCarp                    += "\\" + sFol;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La venta: " + jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString() + " no contiene archivos para borrar.", "Ventas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Pon el foco del teclado en la tabla y regresa*/
                jTab1.grabFocus();
                return;
            }
        }

        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen de la compra*/
        final JFileChooser fc           = new JFileChooser(sCarp);
        fc.setDialogTitle               ("Borrar archivo(s)");
        fc.setMultiSelectionEnabled     (true);
        fc.setAcceptAllFileFilterUsed   (false);

        /*Muestra el file choooser*/
        int iVal                = fc.showSaveDialog(this);

        /*Si el usuario presiono aceptar entonces*/
        if(iVal                == JFileChooser.APPROVE_OPTION)
        {
            /*Preguntar al usuario si esta seguro de que quiere desasociar la ruta*/
            Object[] op = {"Si","No"};
            int iRes            = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar el(los) archivo(s) de venta?", "Borrar archivo(s) venta", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
            if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                return;

            /*Obtiene todos los archivos seleccionados*/
            File[] fils  = fc.getSelectedFiles();

            /*Recorre todos los archivos seleccionados y borralos*/
            for(File fil: fils)
                new File(fil.getAbsolutePath()).delete();

            /*Mensaje de éxito*/
            JOptionPane.showMessageDialog(null, "Archivo(s) borrado(s) para venta: " + jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString() + ".", "Ventas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        }

    }//GEN-LAST:event_jBDelActionPerformed

    
    //Cuando se presiona una tecla en el botón de borrar archivos de ventas
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBDelKeyPressed

    
    //Cuando se presiona una tecla en el botón de información de la venta
    private void jBInfoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBInfoKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBInfoKeyPressed

    
    //Cuando el mouse entra en el botón de información de la venta
    private void jBInfoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBInfoMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBInfo.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBInfoMouseEntered

    
    //Cuando el mouse sale del botón de información
    private void jBInfoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBInfoMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBInfo.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBInfoMouseExited

    
    //Cuando se presiona el botón de información
    private void jBInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBInfoActionPerformed
        
        //Si no a seleccionado una venta entonces
        if(jTab1.getSelectedRow()==-1)
        {
            //Mensajea
            JOptionPane.showMessageDialog(null, "Selecciona una venta para actualizar la información del boleto perdido.", "Información", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            //Coloca el foco del teclado en la tabla y regresa
            jTab2.grabFocus();
            return;
        }
        
        //Muestra la forma para actualizar la información de la venta de boleto perdido
        PerdBol p = new PerdBol(null, jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString().trim());
        p.setVisible(true);
        
    }//GEN-LAST:event_jBInfoActionPerformed

    
    //Cuando el mouse entra en el botón del cliente
    private void jBCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseEntered

        /*Cambia el color del fondo del botón*/
        jBCli.setBackground(Star.colBot);

    }//GEN-LAST:event_jBCliMouseEntered

    
    //Cuando el mouse sale del botón del cliente
    private void jBCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBCli.setBackground(Star.colOri);

    }//GEN-LAST:event_jBCliMouseExited

    
    //Cuando se presiona una tecla en el botón del cliente
    private void jBCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCliActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTCli.getText(), 5, jTCli, null, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código de la cliente*/
        jTCli.grabFocus();
        
    }//GEN-LAST:event_jBCliActionPerformed

    
    //Cuando se presiona una tecla en el botón del cliente
    private void jBCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCliKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCliKeyPressed

    
    //Cuando se gana el foco del teclado en el campo del cliente
    private void jTCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCli.setSelectionStart(0);jTCli.setSelectionEnd(jTCli.getText().length());
        
    }//GEN-LAST:event_jTCliFocusGained

    
    //Cuando se presiona una tecla en el campo del cliente
    private void jTCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyPressed

        //Si se presiona la tecla de abajo entonces presiona el botón del cliente
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBCli.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCliKeyPressed

    
    //Cuando se tipea una tecla en el campo del cliente
    private void jTCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en mayùsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTCliKeyTyped
       
                                       
    /*Función para realizar la factura de cierre de varias ventas*/
    private void vFacCier(String saVtas[], String sNoSer, String sCli, String sSer)
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;                    
        
        /*Contiene los totales de las ventas*/
        String sSubTot      = "0";
        String sImpue       = "0";        
        String sTot         = "0";        
        String sTotDescu    = "0";        
        String sTotCost     = "0";
        
        /*Recorre todo el arreglo de las ventas*/
        for(String sVta: saVtas)
        {            
            /*Actualiza la venta que ya fue facturada*/
            Star.vActFac(sVta, con);                                                                                                            

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;        
            String      sQ; 

            /*Obtiene los totales de todas las ventas*/
            try
            {
                sQ = "SELECT subtot, impue, tot, totcost, totdescu FROM vtas WHERE vta = " + sVta;                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces ve sumando los totales*/
                if(rs.next())
                {                    
                    sSubTot         = Double.toString(Double.parseDouble(sSubTot)   + rs.getDouble("subtot"));
                    sImpue          = Double.toString(Double.parseDouble(sImpue)    + rs.getDouble("impue"));
                    sTot            = Double.toString(Double.parseDouble(sTot)      + rs.getDouble("tot"));
                    sTotDescu       = Double.toString(Double.parseDouble(sTotDescu) + rs.getDouble("totdescu"));
                    sTotCost        = Double.toString(Double.parseDouble(sTotCost)  + rs.getDouble("totcost"));                    
                }
            }
            catch(SQLException expnSQL)
            {                
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                    
            }  
            
        }/*Fin de for(String sVta: saVtas)*/                                                
        
        /*Contiene la serie y el consecutivo de la factura*/
        String sConsFac = "";
        String sSerFac  = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene el consecutivo de la factura y la serie*/               
        try
        {
           sQ = "SELECT ser, consec FROM consecs WHERE ser = '" + sNoSer + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {            
                sConsFac      = rs.getString("consec");
                sSerFac       = rs.getString("ser");                                                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }                  

        /*Actualiza el consecutivo de las facturas*/
        try 
        {            
            sQ = "UPDATE consecs SET "
                    + "consec       = consec + 1, "
                    + "sucu         = '" + Star.sSucu + "', "
                    + "nocaj        = '" + Star.sSucu + "' "
                    + "WHERE ser    = '" + sNoSer + "'";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }                        
        
        /*Contiene la moneda nacional y su valor*/
        String sMon     = "";
        String sTipCam  = "";
        
        /*Obtiene la moneda nacional y su valor*/
        try
        {                  
            sQ = "SELECT mon, val FROM mons WHERE mn = 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                                
                sMon        = rs.getString("mon");
                sTipCam     = rs.getString("val");
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }                                       
        
        //Inserta en la base de datos la nueva factura
        if(Star.iInsVtas(con, sSerFac.replace("'", "''"), sConsFac.replace("'", "''"), sCli, sSer, sSubTot, sImpue, sTot, "now()", "now()", "now()", "'CO'", "0", "", "FAC", "0", "EFE", "0000", "FAC VTAS", "0", sTotDescu, "0", "1", sTotCost, Login.sUsrG, sMon, "1", "C", "", "", "", "", "", "", "", "", "", "", "", "", "0", "", "1", "0", "0", "0","")==-1)
            return;
            
        /*Declara variables*/
        String  sFAlt       = "";        
        String  sVta        = "";                    
        String  sTotDesc    = "";               
        
        /*Obtiene algunos datos de la factura*/
        try
        {                  
            sQ = "SELECT vtas.TOTDESCU, vtas.FALT, vta FROM vtas WHERE tipdoc = 'FAC' ORDER BY vta DESC LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                
                sFAlt       = rs.getString("vtas.FALT");                                                                    
                sVta        = rs.getString("vta");                                
                sTotDesc    = rs.getString("totdescu");              
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }                               
        
        //Crea el formato correcto de fecha para el timbrado
        sFAlt   = sFAlt.replace(" ", "T");
        
        //Declara variables locales
        String sCo1     = "";
        String sCtaPred = "";
        
        /*Obtiene el correo del cliente*/        
        try
        {                  
            sQ = "SELECT ctapred, co1 FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCli + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
            {
                sCtaPred    = rs.getString("ctapred");
                sCo1        = rs.getString("co1");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                            
        }                
                
        /*Recorre todo el arreglo de las ventas para insertar todas las partidas de cada venta en la venta nueva*/
        for(String sVta1: saVtas)        
            Star.vInsPrtvta(sVta1, con, sVta);                    
        
        /*Si hubo error entonces*/
        if(Star.bErr)
        {
            /*Resetea la bandera del error*/
            Star.bErr   = false;
            
            //Cierra la base de datos y regresa
            Star.iCierrBas(con);
            return;
        }
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;

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
        
        /*Dale formato de moneda al total para quitar todos los decimales que por eso falla la función de convertir a letra*/        	
        double dCant                    = Double.parseDouble(sTot);                
        NumberFormat n                  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTot                            = n.format(dCant).replace("$", "").replace(",", "");
        
        /*Obtiene el total con letra*/        
        String sTotLet                  = Star.sObLet(sTot, sMon, sSimb, true);      
        
        /*Declara variables locales de la empresa local*/
        String      sNom                = "";
        String      sCall               = "";
        String      sTel                = "";
        String      sPai                = "";
        String      sCol                = "";
        String      sCP                 = "";
        String      sCiu                = "";
        String      sEsta               = "";        
        String      sRFC                = "";
        String      sNoExt              = "";
        String      sNoInt              = "";
        
        //Crea la consulta dependiendo si es cliente mostrador o no
        if(sCli.compareTo(Star.sCliMostG)==0)
            sQ = "SELECT pai, noint, noext, nom, calle, tel, col, cp, ciu, estad, rfc FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
        else
            sQ = "SELECT pai, noint, noext, nom, calle, tel, col, cp, ciu, estad, rfc FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + sCli + "'";
        
        //Obtiene los datos del cliente
        try
        {                              
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                
                sNom                = rs.getString("nom");                                    
                sCall               = rs.getString("calle");                                    
                sTel                = rs.getString("tel");                                    
                sPai                = rs.getString("pai");                                    
                sCol                = rs.getString("col");                                    
                sCP                 = rs.getString("cp");                                    
                sCiu                = rs.getString("ciu");                                    
                sEsta               = rs.getString("estad");                                                    
                sRFC                = rs.getString("rfc");   
                sNoExt              = rs.getString("noext");   
                sNoInt              = rs.getString("noint");                                                                  
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

        /*Dale formato de moneda a los totales*/
        dCant           = Double.parseDouble(sSubTot);        
        sSubTot         = n.format(dCant);
        dCant           = Double.parseDouble(sImpue);        
        sImpue          = n.format(dCant);
        dCant           = Double.parseDouble(sTot);        
        sTot            = n.format(dCant);        
        
        /*Declara variables finales para el thread*/
        final String sConsFacFi = sConsFac;
        final String sVtaFi     = sVta;
        final String sFAltFi    = sFAlt;
        final String sNomFi     = sNom;
        final String sTelFi     = sTel;
        final String sTipCamFi  = sTipCam;
        final String sCallFi    = sCall;
        final String sTotDescFi = sTotDesc;
        final String sColFi     = sCol;
        final String sCPFi      = sCP;
        final String sNoExtFi   = sNoExt;
        final String sPaiFi     = sPai;
        final String sNoIntFi   = sNoInt;
        final String sCiuFi     = sCiu;
        final String sEstaFi    = sEsta;
        final String sRFCFi     = sRFC;
        final String sCo1Fi     = sCo1;
        final String sTotLetFi  = sTotLet;
        final String sSubTotFi  = sSubTot;
        final String sImpFi     = sImpue;
        final String sCtaPredFi = sCtaPred;
        final String sTotFi     = sTot;
        final String sSerFi     = sSerFac;        
        
        /*Thread para quitar carga y todo se haga mas rápido*/
        (new Thread()
        {
            @Override
            public void run()
            {   
                /*Función para hacer el timbrado y generar PDF y XML*/
                Star.vGenTim("fac", "", sConsFacFi, sVtaFi, "", sFAltFi, sNomFi, sPaiFi, sTelFi, sCallFi, sColFi, sCPFi, sNoExtFi, sNoIntFi, sCiuFi, sEstaFi, sRFCFi, sCo1Fi, sTotLetFi, sSubTotFi, sImpFi, sTotFi, sSerFi, "Efectivo", "0000", "", getClass().getResource(Star.sIconDef).toString(), getClass().getResourceAsStream("/jasreport/rptFac.jrxml"), false, false, "", "", "", false, false, false, false, -1, null, false, "PESOS", sTotDescFi, "Pago en una sola exhibición", "ingreso", sTipCamFi, sCtaPredFi);                                            
            }
        }).start();
        
        //Esconde la forma de loading
        Star.vOcultLoadin();

    }/*Fin de private void vFacCier(String saVtas[], String sSer, String sCli, String sSer)*/
    
    
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
            jBCa.doClick();
        /*Si se presiona F2 presiona el botón de devolucion*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBDev.doClick();
        /*Si se presiona F3 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 presiona el boton de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();
        /*Si se presiona F5 presiona el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();            
        /*Si se presiona F8 presioan el boton de mail*/
        else if(evt.getKeyCode() == KeyEvent.VK_F8)
            jBMail.doClick();
        /*Else if se presiona Alt + F presiona el botón de ver PDF*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F)
            jBPDF.doClick();
        /*Si se presiona F11 presiona el botón de devolución parcial*/
        else if(evt.getKeyCode() == KeyEvent.VK_F11)
            jBDevP.doClick();          
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)                    
            jBNew.doClick();                
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBAcus;
    private javax.swing.JButton jBAyu;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBCa;
    private javax.swing.JButton jBCarg;
    private javax.swing.JButton jBCli;
    private javax.swing.JButton jBCompro;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBDev;
    private javax.swing.JButton jBDevP;
    private javax.swing.JButton jBDirAcus;
    private javax.swing.JButton jBDirBack;
    private javax.swing.JButton jBDirCFDI;
    private javax.swing.JButton jBDirCan;
    private javax.swing.JButton jBDirDev;
    private javax.swing.JButton jBDirDevP;
    private javax.swing.JButton jBDirNot;
    private javax.swing.JButton jBDirTick;
    private javax.swing.JButton jBDirX;
    private javax.swing.JButton jBDirZ;
    private javax.swing.JButton jBEntre;
    private javax.swing.JButton jBFac;
    private javax.swing.JButton jBGenPDF;
    private javax.swing.JButton jBInfo;
    private javax.swing.JButton jBMail;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBNotC;
    private javax.swing.JButton jBPDF;
    private javax.swing.JButton jBRem;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTab2;
    private javax.swing.JButton jBTim;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBVer;
    private javax.swing.JButton jBVerArch;
    private javax.swing.JButton jBXML;
    private javax.swing.JComboBox jComSer;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLNot;
    private javax.swing.JLabel jLNotCli;
    private javax.swing.JLabel jLTimb;
    private javax.swing.JLabel jLVtasTot;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JComboBox jRadBox;
    private javax.swing.JScrollPane jSTab1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTCli;
    private javax.swing.JTable jTab1;
    private javax.swing.JTable jTab2;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Empresas extends javax.swing.JFrame */
