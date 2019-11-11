package com.example.myapplication.work2.informationFragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.work2.R;
import com.example.myapplication.work2.personalConter.GetPhotoFromPhotoAlbum;
import com.example.myapplication.work2.recycleView.StaffMessageRecyclerView;
import com.example.myapplication.work2.table.User;

public class StaffMessageAdapter extends StaffMessageRecyclerView.Adapter<StaffMessageAdapter.ViewHolder> {

    private List<User> userList = new ArrayList<User>();

    private Context context;
    private LayoutInflater layoutInflater;
    private ImageView imageView;

    public StaffMessageAdapter(Context context, List<User> list) {
        this.userList = list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.staff_message_adapter, null);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Log.e("FFFFFF",""+ position);
                //List<Goods> goods = response.body();
                User Entryexit = userList.get(position);
//                Toast.makeText(v.getContext(), "fsadlkfjasdljfla" + goods.getTradeName(), Toast.LENGTH_LONG).show();
                // ������ת������������
//                Intent intent = new Intent(InoutMessageAdapterAdapter.this.context, CommodityDetailsActivity.class);
//                intent.putExtra("commodityId",Entryexit.getEntryexitId());
//                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaffMessageAdapter.ViewHolder viewHolder, int position) {
        //���ݵ��λ�ð�����
        User data = userList.get(position);
        viewHolder.staffMessageAdapterTvId.setText(""+data.getUserId());
        viewHolder.staffMessageAdapterTvPhone.setText(data.getUserPhone());
        viewHolder.staffMessageAdapterTvName.setText(data.getUserName());
        viewHolder.staffMessageAdapterTvType.setText(data.getUserType());
        viewHolder.staffMessageAdapterTvPhonenumber.setText(data.getUserPhonenumber());
        viewHolder.staffMessageAdapterTvEmail.setText(data.getUserEmail());
        Glide.with(viewHolder.staffMessageAdapterIvImage).load("http://192.168.1.103:8080/upload/桌面.jpg");
        Log.d("看看图片路径",data.getUserRandnumber());
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
        return userList.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        private ImageView staffMessageAdapterIvImage;
        private TextView staffMessageAdapterTvId;
        private TextView staffMessageAdapterTvPhone;
        private TextView staffMessageAdapterTvName;
        private TextView staffMessageAdapterTvType;
        private TextView staffMessageAdapterTvPhonenumber;
        private TextView staffMessageAdapterTvEmail;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            staffMessageAdapterIvImage = (ImageView) view.findViewById(R.id.staff_message_adapter_iv_image);
            staffMessageAdapterTvId = (TextView) view.findViewById(R.id.staff_message_adapter_tv_id);
            staffMessageAdapterTvPhone = (TextView) view.findViewById(R.id.staff_message_adapter_tv_phone);
            staffMessageAdapterTvName = (TextView) view.findViewById(R.id.staff_message_adapter_tv_name);
            staffMessageAdapterTvType = (TextView) view.findViewById(R.id.staff_message_adapter_tv_type);
            staffMessageAdapterTvPhonenumber = (TextView) view.findViewById(R.id.staff_message_adapter_tv_phonenumber);
            staffMessageAdapterTvEmail = (TextView) view.findViewById(R.id.staff_message_adapter_tv_email);
        }
    }
}
