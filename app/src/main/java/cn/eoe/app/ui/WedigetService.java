package cn.eoe.app.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.administrator.myapplication555.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.eoe.app.entity.Databean;
import cn.eoe.app.utils.JSONUtils;

import static cn.eoe.app.ui.MainActivity.city;


/**
 * Created by Administrator on 2017/5/12.
 */

public class WedigetService extends Service {
    public Databean databean;
    private  StringBuilder response;  //修改字符而不创建新的对象
    MyHandler myHandler = new  MyHandler();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {   //每次启动服务都会调用该方法
       sendHttpRequest("https://api.seniverse.com/v3/weather/now.json?key=gqirmwtnh4w3ykfs&location=guangzhou&language=zh-Hans&unit=c");
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);     //获取闹钟管理器
        Intent intent1 = new Intent(this,WedigetService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this,1,intent1,0);
        long currenttime = SystemClock.elapsedRealtime()+1000*3600;     //获取系统时间
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,currenttime,pendingIntent);    //通过闹钟循环机制 每隔一段时间启动服务 更新桌面控件
        return super.onStartCommand(intent, flags, startId);
    }
    public  void sendHttpRequest(final String address) {    //网络请求方法
        new Thread(new Runnable() {
            public void run() {                 //网络请求耗时操作 ，开启子线程
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);    //传入地址
                    connection = (HttpURLConnection) url.openConnection();  //获取HttpURLConnection
                    connection.setRequestMethod("GET");   // 请求方式
                    connection.setConnectTimeout(8000);  // 连接超时
                    connection.setReadTimeout(8000);    // 读取超时
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(inputStream, "UTF-8"));   //获取输入流
                    response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Message message = new Message();
                    message.what = 1;                       //主线程进行ui操作
                    Bundle bundle = new Bundle();
                    bundle.putString("weather_data",response.toString());
                    message.setData(bundle);
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public void updateWediget1(){
        RemoteViews  remoteViews = new RemoteViews(getPackageName(), R.layout.content_provider);  //更新remoteview
        remoteViews.setTextViewText( R.id.tv_weather,"天气:"+databean.getText() );
        Log.d("XXXXXX",databean.getText());
        remoteViews.setTextViewText(R.id.tv_location,city);
        if(city==null){remoteViews.setTextViewText(R.id.tv_location,"广州市");}
        remoteViews.setTextViewText(R.id.tv_temporature,"温度:"+databean.getTemperature());
        remoteViews.setTextViewText(R.id.tv_data, "风力等级:"+databean.getWind_scale());
        peatureMatch(databean.getCode(),remoteViews);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getBaseContext());   // 桌面控件管理类获取实例
        ComponentName componentName = new ComponentName(getBaseContext(),AppWigertProvider1.class);
        appWidgetManager.updateAppWidget(componentName, remoteViews);     //更新appWidget
    }

    protected  class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    databean = JSONUtils.parseJson(bundle.getString("weather_data"));
                    updateWediget1();
                    break;
            }
        }
    }

    public static void peatureMatch(String string, RemoteViews  remoteViews){  //根据返回数据选择合适图片
        if (string.equals("0") )
        { remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a0);}
        if (string.equals("1") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a1);}
        if (string.equals("2") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a2);}
        if (string.equals("3") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a3);}
        if (string.equals("4") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a4);}
        if (string.equals("5") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a5);}
        if (string.equals("6") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a6);}
        if (string.equals("7") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a7);}
        if (string.equals("8") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a8);}
        if (string.equals("9") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a9);}
        if (string.equals("10") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a10);}
        if (string.equals("11") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a11);}
        if (string.equals("12") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a12);}
        if (string.equals("13") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a13);}
        if (string.equals("14") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a14);}
        if (string.equals("15") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a15);}
        if (string.equals("16") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a16);}
        if (string.equals("17") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a17);}
        if (string.equals("18") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a18);}
        if (string.equals("19") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a19);}
        if (string.equals("20") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a20);}
        if (string.equals("21") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a21);}
        if (string.equals("22") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a22);}
        if (string.equals("23") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a23);}
        if (string.equals("24") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a24);}
        if (string.equals("25") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a25);}
        if (string.equals("26") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a26);}
        if (string.equals("27") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a27);}
        if (string.equals("28") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a28);}
        if (string.equals("29") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a29);}
        if (string.equals("30") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a30);}
        if (string.equals("31") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a31);}
        if (string.equals("32") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a32);}
        if (string.equals("33") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a33);}
        if (string.equals("34") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a34);}
        if (string.equals("35") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a35);}
        if (string.equals("36") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a36);}
        if (string.equals("37") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a37);}
        if (string.equals("38") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a38);}
        if (string.equals("99") )
        {  remoteViews.setImageViewResource(R.id.iv_widget_weather,R.drawable.a99);}
    }
}
