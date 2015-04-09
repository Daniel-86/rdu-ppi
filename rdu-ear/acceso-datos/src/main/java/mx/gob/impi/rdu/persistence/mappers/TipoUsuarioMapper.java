
  package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.TipoUsuario;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.SelectProvider;
  
  /**
   *
   * interface TipoUsuarioMapper que contiene los metodos de 
   * persistencia y consulta de la entidad tipo_usuario
   */
   
   public interface TipoUsuarioMapper  { 

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo inserta un registro en  
     * la tabla tipo_usuario
     *
     */

	
    @Insert("insert into tipo_usuario (  " +
    " id_tipo, " +
    " nombre) " +
     " values (" +
    
    "#{idTipo, jdbcType=SMALLINT }," +
    "#{nombre, jdbcType=VARCHAR })")
    int insert(TipoUsuario pTipoUsuario);
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un registro en  
     * la tabla tipo_usuario
     * mediante la llave primaria 	
     */
    @Select("select " +
    " id_tipo as idTipo, " +
    " nombre as nombre " +
     " from  tipo_usuario where " +
    
    
    " id_tipo = #{idTipo}")
    TipoUsuario getById(TipoUsuario pTipoUsuario);

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona todos los registros en  
     * la tabla tipo_usuario
     */
    
    List<TipoUsuario> getAll();
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo elimina un registro en  
     * la tabla tipo_usuario
     * mediante la llave primaria 	
     */
    @Delete(" delete from tipo_usuario  where " +
    
    " id_tipo = #{idTipo, jdbcType=SMALLINT}")
    int delete(TipoUsuario pTipoUsuario);
	
	
	/**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo actualiza un registro en  
     * la tabla tipo_usuario
     * mediante la llave primaria 	
     */
    @Update(" UPDATE tipo_usuario  SET " +
    " nombre = #{nombre, jdbcType=VARCHAR} " +
     " where " +
    
    " id_tipo = #{idTipo}")
    int update(TipoUsuario pTipoUsuario);
	
	
	@SelectProvider(type=TipoUsuarioDynamic.class, method="selectTipoUsuarioSql")
	List<TipoUsuario> selectByExample(TipoUsuario pTipoUsuario);
	
	
	int deleteByExample(TipoUsuario pTipoUsuario);
	
    }
  