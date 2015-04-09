package mx.gob.impi.sagpat.persistence.mappers;

import mx.gob.impi.sagpat.persistence.model.Documento;
import mx.gob.impi.sagpat.persistence.model.Solicitante;
import mx.gob.impi.sagpat.persistence.model.TipoOficio;
import mx.gob.impi.sagpat.persistence.model.TipoPromocion;
import mx.gob.impi.sagpat.persistence.model.TramiteOficio;

public interface PromocionesMapper {

    TramiteOficio buscarOficioAResponder(TramiteOficio tramiteOficio);

    TipoOficio consultarValoresPromocion(String tipoOficio);

    TipoPromocion consultarTipoPromocion(String tipoPromocion);

    TramiteOficio consultarNumSolicitud(TramiteOficio tramiteOficio);

    Double consultarNumConcesion(TramiteOficio tramiteOficio);

    Solicitante consultarTipoSolicitante(TramiteOficio tramiteOficio);

    Documento consultarDocumento(TramiteOficio tramiteOficio);

    Integer consultarNotificacion(TramiteOficio tramiteOficio);

    TramiteOficio ejecutarConsultaJerarquica(TramiteOficio tramiteOficio);
    
    TramiteOficio consultarExisteSolicitud(TramiteOficio tramiteOficio);
    
}