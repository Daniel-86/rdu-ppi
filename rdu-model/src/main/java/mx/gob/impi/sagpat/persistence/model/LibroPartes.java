package mx.gob.impi.sagpat.persistence.model;



import java.util.Date;

public class LibroPartes implements java.io.Serializable {

    public LibroPartes() {
    }

    public LibroPartes(String codOficina, Date fecProceso, String tipLibro) {
        this.codOficina = codOficina;
        this.fecProceso = fecProceso;
        this.tipLibro = tipLibro;
    }
    
     public LibroPartes(String codOficina, Date fecProceso, String tipLibro, Integer numPrimero) {
        this.codOficina = codOficina;
        this.fecProceso = fecProceso;
        this.tipLibro = tipLibro;
        this.numPrimero = numPrimero;
        
    }
    
    
    
    private String codOficina;

    private Date fecProceso;

    private String tipLibro;

    private Integer rowVersion;

    private Integer numPrimero;

    private Integer numUltimo;

    private String indAbierto;

    private String indCerrado;

    private Date fecFinCapturaGlosa;

    private Date fecFinDigitalizacion;

    private Date fecFinGlosaKeyfile;

    private Date fecFinCapturaDatos;

    private Date fecFinFiguras;

    private Date fecFinGlosaFisica;

    private Date fecFinCapturaDatosProm;

    private Date fecFinCapturaGlosa1;

    private Date fecFinCapturaDatosProm1;

    private Date fecFinCapturaDatos1;

    private Date fecFinCapturaGlosa2;

    private Date fecFinCapturaDatosProm2;

    private Date fecFinCapturaDatos2;

    private Date fecFinCapturaGlosa3;

    private Date fecFinCapturaDatosProm3;

    private Date fecFinCapturaDatos3;

    private Date fecFinCapturaGlosa4;

    private Date fecFinCapturaDatosProm4;

    private Date fecFinCapturaDatos4;

    private Date fecFinCapturaGlosa5;

    private Date fecFinCapturaDatosProm5;

    private Date fecFinCapturaDatos5;

    private Date fecFinCapturaGlosa6;

    private Date fecFinCapturaDatosProm6;

    private Date fecFinCapturaDatos6;

    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina == null ? null : codOficina.trim();
    }

    public Date getFecProceso() {
        return fecProceso;
    }

    public void setFecProceso(Date fecProceso) {
        this.fecProceso = fecProceso;
    }

    public String getTipLibro() {
        return tipLibro;
    }

    public void setTipLibro(String tipLibro) {
        this.tipLibro = tipLibro == null ? null : tipLibro.trim();
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Integer getNumPrimero() {
        return numPrimero;
    }

    public void setNumPrimero(Integer numPrimero) {
        this.numPrimero = numPrimero;
    }

    public Integer getNumUltimo() {
        return numUltimo;
    }

    public void setNumUltimo(Integer numUltimo) {
        this.numUltimo = numUltimo;
    }

    public String getIndAbierto() {
        return indAbierto;
    }

    public void setIndAbierto(String indAbierto) {
        this.indAbierto = indAbierto == null ? null : indAbierto.trim();
    }

    public String getIndCerrado() {
        return indCerrado;
    }

    public void setIndCerrado(String indCerrado) {
        this.indCerrado = indCerrado == null ? null : indCerrado.trim();
    }

    public Date getFecFinCapturaGlosa() {
        return fecFinCapturaGlosa;
    }

    public void setFecFinCapturaGlosa(Date fecFinCapturaGlosa) {
        this.fecFinCapturaGlosa = fecFinCapturaGlosa;
    }

    public Date getFecFinDigitalizacion() {
        return fecFinDigitalizacion;
    }

    public void setFecFinDigitalizacion(Date fecFinDigitalizacion) {
        this.fecFinDigitalizacion = fecFinDigitalizacion;
    }

    public Date getFecFinGlosaKeyfile() {
        return fecFinGlosaKeyfile;
    }

    public void setFecFinGlosaKeyfile(Date fecFinGlosaKeyfile) {
        this.fecFinGlosaKeyfile = fecFinGlosaKeyfile;
    }

    public Date getFecFinCapturaDatos() {
        return fecFinCapturaDatos;
    }

    public void setFecFinCapturaDatos(Date fecFinCapturaDatos) {
        this.fecFinCapturaDatos = fecFinCapturaDatos;
    }

    public Date getFecFinFiguras() {
        return fecFinFiguras;
    }

    public void setFecFinFiguras(Date fecFinFiguras) {
        this.fecFinFiguras = fecFinFiguras;
    }

    public Date getFecFinGlosaFisica() {
        return fecFinGlosaFisica;
    }

    public void setFecFinGlosaFisica(Date fecFinGlosaFisica) {
        this.fecFinGlosaFisica = fecFinGlosaFisica;
    }

    public Date getFecFinCapturaDatosProm() {
        return fecFinCapturaDatosProm;
    }

    public void setFecFinCapturaDatosProm(Date fecFinCapturaDatosProm) {
        this.fecFinCapturaDatosProm = fecFinCapturaDatosProm;
    }

    public Date getFecFinCapturaGlosa1() {
        return fecFinCapturaGlosa1;
    }

    public void setFecFinCapturaGlosa1(Date fecFinCapturaGlosa1) {
        this.fecFinCapturaGlosa1 = fecFinCapturaGlosa1;
    }

    public Date getFecFinCapturaDatosProm1() {
        return fecFinCapturaDatosProm1;
    }

    public void setFecFinCapturaDatosProm1(Date fecFinCapturaDatosProm1) {
        this.fecFinCapturaDatosProm1 = fecFinCapturaDatosProm1;
    }

    public Date getFecFinCapturaDatos1() {
        return fecFinCapturaDatos1;
    }

    public void setFecFinCapturaDatos1(Date fecFinCapturaDatos1) {
        this.fecFinCapturaDatos1 = fecFinCapturaDatos1;
    }

    public Date getFecFinCapturaGlosa2() {
        return fecFinCapturaGlosa2;
    }

    public void setFecFinCapturaGlosa2(Date fecFinCapturaGlosa2) {
        this.fecFinCapturaGlosa2 = fecFinCapturaGlosa2;
    }

    public Date getFecFinCapturaDatosProm2() {
        return fecFinCapturaDatosProm2;
    }

    public void setFecFinCapturaDatosProm2(Date fecFinCapturaDatosProm2) {
        this.fecFinCapturaDatosProm2 = fecFinCapturaDatosProm2;
    }

    public Date getFecFinCapturaDatos2() {
        return fecFinCapturaDatos2;
    }

    public void setFecFinCapturaDatos2(Date fecFinCapturaDatos2) {
        this.fecFinCapturaDatos2 = fecFinCapturaDatos2;
    }

    public Date getFecFinCapturaGlosa3() {
        return fecFinCapturaGlosa3;
    }

    public void setFecFinCapturaGlosa3(Date fecFinCapturaGlosa3) {
        this.fecFinCapturaGlosa3 = fecFinCapturaGlosa3;
    }

    public Date getFecFinCapturaDatosProm3() {
        return fecFinCapturaDatosProm3;
    }

    public void setFecFinCapturaDatosProm3(Date fecFinCapturaDatosProm3) {
        this.fecFinCapturaDatosProm3 = fecFinCapturaDatosProm3;
    }

    public Date getFecFinCapturaDatos3() {
        return fecFinCapturaDatos3;
    }

    public void setFecFinCapturaDatos3(Date fecFinCapturaDatos3) {
        this.fecFinCapturaDatos3 = fecFinCapturaDatos3;
    }

    public Date getFecFinCapturaGlosa4() {
        return fecFinCapturaGlosa4;
    }

    public void setFecFinCapturaGlosa4(Date fecFinCapturaGlosa4) {
        this.fecFinCapturaGlosa4 = fecFinCapturaGlosa4;
    }

    public Date getFecFinCapturaDatosProm4() {
        return fecFinCapturaDatosProm4;
    }

    public void setFecFinCapturaDatosProm4(Date fecFinCapturaDatosProm4) {
        this.fecFinCapturaDatosProm4 = fecFinCapturaDatosProm4;
    }

    public Date getFecFinCapturaDatos4() {
        return fecFinCapturaDatos4;
    }

    public void setFecFinCapturaDatos4(Date fecFinCapturaDatos4) {
        this.fecFinCapturaDatos4 = fecFinCapturaDatos4;
    }

    public Date getFecFinCapturaGlosa5() {
        return fecFinCapturaGlosa5;
    }

    public void setFecFinCapturaGlosa5(Date fecFinCapturaGlosa5) {
        this.fecFinCapturaGlosa5 = fecFinCapturaGlosa5;
    }

    public Date getFecFinCapturaDatosProm5() {
        return fecFinCapturaDatosProm5;
    }

    public void setFecFinCapturaDatosProm5(Date fecFinCapturaDatosProm5) {
        this.fecFinCapturaDatosProm5 = fecFinCapturaDatosProm5;
    }

    public Date getFecFinCapturaDatos5() {
        return fecFinCapturaDatos5;
    }

    public void setFecFinCapturaDatos5(Date fecFinCapturaDatos5) {
        this.fecFinCapturaDatos5 = fecFinCapturaDatos5;
    }

    public Date getFecFinCapturaGlosa6() {
        return fecFinCapturaGlosa6;
    }

    public void setFecFinCapturaGlosa6(Date fecFinCapturaGlosa6) {
        this.fecFinCapturaGlosa6 = fecFinCapturaGlosa6;
    }

    public Date getFecFinCapturaDatosProm6() {
        return fecFinCapturaDatosProm6;
    }

    public void setFecFinCapturaDatosProm6(Date fecFinCapturaDatosProm6) {
        this.fecFinCapturaDatosProm6 = fecFinCapturaDatosProm6;
    }

    public Date getFecFinCapturaDatos6() {
        return fecFinCapturaDatos6;
    }

    public void setFecFinCapturaDatos6(Date fecFinCapturaDatos6) {
        this.fecFinCapturaDatos6 = fecFinCapturaDatos6;
    }
}