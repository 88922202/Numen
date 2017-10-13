package com.day.numen.contacts;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.day.numen.R;
import com.day.numen.common.BaseFragment;
import com.day.numen.contacts.model.Contact;
import com.day.numen.databinding.FragmentContactListBinding;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by chenning on 17-9-28.
 * <p>
 * 联系人列表
 */

public class ContactListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = ContactListFragment.class.getSimpleName();

    private FragmentContactListBinding mFragmentBinding;

    private ContactListAdapter mContactListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_list, container, false);
        initView();
        return mFragmentBinding.getRoot();
    }

    private void initView() {
//        mFragmentBinding.refreshLayout.setColorSchemeColors(Color.argb());
        mFragmentBinding.refreshLayout.setOnRefreshListener(this);

        mContactListAdapter = new ContactListAdapter(getActivity());

        mFragmentBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mFragmentBinding.recyclerView.setAdapter(mContactListAdapter);

        initData();
    }

    private void initData() {
        getContactList();
    }

    private void getContactList() {
        ArrayList<Contact> list = getContacts();
        Log.d(TAG, "Contacts: " + Arrays.toString(list.toArray(new Contact[list.size()])));

        mContactListAdapter.setData(list);

        if (mFragmentBinding.refreshLayout != null && mFragmentBinding.refreshLayout.isRefreshing()) {
            mFragmentBinding.refreshLayout.setRefreshing(false);
        }
    }

    private ArrayList<Contact> getContacts() {
        ArrayList<Contact> contactList = new ArrayList<>();
        Cursor cursor = null;
        try {
            // 查询联系人数据
            cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                // 获取联系人姓名
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                // 获取联系人手机号
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactList.add(new Contact(displayName, number));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contactList;
    }

    @Override
    public void onRefresh() {
        getContactList();
    }
}
