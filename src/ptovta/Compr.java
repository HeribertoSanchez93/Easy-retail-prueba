//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Cursor;
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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import static ptovta.Princip.bIdle;




/*Clase que controla las comprs*/
public class Compr extends javax.swing.JFrame 
{
    /*Variable que contiene el borde actual*/
    private Border          bBordOri;
    
    /*Bandera para saber si la tecla de control esta presionada o no*/
    private boolean         bAltP              = false;
    
    /*Contador para modificar tabla*/
    private int             iContCellEd;

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;
    
    /*Declara las variables originales de la tabla 2*/
    private String          sTipOri;
    private String          sProdOri;
    private String          sDescripOri;
    private String          sCantOri;    
    private String          sDevsOri;
    private String          sUnidOri;
    private String          sAlmaOri;
    private String          sCostOri;
    private String          sDescOri;
    private String          sDescAdOri;
    private String          sImpueOri;
    private String          sMonOri;
    private String          sFechOri;    
    private String          sLotOri;    
    private String          sPedimenOri;    
    private String          sCaduOri;    
    private String          sSerProdOri;
    private String          sComenSerOri;
    private String          sGaranSerOri;
    
    /*Declara las variables originales de la tabla 1*/
    private String          sCodOri;
    private String          sNoSerOri;
    private String          sQtyROri;
    private String          sNoDocOri;
    private String          sProvOri;
    private String          sNomProvOri;
    private String          sImpoOri;    
    private String          sFAltOri;
    private String          sFEntOri;
    private String          sFDocOri;
    private String          sUltOri;
    private String          sEstadOri;    
    private String          sMotivOri;
    private String          sOrdOri;
    private String          sAsignOri;
    private String          sPagOri;
    private String          sSucOri;
    private String          sCajOri;
    private String          sEstacOri;
    private String          sNomEstacOri;
    
    //Thread para mostrar las partidas de la compra con retardo
    private Thread          thCargPart;         
    
    
    
    
    /*Constructor sin argumentos*/
    public Compr(java.util.ArrayList<Boolean> camposPermisos) 
    {                                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        //(Des)habilita botones
        jBCan.setEnabled(camposPermisos.get(0));
        jBDev.setEnabled(camposPermisos.get(1));
        jBDevPar.setEnabled(camposPermisos.get(2));
        jBNew.setEnabled(camposPermisos.get(3));
        jBNotC.setEnabled(camposPermisos.get(4));
        jBVer.setEnabled(camposPermisos.get(5));
        if(jBVer.isEnabled()){
            jBCarg.setEnabled(camposPermisos.get(6));
            jBDel.setEnabled(camposPermisos.get(7));
        }
        jBRecOr.setEnabled(camposPermisos.get(8));
        /*Crea el grupo de los radio buttons*/
        javax.swing.ButtonGroup bG = new javax.swing.ButtonGroup();
        bG.add(jRComp);
        bG.add(jROrd);
        bG.add(jRTod);
        
        /*Que las comlumnas de la tabla no se muevan*/
        jTab1.getTableHeader().setReorderingAllowed(false);
        jTab2.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);

        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab1.setAutoResizeMode(0);
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab2.setAutoResizeMode(0);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Compras, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicia el contador de filas en 1 inicialmente*/
        Star.iContFiComp      = 1;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab1.getModel());
        jTab1.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
                
        /*Crea el listener para cuando se cambia de selección en la tabla 1*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                //Si no hay selección entonces regresa
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
                        sTipOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString();
                        sCodOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString();
                        sNoSerOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 3).toString();
                        sNoDocOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 4).toString();
                        sProvOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 5).toString();
                        sNomProvOri     = jTab1.getValueAt(jTab1.getSelectedRow(), 6).toString();
                        sImpoOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 7).toString();
                        sMonOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 8).toString();
                        sFAltOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 9).toString();                        
                        sFDocOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 10).toString();
                        sFEntOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 11).toString();
                        sUltOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 12).toString();
                        sEstadOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 13).toString();
                        sMotivOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 14).toString();
                        sQtyROri        = jTab1.getValueAt(jTab1.getSelectedRow(), 15).toString();
                        sOrdOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 16).toString();
                        sAsignOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 17).toString();
                        sPagOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 18).toString();
                        sSucOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 19).toString();
                        sCajOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 20).toString();
                        sEstacOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 21).toString();
                        sNomEstacOri    = jTab1.getValueAt(jTab1.getSelectedRow(), 22).toString();
                       
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab1.setValueAt(sTipOri,       jTab1.getSelectedRow(), 1);
                        jTab1.setValueAt(sCodOri,       jTab1.getSelectedRow(), 2);                        
                        jTab1.setValueAt(sNoSerOri,     jTab1.getSelectedRow(), 3);                        
                        jTab1.setValueAt(sNoDocOri,     jTab1.getSelectedRow(), 4);                        
                        jTab1.setValueAt(sProvOri,      jTab1.getSelectedRow(), 5);                        
                        jTab1.setValueAt(sNomProvOri,   jTab1.getSelectedRow(), 6);                        
                        jTab1.setValueAt(sImpoOri,      jTab1.getSelectedRow(), 7);                        
                        jTab1.setValueAt(sMonOri,       jTab1.getSelectedRow(), 8);                        
                        jTab1.setValueAt(sFAltOri,      jTab1.getSelectedRow(), 9);                        
                        jTab1.setValueAt(sFDocOri,      jTab1.getSelectedRow(), 10);                        
                        jTab1.setValueAt(sFEntOri,      jTab1.getSelectedRow(), 11);
                        jTab1.setValueAt(sUltOri,       jTab1.getSelectedRow(), 12);                        
                        jTab1.setValueAt(sEstadOri,     jTab1.getSelectedRow(), 13);                        
                        jTab1.setValueAt(sMotivOri,     jTab1.getSelectedRow(), 14);                        
                        jTab1.setValueAt(sQtyROri,      jTab1.getSelectedRow(), 15);
                        jTab1.setValueAt(sOrdOri,       jTab1.getSelectedRow(), 16);
                        jTab1.setValueAt(sAsignOri,     jTab1.getSelectedRow(), 17);
                        jTab1.setValueAt(sPagOri,       jTab1.getSelectedRow(), 18);
                        jTab1.setValueAt(sSucOri,       jTab1.getSelectedRow(), 19);                        
                        jTab1.setValueAt(sCajOri,       jTab1.getSelectedRow(), 20);                        
                        jTab1.setValueAt(sEstacOri,     jTab1.getSelectedRow(), 21);                        
                        jTab1.setValueAt(sNomEstacOri,  jTab1.getSelectedRow(), 22);                        
                        
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
                //Si no hay selección entonces regresa
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
                        sCodOri         = jTab2.getValueAt(jTab2.getSelectedRow(), 1).toString();
                        sProdOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 2).toString();
                        sDescripOri     = jTab2.getValueAt(jTab2.getSelectedRow(), 3).toString();
                        sCantOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 4).toString();
                        sDevsOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 5).toString();
                        sUnidOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 6).toString();
                        sAlmaOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 7).toString();
                        sCostOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 8).toString();
                        sDescOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 9).toString();
                        sDescAdOri      = jTab2.getValueAt(jTab2.getSelectedRow(), 10).toString();
                        sImpueOri       = jTab2.getValueAt(jTab2.getSelectedRow(), 11).toString();                        
                        sImpoOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 13).toString();
                        sFechOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 14).toString();
                        sLotOri         = jTab2.getValueAt(jTab2.getSelectedRow(), 15).toString();
                        sPedimenOri     = jTab2.getValueAt(jTab2.getSelectedRow(), 16).toString();
                        sCaduOri        = jTab2.getValueAt(jTab2.getSelectedRow(), 17).toString();
                        sSerProdOri     = jTab2.getValueAt(jTab2.getSelectedRow(), 19).toString();
                        sComenSerOri    = jTab2.getValueAt(jTab2.getSelectedRow(), 20).toString();
                        sGaranSerOri    = jTab2.getValueAt(jTab2.getSelectedRow(), 21).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab2.setValueAt(sCodOri,           jTab2.getSelectedRow(), 1);                        
                        jTab2.setValueAt(sProdOri,          jTab2.getSelectedRow(), 2);                        
                        jTab2.setValueAt(sDescripOri,       jTab2.getSelectedRow(), 3);                        
                        jTab2.setValueAt(sCantOri,          jTab2.getSelectedRow(), 4);                        
                        jTab2.setValueAt(sDevsOri,          jTab2.getSelectedRow(), 5);                        
                        jTab2.setValueAt(sUnidOri,          jTab2.getSelectedRow(), 6);                        
                        jTab2.setValueAt(sAlmaOri,          jTab2.getSelectedRow(), 7);                        
                        jTab2.setValueAt(sCostOri,          jTab2.getSelectedRow(), 8);                        
                        jTab2.setValueAt(sDescOri,          jTab2.getSelectedRow(), 9);                        
                        jTab2.setValueAt(sDescAdOri,        jTab2.getSelectedRow(), 10);                        
                        jTab2.setValueAt(sImpueOri,         jTab2.getSelectedRow(), 11);                                                
                        jTab2.setValueAt(sImpoOri,          jTab2.getSelectedRow(), 13);                        
                        jTab2.setValueAt(sFechOri,          jTab2.getSelectedRow(), 14);                        
                        jTab2.setValueAt(sLotOri,           jTab2.getSelectedRow(), 15);                        
                        jTab2.setValueAt(sPedimenOri,       jTab2.getSelectedRow(), 16);                        
                        jTab2.setValueAt(sCaduOri,          jTab2.getSelectedRow(), 17);                        
                        jTab2.setValueAt(sSerProdOri,       jTab2.getSelectedRow(), 19);
                        jTab2.setValueAt(sComenSerOri,      jTab2.getSelectedRow(), 20);
                        jTab2.setValueAt(sGaranSerOri,      jTab2.getSelectedRow(), 21);
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla 2*/
        jTab2.addPropertyChangeListener(pro);
        
        /*Pon el foco del teclado en el botón de ingresar nueva compra*/
        jBNew.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla e encabezados*/
        jTab1.getColumnModel().getColumn(0).setPreferredWidth(77);
        jTab1.getColumnModel().getColumn(1).setPreferredWidth(81);
        jTab1.getColumnModel().getColumn(4).setPreferredWidth(130);
        jTab1.getColumnModel().getColumn(5).setPreferredWidth(150);
        jTab1.getColumnModel().getColumn(6).setPreferredWidth(350);
        jTab1.getColumnModel().getColumn(8).setPreferredWidth(150);
        jTab1.getColumnModel().getColumn(9).setPreferredWidth(150);
        jTab1.getColumnModel().getColumn(10).setPreferredWidth(150);
        jTab1.getColumnModel().getColumn(11).setPreferredWidth(150);
        jTab1.getColumnModel().getColumn(12).setPreferredWidth(150);
        jTab1.getColumnModel().getColumn(14).setPreferredWidth(120);
        jTab1.getColumnModel().getColumn(15).setPreferredWidth(120);
        jTab1.getColumnModel().getColumn(16).setPreferredWidth(170);
        jTab1.getColumnModel().getColumn(17).setPreferredWidth(170);
        jTab1.getColumnModel().getColumn(19).setPreferredWidth(150);
        jTab1.getColumnModel().getColumn(22).setPreferredWidth(150);
        
        /*Establece el tamaño de las columnas de la tabla de partidas*/        
        jTab2.getColumnModel().getColumn(3).setPreferredWidth(400);        
        jTab2.getColumnModel().getColumn(4).setPreferredWidth(40);
        jTab2.getColumnModel().getColumn(5).setPreferredWidth(110);
        jTab2.getColumnModel().getColumn(10).setPreferredWidth(110);
        jTab2.getColumnModel().getColumn(11).setPreferredWidth(120);
        jTab2.getColumnModel().getColumn(14).setPreferredWidth(140);
        jTab2.getColumnModel().getColumn(17).setPreferredWidth(140);
        jTab2.getColumnModel().getColumn(18).setPreferredWidth(190);
        jTab2.getColumnModel().getColumn(19).setPreferredWidth(190);
        jTab2.getColumnModel().getColumn(20).setPreferredWidth(190);
        
        /*Activa en las tablas que se usen normamente las teclas de tabulador*/
        jTab1.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab1.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
                        
        /*Declara variables*/
        String sCarp;        

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
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces mensajea*/
        if(sCarp.compareTo("")==0)
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Crea el listener para cuando se cambia de selección en la tabla de compras*/
        jTab1.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {        
                //Si no hay selección entonces regresa
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
                        
                        /*Carga todas las aprtidas de la compra*/
                        vLoadParts();               
                    }
                };      
                thCargPart.start();                                    
            }
        });
        
        /*Agrega todos los datos de la base de datos a la tabla de compras*/
        (new Thread()
        {
            @Override
            public void run()
            {
                Star.vCargComp(jTab1);     
            }
            
        }).start();
        
    }/*Fin de public Compras() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBNew = new javax.swing.JButton();
        jBDev = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab1 = new javax.swing.JTable();
        jBVe = new javax.swing.JButton();
        jBCan = new javax.swing.JButton();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMostT = new javax.swing.JButton();
        jBDevPar = new javax.swing.JButton();
        jBVer = new javax.swing.JButton();
        jBCarg = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jBActua = new javax.swing.JButton();
        jBImp = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
        jBTab2 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jBDirOr = new javax.swing.JButton();
        jBVePDF = new javax.swing.JButton();
        jBRecOr = new javax.swing.JButton();
        jPRadio = new javax.swing.JPanel();
        jRComp = new javax.swing.JRadioButton();
        jRTod = new javax.swing.JRadioButton();
        jROrd = new javax.swing.JRadioButton();
        jLNot = new javax.swing.JLabel();
        jBNotC = new javax.swing.JButton();
        jBGenPDF = new javax.swing.JButton();

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

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre8.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nueva Compra (Ctrl+N). Presionando (Alt y este Botón) puedes Tomar una Compra como Machote para una Nueva");
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 100, 140, 30));

        jBDev.setBackground(new java.awt.Color(255, 255, 255));
        jBDev.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDev.setForeground(new java.awt.Color(0, 102, 0));
        jBDev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/devs.png"))); // NOI18N
        jBDev.setText("Devolución");
        jBDev.setToolTipText("Devolución Completa de Compra Completa (F2)");
        jBDev.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDev.setNextFocusableComponent(jBDevPar);
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
        jP1.add(jBDev, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 40, 140, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Partidas:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 170, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jRComp);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 420, 140, 30));

        jTab1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Tipo", "Código", "Serie", "No. Documento", "Cod.Proveedor", "Nombre Proveedor", "Importe", "Moneda", "Fecha Creación", "Fecha Documento", "Fecha Entrega", "Ultima Modificación", "Estado", "Motivo", "Qty.Recibidas", "Cod.Ordén", "Asignada N.C.", "Pago N.C.", "Sucursal", "No. Caja", "Usuario", "Nombre Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
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
        jScrollPane2.setViewportView(jTab1);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 960, 330));

        jBVe.setBackground(new java.awt.Color(255, 255, 255));
        jBVe.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVe.setForeground(new java.awt.Color(0, 102, 0));
        jBVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ver.png"))); // NOI18N
        jBVe.setText("Ver ");
        jBVe.setToolTipText("Ver Órdenen/Compra(s) (F11)");
        jBVe.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVe.setName(""); // NOI18N
        jBVe.setNextFocusableComponent(jBVer);
        jBVe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVeMouseExited(evt);
            }
        });
        jBVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVeActionPerformed(evt);
            }
        });
        jBVe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVeKeyPressed(evt);
            }
        });
        jP1.add(jBVe, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 160, 140, 30));

        jBCan.setBackground(new java.awt.Color(255, 255, 255));
        jBCan.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCan.setForeground(new java.awt.Color(0, 102, 0));
        jBCan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/can.png"))); // NOI18N
        jBCan.setText("Cancelar");
        jBCan.setToolTipText("Cancelar Venta(s) (Ctrl+SUPR)");
        jBCan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBCan.setName(""); // NOI18N
        jBCan.setNextFocusableComponent(jBDev);
        jBCan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCanMouseExited(evt);
            }
        });
        jBCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCanActionPerformed(evt);
            }
        });
        jBCan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCanKeyPressed(evt);
            }
        });
        jP1.add(jBCan, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 10, 140, 30));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 150, 20));

        jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBusc.setNextFocusableComponent(jBMostT);
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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, 670, 20));

        jBMostT.setBackground(new java.awt.Color(255, 255, 255));
        jBMostT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMostT.setForeground(new java.awt.Color(0, 102, 0));
        jBMostT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMostT.setText("Mostrar F4");
        jBMostT.setNextFocusableComponent(jBCan);
        jBMostT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMostTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMostTMouseExited(evt);
            }
        });
        jBMostT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMostTActionPerformed(evt);
            }
        });
        jBMostT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMostTKeyPressed(evt);
            }
        });
        jP1.add(jBMostT, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 360, 140, 20));

        jBDevPar.setBackground(new java.awt.Color(255, 255, 255));
        jBDevPar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDevPar.setForeground(new java.awt.Color(0, 102, 0));
        jBDevPar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/devpar.png"))); // NOI18N
        jBDevPar.setText("Parcial");
        jBDevPar.setToolTipText("Devolución Parcial de Compra (F8)");
        jBDevPar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDevPar.setNextFocusableComponent(jBNew);
        jBDevPar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDevParMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDevParMouseExited(evt);
            }
        });
        jBDevPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDevParActionPerformed(evt);
            }
        });
        jBDevPar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDevParKeyPressed(evt);
            }
        });
        jP1.add(jBDevPar, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 70, 140, 30));

        jBVer.setBackground(new java.awt.Color(255, 255, 255));
        jBVer.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVer.setForeground(new java.awt.Color(0, 102, 0));
        jBVer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/verarch.png"))); // NOI18N
        jBVer.setText("Ver Archivo(s)");
        jBVer.setToolTipText("Ver archivos asociados a la compra");
        jBVer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVer.setNextFocusableComponent(jBCarg);
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
        jP1.add(jBVer, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 190, 140, 30));

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
        jP1.add(jBCarg, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 220, -1, -1));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setText("Borrar");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jBActua);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 220, 60, 20));

        jTab2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Compra", "Producto", "Descripción", "Qty", "Qty. Recibidas", "Devueltos", "Unidad", "Almacén", "Costo", "Desc.", "Desc. Adicional", "Impuesto", "Importe", "Fecha", "Lote", "Pedimento", "Caducidad", "Serie Producto", "Comentario Serie", "Garantía"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab2.setGridColor(new java.awt.Color(255, 255, 255));
        jTab2.setNextFocusableComponent(jBCan);
        jTab2.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTab2KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab2);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 960, 160));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Órdenes/compras:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 160, -1));

        jBActua.setBackground(new java.awt.Color(255, 255, 255));
        jBActua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBActua.setForeground(new java.awt.Color(0, 102, 0));
        jBActua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actualizar.png"))); // NOI18N
        jBActua.setText("Actualizar");
        jBActua.setToolTipText("Actualizar Registros (F5)");
        jBActua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBActua.setNextFocusableComponent(jBImp);
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
        jP1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 240, 140, 30));

        jBImp.setBackground(new java.awt.Color(255, 255, 255));
        jBImp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBImp.setForeground(new java.awt.Color(0, 102, 0));
        jBImp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impres.png"))); // NOI18N
        jBImp.setText("Imprimir");
        jBImp.setToolTipText("Imprimir Compra(s)  (Ctrl+P)");
        jBImp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBImp.setName(""); // NOI18N
        jBImp.setNextFocusableComponent(jBVePDF);
        jBImp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBImpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBImpMouseExited(evt);
            }
        });
        jBImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImpActionPerformed(evt);
            }
        });
        jBImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBImpKeyPressed(evt);
            }
        });
        jP1.add(jBImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 270, 140, 30));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 10, 20));

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
        jP1.add(jBTab2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 450, 140, 20));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar todo");
        jBTod.setToolTipText("Marcar Todos los Registros en la Tabla (Alt+T)");
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 12, 130, 18));

        jBDirOr.setBackground(new java.awt.Color(255, 255, 255));
        jBDirOr.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDirOr.setForeground(new java.awt.Color(0, 102, 0));
        jBDirOr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/dir.png"))); // NOI18N
        jBDirOr.setText("Órdenes");
        jBDirOr.setToolTipText("Directorio de Órdenes de Compra");
        jBDirOr.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDirOr.setNextFocusableComponent(jBSal);
        jBDirOr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDirOrMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDirOrMouseExited(evt);
            }
        });
        jBDirOr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDirOrActionPerformed(evt);
            }
        });
        jBDirOr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDirOrKeyPressed(evt);
            }
        });
        jP1.add(jBDirOr, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 360, 140, 30));

        jBVePDF.setBackground(new java.awt.Color(255, 255, 255));
        jBVePDF.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVePDF.setForeground(new java.awt.Color(0, 102, 0));
        jBVePDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/pdf.png"))); // NOI18N
        jBVePDF.setText("Ver PDF");
        jBVePDF.setToolTipText("Ver PDF de Compra(s) (Alt+F)");
        jBVePDF.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVePDF.setName(""); // NOI18N
        jBVePDF.setNextFocusableComponent(jBGenPDF);
        jBVePDF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVePDFMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVePDFMouseExited(evt);
            }
        });
        jBVePDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVePDFActionPerformed(evt);
            }
        });
        jBVePDF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVePDFKeyPressed(evt);
            }
        });
        jP1.add(jBVePDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 300, 140, 30));

        jBRecOr.setBackground(new java.awt.Color(255, 255, 255));
        jBRecOr.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBRecOr.setForeground(new java.awt.Color(0, 102, 0));
        jBRecOr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/record.png"))); // NOI18N
        jBRecOr.setText("Recibir orden");
        jBRecOr.setToolTipText("Recibir Orden de Compra");
        jBRecOr.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBRecOr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBRecOrMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBRecOrMouseExited(evt);
            }
        });
        jBRecOr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRecOrActionPerformed(evt);
            }
        });
        jBRecOr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBRecOrKeyPressed(evt);
            }
        });
        jP1.add(jBRecOr, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 390, 140, 30));

        jPRadio.setBackground(new java.awt.Color(255, 255, 255));
        jPRadio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPRadioKeyPressed(evt);
            }
        });
        jPRadio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jRComp.setBackground(new java.awt.Color(255, 255, 255));
        jRComp.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRComp.setText("Compras");
        jRComp.setNextFocusableComponent(jROrd);
        jRComp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRCompActionPerformed(evt);
            }
        });
        jRComp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRCompKeyPressed(evt);
            }
        });
        jPRadio.add(jRComp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 79, -1));

        jRTod.setBackground(new java.awt.Color(255, 255, 255));
        jRTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRTod.setSelected(true);
        jRTod.setText("Todo");
        jRTod.setNextFocusableComponent(jTab1);
        jRTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRTodActionPerformed(evt);
            }
        });
        jRTod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jRTodKeyPressed(evt);
            }
        });
        jPRadio.add(jRTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 60, -1));

        jROrd.setBackground(new java.awt.Color(255, 255, 255));
        jROrd.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jROrd.setText("Órdenes");
        jROrd.setNextFocusableComponent(jRTod);
        jROrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jROrdActionPerformed(evt);
            }
        });
        jROrd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jROrdKeyPressed(evt);
            }
        });
        jPRadio.add(jROrd, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 0, 79, -1));

        jP1.add(jPRadio, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 250, 20));

        jLNot.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLNot.setForeground(new java.awt.Color(204, 0, 0));
        jLNot.setFocusable(false);
        jP1.add(jLNot, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 380, 20));

        jBNotC.setBackground(new java.awt.Color(255, 255, 255));
        jBNotC.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNotC.setForeground(new java.awt.Color(0, 102, 0));
        jBNotC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/notcred.png"))); // NOI18N
        jBNotC.setText("Not.crédito");
        jBNotC.setToolTipText("Nota de crédito de proveedor");
        jBNotC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNotC.setNextFocusableComponent(jBVe);
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
        jP1.add(jBNotC, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 130, 140, 30));

        jBGenPDF.setBackground(new java.awt.Color(255, 255, 255));
        jBGenPDF.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGenPDF.setForeground(new java.awt.Color(0, 102, 0));
        jBGenPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/pdf.png"))); // NOI18N
        jBGenPDF.setText("Generar");
        jBGenPDF.setToolTipText("Generar PDF de documento");
        jBGenPDF.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBGenPDF.setName(""); // NOI18N
        jBGenPDF.setNextFocusableComponent(jBDirOr);
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
        jP1.add(jBGenPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 330, 140, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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


    /*Carga todas las aprtidas de la compra*/
    private void vLoadParts()
    {        
        /*Borra la tabla de partidas*/
        DefaultTableModel dm = (DefaultTableModel)jTab2.getModel();
        dm.setRowCount(0);
        
        /*Obtiene la compra*/
        String sComp    = jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString();
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene las partidas de la compra*/
        try
        {
            sQ = "SELECT partcomprs.KITMAE, partcomprs.DESCRIP, partcomprs.GARAN, comprs.TIP, estado, partcomprs.SERPROD, partcomprs.COMENSER, partcomprs.LOT, partcomprs.PEDIMEN, partcomprs.FLOTVENC, partcomprs.ALMA, partcomprs.RECIB, partcomprs.CODCOM, partcomprs.PROD, partcomprs.CANT, partcomprs.DEVS, partcomprs.UNID, prods.DESCRIP, partcomprs.COST, partcomprs.DESCU, partcomprs.DESCAD, partcomprs.CODIMPUE, IFNULL(partcomprs.IMPO,0) AS impo, partcomprs.FALT FROM partcomprs LEFT OUTER JOIN prods ON prods.PROD = partcomprs.PROD LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM WHERE partcomprs.CODCOM = '" + sComp + "'";                                    
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            int iContFiParts    = 1;
            while(rs.next())
            {                
                /*Si es un kit entonces continua por que no lo tiene que mostrar*/
                if(rs.getString("kitmae").compareTo("1")==0)
                    continue;
                
                /*Obtiene los totes*/
                String sCost    = rs.getString("cost");
                String sImp     = Double.toString(Double.parseDouble(sCost)*Double.parseDouble(rs.getString("cant")));
                
                /*Dales formato de moneda a los totes*/                                
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                double dCant    = Double.parseDouble(sCost);                                
                sCost           = n.format(dCant);
                dCant           = Double.parseDouble(sImp);                                
                sImp            = n.format(dCant);
                
                /*Determina el mensaje para el estado*/
                String sMsj     = "";
                if(rs.getString("estado").compareTo("CO")==0)
                    sMsj        = "CONFIRMADA";
                else if(rs.getString("estado").compareTo("CA")==0)
                    sMsj        = "CANCELADA";
                else if(rs.getString("estado").compareTo("DEV")==0)
                    sMsj        = "DEVOLUCIÓN";
                else if(rs.getString("estado").compareTo("DEVP")==0)
                    sMsj        = "DEVOLUCIÓN PARCIAL";
                else if(rs.getString("estado").compareTo("PE")==0)
                    sMsj        = "PENDIENTE";
                
                /*Coloca en el label el tipo de documento que es*/
                if(rs.getString("tip").compareTo("COMP")==0)
                    jLNot.setText("COMPRA: " + sMsj);
                else if(rs.getString("tip").compareTo("ORD")==0)
                    jLNot.setText("ORDEN DE COMPRA: " + sMsj);                                
                else if(rs.getString("tip").compareTo("NOTP")==0)
                    jLNot.setText("NOTA DE CRÉDITO: " + sMsj);                                
                
                /*Carga los datos en la tabla*/                
                DefaultTableModel te    = (DefaultTableModel)jTab2.getModel();
                Object nu[]             = {iContFiParts, rs.getString("codcom"), rs.getString("prod"), rs.getString("partcomprs.DESCRIP"), rs.getString("cant"), rs.getString("recib"), rs.getString("devs"), rs.getString("unid"), rs.getString("alma"), sCost, rs.getString("descu"), rs.getString("descad"), rs.getString("codimpue"), sImp, rs.getString("falt"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("flotvenc"), rs.getString("serprod"), rs.getString("comenser"), rs.getString("garan")};
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
	        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vLoadParts()*/
                
                
    /*Al presionar el botón de ingresar nueva compra*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed

        /*Si la tecla de alt esta presionada entonces*/
        if(bAltP)
        {
            /*Resetea la bandera*/
            bAltP  = false;     
            
            /*Si no a seleccionado una compra de la tabla entonces*/
            if(jTab1.getSelectedRow()==-1)
            {
                /*Mensajea y coloca el fooco del teclado en la tabla*/
                JOptionPane.showMessageDialog(null, "Selecciona una compra primeramente.", "Nueva compra machote", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));               
                jTab1.grabFocus();                
            }
            
            /*Mostrar el formulario de nuevo ingreso de compra*/
            vAbrComp(jTab1, this, jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString().trim());                                            
        }
        /*Else no esta entonces realiza esta entonces muestra la forma para abrir la nueva compra*/
        else                            
            vAbrComp(jTab1, this, null);                                       
        
    }//GEN-LAST:event_jBNewActionPerformed

        
    /*Método para que se abra una sola vez la forma de compra*/
    private void vAbrComp(javax.swing.JTable jTab, javax.swing.JFrame jFram, String sCod)
    {
        /*Abre la forma de compras una sola vez*/
        if(Star.gCompr==null)
        {            
            Star.gCompr = new IngrCom(jTab, jFram, sCod,"","");
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
    }
    
    
    /*Cuando se presiona el botón de devolución*/
    private void jBDevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDevActionPerformed
           
        /*Si el usuario no a seleccionado una compra para devolución no puede avanzar*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una compra para devolución.", "Devolcuión de Compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de comprs y regresa*/
            jTab1.grabFocus();                        
            return;            
        }

        /*Preguntar al usuario si esta seguro querer hacer la devolución de la compra*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres hacer la devolución?", "Devolución", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Mientras el usuario no escriba un motivo de devolución o cancele la devolución entonces*/
        String sMot;
        do
        {
            /*Pide al usuario el motiv de la devolución de la compra*/
            sMot = JOptionPane.showInputDialog(this ,"Motivo de devolución:", "Motivo", JOptionPane.QUESTION_MESSAGE);

            /*Si el usuario cancelo el cuadro entonces regresa por que no puede continuar*/
            if(sMot == null)
                return;

            /*Si el usuario no escribio un motiv de devolución entonces mensajea*/
            if(sMot.compareTo("")==0)
                JOptionPane.showMessageDialog(null, "Escribe un motivo de devolución.", "Escribir Motivo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                 
        }
        while(sMot.compareTo("")== 0);                
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Variable para saber si hubo por lo menos una modificación*/
        boolean bSi = false;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab1.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            //Declara variables locales
            String sEstad   = "";
            String sTip     = "";
            String sNoSer   = "";
            String sProv    = "";
            String sTot     = "";
            String sSubTot  = "";
            String sImpue   = "";
            String sFVenc   = "";            
            String sFDoc    = "";
            String sSer     = "";

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;        
            String      sQ; 

            /*Obtiene algunos datos necesarios para el proceso*/            
            try
            {
                sQ = "SELECT ser, fdoc, prov, tot, subtot, impue, fvenc, tip, tot, noser, estado FROM comprs WHERE codcomp = '" + jTab1.getValueAt(iSel[x], 2) + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces onbtiene los resultados*/
                if(rs.next())
                {                   
                    sEstad      = rs.getString("estado");                                   
                    sTip        = rs.getString("tip");                                                       
                    sNoSer      = rs.getString("noser");                                                       
                    sProv       = rs.getString("prov");
                    sSer        = rs.getString("ser");
                    sTot        = rs.getString("tot");                                                       
                    sSubTot     = rs.getString("subtot");                                                       
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
                    
            /*Si es una compra que ya fue devuelta entonces*/        
            if(sEstad.compareTo("DEV")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Compra:  " + jTab1.getModel().getValueAt(iSel[x], 2).toString() + " ya esta devuelta.", "Devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                continue;
            }
            
            /*Si es una compra que ya fue cancelada entonces*/        
            if(sEstad.compareTo("CA")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Orden/Compra: " + jTab1.getModel().getValueAt(iSel[x], 2).toString() + " esta cancelada.", "Devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                continue;
            }
            
            /*Si es un órden de compra entonces*/        
            if(sTip.compareTo("ORD")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Orden de Compra: " + jTab1.getModel().getValueAt(iSel[x], 2).toString() + " no se puede devolver.", "Devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                continue;
            }

            /*Si es una órden de compra y esta confirmada entonces entonces*/
            if(sEstad.compareTo("CO")==0 && sTip.compareTo("ORD")==0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Orden de compra: " + jTab1.getValueAt(iSel[x], 2).toString() + " esta confirmada y no se puede devolver.", "Devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }                
            
            /*Contiene el abono de esa compra*/
            String sAbon    = "";            
            
            /*Obtiene el abono de esa compra*/            
            try
            {                  
                sQ = "SELECT IFNULL(SUM(abon),0) AS abon FROM cxp WHERE prov = '" + sProv + "' AND norefer = '" + jTab1.getValueAt(iSel[x], 2).toString().trim() + "' AND noser = '" + sNoSer + "' AND concep = 'ABON COMP'";                
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
            
            /*Si el abono es mayor a 0 entonces*/            
            if(Double.parseDouble(sAbon)>0)
            {
                /*Dale formato de moneda al abono*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                double dCant    = Double.parseDouble(sAbon);                
                sAbon           = n.format(dCant);

                /*Preguntar al usuario si esta seguro de querer continuar*/
                iRes    = JOptionPane.showOptionDialog(this, "La compra: " + jTab1.getValueAt(iSel[x], 2).toString().trim() + " del proveedor: " + sProv + " tiene de abonos: " + sAbon + ". ¿Estas seguro que quieres continuar?", "Cancelar compra", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                    continue;                                                       
            }
            
            //Inicia la transacción
            if(Star.iIniTransCon(con)==-1)
                return;
        
            /*Actualiza la compra para que sea de devolución*/
            try 
            {            
                sQ = "UPDATE comprs SET "
                        + "estado           = 'DEV', "
                        + "motiv            = '" + sMot.replace("'", "''") + "', "
                        + "sucu             = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj            = '" + Star.sNoCaj.replace("'", "''") + "'  "
                        + "WHERE codcomp    = '" + jTab1.getValueAt(iSel[x], 2).toString().replace("'", "''") + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }

            /*Si no es una órden de compra entonces*/
            if(sTip.compareTo("ORD")!=0)
            {
                //Agrega una partida en CXP para agregarle saldo al proveedor de la devolución                           
                if(Star.iInsCXCP(con, "cxp", jTab1.getValueAt(iSel[x], 2).toString(), sNoSer, sProv, sSer, sSubTot, sImpue, sTot, "0", sTot, "'" + sFVenc + "'", "'" + sFDoc + "'", "DEV COMP", "", "0", "", "","")==-1)
                    return;        
                
                /*Recorre todas las partidas de la compra*/
                try
                {                  
                    sQ = "SELECT partcomprs.LOT, partcomprs.PEDIMEN, partcomprs.FLOTVENC, prods.SERVI, partcomprs.ESKIT, comprs.PROV, comprs.NOSER, partcomprs.UNID, partcomprs.PROD, partcomprs.CANT, partcomprs.DESCRIP, partcomprs.ALMA FROM partcomprs LEFT JOIN prods ON prods.PROD = partcomprs.PROD LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM  WHERE codcom = '" + jTab1.getValueAt(iSel[x], 2) + "'";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    while(rs.next())
                    {
                        /*Si el producto es un kit o un servicio que continue por que no se debe devolver*/
                        if(rs.getString("eskit").compareTo("1")==0 || rs.getString("servi").compareTo("1")==0)
                            continue;
                        
                        /*Obtiene la cantidad correcta dependiendo de su unidad*/
                        String sCant    = Star.sCantUnid(rs.getString("unid"), rs.getString("cant"));
                    
                        /*Dale seguimiento al costeo*/
                        if(Star.sGetCost(con, rs.getString("prod"), sCant)==null)
                            return;
                        
                        /*Si la partida fue por lote o pedimento entonces*/
                        if(rs.getString("lot").compareTo("")!=0 || rs.getString("pedimen").compareTo("")!=0)
                        {
                            /*Realiza la afectación a lotes y pedimentos*/
                            if(Star.sLotPed(con, rs.getString("prod"), sCant, rs.getString("alma"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("flotvenc"), "-")==null)
                                return;
                        }                            
                        
                        /*Realiza la afectación correspondiente al almacén para la salida*/
                        if(Star.iAfecExisProd(con, rs.getString("partcomprs.PROD"), rs.getString("alma"), sCant, "-")==-1)
                            return;

                        /*Actualiza la existencia general del producto tadeo*/
                        if(Star.iCalcGralExis(con, rs.getString("partcomprs.PROD"))==-1)
                            return;

                        /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
                        if(!Star.vRegMoniInv(con, rs.getString("partcomprs.PROD"), sCant, rs.getString("partcomprs.DESCRIP"), rs.getString("alma"), Login.sUsrG , jTab1.getValueAt(iSel[x], 2).toString(), "DEV COMP", rs.getString("partcomprs.UNID"), rs.getString("comprs.NOSER"), rs.getString("comprs.PROV"), "1"))                                
                            return;                                                                                                                                                                                                                                                             
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }
                
            }/*Fin de if(sTip.compareTo("ORD")!=0)*/
                  
            //Termina la transacción
            if(Star.iTermTransCon(con)==-1)
                return;

            /*Coloca la bandera para saber que si hubo por lo menos una modificación*/
            bSi = true;
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                                                                                                             
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Vuelve a cargar  todas las compras en la tabla*/
        vCargT(jTab1);
        
        /*Mensaje de éxito en caso de que alla habido una modificación*/
        if(bSi)
            JOptionPane.showMessageDialog(null, "Exito en la devolución de la(s) compra(s).", "Devolución Exitosa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
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
        
        /*Cierra la forma*/
        this.dispose();
        Star.gComprs = null;
        
        
        /*Llama al recolector de basura*/
        System.gc();               
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    
    /*Cuando se presiona un botón en el botón de ver*/
    private void jBVeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBVeKeyPressed

    
    /*Cuando se presiona el botón de ver*/
    private void jBVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVeActionPerformed

        /*Obtiene las filas seleccionadas*/
        int iSel[]              = jTab1.getSelectedRows();
        
        /*Si no se a seleccionado por lo menos una compra entonces*/
        if(iSel.length==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una Orden/Compra para abrir.", "Ver Orden/Compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();                        
            return;
        }
        
        /*Recorre toda la selección del usuario*/                
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Obtiene el código de la compra de la fila que selecciono el usuario*/        
            String sComp       = jTab1.getModel().getValueAt(iSel[x], 2).toString();

            /*Manda llamar el formulario para ver la compra y pasa el código de la compra*/
            VComp v = new VComp(sComp);
            v.setVisible(true);
        }
                                                                    
    }//GEN-LAST:event_jBVeActionPerformed

       
    /*Cuando se presiona el botón de cancelar*/
    private void jBCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCanActionPerformed
        
        /*Si el usuario no a seleccionado una compra para cancelar no puede avanzar*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado por lo menos una Orden/Compra para cancelar.", "Cancelación de Orden/Compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de comprs y regresa*/
            jTab1.grabFocus();            
            return;            
        }
        
        /*Preguntar al usuario si esta seguro querer hacer la cancelación de la compra*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres hacer la cancelación?", "Cancelación de Orden/Compra", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        /*Mientras el usuario no escriba un motivo de cancelación o cancele la cancelación entonces*/
        String sMot;
        do
        {
            /*Pide al usuario el motiv de la cancelación de la compra*/
            sMot     = JOptionPane.showInputDialog(this ,"Motivo de cancelación:", "Motivo", JOptionPane.QUESTION_MESSAGE);
            
            /*Si el usuario cancelo el cuadro entonces regresa por que no puede continuar*/
            if(sMot == null)
                return;

            /*Si el usuario no escribio un motiv de cancelación entonces mensajea*/
            if(sMot.compareTo("")==0)
                JOptionPane.showMessageDialog(null, "Escribe un motivo de cancelación.", "Escribir Motivo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                 
        }
        while(sMot.compareTo("")== 0);                        

        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Bandera para saber si se cancelo algún documento*/
        boolean bSi = false;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab1.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Declara variables*/
            String sEstad   = "";
            String sTip     = "";
            String sTot     = "";
            String sSubTot  = "";
            String sImpue   = "";
            String sNoSer   = "";
            String sProv    = "";
            String sFVenc   = "";
            String sFDoc    = "";
            String sSer     = "";

            //Declara variables de la base de datos
            Statement   st;
            ResultSet   rs;        
            String      sQ; 

            /*Obtiene todos los datos necesarios para el proceso*/                        
            try
            {
                sQ = "SELECT ser, fvenc, fdoc, tot, noser, prov, impue, subtot, estado, tip FROM comprs WHERE codcomp = '" + jTab1.getValueAt(iSel[x], 2).toString().trim() + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces onbtiene los resultados*/
                if(rs.next())
                {                   
                    sEstad      = rs.getString("estado");                                   
                    sTip        = rs.getString("tip");                                                       
                    sSubTot     = rs.getString("subtot");                                   
                    sImpue      = rs.getString("impue");                                   
                    sTot        = rs.getString("tot");                                   
                    sProv       = rs.getString("prov");                                   
                    sNoSer      = rs.getString("noser");                                   
                    sFVenc      = rs.getString("fvenc");                                   
                    sFDoc       = rs.getString("fdoc");
                    sSer        = rs.getString("ser");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
                        
            /*Si es una compra que ya fue cancelada entonces*/        
            if(sEstad.compareTo("CA")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Orden/Compra: " + jTab1.getValueAt(iSel[x], 2).toString() + " ya fue cancelada.", "Orden/Compra Cancelada", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }

            /*Si es una compra que ya fue devuelta en su totalidad entonces*/
            if(sEstad.compareTo("DEV")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Orden/Compra: " + jTab1.getValueAt(iSel[x], 2).toString() + " fue por devolución.", "Cancelación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }                
            
            /*Si es una órden de compra y esta confirmada entonces entonces*/
            if(sEstad.compareTo("CO")==0 && sTip.compareTo("ORD")==0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Orden de compra: " + jTab1.getValueAt(iSel[x], 2).toString() + " esta confirmada y no se puede cancelar.", "Cancelación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                continue;
            }                
            
            /*Contiene el abono de esa compra*/
            String sAbon    = "";            
            
            /*Obtiene el abono de esa compra*/            
            try
            {                  
                sQ = "SELECT IFNULL(SUM(abon),0) AS abon FROM cxp WHERE prov = '" + sProv + "' AND norefer = '" + jTab1.getValueAt(iSel[x], 2).toString().trim() + "' AND noser = '" + sNoSer + "' AND concep = 'ABON COMP'";                
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
            
            /*Si el abono es mayor a 0 entonces*/            
            if(Double.parseDouble(sAbon)>0)
            {
                /*Dale formato de moneda al abono*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                double dCant    = Double.parseDouble(sAbon);                
                sAbon           = n.format(dCant);

                /*Preguntar al usuario si esta seguro de querer continuar*/
                iRes    = JOptionPane.showOptionDialog(this, "La compra: " + jTab1.getValueAt(iSel[x], 2).toString().trim() + " del proveedor: " + sProv + " tiene de abonos: " + sAbon + ". ¿Estas seguro que quieres continuar?", "Cancelar compra", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
                if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                    continue;                                                       
            }
            
            //Inicia la transacción
            if(Star.iIniTransCon(con)==-1)
                return;

            /*Actualiza la compra o órden para que sea de cancelación*/
            try 
            {            
                sQ = "UPDATE comprs SET "
                        + "estado           = 'CA', "
                        + "motiv            = '" + sMot.replace("'", "''") + "', "
                        + "sucu             = '" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj            = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE codcomp    = '" + jTab1.getValueAt(iSel[x], 2).toString().replace("'", "''") + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }        
                        
            /*Si no es una órden de compra entonces*/
            if(sTip.compareTo("ORD")!=0)
            {
                //Agrega una partida en CXP para agregarle saldo al proveedor de la cancelación                          
                if(Star.iInsCXCP(con, "cxp", jTab1.getValueAt(iSel[x], 2).toString(), sNoSer, sProv, sSer, sSubTot, sImpue, sTot, "0", sTot, "'" + sFVenc + "'", "'" + sFDoc + "'", "CA COMP", "", "0", "", "","")==-1)
                    return;        
                
                /*Recorre todas las partidas de la compra*/
                try
                {                  
                    sQ = "SELECT partcomprs.FLOTVENC, partcomprs.LOT, partcomprs.PEDIMEN, prods.SERVI, partcomprs.ESKIT, partcomprs.ALMA, partcomprs.LOT, comprs.PROV, comprs.NOSER, partcomprs.UNID, partcomprs.PROD, partcomprs.CANT, partcomprs.DESCRIP FROM partcomprs LEFT JOIN prods ON prods.PROD = partcomprs.PROD LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM WHERE codcom = '" + jTab1.getValueAt(iSel[x], 2).toString() + "'";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    while(rs.next())
                    {   
                        /*Si el producto es un kit o un servicio que continue por que no se debe devolver*/
                        if(rs.getString("eskit").compareTo("1")==0 || rs.getString("servi").compareTo("1")==0)
                            continue;

                        /*Obtiene la cantidad correcta dependiendo de su unidad*/
                        String sCant    = Star.sCantUnid(rs.getString("unid"), rs.getString("cant"));
                    
                        /*Dale seguimiento al costeo*/
                        if(Star.sGetCost(con, rs.getString("prod"), sCant)==null)
                            return;

                        /*Si la partida fue por lote o pedimento entonces*/
                        if(rs.getString("lot").compareTo("")!=0 || rs.getString("pedimen").compareTo("")!=0)
                        {
                            /*Realiza la afectación a lotes y pedimentos*/
                            if(Star.sLotPed(con, rs.getString("prod"), sCant, rs.getString("alma"), rs.getString("lot"), rs.getString("pedimen"), rs.getString("flotvenc"), "-")==null)
                                return;
                        }                            
                        
                        /*Realiza la afectación correspondiente al almacén para la salida*/
                        if(Star.iAfecExisProd(con, rs.getString("partcomprs.PROD"), rs.getString("partcomprs.ALMA"), sCant, "-")==-1)
                            return;

                        /*Actualiza la existencia general del producto tadeo*/
                        if(Star.iCalcGralExis(con, rs.getString("partcomprs.PROD"))==-1)
                            return;

                        /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
                        if(!Star.vRegMoniInv(con, rs.getString("partcomprs.PROD"), sCant, rs.getString("partcomprs.DESCRIP"), rs.getString("alma"), Login.sUsrG , jTab1.getValueAt(iSel[x], 2).toString(), "CA COMP", rs.getString("partcomprs.UNID"), rs.getString("comprs.NOSER"), rs.getString("comprs.PROV"), "1"))                                
                            return;                                                                                                                                                                                                             
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }
                
            }/*Fin de if(sTip.compareTo("ORD")=!0)*/                                                       
           
            //Termina la transacción
            if(Star.iTermTransCon(con)==-1)
                return;

            /*Coloca la bandera para saber que si hubo por lo menos un cambio*/
            bSi = true;
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                        
                   
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Función para agregar nuevamente los elementos actualizados a la tabla*/
        vCargT(jTab1);
        
        /*Mensaje de éxito en caso de que alla habido una modificación*/
        if(bSi)
            JOptionPane.showMessageDialog(null, "Exito en la cancelación de la(s) Orden(es)/Compra(s).", "Cancelación Exitosa", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBCanActionPerformed

    
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
    private void jBMostTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMostTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBMostTKeyPressed
        
    
    /*Función para agregar nuevamente los elementos actualizados a la tabla de las compras*/
    private void vCargT(javax.swing.JTable jTab)
    {                
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
        /*Agrega todos los datos de la base de datos a la tabla de comprs*/
        (new Thread()
        {
            @Override
            public void run()
            {
                Star.vCargComp(jTab1);                  
            }
            
        }).start();
    }
    
    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMostTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMostTActionPerformed

        /*Resetea la nota indicativa*/
        jLNot.setText("");
        
        /*Función para agregar nuevamente los elementos actualizados a la tabla*/
        vCargT(jTab1);
        
    }//GEN-LAST:event_jBMostTActionPerformed

    
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
        
        /*Borra todos los item en la tabla de comprs*/
        DefaultTableModel dm            = (DefaultTableModel)jTab1.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        Star.iContFiComp                  = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si se tiene habilitado que solo se puedan mostrar las comprs del usuario o todas*/
        String sCond    = "";
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'comps' AND conf = 'compsxusr'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca la consulta en caso de que este activado*/
                if(rs.getString("val").compareTo("1")==0)                                  
                    sCond   = "AND comprs.ESTAC = '" + Login.sUsrG + "' ";          
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }                                    
        
        /*Obtiene de la base de datos todos las compras*/        
        try
        {
            sQ = "SELECT comprs.MON, estacs.NOM, comprs.CODORD, comprs.RECIB, comprs.FENT, comprs.TIP, comprs.SUCU, comprs.NOCAJ, IFNULL(estacs.NOM,'') AS nom, comprs.PROV, comprs.NOSER, comprs.CODCOMP, comprs.NODOC, IFNULL(comprs.TOT,0 ) AS tot, comprs.FALT, comprs.FDOC, comprs.FMOD, comprs.ESTADO, comprs.ESTAC, comprs.MOTIV FROM comprs LEFT OUTER JOIN estacs ON estacs.ESTAC = comprs.ESTAC WHERE 1=1 " + sCond + " AND comprs.CODCOMP LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR estacs.NOM LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR comprs.NODOC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR comprs.TOT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR comprs.FALT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR comprs.FDOC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR comprs.FMOD LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR comprs.ESTADO LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR comprs.ESTAC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR comprs.MOTIV LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')  ORDER BY comprs.ID_ID DESC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene el total*/
                String sTot             = rs.getString("tot");
                
                /*Dale formato de moneda al cost*/                
                double dCant    = Double.parseDouble(sTot);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sTot            = n.format(dCant);

                /*Agregalo a la tabla*/
                DefaultTableModel tm    = (DefaultTableModel)jTab1.getModel();
                Object nu[]             = {Star.iContFiComp, rs.getString("comprs.TIP"), rs.getString("comprs.CODCOMP"), rs.getString("comprs.NOSER"), rs.getString("comprs.NODOC"), rs.getString("comprs.PROV"), rs.getString("nom"), sTot, rs.getString("comprs.MON"), rs.getString("comprs.FALT"), rs.getString("comprs.FDOC"), rs.getString("comprs.FENT"), rs.getString("comprs.FMOD"), rs.getString("comprs.ESTADO"), rs.getString("comprs.MOTIV"), rs.getString("comprs.RECIB"), rs.getString("comprs.CODORD"), rs.getString("comprs.SUCU"), rs.getString("comprs.NOCAJ"), rs.getString("comprs.ESTAC"), rs.getString("estacs.NOM")};
                tm.addRow(nu);

                /*Aumenta en uno el contador de filas*/
                ++Star.iContFiComp;                                      
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
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se presiona el botón de devlución parcial*/
    private void jBDevParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDevParActionPerformed
        
        /*Si el usuario no a seleccionado una compra para devolución parcial no puede avanzar*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado por lo menos una compra para devolución parcial.", "Devolcuión Parcial de Compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de comprs y regresa*/
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
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab1.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            //Declara variables locales
            String sEstad   = "";
            String sTip     = "";
            
            /*Obtiene algunos datos necesarios para el proceso*/           
            try
            {
                sQ = "SELECT tip, estado FROM comprs WHERE codcomp = '" + jTab1.getValueAt(iSel[x], 2) + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces onbtiene los resultados*/
                if(rs.next())
                {                   
                    sEstad      = rs.getString("estado");                                   
                    sTip        = rs.getString("tip");                                                                      
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
         
            /*Si no es una compra  entonces*/        
            if(sTip.compareTo("COMP")!= 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "El código: " + jTab1.getValueAt(iSel[x], 2) + " no es una compra entonces no se puede devovler.", "Compra de Devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));  
                continue;
            }
            
            /*Si es una compra que ya fue devuelta entonces*/        
            if(sEstad.compareTo("DEV")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La compra: " + jTab1.getValueAt(iSel[x], 2) + " fue por devolución.", "Compra de Devolución", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));  
                continue;
            }

            /*Si la compra ya esta cancelada entonces*/
            if(sEstad.compareTo("CA")== 0)
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La compra: " + jTab1.getValueAt(iSel[x], 2) + " esta cancelada.", "Compra Cancelada", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                continue;
            }

            /*Abre el formulario para devolución parcial*/
            DevPCom d = new DevPCom(jTab1.getModel().getValueAt(iSel[x], 2).toString(), jTab1);
            d.setVisible(true);
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                    

        //Cierra la base de datos
        Star.iCierrBas(con);                    
        
    }//GEN-LAST:event_jBDevParActionPerformed

    
    /*Cuamdo se presiona una tecla en el botón de devolución parcial*/    
    private void jBDevParKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDevParKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDevParKeyPressed

    
    
    
    
    /*Cuando se presiona una tecla en el botón de ver*/
    private void jBVerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVerKeyPressed

    
    /*Cuando se presiona una tecla en el botón de cargar*/
    private void jBCargKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCargKeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se presiona el botón de cargar*/
    private void jBCargActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargActionPerformed
        
        /*Si no a seleccionado una compra en la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una compra.", "Cargar compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
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
        
        /*Trae el código del proveedor de la compra*/
        String sProv    = "";        
        try
        {
            sQ = "SELECT tip,  prov FROM comprs WHERE codcomp = '" + jTab1.getValueAt(row, 2).toString() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene la consulta*/
            if(rs.next())
            {
                /*Si no es una compra entonces*/
                if(rs.getString("tip").compareTo("COMP")!=0)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Selecciona un documento que sea una compra.", "Compras", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Coloca el foco del control en la tabla y regresa*/
                    jTab1.grabFocus();
                    return;
                }
                
                /*Obtiene los demas resultados*/                
                sProv           = rs.getString("prov");                                                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  	        
             
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
            return;                        
        }
        
        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen de la compra*/
        final JFileChooser fc           = new JFileChooser();
        fc.setDialogTitle               ("Buscar compra");        
        fc.setMultiSelectionEnabled     (true);
        fc.setAcceptAllFileFilterUsed   (false);
        
        /*Muestra el file choooser*/
        int iVal                    = fc.showSaveDialog(this);
        
        /*Si el usuario presiono aceptar entonces*/
        if(iVal                    == JFileChooser.APPROVE_OPTION) 
        {
            /*Preguntar al usuario si esta seguro de que quiere desasociar la ruta*/
            Object[] op = {"Si","No"};
            int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres guardar el(los) archivo(s) de compra?", "Borrar archivo(s) compra", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
            if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                return;                       
            
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
            sCarp                    += "\\" + sProv;
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();
            
            /*Si no existe la carpeta para la compra entonces creala*/
            sCarp                    += "\\" + jTab1.getValueAt(row, 2).toString();     
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
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                            
                    return;
                }
                
            }/*Fin de for(File fil: fils)*/                            
                      
            /*Determina si el PDF y el XML esta cargado*/
            String sPDFC    = "";
            String sXMLC    = "";
            
            /*Obtiene el estado actual de los archivos*/
            try
            {
                sQ = "SELECT archpdf, archxml FROM comprs WHERE codcomp = '" + jTab1.getValueAt(jTab1.getSelectedRow(), 2) + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {                    
                    sPDFC      = rs.getString("archpdf");                                   
                    sXMLC      = rs.getString("archxml");                                   
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;                
            }

            /*Recorre todos los archivos guardados*/
            for(File fil: fils)
            {                
                /*Si el nombre del archivo termina con .PDF entonces marca la bandera y continua*/
                if(fil.getName().toLowerCase().endsWith(".pdf"))
                {
                    sPDFC   = "1";
                    continue;
                }

                /*Si el nombre del archivo termina con .XML entonces marca la bandera*/
                if(fil.getName().toLowerCase().endsWith(".xml"))
                    sXMLC   = "1";        
            }            

            /*Actualiza en la compra para saber si ya se tienen los archivos o no*/
            try 
            {                
                sQ = "UPDATE comprs SET "
                        + "archpdf          = " + sPDFC.replace("'", "''") + ", "
                        + "archxml          = " + sXMLC.replace("'", "''") + " "
                        + "WHERE codcomp    = '" + jTab1.getValueAt(row, 2).toString().replace("'", "''") + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                 //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
             }    
	        
            /*Mensaje de éxito*/
            JOptionPane.showMessageDialog(null, "Archivo(s) guardado(s) con éxito para la compra:" + jTab1.getValueAt(row, 2).toString()  + System.getProperty( "line.separator" ) + sCarp + "\".", "Compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
        }/*Fin de if(iVal           == JFileChooser.APPROVE_OPTION) */
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jBCargActionPerformed

    
    /*Cuando se presiona el botón de ver imágen de compra*/
    private void jBVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerActionPerformed
        
        /*Si no a seleccionado una compra de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una compra.", "No selecciono compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
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
        
        /*Obtiene algunos datos de la compra*/        
        String sProv    = "";        
        try
        {
            sQ = "SELECT prov, tip FROM comprs WHERE codcomp = '" + jTab1.getValueAt(row, 2).toString() + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si no es una compra entonces*/
                if(rs.getString("tip").compareTo("COMP")!=0)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;
                    
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Selecciona un documento que sea una compra.", "Compras", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Coloca el foco del control en la tabla y regresa*/
                    jTab1.grabFocus();
                    return;
                }
               
                /*Obtiene el proveedor*/
                sProv        = rs.getString("prov");
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
        
        /*Si la carpeta de comprs no existe entonces crea la ruta*/
        sCarp                    += "\\Compras";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si no existe la carpeta de la empresa entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si no existe la carpeta para el proveedor entonces creala*/
        sCarp                    += "\\" + sProv;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();       

        /*Si no existe la carpeta para el código de la fctura entonces crea la carpeta*/
        sCarp                    += "\\" + jTab1.getValueAt(row, 2).toString();
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
                
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene archivos entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La compra: " + jTab1.getValueAt(row, 2).toString() + " no contiene archivos cargados.", "Compras", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en la tabla y regresa*/
                jTab1.grabFocus();                        
                return;            
            }
        }                    
        
        /*Configura el file chooser para escoger la ruta del archivo de compra que quiere abrir*/
        final JFileChooser fc   = new JFileChooser  (sCarp);
        fc.setDialogTitle                           ("Abrir Compra");        
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
                Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                        
            }
        
        }/*Fin de if(iVal           == JFileChooser.APPROVE_OPTION) */                
        
    }//GEN-LAST:event_jBVerActionPerformed

    
    /*Cuando se presiona el botón de borrar imágen de compra*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
                                                               
        /*Si no a seleccionado una compra de la tabla entonces*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una compra.", "No selecciono compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
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
        
        /*Trae el código del proveedor de la compra*/        
        String sProv    = "";
        try
        {
            sQ = "SELECT prov, tip, prov FROM comprs WHERE codcomp = '" + jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
            {
                /*Si no es una compra entonces*/
                if(rs.getString("tip").compareTo("COMP")!=0)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "Selecciona un documento que sea una compra.", "Compras", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Coloca el foco del control en la tabla y regresa*/
                    jTab1.grabFocus();
                    return;
                }            
                
                /*Obtiene los resultados*/                
                sProv   = rs.getString("prov");
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  	                
        
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
        
        /*Si la carpeta de las imágenes no existe entonces crea la ruta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de comprs no existe entonces crea la carpeta*/
        sCarp                    += "\\Compras";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si no existe la carpeta de la empresa entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si no existe la carpeta para el proveedor entonces crea la carpeta*/
        sCarp                    += "\\" + sProv;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si no existe la carpeta para la compra entonces crea la carpeta*/
        sCarp                    += "\\" + jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString();     
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
                        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La compra: " + jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString() + " no contiene archivos para borrar.", "Compras", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en la tabla y regresa*/
                jTab1.grabFocus();            
                return;            
            }
        }                      
        
        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen de la compra*/
        final JFileChooser fc           = new JFileChooser(sCarp);
        fc.setDialogTitle               ("Borrar Archivo(s)");        
        fc.setMultiSelectionEnabled     (true);
        fc.setAcceptAllFileFilterUsed   (false);
        
        /*Muestra el file choooser*/
        int iVal                = fc.showSaveDialog(this);
        
        /*Si el usuario presiono aceptar entonces*/
        if(iVal                == JFileChooser.APPROVE_OPTION) 
        {
            /*Preguntar al usuario si esta seguro de que quiere desasociar la ruta*/
            Object[] op = {"Si","No"};
            int iRes            = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar el(los) archivo(s) de Compra?", "Borrar archivo(s) compra", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
            if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
                return;                       

            /*Obtiene todos los archivos seleccionados*/
            File[] fils  = fc.getSelectedFiles();
            
            /*Recorre todos los archivos seleccionados y borralos*/
            for(File fil: fils)
                new File(fil.getAbsolutePath()).delete();                                        

            /*Determina si el PDF y el XML esta cargado*/
            String sPDFC    = "";
            String sXMLC    = "";
            
            /*Obtiene el estado actual de los archivos*/
            try
            {
                sQ = "SELECT archpdf, archxml FROM comprs WHERE codcomp = '" + jTab1.getValueAt(jTab1.getSelectedRow(), 2) + "'";	
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {                    
                    sPDFC      = rs.getString("archpdf");                                   
                    sXMLC      = rs.getString("archxml");                                   
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            }
            
            /*Recorre todos los archivos guardados*/
            for(File fil: fils)
            {                
                /*Si el nombre del archivo termina con .PDF entonces marca la bandera y continua*/
                if(fil.getName().toLowerCase().endsWith(".pdf"))
                {
                    sPDFC   = "0";
                    continue;
                }

                /*Si el nombre del archivo termina con .XML entonces marca la bandera*/
                if(fil.getName().toLowerCase().endsWith(".xml"))
                    sXMLC   = "0";        
            }            
            
            /*Actualiza en la compra para saber si ya se tienen los archivos o no*/
            try 
            {                
                sQ = "UPDATE comprs SET "
                        + "archpdf          = " + sPDFC.replace("'", "''") + ", "
                        + "archxml          = " + sXMLC.replace("'", "''") + " "
                        + "WHERE codcomp    = '" + jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString().replace("'", "''") + "'";                                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
             }    
            
            /*Mensaje de éxito*/
            JOptionPane.showMessageDialog(null, "Archivo(s) borrado(s) para compra: " + jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString() + ".", "Compras", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
        }/*Fin de if(iVal           == JFileChooser.APPROVE_OPTION) */                                
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Cuando se presiona el botón de actualizar*/
    private void jBActuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActuaActionPerformed

        /*Función para cargar todos los elementos en la tabla*/
        vCargT(jTab1);

    }//GEN-LAST:event_jBActuaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de actualizar*/
    private void jBActuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBActuaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBActuaKeyPressed

    
    /*Cuando se presiona el botón de imprimir*/
    private void jBImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImpActionPerformed

        /*Si no se a seleccionado una o varias compras para imrpimir entonces*/
        int row = jTab1.getSelectedRow();
        if(row==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona alguna(s) Orden(es)/Compra(s) para imprimir.", "Imprimir", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el control y regresa*/
            jTab1.grabFocus();
            return;
        }

        /*Obtiene todas las filas seleccionadas en la tabla de compras*/
        int iArr[]      = jTab1.getSelectedRows();

        /*Este arreglo contendrá todas las ventas que se tienen que imprimir*/
        String sArrComps[]  = new String[iArr.length];

        /*Recorre toda la selección del usuario*/
        for(int x = 0; x < iArr.length;x++)
        {
            /*Obtiene la Órden/Compra*/
            String sCom = jTab1.getValueAt(row, 2).toString();

            /*Ve creando el arreglo*/
            sArrComps[x] = sCom;
        }

        /*Muestra la forma para imprimir*/
        ImprDial i      = new ImprDial(null, "", "", "", "compr", sArrComps);
        i.setVisible(true);

    }//GEN-LAST:event_jBImpActionPerformed

    
    /*Cuando se presiona una tecla en el botón de imprimir*/
    private void jBImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBImpKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBImpKeyPressed
        
            
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
    
    
    /*Cuando el moue sale del botón de búscar*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited

        /*Coloca el borde que tenía*/
        jBBusc.setBorder(bBordOri);
        
        /*Coloca el borde que tenía*/
        jBBusc.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
    /*Cuando el mouse entra en el botón de búscar*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);
        
        /*Guardar el borde original que tiene el botón*/
        bBordOri    = jBBusc.getBorder();
                
        /*Aumenta el grosor del botón*/
        jBBusc.setBorder(new LineBorder(Color.GREEN, 3));
        
    }//GEN-LAST:event_jBBuscMouseEntered

    
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

    
    
    
    
    
    /*Cuando se presiona una tecla en la tabla de partidas*/
    private void jTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTab1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTab1KeyPressed

    
    /*Cuando se hace un clic en la tabla de comprs*/
    private void jTab1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTab1MouseClicked

        /*Si se hiso doble clic entonces*/
        if(evt.getClickCount() == 2)
        {
            /*Presiona el botón de ver*/
            jBVe.doClick();

        }

    }//GEN-LAST:event_jTab1MouseClicked

    /*Cuando se presiona una tecla en la tabla 2*/
    private void jTab2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTab2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTab2KeyPressed

        
    /*Cuando se presiona una tecla en el botón de ver órdenes*/
    private void jBVePDFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVePDFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVePDFKeyPressed

    
    /*Cuando se presiona una tecla en el botón de directorio de órdenes*/
    private void jBDirOrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDirOrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDirOrKeyPressed

    
    /*Cuando se presiona el botón de ver directorio de órdenes de compra*/
    private void jBDirOrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDirOrActionPerformed
        
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
        
        /*Si la ruta de las órdenes no existe entonces crea la ruta*/
        String sRutTikCompro = sCarp + "\\Ordenes";
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
        
    }//GEN-LAST:event_jBDirOrActionPerformed

    
    /*Cuando se presioan el botón de ver PDF*/
    private void jBVePDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVePDFActionPerformed
        
        /*Si el usuario no a seleccionado por lo menos una compra no puede avanzar*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos una Orden/Compra/Nota para ver su PDF.", "Ver PDF", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en la tabla y regresa*/
            jTab1.grabFocus();           
            return;            
        }

        /*Preguntar al usuario si esta seguro de querer abrir los PDF*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres abrir el(los) PDF?", "PDF", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
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

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Recorre toda la selección del usuario*/
        int iSel[] = jTab1.getSelectedRows();        
        for(int x = iSel.length - 1; x >= 0; x--)
        {            
            /*Obtiene algunos datos necesarios*/            
            String sBuscEn  = "";
            try
            {
                sQ = "SELECT tip FROM comprs WHERE codcomp = '" + jTab1.getValueAt(iSel[x], 2).toString() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos obtiene el resultado*/
                if(rs.next())                
                    sBuscEn         = rs.getString("tip");                                   
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
            } 
                        
            /*Determina donde buscar, si es por compra entonces búsca en compras*/
            String sRutF            = "";
            if(sBuscEn.compareTo("COMP")==0)
            {
                JOptionPane.showMessageDialog(null, "Selecciono una Compra seleciona Generar para ver su PDF.", "Ver PDF", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Pon el foco del teclado en la tabla y regresa*/
                jTab1.grabFocus();           
                return;
            }
            /*Else, buscara en facturas*/
            else if(sBuscEn.compareTo("ORD")==0)
                sRutF               = sCarp + "\\Ordenes\\" + Login.sCodEmpBD + "\\" + jTab1.getValueAt(iSel[x], 2).toString() + ".pdf";
            /*Else, buscara en notas de crédito*/
            else if(sBuscEn.compareTo("NOTP")==0)
                sRutF               = sCarp + "\\Notas credito\\" + Login.sCodEmpBD + "\\" + jTab1.getValueAt(iSel[x], 2).toString() + ".pdf";

            /*Si no existe el archivo PDF entonces*/
            if(!new File(sRutF).exists())
            {
                /*Mensajea y continua*/
                JOptionPane.showMessageDialog(null, "La Orden/compra/nota no existe: " + System.getProperty( "line.separator" ) + sRutF + ".", "Ver PDF", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
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
        
    }//GEN-LAST:event_jBVePDFActionPerformed

    
    /*Cuando se presiona una tecla en el botón de recibir*/
    private void jBRecOrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBRecOrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBRecOrKeyPressed

    
    /*Cuando se presiona el botón de recibir órden de compra*/
    private void jBRecOrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRecOrActionPerformed
        
        /*Si el usuario no a seleccionado una compra para cancelar no puede avanzar*/
        if(jTab1.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una orden para recibir.", "Recepción de orden", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de comprs y regresa*/
            jTab1.grabFocus();            
            return;            
        }
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables locales
        String sTip     = "";
        String sEstad   = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;       
        String      sQ; 
        
        /*Obtiene algunos datos del código*/            
        try
        {
            sQ = "SELECT tip, estado FROM comprs WHERE codcomp = '" + jTab1.getValueAt(jTab1.getSelectedRow(), 2) + "' AND tip = 'ORD'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {                    
                sTip      = rs.getString("tip");                                   
                sEstad    = rs.getString("estado");                                   
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

        /*Si no es una órden entonces*/
        if(sTip.compareTo("ORD")!=0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El código: " + jTab1.getValueAt(jTab1.getSelectedRow(), 2) + " no es una Orden de compra.", "Ordén de Compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }

        /*Si ya esta cancelada entonces*/
        if(sEstad.compareTo("CA")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El código: " + jTab1.getValueAt(jTab1.getSelectedRow(), 2) + " de Orden esta cancelado.", "Ordén de Compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }

        /*Si ya esta confirmado entonces*/
        if(sEstad.compareTo("CO")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El código: " + jTab1.getValueAt(jTab1.getSelectedRow(), 2) + " de Orden esta confirmado.", "Ordén de Compra", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }

        /*Abre la forma de las compras una sola vez*/
        if(Star.recibOrdG==null)
        {            
            Star.recibOrdG = new RecibOrd(jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString(), jTab1, jTab1.getSelectedRow());
            Star.recibOrdG.setVisible(true);
        }
        else
        {            
            /*Si ya esta visible entonces traela al frente*/
            if(Star.recibOrdG.isVisible())            
                Star.recibOrdG.toFront();
            else
                Star.recibOrdG.setVisible(true);            
        }                    
                
    }//GEN-LAST:event_jBRecOrActionPerformed

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se presiona una tecla en el panel de los radio buttons*/
    private void jPRadioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPRadioKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPRadioKeyPressed

    
    /*Cuando se presiona una tecla en el radio de las compras*/
    private void jRCompKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRCompKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRCompKeyPressed

    
    /*Cuando se presiona una tecla en el radio de las órdenes*/
    private void jROrdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jROrdKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jROrdKeyPressed

    
    /*Cuando se presiona una tecla en el radio de todos*/
    private void jRTodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jRTodKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jRTodKeyPressed

    
    /*Cuando sucede una acción en el radio de compras*/
    private void jRCompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRCompActionPerformed
        
        /*Si esta seleccionado entonces carga solamente las compras*/
        if(jRComp.isSelected())        
            vCargTab(" WHERE tip = 'COMP' ");
                
    }//GEN-LAST:event_jRCompActionPerformed

    
    /*Cuando sucede una acción en el radio de las órdenes de compra*/
    private void jROrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jROrdActionPerformed
        
        /*Si esta seleccionado entonces carga solamente las órdenes de compra*/
        if(jROrd.isSelected())
            vCargTab(" WHERE tip = 'ORD' ");
        
    }//GEN-LAST:event_jROrdActionPerformed

    
    /*Cuando sucede una acción en el combo de todos*/
    private void jRTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRTodActionPerformed
        
        /*Si esta seleccionado entonces carga todas las compras*/
        if(jRTod.isSelected())
            vCargTab("");                    
        
    }//GEN-LAST:event_jRTodActionPerformed

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMostTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMostTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMostT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMostTMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCanMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCan.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCanMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDevMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDevMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDev.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDevMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDevParMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDevParMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDevPar.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDevParMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVe.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVeMouseEntered

    
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVerMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVer.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVerMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCargMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCarg.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCargMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBActuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBActua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBActuaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBImpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBImp.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBImpMouseEntered

    
    
    
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVePDFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVePDFMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVePDF.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVePDFMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDirOrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirOrMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDirOr.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDirOrMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBRecOrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRecOrMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBRecOr.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBRecOrMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón específicado*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMostTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMostTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMostT.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBMostTMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCanMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCan.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBCanMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDevMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDevMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDev.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDevMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDevParMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDevParMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDevPar.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDevParMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVe.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBVeMouseExited

    
    
    /*Cuando el mouse sale del botón específico*/
    private void jBVerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVerMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVer.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBVerMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCargMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCarg.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBCargMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBActuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBActua.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBActuaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBImpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBImp.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBImpMouseExited
       
    
    /*Cuando el mouse sale del botón específico*/
    private void jBVePDFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVePDFMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVePDF.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBVePDFMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDirOrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDirOrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDirOr.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDirOrMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBRecOrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRecOrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBRecOr.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBRecOrMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando se levanta una tecla en el botón de nueva compra*/
    private void jBNewKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyReleased
        
        /*Si se levanto la tecla de control entonces coloca la bandera en false*/
        if(evt.getKeyCode()==KeyEvent.VK_ALT)
            bAltP  = false;
        
    }//GEN-LAST:event_jBNewKeyReleased

    
    /*Cuando el mouse entra en el botón de nota de crédito*/
    private void jBNotCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNotCMouseEntered

        /*Cambia el color del fondo del botón*/
        jBNotC.setBackground(Star.colBot);

    }//GEN-LAST:event_jBNotCMouseEntered

    
    /*Cuando el mouse sale del botón de nota de crédito*/
    private void jBNotCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNotCMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBNotC.setBackground(Star.colOri);

    }//GEN-LAST:event_jBNotCMouseExited

    
    /*Cuando se presiona el botón de nota de crédito*/
    private void jBNotCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNotCActionPerformed

        /*Mostrar el formulario de nu ingreso de factura*/
        NewNotProv n = new NewNotProv(jTab1);
        n.setVisible(true);

    }//GEN-LAST:event_jBNotCActionPerformed

    
    /*Cuando se presiona una tecla en el botón de nota de crédito*/
    private void jBNotCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNotCKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBNotCKeyPressed

    
    
    /*Cuando el mouse entra en el botón de generar PDF de documento*/
    private void jBGenPDFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGenPDFMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGenPDF.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGenPDFMouseEntered

    
    /*Cuando el mouse sale del botón de generar documento PDF*/
    private void jBGenPDFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGenPDFMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBGenPDF.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGenPDFMouseExited

    
    /*Cuando se presiona el botón de generar PDF de documento*/
    private void jBGenPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGenPDFActionPerformed
        
        /*Si no existen compras entonces*/
        if(jTab1.getRowCount()==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No existen Órdenes/Compras/Notas.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }
        
        /*Obtiene las filas seleccionadas*/
        int iSel[]              = jTab1.getSelectedRows();
        
        /*Si el usuario no a seleccionado por lo menos una compra para ver en el reporte entonces*/
        if(iSel.length==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un documento para generar.", "Ver documento(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de comprs y regresa*/
            jTab1.grabFocus();                        
            return;            
        }
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Muestra el loading
        Star.vMostLoading("");
             
        /*Recorre toda la selección del usuario*/                
        for(int x = iSel.length - 1; x >= 0; x--)
        {   
            //Declara variables locales
            String sTip     = "";
            String sNoDoc   = "";
            String sNom     = "";
            String sMon     = "";
            String sFDoc    = "";                                       
            String sProv    = "";
            String sSubTot  = "";
            String sImp     = "";
            String sTot     = "";

            //Declara variables de la base de datos
            Statement       st;
            ResultSet       rs;        
            String          sQ;                 

            /*Obtiene algunos datos de la compra*/            
            try
            {
                sQ = "SELECT mon, prov, subtot, impue, tot, tip, nodoc, nomprov, fdoc FROM comprs WHERE codcomp = '" + jTab1.getValueAt(iSel[x], 2).toString().trim() + "'";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                if(rs.next())
                {
                    /*Obtiene los datos*/
                    sProv           = rs.getString("prov");                
                    sSubTot         = rs.getString("subtot");                
                    sImp            = rs.getString("impue");                                    
                    sTot            = rs.getString("tot");
                    sTip            = rs.getString("tip");
                    sNoDoc          = rs.getString("nodoc");
                    sNom            = rs.getString("nomprov");
                    sMon            = rs.getString("mon");
                    sFDoc           = rs.getString("fdoc");

                    /*Dales formato de moneda al subtot*/                    
                    double dCant    = Double.parseDouble(sSubTot);                
                    NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                    sSubTot         = n.format(dCant);

                    /*Dales formato de moneda al impuesto*/
                    dCant           = Double.parseDouble(sImp);                                
                    sImp            = n.format(dCant);

                    /*Dales formato de moneda al total*/
                    dCant           = Double.parseDouble(sTot);                                
                    sTot            = n.format(dCant);                                
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
            
            /*Obtiene los datos como final*/
            final String sProvFin       = sProv;
            final String sSubTotFin     = sSubTot;
            final String sImpFin        = sImp;
            final String sTotFin        = sTot;
            final String sComp          = jTab1.getValueAt(iSel[x], 2).toString().trim();            
            final String sNomFi         = sNom;
            final String sFDocFi        = sFDoc;
            final String sMonFi         = sMon;
            final String sNoDocFi       = sNoDoc;
            final String sTipFi         = sTip;
            
            /*Crea el thread para cargar el reporte en un hilo aparte*/
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

                    //Declara variables locales
                    String sNomLoc     = "";
                    String sCallLoc    = "";
                    String sTelLoc     = "";
                    String sColLoc     = "";
                    String sCPLoc      = "";
                    String sCiuLoc     = "";
                    String sEstLoc     = "";
                    String sPaiLoc     = "";
                    
                    //Declara variables de la base de datos                
                    Statement   st;
                    ResultSet   rs;                
                    String      sQ; 

                    /*Obtiene todos los datos de la empresa local*/                    
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
                     
                    /*Contiene el nombre del reporte, ya sea compra u órden*/
                    String sRep    = "";
                     
                    /*Si es una Ã³rden entonces serán diferentes parÃ¡metros*/                    
                    java.util.Map <String,String> par = new java.util.HashMap<>();             
                    par.clear();
                    if(sTipFi.compareTo("ORD")==0)
                     {                         
                        par.put("ORD",               sComp);                                            
                        par.put("NOMPROV",           sNomFi);
                        par.put("CODPROV",           sProvFin);                        
                        par.put("EMPLOC",            sNomLoc);
                        par.put("TELLOC",            sTelLoc);
                        par.put("COLLOC",            sColLoc);
                        par.put("CALLLOC",           sCallLoc);
                        par.put("MON",               sMonFi);
                        par.put("CPLOC",             sCPLoc);
                        par.put("SUBTOT",            sSubTotFin);
                        par.put("IMPUE",             sImpFin);
                        par.put("TOT",               sTotFin);
                        par.put("CIULOC",            sCiuLoc);
                        par.put("ESTLOC",            sEstLoc);
                        par.put("PAILOC",            sPaiLoc);                    
                        par.put("LOGE",              Star.class.getResource(Star.sIconDef).toString());
                        
                        /*Inicializa el nombre de reporte compra*/
                        sRep           =   "rptOrd.jrxml";                        
                    }                         
                    /*Else es una compra entonces los parámetros son otros*/
                    else if(sTipFi.compareTo("COMP")==0)
                     {
                         par.put("COMP",       sComp);
                         par.put("FDOC",       sFDocFi);
                         par.put("NOM",        sNomFi);
                         par.put("PROV",       sProvFin);
                         par.put("NODOC",      sNoDocFi);                    
                         par.put("MON",        sMonFi);
                         par.put("SUBTOT",     sSubTotFin);
                         par.put("IMPUE",      sImpFin);
                         par.put("TOT",        sTotFin);
                         par.put("LOGE",       Star.class.getResource(Star.sIconDef).toString());
                         
                         /*Inicializa el nombre de reporte compra*/
                         sRep           =   "rptVCom.jrxml";
                     }
                     /*Else es una nota entonces los parámetros son otros*/
                    else if(sTipFi.compareTo("NOTP")==0)
                     {                         
                         par.put("COMP",       sComp);
                         par.put("FDOC",       sFDocFi);
                         par.put("NOM",        sNomFi);
                         par.put("PROV",       sProvFin);                         
                         par.put("NODOC",      sNoDocFi);
                         par.put("MON",        sMonFi);
                         par.put("SUBTOT",     sSubTotFin);
                         par.put("IMPUE",      sImpFin);
                         par.put("TOT",        sTotFin);
                         par.put("LOGE",       Star.class.getResource(Star.sIconDef).toString());
                         
                         /*Inicializa el nombre de reporte compra*/
                         sRep           =   "rptVNotCom.jrxml";
                     }
                    
                     /*Muestra el formulario*/
                     try
                     {                                             
                        /*Compila el reporte y muestralo maximizado*/
                        JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/" + sRep));
                        JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)par, con);
                        JasperViewer v      = new JasperViewer(pr, false);
                        v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                        /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte Compras");
                    v.setIconImage(newimg);
                        v.setVisible(true);

                        //Esconde la forma de loading
                        Star.vOcultLoadin();
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

        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                                            
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jBGenPDFActionPerformed

    
    /*Cuando se presiona una tecla en el botón de generar PDF de documento*/
    private void jBGenPDFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGenPDFKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGenPDFKeyPressed

    
    //Cuando se presiona una tecla en el botón de cancelar
    private void jBCanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCanKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCanKeyPressed

    
    /*Función para cargar solamente compras en la tabla de enzabezados*/
    private void vCargTab(String sCond)
    {                    
        /*Borra toda la tabla de encebzados y de partidas*/
        DefaultTableModel tm = (DefaultTableModel)jTab1.getModel();
        tm.setRowCount(0);
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
	/*Trae todas las compras de la base de datos y cargalas en la tabla*/
        try
        {
            sQ  = "SELECT comprs.CODORD, comprs.RECIB, comprs.FENT, comprs.TIP, estacs.NOM, comprs.SUCU, comprs.NOSER, comprs.PROV, comprs.NOCAJ, comprs.CODCOMP, comprs.NODOC, IFNULL(provs.NOM,'') AS nom, IFNULL(comprs.TOT,0) AS tot, comprs.FALT, comprs.FDOC,  comprs.ESTADO, comprs.ESTAC, comprs.FMOD, comprs.MOTIV FROM comprs LEFT OUTER JOIN estacs ON estacs.ESTAC = comprs.ESTAC LEFT JOIN provs ON comprs.PROV = CONCAT_WS('', provs.SER, provs.PROV) " + sCond + " ORDER BY comprs.ID_ID DESC";        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                 
                /*Obtiene el total*/
                String sTot            = rs.getString("tot");
                
                /*Dale formato de moneda al tot*/                
                double dCant    = Double.parseDouble(sTot);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                sTot            = n.format(dCant);
        
                /*Agregalo a la tabla*/
                Object nu[]= {Star.iContFiComp, rs.getString("comprs.TIP"), rs.getString("comprs.CODCOMP"), rs.getString("comprs.NOSER"), rs.getString("comprs.NODOC"), rs.getString("comprs.PROV"), rs.getString("nom"), sTot, rs.getString("comprs.FALT"), rs.getString("comprs.FDOC"), rs.getString("comprs.FENT"), rs.getString("comprs.FMOD"), rs.getString("comprs.ESTADO"), rs.getString("comprs.MOTIV"), rs.getString("comprs.RECIB"), rs.getString("comprs.CODORD"), rs.getString("comprs.SUCU"), rs.getString("comprs.NOCAJ"), rs.getString("comprs.ESTAC"), rs.getString("estacs.NOM")};
                tm.addRow(nu);
                
                /*Aumenta en uno el contador de filas de las comprs*/
                ++Star.iContFiComp;                
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
        
    }/*Fin de private void vCargTab(String sTip)*/
                               
        
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
            jBCan.doClick();
        /*Si se presiona F2 presiona el botón de devolución*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBDev.doClick();
        /*Si se presiona F3 presiona el botón de búscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMostT.doClick();
        /*Si se presiona F5 presiona el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();        
        /*Si se presiona F8 presiona el botón de devolución parcial*/
        else if(evt.getKeyCode() == KeyEvent.VK_F8)
            jBDevPar.doClick();
        /*Si se presiona F10 presiona el botón de ver*/
        else if(evt.getKeyCode() == KeyEvent.VK_F10)
            jBVe.doClick();
        /*Else if se presiona Alt + F presiona el botón de ver PDF*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F)
            jBVePDF.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();        
        /*Si se presiona CTRL + P entonces presiona el botón de imprimir*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_P)
            jBImp.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBCan;
    private javax.swing.JButton jBCarg;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBDev;
    private javax.swing.JButton jBDevPar;
    private javax.swing.JButton jBDirOr;
    private javax.swing.JButton jBGenPDF;
    private javax.swing.JButton jBImp;
    private javax.swing.JButton jBMostT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBNotC;
    private javax.swing.JButton jBRecOr;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTab2;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBVe;
    private javax.swing.JButton jBVePDF;
    private javax.swing.JButton jBVer;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLNot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPRadio;
    private javax.swing.JRadioButton jRComp;
    private javax.swing.JRadioButton jROrd;
    private javax.swing.JRadioButton jRTod;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTable jTab1;
    private javax.swing.JTable jTab2;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Empresas extends javax.swing.JFrame */
