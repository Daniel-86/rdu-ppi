package mx.gob.impi.sagpat.persistence.mappers;

import mx.gob.impi.sagpat.persistence.model.SolicitudPalabra;

import org.apache.ibatis.annotations.Param;

public interface SolicitudPalabraMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPalabra") String codPalabra, @Param("codUso") String codUso, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    SolicitudPalabra selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPalabra") String codPalabra, @Param("codUso") String codUso, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);
    int insert(SolicitudPalabra record);
    
    int updateByPrimaryKeySelective(SolicitudPalabra record);

    int updateByPrimaryKey(SolicitudPalabra record);
}