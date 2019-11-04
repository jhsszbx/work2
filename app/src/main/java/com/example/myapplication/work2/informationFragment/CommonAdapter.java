package com.example.myapplication.work2.informationFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.work2.R;

import java.util.List;

public class CommonAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public CommonAdapter(@Nullable List<String> data) {
        super(R.layout.item_common,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.content_tv,item);
    }
}
