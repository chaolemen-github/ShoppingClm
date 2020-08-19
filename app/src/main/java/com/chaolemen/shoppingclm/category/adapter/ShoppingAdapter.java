package com.chaolemen.shoppingclm.category.adapter;

import android.content.Context;

import com.chaolemen.mvplibrary.adapter.BaseAdapter;
import com.chaolemen.mvplibrary.adapter.BaseViewHolder;
import com.chaolemen.shoppingclm.category.bean.ShoppingBean;

import java.util.List;

public class ShoppingAdapter extends BaseAdapter<ShoppingBean> {

    public ShoppingAdapter(List<ShoppingBean> datas, Context context, int itemlayoutId) {
        super(datas, context, itemlayoutId);
    }

    @Override
    protected void bindData(BaseViewHolder holder, ShoppingBean shoppingBean) {

    }
}
