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




/*Clase para controlar los pes*/
public class ClasJeraGral extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color  colOri;
    
    /*Contiene el nodo global para saber en donde esta la selección en el arbol*/
    javax.swing.tree.DefaultMutableTreeNode nodeG;
    
    /*Contiene el nombre de la tabla*/
    String sTip;
            
    /*Contiene el nombre de la tabla para revisar la clasificacion*/
    String sTip2;
    
    /*Contiene el nombre del tipo de jerarquia*/
    String sTipC;
            
    /*Constructor argumentos nombre de la tabla*/
    public ClasJeraGral(String sTip, String sTip2, String sTipC) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Se toma el nombre de la tabla*/
        this.sTip=sTip;
        
        /*Se toma el nombre de la tabla*/
        this.sTip2=sTip2;
        
        /*Se toma el nombre de los mensajes*/
        this.sTipC=sTipC;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Clasificaciones " + sTipC + " jerárquico, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Pon el foco del teclado en el campo de edición de peso*/
        jTCod.grabFocus();
                
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
        vCargNod();                    
                
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
        jBDel = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jTCod = new javax.swing.JTextField();
        jBNew = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
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

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar peso(s) (Ctrl+Supr)");
        jBDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBDel.setNextFocusableComponent(jBSal);
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 520, 90, 20));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 550, 120, 30));

        jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCod.setNextFocusableComponent(jBNew);
        jTCod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCodFocusLost(evt);
            }
        });
        jTCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCodKeyPressed(evt);
            }
        });
        jP1.add(jTCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 310, 20));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nuevo peso (Ctrl+N)");
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 520, 90, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("*Clasificación:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 140, -1));

        jSTree.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSTreeKeyPressed(evt);
            }
        });

        jTre.setNextFocusableComponent(jTCod);
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
            .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todos los nodos de la base de datos y cargalos en la tabla*/
    private void vCargNod()
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
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;                        
        
        /*Recorre todos los nodos de la base de datos*/
        try
        {
            sQ = "SELECT rut, clas FROM " + sTip;
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
        
    }/*Fin de private void vCargNod()*/
                
    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si no a seleccionado nada del árbol entonces*/                
        if(jTre.getSelectionPath()==null)
        {   
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona alguna clasificación para borrar.", "Clasificaciones", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
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
        
        //Si la ruta es la de Clasificaciones entonces
        if(sRut.compareTo("Clasificaciones|")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se puede borrar esta clasificación.", "Borrar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
        
        /*Preguntar al usuario si esta seguro de querer borrar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere borrar la(s) clasificación(es)?", "Borrar Clasificación(es)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                                
        /*Quita la tubería final*/
        sRut        = sRut.substring(0, sRut.length() - 1);
                                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos        
        Statement   st;
        String      sQ;
        ResultSet   rs;
        
        /*Comprueba si esa clasificación ya existe para algún proveedor*/        
        try
        {
            sQ = "SELECT clasjera FROM "+ sTip2 +" WHERE clasjera = '" + sRut + "'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe*/
            if(rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
            
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Esta clasificación: " + nodeG + " esta asignada a alguno de los " + sTipC + " y no se puede eliminar.", "Clasificaciones", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                
                return;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }

        /*Borra de la base de datos la clasificación*/
        try 
        {                
            sQ = "DELETE FROM "+ sTip+" WHERE rut LIKE('" + sRut + "%')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
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
                    
        /*Borra la clasificación del árbol*/                
        javax.swing.tree.DefaultTreeModel mod         = (javax.swing.tree.DefaultTreeModel)jTre.getModel();
        mod.removeNodeFromParent(nodeG);
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Clasificación(es) borrada(s) con éxito.", "Clasificaciones", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBDelActionPerformed
   
   
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

    
    /*Cuando se presiona una tecla en el botón de agregar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed

    
    /*Cuando se presiona una tecla en el campo de edición de línea*/
    private void jTCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCodKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de línea*/
    private void jTCodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCod.setSelectionStart(0);jTCod.setSelectionEnd(jTCod.getText().length());        
        
    }//GEN-LAST:event_jTCodFocusGained

                
    /*Cuando se pierde el foco del teclado en el campo de peso*/
    private void jTCodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCodFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCod.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCod.getText().compareTo("")!=0)
            jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Si el campo excede la cantidad de caes permitidos recortalo*/
        if(jTCod.getText().length()> 30)
            jTCod.setText(jTCod.getText().substring(0, 30));
        
    }//GEN-LAST:event_jTCodFocusLost

                  
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
                                                       
        /*Si hay cadena vacia en el campo de la clasificación entonces*/
        if(jTCod.getText().replace(" ", "").trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La clasificación esta vacia.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCod.grabFocus();                        
            return;            
        }

        /*Si la clasificación contiene tubería entonces*/
        if(jTCod.getText().contains("|"))
        {
            /*Coloca el borde rojo*/                               
            jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La clasificación no puede tener el símbolo de tubería.", "Clasificación", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCod.grabFocus();                        
            return;            
        }
        
        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Agregar Clasificación", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;               
        
        /*Obtiene el módelo del árbol*/
        javax.swing.tree.DefaultTreeModel mod         = (javax.swing.tree.DefaultTreeModel)jTre.getModel();
        
        /*Obtiene el root del árbol*/
        javax.swing.tree.DefaultMutableTreeNode root = (javax.swing.tree.DefaultMutableTreeNode)mod.getRoot();
        
        /*Si no a seleccionado nada del árbol entonces agregalo en el nodo raíz*/                
        if(jTre.getSelectionPath()==null)
            mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(jTCod.getText().trim()), root, root.getChildCount());                            
                
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
        
        /*Obtiene todos los nodos y recorrelos*/
        java.util.Enumeration<javax.swing.tree.DefaultMutableTreeNode> e = nodeG.depthFirstEnumeration();
        while(e.hasMoreElements()) 
        {            
            /*Obtiene el objeto nodo*/
            javax.swing.tree.DefaultMutableTreeNode node = e.nextElement();
            
            /*Si el nodo es igual al que se quiere insertar entonces*/
            if(node.toString().compareToIgnoreCase(jTCod.getText().trim())==0) 
            {                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La clasificación que quieres insertar ya existe en esta categoría.", "Clasificaciones", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Coloca el borde rojo*/                               
                jTCod.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Coloca el foco del teclado en el campo de la clasificación y regresa*/
                jTCod.grabFocus();
                return;
            }
        }
        
        /*Agrega el nodo en el padre*/
        mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(jTCod.getText().trim()), nodeG, 0);                        
        
        /*Muestra y expande hasta ese root*/
        javax.swing.tree.TreeNode[] nodes = mod.getPathToRoot((javax.swing.tree.DefaultMutableTreeNode)jTre.getLastSelectedPathComponent());
        javax.swing.tree.TreePath path = new javax.swing.tree.TreePath(nodes);
        jTre.setExpandsSelectedPaths(true);
        jTre.setSelectionPath(path);        
        jTre.expandPath(path);        
                      
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;                
        String      sQ;
        
        /*Inserta en la base de datos la nueva clasificación*/
        try 
        {                
            sQ = "INSERT INTO "+ sTip +"( clas,                                               rut,                                                                                            estac,                                       sucu,                                    nocaj) " + 
                               "VALUES('" + jTCod.getText().replace("'", "''").trim() + "','" + sRut.replace("'", "''").trim() + "|" + jTCod.getText().replace("'", "''").trim() + "', '" +     Login.sUsrG.replace("'", "''") +"',  '" +    Star.sSucu.replace("'", "''") + "','" +  Star.sNoCaj.replace("'", "''") + "')";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
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
                                
        /*Pon el foco del teclado en el campo de la clasificación*/
        jTCod.grabFocus();
        
        /*Limpia el campo*/
        jTCod.setText       ("");          
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Clasificación agregada con éxito.", "Agregar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBNewActionPerformed

    
   
    
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
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

        
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelMouseEntered

            
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

            
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
            jBDel.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBSal;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jSTree;
    private javax.swing.JTextField jTCod;
    private javax.swing.JTree jTre;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
