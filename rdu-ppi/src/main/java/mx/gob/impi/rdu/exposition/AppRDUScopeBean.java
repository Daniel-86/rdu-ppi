/*
 * Esta clase evita el error:
 * [/index.xhtml] java.lang.IllegalStateException: PWC3999: Cannot create a session after the response has been committed
 */
package mx.gob.impi.rdu.exposition;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

@ManagedBean
@ApplicationScoped
public class AppRDUScopeBean implements Serializable {
	private Logger log = Logger.getLogger(this.getClass());
	   public void preRenderView() {
		      HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
		      
		      //tune session params, eg. session.setMaxInactiveInterval(..);

		      //perform other pre-render stuff, like setting user context...
		   }
	   
       /**
        * Cierra la sesi�n de un usuario 
        */
       public static void cerrarSesion(){
               FacesContext context = FacesContext.getCurrentInstance();
               HttpSession httpSession = (HttpSession) context.getExternalContext()
                               .getSession(false);
               //httpSession.removeAttribute(SessionInfo.class.getName());
               if (httpSession != null){
            	   httpSession.removeAttribute("SESION_USUARIO");
               }
               
       }
       public void ProbarAccessKey(){
       	
       	log.info(":::::: Entro a ProbarAccessKey");
       	
       }
       
	   
}
