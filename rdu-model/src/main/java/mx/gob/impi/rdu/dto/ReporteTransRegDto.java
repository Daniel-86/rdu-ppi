package mx.gob.impi.rdu.dto;

import java.util.ArrayList;
import java.util.List;
import mx.gob.impi.rdu.dto.NumerosSigmarDTO;
import mx.gob.impi.rdu.persistence.model.NumerosSolicitud;
import mx.gob.impi.rdu.persistence.model.TramitePromocionMarca;
import mx.gob.impi.rdu.util.Util;

/**
 *
 * @author JBMM
 */
public class ReporteTransRegDto {
    private String rutaFirmaImpi;
    private String codigoBarras =/*Codigo de barras de prueba*/ "1234545676";
    private String expediente;
    private String folio;
    private String fecha;
    private String tipoSolicitud;
    private String notDomicilio;
    private String notPoblacion;
    private String notCodigoPostal;
    private String notTelefono;
    private String notCorreo;
    private String titularAnterior;
    private String titularAnteriorNac;
    private String titularActual;
    private String titularActualNac;
    private String titularActualTel;
    private String numeros;
    private String titularRegistro;
    private String tipoSolRegistro;
    private String anoSolRegistro;
    private String expedienteRegistro;
    private String registro;
    private String denominacion;
    private String apoderado;
    private String titularRegistroAnexo;
    private String tipoSolRegistroAnexo;
    private String anoSolRegistroAnexo;
    private String expedienteRegistroAnexo;
    private String registroAnexo;
    private String denominacionAnexo;
    private String apoderadoAnexo;

    public ReporteTransRegDto(TramitePromocionMarca promoMarca, String rutaFirmaImpi, List<NumerosSigmarDTO> registros) {
        this.convertirTramite(promoMarca, rutaFirmaImpi, registros);
    }

    public void convertirTramite(TramitePromocionMarca promoMarca, String pathFirmaImpi, List<NumerosSigmarDTO> registros){
        
        this.setRutaFirmaImpi(pathFirmaImpi);
        this.setCodigoBarras(promoMarca.getCodBarras());
        this.setFolio(promoMarca.getFolio());
        this.setFecha(promoMarca.getFechaSysDate()==null?"":Util.formatearFecha(promoMarca.getFechaSysDate(), Util.FORMATODDMMYYYYHHMMSS) );
        if(promoMarca.getNotificacion()!=null){
            this.setNotDomicilio(promoMarca.getNotificacion().getDomicilio().getCalle());
            this.setNotPoblacion(promoMarca.getNotificacion().getDomicilio().getPoblacion());
            this.setNotCodigoPostal(promoMarca.getNotificacion().getDomicilio().getCodigopostal());
            this.setNotTelefono(promoMarca.getNotificacion().getDatosContacto().getTelefono());
            this.setNotCorreo(promoMarca.getNotificacion().getDatosContacto().getCorreoelectronico());
        }       

        
        if(promoMarca.getListaTramitePersona()!=null && promoMarca.getListaTramitePersona().size()>0){
            // 0 Corresponde al Titular Anterior
            // 1 Corresponde al Titular Actual
            this.setTitularAnterior(promoMarca.getListaTramitePersona().size() >0 ?    promoMarca.getListaTramitePersona().get(0).getPersona().getNombrecompleto(): "");
            this.setTitularAnteriorNac(promoMarca.getListaTramitePersona().size()>0 ? promoMarca.getListaTramitePersona().get(0).getPersona().getNacionalidad().getNacionalidad():"");

            if(promoMarca.getListaTramitePersona().size()>1){
                this.setTitularActual(promoMarca.getListaTramitePersona().get(1).getPersona().getNombrecompleto());
                this.setTitularActualNac(promoMarca.getListaTramitePersona().get(1).getPersona().getNacionalidad().getNacionalidad());
                this.setTitularActualTel(promoMarca.getListaTramitePersona().get(1).getPersona().getDatosContacto().getTelefono());
            }

        }

        if(promoMarca.getNumeros()!=null){
            String tipo_solicitud = "";
            String ano_solicitud = "";
            String expeSolicitud = "";
            String registroSolicitud = "";
            String titular = "";
            String denomSolicitud = "";
            String apoderadoSolicitud = "";
            //Anexos
            String tipo_solicitudAnexo = "";
            String ano_solicitudAnexo = "";
            String expeSolicitudAnexo = "";
            String registroSolicitudAnexo = "";
            String titularAnexo = "";
            String denomSolicitudAnexo = "";
            String apoderadoSolicitudAnexo = "";
            int i = 0;

            for(NumerosSigmarDTO numSolicitud : registros){
                if(i<2){

                    System.out.println("************************************  "+registros.get(i).getTipo_solicitud());

                    tipo_solicitud+=registros.get(i).getTipo_solicitud() + " \n";
                    ano_solicitud+=registros.get(i).getAno_solicitud() + " \n";
                    expeSolicitud+=registros.get(i).getExpediente() + " \n";
                    registroSolicitud+=registros.get(i).getRegistro() + " \n";
                    titular+=registros.get(i).getTitular() + " \n";
                    denomSolicitud+=registros.get(i).getDenominacion() + " \n";
                    apoderadoSolicitud+=registros.get(i).getApoderado() + " \n";
                }else{
                    tipo_solicitudAnexo+=registros.get(i).getTipo_solicitud() + " \n";
                    ano_solicitudAnexo+=registros.get(i).getAno_solicitud() + " \n";
                    expeSolicitudAnexo+=registros.get(i).getExpediente() + " \n";
                    registroSolicitudAnexo+=registros.get(i).getRegistro() + " \n";
                    titularAnexo+=registros.get(i).getTitular() + " \n";
                    denomSolicitudAnexo+=registros.get(i).getDenominacion() + " \n";
                    apoderadoSolicitudAnexo+=registros.get(i).getApoderado() + " \n";
                }

                i++;
            }

            this.setTitularRegistro(titular.length() > 2 ? titular.substring(0, titular.length() - 2) : titular);
            this.setTipoSolRegistro(tipo_solicitud.length() > 2 ? tipo_solicitud.substring(0, tipo_solicitud.length() - 2) : tipo_solicitud);
            this.setAnoSolRegistro(ano_solicitud.length() > 2 ? ano_solicitud.substring(0, ano_solicitud.length() - 2) : ano_solicitud);
            this.setExpedienteRegistro(expeSolicitud.length() > 2 ? expeSolicitud.substring(0, expeSolicitud.length() - 2) : expeSolicitud);
            this.setRegistro(registroSolicitud.length() > 2 ? registroSolicitud.substring(0, registroSolicitud.length() - 2) : registroSolicitud);
            this.setDenominacion(denomSolicitud.length() > 2 ? denomSolicitud.substring(0, denomSolicitud.length() - 2) : denomSolicitud);
            this.setApoderado(apoderadoSolicitud.length() > 2 ? apoderadoSolicitud.substring(0, apoderadoSolicitud.length() - 2) : apoderadoSolicitud);

            this.setTitularRegistroAnexo(titularAnexo.length() > 2 ? titularAnexo.substring(0, titularAnexo.length() - 2) : titularAnexo);
            this.setTipoSolRegistroAnexo(tipo_solicitudAnexo.length() > 2 ? tipo_solicitudAnexo.substring(0, tipo_solicitudAnexo.length() - 2) : tipo_solicitudAnexo);
            this.setAnoSolRegistroAnexo(ano_solicitudAnexo.length() > 2 ? ano_solicitudAnexo.substring(0, ano_solicitudAnexo.length() - 2) : ano_solicitudAnexo);
            this.setExpedienteRegistroAnexo(expeSolicitudAnexo.length() > 2 ? expeSolicitudAnexo.substring(0, expeSolicitudAnexo.length() - 2) : expeSolicitudAnexo);
            this.setRegistroAnexo(registroSolicitudAnexo.length() > 2 ? registroSolicitudAnexo.substring(0, registroSolicitudAnexo.length() - 2) : registroSolicitudAnexo);
            this.setDenominacionAnexo(denomSolicitudAnexo.length() > 2 ? denomSolicitudAnexo.substring(0, denomSolicitudAnexo.length() - 2) : denomSolicitudAnexo);
            this.setApoderadoAnexo(apoderadoSolicitudAnexo.length() > 2 ? apoderadoSolicitudAnexo.substring(0, apoderadoSolicitudAnexo.length() - 2) : apoderadoSolicitudAnexo);
                    
        }

        if(promoMarca.getSubTipoSolicitud() != null){
            this.setTipoSolicitud(promoMarca.getSubTipoSolicitud().getDescripcion());
        }

        
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getRutaFirmaImpi() {
        return rutaFirmaImpi;
    }

    public void setRutaFirmaImpi(String rutaFirmaImpi) {
        this.rutaFirmaImpi = rutaFirmaImpi;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getNotCodigoPostal() {
        return notCodigoPostal;
    }

    public void setNotCodigoPostal(String notCodigoPostal) {
        this.notCodigoPostal = notCodigoPostal;
    }

    public String getNotCorreo() {
        return notCorreo;
    }

    public void setNotCorreo(String notCorreo) {
        this.notCorreo = notCorreo;
    }

    public String getNotDomicilio() {
        return notDomicilio;
    }

    public void setNotDomicilio(String notDomicilio) {
        this.notDomicilio = notDomicilio;
    }

    public String getNotPoblacion() {
        return notPoblacion;
    }

    public void setNotPoblacion(String notPoblacion) {
        this.notPoblacion = notPoblacion;
    }

    public String getNotTelefono() {
        return notTelefono;
    }

    public void setNotTelefono(String notTelefono) {
        this.notTelefono = notTelefono;
    }

    public String getTitularActual() {
        return titularActual;
    }

    public void setTitularActual(String titularActual) {
        this.titularActual = titularActual;
    }

    public String getTitularActualNac() {
        return titularActualNac;
    }

    public void setTitularActualNac(String titularActualNac) {
        this.titularActualNac = titularActualNac;
    }

    public String getTitularActualTel() {
        return titularActualTel;
    }

    public void setTitularActualTel(String titularActualTel) {
        this.titularActualTel = titularActualTel;
    }

    public String getTitularAnterior() {
        return titularAnterior;
    }

    public void setTitularAnterior(String titularAnterior) {
        this.titularAnterior = titularAnterior;
    }

    public String getTitularAnteriorNac() {
        return titularAnteriorNac;
    }

    public void setTitularAnteriorNac(String titularAnteriorNac) {
        this.titularAnteriorNac = titularAnteriorNac;
    }

    public String getNumeros() {
        return numeros;
    }

    public void setNumeros(String numeros) {
        this.numeros = numeros;
    }

    public String getTitularRegistro() {
        return titularRegistro;
    }

    public void setTitularRegistro(String titularRegistro) {
        this.titularRegistro = titularRegistro;
    }

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

    public String getAnoSolRegistroAnexo() {
        return anoSolRegistroAnexo;
    }

    public void setAnoSolRegistroAnexo(String anoSolRegistroAnexo) {
        this.anoSolRegistroAnexo = anoSolRegistroAnexo;
    }

    public String getApoderadoAnexo() {
        return apoderadoAnexo;
    }

    public void setApoderadoAnexo(String apoderadoAnexo) {
        this.apoderadoAnexo = apoderadoAnexo;
    }

    public String getDenominacionAnexo() {
        return denominacionAnexo;
    }

    public void setDenominacionAnexo(String denominacionAnexo) {
        this.denominacionAnexo = denominacionAnexo;
    }

    public String getExpedienteRegistroAnexo() {
        return expedienteRegistroAnexo;
    }

    public void setExpedienteRegistroAnexo(String expedienteRegistroAnexo) {
        this.expedienteRegistroAnexo = expedienteRegistroAnexo;
    }

    public String getRegistroAnexo() {
        return registroAnexo;
    }

    public void setRegistroAnexo(String registroAnexo) {
        this.registroAnexo = registroAnexo;
    }

    public String getTipoSolRegistroAnexo() {
        return tipoSolRegistroAnexo;
    }

    public void setTipoSolRegistroAnexo(String tipoSolRegistroAnexo) {
        this.tipoSolRegistroAnexo = tipoSolRegistroAnexo;
    }

    public String getTitularRegistroAnexo() {
        return titularRegistroAnexo;
    }

    public void setTitularRegistroAnexo(String titularRegistroAnexo) {
        this.titularRegistroAnexo = titularRegistroAnexo;
    }


    
}