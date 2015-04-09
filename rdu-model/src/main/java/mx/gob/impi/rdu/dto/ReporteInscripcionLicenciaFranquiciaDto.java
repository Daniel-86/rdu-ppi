/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.dto;

import java.util.Iterator;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.CatSubtiposolicitud;
import mx.gob.impi.rdu.persistence.model.EntidadFederativa;
import mx.gob.impi.rdu.persistence.model.Pais;
import mx.gob.impi.rdu.persistence.model.TramitePersona;
import mx.gob.impi.rdu.persistence.model.TramitePromocionMarca;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.Util;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


/**
 *
 * @author marisol
 */
public class ReporteInscripcionLicenciaFranquiciaDto {

    private String tipoSolicitud="";
    private String subTipoSolicitud="";
  
    private String codigoBarras="";
    private String rutaFirmaImpi="";
    
    private String folio="";
    private String fecha="";
    private String solicitante="";
    //Licenciante
    private String licenciante="";
    private String calleLicenciante="";
    private String coloniaLicenciante="";
    private String poblacionLicenciante="";
    private String paisLicenciante="";
    private String estadoLicenciante="";
    private String cPostalLicenciante="";
    private String nacLicenciante="";
    private String telefonoLicenciante="";
    private String correoLicenciante="";
    private int esSolicitanteTitLicenciante=0;
     

    //Licenciatario
    private String licenciatario="";
    private String calleLicenciatario="";
    private String coloniaLicenciatario="";
    private String poblacionLicenciatario="";
    private String estadoLicenciatario="";
    private String cPostalLicenciatario="";
    private String telefonoLicenciatario="";
    private String correoLicenciatario="";
    private String nacLicenciatario="";
    private String paisLicenciatario="";
    private int esSolicitanteTitLicenciatario=0;
    //
     //Datos de notificacion
    private String calleNotificacion="";
    private String coloniaNotificacion="";
    private String poblacionNotificacion="";
    private String estadoNotificacion="";
    private String codigoPostal="";
    private String telefonoNotificacion="";
    private String correoNotificacion="";


    // Numeros
    private String titularRegistro="";
    private String tipoSolRegistro="";
    private String anoSolRegistro="";
    private String expedienteRegistro="";
    private String registro="";
    private String denominacion="";
    private String apoderado="";


    

    public ReporteInscripcionLicenciaFranquiciaDto(TramitePromocionMarca tramitePromocion, String rutaFirmaImpi, List<CatSubtiposolicitud> listaSubTiposSolicitud,List<Pais> paises,List<EntidadFederativa> entidadesFederativas,List<NumerosSigmarDTO> registros) {
        this.llenarDatos(tramitePromocion, rutaFirmaImpi,listaSubTiposSolicitud,paises,entidadesFederativas,registros);
    }

    public void llenarDatos(TramitePromocionMarca tramitePromocion, String pathFirmaImpi,List<CatSubtiposolicitud> listaSubTiposSolicitud,List<Pais> paises,List<EntidadFederativa> entidadesFederativas,List<NumerosSigmarDTO> registros){
        this.rutaFirmaImpi=pathFirmaImpi;
        this.setFolio(tramitePromocion.getFolio());
        this.setFecha(tramitePromocion.getFechaSysDate()==null? "":Util.formatearFecha(tramitePromocion.getFechaSysDate(), Util.FORMATODDMMYYYYHHMMSS)  );
        this.setCodigoBarras(tramitePromocion.getCodBarras());
        for (CatSubtiposolicitud temp : listaSubTiposSolicitud) {
            if (temp.getIdSubtiposolicitud().intValue() == tramitePromocion.getIdSubtiposolicitud()) {
                this.subTipoSolicitud = temp.getDescripcion();
                break;
            }
        }

        
                     
        if(!tramitePromocion.getListaTramitePersona().isEmpty()){
        //Extrae Personas
        for (Iterator iter = tramitePromocion.getListaTramitePersona().iterator(); iter.hasNext();) {
            TramitePersona oTramitePersona = (TramitePersona) iter.next();

       
              if (oTramitePersona.getIdClasePersona().compareTo(Constantes.CLS_PERSONA_lICENCIANTE_FRANQUICIANTE)==0) {

                if(oTramitePersona.getEsSolicitante()== 1){
                        this.esSolicitanteTitLicenciante = 1;
                }
                if(oTramitePersona.getPersona().getNombrecompleto() != null)
                   this.licenciante = oTramitePersona.getPersona().getNombrecompleto();
                if (null!=oTramitePersona.getPersona().getDomicilioObj()){
                    DomicilioDto recuperaDomicilioDto = Util.recuperaDomicilioDto(oTramitePersona.getPersona().getDomicilioObj());

                    this.calleLicenciante =recuperaDomicilioDto.getCalle();
                    this.coloniaLicenciante =recuperaDomicilioDto.getColonia();
                    this.poblacionLicenciante = recuperaDomicilioDto.getPoblacion();
                    this.cPostalLicenciante = recuperaDomicilioDto.getCodigopostal();
                    this.paisLicenciante = descripcionPais(paises,recuperaDomicilioDto.getIdPais().intValue());
                    if (recuperaDomicilioDto.getIdPais().longValue() == Constantes.ID_PAIS_MEXICO) {
                       this.estadoLicenciante  = descripcionEntidad(entidadesFederativas, Integer.parseInt(recuperaDomicilioDto.getIdEntidad()));
                    }
                    else {
                       this.estadoLicenciante = oTramitePersona.getPersona().getDomicilioObj().getIdEntidad();
                    }

//                    if(oTramitePersona.getPersona().getDomicilioObj().getCalle() != null)
//                       this.calleLicenciante = oTramitePersona.getPersona().getDomicilioObj().getCalle();
//                    if(oTramitePersona.getPersona().getDomicilioObj().getColonia() != null)
//                       this.coloniaLicenciante = oTramitePersona.getPersona().getDomicilioObj().getColonia();
//                    if(oTramitePersona.getPersona().getDomicilioObj().getPoblacion() != null)
//                       this.poblacionLicenciante = oTramitePersona.getPersona().getDomicilioObj().getPoblacion();
//                    if(oTramitePersona.getPersona().getDomicilioObj().getCodigopostal() != null)
//                       this.cPostalLicenciante = oTramitePersona.getPersona().getDomicilioObj().getCodigopostal();
//                    if( oTramitePersona.getPersona().getDomicilioObj().getIdPais().intValue() > 0)
//                        this.paisLicenciante = descripcionPais(paises, oTramitePersona.getPersona().getDomicilioObj().getIdPais().intValue());
//                    if(oTramitePersona.getPersona().getDomicilioObj().getIdEntidad() != null)
//                        this.estadoLicenciante = descripcionEntidad(entidadesFederativas, Integer.parseInt(oTramitePersona.getPersona().getDomicilioObj().getIdEntidad()));

               }else{
                    System.out.println("Error: <<<<<<<<<<<<<<<<<---Domicilio Licenciante Vacio");
               }
               if(oTramitePersona.getPersona().getNacionalidad().getNacionalidad() != null)
                    this.nacLicenciante = oTramitePersona.getPersona().getNacionalidad().getNacionalidad();
               if(oTramitePersona.getPersona().getDatosContacto()!=null) {
//                   if(oTramitePersona.getPersona().getDatosContacto().getTelefono() != null)
//                      this.telefonoLicenciante = oTramitePersona.getPersona().getDatosContacto().getTelefono();
//                   if(oTramitePersona.getPersona().getDatosContacto().getCorreoelectronico() != null)
//                      this.correoLicenciante = oTramitePersona.getPersona().getDatosContacto().getCorreoelectronico();
                     this.telefonoLicenciante = oTramitePersona.getPersona().getDatosContacto().getTelefono();
                     this.correoLicenciante = oTramitePersona.getPersona().getDatosContacto().getCorreoelectronico(); 
                }else{
                    System.out.println("Error: <<<<<<<<<<<<<<<<<---GetDatosContacto-----Licenciante VACIO");
                }

             } // *****************Fin del Licenciante



               if (oTramitePersona.getIdClasePersona().compareTo(Constantes.CLS_PERSONA_LICENCIATARIO_FRANQUICITARIO)==0) {
                    this.licenciatario = oTramitePersona.getPersona().getNombrecompleto();

                    if(oTramitePersona.getEsSolicitante()== 1){
                        this.esSolicitanteTitLicenciatario = 1;
                    }
                    if(oTramitePersona.getPersona().getNombrecompleto() != null)
                            this.licenciatario = oTramitePersona.getPersona().getNombrecompleto();
                    if (null!=oTramitePersona.getPersona().getDomicilioObj()){
                        DomicilioDto recuperaDomicilioLicenciatarioDto = Util.recuperaDomicilioDto(oTramitePersona.getPersona().getDomicilioObj());
                        
                        this.calleLicenciatario =recuperaDomicilioLicenciatarioDto.getCalle();
                        this.coloniaLicenciatario =recuperaDomicilioLicenciatarioDto.getColonia();
                        this.poblacionLicenciatario = recuperaDomicilioLicenciatarioDto.getPoblacion();
                        this.cPostalLicenciatario = recuperaDomicilioLicenciatarioDto.getCodigopostal();
                        this.paisLicenciatario = descripcionPais(paises,recuperaDomicilioLicenciatarioDto.getIdPais().intValue());
                        if (recuperaDomicilioLicenciatarioDto.getIdPais().longValue() == Constantes.ID_PAIS_MEXICO) {
                            this.estadoLicenciatario  = descripcionEntidad(entidadesFederativas, Integer.parseInt(recuperaDomicilioLicenciatarioDto.getIdEntidad()));
                        }
                        else {
                            this.estadoLicenciatario = oTramitePersona.getPersona().getDomicilioObj().getIdEntidad();
                        }
//                        if(oTramitePersona.getPersona().getDomicilioObj().getCalle() != null)
//                            this.calleLicenciatario = oTramitePersona.getPersona().getDomicilioObj().getCalle();
//                        if(oTramitePersona.getPersona().getDomicilioObj().getColonia() != null)
//                            this.coloniaLicenciatario = oTramitePersona.getPersona().getDomicilioObj().getColonia();
//                        if(oTramitePersona.getPersona().getDomicilioObj().getPoblacion() != null)
//                            this.poblacionLicenciatario = oTramitePersona.getPersona().getDomicilioObj().getPoblacion();
//                        if(oTramitePersona.getPersona().getDomicilioObj().getIdEntidad() != null)
//                            this.estadoLicenciatario = descripcionEntidad(entidadesFederativas, Integer.parseInt(oTramitePersona.getPersona().getDomicilioObj().getIdEntidad()));
//                        if(oTramitePersona.getPersona().getDomicilioObj().getCodigopostal() != null)
//                            this.cPostalLicenciatario = oTramitePersona.getPersona().getDomicilioObj().getCodigopostal();
//
//                        if(oTramitePersona.getPersona().getDomicilioObj().getIdPais().intValue() > 0)
//                            this.paisLicenciatario = descripcionPais(paises, oTramitePersona.getPersona().getDomicilioObj().getIdPais().intValue());
//
                    }else{
                        System.out.println("Error: <<<<<<<<<<<<<<<<<---Domicilio Licenciatario Vacio");
                    }
                    if(oTramitePersona.getPersona().getNacionalidad().getNacionalidad() != null)
                            this.nacLicenciatario = oTramitePersona.getPersona().getNacionalidad().getNacionalidad();
                    if(oTramitePersona.getPersona().getDatosContacto()!=null) {
//                       if(oTramitePersona.getPersona().getDatosContacto().getTelefono() != null)
//                            this.telefonoLicenciatario = oTramitePersona.getPersona().getDatosContacto().getTelefono();
//                       if(oTramitePersona.getPersona().getDatosContacto().getCorreoelectronico() != null)
//                            this.correoLicenciatario   = oTramitePersona.getPersona().getDatosContacto().getCorreoelectronico();
                        this.telefonoLicenciatario = oTramitePersona.getPersona().getDatosContacto().getTelefono();
                        this.correoLicenciatario   = oTramitePersona.getPersona().getDatosContacto().getCorreoelectronico();  
                    }else{
                        System.out.println("Error: <<<<<<<<<<<<<<<<<---GetDatosContacto Licenciatario Vacio");
                    }
                              
            }//Fin de Licenciatario

           } // FIN ---> for (Iterator iter = tramitePromocion.getListaTramitePersona().iterator(); iter.hasNext();)
        }
        if (tramitePromocion.getNotificacion()!=null){

            this.calleNotificacion = tramitePromocion.getNotificacion().getDomicilio().getCalle();
            this.codigoPostal = tramitePromocion.getNotificacion().getDomicilio().getCodigopostal();
            this.coloniaNotificacion = tramitePromocion.getNotificacion().getDomicilio().getColonia();
            this.poblacionNotificacion = tramitePromocion.getNotificacion().getDomicilio().getPoblacion();
            this.estadoNotificacion = tramitePromocion.getNotificacion().getDomicilio().getIdEntidad();
            this.telefonoNotificacion = tramitePromocion.getNotificacion().getDatosContacto().getTelefono();
            this.correoNotificacion = tramitePromocion.getNotificacion().getDatosContacto().getCorreoelectronico();
                    

        }

                    //números sigmar
            if(tramitePromocion.getNumeros()!=null) {
                String tipo_solicitud = "";
                String ano_solicitud = "";
                String expeSolicitud = "";
                String registroSolicitud = "";
                String titular = "";
                String denomSolicitud = "";
                String apoderadoSolicitud = "";
                //Anexos
//                String tipo_solicitudAnexo = "";
//                String ano_solicitudAnexo = "";
//                String expeSolicitudAnexo = "";
//                String registroSolicitudAnexo = "";
//                String titularAnexo = "";
//                String denomSolicitudAnexo = "";
//                String apoderadoSolicitudAnexo = "";
                int i = 0;

                for(NumerosSigmarDTO numSolicitud : registros){
                    if(i<2){
                        tipo_solicitud+=registros.get(i).getTipo_solicitud() + " \n";
                        ano_solicitud+=registros.get(i).getAno_solicitud() + " \n";
                        expeSolicitud+=registros.get(i).getExpediente() + " \n";
                        registroSolicitud+=registros.get(i).getRegistro() + " \n";
                        titular+=registros.get(i).getTitular() + " \n";
                        denomSolicitud+=registros.get(i).getDenominacion() + " \n";
                        apoderadoSolicitud+=registros.get(i).getApoderado() + " \n";
                    }
//                    else{
//                        tipo_solicitudAnexo+=registros.get(i).getTipo_solicitud() + " \n";
//                        ano_solicitudAnexo+=registros.get(i).getAno_solicitud() + " \n";
//                        expeSolicitudAnexo+=registros.get(i).getExpediente() + " \n";
//                        registroSolicitudAnexo+=registros.get(i).getRegistro() + " \n";
//                        titularAnexo+=registros.get(i).getTitular() + " \n";
//                        denomSolicitudAnexo+=registros.get(i).getDenominacion() + " \n";
//                        apoderadoSolicitudAnexo+=registros.get(i).getApoderado() + " \n";
//                    }

                    i++;
                }

                this.titularRegistro = (titular.length() > 2 ? titular.substring(0, titular.length() - 2) : titular);
                this.tipoSolRegistro = (tipo_solicitud.length() > 2 ? tipo_solicitud.substring(0, tipo_solicitud.length() - 2) : tipo_solicitud);
                this.anoSolRegistro = (ano_solicitud.length() > 2 ? ano_solicitud.substring(0, ano_solicitud.length() - 2) : ano_solicitud);
                this.expedienteRegistro = (expeSolicitud.length() > 2 ? expeSolicitud.substring(0, expeSolicitud.length() - 2) : expeSolicitud);
                this.registro = (registroSolicitud.length() > 2 ? registroSolicitud.substring(0, registroSolicitud.length() - 2) : registroSolicitud);
                this.denominacion = (denomSolicitud.length() > 2 ? denomSolicitud.substring(0, denomSolicitud.length() - 2) : denomSolicitud);
                this.apoderado = (apoderadoSolicitud.length() > 2 ? apoderadoSolicitud.substring(0, apoderadoSolicitud.length() - 2) : apoderadoSolicitud);

//                this.setTitularRegistroAnexo(titularAnexo.length() > 2 ? titularAnexo.substring(0, titularAnexo.length() - 2) : titularAnexo);
//                this.setTipoSolRegistroAnexo(tipo_solicitudAnexo.length() > 2 ? tipo_solicitudAnexo.substring(0, tipo_solicitudAnexo.length() - 2) : tipo_solicitudAnexo);
//                this.setAnoSolRegistroAnexo(ano_solicitudAnexo.length() > 2 ? ano_solicitudAnexo.substring(0, ano_solicitudAnexo.length() - 2) : ano_solicitudAnexo);
//                this.setExpedienteRegistroAnexo(expeSolicitudAnexo.length() > 2 ? expeSolicitudAnexo.substring(0, expeSolicitudAnexo.length() - 2) : expeSolicitudAnexo);
//                this.setRegistroAnexo(registroSolicitudAnexo.length() > 2 ? registroSolicitudAnexo.substring(0, registroSolicitudAnexo.length() - 2) : registroSolicitudAnexo);
//                this.setDenominacionAnexo(denomSolicitudAnexo.length() > 2 ? denomSolicitudAnexo.substring(0, denomSolicitudAnexo.length() - 2) : denomSolicitudAnexo);
//                this.setApoderadoAnexo(apoderadoSolicitudAnexo.length() > 2 ? apoderadoSolicitudAnexo.substring(0, apoderadoSolicitudAnexo.length() - 2) : apoderadoSolicitudAnexo);

            }
    }

    public String descripcionPais(List<Pais> paises,int idPais){
        String DescPais = "";
        for (Pais tempPais : paises) {
            if ( tempPais.getIdPais().intValue() == idPais) {
                DescPais = tempPais.getNombre();
                break;
            }
        }
        return DescPais;
    }
   //private List<EntidadFederativa> entidadesFederativas
   public String descripcionEntidad(List<EntidadFederativa> entidadesFederativas,int idEntidad){
        String DescEntidad = "";
        for (EntidadFederativa tempEntidad : entidadesFederativas) {
            if ( tempEntidad.getIdEntidadFederativa().intValue() == idEntidad) {
                DescEntidad = tempEntidad.getNombre();
                break;
            }
        }
        return DescEntidad;
    }

      //Getters y Setters

   

    public String getAnoSolRegistro() {
        return anoSolRegistro;
    }

    public void setAnoSolRegistro(String anoSolRegistro) {
        this.anoSolRegistro = anoSolRegistro;
    }

    public String getApoderado() {
        return apoderado;
    }

    public void setApoderado(String apoderado) {
        this.apoderado = apoderado;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getExpedienteRegistro() {
        return expedienteRegistro;
    }

    public void setExpedienteRegistro(String expedienteRegistro) {
        this.expedienteRegistro = expedienteRegistro;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getTipoSolRegistro() {
        return tipoSolRegistro;
    }

    public void setTipoSolRegistro(String tipoSolRegistro) {
        this.tipoSolRegistro = tipoSolRegistro;
    }

    public String getTitularRegistro() {
        return titularRegistro;
    }

    public void setTitularRegistro(String titularRegistro) {
        this.titularRegistro = titularRegistro;
    }
   
    public int getEsSolicitanteTitLicenciante() {
        return esSolicitanteTitLicenciante;
    }

    public void setEsSolicitanteTitLicenciante(int esSolicitanteTitLicenciante) {
        this.esSolicitanteTitLicenciante = esSolicitanteTitLicenciante;
    }

    public int getEsSolicitanteTitLicenciatario() {
        return esSolicitanteTitLicenciatario;
    }

    public void setEsSolicitanteTitLicenciatario(int esSolicitanteTitLicenciatario) {
        this.esSolicitanteTitLicenciatario = esSolicitanteTitLicenciatario;
    }



    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getSubTipoSolicitud() {
        return subTipoSolicitud;
    }
   
    public void setSubTipoSolicitud(String subTipoSolicitud) {
        this.subTipoSolicitud = subTipoSolicitud;
    }

    public String getRutaFirmaImpi() {
        return rutaFirmaImpi;
    }

    public void setRutaFirmaImpi(String rutaFirmaImpi) {
        this.rutaFirmaImpi = rutaFirmaImpi;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLicenciante() {
        return licenciante;
    }

    public void setLicenciante(String licenciante) {
        this.licenciante = licenciante;
    }

    public String getCalleLicenciante() {
        return calleLicenciante;
    }

    public void setCalleLicenciante(String calleLicenciante) {
        this.calleLicenciante = calleLicenciante;
    }

 

    public String getColoniaLicenciante() {
        return coloniaLicenciante;
    }

    public void setColoniaLicenciante(String coloniaLicenciante) {
        this.coloniaLicenciante = coloniaLicenciante;
    }

    public String getPoblacionLicenciante() {
        return poblacionLicenciante;
    }

    public void setPoblacionLicenciante(String poblacionLicenciante) {
        this.poblacionLicenciante = poblacionLicenciante;
    }

    public String getEstadoLicenciante() {
        return estadoLicenciante;
    }

    public void setEstadoLicenciante(String estadoLicenciante) {
        this.estadoLicenciante = estadoLicenciante;
    }

    public String getcPostalLicenciante() {
        return cPostalLicenciante;
    }

    public void setcPostalLicenciante(String cPostalLicenciante) {
        this.cPostalLicenciante = cPostalLicenciante;
    }

    public String getTelefonoLicenciante() {
        return telefonoLicenciante;
    }

    public void setTelefonoLicenciante(String telefonoLicenciante) {
        this.telefonoLicenciante = telefonoLicenciante;
    }

    public String getCorreoLicenciante() {
        return correoLicenciante;
    }

    public void setCorreoLicenciante(String correoLicenciante) {
        this.correoLicenciante = correoLicenciante;
    }

    public String getLicenciatario() {
        return licenciatario;
    }

    public void setLicenciatario(String licenciatario) {
        this.licenciatario = licenciatario;
    }

    public String getCalleLicenciatario() {
        return calleLicenciatario;
    }

    public void setCalleLicenciatario(String calleLicenciatario) {
        this.calleLicenciatario = calleLicenciatario;
    }

    public String getColoniaLicenciatario() {
        return coloniaLicenciatario;
    }

    public void setColoniaLicenciatario(String coloniaLicenciatario) {
        this.coloniaLicenciatario = coloniaLicenciatario;
    }

    public String getPoblacionLicenciatario() {
        return poblacionLicenciatario;
    }

    public void setPoblacionLicenciatario(String poblacionLicenciatario) {
        this.poblacionLicenciatario = poblacionLicenciatario;
    }

    public String getEstadoLicenciatario() {
        return estadoLicenciatario;
    }

    public void setEstadoLicenciatario(String estadoLicenciatario) {
        this.estadoLicenciatario = estadoLicenciatario;
    }

    public String getcPostalLicenciatario() {
        return cPostalLicenciatario;
    }

    public void setcPostalLicenciatario(String cPostalLicenciatario) {
        this.cPostalLicenciatario = cPostalLicenciatario;
    }

    public String getTelefonoLicenciatario() {
        return telefonoLicenciatario;
    }

    public void setTelefonoLicenciatario(String telefonoLicenciatario) {
        this.telefonoLicenciatario = telefonoLicenciatario;
    }

    public String getCorreoLicenciatario() {
        return correoLicenciatario;
    }

    public void setCorreoLicenciatario(String correoLicenciatario) {
        this.correoLicenciatario = correoLicenciatario;
    }

    public String getCalleNotificacion() {
        return calleNotificacion;
    }

    public void setCalleNotificacion(String calleNotificacion) {
        this.calleNotificacion = calleNotificacion;
    }

    public String getColoniaNotificacion() {
        return coloniaNotificacion;
    }

    public void setColoniaNotificacion(String coloniaNotificacion) {
        this.coloniaNotificacion = coloniaNotificacion;
    }

    public String getPoblacionNotificacion() {
        return poblacionNotificacion;
    }

    public void setPoblacionNotificacion(String poblacionNotificacion) {
        this.poblacionNotificacion = poblacionNotificacion;
    }

    public String getEstadoNotificacion() {
        return estadoNotificacion;
    }

    public void setEstadoNotificacion(String estadoNotificacion) {
        this.estadoNotificacion = estadoNotificacion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefonoNotificacion() {
        return telefonoNotificacion;
    }

    public void setTelefonoNotificacion(String telefonoNotificacion) {
        this.telefonoNotificacion = telefonoNotificacion;
    }

    public String getCorreoNotificacion() {
        return correoNotificacion;
    }

    public void setCorreoNotificacion(String correoNotificacion) {
        this.correoNotificacion = correoNotificacion;
    }

    public String getPaisLicenciante() {
        return paisLicenciante;
    }

    public void setPaisLicenciante(String paisLicenciante) {
        this.paisLicenciante = paisLicenciante;
    }

    public String getNacLicenciante() {
        return nacLicenciante;
    }

    public void setNacLicenciante(String nacLicenciante) {
        this.nacLicenciante = nacLicenciante;
    }

    public String getNacLicenciatario() {
        return nacLicenciatario;
    }

    public void setNacLicenciatario(String nacLicenciatario) {
        this.nacLicenciatario = nacLicenciatario;
    }

    public String getPaisLicenciatario() {
        return paisLicenciatario;
    }

    public void setPaisLicenciatario(String paisLicenciatario) {
        this.paisLicenciatario = paisLicenciatario;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    

    

    

    }


