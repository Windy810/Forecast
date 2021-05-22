package com.windy.forecast;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.windy.forecast.Request.HourlyWeatherForecast;
import com.windy.forecast.Request.LifestyleForecast;
import com.google.gson.Gson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Fragment2 extends Fragment {

    private TextView time,tmp,cond_txt,wind_dir,wind_sc;
    private LinearLayout LinerLayoutTime;
    private TextView comf_type,comf_txt;
    private TextView drsg_type,drsg_txt;
    private TextView flu_type,flu_txt;
    private TextView sport_type,sport_txt;
    private TextView trav_type,trav_txt;
    private TextView uv_type,uv_txt;
    private TextView cw_type,cw_txt;
    private TextView air_type,air_txt;
    private TextView tv_update;

    private HourlyWeatherForecast hourlyWeatherForecast;
    private LifestyleForecast lifestyleForecast;
    private volatile String string_hourly_weather_forcast;
    private volatile String string_Lifestyle_forcast;
    String string_city;
    View view;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    parseData();
                    break;
                default:
                    break;
            }
        };
    };


    public Fragment2() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2, container, false);
        bindView();
        getData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            getData();
        }
    }

    void bindView(){
        tv_update = view.findViewById(R.id.tv_update);

        comf_type = view.findViewById(R.id.comf_type);
        comf_txt = view.findViewById(R.id.comf_txt);
        drsg_type = view.findViewById(R.id.drsg_type);
        drsg_txt = view.findViewById(R.id.drsg_txt);
        flu_type = view.findViewById(R.id.flu_type);
        flu_txt = view.findViewById(R.id.flu_txt);
        sport_type = view.findViewById(R.id.sport_type);
        sport_txt = view.findViewById(R.id.sport_txt);
        trav_type = view.findViewById(R.id.trav_type);
        trav_txt = view.findViewById(R.id.trav_txt);
        uv_type = view.findViewById(R.id.uv_type);
        uv_txt = view.findViewById(R.id.uv_txt);
        cw_type = view.findViewById(R.id.cw_type);
        cw_txt = view.findViewById(R.id.cw_txt);
        air_type = view.findViewById(R.id.air_type);
        air_txt = view.findViewById(R.id.air_txt);

        LinerLayoutTime = (LinearLayout)view.findViewById(R.id.time);

    }

    void draw(){
        if(hourlyWeatherForecast == null || lifestyleForecast == null) return ;
        try{
            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm");
            Date upd = sd1.parse(hourlyWeatherForecast.getHeWeather6().get(0).getUpdate().getLoc());
            tv_update.setText(sd2.format(upd)+" 发布");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Resources res =getResources();
        String tm;
        String[] s;
        String degree = "℃";
        for (int i = 0; i < 8; i++) {
            tm = hourlyWeatherForecast.getHeWeather6().get(0).getHourly().get(i).getTime();
            s = tm.split(" ");
            int TimeId=res.getIdentifier("time"+i,"id","com.windy.forecast");
            time = view.findViewById(TimeId);
            time.setText(s[1]);

            int TmpId =res.getIdentifier("tmp"+i,"id","com.windy.forecast");
            tmp = view.findViewById(TmpId);
            tmp.setText(hourlyWeatherForecast.getHeWeather6().get(0).getHourly().get(i).getTmp()+degree);

            int CondTxtId = res.getIdentifier("cond_txt"+i,"id","com.windy.forecast");
            cond_txt = view.findViewById(CondTxtId);
            cond_txt.setText(hourlyWeatherForecast.getHeWeather6().get(0).getHourly().get(i).getCond_txt());

            int WindDirId = res.getIdentifier("wind_dir"+i,"id","com.windy.forecast");
            wind_dir = view.findViewById(WindDirId);
            wind_dir.setText(hourlyWeatherForecast.getHeWeather6().get(0).getHourly().get(i).getWind_dir());

            int WindScId = res.getIdentifier("wind_sc"+i,"id","com.windy.forecast");
            wind_sc = view.findViewById(WindScId);
            wind_sc.setText(hourlyWeatherForecast.getHeWeather6().get(0).getHourly().get(i).getWind_sc()+"级");
        }

        comf_type.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(0).getBrf());
        comf_txt.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(0).getTxt());
        drsg_type.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(1).getBrf());
        drsg_txt.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(1).getTxt());
        flu_type.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(2).getBrf());
        flu_txt.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(2).getTxt());
        sport_type.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(3).getBrf());
        sport_txt.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(3).getTxt());
        trav_type.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(4).getBrf());
        trav_txt.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(4).getTxt());
        uv_type.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(5).getBrf());
        uv_txt.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(5).getTxt());
        cw_type.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(6).getBrf());
        cw_txt.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(6).getTxt());
        air_type.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(7).getBrf());
        air_txt.setText(lifestyleForecast.getHeWeather6().get(0).getLifestyle().get(7).getTxt());
    }

    void getData(){
        new Thread() {
            public void run() {
                try {
                    CityOperator cityOperator = new CityOperator(getContext());
                    string_city = cityOperator.getIsSelectCity().toString2();
                    string_hourly_weather_forcast = GetData.getJson("https://free-api.heweather.com/s6/weather/hourly?location=" + string_city + "&key=2d7b37b322a04de1ab17fca5f2e0f0ea");
                    string_Lifestyle_forcast = GetData.getJson("https://free-api.heweather.com/s6/weather/lifestyle?location=" + string_city + "&key=2d7b37b322a04de1ab17fca5f2e0f0ea");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0x001);
            };
        }.start();
    }

    void parseData(){
        Gson gson = new Gson();
        hourlyWeatherForecast = gson.fromJson(string_hourly_weather_forcast,HourlyWeatherForecast.class);
        lifestyleForecast = gson.fromJson(string_Lifestyle_forcast,LifestyleForecast.class);
        draw();
    }
}
