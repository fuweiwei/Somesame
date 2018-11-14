package com.somesame.somesame.net;


import com.somesame.somesame.base.BaseResult;
import com.somesame.somesame.model.BookModel;
import com.somesame.somesame.model.OtherProductModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 请求数据接口
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/7/2
 */

public interface ApiService {

    /**
     * 广告banner
     * @return
     */
    @GET("/ace-app/bannerInfo/{id}")
    Observable<BaseResult<List<OtherProductModel>>> getBannerInfo(@Path("id") String id);
    /**
     * 搜索图书
     * @param q
     * @param tag
     * @param start
     * @param end
     * @return
     */
    @GET("book/search")
    Observable<BookModel> getBooks(@Query("q") String q,
                                   @Query("tag") String tag,
                                   @Query("start") String start,
                                   @Query("end") String end);


}
