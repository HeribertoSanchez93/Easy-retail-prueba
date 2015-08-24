//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;




/*Clase para ver los precios y costeos de un producto*/
public class LPrecsVis extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color  colOri;
    
    
    
    /*Constructor sin argumentos*/
    public LPrecsVis(String sProd) 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Listas de precios, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
                        
        /*Pon el foco del teclado en el botón de salir*/
        jBSal.grabFocus();
        
        /*Obtén de la base de datos las listas de precio de este producto*/
        vCargPrec(sProd);
                            
    }/*Fin de public LPrecsVis() */

    
    /*Obtén de la base de datos las listas de precio y cargalas en los campos*/
    private void vCargPrec(String sProd)
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Obtiene el UEPS*/
        String sUeps    = Star.sGetUEPS(con, sProd);            
        
        /*Obtiene el PEPS del producto*/           
        String sPeps    = Star.sGetPEPS(con, sProd);
        
        /*Obtiene el costo promedio*/
        String sCostP   = Star.sCostProm(con, sProd);            
            
        /*Obtiene el último costo*/
        String sUltCost = Star.sUltCost(con, sProd);            
        
        /*Dales formato de moneda a todos los costeos*/                
        NumberFormat nu = NumberFormat.getCurrencyInstance(Locale.US);
        double dCant    = Double.parseDouble(sUeps);                
        sUeps           = nu.format(dCant);
        dCant           = Double.parseDouble(sPeps);                
        sPeps           = nu.format(dCant);
        dCant           = Double.parseDouble(sCostP);                
        sCostP          = nu.format(dCant);
        dCant           = Double.parseDouble(sUltCost);                
        sUltCost        = nu.format(dCant);
        
        /*Colocalos en su lugar*/
        jTCost.setText  (sUltCost);
        jTCostP.setText (sCostP);
        jTUEPS.setText  (sUeps);
        jTPEPS.setText  (sPeps);                                

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Obtiene algunos datos del producto*/
        try
        {
            sQ = "SELECT (SELECT IFNULL(costpro,0) AS costp FROM partcomprs WHERE prod = '" + sProd + "' LIMIT 1) AS costp, IFNULL(cost,0) AS cost, IFNULL(prelist1, 0 ) AS prelist1, IFNULL(CASE WHEN (((IFNULL(prelist1, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist1, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por1, IFNULL(prelist2, 0 ) AS prelist2, IFNULL(CASE WHEN (((IFNULL(prelist2, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist2, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por2, IFNULL(prelist3, 0 ) AS prelist3, IFNULL(CASE WHEN (((IFNULL(prelist3, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist3, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por3, IFNULL(prelist4, 0 ) AS prelist4, IFNULL(CASE WHEN (((IFNULL(prelist4, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist4, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100)  END, 0 ) AS por4, IFNULL(prelist5, 0 ) AS prelist5, IFNULL(CASE WHEN (((IFNULL(prelist5, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist5, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 END, 0 ) AS por5,  IFNULL(prelist6, 0 ) AS prelist6, IFNULL(CASE WHEN (((IFNULL(prelist6, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist6, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por6, IFNULL(prelist7, 0 ) AS prelist7, IFNULL(CASE WHEN (((IFNULL(prelist7, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist7, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por7, IFNULL(prelist8, 0 ) AS prelist8, IFNULL(CASE WHEN (((IFNULL(prelist8, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist8, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100)  END, 0 ) AS por8, IFNULL(prelist9, 0 ) AS prelist9, IFNULL(CASE WHEN (((IFNULL(prelist9, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist9, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por9, IFNULL(prelist10, 0 ) AS prelist10, IFNULL(CASE WHEN  (((IFNULL(prelist10, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) < 0 THEN 0 ELSE (((IFNULL(prelist10, 0) * 100 ) / IFNULL(cost, 0 ) ) - 100) END, 0 ) AS por10 FROM prods WHERE prod = '" + sProd + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Carga todas las listas de precios en los campos*/
                String sPre1    = rs.getString("prelist1");
                String sPre2    = rs.getString("prelist2");
                String sPre3    = rs.getString("prelist3");
                String sPre4    = rs.getString("prelist4");
                String sPre5    = rs.getString("prelist5");
                String sPre6    = rs.getString("prelist6");
                String sPre7    = rs.getString("prelist7");
                String sPre8    = rs.getString("prelist8");
                String sPre9    = rs.getString("prelist9");
                String sPre10   = rs.getString("prelist10");
                
                /*Dale formato de moneda a los precios*/                
                NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
                dCant           = Double.parseDouble(sPre1);                                
                sPre1           = n.format(dCant);
                dCant           = Double.parseDouble(sPre2);                                
                sPre2           = n.format(dCant);
                dCant           = Double.parseDouble(sPre3);                                
                sPre3           = n.format(dCant);
                dCant           = Double.parseDouble(sPre4);                                
                sPre4           = n.format(dCant);
                dCant           = Double.parseDouble(sPre5);                                
                sPre5           = n.format(dCant);
                dCant           = Double.parseDouble(sPre6);                                
                sPre6           = n.format(dCant);
                dCant           = Double.parseDouble(sPre7);                                
                sPre7           = n.format(dCant);
                dCant           = Double.parseDouble(sPre8);                                
                sPre8           = n.format(dCant);
                dCant           = Double.parseDouble(sPre9);                                
                sPre9           = n.format(dCant);
                dCant           = Double.parseDouble(sPre10);                                
                sPre10          = n.format(dCant);

                jTPre1.setText(sPre1);
                jTPre2.setText(sPre2);
                jTPre3.setText(sPre3);
                jTPre4.setText(sPre4);
                jTPre5.setText(sPre5);
                jTPre6.setText(sPre6);
                jTPre7.setText(sPre7);
                jTPre8.setText(sPre8);
                jTPre9.setText(sPre9);
                jTPre10.setText(sPre10);
                
                /*Carga todas las utilidades del producto*/
                jTUti1.setText(rs.getString("por1"));
                jTUti2.setText(rs.getString("por2"));
                jTUti3.setText(rs.getString("por3"));
                jTUti4.setText(rs.getString("por4"));
                jTUti5.setText(rs.getString("por5"));
                jTUti6.setText(rs.getString("por6"));
                jTUti7.setText(rs.getString("por7"));
                jTUti8.setText(rs.getString("por8"));
                jTUti9.setText(rs.getString("por9"));
                jTUti10.setText(rs.getString("por10"));                                                    
            }
            /*Else, no existe el producto entonces*/
            else
            {
                /*Coloca todos los campos en 0 pesos*/
                jTCost.setText("$0.00");                                
                jTPre1.setText("$0.00");
                jTPre2.setText("$0.00");
                jTPre3.setText("$0.00");
                jTPre4.setText("$0.00");
                jTPre5.setText("$0.00");
                jTPre6.setText("$0.00");
                jTPre7.setText("$0.00");
                jTPre8.setText("$0.00");
                jTPre9.setText("$0.00");
                jTPre10.setText("$0.00");                
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
        
    }/*Fin de private void vCargPrec()*/
            
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel14 = new javax.swing.JLabel();
        jP1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jTPre1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTPre3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTPre5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTPre7 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTPre9 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTPre2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTPre4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTPre6 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTPre8 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTPre10 = new javax.swing.JTextField();
        jTCost = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTUti10 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTUti1 = new javax.swing.JTextField();
        jTUti2 = new javax.swing.JTextField();
        jTUti3 = new javax.swing.JTextField();
        jTUti4 = new javax.swing.JTextField();
        jTUti5 = new javax.swing.JTextField();
        jTUti6 = new javax.swing.JTextField();
        jTUti7 = new javax.swing.JTextField();
        jTUti8 = new javax.swing.JTextField();
        jTUti9 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLAyu = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTCostP = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTUEPS = new javax.swing.JTextField();
        jTPEPS = new javax.swing.JTextField();

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Utilidad %:");

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Utilidad %:");
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 110, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jBSal);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 500, 110, 30));

        jTPre1.setEditable(false);
        jTPre1.setText("$0.00");
        jTPre1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre1.setNextFocusableComponent(jTUti1);
        jTPre1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre1FocusLost(evt);
            }
        });
        jTPre1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre1KeyPressed(evt);
            }
        });
        jP1.add(jTPre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 150, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Precio 3");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 150, -1));

        jTPre3.setEditable(false);
        jTPre3.setText("$0.00");
        jTPre3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre3.setNextFocusableComponent(jTUti3);
        jTPre3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre3FocusLost(evt);
            }
        });
        jTPre3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre3KeyPressed(evt);
            }
        });
        jP1.add(jTPre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 150, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Precio 5");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 150, -1));

        jTPre5.setEditable(false);
        jTPre5.setText("$0.00");
        jTPre5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre5.setNextFocusableComponent(jTUti5);
        jTPre5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre5FocusLost(evt);
            }
        });
        jTPre5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre5KeyPressed(evt);
            }
        });
        jP1.add(jTPre5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 150, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Precio 7");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 150, -1));

        jTPre7.setEditable(false);
        jTPre7.setText("$0.00");
        jTPre7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre7.setNextFocusableComponent(jTUti7);
        jTPre7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre7FocusLost(evt);
            }
        });
        jTPre7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre7KeyPressed(evt);
            }
        });
        jP1.add(jTPre7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 150, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Precio 9");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 150, -1));

        jTPre9.setEditable(false);
        jTPre9.setText("$0.00");
        jTPre9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre9.setNextFocusableComponent(jTUti9);
        jTPre9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre9FocusLost(evt);
            }
        });
        jTPre9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre9KeyPressed(evt);
            }
        });
        jP1.add(jTPre9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 150, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Precio 2");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 150, -1));

        jTPre2.setEditable(false);
        jTPre2.setText("$0.00");
        jTPre2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre2.setNextFocusableComponent(jTUti2);
        jTPre2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre2FocusLost(evt);
            }
        });
        jTPre2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre2KeyPressed(evt);
            }
        });
        jP1.add(jTPre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 150, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Precio 4");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 150, -1));

        jTPre4.setEditable(false);
        jTPre4.setText("$0.00");
        jTPre4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre4.setNextFocusableComponent(jTUti4);
        jTPre4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre4FocusLost(evt);
            }
        });
        jTPre4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre4KeyPressed(evt);
            }
        });
        jP1.add(jTPre4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 150, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Precio 6");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 150, -1));

        jTPre6.setEditable(false);
        jTPre6.setText("$0.00");
        jTPre6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre6.setNextFocusableComponent(jTUti6);
        jTPre6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre6FocusLost(evt);
            }
        });
        jTPre6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre6KeyPressed(evt);
            }
        });
        jP1.add(jTPre6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 150, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Precio 8");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 150, -1));

        jTPre8.setEditable(false);
        jTPre8.setText("$0.00");
        jTPre8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre8.setNextFocusableComponent(jTUti8);
        jTPre8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre8FocusLost(evt);
            }
        });
        jTPre8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre8KeyPressed(evt);
            }
        });
        jP1.add(jTPre8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 150, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Precio 10");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 150, -1));

        jTPre10.setEditable(false);
        jTPre10.setText("$0.00");
        jTPre10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre10.setNextFocusableComponent(jTUti10);
        jTPre10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre10FocusLost(evt);
            }
        });
        jTPre10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre10KeyPressed(evt);
            }
        });
        jP1.add(jTPre10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 150, 20));

        jTCost.setEditable(false);
        jTCost.setText("$0.00");
        jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCost.setNextFocusableComponent(jTPre1);
        jTCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCostFocusLost(evt);
            }
        });
        jTCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCostKeyPressed(evt);
            }
        });
        jP1.add(jTCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 150, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Precio 1");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 150, -1));

        jTUti10.setEditable(false);
        jTUti10.setText("0");
        jTUti10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti10FocusLost(evt);
            }
        });
        jTUti10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti10KeyPressed(evt);
            }
        });
        jP1.add(jTUti10, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 470, 130, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Costo Promedio:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 130, -1));

        jTUti1.setEditable(false);
        jTUti1.setText("0");
        jTUti1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti1.setNextFocusableComponent(jTPre2);
        jTUti1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti1FocusLost(evt);
            }
        });
        jTUti1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti1KeyPressed(evt);
            }
        });
        jP1.add(jTUti1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 130, 20));

        jTUti2.setEditable(false);
        jTUti2.setText("0");
        jTUti2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti2.setNextFocusableComponent(jTPre3);
        jTUti2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti2FocusLost(evt);
            }
        });
        jTUti2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti2KeyPressed(evt);
            }
        });
        jP1.add(jTUti2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 130, 20));

        jTUti3.setEditable(false);
        jTUti3.setText("0");
        jTUti3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti3.setNextFocusableComponent(jTPre4);
        jTUti3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti3FocusLost(evt);
            }
        });
        jTUti3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti3KeyPressed(evt);
            }
        });
        jP1.add(jTUti3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 130, 20));

        jTUti4.setEditable(false);
        jTUti4.setText("0");
        jTUti4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti4.setNextFocusableComponent(jTPre5);
        jTUti4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti4FocusLost(evt);
            }
        });
        jTUti4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti4KeyPressed(evt);
            }
        });
        jP1.add(jTUti4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 130, 20));

        jTUti5.setEditable(false);
        jTUti5.setText("0");
        jTUti5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti5.setNextFocusableComponent(jTPre6);
        jTUti5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti5FocusLost(evt);
            }
        });
        jTUti5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti5KeyPressed(evt);
            }
        });
        jP1.add(jTUti5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 130, 20));

        jTUti6.setEditable(false);
        jTUti6.setText("0");
        jTUti6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti6.setNextFocusableComponent(jTPre7);
        jTUti6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti6FocusLost(evt);
            }
        });
        jTUti6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti6KeyPressed(evt);
            }
        });
        jP1.add(jTUti6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 130, 20));

        jTUti7.setEditable(false);
        jTUti7.setText("0");
        jTUti7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti7.setNextFocusableComponent(jTPre8);
        jTUti7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti7FocusLost(evt);
            }
        });
        jTUti7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti7KeyPressed(evt);
            }
        });
        jP1.add(jTUti7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 130, 20));

        jTUti8.setEditable(false);
        jTUti8.setText("0");
        jTUti8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti8.setNextFocusableComponent(jTPre9);
        jTUti8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti8FocusLost(evt);
            }
        });
        jTUti8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti8KeyPressed(evt);
            }
        });
        jP1.add(jTUti8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 130, 20));

        jTUti9.setEditable(false);
        jTUti9.setText("0");
        jTUti9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti9.setNextFocusableComponent(jTPre10);
        jTUti9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti9FocusLost(evt);
            }
        });
        jTUti9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti9KeyPressed(evt);
            }
        });
        jP1.add(jTUti9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 430, 130, 20));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Utilidad %:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 450, 110, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Utilidad %:");
        jP1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 110, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Utilidad %:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 110, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Utilidad %:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 110, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Utilidad %:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 110, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Utilidad %:");
        jP1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 110, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Utilidad %:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 110, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Utilidad %:");
        jP1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 370, 110, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Utilidad %:");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 110, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 540, 220, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("UEPS:");
        jP1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 130, -1));

        jTCostP.setEditable(false);
        jTCostP.setText("$0.00");
        jTCostP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCostP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCostPFocusLost(evt);
            }
        });
        jP1.add(jTCostP, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 130, 20));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Ultimo Costo:");
        jP1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("Ultimo Costo:");
        jP1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("PEPS:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 150, -1));

        jTUEPS.setEditable(false);
        jTUEPS.setText("$0.00");
        jTUEPS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUEPS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUEPSFocusLost(evt);
            }
        });
        jTUEPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUEPSKeyPressed(evt);
            }
        });
        jP1.add(jTUEPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 130, 20));

        jTPEPS.setEditable(false);
        jTPEPS.setText("$0.00");
        jTPEPS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPEPS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPEPSFocusLost(evt);
            }
        });
        jTPEPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPEPSKeyPressed(evt);
            }
        });
        jP1.add(jTPEPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 150, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
               
    
    /*Cuando se presiona una tecla en el campo de precio 1*/
    private void jTPre1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTPre1KeyPressed
       
    
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

            
    /*Cuando se presiona una tecla en el campo del precio 3*/
    private void jTPre3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre3KeyPressed
               
    
    /*Cuando se presiona una tecla en el campo del precio 5*/
    private void jTPre5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre5KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre5KeyPressed
               
    
    /*Cuando se presiona una tecla en el campo del precio 7*/
    private void jTPre7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre7KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre7KeyPressed
    
                
    /*Cuando se presiona una tecla en el campo del precio 9*/
    private void jTPre9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre9KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre9KeyPressed

                   
    /*Cuando se presiona una tecla en el campo del precio 2*/
    private void jTPre2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre2KeyPressed

                       
    /*Cuando se presiona una tecla en el campo del precio 4*/
    private void jTPre4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre4KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre4KeyPressed

                    
    /*Cuando se presiona una tecla en el campo del precio 6*/
    private void jTPre6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre6KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre6KeyPressed

                    
    /*Cuando se presiona una tecla en el campo del precio 8*/
    private void jTPre8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre8KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre8KeyPressed

                    
    /*Cuando se presiona una tecla en el campo del precio 10*/
    private void jTPre10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre10KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre10KeyPressed
    
                                            
    /*Cuando se preisona una tecla en el campo del cost*/
    private void jTCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCostKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCostKeyPressed

    
    /*Cuando se presiona una tecla en el campo de utilidad 1*/
    private void jTUti1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUti1KeyPressed

    
    /*Cuando se presiona una tecla en el campo de la utilidad 2*/
    private void jTUti2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUti2KeyPressed

    
    /*Cuando se presiona una tecla en el campo de utilidad 3*/
    private void jTUti3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUti3KeyPressed

    
    /*Cuando se presiona una tecla en el campo de utilidad 4*/
    private void jTUti4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti4KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUti4KeyPressed

    
    /*Cuando se presiona una tecla en el campo de utilidad 5*/
    private void jTUti5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti5KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUti5KeyPressed

    
    /*Cuando se presiona una tecla en el campo de utilidad 6*/
    private void jTUti6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti6KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUti6KeyPressed

    
    /*Cando se presiona una tecla en el campo de utildiad 7*/
    private void jTUti7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti7KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUti7KeyPressed

    
    /*Cuando se presiona una tecla en le campo de utildiad 8*/
    private void jTUti8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti8KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
                
    }//GEN-LAST:event_jTUti8KeyPressed

    
    /*Cuando se presiona una tecla en el campo de utildiad 9*/
    private void jTUti9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti9KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUti9KeyPressed

    
    /*Cuando se presiona una tecla en el campo de utildiad 10*/
    private void jTUti10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti10KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUti10KeyPressed

                          
    /*Cuando se mueve la rueda del mouse en la forma*/
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseWheelMoved

    
    /*Cuando el mouse se arrastra en la forma*/
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged

        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseDragged

    
    /*Cuando se mueve el mouse en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
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
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered
    
    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited
           
    
    /*Cuando se presiona una tecla en el campo del PEPS*/
    private void jTPEPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPEPSKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPEPSKeyPressed

    
    /*Cuando se presiona una tecla en el campo del UEPS*/
    private void jTUEPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUEPSKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUEPSKeyPressed

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCostFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCost.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCostFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPEPSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPEPSFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPEPS.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPEPSFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPre1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre1FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre1.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPre1FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPre2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre2.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPre2FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPre3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre3FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre3.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPre3FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPre4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre4FocusLost
       
        /*Coloca el caret en la posiciòn 0*/
        jTPre4.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPre4FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPre5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre5FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre5.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPre5FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPre6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre6FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre6.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPre6FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPre7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre7FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre7.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPre7FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPre8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre8FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre8.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPre8FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPre9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre9FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre9.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPre9FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPre10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre10FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre10.setCaretPosition(0);
        
    }//GEN-LAST:event_jTPre10FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTCostPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostPFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCost.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCostPFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUEPSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUEPSFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUEPS.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUEPSFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUti1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti1FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUti1.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUti1FocusLost


    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUti2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUti2.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUti2FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUti3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti3FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUti3.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUti3FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUti4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti4FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUti4.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUti4FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUti5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti5FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUti5.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUti5FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUti6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti6FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUti6.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUti6FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUti7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti7FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUti7.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUti7FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUti8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti8FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUti8.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUti8FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUti9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti9FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUti9.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUti9FocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUti10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti10FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUti10.setCaretPosition(0);
        
    }//GEN-LAST:event_jTUti10FocusLost
        
            
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBSal;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTCost;
    private javax.swing.JTextField jTCostP;
    private javax.swing.JTextField jTPEPS;
    private javax.swing.JTextField jTPre1;
    private javax.swing.JTextField jTPre10;
    private javax.swing.JTextField jTPre2;
    private javax.swing.JTextField jTPre3;
    private javax.swing.JTextField jTPre4;
    private javax.swing.JTextField jTPre5;
    private javax.swing.JTextField jTPre6;
    private javax.swing.JTextField jTPre7;
    private javax.swing.JTextField jTPre8;
    private javax.swing.JTextField jTPre9;
    private javax.swing.JTextField jTUEPS;
    private javax.swing.JTextField jTUti1;
    private javax.swing.JTextField jTUti10;
    private javax.swing.JTextField jTUti2;
    private javax.swing.JTextField jTUti3;
    private javax.swing.JTextField jTUti4;
    private javax.swing.JTextField jTUti5;
    private javax.swing.JTextField jTUti6;
    private javax.swing.JTextField jTUti7;
    private javax.swing.JTextField jTUti8;
    private javax.swing.JTextField jTUti9;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
