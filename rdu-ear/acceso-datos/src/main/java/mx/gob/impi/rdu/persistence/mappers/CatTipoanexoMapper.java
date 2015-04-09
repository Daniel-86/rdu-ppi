package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.dto.AnexosViewDto;
import mx.gob.impi.rdu.persistence.model.CatTipoanexo;

public interface CatTipoanexoMapper {

    int deleteByPrimaryKey(Integer idTipoanexo);

    int insert(CatTipoanexo record);

    int insertSelective(CatTipoanexo record);

    CatTipoanexo selectByPrimaryKey(Integer idTipoanexo);

    int updateByPrimaryKeySelective(CatTipoanexo record);

    int updateByPrimaryKey(CatTipoanexo record);

    List<CatTipoanexo> selectDynamic(CatTipoanexo tipoAnexo);

    List<AnexosViewDto> selectDynamicDTO(AnexosViewDto tipoAnexoDto);

    List<AnexosViewDto> selectDynamicDTOSimple(AnexosViewDto tipoAnexoDto);
    List<AnexosViewDto> selectDynamicDTOSimplePatente(AnexosViewDto tipoAnexoDto);
    List<AnexosViewDto> selectDynamicDTOPatentes(AnexosViewDto tipoAnexoDto);
    AnexosViewDto selectAnexoDynamic(AnexosViewDto tipoAnexoDto);
}
