package mx.gob.impi.rdu.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PasswordConfirmationValidator implements Validator{

    //private static final String KEY = "support.validator.PasswordConfirmationValidator.INVALID";
    protected Log logger = LogFactory.getLog(getClass());
    //protected ResourceBundle resourceBundle = ResourceBundle.getBundle("validationMessages");

    
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        // Obtenemos e password ingresado del primer campo del tag f:attribute.
        String password = (String) component.getAttributes().get("password");

        // Casteamos el valor de la confirmacion
        String confirm = (String) value;

        // Comparamos el password y la confirmacion
        if (!password.equals(confirm)) {
            //String mensaje = resourceBundle.getString(KEY);
            logger.error("Ya esta");
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error: ", "Contrasenias diferentes"));
        }
    }
}
