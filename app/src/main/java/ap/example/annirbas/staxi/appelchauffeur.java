package ap.example.annirbas.staxi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;

public class appelchauffeur extends AppCompatActivity {
    //adapter class
    ArrayList<AdapterItems1>    listnewsData = new ArrayList<>();
    MyCustomAdapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appelchauffeur);


        //add data and view it
        // listnewsData.add(new AdapterItems(1,"developer"," develop apps"));
        myadapter=new MyCustomAdapter(listnewsData);
        ListView lsNews=(ListView) findViewById(R.id.LVNews);
        lsNews.setAdapter(myadapter);//intisal with data

        String url="http://192.168.8.2/app/voiture.php";

        new  MyAsyncTaskgetNews().execute(url);
    }



    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems1> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<AdapterItems1>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.ticket1, null);

            final   AdapterItems1 s = listnewsDataAdpater.get(position);


            TextView marque=( TextView)myView.findViewById(R.id.marque);
            marque.setText(s.marque);
            TextView modele=( TextView)myView.findViewById(R.id.modele);
            modele.setText(s.modele);
            TextView matricule=( TextView)myView.findViewById(R.id.matricule);
            matricule.setText( String.valueOf( s.matricule_voi));
            TextView nom=( TextView)myView.findViewById(R.id.nom);
            nom.setText(s.Nom_tax);
            TextView pre=( TextView)myView.findViewById(R.id.pre);
            pre.setText(s.Pre_tax);
            TextView tele=( TextView)myView.findViewById(R.id.tele);
            tele.setText(s.Tele_tax);
            return myView;
        }

    }


    // get news from server
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
                String s="";
                //JSONObject json= new JSONObject(progress[0]);
                JSONArray json =new JSONArray(progress[0]);
                for (int i=0;i<json.length();i++){
                    JSONObject user= json.getJSONObject(i);
                    s=s+""+
                    listnewsData.add(new AdapterItems1(user.getString("marque"),user.getString("modele"),user.getInt("matricule_voi")
                            ,user.getString("Nom_tax"),user.getString("Pre_tax"),user.getString("Tele_tax")));
                }
                myadapter.notifyDataSetChanged();
                //display response data
                Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();
Toast.makeText(appelchauffeur.this,s,Toast.LENGTH_LONG).show();
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

