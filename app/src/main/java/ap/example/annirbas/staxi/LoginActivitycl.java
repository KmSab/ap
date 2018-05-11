package ap.example.annirbas.staxi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivitycl extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */


    // UI references.
    private EditText Nom_util_cl;
    private EditText Mot_d_passe;
    private View mProgressView;
    private View mLoginFormView;
    private TextView ed1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_activitycl);
        Nom_util_cl = (EditText) findViewById(R.id.Nom_util_cl);
        Mot_d_passe = (EditText) findViewById(R.id.Mot_d_passe);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    public void entrez(View view) {

        String url = "http://192.168.8.2/app/logincl.php?Nom_util_cl=" + Nom_util_cl.getText().toString() + "&Mot_d_passe_cl=" + Mot_d_passe.getText().toString();
        //Create a bundle object

        //start the DisplayActivity
        new MyAsyncTaskgetNews().execute(url);

    }

    public void oublie(View view) {
        Intent neo = new Intent(LoginActivitycl.this, confirmation_cl_oub.class);
        startActivity(neo);
    }

    public void inscrir(View view) {
        Intent i = new Intent(LoginActivitycl.this, confirmation_tele_cl.class);
        startActivity(i);
    }

    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //before works
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                String NewsData;
                //define the url we have to connect with
                URL url = new URL(params[0]);
                //make connect with url and send request
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(7000);//set timeout to 5 seconds

                try {
                    //getting the response data
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //convert the stream to string
                    NewsData = ConvertInputToStringNoChange(in);
                    //send to display data
                    publishProgress(NewsData);
                } finally {
                    //end connection
                    urlConnection.disconnect();
                }

            } catch (Exception ex) {
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {

            try {
                String s="";
                JSONArray jarray=new JSONArray(progress[0]);
                JSONObject json=jarray.getJSONObject(0);
                String a="" + json.get("cod_cl");
                String b= ""+json.get("Mot_d_passe_cl");
                s=s+"cod_cl"+json.get("cod_cl")+"Nom_util_cl"+json.getString("Nom_util_cl")+"Mot_d_passe_cl="+json.getString("Mot_d_passe_cl");
                if(s.length()>0){
                    Intent intent = new Intent(getApplicationContext(), demande_course.class);
                    intent.putExtra("id",a);
                    intent.putExtra("nom",b);
                    startActivity(intent);
                    Toast.makeText(LoginActivitycl.this,s,Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(LoginActivitycl.this,"no connect",Toast.LENGTH_LONG).show();
                }
               // Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();


            } catch (Exception ex) {
            }


        }

        protected void onPostExecute(String result2) {


        }


    }

    // this method convert any stream to string
    public static String ConvertInputToStringNoChange(InputStream inputStream) {

        BufferedReader bureader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String linereultcal = "";

        try {
            while ((line = bureader.readLine()) != null) {

                linereultcal += line;

            }
            inputStream.close();


        } catch (Exception ex) {
        }

        return linereultcal;
    }
}
