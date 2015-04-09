package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.pase.persistence.model.Promovente;
import mx.gob.impi.pase.persistence.model.SisAlerta;
import mx.gob.impi.rdu.persistence.model.BitacoraErrores;

/**
 *
 * @author JBMM
 */
public interface RduComunesService {
    public int insertarAlertas(SisAlerta alerta);
    public List<SisAlerta> selectAlertas(SisAlerta alerta);
    public int insertBitacoraErrores(BitacoraErrores bitacora);
    List<BitacoraErrores> selectBitacoraErrores();
    public int eliminarBitacoraErrores(Short idBitacora);
    public Promovente selectPromovente(Long id);
    List<Promovente> selectPromoventeByPerfil(Integer idPerfil);
}