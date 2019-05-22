package br.com.briefer.briefer;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.briefer.briefer.config.RetrofitConfig;
import br.com.briefer.briefer.model.Briefing;
import br.com.briefer.briefer.model.Budget;
import br.com.briefer.briefer.util.PreferencesUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewBriefingFragment extends Fragment {

    private Context context;
    private Calendar calendar;

    private EditText mProjTitle;
    private EditText mClName;
    private EditText mClPhone;
    private EditText mClemail;
    private EditText mExamples;
    private EditText mNumPages;
    private EditText mSocialMedia;
    private EditText mOutline;
    private EditText mObjective;
    private EditText mDescription;
    private EditText mFeatures;
    private EditText mCost;
    private CheckBox mHasVisual;
    private CheckBox mHasLogo;
    private CheckBox mHasCurrent;
    private EditText mTimeGoal;

    private List<String> features = new ArrayList<>();
    private List<EditText> fields = new ArrayList<>();

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_new_briefing, container, false);
        context = view.getContext();

        setFields(view);

        calendar = Calendar.getInstance();

        mTimeGoal = view.findViewById(R.id.new_briefing_time_goal);
        DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            //update date picker
            mTimeGoal.setText(sdf.format(calendar.getTime()));

        };

        mTimeGoal.setOnClickListener(view12 -> new DatePickerDialog(
                context,
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show());
        ChipGroup entryChipGroup = view.findViewById(R.id.new_briefing_tag_features);

        mFeatures = view.findViewById(R.id.new_briefing_features);
        mFeatures.setOnKeyListener((v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_COMMA || keyCode == KeyEvent.KEYCODE_ENTER){
                 String text = mFeatures.getText().toString().replaceAll(",","");
                 if(!text.equals("")){
                     Chip entryChip = getChip(entryChipGroup, text);
                     entryChipGroup.addView(entryChip);
                     features.add(text);
                 }

                 mFeatures.setText("");
            }
            return false;
        });

        Button newBriefingButton = view.findViewById(R.id.new_briefing_button);
        newBriefingButton.setOnClickListener(v -> {
            if(verifyFields()){
                return;
            }
            //get the data
            String projTitle = mProjTitle.getText().toString();
            String clName = mClName.getText().toString();
            String clPhone =  mClPhone.getText().toString();
            String clEmail =  mClemail.getText().toString();
            String examples = mExamples.getText().toString();
            int numPages = Integer.parseInt(mNumPages.getText().toString());
            String socialMedia = mSocialMedia.getText().toString();
            String outline =  mOutline.getText().toString();
            String objective = mObjective.getText().toString();
            String description =  mDescription.getText().toString();
            double cost = Double.parseDouble(mCost.getText().toString());
            boolean hasVisual = mHasVisual.isSelected();
            boolean hasLogo = mHasLogo.isSelected();
            boolean hasCurrent = mHasCurrent.isSelected();
            String timeGoal = mTimeGoal.getText().toString();

            //set budget
            Budget budget = new Budget();
            budget.setCost(cost);
            try {
                budget.setTime_goal(sdf.parse(timeGoal));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String createdBy = PreferencesUtility.getUserId(context);
            Briefing briefing = new Briefing(clName, clPhone, clEmail, examples, numPages, hasVisual, hasLogo, hasCurrent, description, projTitle, socialMedia, outline, objective, features, budget, createdBy);
            String jwtToken = "Bearer "+PreferencesUtility.getUserToken(context);

            Call<Briefing> postBriefing = new RetrofitConfig().getBriefingService().postBriefing(briefing, jwtToken);
            postBriefing.enqueue(new Callback<Briefing>() {
                @Override
                public void onResponse(@NonNull Call<Briefing> call, @NonNull Response<Briefing> response) {
                    Log.i("BriefingService", String.valueOf(response.code()));

                    try {
                        if (response.errorBody() != null) {
                            Log.e("BriefingService", response.errorBody().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Briefing res = response.body();
                    if(res != null){
                        //Transaction to home fragment
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.content_frame, homeFragment);
                        transaction.commit();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<Briefing> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Falha ao criar novo briefing. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                    Log.e("BriefingService", "Error on create new briefing: "+t);
                }
            });

        });
        return view;
    }

    private boolean verifyFields() {
        int errors = 0;
        for(EditText field : fields){
           if(field.getText().toString().equals("")){
               field.setError("Por favor preencha este campo.");
               errors++;
           }else{
               field.setError(null);
           }
       }
       return errors>0;
    }

    private void setFields(View view) {

        mProjTitle = view.findViewById(R.id.new_briefing_proj_title);

        mClName = view.findViewById(R.id.new_briefing_cl_name);
        mClPhone = view.findViewById(R.id.new_briefing_cl_phone);
        mClemail = view.findViewById(R.id.new_briefing_cl_email);
        mExamples = view.findViewById(R.id.new_briefing_examples);
        mNumPages = view.findViewById(R.id.new_briefing_num_pages);
        mSocialMedia = view.findViewById(R.id.new_briefing_social_media);
        mOutline = view.findViewById(R.id.new_briefing_outline);
        mObjective = view.findViewById(R.id.new_briefing_objective);
        mDescription = view.findViewById(R.id.new_briefing_description);
        mFeatures = view.findViewById(R.id.new_briefing_features);
        mCost = view.findViewById(R.id.new_briefing_cost);
        mHasVisual = view.findViewById(R.id.new_briefing_has_visual);
        mHasLogo = view.findViewById(R.id.new_briefing_has_logo);
        mHasCurrent = view.findViewById(R.id.new_briefing_has_current);
        mTimeGoal = view.findViewById(R.id.new_briefing_time_goal);

        //add all the fields in the ArrayList
        fields.add(mProjTitle);
        fields.add(mClName);
        fields.add(mClPhone);
        fields.add(mClemail);
        fields.add(mExamples);
        fields.add(mOutline);
        fields.add(mObjective);
        fields.add(mDescription);
        fields.add(mDescription);
        fields.add(mCost);
    }

    private Chip getChip(final ChipGroup entryChipGroup, String text){
        final Chip chip = new Chip(context);

        chip.setChipDrawable(ChipDrawable.createFromResource(context, R.xml.default_chip));
        chip.setText(text);
        chip.setOnCloseIconClickListener(v -> entryChipGroup.removeView(chip));

        return chip;
    }
}
