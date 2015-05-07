//cambio alan
//Paquete
package cats;

//Importaciones
import java.awt.Cursor;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ptovta.Login;
import ptovta.Star;




/*Catálogo de tallas y colores*/
public class ProdMarcMod extends javax.swing.JFrame 
{
    /*Declara variables originales*/
    private String          sMarcaOri;
    private String          sDescripMarcOri;
    private String          sModeOri;
    private String          sDescripModOri;
    
    /*Declara contadors globales*/
    private int             iContCellEd;
    private int             iContFi;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;

    /*Contiene la referencia del otro formulario para poder leer variables*/
    private final Prods     prods;
    
    /*Variable que contiene el borde actual*/
    private javax.swing.border.Border bBordOri;
    

    
    
    public ProdMarcMod(Prods pro) 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Obtiene del otro formulario la referencia*/
        prods   = pro;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Productos, marcas y modelos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Establece el tamaño de las columnas de la tabla*/
        jTab.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(400);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(400);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Inicializa el contador de filas en uno*/
        iContFi      = 1;
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;                
        
        /*Crea el listener para la tabla, para cuando se modifique una celda*/
        PropertyChangeListener pr = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Obtén la propiedad que a sucedio en el control*/
                String pro = event.getPropertyName();
                
                /*Si el evento fue por entrar en una celda del tabla*/
                if("tableCellEditor".equals(pro)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Si no hay selección entonces que regresa*/
                        if(jTab.getSelectedRow()==-1)
                            return;
                        /*Obtén algunos datos originales de la fila*/ 
                        sMarcaOri            = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sDescripMarcOri      = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sModeOri             = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sDescripModOri       = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();  
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd         = 1;
                            
                        /*Coloca los valores origianales*/
                        jTab.setValueAt(sMarcaOri,          jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sDescripMarcOri,    jTab.getSelectedRow(), 2);
                        jTab.setValueAt(sModeOri,           jTab.getSelectedRow(), 3);
                        jTab.setValueAt(sDescripModOri,     jTab.getSelectedRow(), 4);
                    }
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla de marcs*/
        jTab.addPropertyChangeListener(pr);
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Carga todas las marcas en el combo
        vCargMarc(con);
        
        //Se pone en no modificable el modelo
        jComMod.setEnabled(false);
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Obtén todas las las relaciones de la base de datos*/
        vCarg();
            
    }/*Fin de public Marcs() */

    /*Obtén todas las productos, marcas y modelos de la base de datos*/
    private void vCarg()
    {    
        /*Si no hay datos entonces regresa*/
        if(prods.sCompaMarcMod==null)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Reinicia el contador de filas*/
        iContFi = 1;            
            
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);                   

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;                                
        
        /*Recorre todo el arreglo de string para obtener las compatibilidades*/
        for(String sProd: prods.sCompaMarcMod)
        {            
            //Tokeniza para quitar la raíz común
            java.util.StringTokenizer stk = new java.util.StringTokenizer(sProd, "|");

            //Quita la primera raíz común
            String sMarca = stk.nextToken();
            String sModelo = stk.nextToken();
                            
            /*Obtiene la descripción del producto de la base de datos*/
            try
            {
                sQ ="SELECT a.marc AS Marca, descripcion_marca,a.model AS Modelo,model.descrip AS descripcion_model FROM"+
                    " (SELECT marc,model,marcs.descrip AS descripcion_marca FROM terMarcamodelo LEFT OUTER JOIN marcs ON terMarcamodelo.marc=marcs.cod)a"+
                    " LEFT OUTER JOIN model on a.model=model.cod WHERE a.marc='" + sMarca + "' AND a.model='" + sModelo + "' GROUP BY Marc,Model";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                /*Si hay datos entonces*/
                while(rs.next())
                {
                    /*Agregalo a la tabla*/
                    Object nu[]         = {iContFi, rs.getString("Marca"), rs.getString("descripcion_marca"), rs.getString("Modelo"), rs.getString("descripcion_model")};
                    dm.addRow(nu);

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
            
        }/*Fin de for(String sProd: sProds)*/                    
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vCarg()*/
            
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jComMod = new javax.swing.JComboBox();
        jComMarc = new javax.swing.JComboBox();
        jTDescripMarc = new javax.swing.JTextField();
        jTDescripMod = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jBNew = new javax.swing.JButton();
        jBDel = new javax.swing.JButton();
        jBGuar = new javax.swing.JButton();

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
        jBSal.setNextFocusableComponent(jBTod);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 430, 120, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Productos, marca y modelos");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 370, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Marca", "Descripción marca", "Modelo", "Descripción modelo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBGuar);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 780, 290));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 460, 120, 20));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar todo");
        jBTod.setToolTipText("Marcar Todos los Registros de la Tabla (Alt+T)");
        jBTod.setNextFocusableComponent(jComMarc);
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 122, 130, 18));

        jLabel14.setText("Modelo:");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 60, -1));

        jComMod.setNextFocusableComponent(jBNew);
        jComMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComModActionPerformed(evt);
            }
        });
        jComMod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComModKeyPressed(evt);
            }
        });
        jP1.add(jComMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 90, 20));

        jComMarc.setNextFocusableComponent(jTDescripMarc);
        jComMarc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComMarcFocusLost(evt);
            }
        });
        jComMarc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComMarcActionPerformed(evt);
            }
        });
        jComMarc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComMarcKeyPressed(evt);
            }
        });
        jP1.add(jComMarc, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 90, 20));

        jTDescripMarc.setEditable(false);
        jTDescripMarc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripMarc.setNextFocusableComponent(jComMod);
        jTDescripMarc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripMarcFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripMarcFocusLost(evt);
            }
        });
        jTDescripMarc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripMarcKeyPressed(evt);
            }
        });
        jP1.add(jTDescripMarc, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 390, 20));

        jTDescripMod.setEditable(false);
        jTDescripMod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripMod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripModFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripModFocusLost(evt);
            }
        });
        jTDescripMod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripModKeyPressed(evt);
            }
        });
        jP1.add(jTDescripMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 390, 20));

        jLabel17.setText("Marca:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 60, -1));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nuevo Almacén (Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jBDel);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 90, 20));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Almacen(es) (Ctrl+SUPR)");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jTab);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 90, 20));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar");
        jBGuar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBGuar.setNextFocusableComponent(jBSal);
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
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 430, 120, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        
        /*Llama al recolector de basura y cierra la forma*/
        System.gc();                    
        this.dispose();                    
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

                   
    /*Cuando se presiona una  tecla en la tabla de marcs*/
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

    
    /*Cuando se mueve el ratón en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved
        
    
          
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
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

        
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

                
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando sucede una acción en el combo de los modelos*/
    private void jComModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComModActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComMod.getSelectedItem()==null)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Obtiene la descripción de la talla en base a su código*/
        try
        {
            sQ = "SELECT descrip FROM model WHERE cod = '" + jComMod.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTDescripMod.setText(rs.getString("descrip"));
            else
                jTDescripMod.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }

        /*Coloca al principio del control el caret*/
        jTDescripMod.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComModActionPerformed

    
    /*Cuando se presiona una tecla en el combo de los colores*/
    private void jComModKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComModKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComModKeyPressed

    
    /*Cuando sucede una acción en el combobox de marcas*/
    private void jComMarcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComMarcActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComMarc.getSelectedItem()==null)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Obtiene la descripción de la talla en base a su código*/
        try
        {
            sQ = "SELECT descrip FROM marcs WHERE cod = '" + jComMarc.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTDescripMarc.setText(rs.getString("descrip"));
            else
                jTDescripMarc.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }

        /*Coloca al principio del control el caret*/
        jTDescripMarc.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);
       
    }//GEN-LAST:event_jComMarcActionPerformed

    
    /*Cuando se presiona una tecla en el combo de tallas*/
    private void jComMarcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComMarcKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComMarcKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción de la Marca*/
    private void jTDescripMarcFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripMarcFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripMarc.setSelectionStart(0);jTDescripMarc.setSelectionEnd(jTDescripMarc.getText().length());

    }//GEN-LAST:event_jTDescripMarcFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción de la marca*/
    private void jTDescripMarcFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripMarcFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDescripMarc.setCaretPosition(0);

    }//GEN-LAST:event_jTDescripMarcFocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción de la talla*/
    private void jTDescripMarcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripMarcKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripMarcKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del color*/
    private void jTDescripModFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripModFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripMod.setSelectionStart(0);jTDescripMod.setSelectionEnd(jTDescripMod.getText().length());

    }//GEN-LAST:event_jTDescripModFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del color*/
    private void jTDescripModFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripModFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTDescripMod.setCaretPosition(0);

    }//GEN-LAST:event_jTDescripModFocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción del color*/
    private void jTDescripModKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripModKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripModKeyPressed

    
    /*Cuando el mouse entra en el botón de nuevo*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered

        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);

    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mosue sale del botón de nuevo*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);

    }//GEN-LAST:event_jBNewMouseExited

    //pendiente alan
    /*Cuando se presiona el botón de nueva partida*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed

        
        /*Si por lo menos no se selecciono una talla o color entonces*/
        if(jTDescripMarc.getText().compareTo("")==0 || jTDescripMod.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/
            jComMarc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona tanto marca como modelo.", "Marcas y modelos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el campo de edición y regresa*/
            jComMarc.grabFocus();
            return;
        }
        /*Recorre toda la tabla*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Si el Modelo ya esta en la tabla entonces*/
            if(jTab.getValueAt(x, 1).toString().compareTo(jComMarc.getSelectedItem().toString())==0 && jTab.getValueAt(x, 3).toString().compareTo(jComMod.getSelectedItem().toString())==0)
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "Ya existe esta compatibilidad en la lista.", "Registro existente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Pon el foco del teclado en el control del código*/
                jComMarc.grabFocus();
                
                /*Coloca el borde rojo y regresa*/                               
                jComMarc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                return;
            }
        }
                 
        /*Pregunta al usuario si están bien los datos entonces regresa*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres agregar esta compatibilidad?", "Agregar compatibilidad", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;

        /*Agrega el registro en la tabla*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFi,jComMarc.getSelectedItem().toString(), jTDescripMarc.getText(), jComMod.getSelectedItem().toString(), jTDescripMod.getText()};
        te.addRow(nu);

        /*Aumenta en uno el contador de las filas*/
        ++iContFi;

        /*Pon el foco del teclado en el campo del código del producto*/
        jComMarc.grabFocus();

    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se presiona una tecla en el botón de nueva partida*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando el mouse entra en el botón de borrar partida*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered

        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);

    }//GEN-LAST:event_jBDelMouseEntered

    
    /*Cuando el mouse sale del botón de borrar partida*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(Star.colOri);

    }//GEN-LAST:event_jBDelMouseExited

    //pendiente alan
    /*Cuando se presiona el botón de borrar partida*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed

        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado una compatibilidad de la lista para borrar.", "Borrar Compatibilidad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();            
            return;            
        }
        
        /*Preguntar al usuario si esta seguro de querer borrar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere borrar la(s) compatibilidad(es)?", "Borrar Compatibilidad(es)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {                        
            /*Borralo de la tabla*/            
            tm.removeRow(iSel[x]);
            
            /*Resta en uno el contador de filas el contador de filas en uno*/
            --iContFi;            
        }
        
    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Cuando se presiona una tecla en el botón de borrar partida*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se pierde el foco del teclado en el combo de las marcas*/
    private void jComMarcFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComMarcFocusLost
        
        //Si tiene datos entonces
        if(jComMarc.getSelectedItem().toString().compareTo("")!=0)
        {
            //Coloca le borde negro
            jComMarc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
            
            //Abre la base de datos                             
            Connection  con = Star.conAbrBas(true, false);

            //Si hubo error entonces regresa
            if(con==null)
                return;

            //Habilita el combo de modelos y cargalos
            jComMod.setEnabled(true);
            vCargMod(con);
                
            //Cierra la base de datos
            Star.iCierrBas(con);                              
        }
        else
        {
            jComMod.setEnabled(false);
            /*Borra los items en el combobox*/
            jComMod.removeAllItems();

            /*Agrega cadena vacia*/
            jComMod.addItem("");
        }              
        
    }//GEN-LAST:event_jComMarcFocusLost

    
    //Cuando el mouse entra en el botón de guardar    
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered

        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuarMouseEntered

    
    //Cuando el mouse sale del botón de guardar
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    //Cuando se presiona el botón de guardar
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        //Reinicia el arreglo
        prods.sCompaMarcMod = null;
        
        //Reinicia el arreglo de Modelos
        if(jTab.getRowCount()!=0)            
            prods.sCompaMarcMod = new String[jTab.getRowCount()];
        
        /*Recorre toda la tabla de asociaciones y ve creando nuevamente el arreglo*/
        for(int x = 0; x < jTab.getRowCount(); x++)
            prods.sCompaMarcMod[x] = jTab.getValueAt(x, 1).toString()+"|"+jTab.getValueAt(x, 3).toString();
        
        /*Llama al recolector de basura y cierra la forma*/
        System.gc();
        this.dispose();
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    //Cuando se presiona una tecla en el botón de guardar
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBGuarKeyPressed
            
    
    /*Carga las marcas en el combo*/        
    private void vCargMarc(Connection con)
    {
        /*Borra los items en el combobox*/
        jComMarc.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComMarc.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene las marcas y cargalas en el combo*/        
        try
        {
            sQ = "SELECT marc FROM terMarcamodelo GROUP BY marc";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalas en el conrol*/
            while(rs.next())
                jComMarc.addItem(rs.getString("marc"));            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);           
        }    
        
    }/*Fin de private void vCargMarc(Connection con)*/
    
    
    /*Carga todos los Modelos en el combo*/        
    private void vCargMod(Connection con)
    {
        /*Borra los items en el combobox*/
        jComMod.removeAllItems();
        
        /*Agrega cadena vacia*/
        jComMod.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene todos los Modelos de la base de datos*/        
        try
        {
            sQ = "SELECT model FROM terMarcamodelo WHERE marc= '" + jComMarc.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalas en el conrol*/
            while(rs.next())
                jComMod.addItem(rs.getString("model"));
                
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                         
        }    
        
    }/*Fin de private void vCargMod(Connection con)*/
    
    
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
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();      
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBGuar;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTod;
    private javax.swing.JComboBox jComMarc;
    private javax.swing.JComboBox jComMod;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTDescripMarc;
    private javax.swing.JTextField jTDescripMod;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
