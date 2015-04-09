package mx.gob.impi.rdu.test.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import mx.gob.impi.rdu.persistence.model.*;
import org.apache.log4j.Logger;
import mx.gob.impi.rdu.remote.impl.RduUsuariosBeanImpl;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import java.util.Iterator;
import java.util.List;
import mx.gob.impi.rdu.dto.PersonaDto;
import mx.gob.impi.rdu.service.impl.RduUsuariosServiceImpl;

@ContextConfiguration(locations = {
    "/config/applicationContext-service-ejbRduUsuarios.xml",
    "classpath:config/applicationContext-persistence.xml"})
public class RduUsuarioServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    private Logger LOGGER = Logger.getLogger(this.getClass());

    public RduUsuarioServiceTest() {
    }
    @Qualifier("rduUsuariosBeanImpl")
    @Autowired
    private RduUsuariosBeanImpl rduUsuariosBeanImplTest;

    public void setRduUsuariosBeanImplTest(RduUsuariosBeanImpl rduUsuariosBeanImplTest) {
        this.rduUsuariosBeanImplTest = rduUsuariosBeanImplTest;
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testLogin() throws Exception {
        System.out.println("************ RduUsuariosBeanImplTest.testLogin()");
        String vsUsuario = "isaac.palacios";
        Usuario objUsuario = this.rduUsuariosBeanImplTest.verificarUsuarioByLogin(vsUsuario);
        if (objUsuario != null) {
            LOGGER.info("************ begin ......");
            LOGGER.info("**************** clave::" + objUsuario.getUsuarioSeguridad().getClave());
            if (objUsuario.getPerfiles() != null) {
                LOGGER.info("**************** Perfiles::");
                List<Perfil> perfilesTest = objUsuario.getPerfiles();
                for (Iterator iter = perfilesTest.iterator(); iter.hasNext();) {
                    Perfil element = (Perfil) iter.next();
                    LOGGER.info("---------------------------> " + element.getNombre());

                }

            }
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testGetSolicitantesPorTramite() throws Exception {
        System.out.println("************ RduUsuariosBeanImplTest.testGetSolicitantesPorTramite()");

        Persona all = this.rduUsuariosBeanImplTest.getSolicitanteById(20);

        if (all != null) {
            System.out.println("************* SIZE: " + all.getNombrecompleto());

        }

    }

    @Test(enabled = false)
    @Rollback(false)
    public void testByfiltro() throws Exception {
        System.out.println("************ RduUsuariosBeanImplTest.Byfiltro()");
        CoordinacionEstatal coorEstatal = new CoordinacionEstatal();
        coorEstatal.setIdCoordinacion("500");

        List<Usuario> lstUsuarios = this.rduUsuariosBeanImplTest.selectByFiltro(coorEstatal);
        for (Iterator iter = lstUsuarios.iterator(); iter.hasNext();) {
            Usuario element = (Usuario) iter.next();
            LOGGER.info("----------> " + element.getNombre() + " " + element.getApellidoPaterno());
            LOGGER.info("----------> " + element.getUsuarioSeguridad().getClave() + " " + element.getUsuarioSeguridad().getUsuarioStr());

            //for (Iterator iter2 = element.getTelefonos().iterator(); iter2.hasNext();) {
            // Telefono tel =(Telefono) iter2.next();
            // LOGGER.info("----------> " + tel.getNumero()+" " + tel.getTipoTelefono());
            //}

            LOGGER.info("********************************************************************");
            System.out.println("************Entrando RDUUsuarioServiceTest.testGetSolicitanteById***********");
            LOGGER.info("************Entrando RDUUsuarioServiceTest.testGetSolicitanteById***********");
        }

    }

    @Test(enabled = false)
    @Rollback(false)
    public void testGetSolicitanteById() throws Exception {
        System.out.println("************Entrando RDUUsuarioServiceTest.testGetSolicitanteById***********");

//        printSolicitante(rduUsuariosBeanImplTest.getSolicitanteById(4));
/*
        System.out.println("Insertando solicitante...........");
         */
        Persona solSearch = new Persona();
        solSearch.setIdDomiclio(1L);
        solSearch.setIdSolicitante(1L);
        for (Persona temp : rduUsuariosBeanImplTest.selectDynamic(solSearch)) {
            printSolicitante(temp);
        }

        /*
        System.out.println("Numero de usuarios Insertados: "+ rduUsuariosBeanImplTest.insertSolicitante(solInsert));
         * */

        /*




        System.out.println("******************Eliminado*************++");
        System.out.println("Rows deleted: "+rduUsuariosBeanImplTest.deleteSolicitante(1));

         *
         */

        /*
        System.out.println("Actualizando Solicitante...........");
        Solicitante solUpdate=new Solicitante();
        solUpdate.setIdDomiclio(1);
        
        System.out.println("Numero de usuarios Actualizados: "+ rduUsuariosBeanImplTest.updateSolicitante(solUpdate));
         *
         */



    }

    public void printSolicitante(Persona solicitante) {
        System.out.println("Nombre:" + solicitante.getNombrecompleto());
    }
}
