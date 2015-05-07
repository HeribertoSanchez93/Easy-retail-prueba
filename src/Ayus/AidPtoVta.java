//Paquete
package Ayus;

//Importaciones
import java.awt.GraphicsEnvironment;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ptovta.Login;
import ptovta.Star;




/*Clase para controlar el visor de la tabla*/
public class AidPtoVta extends javax.swing.JFrame 
{
    /*Contador para modificar tabla*/
    private int                     iContCellEd;
    
    /*Declara las variables originales*/
    private String                  sAcesOri;
    private String                  sFuncOri;
    
    
    /*Constructor sin argumentos*/
    public AidPtoVta() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Deshabilita que no se mueven las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);

        /*La ventana se mostrará maximizada*/
        setExtendedState(getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);                        
        
        /*Establece el título de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Ayuda, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla*/        
        jTab.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(400);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(1000);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
                            
        /*Agrega las ayudas en la tabla*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
        Object nu[]             =               {"1", "<F1>", "Ayuda del punto de venta"};
        te.addRow(nu);
        nu                      = new Object[]  {"2", "<F2>", "Ver mensajes."};
        te.addRow(nu);        
        nu                      = new Object[]  {"3", "<F3>", "Colocar el cursor en el campo de la empresa."};        
        te.addRow(nu);        
        nu                      = new Object[]  {"4", "<F4>", "Nueva empresa."};        
        te.addRow(nu);                
        nu                      = new Object[]  {"5", "<F6>", "Logearse."};
        te.addRow(nu);
        nu                      = new Object[]  {"6", "<F9>", "Cobrar."};
        te.addRow(nu);                        
        nu                      = new Object[]  {"7", "<F11>", "Opciones del punto de venta."};
        te.addRow(nu);                
        nu                      = new Object[]  {"8", "<F12>", "Chat corporativo."};
        te.addRow(nu);        
        nu                      = new Object[]  {"9", "<CTRL + N>/<ENTER>", "Agregar nuevo producto."};
        te.addRow(nu);                
        nu                      = new Object[]  {"10", "<FLECHA ABAJO>", "Mostrar Catálogo de Productos."};
        te.addRow(nu);                        
        nu                      = new Object[]  {"11", "<ALT + T>", "Agregar nuevo producto por talla y color."};
        te.addRow(nu);                
        nu                      = new Object[]  {"12", "<ALT + L>", "Agregar nuevo producto por lote y pedimento."};
        te.addRow(nu);                
        nu                      = new Object[]  {"13", "<ALT + S>", "Agregar serie al producto."};
//        te.addRow(nu);                
//        nu                      = new Object[]  {"14", "<ALT + P>", "Perdida de boleto."};
        te.addRow(nu);                
        
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
                        sAcesOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sFuncOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sAcesOri,       jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sFuncOri,       jTab.getSelectedRow(), 2);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public VisEmps() */
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jTab.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jTab.setForeground(new java.awt.Color(0, 51, 102));
        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Acceso", "Función"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setRowHeight(32);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed
   
                    
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

    
    /*Cuando se presiona una tecla en la tabla*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando el estado de la ventana cambia*/
    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        
        /*Que este máximizado siempre*/
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.setMaximizedBounds(env.getMaximumWindowBounds());
        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        
    }//GEN-LAST:event_formWindowStateChanged
    
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
        {
            /*Llama al recolector de basura y sal de la forma*/
            System.gc();
            dispose();        
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
