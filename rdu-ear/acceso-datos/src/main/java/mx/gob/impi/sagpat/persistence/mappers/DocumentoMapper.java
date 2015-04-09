package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.Documento;
import mx.gob.impi.sagpat.persistence.model.LibroPartes;
import org.apache.ibatis.annotations.Param;

public interface DocumentoMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("numDocum") Integer numDocum, @Param("serDocum") Short serDocum, @Param("tipLibro") String tipLibro);

    int insert(Documento record);
String  selectMinNumDocum(Documento record);
    
    String  selectMaxNumDocum(Documento record);
    
    
    int insertSelective(Documento record);

    Documento selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("numDocum") Integer numDocum, @Param("serDocum") Short serDocum, @Param("tipLibro") String tipLibro);

    int updateByPrimaryKeySelective(Documento record);

    int updateByPrimaryKey(Documento record);
}