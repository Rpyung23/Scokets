package com.virtualcode7ecuador.scokets.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuador.scokets.Poo.cSocketServer;
import com.virtualcode7ecuador.scokets.R;

import java.util.ArrayList;

public  class cRecyclerViewSockets extends RecyclerView.Adapter<cRecyclerViewSockets.cViewHolderSockets> implements View.OnClickListener
{
    private int resource_view;
    private Activity activity;
    private ArrayList<cSocketServer> oS;
    private View.OnClickListener onClickListener;
    public cRecyclerViewSockets(int resource_view, Activity activity, ArrayList<cSocketServer> oS)
    {
        this.resource_view = resource_view;
        this.activity = activity;
        this.oS = oS;
    }
    @NonNull
    @Override
    public cViewHolderSockets onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource_view,parent,false);
        view.setOnClickListener(this);
        return new cViewHolderSockets(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderSockets holder, int position)
    {
        holder.textViewName.setText(oS.get(position).getName().toString());
        holder.textViewPort.setText(String.valueOf(oS.get(position).getPort()));
        holder.textViewIp.setText(oS.get(position).getIp_server().toString());
    }

    @Override
    public int getItemCount()
    {
        return oS.size();
    }
    public void setOnClickListener(View.OnClickListener onClickListener)
    {
        this.onClickListener=onClickListener;
    }
    @Override
    public void onClick(View v) {
        if (onClickListener!=null)
        {
            onClickListener.onClick(v);
        }
    }
    public class cViewHolderSockets extends RecyclerView.ViewHolder
    {
        private TextView textViewName;
        private TextView textViewIp;
        private TextView textViewPort;
        public cViewHolderSockets(@NonNull View itemView)
        {
            super(itemView);
            textViewIp = itemView.findViewById(R.id.textview_ip_server_recycler);
            textViewName = itemView.findViewById(R.id.textview_name_server_recycler);
            textViewPort =  itemView.findViewById(R.id.textview_port_ser_recycler);
        }
    }
}
