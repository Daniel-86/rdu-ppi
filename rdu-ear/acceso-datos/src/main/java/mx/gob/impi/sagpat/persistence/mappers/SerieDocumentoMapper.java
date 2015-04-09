package mx.gob.impi.sagpat.persistence.mappers;

import mx.gob.impi.sagpat.persistence.model.SerieDocumento;





public interface SerieDocumentoMapper {
    int deleteByPrimaryKey(Short serDocum);

    SerieDocumento selectByPrimaryKey(Short serDocum);

    int updateByPrimaryKeySelective(SerieDocumento record);

    int updateByPrimaryKey(SerieDocumento record);
}