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
public class Watcher
{

    private int watcherid;
    private int userid;
    private String name;
    private String contact;
    private String email;
    private int active;
    private int enabled;

    public Watcher()
    {
    }

    public Watcher(int userid, String name, String contact, String email)
    {
        this.userid = userid;
        this.name = name;
        this.contact = contact;
        this.email = email;//To change body of generated methods, choose Tools | Templates.
    }

    public int getWatcherid()
    {
        return watcherid;
    }

    public void setWatcherid(int watcherid)
    {
        this.watcherid = watcherid;
    }

    public int getUserid()
    {
        return userid;
    }

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

    public int getActive()
    {
        return active;
    }

    public void setActive(int active)
    {
        this.active = active;
    }

    public int getEnabled()
    {
        return enabled;
    }

    public void setEnabled(int enabled)
    {
        this.enabled = enabled;
    }

}
