/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geofence.service;

import com.fasterxml.jackson.jr.ob.JSON;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class Utils
{

    private static final SimpleDateFormat sdf = new SimpleDateFormat("d/M/y");
    private static Object LocationLog;

    public static Map<String, Object> getRequestObject(HttpServletRequest request)
    {
        try
        {
            String body = Utils.readRequestBody(request);
            System.out.println(body);

            Map<String, Object> map = JSON.std.mapFrom(body);
            return map;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public static String readRequestBody(HttpServletRequest request)
    {
        try
        {
            InputStream is = request.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[10240];
            int n;
            while ((n = is.read(b)) != -1)
            {
                baos.write(b, 0, n);
            }
            b = baos.toByteArray();
            String str = new String(b, "UTF-8");
            return str;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "{}";
    }

    public static void writeResponse(HttpServletResponse response, Map<String, Object> responseMap)
    {
        try
        {
//            response.addHeader("Access-Control-Allow-Origin", "*");
//            response.addHeader("Access-Control-Allow-Headers", "*");
//            response.addHeader("Access-Control-Allow-Methods", "*");

            response.setContentType("text/plain-text");

            String json = JSON.std.asString(responseMap);
            PrintWriter pw = response.getWriter();
            pw.println(json);
            pw.flush();
            pw.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void writeOption(HttpServletResponse response)
    {
        try
        {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "*");
            response.addHeader("Access-Control-Allow-Methods", "*");

            response.setContentType("text/plain-text");

            PrintWriter pw = response.getWriter();
            pw.println();
            pw.flush();
            pw.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Date parseDate(String datestr)
    {
        try
        {

            return sdf.parse(datestr);
        } catch (Exception e)
        {
            LocationLog = null;
        }
        return null;
    }
}
