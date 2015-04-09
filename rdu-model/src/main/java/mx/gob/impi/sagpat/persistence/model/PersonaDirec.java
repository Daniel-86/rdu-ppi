package mx.gob.impi.sagpat.persistence.model;

public class PersonaDirec implements java.io.Serializable { 
    private Long codPersona;

    private Long secDireccion;

    private Integer rowVersion;

    private String dirCalle;

    private String dirColonia;

    private String dirCodPoblacion;

    private String dirPoblacion;

    private String codPaisResid;

    private String codEstado;

    private String nomEstado;

    private String codPostal;
    
    private String telefono;
    
    private String fax;
    
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }    
    
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }    
    
    public Long getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(Long codPersona) {
        this.codPersona = codPersona;
    }

    public Long getSecDireccion() {
        return secDireccion;
    }

    public void setSecDireccion(Long secDireccion) {
        this.secDireccion = secDireccion;
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getDirCalle() {
        return dirCalle;
    }

    public void setDirCalle(String dirCalle) {
        this.dirCalle = dirCalle == null ? null : dirCalle.trim();
    }

    public String getDirColonia() {
        return dirColonia;
    }

    public void setDirColonia(String dirColonia) {
        this.dirColonia = dirColonia == null ? null : dirColonia.trim();
    }

    public String getDirCodPoblacion() {
        return dirCodPoblacion;
    }

    public void setDirCodPoblacion(String dirCodPoblacion) {
        this.dirCodPoblacion = dirCodPoblacion == null ? null : dirCodPoblacion.trim();
    }

    public String getDirPoblacion() {
        return dirPoblacion;
    }

    public void setDirPoblacion(String dirPoblacion) {
        this.dirPoblacion = dirPoblacion == null ? null : dirPoblacion.trim();
    }

    public String getCodPaisResid() {
        return codPaisResid;
    }

    public void setCodPaisResid(String codPaisResid) {
        this.codPaisResid = codPaisResid == null ? null : codPaisResid.trim();
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado == null ? null : codEstado.trim();
    }

    public String getNomEstado() {
        return nomEstado;
    }

    public void setNomEstado(String nomEstado) {
        this.nomEstado = nomEstado == null ? null : nomEstado.trim();
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal == null ? null : codPostal.trim();
    }
}