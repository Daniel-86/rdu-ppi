/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.test.local;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.aspectj.lang.annotation.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;


/**
 *
 * @author
 */


@ContextConfiguration(locations = {
    "/config/applicationContext-service-ejbRduCatalogos.xml",
    "classpath:config/applicationContext-persistence.xml"})
public class EjbFirmaHorariosTest extends AbstractTransactionalTestNGSpringContextTests{
    
    private Logger lger = Logger.getLogger(this.getClass());
    
     public EjbFirmaHorariosTest() {
    }
    
    @Qualifier("miRduCatalogosBeanImpl")
    @Autowired
    private RduCatalogosBeanImpl miRduCatalogosBeanImpl;

      
       public void setMiRduCatalogosBeanImpl(RduCatalogosBeanImpl miRduCatalogosBeanImpl) {
        this.miRduCatalogosBeanImpl = miRduCatalogosBeanImpl;
    }
   

    
     @Test(enabled = false)
    @Rollback(false)
     public void getHorario() throws Exception{
     
         Calendar cal = Calendar.getInstance();
         
          cal.set(Calendar.HOUR,0);
          cal.set(Calendar.HOUR_OF_DAY, 0);
          cal.set(Calendar.MINUTE, 0);
          cal.set(Calendar.SECOND, 0);
          cal.set(Calendar.MILLISECOND, 0);
          
          
         CatFirmahorarios horario = new CatFirmahorarios(5, cal.getTime());
         List<CatFirmahorarios> hr =     this.miRduCatalogosBeanImpl.getHorariosFirma(horario);
        Calendar hoy = GregorianCalendar.getInstance();
         hoy.get(GregorianCalendar.DAY_OF_WEEK);
         hoy.set(Calendar.SECOND, 0);
         hoy.set(Calendar.MILLISECOND, 0);
           
         CatFirmahorarios horarioValido = hr.get(0);
         
         
             String []from = horarioValido.getHorarioDesde().split(":");
             String []to = horarioValido.getHorarioHasta().split(":");
             
             Calendar apertura =(Calendar) hoy.clone();
             Calendar cierre =(Calendar) hoy.clone();
             
             apertura.set(Calendar.HOUR_OF_DAY, new Integer(from[0]));
             apertura.set(Calendar.MINUTE, new Integer(from[1]));
             
             cierre.set(Calendar.HOUR_OF_DAY, new Integer(to[0]));
             cierre.set(Calendar.MINUTE, new Integer(to[1]));
             
             if(hoy.after(apertura) && hoy.before(cierre)){
                 System.out.println("*******************************hora valida");
                 
             }else{
             
             System.out.println("++++++++++++++++ NO hora valida");
             }
                 
             
             
                  
     }

    @Test(enabled = false)
    @Rollback(false)
     public void insertarHorariosFirma(){
         CatFirmahorarios horario = new CatFirmahorarios();
        try {
            Date hoy = new Date();
            horario.setIdFirmahorarios(Long.valueOf("8"));
            horario.setDiaSemana(hoy.getDay());
            horario.setFechaInhabil(hoy);
            horario.setHorarioDesde("09:00");
            horario.setHorarioHasta("15:00");
            System.out.println("  *** insertarHorariosFirma ==> "+this.miRduCatalogosBeanImpl.insertarHorariosFirma(horario));
        }catch(Exception e){
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.copiarSolicitud: ", e);
        }
     }

    @Test(enabled = false)
    @Rollback(false)
     public void eliminarHorariosFirma(){
        try {
            System.out.println("  *** eliminarHorariosFirma ==> "+this.miRduCatalogosBeanImpl.eliminarHorariosFirma(9));
        }catch(Exception e){
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.copiarSolicitud: ", e);
        }
     }
    
    @Test(enabled = false)
    @Rollback(false)
     public void selectHorariosFirma(){
        List<CatFirmahorarios> listaHorarios;
        CatFirmahorarios horarios = new CatFirmahorarios();
        lger.info("------------------------selectHorariosFirma");
        try {
            listaHorarios=this.miRduCatalogosBeanImpl.selectHorariosFirma(horarios);

            for(CatFirmahorarios horario : listaHorarios){
                lger.info("** IdFirmahorarios==> "+horario.getIdFirmahorarios()+" ** DiaSemana==> "+horario.getFechaInhabil());
            }
        }catch(Exception e){
            lger.error("Ocurrio un error en el metodo EjbFirmaHorariosTest.selectHorariosFirma: ", e);
        }
     }

    @Test(enabled = false)
    @Rollback(false)
     public void selectHorariosFiltroFirma(){
        lger.info("** DENTRO DE selectHorariosFiltroFirma==> ");
         Calendar cal = Calendar.getInstance();

          cal.set(Calendar.HOUR,0);
          cal.set(Calendar.HOUR_OF_DAY, 0);
          cal.set(Calendar.MINUTE, 0);
          cal.set(Calendar.SECOND, 0);
          cal.set(Calendar.MILLISECOND, 0);
          cal.set(Calendar.YEAR, 2012);
          cal.set(Calendar.MONTH,5);
          cal.set(Calendar.DAY_OF_MONTH,25);
          
          lger.info("** cal.getTime()==> "+cal.getTime());

         CatFirmahorarios horario = new CatFirmahorarios();
         horario.setFechaInhabil(cal.getTime());

        try {
             List<CatFirmahorarios> listaHorarios =     this.miRduCatalogosBeanImpl.selectHorariosFirma(horario);
             lger.info("** listaHorarios.size==> "+listaHorarios.size());
            for(CatFirmahorarios hora : listaHorarios){
                lger.info("** IdFirmahorarios==> "+hora.getIdFirmahorarios()+"  ** DiaSemana==> "+hora.getFechaInhabil());
            }
        }catch(Exception e){
            lger.error("Ocurrio un error en el metodo EjbFirmaHorariosTest.selectHorariosFiltroFirma: ", e);
        }
     }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
