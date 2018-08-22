package com.parthhingorani.flyasia.Flights;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.parthhingorani.flyasia.R;

import java.util.Calendar;

public class FlightsFragment extends Fragment implements View.OnClickListener {

    TextView tvDate;
    NumberPicker numberPicker;
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

        tvDate.setOnClickListener(this);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                passengerCount = picker.getValue();
            }
        });
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
        }
    }
}
