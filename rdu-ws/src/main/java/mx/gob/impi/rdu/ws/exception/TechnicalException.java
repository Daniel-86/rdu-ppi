package mx.gob.impi.rdu.ws.exception;

/**
 *
 * @author winter
 */
public class TechnicalException extends Exception {
    /**
     * Atributo que guarda el valor de la propiedad
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public TechnicalException() {
    }

    /**
     * Constructor
     *
     * @param message
     */
    public TechnicalException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param cause
     */
    public TechnicalException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor
     *
     * @param message
     * @param cause
     */
    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}