/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geofence;

/**
 *
 * @author admin
 */
public class User
{

    private int userid;
    private String username;
    private String passwordhash;
    private String contact;
    private String email;
    private int active;

    public User()
    {
    }

    public User(String username, String passwordhash, String contact, String email)
    {
        this.username = username;
        this.passwordhash = passwordhash;
        this.contact = contact;
        this.email = email;
    }

    

    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getUserid()
    {
        return userid;
    }

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPasswordhash()
    {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash)
    {
        this.passwordhash = passwordhash;
    }

    public int getActive()
    {
        return active;
    }

    public void setActive(int active)
    {
        this.active = active;
    }

}
