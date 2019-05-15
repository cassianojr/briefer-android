package br.com.briefer.briefer;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ProfileFragment extends Fragment {

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        context = view.getContext();

        Button btnLogout = view.findViewById(R.id.logout_button);
        btnLogout.setOnClickListener(v -> {
            //TODO add logout here

            Intent logoutIntent = new Intent(context, LoginActivity.class);
            startActivity(logoutIntent);

        });

        return view;
    }

}
