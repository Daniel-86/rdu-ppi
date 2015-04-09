/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author CESAR CASTAÃ‘EDA REYES <WWW.INFOTEC.COM>
 */
public class MostrarReporteServlet extends HttpServlet {

    Logger log = Logger.getLogger(MostrarReporteServlet.class);
    public final String CONTENT_TYPE = "application/pdf";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Content-disposition", "inline;archivo.pdf");
        HttpSession session = request.getSession();
        //File pdfFile = new File(request.getRealPath("") + "/imagen.jpg");
        //File pdfFile = new File(request.getRealPath("") + "/impi_00_006.jasper");
        //FileInputStream fis = new FileInputStream(pdfFile);
        //String pathFile = request.getRealPath("") + "/impi_00_006.jasper";
        ByteArrayOutputStream baos = (ByteArrayOutputStream) session.getAttribute("reporteStream");
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
