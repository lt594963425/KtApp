package com.example.ktapp.ui.roomdata.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.ktapp.R;
import com.example.ktapp.data.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.ui.roomdata.adapter
 * @ClassName: RoomDataAdapter
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/5/12 11:27
 * @UpdateUser: LiuTao
 */
public class RoomDataAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    public RoomDataAdapter(List<User> list) {
        super(R.layout.item_room_data, list);
    }
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, User user) {
        baseViewHolder.setText(R.id.text_tv, user.toString());
    }

}
