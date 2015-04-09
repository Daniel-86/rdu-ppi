package mx.gob.impi.rdu.dto;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.TramitePromocionMarca;
import mx.gob.impi.rdu.util.Util;

/**
 *
 * @author JBMM
 */
public class ReporteCambioDomicilioDto {
    private String rutaFirmaImpi;
    private String codigoBarras ;//firma
    private String folio;//firma
    private String fechaRecepcion;//firma
    private String notDomicilio;
    private String notPoblacion;
    private String notCodigoPostal;
    private String notTelefono;
    private String notCorreo;
    private String titular;
    private String tipoSolicitud;
    private String domicilio;
    private String poblacion;
    private String codigoPostal;
    private String telefono;
    private String correo;
    private String pais;

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

    public ReporteCambioDomicilioDto(TramitePromocionMarca promoMarca, String rutaFirmaImpi, List<NumerosSigmarDTO> registros) {
        this.convertirTramite(promoMarca, rutaFirmaImpi, registros);
    }

    public void convertirTramite(TramitePromocionMarca promoMarca, String pathFirmaImpi, List<NumerosSigmarDTO> registros){

        this.setRutaFirmaImpi(pathFirmaImpi);
        this.setCodigoBarras(promoMarca.getCodBarras());
        this.setFolio(promoMarca.getFolio());
        this.setFechaRecepcion(promoMarca.getFechaSysDate() == null ? "":Util.formatearFecha(promoMarca.getFechaSysDate(),Util.FORMATODDMMYYYYHHMMSS) );
        if(promoMarca.getListaTramitePersona()!=null){
            this.setTitular(promoMarca.getListaTramitePersona().get(0).getPersona().getNombrecompleto());
            this.setDomicilio(promoMarca.getListaTramitePersona().get(0).getPersona().getDomicilioObj().getCalle());
            this.setPoblacion(promoMarca.getListaTramitePersona().get(0).getPersona().getDomicilioObj().getPoblacion());
            this.setCodigoPostal(promoMarca.getListaTramitePersona().get(0).getPersona().getDomicilioObj().getCodigopostal());
            this.setTelefono(promoMarca.getListaTramitePersona().get(0).getPersona().getDatosContacto().getTelefono());
            this.setCorreo(promoMarca.getListaTramitePersona().get(0).getPersona().getDatosContacto().getCorreoelectronico());
            this.setPais(promoMarca.getListaTramitePersona().get(0).getPersona().getPais()==null?"":promoMarca.getListaTramitePersona().get(0).getPersona().getPais().getNombre());
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

        if(promoMarca.getNotificacion()!=null){
            this.setNotDomicilio(promoMarca.getNotificacion().getDomicilio().getCalle());
            this.setNotPoblacion(promoMarca.getNotificacion().getDomicilio().getPoblacion());
            this.setNotCodigoPostal(promoMarca.getNotificacion().getDomicilio().getCodigopostal());
            this.setNotTelefono(promoMarca.getNotificacion().getDatosContacto().getTelefono());
            this.setNotCorreo(promoMarca.getNotificacion().getDatosContacto().getCorreoelectronico());
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

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getAnoSolRegistro() {
        return anoSolRegistro;
    }

    public void setAnoSolRegistro(String anoSolRegistro) {
        this.anoSolRegistro = anoSolRegistro;
    }

    public String getAnoSolRegistroAnexo() {
        return anoSolRegistroAnexo;
    }

    public void setAnoSolRegistroAnexo(String anoSolRegistroAnexo) {
        this.anoSolRegistroAnexo = anoSolRegistroAnexo;
    }

    public String getApoderado() {
        return apoderado;
    }

    public void setApoderado(String apoderado) {
        this.apoderado = apoderado;
    }

    public String getApoderadoAnexo() {
        return apoderadoAnexo;
    }

    public void setApoderadoAnexo(String apoderadoAnexo) {
        this.apoderadoAnexo = apoderadoAnexo;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDenominacionAnexo() {
        return denominacionAnexo;
    }

    public void setDenominacionAnexo(String denominacionAnexo) {
        this.denominacionAnexo = denominacionAnexo;
    }

    public String getExpedienteRegistro() {
        return expedienteRegistro;
    }

    public void setExpedienteRegistro(String expedienteRegistro) {
        this.expedienteRegistro = expedienteRegistro;
    }

    public String getExpedienteRegistroAnexo() {
        return expedienteRegistroAnexo;
    }

    public void setExpedienteRegistroAnexo(String expedienteRegistroAnexo) {
        this.expedienteRegistroAnexo = expedienteRegistroAnexo;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getRegistroAnexo() {
        return registroAnexo;
    }

    public void setRegistroAnexo(String registroAnexo) {
        this.registroAnexo = registroAnexo;
    }

    public String getTipoSolRegistro() {
        return tipoSolRegistro;
    }

    public void setTipoSolRegistro(String tipoSolRegistro) {
        this.tipoSolRegistro = tipoSolRegistro;
    }

    public String getTipoSolRegistroAnexo() {
        return tipoSolRegistroAnexo;
    }

    public void setTipoSolRegistroAnexo(String tipoSolRegistroAnexo) {
        this.tipoSolRegistroAnexo = tipoSolRegistroAnexo;
    }

    public String getTitularRegistro() {
        return titularRegistro;
    }

    public void setTitularRegistro(String titularRegistro) {
        this.titularRegistro = titularRegistro;
    }

    public String getTitularRegistroAnexo() {
        return titularRegistroAnexo;
    }

    public void setTitularRegistroAnexo(String titularRegistroAnexo) {
        this.titularRegistroAnexo = titularRegistroAnexo;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }
    
    

}