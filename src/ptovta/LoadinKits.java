//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




/*Clase para mostrar el progreso de la importación de kits*/
public class LoadinKits extends javax.swing.JFrame 
{
    /*Declara variables de clase*/
    private final       String      sRut;
    private final       JTable      jTab;
    private             int         iContFi;        

    /*Bandera para saber cuando hay error*/
    private boolean         bErr;
    
    /*Este es el thread principal*/
    private final Thread    th;
    
    
    
    /*Constructor sin argumentos*/
    public LoadinKits(String sRu, JTable jTabPro, int iContF)     
    {
        /*Inicializa los componentes gráficos*/
        initComponents();
        
        /*Obtiene los datos del otro formulario*/
        sRut        = sRu;
        jTab   = jTabPro;
        iContFi     = iContF;

        /*Inicalmente no hay error*/
        bErr        = false;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);                
                
        /*Función que realiza la importación de los kits a la base de datos*/
        th = new Thread()
        {
            @Override
            public void run()
            {
                vImpBD();
            }            
        };
        th.start();       
        
    }/*Fin de public LoadinKits(String sRu, JTable jTabPro, int iContF)*/

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTInf = new javax.swing.JTextField();

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

        jTInf.setEditable(false);
        jTInf.setBackground(new java.awt.Color(255, 255, 255));
        jTInf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTInf.setText("Iniciando Importación...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTInf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTInf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*Función que realiza la importación de los kits a la base de datos*/
    private void vImpBD()
    {               
        //Abre la base de datos
        Connection  con = Star.conAbrBas(false, true);

        //Si hubo error entonces regresa
        if(con==null)
            dispose();
        
        //Declara variables locales
        int             iConta           = 1;        
        int             iContCell;                

        /*Trabaja con el archivo de excel*/
        try(FileInputStream file    = new FileInputStream(new File(sRut)))
        {
            /*Instancia el objeto de excel con el archivo a importar*/
            XSSFWorkbook workbook   = new XSSFWorkbook(file);

            /*Obtiene la primera hoja del libro*/
            XSSFSheet sheet         = workbook.getSheetAt(0);

            /*Borra todos los registros de los kits*/
            vDelKits(con);

            /*Recorre todas las hojas de excel*/
            Iterator<Row> rowIt     = sheet.iterator();
            while(rowIt.hasNext())
            {
                /*Inicializa el contador de la celda por cada fila*/
                iContCell           = 1;

                /*Recorre todas columnas del archivo*/
                Row row             = rowIt.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                /*Si el contador es igual a uno entonces continua ya que no quiero leer la primera fila de encabezados y que continue*/
                if(iConta == 1)
                {
                    ++iConta;
                    continue;
                }

                //Declara variables de la base de datos
                Statement       st;                
                String          sQInsert;                
                String          sQ; 

                /*Inicializa la consulta*/
                sQInsert    = "INSERT INTO kits(codkit, prod, alma, cant, sucu, nocaj, estac) VALUES('";                    

                /*Cadena que contendrá lo que se mostrará al usuario*/
                String sCadFin  = "";
                
                /*Aqui se almacenará el código del kit que se esta importando para el log*/
                String sCodKit  = "";    
                
                /*Aqui se almacenará el código del producto que se esta importando para el log*/
                String sCodProd = "";    
                
                /*Aquí se almacenará la descripción del producto para el log*/
                String sDescrip = "";

                /*Variable para leer las celdas*/
                String sIn;

                /*Recorre todas las celdas de la fila*/
                while(cellIterator.hasNext())
                {
                    /*Obtiene el objeto de la celda*/
                    Cell cell       = cellIterator.next();                    
                    
                    /*Determina el tipo de celda que es*/
                    switch(cell.getCellType())
                    {
                        /*En caso de que sea de tipo string entonces*/
                        case Cell.CELL_TYPE_STRING:

                            /*Obtiene el valor de la celda*/
                            sIn         = cell.getStringCellValue();                                                 
                            
                            /*Código del producto: Si el contador de celda es igual a uno entonces la lectura esta en la fila después de los encabezados*/
                            if(iContCell == 1)
                            {       
                                /*Si el producto contiene elguno de los carácteres no permitidos entonces*/
                                if(sIn.contains("/") || sIn.contains("\\") || sIn.contains("\"") || sIn.contains("?") || sIn.contains(":") || sIn.contains("|") || sIn.contains("*"))
                                {
                                    //Cierra la base de datos
                                    if(Star.iCierrBas(con)==-1)
                                        return;
                                    
                                    /*Cierra la forma*/
                                    this.dispose();

                                    /*Mensajea y regresa*/
                                    JOptionPane.showMessageDialog(null, "El código de kit: " + sIn + " tiene algún carácter no permitidos: /\\:?*\"| y no se puede hacer la importación.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                     
                                    return;
                                    
                                }/*Fin de if(sIn.contains("/\\\"?:|*"))*/
                                
                                /*Ve creando la cadena a mostrar con el código del kit*/
                                sCadFin     += sIn + "  ";
                                sCodKit     =  sIn;
                                
                                /*Si es el final del archivo entonces*/
                                if(sIn.compareTo("FINARCHIVO")==0)
                                {                           
                                    //Termina la transacción
                                    if(Star.iTermTransCon(con)==-1)
                                        return;

                                    /*Borra todos los item en la tabla de kits*/
                                    DefaultTableModel dm = (DefaultTableModel)jTab.getModel();
                                    dm.setRowCount(0);
                                   
                                    /*Carga todos los kits en la tabla*/
                                    vCargKit(con);                                    

                                    //Cierra la base de datos
                                    if(Star.iCierrBas(con)==-1)
                                        return;
                                        
                                    /*Cierra la forma*/
                                    this.dispose();

                                    /*Mensajea y regresa*/
                                    JOptionPane.showMessageDialog(null, "Exito en la importación de " + (iConta - 1) + " kits.", "Kits", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                     
                                    return;
                                }
                                /*Else completa el código con la cadena a ejecutar*/
                                else
                                    sQInsert   += sCodKit + "','";                                
                            }  
                            /*Else if: Código producto entonces completa la consulta con todas las celdas*/
                            else if(iContCell == 2)
                            {
                                /*Si el producto contiene elguno de los carácteres no permitidos entonces*/
                                if(sIn.contains("/") || sIn.contains("\\") || sIn.contains("\"") || sIn.contains("?") || sIn.contains(":") || sIn.contains("|") || sIn.contains("*"))
                                {
                                    //Cierra la base de datos
                                    if(Star.iCierrBas(con)==-1)
                                        return;
                                    
                                    /*Cierra la forma*/
                                    this.dispose();

                                    /*Mensajea y regresa*/
                                    JOptionPane.showMessageDialog(null, "El código de producto: " + sIn + " tiene algún carácter no permitidos: /\\:?*\"| y no se puede hacer la importación.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                     
                                    return;
                                    
                                }/*Fin de if(sIn.contains("/\\\"?:|*"))*/
                            
                                /*Obtiene el código del producto y completa la cadena con el código*/
                                sCodProd    = sIn;
                                sQInsert   += sIn.replace("'", "''") + "','"; 
                            }
                            /*Else if: Código almacén entonces completa la consulta con todas las celdas*/
                            else if(iContCell == 3)
                                sQInsert   += sIn.replace("'", "''") + "','";                             
                        break;

                        /*En caso de que sea de tipo númerico*/
                        case Cell.CELL_TYPE_NUMERIC:

                            /*If: Cantidad*/
                            if(iContCell == 4)
                            {
                                /*Quita la última coma*/
                                sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                                /*Completa la consulta con todas las celdas*/
                                sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                            }   
                            
                        break;
                            
                    }/*Fin de switch(cell.getCellType())*/                                                           
                                
                    /*Aumenta en uno el contador de la celda*/
                    ++iContCell;

                }/*Fin de while (cellIterator.hasNext())*/

                /*Coloca en el campo para que sea visible que código se esta procesando*/
                jTInf.setText(sCadFin);                    
                    
                /*Quita los últimos carácteres inválidos*/
                sQInsert        = sQInsert.substring(0, sQInsert.length() - 2);
                
                /*Válida que lo que se quiere insertar sea válido*/
                vExis(sCodKit, sCodProd, con, Integer.toString(iConta), Integer.toString(iContCell));
                
                /*Si hubo error entonces cierra la forma y regresa*/                
                if(bErr)
                {
                    this.dispose();
                    return;
                }                    
                
                /*Agrega el terminador de la consulta*/
                sQInsert        += ",'" + Star.sSucu + "','" + Star.sNoCaj + "', '" + Login.sUsrG + "')";
                
                /*Inserta en la base de datos el registro*/
                try
                {
                    st = con.createStatement();                        
                    st.executeUpdate(sQInsert);
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
                    sQ = "INSERT INTO logkit(   kit,                                   prod,                                      descrip,        accio,             estac,                                           sucu,                                           nocaj,                                      falt) " + 
                                  "VALUES('" +  sCodKit.replace("'", "''") + "','" +   sCodProd.replace("'", "''") + "','"    +    sDescrip + "',  'AGREGAR', '" +    Login.sUsrG.replace("'", "''") + "','" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',  now())";                                
                    st = con.createStatement();
                    st.executeUpdate(sQ);
                 }
                catch(SQLException expnSQL) 
                { 
                    //Procesa el error y regresa
                    Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
                    return;                        
                 }

                /*Aumenta el contador de filas*/
                ++iConta;

            }/*Fin de while (rowIt.hasNext())*/
                        
            /*Cierra el fichero*/
            file.close();

        }            
        catch(IOException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
        }       
                    
    }/*Fin de private void LoadinKit()*/
  
    
    /*Comprueba si el código del kit y el producto existe*/
    private void vExis(String sKit, String sProd, Connection con, String sCont, String sContCell)
    {
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
    
        
        
        
        //Comprueba si el kit existe        
        int iRes    = Star.iExistKit(con, sKit);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si no es un kit entonces
        if(iRes==0)
        {            
            /*Cierra la forma*/
            this.dispose();

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código de kit: " + sKit + " no existe o posiblemente no es un kit. No se puede realizar la importación.", "Kits", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca la bandera de error y regresa*/
            bErr    = true;
            return;
            
        }//Fin de if(iRes==0)
                                
        //Comprueba si el el producto es un kit existe                
        iRes        = Star.iExistKit(con, sProd);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el kit existe entonces
        if(iRes==1)
        {            
            /*Cierra la forma*/
            this.dispose();

            //Cierra la base de datos
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Mensajea*/
            JOptionPane.showMessageDialog(null, "El código de producto: " + sProd + " es un kit. No se puede realizar la importación.", "Kits", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));

            /*Coloca la bandera de error y regresa*/
            bErr    = true;
            return;
            
        }//Fin de if(iRes==1)
        
        /*Comprueba si el el producto existe*/        
        try
        {
            sQ = "SELECT prod FROM prods WHERE prod = '" + sProd + "' AND compue = 0";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si no hay datos entonces*/
            if(!rs.next())
            {                                      
                /*Cierra la forma*/
                this.dispose();
            
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
                
                /*Mensajea y coloca la bandera de error*/
                JOptionPane.showMessageDialog(null, "El código de producto: " + sProd + " no existe. No se puede realizar la importación.", "Kits", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                
                bErr    = true;                
            }                                                            
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                
            return;                        
        }  	                                          
        
        /*Comprueba si el kit y el producto ya existen para que no se inserte doble*/        
        try
        {
            sQ = "SELECT prod FROM kits WHERE prod = '" + sProd + "' AND codkit = '" + sKit + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces*/
            if(rs.next())
            {                
                /*Cierra la forma*/
                this.dispose();
            
                //Cierra la base de datos
                if(Star.iCierrBas(con)==-1)
                    return;
            
                /*Mensajea y coloca la bandera*/
                JOptionPane.showMessageDialog(null, "El código de kit: " + sKit + " y producto: " + sProd + " ya estan asociados. No se puede realizar la importación.", "Kits", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                
                bErr    = true;            
            }                                                            
        }
        catch(SQLException expnSQL)
        {            
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                            
        }  	                                          
                
    }/*Fin de private void vExis(String sKit, String sProd, Connection con, String sCont, String sContCell)*/

    
    /*Carga en la tabla de kits los registros*/
    private void vCargKit(Connection con)
    {                                                
        /*Crea el modelo para cargar cadenas en el*/
        DefaultTableModel te = (DefaultTableModel)jTab.getModel();
        
        /*Inicializa el contador de filas*/
        iContFi         = 1;
     
        //Declara variables locales
        String      sQ;                
        Statement   st;
        ResultSet   rs;        
        
        /*Obtiene todos los articulos de la base de datos*/
        try
        {
            sQ = "SELECT codkit, descrip, kits.PROD, kits.CANT, kits.AlMA, kits.FMOD, IFNULL(prelist1,0) prelist1, IFNULL(prelist2,0) prelist2, IFNULL(prelist3,0) prelist3, IFNULL(prelist4,0) prelist4, IFNULL(prelist5,0) prelist5, IFNULL(prelist6,0) prelist6, IFNULL(prelist7,0) prelist7, IFNULL(prelist8,0) prelist8, IFNULL(prelist9,0) prelist9, IFNULL(prelist10,0) prelist10, kits.SUCU, kits.NOCAJ, kits.ESTAC, IFNULL(nom,'') nom FROM kits LEFT OUTER JOIN estacs ON estacs.ESTAC = kits.ESTAC LEFT OUTER JOIN prods ON prods.PROD = kits.CODKIT";
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos*/
            while(rs.next())
            {
                /*Agregalo a la tabla*/
                Object nu[]     = {iContFi, rs.getString("codkit"), rs.getString("descrip"), rs.getString("prod"), rs.getString("cant"), rs.getString("alma"), rs.getString("fmod"), rs.getString("prelist1"), rs.getString("prelist2"), rs.getString("prelist3"), rs.getString("prelist4"), rs.getString("prelist5"), rs.getString("prelist6"), rs.getString("prelist7"), rs.getString("prelist8"), rs.getString("prelist9"), rs.getString("prelist10"), rs.getString("sucu"), rs.getString("nocaj"), rs.getString("estac"), rs.getString("nom")};
                te.addRow(nu);
                
                /*Aumenta en uno el contador de filas de la tabla*/
                ++iContFi;
            }
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                    
        }
                
    }/*Fin de private void vCargKit(Connection con)*/
    
    
    /*Borra todos los registros de los kits*/
    private void vDelKits(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 




        /*Borra todos los registros de la base de datos de los prods*/
        try 
        {            
            sQ = "DELETE FROM kits";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         { 
             //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                    
         }
	        
    }/*Fin de private void vDelKits()*/
            
    
    /*Cuando se mueve la rueda del mouse en la forma*/
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
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField jTInf;
    // End of variables declaration//GEN-END:variables
}
