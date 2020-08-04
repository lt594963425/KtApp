package com.example.ktapp.ui.roomdata;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.ktapp.R;
import com.example.ktapp.base.LazyLoadFragment;
import com.example.ktapp.data.db.User;
import com.example.ktapp.data.db.UserDatabase;
import com.example.ktapp.databinding.RoomDataFragmentBinding;
import com.example.ktapp.ui.roomdata.adapter.RoomDataAdapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RoomDataFragment extends LazyLoadFragment<RoomDataFragmentBinding> {

    private RoomDataViewModel mViewModel;

    public RoomDataAdapter roomDataAdapter;

    public static RoomDataFragment newInstance() {
        return new RoomDataFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.room_data_fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = get();
        mViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    roomDataAdapter.getData().clear();
                    roomDataAdapter.addData(user);
                    roomDataAdapter.notifyDataSetChanged();
                }
            }
        });
        mViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                roomDataAdapter.setNewInstance(users);

            }
        });

        dataBind.addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("艾莉", "20", "女");
                UserDatabase.getInstance(getContext()).getUserDao().insert(user);
                roomDataAdapter.addData(user);

            }
        });
        dataBind.queryUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get().queryAllUsers();
            }
        });
        dataBind.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDatabase.getInstance(getContext()).getUserDao().deleteAll();

                get().queryAllUsers();
            }
        });
        dataBind.updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomDataAdapter.getData().size() > 0) {
                    User user = roomDataAdapter.getData().get(0);
                    user.setName("张三");
                    user.setSex("男");
                    user.setAge("30");
                    UserDatabase.getInstance(getContext()).getUserDao().update(user);
                    roomDataAdapter.notifyDataSetChanged();
                }
            }
        });
        dataBind.queryByUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get().getUser().postValue(UserDatabase.getInstance(getContext()).getUserDao().getUser("张三"));
            }
        });

        initRecycler();
    }

    private void initRecycler() {
        roomDataAdapter = new RoomDataAdapter(null);
        roomDataAdapter.setAnimationEnable(true);
        View view = View.inflate(getContext(), R.layout.item_room_data_empty, null);
        roomDataAdapter.setEmptyView(view);
        roomDataAdapter.addChildClickViewIds(R.id.delete_tv);
        dataBind.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_divider));
        dataBind.recycler.addItemDecoration(divider);
        dataBind.recycler.setAdapter(roomDataAdapter);
        roomDataAdapter.setEmptyView(view);
//        roomDataAdapter.setDiffCallback(new DiffDemoCallback());
        roomDataAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

            }
        });
        roomDataAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.delete_tv) {
                    User user = (User) adapter.getData().get(position);
                    UserDatabase.getInstance(getContext()).getUserDao().delete(user);
                    adapter.getData().remove(position);
                    adapter.notifyItemRemoved(position);
                }
            }
        });



    }

    public RoomDataViewModel get() {
        return getViewModel(RoomDataViewModel.class);
    }

    // 获得当前CPU的核心数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    // 设置线程池的核心线程数2-4之间,但是取决于CPU核数
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    public void createTheadPool() {

        ExecutorService executorService = Executors.newFixedThreadPool(CORE_POOL_SIZE);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Log.e("线程池", "---------------------------------------");
            }
        });
    }
}
