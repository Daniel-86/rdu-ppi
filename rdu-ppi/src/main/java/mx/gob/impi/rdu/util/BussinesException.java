
package mx.gob.impi.rdu.util;

@SuppressWarnings("serial")
public class BussinesException extends Exception { 
	
	ErrorEnumeration idInternacionalizacion;

	
	public BussinesException() {
		super();
	}

	public BussinesException( ErrorEnumeration error) {
		super();
		idInternacionalizacion = error;
	}

	public BussinesException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BussinesException(String arg0) {
		super(arg0);
	}

	public BussinesException(Throwable arg0) {
		super(arg0);
	}

	public ErrorEnumeration getError() {
		return idInternacionalizacion;
	}
	
}
