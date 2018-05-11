package ap.example.annirbas.staxi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class fortaxins extends AppCompatActivity{

    private EditText etUserName;
    private EditText etPassword;
    private EditText Nom;
    private EditText Prenom;
    private EditText Ntele;
    private RadioGroup rg,rg1;
    RadioButton homme,Etranger;
    RadioButton femme,Parmanance;
    String selection,selection1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fortaxins);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        Nom = (EditText) findViewById(R.id.Nom);
        Prenom = (EditText) findViewById(R.id.Prenom);
        Ntele = (EditText) findViewById(R.id.Ntele);

        femme=(RadioButton)findViewById(R.id.femme);
        homme=(RadioButton)findViewById(R.id.homme);
        rg = (RadioGroup) findViewById(R.id.radiogroup);
        Etranger=(RadioButton)findViewById(R.id.Etranger);
       Parmanance=(RadioButton)findViewById(R.id.Parmanance);
        rg1 = (RadioGroup) findViewById(R.id.radiogroupp);




    }

    public void inscription(View view) {
       String url="http://192.168.8.2/app/registerta.php?Nom_util_tax="+  etUserName.getText().toString()+"&Mot_d_passe="+ etPassword.getText().toString()
              +"&Nom_tax="+  Nom.getText().toString()+"&Pre_tax="+ Prenom.getText().toString()+"&Tele_tax="+ Ntele.getText().toString()+"&Sex_tax="+selection+
               "&DES_Type="+selection1
               ;

        new MyAsyncTaskgetNews().execute(url);

    }

    public void annuler(View view) {
        Intent ann=new Intent(fortaxins.this,annuler.class);
        startActivity(ann);
    }
    public void sexe(View view) {
        if (R.id.homme == rg.getCheckedRadioButtonId()) {
            selection = (String) homme.getText();
        } else {
            if (R.id.femme == rg.getCheckedRadioButtonId()) {
                selection = (String) femme.getText();

            }

        }

    }

    public void type(View view) {
        if (R.id.Parmanance == rg1.getCheckedRadioButtonId()) {
            selection1 = (String) Parmanance.getText();
        } else {
            if (R.id.Etranger == rg1.getCheckedRadioButtonId()) {
                selection1 = (String) Etranger.getText();

            }

        }
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

            try {
                JSONObject json= new JSONObject(progress[0]);
                //display response data
                Toast.makeText(getApplicationContext(),json.getString("msg"),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(fortaxins.this, LoginActivitytax.class);
                startActivity(intent);

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
