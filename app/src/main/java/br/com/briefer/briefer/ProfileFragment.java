package br.com.briefer.briefer;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import br.com.briefer.briefer.config.RetrofitConfig;
import br.com.briefer.briefer.model.User;
import br.com.briefer.briefer.util.PreferencesUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private Context context;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        context = view.getContext();

        handleLogout(view);
        handleEditProfile(view);

        handleUserInfo(view);

        return view;
    }

    private void handleUserInfo(View view) {
        String usrEmail = PreferencesUtility.getUserEmail(context);
        Call<User> getUserByEmail = new RetrofitConfig().getUserService().getUserByEmail(usrEmail);
        getUserByEmail.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                user = response.body();

                TextView mUserName = view.findViewById(R.id.user_profile_name);
                mUserName.setText(user.getName());

                TextView mUserEmail = view.findViewById(R.id.user_profile_email);
                mUserEmail.setText(user.getEmail());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(context, "Erro ao buscar usuario. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                Log.e("UserService", t.getMessage());
            }
        });
    }

    private void handleEditProfile(View view) {
        Button btnEditProfile = view.findViewById(R.id.edit_profile_button);
        btnEditProfile.setOnClickListener(v -> {
            Intent editProfileIntent = new Intent(context, EditProfileActivity.class);
            editProfileIntent.putExtra("user", user);

            startActivity(editProfileIntent);
        });
    }

    private void handleLogout(View view) {
        Button btnLogout = view.findViewById(R.id.logout_button);
        btnLogout.setOnClickListener(v -> {
            PreferencesUtility.setLoggedIn(context, false, "", "", "");

            Intent logoutIntent = new Intent(context, LoginActivity.class);
            startActivity(logoutIntent);

        });
    }
}
