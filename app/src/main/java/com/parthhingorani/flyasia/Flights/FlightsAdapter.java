package com.parthhingorani.flyasia.Flights;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parthhingorani.flyasia.R;

import java.util.List;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.MyViewHolder> {

    private List<Flight> flightsList;
    static Context context;

    public FlightsAdapter(Context context, List<Flight> flightsList){
        FlightsAdapter.context =context;
        this.flightsList = flightsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_flights, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(flightsList.get(position));
    }

    @Override
    public int getItemCount() {
        return flightsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView ivFlightImage;
        TextView tvFlightName, tvDepTime, tvArrTime, tvDuration, tvHalt, tvPrice;

        private MyViewHolder(View itemView) {
            super(itemView);

            ivFlightImage = itemView.findViewById(R.id.ivFlightImage);
            tvFlightName = itemView.findViewById(R.id.tvFlightName);
            tvDepTime = itemView.findViewById(R.id.tvDepTime);
            tvArrTime = itemView.findViewById(R.id.tvArrTime);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvHalt = itemView.findViewById(R.id.tvHalt);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }

        public void setData(final Flight data){
            tvFlightName.setText(data.airlineName);
            tvDepTime.setText(data.departureTime);
            tvArrTime.setText(data.arrivalTime);
            tvDuration.setText(data.duration);
            tvHalt.setText(data.halt);
            tvPrice.setText(data.cost);
            Glide.with(context)
                    .load(data.airlineURL)
                    .into(ivFlightImage);
        }
    }
}
