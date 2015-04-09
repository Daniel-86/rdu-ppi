package mx.gob.impi.rdu.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.BindingProvider;
import mx.gob.impi.cliente.ucm.CheckIn;
import mx.gob.impi.cliente.ucm.CheckInSoap;
import mx.gob.impi.cliente.ucm.CheckInUniversalResult;
import mx.gob.impi.cliente.ucm.IdcFile;
import mx.gob.impi.cliente.ucm.IdcProperty;
import mx.gob.impi.cliente.ucm.IdcPropertyList;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.persistence.mappers.TramiteMapper;
import mx.gob.impi.rdu.persistence.mappers.TramitePatenteMapper;
import mx.gob.impi.rdu.persistence.mappers.TramitePromocionPatenteMapper;
import mx.gob.impi.rdu.persistence.model.Firma;
import mx.gob.impi.rdu.service.RduFirmaService;
import mx.gob.impi.rdu.service.RduProcesosService;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.Util;
import org.apache.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author JBMM
 */
public class RduProcesosServiceImpl implements RduProcesosService {

    private Logger lger = Logger.getLogger(this.getClass());
    @Qualifier("rduFirmaService")
    @Autowired
    private RduFirmaService rduFirmaService;
    private int cont = 0;
    private TramiteMapper rduTramiteMapper;
    private TramitePatenteMapper rduTramitePatenteMapper;
    private TramitePromocionPatenteMapper rduTramitePromocionPatenteMapper;

    public void setRduFirmaService(RduFirmaService rduFirmaService) {
        this.rduFirmaService = rduFirmaService;
    }

    public void setRduTramiteMapper(TramiteMapper rduTramiteMapper) {
        this.rduTramiteMapper = rduTramiteMapper;
    }

    public void setRduTramitePatenteMapper(TramitePatenteMapper rduTramitePatenteMapper) {
        this.rduTramitePatenteMapper = rduTramitePatenteMapper;
    }

    public int eliminarSolicitud(SolicitudPreparacionDto solicitudPreparacionDto) {
        int rtRespuesta = 0;
        if (Constantes.SOL_SIGNOS_DISTINTIVOS.intValue() == solicitudPreparacionDto.getIdTiposolicitud().intValue()) {
            rtRespuesta = this.rduTramiteMapper.borradoLogico(solicitudPreparacionDto.getIdTramite(), 0);
        } else if (Constantes.SOL_SOLICITUD_PATENTE.intValue() == solicitudPreparacionDto.getIdTiposolicitud().intValue()
                || Constantes.SOL_MODELO_UTILIDAD.intValue() == solicitudPreparacionDto.getIdTiposolicitud().intValue()
                || Constantes.SOL_DISENO_INDUSTRIAL.intValue() == solicitudPreparacionDto.getIdTiposolicitud().intValue()
                || Constantes.SOL_CIRCUITO_INTEGRADO.intValue() == solicitudPreparacionDto.getIdTiposolicitud().intValue()) {
            rtRespuesta = rduTramitePatenteMapper.eliminacionLogica(solicitudPreparacionDto);
        }
        return rtRespuesta;
    }
    
    public int eliminarPromocion(SolicitudPreparacionDto solicitudPreparacionDto) {
        int rtRespuesta = 0;
        rtRespuesta = rduTramitePromocionPatenteMapper.eliminacionLogica(solicitudPreparacionDto);
        return rtRespuesta;
    }

    public String jobeliminarAcuse() {
        lger.info("***************************eliminarAcuse ==> ");
        String res = new String();

        try {
            IdcFile primaryFile = null;
            StringBuilder cadena = new StringBuilder();
            List<Firma> listaFirma = new ArrayList<Firma>();
            listaFirma = this.rduFirmaService.selectFirmas();


            for (int i = 0; i < listaFirma.size(); i++) {
                String[] campos = listaFirma.get(i).getFolio().split("/");
                String anio = campos[2];
                String folioDocumento = campos[3];
                String codigoHash = Util.getDigest(listaFirma.get(i).getAcusePdf());
                String logicName = listaFirma.get(i).getFolio().replaceAll("/", "_");

                byte[] archivoBytes = listaFirma.get(i).getAcusePdf();
                File anexoComprobante = Util.writeTempFile(new ByteArrayInputStream(archivoBytes), logicName, ".pdf");
                String dDocTitle = listaFirma.get(i).getFolio();
                lger.info("=====>getPath*****" + anexoComprobante.getPath());
                lger.info("=====>NOMBRE GetFile*****" + listaFirma.get(i).getFolio().replaceAll("/", "_"));

                primaryFile = GetFile(anexoComprobante.getPath(), logicName);

                cadena.append(listaFirma.get(0).getFolio());
                cadena.append("-");
                cadena.append(listaFirma.get(0).getCodigoBarras());
                cadena.append("-");
                cadena.append(listaFirma.get(0).getFolio());
                cadena.append("-");
                cadena.append(anio);
                cadena.append("-");
                cadena.append(folioDocumento);
                cadena.append("-");
                cadena.append(codigoHash);
                cadena.append("-");
                cadena.append(listaFirma.get(0).getExpediente());
                cadena.append("-");

                int secuencia = this.rduTramiteMapper.selectsecuencia();
                String nombreArchivo = "Rastrear" + secuencia + ".pdf";
                CheckInUniversalResult r = checkInUniversal(nombreArchivo, //nombre fisico del archivo
                        dDocTitle, //nombre logico EJ.PI_E_2009_009390
                        "Documentos", //Ej. Documentos? Para RDU no se cual seria
                        "rdu", //rdu
                        "Marcas",//"Marcas",//dSecurityGroup, //Ej, Contencioso Para RDU no se cual seria
                        "rdu", //rdu
                        getcustomDocMetaData(cadena), //Ver funcion getcustomDocMetaData
                        primaryFile,
                        null,//alternateFile, //se envia null
                        getcustomDocMetaData(cadena) //extraProps //Ver funcion getcustomDocMetaData
                        );

                lger.info("Resultado                      r.getDRevLabel() = {" + r.getDRevLabel() + "}");
                lger.info("Resultado                            r.getDID() = {" + r.getDID() + "}");
                lger.info("Resultado                    r.getDRevClassID() = {" + r.getDRevClassID() + "}");
                lger.info("Resultado                    r.getDRevisionID() = {" + r.getDRevisionID() + "}");
                lger.info("Resultado  r.getStatusInfo().getStatusMessage() = {" + r.getStatusInfo().getStatusMessage() + "}");
                lger.info("Resultado     r.getStatusInfo().getStatusCode() = {" + r.getStatusInfo().getStatusCode() + "}");

                if (r.getStatusInfo().getStatusCode() == 0) {
                    Firma firma = new Firma();
                    firma.setIdFirma(listaFirma.get(0).getIdFirma());
                    firma.setEnvio(Long.valueOf("1"));

                    this.rduFirmaService.udapteFirmasEnvio(firma);
                    lger.info("  ***Se actualizo el registro con id==>  " + listaFirma.get(i).getIdFirma()
                            + " En la tabla FIRMA");

                    res = "Operación Realizada Con exito";

                } else {
                    res = "Ocurrio errores durante el proceso";
                }

                //delete immediate
                anexoComprobante.delete();

            }


            return res;
        } catch (Exception e) {
            lger.info("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return e.getMessage();
        }
    }

    /**
     * archivo que se envia
     * @param fileName
     * @param logicName
     * @return
     */
    private IdcFile GetFile(String fileName, String logicName) {
        IdcFile primaryFile = new IdcFile();
        byte[] buffer;
        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(fileName);
            buffer = new byte[fileInput.available()];
            fileInput.read(buffer, 0x00, buffer.length);
            primaryFile.setFileContent(buffer);
            primaryFile.setFileName(logicName);
            return primaryFile;
        } catch (FileNotFoundException ex) {
            lger.info("******Errrorr>>>>>>>>>mensaje: " + ex.getMessage() + " localized: " + ex.getLocalizedMessage());
            return null;
        } catch (IOException ex) {
            lger.info("******Errrorr>>>>>>>>>mensaje: " + ex.getMessage() + " localized: " + ex.getLocalizedMessage());
            return null;
        } finally {
            try {
                if (fileInput != null) {
                    fileInput.close();
                }
            } catch (IOException ex) {
                lger.info("******Errrorr>>>>>>>>>mensaje: " + ex.getMessage() + " localized: " + ex.getLocalizedMessage());
            }
        }

    }

    /**
     * Enviar archivo a UCM
     * @param dDocName
     * @param dDocTitle
     * @param dDocType
     * @param dDocAuthor
     * @param dSecurityGroup
     * @param dDocAccount
     * @param customDocMetaData
     * @param primaryFile
     * @param alternateFile
     * @param extraProps
     * @return
     */
    private static CheckInUniversalResult checkInUniversal(java.lang.String dDocName, java.lang.String dDocTitle, java.lang.String dDocType, java.lang.String dDocAuthor, java.lang.String dSecurityGroup, java.lang.String dDocAccount, IdcPropertyList customDocMetaData, IdcFile primaryFile, IdcFile alternateFile, IdcPropertyList extraProps) {
        try {
            CheckIn service = new CheckIn();
            CheckInSoap port = service.getCheckInSoap();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "rdu");
            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "rdu");
            return port.checkInUniversal(dDocName, //nombre fisico del archivo
                    dDocTitle, //nombre logico EJ.PI_E_2009_009390
                    dDocType, //Ej. Documentos? Para RDU no se cual seria
                    dDocAuthor, //rdu
                    dSecurityGroup, //Ej, Contencioso Para RDU no se cual seria
                    dDocAccount, //rdu
                    customDocMetaData, //Ver funcion getcustomDocMetaData
                    primaryFile,
                    null,//alternateFile, //se envia null
                    customDocMetaData //extraProps //Ver funcion getcustomDocMetaData
                    );
        } finally {
        }
    }

    /**
     * Metadata de prueba
     * @param name Es el content_id
     * @return
     */
    public IdcPropertyList getcustomDocMetaData(StringBuilder name) {
        String[] camposFormateados = name.toString().split("-");

        if (cont > 0) {
            lger.info("************ 1.0xTITLE==>  " + camposFormateados[0]);
            lger.info("************ 1.0xCODIGOBARRAS==>  " + camposFormateados[1]);
            lger.info("************ 1.0xDOCUMENTO==>  " + camposFormateados[0]);
            lger.info("************ 1.0xANIO_DOCUMENTO==>  " + camposFormateados[3]);
            lger.info("************ xTIPO_DOCUMENTO==>  Documentos***");
            lger.info("************ xDESCRIPCION==>  Solicitud***");
            lger.info("************ xSECURITY==>  MARCAS***");
            lger.info("************ 1.0xCODIGOHASH==>  " + camposFormateados[5]);
            lger.info("************ xAREA_DOCUMENTO==>  Marcas***");
            lger.info("************ 1.0xEXPEDIENT==>  " + camposFormateados[6]);
            lger.info("************ 1.0xFOLIO_DOCUMENTO==>  " + camposFormateados[4]);
            lger.info("************ 1.0xNUM_EXPED==>  " + camposFormateados[6]);
            lger.info("************ xSENTIDO_DOCUMENTO==>  E***");
            lger.info("************ xSubType==>  Documentos***");
        }

        IdcPropertyList customDocMetaData = new IdcPropertyList();

        IdcProperty prop = new IdcProperty();
        prop.setName("xTITLE");
        prop.setValue(camposFormateados[0]);
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xCODIGOBARRAS");
        prop.setValue(camposFormateados[1]);
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xDOCUMENTO");//es el title
        prop.setValue(camposFormateados[0]);
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xANIO_DOCUMENTO");//serie
        prop.setValue(camposFormateados[3]);
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xTIPO_DOCUMENTO");
        prop.setValue("Documentos");
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xDESCRIPCION");
        prop.setValue("Solicitud");
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xSECURITY");
        prop.setValue("MARCAS");
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xCODIGOHASH");//hash del documento
        prop.setValue(camposFormateados[5]);
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xAREA_DOCUMENTO");
        prop.setValue("Marcas");
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xEXPEDIENT");//expediente de la solicitud
        prop.setValue(camposFormateados[6]);
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xFOLIO_DOCUMENTO");//folio de la solicitud
        prop.setValue("camposFormateados[4]");
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xNUM_EXPED");//expediente de la solicitud
        prop.setValue(camposFormateados[6]);
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xSENTIDO_DOCUMENTO");//entrada
        prop.setValue("E");
        customDocMetaData.getProperty().add(prop);

        prop = new IdcProperty();
        prop.setName("xSubType");
        prop.setValue("Documentos");
        customDocMetaData.getProperty().add(prop);

        cont++;
        return customDocMetaData;
    }

    public int selectsecuencia() {
        return this.rduTramiteMapper.selectsecuencia();
    }

    /**
     * @param rduTramitePromocionPatenteMapper the rduTramitePromocionPatenteMapper to set
     */
    public void setRduTramitePromocionPatenteMapper(TramitePromocionPatenteMapper rduTramitePromocionPatenteMapper) {
        this.rduTramitePromocionPatenteMapper = rduTramitePromocionPatenteMapper;
    }
}
