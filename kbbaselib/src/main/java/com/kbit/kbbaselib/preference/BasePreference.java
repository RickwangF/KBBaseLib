package com.kbit.kbbaselib.preference;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kbit.kbbaselib.util.StringUtil;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasePreference {

    private static BasePreference instance;

    private SharedPreferences mPreferences;

    private Gson gson;

    private void initGson() {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.serializeNulls().create();
    }

    public BasePreference(SharedPreferences sharedPreferences) {
        mPreferences = sharedPreferences;
        instance = this;
        initGson();
    }

    public boolean putLong(String key, long value) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }
        return instance.mPreferences.edit().putLong(key, value).commit();
    }

    public boolean putInt(String key, int value) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }
        return instance.mPreferences.edit().putInt(key, value).commit();
    }

    public boolean putString(String key, String value) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }
        return instance.mPreferences.edit().putString(key, value).commit();
    }

    public boolean putBoolean(String key, boolean value) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }
        return instance.mPreferences.edit().putBoolean(key, value).commit();
    }

    public boolean putFloat(String key, float value) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }
        return instance.mPreferences.edit().putFloat(key, value).commit();
    }

    public boolean putStringSet(String key, Set<String> values) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }
        return instance.mPreferences.edit().putStringSet(key, values).commit();
    }

    public boolean putObject(String key, Object object) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }

        String objectString = instance.gson.toJson(object);
        return instance.mPreferences.edit().putString(key, objectString).commit();
    }

    public boolean putList(String key, List list) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }

        String listString = instance.gson.toJson(list);
        return instance.mPreferences.edit().putString(key, listString).commit();
    }

    public boolean putMap(String key, Map map) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }

        String mapString = instance.gson.toJson(map);
        return instance.mPreferences.edit().putString(key, mapString).commit();
    }

    public int getInt(String key) {
        if (StringUtil.isEmpty(key)) {
            return 0;
        }

        return instance.mPreferences.getInt(key, 0);
    }

    public long getLong(String key) {
        if (StringUtil.isEmpty(key)) {
            return 0;
        }

        return instance.mPreferences.getLong(key, 0);
    }

    public float getFloat(String key) {
        if (StringUtil.isEmpty(key)) {
            return 0;
        }

        return instance.mPreferences.getFloat(key, 0);
    }

    public String getString(String key) {
        if (StringUtil.isEmpty(key)) {
            return "";
        }

        return instance.mPreferences.getString(key, "");
    }

    public Set<String> getStringSet(String key) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }

        return instance.mPreferences.getStringSet(key, null);
    }

    public <T> T getObject(String key, Class<T> tClass) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }

        String jsonString = instance.mPreferences.getString(key, "");
        if (StringUtil.isEmpty(jsonString)) {
            return null;
        }

        return instance.gson.fromJson(jsonString, tClass);
    }

    public <T> List<T> getList(String key, Class<List<T>> listClass) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }

        String jsonString = instance.mPreferences.getString(key, "");
        if (StringUtil.isEmpty(jsonString)) {
            return null;
        }
        return instance.gson.fromJson(jsonString, listClass);
    }

    public <K, V> Map<K, V> getMap(String key, Class<K> keyClass, Class<V> valClass) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }

        String jsonString = instance.mPreferences.getString(key, "");
        if (StringUtil.isEmpty(jsonString)) {
            return null;
        }
        Type type = new TypeToken<Map<K, V>>(){}.getType();
        return instance.gson.fromJson(jsonString, type);
    }

    public boolean removeKey(String key) {
        if (StringUtil.isEmpty(key)) {
            return false;
        }
        return instance.mPreferences.edit().remove(key).commit();
    }

    public boolean clearAll() {
        return instance.mPreferences.edit().clear().commit();
    }
}
