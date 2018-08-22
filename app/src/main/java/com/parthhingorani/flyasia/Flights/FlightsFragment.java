package com.parthhingorani.flyasia.Flights;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;

import com.parthhingorani.flyasia.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FlightsFragment extends Fragment implements View.OnClickListener {

    TextView tvDate;
    FloatingActionButton fabSearch;
    NumberPicker numberPicker;
    ScrollView searchLayout;
    ConstraintLayout listingsLayout;
    FlightsAdapter flightsAdapter;
    RecyclerView rvFlights;
    List<Flight> flightsList;
    LinearLayoutManager linearLayoutManager;
    int passengerCount;

    public FlightsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flights, container, false);

        tvDate = view.findViewById(R.id.tvSearchDate);
        numberPicker = view.findViewById(R.id.npTravellers);
        fabSearch = view.findViewById(R.id.fabSearch);
        searchLayout = view.findViewById(R.id.layoutSearchFlights);
        listingsLayout = view.findViewById(R.id.layoutViewFlights);
        rvFlights = view.findViewById(R.id.rvFlights);

        tvDate.setOnClickListener(this);
        fabSearch.setOnClickListener(this);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                passengerCount = picker.getValue();
            }
        });

        flightsList = new ArrayList<>();
        populateFlights();

        flightsAdapter = new FlightsAdapter(getActivity(), flightsList);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFlights.setAdapter(flightsAdapter);
        rvFlights.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.tvSearchDate:
                // Get Current Date
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int yearOfFlight, int monthOfFlight, int dayOfFlight) {
                        String date = dayOfFlight + "-" + (monthOfFlight + 1) + "-" + yearOfFlight;
                        tvDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
                break;

            case R.id.fabSearch:
                searchLayout.setVisibility(View.GONE);
                listingsLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void populateFlights()  {
        
    }
}
