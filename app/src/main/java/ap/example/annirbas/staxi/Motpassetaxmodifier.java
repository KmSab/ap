package ap.example.annirbas.staxi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Motpassetaxmodifier extends Activity {
    String idobes,pswbserve;
    EditText amdp,nmdp,vmdp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_motpassetaxmodifier );
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        idobes=(String) b.get("cod_tax");
        pswbserve=(String) b.get("psw");
       Toast.makeText(Motpassetaxmodifier.this,idobes,Toast.LENGTH_SHORT).show();
      Toast.makeText(Motpassetaxmodifier.this,pswbserve,Toast.LENGTH_SHORT).show();
        amdp=(EditText)findViewById(R.id.amdp);
        nmdp=(EditText)findViewById(R.id.nmdp);
        vmdp=(EditText)findViewById(R.id.vmdp);
    }

    public void envoyee(View view) {
        String url="http://192.168.8.2/app/changemdptax.php?amdp="+  amdp.getText().toString()+"&nmdp="+ nmdp.getText().toString()
                +"&vmdp="+  vmdp.getText().toString()+"&Cod_tax="+ idobes+"&Mot_d_passe="+ pswbserve;

        new Motpassetaxmodifier.MyAsyncTaskgetNews().execute(url);
        Toast.makeText(Motpassetaxmodifier.this,"  Vous avez changé votre mot de passe successful",Toast.LENGTH_SHORT).show();


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
                Toast.makeText(getApplicationContext(),json.getString("msg"),Toast.LENGTH_LONG).show();

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

