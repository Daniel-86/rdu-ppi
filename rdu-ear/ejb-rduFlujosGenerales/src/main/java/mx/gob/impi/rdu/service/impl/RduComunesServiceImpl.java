package mx.gob.impi.rdu.service.impl;

import java.math.BigDecimal;
import java.util.List;
import mx.gob.impi.pase.persistence.mappers.PromoventeMapper;
import mx.gob.impi.pase.persistence.mappers.SisAlertaMapper;
import mx.gob.impi.pase.persistence.model.Promovente;
import mx.gob.impi.pase.persistence.model.SisAlerta;
import mx.gob.impi.rdu.persistence.mappers.BitacoraErroresMapper;
import mx.gob.impi.rdu.persistence.model.BitacoraErrores;
import mx.gob.impi.rdu.service.RduComunesService;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author JBMM
 */
public class RduComunesServiceImpl implements RduComunesService {

    private Logger lger = Logger.getLogger(this.getClass());
    private SisAlertaMapper rduSisAlertaMapper;
    private BitacoraErroresMapper rduBitacoraErroresMapper;
    private PromoventeMapper promoventeMapper;

    public void setPromoventeMapper(PromoventeMapper promoventeMapper) {
        this.promoventeMapper = promoventeMapper;
    }   
    
    public void setRduSisAlertaMapper(SisAlertaMapper rduSisAlertaMapper) {
        this.rduSisAlertaMapper = rduSisAlertaMapper;
    }

    public void setRduBitacoraErroresMapper(BitacoraErroresMapper rduBitacoraErroresMapper) {
        this.rduBitacoraErroresMapper = rduBitacoraErroresMapper;
    }

    public int insertarAlertas(SisAlerta alerta) {
        int rtRespuesta = 0;

        rtRespuesta = this.rduSisAlertaMapper.insertarAlertas(alerta);
        return rtRespuesta;
    }

    public List<SisAlerta> selectAlertas(SisAlerta alerta) {
        List<SisAlerta> listAlertas = this.rduSisAlertaMapper.selectAlertas(alerta);
        return listAlertas;
    }

    public int insertBitacoraErrores(BitacoraErrores bitacora) {
        int rtRespuesta = 0;
        rtRespuesta = this.rduBitacoraErroresMapper.insertBitacora(bitacora);
        return rtRespuesta;
    }

    public List<BitacoraErrores> selectBitacoraErrores() {
        List<BitacoraErrores> listBitacora = this.rduBitacoraErroresMapper.selectBitacora();
        return listBitacora;
    }
    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManagerPase", rollbackFor = Exception.class)
    public Promovente selectPromovente(Long id) {
        Promovente prm = this.promoventeMapper.selectByPrimaryKey(id);
        return prm;
    }

    public List<Promovente> selectPromoventeByPerfil(Integer idPerfil) {
        return this.promoventeMapper.selectByPerfil(idPerfil);
    }

    public int eliminarBitacoraErrores(Short idBitacora) {
        int rtRespuesta = 0;
        rtRespuesta = this.rduBitacoraErroresMapper.deleteByPrimaryKey(idBitacora);
        return rtRespuesta;
    }
}