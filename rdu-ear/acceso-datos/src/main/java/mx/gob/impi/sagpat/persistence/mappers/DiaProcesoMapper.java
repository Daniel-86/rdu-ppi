package mx.gob.impi.sagpat.persistence.mappers;



import java.util.Date;
import mx.gob.impi.sagpat.persistence.model.DiaProceso;
import org.apache.ibatis.annotations.Param;


public interface DiaProcesoMapper {
 int deleteByPrimaryKey(Date fecProceso);

DiaProceso selectByPrimaryKey(Date fecProceso);
    
    //String obtenerSerieDocExp(Date fecProceso);
//String obtenerSerieDocExp(@Param("diaHabil") Date diaHabil, @Param("tipoEx") String tipoEx);

String obtenerSerieDocExp(DiaProceso record);


int updateByPrimaryKeySelective(DiaProceso record);

int updateByPrimaryKey(DiaProceso record);

}