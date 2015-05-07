/*Paquete*/
package vis;

/*Importaciones*/
import java.awt.GraphicsEnvironment;
import ptovta.Star;
import ptovta.Login;
import static ptovta.Princip.bIdle;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


/*Clase para controlar el visor de la tabla*/
public class VisCots2 extends javax.swing.JFrame 
{
    /*Contador para modificar tabla*/
    private int     iContCellEd;
    
    /*Declara variables originales*/
    private String  sCodOri;
    private String  sCantOri;
    private String  sProdOri;
    private String  sUnidOri;
    private String  sListOri;
    private String  sDescripOri;    
    private String  sPreOri;
    private String  sDescOri;        
    private String  sAlmaOri;    
    private String  sMonOri;        
    private String  sImpoOri;
    private String  sImpueOri;
    private String  sImpoImpueOri;
    private String  sImpoDescOri;
    private String  sSerProdOri;
    private String  sComenSerOri;
    private String  sKitOri;
    private String  sTallOri;
    private String  sColoOri;
    private String  sBackOri;
    private String  sCostOri;
    
    
    
    /*Constructor sin argumentos*/
    public VisCots2() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);

        /*La ventana se mostrará maximizada*/
        setExtendedState(getExtendedState() | VisCots2.MAXIMIZED_BOTH);                        
        
        /*Establece el título de la ventana con la estación, la fecha y hora*/                
        this.setTitle("Visor Tabla, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Cambia el icono de la forma, ya sea el personalizado por el usuario o el de default del sistema*/
        if(new File(new java.io.File("").getAbsolutePath() + "\\Logo.jpg").exists())
        {
            setIconImage(Toolkit.getDefaultToolkit().getImage(new java.io.File("").getAbsolutePath() + "\\Logo.jpg"));
        }
        else
            setIconImage(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla*/        
        jTab.getColumnModel().getColumn(3).setPreferredWidth(130);
        jTab.getColumnModel().getColumn(6).setPreferredWidth(500);
        jTab.getColumnModel().getColumn(13).setPreferredWidth(140);
        jTab.getColumnModel().getColumn(14).setPreferredWidth(140);
        jTab.getColumnModel().getColumn(15).setPreferredWidth(140);
        jTab.getColumnModel().getColumn(16).setPreferredWidth(140);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                

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
                        sCodOri         = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sCantOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sProdOri        = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sUnidOri        = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sListOri        = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sDescripOri     = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sPreOri         = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sDescOri        = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sAlmaOri        = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sMonOri         = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sImpoOri        = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        sImpueOri       = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();
                        sImpoImpueOri   = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();
                        sImpoDescOri    = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();
                        sSerProdOri     = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();
                        sComenSerOri    = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();
                        sKitOri         = jTab.getValueAt(jTab.getSelectedRow(), 17).toString();
                        sTallOri        = jTab.getValueAt(jTab.getSelectedRow(), 18).toString();
                        sColoOri        = jTab.getValueAt(jTab.getSelectedRow(), 19).toString();
                        sBackOri        = jTab.getValueAt(jTab.getSelectedRow(), 20).toString();
                        sCostOri        = jTab.getValueAt(jTab.getSelectedRow(), 21).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sCodOri,        jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sCantOri,       jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sProdOri,       jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sUnidOri,       jTab.getSelectedRow(), 4);
                        jTab.setValueAt(sListOri,       jTab.getSelectedRow(), 5);
                        jTab.setValueAt(sDescripOri,    jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sPreOri,        jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sDescOri,       jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sAlmaOri,       jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sMonOri,        jTab.getSelectedRow(), 10);
                        jTab.setValueAt(sImpoOri,       jTab.getSelectedRow(), 11);
                        jTab.setValueAt(sImpueOri,      jTab.getSelectedRow(), 12);
                        jTab.setValueAt(sImpoImpueOri,  jTab.getSelectedRow(), 13);
                        jTab.setValueAt(sImpoDescOri,   jTab.getSelectedRow(), 14);
                        jTab.setValueAt(sSerProdOri,    jTab.getSelectedRow(), 15);
                        jTab.setValueAt(sComenSerOri,   jTab.getSelectedRow(), 16);
                        jTab.setValueAt(sKitOri,        jTab.getSelectedRow(), 17);
                        jTab.setValueAt(sTallOri,       jTab.getSelectedRow(), 18);
                        jTab.setValueAt(sColoOri,       jTab.getSelectedRow(), 19);
                        jTab.setValueAt(sBackOri,       jTab.getSelectedRow(), 20);
                        jTab.setValueAt(sCostOri,       jTab.getSelectedRow(), 21);
                        
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
        setUndecorated(true);
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

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cotización", "Qty", "Producto", "Unidad", "Lista", "Descripción", "Precio", "Desc.%", "Almacén", "Moneda", "Importe", "%Impuesto", "Total Impuesto", "Importe Descuento", "Serie Producto", "Comentario Serie", "Es Kit", "Talla", "Color", "Back Order", "Costo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
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

    
    /*Cuando se presiona un tecla en la tabla*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando el estado de la ventana cambia*/
    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        
        /*Que este máximizado siempre*/
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.setMaximizedBounds(env.getMaximumWindowBounds());
        this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
        
    }//GEN-LAST:event_formWindowStateChanged

    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape entonces sal de la forma*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            dispose();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
