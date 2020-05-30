/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geofence.service;

import geofence.DBManager;
import geofence.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "LoginServlet", urlPatterns =
{
    "/service/login"
})
public class LoginServlet extends HttpServlet
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
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Map<String, Object> map = Utils.getRequestObject(request);
        System.out.println(map);

        String username = (String) map.get("username");
        String password = (String) map.get("password");

        //
        System.out.println("username = " + username);
        System.out.println("password = " + password);

        User u = DBManager.getUser(username, password);

        Map<String, Object> responseMap = new HashMap<>();
        if (u != null)
        {
            String token = UUID.randomUUID().toString();
            DBManager.addToken(u.getUserid(), token);

            responseMap.put("status", "success");
            responseMap.put("user", u);
            responseMap.put("token", token);
        } else
        {
            responseMap.put("status", "failed");
            responseMap.put("message", "Invalid username or password");
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
