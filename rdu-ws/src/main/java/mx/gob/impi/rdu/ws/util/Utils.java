package mx.gob.impi.rdu.ws.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author winter
 */
public class Utils {

    private Utils() {
    }

    /**
     * Se encarga de escribir un archivo temporal a partir del inputstream.
     *
     * @param fileToWrite Stream del archivo a escribir.
     * @param name Nombre del archivo.
     * @param extension Extension del archivo.
     * @return Apuntador al archivo escrito, <code>null</code> en caso de no haber escrito el archivo.
     */
    public static File writeTempFile(InputStream fileToWrite, String name, String extension) {
        File toWrite = null;
        FileOutputStream writer = null;

        int DEFAULT_CHUNK_SIZE = 1024;
        byte[] chunk = new byte[DEFAULT_CHUNK_SIZE];

        try {
            toWrite = File.createTempFile(name, extension);
            writer = new FileOutputStream(toWrite);

            if (fileToWrite.available() > 0) {
                while ((fileToWrite.read(chunk)) != -1) {
                    writer.write(chunk);
                } // while
            } // if

            writer.flush();
            writer.close();
            fileToWrite.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return toWrite;
    }

    public static byte[] getBytes(InputStream is) throws IOException {
        System.out.println("*********************Obteniendo los bytes*****************");
        int len;
        int size = 1024;
        byte[] buf;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        buf = new byte[size];
        while ((len = is.read(buf, 0, size)) != -1) {
            bos.write(buf, 0, len);
        }
        buf = bos.toByteArray();
        return buf;
    }

    public static byte[] getFile(URL url) throws IOException {
        System.out.println("*****************Conectando a la url***************");
        return getBytes(url.openStream());
    }
}
