package mx.gob.impi.rdu.remote.impl;

import java.util.ArrayList;
import mx.gob.impi.rdu.persistence.model.*;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import mx.gob.impi.rdu.dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.impi.rdu.remote.RduCatalogosBeanLocal;
import mx.gob.impi.rdu.remote.RduCatalogosBeanRemote;
import mx.gob.impi.rdu.service.CatalogosService;
import mx.gob.impi.rdu.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

@Stateless(name = "EJBRduCatalogosBean", mappedName = "EJBRduCatalogosBean", description = "")
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class RduCatalogosBeanImpl implements RduCatalogosBeanRemote, RduCatalogosBeanLocal {

    private Logger lger = Logger.getLogger(this.getClass());
    @Autowired
    @Qualifier("rduCatalogosService")
    private CatalogosService rduCatalogosService;

    /**
     *  Default constructor
     */
    public RduCatalogosBeanImpl() {
    }

    public void setRduCatalogosService(CatalogosService rduCatalogosService) {
        this.rduCatalogosService = rduCatalogosService;
    }

    public List<EntidadFederativa> ConsultarEntidadesFederativas() {
        return this.rduCatalogosService.ConsultarEntidadesFederativas();
    }

    public List<CoordinacionEstatal> ConsultarCoordinacionesEstatales(CoordinacionEstatal coordinacionEstatal) {
        return this.rduCatalogosService.ConsultarCoordinacionesEstatales(coordinacionEstatal);
    }

    public List<CatTipoanexo> consultarTiposAnexos(CatTipoanexo tipoanexo) {
        return this.rduCatalogosService.consultarTiposAnexos(tipoanexo);
    }

    public List<Pais> consultarPaises(Pais pais) {
        return this.rduCatalogosService.consultarPaises(pais);
    }
    
    public List<CatAnexos> consultarAnexos() {
        return this.rduCatalogosService.consultarAnexos();
    }
    
    public List<CatAnexos> consultarAnexosApoderado() {
        return this.rduCatalogosService.consultarAnexosApoderado();
    }
    
    public List<Pais> consultarNacionalidades(Pais pais) {
        return this.rduCatalogosService.consultarNacionalidades(pais);
    }

    public List<CatSubtiposolicitud> getByCriterio(CatSubtiposolicitud criterio) {
        return this.rduCatalogosService.getByCriterio(criterio);
    }

    public List<AnexosViewDto> consultarTiposAnexosDTO(AnexosViewDto tipoanexoDto) {
        return this.rduCatalogosService.consultarTiposAnexosDTO(tipoanexoDto);
    }

    public List<AnexosViewDto> consultarTiposAnexosDTOSimple(AnexosViewDto tipoanexoDto) {
        return this.rduCatalogosService.consultarTiposAnexosDTOSimple(tipoanexoDto);
    }

     public List<AnexosViewDto> consultarTiposAnexosDTOSimplePatente(AnexosViewDto tipoanexoDto){
return this.rduCatalogosService.consultarTiposAnexosDTOSimplePatente(tipoanexoDto);
     }
    public List<TipoUsuario> ConsultarTiposUsuarios() {
        return this.rduCatalogosService.ConsultarTiposUsuarios();
    }

    public List<DocumentoArticulo> consultarDocumentoArticulo(DocumentoArticulo documento) {
        return this.rduCatalogosService.consultarDocumentoArticulo(documento);
    }

    public List<TipoDocumento> consultarTipoDocumento(TipoDocumento tipoDocumento) {
        return this.rduCatalogosService.consultarTipoDocumento(tipoDocumento);
    }

    public List<CatTipopersona> consultarTiposPersona(CatTipopersona catTipopersona) {
        return this.rduCatalogosService.consultarTiposPersona(catTipopersona);
    }

    public List<CatFirmahorarios> getHorariosFirma(CatFirmahorarios horario) {
        return this.rduCatalogosService.getHorariosFirma(horario);
    }

    public CatSubtiposolicitud consultarSubtipoSolById(Long id) {
        return this.rduCatalogosService.consultarSubtipoSolById(id);
    }

    public CatTiposolicitud consultarTipoSolById(Long id) {
        return this.rduCatalogosService.consultarTipoSolById(id);
    }

    public CatEstatusDto getEstusById(Long id) {
        //            this.rduCatalogosService.

        return null;

    }

    public List<CatAreaDto> getAllAreas() {
        List<CatAreaDto> allAreas = new ArrayList<CatAreaDto>();
        CatAreaDto areaDto;
        for (CatArea area : Util.checkListNull(this.rduCatalogosService.getAllAreas())) {
            areaDto = new CatAreaDto();
            BeanUtils.copyProperties(area, areaDto);
            allAreas.add(areaDto);
        }
        return allAreas;
    }

    public List<CatEstatusCertificadoDto> loadAllEstatusCertificado() {
        List<CatEstatusCertificadoDto> allEstatusDto = new ArrayList<CatEstatusCertificadoDto>();
        CatEstatusCertificadoDto estatusDto;
        for (CatEstatusCertificado estatus : Util.checkListNull(this.rduCatalogosService.loadAllEstatusCertificado())) {
            estatusDto = new CatEstatusCertificadoDto();
            BeanUtils.copyProperties(estatus, estatusDto);
            allEstatusDto.add(estatusDto);
        }
        return allEstatusDto;

    }

    public int insertarHorariosFirma(CatFirmahorarios horario) {
        int horariosInsertados = 0;
        try {
            horariosInsertados = this.rduCatalogosService.insertarHorariosFirma(horario);
        } catch (Exception e) {
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.copiarSolicitud: ", e);
        }
        return horariosInsertados;
    }

    public int eliminarHorariosFirma(int idHorario) {
        int horariosEliminados = 0;
        try {
            horariosEliminados = this.rduCatalogosService.eliminarHorariosFirma(idHorario);
        } catch (Exception e) {
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.copiarSolicitud: ", e);
        }
        return horariosEliminados;
    }

    public List<CatFirmahorarios> selectHorariosFirma(CatFirmahorarios horario) {
        List<CatFirmahorarios> listaHorarios = new ArrayList();
        try {
            listaHorarios = this.rduCatalogosService.selectHorariosFirma(horario);
        } catch (Exception e) {
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.copiarSolicitud: ", e);
        }
        return listaHorarios;
    }

    public tipoSolicitudDto areaTipoSolicitud(int idSubtipoSolicitud) {
        return this.rduCatalogosService.areaTipoSolicitud(idSubtipoSolicitud);
    }

    public List<CatTipoSolicitante> getTipoSolicitanteByCriterio(CatTipoSolicitante criterio) {
        return this.rduCatalogosService.getTipoSolicitanteByCriterio(criterio);

    }

    public List<CatTiposolicitud> getTiposSolicitudesByCriterio(CatTiposolicitud criterio) {
        return this.rduCatalogosService.getTiposSolicitudesByCriterio(criterio);

    }

    public List<AnexosViewDto> consultarTiposAnexosDTOPatente(AnexosViewDto tipoanexoDto) {
        return this.rduCatalogosService.consultarTiposAnexosDTOPatente(tipoanexoDto);
    }

    public List<CatCapitulos> getAllCapitulos() {
        return this.rduCatalogosService.getAllCapitulos();
    }

    public AnexosViewDto selectAnexoDynamic(AnexosViewDto tipoAnexoDto) {
        AnexosViewDto anexosViewDto = new AnexosViewDto();
        try {
            anexosViewDto = this.rduCatalogosService.selectAnexoDynamic(tipoAnexoDto);            
        }  catch (Exception e) {
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.selectAnexoDynamic: ", e);
        }
        return anexosViewDto;
    }

    public Map<String,Object> consultarIdiomas() {
        return this.rduCatalogosService.consultarIdiomas();
    }
}
