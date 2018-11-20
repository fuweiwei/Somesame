package com.somesame.somesame.ui.main.release;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseActivity;
import com.somesame.somesame.base.BaseContract;
import com.somesame.somesame.common.ActivityContracts;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/19
 */
@Route(path = ActivityContracts.ACTIVITY_RELEASE)
public class ReleaseActivity extends BaseActivity {
    public static final int REQUEST_CODE_CHOOSE = 1001;
    private List<Uri> mSelected;
    @BindView(R.id.btn_release)
    Button btnRelease;
    @BindView(R.id.rel_add)
    RelativeLayout relAdd;

    @Override
    protected void initView() {
        mBaseToolBar.setToolbarTitle("发布");
    }

    @Override
    protected BaseContract.BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_release;
    }

    @OnClick({R.id.btn_release,R.id.rel_add})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_release:
                break;
            case R.id.rel_add:
                Matisse.from(mContext)
                        .choose(MimeType.allOf()) // 选择 mime 的类型
                        .countable(true)
                        .maxSelectable(6) // 图片选择的最多数量
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.img_height))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f) // 缩略图的比例
                        .theme(R.style.ImgSelectTheme)
                        .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(true,"com.somesame.somesame.fileprovider"))
                        .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
                break;
                default:
                    break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }

}
