package com.parthhingorani.flyasia.Flights;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parthhingorani.flyasia.Database;
import com.parthhingorani.flyasia.R;

import java.util.List;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.MyViewHolder> {

    private List<Flight> flightsList;
    static Context context;
    static Database database;

    public FlightsAdapter(Context context, List<Flight> flightsList, Database database){
        FlightsAdapter.context = context;
        this.flightsList = flightsList;
        FlightsAdapter.database = database;
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
        Button btBook;

        private MyViewHolder(View itemView) {
            super(itemView);

            ivFlightImage = itemView.findViewById(R.id.ivFlightImage);
            tvFlightName = itemView.findViewById(R.id.tvFlightName);
            tvDepTime = itemView.findViewById(R.id.tvDepTime);
            tvArrTime = itemView.findViewById(R.id.tvArrTime);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvHalt = itemView.findViewById(R.id.tvHalt);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btBook = itemView.findViewById(R.id.btBook);
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

            btBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirm Booking?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    database.insertBookedFlight(data.airlineURL, data.airlineName, data.duration, data.departureTime, data.arrivalTime,
                                            data.halt, data.cost, data.source, data.destination, data.date);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                }
            });
        }
    }
}
