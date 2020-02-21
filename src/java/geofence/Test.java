/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geofence;

import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class Test
{

    public static void main(String[] args) throws SQLException
    {
//        User u = new User();
//        u.setUsername("abcd");
//        u.setPasswordhash("efgh");
//
//        long userid = DBManager.addUser(u);
//        System.out.println("userid = " + userid);
        
        float f=1.636f;
        System.out.println(((int)(f/0.005))*0.005);
    }
}
