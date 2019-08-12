package ConexionSQL;

import clases.Producto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public class ProductoDB {
    public ArrayList<Producto> ListaProductos(){
        ArrayList<Producto> listaProductos = new ArrayList();
        try{
            Connection cnx = DBConnection.getConnection();
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery("select p.idproducto IDP, p.nombre nombre, p.precio precio, p.cantidad cantidad, m.nombre marca, p.estado estado from marca m,producto p  where p.marca = m.idmarca order by p.idproducto");
            while(rs.next()){
                Producto producto = new Producto(rs.getInt("IDP"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("cantidad"), rs.getString("marca"), rs.getString("estado"));
                listaProductos.add(producto);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error en el listado");
        }
        return listaProductos;
    }
    
    public void insertarProducto(String nombre,double precio,int cantidad, String marca, String estado){
        try {
            Connection cnx = DBConnection.getConnection();
            CallableStatement stmt = cnx.prepareCall(" {call sp_agregarProducto(?,?,?,?,?)} ");
            stmt.setString(1,nombre);
            stmt.setDouble(2,precio);
            stmt.setInt(3,cantidad);
            stmt.setString(4,marca);
            stmt.setString(5,estado);
            stmt.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error al invocar procedure sp_agregarProducto");
        }
    }
    public int cantidadProductos(){
        int cantidad = 0;
        try {
            Connection cnx = DBConnection.getConnection();
            CallableStatement stmt = cnx.prepareCall("{?=call fn_obtenercantidadproductos}");
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.executeQuery();
            cantidad = stmt.getInt(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error al invocar la funcion");
        }
        return cantidad;
    }
}
