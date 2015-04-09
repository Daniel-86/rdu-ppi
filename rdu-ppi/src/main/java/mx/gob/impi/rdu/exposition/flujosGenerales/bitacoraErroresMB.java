package mx.gob.impi.rdu.exposition.flujosGenerales;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.persistence.model.BitacoraErrores;
import mx.gob.impi.rdu.remote.RduCatalogosBean;
import mx.gob.impi.rdu.service.CatalogosViewServiceImpl;
import mx.gob.impi.rdu.service.FlujosGralesViewService;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.util.ContextUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author JBMM
 */
@ManagedBean(name = "bitacoraErroresMB")
@ViewScoped
public class bitacoraErroresMB implements Serializable {
   private Logger log = Logger.getLogger(this.getClass());
   private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.BitacoraErrores";
   private final ResourceBundle bundleParametrosDias = ResourceBundle.getBundle(BUNDLE_PARAMETROS);
   private String msgAvisoAct = "";
   private List<BitacoraErrores> bitacora;
   private Short idError=0;
   private boolean verErrores;

   public Short getIdError() {
        return idError;
    }

   public void setIdError(Short idError) {
        this.idError = idError;
    }

   @ManagedProperty(value = "#{flujosgralesViewService}")
   private FlujosGralesViewServiceImpl flujosgralesViewService;

   public List<BitacoraErrores> getBitacora() {
        return bitacora;
    }

   public void setBitacora(List<BitacoraErrores> bitacora) {
        this.bitacora = bitacora;
    }

   public void setVerErrores(boolean verErrores) {
       this.verErrores = verErrores;
   }

   public boolean isVerErrores() {
       return verErrores;
   }

   public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
       this.flujosgralesViewService = flujosgralesViewService;
   }

   @PostConstruct
   public void init() {      
    bitacora=this.flujosgralesViewService.selectBitacoraErrores();
   }

   public void eliminar(ActionEvent actionEvent){
       log.info("  ** DENTRO DE ELIMINAR");
       String msgAviso="";
       int resultadoEliminados=0;
       log.info(" ** DENTRO ELIMINAR this.fecha==> "+this.idError);

       if(idError>0){
           resultadoEliminados=this.flujosgralesViewService.eliminarBitacoraErrores(idError);
           log.info(" ** resultadoEliminados==> "+resultadoEliminados);
             if (resultadoEliminados>0){
                msgAviso=bundleParametrosDias.getString("BitacoraErrores.eliminado");
            }
           bitacora=this.flujosgralesViewService.selectBitacoraErrores();
       }else{
            msgAviso="Debe Seleccionar un Error para eliminarlo";
       }

        msgAvisoAct = bundleParametrosDias.getString("BitacoraErrores.eliminado");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msgAvisoAct, msgAvisoAct);
        FacesContext.getCurrentInstance().addMessage(null, message);
        verErrores = false;
   }
   
}