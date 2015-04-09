
package mx.gob.impi.rdu.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 * Descripción:
 * Clase utilitaria que expone metodos para
 * manejo de mensajes de internacionalizacion
 *
 * @author Infotec
 */

public class MessageUtils {
    
	public static final String CURRENT_LOGGING_USER = "intendedUserToLogin_FROM THIS APPLICATION";
	private static Logger log = Logger.getLogger(MessageUtils.class);

	public static void addCallBackParam(boolean exito) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.addCallbackParam("exito", exito);
	}
	
	public static void addMsg(Severity severidad, String detaili18n, Object... parametros ) {
            
            if (severidad.getOrdinal() == FacesMessage.SEVERITY_ERROR.getOrdinal())
            {
		FacesContext.
		getCurrentInstance().
		addMessage(null, new FacesMessage("Error",getResourceText(detaili18n, parametros)));
                
            }else{
		FacesContext.
		getCurrentInstance().
		addMessage(null, new FacesMessage("Mensaje",getResourceText(detaili18n, parametros)));
            
            }
	}

  public static String getResourceText(String bundleName, String key, Object... args) {
      FacesContext ctx = FacesContext.getCurrentInstance();
      String text;
      try {
         Application app = ctx.getApplication();
         ResourceBundle bundle = app.getResourceBundle(ctx, bundleName);
         if (bundle == null) {
            log.error("bundle is null");
         }
         text = bundle.getString(key);
      } catch (MissingResourceException e) {
         log.fatal("could not find labels resource '" + key + "'", e);
         return "???" + key + "???";
      }

      if (args != null) {
         text = MessageFormat.format(text, args);
      }
      return text;
   }


 public static String getResourceText(String key, Object... args) {
      String bundleName = "msgGlobal";
      FacesContext ctx = FacesContext.getCurrentInstance();
      String text;
      try {
         Application app = ctx.getApplication();
         ResourceBundle bundle = app.getResourceBundle(ctx, bundleName);
         if (bundle == null) {
            log.error("bundle is null : " +  bundleName);
         }
         text = bundle.getString(key);
      } catch (MissingResourceException e) {
         log.fatal("could not find labels resource '" + key + "'", e);
         return "???" + key + "???";
      }

      if (args != null) {
		  //si los argumentos son i18n tambien debemos barrerlos y traducirlos
         text = MessageFormat.format(text, args);
      }
      return text;
   }

	
}
