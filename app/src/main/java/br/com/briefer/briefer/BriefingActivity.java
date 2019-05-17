package br.com.briefer.briefer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Locale;

import br.com.briefer.briefer.model.Briefing;
import br.com.briefer.briefer.model.Budget;

public class BriefingActivity extends AppCompatActivity {

    private Briefing briefing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_briefing);

        Intent intent = getIntent();
        briefing = (Briefing) intent.getSerializableExtra("briefingSelected");

        setFields();

        FloatingActionButton btnEdit = findViewById(R.id.briefing_button_edit);
        btnEdit.setOnClickListener(v -> {
            Intent editBriefingIntent = new Intent(this, EditBriefingActivity.class);
            editBriefingIntent.putExtra("briefingToEdit", briefing);
            startActivityForResult(editBriefingIntent, 1);
        });

        FloatingActionButton btnDelete = findViewById(R.id.briefing_button_remove);
        btnDelete.setOnClickListener(v -> {
            //TODO make delete action
            Toast.makeText(BriefingActivity.this, "Deletando briefing...", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                assert data != null;
                briefing = (Briefing) data.getSerializableExtra("briefingEdit");
                setFields();
            }
        }
    }

    private void setFields() {
        setTitle(briefing.getProjTitle());

        TextView mProjTitle = findViewById(R.id.briefing_title);
        mProjTitle.setText(briefing.getProjTitle());

        TextView mClName = findViewById(R.id.briefing_cl_name);
        mClName.setText(briefing.getClName());

        TextView mClPhone = findViewById(R.id.briefing_cl_phone);
        mClPhone.setText(briefing.getClPhone());

        TextView mClEmail = findViewById(R.id.briefing_cl_email);
        mClEmail.setText(briefing.getClEmail());

        TextView mExamples = findViewById(R.id.briefing_examples);
        mExamples.setText(briefing.getExamples());

        TextView mNumPages = findViewById(R.id.briefing_num_pages);
        mNumPages.setText(String.valueOf(briefing.getNumPages()));

        TextView mSocialMedia = findViewById(R.id.briefing_social_media);
        mSocialMedia.setText(briefing.getSocialMedia());

        TextView mOutline = findViewById(R.id.briefing_outline);
        mOutline.setText(briefing.getOutline());

        TextView mDescription = findViewById(R.id.briefing_description);
        mDescription.setText(briefing.getDescription());

        TextView mHasVisual = findViewById(R.id.briefing_has_visual);
        mHasVisual.setText((briefing.isHasVisual()) ? "Sim." : "Não");

        TextView mHasLogo = findViewById(R.id.briefing_has_logo);
        mHasLogo.setText((briefing.isHasLogo()) ? "Sim." : "Não");

        TextView mHasCurrent = findViewById(R.id.briefing_has_current);
        mHasCurrent.setText((briefing.isHasCurrent()) ? "Sim." : "Não");

        ChipGroup briefingFeatures = findViewById(R.id.briefing_features);
        briefingFeatures.removeAllViews();
        for(String feature :briefing.getFeatures()){
            Chip featureChip = getChip(feature);
            briefingFeatures.addView(featureChip);
        }

        Budget budget = briefing.getBudget();
        TextView mTimeGoal = findViewById(R.id.briefing_time_goal);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        mTimeGoal.setText(sdf.format(budget.getTime_goal()));

        TextView mCost = findViewById(R.id.briefing_cost);
        mCost.setText(String.valueOf(budget.getCost()));
        
    }

    private Chip getChip(String text){
        final Chip chip = new Chip(this);

        chip.setChipDrawable(ChipDrawable.createFromResource(this, R.xml.action_chip));
        chip.setText(text);

        return chip;
    }
}
