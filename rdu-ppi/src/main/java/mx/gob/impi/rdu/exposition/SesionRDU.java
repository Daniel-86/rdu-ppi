package mx.gob.impi.rdu.exposition;

import mx.gob.impi.rdu.persistence.model.Usuario;
import mx.gob.impi.rdu.dto.PromoventeDto;
import org.apache.log4j.Logger;

public class SesionRDU {

    private Logger log = Logger.getLogger(this.getClass());
    private Usuario usuario = new Usuario();
    private PromoventeDto promovente = new PromoventeDto();
    private int idMenu;
    private long idTramite;
    private int idTipoTramite;
    private int subTipo;
    private short descuento;
    private int tipoSolicitante;
    private Boolean copiarTramite;

    public int getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(int subTipo) {
        this.subTipo = subTipo;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public PromoventeDto getPromovente() {
        return promovente;
    }

    public void setPromovente(PromoventeDto promovente) {
        this.promovente = promovente;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(long idTramite) {
        this.idTramite = idTramite;
    }

    public Boolean getCopiarTramite() {
        return copiarTramite;
    }

    public void setCopiarTramite(Boolean copiarTramite) {
        this.copiarTramite = copiarTramite;
    }

    /**
     * @return the descuento
     */
    public short getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(short descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the tipoSolicitante
     */
    public int getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante the tipoSolicitante to set
     */
    public void setTipoSolicitante(int tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }
}
