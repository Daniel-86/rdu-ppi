package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.SolicitudReivindicacion;
import org.apache.ibatis.annotations.Param;

public interface SolicitudReivindicacionMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("numExped") Integer numExped, @Param("secReivindicacion") Short secReivindicacion, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    SolicitudReivindicacion selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("numExped") Integer numExped, @Param("secReivindicacion") Short secReivindicacion, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(SolicitudReivindicacion record);
    
    int insert(SolicitudReivindicacion record);

    int updateByPrimaryKeyWithBLOBs(SolicitudReivindicacion record);

    int updateByPrimaryKey(SolicitudReivindicacion record);
}