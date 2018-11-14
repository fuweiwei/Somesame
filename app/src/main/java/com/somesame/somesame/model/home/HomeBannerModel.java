package com.somesame.somesame.model.home;

import java.io.Serializable;

/**
 * 描述
 * Created by Veer
 * date 17/12/26.
 */

public class HomeBannerModel implements Serializable {
    private String bannerId;
    private String imageUrl;
    private String htmlUrl;

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }
}
