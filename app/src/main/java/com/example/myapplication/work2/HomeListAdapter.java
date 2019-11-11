package com.example.myapplication.work2;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.work2.recycleView.HomeListRecyclerView;
import com.example.myapplication.work2.staticTable.Home;

public class HomeListAdapter extends HomeListRecyclerView.Adapter<HomeListAdapter.ViewHolder> {

    private List<Home> homeList = new ArrayList<Home>();

    private Context context;
    private LayoutInflater layoutInflater;

    public HomeListAdapter(Context context, List list) {
        this.homeList = list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.home_list, null);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Log.e("FFFFFF",""+ position);
                //List<Goods> goods = response.body();
                Home home = homeList.get(position);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Home data = homeList.get(position);
        viewHolder.homeListImagename.setText(data.getImageName());
        viewHolder.homeListImage.setImageResource(data.getImage());
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView homeListImage;
        private TextView homeListImagename;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            homeListImage = (ImageView) view.findViewById(R.id.home_list_image);
            homeListImagename = (TextView) view.findViewById(R.id.home_list_imagename);
        }
    }
}
