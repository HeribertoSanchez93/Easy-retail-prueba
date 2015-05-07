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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para controlar la búsqueda genérica 3*/
public class Busc3 extends javax.swing.JDialog
{
    /*Declara variables de instancia*/
    private static  Busc3               obj = null; 
    private final int                   iEn;
    private final String                sBusc;
    private final JTextField            jTFCamp;
    private final JTextField            jTFCamp2;
    private final JTextField            jTFCamp3;
    
    
    
    
    /*Constructor sin argumentos*/
    public Busc3(JFrame jFram, String sBusc1, int iE, JTextField jCamp ,JTextField jCamp2, JTextField jCamp3) 
    {           
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        //Que sea modal la forma
        this.setModal(true);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBCarg);
        
        /*Para que la tabla de partidas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Que las comlumnas de la tabla no se muevan*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Recibe la variable para saber de que bases de datos cargar la información*/
        iEn             = iE;
        
        /*Recibe lo que se va a búscar*/
        sBusc          = sBusc1;
        
        /*Recibe el manejador del campo del otro formulario*/
        jTFCamp         = jCamp;
        
        /*Recibe el manejador del campo 2 del otro formulario*/
        jTFCamp2        = jCamp2;
        
        /*Recibe el manejador del campo del otro formulario*/
        jTFCamp3        = jCamp3;
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Busqueda general 3, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Va a ser top most la ventana*/
        this.setAlwaysOnTop(true);
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de unidades*/        
        jTab.getColumnModel().getColumn(1).setPreferredWidth(180);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Función para saber de que base de datos cargar los datos*/
        vCargReg(sBusc);                        
                        
    }/*Fin de public Buscador3() */

    
    /*Función para saber de que base de datos cargar los datos*/
    private void vCargReg(String sBusc)
    {
        /*Switch entre los diferentes conceptos de busqueda*/
        switch(iEn)
        {
            /*Buscar en clientes*/
            case 1:
                             
                /*Obtiene todos los registros coincidentes de los prods y cargalos en la tabla*/        
                CargProds(sBusc);                    
                break;                
        }        
        
    }/*Fin de private void vCargReg(String sBusc, int iEn)*/
        
        
    /*Metodo para que el formulario no se abra dos veces*/
    public static Busc3 getObj(JFrame jFram, String sBusc, int iEn, JTextField jCampo, JTextField jCampo2, JTextField jCampo3)
    {
        /*Si es null entonces crea una nueva instancia*/
        if(obj==null)
            obj = new Busc3(jFram, sBusc, iEn, jCampo, jCampo2, jCampo3);
        
        /*Devuelve el resultado*/
        return obj;
        
    }/*Fin de public static Buscar getObj()*/
    
    
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
        jP1.add(jBCarg, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 140, 30));

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 140, 30));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod.", "Descripción", "Cod. Almacén"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setInheritsPopupMenu(true);
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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 220, 130, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
   /*Obtiene todos los registros y cargalos en la tabla*/
    private void CargProds(String sBusc)
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
        
        /*Trae todos los registros coincidentes de la base de datos prods*/
        try
        {
            sQ = "SELECT prod, descrip, alma FROM prods WHERE prod <> '-' AND compue <> 1 AND (CASE WHEN '" + sBusc + "' = '' THEN prod = prod ELSE prod LIKE('%" + sBusc + "%') END OR CASE WHEN '" + sBusc + "' = '' THEN descrip = descrip ELSE descrip LIKE('%" + sBusc + "%') END) ORDER BY prod";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces agregalos a la tabla
            while(rs.next())
            {                
                Object nu[]     = {rs.getString("prod"), rs.getString("descrip"), rs.getString("alma")};
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
                
    }/*Fin de private void CargProds()*/       
    
        
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
        obj = null;      
        
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
        
            /*Cierra el formulario*/
            this.dispose();
            obj = null;
            
            /*Si es de poner en un segundo campo entonces pon cadena vacia por que no tiene coincidencias*/
            if(iEn==2)
                jTFCamp2.setText("");
            
            /*Regresa*/
            return;                        

        }

        /*Coloca los valores leidos en los campos del otro formulario*/        
        jTFCamp.setText     (jTab.getValueAt(jTab.getSelectedRow(), 0).toString());
        jTFCamp2.setText    (jTab.getValueAt(jTab.getSelectedRow(), 1).toString());                    
        jTFCamp3.setText    (jTab.getValueAt(jTab.getSelectedRow(), 2).toString());                    
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Sal del formulario*/
        this.dispose();
        obj                 = null;
        
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
        jBCarg.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBCargMouseExited

    
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
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
