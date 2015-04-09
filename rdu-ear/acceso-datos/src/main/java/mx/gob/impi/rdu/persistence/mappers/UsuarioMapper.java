
  package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.Usuario;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.CoordinacionEstatal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
  
  /**
   *
   * interface UsuarioMapper que contiene los metodos de 
   * persistencia y consulta de la entidad usuario
   */
   
   public interface UsuarioMapper  { 

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃ©todo inserta un registro en  
     * la tabla usuario
     *
     * Asigna la llave primaria mediante una secuencia o autoincrementable	
     */

	
    /**@SelectKey( keyProperty="usuario", statement="SELECT IDENT_CURRENT('usuario')", resultType=Long.class, before=false) **/
    @SelectKey( keyProperty="idUsuario", statement="SELECT seq_id_usuario.currVal from dual ", resultType=Long.class, before=false) 
	
    @Insert("insert into usuario (id_usuario,  " +
    " nombre, " +
    " apellido_paterno, " +
    " apellido_materno, " +
    " id_domicilio, " +
    " id_tipo, " +
    " id_dependencia, " +    
    " id_coordinacion, " +
    " id_pais) " +
     " values ( seq_id_usuario.nextVal ," +
    
    "#{nombre, jdbcType=VARCHAR }," +
    "#{apellidoPaterno, jdbcType=VARCHAR }," +
    "#{apellidoMaterno, jdbcType=VARCHAR }," +
    "#{idDomicilio, jdbcType=BIGINT }," +
    "#{idTipo, jdbcType=SMALLINT }," +
    "#{idDependencia, jdbcType=SMALLINT }," +    
    "#{idCoordinacionEstatal, jdbcType=SMALLINT }," +    
    "#{idPais, jdbcType=BIGINT })")
    int insert(Usuario pUsuario);
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃ©todo selecciona un registro en  
     * la tabla usuario
     * mediante la llave primaria 	
     */
    @Select("select " +
    " id_usuario as idUsuario, " +
    " nombre as nombre, " +
    " apellido_paterno as apellidoPaterno, " +
    " apellido_materno as apellidoMaterno, " +
    " id_domicilio as idDomicilio, " +
    " id_tipo as idTipo, " +
    " id_dependencia as idDependencia, " +    
    " id_coordinacion as idCoordinacionEstatal, " +
    " id_pais as idPais " +
     " from  usuario where " +    
    
    " id_usuario = #{idUsuario}")
    Usuario getById(Usuario pUsuario);

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃ©todo selecciona todos los registros en  
     * la tabla usuario
     */
    @Select("select " +
    " usuario.id_usuario as idUsuario, " +
    " usuario.nombre as nombre, " +
    " usuario.apellido_paterno as apellidoPaterno, " +
    " usuario.apellido_materno as apellidoMaterno, " +
    " usuario.id_domicilio as idDomicilio, " +
    " usuario.id_tipo as idTipo, " +
    " usuario.id_dependencia as idDependencia, " +    
    " usuario.id_coordinacion as idCoordinacionEstatal, " +
    " usuario.id_pais as idPais " +
     " from  usuario usuario " )
    List<Usuario> getAll();
    
    @SelectProvider(type=UsuarioDynamic.class, method="selectByFiltro")
    @Results ({
        @Result (column="id_usuario", property="idUsuario"),
        @Result (column="nombre", property="nombre"),
        @Result (column="apellido_paterno", property="apellidoPaterno"),
        @Result (column="apellido_materno", property="apellidoMaterno"),
        @Result (column="id_domicilio", property="idDomicilio"),
        @Result (column="id_tipo", property="idTipo"),
        @Result (column="id_dependencia", property="idDependencia"),        
        @Result (column="id_coordinacion", property="idCoordinacionEstatal"),
        @Result (column="id_pais", property="idPais"),            
        @Result (column="ID_ENTIDAD_FEDERATIVA", property="coordinacionEstatal.entidadFederativa.idEntidadFederativa"),
        @Result (column="entidad_federativa", property="coordinacionEstatal.entidadFederativa.nombre"),
        @Result (column="coordinacion", property="coordinacionEstatal.nombre")
    })             
    List<Usuario> selectByFiltro(CoordinacionEstatal coordinacionEstatal);
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃ©todo elimina un registro en  
     * la tabla usuario
     * mediante la llave primaria 	
     */
    @Delete(" delete from usuario  where " +
    
    " id_usuario = #{idUsuario, jdbcType=BIGINT}")
    int delete(Usuario pUsuario);
	
	
	/**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃ©todo actualiza un registro en  
     * la tabla usuario
     * mediante la llave primaria 	
     */
    @Update(" UPDATE usuario  SET " +
    " nombre = #{nombre, jdbcType=VARCHAR}, " +
    " apellido_paterno = #{apellidoPaterno, jdbcType=VARCHAR}, " +
    " apellido_materno = #{apellidoMaterno, jdbcType=VARCHAR}, " +
    " id_domicilio = #{idDomicilio, jdbcType=BIGINT}, " +
    " id_tipo = #{idTipo, jdbcType=SMALLINT}, " +
    " id_dependencia = #{idDependencia, jdbcType=SMALLINT}, " +    
    " id_coordinacion = #{idCoordinacionEstatal, jdbcType=VARCHAR}, " +
    " id_pais = #{idPais, jdbcType=BIGINT} " +
     " where " +
    
    " id_usuario = #{idUsuario}")
    int update(Usuario pUsuario);
	
	
	@SelectProvider(type=UsuarioDynamic.class, method="selectUsuarioSql")
	List<Usuario> selectByExample(Usuario pUsuario);
	
	
	int deleteByExample(Usuario pUsuario);
	
    }
  