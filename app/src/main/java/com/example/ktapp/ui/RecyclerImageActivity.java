package com.example.ktapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktapp.R;
import com.example.ktapp.base.BaseDataBindActivity;
import com.example.ktapp.databinding.ActivityRecyclerImageBinding;
import com.example.ktapp.ui.adapter.GridImageAdapter;
import com.example.ktapp.ui.recyclercallback.OnRecyclerItemClickListener;
import com.example.ktapp.ui.recyclercallback.WXTouchHelper;
import com.example.ktapp.ui.view.FullyGridLayoutManager;
import com.example.ktapp.utils.DisplayUtil;
import com.example.ktapp.utils.SDFileSelecteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.ui
 * @ClassName: RecyclerImageActivity
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/8/4 11:16
 * @UpdateUser: LiuTao
 */
public class RecyclerImageActivity extends BaseDataBindActivity<ActivityRecyclerImageBinding> {
    private static final String TAG = "RecyclerImageActivity";

    List<String> list = new ArrayList<>();
    private GridImageAdapter mImageAdapter;
    private ItemTouchHelper itemTouchHelper;
    private int bottomItemHeight;
    private int lineSpace;
    private int leftMargin;
    private int starWidth;
    private int judgeClickMargin;
    private int limit = 9;//限制上传多少张图拍呢

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        dataBind.setListenerv1(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedKmlFile();
            }
        });

        initGrid();
//        fixBottom();

        dataBind.tipseeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerImageActivity.this, "提醒谁看", Toast.LENGTH_SHORT).show();
            }
        });
        dataBind.whoseeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerImageActivity.this, "谁能看", Toast.LENGTH_SHORT).show();

            }
        });
        dataBind.positionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerImageActivity.this, "位置", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initView() {
        //textview 高度
        bottomItemHeight = getResources().getDimensionPixelSize(R.dimen.bottom_textview_height);
        //textview之间分割线 高度
        lineSpace = (int) getResources().getDimension(R.dimen.dimen_1);
        //左边距
        leftMargin = (int) getResources().getDimension(R.dimen.dimen_20);
        //同步到控件 星星的宽度  图片大小为30dp 左右各留10dp 方便用户
        starWidth = (int) getResources().getDimension(R.dimen.dimen_50);
    }

    // 打开系统的文件选择器
    private void selectedKmlFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 1101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1101:
                    if (data == null) {
                        // 用户未选择任何文件，直接返回
                        return;
                    }
                    Uri uri = data.getData();
                    Log.e("ddd", uri.toString() + "");
                    Log.e("ddd", uri.getScheme() + "");
                    Log.e("ddd", uri.getPath() + "");
                    String path = SDFileSelecteUtil.getFilePath(this, data);
                    Log.e("ddd", path + "");
                    list.add(path);
                    if (!TextUtils.isEmpty(path) && path != null) {
                        if (dataBind != null) {
                            dataBind.text2.setText(path + "");
//                            dataBind.image.setImageURI(Uri.parse(path));

                        }
                    }
                    mImageAdapter.setList(list);
                    mImageAdapter.notifyDataSetChanged();
                    break;
            }
        }

    }


    private void initGrid() {


        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        dataBind.recyclerImg.setLayoutManager(manager);
        mImageAdapter = new GridImageAdapter(this, mOnAddPicClickListener, mDelPicClickListener);
        mImageAdapter.setList(list);
        mImageAdapter.setSelectMax(9);
        dataBind.recyclerImg.setAdapter(mImageAdapter);

        WXTouchHelper myCallBack = new WXTouchHelper(mImageAdapter, list, dataBind.scrollView);
        itemTouchHelper = new ItemTouchHelper(myCallBack);
        itemTouchHelper.attachToRecyclerView(dataBind.recyclerImg);


        mImageAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                //
            }
        });
        myCallBack.setDragListener(new WXTouchHelper.DragListener() {
            @Override
            public void deleteState(boolean delete) {
                Log.e(TAG, "deleteState: " + delete);
                if (delete) {
                    dataBind.deleteImg.setAlpha(0.8f);
                    dataBind.deleteImg.setText("松手即可删除");
                } else {
                    dataBind.deleteImg.setAlpha(0.5f);
                    dataBind.deleteImg.setText("拖到此处删除");
                }
            }

            @Override
            public void dragState(boolean start) {
                Log.e(TAG, "dragState: " + start);
                if (start) {
                    dataBind.deleteImg.setVisibility(View.VISIBLE);
                } else {
                    dataBind.deleteImg.setVisibility(View.GONE);
                }
            }

            @Override
            public void clearView() {
                Log.e(TAG, "clearView: ");
//                fixBottom();

            }

            @Override
            public void deleteOk() {
                Log.e(TAG, "deleteOk: ");
                limit = 9 - list.size();

            }
        });

        dataBind.recyclerImg.addOnItemTouchListener(new OnRecyclerItemClickListener(dataBind.recyclerImg) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {

//                if (viewHolder.getAdapterPosition() == list.size()) {
//                    DialogUtil.uploadMultiplePhoto(mActivity, getTakePhoto(), limit);
//                } else {
//                    if (list.size() != 0) {
//                        Intent intent = new Intent(mActivity, BigPhotoActivity.class);
//                        intent.putStringArrayListExtra("imgUrls", (ArrayList<String>) imgSelected);
//                        intent.putExtra("position", viewHolder.getAdapterPosition());
//                        mSwipeBackHelper.forward(intent);
//                    }
//                }
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() != list.size()) {
//                    BGAKeyboardUtil.closeKeyboard(mActivity);
                    itemTouchHelper.startDrag(viewHolder);
                }
            }

            @Override
            public void onOtherClick(MotionEvent e) {
                if (e.getY() > judgeClickMargin) {
                    int between = (int) e.getY() - judgeClickMargin;//判读触摸点与 bottom布局分界处的距离

                    int oneItem = (bottomItemHeight + lineSpace);//一个textview+一个分割线的高度
                    Log.e(TAG, e.getY() + "---" + judgeClickMargin + "===" + oneItem);
                    if (between > 0 && between <= oneItem) {
                        //点击在第一个textview上 ---所在位置
                        Toast.makeText(RecyclerImageActivity.this, "所在位置", Toast.LENGTH_SHORT).show();
                    } else if (between > oneItem && between <= 2 * oneItem) {

                        //点击在第二个textview上 ---谁可以看
                        Toast.makeText(RecyclerImageActivity.this, "谁可以看", Toast.LENGTH_SHORT).show();
                    } else if (between > 2 * oneItem && between <= 3 * oneItem) {

                        //点击在第三个textview上 ---提醒谁看
                        Toast.makeText(RecyclerImageActivity.this, "提醒谁看", Toast.LENGTH_SHORT).show();
                    } else if (between > 3 * oneItem && between <= 4 * oneItem && e.getX() >= leftMargin && e.getX() <= (starWidth + leftMargin)) {
                        //点击星星 同步到空间
                        Toast.makeText(RecyclerImageActivity.this, "同步到空间", Toast.LENGTH_SHORT).show();
                    }

                }
            }


        });


    }

    /**
     * 删除 视频 图片文件
     */
    private GridImageAdapter.DelPicClickListener mDelPicClickListener =
            new GridImageAdapter.DelPicClickListener() {
                @Override
                public void onDelPicClick(List<String> lists) {
                    list = lists;
                }
            };
    /**
     * 添加 视频 图片文件
     */
    private GridImageAdapter.onAddPicClickListener mOnAddPicClickListener =
            new GridImageAdapter.onAddPicClickListener() {
                @Override
                public void onAddPicClick() {
                    selectedKmlFile();
                }
            };

    /**
     * 处理recyclerView下面的布局
     */
    private void fixBottom() {

        int row = mImageAdapter.getItemCount() / 3;
        row = (0 == mImageAdapter.getItemCount() % 3) ? row : row + 1;//少于3为1行
        row = (4 == row) ? 3 : row;//最多为三行

        int width = DisplayUtil.getScreenWidth(this);
        int itemWidth = (int) (width - dataBind.recyclerImg.getLayoutManager().getChildAt(0).getWidth()) / 3;//item宽高
        //顶部的高度
        int marginTop = (getResources().getDimensionPixelSize(R.dimen.recycle_margin_top)
                + itemWidth * row
                + getResources().getDimensionPixelSize(R.dimen.bottom_margin_top));
        //用户判断 在每次fix底部布局高度后判断 注意要减去顶部高度
        judgeClickMargin = marginTop - getResources().getDimensionPixelSize(R.dimen.edittext_height);


        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) dataBind.llBottom.getLayoutParams();
        params.setMargins(0, marginTop, 0, 0);
        dataBind.llBottom.setLayoutParams(params);


    }
}
