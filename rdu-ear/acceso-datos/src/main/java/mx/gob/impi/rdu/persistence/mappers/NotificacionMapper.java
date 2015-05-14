package mx.gob.impi.rdu.persistence.mappers;

import java.util.Date;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.Notificacion;
import org.apache.ibatis.annotations.Param;

public interface NotificacionMapper {

    int deleteByPrimaryKey(Integer idNotificacion);

    int insert(Notificacion notificacion);

    Notificacion selectByPrimaryKey(Integer idNotificacion);

    int updateByPrimaryKeySelective(Notificacion record);

    int updateByPrimaryKeyWithBLOBs(Notificacion record);

    int updateByPrimaryKey(Notificacion record);

    List<Notificacion> selectByIdUsuarioCarga(@Param("idUsuarioCarga") Integer idUsuarioCarga,@Param("idUsuarioFirma") Integer idUsuarioFirma); 
    List<Notificacion> selectByTitular(@Param("titular") String titular,@Param("idUsuarioFirma") Integer idUsuarioFirma); 
    
    List<Notificacion> selectByIdUsuarioFirma(Integer idUsuarioFirma);
    
    List<Notificacion> selectByIds(@Param("notificacionIds") String notificacionIds);    
    
    List<Notificacion> selectByDates(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin, 
            @Param("ultimaSemana") Integer ultimaSemana, @Param("ultimoMes") Integer ultimoMes,@Param("idUsuarioFirma") Integer idUsuarioFirma);
    
   List<Notificacion>   selecttest(@Param("notificacionIds") List<Integer> noficacionesIds);
   
   Notificacion selectByFolio(String folio);
   int   deleteByIds(@Param("notificacionIds") List<Integer> noficacionesIds);
    
}
