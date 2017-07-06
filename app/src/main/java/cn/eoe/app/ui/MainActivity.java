package cn.eoe.app.ui;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.administrator.myapplication555.R;

import java.util.List;

import cn.eoe.app.utils.LocationChangeCity;

public class MainActivity extends AppCompatActivity {
    private double latitude = 0.0;
    private double longitude = 0.0;
    private LocationManager manager;
    String provider;
    public static String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  //获取位置管理器
        List<String> provideList = manager.getProviders(true);
        if (provideList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;      //选择位置提供器
        } else {
            Toast.makeText(MainActivity.this, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show(); //假如没打开无线网络 跳转设置页面
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        Location location = manager.getLastKnownLocation( provider);  //获取一个location对象，获取当前位置信息
        if (location != null) {
            ShowLocation(location);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;                                      //权限检查
            }
        }
        manager.requestLocationUpdates(provider, 1000, 1000, locationListener);//设置监听器
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            manager.removeUpdates(locationListener);  //移除监听器
        }
    }
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            ShowLocation(location);    //改变位置时，重新传入位置
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }
        @Override
        public void onProviderEnabled(String s) {

        }
        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void ShowLocation(Location location){
        city= LocationChangeCity.getAddress(this,location.getLatitude(),location.getLongitude()).getLocality();  //将经纬度转为城市
    }

}
