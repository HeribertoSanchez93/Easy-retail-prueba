//Paquete
package ptovta;

//Importaciones
import java.text.NumberFormat;
import java.util.Locale;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.awt.Cursor;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para ver las listas de precio y seleccionar un precio*/
public class BuscPre extends javax.swing.JFrame
{
    /*Contiene el color original del botón*/
    private java.awt.Color              colOri;
    
    /*Declara variables de instancia*/    
    private final int                   iEn;
    private final String                sBusc;
    private final JTextField            jTFCamp;
    
    /*Contador para modificar tabla*/
    private int                         iContCellEd;
    
    /*Declara variables originales*/
    private String                      sCodOri;
    private String                      sListOri;
    private String                      sPreOri;    
        
    /*Contiene el código del producto*/
    private String                      sProd;
    
    
    
    
    /*Constructor sin argumentos*/
    public BuscPre(JFrame jFram, String sBusc1, int iE, JTextField jCamp, String sPro) 
    {           
        /*Inicaliza los componentes gráficos*/
        initComponents();

        //Que sea modal la forma
        this.setAlwaysOnTop(true);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBCarg);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Que las comlumnas de la tabla no se muevan*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Recibe los parámetros del otro formualrio*/
        iEn     = iE;                
        sBusc   = sBusc1;                
        jTFCamp = jCamp;
        sProd   = sPro;        
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Lista de precios, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
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
        
        /*Establece el tamaño de las columnas de la tabla*/        
        //jTab.getColumnModel().getColumn(1).setPreferredWidth(180);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Función para saber de que base de datos cargar los datos*/
        vCargReg(sBusc);                        
        
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
                int row                 = jTab.getSelectedRow();
                if(row==-1)
                    return;
                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pr)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sCodOri         = jTab.getValueAt(row, 1).toString();
                        sListOri        = jTab.getValueAt(row, 2).toString();
                        sPreOri         = jTab.getValueAt(row, 3).toString();                        
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sCodOri,        row, 1);                        
                        jTab.setValueAt(sListOri,       row, 2);                        
                        jTab.setValueAt(sPreOri,        row, 3);                                                           
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public Buscador() */

    
    /*Función para saber de que base de datos cargar los datos*/
    private void vCargReg(String sBusc)
    {
        /*Switch entre los diferentes conceptos de busqueda*/
        switch(iEn)
        {
            /*Buscar en listas de precio*/
            case 1:
                             
                /*Obtiene todos los precios para el producto por almacán*/        
                Carg(sBusc);                    
                break;                
        }                
    }
                        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBCarg = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
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
        jP1.add(jBCarg, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 130, 30));

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 130, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lista", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBCarg);
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 240));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 220, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
   /*Obtiene todos los registros y cargalos en la tabla*/
    private void Carg(String sBusc)
    {        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel tm = (DefaultTableModel)jTab.getModel();                    
        
        /*Remplaza los espacios en blanco por el carácter comodín de mysql*/        
        sBusc               = sBusc.replace(" ", "%");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los precios del producto en base a su código y almacán*/
        try
        {
            sQ = "SELECT prelist1, prelist2, prelist3, prelist4, prelist5, prelist6, prelist7, prelist8, prelist9, prelist10 FROM prods WHERE prod = '" + sProd + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Dale formato de moneda al precio 1*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                double dCant    = Double.parseDouble(rs.getString("prelist1"));                
                String sP1      = n.format(dCant);

                /*Agregalo a la tabla el precio de lista 1*/
                Object nu[]     = {"1", sP1};
                tm.addRow(nu);                               
                
                /*Dale formato de moneda al precio 2*/
                dCant           = Double.parseDouble(rs.getString("prelist2"));                
                String sP2      = n.format(dCant);
                
                /*Agregalo a la tabla el precio de lista 2*/
                nu[0]           = "2";
                nu[1]           = sP2;
                tm.addRow(nu);  
                
                /*Dale formato de moneda al precio 3*/
                dCant           = Double.parseDouble(rs.getString("prelist3"));                
                String sP3      = n.format(dCant);
                
                /*Agregalo a la tabla el precio de lista 3*/
                nu[0]           = "3";
                nu[1]           = sP3;
                tm.addRow(nu);  
                
                /*Dale formato de moneda al precio 4*/
                dCant           = Double.parseDouble(rs.getString("prelist4"));                
                String sP4      = n.format(dCant);
                              
                /*Agregalo a la tabla el precio de lista 4*/
                nu[0]           = "4";
                nu[1]           = sP4;
                tm.addRow(nu);  
                
                /*Dale formato de moneda al precio 5*/
                dCant           = Double.parseDouble(rs.getString("prelist5"));                
                String sP5      = n.format(dCant);
                
                /*Agregalo a la tabla el precio de lista 5*/
                nu[0]           = "5";
                nu[1]           = sP5;
                tm.addRow(nu);  
                
                /*Dale formato de moneda al precio 6*/
                dCant           = Double.parseDouble(rs.getString("prelist6"));                
                String sP6      = n.format(dCant);
                
                /*Agregalo a la tabla el precio de lista 6*/
                nu[0]           = "6";
                nu[1]           = sP6;
                tm.addRow(nu);  
                
                /*Dale formato de moneda al precio 7*/
                dCant           = Double.parseDouble(rs.getString("prelist7"));                
                String sP7      = n.format(dCant);
                
                /*Agregalo a la tabla el precio de lista 7*/
                nu[0]           = "7";
                nu[1]           = sP7;
                tm.addRow(nu);  
                                
                /*Dale formato de moneda al precio 8*/
                dCant           = Double.parseDouble(rs.getString("prelist8"));                
                String sP8      = n.format(dCant);
                
                /*Agregalo a la tabla el precio de lista 8*/
                nu[0]           = "8";
                nu[1]           = sP8;
                tm.addRow(nu);  
                
                /*Dale formato de moneda al precio 9*/
                dCant           = Double.parseDouble(rs.getString("prelist9"));                
                String sP9      = n.format(dCant);
                
                /*Agregalo a la tabla el precio de lista 9*/
                nu[0]           = "9";
                nu[1]           = sP9;
                tm.addRow(nu);  
                
                /*Dale formato de moneda al precio 10*/
                dCant           = Double.parseDouble(rs.getString("prelist10"));                
                String sP10     = n.format(dCant);
                
                /*Agregalo a la tabla el precio de lista 10*/
                nu[0]           = "10";
                nu[1]           = sP10;
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
        
    }/*Fin de private void Carg()*/       
    
        
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

        /*Obtén el precio seleccionado*/
        int row             = jTab.getSelectedRow();
        String sPre         = jTab.getValueAt(row, 0).toString();        
        
        /*Coloca el precio en el otro formulario*/        
        jTFCamp.setText     (sPre);
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Sal del formulario*/
        this.dispose();        
        
    }//GEN-LAST:event_jBCargActionPerformed

    
    /*Cuando se hace un clic en la tabla de reigstros*/
    private void jTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabMouseClicked
        
         /*Si se hiso doble clic entonces presiona el botón de cargar*/
        if(evt.getClickCount() == 2) 
            jBCarg.doClick();
        
    }//GEN-LAST:event_jTabMouseClicked

    
    /*Cuando se arrastra el mouse en el diálogo*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve el mouse en el diálogo*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
       
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se mueve la rueda del botón en la forma*/
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
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
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
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
