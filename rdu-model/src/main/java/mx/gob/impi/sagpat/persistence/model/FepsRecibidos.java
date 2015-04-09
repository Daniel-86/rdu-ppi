package mx.gob.impi.sagpat.persistence.model;



import java.util.Date;

public class FepsRecibidos implements java.io.Serializable { 
    private Long folioFeps;

    private Date fechaRecepcion;

    private String title;

    private Short cveOficinaRecepcion;

    private Short cveAreaDestino;

    private Date fechaModificacion;

    private Short cveSistema;

    private String usuarioRecepcion;

    public Long getFolioFeps() {
        return folioFeps;
    }

    public void setFolioFeps(Long folioFeps) {
        this.folioFeps = folioFeps;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Short getCveOficinaRecepcion() {
        return cveOficinaRecepcion;
    }

    public void setCveOficinaRecepcion(Short cveOficinaRecepcion) {
        this.cveOficinaRecepcion = cveOficinaRecepcion;
    }

    public Short getCveAreaDestino() {
        return cveAreaDestino;
    }

    public void setCveAreaDestino(Short cveAreaDestino) {
        this.cveAreaDestino = cveAreaDestino;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Short getCveSistema() {
        return cveSistema;
    }

    public void setCveSistema(Short cveSistema) {
        this.cveSistema = cveSistema;
    }

    public String getUsuarioRecepcion() {
        return usuarioRecepcion;
    }

    public void setUsuarioRecepcion(String usuarioRecepcion) {
        this.usuarioRecepcion = usuarioRecepcion == null ? null : usuarioRecepcion.trim();
    }
}