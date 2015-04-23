/*<
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.gob.impi.rdu.dto.ExpedientesDto;
import mx.gob.impi.rdu.service.FlujosGralesViewService;
import mx.gob.impi.rdu.util.TipoTramiteEnum;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import mx.gob.impi.sagpat.persistence.model.Solicitud;

/**
 *
 * @author IEGARCIA
 */
public class ExpedienteLazyDM extends LazyDataModel<ExpedientesDto>{
    private List<ExpedientesDto> expedientes;
    private FlujosGralesViewService service;
    private List<Integer> usuarios;
    private Integer idArea;
    private Integer idTipoSolicitud;
    private Integer ultimaSemana;
    private Integer ultimoMes;
    private String fechaInicio;
    private String fechaFin;
    private int totalReg;

    public ExpedienteLazyDM(FlujosGralesViewService service, List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {        
        this.service = service;
        this.usuarios = usuarios;
        this.idArea = idArea;
        this.idTipoSolicitud = idTipoSolicitud;
        this.ultimaSemana = ultimaSemana;
        this.ultimoMes = ultimoMes;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        expedientes = new ArrayList<>();
    }

    
    public void setService(FlujosGralesViewService service){
        this.service = service;
    }


    @Override
    public List<ExpedientesDto> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, String> map) {
        expedientes.clear();
        
        if(getRowCount() <= 0){
            int total = obtenerTotalResultados();            
            setRowCount(total);
        }
        
        expedientes = obtenerExpedientes(startingAt, maxPerPage);
                        
        setPageSize(maxPerPage);
        
        return expedientes;
    }
    
    /**
     * Metodo que permite obtener los expedientes para el tablero electronico.
     * Se decide por el tipo de idTipoSolicitud utilizado en la creación del objeto.
     * @param start Numero desde donde iniciará la paginación en la lista de resultados
     * @param maxPerPage Numero de resultados que traera por pagina
     * @return Una lista de Expedientes encontrados
     */
    private List<ExpedientesDto> obtenerExpedientes(int start, int maxPerPage){
        List<ExpedientesDto> expedientesEncontrados = new ArrayList<>();
        
        
        if (idTipoSolicitud.intValue() == TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite()) {            
            expedientesEncontrados = service.obtenerExpedientesPaginados(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin, start, maxPerPage);
            
        }else if (idTipoSolicitud.intValue() == TipoTramiteEnum.SOL_SIT.getIdTipoTramite()) {            
            expedientesEncontrados = service.obtenerExpedientesPaginados(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin, start, maxPerPage);
            
        }else if (idTipoSolicitud.intValue() == TipoTramiteEnum.SOL_PPI.getIdTipoTramite()) {            
            expedientesEncontrados = service.obtenerExpedientesPaginados(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin, start, maxPerPage);
            
        }else if (idTipoSolicitud.intValue() == TipoTramiteEnum.NOTIFICACIONES.getIdTipoTramite()) {            
            expedientesEncontrados = service.obtenerExpedientesNotPaginados(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin, start, maxPerPage);            
        }else if (idTipoSolicitud.intValue() == TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite()) {            
            expedientesEncontrados = service.obtenerExpedientesPromPaginados(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin, start, maxPerPage);            
        }
        
        return expedientesEncontrados;
    }
    
    /**
     * Obtiene el total de resultados de la consulta. Se basa en el idTipo de solicitud
     * para decidir si busca en expedientes, expedientes promociones o expedientes notificaciones.
     * @return El total de resultados de la busqueda.
     */
    private int obtenerTotalResultados(){
        int total = 0;
        
        if (idTipoSolicitud.intValue() == TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite()) {
            total = service.obtenerTotalExpedientes(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
            
        }else if (idTipoSolicitud.intValue() == TipoTramiteEnum.SOL_SIT.getIdTipoTramite()) {
            total = service.obtenerTotalExpedientes(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
            
        }else if (idTipoSolicitud.intValue() == TipoTramiteEnum.SOL_PPI.getIdTipoTramite()) {
            total = service.obtenerTotalExpedientes(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
            
        }else if (idTipoSolicitud.intValue() == TipoTramiteEnum.NOTIFICACIONES.getIdTipoTramite()) {
            service.obtenerExpedientesNot(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
            total = service.obtenerTotalExpedientesNot(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
            
        }else if (idTipoSolicitud.intValue() == TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite()) {
            service.obtenerExpedientesProm(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
            total = service.obtenerTotalExpedientesProm(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
        }
        totalReg = total;
        
        return totalReg;
    }
    

    @Override
    public ExpedientesDto getRowData() {
        if( isRowAvailable() )
            return super.getRowData();
        return null;
    }

    @Override
    public Object getRowKey(ExpedientesDto object) {
        if( object == null )
            return null;
        return object.getExpediente();
        
        
    }

    @Override
    public void setRowIndex(int rowIndex) {
        super.setRowIndex(rowIndex); //To change body of generated methods, choose Tools | Templates.
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }    
}
