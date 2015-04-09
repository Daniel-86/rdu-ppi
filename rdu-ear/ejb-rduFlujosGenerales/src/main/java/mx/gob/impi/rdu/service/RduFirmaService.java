package mx.gob.impi.rdu.service;

import java.util.Date;
import java.util.List;
import mx.gob.impi.ingresos.persistence.model.FepsRecibidos;
import mx.gob.impi.rdu.dto.TramiteDto;
import mx.gob.impi.rdu.persistence.model.Certificado;
import mx.gob.impi.rdu.persistence.model.Firma;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.persistence.model.TramitePromocionMarca;

public interface RduFirmaService {

    Long insertFirma(Firma firma);

    Firma loadFirma(Long idFirma);

    Integer updateFirma(Firma firma);

    public List<Certificado> loadAllActiveCerts(Integer idArea);

    int insertFirmaAdmin(Certificado firmaAdmin);

    Certificado selectFirmaAdmin(Certificado firmaAdmin);

    Certificado selectFirmaAdminByEstatus(Certificado estatus);

    int updateFirmaAdmin(Certificado firmaAdmin);
    public int insertFeps(TramiteDto prmTramite) throws Exception;

    public FepsRecibidos selectFepsByFolio(Long folio);

    public int copiarSolicitudEnSagpat(TramitePatente prmTramite, int paginas) throws Exception;

    public String validatePhrase(String tmp) throws Exception;

    public String[] generarFoliosSagpat(String expediente, Date today) throws Exception ;

    List<Firma> selectFirmas();

    int udapteFirmasEnvio(Firma firma);

    public int insertFepsSagpat(TramitePatente t) ;

    Integer updateSelectiveFirma(Firma firma);

    Firma obternerFirmaByTramite(Long idTramite);
    
    Firma obternerFirmaByExpediente(Long expediente, String expedienteSat);
}
