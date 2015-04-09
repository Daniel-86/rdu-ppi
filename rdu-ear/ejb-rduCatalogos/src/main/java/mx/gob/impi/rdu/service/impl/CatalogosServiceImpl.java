package mx.gob.impi.rdu.service.impl;

import java.util.ArrayList;
import mx.gob.impi.rdu.dto.*;
import mx.gob.impi.rdu.persistence.mappers.*;
import mx.gob.impi.rdu.persistence.model.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import mx.gob.impi.rdu.dto.*;
import org.apache.log4j.Logger;
import mx.gob.impi.rdu.service.CatalogosService;
import org.springframework.core.convert.converter.Converter;

public class CatalogosServiceImpl implements CatalogosService {

    private Logger log = Logger.getLogger(this.getClass());

    /*se puede utilizar las anotaciones @Autowired y @Qualifier,pero ya no son necesarias puesto que se agregaron
     como property en la declaracion del bean del CatalogosServiceImpl*/
    //@Autowired
    //@Qualifier("rduEntidadFederativaMapper")
    private EntidadFederativaMapper rduEntidadFederativaMapper;
    private CoordinacionEstatalMapper rduCoordinacionEstatalMapper;
    private TipoUsuarioMapper rduTipoUsuarioMapper;
    private CatSubtiposolicitudMapper rduCatSubtiposolicitudMapper;
    private PaisMapper rduPaisMapper;
    private CatAnexosMapper rduCatAnexosMapper;
    private DocumentoArticuloMapper rduDocumentoArticuloMapper;
    private TipoDocumentoMapper rduTipoDocumentoMapper;
    private CatTipoanexoMapper rduCatTipoanexoMapper;
    private CatTipopersonaMapper rduCatTipopersonaMapper;
    private CatTiposolicitudMapper rduCatTiposolicitudMapper;
    private CatFirmahorariosMapper rduCatFirmaHorariosMapper;
    private CatEstatusCertificadoMapper rduCatEstatusCertificadoMapper;
    private CatAreaMapper rduCatAreaMapper;
    private CatTipoSolicitanteMapper rduCatTipoSolicitanteMapper;
    private CatCapitulosMapper rduCatCapitulosMapper;
    private CatIdiomasMapper rduCatIdiomasMapper;

    public void setRduCatCapitulosMapper(CatCapitulosMapper rduCatCapitulosMapper) {
        this.rduCatCapitulosMapper = rduCatCapitulosMapper;
    }

    public void setRduCatTipoSolicitanteMapper(CatTipoSolicitanteMapper rduCatTipoSolicitanteMapper) {
        this.rduCatTipoSolicitanteMapper = rduCatTipoSolicitanteMapper;
    }

    public void setRduCatAreaMapper(CatAreaMapper rduCatAreaMapper) {
        this.rduCatAreaMapper = rduCatAreaMapper;
    }

    public void setRduCatEstatusCertificadoMapper(CatEstatusCertificadoMapper rduCatEstatusCertificadoMapper) {
        this.rduCatEstatusCertificadoMapper = rduCatEstatusCertificadoMapper;
    }

    public void setRduCatTiposolicitudMapper(CatTiposolicitudMapper rduCatTiposolicitudMapper) {
        this.rduCatTiposolicitudMapper = rduCatTiposolicitudMapper;
    }

    public void setRduCatTipopersonaMapper(CatTipopersonaMapper rduCatTipopersonaMapper) {
        this.rduCatTipopersonaMapper = rduCatTipopersonaMapper;
    }

    public void setRduCatTipoanexoMapper(CatTipoanexoMapper rduCatTipoanexoMapper) {
        this.rduCatTipoanexoMapper = rduCatTipoanexoMapper;
    }

    public void setRduDocumentoArticuloMapper(DocumentoArticuloMapper rduDocumentoArticuloMapper) {
        this.rduDocumentoArticuloMapper = rduDocumentoArticuloMapper;
    }

    public void setRduTipoDocumentoMapper(TipoDocumentoMapper rduTipoDocumentoMapper) {
        this.rduTipoDocumentoMapper = rduTipoDocumentoMapper;
    }

    public void setRduTipoUsuarioMapper(TipoUsuarioMapper rduTipoUsuarioMapper) {
        this.rduTipoUsuarioMapper = rduTipoUsuarioMapper;
    }

    public void setRduCoordinacionEstatalMapper(CoordinacionEstatalMapper rduCoordinacionEstatalMapper) {
        this.rduCoordinacionEstatalMapper = rduCoordinacionEstatalMapper;
    }

    public void setRduEntidadFederativaMapper(EntidadFederativaMapper rduEntidadFederativaMapper) {
        this.rduEntidadFederativaMapper = rduEntidadFederativaMapper;
    }

    public void setRduCatSubtiposolicitudMapper(CatSubtiposolicitudMapper rduCatSubtiposolicitudMapper) {
        this.rduCatSubtiposolicitudMapper = rduCatSubtiposolicitudMapper;
    }

    public void setRduPaisMapper(PaisMapper rduPaisMapper) {
        this.rduPaisMapper = rduPaisMapper;
    }  
    
    public void setRduCatAnexosMapper(CatAnexosMapper rduCatAnexosMapper) {
        this.rduCatAnexosMapper = rduCatAnexosMapper;
    }

    public void setRduCatFirmaHorariosMapper(CatFirmahorariosMapper rduCatFirmaHorariosMapper) {
        this.rduCatFirmaHorariosMapper = rduCatFirmaHorariosMapper;
    }

    public void setRduCatIdiomasMapper(CatIdiomasMapper rduCatIdiomasMapper) {
        this.rduCatIdiomasMapper = rduCatIdiomasMapper;
    }

    public List<EntidadFederativa> ConsultarEntidadesFederativas() {
        List<EntidadFederativa> resultado = null;

        try {
            resultado = this.rduEntidadFederativaMapper.getAll();
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }

        return resultado;
    }

    public List<CoordinacionEstatal> ConsultarCoordinacionesEstatales(
            CoordinacionEstatal coordinacionEstatal) {
        List<CoordinacionEstatal> resultado = null;

        try {
            resultado = this.rduCoordinacionEstatalMapper.selectByExample(coordinacionEstatal);
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }

        return resultado;
    }

    public List<CatTipoanexo> consultarTiposAnexos(CatTipoanexo tipoanexo) {
        List<CatTipoanexo> resultado = null;
        try {

            resultado = (List<CatTipoanexo>) this.rduCatTipoanexoMapper.selectDynamic(tipoanexo);
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resultado;
    }

    public List<AnexosViewDto> consultarTiposAnexosDTO(AnexosViewDto tipoanexoDato) {
        List<AnexosViewDto> resultado = new ArrayList();
        List<AnexosViewDto> obtAnexos = null;
        try {
            obtAnexos = this.rduCatTipoanexoMapper.selectDynamicDTO(tipoanexoDato);
            if (null != obtAnexos) {
                for (Iterator iter = obtAnexos.iterator(); iter.hasNext();) {
                    AnexosViewDto oAnexodto = (AnexosViewDto) iter.next();
                    if (null != oAnexodto.getArchivoAnexo()) {
                        oAnexodto.setCargado(Boolean.TRUE);
                    } else {
                        oAnexodto.setCargado(Boolean.FALSE);
                    }
                    //traducciones
                    if (oAnexodto.getTxtAnexoTrad()==null){
                        if (null != oAnexodto.getArchivoTrad()) {
                            oAnexodto.setTradCargada(Boolean.TRUE);
                        } else {
                            oAnexodto.setTradCargada(Boolean.FALSE);
                        }
                    }   
                    resultado.add(oAnexodto);
                }

            }
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resultado;
    }

    public List<AnexosViewDto> consultarTiposAnexosDTOSimple(AnexosViewDto tipoanexoDato) {
        List<AnexosViewDto> resultado = new ArrayList();
        List<AnexosViewDto> obtAnexos = null;
        try {
            obtAnexos = this.rduCatTipoanexoMapper.selectDynamicDTOSimple(tipoanexoDato);
            if (null != obtAnexos) {
                for (Iterator iter = obtAnexos.iterator(); iter.hasNext();) {
                    AnexosViewDto oAnexodto = (AnexosViewDto) iter.next();
                    if (null != oAnexodto.getNombreArchivo()) {
                        oAnexodto.setCargado(Boolean.TRUE);
                    } else {
                        oAnexodto.setCargado(Boolean.FALSE);
                    }
                    //traducciones
                    if (oAnexodto.getTxtAnexoTrad()==null){
                        if (null != oAnexodto.getArchivoTrad()) {
                            oAnexodto.setTradCargada(Boolean.TRUE);
                        } else {
                            oAnexodto.setTradCargada(Boolean.FALSE);
                        }
                    }   
                    resultado.add(oAnexodto);
                }

            }
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resultado;
    }

    public List<AnexosViewDto> consultarTiposAnexosDTOSimplePatente(AnexosViewDto tipoanexoDto) {
        List<AnexosViewDto> resultado = new ArrayList();
        List<AnexosViewDto> obtAnexos = null;
        try {
            obtAnexos = this.rduCatTipoanexoMapper.selectDynamicDTOSimplePatente(tipoanexoDto);
            if (null != obtAnexos) {
                for (Iterator iter = obtAnexos.iterator(); iter.hasNext();) {
                    AnexosViewDto oAnexodto = (AnexosViewDto) iter.next();
                    if (null != oAnexodto.getNombreArchivo()) {
                        oAnexodto.setCargado(Boolean.TRUE);
                    } else {
                        oAnexodto.setCargado(Boolean.FALSE);
                    }
                    //traducciones
                    if (oAnexodto.getTxtAnexoTrad()==null){
                        if (null != oAnexodto.getArchivoTrad()) {
                            oAnexodto.setTradCargada(Boolean.TRUE);
                        } else {
                            oAnexodto.setTradCargada(Boolean.FALSE);
                        }
                    }   
                    resultado.add(oAnexodto);
                }

            }
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resultado;
    }

    public List<Pais> consultarPaises(Pais pais) {
        try {
            return rduPaisMapper.selectDynamic(pais);
        } catch (Exception e) {
            log.info("**********Error en el metod CatalogosServiceImpl.consultarPaises()*********");
            return null;
        }
    }
    
    public List<CatAnexos> consultarAnexos() {
        try {
            return rduCatAnexosMapper.selectAnexos();
        } catch (Exception e) {
            log.info("**********Error en el metod CatalogosServiceImpl.consultarAnexos()*********");
            return null;
        }
    }
    
    public List<CatAnexos> consultarAnexosApoderado() {
        try {
            return rduCatAnexosMapper.selectAnexosApoderado();
        } catch (Exception e) {
            log.info("**********Error en el metod CatalogosServiceImpl.consultarAnexosApoderado()*********");
            return null;
        }
    }
    public List<TipoUsuario> ConsultarTiposUsuarios() {
        List<TipoUsuario> resultado = null;
        try {
            resultado = this.rduTipoUsuarioMapper.getAll();
        } catch (Exception e) {
            System.out.println("------------error----------" + e.getCause());
        }
        return resultado;
    }

    public List<CatSubtiposolicitud> getByCriterio(CatSubtiposolicitud criterio) {
        List<CatSubtiposolicitud> listSubtipos = null;
        try {
            listSubtipos = this.rduCatSubtiposolicitudMapper.getByCriterio(criterio);
        } catch (Exception e) {
            System.out.println("------------error en getByCriterio----------");
        }
        return listSubtipos;
    }

    public CatEstatus getEstusById(Long id) {
        CatEstatus estatus = null;
        return null;
        //this.rducat
    }

    public List<DocumentoArticulo> consultarDocumentoArticulo(DocumentoArticulo documento) {
        List<DocumentoArticulo> listaDocumento = null;
        try {
            listaDocumento = this.rduDocumentoArticuloMapper.getByCriterio(documento);
        } catch (Exception e) {
            System.out.println("------------error en consultaDocumentoArticulo----------");
            e.printStackTrace();
        }

        return listaDocumento;
    }

    public List<TipoDocumento> consultarTipoDocumento(TipoDocumento tipoDocumento) {
        List<TipoDocumento> listaDocumento = null;
        try {
            listaDocumento = this.rduTipoDocumentoMapper.getByCriterio(tipoDocumento);
        } catch (Exception e) {
            System.out.println("------------error en consultaDocumentoArticulo----------");
            e.printStackTrace();
        }

        return listaDocumento;
    }

    public List<CatTipopersona> consultarTiposPersona(CatTipopersona catTipopersona) {
        try {
            return this.rduCatTipopersonaMapper.selectDynamic(catTipopersona);
        } catch (Exception e) {
            System.out.println("------------error en getByCriterioFormasClase----------");
        }
        return null;
    }

    public List<CatArea> getAllAreas() {
        try {
            return this.rduCatAreaMapper.selectAll();
        } catch (Exception e) {
            log.error("****ERROR>>>> " + e.getMessage());
            return null;
        }
    }

    public List<CatEstatusCertificado> loadAllEstatusCertificado() {
        try {
            return this.rduCatEstatusCertificadoMapper.loadAllEstatusCertificado();
        } catch (Exception e) {
            log.error("*****ERROR ESTATUS CERTIFICADO: ", e);
            return null;
        }
    }

    public List<CatFirmahorarios> getHorariosFirma(CatFirmahorarios horario) {
        try {
            return this.rduCatFirmaHorariosMapper.selectByRule(horario);
        } catch (Exception e) {
            System.out.println("------------error en getHorariosFirma----------");
        }
        return null;
    }

    public CatSubtiposolicitud consultarSubtipoSolById(Long id) {
        try {
            return this.rduCatSubtiposolicitudMapper.selectByPrimaryKey(id);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public CatTiposolicitud consultarTipoSolById(Long id) {
        try {
            return this.rduCatTiposolicitudMapper.selectByPrimaryKey(id);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public int insertarHorariosFirma(CatFirmahorarios horario) {
        int horarioInsertado = 0;
        try {
            horarioInsertado = this.rduCatFirmaHorariosMapper.insert(horario);
        } catch (Exception e) {
            log.error("Ocurrio un error en el metodo RduTablerosServiceImpl.copiarSolicitud: ", e);
        }
        return horarioInsertado;
    }

    public int eliminarHorariosFirma(int idHorario) {
        int horarioInsertado = 0;
        try {
            horarioInsertado = this.rduCatFirmaHorariosMapper.deleteByPrimaryKey(idHorario);
        } catch (Exception e) {
            log.error("Ocurrio un error en el metodo RduTablerosServiceImpl.copiarSolicitud: ", e);
        }
        return horarioInsertado;
    }

    public List<CatFirmahorarios> selectHorariosFirma(CatFirmahorarios horario) {
        List<CatFirmahorarios> listaHorarios = new ArrayList();
        try {
            listaHorarios = this.rduCatFirmaHorariosMapper.selectHorarios(horario);
        } catch (Exception e) {
            log.error("Ocurrio un error en el metodo RduTablerosServiceImpl.copiarSolicitud: ", e);
        }

        return listaHorarios;
    }

    public tipoSolicitudDto areaTipoSolicitud(int idSubtipoSolicitud) {
        try {
            return this.rduCatSubtiposolicitudMapper.areaTipoSolicitud(idSubtipoSolicitud);
        } catch (Exception e) {
            log.error("Ocurrio un error en el metodo RduTablerosServiceImpl.copiarSolicitud: ", e);
            return null;
        }
    }

    public List<CatTipoSolicitante> getTipoSolicitanteByCriterio(CatTipoSolicitante criterio) {
        List<CatTipoSolicitante> listtipoSol = null;
        try {
            listtipoSol = this.rduCatTipoSolicitanteMapper.getByCriterio(criterio);
        } catch (Exception e) {
            System.out.println("------------error en getByCriterio----------");
        }
        return listtipoSol;
    }

    public List<CatTiposolicitud> getTiposSolicitudesByCriterio(CatTiposolicitud criterio) {
        List<CatTiposolicitud> listaTipoSol = new ArrayList();
        try {
            listaTipoSol = this.rduCatTiposolicitudMapper.getTiposSolicitudesByCriterio(criterio);
        } catch (Exception e) {
            log.error("Ocurrio un error en el metodo getTiposSolicitudesByCriterio: ", e);
        }

        return listaTipoSol;

    }

    public List<AnexosViewDto> consultarTiposAnexosDTOPatente(AnexosViewDto tipoanexoDto) {
        List<AnexosViewDto> resultado = new ArrayList();
        List<AnexosViewDto> obtAnexos = null;
        try {
            obtAnexos = this.rduCatTipoanexoMapper.selectDynamicDTOPatentes(tipoanexoDto);
            if (null != obtAnexos) {
                for (Iterator iter = obtAnexos.iterator(); iter.hasNext();) {
                    AnexosViewDto oAnexodto = (AnexosViewDto) iter.next();
                    //if (oAnexodto.getTxtAnexo()==null){
                    if (oAnexodto.getDescripcion().equals("OTROS")){
                        if (null != oAnexodto.getArchivoAnexo() ) {
                            oAnexodto.setCargado(Boolean.TRUE);
                        } else {
                            oAnexodto.setCargado(Boolean.FALSE);
                        }
                    }else{
                        if (null != oAnexodto.getArchivoAnexo() && oAnexodto.getTxtAnexo()==null) {
                            oAnexodto.setCargado(Boolean.TRUE);
                        } else {
                            oAnexodto.setCargado(Boolean.FALSE);
                        }
                    }
                   // }
                    //if (oAnexodto.getTxtAnexoTrad()==null){
                        if (null != oAnexodto.getArchivoTrad() && oAnexodto.getTxtAnexoTrad()==null) {
                            oAnexodto.setTradCargada(Boolean.TRUE);
                        } else {
                            oAnexodto.setTradCargada(Boolean.FALSE);
                        }
                    //}    
                    resultado.add(oAnexodto);
                }

            }
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resultado;
    }
    
    

    public List<CatCapitulos> getAllCapitulos() {
        List<CatCapitulos> resultado = null;
        try {
            resultado = this.rduCatCapitulosMapper.selectAll();
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resultado;

    }

    public List<Pais> consultarNacionalidades(Pais pais) {
        try {
            return rduPaisMapper.selectNacionalidades(pais);
        } catch (Exception e) {
            log.info("**********Error en el metod CatalogosServiceImpl.consultarNacionalidades()*********");
            e.printStackTrace();
            return null;
        }
    }

    public AnexosViewDto selectAnexoDynamic(AnexosViewDto tipoAnexoDto) {
        AnexosViewDto resultado = new AnexosViewDto();
        try {
            resultado = this.rduCatTipoanexoMapper.selectAnexoDynamic(tipoAnexoDto);
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resultado;
    }

    public Map<String,Object> consultarIdiomas() {
        List<CatIdiomas> resultado = new ArrayList<CatIdiomas>();
        Map<String,Object> idiomas= new LinkedHashMap<String,Object>();
        
        try {
            resultado = this.rduCatIdiomasMapper.selectAll();
            
            for(int i =0; i < resultado.size(); i++){ 
                idiomas.put(resultado.get(i).getDescripcion(), resultado.get(i).getIdIdiomas());
            }
        
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return idiomas;
    }

}
