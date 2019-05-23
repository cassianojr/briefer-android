package br.com.briefer.briefer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.briefer.briefer.config.RetrofitConfig;
import br.com.briefer.briefer.model.User;
import br.com.briefer.briefer.model.UserUpdate;
import br.com.briefer.briefer.util.PreferencesUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mEmail;
    private EditText mCurrentPassword;
    private EditText mNewPassword;
    private EditText mConfirmPassword;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setUser();
        setFields();
        handleSubmit();
    }

    private void handleSubmit() {
        Button btnSubmit = findViewById(R.id.edit_profile_action_button);
        btnSubmit.setOnClickListener(v -> {
            String name = mName.getText().toString();
            String email = mEmail.getText().toString();
            String currentPassword = mCurrentPassword.getText().toString();
            String newPassword = mNewPassword.getText().toString();
            String confirmNewPassword = mConfirmPassword.getText().toString();

            if(checkFormErrors(newPassword, confirmNewPassword, name, email)){
                UserUpdate userUpdate = new UserUpdate(name, email, currentPassword, newPassword, confirmNewPassword);

                String jwtToken = "Bearer "+PreferencesUtility.getUserToken(this);
                Call<User> putUser = new RetrofitConfig().getUserService().putUser(userUpdate, jwtToken);
                putUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        User userUpdated = response.body();
                        if(userUpdated != null){
                            Toast.makeText(EditProfileActivity.this, "Você será deslogado para atualização de usuario!", Toast.LENGTH_SHORT).show();

                            Intent loginIntent = new Intent(EditProfileActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                            finish();
                        }else{
                            Log.e("UserService", String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        Toast.makeText(EditProfileActivity.this, "Erro ao atualizar usuario.", Toast.LENGTH_SHORT).show();
                        Log.e("UserService ", t.getMessage());
                    }
                });
            }

        });
    }

    private boolean checkFormErrors(String newPassword, String confirmNewPassword, String name, String email) {
        if(!TextUtils.equals(newPassword, confirmNewPassword)){
            mNewPassword.setError("As senhas mão correspondem!");
            mConfirmPassword.setError("As senhas não correspondem!");
            return false;
        }

        mName.setError((TextUtils.isEmpty(name) ? "Digite o nome." : null));
        mEmail.setError((TextUtils.isEmpty(email) ? "Digite o email" : null));
        mNewPassword.setError(null);
        mConfirmPassword.setError(null);

        return !TextUtils.isEmpty(name) || !TextUtils.isEmpty(email);
    }

    private void setUser() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }

    private void setFields() {
        mName = findViewById(R.id.edit_profile_name);
        mEmail = findViewById(R.id.edit_profile_email);
        mCurrentPassword = findViewById(R.id.edit_profile_old_password);
        mNewPassword = findViewById(R.id.edit_profile_password);
        mConfirmPassword = findViewById(R.id.edit_profile_confirm_password);

        mName.setText(user.getName());
        mEmail.setText(user.getEmail());
    }
}
