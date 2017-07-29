package wp.com.myweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WP on 2017/7/28.
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt")
        public String info;
    }
}
