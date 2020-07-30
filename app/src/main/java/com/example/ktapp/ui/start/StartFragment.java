package com.example.ktapp.ui.start;

import androidx.lifecycle.Observer;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.esri.arcgisruntime.geometry.CoordinateFormatter;
import com.esri.arcgisruntime.geometry.GeographicTransformation;
import com.esri.arcgisruntime.geometry.GeographicTransformationStep;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.example.ktapp.R;
import com.example.ktapp.base.LazyLoadFragment;
import com.example.ktapp.base.LiveDataBus;
import com.example.ktapp.data.People;
import com.example.ktapp.databinding.StartFragmentBinding;


/**
 * Mvvm  ViewModel  +LiveData +dataBinding +Lifecycle
 */
public class StartFragment extends LazyLoadFragment<StartFragmentBinding> {


    private String start;
    private double x;
    private double y;

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public StartViewModel getViewModel() {
        return getViewModel(StartViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.start_fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLifecycle().addObserver(getViewModel());
        if (getArguments() != null) {
            start = getArguments().getString("START");
            dataBind.changeMsg.setText(start + "");
        }

        getViewModel().getUsers().observe(getViewLifecycleOwner(), new Observer<People>() {
            @Override
            public void onChanged(People user) {
                dataBind.message.setText(user.getName());
            }
        });
        dataBind.changeMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mViewModel.updateData("改变了");
//                getViewModel().getUsers().setValue(new People("hahahahha", "20"));
                LiveDataBus.get().with("Key_Start").postValue("哈哈哈哈哈哈哈哈哈nihao ");

                //拍照
//                takePhoto();
            }
        });

        LiveDataBus.get()
                .with("Key_Start", String.class)
                .observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Log.e("StartFragment", "" + s);
                        dataBind.message.setText(s);

                    }
                });





//        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());
//
//        cameraProviderFuture.addListener(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //将相机的生命周期和activity的生命周期绑定，camerax 会自己释放，不用担心了
//                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
//                    //预览的 capture，它里面支持角度换算
//                    Preview preview = new Preview.Builder().build();
//
//                    //创建图片的 capture
//                    mImageCapture = new ImageCapture.Builder()
//                            .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
//                            .build();
//
//
//                    //选择后置摄像头
//                    CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
//
//                    //预览之前先解绑
//                    cameraProvider.unbindAll();
//
//                    //将数据绑定到相机的生命周期中
//                    Camera camera = cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, preview, mImageCapture);
//                    //将previewview 的 surface 给相机预览
//                    preview.setSurfaceProvider(dataBind.viewFinder.createSurfaceProvider(camera.getCameraInfo()));
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, ContextCompat.getMainExecutor(getContext()));


    }
//    public void takePhoto() {
//        if (mImageCapture != null) {
//            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            //创建文件
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"testx.jpg");
//            if (file.exists()) {
//                file.delete();
//            }
//            //创建包文件的数据，比如创建文件
//            ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
//
//            //开始拍照
//            mImageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(getContext()), new ImageCapture.OnImageSavedCallback() {
//                @Override
//                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
//                    //    Uri savedUri = outputFileResults.getSavedUri();
//                    Toast.makeText(getContext(), "保存成功: ", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onError(@NonNull ImageCaptureException exception) {
//                    Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }



    /***
     * 1.X：4511673.43 Y：36600622.26
     * 2.X：4511701.41 Y：36602700.27
     * 3.X：4510851.41 Y：36602700.27
     * 4.X：4510851.42 Y：36000622.26
     *
     * 1, 109° 11‘ 28.185581",40° 44' 00.944710"
     * 2, 109° 12’ 56.741167",40 ° 44 '00.’928312"
     * 3, 109 ° 12 ‘56.239724",40 ° 43 ' 33.379060"
     * 4, 109 ° 11 ’27.710457",40 ° 43 '34.302399"
     */


    @Override
    protected void initData() {
        super.initData();
        getViewModel().loadUsers();
    }

    @Override
    protected void initEvent() {
        super.initEvent();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("StartFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("StartFragment", "onPause");
    }

    //    @Override
//    private void onResume() {
//        super.onResume();
//        Log.d("StartFragment", "onResume");
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("StartFragment", "onPause");
//    }
}
