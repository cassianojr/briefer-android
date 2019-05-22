package br.com.briefer.briefer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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

public class EditBriefingActivity extends AppCompatActivity {

    private Briefing briefing;

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

    private Calendar calendar;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_briefing);

        briefing = (Briefing) getIntent().getSerializableExtra("briefingToEdit");

        setFields();
        handleDatePicker();
        handleFeatureTags();
        handleSubmit();

        Log.e("createdBy ", briefing.getCreatedBy());

    }

    private void handleSubmit() {
        Button btnSave = findViewById(R.id.edit_briefing_button);
        btnSave.setOnClickListener(v -> {
            if(verifyFields()){
                return;
            }


            updateCurrentBriefing();

            String jwtToken = "Bearer "+ PreferencesUtility.getUserToken(this);
            Call<Briefing> putBriefing = new RetrofitConfig().getBriefingService().putBriefing(briefing, jwtToken);
            putBriefing.enqueue(new Callback<Briefing>() {
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
                    if(res!= null){
                        Intent intent = new Intent();
                        intent.putExtra("briefingEdit", briefing);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Briefing> call, @NonNull Throwable t) {
                    Log.e("BriefingService", "Error on update briefing: "+t);
                }
            });
        });
    }

    private void updateCurrentBriefing() {
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
        boolean hasVisual = mHasVisual.isChecked();
        boolean hasLogo = mHasLogo.isChecked();
        boolean hasCurrent = mHasCurrent.isChecked();
        String timeGoal = mTimeGoal.getText().toString();
        String createdBy = briefing.getCreatedBy();
        String id = briefing.getId();

        Log.e("hasVisual", String.valueOf(hasVisual));
        Log.e("hasLogo", String.valueOf(hasLogo));
        Log.e("hasCurrent", String.valueOf(hasCurrent));


        //set budget
        Budget budget = new Budget();
        budget.setCost(cost);
        try {
            budget.setTime_goal(sdf.parse(timeGoal));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.briefing = new Briefing(clName, clPhone, clEmail, examples, numPages, hasVisual, hasLogo, hasCurrent, description, projTitle, socialMedia, outline, objective, features, budget, createdBy);
        this.briefing.setId(id);
    }

    private void handleFeatureTags() {
        ChipGroup entryChipGroup = findViewById(R.id.edit_briefing_tag_features);

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
    }

    private void handleDatePicker() {
        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            //update date picker
            mTimeGoal.setText(sdf.format(calendar.getTime()));
        };

        mTimeGoal.setOnClickListener(view12 -> new DatePickerDialog(
                this,
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show());
    }

    private boolean verifyFields() {
        int errors = 0;
        for(EditText field : fields){
            if(TextUtils.isEmpty(field.getText())){
                field.setError("Por favor preencha este campo.");
                errors++;
            }else{
                field.setError(null);
            }
        }

        return errors>0;
    }

    private void setFields() {
        this.mProjTitle = findViewById(R.id.edit_briefing_proj_title);
        mProjTitle.setText(briefing.getProjTitle());

        this.mClName = findViewById(R.id.edit_briefing_cl_name);
        mClName.setText(briefing.getClName());

        this.mClPhone = findViewById(R.id.edit_briefing_cl_phone);
        mClPhone.setText(briefing.getClPhone());

        this.mClemail = findViewById(R.id.edit_briefing_cl_email);
        mClemail.setText(briefing.getClEmail());

        this.mExamples = findViewById(R.id.edit_briefing_examples);
        mExamples.setText(briefing.getExamples());

        this.mNumPages = findViewById(R.id.edit_briefing_num_pages);
        mNumPages.setText(String.valueOf(briefing.getNumPages()));

        this.mSocialMedia = findViewById(R.id.edit_briefing_social_media);
        mSocialMedia.setText(briefing.getSocialMedia());

        this.mOutline = findViewById(R.id.edit_briefing_outline);
        mOutline.setText(briefing.getOutline());

        this.mObjective = findViewById(R.id.edit_briefing_objective);
        mObjective.setText(briefing.getObjective());

        this.mDescription = findViewById(R.id.edit_briefing_description);
        mDescription.setText(briefing.getDescription());

        this.mFeatures = findViewById(R.id.edit_briefing_features);
        ChipGroup entryChipGroup = findViewById(R.id.edit_briefing_tag_features);
        for(String feature : briefing.getFeatures()){
            Chip entryChip = getChip(entryChipGroup, feature);
            entryChipGroup.addView(entryChip);
            features.add(feature);
        }

        this.mCost = findViewById(R.id.edit_briefing_cost);
        mCost.setText(String.valueOf(briefing.getBudget().getCost()));

        this.mHasVisual = findViewById(R.id.edit_briefing_has_visual);
        mHasVisual.setChecked(briefing.isHasVisual());

        this.mHasLogo = findViewById(R.id.edit_briefing_has_logo);
        mHasLogo.setChecked(briefing.isHasLogo());

        this.mHasCurrent = findViewById(R.id.edit_briefing_has_current);
        mHasCurrent.setChecked(briefing.isHasCurrent());

        this.mTimeGoal = findViewById(R.id.edit_briefing_time_goal);
        mTimeGoal.setText(sdf.format(briefing.getBudget().getTime_goal()));

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
        final Chip chip = new Chip(this);

        chip.setChipDrawable(ChipDrawable.createFromResource(this, R.xml.default_chip));
        chip.setText(text);
        chip.setOnCloseIconClickListener(v -> entryChipGroup.removeView(chip));

        return chip;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            handleBack();
        }

        return true;
    }

    private void handleBack() {
        Intent intent = new Intent();
        intent.putExtra("briefingEdit", briefing);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        handleBack();
    }
}
