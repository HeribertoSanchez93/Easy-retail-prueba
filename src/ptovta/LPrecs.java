//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Locale;




/*Clase para controlar las listas de precios*/
public class LPrecs extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Contiene la referencia al otro formulario para poder acceder a sus variables*/
    private final cats.Prods        prods;
    
    //Contiene el producto
    private final String            sProdG;
    
    //Contiene el costo del producto en su método de costeo correspondiente
    private String                  sCostProdG;
    
    
    
    
    /*Constructor sin argumentos*/
    public LPrecs(String sProd, String sCodAlma, boolean bActCos, cats.Prods prod) 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
    
        //Obtiene el producto del otro formulario
        sProdG  = sProd;
        
        //Obtiene el método de costeo del producto
        String sMetCost = Star.sGetMetCostProd(null, sProdG);
                
        //Obtiene el costo del producto en base a su método de costeo
        if(sMetCost.compareTo("ultcost")==0)
            sCostProdG  = Star.sUltCost(null, sProd);
        else if(sMetCost.compareTo("prom")==0)
            sCostProdG  = Star.sCostProm(null, sProd);
        else if(sMetCost.compareTo("peps")==0)
            sCostProdG  = Star.sGetPEPS(null, sProd);
        else if(sMetCost.compareTo("ueps")==0)
            sCostProdG  = Star.sGetUEPS(null, sProd);
                
        /*Obtiene la referencia del otro forulario*/
        prods   = prod;
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGuar);
        
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
                        
        /*Si se tiene que habilitar el campo del último costo entonces habilitalo*/
        if(bActCos)
            jTCost.setEditable(false);
        else
            jTCost.setEditable(true);
        
        /*Pon el foco del teclado en el primer control*/
        jTPre1.grabFocus();
        
        /*Colocalos en su lugar*/
        jTPre1.setText  (prods.sPre1);
        jTPre2.setText  (prods.sPre2);
        jTPre3.setText  (prods.sPre3);
        jTPre4.setText  (prods.sPre4);
        jTPre5.setText  (prods.sPre5);
        jTPre6.setText  (prods.sPre6);
        jTPre7.setText  (prods.sPre7);
        jTPre8.setText  (prods.sPre8);
        jTPre9.setText  (prods.sPre9);
        jTPre10.setText (prods.sPre10);
        jTCostL.setText (prods.sCostL);

        /*Carga todas las utilidades del producto*/
        jTUti1.setText(prods.sUtil1);
        jTUti2.setText(prods.sUtil2);
        jTUti3.setText(prods.sUtil3);
        jTUti4.setText(prods.sUtil4);
        jTUti5.setText(prods.sUtil5);
        jTUti6.setText(prods.sUtil6);
        jTUti7.setText(prods.sUtil7);
        jTUti8.setText(prods.sUtil8);
        jTUti9.setText(prods.sUtil9);
        jTUti10.setText(prods.sUtil10);    
        
        /*Carga todas las utilidades de venta del producto*/
        jTUtil1V.setText(prods.sUtil1V);
        jTUtil2V.setText(prods.sUtil2V);
        jTUtil3V.setText(prods.sUtil3V);
        jTUtil4V.setText(prods.sUtil4V);
        jTUtil5V.setText(prods.sUtil5V);
        jTUtil6V.setText(prods.sUtil6V);
        jTUtil7V.setText(prods.sUtil7V);
        jTUtil8V.setText(prods.sUtil8V);
        jTUtil9V.setText(prods.sUtil9V);
        jTUtil10V.setText(prods.sUtil10V);    
        
        /*Caega el UEPS y PEPS*/
        jTPEPS.setText(prods.sUeps);    
        jTUEPS.setText(prods.sPeps);    
        
        /*Carga el último costo y el costo promedio*/
        jTCostP.setText (prods.sCostP);
        jTCost.setText  (prods.sUltCost);
        
    }/*Fin de public LPrecs() */

        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel14 = new javax.swing.JLabel();
        jP1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBSal = new javax.swing.JButton();
        jTPre1 = new javax.swing.JTextField();
        jBGuar = new javax.swing.JButton();
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
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTUEPS = new javax.swing.JTextField();
        jTPEPS = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTCostL = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTUtil1V = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTUtil2V = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jTUtil3V = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTUtil4V = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jTUtil5V = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTUtil6V = new javax.swing.JTextField();
        jTUtil7V = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jTUtil8V = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jTUtil9V = new javax.swing.JTextField();
        jTUtil10V = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();

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
        jP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 130, -1));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jTCost);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 540, 110, -1));

        jTPre1.setText("$0.00");
        jTPre1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre1.setNextFocusableComponent(jTUti1);
        jTPre1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPre1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre1FocusLost(evt);
            }
        });
        jTPre1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPre1KeyTyped(evt);
            }
        });
        jP1.add(jTPre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 150, 20));

        jBGuar.setBackground(new java.awt.Color(255, 255, 255));
        jBGuar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGuar.setForeground(new java.awt.Color(0, 102, 0));
        jBGuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGuar.setText("Guardar");
        jBGuar.setToolTipText("Guardar (Ctrl+G)");
        jBGuar.setNextFocusableComponent(jBSal);
        jBGuar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGuarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGuarMouseExited(evt);
            }
        });
        jBGuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuarActionPerformed(evt);
            }
        });
        jBGuar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGuarKeyPressed(evt);
            }
        });
        jP1.add(jBGuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 540, 110, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Precio 3");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 150, -1));

        jTPre3.setText("$0.00");
        jTPre3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre3.setNextFocusableComponent(jTUti3);
        jTPre3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPre3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre3FocusLost(evt);
            }
        });
        jTPre3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre3KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPre3KeyTyped(evt);
            }
        });
        jP1.add(jTPre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 150, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Precio 5");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 150, -1));

        jTPre5.setText("$0.00");
        jTPre5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre5.setNextFocusableComponent(jTUti5);
        jTPre5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPre5FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre5FocusLost(evt);
            }
        });
        jTPre5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre5KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPre5KeyTyped(evt);
            }
        });
        jP1.add(jTPre5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 150, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Precio 7");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 150, -1));

        jTPre7.setText("$0.00");
        jTPre7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre7.setNextFocusableComponent(jTUti7);
        jTPre7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPre7FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre7FocusLost(evt);
            }
        });
        jTPre7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre7KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPre7KeyTyped(evt);
            }
        });
        jP1.add(jTPre7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 150, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Precio 9");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 150, -1));

        jTPre9.setText("$0.00");
        jTPre9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre9.setNextFocusableComponent(jTUti9);
        jTPre9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPre9FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre9FocusLost(evt);
            }
        });
        jTPre9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre9KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPre9KeyTyped(evt);
            }
        });
        jP1.add(jTPre9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 150, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Precio 2");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 150, -1));

        jTPre2.setText("$0.00");
        jTPre2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre2.setNextFocusableComponent(jTUti2);
        jTPre2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPre2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre2FocusLost(evt);
            }
        });
        jTPre2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPre2KeyTyped(evt);
            }
        });
        jP1.add(jTPre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 150, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Precio 4");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 150, -1));

        jTPre4.setText("$0.00");
        jTPre4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre4.setNextFocusableComponent(jTUti4);
        jTPre4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPre4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre4FocusLost(evt);
            }
        });
        jTPre4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre4KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPre4KeyTyped(evt);
            }
        });
        jP1.add(jTPre4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 150, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Precio 6");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 150, -1));

        jTPre6.setText("$0.00");
        jTPre6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre6.setNextFocusableComponent(jTUti6);
        jTPre6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPre6FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre6FocusLost(evt);
            }
        });
        jTPre6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre6KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPre6KeyTyped(evt);
            }
        });
        jP1.add(jTPre6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 150, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Precio 8");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 150, -1));

        jTPre8.setText("$0.00");
        jTPre8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre8.setNextFocusableComponent(jTUti8);
        jTPre8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPre8FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre8FocusLost(evt);
            }
        });
        jTPre8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre8KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPre8KeyTyped(evt);
            }
        });
        jP1.add(jTPre8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 150, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Precio 10");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 150, -1));

        jTPre10.setText("$0.00");
        jTPre10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPre10.setNextFocusableComponent(jTUti10);
        jTPre10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPre10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPre10FocusLost(evt);
            }
        });
        jTPre10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPre10KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPre10KeyTyped(evt);
            }
        });
        jP1.add(jTPre10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 150, 20));

        jTCost.setText("$0.00");
        jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCost.setNextFocusableComponent(jTCostL);
        jTCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCostFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCostFocusLost(evt);
            }
        });
        jTCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCostKeyPressed(evt);
            }
        });
        jP1.add(jTCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 150, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Precio 1");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 150, -1));

        jTUti10.setText("0.0");
        jTUti10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti10.setNextFocusableComponent(jTUtil10V);
        jTUti10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUti10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti10FocusLost(evt);
            }
        });
        jTUti10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti10KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUti10KeyTyped(evt);
            }
        });
        jP1.add(jTUti10, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 510, 130, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Costo promedio:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 130, -1));

        jTUti1.setText("0.0");
        jTUti1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti1.setNextFocusableComponent(jTUtil1V);
        jTUti1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUti1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti1FocusLost(evt);
            }
        });
        jTUti1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUti1KeyTyped(evt);
            }
        });
        jP1.add(jTUti1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 130, 20));

        jTUti2.setText("0.0");
        jTUti2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti2.setNextFocusableComponent(jTUtil2V);
        jTUti2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUti2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti2FocusLost(evt);
            }
        });
        jTUti2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUti2KeyTyped(evt);
            }
        });
        jP1.add(jTUti2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 130, 20));

        jTUti3.setText("0.0");
        jTUti3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti3.setNextFocusableComponent(jTUtil3V);
        jTUti3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUti3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti3FocusLost(evt);
            }
        });
        jTUti3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti3KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUti3KeyTyped(evt);
            }
        });
        jP1.add(jTUti3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 130, 20));

        jTUti4.setText("0.0");
        jTUti4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti4.setNextFocusableComponent(jTUtil4V);
        jTUti4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUti4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti4FocusLost(evt);
            }
        });
        jTUti4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti4KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUti4KeyTyped(evt);
            }
        });
        jP1.add(jTUti4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 130, 20));

        jTUti5.setText("0.0");
        jTUti5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti5.setNextFocusableComponent(jTUtil5V);
        jTUti5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUti5FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti5FocusLost(evt);
            }
        });
        jTUti5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti5KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUti5KeyTyped(evt);
            }
        });
        jP1.add(jTUti5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 130, 20));

        jTUti6.setText("0.0");
        jTUti6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti6.setNextFocusableComponent(jTUtil6V);
        jTUti6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUti6FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti6FocusLost(evt);
            }
        });
        jTUti6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti6KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUti6KeyTyped(evt);
            }
        });
        jP1.add(jTUti6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 130, 20));

        jTUti7.setText("0.0");
        jTUti7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti7.setNextFocusableComponent(jTUtil7V);
        jTUti7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUti7FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti7FocusLost(evt);
            }
        });
        jTUti7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti7KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUti7KeyTyped(evt);
            }
        });
        jP1.add(jTUti7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 130, 20));

        jTUti8.setText("0.0");
        jTUti8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti8.setNextFocusableComponent(jTUtil8V);
        jTUti8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUti8FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti8FocusLost(evt);
            }
        });
        jTUti8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti8KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUti8KeyTyped(evt);
            }
        });
        jP1.add(jTUti8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 430, 130, 20));

        jTUti9.setText("0.0");
        jTUti9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUti9.setNextFocusableComponent(jTUtil9V);
        jTUti9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUti9FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUti9FocusLost(evt);
            }
        });
        jTUti9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUti9KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTUti9KeyTyped(evt);
            }
        });
        jP1.add(jTUti9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 470, 130, 20));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Utilidad %:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 490, 130, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Utilidad %:");
        jP1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 130, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Utilidad %:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 130, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Utilidad %:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 130, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Utilidad %:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 130, 20));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Utilidad %:");
        jP1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 130, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Utilidad %:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 370, 130, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Utilidad %:");
        jP1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 130, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Utilidad %:");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 450, 130, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 580, 220, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("UEPS:");
        jP1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 130, -1));

        jTCostP.setEditable(false);
        jTCostP.setText("$0.00");
        jTCostP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCostP.setNextFocusableComponent(jTCost);
        jTCostP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCostPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCostPFocusLost(evt);
            }
        });
        jP1.add(jTCostP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 130, 20));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("Costo logística:");
        jP1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 130, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("PEPS:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        jTUEPS.setEditable(false);
        jTUEPS.setText("$0.00");
        jTUEPS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUEPS.setNextFocusableComponent(jTCost);
        jTUEPS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUEPSFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUEPSFocusLost(evt);
            }
        });
        jTUEPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUEPSKeyPressed(evt);
            }
        });
        jP1.add(jTUEPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 130, 20));

        jTPEPS.setEditable(false);
        jTPEPS.setText("$0.00");
        jTPEPS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPEPS.setNextFocusableComponent(jTCost);
        jTPEPS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPEPSFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPEPSFocusLost(evt);
            }
        });
        jTPEPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPEPSKeyPressed(evt);
            }
        });
        jP1.add(jTPEPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 150, 20));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("Ultimo costo:");
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 150, -1));

        jTCostL.setText("$0.00");
        jTCostL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCostL.setNextFocusableComponent(jTPre1);
        jTCostL.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCostLFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCostLFocusLost(evt);
            }
        });
        jTCostL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCostLKeyPressed(evt);
            }
        });
        jP1.add(jTCostL, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 130, 20));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Utilidad venta 1:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, 130, -1));

        jTUtil1V.setText("0.0");
        jTUtil1V.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUtil1V.setNextFocusableComponent(jTPre2);
        jTUtil1V.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUtil1VFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUtil1VFocusLost(evt);
            }
        });
        jTUtil1V.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUtil1VKeyPressed(evt);
            }
        });
        jP1.add(jTUtil1V, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, 130, 20));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("Utilidad venta 2:");
        jP1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, 130, -1));

        jTUtil2V.setText("0.0");
        jTUtil2V.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUtil2V.setNextFocusableComponent(jTPre3);
        jTUtil2V.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUtil2VFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUtil2VFocusLost(evt);
            }
        });
        jTUtil2V.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUtil2VKeyPressed(evt);
            }
        });
        jP1.add(jTUtil2V, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 190, 130, 20));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setText("Utilidad venta 3:");
        jP1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, 130, -1));

        jTUtil3V.setText("0.0");
        jTUtil3V.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUtil3V.setNextFocusableComponent(jTPre4);
        jTUtil3V.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUtil3VFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUtil3VFocusLost(evt);
            }
        });
        jTUtil3V.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUtil3VKeyPressed(evt);
            }
        });
        jP1.add(jTUtil3V, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 230, 130, 20));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setText("Utilidad venta 4:");
        jP1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250, 130, -1));

        jTUtil4V.setText("0.0");
        jTUtil4V.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUtil4V.setNextFocusableComponent(jTPre5);
        jTUtil4V.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUtil4VFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUtil4VFocusLost(evt);
            }
        });
        jTUtil4V.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUtil4VKeyPressed(evt);
            }
        });
        jP1.add(jTUtil4V, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 270, 130, 20));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setText("Utilidad venta 5:");
        jP1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 290, 130, -1));

        jTUtil5V.setText("0.0");
        jTUtil5V.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUtil5V.setNextFocusableComponent(jTPre6);
        jTUtil5V.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUtil5VFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUtil5VFocusLost(evt);
            }
        });
        jTUtil5V.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUtil5VKeyPressed(evt);
            }
        });
        jP1.add(jTUtil5V, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, 130, 20));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setText("Utilidad venta 6:");
        jP1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 330, 130, -1));

        jTUtil6V.setText("0.0");
        jTUtil6V.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUtil6V.setNextFocusableComponent(jTPre7);
        jTUtil6V.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUtil6VFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUtil6VFocusLost(evt);
            }
        });
        jTUtil6V.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUtil6VKeyPressed(evt);
            }
        });
        jP1.add(jTUtil6V, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 350, 130, 20));

        jTUtil7V.setText("0.0");
        jTUtil7V.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUtil7V.setNextFocusableComponent(jTPre8);
        jTUtil7V.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUtil7VFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUtil7VFocusLost(evt);
            }
        });
        jTUtil7V.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUtil7VKeyPressed(evt);
            }
        });
        jP1.add(jTUtil7V, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 390, 130, 20));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setText("Utilidad venta 7:");
        jP1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, 130, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setText("Utilidad venta 8:");
        jP1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 410, 130, -1));

        jTUtil8V.setText("0.0");
        jTUtil8V.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUtil8V.setNextFocusableComponent(jTPre9);
        jTUtil8V.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUtil8VFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUtil8VFocusLost(evt);
            }
        });
        jTUtil8V.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUtil8VKeyPressed(evt);
            }
        });
        jP1.add(jTUtil8V, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 430, 130, 20));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setText("Utilidad venta 9:");
        jP1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 450, 130, -1));

        jTUtil9V.setText("0.0");
        jTUtil9V.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUtil9V.setNextFocusableComponent(jTPre10);
        jTUtil9V.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUtil9VFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUtil9VFocusLost(evt);
            }
        });
        jTUtil9V.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUtil9VKeyPressed(evt);
            }
        });
        jP1.add(jTUtil9V, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 470, 130, 20));

        jTUtil10V.setText("0.0");
        jTUtil10V.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTUtil10V.setNextFocusableComponent(jBGuar);
        jTUtil10V.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTUtil10VFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTUtil10VFocusLost(evt);
            }
        });
        jTUtil10V.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUtil10VKeyPressed(evt);
            }
        });
        jP1.add(jTUtil10V, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 510, 130, 20));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setText("Utilidad venta 10:");
        jP1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 490, 130, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
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

    
    /*Cuando se presiona una tecla en el botón de aceptar*/
    private void jBGuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuarKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBGuarKeyPressed

    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuarActionPerformed

        /*Obtiene todos los precios de lista*/
        String sPre1     = jTPre1.getText().replace("$", "").replace(",", "");
        String sPre2     = jTPre2.getText().replace("$", "").replace(",", "");
        String sPre3     = jTPre3.getText().replace("$", "").replace(",", "");
        String sPre4     = jTPre4.getText().replace("$", "").replace(",", "");
        String sPre5     = jTPre5.getText().replace("$", "").replace(",", "");
        String sPre6     = jTPre6.getText().replace("$", "").replace(",", "");
        String sPre7     = jTPre7.getText().replace("$", "").replace(",", "");
        String sPre8     = jTPre8.getText().replace("$", "").replace(",", "");
        String sPre9     = jTPre9.getText().replace("$", "").replace(",", "");
        String sPre10    = jTPre10.getText().replace("$", "").replace(",", "");
        String sCost     = jTCost.getText().replace("$", "").replace(",", "");
        String sCostL    = jTCostL.getText().replace("$", "").replace(",", "");        
        
        /*Dale formato de moneda a todos los precios de lista*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        double dCant    = Double.parseDouble(sPre1);                
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
        dCant           = Double.parseDouble(sCostL);                
        sCostL          = n.format(dCant);
        dCant           = Double.parseDouble(sCost);                
        sCost           = n.format(dCant);

        /*Coloca los precio de lista en el otro formulario*/
        prods.sPre1     = sPre1;
        prods.sPre2     = sPre2;
        prods.sPre3     = sPre3;
        prods.sPre4     = sPre4;
        prods.sPre5     = sPre5;
        prods.sPre6     = sPre6;
        prods.sPre7     = sPre7;
        prods.sPre8     = sPre8;
        prods.sPre9     = sPre9;
        prods.sPre10    = sPre10;
        prods.sUltCost  = sCost;
        prods.sCostL    = sCostL;
                
        /*Coloca las utilidades de venta en el otro formulario*/
        prods.sUtil1V   = jTUtil1V.getText().trim();
        prods.sUtil2V   = jTUtil2V.getText().trim();
        prods.sUtil3V   = jTUtil3V.getText().trim();
        prods.sUtil4V   = jTUtil4V.getText().trim();
        prods.sUtil5V   = jTUtil5V.getText().trim();
        prods.sUtil6V   = jTUtil6V.getText().trim();
        prods.sUtil7V   = jTUtil7V.getText().trim();
        prods.sUtil8V   = jTUtil8V.getText().trim();
        prods.sUtil9V   = jTUtil9V.getText().trim();
        prods.sUtil10V  = jTUtil10V.getText().trim();
        
        /*Llama al recolector de basura*/
        System.gc();
                    
        /*Cierra la forma*/
        this.dispose();
        
    }//GEN-LAST:event_jBGuarActionPerformed

    
    /*Cuando se tipea una tecla en el campo del precio 1*/
    private void jTPre1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre1KeyTyped

        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();

    }//GEN-LAST:event_jTPre1KeyTyped
        
    
    /*Cuando se presiona una tecla en el campo de precio 1*/
    private void jTPre1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre1KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTPre1KeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo del precio1*/
    private void jTPre1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPre1.setCaretPosition(0);
                
        /*Si el campo esta vacio entonces que sea 0*/
        if(jTPre1.getText().compareTo("")==0)
        {
            /*Coloca 0 en el precio y la utilidad*/
            jTPre1.setText("$0.00");        
            jTUti1.setText("0");        
        }  
        //Else if el costo es menor o igual a 0 entonces
        else if(Double.parseDouble(sCostProdG)<=0)
        {
            /*Dale formato de moneda al precio 1*/
            String sPre1    = jTPre1.getText().replace("$", "").replace(",", "");                                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            double dCant    = Double.parseDouble(sPre1);                
            sPre1           = n.format(dCant);
            
            /*Colocalo en su lugar*/
            jTPre1.setText(sPre1);
        }
        else
        {
            /*Obtiene el precio*/
            String sPre1    = jTPre1.getText().replace("$", "").replace(",", "");
            
            /*Si el cost es 0 entonces coloca 0 y regresa*/
            if(Double.parseDouble(sCostProdG)<=0)
            {
                /*Coloca 0 en el precio y la utilidad*/
                jTPre1.setText("$0.00");        
                jTUti1.setText("0");   
                return;                
            }
            
            /*Cálcula la utilidad*/
            String sUtili   = Double.toString(((Double.parseDouble(sPre1) * 100) / Double.parseDouble(sCostProdG))-100);
            
            /*Si la utilidad es menor a 0 entonces que sea 0*/
            if(Double.parseDouble(sUtili)<0)
                sUtili      = "0";
            
            /*Coloca la utilidad en su lugar*/
            jTUti1.setText(Double.toString(Double.parseDouble((sUtili))));
            
            /*Dale formato de moneda al precio y colocalo en su lugar*/                        
            double dCant    = Double.parseDouble(jTPre1.getText().replace("$", "").replace(",", ""));                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            jTPre1.setText(n.format(dCant));           
        }

    }//GEN-LAST:event_jTPre1FocusLost

    
    /*Cuando se gana el foco del teclado en el campo de precio*/
    private void jTPre1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre1FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTPre1.setSelectionStart(0);jTPre1.setSelectionEnd(jTPre1.getText().length());        

    }//GEN-LAST:event_jTPre1FocusGained

    
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

    
    /*Cuando se presiona una tecla en el campo de precio 3*/
    private void jTPre3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPre3.setSelectionStart(0);jTPre3.setSelectionEnd(jTPre3.getText().length());        
        
    }//GEN-LAST:event_jTPre3FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del precio 3*/
    private void jTPre3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre3FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPre3.setCaretPosition(0);
        
        /*Si el campo esta vacio entonces que sea 0*/
        if(jTPre3.getText().compareTo("")==0)
        {
            /*Coloca 0 en el precio y la utilidad*/
            jTPre3.setText("$0.00");        
            jTUti3.setText("0");        
        }
        /*Else if el cost esta en 0 entonces*/
        else if(Double.parseDouble(sCostProdG)<=0)
        {
            /*Dale formato de moneda al precio 1*/
            String sPre     = jTPre3.getText().replace("$", "").replace(",", "");                                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            double dCant    = Double.parseDouble(sPre);                
            sPre           = n.format(dCant);
            
            /*Colocalo en su lugar*/
            jTPre3.setText(sPre);
        }
        else
        {
            /*Obtiene el precio*/
            String sPre1    = jTPre3.getText().replace("$", "").replace(",", "");
            
            /*Si el cost es 0 entonces coloca 0 y regresa*/
            if(Double.parseDouble(sCostProdG)<=0)
            {
                /*Coloca 0 en el precio y la utilidad*/
                jTPre3.setText("$0.00");        
                jTUti3.setText("0");   
                return;                
            }
            
            /*Cálcula la utilidad*/
            String sUtili   = Double.toString(((Double.parseDouble(sPre1) * 100) / Double.parseDouble(sCostProdG))-100);
            
            /*Si la utilidad es menor a 0 entonces que sea 0*/
            if(Double.parseDouble(sUtili)<0)
                sUtili      = "0";
            
            /*Coloca la utilidad en su lugar*/
            jTUti3.setText(Double.toString(Double.parseDouble((sUtili))));
            
            /*Dale formato de moneda al precio y colocalo en su lugar*/                        
            double dCant    = Double.parseDouble(jTPre3.getText().replace("$", "").replace(",", ""));                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            jTPre3.setText(n.format(dCant));           
        }   
        
    }//GEN-LAST:event_jTPre3FocusLost

    
    /*Cuando se presiona una tecla en el campo del precio 3*/
    private void jTPre3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre3KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre3KeyPressed

        
    /*Cuando se tipea una tecla en el campo del precio 3*/
    private void jTPre3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre3KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPre3KeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del precio 5*/
    private void jTPre5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre5FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPre5.setSelectionStart(0);jTPre5.setSelectionEnd(jTPre5.getText().length());
        
    }//GEN-LAST:event_jTPre5FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del precio 5*/
    private void jTPre5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre5FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPre5.setCaretPosition(0);
        
        /*Si el campo esta vacio entonces que sea 0*/
        if(jTPre5.getText().compareTo("")==0)
        {
            /*Coloca 0 en el precio y la utilidad*/
            jTPre5.setText("$0.00");        
            jTUti5.setText("0");        
        }
        /*Else if el costO esta en 0 entonces*/
        else if(Double.parseDouble(sCostProdG)<=0)
        {
            /*Dale formato de moneda al precio 1*/
            String sPre     = jTPre5.getText().replace("$", "").replace(",", "");                                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            double dCant    = Double.parseDouble(sPre);                
            sPre            = n.format(dCant);
            
            /*Colocalo en su lugar*/
            jTPre5.setText(sPre);
        }
        else
        {
            /*Obtiene el precio*/
            String sPre1    = jTPre5.getText().replace("$", "").replace(",", "");
            
            /*Si el cost es 0 entonces coloca 0 y regresa*/
            if(Double.parseDouble(sCostProdG)<=0)
            {
                /*Coloca 0 en el precio y la utilidad*/
                jTPre5.setText("$0.00");        
                jTUti5.setText("0.0");   
                return;                
            }
            
            /*Cálcula la utilidad*/
            String sUtili   = Double.toString(((Double.parseDouble(sPre1) * 100) / Double.parseDouble(sCostProdG))-100);
            
            /*Si la utilidad es menor a 0 entonces que sea 0*/
            if(Double.parseDouble(sUtili)<0)
                sUtili      = "0";
            
            /*Coloca la utilidad en su lugar*/
            jTUti5.setText(Double.toString(Double.parseDouble((sUtili))));
            
            /*Dale formato de moneda al precio y colocalo en su lugar*/                        
            double dCant    = Double.parseDouble(jTPre5.getText().replace("$", "").replace(",", ""));                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            jTPre5.setText(n.format(dCant));           
        }
        
    }//GEN-LAST:event_jTPre5FocusLost

    
    /*Cuando se presiona una tecla en el campo del precio 5*/
    private void jTPre5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre5KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre5KeyPressed

        
    /*Cuando se tipea una tecla en el campo del precio 5*/
    private void jTPre5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre5KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPre5KeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del precio 7*/
    private void jTPre7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre7FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPre7.setSelectionStart(0);jTPre7.setSelectionEnd(jTPre7.getText().length());        
        
    }//GEN-LAST:event_jTPre7FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del precio 7*/
    private void jTPre7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre7FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPre7.setCaretPosition(0);
        
        /*Si el campo esta vacio entonces que sea 0*/
        if(jTPre7.getText().compareTo("")==0)
        {
            /*Coloca 0 en el precio y la utilidad*/
            jTPre7.setText("$0.00");        
            jTUti7.setText("0");        
        }
        /*Else if el cost esta en 0 entonces*/
        else if(Double.parseDouble(sCostProdG)<=0)
        {
            /*Dale formato de moneda al precio 1*/
            String sPre     = jTPre7.getText().replace("$", "").replace(",", "");                                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            double dCant    = Double.parseDouble(sPre);                
            sPre            = n.format(dCant);
            
            /*Colocalo en su lugar*/
            jTPre7.setText(sPre);
        }
        else
        {
            /*Obtiene el precio*/
            String sPre1    = jTPre7.getText().replace("$", "").replace(",", "");
            
            /*Si el cost es 0 entonces coloca 0 y regresa*/
            if(Double.parseDouble(sCostProdG)<=0)
            {
                /*Coloca 0 en el precio y la utilidad*/
                jTPre7.setText("$0.00");        
                jTUti7.setText("0");   
                return;                
            }
            
            /*Cálcula la utilidad*/
            String sUtili   = Double.toString(((Double.parseDouble(sPre1) * 100) / Double.parseDouble(sCostProdG))-100);
            
            /*Si la utilidad es menor a 0 entonces que sea 0*/
            if(Double.parseDouble(sUtili)<0)
                sUtili      = "0";
            
            /*Coloca la utilidad en su lugar*/
            jTUti7.setText(Double.toString(Double.parseDouble((sUtili))));
            
            /*Dale formato de moneda al precio y colocalo en su lugar*/                        
            double dCant    = Double.parseDouble(jTPre7.getText().replace("$", "").replace(",", ""));                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            jTPre7.setText(n.format(dCant));           
        }
        
    }//GEN-LAST:event_jTPre7FocusLost

    
    /*Cuando se presiona una tecla en el campo del precio 7*/
    private void jTPre7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre7KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre7KeyPressed
    
    
    /*Cuando se tipea una tecla en el campo del precio 7*/
    private void jTPre7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre7KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPre7KeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del precio 9*/
    private void jTPre9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre9FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPre9.setSelectionStart(0);jTPre9.setSelectionEnd(jTPre9.getText().length());        
        
    }//GEN-LAST:event_jTPre9FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del precio 9*/
    private void jTPre9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre9FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPre9.setCaretPosition(0);
        
        /*Si el campo esta vacio entonces que sea 0*/
        if(jTPre9.getText().compareTo("")==0)
        {
            /*Coloca 0 en el precio y la utilidad*/
            jTPre9.setText("$0.00");        
            jTUti9.setText("0");        
        }
        /*Else if el cost esta en 0 entonces*/
        else if(Double.parseDouble(sCostProdG)<=0)
        {
            /*Dale formato de moneda al precio 1*/
            String sPre     = jTPre9.getText().replace("$", "").replace(",", "");                                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            double dCant    = Double.parseDouble(sPre);                
            sPre            = n.format(dCant);
            
            /*Colocalo en su lugar*/
            jTPre9.setText(sPre);
        }
        else
        {
            /*Obtiene el precio*/
            String sPre1    = jTPre9.getText().replace("$", "").replace(",", "");
            
            /*Si el cost es 0 entonces coloca 0 y regresa*/
            if(Double.parseDouble(sCostProdG)<=0)
            {
                /*Coloca 0 en el precio y la utilidad*/
                jTPre9.setText("$0.00");        
                jTUti9.setText("0");   
                return;                
            }
            
            /*Cálcula la utilidad*/
            String sUtili   = Double.toString(((Double.parseDouble(sPre1) * 100) / Double.parseDouble(sCostProdG))-100);
            
            /*Si la utilidad es menor a 0 entonces que sea 0*/
            if(Double.parseDouble(sUtili)<0)
                sUtili      = "0";
            
            /*Coloca la utilidad en su lugar*/
            jTUti9.setText(Double.toString(Double.parseDouble((sUtili))));
            
            /*Dale formato de moneda al precio y colocalo en su lugar*/                        
            double dCant    = Double.parseDouble(jTPre9.getText().replace("$", "").replace(",", ""));                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            jTPre9.setText(n.format(dCant));           
        }
        
    }//GEN-LAST:event_jTPre9FocusLost

    
    /*Cuando se presiona una tecla en el campo del precio 9*/
    private void jTPre9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre9KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre9KeyPressed

        
    /*Cuando se tipea una tecla en el campo del precio 9*/
    private void jTPre9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre9KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPre9KeyTyped


    /*Cuando se presiona una tecla en el campo del precio2*/
    private void jTPre2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPre2.setSelectionStart(0);jTPre2.setSelectionEnd(jTPre2.getText().length());
        
    }//GEN-LAST:event_jTPre2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del precio 2*/
    private void jTPre2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre2.setCaretPosition(0);
        
        /*Si el campo esta vacio entonces que sea 0*/
        if(jTPre2.getText().compareTo("")==0)
        {
            /*Coloca 0 en el precio y la utilidad*/
            jTPre2.setText("$0.00");        
            jTUti2.setText("0");        
        }
        /*Else if el cost esta en 0 entonces*/
        else if(Double.parseDouble(sCostProdG)<=0)
        {
            /*Dale formato de moneda al precio 1*/
            String sPre     = jTPre2.getText().replace("$", "").replace(",", "");                                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            double dCant    = Double.parseDouble(sPre);                
            sPre           = n.format(dCant);
            
            /*Colocalo en su lugar*/
            jTPre2.setText(sPre);
        }
        else
        {
            /*Obtiene el precio*/
            String sPre1    = jTPre2.getText().replace("$", "").replace(",", "");
            
            /*Si el cost es 0 entonces coloca 0 y regresa*/
            if(Double.parseDouble(sCostProdG)<=0)
            {
                /*Coloca 0 en el precio y la utilidad*/
                jTPre2.setText("$0.00");        
                jTUti2.setText("0");   
                return;                
            }
            
            /*Cálcula la utilidad*/
            String sUtili   = Double.toString(((Double.parseDouble(sPre1) * 100) / Double.parseDouble(sCostProdG))-100);
            
            /*Si la utilidad es menor a 0 entonces que sea 0*/
            if(Double.parseDouble(sUtili)<0)
                sUtili      = "0";
            
            /*Coloca la utilidad en su lugar*/
            jTUti2.setText(Double.toString(Double.parseDouble((sUtili))));
            
            /*Dale formato de moneda al precio y colocalo en su lugar*/                        
            double dCant    = Double.parseDouble(jTPre2.getText().replace("$", "").replace(",", ""));                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            jTPre2.setText(n.format(dCant));           
        }
        
    }//GEN-LAST:event_jTPre2FocusLost

        
    /*Cuando se presiona una tecla en el campo del precio 2*/
    private void jTPre2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre2KeyPressed

        
    /*Cuando se tipea una tecla en el campo del precio 2*/
    private void jTPre2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre2KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPre2KeyTyped

    
    /*Cuando se presiona una tecla en el campo de precio 4*/
    private void jTPre4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre4FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPre4.setSelectionStart(0);jTPre4.setSelectionEnd(jTPre4.getText().length());        
        
    }//GEN-LAST:event_jTPre4FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del precio 4*/
    private void jTPre4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre4FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPre4.setCaretPosition(0);
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre4.setCaretPosition(0);
        
        /*Si el campo esta vacio entonces que sea 0*/
        if(jTPre4.getText().compareTo("")==0)
        {
            /*Coloca 0 en el precio y la utilidad*/
            jTPre4.setText("$0.00");        
            jTUti4.setText("0");        
        }
        /*Else if el cost esta en 0 entonces*/
        else if(Double.parseDouble(sCostProdG)<=0)
        {
            /*Dale formato de moneda al precio 1*/
            String sPre     = jTPre4.getText().replace("$", "").replace(",", "");                                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            double dCant    = Double.parseDouble(sPre);                
            sPre            = n.format(dCant);
            
            /*Colocalo en su lugar*/
            jTPre4.setText(sPre);
        }
        else
        {
            /*Obtiene el precio*/
            String sPre1    = jTPre4.getText().replace("$", "").replace(",", "");
            
            /*Si el cost es 0 entonces coloca 0 y regresa*/
            if(Double.parseDouble(sCostProdG)<=0)
            {
                /*Coloca 0 en el precio y la utilidad*/
                jTPre4.setText("$0.00");        
                jTUti4.setText("0");   
                return;                
            }
            
            /*Cálcula la utilidad*/
            String sUtili   = Double.toString(((Double.parseDouble(sPre1) * 100) / Double.parseDouble(sCostProdG))-100);

            /*Si la utilidad es menor a 0 entonces que sea 0*/
            if(Double.parseDouble(sUtili)<0)
                sUtili      = "0";
            
            /*Coloca la utilidad en su lugar*/
            jTUti4.setText(Double.toString(Double.parseDouble((sUtili))));
            
            /*Dale formato de moneda al precio y colocalo en su lugar*/                        
            double dCant    = Double.parseDouble(jTPre4.getText().replace("$", "").replace(",", ""));                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            jTPre4.setText(n.format(dCant));           
        }
        
    }//GEN-LAST:event_jTPre4FocusLost
    
    
    /*Cuando se presiona una tecla en el campo del precio 4*/
    private void jTPre4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre4KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre4KeyPressed

        
    /*Cuando se tipea una tecla en el campo del precio 4*/
    private void jTPre4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre4KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPre4KeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del precio 6*/
    private void jTPre6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre6FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPre6.setSelectionStart(0);jTPre6.setSelectionEnd(jTPre6.getText().length());
        
    }//GEN-LAST:event_jTPre6FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del precio 6*/
    private void jTPre6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre6FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPre6.setCaretPosition(0);
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre6.setCaretPosition(0);
        
        /*Si el campo esta vacio entonces que sea 0*/
        if(jTPre6.getText().compareTo("")==0)
        {
            /*Coloca 0 en el precio y la utilidad*/
            jTPre6.setText("$0.00");        
            jTUti6.setText("0");        
        }
        /*Else if el cost esta en 0 entonces*/
        else if(Double.parseDouble(sCostProdG)<=0)
        {
            /*Dale formato de moneda al precio 1*/
            String sPre     = jTPre6.getText().replace("$", "").replace(",", "");                                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            double dCant    = Double.parseDouble(sPre);                
            sPre            = n.format(dCant);
            
            /*Colocalo en su lugar*/
            jTPre6.setText(sPre);
        }
        else
        {
            /*Obtiene el precio*/
            String sPre1    = jTPre6.getText().replace("$", "").replace(",", "");
            
            /*Si el cost es 0 entonces coloca 0 y regresa*/
            if(Double.parseDouble(sCostProdG)<=0)
            {
                /*Coloca 0 en el precio y la utilidad*/
                jTPre6.setText("$0.00");        
                jTUti6.setText("0");   
                return;                
            }
            
            /*Cálcula la utilidad*/
            String sUtili   = Double.toString(((Double.parseDouble(sPre1) * 100) / Double.parseDouble(sCostProdG))-100);
            
            /*Si la utilidad es menor a 0 entonces que sea 0*/
            if(Double.parseDouble(sUtili)<0)
                sUtili      = "0";
            
            /*Coloca la utilidad en su lugar*/
            jTUti6.setText(Double.toString(Double.parseDouble((sUtili))));
            
            /*Dale formato de moneda al precio y colocalo en su lugar*/                        
            double dCant    = Double.parseDouble(jTPre6.getText().replace("$", "").replace(",", ""));                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            jTPre6.setText(n.format(dCant));           
        }
        
    }//GEN-LAST:event_jTPre6FocusLost

    
    /*Cuando se presiona una tecla en el campo del precio 6*/
    private void jTPre6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre6KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre6KeyPressed

        
    /*Cuando se tipea una tecla en el campo del precio 6*/
    private void jTPre6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre6KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPre6KeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del precio 8*/
    private void jTPre8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre8FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPre8.setSelectionStart(0);jTPre8.setSelectionEnd(jTPre8.getText().length());
        
    }//GEN-LAST:event_jTPre8FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del precio 8*/
    private void jTPre8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre8FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPre8.setCaretPosition(0);
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre8.setCaretPosition(0);
        
        /*Si el campo esta vacio entonces que sea 0*/
        if(jTPre8.getText().compareTo("")==0)
        {
            /*Coloca 0 en el precio y la utilidad*/
            jTPre8.setText("$0.00");        
            jTUti8.setText("0");        
        }
        /*Else if el cost esta en 0 entonces*/
        else if(Double.parseDouble(sCostProdG)<=0)
        {
            /*Dale formato de moneda al precio 1*/
            String sPre     = jTPre8.getText().replace("$", "").replace(",", "");                                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            double dCant    = Double.parseDouble(sPre);                
            sPre            = n.format(dCant);
            
            /*Colocalo en su lugar*/
            jTPre8.setText(sPre);
        }
        else
        {
            /*Obtiene el precio*/
            String sPre1    = jTPre8.getText().replace("$", "").replace(",", "");
            
            /*Si el cost es 0 entonces coloca 0 y regresa*/
            if(Double.parseDouble(sCostProdG)<=0)
            {
                /*Coloca 0 en el precio y la utilidad*/
                jTPre8.setText("$0.00");        
                jTUti8.setText("0");   
                return;                
            }
            
            /*Cálcula la utilidad*/
            String sUtili   = Double.toString(((Double.parseDouble(sPre1) * 100) / Double.parseDouble(sCostProdG))-100);
            
            /*Si la utilidad es menor a 0 entonces que sea 0*/
            if(Double.parseDouble(sUtili)<0)
                sUtili      = "0";
            
            /*Coloca la utilidad en su lugar*/
            jTUti8.setText(Double.toString(Double.parseDouble((sUtili))));
            
            /*Dale formato de moneda al precio y colocalo en su lugar*/                        
            double dCant    = Double.parseDouble(jTPre8.getText().replace("$", "").replace(",", ""));                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            jTPre8.setText(n.format(dCant));           
        }
        
    }//GEN-LAST:event_jTPre8FocusLost

    
    /*Cuando se presiona una tecla en el campo del precio 8*/
    private void jTPre8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre8KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre8KeyPressed

        
    /*Cuando se tipea una tecla en el campo del precio 8*/
    private void jTPre8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre8KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPre8KeyTyped

    
    /*Cuando se gana el foco del teclado en el campo del precio 10*/
    private void jTPre10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre10FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPre10.setSelectionStart(0);jTPre10.setSelectionEnd(jTPre10.getText().length());
        
    }//GEN-LAST:event_jTPre10FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del precio 10*/
    private void jTPre10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPre10FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPre10.setCaretPosition(0);
        
        /*Coloca el caret en la posiciòn 0*/
        jTPre10.setCaretPosition(0);
        
        /*Si el campo esta vacio entonces que sea 0*/
        if(jTPre10.getText().compareTo("")==0)
        {
            /*Coloca 0 en el precio y la utilidad*/
            jTPre10.setText("$0.00");        
            jTUti10.setText("0");        
        }
        /*Else if el cost esta en 0 entonces*/
        else if(Double.parseDouble(sCostProdG)<=0)
        {
            /*Dale formato de moneda al precio 1*/
            String sPre     = jTPre10.getText().replace("$", "").replace(",", "");                                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            double dCant    = Double.parseDouble(sPre);                
            sPre            = n.format(dCant);
            
            /*Colocalo en su lugar*/
            jTPre10.setText(sPre);
        }
        else
        {
            /*Obtiene el precio*/
            String sPre1    = jTPre10.getText().replace("$", "").replace(",", "");
            
            /*Si el cost es 0 entonces coloca 0 y regresa*/
            if(Double.parseDouble(sCostProdG)<=0)
            {
                /*Coloca 0 en el precio y la utilidad*/
                jTPre10.setText("$0.00");        
                jTUti10.setText("0");   
                return;                
            }
            
            /*Cálcula la utilidad*/
            String sUtili   = Double.toString(((Double.parseDouble(sPre1) * 100) / Double.parseDouble(sCostProdG))-100);
            
            /*Si la utilidad es menor a 0 entonces que sea 0*/
            if(Double.parseDouble(sUtili)<0)
                sUtili      = "0";
            
            /*Coloca la utilidad en su lugar*/
            jTUti10.setText(Double.toString(Double.parseDouble((sUtili))));
            
            /*Dale formato de moneda al precio y colocalo en su lugar*/                        
            double dCant    = Double.parseDouble(jTPre10.getText().replace("$", "").replace(",", ""));                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            jTPre10.setText(n.format(dCant));           
        }
        
    }//GEN-LAST:event_jTPre10FocusLost

    
    /*Cuando se presiona una tecla en el campo del precio 10*/
    private void jTPre10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre10KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPre10KeyPressed
    
    
    /*Cuando se tipea una tecla en el campo del precio 10*/
    private void jTPre10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPre10KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPre10KeyTyped

    
    /*Cuando se pierde el foco del teclado en el campo de la utildiad 1*/
    private void jTUti1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti1FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUti1.setCaretPosition(0);
        
        /*Si el cost esta en 0 entonces regresa*/
        if(Double.parseDouble(sCostProdG)<=0)
            return;
                    
        /*Si es cadena vacia el campo entonces*/
        String sUtil    = jTUti1.getText();
        if(sUtil.compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti1.setText("0");
            jTPre1.setText("$0.00");
            return;
        }
        
        /*Si la utildad es 0 entonces*/
        if(Double.parseDouble(sUtil)==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti1.setText("0");
            jTPre1.setText("$0.00");
            return;
        }
        
        /*Cálcula la utilidad*/ 
        sUtil           = Double.toString((Double.parseDouble(sUtil) / 100) * Double.parseDouble(sCostProdG));
        
        /*Cálcula el precio*/
        String sPre1    = Double.toString(Double.parseDouble(sUtil) + Double.parseDouble(sCostProdG));        
        
        /*Dale formato de moneda al precio*/        
        double dCant    = Double.parseDouble(sPre1);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        sPre1           = n.format(dCant);
        
        /*Colocalo en su lugar*/
        jTPre1.setText(sPre1);
        
    }//GEN-LAST:event_jTUti1FocusLost

    
    /*Cuando se gana el foco del teclado en el campo de la utildiad 1*/
    private void jTUti1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti1FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUti1.setSelectionStart(0);jTUti1.setSelectionEnd(jTUti1.getText().length());        
        
    }//GEN-LAST:event_jTUti1FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la utilidad 2*/
    private void jTUti2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUti2.setSelectionStart(0);jTUti2.setSelectionEnd(jTUti2.getText().length());        
        
    }//GEN-LAST:event_jTUti2FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la utildiad3*/
    private void jTUti3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti3FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUti3.setSelectionStart(0);jTUti3.setSelectionEnd(jTUti3.getText().length());
        
    }//GEN-LAST:event_jTUti3FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la utildiad 4*/
    private void jTUti4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti4FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUti4.setSelectionStart(0);jTUti4.setSelectionEnd(jTUti4.getText().length());
        
    }//GEN-LAST:event_jTUti4FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del a utilidad 5*/
    private void jTUti5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti5FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUti5.setSelectionStart(0);jTUti5.setSelectionEnd(jTUti5.getText().length());
        
    }//GEN-LAST:event_jTUti5FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la utilidad 6*/
    private void jTUti6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti6FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUti6.setSelectionStart(0);jTUti6.setSelectionEnd(jTUti6.getText().length());
        
    }//GEN-LAST:event_jTUti6FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la utilidad 7*/
    private void jTUti7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti7FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUti7.setSelectionStart(0);jTUti7.setSelectionEnd(jTUti7.getText().length());
        
    }//GEN-LAST:event_jTUti7FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la utilidad 8*/
    private void jTUti8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti8FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUti8.setSelectionStart(0);jTUti8.setSelectionEnd(jTUti8.getText().length());
        
    }//GEN-LAST:event_jTUti8FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la utildiad 9*/
    private void jTUti9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti9FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUti9.setSelectionStart(0);jTUti9.setSelectionEnd(jTUti9.getText().length());
        
    }//GEN-LAST:event_jTUti9FocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la utildiad 10*/
    private void jTUti10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti10FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUti10.setSelectionStart(0);jTUti10.setSelectionEnd(jTUti10.getText().length());
        
    }//GEN-LAST:event_jTUti10FocusGained

    
    /*Cuando se gana el foco del teclado en el campo del cost*/
    private void jTCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCost.setSelectionStart(0);jTCost.setSelectionEnd(jTCost.getText().length());
        
    }//GEN-LAST:event_jTCostFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del cost*/
    private void jTCostFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCost.setCaretPosition(0);
        
        /*Si el campo esta vacio entonces que sea 0*/
        if(jTCost.getText().compareTo("")==0)
            jTCost.setText("$0.00");        
        else
        {
            /*Dale formato de moneda*/                       
            double dCant    = Double.parseDouble(jTCost.getText().replace("$", "").replace(",", ""));                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            jTCost.setText(n.format(dCant));           
        }
        
    }//GEN-LAST:event_jTCostFocusLost

    
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

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad 2*/
    private void jTUti2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUti2.setCaretPosition(0);
        
        /*Si el cost esta en 0 entonces regresa*/
        if(Double.parseDouble(sCostProdG)<=0)
            return;
        
        /*Si es cadena vacia el campo entonces*/
        String sUtil    = jTUti2.getText();
        if(sUtil.compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti2.setText("0");
            jTPre2.setText("$0.00");
            return;
        }
        
        /*Si la utildad es 0 entonces*/
        if(Double.parseDouble(sUtil)==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti2.setText("0");
            jTPre2.setText("$0.00");
            return;
        }
        
        /*Cálcula la utilidad*/ 
        sUtil           = Double.toString((Double.parseDouble(sUtil) / 100) * Double.parseDouble(sCostProdG));
        
        /*Cálcula el precio*/
        String sPre1    = Double.toString(Double.parseDouble(sUtil) + Double.parseDouble(sCostProdG));        
        
        /*Dale formato de moneda al precio*/       
        double dCant    = Double.parseDouble(sPre1);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        sPre1           = n.format(dCant);
        
        /*Colocalo en su lugar*/
        jTPre2.setText(sPre1);
        
    }//GEN-LAST:event_jTUti2FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad 3*/
    private void jTUti3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti3FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUti3.setCaretPosition(0);
        
        /*Si el cost esta en 0 entonces regresa*/
        if(Double.parseDouble(sCostProdG)<=0)
            return;
        
        /*Si es cadena vacia el campo entonces*/
        String sUtil    = jTUti3.getText();
        if(sUtil.compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti3.setText("0");
            jTPre3.setText("$0.00");
            return;
        }
        
        /*Si la utildad es 0 entonces*/
        if(Double.parseDouble(sUtil)==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti3.setText("0");
            jTPre3.setText("$0.00");
            return;
        }
        
        /*Cálcula la utilidad*/ 
        sUtil           = Double.toString((Double.parseDouble(sUtil) / 100) * Double.parseDouble(sCostProdG));
        
        /*Cálcula el precio*/
        String sPre1    = Double.toString(Double.parseDouble(sUtil) + Double.parseDouble(sCostProdG));        
        
        /*Dale formato de moneda al precio*/        
        double dCant    = Double.parseDouble(sPre1);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        sPre1           = n.format(dCant);
        
        /*Colocalo en su lugar*/
        jTPre3.setText(sPre1);
        
    }//GEN-LAST:event_jTUti3FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad 4*/
    private void jTUti4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti4FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUti4.setCaretPosition(0);
        
        /*Si el cost esta en 0 entonces regresa*/
        if(Double.parseDouble(sCostProdG)<=0)
            return;
        
        /*Si es cadena vacia el campo entonces*/
        String sUtil    = jTUti4.getText();
        if(sUtil.compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti4.setText("0");
            jTPre4.setText("$0.00");
            return;
        }
        
        /*Si la utildad es 0 entonces*/
        if(Double.parseDouble(sUtil)==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti4.setText("0");
            jTPre4.setText("$0.00");
            return;
        }
        
        /*Cálcula la utilidad*/ 
        sUtil           = Double.toString((Double.parseDouble(sUtil) / 100) * Double.parseDouble(sCostProdG));
        
        /*Cálcula el precio*/
        String sPre1    = Double.toString(Double.parseDouble(sUtil) + Double.parseDouble(sCostProdG));        
        
        /*Dale formato de moneda al precio*/        
        double dCant    = Double.parseDouble(sPre1);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        sPre1           = n.format(dCant);
        
        /*Colocalo en su lugar*/
        jTPre4.setText(sPre1);
        
    }//GEN-LAST:event_jTUti4FocusLost

    
    /*Cando se pierde el foco del teclado en el campo de utilidad 5*/
    private void jTUti5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti5FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUti5.setCaretPosition(0);
        
        /*Si el cost esta en 0 entonces regresa*/
        if(Double.parseDouble(sCostProdG)<=0)
            return;
        
        /*Si es cadena vacia el campo entonces*/
        String sUtil    = jTUti5.getText();
        if(sUtil.compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti5.setText("0");
            jTPre5.setText("$0.00");
            return;
        }
                
        /*Si la utildad es 0 entonces*/
        if(Double.parseDouble(sUtil)==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti4.setText("0");
            jTPre4.setText("$0.00");
            return;
        }
        
        /*Cálcula la utilidad*/ 
        sUtil           = Double.toString((Double.parseDouble(sUtil) / 100) * Double.parseDouble(sCostProdG));
        
        /*Cálcula el precio*/
        String sPre1    = Double.toString(Double.parseDouble(sUtil) + Double.parseDouble(sCostProdG));        
        
        /*Dale formato de moneda al precio*/        
        double dCant    = Double.parseDouble(sPre1);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        sPre1           = n.format(dCant);
        
        /*Colocalo en su lugar*/
        jTPre5.setText(sPre1);
        
    }//GEN-LAST:event_jTUti5FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de tildiad 6*/
    private void jTUti6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti6FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUti6.setCaretPosition(0);
        
        /*Si el cost esta en 0 entonces regresa*/
        if(Double.parseDouble(sCostProdG)<=0)
            return;
        
        /*Si es cadena vacia el campo entonces*/
        String sUtil    = jTUti6.getText();
        if(sUtil.compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti6.setText("0");
            jTPre6.setText("$0.00");
            return;
        }
        
        /*Si la utildad es 0 entonces*/
        if(Double.parseDouble(sUtil)==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti5.setText("0");
            jTPre5.setText("$0.00");
            return;
        }
        
        /*Cálcula la utilidad*/ 
        sUtil           = Double.toString((Double.parseDouble(sUtil) / 100) * Double.parseDouble(sCostProdG));
        
        /*Cálcula el precio*/
        String sPre1    = Double.toString(Double.parseDouble(sUtil) + Double.parseDouble(sCostProdG));        
        
        /*Dale formato de moneda al precio*/        
        double dCant    = Double.parseDouble(sPre1);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        sPre1           = n.format(dCant);
        
        /*Colocalo en su lugar*/
        jTPre6.setText(sPre1);
        
    }//GEN-LAST:event_jTUti6FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la utilidad 7*/
    private void jTUti7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti7FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUti7.setCaretPosition(0);
        
        /*Si el cost esta en 0 entonces regresa*/
        if(Double.parseDouble(sCostProdG)<=0)
            return;
        
        /*Si es cadena vacia el campo entonces*/
        String sUtil    = jTUti7.getText();
        if(sUtil.compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti7.setText("0");
            jTPre7.setText("$0.00");
            return;
        }
        
        /*Si la utildad es 0 entonces*/
        if(Double.parseDouble(sUtil)==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti7.setText("0");
            jTPre7.setText("$0.00");
            return;
        }
        
        /*Cálcula la utilidad*/ 
        sUtil           = Double.toString((Double.parseDouble(sUtil) / 100) * Double.parseDouble(sCostProdG));
        
        /*Cálcula el precio*/
        String sPre1    = Double.toString(Double.parseDouble(sUtil) + Double.parseDouble(sCostProdG));        
        
        /*Dale formato de moneda al precio*/      
        double dCant    = Double.parseDouble(sPre1);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        sPre1           = n.format(dCant);
        
        /*Colocalo en su lugar*/
        jTPre7.setText(sPre1);
        
    }//GEN-LAST:event_jTUti7FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad 8*/
    private void jTUti8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti8FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUti8.setCaretPosition(0);
        
        /*Si el cost esta en 0 entonces regresa*/
        if(Double.parseDouble(sCostProdG)<=0)
            return;
        
        /*Si es cadena vacia el campo entonces*/
        String sUtil    = jTUti8.getText();
        if(sUtil.compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti8.setText("0");
            jTPre8.setText("$0.00");
            return;
        }
        
        /*Si la utildad es 0 entonces*/
        if(Double.parseDouble(sUtil)==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti8.setText("0");
            jTPre8.setText("$0.00");
            return;
        }
        
        /*Cálcula la utilidad*/ 
        sUtil           = Double.toString((Double.parseDouble(sUtil) / 100) * Double.parseDouble(sCostProdG));
        
        /*Cálcula el precio*/
        String sPre1    = Double.toString(Double.parseDouble(sUtil) + Double.parseDouble(sCostProdG));        
        
        /*Dale formato de moneda al precio*/        
        double dCant    = Double.parseDouble(sPre1);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        sPre1           = n.format(dCant);
        
        /*Colocalo en su lugar*/
        jTPre8.setText(sPre1);
        
    }//GEN-LAST:event_jTUti8FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de la utilidad 9*/
    private void jTUti9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti9FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUti9.setCaretPosition(0);
        
        /*Si el cost esta en 0 entonces regresa*/
        if(Double.parseDouble(sCostProdG)<=0)
            return;
        
        /*Si es cadena vacia el campo entonces*/
        String sUtil    = jTUti9.getText();
        if(sUtil.compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti9.setText("0");
            jTPre9.setText("$0.00");
            return;
        }
        
        /*Si la utildad es 0 entonces*/
        if(Double.parseDouble(sUtil)==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti9.setText("0");
            jTPre9.setText("$0.00");
            return;
        }
        
        /*Cálcula la utilidad*/ 
        sUtil           = Double.toString((Double.parseDouble(sUtil) / 100) * Double.parseDouble(sCostProdG));
        
        /*Cálcula el precio*/
        String sPre1    = Double.toString(Double.parseDouble(sUtil) + Double.parseDouble(sCostProdG));        
        
        /*Dale formato de moneda al precio*/        
        double dCant    = Double.parseDouble(sPre1);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        sPre1           = n.format(dCant);
        
        /*Colocalo en su lugar*/
        jTPre9.setText(sPre1);
        
    }//GEN-LAST:event_jTUti9FocusLost

    
    /*Cuando se pierde el foco del teclado en el campo de utiidad 10*/
    private void jTUti10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUti10FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTUti10.setCaretPosition(0);
        
        /*Si el cost esta en 0 entonces regresa*/
        if(Double.parseDouble(sCostProdG)<=0)
            return;
        
        /*Si es cadena vacia el campo entonces*/
        String sUtil    = jTUti10.getText();
        if(sUtil.compareTo("")==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti10.setText("0");
            jTPre10.setText("$0.00");
            return;
        }
        
        /*Si la utildad es 0 entonces*/
        if(Double.parseDouble(sUtil)==0)
        {
            /*Coloca 0 en el campo y regresa*/
            jTUti10.setText("0");
            jTPre10.setText("$0.00");
            return;
        }
        
        /*Cálcula la utilidad*/ 
        sUtil           = Double.toString((Double.parseDouble(sUtil) / 100) * Double.parseDouble(sCostProdG));
        
        /*Cálcula el precio*/
        String sPre1    = Double.toString(Double.parseDouble(sUtil) + Double.parseDouble(sCostProdG));        
        
        /*Dale formato de moneda al precio*/        
        double dCant    = Double.parseDouble(sPre1);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
        sPre1           = n.format(dCant);
        
        /*Colocalo en su lugar*/
        jTPre10.setText(sPre1);
        
    }//GEN-LAST:event_jTUti10FocusLost

    
    /*Cuando se tipea una tecla en el campo de utilidad 1*/
    private void jTUti1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti1KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTUti1KeyTyped

    
    /*Cuando se tipea una tecla en el campo de utilidad 2*/
    private void jTUti2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti2KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTUti2KeyTyped

    
    /*Cuando se tipea una tecla en el campo de utilidad 3*/
    private void jTUti3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti3KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTUti3KeyTyped

    
    /*Cuando se tipea una tecla en el campo de utildiad 4*/
    private void jTUti4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti4KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTUti4KeyTyped

    
    /*Cuando se tipea una tecla en el campo de utilidad 5*/
    private void jTUti5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti5KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTUti5KeyTyped

    
    /*Cuando se tipea una tecla en el campo de utilidad 6*/
    private void jTUti6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti6KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTUti6KeyTyped

    
    /*Cuando se tipea una tecla en el campo de utildiad 7*/
    private void jTUti7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti7KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTUti7KeyTyped

    
    /*Cuando se tipea una tecla en el campo de utildiad 8*/
    private void jTUti8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti8KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTUti8KeyTyped

    
    /*Cuando se tipea una tecla en el campo de utildiad 9*/
    private void jTUti9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti9KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTUti9KeyTyped

    
    /*Cuando se tipea una tecla en el campo de utilidad 10*/
    private void jTUti10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUti10KeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTUti10KeyTyped
    
       
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
    private void jBGuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGuar.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuarMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuarMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGuar.setBackground(colOri);
        
    }//GEN-LAST:event_jBGuarMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando se gana el foco del teclado en el campo del costo promedio*/
    private void jTCostPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostPFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCostP.setSelectionStart(0);
        jTCostP.setSelectionStart(jTCostP.getText().length());
        
    }//GEN-LAST:event_jTCostPFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del PEPS*/
    private void jTPEPSFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPEPSFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPEPS.setSelectionStart(0);jTPEPS.setSelectionEnd(jTPEPS.getText().length());        
        
    }//GEN-LAST:event_jTPEPSFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del UEPS*/
    private void jTUEPSFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUEPSFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUEPS.setSelectionStart(0);jTUEPS.setSelectionEnd(jTUEPS.getText().length());        
        
    }//GEN-LAST:event_jTUEPSFocusGained

    
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
    private void jTCostPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostPFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCostP.setCaretPosition(0);
        
    }//GEN-LAST:event_jTCostPFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTPEPSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPEPSFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTPEPS.setCaretPosition(0);

    }//GEN-LAST:event_jTPEPSFocusLost

    
    /*Cuando se pierde el foco del teclado en el control*/
    private void jTUEPSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUEPSFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTUEPSFocusLost

    
    /*Cuando se gana el foco del teclado en el campo del costo logística*/
    private void jTCostLFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostLFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCostL.setSelectionStart(0);jTCostL.setSelectionEnd(jTCostL.getText().length());
        
    }//GEN-LAST:event_jTCostLFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del costo logística*/
    private void jTCostLFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostLFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCostL.setCaretPosition(0);
        
        /*Si el campo esta vacio entonces que sea 0*/
        if(jTCostL.getText().compareTo("")==0)
            jTCostL.setText("$0.00");        
        else
        {
            /*Dale formato de moneda*/                        
            double dCant    = Double.parseDouble(jTCostL.getText().replace("$", "").replace(",", ""));                
            NumberFormat n  = NumberFormat.getCurrencyInstance(new Locale("es","MX"));
            jTCostL.setText(n.format(dCant));           
        }
        
    }//GEN-LAST:event_jTCostLFocusLost

    
    /*Cuando se presiona una tecla en el campo del costo logítica*/
    private void jTCostLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCostLKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCostLKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de utilidad venta 1*/
    private void jTUtil1VFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil1VFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUtil1V.setSelectionStart(0);jTUtil1V.setSelectionEnd(jTUtil1V.getText().length());
        
    }//GEN-LAST:event_jTUtil1VFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad venta 1*/
    private void jTUtil1VFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil1VFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUtil1V.setCaretPosition(0);
        
        /*Si es cadena vacia el campo de utilidad venta 1 entonces coloca 0 en el campo*/        
        if(jTUtil1V.getText().compareTo("")==0)                   
            jTUtil1V.setText("0.0");                          
                
    }//GEN-LAST:event_jTUtil1VFocusLost

    
    /*Cuando se presiona una tecla en el campo de utilidad venta 1*/
    private void jTUtil1VKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUtil1VKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUtil1VKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de utilidad venta 2*/
    private void jTUtil2VFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil2VFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUtil2V.setSelectionStart(0);jTUtil2V.setSelectionEnd(jTUtil2V.getText().length());
        
    }//GEN-LAST:event_jTUtil2VFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad venta 2*/
    private void jTUtil2VFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil2VFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUtil2V.setCaretPosition(0);
        
        /*Si es cadena vacia el campo de utilidad venta 1 entonces coloca 0 en el campo*/        
        if(jTUtil2V.getText().compareTo("")==0)                   
            jTUtil2V.setText("0.0");                          
        
    }//GEN-LAST:event_jTUtil2VFocusLost

    
    /*Cuando se presiona una tecla en el campo de utilidad venta 2*/
    private void jTUtil2VKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUtil2VKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUtil2VKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de utilidad venta 3*/
    private void jTUtil3VFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil3VFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUtil3V.setSelectionStart(0);jTUtil3V.setSelectionEnd(jTUtil3V.getText().length());
        
    }//GEN-LAST:event_jTUtil3VFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad venta 3*/
    private void jTUtil3VFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil3VFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUtil3V.setCaretPosition(0);
        
        /*Si es cadena vacia el campo de utilidad venta 1 entonces coloca 0 en el campo*/        
        if(jTUtil3V.getText().compareTo("")==0)                   
            jTUtil3V.setText("0.0");                          
        
    }//GEN-LAST:event_jTUtil3VFocusLost

    
    /*Cuando se presiona una tecla en el campo de utilidad venta 3*/
    private void jTUtil3VKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUtil3VKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUtil3VKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de utilidad venta 4*/
    private void jTUtil4VFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil4VFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUtil4V.setSelectionStart(0);jTUtil4V.setSelectionEnd(jTUtil4V.getText().length());
        
    }//GEN-LAST:event_jTUtil4VFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad venta 4*/
    private void jTUtil4VFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil4VFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUtil4V.setCaretPosition(0);
        
        /*Si es cadena vacia el campo de utilidad venta 1 entonces coloca 0 en el campo*/        
        if(jTUtil4V.getText().compareTo("")==0)                   
            jTUtil4V.setText("0.0");                          
        
    }//GEN-LAST:event_jTUtil4VFocusLost

    
    /*Cuando se presiona una tecla en el campo de utilidad venta 4*/
    private void jTUtil4VKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUtil4VKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUtil4VKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de utilidad venta 5*/
    private void jTUtil5VFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil5VFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUtil5V.setSelectionStart(0);jTUtil5V.setSelectionEnd(jTUtil5V.getText().length());
        
    }//GEN-LAST:event_jTUtil5VFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad venta 5*/
    private void jTUtil5VFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil5VFocusLost
 
        /*Coloca el caret en la posiciòn 0*/
        jTUtil5V.setCaretPosition(0);
        
        /*Si es cadena vacia el campo de utilidad venta 1 entonces coloca 0 en el campo*/        
        if(jTUtil5V.getText().compareTo("")==0)                   
            jTUtil5V.setText("0.0");                          
        
    }//GEN-LAST:event_jTUtil5VFocusLost

    
    /*Cuando se presiona una tecla en el campo de utilidad venta 5*/
    private void jTUtil5VKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUtil5VKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUtil5VKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de utilidad venta 6*/
    private void jTUtil6VFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil6VFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUtil6V.setSelectionStart(0);jTUtil6V.setSelectionEnd(jTUtil6V.getText().length());
        
    }//GEN-LAST:event_jTUtil6VFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad venta 6*/
    private void jTUtil6VFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil6VFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTUtil6VFocusLost

    
    /*Cuando se presiona una tecla en el campo de utilidad venta 6*/
    private void jTUtil6VKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUtil6VKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUtil6VKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de utilidad venta 7*/
    private void jTUtil7VFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil7VFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUtil7V.setSelectionStart(0);jTUtil7V.setSelectionEnd(jTUtil7V.getText().length());
        
    }//GEN-LAST:event_jTUtil7VFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad venta 7*/
    private void jTUtil7VFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil7VFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUtil7V.setCaretPosition(0);
        
        /*Si es cadena vacia el campo de utilidad venta 1 entonces coloca 0 en el campo*/        
        if(jTUtil7V.getText().compareTo("")==0)                   
            jTUtil7V.setText("0.0");                          
        
    }//GEN-LAST:event_jTUtil7VFocusLost

    
    /*Cuando se presiona una tecla en el campo de utilidad venta 7*/
    private void jTUtil7VKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUtil7VKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUtil7VKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de utilidad venta 8*/
    private void jTUtil8VFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil8VFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUtil8V.setSelectionStart(0);jTUtil8V.setSelectionEnd(jTUtil8V.getText().length());
        
    }//GEN-LAST:event_jTUtil8VFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad venta 8*/
    private void jTUtil8VFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil8VFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUtil8V.setCaretPosition(0);
        
        /*Si es cadena vacia el campo de utilidad venta 1 entonces coloca 0 en el campo*/        
        if(jTUtil8V.getText().compareTo("")==0)                   
            jTUtil8V.setText("0");                          
        
    }//GEN-LAST:event_jTUtil8VFocusLost

    
    /*Cuando se presiona una tecla en el campo de utilidad venta 8*/
    private void jTUtil8VKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUtil8VKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUtil8VKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de utilidad venta 9*/
    private void jTUtil9VFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil9VFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUtil9V.setSelectionStart(0);jTUtil9V.setSelectionEnd(jTUtil9V.getText().length());
        
    }//GEN-LAST:event_jTUtil9VFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad venta 9*/
    private void jTUtil9VFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil9VFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUtil9V.setCaretPosition(0);
        
        /*Si es cadena vacia el campo de utilidad venta 1 entonces coloca 0 en el campo*/        
        if(jTUtil9V.getText().compareTo("")==0)                   
            jTUtil9V.setText("0");                          
        
    }//GEN-LAST:event_jTUtil9VFocusLost

    
    /*Cuando se presiona una tecla en el campo de utilidad venta 10*/
    private void jTUtil9VKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUtil9VKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUtil9VKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de utilidad venta 10*/
    private void jTUtil10VFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil10VFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTUtil10V.setSelectionStart(0);jTUtil10V.setSelectionEnd(jTUtil10V.getText().length());
        
    }//GEN-LAST:event_jTUtil10VFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de utilidad venta 10*/
    private void jTUtil10VFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTUtil10VFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTUtil10V.setCaretPosition(0);
        
        /*Si es cadena vacia el campo de utilidad venta 1 entonces coloca 0 en el campo*/        
        if(jTUtil10V.getText().compareTo("")==0)                   
            jTUtil10V.setText("0");                          
        
    }//GEN-LAST:event_jTUtil10VFocusLost

    private void jTUtil10VKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUtil10VKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTUtil10VKeyPressed
       
      
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGuar.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGuar;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTCost;
    private javax.swing.JTextField jTCostL;
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
    private javax.swing.JTextField jTUtil10V;
    private javax.swing.JTextField jTUtil1V;
    private javax.swing.JTextField jTUtil2V;
    private javax.swing.JTextField jTUtil3V;
    private javax.swing.JTextField jTUtil4V;
    private javax.swing.JTextField jTUtil5V;
    private javax.swing.JTextField jTUtil6V;
    private javax.swing.JTextField jTUtil7V;
    private javax.swing.JTextField jTUtil8V;
    private javax.swing.JTextField jTUtil9V;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
