/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptovta;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class ConexionBD {
    private static Connection con;
    private static PreparedStatement sentencia;
    private static ResultSet result;
    
    private ConexionBD(){
         
    }
    
    public static Connection getConexion(){
        try 
        {
            con = DriverManager.getConnection("jdbc:mysql://" + Star.sInstancia + ":" + Star.sPort + "/" + Star.sBD + "?user=" + Star.sUsuario + "&password=" + Star.sContrasenia );               
        } 
        catch(SQLException | HeadlessException e) 
        {    
            /*Agrega en     el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/    
            JOptionPane.showMessageDialog(null,"Error in " + e.getMessage() + " in line " + Thread.currentThread().getStackTrace()[2].getLineNumber()); 
        }
        return con;
    }
    
    public static ResultSet ejecutarQuery(String query, Object... parameters){
        int tipoElem = 0,totParametros = 0;
        Connection conn = ConexionBD.getConexion();
        int repetidos = cantidadOcurrencias(query,"?");
        try{
            sentencia = conn.prepareStatement(query);
            if(parameters.length < repetidos){
                totParametros = repetidos;
            }else if(repetidos == parameters.length){
                totParametros = parameters.length;
            }else{
                return null;
            }
            
            if(parameters.length > 0){
                for(int i = 0; i < totParametros; i++)
                {
                    if(parameters[i] instanceof String)
                        tipoElem = java.sql.Types.VARCHAR;
                    else if(parameters[i] instanceof Integer)
                        tipoElem = java.sql.Types.INTEGER;
                    else if(parameters[i] == null)
                        tipoElem = java.sql.Types.NULL;
                    else if(parameters[i] instanceof Double)
                        tipoElem = java.sql.Types.DOUBLE;
                    else if(parameters[i] instanceof Boolean)
                        tipoElem = java.sql.Types.BOOLEAN;
                    else if(parameters[i] instanceof Byte || parameters[i] instanceof Byte[])
                        tipoElem = java.sql.Types.BLOB;    
                    else if(parameters[i] instanceof Date)
                        tipoElem = java.sql.Types.DATE;
                    else if(parameters[i] instanceof Float)
                        tipoElem = java.sql.Types.FLOAT;
                    else if(parameters[i] instanceof Long)
                        tipoElem = java.sql.Types.BIGINT;
                    else
                        tipoElem = java.sql.Types.JAVA_OBJECT;
                    if(i >= parameters.length){
                       sentencia.setObject(i+1,parameters[parameters.length-1],tipoElem);
                    }else{
                        sentencia.setObject(i+1,parameters[i],tipoElem);
                    }
                }
            }
            
            result = sentencia.executeQuery();
        }catch(SQLException e){
            /*Agrega en el log*/
            Login.vLog(e.getMessage());
            
            /*Mensajea y regresa*/    
            JOptionPane.showMessageDialog(null,"Error in " + e.getMessage() + " in line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }finally{
            //ConexionBD.closeCon(conn);
        }
        return result;
    }
    
private static int cantidadOcurrencias(String cadena, String patron)
{
   int cant = 0;
   while (cadena.indexOf(patron) > -1)
   {
     cadena = cadena.substring(cadena.indexOf(patron)+patron.length(),cadena.length());
     cant++;
   }
   return cant;
   
}

public static void ejecutarUpdate(String sentencia){
    Statement st;
    Connection conn = ConexionBD.getConexion();
    int si;
    
    try{
        st = conn.createStatement();
        si = st.executeUpdate(sentencia);
    }catch(SQLException e){
        /*Agrega en     el log*/
            Login.vLog(e.getMessage());
            
        /*Mensajea y regresa*/    
        JOptionPane.showMessageDialog(null,"Error in " + e.getMessage() + " in line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }finally{
        ConexionBD.closeCon(conn);
    }
    
}

public static void cleanDatabase(){
    Connection conn = ConexionBD.getConexion();
    
    try{
        Statement st = conn.createStatement();
        Statement st2 = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT Concat('TRUNCATE TABLE ',table_schema,'.',TABLE_NAME, ';') AS SENTENCIA\n" +
        "FROM INFORMATION_SCHEMA.TABLES TR where  table_schema in ('ele','ele_tmp') AND TABLE_NAME NOT IN ('basdats','estacs');");
        
        st2.execute("SET FOREIGN_KEY_CHECKS=0;");
        while(rs.next()){
            st2.execute(rs.getString("SENTENCIA"));
        }
        st2.execute("SET FOREIGN_KEY_CHECKS=1;");
    }catch(SQLException e){
        /*Agrega en     el log*/
        Login.vLog(e.getMessage());
            
        /*Mensajea y regresa*/    
        JOptionPane.showMessageDialog(null,  Star.class + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon( Star.class.getClass().getResource(Star.sRutIconEr)));
    }finally{
        ConexionBD.closeCon(conn);
    }
}


public static void closeCon(Connection con){
    try{
        con.close();
    }catch(SQLException e){
        /*Agrega en     el log*/
            Login.vLog(e.getMessage());
            
        /*Mensajea y regresa*/    
        JOptionPane.showMessageDialog(null,  Star.class + " Error por " + e.getMessage(), "Error BD", JOptionPane.INFORMATION_MESSAGE, new ImageIcon( Star.class.getClass().getResource(Star.sRutIconEr)));
    }
}
    
}
