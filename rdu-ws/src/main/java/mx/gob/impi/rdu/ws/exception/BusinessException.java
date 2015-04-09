package mx.gob.impi.rdu.ws.exception;

/**
 *
 * @author winter
 */
public class BusinessException extends Exception {

    /**
     * Atributo que guarda el valor de la propiedad
     */
    private static final long serialVersionUID = 1L;

    private String code;

    /**
     * Constructor
     */
    public BusinessException() {
    }

    /**
     * Constructor
     *
     * @param message
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param message
     */
    public BusinessException(String message, String code) {
        super(message);
        this.setCode(code);
    }

    /**
     * Constructor
     *
     * @param message
     */
    public BusinessException(String message, int code) {
        super(message);
        this.setCode(String.valueOf(code));
    }

    /**
     * Constructor
     *
     * @param cause
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor
     *
     * @param message
     * @param cause
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

}