package com.poliveira.apps.allindiafms;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by i80429 on 2/20/2015.
 */
public class FM implements Parcelable {
    String TAG = FM.class.getName();

    String fmid;
    String name;
    String imageurl;
    String address;
    String weburl;
    String url;
    String slogan;
    String genre;
    String favourite;

    public FM() {

    }

    public FM(String fmid, String name, String imageurl, String address, String weburl, String url, String slogan, String genre, String favourite) {
        this.fmid = fmid;
        this.name = name;
        this.imageurl = imageurl;
        this.address = address;
        this.weburl = weburl;
        this.url = url;
        this.weburl = weburl;
        this.slogan = slogan;
        this.genre = genre;
        this.favourite = favourite;
    }


    public FM(Parcel source) {
        fmid = source.readString();
        name = source.readString();
        imageurl = source.readString();
        address = source.readString();
        weburl = source.readString();
        url = source.readString();
        slogan = source.readString();
        genre = source.readString();
        favourite = source.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fmid);
        dest.writeString(name);
        dest.writeString(imageurl);
        dest.writeString(address);
        dest.writeString(weburl);
        dest.writeString(url);
        dest.writeString(slogan);
        dest.writeString(genre);
        dest.writeString(favourite);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getGenre() {
        return genre;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<FM> CREATOR = new Parcelable.Creator<FM>() {
        @Override
        public FM createFromParcel(Parcel source) {
            return new FM(source);
        }

        @Override
        public FM[] newArray(int size) {
            return new FM[size];
        }
    };

    public static FM jsonToFM(JSONObject jo) {
        try {
            FM fm = new FM(

                    jo.getString(AppConst.JSON_FM_ID),
                    jo.getString(AppConst.JSON_FM_NAME),
                    jo.getString(AppConst.JSON_FM_IMAGE_URL),
                    jo.getString(AppConst.JSON_FM_ADDRESS),
                    jo.getString(AppConst.JSON_FM_WBURL),
                    jo.getString(AppConst.JSON_FM_URL),
                    jo.getString(AppConst.JSON_FM_SLOGAN),
                    jo.getString(AppConst.JSON_FM_GENRE), "N");
            fm.print();
            return fm;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void print() {
        Log.d(TAG, "\tId:" + fmid + "  Name :" + name + "   Url :" + url + " ImageUrl : " + imageurl);
    }
}
