package trips.tdp.fi.uba.ar.tripsandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Locale;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.R;
import trips.tdp.fi.uba.ar.tripsandroid.model.LoggedUser;

public class LogInActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private Button withoutLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        setContentView(R.layout.activity_log_in);

        if(LoggedUser.instance().isLogged()){
            LoginManager.getInstance().logOut();
        }

        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Login", "logged to facebook");

                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.d("Usuario facebook", object.toString());
                                BackEndClient backendClient = new BackEndClient();
                                String id = "";
                                String name = "";
                                try {
                                    id = object.getString("id");
                                    name = object.getString("name");
                                    LoggedUser user = LoggedUser.instance();
                                    user.setName(name);
                                } catch (JSONException e) {

                                }

                                backendClient.loginUser(id, name, LogInActivity.this,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response){
                                                Log.d("login", "posted login to webapp login sucessfully, going to next activity");

                                                LoggedUser user = LoggedUser.instance();
                                                Intent intent = new Intent(LogInActivity.this, CityListActivity.class);
                                                startActivity(intent);
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("login", "error loggeando contra webapp");
                                                LoginManager.getInstance().logOut();
                                            }
                                        }
                                );
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();



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
