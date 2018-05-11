package ap.example.annirbas.staxi;


import android.content.Intent;
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
public class mescoursesTax extends AppCompatActivity {
    //adapter class
    ArrayList<AdapterItemst>    listnewsData = new ArrayList<>();
    MyCustomAdapter myadapter;

    String idobes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mescourses_tax);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        idobes=(String) b.get("cod_tax");
        iin.putExtra("cod_tax",idobes);

        //add data and view it
        //listnewsData.add(new AdapterItems(1,"developer"," develop apps"));
        myadapter=new MyCustomAdapter(listnewsData);
        ListView lsNews=(ListView) findViewById(R.id.LVNews);
        lsNews.setAdapter(myadapter);//intisal with data

        String url="http://192.168.8.2/app/mescoursestax.php?Cod_tax="+idobes;

        new  MyAsyncTaskgetNews().execute(url);
    }



    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItemst> listnewsDataAdpater ;

        public MyCustomAdapter(ArrayList<AdapterItemst>  listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return listnewsDataAdpater.toString();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.tickettax, null);

            final   AdapterItemst s = listnewsDataAdpater.get(position);

            TextView datedemn=( TextView)myView.findViewById(R.id.datedemn);
            datedemn.setText( String.valueOf( s.date_demande));
            TextView heuredemn=( TextView)myView.findViewById(R.id.heuredemn);
            heuredemn.setText(s.heure_demande);
            TextView datedep=( TextView)myView.findViewById(R.id.datedep);
            datedep.setText(s.date_depart);
            TextView heuredep=( TextView)myView.findViewById(R.id.heuredep);
            heuredep.setText( String.valueOf( s.heure_depart));

            TextView typecou=( TextView)myView.findViewById(R.id.typecou);
            typecou.setText(s.Tp_cou_pref);
            TextView consultation=( TextView)myView.findViewById(R.id.consultation);
            consultation.setText(s.consultaion);
            TextView observation=( TextView)myView.findViewById(R.id.observation);
            observation.setText(s.Observation);

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
                //JSONObject json= new JSONObject(progress[0]);
                JSONArray json =new JSONArray(progress[0]);
                for (int i=0;i<json.length();i++){
                    JSONObject user= json.getJSONObject(i);
                    listnewsData.add(new AdapterItemst(user.getString("date_demande"),user.getString("heure_demande"),user.getString("date_depart")
                            ,user.getString("heure_depart")
                            ,user.getString("Tp_cou_pref"),user.getString("consultaion"),user.getString("Observation")));
                }
                myadapter.notifyDataSetChanged();
                //display response data
                Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();

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