package mx.gob.impi.rdu.firma.exception;

/**
 * Clase base que representa las excepciones de negocio
 * de la solicitud al OCSP.
 * 
 * @version 0.1
 * @author INFOTEC
 * 
 */
public class OcspException extends BaseBusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OcspException(String businessMessage) {
		super(businessMessage);
	}

	public OcspException(String businessMessage, Throwable throwable) {
		super(businessMessage, throwable);
	}
}
