package com.example.myapplication.work2;

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

import com.example.myapplication.work2.recycleView.PersonListRecyclerView;
import com.example.myapplication.work2.staticTable.Person;

public class PersonListAdapter extends PersonListRecyclerView.Adapter<PersonListAdapter.ViewHolder> {

    private List<Person> personList;

    private Context context;
    private LayoutInflater layoutInflater;

    public PersonListAdapter(Context context, List list) {
        this.personList = list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.person_list, null);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Log.e("FFFFFF",""+ position);
                //List<Goods> goods = response.body();
                Person person = personList.get(position);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Person data = personList.get(position);
        viewHolder.personListAdapterImager.setImageResource(data.getImage());
        viewHolder.personListAdapterName.setText(data.getName());
        viewHolder.personListAdapterArrows.setImageResource(data.getArrows());
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
        return personList.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView personListAdapterImager;
        private TextView personListAdapterName;
        private ImageView personListAdapterArrows;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            personListAdapterImager = (ImageView) view.findViewById(R.id.person_list_adapter_imager);
            personListAdapterName = (TextView) view.findViewById(R.id.person_list_adapter_name);
            personListAdapterArrows = (ImageView) view.findViewById(R.id.person_list_adapter_arrows);
        }
    }
}
