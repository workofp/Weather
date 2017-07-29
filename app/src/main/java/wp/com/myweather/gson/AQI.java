package wp.com.myweather.gson;

/**
 * Created by WP on 2017/7/28.
 */

public class AQI {
    public AQICity City;

    public class AQICity{

        public String aqi;
        public String pm25;
    }
}
