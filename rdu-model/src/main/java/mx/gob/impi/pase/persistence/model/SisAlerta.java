package mx.gob.impi.pase.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@SuppressWarnings("serial")
public class SisAlerta implements Serializable{
    private Short idAlerta;
    private BigDecimal idPromovente;
    private Short idMotivoAlerta;
    private Date fecha;
    private String numeroCertificado;
    private Date fechaExpira;

    public String getNumeroCertificado() {
        return numeroCertificado;
    }

    public void setNumeroCertificado(String numeroCertificado) {
        this.numeroCertificado = numeroCertificado;
    }
    
    public Date getFechaExpira() {
        return fechaExpira;
    }

    public void setFechaExpira(Date fechaExpira) {
        this.fechaExpira = fechaExpira;
    }
 

    public Short getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Short idAlerta) {
        this.idAlerta = idAlerta;
    }

    public BigDecimal getIdPromovente() {
        return idPromovente;
    }

    public void setIdPromovente(BigDecimal idPromovente) {
        this.idPromovente = idPromovente;
    }

    public Short getIdMotivoAlerta() {
        return idMotivoAlerta;
    }

    public void setIdMotivoAlerta(Short idMotivoAlerta) {
        this.idMotivoAlerta = idMotivoAlerta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}