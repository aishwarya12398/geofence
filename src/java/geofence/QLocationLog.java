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
public class QLocationLog
{

    private int qlid;
    private int llid;
    private int userid;
    private float qlongitude;
    private float qlatitude;
    private String day;
    private String triggeredstatus;
    private long qtime;
    private java.sql.Timestamp datetime;

    public Timestamp getDatetime()
    {
        return datetime;
    }

    public void setDatetime(Timestamp datetime)
    {
        this.datetime = datetime;
    }

    public long getQtime()
    {
        return qtime;
    }

    public void setQtime(long qtime)
    {
        this.qtime = qtime;
    }

    public int getQlid()
    {
        return qlid;
    }

    public void setQlid(int qlid)
    {
        this.qlid = qlid;
    }

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

    public float getQlongitude()
    {
        return qlongitude;
    }

    public void setQlongitude(float qlongitude)
    {
        this.qlongitude = qlongitude;
    }

    public float getQlatitude()
    {
        return qlatitude;
    }

    public void setQlatitude(float qlatitude)
    {
        this.qlatitude = qlatitude;
    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public String getTriggeredstatus()
    {
        return triggeredstatus;
    }

    public void setTriggeredstatus(String triggeredstatus)
    {
        this.triggeredstatus = triggeredstatus;
    }

}
