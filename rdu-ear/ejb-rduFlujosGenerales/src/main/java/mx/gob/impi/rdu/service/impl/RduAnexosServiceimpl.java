package mx.gob.impi.rdu.service.impl;

import java.util.Iterator;
import java.util.List;
import mx.gob.impi.rdu.dto.AnexosViewDto;
import mx.gob.impi.rdu.persistence.mappers.AnexosMapper;
import mx.gob.impi.rdu.persistence.mappers.AnexosxInventorMapper;
import mx.gob.impi.rdu.persistence.mappers.PrioridadMapper;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.AnexosxInventor;
import mx.gob.impi.rdu.persistence.model.Prioridad;
import mx.gob.impi.rdu.service.RduAnexosService;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.TipoTramiteEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author JBMM
 */
public class RduAnexosServiceimpl implements RduAnexosService{
    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    @Qualifier("rduAnexosMapper")
    private AnexosMapper rduAnexosMapper;
    @Qualifier("rduPrioridadMapper")
    private PrioridadMapper rduPrioridadMapper;
    @Qualifier("rduAnexosxInventorMapper")
    private AnexosxInventorMapper rduAnexosxInventorMapper;

    public void setRduAnexosMapper(AnexosMapper rduAnexosMapper) {
        this.rduAnexosMapper = rduAnexosMapper;
    }

    public void setRduPrioridadMapper(PrioridadMapper rduPrioridadMapper) {
        this.rduPrioridadMapper = rduPrioridadMapper;
    }

    public void setRduAnexosxInventorMapper(AnexosxInventorMapper rduAnexosxInventorMapper) {
        this.rduAnexosxInventorMapper = rduAnexosxInventorMapper;
    }
    
    public int insertarAnexos(Anexos anexo) {
        int resultado=0;
        try {
            resultado = this.rduAnexosMapper.insert(anexo);
        } catch (Exception e) {
             System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }

        return resultado;
    }

    public List<Anexos> getAnexosByTramite(Long idTramite) {
        
        try {
            return this.rduAnexosMapper.selectByTramiteId(idTramite);
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return null;
        }
        
        
    }

    public int insertarAnexosDto(List<AnexosViewDto> anexosDto) {
        int resultado=0;
        Anexos nvoAnexo;
        try {
            if (null!=anexosDto){
                for (Iterator iter = anexosDto.iterator(); iter.hasNext();) {
                    AnexosViewDto oAnexodto = (AnexosViewDto) iter.next();
                    if (oAnexodto.getCargado() && oAnexodto.getIdTramite()>0 && oAnexodto.getArchivoAnexo()!=null){
                        nvoAnexo=new Anexos();
                        nvoAnexo.setArchivoAnexo(oAnexodto.getArchivoAnexo());
                        nvoAnexo.setExtension(oAnexodto.getExtension());
                        nvoAnexo.setIdAnexo(oAnexodto.getIdAnexo());
                        nvoAnexo.setIdTipoanexo(oAnexodto.getIdTipoanexo());
                        nvoAnexo.setIdTramitePromocionMarcas(oAnexodto.getIdTramitePromocionMarcas());

                        nvoAnexo.setNombreArchivo(oAnexodto.getNombreArchivo());
                        if (oAnexodto.getIdAnexo()>0){
                            nvoAnexo.setIdAnexo(oAnexodto.getIdAnexo());
                            resultado=this.rduAnexosMapper.updateByPrimaryKeyWithBLOBs(nvoAnexo);
                        }
                        else{
                            resultado=this.rduAnexosMapper.insert(nvoAnexo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resultado;
    }

    public int insertarAnexosPatente(List<AnexosViewDto> anexosDtoPatente){
        int resultado=0;
        Anexos nvoAnexo;
        try {
            if (null!=anexosDtoPatente){
                for (Iterator iter = anexosDtoPatente.iterator(); iter.hasNext();) {
                    AnexosViewDto oAnexodtoPat = (AnexosViewDto) iter.next();
                    if (oAnexodtoPat.getCargado() !=null && oAnexodtoPat.getCargado() 
                            && oAnexodtoPat.getIdTramitePatente()>0 && oAnexodtoPat.getArchivoAnexo()!=null){
                        nvoAnexo=new Anexos();
                        nvoAnexo.setArchivoAnexo(oAnexodtoPat.getArchivoAnexo());
                        nvoAnexo.setExtension(oAnexodtoPat.getExtension());
                        nvoAnexo.setIdAnexo(oAnexodtoPat.getIdAnexo());
                        nvoAnexo.setIdTipoanexo(oAnexodtoPat.getIdTipoanexo());
                        nvoAnexo.setIdTramitePatente(oAnexodtoPat.getIdTramitePatente());
                        nvoAnexo.setNombreArchivo(oAnexodtoPat.getNombreArchivo());
                        nvoAnexo.setTxtAnexo(oAnexodtoPat.getTxtAnexo());
                        nvoAnexo.setOtroIdioma(oAnexodtoPat.getOtroIdioma());
                        //Si tiene Traducción
                        if (oAnexodtoPat.getOtroIdioma()==2){
                            nvoAnexo.setArchivoTrad(oAnexodtoPat.getArchivoTrad());
                            nvoAnexo.setIdTipoanexoTrad(oAnexodtoPat.getIdTipoanexoTrad()); 
                            nvoAnexo.setNombreTrad(oAnexodtoPat.getNombreTrad()); 
                            nvoAnexo.setTxtAnexoTrad(oAnexodtoPat.getTxtAnexoTrad());
                        }
                        if (oAnexodtoPat.getIdAnexo()>0){
                            nvoAnexo.setIdAnexo(oAnexodtoPat.getIdAnexo());
                            resultado=this.rduAnexosMapper.updateByPrimaryKeyWithBLOBs(nvoAnexo);
                        }
                        else{
                            resultado=this.rduAnexosMapper.insertPatente(nvoAnexo);
                            Long idPrioridad = oAnexodtoPat.getIdPrioridad();
                                
                            Long maxIdAnexo = this.rduAnexosMapper.selectMaxIdAnexo();
                            
                            if(nvoAnexo.getIdTipoanexo().equals(Constantes.ANEXO_PRIORIDAD_PAT) ||
                                    nvoAnexo.getIdTipoanexo().equals(Constantes.ANEXO_TRADUCCION_PRIORIDAD) ){
                                
                                Prioridad updatePrioridad = new Prioridad();
                                updatePrioridad.setIdPrioridad(idPrioridad);
                            
                                if(nvoAnexo.getIdTipoanexo().equals(Constantes.ANEXO_PRIORIDAD_PAT)){
                                    updatePrioridad.setIdAnexoPrioridad(maxIdAnexo);
                                }else if(nvoAnexo.getIdTipoanexo().equals(Constantes.ANEXO_TRADUCCION_PRIORIDAD)){
                                    updatePrioridad.setIdAnexoTraduccion(maxIdAnexo);
                                }
                                
                                this.rduPrioridadMapper.updateByIdAnexo(updatePrioridad);
                            }    
//                            }else if (nvoAnexo.getIdTipoanexo().equals(Constantes.ANEXO_CESION_DERECHOS)){
//                            Long IdSolicitante = oAnexodtoPat.getIdSolicitante();
//                                AnexosxInventor anexosInventor = new AnexosxInventor();
//                                anexosInventor.setIdAnexo(maxIdAnexo);
//                                anexosInventor.setIdSolicitante(IdSolicitante);
//                                
//                                int res = this.rduAnexosxInventorMapper.insert(anexosInventor);
//                                
//                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resultado;
    }

    public int insertarAnexosPago(Anexos anexo) {
        int resultado=0;
        try {
            resultado = this.rduAnexosMapper.insertPatente(anexo);
        } catch (Exception e) {
             System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }

        return resultado;
    }

    public int deleteAnexosByIds(Long idAnexo) {
        int resultado = 0;
        try {
            resultado = this.rduAnexosMapper.deleteByPrimaryKey(idAnexo.intValue());
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resultado;
    }

    public int deleteByTypeAnexo(Anexos anexo) {
        int resultado=0;
        try {
            resultado = this.rduAnexosMapper.deleteByTypeAnexo(anexo);
        } catch (Exception e) {
             System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }

        return resultado;
    }

    public Anexos obtenerAnexosDynamic(Anexos anexo) {        
        try {
            return this.rduAnexosMapper.obtenerAnexosDynamic(anexo);
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return null;
        }
    }

}