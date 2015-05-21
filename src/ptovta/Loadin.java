//Paquete
package ptovta;

//Importaciones
import static ptovta.Princip.bIdle;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    
//    /*Este es el thread principal*/
//    private Thread          th;
    
    
    
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

        /*Declara variables de la base de datos*/
        PreparedStatement pst;
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
        int iConta = 0;

        /*Inicializa el contador de la celda por cada fila*/
        int iContCell;
        
        /*Recorre todas las hojas de excel*/
        Iterator<Row> rowIt     = sheet.iterator();
        
        while(rowIt.hasNext())
        {
            
            producto:{
                /*Recorre todas columnas del archivo*/
                Row row             = rowIt.next();

                /*Si el contador es igual a uno entonces continua ya que no quiero leer la primera fila de encabezados y que continue*/
                if(iConta < 1)
                {
                    ++iConta;
                    continue;
                }

                /*Variable para leer las celdas*/
                String sIn;

                /*Inicializa la consulta*/
                sQInsert    = "INSERT INTO prods(prod,  unid, servi, invent, metcost, descripcort, descrip,lin, tip, fab, marc, mode, codmed, med, pes, pesman, colo,codext, impue, infor, descprov, prodop1, prodop2, garan, cost, costre, prelist1,prelist2, prelist3, prelist4, prelist5, prelist6, prelist7, prelist8, prelist9, prelist10, esvta, solmaxmin,  min, max,bajcost, lotped, solser, exist, sucu,  nocaj, estac) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";                    

                //Contiene los valores que van en el query dentro de VALUES
                java.util.ArrayList<String> valores = new java.util.ArrayList<>();

                /*Cadena que contendrá lo que se mostrará al usuario*/
                String sCadFin  = "";

                /*Aqui se almacenará el código del producto que se esta importando para el log*/
                String sCodProd = "";    

                /*Aquí se almacenará la descripción del producto para el log*/
                String sDescrip = "";

                //Uso la variable para hacer entero el list
                Double d;
                
                //Uso para guardar tipo de celda
                int tipo;

                /*Recorre todas las celdas de la fila*/
                for(iContCell=1; iContCell <= 43; iContCell++) 
                {
                    /*Obtiene el objeto de la celda*/
                    Cell cell = row.getCell(iContCell, Row.CREATE_NULL_AS_BLANK);

                    //Obtengo el dato de la celda y lo convierto a string, en caso necesario
                    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
                        sIn = cell.getNumericCellValue()+"";  
                    else
                        sIn = cell.getStringCellValue();               

                    tipo = cell.getCellType();
                    /*Determina el tipo de celda que es*/
                    switch(iContCell)
                    {
                        case 1:{//codigo de producto
                            
                            //checa que la longitud de la cadena no sobrepase los limites del dato en la BD
                            sIn = Star.checaLongitud(255, sIn);
                            //si esta vacio o no tiene el formato permitido se brinca,los datos obligatorios no pueden ser vacios
                            if(!sIn.matches("[a-zA-Z\\d\\-]+") || Star.iExistProd(con, sIn) == 1) 
                                break producto;
                            else{
                                /*Ve creando la cadena a mostrar con el código del producto*/
                                sCadFin     += sIn + "  ";
                                valores.add(sIn);
                            }
                        }break;

                        
                        case 2:/*Código de la unidad*/
                        {
                            sIn = Star.checaLongitud(30, sIn);
                             //si esta vacio o no tiene el formato permitido se brinca,los datos obligatorios no pueden ser vacios
                            if(!sIn.matches("[\\w\\-/]+,.+")) 
                                break producto;
                            
                            /*Comprueba si el código de la unidad existe, si no existe entonces la crea con su descripción*/
                            vCrea(sIn, con, Integer.toString(iConta), Integer.toString(iContCell), "unids", "logunid", "cod", "descrip", "cod", "descrip");

                           //Checa si hubo errores y si no, agrega el valor
                            valores = agregaValor(sIn, valores, con);
                            /*Si hubo error entonces*/
                            if(bErr) return;    
                        }break;
                        case 3: case 4:  //Servicio e inventariable
                            //si el tipo de datos es numerico, se convierte en double con este metodo le quita los decimales a los numeros que pudiran haber escrito
                            if(tipo == 0 && !sIn.isEmpty())
                                sIn = quitaDoble(sIn);
                            
                            if (!sIn.matches("0|1")){
                                if (iContCell == 4 && valores.get(2).equals("0"))//si estoy en inventariable y servicio esta en falso
                                    valores.add("1");
                                else
                                    valores.add("0");
                            }
                            else{//si estoy en inventariable y servicio ya esta marcado, lo pongo en falso
                                if(iContCell == 4 ){
                                    if (valores.get(2).equals("1"))
                                        valores.add("0");
                                    else//si no esta marcado lo pongo en verdadero 
                                        valores.add("1");
                                }
                                else
                                    valores.add(sIn);
                            }
                                
                            break;
                        case 5:
                            sIn = Star.checaLongitud(7, sIn);
                            if(!sIn.trim().matches("peps|ueps|ultcost|prom")) 
                                break producto;  
                            valores.add(sIn);
                            break;
                        case 6:{//Descripción larga
                        sIn = Star.checaLongitud(2100, sIn);

                        //si esta vacio o no tiene el formato permitido 
                        if(!sIn.matches("[a-zA-Z\\d\\- ]+")) 
                            valores.add("");
                        else{
                        /*Obtiene la descripción del producto*/
                            sDescrip    = sIn;

                            /*Ve creando la cadena a mostrar con la descripción del producto*/
                            sCadFin     += sDescrip + "  ";

                            /*Completa la consulta con todas las celdas*/
                            valores.add(sIn.replace("'", "''")); 
                        }
                        }break;
                        case 7://nombre (descripcion corta)
                            sIn = Star.checaLongitud(255, sIn);
                            valores.add(sIn.replace("'", "''"));
                            break;
                        case 8:{//Código de línea
                            sIn = Star.checaLongitud(30, sIn);
                             //si esta vacio o no tiene el formato permitido 
                            if(!sIn.matches("[a-zA-Z\\d]+,.+")) 
                                valores.add("");
                            else{
                                /*Comprueba si el código de la línea existe, si no existe entonces la crea con su descripción*/
                                vCrea(sIn, con, Integer.toString(iConta), Integer.toString(iContCell), "lins", "loglins", "cod", "descrip", "cod", "descrip");
                                //Checa si hubo errores y si no, agrega el valor
                                valores = agregaValor(sIn, valores, con);
                                /*Si hubo error entonces*/
                                if(bErr) return;  
                            }
                            }break;
                        case 9:/*Tipo*/
                            {             
                                sIn = Star.checaLongitud(30, sIn);
                                //si esta vacio o no tiene el formato permitido 
                               if(!sIn.matches("[a-zA-Z\\d]+,.+")) 
                                   valores.add("");
                                else{
                            
                                    sIn = Star.checaLongitud(30, sIn);
                                    /*Comprueba si el código del tipo existe, si no existe entonces crealo con su descripción*/
                                    vCrea(sIn, con, Integer.toString(iConta), Integer.toString(iContCell), "tips", "logtip",  "cod", "descrip", "cod", "descrip");

                                    //Checa si hubo errores y si no, agrega el valor
                                    valores = agregaValor(sIn, valores, con);
                                    /*Si hubo error entonces*/
                                    if(bErr) return;     
                               }
                            }break;
                        case 10:/*Código del fabricante*/
                        {
                            sIn = Star.checaLongitud(30, sIn);
                             //si esta vacio o no tiene el formato permitido 
                            if(!sIn.matches("[a-zA-Z\\d]+,.+"))
                                valores.add("");
                            else{

                                /*Comprueba si el código del fabricante existe, si no existe entonces la crea con su descripción*/
                                vCrea(sIn, con, Integer.toString(iConta), Integer.toString(iContCell),"fabs", "logfabs",  "cod", "descrip", "cod", "descrip");

                                //Checa si hubo errores y si no, agrega el valor
                                valores = agregaValor(sIn, valores, con);
                                /*Si hubo error entonces*/
                                if(bErr) return;
                            }
                        }break;
                        case 11:/*Código de la marca*/
                            {
                                sIn = Star.checaLongitud(30, sIn);
                                //si esta vacio o no tiene el formato permitido 
                               if( !sIn.matches("[a-zA-Z\\d]+,.+"))
                                   valores.add("");
                               else{

                                    /*Comprueba si el código de la línea marca, si no existe entonces la crea con su descripción*/
                                    vCrea(sIn, con, Integer.toString(iConta), Integer.toString(iContCell), "marcs", "logmarc",  "cod", "descrip", "cod", "descrip");                                                                

                                    //Checa si hubo errores y si no, agrega el valor
                                    valores = agregaValor(sIn, valores, con);
                                    /*Si hubo error entonces*/
                                    if(bErr) return;     
                               }
                            } break;
                        case 12:{//modelo
                            sIn = Star.checaLongitud(30, sIn);
                             //si esta vacio o no tiene el formato permitido 
                            if( !sIn.matches("[a-zA-Z\\d]+,.+"))
                                valores.add("");
                            else{
                                /*Comprueba si el código del modelo existe, si no existe entonces la crea con su descripción*/
                               vCrea(sIn, con, Integer.toString(iConta), Integer.toString(iContCell), "model", "logmod",  "cod", "descrip", "cod", "descrip");

                               //Checa si hubo errores y si no, agrega el valor
                               valores = agregaValor(sIn, valores, con);
                               /*Si hubo error entonces*/
                               if(bErr) return;
                            }
                        }break;
                        case 13:  { //Código de medida
                            sIn = Star.checaLongitud(30, sIn);
                             //si esta vacio o no tiene el formato permitido 
                            if( !sIn.matches("[a-zA-Z]+,.+"))
                                valores.add("");
                            else{

                                /*Comprueba si el código de la medida existe, si no existe entonces la crea con su descripción*/
                                vCrea(sIn, con, Integer.toString(iConta), Integer.toString(iContCell),"meds", "logmed",  "cod", "descrip", "cod", "descrip");

                                //Checa si hubo errores y si no, agrega el valor
                                valores = agregaValor(sIn, valores, con);
                                /*Si hubo error entonces*/
                                if(bErr) return;
                            }
                        }break;
                        case 14: case 16:
                             //si esta vacio o no tiene el formato permitido se brinca,los datos obligatorios no pueden ser vacios
                            if(!sIn.matches("(\\d+)?(\\.\\d{1,6})?")) 
                                valores.add("");
                            else
                                valores.add(sIn); 
                            break;
                        case 15:/*Código del peso*/
                        {
                            sIn = Star.checaLongitud(30, sIn);
                             //si esta vacio o no tiene el formato permitido 
                            if(!sIn.matches("[a-zA-Z]+,.+"))
                                valores.add("");
                            else{
                                /*Comprueba si el código del pes existe, si no existe entonces la crea con su descripción*/
                                vCrea(sIn, con, Integer.toString(iConta), Integer.toString(iContCell), "pes", "logpes",  "cod", "descrip", "cod", "descrip");

                                //Checa si hubo errores y si no, agrega el valor
                                valores = agregaValor(sIn, valores, con);
                                /*Si hubo error entonces*/
                                if(bErr) return;  
                            }
                        }break;
                        case 17:/*Código del color*/
                            {
                                sIn = Star.checaLongitud(30, sIn);
                                //si esta vacio o no tiene el formato permitido s
                               if(!sIn.matches("[\\w\\-/]+,.+"))
                                   valores.add("");
                               else{
                                    /*Comprueba si el código del color existe, si no existe entonces la crea con su descripción*/
                                    vCrea(sIn, con, Integer.toString(iConta), Integer.toString(iContCell), "colos", "logcolo",  "cod", "descrip", "cod", "descrip");

                                    //Checa si hubo errores y si no, agrega el valor
                                    valores = agregaValor(sIn, valores, con);
                                    /*Si hubo error entonces*/
                                    if(bErr) return;    
                               }
                            }break;
                        case 18:/*Clasificacion extra*/
                        {                         
                            sIn = Star.checaLongitud(30, sIn);
                            if(sIn.isEmpty())
                                valores.add("");
                            else{
                                /*Comprueba si el código del impuesto existe, si no existe entonces crea con su descripción*/
                                vCrea(sIn, con, Integer.toString(iConta), Integer.toString(iContCell), "clasprod", "logprods",  "cod", "descrip", "cod", "descrip");

                                //Checa si hubo errores y si no, agrega el valor
                                valores = agregaValor(sIn, valores, con);
                                /*Si hubo error entonces*/
                                if(bErr) return;   
                            }
                        }break;
                        case 19:/*Impuesto*/
                            {                      
                                sIn = Star.checaLongitud(30, sIn);
                                //si esta vacio o no tiene el formato permitido 
                               if( !sIn.matches("[a-zA-Z]+,(\\d+)?(\\.\\d{1,2})?")) 
                                    valores.add("");
                                else{
                                    /*Comprueba si el código del impuesto existe, si no existe entonces crea con su descripción*/
                                    vCrea(sIn, con, Integer.toString(iConta), Integer.toString(iContCell), "impues", "logimpues", "codimpue", "impueval", "cod", "val");

                                    //Checa si hubo errores y si no, agrega el valor
                                    valores = agregaValor(sIn, valores, con);
                                    /*Si hubo error entonces*/
                                    if(bErr) return;    
                               }
                            }break;
                        case 20: case 21://Informacion, anotaciones
                            if(iContCell == 20)
                                sIn = Star.checaLongitud(255, sIn);
                            else
                                sIn = Star.checaLongitud(100, sIn);
                            valores.add(sIn.replace("'", "''")); 
                            break;
                        case 22: case 23:
                            sIn = Star.checaLongitud(30, sIn);
                            //si esta vacio o no tiene el formato permitido se brinca,los datos obligatorios no pueden ser vacios
                            if( !sIn.matches("[a-zA-Z\\d\\-]+")) 
                                valores.add("");
                            else
                                valores.add(sIn.replace("'", "''")); 
                            break;
                        case 24:/*Garantia*/
                            {                             
                                sIn = Star.checaLongitud(30, sIn);
                                //si esta vacio o no tiene el formato permitido se brinca,los datos obligatorios no pueden ser vacios
                               if( !sIn.matches("[a-zA-Z\\d]+,.+"))
                                   valores.add("");
                                else{

                                    /*Comprueba si el código del impuesto existe, si no existe entonces crea con su descripción*/
                                    vCrea(sIn, con, Integer.toString(iConta), Integer.toString(iContCell), "garan", "loggara", "gara", "descrip", "cod", "descrip");

                                    //Checa si hubo errores y si no, agrega el valor
                                    valores = agregaValor(sIn, valores, con);
                                    /*Si hubo error entonces*/
                                    if(bErr) return; 
                                }
                            }break;
                        
                         case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 32: case 33: case 34: case 35: case 36: 
                             //si el tipo de datos es numerico, se convierte en double con este metodo le quita los decimales a los numeros que pudiran haber escrito
                            if(tipo == 0 && !sIn.isEmpty())
                                sIn = quitaDoble(sIn);
                            
                             if( !sIn.matches("(\\d+)?(\\.\\d{1,2})?")) 
                                 valores.add("0");
                             else
                             {
                                 if(sIn.isEmpty())
                                    valores.add("0");
                                 else
                                    valores.add(sIn);
                             }
                            break;
                             
                            //Casos con valor de tipo BIT
                        case 37: case 38: case 41: case 42: case 43:
                            //si el tipo de datos es numerico, se convierte en double con este metodo le quita los decimales a los numeros que pudiran haber escrito
                            if(tipo == 0 && !sIn.isEmpty())
                                sIn = quitaDoble(sIn);
                            
                            if (!sIn.matches("0|1")) 
                            {
                                if(iContCell == 38 || iContCell == 37)
                                    valores.add("1");
                                else
                                    valores.add("0"); 
                            }
                               
                           else
                               valores.add(sIn);
                            break;
                        case 39: case 40://maximos y minimos
                            //Si No solicitar maximos y minimos esta marcado los pone en 1
                            if(Integer.parseInt(valores.get(37)) == 1)
                                valores.add("0");
                            else{
                                //si el tipo de datos es numerico, se convierte en double con este metodo le quita los decimales a los numeros que pudiran haber escrito
                                if(tipo == 0 && !sIn.isEmpty())
                                    sIn = quitaDoble(sIn);
                            
                                if (!sIn.matches("[\\d]+"))
                                {
                                    if(iContCell==39)
                                    valores.add("1");
                                    else
                                        valores.add(Integer.parseInt(valores.get(valores.size()-1))+1+"");
                                }
                                else
                                    valores.add(sIn);
                            }
                            break;
                            
                        default: valores.add(sIn.replace("'", "''")); 
                    }                                                           

                    /*Aumenta en uno el contador de la celda*/
    //                ++iContCell;

                }/*Fin de while (cellIterator.hasNext())*/

                /*Coloca en el campo para que sea visible que código se esta procesando*/
                jTInf.setText(sCadFin);                    

               /*Inserta en la base de datos el registro*/
                try
                {
                    //(codemp, ser, nom, list, calle, col, noext, cp, ciu, estad, pai, rfc, co1, codclas, estac, sucu, nocaj)
                    pst = con.prepareStatement(sQInsert );      

                    //recorro todo el array y agrega los valores
                    for (int i = 0; i < valores.size(); i++) {
                        switch(i){
                            case 38: case 39:
                                //casos especiales, donde el tipo de dato es integer
                                if(!valores.get(i).isEmpty())
                                    pst.setInt(i + 1, Integer.parseInt(valores.get(i))); break;
                            case 13: case 15: case 24: case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 32: case 33: case 34: case 35:
                                //casos especiales, donde el tipo de dato es flotante
                                if(!valores.get(i).isEmpty())
                                    pst.setFloat(i + 1,Float.parseFloat(valores.get(i))); break;
                            case 2: case 3: case 36: case 37: case 40:  case 41: case 42:
                                //casos especiales, donde el tipo de dato es boolean
                                if(!valores.get(i).isEmpty())
                                    pst.setBoolean(i + 1, (Integer.parseInt(valores.get(i)) == (1))); break;
                            default: pst.setString(i + 1,valores.get(i));
                        }
                    }    
                    pst.setString(44,"0");
                    pst.setString(45,Login.sUsrG);  //agrego estac 
                    pst.setString(46,Star.sSucu);  //agrego sucu
                    pst.setString(47,Star.sNoCaj); //agrego nocaj

                    pst.executeUpdate();
                }
                catch(SQLException | HeadlessException e)
                {                        

                    /*Esconde el loading*/
                    if(Star.lCargGral!=null)
                        Star.lCargGral.setVisible(false);

                    //Cierra la base de datos y regresa
                    if(Star.iCierrBas(con)==-1)                                
                        return;

                    /*Agrega en el log*/
                    Login.vLog(e.getMessage());

                    /*Llama al recolector de basura y mensajea*/
                    System.gc();                       

                    /*Mensajea y regresa*/
                    JOptionPane.showMessageDialog(null, "Error en fila: "  + iConta + " celda: " + iContCell + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));                    
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
                catch(SQLException ex) 
                { 
                    //Cierra la base de datos y sal de la aplicación
                    if(Star.iCierrBas(con)==-1)
                        return;

                    /*Agrega en el log*/
                    Login.vLog(ex.getMessage());


                    /*Mensajea y cierra la forma*/
                    JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                 
                    this.dispose();                        
                 }
            }//fin del label producto:
            
            
            
        }/*Fin de while (rowIt.hasNext())*/

        /*Cierra el fichero*/
        try
        {
            file.close();       
        }
        catch(Exception e)
        {
            /*Llama al recolector de basura*/
            System.gc();

            /*Cierra la forma*/
            this.dispose();

            /*Agrega en el log*/
            Login.vLog(e.getMessage());

            /*Llama al recolector de basura*/
            System.gc();

            /*Mensajea y cierra la forma*/
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error por " + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconAd)));                 
            this.dispose();                        
        }            
        this.dispose();
    }/*Fin de private void vImpBD*/
  
    // (meds, logmed), (lins, loglins), (marcs, logmarcs), (models, logmod), (fabs, logfabs), (pes, logpes), (colos, logcolo), (unids, logunid), (clasprod, logprods), (tips, logtip)
    /*Comprueba si el código de la med, lins, marcs, models, pes,colos, unids, clasprod, tips  existe, si no existe entonces la crea con su descripción*/
    private void vCrea(String sVal, Connection con, String sCont, String sContCell, String tabla, String tablaLog, String colCod, String colDescrip, String colCodLog, String colDescripLog)
    {
        /*Tokeniza*/
        StringTokenizer str = new StringTokenizer(sVal, ",");
        String sCod;
        
        /*Separa el código*/
        try
        {
            sCod            = str.nextToken().trim().toUpperCase();
        }
        catch(Exception e)
        {
            /*Coloca la bandera para saber que hubo error*/
            bErr            = true;
            
            //Cierra la base de datos y sal de la aplicación
            if(Star.iCierrBas(con)==-1)
                return;

            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "Error en fila: " + sCont + " celda: " + sContCell + " por " + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }        
        
        /*Separa la descripción*/
        String sDescrip;
        try
        {
            sDescrip     = str.nextToken().trim().toUpperCase();  
        }
        catch(Exception e)
        {
            //Cierra la base de datos y sal de la aplicación
            if(Star.iCierrBas(con)==-1)
                return;
            
            /*Coloca la bandera para saber que hubo error*/
            bErr            = true;
            
            /*Mensajea y regresa*/
            JOptionPane.showMessageDialog(null, "Error en fila: " + sCont + " celda: " + sContCell + " por " + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr))); 
            return;
        }         
        
        /*Si la descripción es un punto entonces que sea cadena vacia*/
        if(sDescrip.compareTo(".")==0)
            sDescrip        = "";
        
        /*Declara variables de la base de datos*/
        Statement   st;      
        String      sQ              = ""; 
        
        //Comprueba si la medida existe
        int iRes    = Star.iExiste(con, sCod.trim(), tabla, colCod);
                
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
                sQ      = "INSERT INTO "+tabla+"("+colCod+",                                estac,                   "+colDescrip+",                            falt,   fmod,           sucu,                                           nocaj) " + 
                               "VALUES('" + sCod.replace("'", "''") + "', '" +  Login.sUsrG.replace("'", "''") + "', '" +   sDescrip.replace("'", "''") + "',   now(),  now(), '" +     Star.sSucu.replace("'", "''") + "','" +   Star.sNoCaj.replace("'", "''") + "')";                                       
                st      = con.createStatement();
                st.executeUpdate(sQ);
             }
             catch(SQLException | HeadlessException e) 
             { 
                 //Cierra la base de datos y sal de la aplicación
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Agrega en el log*/
                Login.vLog(e.getMessage());                    
                
                /*Coloca la bandera para saber que hubo error*/
                bErr            = true;
            
                /*Mensajea y regresa*/
                JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));                  
                return;                   
             }

            /*Inserta en log de meds*/
            try 
            {            
                sQ = "INSERT INTO "+tablaLog+"("+colCodLog+",              "+colDescripLog+",                            accio,          estac,    sucu,      nocaj,    falt) " + 
                             "VALUES('" + sCod.replace("'", "''") + "', '" +        sDescrip.replace("'", "''") + "',   'AGREGAR',      'INICIAL','INICIAL','INICIAL', now())";                                
                st = con.createStatement();
                st.executeUpdate(sQ);
            }
            catch(SQLException ex) 
            { 
                //Cierra la base de datos y sal de la aplicación
                if(Star.iCierrBas(con)==-1)
                    return;

                /*Coloca la bandera para saber que hubo error*/
                bErr            = true;
            
                /*Agrega en el log y mensajea*/
                Login.vLog(ex.getMessage());                                
                JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + ex.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));                                
            }
                        
        }/*if(!bSi)*/
                
    }// fin de un metodo que se ahorra mas de 1000 lineas,exito (yn)
    
        
    /*Borra todos los registros de los prods*/
    private void vDelProds(Connection con)
    {
        /*Declara variables de la base de datos*/
        Statement   st;                
        String      sQ              = ""; 

        /*Borra todos los registros de la base de datos de los prods*/
        try 
        {            
            sQ = "DELETE FROM prods";                    
            st = con.createStatement();
            st.executeUpdate(sQ);
         }
         catch(SQLException | HeadlessException e) 
         { 
            //Cierra la base de datos y sal de la aplicación    
            if(Star.iCierrBas(con)==-1)
                return;

            /*Agrega en el log y mensajea*/
            Login.vLog(e.getMessage());                        
            JOptionPane.showMessageDialog(null, this.getClass().getName() + " Error en " + sQ + " por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(Star.sRutIconEr)));             
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
//        th = new Thread()
//        {
//            @Override
//            public void run()
//            {
                vImpBD();
//            }            
//        };
//        th.start();       
        
    }//GEN-LAST:event_formWindowActivated
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField jTInf;
    // End of variables declaration//GEN-END:variables

    private ArrayList<String> agregaValor(String sIn, ArrayList<String> valores, Connection con) {
        if(bErr)
        {
            //Cierra la base de datos y sal de la aplicación
            if(Star.iCierrBas(con)==-1)
                return valores;

            /*Cierra y regresa*/
            dispose();                                    
            return valores; 
        }

        /*Obtiene solo el código*/
        StringTokenizer str = new StringTokenizer(sIn, ",");

        /*Completa la consulta con todas las celdas*/
        valores.add(str.nextToken().trim().toUpperCase().replace("'", "''")); 
        return valores;
    }
    
    private String quitaDoble(String sIn) {
        Double d = Double.parseDouble(sIn);
        
        return d.intValue()+"";
    }
    
}
