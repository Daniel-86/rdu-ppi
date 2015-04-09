package mx.gob.impi.sagpat.persistence.model;



import java.util.Date;

public class SolicitudDibujos implements java.io.Serializable { 
    private String codOficina;

    private Long numExped;

    private Integer secDibujos;

    private Integer serExped;

    private String tipExped;

    private Integer rowVersion;

    private Integer numConcesion;

    private Date fechaCarga;

    private String codGaceta;

    private String dibujo;

    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina == null ? null : codOficina.trim();
    }

    public Long getNumExped() {
        return numExped;
    }

    public void setNumExped(Long numExped) {
        this.numExped = numExped;
    }

    public Integer getSecDibujos() {
        return secDibujos;
    }

    public void setSecDibujos(Integer secDibujos) {
        this.secDibujos = secDibujos;
    }

    public Integer getSerExped() {
        return serExped;
    }

    public void setSerExped(Integer serExped) {
        this.serExped = serExped;
    }

    public String getTipExped() {
        return tipExped;
    }

    public void setTipExped(String tipExped) {
        this.tipExped = tipExped == null ? null : tipExped.trim();
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Integer getNumConcesion() {
        return numConcesion;
    }

    public void setNumConcesion(Integer numConcesion) {
        this.numConcesion = numConcesion;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getCodGaceta() {
        return codGaceta;
    }

    public void setCodGaceta(String codGaceta) {
        this.codGaceta = codGaceta == null ? null : codGaceta.trim();
    }

    public String getDibujo() {
        return dibujo;
    }

    public void setDibujo(String dibujo) {
        this.dibujo = dibujo == null ? null : dibujo.trim();
    }
}