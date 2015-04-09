/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
 
@FacesValidator("urlValidator")
public class UrlValidator implements Validator{
 
        private static final String URL_PATTERN = "^[a-zA-Z0-9\\-\\._]+(\\.[a-zA-Z0-9\\-\\._]+){2,}(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\+&amp;%\\$#_]*)?$";
	private Pattern pattern;
	private Matcher matcher;
 
	public UrlValidator(){
		  pattern = Pattern.compile(URL_PATTERN);
	}
 
	//@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
 
		matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){
 
			FacesMessage msg = 
				new FacesMessage("URL validation failed.", 
						"Formato de URL invalido.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
 
		}
 
	}
}