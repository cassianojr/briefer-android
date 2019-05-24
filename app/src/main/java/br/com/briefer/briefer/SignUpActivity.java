package br.com.briefer.briefer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.briefer.briefer.config.RetrofitConfig;
import br.com.briefer.briefer.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setFields();
        handleSubmit();
    }

    private void handleSubmit() {
        Button mSubmit = findViewById(R.id.sign_up_button);
        mSubmit.setOnClickListener(v -> {

            //get form values
            String password = mPassword.getText().toString();
            String confirmPassword = mConfirmPassword.getText().toString();
            String name = mName.getText().toString();
            String email = mEmail.getText().toString();

            //check for errors
            if(checkFormErrors(password, confirmPassword, name, email)){

                //post user
                User user = new User(name, email, password);
                Call<User> postUser = new RetrofitConfig().getUserService().postUser(user);
                postUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call,@NonNull Response<User> response) {
                        User newUser = response.body();
                        if(newUser != null){
                            Toast.makeText(SignUpActivity.this, "Usuario Criado com sucesso!", Toast.LENGTH_SHORT).show();

                            //redirect to login activity
                            Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        Log.e("UserService", t.getMessage());
                        Toast.makeText(SignUpActivity.this, "Erro ao criar usuario. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private boolean checkFormErrors(String password, String confirmPassword, String name, String email) {
        if(!TextUtils.equals(password, confirmPassword)){
            mPassword.setError("As senhas mão correspondem!");
            mConfirmPassword.setError("As senhas não correspondem!");
            return false;
        }

        mName.setError((TextUtils.isEmpty(name) ? "Digite o nome." : null));
        mEmail.setError((TextUtils.isEmpty(email) ? "Digite o email" : null));
        mPassword.setError(null);
        mConfirmPassword.setError(null);

        return !TextUtils.isEmpty(name) || !TextUtils.isEmpty(email);
    }

    private void setFields() {
        mName = findViewById(R.id.sign_up_name);
        mEmail = findViewById(R.id.sign_up_email);
        mPassword = findViewById(R.id.sign_up_password);
        mConfirmPassword = findViewById(R.id.sign_up_confirm_password);
    }
}
