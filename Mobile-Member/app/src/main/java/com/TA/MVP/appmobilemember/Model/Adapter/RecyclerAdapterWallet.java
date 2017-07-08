package com.TA.MVP.appmobilemember.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.TA.MVP.appmobilemember.R;

/**
 * Created by Zackzack on 08/07/2017.
 */

public class RecyclerAdapterWallet extends RecyclerView.Adapter<RecyclerAdapterWallet.ViewHolder> {
    private String[] nominal = {"Rp. 50.000", "Rp. 100.000", "Rp. 300.000", "Rp. 500.000", "Rp. 1.000.000"};
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemnominal;

        public ViewHolder(final View itemview){
            super(itemview);
            itemnominal = (TextView) itemview.findViewById(R.id.card_wallet_nominal);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Toast.makeText(itemview.getContext(),"Clicking card number " + position, Toast.LENGTH_SHORT).show();
//                    doStartActivity(itemview.getContext(), SomethingActivity.class);

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pesan,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemnominal.setText(nominal[position]);
    }

    @Override
    public int getItemCount() {
        return nominal.length;
    }

    public static void doStartActivity(Context context, Class activityClass) {
        Intent _intent = new Intent(context, activityClass);
        context.startActivity(_intent);
    }
}
