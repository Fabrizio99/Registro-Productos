package ConexionSQL;

import clases.Marca;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public class MarcaDB {
    public ArrayList<Marca> ListaMarcas(){
        ArrayList<Marca> listaMarcas = new ArrayList();
        try {
            Connection cnx = DBConnection.getConnection();
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery("  SELECT IDMARCA, NOMBRE, ESTADO FROM MARCA ORDER BY 1  ");
            while (rs.next()) {                
                Marca marca = new Marca(rs.getInt("IDMARCA"),rs.getString("NOMBRE"), rs.getString("ESTADO"));
                listaMarcas.add(marca);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error en el listado");
        }
        return listaMarcas;
    }
    
    public void insertarMarca(String marca, String estado){
        try{
            Connection cnx = DBConnection.getConnection();
            PreparedStatement pst = null;
            String sql = "INSERT INTO MARCA VALUES(SQ_MARCA.NEXTVAL,?,?)";
            pst = cnx.prepareStatement(sql);
            pst.setString(1, marca);
            pst.setString(2, estado);
            pst.executeQuery();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Error en el insert");
        }
    }
    
    public void editarMarca(int idmarca,String marca,String estado){
        try{
            Connection cnx = DBConnection.getConnection();
            CallableStatement stmt = cnx.prepareCall("{call sp_modificarmarca(?,?,?)}");
            stmt.setInt(1, idmarca);
            stmt.setString(2, marca);
            stmt.setString(3, estado);
            stmt.execute();
            System.out.println("done");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Error al invocar procedure sp_modificarmarca");
        }
    }
    public int cantidadMarcas(){
        try{
            Connection cnx = DBConnection.getConnection();
            CallableStatement stmt = cnx.prepareCall("{?= call fn_obtenercantidadmarcas}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.executeQuery();
            return stmt.getInt(1);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Error al invocar la función");
            return 0;
        }
    }
}
