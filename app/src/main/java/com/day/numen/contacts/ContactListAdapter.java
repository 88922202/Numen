package com.day.numen.contacts;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.day.numen.R;
import com.day.numen.contacts.model.Contact;
import com.day.numen.databinding.ItemContactListBinding;
import com.day.numen.urgent.helper.UrgentHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by work on 17-9-28.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private WeakReference<Context> mContextRef;

    private LayoutInflater inflater;

    private ArrayList<Contact> mDataCache = new ArrayList<>();

    public ContactListAdapter(Context ctx) {
        mContextRef = new WeakReference<>(ctx);
        inflater = LayoutInflater.from(ctx);
    }

    public void setData(ArrayList<Contact> data) {
        mDataCache.clear();
        if (data != null && !data.isEmpty()) {
            mDataCache.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemContactListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_contact_list, parent, false);
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemContactListBinding mItemBinding;

        Contact itemData;

        public ViewHolder(ItemContactListBinding binding) {
            super(binding.getRoot());
            mItemBinding = binding;

            itemView.setOnClickListener(this);
        }

        public void onBindData(Contact data) {
            itemData = data;
            mItemBinding.setContact(data);
        }

        @Override
        public void onClick(View view) {
            if (itemData != null && mContextRef != null) {
                Context ctx = mContextRef.get();
                if (ctx != null) {
                    Snackbar.make(view, R.string.tip_urgent_contact_add, Snackbar.LENGTH_LONG)
                            .setAction(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    UrgentHelper.addUrgentContact(view, itemData);
                                }
                            })
                            .show();
                }
            }
            //return true;
        }
    }
}
