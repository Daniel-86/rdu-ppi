package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.*;

public interface RduUsuariosService {
	public Usuario verificarUsuarioByLogin(String usrname) throws Exception;
        List<Usuario> selectByFiltro(CoordinacionEstatal coordinacionEstatal) throws Exception;
        Persona getSolicitanteById(Integer id);
        int insertSolicitante(Persona solicitante);
        int deleteSolicitante(Integer idSolicitante);
        int updateSolicitante(Persona solicitante);
        
     
        List<Persona> selectDynamic(Persona solicitante);
        
}
