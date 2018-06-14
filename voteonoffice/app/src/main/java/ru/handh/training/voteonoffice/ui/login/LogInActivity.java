package ru.handh.training.voteonoffice.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import ru.handh.training.voteonoffice.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.ui.base.BaseActivity;
import ru.handh.training.voteonoffice.ui.signup.SignUpActivity;
import ru.handh.training.voteonoffice.ui.signup.SignUpPresenter;

public class LogInActivity extends BaseActivity implements View.OnClickListener , LogInMvpView{

    @Inject
    LogInPresenter logInPresenter;


    FirebaseAuth firebaseAuth;

    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        logInPresenter.attachView(this);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressbar);


        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null) {
            //временная заглушка до появления списка голосований
            Toast.makeText(getApplicationContext(), "Запомнили логин", Toast.LENGTH_SHORT).show();

            //finish();
            // startActivity(new Intent(this, VotesActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(this, SignUpActivity.class));
                break;

            case R.id.buttonLogin:
                logInPresenter.userLogin(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        logInPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showEmailEmptyError() {
        editTextEmail.setError("Email is required");
        editTextEmail.requestFocus();
    }

    @Override
    public void showEmailIncorrectError() {
        editTextEmail.setError("Please enter a valid email");
        editTextEmail.requestFocus();
    }

    @Override
    public void showPasswordEmptyError() {
        editTextPassword.setError("Password is required");
        editTextPassword.requestFocus();
    }

    @Override
    public void showPasswordLenghtError() {
        editTextPassword.setError("Minimum lenght of password should be 6");
        editTextPassword.requestFocus();
    }

    @Override
    public void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoginError(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginSuccess(){
        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
    }
}