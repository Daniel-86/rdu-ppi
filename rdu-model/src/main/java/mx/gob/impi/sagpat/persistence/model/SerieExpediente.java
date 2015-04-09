package mx.gob.impi.sagpat.persistence.model;

import java.util.logging.Logger;



public class SerieExpediente implements java.io.Serializable { 
    private String codOficina;

    private Integer serExped;

    private String tipExped;

    private Integer rowVersion;

    private String indActiva;

    private String indAnual;

    private Integer numUltimo;

    public SerieExpediente() {
    }
   
    public SerieExpediente(String codOficina, Integer serExped, String tipExped) {
        this.codOficina = codOficina;
        this.serExped = serExped;
        this.tipExped = tipExped;
    }
    
    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina == null ? null : codOficina.trim();
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

    public String getIndActiva() {
        return indActiva;
    }

    public void setIndActiva(String indActiva) {
        this.indActiva = indActiva == null ? null : indActiva.trim();
    }

    public String getIndAnual() {
        return indAnual;
    }

    public void setIndAnual(String indAnual) {
        this.indAnual = indAnual == null ? null : indAnual.trim();
    }

    public Integer getNumUltimo() {
        return numUltimo;
    }

    public void setNumUltimo(Integer numUltimo) {
        this.numUltimo = numUltimo;
    }
}