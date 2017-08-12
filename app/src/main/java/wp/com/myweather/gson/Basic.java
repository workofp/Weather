package wp.com.myweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WP on 2017/7/28.
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    @SerializedName("cnty")
    public String country;

    @SerializedName("prov")
    public String prov;

    @SerializedName("lat")
    public String lat;

    @SerializedName("lon")
    public String lon;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
