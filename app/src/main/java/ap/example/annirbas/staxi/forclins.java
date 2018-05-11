package ap.example.annirbas.staxi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class forclins extends Activity{
    private RadioGroup radioGroup;
    private EditText Nom,Prenom,Ntele,Nomutil,Motdpasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.forclins);
        Nom=(EditText)findViewById(R.id.Nom);
        Prenom=(EditText)findViewById(R.id.Prenom);
        Ntele=(EditText)findViewById(R.id.Ntele);
        Nomutil=(EditText)findViewById(R.id.Nomutil);
        Motdpasse=(EditText)findViewById(R.id.Motdpasse);

    }
    public void annuler(View view) {
        Intent ann=new Intent(forclins.this,annuler.class);
        startActivity(ann);
    }
    public void inscription(View view) {
        String url="http://192.168.8.2/app/registercl.php?Nom_cl="+  Nom.getText().toString()+"&Pre_cl="+ Prenom.getText().toString()
                +"&tele_cl="+  Ntele.getText().toString()+"&Nom_util_cl="+ Nomutil.getText().toString()+"&Mot_d_passe_cl="+ Motdpasse.getText()
                .toString();


        new forclins.MyAsyncTaskgetNews().execute(url);

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



                String s="";
                JSONArray jarray=new JSONArray(progress[0]);
                JSONObject jsonn=jarray.getJSONObject(0);
                //s="" + jsonn.get("cod_cl");
                s=s+"cod_cl"+jsonn.get("cod_cl")+"tele_cl"+jsonn.getString("tele_cl");
                Toast.makeText(forclins.this,s,Toast.LENGTH_SHORT).show();
                /*if(s.length()>0){
                Intent intent = new Intent(getApplicationContext(), annuler.class);
                intent.putExtra("id",a);
                startActivity(intent);
                Toast.makeText(forclins.this,s,Toast.LENGTH_SHORT).show();
                }else {
                Toast.makeText(forclins.this,"no connect",Toast.LENGTH_LONG).show();
                }*/
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
