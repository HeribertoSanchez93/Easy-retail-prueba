//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
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
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Clase para controlar el log de los correos*/
public class LogCorrs extends javax.swing.JFrame 
{
    /*Declara variables de instancia*/        
    private int             iContFi;

    /*Variable que contiene el borde actual*/
    private Border          bBordOri;
    
    /*Contador para modificar tabla*/
    private int             iContCellEd;
    
    /*Declara las variables originales*/
    private String          sDeOri;
    private String          sAOri;
    private String          sNoDocOri;
    private String          sTipDocOri;
    private String          sEstadOri;
    private String          sMotivOri;
    private String          sFAltOri;
    private String          sSucOri;
    private String          sCajOri;
    private String          sEstacOri;
    private String          sNomEstacOri;    
    
    
    
    /*Constructor sin argumentos*/
    public LogCorrs() 
    {        
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo del ventana con El usuario, la fecha y hora*/                
        this.setTitle("Log correos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);                        
        
        /*Inicializa el contador de filas en uno*/
        iContFi      = 1;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en la tabla de correos*/
        jTab.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de correos*/        
        jTab.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(160);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(160);                
        jTab.getColumnModel().getColumn(3).setPreferredWidth(120);                
        jTab.getColumnModel().getColumn(4).setPreferredWidth(120);                
        jTab.getColumnModel().getColumn(5).setPreferredWidth(59);                
        jTab.getColumnModel().getColumn(6).setPreferredWidth(160);                
        jTab.getColumnModel().getColumn(7).setPreferredWidth(120);                        
        jTab.getColumnModel().getColumn(9).setPreferredWidth(59);                
        jTab.getColumnModel().getColumn(10).setPreferredWidth(55);                
        jTab.getColumnModel().getColumn(11).setPreferredWidth(130);                
                
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Obtén de la base de datos todos los log de los correos y cargalos en la tabla*/
        vCargCorr();                    
        
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
                        sDeOri          = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sAOri           = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sNoDocOri       = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sTipDocOri      = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        sEstadOri       = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sMotivOri       = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sFAltOri        = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sSucOri         = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();
                        sCajOri         = jTab.getValueAt(jTab.getSelectedRow(), 9).toString();
                        sEstacOri       = jTab.getValueAt(jTab.getSelectedRow(), 10).toString();
                        sNomEstacOri    = jTab.getValueAt(jTab.getSelectedRow(), 11).toString();
                                                
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sDeOri,         jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sAOri,          jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sNoDocOri,      jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sTipDocOri,     jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sEstadOri,      jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sMotivOri,      jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sFAltOri,       jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sSucOri,        jTab.getSelectedRow(), 8);                        
                        jTab.setValueAt(sCajOri,        jTab.getSelectedRow(), 9);                        
                        jTab.setValueAt(sEstacOri,      jTab.getSelectedRow(), 10); 
                        jTab.setValueAt(sNomEstacOri,   jTab.getSelectedRow(), 11);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public Lugares() */

    
    /*Obtén de la base de datos todos los correos y cargalos en la tabla*/
    private void vCargCorr()
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel tm = (DefaultTableModel)jTab.getModel();                    

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los registros de la base de datos y cargalos en la tabla*/
        try
        {
            sQ = "SELECT logcorrs.CORRDE, logcorrs.CORR, logcorrs.NODOC, logcorrs.TIPDOC, logcorrs.MOTIV, logcorrs.ESTAD, logcorrs.FALT,logcorrs.SUCU, logcorrs.NOCAJ, logcorrs.NOCAJ, logcorrs.ESTAC, estacs.NOM FROM logcorrs LEFT OUTER JOIN estacs ON estacs.ESTAC = logcorrs.ESTAC ORDER BY logcorrs.ID_ID DESC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[]= {iContFi, rs.getString("logcorrs.CORRDE"), rs.getString("logcorrs.CORR"), rs.getString("logcorrs.NODOC"), rs.getString("logcorrs.TIPDOC"), rs.getString("logcorrs.ESTAD"), rs.getString("logcorrs.MOTIV"), rs.getString("logcorrs.FALT"), rs.getString("logcorrs.SUCU"), rs.getString("logcorrs.NOCAJ"), rs.getString("logcorrs.ESTAC"), rs.getString("estacs.NOM")};
                tm.addRow(nu);
                
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
        
        //Cierra la base de datos
        Star.iCierrBas(con);            
                
    }/*Fin de private void vCargaLugaresEnTabla()*/
    
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMostT = new javax.swing.JButton();
        jBLimp = new javax.swing.JButton();
        jBActua = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBRep = new javax.swing.JButton();

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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 120, 120, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Log de correos:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 160, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "De", "A", "No Documento", "Tipo Documento", "Estado", "Motivo", "Fecha envio", "Sucursal", "No.Caja", "Usuario", "Nombre usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jBBusc);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 890, 240));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBBusc.setForeground(new java.awt.Color(0, 102, 0));
        jBBusc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/busc5.png"))); // NOI18N
        jBBusc.setText("Buscar F3");
        jBBusc.setNextFocusableComponent(jTBusc);
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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 140, 20));

        jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBusc.setNextFocusableComponent(jBMostT);
        jTBusc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBuscFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTBuscFocusLost(evt);
            }
        });
        jTBusc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscKeyPressed(evt);
            }
        });
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 610, 20));

        jBMostT.setBackground(new java.awt.Color(255, 255, 255));
        jBMostT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMostT.setForeground(new java.awt.Color(0, 102, 0));
        jBMostT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMostT.setText("Mostrar F4");
        jBMostT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMostT.setNextFocusableComponent(jBLimp);
        jBMostT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMostTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMostTMouseExited(evt);
            }
        });
        jBMostT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMostTActionPerformed(evt);
            }
        });
        jBMostT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMostTKeyPressed(evt);
            }
        });
        jP1.add(jBMostT, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 270, 140, 20));

        jBLimp.setBackground(new java.awt.Color(255, 255, 255));
        jBLimp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBLimp.setForeground(new java.awt.Color(0, 102, 0));
        jBLimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/limp.png"))); // NOI18N
        jBLimp.setText("Limpiar");
        jBLimp.setToolTipText("Limpiar el LOG de Correos Enviados");
        jBLimp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBLimp.setNextFocusableComponent(jBActua);
        jBLimp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBLimpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBLimpMouseExited(evt);
            }
        });
        jBLimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimpActionPerformed(evt);
            }
        });
        jBLimp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBLimpKeyPressed(evt);
            }
        });
        jP1.add(jBLimp, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 30, 120, 30));

        jBActua.setBackground(new java.awt.Color(255, 255, 255));
        jBActua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBActua.setForeground(new java.awt.Color(0, 102, 0));
        jBActua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/actualizar.png"))); // NOI18N
        jBActua.setText("Actualizar");
        jBActua.setToolTipText("Actualizar Registros (F5)");
        jBActua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBActua.setName(""); // NOI18N
        jBActua.setNextFocusableComponent(jBRep);
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
        jP1.add(jBActua, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 60, 120, 30));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(906, 280, 120, 20));

        jBRep.setBackground(new java.awt.Color(255, 255, 255));
        jBRep.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBRep.setForeground(new java.awt.Color(0, 102, 0));
        jBRep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/visor.png"))); // NOI18N
        jBRep.setText("Reporte");
        jBRep.setToolTipText("Reporte de Clientes (F9)");
        jBRep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBRep.setNextFocusableComponent(jBSal);
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
        jP1.add(jBRep, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 90, 120, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
               
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Salir de la forma*/
        this.dispose();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una  tecla en la tabla de correos*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed
        
    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de buscar*/
    private void jTBuscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTBusc.setSelectionStart(0);jTBusc.setSelectionEnd(jTBusc.getText().length());
        
    }//GEN-LAST:event_jTBuscFocusGained

    
    /*Cuando se presiona una tecla en el campo de buscar*/
    private void jTBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscKeyPressed

        /*Si se presionó enter entonces presiona el botón de búsqueda y regresa*/
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            jBBusc.doClick();
            return;
        }
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTBuscKeyPressed

    
    /*Cuando se presiona una tecla en el botón de mostrar todo*/
    private void jBMostTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMostTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMostTKeyPressed

    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMostTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMostTActionPerformed
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtén de la base de datos los logs de correos y cargalos en la tabla*/
        vCargCorr();
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBMostTActionPerformed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed
                
        /*Si el campo de buscar esta vacio no puede seguir*/
        if(jTBusc.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de búsqueda esta vacio.", "Campo Vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de búsqueda y regresa*/
            jTBusc.grabFocus();           
            return;
        }                      
                    
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Borra todos los item en la tabla de correos*/
        DefaultTableModel dm    = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi                 = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todos los logs de correos*/        
        try
        {                  
            sQ = "SELECT logcorrs.CORRDE, logcorrs.CORR, logcorrs.TIPDOC, logcorrs.NODOC, logcorrs.MOTIV, logcorrs.ESTAD, logcorrs.FALT, logcorrs.SUCU, logcorrs.NOCAJ, logcorrs.ESTAC, estacs.NOM FROM logcorrs LEFT OUTER JOIN estacs ON estacs.ESTAC = logcorrs.ESTAC WHERE corr LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR logcorrs.NODOC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR estacs.NOM LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR logcorrs.ESTAD LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR logcorrs.MOTIV LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR logcorrs.ESTAC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR logcorrs.FALT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR logcorrs.TIPDOC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR logcorrs.CORRDE LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("logcorrs.CORRDE"), rs.getString("logcorrs.CORR"), rs.getString("logcorrs.NODOC"), rs.getString("logcorrs.TIPDOC"), rs.getString("logcorrs.ESTAD"), rs.getString("logcorrs.MOTIV"), rs.getString("logcorrs.FALT"), rs.getString("logcorrs.SUCU"), rs.getString("logcorrs.NOCAJ"), rs.getString("logcorrs.ESTAC"), rs.getString("estacs.NOM")};
                tm.addRow(nu);

                /*Aumenta en uno el contador de filas*/
                ++iContFi;                                       
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

        /*Vuelve a poner el foco del teclado en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se presiona el botón de limpiar*/
    private void jBLimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimpActionPerformed
                        
        /*Preguntar al usuario si esta seguro de querer limpiar el log*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres limpiar los registros?", "Limpiar Log", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        /*Borra todos los registros de la tabla de logcorrs*/
        try 
        {            
            sQ = "DELETE FROM logcorrs";                    
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

        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Mensaje de éxito*/
        JOptionPane.showMessageDialog(null, "Registros borrados con éxito.", "Registros Borrados", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        
    }//GEN-LAST:event_jBLimpActionPerformed

    
    /*Cuando se presiona una tecla en el botón de limpiar*/
    private void jBLimpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBLimpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBLimpKeyPressed

    
    /*Cuando se presiona el botón de actualizar*/
    private void jBActuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActuaActionPerformed
        
        /*Borra todos los items de la tabla de correos*/                
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Reinicia el contador de filas*/
        iContFi = 1;
        
        /*Obtén de la base de datos todos los log de los correos y cargalos en la tabla*/
        vCargCorr();                    
        
    }//GEN-LAST:event_jBActuaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de actualizar*/
    private void jBActuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBActuaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBActuaKeyPressed

    
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

    
    /*Cuando el mouse entra en el botón de búscar*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);
        
        /*Guardar el borde original que tiene el botón*/
        bBordOri    = jBBusc.getBorder();
                
        /*Aumenta el grosor del botón*/
        jBBusc.setBorder(new LineBorder(Color.GREEN, 3));
        
    }//GEN-LAST:event_jBBuscMouseEntered

    
    /*Cuando el mouse sale del botón de búscar*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(Star.colOri);
        
        /*Coloca el borde que tenía*/
        jBBusc.setBorder(bBordOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
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
    private void jBLimpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimpMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBLimp.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBLimpMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBActuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBActua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBActuaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMostTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMostTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMostT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMostTMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMostTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMostTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMostT.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBMostTMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBLimpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBLimpMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBLimp.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBLimpMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBActuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBActuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBActua.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBActuaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));

    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando el mouse entra en el botón de reporte*/
    private void jBRepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRepMouseEntered

        /*Cambia el color del fondo del botón*/
        jBRep.setBackground(Star.colBot);

    }//GEN-LAST:event_jBRepMouseEntered

    
    /*Cuando el mouse sale del botón de reporte*/
    private void jBRepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRepMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBRep.setBackground(Star.colOri);

    }//GEN-LAST:event_jBRepMouseExited

    
    /*Cuando se presiona el botón de reporte*/
    private void jBRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRepActionPerformed

        /*Muestra la forma para ver el reporte de los logs de correos*/
        report.RepLogCor r = new report.RepLogCor();
        r.setVisible(true);
        
    }//GEN-LAST:event_jBRepActionPerformed

    
    /*Cuando se presiona una tecla en el botón de reporte*/
    private void jBRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBRepKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBRepKeyPressed

    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona F3 presiona el botón de bùscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMostT.doClick();
        /*Si se presiona F5 presiona el botón de actualizar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F5)
            jBActua.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBActua;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBLimp;
    private javax.swing.JButton jBMostT;
    private javax.swing.JButton jBRep;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
