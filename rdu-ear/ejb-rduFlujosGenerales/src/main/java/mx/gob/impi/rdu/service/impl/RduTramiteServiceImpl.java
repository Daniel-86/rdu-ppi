package mx.gob.impi.rdu.service.impl;

import mx.gob.impi.rdu.persistence.mappers.TramiteMapper;
import mx.gob.impi.rdu.persistence.model.Tramite;
import mx.gob.impi.rdu.service.RduTramiteService;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudMapper;
import mx.gob.impi.sagpat.persistence.model.Solicitud;
import org.apache.log4j.Logger;

public class RduTramiteServiceImpl implements RduTramiteService{
    private Logger lger = Logger.getLogger(this.getClass());
    private TramiteMapper rduTramiteMapper;

    private SolicitudMapper solicitudMapper;
    
    public void setRduTramiteMapper(TramiteMapper rduTramiteMapper) {
        this.rduTramiteMapper = rduTramiteMapper;
    }

    public void setSolicitudMapper(SolicitudMapper solicitudMapper) {
        this.solicitudMapper = solicitudMapper;
    }
    
    
    
    public int cargarSolicitudes(Tramite idTramite) {
        System.out.println("************ entra RduTableroServiceImpl.copiarSolicitud:");
        
        return rduTramiteMapper.updateByPrimaryKeySelective(idTramite);
    }

    public int actualizarSolicitud(Tramite prmTramite) {
        System.out.println("************ entra RduTableroServiceImpl.actualizarSolicitud:");

        return rduTramiteMapper.updateByPrimaryKeySelective(prmTramite);
    }

    public Tramite obtenerSolicitudes(int idTramite) {
        System.out.println("************ entra RduTableroServiceImpl.actualizarSolicitud:");

        return rduTramiteMapper.selectByPrimaryKey(Long.valueOf(idTramite));
    }

    public Solicitud selectByExpedienteDivisional(String codOficina, Integer numExped, Integer serExped, String tipExped) {
        return solicitudMapper.selectByExpedienteDivisional(codOficina, numExped, serExped, tipExped);
    }
    
    

}