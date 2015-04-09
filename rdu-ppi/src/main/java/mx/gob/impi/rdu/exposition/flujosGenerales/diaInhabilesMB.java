package mx.gob.impi.rdu.exposition.flujosGenerales;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import mx.gob.impi.rdu.persistence.model.CatFirmahorarios;
import mx.gob.impi.rdu.service.CatalogosViewServiceImpl;
import org.apache.log4j.Logger;

/**
 *
 * @author JBMM
 */
@ManagedBean(name = "diaInhabilesMB")
@ViewScoped
public class diaInhabilesMB implements Serializable {
   private Logger log = Logger.getLogger(this.getClass());
   private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.diasInhabiles";
   private final ResourceBundle bundleParametrosDias = ResourceBundle.getBundle(BUNDLE_PARAMETROS);
   private String msgAvisoAct = "";

   private Date fecha=null;
   private String horaInicio="";
   private String horaFin="";
   private int idFirmahorarios;
   private boolean verErrores;
   private List<String> errores;
   private List<CatFirmahorarios> listDiasInhabiles;
   private CatFirmahorarios listHrsFirma = new CatFirmahorarios();

   @ManagedProperty(value="#{catalogosViewService}")
   private CatalogosViewServiceImpl catalogosViewService;

   public List<CatFirmahorarios> getListDiasInhabiles() {
        return listDiasInhabiles;
    }

   public void setListDiasInhabiles(List<CatFirmahorarios> listDiasInhabiles) {
        this.listDiasInhabiles = listDiasInhabiles;
    }

   public Date getFecha() {
        return fecha;
    }

   public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

   public String getHoraFin() {
        return horaFin;
    }

   public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

   public String getHoraInicio() {
        return horaInicio;
    }

   public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

   public boolean isVerErrores() {
        return verErrores;
    }

   public int getIdFirmahorarios() {
        return idFirmahorarios;
    }

   public void setIdFirmahorarios(int idFirmahorarios) {
        this.idFirmahorarios = idFirmahorarios;
    }

   public void setVerErrores(boolean verErrores) {
        this.verErrores = verErrores;
    }

   public List<String> getErrores() {
        return errores;
    }

   public void setErrores(List<String> errores) {
        this.errores = errores;
    }

   public void setCatalogosViewService(CatalogosViewServiceImpl catalogosViewService) {
       this.catalogosViewService = catalogosViewService;
   }

   @PostConstruct
   public void init() {
       listDiasInhabiles=this.catalogosViewService.selectHorarios(listHrsFirma);
       horaInicio=bundleParametrosDias.getString("diasInabiles.horaInicio.default");
       horaFin=bundleParametrosDias.getString("diasInabiles.horaFin.default");
    }

   public void guardar(){
       log.info("  **  DENTRO DE diaInhabilesMB.guardar  ==>");
       String validacion ="";

       validacion = validate(fecha, horaInicio, horaFin);
       
       if (validacion.length() != 0) {
           convierteListaErrores(validacion);
           verErrores = true;
       }else {
           CatFirmahorarios horario = new CatFirmahorarios();
           SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
           String fechaFormato=formatoDeFecha.format(fecha);
           Calendar c = Calendar.getInstance();
           c.setTime(fecha);
           String temp[]=fechaFormato.split("/");
           String st=temp[0];
           for(int i=1;i<temp.length;i++)
               st=st+" + "+temp[i];

           int anio = Integer.parseInt(temp[2]);
           int mes  = Integer.parseInt(temp[1])-1;
           int dia  = Integer.parseInt(temp[0]);

           Calendar cal = Calendar.getInstance();

           cal.set(Calendar.HOUR,0);
           cal.set(Calendar.HOUR_OF_DAY, 0);
           cal.set(Calendar.MINUTE, 0);
           cal.set(Calendar.SECOND, 0);
           cal.set(Calendar.MILLISECOND, 0);
           cal.set(Calendar.YEAR, anio);
           cal.set(Calendar.MONTH,mes);
           cal.set(Calendar.DAY_OF_MONTH,dia);

           log.info("** cal.getTime()==> "+cal.getTime());
           log.info("** fecha.getDay()==> "+fecha.getDay());
           log.info("** cal.getTime()==> "+horaInicio);
           log.info("** horaFin==> "+horaFin);

           horario.setFechaInhabil(cal.getTime());
           horario.setDiaSemana(c.get(Calendar.DAY_OF_WEEK));
           horario.setHorarioDesde(horaInicio);
           horario.setHorarioHasta(horaFin);

           try {
                List<CatFirmahorarios> listaHorarios =  this.catalogosViewService.selectHorarios(horario);

                if(listaHorarios.size()>0){
                    validacion=bundleParametrosDias.getString("diasInabiles.error.repetida") + "||";
                    convierteListaErrores(validacion);
                    verErrores = true;
                } else{
                    msgAvisoAct = bundleParametrosDias.getString("diasInabiles.guardada");
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msgAvisoAct, msgAvisoAct);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    fecha=null;
                    this.catalogosViewService.insertarHorariosFirma(horario);
                    verErrores = false;
                }

               listDiasInhabiles=this.catalogosViewService.selectHorarios(listHrsFirma);
           } catch (Exception e) {
                log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
           }
       }
   }

   public void eliminar(ActionEvent actionEvent){
        String msgAviso="";
        int resultadoEliminados=1;

        log.info(" ** DENTRO ELIMINAR this.fecha==> "+this.fecha);
        log.info(" ** DENTRO ELIMINAR this.idFirmahorarios==> "+this.idFirmahorarios);

        if(null!=this.fecha){

            resultadoEliminados=this.catalogosViewService.deleteHorarios(this.idFirmahorarios);
            listDiasInhabiles=this.catalogosViewService.selectHorarios(listHrsFirma);
            this.fecha=null;
            if (resultadoEliminados>0){
                msgAviso=bundleParametrosDias.getString("diasInabiles.eliminado");
            }
        }
        else{
            msgAviso="Seleccione una solicitud para eliminarla";
        }

        msgAvisoAct = bundleParametrosDias.getString("diasInabiles.eliminado");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msgAvisoAct, msgAvisoAct);
        FacesContext.getCurrentInstance().addMessage(null, message);
        verErrores = false;
    }


   public String validate(Date fecha, String hraInicio, String hraFin){
        String error="";
        Date d = new Date();

        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaFormato=new Date(formatoDeFecha.format(fecha));
        Date dFormato=new Date(formatoDeFecha.format(d));

        log.info(" ** FECHA FORMATEDA:  "+fechaFormato);
        log.info(" ** FECHA ACTUAL FORMATEDA:  "+dFormato);

        log.info(" ** FECHA 1:  "+fecha.compareTo(d));
        log.info(" ** FECHA 2.1:  "+dFormato.compareTo(fechaFormato));

        if(fecha == null)
            error += bundleParametrosDias.getString("diasInabiles.diaVacio") + "||";
        else if(fecha.compareTo(d)<0 && dFormato.compareTo(fechaFormato)!=0)
            error += bundleParametrosDias.getString("diasInabiles.error.fechaAnterior") + "||";
        if(hraInicio.equals(""))
            horaInicio=bundleParametrosDias.getString("diasInabiles.horaInicio.default");
        else{
            if(compararHoras(hraInicio, hraFin))
                error += bundleParametrosDias.getString("diasInabiles.error.horaFin") + "||";
        }
        if(hraFin.equals(""))
            horaFin=bundleParametrosDias.getString("diasInabiles.horaFin.default");

        return error;
    }

   public boolean compararHoras(String inicio, String fin) {
        boolean comHra=false;
        inicio = inicio.replaceAll(":", "");
        fin = fin.replaceAll(":", "");
        String zero = bundleParametrosDias.getString("diasInabiles.hora.zero");
        if(inicio.equals(zero)  & fin.equals(zero)  )
            return false;        
        if(Integer.parseInt(fin) <= Integer.parseInt(inicio))
            comHra=true;

        return comHra;
    }

   public void convierteListaErrores(String error) {
        errores = new ArrayList<String>();
        if (error != null && error.length() != 0) {
            StringTokenizer st = new StringTokenizer(error, "||");
            while (st.hasMoreTokens()) {
                errores.add(st.nextToken());
            }
        }
    }

}