package mx.gob.impi.rdu.persistence.model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 *
 * Bean Usuario que representa la entidad usuario.
 * Objetivo: Fungir como receptor desde la vista, transmisor de datos al acceso a datos y vicebersa.
 */
@SuppressWarnings("serial")
public class Usuario implements Serializable
{
    
    /**
     *Constructor vacio por default
     */
    public Usuario()
    {

        usuarioSeguridad = new UsuarioSeguridad();
        pais = new Pais();
        entidadFederativa = new EntidadFederativa();
        domicilio = new Domicilio();      
        perfiles = new ArrayList<Perfil>();
        //telefonos = new ArrayList<Telefono>();     
        municipio = new Municipio();        
        this.idCoordinacionEstatal = 0;
        coordinacionEstatal = new CoordinacionEstatal();
    }

    public Usuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
    
    protected UsuarioSeguridad usuarioSeguridad;
    private CoordinacionEstatal coordinacionEstatal;

    public void setCoordinacionEstatal(CoordinacionEstatal coordinacionEstatal) {
        this.coordinacionEstatal = coordinacionEstatal;
    }

    public CoordinacionEstatal getCoordinacionEstatal() {
        return coordinacionEstatal;
    }

    public UsuarioSeguridad getUsuarioSeguridad()
    {
        return usuarioSeguridad;
    }

    public void setUsuarioSeguridad(UsuarioSeguridad usuarioSeguridad)
    {
        this.usuarioSeguridad = usuarioSeguridad;
    }
    protected Integer idTipo;

    public void setIdTipo(Integer idTipo)
    {
        this.idTipo = idTipo;
    }

    public Integer getIdTipo()
    {
        return this.idTipo;
    }
    protected Pais pais;

    public Pais getPais()
    {
        return this.pais;
    }

    public void setPais(Pais pais)
    {
        this.pais = pais;
    }
    protected Long idPais;

    public void setIdPais(Long idPais)
    {
        this.idPais = idPais;
    }

    public Long getIdPais()
    {
        return this.idPais;
    }
    protected EntidadFederativa entidadFederativa;

    public EntidadFederativa getEntidadFederativa()
    {
        return entidadFederativa;
    }

    public void setEntidadFederativa(EntidadFederativa entidadFederativa)
    {
        this.entidadFederativa = entidadFederativa;
    }
    protected Integer idEntidadFederativa;

    public Integer getIdEntidadFederativa()
    {
        return idEntidadFederativa;
    }

    public void setIdEntidadFederativa(Integer idEntidadFederativa)
    {
        this.idEntidadFederativa = idEntidadFederativa;
    }
    protected Integer idCoordinacionEstatal;

    public Integer getIdCoordinacionEstatal()
    {
        return idCoordinacionEstatal;
    }

    public void setIdCoordinacionEstatal(Integer idCoordinacionEstatal)
    {
        this.idCoordinacionEstatal = idCoordinacionEstatal;
    }
    
    protected Domicilio domicilio;

    public Domicilio getDomicilio()
    {
        return this.domicilio;
    }

    public void setDomicilio(Domicilio domicilio)
    {
        this.domicilio = domicilio;
    }
    protected Long idDomicilio;

    public void setIdDomicilio(Long idDomicilio)
    {
        this.idDomicilio = idDomicilio;
    }

    public Long getIdDomicilio()
    {
        return this.idDomicilio;
    }
//    protected Telefono telefono;
//
//    public Telefono getTelefono() {
//        return telefono;
//    }
//
//    public void setTelefono(Telefono telefono) {
//        this.telefono = telefono;
//    }
//    
//
   
    protected List<Perfil> perfiles;

    public List<Perfil> getPerfiles()
    {
        return perfiles;
    }

    public void setPerfiles(List<Perfil> perfiles)
    {
        this.perfiles = perfiles;
    }
   
    protected Long idUsuario;

    public void setIdUsuario(Long idUsuario)
    {
        this.idUsuario = idUsuario;
    }

    public Long getIdUsuario()
    {
        return this.idUsuario;
    }
    protected String nombre;

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNombre()
    {
        return this.nombre;
    }
    protected String apellidoPaterno;

    public void setApellidoPaterno(String apellidoPaterno)
    {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoPaterno()
    {
        return this.apellidoPaterno;
    }
    protected String apellidoMaterno;

    public void setApellidoMaterno(String apellidoMaterno)
    {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoMaterno()
    {
        return this.apellidoMaterno;
    }
    protected Integer idDependencia;

    public void setIdDependencia(Integer idDependencia)
    {
        this.idDependencia = idDependencia;
    }

    public Integer getIdDependencia()
    {
        return this.idDependencia;
    }    
   
    private Municipio municipio;

    public Municipio getMunicipio()
    {
        return municipio;
    }

    public void setMunicipio(Municipio municipio)
    {
        this.municipio = municipio;
    }
    
    CoordinacionEstatal cordinacionEstatal = new CoordinacionEstatal();

    public CoordinacionEstatal getCordinacionEstatal()
    {
        return cordinacionEstatal;
    }

    public void setCordinacionEstatal(CoordinacionEstatal cordinacionEstatal)
    {
        this.cordinacionEstatal = cordinacionEstatal;
    }

    /**
     * metodo toString 
     */
    @Override
    public String toString()
    {
        StringBuilder strBuffer = new StringBuilder();

        strBuffer.append("idUsuario=" + (idUsuario == null ? "" : idUsuario) + "\n ");

        strBuffer.append("nombre=" + (nombre == null ? "" : nombre) + "\n ");

        strBuffer.append("apellidoPaterno=" + (apellidoPaterno == null ? "" : apellidoPaterno) + "\n ");

        strBuffer.append("apellidoMaterno=" + (apellidoMaterno == null ? "" : apellidoMaterno) + "\n ");

        strBuffer.append("idDomicilio=" + (idDomicilio == null ? "" : idDomicilio) + "\n ");

        strBuffer.append("idTipo=" + (idTipo == null ? "" : idTipo) + "\n ");

        strBuffer.append("idDependencia=" + (idDependencia == null ? "" : idDependencia) + "\n ");

        strBuffer.append("idPais=" + (idPais == null ? "" : idPais) + "\n ");

        return strBuffer.toString();
    }

    /**
     * metodo copyPropertiesTo 
     */
    public void copyPropertiesTo(Usuario mTarget)
    {

        mTarget.setIdUsuario(this.getIdUsuario());

        mTarget.setNombre(this.getNombre());

        mTarget.setApellidoPaterno(this.getApellidoPaterno());

        mTarget.setApellidoMaterno(this.getApellidoMaterno());

        mTarget.setIdDomicilio(this.getIdDomicilio());

        mTarget.setIdTipo(this.getIdTipo());

        mTarget.setIdDependencia(this.getIdDependencia());
      

        mTarget.setIdPais(this.getIdPais());

    }

    /**
     * metodo equals
     */
    @Override
    public boolean equals(Object mobjPrueba)
    {
        if (!(mobjPrueba instanceof Usuario))
        {
            return false;
        }
        Usuario objBuffer = (Usuario) mobjPrueba;

        if (this.getIdUsuario() != objBuffer.getIdUsuario()
                && (this.getIdUsuario() == null || objBuffer.getIdUsuario() == null
                || !this.getIdUsuario().equals(objBuffer.getIdUsuario())))
        {
            return false;
        }

        return true;
    }

    /**
     * metodo hashCode
     */
    @Override
    public int hashCode()
    {
        int ret = 0;

        if (this.getIdUsuario() != null)
        {
            ret += this.getIdUsuario().hashCode();
        }
        ret *= 29;

        return ret;
    }
}