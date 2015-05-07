//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




/*Clase para mostrar el progreso de la importación de prods*/
public class Loadin extends javax.swing.JFrame
{
    /*Contiene la ruta al archivo*/
    private final  String   sRut;        

    /*Bandera para saber cuando hay error*/
    private boolean         bErr;
    
    /*Este es el thread principal*/
    private Thread          th;
    
    
    
    /*Constructor sin argumentos*/
    public Loadin(String sRu)     
    {
        /*Inicializa los componentes gráficos*/
        initComponents();
        
        /*Obtiene los datos del otro formulario*/
        sRut        = sRu;        

        /*Inicalmente no hay error*/
        bErr        = false;
        
        //Establece el ícono de la forma
        Star.vSetIconFram(this);
        
        /*Centra la ventana*/
        this.setLocationRelativeTo(null);                                                
    }

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
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jTInf.setEditable(false);
        jTInf.setBackground(new java.awt.Color(255, 255, 255));
        jTInf.setForeground(new java.awt.Color(51, 102, 0));
        jTInf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTInf.setText("Iniciando importación...");

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

    
    /*Función que realiza la importación de los prods a la base de datos*/
    private void vImpBD()
    {
        //Abre la base de datos
        Connection  con = Star.conAbrBas(true, false);

        //Si hubo error entonces regresa
        if(con==null)
            return;
        
        /*Contiene el método de costeo de la empresa local*/
        String sMetCost = "";

        //Declara variables de la base de datos
        Statement       st;                
        String          sQInsert;                
        String          sQ; 
        ResultSet rs;
        
        /*Obtiene el método de costeo de la empresa local*/        
        try
        {
            sQ = "SELECT metcost FROM basdats WHERE codemp = '" + Login.sCodEmpBD + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces obtiene el resultado*/
            if(rs.next())
                sMetCost    = rs.getString("metcost");
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
        
        /*Crea la instancia hacia el archivo a importar*/
        FileInputStream file;
        try
        {
            file    =  new FileInputStream(new File(sRut));
        }
        catch(FileNotFoundException expnFilNotFoun)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnFilNotFoun.getMessage(), Star.sErrFilNotFoun, expnFilNotFoun.getStackTrace(), con);                        
            return;                        
        }            

        /*Instancia el objeto de excel*/
        XSSFWorkbook wkbok;
        try
        {
            wkbok   = new XSSFWorkbook(file);
        }
        catch(IOException expnIO)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace(), con);                                                                   
            return;                        
        }                        

        /*Obtiene la primera hoja del libro*/
        XSSFSheet sheet         = wkbok.getSheetAt(0);

        /*Borra todos los registros de los productos*/
        vDelProds(con);

        /*Contador de fila*/
        int iConta              = 1;               

        /*Recorre todas las hojas de excel*/
        Iterator<Row> rowIt     = sheet.iterator();
        while(rowIt.hasNext())
        {
            /*Inicializa el contador de la celda por cada fila*/
            int iContCell           = 1;

            /*Recorre todas columnas del archivo*/
            Row row             = rowIt.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            /*Si el contador es igual a uno entonces continua ya que no quiero leer la primera fila de encabezados y que continue*/
            if(iConta == 1)
            {
                ++iConta;
                continue;
            }

            /*Variable para leer las celdas*/
            String sIn;

            /*Inicializa la consulta*/
            sQInsert    = "INSERT INTO prods(prod, prodop1, prodop2, codmed, med, descrip, descripcort, lin, marc, fab, colo, pes, pesman, prelist1, prelist2, prelist3, prelist4, prelist5, prelist6, prelist7, prelist8, prelist9, prelist10, cost, costre, unid, anaq, lug, descprov, infor, min, max, compue, impue, invent, bajcost, esvta, servi, tip, sucu,  nocaj, estac, falt, exist, metcost) VALUES('";                    

            /*Cadena que contendrá lo que se mostrará al usuario*/
            String sCadFin  = "";

            /*Aqui se almacenará el código del producto que se esta importando para el log*/
            String sCodProd = "";    

            /*Aquí se almacenará la descripción del producto para el log*/
            String sDescrip = "";

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
                                //Cierra la base de datos y sal de la aplicación
                                if(Star.iCierrBas(con)==-1)
                                    return;

                                /*Cierra la forma*/
                                this.dispose();

                                /*Mensajea y regresa*/
                                JOptionPane.showMessageDialog(null, "El código de producto: " + sIn + " tiene algún carácter no permitidos: /\\:?*\"| y no se puede hacer la importación.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                     
                                return;

                            }/*Fin de if(sIn.contains("/\\\"?:|*"))*/

                            /*Ve creando la cadena a mostrar con el código del producto*/
                            sCadFin     += sIn + "  ";

                            /*Si el código del producto es este símbolo entonces*/
                            if(sIn.compareTo("%")==0)
                            {
                                /*Genera la clave automáticamente para el código del producto y agregalos a la cadena*/
                                String sCod = Star.sGenClavDia() + "%";
                                sQInsert   += sCod + "','";                                                                                                                                                                                    
                                
                                /*Duerme el thread 1 segundo para la próxima clave del día*/
                                try
                                {
                                    Thread.sleep(1000);
                                }
                                catch(InterruptedException expnInterru)
                                {                                    
                                }
                                
                                /*Guarda el código del producto en la variable*/
                                sCodProd    = sCod;                                                                        
                            }
                            /*Else if si es el fin del archivo entonces*/
                            else if(sIn.compareTo("FINARCHIVO")==0)
                            {
                                //Termina la transacción
                                if(Star.iTermTransCon(con)==-1)
                                    return;

                                //Cierra la base de datos y sal de la aplicación
                                if(Star.iCierrBas(con)==-1)
                                    return;

                                /*Cierra la forma*/
                                this.dispose();

                                /*Mensajea y regresa*/
                                JOptionPane.showMessageDialog(null, "Exito en la importación de " + (iConta - 1) + " productos.", "Productos", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                                                                     
                                return;
                            }
                            else
                            {
                                /*Obtiene el código producto que escribió el cliente*/
                                sCodProd    = cell.getStringCellValue();
                                sQInsert   += sCodProd + "','";
                            }                                                                                                                                                
                        }  
                        /*Else if: Código opcional 1  entonces completa la consulta con todas las celdas*/
                        else if(iContCell == 2)
                            sQInsert   += sIn.replace("'", "''") + "','"; 
                        /*Else if: Código opcional 2  entonces completa la consulta con todas las celdas*/
                        else if(iContCell == 3)
                            sQInsert   += sIn.replace("'", "''") + "','"; 
                        /*Else if: Código de medida*/
                        else if(iContCell == 4)
                        {
                            /*Comprueba si el código de la medida existe, si no existe entonces la crea con su descripción*/
                            vCreMed(sIn, con, Integer.toString(iConta), Integer.toString(iContCell));

                            /*Si hubo error entonces cierra la forma y regresa*/
                            if(bErr)
                            {
                                dispose();                                    
                                return; 
                            }

                            /*Obtiene solo el código*/
                            StringTokenizer str = new StringTokenizer(sIn, ",");
                            sIn         = str.nextToken().trim().toUpperCase();

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        }
                        /*Else if: Descripción larga*/
                        else if(iContCell == 6)
                        {
                            /*Si es cadena vacia entonces que sea cadena vacia*/
                            if(sIn.compareTo(".")==0)
                                sIn     = "";

                            /*Obtiene la descripción del producto*/
                            sDescrip    = sIn;

                            /*Ve creando la cadena a mostrar con la descripción del producto*/
                            sCadFin     += sDescrip + "  ";

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        }
                        /*Else if: Descripción corta*/
                        else if(iContCell == 7)
                        {
                            /*Si es cadena vacia entonces que sea cadena vacia*/
                            if(sIn.compareTo(".")==0)
                                sIn     = "";

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        }
                        /*Else if: Código de línea*/
                        else if(iContCell == 8)
                        {
                            /*Comprueba si el código de la línea existe, si no existe entonces la crea con su descripción*/
                            vCreLin(sIn, con, Integer.toString(iConta), Integer.toString(iContCell));

                            /*Si hubo error entonces*/
                            if(bErr)
                            {
                                //Cierra la base de datos y sal de la aplicación
                                if(Star.iCierrBas(con)==-1)
                                    return;
                                /*Cierra y regresa*/
                                dispose();                                    
                                return; 
                            }

                            /*Obtiene solo el código*/
                            StringTokenizer str = new StringTokenizer(sIn, ",");
                            sIn         = str.nextToken().trim().toUpperCase();

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        }
                        /*Else if: Código de la marca*/
                        else if(iContCell == 9)
                        {
                            /*Comprueba si el código de la línea marca, si no existe entonces la crea con su descripción*/
                            vCreMarc(sIn, con, Integer.toString(iConta), Integer.toString(iContCell));                                                                

                            /*Si hubo error entonces*/
                            if(bErr)
                            {
                                //Cierra la base de datos y sal de la aplicación
                                if(Star.iCierrBas(con)==-1)
                                    return;

                                /*Cierra la forma y regresa*/
                                dispose();                                    
                                return; 
                            }

                            /*Obtiene solo el código*/
                            StringTokenizer str = new StringTokenizer(sIn, ",");
                            sIn         = str.nextToken().trim().toUpperCase();

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        }   
                        /*Else if: Código del fabricante*/
                        else if(iContCell == 10)
                        {
                            /*Comprueba si el código del fabricante existe, si no existe entonces la crea con su descripción*/
                            vCreFab(sIn, con, Integer.toString(iConta), Integer.toString(iContCell));

                            /*Si hubo error entonces*/
                            if(bErr)
                            {
                                //Cierra la base de datos y sal de la aplicación
                                if(Star.iCierrBas(con)==-1)
                                    return;
                                
                                /*Cierra y regresa*/
                                dispose();                                    
                                return; 
                            }

                            /*Obtiene solo el código*/
                            StringTokenizer str = new StringTokenizer(sIn, ",");
                            sIn         = str.nextToken().trim().toUpperCase();

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        }   
                        /*Else if: Código del color*/
                        else if(iContCell == 11)
                        {
                            /*Comprueba si el código del color existe, si no existe entonces la crea con su descripción*/
                            vCreColo(sIn, con, Integer.toString(iConta), Integer.toString(iContCell));

                            /*Si hubo error entonces*/
                            if(bErr)
                            {
                                //Cierra la base de datos y sal de la aplicación
                                if(Star.iCierrBas(con)==-1)
                                    return;

                                /*Cierra la forma y rgeresa*/
                                dispose();                                    
                                return; 
                            }

                            /*Obtiene solo el código*/
                            StringTokenizer str = new StringTokenizer(sIn, ",");
                            sIn         = str.nextToken().trim().toUpperCase();

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        }  
                        /*Else if: Código del peso*/
                        else if(iContCell == 12)
                        {
                            /*Comprueba si el código del pes existe, si no existe entonces la crea con su descripción*/
                            vCrePes(sIn, con, Integer.toString(iConta), Integer.toString(iContCell));

                            /*Si hubo error entonces cierra la forma y regresa*/
                            if(bErr)
                            {
                                //Cierra la base de datos y sal de la aplicación
                                if(Star.iCierrBas(con)==-1)
                                    return;

                                /*Cierra la forma y regresa*/
                                dispose();                                    
                                return; 
                            }

                            /*Obtiene solo el código*/
                            StringTokenizer str = new StringTokenizer(sIn, ",");
                            sIn         = str.nextToken().trim().toUpperCase();

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        } 
                        /*Else if: Peso entonces completa la ruta*/
                        else if(iContCell == 13)
                            sQInsert   += sIn.replace("'", "''") + "','";                                  
                        /*Else if: Precio lista 1 entonces completa la ruta*/
                        else if(iContCell == 14)
                            sQInsert   += sIn.replace("'", "''") + "','";                                  
                        /*Else if: Precio lista 2 entonces completa la ruta*/
                        else if(iContCell == 15)
                            sQInsert   += sIn.replace("'", "''") + "','";                                  
                        /*Else if: Precio lista 3 entonces completa la ruta*/
                        else if(iContCell == 16)
                            sQInsert   += sIn.replace("'", "''") + "','";                                  
                        /*Else if: Precio lista 4 entonces completa la ruta*/
                        else if(iContCell == 17)
                            sQInsert   += sIn.replace("'", "''") + "','";                                  
                        /*Else if: Precio lista 5 entonces completa la ruta*/
                        else if(iContCell == 18)
                            sQInsert   += sIn.replace("'", "''") + "','";                                  
                        /*Else if: Precio lista 6 entonces completa la ruta*/
                        else if(iContCell == 19)
                            sQInsert   += sIn.replace("'", "''") + "','";                                  
                        /*Else if: Precio lista 7 entonces completa la ruta*/
                        else if(iContCell == 20)
                            sQInsert   += sIn.replace("'", "''") + "','";                                  
                        /*Else if: Precio lista 8 entonces completa la ruta*/
                        else if(iContCell == 21)
                            sQInsert   += sIn.replace("'", "''") + "','";                                  
                        /*Else if: Precio lista 9 entonces completa la ruta*/
                        else if(iContCell == 22)
                            sQInsert   += sIn.replace("'", "''") + "','";                                  
                        /*Else if: Precio lista 10 entonces completa la ruta*/
                        else if(iContCell == 23)
                            sQInsert   += sIn.replace("'", "''") + "','";                                  
                        /*Else if: Costo entonces completa la ruta*/
                        else if(iContCell == 24)
                            sQInsert   += sIn.replace("'", "''") + "','";                                  
                        /*Else if: Costo recoger entonces completa la ruta*/
                        else if(iContCell == 25)
                            sQInsert   += sIn.replace("'", "''") + "','";                                                          
                        /*Else if: Código de la unidad*/
                        else if(iContCell == 26)
                        {
                            /*Comprueba si el código de la unidad existe, si no existe entonces la crea con su descripción*/
                            vCreUnid(sIn, con, Integer.toString(iConta), Integer.toString(iContCell));

                            /*Si hubo error entonces cierra la forma y regresa*/
                            if(bErr)
                            {
                                //Cierra la base de datos y sal de la aplicación
                                if(Star.iCierrBas(con)==-1)
                                    return;

                                /*Cierra la forma y regresa*/
                                dispose();                                    
                                return; 
                            }

                            /*Obtiene solo el código*/
                            StringTokenizer str = new StringTokenizer(sIn, ",");
                            sIn         = str.nextToken().trim().toUpperCase();

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        } 
                        /*Else if: Código del anaquel*/
                        else if(iContCell == 27)
                        {
                            /*Comprueba si el código del anaquel existe, si no existe entonces la crea con su descripción*/
                            vCreAnaq(sIn, con, Integer.toString(iConta), Integer.toString(iContCell));

                            /*Si hubo error entonces cierra la forma y regresa*/
                            if(bErr)
                            {
                                //Cierra la base de datos y sal de la aplicación
                                if(Star.iCierrBas(con)==-1)
                                    return;
                                
                                /*Cierra la forma y regresa*/
                                dispose();                                    
                                return; 
                            }

                            /*Obtiene solo el código*/
                            StringTokenizer str = new StringTokenizer(sIn, ",");
                            sIn         = str.nextToken().trim().toUpperCase();

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        } 
                        /*Else if: Código del lugar*/
                        else if(iContCell == 28)
                        {
                            /*Comprueba si el código del lugar existe, si no existe entonces la crea con su descripción*/
                            vCreLug(sIn, con, Integer.toString(iConta), Integer.toString(iContCell));

                            /*Si hubo error entonces cierra la forma y regresa*/
                            if(bErr)
                            {
                                //Cierra la base de datos y sal de la aplicación
                                if(Star.iCierrBas(con)==-1)
                                    return;

                                /*Cierra la forma y regresa*/
                                dispose();                                    
                                return; 
                            }

                            /*Obtiene solo el código*/
                            StringTokenizer str = new StringTokenizer(sIn, ",");
                            sIn         = str.nextToken().trim().toUpperCase();

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        }                      
                        /*Else if: Anotaciones*/
                        else if(iContCell == 29)
                        {
                            /*Si es cadena vacia entonces que sea cadena vacia*/
                            if(sIn.compareTo(".")==0)
                                sIn     = "";

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        } 

                        /*Else if: Información*/
                        else if(iContCell == 30)
                        {
                            /*Si es cadena vacia entonces que sea cadena vacia*/
                            if(sIn.compareTo(".")==0)
                                sIn     = "";

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        }  
                        /*Else if: Impuesto*/
                        else if(iContCell == 34)
                        {                      
                            /*Si el impuesto no es punto entonces*/
                            if(sIn.compareTo(".")!=0)
                            {                                    
                                /*Comprueba si el código del impuesto existe, si no existe entonces crea con su descripción*/
                                vCreImpue(sIn, con, Integer.toString(iConta), Integer.toString(iContCell));

                                /*Si hubo error entonces*/
                                if(bErr)
                                {
                                    //Cierra la base de datos y sal de la aplicación
                                    if(Star.iCierrBas(con)==-1)
                                        return;
                                
                                    /*Cierra la forma y regresa*/
                                    dispose();                                    
                                    return; 
                                }

                                /*Obtiene solo el código*/
                                StringTokenizer str = new StringTokenizer(sIn, ",");
                                sIn         = str.nextToken().trim().toUpperCase();

                            }/*Fin de if(sIn.compareTo(".")!=0)*/

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''").replace(".", "") + "','";     
                        } 
                        /*Else if: Tipo*/
                        else if(iContCell == 39)
                        {                                
                            /*Comprueba si el código del tipo existe, si no existe entonces crealo con su descripción*/
                            vCreTip(sIn, con, Integer.toString(iConta), Integer.toString(iContCell));

                            /*Si hubo error entonces*/
                            if(bErr)
                            {
                                //Cierra la base de datos y sal de la aplicación
                                if(Star.iCierrBas(con)==-1)
                                    return;
                                
                                /*Cierra la forma y regresa*/
                                dispose();                                    
                                return; 
                            }

                            /*Obtiene solo el código*/
                            StringTokenizer str = new StringTokenizer(sIn, ",");
                            sIn         = str.nextToken().trim().toUpperCase();

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += sIn.replace("'", "''") + "','";     
                        }                             

                    break;

                    /*En caso de que sea de tipo númerico*/
                    case Cell.CELL_TYPE_NUMERIC:

                        /*If: Medida*/
                        if(iContCell == 5)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        }   
                        /*Else if: Peso*/
                        else if(iContCell == 13)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Precio lista 1*/
                        else if(iContCell == 14)
                        {
                            /*Quita la última coma*/
                            sQInsert    = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Precio lista 2*/
                        else if(iContCell == 15)
                        {
                            /*Quita la última coma*/
                            sQInsert    = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Precio lista 3*/
                        else if(iContCell == 16)
                        {
                            /*Quita la última coma*/
                            sQInsert    = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Precio lista 4*/
                        else if(iContCell == 17)
                        {
                            /*Quita la última coma*/
                            sQInsert    = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Precio lista 5*/
                        else if(iContCell == 18)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Precio lista 6*/
                        else if(iContCell == 19)
                        {
                            /*Quita la última coma*/
                            sQInsert    = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Precio lista 7*/
                        else if(iContCell == 20)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Precio lista 8*/
                        else if(iContCell == 21)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Precio lista 9*/
                        else if(iContCell == 22)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Precio lista 10*/
                        else if(iContCell == 23)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Costo*/
                        else if(iContCell == 24)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Costo recoger*/
                        else if(iContCell == 25)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Mínimo*/
                        else if(iContCell == 31)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Máximo*/
                        else if(iContCell == 32)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        } 
                        /*Else if: Compuesto*/
                        else if(iContCell == 33)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        }                                 
                        /*Else if: Inventariable*/
                        else if(iContCell == 35)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        }                                 
                        /*Else if: Vender abajo del cost*/
                        else if(iContCell == 36)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        }                                                                 
                        /*Else if: Es para venta*/
                        else if(iContCell == 37)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        }                      
                        /*Else if es servicio*/
                        else if(iContCell == 38)
                        {
                            /*Quita la última coma*/
                            sQInsert = sQInsert.substring(0, sQInsert.length() - 1);

                            /*Completa la consulta con todas las celdas*/
                            sQInsert   += Double.toString(cell.getNumericCellValue()) + ",'";                                
                        }

                    break;
                }                                                           

                /*Aumenta en uno el contador de la celda*/
                ++iContCell;

            }/*Fin de while (cellIterator.hasNext())*/

            /*Coloca en el campo para que sea visible que código se esta procesando*/
            jTInf.setText(sCadFin);                    

            /*Quita los últimos carácteres inválidos*/
            sQInsert        = sQInsert.substring(0, sQInsert.length() - 2);

            /*Agrega el terminador de la consulta*/
            sQInsert        += ",'" + Star.sSucu + "','" + Star.sNoCaj + "', '" + Login.sUsrG + "', now(), 0, '" + sMetCost + "')";
            
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

            /*Inserta en log de productos*/
            try 
            {            
                sQ = "INSERT INTO logprods( cod,                                        descrip,        accio,             estac,                                       sucu,                                     nocaj,                                falt) " + 
                              "VALUES('" +  sCodProd.replace("'", "''") + "','" +       sDescrip + "',  'AGREGAR', '" +    Login.sUsrG.replace("'", "''") + "','" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "',  now())";                                
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
        try
        {
            file.close();       
        }
        catch(IOException expnIO)
        {
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnIO.getMessage(), Star.sErrIO, expnIO.getStackTrace());                                                                   
        }            
                    
    }/*Fin de private void vImpBD*/
  
    
    /*Comprueba si el código de la medida existe, si no existe entonces la crea con su descripción*/
    private void vCreMed(String sVal, Connection con, String sCont, String sContCell)
    {
        //Obtiene el código
        StringTokenizer str = new StringTokenizer(sVal, ",");
        String sCod         = str.nextToken().trim().toUpperCase();
                
        //Obtiene la descricpión
        String sDescrip     = str.nextToken().trim().toUpperCase();  
        
        /*Si la descripción es un punto entonces que sea cadena vacia*/
        if(sDescrip.compareTo(".")==0)
            sDescrip        = "";
        
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        //Comprueba si la medida existe
        int iRes    = Star.iExistMed(con, sCod.trim());
                
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si la medida existe entonces coloca la bandera
        boolean bSi = false;
        if(iRes==1)
            bSi     = true;
        
        /*Si el código no existe entonces*/
        if(!bSi)
        {
            /*Inserta el código en la base de datos*/
            try 
            {                
                sQ      = "INSERT INTO meds(cod,                                estac,                                      descrip,                            falt,   fmod,           sucu,                                           nocaj) " + 
                               "VALUES('" + sCod.replace("'", "''") + "', '" +  Login.sUsrG.replace("'", "''") + "', '" +   sDescrip.replace("'", "''") + "',   now(),  now(), '" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                                       
                st      = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                        
             }

            /*Inserta en log de meds*/
            try 
            {            
                sQ = "INSERT INTO logmeds(cod,                                      descrip,                            accio,          estac,    sucu,      nocaj,    falt) " + 
                             "VALUES('" + sCod.replace("'", "''") + "', '" +        sDescrip.replace("'", "''") + "',   'AGREGAR',      'INICIAL','INICIAL','INICIAL', now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            { 
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                       
            }
                        
        }/*if(!bSi)*/
                
    }/*Fin de private void vCreMed(String sMed)*/

    
    /*Comprueba si el código de la línea existe, si no existe entonces la crea con su descripción*/
    private void vCreLin(String sVal, Connection con, String sCont, String sContCell)
    {
        //Ontiene el código
        StringTokenizer str = new StringTokenizer(sVal, ",");
        String sCod         = str.nextToken().trim().toUpperCase();
        
        //Obtiene la descripción
        String sDescrip     = str.nextToken().trim().toUpperCase();  
        
        /*Si la descripción es un punto entonces que sea cadena vacia*/
        if(sDescrip.compareTo(".")==0)
            sDescrip        = "";
        
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        //Comprueba si existe una línea
        int iRes        = Star.iExistLin(con, sCod.trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si existe la línea entonces coloca la bandera
        boolean bSi = false;
        if(iRes==1)
            bSi     = true;
        
        /*Si el código no existe entonces*/
        if(!bSi)
        {
            /*Inserta el registro en la base de datos*/
            try 
            {                
                sQ = "INSERT INTO lins(  cod,                                   estac,                                      descrip,                            falt,   fmod,       sucu,                                           nocaj) " + 
                       "VALUES('" +      sCod.replace("'", "''") + "', '" +     Login.sUsrG.replace("'", "''") + "', '" +   sDescrip.replace("'", "''") + "',   now(),  now(), '" + Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                                       
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                        
             } 

            /*Inserta en log de líneas*/
            try 
            {            
                sQ = "INSERT INTO loglins(cod,                                      descrip,                            accio,          estac,    sucu,      nocaj,    falt) " + 
                             "VALUES('" + sCod.replace("'", "''") + "', '" +        sDescrip.replace("'", "''") + "',   'AGREGAR',      'INICIAL','INICIAL','INICIAL', now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            { 
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                       
            }
                        
        }/*if(!bSi)*/              

    }/*Fin de private void vCreLin(String sMed)*/

    
    /*Comprueba si el código de la marca existe, si no existe entonces la crea con su descripción*/
    private void vCreMarc(String sVal, Connection con, String sCont, String sContCell)
    {
        //Obtiene el código
        StringTokenizer str = new StringTokenizer(sVal, ",");
        String sCod         = str.nextToken().trim().toUpperCase();
        
        //Obtiene la descripción
        String sDescrip     = str.nextToken().trim().toUpperCase();  
        
        /*Si la descripción es un punto entonces que sea cadena vacia*/
        if(sDescrip.compareTo(".")==0)
            sDescrip        = "";
        
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        //Comprueba si la marca existe
        int iRes    = Star.iExistMarc(con, sCod);
        
        //Si hubo error entonces regreas
        if(iRes==-1)
            return;
        
        //Si la marca existe entonces coloca la bandera
        boolean bSi = false;
        if(iRes==1)
            bSi     = true;
        
        /*Si el código no existe entonces*/
        if(!bSi)
        {
            /*Inserta el registro en la base de datos*/
            try 
            {                
                sQ = "INSERT INTO marcs(cod,                                estac,                                      descrip,                                falt,      fmod,                sucu,                                           nocaj) " + 
                           "VALUES('" + sCod.replace("'", "''") + "', '" +  Login.sUsrG.replace("'", "''") + "', '" +   sDescrip.replace("'", "''") + "',       now(),     now(), '" +          Star.sSucu.replace("'", "''") + "','" +    Star.sNoCaj.replace("'", "''") + "')";                                       
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                        
             }    
            
            /*Inserta en log de marcs*/
            try 
            {            
                sQ = "INSERT INTO logmarc(cod,                                  descrip,                            accio,          estac,    sucu,      nocaj,    falt) " + 
                            "VALUES('" + sCod.replace("'", "''") + "', '" +     sDescrip.replace("'", "''") + "',   'AGREGAR',      'INICIAL','INICIAL','INICIAL', now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            {    
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                       
            }
            
        }/*if(!bSi)*/
               
    }/*Fin de private void vCreMarc(String sMed)*/
    
    
    /*Comprueba si el código del fabricante existe, si no existe entonces la crea con su descripción*/
    private void vCreFab(String sVal, Connection con, String sCont, String sContCell)
    {
        //Obtiene el código
        StringTokenizer str = new StringTokenizer(sVal, ",");
        String sCod         = str.nextToken().trim().toUpperCase();
        
        //Obtiene la descripción
        String sDescrip     = str.nextToken().trim().toUpperCase();  
        
        /*Si la descripción es un punto entonces que sea cadena vacia*/
        if(sDescrip.compareTo(".")==0)
            sDescrip        = "";
        
        //Declara variables de la base de datos
        Statement   st;      
        String      sQ; 
        
        //Comprueba si el fabricante existe
        int iRes    = Star.iExistFab(con, sCod.trim());
                
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el fabricante existe entonces coloca la bandera
        boolean bSi = false;
        if(iRes==1)
            bSi     = true;
        
        /*Si el código no existe entonces*/
        if(!bSi)
        {
            /*Inserta el registro en la base de datos*/
            try 
            {                
                sQ = "INSERT INTO fabs(cod,                                 estac,                                      descrip,                                falt,      fmod,                   sucu,                                                nocaj) " + 
                          "VALUES('" + sCod.replace("'", "''") + "', '" +   Login.sUsrG.replace("'", "''") + "', '" +   sDescrip.replace("'", "''") + "',       now(),     now(), '" +             Star.sSucu.replace("'", "''") + "','" +        Star.sNoCaj.replace("'", "''") + "')";                                       
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                        
             }                        

            /*Inserta en log de fabs*/
            try 
            {            
                sQ = "INSERT INTO logfabs(cod,                                      descrip,                            accio,          estac,    sucu,      nocaj,    falt) " + 
                             "VALUES('" + sCod.replace("'", "''") + "', '" +        sDescrip.replace("'", "''") + "',   'AGREGAR',      'INICIAL','INICIAL','INICIAL', now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            {    
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                       
            }
                        
        }/*Fin de if(!bSi)*/                

    }/*Fin de private void vCreFab(String sMed)*/
    
    
    /*Comprueba si el código del color existe, si no existe entonces la crea con su descripción*/
    private void vCreColo(String sVal, Connection con, String sCont, String sContCell)
    {
        //Obtiene el código
        StringTokenizer str = new StringTokenizer(sVal, ",");
        String sCod         = str.nextToken().trim().toUpperCase();
        
        //Obtiene la descripción
        String sDescrip     = str.nextToken().trim().toUpperCase();  
        
        /*Si la descripción es un punto entonces que sea cadena vacia*/
        if(sDescrip.compareTo(".")==0)
            sDescrip        = "";
        
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        //Inicialmente no existe el color
        boolean bSi = false;        
        
        //Comprueba si existe el color                
        int iRes    = Star.iExisColo(con, sCod);
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Determina si existe o no
        if(iRes==1)
            bSi     = true;
                
        /*Si el código no existe entonces*/
        if(!bSi)
        {
            /*Inserta el registro en la base de datos*/
            try 
            {
                sQ = "INSERT INTO colos(cod,                                estac,                                      descrip,                                falt,      fmod,           sucu,                                            nocaj) " + 
                           "VALUES('" + sCod.replace("'", "''") + "', '" +  Login.sUsrG.replace("'", "''") + "', '" +   sDescrip.replace("'", "''") + "',       now(),     now(), '" +     Star.sSucu.replace("'", "''") + "','" +    Star.sNoCaj.replace("'", "''") + "')";                                       
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                        
             }  
            
            /*Inserta en log de colos*/
            try 
            {            
                sQ = "INSERT INTO logcolo(cod,                                  descrip,                            accio,          estac,    sucu,      nocaj,    falt) " + 
                            "VALUES('" + sCod.replace("'", "''") + "', '" +     sDescrip.replace("'", "''") + "',   'AGREGAR',      'INICIAL','INICIAL','INICIAL', now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            {    
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                       
            }
                        
        }/*Fin de if(!bSi)*/                

    }/*Fin de private void vCreColo(String sMed)*/
    
    
    /*Comprueba si el código del pes existe, si no existe entonces la crea con su descripción*/
    private void vCrePes(String sVal, Connection con, String sCont, String sContCell)
    {
        //Obtiene el código
        StringTokenizer str = new StringTokenizer(sVal, ",");
        String sCod         = str.nextToken().trim().toUpperCase();
        
        //Obtiene la descripción
        String sDescrip     = str.nextToken().trim().toUpperCase();  
        
        /*Si la descripción es un punto entonces que sea cadena vacia*/
        if(sDescrip.compareTo(".")==0)
            sDescrip        = "";
        
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        //Comprueba si el peso existe
        int iRes        = Star.iExistPes(con, sCod.trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
                
        //Si existe el peso entonces coloca la bandera
        boolean bSi = false;
        if(iRes==1)
            bSi     = true;
        
        /*Si el código no existe entonces*/
        if(!bSi)
        {
            /*Inserta el registro en la base de datos*/
            try 
            {                
                sQ = "INSERT INTO pes(cod,                                  estac,                                      descrip,                                falt,      fmod,              sucu,                                             nocaj) " + 
                         "VALUES('" + sCod.replace("'", "''") + "', '" +    Login.sUsrG.replace("'", "''") + "', '" +   sDescrip.replace("'", "''") + "',       now(),     now(), '" +        Star.sSucu.replace("'", "''") + "','" +     Star.sNoCaj.replace("'", "''") + "')";                                       
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            {                 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                        
             }

            /*Inserta en log de pess*/
            try 
            {            
                sQ = "INSERT INTO logpes(cod,                                   descrip,                            accio,          estac,    sucu,      nocaj,    falt) " + 
                            "VALUES('" + sCod.replace("'", "''") + "', '" +     sDescrip.replace("'", "''") + "',   'AGREGAR',      'INICIAL','INICIAL','INICIAL', now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            { 
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                       
            }
                        
        }/*Fin de if(!bSi)*/               

    }/*Fin de private void vCrePes(String sMed)*/
    
    
    /*Comprueba si el código de la unidad existe, si no existe entonces la crea con su descripción*/
    private void vCreUnid(String sVal, Connection con, String sCont, String sContCell)
    {
        //Obtiene el código
        StringTokenizer str = new StringTokenizer(sVal, ",");
        String sCod         = str.nextToken().trim().toUpperCase();
        
        //Separa la descripción
        String sDescrip     = str.nextToken().trim().toUpperCase();  ;
        
        /*Si la descripción es un punto entonces que sea cadena vacia*/
        if(sDescrip.compareTo(".")==0)
            sDescrip        = "";
        
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        //Comprueba si la unidad existe
        int iRes        = Star.iExistUnid(con, sCod.trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si existe la unidad entonces coloca la bandera
        boolean bSi = false;
        if(iRes==1)
            bSi     = true;
        
        /*Si el código no existe entonces*/
        if(!bSi)
        {                        
            /*Inserta el registro en la base de datos*/
            try 
            {                
                sQ = "INSERT INTO unids(cod,                                estac,                 descrip,                                 falt,   fmod,           sucu,                                           nocaj) " + 
                           "VALUES('" + sCod.replace("'", "''") + "', '" +  Login.sUsrG + "', '" + sDescrip.replace("'", "''") + "',        now(),  now(), '" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                                       
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             {  
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                        
             }

            /*Inserta en log de unidaes*/
            try 
            {            
                sQ = "INSERT INTO logunid(cod,                                      descrip,                            accio,          estac,    sucu,      nocaj,    falt) " + 
                             "VALUES('" + sCod.replace("'", "''") + "', '" +        sDescrip.replace("'", "''") + "',   'AGREGAR',      'INICIAL','INICIAL','INICIAL', now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            { 
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                       
            }
                        
        }/*Fin de if(!bSi)*/              

    }/*Fin de private void vCreUnid(String sMed)*/
    
    
    /*Comprueba si el código del anaquel existe, si no existe entonces la crea con su descripción*/
    private void vCreAnaq(String sVal, Connection con, String sCont, String sContCell)
    {
        /*Tokeniza*/
        StringTokenizer str = new StringTokenizer(sVal, ",");
        String sCod         = str.nextToken().trim().toUpperCase();
        
        //Obtiene la descripción
        String sDescrip     = str.nextToken().trim().toUpperCase();  
        
        /*Si la descripción es un punto entonces que sea cadena vacia*/
        if(sDescrip.compareTo(".")==0)
            sDescrip        = "";
        
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        //Comprueba si existe el anaquel
        int iRes    = Star.iExistAnaq(con, sCod.trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si existe netonces coloca la bandera
        boolean bSi = false;
        if(iRes==1)
            bSi     = true;
        
        /*Si el código no existe entonces*/
        if(!bSi)
        {
            /*Inserta el registro en la base de datos*/
            try 
            {                
                sQ = "INSERT INTO anaqs(cod,                                estac,                                      descrip,                                falt,       fmod,           sucu,                                           nocaj) " + 
                          "VALUES('" + sCod.replace("'", "''") + "', '" +   Login.sUsrG.replace("'", "''") + "', '" +   sDescrip.replace("'", "''") + "',       now(),      now(), '" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                                       
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                        
             }

            /*Inserta en log de anaqs*/
            try 
            {            
                sQ = "INSERT INTO loganaq(cod,                                  descrip,                            accio,          estac,    sucu,      nocaj,    falt) " + 
                            "VALUES('" + sCod.replace("'", "''") + "', '" +     sDescrip.replace("'", "''") + "',   'AGREGAR',      'INICIAL','INICIAL','INICIAL', now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            { 
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                       
            }            
            
        }/*Fin de if(!bSi)*/
                
    }/*Fin de private void vCreAnaq(String sMed)*/
    
    
    /*Comprueba si el código del lugar existe, si no existe entonces la crea con su descripción*/
    private void vCreLug(String sVal, Connection con, String sCont, String sContCell)
    {
        //Obtiene el código
        StringTokenizer str = new StringTokenizer(sVal, ",");
        String sCod         = str.nextToken().trim().toUpperCase();
        
        //Obtiene la descripción
        String sDescrip     = str.nextToken().trim().toUpperCase();  
        
        /*Si la descripción es un punto entonces que sea cadena vacia*/
        if(sDescrip.compareTo(".")==0)
            sDescrip        = "";
        
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        //Comprueba si el lugar existe
        int iRes    = Star.iExistLug(con, sCod.trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el lugar existe entonces colcoa la bandera
        boolean bSi = false;
        if(iRes==1)
            bSi     = true;
        
        /*Si el código no existe entonces*/
        if(!bSi)
        {
            /*Inserta el registro en la base de datos*/
            try 
            {                
                sQ = "INSERT INTO lugs(cod,                                 estac,                                      descrip,                                falt,       fmod,               sucu,                                           nocaj) " + 
                         "VALUES('" + sCod.replace("'", "''") + "', '" +    Login.sUsrG.replace("'", "''") + "', '" +   sDescrip.replace("'", "''") + "',       now(),      now(), '" +         Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                                       
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                        
             }
            
            /*Inserta en log de lugs*/
            try 
            {            
                sQ = "INSERT INTO loglugs(cod,                                  descrip,                            accio,          estac,    sucu,      nocaj,    falt) " + 
                           "VALUES('" + sCod.replace("'", "''") + "', '" +      sDescrip.replace("'", "''") + "',   'AGREGAR',      'INICIAL','INICIAL','INICIAL', now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            { 
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                       
            }
                                    
        }/*Fin de if(!bSi)*/
                
    }/*Fin de private void vCreLug(String sMed)*/
            
    
    /*Comprueba si el código del impue existe, si no existe entonces la crea con su descripción*/
    private void vCreImpue(String sVal, Connection con, String sCont, String sContCell)
    {
        //Obtiene el código
        StringTokenizer str = new StringTokenizer(sVal, ",");
        String sCod         = str.nextToken().trim().toUpperCase();
                
        //Obtiene la descripción
        String sDescrip     = str.nextToken().trim().toUpperCase();  
        
        //Declara variables de la base de datos
        Statement   st;        
        String      sQ; 
        
        //Comprueba si el impuesto ya existe en la base de datos
        int iRes        = Star.iExistImpue(con, sCod.trim());
        
        //Si hubo error entonces regresa
        if(iRes==-1)
            return;
        
        //Si el impuesto ya existe entonces coloca la bandera
        boolean bSi = false;
        if(iRes==1)
            bSi     = true;
        
        /*Si el código no existe entonces*/
        if(!bSi)
        {
            /*Inserta el registro en la base de datos*/
            try 
            {                
                sQ = "INSERT INTO impues(codimpue,                          estac,                                          impueval,                           falt,   fmod,           sucu,                                               nocaj) " + 
                            "VALUES('" + sCod.replace("'", "''") + "', '" + Login.sUsrG.replace("'", "''") + "', '" +   sVal.replace("'", "''") + "',       now(),  now(), '" +     Star.sSucu.replace("'", "''") + "','" +       Star.sNoCaj.replace("'", "''") + "')";                                       
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                                        
             }
                        
            /*Inserta en log de impues*/
            try 
            {            
                sQ = "INSERT INTO logimpue(impue,                                   descrip,                            accio,          estac,    sucu,      nocaj,    falt) " + 
                             "VALUES('" + sCod.replace("'", "''") + "', '" +        sDescrip.replace("'", "''") + "',   'AGREGAR',      'INICIAL','INICIAL','INICIAL', now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            { 
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                       
            }
            
        }/*Fin de if(!bSi)*/              

    }/*Fin de private void vCreImpue(String sMed)*/
    
        
    /*Comprueba si el código del tipo existe, si no existe entonces crealo con su descripción*/
    private void vCreTip(String sVal, Connection con, String sCont, String sContCell)
    {
        /*Tokeniza*/
        StringTokenizer str = new StringTokenizer(sVal, ",");
        String sCod         = str.nextToken().trim().toUpperCase();
        
        /*Separa la descripción*/
        String sDescrip     = str.nextToken().trim().toUpperCase();  
        
        //Declara variables de la base de datos
        Statement   st;
        ResultSet   rs;        
        String      sQ; 
        
        /*Comprueba si el el código existe*/
        boolean bSi = false;
        try
        {
            sQ = "SELECT cod FROM tips WHERE cod = '" + sCod + "'";                        
            st = con.createStatement();
            rs = st.executeQuery(sQ);
            /*Si hay datos entonces coloca la bandera*/
            if(rs.next())
                bSi = true;                                                                        
        }
        catch(SQLException expnSQL)
        {
            //Procesa el error y regresa
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
            return;                                                   
        }  
	  
        /*Si el código no existe entonces*/
        if(!bSi)
        {
            /*Inserta el registro en la base de datos*/
            try 
            {                
                sQ = "INSERT INTO tips(cod,                                 estac,                                      descrip,                            falt,   fmod,           sucu,                                         nocaj) " + 
                          "VALUES('" + sCod.replace("'", "''") + "', '" +   Login.sUsrG.replace("'", "''") + "', '" +   sVal.replace("'", "''") + "',       now(),  now(), '" +     Star.sSucu.replace("'", "''") + "','" +       Star.sNoCaj.replace("'", "''") + "')";                                       
                st = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException expnSQL) 
             { 
                //Procesa el error y regresa
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                       
                return;                                                       
             }
                        
            /*Inserta en log de tipos*/
            try 
            {            
                sQ = "INSERT INTO logtip(cod,                                       descrip,                         accio,          estac,    sucu,      nocaj,    falt) " + 
                             "VALUES('" + sCod.replace("'", "''") + "', '" +        sDescrip.replace("'", "''") + "',   'AGREGAR',      'INICIAL','INICIAL','INICIAL', now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException expnSQL) 
            { 
                //Procesa el error
                Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                                                       
            }
            
        }/*Fin de if(!bSi)*/              

    }/*Fin de private void vCreTip(String sVal, Connection con, String sCont, String sContCell)*/
    
        
    /*Borra todos los registros de los prods*/
    private void vDelProds(Connection con)
    {
        //Declara variables de la base de datos
        Statement   st;                
        String      sQ; 




        /*Borra todos los registros de la base de datos de los prods*/
        try 
        {            
            sQ = "DELETE FROM prods";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException expnSQL) 
         {              
            //Procesa el error
            Star.iErrProc(this.getClass().getName() + " " + expnSQL.getMessage(), Star.sErrSQL, expnSQL.getStackTrace(), con);                                                                              
         }
	        
    }/*Fin de private void vDelProds()*/
            
    
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

    
    /*Cuando se activa la forma*/
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
        /*Función que realiza la importación de los prods a la base de datos*/
        th = new Thread()
        {
            @Override
            public void run()
            {
                vImpBD();
            }            
        };
        th.start();       
        
    }//GEN-LAST:event_formWindowActivated
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField jTInf;
    // End of variables declaration//GEN-END:variables
}
