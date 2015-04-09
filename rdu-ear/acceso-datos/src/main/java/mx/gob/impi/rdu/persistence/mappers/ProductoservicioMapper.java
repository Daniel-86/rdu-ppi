package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.Productoservicio;
import org.apache.ibatis.annotations.Param;

public interface ProductoservicioMapper {
    int deleteByTipoClase( Long idTipoclaseseleccionada);

    int insert(Productoservicio record);

    int insertSelective(Productoservicio record);
    List<Productoservicio> getProductosByTipoClaseSeleccionada(Long idTipoClaseSel);
}