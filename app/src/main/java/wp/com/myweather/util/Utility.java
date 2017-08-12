package wp.com.myweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wp.com.myweather.db.City;
import wp.com.myweather.db.County;
import wp.com.myweather.db.Province;
import wp.com.myweather.gson.Weather;

import static android.content.ContentValues.TAG;

/**
 * Created by WP on 2017/7/28.
 */

public class Utility {
    /**
     * 解析和处理服务器返回的jason数据-省级数据
     *
     * @param response jason数据构成的字符串
     * @return boolean
     */
    public static boolean handleProvinceResponse(String response) {

        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvince = new JSONArray(response);
                for (int i = 0; i < allProvince.length(); i++) {
                    JSONObject provinceObject = allProvince.getJSONObject(i);
                    Province aProvince = new Province();
                    aProvince.setProvinceName(provinceObject.getString("name"));
                    aProvince.setProvinceCode(provinceObject.getInt("id"));
                    aProvince.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的jason数据--市级数据
     * @param response
     * @param provinceId
     * @return
     */
    public static boolean handleCityResponse(String response,int provinceId) {

        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City aCity=new City();
                    aCity.setCityName(cityObject.getString("name"));
                    aCity.setCityCode(cityObject.getInt("id"));
                    aCity.setProvinceId(provinceId);
                    aCity.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的jason数据--县数据
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handleCountyResponse(String response,int cityId) {

        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County aCounty=new County();
                    aCounty.setCountyName(countyObject.getString("name"));
                    aCounty.setWeatherId(countyObject.getString("weather_id"));
                    aCounty.setCityId(cityId);
                    aCounty.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 将返回的jason数据解析成为weather实体类
     */
    public static Weather handleWeatherResponse(String response){

        try{
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather5");
            String weatherContent=jsonArray.getJSONObject(0).toString();
            Log.e(TAG, "handleWeatherResponse: "+weatherContent);
            return new Gson().fromJson(weatherContent,Weather.class);

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
