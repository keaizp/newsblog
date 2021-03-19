package com.example.newsblog.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.newsblog.LocalImageHolderView;
import com.example.newsblog.NestedListView;
import com.example.newsblog.NewActivity;
import com.example.newsblog.NewsAdapter;
import com.example.newsblog.NewsInfo;
import com.example.newsblog.R;
import com.example.newsblog.dao.NewsDao;
import com.google.android.material.appbar.AppBarLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment implements AppBarLayout.OnOffsetChangedListener,View.OnClickListener {
    private List<NewsInfo> list;
    private NewsInfo news;
    private NewsAdapter adapter;
    private AppBarLayout appBarLayout;
    private androidx.appcompat.widget.Toolbar toolbar_default;
    private Toolbar toolbar_nono;
    private EditText searchTxt_nono;
    private ImageView seatchImg_nono;
    private ConvenientBanner convenientBanner;
    private NewsDao newsDao;
    private ImageView menu_img;
    private DrawerLayout mDrawerLayout;

    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    boolean flag_defaulttono = true;

    public Fragment1() {
    }


    public static Fragment1 newInstance() {
        Fragment1 fragment = new Fragment1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        NestedListView listView = view.findViewById(R.id.mylist);
        appBarLayout = view.findViewById(R.id.appBarLayout);
        toolbar_default = view.findViewById(R.id.toolbar_default);
        toolbar_nono = view.findViewById(R.id.toolbar_nono);
        searchTxt_nono = view.findViewById(R.id.nono_search_news);
        menu_img = view.findViewById(R.id.default_menu);
        seatchImg_nono = view.findViewById(R.id.nono_searchImg);
        convenientBanner = view.findViewById(R.id.convenientBanner);
        mDrawerLayout = getActivity().findViewById(R.id.drawer_layout);

        init();
        list = new ArrayList<NewsInfo>();
        newsDao = new NewsDao(view.getContext(),"News.db",null,1);
        newsDao.getDataBase(newsDao);

        list = newsDao.queryAllNews();
        adapter = new NewsAdapter(view.getContext(), list);
        //绑定适配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), NewActivity.class);
                NewsInfo news = list.get(position);
                intent.putExtra("author",list.get(position).getAuthor());
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("content",list.get(position).getContent());
                Toast.makeText(view.getContext(), "点击了第"+position+"个news", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


        menu_img.setOnClickListener(this);
        appBarLayout.addOnOffsetChangedListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        //通过vertical的偏移值除以totalScrollRange来获取到百分比
        float progress = (float) (((int) (Math.abs(i) / (appBarLayout.getTotalScrollRange() * 0.01))) * 0.01);

        if (i == 0) {
            // 展开状态 progress为0，展开状态的toolbar可见

            toolbar_default.setVisibility(View.VISIBLE);
            toolbar_nono.setVisibility(View.GONE);
            toolbar_default.getBackground().setAlpha(255);
            menu_img.getBackground().setAlpha(255);

            toolbar_nono.getBackground().setAlpha(255);

        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            //折叠状态 progress为1，折叠状态的toolbar可见

            toolbar_default.setVisibility(View.GONE);
            toolbar_nono.setVisibility(View.VISIBLE);
            toolbar_default.getBackground().setAlpha(255);

            toolbar_nono.getBackground().setAlpha(255);
            seatchImg_nono.getBackground().setAlpha(255);
            searchTxt_nono.getBackground().setAlpha(255);

        } else {

            //中间状态，两个toolbar是重叠在一起的，根据是从折叠到展开，还是从展开到折叠，同时加上进度progress确定两个toolbar的透明比

            if (toolbar_nono.getVisibility() == View.GONE) {// 折叠状态的toolbar不可见，则是从展开到折叠变化
                flag_defaulttono = true;
            } else if (toolbar_default.getVisibility() == View.GONE) {// 展开状态的toolbar不可见，则是从折叠到展开变化，为什么？因为只有到0的时候可见性才会变成gone，其他都是可见或改变透明度。
                flag_defaulttono = false;
            }// 以上两种情况都不是的话，则不需要改变flag_defaluttono的值

            toolbar_default.setVisibility(View.VISIBLE);
            toolbar_nono.setVisibility(View.VISIBLE);//

            if (flag_defaulttono) { // 0到1变换
                toolbar_default.getBackground().setAlpha((int) ((1 - progress) * 255));
                menu_img.getBackground().setAlpha((int) ((1 - progress) * 255));

                toolbar_nono.getBackground().setAlpha((int) (progress * 255));
                searchTxt_nono.getBackground().setAlpha((int) (progress * 255));
                seatchImg_nono.getBackground().setAlpha((int) (progress * 255));

            } else {

                toolbar_default.getBackground().setAlpha((int) (progress * 255));
                menu_img.getBackground().setAlpha((int) (progress * 255));

                toolbar_nono.getBackground().setAlpha((int) ((1 - progress) * 255));
                seatchImg_nono.getBackground().setAlpha((int) ((1 - progress) * 255));
                searchTxt_nono.getBackground().setAlpha((int) ((1 - progress) * 255));
            }

        }
    }

    private void loadTestDatas() {
        //本地图片集合
        for (int position = 0; position < 5; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     *初始化轮播图
     */
    public void init(){
        loadTestDatas();
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public LocalImageHolderView createHolder(View itemView) {
                return new LocalImageHolderView(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_localimage;
            }
        },localImages).setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("keaizp","onResume");
        convenientBanner.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }
}
