package mx.gob.impi.rdu.firma.exception;

/**
 * Clase base que representa las excepciones de negocio.
 * 
 * @version 0.1
 * @author INFOTEC
 * 
 */
public class BaseBusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String businessMessage;

	public BaseBusinessException(String businessMessage) {
		this.businessMessage = businessMessage;
	}

	public BaseBusinessException(String businessMessage, Throwable throwable) {
		super(throwable);
		this.businessMessage = businessMessage;
	}

	public String getBusinessMessage() {
		return businessMessage;
	}

}
