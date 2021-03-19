package com.example.newsblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsblog.fragment.Fragment1;
import com.example.newsblog.fragment.Fragment2;
import com.example.newsblog.fragment.Fragment3;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,NavigationView.OnNavigationItemSelectedListener{

    private FrameLayout fl_FrameLayout;
    private BottomNavigationView bottomNavigationView;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment fragment_now;
    private NavigationView navigationView;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fl_FrameLayout = findViewById(R.id.fl_fragment);
        inint();
    }


    @SuppressLint("NewApi")
    private void inint() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);//设置 NavigationItemSelected 事件监听
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);//改变 BottomNavigationView 默认的效果
        //选中第一个item,对应第一个fragment
        bottomNavigationView.setSelectedItemId(R.id.item_1);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_about:
                Toast.makeText(this,"点击了有关选项",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_write:
                Intent intent = new Intent(MainActivity.this,EditBlogActivity.class);
                intent.putExtra("username",username);
                startActivityForResult(intent,Constants.EDITBLOG_REQUEST);
                break;
            case R.id.nav_quit:
                finish();
                break;
        }
        changePageFragment(menuItem.getItemId());
        return true;
    }

    /**
     * 当点击导航栏时改变fragment
     *
     * @param id
     */
    public void changePageFragment(int id) {
        switch (id) {
            case R.id.item_1:
                if (fragment1 == null) { //减少new fragmnet,避免不必要的内存消耗
                    fragment1 = Fragment1.newInstance();
                }
                switchFragment(fragment_now, fragment1);
                break;
            case R.id.item_2:
                if (fragment2 == null) {
                    fragment2 = Fragment2.newInstance();
                }
                switchFragment(fragment_now, fragment2);
                break;
            case R.id.item_3:
                if (fragment3 == null) {
                    fragment3 = Fragment3.newInstance();
                }
                switchFragment(fragment_now, fragment3);
                break;
        }
    }

    /**
     * 隐藏显示fragment
     *
     * @param from 需要隐藏的fragment
     * @param to   需要显示的fragment
     */
    public void switchFragment(Fragment from, Fragment to) {
        if (to == null)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            if (from == null) {
                transaction.add(R.id.fl_fragment, to).show(to).commit();
            } else {
                // 隐藏当前的fragment，add下一个fragment到Activity中
                transaction.hide(from).add(R.id.fl_fragment, to).commitAllowingStateLoss();
            }
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commit();
        }
        fragment_now = to;
    }

}
