package ConexionSQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    public static Connection getConnection(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String myDB = "jdbc:oracle:thin:@DESKTOP-K4NOVPG:1521:XE [bdproductos on BDPRODUCTOS]";
            String usuario = "bdproductos";
            String password = "123";
            Connection cnx = DriverManager.getConnection(myDB,usuario,password);
            return cnx;
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        } catch(ClassNotFoundException ex){
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE,null,ex);
        }
        return null;
    }
}
