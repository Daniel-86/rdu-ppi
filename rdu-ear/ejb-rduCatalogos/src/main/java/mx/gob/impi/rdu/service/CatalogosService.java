package mx.gob.impi.rdu.service;

import java.util.List;
import java.util.Map;
import mx.gob.impi.rdu.dto.*;
import mx.gob.impi.rdu.persistence.model.*;

public interface CatalogosService {

    List<EntidadFederativa> ConsultarEntidadesFederativas();

    List<CoordinacionEstatal> ConsultarCoordinacionesEstatales(CoordinacionEstatal coordinacionEstatal);

    List<CatTipoanexo> consultarTiposAnexos(CatTipoanexo tipoanexo);

    List<AnexosViewDto> consultarTiposAnexosDTO(AnexosViewDto tipoanexoDto);

    List<AnexosViewDto> consultarTiposAnexosDTOSimple(AnexosViewDto tipoanexoDato);

    List<AnexosViewDto> consultarTiposAnexosDTOSimplePatente(AnexosViewDto tipoanexoDto);

    List<DocumentoArticulo> consultarDocumentoArticulo(DocumentoArticulo documento);

    List<TipoDocumento> consultarTipoDocumento(TipoDocumento tipoDocumento);

    List<TipoUsuario> ConsultarTiposUsuarios();

    List<CatSubtiposolicitud> getByCriterio(CatSubtiposolicitud criterio);

    List<Pais> consultarPaises(Pais pais);
    
    List<CatAnexos> consultarAnexos();

    List<CatAnexos> consultarAnexosApoderado();

    List<CatTipopersona> consultarTiposPersona(CatTipopersona catTipopersona);

    List<CatArea> getAllAreas();

    List<CatEstatusCertificado> loadAllEstatusCertificado();

    CatEstatus getEstusById(Long id);

    List<CatFirmahorarios> getHorariosFirma(CatFirmahorarios horario);

    int insertarHorariosFirma(CatFirmahorarios horario);

    int eliminarHorariosFirma(int idHorario);

    List<CatFirmahorarios> selectHorariosFirma(CatFirmahorarios horario);

    CatSubtiposolicitud consultarSubtipoSolById(Long id);

    CatTiposolicitud consultarTipoSolById(Long id);

    tipoSolicitudDto areaTipoSolicitud(int idSubtipoSolicitud);

    List<CatTipoSolicitante> getTipoSolicitanteByCriterio(CatTipoSolicitante criterio);

    List<CatTiposolicitud> getTiposSolicitudesByCriterio(CatTiposolicitud criterio);

    List<AnexosViewDto> consultarTiposAnexosDTOPatente(AnexosViewDto tipoanexoDto);

    List<CatCapitulos> getAllCapitulos();

    List<Pais> consultarNacionalidades(Pais pais);
    AnexosViewDto selectAnexoDynamic(AnexosViewDto tipoAnexoDto);

    Map<String,Object> consultarIdiomas();
}
