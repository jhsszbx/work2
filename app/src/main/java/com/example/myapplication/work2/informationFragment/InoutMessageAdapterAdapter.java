package com.example.myapplication.work2.informationFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.work2.R;
import com.example.myapplication.work2.recycleView.InOutMessageRecyclerView;
import com.example.myapplication.work2.table.Entryexit;

public class InoutMessageAdapterAdapter extends InOutMessageRecyclerView.Adapter<InoutMessageAdapterAdapter.ViewHolder> {

    private List<Entryexit> entryexitList;

    private Context context;
    private LayoutInflater layoutInflater;

    public InoutMessageAdapterAdapter(Context context, List<Entryexit> list) {
        this.entryexitList = list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * ����viewhodler���൱��listview��getview�еĴ���view��viewhodler
     *
     * @param
     * @param i
     * @return
     */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.inout_message_adapter, null);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Log.e("FFFFFF",""+ position);
                //List<Goods> goods = response.body();
                Entryexit Entryexit = entryexitList.get(position);
//                Toast.makeText(v.getContext(), "fsadlkfjasdljfla" + goods.getTradeName(), Toast.LENGTH_LONG).show();
                // ������ת������������
//                Intent intent = new Intent(InoutMessageAdapterAdapter.this.context, CommodityDetailsActivity.class);
//                intent.putExtra("commodityId",Entryexit.getEntryexitId());
//                context.startActivity(intent);
            }
        });
        return viewHolder;
    }


    /*
     * 设置时区，获取数据库datetime并转回成String显示出来
     */
    public static String dateToStamp(long dateLong)  {
        Date date = new Date();
        date.setTime(dateLong);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String datestr = simpleDateFormat.format(date);
        return datestr;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //���ݵ��λ�ð�����
        Entryexit data = entryexitList.get(position);
        if (data.getEntryexitTime()==0){
            viewHolder.inoutMessageAdapterTvTime.setText("未记载有时间");
        }else {
            viewHolder.inoutMessageAdapterTvTime.setText(dateToStamp(data.getEntryexitTime())+"");
        }
        viewHolder.inoutMessageAdapterTvPhone.setText(data.getUserPhone());
        viewHolder.inoutMessageAdapterTvName.setText(data.getUserName());
        viewHolder.inoutMessageAdapterTvType.setText(data.getUserType());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * �õ�������
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return entryexitList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private View view;
        private TextView inoutMessageAdapterTvTime;
        private TextView inoutMessageAdapterTvPhone;
        private TextView inoutMessageAdapterTvName;
        private TextView inoutMessageAdapterTvType;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            inoutMessageAdapterTvTime = (TextView) view.findViewById(R.id.inout_message_adapter_tv_time);
            inoutMessageAdapterTvPhone = (TextView) view.findViewById(R.id.inout_message_adapter_tv_phone);
            inoutMessageAdapterTvName = (TextView) view.findViewById(R.id.inout_message_adapter_tv_name);
            inoutMessageAdapterTvType = (TextView) view.findViewById(R.id.inout_message_adapter_tv_type);
        }
    }
}
