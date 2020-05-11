package com.example.ktapp.ui.roomdata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ktapp.R;
import com.example.ktapp.base.LazyLoadFragment;
import com.example.ktapp.data.User;
import com.example.ktapp.data.db.UserDatabase;
import com.example.ktapp.databinding.RoomDataFragmentBinding;

import java.util.List;

import javax.inject.Inject;


public class RoomDataFragment extends LazyLoadFragment<RoomDataFragmentBinding> {

    private RoomDataViewModel mViewModel;
    private User user;

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
        mViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    dataBind.showData.setText(user.toString());
                }
            }
        });
        mViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                dataBind.showData.setText("");
                if (users.size() > 0) {
                    user = users.get(0);
                }
                for (int i = 0; i < users.size(); i++) {
                    Log.e("用户查询", users.get(i).toString());
                    dataBind.showData.append(users.get(i).toString() + "\n");
                }
            }
        });
        dataBind.addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDatabase.getInstance(getContext()).getUserDao().insert(new User("艾莉", "20", "女"));
                get().queryAllUsers();
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
                dataBind.showData.setText("");
                user = null;
                get().queryAllUsers();
            }
        });
        dataBind.updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    user.setName("张三");
                    user.setSex("男");
                    user.setAge("30");
                    UserDatabase.getInstance(getContext()).getUserDao().update(user);
                    get().queryAllUsers();
                }
            }
        });
        dataBind.queryByUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get().getUser().postValue(UserDatabase.getInstance(getContext()).getUserDao().getUser("张三"));
            }
        });
    }

    public RoomDataViewModel get() {
        return getViewModel(RoomDataViewModel.class);
    }
}
