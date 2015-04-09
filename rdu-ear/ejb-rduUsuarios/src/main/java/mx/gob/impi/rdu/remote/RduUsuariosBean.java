package mx.gob.impi.rdu.remote;


import java.util.List;
import mx.gob.impi.rdu.dto.PersonaDto;
import mx.gob.impi.rdu.persistence.model.CoordinacionEstatal;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.Usuario;

public interface RduUsuariosBean {
	public Usuario verificarUsuarioByLogin(String usrname) throws Exception;
        List<Usuario> selectByFiltro(CoordinacionEstatal coordinacionEstatal) throws Exception;
        Persona getSolicitanteById(Integer id);
        int insertSolicitante(Persona solicitante);
        int deleteSolicitante(Integer idSolicitante);
        int updateSolicitante(Persona solicitante);
      
        List<Persona> selectDynamic(Persona solicitante);
}
