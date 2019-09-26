package com.example.myapplication.work2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;


import com.example.myapplication.work2.fragment.HomeFragment;
import com.example.myapplication.work2.fragment.InformationFragment;
import com.example.myapplication.work2.fragment.PersonFragment;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private FragmentTransaction fragmentTransaction;
    private int lastIndex;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setListener();
        initFragment();
    }


    public void setListener() {
        mBottomNavigationView = findViewById(R.id.nav_view);
        // 解决当item大于三个时，非平均布局问题
        // BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                setFragmentPosition(0);
                                break;
                            case R.id.navigation_dashboard:
                                setFragmentPosition(1);
                                break;
                            case R.id.navigation_person:
                                setFragmentPosition(2);
                                break;
                            default:
                                break;
                        }
                        // 这里注意返回true,否则点击失效
                        return true;
                    }
                });
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new InformationFragment());
        fragments.add(new PersonFragment());
        // 初始化展示MessageFragment
        setFragmentPosition(0);
    }

    // 回收 Fragment
    private void setFragmentPosition(int position){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = fragments.get(position);
        Fragment lastFragment = fragments.get(lastIndex);
        lastIndex = position;
        fragmentTransaction.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            fragmentTransaction.add(R.id.fl_content, currentFragment);
        }
        fragmentTransaction.show(currentFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }


}
