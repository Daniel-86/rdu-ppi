package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.util.Date;

public class CatFirmahorarios implements Serializable {
    
    
    
    private Long idFirmahorarios;

    private Integer  diaSemana;

    private Date fechaInhabil;

    private String horarioDesde;

    private String horarioHasta;

    public CatFirmahorarios() {
        super();
    }

    
    public CatFirmahorarios(Integer diaSemana, Date fechaInhabil) {
        this.diaSemana = diaSemana;
        this.fechaInhabil = fechaInhabil;
    }
    

    public Long getIdFirmahorarios() {
        return idFirmahorarios;
    }

    public void setIdFirmahorarios(Long idFirmahorarios) {
        this.idFirmahorarios = idFirmahorarios;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Date getFechaInhabil() {
        return fechaInhabil;
    }

    public void setFechaInhabil(Date fechaInhabil) {
        this.fechaInhabil = fechaInhabil;
    }

    public String getHorarioDesde() {
        return horarioDesde;
    }

    public void setHorarioDesde(String horarioDesde) {
        this.horarioDesde = horarioDesde == null ? null : horarioDesde.trim();
    }

    public String getHorarioHasta() {
        return horarioHasta;
    }

    public void setHorarioHasta(String horarioHasta) {
        this.horarioHasta = horarioHasta == null ? null : horarioHasta.trim();
    }
}