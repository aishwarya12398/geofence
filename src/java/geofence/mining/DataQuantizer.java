/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geofence.mining;

import static geofence.DBManager.getConnection;
import static geofence.mining.Main.file;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aish
 */
public class DataQuantizer
{

    static
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException ex)
        {
        }
    }
    private static final String CON_STRING = "jdbc:mysql://localhost:3306/geofence";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(CON_STRING, USERNAME, PASSWORD);
    }

    public static void quantizeData()
    {

        Calendar c = Calendar.getInstance();

        //retrieve not processed locationlog
        try (Connection con = getConnection();
                PreparedStatement ps1 = con.prepareStatement("insert into qlocationlog values (0,?,?,?,?,?,?,?,?)");
                PreparedStatement ps = con.prepareStatement("select * from locationlog l where l.isprocessed=0");
                ResultSet rs = ps.executeQuery())
        {
            //TODO: Begin transaction

            while (rs.next())
            {
                int llid = rs.getInt("llid");
                int userid = rs.getInt("userid");
                float longitude = rs.getFloat("longitude");
                float latitude = rs.getFloat("latitude");
                Timestamp datetime = rs.getTimestamp("datetime");
                String triggeredstatus = rs.getString("triggeredstatus");
//                int isprocessed = rs.getInt("isprocessed");

                System.out.println(llid);

                c.setTime(datetime);
                c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) / 15 * 15);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);

                int day = c.get(Calendar.DAY_OF_WEEK);
                Time t = new Time(c.getTimeInMillis());

                float qlong = ((int) (longitude / 0.005f)) * 0.005f;
                float qlat = ((int) (latitude / 0.005f)) * 0.005f;

                ps1.setInt(1, llid);
                ps1.setInt(2, userid);
                ps1.setFloat(3, qlong);
                ps1.setFloat(4, qlat);
                ps1.setTimestamp(5, datetime);
                ps1.setInt(6, day);
                ps1.setTime(7, t);
                ps1.setString(8, triggeredstatus);
                ps1.addBatch();
                //double qlongitude=longitude.getTinyValue();
            }
            //TODO: Commit transaction
            ps1.executeBatch();

            ps1.executeUpdate("update locationlog set isprocessed=1");
        } catch (SQLException ex)
        {
            Logger.getLogger(DataQuantizer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // quantize time
        // quantize long,lat
        //insert into qlocationlog
        //-------------
    }

    public static void generateDataset()
    {
        int threshold = 1;
        File folder = new File("dataset");
        if (!folder.exists())
        {
            folder.mkdirs();
        }

        //retrieve list of users
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("select userid from users");
                ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                int userid = rs.getInt("userid");
                System.out.println("userid = " + userid);
                try (Connection conn = getConnection();
                        PreparedStatement ps1 = conn.prepareStatement("select * from qlocationlog where userid=" + userid);
                        PreparedStatement ps2 = conn.prepareStatement("delete from fence where userid=?");
                        PreparedStatement ps3 = conn.prepareStatement("insert into fence values (0,?,?)");
                        ResultSet rs1 = ps1.executeQuery();
                        PrintWriter pw = new PrintWriter(new File(folder, userid + ".csv")))

                {
                    while (rs1.next())
                    {
                        int llid = rs1.getInt("llid");
                        float qlong = rs1.getFloat("qlongitude");
                        float qlat = rs1.getFloat("qlatitude");
                        Timestamp datetime = rs1.getTimestamp("datetime");
                        int day = rs1.getInt("day");
                        Time t = rs1.getTime("qtime");
                        String str = String.format("DAY=%d, DATE=%d, TIME=%s, LONG=%f, LAT=%f", day, datetime.getDate(), t.toLocalTime(), qlong, qlat);
                        pw.println(str);
                    }
                    pw.close();
                    new FPGrowth(new File(folder, userid + ".csv"), threshold, userid + ".txt");
                    File file = new File(userid + ".txt");
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String st;
                    String str[];

                    ps2.setInt(1, userid);
                    ps2.executeUpdate();

                    ps3.setInt(1, userid);
                    while ((st = br.readLine()) != null)
                    {
                        if (st.contains("LONG") && st.contains("LAT"))
                        {
                            ps3.setString(2, st);
                            ps3.executeUpdate();
                        }
                    }

//                    while((st=br.readLine())!=null)
//                    {
//                        str=st.split(",");
//                        for(String a:str)
//                        {
//                            List<String> list=Arrays.asList(a.split("="));
//                            List<String> list1=new ArrayList<>();
//                            list1.add("LONG");
//                            list1.add("LAT");
//                            if(list.containsAll(list1))
//                            {
//                                for(int i=0;i<list.size();i++)
//                                {
//                                    if(list[i])
//                                }
//                               PreparedStatement ps2=con.prepareStatement("insert into fence values(0,?,?,?,?,?)");
//                                
//                            } else
//                            {
//                            }
//                         
//                        }
//                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        //for()
        //create csv file
        //retrive qlocation for last year for this user
        //for each entry
//        Date d=rs.getDate("date");
    }

    public static void main(String[] args)
    {
        quantizeData();
        generateDataset();
    }
}
