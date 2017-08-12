package wp.com.myweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WP on 2017/8/1.
 */

public class Alarm {
    @SerializedName("title")
    public String title;
    @SerializedName("level")
    public String level;
    @SerializedName("type")
    public String type;
    @SerializedName("txt")
    public String txt;
}
