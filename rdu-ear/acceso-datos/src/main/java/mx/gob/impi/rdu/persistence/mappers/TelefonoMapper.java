package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.Telefono;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.Usuario;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.SelectKey;
  
  /**
   *
   * interface TelefonoMapper que contiene los metodos de 
   * persistencia y consulta de la entidad telefono
   */
   
   public interface TelefonoMapper  { 

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo inserta un registro en  
     * la tabla telefono
     *
     * Asigna la llave primaria mediante una secuencia o autoincrementable 
     */

 
    /**@SelectKey( keyProperty="telefono", statement="SELECT IDENT_CURRENT('telefono')", resultType=Long.class, before=false) **/
    @SelectKey( keyProperty="idTelefono", statement="SELECT seq_id_telefono.currVal from dual ", resultType=Long.class, before=false) 
    
    @Insert("insert into telefono ( id_telefono, " +
    " tipo_telefono, " +
    " lada, " +
    " numero, " +
    " id_usuario) " +
     " values ( seq_id_telefono.nextVal ," +
    
    "#{tipoTelefono, jdbcType=VARCHAR }," +
    "#{lada, jdbcType=VARCHAR }," +
    "#{numero, jdbcType=VARCHAR }," +
    "#{idUsuario, jdbcType=BIGINT })")
    int insert(Telefono pTelefono);
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un registro en  
     * la tabla telefono
     * mediante la llave primaria  
     */
    @Select("select " +
    " id_telefono as idTelefono, " +
    " tipo_telefono as tipoTelefono, " +
    " lada as lada, " +
    " numero as numero, " +
    " id_usuario as idUsuario " +
     " from  telefono where " +
    
    
    " id_telefono = #{idTelefono}")
    Telefono getById(Telefono pTelefono);

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona todos los registros en  
     * la tabla telefono
     */
    @Select("select " +
    " telefono.id_telefono as idTelefono, " +
    " telefono.tipo_telefono as tipoTelefono, " +
    " telefono.lada as lada, " +
    " telefono.numero as numero, " +
    " telefono.id_usuario as idUsuario " +
     " from  telefono telefono "
            + "where id_usuario = #{idUsuario}" )
    List<Telefono> getAllTelefonosUsuario(Telefono telefono);
 
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo elimina un registro en  
     * la tabla telefono
     * mediante la llave primaria  
     */
    @Delete(" delete from telefono  where " +
    
    " id_telefono = #{idTelefono, jdbcType=BIGINT}")
    int delete(Telefono pTelefono);

    @Delete(" delete from telefono  where " +
    
    " id_usuario = #{idUsuario, jdbcType=BIGINT}")
    int deleteTelefonosUsuario(Usuario usuario);
 /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo actualiza un registro en  
     * la tabla telefono
     * mediante la llave primaria  
     */
    @Update(" UPDATE telefono  SET " +
    " tipo_telefono = #{tipoTelefono, jdbcType=VARCHAR}, " +
    " lada = #{lada, jdbcType=VARCHAR}, " +
    " numero = #{numero, jdbcType=VARCHAR}, " +
    " id_usuario = #{idUsuario, jdbcType=BIGINT} " +
     " where " +
    
    " id_telefono = #{idTelefono}")
    int update(Telefono pTelefono);
 
 
 @SelectProvider(type=TelefonoDynamic.class, method="selectTelefonoSql")
 List<Telefono> selectByExample(Telefono pTelefono);
 
 
 int deleteByExample(Telefono pTelefono);
 
    }