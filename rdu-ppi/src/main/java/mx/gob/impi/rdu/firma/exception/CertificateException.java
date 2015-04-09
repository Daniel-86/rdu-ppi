package mx.gob.impi.rdu.firma.exception;

/**
 * Clase base que representa las excepciones de negocio de los certificados.
 * 
 * @version 0.1
 * @author INFOTEC
 * 
 */
public class CertificateException extends BaseBusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CertificateException(String businessMessage) {
		super(businessMessage);
	}

	public CertificateException(String businessMessage, Throwable throwable) {
		super(businessMessage, throwable);
	}

}
