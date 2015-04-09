/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import java.util.List;
import java.util.Map;
import mx.gob.impi.rdu.dto.*;
import mx.gob.impi.rdu.persistence.model.*;

/**
 *
 * @author usradmin
 */
public interface CatalogosViewService {

    List<EntidadFederativa> ConsultarEntidadesFederativas();

    List<CoordinacionEstatal> ConsultarCoordinacionesEstatales(CoordinacionEstatal coordinacionEstatal);

    List<CatSubtiposolicitud> getSubtiposSolByCriterio(CatSubtiposolicitud criterio);

    List<Pais> consultarPaises(Pais pais);

    List<Pais> consultarNacionalidades(Pais pais);

//    List<CatTipomarca> getMarcasByCriterio(CatTipomarca criterio);

//    List<CatFormaseleccionclase> getFormasSelClaseByCriterio(CatFormaseleccionclase criterio);

    List<CatTipopersona> consultarTiposPersona(CatTipopersona CatTipopersona);

    List<DocumentoArticulo> cosultarDocumentoArticulo(DocumentoArticulo documentoArticulo);

//    List<CatClase> consultarClasesActuales();

    CatFirmahorarios getHorariosFirma(CatFirmahorarios horario);

    List<CatFirmahorarios> selectHorarios(CatFirmahorarios horario);

    List<CatAreaDto> getAllAreas();

    List<CatEstatusCertificadoDto> getAllEstatusCertificado();

    int insertarHorariosFirma(CatFirmahorarios horario);

    int deleteHorarios(int idHorario);

//    List<CatProducto> consultarProductosXCriterios(CatProducto criterio);

    List<AnexosViewDto> CosultaAnexosDto(AnexosViewDto anexosViewDto);

    List<AnexosViewDto> CosultaAnexosDtoSimple(AnexosViewDto anexosViewDto);

    List<CatTipoSolicitante> consultarTiposSolicitanteXCriterio(CatTipoSolicitante criterio);

//    List<TipoDenominacionOrigen> loadAllDenominaciones();

    List<AnexosViewDto> ConsultaAnexosDtoPatentes(AnexosViewDto anexosViewDto);
    AnexosViewDto selectAnexoDynamic(AnexosViewDto tipoAnexoDto);

    Map<String,Object> consultarIdiomas();
}
