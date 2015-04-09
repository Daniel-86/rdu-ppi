package mx.gob.impi.sagpat.persistence.model;



import java.util.Date;

public class DiaProceso implements java.io.Serializable  {
    private Date fecProceso;

    private Integer rowVersion;

    private Short serDocum;

    private Date fecHabilVto;
    private String tipoEx;

    public DiaProceso() {
    }

    public DiaProceso(Date fecProceso, String tipoEx) {
        this.fecProceso = fecProceso;
        this.tipoEx = tipoEx;
    }
    
    

    public void setTipoEx(String tipoEx) {
        this.tipoEx = tipoEx;
    }

    public String getTipoEx() {
        return tipoEx;
    }
    

    public Date getFecProceso() {
        return fecProceso;
    }

    public void setFecProceso(Date fecProceso) {
        this.fecProceso = fecProceso;
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Short getSerDocum() {
        return serDocum;
    }

    public void setSerDocum(Short serDocum) {
        this.serDocum = serDocum;
    }

    public Date getFecHabilVto() {
        return fecHabilVto;
    }

    public void setFecHabilVto(Date fecHabilVto) {
        this.fecHabilVto = fecHabilVto;
    }
}