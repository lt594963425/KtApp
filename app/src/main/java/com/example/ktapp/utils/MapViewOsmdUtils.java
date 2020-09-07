package com.example.ktapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.utils
 * @ClassName: MapViewOsmdUtils
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/8/20 17:57
 * @UpdateUser: LiuTao
 */
public class MapViewOsmdUtils {
    //
//    static volatile MapViewOsmdUtils mMapViewUtils = null;
//
//    MapView mMapView;
//    Context mContext;
//
//    public MapViewOsmdUtils(MapView mapView, Context mContext) {
//        this.mMapView = mapView;
//        this.mContext = mContext;
//    }
//
//
//    public void initMapView(boolean isShowMetrics, boolean isShowMyLocation) {
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), Config.FILE_PATH);
//        if (!file.exists() && !file.mkdirs()) {
//            Log.d("TAG", "onCreate: " + file.getAbsolutePath() + " mkdirs fail ");
//        }
//        Configuration.getInstance().setOsmdroidBasePath(new File(path + Config.FILE_PATH));
//        Configuration.getInstance().setOsmdroidTileCache(new File(path + Config.FILE_PATH));
//
//        Configuration.getInstance().load(mContext, mContext.getSharedPreferences(Config.SP_NAME, mContext.MODE_PRIVATE));
//        mMapView.setDrawingCacheEnabled(true);
//        mMapView.setMultiTouchControls(true);// 触控放大缩小
//        mMapView.setMaxZoomLevel(22.0);
//        mMapView.setMinZoomLevel(6.0);
//        mMapView.getController().setZoom(15.0);
//        //mMapView
//        mMapView.setUseDataConnection(true);
//        mMapView.setMultiTouchControls(true);// 触控放大缩小
//        //是否显示地图数据源
//        mMapView.getOverlayManager().getTilesOverlay().setEnabled(true);
//        //加载谷歌地图，设置地图数据源的形式
//        mMapView.setTileSource(GoogleTileSource.GoogleRoads);
//
//        //地图自由旋转
//        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(mMapView);
//        mRotationGestureOverlay.setEnabled(true);
//        mMapView.getOverlays().add(mRotationGestureOverlay);
//        //比例尺配置
//
//        if (isShowMetrics) {
//            final DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
//            ScaleBarOverlay mScaleBarOverlay = new ScaleBarOverlay(mMapView);
//            mScaleBarOverlay.setCentred(true);
//            mScaleBarOverlay.setAlignBottom(true); //底部显示
//            mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 5, 80);
//            mMapView.getOverlays().add(mScaleBarOverlay);
//        }
//
//        //指南针方向
//        CompassOverlay mCompassOverlay = new CompassOverlay(mContext, new InternalCompassOrientationProvider(mContext),
//                mMapView);
//        mCompassOverlay.enableCompass();
//        mMapView.getOverlays().add(mCompassOverlay);
//        //设置导航图标
//
//        if (isShowMyLocation) {
//            MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(mContext),
//                    mMapView);
//
//            Bitmap bitmap = MyUtils.zoomImg(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.renyuan), 80, 80);
//
//            mLocationOverlay.setDirectionArrow(bitmap,
//                    ((BitmapDrawable)
//                            mMapView.getContext().getResources().getDrawable(org.osmdroid.library.R.drawable.person)).getBitmap());
//
//
//            mMapView.getOverlays().add(mLocationOverlay);
//            mLocationOverlay.enableMyLocation();  //设置可视
//        }
//
//    }
//
//    /**
//     * 设置中心点
//     */
//    public void setPositionCenter(GeoPoint latLng) {
//
//        mMapView.getController().setCenter(latLng);
//    }
//
//    /*
//     获得地图绘制的多边形
//     */
//    public Polygon getPolygon(ArrayList<GeoPoint> pts) {
//        //here, we create a polygon using polygon class, note that you need 4 points in order to make a rectangle
//
//        if (pts == null || pts.size() < 3) {
//            return new Polygon(mMapView);
//        }
//        Polygon polygon = new Polygon(mMapView);
//        polygon.setFillColor(0x7084bf96);
//        polygon.setVisible(true);
//        polygon.setStrokeColor(0xAA00FF00);
//        polygon.setStrokeWidth(5);
//        polygon.setPoints(pts);
//        return polygon;
//    }
//
//    /*
//    绘制多边形
//     */
//    public void drawPolygonView(Polygon polygon) {
//        mMapView.getOverlays().add(polygon);
//    }
//
//    /*
//        绘制多边形
//  */
//    public void drawPolygonView(ArrayList<GeoPoint> pts) {
//        Polygon polygon = getPolygon(pts);
//        mMapView.getOverlays().add(polygon);
//    }
//
//    /*
//     设置获得  绘制的线
//     */
//    public Polyline getPolyline(List<GeoPoint> pts) {
//        if (pts == null && pts.size() < 2) {
//            return new Polyline(mMapView);
//        }
//        Polyline line = new Polyline(mMapView);
//        line.setSubDescription(Polyline.class.getCanonicalName());
//        line.setWidth(15f);
//        line.setPoints(pts);
//        line.setColor(Color.RED);
//        line.setGeodesic(true);
//
//        return line;
//    }
//
//    /*
//  绘制多边形
//   */
//    public void drawPolylineView(Polyline line) {
////        mMapView.getOverlays().add(polygon);
//        mMapView.getOverlayManager().add(line);
//    }
//
//    /*
//        绘制多边形
//  */
//    public void drawPolylineView(List<GeoPoint> pts) {
//        Polyline line = getPolyline(pts);
//        mMapView.getOverlays().add(line);
//    }
//
//    /**
//     * 设置marker覆盖物
//     *
//     * @param startPoint 坐标
//     * @param icon       图标
//     */
//    public void setMarkerOptions(GeoPoint startPoint, int icon) {
//        if (startPoint == null) return;
//        Marker startMarker = new Marker(mMapView);
//        startMarker.setPosition(startPoint);
//        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//
//        Bitmap bitmap = MyUtils.zoomImg(BitmapFactory.decodeResource(mContext.getResources(), icon), 300, 300);
//        BitmapDrawable bd = new BitmapDrawable(bitmap);
//        startMarker.setIcon(bd);
//        startMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker, MapView mapView) {
//                marker.showInfoWindow();
//                return true;
//            }
//        });
//
//        mMapView.getOverlays().add(startMarker);
//    }
}
