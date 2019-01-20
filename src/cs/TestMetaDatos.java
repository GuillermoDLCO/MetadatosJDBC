package cs;

import datos.Conexion;
import java.sql.*;
import oracle.jdbc.OracleResultSetMetaData;

public class TestMetaDatos {
    
    public static void main(String args[]){
        
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM employees");
            //Obtener un objeto metadado de Oracle
            OracleResultSetMetaData rsOracle = (OracleResultSetMetaData) rs.getMetaData();
            
            if (rsOracle == null) {
                System.out.println("Metadatos no disponibles");                
            } else {
                //Preguntar # de columnas de la tabla empleados
                int columnCount = rsOracle.getColumnCount();
                
                System.out.println("No. columnas: " + columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    //Nombre y tipó de la columna
                    System.out.print("Nombre Columna: " + rsOracle.getColumnName(i));
                    System.out.print(", Tipo Columna: " + rsOracle.getColumnTypeName(i));
                    //¿La columna puede almanecar valores nulos?
                    switch (rsOracle.isNullable(columnCount)){
                        case OracleResultSetMetaData.columnNoNulls:
                            System.out.println(", No acepta Nulos");
                            break;
                        case OracleResultSetMetaData.columnNullable:
                            System.out.print(", Si acepta Nulos");
                            break;
                        case OracleResultSetMetaData.columnNullableUnknown:
                            System.out.print(", Valor nulo desconocido");
                    }
                    System.out.println("");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(con);
            Conexion.close(rs);
        }
    }
}
