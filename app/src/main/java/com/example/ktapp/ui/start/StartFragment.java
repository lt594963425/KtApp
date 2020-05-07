package com.example.ktapp.ui.start;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ktapp.R;
import com.example.ktapp.User;
import com.example.ktapp.databinding.StartFragmentBinding;

/**
 * Mvvm  ViewModel  +LiveData +dataBinding +Lifecycle
 */
public class StartFragment extends Fragment {

    private StartFragmentBinding startFragmentBinding;

    public static StartFragment newInstance() {
        return new StartFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //dataBinding+ViewModel+Lifecycle+LiveData
        startFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.start_fragment, container, false);
        StartViewModel mViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(StartViewModel.class);
        getLifecycle().addObserver(mViewModel);
        View view = startFragmentBinding.getRoot();
        mViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                startFragmentBinding.message.setText(user.getName());
            }
        });
        startFragmentBinding.changeMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mViewModel.updateData("改变了");
                mViewModel.getUsers().setValue(new User("hahahahha"));
            }
        });
        return view;
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
