package com.example.ktapp;

import com.esri.arcgisruntime.geometry.CoordinateFormatter;
import com.esri.arcgisruntime.geometry.DatumTransformation;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp
 * @ClassName: testja
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/4/24 15:17
 */
public class testja {
       //中央经线 是多少？？？？
       //保留代号结果会正确 代号是多少呢？？
       //投影带 是多少
      //37.203267527777776,115.40381543333334,3
    public static void main(String[] args) {
        Tuple tuple = MyKrtUtils.gpsGetXY(37.20305555555556, 115.40361111111112, 3);
        System.out.println(" " + tuple.getX() + "   " + tuple.getY() + "");
        LatLng latLng = MyKrtUtils.xyTowgs84(2985130.22, 37474749.87, 3);//此算法去带号的  ，---##保留代号结果会正确 代号是多少呢？？
        System.out.println(latLng.getLat() + "   " + latLng.getLng() + "");
//        Point pointGeometry0 = CoordinateFormatter.fromLatitudeLongitude("38.0218840295077 114.49618767621", SpatialReferences.getWgs84());
////
//        SpatialReference inputSR = SpatialReference.create(4490); //4523为2000坐标的参数
//        SpatialReference outputSR = SpatialReference.create(4326);//4326为84坐标参数
////        Point point200 = new Point(2985130.22, 37474749.87);//偏差后4位
//        Point point2000 = (Point) GeometryEngine.project(pointGeometry0, inputSR);
//
//        double x = point2000.getX();
//        double y = point2000.getY();
//        System.out.println(x+"   "+y+"");

    }




}
