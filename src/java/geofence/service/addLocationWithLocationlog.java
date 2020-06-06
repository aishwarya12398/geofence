/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geofence.service;

import geofence.DBManager;
import geofence.LocationLog;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
@WebServlet(name = "addLocationWithLocationlog", urlPatterns =
{
    "/service/addLocationWithLocationlog"
})
public class addLocationWithLocationlog extends HttpServlet
{

    private Map<Integer, Long> lastSentMap = new HashMap<>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Map<String, Object> map = Utils.getRequestObject(request);
        System.out.println(map);
        final int userid = (int) map.get("userid");
        final float longitude = ((Number) map.get("longitude")).floatValue();
        final float latitude = ((Number) map.get("latitude")).floatValue();
        final String triggeredStatus = (String) map.get("triggeredstatus");

        LocationLog l = new LocationLog(userid, longitude, latitude, triggeredStatus);
        Map<String, Object> responseMap = new HashMap<>();
        long n = DBManager.addlocation(l);

        List<String> fences = DBManager.getFences(userid);

        if (checkBrokenFence(fences, userid, longitude, latitude))
        {
            //send message to watchers
        }

        if (n != -1)
        {
            responseMap.put("status", "success");
            responseMap.put("LocationLog", l);
        } else
        {
            responseMap.put("status", "failed");
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    private static boolean checkBrokenFence(List<String> fences, int userid, float longitude, float latitude)
    {
        try
        {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) / 15 * 15);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            int day = c.get(Calendar.DAY_OF_WEEK);
            int date = c.get(Calendar.DAY_OF_MONTH);
            Time t = new Time(c.getTimeInMillis());
            float qlong = ((int) (longitude / 0.005f)) * 0.005f;
            float qlat = ((int) (latitude / 0.005f)) * 0.005f;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            for (String fence : fences)
            {
                String[] tokens = fence.split("[,=\\s]+");
                int count = 0;
                int match = 0;

                float flong=0;
                float flat=0;
                
                for (int i = 0; i < tokens.length - 1; i += 2)
                {
                    String rule = tokens[i];
                    if (rule.equals("DATE"))
                    {
                        int fd = Integer.parseInt(tokens[i + 1]);
                        if (fd == date)
                        {
                            match++;
                        }
                        count++;
                    } else if (rule.equals("DAY"))
                    {
                        int fd = Integer.parseInt(tokens[i + 1]);
                        if (fd == day)
                        {
                            match++;
                        }
                        count++;
                    } else if (rule.equals("TIME"))
                    {
                        Calendar tt = Calendar.getInstance();
                        tt.setTime(sdf.parse(tokens[i + 1]));
                        Time ft = new Time(tt.getTimeInMillis());
                        if (ft.equals(t))
                        {
                            match++;
                        }
                        count++;
                    }
                    else if(rule.equals("LONG"))
                    {
                        flong=Float.parseFloat(tokens[i+1]);
                    }
                    else if(rule.equals("LAT"))
                    {
                        flat=Float.parseFloat(tokens[i+1]);
                    }
                }
                if(match==count)
                {
                    if(Math.abs(qlong-flong)<0.0001 && Math.abs(qlat-flat)<0.0001)
                    {
                        
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args)
    {
        String fence = "DATE=29               209";
        String[] tokens = fence.split("[,=\\s]+");
        System.out.println(Arrays.toString(tokens));

    }
}
