//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;




/*Clase para imprimir*/
public class ImprDial extends javax.swing.JFrame
{    
    /*Contiene el color original del botón*/
    private final java.awt.Color    colOri;
    
    /*Contiene el arreglo de enteros*/
    private final int               iAVtas[];
    
    /*Contiene cadenas de caracteres*/
    private final String[]          sStri;
    
    /*Contiene las rutas*/
    private final String            sRutTik, sRutFac, sTipImp, sRutNot;
    
    
    
    
    /*Constructor sin argumentos*/
    public ImprDial(int iAVta[], String sRutT, String sRutF, String sRutN, String sTipIm, String[] sStrin)     
    {
        /*Inicializa los componentes gráficos*/
        initComponents();
    
        /*Establece el botón por default*/
        this.getRootPane().setDefaultButton(jBImp);
        
        /*Obtiene el color original que deben tener los botones*/
        colOri  = jBSal.getBackground();
        
        /*Recibe los parámetros del otro formulario*/
        iAVtas  = iAVta;
        sRutTik = sRutT;
        sRutFac = sRutF;
        sRutNot = sRutN;
        sTipImp = sTipIm;
        sStri   = sStrin;
        
        /*Establece el título de la ventana*/
        this.setTitle("Selecciona una impresora");
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);                

        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Crea la impresión*/
        DocFlavor flavor                = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
        PrintRequestAttributeSet patts  = new HashPrintRequestAttributeSet();
        PrintService[] ps               = PrintServiceLookup.lookupPrintServices(flavor, patts);
        
        /*Si no se encontrarón impresoras entonces*/
        if(ps.length == 0) 
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No se encontrarón impresoras.", "Imprimir", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            return;
        }
        
       /*Recorre todas las impresoras obtenidas y agregalas a la lista*/
        DefaultListModel lm = new DefaultListModel();
        for(PrintService p : ps) 
        {
            /*Agrega la impresora a la lista*/
            lm.addElement(p.getName());
        }
        jLImp.setModel(lm);
                
    }/*Fin de public Loadin(String sRu, JTable jTabPro, int iContF)*/

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jLImp = new javax.swing.JList();
        jBImp = new javax.swing.JButton();
        jBSal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLImp.setNextFocusableComponent(jBImp);
        jLImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLImpKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jLImp);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 120));

        jBImp.setBackground(new java.awt.Color(255, 255, 255));
        jBImp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBImp.setForeground(new java.awt.Color(0, 102, 0));
        jBImp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/impres.png"))); // NOI18N
        jBImp.setText("Imprimir");
        jBImp.setToolTipText("Imprimir  (Ctrl+P)");
        jBImp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBImp.setNextFocusableComponent(jBSal);
        jBImp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBImpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBImpMouseExited(evt);
            }
        });
        jBImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImpActionPerformed(evt);
            }
        });
        jBImp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBImpKeyPressed(evt);
            }
        });
        getContentPane().add(jBImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 130, 30));

        jBSal.setBackground(new java.awt.Color(255, 255, 255));
        jBSal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jBSal.setForeground(new java.awt.Color(0, 102, 0));
        jBSal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/sal.png"))); // NOI18N
        jBSal.setText("Salir");
        jBSal.setToolTipText("Salir (ESC)");
        jBSal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBSal.setNextFocusableComponent(jLImp);
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
        getContentPane().add(jBSal, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 130, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents
             
    
    /*Cuando se presiona una tecla en la lista*/
    private void jLImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLImpKeyPressed
       
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jLImpKeyPressed

    
    /*Cuando se presiona una tecla en el botón de imprimir*/
    private void jBImpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBImpKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBImpKeyPressed

    
    /*Cuando se presiona una tecla en el botón de salir*/
    private void jBSalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBSalKeyPressed
        
        //Llama a la función escalable
        vKeyPreEsc(evt);        
        
    }//GEN-LAST:event_jBSalKeyPressed

    
    /*Cuando se presiona el botón de salir*/
    private void jBSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalActionPerformed
        
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        dispose();
        
    }//GEN-LAST:event_jBSalActionPerformed

    
    /*Cuando se presiona el botón de imprimir*/
    private void jBImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImpActionPerformed
        
        /*Si no a seleccionado una impresora de la lista entonces*/
        if(jLImp.getSelectedIndex()==-1)
        {
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "Selecciona una impresora.", "Imprimir", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
            
            /*Coloca el foco del teclado en el control y regresa*/
            jLImp.grabFocus();
            return;
        }
        
        /*Crea la impresión*/
        DocFlavor flavor                = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
        PrintRequestAttributeSet patts  = new HashPrintRequestAttributeSet();
        PrintService[] ps               = PrintServiceLookup.lookupPrintServices(flavor, patts);
        
        /*Selecciona la impresora en el servicio*/
        PrintService myService = null;
        for (PrintService printService : ps) 
        {
            if(printService.getName().equals(jLImp.getSelectedValue().toString())) 
            {
                myService = printService;
                break;
            }
        }
        
        /*Si no existe la impresora entonces*/
        if(myService == null) 
        {
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "No existe la impresora.", "Imprimir", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd))); 
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

        /*Si se va a imprimir una factura, nota de crédito o un ticket entonces*/
        if(sTipImp.compareTo("vta")==0)
        {
            /*Recorre todos los elementos del arreglo*/       
            for(int x = 0; x < iAVtas.length; x++)
            {
                /*Declara varibles locales*/
                String sFol     = "";
                String sTipDoc  = "";
                String sSer     = "";
                
                /*Obtiene algunos datos de la vta*/                                
                try
                {
                    sQ = "SELECT norefer, noser, tipdoc FROM vtas WHERE vta = " + iAVtas[x];
                    st = con.createStatement();
                    rs = st.executeQuery(sQ);
                    /*Si hay datos entonces obtiene los resultados*/
                    if(rs.next())
                    {                                               
                        sFol    = rs.getString("norefer");                                                
                        sSer    = rs.getString("noser");
                        sTipDoc = rs.getString("tipdoc");
                    }
                }
                catch(SQLException expnSQL)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                    return;                                                                                    
                }

                //Obtiene el RFC de la empresa local
                String sRFCLoc  = Star.sGetRFCLoc(con);
                
                //Si hubo error entonces regresa
                if(sRFCLoc==null)
                    return;
                
                /*Completa la ruta dependiente el documento que es*/
                String sRut = "";
                if(sTipDoc.compareTo("TIK")==0)
                    sRut    = sRutTik + "\\" + sSer + "-" + sFol + ".pdf";
                /*Else, completa la ruta para factura*/
                else if(sTipDoc.compareTo("FAC")==0)
                    sRut    = sRutFac + "\\CFDI-" + sRFCLoc + "-" + sSer + "-" + sFol + ".pdf";
                else if(sTipDoc.compareTo("NOTC")==0)
                    sRut    = sRutNot + "\\CFDI-" + sRFCLoc + "-" + sSer + "-" + sFol + ".pdf";
                else if(sTipDoc.compareTo("NOTP")==0)
                    sRut    = sRutNot + "\\CFDI-" + sRFCLoc + "-" + sSer + "-" + sFol + ".pdf";
               
                /*Crea el flujo para el archivo*/
                FileInputStream fis;
                try
                {
                    fis = new FileInputStream(sRut);
                }
                catch(FileNotFoundException expnFilNotFoun)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnFilNotFoun.getMessage(), Star.sErrFilNotFoun, expnFilNotFoun.getStackTrace(), con);                                                       
                    return;                                                                                   
                }        

                /*Crea el trabajo de impresión*/
                Doc pdfDoc              = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
                DocPrintJob printJob    = myService.createPrintJob();

                /*Imprime el documento*/
                try
                {
                    printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
                }
                catch(PrintException expnPrint)
                {
                    /*Manda la forma para atras*/
                    this.setAlwaysOnTop(false);
                    
                    //Procesa el error
                    if(Star.iErrProc(this.getClass().getName() + " " + expnPrint.getMessage(), Star.sErrPrint, expnPrint.getStackTrace(), con)==-1)                                                       
                        return;                                        
                    
                    /*Manda la forma adelante y regresa*/
                    this.setAlwaysOnTop(true);
                    return;
                }

                /*Cierra el archivo*/
                try
                {
                    fis.close();   
                }
                catch(IOException expnIO)
                {
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                   
                    return;                                                                            
                }            
                
            }/*Fin de for(int x = 0; x < iAVtas.length; x++)*/

            //Cierra la base de datos
            Star.iCierrBas(con);                
                                    
        }/*Fin de if(sTipImp.compareTo("vta")==0)*/                            
        /*Else if se quiere imprimir una compra u órden de compra entonces*/
        else if(sTipImp.compareTo("compr")==0)
        {
            /*Declara variables final para el thread*/            
            final PrintService myServiceFi      = myService;
            
            /*Thread que controla toda esta parte para hacer mas eficiente el programa*/
            (new  Thread()
            {
                @Override
                public void run()
                {                    
                    //Abre la base de datos                             
                    Connection  con = Star.conAbrBas(true, false);

                    //Si hubo error entonces regresa
                    if(con==null)
                        return;

                    //Declara variables locales
                    String  sNomLoc             = "";
                    String  sCallLoc            = "";
                    String  sTelLoc             = "";
                    String  sColLoc             = "";
                    String  sCPLoc              = "";
                    String  sCiuLoc             = "";
                    String  sEstLoc             = "";
                    String  sPaiLoc             = "";                    

                    //Declara variables de la base de datos
                    Statement   st;
                    ResultSet   rs;                                        
                    String      sQ; 
        
                    /*Obtiene todos los datos del cliente local*/
                    try
                    {                  
                        sQ = "SELECT nom, calle, tel, col, cp, ciu, estad, pai FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";
                        st = con.createStatement();
                        rs = st.executeQuery(sQ);
                        /*Si hay datos*/
                        if(rs.next())
                        {
                            /*Obtiene todos los datos de la consulta*/
                            sNomLoc             = rs.getString("nom");                                    
                            sCallLoc            = rs.getString("calle");                                    
                            sTelLoc             = rs.getString("tel");                                    
                            sColLoc             = rs.getString("col");                                    
                            sCPLoc              = rs.getString("cp");                                    
                            sCiuLoc             = rs.getString("ciu");                                    
                            sEstLoc             = rs.getString("estad");                                    
                            sPaiLoc             = rs.getString("pai");                                                                

                        }/*Fin de while (rs.next())*/
                    }
                    catch(SQLException expnSQL)
                    {
                        //Procesa el error y regresa
                        Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                        return;                                                               
                    }

                    /*Recorre todos los elementos*/
                    for(int x = 0; x < sStri.length; x++)
                    {
                        /*Guarda el código de la compra*/
                        String sCodCom  = sStri[x];

                        //Declara variables locales
                        String sProv    = "";
                        String sSubTot  = "";
                        String sImp     = "";
                        String sTot     = "";
                        String sTip     = "";
                        String sFDoc    = "";
                        String sNoDoc   = "";                
                        String sNomb    = "";                        
                        
                        /*Obtiene algunos datos de la compra*/                        
                        try
                        {
                            sQ = "SELECT comprs.TIP, provs.NOM, nodoc, comprs.PROV, subtot, impue, tot, comprs.FALT FROM comprs LEFT OUTER JOIN provs ON CONCAT_WS('', provs.SER,provs.PROV ) = comprs.PROV WHERE codcomp = '" + sCodCom + "'";                                                    
                            st = con.createStatement();
                            rs = st.executeQuery(sQ);
                            /*Si hay datos*/
                            if(rs.next())
                            {
                                /*Obtiene los datos*/
                                sProv           = rs.getString("prov");                
                                sSubTot         = rs.getString("subtot");                
                                sImp            = rs.getString("impue");                
                                sTot            = rs.getString("tot");     
                                sFDoc           = rs.getString("falt");     
                                sNoDoc          = rs.getString("nodoc");     
                                sNomb           = rs.getString("nom");     
                                sTip            = rs.getString("tip");     

                                /*Dales formato de moneda al subtot*/                                
                                double dCant    = Double.parseDouble(sSubTot);                
                                NumberFormat n  = NumberFormat.getCurrencyInstance(Locale.getDefault());
                                sSubTot         = n.format(dCant);

                                /*Dales formato de moneda al impuesto*/
                                dCant           = Double.parseDouble(sImp);                                
                                sImp            = n.format(dCant);

                                /*Dales formato de moneda al total*/
                                dCant           = Double.parseDouble(sTot);                                
                                sTot            = n.format(dCant);
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

                        /*Crea los parámetros que se pasarán*/
                        Map <String,String> para = new HashMap<>();             
                        para.clear();

                        /*Si es una órden de compra entonces los parámetros serán otros*/
                        if(sTip.compareTo("ORD")==0)
                        {
                            para.put("ORD",               sCodCom);                                        
                            para.put("NOMPROV",           sNomb);
                            para.put("CODPROV",           sProv);                    
                            para.put("EMPLOC",            sNomLoc);
                            para.put("TELLOC",            sTelLoc);
                            para.put("COLLOC",            sColLoc);
                            para.put("CALLLOC",           sCallLoc);
                            para.put("CPLOC",             sCPLoc);
                            para.put("CIULOC",            sCiuLoc);
                            para.put("ESTLOC",            sEstLoc);
                            para.put("PAILOC",            sPaiLoc);                    
                            para.put("LOGE",              Star.class.getResource(Star.sIconDef).toString());
                        }
                        /*Else los parámetros serán otros*/
                        else if(sTip.compareTo("COMP")==0)
                        {
                            para.put("COMP",        sCodCom);
                            para.put("FDOC",        sFDoc);
                            para.put("NOM",         sNomb);
                            para.put("PROV",        sProv);
                            para.put("NODOC",       sNoDoc);                    
                            para.put("SUBTOT",      sSubTot);
                            para.put("IMPUE",       sImp);
                            para.put("TOT",         sTot);
                            para.put("LOGE",        Star.class.getResource(Star.sIconDef).toString());
                        }
                        
                        /*Crea el reporte ya sea de compra o de órden de compra*/
                        String sRep     = "rptOrd.jrxml";
                        if(sTip.compareTo("COMP")==0)
                            sRep        = "rptVCom.jrxml";
                        
                        /*Compila la compra*/
                        try
                        {                                                        
                            /*Compila el reporte*/
                            JasperReport ja     = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jasreport/" + sRep));
                            JasperPrint pr      = JasperFillManager.fillReport(ja, (Map)para, con);
                            JasperViewer v      = new JasperViewer(pr, false);
                     
                            /*Exportalo al directorio actual*/
                            JasperExportManager.exportReportToPdfFile(pr, "Temp.pdf");
                        }
                        catch(JRException expnJASR)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnJASR.getMessage(), Star.sErrJASR, expnJASR.getStackTrace(), con);                                                       
                            return;                                                                    
                        }

                        /*Crea el flujo para el archivo*/
                        FileInputStream fis;
                        try
                        {
                            fis = new FileInputStream("Temp.pdf");
                        }
                        catch(FileNotFoundException expnFilNotFoun)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnFilNotFoun.getMessage(), Star.sErrFilNotFoun, expnFilNotFoun.getStackTrace(), con);                                                       
                            return;                                                                    
                        }        

                        /*Crea el trabajo de impresión*/
                        Doc pdfDoc              = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
                        DocPrintJob printJob    = myServiceFi.createPrintJob();

                        /*Imprime el documento*/
                        try
                        {
                            printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
                        }
                        catch(PrintException expnPrint)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnPrint.getMessage(), Star.sErrPrint, expnPrint.getStackTrace(), con);                                                       
                            return;                                                                    
                        }

                        /*Cierra el archivo*/
                        try
                        {
                            fis.close();   
                        }
                        catch(IOException expnIO)
                        {
                            //Procesa el error y regresa
                            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                   
                            return;                                                                    
                        }            

                        /*Borralo*/
                        new File("Temp.pdf").delete();                                                

                    }/*Fin de for(int x = 0; x < iAVtas.length; x++)*/        
                    
                    //Cierra la base de datos
                    Star.iCierrBas(con);

                }/*Fin de public void run()*/
                
            }).start();
            
        }/*Fin de else if(sTipImp.compareTo("compr")==0)*/
                 
        /*Llama al recolector de basura*/
        System.gc();
        
        /*Cierra la forma*/
        dispose();
        
    }//GEN-LAST:event_jBImpActionPerformed

    
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

    
    /*Cuando se mueve el ratón en la forma*/
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
    }//GEN-LAST:event_formMouseMoved

    
    /*Cuando se esta saliendo de la forma*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
        /*Presiona el botón de salir*/
        jBSal.doClick();
        
    }//GEN-LAST:event_formWindowClosing

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBImpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBImp.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBImpMouseEntered

    
    /*Cuando el mouse entra en el botón específico*/
    private void jBSalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseEntered
        
        /*Cambia el color del fondo del botón*/
        jBSal.setBackground(Star.colBot);
        
    }//GEN-LAST:event_jBSalMouseEntered

    
    /*Cuando el mouse sale del botón específico*/
    private void jBImpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBImpMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBImp.setBackground(colOri);
        
    }//GEN-LAST:event_jBImpMouseExited

    
    /*Cuando el mouse sale del botón específico*/
    private void jBSalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBSalMouseExited
        
        /*Cambia el color del fondo del botón al original*/
        jBSal.setBackground(colOri);
        
    }//GEN-LAST:event_jBSalMouseExited
  
                       
    /*Función escalable para cuando se presiona una tecla en el módulo*/
    void vKeyPreEsc(java.awt.event.KeyEvent evt)
    {
        /*Pon la bandera para saber que ya hubó un evento y no se desloguie*/
        bIdle   = true;
        
        /*Si se presiona la tecla de escape presiona el botón de salir*/
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
            jBSal.doClick();
        /*Si se presiona CTRL + P entonces presiona el botón de imprimir*/
        else if(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_P)
            jBImp.doClick();
        
    }/*Fin de void vKeyPreEsc(java.awt.event.KeyEvent evt)*/
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBImp;
    private javax.swing.JButton jBSal;
    private javax.swing.JList jLImp;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
