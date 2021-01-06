package de.hsos.vs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementierung des BillBoard-Servers. In dieser Version unterstützt er
 * asynchrone Aufrufe. Damit wird die Implementierung von Long Polling möglich:
 * Anfragen (HTTP GET) werden nicht sofort wie bei zyklischem Polling
 * beantwortet sondern verbleiben so lange im System, bis eine Änderung an den
 * Client gemeldet werden kann.
 *
 * @author heikerli
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/BillBoardServlet"})
public class BillBoardServlet extends HttpServlet {

    private static final long TIMEOUT = 10000; // 10 Seconds
    public static final String INSTANT_PARAM = "instant";
    public static final String CONTEXT = "BillBoardServlet";

    private final BillBoardJsonAdapter bb = new BillBoardJsonAdapter(CONTEXT);

    private final ConcurrentLinkedQueue<Thread> waiting = new ConcurrentLinkedQueue();

    private void changed() {
        try {
            while (true) {
                Thread thread = waiting.remove();
                thread.interrupt();
            }
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            String caller_ip = request.getRemoteAddr();
            /* Ausgabe des gesamten Boards */
            System.out.printf("BillBoardServer - GET (%s): full output, Thread:%d\n", caller_ip, Thread.currentThread().getId());
            response.setContentType("application/json;charset=UTF-8");
            String data = "";
            if (request.getParameter(INSTANT_PARAM) == null) {
                try {
                    waiting.add(Thread.currentThread());
                    Thread.sleep(TIMEOUT);
                    // Nicht unterbrochen -> Billboard hat sich nicht verändert
                    waiting.remove(Thread.currentThread());
                    data = "{}";
                } catch (InterruptedException e) {
                    data = bb.readEntries(caller_ip);
                }
            } else {
                data = bb.readEntries(caller_ip);

            }
            out.println(data);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (BufferedReader reader = request.getReader();
                PrintWriter writer = response.getWriter()) {
            String caller_ip = request.getRemoteAddr();
            String body = reader.readLine();
            System.out.printf("BillBoardServer - POST (%s): %s Thread:%d\n", caller_ip, body, Thread.currentThread().getId());
            bb.createEntry(body, caller_ip);
            changed();
        }
    }

    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (BufferedReader reader = request.getReader();
                PrintWriter writer = response.getWriter()) {
            String caller_ip = request.getRemoteAddr();
            String idxString = reader.readLine();
            System.out.printf("BillBoardServer - DELETE (%s): %s Thread:%d\n", caller_ip, idxString, Thread.currentThread().getId());
            try {
                int idx = Integer.parseInt(idxString);
                bb.deleteEntry(idx);
                changed();
            } catch (NumberFormatException e) {
                response.sendError(400, "Invalid Index");
            }
        }
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (BufferedReader reader = request.getReader();
                PrintWriter writer = response.getWriter()) {
            String caller_ip = request.getRemoteAddr();
            String idxString = reader.readLine();
            String content = reader.readLine();
            System.out.printf("BillBoardServer - PUT (%s): %s@%s Thread:%d\n", caller_ip, content, idxString, Thread.currentThread().getId());
            try {

                int idx = Integer.parseInt(idxString);
                bb.updateEntry(idx, content, caller_ip);
                changed();
            } catch (NumberFormatException e) {
                response.sendError(400, "Invalid Index");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "BillBoard Servlet";
    }// </editor-fold>
}
