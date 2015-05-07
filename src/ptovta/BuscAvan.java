//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;




/*Clase para búscar un producto en una búsqueda avanzada*/
public class BuscAvan extends javax.swing.JFrame
{
    //Contiene los nombres de los nodos padre
    private final String sLinG         = "Línea";
    private final String sTipG         = "Tipo";        
    private final String sFabG         = "Fabricante";
    private final String sMarcG        = "Marca";        
    private final String sModG         = "Modelo";        
    private final String sNumPartG     = "Número de parte";        
    private final String sCompaG       = "Compatibilidad";            
    private final String sMedG         = "Medida";
    private final String sPesG         = "Peso";
    private final String sColoG        = "Color";
    private final String sUnidG        = "Unidad";
    private final String sClasExtG     = "Clasificación extra";
    private final String sImpueG       = "Impuesto";
    private final String sAnaqG        = "Anaquel";
    private final String sLugG         = "Lugar";
    private final String sUbiAdG       = "Ubicación adicional";
    private final String sSerG         = "Serie";
    private final String sClasJeraG    = "Clasificación jerárquica"; 
    private final String sCompMarcModG = "Compatibilidad de marca y modelo"; 
    
    /*Contiene la condición inicial de búsqueda de todos los filtros*/
    private String                  sConLin     = "";    
    private String                  sConMarc    = "";
    private String                  sConFab     = "";
    private String                  sConMed     = "";
    private String                  sConPes     = "";
    private String                  sConColo    = "";
    private String                  sConUnid    = "";
    private String                  sConClas    = "";
    private String                  sConMod     = "";
    private String                  sConImpue   = "";
    private String                  sConPart    = "";
    private String                  sConCompa   = "";
    private String                  sConAlma    = "";
    private String                  sConAnaq    = "";
    private String                  sConLug     = "";
    private String                  sConUbiAd   = "";
    private String                  sConTip     = "";
    private String                  sConSer     = "";
    private String                  sConClasJera= "";
    private String                  sCompMarcMod= "";
    
    /*Contiene la consulta que se hace a la base de datos*/
    private String                  sConsul;
    
    /*Contiene las direcciones del otro forumario*/
    private javax.swing.JTextField  jTProd;    
    private javax.swing.JTextField  jTDescrip;
    private javax.swing.JTextField  jTSerPP;
    private javax.swing.JComboBox   jComAlma;
    
    
    
    
    /*Constructor sin argumentos*/
    public BuscAvan(javax.swing.JFrame jFra, javax.swing.JTextField jTPro, javax.swing.JTextField jTDescri, javax.swing.JComboBox jCom, javax.swing.JTextField jTSe) 
    {   
        /*Inicaliza los componentes gráficos*/
        initComponents();
    
        //Que sea modal la forma
        this.setAlwaysOnTop(true);
        
        //Esconde el control de la serie del producto
        jTSerP.setVisible(false);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Obtiene las direcciones del otro formulario*/
        jTProd      = jTPro;        
        jTDescrip   = jTDescri;
        jTSerPP     = jTSe;
        jComAlma    = jCom;
        
        /*Para que la tabla tenga scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Establece el tamaño de las columnas de la tabla*/
        jTab.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(500);
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBGua);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Busqueda avanzada, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Pon el foco del teclado en el árbol*/
        jTreClas.grabFocus();

        /*Crea el listener para cuando se cambia de selección en la tabla de productos*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {   
                /*Si no hay selección en la fila entonces regresa*/        
                if(jTab.getSelectedRow()==-1)
                    return;                                    
                
                /*Obtiene la imágen si es que tiene*/
                (new Thread()
                {
                    @Override
                    public void run()
                    {
                        /*Carga la imágen en el control*/
                        Star.vGetImg(jTab.getValueAt(jTab.getSelectedRow(), 1).toString(), jLImg);                       
                    }
                    
                }).start();
            }
        });
        
        /*Establece el nodo padre*/
        javax.swing.tree.DefaultTreeModel mod         = (javax.swing.tree.DefaultTreeModel)jTreClas.getModel();
        javax.swing.tree.DefaultMutableTreeNode root  = new javax.swing.tree.DefaultMutableTreeNode("Clasificación");                
        mod.setRoot(root);
        
        /*Establece los nodos hijos*/                
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sLinG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sTipG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sFabG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sMarcG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sModG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sNumPartG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sCompaG));        
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sMedG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sPesG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sColoG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sUnidG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sClasExtG));       
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sImpueG));        
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sAnaqG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sLugG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sUbiAdG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sSerG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sClasJeraG));
        root.add(new javax.swing.tree.DefaultMutableTreeNode(sCompMarcModG));
        
        /*Expande el root*/
        jTreClas.expandRow(0);
                        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene todas las líneas de la base de datos*/
        javax.swing.tree.DefaultMutableTreeNode nod = root.getNextNode();
        try
        {
            sQ = "SELECT cod, descrip FROM lins";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
        
        /*Obtiene todos los tipos de la base de datos*/
        nod = nod.getNextLeaf();                               
        try
        {
            sQ = "SELECT cod, descrip FROM tips";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;            
        }
        
        /*Obtiene todos los fabricantes de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT cod, descrip FROM fabs";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
                
        /*Obtiene todas las marcas de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT cod, descrip FROM marcs";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
                
        /*Obtiene todos los Modelos de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT cod, descrip FROM model GROUP BY cod, descrip";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
                
        /*Obtiene todos los números de parte de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT part FROM prodpart GROUP BY part";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("part")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
        
        /*Obtiene todas las compatibilidades de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT compa, descrip FROM compa LEFT OUTER JOIN prods ON prods.PROD = compa.PROD GROUP BY compa, descrip";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("compa") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
        
        /*Obtiene todas las medidas de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT cod, descrip FROM meds";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
        
        /*Obtiene todos los pesos de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT cod, descrip FROM pes";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
        
        /*Obtiene todos los colores de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT cod, descrip FROM colos";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
        
        /*Obtiene todas las unidades de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT cod, descrip FROM unids";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
        
        /*Obtiene todas las clasificaciones extra de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT cod, descrip FROM clasprod";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
                
        /*Obtiene todos los fabricantes de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT codimpue FROM impues";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("codimpue")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
                
        /*Obtiene todos los anaqueles de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT cod, descrip FROM anaqs";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
        
        /*Obtiene todos los lugares de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT cod, descrip FROM lugs";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
        
        /*Obtiene todas las ubicaciones adiconales de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT cod, descrip FROM ubiad";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("cod") + "<>" + rs.getString("descrip")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }
        
        /*Obtiene todas las series de la base de datos*/
        nod = nod.getNextLeaf();
        try
        {
            sQ = "SELECT ser, prod FROM serieprod";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces inserta el código y la descripción en el árbol*/
            while(rs.next())            
                mod.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(rs.getString("ser") + "<>" + rs.getString("prod")), nod, 0);                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }
        
        //Avanza a la siguiente hoja
        nod = nod.getNextLeaf();
        
        //Guarda la hoja original
        javax.swing.tree.DefaultMutableTreeNode nodOri = nod;
        
        //Obtiene todas las clasificaciones jerárquicas de productos        
        try
        {
            sQ = "SELECT rut FROM clasjeraprod";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces
            while(rs.next())       
            {
                //El nodo va a ser al orginal
                nod = nodOri;
                
                //Tokeniza para quitar la raíz común
                java.util.StringTokenizer stk = new java.util.StringTokenizer(rs.getString("rut"), "|");
                
                //Quita la primera raíz común
                stk.nextToken();                
                
                //Recorre todos los nodos
                String sRut;
                while(stk.hasMoreTokens())
                {    
                    //Obtiene la ruta
                    sRut        = stk.nextToken();
                    
                    /*Cree el path array de los nodos*/
                    javax.swing.tree.TreePath pat = new javax.swing.tree.TreePath(sRut);                
                                        
                    /*Si el nodo que se quiere insertar es igual al nodo acutal entonces continua*/
                    if(pat.getLastPathComponent().toString().compareTo(nod.toString())==0)                                        
                        continue;                    
                    
                    /*Para saber si ya existe ese nodo en el nodo actual*/
                    boolean bSi         = true;
                    
                    /*Si este nodo tiene hijos entonces*/
                    if(nod.getChildCount()>0)
                    {
                        /*Recorre los hijos de este nodo*/
                        for(int x = 0; x < nod.getChildCount(); x++)
                        {
                            /*Si alguno de los hijos es igual al nodo que se quiere insertar entonces*/
                            if(nod.getChildAt(x).toString().compareTo(pat.getLastPathComponent().toString())==0)
                            {
                                /*Avanza al nodo siguiente*/
                                nod        = (javax.swing.tree.DefaultMutableTreeNode)nod.getChildAt(x);
                                
                                /*Coloca la bandera para saber que ya existe y sal del búcle*/
                                bSi = false;
                                break;
                            }
                        }
                    }
                    
                    /*Agrega el nodo y avanza al siguiente hijo*/
                    if(bSi)
                    {
                        nod.add(new javax.swing.tree.DefaultMutableTreeNode(pat.getLastPathComponent()));
                        nod     = nod.getNextNode();
                    }
                    
                }//Fin while(stk.hasMoreTokens())     
                
            }//Fin while(rs.next())
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                                                
        }
        
        //Avanza a la siguiente hoja
        nod =nodOri.getNextLeaf();
        
        //Guarda la hoja original
        nodOri = nod;
        
        //Obtiene todas las clasificaciones jerárquicas de productos        
        try
        {
            sQ = "SELECT rut FROM terProdCompa";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            //Si hay datos entonces
            while(rs.next())       
            {
                //El nodo va a ser al orginal
                nod = nodOri;

                //Tokeniza para quitar la raíz común
                java.util.StringTokenizer stk = new java.util.StringTokenizer(rs.getString("rut"), "|");

                String sRut1        = stk.nextToken();
                String sRut2        = stk.nextToken();
                String sRut3        = stk.nextToken();

                /*Cree el path array de los nodos*/
                javax.swing.tree.TreePath pat1 = new javax.swing.tree.TreePath(sRut1);
                javax.swing.tree.TreePath pat2 = new javax.swing.tree.TreePath(sRut2);
                javax.swing.tree.TreePath pat3 = new javax.swing.tree.TreePath(sRut3);

                //caso base si la hoja esta sola inserta todas
                if(nodOri.getNextNode()==null)
                {
                    nod.add(new javax.swing.tree.DefaultMutableTreeNode(pat1.getLastPathComponent()));
                    nod         = nod.getNextNode();

                    nod.add(new javax.swing.tree.DefaultMutableTreeNode(pat2.getLastPathComponent()));
                    nod         = nod.getNextNode();

                    nod.add(new javax.swing.tree.DefaultMutableTreeNode(pat3.getLastPathComponent()));
                }
                else
                {
                    boolean bSi=true;
                    if(nod.getChildCount()>0)
                    {
                        /*Recorre los hijos de este nodo*/
                        for(int x = 0; x < nod.getChildCount(); x++)
                        {
                            /*Si alguno de los hijos es igual al nodo que se quiere insertar entonces*/
                            if(nod.getChildAt(x).toString().compareTo(pat1.getLastPathComponent().toString())==0)
                            {
                                /*Avanza al nodo siguiente*/
                                nod        = (javax.swing.tree.DefaultMutableTreeNode)nod.getChildAt(x);

                                /*Coloca la bandera para saber que ya existe y sal del búcle*/
                                bSi = false;
                                break;
                            }
                        }
                    }
                    /*Agrega el nodo y avanza al siguiente hijo*/
                    if(bSi)
                    {
                        nod.add(new javax.swing.tree.DefaultMutableTreeNode(pat1.getLastPathComponent()));
                        nod        = (javax.swing.tree.DefaultMutableTreeNode)nod.getChildAt(nod.getChildCount()-1);
                    }

                    bSi=true;
                    if(nod.getChildCount()>0)
                    {
                        /*Recorre los hijos de este nodo*/
                        for(int x = 0; x < nod.getChildCount(); x++)
                        {
                            /*Si alguno de los hijos es igual al nodo que se quiere insertar entonces*/
                            if(nod.getChildAt(x).toString().compareTo(pat2.getLastPathComponent().toString())==0)
                            {
                                /*Avanza al nodo siguiente*/
                                nod        = (javax.swing.tree.DefaultMutableTreeNode)nod.getChildAt(x);

                                /*Coloca la bandera para saber que ya existe y sal del búcle*/
                                bSi = false;
                                break;
                            }
                        }
                    }
                    /*Agrega el nodo y avanza al siguiente hijo*/
                    if(bSi)
                    {
                        nod.add(new javax.swing.tree.DefaultMutableTreeNode(pat2.getLastPathComponent()));
                        nod        = (javax.swing.tree.DefaultMutableTreeNode)nod.getChildAt(nod.getChildCount()-1);
                    }

                    bSi=true;
                    if(nod.getChildCount()>0)
                    {
                        /*Recorre los hijos de este nodo*/
                        for(int x = 0; x < nod.getChildCount(); x++)
                        {
                            /*Si alguno de los hijos es igual al nodo que se quiere insertar entonces*/
                            if(nod.getChildAt(x).toString().compareTo(pat3.getLastPathComponent().toString())==0)
                            {
                                /*Avanza al nodo siguiente*/
                                nod        = (javax.swing.tree.DefaultMutableTreeNode)nod.getChildAt(x);

                                /*Coloca la bandera para saber que ya existe y sal del búcle*/
                                bSi = false;
                                break;
                            }
                        }
                    }

                    /*Agrega el nodo y avanza al siguiente hijo*/
                    if(bSi)
                        nod.add(new javax.swing.tree.DefaultMutableTreeNode(pat3.getLastPathComponent()));

                }//Fin else
                                    
                
            }//Fin while(rs.next())
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

        //Carga los productos
        (new Thread()
        {
            @Override
            public void run()
            {
                vCargarProd();
            }
            
        }).start();
                
        /*Listener para cuando cambia algo en el árbol*/
        jTreClas.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() 
        {
            @Override
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) 
            {   
                /*Objeto nodo seleccionado*/
                javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode)jTreClas.getLastSelectedPathComponent();
                
                /*Si no se a seleccionado nada entonces regresa*/ 
                if(node==null) 
                    return;

                /*Resetea la condición de la línea*/                    
                sConLin         = "";

                /*Resetea la condición de la marca*/                    
                sConMarc        = "";
                
                /*Resetea la condición del fabricante*/                    
                sConFab         = "";
                
                /*Resetea la de la medida*/                    
                sConMed         = "";
                
                /*Resetea la condición del peso*/                    
                sConPes         = "";

                /*Resetea la condición del color*/                    
                sConColo        = "";

                /*Resetea la condición de la unidad*/                    
                sConUnid        = "";

                /*Resetea la condición de la clasificación extra*/                    
                sConClas        = "";

                /*Resetea la condición del Modelo*/                    
                sConMod         = "";
                
                /*Resetea la condición del impuesto*/                    
                sConImpue       = "";

                /*Resetea la condición de los números de parte*/                    
                sConPart        = "";

                /*Resetea la condición de las compatibilidades*/                    
                sConCompa       = "";

                /*Resetea la condición del almacén*/                    
                sConAlma        = "";
                
                /*Resetea la condición del anaquel*/                    
                sConAnaq        = "";

                /*Resetea la condición del lugar*/                    
                sConLug         = "";

                /*Resetea la condición de la ubicación adicional*/                    
                sConUbiAd       = "";

                /*Resetea la condición del tipo*/                    
                sConTip         = "";
                
                /*Resetea la condición de la serie*/
                sConSer         = "";
                
                /*Resetea la condición de la Clasificación jerárquica*/
                sConClasJera    = "";
                
                /*Resetea la condición de la compatibilidad marca modelo*/
                sCompMarcMod     = "";
                
                /*Recorre toda la selección actual*/                    
                javax.swing.tree.TreePath[] paths = jTreClas.getSelectionPaths();               
                for(javax.swing.tree.TreePath pa: paths)
                {                       
                    /*Obtiene el código primario del árbol, si hay excepción solo regresa*/
                    String sCodPri = pa.getPathComponent(2).toString();
                    
                    /*Crea todas las condiciones dependiendo de el root*/
                    if(pa.getPathComponent(1).toString().compareTo(sCompMarcModG)==0)
                    {
                        //Se le pone el primer nodo para la busqueda en clasificaciones
                        String sCodPrim = "";
                        
                        //Se le juntan los siguientes nodos de las clasificaciones
                        for(int i=2;i <= pa.getPathCount()-1;i++)
                            sCodPrim = sCodPrim + "" + pa.getPathComponent(i).toString() + "|";
                        
                        //Cuenta lo pipeline
                        String s = sCodPrim;
                        int counter = 0;
                        for( int i=0; i<s.length(); i++ )
                        {
                            if( s.charAt(i) == '|' )
                                counter++;
                        }
                            
                        //se quita el ultimo pipeline si son 3
                        if(counter==3)
                            sCodPrim = sCodPrim.substring(0, sCodPrim.length()-1);
                        
                        /*Completa la cadena de condición*/
                        sCompMarcMod += "AND rut LIKE '" + sCodPrim + "%'";
                        
                    }
                    /*Crea todas las condiciones dependiendo de el root*/
                    if(pa.getPathComponent(1).toString().compareTo(sClasJeraG)==0)
                    {
                        //Se le pone el primer nodo para la busqueda en clasificaciones
                        String sCodPrim = "Clasificaciones";
                        
                        //Se le juntan los siguientes nodos de las clasificaciones
                        for(int i=2;i <= pa.getPathCount()-1;i++)
                            sCodPrim = sCodPrim + "|" + pa.getPathComponent(i).toString();
                        
                        /*Completa la cadena de condición*/
                        sConClasJera += " AND clasjera = '" + sCodPrim + "'";                                            
                    }
                    
                    /*Crea todas las condiciones dependiendo de el root*/
                    if(pa.getPathComponent(1).toString().compareTo(sLinG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConLin += " AND lin = '" + sCod + "'";                                            
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sMarcG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConMarc += " AND (marc = '" + sCod + "' OR '" + sCod + "' IN(SELECT prod FROM marcprod))";                                                                    
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sTipG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConTip += " AND tip = '" + sCod + "'";                                                                    
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sFabG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConFab += " AND fab = '" + sCod + "'";                                            
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sMedG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConMed += " AND codmed = '" + sCod + "'";                                            
                    }                        
                    else if(pa.getPathComponent(1).toString().compareTo(sPesG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConPes += " AND pes = '" + sCod + "'";                                            
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sColoG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConColo += " AND colo = '" + sCod + "'";                                            
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sUnidG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConUnid += " AND unid = '" + sCod + "'";                                            
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sClasExtG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConClas += " AND codext = '" + sCod + "'";                                            
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sModG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConMod += " AND (mode = '" + sCod + "' OR '" + sCod + "' IN(SELECT prod FROM modelprod))";                                            
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sImpueG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConImpue += " AND impue = '" + sCod + "'";                                            
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sNumPartG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConPart += " AND prod IN(SELECT prod FROM prodpart WHERE part = '" + sCod + "')";                                                                    
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sCompaG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConCompa += " AND prod IN(SELECT prod FROM compa WHERE compa = '" + sCod + "')";                                            
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sAnaqG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConAnaq += " AND anaq = '" + sCod + "'";                                            
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sLugG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConLug += " AND lug = '" + sCod + "'";                                            
                    }
                    else if(pa.getPathComponent(1).toString().compareTo(sUbiAdG)==0)
                    {
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Completa la cadena de condición*/
                        sConUbiAd += " AND codubi = '" + sCod + "'";                                            
                    }                                  
                    else if(pa.getPathComponent(1).toString().compareTo(sSerG)==0)
                    {                        
                        /*Obtiene solamente el código de la marca*/
                        java.util.StringTokenizer stk = new java.util.StringTokenizer(sCodPri, "<>");
                        String sCod = stk.nextToken();
                        
                        /*Guarda la serie del producto*/
                        jTSerP.setText(sCod);
                        
                        /*Completa la cadena de condición*/
                        sConSer     += " AND prod IN(SELECT prod FROM serieprod WHERE ser = '" + sCod + "') ";                                            
                    }
                    
                }//Fin for(javax.swing.tree.TreePath pa: paths)                                                                                                           

                //Carga los productos
                (new Thread()
                {
                    @Override
                    public void run()
                    {
                        vCargarProd();
                    }
                    
                }).start();
            }
        });
        
    }/*Fin de public BuscAvan() */
        
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jBGua = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreClas = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jScrImg = new javax.swing.JScrollPane();
        jLImg = new javax.swing.JLabel();
        jTSerP = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
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
        jBSal.setNextFocusableComponent(jBGua);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 480, 120, 40));

        jBGua.setBackground(new java.awt.Color(255, 255, 255));
        jBGua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGua.setForeground(new java.awt.Color(0, 102, 0));
        jBGua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGua.setText("Guardar");
        jBGua.setToolTipText("Ver Log de Rastreo (Alt+L)");
        jBGua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBGua.setNextFocusableComponent(jTreClas);
        jBGua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBGuaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBGuaMouseExited(evt);
            }
        });
        jBGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuaActionPerformed(evt);
            }
        });
        jBGua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGuaKeyPressed(evt);
            }
        });
        jP1.add(jBGua, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 480, 120, 40));

        jTreClas.setNextFocusableComponent(jTab);
        jTreClas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTreClasKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTreClas);

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 520));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Producto", "Almacén", "Descripción"
            }
        ));
        jTab.setGridColor(new java.awt.Color(255, 255, 255));
        jTab.setNextFocusableComponent(jScrImg);
        jTab.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTab.setShowHorizontalLines(false);
        jTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTabKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTab);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 280, 520));

        jScrImg.setBackground(new java.awt.Color(255, 255, 255));
        jScrImg.setNextFocusableComponent(jBSal);
        jScrImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jScrImgKeyPressed(evt);
            }
        });

        jLImg.setBackground(new java.awt.Color(255, 255, 255));
        jLImg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLImgKeyPressed(evt);
            }
        });
        jScrImg.setViewportView(jLImg);

        jP1.add(jScrImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 330, 480));
        jP1.add(jTSerP, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 490, 10, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        
        /*Llama al recolector de basura y cierra la forma*/
        System.gc();                    
        this.dispose();        
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona una tecla en el botón salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBSalKeyPressed
       
       
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

    
    //Se cargan los productos en la tabla
    private void vCargarProd()
    {
        
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
                
        if(sCompMarcMod.compareTo("")==0)
            /*Crea la consulta*/
            sConsul = "SELECT prod, alma, descrip FROM prods WHERE 1=1 " + sConSer + " " + sConTip + " " + sConLin + " " + sConMarc + " " + sConFab + " " + sConMed + " " + sConPes + " " + sConColo + " " + sConUnid + " " + sConClas + " " + sConMod + " " + sConImpue + " " + sConPart + " " + sConCompa + " " + sConAlma + " " + sConAnaq + " " + sConLug + " " + sConUbiAd +" "+ sConClasJera;                                
        else
            sConsul = "SELECT prods.prod AS prod, alma, descrip FROM terProdCompa LEFT OUTER JOIN prods ON prods.prod=terProdCompa.prod WHERE 1=1 " + sCompMarcMod + " ORDER BY prods.PROD";
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;                
        String      sQ; 

        /*Obtiene las coincidencias de la consulta de la base de datos*/
        int iContFi = 1;
        try
        {
            sQ = sConsul;	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Agrega los datos en la tabla*/                        
                Object nu[]= {iContFi, rs.getString("prod"), rs.getString("alma"), rs.getString("descrip")};        
                dm.addRow(nu);

                /*Aumenta el contador de filas*/
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
        
    }//Fin de private void vCargarProd()
    
    
    /*Cuando se presiona el botón de guardar*/
    private void jBGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuaActionPerformed

        /*Si hay selección entonces*/
        if(jTab.getSelectedRow()!=-1)
        {
            /*Coloca los valores en los controles del formulario llamador*/
            if(jTProd!=null)            
                jTProd.setText(jTab.getValueAt(jTab.getSelectedRow(), 1).toString());                            
            if(jComAlma!=null)
                jComAlma.setSelectedItem(jTab.getValueAt(jTab.getSelectedRow(), 2).toString());
            if(jTDescrip!=null)
                jTDescrip.setText(jTab.getValueAt(jTab.getSelectedRow(), 3).toString());            
            if(jTSerPP!=null)
                jTSerPP.setText(jTSerP.getText().trim());            
        }            
                
        /*Llama al recolector de basura y cierra la forma*/
        System.gc();                    
        this.dispose();        
                
    }//GEN-LAST:event_jBGuaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de log*/
    private void jBGuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuaKeyPressed
    
    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing
   
    
    /*Cuando el mouse entra en el botón específico*/
    private void jBGuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBGua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuaMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered
   
    
    /*Cuando el mouse sale del botón específico*/
    private void jBGuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBGua.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGuaMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBSalMouseExited

              
    /*Cuando se presiona una tecla en el árbol*/
    private void jTreClasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTreClasKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTreClasKeyPressed

    
    /*Cuando se presiona una tecla en la tabla de productos*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed

    
    /*Cuando se presiona una tecla en el control de scroll de imágen*/
    private void jScrImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jScrImgKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jScrImgKeyPressed

    
    /*Cuando se presiona una tecla en el label de la imágen*/
    private void jLImgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLImgKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jLImgKeyPressed
          
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Else if se presiona Alt + G presiona el botón de ver log de correos*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGua.doClick();       
        /*Else if se presiona ENTER entonces presiona el botón de aceptar*/
        else if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            jBGua.doClick();       
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBGua;
    private javax.swing.JButton jBSal;
    private javax.swing.JLabel jLImg;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrImg;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTSerP;
    private javax.swing.JTable jTab;
    private javax.swing.JTree jTreClas;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
