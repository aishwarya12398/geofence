/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geofence.service;

import geofence.DBManager;
import geofence.Watcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "addWatcher", urlPatterns =
{
    "/service/addWatcher"
})
public class addWatcher extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        Map<String, Object> map = Utils.getRequestObject(request);
        System.out.println(map);

        String token = (String) map.get("token");
        int userid = Integer.parseInt((String) map.get("userid"));

        Map<String, Object> responseMap = new HashMap<>();
        if (token == null || !DBManager.verifyToken(userid, token))
        {
            responseMap.put("status", "failed");
            responseMap.put("message", "Not authenticated");

        } else
        {
            String name = (String) map.get("name");
            String contact = (String) map.get("contact");
            String email = (String) map.get("email");

            Watcher w = new Watcher(userid, name, contact, email);
            long id = DBManager.addWatcher(w);
            if (id != -1)
            {
                responseMap.put("status", "success");
                responseMap.put("Watcher", w);
            } else
            {
                responseMap.put("status", "failed");
            }
        }
        Utils.writeResponse(response, responseMap);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
            throws ServletException, IOException
    {
        service(request, response);
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
            throws ServletException, IOException
    {
        service(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
