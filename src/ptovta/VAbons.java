//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para ver los abonos de un cxc o cxp*/
public class VAbons extends javax.swing.JFrame
{
    /*Declara variables de instancia*/       
    private final String     sIdGlo;    

    /*Contador para modificar tabla*/
    private int              iContCellEd;
    
    /*Declara variables originales*/
    private String           sFolConsecOri;
    private String           sFolOri;
    private String           sFechOri;
    private String           sImpoOri;
    private String           sSucOri;
    private String           sCajOri;
    private String           sEstacOri;
    private String           sComenOri;
    private String           sFormOri;
    private String           sConcepOri;
    private String           sConcepsOri;

    /*Contiene los datos de ese CXC*/
    private String          sFolCXC     = "";
    private String          sNoSerCXC   = "";
    private String          sEmpCXC     = "";
    
    /*Contiene el tipo de documento con el que se esta trabajando*/
    private String          sTipDoc;
    
    
    
    
    /*Constructor sin argumentos*/
    public VAbons(String sId, String sBusc, String sProvCli) 
    {           
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Esconde la columna del id*/
        jTab.getColumnModel().getColumn(10).setMinWidth(0);
        jTab.getColumnModel().getColumn(10).setMaxWidth(0);
        
        //Inicializa el proveedor o cliente
        sEmpCXC = sProvCli;
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Recibe el id de CXCP o CXC*/
        sIdGlo          = sId;        
        
        /*Obtiene el tipo de documento con el que se esta trabajando*/
        sTipDoc         = sBusc;
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Contiene el nombre del campo del cliente/proveedor*/
        String  sNomCam = "empre";

        /*Contiene el nombre de la tabla*/
        String sTab     = "cxc";

        /*Si el documento es de CXP entonces*/            
        if(sTipDoc.compareTo("comp")==0)
        {
            /*Búsca en la tabla de CXP y el nombre del campo será proveedo*/
            sTab        = "cxp";
            sNomCam     = "prov";
        }

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene los datos de ese CXC o CXP*/
        try
        {
            sQ = "SELECT norefer, noser, " + sNomCam + " FROM " + sTab + " WHERE id_id = " + sId;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sFolCXC     = rs.getString("norefer");
                sNoSerCXC   = rs.getString("noser");
                sEmpCXC     = rs.getString(sNomCam);
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

        /*Esconde la columna del ID de la tabla*/
        jTab.getColumnModel().getColumn(7).setMinWidth(0);

        /*Establece el tamaño de las columnas de la tabla*/
        jTab.getColumnModel().getColumn(0).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(140);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(130);        
        jTab.getColumnModel().getColumn(7).setPreferredWidth(300);        
        jTab.getColumnModel().getColumn(8).setPreferredWidth(120);
        jTab.getColumnModel().getColumn(9).setPreferredWidth(120);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
                
        /*Crea el mensaje a mostrar*/
        String sMsj = "CXC";
        if(sBusc.compareTo("comp")==0)
            sMsj    = "CXP";
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Abonos " + sMsj + ", Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el botón de salir*/
        jBSal.grabFocus();
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Busca lo que corresponda en abonos*/
        if(sBusc.compareTo("vtas")==0)
            vCargVta(sProvCli);                        
        /*Else carga los abonos de las compras*/
        else if(sBusc.compareTo("comp")==0)
            vCargComp(sProvCli);                        

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
                        sFolConsecOri   = jTab.getValueAt(jTab.getSelectedRow(), 0).toString();
                        sFolOri         = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sFechOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sImpoOri        = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sSucOri         = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sCajOri         = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sEstacOri       = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sComenOri       = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sFormOri        = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sConcepOri      = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sConcepsOri     = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sFolConsecOri,  jTab.getSelectedRow(), 0);                        
                        jTab.setValueAt(sFolOri,        jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sFechOri,       jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sImpoOri,       jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sSucOri,        jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sCajOri,        jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sEstacOri,      jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sComenOri,      jTab.getSelectedRow(), 7);
                        jTab.setValueAt(sFormOri,       jTab.getSelectedRow(), 8);
                        jTab.setValueAt(sConcepOri,     jTab.getSelectedRow(), 9);
                        jTab.setValueAt(sConcepsOri,     jTab.getSelectedRow(), 11);
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public VAbons() */

    
    /*Función para saber de que base de datos cargar los datos*/
    private void vCargVta(String sCli)
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

        //Determina la consulta dependiendo si son abonos solamente o abonos de documento
        if(sCli!=null)
            sQ = "SELECT cxc.COMEN, cxc.ID_ID, cxc.FOL, cxc.NOREFER, abon, cxc.ID_ID, cxc.FALT, cxc.ESTAC, cxc.NOCAJ, cxc.SUCU, cxc.FORMPAG, cxc.CONCEPPAG, cxc.concep FROM cxc WHERE empre = '" + sCli + "' AND concep <> 'ACA ABON' AND abon >0 ";                                                
        else
            sQ = "SELECT cxc.COMEN, cxc.ID_ID, cxc.FOL, cxc.NOREFER, abon, cxc.ID_ID, cxc.FALT, cxc.ESTAC, cxc.NOCAJ, cxc.SUCU, cxc.FORMPAG, cxc.CONCEPPAG,cxc.concep FROM cxc WHERE norefer = '" + sFolCXC + "' AND noser = '" + sNoSerCXC + "' AND empre = '" + sEmpCXC + "' AND carg = 0 AND concep <> 'ACA ABON'";                                                

        /*Obtiene todos los abonos de ese CXC*/
        try
        {            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Dale formato de moneda al abono*/                
                double dCant    = Double.parseDouble(rs.getString("abon"));                
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                String sAbon    = n.format(dCant);
                
                /*Agrega el registro en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {rs.getString("fol"), rs.getString("cxc.NOREFER"), rs.getString("cxc.FALT"), sAbon, rs.getString("sucu"), rs.getString("cxc.NOCAJ"), rs.getString("cxc.ESTAC"), rs.getString("cxc.COMEN"), rs.getString("cxc.FORMPAG"), rs.getString("cxc.CONCEPPAG"), rs.getString("cxc.ID_ID"), rs.getString("cxc.concep")};
                te.addRow(nu);                                                
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
        
    }/*Fin de private void vCargVta()*/
        
            
    /*Carga todos los abonos de la compra en específico*/
    private void vCargComp(String sProvA)
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Contiene los datos del CXC actual*/
        String sNoRefer = "";
        String sNoSer   = "";
        String sProv    = "";

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene el folio, la serie y el documento de ese CXC*/
        try
        {
            sQ = "SELECT norefer, noser, prov FROM cxp WHERE id_id = " + sIdGlo;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next())
            {
                sNoRefer    = rs.getString("norefer");
                sNoSer      = rs.getString("noser");
                sProv       = rs.getString("prov");
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                                                                                                                                    
        }  
        
        //Determina la consulta si se van a mostrar abonos de concepto o de documento
        if(sProvA!=null)
            sQ = "SELECT cxp.COMEN, cxp.FOL, cxp.NOREFER, abon, cxp.ID_ID, cxp.FALT, cxp.ESTAC, cxp.NOCAJ, cxp.SUCU, cxp.FORMPAG, cxp.CONCEPPAG, cxp.concep FROM cxp WHERE prov = '" + sProvA + "' AND concep <> 'COMP' AND abon >0";                                    
        else
            sQ = "SELECT cxp.COMEN, cxp.FOL, cxp.NOREFER, abon, cxp.ID_ID, cxp.FALT, cxp.ESTAC, cxp.NOCAJ, cxp.SUCU, cxp.FORMPAG, cxp.CONCEPPAG, cxp.concep FROM cxp WHERE norefer = '" + sNoRefer + "' AND noser = '" + sNoSer + "' AND prov = '" + sProv + "' AND carg = 0 AND concep = 'ABON COMP'";                                    
        
        /*Obtiene todos los abonos de ese CXP*/
        try
        {            
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Dale formato de moneda al abono*/                
                double dCant    = Double.parseDouble(rs.getString("abon"));                
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                String sAbon    = n.format(dCant);
                
                /*Agrega el registro en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {rs.getString("fol"), rs.getString("cxp.NOREFER"), rs.getString("cxp.FALT"), sAbon, rs.getString("sucu"), rs.getString("cxp.NOCAJ"), rs.getString("cxp.ESTAC"), rs.getString("comen"), rs.getString("cxp.FORMPAG"), rs.getString("cxp.CONCEPPAG"), rs.getString("cxp.ID_ID"), rs.getString("cxp.concep")};
                te.addRow(nu);                                                
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
        
    }/*Fin de private void vCargComp()*/
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBTab1 = new javax.swing.JButton();
        jBCan = new javax.swing.JButton();

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

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 110, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Folio Consecutivo", "Folio", "Fecha", "Abono", "Sucursal", "Caja", "Usuario", "Comentario", "Forma de pago", "Concepto de pago", "ID", "Concepto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBCan);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 240));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 220, 10, 20));

        jBCan.setBackground(new java.awt.Color(255, 255, 255));
        jBCan.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCan.setForeground(new java.awt.Color(0, 102, 0));
        jBCan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/can.png"))); // NOI18N
        jBCan.setText("Cancelar");
        jBCan.setToolTipText("Cancelar pago (Ctrl+Supr)");
        jBCan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBCan.setNextFocusableComponent(jBSal);
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
        jP1.add(jBCan, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, 110, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

       
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed
   
    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
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

    
    /*Cuando se presiona el botón de ver la tabla*/
    private void jBTab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTab1ActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab);       

    }//GEN-LAST:event_jBTab1ActionPerformed

    
    /*Cuando se presiona una tecla en el notón de ver la tabla*/
    private void jBTab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTab1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTab1KeyPressed

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse entra en el botón de cancelar abono*/
    private void jBCanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCanMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCan.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCanMouseEntered

    
    /*Cuando el mouse sale del botón de cancelar abono*/
    private void jBCanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCanMouseExited
        
        /*Cambia el color del fondo del botón*/
        jBCan.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBCanMouseExited

    
    /*Cuando se presiona el botón de cancelar abono*/
    private void jBCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCanActionPerformed
        
        /*Si no hay pagos entonces*/
        if(jTab.getRowCount()==0)
        {            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No hay abonos para borrar.", "Cancelar Pago(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        /*Si no hay selección en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {   
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un abono para cancelar.", "Cancelar Pago(s)", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }
        
        /*Preguntar al usuario si esta seguro de querer cancelar el o los pagos*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres cancelar el(los) pago(s)?", "Cancelar Pago(s)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Contiene algunos datos del CXC*/
            String sTot     = "";
            String sSubTot  = "";
            String sImpue   = "";
            String sFDoc    = "";
            String sFVenc   = "";
            String sAbon    = "";
            
            /*Contiene el nombre del campo del cliente*/
            String  sNomCam = "empre";
            
            /*Contiene el nombre de la tabla*/
            String sTab     = "cxc";
            
            /*Si el documento es de CXP entonces*/            
            if(sTipDoc.compareTo("comp")==0)
            {
                /*Búsca en la tabla de CXP y el nombre del campo será proveedo*/
                sTab        = "cxp";
                sNomCam     = "prov";
            }
                
            /*Obtiene algunos datos de ese CXC*/
            try
            {
                sQ = "SELECT tot, abon, subtot, impue, fvenc, fdoc FROM " + sTab + " WHERE id_id = " + jTab.getValueAt(iSel[x], 10);                                                        
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces obtiene los resultados*/
                if(rs.next())
                {
                    sSubTot = rs.getString("subtot");
                    sImpue  = rs.getString("impue");
                    sTot    = rs.getString("tot");                    
                    sAbon   = rs.getString("abon");
                    sFDoc   = rs.getString("fdoc");
                    sFVenc  = rs.getString("fvenc");
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                           
            }  

            /*Inserta el CXC/CXP de ajuste por abono cancelado*/
            try 
            {     
                sQ = "INSERT INTO " + sTab + "(         norefer,                        noser,                 " +  sNomCam +    ",         ser,                    tot,               impue,                  subtot,           carg,       abon,     fvenc,              fdoc,           falt, fmod,       estac,                                            sucu,                                    nocaj,                               concep )" + 
                                        "VALUES('" +    sFolCXC + "',         '" +      sNoSerCXC + "', '" +        sEmpCXC + "', '" +      sNoSerCXC + "'," +      sTot + ", " +      sImpue + ",       " +   sSubTot + ",   " + sAbon + ", 0,   '" + sFVenc + "', '" +   sFDoc + "',     now(), now(), '" + Login.sUsrG.replace("'", "''") +"',  '" +        Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "', 'CA ABON')";                                    
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                           
            }
            
            /*Actualiza ese pago para saber que fue de abono a cancelación*/
            try 
            {                
                sQ = "UPDATE " + sTab + " SET concep = 'CA ABON', fvenc= '" +  sFVenc + "' WHERE id_id = " + jTab.getValueAt(iSel[x], 10).toString().trim();                                               
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                            
            }
            
            /*Borralo de la tabla*/            
            tm.removeRow(iSel[x]);
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;

        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Mensajea de exito*/
        JOptionPane.showMessageDialog(null, "Abono(s) cancelados con éxito.", "Cancelar abonos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBCanActionPerformed

    
    /*Cuando se presiona una tecla en el botón de cancelar pago*/
    private void jBCanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCanKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCanKeyPressed
    
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de cancelar pago*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBCan.doClick();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCan;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
