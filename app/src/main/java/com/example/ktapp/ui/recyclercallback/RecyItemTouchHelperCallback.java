package com.example.ktapp.ui.recyclercallback;

import android.graphics.Color;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktapp.ui.adapter.GridImageAdapter;

import java.util.Collections;


/**
 * created by dalang at 2018/11/15
 *
 * 仅拖拽排序 不含微信拖拽删除
 */
public class RecyItemTouchHelperCallback extends ItemTouchHelper.Callback {
    RecyclerView.Adapter mAdapter;
    boolean isSwipeEnable;
    boolean isFirstDragUnable;

    public RecyItemTouchHelperCallback(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        isSwipeEnable = false;
        isFirstDragUnable = false;
    }

    public RecyItemTouchHelperCallback(RecyclerView.Adapter adapter, boolean isSwipeEnable, boolean isFirstDragUnable) {
        mAdapter = adapter;
        this.isSwipeEnable = isSwipeEnable;
        this.isFirstDragUnable = isFirstDragUnable;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (isFirstDragUnable && toPosition == 0) {
            return false;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(((GridImageAdapter) mAdapter).getData(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(((GridImageAdapter) mAdapter).getData(), i, i - 1);
            }
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {


        int adapterPosition = viewHolder.getAdapterPosition();
        mAdapter.notifyItemRemoved(adapterPosition);
        ((GridImageAdapter) mAdapter).getData().remove(adapterPosition);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipeEnable;
    }


}
