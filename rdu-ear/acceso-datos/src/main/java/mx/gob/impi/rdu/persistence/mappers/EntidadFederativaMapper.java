package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.EntidadFederativa;
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
 * interface EntidadFederativaMapper que contiene los metodos de
 * persistencia y consulta de la entidad entidad_federativa
 */
public interface EntidadFederativaMapper {

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo inserta un registro en  
     * la tabla entidad_federativa
     *
     */
    @Insert("insert into entidad_federativa (  "
    + " id_entidad_federativa, "
    + " nombre, "
    + " id_pais, "
    + " id_zona_horaria, "
    + " id_horario_verano) "
    + " values ("
    + "#{idEntidadFederativa, jdbcType=SMALLINT },"
    + "#{nombre, jdbcType=VARCHAR },"
    + "#{idPais, jdbcType=BIGINT },"
    + "#{idZonaHoraria, jdbcType=SMALLINT },"
    + "#{idHorarioVerano, jdbcType=SMALLINT })")
    int insert(EntidadFederativa pEntidadFederativa);

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un registro en  
     * la tabla entidad_federativa
     * mediante la llave primaria 	
     */
    @Select("select "
    + " id_entidad_federativa as idEntidadFederativa, "
    + " nombre as nombre, "
    + " id_pais as idPais, "
    + " id_zona_horaria as idZonaHoraria, "
    + " id_horario_verano as idHorarioVerano "
    + " from  entidad_federativa where "
    + " id_entidad_federativa = #{idEntidadFederativa} AND "
    + " id_pais = #{idPais}")
    EntidadFederativa getById(EntidadFederativa pEntidadFederativa);

    /*
    @Select(" select entidad.id_entidad_federativa as IdEntidadFederativa, " +
    " entidad.nombre as nombre, entidad.id_pais as idPais " +
    " from entidad_federativa entidad " +
    " inner join region_entidad region on entidad.id_entidad_federativa = region.id_entidad_federativa " +
    "  where region.id_region = #{idRegion} order by entidad.nombre " )
    List<EntidadFederativa> getEntidadPorRegion(ReportePlantillaDto pEntidadFederativa);
     *
     */
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona todos los registros en  
     * la tabla entidad_federativa
     */
    @Select("select "
    + " entidadFederativa.id_entidad_federativa as idEntidadFederativa, "
    + " entidadFederativa.nombre as nombre, "
    + " entidadFederativa.id_pais as idPais "
    + " from  entidad_federativa entidadFederativa order by ( entidadFederativa.nombre )")
    List<EntidadFederativa> getAll();

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo elimina un registro en  
     * la tabla entidad_federativa
     * mediante la llave primaria 	
     */
    @Delete(" delete from entidad_federativa  where "
    + " id_entidad_federativa = #{idEntidadFederativa, jdbcType=SMALLINT} AND "
    + " id_pais = #{idPais, jdbcType=BIGINT}")
    int delete(EntidadFederativa pEntidadFederativa);

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo actualiza un registro en  
     * la tabla entidad_federativa
     * mediante la llave primaria 	
     */
    @Update(" UPDATE entidad_federativa  SET "
    + " nombre = #{nombre, jdbcType=VARCHAR} "
    + " where "
    + " id_entidad_federativa = #{idEntidadFederativa} AND "
    + " id_pais = #{idPais}")
    int update(EntidadFederativa pEntidadFederativa);

    @SelectProvider(type = EntidadFederativaDynamic.class, method = "selectEntidadFederativaSql")
    List<EntidadFederativa> selectByExample(EntidadFederativa pEntidadFederativa);

    int deleteByExample(EntidadFederativa pEntidadFederativa);
}
