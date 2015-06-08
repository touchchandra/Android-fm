package com.poliveira.apps.allindiafms.utils;

import android.content.Context;

import com.poliveira.apps.allindiafms.FM;
import com.poliveira.apps.allindiafms.FMDao;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by i80429 on 3/12/2015.
 */
public class AppHelper {

    public static ArrayList<FM> parseAndLoad(Context ctx, JSONArray jsonArray) {
        ArrayList<FM> fms = new ArrayList<FM>();
        FMDao fmDao = new FMDao(ctx);
        fmDao.open();
        fmDao.deleteAll();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                FM fm = FM.jsonToFM(jsonArray.getJSONObject(i));
                if (fm != null) {
                    fmDao.addFM(fm);
                    fms.add(fm);
                    fm.print();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        fmDao.close();
        return fms;
    }

}
