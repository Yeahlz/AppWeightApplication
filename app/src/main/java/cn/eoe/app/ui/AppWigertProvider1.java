package cn.eoe.app.ui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/5/8.
 */

public class AppWigertProvider1 extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context) {  //当一个App Widget实例第一次创建时被调用
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {   //桌面控件挂在桌面时调用
        super.onUpdate(context, appWidgetManager, appWidgetIds);
       context.startService(new Intent(context, WedigetService.class));
    }

    public void onDeleted(Context context, int[] appWidgetIds) { // 当App Widget的最后一个实例被从宿主中删除时被调用
        super.onDeleted(context, appWidgetIds);
    }

    public void onReceive(final Context context, Intent intent) {  //当App Widget的最后一个实例被从宿主中删除时被调用
        super.onReceive(context, intent);
    }
}

