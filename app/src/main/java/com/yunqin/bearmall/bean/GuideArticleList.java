package com.yunqin.bearmall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class GuideArticleList implements Parcelable {

    private int hits;
    private int store_id;
    private String image;
    private String createdDate;
    private int guideArticle_id;
    private String caption;
    private String logo;
    private String storeName;
    private String guideVideo;
    private int type;
    private String title;
    private String content;

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getGuideArticle_id() {
        return guideArticle_id;
    }

    public void setGuideArticle_id(int guideArticle_id) {
        this.guideArticle_id = guideArticle_id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getGuideVideo() {
        return guideVideo;
    }

    public void setGuideVideo(String guideVideo) {
        this.guideVideo = guideVideo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.hits);
        dest.writeInt(this.store_id);
        dest.writeString(this.image);
        dest.writeString(this.createdDate);
        dest.writeInt(this.guideArticle_id);
        dest.writeString(this.caption);
        dest.writeString(this.logo);
        dest.writeString(this.storeName);
        dest.writeString(this.guideVideo);
        dest.writeInt(this.type);
        dest.writeString(this.title);
        dest.writeString(this.content);
    }

    public GuideArticleList() {
    }

    protected GuideArticleList(Parcel in) {
        this.hits = in.readInt();
        this.store_id = in.readInt();
        this.image = in.readString();
        this.createdDate = in.readString();
        this.guideArticle_id = in.readInt();
        this.caption = in.readString();
        this.logo = in.readString();
        this.storeName = in.readString();
        this.guideVideo = in.readString();
        this.type = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
    }

    public static final Creator<GuideArticleList> CREATOR = new Creator<GuideArticleList>() {
        @Override
        public GuideArticleList createFromParcel(Parcel source) {
            return new GuideArticleList(source);
        }

        @Override
        public GuideArticleList[] newArray(int size) {
            return new GuideArticleList[size];
        }
    };
}
