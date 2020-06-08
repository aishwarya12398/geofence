/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geofence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class DBManager
{

    static
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static final String CON_STRING = "jdbc:mysql://localhost:3306/geofence";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException
    {
        try
        {
            return DriverManager.getConnection(CON_STRING, USERNAME, PASSWORD);
        } catch (Exception e)
        {
            return DriverManager.getConnection(CON_STRING, USERNAME, "root");
        }
    }

    static public long addUser(User u)
    {
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("insert into users values (0,?,password(?),?,?,1)"))
        {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPasswordhash());
            ps.setString(3, u.getContact());
            ps.setString(4, u.getEmail());

            ps.executeUpdate();

            try (ResultSet rs = ps.executeQuery("select LAST_INSERT_ID()"))
            {
                rs.next();
                return rs.getLong(1);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public static User getUser(int userid)
    {
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("select * from users u where u.userid=" + userid);
                ResultSet rs = ps.executeQuery())
        {

            if (rs.next())
            {
                User u = new User();
                u.setUserid(rs.getInt("userid"));
                u.setUsername(rs.getString("username"));
                u.setContact(rs.getString("contact"));
                u.setEmail(rs.getString("email"));
                u.setActive(rs.getInt("active"));
                return u;
            } else
            {
                return null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static User getUser(String username, String password)
    {
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("select * from users u where u.username=? and u.passwordhash=password(?)"))
        {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    User u = new User();
                    u.setUserid(rs.getInt("userid"));
                    u.setUsername(rs.getString("username"));
                    u.setContact(rs.getString("contact"));
                    u.setEmail(rs.getString("email"));
                    u.setActive(rs.getInt("active"));
                    return u;
                } else
                {
                    return null;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    public static List<User> getAllUsers()
    {
        List<User> list = new ArrayList<>();

        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("select * from users");
                ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                User u = new User();
                u.setUserid(rs.getInt("userid"));
                u.setUsername(rs.getString("username"));
                u.setContact(rs.getString("contact"));
                u.setEmail(rs.getString("email"));
                u.setActive(rs.getInt("active"));
                list.add(u);
            }
            return list;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    static public long addWatcher(Watcher w)
    {

        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("insert into watcher values (0,?,?,?,?,1,1)"))
        {
            ps.setInt(1, w.getUserid());
            ps.setString(2, w.getName());
            ps.setString(3, w.getContact());
            ps.setString(4, w.getEmail());
            ps.executeUpdate();

            try (ResultSet rs = ps.executeQuery("select LAST_INSERT_ID()"))
            {
                rs.next();
                return rs.getLong(1);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<Watcher> getWatchers(int userid)
    {
        List<Watcher> list = new ArrayList<>();

        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("select * from watcher w where w.userid=" + userid);
                ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Watcher w = new Watcher();
                w.setWatcherid(rs.getInt("watcherid"));
                w.setUserid(rs.getInt("userid"));
                w.setName(rs.getString("name"));
                w.setEmail(rs.getString("Email"));
                w.setContact(rs.getString("Contact"));
                w.setEnabled(rs.getInt("enabled"));
                w.setActive(rs.getInt("active"));
                list.add(w);

            }
            return list;
        } catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
        return null;

    }

    static public int removeWatcher(String watchername,int userid)
    {

        try (Connection con = getConnection();
                //PreparedStatement ps = con.prepareStatement("Update watcher w Set active=0 where w.name=" + watchername))
                PreparedStatement ps = con.prepareStatement("delete from watcher where name=? and userid=?"))
        {
            ps.setString(1, watchername);
            ps.setInt(2, userid);
            ps.executeUpdate();
            return 1;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    static public long addlocation(LocationLog l)
    {

        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("insert into LocationLog values (0,?,?,?,?,?,?)"))
        {

            ps.setInt(1, l.getUserid());
            ps.setFloat(2, l.getLongitude());
            ps.setFloat(3, l.getLatitude());
            ps.setTimestamp(4, new Timestamp(new Date().getTime()));
            ps.setString(5, l.getTriggeredstatus());
            ps.setInt(6, 0);

            ps.executeUpdate();

            try (ResultSet rs = ps.executeQuery("select LAST_INSERT_ID()"))
            {
                rs.next();
                return rs.getLong(1);
            }
        } catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
        return -1;
    }

    public static List<LocationLog> getLocation(int userid, Date startdate, Date enddate)
    {
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("select * from locationlog l where l.userid=? and l.datetime between ? and ?"))
        {
            ps.setInt(1, userid);
            ps.setTimestamp(2, new Timestamp(startdate.getTime()));
            ps.setTimestamp(3, new Timestamp(enddate.getTime()));
            ResultSet rs = ps.executeQuery();
            List<LocationLog> L = new ArrayList<>();

            while (rs.next())
            {
                LocationLog l = new LocationLog();
                l.setLlid(rs.getInt("llid"));
                l.setUserid(rs.getInt("userid"));
                l.setLongitude(rs.getFloat("longitude"));
                l.setLatitude(rs.getFloat("latitude"));
                l.setDatetime(rs.getTimestamp("datetime"));
                l.setTriggeredstatus(rs.getString("triggeredstatus"));
                l.setIsprocessed(rs.getBoolean("isprocessed"));
                L.add(l);
            }
            return L;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static LocationLog getLocation(float longitude, float latitude)
    {
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("select * from locationlog l where l.longitude=? and l.latitude=?"))
        {
            ps.setFloat(1, longitude);
            ps.setFloat(2, latitude);
            ResultSet rs = ps.executeQuery();

            LocationLog l = new LocationLog();
            l.setLlid(rs.getInt("llid"));
            l.setUserid(rs.getInt("userid"));
            l.setLongitude(rs.getFloat("longitude"));
            l.setLatitude(rs.getFloat("latitude"));
            l.setDatetime(rs.getTimestamp("datetime"));
            l.setTriggeredstatus(rs.getString("triggeredstatus"));
            l.setIsprocessed(rs.getBoolean("isprocessed"));
            return l;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void addToken(int userid, String token)
    {
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("insert into token values (?,?) on duplicate key update token=?"))
        {
            ps.setInt(1, userid);
            ps.setString(2, token);
            ps.setString(3, token);

            ps.executeUpdate();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static boolean verifyToken(int userid, String token)
    {
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("select * from token t where t.userid=? and t.token=?"))
        {
            ps.setInt(1, userid);
            ps.setString(2, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return true;
            }
            return false;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean logout(int userid, String token)
    {
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("delete from token where userid=? and token=?"))
        {
            ps.setInt(1, userid);
            ps.setString(2, token);

            if (ps.executeUpdate() == 1)
            {
                return true;
            }
            return false;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static List<String> getFences(int userid)
    {
        List<String> list = new ArrayList<>();
        try (Connection con = getConnection();
                PreparedStatement ps = con.prepareStatement("select * from fence where userid=?"))
        {
            ps.setInt(1, userid);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                String fence = rs.getString(3);
                list.add(fence);
            }
            rs.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

}
