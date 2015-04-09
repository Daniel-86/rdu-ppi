package mx.gob.impi.pase.persistence.mappers;

import java.math.BigDecimal;
import java.util.List;
import mx.gob.impi.pase.persistence.model.Promovente;


public interface PromoventeMapper {
    int deleteByPrimaryKey(Long idPromovente);

    Promovente selectByPrimaryKey(Long idPromovente);

    int updateByPrimaryKeySelective(Promovente record);

    int updateByPrimaryKey(Promovente record);

    List<Promovente> selectByPerfil(Integer idPerfil);
}