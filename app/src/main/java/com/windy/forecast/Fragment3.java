package com.windy.forecast;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class Fragment3 extends Fragment implements View.OnClickListener{
    private ListView list_city;
    private Button button;
    private View view;
    private CityOperator cityOperator;
    private List<City> cityList;
    private Button btn_add;
    private AutoCompleteTextView autoCompleteTextView;
    public android.app.Activity fck;
    CityAdapter adapter;

    public Fragment3() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_city, container, false);
        bindView();
        draw();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            String string_city = data.getStringExtra("cityName");
            if (nameExistInList(string_city)) {
            } else if (!nameExistInList(string_city)) {
                updateCurrentCity(string_city);
                draw();
            }
        }

    }

    private void bindView(){
        cityOperator = new CityOperator(getContext());
        list_city = view.findViewById(R.id.list_city);
        this.autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        Resources resources = this.getResources();
        String[] country = resources.getStringArray(R.array.city_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                country
        );
        this.autoCompleteTextView.setAdapter(adapter);
        this.autoCompleteTextView.setOnClickListener(this);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
//        button = view.findViewById(R.id.add_city);
//        button.setOnClickListener(this);
    }

    private void draw(){
        cityList = cityOperator.getItemCity();
        adapter = new CityAdapter(cityList,getContext());
        list_city.setAdapter(adapter);
    }

    private boolean nameExistInList(String name){
        for(int i = 1; i < cityList.size(); i ++){
            if(cityList.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    private void updateCurrentCity(String string_city){
        if(cityOperator.getIsExist(string_city) == 1){
            City city1 = cityOperator.getIsSelectCity();
            cityOperator.update(city1);
            City city2 = new City(string_city,"否");
            cityOperator.update(city2);
        }
        else if(cityOperator.getIsExist(string_city) == 0){
            City city1 = cityOperator.getIsSelectCity();
            cityOperator.update(city1);
            City city2 = new City(string_city, "是");
            cityOperator.add(city2);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add) {
            Intent intent = new Intent();
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    //注意点1
            //TODO 传入这个界面数据请求到的ID
            String string_city = autoCompleteTextView.getText().toString();
            intent.putExtra("cityName", string_city);
            intent.setClass(getActivity(), Add_city.class);
            //getActivity().startActivityForResult(intent, REQUEST_CODE);  //注意点2
            startActivityForResult(intent, 1);
        }
    }
}
