package mx.gob.impi.sagpat.persistence.model;



public class TipoSolicitud implements java.io.Serializable { 
    private String tipSolicitud;

    private Integer rowVersion;

    private String codOficina;

    private String desTipo;

    private String tipExped;

    private String tipTramite;

    private String nomTabla;

    private String desTitulo;

    public TipoSolicitud(String tipSolicitud) {
        this.tipSolicitud = tipSolicitud;
    }

    public TipoSolicitud() {
    }
    
    

    public String getTipSolicitud() {
        return tipSolicitud;
    }

    public void setTipSolicitud(String tipSolicitud) {
        this.tipSolicitud = tipSolicitud == null ? null : tipSolicitud.trim();
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina == null ? null : codOficina.trim();
    }

    public String getDesTipo() {
        return desTipo;
    }

    public void setDesTipo(String desTipo) {
        this.desTipo = desTipo == null ? null : desTipo.trim();
    }

    public String getTipExped() {
        return tipExped;
    }

    public void setTipExped(String tipExped) {
        this.tipExped = tipExped == null ? null : tipExped.trim();
    }

    public String getTipTramite() {
        return tipTramite;
    }

    public void setTipTramite(String tipTramite) {
        this.tipTramite = tipTramite == null ? null : tipTramite.trim();
    }

    public String getNomTabla() {
        return nomTabla;
    }

    public void setNomTabla(String nomTabla) {
        this.nomTabla = nomTabla == null ? null : nomTabla.trim();
    }

    public String getDesTitulo() {
        return desTitulo;
    }

    public void setDesTitulo(String desTitulo) {
        this.desTitulo = desTitulo == null ? null : desTitulo.trim();
    }
}