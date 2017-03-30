package com.gopettingcodingchallenge.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by jaypoojara on 15-02-2017.
 */

public class Utils {
    public static SharedPreferences getSharedPreference(Context context){
        return context.getSharedPreferences(Constants.PREFERENCE_NAME,Context.MODE_PRIVATE);
    }
    public static int getRandomMaterialColor(String typeColor,Context context) {
        int returnColor = Color.GRAY;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }
    public static void shareMessage(Context context, String message) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
    public static void replaceFragment(android.support.v4.app.Fragment fragment, String Tag,
                                       boolean isBackStack, boolean isMenuSupport,
                                       FragmentManager fragmentManager, int id) {
        FragmentManager fm = fragmentManager;
        FragmentTransaction ft = fm.beginTransaction();
        fragment.setHasOptionsMenu(isMenuSupport);
        ft.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        ft.replace(id, fragment, Tag);
        if (isBackStack) {
            ft.addToBackStack(Tag);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
