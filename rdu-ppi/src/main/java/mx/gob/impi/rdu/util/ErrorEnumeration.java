
package mx.gob.impi.rdu.util;

public enum ErrorEnumeration {

	EXISTE("message.elemento.existe"),
	NOEXISTE("message.elemento.no.existe"),
	ACTUALIZA("message.elemento.no.actualiza"),
	ELIMINA("message.elemento.no.elimina");
		
	private final String idMensaje;

	public String getIdMensaje() {
		return idMensaje;
	}

	private ErrorEnumeration(String idMensaje) {
		this.idMensaje = idMensaje;
	}
	
}
