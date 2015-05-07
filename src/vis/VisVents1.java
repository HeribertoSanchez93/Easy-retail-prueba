/*Paquete*/
package vis;

/*Importaciones*/
import ptovta.Star;
import java.awt.GraphicsEnvironment;
import ptovta.Login;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import static ptovta.Princip.bIdle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


/*Clase para controlar el visor de la tabla*/
public class VisVents1 extends javax.swing.JFrame 
{
    /*Contador para modificar tabla*/
    private int     iContCellEd;
    
    /*Declara variables originales*/
    private String  sVtaOri;
    private String  sFolOri;
    private String  sNoSerOri;
    private String  sEmpOri;
    private String  sImpoOri;
    private String  sTotDescOri;
    private String  sTotCostOri;
    private String  sFCreaOri;
    private String  sFDocOri;
    private String  sUltModOri;
    private String  sVenciOri;
    private String  sNotCredOri;
    private String  sNotCredPOri;
    private String  sEstadOri;
    private String  sEstacOri;
    private String  sMotivOri;
    private String  sTipDocOri;
    private String  sObservOri;
    private String  sSucOri;
    private String  sCajOri;
    private String  sNomEstacOri;
    private String  sTimOri;
    private String  sCodCotOri;
    private String  sVtaDevOri;
    private String  sFolFiscOri;
    
    
    
    
    /*Constructor sin argumentos*/
    public VisVents1() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Esconde la columna del id*/
        jTab.getColumnModel().getColumn(25).setMinWidth(0);
        jTab.getColumnModel().getColumn(25).setMaxWidth(0);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);

        /*La ventana se mostrará maximizada*/
        setExtendedState(getExtendedState() | VisVents1.MAXIMIZED_BOTH);                        
        
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
        
        /*Establece el tamaño de las columnas de la tabla de ventas*/
        jTab.getColumnModel().getColumn(0).setPreferredWidth(37);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(48);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(48);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(400);
        jTab.getColumnModel().getColumn(6).setPreferredWidth(130);
        jTab.getColumnModel().getColumn(7).setPreferredWidth(130);
        jTab.getColumnModel().getColumn(8).setPreferredWidth(130);
        jTab.getColumnModel().getColumn(9).setPreferredWidth(130);
        jTab.getColumnModel().getColumn(10).setPreferredWidth(130);
        jTab.getColumnModel().getColumn(11).setPreferredWidth(130);
        jTab.getColumnModel().getColumn(12).setPreferredWidth(140);
        jTab.getColumnModel().getColumn(13).setPreferredWidth(130);
        jTab.getColumnModel().getColumn(16).setPreferredWidth(130);
        jTab.getColumnModel().getColumn(17).setPreferredWidth(220);
        jTab.getColumnModel().getColumn(120).setPreferredWidth(160);        
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
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
                        sVtaOri         = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sFolOri         = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sNoSerOri       = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sEmpOri         = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sImpoOri        = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sTotDescOri     = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sTotCostOri     = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sFCreaOri       = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sFDocOri        = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sUltModOri      = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sVenciOri       = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                        sNotCredOri     = jTab.getValueAt(jTab.getSelectedRow(), 12).toString();
                        sNotCredPOri    = jTab.getValueAt(jTab.getSelectedRow(), 13).toString();
                        sEstadOri       = jTab.getValueAt(jTab.getSelectedRow(), 14).toString();
                        sEstacOri       = jTab.getValueAt(jTab.getSelectedRow(), 15).toString();
                        sMotivOri       = jTab.getValueAt(jTab.getSelectedRow(), 16).toString();
                        sTipDocOri      = jTab.getValueAt(jTab.getSelectedRow(), 17).toString();
                        sObservOri      = jTab.getValueAt(jTab.getSelectedRow(), 18).toString();
                        sSucOri         = jTab.getValueAt(jTab.getSelectedRow(), 19).toString();
                        sCajOri         = jTab.getValueAt(jTab.getSelectedRow(), 20).toString();
                        sNomEstacOri    = jTab.getValueAt(jTab.getSelectedRow(), 21).toString();
                        sTimOri         = jTab.getValueAt(jTab.getSelectedRow(), 22).toString();
                        sCodCotOri      = jTab.getValueAt(jTab.getSelectedRow(), 23).toString();
                        sVtaDevOri      = jTab.getValueAt(jTab.getSelectedRow(), 25).toString();
                        sFolFiscOri     = jTab.getValueAt(jTab.getSelectedRow(), 26).toString();
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sVtaOri,        jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sFolOri,        jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sNoSerOri,      jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sEmpOri,        jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sImpoOri,       jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sTotDescOri,    jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sTotCostOri,    jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sFCreaOri,      jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sFDocOri,       jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sUltModOri,     jTab.getSelectedRow(), 10);                        
                        jTab.setValueAt(sVenciOri,      jTab.getSelectedRow(), 11);                        
                        jTab.setValueAt(sNotCredOri,    jTab.getSelectedRow(), 12);                        
                        jTab.setValueAt(sNotCredPOri,   jTab.getSelectedRow(), 13);
                        jTab.setValueAt(sEstadOri,      jTab.getSelectedRow(), 14);                        
                        jTab.setValueAt(sEstacOri,      jTab.getSelectedRow(), 15);                        
                        jTab.setValueAt(sMotivOri,      jTab.getSelectedRow(), 16);                        
                        jTab.setValueAt(sTipDocOri,     jTab.getSelectedRow(), 17);                        
                        jTab.setValueAt(sObservOri,     jTab.getSelectedRow(), 18);                        
                        jTab.setValueAt(sSucOri,        jTab.getSelectedRow(), 19);                        
                        jTab.setValueAt(sCajOri,        jTab.getSelectedRow(), 20);                        
                        jTab.setValueAt(sNomEstacOri,   jTab.getSelectedRow(), 21);
                        jTab.setValueAt(sTimOri,        jTab.getSelectedRow(), 22);                        
                        jTab.setValueAt(sCodCotOri,     jTab.getSelectedRow(), 23);
                        jTab.setValueAt(sVtaDevOri,     jTab.getSelectedRow(), 25);
                        jTab.setValueAt(sFolFiscOri,    jTab.getSelectedRow(), 26);
                        
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Visor Tabla de Empresas");
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
                "No.", "Venta", "Folio", "Serie", "Cliente", "Importe", "Total Descuento", "Total Costo", "Fecha Creación", "Fecha Documento", "Última Modificación", "Vencimiento", "Asignada N.C.", "Pago N.C.", "Estado", "Usuario", "Motivo", "Tipo Documento", "Observaciones", "Sucursal", "No. Caja", "Nombre Usuario", "Timbrada", "Cod.Cotización", "Venta Devolución", "ID", "Fol.Fiscal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true
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

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

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

    
    /*Cuando se presiona una tecla en el botón de ver la tabla*/
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
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
