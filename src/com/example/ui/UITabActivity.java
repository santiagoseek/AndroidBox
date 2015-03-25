package com.example.ui;

import com.example.androidbox.GridUIActivity;
import com.example.androidbox.HandlerActivity;
import com.example.androidbox.InternetUtilActivity;
import com.example.androidbox.R;
import com.example.androidbox.SystemUtilActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.RadioButton;

public class UITabActivity extends TabActivity implements OnCheckedChangeListener {


    private TabHost mHost;
    private Intent mMBlogIntent;
    private Intent mMoreIntent;
    private Intent mInfoIntent;
    private Intent mSearchIntent;
    private Intent mUserInfoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.uitabhost);

        // ~~~~~~~~~~~~ 初始化
        /*this.mMBlogIntent = new Intent(this, HomeListActivity.class);
        this.mSearchIntent = new Intent(this, SearchSquareActivity.class);
        this.mInfoIntent = new Intent(this, MessageGroup.class);
        this.mUserInfoIntent = new Intent(this, MyInfoActivity.class);
        this.mMoreIntent = new Intent(this, MoreItemsActivity.class);*/
        this.mMBlogIntent = new Intent(this, GridUIActivity.class);
        this.mSearchIntent = new Intent(this, SystemUtilActivity.class);
        this.mInfoIntent = new Intent(this, UIExpandableListView.class);
        this.mUserInfoIntent = new Intent(this, SomeDialog.class);
        this.mMoreIntent = new Intent(this, UIListViewActivity.class);

        initRadios();
        
        setupIntent();
    }

    /**
     * 初始化底部按钮
     */
    private void initRadios() {
         ((RadioButton) findViewById(R.id.radio_button0)).setOnCheckedChangeListener(this);
         ((RadioButton) findViewById(R.id.radio_button1)).setOnCheckedChangeListener(this);
         ((RadioButton) findViewById(R.id.radio_button2)).setOnCheckedChangeListener(this);
         ((RadioButton) findViewById(R.id.radio_button3)).setOnCheckedChangeListener(this);
         ((RadioButton) findViewById(R.id.radio_button4)).setOnCheckedChangeListener(this);
    }

    /**
     * 切换模块
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
            case R.id.radio_button0:
                this.mHost.setCurrentTabByTag("mblog_tab");
                break;
            case R.id.radio_button1:
                this.mHost.setCurrentTabByTag("message_tab");
                break;
            case R.id.radio_button2:
                this.mHost.setCurrentTabByTag("userinfo_tab");
                break;
            case R.id.radio_button3:
                this.mHost.setCurrentTabByTag("search_tab");
                break;
            case R.id.radio_button4:
                this.mHost.setCurrentTabByTag("more_tab");
                break;
            }
        }
    }

    private void setupIntent() {
        this.mHost = getTabHost();
        TabHost localTabHost = this.mHost;

        localTabHost.addTab(buildTabSpec("mblog_tab", R.string.main_home,
                R.drawable.i1, this.mMBlogIntent));

        localTabHost.addTab(buildTabSpec("message_tab", R.string.main_news,
                R.drawable.i2, this.mInfoIntent));

        localTabHost.addTab(buildTabSpec("userinfo_tab", R.string.main_my_info,
                R.drawable.icon3, this.mUserInfoIntent));

        localTabHost.addTab(buildTabSpec("search_tab", R.string.menu_search,
                R.drawable.icon4, this.mSearchIntent));

        localTabHost.addTab(buildTabSpec("more_tab", R.string.more,
                R.drawable.icon5, this.mMoreIntent));

    }

    private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
            final Intent content) {
        return this.mHost
                .newTabSpec(tag)
                .setIndicator(getString(resLabel),
                        getResources().getDrawable(resIcon))
                .setContent(content);
    }
}
