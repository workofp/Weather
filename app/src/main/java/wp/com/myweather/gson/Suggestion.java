package wp.com.myweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WP on 2017/7/28.
 */

public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash carWash;

    @SerializedName("sport")
    public Sport sport;

    @SerializedName("drsg")
    public Drsg drsg;

    @SerializedName("flu")
    public Flu flu;

    @SerializedName("trav")
    public Travel travel;

    @SerializedName("uv")
    public Uv uv;

    public class Comfort{
        @SerializedName("txt")
        public String info;
        @SerializedName("brf")
        public String brf;
    }
    public  class CarWash{
        @SerializedName("txt")
        public String info;
        @SerializedName("brf")
        public String brf;
    }
    public class Sport{
        @SerializedName("txt")
        public String info;
        @SerializedName("brf")
        public String brf;
    }

    public class Travel{
        @SerializedName("brf")
        public String brf;
        @SerializedName("txt")
        public String txt;
    }
    public class Uv{
        @SerializedName("brf")
        public String brf;
        @SerializedName("txt")
        public String txt;
    }
    public class Flu{
        @SerializedName("brf")
        public String brf;
        @SerializedName("txt")
        public String txt;
    }

    public class Drsg{
        @SerializedName("brf")
        public String brf;
        @SerializedName("txt")
        public String txt;
    }
}
