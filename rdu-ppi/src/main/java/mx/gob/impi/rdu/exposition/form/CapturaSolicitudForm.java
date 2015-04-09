package mx.gob.impi.rdu.exposition.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import mx.gob.impi.rdu.dataModel.PersonaDM;
import mx.gob.impi.rdu.dto.AnexosViewDto;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.util.Constantes;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author JBMM
 */
public class CapturaSolicitudForm {

    private String rutaImagen = "";
    private String rutaDesc = "";
    private String rutaArcDivPre;
    private String rutaReiv = "";
    private String rutaResum = "";
    private String rutaPrioridad="";
    private UploadedFile fileFigura;
    private List<UploadedFile> fileFiguras;
    private UploadedFile fileDesc;
    private UploadedFile fileArcDivPre;
    private UploadedFile fileReivindicacion;
    private UploadedFile fileResumen;
    private UploadedFile filePrioridad; 
    private UploadedFile filePersonalidad; 
    private boolean chkAceptarTerminos;
    private boolean renderedAceptarTerminos;
    private int hideDlgTerminos;
    private boolean renderedCapturatexto;
    private boolean renderedCargarPdf;
    private boolean renderedCapturatextoReiv;
    private boolean renderedCargarPdfReiv;
    private boolean renderedPrioridades;
    private boolean renderedCargarPrioPdf;
    private String idiomaDocumento;
    private int idAnexoSel;

    //ELIMINAR CUANDO SE TERMINEN LAS PRUEBAS
    private Map<String, Object> tiposDescripcion;
    
    public int valorTipoDesc;
    public int valorTipoPrio;
    public String descText;
    public String descPdf;
    public String prioPdf;
    private boolean renderedDescText;
    public String capPCT;

    
    public String reivText;
    
    public String resuText;
    private boolean renderedResuText;
    public String resuPdf;
    private boolean renderedResuPdf;
    private String rutaFileResu = "";
    public int valorTipoResu;
    
    private Map<String, Object> idiomasdescripcion;    
    private Map<String, Object> idiomasResumen;
    private boolean renderedCargarPdfDes;
    private boolean renderedCargarPdfRei;
    private boolean renderedCargarPdfResu;
    
    public String tipoDescripcion;
    private Map<String, Object> tiposReivindicacion;
    public String tipoReivindicacion;
    private Map<String, Object> idiomas;
    public String idiomaSelected;
    private Map<String, Object> idiomasReivindicacion;
    public String idiomaSelectReivin;
    public boolean personaMoral;
    public boolean personaFisica;
    private PersonaDM solicitanteModelTmp;
    //Campos referentes a
    private TramitePatente tramitePat = new TramitePatente();
    public boolean renderedVistaPrevia;
    public boolean renderedAviso;
    
    public Date FechaInicial;
    public Date FechaActual;
    public Date maxdatePresDiv;
    public boolean renderedDisenoInd;
    
    private AnexosViewDto anexoDescripcionMe;
    private AnexosViewDto anexoResumen;
    private String idiomaDescripcion;
    private String idiomaReivindicacion;
    private String idiomaResumen;
    private String idiomaAnexo;
    private boolean renderedResumen;
    
    private String rutaAnexoTrad = "";
    private AnexosViewDto anexoSeleccionado;
    private String descTextTrad; 
    private String descPdfTrad;
    private boolean renderedDescTextTrad;
    private String textoTraduccion;
    private boolean renderedCargarPdfTrad;
    public int valorTipoTrad;
    
    private UploadedFile fileArcTraduccion;
    private boolean esDesabilitado;
    private String rutaAnexoApoderado = "";
    private boolean renderedOtroDoc;
    private boolean esDesabilitadoPDF;
    private String esTxtOtroDocumento;



    public CapturaSolicitudForm() {
        this.renderedAceptarTerminos = true;
        this.hideDlgTerminos = 0;
        
        this.renderedCapturatexto = true;
        this.renderedDescText = false;
        this.renderedCargarPdf = true;
        this.renderedPrioridades=true;   
        this.valorTipoDesc = 0;
        this.valorTipoResu = 0;
        this.renderedCargarPdfDes = true;
        this.renderedResuText = true;
        this.renderedResuPdf = true;
        this.renderedPrioridades=true;
        
        tiposDescripcion = new LinkedHashMap<String, Object>();
        tiposDescripcion.put("Capturar en Texto", Constantes.INIT);
        tiposDescripcion.put("Cargar documento pdf", Constantes.SECOND);

        tiposReivindicacion = new LinkedHashMap<String, Object>();
        tiposReivindicacion.put("Capturar en Texto", Constantes.INIT);
        tiposReivindicacion.put("Cargar documento pdf", Constantes.SECOND);

        this.personaFisica = true;
        this.personaMoral = true;
        this.solicitanteModelTmp = new PersonaDM(new ArrayList<Persona>());
        this.renderedVistaPrevia = false;
        this.renderedAviso = false;
        this.renderedDisenoInd = true;
        this.renderedResumen = false;
        
        this.descText = Constantes.INIT.toString();
        this.idiomaAnexo =Constantes.INIT.toString();
        this.idiomaDescripcion = Constantes.INIT.toString();
        
        this.idiomaReivindicacion = Constantes.INIT.toString();
        
        this.descTextTrad = Constantes.INIT.toString();
        this.descPdfTrad="";
        this.renderedDescTextTrad=false;
        this.renderedCargarPdfTrad=true;
        this.valorTipoTrad=0;
        this.esDesabilitado=true;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public UploadedFile getFileFigura() {
        return fileFigura;
    }

    public void setFileFigura(UploadedFile fileFigura) {
        this.fileFigura = fileFigura;
    }
    
    public List<UploadedFile> getFileFiguras() {
        return fileFiguras;
    }

    public void setFileFiguras(List<UploadedFile> fileFiguras) {
        this.fileFiguras = fileFiguras;
    }
    
    public UploadedFile getFileDesc() {
        return fileDesc;
    }

    public String getRutaDesc() {
        return rutaDesc;
    }

    public void setRutaDesc(String rutaDesc) {
        this.rutaDesc = rutaDesc;
    }

    public void setFileDesc(UploadedFile fileDesc) {
        this.fileDesc = fileDesc;
    }

    public boolean isChkAceptarTerminos() {
        return chkAceptarTerminos;
    }

    public void setChkAceptarTerminos(boolean chkAceptarTerminos) {
        this.chkAceptarTerminos = chkAceptarTerminos;
    }

    public boolean isRenderedAceptarTerminos() {
        return renderedAceptarTerminos;
    }

    public void setRenderedAceptarTerminos(boolean renderedAceptarTerminos) {
        this.renderedAceptarTerminos = renderedAceptarTerminos;
    }

    public int getHideDlgTerminos() {
        return hideDlgTerminos;
    }

    public void setHideDlgTerminos(int hideDlgTerminos) {
        this.hideDlgTerminos = hideDlgTerminos;
    }

    public boolean isRenderedCapturatexto() {
        return renderedCapturatexto;
    }

    public void setRenderedCapturatexto(boolean renderedCapturatexto) {
        this.renderedCapturatexto = renderedCapturatexto;
    }

    public boolean isRenderedCargarPdf() {
        return renderedCargarPdf;
    }

    public void setRenderedCargarPdf(boolean renderedCargarPdf) {
        this.renderedCargarPdf = renderedCargarPdf;
    }

    public String getIdiomaDocumento() {
        return idiomaDocumento;
    }

    public void setIdiomaDocumento(String idiomaDocumento) {
        this.idiomaDocumento = idiomaDocumento;
    }

    //ELIMINAR
    public Map<String, Object> getTiposDescripcion() {
        return tiposDescripcion;
    }

    public void setTiposDescripcion(Map<String, Object> tiposDescripcion) {
        this.tiposDescripcion = tiposDescripcion;
    }

    public int getValorTipoDesc() {
        return valorTipoDesc;
    }

    public void setValorTipoDesc(int valorTipoDesc) {
        this.valorTipoDesc = valorTipoDesc;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    public String getDescPdf() {
        return descPdf;
    }

    public void setDescPdf(String descPdf) {
        this.descPdf = descPdf;
    }

    public boolean isRenderedDescText() {
        return renderedDescText;
    }

    public void setRenderedDescText(boolean renderedDescText) {
        this.renderedDescText = renderedDescText;
    }

    public Map<String, Object> getIdiomasdescripcion() {
        return idiomasdescripcion;
    }

    public void setIdiomasdescripcion(Map<String, Object> idiomasdescripcion) {
        this.idiomasdescripcion = idiomasdescripcion;
    }

    public Map<String, Object> getIdiomasResumen() {
        return idiomasResumen;
    }

    public void setIdiomasResumen(Map<String, Object> idiomasResumen) {
        this.idiomasResumen = idiomasResumen;
    }

    public boolean isRenderedCargarPdfDes() {
        return renderedCargarPdfDes;
    }

    public void setRenderedCargarPdfDes(boolean renderedCargarPdfDes) {
        this.renderedCargarPdfDes = renderedCargarPdfDes;
    }

    public boolean isRenderedCargarPdfRei() {
        return renderedCargarPdfRei;
    }

    public void setRenderedCargarPdfRei(boolean renderedCargarPdfRei) {
        this.renderedCargarPdfRei = renderedCargarPdfRei;
    }

    public boolean isRenderedCargarPdfResu() {
        return renderedCargarPdfResu;
    }

    public void setRenderedCargarPdfResu(boolean renderedCargarPdfResu) {
        this.renderedCargarPdfResu = renderedCargarPdfResu;
    }
    
    
    
    

    public Map<String, Object> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Map<String, Object> idiomas) {
        this.idiomas = idiomas;
    }

    public String getIdiomaSelected() {
        return idiomaSelected;
    }

    public void setIdiomaSelected(String idiomaSelected) {
        this.idiomaSelected = idiomaSelected;
    }

    public String getTipoDescripcion() {
        return tipoDescripcion;
    }

    public void setTipoDescripcion(String tipoDescripcion) {
        this.tipoDescripcion = tipoDescripcion;
    }

    public Map<String, Object> getIdiomasReivindicacion() {
        return idiomasReivindicacion;
    }

    public void setIdiomasReivindicacion(Map<String, Object> idiomasReivindicacion) {
        this.idiomasReivindicacion = idiomasReivindicacion;
    }

    public String getIdiomaSelectReivin() {
        return idiomaSelectReivin;
    }

    public void setIdiomaSelectReivin(String idiomaSelectReivin) {
        this.idiomaSelectReivin = idiomaSelectReivin;
    }

    public TramitePatente getTramitePat() {
        return tramitePat;
    }

    public void setTramitePat(TramitePatente tramitePat) {
        this.tramitePat = tramitePat;
    }

    public boolean isPersonaMoral() {
        return personaMoral;
    }

    public void setPersonaMoral(boolean personaMoral) {
        this.personaMoral = personaMoral;
    }

    public boolean isPersonaFisica() {
        return personaFisica;
    }

    public void setPersonaFisica(boolean personaFisica) {
        this.personaFisica = personaFisica;
    }

    public PersonaDM getSolicitanteModelTmp() {
        return solicitanteModelTmp;
    }

    public void setSolicitanteModelTmp(PersonaDM solicitanteModelTmp) {
        this.solicitanteModelTmp = solicitanteModelTmp;
    }

    public UploadedFile getFileReivindicacion() {
        return fileReivindicacion;
    }

    public void setFileReivindicacion(UploadedFile fileReivindicacion) {
        this.fileReivindicacion = fileReivindicacion;
    }

    public UploadedFile getFileResumen() {
        return fileResumen;
    }

    public void setFileResumen(UploadedFile fileResumen) {
        this.fileResumen = fileResumen;
    }

    public String getRutaReiv() {
        return rutaReiv;
    }

    public void setRutaReiv(String rutaReiv) {
        this.rutaReiv = rutaReiv;
    }

    public String getRutaResum() {
        return rutaResum;
    }

    public void setRutaResum(String rutaResum) {
        this.rutaResum = rutaResum;
    }

    public boolean isRenderedVistaPrevia() {
        return renderedVistaPrevia;
    }

    public void setRenderedVistaPrevia(boolean renderedVistaPrevia) {
        this.renderedVistaPrevia = renderedVistaPrevia;
    }

    public Map<String, Object> getTiposReivindicacion() {
        return tiposReivindicacion;
    }

    public void setTiposReivindicacion(Map<String, Object> tiposReivindicacion) {
        this.tiposReivindicacion = tiposReivindicacion;
    }

    public String getTipoReivindicacion() {
        return tipoReivindicacion;
    }

    public void setTipoReivindicacion(String tipoReivindicacion) {
        this.tipoReivindicacion = tipoReivindicacion;
    }

    public boolean isRenderedCapturatextoReiv() {
        return renderedCapturatextoReiv;
    }

    public void setRenderedCapturatextoReiv(boolean renderedCapturatextoReiv) {
        this.renderedCapturatextoReiv = renderedCapturatextoReiv;
    }

    public boolean isRenderedCargarPdfReiv() {
        return renderedCargarPdfReiv;
    }

    public void setRenderedCargarPdfReiv(boolean renderedCargarPdfReiv) {
        this.renderedCargarPdfReiv = renderedCargarPdfReiv;
    }

    public boolean isRenderedAviso() {
        return renderedAviso;
    }

    public void setRenderedAviso(boolean renderedAviso) {
        this.renderedAviso = renderedAviso;
    }
    
    public Date getFechaActual() {
        return new Date();
    }

    public void setFechaActual(Date FechaActual) {
        this.FechaActual = FechaActual;
    }

    public Date getFechaInicial() {
        return FechaInicial;
    }

    public void setFechaInicial(Date FechaInicial) {
        this.FechaInicial = FechaInicial;
    }

    public Date getMaxdatePresDiv() {
        return maxdatePresDiv;
    }

    public void setMaxdatePresDiv(Date maxdatePresDiv) {
        this.maxdatePresDiv = maxdatePresDiv;
    }

    public boolean isRenderedDisenoInd() {
        return renderedDisenoInd;
    }

    public void setRenderedDisenoInd(boolean renderedDisenoInd) {
        this.renderedDisenoInd = renderedDisenoInd;
    }
    

    public String getReivText() {
        return reivText;
    }

    public void setReivText(String reivText) {
        this.reivText = reivText;
    }

    public String getResuText() {
        return resuText;
    }

    public void setResuText(String resuText) {
        this.resuText = resuText;
    }

    public String getResuPdf() {
        return resuPdf;
    }

    public void setResuPdf(String resuPdf) {
        this.resuPdf = resuPdf;
    }

    public boolean isRenderedResuText() {
        return renderedResuText;
    }

    public void setRenderedResuText(boolean renderedResuText) {
        this.renderedResuText = renderedResuText;
    }

    public boolean isRenderedResuPdf() {
        return renderedResuPdf;
    }

    public void setRenderedResuPdf(boolean renderedResuPdf) {
        this.renderedResuPdf = renderedResuPdf;
    }

    public String getRutaFileResu() {
        return rutaFileResu;
    }

    public void setRutaFileResu(String rutaFileResu) {
        this.rutaFileResu = rutaFileResu;
    }

    public int getValorTipoResu() {
        return valorTipoResu;
    }

    public void setValorTipoResu(int valorTipoResu) {
        this.valorTipoResu = valorTipoResu;
    }

    public AnexosViewDto getAnexoDescripcionMe() {
        return anexoDescripcionMe;
    }

    public void setAnexoDescripcionMe(AnexosViewDto anexoDescripcionMe) {
        this.anexoDescripcionMe = anexoDescripcionMe;
    }

    public AnexosViewDto getAnexoResumen() {
        return anexoResumen;
    }

    public void setAnexoResumen(AnexosViewDto anexoResumen) {
        this.anexoResumen = anexoResumen;
    }

    public String getIdiomaDescripcion() {
        return idiomaDescripcion;
    }

    public void setIdiomaDescripcion(String idiomaDescripcion) {
        this.idiomaDescripcion = idiomaDescripcion;
    }

    public String getIdiomaReivindicacion() {
        return idiomaReivindicacion;
    }

    public void setIdiomaReivindicacion(String idiomaReivindicacion) {
        this.idiomaReivindicacion = idiomaReivindicacion;
    }

    public String getIdiomaResumen() {
        return idiomaResumen;
    }

    public void setIdiomaResumen(String idiomaResumen) {
        this.idiomaResumen = idiomaResumen;
    } 

    /**
     * @return the fileArcDivPre
     */
    public UploadedFile getFileArcDivPre() {
        return fileArcDivPre;
    }

    /**
     * @param fileArcDivPre the fileArcDivPre to set
     */
    public void setFileArcDivPre(UploadedFile fileArcDivPre) {
        this.fileArcDivPre = fileArcDivPre;
    }

    /**
     * @return the rutaArcDivPre
     */
    public String getRutaArcDivPre() {
        return rutaArcDivPre;
    }

    /**
     * @param rutaArcDivPre the rutaArcDivPre to set
     */
    public void setRutaArcDivPre(String rutaArcDivPre) {
        this.rutaArcDivPre = rutaArcDivPre;
    }

    public boolean isRenderedResumen() {
        return renderedResumen;
    }

    public void setRenderedResumen(boolean renderedResumen) {
        this.renderedResumen = renderedResumen;
    }
    
    
    public boolean isRenderedPrioridades() {
        return renderedPrioridades;
    }

    public void setRenderedPrioridades(boolean renderedPrioridades) {
        this.renderedPrioridades = renderedPrioridades;
    }
    
    public String getRutaPrioridad() {
        return rutaPrioridad;
    }

    public void setRutaPrioridad(String rutaPrioridad) {
        this.rutaPrioridad = rutaPrioridad;
    }

    public UploadedFile getFilePrioridad() {
        return filePrioridad;
    }

    public void setFilePrioridad(UploadedFile filePrioridad) {
        this.filePrioridad = filePrioridad;
    }
    
    public String getPrioPdf() {
        return prioPdf;
    }

    public void setPrioPdf(String prioPdf) {
        this.prioPdf = prioPdf;
    }
    
    public boolean isRenderedCargarPrioPdf() {
        return renderedCargarPrioPdf;
    }

    public void setRenderedCargarPrioPdf(boolean renderedCargarPrioPdf) {
        this.renderedCargarPrioPdf = renderedCargarPrioPdf;
    }
    
    public int getValorTipoPrio() {
        return valorTipoPrio;
    }

    public void setValorTipoPrio(int valorTipoPrio) {
        this.valorTipoPrio = valorTipoPrio;
    }
     
    public int getIdAnexoSel() {
        return idAnexoSel;
    }

    public void setIdAnexoSel(int idAnexoSel) {
        this.idAnexoSel = idAnexoSel;
    }
    
    public String getIdiomaAnexo() {
        return idiomaAnexo;
    }

    public void setIdiomaAnexo(String idiomaAnexo) {
        this.idiomaAnexo = idiomaAnexo;
    }
    
    public AnexosViewDto getAnexoSeleccionado() {
        return anexoSeleccionado;
    }

    public void setAnexoSeleccionado(AnexosViewDto anexoSeleccionado) {
        this.anexoSeleccionado = anexoSeleccionado;
    }
     
    public String getRutaAnexoTrad() {
        return rutaAnexoTrad;
    }

    public void setRutaAnexoTrad(String rutaAnexoTrad) {
        this.rutaAnexoTrad = rutaAnexoTrad;
    }
    
    public String getDescTextTrad() {
        return descTextTrad;
    }

    public void setDescTextTrad(String descTextTrad) {
        this.descTextTrad = descTextTrad;
    }

    public boolean isRenderedDescTextTrad() {
        return renderedDescTextTrad;
    }

    public void setRenderedDescTextTrad(boolean renderedDescTextTrad) {
        this.renderedDescTextTrad = renderedDescTextTrad;
    }

    public String getTextoTraduccion() {
        return textoTraduccion;
    }

    public void setTextoTraduccion(String textoTraduccion) {
        this.textoTraduccion = textoTraduccion;
    }
    
    public boolean isRenderedCargarPdfTrad() {
        return renderedCargarPdfTrad;
    }

    public void setRenderedCargarPdfTrad(boolean renderedCargarPdfTrad) {
        this.renderedCargarPdfTrad = renderedCargarPdfTrad;
    }

    public String getDescPdfTrad() {
        return descPdfTrad;
    }

    public void setDescPdfTrad(String descPdfTrad) {
        this.descPdfTrad = descPdfTrad;
    }

    public int getValorTipoTrad() {
        return valorTipoTrad;
    }

    public void setValorTipoTrad(int valorTipoTrad) {
        this.valorTipoTrad = valorTipoTrad;
    }
    
    public UploadedFile getFileArcTraduccion() {
        return fileArcTraduccion;
    }

    public void setFileArcTraduccion(UploadedFile fileArcTraduccion) {
        this.fileArcTraduccion = fileArcTraduccion;
    }
    
    public boolean isEsDesabilitado() {
        return esDesabilitado;
    }

    public void setEsDesabilitado(boolean esDesabilitado) {
        this.esDesabilitado = esDesabilitado;
    }
    
    public String getRutaAnexoApoderado() {
        return rutaAnexoApoderado;
    }

    public void setRutaAnexoApoderado(String rutaAnexoApoderado) {
        this.rutaAnexoApoderado = rutaAnexoApoderado;
    }
   
    public boolean isRenderedOtroDoc() {
        return renderedOtroDoc;
    }

    public void setRenderedOtroDoc(boolean renderedOtroDoc) {
        this.renderedOtroDoc = renderedOtroDoc;
    }
    
    public UploadedFile getFilePersonalidad() {
        return filePersonalidad;
    }

    public void setFilePersonalidad(UploadedFile filePersonalidad) {
        this.filePersonalidad = filePersonalidad;
    }
        public boolean isEsDesabilitadoPDF() {
        return esDesabilitadoPDF;
    }

    public void setEsDesabilitadoPDF(boolean esDesabilitadoPDF) {
        this.esDesabilitadoPDF = esDesabilitadoPDF;
    }
    
    
    public String getEsTxtOtroDocumento() {
        return esTxtOtroDocumento;
    }

    public void setEsTxtOtroDocumento(String esTxtOtroDocumento) {
        this.esTxtOtroDocumento = esTxtOtroDocumento;
    }

    public String getCapPCT() {
        return capPCT;
    }

    public void setCapPCT(String capPCT) {
        this.capPCT = capPCT;
    }
}