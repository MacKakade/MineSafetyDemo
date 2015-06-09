package com.dmi.mobile.inspection.repository;

import android.content.Context;
import android.util.Log;

import com.dmi.mobile.inspection.DmiMobileInspection;
import com.dmi.mobile.inspection.greendao.Citation;
import com.dmi.mobile.inspection.greendao.CitationDao;

import java.util.List;

/**
 * Created by Mandar on 10/16/2014.
 */
public class CitationRepository {

    public static final Long ID = 1L;

    public static void insertOrUpdate(Context context,
            Citation basicInformation) {
        getCitationDAO(context).insertOrReplace(basicInformation);
    }

    public static void update(Context context,
            Citation basicInformation) {
        getCitationDAO(context)
                .update(basicInformation);
    }

    public static void deleteCitation(Context context,
            Citation basicInformation) {
        getCitationDAO(context).delete(basicInformation);
    }

    public static void deleteCitation(Context context) {
        Log.d("BasicInfoRepository", "deleteBasicInformation");
        getCitationDAO(context).deleteAll();
    }

    //Resets all locally changed properties of the entity by reloading the values from the database.
    public static void refreshCitation(Context context,
            Citation basicInformation) {
        getCitationDAO(context).refresh(basicInformation);
    }


    public static Citation getCitation(Context context) {
        return getCitationDAO(context).load(ID);
    }

    public static List<Citation> getAllCitations(Context context) {
        return getCitationDAO(context).loadAll();
    }


    private static CitationDao getCitationDAO(
            Context context) {
        return ((DmiMobileInspection) context.getApplicationContext())
                .getDaoSession().getCitationDao();

    }


}
