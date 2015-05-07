//Paquete
package ptovta;

//Importaciones
import java.awt.Cursor;
import java.awt.Color;
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;




/*Activos fijos*/
public class ActFij extends javax.swing.JFrame 
{
    /*Variable que contiene el borde actual*/
    private Border          bBordOri;
    
    /*Declara variables gloabales originales*/
    private String          sCodOriG;
    private String          sDescripOriG;
    private String          sSerOriG;
    
    /*Declara contadores globales*/
    private int             iContCellEd;
    private int             iContFi;
        
    /*Para controlar si seleccionar todo o deseleccionarlo de la tabla*/
    private boolean          bSel;

    
    
    
    /*Constructor sin argumentos*/
    public ActFij() 
    {                
        /*Inicaliza los componentes gráficos*/
        initComponents();
        
        /*Listener para el combobox de lugares 1*/
        jComLug.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {                
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Carga todos los lugares*/
                vCargLug(con, jComLug);
                
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
        
        /*Listener para el combobox de lugares 2*/
        jComLug2.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;

                /*Carga todos los lugares*/
                vCargLug(con, jComLug2);
                
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
        
        /*Esconde la columna del ID*/
        jTab.getColumnModel().getColumn(4).setMinWidth(0);
        jTab.getColumnModel().getColumn(4).setMaxWidth(0);
        
        /*Crea el listener para cuando se cambia de selección en la tabla*/
        jTab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent lse) 
            {                
                /*Carga los datos de ese activo seleccionado*/
                vCargActSel();               
            }
        });
        
        /*Para que la tabla tengan scroll horisontal*/
        jTab.setAutoResizeMode(0);
        
        /*Activa en el control de comentarios que se usen normamente las teclas de tabulador*/
        jTAComen.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAComen.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        jTAComen2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTAComen2.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
        
        /*Establece el campo de fecha para que solo se pueda modificar con el botón*/
        jDFAdq.getDateEditor().setEnabled       (false);
        jDFIniDep.getDateEditor().setEnabled    (false);
        jDFFinDep.getDateEditor().setEnabled    (false);
        jDtFBaj.getDateEditor().setEnabled      (false);
        
        /*Establece la fecha del día de hoy para los controles de fechas*/
        Date f = new Date();
        jDFAdq.setDate(f);
        jDFIniDep.setDate(f);
        jDFFinDep.setDate(f);
        jDtFBaj.setDate(f);
        
        /*Listener para el combobox de almacenes*/
        jComAlma.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                //Carga todos los almacenes en el combo
                if(Star.iCargAlmaCom(con, jComAlma)==-1)
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
        
        /*Listener para el combobox de almacenes 2*/
        jComAlma2.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                //Carga todos los almacenes en el combo
                if(Star.iCargAlmaCom(con, jComAlma2)==-1)
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
        
        /*Listener para el combobox de conceptos*/
        jComConcep.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {               
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                /*Borra los items en el combobox*/
                jComConcep.removeAllItems();
                
                /*Agrega el concepto vacio en el combo de los almacenes*/
                jComConcep.addItem("");

                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 

                /*Obtiene todas los conceptos actualizados y cargalos en el combobox*/
                try
                {
                    sQ = "SELECT concep FROM actfijcat";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces cargalos en el combobox*/
                    while(rs.next())
                        jComConcep.addItem(rs.getString("concep"));                    
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }  

                //Cierra la base de datos
                Star.iCierrBas(con);
                
            }/*Fin de public void popupMenuWillBecomeVisible(PopupMenuEvent pme) */

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Listener para el combobox de conceptos 2*/
        jComConcep2.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {   
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                /*Borra los items en el combobox*/
                jComConcep2.removeAllItems();
                
                /*Agrega el concepto vacio en el combo de los almacenes*/
                jComConcep2.addItem("");
             
                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 

                /*Obtiene todas los conceptos actualizados y cargalos en el combobox*/
                try
                {
                    sQ = "SELECT concep FROM actfijcat";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces cargalos en el combobox*/
                    while(rs.next())
                        jComConcep2.addItem(rs.getString("concep"));                    
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }  

                //Cierra la base de datos
                Star.iCierrBas(con);
                
            }/*Fin de public void popupMenuWillBecomeVisible(PopupMenuEvent pme) */

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Listener para el combobox de tipos de activos*/
        jComAct.addPopupMenuListener(new PopupMenuListener()
        {            
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) 
            {                
                //Abre la base de datos                             
                Connection  con = Star.conAbrBas(true, false);

                //Si hubo error entonces regresa
                if(con==null)
                    return;
                
                /*Borra los items en el combobox*/
                jComAct.removeAllItems();
                
                /*Agrega el elemento vacio en el combo de los almacenes*/
                jComAct.addItem("");
                
                //Declara variables de la base de datos
                Statement   st;
                ResultSet   rs;                
                String      sQ; 

                /*Obtiene todas los tipos de activos actualizados y cargalos en el combobox*/
                try
                {
                    sQ = "SELECT tip FROM tipactfij";                        
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces cargalos en el combobox*/
                    while(rs.next())
                        jComAct.addItem(rs.getString("tip"));                    
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;
                }  

                //Cierra la base de datos
                Star.iCierrBas(con);
                
            }/*Fin de public void popupMenuWillBecomeVisible(PopupMenuEvent pme) */

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) 
            {                
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) 
            {                
            }
        });
        
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Para que no se muevan las columnas*/
        jTab.getTableHeader().setReorderingAllowed(false);
        
        /*Esconde el link de ayuda*/
        jLAyu.setVisible(false);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);
        
        /*Establece el titulo de la ventana con El usuario, la fecha y hora*/                
        this.setTitle("Activos fijos, Usuario: <" + Login.sUsrG + "> " + Login.sFLog);        

        /*Inicialmente esta deseleccionada la tabla*/
        bSel        = false;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Inicializa el contador de filas en 1*/
        iContFi      = 1;
        
        /*Para que la tabla este ordenada al mostrarce y al dar clic en el nombre de la columna*/
        TableRowSorter trs = new TableRowSorter<>((DefaultTableModel)jTab.getModel());
        jTab.setRowSorter(trs);
        trs.setSortsOnUpdates(true);
        
        /*Pon el foco del teclado en el campo del producto*/
        jTProd.grabFocus();
        
        /*Establece el tamaño de las columnas de la tabla de unidades*/        
        jTab.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(700);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(250);
        
        /*Activa en la tabla que se usen normamente las teclas de tabulador*/
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        jTab.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);                
        
        /*Incializa el contador del cell editor*/
        iContCellEd = 1;
        
        /*Obtiene todos los activos de la base de datos y cargalos en la tabla*/
        vCargAct();
            
        /*Crea el listener para la tabla, para cuando se modifique una celda*/
        PropertyChangeListener pro = new PropertyChangeListener() 
        {
            @Override
            public void propertyChange(PropertyChangeEvent event) 
            {
                /*Si no hay selección entonces que regresa*/
                if(jTab.getSelectedRow()==-1)
                    return;

                /*Obtén la propiedad que a sucedio en el control*/
                String pro = event.getPropertyName();                                
                                
                /*Si el evento fue por entrar en una celda del tabla*/
                if("tableCellEditor".equals(pro)) 
                {
                    /*Si el contador de cell editor está en 1 entonces que lea el valor original que estaba ya*/
                    if(iContCellEd==1)
                    {                                               
                        /*Obtén algunos datos originales de la fila*/                        
                        sCodOriG            = jTab.getValueAt(jTab.getSelectedRow(), 1).toString();                                                                                  
                        sDescripOriG        = jTab.getValueAt(jTab.getSelectedRow(), 2).toString();
                        sSerOriG            = jTab.getValueAt(jTab.getSelectedRow(), 3).toString();
                        
                        /*Aumenta en uno el contador de celleditor*/
                        ++iContCellEd;
                    }
                    /*Else, el contador de cell editor es 2, osea que va de salida*/
                    else
                    {
                        /*Reinicia el conteo*/
                        iContCellEd         = 1;
                        
                        /*Coloca los valores origianales*/
                        jTab.setValueAt(sCodOriG,       jTab.getSelectedRow(), 1);
                        jTab.setValueAt(sDescripOriG,   jTab.getSelectedRow(), 2);
                        jTab.setValueAt(sSerOriG,       jTab.getSelectedRow(), 3);                                                                
                    }
                    
                }/*Fin de /*Fin de if("tableCellEditor".equals(property)) */
            
            }/*Fin de public void propertyChange(PropertyChangeEvent event) */            
        };
        
        /*Establece el listener para la tabla de pes*/
        jTab.addPropertyChangeListener(pro);
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;

        /*Carga los lugares en los dos combos*/
        vCargLug(con, jComLug);
        vCargLug(con, jComLug2);
        
        //Carga todos los almacenes en el combo
        if(Star.iCargAlmaCom(con, jComAlma)==-1)
            return;

        //Carga todos los almacenes en el combo
        if(Star.iCargAlmaCom(con, jComAlma2)==-1)
            return;
                
        /*Agrega el concepto vacio*/
        jComConcep.addItem("");
        
        /*Selecciona el elemento vacio en el combo de los conceptos*/
        jComConcep.setSelectedItem("");

        //Declara variables de la base de datos    
        Statement   st;
        ResultSet   rs;        
        String      sQ;
        
        /*Trae todos los códigos de las conceptos de la base de datos*/        
        try
        {                  
            sQ = "SELECT concep FROM actfijcat";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combo*/
            while(rs.next())
                jComConcep.addItem(rs.getString("concep"));                                                                                                         
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Agrega el concepto vacio*/
        jComConcep2.addItem("");
        
        /*Selecciona el elemento vacio en el combo de los conceptos 2*/
        jComConcep2.setSelectedItem("");
                        
        /*Trae todos los códigos de las conceptos de la base de datos*/        
        try
        {                  
            sQ = "SELECT concep FROM actfijcat";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combo*/
            while(rs.next())
                jComConcep2.addItem(rs.getString("concep"));                                                                                                         
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Agrega el tipo vacio*/
        jComAct.addItem("");
        
        /*Selecciona el elemento vacio en el combo de los tipos*/
        jComAct.setSelectedItem("");
                        
        /*Trae todos los códigos de las conceptos de la base de datos*/        
        try
        {                  
            sQ = "SELECT tip FROM tipactfij";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el combo*/
            while(rs.next())
                jComAct.addItem(rs.getString("tip"));                                                                                                         
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de public ActFij() */
        
    
    /*Carga todos los lugares en el combo*/        
    private void vCargLug(Connection con, javax.swing.JComboBox jCom)
    {
        /*Borra los items en el combobox*/
        jCom.removeAllItems();
        
        /*Agrega cadena vacia*/
        jCom.addItem("");
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ;                 
        
        /*Obtiene los lugares de la base de datos*/        
        try
        {
            sQ  = "SELECT * FROM lugs";
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces cargalos en el control*/
            while(rs.next())
                jCom.addItem(rs.getString("COD"));
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                            
        }    
        
    }/*Fin de private void vCargLug(Connection con, javax.swing.jCombobox jCom)*/
    
    
    /*Carga los datos de ese activo seleccionado*/
    private void vCargActSel()
    {
        /*Si no hay selección en la tabla entonces regresa*/
        if(jTab.getSelectedRow()==-1)
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
        
        /*Obtiene los datos de la fila seleccionada*/
        try
        {
            sQ = "SELECT lug, DATE(ffindep) AS ffindep, TIMESTAMPDIFF(MONTH, finidep, ffindep) AS difmesrea, porcendedu, ctagast, ctadedu, ctadepre, CASE WHEN DATE(NOW()) >= ffindep THEN 'COMPLETO' WHEN TIMESTAMPDIFF(MONTH, finidep, NOW()) <= (TIMESTAMPDIFF(MONTH, finidep, ffindep) / 2) THEN 'NUEVO' WHEN TIMESTAMPDIFF(MONTH, finidep, NOW()) > (TIMESTAMPDIFF(MONTH, finidep, ffindep) / 2) THEN 'PARCIAL' END AS estadsis, TIMESTAMPDIFF(MONTH, finidep, NOW()) AS dif, DATE(fbaj) AS fbaj, comen, cost, baj, estadusr, prod, descrip, serprod, tipact, porcendep, porcendedu, DATE(fadquisusr) AS fadquisusr, DATE(finidep) AS finidep, DATE(ffindep) AS ffindep, DATE(fbajdep) AS fbajdep FROM actfij WHERE id_id = " + jTab.getValueAt(jTab.getSelectedRow(), 4);
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            while(rs.next())
            {
                /*Obtiene la diferencia de meses*/
                String sDif     = rs.getString("dif");
                
                /*Obtiene el porcentaje de la depreciación mensual y deducible*/
                String sPorcenDep   = rs.getString("porcendep");
                String sPorcenDedu  = rs.getString("porcendedu");
                
                /*Coloca los valores en sus controles*/
                jTProd2.setText     (rs.getString("prod"));
                jTDescrip2.setText  (rs.getString("descrip"));
                jTSer2.setText      (rs.getString("serprod"));
                jComLug2.setSelectedItem(rs.getString("lug"));
                jTAct2.setText      (rs.getString("tipact"));
                jTDep2.setText      (sPorcenDep);
                jTFAdq2.setText     (rs.getString("fadquisusr"));
                jTFIniDep2.setText  (rs.getString("finidep"));
                jTFinDep.setText    (rs.getString("ffindep"));                
                jTCtaGast2.setText  (rs.getString("ctagast"));
                jTCtaDedu2.setText  (rs.getString("ctadedu"));
                jTCtaDep2.setText   (rs.getString("ctadepre"));
                jTMesDep.setText    (sDif);
                jTTipAct2.setText   (rs.getString("tipact"));
                jTMese.setText      (rs.getString("difmesrea"));
                jTDedu2.setText     (sPorcenDedu);
                jTDepSis2.setText   (rs.getString("estadsis"));                                
                jComConcep2.setSelectedItem(rs.getString("estadusr"));
                jTAComen2.setText   (rs.getString("comen"));
                
                /*Determina si esta activo o no*/
                String sAct     = "Si";
                if(rs.getString("baj").compareTo("1")==0)
                    sAct        = "No";
                
                /*Coloca si esta activo o no*/
                jTAct2.setText(sAct);
                
                /*Si ya esta dado de baja entonces muestra la fecha de baja*/
                if(sAct.compareTo("No")==0)
                    jTFBajDep2.setText(rs.getString("fbaj"));
                
                /*Dale formato de moneda al costo*/               
                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
                double dCant    = Double.parseDouble(rs.getString("cost"));                                
                
                /*Coloca el costo en su lugar*/
                jTCost2.setText(n.format(dCant));
                                
                /*Obtiene la depreciación mensual*/
                String sDepMens = Double.toString(((Double.parseDouble(sPorcenDep)/12)/100)* Double.parseDouble(rs.getString("cost")));
                
                /*Obtiene el deducible mensual*/
                String sDeduMens= Double.toString(Double.parseDouble(sDepMens)*(Double.parseDouble(sPorcenDedu)/100));                                
                
                /*Convierte al cantidad a double*/                               
                dCant                   = Double.parseDouble(sDepMens);                                
                
                /*Coloca la depresiasión mensual en su lugar*/
                jTDepMens.setText(n.format(dCant));
                
                /*Convierte a double el deducible mensual*/                               
                dCant           = Double.parseDouble(sDeduMens);                                
                
                /*Coloca el deducible mensual en su lugar*/
                jTDeduMens.setText(n.format(dCant));
                
                /*Obtiene el valor actual de la depresiasión mensual*/
                String sValActDepMens   =Double.toString(Double.parseDouble(rs.getString("cost")) - (Double.parseDouble(sDif) * Double.parseDouble(sDepMens)));
                
                /*Obtiene el valor actual del deducible mensual*/
                String sValActDeduMens  =Double.toString(Double.parseDouble(rs.getString("cost")) - (Double.parseDouble(sDif) * Double.parseDouble(sDeduMens)));
                
                /*Obtiene el valor actual dependiendo si va a tomar el deducible o la depreciación*/
                String sValActua;
                if(Double.parseDouble(sPorcenDedu)>0)                    
                    sValActua   =   sValActDeduMens;
                else
                    sValActua   =   sValActDepMens;
                
                /*Si el estado ya es completo entonces el valor actual será 0 por que ya no se puede depreciar mas*/
                if(rs.getString("estadsis").compareTo("COMPLETO")==0)                
                    sValActua   = "0";
                
                /*Dale formato de moneda al valor actual*/                               
                dCant           = Double.parseDouble(sValActua);                                
                
                /*Coloca el valor actual en su lugar*/
                jTValAct.setText(n.format(dCant));                                
                
                /*Obtiene el acumulado mensual de la depreciación*/
                String sAcumMensDep = Double.toString(Double.parseDouble(sDif) * Double.parseDouble(sDepMens));
                
                /*Obtiene el acumulado mensual de la deducción*/
                String sAcumMensDedu= Double.toString(Double.parseDouble(sDif) * Double.parseDouble(sDeduMens));
                
                /*Obtiene el acumulado de la depresiasión dependiendo si va a tomar el deducible o la depreciación*/
                String sTotDep;
                if(Double.parseDouble(sPorcenDedu)>0)                    
                    sTotDep     =   sAcumMensDedu;
                else
                    sTotDep     =   sAcumMensDep;
                                
                /*Si el estado ya es completo entonces será 0 por que ya no se puede depreciar mas*/
                if(rs.getString("estadsis").compareTo("COMPLETO")==0)                
                    sTotDep     = rs.getString("cost");
                        
                /*Dale formato de moneda al total de depreiciasión*/                               
                dCant           = Double.parseDouble(sTotDep);                                
                
                /*Coloca el acumulado de la depreciación en su lugar*/
                jTTotDep2.setText(n.format(dCant));
                
                /*Coloca algunos carets en la posición 0*/
                jTProd2.setCaretPosition        (0);
                jTDescrip2.setCaretPosition     (0);
                jTSer2.setCaretPosition         (0);
                jTAct2.setCaretPosition         (0);
                jTDep2.setCaretPosition         (0);
                jTFAdq2.setCaretPosition        (0);
                jTFIniDep2.setCaretPosition     (0);
                jTMese.setCaretPosition         (0);
                jTTipAct2.setCaretPosition      (0);
                jTFBajDep2.setCaretPosition     (0);               
                jTAComen2.setCaretPosition      (0);                                
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
        
    }/*Fin de private void vCargActSel()*/
                
                
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComLug = new javax.swing.JComboBox();
        jP1 = new javax.swing.JPanel();
        jBSal = new javax.swing.JButton();
        jBNew = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        jBBusc = new javax.swing.JButton();
        jTBusc = new javax.swing.JTextField();
        jBMosT = new javax.swing.JButton();
        jBTab1 = new javax.swing.JButton();
        jLAyu = new javax.swing.JLabel();
        jBTod = new javax.swing.JButton();
        jComConcep = new javax.swing.JComboBox();
        jTEstad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComAct = new javax.swing.JComboBox();
        jTAct = new javax.swing.JTextField();
        jTProd = new javax.swing.JTextField();
        jBProd = new javax.swing.JButton();
        jTDescrip = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jBGranDescrip = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTPorDepre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jDFAdq = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jDFIniDep = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTAComen = new javax.swing.JTextArea();
        jComAlma = new javax.swing.JComboBox();
        jTDescripAlma = new javax.swing.JTextField();
        jBExisAlma = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jDFFinDep = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jTSer = new javax.swing.JTextField();
        jTCost = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTDescrip2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTSer2 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTDep2 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTFAdq2 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTMese = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTFIniDep2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTFBajDep2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTDepSis2 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTTipAct2 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTAComen2 = new javax.swing.JTextArea();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTAct2 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTTotDep2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jTDepMens = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTProd2 = new javax.swing.JTextField();
        jBGranDescrip2 = new javax.swing.JButton();
        jComConcep2 = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        jTCost2 = new javax.swing.JTextField();
        jBGua = new javax.swing.JButton();
        jBBaj = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jTDedu = new javax.swing.JTextField();
        jTCtaDep = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jTCtaGast = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTCtaDedu = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTCtaDep2 = new javax.swing.JTextField();
        jTCtaGast2 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTCtaDedu2 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jTDedu2 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTDeduMens = new javax.swing.JTextField();
        jTValAct = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jTMesDep = new javax.swing.JTextField();
        jTFinDep = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jComAlma2 = new javax.swing.JComboBox();
        jBTrans = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jDtFBaj = new com.toedter.calendar.JDateChooser();
        jTLug = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jComLug2 = new javax.swing.JComboBox();
        jTLug2 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();

        jComLug.setNextFocusableComponent(jTLug);
        jComLug.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComLugFocusLost(evt);
            }
        });
        jComLug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComLugActionPerformed(evt);
            }
        });
        jComLug.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComLugKeyPressed(evt);
            }
        });

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
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setName(""); // NOI18N
        jBSal.setNextFocusableComponent(jTProd2);
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
        jP1.add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 520, 60, 30));

        jBNew.setBackground(new java.awt.Color(255, 255, 255));
        jBNew.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBNew.setForeground(new java.awt.Color(0, 102, 0));
        jBNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/agre.png"))); // NOI18N
        jBNew.setText("Nuevo");
        jBNew.setToolTipText("Nuevo Peso (Ctrl+N)");
        jBNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBNew.setNextFocusableComponent(jTAComen);
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
        jP1.add(jBNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 410, 90, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Estado concepto:");
        jP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 140, -1));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Código", "Descripción", "Serie", "ID_ID"
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

        jP1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 910, 210));

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
        jP1.add(jBBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 140, 20));

        jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTBusc.setNextFocusableComponent(jBMosT);
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
        jP1.add(jTBusc, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 240, 630, 20));

        jBMosT.setBackground(new java.awt.Color(255, 255, 255));
        jBMosT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBMosT.setForeground(new java.awt.Color(0, 102, 0));
        jBMosT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/mostt.png"))); // NOI18N
        jBMosT.setText("Mostrar F4");
        jBMosT.setToolTipText("Mostrar Nuevamente todos los Registros");
        jBMosT.setName(""); // NOI18N
        jBMosT.setNextFocusableComponent(jComConcep2);
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
        jP1.add(jBMosT, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 240, 140, 20));

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
        jP1.add(jBTab1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, 10, 20));

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
        jP1.add(jLAyu, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 530, 120, 30));

        jBTod.setBackground(new java.awt.Color(255, 255, 255));
        jBTod.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jBTod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/marct.png"))); // NOI18N
        jBTod.setText("Marcar Todo");
        jBTod.setToolTipText("Marcar Todos los Registros de la Tabla (Alt+T)");
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
        jP1.add(jBTod, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 12, 130, 18));

        jComConcep.setNextFocusableComponent(jTEstad);
        jComConcep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComConcepActionPerformed(evt);
            }
        });
        jComConcep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComConcepKeyPressed(evt);
            }
        });
        jP1.add(jComConcep, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 150, 20));

        jTEstad.setEditable(false);
        jTEstad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTEstad.setNextFocusableComponent(jTCost);
        jTEstad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTEstadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTEstadFocusLost(evt);
            }
        });
        jTEstad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTEstadKeyPressed(evt);
            }
        });
        jP1.add(jTEstad, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 140, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Descripción:");
        jP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 290, 140, -1));

        jComAct.setNextFocusableComponent(jTAct);
        jComAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComActActionPerformed(evt);
            }
        });
        jComAct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComActKeyPressed(evt);
            }
        });
        jP1.add(jComAct, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 150, 20));

        jTAct.setEditable(false);
        jTAct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAct.setNextFocusableComponent(jComConcep);
        jTAct.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTActFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTActFocusLost(evt);
            }
        });
        jTAct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTActKeyPressed(evt);
            }
        });
        jP1.add(jTAct, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 140, 20));

        jTProd.setBackground(new java.awt.Color(255, 255, 153));
        jTProd.setToolTipText("Ctrl+B búsqueda avanzada");
        jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProd.setNextFocusableComponent(jBProd);
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
        jP1.add(jTProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 150, 20));

        jBProd.setBackground(new java.awt.Color(255, 255, 255));
        jBProd.setText("...");
        jBProd.setToolTipText("Buscar Producto(s)");
        jBProd.setNextFocusableComponent(jTDescrip);
        jBProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBProdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBProdMouseExited(evt);
            }
        });
        jBProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProdActionPerformed(evt);
            }
        });
        jBProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBProdKeyPressed(evt);
            }
        });
        jP1.add(jBProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 30, 20));

        jTDescrip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescrip.setNextFocusableComponent(jTSer);
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
        jP1.add(jTDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 140, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Tipo de activo:");
        jP1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 140, -1));

        jBGranDescrip.setBackground(new java.awt.Color(0, 153, 153));
        jBGranDescrip.setToolTipText("Ver/Modificar Descripción en Grande");
        jBGranDescrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGranDescripActionPerformed(evt);
            }
        });
        jBGranDescrip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGranDescripKeyPressed(evt);
            }
        });
        jP1.add(jBGranDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 10, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Cta.Depreciación:");
        jP1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 180, -1));

        jTPorDepre.setText("0.0");
        jTPorDepre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTPorDepre.setNextFocusableComponent(jTDedu);
        jTPorDepre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPorDepreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPorDepreFocusLost(evt);
            }
        });
        jTPorDepre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPorDepreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTPorDepreKeyTyped(evt);
            }
        });
        jP1.add(jTPorDepre, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 140, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Fecha adquisición:");
        jP1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 160, -1));

        jDFAdq.setNextFocusableComponent(jDFIniDep);
        jDFAdq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFAdqKeyPressed(evt);
            }
        });
        jP1.add(jDFAdq, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 140, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Comentarios:");
        jP1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 190, -1));

        jDFIniDep.setNextFocusableComponent(jDFFinDep);
        jDFIniDep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFIniDepKeyPressed(evt);
            }
        });
        jP1.add(jDFIniDep, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 140, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Inicio depreciación:");
        jP1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 160, -1));

        jTAComen.setColumns(20);
        jTAComen.setLineWrap(true);
        jTAComen.setRows(5);
        jTAComen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAComen.setNextFocusableComponent(jTab);
        jTAComen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAComenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAComenFocusLost(evt);
            }
        });
        jTAComen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAComenKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTAComen);

        jP1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 320, 110));

        jComAlma.setNextFocusableComponent(jTDescripAlma);
        jComAlma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComAlmaFocusLost(evt);
            }
        });
        jComAlma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComAlmaActionPerformed(evt);
            }
        });
        jComAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComAlmaKeyPressed(evt);
            }
        });
        jP1.add(jComAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 150, 20));

        jTDescripAlma.setEditable(false);
        jTDescripAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescripAlma.setNextFocusableComponent(jComAct);
        jTDescripAlma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescripAlmaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescripAlmaFocusLost(evt);
            }
        });
        jTDescripAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescripAlmaKeyPressed(evt);
            }
        });
        jP1.add(jTDescripAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 140, 20));

        jBExisAlma.setBackground(new java.awt.Color(0, 153, 153));
        jBExisAlma.setToolTipText("Existencias por almacén del producto");
        jBExisAlma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExisAlmaActionPerformed(evt);
            }
        });
        jBExisAlma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBExisAlmaKeyPressed(evt);
            }
        });
        jP1.add(jBExisAlma, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 10, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Serie producto:");
        jP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 140, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Fin depreciación:");
        jP1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 160, -1));

        jDFFinDep.setNextFocusableComponent(jTCtaDep);
        jDFFinDep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDFFinDepKeyPressed(evt);
            }
        });
        jP1.add(jDFFinDep, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 140, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Producto:");
        jP1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, -1));

        jTSer.setEditable(false);
        jTSer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSer.setNextFocusableComponent(jComAlma);
        jTSer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSerFocusLost(evt);
            }
        });
        jTSer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSerKeyPressed(evt);
            }
        });
        jP1.add(jTSer, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 140, 20));

        jTCost.setText("$0.00");
        jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCost.setNextFocusableComponent(jTPorDepre);
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTCostKeyTyped(evt);
            }
        });
        jP1.add(jTCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 140, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Costo:");
        jP1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 140, -1));

        jTDescrip2.setEditable(false);
        jTDescrip2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDescrip2.setNextFocusableComponent(jTSer2);
        jTDescrip2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDescrip2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDescrip2FocusLost(evt);
            }
        });
        jTDescrip2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDescrip2KeyPressed(evt);
            }
        });
        jP1.add(jTDescrip2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 290, 110, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Almacén:");
        jP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 140, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Serie:");
        jP1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 310, 140, -1));

        jTSer2.setEditable(false);
        jTSer2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTSer2.setNextFocusableComponent(jTTipAct2);
        jTSer2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTSer2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTSer2FocusLost(evt);
            }
        });
        jTSer2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTSer2KeyPressed(evt);
            }
        });
        jP1.add(jTSer2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 310, 120, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("% Depreciación:");
        jP1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 490, 140, -1));

        jTDep2.setEditable(false);
        jTDep2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDep2.setNextFocusableComponent(jTDedu2);
        jTDep2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDep2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDep2FocusLost(evt);
            }
        });
        jTDep2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDep2KeyPressed(evt);
            }
        });
        jP1.add(jTDep2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 490, 120, 20));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Fecha adquisición:");
        jP1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 410, 140, -1));

        jTFAdq2.setEditable(false);
        jTFAdq2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFAdq2.setNextFocusableComponent(jTFIniDep2);
        jTFAdq2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFAdq2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFAdq2FocusLost(evt);
            }
        });
        jTFAdq2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFAdq2KeyPressed(evt);
            }
        });
        jP1.add(jTFAdq2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 410, 120, 20));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Fin depreciación:");
        jP1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 450, 140, -1));

        jTMese.setEditable(false);
        jTMese.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMese.setNextFocusableComponent(jTDep2);
        jTMese.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMeseFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMeseFocusLost(evt);
            }
        });
        jTMese.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMeseKeyPressed(evt);
            }
        });
        jP1.add(jTMese, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 470, 120, 20));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Inicio depreciación:");
        jP1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 430, 140, -1));

        jTFIniDep2.setEditable(false);
        jTFIniDep2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFIniDep2.setNextFocusableComponent(jTFinDep);
        jTFIniDep2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFIniDep2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFIniDep2FocusLost(evt);
            }
        });
        jTFIniDep2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFIniDep2KeyPressed(evt);
            }
        });
        jP1.add(jTFIniDep2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 430, 120, 20));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Fecha baja:");
        jP1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 350, 140, -1));

        jTFBajDep2.setEditable(false);
        jTFBajDep2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFBajDep2.setNextFocusableComponent(jTDepSis2);
        jTFBajDep2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFBajDep2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFBajDep2FocusLost(evt);
            }
        });
        jTFBajDep2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFBajDep2KeyPressed(evt);
            }
        });
        jP1.add(jTFBajDep2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 350, 120, 20));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Concepto usuario:");
        jP1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 270, 170, -1));

        jTDepSis2.setEditable(false);
        jTDepSis2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDepSis2.setNextFocusableComponent(jTAct2);
        jTDepSis2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDepSis2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDepSis2FocusLost(evt);
            }
        });
        jTDepSis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDepSis2KeyPressed(evt);
            }
        });
        jP1.add(jTDepSis2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 370, 120, 20));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Comentarios:");
        jP1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 140, -1));

        jTTipAct2.setEditable(false);
        jTTipAct2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTipAct2.setNextFocusableComponent(jTFBajDep2);
        jTTipAct2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTipAct2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTipAct2FocusLost(evt);
            }
        });
        jTTipAct2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTipAct2KeyPressed(evt);
            }
        });
        jP1.add(jTTipAct2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 330, 120, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Tipo activo:");
        jP1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 330, 140, -1));

        jTAComen2.setColumns(20);
        jTAComen2.setLineWrap(true);
        jTAComen2.setRows(5);
        jTAComen2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAComen2.setNextFocusableComponent(jComAlma2);
        jTAComen2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAComen2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAComen2FocusLost(evt);
            }
        });
        jTAComen2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAComen2KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTAComen2);

        jP1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 440, 320, 80));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Depreciasión:");
        jP1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 370, 140, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Activo:");
        jP1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 390, 140, -1));

        jTAct2.setEditable(false);
        jTAct2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTAct2.setNextFocusableComponent(jTFAdq2);
        jTAct2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTAct2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTAct2FocusLost(evt);
            }
        });
        jTAct2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAct2KeyPressed(evt);
            }
        });
        jP1.add(jTAct2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 390, 120, 20));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Deducible mensual:");
        jP1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 290, 140, 20));

        jTTotDep2.setEditable(false);
        jTTotDep2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTTotDep2.setNextFocusableComponent(jTValAct);
        jTTotDep2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTTotDep2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTTotDep2FocusLost(evt);
            }
        });
        jTTotDep2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTTotDep2KeyPressed(evt);
            }
        });
        jP1.add(jTTotDep2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 310, 120, 20));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Depreciación mensual:");
        jP1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 270, 170, 20));

        jTDepMens.setEditable(false);
        jTDepMens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDepMens.setNextFocusableComponent(jTDeduMens);
        jTDepMens.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDepMensFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDepMensFocusLost(evt);
            }
        });
        jTDepMens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDepMensKeyPressed(evt);
            }
        });
        jP1.add(jTDepMens, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 270, 120, 20));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Producto:");
        jP1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 270, 140, -1));

        jTProd2.setEditable(false);
        jTProd2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTProd2.setNextFocusableComponent(jTDescrip2);
        jTProd2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTProd2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProd2FocusLost(evt);
            }
        });
        jTProd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProd2KeyPressed(evt);
            }
        });
        jP1.add(jTProd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 270, 120, 20));

        jBGranDescrip2.setBackground(new java.awt.Color(0, 153, 153));
        jBGranDescrip2.setToolTipText("Ver/Modificar Descripción en Grande");
        jBGranDescrip2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGranDescrip2ActionPerformed(evt);
            }
        });
        jBGranDescrip2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBGranDescrip2KeyPressed(evt);
            }
        });
        jP1.add(jBGranDescrip2, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 290, 10, 20));

        jComConcep2.setNextFocusableComponent(jTCtaDep2);
        jComConcep2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComConcep2ActionPerformed(evt);
            }
        });
        jComConcep2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComConcep2KeyPressed(evt);
            }
        });
        jP1.add(jComConcep2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 270, 140, 20));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Costo inicial:");
        jP1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 530, 140, 20));

        jTCost2.setEditable(false);
        jTCost2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCost2.setNextFocusableComponent(jTDepMens);
        jTCost2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCost2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCost2FocusLost(evt);
            }
        });
        jTCost2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCost2KeyPressed(evt);
            }
        });
        jP1.add(jTCost2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 530, 120, 20));

        jBGua.setBackground(new java.awt.Color(255, 255, 255));
        jBGua.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBGua.setForeground(new java.awt.Color(0, 102, 0));
        jBGua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/save.png"))); // NOI18N
        jBGua.setToolTipText("Guardar cambios (Ctrl+G)");
        jBGua.setName(""); // NOI18N
        jBGua.setNextFocusableComponent(jBSal);
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
        jP1.add(jBGua, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 520, 60, 30));

        jBBaj.setBackground(new java.awt.Color(255, 255, 255));
        jBBaj.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBBaj.setForeground(new java.awt.Color(0, 102, 0));
        jBBaj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/can.png"))); // NOI18N
        jBBaj.setToolTipText("Dar de baja activo (Ctrl+SUPR)");
        jBBaj.setName(""); // NOI18N
        jBBaj.setNextFocusableComponent(jBGua);
        jBBaj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBBajMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBBajMouseExited(evt);
            }
        });
        jBBaj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBajActionPerformed(evt);
            }
        });
        jBBaj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBBajKeyPressed(evt);
            }
        });
        jP1.add(jBBaj, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 520, 60, 30));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("% Depreciación:");
        jP1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 140, -1));

        jTDedu.setText("0.0");
        jTDedu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDedu.setNextFocusableComponent(jDFAdq);
        jTDedu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDeduFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDeduFocusLost(evt);
            }
        });
        jTDedu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDeduKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTDeduKeyTyped(evt);
            }
        });
        jP1.add(jTDedu, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 140, 20));

        jTCtaDep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCtaDep.setNextFocusableComponent(jTCtaGast);
        jTCtaDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaDepFocusLost(evt);
            }
        });
        jTCtaDep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaDepKeyPressed(evt);
            }
        });
        jP1.add(jTCtaDep, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 140, 20));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("% Deducción:");
        jP1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 140, -1));

        jTCtaGast.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCtaGast.setNextFocusableComponent(jTCtaDedu);
        jTCtaGast.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaGastFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaGastFocusLost(evt);
            }
        });
        jTCtaGast.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaGastKeyPressed(evt);
            }
        });
        jP1.add(jTCtaGast, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 140, 20));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Cta.Gasto:");
        jP1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 180, 20));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("Lugar:");
        jP1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 160, 20));

        jTCtaDedu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCtaDedu.setNextFocusableComponent(jComLug);
        jTCtaDedu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaDeduFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaDeduFocusLost(evt);
            }
        });
        jTCtaDedu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaDeduKeyPressed(evt);
            }
        });
        jP1.add(jTCtaDedu, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 140, 20));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("Cta.Depreciación:");
        jP1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 290, 180, 20));

        jTCtaDep2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCtaDep2.setNextFocusableComponent(jTCtaGast2);
        jTCtaDep2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaDep2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaDep2FocusLost(evt);
            }
        });
        jTCtaDep2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaDep2KeyPressed(evt);
            }
        });
        jP1.add(jTCtaDep2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 290, 140, 20));

        jTCtaGast2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCtaGast2.setNextFocusableComponent(jTCtaDedu2);
        jTCtaGast2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaGast2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaGast2FocusLost(evt);
            }
        });
        jTCtaGast2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaGast2KeyPressed(evt);
            }
        });
        jP1.add(jTCtaGast2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 310, 140, 20));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("Cta.Gasto:");
        jP1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 310, 180, 20));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("Fecha baja:");
        jP1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 390, 180, 20));

        jTCtaDedu2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTCtaDedu2.setNextFocusableComponent(jComLug2);
        jTCtaDedu2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTCtaDedu2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTCtaDedu2FocusLost(evt);
            }
        });
        jTCtaDedu2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCtaDedu2KeyPressed(evt);
            }
        });
        jP1.add(jTCtaDedu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 330, 140, 20));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("% Deducción:");
        jP1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 510, 140, -1));

        jTDedu2.setEditable(false);
        jTDedu2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDedu2.setNextFocusableComponent(jTCost2);
        jTDedu2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDedu2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDedu2FocusLost(evt);
            }
        });
        jTDedu2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDedu2KeyPressed(evt);
            }
        });
        jP1.add(jTDedu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 510, 120, 20));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setText("Acum. depreciación:");
        jP1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 310, 160, 20));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setText("Meses depreciado:");
        jP1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 350, 140, 20));

        jTDeduMens.setEditable(false);
        jTDeduMens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTDeduMens.setNextFocusableComponent(jTTotDep2);
        jTDeduMens.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTDeduMensFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTDeduMensFocusLost(evt);
            }
        });
        jTDeduMens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTDeduMensKeyPressed(evt);
            }
        });
        jP1.add(jTDeduMens, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 290, 120, 20));

        jTValAct.setEditable(false);
        jTValAct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTValAct.setNextFocusableComponent(jTMesDep);
        jTValAct.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTValActFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTValActFocusLost(evt);
            }
        });
        jTValAct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTValActKeyPressed(evt);
            }
        });
        jP1.add(jTValAct, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 330, 120, 20));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setText("Valor actual:");
        jP1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 330, 140, 20));

        jTMesDep.setEditable(false);
        jTMesDep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTMesDep.setNextFocusableComponent(jTProd);
        jTMesDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTMesDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTMesDepFocusLost(evt);
            }
        });
        jTMesDep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMesDepKeyPressed(evt);
            }
        });
        jP1.add(jTMesDep, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 350, 120, 20));

        jTFinDep.setEditable(false);
        jTFinDep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTFinDep.setNextFocusableComponent(jTMese);
        jTFinDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTFinDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTFinDepFocusLost(evt);
            }
        });
        jTFinDep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTFinDepKeyPressed(evt);
            }
        });
        jP1.add(jTFinDep, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 450, 120, 20));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setText("Meses depreciación:");
        jP1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 470, 170, 20));

        jComAlma2.setNextFocusableComponent(jBTrans);
        jComAlma2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComAlma2FocusLost(evt);
            }
        });
        jComAlma2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComAlma2ActionPerformed(evt);
            }
        });
        jComAlma2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComAlma2KeyPressed(evt);
            }
        });
        jP1.add(jComAlma2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 520, 80, 20));

        jBTrans.setBackground(new java.awt.Color(255, 255, 255));
        jBTrans.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBTrans.setForeground(new java.awt.Color(0, 102, 0));
        jBTrans.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/traspas.png"))); // NOI18N
        jBTrans.setToolTipText("Traspasar activo a almacén específico");
        jBTrans.setName(""); // NOI18N
        jBTrans.setNextFocusableComponent(jBBaj);
        jBTrans.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBTransMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBTransMouseExited(evt);
            }
        });
        jBTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTransActionPerformed(evt);
            }
        });
        jBTrans.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBTransKeyPressed(evt);
            }
        });
        jP1.add(jBTrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 520, 60, 30));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setText("Lugar:");
        jP1.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 350, 180, 20));

        jDtFBaj.setNextFocusableComponent(jTAComen2);
        jDtFBaj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDtFBajKeyPressed(evt);
            }
        });
        jP1.add(jDtFBaj, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 390, 140, -1));

        jTLug.setEditable(false);
        jTLug.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLug.setNextFocusableComponent(jBNew);
        jTLug.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLugFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLugFocusLost(evt);
            }
        });
        jTLug.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLugKeyPressed(evt);
            }
        });
        jP1.add(jTLug, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 140, 20));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setText("Cta.Deducible:");
        jP1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 180, 20));

        jComLug2.setNextFocusableComponent(jTLug2);
        jComLug2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComLug2FocusLost(evt);
            }
        });
        jComLug2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComLug2ActionPerformed(evt);
            }
        });
        jComLug2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComLug2KeyPressed(evt);
            }
        });
        jP1.add(jComLug2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 370, 120, 20));

        jTLug2.setEditable(false);
        jTLug2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jTLug2.setNextFocusableComponent(jDtFBaj);
        jTLug2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTLug2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTLug2FocusLost(evt);
            }
        });
        jTLug2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTLug2KeyPressed(evt);
            }
        });
        jP1.add(jTLug2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 370, 140, 20));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setText("Cta.Deducible:");
        jP1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 330, 180, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.DEFAULT_SIZE, 1289, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Obtiene todos los activos de la base de datos y cargalos en la tabla*/
    private void vCargAct()
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
        
        /*Trae todas los activos de la base de datos y cargalos en la tabla*/
        try
        {
            sQ = "SELECT prod, descrip, serprod, id_id FROM actfij WHERE baj = 0";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[]         = {iContFi, rs.getString("prod"), rs.getString("descrip"), rs.getString("serprod"), rs.getString("id_id")};
                te.addRow(nu);
                
                /*Aumentar en uno el contador de pes*/
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
        
    }/*Fin de private void vCargAct()*/
          
   
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
        
    
    /*Cuando se presiona una  tecla en la tabla de pes*/
    private void jTabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTabKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTabKeyPressed
    
                     
    /*Cuando se presiona el botón de agregar*/
    private void jBNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNewActionPerformed
                                                       
        /*Si hay cadena vacia en el campo del código del producto no puede continuar*/
        if(jTProd.getText().replace(" ", "").trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código del producto esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTProd.grabFocus();                        
            return;            
        }
        
        /*Si hay cadena vacia en el almacén entonces*/
        if(jComAlma.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComAlma.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del almacén esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jComAlma.grabFocus();                        
            return;            
        }
        
        /*Si hay cadena vacia en el combo del tipo de activo*/
        if(jComAct.getSelectedItem().toString().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jComAct.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El campo del tipo de activo esta vacio.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jComAct.grabFocus();                        
            return;            
        }
        
        /*Si el procentaje de depreciación no es correcto entonces*/
        if(Double.parseDouble(jTPorDepre.getText().trim())<=0)
        {
            /*Coloca el borde rojo*/                               
            jTPorDepre.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El porcentaje de depreciación no es válido.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTPorDepre.grabFocus();                        
            return;            
        }
                
        /*Si el costo no es correcto entonces*/
        if(Double.parseDouble(jTCost.getText().trim().replace("$", "").replace(",", ""))<=0)
        {
            /*Coloca el borde rojo*/                               
            jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El costo no es válido.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCost.grabFocus();                        
            return;            
        }
        
        /*Si hay cadena vacia en el campo de la cuenta de depreciación entonces*/
        if(jTCtaDep.getText().replace(" ", "").trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCtaDep.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La cuenta de depreciación esta vacia.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCtaDep.grabFocus();                        
            return;            
        }
        
        /*Si hay cadena vacia en el campo de la cuenta de gastos entonces*/
        if(jTCtaGast.getText().replace(" ", "").trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCtaGast.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La cuenta de gasto esta vacia.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCtaGast.grabFocus();                        
            return;            
        }
        
        /*Si el porcentaje de deducción es mayor a 0 entonces*/
        if(Double.parseDouble(jTDedu.getText().trim())>0)
        {
            /*Si hay cadena vacia en el campo de la cuenta de deducibles entonces*/
            if(jTCtaDedu.getText().replace(" ", "").trim().compareTo("")==0)
            {
                /*Coloca el borde rojo*/                               
                jTCtaDedu.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La cuenta de deducibles esta vacia.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Pon el foco del teclado en el campo de edición y regresa*/
                jTCtaDedu.grabFocus();                        
                return;            
            }
        }
                
        /*Pregunta al usuario si están bien los datos*/                
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que están bien los datos?", "Agregar activo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(false, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Contiene si el producto es un kit o un servcio*/
        String sKit     = "0";
        String sServ    = "0";

        //Declara variables de la base de datos    
        Statement   st;                
        String      sQ;
        ResultSet   rs;
        
        /*Obtiene si el producto es un kit o un servicio*/                
        try
        {
            sQ  = "SELECT compue, servi FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";                      
            st  = con.createStatement();
            rs  = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene los resultados*/
            if(rs.next() && jTProd.getText().trim().compareTo("")!=0)
            {
                sKit    = rs.getString("compue");
                sServ   = rs.getString("servi");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }            

        /*Si tiene una serie entonces*/
        if(jTSer.getText().trim().compareTo("")!=0)
        {
            /*Actualiza el almacén de esa serie para que pase a ser de activos fijos*/
            try 
            {            
                sQ = "UPDATE serieprod SET "
                        + "alma             = '" + Star.strAlmaActFij + "' "
                        + "WHERE ser        = '" + jTSer.getText().trim() + "'";
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
             }
            
        }/*Fin de if(jTSer.getText().trim().compareTo("")!=0)*/
        
        /*Si es un kit entonces*/
        if(sKit.compareTo("1")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El producto: " + jTProd.getText() + " es un kit y no se puede continuar.", "Activo fijo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el conrol y regresa*/
            jTProd.grabFocus();
            return;
        }
        
        /*Si es un servicio entonces*/
        if(sServ.compareTo("1")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El producto: " + jTProd.getText() + " es un servicio y no se puede continuar.", "Activo fijo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Coloca el foco del teclado en el conrol y regresa*/
            jTProd.grabFocus();
            return;
        }
        /*Obtiene la fecha de adquisición definida por el usuario*/
        SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");
        java.util.Date dtDat    = jDFAdq.getDate();                        
        String sFAdqUsr         = sdf.format(dtDat);
        
        /*Obtiene la fecha de fin de depreciación*/
        dtDat                   = jDFFinDep.getDate();
        String sFFinDep         = sdf.format(dtDat);
        
        /*Obtiene la fecha de inicio de depreciación*/
        dtDat                   = jDFIniDep.getDate();
        String sFIniDep         = sdf.format(dtDat);                
        
        /*Contiene la unidad del producto*/
        String sUnid    = "";
        
        /*Obtiene la unidad actual del producto*/        
        try
        {
            sQ = "SELECT unid FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sUnid   = rs.getString("unid");                                   
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        /*Si la unidad es kilo, tonelada y gramo entonces*/
        if(sUnid.compareTo("KILO")==0 || sUnid.compareTo("GRAMO")==0 || sUnid.compareTo("TONELADA")==0)
        {
            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El producto: " + jTProd.getText().trim() + " tiene una unidad no valida y no se puede continuar.", "Activo fijo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Pon el foco del teclado en el control y regresa*/
            jTProd.grabFocus();
            return;
        }

        //Inicia la transacción
        if(Star.iIniTransCon(con)==-1)
            return;

        /*Realiza la afectación correspondiente al almacén para la salida*/
        if(Star.iAfecExisProd(con, jTProd.getText().replace("'", "''").trim(), jComAlma.getSelectedItem().toString().replace("'", "''").trim(), "1", "-")==-1)
            return;
        
        /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
        if(!Star.vRegMoniInv(con, jTProd.getText().trim(), "1", jTDescrip.getText().trim(), jComAlma.getSelectedItem().toString().trim(), Login.sUsrG , "", "TRAS", sUnid, "", "", "1"))                                
            return;      
        
        /*Realiza la afectación correspondiente al almacén para la entrada*/
        if(Star.iAfecExisProd(con, jTProd.getText().replace("'", "''").trim(), Star.strAlmaActFij, "1", "+")==-1)
            return;
        
        /*Registra el producto que se esta ingresando al inventario en la tabla de monitor de inventarios*/
        if(!Star.vRegMoniInv(con, jTProd.getText().trim(), "1", jTDescrip.getText().trim(), Star.strAlmaActFij, Login.sUsrG , "", "TRAS", sUnid, "", "", "0"))                                
            return;      
        sUnid="'"+sUnid+"'";
        /*Registra el traspaso entre los almacenes*/
        try 
        {        
            sQ = "INSERT INTO traspas(      prod,                                                   alma,                                                                concep,   unid,   cantsal,         almaa,                   cantent,    estac,                                   falt,           sucu,                                     nocaj) " + 
                            "VALUES('" +    jTProd.getText().replace("'", "''").trim() + "','" +    jComAlma.getSelectedItem().toString().replace("'", "''").trim() + "','TRAS',"+sUnid+ ",    1,          '" + Star.strAlmaActFij + "',   1, '" +     Login.sUsrG.replace("'", "''") + "',     now(), '" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''")+"')" ;
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
         }
        System.out.println("1");
        /*Inserta el nuevo activo fijo en la base de datos*/
        try 
        {            
            sQ = "INSERT INTO actfij(prod,                                                  serprod,                         descrip,                               porcendep,                             fadquisusr,        finidep,           ffindep,           estadusr,                                                  tipact,                                                 comen,                                  estac,                sucu,                                     nocaj,                                   cost,                                                               alma,                                           porcendedu,                         ctadedu,                              ctadepre,                            ctagast,                              lug) " + 
                        "VALUES('" + jTProd.getText().trim().replace("'", "''") + "','" +   jTSer.getText().trim() + "','" + jTDescrip.getText().trim() + "'," +    jTPorDepre.getText().trim() + ", '" +  sFAdqUsr + "','" + sFIniDep + "','" + sFFinDep + "','" + jComConcep.getSelectedItem().toString().trim() + "','" +   jComAct.getSelectedItem().toString().trim() + "','" +   jTAComen.getText().trim() + "','" +     Login.sUsrG + "','" + Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "', " + jTCost.getText().trim().replace("$", "").replace(",", "") + ", '" + jComAlma.getSelectedItem().toString() + "', " + jTDedu.getText().trim() + ", '" +   jTCtaDedu.getText().trim() + "', '" + jTCtaDep.getText().trim() + "', '" + jTCtaGast.getText().trim() + "', '" + jComLug.getSelectedItem().toString().trim() + "')";                                
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        System.out.println("2");
        /*Contiene el último id insertado*/
        String sId  = "";
        
        /*Obtiene el último ID insertado*/
        try
        {
            sQ = "SELECT MAX(id_id) AS id FROM actfij ORDER BY id_id DESC";	
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resutlado*/
            if(rs.next())
                sId     = rs.getString("id");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        System.out.println("yay");
        //Termina la transacción
        if(Star.iTermTransCon(con)==-1)
            return;
                
        //Cierra la base de datos
        if(Star.iCierrBas(con)==-1)
            return;

        /*Agrega el registro del peso en la tabla*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();
        Object nu[]= {iContFi, jTProd.getText().trim(), jTDescrip.getText().trim(), jTSer.getText().trim(), sId};
        te.addRow(nu);
        
        /*Aumenta en uno el contador de filas en 1*/
        ++iContFi;
        
        /*Pon el foco del teclado en el campo del producto*/
        jTProd.grabFocus();
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Activo: " + jTProd.getText().trim() + " agregado con éxito.", "Exito al agregar", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                    
         
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
    private void jBMosTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMosTKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBMosTKeyPressed

    
    /*Función para mostrar nuevamente los elementos actualizados en la tabla*/
    private void vCargT()
    {
        /*Borra todos los item en la tabla*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Obtiene todos los pes de la base de datos y cargalos en la tabla*/
        vCargAct();
        
        /*Vuelve a poner el foco del teclaod en el campo de buscar*/
        jTBusc.grabFocus();        
    }
    
    
    /*Cuando se presiona el botón de mostrar todo*/
    private void jBMosTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMosTActionPerformed
        
        /*Función para mostrar nuevamente los elementos actualizados en la tabla*/
        vCargT();
        
    }//GEN-LAST:event_jBMosTActionPerformed

    
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
        
        /*Borra todos los item en la tabla de almacenes*/
        DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
        dm.setRowCount(0);
        
         /*Resetea el contador de filas*/
        iContFi              = 1;

        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Obtiene de la base de datos todos los pes*/        
        try
        {                  
            sQ = "SELECT prod, descrip, serprod, id_id FROM actfij WHERE prod LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR descrip LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR fadquisusr LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR finidep LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR ffindep LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR estadusr LIKE('%" + jTBusc.getText().replace(" ", "%") + "%') OR serprod LIKE('%" + jTBusc.getText().replace(" ", "%") + "%')";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo en la tabla*/
                DefaultTableModel te    = (DefaultTableModel)jTab.getModel();
                Object nu[]             = {iContFi, rs.getString("prod"), rs.getString("descrip"), rs.getString("serprod"), rs.getString("id_id")};
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

    
    /*Cuando el mouse sale del botón de bùscar*/
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
    private void jBNewMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBNew.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBNewMouseEntered

        
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
    private void jBNewMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBNewMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBNew.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBNewMouseExited
    
    
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

        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBNew);
        
        /*Coloca el caret en la posiciòn 0*/
        jTBusc.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTBusc.getText().compareTo("")!=0)
            jTBusc.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(204,204,255)));
        
    }//GEN-LAST:event_jTBuscFocusLost

    
    /*Cuando se presiona una tecla en el combo de los conceptos*/
    private void jComConcepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComConcepKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComConcepKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del estado*/
    private void jTEstadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTEstad.setSelectionStart(0);jTEstad.setSelectionEnd(jTEstad.getText().length());        
        
    }//GEN-LAST:event_jTEstadFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del estado*/
    private void jTEstadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTEstadFocusLost
 
        /*Coloca el caret en la posiciòn 0*/
        jTEstad.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTEstad.getText().compareTo("")!=0)
            jTEstad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTEstadFocusLost

    
    /*Cuando se presiona una tecla en el campo del estado*/
    private void jTEstadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTEstadKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTEstadKeyPressed

    
    /*Cuando se presiona una tecla en el combo de activos*/
    private void jComActKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComActKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComActKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del activo fijo*/
    private void jTActFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTActFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAct.setSelectionStart(0);jTAct.setSelectionEnd(jTAct.getText().length());        
        
    }//GEN-LAST:event_jTActFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del activo*/
    private void jTActFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTActFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAct.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAct.getText().compareTo("")!=0)
            jTAct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTActFocusLost

    
    /*Cuando se presiona una tecla en el campo del activo fijo*/
    private void jTActKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTActKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTActKeyPressed

    
    /*Cuando sucede una acción en el combo de los conceptos*/
    private void jComConcepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComConcepActionPerformed
        
        /*Si no hay datos entonces regresa*/
        if(jComConcep.getSelectedItem()==null)
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

        /*Obtiene la descripción del concepto en base a su código*/
        try
        {
            sQ = "SELECT descrip FROM actfijcat WHERE concep = '" + jComConcep.getSelectedItem().toString() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la descripción en su campo*/
            if(rs.next())
                jTEstad.setText(rs.getString("descrip"));
            else
                jTEstad.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Coloca al principio del control el caret*/
        jTEstad.setCaretPosition(0);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComConcepActionPerformed

    
    /*Cuando sucede una acción en el combo de tipo de activo fijo*/
    private void jComActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComActActionPerformed
        
        /*Si no hay datos entonces regresa*/
        if(jComAct.getSelectedItem()==null)
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

        /*Obtiene la descripción del tipo de activo en base a su código*/
        try
        {
            sQ = "SELECT descrip FROM tipactfij WHERE tip = '" + jComAct.getSelectedItem().toString() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la descripción en su campo*/
            if(rs.next())
                jTAct.setText(rs.getString("descrip"));
            else
                jTAct.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Coloca al principio del control el caret*/
        jTAct.setCaretPosition(0);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComActActionPerformed
   
    
    /*Cuando se gana el foco del teclado en el campo del producto*/
    private void jTProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTProd.setSelectionStart(0);
        jTProd.setSelectionEnd(jTProd.getText().length());
        
    }//GEN-LAST:event_jTProdFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del producto*/
    private void jTProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProdFocusLost

        /*Búsca la información del producto*/
        vBuscProd();            
        
    }//GEN-LAST:event_jTProdFocusLost

    
    /*Cuando se presiona una tecla en el campo del producto*/
    private void jTProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyPressed

        /*Si se presiona la tecla de abajo entonces presiona el botón de búscar producto*/
        if(evt.getKeyCode() == KeyEvent.VK_DOWN)
            jBProd.doClick();
        /*Else if se presiono CTRL + G entonces presiona el botón de guardar cambios*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGua.doClick();
        /*Else if se presiono CTRL + SUPR entonces presiona el botón de dar de baja activo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_DELETE)
            jBBaj.doClick();
        /*Else if se presiono CTRL + B entonces*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_B)
        {
            /*Llama a la forma para búscar productos avanzadamente*/
            ptovta.BuscAvan w = new ptovta.BuscAvan(this, jTProd, null, null, jTSer);
            w.setVisible(true);

            /*Coloca el foco del teclado en el campo del producto*/
            jTProd.grabFocus();
            
            /*Búsca la información del producto*/
            vBuscProd();            
        }
        /*Else, llama a la función para procesarlo normlemnte*/
        else
            vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProdKeyPressed

    
    /*Búsca la información del producto*/
    private void vBuscProd()
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

        /*Obtiene la descripción del producto en base a su código*/
        try
        {
            sQ = "SELECT descrip FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTDescrip.setText(rs.getString("descrip"));
            else
                jTDescrip.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Coloca al principio del control el caret*/
        jTDescrip.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }/*Fin de private void vBuscProd()*/
            
    /*Cuando se tipea una tecla en el campo del producto*/
    private void jTProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProdKeyTyped

        /*Si el carácter introducido es minúscula entonces colocalo en el campo con mayúsculas*/
        if(Character.isLowerCase(evt.getKeyChar()))
            evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));

    }//GEN-LAST:event_jTProdKeyTyped

    
    /*Cuando el mouse entra en el botón de búscar producto*/
    private void jBProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseEntered

        /*Cambia el color del fondo del botón*/
        jBProd.setBackground(Star.colBot);

    }//GEN-LAST:event_jBProdMouseEntered

    
    /*Cuando el mouse sale del botón de búscar producto*/
    private void jBProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBProdMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBProd.setBackground(Star.colOri);

    }//GEN-LAST:event_jBProdMouseExited

    
    /*Cuando se presiona el botón de búscar producto*/
    private void jBProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProdActionPerformed

        /*Llama al otro formulario de búsqueda y pasale lo que el usuario escribió*/
        Busc b = new Busc(this, jTProd.getText(), 2, jTProd, jTDescrip, null, "", null);
        b.setVisible(true);

        /*Coloca la descripción del producto al principio del control*/
        jTDescrip.setCaretPosition(0);

        /*Coloca el foco del teclado en el campo del código del producto*/
        jTProd.grabFocus();
        
    }//GEN-LAST:event_jBProdActionPerformed

    
    /*Cuando se presiona una tecla en el botón de búscar producto*/
    private void jBProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBProdKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBProdKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del producto*/
    private void jTDescripFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescrip.setSelectionStart(0);jTDescrip.setSelectionEnd(jTDescrip.getText().length());

    }//GEN-LAST:event_jTDescripFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del producto*/
    private void jTDescripFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripFocusLost

        /*Coloca el caret al principio del control*/
        jTDescrip.setCaretPosition(0);

    }//GEN-LAST:event_jTDescripFocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción del producto*/
    private void jTDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTDescripKeyPressed

    
    /*Cuando se presiona el botón de ver en grande la descripción del producto*/
    private void jBGranDescripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGranDescripActionPerformed

        /*Muestar la forma para ver la descripción en grande*/
        DescripGran b = new DescripGran(jTDescrip, null);
        b.setVisible(true);
        
    }//GEN-LAST:event_jBGranDescripActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver en grande la descripción del producto*/
    private void jBGranDescripKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGranDescripKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBGranDescripKeyPressed

    
    /*Cuando se gana el foco en el campo del porcentaje de depreciación*/
    private void jTPorDepreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPorDepreFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTPorDepre.setSelectionStart(0);jTPorDepre.setSelectionEnd(jTPorDepre.getText().length());        
        
    }//GEN-LAST:event_jTPorDepreFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del porcentaje de depreciación*/
    private void jTPorDepreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPorDepreFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTPorDepre.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTPorDepre.getText().compareTo("")!=0)
            jTPorDepre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
        /*Si no se puede parsear a doble entonces*/
        double dVal;
        try
        {
            dVal    = Double.parseDouble(jTPorDepre.getText().trim());
        }
        catch(NumberFormatException expnNumForm)
        {
            /*Coloca 0.0 y regresa*/
            jTPorDepre.setText("0.0");
            
            /*Valida las fechas de inicio de depreciación, costo y porcentaje y regresa*/
            //vValDep();                    
            return;
        }
        
        /*Coloca el nuevo valor parseado a double*/
        jTPorDepre.setText(Double.toString(dVal));        
        
        /*Valida las fechas de inicio de depreciación, costo y porcentaje*/
        //vValDep();                    
            
    }//GEN-LAST:event_jTPorDepreFocusLost

    
    /*Valida las fechas de inicio de depreciación, costo y porcentaje*/
    private void vValDep()
    {
        /*Obtiene el porcentaje de depreciación*/
        String sPorDep  = jTPorDepre.getText().trim();
    
        /*Si el porcentaje de depreciación es 0 entonces*/
        if(Double.parseDouble(sPorDep)==0)
        {
            /*Coloca la fecha del día de hoy para la fecha de fin de depreciación*/
            java.util.Date dtDat = new java.util.Date();
            jDFFinDep.setDate(dtDat);
            
            /*Regresa*/
            return;
        }
        /*Obtiene el porcentaje de depreciación de ese producto*/
        String sPorProd = Double.toString(Double.parseDouble(sPorDep)/12);                
        
        /*Obtiene los meses y días posibles para depreciación*/
        String sMesDia  = Double.toString(100.0 / Double.parseDouble(sPorProd));                
        
        /*Obtengo los meses que tengo que sumar*/
        String sMes     = Integer.toString((int)Double.parseDouble(sMesDia));                
        
        /*Obtiene la fecha de inicio de depreciación que ingreso el usuario*/
        java.util.Date dtDat    = jDFIniDep.getDate();
        
        /*Declara el objeto calendario*/        
        Calendar cal = Calendar.getInstance(); 
        
        /*Inicializalo con la fecha de inicio de depreciación que escogió el usuario*/
        cal.setTime(dtDat);
        
        /*Sumale los meses que se puede depreciar*/
        cal.add(Calendar.MONTH,         Integer.parseInt(sMes));        

        /*Obtiene la fecha que quedo*/
        dtDat   = cal.getTime();
        
        /*Establece la fecha de depreciación en la fecha final de depreciación*/
        jDFFinDep.setDate(dtDat);
        
    }/*Fin de private void vValDep()*/
        
        
    /*Cuando se presiona una tecla en el campo del porcentaje de depreciación*/
    private void jTPorDepreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPorDepreKeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTPorDepreKeyPressed

    
    /*Cuando se tipea una tecla en el campo del porcentaje de depreciación*/
    private void jTPorDepreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPorDepreKeyTyped
 
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTPorDepreKeyTyped
    
    
    /*Cuando se presiona una tecla en el campo de fecha de adquicisión*/
    private void jDFAdqKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFAdqKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jDFAdqKeyPressed

    
    /*Cuando se presiona una tecla en el control de la fecha de inición de depreciación*/
    private void jDFIniDepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFIniDepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDFIniDepKeyPressed

    
    /*Cuando se presiona una tecla en el campo de los comentarios*/
    private void jTAComenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAComenKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAComenKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del comentario*/
    private void jTAComenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAComenFocusGained
 
        /*Selecciona todo el texto cuando gana el foco*/
        jTAComen.setSelectionStart(0);jTAComen.setSelectionEnd(jTAComen.getText().length());        
        
    }//GEN-LAST:event_jTAComenFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del comentario*/
    private void jTAComenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAComenFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAComen.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAComen.getText().compareTo("")!=0)
            jTAComen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTAComenFocusLost

    
    /*Cuando se pierde el foco en el combo el almacén*/
    private void jComAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComAlmaFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComAlma.getSelectedItem().toString().compareTo("")!=0)
            jComAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComAlmaFocusLost

    
    /*Cuando sucede una acción en el combo de almacén*/
    private void jComAlmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComAlmaActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComAlma.getSelectedItem()==null)
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

        /*Obtiene la descripción del almacén en base a su código*/
        try
        {
            sQ = "SELECT almadescrip FROM almas WHERE alma = '" + jComAlma.getSelectedItem().toString() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca el valor en el campo*/
            if(rs.next())
                jTDescripAlma.setText(rs.getString("almadescrip"));
            else
                jTDescripAlma.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;            
        }

        /*Coloca al principio del control el caret*/
        jTDescripAlma.setCaretPosition(0);

        //Cierra la base de datos
        Star.iCierrBas(con);                    
        
    }//GEN-LAST:event_jComAlmaActionPerformed

    
    /*Cuando se presiona una tecla en el combo del almacén*/
    private void jComAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComAlmaKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la descripción del almacén*/
    private void jTDescripAlmaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlmaFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTDescripAlma.setSelectionStart(0);jTDescripAlma.setSelectionEnd(jTDescripAlma.getText().length());
        
    }//GEN-LAST:event_jTDescripAlmaFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción del almacén*/
    private void jTDescripAlmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescripAlmaFocusLost

        /*Coloca el caret al principio del control*/
        jTDescripAlma.setCaretPosition(0);
        
    }//GEN-LAST:event_jTDescripAlmaFocusLost

    
    /*Cuando se presiona una tecal en el campo de la descripción del almacén*/
    private void jTDescripAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescripAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescripAlmaKeyPressed

    
    /*Cuando sucede una acción en el botón de existencias por almacén*/
    private void jBExisAlmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExisAlmaActionPerformed

        /*Si no a ingresado un producto entonces*/
        if(jTProd.getText().trim().compareTo("")==0)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona primero un producto.", "Existencias por almacén", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca el borde rojo*/
            jTProd.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Coloca el foco del teclado en el campo del producto y regresa*/
            jTProd.grabFocus();
            return;
        }

        /*Muestra la forma para ver las existencias por almacén del producto*/
        ptovta.ProdExisAlm m = new ptovta.ProdExisAlm(jTProd.getText().trim(),jComAlma);
        m.setVisible(true);

    }//GEN-LAST:event_jBExisAlmaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de existencias por almacén*/
    private void jBExisAlmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBExisAlmaKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jBExisAlmaKeyPressed

    
    /*Cuando se presiona una tecla en el control de la fecha de fin de depreciación*/
    private void jDFFinDepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDFFinDepKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jDFFinDepKeyPressed

    
    /*Cuando se presiona una tecla en el botón de seleccionar todo*/
    private void jBTodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTodKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
    }//GEN-LAST:event_jBTodKeyPressed

    
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

    
    /*Cuando el mouse sale del botón específico*/
    private void jBTodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseExited

        /*Cambia el color del fondo del botón al original*/
        jBTod.setBackground(Star.colOri);

    }//GEN-LAST:event_jBTodMouseExited

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBTodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTodMouseEntered

        /*Cambia el color del fondo del botón*/
        jBTod.setBackground(Star.colBot);

    }//GEN-LAST:event_jBTodMouseEntered

    
    /*Cuando se gana el foco del teclado en el campo de la serie*/
    private void jTSerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSer.setSelectionStart(0);jTSer.setSelectionEnd(jTSer.getText().length());
        
    }//GEN-LAST:event_jTSerFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la serie*/
    private void jTSerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSerFocusLost
        
        /*Coloca el caret al principio del control*/
        jTSer.setCaretPosition(0);
        
    }//GEN-LAST:event_jTSerFocusLost

    
    /*Cuando se presiona una tcla en el campo de la serie del producto*/
    private void jTSerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSerKeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSerKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del costo*/
    private void jTCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCost.setSelectionStart(0);jTCost.setSelectionEnd(jTCost.getText().length());        
        
    }//GEN-LAST:event_jTCostFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del costo*/
    private void jTCostFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCostFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCost.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCost.getText().compareTo("")!=0)
            jTCost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
        /*Si no se puede parsear a doble entonces*/        
        try
        {
            Double.parseDouble(jTCost.getText().trim().replace("$", "").replace(",", ""));
        }
        catch(NumberFormatException expnNumForm)
        {
            /*Coloca $0.0 y regresa*/
            jTCost.setText("$0.0");
            
            /*Valida las fechas de inicio de depreciación, costo, porcentaje y regresa*/
            //vValDep();                    
            return;
        }
        
        /*Dale formato de moneda al costo*/        
        NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.US);
        double dCant    = Double.parseDouble(jTCost.getText().trim().replace("$", "").replace(",", ""));                        

        /*Coloca el nuevo valor en el control*/
        jTCost.setText(n.format(dCant));
        
        /*Valida las fechas de inicio de depreciación, costo y porcentaje*/
        //vValDep();                    
        
    }//GEN-LAST:event_jTCostFocusLost

    
    /*Cuando se presiona uan tecla en el campo del costo*/
    private void jTCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCostKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCostKeyPressed

    
    /*Cuando se tipea una tecla en el campo del costo*/
    private void jTCostKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCostKeyTyped
 
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTCostKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de la descripción 2*/
    private void jTDescrip2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescrip2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDescrip2.setSelectionStart(0);jTDescrip2.setSelectionEnd(jTDescrip2.getText().length());
        
    }//GEN-LAST:event_jTDescrip2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la descripción 2*/
    private void jTDescrip2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDescrip2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDescrip2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDescrip2.getText().compareTo("")!=0)
            jTDescrip2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTDescrip2FocusLost

    
    /*Cuando se presiona una tecla en el campo de la descripción 2*/
    private void jTDescrip2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDescrip2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDescrip2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la serie 2*/
    private void jTSer2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSer2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTSer2.setSelectionStart(0);jTSer2.setSelectionEnd(jTSer2.getText().length());
        
    }//GEN-LAST:event_jTSer2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del a serie*/
    private void jTSer2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTSer2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTSer2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTSer2.getText().compareTo("")!=0)
            jTSer2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTSer2FocusLost

    
    /*Cuando se presiona una tecla en el campo de la serie 2*/
    private void jTSer2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTSer2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTSer2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de depreciación 2*/
    private void jTDep2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDep2FocusGained
 
        /*Selecciona todo el texto cuando gana el foco*/
        jTDep2.setSelectionStart(0);jTDep2.setSelectionEnd(jTDep2.getText().length());
        
    }//GEN-LAST:event_jTDep2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del porcentaje de depreciación*/
    private void jTDep2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDep2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDep2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDep2.getText().compareTo("")!=0)
            jTDep2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTDep2FocusLost

    
    /*Cuando se presiona una tecla en el campo de la depreciación 2*/
    private void jTDep2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDep2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDep2KeyPressed


    /*Cuando se gana el foco del teclado en el campo de la fecha de adquisición 2*/
    private void jTFAdq2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFAdq2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFAdq2.setSelectionStart(0);jTFAdq2.setSelectionEnd(jTFAdq2.getText().length());
        
    }//GEN-LAST:event_jTFAdq2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la fecha de adquisición 2*/
    private void jTFAdq2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFAdq2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTFAdq2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTFAdq2.getText().compareTo("")!=0)
            jTFAdq2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTFAdq2FocusLost

    
    /*Cuando se presiona una tecla en el campo de la fecha de adquisición 2*/
    private void jTFAdq2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFAdq2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFAdq2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de diferencia en meses de fin de depreciación*/
    private void jTMeseFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMeseFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMese.setSelectionStart(0);jTMese.setSelectionEnd(jTMese.getText().length());
        
    }//GEN-LAST:event_jTMeseFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la diferencia de meses de depreciación*/
    private void jTMeseFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMeseFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTMese.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTMese.getText().compareTo("")!=0)
            jTMese.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTMeseFocusLost

    
    /*Cuando se presiona una tecla en el campo de diferencia de meses de depreciación*/
    private void jTMeseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMeseKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTMeseKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la fecha de inicio de depreciación*/
    private void jTFIniDep2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFIniDep2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFIniDep2.setSelectionStart(0);jTFIniDep2.setSelectionEnd(jTFIniDep2.getText().length());
        
    }//GEN-LAST:event_jTFIniDep2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del inicio de depreciación*/
    private void jTFIniDep2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFIniDep2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTFIniDep2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTFIniDep2.getText().compareTo("")!=0)
            jTFIniDep2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTFIniDep2FocusLost

    
    /*Cuando se presiona una tecla en el campo de la fecha de inicio de depreciación*/
    private void jTFIniDep2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFIniDep2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFIniDep2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la fecha de baja 2*/
    private void jTFBajDep2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFBajDep2FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTFBajDep2.setSelectionStart(0);jTFBajDep2.setSelectionEnd(jTFBajDep2.getText().length());
        
    }//GEN-LAST:event_jTFBajDep2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la fecha de baja*/
    private void jTFBajDep2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFBajDep2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTFBajDep2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTFBajDep2.getText().compareTo("")!=0)
            jTFBajDep2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTFBajDep2FocusLost

    
    /*Cuando se presiona una tecla en el campo de la fecha de baja*/
    private void jTFBajDep2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFBajDep2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFBajDep2KeyPressed

    
    
    
    
    /*Cuando se gana el foco del teclado en el campo de la depreciación del sistema 2*/
    private void jTDepSis2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDepSis2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDepSis2.setSelectionStart(0);jTDepSis2.setSelectionEnd(jTDepSis2.getText().length());
                
    }//GEN-LAST:event_jTDepSis2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del estado de depreciación del usuario*/
    private void jTDepSis2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDepSis2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDepSis2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDepSis2.getText().compareTo("")!=0)
            jTDepSis2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
                
    }//GEN-LAST:event_jTDepSis2FocusLost

    
    /*Cuando se presiona una tecla en el campo del estado de la depreciación del sistema*/
    private void jTDepSis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDepSis2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDepSis2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del tipo de activo 2*/
    private void jTTipAct2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTipAct2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTipAct2.setSelectionStart(0);jTTipAct2.setSelectionEnd(jTTipAct2.getText().length());
        
    }//GEN-LAST:event_jTTipAct2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del tipo de activo*/
    private void jTTipAct2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTipAct2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTipAct2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTTipAct2.getText().compareTo("")!=0)
            jTTipAct2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTTipAct2FocusLost

    
    /*Cuando se presiona una tecla en el campo del tipo de activo 2*/
    private void jTTipAct2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTipAct2KeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTipAct2KeyPressed


    /*Cuando se gana el foco del teclado en el campo de comentarios 2*/
    private void jTAComen2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAComen2FocusGained
 
        /*Selecciona todo el texto cuando gana el foco*/
        jTAComen2.setSelectionStart(0);jTAComen2.setSelectionEnd(jTAComen2.getText().length());
        
    }//GEN-LAST:event_jTAComen2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del comentario 2*/
    private void jTAComen2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAComen2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAComen2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAComen2.getText().compareTo("")!=0)
            jTAComen2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTAComen2FocusLost

    
    /*Cuando se presiona una tecla en el campo de comentarios 2*/
    private void jTAComen2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAComen2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAComen2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del activo 2*/
    private void jTAct2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAct2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTAct2.setSelectionStart(0);jTAct2.setSelectionEnd(jTAct2.getText().length());
        
    }//GEN-LAST:event_jTAct2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del activo o no*/
    private void jTAct2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTAct2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTAct2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTAct2.getText().compareTo("")!=0)
            jTAct2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTAct2FocusLost

    
    /*Cuando se presiona una tecla en el campo del activo o inactivo*/
    private void jTAct2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAct2KeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTAct2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del total de depreciación*/
    private void jTTotDep2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotDep2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTTotDep2.setSelectionStart(0);jTTotDep2.setSelectionEnd(jTTotDep2.getText().length());
        
    }//GEN-LAST:event_jTTotDep2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del total de depreciación*/
    private void jTTotDep2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTTotDep2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTTotDep2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTTotDep2.getText().compareTo("")!=0)
            jTTotDep2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTTotDep2FocusLost

    
    /*Cuando se presiona una tecla en el campo del total de la depreciación*/
    private void jTTotDep2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTTotDep2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTTotDep2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de depreciación mensual 2*/
    private void jTDepMensFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDepMensFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDepMens.setSelectionStart(0);jTDepMens.setSelectionEnd(jTDepMens.getText().length());
        
    }//GEN-LAST:event_jTDepMensFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la depreciación mensual 2*/
    private void jTDepMensFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDepMensFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDepMens.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDepMens.getText().compareTo("")!=0)
            jTDepMens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTDepMensFocusLost

    
    /*Cuando se presiona una tecla en el campo de la depreciación mensual*/
    private void jTDepMensKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDepMensKeyPressed
                
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDepMensKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del producto 2*/
    private void jTProd2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProd2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTProd2.setSelectionStart(0);jTProd2.setSelectionEnd(jTProd2.getText().length());
        
    }//GEN-LAST:event_jTProd2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del producto 2*/
    private void jTProd2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProd2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTProd2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTProd2.getText().compareTo("")!=0)
            jTProd2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTProd2FocusLost

    
    /*Cuando se presiona una tecla en el campo del producto 2*/
    private void jTProd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProd2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTProd2KeyPressed

    
    /*Cuando se presiona el botón de ver descripción en grande*/
    private void jBGranDescrip2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGranDescrip2ActionPerformed
        
        /*Obtiene el valor original*/
        String sValOri  = jTDescrip2.getText().trim();
        
        /*Muestar la forma para ver la descripción en grande*/
        DescripGran b = new DescripGran(jTDescrip2, null);
        b.setVisible(true);
        
        /*Coloca el valor original en el control que tenía*/
        jTDescrip2.setText(sValOri);
        
    }//GEN-LAST:event_jBGranDescrip2ActionPerformed

    
    /*Cuando se presiona una tecla en el botón de ver descripción en grande 2*/
    private void jBGranDescrip2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGranDescrip2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGranDescrip2KeyPressed

    
    /*Cuando sucede una acción en el combo de conceptos 2*/
    private void jComConcep2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComConcep2ActionPerformed
        
        /*Si no hay datos entonces regresa*/
        if(jComConcep2.getSelectedItem()==null)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "descrip", "actfijcat", "WHERE concep = '" + jComConcep2.getSelectedItem().toString() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComConcep2.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComConcep2ActionPerformed

    
    /*Cuando se presiona una tecla en el combo de los conceptos*/
    private void jComConcep2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComConcep2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComConcep2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del costo 2*/
    private void jTCost2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCost2FocusGained
 
        /*Selecciona todo el texto cuando gana el foco*/
        jTCost2.setSelectionStart(0);jTCost2.setSelectionEnd(jTCost2.getText().length());
        
    }//GEN-LAST:event_jTCost2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del costo 2*/
    private void jTCost2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCost2FocusLost
 
        /*Coloca el caret en la posiciòn 0*/
        jTCost2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCost2.getText().compareTo("")!=0)
            jTCost2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTCost2FocusLost

    
    /*Cuando se presiona una tecal en el campo del costo 2*/
    private void jTCost2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCost2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCost2KeyPressed

    
    /*Cuando el mouse entra en el botón de guardar*/
    private void jBGuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaMouseEntered
 
        /*Cambia el color del fondo del botón*/
        jBGua.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBGuaMouseEntered

    
    /*Cuando el mouse sale del botón de guardar cambios*/
    private void jBGuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBGuaMouseExited
 
        /*Cambia el color del fondo del botón*/
        jBGua.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBGuaMouseExited

    
    /*Cuando se presiona el botón de guardar cambios*/
    private void jBGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuaActionPerformed
        
        /*Si no se a seleccionado nada en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un activo de la lista para guardar cambios.", "Guardar cambios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }
                
        /*Si hay cadena vacia en el campo de la cuenta de depreciación entonces*/
        if(jTCtaDep2.getText().replace(" ", "").trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCtaDep2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La cuenta de depreciación esta vacia.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCtaDep2.grabFocus();                        
            return;            
        }
        
        /*Si hay cadena vacia en el campo de la cuenta de gastos entonces*/
        if(jTCtaGast2.getText().replace(" ", "").trim().compareTo("")==0)
        {
            /*Coloca el borde rojo*/                               
            jTCtaGast2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "La cuenta de gasto esta vacia.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en el campo de edición y regresa*/
            jTCtaGast2.grabFocus();                        
            return;            
        }
        
        /*Si el porcentaje de deducción es mayor a 0 entonces*/
        if(Double.parseDouble(jTDedu2.getText().trim())>0)
        {
            /*Si hay cadena vacia en el campo de la cuenta de deducibles entonces*/
            if(jTCtaDedu2.getText().replace(" ", "").trim().compareTo("")==0)
            {
                /*Coloca el borde rojo*/                               
                jTCtaDedu2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));

                /*Mensajea*/
                JOptionPane.showMessageDialog(null, "La cuenta de deducibles esta vacia.", "Campo vacio", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

                /*Pon el foco del teclado en el campo de edición y regresa*/
                jTCtaDedu2.grabFocus();                        
                return;            
            }
        }
        
        /*Preguntar al usuario si esta seguro de que querer guardar los cambios*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres guardar los cambios?", "Guardar cambios", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
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
        
        /*Guarda los cambios en la base de datos*/
        try 
        {                
            sQ = "UPDATE actfij SET "
                    + "comen            = '" + jTAComen2.getText().trim() + "', "
                    + "ctagast          = '" + jTCtaGast2.getText().trim() + "', "
                    + "ctadedu          = '" + jTCtaDedu2.getText().trim() + "', "
                    + "ctadepre         = '" + jTCtaDep2.getText().trim() + "', "
                    + "lug              = '" + jComLug2.getSelectedItem().toString().trim() + "', "
                    + "estadusr         = '" + jComConcep2.getSelectedItem().toString().trim() + "' "
                    + "WHERE id_id      = " + jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                            
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

        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Cambios guardados con éxito.", "Guardar cambios", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBGuaActionPerformed

    
    /*Cuando se presiona una tecla en el botón de guardar cambios*/
    private void jBGuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBGuaKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBGuaKeyPressed

    
    /*Cuando el mouse entra en el botón de baja*/
    private void jBBajMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBajMouseEntered
 
        /*Cambia el color del fondo del botón*/
        jBBaj.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBBajMouseEntered

    
    /*Cuando el mouse sale del botón de baja*/
    private void jBBajMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBajMouseExited
 
        /*Cambia el color del fondo del botón*/
        jBBaj.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBBajMouseExited

    
    /*Cuando se presiona el botón de baja de producto*/
    private void jBBajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBajActionPerformed
        
        /*Si no se a seleccionado nada en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona por lo menos un activo de la lista para dar de baja.", "Baja activo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }
        
        /*Preguntar al usuario si esta seguro de que querer dar de baja el activo*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres dar de baja el(los) activo(s)?", "Baja activo(s)", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
        if(iRes==JOptionPane.NO_OPTION || iRes==JOptionPane.CLOSED_OPTION)
            return;                       
                
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Obtiene la fecha de baja*/
        Date fe                 =  jDtFBaj.getDate();
        SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");
        String sFBaj            = sdf.format(fe);      

        //Declara variables de la base de datos    
        Statement   st;                
        String      sQ;
        
        /*Actualiza el activo sus datos de baja*/
        try 
        {                
            sQ = "UPDATE actfij SET "                    
                    + "baj          = 1, "
                    + "fmod         = NOW(), "
                    + "costsal      =  " + jTValAct.getText().trim().replace("$", "").replace(",", "") + ", "
                    + "totacumes    =  " + jTDepMens.getText().trim().replace("$", "").replace(",", "") + ", "
                    + "totmesbaj    =  " + jTMesDep.getText().trim() + ", "
                    + "totestadbaj  =  '" + jTDepSis2.getText().trim() + "', "
                    + "totvalactbaj =  " + jTValAct.getText().trim().replace("$", "").replace(",", "") + ", "
                    + "fbaj         = '" + sFBaj + "' "
                    + "WHERE id_id = " + jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                                                                            
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

        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Borra los items de la tabla*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();
        te.setRowCount(0);
        
        /*Obtiene todos los activos de la base de datos y cargalos en la tabla nuevamente*/
        (new Thread()
        {
            @Override
            public void run()
            {
                vCargAct();
            }
            
        }).start();
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Activo(s) dado(s) de baja con éxito.", "Activo fijo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBBajActionPerformed

    
    /*Cuando se presiona una tecla en el botón de baja*/
    private void jBBajKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBBajKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBBajKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de deducción*/
    private void jTDeduFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDeduFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDedu.setSelectionStart(0);jTDedu.setSelectionEnd(jTDedu.getText().length());        
        
    }//GEN-LAST:event_jTDeduFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la deducción*/
    private void jTDeduFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDeduFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDedu.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDedu.getText().compareTo("")!=0)
            jTDedu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
        /*Si no se puede parsear a doble entonces*/
        double dVal;
        try
        {
            dVal    = Double.parseDouble(jTDedu.getText().trim());
        }
        catch(NumberFormatException expnNumForm)
        {
            /*Coloca 0.0 y regresa*/
            jTDedu.setText("0.0");                                    
            return;
        }
        
        /*Coloca el nuevo valor parseado a double*/
        jTDedu.setText(Double.toString(dVal));        
                
    }//GEN-LAST:event_jTDeduFocusLost

    
    /*Cuando se presiona una tecla en el campo de dedusible*/
    private void jTDeduKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDeduKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDeduKeyPressed

    
    /*Cuando se tipea una tecla en el campo de deducible*/
    private void jTDeduKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDeduKeyTyped
 
        /*Comprueba que el carácter este en los límites permitidos para numeración*/
        if(((evt.getKeyChar() < '0') || (evt.getKeyChar() > '9')) && (evt.getKeyChar() != '\b') && (evt.getKeyChar() != '.')) 
            evt.consume();
        
    }//GEN-LAST:event_jTDeduKeyTyped

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta de depreciación*/
    private void jTCtaDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaDepFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCtaDep.setSelectionStart(0);jTCtaDep.setSelectionEnd(jTCtaDep.getText().length());
        
    }//GEN-LAST:event_jTCtaDepFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta de depreciación*/
    private void jTCtaDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaDepFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCtaDep.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCtaDep.getText().compareTo("")!=0)
            jTCtaDep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTCtaDepFocusLost

    
    /*Cuando se presiona una tecla en el campo de la cuenta de depósito*/
    private void jTCtaDepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaDepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaDepKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta de gasto*/
    private void jTCtaGastFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaGastFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCtaGast.setSelectionStart(0);jTCtaGast.setSelectionEnd(jTCtaGast.getText().length());
        
    }//GEN-LAST:event_jTCtaGastFocusGained

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta de gasto*/
    private void jTCtaGastFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaGastFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCtaGast.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCtaGast.getText().compareTo("")!=0)
            jTCtaGast.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTCtaGastFocusLost

    
    /*Cuando se presiona una tecal en el campo de la cuenta de gasto*/
    private void jTCtaGastKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaGastKeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaGastKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta de deducible*/
    private void jTCtaDeduFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaDeduFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCtaDedu.setSelectionStart(0);jTCtaDedu.setSelectionEnd(jTCtaDedu.getText().length());
        
    }//GEN-LAST:event_jTCtaDeduFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta deducible*/
    private void jTCtaDeduFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaDeduFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCtaDedu.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCtaDedu.getText().compareTo("")!=0)
            jTCtaDedu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTCtaDeduFocusLost

    
    /*Cuando se presiona una tecla en el cmapo de la cuenta de deducible*/
    private void jTCtaDeduKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaDeduKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaDeduKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta de depreciación 2*/
    private void jTCtaDep2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaDep2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCtaDep2.setSelectionStart(0);jTCtaDep2.setSelectionEnd(jTCtaDep2.getText().length());
        
    }//GEN-LAST:event_jTCtaDep2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta de depreciación 2*/
    private void jTCtaDep2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaDep2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTCtaDep2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCtaDep2.getText().compareTo("")!=0)
            jTCtaDep2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTCtaDep2FocusLost

    
    /*Cuando se presiona una tecla en el campo de la cuenta de depreciación 2*/
    private void jTCtaDep2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaDep2KeyPressed
 
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaDep2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta de gastos 2*/
    private void jTCtaGast2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaGast2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTCtaGast2.setSelectionStart(0);jTCtaGast2.setSelectionEnd(jTCtaGast2.getText().length());
        
    }//GEN-LAST:event_jTCtaGast2FocusGained

    
    /*Cuando se presiona una tecla en el campo de la cuenta de gastos 2*/
    private void jTCtaGast2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaGast2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCtaGast2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCtaGast2.getText().compareTo("")!=0)
            jTCtaGast2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTCtaGast2FocusLost

    
    /*Cuando se presiona una tecla en el campo de la cuenta de gastos 2*/
    private void jTCtaGast2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaGast2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaGast2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de la cuenta de deducibles 2*/
    private void jTCtaDedu2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaDedu2FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTCtaDedu2.setSelectionStart(0);jTCtaDedu2.setSelectionEnd(jTCtaDedu2.getText().length());
        
    }//GEN-LAST:event_jTCtaDedu2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la cuenta de deducibles 2*/
    private void jTCtaDedu2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTCtaDedu2FocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTCtaDedu2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTCtaDedu2.getText().compareTo("")!=0)
            jTCtaDedu2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTCtaDedu2FocusLost

    
    /*Cuando se presiona una tecla en el campo de la cuenta de deducibles 2*/
    private void jTCtaDedu2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCtaDedu2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTCtaDedu2KeyPressed

    
    /*Cuando se gana el foco del teclado en el cmapo de la deducción 2*/
    private void jTDedu2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDedu2FocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDedu2.setSelectionStart(0);jTDedu2.setSelectionEnd(jTDedu2.getText().length());
        
    }//GEN-LAST:event_jTDedu2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la deducción 2*/
    private void jTDedu2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDedu2FocusLost
 
        /*Coloca el caret en la posiciòn 0*/
        jTDedu2.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDedu2.getText().compareTo("")!=0)
            jTDedu2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTDedu2FocusLost

    
    /*Cuando se presiona una tecla en el campo de las deducciones 2*/
    private void jTDedu2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDedu2KeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTDedu2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del deducible mensual*/
    private void jTDeduMensFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDeduMensFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTDeduMens.setSelectionStart(0);jTDeduMens.setSelectionEnd(jTDeduMens.getText().length());
        
    }//GEN-LAST:event_jTDeduMensFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del deducible mensual*/    
    private void jTDeduMensFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTDeduMensFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTDeduMens.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTDeduMens.getText().compareTo("")!=0)
            jTDeduMens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTDeduMensFocusLost

    private void jTDeduMensKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTDeduMensKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTDeduMensKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del valor actual*/
    private void jTValActFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTValActFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTValAct.setSelectionStart(0);jTValAct.setSelectionEnd(jTValAct.getText().length());
        
    }//GEN-LAST:event_jTValActFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del valor actual*/
    private void jTValActFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTValActFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTValAct.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTValAct.getText().compareTo("")!=0)
            jTValAct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTValActFocusLost

    
    /*Cuando se presiona una tecla en el campo del valor actual*/
    private void jTValActKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTValActKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTValActKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del mes depreciado*/
    private void jTMesDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMesDepFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTMesDep.setSelectionStart(0);jTMesDep.setSelectionEnd(jTMesDep.getText().length());
        
    }//GEN-LAST:event_jTMesDepFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de los meses de preciados*/
    private void jTMesDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTMesDepFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTMesDep.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTMesDep.getText().compareTo("")!=0)
            jTMesDep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTMesDepFocusLost

    
    /*Cuando se presiona una tecal en el campo de meses depresiados*/
    private void jTMesDepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMesDepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTMesDepKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo de diferencia en meses de fin de depreciación*/
    private void jTFinDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFinDepFocusGained
        
        /*Selecciona todo el texto cuando gana el foco*/
        jTFinDep.setSelectionStart(0);jTFinDep.setSelectionEnd(jTFinDep.getText().length());
        
    }//GEN-LAST:event_jTFinDepFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo de la diferencia de meses de depreciación*/
    private void jTFinDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTFinDepFocusLost
        
        /*Coloca el caret en la posiciòn 0*/
        jTFinDep.setCaretPosition(0);
        
        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/                               
        if(jTFinDep.getText().compareTo("")!=0)
            jTFinDep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));                
        
    }//GEN-LAST:event_jTFinDepFocusLost

    
    //Cuando se presiona una tecla en el campo de fin de depreciaciòn
    private void jTFinDepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFinDepKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jTFinDepKeyPressed

    
    //Cuando se pierde el foco del teclado en el combo del almacèn 2
    private void jComAlma2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComAlma2FocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComAlma.getSelectedItem().toString().compareTo("")!=0)
            jComAlma.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));
        
    }//GEN-LAST:event_jComAlma2FocusLost

    
    /*Cuando sucede una acción en el combo de almacenes*/
    private void jComAlma2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComAlma2ActionPerformed

        /*Si no hay datos entonces regresa*/
        if(jComAlma2.getSelectedItem()==null)
            return;
        
        //Abre la base de datos                             
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        //Coloca en el tooltipo la descripción
        String sDescrip = Star.sGetDescripCamp(con, "almadescrip", "almas", "WHERE alma = '" + jComAlma2.getSelectedItem().toString() + "'");
        
        //Si hubo error entonces regresa
        if(sDescrip==null)
            return;
        
        //Coloca la descripción en el control
        jComAlma2.setToolTipText(sDescrip);
        
        //Cierra la base de datos
        Star.iCierrBas(con);
        
    }//GEN-LAST:event_jComAlma2ActionPerformed

    
    /*Cuando e presiona una tecla en el combo e almacen 2*/
    private void jComAlma2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComAlma2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jComAlma2KeyPressed

    
    /*Cuando el mouse entra en el botón de traspasar a almacén*/
    private void jBTransMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTransMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBTrans.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBTransMouseEntered

    
    /*Cuando el mouse sale del botón de traspasar almacén*/
    private void jBTransMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBTransMouseExited
 
        /*Cambia el color del fondo del botón*/
        jBTrans.setBackground(Star.colOri);
        
    }//GEN-LAST:event_jBTransMouseExited

    
    /*Cuando se presiona el botón de transpasar a almacén*/
    private void jBTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTransActionPerformed
        
        /*Si no se a seleccionado nada en la tabla entonces*/
        if(jTab.getSelectedRow()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un activo de la lista para hacer el traspaso.", "Traspaso activo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jTab.grabFocus();
            return;
        }
        
        /*Si no se a seleccionado un alamcén de salida entonces*/
        if(jComAlma2.getSelectedItem().toString().trim().compareTo("")==0)      
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona un almacén de traspaso.", "Traspaso activo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
            
            /*Coloca el borde rojo*/                               
            jComAlma2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            
            /*Pon el foco del teclado en la tabla y regresa*/
            jComAlma2.grabFocus();
            return;
        }
        
        /*Preguntar al usuario si esta seguro de que querer hacer el traspso del activo*/
        Object[] op = {"Si","No"};
        int iRes    = JOptionPane.showOptionDialog(this, "¿Seguro que quieres traspasar el activo?", "Traspaso activo", JOptionPane.YES_NO_OPTION,  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconDu)), op, op[0]);
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
        
        /*Si tiene una serie entonces*/
        if(jTSer2.getText().trim().compareTo("")!=0)
        {
            /*Actualiza el almacén de esa serie para que pase a ser de activos fijos*/
            try 
            {            
                sQ = "UPDATE serieprod SET "
                        + "alma             = '" + jComAlma2.getSelectedItem().toString().trim() + "' "
                        + "WHERE ser        = '" + jTSer2.getText().trim() + "'";
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                return;
             }
            
        }/*Fin de if(jTSer.getText().trim().compareTo("")!=0)*/
        
        /*Contiene la unidad del producto*/
        String sUnid    = "";
        System.out.println(jTProd2.getText().trim());
        /*Obtiene la unidad actual del producto*/        
        ResultSet rs;
        try
        {
            sQ = "SELECT unid FROM prods WHERE prod = '" + jTProd2.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sUnid   = rs.getString("unid");                                   
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        /*Registra el traspaso entre los almacenes*/
        try 
        {            
            sQ = "INSERT INTO traspas(      prod,                                                           alma,                      concep,    unid,    cantsal,        almaa,                                                                  cantent,    estac,                                   falt,           sucu,                                     nocaj) " + 
                            "VALUES('" +    jTProd.getText().replace("'", "''").trim() + "',        '" +    Star.strAlmaActFij + "',   'TRAS','"+sUnid+"', 1 ,'" +          jComAlma2.getSelectedItem().toString().replace("'", "''").trim() + "',  1, '" +     Login.sUsrG.replace("'", "''") + "',     now(), '" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        
        /*Obtiene el último ID de moniven*/
        String sId  = "0";
        try
        {
            sQ = "SELECT id_id FROM moninven ORDER BY id_id DESC LIMIT 1";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sId      = rs.getString("id_id");                                               
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        //Inserta la compra en la base de datos que sirve para ingresar el costeo
        if(Star.iInsComprs(con, "II" + sId, "", "", "", "", "now()", "0", "0", "0", "'CO'", "now()", "", "", "now()", "INV", "now()","","","","0","", "0", "0")==-1)
            return;
        System.out.println("yes");
        //Declara variables locales
        String sExistG              = "";
        String sCostU               = "";

        /*Obtiene algunos datos del producto para el costo promedio*/                
        try
        {
            sQ = "SELECT (SELECT IFNULL(cost,0) FROM partcomprs LEFT OUTER JOIN comprs ON comprs.CODCOMP = partcomprs.CODCOM WHERE comprs.ESTADO IN('DEVP','CO') AND (partcomprs.CANT - partcomprs.DEVS) > 0 AND prod = '" + jTProd.getText().trim() + "' ORDER BY partcomprs.ID_ID DESC LIMIT 1) AS ultcost, IFNULL(SUM(exist),0) AS exist FROM prods WHERE prod = '" + jTProd.getText().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Obtiene el último costo*/
                sCostU  = rs.getString("ultcost");

                /*Si el último costo es cadena vacia entonces*/
                if(sCostU==null || sCostU.compareTo("")==0)
                    sCostU  = "0";

                /*Obtiene la existencia general*/
                sExistG = rs.getString("exist");
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }
        /*Si el último costo es 0 entonces dejarlo como el costo actual*/
        String sCostP;
        if(Double.parseDouble(sCostU)==0)
            sCostP      = jTValAct.getText().trim().replace("$", "").replace(",", "");
        else
            sCostP      = Double.toString(((Double.parseDouble(sExistG) * Double.parseDouble(sCostU)) + (Double.parseDouble("1") * Double.parseDouble(jTCost.getText().trim().replace("$", "").replace(",", "")))) / (Double.parseDouble(sExistG) + Double.parseDouble("1")));

        /*Inserta las partidas de las compras*/
        try
        {
            sQ = "INSERT INTO partcomprs(codcom,                  prod,                                            cant,   unid,             descrip,                                                   cost,                                                             descu,    descad,     codimpue,   mon,    impo,    falt,      recib,        alma,                                                                       costpro,                                      lot,       pedimen,    flotvenc,   cantlotpend,    serprod,    comenser,   garan,  tipcam,     eskit) " +
                            "VALUES(     'II" + sId + "','"  +    jTProd2.getText().replace("'", "''").trim() + "',1,'" +  sUnid + "','" +   jTDescrip2.getText().replace("'", "''").trim() + "','" +   jTValAct.getText().trim().replace("$", "").replace(",", "") + "', 0,        0,          '',         '',     0,       now(),     1,    '" +    jComAlma2.getSelectedItem().toString().replace("'", "''").trim() + "', " +  sCostP.replace("$", "").replace(",", "") + ", '',        '',         now(),      0,              '',         '',         '',     0,          0)";
            st = con.createStatement();
            st.executeUpdate(sQ);
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        /*Realiza la afectación correspondiente al almacén para la salida*/
        if(Star.iAfecExisProd(con, jTab.getValueAt(jTab.getSelectedRow(), 1).toString().trim(), Star.strAlmaActFij, "1", "-")==-1)
            return;

        /*Registra el producto que se esta sacando del inventario en la tabla de monitor de inventarios*/
        if(!Star.vRegMoniInv(con, jTab.getValueAt(jTab.getSelectedRow(), 1).toString().trim(), "1", jTDescrip.getText().trim(), Star.strAlmaActFij, Login.sUsrG , "", "TRAS", sUnid, "", "", "1"))                                
            return;      

        /*Realiza la afectación correspondiente al almacén para la entrada*/
        if(Star.iAfecExisProd(con, jTab.getValueAt(jTab.getSelectedRow(), 1).toString().trim(), jComAlma2.getSelectedItem().toString().trim(), "1", "+")==-1)
            return;

        /*Registra el producto que se esta ingresando al inventario en la tabla de monitor de inventarios*/
        if(!Star.vRegMoniInv(con, jTab.getValueAt(jTab.getSelectedRow(), 1).toString().trim(), "1", jTDescrip.getText().trim(), jComAlma2.getSelectedItem().toString().trim(), Login.sUsrG , "", "TRAS", sUnid, "", "", "0"))                                
            return;      

        /*Obtiene la fecha de baja*/
        Date fe                 =  jDtFBaj.getDate();
        SimpleDateFormat sdf    = new SimpleDateFormat("yyy-MM-dd");
        String sFBaj            = sdf.format(fe);      
        
        /*Actualiza el activo sus datos de baja*/
        try 
        {                
            sQ = "UPDATE actfij SET "                    
                    + "baj          = 1, "
                    + "fmod         = NOW(), "
                    + "costsal      =  " + jTValAct.getText().trim().replace("$", "").replace(",", "") + ", "
                    + "totacumes    =  " + jTDepMens.getText().trim().replace("$", "").replace(",", "") + ", "
                    + "totmesbaj    =  " + jTMesDep.getText().trim() + ", "
                    + "totestadbaj  =  '" + jTDepSis2.getText().trim() + "', "
                    + "totvalactbaj =  " + jTValAct.getText().trim().replace("$", "").replace(",", "") + ", "
                    + "fbaj         = '" + sFBaj + "' "
                    + "WHERE id_id = " + jTab.getValueAt(jTab.getSelectedRow(), 4).toString();                                                                                                            
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

        /*Resetea el contador de filas*/
        iContFi = 1;
        
        /*Borra los items de la tabla*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();
        te.setRowCount(0);
        
        /*Obtiene todos los activos de la base de datos y cargalos en la tabla nuevamente*/
        (new Thread()
        {
            @Override
            public void run()
            {
                vCargAct();
            }
            
        }).start();
        
        /*Mensajea de éxito*/
        JOptionPane.showMessageDialog(null, "Traspaso de activo terminado con éxito.", "Activo fijo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));
        
    }//GEN-LAST:event_jBTransActionPerformed

    
    /*Cuando se presiona una tecla en el botón de traspasar a otro almacén*/
    private void jBTransKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBTransKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);
        
    }//GEN-LAST:event_jBTransKeyPressed

    
    /*Cuando se presiona una tecla en el campo de la fecha de baja*/
    private void jDtFBajKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDtFBajKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jDtFBajKeyPressed

    
    /*Cuando se pierde el foco del teclado en el campo del combo del lugar*/
    private void jComLugFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComLugFocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComLug.getSelectedItem().toString().compareTo("")!=0)
            jComLug.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComLugFocusLost

    
    /*Cuando sucede una acción en el combp de lugares*/
    private void jComLugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComLugActionPerformed

        /*Si no hay selección entonces regresa*/
        if(jComLug.getSelectedItem()==null)
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

        /*Obtén la descripción del lugar de la base de datos*/
        try
        {
            sQ = "SELECT descrip FROM lugs WHERE COD = '" + jComLug.getSelectedItem().toString().trim() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el control y el caret al principio*/
                jTLug.setText(rs.getString("descrip"));
                jTLug.setCaretPosition(0);
            }
            else
                jTLug.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        //Cierra la base de datos
        Star.iCierrBas(con);                    

    }//GEN-LAST:event_jComLugActionPerformed

    
    /*Cuando se presiona una tecla en el combo de los lugares*/
    private void jComLugKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComLugKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComLugKeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del lugar*/
    private void jTLugFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLugFocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTLug.setSelectionStart(0);jTLug.setSelectionEnd(jTLug.getText().length());

    }//GEN-LAST:event_jTLugFocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del lugar*/
    private void jTLugFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLugFocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTLug.setCaretPosition(0);

    }//GEN-LAST:event_jTLugFocusLost

    
    /*Cuando se presiona una tecla en el campo del lugar*/
    private void jTLugKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLugKeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTLugKeyPressed

    
    /*Cuando se pierde el foco el teclado en el campo del lugar 2*/
    private void jComLug2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComLug2FocusLost

        /*Coloca el borde negro si tiene datos, caso contrario de rojo*/
        if(jComLug.getSelectedItem().toString().compareTo("")!=0)
            jComLug.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204,204,255)));

    }//GEN-LAST:event_jComLug2FocusLost

    
    /*Cuando sucede una acción en el combo de lugar 2*/
    private void jComLug2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComLug2ActionPerformed

        /*Si no hay selección entonces regresa*/
        if(jComLug2.getSelectedItem()==null)
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

        /*Obtén la descripción del lugar de la base de datos*/
        try
        {
            sQ = "SELECT descrip FROM lugs WHERE COD = '" + jComLug2.getSelectedItem().toString() + "'";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {
                /*Coloca el valor en el control y el caret al principio*/
                jTLug2.setText(rs.getString("descrip"));
                jTLug2.setCaretPosition(0);
            }
            else
                jTLug2.setText("");
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;
        }

        //Cierra la base de datos
        Star.iCierrBas(con);                    

    }//GEN-LAST:event_jComLug2ActionPerformed

    
    /*Cuando se presiona una tecla en el combo de lugares*/
    private void jComLug2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComLug2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jComLug2KeyPressed

    
    /*Cuando se gana el foco del teclado en el campo del lugar 2*/
    private void jTLug2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLug2FocusGained

        /*Selecciona todo el texto cuando gana el foco*/
        jTLug2.setSelectionStart(0);jTLug2.setSelectionEnd(jTLug2.getText().length());

    }//GEN-LAST:event_jTLug2FocusGained

    
    /*Cuando se pierde el foco del teclado en el campo del lugar*/
    private void jTLug2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTLug2FocusLost

        /*Coloca el caret en la posiciòn 0*/
        jTLug2.setCaretPosition(0);

    }//GEN-LAST:event_jTLug2FocusLost

    
    /*Cuando se presiona una teclado en el campo del lugar 2*/
    private void jTLug2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTLug2KeyPressed

        //Llama a la función escalable
        vKeyPreEsc(evt);

    }//GEN-LAST:event_jTLug2KeyPressed
   
    
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape cerrar presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Else if se presiona Alt + T entonces presiona el botón de marcar todo*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_T)
            jBTod.doClick();
        /*Si se presiona CTRL + G entonces presiona el botón de guardar cambios*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_G)
            jBGua.doClick();
        /*Si se presiona CTRL + N entonces presiona el botón de nuevo*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N)
            jBNew.doClick();
        /*Si se presiona F3 presiona el botón de bùscar*/
        else if(evt.getKeyCode() == KeyEvent.VK_F3)
            jBBusc.doClick();
        /*Else if se presiona Alt + F4 entonces presiona el botón de salir*/
        else if(evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4)
            jBSal.doClick();
        /*Si se presiona F4 presiona el botón de mostrar todo*/
        else if(evt.getKeyCode() == KeyEvent.VK_F4)
            jBMosT.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBaj;
    private javax.swing.JButton jBBusc;
    private javax.swing.JButton jBExisAlma;
    private javax.swing.JButton jBGranDescrip;
    private javax.swing.JButton jBGranDescrip2;
    private javax.swing.JButton jBGua;
    private javax.swing.JButton jBMosT;
    private javax.swing.JButton jBNew;
    private javax.swing.JButton jBProd;
    private javax.swing.JButton jBSal;
    private javax.swing.JButton jBTab1;
    private javax.swing.JButton jBTod;
    private javax.swing.JButton jBTrans;
    private javax.swing.JComboBox jComAct;
    private javax.swing.JComboBox jComAlma;
    private javax.swing.JComboBox jComAlma2;
    private javax.swing.JComboBox jComConcep;
    private javax.swing.JComboBox jComConcep2;
    private javax.swing.JComboBox jComLug;
    private javax.swing.JComboBox jComLug2;
    private com.toedter.calendar.JDateChooser jDFAdq;
    private com.toedter.calendar.JDateChooser jDFFinDep;
    private com.toedter.calendar.JDateChooser jDFIniDep;
    private com.toedter.calendar.JDateChooser jDtFBaj;
    private javax.swing.JLabel jLAyu;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
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
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jP1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTAComen;
    private javax.swing.JTextArea jTAComen2;
    private javax.swing.JTextField jTAct;
    private javax.swing.JTextField jTAct2;
    private javax.swing.JTextField jTBusc;
    private javax.swing.JTextField jTCost;
    private javax.swing.JTextField jTCost2;
    private javax.swing.JTextField jTCtaDedu;
    private javax.swing.JTextField jTCtaDedu2;
    private javax.swing.JTextField jTCtaDep;
    private javax.swing.JTextField jTCtaDep2;
    private javax.swing.JTextField jTCtaGast;
    private javax.swing.JTextField jTCtaGast2;
    private javax.swing.JTextField jTDedu;
    private javax.swing.JTextField jTDedu2;
    private javax.swing.JTextField jTDeduMens;
    private javax.swing.JTextField jTDep2;
    private javax.swing.JTextField jTDepMens;
    private javax.swing.JTextField jTDepSis2;
    private javax.swing.JTextField jTDescrip;
    private javax.swing.JTextField jTDescrip2;
    private javax.swing.JTextField jTDescripAlma;
    private javax.swing.JTextField jTEstad;
    private javax.swing.JTextField jTFAdq2;
    private javax.swing.JTextField jTFBajDep2;
    private javax.swing.JTextField jTFIniDep2;
    private javax.swing.JTextField jTFinDep;
    private javax.swing.JTextField jTLug;
    private javax.swing.JTextField jTLug2;
    private javax.swing.JTextField jTMesDep;
    private javax.swing.JTextField jTMese;
    private javax.swing.JTextField jTPorDepre;
    private javax.swing.JTextField jTProd;
    private javax.swing.JTextField jTProd2;
    private javax.swing.JTextField jTSer;
    private javax.swing.JTextField jTSer2;
    private javax.swing.JTextField jTTipAct2;
    private javax.swing.JTextField jTTotDep2;
    private javax.swing.JTextField jTValAct;
    private javax.swing.JTable jTab;
    // End of variables declaration//GEN-END:variables

}/*Fin de public class Clientes extends javax.swing.JFrame */
