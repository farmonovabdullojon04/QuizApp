package com.abdullojon.quizapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class UserPreferences {
    private static final String PREF_NAME = "UserProfilePrefs";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_DIAMONDS = "user_diamonds";
    private static final String KEY_CAT_SCORE_PREFIX = "cat_score_";
    private static final String KEY_RECENT_CATS = "recent_categories";

    private SharedPreferences prefs;

    public UserPreferences(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, "Abdullojon");
    }

    public void setUserName(String name) {
        prefs.edit().putString(KEY_USER_NAME, name).apply();
    }

    public String getUserId() {
        return prefs.getString(KEY_USER_ID, "ID-1809");
    }

    public int getDiamonds() {
        return prefs.getInt(KEY_DIAMONDS, 160);
    }

    public void addDiamonds(int amount) {
        int current = getDiamonds();
        prefs.edit().putInt(KEY_DIAMONDS, current + amount).apply();
    }

    public boolean spendDiamonds(int amount) {
        int current = getDiamonds();
        if (current >= amount) {
            prefs.edit().putInt(KEY_DIAMONDS, current - amount).apply();
            return true;
        }
        return false;
    }

    public int getCategoryScore(int categoryIndex, int defaultScore) {
        return prefs.getInt(KEY_CAT_SCORE_PREFIX + categoryIndex, defaultScore);
    }

    public void setCategoryScore(int categoryIndex, int score) {
        prefs.edit().putInt(KEY_CAT_SCORE_PREFIX + categoryIndex, score).apply();
    }

    public List<Integer> getRecentCategories() {
        String saved = prefs.getString(KEY_RECENT_CATS, "0,1,2");
        List<Integer> list = new ArrayList<>();
        if (saved != null && !saved.isEmpty()) {
            String[] split = saved.split(",");
            for (String s : split) {
                try {
                    int val = Integer.parseInt(s.trim());
                    if (!list.contains(val)) {
                        list.add(val);
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        int[] defaults = {0, 1, 2, 3, 4};
        for (int d : defaults) {
            if (list.size() >= 3) break;
            if (!list.contains(d)) {
                list.add(d);
            }
        }
        return list;
    }

    public void addRecentCategory(int categoryIndex) {
        List<Integer> current = getRecentCategories();
        current.remove(Integer.valueOf(categoryIndex));
        current.add(0, categoryIndex);
        while (current.size() > 3) {
            current.remove(current.size() - 1);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < current.size(); i++) {
            sb.append(current.get(i));
            if (i < current.size() - 1) sb.append(",");
        }
        prefs.edit().putString(KEY_RECENT_CATS, sb.toString()).apply();
    }
}