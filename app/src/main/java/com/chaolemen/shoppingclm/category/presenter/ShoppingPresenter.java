package com.chaolemen.shoppingclm.category.presenter;

import com.chaolemen.mvplibrary.model.ModelFractory;
import com.chaolemen.mvplibrary.presenter.BasePresenter;
import com.chaolemen.shoppingclm.category.bean.ShoppingBean;
import com.chaolemen.shoppingclm.category.contract.ShoppingContract;
import com.chaolemen.shoppingclm.category.model.ShoppingModel;

public class ShoppingPresenter extends BasePresenter<ShoppingContract.View> implements ShoppingContract.Presenter {
    @Override
    public void getDataShopping() {
        ShoppingModel shoppingModel = ModelFractory.createModel(ShoppingModel.class);
        shoppingModel.getDataShopping(this, getLifecycle());
    }

    @Override
    public void onSuccess(ShoppingBean shoppingBean) {
        mView.onSuccess(shoppingBean);
    }

    @Override
    public void onFail(String error) {
        mView.onFailItem(error);
    }

    @Override
    public void onCancal() {
        mView.onCancal();
    }
}
