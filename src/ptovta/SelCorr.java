//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para controlar de que corr se va a reenviar la factura*/
public class SelCorr extends javax.swing.JFrame
{
    /*Contiene el color original del botón*/
    private java.awt.Color              colOri;
    
    /*Declara variables de instancia*/    
    private final String                sCodEmp;
    private final String                sFol;
    private final String                sRutPDF;
    private final String                sRutXML;
    private final String                sNombPDF;
    private final String                sNombXML;
    private final String                sCodCot;

    /*Bandera para saber si se tiene que cerrar la forma o no*/
    private boolean                     bCie;
    
    /*Contador para modificar tabla*/
    private int                         iContCellEd;
    
    /*Declara variables originales*/
    private String                      sCorrOri;
    
    /*Variable para saber que se debe mandar*/
    private final int                    iTip;
    
        
    
    /*Constructor sin argumentos*/
    public SelCorr(JFrame jFram, String sRutPD, String sRutXM, String sCodEm, String sFo, String sNombPD, String sNombXM, int iTi, String sNomE) 
    {           
        /*Inicaliza los componentes gráficos*/
        initComponents();

        //Que la forma sea modal
        this.setAlwaysOnTop(true);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBEnvi);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Inicialmente no tiene que cerrar la forma*/
        bCie    = false;
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el tamaño de las columnas de la tabla de registros*/        
        jTab.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(80);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(500);
        
        /*Recibe los parámetros*/
        sRutPDF     = sRutPD;
        sRutXML     = sRutXM;
        sNombPDF    = sNombPD;
        sNombXML    = sNombXM;        
        sCodEmp     = sCodEm;
        sFol        = sFo;
        sCodCot     = sFo;
        iTip        = iTi;
        
        /*Si es por factura entonces coloca el texto en el label*/
        if(iTip==1 || iTip==2)
            jLDat.setText("Folio: " + sFol + " cliente: " + sCodEmp + " nombre: " + sNomE);
        /*Else if es por cotización entonces colcoa el texto en el label*/
        else if(iTip==2)
            jLDat.setText("Cotización: " + sFol + " cliente: " + sCodEmp + " nombre: " + sNomE);
        /*Else no es por factura entonces el label de datos no será visible*/
        else
            jLDat.setVisible(false);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Selecciona correo, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
                
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);

        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para cuando se cambia de selección en la tabla*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces regresa*/                
                if(jTab.getSelectedRow()==-1)
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
                        sCorrOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sCorrOri,       jTab.getSelectedRow(), 1);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Cargar todos los correos electrónicos para el usuario en la tabla*/
        vCargReg();                        
        
        /*Cargar en los tres campos los tres correos del cliente en base a su código*/
        vCargCorr();                        
        
    }/*Fin de public Buscador()*/

    
    /*Cargar en los tres campos los tres correos de la empresa en base a su código*/
    private void vCargCorr()
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Trae los correos de la empresa*/
        try
        {
            sQ = "SELECT co1, co2, co3 FROM emps WHERE CONCAT_WS('', ser, codemp ) = '" + sCodEmp + "'";                               
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos en los controles*/
            if(rs.next())
            {                
                jTCo1.setText(rs.getString("co1"));
                jTCo2.setText(rs.getString("co2"));
                jTCo3.setText(rs.getString("co3"));
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
        
    }/*Fin de private void vCargCorr()*/
        
        
    /*Cargar todos los correos electrónicos para El usuario en la tabla*/
    private void vCargReg()
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);
        
        //Si hubo error entonces regresa
        if(con==null)
            return;
                
        /*Inicia el contador de filas en 1 inicialmente*/
        int iContFi      = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Trae todos los correos dados de alta para esta usuario*/
        try
        {
            sQ = "SELECT id_id, usr FROM corrselec WHERE estac = '" + Login.sUsrG + "'";                   
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Agregalo a la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("id_id"), rs.getString("usr")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas de las compras*/
                ++iContFi;                                                
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
        
    }/*Fin de private void vCargReg(String sBusca, int iEn)*/
        
            
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jP1 = new javax.swing.JPanel();
        jCCo1 = new javax.swing.JCheckBox();
        jTCo1 = new javax.swing.JTextField();
        jTCo2 = new javax.swing.JTextField();
        jCCo2 = new javax.swing.JCheckBox();
        jTCo3 = new javax.swing.JTextField();
        jCCo3 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBSal = new javax.swing.JButton();
        jBEnvi = new javax.swing.JButton();
        jCGua = new javax.swing.JCheckBox();
        jLDat = new javax.swing.JLabel();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();

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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jP1.setBackground(new java.awt.Color(255, 255, 255));
        jP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jP1KeyPressed(evt);
            }
        });
        jP1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCCo1.setBackground(new java.awt.Color(255, 255, 255));
        jCCo1.setSelected(true);
        jCCo1.setNextFocusableComponent(jTCo2);
        jCCo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo1KeyPressed(evt);
            }
        });
        jP1.add(jCCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, -1, -1));

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
        jP1.add(jTCo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 210, 20));

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
        jP1.add(jTCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 210, 20));

        jCCo2.setBackground(new java.awt.Color(255, 255, 255));
        jCCo2.setNextFocusableComponent(jTCo3);
        jCCo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo2KeyPressed(evt);
            }
        });
        jP1.add(jCCo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, -1, -1));

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
        jP1.add(jTCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 210, 20));

        jCCo3.setBackground(new java.awt.Color(255, 255, 255));
        jCCo3.setNextFocusableComponent(jCGua);
        jCCo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCCo3KeyPressed(evt);
            }
        });
        jP1.add(jCCo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, -1, -1));

        jLabel1.setText("Correo 3:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 80, -1));

        jLabel2.setText("*Correo 1:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 80, -1));

        jLabel3.setText("Correo 2:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 80, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "ID", "Correo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jTCo1);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabMouseClicked(evt);
            }
        });
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 0, -1, 110));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jTab);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, 100, 30));

        jBEnvi.setBackground(new java.awt.Color(255, 255, 255));
        jBEnvi.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBEnvi.setForeground(new java.awt.Color(0, 102, 0));
        jBEnvi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/corrs.png"))); // NOI18N
        jBEnvi.setText("ENTER");
        jBEnvi.setToolTipText("Enviar");
        jBEnvi.setNextFocusableComponent(jBSal);
        jBEnvi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBEnviMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBEnviMouseExited(evt);
            }
        });
        jBEnvi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEnviActionPerformed(evt);
            }
        });
        jBEnvi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBEnviKeyPressed(evt);
            }
        });
        jP1.add(jBEnvi, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 100, 30));

        jCGua.setBackground(new java.awt.Color(255, 255, 255));
        jCGua.setText("Guardar correos F2");
        jCGua.setNextFocusableComponent(jBEnvi);
        jCGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCGuaActionPerformed(evt);
            }
        });
        jCGua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCGuaKeyPressed(evt);
            }
        });
        jP1.add(jCGua, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 150, -1));

        jLDat.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLDat.setText("Datos de documento:");
        jP1.add(jLDat, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 530, -1));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 190, 130, 20));

        getContentPane().add(jP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 210));

        pack();
    }// </editor-fold>//GEN-END:initComponents

        
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed
   
    
    /*Cuando se presiona una tecla en el panel*/
    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPanel1KeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Sal del formulario*/
        this.dispose();        
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una  tecla en la tabla de registros*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed
       
    
    /*Cuando se presiona una tecla en el botón de enviar*/
    private void jBEnviKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBEnviKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBEnviKeyPressed

    
    /*Método para mandar todo el proceso de las facturas*/
    private void vMandFac()
    {        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado un correo del que se reenviara.", "Reenviar Factura", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();            
            return;
        }

        //Declara variables locales
        final String      sID;                                        
        
        /*Si ninguno de los checkbox esta seleccionado entonces*/
        if(!jCCo1.isSelected() && !jCCo2.isSelected() && !jCCo3.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado un correo a quién reenviar.", "Correo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el primer control de los correos y regresa*/
            jTCo1.grabFocus();                        
            return;
        }

        /*Preguntar al usr si esta seguro de querer reenviar la factura*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Reenviar", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       

        /*Si el checkbox de guadar correos de la empresa esta seleccionado entonces*/
        if(jCGua.isSelected())
        {
            //Abre la base de datos
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Declara variables de la base de datos
            Statement   st;                        
            String      sQ; 

            /*Actualiza en la base de datos los correos*/
            try
            {                           
                sQ = "UPDATE emps SET "
                        + "co1          = '" + jTCo1.getText().replace("'", "''").trim() + "', "
                        + "co1          = '" + jTCo2.getText().replace("'", "''").trim() + "', "
                        + "co3          = '" + jTCo3.getText().replace("'", "''").trim() + "' "
                        + "WHERE CONCAT_WS('', ser,  codemp) = '" + sCodEmp.replace("'", "''") + "'";                                                
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

        /*Obtén el id del correo seleccionado*/        
        sID             = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                  

        //Muestra el loading
        Star.vMostLoading("");
        
        /*Manda el PDF a la empresa en un thread*/        
        (new Thread()
        {
            @Override
            public void run()
            {                                
                /*Lee los correos que inserto el usuario*/
                String sCo1            = jTCo1.getText();
                String sCo2            = jTCo2.getText();
                String sCo3            = jTCo3.getText();

                /*Determina a que correos se les va a mandar*/
                if(!jCCo1.isSelected())
                    sCo1        = null;
                if(!jCCo2.isSelected())
                    sCo2        = null;
                if(!jCCo3.isSelected())
                    sCo3        = null;
                
                //Abre la base de datos
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;                

                //Declara variables locales
                String      sServSMTPSal        = "";
                String      sSMTPPort           = "";
                String      sUsr                = "";
                String      sContra             = "";
                String      sActSSL             = "";
                String      sAsunFac            = "";
                String      sCuerFac            = "";

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ;               
                
                /*Trae todos los datos del corr electrónico de la base de datos en base a su id*/                
                try
                {
                    sQ = "SELECT srvsmtpsal, portsmtp, actslenlog, usr, pass, asunfac, cuerpfac FROM corrselec WHERE id_id = " + sID;                   
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    if(rs.next())
                    {
                        /*Obtiene todos los datos de la consulta*/
                        sServSMTPSal            = rs.getString("srvsmtpsal");
                        sSMTPPort               = rs.getString("portsmtp");
                        sUsr                    = rs.getString("usr");
                        sContra                 = rs.getString("pass");
                        sAsunFac                = rs.getString("asunfac");
                        sCuerFac                = rs.getString("cuerpfac");

                        /*Si activar ssl login esta activado entonces guarda true*/
                        if(rs.getString("actslenlog").compareTo("1")==0)
                            sActSSL = "true";
                        else
                            sActSSL = "false";

                        /*Desencripta la contraseña*/
                        sContra                = Star.sDecryp(sContra);                        
                    }
                }
                catch(SQLException expnSQL)
                {                    
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;                                                                                                       
                }

                /*Crea el usr y la contraseña como final para que el th valide si son válidos o no*/
                final String sUser          = sUsr;                

                /*Si el primer correo no es null entonces*/
                if(sCo1!=null)
                {
                    /*Manda un corr*/
                    try
                    {
                        Properties props = System.getProperties();
                        props.setProperty("mail.smtp.host", sServSMTPSal);
                        props.put("mail.smtp.starttls.enable", sActSSL);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.debug", "true");
                        props.put("mail.smtp.port", sSMTPPort);
                        props.put("mail.store.protocol", "pop3");
                        props.put("mail.transport.protocol", "smtp");
                        final String username = sUsr;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });

                        MimeMessage  msj = new MimeMessage(session);
                        msj.setFrom(new InternetAddress(sUser));
                        msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sCo1));
                        msj.setSubject(sAsunFac + "\"" + sFol + "\"");
                        String msg             = sCuerFac;
                        msj.setContent(msg, "text/html; charset=utf-8");

                        /*Genera el adjunto*/
                        BodyPart msjbod = new MimeBodyPart();
                        msjbod.setText(sCuerFac);
                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(msjbod);

                        /*Adjunta el PDF*/
                        msjbod = new MimeBodyPart();                                               
                        DataSource src1 = new FileDataSource(sRutPDF);                        
                        msjbod.setDataHandler(new DataHandler(src1));
                        msjbod.setFileName(sNombPDF);
                        multipart.addBodyPart(msjbod);                                                

                        /*Adjunta el XML*/
                        msjbod = new MimeBodyPart();                                               
                        DataSource src2 = new FileDataSource(sRutXML);                        
                        msjbod.setDataHandler(new DataHandler(src2));                                                                        
                        msjbod.setFileName(sNombXML);
                        multipart.addBodyPart(msjbod);                        

                        /*Relaciona todo el contenido del correo y regresa*/
                        msj.setContent(multipart);                       
                        Transport.send(msj);
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {
                            sQ = "INSERT INTO logcorrs(corr,                                nodoc,                              estad,     motiv,  estac,                                       falt,      tipdoc,                corrde,                               sucu,                                           nocaj) " + 
                                          "VALUES('" + sCo1.replace("'", "''") + "','" +    sFol.replace("'", "''") + "',       'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(),     'FACTURA REENVIO', '" + sUsr.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                        
                            
                            sQ = "INSERT INTO estadiscor(corr,                                nodoc,                              estad,     motiv,  estac,                                       falt,      tipdoc,                corrde,                               sucu,                                           nocaj) " + 
                                          "VALUES('" + sCo1.replace("'", "''") + "','" +    sFol.replace("'", "''") + "',       'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(),     'FACTURA REENVIO', '" + sUsr.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
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
                            sQ = "INSERT INTO logcorrs(corr,                                nodoc,                              estad,       motiv,                                                     estac,                                  falt,       corrde,                             tipdoc,                     sucu,                                     nocaj) " + 
                                          "VALUES('" + sCo1.replace("'", "''") + "','" +    sFol.replace("'", "''") + "',       'FALLO','" + expnMessag.getMessage().replace("'", "''") + "','" +       Login.sUsrG.replace("'", "''") + "',    now(), '" + sUsr.replace("'", "''") + "',       'FACTURA REENVIO','" +      Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                            
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                               
                        }

                        /*Mandalo al corr alternativo*/
                        Star.vMandAlter(sNombPDF, sRutPDF, sCo1, sFol, null, 2, expnMessag.getMessage(), null);

                    }/*Fin de catch(MessagingException E)*/                                        

                }/*Fin de if(sCo1!=null)*/

                /*Si el primer corr no es null entonces*/
                if(sCo2!=null)
                {
                    /*Manda un corr*/
                    try
                    {
                        Properties props = System.getProperties();
                        props.setProperty("mail.smtp.host", sServSMTPSal);
                        props.put("mail.smtp.starttls.enable", sActSSL);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.debug", "true");
                        props.put("mail.smtp.port", sSMTPPort);
                        props.put("mail.store.protocol", "pop3");
                        props.put("mail.transport.protocol", "smtp");
                        final String username = sUsr;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });

                        MimeMessage  msj = new MimeMessage(session);
                        msj.setFrom(new InternetAddress(sUser));
                        msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sCo2));
                        msj.setSubject(sAsunFac + "\"" + sFol + "\"");
                        String msg             = sCuerFac;
                        msj.setContent(msg, "text/html; charset=utf-8");

                        /*Genera el adjunto*/
                        BodyPart msjbod = new MimeBodyPart();
                        msjbod.setText(sCuerFac);
                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(msjbod);

                        /*Adjunta el PDF*/
                        msjbod = new MimeBodyPart();                                               
                        DataSource src1 = new FileDataSource(sRutPDF);                        
                        msjbod.setDataHandler(new DataHandler(src1));
                        msjbod.setFileName(sNombPDF);
                        multipart.addBodyPart(msjbod);                                                

                        /*Adjunta el XML*/
                        msjbod = new MimeBodyPart();                                               
                        DataSource src2 = new FileDataSource(sRutXML);                        
                        msjbod.setDataHandler(new DataHandler(src2));                                                                        
                        msjbod.setFileName(sNombXML);
                        multipart.addBodyPart(msjbod);                        

                        /*Relaciona todo el contenido del corr y manda el corr*/
                        msj.setContent(multipart);                       
                        Transport.send(msj);   
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                        
                            sQ = "INSERT INTO logcorrs (corr,                                   nodoc,                          estad,     motiv,  estac,                                       falt,       tipdoc,                 corrde,                             sucu,                                               nocaj) " + 
                                           "VALUES('" + sCo2.replace("'", "''") + "','" +       sFol.replace("'", "''") + "',   'ENVIADO', '','" + Login.sUsrG.replace("'", "''") + "',     now(),      'FACTURA REENVIO', '" + sUsr.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +       Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                            
                            sQ = "INSERT INTO estadiscor(corr,                                   nodoc,                          estad,     motiv,  estac,                                       falt,       tipdoc,                 corrde,                             sucu,                                               nocaj) " + 
                                           "VALUES('" + sCo2.replace("'", "''") + "','" +       sFol.replace("'", "''") + "',   'ENVIADO', '','" + Login.sUsrG.replace("'", "''") + "',     now(),      'FACTURA REENVIO', '" + sUsr.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +       Star.sNoCaj.replace("'", "''") + "')";                    
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
                            sQ = "INSERT INTO logcorrs (corr,                              nodoc,                           estad,          motiv,                                               estac,                                  falt,       tipdoc,                corrde,                              sucu,                                     nocaj) " + 
                                           "VALUES('" + sCo2.replace("'", "''") + "','" +  sFol.replace("'", "''") + "',    'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" + Login.sUsrG.replace("'", "''") + "',    now(),      'FACTURA REENVIO', '" + sUsr.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "', '" +  Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                           
                        }
                        catch(SQLException expnSQL)
                        { 
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                          
                        }                     

                        /*Mandalo al corr alternativo*/
                        Star.vMandAlter(sNombPDF, sRutPDF, sCo2, sFol, null, 2, expnMessag.getMessage(), null);

                    }/*Fin de catch(MessagingException expnMessag)*/                       

                }/*Fin de if(sCo2!=null)*/

                /*Si el primer corr no es null entonces*/
                if(sCo3!=null)
                {
                    /*Manda un corr*/
                    try
                    {
                        Properties props = System.getProperties();
                        props.setProperty("mail.smtp.host", sServSMTPSal);
                        props.put("mail.smtp.starttls.enable", sActSSL);
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.debug", "true");
                        props.put("mail.smtp.port", sSMTPPort);
                        props.put("mail.store.protocol", "pop3");
                        props.put("mail.transport.protocol", "smtp");
                        final String username = sUsr;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });
                        
                        MimeMessage  msj = new MimeMessage(session);
                        msj.setFrom(new InternetAddress(sUser));
                        msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sCo3));
                        msj.setSubject(sAsunFac + "\"" + sFol + "\"");
                        String msg             = sCuerFac;
                        msj.setContent(msg, "text/html; charset=utf-8");

                        /*Genera el adjunto*/
                        BodyPart msjbod = new MimeBodyPart();
                        msjbod.setText(sCuerFac);
                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(msjbod);

                        /*Adjunta el PDF*/
                        msjbod = new MimeBodyPart();                                               
                        DataSource src1 = new FileDataSource(sRutPDF);                        
                        msjbod.setDataHandler(new DataHandler(src1));
                        msjbod.setFileName(sNombPDF);
                        multipart.addBodyPart(msjbod);                                                

                        /*Adjunta el XML*/
                        msjbod = new MimeBodyPart();                                               
                        DataSource src2 = new FileDataSource(sRutXML);                        
                        msjbod.setDataHandler(new DataHandler(src2));                                                                        
                        msjbod.setFileName(sNombXML);
                        multipart.addBodyPart(msjbod);                        

                        /*Relaciona todo el contenido del corr y manda el mensaje*/
                        msj.setContent(multipart);                       
                        Transport.send(msj);                        
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                        
                            sQ = "INSERT INTO logcorrs(corr,                                nodoc,                              estad,      motiv,  estac,                                      falt,       tipdoc,                 corrde,                             sucu,                                           nocaj) " + 
                                          "VALUES('" + sCo3.replace("'", "''") + "','" +    sFol.replace("'", "''") + "',       'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(),      'FACTURA REENVIO', '" + sUsr.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                       
                            
                            sQ = "INSERT INTO estadiscor(corr,                                nodoc,                              estad,      motiv,  estac,                                      falt,       tipdoc,                 corrde,                             sucu,                                           nocaj) " + 
                                          "VALUES('" + sCo3.replace("'", "''") + "','" +    sFol.replace("'", "''") + "',       'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(),      'FACTURA REENVIO', '" + sUsr.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
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
                            sQ = "INSERT INTO logcorrs(corr,                                nodoc,                              estad,          motiv,                                                  estac,                                  falt,       tipdoc,                 corrde,                             sucu,                                     nocaj) " + 
                                          "VALUES('" + sCo3.replace("'", "''") + "','" +    sFol.replace("'", "''") + "',       'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" +    Login.sUsrG.replace("'", "''") + "',    now(),      'FACTURA REENVIO', '" + sUsr.replace("'", "''") + "','" +   Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                            
                        }
                        catch(SQLException expnSQL)
                        { 
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                          
                        }

                        /*Mandalo al corr alternativo y regresa*/
                        Star.vMandAlter(sNombPDF, sRutPDF, sCo3, sFol, null, 2, expnMessag.getMessage(), null);              
                        return;

                    }/*Fin de catch(MessagingException expnMessag)*/                                          
                                        
                }/*Fin de if(sCo3!=null)*/

                //Esconde la forma de loading
                Star.vOcultLoadin();
                    
                //Cierra la base de datos
                Star.iCierrBas(con);                                                      

            }/*Fin de public void run()*/

        }).start();
        
    }/*Fin de private void vMandFac()*/
    

    /*Método para procesar todo el envio de las cotizaciones*/
    private void vMandCot()
    {
        //Declara variables locales
        final String      sID;                                        
                        
                
        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado un correo del que se reenviara.", "Reenviar cotización", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                        
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();           
            return;            
        }
        
        /*Si ninguno de los checkbox esta seleccionado entonces*/
        if(!jCCo1.isSelected() && !jCCo2.isSelected() && !jCCo3.isSelected())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado un correo a quién reenviar.", "Correo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el primer control de los correos y regresa*/
            jTCo1.grabFocus();            
            return;            
        }
        
        /*Preguntar al usr si esta seguro de querer reenviar la cotización*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que estan bien los datos?", "Reenviar cotización", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                    
        /*Si el checkbox de guadar correos de la empresa esta seleccionado entonces*/
        if(jCGua.isSelected())
        {            
            //Abre la base de datos
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Declara variables de la base de datos
            Statement   st;            
            String      sQ; 
            
            /*Actualiza en la base de datos los correos*/
            try
            {                           
                sQ = "UPDATE emps SET "
                        + "co1                              = '" + jTCo1.getText().replace("'", "''").trim() + "', "
                        + "co2                              = '" + jTCo2.getText().replace("'", "''").trim() + "', "
                        + "co3                              = '" + jTCo3.getText().replace("'", "''").trim() + "' "
                        + "WHERE CONCAT_WS('', ser, codemp) = '" + sCodEmp.replace("'", "''").trim() + "'";                                                                
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
        
        /*Obtén el id del correo seleccionado*/
        int row         = jTab.getSelectedRow();        
        sID             = jTab.getValueAt(row, 1).toString();                                                  

        //Muestra el loading
        Star.vMostLoading("");
        
        /*Ya se puede cerrar la aplicación*/
        bCie    = true;
        
        /*Manda el PDF a la empresa en un th*/        
        (new Thread()
        {
            @Override
            public void run()
            {                                                                
                /*Lee los correos que inserto el usr*/
                String sCo1            = jTCo1.getText();
                String sCo2            = jTCo2.getText();
                String sCo3            = jTCo3.getText();
                
                /*Determina a que correos se les va a mandar a la empresa*/
                if(!jCCo1.isSelected())
                    sCo1        = null;
                if(!jCCo2.isSelected())
                    sCo2        = null;
                if(!jCCo3.isSelected())
                    sCo3        = null;
                             
                //Abre la base de datos
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;                

                /*Declara variables locales del corr*/
                String      sServSMTPSal        = "";
                String      sSMTPPort           = "";
                String      sUsr                = "";
                String      sContra             = "";
                String      sActSSL             = "";
                String      sAsunCot            = "";
                String      sCuerCot            = "";

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ;
                
                /*Trae todos los datos del correo electrónico de la base de datos en base a su id*/                
                try
                {   
                    sQ = "SELECT srvsmtpsal, portsmtp, actslenlog, usr, pass, asuncot, cuerpcot FROM corrselec WHERE id_id = " + sID;                   
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos*/
                    if(rs.next())
                    {
                        /*Obtiene todos los datos de la consulta*/
                        sServSMTPSal            = rs.getString("srvsmtpsal");
                        sSMTPPort               = rs.getString("portsmtp");
                        sUsr                    = rs.getString("usr");
                        sContra                 = rs.getString("pass");
                        sAsunCot                = rs.getString("asuncot");
                        sCuerCot                = rs.getString("cuerpcot");

                        /*Si activar ssl login esta activado entonces guarda true*/
                        if(rs.getString("actslenlog").compareTo("1")==0)
                            sActSSL = "true";
                        else
                            sActSSL = "false";
                        
                        /*Desencripta la contraseña*/
                        sContra                = Star.sDecryp(sContra);                        
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;                                                                                          
                }
                
                /*Crea el usuario y la contraseña como final para que el thread valide si son válidos o no*/
                final String sUser          = sUsr;                
                
                /*Si el primer corr no es null entonces*/
                if(sCo1!=null)
                {
                    /*Manda el primero correo*/
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
                        final String username = sUsr;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });
                        
                        MimeMessage  msj = new MimeMessage(session);
                        msj.setFrom(new InternetAddress(sUser));
                        msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sCo1));
                        msj.setSubject(sAsunCot + "\"" + sCodCot + "\"");
                        String msg             = sCuerCot;
                        msj.setContent(msg, "text/html; charset=utf-8");

                        /*Genera el adjunto*/
                        BodyPart msjbod         = new MimeBodyPart();
                        msjbod.setText          (sCuerCot);
                        Multipart multipart     = new MimeMultipart();
                        multipart.addBodyPart   (msjbod);
                        
                        /*Adjunta el PDF*/
                        msjbod                  = new MimeBodyPart();                                               
                        DataSource src1         = new FileDataSource(sRutPDF);                        
                        msjbod.setDataHandler   (new DataHandler(src1));
                        msjbod.setFileName      (sNombPDF);
                        multipart.addBodyPart   (msjbod);                                                                        
                        
                        /*Relaciona todo el contenido del corr y manda el corr*/
                        msj.setContent(multipart);                       
                        Transport.send(msj);               
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                        
                            sQ = "INSERT INTO logcorrs(corr,                                nodoc,                              estad,      motiv,  estac,                                      falt,       tipdoc,                   corrde,                                   sucu,                                               nocaj) " + 
                                          "VALUES('" + sCo1.replace("'", "''") + "','" +    sCodCot.replace("'", "''") + "',    'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(),      'COTIZACIÓN REENVIO', '" + sUsr.replace("'", "''") + "','" +        Star.sSucu.replace("'", "''") + "','" +       Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                       
                            
                            sQ = "INSERT INTO estadiscor(corr,                                nodoc,                              estad,      motiv,  estac,                                      falt,       tipdoc,                   corrde,                                   sucu,                                               nocaj) " + 
                                          "VALUES('" + sCo1.replace("'", "''") + "','" +    sCodCot.replace("'", "''") + "',    'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(),      'COTIZACIÓN REENVIO', '" + sUsr.replace("'", "''") + "','" +        Star.sSucu.replace("'", "''") + "','" +       Star.sNoCaj.replace("'", "''") + "')";                    
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
                            sQ = "INSERT INTO logcorrs(corr,                                nodoc,                              estad,      motiv,                                                  estac,                                 falt,       corrde,                          tipdoc,                      sucu,                                    nocaj) " + 
                                          "VALUES('" + sCo1.replace("'", "''") + "','" +    sCodCot.replace("'", "''") + "',    'FALLO','" + expnMessag.getMessage().replace("'", "''") + "','" +   Login.sUsrG.replace("'", "''") + "',   now(), '" + sUsr.replace("'", "''") + "',    'COTIZACIÓN REENVIO','" +    Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                           
                        }
                        catch(SQLException expnSQL)
                        {                   
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                          
                        }
                              
                        /*Mandalo al corr alternativo*/
                        Star.vMandAlter(sNombPDF, sRutPDF, sCo1, sCodCot, null, 2, expnMessag.getMessage(), null);
                        
                    }/*Fin de catch(MessagingException expnMessag)*/                       
                    
                }/*Fin de if(sCo1!=null)*/

                /*Si el primer corr no es null entonces*/
                if(sCo2!=null)
                {
                    /*Manda un corr*/
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
                        final String username = sUsr;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });
                        
                        MimeMessage  msj = new MimeMessage(session);
                        msj.setFrom(new InternetAddress(sUser));
                        msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sCo2));
                        msj.setSubject(sAsunCot + "\"" + sCodCot + "\"");
                        String msg             = sCuerCot;
                        msj.setContent(msg, "text/html; charset=utf-8");

                        /*Genera el adjunto*/
                        BodyPart msjbod = new MimeBodyPart();
                        msjbod.setText(sCuerCot);
                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(msjbod);
                        
                        /*Adjunta el PDF*/
                        msjbod = new MimeBodyPart();                                               
                        DataSource src1 = new FileDataSource(sRutPDF);                        
                        msjbod.setDataHandler(new DataHandler(src1));
                        msjbod.setFileName(sNombPDF);
                        multipart.addBodyPart(msjbod);                                                
                        
                        /*Relaciona todo el contenido del corr y manda el corr*/
                        msj.setContent(multipart);                       
                        Transport.send(msj);        
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                        
                            sQ = "INSERT INTO logcorrs(corr,                                nodoc,                          estad,     motiv,  estac,                                   falt,        tipdoc,                   corrde,                          sucu,                                           nocaj) " + 
                                          "VALUES('" + sCo2.replace("'", "''") + "','" +    sCodCot.replace("'", "''") + "','ENVIADO', '','" + Login.sUsrG.replace("'", "''") + "', now(),      'COTIZACIÓN REENVIO', '" + sUsr.replace("'", "''") + "','" +Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                        
                            
                            sQ = "INSERT INTO estadiscor(corr,                                nodoc,                          estad,     motiv,  estac,                                   falt,        tipdoc,                   corrde,                          sucu,                                           nocaj) " + 
                                          "VALUES('" + sCo2.replace("'", "''") + "','" +    sCodCot.replace("'", "''") + "','ENVIADO', '','" + Login.sUsrG.replace("'", "''") + "', now(),      'COTIZACIÓN REENVIO', '" + sUsr.replace("'", "''") + "','" +Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
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
                            sQ = "INSERT INTO logcorrs(corr,                                nodoc,                              estad,             motiv,                                               estac,                                  falt,       tipdoc,                    corrde,                              sucu,                                         nocaj) " + 
                                          "VALUES('" + sCo2.replace("'", "''") + "','" +    sCodCot.replace("'", "''") + "',    'FALLO','" +       expnMessag.getMessage().replace("'", "''") + "','" + Login.sUsrG.replace("'", "''") + "',    now(),      'COTIZACIÓN REENVIO', '" + sUsr.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "', '" +      Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                            
                        }
                        catch(SQLException expnSQL)
                        {                   
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                          
                        }                     
                        
                        /*Mandalo al corr alternativo*/
                        Star.vMandAlter(sNombPDF, sRutPDF, sCo2, sCodCot, null, 2, expnMessag.getMessage(), null);
                    
                    }/*Fin de catch(MessagingException expnMessag)*/                   
                    
                }/*Fin de if(sCo2!=null)*/

                /*Si el primer corr no es null entonces*/
                if(sCo3!=null)
                {
                    /*Manda un corr*/
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
                        final String username = sUsr;
                        final String password = sContra;
                        Session session = Session.getDefaultInstance(props,
                                new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(username, password);
                                    }
                                });
                        
                        MimeMessage  msj = new MimeMessage(session);
                        msj.setFrom(new InternetAddress(sUser));
                        msj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(sCo3));
                        msj.setSubject(sAsunCot + "\"" + sCodCot + "\"");
                        String msg             = sCuerCot;
                        msj.setContent(msg, "text/html; charset=utf-8");

                        /*Genera el adjunto*/
                        BodyPart msjbod = new MimeBodyPart();
                        msjbod.setText(sCuerCot);
                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(msjbod);
                        
                        /*Adjunta el PDF*/
                        msjbod = new MimeBodyPart();                                               
                        DataSource src1 = new FileDataSource(sRutPDF);                        
                        msjbod.setDataHandler(new DataHandler(src1));
                        msjbod.setFileName(sNombPDF);
                        multipart.addBodyPart(msjbod);                                                
                        
                        /*Relaciona todo el contenido del corr y manda el mensaje*/
                        msj.setContent(multipart);                       
                        Transport.send(msj);  
                        
                        /*Ingresa en la base de datos el registor de que se mando con éxito*/
                        try 
                        {                        
                            sQ = "INSERT INTO logcorrs(corr,                                nodoc,                              estad,      motiv,  estac,                                  falt,       tipdoc,                    corrde,                              sucu,                                     nocaj) " + 
                                          "VALUES('" + sCo1.replace("'", "''") + "','" +    sCodCot.replace("'", "''") + "',    'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(),      'COTIZACIÓN REENVIO', '" + sUsr.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                       
                            
                            sQ = "INSERT INTO estadiscor(corr,                                nodoc,                              estad,      motiv,  estac,                                      falt,       tipdoc,                    corrde,                              sucu,                                           nocaj) " + 
                                          "VALUES('" + sCo1.replace("'", "''") + "','" +    sCodCot.replace("'", "''") + "',    'ENVIADO',  '','" + Login.sUsrG.replace("'", "''") + "',    now(),      'COTIZACIÓN REENVIO', '" + sUsr.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
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
                            sQ = "INSERT INTO logcorrs(corr,                                nodoc,                              estad,          motiv,                                                  estac,                                  falt,       tipdoc,                   corrde,                           sucu,                                     nocaj) " + 
                                          "VALUES('" + sCo3.replace("'", "''") + "','" +    sCodCot.replace("'", "''") + "',    'FALLO','" +    expnMessag.getMessage().replace("'", "''") + "','" +    Login.sUsrG.replace("'", "''") + "',    now(),      'COTIZACIÓN REENVIO', '" + sUsr.replace("'", "''") + "','" +Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);                            
                        }
                        catch(SQLException expnSQL)
                        {   
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                                                      
                        }
                        
                        /*Mandalo al corr alternativo*/
                        Star.vMandAlter(sNombPDF, sRutPDF, sCo3, sCodCot, null, 2, expnMessag.getMessage(), null);                                                
                                                
                    }/*Fin de catch(MessagingException expnMessag)*/                   
                    
                }/*Fin de if(sCo3!=null)*/

                //Esconde la forma de loading
                Star.vOcultLoadin();
                    
                //Cierra la base de datos
                Star.iCierrBas(con);               
                
            }/*Fin de public void run()*/
            
        }).start();
                         
    }/*Fin de vMandCot()*/
    
    
    /*Cuando se presiona el botón de enviar*/
    private void jBEnviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEnviActionPerformed

        /*Si no a escrito un correo entonces*/
        if(jTCo1.getText().compareTo("")==0 && jTCo2.getText().compareTo("")==0 && jTCo3.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Coloca el foco del teclado en el control*/
            jTCo1.grabFocus();
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "Ingresa por lo menos un correo electrónico.", "Enviar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }
        
        /*Si es envio de factura entonces manda la factura*/
        if(iTip==1)
            vMandFac();            
        /*Else if es cotizaciones entonces manda la cotización*/
        else if(iTip==2)
            vMandCot();
                    
        /*Cierra la forma*/
        if(bCie)
        {
            /*Llama al recolector de basura*/
            System.gc();
            this.dispose();        
        }
        
    }//GEN-LAST:event_jBEnviActionPerformed

    
    /*Cuando se da clic con el mouse en la tabla de registros*/
    private void jTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabMouseClicked
        
        /*Si se hiso doble clic entonces presiona el botón de cargar*/
        if(evt.getClickCount() == 2) 
            jBEnvi.doClick();
        
    }//GEN-LAST:event_jTabMouseClicked

    
    /*Cuando se presiona una tecla en el campo de corr 1*/
    private void jTCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de corr 2*/
    private void jTCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de corr 3*/
    private void jTCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCo3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCo3KeyPressed

    
    /*Cuando se presiona una tecla en el panel 2*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de corr 1*/
    private void jCCo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo1KeyPressed

    
    /*Cuando se presiona una tecla en el check box de corr 2*/
    private void jCCo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo2KeyPressed

    
    /*Cuando se presiona una tecla en el checkbox de corre 3*/
    private void jCCo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCCo3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCCo3KeyPressed

    
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

    
    /*Cuando se pierde el foco del teclado en el campo del corr 1*/
    private void jTCo1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo1.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCo1.getText().compareTo("")!=0)
            jTCo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTCo1.getText().length()> 100)
            jTCo1.setText(jTCo1.getText().substring(0, 100));        
        
    }//GEN-LAST:event_jTCo1FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo del corr 2*/
    private void jTCo2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo2.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTCo2.getText().length()> 100)
            jTCo2.setText(jTCo2.getText().substring(0, 100));
        
    }//GEN-LAST:event_jTCo2FocusLost

    
    /*Cuando se pierde el foco del teclado en el cmapo del corr 3*/
    private void jTCo3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCo3FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCo3.setCaretPosition(0);
        
        /*Si el campo excede la cantidad de caracteres permitidos recortalo*/
        if(jTCo3.getText().length()> 100)
            jTCo3.setText(jTCo3.getText().substring(0, 100));
        
    }//GEN-LAST:event_jTCo3FocusLost

    
    /*Cuando se presiona una tecla en el checkbox de guardar correos*/
    private void jCGuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCGuaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCGuaKeyPressed

    
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

    
    /*Cuando sucede una acción en el checkbox de guardar correos de empresa*/
    private void jCGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCGuaActionPerformed
        
        /*Si es seleccionado entonces*/
        if(jCGua.isSelected())
        {
            /*Si es clietne mostrador entonces*/
            if(sCodEmp.compareTo(Star.sCliMostG)==0)
            {
                /*Mensajea y resetea el control*/
                JOptionPane.showMessageDialog(null, "No se puede modificar el cliente mostrador.", "Modificar Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                jCGua.setSelected(false);
            }
        }
        
    }//GEN-LAST:event_jCGuaActionPerformed

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBEnviMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEnviMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBEnvi.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBEnviMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBEnviMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEnviMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBEnvi.setBackground(colOri);
        
    }//GEN-LAST:event_jBEnviMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited
                            
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona F2*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
        {
            /*Marca o desmarca el control de guardar correos de la empresa*/
            if(jCGua.isSelected())
                jCGua.setSelected(false);
            else
                jCGua.setSelected(true);            
        }        
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBEnvi;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JCheckBox jCCo1;
    private javax.swing.JCheckBox jCCo2;
    private javax.swing.JCheckBox jCCo3;
    private javax.swing.JCheckBox jCGua;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLDat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jP1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTCo1;
    private javax.swing.JTextField jTCo2;
    private javax.swing.JTextField jTCo3;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
