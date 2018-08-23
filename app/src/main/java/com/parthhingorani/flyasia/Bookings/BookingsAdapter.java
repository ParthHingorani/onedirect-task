package com.parthhingorani.flyasia.Bookings;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parthhingorani.flyasia.Database;
import com.parthhingorani.flyasia.Flights.Flight;
import com.parthhingorani.flyasia.R;

import java.util.List;

//recyclerview adapter for booked flight history
public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder> {

    //data structures
    private List<Flight> flightsList;
    static Context context;
    static Database database;

    //constructor
    public BookingsAdapter(Context context, List<Flight> flightsList, Database database){
            BookingsAdapter.context = context;
            this.flightsList = flightsList;
            BookingsAdapter.database = database;
    }

    //object holder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_bookings, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
    }

    //set object data
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.setData(flightsList.get(position));
    }

    //returns booked flights count
    @Override
    public int getItemCount() {
            return flightsList.size();
    }

    //inner viewholder class
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //data structures
        AppCompatImageView ivFlightImage;
        TextView tvFlightName, tvDepTime, tvArrTime, tvDuration, tvHalt, tvPrice;

        //initialize structures
        private MyViewHolder(View itemView) {
            super(itemView);

            ivFlightImage = itemView.findViewById(R.id.ivBookedFlightImage);
            tvFlightName = itemView.findViewById(R.id.tvBookedFlightName);
            tvDepTime = itemView.findViewById(R.id.tvBookedDepTime);
            tvArrTime = itemView.findViewById(R.id.tvBookedArrTime);
            tvDuration = itemView.findViewById(R.id.tvBookedDuration);
            tvHalt = itemView.findViewById(R.id.tvBookedHalt);
            tvPrice = itemView.findViewById(R.id.tvBookedPrice);
        }

        //set data in placeholders
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
