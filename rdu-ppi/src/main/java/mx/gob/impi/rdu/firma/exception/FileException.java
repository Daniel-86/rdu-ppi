package mx.gob.impi.rdu.firma.exception;


import mx.gob.impi.rdu.firma.exception.BaseBusinessException;

/**
 * Clase base que representa las excepciones de negocio del acceso a archivos.
 * 
 * @version 0.1
 * @author INFOTEC
 * 
 */
public class FileException extends BaseBusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileException(String businessMessage) {
		super(businessMessage);
	}

	public FileException(String businessMessage, Throwable throwable) {
		super(businessMessage, throwable);
	}

}
