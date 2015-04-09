package mx.gob.impi.rdu.service;

import mx.gob.impi.rdu.persistence.model.Tramite;
import mx.gob.impi.sagpat.persistence.model.Solicitud;


public interface RduTramiteService {
    public int cargarSolicitudes(Tramite idTramite);
    public int actualizarSolicitud(Tramite prmTramite);
    public Tramite  obtenerSolicitudes(int idTramite);
    public Solicitud selectByExpedienteDivisional( String codOficina, Integer numExped, Integer serExped,String tipExped);
}