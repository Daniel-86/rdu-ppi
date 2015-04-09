 package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.UsuarioPerfil;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.UpdateProvider;
  
  /**
   *
   * interface UsuarioPerfilMapper que contiene los metodos de 
   * persistencia y consulta de la entidad usuario_perfil
   */
   
   public interface UsuarioPerfilMapper  { 

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo inserta un registro en  
     * la tabla usuario_perfil
     *
     */

 
    @Insert("insert into usuario_perfil (  " +
    " id_perfil, " +
    " id_usuario) " +
     " values (" +
    
    "#{idPerfil, jdbcType=VARCHAR }," +
    "#{idUsuario, jdbcType=BIGINT })")
    int insert(UsuarioPerfil pUsuarioPerfil);
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un registro en  
     * la tabla usuario_perfil
     * mediante la llave primaria  
     */
    @Select("select " +
    " id_perfil as idPerfil, " +
    " id_usuario as idUsuario " +
     " from  usuario_perfil where " +
    
    
    " id_perfil = #{idPerfil} AND " + 
    " id_usuario = #{idUsuario}")
    UsuarioPerfil getById(UsuarioPerfil pUsuarioPerfil);

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona todos los registros en  
     * la tabla usuario_perfil
     */
    @Select("select " +
    " usuarioPerfil.id_perfil as idPerfil, " +
    " usuarioPerfil.id_usuario as idUsuario " +
     " from  usuario_perfil usuarioPerfil " )
    List<UsuarioPerfil> getAll();

    
    
       @Select("select " +
    " usuarioPerfil.id_perfil as idPerfil, " +
    " usuarioPerfil.id_usuario as idUsuario " +
     " from  usuario_perfil usuarioPerfil " +
      "where" +
      "  id_usuario = #{idUsuario}")               
    List<UsuarioPerfil> getPerfilAllUsuario(UsuarioPerfil usuarioPerfil);
       
       
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo elimina un registro en  
     * la tabla usuario_perfil
     * mediante la llave primaria  
     */
    @Delete(" delete from usuario_perfil  where " +
    // " id_perfil = #{idPerfil, jdbcType=VARCHAR} AND " + 
    " id_usuario = #{idUsuario, jdbcType=BIGINT}")
    int delete(UsuarioPerfil pUsuarioPerfil);
 
 
 /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo actualiza un registro en  
     * la tabla usuario_perfil
     * mediante la llave primaria  
     */
    @Update(" UPDATE usuario_perfil  SET " +
     " where " +
    
    " id_perfil = #{idPerfil} AND " + 
    " id_usuario = #{idUsuario}")
    int update(UsuarioPerfil pUsuarioPerfil);
 
 
 @SelectProvider(type=UsuarioPerfilDynamic.class, method="selectUsuarioPerfilSql")
 List<UsuarioPerfil> selectByExample(UsuarioPerfil pUsuarioPerfil);
 
 
 int deleteByExample(UsuarioPerfil pUsuarioPerfil);
 
    }