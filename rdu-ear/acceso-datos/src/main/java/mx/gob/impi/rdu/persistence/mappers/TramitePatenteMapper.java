package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import org.apache.ibatis.annotations.Param;

public interface TramitePatenteMapper {

    int deleteByPrimaryKey(Long idTramitePatente);

    TramitePatente selectByPrimaryKey(Long idTramitePatente);

    TramitePatente selectDatosBasicos(Long idTramite);

    int updateByPrimaryKeySelective(TramitePatente record);

    int eliminacionLogica(SolicitudPreparacionDto solicitudPreparacionDto);

    int updateByPrimaryKey(TramitePatente record);

    List<TramitePatente> selectTramitesPatentes();

    int insert(TramitePatente record);

    List<TramitePatente> selectByUsuarioTramites(Integer idUsuariocaptura);

    List<SolicitudPreparacionDto> solicitudesByUsuarios(@Param("usuarios") List<Integer> usuarios, @Param("idTipoTramite") long idTipoTramite);
}
