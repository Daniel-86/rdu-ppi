package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.dto.AnexosViewDto;
import mx.gob.impi.rdu.persistence.model.Anexos;

/**
 *
 * @author JBMM
 */
public interface RduAnexosService {
    public int insertarAnexos(Anexos anexo);
    
    public List<Anexos> getAnexosByTramite(Long idTramite);
    public int insertarAnexosDto(List<AnexosViewDto> anexosDto);
    public int insertarAnexosPatente(List<AnexosViewDto> anexosDto);

    int insertarAnexosPago(Anexos anexo);
    
    int deleteAnexosByIds(Long idAnexo);
    int deleteByTypeAnexo(Anexos anexo);

    public Anexos obtenerAnexosDynamic(Anexos anexo);
}