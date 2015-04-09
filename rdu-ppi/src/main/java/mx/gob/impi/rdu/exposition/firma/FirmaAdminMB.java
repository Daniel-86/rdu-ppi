/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.firma;

import com.philvarner.clamavj.ClamScan;
import com.philvarner.clamavj.ScanResult;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import mx.gob.impi.rdu.dto.CatAreaDto;
import mx.gob.impi.rdu.dto.CatEstatusCertificadoDto;
import mx.gob.impi.rdu.dto.CertificadoDto;
import mx.gob.impi.rdu.firma.exception.BaseBusinessException;
import mx.gob.impi.rdu.firma.service.CertificateValidatorImpl;
import mx.gob.impi.rdu.service.CatalogosViewServiceImpl;
import mx.gob.impi.rdu.service.FlujosGralesViewService;
import mx.gob.impi.rdu.util.BundleUtils;
import mx.gob.impi.rdu.util.CipherEncript;
import mx.gob.impi.rdu.util.ContextUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.ocsp.OCSPResp;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author
 */


@ManagedBean(name = "firmaAdminMB")
@ViewScoped
@SuppressWarnings("serial")
public class FirmaAdminMB {

    private UploadedFile fileCert;
    private UploadedFile fileKey;
    private String pass;
    private Integer idArea;
    private Integer estatus;
    private List<CatAreaDto> areas = Collections.emptyList();
    private List<CatEstatusCertificadoDto> estatusUpdate = new ArrayList<CatEstatusCertificadoDto>();
    private List<CatEstatusCertificadoDto> estatusNew = Collections.emptyList();
    private List<CertificadoDto> activeCerts = new ArrayList<CertificadoDto>();
    @ManagedProperty(value = "#{certificateValidatorService}")
    private CertificateValidatorImpl certificateValidatorService;
    @ManagedProperty(value = "#{catalogosViewService}")
    private CatalogosViewServiceImpl catalogosViewService;
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewService flujosgralesViewService;
    private Logger logger = Logger.getLogger(this.getClass());
    private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.parametros";
    final ResourceBundle bundleParametros = ResourceBundle.getBundle(BUNDLE_PARAMETROS);

    public void setCertificateValidatorService(CertificateValidatorImpl certificateValidatorService) {
        this.certificateValidatorService = certificateValidatorService;
    }

    public void setFlujosgralesViewService(FlujosGralesViewService flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    
  
    public void setCatalogosViewService(CatalogosViewServiceImpl catalogosViewService) {
        this.catalogosViewService = catalogosViewService;
    }

    @PostConstruct
    public void init() throws Exception {
         List<CatAreaDto> tmp = new ArrayList<CatAreaDto>();
        String area = (String)ContextUtils.getSession().getAttribute("area");  
        areas = this.catalogosViewService.getAllAreas();
        
        for(CatAreaDto ar : areas){        
            if(ar.getIdArea().intValue() ==new Integer(area).intValue()){
                areas = new ArrayList<CatAreaDto>();
                areas.add(ar);
                break;
            }           
        }
        estatusUpdate = this.catalogosViewService.getAllEstatusCertificado();
        estatusNew = new ArrayList<CatEstatusCertificadoDto>();
        estatusNew.add(estatusUpdate.get(0));
        estatusNew.add(estatusUpdate.get(1));
              
        activeCerts = this.flujosgralesViewService.loadAllActiveCerts(new Integer(area));
         
    }

    public void updateFila(RowEditEvent event) {

        CertificadoDto c = (CertificadoDto) event.getObject();
        UIComponent comp = event.getComponent();
        this.flujosgralesViewService.actualizaFirmaAdmin(c);
        Integer area = (Integer)ContextUtils.getSession().getAttribute("area");      
        activeCerts = this.flujosgralesViewService.loadAllActiveCerts(area);
    }

    public boolean deteccionVrus(byte[] prmFile) throws IOException {
        boolean resulDet = true;
        try {
            String hostDeteccionVirus = bundleParametros.getString("anexo.antivirus.host");
            int ptoDeteccionVirus = Integer.parseInt(bundleParametros.getString("anexo.antivirus.pruerto"));

            ClamScan cs = new ClamScan();
            cs.setHost(hostDeteccionVirus);
            cs.setPort(ptoDeteccionVirus);
            cs.setTimeout(100);
            ScanResult sr = cs.scan(prmFile);
            //se intenta dos veces ya que a la primera siempre falla
            sr = cs.scan(prmFile);
            resulDet = sr.getStatus() == sr.getStatus().PASSED ? true : false;
            logger.info(".....verificacion de virus en anexo>>> result:" + sr.getResult() + " estatus: " + sr.getStatus());
            /*
             * System.out.println("Resultado = {" + sr.getResult() + "}");
             * System.out.println("Resultado = {" + sr.getStatus() + "}");
             * System.out.println("Resultado = {" + sr.getSignature() + "}");
             */
        } catch (FileNotFoundException ex) {
            logger.fatal("Informacion de Error: -- " + ex + " -- ubicado en: " + ex.getLocalizedMessage());
        }

        return resulDet;
    }

    public void uploadFileCer(ValueChangeEvent event) throws IOException {

        UIComponent ab = event.getComponent();
        Object bb = event.getNewValue();
    }

    public void uploadFileCer2(FileUploadEvent event) throws IOException {

        UploadedFile ab = event.getFile();

    }

    public void validarVirus() {

        try {
           // if ((fileCert.getContents() != null && this.deteccionVrus(fileCert.getContents())) & (fileCert.getContents() != null && this.deteccionVrus(fileCert.getContents()))) {
             if (   (fileCert.getContents() != null ) & (fileCert.getContents() != null )     ) {
                this.guardaFirmaAdmin();
            }
     //   } catch (FileNotFoundException e) {
      //      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SELECCIONE UN ARCHIVO", BundleUtils.getResource("firma.error")));
        }catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ARCHIVO INVALIDO", BundleUtils.getResource("firma.error")));
        }

    }

    public void validarFirmaAdmin(ActionEvent event) {
        try {
            
            this.certificateValidatorService.start(fileCert.getContents(), fileKey.getContents(), pass);  //(fileCert.getContents()  , fileKey.getContents(), pass);
            String[] iss = this.certificateValidatorService.getIssuer();                    
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CERTIFICADO VALIDO", "Se puede firmar."));
            this.setFileCert(fileCert);
            event.setPhaseId(PhaseId.RENDER_RESPONSE);
        } catch (BaseBusinessException e) {
            logger.error("ERROR AL VALIDAR CERTIFICADO BUSSINES EXCEPTION >>>>>  "+ e.getMessage());
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getBusinessMessage(), BundleUtils.getResource("firma.error")));
        } catch (Exception ex) {
            logger.error("ERROR AL VALIDAR CERTIFICADO MAIN EXCEPTION >>>>>  "+ ex.getMessage());
            ex.printStackTrace();            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), BundleUtils.getResource("firma.error")));
        }
    }

    public void guardaFirmaAdmin() {

        if (this.idArea != 0 & this.estatus != 0) {


            try {

                //if (deteccionVrus(fileCert.getContents()) && deteccionVrus(fileKey.getContents())) {
               // }

                this.certificateValidatorService.start(fileCert.getContents(), fileKey.getContents(), pass);  //(fileCert.getContents()  , fileKey.getContents(), pass);
                String[] issuer = this.certificateValidatorService.getIssuer();
                OCSPResp resp = this.certificateValidatorService.obtenerResponse();
                CertificadoDto certDto = new CertificadoDto();

                Pattern pattern = Pattern.compile(BundleUtils.getResource("firma.pattern.nombresol"));
                Matcher matcher = pattern.matcher(issuer[0]);
                if (matcher.find()) {
                    certDto.setNombreAdmin(matcher.group(1));
                }

                pattern = Pattern.compile(BundleUtils.getResource("firma.pattern.serial"));
                matcher = pattern.matcher(issuer[0]);
                if (matcher.find()) {
                    certDto.setNumeroSerie(matcher.group(1));
                }

                certDto.setCerFile(fileCert.getContents());
                certDto.setFechaCarga(new Date());
                certDto.setIdEstatusCertificado(this.estatus);
                certDto.setKeyFile(fileKey.getContents());
                certDto.setPass(CipherEncript.enc(pass));
                certDto.setUsuario("XX");
                certDto.setIdCatArea(idArea);
                this.flujosgralesViewService.insertaFirmaAdmin(certDto);
                Integer area = (Integer)ContextUtils.getSession().getAttribute("area");      
                activeCerts = this.flujosgralesViewService.loadAllActiveCerts(area);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "GUARDADO SATISFACTORIAMENTE", ""));
            } catch (BaseBusinessException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getBusinessMessage(), BundleUtils.getResource("firma.error")));
            } catch (Exception ee) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AL GUARDAR CERTIFICADO", BundleUtils.getResource("firma.error")));
            }

        } else {
            String msg = (idArea == 0) ? "Area es requerido" : "";
            if (idArea == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "AREA ES REQUERIDO", ""));
            }
            if (this.estatus == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ESTATUS ES REQUERIDO", ""));
            }

        }






    }

    public UploadedFile getFileCert() {
        return fileCert;
    }

    public UploadedFile getFileKey() {
        return fileKey;
    }

    public String getPass() {
        return pass;
    }

    public void setFileCert(UploadedFile fileCert) {
        this.fileCert = fileCert;
    }

    public void setFileKey(UploadedFile fileKey) {
        this.fileKey = fileKey;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<CatAreaDto> getAreas() {
        return areas;
    }

    public void setAreas(List<CatAreaDto> areas) {
        this.areas = areas;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public List<CatEstatusCertificadoDto> getEstatusNew() {
        return estatusNew;
    }

    public List<CatEstatusCertificadoDto> getEstatusUpdate() {
        return estatusUpdate;
    }

    public void setEstatusNew(List<CatEstatusCertificadoDto> estatusNew) {
        this.estatusNew = estatusNew;
    }

    public void setEstatusUpdate(List<CatEstatusCertificadoDto> estatusUpdate) {
        this.estatusUpdate = estatusUpdate;
    }

    public void setActiveCerts(List<CertificadoDto> activeCerts) {
        this.activeCerts = activeCerts;
    }

    public List<CertificadoDto> getActiveCerts() {
        return activeCerts;
    }
}
