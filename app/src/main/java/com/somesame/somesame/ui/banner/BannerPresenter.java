package com.somesame.somesame.ui.banner;


import com.somesame.somesame.base.BasePresenter;
import com.somesame.somesame.base.BaseResult;
import com.somesame.somesame.model.OtherProductModel;
import com.somesame.somesame.net.RetrofitHelper;
import com.somesame.somesame.net.RxSchedulers;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/7/2
 */

public class BannerPresenter extends BasePresenter<BannerContract.View> implements BannerContract.Presenter{

    @Override
    public void getBanner(String id) {
        RetrofitHelper.getInstance().getServer()
                .getBannerInfo(id)
                .compose(RxSchedulers.<BaseResult<List<OtherProductModel>>>applySchedulers())
                .compose(mView.<BaseResult<List<OtherProductModel>>>bindToLife())
                .subscribe(new Consumer<BaseResult<List<OtherProductModel>>>() {
                    @Override
                    public void accept(BaseResult<List<OtherProductModel>> listBaseResult) throws Exception {
                        mView.setBanner(listBaseResult.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFailed("网络错误，请稍后重试！");
                    }
                });
    }
}
