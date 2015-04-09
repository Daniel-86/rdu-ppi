package mx.gob.impi.sagpat.persistence.mappers;



import mx.gob.impi.sagpat.persistence.model.SolicitudReivindicacionWord;
import org.apache.ibatis.annotations.Param;

public interface SolicitudReivindicacionWordMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("numExped") Integer numExped, @Param("secReivindicacion") Short secReivindicacion, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    SolicitudReivindicacionWord selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("numExped") Integer numExped, @Param("secReivindicacion") Short secReivindicacion, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(SolicitudReivindicacionWord record);
    
    int insert(SolicitudReivindicacionWord record);

    int updateByPrimaryKeyWithBLOBs(SolicitudReivindicacionWord record);

    int updateByPrimaryKey(SolicitudReivindicacionWord record);
}