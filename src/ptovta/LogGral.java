//Paquete
package ptovta;

//Importaciones
import java.awt.Color;
import java.awt.Cursor;
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
import static ptovta.Princip.bIdle;;




/*Clase para ver distintos logs*/
public class LogGral extends javax.swing.JFrame 
{
    /*Variable que contiene el borde actual*/
    private Border          bBordOri;
    
    /*Declara variables de instancia*/    
    private int             iContFi;
                
    /*Contador para modificar tabla*/
    private int             iContCellEd;
    
    /*Declara las variables originales*/
    private String          sCodOri;
    private String          sDescripOri;
    private String          sAccioOri;
    private String          sFOri;
    private String          sSucOri;
    private String          sCajOri;
    private String          sEstacOri;
    private String          sNomEstacOri;
    
    /*Contiene el nombre de la tabla de donde se consultará*/
    private String          sTab;
    
    /*Contiene el campo*/
    private String          sCamp;
    
    
    
    
    /*Constructor*/
    public LogGral(String sTit, String sTa, String sCam) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Obtiene la tabla del otro formualario*/
        sTab    = sTa;
                
        /*Obtiene el nombre del campo*/
        sCamp   = sCam;
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle(sTit + ", Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
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

        /*Establece el tamaño de las columnas de la tabla*/        
        jTab.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(300);
        jTab.getColumnModel().getColumn(8).setPreferredWidth(200);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                

        /*Carga todos los logs*/
        vCargReg();
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Crea el listener para cuando se cambia de selección en la tabla*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces regresa*/                
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
                        sCodOri         = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
                        sDescripOri     = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sAccioOri       = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        sFOri           = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                        
                        sSucOri         = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();
                        sCajOri         = jTab.getValueAt(jTab.getSelectedRow(), 6).toString();
                        sEstacOri       = jTab.getValueAt(jTab.getSelectedRow(), 7).toString();
                        sNomEstacOri    = jTab.getValueAt(jTab.getSelectedRow(), 8).toString();                        
                        
                        /*Aumenta el contador para saber que va de salida*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Coloca los valores originales que tenian*/
                        jTab.setValueAt(sCodOri,        jTab.getSelectedRow(), 1);                        
                        jTab.setValueAt(sDescripOri,    jTab.getSelectedRow(), 2);                        
                        jTab.setValueAt(sAccioOri,      jTab.getSelectedRow(), 3);                        
                        jTab.setValueAt(sFOri,          jTab.getSelectedRow(), 4);                        
                        jTab.setValueAt(sSucOri,        jTab.getSelectedRow(), 5);                        
                        jTab.setValueAt(sCajOri,        jTab.getSelectedRow(), 6);                        
                        jTab.setValueAt(sEstacOri,      jTab.getSelectedRow(), 7);                        
                        jTab.setValueAt(sNomEstacOri,   jTab.getSelectedRow(), 8);                        
                        
                        /*Resetea el celleditor*/
                        iContCellEd = 1;
                    }                                            
                                            
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };        
        
        /*Establece el listener para la tabla*/
        jTab.addPropertyChangeListener(pro);
        
    }/*Fin de public LogGral() */
        
    
    /*Carga todos los logs en la tabla*/
    private void vCargReg()
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Declara el objeto para cargar cadenas en la tabla*/
        DefaultTableModel te    = (DefaultTableModel)jTab.getModel();        

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Obtiene todos los logs de la base de datos*/
        try
        {
            sQ = "SELECT IFNULL(estacs.NOM,'') AS nom, accio, cod, " + sCamp + ", " + sTab + ".FALT, " + sTab + ".SUCU, " + sTab + ".NOCAJ, " + sTab + ".ESTAC FROM " + sTab + " LEFT OUTER JOIN estacs ON estacs.ESTAC = " + sTab + ".ESTAC ORDER BY " + sTab + ".ID_ID DESC";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Obtiene los resultados y cargalos en la tabla*/
                Object nu[]             = {iContFi, rs.getString("cod"), rs.getString(sCamp), rs.getString("accio"), rs.getString("falt"), rs.getString("sucu"), rs.getString("nocaj"), rs.getString("estac"), rs.getString("nom")};
                te.addRow(nu);                
                
                /*Aumenta e lcontador de filas*/
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
        
    }/*Fin de private void vCargReg()*/   
    
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBRep = new javax.swing.JButton();
        jBBusc = new javax.swing.JButton();
        jBMosT = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBTab1 = new javax.swing.JButton();
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 110, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Logs:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 480, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Código", "Descripción", "Acción", "Fecha", "Sucursal", "Caja", "Usuario", "Nombre Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 490, 330));

        jBRep.setBackground(new java.awt.Color(255, 255, 255));
        jBRep.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBRep.setForeground(new java.awt.Color(0, 102, 0));
        jBRep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/visor.png"))); // NOI18N
        jBRep.setText("Reporte");
        jBRep.setToolTipText("Reporte de LOG ( Ctrl+R)");
        jBRep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBRep.setName(""); // NOI18N
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
        jP1.add(jBRep, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 110, 30));

        jBBusc.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBBusc.setForeground(new java.awt.Color(0, 102, 0));
        jBBusc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/busc5.png"))); // NOI18N
        jBBusc.setText("Buscar F3");
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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 130, 19));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setNextFocusableComponent(jBRep);
        jBMosT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBMosTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBMosTMouseExited(evt);
            }
        });
        jBMosT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMosTActionPerformed(evt);
            }
        });
        jBMosT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBMosTKeyPressed(evt);
            }
        });
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 360, 130, 19));

        jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 230, 20));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 340, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 360, 110, 20));

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

        /*Muestra la forma para reportear dependiendo el tipo*/
        if(sTab.compareTo("logprods")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logprods", "El producto", "cod", "Reporte log productos", "/jasreport/rptLogProd.jrxml", "Producto:", 2);
            c.setVisible(true);            
        }            
        else if(sTab.compareTo("loglins")==0)
        {
            report.RepLogGral c = new report.RepLogGral("loglins", "La línea", "cod", "Reporte log líneas", "/jasreport/rptLogLin.jrxml", "Línea:", 18);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logmarc")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logmarc", "La marca", "cod", "Reporte log marcas", "/jasreport/rptLogMarc.jrxml", "Marca:", 17);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logfabs")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logfabs", "El fabricante", "cod", "Reporte log fabricantes", "/jasreport/rptLogFab.jrxml", "Fabricante:", 27);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logcolo")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logcolo", "El color", "cod", "Reporte log colores", "/jasreport/rptLogColo.jrxml", "Color", 23);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logclasext")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logclasext", "La clasificación", "cod", "Reporte log clasificaciones", "/jasreport/rptLogClas.jrxml", "Clasificación", 13);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logpes")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logpes", "El peso", "cod", "Reporte log pesos", "/jasreport/rptLogPes.jrxml", "Peso:", 22);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logmed")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logmed", "La medida", "cod", "Reporte log medidas", "/jasreport/rptLogMed.jrxml", "Medida:", 25);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logunid")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logunid", "La unidad", "cod", "Reporte log unidades", "/jasreport/rptLogUnid.jrxml", "Unidad:", 21);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logmons")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logmons", "La moneda", "cod", "Reporte log monedas", "/jasreport/rptLogMon.jrxml", "Moneda", 10);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logimpue")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logimpue", "El impuesto", "cod", "Reporte log impuestos", "/jasreport/rptLogImpue.jrxml", "Impuesto:", 20);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logconcep")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logconcep", "El concepto", "cod", "Reporte log conceptos", "/jasreport/rptLogConce.jrxml", "Concepto:", 7);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logalmas")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logalmas", "El almacén", "cod", "Reporte log almacenes", "/jasreport/rptLogAlma.jrxml", "Almacén:", 11);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("loganaq")==0)
        {
            report.RepLogGral c = new report.RepLogGral("loganaq", "El anaquel", "cod", "Reporte log anaqueles", "/jasreport/rptLogAna.jrxml", "Anaquel:", 15);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("loglugs")==0)
        {
            report.RepLogGral c = new report.RepLogGral("loglugs", "El lugar", "cod", "Reporte log lugares", "/jasreport/rptLogLug.jrxml", "Lugar:", 16);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logubiad")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logubiad", "La ubicación", "cod", "Reporte log ubicaciones", "/jasreport/rptLogUbi.jrxml", "Ubicación:", 24);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logmod")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logmod", "El módelo", "cod", "Reporte log modelos", "/jasreport/rptLogMod.jrxml", "Modelo:", 29);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logtall")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logtall", "La talla", "cod", "Reporte log tallas", "/jasreport/rptLogTall.jrxml", "Talla", 41);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logtip")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logtip", "El tipo", "cod", "Reporte log tipos", "/jasreport/rptLogTip.jrxml", "Tipo:", 31);
            c.setVisible(true);            
        }        
        else if(sTab.compareTo("logactcat")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logactcat", "El activo", "cod", "Reporte log activo", "/jasreport/rptLogActCat.jrxml", "Activo:", 39);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logacttip")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logacttip", "El tipo activo", "cod", "Reporte log tipo activo", "/jasreport/rptLogActTip.jrxml", "Tipo:", 40);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logcatgral")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logcatgral", "El general", "cod", "Reporte log general", "/jasreport/rptLogGral.jrxml", "General:", 14);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logclasemp")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logclasemp", "La clasificación", "cod", "Reporte log clasificación", "/jasreport/rptLogClasEmp.jrxml", "Clasificación", 12);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logclasprov")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logclasprov", "La clasificación", "cod", "Reporte log clasificación", "/jasreport/rptLogClasProv.jrxml", "Clasificación:", 13);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logconcnot")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logconcnot", "El concepto", "cod", "Reporte log conceptos", "/jasreport/rptLogConcNot.jrxml", "Concepto", 38);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("loggara")==0)
        {
            report.RepLogGral c = new report.RepLogGral("loggara", "La garantía", "cod", "Reporte log garantías", "/jasreport/rptLogGaran.jrxml", "Garantía:", 37);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logtip")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logtip", "El tipo", "cod", "Reporte log tipos", "/jasreport/rptLogTip.jrxml", "Tipo:", 31);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("loggiro")==0)
        {
            report.RepLogGral c = new report.RepLogGral("loggiro", "El giro", "cod", "Reporte log giros", "/jasreport/rptLogGir.jrxml", "Giro:", 33);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logzona")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logzona", "La zona", "cod", "Reporte log zonas", "/jasreport/rptLogZon.jrxml", "Zona:", 32);
            c.setVisible(true);            
        }
        else if(sTab.compareTo("logconcpag")==0)
        {
            report.RepLogGral c = new report.RepLogGral("logconcpag", "El concepto", "cod", "Reporte log conceptos", "/jasreport/rptLogConcPag.jrxml", "Zona:", 32);
            c.setVisible(true);            
        }
        
    }//GEN-LAST:event_jBRepActionPerformed

    
    /*Cuando se presiona el botón de búscar*/
    private void jBBuscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscActionPerformed

        /*Si el campo de buscar esta vacio no puede seguir*/
        if(jTBusc.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo de búsqueda esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el foco del teclado en el campo de búsqueda y regresa*/
            jTBusc.grabFocus();           
            return;
        }
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);

        /*Resetea el contador de filas*/
        iContFi                 = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene de la base de datos todos los registros*/
        try
        {
            sQ = "SELECT cod, " + sCamp + ", accio, " + sTab + ".FALT, " + sTab + ".SUCU, " + sTab + ".NOCAJ, " + sTab + ".ESTAC, estacs.NOM FROM " + sTab + " LEFT OUTER JOIN estacs ON estacs.ESTAC = " + sTab + ".ESTAC WHERE cod LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR estacs.NOM LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR " + sCamp + " LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR accio LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR " + sTab + ".ESTAC LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR " + sTab + ".SUCU LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR " + sTab + ".NOCAJ LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR " + sTab + ".FALT LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Cargalo en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("cod"), rs.getString(sCamp), rs.getString("accio"), rs.getString("falt"), rs.getString("sucu"), rs.getString("nocaj"), rs.getString("estac"), rs.getString("nom")};
                te.addRow(nu);

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

        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();

    }//GEN-LAST:event_jBBuscActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de búsqueda*/
    private void jTBuscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTBusc.setSelectionStart(0);jTBusc.setSelectionEnd(jTBusc.getText().length());

    }//GEN-LAST:event_jTBuscFocusGained

    
    /*Cuando se presiona una tecla en el campo de búscar*/
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

    
    /*Cuando se presona el botón de mostrar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed

        /*Borra todos los item en la tabla*/
        DefaultTableModel dm    = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);

        /*Resetea el contador de filas*/
        iContFi                 = 1;

        /*Agrega todos los registros logs de la base de datos en la tabla*/
        vCargReg();

        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();

    }//GEN-LAST:event_jBMosTActionPerformed

    
    /*Cuando se presiona una tecla en el botón de mostrar todo*/
    private void jBMosTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMosTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBMosTKeyPressed

    
    /*Cuando se presioan el botón de ver tabla*/
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
        
        /*Coloca el borde que tenía*/
        jBBusc.setBorder(bBordOri);
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(Star.colOri);
        
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
    private void jBRepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRepMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBRep.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBRepMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMosTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMosT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMosTMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBRepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRepMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBRep.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBRepMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMosTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMosTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMosT.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBMosTMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

       
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
        /*Else if se presiona F3 entonces presiona el botón de búscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();        
        /*Else if se presiona F4 entonces presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBMosT;
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
