/*Paquete*/
package cats;

/*Importaciones*/
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Toolkit;
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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import ptovta.Star;
import ptovta.ImgVis;
import ptovta.LoadinGral;
import ptovta.Login;
import static ptovta.Princip.bIdle;
import vis.VisAltPers;



/*Clase para dar de alta a las personas*/
public class AltPers extends javax.swing.JFrame 
{
    /*Declara variables de instancia*/    
    private String                  sNomOri;
    private int                     iContFil;
        
    /*Contiene la dirección de la forma para ver imágen en otra vista*/
    private ImgVis                  v;
    
    /*Contador para modificar tabla*/
    private int                     iContCellEd;

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean                 bSel;

    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Variable que contiene el borde actual*/
    private Border                  bBordOri;
    
    
    
    
    /*Constructor sin argumentos*/
    public AltPers() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        Star.lCargGral=null;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Deshabilita que no se mueven las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Establece el titulo de la ventana con el usuario, la fec y hora*/                
        this.setTitle("Alta Personas, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);                               
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Para que la tabla de personas este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
                
        /*Cambia el icono de la forma, ya sea el personalizado por el usuario o el de default del sistema*/
        if(new File(new java.io.File("").getAbsolutePath() + "\\Logo.jpg").exists())
        {
            setIconImage(Toolkit.getDefaultToolkit().getImage(new java.io.File("").getAbsolutePath() + "\\Logo.jpg"));
        }
        else
            setIconImage(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());
        
        /*Inicializa el contador de filas en uno*/
        iContFil          = 1;
        
        /*Inicialmente el label de imágen no será visible, se vuelve visible cuando se carga una imágen*/
        jLImg.setVisible(false);
        
        /*Establece el tamaño de la columna del nombre de la presona*/
        jTab.getColumnModel().getColumn(1).setPreferredWidth(320);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Establece los campos de fecha para que solo se puedan modificar con el botón*/
        jDatIngEmp.getDateEditor().setEnabled(false);
        jDatSalEmp.getDateEditor().setEnabled(false);
        
        /*Deshabilita inicialmente el control de fecha de salida*/
        jDatSalEmp.setEnabled(false);
            
        /*Coloca el botón de guardar cambios a deshabilitado ya que no se pueden guardar cambios para una persona nueva*/
        jBGuar.setEnabled(false);
        
        /*Selecciona una fecha en el de ingreso de la empresa inicialmente*/
        Date FIni = new Date();
        jDatIngEmp.setDate(FIni);
        
        /*Selecciona una fecha en el de salida de la empresa inicialmente*/        
        jDatSalEmp.setDate(FIni);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Pon el foco del teclado en el campo de edición de persona*/
        jTNomb.grabFocus();
        
        /*Carga los nombres de las personas de la base de datos en la tabla*/
        vCargrPers();
        
        /*Crea el listener para cuando se cambia de selección en la tabla de personas*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {
                /*Carga todos los datos de la fila de la tabla de personas en los campos*/
                vCargFil();               
            }
        });
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para cuando se cambia de selección en la tabla*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtén la propiedad que a sucedio en el control*/
                String pr = event.getPropertyName();                                
                                
                /*Obtiene la fila seleccionada*/                
                if(jTab.getSelectedRow()==-1)
                     return;
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pr)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sNomOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sNomOri,       jTab.getSelectedRow(), 1);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public Personas() */

    
    /*Carga los nombres de las personas de la base de datos en la tabla*/
    private void vCargrPers()
    {   
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();                    

        /*Declara variables de la base de datos*/    
        Statement   st;
        ResultSet   rs;        
        String      sQ           = "";
        
        /*Trae todas personas de la base de datos y cargalos en la tabla*/
        try
        {
            sQ = "SELECT nom FROM pers";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[] = {iContFil, rs.getString("nom")};
                te.addRow(nu);
                
                /*Aumenta el contador de filas*/
                ++iContFil;                
            }
                        
        }/*Fin de try*/
        catch(SQLException e)
        {            
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                                
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }            
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vCargrPers()*/
        
    
    /*Carga todos los datos de la fila de la tabla de personas en los campos*/
    private void vCargFil()
    {        
        //Contiene el nombre
        String      sNomb          = "";        
        
        
        
        /*Obtén el nombre de la persona de la fila seleccionada*/
        int row         = jTab.getSelectedRow();                       
        if(row != -1)
            sNomb     = jTab.getValueAt(row, 1).toString();   
        
        /*Guarda el nombre original por si después quiere cambiarle el nombre*/
        sNomOri = sNomb;

        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        //Declara variables locales
        double      dCant;        
        String      sSueldAct;
        String      sSueldFora;
        String      sTExt;        
        String      sCarp;        

        /*Declara variables de la base de datos*/    
        Statement   st;
        ResultSet   rs;        
        String      sQ           = "";
        
        /*Trae todos los datos del registro del nombre de la persona*/
        try
        {
            sQ = "SELECT sueldactua, sueldfora, textra, calle, col, noext, noint, cp, cel1, cel2, tel1, tel2, falt, fmod, teleme1, teleme2, contac1, contac2, act, fingemp, fsalemp FROM pers WHERE nom = '" + sNomb + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene el sueldo actual*/
                sSueldAct           = rs.getString("sueldactua");   
                
                /*Formatealo*/
                dCant               = Double.parseDouble(sSueldAct);
                NumberFormat n      = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sSueldAct           = n.format(dCant);
                
                /*Obtiene el sueldo foraneo*/
                sSueldFora          = rs.getString("sueldfora");   
                
                /*Formatealo*/
                dCant               = Double.parseDouble(sSueldFora);
                n                   = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sSueldFora          = n.format(dCant);
                
                /*Obtiene el tiempo extra*/
                sTExt               = rs.getString("textra"); 
                
                /*Formatealo*/
                dCant               = Double.parseDouble(sTExt);
                n                   = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sTExt               = n.format(dCant);
                       
                /*Habilita el boton de guardar cambios*/
                jBGuar.setEnabled (true);
                
                /*Coloca en los controles los valores obtenidos*/
                jTNomb.setText      (sNomb);
                jTCall.setText      (rs.getString("calle"));
                jTCol.setText       (rs.getString("col"));
                jTNoExt.setText     (rs.getString("noext"));
                jTNoInt.setText     (rs.getString("noint"));
                jTCP.setText        (rs.getString("cp"));
                jTCel1.setText      (rs.getString("cel1"));
                jTCel2.setText      (rs.getString("cel2"));
                jTTel1.setText      (rs.getString("tel1"));
                jTTel2.setText      (rs.getString("tel2"));
                jLFCrea.setText     (rs.getString("falt"));
                jLFMod.setText      (rs.getString("fmod"));                
                jTTelEm1.setText    (rs.getString("teleme1"));
                jTTelEme2.setText   (rs.getString("teleme2"));
                jTCont1.setText     (rs.getString("contac1"));
                jTCon2.setText      (rs.getString("contac2"));
                jTSuelAct.setText   (sSueldAct);
                jTSuelFor.setText   (sSueldFora);
                jTieExt.setText     (sTExt);
                
                /*Coloca la fecha de ingreso en la empresa*/
                SimpleDateFormat form   = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date fec                = form.parse(rs.getString("fingemp"));
                jDatIngEmp.setDate(fec);
                
                /*Coloca la fecha de salida de la empresa*/                
                fec                     = form.parse(rs.getString("fsalemp"));
                jDatSalEmp.setDate(fec);    
                                
                /*Activa el checkbox si esta una persona activa o no*/
                if(rs.getString("act").compareTo("1")==0)
                    jCAct.setSelected(true);
                else
                    jCAct.setSelected(false);
                
            }/*Fin de while (rs.next())*/

        }/*Fin de try*/
        catch(SQLException | NumberFormatException | ParseException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }            

        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        sCarp       = Star.sGetRutCarp(con);                    

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
        
        /*Si la carpeta de las imágenes no existe entonces crea la carpeta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de las personas no existe entonces crea la carpeta*/
        sCarp                    += "\\Personas";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la persona en específico no existe entonces crea la carpeta*/
        sCarp                    += "\\" + sNomb;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe en la ruta entonces*/                
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            if(new File(sCarp).list().length > 0)
            {            
                /*Obtiene la lista de directorios*/
                String sArchs [] = new File(sCarp).list();

                /*Carga la imágen en el panel*/
                jLImg.setIcon(new ImageIcon(sCarp + "\\" + sArchs[0]));

                /*Que el label sea visible*/
                jLImg.setVisible(true);
            }
            /*Else, no existe imágen entonces*/
            else
            {
                /*Que el label con la imágen no sea visible*/
                jLImg.setVisible(false);
            }
        }                    
        
    }/*Fin de private void vCargFil()*/   
            
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jTNomb = new javax.swing.JTextField();
        jBDel = new javax.swing.JButton();
        jTCall = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTCol = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTNoExt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTNoInt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTCP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTTel1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTTel2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTCel1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTCel2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jDatIngEmp = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jDatSalEmp = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jTTelEm1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTCont1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTTelEme2 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTCon2 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTSuelAct = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTSuelFor = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTieExt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBGuar = new javax.swing.JButton();
        jBLim = new javax.swing.JButton();
        jBBusc = new javax.swing.JButton();
        jBMosT = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLFMod = new javax.swing.JLabel();
        jLFCrea = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jBCargImg = new javax.swing.JButton();
        jBDelImg = new javax.swing.JButton();
        jCAct = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLImg = new javax.swing.JLabel();
        jBVeGran = new javax.swing.JButton();
        jBVis = new javax.swing.JButton();
        jCMosT = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jBTabG = new javax.swing.JButton();
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Personas:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 170, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jBCargImg);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 150, 130, 30));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre8.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nueva Persona(Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jBLim);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 130, 30));

        jTNomb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNomb.setNextFocusableComponent(jTCall);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTNombKeyTyped(evt);
            }
        });
        jP1.add(jTNomb, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 210, 20));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del5.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Persona (Ctrl+SUPR)");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jBNew);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, 130, 30));

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
        jP1.add(jTCall, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 210, 20));

        jLabel2.setText("*Nombre:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, -1));

        jLabel3.setText("Colonia:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 88, 130, -1));

        jTCol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCol.setNextFocusableComponent(jTNoExt);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTColKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTColKeyTyped(evt);
            }
        });
        jP1.add(jTCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 105, 210, 20));

        jLabel4.setText("No. Exterior:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 127, 130, -1));

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTNoExtKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTNoExtKeyTyped(evt);
            }
        });
        jP1.add(jTNoExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 143, 210, 20));

        jLabel5.setText("No. Interior:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 167, 130, -1));

        jTNoInt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTNoInt.setNextFocusableComponent(jTCP);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTNoIntKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTNoIntKeyTyped(evt);
            }
        });
        jP1.add(jTNoInt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 182, 210, 20));

        jLabel6.setText("CP:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 205, 130, -1));

        jTCP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCP.setNextFocusableComponent(jTTel1);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTCPKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCPKeyTyped(evt);
            }
        });
        jP1.add(jTCP, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 210, 20));

        jLabel7.setText("Calle:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 53, 130, -1));

        jLabel8.setText("Teléfono 1:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 130, -1));

        jTTel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel1.setNextFocusableComponent(jTTel2);
        jTTel1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTel1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTel1FocusLost(evt);
            }
        });
        jTTel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTel1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTTel1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTel1KeyTyped(evt);
            }
        });
        jP1.add(jTTel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 210, 20));

        jLabel9.setText("Teléfono 2:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 284, 130, -1));

        jTTel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTel2.setNextFocusableComponent(jTCel1);
        jTTel2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTel2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTel2FocusLost(evt);
            }
        });
        jTTel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTel2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTTel2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTel2KeyTyped(evt);
            }
        });
        jP1.add(jTTel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 210, 20));

        jLabel10.setText("Celular 1:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 324, 130, -1));

        jTCel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCel1.setNextFocusableComponent(jTCel2);
        jTCel1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCel1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCel1FocusLost(evt);
            }
        });
        jTCel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCel1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTCel1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCel1KeyTyped(evt);
            }
        });
        jP1.add(jTCel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 210, 20));

        jLabel11.setText("Celular 2:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 365, 130, -1));

        jTCel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCel2.setNextFocusableComponent(jDatIngEmp);
        jTCel2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCel2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCel2FocusLost(evt);
            }
        });
        jTCel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCel2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTCel2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCel2KeyTyped(evt);
            }
        });
        jP1.add(jTCel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 210, 20));

        jLabel12.setText("Fecha de Ingreso a la Empresa:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 210, -1));

        jDatIngEmp.setNextFocusableComponent(jDatSalEmp);
        jDatIngEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDatIngEmpKeyPressed(evt);
            }
        });
        jP1.add(jDatIngEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 210, -1));

        jLabel13.setText("Fecha de Salida de la Empresa:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 210, -1));

        jDatSalEmp.setNextFocusableComponent(jTTelEm1);
        jDatSalEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDatSalEmpKeyPressed(evt);
            }
        });
        jP1.add(jDatSalEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 210, -1));

        jLabel14.setText("Teléfono de Emergencia:");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 170, -1));

        jTTelEm1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelEm1.setNextFocusableComponent(jTCont1);
        jTTelEm1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelEm1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelEm1FocusLost(evt);
            }
        });
        jTTelEm1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelEm1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTTelEm1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelEm1KeyTyped(evt);
            }
        });
        jP1.add(jTTelEm1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 210, 20));

        jLabel15.setText("A quién contactar:");
        jP1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 130, -1));

        jTCont1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCont1.setNextFocusableComponent(jTSuelAct);
        jTCont1.setOpaque(false);
        jTCont1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCont1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCont1FocusLost(evt);
            }
        });
        jTCont1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCont1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTCont1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCont1KeyTyped(evt);
            }
        });
        jP1.add(jTCont1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 210, 20));

        jLabel16.setText("Telèfono de Emergencia 2:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 480, 200, -1));

        jTTelEme2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTelEme2.setNextFocusableComponent(jTCon2);
        jTTelEme2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTelEme2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTelEme2FocusLost(evt);
            }
        });
        jTTelEme2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTelEme2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTTelEme2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTTelEme2KeyTyped(evt);
            }
        });
        jP1.add(jTTelEme2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 500, 210, 20));

        jLabel17.setText("A quién contactar:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 520, 130, -1));

        jTCon2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCon2.setNextFocusableComponent(jTieExt);
        jTCon2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCon2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCon2FocusLost(evt);
            }
        });
        jTCon2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCon2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTCon2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCon2KeyTyped(evt);
            }
        });
        jP1.add(jTCon2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 540, 210, 20));

        jLabel18.setText("Sueldo Actual:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 400, 170, -1));

        jTSuelAct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSuelAct.setNextFocusableComponent(jCAct);
        jTSuelAct.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSuelActFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSuelActFocusLost(evt);
            }
        });
        jTSuelAct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSuelActKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTSuelActKeyTyped(evt);
            }
        });
        jP1.add(jTSuelAct, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 420, 210, 20));

        jLabel19.setText("Sueldo Foraneo:");
        jP1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 440, 130, -1));

        jTSuelFor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSuelFor.setNextFocusableComponent(jBGuar);
        jTSuelFor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSuelForFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSuelForFocusLost(evt);
            }
        });
        jTSuelFor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSuelForKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTSuelForKeyTyped(evt);
            }
        });
        jP1.add(jTSuelFor, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 460, 210, 20));

        jLabel20.setText("X hora.");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(543, 429, 70, -1));

        jLabel21.setText("Tiempo Extra:");
        jP1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 400, 170, -1));

        jTieExt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTieExt.setNextFocusableComponent(jTSuelFor);
        jTieExt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTieExtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTieExtFocusLost(evt);
            }
        });
        jTieExt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTieExtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTieExtKeyTyped(evt);
            }
        });
        jP1.add(jTieExt, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 420, 70, 20));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 450, 340));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar Cambios");
        jBGuar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBGuar.setNextFocusableComponent(jBDel);
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, 130, 30));

        jBLim.setBackground(new java.awt.Color(255, 255, 255));
        jBLim.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBLim.setForeground(new java.awt.Color(0, 102, 0));
        jBLim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/limp.png"))); // NOI18N
        jBLim.setText("Limpiar");
        jBLim.setToolTipText("Limpiar Todos los Campos (F6)");
        jBLim.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBLim.setNextFocusableComponent(jBSal);
        jBLim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBLimMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBLimMouseExited(evt);
            }
        });
        jBLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimActionPerformed(evt);
            }
        });
        jBLim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBLimKeyPressed(evt);
            }
        });
        jP1.add(jBLim, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 120, 130, 30));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBBusc.setForeground(new java.awt.Color(0, 102, 0));
        jBBusc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/busc5.png"))); // NOI18N
        jBBusc.setText("Buscar F3");
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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 370, 140, 20));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 370, 140, 20));

        jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 370, 170, 20));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setText("Modificado:");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 490, 80, -1));

        jLFMod.setForeground(new java.awt.Color(51, 51, 255));
        jLFMod.setText("-");
        jP1.add(jLFMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 500, 160, -1));

        jLFCrea.setForeground(new java.awt.Color(51, 51, 255));
        jLFCrea.setText("-");
        jP1.add(jLFCrea, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 500, 170, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setText("Creado:");
        jP1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 490, 50, -1));

        jBCargImg.setBackground(new java.awt.Color(255, 255, 255));
        jBCargImg.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBCargImg.setText("Cargar");
        jBCargImg.setNextFocusableComponent(jBDelImg);
        jBCargImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCargImgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCargImgMouseExited(evt);
            }
        });
        jBCargImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCargImgActionPerformed(evt);
            }
        });
        jBCargImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCargImgKeyPressed(evt);
            }
        });
        jP1.add(jBCargImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 370, 62, -1));

        jBDelImg.setBackground(new java.awt.Color(255, 255, 255));
        jBDelImg.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jBDelImg.setText("Borrar");
        jBDelImg.setNextFocusableComponent(jBVeGran);
        jBDelImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDelImgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDelImgMouseExited(evt);
            }
        });
        jBDelImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDelImgActionPerformed(evt);
            }
        });
        jBDelImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDelImgKeyPressed(evt);
            }
        });
        jP1.add(jBDelImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 370, 60, -1));

        jCAct.setBackground(new java.awt.Color(255, 255, 255));
        jCAct.setSelected(true);
        jCAct.setText("Activo");
        jCAct.setNextFocusableComponent(jTTelEme2);
        jCAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCActActionPerformed(evt);
            }
        });
        jCAct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCActKeyPressed(evt);
            }
        });
        jP1.add(jCAct, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 460, 80, -1));

        jLImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLImgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLImgMouseExited(evt);
            }
        });
        jLImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLImgKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jLImg);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 230, 150, 140));

        jBVeGran.setBackground(new java.awt.Color(255, 255, 255));
        jBVeGran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBVeGran.setToolTipText("Ver Imágen Completa");
        jBVeGran.setNextFocusableComponent(jBVis);
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
        jP1.add(jBVeGran, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 370, 30, 20));

        jBVis.setBackground(new java.awt.Color(255, 255, 255));
        jBVis.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBVis.setForeground(new java.awt.Color(0, 102, 0));
        jBVis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/visor.png"))); // NOI18N
        jBVis.setText("Reporte");
        jBVis.setToolTipText("Reporte de Productos (F7)");
        jBVis.setNextFocusableComponent(jCMosT);
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
        jP1.add(jBVis, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 410, 110, 30));

        jCMosT.setBackground(new java.awt.Color(255, 255, 255));
        jCMosT.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCMosT.setSelected(true);
        jCMosT.setText("Mostrar Todo");
        jCMosT.setNextFocusableComponent(jTNomb);
        jCMosT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCMosTKeyPressed(evt);
            }
        });
        jP1.add(jCMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 450, 100, -1));
        jP1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 390, -1, -1));

        jBTabG.setBackground(new java.awt.Color(0, 153, 153));
        jBTabG.setToolTipText("Mostrar Tabla en Grande");
        jBTabG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTabGActionPerformed(evt);
            }
        });
        jBTabG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTabGKeyPressed(evt);
            }
        });
        jP1.add(jBTabG, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(736, 550, 120, 20));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar Todo");
        jBTod.setToolTipText("Marcar Todos los Registros en la Tabla (Alt+T)");
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 12, 130, 18));

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
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
        /*Pregunta al usuario si esta seguro de salir*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
            
        /*Llama al recolector de basura y cierra la forma*/
        System.gc();
        this.dispose();        
            
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

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona el botón de agregar persona*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed

        /*Declara variables de la base de datos*/    
        Statement   st;
        ResultSet   rs;
        Connection  con;
        String      sQ           = "";
        
        /*Declara variables locales*/
        String      sNomb;        
        String      sCall;
        String      sCol;        
        String      sNoExt;
        String      sNoInt;
        String      sFCrea   = "";
        String      sCP;
        String      sTel1;
        String      sTel2;
        String      sAct;
        String      sCel1;
        String      sCel2;
        String      sFIngEmp;
        String      sFSalEmp;
        String      sTelEme1;
        String      sTelEme2;
        String      sConta1;
        String      sConta2;
        String      sSueldAct;
        String      sSueldFora;
        String      sTExt;
        
        
        
        /*Lee el nombre introducido por el usuario*/
        sNomb = jTNomb.getText().trim();
                        
        /*Si hay cadena vacia en el campo de edición de nombre de persona no puede continuar*/
        if(sNomb.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTNomb.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de nombre esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTNomb.grabFocus();                        
            return;            
        }
                
        /*Guarda el nombre original por si después quiere cambiarle el nombre*/
        sNomOri             = sNomb;
                
        /*Lee la calle*/
        sCall               = jTCall.getText();
        
        /*Lee la colonia*/
        sCol                = jTCol.getText();
        
        /*Lee si esta activo o no*/
        if(jCAct.isSelected())
            sAct            = "1";
        else
            sAct            = "0";
        
        /*Lee el número de exterior*/
        sNoExt              = jTNoExt.getText();
        
        /*Lee el número de interior*/
        sNoInt              = jTNoInt.getText();
        
        /*Lee el CP*/
        sCP                 = jTCP.getText();
        
        /*Lee el teléfono 1*/
        sTel1               = jTTel1.getText();
        
        /*Lee el teléfono 2*/
        sTel2               = jTTel2.getText();
        
        /*Lee el celular 1*/
        sCel1               = jTCel1.getText();
        
        /*Lee el celular 2*/
        sCel2               = jTCel2.getText();
        
        /*Lee fecha de ingreso en la empresa*/
        Date fec            = jDatIngEmp.getDate();
        SimpleDateFormat sdf= new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        sFIngEmp            = sdf.format(fec);                       
        
        /*Lee fec de salida de la empresa*/
        fec                 = jDatSalEmp.getDate();
        sdf                 = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        sFSalEmp            = sdf.format(fec);               
        
        /*Lee el teléfono de emergencia 1*/
        sTelEme1            = jTTelEm1.getText();
        
        /*Lee el teléfono de emergencia 2*/
        sTelEme2            = jTTelEme2.getText();
        
        /*Lee a quien contactar 1*/
        sConta1             = jTCont1.getText();
        
        /*Lee a quien contactar 2*/
        sConta2             = jTCon2.getText();
        
        /*Lee el sueldo actual*/
        sSueldAct           = jTSuelAct.getText();
        
        /*Si tiene el signo de dollar quitarcelo*/
        sSueldAct           = sSueldAct.replace("$", "");
        
        /*Si tiene coma el sueldo actual quitaselo*/
        sSueldAct           = sSueldAct.replace(",", "");
        
        /*Si el sueldo es cadena vacia ponerlo en 0 para que lo reciba la base de datos*/
        if(sSueldAct.compareTo("")==0)
            sSueldAct       = "0";
        
        /*Lee el sueldo foraneo*/
        sSueldFora          = jTSuelFor.getText();
        
        /*Si tiene el signo de dollar quitarcelo*/
        sSueldFora          = sSueldFora.replace("$", "");
        
        /*Si tiene coma el sueldo foraneo quitaselo*/
        sSueldFora          = sSueldFora.replace(",", "");
        
        /*Si el sueldo foraneo es cadena vacia ponerlo en 0 para que lo reciba la base de datos*/
        if(sSueldFora.compareTo("")==0)
            sSueldFora      = "0";
        
        /*Lee el tiempo extra*/
        sTExt               = jTieExt.getText();
        
        /*Si tiene el signo de dollar quitarcelo*/
        sTExt = sTExt.replace("$", "");
        
        /*Si tiene coma el tiempo extra quitaselo*/
        sTExt = sTExt.replace(",", "");
        
        /*Si el tiempo extra es cadena vacia ponerlo en 0 para que lo reciba la base de datos*/
        if(sTExt.compareTo("")==0)
            sTExt = "0";
        
        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Agregar Persona", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        //Abre la base de datos                             
        con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Comprueba si el nombre de la persona ya existe en la base de datos*/        
        try
        {
            sQ = "SELECT nom FROM pers WHERE nom = '" + sNomb + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El nombre: " + sNomb + " ya existe.", "Nombre Existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el campo de nombre y regresa*/
                jTNomb.grabFocus();                
                return;
            }
        }
        catch(SQLException | HeadlessException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }            
                
        /*Guarda los datos en la base de datos*/
        try 
        {               
            sQ = "INSERT INTO pers(nom,                             falt,       calle,                                  col,                                    noext,                                  noint,                                  cp,                                 tel1,                               tel2,                                   cel1,                               cel2,                               fingemp,                                    fsalemp,                                teleme1,                                    teleme2,                                    contac1,                                contac2,                                sueldactua,                             textra,                             sueldfora,                                  estac,                                          act,           sucu,                                            nocaj) " + 
                      "VALUES('" + sNomb.replace("'", "''") + "',   now(),'" +  sCall.replace("'", "''") + "','" +      sCol.replace("'", "''") + "','" +       sNoExt.replace("'", "''") + "','" +     sNoInt.replace("'", "''") + "','" +     sCP.replace("'", "''") + "','" +    sTel1.replace("'", "''") + "','" +  sTel2.replace("'", "''") + "', '" +     sCel1.replace("'", "''") + "','" +  sCel2.replace("'", "''") + "','" +  sFIngEmp.replace("'", "''") + "','" +       sFSalEmp.replace("'", "''") + "','" +   sTelEme1.replace("'", "''") + "','" +       sTelEme2.replace("'", "''") + "','" +       sConta1.replace("'", "''") + "','" +    sConta2.replace("'", "''") + "'," +     sSueldAct.replace("'", "''") + "," +    sTExt.replace("'", "''") + "," +    sSueldFora.replace("'", "''") + ", '" +     Login.sUsrG.replace("'", "''") + "', " +    sAct + ",'" +  Star.sSucu.replace("'", "''") + "','" +    Star.sNoCaj.replace("'", "''") + "')";                                            
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException ex) 
         { 
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            /*Agrega en el log*/
            Login.vLog(ex.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
         }
        
        /*Obtiene la fec de creación del producto*/
	try
        {                  
            sQ = "SELECT falt FROM pers WHERE nom = '" + sNomb + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sFCrea      = rs.getString("falt");                                                                           
            
        }/*Fin de try*/
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Agrega el nombre de la persona en la tabla*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFil, sNomb};
        te.addRow(nu);
        
        /*Pon el foco del teclado en el campo de persona*/
        jTNomb.grabFocus();
        
        /*Aumenta en uno el contador de filas*/
        ++iContFil;
        
        /*Coloca la fec de alta y de última modificación en los campos*/
        jLFCrea.setText         (sFCrea);
        jLFMod.setText          (sFCrea); 
        
        /*Habilita el botón de guardar cambios*/
        jBGuar.setEnabled     (true);
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Persona: " + sNomb + " agregada con éxito.", "Exito al agregar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
        
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se presiona una tecla en el botón de agregar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona una tecla en el campo de persona*/
    private void jTNombKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNombKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNombKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de persona*/
    private void jTNombFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNombFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNomb.setSelectionStart(0);        
        jTNomb.setSelectionEnd(jTNomb.getText().length());        
        
    }//GEN-LAST:event_jTNombFocusGained
    
    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
                
        /*Si no hay selección en la tabla de personas no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una persona de la lista para borrar.", "Borrar Persona", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();            
            return;
            
        }/*Fin de if(jListPersonas.getSelectedIndex()==-1)*/
        
        /*Preguntar al usuario si esta seguro de querer borrar a la persona*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere borrar a la persona?", "Borrar Persona", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Declara variables de la base de datos*/    
        Statement   st;
        ResultSet   rs;        
        String      sQ           = "";
        
        //Contiene el nombre
        String      sNomb;                
        
        /*Recorre toda la selección del usuario*/
        int iSel[] = jTab.getSelectedRows();
        DefaultTableModel te  = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x++)
        {
            /*Obtén el nombre de la persona seleccionada*/        
            sNomb       = jTab.getValueAt(iSel[x], 1).toString();          
         
            /*Comprueba si la persona ya esta cargada en algún proyecto*/        
            try
            {
                sQ = "SELECT nompers FROM proys WHERE nompers LIKE('%" + sNomb + "%')";                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos, entonces ya esta esta persona en algún proyecto*/
                if(rs.next())
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;
                    
                   /*Mensajea y continua*/
                    JOptionPane.showMessageDialog(null, "Ya existen proyectos con esta persona: " + sNomb + ", no se puede eliminar.", "Personas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                     
                    continue; 
                }

            }/*Fin de try*/
            catch(SQLException e)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Agrega en el log*/
                Login.vLog(e.getMessage());

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                return;
            }  

            /*Borra la persona*/
            try 
            {                
                sQ = "DELETE FROM pers WHERE nom = '" + sNomb + "'";                                            
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException ex) 
             { 
                 //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                return;
             }      
            
            /*Borralo de la tabla*/            
            te.removeRow(iSel[x]);
            
            /*Resta en uno el contador de filas el contador de filas en uno*/
            --iContFil;

        }                                                                          
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
                   
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Persona borrada con éxito.", "Personas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se tipea una tecla en el campo de nombre*/
    private void jTNombKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNombKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTNombKeyTyped

           
    /*Cuando se pierde el foco del teclado en el campo de nombre de persona*/
    private void jTNombFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNombFocusLost

        /*Coloca el cursor al principio del control*/
        jTNomb.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTNomb.getText().compareTo("")!=0)
            jTNomb.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNomb.getText().length()> 255)
            jTNomb.setText(jTNomb.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTNombFocusLost

    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se gana el foco del teclado en el campo de callee*/
    private void jTCallFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCall.setSelectionStart(0);        
        jTCall.setSelectionEnd(jTCall.getText().length());        
        
    }//GEN-LAST:event_jTCallFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de colonia*/
    private void jTColFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCol.setSelectionStart(0);        
        jTCol.setSelectionEnd(jTCol.getText().length());        
        
    }//GEN-LAST:event_jTColFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de exterior*/
    private void jTNoExtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoExt.setSelectionStart(0);        
        jTNoExt.setSelectionEnd(jTNoExt.getText().length());        
        
    }//GEN-LAST:event_jTNoExtFocusGained

    /*Cuando se gana el foco del teclado en el campo de número de interior*/
    private void jTNoIntFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNoInt.setSelectionStart(0);        
        jTNoInt.setSelectionEnd(jTNoInt.getText().length());        
        
    }//GEN-LAST:event_jTNoIntFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de CP*/
    private void jTCPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCP.setSelectionStart(0);        
        jTCP.setSelectionEnd(jTCP.getText().length());        
        
    }//GEN-LAST:event_jTCPFocusGained

    
    /*Cuando se gana el foco del telefono1*/
    private void jTTel1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel1.setSelectionStart(0);        
        jTTel1.setSelectionEnd(jTTel1.getText().length());        
        
    }//GEN-LAST:event_jTTel1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de teléfono 2*/
    private void jTTel2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTel2.setSelectionStart(0);        
        jTTel2.setSelectionEnd(jTTel2.getText().length());        
        
    }//GEN-LAST:event_jTTel2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de celular 1*/
    private void jTCel1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCel1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCel1.setSelectionStart(0);        
        jTCel1.setSelectionEnd(jTCel1.getText().length());        
        
    }//GEN-LAST:event_jTCel1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de celular 2*/
    private void jTCel2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCel2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCel2.setSelectionStart(0);        
        jTCel2.setSelectionEnd(jTBusc.getText().length());        
        
    }//GEN-LAST:event_jTCel2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de teléfono de emergencia 1*/
    private void jTTelEm1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelEm1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelEm1.setSelectionStart(0);        
        jTTelEm1.setSelectionEnd(jTTelEm1.getText().length());        
        
    }//GEN-LAST:event_jTTelEm1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de contactar 1*/
    private void jTCont1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCont1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCont1.setSelectionStart(0);        
        jTCont1.setSelectionEnd(jTCont1.getText().length());        
        
    }//GEN-LAST:event_jTCont1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de emergencia 2*/
    private void jTTelEme2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelEme2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTelEme2.setSelectionStart(0);        
        jTTelEme2.setSelectionEnd(jTTelEme2.getText().length());        
        
    }//GEN-LAST:event_jTTelEme2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de contactar 2*/
    private void jTCon2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCon2.setSelectionStart(0);        
        jTCon2.setSelectionEnd(jTCon2.getText().length());        
        
    }//GEN-LAST:event_jTCon2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de sueldo actual*/
    private void jTSuelActFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSuelActFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSuelAct.setSelectionStart(0);        
        jTSuelAct.setSelectionEnd(jTSuelAct.getText().length());        
        
    }//GEN-LAST:event_jTSuelActFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de sueldo foraneo*/
    private void jTSuelForFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSuelForFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSuelFor.setSelectionStart(0);        
        jTSuelFor.setSelectionEnd(jTBusc.getText().length());        
        
    }//GEN-LAST:event_jTSuelForFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de tiempo extra*/
    private void jTieExtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTieExtFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTieExt.setSelectionStart(0);        
        jTieExt.setSelectionEnd(jTieExt.getText().length());        
        
    }//GEN-LAST:event_jTieExtFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de callee*/
    private void jTCallFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCallFocusLost

        /*Coloca el cursor al principio del control*/
        jTCall.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCall.getText().length()> 255)
            jTCall.setText(jTCall.getText().substring(0, 255));
     
    }//GEN-LAST:event_jTCallFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de colonia*/
    private void jTColFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTColFocusLost

        /*Coloca el cursor al principio del control*/
        jTCol.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCol.getText().length()> 255)
            jTCol.setText(jTCol.getText().substring(0, 255));
      
    }//GEN-LAST:event_jTColFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de número de exterior*/
    private void jTNoExtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoExtFocusLost

        /*Coloca el cursor al principio del control*/
        jTNoExt.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNoExt.getText().length()> 21)
            jTNoExt.setText(jTNoExt.getText().substring(0, 21));
        
    }//GEN-LAST:event_jTNoExtFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de número de interior*/
    private void jTNoIntFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNoIntFocusLost

        /*Coloca el cursor al principio del control*/
        jTNoInt.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTNoInt.getText().length()> 21)
            jTNoInt.setText(jTNoInt.getText().substring(0, 21));
        
    }//GEN-LAST:event_jTNoIntFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de CP*/
    private void jTCPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCPFocusLost
 
        /*Coloca el cursor al principio del control*/
        jTCP.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCP.getText().length()> 6)
            jTCP.setText(jTCP.getText().substring(0, 6));
       
    }//GEN-LAST:event_jTCPFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de teléfono 1*/
    private void jTTel1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel1FocusLost

        /*Coloca el cursor al principio del control*/
        jTTel1.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTel1.getText().length()> 255)
            jTTel1.setText(jTTel1.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTTel1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de teléfono 2*/
    private void jTTel2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTel2FocusLost

        /*Coloca el cursor al principio del control*/
        jTTel2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTel2.getText().length()> 255)
            jTTel2.setText(jTTel2.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTTel2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de celular 1*/
    private void jTCel1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCel1FocusLost

        /*Coloca el cursor al principio del control*/
        jTCel1.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCel1.getText().length()> 255)
            jTCel1.setText(jTCel1.getText().substring(0, 255));
     
    }//GEN-LAST:event_jTCel1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de celular 2*/
    private void jTCel2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCel2FocusLost

        /*Coloca el cursor al principio del control*/
        jTCel2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCel2.getText().length()> 255)
            jTCel2.setText(jTCel2.getText().substring(0, 255));
              
    }//GEN-LAST:event_jTCel2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de teléfono de emergencia 1*/
    private void jTTelEm1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelEm1FocusLost

        /*Coloca el cursor al principio del control*/
        jTTelEm1.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTelEm1.getText().length()> 255)
            jTTelEm1.setText(jTTelEm1.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTTelEm1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de teléfono de emergencia 2*/
    private void jTTelEme2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTelEme2FocusLost

        /*Coloca el cursor al principio del control*/
        jTTelEme2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTTelEme2.getText().length()> 255)
            jTTelEme2.setText(jTTelEme2.getText().substring(0, 255));
              
    }//GEN-LAST:event_jTTelEme2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de contactar 1*/
    private void jTCont1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCont1FocusLost

        /*Coloca el cursor al principio del control*/
        jTCont1.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCont1.getText().length()> 255)
            jTCont1.setText(jTCont1.getText().substring(0, 255));
        
    }//GEN-LAST:event_jTCont1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de a quién contactar*/
    private void jTCon2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCon2FocusLost

        /*Coloca el cursor al principio del control*/
        jTCon2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCon2.getText().length()> 255)
            jTCon2.setText(jTCon2.getText().substring(0, 255));
               
    }//GEN-LAST:event_jTCon2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de sueldo actual*/
    private void jTSuelActFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSuelActFocusLost

        /*Coloca el cursor al principio del control*/
        jTSuelAct.setCaretPosition(0);
        
        /*Lee el texto introducido por el usuario*/
        String sTex = jTSuelAct.getText();
        
        /*Si tiene el signo de dollar quitaselo*/
        sTex = sTex.replace("$", "");
        
        /*Si tiene coma quitaselo*/
        sTex = sTex.replace(",", "");
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(sTex.length()> 30)
            jTSuelAct.setText(jTSuelAct.getText().substring(0, 30));
        
        /*Si es cadena vacia regresa y no continuar*/
        if(sTex.compareTo("")==0)
            return;
        
        /*Si los caes introducidos no se puede convertir a double colocar cadena vacia y regresar*/
        try  
        {  
            double d = Double.parseDouble(sTex);  
        }  
        catch(NumberFormatException nfe)  
        {              
            jTSuelAct.setText("");
            return;
        }                  
                
        /*Conviertelo a double*/
        double dCant = Double.parseDouble(sTex);
        
        /*Formatealo*/
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTex            = n.format(dCant);
        
        /*Colocalo de nu en el campo de texto*/
        jTSuelAct.setText(sTex);                               
        
    }//GEN-LAST:event_jTSuelActFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de sueldo foraneo*/
    private void jTSuelForFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSuelForFocusLost

        /*Coloca el cursor al principio del control*/
        jTSuelFor.setCaretPosition(0);
        
        /*Lee el texto introducido por el usuario*/
        String sTex = jTSuelFor.getText();
        
        /*Si tiene el signo de dollar quitaselo*/
        sTex = sTex.replace("$", "");
        
        /*Si tiene coma quitaselo*/
        sTex = sTex.replace(",", "");
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTSuelFor.getText().length()> 30)
            jTSuelFor.setText(jTSuelFor.getText().substring(0, 30));
        
        /*Si es cadena vacia regresa y no continuar*/
        if(sTex.compareTo("")==0)
            return;
        
        /*Si los caes introducidos no se puede convertir a double colocar cadena vacia y regresar*/
        try  
        {  
            double d = Double.parseDouble(sTex);  
        }  
        catch(NumberFormatException n)  
        {  
            jTSuelFor.setText("");
            return;
        }                  
                
        /*Conviertelo a double*/
        double dCant = Double.parseDouble(sTex);
        
        /*Formatealo*/
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTex            = n.format(dCant);
        
        /*Colocalo de nu en el campo de texto*/
        jTSuelFor.setText(sTex);                                
      
    }//GEN-LAST:event_jTSuelForFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de tiempo extra*/
    private void jTieExtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTieExtFocusLost

        /*Coloca el cursor al principio del control*/
        jTieExt.setCaretPosition(0);
        
        /*Lee el texto introducido por el usuario*/
        String sTex = jTieExt.getText();
        
        /*Si tiene el signo de dollar quitaselo*/
        sTex = sTex.replace("$", "");
        
        /*Si tiene coma quitaselo*/
        sTex = sTex.replace(",", "");
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTieExt.getText().length()> 30)
            jTieExt.setText(jTieExt.getText().substring(0, 30));
        
        /*Si es cadena vacia regresa y no continuar*/
        if(sTex.compareTo("")==0)
            return;
        
        /*Si los caes introducidos no se puede convertir a double colocar cadena vacia y regresar*/
        try  
        {  
            double d = Double.parseDouble(sTex);  
        }  
        catch(NumberFormatException n)  
        {  
            jTieExt.setText("");
            return;
        }                  
                
        /*Conviertelo a double*/
        double dCant = Double.parseDouble(sTex);
        
        /*Formatealo*/
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTex            = n.format(dCant);
        
        /*Colocalo de nu en el campo de texto*/
        jTieExt.setText(sTex);                   
              
    }//GEN-LAST:event_jTieExtFocusLost

    
    /*Cuando se typea una tecla en el campo de sueldo actual*/
    private void jTSuelActKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSuelActKeyTyped
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTSuelAct.getText().length()> 30)
            jTSuelAct.setText(jTSuelAct.getText().substring(0, 30));
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTSuelActKeyTyped

    
    /*Cuando se presiona una tecla en el campo de callee*/
    private void jTCallKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCallKeyPressed

    
    /*Cuando se presiona una tecla en el campo de colonia*/
    private void jTColKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTColKeyPressed

    
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

    
    /*Cuando se presiona una tecla en el campo de CP*/
    private void jTCPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCPKeyPressed

    
    /*Cuando se presiona una tecla en el campo de teléfono */
    private void jTTel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTel1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de teléfono 2*/
    private void jTTel2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTel2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de celular 1*/
    private void jTCel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCel1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCel1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de celular2*/
    private void jTCel2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCel2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCel2KeyPressed

    
    /*Cuando se presiona una tecla en el jchoooser de ingreso a la empresa*/
    private void jDatIngEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDatIngEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDatIngEmpKeyPressed

    
    /*Cuando se presiona una tecla en el jchooser de salida de la empresa*/
    private void jDatSalEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDatSalEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDatSalEmpKeyPressed

    
    /*Cuando se presiona una tecla en el campo de teléfono de emergencia*/
    private void jTTelEm1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelEm1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelEm1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de a quien contactar 1*/
    private void jTCont1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCont1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCont1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de sueldo actual*/
    private void jTSuelActKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSuelActKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSuelActKeyPressed

    
    /*Cuando se presiona una tecla en el campo de sueldo foraneo*/
    private void jTSuelForKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSuelForKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSuelForKeyPressed

    
    /*Cuando se presiona una tecla en el campo de tiempo extra*/
    private void jTieExtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTieExtKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTieExtKeyPressed

    
    /*Cuando se presiona una tecla en el campo de emergencia2*/
    private void jTTelEme2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelEme2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTelEme2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de contactar 2*/
    private void jTCon2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCon2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCon2KeyPressed
   
    
    /*Cuando se tipea una tecla en el campo de sueldo foraneo*/
    private void jTSuelForKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSuelForKeyTyped
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTSuelFor.getText().length()> 30)
            jTSuelFor.setText(jTSuelFor.getText().substring(0, 30));
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTSuelForKeyTyped

    
    /*Cuando se tipea una tecla en el campo de tiempo extra*/
    private void jTieExtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTieExtKeyTyped
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTieExt.getText().length()> 30)
            jTieExt.setText(jTieExt.getText().substring(0, 30));
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTieExtKeyTyped

    
    /*Cuando se presiona una tecla en la tabla de personas*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se presiona una tecla en el botón de guardar cambios*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se presiona una tecla en el botón de limpiar*/
    private void jBLimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLimKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBLimKeyPressed

    
    /*Cuando se da clic en el botón de limpiar*/
    private void jBLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimActionPerformed
        
        /*Deja todos los controles en su estado original*/
        jTNomb.setText                  ("");
        jTCall.setText                  ("");
        jTCol.setText                   ("");
        jTCP.setText                    ("");
        jTCel1.setText                  ("");
        jTCel2.setText                  ("");
        jTTel1.setText                  ("");
        jTTel2.setText                  ("");
        jTNoExt.setText                 ("");
        jLFCrea.setText                 ("-");
        jLFMod.setText                  ("-");
        jTNoInt.setText                 ("");
        jTTelEm1.setText                ("");
        jTTelEme2.setText               ("");
        jTCont1.setText                 ("");        
        jTCon2.setText                  ("");
        jTSuelAct.setText               ("");
        jTSuelFor.setText               ("");
        jTieExt.setText                 ("");
        
        /*Que el label que muestra la imágen no sea visible*/
        jLImg.setVisible(false);
        
        /*Selecciona el día de hoy en la fec de ingreso de la empresa*/
        Date FIni = new Date();
        jDatIngEmp.setDate(FIni);
        
        /*Selecciona el día de hoy en la fec de salida de la empresa*/
        FIni = new Date();
        jDatSalEmp.setDate(FIni);                
        
        /*Coloca el botón de guardar cambios a deshabilitado yajBGuase pueden guardar cambios para una persona nueva*/
        jBGuar.setEnabled(false);
                
        /*Pon la persona activa*/              
        jCAct.setSelected(true);
        
    }//GEN-LAST:event_jBLimActionPerformed

    
    /*Cuando se presiona el botón de guardar cambios*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        /*Declara variables de la base de datos*/    
        Statement   st;
        ResultSet   rs;
        Connection  con;
        String      sQ           = "";
        
        /*Declara variables locales*/
        String      sNomb;        
        String      sCall;
        String      sCol;
        String      sNoExt;
        String      sNoInt;        
        String      sCP;
        String      sTel1;
        String      sAct;
        String      sTel2;
        String      sCel1;
        String      sCel2;
        String      sFIngEmp;
        String      sFSalEmp;
        String      sTelEme1;
        String      sTelEme2;
        String      sConta1;
        String      sConta2;
        String      sSueldAct;
        String      sSueldFora;
        String      sTExt;
        
        
        
        /*Lee el nombre introducido por el usuario*/
        sNomb     = jTNomb.getText().trim();
                
        /*Si hay cadena vacia en el campo de edición de nombre de persona no puede continuar*/
        if(sNomb.compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTNomb.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de nombre esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTNomb.grabFocus();               
            return;           
        }
              
        /*Si no se a seleccionado una fecha de ingreso a la empresa entonces*/
        if(jDatIngEmp.getDate()==null)
        {
            /*Coloca el borde rojo*/                               
            jDatIngEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Tienes que seleccionar una fec de ingreso a la empresa.", "Seleccionar Fecha", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control y regresa*/
            jDatIngEmp.grabFocus();                        
            return;
        }
        
        /*Si no se a seleccionado una fecha de salida de la empresa entonces*/
        if(jDatSalEmp.getDate()==null)
        {
            /*Coloca el borde rojo*/                               
            jDatSalEmp.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Tienes que seleccionar una fec de salida de la empresa.", "Seleccionar Fecha", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el control y regresa*/
            jDatSalEmp.grabFocus();            
            return;
        }
        
        /*Lee la callee*/
        sCall                   = jTCall.getText();
        
        /*Lee la colonia*/
        sCol                    = jTCol.getText();
        
        /*Lee si esta activo o no la persona*/
        if(jCAct.isSelected())        
            sAct                = "1";        
        else
            sAct                = "0";
        
        /*Lee el número de exterior*/
        sNoExt                  = jTNoExt.getText();
        
        /*Lee el número de interior*/
        sNoInt                  = jTNoInt.getText();
        
        /*Lee el CP*/
        sCP                     = jTCP.getText();
        
        /*Lee el teléfono 1*/
        sTel1                   = jTTel1.getText();
        
        /*Lee el teléfono 2*/
        sTel2                   = jTTel2.getText();
        
        /*Lee el celular 1*/
        sCel1                   = jTCel1.getText();
        
        /*Lee el celular 2*/
        sCel2                   = jTCel2.getText();
        
        /*Lee fec de ingreso en la empresa*/
        Date fec                = jDatIngEmp.getDate();
        SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        sFIngEmp                = sdf.format(fec);                       
        
        /*Lee fec de salida de la empresa*/
        fec                     = jDatSalEmp.getDate();
        sdf                     = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
        sFSalEmp                = sdf.format(fec);               
        
        /*Lee el teléfono de emergencia 1*/
        sTelEme1                = jTTelEm1.getText();
        
        /*Lee el teléfono de emergencia 2*/
        sTelEme2                = jTTelEme2.getText();
        
        /*Lee a quien contactar 1*/
        sConta1                 = jTCont1.getText();
        
        /*Lee a quien contactar 2*/
        sConta2                 = jTCon2.getText();
        
        /*Lee el sueldo actual*/
        sSueldAct               = jTSuelAct.getText();
        
        /*Si tiene el signo de dollar quitarcelo*/
        sSueldAct               = sSueldAct.replace("$", "");
        
        /*Si tiene coma el sueldo actual quitaselo*/
        sSueldAct               = sSueldAct.replace(",", "");
        
        /*Si el sueldo es cadena vacia ponerlo en 0 para que lo reciba la base de datos*/
        if(sSueldAct.compareTo("")==0)
            sSueldAct           = "0";
        
        /*Lee el sueldo foraneo*/
        sSueldFora              = jTSuelFor.getText();
        
        /*Si tiene el signo de dollar quitarcelo*/
        sSueldFora              = sSueldFora.replace("$", "");
        
        /*Si tiene coma el sueldo foraneo quitaselo*/
        sSueldFora              = sSueldFora.replace(",", "");
        
        /*Si el sueldo foraneo es cadena vacia ponerlo en 0 para que lo reciba la base de datos*/
        if(sSueldFora.compareTo("")==0)
            sSueldFora          = "0";
        
        /*Lee el tiempo extra*/
        sTExt                   = jTieExt.getText();
        
        /*Si tiene el signo de dollar quitarcelo*/
        sTExt                   = sTExt.replace("$", "");
        
        /*Si tiene coma el tiempo extra quitaselo*/
        sTExt                   = sTExt.replace(",", "");
        
        /*Si el tiempo extra es cadena vacia ponerlo en 0 para que lo reciba la base de datos*/
        if(sTExt.compareTo("")==0)
            sTExt               = "0";
        
        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Agregar Persona", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        //Abre la base de datos                             
        con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Comprueba si el nombre de la persona ya existe en la base de datos*/        
        try
        {
            sQ = "SELECT nom FROM pers WHERE nom = '" + sNomb + "' AND nom <> '" + sNomOri + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El nombre: " + sNomb + " ya existe.", "Nombre Existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el campo de nombre y regresa*/
                jTNomb.grabFocus();
                return; 
            }
        }
        catch(SQLException | HeadlessException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }            
        
        /*Actualiza los datos en la base de datos*/
        try
        {          
            /*Crea la consulta*/
            sQ = "UPDATE pers SET "+
                     "nom                   = '" + sNomb.replace("'", "''") + "', " +                     
                     "calle                  = '" + sCall.replace("'", "''") + "', " + 
                     "col                   = '" + sCol.replace("'", "''") + "', " +
                     "act                   = " + sAct + ", " +
                     "cp                    = '" + sCP.replace("'", "''") + "', " +
                     "noext                 = '" + sNoExt.replace("'", "''") + "', " +                    
                     "noint                 = '" + sNoInt.replace("'", "''") + "', " +
                     "tel1                  = '" + sTel1.replace("'", "''") + "', " +
                     "tel2                  = '" + sTel2.replace("'", "''") + "', " +
                     "cel1                  = '" + sCel1.replace("'", "''") + "', " +
                     "cel2                  = '" + sCel2.replace("'", "''") + "', " +
                     "fingemp               = '" + sFIngEmp.replace("'", "''") + "', " +
                     "fsalemp               = '" + sFSalEmp.replace("'", "''") + "', " +
                     "teleme1               = '" + sTelEme1.replace("'", "''") + "', " +
                    "teleme2              = '" + sTelEme2.replace("'", "''") + "', " +
                    "contac1              = '" + sConta1.replace("'", "''") + "', " +
                    "contac2              = '" + sConta2.replace("'", "''") + "', " +
                     "sueldactua            = " + sSueldAct.replace("'", "''") + ", " +                     
                     "sueldfora             = " + sSueldFora.replace("'", "''") + ", " +
                     "textra                = " + sTExt.replace("'", "''") + ", " +                    
                     "sucu                  = '" + Star.sSucu.replace("'", "''") + "', " +
                     "nocaj                 = '" + Star.sNoCaj.replace("'", "''") + "', " + 
                     "fmod                  = now(), " +
                     "estac                 = '" + Login.sUsrG.replace("'", "''") + "' WHERE nom = '" + sNomOri.replace("'", "''") + "'";
            
            /*Ejecuta la consulta*/
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException ex)
         {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Agrega en el log*/
            Login.vLog(ex.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));
            return;
        }        
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Obtén la fila seleccionada*/
        int row                 = jTab.getSelectedRow();  
        
        /*Si no hay selección en la tabla entonces*/
        if(row == -1)
        {
            /*Recorre toda la tabla en busca de la persona*/
            for(int x = 0; x < jTab.getRowCount(); x++)
            {
                /*Si el nombre es el de la fila entonces*/ 
                if(sNomb.compareTo(jTab.getValueAt(x, 1).toString())==0)
                {
                    /*Sal del bucle ahora que ya tenemos el número de la fila en la que esta el nombre de la persona*/
                    row = x;
                    break;
                }               
            }            
            
        }/*Fin de if(row == -1)*/
        
        /*Agrega el nombre de la persona en la tabla*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFil - 1, sNomb};
        te.addRow(nu);
        
        /*Borra el anterior de la tabla*/
        te.removeRow(row);
        
        /*Seleccionalo en la tabla*/
        jTab.setRowSelectionInterval(row, row);
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Persona: " + sNomb + " modificada con éxito.", "Exito al modificar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    
    /*Cuando se levanta una tecla en el campo de colonia*/
    private void jTColKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyReleased
        
        
    }//GEN-LAST:event_jTColKeyReleased

    
    /*Cuando se levanta una tecla en el campo de número de exterior*/
    private void jTNoExtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoExtKeyReleased
        
      
        
    }//GEN-LAST:event_jTNoExtKeyReleased

    
    /*Cuando se levanta una tecla en el campo de número de interior*/
    private void jTNoIntKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyReleased
        
        
    }//GEN-LAST:event_jTNoIntKeyReleased

    
    /*Cuando se levanta una tecla en el campo de CP*/
    private void jTCPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyReleased
        
        
        
    }//GEN-LAST:event_jTCPKeyReleased

    
    /*Cuando se levanta una tecla en el campo de teléfono 1*/
    private void jTTel1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel1KeyReleased
        
        
    }//GEN-LAST:event_jTTel1KeyReleased

    
    /*Cuando se levanta una tecla en el campo de teléfono 2*/
    private void jTTel2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel2KeyReleased
        
        
    }//GEN-LAST:event_jTTel2KeyReleased

    
    /*Cuando se levanta una tecla en el campo de celular 1*/
    private void jTCel1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCel1KeyReleased
        
        
       
    }//GEN-LAST:event_jTCel1KeyReleased

    
    /*Cuando se gana el foco del teclado en el campo de celular 2*/
    private void jTCel2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCel2KeyReleased
        
        
    }//GEN-LAST:event_jTCel2KeyReleased

    
    /*Cuando se levanta una tecla en el campo de teléfono de emergencia 1*/
    private void jTTelEm1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelEm1KeyReleased
        
       
        
    }//GEN-LAST:event_jTTelEm1KeyReleased

    
    /*Cuando se gana el foco del teclado en el campo de Contacto 1*/
    private void jTCont1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCont1KeyReleased
        
        
    }//GEN-LAST:event_jTCont1KeyReleased

    
    /*Cuando se levanta una tecla en el campo de teléfono de emergencia 2*/
    private void jTTelEme2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelEme2KeyReleased
        
        
        
    }//GEN-LAST:event_jTTelEme2KeyReleased

    
    /*Cuando se levanta una tecla en el campo de contactar 2*/
    private void jTCon2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCon2KeyReleased
        
       
        
    }//GEN-LAST:event_jTCon2KeyReleased

    
    /*Cuando se tipea una tecla en el campo de callee*/
    private void jTCallKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCallKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCallKeyTyped

    
    /*Cuando se tipea una tecla en el campo de colonia*/
    private void jTColKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTColKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTColKeyTyped

    
    /*Cuando se tipea una tecla en el campo de número de exterior*/
    private void jTNoExtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoExtKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTNoExtKeyTyped

    
    /*Cuando se tipea una tecla en el campo de número de interior*/
    private void jTNoIntKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNoIntKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTNoIntKeyTyped

    
    /*Cuando se tipea una tecla en el campo de CP*/
    private void jTCPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCPKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCPKeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono 1*/
    private void jTTel1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel1KeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTTel1KeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono 2*/
    private void jTTel2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTel2KeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTTel2KeyTyped

    
    /*Cuando se tipea una tecla en el control de celular 1*/
    private void jTCel1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCel1KeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCel1KeyTyped

    
    /*Cuando se tipea una tecla en el campo de celular 2*/
    private void jTCel2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCel2KeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCel2KeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono de emergencia*/
    private void jTTelEm1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelEm1KeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTTelEm1KeyTyped

    
    /*Cuando se tipea una tecla en el campo de a quién contactar 1*/
    private void jTCont1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCont1KeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCont1KeyTyped

    
    /*Cuando se tipea una tecla en el campo de teléfono de emergencia 2*/
    private void jTTelEme2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTelEme2KeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTTelEme2KeyTyped

    
    /*Cuando se tipea una tecla en el campo de contactar 2*/
    private void jTCon2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCon2KeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCon2KeyTyped

    
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

    
    /*Cuando se presiona una tecla en el camopo de buscar*/
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

    
    /*Cuando se presiona el botón de buscar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed
        
        /*Borra todos los item en la tabla de personas*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFil = 1;
        
        /*Carga los nombres de las personas de la base de datos en la tabla*/
        vCargrPers();
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBMosTActionPerformed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed
        
        /*Si el campo de buscar esta vacio no puede seguir*/
        if(jTBusc.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de búsqueda esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de búsqueda y regresa*/
            jTBusc.grabFocus();          
            return;
        }                      
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Borra todos los item en la tabla de personas*/
        DefaultTableModel dm    = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
         /*Resetea el contador de filas*/
        iContFil                = 1;

        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ          = ""; 
        
        /*Obtiene de la base de datos todos los datos de la persona*/        
        try
        {                  
            sQ = "SELECT * FROM pers WHERE nom LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR falt LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR fmod LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR calle LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR col LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR noint LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR noext LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR cp LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR tel1 LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR tel2 LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR cel1 LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR cel2 LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR  fingemp LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR fsalemp LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR teleme1 LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR teleme2 LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR contac1 LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR contac2 LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR sueldactua LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR textra LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR sueldfora LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {                                
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFil, rs.getString("nom")};
                te.addRow(nu);

                /*Aumenta en uno el contador de filas*/
                ++iContFil;                
                
            }/*Fin de while (rs.next())*/
                        
        }/*Fin de try*/
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de buscar*/
    private void jTBuscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusGained

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBBusc);
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTBusc.setSelectionStart(0);        
        jTBusc.setSelectionEnd(jTBusc.getText().length());        
        
    }//GEN-LAST:event_jTBuscFocusGained

      
    /*Cuando se presiona el botón de guardar*/
    private void jBCargImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargImgActionPerformed
                
        /*Si no a seleccionado una persona de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una persona.", "Personas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla de personas y regresa*/
            jTab.grabFocus();                        
            return;            
        }
        
        /*Obtiene la fila seleccionada*/
        int row                         = jTab.getSelectedRow();        

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
        
        /*Forma bien la ruta a la carpeta de la aplicación compartida en el servidor*/
        String sRutAp   = "";
        sCarp                    = sRutAp + "\\" + sCarp;
        
        /*Si la carpeta de las imágenes no existe entonces crea la ruta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de las personas no existe entonces crea la ruta*/
        sCarp                    += "\\Personas";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la ruta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la persona en específico no existe entonces crea la ruta*/
        sCarp                    += "\\" + jTab.getValueAt(row, 1).toString();
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si hay archvios entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Ya existe una imágen para la persona: " + jTab.getValueAt(row, 1).toString() + " en \"" + sCarp + "\".", "Personas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
                return;
            }
        }    
        
        /*Configura el file chooser para escoger la ruta del directorio donde esta la imágen*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar Imágen");
        fc.setAcceptAllFileFilterUsed               (false);

        /*Si el usuario presiono aceptar entonces*/
        String sRut;
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            sRut                = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena la carpeta final seleccionada*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Completa la ruta final con el nombre del archivo a donde se copiara*/
            sCarp               +=  "\\" + fc.getSelectedFile().getName();  

            /*Si el nombre del archivo no termina con una extensión de imágen entonces*/
            if(!fc.getSelectedFile().getName().endsWith(".jpg") && !fc.getSelectedFile().getName().endsWith(".jpeg") && !fc.getSelectedFile().getName().endsWith(".bmp") && !fc.getSelectedFile().getName().endsWith(".gif") && !fc.getSelectedFile().getName().endsWith(".png"))
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El formato de la imágen debe ser un formato de imágen conocido.", "Imágen", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                return;
            }
            
            /*Si el archivo de origén no existe entonces*/
            if( !new File(sRut).exists())
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El archivo de origén \"" + sRut + "\" no existe.", "Archivo no existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));              
                return;
            }

            /*Copia el archivo orgien al destino*/
            try
            {
                org.apache.commons.io.FileUtils.copyFile(new File(sRut), new File(sCarp));
            }
            catch(IOException ex)
            {
                /*Agrega en el log*/
                Login.vLog(ex.getMessage());

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en FileUtils.copyFile() por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));
                return;
            }

            /*Carga la imágen en el panel*/
            jLImg.setIcon(new ImageIcon(sRut));

            /*Que el label sea visible*/
            jLImg.setVisible(true);

        }/*Fin de if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)*/

    }//GEN-LAST:event_jBCargImgActionPerformed

    
    /*Cuando se presiona una tecla en el botón de cargar imágen*/
    private void jBCargImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargImgKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCargImgKeyPressed

    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelImgActionPerformed
                
        /*Si no a seleccionado una persona de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una persona.", "Personas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                        
            return;            
        }

        /*Preguntar al usuario si esta seguro de que quiere borrar la imágen*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la imágen?", "Borrar Imágen", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        /*Obtiene el nombre de la persona*/
        int row         = jTab.getSelectedRow();        
        
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
        
        /*Completa la ruta a la carpeta compartida de la aplicación en el servidor*/
        String sRutAp   = "";   
        sCarp            = sRutAp + "\\" + sCarp;
        
        /*Si la carpeta de imágenes no existe entonces crea la ruta*/
        sCarp            += "\\Imagenes"; 
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de personas no existe entonces crea la ruta*/
        sCarp            += "\\Personas";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la ruta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la persona en específico no existe entonces crea la ruta*/
        sCarp             += "\\" + jTab.getValueAt(row, 1).toString();
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si hay archivod entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "La persona: " + jTab.getValueAt(row, 1).toString() + " no contiene imágen para borrar en \"" + sCarp + "\".", "Personas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
                return;
            }

        }                    
        /*Borra todos los archivos de la carpeta*/
        try
        {
            org.apache.commons.io.FileUtils.cleanDirectory(new File(sCarp)); 
        }
        catch(IOException e)
        {
            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en FileUtils() por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));
            return;
        }                       
        
        /*Que no sea visible el label del logotipo*/
        jLImg.setVisible(false);

        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Imágen borrada con éxito para: " + jTab.getValueAt(row, 1).toString() + " desde \"" + sCarp + "\".", "Personas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBDelImgActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar persona*/
    private void jBDelImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelImgKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBDelImgKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de activo*/
    private void jCActKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCActKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCActKeyPressed

    
    /*Cuando sucede una acción el el checkbox de activo*/
    private void jCActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCActActionPerformed
        
        /*Si el checkbox de activo esta marcado entonces*/
        if(jCAct.isSelected())
        {
            /*Deshabilita el control de fec de salida*/
            jDatSalEmp.setEnabled(false);
        }
        else
        {
            /*Habilita el control de fec de salida*/
            jDatSalEmp.setEnabled(true);
            
            /*Que no sea editable mas que con el botón*/
            jDatSalEmp.getDateEditor().setEnabled(false);
        }
            
        
    }//GEN-LAST:event_jCActActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver en grande la imágen*/
    private void jBVeGranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVeGranKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVeGranKeyPressed

    
    /*Cuando se presiona el botón de ver en grande*/
    private void jBVeGranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVeGranActionPerformed

        /*Declara variables locales*/       
        String      sCarp;            
        String      sRutAp             = "";   
        String      sNomb;
        
        
        
        
        /*Si no a seleccionado una persona de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero una persona.", "Personas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                        
            return;            
        }
        
        /*Obtiene el nombre de la personas seleccionada*/
        int row                         = jTab.getSelectedRow();
        sNomb                           = jTab.getValueAt(row, 1).toString();
        
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
        
        /*Forma bien la ruta a la carpeta de la aplicación compartida en el servidor*/
        sCarp                    = sRutAp + "\\" + sCarp;
        
        /*Si la carpeta de las imágenes no existe entonces crea el directorio*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de las personas no existe entonces crea el directorio*/
        sCarp                    += "\\Personas";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existe entonces crea el directorio*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la persona en específico no existe entonces crea la carpeta*/
        sCarp                    += "\\" + sNomb;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
                
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si el contador e ficheros es igual a 0 entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe una imágen para la persona \"" + sNomb + "\" en \"" + sCarp + "\".", "Personas", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
                return;
            }
        }                    
                
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si contiene ficheros*/
            if( new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista del archivo*/
                String sA [] = new File(sCarp).list();

                /*Abre el archivo*/
                try 
                {
                    Desktop.getDesktop().open(new File(sCarp + "\\" + sA[0]));
                } 
                catch(IOException e) 
                {
                    /*Agrega en el log y mensajea*/
                    Login.vLog(e.getMessage());            
                    JOptionPane.showMessageDialog(null, "No se puede abrir archivo de persona por " + e.getMessage(), "Abrir", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                }            
            }
        }                    
        
    }//GEN-LAST:event_jBVeGranActionPerformed

    
    /*Cuando se presiona una tecla en el panel de la imágen*/
    private void jLImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLImgKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jLImgKeyPressed

    
    /*Cuando se presiona el botón de visor*/
    private void jBVisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVisActionPerformed

        /*Si no hay datos en la tabla entonces*/
        if(jTab.getRowCount()==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No existen personas.", "Reporte", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }
        
        /*Crea el th para cargar el reporte en un hilo aparte*/
        Thread th = new Thread()
        {
            @Override
            public void run()
            {
                //Abre la base de datos                             
                Connection  con2 = Star.conAbrBas(true, true);

                //Si hubo error entonces regresa
                if(con2==null)
                    return;

                /*Si el checkbox de mostrar todo esta activado entonces*/
                if(jCMosT.isSelected())
                {
                    /*Muestra el formulario para todas las personas*/
                    try
                    {
                        /*Crea los parámetros que se pasarán*/
                        Map <String,String> pa = new HashMap<>();             
                        pa.clear();
                        pa.put("NOM",       "");      
                        pa.put("LOG",       getClass().getResource(Star.sIconDef).toString());

                        /*Compila el reporte y muestralo maximizado*/
                        JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptPerson.jrxml"));
                        JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con2);
                        JasperViewer v      = new JasperViewer(pr, false);
                        v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);                    
                        v.setVisible(true);                        

                        /*Esconde el loading*/
                        Star.lCargGral.setVisible(false);                        
                    }
                    catch(JRException e)
                    {
                        //Cierra la base de datos
                        if(Star.iCierrBas(con2)==-1)
                            return;

                        /*Esconde el loading*/
                        Star.lCargGral.setVisible(false);
                        
                        /*Agrega en el log*/
                        Login.vLog(e.getMessage());
            
                        /*Mensajea y regresa*/
                        JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por :" + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        return;
                    }
                    
                }/*Fin de if(jCheckBoxMostrarT.isSelected())*/
                /*Else, muestra el formulario para una persona en específico*/
                else
                {
                    /*Declara variables*/
                    String sNomb;
                    
                    /*Si no a seleccionado una persona de la lista entonces*/
                    if(jTab.getSelectedRow()==-1)
                    {
                        //Cierra la base de datos
                        if(Star.iCierrBas(con2)==-1)
                            return;
                        
                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "Selecciona primero una persona.", "Persona", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        
                        /*Pon el foco del teclado en la tabla y regresa*/
                        jTab.grabFocus();                                                
                        return;
                    }
                    
                    /*Obtiene el nombre de la persona seleccionada*/
                    int row = jTab.getSelectedRow();
                    sNomb   = jTab.getValueAt(row, 1).toString();
                    
                    /*Crea los parámetros que se pasarán*/
                    Map <String,String> pa = new HashMap<>();             
                    pa.clear();
                    pa.put("NOMBRE",    sNomb);                        
                    pa.put("LOG",       getClass().getResource(Star.sIconDef).toString());
                    
                    /*Muestra el formulario para una persona en específico*/
                    try
                    {
                        /*Compila el reporte y muestralo maximizado*/
                        JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptPerson.jrxml"));
                        JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con2);
                        JasperViewer v      = new JasperViewer(pr, false);
                        v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);                    
                        v.setVisible(true);
                        
                        /*Esconde el loading*/
                        Star.lCargGral.setVisible(false);

                    }
                    catch(JRException e)
                    {
                        //Cierra la base de datos
                        if(Star.iCierrBas(con2)==-1)
                            return;

                        /*Esconde el loading*/
                        Star.lCargGral.setVisible(false);
                        
                        /*Agrega en el log*/
                        Login.vLog(e.getMessage());
            
                        /*Mensajea y regresa*/
                        JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por :" + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        return;
                    }
                    
                }/*Fin de elese*/
                    
                //Cierra la base de datos
                Star.iCierrBas(con2);
                
            }/*Fin de public void run()*/
        };

        /*Muestra la forma para simular que esta cargando el reporte*/
        Star.lCargGral = new LoadinGral("Cargando reporte de personas...");
        Star.lCargGral.setVisible(true);

        /*Comienza el hilo*/
        th.start();                        
        
    }//GEN-LAST:event_jBVisActionPerformed

    
    /*Cuando se presiona una tecla en el botón de visor*/
    private void jBVisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVisKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBVisKeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de mostrar todo*/
    private void jCMosTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCMosTKeyPressed
        
         //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCMosTKeyPressed

    
    /*Cuando la rueda del ratón es movido en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando el mouse es arrastrado en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando el mouse es movido en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se presiona el botón de mostrar en grande la tabla*/
    private void jBTabGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTabGActionPerformed

        /*Instancia la forma para ver las empresas en grande*/
        VisAltPers e = new VisAltPers();

        /*Recorre la tabla*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Agrega todo el registro a la otra tabla de empresas*/
            DefaultTableModel te    = (DefaultTableModel)e.jTab.getModel();
            Object nu[]             = {jTab.getValueAt(x, 0), jTab.getValueAt(x, 1)};
            te.addRow(nu);
        }

        /*Muestra la forma*/
        e.setVisible(true);

    }//GEN-LAST:event_jBTabGActionPerformed

    
    /*Cuando se presiona una tecla en el botón de mostrar la tabla en grande*/
    private void jBTabGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTabGKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTabGKeyPressed

    
    /*Cuando el mouse sale del botón de búscar*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited
        
        /*Coloca el borde que tenía*/
        jBBusc.setBorder(bBordOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
    /*Cuando el mouse entrae en el botón de búscar*/
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

    
    /*Cuando el mouse entra en el campo de la imágen*/
    private void jLImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLImgMouseEntered
        
        /*Obtiene la fila seleccionada*/
        int row = jTab.getSelectedRow();
        if(row==-1)
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

        /*Si la carpeta de las personas no existe entonces creala*/
        sCarp                    += "\\Personas";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la aplicación no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de El usuario en específico no existe entonces creala*/
        sCarp                    += "\\" + jTab.getValueAt(row, 1).toString();
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
        
    }//GEN-LAST:event_jLImgMouseEntered

    
    /*Cuando el mouse sale del conrol de la imágen*/
    private void jLImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLImgMouseExited
        
        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
    }//GEN-LAST:event_jLImgMouseExited

    
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
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBLimMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBLim.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBLimMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMosTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMosT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMosTMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCargImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargImgMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCargImg.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCargImgMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelImgMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelImgMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVeGranMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGranMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVeGran.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVeGranMouseEntered


    /*Cuando el mouse entra en el botón específico*/
    private void jBVisMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVisMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVis.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVisMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMosTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMosT.setBackground(colOri);
        
    }//GEN-LAST:event_jBMosTMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBLimMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBLim.setBackground(colOri);
        
    }//GEN-LAST:event_jBLimMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCargImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargImgMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCargImg.setBackground(colOri);
        
    }//GEN-LAST:event_jBCargImgMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelImgMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelImgMouseExited

    
    /*Cuando el mouse sale del botón específico*/    
    private void jBVeGranMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGranMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVeGran.setBackground(colOri);
        
    }//GEN-LAST:event_jBVeGranMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVisMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVisMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVis.setBackground(colOri);
        
    }//GEN-LAST:event_jBVisMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
        /*Coloca el cursor al principio del control*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));

    }//GEN-LAST:event_jTBuscFocusLost

   
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape entonces presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
            jBTod.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        /*Si se presiona F3 y presiona el botón de agregar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 y presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona F6 y presiona el botón de limpiar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F6)
            jBLim.doClick();
        /*Si se presiona F7 y presiona el botón de visor*/
        else if(evt.getKeyCode() == KeyEvent.VK_F7)
            jBVis.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBCargImg;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBDelImg;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBLim;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTabG;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBVeGran;
    private javax.swing.JButton jBVis;
    private javax.swing.JCheckBox jCAct;
    private javax.swing.JCheckBox jCMosT;
    private com.toedter.calendar.JDateChooser jDatIngEmp;
    private com.toedter.calendar.JDateChooser jDatSalEmp;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLFCrea;
    private javax.swing.JLabel jLFMod;
    private javax.swing.JLabel jLImg;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTCP;
    private javax.swing.JTextField jTCall;
    private javax.swing.JTextField jTCel1;
    private javax.swing.JTextField jTCel2;
    private javax.swing.JTextField jTCol;
    private javax.swing.JTextField jTCon2;
    private javax.swing.JTextField jTCont1;
    private javax.swing.JTextField jTNoExt;
    private javax.swing.JTextField jTNoInt;
    private javax.swing.JTextField jTNomb;
    private javax.swing.JTextField jTSuelAct;
    private javax.swing.JTextField jTSuelFor;
    private javax.swing.JTextField jTTel1;
    private javax.swing.JTextField jTTel2;
    private javax.swing.JTextField jTTelEm1;
    private javax.swing.JTextField jTTelEme2;
    private javax.swing.JTable jTab;
    private javax.swing.JTextField jTieExt;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
