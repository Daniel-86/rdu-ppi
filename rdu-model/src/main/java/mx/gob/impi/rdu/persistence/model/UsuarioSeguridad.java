package mx.gob.impi.rdu.persistence.model;

import java.sql.Clob;
import java.sql.Timestamp;

/**
 *
 * Bean UsuarioSeguridad que representa la entidad usuario_seguridad
 */
@SuppressWarnings("serial")
public class UsuarioSeguridad implements java.io.Serializable {

    /**
     *Constructor vacio por default
     */
    public UsuarioSeguridad() {
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.usuario
     *
     */
    protected String usuarioStr;
    /* Originalmente estaba como "usuario", sin embargo, ocasionaba conflicto con la propiedad "usuario" de tipo "Usuario", 
     * por ello se llam� a �sta propieda de tipo cadena "usuarioStr" */

    public String getUsuarioStr() {
        return usuarioStr;
    }

    public void setUsuarioStr(String usuarioStr) {
        this.usuarioStr = usuarioStr;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.clave
     *
     */
    protected String clave;

    /**
     * Asigna el valor de la propiedad clave
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Retorna el valor de la propiedad clave
     */
    public String getClave() {
        return this.clave;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.correo
     *
     */
    protected String claveConfirmada;

    public String getClaveConfirmada() {
        return claveConfirmada;
    }

    public void setClaveConfirmada(String claveConfirmada) {
        this.claveConfirmada = claveConfirmada;
    }
    protected String correo;

    /**
     * Asigna el valor de la propiedad correo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Retorna el valor de la propiedad correo
     */
    public String getCorreo() {
        return this.correo;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.fecha_creacion
     *
     */
    protected Timestamp fechaCreacion;

    /**
     * Asigna el valor de la propiedad fechaCreacion
     */
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Retorna el valor de la propiedad fechaCreacion
     */
    public Timestamp getFechaCreacion() {
        return this.fechaCreacion;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.cuenta_no_expirada
     *
     */
    protected Boolean cuentaNoExpirada;

    /**
     * Asigna el valor de la propiedad cuentaNoExpirada
     */
    public void setCuentaNoExpirada(Boolean cuentaNoExpirada) {
        this.cuentaNoExpirada = cuentaNoExpirada;
    }

    /**
     * Retorna el valor de la propiedad cuentaNoExpirada
     */
    public Boolean getCuentaNoExpirada() {
        return this.cuentaNoExpirada;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.cuenta_no_bloqueda
     *
     */
    protected Boolean cuentaNoBloqueda;

    /**
     * Asigna el valor de la propiedad cuentaNoBloqueda
     */
    public void setCuentaNoBloqueda(Boolean cuentaNoBloqueda) {
        this.cuentaNoBloqueda = cuentaNoBloqueda;
    }

    /**
     * Retorna el valor de la propiedad cuentaNoBloqueda
     */
    public Boolean getCuentaNoBloqueda() {
        return this.cuentaNoBloqueda;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.credencial_no_expirada
     *
     */
    protected Boolean credencialNoExpirada;

    /**
     * Asigna el valor de la propiedad credencialNoExpirada
     */
    public void setCredencialNoExpirada(Boolean credencialNoExpirada) {
        this.credencialNoExpirada = credencialNoExpirada;
    }

    /**
     * Retorna el valor de la propiedad credencialNoExpirada
     */
    public Boolean getCredencialNoExpirada() {
        return this.credencialNoExpirada;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.habilitado
     *
     */
    protected Boolean habilitado;

    /**
     * Asigna el valor de la propiedad habilitado
     */
    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    /**
     * Retorna el valor de la propiedad habilitado
     */
    public Boolean getHabilitado() {
        return this.habilitado;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.intentos_fallidos
     *
     */
    protected Integer intentosFallidos;

    /**
     * Asigna el valor de la propiedad intentosFallidos
     */
    public void setIntentosFallidos(Integer intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    /**
     * Retorna el valor de la propiedad intentosFallidos
     */
    public Integer getIntentosFallidos() {
        return this.intentosFallidos;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.instante_bloqueo
     *
     */
    protected Timestamp instanteBloqueo;

    /**
     * Asigna el valor de la propiedad instanteBloqueo
     */
    public void setInstanteBloqueo(Timestamp instanteBloqueo) {
        this.instanteBloqueo = instanteBloqueo;
    }

    /**
     * Retorna el valor de la propiedad instanteBloqueo
     */
    public Timestamp getInstanteBloqueo() {
        return this.instanteBloqueo;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.respuesta_secreta
     *
     */
    protected String respuestaSecreta;

    /**
     * Asigna el valor de la propiedad respuestaSecreta
     */
    public void setRespuestaSecreta(String respuestaSecreta) {
        this.respuestaSecreta = respuestaSecreta;
    }

    /**
     * Retorna el valor de la propiedad respuestaSecreta
     */
    public String getRespuestaSecreta() {
        return this.respuestaSecreta;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.id_seguridad
     *
     */
    protected String idSeguridad;

    /**
     * Asigna el valor de la propiedad idSeguridad
     */
    public void setIdSeguridad(String idSeguridad) {
        this.idSeguridad = idSeguridad;
    }

    /**
     * Retorna el valor de la propiedad idSeguridad
     */
    public String getIdSeguridad() {
        return this.idSeguridad;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.ventana_id_seguridad
     *
     */
    protected Timestamp ventanaIdSeguridad;

    /**
     * Asigna el valor de la propiedad ventanaIdSeguridad
     */
    public void setVentanaIdSeguridad(Timestamp ventanaIdSeguridad) {
        this.ventanaIdSeguridad = ventanaIdSeguridad;
    }

    /**
     * Retorna el valor de la propiedad ventanaIdSeguridad
     */
    public Timestamp getVentanaIdSeguridad() {
        return this.ventanaIdSeguridad;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.id_usuario
     *
     */
    protected Long idUsuario;

    /**
     * Asigna el valor de la propiedad idUsuario
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Retorna el valor de la propiedad idUsuario
     */
    public Long getIdUsuario() {
        return this.idUsuario;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_seguridad.id_pregunta_secreta
     *
     */
    protected Integer idPreguntaSecreta;

    /**
     * Asigna el valor de la propiedad idPreguntaSecreta
     */
    public void setIdPreguntaSecreta(Integer idPreguntaSecreta) {
        this.idPreguntaSecreta = idPreguntaSecreta;
    }

    /**
     * Retorna el valor de la propiedad idPreguntaSecreta
     */
    public Integer getIdPreguntaSecreta() {
        return this.idPreguntaSecreta;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la relacion usuario_seguridad.id_usuario
     *
     */
    private Usuario usuario;

    /**
     * Asigna el valor de la relacion idUsuario
     */
    public Usuario getUsuario() {
        return this.usuario;
    }

    /**
     * Retorna el valor de la relacion idUsuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la relacion usuario_seguridad.id_pregunta_secreta
     *
     */
    private PreguntaSecreta preguntaSecreta;

    /**
     * Asigna el valor de la relacion idPreguntaSecreta
     */
    public PreguntaSecreta getPreguntaSecreta() {
        return this.preguntaSecreta;
    }

    /**
     * Retorna el valor de la relacion idPreguntaSecreta
     */
    public void setPreguntaSecreta(PreguntaSecreta preguntaSecreta) {
        this.preguntaSecreta = preguntaSecreta;
    }

    /**
     * metodo toString 
     */
    @Override
    public String toString() {
        StringBuilder strBuffer = new StringBuilder();

        strBuffer.append("usuario=" + (usuario == null ? "" : usuario) + "\n ");

        strBuffer.append("clave=" + (clave == null ? "" : clave) + "\n ");

        strBuffer.append("correo=" + (correo == null ? "" : correo) + "\n ");

        strBuffer.append("fechaCreacion=" + (fechaCreacion == null ? "" : fechaCreacion) + "\n ");

        strBuffer.append("cuentaNoExpirada=" + (cuentaNoExpirada == null ? "" : cuentaNoExpirada) + "\n ");

        strBuffer.append("cuentaNoBloqueda=" + (cuentaNoBloqueda == null ? "" : cuentaNoBloqueda) + "\n ");

        strBuffer.append("credencialNoExpirada=" + (credencialNoExpirada == null ? "" : credencialNoExpirada) + "\n ");

        strBuffer.append("habilitado=" + (habilitado == null ? "" : habilitado) + "\n ");

        strBuffer.append("intentosFallidos=" + (intentosFallidos == null ? "" : intentosFallidos) + "\n ");

        strBuffer.append("instanteBloqueo=" + (instanteBloqueo == null ? "" : instanteBloqueo) + "\n ");

        strBuffer.append("respuestaSecreta=" + (respuestaSecreta == null ? "" : respuestaSecreta) + "\n ");

        strBuffer.append("idSeguridad=" + (idSeguridad == null ? "" : idSeguridad) + "\n ");

        strBuffer.append("ventanaIdSeguridad=" + (ventanaIdSeguridad == null ? "" : ventanaIdSeguridad) + "\n ");

        strBuffer.append("idUsuario=" + (idUsuario == null ? "" : idUsuario) + "\n ");

        strBuffer.append("idPreguntaSecreta=" + (idPreguntaSecreta == null ? "" : idPreguntaSecreta) + "\n ");

        return strBuffer.toString();
    }

    /**
     * metodo copyPropertiesTo 
     */
    public void copyPropertiesTo(UsuarioSeguridad mTarget) {

        mTarget.setUsuario(this.getUsuario());

        mTarget.setClave(this.getClave());

        mTarget.setCorreo(this.getCorreo());

        mTarget.setFechaCreacion(this.getFechaCreacion());

        mTarget.setCuentaNoExpirada(this.getCuentaNoExpirada());

        mTarget.setCuentaNoBloqueda(this.getCuentaNoBloqueda());

        mTarget.setCredencialNoExpirada(this.getCredencialNoExpirada());

        mTarget.setHabilitado(this.getHabilitado());

        mTarget.setIntentosFallidos(this.getIntentosFallidos());

        mTarget.setInstanteBloqueo(this.getInstanteBloqueo());

        mTarget.setRespuestaSecreta(this.getRespuestaSecreta());

        mTarget.setIdSeguridad(this.getIdSeguridad());

        mTarget.setVentanaIdSeguridad(this.getVentanaIdSeguridad());

        mTarget.setIdUsuario(this.getIdUsuario());

        mTarget.setIdPreguntaSecreta(this.getIdPreguntaSecreta());

    }

    /**
     * metodo equals
     */
    @Override
    public boolean equals(Object mobjPrueba) {
        if (!(mobjPrueba instanceof UsuarioSeguridad)) {
            return false;
        }
        UsuarioSeguridad objBuffer = (UsuarioSeguridad) mobjPrueba;

        if (this.getIdUsuario() != objBuffer.getIdUsuario()
                && (this.getIdUsuario() == null || objBuffer.getIdUsuario() == null
                || !this.getIdUsuario().equals(objBuffer.getIdUsuario()))) {
            return false;
        }

        return true;
    }

    /**
     * metodo hashCode
     */
    @Override
    public int hashCode() {
        int ret = 0;

        if (this.getIdUsuario() != null) {
            ret += this.getIdUsuario().hashCode();
        }
        ret *= 29;

        return ret;
    }
}
