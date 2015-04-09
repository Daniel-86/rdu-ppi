package mx.gob.impi.rdu.service.impl;

import java.nio.charset.Charset;
import java.util.*;
import mx.gob.impi.ingresos.persistence.mappers.FepsRecibidosMapper;
import mx.gob.impi.ingresos.persistence.model.FepsRecibidos;

import mx.gob.impi.rdu.dto.TramiteDto;
import mx.gob.impi.rdu.persistence.mappers.CatAreaMapper;
import mx.gob.impi.rdu.persistence.mappers.FirmaMapper;
import mx.gob.impi.rdu.persistence.mappers.FolioMapper;
import mx.gob.impi.rdu.persistence.mappers.CertificadoMapper;
import mx.gob.impi.rdu.persistence.mappers.CatEstatusCertificadoMapper;
import mx.gob.impi.rdu.persistence.mappers.FuncionesSimplesMapper;
import mx.gob.impi.rdu.persistence.model.*;

import mx.gob.impi.rdu.service.RduFirmaService;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.Util;
import mx.gob.impi.sagpat.persistence.mappers.DiaProcesoMapper;
import mx.gob.impi.sagpat.persistence.mappers.DocumentoExpedienteMapper;
import mx.gob.impi.sagpat.persistence.mappers.DocumentoMapper;
import mx.gob.impi.sagpat.persistence.mappers.ExpedienteMapper;
import mx.gob.impi.sagpat.persistence.mappers.LegajoMapper;
import mx.gob.impi.sagpat.persistence.mappers.LibroPartesMapper;
import mx.gob.impi.sagpat.persistence.mappers.PalabraIgnorarMapper;
import mx.gob.impi.sagpat.persistence.mappers.PersonaDirecMapper;
import mx.gob.impi.sagpat.persistence.mappers.PersonaMapper;
import mx.gob.impi.sagpat.persistence.mappers.PersonaPalabraMapper;
import mx.gob.impi.sagpat.persistence.model.Persona;

import mx.gob.impi.sagpat.persistence.mappers.SerieExpedienteMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudApoderadoMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudDibujosMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudInventorMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudPalabraMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudPrioridadMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudReivindicacionMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudReivindicacionWordMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudResumenUnicodeMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudTitularMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudTituloUnicodeMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudTituloWordMapper;
import mx.gob.impi.sagpat.persistence.mappers.ThisControlMapper;
import mx.gob.impi.sagpat.persistence.mappers.TipoSolicitudMapper;

import mx.gob.impi.sagpat.persistence.model.*;
import org.apache.log4j.Logger;
import org.apache.tika.language.LanguageIdentifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class RduFirmaServiceImpl implements RduFirmaService {

    private Logger lger = Logger.getLogger(this.getClass());
    private CatAreaMapper rduCatAreaMapper;
    private CatEstatusCertificadoMapper rduCatEstatusCertificadoMapper;
    private FirmaMapper rduFirmaMapper;
    private FolioMapper rduFolioMapper;
    private CertificadoMapper rduCertificadoMapper;
    private FepsRecibidosMapper fepsRecibidoMapper;
    private ThisControlMapper thisControlMapper;
    private mx.gob.impi.sagpat.persistence.mappers.PersonaMapper personaMapperSagpat;
    private PersonaDirecMapper personaDirecMapper;
    private PersonaPalabraMapper personaPalabraMapper;
    private DocumentoMapper documentoMapper;
    private ExpedienteMapper expedienteMapper;
    private LegajoMapper legajoMapper;
    private DocumentoExpedienteMapper documentoExpedienteMapper;
    private SolicitudMapper solicitudMapper;
    private SolicitudTitularMapper solicitudTitularMapper;
    private SolicitudInventorMapper solicitudInventorMapper;
    private SolicitudApoderadoMapper solicitudApoderadoMapper;
    private SolicitudPrioridadMapper solicitudPrioridadMapper;
    private SolicitudTituloWordMapper solicitudTituloWordMapper;
    private FuncionesSimplesMapper rduFuncionesSimplesMapper;
    private SolicitudReivindicacionMapper solicitudReivindicacionMapper;
    private SolicitudReivindicacionWordMapper solicitudReivindicacionWordMapper;
    private SolicitudDibujosMapper solicitudDibujosMapper;
    private SolicitudResumenUnicodeMapper solicitudResumenUnicodeMapper;
    private SolicitudTituloUnicodeMapper solicitudTituloUnicodeMapper;
    private DiaProcesoMapper diaProcesoMapper;
    private LibroPartesMapper libroPartesMapper;
    private SerieExpedienteMapper serieExpedienteMapper;
    private TipoSolicitudMapper tipoSolicitudMapper;
    private mx.gob.impi.sagpat.persistence.mappers.FepsRecibidosMapper fepsRecibidosMapperSagpat;
    private PalabraIgnorarMapper palabraIgnorarMapper;
    private SolicitudPalabraMapper solicitudPalabraMapper;
    public static final String BUNDLE_RESOURCE = "mx.gob.impi.rdu.i18n.generales";
    public static ResourceBundle MSG_BUNDLE;

    static {
        try {
            MSG_BUNDLE = ResourceBundle.getBundle(BUNDLE_RESOURCE);
        } catch (Exception exception) {
            MSG_BUNDLE = null;
        }
    }

    public void setFepsRecibidosMapperSagpat(mx.gob.impi.sagpat.persistence.mappers.FepsRecibidosMapper fepsRecibidosMapperSagpat) {
        this.fepsRecibidosMapperSagpat = fepsRecibidosMapperSagpat;
    }

    public void setPalabraIgnorarMapper(PalabraIgnorarMapper palabraIgnorarMapper) {
        this.palabraIgnorarMapper = palabraIgnorarMapper;
    }

    public void setSolicitudPalabraMapper(SolicitudPalabraMapper solicitudPalabraMapper) {
        this.solicitudPalabraMapper = solicitudPalabraMapper;
    }

    public void setFepsRecibidosMapper(mx.gob.impi.sagpat.persistence.mappers.FepsRecibidosMapper fepsRecibidosMapper) {
        this.fepsRecibidosMapperSagpat = fepsRecibidosMapper;
    }

    public void setTipoSolicitudMapper(TipoSolicitudMapper tipoSolicitudMapper) {
        this.tipoSolicitudMapper = tipoSolicitudMapper;
    }

    public void setRduFuncionesSimplesMapper(FuncionesSimplesMapper rduFuncionesSimplesMapper) {
        this.rduFuncionesSimplesMapper = rduFuncionesSimplesMapper;
    }

    public void setSerieExpedienteMapper(SerieExpedienteMapper serieExpedienteMapper) {
        this.serieExpedienteMapper = serieExpedienteMapper;
    }

    public void setLibroPartesMapper(LibroPartesMapper libroPartesMapper) {
        this.libroPartesMapper = libroPartesMapper;
    }

    public void setDiaProcesoMapper(DiaProcesoMapper diaProcesoMapper) {
        this.diaProcesoMapper = diaProcesoMapper;
    }

    public void setSolicitudTituloWordMapper(SolicitudTituloWordMapper solicitudTituloWordMapper) {
        this.solicitudTituloWordMapper = solicitudTituloWordMapper;
    }

    public void setSolicitudPrioridadMapper(SolicitudPrioridadMapper solicitudPrioridadMapper) {
        this.solicitudPrioridadMapper = solicitudPrioridadMapper;
    }

    public void setSolicitudApoderadoMapper(SolicitudApoderadoMapper solicitudApoderadoMapper) {
        this.solicitudApoderadoMapper = solicitudApoderadoMapper;
    }

    public void setSolicitudInventorMapper(SolicitudInventorMapper solicitudInventorMapper) {
        this.solicitudInventorMapper = solicitudInventorMapper;
    }

    public void setSolicitudTitularMapper(SolicitudTitularMapper solicitudTitularMapper) {
        this.solicitudTitularMapper = solicitudTitularMapper;
    }

    public void setSolicitudMapper(SolicitudMapper solicitudMapper) {
        this.solicitudMapper = solicitudMapper;
    }

    public void setDocumentoExpedienteMapper(DocumentoExpedienteMapper documentoExpedienteMapper) {
        this.documentoExpedienteMapper = documentoExpedienteMapper;
    }

    public void setLegajoMapper(LegajoMapper legajoMapper) {
        this.legajoMapper = legajoMapper;
    }

    public void setExpedienteMapper(ExpedienteMapper expedienteMapper) {
        this.expedienteMapper = expedienteMapper;
    }

    public void setDocumentoMapper(DocumentoMapper documentoMapper) {
        this.documentoMapper = documentoMapper;
    }

    public void setPersonaPalabraMapper(PersonaPalabraMapper personaPalabraMapper) {
        this.personaPalabraMapper = personaPalabraMapper;
    }

    public void setPersonaDirecMapper(PersonaDirecMapper personaDirecMapper) {
        this.personaDirecMapper = personaDirecMapper;
    }

    public void setPersonaMapperSagpat(PersonaMapper personaMapper) {
        this.personaMapperSagpat = personaMapper;
    }

    public void setThisControlMapper(ThisControlMapper thisControlMapper) {
        this.thisControlMapper = thisControlMapper;
    }

    public void setFepsRecibidoMapper(FepsRecibidosMapper fepsRecibidoMapper) {
        this.fepsRecibidoMapper = fepsRecibidoMapper;
    }

    public void setRduCatEstatusCertificadoMapper(CatEstatusCertificadoMapper rduCatEstatusCertificadoMapper) {
        this.rduCatEstatusCertificadoMapper = rduCatEstatusCertificadoMapper;
    }

    public void setRduCertificadoMapper(CertificadoMapper rduCertificadoMapper) {
        this.rduCertificadoMapper = rduCertificadoMapper;
    }

    public void setRduFolioMapper(FolioMapper rduFolioMapper) {
        this.rduFolioMapper = rduFolioMapper;
    }

    public void setRduFirmaMapper(FirmaMapper rduFirmaMapper) {
        this.rduFirmaMapper = rduFirmaMapper;
    }

    public void setRduCatAreaMapper(CatAreaMapper rduCatArea) {
        this.rduCatAreaMapper = rduCatArea;
    }

    public void setSolicitudTituloUnicodeMapper(SolicitudTituloUnicodeMapper solicitudTituloUnicodeMapper) {
        this.solicitudTituloUnicodeMapper = solicitudTituloUnicodeMapper;
    }

    public void setSolicitudResumenUnicodeMapper(SolicitudResumenUnicodeMapper solicitudResumenUnicodeMapper) {
        this.solicitudResumenUnicodeMapper = solicitudResumenUnicodeMapper;
    }

    public void setSolicitudReivindicacionMapper(SolicitudReivindicacionMapper SolicitudReivindicacionMapper) {
        this.solicitudReivindicacionMapper = SolicitudReivindicacionMapper;
    }

    public void setSolicitudReivindicacionWordMapper(SolicitudReivindicacionWordMapper SolicitudReivindicacionWordMapper) {
        this.solicitudReivindicacionWordMapper = SolicitudReivindicacionWordMapper;
    }

    public void setSolicitudDibujosMapper(SolicitudDibujosMapper solicitudDibujosMapper) {
        this.solicitudDibujosMapper = solicitudDibujosMapper;
    }

    public mx.gob.impi.sagpat.persistence.model.Persona guardaPersonasSagpat(List<mx.gob.impi.rdu.persistence.model.Persona> personas) {

        int total = 0;
        Persona idTitular = null;
        //Se iteran todas las personas
        for (mx.gob.impi.rdu.persistence.model.Persona ap : personas) {

            String codPer = null;
            //buscar persona por su nombre completo y con sintaxi .*
            codPer = this.personaMapperSagpat.selectPersonaCodByDot(ap.getNombrecompleto().trim() + ".*");
            if (codPer == null) {
                //si no existio con sintaxis .* entonces buscarlo SIN
                codPer = this.personaMapperSagpat.selectPersonaCod(ap.getNombrecompleto().trim());
            }

            // si no se encontro bajo ningun criterio entonces es persona nueva, guarda con persona , direccion y personaPalabra
            if (codPer == null) {

                mx.gob.impi.sagpat.persistence.model.Persona persona = new mx.gob.impi.sagpat.persistence.model.Persona();
                persona.setCodDespacho(null);
                //INSERTAR RPG ****************************************
                if (ap.getDomicilioObj() != null && ap.getDomicilioObj().getPais() != null) {
                    persona.setCodPaisNacion(ap.getDomicilioObj().getPais().getClavePais());
                } else {
                    persona.setCodPaisNacion("XX");
                }
                ThisControl ctrl = this.obtenerCodigo();
                persona.setCodPersona(ctrl.getMaxnumber());
                persona.setNomPersona(ap.getNombrecompleto());
                persona.setRowVersion(1L);
                persona.setNumRgp(ap.getRgp() == null ? null : new Long(ap.getRgp()));
                if (ap.getTipoPersona() != null && ap.getTipoPersona().getDescripcion() != null) {
                    persona.setTipPersona(ap.getTipoPersona().getDescripcion().substring(0, 1));//verificar este valor en tabla, los actuales y el prox a insertar******
                }
                total += this.personaMapperSagpat.insert(persona);
                if (ap.getPrincipal() != null) {
                    idTitular = persona;
                }
                //asingar el codigo del id y aplicar esta busqueda para descripcion del producto

                //nombre descomponer en espacios   LISTO
                //eliminar espacios en blanco  LISTO
                //eliminar palabras raras
                //eliminar " "   ,  .  ;
                //eliminar palaras repetidas

                //se obtiene nombre completo sin acentos
                String nombreSin = this.personaMapperSagpat.selectPersonaNombreCaracter(new mx.gob.impi.sagpat.persistence.model.Persona(persona.getNomPersona(), MSG_BUNDLE.getString("patente.translate.acen1"), MSG_BUNDLE.getString("patente.translate.acen2")));

                String persoPal[] = nombreSin.split(" ");
                // String persoPalSaved[] = new String[persoPal.length];
                //persoPalSaved[0]="";
                List<String> saved = new ArrayList<String>();
                Collections.sort(saved);
                String tmpName;
                for (int i = 0; i < persoPal.length; i++) {
                    tmpName = persoPal[i].trim();
                    tmpName = tmpName.replace(",", "");
                    tmpName = tmpName.replace(".", "");
                    tmpName = tmpName.replace(";", "");
                    persoPal[i] = tmpName;
                }

                Arrays.sort(persoPal);

                tmpName = "";
                //se iteran todos las palabras que componen el nombre completo
                for (int i = 0; i < persoPal.length; i++) {
                    //se obtiene una palabra que compone el nombre de la persona
                    tmpName = persoPal[i];
                    //se busca que no exista entre los que ya se guardaron, primera iteracion pasa no lo encuentra
                    // int  position = Arrays.binarySearch(persoPalSaved, tmpName);
                    int position = Collections.binarySearch(saved, tmpName);
                    //si no existe en las que ya se guardaron entonces se crea un nuevo registro
                    if (position < 0) {
                        PersonaPalabra personaPal = new PersonaPalabra();
                        personaPal.setRowVersion(1);
                        personaPal.setCodPalabra(tmpName);
                        personaPal.setCodPersona(persona.getCodPersona());
                        total += this.personaPalabraMapper.insert(personaPal);
                        //se agrega el nombre guardado a la lista de ya persistidos
                        saved.add(tmpName);
                        //persoPalSaved[i]=tmpName;
                        //se ordena para realizar busquedas posteriores
                        Collections.sort(saved);

                    }

                }
                //se guarda la direccion para persona
                PersonaDirec direccion = new PersonaDirec();
                direccion.setCodEstado(null);
                if (ap.getDomicilioObj() != null && ap.getDomicilioObj().getPais() != null) {
                    direccion.setCodPaisResid(ap.getDomicilioObj().getPais().getClavePais());
                } else {
                    direccion.setCodPaisResid("XX");
                }
                direccion.setCodPersona(ctrl.getMaxnumber());
                if (ap.getDomicilioObj() != null) {
                    direccion.setCodPostal(ap.getDomicilioObj().getCodigopostal());
                    direccion.setDirCalle(ap.getDomicilioObj().getCalle());
                    direccion.setDirColonia(ap.getDomicilioObj().getColonia());
                    direccion.setDirPoblacion(ap.getDomicilioObj().getPoblacion() == null ? "" : ap.getDomicilioObj().getPoblacion().toUpperCase());
                    direccion.setNomEstado(ap.getDomicilioObj().getEntidad().getNombre());

                }
                if (ap.getDatosContacto() != null) {
                    direccion.setTelefono(ap.getDatosContacto().getTelefono());
                    direccion.setFax(ap.getDatosContacto().getFax());
                    direccion.setEmail(ap.getDatosContacto().getCorreoelectronico());
                }


                direccion.setDirCodPoblacion(null);//"PATENTES"."PERSONA_DIREC"."DIR_COD_POBLACION" (real: 13, máximo: 6)


                direccion.setRowVersion(1);
                direccion.setSecDireccion(1L);

                //cual es el uso de esta validacion y asignacion
                if (idTitular != null) {
                    //idTitular.setRowVersion(direccion.getSecDireccion());//esta asignacion era por que no se habia agregado el id codDireccion
                    idTitular.setSecDireccion(direccion.getSecDireccion());
                }


                total += this.personaDirecMapper.insert(direccion);

                //si ya existe la persona asignar este codigo a direccion y a persona palabra
            } else if (codPer != null) {

                //si la persona es el principal
                if (ap.getPrincipal() != null) {
                    idTitular = new Persona(new Long(codPer));
                }

                //se obtienes todas las direcciones asociadas a una persona
                List<PersonaDirec> allPersona = this.personaDirecMapper.selectByCodPersona(new Integer(codPer));
                //solamente hasta no iterar la coleccion entera este valor puede cambiar
                boolean esNuevaDireccion = false;

                // settear el valor de secuencia a persona direccion
                //se iteran para identificar si el domicilio recibido ya existe en bd
                for (PersonaDirec per : allPersona) {
                    //se busca domicilio con el codigo de la persona ya en bd, la secuencia de domicilios(1..n), y el "nuevo" domicilio
                    PersonaDirec existe = this.personaDirecMapper.selectByCodPersonaDireccion(new Integer(codPer), per.getSecDireccion(), ap.getDomicilioObj().getCalle());
                    //si existe el domicilio entonces el nombre y la direccion ya esta en bd y no se persiste
                    if (existe != null && idTitular != null) {
                        idTitular.setRowVersion(existe.getSecDireccion());
                        break;
                    } else {//si no existe en esta iteracion puede que lo haya en la siguiente, iterar
                        esNuevaDireccion = true;
                    }
                }

                if (esNuevaDireccion) {
                    //se termina la cantidad de direcciones asociadas a una persona
                    long secuenciaDireccion = allPersona.get(allPersona.size() - 1).getSecDireccion();
                    ++secuenciaDireccion;


                    PersonaDirec direccion = new PersonaDirec();
                    direccion.setCodEstado(null);
                    direccion.setCodPaisResid(ap.getDomicilioObj().getPais().getClavePais());
                    direccion.setCodPersona(new Long(codPer));
                    direccion.setCodPostal(ap.getDomicilioObj().getCodigopostal());
                    direccion.setDirCalle(ap.getDomicilioObj().getCalle());
                    direccion.setDirCodPoblacion(null);//"PATENTES"."PERSONA_DIREC"."DIR_COD_POBLACION" (real: 13, máximo: 6)
                    direccion.setRowVersion(1);
                    direccion.setSecDireccion(secuenciaDireccion);

                    if (ap.getDomicilioObj() != null) {
                        direccion.setDirColonia(ap.getDomicilioObj().getColonia());
                        direccion.setDirPoblacion(ap.getDomicilioObj().getPoblacion() == null ? "" : ap.getDomicilioObj().getPoblacion().toUpperCase());
                        direccion.setNomEstado(ap.getDomicilioObj().getEntidad().getNombre());
                    }

                    if (ap.getDatosContacto() != null) {
                        direccion.setTelefono(ap.getDatosContacto().getTelefono());
                        direccion.setFax(ap.getDatosContacto().getFax());
                        direccion.setEmail(ap.getDatosContacto().getCorreoelectronico());
                    }



                    if (idTitular != null) {
                        idTitular.setSecDireccion(direccion.getSecDireccion());
                    }
                    total += this.personaDirecMapper.insert(direccion);

                }


            }




        }



        return idTitular;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManagerSagpat", rollbackFor = Exception.class)
    public String[] generarFoliosSagpat(String expedienteDivisional, Date hoy) throws Exception {

        String[] foliosSeries = new String[4];
        Integer serieDoc = null;
        Integer serieExp = null;
        
        String serieDocExp = this.diaProcesoMapper.obtenerSerieDocExp(new DiaProceso(hoy, expedienteDivisional));

        String docExp[] = serieDocExp.split("-");

        if (docExp != null && docExp.length != 2) {
            throw new Exception("No existe este día como hábil. Comuníquese al IMPI", new Exception());
        }

        try {
            serieExp = new Integer(docExp[0]);
            serieDoc = new Integer(docExp[1]);

            foliosSeries[0] = docExp[0];
            foliosSeries[1] = docExp[1];


        } catch (Exception e) {
            throw new Exception("No existe este día como hábil. Comuníquese al IMPI", e);
        }



        Long numUltimo = this.libroPartesMapper.obtenerNumUltimoFolio("MX", serieDoc.toString() + "", "E");

        if (new Long(numUltimo) < 2L) {
            throw new Exception("No es posible realizar la recepción del documento. Comuníquese al IMPI .", new Exception());
        }

        //Se obtiene FEC_PROCESO del Libro de Partes de Entrada abierto
        Date fechaProceso = this.libroPartesMapper.obtenerFechaProceso("MX", serieDoc.toString(), "E");
        if (fechaProceso == null) {//si esta cerrado el libro
            //Se obtiene la FEC_PROCESO del último Libro de Partes de Entrada cerrado
            fechaProceso = this.libroPartesMapper.obtenerFechaProcesoLibroCerrado("MX", serieDoc.toString(), "E");
            if (fechaProceso == null) {
                throw new Exception("No existe un Libro de Partes de Entrada en el sistema. Comuníquese al IMPI", new Exception());
            }
        }


        Calendar fp = Calendar.getInstance();
        fp.setTime(fechaProceso);
        fp.set(Calendar.SECOND, 0);
        fp.set(Calendar.HOUR, 0);
        fp.set(Calendar.HOUR_OF_DAY, 0);
        fp.set(Calendar.MINUTE, 0);
        fp.set(Calendar.MILLISECOND, 0);

        Calendar today = Calendar.getInstance();
        today.setTime(hoy);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.MILLISECOND, 0);


        String primerNumFolio = "0";
        if (fp.compareTo(today) == 0) {
            //Si libro de partes del today se cerro anticipadamente, entonces se reabre
            int rows = this.libroPartesMapper.reabrirLibroPartes(new LibroPartes("MX", today.getTime(), "E")); //REALIZAR COMMIT

            //  Se verifica en libro de parte abierto primer número de folio como NO NULO
            primerNumFolio = this.libroPartesMapper.verificaPrimerNumeroFolio(new LibroPartes("MX", fp.getTime(), "E"));

            if (primerNumFolio == null) {
                throw new Exception("No existe un Libro de Partes de Entrada en el sistema. Comuníquese al IMPI", new Exception());
            }


        } else if (fp.compareTo(today) < 0) {  // A
            //Cerrar LIBRO_PARTES de WVdtFEC_PROCESO y abrir el día WVdtDiahábilRDU
//            Date fechaProc = this.libroPartesMapper.cerrarLibroSelectFecha(new LibroPartes("MX", fp.getTime(), "E"));
//            if (fechaProc != null) {
//                //' El Libro de Partes de Entrada de WVdtFEC_PROCESO esta abierto
//                // ' Se verifica que existan documentos ingresados para la WVdtFEC_PROCESO
//                //String fpTime =  Util.formatearFecha(fp.getTime(), Util.FORMATODDMMYYYY);
//                String minNum = this.documentoMapper.selectMinNumDocum(new Documento("MX", fp.getTime(), "E"));
//                //String minNum ="2";
//                String maxNum = this.documentoMapper.selectMaxNumDocum(new Documento("MX", fp.getTime(), "E"));
//                //   String maxNum="22222";
//                if(minNum == null | maxNum ==null){//******************
//                    throw new Exception("No existen documentos ingresados para la fecha de proceso. Comuníquese al IMPI", new Exception());
//                }
//
//            }
            //' Si esta abierto se cierra LIBRO_PARTES de WVdtFEC_PROCESO
            int total = this.libroPartesMapper.cerrarLibroPartes(new LibroPartes("MX", fp.getTime(), "E"));
            // EJECUTAR COMMIT

            //! calculo el primer número de folio a generar en el siguiente libro abierto
            String numPrimero = this.libroPartesMapper.generarNumFolio("MX", serieDoc.toString(), "E");

            //Abrir LIBRO_PARTES
            int tot = this.libroPartesMapper.actualizarNumFolio(new LibroPartes("MX", today.getTime(), "E", new Integer(numPrimero)));
            //ejecutar commit


            //' Se asigna WVdtDiahábilRDU como WVdtFEC_PROCESO
            fp = today;



        }

        //! Se genera numero de expediente para el tipo de expediente
        int totalUpdate = this.libroPartesMapper.generaFolioGeneralEntrada(new LibroPartes("MX", fp.getTime(), "E"));
        //

        //! si quedo en nulo lo actualizamos con el NUM_PRIMERO
        int num = this.libroPartesMapper.actualizarNumUltimoIfNulo(new LibroPartes("MX", fp.getTime(), "E"));


        //! Se genera numero de expediente para el tipo de expediente
        num = this.serieExpedienteMapper.generaNumeroExpediente(new SerieExpediente("MX", new Integer(serieExp), expedienteDivisional));


        //! Leemos folio general de entrada
        String numeroDocumento = this.libroPartesMapper.generarFolioGeneralEntrada(new LibroPartes("MX", fp.getTime(), "E"));
        foliosSeries[3] = numeroDocumento;
        //Leemos numero de expediente para el tipo de expediente
        String numeroExpediente = this.serieExpedienteMapper.seleccionaNumeroExpediente(new SerieExpediente("MX", new Integer(serieExp), expedienteDivisional));
        foliosSeries[2] = numeroExpediente;
        
        return foliosSeries;
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManagerSagpat", rollbackFor = Exception.class)
    public int copiarSolicitudEnSagpat(TramitePatente patente, int paginas) throws Exception {



        List<ThisControl> cods = new LinkedList<ThisControl>();

        List<Persona> allPersonas = new ArrayList<Persona>();

        allPersonas.addAll(patente.getApoderados() == null ? Collections.EMPTY_LIST : patente.getApoderados());
        allPersonas.addAll(patente.getInventores() == null ? Collections.EMPTY_LIST : patente.getInventores());
        allPersonas.addAll(patente.getPersonasNot() == null ? Collections.EMPTY_LIST : patente.getPersonasNot());
        allPersonas.addAll(patente.getSolicitantes() == null ? Collections.EMPTY_LIST : patente.getSolicitantes());

//        for (int i = 0; i < nums; i++) {
//            ThisControl ctrl = this.thisControlMapper.selectByPrimaryKey("PERSONA");
//            //long a =ctrl.getMaxnumber();
//            ctrl.setMaxnumber(ctrl.getMaxnumber() + 1L);
//            this.thisControlMapper.updateByVariable(ctrl);
//            cods.add(ctrl);
//        }
        //

        // VALIDAR QUE NO EXISTAN EN BD FIRST   ************************************************+

        Persona codTitular = guardaPersonasSagpat(patente.getSolicitantes() == null ? Collections.EMPTY_LIST : patente.getSolicitantes());
        Persona codApoderado = guardaPersonasSagpat(patente.getApoderados() == null ? Collections.EMPTY_LIST : patente.getApoderados());
        //Persona codPersonaNot =   guardaPersonasSagpat(patente.getPersonasNot() == null ? Collections.EMPTY_LIST : patente.getPersonasNot());
        Persona codInventor = guardaPersonasSagpat(patente.getInventores() == null ? Collections.EMPTY_LIST : patente.getInventores());







        int total = 0;

        //total = total == (allPersonas.size() * 3) ? 3 : 0;

        //select * from PALABRA_IGNORAR p;  -- no es visible FUNCION  GFbCargarPalabraIgnorar

        String subtipoSolicitudCode = patente.getSubTipoSol() == null ? null : patente.getSubTipoSol().getDescripcion().substring(0, 1);
        TipoSolicitud tipoSol = null;
        if (subtipoSolicitudCode != null) {
            tipoSol = this.tipoSolicitudMapper.selectByTipExp(patente.getTipoExpediente());
        } else {
            tipoSol = new TipoSolicitud("PAT");
        }

        //Charset ASCII_CHARSET = Charset.forName("US-ASCII");

        Date fechRdu = new Date();

        Documento doc = new Documento();
        doc.setCodOficina("MX");
        doc.setTipLibro("E");
        doc.setFecProceso(fechRdu);// COMO SE OBTIENE SI ESTA ABIERTO O CERRADO PROCESO--- LIBRO_PARTES
        doc.setFecPresentacion(fechRdu);
        doc.setFecRecepcion(fechRdu);
        doc.setSerDocum(new Integer(patente.getFoliosSeries()[1]));
        doc.setNumDocum(new Long(patente.getFoliosSeries()[3]));
//++doc.setSerExped(new Integer(patente.getFoliosSeries()[0]));
        doc.setTipSolicitud(tipoSol == null ? null : tipoSol.getTipSolicitud());//*******************PENDIENTE 4A QRY ARROJA MAS DE UN RESULTADO
//++doc.setTipExped(patente.getTipoExpediente());//***********************PENDIENTE   CONSTANTE f
        doc.setSubtipSolicitud(patente.getSubTipoSol() == null ? null : patente.getSubTipoSol().getDescripcion().substring(0, 1));//***********************PENDIENTE  4A QRY ARROJA MAS DE UN RESULTADO
//++doc.setNumExped(new Long(patente.getFoliosSeries()[2]));
        doc.setNumPaginas(paginas);
        doc.setTipEntrada("SC");
        doc.setIndDepositoMatBiologico(null);
        doc.setCodOficinaReceptora((short) 7);
        doc.setIndCapturaGlosa("1");
        doc.setIndCapturaDatosProm("1");
        doc.setIndCapturaDatos("0");
        doc.setRowVersion(1);
        doc.setObs("Expediente Divisional y su Fecha de Presentacion :" + patente.getExpDivisional() + " : " + Util.formatearFecha(patente.getFechaPresentacionExp(), Util.FORMATODDMMYYYY));
        total += this.documentoMapper.insert(doc);// NO SE PUDO INSERTAR



        //String tituloAscii = new String(patente.getInvencion().getBytes(ASCII_CHARSET),ASCII_CHARSET);
        byte[] utf8Bytes = patente.getInvencion().getBytes("UTF8");
        String roundTrip = new String(utf8Bytes, "UTF8");
        Expediente exp = new Expediente();
        exp.setRowVersion(1);
        exp.setCodLey((short) 3);
        exp.setTitulo(roundTrip);//***********************PENDIENTE  X2
        exp.setCodPersonaTit1(patente.getSolicitantes().get(0).getIdSolicitante());
        exp.setCodPersonaPro(patente.getApoderados().get(0).getIdSolicitante());
        exp.setCodOficina("MX");
        exp.setTipExped(patente.getTipoExpediente());//***********************PENDIENTE  CONTANTE f
        exp.setSerExped(new Integer(patente.getFoliosSeries()[0]));//**********************PENDIENTE  3A
        exp.setNumExped(new Long(patente.getFoliosSeries()[2]));//*********************PENDIENTE  5A PROBAR QRY
        exp.setCodOrigenExpediente(null);//**************PENDIENTE   NO SE ESPECIFICA COMO SE OBTIENE
        exp.setTipSolicitud(tipoSol == null ? "PAT" : tipoSol.getTipSolicitud());//***************PENDIENTE  4A QRY ARROJA MAS DE UN RESULTADO
//++exp.setTipLibro("E");
//++exp.setSerDocum( new Long(patente.getFoliosSeries()[1]));//*****************PENDIENTE  1A
//++exp.setNumDocum(new Long(patente.getFoliosSeries()[3]));//****************PENDIENTE  2A
        exp.setSubtipSolicitud(patente.getSubTipoSol() == null ? null : patente.getSubTipoSol().getDescripcion().substring(0, 1));
        exp.setFecPresentacion(patente.getFechaPresentacionExp());
        total += this.expedienteMapper.insert(exp);

        DocumentoExpediente docExpediente = new DocumentoExpediente();
        docExpediente.setRowVersion(1);
        docExpediente.setCodOficinaDocum("MX");
        docExpediente.setTipLibro("E");
        docExpediente.setSerDocum(doc.getSerDocum());//*******************PENDIENTE   1A
        docExpediente.setTipExped(patente.getTipoExpediente());//*********************PENDIENTE
        docExpediente.setSerExped(new Integer(patente.getFoliosSeries()[0]));//********************PENDIENTE  3A
        docExpediente.setNumExped(new Long(patente.getFoliosSeries()[2]));//*******************PENDIENTE  5A PROBAR QRY
        docExpediente.setNumDocum(doc.getNumDocum());//******************PENDIENTE  2A
        docExpediente.setCodOficinaExped("MX");

        total += this.documentoExpedienteMapper.insert(docExpediente);
        //ACTUALIZA DOCUMENTO

        Documento docUp = this.documentoMapper.selectByPrimaryKey("MX", doc.getNumDocum().intValue(), doc.getSerDocum().shortValue(), doc.getTipLibro());
        docUp.setSerExped(new Integer(patente.getFoliosSeries()[0]));
        doc.setSerExped(docUp.getSerExped());
        docUp.setTipExped(patente.getTipoExpediente());
        doc.setTipExped(docUp.getTipExped());
        docUp.setNumExped(new Long(patente.getFoliosSeries()[2]));
        doc.setNumExped(docUp.getNumExped());
        this.documentoMapper.updateByPrimaryKey(docUp);

        //ACTUALIZA EXPEDIENTE
        Expediente expUp = this.expedienteMapper.selectByPrimaryKey("MX", new Integer(patente.getFoliosSeries()[2]), exp.getSerExped().shortValue(), exp.getTipExped());
        expUp.setTipLibro("E");
        exp.setTipLibro("E");
        expUp.setSerDocum(new Long(patente.getFoliosSeries()[1]));
        exp.setSerDocum(expUp.getSerDocum());
        expUp.setNumDocum(new Long(patente.getFoliosSeries()[3]));
        exp.setNumDocum(expUp.getNumDocum());
        this.expedienteMapper.updateByPrimaryKey(expUp);


























        Legajo legajo = new Legajo();
        legajo.setRowVersion(1);
        legajo.setCodOficina(doc.getCodOficina());
        legajo.setTipExped(doc.getTipExped());//*********************PENDIENTE
        legajo.setSerExped(doc.getSerExped());//*********************PENDIENTE  3A
        legajo.setNumExped(doc.getNumExped());//*******************PENDIENTE 5A PROBAR QRY
        legajo.setSecLegajo((short) 0);
        legajo.setDesLegajo("0");
        legajo.setCodOficinaReceptora((short) 7);
        total += this.legajoMapper.insert(legajo);


        Solicitud solicitud = new Solicitud();
        solicitud.setRowVersion(1);
        solicitud.setCodOficina("MX");
        solicitud.setTipExped(doc.getTipExped());//********************PENDIENTE
        solicitud.setSerExped(doc.getSerExped());//********************PENDIENTE 3A
        solicitud.setNumExped(doc.getNumExped());//*********************PENDIENTE   5A PROBAR QRY
        solicitud.setTipLibro("E");
        solicitud.setSerDocum(doc.getSerDocum());//*******************PENDIENTE  1A
        solicitud.setNumDocum(doc.getNumDocum());//****************PENDIENTE 2A
        Calendar c = Calendar.getInstance();
        c.setTime(patente.getSysDate());
        c.set(Calendar.SECOND, 0);
        solicitud.setFecRecepcion(c.getTime());

        solicitud.setFecPresentacion(patente.getFechaPresentacionExp() == null ? new Date() : patente.getFechaPresentacionExp());

        solicitud.setTipSolicitud(tipoSol == null ? "PAT" : tipoSol.getTipSolicitud()); //doc.getTipSolicitud());//************PENDIENTE   4A QRY ARROJA MAS DE UN RESULTADO


        solicitud.setSubtipSolicitud(patente.getSubTipoSol().getDescripcion().substring(0, 1));//  patente.getSubTipoSol().getDescripcion());
        solicitud.setCodLey((short) 3);
        solicitud.setTitulo(patente.getInvencion());//*******************PENDIENTE cesar    X2

        solicitud.setCodPersonaTit1(codTitular.getCodPersona());//****************************guardar solo titular VALIDAR
        solicitud.setSecDireccionTit1(codTitular.getSecDireccion());


        solicitud.setCodPersonaPro(codApoderado.getCodPersona());
        solicitud.setSecDireccionPro(codApoderado.getSecDireccion());




        solicitud.setTipSolicitante(patente.getTipoSol().getIdTiposolicitud().shortValue());//****************PENDIENTE   CATALOGO del SAGPAT: TIPO_SOLICITANTE


        solicitud.setIndTitulInven(patente.getSolicitantes().get(0).getInventor() == null ? "N" : patente.getSolicitantes().get(0).getInventor() == 1 ? "S" : "N");//************PENDIENTE CESAR   ES INVENTOR



        solicitud.setCodOficinaDividida(null);// patente.getExpDivisional());//****************PENDIENTE CESAR   El expediente Divisional se enviará como parte de las OBSERVACIONES
        solicitud.setTipExpedDividida(null);// patente.getExpDivisional());//****************PENDIENTE CESAR  El expediente Divisional se enviará como parte de las OBSERVACIONES
        solicitud.setSerExpedDividida(null);//****************PENDIENTE CESAR  El expediente Divisional se enviará como parte de las OBSERVACIONES
        solicitud.setNumExpedDividida(null);//****************PENDIENTE CESAR  El expediente Divisional se enviará como parte de las OBSERVACIONES
        solicitud.setUserIdCapt(null);//
        solicitud.setFecCapt(new Date());
        solicitud.setIdSolicitudPct(null);
        solicitud.setFecSolicitudPct(null);
        solicitud.setIdPublPct(null);
        solicitud.setFecPublPct(null);
        solicitud.setTipPublPct(null);
        solicitud.setTituloIngles(null);
        //solicitud.setFec1erPrioridad(  patente.getPrioridades() == null ?null : patente.getPrioridades().size() >0 ? patente.getPrioridades().get(0).getFechaPresentacionExt(): null);
        solicitud.setFec1erPrioridad(null);
        solicitud.setFecDivulgacion(patente.getFechaDivulgacion());
        solicitud.setUserIdCapt(patente.getIdUsuarioFirmante().intValue());
        //solicitud.setFasePct(new Short(patente.getFaseSolPCT())); //************PENDIENTE CESAR


        solicitud.setCodOficinaReceptora((short) 7);
        total += solicitudMapper.insert(solicitud);

        //

        String invencionSin = this.personaMapperSagpat.selectPersonaNombreCaracter(new mx.gob.impi.sagpat.persistence.model.Persona(patente.getInvencion(), MSG_BUNDLE.getString("patente.translate.acen1"), MSG_BUNDLE.getString("patente.translate.acen2")));
        String allInvencion[] = invencionSin.split(" ");
        List<String> allInvencionSaved = new ArrayList<String>();
        String tmpInvencion;
        for (int i = 0; i < allInvencion.length; i++) {
            tmpInvencion = allInvencion[i].trim();
            tmpInvencion = tmpInvencion.replace(",", "");
            tmpInvencion = tmpInvencion.replace(".", "");
            tmpInvencion = tmpInvencion.replace(";", "");
            allInvencion[i] = tmpInvencion;
        }
        //obtiene la lista de palabras a ignorar
        List<PalabraIgnorar> allPalabras = palabraIgnorarMapper.selectAll("");
        //elimina la palabra que hacen match con las de la tabla
        for (int i = 0; i < allInvencion.length; i++) {
            if (allPalabras.contains(allInvencion[i])) {
                allInvencion[i] = null;
            }
        }
        Arrays.sort(allInvencion);
        //guardar solo las palabras que no esten repetidas
        for (int i = 0; i < allInvencion.length; i++) {
            tmpInvencion = allInvencion[i];

            int position = Collections.binarySearch(allInvencionSaved, tmpInvencion);

            if (position < 0) {
                SolicitudPalabra solicitudPalabra = new SolicitudPalabra();
                solicitudPalabra.setCodOficina("MX");
                solicitudPalabra.setCodPalabra(tmpInvencion);
                solicitudPalabra.setCodUso(null);
                solicitudPalabra.setNumExped(doc.getNumExped());
                solicitudPalabra.setRowVersion(1);
                solicitudPalabra.setSerExped(doc.getSerExped());
                solicitudPalabra.setTipExped(doc.getTipExped());
                // this.solicitudPalabraMapper.insert(solicitudPalabra);
                allInvencionSaved.add(tmpInvencion);
                Collections.sort(allInvencionSaved);
            }


        }


        Charset UTF8_CHARSET = Charset.forName("UTF-8");




        SolicitudTitular solTitular = new SolicitudTitular();
        solTitular.setRowVersion(1);
        solTitular.setCodOficina("MX");
        solTitular.setTipExped(doc.getTipExped());//******************PENDIENTE
        solTitular.setSerExped(doc.getSerExped());//  3A
        solTitular.setNumExped(doc.getNumExped());//***************PENDIENTE
        solTitular.setCodPersona(codTitular == null ? null : codTitular.getCodPersona());//***************PENDIENTE WHICH
        solTitular.setSecDireccion(codTitular == null ? null : codTitular.getRowVersion());//*************PENDIENTE WHICH
        solTitular.setObsParticipacion(null);
        total += this.solicitudTitularMapper.insert(solTitular);

        SolicitudInventor solInventor = new SolicitudInventor();
        solInventor.setRowVersion(1);
        solInventor.setCodOficina("MX");
        solInventor.setTipExped(doc.getTipExped());//************PENDIENTE
        solInventor.setSerExped(doc.getSerExped());//*************PENDIENTE  3A
        solInventor.setNumExped(doc.getNumExped());  //   5A PROBAR QRY
        solInventor.setCodPersona(codInventor == null ? null : codInventor.getCodPersona());//**************PENDINETE WHICH
        solInventor.setSecDireccion(codInventor == null ? null : codInventor.getRowVersion());//*************PENDIENTE WHICH
        //total += this.solicitudInventorMapper.insert(solInventor);

        SolicitudApoderado solApoderado = new SolicitudApoderado();
        solApoderado.setRowVersion(1);
        solApoderado.setCodOficina("MX");
        solApoderado.setTipExped(doc.getTipExped());//*******************PENDIENTE
        solApoderado.setSerExped(doc.getSerExped());//*************PENDIENTE  3A
        solApoderado.setNumExped(doc.getNumExped());//***************PENDIENTE 5A PROBAR QRY
        solApoderado.setCodPersona(codApoderado == null ? null : codApoderado.getCodPersona());//*************PENDIENTE WHICH
        solApoderado.setSecDireccion(codApoderado == null ? null : codApoderado.getRowVersion());//***********PENDIENTE WHICH
        total += this.solicitudApoderadoMapper.insert(solApoderado);

        SolicitudPrioridad solPrioridad = new SolicitudPrioridad();
        solPrioridad.setRowVersion(1);
        solPrioridad.setCodOficina("MX");
        solPrioridad.setTipExped(doc.getTipExped());//*************PENDIENTE
        solPrioridad.setSerExped(doc.getSerExped());//************PENDIENTE   3A
        solPrioridad.setNumExped(doc.getNumExped()); // 5A PROBAR QRY
        solPrioridad.setCodPais("MX");

        solPrioridad.setIdSolicitud("29/113,582"); //patente.getSubTipoSol().getIdSubtiposolicitud().toString());//PENDIENTE HOW ALGORITMO**********************
        solPrioridad.setFecSolicitud(patente.getFechaSolPCT());
        solPrioridad.setReconocida(null);//
        solPrioridad.setReconocida(null);
        total += this.solicitudPrioridadMapper.insert(solPrioridad);

        SolicitudTituloWord solTitWord = new SolicitudTituloWord();
        solTitWord.setRowVersion(1);
        solTitWord.setCodOficina("MX");
        solTitWord.setTipExped(doc.getTipExped());//*************PENDIENTE
        solTitWord.setSerExped(doc.getSerExped());//*************PENDIENTE   3A
        solTitWord.setNumExped(doc.getNumExped());//************PENDIENTE // 5A PROBAR QRY
        solTitWord.setCodPais("MX");
        solTitWord.setLargo(null);
        solTitWord.setTituloWord(patente.getInvencion());
        total += this.solicitudTituloWordMapper.insert(solTitWord);


        if (patente.getResumen() != null && new LanguageIdentifier(patente.getResumen()).getLanguage().equals("es")) {
            SolicitudResumenUnicode solicitudResumenUnicode = new SolicitudResumenUnicode();
            solicitudResumenUnicode.setCodOficina("MX");
            solicitudResumenUnicode.setCodPais("MX");
            solicitudResumenUnicode.setNumExped(doc.getNumExped());
            String rsm = new String(patente.getResumen().getBytes(UTF8_CHARSET), UTF8_CHARSET);
            solicitudResumenUnicode.setResumenUnicode(rsm);//pendinee +++++++++++++++++++
            solicitudResumenUnicode.setRowVersion(1);
            solicitudResumenUnicode.setSerExped(doc.getSerExped());
            solicitudResumenUnicode.setTipExped(doc.getTipExped());
            total += this.solicitudResumenUnicodeMapper.insert(solicitudResumenUnicode);
        }

        if (patente.getInvencion() != null && new LanguageIdentifier(patente.getInvencion()).getLanguage().equals("es")) {
            SolicitudTituloUnicode solicitudTituloUnicode = new SolicitudTituloUnicode();
            solicitudTituloUnicode.setCodOficina("MX");
            solicitudTituloUnicode.setCodPais("MX");
            solicitudTituloUnicode.setNumExped(doc.getNumExped());
            solicitudTituloUnicode.setRowVersion(1);
            solicitudTituloUnicode.setSerExped(doc.getSerExped());
            solicitudTituloUnicode.setTipExped(doc.getTipExped());
            String titulo = new String(patente.getInvencion().getBytes(UTF8_CHARSET), UTF8_CHARSET);
            solicitudTituloUnicode.setTituloUnicode(titulo);// pendiente *******************
            total += this.solicitudTituloUnicodeMapper.insert(solicitudTituloUnicode);
        }



        mx.gob.impi.sagpat.persistence.model.FepsRecibidos feps = new mx.gob.impi.sagpat.persistence.model.FepsRecibidos();
        feps.setCveAreaDestino(null);
        feps.setCveOficinaRecepcion(null);
        feps.setCveSistema(null);
        feps.setFechaModificacion(new Date());
        feps.setFolioFeps(new Long(patente.getPago().getFoliopago()));
        feps.setTitle(null);
        feps.setUsuarioRecepcion(null);
        this.fepsRecibidosMapperSagpat.insert(feps);


        for (Reivindicacion rein : Util.checkListNull(patente.getReivindicaciones())) {

            SolicitudReivindicacion solicitudReivindicacion = new SolicitudReivindicacion();
            solicitudReivindicacion.setCodOficina("MX");
            solicitudReivindicacion.setNumExped(doc.getNumExped());
            solicitudReivindicacion.setReivindicacion(rein.getDescripcion()); //PENDIENTE++++++++++++++++
            solicitudReivindicacion.setRowVersion(1);
            solicitudReivindicacion.setSecReivindicacion(rein.getOrden().shortValue());
            solicitudReivindicacion.setSerExped(doc.getSerExped());
            solicitudReivindicacion.setTipExped(doc.getTipExped());
            total += this.solicitudReivindicacionMapper.insert(solicitudReivindicacion);

            SolicitudReivindicacionWord solicitudReivindicacionWord = new SolicitudReivindicacionWord();
            solicitudReivindicacionWord.setCodOficina("MX");
            solicitudReivindicacionWord.setSecReivindicacion(rein.getOrden().shortValue());//CAMPO CHECHAR
            solicitudReivindicacionWord.setLargo(rein.getDescripcion().getBytes().length);//PENDIENTE QUE TIPO DE DATO   ************************
            solicitudReivindicacionWord.setNumExped(doc.getNumExped());

            solicitudReivindicacionWord.setReivindicacionWord(rein.getDescripcion());// PENDIENTE CAMPO CHECHAR
            solicitudReivindicacionWord.setRowVersion(1);
            solicitudReivindicacionWord.setSerExped(doc.getSerExped());
            solicitudReivindicacionWord.setTipExped(doc.getTipExped());
            this.solicitudReivindicacionWordMapper.insert(solicitudReivindicacionWord);

        }




        //FileUtils.readFileToString(new File());

        for (ImagenDibujo img : Util.checkListNull(patente.getImagenes())) {
            SolicitudDibujos solicitudDibujos = new SolicitudDibujos();
            solicitudDibujos.setCodGaceta(null);
            solicitudDibujos.setCodOficina("MX");
            solicitudDibujos.setDibujo(Util.encodeObject(img.getArchivo()));//pendiente tranformar a Strin byteArray *********************
            solicitudDibujos.setFechaCarga(patente.getFechaCaptura());//captura SYSDATE++++++++
            solicitudDibujos.setNumConcesion(null);
            solicitudDibujos.setNumExped(doc.getNumExped());
            solicitudDibujos.setRowVersion(1);
            solicitudDibujos.setSecDibujos(img.getOrden().intValue());//PENDIENTE CHECHAR
            solicitudDibujos.setSerExped(doc.getSerExped());
            solicitudDibujos.setTipExped(doc.getTipExped());
            total += this.solicitudDibujosMapper.insert(solicitudDibujos);

        }



        return total;
    }

    public ThisControl obtenerCodigo() {

        ThisControl ctrl = this.thisControlMapper.selectByPrimaryKey(new ThisControl("PERSONA"));
        //long a =ctrl.getMaxnumber();
        ctrl.setMaxnumber(ctrl.getMaxnumber() + 1L);
        this.thisControlMapper.updateByVariable(ctrl);
        return ctrl;
    }

//    public String determinaPaisColonia(String paisColonia){
//        if(paisColonia!= null && paisColonia.equals("MX")){
//            return Constantes.FIRMA_COL;
//        }else{
//            return "";
//        }
//    }
    //
    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManagerSagpat", rollbackFor = Exception.class)
    public String validatePhrase(String tmp) throws Exception {
        String val = this.personaMapperSagpat.selectPersonaNombreCaracter(new mx.gob.impi.sagpat.persistence.model.Persona(tmp, MSG_BUNDLE.getString("patente.translate.acen1"), MSG_BUNDLE.getString("patente.translate.acen2")));
        return val;
    }

    
    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManagerIngresos", rollbackFor = Exception.class)
    public int insertFeps(TramiteDto t) {


        FepsRecibidos fep = new FepsRecibidos();
        fep.setCveAreaDestino((short) 1);
        fep.setCveOficinaRecepcion((short) 0);
        fep.setCveSistema((short) 9);
        fep.setFechaRecepcion(t.getFechaSysdate());
        try {
            fep.setFolioFeps(new Long(t.getPagoDto().getFoliopago()));
        } catch (NumberFormatException e) {
        }
        String a = t.getFirma().getFolio().split("/")[3];
        fep.setTitle(Constantes.PREFIX_FOLIO + Calendar.getInstance().get(Calendar.YEAR) + "/" + a);

        return this.fepsRecibidoMapper.insert(fep);

    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManagerIngresos", rollbackFor = Exception.class)
    public int insertFepsSagpat(TramitePatente t) {


        FepsRecibidos fep = new FepsRecibidos();
        fep.setCveAreaDestino((short) 2);
        fep.setCveOficinaRecepcion((short) 0);
        fep.setCveSistema((short) 9);
        fep.setFechaRecepcion(t.getPago().getFechapago());
        try {
            fep.setFolioFeps(new Long(t.getPago().getFoliopago()));
        } catch (NumberFormatException e) {
        }

        fep.setTitle(t.getFolioFirma());
        return this.fepsRecibidoMapper.insert(fep);

    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManagerIngresos", rollbackFor = Exception.class)
    public FepsRecibidos selectFepsByFolio(Long folio) {
        return fepsRecibidoMapper.selectFepsByFolio(folio);
    }

//    public synchronized Folio getDinamicFolio(Folio folio) {
//        Folio f = this.rduFolioMapper.selectDynamicFolio(folio);
//        if (f != null) {
//            long tmpFolio = f.getFolio();
//            f.setFolio(tmpFolio + 1L);
//            if (this.rduFolioMapper.updateByPrimaryKeySelective(f) > 0) {
//                f.setFolio(tmpFolio);
//            } else {
//                return null;
//            }
//        }
//        return f;
//    }
    public Long insertFirma(Firma firma) {
        if (rduFirmaMapper.insert(firma) > 0) {
            return firma.getIdFirma();
        } else {
            return null;
        }
    }

    public Firma loadFirma(Long idFirma) {
        return this.rduFirmaMapper.selectByPrimaryKey(idFirma);
    }

    public Integer updateFirma(Firma firma) {
        return this.rduFirmaMapper.updateByPrimaryKeyWithBLOBs(firma);
    }

    public Integer updateSelectiveFirma(Firma firma) {
        return rduFirmaMapper.updateByPrimaryKeySelective(firma);
    }

    public int insertFirmaAdmin(Certificado firmaAdmin) {
        return this.rduCertificadoMapper.insert(firmaAdmin);
    }

    public int updateFirmaAdmin(Certificado firmaAdmin) {
        return this.rduCertificadoMapper.updateByPrimaryKey(firmaAdmin);
    }

    public Certificado selectFirmaAdmin(Certificado firmaAdmin) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Certificado selectFirmaAdminByEstatus(Certificado estatus) {
        try {
            Certificado cer = this.rduCertificadoMapper.selectByEstatusArea(estatus);
            cer.setCatArea(this.rduCatAreaMapper.selectByPrimaryKey(cer.getIdCatArea()));
            cer.setCatEstatusCertificado(this.rduCatEstatusCertificadoMapper.selectByPrimaryKey(cer.getIdEstatusCertificado()));
            return cer;
        } catch (Exception e) {
            lger.error("Ocurrio un error en el metodo RduFirmaServiceImpl.selectFirmaAdminByEstatus: ", e);
            return null;
        }
    }

    public List<Certificado> loadAllActiveCerts(Integer idArea) {

        List<Certificado> allActive = this.rduCertificadoMapper.loadAllActiveCerts(idArea);

        for (Certificado cer : mx.gob.impi.rdu.util.Util.checkListNull(allActive)) {
            cer.setCatEstatusCertificado(this.rduCatEstatusCertificadoMapper.selectByPrimaryKey(cer.getIdEstatusCertificado()));
            cer.setCatArea(this.rduCatAreaMapper.selectByPrimaryKey(cer.getIdCatArea()));
        }
        return allActive;
    }

   public List<Firma> selectFirmas() {
        try {
            return this.rduFirmaMapper.selectFirma();
        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
        return null;
    }

    public int udapteFirmasEnvio(Firma firma) {
        int actualizados = 0;
        try {
            actualizados = this.rduFirmaMapper.updateByPrimaryKeySelective(firma);
        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
        return actualizados;
    }

    public Firma obternerFirmaByTramite(Long idTramite) {
        return rduFirmaMapper.selectByTramite(idTramite);
    }
    
    public Firma obternerFirmaByExpediente(Long expediente, String expedienteSag){
        return rduFirmaMapper.selectByExpediente(expediente, expedienteSag);
    }
}
