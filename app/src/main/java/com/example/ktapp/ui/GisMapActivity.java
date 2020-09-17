package com.example.ktapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.loadable.LoadStatusChangedEvent;
import com.esri.arcgisruntime.loadable.LoadStatusChangedListener;
import com.esri.arcgisruntime.location.LocationDataSource;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.MobileMapPackage;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.portal.PortalItem;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.example.ktapp.R;
import com.example.ktapp.base.App;
import com.example.ktapp.base.BaseDataBindActivity;
import com.example.ktapp.databinding.ActivityGisMapBinding;
import com.example.ktapp.utils.FileUtils;
import com.example.ktapp.utils.SDFileSelecteUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import cn.sddman.arcgistool.common.Variable;
import cn.sddman.arcgistool.entity.DrawEntity;
import cn.sddman.arcgistool.listener.MapRotateClickListener;
import cn.sddman.arcgistool.listener.MapViewOnTouchListener;
import cn.sddman.arcgistool.listener.MeasureClickListener;
import cn.sddman.arcgistool.listener.ZoomClickListener;
import cn.sddman.arcgistool.manager.ArcgisToolManager;
import cn.sddman.arcgistool.view.ArcGisZoomView;
import cn.sddman.arcgistool.view.DrawGraphView;
import cn.sddman.arcgistool.view.MapRotateView;
import cn.sddman.arcgistool.view.MeasureToolView;

public class GisMapActivity extends BaseDataBindActivity<ActivityGisMapBinding> {
    //Runtime Lite许可证密钥
    //runtimelite,1000,rud5244175697,none,D7MFA0PL4S8PC2EN0171
    //临时访问令牌
    //BY75mHAOc2KlAnwngTTP8OIgqSnoVa_fJJ7BkdtMJNNRMh7traBcvgKfBN-3O3ffhST7DGKpkaIidrjLXtOprsldk5RGEuG4db3DWrtIn3LD8UG50cofWC7RZ3EnUgagQq3BfuFNU9wnI0sF0yxuOg..
    private ArcGISMapImageLayer mWorldStreetsLayer = null;
    private String url = "https://cache1.arcgisonline.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer";
    private String templateUri = "http://mt{subDomain}.google.cn/vt?lyrs=m&scale=1&hl=zh-CN&gl=cn&x={col}&y={row}&z={level}";
    private GraphicsOverlay mGraphicsOverlay;
    /* ** ADD 定位 ** */
    private LocationDisplay mLocationDisplay;
    private Point mStart;
    private Point mEnd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gis_map;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBind.mapView.setAttributionTextVisible(false);
        mGraphicsOverlay = new GraphicsOverlay();
        dataBind.mapView.getGraphicsOverlays().add(mGraphicsOverlay);
        loadMapData();

        MeasureToolView measureToolView = (MeasureToolView) findViewById(R.id.measure_tool);
        ArcgisToolManager arcgisToolManager = new ArcgisToolManager(this, dataBind.mapView);
        arcgisToolManager
                .setMapClickCallBack(new MapViewOnTouchListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        //Toast.makeText(MainActivity.this,"onSingleTapUp",Toast.LENGTH_SHORT).show();

                        android.graphics.Point screenPoint = new android.graphics.Point(
                                Math.round(e.getX()),
                                Math.round(e.getY()));
                        Point mapPoint = dataBind.mapView.screenToLocation(screenPoint);
                        mapClicked(mapPoint);
                        return super.onSingleTapUp(e);
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        //Toast.makeText(MainActivity.this,"onDoubleTap",Toast.LENGTH_SHORT).show();
                        return super.onDoubleTap(e);
                    }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        //Toast.makeText(MainActivity.this,"onScroll",Toast.LENGTH_SHORT).show();
                        return super.onScroll(e1, e2, distanceX, distanceY);
                    }

                    @Override
                    public boolean onRotate(MotionEvent event, double rotationAngle) {
                        // Toast.makeText(MainActivity.this,"onRotate",Toast.LENGTH_SHORT).show();
                        return super.onRotate(event, rotationAngle);
                    }

                    @Override
                    public boolean onScale(ScaleGestureDetector detector) {
                        //Toast.makeText(MainActivity.this,"onScale",Toast.LENGTH_SHORT).show();
                        return super.onScale(detector);
                    }
                })
                .builderMeasure(measureToolView)
                .setButtonWidth(60)
                .setButtonHeight(40)
                .setMeasureBackground(R.color.colorAccent)
                .setSohwText(true)
                .setFontSize(12)
                .setFontColor(R.color.white)
                .setMeasurePrevStr("撤销")
                .setMeasureNextStr("恢复")
                .setMeasureLengthStr("测距")
                .setMeasureAreaStr("测面积")
                .setMeasureClearStr("清除")
                .setMeasureEndStr("完成")
                .setMeasurePrevImage(R.drawable.sddman_measure_prev)
                .setMeasureNextImage(R.drawable.sddman_measure_next)
                .setMeasureLengthImage(R.drawable.sddman_measure_length)
                .setMeasureAreaImage(R.drawable.sddman_measure_area)
                .setMeasureClearImage(R.drawable.sddman_measure_clear)
                .setMeasureEndImage(R.drawable.sddman_measure_end)
                .setSpatialReference(SpatialReference.create(3857))
                .setLengthType(Variable.Measure.KM)
                .setAreaType(Variable.Measure.KM2)
                .setMeasureClickListener(new MeasureClickListener() {
                    @Override
                    public void prevClick(boolean hasPrev) {
                        Toast.makeText(GisMapActivity.this, "MeasureToolView prevClick", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void nextClick(boolean hasNext) {
                        Toast.makeText(GisMapActivity.this, "MeasureToolView nextClick", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void lengthClick() {
                        Toast.makeText(GisMapActivity.this, "MeasureToolView lengthClick", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void areaClick() {
                        Toast.makeText(GisMapActivity.this, "MeasureToolView areaClick", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void clearClick(DrawEntity draw) {
                        Toast.makeText(GisMapActivity.this, "MeasureToolView clearClick", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void endClick(DrawEntity draw) {
                        Toast.makeText(GisMapActivity.this, "MeasureToolView endClick", Toast.LENGTH_SHORT).show();
                    }
                });
        ArcGisZoomView zoomBtn = (ArcGisZoomView) findViewById(R.id.arcgis_zoom_btn);
        arcgisToolManager.builderZoomView(zoomBtn)
                .setZoomHeight(35)
                .setZoomWidth(60)
                .setZoomBackground(R.drawable.round_corner)
                .isHorizontal(true)
                .setZoomOutImage(R.drawable.sddman_zoomout)
                .setZoomInImage(R.drawable.sddman_zoomin)
                .setShowText(true)
                .setZoomOutText("缩小")
                .setZoomInText("放大")
                .setFontSize(12)
                .setFontColor(R.color.colorMain)
                .setZoomClickListener(new ZoomClickListener() {
                    @Override
                    public void zoomInClick(View view) {
                        Toast.makeText(GisMapActivity.this, "zoom in", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void zoomOutClick(View view) {
                        Toast.makeText(GisMapActivity.this, "zoom out", Toast.LENGTH_SHORT).show();
                    }
                });

        MapRotateView mapRotateView = (MapRotateView) findViewById(R.id.map_rotate_view);
        arcgisToolManager.builderRotateView(mapRotateView)
                .setHeight(40)
                .setWidth(60)
                .setBackground(R.drawable.round_corner)
                .setRotateNum(-45)
                .setRotateImage(R.drawable.sddman_measure_prev)
                .setRotateText("旋转")
                .setShowText(true)
                .setFontSize(16)
                .setFontColor(R.color.colorMain)
                .setMapRotateClickListener(new MapRotateClickListener() {
                    @Override
                    public void mapRotateClick(View view) {
                        Toast.makeText(GisMapActivity.this, "Map Rotate", Toast.LENGTH_SHORT).show();
                    }
                });
        DrawGraphView drawGraphView = findViewById(R.id.arcgis_draw_tool);
        arcgisToolManager.builderDrawGraphView(drawGraphView)
                .setButtonWidth(60)
                .setButtonHeight(40)
                .setBackground(R.color.colorAccent)
                .setSohwText(true)
                .setFontSize(12)
                .setFontColor(R.color.white);


    }

    /*
    在线地图
     */
    private void setupMap() {
        ArcGISTiledLayer tiledLayerBaseMap = new ArcGISTiledLayer(url);
        WebTiledLayer webTiledLayer = new WebTiledLayer(templateUri, Arrays.asList("0", "1", "2", "3"));
        Basemap basemap = new Basemap(webTiledLayer);
        ArcGISMap map = new ArcGISMap(basemap);
        basemap.getBaseLayers().add(tiledLayerBaseMap);
        //外包矩形
        Envelope mInitExtent = new Envelope(12152397.115334747, 2780298.008156988, 12204603.605653452, 2804643.2016657833, SpatialReference.create(102100));
        Viewpoint vp = new Viewpoint(mInitExtent);
        map.setInitialViewpoint(vp);

        dataBind.mapView.setMap(map);
//        dataBind.mapView.setViewpointCenterAsync(new Point(113.5665541, 28.25544111),10);
//        dataBind.mapView.setAttributionTextVisible(false);
        createGraphics();
        setupLocationDisplay();
    }

    private void setupMap2() {
        if (dataBind.mapView != null) {
            String itemId = "41281c51f9de45edaf1c8ed44bb10e30";
            Portal portal = new Portal("https://www.arcgis.com", false);
            PortalItem portalItem = new PortalItem(portal, itemId);
            ArcGISMap map = new ArcGISMap(portalItem);
            dataBind.mapView.setMap(map);
        }
    }

    private void addTrailheadsLayer() {
        String url = "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trailheads/FeatureServer/0";
        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable(url);
        FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
        ArcGISMap map = dataBind.mapView.getMap();
        map.getOperationalLayers().add(featureLayer);
        map.getBasemap().getBaseLayers().add(featureLayer);
    }

    @Override
    protected void onPause() {
        if (dataBind.mapView != null) {
            dataBind.mapView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dataBind.mapView != null) {
            dataBind.mapView.resume();
        }
    }

    @Override
    protected void onDestroy() {

        if (dataBind.mapView != null) {
            dataBind.mapView.dispose();
        }
        super.onDestroy();
    }

    /**
     * 添加点线面
     */
    private void createGraphics() {

        createPointGraphics();
        createPolylineGraphics();
        createPolygonGraphics();
    }

    /**
     * 点
     */
    private void createPointGraphics() {

        Point point = new Point(117.69333917997633, 34.032793670122885, SpatialReferences.getWgs84());
        SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.rgb(226, 119, 40), 10.0f);
        pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2.0f));
        Graphic pointGraphic = new Graphic(point, pointSymbol);
        mGraphicsOverlay.getGraphics().add(pointGraphic);
    }

    private void createPolylineGraphics() {
        PointCollection polylinePoints = new PointCollection(SpatialReferences.getWgs84());
        polylinePoints.add(new Point(118.67999016098526, 34.035828839974684));
        polylinePoints.add(new Point(118.65702911071331, 34.07649252525452));
        Polyline polyline = new Polyline(polylinePoints);
        SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 3.0f);
        Graphic polylineGraphic = new Graphic(polyline, polylineSymbol);
        mGraphicsOverlay.getGraphics().add(polylineGraphic);
    }


    private void createPolygonGraphics() {
        PointCollection polygonPoints = new PointCollection(SpatialReferences.getWgs84());
        polygonPoints.add(new Point(118.70372100524446, 34.03519536420519));
        polygonPoints.add(new Point(118.71766916267414, 34.03505116445459));
        polygonPoints.add(new Point(118.71923322580597, 34.04919407570509));
        polygonPoints.add(new Point(118.71631129436038, 34.04915962906471));
        polygonPoints.add(new Point(118.71526020370266, 34.059921300916244));
        polygonPoints.add(new Point(118.71153226844807, 34.06035488360282));
        polygonPoints.add(new Point(118.70803735010169, 34.05014385296186));
        polygonPoints.add(new Point(118.69877903513455, 34.045182336992816));
        polygonPoints.add(new Point(118.6979656552508, 34.040267760924316));
        polygonPoints.add(new Point(118.70259112469694, 34.038800278306674));
        polygonPoints.add(new Point(118.70372100524446, 34.03519536420519));
        Polygon polygon = new Polygon(polygonPoints);
        SimpleFillSymbol polygonSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.rgb(226, 119, 40),
                new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2.0f));
        Graphic polygonGraphic = new Graphic(polygon, polygonSymbol);
        mGraphicsOverlay.getGraphics().add(polygonGraphic);
    }


    /**
     * 定位
     * OFF,
     * RECENTER,– 中心点显示定位
     * NAVIGATION,– 导航模式（此模式，最佳适用于驾车导航）
     * COMPASS_NAVIGATION;– 罗盘模式(此模式最佳适用于用户步行时的路径导航）
     */
    private void setupLocationDisplay() {
        mLocationDisplay = dataBind.mapView.getLocationDisplay();

        mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
        mLocationDisplay.startAsync();
        mLocationDisplay.addDataSourceStatusChangedListener(new LocationDisplay.DataSourceStatusChangedListener() {
            @Override
            public void onStatusChanged(LocationDisplay.DataSourceStatusChangedEvent dataSourceStatusChangedEvent) {
                if (dataSourceStatusChangedEvent.isStarted() || dataSourceStatusChangedEvent.getError() == null) {
                    return;
                }
                int requestPermissionsCode = 2;
                String[] requestPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                if (!(ContextCompat.checkSelfPermission(GisMapActivity.this, requestPermissions[0]) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(GisMapActivity.this, requestPermissions[1]) == PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(GisMapActivity.this, requestPermissions, requestPermissionsCode);
                } else {
                    String message = String.format("Error in DataSourceStatusChangedListener: %s",
                            dataSourceStatusChangedEvent.getSource().getLocationDataSource().getError().getMessage());
                    Toast.makeText(GisMapActivity.this, message, Toast.LENGTH_LONG).show();
                }

            }
        });
        mLocationDisplay.addLocationChangedListener(new LocationDisplay.LocationChangedListener() {
            @Override
            public void onLocationChanged(LocationDisplay.LocationChangedEvent locationChangedEvent) {
                LocationDataSource.Location location = locationChangedEvent.getLocation();
                LocationDisplay locationDisplay = locationChangedEvent.getSource();
                Point point = location.getPosition();

            }
        });
        mLocationDisplay.addAutoPanModeChangedListener(new LocationDisplay.AutoPanModeChangedListener() {
            @Override
            public void onAutoPanModeChanged(LocationDisplay.AutoPanModeChangedEvent autoPanModeChangedEvent) {
                LocationDisplay.AutoPanMode autoPanMode = autoPanModeChangedEvent.getAutoPanMode();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationDisplay.startAsync();
        } else {
            Toast.makeText(GisMapActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
        }
    }


    public void loadMapData() {
        setupMap();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////                copyAssets();
//                setupOfflineMap();
//            }
//        }).start();
    }

    /**
     * 离线地图
     * 5a29218d-cb01-4050-afdc-01ba4c8903f4
     */
    private void setupOfflineMap() {
        String fileNamess = "offline-maps-package.mmpk";
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/ArcGISMap/");
        File file2 = new File(file.getAbsolutePath() + "/" + fileNamess);
        if (!file2.exists()) {
            FileUtils.copyAssets(file, fileNamess);
        }
//        File path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
//        File path = new File("/storage/emulated/0/Pictures");
//        File mmpkFile = new File(path, "offline-maps-package.mmpk");
        Log.w("MobileMapPackage 1", file2.getAbsolutePath() + "");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MobileMapPackage mapPackage = new MobileMapPackage(file2.getAbsolutePath());
                mapPackage.addLoadStatusChangedListener(new LoadStatusChangedListener() {
                    @Override
                    public void loadStatusChanged(LoadStatusChangedEvent loadStatusChangedEvent) {
                        if (loadStatusChangedEvent.getNewLoadStatus() == LoadStatus.LOADED && !mapPackage.getMaps().isEmpty()) {
                            for (int i = 0; i < mapPackage.getMaps().size(); i++) {
                                if (mapPackage.getMaps().get(i) != null) {
                                    Log.w("MobileMapPackage 2", i + "====" + mapPackage.getMaps().get(i).toString() + "");
                                }
                            }
                            dataBind.mapView.setMap(mapPackage.getMaps().get(0));
                            Log.w("MobileMapPackage 3", file2.getAbsolutePath());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    createGraphics();
                                    setupLocationDisplay();
                                }
                            });
                        } else {
                            Log.e("MobileMapPackage", "Cannot load " + file2.toString());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setupMap();
                                }
                            });
                        }
                    }
                });
                mapPackage.loadAsync();
            }
        });
    }


    /***
     * 添加点
     * @param location
     */
    private void mapClicked(Point location) {
        if (mStart == null) {
            // Start is not set, set it to a tapped location
            setStartMarker(location);
        } else if (mEnd == null) {
            // End is not set, set it to the tapped location then find the route
            setEndMarker(location);
        } else {
            // Both locations are set; re-set the start to the tapped location
            setStartMarker(location);
        }
    }


    private void setMapMarker(Point location, SimpleMarkerSymbol.Style style, int markerColor, int outlineColor) {
        float markerSize = 8.0f;
        float markerOutlineThickness = 2.0f;
        SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(style, markerColor, markerSize);
        pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, outlineColor, markerOutlineThickness));
        Graphic pointGraphic = new Graphic(location, pointSymbol);
        mGraphicsOverlay.getGraphics().add(pointGraphic);
    }

    private void setStartMarker(Point location) {
        mGraphicsOverlay.getGraphics().clear();
        setMapMarker(location, SimpleMarkerSymbol.Style.DIAMOND, Color.rgb(226, 119, 40), Color.BLUE);
        mStart = location;
        mEnd = null;
    }

    private void setEndMarker(Point location) {
        setMapMarker(location, SimpleMarkerSymbol.Style.TRIANGLE, Color.rgb(40, 119, 226), Color.RED);
        mEnd = location;
        // findRoute();
    }
}
