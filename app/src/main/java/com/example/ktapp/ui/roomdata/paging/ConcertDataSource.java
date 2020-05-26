package com.example.ktapp.ui.roomdata.paging;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.example.ktapp.data.db.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.ui.roomdata.paging
 * @ClassName: ConcertDataSource
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/5/12 12:09
 * @UpdateUser: LiuTao
 */
public class ConcertDataSource extends PositionalDataSource<User> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<User> callback) {
        callback.onResult(getStudents(0, 20), 0, 500);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<User> callback) {
        callback.onResult(getStudents(params.startPosition, params.loadSize));
    }


    private List<User> getStudents(int startPosition, int pageSize) {
        List<User> list = new ArrayList<>();
        for (int i = startPosition; i < startPosition + pageSize; i++) {
            User user = new User();
            if (i % 2 == 1) {
                user.setName("张三");
                user.setSex("男");
            } else {
                user.setName("艾利");
                user.setSex("女");
            }
            list.add(user);
        }
        return list;
    }
}
