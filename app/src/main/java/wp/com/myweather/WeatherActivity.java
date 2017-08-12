package wp.com.myweather;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wp.com.myweather.gson.Forecast;
import wp.com.myweather.gson.Weather;
import wp.com.myweather.service.AutoUpdateService;
import wp.com.myweather.util.HttpUtil;
import wp.com.myweather.util.Utility;

public class WeatherActivity extends AppCompatActivity {

    private NestedScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;

    private TextView degreeText;
    private TextView weatherInfoText;
//    private TextView flText;
//    private TextView humText;
//    private TextView pcpnText;
//    private TextView presText;
//    private TextView visText;


    private LinearLayout forecastLayout;

    private TextView aqiText;
    private TextView pm25Text;
    private TextView coText;
    private TextView no2Text;
    private TextView o3Text;
    private TextView pm10Text;
    private TextView qltyText;
    private TextView so2Text;

    private TextView carWashText;
    private TextView comfortText;
    private TextView sportText;

    private ImageView bingPicImg;
    public SwipeRefreshLayout swipeRefresh;
    public FloatingActionButton floating;
    private String mWeatherId;
    public DrawerLayout drawerLayout;
    private Button navButton;
    private float status = 0;

    private static final String TAG = "WeatherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        initView();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home);
        }
        //swipeRefresh.setColorSchemeColors(getResources()
        //        .getColor(R.color.colorPrimary));
//        navButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = preferences.getString("weather", null);
        String bingPic = preferences.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }
        if (weatherString != null) {
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);

        } else {
            mWeatherId = getIntent().getStringExtra("weather_id");
            String weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                requestWeather(mWeatherId);
//            }
//        });
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestWeather(mWeatherId);
            }
        });
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = drawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1-slideOffset;
                float rightScale = 0.6f+scale*0.4f;  //1~0.6
                float leftScrale = 0.5f+slideOffset*0.5f;
                mMenu.setAlpha(leftScrale);
                mMenu.setScaleX(leftScrale);
                mMenu.setScaleY(leftScrale);
                mContent.setPivotX(0);
                mContent.setPivotY(mContent.getHeight()/2);
                mContent.setScaleX(rightScale);
                mContent.setScaleY(rightScale);
                mContent.setTranslationX(mMenu.getWidth()*slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (Build.VERSION.SDK_INT >= 21) {
                    getWindow().setStatusBarColor(Color.TRANSPARENT);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void initView() {
        weatherLayout = (NestedScrollView) findViewById(R.id.weather_layout);
        //titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
//        flText=(TextView)findViewById(R.id.fl_text);
//        humText=(TextView)findViewById(R.id.hum_text);
//        pcpnText=(TextView)findViewById(R.id.pcpn_text);
//        presText=(TextView)findViewById(R.id.pres_text);
//        visText=(TextView)findViewById(R.id.vis_text);

        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);

        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        coText = (TextView) findViewById(R.id.co_text);
        no2Text = (TextView) findViewById(R.id.no2_text);
        o3Text = (TextView) findViewById(R.id.o3_text);
        pm10Text = (TextView) findViewById(R.id.pm10_text);
        so2Text = (TextView) findViewById(R.id.so2_text);
        qltyText = (TextView) findViewById(R.id.qlty_text);

        carWashText = (TextView) findViewById(R.id.carwash_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        sportText = (TextView) findViewById(R.id.sport_text);

        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        //swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        floating = (FloatingActionButton) findViewById(R.id.floating);
        // navButton = (Button) findViewById(R.id.nav_button);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager
                        .getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }

    /**
     * 根据天气ID请求城市天气信息
     */
    public void requestWeather(final String weatherId) {
        // TODO: 2017/7/29//guolin.tech/api/weather?cityid
        String weatherURL = "https://free-api.heweather.com/v5/weather?city="
                + weatherId + "&key=6cdb7cbf586c4172bb3314cb0888661e";

//        String weatherURL = "http://guolin.tech/api/weather?cityid="
//                + weatherId + "&key=6cdb7cbf586c4172bb3314cb0888661e";
        HttpUtil.sendOkHttpRequest(weatherURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败！", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                Log.e(TAG, "onResponse: " + responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager
                                    .getDefaultSharedPreferences(WeatherActivity.this)
                                    .edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败！", Toast.LENGTH_SHORT).show();

                        }
                        // swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    /**
     * 展示天气信息
     */
    private void showWeatherInfo(Weather weather) {
        if (weather != null && "ok".equals(weather.status)) {
            String cityName = weather.basic.cityName;
            String updateTime = weather.basic.update.updateTime.split(" ")[1];
            String degree = weather.now.temperature + "℃";
            String weatherInfo = weather.now.more.info;

            //  titleCity.setText(cityName);
            collapsingToolbar.setTitle(cityName);
            titleUpdateTime.setText("更新时间：" + updateTime);

            degreeText.setText(degree);
            weatherInfoText.setText(weatherInfo);
//            flText.setText(weather.now.fl);
//            humText.setText(weather.now.hum);
//            pcpnText.setText(weather.now.pcpn);
//            presText.setText(weather.now.pres);
//            visText.setText(weather.now.vis);

            forecastLayout.removeAllViews();

            for (Forecast forecast : weather.forecastList) {
                View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
                TextView dateText = (TextView) view.findViewById(R.id.date_text);
                TextView infoText = (TextView) view.findViewById(R.id.info_text);
                TextView maxText = (TextView) view.findViewById(R.id.max_text);
                TextView minText = (TextView) view.findViewById(R.id.min_text);

                dateText.setText(forecast.date);
                infoText.setText(forecast.more.info);
                maxText.setText(forecast.temperature.Max);
                minText.setText(forecast.temperature.Min);

                forecastLayout.addView(view);
            }
            if (weather.aqi != null) {

                aqiText.setText(weather.aqi.City.aqi);
                pm25Text.setText(weather.aqi.City.pm25);
                pm10Text.setText(weather.aqi.City.pm10);
                coText.setText(weather.aqi.City.co);
                no2Text.setText(weather.aqi.City.no2);
                so2Text.setText(weather.aqi.City.so2);
                o3Text.setText(weather.aqi.City.o3);
                qltyText.setText(weather.aqi.City.qlty);

            }
            String comfort = "舒适度：" + weather.suggestion.comfort.info;
            String carWash = "洗车指数：" + weather.suggestion.carWash.info;
            String sport = "运动建议：" + weather.suggestion.sport.info;
            carWashText.setText(carWash);
            comfortText.setText(comfort);
            sportText.setText(sport);
            weatherLayout.setVisibility(View.VISIBLE);

            Intent intent = new Intent(WeatherActivity.this, AutoUpdateService.class);
            startService(intent);
        } else {
            Toast.makeText(WeatherActivity.this, "获取天气信息失败！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(Color.parseColor("#339999"));
            }
        }
        return true;
    }
}
