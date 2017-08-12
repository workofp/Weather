package wp.com.myweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WP on 2017/7/28.
 */

public class Forecast {
    @SerializedName("date")
    public String date;

    @SerializedName("hum")
    public String hum;

    @SerializedName("pcpn")
    public String pcpn;

    @SerializedName("pop")
    public String pop;

    @SerializedName("pres")
    public String pres;

    @SerializedName("vis")
    public String vis;

    @SerializedName("wind")
    public Wind wid;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature {
        @SerializedName("max")
        public String Max;
        @SerializedName("min")
        public String Min;
    }

    public class More {
        @SerializedName("txt_d")
        public String info;
        @SerializedName("txt_n")
        public String txt_n;

    }

    public class Wind {
        @SerializedName("deg")
        public String deg;
        @SerializedName("dir")
        public String dir;
        @SerializedName("sc")
        public String sc;
        @SerializedName("spd")
        public String spd;
    }
}
