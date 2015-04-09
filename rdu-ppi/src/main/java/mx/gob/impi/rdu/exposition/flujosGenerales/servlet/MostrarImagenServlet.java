/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.gob.impi.rdu.persistence.model.ImagenDibujo;
import org.apache.log4j.Logger;

/**
 *
 * @author CESAR CASTAÃ‘EDA REYES <WWW.INFOTEC.COM>
 */
public class MostrarImagenServlet extends HttpServlet {

    Logger log = Logger.getLogger(MostrarReporteServlet.class);
    public String mime;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        mime = (String) session.getAttribute("mime");
        response.setContentType(mime);
        response.setHeader("Content-disposition", "inline;archivo." + mime);
        //File pdfFile = new File(request.getRealPath("") + "/imagen.jpg");
        //File pdfFile = new File(request.getRealPath("") + "/impi_00_006.jasper");
        //FileInputStream fis = new FileInputStream(pdfFile);
        //String pathFile = request.getRealPath("") + "/impi_00_006.jasper";
        long idImagen = Long.parseLong(request.getParameter("idImagen"));
        List<ImagenDibujo> imagenes = (List<ImagenDibujo>) session.getAttribute("imagenes");
        ByteArrayOutputStream baos = null;
        if (imagenes != null) {
            for (ImagenDibujo imagen : imagenes) {
                if (imagen.getOrden().longValue() == idImagen) {
                    baos = new ByteArrayOutputStream();
                    baos.write(imagen.getArchivo());
                    break;
                }
            }
        }
        /*
        byte[] buf = new byte[(int) pdfFile.length()];
        for (int readNum; (readNum = fis.read(buf)) != -1;) {
        bos.write(buf, 0, readNum);
        }
        System.out.println("Parametro: "+request.getParameter("id"));
        response.setContentLength(buf.length);
        response.getOutputStream().write(buf);
         * */
        if (baos != null) {
            response.setContentLength(baos.size());
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            //session.invalidate();
            //session.removeAttribute("reporteStream");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
