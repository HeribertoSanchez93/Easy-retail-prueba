//Paquete
package cats;

//Importaciones
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.awt.Cursor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ptovta.Star;
import ptovta.Login;
import static ptovta.Princip.bIdle;




/*Clase para controlar los componentes de los kits*/
public class Comps extends javax.swing.JFrame 
{
    /*Declara variables de instancia*/    
    private int             iContCellEd;
    private int             iContFi;      
        
    /*Variable que contiene el borde actual*/
    private Border          bBordOri;
    
    /*Variables originales de la tabla*/
    private String          sKitOri;
    private String          sProdOri;    
    private String          sCantOri;
    private String          sDescripOri;
    
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean         bSel;
    
    
    
    
    /*Constructor sin argumentos*/
    public Comps( String sCodK, String sDescrip) 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
            
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Que las comlumnas de la tabla no se muevan*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Coloca en su lugar el código del kit*/
        jTKit.setText                (sCodK);
        jTKit.setCaretPosition       (0);
        
        /*Coloca en su lugar la descripción del kit*/
        jTDescripKit.setText            (sDescrip);
        jTDescripKit.setCaretPosition   (0);
        
        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Kits, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Pon el foco del teclado en el campo del producto*/
        jTProd.grabFocus();
        
        /*Iincializa el contador de filas en uno*/
        iContFi      = 1;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Establece el tamaño de las columnas de la tabla de monedas*/        
        jTab.getColumnModel().getColumn(4).setPreferredWidth(200);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;                
        
        /*Obtiene de la base de datos todos los componentes del kit y cargalos en la tabla*/
        vCargComps();
                    
        /*Crea el listener para la tabla de componentes, para cuando se modifique una celda guardarlo en la base de datos el cambio*/
        PropertyChangeListener pr = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces regresa*/                        
                if(jTab.getSelectedRow()==-1)
                    return;

                /*Declara variables de bloque*/
                String      sProd           = "";                
                String      sDescrip        = "";
                String      sKit            = "";   
                String      sCant           = "";                                                
                
                //Declara variables de la base de datos    
                Statement   st;                                
                String      sQ;
                               
                /*Obtén la propiedad que a sucedio en el control*/
                String pro = event.getPropertyName();                                
                                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(pro)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {                        
                        sKitOri             = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                          
                        sProdOri            = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                                                                                 
                        sCantOri            = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                          
                        sDescripOri         = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                          
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {                        
                        /*Obtén algunos datos de la fila*/                        
                        sKit                = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                  
                        sProd               = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();                        
                        sCant               = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                
                        sDescrip            = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                
                        
                        /*Coloca los valores originales en su lugar*/
                        jTab.setValueAt(sKitOri,    jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sProdOri,   jTab.getSelectedRow(), 2);
                        jTab.setValueAt(sCantOri,   jTab.getSelectedRow(), 3);
                        jTab.setValueAt(sDescripOri,jTab.getSelectedRow(), 4);                        
                    }
                        
                    //Abre la base de datos                             
                    Connection  con = Star.conAbrBas(false, false);

                    //Si hubo error entonces regresa
                    if(con==null)
                        return;

                    /*Si el contador está en 2 entonces el cell editor va de salida*/
                    if(iContCellEd >= 2)  
                    {
                        /*Si el campo de cantidad es cadena vacia entonces*/
                        if(sCant.compareTo("")==0)
                        {
                            //Cierra la base de datos
                            if(Star.iCierrBas(con)==-1)
                                return;
            
                            /*Pon la cantidad que tenía originalmente y regresa por que no puede continuar*/
                            jTab.getModel().setValueAt(sCantOri, jTab.getSelectedRow(), 3);
                            
                            /*Reinicia el conteo y regresa*/
                            iContCellEd = 1;                                                        
                            return;                        
                        }
                        
                        /*Si los carácteres introducidos no se puede convertir a double entonces coloca el valor original*/
                        try  
                        {  
                            double d = Double.parseDouble(sCant);  
                        }  
                        catch(NumberFormatException expnNumForm)  
                        {  
                            //Cierra la base de datos
                            if(Star.iCierrBas(con)==-1)
                                return;
                            
                            /*Pon el valor original*/
                            jTab.getModel().setValueAt(sCantOri, jTab.getSelectedRow(), 3);
                            
                            /*Reinicia el conteo y regresa*/
                            iContCellEd = 1;                                                        
                            return;
                        }    
                        
                        /*Si la cantidad es 0 entonces*/
                        if(Integer.parseInt(sCant)==0)
                        {
                            //Cierra la base de datos
                            if(Star.iCierrBas(con)==-1)
                                return;
                            
                            /*Pon la cantidad que tenía oroginalmente y regresa por que no puede continuar*/
                            jTab.getModel().setValueAt(sCantOri, jTab.getSelectedRow(), 3);
                            
                            /*Reinicia el conteo y regresa*/
                            iContCellEd = 1;                                                        
                            return;                        
                        }
                        
                        /*Conviertelo a su valor absoluto en caso de que sea negativo*/
                        sCant   = Integer.toString(Math.abs(Integer.parseInt(sCant)));
                        
                        /*Colocalo nuevamente en la tabla ya con el valor absoluto*/
                        jTab.getModel().setValueAt(sCant, jTab.getSelectedRow(), 3);

                        //Inicia la transacción
                        if(Star.iIniTransCon(con)==-1)
                            return;

                        /*Actualiza en la base de datos la cant del componente por el kit*/
                        try 
                        {                                                        
                            sQ = "UPDATE kits SET "
                                    + "cant         = " + sCant + ", "
                                    + "fmod         = now(), "
                                    + "estac        = '" + Login.sUsrG.replace("'", "''") + "', "
                                    + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                                    + "nocaj        = '" + Star.sNoCaj.replace("'", "''") + "' "
                                    + "WHERE codkit = '" + sKit.replace("'", "''") + "' AND prod = '" + sProd.replace("'", "''") + "'";                                                
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                             //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                            return;                        
                         }                                                                                     
                    
                        /*Inserta en log de kits*/
                        try 
                        {            
                            sQ = "INSERT INTO logkit(  kit,                                 prod,                                   descrip,                                accio,                estac,                                            sucu,                                               nocaj,                                      falt) " + 
                                          "VALUES('" +  sKit.replace("'", "''") + "','" +   sProd.replace("'", "''") + "',  '" +    sDescrip.replace("'", "''") + "',       'MODIFICAR', '" +     Login.sUsrG.replace("'", "''") + "','" +      Star.sSucu.replace("'", "''") + "','" +       Star.sNoCaj.replace("'", "''") + "',  now())";                                
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                            return;                        
                        }

                        //Termina la transacción
                        if(Star.iTermTransCon(con)==-1)
                            return;

                        //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return;
                        
                        /*Reinicia el conteo*/
                        iContCellEd = 1;
                        
                    }/*Fin de if(iContCellEd >= 2)  */                        
                    else
                        ++iContCellEd;                                                                                
                    
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */
            
        };
        
        /*Establece el listener para la tabla de monedas*/
        jTab.addPropertyChangeListener(pr);
        
    }/*Fin de public Monedas() */

    
    /*Obtiene de la base de datos todos los componentes del kit y cargalas en la tabla*/
    private void vCargComps()
    {
        /*Obtiene el código del kit del campo*/
        String sKit = jTKit.getText();
        
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
    
        /*Trae todos los componentes de la base de datos y cargalos en la tabla*/        
        try
        {
            sQ = "SELECT kits.PROD, cant, descrip, kits.ALMA FROM kits LEFT OUTER JOIN prods ON CONCAT_WS('', kits.ALMA, kits.PROD) = CONCAT_WS('', prods.ALMA, prods.PROD) WHERE codkit = '" + sKit + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalos a la tabla*/
                Object nu[]         = {iContFi, sKit, rs.getString("prod"), rs.getString("cant"), rs.getString("descrip")};
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
        Star.iCierrBas(con);            
        
    }/*Fin de private void vObtenMonedasCargaEnTabla()*/
            
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBDel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jTDescrip = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMostT = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTCant = new javax.swing.JTextField();
        jTKit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTDescripKit = new javax.swing.JTextField();
        jTProd = new javax.swing.JTextField();
        jBBusc1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jBTabG = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();

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
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/del5.png"))); // NOI18N
        jBDel.setText("Borrar");
        jBDel.setToolTipText("Borrar Componente(s) (Ctrl+SUPR)");
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
        jP1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, 110, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Descripción del kit:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 270, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jTProd);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 210, 110, 30));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nuevo Componente (Ctrl+N)");
        jBNew.setNextFocusableComponent(jTCant);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 110, 20));

        jTDescrip.setEditable(false);
        jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescrip.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripFocusLost(evt);
            }
        });
        jTDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripKeyPressed(evt);
            }
        });
        jP1.add(jTDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 270, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Componentes:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 160, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Cod. Kit:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 80, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cod. Kit", "Cod. Producto", "Cantidad", "Descripción Producto"
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
        jTab.setNextFocusableComponent(jBBusc);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTab);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 640, 250));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 140, 20));

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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 430, 360, 20));

        jBMostT.setBackground(new java.awt.Color(255, 255, 255));
        jBMostT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMostT.setForeground(new java.awt.Color(0, 102, 0));
        jBMostT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMostT.setText("Mostrar F4");
        jBMostT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMostT.setNextFocusableComponent(jBDel);
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
        jP1.add(jBMostT, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 430, 140, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("*Producto:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        jTCant.setText("1");
        jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCant.setNextFocusableComponent(jTab);
        jTCant.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCantFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCantFocusLost(evt);
            }
        });
        jTCant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCantKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCantKeyTyped(evt);
            }
        });
        jP1.add(jTCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 90, 20));

        jTKit.setEditable(false);
        jTKit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTKit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTKitFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTKitFocusLost(evt);
            }
        });
        jTKit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTKitKeyPressed(evt);
            }
        });
        jP1.add(jTKit, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 90, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("*Cantidad:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 80, -1));

        jTDescripKit.setEditable(false);
        jTDescripKit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripKit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripKitFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripKitFocusLost(evt);
            }
        });
        jTDescripKit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripKitKeyPressed(evt);
            }
        });
        jP1.add(jTDescripKit, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 270, 20));

        jTProd.setBackground(new java.awt.Color(255, 255, 153));
        jTProd.setToolTipText("Ctrl+B búsqueda avanzada");
        jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProd.setNextFocusableComponent(jBBusc1);
        jTProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProdFocusLost(evt);
            }
        });
        jTProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProdKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTProdKeyTyped(evt);
            }
        });
        jP1.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 170, 20));

        jBBusc1.setBackground(new java.awt.Color(255, 255, 255));
        jBBusc1.setText("...");
        jBBusc1.setToolTipText("Buscar Producto(s)");
        jBBusc1.setNextFocusableComponent(jBNew);
        jBBusc1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBusc1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBusc1MouseExited(evt);
            }
        });
        jBBusc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBusc1ActionPerformed(evt);
            }
        });
        jBBusc1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBusc1KeyPressed(evt);
            }
        });
        jP1.add(jBBusc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 30, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Descripción del producto:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 270, -1));

        jBTabG.setBackground(new java.awt.Color(0, 153, 153));
        jBTabG.setToolTipText("Mostrar Tabla en Grande");
        jBTabG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTabGActionPerformed(evt);
            }
        });
        jBTabG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTabGKeyPressed(evt);
            }
        });
        jP1.add(jBTabG, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(656, 450, 120, -1));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar Todo");
        jBTod.setToolTipText("Marcar Todos los Registros en la Tabla (Alt+T)");
        jBTod.setNextFocusableComponent(jTab);
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 162, 130, 18));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona el botón de borrar*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed
        
        /*Si no hay selección en la tabla no puede seguir*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "No has seleccionado componente(s) de la tabla para borrar.", "Borrar Componente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();            
            return;            
        }
        
        /*Preguntar al usuario si esta seguro de querer borrar*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quiere borrar el componente?", "Borrar Componente", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;        
        String      sQ;        
        
        /*Recorre toda la selección del usuario*/
        int iSel[]              = jTab.getSelectedRows();
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        for(int x = iSel.length - 1; x >= 0; x--)
        {
            /*Borra el componente*/
            try 
            {            
                sQ = "DELETE FROM kits WHERE codkit = '" + jTab.getValueAt(x, 1).toString() + "' AND prod = '" + jTab.getValueAt(x, 2).toString() + "'";                               
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                        
            }                        

            /*Inserta en log de kits*/
            try 
            {            
                sQ = "INSERT INTO logkit(  kit,                                                                 prod,                                                                   descrip,                                                        accio,            estac,                                        sucu,                                     nocaj,                                    falt) " + 
                              "VALUES('" +  jTab.getValueAt(iSel[x], 1).toString().replace("'", "''") + "','" + jTab.getValueAt(iSel[x], 2).toString().replace("'", "''") + "',  '" +   jTab.getValueAt(iSel[x], 4).toString().replace("'", "''") + "', 'BORRAR', '" +    Login.sUsrG.replace("'", "''") + "','" +      Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',      now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                return;                        
            }
            
            /*Borralo de la tabla*/            
            tm.removeRow(iSel[x]);
            
            /*Resta en uno el contador de filas el contador de filas en uno*/
            --iContFi;
            
        }/*Fin de for(int x = iSel.length - 1; x >= 0; x--)*/
                    
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Componente(s) borrado(s) con éxito.", "Componentes", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                
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
        
        /*Pregunta al usuario si esta seguro de salir*/                
        Object[] op = {"Si","No"};
        if((JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0])) == JOptionPane.YES_OPTION)
        {
            /*Llama al recolector de basura y cierra la forma*/
            System.gc();                    
            this.dispose();            
        }
        
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

    
    /*Cuando se presiona una tecla en el campo de descripción de moneda*/
    private void jTDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripKeyPressed

    
    /*Cuando se presiona una  tecla en la tabla de componentes*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de edición de texto de descripción de la moneda*/
    private void jTDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescrip.setSelectionStart(0);jTDescrip.setSelectionEnd(jTDescrip.getText().length());                
        
    }//GEN-LAST:event_jTDescripFocusGained
           
    
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
        
        /*Si hay cadena vacia en el campo del código del producto no puede continuar*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código del producto esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTProd.grabFocus();                        
            return;            
        }
                      
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        //Comprueba si el producto existe en la base de datos                
        int iRes    = Star.iExistProd(con, jTProd.getText().trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el producto no existe entonces
        if(iRes==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El producto: " + jTProd.getText() + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Pon el foco del teclado en el control y regresa*/
            jTProd.grabFocus();               
            return;                            
        }
        
        /*Si hay cadena vacia en el campo la cantidad no puede continuar*/
        if(jTCant.getText().compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca el borde rojo*/                               
            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La cantidad esta vacia.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCant.grabFocus();                        
            return;            
        }
        
        /*Si la cantidad es cero no puede continuar*/
        if(Integer.parseInt(jTCant.getText())==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
                        
            /*Coloca el borde rojo*/                               
            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La cantidad es 0.", "Cantidad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCant.grabFocus();                        
            return;            
        }                
        
        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        iRes        = JOptionPane.showOptionDialog(this, "¿Seguro que quieres agregar el componente?", "Agregar componente", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Regresa*/
            return;
        }
        
        /*Checa si el código del producto ya existe en la base de datos de kits*/        
        try
        {
            sQ = "SELECT prod FROM kits WHERE prod = '" + jTProd.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe*/
            if(rs.next())
            {
                /*Aumenta la cantidad en la base de datos*/            
                try 
                {                
                    sQ = "UPDATE kits SET "
                            + "cant         = cant + " + jTCant.getText() + ", "
                            + "sucu         = '" + Star.sSucu.replace("'", "''") + "', "
                            + "nocaj        = '"  + Star.sNoCaj.replace("'", "''") + "' "
                            + "WHERE codkit = '" + jTKit.getText().replace("'", "''") + "' AND prod = '" + jTProd.getText().replace("'", "''").trim() + "'";                                        
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                 catch(SQLException expnSQL) 
                 { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
                    return;                        
                 }
                
                /*Recorre toda la tabla de componentes en búsca del kit y el producto*/
                for(int x = 0; x < jTab.getRowCount(); x++)
                {
                    /*Si en la fila actual esta el código del producto entonces actualiza la cantidad en la fila*/
                    if(jTab.getModel().getValueAt(x, 2).toString().compareTo(jTProd.getText().trim())==0)
                        jTab.setValueAt(Integer.parseInt(jTCant.getText().trim()) + Integer.parseInt(jTab.getValueAt(x, 3).toString()), x, 3);
                }

                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                            
                /*Mensaje de éxito*/
                JOptionPane.showMessageDialog(null, "Producto: " + jTProd.getText().trim() + " agregado con éxito.", "Componente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Regresa para que ya no continue*/
                return;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }                       
    
        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;
        
        /*Guarda los datos en la base de datos*/
        try 
        {            
            sQ = "INSERT INTO kits(    codkit,                                                prod,                                           estac,                                  falt,           cant,                  alma,       sucu,                                     nocaj) " + 
                         "VALUES('" +  jTKit.getText().replace("'", "''") + "', '" +         jTProd.getText().replace("'", "''") + "', '" +   Login.sUsrG.replace("'", "''") + "',    now(), " +      jTCant.getText() + ",  '','" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";            
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
         }
        
        /*Actualiza que ese producto es un kit y que no es inventariable*/
        try 
        {            
            sQ = "UPDATE prods SET "
                    + "compue       = 1,"
                    + "invent       = 0 "
                    + "WHERE prod   = '" + jTKit.getText().replace("'", "''").trim() + "'";                         
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
         }
        
        /*Inserta en log de kits*/
        try 
        {            
            sQ = "INSERT INTO logkit(  kit,                                              prod,                                               descrip,                                            accio,              estac,                                      sucu,                                     nocaj,                                    falt) " + 
                          "VALUES('" +  jTKit.getText().replace("'", "''") + "','" +     jTProd.getText().replace("'", "''") + "',  '" +     jTDescrip.getText().replace("'", "''") + "',        'AGREGAR', '" +     Login.sUsrG.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',      now())";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         {              
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }
        
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
        
        /*Agrega el registro en la tabla*/
        DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {iContFi, jTKit.getText(), jTProd.getText(), jTCant.getText(), jTDescrip.getText()};
        tm.addRow(nu);
        
        /*Aumenta en uno el contador de filas en uno*/
        ++iContFi;
        
        /*Pon el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();
        
        /*Limpia los campos*/
        jTProd.setText      ("");
        jTDescrip.setText   ("");
        jTCant.setText      ("1");
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Componente: " + jTProd.getText() + " agregado con éxito para el kit: " + jTKit.getText() + ".", "Exito al agregar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
         
    }//GEN-LAST:event_jBNewActionPerformed
        
    
    /*Cuando se presiona una tecla en el botón de buscar*/
    private void jBBuscKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de buscar*/
    private void jTBuscFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusGained

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBBusc);
        
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

    
    /*Cuando se presiona el botón de buscar*/
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
        
        /*Borra todos los item en la tabla de componentes*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi              = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
                
        /*Obtiene de la base de datos todos los componentes del kit*/        
        try
        {                  
            sQ = "SELECT kits.PROD, prods.DESCRIP, kits.CANT FROM kits LEFT OUTER JOIN prods ON CONCAT_WS('', kits.ALMA, kits.PROD) = CONCAT_WS('', prods.ALMA, prods.PROD) WHERE codkit = '" + jTKit.getText() + "' AND (kits.PROD LIKE('%" + jTBusc.getText().replace(" ", "%") +  "%') OR prods.DESCRIP LIKE('%" + jTBusc.getText().replace(" ", "%") +  "%') OR kits.CANT LIKE('%" + jTBusc.getText().replace(" ", "%") +  "%') OR kits.FALT LIKE('%" + jTBusc.getText().replace(" ", "%") +  "%') OR kits.FMOD LIKE('%" + jTBusc.getText().replace(" ", "%") +  "%'))";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agrega el registro en la tabla*/
                DefaultTableModel tm    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, jTKit.getText(), rs.getString("prod"), rs.getString("cant"), rs.getString("descrip")};
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

    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMostTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMostTActionPerformed
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtiene de la base de datos todos los componentes y cargalas en la tabla*/
        vCargComps();
        
        /*Vuelve a poner el foco del teclado en el campo de buscar*/
        jTBusc.grabFocus();
        
    }//GEN-LAST:event_jBMostTActionPerformed

        
    /*Cuando se gana el foco del teclado en el campo de cant*/
    private void jTCantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCant.setSelectionStart(0);jTCant.setSelectionEnd(jTCant.getText().length());        
        
    }//GEN-LAST:event_jTCantFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de código del kit*/
    private void jTKitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTKitFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTKit.setSelectionStart(0);jTKit.setSelectionEnd(jTKit.getText().length());                
        
    }//GEN-LAST:event_jTKitFocusGained

    
    /*Cuando se presiona una tecla en el campo de la cant*/
    private void jTCantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCantKeyPressed

    
    /*Cuando se presiona una tecla en el campo del código del kit*/
    private void jTKitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTKitKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTKitKeyPressed

    
    /*Cuando se pierde el foco del teclaod en el campo de cant*/
    private void jTCantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCant.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCant.getText().compareTo("")!=0)
            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
        /*Lee el texto introducido por el usuario*/
        String sTex = jTCant.getText();
        
        /*Si es cadena vacia entonces*/
        if(sTex.compareTo("")==0)
        {
            /*Colocar 1 y regresar*/
            jTCant.setText("1");            
            return;
        }
        
        /*Si los caes introducidos no se puede convertir a double colocar cadena vacia y regresar*/
        try  
        {  
            double d = Double.parseDouble(sTex);  
        }  
        catch(NumberFormatException expnNumForm)  
        {              
            jTCant.setText("");
            return;
        }        
        
        /*Si el valor introducido es menor a 0 entonces*/
        if(Integer.parseInt(sTex)<= 0)                
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La cant debe ser mayor a 0.", "Cantidad Inválida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

            /*Coloca el foco del teclado en el control*/
            jTCant.grabFocus();
            
            /*Coloca en el campo 1*/
            jTCant.setText("1");
        }
        
    }//GEN-LAST:event_jTCantFocusLost

    
    /*Cuando se tipea una tecla en el campo de cant*/
    private void jTCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyTyped
       
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.'))         
            evt.consume();      
        
    }//GEN-LAST:event_jTCantKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de descripción del kit*/
    private void jTDescripKitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripKitFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripKit.setSelectionStart(0);jTDescripKit.setSelectionEnd(jTDescripKit.getText().length());                
        
    }//GEN-LAST:event_jTDescripKitFocusGained

    
    /*Cuando se presiona una tecla en el campo de descripción de kit*/
    private void jTDescripKitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripKitKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripKitKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de producto*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());

    }//GEN-LAST:event_jTProdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de producto*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTProd.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTProd.getText().compareTo("")!=0)
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Comprueba si el código del producto existe*/        
        try
        {
            sQ = "SELECT descrip, alma FROM prods WHERE prod <> '-' AND prod = '" + jTProd.getText() + "' LIMIT 1";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Coloca la descripción en el campo correspondiente*/
                jTDescrip.setText               (rs.getString("descrip"));                                                
                jTDescrip.setCaretPosition      (0);                                
            }
            /*Else coloca vacio el campo*/
            else
                jTDescrip.setText        ("");                                                    
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);
            return;                        
        }            
        
        //Cierra la base de datos
        Star.iCierrBas(con);            
        
    }//GEN-LAST:event_jTProdFocusLost

    
    /*Cuando se presiona una tecla en el campo de producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            ptovta.Busc b = new ptovta.Busc(this, jTProd.getText(), 30, jTProd, jTDescrip, null, "", null);
            b.setVisible(true);
        }
        /*Else if se presiono CTRL + B entonces*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_B)
        {
            /*Llama a la forma para búscar productos avanzadamente*/
            ptovta.BuscAvan v = new ptovta.BuscAvan(this, jTProd, jTDescrip, null, null);
            v.setVisible(true);
            
            /*Coloca el foco del teclado en el campo del producto*/
            jTProd.grabFocus();            
        }   
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTProdKeyPressed

    
    /*Cuando se tipea una tecla en el campo del producto*/
    private void jTProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyTyped

        /*Si el carácter escrito no es un ca imprimible regresar*/
        if(!(evt.getKeyChar() >= 32 && evt.getKeyChar() <= 126))
            return;

        /*Lee los caes introducidos anteriormente por el usuario*/
        String sCad    = jTProd.getText();

        /*Si el carácter introducido es minúscula entonces conviertelo en mayùsculas*/
        char ca         = evt.getKeyChar();
        if(Character.isLowerCase(ca))       
            ca  = Character.toUpperCase(ca);       

        /*No escribas la letra*/
        evt.consume();

        /*Si no es back space*/
        if(ca != '\b')
        {
            /*Forma la cadena*/
            sCad += Character.toString(ca);

            /*Coloca la cadena en el campo*/
            jTProd.setText              (sCad);
            jTProd.setCaretPosition     (0);
        }

    }//GEN-LAST:event_jTProdKeyTyped

    
    /*Cuando se presiona el botón de búscar coincidencia*/
    private void jBBusc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBusc1ActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        ptovta.Busc b = new ptovta.Busc(this, jTProd.getText(), 30, jTProd, jTDescrip, null, "", null);
        b.setVisible(true);

    }//GEN-LAST:event_jBBusc1ActionPerformed

    
    /*Cuando se presiona el botón de buscar*/
    private void jBBusc1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBusc1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBBusc1KeyPressed
                           
    
    /*Cuando se mueve la rueda del ratón en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando se arrastra el ratón en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve el ratón en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se presiona el botón de ver tabla*/
    private void jBTabGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTabGActionPerformed

        //Muestra la tabla maximizada
        Star.vMaxTab(jTab);       

    }//GEN-LAST:event_jBTabGActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver tabla*/
    private void jBTabGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTabGKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTabGKeyPressed

    
    /*Cuando el mouse sale del botón de búscar*/
    private void jBBuscMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseExited
        
        /*Coloca el borde que tenía*/
        jBBusc.setBorder(bBordOri);
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBBuscMouseExited

    
    /*Cuando el mouse entra en el botón de búscar*/
    private void jBBuscMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc.setBackground(Star.colBot);
        
        /*Guardar el borde original que tiene el botón*/
        bBordOri    = jBBusc.getBorder();
                
        /*Aumenta el grosor del botón*/
        jBBusc.setBorder(new LineBorder(Color.GREEN, 3));
        
    }//GEN-LAST:event_jBBuscMouseEntered

    
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

    
    /*Cuando se presiona el botón de seleccionar todo*/
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

    
    /*Cuando se presiona una tecla en el botón de seleccionar todo*/
    private void jBTodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTodKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBTodKeyPressed

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBusc1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusc1MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBusc1.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBusc1MouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTodMouseEntered

    
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

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBMostTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMostTMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBMostT.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBMostTMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBusc1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBusc1MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBusc1.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBBusc1MouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBMostTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBMostTMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBMostT.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBMostTMouseExited

    
    /*Cuando se pierde el foco del teclado en el control de bùsqueda*/
    private void jTBuscFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscFocusLost

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la descripciòn del producto*/
    private void jTDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescrip.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripFocusLost

    
    
    /*Cuando se pierde el foco del teclado en el campo del còdigo del kit*/
    private void jTKitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTKitFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTKit.setCaretPosition(0);
        
    }//GEN-LAST:event_jTKitFocusLost

    /*Cuando se pierde el foco del teclado en el campo de la descripciòn del kit*/
    private void jTDescripKitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripKitFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescripKit.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripKitFocusLost
       
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
            jBTod.doClick();
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona F3 presiona el botón de búscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMostT.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBBusc1;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBMostT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTabG;
    private javax.swing.JButton jBTod;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTCant;
    private javax.swing.JTextField jTDescrip;
    private javax.swing.JTextField jTDescripKit;
    private javax.swing.JTextField jTKit;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
