/*Paquete*/
package report;

/*Importaciones*/

import java.sql.Timestamp;
import java.awt.Cursor;
import ptovta.Star;
import ptovta.Login;
import ptovta.Busc;
import ptovta.LoadinGral;
import static ptovta.Princip.bIdle;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;





/*Clase para reportear a los clientes*/
public class RepProvs extends javax.swing.JFrame 
{
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    
    
    
    
    /*Constructor sin argumentos*/
    public RepProvs() 
    {
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBAbr);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);                
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Reporte proveedores, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        
        
        /*Cambia el icono de la forma, ya sea el personalizado por el usuario o el de default del sistema*/
        if(new File(new java.io.File("").getAbsolutePath() + "\\Logo.jpg").exists())
        {
            setIconImage(Toolkit.getDefaultToolkit().getImage(new java.io.File("").getAbsolutePath() + "\\Logo.jpg"));
        }
        else
            setIconImage(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());
        
        /*Pon el foco del teclado en el combo de los tipos de reportes*/
        jComRep.grabFocus();
        
      
    }/*Fin de public RepCliens() */
        
            
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jTProv = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jBAbr = new javax.swing.JButton();
        jBProv = new javax.swing.JButton();
        jComRep = new javax.swing.JComboBox();
        jLAyu = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTJera = new javax.swing.JTextField();
        jBJera = new javax.swing.JButton();
        jDDe = new com.toedter.calendar.JDateChooser();
        jDA = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLblDate = new javax.swing.JLabel();

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
        jBSal.setNextFocusableComponent(jComRep);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, 110, 30));

        jTProv.setBackground(new java.awt.Color(204, 255, 204));
        jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProv.setNextFocusableComponent(jBProv);
        jTProv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProvFocusLost(evt);
            }
        });
        jTProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProvKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTProvKeyTyped(evt);
            }
        });
        jP1.add(jTProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 250, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Proveedor:");
        jP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 170, 20));

        jBAbr.setBackground(new java.awt.Color(255, 255, 255));
        jBAbr.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBAbr.setForeground(new java.awt.Color(0, 102, 0));
        jBAbr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/abr.png"))); // NOI18N
        jBAbr.setText("Abrir");
        jBAbr.setToolTipText("Abrir Reporte (Ctrl+A)");
        jBAbr.setNextFocusableComponent(jBSal);
        jBAbr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBAbrMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBAbrMouseExited(evt);
            }
        });
        jBAbr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbrActionPerformed(evt);
            }
        });
        jBAbr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAbrKeyPressed(evt);
            }
        });
        jP1.add(jBAbr, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 110, 30));

        jBProv.setBackground(new java.awt.Color(255, 255, 255));
        jBProv.setText("...");
        jBProv.setToolTipText("Buscar Cliente");
        jBProv.setNextFocusableComponent(jBAbr);
        jBProv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProvMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProvMouseExited(evt);
            }
        });
        jBProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProvActionPerformed(evt);
            }
        });
        jBProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProvKeyPressed(evt);
            }
        });
        jP1.add(jBProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, 30, 20));

        jComRep.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cátalogo de proveedores", "Reporte de proveedores" }));
        jComRep.setNextFocusableComponent(jTJera);
        jComRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComRepKeyPressed(evt);
            }
        });
        jP1.add(jComRep, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 280, -1));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 110, 20));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Jerarquía:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 130, -1));

        jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTJera.setNextFocusableComponent(jBJera);
        jTJera.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTJeraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTJeraFocusLost(evt);
            }
        });
        jTJera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTJeraKeyPressed(evt);
            }
        });
        jP1.add(jTJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 250, 20));

        jBJera.setBackground(new java.awt.Color(255, 255, 255));
        jBJera.setText("...");
        jBJera.setToolTipText("Búscar Jerárquia(s)");
        jBJera.setNextFocusableComponent(jTProv);
        jBJera.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBJeraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBJeraMouseExited(evt);
            }
        });
        jBJera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBJeraActionPerformed(evt);
            }
        });
        jBJera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBJeraKeyPressed(evt);
            }
        });
        jP1.add(jBJera, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 30, 20));

        jDDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDDeKeyPressed(evt);
            }
        });
        jP1.add(jDDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 140, 20));

        jDA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDAKeyPressed(evt);
            }
        });
        jP1.add(jDA, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 140, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("De:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 130, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("A:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 150, 20));

        jLblDate.setName(""); // NOI18N
        jP1.add(jLblDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        
        /*Cerrar el formulario*/
        this.dispose();        
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el campo del proveedor*/
    private void jTProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar proveedor*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBProv.doClick();
        /*Else, llama a la función para procesarlo normalmente*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProvKeyPressed

    
    /*Cuando se gana el foco del teclado en el control del proveedor*/
    private void jTProvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTProv.setSelectionStart(0);jTProv.setSelectionEnd(jTProv.getText().length());        
        
    }//GEN-LAST:event_jTProvFocusGained
            
        
    /*Cuando se tipea una tecla en el campo del proveedor*/
    private void jTProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProvKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTProvKeyTyped

    
    /*Cuando se presiona el botón de abrir*/
    private void jBAbrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrActionPerformed
        
        /*Abre la base de datos*/        
        Connection  con;  
        try 
        {
            con    = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
        } 
        catch(SQLException | HeadlessException e) 
        {                
            /*Agrega enel log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error al abrir la base de datos en jButtonProyectoPendienteActionPerformed por " + e.getMessage(), "Error al abrir base de datos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }

        /*Declara variables de la base de datos*/
        Statement   st;
        ResultSet   rs;        
        String      sQ          = ""; 

        /*Comprueba si el proveedor existe*/        
        try
        {
            sQ = "SELECT prov FROM provs WHERE CONCAT_WS('', ser, prov) = '" + jTProv.getText().trim() + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(!rs.next() && jTProv.getText().trim().compareTo("")!=0)
            {                
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;                    

                /*Coloca el borde rojo*/                               
                jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El proveedor: " + jTProv.getText().trim() + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTProv.grabFocus();             
                return;     
            }                        
        }
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;                    

            /*Agrega enel log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }            

        /*Comprueba si la clasificación de la jerarquía existe*/        
        try
        {
            sQ = "SELECT rut FROM clasjeraprov WHERE rut = '" + jTJera.getText().trim() + "'";                      
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces*/
            if(!rs.next() && jTJera.getText().trim().compareTo("")!=0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;                    
                
                /*Coloca el borde rojo*/                               
                jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La jerarquía: " + jTJera.getText().trim() + " no existe.", "No existe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Coloca el foco del teclado en el campo y regresa*/
                jTJera.grabFocus();             
                return;     
            }                        
        }
        catch(SQLException e)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;                    

            /*Agrega enel log*/
            Login.vLog(e.getMessage());

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }            
       
        /*Determina el reporte que será*/
        int iRep    = 0;
        if(jComRep.getSelectedItem().toString().compareTo("Cátalogo de proveedores")==0)
            iRep    = 1;  
        else if(jComRep.getSelectedItem().toString().compareTo("Reporte de proveedores")==0)
            iRep    = 2;
        if(iRep==2)
        {
            /*Verifica si se selecciono un rango de fechas*/
            if((jDDe.getDate()==null&&jDA.getDate()==null)||(jDDe.getDate()!=null&&jDA.getDate()==null)||(jDDe.getDate()==null&&jDA.getDate()!=null))
            {
                   jLblDate.setText("Seleccionar un rango de fechas");
                return;
            }
            /*Si no se selecciono un rango pero si se marco alguna fecha marcara error*/
            else
            {
                /*Obtiene la diferencia de ambas fechas en dias*/
                long diferencia =jDA.getDate().getTime()-jDDe.getDate().getTime();
                double diasDif=Math.floor(diferencia/(1000*60*60*24));
                /*Si sobrepasa el año envia un mensaje de error*/
                if(diasDif>365)
                {
                    jLblDate.setText("No se permite rango mayor a un año");
                    return;
                }
                /*Si la fecha inicial es mayor a la fecha final envia un mensaje de error*/
                else if(diasDif<0)
                {
                    jLblDate.setText("La fecha inicial no debe ser mayor a la fecha final.");
                    return;
                }
                /*Si no existio problema alguno limpia la etiqueta*/
                else
                    jLblDate.setText("");
            }
        }
        /*Declara variables final para el thread*/
        final String sProvFi        = jTProv.getText().trim();
        final String sJeraFi        = jTJera.getText().trim();
        final int    iRepFi         = iRep;
                
        /*Crea el thread para cargar el reporte en un hilo aparte*/
        Thread th = new Thread()
        {
            @Override
            public void run()
            {
                /*Abre la base de datos*/        
                Connection  con2;
                try 
                {
                    con2    = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
                } 
                catch(SQLException | HeadlessException e) 
                {    
                    /*Agrega enel log*/
                    Login.vLog(e.getMessage());

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error al abrir la base de datos en jButtonProyectoPendienteActionPerformed por " + e.getMessage(), "Error al abrir base de datos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
                    return;
                } 

                /*Obtiene la fecha de hoy*/               
                DateFormat da   = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date dat        = new Date();
                

                /*Muestra el formulario*/
                try
                {
                    /*Crea los parámetros que se pasarán*/
                    Map <String,Object> pa = new HashMap<>();             
                    pa.clear();
                    pa.put("PROV",          sProvFi);
                    pa.put("JERA",          sJeraFi);
                    pa.put("LOG",           getClass().getResource(Star.sIconDef).toString());
                    pa.put("FECH",          da.format(dat));  
                    
                    if(iRepFi==2)
                    {
                        Timestamp date1=new Timestamp(jDDe.getDate().getTime());
                        Timestamp date2=new Timestamp(jDA.getDate().getTime());
                        pa.put("FechaIni",      jDDe.getDate());
                        pa.put("FechaFin",      jDA.getDate());
                    }
                   
                    
                    /*Si es el reporte es de catálogo de proveedores entonces*/                    
                    JasperReport ja = null;
                    if(iRepFi==1)                                            
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptProvs.jrxml"));
                    else if(iRepFi==2)                                            
                        ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/rptHistComPro.jrxml"));
                                                
                    /*Llenalo y muestralo*/
                    JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)pa, con2);
                    JasperViewer v      = new JasperViewer(pr, false);
                    v.setExtendedState(JasperViewer.MAXIMIZED_BOTH);                    
                    /*Crea un imageicon con la imàgen del banner*/        
                    ImageIcon img       = new ImageIcon(new ImageIcon(getClass().getResource(Star.sIconDef)).getImage());        

                    /*Redimensiona la imàgen del banner*/
                    java.awt.Image im = img.getImage(); 
                    java.awt.Image newimg = im.getScaledInstance( 600, 350,  java.awt.Image.SCALE_SMOOTH );


                    v.setTitle("Reporte De Proveedores");
                    v.setIconImage(newimg);
                    v.setVisible(true);

                    /*Esconde el loading*/
                    Star.lCargGral.setVisible(false);
                }
                catch(JRException e)
                {
                    /*Esconde el loading*/
                    Star.lCargGral.setVisible(false);
                    
                    //Cierra la base de datos
                    if(Star.iCierrBas(con2)==-1)
                        return;                    
                    
                    /*Agrega enel log*/
                    Login.vLog(e.getMessage());

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por :" + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    return;
                }

                //Cierra la base de datos
                Star.iCierrBas(con2);
                
            }/*Fin de public void run()*/
        };
        
        /*Muestra la forma para simular que esta cargando el reporte*/
        Star.lCargGral = new LoadinGral("Cargando reporte de proveedores...");
        Star.lCargGral.setVisible(true);
            
        /*Comienza el hilo*/
        th.start();                        
        
    }//GEN-LAST:event_jBAbrActionPerformed

    
    /*Cuando se presiona una tecla en el botón de abrir*/
    private void jBAbrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAbrKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBAbrKeyPressed
        
    
    /*Cuando se presiona una tecla en el campo de buscar proveedor*/
    private void jBProvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProvKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProvKeyPressed

    
    /*Cuando se presiona el botón de buscar proveedor*/
    private void jBProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProvActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProv.getText(), 3, jTProv, null, null, "", null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBProvActionPerformed
        
    
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

         
    /*Cuando se presiona una tecla en el combo de los tipos de reportes*/
    private void jComRepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComRepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jComRepKeyPressed
                  
       
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

        
    /*Cuando se pierde el foco del teclado en el campo del proveedor*/
    private void jTProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProvFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTProv.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos*/                               
        if(jTProv.getText().compareTo("")!=0)
            jTProv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTProvFocusLost

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBAbrMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBAbr.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBAbrMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered
            
    
    /*Cuando el mouse sale del botón específico*/
    private void jBAbrMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBAbrMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBAbr.setBackground(colOri);
        
    }//GEN-LAST:event_jBAbrMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProvMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProv.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProvMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProvMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProvMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProv.setBackground(colOri);
        
    }//GEN-LAST:event_jBProvMouseExited

    
    /*Cuando se gana el foco del teclado en el campo de la jerarquía*/
    private void jTJeraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTJera.setSelectionStart(0);jTJera.setSelectionEnd(jTJera.getText().length());

    }//GEN-LAST:event_jTJeraFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la jerarquía*/
    private void jTJeraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTJeraFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTJera.setCaretPosition(0);

        /*Coloca el borde negro si tiene datos*/
        if(jTJera.getText().compareTo("")!=0)
            jTJera.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jTJeraFocusLost

    
    /*Cuando se presiona una tecla en el campo de la jerarquía*/
    private void jTJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTJeraKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar jeraquía*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBJera.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);

    }//GEN-LAST:event_jTJeraKeyPressed

    
    /*Cuando el mouse entra en el botón de la jerarquía*/
    private void jBJeraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseEntered

        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBJeraMouseEntered

    
    /*Cuando el mouse sale del botón de la jerarquía*/
    private void jBJeraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBJeraMouseExited

        /*Cambia el color del fondo del botón*/
        jBJera.setBackground(colOri);
        
    }//GEN-LAST:event_jBJeraMouseExited

    
    /*Cuando se presiona el botón de búscar jerarquía*/
    private void jBJeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBJeraActionPerformed

        /*Muestra la forma para ver las jerárquias*/
        cats.ClasJeraVis v = new cats.ClasJeraVis(jTJera.getText().trim(), jTJera, "prov");
        v.setVisible(true);
        
    }//GEN-LAST:event_jBJeraActionPerformed

    
    /*Cuando se presiona una tecla en el botón de la jerarquía*/
    private void jBJeraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBJeraKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBJeraKeyPressed

    private void jDDeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDDeKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jDDeKeyPressed

    private void jDAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDAKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jDAKeyPressed
                      
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar el formulario presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + A entonces presiona el botón de abrir*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_A)
            jBAbr.doClick();        
        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbr;
    private javax.swing.JButton jBJera;
    private javax.swing.JButton jBProv;
    private javax.swing.JButton jBSal;
    private javax.swing.JComboBox jComRep;
    private com.toedter.calendar.JDateChooser jDA;
    private com.toedter.calendar.JDateChooser jDDe;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLblDate;
    private javax.swing.JPanel jP1;
    private javax.swing.JTextField jTJera;
    private javax.swing.JTextField jTProv;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
