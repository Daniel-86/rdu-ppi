/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.impi.rdu.dto.*;
import mx.gob.impi.rdu.persistence.model.*;
import mx.gob.impi.rdu.remote.RduCatalogosBeanRemote;
import org.apache.log4j.Logger;
import mx.gob.impi.rdu.util.Constantes;

/**
 *
 * @author usradmin
 */
public class CatalogosViewServiceImpl implements CatalogosViewService {

    private Logger log = Logger.getLogger(this.getClass());
    private RduCatalogosBeanRemote rduCatalogosBeanRemote;

    public void setRduCatalogosBeanRemote(RduCatalogosBeanRemote rduCatalogosBeanRemote) {
        this.rduCatalogosBeanRemote = rduCatalogosBeanRemote;
    }

    public List<EntidadFederativa> ConsultarEntidadesFederativas() {
        try {
            List<EntidadFederativa> retEntidadFederativas = this.rduCatalogosBeanRemote.ConsultarEntidadesFederativas();
            return retEntidadFederativas;

        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }

    }

    public List<CoordinacionEstatal> ConsultarCoordinacionesEstatales(CoordinacionEstatal coordinacionEstatal) {
        try {
            List<CoordinacionEstatal> retCoordinacionEstatales = this.rduCatalogosBeanRemote.ConsultarCoordinacionesEstatales(coordinacionEstatal);
            return retCoordinacionEstatales;

        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }

    }

    public List<CatSubtiposolicitud> getSubtiposSolByCriterio(CatSubtiposolicitud criterio) {
        try {

            List<CatSubtiposolicitud> listaSubtiposSol = this.rduCatalogosBeanRemote.getByCriterio(criterio);
            return listaSubtiposSol;
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;

        }
    }

    public List<Pais> consultarPaises(Pais pais) {
        try {
            return this.rduCatalogosBeanRemote.consultarPaises(pais);

        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }
    }
    
    public List<CatAnexos> consultarAnexos() {
        try {
            return this.rduCatalogosBeanRemote.consultarAnexos();

        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }
    }
        public List<CatAnexos> consultarAnexosApoderado() {
        try {
            return this.rduCatalogosBeanRemote.consultarAnexosApoderado();

        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }
    }

    public List<Pais> consultarNacionalidades(Pais pais) {
        try {
            return this.rduCatalogosBeanRemote.consultarNacionalidades(pais);

        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }
    }
    public List<AnexosViewDto> CosultaAnexosDto(AnexosViewDto anexosViewDto) {
        try {

            List<AnexosViewDto> listaAnexosDto = this.rduCatalogosBeanRemote.consultarTiposAnexosDTO(anexosViewDto);

            return listaAnexosDto;
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;

        }
    }

    public List<AnexosViewDto> CosultaAnexosDtoSimple(AnexosViewDto anexosViewDto) {
        try {

            List<AnexosViewDto> listaAnexosDto = this.rduCatalogosBeanRemote.consultarTiposAnexosDTOSimple(anexosViewDto);

            return listaAnexosDto;
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;

        }
    }
    
     public List<AnexosViewDto> CosultaAnexosDtoSimplePatente(AnexosViewDto anexosViewDto) {
        try {

            List<AnexosViewDto> listaAnexosDto = this.rduCatalogosBeanRemote.consultarTiposAnexosDTOSimplePatente(anexosViewDto);

            return listaAnexosDto;
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;

        }
    }



    public List<CatTipopersona> consultarTiposPersona(CatTipopersona catTipopersona) {
        try {
            return this.rduCatalogosBeanRemote.consultarTiposPersona(catTipopersona);
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;

        }

    }

    public List<DocumentoArticulo> cosultarDocumentoArticulo(DocumentoArticulo documentoArticulo) {
        try {

            List<DocumentoArticulo> listaDocumentoArticulo = this.rduCatalogosBeanRemote.consultarDocumentoArticulo(documentoArticulo);

            return listaDocumentoArticulo;
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;

        }
    }

    public CatFirmahorarios getHorariosFirma(CatFirmahorarios horario) {
        try {
            List<CatFirmahorarios> horarios = this.rduCatalogosBeanRemote.getHorariosFirma(horario);


            return horarios != null ? horarios.get(0) : null;

        } catch (Exception e) {
            e.printStackTrace();
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;

        }



    }


    public List<CatEstatusCertificadoDto> getAllEstatusCertificado() {
        try {
            return this.rduCatalogosBeanRemote.loadAllEstatusCertificado();
        } catch (Exception e) {
            log.error("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
        return null;
    }

    public List<CatAreaDto> getAllAreas() {

        try {

            return this.rduCatalogosBeanRemote.getAllAreas();

        } catch (Exception e) {
            log.error("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
        return null;
    }

    public List<CatFirmahorarios> selectHorarios(CatFirmahorarios horario) {
        List<CatFirmahorarios> listaHorarios = new ArrayList();
        try {
            listaHorarios = this.rduCatalogosBeanRemote.selectHorariosFirma(horario);
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
        return listaHorarios;
    }

    public int insertarHorariosFirma(CatFirmahorarios horario) {
        int horariosInsertados = 0;
        try {
            horariosInsertados = this.rduCatalogosBeanRemote.insertarHorariosFirma(horario);
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
        return horariosInsertados;
    }

    public int deleteHorarios(int idHorario) {
        int horariosEliminados = 0;
        try {
            horariosEliminados = this.rduCatalogosBeanRemote.eliminarHorariosFirma(idHorario);
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
        return horariosEliminados;
    }

    public List<CatTipoSolicitante> consultarTiposSolicitanteXCriterio(CatTipoSolicitante criterio) {
        try {

            List<CatTipoSolicitante> listaTipoSol = this.rduCatalogosBeanRemote.getTipoSolicitanteByCriterio(criterio);
            return listaTipoSol;
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;

        }
    }

    public List<CatTiposolicitud> getTiposSolicitudesByCriterio(CatTiposolicitud criterio) {
        try {

            List<CatTiposolicitud> listaTipoSol = this.rduCatalogosBeanRemote.getTiposSolicitudesByCriterio(criterio);
            return listaTipoSol;
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;

        }
    }

    public List<AnexosViewDto> ConsultaAnexosDtoPatentes(AnexosViewDto anexosViewDto) {
        try {

            List<AnexosViewDto> listaAnexosDto = this.rduCatalogosBeanRemote.consultarTiposAnexosDTOPatente(anexosViewDto);

            return listaAnexosDto;
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;

        }
    }
    
    public AnexosViewDto selectAnexoDynamic(AnexosViewDto tipoAnexoDto) {
        AnexosViewDto anexosViewDto = new AnexosViewDto();
        
        try {
            anexosViewDto = this.rduCatalogosBeanRemote.selectAnexoDynamic(tipoAnexoDto);
        } catch (Exception e) {
        }
        return anexosViewDto;
    }
    
    public Map<String,Object> consultarIdiomas() {
        Map<String,Object> listaIdiomas = new HashMap<String,Object>();
        
        try {
            listaIdiomas = this.rduCatalogosBeanRemote.consultarIdiomas();

        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }
        return listaIdiomas;
    }
    
}
