package mx.gob.impi.rdu.firma.exception;

/**
 * Clase base que representa las excepciones de negocio de la llave privada.
 * 
 * @version 0.1
 * @author INFOTEC
 * 
 */
public class PrivateKeyException extends BaseBusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrivateKeyException(String businessMessage) {
		super(businessMessage);
	}

	public PrivateKeyException(String businessMessage, Throwable throwable) {
		super(businessMessage, throwable);
	}
}
