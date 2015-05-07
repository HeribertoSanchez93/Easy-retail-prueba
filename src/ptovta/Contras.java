//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
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
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;




/*Clase para controlar los contrarecibos*/
public class Contras extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Declara variables de instancia*/           
    private int                 iContFi;    
    private int                 iContFiContra;

    /*Contador para modificar tabla*/
    private int                  iContCellEd;
    
    /*Declara variables originales*/
    private String              sComOri;    
    private String              sNomOri;
    private String              sImpoOri;
    private String              sImpueOri;
    private String              sTotOri;
    private String              sFOri;
    private String              sFVenOri;
    private String              sSucOri;
    private String              sCajOri;
    private String              sEstacOri;
    private String              sNomEstacOri;
    private String              sContraOri;

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean             bSel;
    
    
    
    /*Constructor sin argumentos*/
    public Contras() 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGen);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que no se muevan las columnas*/
        jTab1.getTableHeader().setReorderingAllowed(false);
        jTab2.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Contra recibos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa los contadores de filas en uno*/
        iContFi          = 1;
        iContFiContra    = 1;
        
        /*Selecciona el día de hoy inicialmente para las fechas*/
        Date f = new Date();
        jDTDe.setDate   (f);
        jDTA.setDate    (f);

        /*Establece los campos de fecha para que solo se puedan modificar con el botón*/
        jDTDe.getDateEditor().setEnabled    (false);
        jDTA.getDateEditor().setEnabled     (false);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nom de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab1.getModel());
        jTab1.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el campo de la empresa*/
        jTProv.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de fabricantes*/
        //jTabCXC.getColumnModel().getColumn(2).setPreferredWidth(250);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab1.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,     null);
        jTab1.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,    null);
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,   null);
        jTab2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,  null);
        
        /*Carga todas las comprs en la tabla*/
        vCargComp();
                        
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
                        sComOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 1).toString();
                        sNomOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 2).toString();
                        sImpoOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 3).toString();
                        sImpueOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 4).toString();
                        sTotOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 5).toString();
                        sFOri           = jTab1.getValueAt(jTab1.getSelectedRow(), 6).toString();
                        sFVenOri        = jTab1.getValueAt(jTab1.getSelectedRow(), 7).toString();
                        sSucOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 8).toString();
                        sCajOri         = jTab1.getValueAt(jTab1.getSelectedRow(), 9).toString();
                        sEstacOri       = jTab1.getValueAt(jTab1.getSelectedRow(), 10).toString();
                        sNomEstacOri    = jTab1.getValueAt(jTab1.getSelectedRow(), 11).toString();
                        sContraOri      = jTab1.getValueAt(jTab1.getSelectedRow(), 12).toString();                        
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab1.setValueAt(sComOri,           jTab1.getSelectedRow(), 1);                        
                        jTab1.setValueAt(sNomOri,           jTab1.getSelectedRow(), 2);                        
                        jTab1.setValueAt(sImpoOri,          jTab1.getSelectedRow(), 3);                        
                        jTab1.setValueAt(sImpueOri,         jTab1.getSelectedRow(), 4);                        
                        jTab1.setValueAt(sTotOri,           jTab1.getSelectedRow(), 5);                        
                        jTab1.setValueAt(sFOri,             jTab1.getSelectedRow(), 6);                        
                        jTab1.setValueAt(sFVenOri,          jTab1.getSelectedRow(), 7);                        
                        jTab1.setValueAt(sSucOri,           jTab1.getSelectedRow(), 8);                        
                        jTab1.setValueAt(sCajOri,           jTab1.getSelectedRow(), 9);                        
                        jTab1.setValueAt(sEstacOri,         jTab1.getSelectedRow(), 10);                        
                        jTab1.setValueAt(sNomEstacOri,      jTab1.getSelectedRow(), 11);                        
                        jTab1.setValueAt(sContraOri,        jTab1.getSelectedRow(), 12);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla 2*/
        jTab2.addPropertyChangeListener(pro);
        
        /*Crea el listener para cuando se cambia de selección en la tabla 2*/
        pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtén la propiedad que a sucedio en el control*/
                String pr = event.getPropertyName();                                
                                
                /*Si no hay selección entonces regresa*/
                if(jTab2.getSelectedRow()==-1)
                    return;                
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pr)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sComOri         = jTab1.getValueAt(jTab2.getSelectedRow(), 1).toString();
                        sNomOri         = jTab1.getValueAt(jTab2.getSelectedRow(), 2).toString();
                        sImpoOri        = jTab1.getValueAt(jTab2.getSelectedRow(), 3).toString();
                        sImpueOri       = jTab1.getValueAt(jTab2.getSelectedRow(), 4).toString();
                        sTotOri         = jTab1.getValueAt(jTab2.getSelectedRow(), 5).toString();
                        sFOri           = jTab1.getValueAt(jTab2.getSelectedRow(), 6).toString();
                        sFVenOri        = jTab1.getValueAt(jTab2.getSelectedRow(), 7).toString();
                        sEstacOri       = jTab1.getValueAt(jTab2.getSelectedRow(), 8).toString();
                        sContraOri      = jTab1.getValueAt(jTab2.getSelectedRow(), 9).toString();                        
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab1.setValueAt(sComOri,           jTab2.getSelectedRow(), 1);                        
                        jTab1.setValueAt(sNomOri,           jTab2.getSelectedRow(), 2);                        
                        jTab1.setValueAt(sImpoOri,          jTab2.getSelectedRow(), 3);                        
                        jTab1.setValueAt(sImpueOri,         jTab2.getSelectedRow(), 4);                        
                        jTab1.setValueAt(sTotOri,           jTab2.getSelectedRow(), 5);                        
                        jTab1.setValueAt(sFOri,             jTab2.getSelectedRow(), 6);                        
                        jTab1.setValueAt(sFVenOri,          jTab2.getSelectedRow(), 7);                        
                        jTab1.setValueAt(sEstacOri,         jTab2.getSelectedRow(), 8);                        
                        jTab1.setValueAt(sContraOri,        jTab2.getSelectedRow(), 9);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla 2*/
        jTab2.addPropertyChangeListener(pro);
        
    }/*Fin de public Contras() */

    
    /*Obtén las comprs de la base de datos y cargalos en la tabla*/
    private void vCargComp()
    {
        /*Limpia la tabla de comprs*/
        DefaultTableModel dm    = (DefaultTableModel)jTab1.getModel();
        dm.setRowCount(0);                
                     
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
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab1.getModel();                    
        
        /*Si no hay nada marcado entonces que regrese ya no es necesario que continue*/
        if(!jCPe.isSelected() && !jCCo.isSelected())
        {            
            //Cierra la base de datos
            Star.iCierrBas(con);
            return;
        }
        
        /*Reinicia el contador de filas*/
        iContFi     = 1;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ  = "";
        
        /*Trae todos los comprs  de la base de datos y cargalos en la tabla*/
        try
        {
            /*Determina que consulta utilizar*/
            if(jCPe.isSelected())
                sQ = "SELECT comprs.SUCU, comprs.NOCAJ, estacs.NOM, comprs.CODCOMP, comprs.PROV, provs.NOM, comprs.SUBTOT, comprs.IMPUE, comprs.TOT, comprs.FDOC, comprs.FVENC, comprs.ESTAC, comprs.CONTRA FROM comprs LEFT OUTER JOIN estacs ON comprs.ESTAC = estacs.ESTAC LEFT OUTER JOIN provs ON CONCAT_WS('',provs.SER, provs.PROV ) = comprs.PROV WHERE DATE(comprs.FDOC) >= '" + sD + "' AND DATE(comprs.FDOC) <= '" + sA + "' AND comprs.CONTRA = 0 AND comprs.PROV LIKE('%" + jTProv.getText().replace("", "%") + "%')  GROUP BY comprs.CODCOMP, comprs.PROV, provs.NOM, comprs.SUBTOT, comprs.IMPUE, comprs.TOT, comprs.FDOC";
            else if(jCCo.isSelected())
                sQ = "SELECT comprs.SUCU, comprs.NOCAJ, estacs.NOM, comprs.CODCOMP, comprs.PROV, provs.NOM, comprs.SUBTOT, comprs.IMPUE, comprs.TOT, comprs.FDOC, comprs.FVENC, comprs.ESTAC, comprs.CONTRA FROM comprs LEFT OUTER JOIN estacs ON comprs.ESTAC = estacs.ESTAC LEFT OUTER JOIN provs ON CONCAT_WS('',provs.SER, provs.PROV ) = comprs.PROV WHERE DATE(comprs.FDOC) >= '" + sD + "' AND DATE(comprs.FDOC) <= '" + sA + "' AND comprs.CONTRA = 1 AND comprs.PROV LIKE('%" + jTProv.getText().replace("", "%") + "%')  GROUP BY comprs.CODCOMP, comprs.PROV, provs.NOM, comprs.SUBTOT, comprs.IMPUE, comprs.TOT, comprs.FDOC";
            if(jCCo.isSelected() && jCPe.isSelected())
                sQ = "SELECT comprs.SUCU, comprs.NOCAJ, estacs.NOM, comprs.CODCOMP, comprs.PROV, provs.NOM, comprs.SUBTOT, comprs.IMPUE, comprs.TOT, comprs.FDOC, comprs.FVENC, comprs.ESTAC, comprs.CONTRA FROM comprs LEFT OUTER JOIN estacs ON comprs.ESTAC = estacs.ESTAC LEFT OUTER JOIN provs ON CONCAT_WS('',provs.SER, provs.PROV ) = comprs.PROV WHERE DATE(comprs.FDOC) >= '" + sD + "' AND DATE(comprs.FDOC) <= '" + sA + "' AND comprs.PROV LIKE('%" + jTProv.getText().replace("", "%") + "%')  GROUP BY comprs.CODCOMP, comprs.PROV, provs.NOM, comprs.SUBTOT, comprs.IMPUE, comprs.TOT, comprs.FDOC";
            
            /*Ejecuta la consulta*/
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                     
                /*Obtiene los totales*/
                String sImp     = rs.getString("comprs.SUBTOT");
                String sImpue   = rs.getString("comprs.IMPUE");
                String sTot     = rs.getString("comprs.TOT");                
                
                /*Dale formato de moneda a los totales*/                
                double dCant    = Double.parseDouble(sImp);                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sImp            = n.format(dCant);
                dCant           = Double.parseDouble(sImpue);                
                sImpue          = n.format(dCant);
                dCant           = Double.parseDouble(sTot);                
                sTot            = n.format(dCant);                
               
                /*Determina si ya se hizo contrarecibo de esta compra o no*/
                String  sContra;
                if(rs.getString("comprs.CONTRA").compareTo("1")==0)
                    sContra     = "Si";
                else
                    sContra     = "No";
                /*Agregalo a la tabla*/
                Object nu[]             = {iContFi, rs.getString("comprs.CODCOMP"), rs.getString("comprs.PROV"), rs.getString("provs.NOM"), sImp, sImpue,sTot, rs.getString("comprs.FDOC"), rs.getString("comprs.FVENC"), rs.getString("comprs.SUCU"), rs.getString("comprs.NOCAJ"), rs.getString("comprs.ESTAC"), rs.getString("estacs.NOM"), sContra};
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
        Star.iCierrBas(con);
                
    }/*Fin de private void vCargComp()*/
            
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBGen = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab2 = new javax.swing.JTable();
        jCPe = new javax.swing.JCheckBox();
        jCCo = new javax.swing.JCheckBox();
        jDTA = new com.toedter.calendar.JDateChooser();
        jDTDe = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jTProv = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTNomProv = new javax.swing.JTextField();
        jBBusc = new javax.swing.JButton();
        jTRespon = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTTot = new javax.swing.JTextField();
        jBVer = new javax.swing.JButton();
        jBActua = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
        jBTab2 = new javax.swing.JButton();
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

        jBGen.setBackground(new java.awt.Color(255, 255, 255));
        jBGen.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGen.setForeground(new java.awt.Color(0, 102, 0));
        jBGen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/gencontra.png"))); // NOI18N
        jBGen.setText("Generar");
        jBGen.setToolTipText("Generar Contrarecibo");
        jBGen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBGen.setNextFocusableComponent(jBVer);
        jBGen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGenMouseExited(evt);
            }
        });
        jBGen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGenActionPerformed(evt);
            }
        });
        jBGen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGenKeyPressed(evt);
            }
        });
        jP1.add(jBGen, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 90, 110, 30));

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 180, 110, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("TOTAL:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 560, 80, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("A:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 40, -1));

        jTab1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Compra", "Proveedor", "Nombre", "Importe", "Impuesto", "Total", "Fecha", "Fecha Vencimiento", "Sucursal", "Caja", "Usuario", "Nombre Usuario", "Contra recibo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab1.setGridColor(new java.awt.Color(255, 255, 255));
        jTab1.setNextFocusableComponent(jTab2);
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 830, 270));

        jTab2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Compra", "Proveedor", "Nombre", "Importe", "Impuesto", "Total", "Fecha", "Fecha Vencimiento", "Usuario", "Contra recibo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab2.setColumnSelectionAllowed(true);
        jTab2.setGridColor(new java.awt.Color(255, 255, 255));
        jTab2.setNextFocusableComponent(jBGen);
        jTab2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
        jTab2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 830, 190));

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
        jP1.add(jCPe, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 80, -1));

        jCCo.setBackground(new java.awt.Color(255, 255, 255));
        jCCo.setSelected(true);
        jCCo.setText("CO");
        jCCo.setNextFocusableComponent(jTRespon);
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
        jP1.add(jCCo, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 40, 80, -1));

        jDTA.setNextFocusableComponent(jCPe);
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
        jLabel5.setText("*Proveedor:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 80, -1));

        jTProv.setBackground(new java.awt.Color(204, 255, 204));
        jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProv.setNextFocusableComponent(jBBusc);
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
        jLabel6.setText("*Responsable:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 20, 220, -1));

        jTNomProv.setEditable(false);
        jTNomProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNomProv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTNomProvFocusLost(evt);
            }
        });
        jP1.add(jTNomProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 230, 20));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setText("...");
        jBBusc.setToolTipText("Buscar Proveedor(es)");
        jBBusc.setNextFocusableComponent(jDTDe);
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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 30, 20));

        jTRespon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRespon.setNextFocusableComponent(jTab1);
        jTRespon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTResponFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTResponFocusLost(evt);
            }
        });
        jTRespon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTResponKeyPressed(evt);
            }
        });
        jP1.add(jTRespon, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 40, 220, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("De:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 40, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Compras:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 160, -1));

        jTTot.setEditable(false);
        jTTot.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTTot.setForeground(new java.awt.Color(0, 0, 204));
        jTTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTot.setText("$0.00");
        jTTot.setFocusable(false);
        jP1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 550, 140, 30));

        jBVer.setBackground(new java.awt.Color(255, 255, 255));
        jBVer.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVer.setForeground(new java.awt.Color(0, 102, 0));
        jBVer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/ver.png"))); // NOI18N
        jBVer.setText("Ver");
        jBVer.setToolTipText("Ver Contrarecibos (F2)");
        jBVer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBVer.setNextFocusableComponent(jBActua);
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
        jP1.add(jBVer, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 120, 110, 30));

        jBActua.setBackground(new java.awt.Color(255, 255, 255));
        jBActua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBActua.setForeground(new java.awt.Color(0, 102, 0));
        jBActua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actualizar.png"))); // NOI18N
        jBActua.setText("Actualizar");
        jBActua.setToolTipText("Actualizar Registros (F5)");
        jBActua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBActua.setNextFocusableComponent(jBSal);
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
        jP1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 150, 110, 30));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 10, 20));

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
        jP1.add(jBTab2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(846, 574, 150, 20));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar Todo");
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 72, 130, 18));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 998, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de generar*/
    private void jBGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGenActionPerformed
        
                              
        /*Si no hay contrarecibos para generar entonces*/
        if(jTab2.getRowCount()==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No hay compras para generar contrarecibo.", "Contrarecibo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab2.grabFocus();            
            return;            
        }
        
        /*Si el campo del responsable esta vacio entonces*/
        String sRespon  = jTRespon.getText();
        if(sRespon.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTRespon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa un responsable.", "Contrarecibo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTRespon.grabFocus();                        
            return;            
        }
        
        /*Si esta vacio el proveedor entonces*/        
        if(jTNomProv.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Ingresa un proveedor.", "Contrarecibo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTProv.grabFocus();                        
            return;            
        }
        
        /*Obtiene el primer proveedor de la fila*/
        String sProvRepe    = jTab2.getValueAt(0, 2).toString();
        
        /*Recorre la tabla de contrarecibos*/
        for(int x = 0; x < jTab2.getRowCount(); x++)
        {
            /*Si el proveedor no es igual entonces*/
            if(jTab2.getValueAt(x, 2).toString().compareTo(sProvRepe)!=0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Hay mas de un proveedor en la lista de contrarecibos.", "Contra recibo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el control y regresa*/
                jTab2.grabFocus();               
                return;
            }
            
        }
        
        /*Preguntar al usuario si esta seguro de querer generar el contrarecibo*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres generar el contrarecibo para el cliente: " + jTNomProv.getText() + "?", "Contrarecibo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables locales
        String sNom     = "";
        String sCall    = "";
        String sTel     = "";
        String sCol     = "";
        String sCP      = "";
        String sCiuEst  = "";
        String sCiu;
        String sEst;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        //Obtiene los datos de la empresa local
        try
        {                  
            sQ = "SELECT nom, call, tel, col, cp, ciu, estad FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene todos los datos de la consulta*/
                sNom                    = rs.getString("nom");                                    
                sCall                   = rs.getString("call");                                    
                sTel                    = rs.getString("tel");                                    
                sCol                    = rs.getString("col");                                    
                sCP                     = rs.getString("cp");                                                    
                sCiu                    = rs.getString("ciu");                                                    
                sEst                    = rs.getString("estad");                                                    
                
                /*Forma la ciudad y el estado en uno solo*/
                sCiuEst                 = sCiu + ", " + sEst;                               
            }                       
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Obtiene el máximo id de la tabla de contrarecibos*/
        String sMax = "";
        try
        {
            sQ = "SELECT IFNULL(MAX(IFNULL(id_id,0)),0) + 1 AS MAX FROM contra";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sMax    = rs.getString("max");                                                                       
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
        
        /*Completa la ruta completa hacia la carpeta del logo de la empresa*/
        String sRutLog              = sCarp + "\\imgs\\Logotipo Empresa\\" + Login.sCodEmpBD + "\\Logo.jpg";
        
        /*Obtiene la impresora predeterminada actual*/
        PrintService service        = PrintServiceLookup.lookupDefaultPrintService(); 
        String sImpAnt              = service.getName();
        
        /*Obtiene el nom de la impresora que tiene configurada El usuario actual y si es de cort o no*/
        String sImpr    = "";
        String s52      = "";
        String sCort    = "";
        try
        {
            sQ = "SELECT imptic, 52m, cort FROM estacs WHERE estac = '" + Login.sUsrG + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Obtiene el nom de la impresora y la medida del ticket*/
                sImpr           = rs.getString("imptic");
                s52             = rs.getString("52m");
                sCort           = rs.getString("cort");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }   
        
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;
        
        /*Recorre toda la tabla de contrarecibos*/
        for(int x = 0; x < jTab2.getRowCount(); x++)
        {
            /*Obtiene los datos de la fila*/
            String sComp    = jTab2.getValueAt(x, 1).toString();
            String sProv    = jTab2.getValueAt(x, 2).toString();
            String sTot     = jTab2.getValueAt(x, 6).toString().replace("$", "").replace(",", "");
            String sFech    = jTab2.getValueAt(x, 7).toString();
            
            /*Inserta en la tabla de contrarecibos todos los que se van a hacer*/
            try 
            {                
                sQ = "INSERT INTO contra(id_id,         prov,                               comp,                                   tot,           fech,                            falt,       estac,                                          respon,                                 sucu,                                           nocaj) " + 
                             "VALUES(" + sMax + ", '" + sProv.replace("'", "''") + "', '" + sComp.replace("'", "''") + "', " +      sTot + ", '" + sFech.replace("'", "''") + "',   now(), '" + Login.sUsrG.replace("'", "''") + "', '" +   sRespon.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
             }    
            
            /*Actualiza la compra para saber que ya se hizo contrarecibo*/
            try 
            {                
                sQ = "UPDATE comprs SET "
                        + "contra       = 1, "
                        + "sucu         ='" + Star.sSucu.replace("'", "''") + "', "
                        + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                        + "WHERE codcom = '" + sComp.replace("'", "''") + "'";                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
             }    
            
        }/*Fin de for(int x = 0; x < jTabContra.getRowCount(); x++)*/
                                
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Declara variables finales para el contrarecibo*/
        final String sNomFi     = sNom;
        final String sCallFi    = sCall;
        final String sColFi     = sCol;
        final String sCPFi      = sCP;
        final String sCiuEstFi  = sCiuEst;
        final String sTelFi     = sTel;
        final String sResponFi  = sRespon;
        final String sMaxFi     = sMax;
        final String sRutLogFi  = sRutLog;
        final String s52Fi      = s52;
        final String sImprFi    = sImpr;
        final String sImpAntFi  = sImpAnt;        
        final String sCarpFi    = sCarp;
        
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

                /*Muestra el reporte del contrarecibo*/
                JasperPrint pri;
                try
                {                
                    /*Crea los parámetros que se pasarán*/
                    Map <String,String> par = new HashMap<>();             
                    par.clear();
                    par.put("EMPLOC",               sNomFi);
                    par.put("CALLLOC",              sCallFi);
                    par.put("COLLOC",               sColFi);
                    par.put("CPLOC",                sCPFi);
                    par.put("CIUESTLOC",            sCiuEstFi);
                    par.put("TELLOC",               sTelFi);
                    par.put("RESPON",               sResponFi);
                    par.put("ID",                   sMaxFi);
                    par.put("LOG",                  sRutLogFi);
                    par.put("LOGE",                 Star.class.getResource(Star.sIconDef).toString());
                    
                    /*Llama al ticket de 52 o al normal*/   
                    JasperReport    ja;
                    if(s52Fi.compareTo("1")==0)
                        ja      = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptContra52.jrxml"));                                                       
                    else
                        ja      = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptContra52.jrxml"));                                        
                    
                    pri         = JasperFillManager.fillReport(ja, (Map)par, con);            
                }
                catch(JRException expnJASR)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace());                    
                    return;
                }      

                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Cambia la impresora predeterminada */
                Star.vCambImp(sImprFi);

                /*Manda una impresión*/
                try
                {
                    JasperPrintManager.printReport(pri,false);                    
                }
                catch(JRException expnJASR)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace());                                
                    return;
                } 

                /*Cambia la impresora predeterminada que estaba anteriormente*/
                Star.vCambImp(sImpAntFi);

                /*Crea la ruta completa*/
                String sRutContra        = sCarpFi + "\\Contrarecibos";                                       

                /*Si la ruta no existe entonces creala*/
                if(!new File(sRutContra).exists())
                {
                    try
                    {
                        new File(sRutContra).mkdir();
                    }
                    catch(SecurityException expnSecuri)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSecuri.getMessage(), Star.sErrSecuri, expnSecuri.getStackTrace(), con);                        
                        return;
                    }
                }

                /*Completa la ruta al contrarecibo*/
                sRutContra        = sRutContra + "\\" + sMaxFi + ".pdf";                                       

                /*Exportalo a pdf en el directorio completo con el nom del código del ticket*/
                try
                {
                    JasperExportManager.exportReportToPdfFile(pri, sRutContra);                                      
                }
                catch(JRException expnJASR)
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                
                }   

        }}).start();

        /*Borra todos los item en la tabla de contrarecibo*/
        DefaultTableModel dm = (DefaultTableModel)jTab2.getModel();
        dm.setRowCount(0);

        /*Carga nuevamente los contrarecibos*/
        vCargComp();

        /*Reinicia el campo del responsable*/
        jTRespon.setText("");
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Contrarecibo generado con éxito.", "Contrarecibo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBGenActionPerformed
   
   
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
    /*Cuando se presiona una tecla en el botón de abonar*/
    private void jBGenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGenKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGenKeyPressed

        
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

                    
    /*Cuando se presiona una  tecla en la tabla de comprs*/
    private void jTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTab1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTab1KeyPressed
    
                                              
    /*Cuando se presiona una tecla en la tabla de contrarecibos*/
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
        
        /*Obtén comprs de la base de datos y cargalos en la tabla*/
        vCargComp();
        
    }//GEN-LAST:event_jCPeActionPerformed

    
    /*Cuando sucede una acción en el checkbox de confirmados*/
    private void jCCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCCoActionPerformed

        /*Obtén comprs de la base de datos y cargalos en la tabla*/
        vCargComp();
       
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
        
        /*Obtén comprs de la base de datos y cargalos en la tabla*/
        vCargComp();
        
    }//GEN-LAST:event_jDTDePropertyChange

    
    /*Cuando cambia la fecha en el control a*/
    private void jDTAPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDTAPropertyChange
        
        /*Obtén comprs de la base de datos y cargalos en la tabla*/
        vCargComp();
        
    }//GEN-LAST:event_jDTAPropertyChange

    
    /*Cuando se gana el foco del teclado en el campo del proveedor*/
    private void jTProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTProv.setSelectionStart(0);jTProv.setSelectionEnd(jTProv.getText().length());        
        
    }//GEN-LAST:event_jTProvFocusGained

    
    /*Cuando se presiona una tecla en el campo del proveedor*/
    private void jTProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTProv.getText(), 3, jTProv, jTNomProv, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProvKeyPressed

    
    /*Cuando se presiona el botón de búscar*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProv.getText(), 3, jTProv, jTNomProv, null, "", null);
        b.setVisible(true);
        
        /*Coloca el foco del teclado en el campo del código de la empresa*/
        jTProv.grabFocus();
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se tipea una tecla en el campo del proveedor*/
    private void jTProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTProvKeyTyped

    
    /*Cuando se pierde el foco del teclado en el control del proveedor*/
    private void jTProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusLost

        /*Coloca el cursor al principio del control*/
        jTProv.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTProv.getText().compareTo("")!=0)
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Carga todos loscomprsen la tabla*/
        vCargComp();
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si la empresa existe*/
        try
        {
            sQ = "SELECT nom FROM provs WHERE CONCAT_WS('', ser, prov ) = '" + jTProv.getText() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la consulta en el control*/
            if(rs.next())
                jTNomProv.setText(rs.getString("nom"));                                                         
            /*Else no existe, entonces coloca cadena vacia en el campo*/
            else
                jTNomProv.setText("");                         
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }  
	        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jTProvFocusLost

       
    /*Cuando se presiona una tecla en el botón de búscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del responsable*/
    private void jTResponFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTResponFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRespon.setSelectionStart(0);jTRespon.setSelectionEnd(jTRespon.getText().length());      
        
    }//GEN-LAST:event_jTResponFocusGained

    
    /*Cuando se presiona una tecla en el campo del responsable*/
    private void jTResponKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTResponKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTResponKeyPressed

    
    /*Cuando se hace clic en la tabla de comprs*/
    private void jTab1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTab1MouseClicked
        
        /*Si se dierón dos clics entonces*/        
        if(evt.getClickCount()==2)
        {
            /*Obtiene la fila seleccionada*/
            int row         = jTab1.getSelectedRow();
            
            /*Lee todos los datos de la fila seleccionada*/
            String sComp    = jTab1.getValueAt(row, 1).toString();
            String sProv    = jTab1.getValueAt(row, 2).toString();
            String sNom     = jTab1.getValueAt(row, 3).toString();
            String sImp     = jTab1.getValueAt(row, 4).toString();
            String sImpue   = jTab1.getValueAt(row, 5).toString();
            String sTot     = jTab1.getValueAt(row, 6).toString();
            String sFech    = jTab1.getValueAt(row, 7).toString();
            String sFVenc   = jTab1.getValueAt(row, 8).toString(); 
            String sSucu    = jTab1.getValueAt(row, 9).toString();             
            String sEstac   = jTab1.getValueAt(row, 10).toString();                       
            String sCaj     = jTab1.getValueAt(row, 11).toString();
            String sNomb    = jTab1.getValueAt(row, 12).toString();
            String sContra  = jTab1.getValueAt(row, 13).toString();
            
            /*Comprueba si la compra esta ya cargada*/
            for(int x = 0; x < jTab2.getRowCount(); x++)
            {
                /*Compara la compra*/
                if(jTab2.getValueAt(x, 1).toString().compareTo(sComp)==0)
                {
                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "Esta compra ya esta para contrarecibo.", "Contrarecibo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                         
                    return;
                }
            }
            
            /*Cargalos en la tabla de contrarecibos*/            
            DefaultTableModel te    = (DefaultTableModel)jTab2.getModel();
            Object nu[]             = {iContFiContra, sComp, sProv, sNom, sImp, sImpue, sTot, sFech, sFVenc, sSucu, sCaj, sEstac, sNomb, sContra };
            te.addRow(nu);
            
            /*Aumenta el contador de filas*/
            ++iContFiContra;
            
            /*Calcula nuevamente el total*/
            vCalcTot();
        }
        
    }//GEN-LAST:event_jTab1MouseClicked

    
    /*Calcula nuevamente el total global*/
    private void vCalcTot()
    {
        /*Recorre toda la tabla de contrarecibos*/
        String sTot = "0";
        for(int x = 0; x < jTab2.getRowCount(); x++)
        {
            /*Suma el total de la fila al total global*/
            sTot    = Double.toString(Double.parseDouble(sTot) + Double.parseDouble(jTab2.getValueAt(x, 6).toString().replace("$", "").replace("$", "")));
        }
        
        /*Dale formato de moneda nuevamente al total*/                
        double dCant    = Double.parseDouble(sTot);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTot            = n.format(dCant);
        
        /*Coloca el total en su lugar*/
        jTTot.setText(sTot);
        
    }/*Fin de private void vCalcTot()*/
    
            
    /*Cuando se da un clic en la tabla de contrarecibos*/
    private void jTab2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTab2MouseClicked
        
        /*Si se dierón dos clics entonces*/        
        if(evt.getClickCount()==2)
        {
            /*Obtiene la fila seleccionada*/
            int row         = jTab2.getSelectedRow();
            
            /*Borralo de la tabla*/
            DefaultTableModel te = (DefaultTableModel)jTab2.getModel();
            te.removeRow(row);
            
            /*Resta en uno el contador de filas*/
            ++iContFiContra;
            
            /*Calcula nuevamente el total*/
            vCalcTot();            
        }
        
    }//GEN-LAST:event_jTab2MouseClicked

    
    /*Cuando se presiona una tecla en el botón de ver contrarecibos*/
    private void jBVerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVerKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVerKeyPressed

    
    /*Cuando se presiona el botón de ver*/
    private void jBVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerActionPerformed
        
        /*Muestra la forma para ver los contrarecibos*/
        VContras v = new VContras();
        v.setVisible(true);
        
    }//GEN-LAST:event_jBVerActionPerformed

    
    /*Cuando se presiona el botón de actualizar*/
    private void jBActuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActuaActionPerformed

        /*Función para cargar todos los elementos en la tabla*/        
        vCargComp();

    }//GEN-LAST:event_jBActuaActionPerformed

    
    /*Cuando se presiona el botón de actualizar*/
    private void jBActuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBActuaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBActuaKeyPressed

    
    /*Cuando se arrastrae el mouse en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve el ratón en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando la rueda del ratón se mueve en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se presiona el botón de mostrar tabla 1*/
    private void jBTab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab1ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab1);       

    }//GEN-LAST:event_jBTab1ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de mostrar tabla 1*/
    private void jBTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTab1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTab1KeyPressed

    
    /*Cuando se presiona una tecla en el botón de mostrar tabla 2*/
    private void jBTab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab2ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab2);       

    }//GEN-LAST:event_jBTab2ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de mostrar tabla 2*/
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

    
    /*Cuiando se pierde el foco del teclado en el control del responsable*/
    private void jTResponFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTResponFocusLost

        /*Coloca el cursor al principio del control*/
        jTRespon.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTRespon.getText().compareTo("")!=0)
            jTRespon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTResponFocusLost

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBuscMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGenMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGen.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGenMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVerMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVer.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVerMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBActuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBActua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBActuaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(colOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGenMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGen.setBackground(colOri);
        
    }//GEN-LAST:event_jBGenMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVerMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVer.setBackground(colOri);
        
    }//GEN-LAST:event_jBVerMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBActuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBActua.setBackground(colOri);
        
    }//GEN-LAST:event_jBActuaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se pierde el foco del teclado en el campo del nombre del proveedor*/
    private void jTNomProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNomProvFocusLost

        /*Coloca el cursor al principio del control*/
        jTNomProv.setCaretPosition(0);
        
    }//GEN-LAST:event_jTNomProvFocusLost
    
    
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
        /*Si se presiona F2 presiona el botón de ver*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            jBVer.doClick();
        /*Si se presiona F5 presiona el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBGen;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTab2;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBVer;
    private javax.swing.JCheckBox jCCo;
    private javax.swing.JCheckBox jCPe;
    private com.toedter.calendar.JDateChooser jDTA;
    private com.toedter.calendar.JDateChooser jDTDe;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTNomProv;
    private javax.swing.JTextField jTProv;
    private javax.swing.JTextField jTRespon;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTable jTab1;
    private javax.swing.JTable jTab2;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
