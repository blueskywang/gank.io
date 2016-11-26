package com.example.bluesky.gankio.DateBean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 *
 * 形成的喜欢的类
 * Created by localhost on 2016/11/23.
 */


@Entity
public class LoveDate implements Parcelable {

    @Property(nameInDb = "Title")
    private String desc;
    @Property(nameInDb = "Writer")
    private String who;
    @Property(nameInDb = "ImageURL")
    private String images;
    @Id
    @Property(nameInDb = "InfoURL")
    private String url;
    @Property(nameInDb = "Type")
    private String type;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.desc);
        dest.writeString(this.who);
        dest.writeString(this.images);
        dest.writeString(this.url);
        dest.writeString(this.type);
    }

    protected LoveDate(Parcel in) {

        this.desc = in.readString();
        this.who = in.readString();
        this.images = in.readString();
        this.url = in.readString();
        this.type = in.readString();
    }

    @Generated(hash = 1340819077)
    public LoveDate(String desc, String who, String images, String url, String type) {
        this.desc = desc;
        this.who = who;
        this.images = images;
        this.url = url;
        this.type = type;
    }

    @Generated(hash = 741294278)
    public LoveDate() {
    }
    
    
    public static final Parcelable.Creator<LoveDate> CREATOR = new Parcelable.Creator<LoveDate>() {
        @Override
        public LoveDate createFromParcel(Parcel source) {
            return new LoveDate(source);
        }

        @Override
        public LoveDate[] newArray(int size) {
            return new LoveDate[size];
        }
    };
}
