package mx.gob.impi.ingresos.persistence.mappers;

import mx.gob.impi.ingresos.persistence.model.FepsRecibidos;



public interface FepsRecibidosMapper {
    int insert(FepsRecibidos record);
    
    FepsRecibidos selectFepsByFolio(Long folioFeps);
     
    int insertSelective(FepsRecibidos record);
}