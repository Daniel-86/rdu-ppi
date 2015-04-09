package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.DocumentoExpediente;
import org.apache.ibatis.annotations.Param;

public interface DocumentoExpedienteMapper {
    int deleteByPrimaryKey(@Param("codOficinaDocum") String codOficinaDocum, @Param("codOficinaExped") String codOficinaExped, @Param("numDocum") Integer numDocum, @Param("numExped") Integer numExped, @Param("serDocum") Short serDocum, @Param("serExped") Short serExped, @Param("tipExped") String tipExped, @Param("tipLibro") String tipLibro);

    int insert(DocumentoExpediente record);

    int insertSelective(DocumentoExpediente record);

    DocumentoExpediente selectByPrimaryKey(@Param("codOficinaDocum") String codOficinaDocum, @Param("codOficinaExped") String codOficinaExped, @Param("numDocum") Integer numDocum, @Param("numExped") Integer numExped, @Param("serDocum") Short serDocum, @Param("serExped") Short serExped, @Param("tipExped") String tipExped, @Param("tipLibro") String tipLibro);

    int updateByPrimaryKeySelective(DocumentoExpediente record);

    int updateByPrimaryKey(DocumentoExpediente record);
}