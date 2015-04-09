package mx.gob.impi.rdu.util;

/******************************************************************************/
/* Name: Gerardo Roque Celis. roque
/* Date: 08-mar-2012 10:26:16
/* Description: Perfiles del sistema
/******************************************************************************/
public enum PerfilEnumeration
{
    //El id perfil va en relacion a la BD
    ROLE_ADMIN("1","ROLE_ADMIN"),
    ROLE_CAPTURISTA("2","ROLE_CAPTURISTA"),
    ROLE_RESPONSABLE("3","ROLE_RESPONSABLE"),
    ROLE_OPERADOR_PORTAL("4","ROLE_OPERADOR_PORTAL"),
    ROLE_USUARIO_MAESTRO("5","ROLE_USUARIO_MAESTRO"),
    ROLE_USUARIO_PROMOVENTE("6","ROLE_USUARIO_PROMOVENTE"),
    ROLE_COORDINADOR_RECEPCION("18","ROLE_COORDINADOR_RECEPCION"),
    ROLE_ADMINISTRADOR_RDU("19","ROLE_ADMINISTRADOR_RDU"),
    
    ROLE_EXAMINADOR_PATENTES("42","EXAMINADOR_PATENTES"),
    ROLE_COORDINADOR_PATENTES("43","COORDINADOR_PATENTES"),    
    
    ROLE_COORDINADOR_RECEPCION_PAT("20","ROLE_COORDINADOR_RECEPCION_PAT");


    private String constante;
    private String idPerfil;

    private PerfilEnumeration(String idPerfil, String constante)
    {
        this.idPerfil = idPerfil;
        this.constante = constante;
    }

    /**
     * @return the constante
     */
    public String getConstante()
    {
        return constante;
    }

    /**
     * @param constante the constante to set
     */
    public void setConstante(String constante)
    {
        this.constante = constante;
    }

    /**
     * @return the idPerfil
     */
    public String getIdPerfil()
    {
        return idPerfil;
    }

    /**
     * @param idPerfil the idPerfil to set
     */
    public void setIdPerfil(String idPerfil)
    {
        this.idPerfil = idPerfil;
    }

}
