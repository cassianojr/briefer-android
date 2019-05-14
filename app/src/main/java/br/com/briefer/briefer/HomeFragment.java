package br.com.briefer.briefer;

import android.app.Fragment;
import android.content.Context;
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
            //TODO briefing intent
            Toast.makeText(context, "Visualizando Briefing...", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void loadList() {
        //TODO connect to webservice using Retrofit
        List<Briefing> briefings = new ArrayList<Briefing>();

        Briefing staticBriefing = new Briefing();
        staticBriefing.setProjTitle("A static briefing");
        staticBriefing.setDescription("This is a static briefing just for test of the interface, this will be replaced soon by the dynamic briefings comming from the Retrofit and webservice!");

        Budget budget = new Budget();
        budget.setCost(2000);
        budget.setTime_goal(new Date());

        staticBriefing.setBudget(budget);
        briefings.add(staticBriefing);

        BriefingsAdapter adapter = new BriefingsAdapter(briefings, context);
        listBriefings.setAdapter(adapter);
    }
}
