package com.day.numen.urgent;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.day.numen.R;
import com.day.numen.common.BaseFragment;
import com.day.numen.common.NumenOrmHelper;
import com.day.numen.common.OttoBus;
import com.day.numen.databinding.FragmentUrgentContactListBinding;
import com.day.numen.urgent.event.MessageEvent;
import com.day.numen.urgent.model.UrgentContact;
import com.squareup.otto.Subscribe;

import org.cn.orm.Query;
import org.cn.orm.SimpleOrmHelper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chenning on 17-9-28.
 * <p>
 * 紧急联系人列表
 */

public class UrgentContactListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = UrgentContactListFragment.class.getSimpleName();

    private FragmentUrgentContactListBinding mFragmentBinding;

    private UrgentContactListAdapter mContactListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_urgent_contact_list, container, false);
        initView(mFragmentBinding.getRoot());
        OttoBus.getInstance().register(this);
        return mFragmentBinding.getRoot();
    }

    private void initView(View view) {
//        mFragmentBinding.refreshLayout.setColorSchemeColors(Color.argb());
        mFragmentBinding.refreshLayout.setOnRefreshListener(this);

        mContactListAdapter = new UrgentContactListAdapter(getActivity());

        mFragmentBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mFragmentBinding.recyclerView.setAdapter(mContactListAdapter);

        initData();
    }

    private void initData() {
        getContactList();
    }

    @Override
    public void onDestroyView() {
        OttoBus.getInstance().unregister(this);
        super.onDestroyView();
    }

    private void getContactList() {
        List<UrgentContact> list = getContacts();
        Log.d(TAG, "Contacts: " + Arrays.toString(list.toArray(new UrgentContact[list.size()])));

        mContactListAdapter.setData(list);

        if (mFragmentBinding.refreshLayout != null && mFragmentBinding.refreshLayout.isRefreshing()) {
            mFragmentBinding.refreshLayout.setRefreshing(false);
        }
    }

    private List<UrgentContact> getContacts() {
        SimpleOrmHelper helper = NumenOrmHelper.getInstance();
        Query query = helper.createQuery("SELECT * FROM urgent_contact ORDER BY _timestamp DESC LIMIT 0, 5").addEntity(UrgentContact.class);
        List<UrgentContact> list = query.list();
        return list;
    }

    @Override
    public void onRefresh() {
        getContactList();
    }

    @Subscribe
    public void onMessageEvent(MessageEvent message) {
        getContactList();
    }
}
