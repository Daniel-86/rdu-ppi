package mx.gob.impi.rdu.test.local;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.ApoderadoXTramitePatente;
import mx.gob.impi.rdu.persistence.model.Domicilio;
import mx.gob.impi.rdu.persistence.model.ImagenDibujo;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.PersonaNotXTramitePatente;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import mx.gob.impi.rdu.remote.impl.RduPatentesBeanImpl;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = {
    "/config/applicationContext-service-ejbRduPatentes.xml",
    "classpath:config/applicationContext-persistence.xml",
    "classpath:config/applicationContext-persistence-sigmar.xml",
    "classpath:config/applicationContext-persistence-pase.xml",
    "classpath:config/applicationContext-persistence-sagpat.xml",
    "classpath:config/applicationContext-persistence-ingresos.xml"})
public class PatentesNegocioServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    public PatentesNegocioServiceTest() {
    }
    @Qualifier("testRduPatentesBeanImpl")
    @Autowired
    private RduPatentesBeanImpl testRduPatentesBeanImpl;

    public void setTestRduPatentesBeanImpl(RduPatentesBeanImpl testRduPatentesBeanImpl) {
        this.testRduPatentesBeanImpl = testRduPatentesBeanImpl;
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testHolaMundo() throws Exception {
        System.out.println("************ " + this.testRduPatentesBeanImpl.holaMundo());

    }

    @Test(enabled = false)
    @Rollback(false)
    public void testConsultarPersonaNot() throws Exception {
        List<Persona> personas = this.testRduPatentesBeanImpl.obtenerPersonasNot(981L);
        for (Persona persona : personas) {
            System.out.println("Nombre:" + persona.getNombrecompleto());
        }

    }

    @Test(enabled = false)
    @Rollback(false)
    public void testEliminarApoderadosXTramite() throws Exception {
        System.out.println(">>>>>>>>>>>>>>>testEliminarApoderadosXTramite()>>>>>>>>>>>>>>><<<<<<");
        System.out.println("Filas eliminadas: " + this.testRduPatentesBeanImpl.eliminarApoderadosXTramite(981L));
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testInsertarApoderadoXTramite() throws Exception {
        System.out.println(">>>>>>>>>>>>>>>testInsertarApoderadoXTramite()>>>>>>>>>>>>>>><<<<<<");
        ApoderadoXTramitePatente apoderadoXTramitePatente = new ApoderadoXTramitePatente();
        apoderadoXTramitePatente.setIdApoderado(129L);
        apoderadoXTramitePatente.setIdTramitePatente(981L);
        System.out.println("Filas insertadas: " + testRduPatentesBeanImpl.insertarApoderadoXTramite(apoderadoXTramitePatente));

    }

    @Test(enabled = false)
    @Rollback(false)
    public void testInsertarPersonaXTramite() throws Exception {
        System.out.println(">>>>>>>>>>>>>>>testInsertarPersonaXTramite()>>>>>>>>>>>>>>><<<<<<");
        PersonaNotXTramitePatente personaNotXTramitePatente = new PersonaNotXTramitePatente();
        personaNotXTramitePatente.setIdPersonaNot(129L);
        personaNotXTramitePatente.setIdTramitePatente(981L);
        System.out.println("Filas insertadas: " + testRduPatentesBeanImpl.insertarPersonaNotXTramitePatente(personaNotXTramitePatente));
    }

//    @Test(enabled = false)
//    @Rollback(false)
//    public void selectTramitePatente() throws Exception {
//        System.out.println("************ PatentesNegocioServiceTest.selectTramitePatente()");
//        TramitePatente listTramite  = new TramitePatente();
//        try {
//            listTramite = this.testRduPatentesBeanImpl.selectTramite(Long.valueOf("99999999"));
//
//                System.out.println("************ IdTramitePatente==> "+listTramite.getIdTramitePatente());
//
//        } catch (Exception e) {
//            System.out.println("************ ERROR selectTramitePatente "+e.getMessage());
//        }
//
//    }
    @Test(enabled = false)
    @Rollback(false)
    public void testEliminarPersonaXTramite() throws Exception {
        System.out.println(">>>>>>>>>>>>>>>testEliminarPersonaXTramite()>>>>>>>>>>>>>>><<<<<<");
        System.out.println("Filas eliminadas: " + this.testRduPatentesBeanImpl.eliminarPersonaNotXTramitePatente(981L));
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testInsertarTramitePatente() throws Exception {
        System.out.println(">>>>>>>>>>>>>>>testInsertarTramitePatente>>>>>>>>>>>>>>><<<<<<");
        TramitePatente tramitePatente = new TramitePatente();
        Persona persona = new Persona();
        Domicilio domicilio = new Domicilio();
        tramitePatente.setIdTramitePatente(942L);
        tramitePatente.setIdSubtipoSolicitud(13L);
        tramitePatente.setInvencion("prueba transactional cesar");
        tramitePatente.setIdEstatus(1L);
        tramitePatente.setFechaCaptura(new Date());
        tramitePatente.setIdUsuarioCaptura(6889L);
        tramitePatente.setIdEstatus(1L);
        tramitePatente.setIndActivo(new Short("1"));
        persona.setNombrecompleto("cesar castañeda reyes");
        persona.setIdNacionalidad(938L);
        persona.setIdTipopersona(new Short("1"));
        domicilio.setIdPais(938L);
        persona.setDomicilioObj(domicilio);
        domicilio.setIdEntidad("21");
        domicilio.setCalle("GUADALUPE VICTORIA S/N");
        domicilio.setColonia("JILOTEPEC");
        domicilio.setPoblacion("ZACAPOAXTLA");
        domicilio.setCodigopostal("73690");
        //tramitePatente.setApoderados(new ArrayList<Persona>());
        //tramitePatente.getApoderados().add(persona);
        //tramitePatente.setPersonasNot(new ArrayList<Persona>());
        //tramitePatente.getPersonasNot().add(persona);
        try {
            boolean numrow = testRduPatentesBeanImpl.updateTramitePatenteByPrimaryKey(tramitePatente);
            System.out.println("Tramite actualizado: " + numrow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testObtenerTramitePatenteById() {
        System.out.println(">>>>>>>>>>>>>>>testObtenerTramitePatenteById()>>>>>>>>>>>>>>><<<<<<");
        try {
            TramitePatente tramitePatente = testRduPatentesBeanImpl.obtenerTramitePatenteById(1122L);
            System.out.println("No de apoderados: " + tramitePatente.getApoderados().size());
            System.out.println("No de personasNot: " + tramitePatente.getPersonasNot().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testActualizarTramitePatente() throws Exception {
        System.out.println(">>>>>>>>>>>>>>>testActualizarTramitePatente>>>>>>>>>>>>>>><<<<<<");
        TramitePatente tramitePatente = new TramitePatente();
        tramitePatente.setIdTramitePatente(Long.valueOf("99999999"));
        tramitePatente.setIdEstatus(Long.valueOf("2"));
        try {
            int numrow = testRduPatentesBeanImpl.updateTramitePatente(tramitePatente);
            System.out.println("Tramite actualizado: " + numrow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testSelectImagenDibujo() throws Exception {
        System.out.println(">>>>>>>>>>>>>>>testSelectImagenDibujo()>>>>>>>>>>>>>>><<<<<<");
        ImagenDibujo imagenDibujo = new ImagenDibujo(null, 1293L, null, null, null);
        imagenDibujo.setArchivo(null);
        imagenDibujo.setIdTramite(1214L);
        imagenDibujo.setOrden(1L);
        try {
            TramitePatente tramitePatente = testRduPatentesBeanImpl.obtenerTramitePatenteById(1293L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
