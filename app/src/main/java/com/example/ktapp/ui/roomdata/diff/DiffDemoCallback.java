package com.example.ktapp.ui.roomdata.diff;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.ktapp.data.User;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.ui.roomdata.diff
 * @ClassName: DiffDemoCallback
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/5/12 12:20
 * @UpdateUser: LiuTao
 */
public class DiffDemoCallback  extends DiffUtil.ItemCallback<User> {
    /**
     * 判断是否是同一个item
     *
     * @param oldItem New data
     * @param newItem old Data
     * @return
     */
    @Override
    public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
//        return false;
        return oldItem.getId() == newItem.getId();
    }

    /**
     * 当是同一个item时，再判断内容是否发生改变
     *
     * @param oldItem New data
     * @param newItem old Data
     * @return
     */
    @Override
    public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
        return oldItem.getName().equals(newItem.getName())
                && oldItem.getSex().equals(newItem.getSex())
                && oldItem.getAge().equals(newItem.getAge());
    }

//    /**
//     * 可选实现
//     * 如果需要精确修改某一个view中的内容，请实现此方法。
//     * 如果不实现此方法，或者返回null，新整个将会直接刷item。
//     *
//     * @param oldItem Old data
//     * @param newItem New data
//     * @return Payload info. if return null, the entire item will be refreshed.
//     */
//    @Override
//    public Object getChangePayload(@NonNull User oldItem, @NonNull User newItem) {
//        return newItem;
//    }
}
