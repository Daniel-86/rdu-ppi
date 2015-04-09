package mx.gob.impi.sagpat.persistence.mappers;




import mx.gob.impi.sagpat.persistence.model.SolicitudDibujos;
import org.apache.ibatis.annotations.Param;

public interface SolicitudDibujosMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("numExped") Integer numExped, @Param("secDibujos") Integer secDibujos, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    SolicitudDibujos selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("numExped") Integer numExped, @Param("secDibujos") Integer secDibujos, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(SolicitudDibujos record);
    
    int insert(SolicitudDibujos record);

    int updateByPrimaryKeyWithBLOBs(SolicitudDibujos record);

    int updateByPrimaryKey(SolicitudDibujos record);
}