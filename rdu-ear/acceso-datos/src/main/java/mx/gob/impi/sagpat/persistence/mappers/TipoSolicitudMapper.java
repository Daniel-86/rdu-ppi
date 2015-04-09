package mx.gob.impi.sagpat.persistence.mappers;

import mx.gob.impi.sagpat.persistence.model.TipoSolicitud;





public interface TipoSolicitudMapper {
    int deleteByPrimaryKey(String tipSolicitud);

    TipoSolicitud selectByPrimaryKey(String tipSolicitud);
    
   TipoSolicitud selectByTipExp(String tipSolicitud);

    int updateByPrimaryKeySelective(TipoSolicitud record);

    int updateByPrimaryKey(TipoSolicitud record);
}