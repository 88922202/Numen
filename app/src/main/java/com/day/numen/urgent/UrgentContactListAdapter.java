package com.day.numen.urgent;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.day.numen.R;
import com.day.numen.databinding.ItemUrgentContactListBinding;
import com.day.numen.urgent.helper.UrgentHelper;
import com.day.numen.urgent.model.UrgentContact;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by work on 17-9-28.
 * <p>
 * 紧急联系人列表适配器
 */

public class UrgentContactListAdapter extends RecyclerView.Adapter<UrgentContactListAdapter.ViewHolder> {

    private WeakReference<Context> mContextRef;

    private LayoutInflater inflater;

    private ArrayList<UrgentContact> mDataCache = new ArrayList<>();

    public UrgentContactListAdapter(Context ctx) {
        mContextRef = new WeakReference<>(ctx);
        inflater = LayoutInflater.from(ctx);
    }

    public void setData(List<UrgentContact> data) {
        mDataCache.clear();
        if (data != null && !data.isEmpty()) {
            mDataCache.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemUrgentContactListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_urgent_contact_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBindData(mDataCache.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataCache.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        ItemUrgentContactListBinding mItemBinding;

        UrgentContact itemData;

        public ViewHolder(ItemUrgentContactListBinding binding) {
            super(binding.getRoot());
            mItemBinding = binding;

            itemView.setOnLongClickListener(this);
        }

        public void onBindData(UrgentContact data) {
            itemData = data;
            mItemBinding.setUrgentContact(data);
        }

        @Override
        public boolean onLongClick(View view) {
            if (itemData != null && mContextRef != null) {
                Context ctx = mContextRef.get();
                if (ctx != null) {
                    Snackbar.make(view, R.string.tip_urgent_contact_remove, Snackbar.LENGTH_LONG)
                            .setAction(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    UrgentHelper.removeUrgentContact(itemData);
                                }
                            })
                            .show();
                }
            }
            return true;
        }

    }
}
