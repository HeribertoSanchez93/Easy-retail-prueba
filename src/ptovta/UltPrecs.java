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
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para ver los últimos precios que se le dió a la empresa y por producto*/
public class UltPrecs extends javax.swing.JFrame 
{
    /*Declara variables de instancia*/    
    private int             iContFi;
    private final String    sCodEP;
    private final String    sProd;
        
    /*Contador para modificar tabla*/
    private int              iContCellEd;
    
    /*Declara variables originales*/
    private String           sFolOri;
    private String           sSerOri;
    private String           sTipDocOri;
    private String           sPreOri;
    private String           sFechOri;
    private String           sProvOri;
    private String           sNomOri;
    
    
    /*Constructor*/
    public UltPrecs(String sCodEm, String sPro, String sTip, boolean bTod) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);

        /*Recibe los parámetros del otro formulario*/
        sCodEP = sCodEm;
        sProd   = sPro;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Ultimos precios/costos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en 1*/
        iContFi      = 1;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla*/        
        jTab.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(5).setPreferredWidth(130);
        jTab.getColumnModel().getColumn(6).setPreferredWidth(130);
        jTab.getColumnModel().getColumn(7).setPreferredWidth(300);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
                
        /*Obtiene todos los precios del cliente/proveedor y producto y cargalos en la tabla*/
        vCargPre(sTip, bTod);
                    
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
                                
                /*Si no hay selecciòn entonces regresa*/                
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pr)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sFolOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sSerOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sTipDocOri     = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sPreOri        = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sFechOri       = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sProvOri       = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sNomOri        = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sFolOri,       jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sSerOri,       jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sTipDocOri,    jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sPreOri,       jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sFechOri,      jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sProvOri,      jTab.getSelectedRow(), 6);
                        jTab.setValueAt(sNomOri,       jTab.getSelectedRow(), 7);
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public UtlPrecs() */

        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
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
        jBSal.setName(""); // NOI18N
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 120, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Folio", "Serie", "Tipo Documento", "Precio", "Fecha", "C/P", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
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
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 310));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 290, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(496, 290, 120, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todos los últimos precios/costos de la base de datos y cargalos en la tabla*/
    private void vCargPre(String sTip, boolean bTod)
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();                    

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Si es de ventas entonces*/
        if(sTip.compareTo("fac")==0)
        {
            /*Crea la consulta dependiendo si tiene que traer los últimos precios de todos los clientes/proveedores o solo del cliente/proveedor específicado*/
            if(bTod)
                sQ = "SELECT vtas.CODEMP, emps.NOM, vtas.NOREFER, vtas.NOSER, vtas.TIPDOC, partvta.PRE, vtas.FALT  FROM partvta LEFT OUTER JOIN vtas ON vtas.VTA = partvta.VTA LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP) = vtas.CODEMP WHERE prod = '" + sProd + "' ORDER BY vtas.VTA DESC LIMIT 1000";
            else
                sQ = "SELECT vtas.CODEMP, emps.NOM, vtas.NOREFER, vtas.NOSER, vtas.TIPDOC, partvta.PRE, vtas.FALT  FROM partvta LEFT OUTER JOIN vtas ON vtas.VTA = partvta.VTA LEFT OUTER JOIN emps ON CONCAT_WS('', emps.SER, emps.CODEMP) = vtas.CODEMP WHERE prod = '" + sProd + "' AND vtas.CODEMP = '" + sCodEP + "' ORDER BY vtas.VTA DESC LIMIT 1000";
            
            /*Trae los últimos 100 precios de la base de datos y cargalos en la tabla*/
            try
            {                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                while(rs.next())
                {                
                    /*Obtiene el precio*/
                    String sPre = rs.getString("PRE");

                    /*Dale formato de moneda al precio*/                    
                    NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                    double dCant    = Double.parseDouble(sPre);                
                    sPre            = n.format(dCant);

                    /*Agregalo a la tabla el registro*/
                    Object nu[]         = {iContFi, rs.getString("vtas.NOREFER"), rs.getString("vtas.NOSER"), rs.getString("vtas.TIPDOC"), sPre, rs.getString("vtas.FALT"), rs.getString("codemp"), rs.getString("nom")};
                    te.addRow(nu);

                    /*Aumentar en uno el contador de pesos*/
                    ++iContFi;                
                }
            }
            catch(SQLException expnSQL)
            {    
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                         
            }            
            
        }/*Fin de if(sTip.compareTo("fac")==0)*/                        
        /*Else es compras entocnes*/
        else
        {
            /*Crea la consulta dependiendo si tiene que traer los últimos precios de todos los clientes/proveedores o solo del cliente/proveedor específicado*/
            if(bTod)
                sQ = "SELECT comprs.PROV, provs.NOM, comprs.NODOC, comprs.NOSER, 'COMP' AS tipdoc, partcomprs.COST, comprs.FDOC FROM partcomprs LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = comprs.PROV WHERE prod = '" + sProd + "' ORDER BY comprs.ID_ID DESC  LIMIT 1000";
            else
                sQ = "SELECT comprs.PROV, provs.NOM, comprs.NODOC, comprs.NOSER, 'COMP' AS tipdoc, partcomprs.COST, comprs.FDOC FROM partcomprs LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER, provs.PROV) = comprs.PROV WHERE prod = '" + sProd + "' AND comprs.PROV = '" + sCodEP + "' ORDER BY comprs.ID_ID DESC  LIMIT 1000";
            
            /*Trae los últimos 100 precios de la base de datos y cargalos en la tabla*/
            try
            {                
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos*/
                while(rs.next())
                {                
                    /*Obtiene el precio*/
                    String sPre = rs.getString("COST");

                    /*Dale formato de moneda al costo*/                    
                    NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                    double dCant    = Double.parseDouble(sPre);                
                    sPre            = n.format(dCant);

                    /*Agregalo a la tabla el registro*/
                    Object nu[]         = {iContFi, rs.getString("comprs.NODOC"), rs.getString("comprs.NOSER"), rs.getString("tipdoc"), sPre, rs.getString("comprs.FDOC"), rs.getString("prov"), rs.getString("nom")};
                    te.addRow(nu);

                    /*Aumentar en uno el contador de pesos*/
                    ++iContFi;                
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                                        
            }            
            
        }/*Fin de else*/
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vCargPre()*/
    
         
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
        
        /*Cierra el formulario*/
        dispose();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

       
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

    
    /*Cuando se presiona una  tecla en la tabla de ultimos precios*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTabKeyPressed

    
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
        
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
