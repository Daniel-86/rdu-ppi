package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.UsuarioSeguridad;
import java.util.List;
import mx.gob.impi.rdu.dto.ConteoUsuarioDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.SelectProvider;
  
  /**
   *
   * interface UsuarioSeguridadMapper que contiene los metodos de 
   * persistencia y consulta de la entidad usuario_seguridad
   */
   
   public interface UsuarioSeguridadMapper  { 

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo inserta un registro en  
     * la tabla usuario_seguridad
     *
     */

 
    @Insert("insert into usuario_seguridad (  " +
    " usuario, " +
    " clave, " +
    " correo, " +
    " fecha_creacion, " +
    " cuenta_no_expirada, " +
    " cuenta_no_bloqueada, " +
    " credencial_no_expirada, " +
    " habilitado, " +
    " intentos_fallidos, " +
    " instante_bloqueo, " +
    " respuesta_secreta, " +
    " id_seguridad, " +
    " ventana_id_seguridad, " +
    " id_usuario, " +
    " id_pregunta_secreta, " +
    " uuid_token) " +
     " values (" +
    
    "#{usuarioStr, jdbcType=VARCHAR }," +
    "#{clave, jdbcType=VARCHAR }," +
    "#{correo, jdbcType=VARCHAR }," +
    "#{fechaCreacion, jdbcType=TIMESTAMP }," +
    "#{cuentaNoExpirada, jdbcType=BIT }," +
    "#{cuentaNoBloqueda, jdbcType=BIT }," +
    "#{credencialNoExpirada, jdbcType=BIT }," +
    "#{habilitado, jdbcType=BIT }," +
    "#{intentosFallidos, jdbcType=SMALLINT }," +
    "#{instanteBloqueo, jdbcType=TIMESTAMP }," +
    "#{respuestaSecreta, jdbcType=VARCHAR }," +
    "#{idSeguridad, jdbcType=VARCHAR }," +
    "#{ventanaIdSeguridad, jdbcType=TIMESTAMP }," +
    "#{idUsuario, jdbcType=BIGINT }," +
    "#{idPreguntaSecreta, jdbcType=INTEGER }, " +
    "#{token, jdbcType=VARCHAR } )")
    int insert(UsuarioSeguridad pUsuarioSeguridad);
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un registro en  
     * la tabla usuario_seguridad
     * mediante la llave primaria  
     */
    @Select("select " +
    " usuario as usuarioStr, " +
    " clave as clave, " +
    " correo as correo, " +
    " fecha_creacion as fechaCreacion, " +
    " cuenta_no_expirada as cuentaNoExpirada, " +
    " cuenta_no_bloqueada as cuentaNoBloqueda, " +
    " credencial_no_expirada as credencialNoExpirada, " +
    " habilitado as habilitado, " +
    " intentos_fallidos as intentosFallidos, " +
    " instante_bloqueo as instanteBloqueo, " +
    " respuesta_secreta as respuestaSecreta, " +
    " id_seguridad as idSeguridad, " +
    " ventana_id_seguridad as ventanaIdSeguridad, " +
    " id_usuario as idUsuario, " +
    " id_pregunta_secreta as idPreguntaSecreta " +
     " from  usuario_seguridad where " +
    
    
    " id_usuario = #{idUsuario}")
    UsuarioSeguridad getById(UsuarioSeguridad pUsuarioSeguridad);

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona todos los registros en  
     * la tabla usuario_seguridad
     */
    @Select("select " +
    " usuarioSeguridad.usuario as usuario, " +
    " usuarioSeguridad.clave as clave, " +
    " usuarioSeguridad.correo as correo, " +
    " usuarioSeguridad.fecha_creacion as fechaCreacion, " +
    " usuarioSeguridad.cuenta_no_expirada as cuentaNoExpirada, " +
    " usuarioSeguridad.cuenta_no_bloqueada as cuentaNoBloqueda, " +
    " usuarioSeguridad.credencial_no_expirada as credencialNoExpirada, " +
    " usuarioSeguridad.habilitado as habilitado, " +
    " usuarioSeguridad.intentos_fallidos as intentosFallidos, " +
    " usuarioSeguridad.instante_bloqueo as instanteBloqueo, " +
    " usuarioSeguridad.respuesta_secreta as respuestaSecreta, " +
    " usuarioSeguridad.id_seguridad as idSeguridad, " +
    " usuarioSeguridad.ventana_id_seguridad as ventanaIdSeguridad, " +
    " usuarioSeguridad.id_usuario as idUsuario, " +
    " usuarioSeguridad.id_pregunta_secreta as idPreguntaSecreta " +
     " from  usuario_seguridad usuarioSeguridad " )
    List<UsuarioSeguridad> getAll();
 
    
     /** Verifica si ya existe el usuario */  
  @Select("select " +
    " usuario_seguridad.usuario as usuarioStr " +
    " from  usuario_seguridad where " +
    " usuario = #{usuarioStr}")
    UsuarioSeguridad getByUsuario(UsuarioSeguridad usuarioSeguridad);

  
       /** Verifica si ya existe el usuario */  
  @Select("select " +
    //" usuario_seguridad.usuario as usuario, " +
    " count(usuario_seguridad.usuario) as contador " +
    " from  usuario_seguridad where " +
    " usuario = #{usuarioStr} " +
    "group by usuario_seguridad.usuario ")
    ConteoUsuarioDto  getByUsuarioContador(UsuarioSeguridad usuarioSeguridad);

  
       /** Verifica si ya existe el usuario */  
  @Select("select " +
    " usuario_seguridad.usuario  as usuarioStr," +
    " usuario_seguridad.clave as clave, " +
    " usuario_seguridad.id_usuario as idUsuario " + 
    " from  usuario_seguridad where " +
    " usuario = #{usuarioStr} and" +
    " clave = #{clave}")
    UsuarioSeguridad getUsuario(UsuarioSeguridad usuarioSeguridad);

   /** Verifica si ya existe el usuario de acuerdo al username*/  
  @Select("select " +
    " usuario_seguridad.usuario  as usuarioStr," +
    " usuario_seguridad.clave as clave, " +
    " usuario_seguridad.id_usuario as idUsuario " + 
    " from  usuario_seguridad where " +
    " usuario = #{usuarioStr} ")
    UsuarioSeguridad getUsuarioByLogin(UsuarioSeguridad usuarioSeguridad);
  
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo elimina un registro en  
     * la tabla usuario_seguridad
     * mediante la llave primaria  
     */
    @Delete(" delete from usuario_seguridad  where " +
    
    " id_usuario = #{idUsuario, jdbcType=BIGINT}")
    int delete(UsuarioSeguridad pUsuarioSeguridad);
 
 
 /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo actualiza un registro en  
     * la tabla usuario_seguridad
     * mediante la llave primaria  
     */
    @Update(" UPDATE usuario_seguridad  SET " +
    " usuario = #{usuarioStr, jdbcType=VARCHAR}, " +
    " clave = #{clave, jdbcType=VARCHAR}, " +
    " correo = #{correo, jdbcType=VARCHAR}, " +
    " fecha_creacion = #{fechaCreacion, jdbcType=TIMESTAMP}, " +
    " cuenta_no_expirada = #{cuentaNoExpirada, jdbcType=BIT}, " +
    " cuenta_no_bloqueada = #{cuentaNoBloqueda, jdbcType=BIT}, " +
    " credencial_no_expirada = #{credencialNoExpirada, jdbcType=BIT}, " +
    " habilitado = #{habilitado, jdbcType=BIT}, " +
    " intentos_fallidos = #{intentosFallidos, jdbcType=SMALLINT}, " +
    " instante_bloqueo = #{instanteBloqueo, jdbcType=TIMESTAMP}, " +
    " respuesta_secreta = #{respuestaSecreta, jdbcType=VARCHAR}, " +
    " id_seguridad = #{idSeguridad, jdbcType=VARCHAR}, " +
    " ventana_id_seguridad = #{ventanaIdSeguridad, jdbcType=TIMESTAMP}, " +
    " id_pregunta_secreta = #{idPreguntaSecreta, jdbcType=INTEGER} " +
     " where " +
    
    " id_usuario = #{idUsuario}")
    int update(UsuarioSeguridad pUsuarioSeguridad);
 
 
 @SelectProvider(type=UsuarioSeguridadDynamic.class, method="selectUsuarioSeguridadSql")
 List<UsuarioSeguridad> selectByExample(UsuarioSeguridad pUsuarioSeguridad);
 
 
 int deleteByExample(UsuarioSeguridad pUsuarioSeguridad);

    @Select("select " +
    " COUNT(*) as valor " +
    " FROM USUARIO_SEGURIDAD " +
    " WHERE USUARIO = #{usuarioStr} " + 
    " AND UUID_TOKEN = #{token} ")
    int selectCountExistence(UsuarioSeguridad usuarioSeguridad);
    
    @Update(" UPDATE usuario_seguridad SET " +
    " habilitado = #{habilitado, jdbcType=BIT} " +
    " where usuario = #{usuarioStr}")
    void updateHabilitar(UsuarioSeguridad usuarioSeguridad);
}