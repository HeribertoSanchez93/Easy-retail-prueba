//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import static ptovta.Princip.bIdle;




/*Clase para compartir archivos entre todas las usuarios*/
public class ArchComps extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private java.awt.Color      colOri;
    
    /*Bandera para saber si el usuario selecciono algo en el árbol*/
    private boolean             bSel;
    
    /*Almacena la ruta completa hacia el archivo o carpeta compartido a borrar*/
    private String              sRutPad;
    
    
    
    /*Constructor sin argumentos*/
    public ArchComps() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBEnvi);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Inicialmente no hay selección*/
        bSel    = false;
                
        /*Establece el titulo de la ventana con el usuario, la fecha y hora*/                
        this.setTitle("Archivos compartidos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Pon el foco del teclado en el árbol de directorios*/
        jTR1.grabFocus();                                   
        
        /*Listener para cuando cambia algo en el árbol*/
        jTR1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() 
        {
            @Override
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) 
            {   
                /*Objeto nodo*/
                javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode)jTR1.getLastSelectedPathComponent();
                
                /*Si no se a seleccionado nada entonces regresa*/ 
                if(node == null) 
                    return;

                /*Coloca la bandera pra saber que hay selección*/
                bSel    = true;
                
                /*Obtiene el nodo que se seleccionó*/ 
                Object nod = node.getUserObject();                                                
                
                /*Obtiene el padres del nodo*/
                javax.swing.tree.TreeNode[] tre = node.getPath();
                
                /*Si es el nodo raíz entonces*/
                if(tre[0].toString().compareTo(nod.toString())==0)
                {
                    /*La ruta sera esta y regresa*/
                    sRutPad = nod.toString();                                                                
                    return;
                }
                
                /*Si tiene padre entonces guarda la ruta al padre globalmente*/
                if(tre!=null)
                    sRutPad = tre[0].toString() + "\\" + nod.toString();                                                                
            }
        });
               
        /*Carga todos los archivos compartidos*/
        vCarg();                        
        
    }/*Fin de public ArchComps() */
                                                                           
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jBEnvi = new javax.swing.JButton();
        jBBusc = new javax.swing.JButton();
        jTRut = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jBDesc = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTR1 = new javax.swing.JTree();
        jBDel = new javax.swing.JButton();
        jBActua = new javax.swing.JButton();
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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jBSal.setNextFocusableComponent(jBDel);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 460, 130, 30));

        jBEnvi.setBackground(new java.awt.Color(255, 255, 255));
        jBEnvi.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBEnvi.setForeground(new java.awt.Color(0, 102, 0));
        jBEnvi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/envi.png"))); // NOI18N
        jBEnvi.setToolTipText("Enviar Petición de Archivo (Ctrl+S)");
        jBEnvi.setNextFocusableComponent(jBSal);
        jBEnvi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBEnviMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBEnviMouseExited(evt);
            }
        });
        jBEnvi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEnviActionPerformed(evt);
            }
        });
        jBEnvi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBEnviKeyPressed(evt);
            }
        });
        jP1.add(jBEnvi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 130, 30));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setText("jButton1");
        jBBusc.setToolTipText("Buscar Ruta");
        jBBusc.setNextFocusableComponent(jBDesc);
        jBBusc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBuscMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBuscMouseExited(evt);
            }
        });
        jBBusc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscActionPerformed(evt);
            }
        });
        jBBusc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBuscKeyPressed(evt);
            }
        });
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 440, 30, 20));

        jTRut.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTRut.setNextFocusableComponent(jBBusc);
        jTRut.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTRutFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTRutFocusLost(evt);
            }
        });
        jTRut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTRutKeyPressed(evt);
            }
        });
        jP1.add(jTRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 260, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Archivos/Carpetas:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, 200, 20));

        jBDesc.setBackground(new java.awt.Color(255, 255, 255));
        jBDesc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBDesc.setText("Descargar");
        jBDesc.setNextFocusableComponent(jBEnvi);
        jBDesc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBDescMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBDescMouseExited(evt);
            }
        });
        jBDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDescActionPerformed(evt);
            }
        });
        jBDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBDescKeyPressed(evt);
            }
        });
        jP1.add(jBDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 440, -1, 20));

        jTR1.setNextFocusableComponent(jTRut);
        jTR1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTR1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTR1);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 380, 410));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBDel.setForeground(new java.awt.Color(0, 102, 0));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Archivo/Directorio (Ctrl+SUPR)");
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 460, -1, 30));

        jBActua.setBackground(new java.awt.Color(255, 255, 255));
        jBActua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actualizar.png"))); // NOI18N
        jBActua.setToolTipText("Actualizar Registros (F5)");
        jBActua.setNextFocusableComponent(jTR1);
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
        jP1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 460, 33, 30));

        jLAyu.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLAyu.setForeground(new java.awt.Color(0, 51, 204));
        jLAyu.setText("http://Ayuda en Lìnea");
        jLAyu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLAyuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLAyuMouseExited(evt);
            }
        });
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 510, 120, -1));

        getContentPane().add(jP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 400, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Carga los archivos compartidos de todos*/
    private synchronized void vCarg()
    {        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
                
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de las compartidos no existe entonces crea la carpeta*/
        sCarp                    += "\\Compartidos Todos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Borra el contenido del árbol*/
        javax.swing.tree.DefaultTreeModel md            = (javax.swing.tree.DefaultTreeModel)jTR1.getModel();                
        javax.swing.tree.DefaultMutableTreeNode root    = (javax.swing.tree.DefaultMutableTreeNode)md.getRoot();               
        root.removeAllChildren();        
        md.reload();
                
        /*Crea el nuevo root*/
        File fFil = new File(sCarp);
        root = new javax.swing.tree.DefaultMutableTreeNode(fFil);
        
        /*Carga el en root los directorios recursivamente*/
        sRecu(sCarp, root);                
            
        /*Establece el nuevo Modelo*/
        md.setRoot(root);
        
    }/*Fin de private void vCarg()*/
    
        
    /*Función recursiva para recorrer recursivamente los directorios*/
    private String sRecu(String sRut, javax.swing.tree.DefaultMutableTreeNode nodPap)
    {
        /*Obtiene la lista de esa ruta*/
        File f      = new File(sRut);
        File[]fA    = f.listFiles();
                
        /*Si tiene directorios entonces recorrelos recursivamente*/
        if(fA!=null && fA.length > 0)
        {
            for(File fA1 : fA) 
            {
                /*Crea nodo*/
                javax.swing.tree.DefaultMutableTreeNode nodHij   = new javax.swing.tree.DefaultMutableTreeNode(fA1.getName());               
        
                /*Agrega el nuevo root*/
                nodPap.add(nodHij);
            
                /*Llama a la función recurviva*/
                sRecu(fA1.getAbsolutePath(), nodHij);            
            }
        }                                                
        
        /*Regresa la ruta*/
        return sRut;                                
    }
    
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

    
    /*Cuando se presiona una tecla en el panel*/
    private void jP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jP1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jP1KeyPressed

        
    /*Cuando se presiona una tecla en el botón salir*/
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

    
    /*Cuando se presiona una tecla en el botón de enviar*/
    private void jBEnviKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBEnviKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBEnviKeyPressed

        
    /*Cuando se presiona el botón de enviar*/
    private void jBEnviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEnviActionPerformed
        
        /*Si no a ingresado una ruta entonces*/
        if(jTRut.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTRut.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una ruta de archivo/carpeta a enviar.", "Enviar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTRut.grabFocus();
            return;
        }            
        
        /*Si el archivo/carpeta no existe entonces*/
        if(!new File(jTRut.getText()).exists())
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El archivo/carpeta " + jTRut.getText() + " no existe. Ingresa un archivo/carpeta existente.", "Enviar Archivo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/
            jTRut.grabFocus();
            return;
        }
        
        /*Preguntar al usuario si esta seguro de querer enviar el archivo al(los) usuario(s)*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres enviar archivo(s)/carpeta(s)?", "Enviar", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                                    

        /*Copia el archivo origén al servidor e inserta la petición*/
        vCopFil();                                                        
        
    }//GEN-LAST:event_jBEnviActionPerformed


    /*Copia el archivo origén al servidror*/
    private void vCopFil()
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }
        
        /*Si la carpeta de las compartidos no existe entonces crea la carpeta*/
        sCarp                    += "\\Compartidos Todos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Obtiene el nombre de la carpeta/archivo a enviar*/
        String sNomAp           = new File(jTRut.getText()).getName();
        
        /*Completa la ruta a dode se irá el archivo/carpeta*/        
        sCarp                   += "\\" + sNomAp;
        
        /*Declara variabls final para el thread*/
        final String sCarpFi    = sCarp;
        
        /*Thread para hacer el cargado de los archivos o carpetas*/
        (new Thread()
        {
            @Override
            public void run()
            {
                //Muestra el loading
                Star.vMostLoading("");

                /*Si es un archivo entonces*/
                if(new File(jTRut.getText()).isFile())
                {
                    /*Copia el archivo orgien al servidor*/
                    try
                    {
                        org.apache.commons.io.FileUtils.copyFile(new File(jTRut.getText()), new File(sCarpFi));
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                        return;
                    }
                }
                /*Else es un directorio*/
                else
                {
                    /*Copia el carpeta orgien al servidor*/
                    try
                    {
                        org.apache.commons.io.FileUtils.copyDirectory(new File(jTRut.getText()), new File(sCarpFi));
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                        return;
                    }
                }                                        

                //Esconde la forma de loading
                Star.vOcultLoadin();

                /*Carga nuevamente los archivos compartidos*/
                vCarg();

                /*Mensajea de éxito*/
                JOptionPane.showMessageDialog(null, "Archivo(s)/carpeta(s) enviado(s) con éxito.", "Enviar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));         
            }
            
        }).start();
        
    }/*Fin de private void vCopFil(Connection con)*/
            
                        
    
    /*Cuando se gana el foco del teclado en el campo de la ruta del archivo a enviar*/
    private void jTRutFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRutFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTRut.setSelectionStart(0);        
        jTRut.setSelectionEnd(jTRut.getText().length());        
        
    }//GEN-LAST:event_jTRutFocusGained

    
    /*Cuando se presiona una tecla en el botón de bùscar ruta de archivo a enviar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la ruta a enviar*/
    private void jTRutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRutKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTRutKeyPressed

    
    /*Cuando se presiona el botón de bùscar archivo*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed
        
        /*Configura el file chooser para escoger la ruta del directorio donde esta la calculadora*/
        final JFileChooser fc   = new JFileChooser  ();
        fc.setDialogTitle                           ("Buscar");
        fc.setAcceptAllFileFilterUsed               (false);
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        /*Si el usuario presiono aceptar entonces*/
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            /*Lee la ruta seleccionada*/
            String sRut         = fc.getCurrentDirectory().getAbsolutePath();

            /*Concatena el exe al final seleccionado*/
            sRut                += "\\" + fc.getSelectedFile().getName();    

            /*Coloca la ruta en el campo*/
            jTRut.setText(sRut);
        }
        
    }//GEN-LAST:event_jBBuscActionPerformed

                
    /*Cuando se presiona una tecla en el botón de descargar*/
    private void jBDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDescKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDescKeyPressed

        
    /*Cuando se presiona el botón de descargar*/
    private void jBDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDescActionPerformed
        
        /*Si no hay selección en el control entonces*/
        if(!bSel)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona Archivo(s)/Carpeta(s) primero.", "Descargar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control*/
            jTR1.grabFocus();
            return;
        }
        
        /*Configura el file chooser para escoger la ruta del directorio donde esta la calculadora*/
        final JFileChooser fc                       = new JFileChooser  ();
        fc.setDialogTitle                           ("Guardar");
        fc.setAcceptAllFileFilterUsed               (false);
        fc.setFileSelectionMode                     (JFileChooser.DIRECTORIES_ONLY);

        /*Si el usuario presiono aceptar entonces obtiene la ruta, caso contrario regresa*/
        String sRut;
        if(fc.showSaveDialog(this)== JFileChooser.APPROVE_OPTION)
                sRut            = fc.getSelectedFile().getAbsolutePath();   
        else
            return;
        
        /*Descarga el archivo/directorio en el disco local*/
        vDescFil(sRut);                                        
        
    }//GEN-LAST:event_jBDescActionPerformed

    
    /*Cuando se presiona una tecla en el control de directorios*/
    private void jTR1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTR1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTR1KeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se presiona una tecla en el botón de actualizar*/
    private void jBActuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBActuaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBActuaKeyPressed

    
    /*Cuando se presiona el botón de actualizar*/
    private void jBActuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActuaActionPerformed
        
        /*Carga todos los archivos compartidos*/
        vCarg();                        
        
    }//GEN-LAST:event_jBActuaActionPerformed

    
    /*Cuando se presiona el botón de suprimir*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed

        /*Si no hay selección en el control entonces*/
        if(!bSel)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona Archivo(s)/Carpeta(s).", "Borrar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control*/
            jTR1.grabFocus();
            return;
        }

        /*Preguntar al usuario si esta seguro de querer borrar el archivo o directorio*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar Archvio(s)/Directorio(s)?", "Borrar", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                                    
        
        /*Thread para hacer el borrado de archivos y directorios*/
        (new Thread()
        {
            @Override
            public void run()
            {
                //Muestra el loading
                Star.vMostLoading("");

                /*Si es archivo entonces borra el archivo*/    
                if(new File(sRutPad).isFile())
                    new File(sRutPad).delete();
                /*Else es directorio entonces*/
                else
                {
                    try
                    {
                        /*Borra el contenido de la caerpeta*/
                        FileUtils.cleanDirectory(new File(sRutPad));                                             

                        /*Borra el directorio en si*/
                        new File(sRutPad).delete();

                    }
                    catch(IOException expnIO)
                    {   
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                        return;
                    }                
                }

                //Esconde la forma de loading
                Star.vOcultLoadin();

                /*Mensaje de éxito*/
                JOptionPane.showMessageDialog(null, "Archivo(s)/caperta(s) borrado(s) con éxito.", "Borrar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Carga nuevamente los archivos compartidos*/
                vCarg();
                
            }/*Fin de public void run()*/
            
        }).start();
        
    }//GEN-LAST:event_jBDelActionPerformed

    
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

    
    /*Cuando se pierde el foco del teclado en el control de la ruta*/
    private void jTRutFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTRutFocusLost

        /*Coloca el cursor al principio del control*/
        jTRut.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTRut.getText().compareTo("")!=0)
            jTRut.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTRutFocusLost

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);
                
    }//GEN-LAST:event_jBBuscMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDescMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDescMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDesc.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDescMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBEnviMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEnviMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBEnvi.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBEnviMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
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

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(colOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDescMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDescMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDesc.setBackground(colOri);
        
    }//GEN-LAST:event_jBDescMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBEnviMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBEnviMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBEnvi.setBackground(colOri);
        
    }//GEN-LAST:event_jBEnviMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
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

    
    /*Descarga el archivo/directorio en el disco local*/
    private void vDescFil(String sRut)
    {
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));           
            return;                        
        }

        /*Si la carpeta de las compartidos no existe entonces crea la carpeta*/
        sCarp                    += "\\Compartidos Todos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la empresa no existe entonces crea la carpeta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Obtiene el nombre del archivo o carpeta de la ruta origén*/
        String sNomArch          = new File(sRutPad).getName();
        
        /*Completa la ruta de destino*/        
        sRut                     += "\\" + sNomArch;
                
        /*Declara variables final para el thread*/
        final String sRutFi      = sRut;
        
        /*Thread para descargar todos los archivos y carpetas*/
        (new Thread()
        {
            @Override
            public void run()
            {
                //Muestra el loading
                Star.vMostLoading("");

                /*Si es un archivo entonces*/
                if(new File(sRutPad).isFile())
                {         
                    /*Copia el archivo orgien al servidor*/
                    try
                    {
                        org.apache.commons.io.FileUtils.copyFile(new File(sRutPad), new File(sRutFi));
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                        return;
                    }
                }
                /*Else es un directorio*/
                else
                {                      
                    /*Copia el carpeta orgien al servidor*/
                    try
                    {
                        org.apache.commons.io.FileUtils.copyDirectory(new File(sRutPad), new File(sRutFi));
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                
                        return;
                    }
                }                                

                //Esconde la forma de loading
                Star.vOcultLoadin();

                /*Mensajea de éxito*/        
                JOptionPane.showMessageDialog(null, "Descarga archivo(s)/directorio(s) con éxito.", "Descarga", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                 
                
            }/*Fin de public void run()*/
            
        }).start();
            
    }/*Fin de private void vDescFil(String sID, Connection con)*/
            
                    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + S entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_S)
            jBEnvi.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Else if se presiona F5 entonces presiona el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBDesc;
    private javax.swing.JButton jBEnvi;
    private javax.swing.JButton jBSal;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTR1;
    private javax.swing.JTextField jTRut;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
