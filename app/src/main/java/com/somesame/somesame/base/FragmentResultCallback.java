package com.somesame.somesame.base;

import android.content.Intent;

/***
 * 回调接口
 * @author Veer
 * @email  276412667@qq.com
 * @date   18/11/20
 */
public interface FragmentResultCallback {

    /**
     * 结果反馈回调
     * @param requestCode 请求编码
     * @param resultCode 结果编码
     * @param data
     * @see [类、类#方法、类#成员]
     */
    void onFragmentResult(int requestCode, int resultCode, Intent data);

}
