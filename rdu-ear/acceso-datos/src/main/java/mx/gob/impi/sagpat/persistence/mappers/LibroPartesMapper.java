package mx.gob.impi.sagpat.persistence.mappers;



import java.util.Date;
import mx.gob.impi.sagpat.persistence.model.LibroPartes;
import org.apache.ibatis.annotations.Param;

public interface LibroPartesMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("fecProceso") Date fecProceso, @Param("tipLibro") String tipLibro);

    LibroPartes selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("fecProceso") Date fecProceso, @Param("tipLibro") String tipLibro);

    int updateByPrimaryKeySelective(LibroPartes record);
    
    Long obtenerNumUltimoFolio(@Param("codOficina") String codOficina, @Param("indAbierto") String indAbierto, @Param("tipLibro") String tipLibro);
    
    Date obtenerFechaProceso(@Param("codOficina") String codOficina, @Param("indAbierto") String indAbierto, @Param("tipLibro") String tipLibro);
    
    Date obtenerFechaProcesoLibroCerrado(@Param("codOficina") String codOficina, @Param("indAbierto") String indAbierto, @Param("tipLibro") String tipLibro);
    
    int reabrirLibroPartes(LibroPartes record);
    
    String verificaPrimerNumeroFolio(LibroPartes record);
    
    Date cerrarLibroSelectFecha(LibroPartes record);
    
    
    int cerrarLibroPartes(LibroPartes record);
    
    String generarNumFolio(@Param("codOficina") String codOficina, @Param("indAbierto") String indAbierto, @Param("tipLibro") String tipLibro);
    
    int actualizarNumFolio(LibroPartes record);
    
    int generaFolioGeneralEntrada(LibroPartes record);
    
    int actualizarNumUltimoIfNulo(LibroPartes record);
    
    String generarFolioGeneralEntrada(LibroPartes record);

    int updateByPrimaryKey(LibroPartes record);
}