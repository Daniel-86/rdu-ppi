/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util;

import com.philvarner.clamavj.ClamScan;
import com.philvarner.clamavj.ScanResult;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author oracle
 */
public class AnexosUtil {
    
     private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.parametros";
     final ResourceBundle bundleParametros = ResourceBundle.getBundle(BUNDLE_PARAMETROS);
     private Logger logger = Logger.getLogger(this.getClass());
    
     public boolean deteccionVrus(UploadedFile prmFile) throws IOException {
        boolean resulDet = true;
        try {
            String hostDeteccionVirus = bundleParametros.getString("anexo.antivirus.host");
            int ptoDeteccionVirus = Integer.parseInt(bundleParametros.getString("anexo.antivirus.pruerto"));

            ClamScan cs = new ClamScan();
            cs.setHost(hostDeteccionVirus);
            cs.setPort(ptoDeteccionVirus);
            cs.setTimeout(100);
            ScanResult sr = cs.scan(prmFile.getContents());
            //se intenta dos veces ya que a la primera siempre falla
            sr = cs.scan(prmFile.getContents());
            resulDet = sr.getStatus() == sr.getStatus().PASSED ? true : false;
            logger.info(".....verificacion de virus en anexo>>> result:" + sr.getResult() + " estatus: " + sr.getStatus());
            System.out.println(".....verificacion de virus en anexo>>> result:" + sr.getResult() + " estatus: " + sr.getStatus());
            /*
             System.out.println("Resultado = {" + sr.getResult() + "}");
             System.out.println("Resultado = {" + sr.getStatus() + "}");
             System.out.println("Resultado = {" + sr.getSignature() + "}");
             */
        } catch (FileNotFoundException ex) {
            logger.fatal("Informacion de Error: -- " + ex + " -- ubicado en: " + ex.getLocalizedMessage());
        }

        return resulDet;
    }
    
}
