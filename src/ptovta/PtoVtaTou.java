//Paquete
package ptovta;

//Importaciones
import Ayus.AidPtoVta;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import static ptovta.Princip.bIdle;




/*Clase que controla el punto de vta touch*/
public class PtoVtaTou extends javax.swing.JFrame 
{
    //Contiene los datos de boleto perdido
    public String           sRecib      = "";
    public String           sMarc       = "";
    public String           sMod        = "";
    public String           sColo       = "";
    public String           sPlacs      = "";
    public String           sNom        = "";
    public String           sTarCirc    = "";
    public String           sNumLic     = "";
    public String           sTel        = "";
    public String           sDirPart    = "";
    public String           sDirOfi     = "";
    public String           sTelOfi     = "";    
    
    /*Contiene la dirección de la forma para ver imágen en otra vista*/
    private ImgVis          v;
    
    /*Contador de celleditor*/
    private int             iContCellEd;
    
    /*Variable de apoyo para control el descuento*/
    private String          sDescGlo;
    
    /*Contiene la dirección de la forma actual*/
    private javax.swing.JFrame jFram;
    
    //Contiene la dirección a si misma
    private PtoVtaTou       frmMe;
    
    /*Declara variables originales*/
    private String          sCantOri;    
    private String          sDescripOri;
    private String          sDescOri;    
    private String          sPreOri;        
    
    /*Declara variables de threads*/
    private Thread          tEstCon     = null;
    private Thread          th          = null;
    
    /*Variable para saber si se tiene que editar la descripción o no*/
    private boolean         bDescripGlo = false;
    
        
        
    
    /*Constructor del punto de vta touch*/
    public PtoVtaTou(JFrame jPri) 
    {                                      
        /*Inicializa los componentes gráficos*/
        initComponents();

        //Establece el cliente mostrador en el control
        jTCli.setText(Star.sCliMostG);
        
        //Coloca el nombre de cliente mostrador en el control de nombre del cliente
        jTNomb.setText(Star.sNomCliMostG);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Punto de venta, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);                        
        
        //Inicialmente la serie de la empresa será de cliente mostrador
        jTSer.setText   (Star.sSerMostG);
        
        /*Esconde la columna del id*/
        jTab.getColumnModel().getColumn(15).setMinWidth(0);
        jTab.getColumnModel().getColumn(15).setMaxWidth(0);
        
        //Inicializa todos los controles de boleto perdido
        jTRecib.setText     ("");
        jTMarc.setText      ("");
        jTMod.setText       ("");
        jTColoAut.setText   ("");
        jTPlacs.setText     ("");
        jTNom.setText       ("");
        jTTarCirc.setText   ("");
        jTNumLic.setText    ("");
        jTTel.setText       ("");
        jTDirPart.setText   ("");
        jTDirOfi.setText    ("");
        jTTelOfi.setText    ("");
        jTImpo.setText      ("");
                
        //Esconde todos los controles de boleto perdido
        jTRecib.setVisible  (false);        
        jTMarc.setVisible   (false);
        jTMod.setVisible    (false);
        jTColoAut.setVisible(false);
        jTPlacs.setVisible  (false);
        jTNom.setVisible    (false);
        jTTarCirc.setVisible(false);
        jTNumLic.setVisible (false);
        jTTel.setVisible    (false);
        jTDirPart.setVisible(false);
        jTDirOfi.setVisible (false);
        jTTelOfi.setVisible (false);
        jTImpo.setVisible   (false);
        
        //Esconde otros controles
        jTTotCost.setVisible(false);               
        jTCantLot.setVisible(false);                
        jTList.setVisible   (false);
        
        /*Esconde la columna del costo*/
        jTab.getColumnModel().getColumn(22).setMinWidth(0);
        jTab.getColumnModel().getColumn(22).setMaxWidth(0);
        
        /*Esconde la columna del id del pedimento*/
        jTab.getColumnModel().getColumn(23).setMinWidth(0);
        jTab.getColumnModel().getColumn(23).setMaxWidth(0);
        
        /*Esconde el control de la garantía*/
        jTGara.setVisible       (false);        
        
        /*Esconde el control del costo total*/
        jTGara.setVisible       (false);        
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Esconde algunos controles*/
        jTLot.setVisible        (false);        
        jTPedimen.setVisible    (false);        
        jTCadu.setVisible       (false);        
        jTId.setVisible         (false);                
        jTComenSer.setVisible   (false);                
        jTSerProd.setVisible    (false);
        
        /*Esconde el control de almacén, talla y color*/
        jTAlma.setVisible(false);
        jTTall.setVisible(false);
        jTColo.setVisible(false);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;        
        
        /*La ventana se mostrará maximizada*/
        setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);

        /*Inicialmente los campos de subtotal y de impuesto no serán visibles*/                
        jLImg.setVisible        (false);
        jTCodProd.setVisible    (false);
            
        /*Listener para el combobox de unidades*/
        jComUnid.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                //Obtiene todas las unidades y cargalas en el combo
                if(Star.iCargUnidCom(con, jComUnid)==-1)
                    return;
                
                //Cierra la base de datos
                Star.iCierrBas(con);                
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Establece el tamaño de la columna de la tabla*/        
        jTab.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(135);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(215);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(50);
        jTab.getColumnModel().getColumn(14).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(17).setPreferredWidth(110);
        jTab.getColumnModel().getColumn(18).setPreferredWidth(110);
        jTab.getColumnModel().getColumn(19).setPreferredWidth(120);
        jTab.getColumnModel().getColumn(20).setPreferredWidth(125);        
        jTab.getColumnModel().getColumn(21).setPreferredWidth(120);
        jTab.getColumnModel().getColumn(24).setPreferredWidth(120);
        
        /*Esconde el campo de la serie del cliente*/
        jTSer.setVisible        (false);
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Poner el foco del teclado en el campo del producto*/
        jTProd.grabFocus();
        
        /*Para que las tablas tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
                    
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Obtiene cuál es la moneda nacional*/
        String sCodMN   = Star.sGetMonNac(con);
        
        //Si hubo error entonces regresa
        if(sCodMN==null)
            return;
        
        //Selecciona la moneda nacional
        jTMon.setText(sCodMN);
        
        //Obtiene todas las unidades y cargalas en el combo
        if(Star.iCargUnidCom(con, jComUnid)==-1)
            return;

        /*Selecciona la pieza en el combo*/
        jComUnid.setSelectedItem("PIEZA");

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene el almacén del punto de venta*/        
        try
        {
            sQ = "SELECT extr FROM confgral WHERE clasif = 'vtas' AND conf = 'almapto'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el almacén y colocalo en el control*/
            if(rs.next())
                jTAlma.setText(rs.getString("extr"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }            
        
        /*Comprueba si se pueden o no editar las columnas de la descripción del producto*/
        try
        {                  
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'descrippto'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/                
            if(rs.next())
            {
                /*Si se tienen que poder editar entonces coloca la bandera global para saber que se puede editar*/                
                if(rs.getString("val").compareTo("1")==0)                
                    bDescripGlo    = true;                
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
        
        /*Comprueba si se tiene que mostrar o no el botón de nuevo cliente*/
        try
        {                  
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'empspto'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/                
            if(rs.next())
            {
                /*Si no se tiene que mostrar entonces*/
                if(rs.getString("val").compareTo("0")==0)
                    jBNewEmp.setVisible(false);
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
        
        /*Comprueba si se tiene que mostrar o no el catálogo general*/
        try
        {                  
            sQ = "SELECT val FROM confgral WHERE conf = 'catgralpvta'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces si existe ya un corta Z*/                
            if(rs.next())
            {
                /*Si se tiene que mostrar entonces que sea visible y que tenga parada de tabulador*/
                if(rs.getString("val").compareTo("1")==0)
                {
                    /*El campo*/
                    jTBuscGral.setVisible   (true);
                    jTBuscGral.setFocusable (true);
                    
                    /*El botón*/
                    jBBuscGral.setVisible   (true);
                    jBBuscGral.setFocusable (true);
                }
                /*Else deshabilitalo*/
                else
                {
                    /*El campo*/
                    jTBuscGral.setVisible   (false);
                    jTBuscGral.setFocusable (false);
                    
                    /*El botón*/
                    jBBuscGral.setVisible   (false);
                    jBBuscGral.setFocusable (false);
                }

            }/*Fin de while (rs.next())*/
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
        catch(NumberFormatException expnNumForm)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnNumForm.getMessage(), Star.sErrNumForm, expnNumForm.getStackTrace(), con);                                
            return;                                                                                                     
        }
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Almacena la ruta en una temporal para siempre tenerla disponible*/
        String sTmpCarp = sCarp;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")!=0)
        {
            /*Si la carpeta de las imágenes no existe entonces crea la ruta*/
            sCarp                   += "\\Imagenes";
            String sCarpTemp        = sCarp;
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();

            /*Si la carpeta del logotipo de la cliente no existe entonces crea la ruta*/
            sCarp                    += "\\Logotipo Empresa";
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();

            /*Si la carpeta de la cliente en específico no existe entonces crea la ruta*/
            sCarp                    += "\\" + Login.sCodEmpBD;
            if(!new File(sCarp).exists())
                new File(sCarp).mkdir();

            /*Completa la ruta con el nom del logo*/
            sCarp                     += "\\Logo.jpg";
            
            /*Si la carpeta de la cédula no existe entonces crea la ruta*/
            sCarpTemp                  += "\\Cedula";
            if(!new File(sCarpTemp).exists())
                new File(sCarpTemp).mkdir();

            /*Si la carpeta de la cliente en específico no existe entonces crea la ruta*/
            sCarpTemp                  += "\\" + Login.sCodEmpBD;
            if(!new File(sCarpTemp).exists())
                new File(sCarpTemp).mkdir();

            /*Completa la ruta con el nombre del logo*/
            sCarpTemp                  += "\\Cedula.jpg";
                        
        }/*Fin de if(sCarp.compareTo("")!=0)*/                        
                                
        /*Obtiene si se deben de colocar imágenes en las líneas del punto de venta*/
        boolean bImgLin = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'imglin'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Establece la bandera para saber si se debe o no poner imágenes en las líneas*/
                if(rs.getString("val").compareTo("1")==0)
                    bImgLin = true;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
        
        /*Obtiene todas las líneas de la base de datos*/
	try
        {                  
            sQ = "SELECT * FROM lins";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {   
                /*Crea la ruta a la imágen de la línea*/
                String sImg= sTmpCarp + "\\imgs\\Lineas\\" + Login.sCodEmpBD + "\\" + rs.getString("cod");                
                
                /*Crea el botón*/
                JButton bot = new JButton(rs.getString("descrip"));                
                                
                /*Si la ruta existe y si se pueden mostrar imágenes en las líneas entonces*/
                if( new File(sImg).exists() && bImgLin)
                {          
                    /*Si existe una imágen para la línea entonces*/
                    if( new File(sImg).list().length > 0)
                    {                    
                        /*Obtiene la lista del archivo*/
                        String sArchs []    = new File(sImg).list();
                        
                        /*Crea  el icono en el botón con un tamaño personalizado*/                        
                        ImageIcon img       = new ImageIcon(sImg + "\\" + sArchs[0]);
                                                
                        /*Crea la imágen para redimensionar la imágen del icono*/
                        java.awt.Image im = img.getImage(); 
                        java.awt.Image newimg = im.getScaledInstance( 125, 115,  java.awt.Image.SCALE_SMOOTH );  

                        /*Crea el nuevo ícono*/
                        img                 = new ImageIcon(newimg);

                        /*Crea el label para que contenga el icono*/
                        javax.swing.JLabel jLImg2    = new javax.swing.JLabel();
                        
                        /*Agrega el icono a la imágen*/
                        jLImg2.setIcon(img);

                        /*Crea el botón vacio*/
                        bot = new JButton();
                        
                        /*Establece el layout*/
                        bot.setLayout(new java.awt.BorderLayout());                                                

                        /*Agrega el label con el icono al layput del botón*/
                        bot.add(jLImg2);
                        
                        /*Coloca el texto del botón con la descripción de la línea*/
                        bot.setText(rs.getString("descrip"));                                                                                                                                                                                                                                                                                         
                    }                    
                }   
                
                /*Establece la descripción de la línea sobre el botón*/                                
                bot.setHorizontalTextPosition   (JButton.CENTER);
                bot.setVerticalTextPosition     (JButton.CENTER);
    
                /*Establece el tamaño del botón*/
                bot.setPreferredSize            (new Dimension(110, 110));
                
                /*Establece el color de fondo del botón*/
                bot.setBackground               (Color.white);
                
                /*Establece el action listener para el botón*/
                bot.addActionListener(new java.awt.event.ActionListener() 
                {
                    /*Cuando sucede una acción en el botón*/
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) 
                    {
                        /*Obtiene la descripción del botón*/
                        final String sDescrip    = evt.getActionCommand();
                                
                        /*Lo pongo en un thread para simular velocidad*/
                        (new Thread()
                        {
                            @Override
                            public void run()
                            {                                
                                /*Borra todo lo que tiene el panel de prods*/
                                jPanProds.removeAll();
                                
                                //Abre la base de datos
                                Connection  con = Star.conAbrBas(true, false);

                                //Si hubo error entonces regresa
                                if(con==null)
                                    return;

                                //Declara variables de la base de datos
                                Statement   st;
                                ResultSet   rs;                                
                                String      sQ;                                 
                                
                                /*Obtiene el código de la línea en base a su descripción*/
                                String sLin = "";
                                try
                                {
                                    sQ = "SELECT cod FROM lins WHERE descrip = '" + sDescrip + "'";                        
                                    st = con.createStatement();
                                    rs = st.executeQuery(sQ);
                                    /*Si hay datos entonces obtiene el resultado*/
                                    if(rs.next())
                                        sLin         = rs.getString("cod");
                                }
                                catch(SQLException expnSQL)
                                {
                                    //Procesa el error y regresa
                                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                                    return;                                                                                                                                         
                                }  
                                
                                //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
                                String sCarp    = Star.sGetRutCarp(con);                    

                                //Si hubo error entonces regresa
                                if(sCarp==null)
                                    return;
//                                jPanProds.removeAll();
//                                jPanProds.revalidate();
                                //Obtiene todos los productos que esten con esa línea en específico
                                try
                                {
                                    sQ = "SELECT prod FROM prods WHERE lin = '" + sLin + "'";                        
                                    st = con.createStatement();
                                    rs = st.executeQuery(sQ);
                                    /*Si hay datos*/
                                    while(rs.next())
                                    {                                        
                                        /*Crea la ruta a la imágen del producto*/
                                        String sImg = sCarp + "\\imgs\\Productos\\" + Login.sCodEmpBD + "\\" + rs.getString("prod");                
                                        
                                        /*Crea el botón*/
                                        JButton bot = new JButton(rs.getString("prod"));
                                                                                
                                        /*Si la ruta existe entonces*/
                                        if( new File(sImg).exists())
                                        {          
                                            /*Si existe una imágen para el producto entonces*/
                                            if( new File(sImg).list().length > 0)
                                            {                    
                                                /*Obtiene la lista del archivo*/
                                                String sArchs []    = new File(sImg).list();

                                                /*Crea  el icono*/                        
                                                ImageIcon img       = new ImageIcon(sImg + "\\" + sArchs[0]);
                                                
                                                /*Crea la imágen para redimensionar la imágen del icono*/
                                                java.awt.Image im = img.getImage(); 
                                                java.awt.Image newimg = im.getScaledInstance( 87, 87,  java.awt.Image.SCALE_SMOOTH );  
                                                
                                                /*Crea el nuevo ícono*/
                                                img                 = new ImageIcon(newimg);
                                                
                                                /*Crea el label para que contenga el icono*/
                                                javax.swing.JLabel jLImg        = new javax.swing.JLabel();
                                                
                                                /*Agrega el icono a la imágen*/
                                                jLImg.setIcon(img);
                                                
                                                /*Crea el botón vacio*/
                                                bot = new JButton();
                                                
                                                /*Establece el layout*/
                                                bot.setLayout(new java.awt.BorderLayout());                                                
                                                
                                                /*Agrega el label con el icono al layput del botón*/
                                                bot.add(jLImg);
                                                
                                                /*Coloca el texto del botón con el código del producto*/
                                                bot.setText(rs.getString("prod"));
                                            }                    
                                        }   
                
                                        /*Establece el tamaño del botón*/
                                        bot.setPreferredSize(new Dimension(110, 110));

                                        /*Establece el color de fondo del botón*/
                                        bot.setBackground(Color.white);

                                        /*Inicialmente estará escondido el botón para que no se vea cuando se agrega*/
                                        bot.setVisible(false);

                                        /*Agrega un acción listener al botón*/
                                        bot.addActionListener(new java.awt.event.ActionListener() 
                                        {
                                            /*Cuando sucede una acción en el botón*/
                                            @Override
                                            public void actionPerformed(java.awt.event.ActionEvent evt) 
                                            {                                                                                        
                                                /*Coloca el código del producto en el campo del código del producto*/
                                                jTProd.setText(evt.getActionCommand());

                                                /*Función escalable para cuando sucede una acción en el campo del código del producto*/
                                                vFunEsc();

                                                /*Coloca el foco del teclado en el campo del codigo del producto*/
                                                jTProd.grabFocus();
                                            }
                                        });

                                        /*Agregalo al panel*/
                                        jPanProds.add(bot);

                                        /*Para que se scrollie bien el panel de prods*/
                                        jPanProds.scrollRectToVisible(bot.getBounds());

                                        /*Ponlo visible ahora si el botón*/
                                        bot.setVisible(true);                                                                
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

                                /*Coloca el foco del teclado en el campo del codigo del producto*/
                                jTProd.grabFocus();

                                /*Valida el control de prods*/
                                //jPanProds.validate();
                                jPanProds.revalidate();
                                jPanProds.repaint();

                            }/*Fin de Thread th = new Thread()*/
                        }).start();                        
                                                                
                    }/*Fin de public void actionPerformed(java.awt.event.ActionEvent evt) */

                    /*Cuando se presiona una tecla en el botón entonces*/
                    public void keyPressed(java.awt.event.KeyEvent evt) 
                    {
                        /*Presiona el botón de salir*/
                        jBSal.doClick();
                    }
                        
                 });                                

                /*Agregalo al panel*/
                jPanelLin.add(bot);
                
                /*Para que se scrollie bien el panel*/
                jPanelLin.scrollRectToVisible(bot.getBounds());                                                
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
        
        /*Comprueba si se debe o no mostrar la barra lateral*/
        try
        {                  
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf ='barlat'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos, entonces si existe ya un corta Z*/                
            if(rs.next())
            {
                //Si no se debe mostrar entonces esconde los controles
                if(rs.getString("val").compareTo("0")==0)
                {                    
                    jScrollProds.setVisible     (false);
                    jScrollPaneLin.setVisible   (false);                                        
                }                                     
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
        catch(NumberFormatException expnNumForm)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnNumForm.getMessage(), Star.sErrNumForm, expnNumForm.getStackTrace(), con);                                
            return;                                                                                                     
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Crea el listener para cuando se cambia de selección en la tabla de partidas*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {
                /*Carga la imágen de la fila de la tabla de líneas*/
                vCargImgFi();               
            }
        });
              
        /*Crea el listener para la tabla de partidas, para cuando se modifique una celda guardarlo en la base de datos el cambio*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección inicialmente entonces regresa*/                    
                if(jTab.getSelectedRow()==-1)
                    return;

                /*Obtén la propiedad que a sucedio en el control*/
                String property = event.getPropertyName();                                
                                
                /*Si el evento fue por entrar en una celda de la tabla*/
                if("tableCellEditor".equals(property)) 
                {                    
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {
                        //Obtén algunos datos originales
                        sCantOri                = jTab.getValueAt(jTab.getSelectedRow(), 0).toString();                        
                        sDescripOri             = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();                                                                        
                        sDescOri                = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                                        
                        sPreOri                 = jTab.getValueAt(jTab.getSelectedRow(), 5).toString();                        
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Obtén los datos que pueden cambiar*/                        
                        String sCant            = jTab.getValueAt(jTab.getSelectedRow(), 0).toString();                       
                        String sDe              = jTab.getValueAt(jTab.getSelectedRow(), 4).toString();
                        String sPre             = jTab.getValueAt(jTab.getSelectedRow(), 5).toString().replace("$", "").replace(",", "");                                                  
                        
                        /*Si la descripcion no puede cambiar entonces coloca la que tenía originalmente*/
                        if(!bDescripGlo)
                            jTab.setValueAt(sDescripOri, jTab.getSelectedRow(), 3);
                        
                        /*Si el campo de cantidad no es un nùmero entonces*/
                        try
                        {
                            Double.parseDouble(sCant);
                        }   
                       catch(NumberFormatException expnNumForm)
                        {                        
                            /*Coloca la cantidad original y regresa*/
                            jTab.setValueAt(sCantOri, jTab.getSelectedRow(), 0);
                            iContCellEd = 1;
                            return;
                        }                        
                        
                        /*Si el campo del descuento no es un nùmero entonces*/
                        try
                        {
                            Double.parseDouble(sDe);
                        }   
                       catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca la cantidad original y regresa*/
                            jTab.setValueAt(sDescOri, jTab.getSelectedRow(), 4);
                            iContCellEd = 1;
                            return;
                        }                                               
                        
                        /*Si el campo del precio no es un nùmero entonces*/
                        try
                        {
                            Double.parseDouble(sPre);
                        }   
                       catch(NumberFormatException expnNumForm)
                        {
                            /*Coloca la cantidad original y regresa*/
                            jTab.setValueAt(sPreOri, jTab.getSelectedRow(), 5);
                            iContCellEd = 1;
                            return;
                        }                        
                        
                        /*Si la cantidad es menor a 0 entonces*/
                        if(Double.parseDouble(sCant) <= 0)
                         {
                             /*Coloca el valor oroginal que tenía*/
                             jTab.setValueAt(sCantOri, jTab.getSelectedRow(), 0);
                             iContCellEd = 1;
                             return;
                         }                           

                        /*Si el descuento es menor a 0 o mayor a 100 entonces*/
                        if(Double.parseDouble(sDe) < 0 || Double.parseDouble(sDe) > 100)
                         {
                             /*Coloca el valor oroginal que tenía*/
                             jTab.setValueAt(sDescOri, jTab.getSelectedRow(), 4);
                             iContCellEd = 1;
                             return;
                         }   
                        
                        /*Si el precio es menor o igual 0 entonces*/
                        if(Double.parseDouble(sPre) <= 0)
                         {
                             /*Coloca el valor oroginal que tenía*/
                             jTab.setValueAt(sPreOri, jTab.getSelectedRow(), 5);
                             iContCellEd = 1;
                             return;
                         }   
                            
                        /*Si el producto es por lote y pedimento entonces*/
                        if(Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 23).toString())>0)
                        {
                            /*Si la cantidad que se quiere insertar es mayor a la cantidad del lote permitido entonces*/
                            if(Double.parseDouble(sCant)>Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 23).toString()))
                            {
                                /*Coloca la cantidad original que tenía*/
                                jTab.setValueAt(sCantOri, jTab.getSelectedRow(), 0);
                                
                                /*Mensajea y regresa*/
                                JOptionPane.showMessageDialog(null, "La cantidad de lote a insertar del producto es mayor a la permitida.", "Lote y pedimento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                
                                return;
                            }

                            /*Obtiene la diferencia de lo que ya esta cargado y lo que se quiere cargar*/
                            String sDif = Double.toString(Double.parseDouble(sCant) + Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 0).toString()));

                            /*Si la diferencia es mayor que lo que se quiere insertar entonces*/
                            if(Double.parseDouble(sDif)>Double.parseDouble(jTab.getValueAt(jTab.getSelectedRow(), 23).toString()))
                            {
                                /*Coloca la cantidad original que tenía*/
                                jTab.setValueAt(sCantOri, jTab.getSelectedRow(), 0);
                                
                                /*Mensajea y regresa*/
                                JOptionPane.showMessageDialog(null, "La cantidad de lote a insertar: " + sCant + " mas la cantidad ya cargada: " + jTab.getValueAt(jTab.getSelectedRow(), 0).toString() + " del producto es mayor a la permitida: " + jTab.getValueAt(jTab.getSelectedRow(), 26).toString(), "Lote y pedimento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                                return;                                                
                            }                                

                        }/*Fin de if(Double.parseDouble(sCantOriLot)>0)*/                                                
                                    
                        //Abre la base de datos
                        Connection  con = Star.conAbrBas(true, false);

                        //Si hubo error entonces regresa
                        if(con==null)
                            return;

                        //Declara variables de la base de datos
                        Statement   st;
                        ResultSet   rs;                        
                        String      sQ; 
                        
                        /*Obtiene si el producto se puede vender al bajo del costo o no*/                        
                        try
                        {
                            sQ = "SELECT bajcost, cost FROM prods WHERE prod = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + "'";                        
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos*/
                            if(rs.next())
                            {
                                /*Si no se puede vender abajo del cost entonces*/
                                if(rs.getString("bajcost").compareTo("0")==0)
                                {                                    
                                    /*Si el precio es menor al costo entonces*/
                                    if(Double.parseDouble(sPre) < Double.parseDouble(rs.getString("cost")))
                                    {
                                        //Cierra la base de datos
                                        if(Star.iCierrBas(con)==-1)
                                            return;
                                        
                                        /*Mensajea*/
                                        JOptionPane.showMessageDialog(null, "No se puede vender a bajo del costo este producto.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                                        
                                        /*Coloca el valor original que tenía y regresa*/
                                        jTab.setValueAt(sPreOri, jTab.getSelectedRow(), 5);                                                                                                                        
                                        return;
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
                        
                        /*Obtiene el descuento posible para esta usuario*/
                        String sDesc    = "100";
                        try
                        {
                            sQ = "SELECT descu, habdesc FROM estacs WHERE estac = '" + Login.sUsrG + "'" ;                        
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos entonces*/
                            if(rs.next())
                            {                                
                                /*Si el descuento esta deshabilitado para esta usuario entonces*/
                                if(rs.getString("habdesc").compareTo("0")==0)
                                {
                                    //Cierra la base de datos
                                    if(Star.iCierrBas(con)==-1)
                                        return;

                                    /*Coloca le descuento original que tenía*/
                                    jTab.setValueAt(sDescOri, jTab.getSelectedRow(), 4);

                                    /*Mensajea y regresa*/
                                    JOptionPane.showMessageDialog(null, "El descuento para el usuario: " + Login.sUsrG + " esta deshabilitado.", "Descuento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                         
                                    return;                          
                                }
                                
                                /*Obtiene el descuento*/
                                sDesc           = rs.getString("descu");                                                                                                                                
                            }
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                                                                 
                        }                          
                        
                        /*Obtiene la existencia actual del producto*/
                        String  sExist  = "0";
                        try
                        {
                            sQ      = "SELECT IFNULL(exist, 0 ) AS exist FROM prods WHERE prod = '" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString() + "' AND alma = '" + jTab.getValueAt(jTab.getSelectedRow(), 9).toString() + "'";                        
                            st      = con.createStatement();
                            rs      = st.executeQuery(sQ);
                            /*Si hay datos entonces obtiene el resultado*/
                            if(rs.next())
                                sExist  = rs.getString("exist");
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                                                                 
                        }  
                        
                        //Obtiene la configuración para saber si mostrar o no el mensaje de existencias negativas
                        int iRes    = Star.iGetConfExistNeg(con);
                        
                        //Si hubo error entonces regresa
                        if(iRes==-1)
                            return;
                        
                        //Si se tiene que mostrar entonces coloca la bandera
                        boolean bSi     = false;
                        if(iRes==1)
                            bSi         = true;                        
                        
                        //Cierra la base de datos
                        if(Star.iCierrBas(con)==-1)
                            return;

                        /*Si se tiene que mostrar el mensaje de existencias negativas entonces*/
                        if(bSi)
                        {
                            /*Mensajea para que el usuario este enterado y si el artículo no es de kit por que si es kit no debe de validar esto*/                                                
                            if(Integer.parseInt(sCant) > Integer.parseInt(sExist))
                                JOptionPane.showMessageDialog(null, "No hay existencias suficientes para este producto.", "Existencia", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                        } 

                        /*Si el descuento que se quiere dar es mayor al permitido entonces*/
                        if(Double.parseDouble(sDe) > Double.parseDouble(sDesc))
                        {
                            //Cierra la base de datos
                            if(Star.iCierrBas(con)==-1)
                                return;

                            /*Coloca le descuento original que tenía*/
                            jTab.setValueAt(sDescOri, jTab.getSelectedRow(), 4);

                            /*Mensajea y regresa*/
                            JOptionPane.showMessageDialog(null, "El descuento máximo permitido para el usuario: " + Login.sUsrG + " es: " + sDesc + "%.", "Descuento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                         
                            return;                            
                        }
                        
                        /*Genera los importes correctos*/
                        vGenImp();
                        
                        /*Recalcula el total basandonos en la suma de los importes*/
                        vRecTot();
                                                                                                
                    }/*Fin de else*/
                                                
                    /*Si el contador está en 2 entonces el cell editor va de salida*/
                    if(iContCellEd >= 2)  
                    {
                        /*Reinicia el conteo*/
                        iContCellEd = 1;
                        
                        /*Pon el foco del teclado en el control del producto*/
                        jTProd.grabFocus();
                        
                    }/*Fin de if(iContCellEd >= 2)  */                      
                    else
                        ++iContCellEd;                                                                                
                        
                }/*Fin de if("tableCellEditor".equals(property)) */
                
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */
            
        };
        
        /*Establece el listener para la tabla de partidas*/
        jTab.addPropertyChangeListener(pro);       
            
        /*Comprueba si existe ya un ingreso de caja por medio de un thread*/
        (new Thread()
        {
            @Override
            public void run()
            {               
                //Abre la base de datos
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 
                
                /*Comprueba si existe impresora asociada para el usuario actual*/        
                try
                {
                    sQ = "SELECT imptic FROM estacs WHERE estac = '" + Login.sUsrG + "'";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces mensajea*/
                    if(!rs.next())
                        JOptionPane.showMessageDialog(null, "No se a definido impresora para el usuario: " + Login.sUsrG + ". Entonces no se podra imprimir las ventas.s", "Impresora",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;                                                                                                                         
                }

                //Obtiene si el usuario tiene correo asociado
                int iRes    = Star.iUsrConfCorr(con, Login.sUsrG);

                //Si hubo error entonces regresa
                if(iRes==-1)
                    return;

                //Comprueba si la configuración de mostrar el mensaje esta habilitado o no
                int iMosMsj = Star.iMostMsjCorrUsr(con);

                //Si hubo error entonces regresa
                if(iMosMsj==-1)
                    return;                        

                //Si no tiene correo asociado entonces solamente mensajea
                if(iRes==0 && iMosMsj==1)
                    JOptionPane.showMessageDialog(null, "No se a definido correo electrónico para el usuario: " + Login.sUsrG + ". " + System.getProperty( "line.separator" ) + "Entonces no se podrán enviar facturas generadas por correo electrónico.", "Correo electrónico",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                        
                
                /*Declara variables*/
                String sConsecCor                 = "1";

                /*Obtiene el consecutivo del corte Z*/
                boolean bSi  = false;
                try
                {                  
                    sQ = "SELECT numcort FROM cortszx WHERE cort = 'Z' AND regis = 0";
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos, entonces si existe ya un corta Z*/                
                    if(rs.next())
                    {
                        /*Obtiene el consecutivo del fluj y pon la bandera*/
                        sConsecCor          = rs.getString("numcort");     
                        bSi                 = true; 
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;                                                                                                                         
                }

                /*Si no existe un corte entonces*/
                if(!bSi)
                {                        
                    /*Obtiene el máximo del corta*/                
                    try
                    {                  
                        sQ = "SELECT numcort + 1 as numcort FROM cortszx WHERE cort = 'Z'";
                        st = con.createStatement();
                        rs = st.executeQuery(sQ);
                        /*Si hay datos, entonces obtiene el resultado*/                
                        if(rs.next())
                            sConsecCor            = rs.getString("numcort");                             
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                        return;                                                                                                                             
                    }

                    /*Inserta el corte Z*/
                    try 
                    {                
                        sQ = "INSERT INTO cortszx (numcort,                             cort, regis,      totvtas,     totingr,       totegre,     totcaj,     impue,      estac,                                           sucu,                                               nocaj ) " + 
                                     "VALUES(" + sConsecCor.replace("'", "''") + ",     'Z',   0,          0,           0,             0,            0,         0,     '" + Login.sUsrG.replace("'", "''") + "','" +    Star.sSucu.replace("'", "''") + "','" +       Star.sNoCaj.replace("'", "''") + "')";                    
                        st = con.createStatement();
                        st.executeUpdate(sQ);
                     }
                     catch(SQLException expnSQL) 
                     { 
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                        return;                                                                                                                             
                     }                               

                }/*Fin de if(!bSi)*/

                boolean bSiI = false;
                try
                {
                    sQ = "SELECT concep FROM fluj WHERE ing_eg = 'I' AND corta = 0";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces coloca la bandera*/
                    if(rs.next())
                        bSiI = true;
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;                                                                                                                         
                }

                /*Si no existe ingreso de caja entonces*/
                if(!bSiI)
                {       
                    /*Contiene la cantidad a insertar si lo tiene que hacer automáticamente*/
                    String sCant    = "";

                    /*Obtiene si se tiene que insertar automáticamente dinero en el cajón o no*/                
                    boolean bSiInsCaj   = false;
                    try
                    {                  
                        sQ = "SELECT val, extr FROM confgral WHERE clasif = 'vtas' AND conf = 'insautcaj'";
                        st = con.createStatement();
                        rs = st.executeQuery(sQ);
                        /*Si hay datos, entonces*/                
                        if(rs.next())
                        {
                            /*Coloca la bandera correcta*/
                            if(rs.getString("val").compareTo("1")==0)
                                bSiInsCaj = true;

                            /*Obtiene la cantidad a insertar en la caja automáticamente*/
                            sCant   = rs.getString("extr");
                        }
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                        return;                                                                                                                             
                    }

                    /*Si se tiene que insertar automáticamente el dinero en el cajón entonces*/
                    if(bSiInsCaj)
                    {
                        /*Ingresa en el flujo lo de la caja inicial*/
                        try 
                        {                    
                            sQ = "INSERT INTO fluj(concep,    tipdoc,   norefer,    ing_eg,   impo,     mon,    modd,    vta,        ncortz,        corta,    estac,                                            sucu,                                           nocaj) " + 
                                           "VALUES('CAJ',    'NA',     'NA',       'I',   " + sCant + ", 'MN',   'CXC',  0,   " +    sConsecCor + ", 0, '" +  Login.sUsrG.replace("'", "''") + "','" +      Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                    
                            st = con.createStatement();
                            st.executeUpdate(sQ);
                         }
                         catch(SQLException expnSQL) 
                         { 
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                                                                 
                         }

                    }/*Fin de if(bSiInsCaj)*/
                    /*Else haz inserción por el usuario*/
                    else
                    {
                        /*Declara variables*/
                        String sCaj;

                        /*Mientras no se pueda salir del bucle entonces*/
                        boolean bSa;
                        do
                        {
                            /*Pidele al usuario la cantidad para ingresar en caja*/                
                            sCaj = JOptionPane.showInputDialog("No existe ingreso de caja anterior, ingresa la cantidad inicial: ");

                            /*Si es nulo entonces sal del bucle*/
                            if(sCaj==null)               
                                break;                

                            /*Si es cadena vacia entonces*/
                            if(sCaj.compareTo("")==0)
                            {
                                /*Mensajea*/
                                JOptionPane.showMessageDialog(null, "Ingresa una cantidad válida.", "Ingreso Caja", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                                /*Pon la bandera en falso y continua*/
                                bSa    = false;
                                continue;
                            }

                            /*Si no se puede convertir a double entonces*/
                            try
                            {
                                Double.parseDouble(sCaj);
                            }
                            catch(NumberFormatException expnNumForm)
                            {                                
                                /*Mensajea*/
                                JOptionPane.showMessageDialog(null, "Ingresa una cantidad válida.", "Ingreso Caja", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                                /*Pon la banera en false y continua*/
                                bSa    = false;
                                continue;
                            }

                            /*Si el valor es menor a 0 entonces*/
                            if(Double.parseDouble(sCaj) < 0)
                            {
                                /*Mensajea*/
                                JOptionPane.showMessageDialog(null, "Ingresa una cantidad válida mayor a 0.", "Ingreso Caja", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                                /*Pon la banera en false y continua*/
                                bSa    = false;
                                continue;
                            }

                            /*Ingresa en el flujo lo de la caja inicial*/
                            try 
                            {                    
                                sQ = "INSERT INTO fluj(concep,    tipdoc,   norefer,    ing_eg,   impo,     mon,    modd,    vta,        ncortz,        corta,    estac,                                            sucu,                                            nocaj) " + 
                                               "VALUES('CAJ',    'NA',     'NA',       'I',   " + sCaj + ", 'MN',   'CXC',  0,   " +    sConsecCor + ", 0, '" +  Login.sUsrG.replace("'", "''") + "','" +       Star.sSucu.replace("'", "''") + "','" +     Star.sNoCaj.replace("'", "''") + "')";                    
                                st = con.createStatement();
                                st.executeUpdate(sQ);
                             }
                             catch(SQLException expnSQL) 
                             { 
                                //Procesa el error y regresa
                                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                                return;                                                                                                                                     
                             }

                            /*Poner la bandera en true*/
                            bSa        = true;                    

                        }
                        while(!bSa);                        

                    }/*Fin de else*/                            

                }/*Fin de if(!bSiI)*/
                
                //Cierra la base de datos
                Star.iCierrBas(con);
                
            }/*Fin de public void run()*/
        }).start();        
        
        /*Thread para que este mostrando la hora del sistema actualizada*/
        th  = new Thread()
        {
            @Override
            public void run()
            {             
                /*Bucle inifinito*/
                while(true)
                {
                    /*Obtiene la fecha y hora del sistema y colocalo en el campo correspondiente*/
                    DateFormat dat      = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date da             = new Date();
                    jTFec.setText(dat.format(da));
                    
                    /*Duermelo un segundo*/
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException expnInterru)
                    { 
                        /*Sale del thread*/
                        break;
                    }                    
                }                
            }
        };
        th.start();                                
        
        /*Thread para saber el estado de la conexión con la base de datos*/
        tEstCon     = new Thread()
        {
            @Override
            public void run()
            {             
                /*Bucle inifinito*/
                while(true)
                {
                    /*Duermelo un segundo*/
                    try
                    {
                        Thread.sleep(3000);
                    }
                    catch(InterruptedException expnInterru)
                    {      
                        /*Sal del thread*/
                        break;
                    }                                                               
                    
                    //Abre la base de datos
                    Connection  con = Star.conAbrBas(true, false);

                    //Si hubo error entonces regresa
                    if(con==null)
                    {
                        //Cambia el icono del botón a el de desconectado                        
                        java.awt.EventQueue.invokeLater(new Runnable() 
                        {
                            @Override
                            public void run() 
                            {
                                try
                                {                                                                        
                                    java.awt.image.BufferedImage img    = javax.imageio.ImageIO.read(getClass().getClassLoader().getResource("imgs/descon.png"));
                                    jBLed.setIcon(new ImageIcon(img));                                                                
                                }
                                catch(IOException expnIO)
                                {
                                    //Procesa el error
                                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                                                        
                                }                                                                                                        
                            }
                        });                                               
                        
                        /*Cambia la imágen del botón led y el tool tip*/                                
                        jBLed.setToolTipText("Conectado");                                                
                        
                        /*Cambia el tool tip del punto de venta y agrega en el log*/
                        jBLed.setToolTipText("No hay conexión");                                                
                        Login.vLog("No hay conexión desde el punto de venta");                                                                        
                    }
                    //Else si se pudo conectar entonces
                    else
                    {
                        /*Cambia el icono al de conectado*/
                        java.awt.EventQueue.invokeLater(new Runnable() 
                        {
                            @Override
                            public void run() 
                            {
                                try
                                {                                                                        
                                    java.awt.image.BufferedImage img    = javax.imageio.ImageIO.read(getClass().getClassLoader().getResource("imgs/concon.png"));
                                    jBLed.setIcon(new ImageIcon(img));                                                                
                                }
                                catch(IOException expnIO)
                                {
                                    //Procesa el error
                                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                    
                                }                                                                                                        
                            }
                        });                                               
                    }                       

                    /*Cierra la base de datos*/
                    if(con!=null)                                            
                        Star.iCierrBas(con);                                                     
                                        
                }/*Fin de while(true)*/
                
            }/*Fin de public void run()*/
        };
        tEstCon.start();                                
        
    }/*Fin de public PtoVtaTou() */


    /*Carga la imágen de la fila de la tabla de líneas*/
    private void vCargImgFi()
    {        
        /*Si no hay selecciòn entonces regresa*/        
        if(jTab.getSelectedRow()==-1)            
            return;
     
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
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor. " + System.getProperty( "line.separator" ) + "Entonces no se podrán generar los PDF de las ventas generadas.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
            return;                       
        }                                              
        
        /*Si la carpeta de las imágenes no existe entonces crea el directorio*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de los prods no existe entonces crea el directorio*/
        sCarp                    += "\\Productos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta del producto en específico no existe entonces crea el directorio*/
        sCarp                    += "\\" + jTab.getValueAt(jTab.getSelectedRow(), 1).toString();
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
                
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if(new File(sCarp).list().length > 0)
            {            
                /*Obtiene la lista de directorios*/
                String sArchs [] = new File(sCarp).list();

                /*Carga la imágen en el panel*/
                jLImg.setIcon(new ImageIcon(sCarp + "\\" + sArchs[0]));

                /*Que el label sea visible*/
                jLImg.setVisible(true);
            }
            /*Else, no existe imágen entonces que la imágen no sea visible*/
            else
                jLImg.setVisible(false);
        }                    
        
    }/*Fin de private void vCargImgFi()*/   
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jBNewVta = new javax.swing.JButton();
        jTCli = new javax.swing.JTextField();
        jTNomb = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBDel = new javax.swing.JButton();
        jB1 = new javax.swing.JButton();
        jBTecla = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();
        jTQtyP = new javax.swing.JTextField();
        jTProd = new javax.swing.JTextField();
        jTMon = new javax.swing.JTextField();
        jBProds = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jBCob = new javax.swing.JButton();
        jScrollPaneLin = new javax.swing.JScrollPane();
        jPanelLin = new javax.swing.JPanel();
        jBCli = new javax.swing.JButton();
        jBNewEmp = new javax.swing.JButton();
        jScrollProds = new javax.swing.JScrollPane();
        jPanProds = new javax.swing.JPanel();
        jBBuscGral = new javax.swing.JButton();
        jTBuscGral = new javax.swing.JTextField();
        jTDesc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTSer = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLAyu = new javax.swing.JLabel();
        jTTot = new javax.swing.JTextField();
        jTImpue = new javax.swing.JTextField();
        jTSubTot = new javax.swing.JTextField();
        jBVeGran = new javax.swing.JButton();
        jPanImg = new javax.swing.JPanel();
        jLImg = new javax.swing.JLabel();
        jTCodProd = new javax.swing.JTextField();
        jPCon = new javax.swing.JPanel();
        jBLed = new javax.swing.JButton();
        jTColo = new javax.swing.JTextField();
        jTAlma = new javax.swing.JTextField();
        jTTall = new javax.swing.JTextField();
        jTId = new javax.swing.JTextField();
        jTLot = new javax.swing.JTextField();
        jTPedimen = new javax.swing.JTextField();
        jTCadu = new javax.swing.JTextField();
        jTCant = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTTotDesc = new javax.swing.JTextField();
        jTComenSer = new javax.swing.JTextField();
        jTSerProd = new javax.swing.JTextField();
        jTGara = new javax.swing.JTextField();
        jTTotCost = new javax.swing.JTextField();
        jTFec = new javax.swing.JTextField();
        jLTipVta = new javax.swing.JLabel();
        jTCantLot = new javax.swing.JTextField();
        jTList = new javax.swing.JTextField();
        jComUnid = new javax.swing.JComboBox();
        jTImpo = new javax.swing.JTextField();
        jTRecib = new javax.swing.JTextField();
        jTMarc = new javax.swing.JTextField();
        jTMod = new javax.swing.JTextField();
        jTColoAut = new javax.swing.JTextField();
        jTPlacs = new javax.swing.JTextField();
        jTNom = new javax.swing.JTextField();
        jTTarCirc = new javax.swing.JTextField();
        jTNumLic = new javax.swing.JTextField();
        jTTel = new javax.swing.JTextField();
        jTDirPart = new javax.swing.JTextField();
        jTDirOfi = new javax.swing.JTextField();
        jTTelOfi = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
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
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBNewVta.setBackground(new java.awt.Color(255, 255, 255));
        jBNewVta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/newvta.png"))); // NOI18N
        jBNewVta.setToolTipText("Nueva Venta");
        jBNewVta.setNextFocusableComponent(jBDel);
        jBNewVta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBNewVtaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBNewVtaMouseExited(evt);
            }
        });
        jBNewVta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNewVtaActionPerformed(evt);
            }
        });
        jBNewVta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBNewVtaKeyPressed(evt);
            }
        });
        jPanel1.add(jBNewVta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 100, 90));

        jTCli.setBackground(new java.awt.Color(204, 255, 204));
        jTCli.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTCli.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCli.setNextFocusableComponent(jBNewVta);
        jTCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCliFocusLost(evt);
            }
        });
        jTCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTCliMouseClicked(evt);
            }
        });
        jTCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTCliActionPerformed(evt);
            }
        });
        jTCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCliKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCliKeyTyped(evt);
            }
        });
        jPanel1.add(jTCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 110, 40));

        jTNomb.setEditable(false);
        jTNomb.setBackground(new java.awt.Color(204, 255, 255));
        jTNomb.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTNomb.setBorder(null);
        jTNomb.setFocusable(false);
        jTNomb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTNombFocusGained(evt);
            }
        });
        jTNomb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNombKeyPressed(evt);
            }
        });
        jPanel1.add(jTNomb, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 460, 40));

        jTab.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Qty.", "Cod. Producto", "Unidad", "Descripción", "Desc.", "Precio", "Importe", "Impuesto", "Valor Imp.", "Almacén", "Talla", "Color", "Lote", "Pedimento", "Caducidad", "ID", "Es Kit", "Precio Original", "Importe Descuento", "Serie Producto", "Comentario Producto", "Garantía", "Costo", "idped", "Cod.Impuesto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setRowHeight(25);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 570, 430));

        jBDel.setBackground(new java.awt.Color(255, 255, 255));
        jBDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/delpart.png"))); // NOI18N
        jBDel.setToolTipText("Borrar Partida");
        jBDel.setNextFocusableComponent(jB1);
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
        jPanel1.add(jBDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 100, 90));

        jB1.setBackground(new java.awt.Color(255, 255, 255));
        jB1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/calcg.png"))); // NOI18N
        jB1.setToolTipText("Calculadora");
        jB1.setNextFocusableComponent(jBTecla);
        jB1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jB1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jB1MouseExited(evt);
            }
        });
        jB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jB1ActionPerformed(evt);
            }
        });
        jB1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jB1KeyPressed(evt);
            }
        });
        jPanel1.add(jB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 100, 90));

        jBTecla.setBackground(new java.awt.Color(255, 255, 255));
        jBTecla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tecla.png"))); // NOI18N
        jBTecla.setToolTipText("Teclado Virtual");
        jBTecla.setNextFocusableComponent(jBSal);
        jBTecla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBTeclaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBTeclaMouseExited(evt);
            }
        });
        jBTecla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTeclaActionPerformed(evt);
            }
        });
        jBTecla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTeclaKeyPressed(evt);
            }
        });
        jPanel1.add(jBTecla, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 100, 80));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jBSal.setForeground(new java.awt.Color(255, 51, 51));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/salpto.png"))); // NOI18N
        jBSal.setToolTipText("Salir (ESC)");
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
        jPanel1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 100, 80));

        jTQtyP.setEditable(false);
        jTQtyP.setBackground(new java.awt.Color(255, 255, 255));
        jTQtyP.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jTQtyP.setForeground(new java.awt.Color(102, 102, 0));
        jTQtyP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTQtyP.setText("0 pzas");
        jTQtyP.setToolTipText("Cantidad de Productos Actual en las Partidas");
        jTQtyP.setBorder(null);
        jTQtyP.setFocusable(false);
        jTQtyP.setNextFocusableComponent(jTProd);
        jPanel1.add(jTQtyP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 100, 50));

        jTProd.setBackground(new java.awt.Color(255, 255, 153));
        jTProd.setFont(new java.awt.Font("Tahoma", 0, 21)); // NOI18N
        jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProd.setNextFocusableComponent(jTCant);
        jTProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProdFocusGained(evt);
            }
        });
        jTProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTProdActionPerformed(evt);
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
        jPanel1.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 490, 270, 50));

        jTMon.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jTMon.setNextFocusableComponent(jComUnid);
        jTMon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMonFocusLost(evt);
            }
        });
        jTMon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMonKeyPressed(evt);
            }
        });
        jPanel1.add(jTMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 490, 50, 50));

        jBProds.setBackground(new java.awt.Color(255, 255, 255));
        jBProds.setText("...");
        jBProds.setToolTipText("Buscar productos (Flecha abajo)");
        jBProds.setNextFocusableComponent(jBNew);
        jBProds.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProdsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProdsMouseExited(evt);
            }
        });
        jBProds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProdsActionPerformed(evt);
            }
        });
        jBProds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProdsKeyPressed(evt);
            }
        });
        jPanel1.add(jBProds, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 490, 40, 50));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jBNew.setForeground(new java.awt.Color(51, 153, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre8.png"))); // NOI18N
        jBNew.setToolTipText("Agregar Producto(CTRL+N)");
        jBNew.setName(""); // NOI18N
        jBNew.setNextFocusableComponent(jBCob);
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
        jPanel1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 490, 40, 50));

        jBCob.setBackground(new java.awt.Color(255, 255, 255));
        jBCob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/cobra.png"))); // NOI18N
        jBCob.setToolTipText("Cobrar");
        jBCob.setNextFocusableComponent(jTDesc);
        jBCob.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCobMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCobMouseExited(evt);
            }
        });
        jBCob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCobActionPerformed(evt);
            }
        });
        jBCob.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCobKeyPressed(evt);
            }
        });
        jPanel1.add(jBCob, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 490, 40, 50));

        jScrollPaneLin.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPaneLin.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPaneLin.setAutoscrolls(true);
        jScrollPaneLin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jScrollPaneLinKeyPressed(evt);
            }
        });

        jPanelLin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanelLinKeyPressed(evt);
            }
        });
        jPanelLin.setLayout(new org.jdesktop.swingx.VerticalLayout());
        jScrollPaneLin.setViewportView(jPanelLin);

        jPanel1.add(jScrollPaneLin, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 60, 200, 490));

        jBCli.setBackground(new java.awt.Color(255, 255, 255));
        jBCli.setText("...");
        jBCli.setToolTipText("Buscar Cliente");
        jBCli.setNextFocusableComponent(jTCli);
        jBCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBCliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBCliMouseExited(evt);
            }
        });
        jBCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCliActionPerformed(evt);
            }
        });
        jBCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBCliKeyPressed(evt);
            }
        });
        jPanel1.add(jBCli, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 60, 40));

        jBNewEmp.setBackground(new java.awt.Color(255, 255, 255));
        jBNewEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/Insertar.gif"))); // NOI18N
        jBNewEmp.setToolTipText("Nuevo Cliente");
        jBNewEmp.setNextFocusableComponent(jBCli);
        jBNewEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBNewEmpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBNewEmpMouseExited(evt);
            }
        });
        jBNewEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNewEmpActionPerformed(evt);
            }
        });
        jBNewEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBNewEmpKeyPressed(evt);
            }
        });
        jPanel1.add(jBNewEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, 60, 40));

        jScrollProds.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollProds.setToolTipText("");
        jScrollProds.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollProds.setViewportBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollProds.setAutoscrolls(true);
        jScrollProds.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jScrollProds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jScrollProdsKeyPressed(evt);
            }
        });

        jPanProds.setAutoscrolls(true);
        jPanProds.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanProds.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jPanProds.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanProdsKeyPressed(evt);
            }
        });
        jPanProds.setLayout(new java.awt.GridLayout(5, 3));
        jScrollProds.setViewportView(jPanProds);

        jPanel1.add(jScrollProds, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 60, 500, 660));

        jBBuscGral.setBackground(new java.awt.Color(255, 255, 255));
        jBBuscGral.setText("...");
        jBBuscGral.setToolTipText("Buscar Catálogo General");
        jBBuscGral.setNextFocusableComponent(jBNewEmp);
        jBBuscGral.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBuscGralMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBuscGralMouseExited(evt);
            }
        });
        jBBuscGral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscGralActionPerformed(evt);
            }
        });
        jBBuscGral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBuscGralKeyPressed(evt);
            }
        });
        jPanel1.add(jBBuscGral, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 540, 60, 40));

        jTBuscGral.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTBuscGral.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBuscGral.setNextFocusableComponent(jBBuscGral);
        jTBuscGral.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTBuscGralFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTBuscGralFocusLost(evt);
            }
        });
        jTBuscGral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTBuscGralKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTBuscGralKeyTyped(evt);
            }
        });
        jPanel1.add(jTBuscGral, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 540, 170, 40));

        jTDesc.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jTDesc.setForeground(new java.awt.Color(0, 0, 255));
        jTDesc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTDesc.setText("0");
        jTDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDesc.setNextFocusableComponent(jTBuscGral);
        jTDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescFocusLost(evt);
            }
        });
        jTDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTDescActionPerformed(evt);
            }
        });
        jTDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDescKeyTyped(evt);
            }
        });
        jPanel1.add(jTDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 540, 70, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel2.setText("DESCUENTO %");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 540, 210, -1));

        jTSer.setEditable(false);
        jTSer.setFocusable(false);
        jPanel1.add(jTSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 710, 10, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel5.setText("SUBTOTAL:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 580, 220, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel7.setText("IMPUESTO:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 610, 220, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel6.setText("DESCUENTO:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 640, 220, 30));

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
        jPanel1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 720, 350, 20));

        jTTot.setEditable(false);
        jTTot.setBackground(new java.awt.Color(255, 255, 204));
        jTTot.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jTTot.setForeground(new java.awt.Color(0, 0, 255));
        jTTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTot.setText("$0.00");
        jTTot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTotFocusGained(evt);
            }
        });
        jTTot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTotKeyPressed(evt);
            }
        });
        jPanel1.add(jTTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 670, 300, 30));

        jTImpue.setEditable(false);
        jTImpue.setBackground(new java.awt.Color(255, 255, 204));
        jTImpue.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jTImpue.setForeground(new java.awt.Color(0, 0, 255));
        jTImpue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTImpue.setText("$0.00");
        jTImpue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTImpue.setFocusable(false);
        jPanel1.add(jTImpue, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 610, 300, 30));

        jTSubTot.setEditable(false);
        jTSubTot.setBackground(new java.awt.Color(255, 255, 204));
        jTSubTot.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jTSubTot.setForeground(new java.awt.Color(0, 0, 255));
        jTSubTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTSubTot.setText("$0.00");
        jTSubTot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSubTot.setFocusable(false);
        jPanel1.add(jTSubTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 580, 300, 30));

        jBVeGran.setBackground(new java.awt.Color(255, 255, 255));
        jBVeGran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/img.png"))); // NOI18N
        jBVeGran.setToolTipText("Ver Imágen  de Producto Completa");
        jBVeGran.setNextFocusableComponent(jBNewEmp);
        jBVeGran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBVeGranMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBVeGranMouseExited(evt);
            }
        });
        jBVeGran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVeGranActionPerformed(evt);
            }
        });
        jBVeGran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBVeGranKeyPressed(evt);
            }
        });
        jPanel1.add(jBVeGran, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 720, 200, 30));

        jPanImg.setBackground(new java.awt.Color(255, 255, 204));
        jPanImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanImgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanImgMouseExited(evt);
            }
        });
        jPanImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanImgKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanImgLayout = new javax.swing.GroupLayout(jPanImg);
        jPanImg.setLayout(jPanImgLayout);
        jPanImgLayout.setHorizontalGroup(
            jPanImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanImgLayout.createSequentialGroup()
                .addComponent(jLImg)
                .addGap(0, 200, Short.MAX_VALUE))
        );
        jPanImgLayout.setVerticalGroup(
            jPanImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanImgLayout.createSequentialGroup()
                .addComponent(jLImg)
                .addContainerGap(170, Short.MAX_VALUE))
        );

        jPanel1.add(jPanImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 550, 200, 170));

        jTCodProd.setEditable(false);
        jPanel1.add(jTCodProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 710, 10, -1));

        jPCon.setBackground(new java.awt.Color(255, 255, 255));
        jPCon.setFocusable(false);
        jPCon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPConKeyPressed(evt);
            }
        });

        jBLed.setBackground(new java.awt.Color(255, 255, 255));
        jBLed.setForeground(new java.awt.Color(255, 255, 255));
        jBLed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/concon.png"))); // NOI18N
        jBLed.setBorder(null);
        jBLed.setFocusable(false);

        javax.swing.GroupLayout jPConLayout = new javax.swing.GroupLayout(jPCon);
        jPCon.setLayout(jPConLayout);
        jPConLayout.setHorizontalGroup(
            jPConLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPConLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jBLed, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPConLayout.setVerticalGroup(
            jPConLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPConLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBLed, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jPanel1.add(jPCon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 100, 50));

        jTColo.setEditable(false);
        jPanel1.add(jTColo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 710, 10, -1));

        jTAlma.setEditable(false);
        jPanel1.add(jTAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 710, 10, -1));

        jTTall.setEditable(false);
        jPanel1.add(jTTall, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 710, 10, -1));

        jTId.setEditable(false);
        jPanel1.add(jTId, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 710, 10, -1));

        jTLot.setEditable(false);
        jPanel1.add(jTLot, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 710, 10, -1));

        jTPedimen.setEditable(false);
        jPanel1.add(jTPedimen, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 710, 10, -1));

        jTCadu.setEditable(false);
        jPanel1.add(jTCadu, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 710, 10, -1));

        jTCant.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTCant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTCant.setText("1");
        jTCant.setToolTipText("Cantidad a Vender");
        jTCant.setNextFocusableComponent(jTMon);
        jTCant.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCantFocusGained(evt);
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
        jPanel1.add(jTCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 490, 50, 50));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel8.setText("TOTAL:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 670, 220, 30));

        jTTotDesc.setEditable(false);
        jTTotDesc.setBackground(new java.awt.Color(255, 255, 204));
        jTTotDesc.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jTTotDesc.setForeground(new java.awt.Color(0, 0, 255));
        jTTotDesc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTTotDesc.setText("$0.00");
        jTTotDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTotDesc.setFocusable(false);
        jPanel1.add(jTTotDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 640, 300, 30));

        jTComenSer.setEditable(false);
        jTComenSer.setBackground(new java.awt.Color(255, 255, 255));
        jTComenSer.setFocusable(false);
        jPanel1.add(jTComenSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 710, 10, 20));

        jTSerProd.setEditable(false);
        jTSerProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPanel1.add(jTSerProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 710, 10, 20));

        jTGara.setEditable(false);
        jPanel1.add(jTGara, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 710, 10, -1));

        jTTotCost.setEditable(false);
        jTTotCost.setFocusable(false);
        jPanel1.add(jTTotCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 710, 10, -1));

        jTFec.setEditable(false);
        jTFec.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jTFec.setBorder(null);
        jTFec.setFocusable(false);
        jTFec.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFecFocusGained(evt);
            }
        });
        jTFec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFecKeyPressed(evt);
            }
        });
        jPanel1.add(jTFec, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 310, 40));

        jLTipVta.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLTipVta.setText("VENTA DE CONTADO");
        jLTipVta.setFocusable(false);
        jPanel1.add(jLTipVta, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 10, 220, 40));

        jTCantLot.setEditable(false);
        jTCantLot.setText("0");
        jPanel1.add(jTCantLot, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 710, 10, -1));

        jTList.setEditable(false);
        jPanel1.add(jTList, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 710, 10, -1));

        jComUnid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComUnid.setToolTipText("Unidad de producto");
        jComUnid.setName(""); // NOI18N
        jComUnid.setNextFocusableComponent(jBProds);
        jComUnid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComUnidFocusLost(evt);
            }
        });
        jComUnid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComUnidKeyPressed(evt);
            }
        });
        jPanel1.add(jComUnid, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 490, 80, 50));
        jPanel1.add(jTImpo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 730, 10, -1));
        jPanel1.add(jTRecib, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 730, 10, -1));
        jPanel1.add(jTMarc, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 730, 10, -1));
        jPanel1.add(jTMod, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 730, 10, -1));
        jPanel1.add(jTColoAut, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 730, 10, -1));
        jPanel1.add(jTPlacs, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 730, 10, -1));
        jPanel1.add(jTNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 730, 10, -1));
        jPanel1.add(jTTarCirc, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 730, 10, -1));
        jPanel1.add(jTNumLic, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 730, 10, -1));
        jPanel1.add(jTTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 730, 10, -1));
        jPanel1.add(jTDirPart, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 730, 10, -1));
        jPanel1.add(jTDirOfi, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 730, 10, -1));
        jPanel1.add(jTTelOfi, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 730, 10, -1));

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Cuando se presiona una tecla en la forma de punto de vta touch*/
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_formKeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed

        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
        /*Preguntar al usuario si esta seguro de querer salir*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres salir?", "Salir del Punto de Venta Touch", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
        {                   
            /*Coloca el foco del teclado en el campo del código del producto y regresa*/
            jTProd.grabFocus();                       
            return;                                   
        }    
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Comprueba si el usuario es usuario de punto de venta*/
        boolean bUsr    = false;
        try
        {
            sQ = "SELECT ptovta FROM estacs WHERE estac = '" + Login.sUsrG + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca la bandera para saber si es un usuario de punto de venta o no*/
                if(rs.getString("ptovta").compareTo("1")==0)
                    bUsr    = true;
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

        /*Cierra el formulario*/
        this.dispose();
        
        /*Detiene los threads*/
        th.interrupt();
        tEstCon.interrupt();
        //cierra el punto de venta
        Star.gPtoVtaTou = null;
            
        /*Llama al recolector de basura*/
        System.gc();
                
        /*Si es usurio de punto de venta entonces*/
        if(bUsr)
        {
            //Registra el deslog del actual usuario
            if(Star.iRegUsr(null, "1")==-1)
                return;                        

            //Borralo de la tabla de logestac
            Star.iRegLogUsr(null, "del", "", "", "");
            //Sal del sistema
            System.exit(0);
        }
                
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona una tecla en el la tabla de partidas*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Función para ver si se muestra o no la forma para ver los mensajes*/
    private void vVeMsj()
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Comprueba si se tiene habilitado que se vean los mensajes en el punto de venta*/
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vmsjpto'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si no se puede entonces*/
                if(rs.getString("val").compareTo("0")==0)                                  
                {
                    //Cierra la base de datos y regresa
                    Star.iCierrBas(con);
                    return;
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

        /*Muestra la forma para ver los mensajes de los cajeros*/
        Msjs w = new Msjs();
        w.setVisible(true);
            
    }/*Fin de private void vVeMsj()*/
            
            
    /*Cuando se presiona una tecla en el campo del producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed
                
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Presiona el botón de búscar producto*/
            jBProds.doClick();
            
            /*Obtiene la imágen si es que tiene*/
            (new Thread()
            {
                @Override
                public void run()
                {
                    /*Carga la imágen en el control*/
                    Star.vGetImg(jTProd.getText(), jLImg);

                    /*Muestra que se termino de cargar la imágen*/
                    jLImg.setText("");
                }
            }).start();
        }        
        /*Else, llama a la función escalable*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProdKeyPressed

    
    /*Valida si tiene que hacer el corte X automáticamente*/
    private void vCortXAut()
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si tiene que hacer corte X automáticamente*/        
        boolean bSi = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'autcortx'";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca la banera*/
                if(rs.getString("val").compareTo("1")==0)
                    bSi = true;                
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

        /*Si no tiene que hacer corte entonces regresa*/
        if(!bSi)
            return;
        
        /*Llama a la función para hacer el corte X*/
        Star.vCortX("X","\\Cortes X");
        
    }/*Fin de private void vCortXAut()*/
            
            
    /*Función para validar y mostrar el chat*/
    private void vChatC()
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 

        /*Comprueba si se tiene habilitado que puedan chatear corporatiamente desde el punto de venta o no*/
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'chatptoc'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si no se puede entonces*/
                if(rs.getString("val").compareTo("0")==0)                                  
                {
                    //Cierra la base de datos y regresa
                    Star.iCierrBas(con);
                    return;
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

        /*Muestra la forma de chat corporativo*/
        ChatC c = new ChatC(false);
        c.getObj(true).setVisible(true);
        
    }/*Fin de private void vChatC()*/
                        
            
    /*Cuando se gana el foco del teclado en el campo de artículo*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());        
        
    }//GEN-LAST:event_jTProdFocusGained

    
    /*Cuando se presiona una tecla en el botón de calculadora*/
    private void jB1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jB1KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jB1KeyPressed

    
    /*Cuando se presiona una tecla en el botón de borrar*/
    private void jBDelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBDelKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBDelKeyPressed

    
    /*Cuando se presiona el botón de borrar todas las partidas*/
    private void jBNewVtaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewVtaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewVtaKeyPressed
    
    
    /*Cuando se gana el foco del teclado en el campo de total*/
    private void jTTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTot.setSelectionStart(0);jTTot.setSelectionEnd(jTTot.getText().length());                
        
    }//GEN-LAST:event_jTTotFocusGained

    
    /*Cuando se presiona una tecla en el botón de agregar*/
    private void jBNewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewKeyPressed
        
    
    /*Cuando se presiona una tecla en el campo de total*/
    private void jTTotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTotKeyPressed

    
    /*Cuando se presiona una tecla en el botón de teclado*/
    private void jBTeclaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTeclaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBTeclaKeyPressed

        
    /*Cuando se tipea una tecla en el campo del artículo*/
    private void jTProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTProdKeyTyped
    

    /*Función escalable para cuando sucede una acción en el campo del código del producto*/
    private void vFunEsc()
    {
        /*Si el código del producto es cadena vacia entonces*/
        if(jTProd.getText().trim().compareTo("")==0)
            return;
    
        /*Si la unidad es cadena vacia entonces*/
        if(jComUnid.getSelectedItem().toString().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una unidad primero.", "Unidad", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jComUnid.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
            /*Pon el foco del teclado en el control y regresa*/
            jComUnid.grabFocus();
            return;
        }
        
        /*Si el producto es por lote y pedimento entonces*/
        if(Double.parseDouble(jTCantLot.getText().trim())>0)
        {
            /*Si la cantidad que se quiere insertar es mayor a la cantidad del lote permitido entonces*/
            if(Double.parseDouble(jTCant.getText())>Double.parseDouble(jTCantLot.getText().trim()))
            {
                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La cantidad de lote a insertar del producto es mayor a la permitida.", "Lote y pedimento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Coloca el borde rojo*/                               
                jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Pon el foco del teclado en el campo de la cantidad y regresa*/
                jTCant.grabFocus();
                return;
            }
            
            /*Recorre toda la tabla de partidas*/
            for(int x = 0; x < jTab.getRowCount(); x++)
            {                
                /*Si en la fila ya esta ese lote entonces*/
                if(jTab.getValueAt(x, 20).toString().compareTo(jTId.getText().trim())==0)
                {
                    /*Obtiene la diferencia de lo que ya esta cargado y lo que se quiere cargar*/
                    String sDif = Double.toString(Double.parseDouble(jTCant.getText().trim()) + Double.parseDouble(jTab.getValueAt(x, 3).toString()));
                    
                    /*Si la diferencia es mayor que lo que se quiere insertar entonces*/
                    if(Double.parseDouble(sDif)>Double.parseDouble(jTCantLot.getText().trim()))
                    {
                        /*Mensajea*/
                        JOptionPane.showMessageDialog(null, "La cantidad de lote a insertar: " + jTCant.getText().trim() + " mas la cantidad ya cargada: " + jTab.getValueAt(x, 3).toString() + " del producto es mayor a la permitida: " + jTCantLot.getText().trim(), "Lote y pedimento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                        /*Coloca el borde rojo*/                               
                        jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                        /*Pon el foco del teclado en el campo de la cantidad y regresa*/
                        jTCant.grabFocus();
                        return;                                                
                    }
                }
                
            }/*Fin de for(int x = 0; row < jTab.getRowCount(); row++)*/
            
        }/*Fin de if(Double.parseDouble(sCantOriLot)>0)*/
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Si el producto necesita a fuerzas serie entonces
        int iRes    = Star.iProdSolSer(con, jTProd.getText().trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el producto necesita a fuerzas serie entonces
        if(iRes==1)
        {
            //Si la serie es cadena vacia entonces
            if(jTSerProd.getText().trim().compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                //Mensajea
                JOptionPane.showMessageDialog(null, "El producto solicita número de serie.", "Número serie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                //Poner el foco del teclado en el campo de la serie
                jTSerProd.grabFocus();
                
                //Coloca el borde rojo y egresa
                jTSerProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                return;
            }            
        }

        //Si el producto no solicita número de serie entonces
        if(iRes==0)
        {
            //Obtiene si el producto tiene existencias por serie
            double dExist   = Star.dGetExistProdSer(con, jTProd.getText().trim());
            
            //Si hubo error entonces regresa
            if(dExist==-1)
                return;
            
            //Si hay existencias entonces mensajea
            if(dExist>0)                            
                JOptionPane.showMessageDialog(null, "El producto: " + jTProd.getText().trim() + " no esta habilitado para venta por series pero tiene existencias: " + dExist + " de las mismas.", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                        
        }
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si el producto requiere lote y pedimento*/
        String sLot = "";
        try
        {
            sQ = "SELECT lotped FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sLot      = rs.getString("lotped");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }        
        
        //Si es cadena vacia el lote que sea 0
        if(sLot.compareTo("")==0)
            sLot    = "0";
        
        /*Si el producto requiere lote y pedimento entonces*/
        if(Integer.parseInt(sLot)==1)
        {
            /*Si no se a ingresado por lo menos un lote o pedimento entonces*/            
            if(jTLot.getText().trim().compareTo("")==0 && jTPedimen.getText().trim().compareTo("")==0)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El producto requiere de lote o pedimento. Ingresa alguno de estos datos primeramente.", "Lote y pedimetno", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Coloca el borde rojo*/
                jTLot.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
                
                /*Coloca el foco del teclado en el contorl y regresa*/
                jTLot.grabFocus();
                return;
            }
        }
        
        /*Obtiene si se tiene que agregar la garantía en la descripción del producto*/
        boolean bGara   = false;
	try
        {                  
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'garandescpto'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Si se tiene que agregar en la descripción entonces coloca la bandera*/
                if(rs.getInt("val")==1)
                    bGara   = true;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
                
        /*Comprueba si existe la moneda ingresada*/
        try
        {
            sQ = "SELECT mon FROM mons WHERE mon = '" + jTMon.getText().trim() + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces*/
            if(!rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La moneda: " + jTMon.getText().trim() + " no existe.", "Moneda", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                
                /*Coloca el borde rojo*/                               
                jTMon.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Coloca el foco del teclado en el campo de la cantidad y regresa*/
                jTMon.grabFocus();                        
                return;
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
                    
        /*Obtiene el almacén configurado para el punto de venta*/
        String sAlma    = "";
        try
        {
            sQ = "SELECT extr FROM confgral WHERE clasif = 'vtas' AND conf = 'almapto'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sAlma   = rs.getString("extr");            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
        
        //Declara variables locales                        
        String sDescripCort;
        String sImp;
        String sExist;
        String sKit;
        String sCost;        
        
        /*Comprueba si el producto existe en la base de datos y obtiene los mismos del producto*/                
        try
        {
            sQ = "SELECT cost, garan.DESCRIP, CASE WHEN compue = 1 THEN 'Si' ELSE 'No' END AS kit, descripcort, impue, exist, alma FROM prods LEFT OUTER JOIN garan ON garan.GARA = prods.GARAN WHERE prod = '" + jTProd.getText() + "' AND esvta = 1";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe*/
            if(rs.next())
            {                
                /*Obtiene los datos de la consulta*/                
                sDescripCort    = rs.getString("descripcort");
                sImp            = rs.getString("impue");
                sExist          = rs.getString("exist");                
                sKit            = rs.getString("kit");                
                sCost           = rs.getString("cost");                
                            
                /*Coloca la garantia en el control*/
                jTGara.setText  (rs.getString("garan.DESCRIP"));                                
            }   
            /*Else no hay datos entonces no existe*/
            else
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "El código del producto: " + jTProd.getText().trim() + " no existe.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 

                /*Pon el foco del teclado en el campo y regresa*/
                jTProd.grabFocus();                        
                return;            
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }  	        
                
        /*Si el producto es un kit entonces*/
        if(sKit.compareTo("Si")==0)
        {            
            //Obtiene si el producto tiene componentes
            double dRes     = Star.dGetCompsProd(con, jTProd.getText().trim());

            //Si hubo error entonces regresa
            if(dRes==-1)
                return;

            //Si tiene componentes entonces coloca la bandera
            boolean bSiKit  = false;
            if(dRes>0)
                bSiKit      = true;
            
            /*Si no tiene componentes entonces*/
            if(!bSiKit)
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "El kit: " + jTProd.getText() + " no tiene componentes y no se puede cargar.", "Kits", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
        }/*Fin de if(sKit.compareTo("1")==0)*/
                
        /*Comprueba si no se debe vender abajo de las existencias*/
        boolean bSiExistN  = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'vendsinexistpvta'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Coloca la banera si esta habilitado*/
                if(rs.getString("val").compareTo("0")==0)
                    bSiExistN = true;
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
        
        /*Comprueba si esta habilitado mostrar el mensaje de existencias negativas*/
        boolean bSiMsg  = false;
        try
        {
            sQ = "SELECT val FROM confgral WHERE clasif = 'vtas' AND conf = 'msjexistnegpvta'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Coloca la banera si esta habilitado*/
                if(rs.getString("val").compareTo("1")==0)
                    bSiMsg = true;
            }                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }  
        
        /*Si esta habilitado el mostrar mensaje de existencias negativas entonces*/        
        if(bSiMsg)
        {
            /*Si lo que se quiere agregar es mayor que la existencia entonces mensajea*/
            if( 1 > Double.parseDouble(sExist))
                JOptionPane.showMessageDialog(null, "No hay existencias suficientes para este producto.", "Existencias", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
        }
        
        /*Si no se puede vender con existencia baja entonces*/
        if(bSiExistN && (1 > Double.parseDouble(sExist)))
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se puede vender sin existencias.", "Existencias", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));            
            return;                    
        }                        
        
        /*Coloca el código del producto en el campo para que cuando se presione el botón de ver imágen en grande lo tome de ahí*/
        jTCodProd.setText(jTProd.getText());
                
        /*Obtiene el valor del impuesto para el producto*/
        String sVal = "0";
        try
        {
            sQ = "SELECT impueval FROM impues WHERE codimpue = '" + sImp + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces si existe entonces obtiene el resultado*/
            if(rs.next())
                sVal      = rs.getString("impueval");                                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }  
        
        /*Si se va a insertar una serie entonces*/
        if(jTSerProd.getText().compareTo("")!=0)
        {            
            /*Si la tabla de partidas no tiene partidas entonces*/
            if(jTab.getRowCount()==0)
            {
                /*Obtiene la existencia de esa serie actual*/
                String sExistSer   = "";
                try
                {
                    sQ  = "SELECT exist FROM serieprod WHERE ser = '" + jTSerProd.getText().trim() + "'";
                    st  = con.createStatement();
                    rs  = st.executeQuery(sQ);
                    /*Si hay datos entonces obtiene el resultado*/
                    if(rs.next())
                        sExistSer          = rs.getString("exist");
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;                                                                                                                         
                }

                /*Si la cantidad que se quiere insertar es mayor a la permitida de existencia entonces*/
                if(Double.parseDouble(jTCant.getText())>Double.parseDouble(sExistSer))
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;
            
                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "No se pueden insertar mas cantidades para la serie del producto ya que no alcanzan las existencias.", "Series Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                    /*Coloca el borde rojo*/                               
                    jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                    /*Coloca el foco del teclado en el campo de la cantidad y regresa*/
                    jTCant.grabFocus();                        
                    return;
                }

            }/*Fin de if(jTab.getRowCount()==0)*/
            else
            {
                /*Recorre la tabla de partidas para búscar si esta esta serie ya cargada*/                               
                for(int row = 0; row < jTab.getRowCount(); row++)
                {
                    /*Si ya existe serie en la fila entonces*/            
                    if(jTab.getValueAt(row, 19).toString().compareTo(jTSerProd.getText())==0)
                    {
                        /*Obtiene la existencia de esa serie actual*/
                        String sExistSer   = "";
                        try
                        {
                            sQ  = "SELECT exist FROM serieprod WHERE ser = '" + jTSerProd.getText().trim() + "'";
                            st  = con.createStatement();
                            rs  = st.executeQuery(sQ);
                            /*Si hay datos entonces obtiene el resultado*/
                            if(rs.next())
                                sExistSer          = rs.getString("exist");
                        }
                        catch(SQLException expnSQL)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                            return;                                                                                                                                 
                        }

                        /*Crea la cantidad correcta que se puede insertar*/
                        String sCantCo  = Double.toString(Double.parseDouble(jTCant.getText()) + Double.parseDouble(jTab.getValueAt(row, 0).toString()));

                        /*Si la cantidad que se quiere insertar es mayor a la permitida de existencia entonces*/
                        if(Double.parseDouble(sCantCo)>Double.parseDouble(sExistSer))
                        {
                            //Cierra la base de datos
                            if(Star.iCierrBas(con)==-1)
                                return;
                            
                            /*Mensajea*/
                            JOptionPane.showMessageDialog(null, "No se pueden insertar mas cantidades para la serie del producto ya que no alcanzan las existencias.", "Series Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                            /*Coloca el borde rojo*/                               
                            jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                            /*Coloca el foco del teclado en el campo de la cantidad y regresa*/
                            jTCant.grabFocus();                        
                            return;
                        }
                    }

                }/*Fin de for( int row = 0; row < jTab.getRowCount(); row++)*/
                
            }/*Fin de else*/                            
            
        }/*Fin de if(jTSerProd.getText().compareTo("")!=0)*/
        
        /*Recorre toda la tabla de partidas para ver si ya existe el producto*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {            
            /*Si el producto ya existe en la tabla entonces*/
            if(jTProd.getText().trim().compareTo(jTab.getValueAt(x, 1).toString().trim())==0 && jTab.getValueAt(x, 4).toString().trim().compareTo(jTDesc.getText().trim())==0 && jTab.getValueAt(x, 9).toString().trim().compareTo(jTAlma.getText().trim())==0 && jTab.getValueAt(x, 10).toString().trim().compareTo(jTTall.getText().trim())==0 && jTab.getValueAt(x, 11).toString().trim().compareTo(jTColo.getText().trim())==0 && jTab.getValueAt(x, 12).toString().trim().compareTo(jTLot.getText().trim())==0 && jTab.getValueAt(x, 13).toString().trim().compareTo(jTPedimen.getText().trim())==0 && jTab.getValueAt(x, 14).toString().trim().compareTo(jTCadu.getText().trim())==0 && jTab.getValueAt(x, 15).toString().trim().compareTo(jTId.getText().trim())==0 && jTab.getValueAt(x, 19).toString().trim().compareTo(jTSerProd.getText().trim())==0 && jTab.getValueAt(x, 21).toString().trim().compareTo(jTGara.getText().trim())==0)
            {                
                /*Obtiene la cantidad, el precio y el valor del impuesto en la tabla*/
                String sCant        = jTab.getValueAt(x, 0).toString();
                String sPre         = jTab.getValueAt(x, 5).toString().replace("$", "").replace(",", "");
                String sV           = jTab.getValueAt(x, 8).toString();                               

                /*Suma la cantidad a la que ya estaba*/
                sCant               = Integer.toString(Integer.parseInt(sCant) + 1);
                                
                /*Si no se puede vender con exist baja entonces*/
                if(bSiExistN && (Double.parseDouble(sCant) > Double.parseDouble(sExist)))
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "No se puede vender sin existencias.", "Existencias", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
                    return;                    
                }

                /*Si hay pedimento o lote entonces*/
                if(jTLot.getText().compareTo("")!=0 || jTPedimen.getText().compareTo("")!=0 || jTCadu.getText().compareTo("")!=0 || jTId.getText().compareTo("")!=0)
                {
                    /*Obtiene la cantidad de piezas con ese id en las partidas de las compra para saber*/                       
                    String sCantPend    = "";
                    try
                    {
                        sQ = "SELECT cantlotpend FROM partcomprs WHERE id_id = " + jTId.getText();	                
                        st = con.createStatement();
                        rs = st.executeQuery(sQ);
                        /*Si hay datos entonces obtiene la cantidad pendiente de vender de los lotes*/
                        if(rs.next())               
                            sCantPend   = rs.getString("cantlotpend");                                                                           
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                        return;                                                                                                                            
                    }

                    /*Recorre la tabla de partidas para búscar si hay partidas con lotes y pedimento*/            
                    int row                         = 0;           
                    for( ; row < jTab.getRowCount(); row++)
                    {
                        /*Si ya existe ese lote en la fila entonces*/            
                        if(jTab.getValueAt(row, 12).toString().compareTo(jTLot.getText())==0 && jTab.getValueAt(row, 13).toString().compareTo(jTPedimen.getText())==0 && jTab.getValueAt(row, 14).toString().compareTo(jTCadu.getText())==0 && jTab.getValueAt(row, 15).toString().compareTo(jTId.getText())==0)
                        {
                            /*Crea la cantidad correcta que se puede insertar*/
                            String sCantCo  = Double.toString(Double.parseDouble(sCantPend) - Double.parseDouble(jTab.getValueAt(row, 0).toString()));

                            /*Si la cantidad que se quiere insertar es mayor a la permitida entonces*/
                            if(Double.parseDouble(jTCant.getText())>Double.parseDouble(sCantCo))
                            {
                                //Cierra la base de datos
                                if(Star.iCierrBas(con)==-1)
                                    return;

                                /*Mensajea*/
                                JOptionPane.showMessageDialog(null, "No se pueden insertar mas cantidades para lote y pedimento que las existentes.", "Lote y Pedimento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                                /*Coloca el borde rojo*/                               
                                jTCant.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                                /*Coloca el foco del teclado en el campo de la cantidad y regresa*/
                                jTCant.grabFocus();                        
                                return;
                            }
                        }

                    }/*Fin de for( ; row < jTab.getRowCount(); row++)*/

                }/*Fin de if(jTLot.getText().compareTo("")!=0 || jTPedimen.getText().compareTo("")!=0 || jTCadu.getText().compareTo("")!=0)*/

                /*Genera el importe que es la cantidad por el precio unitario*/
                String sImp1        = Double.toString(Double.parseDouble(sPre) * Integer.parseInt(sCant));               
                
                /*Obtiene el impuesto*/
                String sImpTot      = Double.toString((Double.parseDouble(sPre) * Integer.parseInt(sCant)) / ((Double.parseDouble(sV) / 100) + 1));
                sImpTot             = Double.toString(Double.parseDouble(sImp1) - Double.parseDouble(sImpTot));                                                
                
                /*Dale formato de moneda al importe*/                
                double dCant        = Double.parseDouble(sImp1);                
                NumberFormat n      = NumberFormat.getCurrencyInstance(Locale.getDefault());
                sImp1               = n.format(dCant);
                
                /*Dale formato de moneda al impuesto total*/                
                dCant               = Double.parseDouble(sImpTot);                                
                sImpTot             = n.format(dCant);

                /*Coloca la cantidad en la fila y el importe nuevo*/
                jTab.setValueAt(sCant,      x,      0);
                jTab.setValueAt(sImp1,      x,      6);
                jTab.setValueAt(sImpTot,    x,      7);
                
                /*Selecciona todo el campo del código del producto*/
                jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());
                
                /*Recalcula los totales*/
                vRecTot();
                
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Muestra que esta cargando la imágen*/
                jLImg.setText("Cargando...");

                /*Obtiene la imágen si es que tiene*/
                (new Thread()
                {
                    @Override
                    public void run()
                    {
                        /*Carga la imágen en el control*/
                        Star.vGetImg(jTProd.getText(), jLImg);

                        /*Muestra que se termino de cargar la imágen*/
                        jLImg.setText("");
                    }
                }).start();

                /*Llama al contador de partidas*/
                vContP();
                
                /*Regresa para que ya no continue*/
                return;                
        
            }/*Fin de if(sCodProd.compareTo(jTablePartidas.getValueAt(x, 1).toString())==0)*/
            
        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
        
        /*Obtiene el precio que debe tener correctamente por las reglas de negocio*/
        String sPre     = Star.sPreCostVta(jTProd.getText().trim(), jTCli.getText().trim(), "1", "vtas", "pvta");                
        /*Si hubo error entonces regresa*/
        if(sPre==null)
            return;                

        /*Tokeniza para obtener el precio y la lista*/
        java.util.StringTokenizer stk = new java.util.StringTokenizer(sPre, "|");
        sPre            = stk.nextToken();
        String sList    = stk.nextToken();
        
        /*Si el precio es 0 entonces*/
        if(Double.parseDouble(sPre)==0)
        {            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "El producto no tiene precio definido.", "Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }
                
        /*Guarda el precio de lista original*/
        sPreOri             = sPre;
        
        /*Coloca la lista en su lugar*/
        jTList.setText  (sList);

        /*Obtiene el importe del descuento*/
        String sImpoDesc    = Double.toString(Double.parseDouble(sPre) * (Double.parseDouble(jTDesc.getText()) / 100));
        
        /*Aplicale al precio unitario el descuento permitido*/
        sPre                = Double.toString(Double.parseDouble(sPre) - (Double.parseDouble(sPre) * (Double.parseDouble(jTDesc.getText()) / 100)));
                
        /*Obtiene el impuesto del precio unitario*/
        String sImpueRel    = Double.toString(Double.parseDouble(sPre) * (Double.parseDouble(sVal) / 100));
                    
        /*Aumentale el impuesto al precio*/
        sPre                = Double.toString((Double.parseDouble(sPre) + Double.parseDouble(sImpueRel)));
        
        /*Dale formato de moneda al precio unitario ya con impuesto*/	        
        NumberFormat n      = NumberFormat.getCurrencyInstance(Locale.getDefault());
        double dCant        = Double.parseDouble(sPre);                        
        sPre                = n.format(dCant);
        
        /*Dale formato de moneda al impuesto solamente*/	
        dCant               = Double.parseDouble(sImpueRel);                        
        sImpueRel           = n.format(dCant);
        
        /*Dale formato de moneda al precio original*/	
        dCant               = Double.parseDouble(sPreOri);                        
        sPreOri             = n.format(dCant);
                
        /*Dale formato de moneda al importe del descuento*/	
        dCant               = Double.parseDouble(sImpoDesc);                        
        sImpoDesc           = n.format(dCant);
        
        /*Si esta permitida la garantía entonces obtiene la garantia del control*/
        String  sGara       = "";
        if(bGara)
            sGara           = jTGara.getText().trim();

        //Determina las placas
        String sPlac        = sPlacs;
        if(sPlac.compareTo("")!=0)
            sPlac           = "PLACAS:" + sPlac;
        
        //Determina recibido por
        String sReci        = this.sRecib;
        if(sReci.compareTo("")!=0)
            sReci           = "RECIBIDO:" + sReci;
        
        /*Agrega la partida a la tabla*/
        DefaultTableModel temp  = (DefaultTableModel)jTab.getModel();
        Object nu[]             = {jTCant.getText(), jTProd.getText(), jComUnid.getSelectedItem().toString().trim(), sDescripCort + " " + sGara + " " + jTTall.getText() + " " + jTColo.getText() + " " + sPlac + " " + sReci, jTDesc.getText(), sPre, sPre, sImpueRel, sVal, sAlma, jTTall.getText(), jTColo.getText(), jTLot.getText(), jTPedimen.getText(), jTCadu.getText(), jTId.getText(), sKit, sPreOri, sImpoDesc, jTSerProd.getText(), jTComenSer.getText(), sGara, sCost, jTCantLot.getText().trim(), sImp};
        temp.addRow(nu);
        
        /*Selecciona todo el campo del código del producto*/
        jTProd.setSelectionStart(0);jTProd.setSelectionEnd(jTProd.getText().length());
        
        /*Recalcula los totales*/
        vRecTot();
        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor." + System.getProperty( "line.separator" ) + "Entonces no se podrán generar los PDF de las ventas generadas.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                       
            return;                        
        }
        
        /*Si la carpeta de las imágenes no existe entonces crea la ruta*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de los prods no existe entonces crea la ruta*/
        sCarp                    += "\\Productos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta de la cliente no existe entonces crea la ruta*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la carpeta del producto en específico no existe entonces crea la ruta*/
        sCarp                    += "\\" + jTProd.getText();
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
               
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if(new File(sCarp).list().length > 0)
            {            
                /*Obtiene la lista de directorios*/
                String sArchs [] = new File(sCarp).list();

                /*Carga la imágen en el panel*/
                jLImg.setIcon(new ImageIcon(sCarp + "\\" + sArchs[0]));

                /*Que el label sea visible*/
                jLImg.setVisible(true);

            }
            /*Else, no existe imágen entonces que no sea visible*/
            else
                jLImg.setVisible(false);
        }                   
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Llama al contador de partidas*/
        vContP();
                
    }/*Fin de private void vFunEsc()*/
    
    
    /*Llama al contador de partidas*/
    private void vContP()
    {
        /*Si no hay partidas entonces*/
        if(jTab.getRowCount()==0)
        {
            /*El total de cantidades es cero y regresa*/
            jTQtyP.setText("0 pzas");
            return;
        }
        
        /*Almacena las cantidades*/
        int iCant   = 0;
        
        /*Recorre toda la tabla de partidas y ve sumando las cantidades*/
        for(int x = 0; x < jTab.getRowCount(); x++)
            iCant   +=  Integer.parseInt(jTab.getValueAt(x, 0).toString());
        
        /*Coloca la cantidad final en el campo*/
        jTQtyP.setText(Integer.toString(iCant) + " pzas");
        
    }/*Fin de private void vContP()*/            
                
                
    /*Cuando sucede una acción en el control del código del artículo*/
    private void jTProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTProdActionPerformed
                
        /*Presiona el botón de agregar partida*/
        jBNew.doClick();
            
    }//GEN-LAST:event_jTProdActionPerformed

    
    /*Genera los importes correctos*/
    private void vGenImp()
    {
        /*Recorre toda la tabla de partidas*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Obtiene algunos datos de la fila*/
            String sCant           = jTab.getValueAt(x, 0).toString();
            String sDesc           = jTab.getValueAt(x, 4).toString();
            String sPre            = jTab.getValueAt(x, 5).toString().replace("$", "").replace(",", "");
            String sImpue          = jTab.getValueAt(x, 8).toString();
            
            /*Obtiene el importe del descuento*/
            String sImpoDesc= Double.toString((Double.parseDouble(sPre)) * (Double.parseDouble(sDesc) / 100));
            
            /*Obtiene el precio unitario ya con el descuento aplicado*/
            sPre            = Double.toString(Double.parseDouble(sPre) - (Double.parseDouble(sImpoDesc)));
            
            /*Obtiene el impuesto unitario*/
            sImpue          = Double.toString(Double.parseDouble(sPre) - (Double.parseDouble(sPre) / (1 + (Double.parseDouble(sImpue) / 100))));            
            
            /*Obtiene el importe*/
            String  sImpo   = Double.toString((Double.parseDouble(sPre)) * Double.parseDouble(sCant));
            
            /*Dale formato de moneda al precio*/            
            NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
            double dCant    = Double.parseDouble(sPre);                            
            sPre            = n.format(dCant);
            
            /*Dale formato de moneda al impuesto*/            
            dCant           = Double.parseDouble(sImpue);                            
            sImpue          = n.format(dCant);
            
            /*Dale formato de moneda al importe*/            
            dCant           = Double.parseDouble(sImpo);                            
            sImpo           = n.format(dCant);
                                 
            /*Dale formato de moneda al importe del descuento*/            
            dCant           = Double.parseDouble(sImpoDesc);                            
            sImpoDesc       = n.format(dCant);
            
            /*Coloca el precio unitario, impuesto, importe y importe  del descuento en su lugar*/
            jTab.setValueAt(sPre,       x, 5);                                    
            jTab.setValueAt(sImpo,      x, 6);            
            jTab.setValueAt(sImpue,     x, 7);
            jTab.setValueAt(sImpoDesc,  x, 18);
                        
        }/*Fin de for(int x = 0; x < jTablePartidas.getRowCount(); x++)*/
        
    }/*Fin de private void vGenImp()*/
                    
                    
    /*Recalcula los totales*/
    private void vRecTot()
    {
        //Declara variables locales        
        String  sTotImpue    = "0";        
        String  sTot         = "0";                
        String  sTotDesc     = "0";
        String  sTotCost     = "0";
        
        
        
        
        
        
        
        /*Recorre toda la tabla de partidas*/
        for(int x = 0; x < jTab.getRowCount(); x++)
        {
            /*Ve sumandolo al total*/
            sTot            = Double.toString(Double.parseDouble(sTot) + Double.parseDouble(jTab.getValueAt(x,6).toString().replace("$", "").replace(",", "")));
            
            /*Ve sumandolo al total de impuesto*/
            sTotImpue       = Double.toString(Double.parseDouble(sTotImpue) + Double.parseDouble(jTab.getValueAt(x,7).toString().replace("$", "").replace(",", "")));
            
            /*Ve sumandolo al total del descuento*/
            sTotDesc        = Double.toString(Double.parseDouble(sTotDesc) + Double.parseDouble(jTab.getValueAt(x,18).toString().replace("$", "").replace(",", "")));
            
            /*Ve sumando el costo al global*/
            sTotCost        = Double.toString(Double.parseDouble(sTotCost) + (Double.parseDouble(jTab.getValueAt(x,22).toString()) * Double.parseDouble(jTab.getValueAt(x,0).toString())));
        }
        
        /*Obtiene el sub total*/
        String sSubTot      = Double.toString(Double.parseDouble(sTot) - Double.parseDouble(sTotImpue));        
        
        //Comprueba si debe de redondear o no el total
        String sResp    = Star.sGetConfRedon(null);
        
        //Si hubo error entonces regresa
        if(sResp==null)
            return;
        
        //Obtiene si se tiene que redondear y las posiciones a redondear
        java.util.StringTokenizer stk            = new java.util.StringTokenizer(sResp, "|");
        String sRedo    = stk.nextToken();
        String sNumRedo = stk.nextToken();                
        
        //Si no se tiene que redondear entonces deja la cantidad en 2
        if(sRedo.compareTo("0")==0)
            sNumRedo    = "2";
        
        //Redondea el total
        sTot                = Double.toString(Star.dRound(Double.parseDouble(sTot), Integer.parseInt(sNumRedo)));
        
        /*Dale formato de moneda a los totales*/	
        NumberFormat n      = NumberFormat.getCurrencyInstance(Locale.getDefault());
        double dCant        = Double.parseDouble(sTot);                
        sTot                = n.format(dCant);        
        dCant               = Double.parseDouble(sTotImpue);                
        sTotImpue           = n.format(dCant);
        dCant               = Double.parseDouble(sSubTot);                
        sSubTot             = n.format(dCant);
        dCant               = Double.parseDouble(sTotDesc);                
        sTotDesc            = n.format(dCant);
        
        /*Colocalos en el campo*/
        jTSubTot.setText    (sSubTot);
        jTImpue.setText     (sTotImpue);
        jTTotDesc.setText   (sTotDesc);
        jTTot.setText       (sTot);        
        jTTotCost.setText   (sTotCost);
                
    }/*Fin de private void vRecTot()*/
    
    
    /*Cuando se presiona el botón de borrar partidas*/
    private void jBNewVtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewVtaActionPerformed
        
        /*Preguntar al usuario si esta seguro de querer borrrar las partidas*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres borrar las partidas?", "Borrar Partidas", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       

        /*Borra toda la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea los totales*/
        jTTot.setText       ("$0.00");
        jTSubTot.setText    ("$0.00");
        jTImpue.setText     ("$0.00");
        jTTotDesc.setText   ("$0.00");
        
        /*Resetea la cantidad*/
        jTCant.setText      ("1");
        
        /*Coloca el foco del teclado en el control del código del producto*/
        jTProd.grabFocus();
        
        /*Que no sea visible la imágen*/
        jLImg.setVisible(false);

        /*Coloca venta de contado en el label*/
        jLTipVta.setText("VENTA DE CONTADO");

        /*Coloca la cantidad de lote y pedimento a 0*/
        jTCantLot.setText           ("0");
        
        /*Llama al contador de partidas*/
        vContP();
                
    }//GEN-LAST:event_jBNewVtaActionPerformed
        
    
    /*Cuando se presiona el botón de borrar partida*/
    private void jBDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDelActionPerformed

        /*Si no se a seleccionado nada en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una partida.", "Borrar Partida", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el código del producto y regresa*/
            jTProd.grabFocus();            
            return;
        }
        
        /*Si no hay selecciòn entonces regresa*/
        if(jTab.getSelectedRow()==-1)
            return;
        
        /*Borra la fila*/
        DefaultTableModel model=(DefaultTableModel)jTab.getModel();
        model.removeRow(jTab.getSelectedRow());                
        
        /*Que no sea visible el label de la imágen*/
        jLImg.setVisible(false);
        
        /*Coloca le foco del teclado en el control del código del producto*/
        jTProd.grabFocus();
        
        /*Recalcula el total*/
        vRecTot();
        
        /*Llama al contador de partidas*/
        vContP();
                
    }//GEN-LAST:event_jBDelActionPerformed

    
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
        
        /*Función escalable para cuando sucede una acción en el campo del código del producto*/
        vFunEsc();

        /*Resetea el pedimento*/
        jTLot.setText       ("");
        jTPedimen.setText   ("");
        jTCadu.setText      ("");
        jTId.setText        ("");
        
        /*Coloca 1 en el campo de la cantidad*/
        jTCant.setText      ("1");
        
        /*Coloca 0 en el campo del descuento*/
        jTDesc.setText      ("0");
                
        /*Coloca le foco del teclado en el control del código del producto*/
        jTProd.grabFocus();
        
        /*Resetea algunos otros controles*/
        jTSerProd.setText   ("");
        jTComenSer.setText  ("");

        /*Resetea la cantidad de lote y pedimento*/
        jTCantLot.setText   ("0");
        
    }//GEN-LAST:event_jBNewActionPerformed

    
    /*Cuando se presiona el botón de calculadora*/
    private void jB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jB1ActionPerformed

        /*Muestra la calculadora que este configurada*/
        Star.vShowCal();
        
        /*Pon el foco del teclado en el control del código del producto*/
        jTProd.grabFocus();
        
    }//GEN-LAST:event_jB1ActionPerformed

    
    /*Cuando se presiona una tecla en el panel de líneas*/
    private void jPanelLinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanelLinKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPanelLinKeyPressed

    
    /*Cuando se presiona una tecla en el scroll pan de las líneas*/
    private void jScrollPaneLinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jScrollPaneLinKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jScrollPaneLinKeyPressed

    
    /*Cuando se presiona una tecla en el panel de prods*/
    private void jPanProdsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanProdsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPanProdsKeyPressed

    
    /*Cuando se presiona una tecla en el scroll paner de los prods*/
    private void jScrollProdsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jScrollProdsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jScrollProdsKeyPressed

    
    /*Cuando se presiona una tecla en el botón de cobrar*/
    private void jBCobKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCobKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCobKeyPressed

    
    /*Cuando se presiona el botón de cobrar*/
    private void jBCobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCobActionPerformed
        
        /*Lee el total*/
        String sTot        = jTTot.getText().replace("$", "").replace(",", "");
        
        /*Si el total es 0 entonces*/
        if(Double.parseDouble(sTot)==0)
        {
            /*Coloca el foco del teclado en el control del producto y regresa*/
            jTProd.grabFocus();            
            return;
        }
        
        /*Si el nombre del cliente es vacio entonces*/
        if(jTNomb.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un cliente.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el campo del cliente y regres*/
            jTCli.grabFocus();                        
            return;
        }
                
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si el cliente esta bloqueado o no*/        
        try
        {
            sQ = "SELECT bloq FROM emps WHERE CONCAT_WS('', ser, codemp ) = '" + jTCli.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Pon la bandera correcta*/
                if(rs.getString("bloq").compareTo("1")==0)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Mensajea*/
                    JOptionPane.showMessageDialog(null, "El cliente: " + jTCli.getText() + " esta bloqueado.", "Cliente", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
                    
                    /*Pon el foco del teclado en el campo de la cliente y regresa*/
                    jTCli.grabFocus();                                        
                    return;
                }                                                    
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
                
        /*Comprueba si existe impresora asociada para el usuario actual*/        
        try
        {
            sQ = "SELECT imptic FROM estacs WHERE estac = '" + Login.sUsrG + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos, entonces no tiene impresora*/
            if(!rs.next())
            {
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No se a definido impresora para el usuario: " + Login.sUsrG + ". " + System.getProperty( "line.separator" ) + "Entonces no se podrán imprimir las ventas generadas.", "Impresora",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                       
                return;
            }            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }
                        
        //Trae la carpeta compartida de la aplicación y la ruta en el servidor de la base de datos
        String sCarp    = Star.sGetRutCarp(con);                    

        //Si hubo error entonces regresa
        if(sCarp==null)
            return;
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces*/
        if(sCarp.compareTo("")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor. " + System.getProperty( "line.separator" ) + "Entonces no se podrán generar los PDF de las ventas generadas.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                           
            return;
        }
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;
            
        /*Dale formato de menda al total nuevamente*/	
        double dCant    = Double.parseDouble(sTot);                
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
        sTot            = n.format(dCant);

        /*Determina la forma de pago*/
        String sFormPag = "C";
        if(jLTipVta.getText().trim().compareTo("VENTA DE CONTADO")!=0)
            sFormPag    = "CR";
        
        /*Muestra la forma de cobro*/
        Cobro c         = new Cobro (sTot, jTCli.getText(), jTSer.getText(), jTFec.getText(), jTNomb.getText(), jTab, jTTot, jLImg, jTProd, jTDesc.getText(), jTSubTot.getText(), jTImpue.getText(), jTCli, jTNomb, jTBuscGral.getText(), jTImpue, jTSubTot, jTQtyP, jTTotDesc, jTTotCost, jTMon.getText().trim(), sFormPag, frmMe);
        c.setVisible    (true);
        
    }//GEN-LAST:event_jBCobActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo del código de la cliente*/
    private void jTCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCli.setSelectionStart(0);jTCli.setSelectionEnd(jTCli.getText().length());        
        
    }//GEN-LAST:event_jTCliFocusGained

    
    /*Cuando se presiona una tecla en el campo del código de la cliente*/
    private void jTCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBCli.doClick();
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);            
        
    }//GEN-LAST:event_jTCliKeyPressed

    
    /*Cuando se tipea una tecla en el campo del código de la cliente*/
    private void jTCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCliKeyTyped
        
        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTCliKeyTyped

    
    /*Cuando se pierde el foco del teclado en el campo del código de la cliente entonces*/
    private void jTCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCliFocusLost
        
        /*Limpia toda la tabla de partidas de venta*/            
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);              

        /*Resetea los totales*/
        jTTot.setText       ("$0.00");
        jTSubTot.setText    ("$0.00");
        jTImpue.setText     ("$0.00");
        jTTotDesc.setText   ("$0.00");

        /*Resetea la cantidad*/
        jTCant.setText      ("1");
                    
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene el nombre de la cliente base a su código y si no existe activa la bandera*/
        boolean bSiExiste   = false;
        try
        {                  
            sQ = "SELECT nom FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTCli.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {
                /*Pon la bandera para saber que si existe esta cliente*/
                bSiExiste = true;
                                
                /*Coloca el nom de la cliente en el campo*/
                jTNomb.setText(rs.getString("nom"));                                                                                                               
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

        /*Valida el tipo de venta que será*/
        vValTip();
        
        /*Si el código de la cliente no existe entonces*/
        if(!bSiExiste)
        {
            /*Coloca al cliente mostrador*/
//            jTNomb.setText  (Star.sNomCliMostG);            
//            jTCli.setText   (Star.sCliMostG);                                                
//            jTSer.setText   (Star.sSerMostG);
//            jLTipVta.setText("VENTA DE CONTADO");
            jLTipVta.setText("VENTA DE CONTADO");
            jTNomb.setText  ("");
            jTSer.setText   ("");
        }                   
        
    }//GEN-LAST:event_jTCliFocusLost

    
    /*Cuando se presiona una tecla en el campo del nom*/
    private void jTNombKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNombKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTNombKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la fecha*/
    private void jTFecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFecKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFecKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del nom*/
    private void jTNombFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTNombFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTNomb.setSelectionStart(0);jTNomb.setSelectionEnd(jTNomb.getText().length());        
        
    }//GEN-LAST:event_jTNombFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de fecha*/
    private void jTFecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFecFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFec.setSelectionStart(0);jTFec.setSelectionEnd(jTFec.getText().length());        
        
    }//GEN-LAST:event_jTFecFocusGained

    
    /*Cuando se gana el foco del teclado en el campo del descuento*/
    private void jTDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDesc.setSelectionStart(0);jTDesc.setSelectionEnd(jTDesc.getText().length());        
        
        /*Guarda el descuento inicial*/
        sDescGlo    = jTDesc.getText();
        
    }//GEN-LAST:event_jTDescFocusGained

    
    /*Cuando se presiona una tecla en el campo del descuento*/
    private void jTDescKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
            
    }//GEN-LAST:event_jTDescKeyPressed

    
    /*Cuando se tipea una tecla en el campo del descuento*/
    private void jTDescKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTDescKeyTyped

    
    /*Cuando se pierde lee foco del teclado en el campo del descuento*/
    private void jTDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescFocusLost
        
        /*Si el campo es cadena vacia entones*/
        if(jTDesc.getText().compareTo("")==0)
        {
            /*Coloca cero en el campo y regresa*/
            jTDesc.setText("0");                        
            return;
        }                          
        
        /*Si el valor introducido es menor a 0 o mayor a 100 entonces*/
        if(Double.parseDouble(jTDesc.getText()) < 0 || Double.parseDouble(jTDesc.getText()) > 100 )
        {
            /*Coloca en el campo 0 y regresa*/
            jTDesc.setText("0");            
            return;
        }                

        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene el descuento posible para esta usuario*/
        String sDesc    = "100";
        try
        {
            sQ = "SELECT descu, habdesc FROM estacs WHERE estac = '" + Login.sUsrG + "'" ;                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            if(rs.next())
            {    
                /*Obtiene el descuento de El usuario*/
                sDesc         = rs.getString("descu");                
                
                /*Si el descuento esta deshabilitado para esta usuario entonces*/
                if(rs.getString("habdesc").compareTo("0")==0 && Double.parseDouble(jTDesc.getText())>0)
                {
                    //Cierra la base de datos
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Coloca 0 en el campo del descuento*/
                    jTDesc.setText("0");
                    
                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "El descuento para el usuario: " + Login.sUsrG + " esta deshabilitado.", "Descuento", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                         
                    return;                                                
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
        
        /*Si el descuento que se quiere dar es mayor al permitido entonces*/
        if(Double.parseDouble(jTDesc.getText()) > Double.parseDouble(sDesc))
        {
            /*Coloca le descuento original que tenía*/
            jTDesc.setText(sDescGlo);
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El descuento máximo permitido para el usuario: " + Login.sUsrG + " es: " + sDesc + "%.", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Pon el foco del teclado en el campo del descuento*/
            jTDesc.grabFocus();
        }
        
    }//GEN-LAST:event_jTDescFocusLost

    
    /*Cuando sucede una acción en el campo del descuento*/
    private void jTDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTDescActionPerformed
        
        /*Pon el foco del teclado en el campo del producto*/
        jTProd.grabFocus();
        
    }//GEN-LAST:event_jTDescActionPerformed

    
    /*Cuando se gana el foco del teclado en el campo de mon*/
    private void jTMonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMonFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMon.setSelectionStart(0);jTMon.setSelectionEnd(jTMon.getText().length());                                
        
    }//GEN-LAST:event_jTMonFocusGained

    
    /*Cuando se presiona una tecla en el campo de la mon*/
    private void jTMonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMonKeyPressed
        
        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b    = new Busc(this, jTMon.getText(), 10, jTMon, null, null, "", null);
            b.setVisible(true);
            
            /*Pon el foco del teclado en el campo de el código del producto*/
            jTProd.grabFocus();
            
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);            
        
    }//GEN-LAST:event_jTMonKeyPressed
        
            
    /*Cuando se presiona una tecla en el botón de cargar prods*/
    private void jBProdsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdsKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProdsKeyPressed

    
    /*Cuando se presiona el botón de cargar prods*/
    private void jBProdsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdsActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc       (this, jTProd.getText(), 2, jTProd, null, null, "ptovta", null);
        b.setVisible            (true);        
                    
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Coloca la unidad del producto en el combobox*/
        jComUnid.setSelectedItem(Star.sGetUnidProd(con, jTProd.getText().trim()));
        
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Muestra que esta cargando la imágen*/
        jLImg.setText("Cargando...");
        
        /*Obtiene la imágen si es que tiene*/
        (new Thread()
        {
            @Override
            public void run()
            {
                /*Carga la imágen en el control*/
                Star.vGetImg(jTProd.getText(), jLImg);
                
                /*Muestra que se termino de cargar la imágen*/
                jLImg.setText("");
            }
        }).start();

        /*Coloca le foco del teclado en el control del código del producto*/
        jTProd.grabFocus();        
        
    }//GEN-LAST:event_jBProdsActionPerformed

    
    /*Cuando se hace clic en el campo del código del cliente*/
    private void jTCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTCliMouseClicked
        
//        /*Llama al otro formulario de búsqueda sin filtro*/
//        Busc b = new Busc(this, "", 5, jTCli, jTNomb, jTSer, "", null);
//        b.setVisible(true);
//        
//        /*Coloca le foco del teclado en el control del código del producto*/
//        jTProd.grabFocus();  
//            
    }//GEN-LAST:event_jTCliMouseClicked
    
    
    /*Cuando se gana el foco del teclado en el campo de bùscar catálogo general*/
    private void jTBuscGralFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscGralFocusGained

        /*Coloca la posición del ratón al final del campo*/
        jTBuscGral.setCaretPosition(jTBuscGral.getText().length());
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTBuscGral.setSelectionStart(0);jTBuscGral.setSelectionEnd(jTBuscGral.getText().length());                        
        
    }//GEN-LAST:event_jTBuscGralFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de bùscar general*/
    private void jTBuscGralFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTBuscGralFocusLost
    
        /*Coloca el cursor del ratón al principio del campo*/
        jTBuscGral.setCaretPosition(0);
        
    }//GEN-LAST:event_jTBuscGralFocusLost

    
    /*Cuando se presiona una tecla en el campo de bùscar en catálogo general*/
    private void jTBuscGralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscGralKeyPressed

        /*Si se presiona la tecla de abajo entonces*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTBuscGral.getText(), 14, jTBuscGral, null, null, "", null);
            b.setVisible(true);
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTBuscGralKeyPressed

    
    /*Cuando se tipea una tecla en el campo de bùscar catálogo general*/
    private void jTBuscGralKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTBuscGralKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))       
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));                              
        
    }//GEN-LAST:event_jTBuscGralKeyTyped

    
    /*Cuando se presiona el botón de búscar catálogo general*/
    private void jBBuscGralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscGralActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTBuscGral.getText(), 14, jTBuscGral, null, null, "", null);
        b.setVisible(true);

        /*Coloca el foco del teclado en el campo del código del producto*/
        jTCodProd.grabFocus();
        
    }//GEN-LAST:event_jBBuscGralActionPerformed

    
    /*Cuando se presiona una tecla en el botón de buscar catálogo general*/
    private void jBBuscGralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBuscGralKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBuscGralKeyPressed

    
    /*Cuando se arrastra la rueda del mouse en la forma*/
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

            
    /*Cuando se presiona una tecla en el botón de nueva cliente*/
    private void jBNewEmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNewEmpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBNewEmpKeyPressed

    
    /*Cuando se presiona el botón de nueva cliente*/
    private void jBNewEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewEmpActionPerformed
        
        /*Colcoa le foco del teclado en el campo del producto*/
        jTProd.grabFocus();

        /*Abre la forma de nueva cliente*/
        NewClienExp  n = new NewClienExp();
        n.setVisible(true);
        
    }//GEN-LAST:event_jBNewEmpActionPerformed

    
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

    
    /*Cuando se presiona una tecla en el botón de búscar cliente*/
    private void jBCliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBCliKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBCliKeyPressed

    
    /*Cuando se presiona el botón de búscar cliente*/
    private void jBCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCliActionPerformed
        
        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTCli.getText(), 5, jTCli, jTNomb, null, "", null);
        b.setVisible(true);

        /*Obtiene la lista de precio del cliente*/
        vLisPrec();
        
        /*Valida el tipo de venta que será*/
        vValTip();
        
        /*Coloca el foco del teclado en el campo del producto*/
        jTProd.grabFocus();

    }//GEN-LAST:event_jBCliActionPerformed

    
    /*Obtiene la lista de precio del cliente*/
    private void vLisPrec()
    {
        /*Si no hay un cliente entonces regresa*/
        if(jTCli.getText().compareTo("")==0)
            return;
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Obtiene la lista de precios del cliente*/        
        try
        {
            sQ  = "SELECT list FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTCli.getText().trim() + "'";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el control, caso contrario coloca la lista 1*/
            if(rs.next())
                jTList.setText(rs.getString("list"));
            else
                jTList.setText("1");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                                                                                             
        }

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vLisPrec()*/
        
        
    /*Valida el tipo de venta que será*/
    private void vValTip()
    {
        /*Si no hay un cliente entonces regresa*/
        if(jTCli.getText().compareTo("")==0)
            return;
        
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;

        /*Comprueba si la venta va a ser de contado o a crédito*/        
        try
        {
            sQ  = "SELECT CASE WHEN (diacred > 0 AND limtcred > 0) THEN 1 ELSE 0 END AS tip FROM emps WHERE CONCAT_WS('', ser, codemp) = '" + jTCli.getText().trim() + "'";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca en el label si es de contado o a crédito*/
                if(rs.getString("tip").compareTo("1")==0)                    
                    jLTipVta.setText("VENTA A CRÉDITO");
                else
                    jLTipVta.setText("VENTA DE CONTADO");
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
        
    }/*Fin de private void vValTip()*/
        
        
    /*Cuando se presiona el botón de teclado en el punto de venta*/
    private void jBTeclaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTeclaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBTeclaActionPerformed

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el control de la imágen*/
    private void jPanImgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImgMouseEntered

        /*Si no a seleccionado un prodcuto válido entonces regresa*/
        if(jTProd.getText().compareTo("")==0)
            return;

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
        
        /*Si la carpeta de la aplicación compartida en el servidor no esta definida entonces regresa*/
        if(sCarp.compareTo("")==0)
            return;

        /*Si la carpeta de las imágenes no existe entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de los usuarios no existe entonces creala*/
        sCarp                    += "\\Productos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la aplicación no existe entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de El usuario en específico no existe entonces creala*/
        sCarp                    += "\\" + jTProd.getText();
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if(new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista de directorios*/
                String sArc [] = new File(sCarp).list();

                /*Muestra la forma para ver la imágen en otra vista*/
                v = new ImgVis(sCarp + "\\" + sArc[0]);            
                v.setVisible(true);
            }
        }                    

    }//GEN-LAST:event_jPanImgMouseEntered

    
    /*Cunado el mouse sale del panel de la imágen*/
    private void jPanImgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanImgMouseExited

        /*Si el visor no es nulo entonces escondelo*/
        if(v!=null)
            v.setVisible(false);
        
    }//GEN-LAST:event_jPanImgMouseExited

    
    /*Cuando se presiona una tecla en el control de la imágen*/
    private void jPanImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanImgKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jPanImgKeyPressed

    
    /*Cuando se presiona el botón de ver en grande la imágen*/
    private void jBVeGranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVeGranActionPerformed

        /*Si a ingresado un producto entonces*/
        if(jTProd.getText().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un producto válido.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Pon el foco del teclado en el control y regresa*/
            jTProd.grabFocus();
            return;
        }

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
            JOptionPane.showMessageDialog(null, "No se a definido la carpeta compartida de la aplicación en el servidor. " + System.getProperty( "line.separator" ) + "Entonces no se podrán generar los PDF de las ventas generadas.", "Servidor",  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            return;
        }

        /*Si la carpeta de las imágenes no existen entonces creala*/
        sCarp                    += "\\Imagenes";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de los prods no existen entonces creala*/
        sCarp                    += "\\Productos";
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta de la empresa no existen entonces creala*/
        sCarp                    += "\\" + Login.sCodEmpBD;
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();

        /*Si la carpeta del prodcuto en específico no existe entonces creala*/
        sCarp                    += "\\" + jTProd.getText();
        if(!new File(sCarp).exists())
            new File(sCarp).mkdir();
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length == 0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "No existe imágen para el producto: " + jTProd.getText() + " en \"" + sCarp + "\".", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
        }                    
        
        /*Si la imágen existe entonces*/
        if(new File(sCarp).exists())
        {
            /*Si tiene ficheros entonces*/
            if( new File(sCarp).list().length > 0)
            {
                /*Obtiene la lista del archivo*/
                String sArch [] = new File(sCarp).list();

                /*Abre el archivo*/
                try
                {
                    Desktop.getDesktop().open(new File(sCarp + "\\" + sArch[0]));
                }
                catch(IOException expnIO)
                {
                    //Procesa el error
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                                    
                }
            }
        }                    
        
    }//GEN-LAST:event_jBVeGranActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver imágen en grande*/
    private void jBVeGranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBVeGranKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBVeGranKeyPressed

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCli.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCliMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewEmpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewEmpMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNewEmp.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewEmpMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewVtaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewVtaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNewVta.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewVtaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBDel.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBDelMouseEntered

        
    /*Cuando el mouse entra en el botón específico*/
    private void jB1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jB1MouseEntered
        
        /*Cambia el color del fondo del botón*/
        jB1.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jB1MouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTeclaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTeclaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTecla.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTeclaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBProdsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdsMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBProds.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBProdsMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBCobMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCobMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBCob.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBCobMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBBuscGralMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscGralMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBBuscGral.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBuscGralMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBVeGranMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGranMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBVeGran.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBVeGranMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewVtaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewVtaMouseExited
     
        /*Cambia el color del fondo del botón al original*/
        jBNewVta.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewVtaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBDelMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBDel.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBDelMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jB1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jB1MouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jB1.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jB1MouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTeclaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTeclaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBTecla.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTeclaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCliMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCli.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBCliMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewEmpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewEmpMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNewEmp.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewEmpMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBProdsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdsMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBProds.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBProdsMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBCobMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBCobMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBCob.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBCobMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBBuscGralMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscGralMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBBuscGral.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBBuscGralMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBVeGranMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBVeGranMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBVeGran.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBVeGranMouseExited

    
    /*Cuando se presiona uan tecla en el panel que muestra la conexión actual*/
    private void jPConKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPConKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jPConKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cantidad*/
    private void jTCantFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCantFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCant.setSelectionStart(0);jTCant.setSelectionEnd(jTCant.getText().length());        
        
    }//GEN-LAST:event_jTCantFocusGained

    
    /*Cuando se tipea una tecla en el campo de la cantidad*/
    private void jTCantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyTyped
        
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTCantKeyTyped

    
    /*Cuando se presiona una tecla en el campo de la cantidad*/
    private void jTCantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCantKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo de moneda*/
    private void jTMonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMonFocusLost
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTMon.getText().compareTo("")!=0)
            jTMon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jTMonFocusLost

    private void jComUnidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComUnidFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComUnid.getSelectedItem().toString().compareTo("")!=0)
        jComUnid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComUnidFocusLost

    
    /*Cuando se presiona una tecla en el combo de las unidades del producto*/
    private void jComUnidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComUnidKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComUnidKeyPressed

    
    /*Cuando la forma se activa*/
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        /*Inicializa la dirección de la forma actual*/
        jFram   = this;
        frmMe   = this;
        
    }//GEN-LAST:event_formWindowActivated

    private void jTCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTCliActionPerformed

    
    
                                
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if F1 entonces muestra la forma de ayuda*/
        else if(evt.getKeyCode() == KeyEvent.VK_F1)
        {
            /*Muestra la forma con la ayuda del sistema de los accesos rápidos*/
            AidPtoVta w = new AidPtoVta();
            w.setVisible(true);            
        }
        /*Else if F2 entonces muestra los mensajes con la función*/
        else if(evt.getKeyCode() == KeyEvent.VK_F2)
            vVeMsj();                        
        /*Else if F3 entonces pon el foco del teclado en el campo del cliente*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jTCli.grabFocus();        
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Else if F4 entonces*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
        {
            /*Si el botón de nueva cliente esta visible entonces pesionalo*/
            if(jBNewEmp.isVisible())
                jBNewEmp.doClick();
        }
        /*Else if F6 entonces*/
        else if(evt.getKeyCode() == KeyEvent.VK_F6)
        {
            /*Guarda el usuario que se va a deslogear*/
            String sUsr = Login.sUsrG;
            
            /*Muestra la forma para deslogearse*/            
            DesLogin l = new DesLogin(jFram);
            l.setVisible(true);
            
            /*Si es el mismo usaurio entonces regresa*/
            if(sUsr.compareTo(Login.sUsrG)==0)
                return;
            
            /*Valida si tiene que hacer el corte X automáticamente*/
            vCortXAut();
        }
        /*Else if F10 entonces presiona el botón de cobrar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F9)                  
            jBCob.doClick();        
        /*Else if se presiono CTRL + B entonces*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_B)
        {
            /*Llama a la forma para búscar productos avanzadamente*/
            ptovta.BuscAvan w = new ptovta.BuscAvan(this, jTProd, null, null, null);
            w.setVisible(true);
            
            /*Coloca el foco del teclado en el campo del producto*/
            jTProd.grabFocus();            
        }   
        /*Else if, fue F11*/
        else if(evt.getKeyCode() == KeyEvent.VK_F11)
        {
            /*Muestra el formulario de las opciones del punto de venta*/
            OptPtoVta p   = new OptPtoVta();
            p.setVisible(true);            
        }
        /*Else if, fue F12 entonce muestra el chat*/
        else if(evt.getKeyCode() == KeyEvent.VK_F12)
            vChatC();       
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona ALT + T entonces abre la forma para tallas y colores*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
        {
            TallColoPto t = new TallColoPto(jTProd.getText(), jTProd, jTAlma, jTTall, jTColo, "ptovta");
            t.setVisible(true);
        }
        /*Si se presiona ALT + S entonces abre la forma para las series del producto*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_S)
        {
            /*Si no a seleccionado un producto entonces*/
            if(jTProd.getText().compareTo("")==0)
            {
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, "Selecciona un producto para ver sus series primeramente.", "Series de Producto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
                return;
            }
            
            /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
            Busc b = new Busc(this, jTProd.getText().trim(), 34, jTSerProd, jTComenSer, null, "", null);
            b.setVisible(true);
        }
        /*Si se presiona ALT + L entonces abre la forma para tallas y colores*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_L)
        {
            /*Abre la forma de lotes y pedimentos*/
            LotPed p = new LotPed(this, jTProd.getText(), jTProd, jTAlma, jTLot, jTPedimen, jTCadu, jTCantLot, jTId);
            p.setVisible(true);
        }
        /*Si se presiona CTRL + SUP entonces presiona el botón de borrar*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBDel.doClick();        
        /*Si se presiona ALT + P entonces muestra la forma de perdida de boleto*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_P)
        {
            PerdBol p = new PerdBol(frmMe, null);
            p.setVisible(true);
        }
                
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jB1;
    private javax.swing.JButton jBBuscGral;
    private javax.swing.JButton jBCli;
    private javax.swing.JButton jBCob;
    private javax.swing.JButton jBDel;
    private javax.swing.JButton jBLed;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBNewEmp;
    private javax.swing.JButton jBNewVta;
    private javax.swing.JButton jBProds;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTecla;
    private javax.swing.JButton jBVeGran;
    private javax.swing.JComboBox jComUnid;
    private javax.swing.JLabel jLAyu;
    private javax.swing.JLabel jLImg;
    private javax.swing.JLabel jLTipVta;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPCon;
    private javax.swing.JPanel jPanImg;
    private javax.swing.JPanel jPanProds;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelLin;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneLin;
    private javax.swing.JScrollPane jScrollProds;
    private javax.swing.JTextField jTAlma;
    private javax.swing.JTextField jTBuscGral;
    private javax.swing.JTextField jTCadu;
    private javax.swing.JTextField jTCant;
    private javax.swing.JTextField jTCantLot;
    private javax.swing.JTextField jTCli;
    private javax.swing.JTextField jTCodProd;
    private javax.swing.JTextField jTColo;
    private javax.swing.JTextField jTColoAut;
    private javax.swing.JTextField jTComenSer;
    private javax.swing.JTextField jTDesc;
    private javax.swing.JTextField jTDirOfi;
    private javax.swing.JTextField jTDirPart;
    private javax.swing.JTextField jTFec;
    private javax.swing.JTextField jTGara;
    private javax.swing.JTextField jTId;
    private javax.swing.JTextField jTImpo;
    private javax.swing.JTextField jTImpue;
    private javax.swing.JTextField jTList;
    private javax.swing.JTextField jTLot;
    private javax.swing.JTextField jTMarc;
    private javax.swing.JTextField jTMod;
    private javax.swing.JTextField jTMon;
    private javax.swing.JTextField jTNom;
    private javax.swing.JTextField jTNomb;
    private javax.swing.JTextField jTNumLic;
    private javax.swing.JTextField jTPedimen;
    private javax.swing.JTextField jTPlacs;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTQtyP;
    private javax.swing.JTextField jTRecib;
    private javax.swing.JTextField jTSer;
    private javax.swing.JTextField jTSerProd;
    private javax.swing.JTextField jTSubTot;
    private javax.swing.JTextField jTTall;
    private javax.swing.JTextField jTTarCirc;
    private javax.swing.JTextField jTTel;
    private javax.swing.JTextField jTTelOfi;
    private javax.swing.JTextField jTTot;
    private javax.swing.JTextField jTTotCost;
    private javax.swing.JTextField jTTotDesc;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class PtoVtaTou extends javax.swing.JFrame */

