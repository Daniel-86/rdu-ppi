package mx.gob.impi.rdu.test.local;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mx.gob.impi.rdu.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import mx.gob.impi.rdu.persistence.model.*;

import mx.gob.impi.rdu.remote.impl.RduCatalogosBeanImpl;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import org.apache.log4j.Logger;

@ContextConfiguration(locations = {
    "/config/applicationContext-service-ejbRduCatalogos.xml",
    "classpath:config/applicationContext-persistence.xml"})
public class RduCatalogosServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    private Logger LOGGER = Logger.getLogger(this.getClass());

    public RduCatalogosServiceTest() {
    }
    @Qualifier("miRduCatalogosBeanImpl")
    @Autowired
    private RduCatalogosBeanImpl miRduCatalogosBeanImpl;

    public void setMiRduCatalogosBeanImpl(RduCatalogosBeanImpl miRduCatalogosBeanImpl) {
        this.miRduCatalogosBeanImpl = miRduCatalogosBeanImpl;
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testEntidadesFederativas() throws Exception {
        System.out.println("************ RduCatalogosServiceTest.testEntidadesFederativas()");

        List<EntidadFederativa> objEntidades = this.miRduCatalogosBeanImpl.ConsultarEntidadesFederativas();
        if (objEntidades != null) {

            for (Iterator iter = objEntidades.iterator(); iter.hasNext();) {
                EntidadFederativa element = (EntidadFederativa) iter.next();
                LOGGER.info("---------------------------> " + element.getNombre());
            }
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testCoordinacionesEstatales() throws Exception {
        System.out.println("************ RduCatalogosServiceTest.testCoordinacionesEstatales()");

        CoordinacionEstatal coorEstatal = new CoordinacionEstatal();
        coorEstatal.setIdEntidadFederativa(9);

        List<CoordinacionEstatal> objCoordinaciones = this.miRduCatalogosBeanImpl.ConsultarCoordinacionesEstatales(coorEstatal);
        if (objCoordinaciones != null) {

            for (Iterator iter = objCoordinaciones.iterator(); iter.hasNext();) {
                CoordinacionEstatal element = (CoordinacionEstatal) iter.next();
                LOGGER.info("---------------------------> " + element.getNombre());
            }
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testCatAnexos() throws Exception {
        System.out.println(">>>>>>>************ RduCatalogosServiceTest.testCatAnexos()");


        CatTipoanexo tipoAnexo = new CatTipoanexo();
        tipoAnexo.setIdSubtiposolicitud(1);


        List<CatTipoanexo> listAnexos = this.miRduCatalogosBeanImpl.consultarTiposAnexos(tipoAnexo);
        if (listAnexos != null) {

            for (Iterator iter = listAnexos.iterator(); iter.hasNext();) {
                CatTipoanexo element = (CatTipoanexo) iter.next();
                LOGGER.info("---------------------------> " + element.getDescripcion());
            }
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testCatAnexosDTO() throws Exception {
        System.out.println(">>>>>>>************ PRUEBAS RduCatalogosServiceTest.testCatAnexosDTO()");


        AnexosViewDto anexosViewDto = new AnexosViewDto();
        anexosViewDto.setIdSubtiposolicitud(1L);
        anexosViewDto.setIdArea(0);
        anexosViewDto.setIdTramite(0);//263
        anexosViewDto.setIdTipomarca(1);
        anexosViewDto.setReglasUso(6);
        anexosViewDto.setPrioridad(2);


        List<AnexosViewDto> listAnexosViewDto = this.miRduCatalogosBeanImpl.consultarTiposAnexosDTO(anexosViewDto);

        if (listAnexosViewDto != null) {

            for (Iterator iter = listAnexosViewDto.iterator(); iter.hasNext();) {
                AnexosViewDto element = (AnexosViewDto) iter.next();
                LOGGER.info("---------------------------> " + element.getDescripcion() + " tramite:" + element.getIdTramite()
                        + " ext:" + element.getExtension() + " cargado:" + element.getCargado()
                        + " orden:" + element.getOrden() + " marca:" + element.getIdTipomarca()
                        + " obligatorio:" + element.getObligatorio());
            }
        }


    }

    @Test(enabled = false)
    @Rollback(false)
    public void testCatAnexosDTOSimple() throws Exception {
        System.out.println(">>>>>>>************ PRUEBAS RduCatalogosServiceTest.testCatAnexosDTOSimple()>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");


        AnexosViewDto anexosViewDto = new AnexosViewDto();
        anexosViewDto.setIdSubtiposolicitud(1L);
        anexosViewDto.setIdArea(0);
        anexosViewDto.setIdTramite(0);//263
        anexosViewDto.setIdTipomarca(1);
        anexosViewDto.setReglasUso(6);
        anexosViewDto.setPrioridad(2);


        List<AnexosViewDto> listAnexosViewDto = this.miRduCatalogosBeanImpl.consultarTiposAnexosDTOSimple(anexosViewDto);

        if (listAnexosViewDto != null) {

            for (Iterator iter = listAnexosViewDto.iterator(); iter.hasNext();) {
                AnexosViewDto element = (AnexosViewDto) iter.next();
                LOGGER.info("---------------------------> " + element.getDescripcion() + " tramite:" + element.getIdTramite()
                        + " ext:" + element.getExtension() + " cargado:" + element.getCargado()
                        + " orden:" + element.getOrden() + " marca:" + element.getIdTipomarca()
                        + " obligatorio:" + element.getObligatorio());
            }
        }


    }

    @Test(enabled = false)
    @Rollback(false)
    public void testPaises() throws Exception {
        System.out.println("************ RduCatalogosServiceTest.testPaises()");
        List<Pais> paises = this.miRduCatalogosBeanImpl.consultarPaises(new Pais());
        if (paises != null) {
            for (Iterator iter = paises.iterator(); iter.hasNext();) {
                Pais element = (Pais) iter.next();
                LOGGER.info("---------------------------> " + element.getNacionalidad());
            }
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testTipoDocumento() throws Exception {
        System.out.println("************ RduCatalogosServiceTest.testTipoDocumento()");
        TipoDocumento tipodocumento = new TipoDocumento();

        tipodocumento.setIdArea(1);
        tipodocumento.setDescripcion("SOLICITUD DE FUSION");
        tipodocumento.setDescripcionCorta("FUSION");


        List<TipoDocumento> tipoDocu = this.miRduCatalogosBeanImpl.consultarTipoDocumento(tipodocumento);
        if (tipoDocu != null) {
            for (Iterator iter = tipoDocu.iterator(); iter.hasNext();) {
                TipoDocumento element = (TipoDocumento) iter.next();
                LOGGER.info("---------------------------> " + element.getDescripcion());
                LOGGER.info("---------------------------> " + element.getDescripcionCorta());
            }
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testConsultarTiposPerson() {
        System.out.println("------------------------>No. de tipos persona encontrados: " + this.miRduCatalogosBeanImpl.consultarTiposPersona(null).size());
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testDocumentoArticulo() throws Exception {
        System.out.println("************ RduCatalogosServiceTest.testDocumentoArticulo()");
        DocumentoArticulo documento = new DocumentoArticulo();

        documento.setIdArea(1);
        documento.setIdTipoDocumento(1);
        documento.setIdTipoSolicitud(1);
        documento.setArticuloPago("14");

        List<DocumentoArticulo> docu = this.miRduCatalogosBeanImpl.consultarDocumentoArticulo(documento);
        if (docu != null) {
            for (Iterator iter = docu.iterator(); iter.hasNext();) {
                DocumentoArticulo element = (DocumentoArticulo) iter.next();
                LOGGER.info("---------------------------> " + element.getArticuloPago());
            }
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testClasesTipoActual() throws Exception {
        System.out.println("************ RduCatalogosServiceTest.testClasesTipoActual()");


//        List<CatClase> oClases = this.miRduCatalogosBeanImpl.consultarClasesTipoActual();
//        if (oClases != null) {
//            for (Iterator iter = oClases.iterator(); iter.hasNext();) {
//                CatClase element = (CatClase) iter.next();
//                LOGGER.info("---> " + element.getIdclase() + " " + element.getDescripcion() + " " + element.getTipoClase());
//
//            }
//        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testProcuctoCriterio() throws Exception {
        System.out.println("************ RduCatalogosServiceTest.testProcuctoCriterio()");

//        CatProducto ctProducto = new CatProducto();
//        //ctProducto.setIdClase(1);
//        ctProducto.setIdProductoServicio(12922);
//        //ctProducto.setDescripcion("ACEITE");
//        List<CatProducto> olista = this.miRduCatalogosBeanImpl.consultarProductoCriterios(ctProducto);
//        if (olista != null) {
//            for (Iterator iter = olista.iterator(); iter.hasNext();) {
//                CatProducto element = (CatProducto) iter.next();
//                LOGGER.info("---> " + element.getIdProductoServicio() + " " + element.getDescripcion() + " " + element.getIdClase());
//
//            }
//        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void areaTipoSolicitud() throws Exception {
        System.out.println("************ RduCatalogosServiceTest.areaTipoSolicitud()");
        tipoSolicitudDto tipoSolicitudDto = new tipoSolicitudDto();
        tipoSolicitudDto tipoSolicitud = new tipoSolicitudDto();

        tipoSolicitudDto = this.miRduCatalogosBeanImpl.areaTipoSolicitud(9);
        tipoSolicitud = this.miRduCatalogosBeanImpl.areaTipoSolicitud(1);

        System.out.println("************ AREA==> "+tipoSolicitudDto.getIdArea());
        System.out.println("************ AREA==> "+tipoSolicitud.getIdArea());

    }

    @Test(enabled = false)
    @Rollback(false)
    public void testConsultarTipoNumero() throws Exception {
        System.out.println("************ RduMarcasServiceTest.testConsultarTipoNumero()");
        try {

//            List<CatTipoNumero> consultarTipoNumero = this.miRduCatalogosBeanImpl.getAllTipoNumero();
//            System.out.println("************ Registros en catálogo Tipo Numero: ");
//            if(consultarTipoNumero != null){
//                for(CatTipoNumero tipoNumero: consultarTipoNumero){
//                    System.out.println("******  Id: " + tipoNumero.getIdTiponumero() + ", Descripción:" + tipoNumero.getDescripcion());
//                }
//            }
        } catch (Exception e) {
            System.out.println("************ ERROR testConsultarTipoNumero "+e.getMessage());
        }
    }
}
