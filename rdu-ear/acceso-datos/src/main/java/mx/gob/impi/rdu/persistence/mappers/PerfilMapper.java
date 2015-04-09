package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.Perfil;
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
 * interface PerfilMapper que contiene los metodos de persistencia y consulta de
 * la entidad perfil
 */
public interface PerfilMapper
{

    /**
     * Este codigo se genero con Arquitecto MVC. Este mÃƒÂ©todo inserta un
     * registro en la tabla perfil
     *
     */
    @Insert("insert into perfil (  "
    + " id_perfil, "
    + " nombre, "
    + " id_tipo_agrupacion, "
    + " constante) "
    + " values ("
    + "#{idPerfil, jdbcType=VARCHAR },"
    + "#{nombre, jdbcType=VARCHAR },"
    + "#{idTipoAgrupacion, jdbcType=SMALLINT  },"
    + "#{constante, jdbcType=VARCHAR })")
    int insert(Perfil pPerfil);

    @Select("select "
    + " id_perfil as idPerfil, "
    + " nombre as nombre, "
    + " id_tipo_agrupacion as idTipoAgrupacion, "
    + " constante as constante "
    + " from  perfil where "
    + " id_perfil = #{idPerfil}")
    Perfil getById(Perfil pPerfil);

    List<Perfil> getAll();

    @Select("select  "
    + " perfil.id_perfil as idPerfil,  "
    + " perfil.nombre as nombre,  "
    + " perfil.id_tipo_agrupacion as idTipoAgrupacion ,  "
    + " perfil.constante as constante "
    + " from  perfil perfil  where id_tipo_agrupacion = #{idTipoAgrupacion} "
    + " order by perfil.nombre ")
    List<Perfil> getByAgrupacion(Perfil pPerfil);

    
    @Delete(" delete from perfil  where "
    + " id_perfil = #{idPerfil, jdbcType=VARCHAR}")
    int delete(Perfil pPerfil);

    @Update(" UPDATE perfil  SET "
    + " nombre = #{nombre, jdbcType=VARCHAR} "
    + " where "
    + " id_perfil = #{idPerfil}")
    int update(Perfil pPerfil);

    @SelectProvider(type = PerfilDynamic.class, method = "selectPerfilSql")
    List<Perfil> selectByExample(Perfil pPerfil);

    int deleteByExample(Perfil pPerfil);
}
