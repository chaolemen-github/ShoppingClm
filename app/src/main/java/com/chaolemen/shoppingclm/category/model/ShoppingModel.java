package com.chaolemen.shoppingclm.category.model;

import com.chaolemen.httplibrary.client.HttpClient;
import com.chaolemen.httplibrary.utils.JsonUtils;
import com.chaolemen.shoppingclm.category.bean.ShoppingBean;
import com.chaolemen.shoppingclm.category.contract.ShoppingContract;
import com.chaolemen.shoppingclm.net.CGHttpCallBack;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

public class ShoppingModel implements ShoppingContract.Model {
    @Override
    public void getDataShopping(final ShoppingContract.ShoppingCallBack shoppingCallBack, LifecycleProvider lifecycleProvider) {

        new HttpClient.Builder()
                .post()
                .setJsonBody("", true)
                .setApiUrl("")
                .build()
                .request(new CGHttpCallBack<ShoppingBean>() {
                    @Override
                    public void onError(String message, int code) {
                        shoppingCallBack.onFail(message + code);
                    }

                    @Override
                    public void cancle() {
                        shoppingCallBack.onCancal();
                    }

                    @Override
                    public void onSuccess(ShoppingBean shoppingBean) {
                        shoppingCallBack.onSuccess(shoppingBean);
                    }

                    @Override
                    public ShoppingBean convert(JsonElement result) {
                        return JsonUtils.jsonToClass(result, ShoppingBean.class);
                    }
                });
    }
}
