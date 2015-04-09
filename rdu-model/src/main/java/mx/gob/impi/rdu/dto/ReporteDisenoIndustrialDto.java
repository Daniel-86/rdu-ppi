/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.ImagenDibujo;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.Prioridad;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.Util;
  
  

/**
 *
 * @author cesar
 */
public class ReporteDisenoIndustrialDto {

    private int tipoSolicitud;
    private int subTipoSolicitud;
    private String expediente;
    private String folio;
    private String fecha;
    private String nombresSol;
    private String nacionalidadSol;
    private String calleColoniaSol;
    private String poblacionEstadoSol;
    private String telefonoSol;
    private String faxSol;
    private String correoSol;
    private String nombresInv;
    private String nacionalidadInv;
    private String calleColoniaInv;
    private String poblacionEstadoInv;
    private String telefonoInv;
    private String faxInv;
    private String correoInv;
    private String nombresApod;
    private String rgpApod;
    private String nacionalidadApod;
    private String calleColoniaApod;
    private String poblacionEstadoAPod;
    private String telefonoApod;
    private String faxApod;
    private String correoApod;
    private String invencion;
    private String fechaDivulgacion;
    private String expedienteDivisional;
    private String figuraJurDivisional;
    private String fechaPresDivicional;
    private String nombresNot;
    private String rutaFirmaImpi;
    private String folioPago;
    private String montoPago;
    private String codigoBarras;
    private String nombresPaisPrior;
    private String fechaPresPaisPrior;
    private String numSeriePaisPrior;
    private String nombresSolAnexo;
    private String nombresApodAnexo;
    private String nombresPersNotAnexo;
    private String nombresInvAnexo;
    private String paisPriorAnexo;
    private String cadenaImpi;
    private String cadenaSolicitante;
    private String selloImpi;
    private String selloSolicitante;
    private String certificadora;
    private String nombreFirmante;
    private FirmaDto firma;
    private Short inventorSol;
    private String observaciones;
    private String cadenaAnexos;
    private String nombreSolicitante;
    private Integer descuento;
    private String fechaPriorAnexo;
    private String expedientePriorAnexo;
    private String nombreDocAnexo;
    private String tamanoAnexo;
    private String numHojasAnexo;
    private String observacionesAnexo;
    private Long totalHojasAnexo;
    private String totalHojasAnexoStr;
    private String observacionAnexo;
    private String idSolicitud;
    private List<ReportesDto> listaTmpReportes;
    private String fechaEstatusActual;
    private String emailSolicitante;
    private String emailApoderado;
    private String fechaDeCreacion;
    private String extTelefonoSol;
    private String extTelefonoInv;
    private TramitePatente tramitePat;

    public ReporteDisenoIndustrialDto(TramitePatente tramitePatente, String rutaFirmaImpi) {
        firma = new FirmaDto();

        this.paisPriorAnexo = "";
        this.fechaPriorAnexo = "";

        this.expedientePriorAnexo = "";
        this.nombresInvAnexo = "";
        this.nombresPersNotAnexo = "";
        this.nombresSolAnexo = "";
        this.nombresApodAnexo = "";
        this.cadenaAnexos = "";
        this.folio = "";
        this.setTramitePat(tramitePatente);
        this.convertirTramite(tramitePatente, rutaFirmaImpi);

    }

    public ReporteDisenoIndustrialDto(TramitePatente tramitePatente, String rutaFirmaImpi, List<ReportesDto> listaTmp) {
        firma = new FirmaDto();

        this.paisPriorAnexo = "";
        this.fechaPriorAnexo = "";

        this.expedientePriorAnexo = "";
        this.nombresInvAnexo = "";
        this.nombresPersNotAnexo = "";
        this.nombresSolAnexo = "";
        this.nombresApodAnexo = "";
        this.cadenaAnexos = "";
        this.folio = "";
        this.listaTmpReportes = listaTmp;
        this.setTramitePat(tramitePatente);
        this.convertirTramite(tramitePatente, rutaFirmaImpi);

    }

    public ReporteDisenoIndustrialDto(TramitePatente tramitePatente, String rutaFirmaImpi, FirmaDto dto) {
        this.firma = dto;
        this.paisPriorAnexo = "";
        this.fechaPriorAnexo = "";

        this.expedientePriorAnexo = "";
        this.nombresInvAnexo = "";
        this.nombresPersNotAnexo = "";
        this.nombresSolAnexo = "";
        this.nombresApodAnexo = "";
        this.cadenaAnexos = "";
        this.folio = "";
        this.setTramitePat(tramitePatente);
        this.convertirTramite(tramitePatente, rutaFirmaImpi);
    }

    public ReporteDisenoIndustrialDto(TramitePatente tramitePatente, String rutaFirmaImpi, FirmaDto dto, List<ReportesDto> listaTmp) {
        this.firma = dto;
        this.paisPriorAnexo = "";
        this.fechaPriorAnexo = "";

        this.expedientePriorAnexo = "";
        this.nombresInvAnexo = "";
        this.nombresPersNotAnexo = "";
        this.nombresSolAnexo = "";
        this.nombresApodAnexo = "";
        this.cadenaAnexos = "";
        this.folio = "";
        this.listaTmpReportes = listaTmp;
        this.setTramitePat(tramitePatente);
        this.convertirTramite(tramitePatente, rutaFirmaImpi);
    }

    public void convertirTramite(TramitePatente tramitePatente, String pathFirmaImpi) {

        this.idSolicitud = tramitePatente.getIdTramitePatente().toString();
        this.descuento = firma.getDescuento();

        this.setCadenaImpi(firma.getCadenaImpi());
        this.setCadenaSolicitante(firma.getCadenaSolicitante());
        this.setSelloImpi(firma.getFirmaImpi());
        this.setSelloSolicitante(firma.getFirmaSolicitante());
        this.setCertificadora(firma.getCertificadora() == null ? "" : firma.getCertificadora());
        this.setExpediente(firma.getExpedienteId() == null ? "" : firma.getExpedienteId());
        this.setFolio(firma.getFolioId() == null ? "" : firma.getFolioId());
        //this.setDescuento(tipoSolicitud);
        this.setFechaEstatusActual(Util.formatearFecha(tramitePatente.getFechaEstatusActual(), Constantes.FORMATO_FECHA));

        this.setNombreFirmante(firma.getNombreFirmante() == null ? "" : firma.getNombreFirmante());
        //this.setFolio(tramitePatente.getFoliosSeries() == null ? "" : tramitePatente.getFoliosSeries().length < 3 ? "" : tramitePatente.getFoliosSeries()[2]);
        //this.setExpediente(tramitePatente.getFoliosSeries() == null ? "" : tramitePatente.getFoliosSeries().length < 4 ? "" : tramitePatente.getFoliosSeries()[3]);
        this.setNombreSolicitante(firma.getNombreFirmante());
        this.setFecha(firma.getFechaRegistro() == null ? "" : Util.formatearFecha(firma.getFechaRegistro(), Util.FORMATODDMMYYYYHHMMSS));
        this.setFechaDeCreacion(firma.getFechaRegistro() == null ? "" : Util.formatearFecha(firma.getFechaRegistro(), Util.FORMATODDMMYYYYHHMMSS));
        Persona apoderadoPrincipal = null;
        Persona solicitantePrincipal = null;
        String nombres[] = {"", ""};
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.setObservaciones(tramitePatente.getObservaciones() == null ? "" : tramitePatente.getObservaciones());
        if (!tramitePatente.getApoderados().isEmpty()) {
            apoderadoPrincipal = obtenerPrincipal(tramitePatente.getApoderados());
        }
        if (!tramitePatente.getSolicitantes().isEmpty()) {
            solicitantePrincipal = obtenerPrincipal(tramitePatente.getSolicitantes());
        }

        this.setRutaFirmaImpi(pathFirmaImpi);
        if (tramitePatente.getTipoSol().getIdTiposolicitud() != null) {
            this.setTipoSolicitud(tramitePatente.getTipoSol().getIdTiposolicitud().intValue());
        }
        if (tramitePatente.getSubTipoSol().getIdSubtiposolicitud() != null) {
            this.setSubTipoSolicitud(tramitePatente.getSubTipoSol().getIdSubtiposolicitud().intValue());
        }
        //condicion para Modelo de utilidad solo mostrar el titulo de la invencion
        if (tramitePatente.getTipoSol().getIdTiposolicitud().intValue()== Constantes.TIPO_SOL_MODELO_UTILIDAD){
            this.setInvencion(tramitePatente.getInvencion().toUpperCase());
        }else{
            this.setInvencion(tramitePatente.getSubTipoSol().getDescripcion().toUpperCase()+" "+tramitePatente.getInvencion().toUpperCase());
        }
        

        if (tramitePatente.getFechaDivPrevia() != null) {
            this.setFechaDivulgacion(dateFormat.format(tramitePatente.getFechaDivPrevia()));
        }

        if (tramitePatente.getExpDivisional() != null) {
            this.setExpedienteDivisional(tramitePatente.getExpDivisional());
        }

        this.setFiguraJurDivisional("************");

        if (tramitePatente.getFechaPresentacionExp() != null) {
            this.setFechaPresDivicional(dateFormat.format(tramitePatente.getFechaPresentacionExp()));
        }

        String domicilioPob[] = new String[]{"", ""};
        if (solicitantePrincipal != null) {

            nombres = concatenarNomNacDom(tramitePatente.getSolicitantes(), solicitantePrincipal.getIdSolicitante(), Constantes.CLS_PERSONA_SOLICITANTE_PATENTES);
            this.setNombresSol(nombres[0]);
            this.setNombresSolAnexo(nombres[1]);
            this.setInventorSol(solicitantePrincipal.getInventor());
            this.setNacionalidadSol(solicitantePrincipal.getNacionalidad().getNacionalidad());

            domicilioPob = recuperarDomicilioPoblacion(solicitantePrincipal);
            this.setCalleColoniaSol(domicilioPob[0]);
            this.setPoblacionEstadoSol(domicilioPob[1]);

            this.setTelefonoSol(solicitantePrincipal.getDatosContacto().getTelefono());
            this.setExtTelefonoSol(solicitantePrincipal.getDatosContacto().getTelefonoExt() == null ? "" : 
                    solicitantePrincipal.getDatosContacto().getTelefonoExt());
            this.setFaxSol(solicitantePrincipal.getDatosContacto().getFax());
            this.setCorreoSol(solicitantePrincipal.getDatosContacto().getCorreoelectronico());
            this.setEmailSolicitante(solicitantePrincipal.getDatosContacto().getCorreoelectronico() == null ? "" : 
                    solicitantePrincipal.getDatosContacto().getCorreoelectronico());
        } else if (!tramitePatente.getSolicitantes().isEmpty()) {
//            nombres = concatenarNombresInventores(tramitePatente.getSolicitantes());
            nombres = concatenarNomNacDom(tramitePatente.getSolicitantes(), 0L, Constantes.CLS_PERSONA_SOLICITANTE_PATENTES);
            this.setNombresSol(nombres[0]);
            this.setNombresSolAnexo(nombres[1]);
            this.setInventorSol(tramitePatente.getSolicitantes().get(0).getInventor());
            this.setNacionalidadSol(tramitePatente.getSolicitantes().get(0).getNacionalidad().getNacionalidad());

            domicilioPob = recuperarDomicilioPoblacion(solicitantePrincipal);
            this.setCalleColoniaSol(domicilioPob[0]);
            this.setPoblacionEstadoSol(domicilioPob[1]);

            this.setTelefonoSol(tramitePatente.getSolicitantes().get(0).getDatosContacto().getTelefono());
            this.setExtTelefonoSol(tramitePatente.getSolicitantes().get(0).getDatosContacto().getTelefonoExt() == null ? "" :
                    tramitePatente.getSolicitantes().get(0).getDatosContacto().getTelefonoExt());
            this.setFaxSol(tramitePatente.getSolicitantes().get(0).getDatosContacto().getFax());
            this.setCorreoSol(tramitePatente.getSolicitantes().get(0).getDatosContacto().getCorreoelectronico());
            this.setEmailSolicitante(tramitePatente.getSolicitantes().get(0).getDatosContacto().getCorreoelectronico() == null ? "" : 
                    tramitePatente.getSolicitantes().get(0).getDatosContacto().getCorreoelectronico());
        }

        if (!tramitePatente.getInventores().isEmpty()) {
//            nombres = concatenarNombresInventores(tramitePatente.getInventores());
            nombres = concatenarNomNacDom(tramitePatente.getInventores(), 0L, Constantes.CLS_PERSONA_INVENTOR);
            this.setNombresInv(nombres[0]);
            this.setNombresInvAnexo(nombres[1]);
            this.setNacionalidadInv(tramitePatente.getInventores().get(0).getNacionalidad().getNacionalidad());
            String numExt = tramitePatente.getInventores().get(0).getDomicilioObj().getNumExt()!= null ? tramitePatente.getInventores().get(0).getDomicilioObj().getNumExt() + ", "  : "";;
            String numInt = tramitePatente.getInventores().get(0).getDomicilioObj().getNumInt()!= null ? tramitePatente.getInventores().get(0).getDomicilioObj().getNumInt() + ", "  : "";;
            if (tramitePatente.getInventores().get(0).getDomicilioObj().getPais().getIdPais().longValue() == Constantes.ID_PAIS_MEXICO) {
                this.setCalleColoniaInv(tramitePatente.getInventores().get(0).getDomicilioObj().getCalle() + ", " + numExt + ", " + numInt +  tramitePatente.getInventores().get(0).getDomicilioObj().getColonia() + " C.P. " + tramitePatente.getInventores().get(0).getDomicilioObj().getCodigopostal());
                this.setPoblacionEstadoInv(tramitePatente.getInventores().get(0).getDomicilioObj().getPoblacion() + ", " + tramitePatente.getInventores().get(0).getDomicilioObj().getEntidad().getNombre() + ", " + tramitePatente.getInventores().get(0).getDomicilioObj().getPais().getNombre());
            } else {
                this.setCalleColoniaInv(concatenarTresCadenas(tramitePatente.getInventores().get(0).getDomicilioObj().getCalle(), numExt, numInt, tramitePatente.getInventores().get(0).getDomicilioObj().getColonia(), 
                        "C.P. " + tramitePatente.getInventores().get(0).getDomicilioObj().getCodigopostal()));
                this.setPoblacionEstadoInv(concatenarCadenasConComa(tramitePatente.getInventores().get(0).getDomicilioObj().getPoblacion(), tramitePatente.getInventores().get(0).getDomicilioObj().getIdEntidad(), tramitePatente.getInventores().get(0).getDomicilioObj().getPais().getNombre()));
            }
            this.setTelefonoInv(tramitePatente.getInventores().get(0).getDatosContacto().getTelefono());
            this.setFaxInv(tramitePatente.getInventores().get(0).getDatosContacto().getFax());
            this.setCorreoInv(tramitePatente.getInventores().get(0).getDatosContacto().getCorreoelectronico());
            this.setExtTelefonoInv(tramitePatente.getInventores().get(0).getDatosContacto().getTelefonoExt() == null ? "" :
                    tramitePatente.getInventores().get(0).getDatosContacto().getTelefonoExt());
        }

        if (apoderadoPrincipal != null) {
//            nombres = concatenarNombres(tramitePatente.getApoderados(), apoderadoPrincipal.getIdSolicitante());
            nombres = concatenarNomNacDom(tramitePatente.getApoderados(), apoderadoPrincipal.getIdSolicitante(), Constantes.CLS_PERSONA_APODERADO);
            this.setNombresAPod(nombres[0]);
            this.setNombresApodAnexo(nombres[1]);
            this.setRgpApod(apoderadoPrincipal.getRgp());
            if (apoderadoPrincipal.getDomicilioObj()!=null){
            //this.setNacionalidadApod(apoderadoPrincipal.getNacionalidad().getNacionalidad());
                if (apoderadoPrincipal.getDomicilioObj().getPais().getIdPais().longValue() == Constantes.ID_PAIS_MEXICO) {
                    String numExt = apoderadoPrincipal.getDomicilioObj().getNumExt()!= null ? apoderadoPrincipal.getDomicilioObj().getNumExt( )+ ", "  : "";;
                    String numInt = apoderadoPrincipal.getDomicilioObj().getNumInt()!= null ? apoderadoPrincipal.getDomicilioObj().getNumInt() + ", "  : "";;
                    this.setCalleColoniaApod(apoderadoPrincipal.getDomicilioObj().getCalle() + ", " + numExt + numInt + apoderadoPrincipal.getDomicilioObj().getColonia() + " C.P. " + apoderadoPrincipal.getDomicilioObj().getCodigopostal());
                    this.setPoblacionEstadoAPod(apoderadoPrincipal.getDomicilioObj().getPoblacion() + ", " + apoderadoPrincipal.getDomicilioObj().getEntidad().getNombre() + ", " + apoderadoPrincipal.getDomicilioObj().getPais().getNombre());
                } else {
                    this.setCalleColoniaApod(apoderadoPrincipal.getDomicilioObj().getCalle() + ", " + apoderadoPrincipal.getDomicilioObj().getColonia() + " C.P. " + apoderadoPrincipal.getDomicilioObj().getCodigopostal());
                    this.setPoblacionEstadoAPod(apoderadoPrincipal.getDomicilioObj().getPoblacion() + ", " + apoderadoPrincipal.getDomicilioObj().getIdEntidad() + ", " + apoderadoPrincipal.getDomicilioObj().getPais().getNombre());
                }
                this.setTelefonoApod(apoderadoPrincipal.getDatosContacto().getTelefono());
                this.setFaxApod(apoderadoPrincipal.getDatosContacto().getFax());
                this.setCorreoApod(apoderadoPrincipal.getDatosContacto().getCorreoelectronico());
                this.setEmailApoderado(apoderadoPrincipal.getDatosContacto().getCorreoelectronico());
            }
        } else if (!tramitePatente.getApoderados().isEmpty()) {
//            nombres = concatenarNombresInventores(tramitePatente.getApoderados());
            nombres = concatenarNomNacDom(tramitePatente.getApoderados(), 0L, Constantes.CLS_PERSONA_APODERADO);
            this.setNombresAPod(nombres[0]);
            this.setNombresApodAnexo(nombres[1]);
            this.setRgpApod(tramitePatente.getApoderados().get(0).getRgp());
            //this.setNacionalidadApod(apoderadoPrincipal.getNacionalidad().getNacionalidad());
            if (tramitePatente.getApoderados().get(0).getDomicilioObj().getPais().getIdPais().longValue() == Constantes.ID_PAIS_MEXICO) {
                this.setCalleColoniaApod(tramitePatente.getApoderados().get(0).getDomicilioObj().getCalle() + ", " + tramitePatente.getApoderados().get(0).getDomicilioObj().getColonia() + " C.P. " + tramitePatente.getApoderados().get(0).getDomicilioObj().getCodigopostal());
                this.setPoblacionEstadoAPod(tramitePatente.getApoderados().get(0).getDomicilioObj().getPoblacion() + ", " + tramitePatente.getApoderados().get(0).getDomicilioObj().getEntidad().getNombre() + ", " + tramitePatente.getApoderados().get(0).getDomicilioObj().getPais().getNombre());
            } else {
                this.setCalleColoniaApod(tramitePatente.getApoderados().get(0).getDomicilioObj().getCalle() + ", " + tramitePatente.getApoderados().get(0).getDomicilioObj().getColonia() + " C.P. " + tramitePatente.getApoderados().get(0).getDomicilioObj().getCodigopostal());
                this.setPoblacionEstadoAPod(tramitePatente.getApoderados().get(0).getDomicilioObj().getPoblacion() + ", " + tramitePatente.getApoderados().get(0).getDomicilioObj().getIdEntidad() + ", " + tramitePatente.getApoderados().get(0).getDomicilioObj().getPais().getNombre());
            }
            this.setTelefonoApod(tramitePatente.getApoderados().get(0).getDatosContacto().getTelefono());
            this.setFaxApod(tramitePatente.getApoderados().get(0).getDatosContacto().getFax());
            this.setCorreoApod(tramitePatente.getApoderados().get(0).getDatosContacto().getCorreoelectronico());
            this.setEmailApoderado(tramitePatente.getApoderados().get(0).getDatosContacto().getCorreoelectronico());
        }

        if (!tramitePatente.getPersonasNot().isEmpty()) {
            this.setNombresNot(concatenarNombres(tramitePatente.getPersonasNot()));
        }

        if (!tramitePatente.getPrioridades().isEmpty()) {
            String paisesPrioridad = "";
            String fechaPresPrioridad = "";
            String noSeriePrioridad = "";
            int i = 0;
            for (Prioridad prioridad : tramitePatente.getPrioridades()) {
                if (i <= 2) {
                    paisesPrioridad += prioridad.getNombrePais() + " \n";
                    fechaPresPrioridad += dateFormat.format(prioridad.getFechaPresentacionExt()) + " \n";
                    noSeriePrioridad += prioridad.getNumeroExpediente() + " \n";
                } else {

                    //paisPriorAnexo += "Pais: " + prioridad.getNombrePais() + "\t Fecha de Presentación: " + prioridad.getFechaPresentacion() + "\tNumero expediente: " + prioridad.getNumeroExpediente() + " \n";
                    paisPriorAnexo += prioridad.getNombrePais() + "\n";
                    fechaPriorAnexo += dateFormat.format(prioridad.getFechaPresentacionExt()) + " \n";
                    expedientePriorAnexo += prioridad.getNumeroExpediente() + " \n";

                }
                i++;
            }
            this.setNombresPaisPrior(paisesPrioridad.length() > 2 ? paisesPrioridad.substring(0, paisesPrioridad.length() - 2) : paisesPrioridad);
            this.setFechaPresPaisPrior(fechaPresPrioridad.length() > 2 ? fechaPresPrioridad.substring(0, fechaPresPrioridad.length() - 2) : fechaPresPrioridad);
            this.setNumSeriePaisPrior(noSeriePrioridad.length() > 2 ? noSeriePrioridad.substring(0, noSeriePrioridad.length() - 2) : noSeriePrioridad);
        }
         if (tramitePatente.getAnexos() != null) {
            this.formarAnexos(tramitePatente.getAnexos(),tramitePatente.getImagenes(), tramitePatente.getLstPrio());
        }
    }
    
    public String concatenarTresCadenas(String cadena1, String cadena2, String cadena3, String cadena4, String cadena5) {
        String result = "";     
        
        result = cadena1 != null ? cadena1 + ", "  : "";
        result += cadena2 != null ? cadena2 + ", " : "";
        result += cadena3 != null ? cadena3 + ", " : "";
        result += cadena4 != null ? cadena4 + ", " : "";
        result += cadena5 != null ? cadena5 + ", " : "";
        
        return result;
    }

    public String concatenarCadenasConComa(String cadena1, String cadena2, String cadena3) {
        String result = "";
        if ((cadena1 != null ? cadena1.length() > 0 : false)
                && (cadena2 != null ? cadena2.length() > 0 : false)
                && (cadena3 != null ? cadena3.length() > 0 : false)) {
            result = cadena1 + ", " + cadena2 + ", " + cadena3;
        } else if ((cadena1 != null ? cadena1.length() > 0 : false)
                && (cadena2 != null ? cadena2.length() > 0 : false)) {
            result = cadena1 + ", " + cadena2;
        } else if ((cadena1 != null ? cadena1.length() > 0 : false)
                && (cadena3 != null ? cadena3.length() > 0 : false)) {
            result = cadena1 + ", " + cadena3;
        } else if ((cadena2 != null ? cadena2.length() > 0 : false)
                && (cadena3 != null ? cadena3.length() > 0 : false)) {
            result = cadena2 + ", " + cadena3;
        } else if (cadena1 != null ? cadena1.length() > 0 : false) {
            result = cadena1;
        } else if (cadena2 != null ? cadena2.length() > 0 : false) {
            result = cadena2;
        } else if (cadena3 != null ? cadena3.length() > 0 : false) {
            result = cadena3;
        }
        return result;
    }

    public void formarAnexos(List<Anexos> anexos,List<ImagenDibujo> figuras, List<Anexos> Prioridades) {
        Util util = new Util();
        List<FormarAnexosDto> formarAnexosList = new ArrayList<FormarAnexosDto>();
        List<FormarAnexosDto> listaAnexos = new ArrayList<FormarAnexosDto>();
        FormarAnexosDto anexosDto = null;
        FormarAnexosDto anexosDtoTrad = null;
        this.totalHojasAnexo = new Long(0);
        this.nombreDocAnexo = "";
        this.tamanoAnexo = "";
        this.numHojasAnexo = "";
        this.cadenaAnexos = "";
        this.observacionAnexo="";
        try {
            for (Anexos anexo : anexos) {
                if (util.recuperarNombreAnexo(anexo.getIdTipoanexo())!= "PRIORIDAD" && util.recuperarNombreAnexo(anexo.getIdTipoanexo())!= "TRADUCCION" ){
                    anexosDto = new FormarAnexosDto();
                    anexosDto.setCadenaAnexos(util.recuperarNombreAnexo(anexo.getIdTipoanexo()));
                    anexosDto.setNombreDocAnexo(util.parseNombreAnexo(anexo.getNombreArchivo()));
                    anexosDto.setTamanoAnexo(anexo.getArchivoAnexo().length / 1024);
                    anexosDto.setNumHojasAnexo(Util.getNumberPages(anexo.getArchivoAnexo()));
                    anexosDto.setOrden(ordenarAnexos(anexo));
                    formarAnexosList.add(anexosDto);
                    if (anexo.getOtroIdioma()==2){
                        anexosDtoTrad = new FormarAnexosDto();
                        anexosDtoTrad.setCadenaAnexos(util.recuperarNombreAnexo(anexo.getIdTipoanexoTrad()));
                        anexosDtoTrad.setNombreDocAnexo(util.parseNombreAnexo(anexo.getNombreTrad()));
                        anexosDtoTrad.setTamanoAnexo(anexo.getArchivoTrad().length / 1024);
                        anexosDtoTrad.setNumHojasAnexo(Util.getNumberPages(anexo.getArchivoTrad()));
                        anexosDtoTrad.setOrden(ordenarAnexosTraduccion(anexo));
                        formarAnexosList.add(anexosDtoTrad); 
                    }
                }
            }
            
            if (listaTmpReportes != null && listaTmpReportes.size() > 0) {
                for (ReportesDto dto : listaTmpReportes) {
                    anexosDto = new FormarAnexosDto();
                    anexosDto.setCadenaAnexos(dto.getNombreBookMark());
                    anexosDto.setNombreDocAnexo(dto.getNombreReporte());
                    anexosDto.setTamanoAnexo(dto.getReporteBytes().length / 1024);
                    anexosDto.setNumHojasAnexo(Util.getNumberPages(dto.getReporteBytes()));
                    anexosDto.setOrden(dto.getOrden());
                    formarAnexosList.add(anexosDto);

                }
            }
           
            ////////////////////////////////
        //ordenar anexos
        FormarAnexosDto anexoAux;
        for (int i=0;i<formarAnexosList.size()-1;i++){            
            for(int j=i+1;j<formarAnexosList.size();j++)
            {
                if (formarAnexosList.get(j).getOrden() < formarAnexosList.get(i).getOrden())
                {
                    anexoAux=formarAnexosList.get(i);
                    formarAnexosList.set(i,formarAnexosList.get(j));
                    formarAnexosList.set(j, anexoAux);
                }    
            }
        }
        List<Anexos> prioridadAnx=Prioridades;
        if (prioridadAnx != null && prioridadAnx.size() > 0) {
                for (Anexos anexo : prioridadAnx) {
                    anexosDto = new FormarAnexosDto();
                    anexosDto.setCadenaAnexos(util.recuperarNombreAnexo(anexo.getIdTipoanexo()));
                    anexosDto.setNombreDocAnexo(util.parseNombreAnexo(anexo.getNombreArchivo()));
                    anexosDto.setTamanoAnexo(anexo.getArchivoAnexo().length / 1024);
                    anexosDto.setNumHojasAnexo(Util.getNumberPages(anexo.getArchivoAnexo()));
                    anexosDto.setOrden(ordenarAnexos(anexo));
                    formarAnexosList.add(anexosDto);
                }
            }

 ////////////////////////////////////////////           
            listaAnexos = reordenarReporte(formarAnexosList);
            listaAnexos = ordenarReporte(formarAnexosList);
            if (listaAnexos != null && listaAnexos.size() > 0) {
                for (int i=0; i< listaAnexos.size();i++)
                {
                if (!listaAnexos.get(i).getOrden().equals(1)) {
                        if (listaAnexos.get(i).getCadenaAnexos()=="HOJA DE DESCUENTO")
                        { int descuento=0, tamaño=0,paginas=0;
                          for (int j=i;j<listaAnexos.size();j++)
                          {
                              if (listaAnexos.get(j).getCadenaAnexos()=="HOJA DE DESCUENTO")
                              {
                               tamaño +=  listaAnexos.get(j).getTamanoAnexo();
                               paginas += listaAnexos.get(j).getNumHojasAnexo();
                               descuento++;
                              }
                          }
                            this.cadenaAnexos += " * " + listaAnexos.get(i).getCadenaAnexos() + "\n";
                            this.nombreDocAnexo += "  " + listaAnexos.get(i).getNombreDocAnexo() +  "\n";
                            this.tamanoAnexo += "  " + tamaño + " KB\n";
                            this.numHojasAnexo += "  " + paginas + " hoja(s)\n";
                            this.observacionAnexo += "  " + "\n";
                            this.totalHojasAnexo += paginas;
                            i+=descuento-1;
//                        if (listaAnexos.get(i).getCadenaAnexos()=="DESCRIPCION" && listaAnexos.get(i+1).getCadenaAnexos()=="MEMORIA TÉCNICA")
//                        {   
//                            int tamaño = listaAnexos.get(i).getTamanoAnexo()+listaAnexos.get(i+1).getTamanoAnexo();
//                            long hojas = listaAnexos.get(i).getNumHojasAnexo()+listaAnexos.get(i+1).getNumHojasAnexo();
//                            this.cadenaAnexos += " * " + listaAnexos.get(i+1).getCadenaAnexos() + "\n";
//                            this.nombreDocAnexo += "  " + listaAnexos.get(i).getNombreDocAnexo()+"/"+listaAnexos.get(i+1).getNombreDocAnexo() + "\n";
//                            this.tamanoAnexo += "  " + tamaño + " KB\n";
//                            this.numHojasAnexo += "  " + hojas + " hoja(s)\n";
//                            this.observacionAnexo += "  " + "\n";
//                            this.totalHojasAnexo += hojas;
//                             i++;
                        }else if (listaAnexos.get(i).getCadenaAnexos()=="FIGURAS")
                        {   
                            this.cadenaAnexos += " * " + listaAnexos.get(i).getCadenaAnexos() + "\n";
                            this.nombreDocAnexo += "  " + "\n";
                            this.tamanoAnexo += "  " + "\n";
                            this.numHojasAnexo += "  " + "\n";
                            this.observacionAnexo += "  " + "\n";
                            this.totalHojasAnexo += listaAnexos.get(i).getNumHojasAnexo();
                            for(ImagenDibujo imagen: figuras)
                            {
                                this.cadenaAnexos += "   " + "\n";
                                this.nombreDocAnexo += "  " + imagen.getNombreArchivo() + "\n";
                                this.tamanoAnexo += "  " + imagen.getArchivo().length/1024 + " KB\n";
                                this.numHojasAnexo += "  " + "1 hoja(s)\n";
                                this.observacionAnexo += "  " + "\n";
                            }

                        }else if(listaAnexos.get(i).getCadenaAnexos()=="ACUSE DE RECIBO"){
                            
                        }else
                        {
                            this.cadenaAnexos += " * " + listaAnexos.get(i).getCadenaAnexos() + "\n";
                            this.nombreDocAnexo += "  " + listaAnexos.get(i).getNombreDocAnexo() + "\n";
                            this.tamanoAnexo += "  " + listaAnexos.get(i).getTamanoAnexo() + " KB\n";
                            this.numHojasAnexo += "  " + listaAnexos.get(i).getNumHojasAnexo() + " hoja(s)\n";
                            this.observacionAnexo += "  " + "\n";
                            this.totalHojasAnexo += listaAnexos.get(i).getNumHojasAnexo();
                        }
                    }
                    else
                    {   this.cadenaAnexos += " * " + listaAnexos.get(i).getCadenaAnexos() + "\n";
                        this.nombreDocAnexo += "  " + listaAnexos.get(i).getNombreDocAnexo() + "\n";
                        this.tamanoAnexo += "  " + listaAnexos.get(i).getTamanoAnexo() + " KB\n";
                        this.numHojasAnexo += "  " + listaAnexos.get(i).getNumHojasAnexo() + " hoja(s)\n";
                        this.observacionAnexo += "  " + "\n";
                        this.totalHojasAnexo += listaAnexos.get(i).getNumHojasAnexo();//-1;
                    }
                }
                
//                for (FormarAnexosDto formarAnexosDto : listaAnexos) {
//                    if (!formarAnexosDto.getOrden().equals(1)) {
//                        this.cadenaAnexos += " * " + formarAnexosDto.getCadenaAnexos() + "\n";
//                        this.nombreDocAnexo += "  " + formarAnexosDto.getNombreDocAnexo() + "\n";
//                        this.tamanoAnexo += "  " + formarAnexosDto.getTamanoAnexo() + " KB\n";
//                         this.numHojasAnexo += "  " + formarAnexosDto.getNumHojasAnexo() + " hoja(s)\n";
//                        this.totalHojasAnexo += formarAnexosDto.getNumHojasAnexo();
//                    }
//                    else
//                    {
//                        this.totalHojasAnexo += formarAnexosDto.getNumHojasAnexo()-1;
//                    }
//                }
            }
            this.totalHojasAnexoStr = totalHojasAnexo.toString().equals("0") ? null : totalHojasAnexo.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Persona obtenerPrincipal(List<Persona> personas) {
        Persona principal = null;
        for (Persona persona : personas) {
            if (persona.getPrincipal() != null) {
                principal = persona;
                break;
            }
        }
        return principal;
    }

    public String[] concatenarNombres(List<Persona> personas, Long idPrincipal) {
        String nombres[] = new String[]{"", ""};
        if (personas.size() > 1) {
            for (int i = 0; i < personas.size(); i++) {
                if (i < 1) {
                    if (personas.get(i).getIdSolicitante() != null) {
                        if (personas.get(i).getIdSolicitante().equals(idPrincipal)) {
                            nombres[0] = personas.get(i).getNombreConcatenado() + ", " + nombres[0];
                        } else {
                            nombres[1] += personas.get(i).getNombreConcatenado() + ", ";
                        }
                    }
                } else if (personas.get(i).getIdSolicitante().equals(idPrincipal)) {
                    nombres[0] = personas.get(i).getNombreConcatenado() + ", " + nombres[0];
                } else {
                    nombres[1] += personas.get(i).getNombreConcatenado() + ", ";
                }
            }
        } else if (personas.size() == 1) {
            nombres[0] = personas.get(0).getNombreConcatenado() + ", " + nombres[0];
        }
        nombres[0] = nombres[0].length() < 2 ? nombres[0] : nombres[0].substring(0, nombres[0].length() - 2);
        nombres[1] = nombres[1].length() < 2 ? nombres[1] : nombres[1].substring(0, nombres[1].length() - 2);
        return nombres;
    }

    public String[] concatenarNomNacDom(List<Persona> personas, Long idPrincipal, Long clasePersonas) {
        String nombres[] = new String[]{"", ""};
        String domicilioPob[] = new String[]{"", ""};
        int noPersona = 1;
        if (personas.size() > 1) {
            for (int i = 0; i < personas.size(); i++) {
                if (i < 1) {
                    if (personas.get(i).getIdSolicitante() != null) {
                        if (personas.get(i).getIdSolicitante().equals(idPrincipal) || idPrincipal == 0) {
                            nombres[0] = personas.get(i).getNombreConcatenado() + ", " + nombres[0];
                        } else {
                            noPersona += 1;
                            nombres[1] += concatenarNomNacDomAnexo(personas.get(i), clasePersonas, noPersona);
                        }
                    }
                } else if ((personas.get(i).getIdSolicitante().equals(idPrincipal) || idPrincipal == 0) && clasePersonas != Constantes.CLS_PERSONA_INVENTOR) {
                    nombres[0] = personas.get(i).getNombreConcatenado() + ", " + nombres[0];
                } else {
                    noPersona += 1;
                    nombres[1] += concatenarNomNacDomAnexo(personas.get(i), clasePersonas, noPersona);
                }
            }
        } else if (personas.size() == 1) {
            nombres[0] = personas.get(0).getNombreConcatenado() + ", " + nombres[0];
        }
        nombres[0] = nombres[0].length() < 2 ? nombres[0] : nombres[0].substring(0, nombres[0].length() - 2);
//        nombres[1] = nombres[1].length() < 2 ? nombres[1] : nombres[1].substring(0, nombres[1].length() - 2);
        return nombres;
    }

    public String concatenarNomNacDomAnexo(Persona persona, Long clasePersonas, int noPersona) {
        String cadenaAnexo = "";
        String domicilioPob[] = new String[]{"", ""};
        if (clasePersonas == Constantes.CLS_PERSONA_APODERADO) {
            if (persona.getDomicilioObj()!=null){
                domicilioPob = recuperarDomicilioContacto(persona);
            }
        }else{
            domicilioPob = recuperarDomicilioPoblacion(persona);
        }
//        if(persona.getIdTipoSolicitante() !=null) {
        if (clasePersonas == Constantes.CLS_PERSONA_SOLICITANTE_PATENTES) {
            cadenaAnexo = "Solicitante " + noPersona + ":\n";
            cadenaAnexo += "- El Solicitante es el Inventor: ";
            if (persona.getInventor() > 0) {
                cadenaAnexo += "SI\n";
            } else {
                cadenaAnexo += "NO, el solicitante es causahabiente\n";
            }
        } else if (clasePersonas == Constantes.CLS_PERSONA_INVENTOR) {
            cadenaAnexo = "Inventor " + noPersona + ":\n";
        } else if (clasePersonas == Constantes.CLS_PERSONA_APODERADO) {
            cadenaAnexo = "Apoderado " + noPersona + ":\n";
        }

        if ((clasePersonas == Constantes.CLS_PERSONA_SOLICITANTE_PATENTES)
                || (clasePersonas == Constantes.CLS_PERSONA_INVENTOR)
                || (clasePersonas == Constantes.CLS_PERSONA_APODERADO)) {
            cadenaAnexo += "- Nombre: " + persona.getNombreConcatenado();
            if (clasePersonas != Constantes.CLS_PERSONA_APODERADO) {
                cadenaAnexo += "\n- Nacionalidad: " + persona.getNacionalidad().getNacionalidad();
                cadenaAnexo += "\n- Domicilio; calle, núm. Ext., núm. Int., colonia y código postal: " + domicilioPob[0]
                    + "\n- Población, Estado y País: " + domicilioPob[1] + "\n\n";
            } else {
                cadenaAnexo += persona.getRgp() == null ? "" : "\n- RGP: " + persona.getRgp();
//                if (persona.getDomicilioObj()!=null){
//                    cadenaAnexo += "\n- Domicilio; calle, núm. Ext., núm. Int., colonia y código postal: " + domicilioPob[0]
//                    + "\n- Población, Estado y País: " + domicilioPob[1] + "\n\n";
//                }else{
//                    cadenaAnexo += "\n- Domicilio; calle, núm. Ext., núm. Int., colonia y código postal: \n- Población, Estado y País: \n\n";
//                }
            }
//            cadenaAnexo += "\n- Domicilio; calle, núm. Ext., núm. Int., colonia y código postal: " + domicilioPob[0]
//                    + "\n- Población, Estado y País: " + domicilioPob[1] + "\n\n";
        }

        return cadenaAnexo;
    }

    public String concatenarNombres(List<Persona> personas) {
        String nombre = "";
        for (Persona persona : personas) {
            nombre += persona.getNombreConcatenado() + ", ";
        }
        return personas.isEmpty() ? nombre : nombre.substring(0, nombre.length() - 2);
    }

    public String[] concatenarNombresInventores(List<Persona> personas) {
        String nombres[] = new String[]{"", ""};
        for (int i = 0; i < personas.size(); i++) {
            if (i == 0) {
                nombres[0] = personas.get(i).getNombreConcatenado() + ", ";
            } else {
                nombres[1] += personas.get(i).getNombreConcatenado() + ", ";
            }
        }
        nombres[0] = nombres[0].length() < 2 ? nombres[0] : nombres[0].substring(0, nombres[0].length() - 2);
        nombres[1] = nombres[1].length() < 2 ? nombres[1] : nombres[1].substring(0, nombres[1].length() - 2);
        return nombres;
    }

    public String[] recuperarDomicilioPoblacion(Persona persona) {
        String domicilioPob[] = new String[]{"", ""};
        if (persona != null) {
           if (persona.getDomicilioObj().getPais().getIdPais().longValue() == Constantes.ID_PAIS_MEXICO) {
//               String numero = persona.getDomicilioObj().getNumero() != null ? persona.getDomicilioObj().getNumero() : Constantes.SIN_NUMERO ;
               String numExt = persona.getDomicilioObj().getNumExt() != null ? persona.getDomicilioObj().getNumExt() : "" ;
               String numInt = persona.getDomicilioObj().getNumInt() != null ? persona.getDomicilioObj().getNumInt() : "";
               
                domicilioPob[0] = persona.getDomicilioObj().getCalle() + ", " + numExt + ", " + numInt + ", " 
                        + persona.getDomicilioObj().getColonia() + " C.P. " + persona.getDomicilioObj().getCodigopostal();
                domicilioPob[1] = persona.getDomicilioObj().getPoblacion() + ", " + persona.getDomicilioObj().getEntidad().getNombre() + ", " + persona.getDomicilioObj().getPais().getNombre();
            } else {               
               domicilioPob[0] = concatenarTresCadenas(persona.getDomicilioObj().getCalle(), persona.getDomicilioObj().getNumExt() , 
                       persona.getDomicilioObj().getNumInt(), persona.getDomicilioObj().getColonia(), "C.P. " + persona.getDomicilioObj().getCodigopostal());
               
               domicilioPob[1] = concatenarCadenasConComa(persona.getDomicilioObj().getPoblacion(), persona.getDomicilioObj().getIdEntidad(), persona.getDomicilioObj().getPais().getNombre());
            }
        }

        return domicilioPob;
    }
    
    public String[] recuperarDomicilioContacto(Persona persona) {
        String domicilioPob[] = new String[]{"", ""};
        if (persona != null) {
           if (tramitePat.getDomicilioObj().getIdPais().longValue() == Constantes.ID_PAIS_MEXICO) {
//               String numero = persona.getDomicilioObj().getNumero() != null ? persona.getDomicilioObj().getNumero() : Constantes.SIN_NUMERO ;
               String numExt = tramitePat.getDomicilioObj().getNumExt() != null ? tramitePat.getDomicilioObj().getNumExt() : "" ;
               String numInt = tramitePat.getDomicilioObj().getNumInt() != null ? tramitePat.getDomicilioObj().getNumInt() : "";
               
                domicilioPob[0] = tramitePat.getDomicilioObj().getCalle() + ", " + numExt + ", " + numInt + ", " 
                        + tramitePat.getDomicilioObj().getColonia() + " C.P. " + tramitePat.getDomicilioObj().getCodigopostal().toString();
                domicilioPob[1] = tramitePat.getDomicilioObj().getPoblacion() + ", " + tramitePat.getDomicilioObj().getEntidad().getNombre() + ", " + tramitePat.getDomicilioObj().getPais().getNombre();
            } else {               
               domicilioPob[0] = concatenarTresCadenas(tramitePat.getDomicilioObj().getCalle(), tramitePat.getDomicilioObj().getNumExt() , 
                       tramitePat.getDomicilioObj().getNumInt(), tramitePat.getDomicilioObj().getColonia(), "C.P. " + tramitePat.getDomicilioObj().getCodigopostal());
               
               domicilioPob[1] = concatenarCadenasConComa(tramitePat.getDomicilioObj().getPoblacion(), tramitePat.getDomicilioObj().getIdEntidad(), tramitePat.getDomicilioObj().getPais().getNombre());
            }
        }

        return domicilioPob;
    }

        public List<FormarAnexosDto> reordenarReporte(List<FormarAnexosDto> listReportes) {
        List<FormarAnexosDto> listReportesOrdenados = null;
        
        int numAnexoDesc = 0;
        int con = 0;
        int orden = 0;
        
        FormarAnexosDto anexoAux = new FormarAnexosDto();
        
        for (int i=0;i<listReportes.size()-1;i++){            
            for(int j=i+1;j<listReportes.size();j++)
            {
                if (listReportes.get(j).getOrden() < listReportes.get(i).getOrden())
                {
                    anexoAux=listReportes.get(i);
                    listReportes.set(i,listReportes.get(j));
                    listReportes.set(j, anexoAux);
                }    
                
            }
        }
        orden =listReportes.get(0).getOrden();
        
        for (int i=0;i<listReportes.size();i++)
        {
            listReportes.get(i).setOrden(orden);
            orden += 1;
        }
//Si se vuelven  a juntar los documentos de descripcion y reivindicacion descomentar           
//        uno:
//        for (FormarAnexosDto dto : listReportes) {
//            if (dto.getCadenaAnexos().equals("HOJA DE DESCUENTO")) {
//                numAnexoDesc++;
//                orden = dto.getOrden();
//            }
//
//            if (numAnexoDesc > 1) {
//                break uno;
//            }
//            con++;
//        }
//
//        if (numAnexoDesc > 1) {
//            con -= 1;
//            for (; con < listReportes.size(); con++) {
//                if(listReportes.get(con+1).getCadenaAnexos()=="DESCRIPCION" )
//                {
//                  listReportes.get(con+1).setOrden(orden);
//                  orden += 1;
//                  listReportes.get(con).setOrden(orden);
//                  con++;
//                }
//                else
//                {    
//                    listReportes.get(con).setOrden(orden);
//                }
//                orden += 1;
//            }
//        }
        
        return listReportes;
    }
    
    public Integer ordenarAnexos(Anexos anex) {
        int qty = 9;
        Integer orden = null;

        if (anex.getExtension().equals("pdf")) {
            switch (anex.getIdTipoanexo().intValue()) {
                case 17://comprobante pago                        
                    orden = new Integer(2);
                    break;
                case 41://Documento descuento
                    orden = new Integer(3);
                    break;
                case 8://poder de acreditamiento del apoderado/RGP
                    orden = new Integer(4);
                    break;    
                case 19://poder de acreditamiento del apoderado/RGP
                    orden = new Integer(4);
                    break;
                case 20://cesión de derechos
                    orden = new Integer(6);
                    break;
//                    48 CARTA PODER SIMPLE
//                    49 CONSTANCIA RGP
//                    50 PODER NOTARIAL
//                    51 ACTA CONSTITUTIVA
//                    52 OTRO 
                case 48: case 49: case 50:
                case 51: case 52:    
                    orden = new Integer(6);
                    break;                          
                case 21://DOCUMENTO COMPROBATORIO DE DIVULGACIÓN PREVIA
                    orden = new Integer(7);
                    break;
                case 15://descripcion
                    orden = new Integer(8);
                    break;
                case 16://REIVINDICACION //Memoria tecnica
                    orden = new Integer(9);
                    break; 
                case 43://Resumen
                    orden = new Integer(10);
                    break;                      
                case 22://prioridad
                    orden = new Integer(15);//11//13//14
                    break;
                case 27://traduccion prioridad
                    orden = new Integer(16);//12//14//15
                    break;           
                default://otros
                    orden = new Integer(17);//13//15//16
                    break;         
            }
        }
        return orden;
    }
    public Integer ordenarAnexosTraduccion(Anexos anex) {
        Integer orden = null;
        if (anex.getExtension().equals("pdf")) {
            switch (anex.getIdTipoanexoTrad().intValue()) {
                 case 45://Traducción descripción
                    orden = new Integer(12);//11
                    break; 
                case 46://Traducción reivindicación 
                    orden = new Integer(13);//12
                    break; 
                case 47://Traducción Resumen 1
                    orden = new Integer(14);//13
                    break;    
                default://otros
                    orden = new Integer(18);//13//16//17
                    break;        
            }
        }
        return orden;
    }

    public List<FormarAnexosDto> ordenarReporte(List<FormarAnexosDto> listReportes) {
        List<FormarAnexosDto> listAnexosOrdenados = null;
        HashMap<Integer, FormarAnexosDto> mapaReportes = new HashMap<Integer, FormarAnexosDto>();

        for (FormarAnexosDto dto : listReportes) {
            //Validacion para los documentos OTROS
            if (dto.getOrden() == 13) {
                int checkExiste = dto.getOrden();
                while (mapaReportes.get(checkExiste) != null) {
                    checkExiste++;
                }
                mapaReportes.put(checkExiste, dto);
            } else {
                mapaReportes.put(dto.getOrden(), dto);
            }
        }

        return listAnexosOrdenados = new ArrayList<FormarAnexosDto>(mapaReportes.values());
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getCorreoApod() {
        return correoApod;
    }

    public void setCorreoApod(String correoApod) {
        this.correoApod = correoApod;
    }

    public String getCorreoInv() {
        return correoInv;
    }

    public void setCorreoInv(String correoInv) {
        this.correoInv = correoInv;
    }

    public String getCorreoSol() {
        return correoSol;
    }

    public void setCorreoSol(String correoSol) {
        this.correoSol = correoSol;
    }

    public String getCalleColoniaApod() {
        return calleColoniaApod;
    }

    public void setCalleColoniaApod(String calleColoniaApod) {
        this.calleColoniaApod = calleColoniaApod;
    }

    public String getPoblacionEstadoAPod() {
        return poblacionEstadoAPod;
    }

    public void setPoblacionEstadoAPod(String poblacionEstadoAPod) {
        this.poblacionEstadoAPod = poblacionEstadoAPod;
    }

    public String getCalleColoniaInv() {
        return calleColoniaInv;
    }

    public void setCalleColoniaInv(String calleColoniaInv) {
        this.calleColoniaInv = calleColoniaInv;
    }

    public String getPoblacionEstadoInv() {
        return poblacionEstadoInv;
    }

    public void setPoblacionEstadoInv(String poblacionEstadoInv) {
        this.poblacionEstadoInv = poblacionEstadoInv;
    }

    public String getCalleColoniaSol() {
        return calleColoniaSol;
    }

    public void setCalleColoniaSol(String calleColoniaSol) {
        this.calleColoniaSol = calleColoniaSol;
    }

//      public void setCalleColoniaEst(String calle, String colonia) {
//        if (colonia != null && calle != null && calle.length() > 0 && colonia.length() > 0) {
//            this.calleColoniaEst = calle + "," + colonia;
//        } else {
//            if (colonia != null && colonia.length() > 0) {
//                this.calleColoniaEst = colonia;
//            } else if (calle != null && calle.length() > 0) {
//                this.calleColoniaEst = calle;
//            }
//        }
//
//    }
    public String getPoblacionEstadoSol() {
        return poblacionEstadoSol;
    }

    public void setPoblacionEstadoSol(String poblacionEstadoSol) {
        this.poblacionEstadoSol = poblacionEstadoSol;
    }

    public String getFaxApod() {
        return faxApod;
    }

    public void setFaxApod(String faxApod) {
        this.faxApod = faxApod;
    }

    public String getFaxInv() {
        return faxInv;
    }

    public void setFaxInv(String faxInv) {
        this.faxInv = faxInv;
    }

    public String getFaxSol() {
        return faxSol;
    }

    public void setFaxSol(String faxSol) {
        this.faxSol = faxSol;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaDivulgacion() {
        return fechaDivulgacion;
    }

    public void setFechaDivulgacion(String fechaDivulgacion) {
        this.fechaDivulgacion = fechaDivulgacion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getInvencion() {
        return invencion;
    }

    public void setInvencion(String invencion) {
        this.invencion = invencion;
    }

    public String getNacionalidadApod() {
        return nacionalidadApod;
    }

    public void setNacionalidadApod(String nacionalidadApod) {
        this.nacionalidadApod = nacionalidadApod;
    }

    public String getNacionalidadInv() {
        return nacionalidadInv;
    }

    public void setNacionalidadInv(String nacionalidadInv) {
        this.nacionalidadInv = nacionalidadInv;
    }

    public String getNacionalidadSol() {
        return nacionalidadSol;
    }

    public void setNacionalidadSol(String nacionalidadSol) {
        this.nacionalidadSol = nacionalidadSol;
    }

    public String getNombresAPod() {
        return nombresApod;
    }

    public void setNombresAPod(String nombresAPod) {
        this.nombresApod = nombresAPod;
    }

    public String getNombresInv() {
        return nombresInv;
    }

    public void setNombresInv(String nombresInv) {
        this.nombresInv = nombresInv;
    }

    public String getNombresSol() {
        return nombresSol;
    }

    public void setNombresSol(String nombresSol) {
        this.nombresSol = nombresSol;
    }

    public String getTelefonoApod() {
        return telefonoApod;
    }

    public void setTelefonoApod(String telefonoApod) {
        this.telefonoApod = telefonoApod;
    }

    public String getTelefonoInv() {
        return telefonoInv;
    }

    public void setTelefonoInv(String telefonoInv) {
        this.telefonoInv = telefonoInv;
    }

    public String getTelefonoSol() {
        return telefonoSol;
    }

    public void setTelefonoSol(String telefonoSol) {
        this.telefonoSol = telefonoSol;
    }

    public String getNombresApod() {
        return nombresApod;
    }

    public void setNombresApod(String nombresApod) {
        this.nombresApod = nombresApod;
    }

    public String getRgpApod() {
        return rgpApod;
    }

    public void setRgpApod(String rgpApod) {
        this.rgpApod = rgpApod;
    }

    public String getNombresNot() {
        return nombresNot;
    }

    public void setNombresNot(String nombresNot) {
        this.nombresNot = nombresNot;
        this.nombresNot = Util.getRows(nombresNot, new Font(Font.SANS_SERIF, Font.BOLD, 8), 600, 3);
        if (nombresNot.equals(this.nombresNot)) {
            this.nombresPersNotAnexo = "";
        } else {
            this.nombresPersNotAnexo = nombresNot.substring(this.nombresNot.length(), nombresNot.length());
        }
    }

    public int getSubTipoSolicitud() {
        return subTipoSolicitud;
    }

    public void setSubTipoSolicitud(int subTipoSolicitud) {
        this.subTipoSolicitud = subTipoSolicitud;
    }

    public int getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(int tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getRutaFirmaImpi() {
        return rutaFirmaImpi;
    }

    public void setRutaFirmaImpi(String rutaFirmaImpi) {
        this.rutaFirmaImpi = rutaFirmaImpi;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getFolioPago() {
        return folioPago;
    }

    public void setFolioPago(String folioPago) {
        this.folioPago = folioPago;
    }

    public String getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(String montoPago) {
        this.montoPago = montoPago;
    }

    public String getExpedienteDivisional() {
        return expedienteDivisional;
    }

    public void setExpedienteDivisional(String expedienteDivisional) {
        this.expedienteDivisional = expedienteDivisional;
    }

    public String getFechaPresDivicional() {
        return fechaPresDivicional;
    }

    public void setFechaPresDivicional(String fechaPresDivicional) {
        this.fechaPresDivicional = fechaPresDivicional;
    }

    public String getFechaPresPaisPrior() {
        return fechaPresPaisPrior;
    }

    public void setFechaPresPaisPrior(String fechaPresPaisPrior) {
        this.fechaPresPaisPrior = fechaPresPaisPrior;
    }

    public String getFiguraJurDivisional() {
        return figuraJurDivisional;
    }

    public void setFiguraJurDivisional(String figuraJurDivisional) {
        this.figuraJurDivisional = figuraJurDivisional;
    }

    public String getNombresPaisPrior() {
        return nombresPaisPrior;
    }

    public void setNombresPaisPrior(String nombresPaisPrior) {
        this.nombresPaisPrior = nombresPaisPrior;
    }

    public String getNumSeriePaisPrior() {
        return numSeriePaisPrior;
    }

    public void setNumSeriePaisPrior(String numSeriePaisPrior) {
        this.numSeriePaisPrior = numSeriePaisPrior;
    }

    public String getNombresApodAnexo() {
        return nombresApodAnexo;
    }

    public void setNombresApodAnexo(String nombresApodAnexo) {
        this.nombresApodAnexo = nombresApodAnexo;
    }

    public String getNombresInvAnexo() {
        return nombresInvAnexo;
    }

    public void setNombresInvAnexo(String nombresInvAnexo) {
        this.nombresInvAnexo = nombresInvAnexo;
    }

    public String getNombresPersNotAnexo() {
        return nombresPersNotAnexo;
    }

    public void setNombresPersNotAnexo(String nombresPersNotAnexo) {
        this.nombresPersNotAnexo = nombresPersNotAnexo;
    }

    public String getNombresSolAnexo() {
        return nombresSolAnexo;
    }

    public void setNombresSolAnexo(String nombresSolAnexo) {
        this.nombresSolAnexo = nombresSolAnexo;
    }

    public String getPaisPriorAnexo() {
        return paisPriorAnexo;
    }

    public void setPaisPriorAnexo(String paisPriorAnexo) {
        this.paisPriorAnexo = paisPriorAnexo;
    }

    public void setCadenaImpi(String cadenaImpi) {
        this.cadenaImpi = cadenaImpi;
    }

    public void setCadenaSolicitante(String cadenaSolicitante) {
        this.cadenaSolicitante = cadenaSolicitante;
    }

    public void setCertificadora(String certificadora) {
        this.certificadora = certificadora;
    }

    public void setSelloImpi(String selloImpi) {
        this.selloImpi = selloImpi;
    }

    public void setSelloSolicitante(String selloSolicitante) {
        this.selloSolicitante = selloSolicitante;
    }

    public String getCadenaImpi() {
        return cadenaImpi;
    }

    public String getCadenaSolicitante() {
        return cadenaSolicitante;
    }

    public String getCertificadora() {
        return certificadora;
    }

    public String getSelloImpi() {
        return selloImpi;
    }

    public String getSelloSolicitante() {
        return selloSolicitante;
    }

    public String getNombreFirmante() {
        return nombreFirmante;
    }

    public void setNombreFirmante(String nombreFirmante) {
        this.nombreFirmante = nombreFirmante;
    }

    public FirmaDto getFirma() {
        return firma;
    }

    public void setFirma(FirmaDto firma) {
        this.firma = firma;
    }

    public Short getInventorSol() {
        return inventorSol;
    }

    public void setInventorSol(Short inventorSol) {
        this.inventorSol = inventorSol;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCadenaAnexos() {
        return cadenaAnexos;
    }

    public void setCadenaAnexos(String cadenaAnexos) {
        this.cadenaAnexos = cadenaAnexos;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public String getExpedientePriorAnexo() {
        return expedientePriorAnexo;
    }

    public void setExpedientePriorAnexo(String expedientePriorAnexo) {
        this.expedientePriorAnexo = expedientePriorAnexo;
    }

    public String getFechaPriorAnexo() {
        return fechaPriorAnexo;
    }

    public void setFechaPriorAnexo(String fechaPriorAnexo) {
        this.fechaPriorAnexo = fechaPriorAnexo;
    }

    public String getNombreDocAnexo() {
        return nombreDocAnexo;
    }

    public void setNombreDocAnexo(String nombreAnexo) {
        this.nombreDocAnexo = nombreAnexo;
    }

    public String getTamanoAnexo() {
        return tamanoAnexo;
    }

    public void setTamanoAnexo(String tamañoAnexo) {
        this.tamanoAnexo = tamañoAnexo;
    }

    public String getNumHojasAnexo() {
        return numHojasAnexo;
    }

    public void setNumHojasAnexo(String numHojasAnexo) {
        this.numHojasAnexo = numHojasAnexo;
    }

    public String getObservacionesAnexo() {
        return observacionesAnexo;
    }

    public void setObservacionesAnexo(String observacionesAnexo) {
        this.observacionesAnexo = observacionesAnexo;
    }

    public Long getTotalHojasAnexo() {
        return totalHojasAnexo;
    }

    public void setTotalHojasAnexo(Long totalHojasAnexo) {
        this.totalHojasAnexo = totalHojasAnexo;
    }

    public String getTotalHojasAnexoStr() {
        return totalHojasAnexoStr;
    }

    public void setTotalHojasAnexoStr(String totalHojasAnexoStr) {
        this.totalHojasAnexoStr = totalHojasAnexoStr;
    }

    public String getObservacionAnexo() {
        return observacionAnexo;
    }

    public void setObservacionAnexo(String observacionAnexo) {
        this.observacionAnexo = observacionAnexo;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public List<ReportesDto> getListaTmpReportes() {
        return listaTmpReportes;
    }

    public void setListaTmpReportes(List<ReportesDto> listaTmpReportes) {
        this.listaTmpReportes = listaTmpReportes;
    }

    public String getFechaEstatusActual() {
        return fechaEstatusActual;
    }

    public void setFechaEstatusActual(String fechaEstatusActual) {
        this.fechaEstatusActual = fechaEstatusActual;
    }

    public String getEmailSolicitante() {
        return emailSolicitante;
    }

    public void setEmailSolicitante(String emailSolicitante) {
        this.emailSolicitante = emailSolicitante;
    }

    public String getEmailApoderado() {
        return emailApoderado;
    }

    public void setEmailApoderado(String emailApoderado) {
        this.emailApoderado = emailApoderado;
    }

    public String getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(String fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public String getExtTelefonoSol() {
        return extTelefonoSol;
    }

    public void setExtTelefonoSol(String extTelefonoSol) {
        this.extTelefonoSol = extTelefonoSol;
    }

    public String getExtTelefonoInv() {
        return extTelefonoInv;
    }

    public void setExtTelefonoInv(String extTelefonoInv) {
        this.extTelefonoInv = extTelefonoInv;
    }
    
    public TramitePatente getTramitePat() {
        return tramitePat;
    }

    public void setTramitePat(TramitePatente tramitePat) {
        this.tramitePat = tramitePat;
    }
 
    
}
