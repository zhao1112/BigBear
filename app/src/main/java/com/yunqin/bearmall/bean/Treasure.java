package com.yunqin.bearmall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenchen on 2018/8/12.
 */

public class Treasure implements Parcelable{

    private int purchaseCount;
    private String totalCost;
    private String name;
    private String createdDate;
    private String rewardMember_id;
    private int isReward;
    private long endTime;
    private String reward;
    private int status;
    private int tag;
    private int participationPerson;
    private String participationDate;
    private String img;
    private long restTime;
    private String treasureName;
    private String treasure_id;
    private String cost;
    private String member_id;
    private int isToday;
    private int isBuy;
    private String nickName;
    private String iconUrl;

    public String getIconUrl() {
        return iconUrl;
    }


    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(int isBuy) {
        this.isBuy = isBuy;
    }

    public static Creator<Treasure> getCREATOR() {
        return CREATOR;
    }

    public int getIsToday() {
        return isToday;
    }

    public void setIsToday(int isToday) {
        this.isToday = isToday;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getRewardMember_id() {
        return rewardMember_id;
    }

    public void setRewardMember_id(String rewardMember_id) {
        this.rewardMember_id = rewardMember_id;
    }

    public int getIsReward() {
        return isReward;
    }

    public void setIsReward(int isReward) {
        this.isReward = isReward;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getParticipationPerson() {
        return participationPerson;
    }

    public void setParticipationPerson(int participationPerson) {
        this.participationPerson = participationPerson;
    }

    public String getParticipationDate() {
        return participationDate;
    }

    public void setParticipationDate(String participationDate) {
        this.participationDate = participationDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getRestTime() {
        return restTime;
    }

    public void setRestTime(long restTime) {
        this.restTime = restTime;
    }

    public String getTreasureName() {
        return treasureName;
    }

    public void setTreasureName(String treasureName) {
        this.treasureName = treasureName;
    }

    public String getTreasure_id() {
        return treasure_id;
    }

    public void setTreasure_id(String treasure_id) {
        this.treasure_id = treasure_id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }


    public Treasure() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.purchaseCount);
        dest.writeString(this.totalCost);
        dest.writeString(this.name);
        dest.writeString(this.createdDate);
        dest.writeString(this.rewardMember_id);
        dest.writeInt(this.isReward);
        dest.writeLong(this.endTime);
        dest.writeString(this.reward);
        dest.writeInt(this.status);
        dest.writeInt(this.tag);
        dest.writeInt(this.participationPerson);
        dest.writeString(this.participationDate);
        dest.writeString(this.img);
        dest.writeLong(this.restTime);
        dest.writeString(this.treasureName);
        dest.writeString(this.treasure_id);
        dest.writeString(this.cost);
        dest.writeString(this.member_id);
        dest.writeInt(this.isToday);
    }

    protected Treasure(Parcel in) {
        this.purchaseCount = in.readInt();
        this.totalCost = in.readString();
        this.name = in.readString();
        this.createdDate = in.readString();
        this.rewardMember_id = in.readString();
        this.isReward = in.readInt();
        this.endTime = in.readLong();
        this.reward = in.readString();
        this.status = in.readInt();
        this.tag = in.readInt();
        this.participationPerson = in.readInt();
        this.participationDate = in.readString();
        this.img = in.readString();
        this.restTime = in.readLong();
        this.treasureName = in.readString();
        this.treasure_id = in.readString();
        this.cost = in.readString();
        this.member_id = in.readString();
        this.isToday = in.readInt();
    }

    public static final Creator<Treasure> CREATOR = new Creator<Treasure>() {
        @Override
        public Treasure createFromParcel(Parcel source) {
            return new Treasure(source);
        }

        @Override
        public Treasure[] newArray(int size) {
            return new Treasure[size];
        }
    };
}
