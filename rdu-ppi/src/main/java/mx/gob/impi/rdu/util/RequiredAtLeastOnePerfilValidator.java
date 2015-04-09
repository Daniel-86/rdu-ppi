package mx.gob.impi.rdu.util;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import mx.gob.impi.rdu.persistence.model.Perfil;
import mx.gob.impi.rdu.persistence.model.Usuario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequiredAtLeastOnePerfilValidator implements Validator {

    protected Log logger = LogFactory.getLog(getClass());

    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        // Obtenemos e password ingresado del primer campo del tag f:attribute.
        List<Perfil> perfiles = (List<Perfil>) component.getAttributes().get("perfilesActuales");
        Usuario usuario = (Usuario) component.getAttributes().get("usuario");
        
        logger.info("perfiles: " + perfiles);
        logger.info("usuario: " + usuario);
        

        if (perfiles == null || perfiles.isEmpty()) {
            logger.error("RequiredAtLeastOnePerfilValidator no cumple");
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error: ", "Debe seleccionar al menos un perfil."));
        }

    }
}
