//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para búscar ventas y escoger alguna*/
public class BuscComp extends javax.swing.JFrame
{
    /*Declara variables de instancia*/      
    private final String                sBusc;
    private final JTextField            jTFCamp;
    
    /*Contiene el color original del botón*/
    private final java.awt.Color        colOri;
      
    /*Contiene la tabla del otro formulario*/
    private javax.swing.JTable          jTabOtr;
    
    /*Contiene el tipo de documento que se esta trabajando*/
    private String                      sTipDoc;
    
    /*Contiene el código de cliente*/
    private String                      sProv;
    
    /*Contiene el contador de tabla de fila del otro formulario*/
    private int                         iContFi;

    
    
    
    
    /*Constructor sin argumentos*/
    public BuscComp(JFrame jFram, String sBu, JTextField jCamp1, String sTip, javax.swing.JTable jTa, String sPro, int iContF) 
    {           
        /*Inicaliza los componentes gráficos*/
        initComponents();

        //Que sea modal la forma
        this.setAlwaysOnTop(true);
        
        /*Para que la tabla tenga scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el tamaño de las columnas de la tabla*/
        jTab.getColumnModel().getColumn(3).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(5).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(6).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(8).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(9).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(15).setPreferredWidth(250);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBCarg);
                        
        /*Obtiene el handle de la tabla del otro formulario*/
        jTabOtr     = jTa;
        
        /*Obtiene el tipo de documento con el que se esta trabajando*/
        sTipDoc     = sTip;
        
        /*Obtiene el código del proveedor de quién se mostraran los documentos*/
        sProv       = sPro;
        
        /*Obtiene el contador de fila del otro formulario*/
        iContFi     = iContF;

        /*Obtiene el color original que deben tener los botones*/
        colOri      = jBSal.getBackground();
        
        /*Para que la tabla de partidas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Que las comlumnas de la tabla no se muevan*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Recibe lo que se va a búscar*/
        sBusc           = sBu;
        
        /*Recibe el manejador del campo del otro formulario*/
        jTFCamp         = jCamp1;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Busqueda compras, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Va a ser top most la ventana*/
        this.setAlwaysOnTop(true);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Crea el listener para cuando se cambia de selección en la tabla*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {   
                /*Si no hay selección regresa*/
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Dale formato de moneda al total*/                
                java.text.NumberFormat n    = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US);
                double dCant                = Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 12).toString());                
                
                /*Coloca en el label de total el total de la factura ya con formato*/
                jLTot.setText(n.format(dCant));
            }
        });
        
        /*Si el tipo es de compras entonces muestra las compras, caso contrario carga las notas de crédito pendientes*/
        if(sTip.compareTo("fac")==0)
            vCargCom(sBusc);                        
        else
            vCargNot(sBusc);                        
        
    }/*Fin de public BuscComp() */

    
    /*Funciòn para cargar todos los registros con el filtro de búsqueda*/
    private void vCargCom(String sBusc)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBusc                   = sBusc.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de compras*/
        try
        {
            sQ = "SELECT nomprov, codcomp, nodoc, noser, tip, comprs.PROV, comprs.SER, comprs.METPAG, comprs.CTA, fdoc, subtot, impue, tot, comprs.ESTADO, motiv, comprs.OBSERV, comprs.SUCU, comprs.NOCAJ, comprs.ESTAC, fvenc FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = comprs.PROV WHERE tip = 'COMP'  AND notcred = '' AND comprs.PROV = '" + sProv + "' AND (CASE WHEN '" + sBusc + "' = '' THEN nodoc = nodoc ELSE nodoc LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN codcomp = codcomp ELSE codcomp LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN noser = noser ELSE noser LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN tip = tip ELSE tip LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.PROV = comprs.PROV ELSE comprs.PROV LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.SER = comprs.SER ELSE comprs.SER LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.METPAG = comprs.METPAG ELSE comprs.METPAG LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.CTA = comprs.CTA ELSE comprs.CTA LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN fdoc = fdoc ELSE fdoc LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN subtot = subtot ELSE subtot LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN impue = impue ELSE impue LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN tot = tot ELSE tot LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.ESTADO = comprs.ESTADO ELSE comprs.ESTADO LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN motiv = motiv ELSE motiv LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.OBSERV = comprs.OBSERV ELSE comprs.OBSERV LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.SUCU = comprs.SUCU ELSE comprs.SUCU LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.NOCAJ = comprs.NOCAJ ELSE comprs.NOCAJ LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.ESTAC = comprs.ESTAC ELSE comprs.ESTAC LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN fvenc = fvenc ELSE fvenc LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN nomprov = nomprov ELSE nomprov LIKE('%" + sBusc + "%') END) ORDER BY comprs.ID_ID DESC";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agrega los registros en la tabla*/
            while(rs.next())
            {                
                Object nu[]     = {rs.getString("codcomp"), rs.getString("nodoc"), rs.getString("noser"), rs.getString("tip"), rs.getString("prov"), rs.getString("ser"), rs.getString("metpag"), rs.getString("cta"), rs.getString("fdoc"), rs.getString("fvenc"), rs.getString("subtot"), rs.getString("impue"), rs.getString("tot"), rs.getString("comprs.ESTADO"), rs.getString("motiv"), rs.getString("observ"), rs.getString("sucu"), rs.getString("nocaj"), rs.getString("estac")};
                tm.addRow(nu);                                
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
        
    }/*Fin de private void vCargCom(String sBusc, int iEn)*/
        
        
    /*Funciòn para cargar todas las notas de crédito en la forma de búsqueda*/
    private void vCargNot(String sBusc)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBusc                   = sBusc.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros coincidentes de la base de datos de ventas*/
        try
        {
            sQ = "SELECT nom, codcomp, nodoc, noser, tip, comprs.PROV, comprs.SER, comprs.METPAG, comprs.CTA, fdoc, subtot, impue, tot, comprs.ESTADO, motiv, comprs.OBSERV, comprs.SUCU, comprs.NOCAJ, comprs.ESTAC, fvenc FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = comprs.PROV WHERE tip = 'NOTP'  AND comprs.ESTADO = 'PE' AND comprs.PROV = '" + sProv + "' AND (CASE WHEN '" + sBusc + "' = '' THEN nodoc = nodoc ELSE nodoc LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN codcomp = codcomp ELSE codcomp LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN noser = noser ELSE noser LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN tip = tip ELSE tip LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.PROV = comprs.PROV ELSE comprs.PROV LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.SER = comprs.SER ELSE comprs.SER LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.METPAG = comprs.METPAG ELSE comprs.METPAG LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.CTA = comprs.CTA ELSE comprs.CTA LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN fdoc = fdoc ELSE fdoc LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN subtot = subtot ELSE subtot LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN impue = impue ELSE impue LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN tot = tot ELSE tot LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.ESTADO = comprs.ESTADO ELSE comprs.ESTADO LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN motiv = motiv ELSE motiv LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.OBSERV = comprs.OBSERV ELSE comprs.OBSERV LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.SUCU = comprs.SUCU ELSE comprs.SUCU LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.NOCAJ = comprs.NOCAJ ELSE comprs.NOCAJ LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN comprs.ESTAC = comprs.ESTAC ELSE comprs.ESTAC LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN fvenc = fvenc ELSE fvenc LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN nomprov = nomprov ELSE nomprov LIKE('%" + sBusc + "%') END) ORDER BY comprs.ID_ID DESC";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces agregalos a la tabla*/
            while(rs.next())
            {                
                Object nu[]     = {rs.getString("codcomp"), rs.getString("nodoc"), rs.getString("noser"), rs.getString("tip"), rs.getString("prov"), rs.getString("ser"), rs.getString("metpag"), rs.getString("cta"), rs.getString("fdoc"), rs.getString("fvenc"), rs.getString("subtot"), rs.getString("impue"), rs.getString("tot"), rs.getString("comprs.ESTADO"), rs.getString("motiv"), rs.getString("observ"), rs.getString("sucu"), rs.getString("nocaj"), rs.getString("estac")};
                tm.addRow(nu);                                
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
        
    }/*Fin de private void vCargNot(String sBusc, int iEn)*/
    
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBCarg = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jLAyu = new javax.swing.JLabel();
        jLTot = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

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

        jBCarg.setBackground(new java.awt.Color(255, 255, 255));
        jBCarg.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBCarg.setForeground(new java.awt.Color(0, 102, 0));
        jBCarg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/carg.png"))); // NOI18N
        jBCarg.setText("Aceptar");
        jBCarg.setToolTipText("Aceptar (ENTER)");
        jBCarg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBCarg.setNextFocusableComponent(jBSal);
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
        jP1.add(jBCarg, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 140, 30));

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 140, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Compra", "Folio", "Serie", "Tipo Documento", "Proveedor", "Serie Proveedor", "Método Pago", "Cuenta", "Fecha", "Fecha Vencimiento", "SubTotal", "Impuesto", "Total", "Estado", "Motivo", "Observaciones", "Sucursal", "Caja", "Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setInheritsPopupMenu(true);
        jTab.setNextFocusableComponent(jBCarg);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 300));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 280, 130, -1));

        jLTot.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLTot.setForeground(new java.awt.Color(204, 0, 0));
        jLTot.setFocusable(false);
        jP1.add(jLTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 120, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setText("TOTAL:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, 90, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
       
    
    /*Cuando se presiona una tecla en el botón de cargar*/
    private void jBCargKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCargKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBCargKeyPressed

    
    /*Cuando se presiona el botón de cargar*/
    private void jBCargActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargActionPerformed

        /*Si no hay selección en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Llama al recolector de basura*/
            System.gc();
        
            /*Cierra el formulario y regresa*/
            this.dispose();                        
            return;                        
        }

        /*Si el tipo de documento es factura entonces coloca la serie y el folio de la venta en el otro formulario*/
        if(sTipDoc.compareTo("fac")==0)                   
            jTFCamp.setText     (jTab.getValueAt(jTab.getSelectedRow(), 0).toString());       
        /*Else es otro documento entonces*/
        else
        {                               
            /*Recorre toda la selección del usuario*/
            int iSel[]              = jTab.getSelectedRows();
            DefaultTableModel tm    = (DefaultTableModel)jTabOtr.getModel();
            for(int x = iSel.length - 1; x >= 0; x--)
            {   
                /*Recorre la tabla de partidas del otro formulario en búsca de esta nota de crédito y evitar duplicidades*/
                boolean bCon        = false;
                for(int xI = 0; xI < jTabOtr.getRowCount(); xI++)
                {   
                    /*Si la nota de crédito ya esta carga en la tabla entonces*/
                    if(jTabOtr.getValueAt(xI, 1).toString().compareTo(jTab.getValueAt(iSel[x], 2).toString() + jTab.getValueAt(iSel[x], 1).toString())==0)
                    {
                        /*Coloca la bandera para saber que ya esta cargada y sal del búcle*/
                        bCon        = true;                                                
                        break;
                    }
                }
                
                /*Si tiene que continuar entonces hazlo*/
                if(bCon)
                    continue;
                
                /*Dale formato de moneda al total*/                
                java.text.NumberFormat n    = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.US);
                double dCant                = Double.parseDouble(jTab.getValueAt(iSel[x], 12).toString());                
                String sImpo                = n.format(dCant);

                /*Agrega el registro en la tabla del otro formulario*/            
                Object nu[]             = {iContFi, jTab.getValueAt(iSel[x], 2).toString() + jTab.getValueAt(iSel[x], 1).toString(), "0", "", "", "APLICA NOTA DE CRÉDITO", "", "", "", "", sImpo, "", "0", "", "", "", "", "", "", "", "$0.00", "", "", "", "0"};
                tm.addRow(nu);
            }               
        }
                   
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Sal del formulario*/
        this.dispose();        
        
    }//GEN-LAST:event_jBCargActionPerformed

    
    /*Cuando se da un clic en la tabla de registros*/
    private void jTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabMouseClicked
        
         /*Si se hiso doble clic entonces presiona el botón de cargar*/
        if(evt.getClickCount() == 2) 
            jBCarg.doClick();
        
    }//GEN-LAST:event_jTabMouseClicked

    
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

    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

            
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

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCargMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCarg.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCargMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCargMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCargMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCarg.setBackground(colOri);
        
    }//GEN-LAST:event_jBCargMouseExited

    
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
        
        /*Si se presiona la tecla de escape presiona el botón de cargar*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if el botón de enter se presiona entonces*/
        else if(evt.getKeyCode() == KeyEvent.VK_ENTER) 
        {
            /*Si la tabla tiene el foco del teclado en entonces presona el botón de cargar*/
            if(jTab.hasFocus())
                jBCarg.doClick();
        }
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCarg;
    private javax.swing.JButton jBSal;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLTot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
