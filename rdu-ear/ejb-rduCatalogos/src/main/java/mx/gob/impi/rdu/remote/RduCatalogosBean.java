package mx.gob.impi.rdu.remote;

import mx.gob.impi.rdu.persistence.model.*;
import java.util.List;
import java.util.Map;

import mx.gob.impi.rdu.dto.*;

public interface RduCatalogosBean {

    List<EntidadFederativa> ConsultarEntidadesFederativas();

    List<CoordinacionEstatal> ConsultarCoordinacionesEstatales(CoordinacionEstatal coordinacionEstatal);

    List<CatTipoanexo> consultarTiposAnexos(CatTipoanexo tipoanexo);

    List<AnexosViewDto> consultarTiposAnexosDTO(AnexosViewDto tipoanexoDto);

    List<AnexosViewDto> consultarTiposAnexosDTOSimple(AnexosViewDto tipoanexoDto);

    List<AnexosViewDto> consultarTiposAnexosDTOSimplePatente(AnexosViewDto tipoanexoDto);

    List<DocumentoArticulo> consultarDocumentoArticulo(DocumentoArticulo documento);

    List<TipoDocumento> consultarTipoDocumento(TipoDocumento tipoDocumento);

    List<Pais> consultarPaises(Pais pais);
    
    List<CatAnexos> consultarAnexos();
    
    List<CatAnexos> consultarAnexosApoderado();

    List<TipoUsuario> ConsultarTiposUsuarios();

    List<CatEstatusCertificadoDto> loadAllEstatusCertificado();

    List<CatAreaDto> getAllAreas();

    List<CatSubtiposolicitud> getByCriterio(CatSubtiposolicitud criterio);

    List<CatTipopersona> consultarTiposPersona(CatTipopersona catTipopersona);

    List<CatFirmahorarios> getHorariosFirma(CatFirmahorarios horario);

    int insertarHorariosFirma(CatFirmahorarios horario);

    int eliminarHorariosFirma(int idHorario);

    List<CatFirmahorarios> selectHorariosFirma(CatFirmahorarios horario);

    CatEstatusDto getEstusById(Long id);

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
