package com.chaolemen.shoppingclm.category;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chaolemen.httplibrary.utils.LogUtils;
import com.chaolemen.mvplibrary.base.BaseMvpActivity;
import com.chaolemen.shoppingclm.R;
import com.chaolemen.shoppingclm.category.adapter.ShoppingAdapter;
import com.chaolemen.shoppingclm.category.bean.ShoppingBean;
import com.chaolemen.shoppingclm.category.contract.ShoppingContract;
import com.chaolemen.shoppingclm.category.presenter.ShoppingPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingActivity extends BaseMvpActivity<ShoppingContract.View, ShoppingPresenter> implements ShoppingContract.View {

    @BindView(R.id.recycler_shopping)
    RecyclerView mRecyclerShopping;
    private ShoppingAdapter shoppingAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping;
    }

    @Override
    protected void initData() {
//        mPresenter.getDataShopping();

        mRecyclerShopping.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerShopping.addItemDecoration(new DividerItemDecoration(this,RecyclerView.VERTICAL));
        List<ShoppingBean> list = new ArrayList<>();
        shoppingAdapter = new ShoppingAdapter(list, this, R.layout.activity_shopping_item);
        mRecyclerShopping.setAdapter(shoppingAdapter);
    }

    @Override
    protected ShoppingPresenter initPresenter() {
        return new ShoppingPresenter();
    }

    @Override
    public void onSuccess(ShoppingBean shoppingBean) {

    }

    @Override
    public void onFailItem(String error) {
        LogUtils.e(error);
    }

    @Override
    public void onCancal() {
        mPresenter.onCancal();
    }

}
