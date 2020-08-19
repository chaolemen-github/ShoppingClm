package com.chaolemen.shoppingclm.category.contract;

import com.chaolemen.mvplibrary.model.BaseModel;
import com.chaolemen.mvplibrary.view.BaseView;
import com.chaolemen.shoppingclm.category.bean.ShoppingBean;
import com.trello.rxlifecycle2.LifecycleProvider;

public interface ShoppingContract {
    interface Model extends BaseModel {
        void getDataShopping(ShoppingCallBack shoppingCallBack, LifecycleProvider lifecycleProvider);
    }

    interface View extends BaseView {
        void onSuccess(ShoppingBean shoppingBean);
    }

    interface Presenter extends ShoppingCallBack{
        void getDataShopping();
    }

    interface ShoppingCallBack{
        void onSuccess(ShoppingBean shoppingBean);
        void onFail(String error);
        void onCancal();
    }
}
