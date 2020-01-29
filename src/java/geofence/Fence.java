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
public class Fence
{

    private int fenceid;
    private int userid;
    private float qlongitude;
    private float qlatitude;
    private String rule;
    private float confidence;
    private java.sql.Timestamp datetime;

    public Timestamp getDatetime()
    {
        return datetime;
    }

    public void setDatetime(Timestamp datetime)
    {
        this.datetime = datetime;
    }

    public int getFenceid()
    {
        return fenceid;
    }

    public void setFenceid(int fenceid)
    {
        this.fenceid = fenceid;
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

    public String getRule()
    {
        return rule;
    }

    public void setRule(String rule)
    {
        this.rule = rule;
    }

    public float getConfidence()
    {
        return confidence;
    }

    public void setConfidence(float confidence)
    {
        this.confidence = confidence;
    }

}
