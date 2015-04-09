
  package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.CoordinacionEstatal;
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
   * interface CoordinacionEstatalMapper que contiene los metodos de 
   * persistencia y consulta de la entidad coordinacion_estatal
   */
   
   public interface CoordinacionEstatalMapper  { 

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo inserta un registro en  
     * la tabla coordinacion_estatal
     *
     */

	
    @Insert("insert into coordinacion_estatal (  " +
    " id_coordinacion, " +
    " nombre, " +
    " id_entidad_federativa, " +
    " id_pais) " +
     " values (" +
    
    "#{idCoordinacion, jdbcType=VARCHAR }," +
    "#{nombre, jdbcType=VARCHAR }," +
    "#{idEntidadFederativa, jdbcType=SMALLINT }," +
    "#{idPais, jdbcType=BIGINT })")
    int insert(CoordinacionEstatal pCoordinacionEstatal);
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un registro en  
     * la tabla coordinacion_estatal
     * mediante la llave primaria 	
     */
    @Select("select " +
    " id_coordinacion as idCoordinacion, " +
    " nombre as nombre, " +
    " id_entidad_federativa as idEntidadFederativa, " +
    " id_pais as idPais " +
     " from  coordinacion_estatal where " +
    
    
    " id_coordinacion = #{idCoordinacion}")
    CoordinacionEstatal getById(CoordinacionEstatal pCoordinacionEstatal);

    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona todos los registros en  
     * la tabla coordinacion_estatal
     */
    @Select("select " +
    " coordinacionEstatal.id_coordinacion as idCoordinacion, " +
    " coordinacionEstatal.nombre as nombre, " +
    " coordinacionEstatal.id_entidad_federativa as idEntidadFederativa, " +
    " coordinacionEstatal.id_pais as idPais " +
     " from  coordinacion_estatal coordinacionEstatal " )
    List<CoordinacionEstatal> getAll();
	
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo elimina un registro en  
     * la tabla coordinacion_estatal
     * mediante la llave primaria 	
     */
    @Delete(" delete from coordinacion_estatal  where " +
    
    " id_coordinacion = #{idCoordinacion, jdbcType=VARCHAR}")
    int delete(CoordinacionEstatal pCoordinacionEstatal);
	
	
	/**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo actualiza un registro en  
     * la tabla coordinacion_estatal
     * mediante la llave primaria 	
     */
    @Update(" UPDATE coordinacion_estatal  SET " +
    " nombre = #{nombre, jdbcType=VARCHAR}, " +
    " id_entidad_federativa = #{idEntidadFederativa, jdbcType=SMALLINT}, " +
    " id_pais = #{idPais, jdbcType=BIGINT} " +
     " where " +
    
    " id_coordinacion = #{idCoordinacion}")
    int update(CoordinacionEstatal pCoordinacionEstatal);
	
	
	@SelectProvider(type=CoordinacionEstatalDynamic.class, method="selectCoordinacionEstatalSql")
	List<CoordinacionEstatal> selectByExample(CoordinacionEstatal pCoordinacionEstatal);
	
	
	int deleteByExample(CoordinacionEstatal pCoordinacionEstatal);
	
    }
  