package mx.gob.impi.rdu.test.local;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import mx.gob.impi.pase.persistence.model.SisAlerta;
import mx.gob.impi.rdu.persistence.model.BitacoraErrores;
import mx.gob.impi.rdu.persistence.model.Firma;
import mx.gob.impi.rdu.persistence.model.Tramite;
import mx.gob.impi.rdu.remote.impl.RduFlujosGeneralesBeanImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 *
 * @author JBMM
 */

@ContextConfiguration(locations = {
    "/config/applicationContext-service-ejbFlujosGenerales.xml",
    "classpath:config/applicationContext-persistence-sigmar.xml",
    "classpath:config/applicationContext-persistence-pase.xml",
    "classpath:config/applicationContext-persistence.xml",
    "classpath:config/applicationContext-persistence-sagpat.xml",
    "classpath:config/applicationContext-persistence-ingresos.xml"})
public class comunEjbTest extends AbstractTransactionalTestNGSpringContextTests {
    @Qualifier("rduFlujosGeneralesBean")
    @Autowired
    private RduFlujosGeneralesBeanImpl rduFlujosGeneralesBean;

    public void setRduFlujosGeneralesBean(RduFlujosGeneralesBeanImpl rduFlujosGeneralesBean) {
        this.rduFlujosGeneralesBean = rduFlujosGeneralesBean;
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testInsertAlertas() throws Exception
    {
        System.out.println("************ RduAnexosServiceTest.testInsertAlertas()");

        Date date = new Date();
        
        SisAlerta alerta = new SisAlerta();
        alerta.setIdAlerta(Short.valueOf("666"));
        alerta.setIdPromovente(BigDecimal.valueOf(6889));
        alerta.setIdMotivoAlerta(Short.valueOf("1"));
        alerta.setNumeroCertificado("NUM3432432");
        alerta.setFechaExpira(date);

        SisAlerta alert = new SisAlerta();
        alert.setIdAlerta(Short.valueOf("665"));
        alert.setIdPromovente(BigDecimal.valueOf(83));
        alert.setIdMotivoAlerta(Short.valueOf("1"));
        alert.setNumeroCertificado("NUM34");
        alert.setFechaExpira(date);

        try {
            System.out.println("Numero de alertas Insertadas ALERTA: "+this.rduFlujosGeneralesBean.insertarAlertas(alerta));
            System.out.println("Numero de alertas Insertadas alert: "+this.rduFlujosGeneralesBean.insertarAlertas(alert));
        } catch (Exception e) {
            System.out.println("************ ERROR testInsertAlertas "+e.getMessage());
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testSelectAlertas() throws Exception
    {
        System.out.println("************ RduAnexosServiceTest.testSelectAlertas()");

        try {
            SisAlerta alerta = new SisAlerta();

            alerta.setIdPromovente(BigDecimal.valueOf(20419));

            List<SisAlerta> listAlertas = this.rduFlujosGeneralesBean.selectAlertas(alerta);

            for(Iterator iter=listAlertas.iterator(); iter.hasNext();){
                SisAlerta alertas = (SisAlerta) iter.next();
                System.out.println("************ IdAlerta "+alertas.getIdAlerta());
                System.out.println("************ IdPromovente "+alertas.getIdPromovente());
                System.out.println("************ IdMotivoAlerta "+alertas.getIdMotivoAlerta());
                System.out.println("************ Fecha "+alertas.getFecha());
            }
        } catch (Exception e) {
            System.out.println("************ ERROR testSelectAlertas "+e.getMessage());
        }
    }

   @Test(enabled = false)
    @Rollback(false)
    public void eliminartramites() throws Exception{
        System.out.println("************ RduAnexosServiceTest.eliminartramites()");
        try {
            List<Tramite> param = new ArrayList();

            param = this.rduFlujosGeneralesBean.selectTramite();

            System.out.println("************ TOTAL:  "+param.get(0).getIdTramite());
            System.out.println("************ TOTAL:  "+param.get(1).getIdTramite());
            System.out.println("************ TOTAL:  "+param.get(2).getIdTramite());
        } catch (Exception e) {
            System.out.println("************ ERROR eliminartramites "+e.getMessage());
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void insertBitacoraErrores() throws Exception{
        System.out.println("************ comunEjbTest.insertBitacoraErrores()");
        try {
            BitacoraErrores bitacora = new BitacoraErrores();
            bitacora.setDescripcion("TEST DE ERROR");
                System.out.println("************ insertados "+this.rduFlujosGeneralesBean.insertBitacoraErrores(bitacora));

        } catch (Exception e) {
            System.out.println("************ ERROR insertBitacoraErrores "+e.getMessage());
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void selectBitacoraErrores() throws Exception{
        System.out.println("************ comunEjbTest.selectBitacoraErrores()");
        try {
            List<BitacoraErrores> listBitacora = new ArrayList<BitacoraErrores>();
            listBitacora= this.rduFlujosGeneralesBean.selectBitacoraErrores();

            for(BitacoraErrores bitacora : listBitacora){
                System.out.println("************IdError==> "+bitacora.getIdError());
                System.out.println("************Descripcion==> "+bitacora.getDescripcion());
                System.out.println("************FechaError==> "+bitacora.getFechaError());
            }

        } catch (Exception e) {
            System.out.println("************ ERROR selectBitacoraErrores "+e.getMessage());
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void EliminaBitacoraErrores() throws Exception {
        System.out.println("************ comunEjbTest.EliminaBitacoraErrores");
        try {
            System.out.println("************ ELIMINADOS "+this.rduFlujosGeneralesBean.eliminarBitacoraErrores(Short.valueOf("24")));
        } catch (Exception e) {
            System.out.println("************ ERROR EliminaBitacoraErrores "+e.getMessage());
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void selectTramite() throws Exception{
        System.out.println("************ comunEjbTest.selectTramite()");
        try {
//            List<Tramite> listBitacora = new ArrayList<Tramite>();
//            listBitacora= this.rduFlujosGeneralesBean.selectTramite();
//
//            for(Tramite bitacora : listBitacora){
//                System.out.println("************IdError==> "+bitacora.getEstatus());
//                System.out.println("************Descripcion==> "+bitacora.getFechacaptura());
//            }

//            this.rduFlujosGeneralesBean.eliminarSolicitud(120, 1);

        } catch (Exception e) {
            System.out.println("************ ERROR selectTramite "+e.getMessage());
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void selectFirma() throws Exception{
        System.out.println("************ comunEjbTest.selectFirma()");
        try {
            List<Firma> firma = new ArrayList<Firma>();
            firma = this.rduFlujosGeneralesBean.selectFirmas();

            for (Firma firmas : firma){
                System.out.println("************ IdTramite==>  "+firmas.getIdTramite()+"   IdFirma==>  "+firmas.getIdFirma());
            }

        } catch (Exception e) {
            System.out.println("************ ERROR selectFirma "+e.getMessage());
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testEnvioUCM(){
        System.out.println("************ comunEjbTest.testEnvioUCM()");
        try {
            Firma firma = new Firma();
            firma.setIdFirma(Long.valueOf("5"));
            firma.setEnvio(Long.valueOf("1"));

            System.out.println("************ ACTUALIZADOS==> "+this.rduFlujosGeneralesBean.udapteFirmasEnvio(firma));
        } catch (Exception e) {
            System.out.println("************ ERROR testEnvioUCM "+e.getMessage());
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testJobEliminarAcuse(){
        System.out.println("************ comunEjbTest.testJobEliminarAcuse()");
        try {
            System.out.println("************ RESPUESTA==>  "+this.rduFlujosGeneralesBean.jobeliminarAcuse());
        } catch (Exception e) {
            System.out.println("************ ERROR testJobEliminarAcuse "+e.getMessage());
        }
    }

    @Test(enabled = false)
    @Rollback(false)
    public void testSecuencia(){
        System.out.println("************ comunEjbTest.testSecuencia()");
        try {
            System.out.println("************ RESPUESTA==>  "+this.rduFlujosGeneralesBean.selectsecuencia());
        } catch (Exception e) {
            System.out.println("************ ERROR testSecuencia "+e.getMessage());
        }
    }
}