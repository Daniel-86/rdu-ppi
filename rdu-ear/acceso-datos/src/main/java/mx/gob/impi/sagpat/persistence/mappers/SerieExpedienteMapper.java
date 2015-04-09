package mx.gob.impi.sagpat.persistence.mappers;



import mx.gob.impi.sagpat.persistence.model.SerieExpediente;
import org.apache.ibatis.annotations.Param;

public interface SerieExpedienteMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    SerieExpediente selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(SerieExpediente record);

    int updateByPrimaryKey(SerieExpediente record);
    
    int generaNumeroExpediente(SerieExpediente record);
    
    String seleccionaNumeroExpediente(SerieExpediente record);
    
    
    
}