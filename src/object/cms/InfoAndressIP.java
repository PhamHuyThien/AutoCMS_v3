/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object.cms;

/**
 *
 * @author Administrator
 */
public class InfoAndressIP {
    private String ip;
    private String city;
    private String region;
    private String country;
    private String timezone;

    public InfoAndressIP() {
    }

    public InfoAndressIP(String ip, String city, String region, String country, String timezone) {
        this.ip = ip;
        this.city = city;
        this.region = region;
        this.country = country;
        this.timezone = timezone;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        return "InfoAndressIP{" + "ip=" + ip + ", city=" + city + ", region=" + region + ", country=" + country + ", timezone=" + timezone + '}';
    }
    
}
