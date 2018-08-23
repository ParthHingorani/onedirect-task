package com.parthhingorani.flyasia.Flights;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parthhingorani.flyasia.Database;
import com.parthhingorani.flyasia.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FlightsFragment extends Fragment implements View.OnClickListener {

    TextView tvDate, tvError;
    FloatingActionButton fabSearch;
    NumberPicker numberPicker;
    Spinner spnSource, spnDestination;
    ScrollView searchLayout;
    LinearLayout listingsLayout;
    FlightsAdapter flightsAdapter;
    RecyclerView rvFlights;
    List<Flight> flightsList;
    LinearLayoutManager linearLayoutManager;
    Database database;
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
        tvError = view.findViewById(R.id.emsgNoFlights);
        numberPicker = view.findViewById(R.id.npTravellers);
        fabSearch = view.findViewById(R.id.fabSearch);
        searchLayout = view.findViewById(R.id.layoutSearchFlights);
        listingsLayout = view.findViewById(R.id.layoutViewFlights);
        rvFlights = view.findViewById(R.id.rvFlights);
        spnSource = view.findViewById(R.id.spnSource);
        spnDestination = view.findViewById(R.id.spnDestination);

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

        database = new Database(getActivity());
        flightsList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFlights.setLayoutManager(linearLayoutManager);

        database.insertIntoFlight();
        database.insertIntoAirport();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, database.getAirports());
        spnSource.setAdapter(spinnerAdapter);
        spnDestination.setAdapter(spinnerAdapter);

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
                populateFlights();
                flightsAdapter = new FlightsAdapter(getActivity(), flightsList, database);
                rvFlights.setAdapter(flightsAdapter);
                break;
        }
    }

    private void populateFlights()  {

        flightsList = database.getFlights(spnSource.getSelectedItem().toString().substring(0,3),
                spnDestination.getSelectedItem().toString().substring(0,3),
                tvDate.getText().toString());
//        flightsAdapter.notifyDataSetChanged();

        if (flightsList == null)    {
            tvError.setVisibility(View.VISIBLE);
            rvFlights.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.GONE);
            rvFlights.setVisibility(View.VISIBLE);
        }
    }
}
