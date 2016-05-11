package com.rm.appstoreandroid.Utils;

import android.content.Context;

import com.rm.appstoreandroid.R;

/**
 * Created by oscargallon on 5/11/16.
 */
public class Utils {
    private Utils() {
    }

    public static int getCategoryDrawable(String term, Context context) {
        int drawable = 0;
        if (term.equals(context.getString(R.string.books_key))) {
            drawable = R.drawable.ic_book_black_24dp;
        } else if (term.equals(context.getString(R.string.business_key))) {
            drawable = R.drawable.ic_business_black_24dp;

        } else if (term.equals(context.getString(R.string.education_key))) {
            drawable = R.drawable.ic_education_black_24dp;

        } else if (term.equals(context.getString(R.string.catalogs_key))) {
            drawable = R.drawable.ic_catalog_black_24dp;

        } else if (term.equals(context.getString(R.string.entertaiment_key))) {
            drawable = R.drawable.ic_entertaiment_black_24dp;

        } else if (term.equals(context.getString(R.string.finance_key))) {
            drawable = R.drawable.ic_financial_black_24dp;

        } else if (term.equals(context.getString(R.string.food_and_drink_key))) {
            drawable = R.drawable.ic_food_and_drink_black_24dp;

        } else if (term.equals(context.getString(R.string.games_key))) {
            drawable = R.drawable.ic_games_black_24dp;

        } else if (term.equals(context.getString(R.string.health_and_fitness_key))) {
            drawable = R.drawable.ic_health_black_24dp;

        } else if (term.equals(context.getString(R.string.lifestyle_key))) {
            drawable = R.drawable.ic_life_style_black_24dp;

        } else if (term.equals(context.getString(R.string.medical_key))) {
            drawable = R.drawable.ic_medical_black_24dp;

        } else if (term.equals(context.getString(R.string.music_key))) {
            drawable = R.drawable.ic_music_black_24dp;

        } else if (term.equals(context.getString(R.string.navigation_key))) {
            drawable = R.drawable.ic_navigation_24dp;

        } else if (term.equals(context.getString(R.string.news_key))) {
            drawable = R.drawable.ic_news_24dp;

        } else if (term.equals(context.getString(R.string.photo_and_video_key))) {
            drawable = R.drawable.ic_photo_and_video_24dp;

        } else if (term.equals(context.getString(R.string.productivity_key))) {
            drawable = R.drawable.ic_productivity_24dp;

        } else if (term.equals(context.getString(R.string.reference_key))) {
            drawable = R.drawable.ic_reference_24dp;

        } else if (term.equals(context.getString(R.string.social_networking_key))) {
            drawable = R.drawable.ic_social_networking_24dp;

        } else if (term.equals(context.getString(R.string.sports_key))) {
            drawable = R.drawable.ic_sports_black_24dp;

        } else if (term.equals(context.getString(R.string.travel_key))) {
            drawable = R.drawable.ic_travel_black_24dp;

        } else if (term.equals(context.getString(R.string.utilities_key))) {
            drawable = R.drawable.ic_utilities_24dp;
        } else if (term.equals(context.getString(R.string.weather_key))) {
            drawable = R.drawable.ic_weather_black_24dp;
        } else {
            drawable = R.drawable.ic_utilities_24dp;


        }


        return drawable;
    }
}
