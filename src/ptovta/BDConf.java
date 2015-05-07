//Paquete
package ptovta;

/*Clase que es serializada para el archivo de configuración de la base de datos*/
public class BDConf implements java.io.Serializable
{
    /*Declara variables de la base de datos remota*/
    public String sInst;
    public String sUsr;
    public String sContra;
    public String sBD; 
    public String sPort; 
    
    /*Variable que contendrá la sucursal*/
    public String sSucur;
    
    /*Variabl que contendrá el nùmero de caja*/
    public String sNumCaj;
    
    /*Variable que contendrá el nombre de la empresa*/
    public String sNombEmp;
    
    /*Variable que contendrá si es estación de trabajo o no*/
    public String sEstacTrab;
    
    /*Declara variables de la base de datos local temporal*/
    public String sInstLoc;
    public String sUsrLoc;
    public String sContraLoc;
    public String sPortLoc;
    
}/*Fin de public class BDConf implements java.io.Serializable*/
