package com.chaolemen.shoppingclm.user;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaolemen.mvplibrary.base.BaseFragment;
import com.chaolemen.shoppingclm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment {


    @BindView(R.id.iv_user)
    ImageView ivUser;
    @BindView(R.id.tv_user_login)
    TextView tvUserLogin;
    @BindView(R.id.tv_user_pay)
    TextView tvUserPay;
    @BindView(R.id.tv_user_confirm)
    TextView tvUserConfirm;
    @BindView(R.id.tv_user_completed)
    TextView tvUserCompleted;
    @BindView(R.id.tv_user_order)
    TextView tvUserOrder;
    @BindView(R.id.iv_user_add)
    ImageView ivUserAdd;
    @BindView(R.id.iv_user_share)
    ImageView ivUserShare;
    @BindView(R.id.iv_user_setting)
    ImageView ivUserSetting;
    Unbinder unbinder;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_user, R.id.tv_user_login, R.id.tv_user_pay, R.id.tv_user_confirm, R.id.tv_user_completed, R.id.tv_user_order, R.id.iv_user_add, R.id.iv_user_share, R.id.iv_user_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user:
                Toast.makeText(getActivity(), "头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_login:
//                Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginUserActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_user_pay:
                Toast.makeText(getActivity(), tvUserPay.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_confirm:
                Toast.makeText(getActivity(), tvUserConfirm.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_completed:
                Toast.makeText(getActivity(), tvUserCompleted.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_order:
                Toast.makeText(getActivity(), tvUserOrder.getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_user_add:
                Toast.makeText(getActivity(), "收货管理", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_user_share:
                Toast.makeText(getActivity(), "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_user_setting:
                Toast.makeText(getActivity(), "设置", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
