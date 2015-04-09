package mx.gob.impi.rdu.persistence.model;

public class CatTipoSolicitante implements java.io.Serializable{

    private Long idTipoSolicitante;
    private String descripcion;
    private Integer indActivo;
    private Integer idTipoPersona;
    private Integer aplicaDescuento;
    private CatTipopersona tipoPersona;

    public Integer getAplicaDescuento() {
        return aplicaDescuento;
    }

    public void setAplicaDescuento(Integer aplicaDescuento) {
        this.aplicaDescuento = aplicaDescuento;
    }

    public Integer getIdTipoPersona() {
        return idTipoPersona;
    }

    public void setIdTipoPersona(Integer idTipoPersona) {
        this.idTipoPersona = idTipoPersona;
    }
    
   
    public CatTipopersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(CatTipopersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }
    

    public Long getIdTipoSolicitante() {
        return idTipoSolicitante;
    }

    public void setIdTipoSolicitante(Long idTipoSolicitante) {
        this.idTipoSolicitante = idTipoSolicitante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public Integer getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Integer indActivo) {
        this.indActivo = indActivo;
    }
}
