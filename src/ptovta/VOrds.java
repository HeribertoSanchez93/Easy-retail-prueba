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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para poder ver todas las ordenes de compra*/
public class VOrds extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color  colOri;
    
    /*Declara variables de instancia*/    
    private final String    sProGlo;
    private int             iContFi;

    /*Contador para modificar tabla*/
    private int             iContCellEd;
    
    /*Declara variables originals*/
    private String          sOrdOri;
    private String          sProOri;
    private String          sProvOri;
    private String          sImpoOri;
    private String          sImpueOri;
    private String          sTotOri;
    private String          sRealOri;
    private String          sFecOri;
    private String          sSucOri;
    private String          sCajOri;
    private String          sEstacOri;
    private String          sNomEstacOri;

    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;
    
    
    
    
    /*Consructor sin argumentos*/
    public VOrds(String sPro) 
    {
        /*Inicializa los componentes gráfcos*/
        initComponents();
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Obtiene los datos del otro formulario*/
        sProGlo = sPro;
        
        /*Si el código del proyecto es nulo entonces poner el checkbox como marcado y deshabilitado*/
        if(sProGlo==null)
        {
            jCVerT.setSelected  (true);
            jCVerT.setEnabled   (false);            
        }
        
        /*Inicializa el contador de filas de la caratula*/
        iContFi     = 1;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Ver Órdenes de compra, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el la tabla de ordenes*/
        jTab.grabFocus();
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
                
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Carga todas las ordenes del proyecto*/
        vCargOrds();
        
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
                        sOrdOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sProOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sProvOri       = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sImpoOri       = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sImpueOri      = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sTotOri        = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sRealOri       = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sFecOri        = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sSucOri        = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sCajOri        = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sEstacOri      = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        sNomEstacOri   = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sOrdOri,        jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sProOri,        jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sProvOri,       jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sImpoOri,       jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sImpueOri,      jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sTotOri,        jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sRealOri,       jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sFecOri,        jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sSucOri,        jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sCajOri,        jTab.getSelectedRow(), 10);                        
                        jTab.setValueAt(sEstacOri,      jTab.getSelectedRow(), 11);                        
                        jTab.setValueAt(sNomEstacOri,   jTab.getSelectedRow(), 12);                                                
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public VOrds() */
           
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBDel = new javax.swing.JButton();
        jBAbr = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jCVerT = new javax.swing.JCheckBox();
        jBActua = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
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

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Orden(es) (Ctrl+SUPR)");
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 540, 100, 30));

        jBAbr.setBackground(new java.awt.Color(255, 255, 255));
        jBAbr.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBAbr.setForeground(new java.awt.Color(0, 102, 0));
        jBAbr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/abr.png"))); // NOI18N
        jBAbr.setText("Abrir");
        jBAbr.setToolTipText("Abrir + Orden(es) (Ctrl+A)");
        jBAbr.setNextFocusableComponent(jBDel);
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
        jP1.add(jBAbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 110, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Orden", "Proyecto", "Proveedor", "Importe", "Impuesto", "Total", "Realizo", "Fecha", "Sucursal", "No. Caja", "Usuario", "Nombre Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBAbr);
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
        jScrollPane1.setViewportView(jTab);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 1000, 500));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Órdenes de Compra");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 220, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setNextFocusableComponent(jCVerT);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 540, 100, 30));

        jCVerT.setBackground(new java.awt.Color(255, 255, 255));
        jCVerT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jCVerT.setText("Ver Todas F4");
        jCVerT.setNextFocusableComponent(jTab);
        jCVerT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCVerTActionPerformed(evt);
            }
        });
        jCVerT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCVerTKeyPressed(evt);
            }
        });
        jP1.add(jCVerT, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 540, 210, -1));

        jBActua.setBackground(new java.awt.Color(255, 255, 255));
        jBActua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBActua.setForeground(new java.awt.Color(0, 102, 0));
        jBActua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actualizar.png"))); // NOI18N
        jBActua.setText("Actualizar");
        jBActua.setToolTipText("Actualizar Registros (F5)");
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
        jP1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 540, 110, 30));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(866, 563, 150, 30));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar Todo");
        jBTod.setToolTipText("Marcar Todos los Registros de la Tabla (Alt+T)");
        jBTod.setNextFocusableComponent(jTab);
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, 130, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 1039, Short.MAX_VALUE)
                .addContainerGap())
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

    
    /*Carga todas las ordenes del proyecto*/
    private void vCargOrds()
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
        
        /*Obtiene todas las órdenes de compra de este proyecto de la base de datos*/
        try
        {                  
            sQ = "SELECT ords.CODORD, ords.PROY, provs.NOM, ords.SUBTOT, ords.IMPUE, ords.TOT, ords.ESTAC, ords.FALT, ords.FMOD FROM ords LEFT OUTER JOIN estacs ON estacs.ESTAC = ords.ESTAC LEFT OUTER JOIN provs ON provs.PROV = ords.PROV WHERE proy = '" + sProGlo + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Obtiene los datos*/
                String sImp;
                String sImpue;
                String sTot;                                               
                sImp            = rs.getString("ords.SUBTOT");   
                sImpue          = rs.getString("ords.IMPUE");   
                sTot            = rs.getString("ords.TOT");                                                   
                
                /*Dales formato de moneda a los campos que lo requieren*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sImp);                                
                sImp            = n.format(dCant);
                dCant           = Double.parseDouble(sImpue);                                
                sImpue          = n.format(dCant);
                dCant           = Double.parseDouble(sTot);                                
                sTot            = n.format(dCant);
                
                /*Agregalos en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("ords.CODORD"), rs.getString("ords.PROY"), rs.getString("provs.NOM"), sImp, sImpue, sTot, rs.getString("ords.ESTAC"), rs.getString("ords.FALT"), rs.getString("ords.FMOD"), rs.getString("ords.SUCU"), rs.getString("ords.NOCAJ")};
                te.addRow(nu);
                
                /*Suma uno al contador de filas*/
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
        
    }/*Fin de private void vCargOrds()*/    
        

    /*Cuando se presiona el botón de ver borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si no a seleccionado nada en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)        
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una o varias órdenes.", "Borrar Órdenes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla*/
            jTab.grabFocus();                        
            return;            
        }
                
        /*Preguntar al usuario si esta seguro de querer borrar la órden*/
        Object[] options = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar la(s) orden(es)?", "Borrar Orden(es)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), options, options[0])) == JOptionPane.NO_OPTION)
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
        
        /*Recorre todas las órdenes seleccionadas en la tabla*/
        int iRows[] = jTab.getSelectedRows();
        for(int x = 0; x < iRows.length; x++)
        {
            /*Recorre todas las partidas que tiene la órden*/
            try
            {                  
                sQ = "SELECT codord FROM partords WHERE codord = '" +  iRows[x] + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces borra las partidas*/
                while(rs.next())
                    vDelPartOrd(rs.getString("codord"));                    
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                   
            }
            
            /*Borra de la tabla la órden*/
            DefaultTableModel te = (DefaultTableModel)jTab.getModel();
            te.removeRow(iRows[x]);
            
        }/*Fin de for(int x = 0; x < jTabOrds.getRowCount(); x++)*/   
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;

        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Orden(es) borrada(s) con éxito.", "Borrar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Elimina todas las partidas de esa órden*/
    public static void vDelPartOrd(String sOrd)
    {        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   stmt;        
        String      sQ; 

        /*Borra todas las partidas de la órden específica por el código*/
        try 
        {            
            sQ = "DELETE FROM partords WHERE codord = '" + sOrd + "'";                               
            stmt = con.createStatement();
            stmt.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(VOrds.class.getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                   
            return;                                                                                                                                            
         }
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de public static void vDelPartOrd(String sCodCot)*/
    
    
    /*Cuando se presiona el botón de abrir*/
    private void jBAbrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrActionPerformed

        /*Si no a seleccionado una ordén para abrir entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una orden.", "Abrir Orden", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                        
            return;
        }
        
        /*Si la cantidad de filas seleccionadas es mayor a uno entonces*/
        if(jTab.getSelectedRowCount()>1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona solo una orden.", "Abrir Orden", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();                        
            return;
        }
        
        /*Obtiene el número de la órden*/
        String sOrd = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
        
        /*Muestra el formulario para abrir una órden*/
        AOrd a = new AOrd(sOrd, jTab, jTab.getSelectedRow());
        a.setVisible(true);
        
    }//GEN-LAST:event_jBAbrActionPerformed

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se presiona una tecla en el botón de abrir*/
    private void jBAbrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAbrKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBAbrKeyPressed

    
    /*Cuando se esta cerrando el formulario*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBAbr.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jP1KeyPressed

    
    /*Cuando se presiona una tecla en la tabla de ordenes*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed
       
    
    /*Cuando se presiona una tecla en el checkbox de ver todas las ordenes de compra*/
    private void jCVerTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCVerTKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jCVerTKeyPressed

    
    /*Función para cargar nuevamente todos los elementos en la tabla*/
    private void vCargT()
    {
        /*Limpia toda la lista de órdenes*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();
        te.setRowCount(0);
        
        /*Inicializa el contador de filas*/
        iContFi = 1;
                
        /*Si el checkbox esta marcado entonces trae todas las órdenes de compra, caso contrario obtiene solo las del proyecto*/        
        if(jCVerT.isSelected())
            vCargTOrds();
        else
            vCargOrds();        
    }
    
    
    /*Cuando sucede una acción en el checkbox de ver todas las órdenes de compra*/
    private void jCVerTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCVerTActionPerformed
        
        /*Función para cargar nuevamente todos los elementos en la tabla*/
        vCargT();
        
    }//GEN-LAST:event_jCVerTActionPerformed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        this.dispose();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se da un clic en la tabla de órdenes*/
    private void jTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabMouseClicked
        
         /*Si se hiso doble clic entonces presiona el botón de abrir proyecto*/
        if(evt.getClickCount() == 2) 
            jBAbr.doClick();
        
    }//GEN-LAST:event_jTabMouseClicked

    
    /*Cuando se presiona el botón de actualizar*/
    private void jBActuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActuaActionPerformed

        /*Función para cargar todos los elementos en la tabla*/
        vCargT();

    }//GEN-LAST:event_jBActuaActionPerformed

    
    /*Cuando se presiona una tecla en el botónd de actualizar*/
    private void jBActuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBActuaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBActuaKeyPressed

    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se arrastra el mouse del ratón en la forma*/
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

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBAbrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBAbr.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAbrMouseEntered

    
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
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBAbrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBAbr.setBackground(colOri);
        
    }//GEN-LAST:event_jBAbrMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
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

        
    /*Función para cargar todas las órdenes de compra*/
    private void vCargTOrds()
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
        
        /*Obtiene todas las órdenes de compra de la base de datos*/
        try
        {                  
            sQ = "SELECT ords.CODORD, ords.PROY, provs.NOM, ords.SUBTOT, ords.TOT, ords.IMPUE, ords.ESTAC, ords.FALT, ords.FMOD FROM ords LEFT OUTER JOIN estacs ON estacs.ESTAC = ords.ESTAC LEFT OUTER JOIN provs ON provs.PROV = ords.PROV";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while (rs.next())
            {
                /*Obtiene los datos*/                
                String sImp     = rs.getString("ords.SUBTOT");   
                String sImpue   = rs.getString("ords.IMPUE");   
                String sTot     = rs.getString("ords.TOT");                                                   
                
                /*Dales formato de moneda a los campos que lo requieren*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(sImp);                                
                sImp            = n.format(dCant);
                dCant           = Double.parseDouble(sImpue);                                
                sImpue          = n.format(dCant);
                dCant           = Double.parseDouble(sTot);                                
                sTot            = n.format(dCant);
                
                /*Agregalos en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("ords.CODORD"), rs.getString("ords.PROY"), rs.getString("provs.NOM"), sImp, sImpue, sTot, rs.getString("ords.ESTAC"), rs.getString("ords.FALT"), rs.getString("ords.FMOD")};
                te.addRow(nu);
                
                /*Suma uno al contador de filas*/
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
        
    }/*Fin de private void vCargTOrds()*/
    
        
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona CTRL + A entonces presiona el botón de abrir*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_A)
            jBAbr.doClick();
        /*Si se presiona F5 presiona el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();
        /*Se presionó F4*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
        {
            /*Limpia toda la lista de órdenes*/
            DefaultTableModel te = (DefaultTableModel)jTab.getModel();
            te.setRowCount(0);

            /*Inicializa el contador de filas*/
            iContFi = 1;

            /*Si el checkbox esta marcado entonces trae todas las órdenes de compra, caso contrario obtiene solo las del proyecto*/        
            if(jCVerT.isSelected())
            {
                /*Desmarcalo y carga solo las órdenes del proyecto*/
                jCVerT.setSelected(false);
                vCargOrds();
            }
            else
            {
                /*Marcalo y carga todas las órdenes*/
                jCVerT.setSelected(true);
                vCargTOrds();                
            }
        }
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
            jBTod.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbr;
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JCheckBox jCVerT;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class NuevaEmpresa extends javax.swing.JFrame */
