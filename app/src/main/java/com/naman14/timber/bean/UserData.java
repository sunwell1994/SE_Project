package com.naman14.timber.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by sunwell on 15/12/27.
 * 简单的用户个人数据表，等待添加和补全
 */
public class UserData extends BmobObject {
//    姓名
    private String userName;
//    个性签名
    private String userSign;
//    用户性别
    private Boolean male;
//    用户头像
    private BmobFile userPic;
    //粉丝
    private BmobRelation follower;
    //关注
    private BmobRelation follow;
    public String getPlayerName() {
        return userName;
    }

    public void setPlayerName(String playerName) {
        this.userName = playerName;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public Boolean getMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public BmobFile getUserPic() {
        return userPic;
    }

    public void setUserPic(BmobFile userPic) {
        this.userPic = userPic;
    }

    public BmobRelation getFollower() {
        return follower;
    }

    public void setFollower(BmobRelation follower) {
        this.follower = follower;
    }

    public BmobRelation getFollow() {
        return follow;
    }

    public void setFollow(BmobRelation follow) {
        this.follow = follow;
    }
}
