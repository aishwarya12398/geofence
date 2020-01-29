/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geofence;

import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class LocationLog
{

    private int llid;
    private int userid;
    private float longitude;
    private float latitude;
    private java.sql.Timestamp datetime;
    
    public LocationLog()
    {
        
    }
    public LocationLog(int i0, float f, float f0,String s)
    {
       
        userid=i0;
        longitude=f;
        latitude=f0;
        triggeredstatus=s;//To change body of generated methods, choose Tools | Templates.
    }

    

    public Timestamp getDatetime()
    {
        return datetime;
    }

    public void setDatetime(Timestamp datetime)
    {
        this.datetime = datetime;
    }

    private String triggeredstatus;
    private boolean isprocessed;

    public int getLlid()
    {
        return llid;
    }

    public void setLlid(int llid)
    {
        this.llid = llid;
    }

    public int getUserid()
    {
        return userid;
    }

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

    public float getLongitude()
    {
        return longitude;
    }

    public void setLongitude(float longitude)
    {
        this.longitude = longitude;
    }

    public float getLatitude()
    {
        return latitude;
    }

    public void setLatitude(float latitude)
    {
        this.latitude = latitude;
    }

    public String getTriggeredstatus()
    {
        return triggeredstatus;
    }

    public void setTriggeredstatus(String triggeredstatus)
    {
        this.triggeredstatus = triggeredstatus;
    }

    public boolean isIsprocessed()
    {
        return isprocessed;
    }

    public void setIsprocessed(boolean isprocessed)
    {
        this.isprocessed = isprocessed;
    }

}
