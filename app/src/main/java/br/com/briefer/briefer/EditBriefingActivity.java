package br.com.briefer.briefer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.briefer.briefer.model.Briefing;

public class EditBriefingActivity extends AppCompatActivity {

    private Briefing briefing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_briefing);

        briefing = (Briefing) getIntent().getSerializableExtra("briefingToEdit");
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
