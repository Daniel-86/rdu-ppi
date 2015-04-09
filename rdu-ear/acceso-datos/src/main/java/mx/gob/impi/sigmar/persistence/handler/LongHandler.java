/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sigmar.persistence.handler;

/**
 *
 * @author jess
 */
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
 

public class LongHandler implements TypeHandler{
 
    
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) 
           throws SQLException {
        
        System.out.println("****************setParameter*************");
        
        String s = (String) parameter;
        StringReader reader = new StringReader(s);
        ps.setCharacterStream(i, reader, s.length());
    }
 
    
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        System.out.println("****************getResult1*************");
        System.out.println(columnName);
//        System.out.println(rs.getClass());
//        System.out.println(rs.getStatement().toString());
//        System.out.println(rs.getMetaData().getColumnTypeName(1));
//        System.out.println(rs.getMetaData().getColumnTypeName(2));
//        System.out.println(rs.isClosed());
        
        //rs.first();
        
//        String sql = "SELECT PRODUCTO FROM MARCAS.solicitud_captura WHERE " +
//            "tipo_solicitud = 1 and " + 
//            "ano_solicitud = 1985 and " +
//            "expediente = 382915";
        
        //ResultSet rs2 = rs.getStatement().executeQuery(sql);
        //rs2.getString(1);
        
        //rs.getString(columnName);
        //rs.getObject(2);
        //rs.getObject(1);
        //rs.getCharacterStream(columnName);
        //rs.getAsciiStream(2);
        //rs.getBinaryStream(columnName);
        //rs.getBlob(columnName);
        //rs.getByte(columnName);
        //rs.getBytes(columnName);
        //rs.getCharacterStream(columnName);
        //rs.getClob(columnName);
        //rs.getNCharacterStream(columnName);
        //rs.getLong(columnName);
        //rs.getBoolean(columnName);
        //rs.getNClob(columnName);
        //rs.getNString(columnName);
        //rs.getObject(columnName);
        //rs.getObject(columnName, Wrapper.class);
        //rs.getUnicodeStream(columnName);
        
        
            return "algunos productos";
    }
 
    
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        System.out.println("****************getResult2*************");
        return "algunos productos";
        //return cs.getString(columnIndex);
    }

    
    
    
}
