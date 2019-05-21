package br.com.briefer.briefer;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.briefer.briefer.adapter.BriefingsAdapter;
import br.com.briefer.briefer.config.RetrofitConfig;
import br.com.briefer.briefer.model.Briefing;
import br.com.briefer.briefer.util.PreferencesUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private Context context;
    private ListView listBriefings;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = view.getContext();
        listBriefings = view.findViewById(R.id.list_briefings);
        loadList();

        listBriefings.setOnItemClickListener((parent, view1, position, id) ->{
            Briefing briefingSelected = (Briefing) listBriefings.getItemAtPosition(position);

            Intent briefingIntent = new Intent(context, BriefingActivity.class);
            briefingIntent.putExtra("briefingSelected", briefingSelected);
            startActivity(briefingIntent);

        });

        return view;
    }

    private void loadList() {
        fetchBriefings();
    }

    private void fetchBriefings() {
        String jwtToken = "Bearer "+PreferencesUtility.getUserToken(context);
        Call<List<Briefing>> callListBriefings = new RetrofitConfig().getBriefingService().getBriefings(jwtToken);
        callListBriefings.enqueue(new Callback<List<Briefing>>() {
            @Override
            public void onResponse(@NonNull Call<List<Briefing>> call,@NonNull Response<List<Briefing>> response) {
                List<Briefing> briefings = response.body();

                BriefingsAdapter adapter = new BriefingsAdapter(briefings, context);
                listBriefings.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Briefing>> call,@NonNull Throwable t) {
                Log.e("BriefingService   ", "Error on fetch briefings: "+t.getMessage());
            }
        });
    }
}
