package br.com.briefer.briefer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.briefer.briefer.R;
import br.com.briefer.briefer.model.Briefing;

public class BriefingsAdapter extends BaseAdapter{

    private final List<Briefing> briefings;
    private final Context context;

    public BriefingsAdapter(List<Briefing> briefings, Context context) {
        this.briefings = briefings;
        this.context = context;
    }

    @Override
    public int getCount() {
       return briefings.size();
    }

    @Override
    public Object getItem(int i) {
        return briefings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Briefing briefing = briefings.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(convertView == null){
            view = inflater.inflate(R.layout.list_briefings, parent, false);
        }

        TextView mBriefingTitle = view.findViewById(R.id.list_briefing_title);
        mBriefingTitle.setText(briefing.getProj_title());

        TextView mTimeGoal = view.findViewById(R.id.list_briefing_time_goal);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String timeGoal = sdf.format(briefing.getBudget().getTime_goal());
        mTimeGoal.setText(timeGoal);


        TextView mCost = view.findViewById(R.id.list_briefing_cost);
        mCost.setText(String.valueOf(briefing.getBudget().getCost()));

        TextView mDescription = view.findViewById(R.id.list_briefing_description);
        mDescription.setText(briefing.getDescription());

        return view;

    }
}
