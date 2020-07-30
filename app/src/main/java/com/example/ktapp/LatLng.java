package com.example.ktapp;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp
 * @ClassName: LatLng
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/7/1 9:00
 * @UpdateUser: LiuTao
 */
public class LatLng {

    private double lng;
    private double lat;

    public LatLng(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
