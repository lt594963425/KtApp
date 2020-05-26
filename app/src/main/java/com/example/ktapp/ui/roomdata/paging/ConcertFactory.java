package com.example.ktapp.ui.roomdata.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.ktapp.data.db.User;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.ui.roomdata.paging
 * @ClassName: ConcertFactory
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/5/12 12:12
 * @UpdateUser: LiuTao
 */
public class ConcertFactory extends DataSource.Factory<Integer, User> {

    //paging
    private MutableLiveData<ConcertDataSource> mSourceLiveData = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        ConcertDataSource concertDataSource = new ConcertDataSource();
        mSourceLiveData.postValue(concertDataSource);
        return concertDataSource;
    }
}
