package cn.demo.timeline.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.lang.reflect.Field;

import cn.demo.timeline.AndroidBug5497Workaround;
import cn.demo.timeline.R;
import cn.demo.timeline.adapter.TimeLineAdapter;
import cn.demo.timeline.entity.TimeLine;

/**
 * Created by Administrator on 2017/2/20.
 */

public class MainActivity extends AppCompatActivity {

    private ExpandableListView elv_Main;
    private TimeLineAdapter timeLineAdapter;
    private TimeLine timeLine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        LinearLayout linear_bar = (LinearLayout) findViewById(R.id.ll);
        linear_bar.setVisibility(View.VISIBLE);
        //获取到状态栏的高度
        int statusHeight = getStatusBarHeight();
        //动态的设置隐藏布局的高度
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
        params.height = statusHeight;
        linear_bar.setLayoutParams(params);
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));
        initData();
        initView();
    }

    /**
     * 通过反射的方式获取状态栏高度
     * @return
     */
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void initView() {
        elv_Main = (ExpandableListView) findViewById(R.id.main_elv);
        timeLineAdapter = new TimeLineAdapter(this,timeLine.getRows());
        elv_Main.setAdapter(timeLineAdapter);
        elv_Main.setDivider(null);
        elv_Main.setGroupIndicator(null);

        for (int i = 0;i<timeLineAdapter.getGroupCount();i++){
            elv_Main.expandGroup(i);
        }
    }

    private void initData() {
        String dataJson = "{\"msgCode\":\"0000\",\"isSuccess\":\"true\",\"rows\":[{\"daylist\":[{\"datalist\":[{\"progress\":\"45\",\"name\":\"测试1111测试\",\"courseid\":\"1100000000000005521\"},{\"progress\":\"50\",\"name\":\"陈测试\",\"courseid\":\"1100000000000005560\"}],\"month\":\"01\",\"day\":\"12\"}],\"month\":\"01\",\"year\":\"2017\"},{\"daylist\":[{\"datalist\":[{\"progress\":\"73\",\"name\":\"bai-test111111\",\"courseid\":\"1100000000000005193\"},{\"progress\":\"75\",\"name\":\"bai-test(课程)\",\"courseid\":\"1100000000000005197\"},{\"progress\":\"80\",\"name\":\"kecheng\",\"courseid\":\"1100000000000005342\"},{\"progress\":\"0\",\"name\":\"文档\",\"courseid\":\"1100000000000005598\"},{\"progress\":\"2267\",\"name\":\"wdsp\",\"courseid\":\"1100000000000005688\"},{\"progress\":\"32\",\"name\":\"测试开始学习\",\"courseid\":\"1100200000000005530\"}],\"month\":\"02\",\"day\":\"17\"},{\"datalist\":[{\"progress\":\"65\",\"name\":\"课程名称kecheng名称名称\",\"courseid\":\"1100000000000005353\"},{\"progress\":\"0\",\"name\":\"ceshi4\",\"courseid\":\"10010\"},{\"progress\":\"24\",\"name\":\"test1111\",\"courseid\":\"1100000000000005688\"},{\"progress\":\"21\",\"name\":\"测试开始学习\",\"courseid\":\"1100200000000005530\"},{\"progress\":\"0\",\"name\":\"小沐测试课程\",\"courseid\":\"889990001\"}],\"month\":\"02\",\"day\":\"18\"},{\"datalist\":[{\"progress\":\"0\",\"name\":\"\",\"courseid\":\"1100000000000005193\"},{\"progress\":\"79645\",\"name\":\"视频\",\"courseid\":\"1100000000000005598\"},{\"progress\":\"99757\",\"name\":\"wdsp\",\"courseid\":\"1100000000000005688\"},{\"progress\":\"0\",\"name\":\"dd\",\"courseid\":\"1100000000000005772\"}],\"month\":\"02\",\"day\":\"20\"}],\"month\":\"02\",\"year\":\"2017\"}]}";
        timeLine = new Gson().fromJson(dataJson,TimeLine.class);

    }
}
