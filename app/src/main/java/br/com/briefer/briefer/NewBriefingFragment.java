package br.com.briefer.briefer;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class NewBriefingFragment extends Fragment {

    private Context context;
    private Calendar calendar;

    private EditText mTimeGoal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_new_briefing, container, false);

        context = view.getContext();


        calendar = Calendar.getInstance();

        mTimeGoal = view.findViewById(R.id.new_briefing_time_goal);
        DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDatePicker();
        };

        mTimeGoal.setOnClickListener(view12 -> new DatePickerDialog(
                context,
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show());

        return view;
    }

    private void updateDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        mTimeGoal.setText(sdf.format(calendar.getTime()));
    }

}
