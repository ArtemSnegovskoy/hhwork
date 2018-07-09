package ru.handh.training.voteonoffice.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import ru.handh.training.voteonoffice.R;
import ru.handh.training.voteonoffice.ui.base.BaseActivity;
import ru.handh.training.voteonoffice.ui.signup.SignUpActivity;
import ru.handh.training.voteonoffice.ui.voteslist.VoteListActivity;

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
            //Toast.makeText(getApplicationContext(), "Запомнили логин", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, VoteListActivity.class));
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

//                finish();
//                startActivity(new Intent(this, VoteListActivity.class));
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
        editTextEmail.setError(getText(R.string.error_need_email));
        editTextEmail.requestFocus();
    }

    @Override
    public void showEmailIncorrectError() {
        editTextEmail.setError(getText(R.string.error_valid_email));
        editTextEmail.requestFocus();
    }

    @Override
    public void showPasswordEmptyError() {
        editTextPassword.setError(getText(R.string.error_need_password));
        editTextPassword.requestFocus();
    }

    @Override
    public void showPasswordLenghtError() {
        editTextPassword.setError(getText(R.string.error_password_lenght));
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
        startActivity(new Intent(this, VoteListActivity.class));
    }
}