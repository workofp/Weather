package wp.com.myweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WP on 2017/7/28.
 */

public class AQI {
    @SerializedName("city")
    public AQICity City;

    public class AQICity{

        @SerializedName("aqi")
        public String aqi;
        @SerializedName("pm25")
        public String pm25;
        @SerializedName("qlty")
        public String qlty;
        @SerializedName("co")
        public String co;
        @SerializedName("no2")
        public String no2;
        @SerializedName("so2")
        public String so2;
        @SerializedName("o3")
        public String o3;
        @SerializedName("pm10")
        public String pm10;
    }
}
