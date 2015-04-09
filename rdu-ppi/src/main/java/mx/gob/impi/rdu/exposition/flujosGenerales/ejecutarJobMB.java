package mx.gob.impi.rdu.exposition.flujosGenerales;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import org.apache.log4j.Logger;

/**
 *
 * @author JBMM
 */
@ManagedBean(name = "ejecutarJobMB")
@ViewScoped
public class ejecutarJobMB implements Serializable {
    private Logger log = Logger.getLogger(this.getClass());

   @ManagedProperty(value = "#{flujosgralesViewService}")
   private FlujosGralesViewServiceImpl flujosgralesViewService;
   private String resultado;

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

   private boolean verErrores;
   private List<String> errores;

    public boolean isVerErrores() {
        return verErrores;
    }

    public void setVerErrores(boolean verErrores) {
        this.verErrores = verErrores;
    }

        public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

   public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
       this.flujosgralesViewService = flujosgralesViewService;
   }

    public void ejecutarJob(){
       log.info(" ***DENTRO DE ejecutarJob");
       try {
           resultado =this.flujosgralesViewService.jobeliminarAcuse();

       } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
    }

    public void ejecutarDesactivarSolPreparacion(){
       log.info(" ***DENTRO DE ejecutarJob");
       try {
           this.flujosgralesViewService.eliminarTramitesJob();

       } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
    }


}
