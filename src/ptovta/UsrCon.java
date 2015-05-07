//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import report.RepEstac;




/*Clase para ver los usuarios conectados actualmente*/
public class UsrCon extends javax.swing.JFrame 
{
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;
    
    /*Declara variables de instancia*/    
    private int             iContFi;
    private Thread          th;
    
    /*Contador para modificar tabla*/
    private int             iContCellEd;
        
    /*Declara variables originales*/
    private String          sSucOri;
    private String          sCajOri;
    private String          sEstacOri;
    private String          sNomEstacOri;
    
    /*Thread para recibir microfono del host*/
    private Thread          thRMic;

    /*Socket para conectar y sacar al usuario*/
    private java.net.Socket socSaca;
    
    /*Socket para obtener el audio del usuario*/
    private java.net.DatagramSocket  socRAud;
    
    
    
    
    
    
    /*Constructor sin argumentos*/
    public UsrCon() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
    
        /*Inicialmente esta deseleccionada la tabla*/
        bSel    = false;
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Usuarios conectados, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en 1*/
        iContFi      = 1;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla*/
        jTab.grabFocus();

        /*Agrega el listener para cuando se cierra la forma captar el evento*/
        this.addWindowStateListener(
        new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                //Interumple el thread principal que hace conexiones con los usuarios para saber si están conectados
                if(th!=null)
                    th.interrupt();
            }
        });

        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para cuando se cambia de selección en la tabla*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                //Si no hay selección entonces regresa
                if(jTab.getSelectedRow()==-1)
                    return;
                
                /*Obtén la propiedad que a sucedio en el control*/
                String pr = event.getPropertyName();                                
                                                                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pr)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        /*Obtiene todos los datos originales*/
                        sSucOri        = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sCajOri        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sEstacOri      = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sNomEstacOri   = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                        
                                                
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sSucOri,        jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sCajOri,        jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sEstacOri,      jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sNomEstacOri,   jTab.getSelectedRow(), 4);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Crea el thread para que este monitoreando los usuarios conectados*/
        th = new Thread()
        {
            @Override
            public void run()
            {
                /*lama a la función para que realice el proceso*/
                vCone();
            }
        };
        th.start();
        
    }/*Fin de public Pesos() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBRep = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBRemot = new javax.swing.JButton();
        jBSac = new javax.swing.JButton();
        jBTod = new javax.swing.JButton();
        jBObtAud = new javax.swing.JButton();
        jBObtVid = new javax.swing.JButton();

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
        jBSal.setName(""); // NOI18N
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 120, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Usuarios Conectados:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 370, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Sucursal", "Caja", "Usuario", "Nombre Usuario"
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
        jTab.setNextFocusableComponent(jBRep);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 490, 350));

        jBRep.setBackground(new java.awt.Color(255, 255, 255));
        jBRep.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBRep.setForeground(new java.awt.Color(0, 102, 0));
        jBRep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/visor.png"))); // NOI18N
        jBRep.setText("Reporte");
        jBRep.setToolTipText("Ver reporte de usuarios (Ctrl+R)");
        jBRep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBRep.setName(""); // NOI18N
        jBRep.setNextFocusableComponent(jBRemot);
        jBRep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBRepMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBRepMouseExited(evt);
            }
        });
        jBRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRepActionPerformed(evt);
            }
        });
        jBRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBRepKeyPressed(evt);
            }
        });
        jP1.add(jBRep, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 120, 30));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 360, 10, 20));

        jLAyu.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLAyu.setForeground(new java.awt.Color(0, 51, 204));
        jLAyu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLAyu.setText("http://Ayuda en Lìnea");
        jLAyu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLAyuMouseEntered(evt);
            }
        });
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(496, 360, 120, 20));

        jBRemot.setBackground(new java.awt.Color(255, 255, 255));
        jBRemot.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBRemot.setForeground(new java.awt.Color(0, 102, 0));
        jBRemot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/remot.png"))); // NOI18N
        jBRemot.setText("Remoto");
        jBRemot.setToolTipText("Ver remotamente el equipo de un usuario");
        jBRemot.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBRemot.setNextFocusableComponent(jBSac);
        jBRemot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBRemotMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBRemotMouseExited(evt);
            }
        });
        jBRemot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemotActionPerformed(evt);
            }
        });
        jBRemot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBRemotKeyPressed(evt);
            }
        });
        jP1.add(jBRemot, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 120, 30));

        jBSac.setBackground(new java.awt.Color(255, 255, 255));
        jBSac.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSac.setForeground(new java.awt.Color(0, 102, 0));
        jBSac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sacusr.png"))); // NOI18N
        jBSac.setText("Sacar");
        jBSac.setToolTipText("Sacar usuario(s) del sistema");
        jBSac.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSac.setNextFocusableComponent(jBObtVid);
        jBSac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBSacMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBSacMouseExited(evt);
            }
        });
        jBSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSacActionPerformed(evt);
            }
        });
        jBSac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBSacKeyPressed(evt);
            }
        });
        jP1.add(jBSac, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, 120, 30));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar todo");
        jBTod.setToolTipText("Marcar Todos los Registros en la Tabla (Alt+T)");
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 12, 130, 18));

        jBObtAud.setBackground(new java.awt.Color(255, 255, 255));
        jBObtAud.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBObtAud.setForeground(new java.awt.Color(0, 102, 0));
        jBObtAud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/obtaud.png"))); // NOI18N
        jBObtAud.setText("Audio");
        jBObtAud.setToolTipText("Recibir audio del usuario");
        jBObtAud.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBObtAud.setNextFocusableComponent(jBSal);
        jBObtAud.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBObtAudMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBObtAudMouseExited(evt);
            }
        });
        jBObtAud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBObtAudActionPerformed(evt);
            }
        });
        jBObtAud.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBObtAudKeyPressed(evt);
            }
        });
        jP1.add(jBObtAud, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 150, 120, 30));

        jBObtVid.setBackground(new java.awt.Color(255, 255, 255));
        jBObtVid.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBObtVid.setForeground(new java.awt.Color(0, 102, 0));
        jBObtVid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/obtvid.png"))); // NOI18N
        jBObtVid.setText("Video");
        jBObtVid.setToolTipText("Recibir video del usuario");
        jBObtVid.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBObtVid.setNextFocusableComponent(jBObtAud);
        jBObtVid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBObtVidMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBObtVidMouseExited(evt);
            }
        });
        jBObtVid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBObtVidActionPerformed(evt);
            }
        });
        jBObtVid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBObtVidKeyPressed(evt);
            }
        });
        jP1.add(jBObtVid, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, 120, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Comprueba los usuarios que están conectados y los que no*/
    private void vCone()
    {               
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();         
     
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Bucle infinito*/
        while(true)
        {
            /*Borrra la tabla primeramente*/
            te.setRowCount(0);
            
            /*Si la conexión esta cerrada entonces regresa*/
            try
            {
                if(con.isClosed())
                    return;
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                            
            }            
            
            /*Inicializa el contador de filas en 1*/
            iContFi      = 1;
                    
            //Obtiene todos los registros de la tabla de logestac
            try
            {
                sQ = "SELECT logestac.ESTAC, logestac.SUCU, logestac.NOCAJ, estacs.NOM FROM logestac LEFT OUTER JOIN estacs ON estacs.ESTAC = logestac.ESTAC WHERE logestac.ESTAC <> '" + Login.sUsrG + "'";
                st = con.createStatement();
                rs = st.executeQuery(sQ);
                //Si hay datos entonces
                while(rs.next())
                {
                    //Comprueba si el usuario esta conectado
                    int iRes    = Star.iUsrCon(con, rs.getString("estac"));
                    
                    //Si hubo error entonces regresa
                    if(iRes==-1)
                        return;
                    
                    //Si el mensaje es 1 entonces
                    if(iRes==1)
                    {
                        /*Agregalo a la tabla*/
                        Object nu[]         = {iContFi, rs.getString("logestac.SUCU"), rs.getString("logestac.NOCAJ"), rs.getString("logestac.ESTAC"), rs.getString("estacs.NOM")};
                        te.addRow(nu);
                        
                        /*Aumentar en uno el contador de pesos*/
                        ++iContFi;                
                    }                                               
                }
            }
            catch(SQLException expnSQL)
            {
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                                                                                                            
            }            
            
            /*Dueme el thread 10 segundos*/
            try
            {
                Thread.sleep(10000);
            }
            catch(InterruptedException expnInterru)
            {                
                //Cierra la base de datos y sal
                Star.iCierrBas(con);                    
                break;
            }
            
        }/*Fin de while(true)*/                                
        
    }/*Fin de private void vCone()*/
    
        
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

    
    /*Cuando se presiona una  tecla en la tabla*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Llama al recolector de basura*/
        System.gc();
        
        /*Interrumpe los threads*/        
        if(th!=null)
            th.interrupt();           
        if(thRMic!=null)
            thRMic.interrupt();
        
        /*Cierra el socket de recepción de audio*/
        if(socRAud!=null)
        {
            socRAud.close();                        
        }
        
        /*Cierra la forma*/
        this.dispose();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón de reporte*/
    private void jBRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBRepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBRepKeyPressed

    
    /*Cuando se presiona el botón de reporte*/
    private void jBRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRepActionPerformed

        /*Muestra la forma para reportear las estacs*/
        RepEstac c = new RepEstac();
        c.setVisible(true);
        
    }//GEN-LAST:event_jBRepActionPerformed

    
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
        // TODO add your handling code here:
    }//GEN-LAST:event_jLAyuMouseEntered

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando se presiona una tecla en el botón de remoto*/
    private void jBRemotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBRemotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBRemotKeyPressed

    
    /*Cuando se presiona el botón de remoto*/
    private void jBRemotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemotActionPerformed
        
        /*Si no se a seleccionado ningún usuario de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un usuario primero.", "Remoto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }

        //Comprueba si el usuario esta conectado
        int iRes    = Star.iUsrCon(null, jTab.getValueAt(jTab.getSelectedRow(), 3).toString().trim());

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si no esta conectado entonces
        if(iRes==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El usuario: " + jTab.getValueAt(jTab.getSelectedRow(), 3) + " no esta conectado actualmente.", "Video Llamada", JOptionPane.INFORMATION_MESSAGE, null);                                                                                   
            return;
        }
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Obtiene el puerto del usuario
        String sPort    = Star.sGetPortUsr(con, jTab.getValueAt(jTab.getSelectedRow(), 3).toString().trim());        
        
        //Si hubo error entonces regresa
        if(sPort==null)
            return;
        
        //Obtiene el host del usuario
        String sHost    = Star.sGetHostUsr(con, jTab.getValueAt(jTab.getSelectedRow(), 3).toString().trim());
        
        //Si hubo error entonces regresa
        if(sHost==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Muestra la forma que estara mostrando las imágenes*/
        ImgRemo i = new ImgRemo(sHost, sPort, jTab.getValueAt(jTab.getSelectedRow(), 3).toString());
        i.setVisible(true);
        
    }//GEN-LAST:event_jBRemotActionPerformed

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBRepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRepMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBRep.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBRepMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBRemotMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRemotMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBRemot.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBRemotMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBRepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRepMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBRep.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBRepMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBRemotMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRemotMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBRemot.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBRemotMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se presiona una tecla en el botó de sacar*/
    private void jBSacKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSacKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSacKeyPressed

    
    /*Cuando el mouse entra en el botón de sacar*/
    private void jBSacMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSacMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSac.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSacMouseEntered

    
    /*Cuando el mouse sale del botón de sacar*/
    private void jBSacMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSacMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSac.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSacMouseExited

    
    /*Cuando se presiona el botón de sacar*/
    private void jBSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSacActionPerformed
        
        /*Si no se a seleccionado ningún usuario de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un usuario primero.", "Sacar del sistema", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }

        /*Preguntar al usuario si esta seguro de quere sarcar a el o los usuarios*/
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres sacar del sistema al/los usuario(s)?", "Sacar del sistema", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, null, op, op[0])) == JOptionPane.NO_OPTION)
            return;                                           
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Recorre toda la selección del usuario*/
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        int iSel[]              = jTab.getSelectedRows();                
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            //Obtiene el puerto del usuario
            String sPort    = Star.sGetPortUsr(con, jTab.getValueAt(iSel[x], 3).toString().trim());        

            //Si hubo error entonces regresa
            if(sPort==null)
                return;

            //Obtiene el host del usuario
            String sHost    = Star.sGetHostUsr(con, jTab.getValueAt(iSel[x], 3).toString().trim());

            //Si hubo error entonces regresa
            if(sHost==null)
                return;

            /*Declara variables final para el thread*/
            final String sHostFi    = sHost;
            final String sPortFi    = sPort;

            /*Thread para mandar las peticiones de que se desconecten*/
            (new Thread()
            {
                @Override
                public void run()
                {
                    /*Conecta con el servidor*/                    
                    try
                    {
                        socSaca = new java.net.Socket(sHostFi, Integer.parseInt(sPortFi));
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                        return;                                                                                                                                                                 
                    }                    

                    /*Declara objetos para poder mandarle mensaje al servidor*/
                    java.io.OutputStream        outS;
                    java.io.DataOutputStream    out;
                    try
                    {
                        outS   = socSaca.getOutputStream();
                        out= new java.io.DataOutputStream(outS);
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                        return;                                                                                                                                                                 
                    }

                    /*Manda mensaje al servidor*/
                    try
                    {
                        out.writeUTF("exit");
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                        return;                                                                                                                                                                 
                    }

                    /*Cerra el socket*/
                    try
                    {
                        socSaca.close();
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                               
                    }                                    

                }/*Fin de public void run()*/
            }).start();
            
            /*Borra el registro del usuario en la tabla*/                                    
            tm.removeRow(iSel[x]);
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/                    
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jBSacActionPerformed

    
    /*Cuando el mouse entra en el botón de seleccionar todos los registros de la tabla*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered

        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);

    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando el mouse sale del botón de seleccionar todos los registros de la tabla*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);

    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando se presiona el botón de seleccionar todos los registros en la tabla*/
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

    
    /*Cuando se presiona una tecla en el botón de mostrar todo*/
    private void jBTodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTodKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTodKeyPressed

    
    /*Cuando se presiona el botón de recibir audio*/
    private void jBObtAudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBObtAudActionPerformed
        
        /*Si no se a seleccionado ningún usuario de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un usuario primero.", "Remoto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }

        //Comprueba si el usuario esta conectado
        int iRes    = Star.iUsrCon(null, jTab.getValueAt(jTab.getSelectedRow(), 3).toString().trim());

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si no esta conectado entonces
        if(iRes==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El usuario: " + jTab.getValueAt(jTab.getSelectedRow(), 3) + " no esta conectado actualmente.", "Video Llamada", JOptionPane.INFORMATION_MESSAGE, null);                                                                                   
            return;
        }
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Obtiene el puerto del usuario
        String sPort    = Star.sGetPortUsr(con, jTab.getValueAt(jTab.getSelectedRow(), 3).toString().trim());        

        //Si hubo error entonces regresa
        if(sPort==null)
            return;

        //Obtiene el host del usuario
        String sHost    = Star.sGetHostUsr(con, jTab.getValueAt(jTab.getSelectedRow(), 3).toString().trim());

        //Si hubo error entonces regresa
        if(sHost==null)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Declara variables final para el thread*/
        final String sHostFi    = sHost;
        final String sPortFi    = sPort;
        
        /*Thread para recibir audio del micro del host y reproducirlo por las bocinas*/
        thRMic   = new Thread()
        {                
            @Override
            public void run()
            {    
                /*Inicia el objeto de conexión UDP*/                
                try
                {
                    socRAud = new java.net.DatagramSocket();
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                                                                                                                         
                }                    

                /*Crea el host para mandar el paquete*/                
                java.net.InetAddress iaAdd;
                try
                {
                    iaAdd = java.net.InetAddress.getByName(sHostFi);
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                                                                                                                         
                }                    
                
                /*Crea el mensaje a enviar*/
                byte[] bDat = "audrecib".getBytes();                
                
                /*Crea el objeto para enviar el mensaje*/
                java.net.DatagramPacket dgPacE = new java.net.DatagramPacket(bDat, bDat.length, iaAdd, Integer.parseInt(sPortFi));
                
                /*Envia el paquete por el socket*/
                try
                {
                    socRAud.send(dgPacE);
                }
                catch(Exception expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                       
                    return;                                                                                                                                                             
                }
                                
                /*Objeto para recibir paquetes del servidor*/
                byte bReci[] = new byte[1600];                
                java.net.DatagramPacket dgPacR = new java.net.DatagramPacket(bReci, bReci.length);        
                
                /*Búcle inifinito para estar reciviendo audio del servidor*/
                while(true)
                {                                               
                    /*Obtiene el paquete del socket*/                    
                    try
                    {
                        socRAud.receive(dgPacR);                        
                    }
                    catch(IOException expnIO)
                    {
                        /*Regresa*/
                        return;
                    }
                    
                    /*Crea el audioinputstream*/
                    javax.sound.sampled.AudioInputStream aud = new javax.sound.sampled.AudioInputStream(new java.io.ByteArrayInputStream(bReci), Star.format, bReci.length);
                    try
                    {
                        javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
                        clip.open(aud);
                        clip.start();        
                        while(clip.getMicrosecondLength() != clip.getMicrosecondPosition())
                        {
                        }
                    }
                    catch(LineUnavailableException expnLinUnav)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnLinUnav.getMessage(), Star.sErrLinUnav, expnLinUnav.getStackTrace());                                                       
                        return;                                                                                                                                         
                    }
                    catch(IOException expnIO)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                        
                        return;                                                                                                                                         
                    }                                              
                                    
                }/*Fin de while(true)*/                                    
            }
        };
        thRMic.start();
        
    }//GEN-LAST:event_jBObtAudActionPerformed

    
    /*Cuando se presiona el botón de recibir video de la cámara del usuario*/
    private void jBObtVidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBObtVidActionPerformed
        
        /*Si no se a seleccionado ningún usuario de la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un usuario primero.", "Remoto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }

        //Comprueba si el usuario esta conectado
        int iRes    = Star.iUsrCon(null, jTab.getValueAt(jTab.getSelectedRow(), 3).toString().trim());

        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si no esta conectado entonces
        if(iRes==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El usuario: " + jTab.getValueAt(jTab.getSelectedRow(), 3) + " no esta conectado actualmente.", "Video Llamada", JOptionPane.INFORMATION_MESSAGE, null);                                                                                   
            return;
        }
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Obtiene el puerto del usuario
        String sPort    = Star.sGetPortUsr(con, jTab.getValueAt(jTab.getSelectedRow(), 3).toString().trim());        

        //Si hubo error entonces regresa
        if(sPort==null)
            return;

        //Obtiene el host del usuario
        String sHost    = Star.sGetHostUsr(con, jTab.getValueAt(jTab.getSelectedRow(), 3).toString().trim());

        //Si hubo error entonces regresa
        if(sHost==null)
            return;

        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Abre la forma para recibir de la cámara*/
        ImgCamUsr i = new ImgCamUsr(sPort, sHost);
        i.setVisible(true);
        
    }//GEN-LAST:event_jBObtVidActionPerformed

    
    /*Cuando se presiona una tecla en el botón de obtener video*/
    private void jBObtVidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBObtVidKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBObtVidKeyPressed

    
    /*Cuando el mouse entra en el botón de obtener video*/
    private void jBObtVidMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBObtVidMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBObtVid.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBObtVidMouseEntered

    
    /*Cuando el mouse entra en el botón de obtener audio*/
    private void jBObtAudMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBObtAudMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBObtAud.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBObtAudMouseEntered

    
    /*Cuando se presiona una tecla en el botón de obtener audio*/
    private void jBObtAudKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBObtAudKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBObtAudKeyPressed

    
    /*Cuando el mouse sal del botón de obtener video*/
    private void jBObtVidMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBObtVidMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBObtVid.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBObtVidMouseExited

    
    /*Cuando el mouse sale del botón de obtener audio*/
    private void jBObtAudMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBObtAudMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBObtAud.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBObtAudMouseExited

       
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + R entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_R)
            jBRep.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBObtAud;
    private javax.swing.JButton jBObtVid;
    private javax.swing.JButton jBRemot;
    private javax.swing.JButton jBRep;
    private javax.swing.JButton jBSac;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
