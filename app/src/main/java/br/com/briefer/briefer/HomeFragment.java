package br.com.briefer.briefer;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.briefer.briefer.adapter.BriefingsAdapter;
import br.com.briefer.briefer.model.Briefing;
import br.com.briefer.briefer.model.Budget;


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
        //TODO connect to webservice using Retrofit
        List<Briefing> briefings = new ArrayList<Briefing>();


        Budget budget = new Budget();
        budget.setCost(2000);
        budget.setTime_goal(new Date());

        List<String> features = new ArrayList<>();
        features.add("Design bonito");
        features.add("Otimização");
        features.add("Sistema legal");

        Briefing staticBriefing = new Briefing(
                "Teste",
                "(21)9999999",
                "teste@teste.com",
                "Facebook",
                15,
                false,
                true,
                false,
                "a project test for only tests uses",
                "Test Briefing",
                "no have",
                "I don't know what write here",
                "make the world a better place",
                features,
                budget
                );

        staticBriefing.setBudget(budget);
        briefings.add(staticBriefing);

        BriefingsAdapter adapter = new BriefingsAdapter(briefings, context);
        listBriefings.setAdapter(adapter);
    }
}
