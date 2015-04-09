package mx.gob.impi.sigappi.persistence.model;

import mx.gob.impi.sigappi.persistence.model.*;
import java.io.Serializable;
import java.util.Date;

public class KfContenedores implements Serializable {

    private String title;
    private String person;
    private String type;
    private String description;
    private String fecha;
    private String subclassid;
    private String security;
    private String servidor;
    private String objid;
    private String objidarchivero;
    private String pc;
    private Integer folio;
    private Integer serie;
    private Integer folioSeccion;
    private String accion;
    private Integer folioAccion;
    private String folioString;
    private String folioexp;
    private String objid2;
    private Integer indResuelto;
    private String usuario;
    private Date fechaAsignacion;
    private String titlePadre;
    private Integer indPatente;
    private Integer indResolNotificada;
    private Integer subtipoDocumento;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSubclassid() {
        return subclassid;
    }

    public void setSubclassid(String subclassid) {
        this.subclassid = subclassid;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getObjid() {
        return objid;
    }

    public void setObjid(String objid) {
        this.objid = objid;
    }

    public String getObjidarchivero() {
        return objidarchivero;
    }

    public void setObjidarchivero(String objidarchivero) {
        this.objidarchivero = objidarchivero;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public Integer getFolioSeccion() {
        return folioSeccion;
    }

    public void setFolioSeccion(Integer folioSeccion) {
        this.folioSeccion = folioSeccion;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Integer getFolioAccion() {
        return folioAccion;
    }

    public void setFolioAccion(Integer folioAccion) {
        this.folioAccion = folioAccion;
    }

    public String getFolioString() {
        return folioString;
    }

    public void setFolioString(String folioString) {
        this.folioString = folioString;
    }

    public String getFolioexp() {
        return folioexp;
    }

    public void setFolioexp(String folioexp) {
        this.folioexp = folioexp;
    }

    public String getObjid2() {
        return objid2;
    }

    public void setObjid2(String objid2) {
        this.objid2 = objid2;
    }

    public Integer getIndResuelto() {
        return indResuelto;
    }

    public void setIndResuelto(Integer indResuelto) {
        this.indResuelto = indResuelto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getTitlePadre() {
        return titlePadre;
    }

    public void setTitlePadre(String titlePadre) {
        this.titlePadre = titlePadre;
    }

    public Integer getIndPatente() {
        return indPatente;
    }

    public void setIndPatente(Integer indPatente) {
        this.indPatente = indPatente;
    }

    public Integer getIndResolNotificada() {
        return indResolNotificada;
    }

    public void setIndResolNotificada(Integer indResolNotificada) {
        this.indResolNotificada = indResolNotificada;
    }

    public Integer getSubtipoDocumento() {
        return subtipoDocumento;
    }

    public void setSubtipoDocumento(Integer subtipoDocumento) {
        this.subtipoDocumento = subtipoDocumento;
    }

}
