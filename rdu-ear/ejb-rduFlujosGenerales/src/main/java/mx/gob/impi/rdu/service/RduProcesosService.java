package mx.gob.impi.rdu.service;

import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;

/**
 *
 * @author JBMM
 */
public interface RduProcesosService {

    public int eliminarSolicitud(SolicitudPreparacionDto solicitudPreparacionDto);
    
    public int eliminarPromocion(SolicitudPreparacionDto solicitudPreparacionDto);

    public String jobeliminarAcuse();

    public int selectsecuencia();
}
