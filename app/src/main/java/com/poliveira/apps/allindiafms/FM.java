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
    int id;
    String name;
    String url;
    String imageurl;

    public FM() {

    }

    public FM(int id, String name, String url, String slogan) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.imageurl = slogan;
    }


    public FM(Parcel source) {
        name = source.readString();
        url = source.readString();
        imageurl = source.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(imageurl);
    }

    public int getId() {
        return id;
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
            FM fm = new FM(jo.getInt(AppConst.JSON_FM_ID), jo.getString(AppConst.JSON_FM_NAME), jo.getString(AppConst.JSON_FM_URL), jo.getString(AppConst.JSON_FM_IMAGE_URL));
            fm.print();
            return fm;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void print() {
        Log.d(TAG, "Id:" + id + "  Name :" + name + "   Url :" + url + " ImageUrl : " + imageurl);
    }
}
