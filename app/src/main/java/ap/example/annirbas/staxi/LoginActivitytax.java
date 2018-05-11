package ap.example.annirbas.staxi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
public class LoginActivitytax extends AppCompatActivity  {

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
    private EditText etUserName;
    private EditText etPassword;
    private View mProgressView;
    private View mLoginFormView;
    private Button btnentre,btninsc;
    private TextView ed1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_logintax);
        // Set up the login form.
        etUserName = (EditText) findViewById(R.id.Nom_util_tax);
        etPassword = (EditText) findViewById(R.id.Mot_d_passe);

        ed1=(TextView) findViewById( R.id.ed1);
        ed1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent neo = new Intent(LoginActivitytax.this, confirmation_tax_oub.class);
                startActivity(neo);
            }
        } );




        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);}

    public void Entrez(View view) {
        String url="http://192.168.8.2/app/logintax.php?Nom_util_tax="+  etUserName.getText().toString()+"&Mot_d_passe="+ etPassword.getText().toString();

        new LoginActivitytax.MyAsyncTaskgetNews().execute(url);

    }
    public void inscrir(View view) {
        Intent i=new Intent(LoginActivitytax.this,confirmation_tele_tax.class);
        startActivity(i);
    }



    public class MyAsyncTaskgetNews extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //before works
        }
        @Override
        protected String  doInBackground(String... params) {
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

            }catch (Exception ex){}
            return null;
        }
        protected void onProgressUpdate(String... progress) {

            try { String s="";

                JSONArray jarray=new JSONArray(progress[0]);
                JSONObject json=jarray.getJSONObject(0);
                String a="" + json.get("Cod_tax");
                String b=""+json.get("Mot_d_passe");
                s=s+"cod_tax"+json.get("Cod_tax")+"Nom_util_tax"+json.getString("Nom_util_tax")+"Mot_d_passe="+json.getString("Mot_d_passe");
                if(s.length()>0){
                    Intent intent = new Intent(getApplicationContext(), comptetax.class);
                    intent.putExtra("cod_tax",a);
                    intent.putExtra("psw",b);

                    startActivity(intent);
                    Toast.makeText(LoginActivitytax.this,s,Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),json.getString("msg"),Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivitytax.this,"no connect",Toast.LENGTH_LONG).show();
                }

        } catch (Exception ex) {

            }
        }

        protected void onPostExecute(String  result2){


        }




    }

    // this method convert any stream to string
    public static String ConvertInputToStringNoChange(InputStream inputStream) {

        BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
        String line ;
        String linereultcal="";

        try{
            while((line=bureader.readLine())!=null) {

                linereultcal+=line;

            }
            inputStream.close();


        }catch (Exception ex){}

        return linereultcal;
    }


}
