//Paquete
package cats;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import ptovta.Star;
import ptovta.Login;




/*Clase para ver las clasificaciones jerárquicas y regresar la ruta de alguna*/
public class ClasJeraVis extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color  colOri;
    
    /*Contiene el nodo global para saber en donde esta la selección en el arbol*/
    javax.swing.tree.DefaultMutableTreeNode nodeG;
    
    /*Contiene la dirección del control del otro formulario para colocar la ruta seleccionada*/
    private final javax.swing.JTextField jT1;
            
    
    
    
            
    /*Constructor sin argumentos*/
    public ClasJeraVis(String sRut, javax.swing.JTextField jTDir, String sTip) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Guarda la dirección del control donde se tiene que devover la ruta seleccionada*/
        jT1 = jTDir;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGua);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Clasificaciones jerárquicas, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Pon el foco del teclado en el el árbol*/
        jTre.grabFocus();
                
        /*Establece la raíz de todos*/
        javax.swing.tree.DefaultTreeModel mod         = (javax.swing.tree.DefaultTreeModel)jTre.getModel();
        javax.swing.tree.DefaultMutableTreeNode root  = new javax.swing.tree.DefaultMutableTreeNode("Clasificaciones");                
        mod.setRoot(root);

        /*Muestra y expande hasta ese root*/
        javax.swing.tree.TreeNode[] nodes = mod.getPathToRoot(root);
        javax.swing.tree.TreePath path = new javax.swing.tree.TreePath(nodes);        
        jTre.setExpandsSelectedPaths(true);
        jTre.setSelectionPath(path);        
        jTre.expandPath(path);        
        
        /*Carga todos los nodos de la base de datos en el árbol*/
        vCargNod(sTip, sRut);                    
                
        /*El nodo inicial será el root*/
        nodeG   = root;
        
        /*Listener para cuando cambia algo en el árbol*/
        jTre.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() 
        {
            @Override
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) 
            {   
                /*Obtén el nodo seleccionado*/
                nodeG = (javax.swing.tree.DefaultMutableTreeNode)jTre.getLastSelectedPathComponent();                                                
            }
        });
                
    }/*Fin de public ClasJeraCli() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBGua = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jSTree = new javax.swing.JScrollPane();
        jTre = new javax.swing.JTree();

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

        jBGua.setBackground(new java.awt.Color(255, 255, 255));
        jBGua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGua.setForeground(new java.awt.Color(0, 102, 0));
        jBGua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGua.setText("Guardar");
        jBGua.setToolTipText("Guardar (Ctrl+G)");
        jBGua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBGua.setNextFocusableComponent(jBSal);
        jBGua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGuaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGuaMouseExited(evt);
            }
        });
        jBGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuaActionPerformed(evt);
            }
        });
        jBGua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGuaKeyPressed(evt);
            }
        });
        jP1.add(jBGua, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 500, 110, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (Esc)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jTre);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 500, 110, 30));

        jSTree.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSTreeKeyPressed(evt);
            }
        });

        jTre.setNextFocusableComponent(jBGua);
        jTre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTreKeyPressed(evt);
            }
        });
        jSTree.setViewportView(jTre);

        jP1.add(jSTree, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todos los nodos de la base de datos y cargalos en la tabla*/
    private void vCargNod(String sTip, String sRut)
    {
        /*Obtiene el módelo y la raíz del árbol*/
        javax.swing.tree.DefaultTreeModel mod           = (javax.swing.tree.DefaultTreeModel)jTre.getModel();                
        javax.swing.tree.DefaultMutableTreeNode root    = (javax.swing.tree.DefaultMutableTreeNode)mod.getRoot();               
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Contiene el nodo original*/
        javax.swing.tree.DefaultMutableTreeNode rootOri = root;               
        
        /*Determina la tabla por si es cliente o proveedor*/
        String sTab = "clasjeracli";
        if(sTip.compareTo("prov")==0)
            sTab    = "clasjeraprov";
        else if(sTip.compareTo("prod")==0)
            sTab    = "clasjeraprod";

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;                        
        
        /*Recorre todos los nodos de la base de datos*/
        try
        {
            sQ = "SELECT rut, clas FROM " + sTab;
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {       
                /*Resetea el root original*/
                root    = rootOri;
                
                /*Tokeniza la ruta por tubería*/
                java.util.StringTokenizer stk = new java.util.StringTokenizer(rs.getString("rut"), "|");                                                                    
                                
                /*Recorre todos los tokens*/                
                while(stk.hasMoreTokens())
                {
                    /*Crea el arreglo que contendrá ese token*/
                    Object[] obAr       = new Object[1];
                    
                    /*Inicializa el objeto con el token*/
                    obAr[0]             = stk.nextToken();                                        
                    
                    /*Cree el path array de los nodos*/
                    javax.swing.tree.TreePath pat = new javax.swing.tree.TreePath(obAr);                
                                        
                    /*Si el nodo que se quiere insertar es igual al nodo acutal entonces continua*/
                    if(pat.getLastPathComponent().toString().compareTo(root.toString())==0)                                        
                        continue;                    
                    
                    /*Para saber si ya existe ese nodo en el nodo actual*/
                    boolean bSi         = true;
                    
                    /*Si este nodo tiene hijos entonces*/
                    if(root.getChildCount()>0)
                    {
                        /*Recorre los hijos de este nodo*/
                        for(int x = 0; x < root.getChildCount(); x++)
                        {
                            /*Si alguno de los hijos es igual al nodo que se quiere insertar entonces*/
                            if(root.getChildAt(x).toString().compareTo(pat.getLastPathComponent().toString())==0)
                            {
                                /*Avanza al nodo siguiente*/
                                root        = (javax.swing.tree.DefaultMutableTreeNode)root.getChildAt(x);
                                
                                /*Coloca la bandera para saber que ya existe y sal del búcle*/
                                bSi = false;
                                break;
                            }
                        }
                    }                                                                
                    
                    /*Agrega el nodo y avanza al siguiente hijo*/
                    if(bSi)
                    {
                        root.add(new javax.swing.tree.DefaultMutableTreeNode(pat.getLastPathComponent()));                                                                                                               
                        root        = (javax.swing.tree.DefaultMutableTreeNode)root.getLastChild();
                    }                        
                }                                                                                   
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

        /*Expande la raíz*/
        javax.swing.tree.TreePath trePath   = new javax.swing.tree.TreePath(rootOri);
        jTre.expandPath(trePath);
        
        /*Si la ruta es cadena vacia entonces regresa*/
        if(sRut.compareTo("")==0)
            return;
        
        /*Separa la cadena en tokens de tubería*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sRut, "|");
        
        /*Resetea el root*/
        root    = rootOri;
        
        /*Recorre todos los tokens*/                
        while(stk.hasMoreTokens())
        {
            /*Crea el arreglo que contendrá ese token*/
            Object[] obAr       = new Object[1];

            /*Inicializa el objeto con el token*/
            obAr[0]             = stk.nextToken();                                        

            /*Cree el path array de los nodos*/
            javax.swing.tree.TreePath pat = new javax.swing.tree.TreePath(obAr);                

            /*Si el nodo que se quiere insertar es igual al nodo acutal entonces continua*/
            if(pat.getLastPathComponent().toString().compareTo(root.toString())==0)                                        
                continue;                    

            /*Si este nodo tiene hijos entonces*/
            if(root.getChildCount()>0)
            {
                /*Recorre los hijos de este nodo*/
                for(int x = 0; x < root.getChildCount(); x++)
                {
                    /*Si alguno de los hijos es igual al nodo que se quiere insertar entonces*/
                    if(root.getChildAt(x).toString().compareTo(pat.getLastPathComponent().toString())==0)
                    {                                                
                        /*Avanza al nodo siguiente*/
                        root        = (javax.swing.tree.DefaultMutableTreeNode)root.getChildAt(x);                               
                        
                        /*Expande el nodo y seleccionalo*/
                        javax.swing.tree.TreeNode[] nodes = mod.getPathToRoot(root);
                        javax.swing.tree.TreePath path = new javax.swing.tree.TreePath(nodes);        
                        jTre.setExpandsSelectedPaths(true);
                        jTre.setSelectionPath(path);        
                        jTre.expandPath(path);        
                        
                        /*Sal del búcle*/
                        break;
                    }
                }
            }                                                                           
            
        }/*Fin de while(stk.hasMoreTokens())*/                                                                                                       
        
    }/*Fin de private void vCargNod()*/
                
    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuaActionPerformed
        
        /*Si no a seleccionado nada del árbol entonces*/                
        if(jTre.getSelectionPath()==null)
        {   
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona alguna clasificación primero.", "Clasificaciones", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en el árbol y regresa*/
            jTre.grabFocus();
            return;
        }
            
        /*Obtiene la ruta seleccionada*/
        javax.swing.tree.TreePath[] paths = jTre.getSelectionPaths();        
        
        /*Construye la ruta seleccionada*/
        String sRut = "";
        for(javax.swing.tree.TreePath pa: paths)        
        {
            /*Recorre toda la ruta para crear la cadena*/            
            for(int x = 0; x < pa.getPathCount(); x++)
                sRut    += pa.getPathComponent(x) + "|";
        }
                
        /*Quita la tubería final*/
        sRut        = sRut.substring(0, sRut.length() - 1);
       
        /*Se compara que no sea el nodo raiz el seleccionado*/
        if(sRut.compareTo("Clasificaciones")==0)
               sRut="";
        
        /*Coloca la ruta en el control correspondiente del otro formulario*/
        if(jT1!=null)
            jT1.setText(sRut);
        
        /*Cierra la forma*/
        dispose();
        
    }//GEN-LAST:event_jBGuaActionPerformed
   
   
    /*Cuando se presiona una tecla en el formulario*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

   
    /*Cuando se presiona una tecla en el botón de guardar*/
    private void jBGuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuaKeyPressed
    
    
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

            
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

            
    /*Cuando el mouse entra en el botón específico*/
    private void jBGuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuaMouseEntered
        
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

       
    /*Cuando el mouse sale del botón específico*/
    private void jBGuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGua.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuaMouseExited
        
    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se presiona una tecla en el árbol*/
    private void jTreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTreKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTreKeyPressed

    
    /*Cuando se presiona una tecla en el control del scroll del árbol*/
    private void jSTreeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSTreeKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jSTreeKeyPressed
    
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBGua.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGua.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGua;
    private javax.swing.JButton jBSal;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jSTree;
    private javax.swing.JTree jTre;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
