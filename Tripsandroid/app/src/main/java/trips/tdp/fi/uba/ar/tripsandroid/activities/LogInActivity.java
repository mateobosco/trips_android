package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.LoggedUser;

public class LogInActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    private LoginButton loginButton;
    private Button withoutLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Login", "logged to facebook");
                LoggedUser user = LoggedUser.instance();
                user.setLogged(true);
                Intent intent = new Intent(LogInActivity.this, CityListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d("Login", "canceled login to facebook");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("Login", "error logging to facebook");
            }
        });

        withoutLoginButton = (Button) findViewById(R.id.no_loggin_button);

        withoutLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, CityListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
