/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.util;

/**
 *
 * @author usradmin
 */
public enum TipoTramiteEnum {
    /*
     *No esta relacionado con los catalogos
     * solamente se usa para filtrar en los combos
     * y saber que consulta se debe de usar en el
     * mapper tramitemapper.expedientesByUsuario()
     */
    SOL_PATENTES(11,"Solicitudes de Patentes"),
    PROM_PATENTES(4,"Promociones de Patentes"),
    NOTIFICACIONES(5,"Notificaciones"),
    SOL_SIT(20,"Solicitudes del SIT"),
    SOL_PPI(40,"Solicitudes del PPI");

    private TipoTramiteEnum(int idTipoTramite, String descripcion) {
        this.idTipoTramite = idTipoTramite;
        this.descripcion = descripcion;
    }

    private int idTipoTramite;
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }


}
